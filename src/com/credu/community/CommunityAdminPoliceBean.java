//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityAdminPoliceBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��: �̳��� 05.11.23 _ �Ұ��� Ŀ�´�Ƽ ó�� ��������
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.common.GetCodenm;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommunityAdminPoliceBean {
    private ConfigSet config;
    private int row;

    public CommunityAdminPoliceBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row"));  //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
    * �ҷ� Ŀ�´�Ƽ �亯
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int updatePolice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement   pstmt = null;
        ListSet     ls          = null;
        String      sql         = "";
        String      sql1        = "";

        int         isOk        = 0;

        String v_policeno  = box.getString("p_policeno");
        String v_repmemo   = StringManager.replace(box.getString("content"),"<br>","\n");
        String s_userid    = box.getSession("userid");
        String s_name      = box.getSession("name");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false); 

		   /*********************************************************************************************/
		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
		   try {
			   v_repmemo =(String) NamoMime.setNamoContent(v_repmemo);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/ 
int index = 1;
			sql  =" update tz_cmupolice set  str_fg=2"
//                +"                         ,repmemo=empty_clob()"
                +"                         ,repmemo=?"
                +"                         ,str_userid=?"
                +"                         ,str_dte=to_char(sysdate, 'YYYYMMDDHH24MISS')"
                +"  where policeno =?";
           pstmt = connMgr.prepareStatement(sql);
				pstmt.setCharacterStream(index++, new StringReader(v_repmemo), v_repmemo.length());
//		   pstmt.setString(index++, v_repmemo);
           pstmt.setString   (index++, s_userid                       );
           pstmt.setString   (index++, v_policeno                       );
           isOk = pstmt.executeUpdate();

//			sql1 = "select repmemo from tz_cmupolice where policeno = '" + v_policeno + "'";
//           connMgr.setOracleCLOB(sql1, v_repmemo);       //      (��Ÿ ���� ���)


               //�Ϸù�ȣ ���ϱ�
               int v_mailno=0;
               sql1 = "select NVL(max(MAILNO), 0)   from TZ_CMUMAIL ";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_mailno = ls.getInt(1);

index = 1;
               sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
                    +"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
                    +"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
                    +"               values  (?,?,?,?"
//                    +"                       ,?,?,?,?,?,empty_clob()"
                    +"                       ,?,?,?,?,?,?"
                    +"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
                    ;
               pstmt = connMgr.prepareStatement(sql);


               //�߽��� �̸���
               String v_tmp_send_email="";
               sql1 = "select email   from tz_member where userid = '"+s_userid+"' ";
               System.out.println(sql1);
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) v_tmp_send_email = ls.getString(1);

