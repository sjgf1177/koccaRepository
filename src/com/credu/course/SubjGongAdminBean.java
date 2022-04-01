//**********************************************************
//  1. ��      ��: ������ �������� BEAN
//  2. ���α׷���:  SubjGongAdminBean.java
//  3. ��      ��: ������ �������� BEAN
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 11. 11
//  7. ��      ��: �̳��� 05.11.17 _ connMgr.setOracleCLOB ����1
//**********************************************************
package com.credu.course;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.*;
import com.credu.system.CodeConfigBean;
import com.credu.system.CodeData;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubjGongAdminBean {
    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10;                    //    �������� ���õ� ����÷�� ����
    private ConfigSet config;
	private int row;
	
    public SubjGongAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
	public static int getFILE_LIMIT(){
		return FILE_LIMIT;
	}

    /**
    ������ �������� ��Ȳ  ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ������ �������� ��Ȳ
    */
     @SuppressWarnings("unchecked")
	public ArrayList selectListGongAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        String sql1         = "";

		DataBox dbox = null;

        String  ss_grcode    = box.getStringDefault("s_grcode","ALL");        //�����׷�
        String  ss_gyear     = box.getStringDefault("s_gyear","ALL");         //�⵵
        String  ss_grseq     = box.getStringDefault("s_grseq","ALL");         //��������
        String  ss_uclass    = box.getStringDefault("s_upperclass","ALL");    //�����з�(��)
        String  ss_mclass    = box.getStringDefault("s_middleclass","ALL");   //�����з�(��)
        String  ss_lclass    = box.getStringDefault("s_lowerclass","ALL");    //�����з�(��)
        String  ss_subjcourse= box.getStringDefault("s_subjcourse","ALL");    //����&�ڽ�
        String  ss_subjseq   = box.getStringDefault("s_subjseq","ALL");       //���� ����
        String  ss_isclosed  = box.getStringDefault("s_isclosed","ALL");      //�������࿩��
        String  ss_notpageing  = box.getStringDefault("s_notpageing","ALL");      //����¡ó������
        String ss_area          = box.getString("s_area");                 //����,����,���
        String ss_biyong        = box.getString("s_biyong");                 //��ü,����,����

        String  v_orderColumn   = box.getString("p_orderColumn");               //������ �÷���
        String  v_orderType     = box.getString("p_orderType");                 //������ ����

        int    v_pageno      = box.getInt("p_pageno");

        String sql_add1 = "";                                        // �������� ���� �߰� ����(�����ڵ�)
        String sql_add2 = "";                                        // �������� ���� �߰� ����(�����ڵ�)
        String sql_add3 = "";                                        // �������� ���� ������ ����� ���� (mssql ��)
		String sql_add4 = "";										// Group By ���ǿ� �߰�.
        ArrayList list3 = (ArrayList)CodeConfigBean.getCodeList("noticeCategory","",1);    // ī�װ���������Ʈ
        CodeData data3 = null;
        String v_code    = "";
        int    v_codecnt = list3.size();

        try {

            //   ����ī�װ� ���� ���� ����
            for(int i = 0; i < v_codecnt; i++) {
                data3   = (CodeData)list3.get(i);
                v_code   = data3.getCode();

                //����ī�װ��ڵ�
                if (i == 0) {                                           // ó�� ���� ������ ���Ͽ�
                    sql_add1 += ", ";
                } else {                                                // ���ڰ� ���ս� ���̿� '/' �߰�
                    sql_add1 += " || '/' ||  ";
                }
                // ������ �ڵ�  ID colnum
                sql_add1 += StringManager.makeSQL(v_code) ;

                if ((i+1) == v_codecnt ) sql_add1 += " as types ";   // ������ �ο��� ���

                //ī�װ� ����
				sql_add2 += ", ";

				if(i == 0) {
					sql_add3 += " , TO_CHAR(max(a." + v_code+ ")) || '/' ||"  ;
				}
				else if ((i+1) < v_codecnt ) {
					sql_add3 += " TO_CHAR(max(a." + v_code+ ")) || '/' ||"  ;
				}
				else {
					sql_add3 += " TO_CHAR(max(a." + v_code+ ")) as typescnt";
				}

				if ((i+1) <= v_codecnt ) {
					sql_add2 += " (select count(*) from tz_gong where types=" + StringManager.makeSQL(v_code)+
							" and subj = a.subj and year = a.year and subjseq = a.subjseq) as " + v_code ;
				}

				if(i > 0) {
					sql_add4 += ", a.types";
				}
            }

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

			sql1 = "select  a.grcode, a.grcodenm, a.gyear, a.grseq, a.course, a.cyear, a.courseseq, a.coursenm, a.subj, ";
			sql1 += " a.year, a.subjseq, a.subjseqgr, a.subjnm, a.isonoff, a.edustart, a.eduend";
			sql1 += " , a.types, a.isbelongcourse, a.subjcnt ";
			sql1 += sql_add3;
			sql1 += " from (";
            sql1 += "  select a.grcode grcode, b.grcodenm grcodenm, a.gyear gyear, a.grseq grseq, a.course course, a.cyear cyear,       ";
            sql1 += "         a.courseseq courseseq, a.coursenm coursenm, a.subj subj, a.year year, a.subjseq subjseq,a.subjseqgr, a.subjnm subjnm, ";
            sql1 += "         a.isonoff isonoff, a.edustart edustart, a.eduend eduend    , a.isbelongcourse, a.subjcnt                                               ";
            sql1 += sql_add1;
            sql1 += sql_add2;
            sql1 += "    from VZ_SCSUBJSEQ a, TZ_GRCODE b, TZ_GONG c                                                                    ";
            sql1 += "   where a.grcode = b.grcode                                                                                       ";
			sql1 += "     and a.subj    =  c.subj  (+) and a.year   =  c.year (+) and a.subjseq  =  c.subjseq (+) " ;

            if (!ss_grcode.equals("ALL")) {
                sql1+= " and a.grcode = '" + ss_grcode + "'";
            }
            if (!ss_gyear.equals("ALL")) {
                sql1+= " and a.gyear = '" + ss_gyear + "'";
            }
            if (!ss_grseq.equals("ALL")) {
                sql1+= " and a.grseq = '" + ss_grseq + "'";
            }

            if (!ss_uclass.equals("ALL")) {
                sql1+= " and a.scupperclass = '" + ss_uclass + "'";
            }
            if (!ss_mclass.equals("ALL")) {
                sql1+= " and a.scmiddleclass = '" + ss_mclass + "'";
            }
            if (!ss_lclass.equals("ALL")) {
                sql1+= " and a.sclowerclass = '" + ss_lclass + "'";
            }

            if (!ss_subjcourse.equals("ALL")) {
                sql1+= " and a.scsubj = '" + ss_subjcourse + "'";
            }
            if (!ss_subjseq.equals("ALL")) {
                sql1+= " and a.scsubjseq = '" + ss_subjseq + "'";
            }
            if (!ss_isclosed.equals("ALL")) {
                sql1+= " and isclosed = '" + ss_isclosed + "'";
            }
            if(!ss_area.equals("T"))
                sql1+=" and a.area="+SQLString.Format(ss_area);
            if (ss_biyong.equals("Z"))
                sql1+= " and a.BIYONG        = 0";
            else if (ss_biyong.equals("P"))
                sql1+= " and a.BIYONG        != 0";

			sql1 += " ) a";

            //sql1 += " group by a.grcode, b.grcodenm, a.gyear, a.grseq, a.course, a.cyear, a.courseseq, a.coursenm,         ";
			sql1 += " group by a.grcode, a.grcodenm, a.gyear, a.grseq, a.course, a.cyear, a.courseseq, a.coursenm,         ";
            sql1 += "          a.subj, a.year, a.subjseq, a.subjseqgr,a.subjnm, a.isonoff, a.edustart, a.eduend , a.isbelongcourse, a.subjcnt    ";

			sql1 += sql_add4;

            if(v_orderColumn.equals("")) {
                //sql1 += " order by a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseqgr,a.subjseq ";
                sql1 += " order by a.subj, a.year,a.subjseq desc";
            } else {
                sql1 += " order by a.course, " + v_orderColumn + v_orderType;
            }

            ls1 = connMgr.executeQuery(sql1);

            //System.out.println("gong----------sql --> " + sql1);

            if(!ss_notpageing.equals("ALL"))
                row=10000;

            //����¡
            ls1.setPageSize(row);             //  �������� row ������ �����Ѵ�
            ls1.setCurrentPage(v_pageno);                    //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls1.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls1.getTotalCount();    //     ��ü row ���� ��ȯ�Ѵ�

            while (ls1.next()) {

				 dbox = ls1.getDataBox();
				 
				 //����¡
	            dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
	            dbox.put("d_totalpage", new Integer(total_page_count));
	            dbox.put("d_rowcount", new Integer(row));

		         list1.add(dbox);
            }
            ls1.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }


    /**
    ���������� ���� ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ���������� ����  ����Ʈ
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectListGong(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        
        ListSet ls        			= null;
        ArrayList list    			= null;
        
        StringBuffer sql        	= new StringBuffer();
        
        DataBox dbox	 			= null;

        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        String  v_orderColumn   = box.getString("p_orderColumn");               //������ �÷���
        String  v_orderType     = box.getString("p_orderType");                 //������ ����

        try {
        	
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append(" SELECT SEQ, TYPES, ADDATE, TITLE , USERID, ADCONTENT, C.CODENM \n");
			sql.append(" FROM TZ_GONG G \n");
			sql.append(" 	JOIN (SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN='0008')  C ON C.CODE = G.TYPES \n");
            sql.append("  WHERE SUBJ    = " + StringManager.makeSQL(v_subj) + " \n");
            sql.append("    AND YEAR    = " + StringManager.makeSQL(v_year) + " \n");
            sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");

            if(v_orderColumn.equals("")) {
                sql.append(" ORDER BY SEQ DESC \n");
            } else {
                sql.append(" ORDER BY " + v_orderColumn + v_orderType + " \n");
            }

            ls = connMgr.executeQuery( sql.toString() );

            while (ls.next()) {
				 dbox=ls.getDataBox();
                 list.add(dbox);
            }
        } catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return list;
    }

    /**
    ���������� ���� ȭ�� ����Ʈ ����¡ ��� ����
    @param box          receive from the form object and session
    @return ArrayList   ���������� ����  ����Ʈ
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectListPageGong(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls        = null;
        ArrayList list    = null;
        StringBuffer sql  = new StringBuffer();
        StringBuffer count_sql  = new StringBuffer();
        DataBox dbox	  = null;
        
        int    v_pageno    = box.getInt("p_pageno");
 	    if (v_pageno == 0) v_pageno = 1;

        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        String v_searchtext = box.getString("p_searchtext");
        String v_search     = box.getString("p_search");
        
        String  v_orderColumn   = box.getString("p_orderColumn");               //������ �÷���
        String  v_orderType     = box.getString("p_orderType");                 //������ ����

        try {
        	
            connMgr = new DBConnectionManager();

            list = new ArrayList();

//            sql  = " Select seq , types, addate, title , g.userid, adcontent, c.codenm, m.name,g.cnt,";
//            sql += "          (select count(*) from TZ_GONGFILE where subj = g.subj and year = g.year and subjseq = g.subjseq and seq = g.seq) upfilecnt";
//			sql += " From TZ_GONG g, (Select code, codenm From tz_code Where gubun='0008') c, TZ_MEMBER m";
//			sql += "  where c.code = g.types ";
//            sql += "  and subj     = " + StringManager.makeSQL(v_subj);
//            sql += "  and year     = " + StringManager.makeSQL(v_year);
//            sql += "  and subjseq  = " + StringManager.makeSQL(v_subjseq);
//            sql += "  and g.userid = m.userid ";
            
            sql.append(" SELECT SEQ, TYPES, ADDATE, TITLE , USERID, ADCONTENT, C.CODENM \n");
			sql.append(" FROM TZ_GONG G \n");
			sql.append(" 	JOIN (SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN='0008')  C ON C.CODE = G.TYPES \n");
            sql.append("  WHERE SUBJ    = " + StringManager.makeSQL(v_subj) + " \n");
            sql.append("    AND YEAR    = " + StringManager.makeSQL(v_year) + " \n");
            sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");
            
            if ( !v_searchtext.equals("")) {                //    �˻�� ������
                if (v_search.equals("name")) {              //    �̸����� �˻��Ҷ�
					sql.append(" and m.name like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+")");
                }
                else if (v_search.equals("title")) {        //    �������� �˻��Ҷ�
					sql.append(" and g.title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+")");
                }
                else if (v_search.equals("content")) {     //    �������� �˻��Ҷ�
					sql.append(" and g.adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%"));            //   Oracle 9i ��
                }
            }

            if(v_orderColumn.equals("")) {
                sql.append(" order by seq desc");
            } else {
                sql.append(" order by " + v_orderColumn + v_orderType);
            }

            ls = connMgr.executeQuery(sql.toString());

            
			count_sql.append(" Select count(*) ");
//			count_sql += " From TZ_GONG g, (Select code, codenm From tz_code Where gubun='0008') c, TZ_MEMBER m";
//			count_sql += "  where c.code = g.types ";
//			count_sql += "  and subj     = " + StringManager.makeSQL(v_subj);
//			count_sql += "  and year     = " + StringManager.makeSQL(v_year);
//			count_sql += "  and subjseq  = " + StringManager.makeSQL(v_subjseq);
//			count_sql += "  and g.userid = m.userid ";
			count_sql.append(" FROM TZ_GONG G \n");
			count_sql.append(" 	JOIN (SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN='0008')  C ON C.CODE = G.TYPES \n");
			count_sql.append("  WHERE SUBJ    = " + StringManager.makeSQL(v_subj) + " \n");
			count_sql.append("    AND YEAR    = " + StringManager.makeSQL(v_year) + " \n");
			count_sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");
            if ( !v_searchtext.equals("")) {                //    �˻�� ������
                if (v_search.equals("name")) {              //    �̸����� �˻��Ҷ�
                	count_sql.append(" and m.name like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+")");
                }
                else if (v_search.equals("title")) {        //    �������� �˻��Ҷ�
                	count_sql.append(" and g.title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+")");
                }
                else if (v_search.equals("content")) {     //    �������� �˻��Ҷ�
                	count_sql.append(" and g.adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%"));            //   Oracle 9i ��
                }
            }

			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql.toString()); //     ��ü row ���� ��ȯ�Ѵ�
			
            ls.setPageSize(row);                       		//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage();    		//     ��ü ������ ���� ��ȯ�Ѵ�
            
            while (ls.next()) {
				 dbox=ls.getDataBox();
				 
                 dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                 dbox.put("d_totalpage", new Integer(totalpagecount));
                 dbox.put("d_rowcount", new Integer(row));

                 list.add(dbox);
            }
        } catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return list;
    }


    /**
    ���������� ��ü ���� ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ���������� ����  ����Ʈ
    */
    @SuppressWarnings("unchecked")
	public ArrayList selectListGongAll_H(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        
        ListSet ls        			= null;
        ArrayList list    			= null;
        
        StringBuffer sql        	= new StringBuffer();
        
        SubjGongData data 			= null;

        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        String v_typesgubun = CodeConfigBean.getConfigValue("noticeCategory");    // ī�װ���������Ʈ

        try {
        	
            connMgr = new DBConnectionManager();
            
            list = new ArrayList();

			sql.append(" SELECT * FROM ( SELECT ROWNUM RNUM,  A.SEQ SEQ , A.TYPES TYPES, B.CODENM TYPESNM, A.ADDATE ADDATE, \n");
            sql.append("          A.TITLE TITLE, A.USERID USERID, A.ADCONTENT ADCONTENT, CNT, \n");
            sql.append("          (SELECT NAME FROM TZ_MEMBER WHERE USERID = A.USERID) NAME, \n");
            sql.append("          (SELECT COUNT(*) FROM TZ_GONGFILE WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR AND SUBJSEQ = A.SUBJSEQ AND SEQ = A.SEQ) UPFILECNT \n");
            sql.append("          FROM TZ_GONG A, TZ_CODE B \n");
            sql.append("          WHERE A.TYPES   = B.CODE \n");
            sql.append("            AND B.GUBUN   = " + StringManager.makeSQL(v_typesgubun) + " \n");
            sql.append("            AND B.LEVELS  = 1 AND A.TYPES = 'H' \n");
            sql.append("            AND A.SUBJ    = " + StringManager.makeSQL(v_subj) + " \n");
            sql.append("            AND A.YEAR    = " + StringManager.makeSQL(v_year) + " \n");
            sql.append("            AND A.SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");
            sql.append("          ORDER BY A.SEQ DESC ) WHERE RNUM < 3 \n");

            ls = connMgr.executeQuery( sql.toString() );

            while (ls.next()) {
                data = new SubjGongData();

                data.setSeq(ls.getInt("seq"));
                data.setTypes(ls.getString("types"));
                data.setTypesnm(ls.getString("typesnm"));
                data.setAddate(ls.getString("addate"));
                data.setTitle(ls.getString("title"));
                data.setUserid(ls.getString("userid"));
                data.setAdcontent(ls.getCharacterStream("adcontent"));
                data.setName(ls.getString("name"));
                data.setUpfilecnt(ls.getInt("upfilecnt"));
                data.setCnt(ls.getInt("cnt"));

                list.add(data);
            }
        } catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return list;
    }


    /**
     * ���������� ���� �󼼺���
     * @param box          receive from the form object and session
     * @return data        SubjGongData ��������Ÿ��
     * @throws Exception
     */
    public SubjGongData selectViewGong(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls        = null;
//        ArrayList list    = null;
        String sql        = "";
        SubjGongData data = null;

        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        int    v_seq     = box.getInt("p_seq");

//        String v_typesgubun =
        	CodeConfigBean.getConfigValue("noticeCategory");    // ī�װ���������Ʈ

        try {
            connMgr = new DBConnectionManager();

            //sql  = " select a.seq seq , a.types types, b.codenm typesnm, a.addate addate, ";
            //sql += "        a.title title, a.userid userid, a.adcontent adcontent         ";
            //sql += "   from TZ_GONG a, TZ_CODE b                                          ";
            //sql += "  where a.types   = b.code                                            ";
            //sql += "    and b.gubun   = " + StringManager.makeSQL(v_typesgubun);
            //sql += "    and b.levels  = 1                                                 ";
            //sql += "    and a.subj    = " + StringManager.makeSQL(v_subj);
            //sql += "    and a.year    = " + StringManager.makeSQL(v_year);
            //sql += "    and a.subjseq = " + StringManager.makeSQL(v_subjseq);
            //sql += "    and a.seq     = " + v_seq;

			sql  = " select seq , types, addate, title, userid, adcontent         ";
            sql += "   from TZ_GONG                                          ";
            sql += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql += "    and seq     = " + v_seq;


            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new SubjGongData();

                data.setSeq(ls.getInt("seq"));
                data.setTypes(ls.getString("types"));
                data.setAddate(ls.getString("addate"));
                data.setTitle(ls.getString("title"));
                data.setUserid(ls.getString("userid"));
                data.setAdcontent(ls.getCharacterStream("adcontent"));
                //data.setAdcontent(ls.getString("adcontent"));
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
        return data;

    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select,  ÷������ �߰�, ��ȸ��
     * @param box          receive from the form object and session
     * @return ArrayList   ��ȸ�� ������
     * @throws Exception
     */
     public DataBox selectViewGongNew(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          ListSet ls = null;
          String sql = "";
          DataBox dbox = null;
          String v_upcnt = "Y";

          String v_subj    = box.getString("p_subj");
          String v_year    = box.getString("p_year");
          String v_subjseq = box.getString("p_subjseq");
          int    v_seq     = box.getInt("p_seq");
          int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

          Vector realfileVector = new Vector();
          Vector savefileVector = new Vector();
          Vector fileseqVector  = new Vector();

          try {
              connMgr = new DBConnectionManager();

  			  sql  = " select a.seq , a.types, a.addate, a.title, a.userid, a.adcontent,         ";
			  sql += "        b.fileseq fileseq, b.realfile realfile, b.savefile savefile,";
			  sql += "        (select name from TZ_MEMBER where userid = a.userid) name, cnt, ";
			  sql += "        (select count(realfile) from TZ_GONGFILE where subj = a.subj and year = a.year and subjseq = a.subjseq and seq = a.seq) upfilecnt ";
              sql += "   from TZ_GONG a, TZ_GONGFILE b  ";
              sql += "  where a.subj = b.subj(+)";
              sql += "    and a.year = b.year(+)";
              sql += "    and a.subjseq = b.subjseq(+)";
              sql += "    and a.seq = b.seq(+)";
              sql += "    and a.subj    = " + StringManager.makeSQL(v_subj);
              sql += "    and a.year    = " + StringManager.makeSQL(v_year);
              sql += "    and a.subjseq = " + StringManager.makeSQL(v_subjseq);
              sql += "    and a.seq     = " + v_seq;
              
              ls = connMgr.executeQuery(sql);

              for (int i = 0; ls.next(); i++) {

                  dbox = ls.getDataBox();
                  realfileVector.addElement(dbox.getString("d_realfile"));
                  savefileVector.addElement(dbox.getString("d_savefile"));
                  fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
  				
              }

              if (realfileVector  != null) dbox.put("d_realfile", realfileVector);
              if (savefileVector  != null) dbox.put("d_savefile", savefileVector);			
              if (fileseqVector   != null) dbox.put("d_fileseq", fileseqVector);

              if (!v_upcnt.equals("N")){
                  connMgr.executeUpdate("update TZ_GONG set cnt = cnt + 1 where subj = " + StringManager.makeSQL(v_subj) + " and year    = " + StringManager.makeSQL(v_year) + " and subjseq = " + StringManager.makeSQL(v_subjseq) + " and seq = " + v_seq);
              }
          }
          catch (Exception ex) {
              ErrorManager.getErrorStackTrace(ex, box, sql);
              throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
          }
          finally {
              if(ls != null) {try {ls.close();} catch(Exception e){}}
              if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          return dbox;
      }

     /**
      * ���õ� �ڷ�� �Խù� �󼼳��� select,  ÷������ �߰�, ��ȸ��
      * @param box          receive from the form object and session
      * @return ArrayList   ��ȸ�� ������
      * @throws Exception
      */
      public DataBox selectViewGongNewAdmin(RequestBox box) throws Exception {
           DBConnectionManager connMgr = null;
           ListSet ls = null;
           String sql = "";
           DataBox dbox = null;
           String v_upcnt = "Y";

           String v_subj    = box.getString("p_subj");
           String v_year    = box.getString("p_year");
           String v_subjseq = box.getString("p_subjseq");
           int    v_seq     = box.getInt("p_seq");
           int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

           Vector realfileVector = new Vector();
           Vector savefileVector = new Vector();
           Vector fileseqVector  = new Vector();

           try {
               connMgr = new DBConnectionManager();

   			  sql  = " select a.seq , a.types, a.addate, a.title, a.userid, a.adcontent,         ";
 			  sql += "        b.fileseq fileseq, b.realfile realfile, b.savefile savefile,";
 			  sql += "        (select codenm from TZ_CODE where gubun = '0008' and code = a.types and levels = 1) typesnm,";
 			  sql += "        (select name from TZ_MEMBER where userid = a.userid) name, cnt, ";
 			  sql += "        (select count(realfile) from TZ_GONGFILE where subj = a.subj and year = a.year and subjseq = a.subjseq and seq = a.seq) upfilecnt ";
               sql += "   from TZ_GONG a, TZ_GONGFILE b  ";
               sql += "  where a.subj = b.subj(+)";
               sql += "    and a.year = b.year(+)";
               sql += "    and a.subjseq = b.subjseq(+)";
               sql += "    and a.seq = b.seq(+)";
               sql += "    and a.subj    = " + StringManager.makeSQL(v_subj);
               sql += "    and a.year    = " + StringManager.makeSQL(v_year);
               sql += "    and a.subjseq = " + StringManager.makeSQL(v_subjseq);
               sql += "    and a.seq     = " + v_seq;
               
               ls = connMgr.executeQuery(sql);

               for (int i = 0; ls.next(); i++) {

                   dbox = ls.getDataBox();
                   realfileVector.addElement(dbox.getString("d_realfile"));
                   savefileVector.addElement(dbox.getString("d_savefile"));
                   fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
   				
               }

               if (realfileVector  != null) dbox.put("d_realfile", realfileVector);
               if (savefileVector  != null) dbox.put("d_savefile", savefileVector);			
               if (fileseqVector   != null) dbox.put("d_fileseq", fileseqVector);

               if (!v_upcnt.equals("N")){
                   connMgr.executeUpdate("update TZ_GONG set cnt = cnt + 1 where subj = " + StringManager.makeSQL(v_subj) + " and year    = " + StringManager.makeSQL(v_year) + " and subjseq = " + StringManager.makeSQL(v_subjseq) + " and seq = " + v_seq);
               }
           }
           catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
               throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
           }
           finally {
               if(ls != null) {try {ls.close();} catch(Exception e){}}
               if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
           }
           return dbox;
       }

    /**
    �������к� ���� ������������
    @param box            receive from the form object and session
    @return SubjGongData  ���� ������������
    */
   public SubjGongData selectGongSample(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        SubjGongData  data = null;
        String v_types   = "";
        String v_default = "";

        try {
            v_default = CodeConfigBean.getCodeDefault("noticeCategory","",1);
            v_types   = box.getStringDefault("p_types",v_default);

            connMgr = new DBConnectionManager();


            sql  = " select title, adcontent from TZ_GONGSAMPLE        ";
            sql += "  where types = " + StringManager.makeSQL(v_types);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data=new SubjGongData();
                data.setTitle(ls.getString("title"));
                data.setAdcontent(ls.getCharacterStream("adcontent"));

                //data.setAdcontent(ls.getString("adcontent"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return data;
    }

      /**
    ��ü ������ �������� ����� ���� ��������
    @param box            receive from the form object and session
    @return SubjGongData  ���� ������������
    */
   public void selectGongJiTitleAndContent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String resultData="";
        String v_types   = "";
        String v_default = "";
        String [] box_Split_Coma=box.getString("p_checks").split(",");
        String [] box_Split_slush=box_Split_Coma[0].split("/");
        String firstValue_subj=box_Split_slush[0];
        String firstValue_year=box_Split_slush[1];
        String firstValue_subjseq=box_Split_slush[2];

        try {
            v_default = CodeConfigBean.getCodeDefault("noticeCategory","",1);
            v_types   = box.getStringDefault("p_types",v_default);

            connMgr = new DBConnectionManager();

            sql  = " select title, adcontent from tz_gong a       ";
            sql += "  where types = " + StringManager.makeSQL(v_types);
            sql += "  and subj = " + StringManager.makeSQL(firstValue_subj);
            sql += "  and year = " + StringManager.makeSQL(firstValue_year);
            sql += "  and subjseq = " + StringManager.makeSQL(firstValue_subjseq);
            sql += "  and seq=(select max(seq) from tz_gong z where z.types=a.types and z.subj=a.subj and z.year=a.year and z.subjseq=a.subjseq)";
            
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                box.put("p_title",ls.getString("title"));
                box.put("adcontent",ls.getCharacterStream("adcontent"));
//                resultData=ls.getString("title")+"�Ρ�";
//                resultData+=ls.getCharacterStream("adcontent");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
    }

    /**
    ���� �������� ���
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertGongAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        PreparedStatement pstmt = null;
        SubjGongData  data = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk = 1;
        int isOk2 = 1;
        int isOk_check = 1;

//        String  ss_grcode     = box.getString("s_grcode");        //�����׷�
//        String  ss_gyear      = box.getString("s_gyear");         //�⵵
//        String  ss_grseq      = box.getStringDefault("s_grseq","ALL");         //��������
//        String  ss_uclass    = box.getStringDefault("s_upperclass","ALL");    //�����з�(��)
//        String  ss_mclass    = box.getStringDefault("s_middleclass","ALL");   //�����з�(��)
//        String  ss_lclass    = box.getStringDefault("s_lowerclass","ALL");    //�����з�(��)
//        String  ss_subjcourse = box.getString("s_subjcourse");    //����&�ڽ�
//        String  ss_subjseq    = box.getString("s_subjseq");       //���� ����
//        String  ss_isclosed   = box.getString("s_isclosed");      //���࿩��

        String v_types         = box.getString("p_types");
        String v_title     = box.getString("p_title");        // ������ ����
        String v_adcontent = box.getString("p_adcontent");    // ������ ����

        String s_userid        = box.getSession("userid");

        String v_checks        = box.getString("p_checks");       //üũ�� �����ڵ�+"/"+�⵵+"/"+��������

        String v_userid    = "";
        String v_subj      = "";
        String v_year      = "";
        String v_subjseq   = "";
        int    v_seq       = 0;

        //String v_title     = "";    // ������ ����
        //String v_adcontent = "";    // ������ ����


//        String e_subj;

        try {
        	
  		   /*********************************************************************************************/
  		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
  		   try {
  			   v_adcontent =(String) NamoMime.setNamoContent(v_adcontent);
  		   } catch(Exception e) {
  			   System.out.println(e.toString());
  			   return 0;
  		   }
  		   /*********************************************************************************************/       

            /*********************************************************************************************/
			// ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
			//ConfigSet conf = new ConfigSet();
			//SmeNamoMime namo = new SmeNamoMime(v_adcontent); // ��ü����
			//boolean result = namo.parse(); // ���� �Ľ� ����
			//if ( !result ) { // �Ľ� ���н�
			//	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
			//	return 0;
			//}
			//if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
			//	String v_server = conf.getProperty("kocca.url.value");
			//	String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
			//	String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
			//	String prefix =  "subjgongAdmin" + v_seq;         // ���ϸ� ���ξ�
			//	result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
			//}
			//if ( !result ) { // �������� ���н�
			//	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
			//	return 0;
			//}
			//v_adcontent = namo.getContent(); // ���� ����Ʈ ���
			/*********************************************************************************************/


           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

           isOk = 1;

           //p_checks[]�� ����
           StringTokenizer st = new StringTokenizer(v_checks, ",");

           //while (ls1.next()) {
           while (st.hasMoreElements()) {
                String token = st.nextToken();

                StringTokenizer st2 = new StringTokenizer(token, "/");

                while(st2.hasMoreElements()) {
                    v_subj   = st2.nextToken();
                    v_year   = st2.nextToken();
                    v_subjseq = st2.nextToken();
                    break;
                }
                data = getChangeText(v_subj, v_year, v_subjseq, v_title, v_adcontent );
                if (data != null) {
                    v_title     = data.getTitle();
                    v_adcontent = data.getAdcontent();
                    v_userid    = data.getUserid();

                }

                sql2  = " select max(seq) from TZ_GONG  ";
                sql2 += "  where subj    = " + StringManager.makeSQL(v_subj);
                sql2 += "    and year    = " + StringManager.makeSQL(v_year);
                sql2 += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
                ls2 = connMgr.executeQuery(sql2);
                if (ls2.next()) {
                    v_seq = ls2.getInt(1) + 1;
                } else {
                    v_seq = 1;
                }
                ls2.close();



                sql3 =  " insert into TZ_GONG(subj, year, subjseq, seq, types, addate, title, userid, adcontent, luserid, ldate)                    ";
//                sql3 += "             values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ? , ?, empty_clob(), ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                sql3 += "             values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ? , ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

int index = 1;
                pstmt = connMgr.prepareStatement(sql3);

                pstmt.setString(index++, v_subj);
                pstmt.setString(index++, v_year);
                pstmt.setString(index++, v_subjseq);
                pstmt.setInt   (index++, v_seq);
                pstmt.setString(index++, v_types);
                pstmt.setString(index++, v_title);
                pstmt.setString(index++, v_userid);
pstmt.setCharacterStream(index++, new StringReader(v_adcontent), v_adcontent.length());
//				pstmt.setString(index++, v_adcontent);
                pstmt.setString(index++, s_userid);

                isOk_check =  pstmt.executeUpdate();
                if (isOk_check == 0) isOk = 0;
				/* 05.11.15 �̳���
		   */
                sql2 = "select adcontent from TZ_GONG";
                sql2 += "  where subj    = " + StringManager.makeSQL(v_subj);
                sql2 += "    and year    = " + StringManager.makeSQL(v_year);
                sql2 += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
                sql2 += "    and seq = " + v_seq;

//                connMgr.setOracleCLOB(sql2, v_adcontent);       //      (��Ÿ ���� ���)

                // ���Ͼ��ε�
                isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, box);

                
           }

           if ( isOk > 0) connMgr.commit();
           else connMgr.rollback();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); } catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
          if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk*isOk2;
    }


    /**
    ���������� �������� ���
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertGong(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        SubjGongData  data = null;
        String sql  = "";
        String sql1 = "";
        int isOk = 1;
        int isOk2 = 1;

        String v_subj      = box.getString("p_subj");
        String v_year      = box.getString("p_year");
        String v_subjseq   = box.getString("p_subjseq");

        String v_types     = box.getString("p_types");
        String v_title    = box.getString("p_title");

        String v_content   = box.getString("p_adcontent");
        String s_userid    = box.getSession("userid");

        //String v_title="";
//        String v_adcontent= "";

        String v_userid    = "";
        int    v_seq       = 0;

        try {

 		   /*********************************************************************************************/
 		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
 		   try {
 			   v_content =(String) NamoMime.setNamoContent(v_content);
 		   } catch(Exception e) {
 			   System.out.println(e.toString());
 			   return 0;
 		   }
 		   /*********************************************************************************************/       

        	/*********************************************************************************************/
		   // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
		   //ConfigSet conf = new ConfigSet();
		   //SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü����
		   //boolean result = namo.parse(); // ���� �Ľ� ����
           //
		   //if ( !result ) { // �Ľ� ���н�
		   //	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
		   //	return 0;
		   //}
           //
		   //if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
		   //	String v_server = conf.getProperty("kocca.url.value");
		   //	String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
		   //	String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
		   //	String prefix =  "SubjGong" + v_seq;         // ���ϸ� ���ξ�
		   //	result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
		   //}
           //
		   //if ( !result ) { // �������� ���н�
		   //	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
		   //	return 0;
		   //}
           //
           //
		   //v_content = namo.getContent(); // ���� ����Ʈ ���
		   /*********************************************************************************************/

            data = getChangeText(v_subj, v_year, v_subjseq, v_title, v_content );
            if (data != null) {
                v_title     = data.getTitle();
                v_content = data.getAdcontent();
                v_userid    = data.getUserid();
            }

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql  = " select max(seq) from TZ_GONG  ";
            sql += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql1 =  " insert into TZ_GONG(subj, year, subjseq, seq, types, addate, title, userid, adcontent, luserid, ldate)                     ";
