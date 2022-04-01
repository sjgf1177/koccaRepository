package com.credu.course;

import com.credu.library.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010. 7. 6
 * Time: ���� 6:59:56
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("unchecked")
public class HitNumberAdminBean {
    private ConfigSet config;
	private int row;

    public HitNumberAdminBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

    public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null,ls2=null;
		ArrayList list1 	= null;
		StringBuffer sb 	= new StringBuffer();
		DataBox dbox 		= null;

		String p_grcode		= box.getString("s_grcode");
		String p_gyear		= box.getString("s_gyear");
		String p_grseq		= box.getStringDefault("s_grseq","ALL");
		String ss_uclass	= box.getStringDefault("s_upperclass","ALL");	//�����з�
		String ss_mclass	= box.getStringDefault("s_middleclass","ALL");	//�����з�
		String ss_lclass	= box.getStringDefault("s_lowerclass","ALL");	//�����з�
		String p_subjcourse = box.getString("s_subjcourse");
		String p_membergubun= box.getString("p_membergubun");				//ȸ��<br>�з�
		String p_subjsearchkey  = box.getString("s_subjsearchkey");
        String ss_area      = box.getString("s_area");
        String ss_biyong      = box.getString("s_biyong");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sb.append("\nSELECT subj,subjnm,studentlimit,hitnumber, ");
            sb.append("\n (case when biyong=0 then '����' else '����' end) as biyong, ");
            sb.append("\n (case when area='B0' then '���' ");
            sb.append("\n       when area='G0' then '����' else '����' end) as area,");
            sb.append("\n (case when ISUSE='Y' then '��' else '�ƴϿ�' end) as isuse, ");
            sb.append("\n ISABLEREVIEW,REVIEWTYPE,REVIEWDAYS ");
			sb.append("\nfrom VZ_SCSUBJSEQ ");
			sb.append("\nwhere ");
			sb.append("\n    grcode=" + SQLString.Format(p_grcode));

            if (!p_gyear.equals(""))
			    sb.append("\n    and gyear="  + SQLString.Format(p_gyear));

			if (!p_grseq.equals("ALL")) {
				sb.append("\n	and grseq="  + SQLString.Format(p_grseq));
			}
			if (!ss_uclass.equals("ALL")) {
				sb.append("\n	and scupperclass ="  + SQLString.Format(ss_uclass));
			}
			if (!ss_mclass.equals("ALL")) {
				sb.append("\n	and scmiddleclass="  + SQLString.Format(ss_mclass));
			}
			if (!ss_lclass.equals("ALL")) {
				sb.append("\n	and sclowerclass ="  + SQLString.Format(ss_lclass));
			}
			if (!p_subjcourse.equals("ALL")) {
				sb.append("\n	and scsubj="  + SQLString.Format(p_subjcourse));
			}

			if (p_membergubun.length()>0&&!p_membergubun.equals("ALL")) {
				sb.append("\n	and membergubun="  + SQLString.Format(p_membergubun));//ȸ��<br>�з�
			}

             if(!ss_area.equals("T"))
                sb.append(" and area="+SQLString.Format(ss_area));

            if(!ss_biyong.equals("A"))
                sb.append(" and biyong="+SQLString.Format(ss_biyong));

            if(!p_subjsearchkey.equals(""))
                 sb.append(" and subjnm like '%"+p_subjsearchkey+"%'");

            sb.append("\n	order by hitnumber desc");

			ls = connMgr.executeQuery(sb.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				list1.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sb.toString());
			throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

    public int Update(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;
		String [] strsplit = box.getString("p_update").split(",");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

            for(int i=0;i<strsplit.length;i++)
            {
                String [] tmp=strsplit[i].split("@");

                sql =  "update TZ_SUBJ set \n";
                sql+=  " hitnumber=? \n";
                sql+= "	 where subj=?";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString( 1, tmp[0]);                                           //�����ڵ�
                pstmt.setString( 2, tmp[1]);
                isOk = pstmt.executeUpdate();
            }

            if (isOk==1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
		}
		catch(Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
}