if(ls != null) { try { ls.close(); } catch (Exception e) {} }
               sql1 = "select a.policeno, a.cmuno  , a.cmu_nm   , a.userid,b.name,b.email "
                     +"  from tz_cmupolice a,tz_member b "
                     +" where a.userid   = b.userid "
                     +"   and a.policeno ='"+v_policeno+"'";
               ls = connMgr.executeQuery(sql1);
               while (ls.next()) {
                     v_mailno =v_mailno+1;
                     pstmt.setInt   (index++, v_mailno                                );//�Ϸù�ȣ
                     pstmt.setString(index++, ls.getString(4)                         );//�����ھ��̵�
                     pstmt.setString(index++, ls.getString(5)                         );//�����ڸ�
                     pstmt.setString(index++, ls.getString(6)                         );//�������̸���
                     pstmt.setString(index++, ls.getString(2)                         );//Ŀ�´�Ƽ��ȣ
                     pstmt.setString(index++, ls.getString(3)                            );//Ŀ�´�Ƽ��
                     pstmt.setString(index++ ,s_userid                                );//�߽��ھ��̵�
                     pstmt.setString(index++ ,v_tmp_send_email                        );//�߽����̸���
                     pstmt.setString(index++ , ""                                     );//���� 
					pstmt.setCharacterStream(index++, new StringReader(v_repmemo), v_repmemo.length());
//					 pstmt.setString(index++, v_repmemo                                    );//����
                     pstmt.setString(index++, "6"                                    );//����
                     pstmt.setString(index++, "�Ұ���Ŀ�´�Ƽ�Ű�"                     );//���и�
                     isOk = pstmt.executeUpdate();
//                     sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
//                     connMgr.setOracleCLOB(sql1, v_repmemo);
                    // if(isOk > 0 ) {
                     //    if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                    // }
               }

           if(isOk > 0 ) {
               if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
           }

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
    * ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   ����Ʈ
    * @throws Exception
    */
    public ArrayList selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
		// ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
        String sql     = "";
        String count_sql     = "";
        String head_sql = "";
		String body_sql = "";		
        String group_sql = "";
        String order_sql = "";
        DataBox             dbox    = null;
		
		String s_userid  = box.getSession      ("userid");
        String v_str_fg  = box.getStringDefault("p_str_fg","1");

	    String  s_grcode	 = box.getSession("tem_grcode");
		String  v_grtype	 = GetCodenm.get_grtype(box,s_grcode);
		
        int v_pageno     = box.getInt		   ("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            head_sql  ="\n  select    a.policeno  policeno , a.cmuno   cmuno , a.cmu_nm    cmu_nm  "
                 +"\n           ,a.userid    userid   , a.email   email , a.content   content "
                 +"\n           ,a.singo_dte singo_dte, a.str_fg  str_fg, a.repmemo   repmemo "
//                 +"\n           ,a.str_dte   str_dte  , a.str_userid    , d.cmu_nm    cmu_nm  "
				 +"\n           ,a.str_dte   str_dte  , a.str_userid   "
                 +"\n           ,e.kor_name  room_master,d.member_cnt member_cnt,d.accept_dte accept_dte,d.intro intro"
                 +"\n           ,b.deptnam         bdeptnam                ,b.jikupnm              bjikupnm "     
                 +"\n           ,b.jikwinm         bjikwinm                ,b.name                 bname"          
                 +"\n           ,NVL(c.deptnam,'') cdeptnam                ,NVL(c.jikupnm,'')      cjikupnm "     
                 +"\n           ,NVL(c.jikwinm,'') cjikwinm                ,NVL(c.name,'')         cname " ;          
            body_sql += "\n    from tz_cmupolice a,tz_member b,tz_member c,tz_cmubasemst d"
                 +"\n       ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') e"
                 +"\n   where a.cmuno            = d.cmuno  "
                 +"\n     and a.userid           = b.userid "  
                 +"\n     and d.cmuno            = e.cmuno  "
                 // ������ : 05.11.04 ������ : �̳��� _(+)  ����
//                 +"\n     and a.str_userid           = c.userid(+) "  
				 +"\n     and a.str_userid       =  c.userid(+) "  
                 +"\n     and a.str_fg           = '"+v_str_fg+"' ";
            	// +"\n     and d.grtype           = '"+v_grtype+"' " ;
            order_sql += "\n  order by a.policeno desc ";
			
			sql= head_sql+ body_sql+ group_sql+ order_sql;
            ls = connMgr.executeQuery(sql);
            
			count_sql= "select count(*) " + body_sql;
			int totalrowcount= BoardPaging.getTotalRow(connMgr, count_sql);
			
            ls.setPageSize(row);                         // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalrowcount);  // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage();    // ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount();    // ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount" , new Integer(row));
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }



    /**
    * ��ȸ
    * @param box          receive from the form object and session
    * @return ArrayList   ��ȸ
    * @throws Exception
    */
    public ArrayList selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet             ls      = null;
        ArrayList           list    = new ArrayList();
        String              sql     = "";
        String              sql1    = "";
        DataBox             dbox    = null;
		String s_userid    = box.getSession("userid");
        String v_policeno  = box.getString("p_policeno");
        int v_pageno       = box.getInt   ("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            sql  ="\n  select    a.policeno  policeno , a.cmuno   cmuno , a.cmu_nm    cmu_nm  "
                 +"\n           ,a.userid    userid   , a.email   email , a.content   content "
                 +"\n           ,a.singo_dte singo_dte, a.str_fg  str_fg, a.repmemo   repmemo "
//                 +"\n           ,a.str_dte   str_dte  , a.str_userid    , d.cmu_nm    cmu_nm  "
				 +"\n           ,a.str_dte   str_dte  , a.str_userid    "
                 +"\n           ,e.kor_name  room_master,d.member_cnt member_cnt,d.accept_dte accept_dte,d.intro intro"
                 +"\n           ,b.deptnam         bdeptnam                ,b.jikupnm              bjikupnm "     
                 +"\n           ,b.jikwinm         bjikwinm                ,b.name                 bname"          
                 +"\n           ,NVL(c.deptnam,'') cdeptnam                ,NVL(c.jikupnm,'')      cjikupnm "     
                 +"\n           ,NVL(c.jikwinm,'') cjikwinm                ,NVL(c.name,'')         cname"          
                 +"\n    from tz_cmupolice a,tz_member b,tz_member c,tz_cmubasemst d"
                 +"\n       ,(select cmuno,userid,kor_name,grade from tz_cmuusermst where grade='01' and close_fg='1') e"
                 +"\n   where a.cmuno            = d.cmuno  "
                 +"\n     and a.userid           = b.userid "  
                 +"\n     and d.cmuno            = e.cmuno  "
                 // ������ : 05.11.04 ������ : �̳��� _(+)  ����
//                 +"\n     and a.str_userid           = c.userid(+) "  
				 +"\n     and a.str_userid       =  c.userid(+) "  
                 +"\n     and a.policeno         = '"+v_policeno+"'";

            ls = connMgr.executeQuery(sql);
            

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

}