//          sql1 += "             values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, empty_clob(), ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
          sql1 += "             values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

int index = 1;
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setInt   (index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, v_title);
            pstmt.setString(index++, v_userid);
pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//            pstmt.setString(index++, v_content);
            pstmt.setString(index++, s_userid);
            isOk = pstmt.executeUpdate();

            sql1 = "select adcontent from tz_gong";
            sql1 += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql1 += "    and year    = " + StringManager.makeSQL(v_year);
            sql1 += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql1 += "    and seq = " + v_seq;
//            System.out.println("v_adcontentv_adcontentv_adcontent="+v_content);
////            System.out.println("sql1sql1sql1="+sql1);
//          connMgr.setOracleCLOB(sql1, v_content);       //      (ORACLE 9i ����)

            // ���Ͼ��ε�
            isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, box);

            
            if ( isOk > 0) connMgr.commit();
           else connMgr.rollback();

        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
          if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


    /**
     ���������� �������� �����Ͽ� �����Ҷ�
     @param box      receive from the form object and session
     @return isOk    1:update success,0:update fail
     */
      public int updateGong(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
         PreparedStatement pstmt = null;
        int isOk = 1;
        String sql="";

        String v_subj      = box.getString("p_subj");
        int    v_upfilecnt = box.getInt("p_upfilecnt");    //  ������ ������ִ� ���ϼ�

        String v_checks    = box.get("p_checks");
        String [] v_subjs = v_checks.split(",");
        
		Vector v_savefile     =	new	Vector();
		Vector v_filesequence =	new	Vector();

		for(int	i =	0; i < v_upfilecnt;	i++) {
			if(	!box.getString("p_fileseq" + i).equals(""))	{
				v_savefile.addElement(box.getString("p_savefile" + i));			//		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
				v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
			}
		}

        try {
              connMgr = new DBConnectionManager();
              connMgr.setAutoCommit(false);
            if(!v_subj.equals(""))
            {
                isOk=updateGongFor(box,v_savefile,v_filesequence,null,connMgr,pstmt);
            }
            else
            {
                for(int i=0;i<v_subjs.length;i++)
                {
                    String [] v_subj_year_subjseq=v_subjs[i].split("/");
                    isOk=updateGongFor(box,v_savefile,v_filesequence,v_subj_year_subjseq,connMgr,pstmt);
                }
            }
            if ( isOk > 0) connMgr.commit();
            else connMgr.rollback();
         }
         catch(Exception ex) {
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

     private int updateGongFor(RequestBox box,Vector v_savefile,Vector v_filesequence,String [] v_subj_year_subjseq,DBConnectionManager connMgr,PreparedStatement pstmt) throws Exception
     {
        String v_subj      = "";
        String v_year      = "";
        String v_subjseq   = "";
        int    v_seq       = 0;
        String v_types     = "";
        String v_title     = "";
        String v_adcontent = "";

         if(v_subj_year_subjseq!=null && v_subj_year_subjseq.length>0)
        {
            v_subj      = v_subj_year_subjseq[0];
            v_year      = v_subj_year_subjseq[1];
            v_subjseq   = v_subj_year_subjseq[2];
            v_seq       = 0;
         }
         else
         {
            v_subj      = box.getString("p_subj");
            v_year      = box.getString("p_year");
            v_subjseq   = box.getString("p_subjseq");
            v_seq       = box.getInt("p_seq");
         }

        v_types     = box.getString("p_types");
        v_title     = box.getString("p_title");
        v_adcontent = box.getString("p_adcontent");
        String s_userid    = box.getSession("userid");
        String v_userid    = "";
        SubjGongData  data = null;
        String sql = "";
        int isOk = 1;
        int isOk2 = 1;
        int isOk3 = 1;

         /*********************************************************************************************/
  		   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
  		   try {
  			   v_adcontent =(String) NamoMime.setNamoContent(v_adcontent);
  		   } catch(Exception e) {
  			   System.out.println(e.toString());
  			   return 0;
  		   }
  		   /*********************************************************************************************/

		   /*********************************************************************************************/
		   // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
		   //ConfigSet conf = new ConfigSet();
		   //SmeNamoMime namo = new SmeNamoMime(v_adcontent); // ��ü����
		   //boolean result = namo.parse(); // ���� �Ľ� ����
           //
		   //if ( !result ) { // �Ľ� ���н�
		   //	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
		   //	return 0;
		   //}
           //
		   //if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ�
		   //	String v_server = conf.getProperty("kocca.url.value");
		   //	String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
		   //	String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
		   //	String prefix =  "HomeNotice" + v_seq;         // ���ϸ� ���ξ�
		   //	result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ����
		   //}
           //
		   //if ( !result ) { // �������� ���н�
		   //	System.out.println( namo.getDebugMsg() ); // ����� �޽��� ���
		   //	return 0;
		   //}
           //
           //
		   //v_adcontent = namo.getContent(); // ���� ����Ʈ ���
		   /*********************************************************************************************/

            data = getChangeText(v_subj, v_year, v_subjseq, v_title, v_adcontent );
            if (data != null) {
                v_title     = data.getTitle();
                v_adcontent = data.getAdcontent();
                v_userid    = data.getUserid();
            }

            sql  = " update TZ_GONG a set types = ?, title = ?, adcontent= ?, userid = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";

         if(v_subj_year_subjseq!=null && v_subj_year_subjseq.length>0)
            sql += "  where subj = ? and year = ? and subjseq = ? and seq=(select max(seq) from tz_gong z where z.types=a.types and z.subj=a.subj and z.year=a.year and z.subjseq=a.subjseq) and types=?";
         else
            sql += "  where subj = ? and year = ? and subjseq = ? and seq = ?                                                                    ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(index++,  v_types);
            pstmt.setString(index++,  v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_adcontent), v_adcontent.length());
//            pstmt.setString(index++,  v_adcontent);
            pstmt.setString(index++,  v_userid);
            pstmt.setString(index++,  s_userid);
            pstmt.setString(index++,  v_subj);
            pstmt.setString(index++,  v_year);
            pstmt.setString(index++,  v_subjseq);
             if(v_subj_year_subjseq!=null && v_subj_year_subjseq.length>0)
                 pstmt.setString(index++,  box.getString("p_oldtypes"));
             else
                pstmt.setInt   (index++,  v_seq);

             isOk = pstmt.executeUpdate();

            sql = "select adcontent from tz_gong";
            sql += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
            sql += "    and seq = " + v_seq;

//            connMgr.setOracleCLOB(sql, v_adcontent);       //      (ORACLE 9i ����)

            isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, box);       //      ����÷���ߴٸ� ����table��  insert

            isOk3 = this.deleteUpFile(connMgr, box, v_filesequence);        //     ������ ������ �ִٸ� ����table���� ����

            if(isOk > 0 && isOk2 > 0 && isOk3 > 0) {

                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile);         //   DB ���� ���ó���� �Ϸ�Ǹ� �ش� ÷������ ����
                }
            }
        return isOk;
     }

     /**
     ���������� �������� �����Ҷ�
     @param box      receive from the form object and session
     @return isOk    1:delete success,0:delete fail
     */
     public int deleteGong(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         PreparedStatement pstmt = null;
         String sql = "";
         int isOk = 0;

        String v_subj      = box.getString("p_subj");
        String v_year      = box.getString("p_year");
        String v_subjseq   = box.getString("p_subjseq");
        int    v_seq       = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql  = " delete from TZ_GONG                                      ";
            sql += "  where subj = ? and year = ? and subjseq = ? and seq = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1,  v_subj);
            pstmt.setString(2,  v_year);
            pstmt.setString(3,  v_subjseq);
            pstmt.setInt(4,  v_seq);
            isOk = pstmt.executeUpdate();

             Log.info.println(this, box, "delete TZ_GONG where subj" + v_subj + " and year " +v_year+ " and subjseq" + v_subjseq+ " and seq" + v_seq);
         }
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, box, sql);
             throw new Exception("sql = " + sql + "\r\n"  + ex.getMessage());
         }
         finally {
             if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
     }

     /**
     ���������� �������� ���� �����Ҷ�
     @param box      receive from the form object and session
     @return isOk    1:delete success,0:delete fail
     */
     @SuppressWarnings("unchecked")
	public int deleteAllGong(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         PreparedStatement pstmt = null;
         String sql = "";
         int isOk = 0;
         /*
        String v_subj      = box.getString("p_subj");
        String v_year      = box.getString("p_year");
        String v_subjseq   = box.getString("p_subjseq");
        int    v_seq       = box.getInt("p_seq");
        */

        Vector v_check     = new Vector();
        v_check  = box.getVector("p_checks");
        Enumeration em   = v_check.elements();

        StringTokenizer st1 = null;
        String v_checks     = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            while(em.hasMoreElements()){
            	v_checks    = (String)em.nextElement();
            	st1 = new StringTokenizer(v_checks,"/");
            	while (st1.hasMoreElements()) {

            		String v_subj	= (String)st1.nextToken();
            		String v_year	= (String)st1.nextToken();
            		String v_subjseq	= (String)st1.nextToken();

            		sql  = " delete from TZ_GONG                             ";
            		sql += "  where subj = ? and year = ? and subjseq = ?    ";
            		pstmt = connMgr.prepareStatement(sql);
            		pstmt.setString(1,  v_subj);
            		pstmt.setString(2,  v_year);
            		pstmt.setString(3,  v_subjseq);

            		isOk = pstmt.executeUpdate();
            		break;

            	}
            }

            if (isOk > 0) {
           	 connMgr.commit();
            } else {
           	 connMgr.rollback();
            }
         }
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, box, sql);
             throw new Exception("sql = " + sql + "\r\n"  + ex.getMessage());
         }
         finally {
             if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return isOk;
     }

    /**
     * ������ ��ġ(���� �� ����)
     * @param subj       �����ڵ�
     * @param year       �����⵵
     * @param subjseq    ��������
     * @param title      ��������
     * @param adcontent  ��������
     * @return data      SubjGongData (��������Ÿ��- ������ ����,Ÿ��Ʋ ����)
     * @throws Exception
     */
    public SubjGongData getChangeText (String subj, String year, String subjseq, String title, String adcontent) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        SubjGongData  data = null;

        String v_subjnm     = "";   // ������
        String v_edudates   = "";   // �����ϼ�
        String v_eduperiod  = "";   // �����Ⱓ
        String v_name       = "";   // ��� �̸�
        String v_comptel    = "";   // ��� ��ȭ
        String v_email      = "";   // ��� E-mail
        String v_wstep      = "";   // ����ġ(������)
        String v_wmtest     = "";   // ����ġ(�߰���)
        String v_wftest     = "";   // ����ġ(������)
        String v_whtest     = "";   // ����ġ(������)
        String v_wreport    = "";   // ����ġ(������Ʈ)
        String v_wact       = "";   // ����ġ(��Ƽ��Ƽ)
        String v_gradscore  = "";   // �����������
        String v_gradstep   = "";   // �������������
        String v_gradexam   = "";   // ������ؽ���
        String v_gradreport = "";   // ������ظ���Ʈ
        String v_point      = "";   // �̼�����
        String v_edustart   = "";   // ������
        String v_eduend     = "";   // ������

        String v_userid     = "";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select a.subjnm subjnm, ";
            sql += "        a.point point, ";
            sql += "        a.gradscore gradscore, ";
            sql += "        a.gradstep gradstep, ";
            sql += "        a.gradexam gradexam, ";
            sql += "        a.gradreport gradreport,";
            sql += "        a.wstep wstep, ";
            sql += "        a.wmtest wmtest,";
            sql += "        a.wftest wftest, ";
            sql += "        a.whtest whtest, ";
            sql += "        a.wreport wreport, ";
            sql += "        a.wact wact, ";
            sql += "        a.edustart edustart,     ";
            sql += "        a.eduend eduend, ";
            sql += "        b.muserid userid, ";
            sql += "        (select name from tz_member where userid=b.muserid) name, ";
            sql += "        (select email from tz_member where userid=b.muserid) email, ";
            sql += "        (select comptel from tz_member where userid=b.muserid) comptel ";
            sql += " from   tz_subjseq a, TZ_SUBJ b ";
            sql += " where  a.subj = b.subj  ";
            sql += "    and a.subj    = " + StringManager.makeSQL(subj);
            sql += "    and a.year    = " + StringManager.makeSQL(year);
            sql += "    and a.subjseq = " + StringManager.makeSQL(subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                v_subjnm     = ls.getString("subjnm");
                v_point      = String.valueOf(ls.getInt("point"));
                v_gradscore  = ls.getString("gradscore");
                v_gradstep   = ls.getString("gradstep");
                v_gradexam   = ls.getString("gradexam");
                v_gradreport = ls.getString("gradreport");
                v_wstep      = String.valueOf(ls.getInt("wstep"));
                v_wmtest     = String.valueOf(ls.getInt("wmtest"));
                v_wftest     = String.valueOf(ls.getInt("wftest"));
                v_whtest     = String.valueOf(ls.getInt("whtest"));
                v_wreport    = String.valueOf(ls.getInt("wreport"));
                v_wact       = String.valueOf(ls.getInt("wact"));

                v_edustart   = ls.getString("edustart");
                v_eduend     = ls.getString("eduend");
                v_edudates   = String.valueOf(FormatDate.datediff("date", v_edustart, v_eduend));
                v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                v_eduend     = FormatDate.getFormatDate(v_eduend, "yyyy/MM/dd");
                v_eduperiod  = v_edustart + " ~ " + v_eduend;

                v_userid     = ls.getString("userid");

                v_name       = ls.getString("name");
                v_comptel    = ls.getString("comptel");
                v_email      = ls.getString("email");

                // Ÿ��Ʋ ����
                title = StringManager.replace(title, "v_subjnm", v_subjnm);
                title = StringManager.replace(title, "v_point", v_point);
                title = StringManager.replace(title, "v_gradscore", v_gradscore);
                title = StringManager.replace(title, "v_gradstep", v_gradstep);
                title = StringManager.replace(title, "v_gradexam", v_gradexam);
                title = StringManager.replace(title, "v_gradreport", v_gradreport);
                title = StringManager.replace(title, "v_wstep", v_wstep);
                title = StringManager.replace(title, "v_wmtest", v_wmtest);
                title = StringManager.replace(title, "v_wftest", v_wftest);
                title = StringManager.replace(title, "v_whtest", v_whtest);
                title = StringManager.replace(title, "v_wreport", v_wreport);
                title = StringManager.replace(title, "v_wact", v_wact);
                title = StringManager.replace(title, "v_edustart", v_edustart);
                title = StringManager.replace(title, "v_eduend", v_eduend);
                title = StringManager.replace(title, "v_edudates", v_edudates);
                title = StringManager.replace(title, "v_eduperiod", v_eduperiod);
                title = StringManager.replace(title, "v_name", v_name);
                title = StringManager.replace(title, "v_comptel", v_comptel);
                title = StringManager.replace(title, "v_email", v_email);

                // ���� ����
                adcontent = StringManager.replace(adcontent, "v_subjnm", v_subjnm);
                adcontent = StringManager.replace(adcontent, "v_point", v_point);
                adcontent = StringManager.replace(adcontent, "v_gradscore", v_gradscore);
                adcontent = StringManager.replace(adcontent, "v_gradstep", v_gradstep);
                adcontent = StringManager.replace(adcontent, "v_gradexam", v_gradexam);
                adcontent = StringManager.replace(adcontent, "v_gradreport", v_gradreport);
                adcontent = StringManager.replace(adcontent, "v_wstep", v_wstep);
                adcontent = StringManager.replace(adcontent, "v_wmtest", v_wmtest);
                adcontent = StringManager.replace(adcontent, "v_wftest", v_wftest);
                adcontent = StringManager.replace(adcontent, "v_whtest", v_whtest);
                adcontent = StringManager.replace(adcontent, "v_wreport", v_wreport);
                adcontent = StringManager.replace(adcontent, "v_wact", v_wact);
                adcontent = StringManager.replace(adcontent, "v_edustart", v_edustart);
                adcontent = StringManager.replace(adcontent, "v_eduend", v_eduend);
                adcontent = StringManager.replace(adcontent, "v_edudates", v_edudates);
                adcontent = StringManager.replace(adcontent, "v_eduperiod", v_eduperiod);
                adcontent = StringManager.replace(adcontent, "v_name", v_name);
                adcontent = StringManager.replace(adcontent, "v_comptel", v_comptel);
                adcontent = StringManager.replace(adcontent, "v_email", v_email);


                data = new SubjGongData();
                data.setTitle(title);
                data.setAdcontent(adcontent);
                data.setUserid(v_userid);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return data;
    }


    /**
    ���������� (  ����������=(������� �ϼ�*100)/��ü�н��Ⱓ�ϼ� )
    @param box            receive from the form object and session
    @return String       ����������
    */
   public String getPromotion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        
        ListSet ls 					= null;
        
        StringBuffer sql 			= new StringBuffer();

        float percent     = (float)0.0;
        String result     = "0";
        String v_today    = "";
        String v_edustart = "";
        String v_eduend   = "";
        int    v_nowday   = 0;
        int    v_allday   = 0;

        String v_subj      = box.getString("p_subj");
        String v_year      = box.getString("p_year");
        String v_subjseq   = box.getString("p_subjseq");
        
        try {
        	
            connMgr = new DBConnectionManager();

            sql.append(" SELECT /* SubGongAdminBean.getPromotion:1499 */ \n");
            sql.append("	TO_CHAR(SYSDATE,'YYYYMMDD') TODAY, SUBSTRING(EDUSTART,1,8) EDUSTART, SUBSTRING(EDUEND,1,8) EDUEND \n");
            sql.append("   FROM TZ_SUBJSEQ \n");
            sql.append("  WHERE SUBJ = " + StringManager.makeSQL(v_subj) + " \n");
            sql.append("    AND YEAR = " + StringManager.makeSQL(v_year) + " \n");
            sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");

            ls = connMgr.executeQuery( sql.toString() );

            if (ls.next()) {
				v_today    = ls.getString("today");
				v_edustart = ls.getString("edustart");
				v_eduend   = ls.getString("eduend");
				v_nowday = FormatDate.datediff("date",v_edustart,v_today);
				v_allday = FormatDate.datediff("date",v_edustart,v_eduend);

                if(v_allday != 0) {
                    percent = (float)((v_nowday * 100) / (float)v_allday);
                    if (percent <= 0.0) {
                    	percent=0;
                    } else if(percent > 100.0) {
                    	percent=100;
                    }
                    
                    result =  new DecimalFormat("0.00").format(percent);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return result;
    }

    /**
    ���������
    @param box            receive from the form object and session
    @return String       ���������
    */
   public String getAverage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        float percent     = (float)0.0;
        String result     = "0";

        String v_subj      = box.getString("p_subj");
        String v_year      = box.getString("p_year");
        String v_subjseq   = box.getString("p_subjseq");
        try {
            connMgr = new DBConnectionManager();

            sql  = " select isnull(avg(tstep),0.0) tstep  ";
            sql += "   from tz_student                 ";
            sql += "  where subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                percent    = ls.getFloat("tstep");
                result =  String.valueOf(percent);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }
    /**
    �ڱ�������
    @param box            receive from the form object and session
    @return String       �ڱ�������
    */
   public String getProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls 					= null;
        StringBuffer sql 			= new StringBuffer();

        float percent		= (float)0.0;
        String result		= "0";

        String v_subj      	= box.getString("p_subj");//System.out.println(v_subj);
        String v_year      	= box.getString("p_year");//System.out.println(v_year);
        String v_subjseq   	= box.getString("p_subjseq");//System.out.println(v_subjseq);
        String v_ispreview 	= box.getString("p_ispreview");//System.out.println("v_ispreview==========>>>>"+v_ispreview);
        String s_userid 	= box.getSession("userid");//System.out.println(s_userid);

        try {
            // �̸����Ⱑ �ƴҰ�츸 ����
            if (!v_ispreview.equals("Y") && !v_year.equals("2000")) {
                connMgr = new DBConnectionManager();

            	CalcUtil.getInstance().calc_score(connMgr,CalcUtil.ALL,v_subj,v_year,v_subjseq,s_userid);

                sql.append(" SELECT TSTEP \n");
                sql.append("   FROM TZ_STUDENT \n");
                sql.append("  WHERE SUBJ    = " + StringManager.makeSQL(v_subj) + " \n");
                sql.append("    AND YEAR    = " + StringManager.makeSQL(v_year) + " \n");
                sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n");
                sql.append("    AND USERID = " + StringManager.makeSQL(s_userid) + " \n");

                ls = connMgr.executeQuery( sql.toString() );

                if (ls.next()) {
                    percent    = ls.getFloat("tstep");
                    result =  String.valueOf(percent);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        
        return result;
    }
   
   /**
    * ����н�����
    * @param box
    * @return
    * @throws Exception
    */
   public String getIsalways(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;
	   ListSet ls 					= null;
	   StringBuffer sql 			= new StringBuffer();

	   String result		= "N";

	   String v_subj      	= box.getString("p_subj");//System.out.println(v_subj);
	   String v_year      	= box.getString("p_year");//System.out.println(v_year);
	   String v_subjseq   	= box.getString("p_subjseq");//System.out.println(v_subjseq);

	   try {
			   connMgr = new DBConnectionManager();
			   System.out.println("============================ getIsalways");
			   
			   sql.append("    select      												");
			   sql.append("\n        (select isalways from tz_grseq      				");
			   sql.append("\n      where grcode = a.grcode     							");
			   sql.append("\n      and gyear = a.gyear      							");
			   sql.append("\n      and grseq = a.grseq      							");
			   sql.append("\n      ) as isalways     									");
			   sql.append("\n    from tz_subjseq a     									");
			   sql.append("\n    where a.subj = " + StringManager.makeSQL(v_subj) + "   ");
			   sql.append("\n    and a.year = " + StringManager.makeSQL(v_year) + "     ");
			   sql.append("\n    and a.subjseq = " + StringManager.makeSQL(v_subjseq) +"");

			   ls = connMgr.executeQuery( sql.toString() );

			   if (ls.next()) {
				   result = ls.getString("isalways");
			   }
	   } catch (Exception ex) {
		   ErrorManager.getErrorStackTrace(ex, box, sql.toString());
		   throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
	   } finally {
		   if(ls != null) {try {ls.close();} catch(Exception e){}}
		   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	   }

	   return result;
   }
   
   /**
    * ���Ῡ��
    * @param box
    * @return
    * @throws Exception
    */
   public String getIsgrad(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;
	   ListSet ls 					= null;
	   StringBuffer sql 			= new StringBuffer();
	   
	   String result		= "N";
	   
	   String v_subj      	= box.getString("p_subj");//System.out.println(v_subj);
	   String v_year      	= box.getString("p_year");//System.out.println(v_year);
	   String v_subjseq   	= box.getString("p_subjseq");//System.out.println(v_subjseq);
	   String s_userid 	= box.getSession("userid");//System.out.println(s_userid);
	   
	   try {
		   connMgr = new DBConnectionManager();
		   
		   
		   sql.append("    select      												");
		   sql.append("\n      isgraduated     										");
		   sql.append("\n    from tz_student a     									");
		   sql.append("\n    where a.subj = " + StringManager.makeSQL(v_subj) + "   ");
		   sql.append("\n    and a.year = " + StringManager.makeSQL(v_year) + "     ");
		   sql.append("\n    and a.subjseq = " + StringManager.makeSQL(v_subjseq) +"");
		   sql.append("\n    and a.userid = " + StringManager.makeSQL(s_userid) +"");
		   
		   ls = connMgr.executeQuery( sql.toString() );
		   
		   if (ls.next()) {
			   result = ls.getString("isgraduated");
		   }
	   } catch (Exception ex) {
		   ErrorManager.getErrorStackTrace(ex, box, sql.toString());
		   throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
	   } finally {
		   if(ls != null) {try {ls.close();} catch(Exception e){}}
		   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	   }
	   
	   return result;
   }
   
   /**
    * �н�������
    * @param box
    * @return
    * @throws Exception
    */
   public String getStartEdu(RequestBox box) throws Exception {
	   DBConnectionManager connMgr = null;
	   ListSet ls 					= null;
	   StringBuffer sql 			= new StringBuffer();
	   
	   String result		= "N";
	   
	   String v_subj      	= box.getString("p_subj");//System.out.println(v_subj);
	   String v_year      	= box.getString("p_year");//System.out.println(v_year);
	   String v_subjseq   	= box.getString("p_subjseq");//System.out.println(v_subjseq);
	   String s_userid 	= box.getSession("userid");//System.out.println(s_userid);
	   
	   try {
		   connMgr = new DBConnectionManager();
		   
		   
		   sql.append("    select      												");
		   sql.append("\n      edustart     										");
		   sql.append("\n    from tz_student a     									");
		   sql.append("\n    where a.subj = " + StringManager.makeSQL(v_subj) + "   ");
		   sql.append("\n    and a.year = " + StringManager.makeSQL(v_year) + "     ");
		   sql.append("\n    and a.subjseq = " + StringManager.makeSQL(v_subjseq) +"");
		   sql.append("\n    and a.userid = " + StringManager.makeSQL(s_userid) +"");
		   
		   ls = connMgr.executeQuery( sql.toString() );
		   
		   if (ls.next()) {
			   result = ls.getString("edustart");
		   }
	   } catch (Exception ex) {
		   ErrorManager.getErrorStackTrace(ex, box, sql.toString());
		   throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
	   } finally {
		   if(ls != null) {try {ls.close();} catch(Exception e){}}
		   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	   }
	   
	   return result;
   }
   


    /**
    �ڱ�������
    @param box            receive from the form object and session
    @return String       �ڱ�������
    */
   public String getProgress2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        float percent     = (float)0.0;
        String result     = "0";

        String v_subj      = box.getString("p_subj");//System.out.println(v_subj);
        String v_year      = box.getString("p_year");//System.out.println(v_year);
        String v_subjseq   = box.getString("p_subjseq");//System.out.println(v_subjseq);
        String v_ispreview = box.getString("p_ispreview");//System.out.println("v_ispreview==========>>>>"+v_ispreview);
        String s_userid = box.getSession("userid");//System.out.println(s_userid);

        //String s_userid = box.getString("p_userid");
        try {
            // �̸����Ⱑ �ƴҰ�츸 ����
            if (!v_ispreview.equals("Y") && !v_year.equals("2000")) {
                connMgr = new DBConnectionManager();

                sql  = " select tstep                ";
                sql += "   from tz_student           ";
                sql += "  where subj    = " + StringManager.makeSQL(v_subj);
                sql += "    and year    = " + StringManager.makeSQL(v_year);
                sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);
                sql += "    and userid = " + StringManager.makeSQL(s_userid);

                ls = connMgr.executeQuery(sql);

                if (ls.next()) {
                    percent    = ls.getFloat("tstep");
                    result =  String.valueOf(percent);
                    System.out.println("percent==========="+percent);
                    System.out.println("result============"+result);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }

    /**
     * ��뺸��ȳ� ����
     * @param box          receive from the form object and session
     * @return data        SubjGongData ��������Ÿ��
     * @throws Exception
     */
    public SubjGongData selectViewGoyong(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls        = null;
//        ArrayList list    = null;
        String sql        = "";
        SubjGongData data = null;


        String v_subj    = box.getString("p_subj");
        String v_year    = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
//        int    v_seq     = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            //sql  = " select a.subjnm, ";
            //sql += "        a.studentlimit, ";
            //sql += "        a.framecnt, ";
            //sql += "        a.edutimes, ";
            //sql += "        a.musertel, ";
            //sql += "        b.name, ";
            //sql += "        (select substr(comp,0,4) from tz_grcode where grcode=c.grcode) comp";
            //sql += "   from tz_subj a, ";
            //sql += "        tz_member b, ";
            //sql += "        tz_subjseq c";
            //sql += "  where a.muserid=b.userid ";
            //sql += "    and a.subj=c.subj ";
            //sql += "    and a.subj="+StringManager.makeSQL(v_subj);
            //sql += "    and c.subjseq="+StringManager.makeSQL(v_subjseq);
            //sql += "    and c.year="+StringManager.makeSQL(v_year);
            System.out.println("sql="+sql);
            sql  = " select a.subjnm, ";
            sql += "        a.studentlimit, ";
            sql += "        a.framecnt, ";
            sql += "        a.edutimes, ";
            sql += "        a.musertel, ";
            sql += "        (select substring(comp,1,4) from tz_grcode where grcode=c.grcode) comp";
            sql += "   from tz_subj a, ";
            sql += "        tz_subjseq c";
            sql += "  where a.subj=c.subj ";
            sql += "    and a.subj="+StringManager.makeSQL(v_subj);
            sql += "    and c.subjseq="+StringManager.makeSQL(v_subjseq);
            sql += "    and c.year="+StringManager.makeSQL(v_year);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new SubjGongData();

                data.setSubjnm(ls.getString("subjnm"));
                data.setStudentLimit(ls.getInt("studentlimit"));
                data.setFrameCnt(ls.getInt("framecnt"));
                data.setEduTimes(ls.getInt("edutimes"));
                data.setMuserTel(ls.getString("musertel"));
                data.setComp(ls.getString("comp"));

                  //data.setAdcontent(ls.getString("adcontent"));

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
        return data;

    }


    /**
    �н� ���� ���� ��ȸ
    @param box      receive from the form object and session
    @return         1: isOk , 0: isFail
    */
    @SuppressWarnings("unchecked")
	public int allowStudy(RequestBox box, String subj, String year, String subjseq) throws Exception {
		box.put("p_subj", subj);
		box.put("p_year", year);
		box.put("p_subjseq", subjseq);
		return allowStudy(box);
	}

    /**
    �н� ���� ���� ��ȸ
    @param box      receive from the form object and session
    @return         1: isOk , 0: isFail
    */
    public int allowStudy(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        ListSet ls4 = null;
        ListSet ls5 = null;

        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";

        int v_cnt = 0;
        boolean isexp = false;

        String v_subj       = box.getString("p_subj");
        String v_year       = box.getString("p_year");
        String v_subjseq    = box.getString("p_subjseq");
        String s_userid     = box.getSession("userid");

        String v_upperclass = "", v_comp = "";//, v_day = "", v_code = "";

        try{
            connMgr = new DBConnectionManager();

            sql1  = "  select comp from tz_member where userid=" + StringManager.makeSQL(s_userid);
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) {
                v_comp = ls1.getString("comp");
            }

            // ��з� ��ȸ -> ����, ���� �����ϱ� ����
            sql2  = " select scupperclass from vz_scsubjseq ";
            sql2 += "   where scsubj=" + StringManager.makeSQL(v_subj);
            sql2 += "     and scyear=" + StringManager.makeSQL(v_year);
            sql2 += "     and scsubjseq=" + StringManager.makeSQL(v_subjseq);
            ls2 = connMgr.executeQuery(sql2);
            if (ls2.next()){
                v_upperclass = ls2.getString("scupperclass");
            }


            // �н����� ������üũ -> �н�����
            sql3  = " select count(*) cnt from TZ_STUDYCONTROLEXP ";
            sql3 += "   where company = " + StringManager.makeSQL(v_comp);
            sql3 += "     and gubun   = (select matchcode from tz_classfymatch where upperclass = " + StringManager.makeSQL(v_upperclass) + ")" ;
            sql3 += "     and userid=" + StringManager.makeSQL(s_userid);
            ls3 = connMgr.executeQuery(sql3);
            if (ls3.next()){
                if (ls3.getInt("cnt") > 0) isexp = true;
            }

            // �����ڰ� �ƴҰ��
            if (!isexp) {
                // ���� ���� üũ -> �н��Ұ�
                sql4  = " select count(*) cnt from TZ_STUDYCONTROL ";
                sql4 += "   where company = " + StringManager.makeSQL(v_comp);
                sql4 += "     and gubun   = (select matchcode from tz_classfymatch where upperclass = " + StringManager.makeSQL(v_upperclass) + ")" ;
                sql4 += "     and startdt <= to_char(sysdate,'yyyyMMdd') ";
                sql4 += "     and enddt >= to_char(sysdate,'yyyyMMdd')   ";
                sql4 += "     and isholiday = 'Y'                        ";
                sql4 += "     and isuse='Y'                              ";
                ls4 = connMgr.executeQuery(sql4);
                if (ls4.next()){
                    v_cnt = ls4.getInt("cnt");
                }

                if (v_cnt == 0) {
                    // �н��ð����� üũ
                    sql5  = " select count(seq) cnt ";
                    sql5 += "   from TZ_STUDYCONTROL ";
                    sql5 += "  where company=" + StringManager.makeSQL(v_comp);
                    sql5 += "    and gubun= (select matchcode from tz_classfymatch where upperclass = " + StringManager.makeSQL(v_upperclass) + ")" ;
                    sql5 += "    and startdt <= to_char(sysdate,'yyyyMMdd') ";
                    sql5 += "    and enddt >= to_char(sysdate,'yyyyMMdd') ";
                    sql5 += "    and starttime <= to_char(sysdate,'HH24mi') ";
                    sql5 += "    and endtime >= to_char(sysdate,'HH24mi') ";
					sql5 += "    and ( ";
					sql5 += " select ";
					sql5 += " 	case to_char(sysdate,'dy') ";
					sql5 += " 		When '��' Then '1' ";
					sql5 += " 		When 'ȭ' Then '2' ";
					sql5 += " 		When '��' Then '3' ";
					sql5 += " 		When '��' Then '4' ";
					sql5 += " 		When '��' Then '5' ";
					sql5 += " 		When '��' Then '6' ";
					sql5 += " 		When '��' Then '7' ";
					sql5 += " 	End FROM DUAL) between day1 and day2 ";
                    sql5 += "    and isuse='Y' ";

                    ls5 = connMgr.executeQuery(sql5);

                    if (ls5.next()) {
                        v_cnt = ls5.getInt("cnt");
                    }
                }
            }
        } catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception ( "sql = " + sql1 + "\r\n " + ex.getMessage());
        }
        finally {
         if (ls1 != null) { try { ls1.close(); } catch (Exception e) {} }
         if (ls2 != null) { try { ls2.close(); } catch (Exception e) {} }
         if (ls3 != null) { try { ls3.close(); } catch (Exception e) {} }
         if (ls4 != null) { try { ls4.close(); } catch (Exception e) {} }
         if (ls5 != null) { try { ls5.close(); } catch (Exception e) {} }
         if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }

        return v_cnt;
    }

    /**
     * ���ο� �ڷ����� ���
     * @param connMgr  DB Connection Manager
     * @param p_seq    �Խù� �Ϸù�ȣ
     * @param box      receive from the form object and session
     * @return isOk    1:insert success,0:insert fail
     * @throws Exception
     */
      public int insertUpFile(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, int p_seq, RequestBox   box) throws Exception {
         ListSet ls = null;
         PreparedStatement pstmt2 = null;
         String sql = "";
         String sql2 = "";
         int isOk2 = 1;

         //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------

         String [] v_realFileName = new String [FILE_LIMIT];
         String [] v_newFileName = new String [FILE_LIMIT];

         for(int i = 0; i < FILE_LIMIT; i++) {
             v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
             v_newFileName [i] = box.getNewFileName(FILE_TYPE + (i+1));
         }
         //----------------------------------------------------------------------------------------------------------------------------

         String s_userid = box.getSession("userid");

         try {
              //----------------------   �ڷ� ��ȣ �����´� ----------------------------
             sql  = "select isnull(max(fileseq), 0) from TZ_GONGFILE ";
             sql += " where subj = " + StringManager.makeSQL(p_subj);
             sql += "   and year = " + p_year;
             sql += "   and subjseq = " + StringManager.makeSQL(p_subjseq);
             sql += "   and seq =   " + p_seq;
             ls = connMgr.executeQuery(sql);
             ls.next();
             int v_fileseq = ls.getInt(1) + 1;
             ls.close();
             //------------------------------------------------------------------------------------

             //////////////////////////////////   ���� table �� �Է�  ///////////////////////////////////////////////////////////////////
             sql2 =  "insert into TZ_GONGFILE(subj, year, subjseq, seq, fileseq, realfile, savefile, luserid, ldate)";
             sql2 += " values (?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

             pstmt2 = connMgr.prepareStatement(sql2);

             for(int i = 0; i < FILE_LIMIT; i++) {
                 if( !v_realFileName [i].equals("")) {       //      ���� ���ε� �Ǵ� ���ϸ� üũ�ؼ� db�� �Է��Ѵ�
 					 pstmt2.setString(1, p_subj);                	
                     pstmt2.setString(2, p_year);                	
                     pstmt2.setString(3, p_subjseq);
                     pstmt2.setInt(4, p_seq);
                     pstmt2.setInt(5, v_fileseq);
                     pstmt2.setString(6, v_realFileName [i]);
                     pstmt2.setString(7, v_newFileName [i]);
                     pstmt2.setString(8, s_userid);

                     isOk2 = pstmt2.executeUpdate();
                     v_fileseq++;
                 }
             }
         }
         catch (Exception ex) {
             FileManager.deleteFile(v_newFileName, FILE_LIMIT);      //  �Ϲ�����, ÷������ ������ ����..
             ErrorManager.getErrorStackTrace(ex, box, sql2);
             throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
         }
         finally {
             if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
         }
         return isOk2;
     }

      /**
       * ���õ� �ڷ����� DB���� ����
       * @param connMgr           DB Connection Manager
       * @param box               receive from the form object and session
       * @param p_filesequence    ���� ���� ����
       * @return
       * @throws Exception
       */
      public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
          PreparedStatement pstmt3 = null;
          String sql3 = "";
          int isOk3 = 1;

          String v_subj    = box.getString("p_subj");
          String v_year    = box.getString("p_year");
          String v_subjseq = box.getString("p_subjseq");
          int v_seq     = box.getInt("p_seq");

          try {
              sql3 = "delete from TZ_GONGFILE where subj =? and year = ? and subjseq = ? and seq =? and fileseq = ?";

              pstmt3 = connMgr.prepareStatement(sql3);


              for(int i = 0; i < p_filesequence.size(); i++) {
                  int v_fileseq = Integer.parseInt((String)p_filesequence.elementAt(i));

                  pstmt3.setString(1, v_subj);                	
                  pstmt3.setString(2, v_year);                	
                  pstmt3.setString(3, v_subjseq);
                  pstmt3.setInt(4, v_seq);
                  pstmt3.setInt(5, v_fileseq);

                  isOk3 = pstmt3.executeUpdate();
              }
          }
          catch (Exception ex) {
                 ErrorManager.getErrorStackTrace(ex, box, sql3);
              throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
          }
          finally {
              if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
          }
          return isOk3;
      }

     @SuppressWarnings("unchecked")
	public ArrayList selectCompleteBasic(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ArrayList list1     = null;
        String sql1         = "";

		DataBox dbox = null;

        try {

            sql1="SELECT \n";
            sql1+="GRADSCORE,GRADSTEP,GRADREPORT,GRADEXAM,GRADFTEST,GRADHTEST,\n";
            sql1+="WSTEP,WMTEST,WHTEST,WFTEST,WREPORT,WACT,WETC1\n";
            sql1+="  FROM   tz_subjseq \n";
            sql1+="where subj="+ SQLString.Format(box.getString("p_subj"));
            sql1+="   and year="+SQLString.Format(box.getString("p_year"));
            sql1+="   and subjseq="+SQLString.Format(box.getString("p_subjseq"));
            
            connMgr = new DBConnectionManager();
            ls1 = connMgr.executeQuery(sql1);

    	    //����¡
            ls1.setPageSize(65000);             //  �������� row ������ �����Ѵ�
            ls1.setCurrentPage(1);                    //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls1.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls1.getTotalCount();    //     ��ü row ���� ��ȯ�Ѵ�

            list1=new ArrayList();
            while (ls1.next()) {

				 dbox = ls1.getDataBox();

				 //����¡
	            dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
	            dbox.put("d_totalpage", new Integer(total_page_count));
	            dbox.put("d_rowcount", new Integer(row));

		         list1.add(dbox);
            }
            ls1.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }
}