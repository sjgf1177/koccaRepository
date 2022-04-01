//**********************************************************
//1. ��      ��: QnA ����
//2. ���α׷���: QnaAdminBean.java
//3. ��      ��: QnA ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0 QnA
//6. ��      ��: �̿��� 2005. 7.  7
//7. ��      ��: �̳��� 05.11.24 _ ls -> dbox �� ���
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FormMail;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeData;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QnaAdminBean {
    private ConfigSet config;
    private int row;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; //	  �������� ���õ� ����÷�� ����

    public QnaAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getFILE_LIMIT() {
        return FILE_LIMIT;
    }

    public QnaAdminBean(String type) {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
            this.v_type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �ڷ�� ���̺��ȣ
     * 
     * @param box receive from the form object and session
     * @return int �ڷ�� ���̺��ȣ
     * @throws Exception
     */
    public int selectTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_type = box.getStringDefault("p_type", "");
        String v_grcode = box.getStringDefault("p_grcode", "0000000");
        String v_comp = box.getStringDefault("p_comp", "0000000000");
        String v_subj = box.getStringDefault("p_subj", "0000000000");
        String v_year = box.getStringDefault("p_year", "0000");
        String v_subjseq = box.getStringDefault("p_subjseq", "0000");

        try {
            connMgr = new DBConnectionManager();

            sql = " select tabseq from TZ_BDS      ";
            sql += "  where type    = " + StringManager.makeSQL(v_type);
            sql += "    and grcode  = " + StringManager.makeSQL(v_grcode);
            sql += "    and comp    = " + StringManager.makeSQL(v_comp);
            sql += "    and subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("tabseq");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return result;
    }

    /**
     * QnA ī�װ� ���̺� ����Ʈ
     * 
     * @param name ����Ʈ�ڽ���
     * @param selected ���ð�
     * @param event �̺�Ʈ��
     * @param allcheck ��ü����
     * @return
     * @throws Exception
     */
    public static String homepageGetQnaCategoryTable(String category_code) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        String sql = "";
        String sql1 = "";
        String result = "";
        int count = 0;
        // ResultSet rs1 = null;

        // Statement stmt1 = null;
        CodeData data = null;
        int i = 1;

        result = "\n   <table width='675' border='0' cellpadding='0' cellspacing='0'>";
        try {
            connMgr = new DBConnectionManager();

            //Ȩ������ QnA�϶�
            sql1 = "select count(*) cnt from tz_code where gubun='0046' and levels='1' ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt");
            }
            ls1.close();

            sql = " select code, codenm from tz_code  where gubun  = '0046' and levels = '1'";
            sql += " order by code asc                        ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                if (i % 4 == 1) {
                    result += "\n <tr> ";
                }
                result += "\n     <td width='180' class='tbl_cfaq'><input type='radio'  name='p_categorycd' value='" + data.getCode() + "' ";
                if (data.getCode().equals(category_code))
                    result += " checked";
                result += ">" + data.getCodenm() + "</td> ";

                if (i % 4 == 0 && i != (count)) {
                    result += "\n  </tr> ";
                }

                if (i != 4 && i % 4 == 0) {
                    result += "\n </tr> ";
                }
                i++;
            }
            int mod_cnt = (count) % 5;

            if (mod_cnt != 0) {
                for (int j = 0; j < (5 - mod_cnt); j++) {
                    result += "<td width='180' class='tbl_cfaq'>&nbsp;</td>";
                }
            }

            result += "</tr>";

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "\n   </table>";

        return result;

    }

    /**
     * QNAȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
        StringBuffer sql = new StringBuffer();
        String sql1 = "";

        PreparedStatement pstmt = null;

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_categorycd = box.getStringDefault("p_categorycd", "00");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String ss_grcode = box.getString("s_grcode");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //������ ����
        int v_pageno = box.getInt("p_pageno");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
        String s_userid = box.getSession("userid");

        String v_type = box.getStringDefault("p_type", "PQ");//��ü,Ȩ������,��ڿ���,������������ ������ �� �ִ� Ű
        int v_tabseq = 0;
        int v_tablen = 0;
        String v_tabseqstr = "";

        if (v_order.equals("indate")) {
            v_order = "a.indate";
        }

        try {
            connMgr = new DBConnectionManager();
            if (!v_type.equals("ALL")) {
                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type);
            } else {
                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE IN ('PQ','MM','KB')";
            }
            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                v_tabseq = ls.getInt(1);
                v_tabseqstr += v_tabseq + ",";
            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            v_tablen = v_tabseqstr.length() - 1;
            v_tabseqstr = StringManager.substring(v_tabseqstr, 0, v_tablen);

            list = new ArrayList<DataBox>();

            if (v_type.equals("ALL")) {
                sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                sql.append(" FROM    ( SELECT  *  FROM  TZ_HOMEQNA  WHERE  TYPES = '0' ) A \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                sql.append(" WHERE   A.INUSERID = B.USERID \n");
                sql.append("   AND   A.GRCODE = B.GRCODE \n");
                sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                sql.append("   AND   C.TYPE IN('PQ','CU','BU','OO','MM','KB') \n");
                sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = E.SEQ(+) \n");

                if (!ss_grcode.equals("ALL")) {
                    sql.append("   AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append("   AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append("   AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������        		
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�        		
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�        			
                        sql.append("   AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�        			
                        sql.append("   AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�        			
                        sql.append("   AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    }
                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                sql.append(" UNION ALL \n");
                sql.append(" SELECT  DISTINCT A.SEQ, A.KIND TYPES, A.TITLE , A.SUBJ, A.YEAR, C.SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE,A.INUSERID, '' UPFILE,  A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,  A.SUBJSEQ,   A.CATEGORYCD, 0 CNT \n");
                sql.append("         , '' TYPE, B.NAME, C.SCSUBJ,C.SUBJSEQGR, 0 FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0) REPLYSTATE, A.OKYN1 \n");
                sql.append(" FROM    ( SELECT  * FROM  TZ_QNA WHERE  KIND = '0' ) A \n");
                sql.append("         , ( SELECT  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ, COUNT(*) REPLYSTATE FROM  TZ_QNA WHERE  KIND > '0' GROUP BY  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ ) D \n");
                sql.append("         , TZ_MEMBER B, VZ_SCSUBJSEQ C \n");
                sql.append(" WHERE A.INUSERID = B.USERID(+) \n");
                sql.append(" AND A.SUBJ = C.SUBJ(+) \n");
                sql.append(" AND A.YEAR=C.YEAR(+) \n");
                sql.append(" AND A.SUBJSEQ = C.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJSEQ = D.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJ = D.SUBJ(+) \n");
                sql.append(" AND A.YEAR = D.YEAR(+) \n");
                sql.append(" AND A.LESSON = D.LESSON(+) \n");
                sql.append(" AND A.SEQ   = D.SEQ(+) \n");

                if (!ss_grcode.equals("ALL")) {
                    sql.append(" AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append(" AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append(" AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������        		
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�        		
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�        			
                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�        			
                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�        			
                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    }
                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {
                    sql.append(" ORDER BY REPLYSTATE, INDATE DESC, SEQ DESC,TYPES ASC ");
                } else {
                    sql.append(" ORDER BY " + v_order + v_orderType);
                }
            } else {
                if (v_type.equals("KB") && v_gadmin.equals("P")) {
                    sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                    sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                    sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                    sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                    sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                    sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                    sql.append(" FROM    (SELECT  *  FROM  TZ_HOMEQNA WHERE  TYPES = '0' ) A \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                    sql.append("         , TZ_MEMBER B, TZ_BDS C, TZ_SUBJMAN D \n");
                    sql.append(" WHERE   A.INUSERID = B.USERID \n");
                    sql.append("   AND   A.GRCODE = B.GRCODE \n");
                    sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                    sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                    sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                    sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = E.SEQ(+) \n");
                    sql.append("   AND   D.USERID    = '" + s_userid + "' \n");
                    sql.append("   AND   D.SUBJ    = A.CATEGORYCD \n");

                } else {

                    sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                    sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                    sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                    sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                    sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                    sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                    sql.append(" FROM    (SELECT  *  FROM  TZ_HOMEQNA WHERE  TYPES = '0' ) A \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                    sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                    sql.append(" WHERE   A.INUSERID = B.USERID \n");
                    sql.append("   AND   A.GRCODE = B.GRCODE \n");
                    sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                    sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                    sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                    sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = E.SEQ(+) \n");
                }

                if (v_type.equals("ALL")) {
                    sql.append("    AND C.TYPE IN('PQ','CU','BU','OO', 'MM')  \n");
                } else if (v_type.equals("MM")) {
                    sql.append("    AND C.TYPE IN('CU','BU','OO', 'MM') \n");
                } else {
                    sql.append("    AND C.TYPE = '" + v_type + "' \n");
                }

                if (!ss_grcode.equals("ALL")) {
                    sql.append("    AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                sql.append("    AND A.TABSEQ IN( " + v_tabseqstr + " ) \n");

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append("  AND A.INDATE BETWEEN '" + v_startdate + "' AND '" + v_enddate + "999999' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append(" AND A.INDATE > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������            	
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�				
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�					
                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�					
                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��					
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�					
                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��					
                    }
                }

                if (v_gadmin.equals("F") || v_gadmin.equals("P")) {

                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {
                    //				sql.append(" ORDER BY REPLYSTATE, A.INDATE DESC, A.SEQ DESC, A.TYPES ASC ");				
                    sql.append(" ORDER BY A.INDATE DESC, A.SEQ DESC, A.TYPES ASC ");
                } else {
                    sql.append(" ORDER BY " + v_order + v_orderType);
                }
            }
System.out.println(sql.toString());
            pstmt = connMgr.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ls = new ListSet(pstmt);

            ls.setPageSize(row); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.

            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * QNA ���������� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     **/
    public ArrayList<DataBox> selectListQnaCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        // ������ : 05.11.16 ������ : �̳���_ totalcount �ϱ����� ��������
        String sql = "";
        String head_sql = "";
        StringBuffer body_sql = new StringBuffer();
        String group_sql = "";
        String order_sql = "";

        DataBox dbox = null;

        // String v_type = box.getStringDefault("p_type", "PQ");
        String p_grcode = box.getString("s_grcode");
        String p_gyear = box.getStringDefault("s_gyear", "ALL");
        String p_grseq = box.getStringDefault("s_grseq", "ALL");
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //�����з�
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        String ss_subjseq = box.getString("s_subjseq");
        // String ss_company = box.getString("s_company");

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_categorycd = box.getStringDefault("p_categorycd", "00");
        int v_pageno = box.getInt("p_pageno");
        // String v_action = box.getString("p_action");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        // String v_replystate = box.getString("p_replystate");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //������ ����

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql += " SELECT      A.TITLE, A.GRCODE, B.NAME, A.ISOPEN, A.LUSERID, A.SUBJSEQ, A.LDATE, A.SUBJ, A.YEAR, A.SEQ     \n";
            head_sql += "             , C.SCSUBJNM, A.CATEGORYCD, A.INDATE, B.NAME, C.GRCODE, C.SCSUBJ, C.SUBJSEQGR, D.REPLYSTATE, A.OKYN1   \n";

            body_sql.append(" FROM        (                                                                     \n");
            body_sql.append("               SELECT * FROM TZ_QNA WHERE KIND = '0'                               \n");
            body_sql.append("             ) A                                                                   \n");
            body_sql.append("             , TZ_MEMBER B, VZ_SCSUBJSEQ C                                         \n");
            body_sql.append("             , (                                                                   \n");
            body_sql.append("               SELECT    SUBJ, YEAR, SUBJSEQ, LESSON, SEQ, COUNT(*) REPLYSTATE     \n");
            body_sql.append("                 FROM    TZ_QNA                                                    \n");
            body_sql.append("                WHERE    KIND > '0'                                                \n");
            body_sql.append("               GROUP BY  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ                          \n");
            body_sql.append("             ) D                                                                   \n");
            body_sql.append(" WHERE       A.INUSERID  = B.USERID(+)                                             \n");
            body_sql.append("   AND       A.SUBJ      = C.SUBJ(+)                                               \n");
            body_sql.append("   AND       A.YEAR      = C.YEAR(+)                                               \n");
            body_sql.append("   AND       A.SUBJSEQ   = C.SUBJSEQ(+)                                            \n");
            body_sql.append("   AND       A.SUBJ      = D.SUBJ(+)                                               \n");
            body_sql.append("   AND       A.YEAR      = D.YEAR(+)                                               \n");
            body_sql.append("   AND       A.SUBJSEQ   = D.SUBJSEQ(+)                                            \n");
            body_sql.append("   AND       A.LESSON    = D.LESSON(+)                                             \n");
            body_sql.append("   AND       A.SEQ       = D.SEQ(+)                                                \n");

            //System.out.println(" QnaAdmin p_grcode ::: "+p_grcode);

            if (!p_grcode.equals("ALL")) {
                body_sql.append("   AND       A.GRCODE= " + SQLString.Format(p_grcode) + " \n");
            }
            if (!p_grseq.equals("ALL"))
                body_sql.append("   AND       c.grseq=" + SQLString.Format(p_grseq) + " \n");

            if (!p_gyear.equals("ALL") && !p_gyear.equals("0000")) {
                body_sql.append("   AND       A.YEAR = " + SQLString.Format(p_gyear) + " \n");
            }

            if (!ss_uclass.equals("ALL")) {
                body_sql.append("   AND       SCUPPERCLASS = '" + ss_uclass + "' \n");
                if (!ss_mclass.equals("ALL")) {
                    body_sql.append("   AND       SCMIDDLECLASS = '" + ss_mclass + "' \n");
                    if (!ss_lclass.equals("ALL")) {
                        body_sql.append("   AND       SCLOWERCLASS = '" + ss_lclass + "' \n");
                    }
                }
            }

            if (!v_startdate.equals("") && !v_enddate.equals("")) {
                body_sql.append(" AND substring(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "'  \n");
            }
            if (!ss_subjcourse.equals("ALL")) {
                body_sql.append("   AND       SCSUBJ = '" + ss_subjcourse + "' \n");
            }

            if (!ss_subjseq.equals("ALL")) {
                body_sql.append("   AND       SCSUBJSEQ = '" + ss_subjseq + "' \n");
            }

            if (!v_searchtext.equals("")) { //    �˻�� ������                                                                
                v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�                                                           

                if (v_select.equals("title")) { //    �������� �˻��Ҷ�                                                            
                    body_sql.append("   AND       A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�                                                   
                    body_sql.append("   AND       A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��
                } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�                                                      
                    body_sql.append("   AND       B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��    
                }

                body_sql.append("   AND       A.CATEGORYCD = '" + v_categorycd + "' \n");
            }

            if (v_order.equals("scsubjnm"))
                v_order = "C.SCSUBJNM";
            if (v_order.equals("subjseqgr"))
                v_order = "C.SUBJSEQGR";
            if (v_order.equals("title"))
                v_order = "A.TITLE";
            if (v_order.equals("name"))
                v_order = "B.NAME";
            if (v_order.equals("indate"))
                v_order = "A.INDATE";
            if (v_order.equals("replystate"))
                v_order = "REPLYSTATE";
            if (v_order.equals("")) {
                order_sql += " ORDER BY A.INDATE DESC, A.SEQ DESC ";
            } else {
                order_sql += " ORDER BY " + v_order + v_orderType;
            }

            sql = head_sql + body_sql.toString() + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            //count_sql= "select count(*) "+ body_sql;
            //int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     ��ü row ���� ��ȯ�Ѵ�
            //int total_page_count = ls.getTotalPage();       					  //     ��ü ������ ���� ��ȯ�Ѵ�
            //ls.setPageSize(row);             									  //  �������� row ������ �����Ѵ�
            //ls.setCurrentPage(v_pageno, total_row_count );                        //     ������������ȣ�� �����Ѵ�.

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /* �Խ��� ��ȣ�ޱ� */
    public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><img src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\"></a>&nbsp;&nbsp;";
            } else {
                str += "<img src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\">";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<B>" + i + "</B>" + "&nbsp;";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "')\">" + i + "</a>&nbsp;";
                }
            }

            if (pagelist.next()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\">&nbsp;<img src=\"/images/board/next.gif\"  border=\"0\" align=\"middle\"></a>";
            } else {
                str += "<img src=\"/images/board/next.gif\" border=\"0\" align=\"middle\">";
            }

            if (str.equals("")) {
                str += "�ڷᰡ �����ϴ�.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";

            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     */
    public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");
        // String v_fileseq = box.getString("p_fileseq");
        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");
        String v_grocde = box.getStringDefault("s_grcode", "N000001");
        String s_userid = box.getSession("userid");

        // int v_upfilecnt = (box.getInt("p_upfilecnt") > 0 ? box.getInt("p_upfilecnt") : 1);

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        // int[] fileseq = new int[v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            if (!"".equals(vv_type)) {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            sql = "select a.tabseq, a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd,a.grcode, b.fileseq,b.realfile, b.savefile, a.indate ,c.name,d.type, ";
            sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm, ";
            sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm ";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c,tz_bds d ";
            sql += " where    \n";
            // ������ : 05.11.04 ������ : �̳��� _(+)  ����
            //      sql += " a.tabseq = b.tabseq(+)   \n";
            //      sql += " and a.tabseq = d.tabseq(+)   \n";
            //      sql += " and a.seq = b.seq(+)         \n";
            //      sql += " and a.types = b.types(+)     \n";
            //      sql += " and a.inuserid = c.userid(+)  \n";
            sql += " a.tabseq  =  b.tabseq(+)   \n";
            sql += " and a.tabseq  =  d.tabseq(+)   \n";
            if (!v_grocde.equals("ALL"))
                sql += " and c.grcode  =  '" + v_grocde + "'   \n";
            sql += " and a.seq  =  b.seq(+)         \n";
            sql += " and a.types  =  b.types(+)     \n";
            sql += " and a.inuserid  =  c.userid(+)   \n";

            // ������ : 05.11.04 ������ : �̳��� _ ������� ����
            //sql += " and a.upfile = b.savefile(+)   \n";
            sql += " and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types = " + v_types;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();

                if ((dbox.getString("d_okyn1").equals("") ? "1" : dbox.getString("d_okyn1")).equals("1")) {

                    if (updateRepStatus(v_tabseq, v_seq, "2", s_userid) > 0) {
                        dbox.put("d_okyn1", "2");
                    }
                }
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }

            sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = " + v_seq + " and types = " + v_types;
            connMgr.executeUpdate(sql);

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     */
    public DataBox selectRepQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_repseq = box.getInt("p_repseq");
        // int v_seq = box.getInt("p_seq");
        String v_reptypes = box.getString("p_reptypes");
        String v_type = box.getString("p_reptype");
        // String v_types = box.getString("p_types");
        // int v_upfilecnt = (box.getInt("p_upfilecnt") > 0 ? box.getInt("p_upfilecnt") : 1);

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        // int[] fileseq = new int[v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            sql = "select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd,a.grcode, b.fileseq,b.realfile, b.savefile, a.indate ,c.name,d.type, ";
            sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            sql += " (select okyn1 from tz_homeqna where tabseq = " + v_tabseq + " and seq = " + v_repseq + " and types = 0 ) repstatus, ";
            sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm, ";
            sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c,tz_bds d";
            sql += " where    \n";
            // ������ : 05.11.04 ������ : �̳��� _(+)  ����
            //        sql +=" a.tabseq = b.tabseq(+)   \n";
            //        sql +=" and a.tabseq = d.tabseq(+)   \n";
            //        sql +=" and a.seq = b.seq(+)         \n";
            //        sql +=" and a.types = b.types(+)     \n";
            //        sql +=" and a.inuserid = c.userid(+)  \n";
            sql += " a.tabseq  =  b.tabseq(+)   \n";
            sql += " and a.tabseq  =  d.tabseq(+)   \n";
            sql += " and a.seq  =  b.seq(+)         \n";
            sql += " and a.types  =  b.types(+)     \n";
            sql += " and a.inuserid  =  c.userid(+)  \n";

            // ������ : 05.11.04 ������ : �̳��� _ ������� ����
            //sql += " and a.upfile = b.savefile(+)   \n";
            sql += " and a.tabseq = " + v_tabseq + " and a.seq = " + v_repseq + " and a.types = " + v_reptypes;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                // ������ : 05.11.24 ������ : �̳���
                //            realfileVector.addElement(ls.getString("realfile"));
                //            savefileVector.addElement(ls.getString("savefile"));
                //			fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            //sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq + " and types = "+ v_types;
            //connMgr.executeUpdate(sql);
            //System.out.println(sql);

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     */
    public DataBox selectRepCourseQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_repseq = box.getInt("p_repseq");
        // int v_seq = box.getInt("p_seq");

        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_repkind = box.getString("p_repkind");
        // String v_kind = box.getString("p_kind");

        // int v_upfilecnt = (box.getInt("p_upfilecnt") > 0 ? box.getInt("p_upfilecnt") : 1);

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        // int[] fileseq = new int[v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();

            sql += " select                                                         \n";
            sql += "   a.subj,   a.subjseq,   a.year,   a.seq,                      \n";
            sql += "   a.kind,  a.title,   a.categorycd,  a.grcode, a.contents,     \n";
            sql += "   a.indate ,  b.fileseq,  b.realfile,   b.savefile,            \n";
            sql += "   get_name(a.inuserid) name,   okyn1,   okuserid1,   okyn2,    \n";
            sql += "   okuserid2,   okdate1,   okdate2,   \n";
            sql += "   (select count(*)  from tz_qnafile x where x.subj = a.subj and x.subjseq = a.subjseq and x.year = a.year and x.seq = a.seq and x.kind = a.kind ) upfilecnt,   \n";
            sql += "   (select okyn1 from tz_qna where subj    = '" + v_subj + "' and subjseq = '" + v_subjseq + "' and year    = '" + v_year + "' and seq     =  " + v_repseq + "  and kind    =  0) repstatus, \n";
            sql += "   (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,   \n";
            sql += "   (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm  \n";
            sql += " from   \n";
            sql += "   tz_qna a, tz_qnafile b  \n";
            sql += " where    \n";
            // ������ : 05.11.04 ������ : �̳��� _(+)  ����
            //        sql+= "   a.subj = b.subj(+)   \n";
            //        sql+= "   and a.year = b.year(+)  \n";
            //        sql+= "   and a.subjseq = b.subjseq(+)  \n";
            //        sql+= "   and a.seq     = b.seq(+)  \n";
            //        sql+= "   and a.kind    = b.kind(+)  \n";
            sql += "   a.subj  =  b.subj(+)   \n";
            sql += "   and a.year  =  b.year(+)  \n";
            sql += "   and a.subjseq  =  b.subjseq(+)  \n";
            sql += "   and a.seq      =  b.seq(+)  \n";
            sql += "   and a.kind     =  b.kind(+)  \n";
            // ������ : 05.11.04 ������ : �̳��� _ ������� ����

            sql += "   and a.subj    = '" + v_subj + "'  \n";
            sql += "   and a.subjseq = '" + v_subjseq + "'  \n";
            sql += "   and a.year    = '" + v_year + "'  \n";
            sql += "   and a.seq     =  " + v_repseq + "  \n";
            sql += "   and a.kind    =  " + v_repkind + "  \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                // ������ : 05.11.24 ������ : �̳���
                //            realfileVector.addElement(ls.getString("realfile"));
                //            savefileVector.addElement(ls.getString("savefile"));
                //			fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            //sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq + " and types = "+ v_types;
            //connMgr.executeUpdate(sql);
            //System.out.println(sql);
            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * ���õ� QnA ������������ �󼼳��� select
     */
    public DataBox selectQnaCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_type = box.getString("p_type");
        String v_categorycd = box.getString("p_categorycd");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();
        try {
            connMgr = new DBConnectionManager();

            //sql  = "select  a.subj,a.year,a.subjseq,a.lesson,a.seq,a.kind,a.title,a.grcode, b.scsubjnm,b.subjseqgr,a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, ";
            //sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            //sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,  ";
            //sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm  ";
            //sql += " from tz_qna a, vz_scsubjseq b, tz_member c   ";
            //sql += " where a.subj = b.subj and a.inuserid = c.userid(+) and a.year=b.year and a.subjseq = b.subjseq and a.kind=0 ";
            //sql += "       and a.seq = "+ v_seq +" and a.subj= '"+ v_subj +"' and a.year = '"+v_year+"' and a.subjseq = '"+ v_subjseq +"'  ";
            //if(!v_categorycd.equals("") ){
            //   sql +=" and categorycd = '"+v_categorycd+"'";
            //}

            sql += " select a.subj, a.year, a.subjseq,  a.lesson,                                         ";
            sql += "        a.seq,  a.kind,  a.title,  a.contents, a.grcode,                              ";
            sql += "        b.scsubjnm,  b.subjseqgr,  a.inuserid,                                        ";
            sql += "        a.categorycd,  a.indate ,  get_name(a.inuserid) name,                     ";
            sql += "        okyn1,   okuserid1,  okyn2,  okuserid2,  okdate1,  okdate2,                   ";
            sql += "        (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,            ";
            sql += "        (select codenm from tz_code                                                   ";
            sql += "          where gubun = '0046' and levels = '1' and code = a.categorycd) categorynm,  ";
            sql += "        c.fileseq,c.realfile, c.savefile                                              ";
            sql += "   from tz_qna a, tz_qnafile c , vz_scsubjseq b                                       ";
            sql += "  where a.subj   = b.subj                                                             ";
            sql += "    and a.year    = b.year                                                            ";
            sql += "    and a.subjseq = b.subjseq                                                         ";
            sql += "    and a.subj     =  c.subj(+)                                                           ";
            sql += "    and a.year     =  c.year(+)                                                           ";
            sql += "    and a.subjseq  =  c.subjseq(+)                                                        ";
            sql += "    and a.lesson   =  c.lesson(+)                                                         ";
            sql += "    and a.seq      =  c.seq(+)                                                            ";
            sql += "    and a.kind     =  c.kind       		(+)                                              ";
            sql += "    and a.kind=0                                                                      ";
            sql += "    and a.seq = " + v_seq + "                                                             ";
            sql += "    and a.subj= '" + v_subj + "'                                                          ";
            sql += "    and a.year = '" + v_year + "'                                                         ";
            sql += "    and a.subjseq = '" + v_subjseq + "'                                                   ";
            if (!v_categorycd.equals("")) {
                sql += "   and categorycd = '" + v_categorycd + "'                                              ";
            }

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * QNA ��ȭ�鿡�� �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int viewupdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        ListSet ls = null;
        ListSet ls1 = null;
        int isOk = 0;
        // int isOk1 = 0;

        // String s_grcode = box.getString("p_grcode");
        int v_seq = box.getInt("p_seq");
        String v_type = box.getString("p_type");
        String v_types = box.getString("p_types");
        String v_categorycd = box.getString("p_categorycd");
        String v_okyn1 = box.getString("p_okyn1");
        String v_okyn2 = box.getString("p_okyn2");
        String v_approval1 = box.getString("p_approval1");
        String v_approval2 = box.getString("p_approval2");

        // String v_isopen  = "Y";
        // String s_userid = "";
        String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");

        //   if (s_gadmin.equals("A1")){
        //       s_userid = "���";
        //   }else{
        // s_userid = box.getSession("userid");
        //    }
        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //----------------�з��� �����Ͽ� ������Ʈ�Ѵ�.---------------

            sql = " update TZ_HOMEQNA set ";
            if (v_okyn1.equals("Y") && v_approval1.equals("N")) {
                sql += " okyn1 = '" + v_okyn1 + "' ,   \n";
                sql += " okuserid1 = '" + s_usernm + "',    \n";
                sql += " okdate1 = to_char(sysdate, 'YYYYMMDDHH24MISS'),   \n";
            }

            if (v_okyn2.equals("Y") && v_approval1.equals("Y") && v_approval2.equals("N")) {
                sql += " okyn2 = '" + v_okyn2 + "' ,   \n";
                sql += " okuserid2 = '" + s_usernm + "',     \n";
                sql += " okdate2 = to_char(sysdate, 'YYYYMMDDHH24MISS'),  \n";
            }

            sql += "  categorycd = ?     \n";
            sql += "  where tabseq = ? and seq = ? and types = ?   \n";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_categorycd);
            pstmt.setInt(2, v_tabseq);
            pstmt.setInt(3, v_seq);
            pstmt.setString(4, v_types);

            isOk = pstmt.executeUpdate();

            //----------------------   �Խ����� �亯�ۼ��� üũ�Ѵ�.----------------------------

            sql3 = "select count(*) replyCnt from tz_homeqna where tabseq = " + v_tabseq + " and seq= " + v_seq + " and types>0";

            ls1 = connMgr.executeQuery(sql3);
            ls1.next();
            int v_replyCnt = ls1.getInt(1);
            ls1.close();

            //----------------�з��� �ٲ�� �亯�۵� ������Ʈ�Ѵ�.---------------

            if (v_replyCnt > 0) {
                sql2 = " update TZ_HOMEQNA set ";
                sql2 += " categorycd = '" + v_categorycd + "' ";
                sql2 += " where tabseq =? and seq = ? and types>0 ";

                pstmt1 = connMgr.prepareStatement(sql2);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);

                pstmt1.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA ���������� ��ȭ�鿡�� �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int viewCourseupdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        // String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        // ListSet ls = null;
        ListSet ls1 = null;
        int isOk = 1;
        int isOk1 = 1;

        // String s_grcode = box.getString("p_grcode");
        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_type = box.getString("p_type");
        String v_categorycd = box.getString("p_categorycd");
        String v_okyn1 = box.getString("p_okyn1");
        String v_okyn2 = box.getString("p_okyn2");
        String v_approval1 = box.getString("p_approval1");
        String v_approval2 = box.getString("p_approval2");

        // String v_isopen  = "Y";
        // String s_userid = "";
        String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");

        //        if (s_gadmin.equals("A1")) {
        //            s_userid = "���";
        //        } else {
        //            s_userid = box.getSession("userid");
        //        }

        try {
            connMgr = new DBConnectionManager();
            // 05.11.23 �̳���
            connMgr.setAutoCommit(false);
            //----------------�з��� �����Ͽ� ������Ʈ�Ѵ�.---------------

            sql = " update TZ_qna set ";
            if (v_okyn1.equals("Y") && v_approval1.equals("N")) {
                sql += " okyn1 = '" + v_okyn1 + "' ,   \n";
                sql += " okuserid1 = '" + s_usernm + "',    \n";
                sql += " okdate1 = to_char(sysdate, 'YYYYMMDDHH24MISS'),   \n";
            }

            if (v_okyn2.equals("Y") && v_approval1.equals("Y") && v_approval2.equals("N")) {
                sql += " okyn2 = '" + v_okyn2 + "' ,   \n";
                sql += " okuserid2 = '" + s_usernm + "',     \n";
                sql += " okdate2 = to_char(sysdate, 'YYYYMMDDHH24MISS'),  \n";
            }

            sql += "  categorycd = ?     \n";
            sql += "  where seq = ? and subj = ? and year = ? and subjseq = ?   \n";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_categorycd);
            pstmt.setInt(2, v_seq);
            pstmt.setString(3, v_subj);
            pstmt.setString(4, v_year);
            pstmt.setString(5, v_subjseq);
            isOk = pstmt.executeUpdate();
            //----------------------   �Խ����� �亯�ۼ��� üũ�Ѵ�. ----------------------------

            sql3 = "select count(*) replyCnt from tz_qna where seq = " + v_seq + " and subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "' and kind>0";

            ls1 = connMgr.executeQuery(sql3);
            ls1.next();
            int v_replyCnt = ls1.getInt(1);
            ls1.close();

            //----------------�з��� �ٲ�� �亯�۵� ������Ʈ�Ѵ�.---------------

            if (v_replyCnt > 0) {
                sql2 = " update TZ_qna set ";
                sql2 += " categorycd = '" + v_categorycd + "' ";
                sql2 += " where seq = " + v_seq + " and subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "' and kind>0";

                pstmt1 = connMgr.prepareStatement(sql2);

                isOk1 = pstmt1.executeUpdate();
            }
            isOk = isOk * isOk1;
            connMgr.commit();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA ����Ҷ�(�亯)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertQnaAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        StringBuffer sql3 = new StringBuffer();
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int v_cnt = 0;

        String s_grcode = box.getString("p_grcode");
        int v_seq = box.getInt("p_seq");
        String v_types = "";
        String v_type = box.getStringDefault("p_type", "PQ");//��ü,Ȩ������,��ڿ���,������������ ������ �� �ִ� Ű
        String v_title = box.getString("p_title");
        String v_contents = box.getString("contents");
        String v_isopen = "Y";

        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        String v_categorycd = box.getString("p_categorycd");
        String v_okyn1 = box.getString("p_repstatus");

        // boolean isMailed = false;

        //    if (s_gadmin.equals("A1")){
        //        s_userid = "���";
        //    }else{
        s_userid = box.getSession("userid");
        //    }
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            sql = " select max(to_number(types)) from TZ_HOMEQNA  ";
            sql += "  where tabseq = " + v_tabseq + " and seq = " + v_seq;
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_types = String.valueOf((ls.getInt(1) + 1));
            } else {
                v_types = "1";
            }

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            sql2 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, cnt, grcode,categorycd ";
            //        if(v_tabseq == 101 || v_tabseq == 34 || v_tabseq == 100 ) {
            //        	sql2 += " ,retmailing ";
            //        }

            sql2 += ")                ";
            //      sql2 += " values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,? ";
            sql2 += " values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,? ";
            //        if(v_tabseq == 101 || v_tabseq == 34 || v_tabseq == 100){
            //        	  sql2 += " ,'N' ";
            //        }
            sql2 += " ) ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql2);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            //		pstmt.setString(index++, v_contents);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isopen);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_cnt);
            pstmt.setString(index++, s_grcode);
            pstmt.setString(index++, v_categorycd);

            isOk = pstmt.executeUpdate();
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, v_types, box); //      ����÷���ߴٸ� ����table��  insert

            /*
             * System.out.println(" QnaAdmin tabseq::::"+v_tabseq);
             * System.out.println(" QnaAdmin seq::::"+v_seq);
             * System.out.println(" QnaAdmin types::::"+v_types);
             * System.out.println(" QnaAdmin type::::"+v_type);
             */

            // ���Ϲ߼�   ( 1 vs 1, ���װԽ���, ��ڿ��� )
            if (v_okyn1.equals("3") && (v_tabseq == 101 || v_tabseq == 34 || v_tabseq == 100 || v_tabseq == 1208)) {
                sendFormMail(connMgr, box, v_tabseq, v_contents);
            }

            /*
             * 05.11.16 �̳��� sql2 = "select contents from TZ_HOMEQNA"; sql2 +=
             * "  where tabseq    = " + v_tabseq ; sql2 += "    and seq    = " +
             * v_seq; sql2 += "    and types = " +
             * StringManager.makeSQL(v_types);
             * 
             * connMgr.setOracleCLOB(sql2, v_contents); // (ORACLE 9i ����)
             */

            // �亯 ���¸� �����մϴ�.
            sql3.append(" UPDATE TZ_HOMEQNA                                        \n ");
            sql3.append(" SET                                                      \n ");
            sql3.append("     OKYN1       = ?                                      \n ");
            sql3.append("     , LUSERID   = ?                                      \n ");
            sql3.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql3.append(" WHERE                                                    \n ");
            sql3.append("         TABSEQ  = ?                                      \n ");
            sql3.append(" AND     SEQ     = ?                                      \n ");
            sql3.append(" AND     TYPES   = 0                                         ");

            index = 1;
            pstmt = connMgr.prepareStatement(sql3.toString());

            pstmt.setString(index++, v_okyn1);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk3 = pstmt.executeUpdate();

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0)
                connMgr.commit();
            else
                connMgr.rollback();

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �亯 ������ �߼�
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : send ok 2 : send fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public boolean sendFormMail(DBConnectionManager connMgr, RequestBox box, int p_tabseq, String p_contents) throws Exception {
        boolean isMailed = false;
        String v_mailContent = "";

        ListSet ls = null;
        ListSet ls1 = null;
        String sql = "";
        String sql1 = "";

        String v_grtype = "";
        String v_grcode = box.getString("p_grcode");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        int v_seq = box.getInt("p_seq");

        try {

            //////////////////// ������ �߼� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            sql1 = " select grtype from tz_grcode ";
            sql1 += "  where grcode = " + StringManager.makeSQL(v_grcode);
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_grtype = ls1.getString("grtype");
            }

            String v_sendhtml = "freeMailForm.html";
            FormMail fmail = new FormMail(v_sendhtml, v_grtype); //      �����Ϲ߼��� ���

            box.put("grtype", v_grtype);

            ConfigSet conf = new ConfigSet();
            String v_fromEmail = "";
            String v_fromName = "";
            String v_comptel = "";

            if (v_grtype.equals("KGDI")) {
                v_fromEmail = conf.getProperty("mail.admin.email.kgdi");
                v_fromName = conf.getProperty("mail.admin.name.kgdi");
                v_comptel = conf.getProperty("mail.admin.comptel.kgdi");
            } else if (v_grtype.equals("KOCCA")) {
                v_fromEmail = conf.getProperty("mail.admin.email.kocca");
                v_fromName = conf.getProperty("mail.admin.name.kocca");
                v_comptel = conf.getProperty("mail.admin.comptel.kocca");
            } else {
                v_fromEmail = conf.getProperty("mail.admin.email");
                v_fromName = conf.getProperty("mail.admin.name");
                v_comptel = conf.getProperty("mail.admin.comptel");
            }
            box.put("p_fromEmail", v_fromEmail);
            box.put("p_fromName", v_fromName);
            box.put("p_comptel", v_comptel);
            MailSet mset = new MailSet(box); //      ���� ���� �� �߼�

            String v_mailTitle = " [�亯] " + v_title;
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// /

            sql = " select userid, name, email, ismailing, state ";
            sql += "   from TZ_MEMBER                                    ";
            sql += "  where userid = (select inuserid from TZ_HOMEQNA    ";
            sql += "                  where tabseq = " + p_tabseq;
            sql += "                    and types = '0' and seq = " + v_seq;
            sql += "                 )                                   ";
            sql += "    and state = 'Y'                                  ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                String v_isMailing = "1";

                String v_userid = ls.getString("userid");
                String v_toEmail = ls.getString("email");

                if (!v_toEmail.equals("")) {
                    mset.setSender(fmail); //  ���Ϻ����� ��� ����

                    fmail.setVariable("content", StringUtil.removeTag(p_contents));
                    v_mailContent = fmail.getNewMailContent();

                    isMailed = mset.sendMail(v_userid, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return isMailed;
    }

    public int insertQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 1;
        int isOk1 = 1;
        // int isOk2 = 1;
        int isOk3 = 1;
        int v_cnt = 0;
        int v_tabseq = 7;

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_types = "0";
        String s_userid = box.getSession("userid");
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_isopen = box.getStringDefault("p_isopen", "Y");
        String v_categorycd = box.getString("s_subj");

        // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
        /*
         * ConfigSet conf = new ConfigSet(); SmeNamoMime namo = new
         * SmeNamoMime(v_contents); // ��ü���� boolean result = namo.parse(); // ����
         * �Ľ� ���� if ( !result ) { // �Ľ� ���н� System.out.println(
         * namo.getDebugMsg() ); // ����� �޽��� ��� return 0; } if (
         * namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� String v_server =
         * conf.getProperty("autoever.url.value"); String fPath =
         * conf.getProperty("dir.namo"); // ���� ���� ��� ���� String refUrl =
         * conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ��� String
         * prefix = "factory" + v_categorycd; // ���ϸ� ���ξ� result =
         * namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ���� } if (
         * !result ) { // �������� ���н� System.out.println( namo.getDebugMsg() ); //
         * ����� �޽��� ��� return 0; }
         */
        //v_contents = namo.getContent(); // ���� ����Ʈ ���
        v_content = (String) NamoMime.setNamoContent(v_content);

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //------------------------------------------------------------------------------------
            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql.setLength(0);

            sql.append(" SELECT NVL(MAX(SEQ), 0) FROM TZ_HOMEQNA WHERE TABSEQ = '7' ");

            ls = connMgr.executeQuery(sql.toString());

            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

            //////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql.setLength(0);

            sql.append(" INSERT INTO TZ_HOMEQNA(TABSEQ, SEQ, TYPES, TITLE, CONTENTS, INDATE, INUSERID, ISOPEN, LUSERID, LDATE, GRCODE, CNT, CATEGORYCD, OKYN1) \n");
            sql.append(" VALUES (?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),?,?,?, ?) ");

            pstmt1 = connMgr.prepareStatement(sql.toString());

            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setString(4, v_title);
            //pstmt1.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
            pstmt1.setString(5, v_content);
            pstmt1.setString(6, s_userid);
            pstmt1.setString(7, v_isopen);
            pstmt1.setString(8, s_userid);
            pstmt1.setString(9, s_grcode);
            pstmt1.setInt(10, v_cnt);
            pstmt1.setString(11, v_categorycd);
            pstmt1.setString(12, "4"); // ���� ���丮 �Խ� 

            isOk1 = pstmt1.executeUpdate(); //      ���� �ش� content �� empty_clob()�� �����ϰ� ���� ���� ��Ʈ������ ġȯ�Ѵ�.
            //isOk2 = UploadUtil.fnRegisterAttachFile(box);
            isOk3 = this.insertUpFile(connMgr, v_tabseq, v_seq, v_types, box);

            if (isOk1 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
                isOk = 0;
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return isOk;
    }

    /**
     * QNA ���������� ����Ҷ�(�亯)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertQnaCourseAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        // String sql1 = "";
        String sql2 = "";
        StringBuffer sql3 = new StringBuffer();
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        String s_grcode = box.getString("p_grcode");

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_type = box.getString("p_type");
        String v_title = box.getString("p_title");
        String v_contents = box.getString("contents");
        // String v_contents =  StringManager.replace(box.getString("content"),"<br>","\n");
        String v_isopen = "Y";
        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        String v_categorycd = box.getString("p_categorycd");
        String v_kind = "";
        String v_lesson = box.getString("p_lesson");
        String v_okyn1 = box.getString("p_repstatus");

        //    if (s_gadmin.equals("A1")){
        //        s_userid = "���";
        //    }else{
        s_userid = box.getSession("userid");
        //    }
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql = " select max(to_number(kind)) from tz_qna  ";
            sql += "  where seq = " + v_seq + " and subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = '" + v_subjseq + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_kind = String.valueOf((ls.getInt(1) + 1));
            } else {
                v_kind = "1";
            }

            // String v_namoseq = v_subj + v_subjseq + v_year + String.valueOf(v_seq) + v_kind;
            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            sql2 = "insert into TZ_QNA(subj,year,subjseq,lesson, seq, kind, title, contents, indate, inuserid, isopen, luserid, ldate, grcode,categorycd, okyn1)                ";
            //      sql2 += "                values (?, ?, ?, ?,?,?,?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?) ";
            sql2 += "                values (?, ?, ?, ?,?,?,?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?, ?) ";
            int index = 1;
            pstmt = connMgr.prepareStatement(sql2);
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_lesson);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_kind);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            //        pstmt.setString(index++, v_contents);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isopen);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, s_grcode);
            pstmt.setString(index++, v_categorycd);
            pstmt.setString(index++, v_okyn1);

            isOk = pstmt.executeUpdate();
            /*
             * 05.11.16 �̳��� sql2 = "select contents from TZ_qna"; sql2 +=
             * "  where seq = "+ v_seq +" and subj = '"+v_subj+"' and year = '"+
             * v_year +"' and subjseq = '"+v_subjseq+ "' and kind= '"+ v_kind +
             * "' " ;
             * 
             * connMgr.setOracleCLOB(sql2, v_contents); // (ORACLE 9i ����)
             */

            isOk1 = insertCourseUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, v_kind, box);

            sql3.append(" UPDATE TZ_QNA                                           \n ");
            sql3.append(" SET                                                      \n ");
            sql3.append("     OKYN1       = ?                                      \n ");
            sql3.append("     , LUSERID   = ?                                      \n ");
            sql3.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql3.append(" WHERE                                                    \n ");
            sql3.append("         SUBJ    = ?                                      \n ");
            sql3.append(" AND     YEAR    = ?                                      \n ");
            sql3.append(" AND     SUBJSEQ = ?                                      \n ");
            sql3.append(" AND     LESSON  = ?                                      \n ");
            sql3.append(" AND     SEQ     = ?                                      \n ");
            sql3.append(" AND     KIND    = 0                                         ");

            index = 1;
            pstmt = connMgr.prepareStatement(sql3.toString());

            pstmt.setString(index++, v_okyn1);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_lesson);
            pstmt.setInt(index++, v_seq);

            isOk2 = pstmt.executeUpdate();

            if (isOk > 0 && isOk1 > 0 && isOk2 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA �亯 ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectQnaListA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ListSet ls1 = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql1 = "";
        DataBox dbox = null;

        // String v_categorycd = box.getStringDefault("p_categorycd", "00");
        // int v_pageno = box.getInt("p_pageno");
        int v_seq = box.getInt("p_seq");
        //int v_tabseq = box.getInt("p_tabseq");
        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");
        String v_grcode = box.getString("s_grcode");

        // String v_fileseq = box.getString("p_fileseq");
        // String v_gadmin = box.getSession("gadmin");

        // Vector realfileVector = new Vector();
        // Vector savefileVector = new Vector();
        // Vector fileseqVector = new Vector();

        // int v_upfilecnt = (box.getInt("p_upfilecnt") > 0 ? box.getInt("p_upfilecnt") : 1);
        // int[] fileseq = new int[v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            if (!"".equals(vv_type)) {
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            sql += " select a.tabseq, a.seq , a.types, a.title, a.contents, a.indate, a.inuserid, ";
            sql += "        a.upfile, a.isopen, a.luserid, a.ldate, b.name,a.cnt, a.categorycd ";
            sql += "   from TZ_HOMEQNA a, tz_member b ";
            sql += "  where   ";
            // ������ : 05.11.04 ������ : �̳��� _(+)  ����
            //      sql += "  a.inuserid = b.userid(+)";
            sql += "  a.inuserid  =  b.userid(+)";
            sql += "  and   a.tabseq   = " + v_tabseq;
            sql += "  and   a.seq      = " + v_seq;
            sql += "  and   a.types  != '0' ";
            if (!v_grcode.equals("ALL"))
                sql += "  and   a.grcode  = '" + v_grcode + "' ";

            //        if(!v_categorycd.equals("") ){
            //           sql +=" and a.categorycd = '"+v_categorycd+"'";
            //        }

            sql += " order by seq desc, types asc ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
                //break; // lee1 �� �˻��ϸ� �ΰ��� ���� ���� �׷��� ���� ���ϸ� �׷��� �Ѱ����� ó�� �ؾ� �Ǳ� ������ �ѹ� ����Ÿ �̰��ϰ� ��������

                //���⼭ tz_homefile select �� ���Ͱ����� ����
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * QNA ���������� �亯 ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectQnaCourseListA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.16 ������ : �̳��� _ totalcount �ϱ����� ��������
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_type = box.getString("p_type");
        String v_categorycd = box.getString("p_categorycd");

        //String v_categorycd = box.getStringDefault("p_categorycd", "00");
        int v_pageno = box.getInt("p_pageno");
        //int v_seq = box.getInt("p_seq");
        //int v_tabseq = box.getInt("p_tabseq");
        //String vv_type = box.getString("pp_type");
        //String v_type = box.getString("p_type");
        try {
            connMgr = new DBConnectionManager();

            //------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate

            head_sql = "select  a.subj,a.year,a.subjseq,a.lesson,a.seq,a.kind,a.title, b.scsubjnm,b.subjseqgr,a.inuserid, a.contents, a.categorycd, a.indate ,c.name, ";
            head_sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            head_sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,  ";
            head_sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm  ";
            body_sql += " from tz_qna a, vz_scsubjseq b, tz_member c   ";
            // ������ : 05.11.04 ������ : �̳��� _(+)  ����
            //      sql += " where a.subj = b.subj and a.inuserid = c.userid(+) and a.year=b.year and a.subjseq = b.subjseq and a.kind>0 ";
            body_sql += " where a.subj = b.subj and a.inuserid  =  c.userid(+) and a.year=b.year and a.subjseq = b.subjseq and a.kind>0 ";
            body_sql += "       and a.seq = " + v_seq + " and a.subj= '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq = '" + v_subjseq + "' and c.grcode='N000001'  ";

            if (!v_categorycd.equals("")) {
                body_sql += " and categorycd = '" + v_categorycd + "'";
            }

            order_sql += " order by seq desc ";
            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     ��ü row ���� ��ȯ�Ѵ�
            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list.add(dbox);
            }

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * QNA ���������� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteCourseQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        // String sql = "";
        String sql1 = "";
        int isOk1 = 1;
        // int isOk2 = 1;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        // String v_type = box.getString("p_type");
        // String v_categorycd = box.getString("p_categorycd");

        // int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�		
        Vector v_savefile = box.getVector("p_savefile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ���������� �亯���û���
            sql1 = " delete from TZ_qna    ";
            sql1 += "  where seq = " + v_seq + " and subj = '" + v_subj + "' and year= '" + v_year + "' and subjseq = '" + v_subjseq + "' ";
            pstmt1 = connMgr.prepareStatement(sql1);
            isOk1 = pstmt1.executeUpdate();

            // ÷������ ����
            this.deleteCourseUpFile(connMgr, box);
            connMgr.commit();
            if (v_savefile != null) {
                FileManager.deleteFile(v_savefile); //	 ÷������ ����
            }

            //        if (isOk1 >	0 && isOk2 > 0)	{
            //			connMgr.commit();
            //			if (v_savefile != null)	{
            //				FileManager.deleteFile(v_savefile);			//	 ÷������ ����
            //			}            
            //		} else connMgr.rollback();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

    /**
     * QNA ���������� �亯 �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteRepCourseQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        // String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        // int isOk2 = 1;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        int v_repseq = box.getInt("p_repseq");
        String v_repkind = box.getString("p_repkind");
        // String v_categorycd = box.getString("p_categorycd");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // ���������� �亯���û���
            sql1 = " delete from TZ_qna    ";
            sql1 += "  where ";
            sql1 += "  seq = " + v_repseq;
            sql1 += "  and subj = '" + v_subj + "' ";
            sql1 += "  and year= '" + v_year + "'   ";
            sql1 += "  and subjseq = '" + v_subjseq + "' ";
            sql1 += "  and kind    = '" + v_repkind + "' ";

            pstmt1 = connMgr.prepareStatement(sql1);
            isOk1 = pstmt1.executeUpdate();

            //���ϻ���
            sql2 = "delete from tz_qnafile   \n";
            sql2 += " where                  \n";
            sql2 += " subj        = ?        \n";
            sql2 += " and year    = ?        \n";
            sql2 += " and subjseq = ?        \n";
            sql2 += " and seq     = ?        \n";
            sql2 += " and kind    = ?        \n";

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setInt(4, v_repseq);
            pstmt2.setString(5, v_repkind);
            pstmt2.executeUpdate();
            connMgr.commit();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

    /**
     * QNA �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3 = 1;

        String v_type = box.getString("p_type");

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");

        int v_repseq = box.getInt("p_repseq");
        String v_reptypes = box.getString("p_reptypes");

        Vector savefile = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            if (v_types.equals("0")) { // ���������� �亯���û���
                sql1 = " delete from TZ_HOMEQNA    ";
                sql1 += "  where tabseq = ? and seq = ?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);
            } else {
                sql1 = " delete from TZ_HOMEQNA";
                sql1 += "  where tabseq = ? and seq = ? and types = ?  ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_repseq);
                pstmt1.setString(3, v_reptypes);
            }

            isOk1 = pstmt1.executeUpdate();

            sql3 = " delete from TZ_COMMENTQNA    ";
            sql3 += "  where tabseq = ? and seq = ? and types = ?  ";
            pstmt2 = connMgr.prepareStatement(sql3);
            if (v_types.equals("0")) { // ���������� �亯���û���
                pstmt2.setInt(1, v_tabseq);
                pstmt2.setInt(2, v_seq);
                pstmt2.setString(3, v_types);
            } else {
                pstmt2.setInt(1, v_tabseq);
                pstmt2.setInt(2, v_repseq);
                pstmt2.setString(3, v_reptypes);
            }

            pstmt2.executeUpdate();

            for (int i = 0; i < savefile.size(); i++) {
                String str = (String) savefile.elementAt(i);
                if (!str.equals("")) {
                    //isOk2 = this.deleteUpFile(connMgr, box);
                }
            }

            isOk2 = this.deleteUpFile(connMgr, box, savefile);

            if (isOk1 > 0) {
                if (savefile != null) {
                    FileManager.deleteFile(savefile); //     ÷������ ����
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);
                }

                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2;
    }

    /**
     * QNA �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteRepQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3 = 1;

        String v_type = box.getString("p_type");
        // int v_seq = box.getInt("p_seq");
        // String v_types = box.getString("p_types");
        int v_repseq = box.getInt("p_repseq");
        String v_reptypes = box.getString("p_reptypes");

        Vector savefile = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

            sql1 = " delete from TZ_HOMEQNA ";
            sql1 += " where tabseq = ? and seq = ? and types = ?  ";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_repseq);
            pstmt1.setString(3, v_reptypes);

            isOk1 = pstmt1.executeUpdate();

            sql3 = " delete from TZ_COMMENTQNA    ";
            sql3 += "  where tabseq = ? and seq = ? and types = ?  ";
            pstmt2 = connMgr.prepareStatement(sql3);

            pstmt2.setInt(1, v_tabseq);
            pstmt2.setInt(2, v_repseq);
            pstmt2.setString(3, v_reptypes);

            pstmt2.executeUpdate();

            if (savefile.size() > 0) {
                isOk2 = this.deleteRepUpFile(connMgr, box, null);
            }

            if (isOk1 > 0 && isOk2 > 0) {
                if (savefile != null) {
                    FileManager.deleteFile(savefile); //     ÷������ ����
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);
                }
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2;
    }

    /**
     * QNA ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, String types, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 0;
        int isOk = 1;

        //----------------------   ���ε�Ǵ� ������ ������	�˰� �ڵ��ؾ��Ѵ�  --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {

            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));

        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
            //----------------------	�ڷ� ��ȣ �����´� ----------------------------
            sql = "select isnull(max(fileseq),	0) from	tz_homefile	where tabseq = " + p_tabseq + " and seq = " + p_seq + "and types = " + types;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_homefile(tabseq, seq, fileseq, types, realfile, savefile, luserid,	ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, types);
                    pstmt2.setString(5, v_realFileName[i]);
                    pstmt2.setString(6, v_newFileName[i]);
                    pstmt2.setString(7, s_userid);
                    isOk2 = pstmt2.executeUpdate();

                    isOk = isOk * isOk2;
                    v_fileseq++;
                    System.out.println(" QnaAdmin2020 p_tabseq ::: " + p_tabseq);
                    System.out.println(" QnaAdmin2021 p_seq    ::: " + p_seq);
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //	�Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA Upload Filelist
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */

    public ArrayList<DataBox> fileList(int v_tabseq, int v_seq, String types) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        String sql1 = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            sql1 = "select fileseq,realfile, savefile";
            sql1 += " from tz_homefile ";
            sql1 += " where tabseq = " + v_tabseq + "  and seq = " + v_seq + " and types =  '" + types + "'";
            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * QNA Upload Filelist
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public ArrayList<DataBox> fileCourseList(String v_subj, String v_year, String v_subjseq, int v_repseq, String v_repkind) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        String sql1 = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();
            sql1 = "select fileseq,realfile, savefile";
            sql1 += " from tz_qnafile                        ";
            sql1 += " where                                  ";
            sql1 += "   subj = '" + v_subj + "'                ";
            sql1 += "   and year = '" + v_year + "'           ";
            sql1 += "   and subjseq  =  '" + v_subjseq + "'    ";
            sql1 += "   and seq = " + v_repseq;
            sql1 += "   and kind = '" + v_repkind + "' ";
            System.out.println(" QnaAdmin sql9 " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * QNA �亯����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int viewReplayUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        // int v_cnt = 0;
        // String s_grcode = box.getString("p_grcode");

        int v_repseq = box.getInt("p_repseq");
        String v_reptypes = box.getString("p_reptypes");
        String v_type = box.getStringDefault("p_type", "PQ");//��ü,Ȩ������,��ڿ���,������������ ������ �� �ִ� Ű
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        // String v_isopen = "Y";
        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        // String v_categorycd = box.getString("p_categorycd");
        String v_okyn1 = box.getString("p_repstatus");

        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        Vector<String> v_savefile = new Vector<String>();
        Vector<String> v_filesequence = new Vector<String>();

        // boolean isMailed = false;

        //    if (s_gadmin.equals("A1")){
        //      s_userid = "���";
        //    }else{
        s_userid = box.getSession("userid");
        //    }
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            for (int i = 0; i < v_upfilecnt; i++) {
                if (!box.getString("p_fileseq" + i).equals("")) {
                    v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                    v_filesequence.addElement(box.getString("p_fileseq" + i)); //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
                }
            }

            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //sql2 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, cnt, grcode,categorycd)                ";
            //sql2 += " values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

            sql2 = " update tz_homeqna set     ";
            sql2 += "   title = ?,              ";
            //      sql2+= "   contents = empty_clob(),";
            sql2 += "   contents = ?,";
            sql2 += "   luserid = ?,            ";
            sql2 += "   ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')               ";
            sql2 += " where                     ";
            sql2 += "   tabseq = ?              ";
            sql2 += "   and seq = ?             ";
            sql2 += "   and types = ?           ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql2);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_repseq);
            pstmt.setString(index++, v_reptypes);
            isOk = pstmt.executeUpdate();

            /*
             * 05.11.16 �̳��� sql2 = "select contents from TZ_HOMEQNA"; sql2 +=
             * "  where tabseq    = " + v_tabseq ; sql2 += "    and seq    = " +
             * v_repseq; sql2 += "    and types = " +
             * StringManager.makeSQL(v_reptypes); connMgr.setOracleCLOB(sql2,
             * v_contents); // (ORACLE 9i ����)
             */

            isOk2 = this.deleteRepUpFile(connMgr, box, v_filesequence);
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_repseq, v_reptypes, box); //      ����÷���ߴٸ� ����table��  insert

            isOk3 = updateRepStatus(v_tabseq, v_repseq, v_okyn1, s_userid); // �������� �亯���θ� ����

            if (v_okyn1.equals("3") && (v_tabseq == 101 || v_tabseq == 34 || v_tabseq == 100 || v_tabseq == 1208)) {
                sendFormMail(connMgr, box, v_tabseq, v_contents);
            }

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                // ���Ϲ߼�   ( 1 vs 1, ���װԽ���, ��ڿ��� )
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA �亯����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int viewReplayCourseUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmtu = null;
        String sql2 = "";
        String sql3 = "";
        String sqlu = "";
        int isOk = 0;
        // int isOk3 = 1;
        // int isOku = 0;
        // String s_grcode = box.getString("p_grcode");

        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        int v_repseq = box.getInt("p_repseq");
        String v_repkind = box.getString("p_repkind");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        // String v_isopen = "Y";
        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        // String v_categorycd = box.getString("p_categorycd");
        String v_repstatus = box.getString("p_repstatus");

        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        Vector<String> v_savefile = new Vector<String>();
        Vector<String> v_filesequence = new Vector<String>();

        s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // String v_namoseq = String.valueOf(v_repseq) + v_repkind;
            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            for (int i = 0; i < v_upfilecnt; i++) {
                if (!box.getString("p_fileseq" + i).equals("")) {
                    v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                    v_filesequence.addElement(box.getString("p_fileseq" + i)); //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
                }
            }

            sql2 = " update tz_qna set     ";
            sql2 += "  okyn1 = '3' ,   \n";
            sql2 += "   title    = ?,              ";
            sql2 += "   contents = ?,";
            sql2 += "   luserid = ?,            ";
            sql2 += "   ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')               ";
            sql2 += " where                   ";
            sql2 += "   subj = ?              ";
            sql2 += "   and subjseq = ?       ";
            sql2 += "   and year = ?          ";
            sql2 += "   and seq = ?           ";
            sql2 += "   and kind = ?        ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql2);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_year);
            pstmt.setInt(index++, v_repseq);
            pstmt.setString(index++, v_repkind);

            isOk = pstmt.executeUpdate();

            sqlu = " update tz_qna set   ";
            sqlu += " okyn1 = ? \n";
            sqlu += " where                   ";
            sqlu += "   subj = ?              ";
            sqlu += "   and subjseq = ?       ";
            sqlu += "   and year = ?          ";
            sqlu += "   and seq = ?           ";
            sqlu += "   and kind = '0'        ";
            pstmtu = connMgr.prepareStatement(sqlu);
            pstmtu.setString(1, v_repstatus);
            pstmtu.setString(2, v_subj);
            pstmtu.setString(3, v_subjseq);
            pstmtu.setString(4, v_year);
            pstmtu.setInt(5, v_repseq);

            pstmtu.executeUpdate();

            //���ϻ���
            sql3 = "delete from tz_qnafile where subj = " + v_subj + " and year ='" + v_year + "' and subjseq ='" + v_subjseq + "' and seq = " + v_repseq + " and kind = '" + v_repkind + "' and fileseq = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < v_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String) v_filesequence.elementAt(i));
                pstmt3.setInt(1, v_fileseq);

                pstmt3.executeUpdate();
            }

            this.insertCourseUpFile(connMgr, v_subj, v_year, v_subjseq, v_repseq, v_repkind, box);

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA �亯 ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateRepStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // PreparedStatement pstmt3 = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");
        String s_userid = box.getSession("userid");
        String v_okyn1 = box.getString("p_repstatus");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" UPDATE TZ_HOMEQNA                                        \n ");
            sql.append(" SET                                                      \n ");
            sql.append("     OKYN1       = ?                                      \n ");
            sql.append("     , LUSERID   = ?                                      \n ");
            sql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql.append(" WHERE                                                    \n ");
            sql.append("         TABSEQ  = ?                                      \n ");
            sql.append(" AND     SEQ     = ?                                      \n ");
            sql.append(" AND     TYPES   = 0                                         ");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, v_okyn1);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA �亯 ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateRepStatus(int v_tabseq, int v_seq, String repstatus, String s_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // PreparedStatement pstmt3 = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" UPDATE TZ_HOMEQNA                                        \n ");
            sql.append(" SET                                                      \n ");
            sql.append("     OKYN1       = ?                                      \n ");
            sql.append("     , LUSERID   = ?                                      \n ");
            sql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql.append(" WHERE                                                    \n ");
            sql.append("         TABSEQ  = ?                                      \n ");
            sql.append(" AND     SEQ     = ?                                      \n ");
            sql.append(" AND     TYPES   = 0                                         ");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, repstatus);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA �亯 ���� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateCourseRepStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // PreparedStatement pstmt3 = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        int v_seq = box.getInt("p_seq");

        String s_userid = box.getSession("userid");
        String v_okyn1 = box.getString("p_repstatus");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" UPDATE TZ_QNA                                           \n ");
            sql.append(" SET                                                      \n ");
            sql.append("     OKYN1       = ?                                      \n ");
            sql.append("     , LUSERID   = ?                                      \n ");
            sql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql.append(" WHERE                                                    \n ");
            sql.append("         SUBJ    = ?                                      \n ");
            sql.append(" AND     YEAR    = ?                                      \n ");
            sql.append(" AND     SUBJSEQ = ?                                      \n ");
            sql.append(" AND     LESSON  = ?                                      \n ");
            sql.append(" AND     SEQ     = ?                                      \n ");
            sql.append(" AND     KIND    = 0                                         ");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, v_okyn1);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_subj);
            pstmt.setString(index++, v_year);
            pstmt.setString(index++, v_subjseq);
            pstmt.setString(index++, v_lesson);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk3 = 1;
        String v_types = box.getString("p_types");
        int v_seq = box.getInt("p_seq");

        try {

            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            sql3 = "delete from tz_homefile where tabseq = " + v_tabseq + " and seq =? and fileseq = ? and types = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < p_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));
                pstmt3.setInt(1, v_seq);
                pstmt3.setInt(2, v_fileseq);
                pstmt3.setString(3, v_types);
                System.out.println("fileseq : " + v_fileseq);
                isOk3 = pstmt3.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteRepUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk3 = 1;
        String v_types = box.getString("p_reptypes");
        String v_type = box.getString("p_type");
        int v_seq = box.getInt("p_repseq");

        try {

            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            if (p_filesequence != null) {
                sql3 = "delete from tz_homefile where tabseq = " + v_tabseq + " and seq =? and fileseq = ? and types = ? ";
                pstmt3 = connMgr.prepareStatement(sql3);
                for (int i = 0; i < p_filesequence.size(); i++) {
                    int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));
                    pstmt3.setInt(1, v_seq);
                    pstmt3.setInt(2, v_fileseq);
                    pstmt3.setString(3, v_types);
                    isOk3 = pstmt3.executeUpdate();
                }
            } else {
                sql3 = "delete from tz_homefile where tabseq = " + v_tabseq + " and seq =? and types = ?";
                pstmt3 = connMgr.prepareStatement(sql3);
                pstmt3.setInt(1, v_seq);
                pstmt3.setString(2, v_types);
                isOk3 = pstmt3.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * QNA ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int insertCourseUpFile(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, int p_seq, String p_kind, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 0;
        int isOk = 1;

        //----------------------   ���ε�Ǵ� ������ ������	�˰� �ڵ��ؾ��Ѵ�  --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
            System.out.println(" QnaAdmin 2514 v_realFileName [i]:::" + v_realFileName[i]);
        }
        //----------------------------------------------------------------------------------------------------------------------------
        String s_userid = box.getSession("userid");

        try {
            //----------------------	�ڷ� ��ȣ �����´� ----------------------------
            sql = "select isnull(max(fileseq),	0) from	tz_qnafile	where subj = '" + p_subj + "' and subjseq = '" + p_subjseq + "' and year = '" + p_year + "' and seq = " + p_seq + " and kind = '" + p_kind + "'";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            System.out.println("fileseq::::" + v_fileseq);

            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_qnafile(";
            sql2 += " subj,     subjseq,    year,     lesson,   ";
            sql2 += " seq,      kind,       fileseq,  realfile, ";
            sql2 += " savefile, luserid,	ldate)              ";
            sql2 += " values (";
            sql2 += " ?,  ?,  ?,  '000', ";
            sql2 += " ?,  ?,  ?,  ?, ";
            sql2 += " ?,  '" + s_userid + "',  to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setString(1, p_subj);
                    pstmt2.setString(2, p_subjseq);
                    pstmt2.setString(3, p_year);
                    pstmt2.setInt(4, p_seq);
                    pstmt2.setString(5, p_kind);
                    pstmt2.setInt(6, v_fileseq);
                    pstmt2.setString(7, v_realFileName[i]);
                    pstmt2.setString(8, v_newFileName[i]);
                    isOk2 = pstmt2.executeUpdate();

                    isOk = isOk * isOk2;
                    v_fileseq++;
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //	�Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk;
    }

    /**
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    public int deleteCourseUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;

        String sql3 = "";
        int isOk3 = 1;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        int v_seq = box.getInt("p_seq");

        try {
            sql3 = "delete from tz_qnafile   \n";
            sql3 += " where                  \n";
            sql3 += " subj        = ?        \n";
            sql3 += " and year    = ?        \n";
            sql3 += " and subjseq = ?        \n";
            sql3 += " and seq     = ?        \n";

            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_year);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setInt(4, v_seq);
            isOk3 = pstmt3.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * QNA ���������� ��ȭ�鿡�� Ŭ���� �亯���� ���¸� ������Ʈ
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int viewQnAUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";

        ListSet ls = null;
        int isOk = 1;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");

        //        String s_userid = "";
        //        if (s_gadmin.equals("A1")) {
        //            s_userid = "���";
        //        } else {
        //            s_userid = box.getSession("userid");
        //        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select isnull(okyn1,	'1') from	TZ_qna where seq = '" + v_seq + "' and subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq = " + v_subjseq;
            ls = connMgr.executeQuery(sql);
            ls.next();
            String okyn1 = ls.getString(1);
            ls.close();

            if (okyn1.equals("1")) {
                connMgr.setAutoCommit(false);

                sql = " update TZ_qna set ";
                sql += " okyn1 = '2' ,   \n";
                sql += " okuserid1 = '" + s_usernm + "',    \n";
                sql += " okdate1 = to_char(sysdate, 'YYYYMMDDHH24MISS')   \n";
                sql += "  where seq = ? and subj = ? and year = ? and subjseq = ?   \n";
                pstmt = connMgr.prepareStatement(sql);

                pstmt.setInt(1, v_seq);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_year);
                pstmt.setString(4, v_subjseq);
                isOk = pstmt.executeUpdate();
                connMgr.commit();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    //����
    public ArrayList<DataBox> excelListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
        StringBuffer sql = new StringBuffer();
        String sql1 = "";

        PreparedStatement pstmt = null;

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_categorycd = box.getStringDefault("p_categorycd", "00");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String ss_grcode = box.getString("s_grcode");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //������ ����
        int v_pageno = box.getInt("p_pageno");

        String v_type = box.getStringDefault("p_type", "PQ");//��ü,Ȩ������,��ڿ���,������������ ������ �� �ִ� Ű
        int v_tabseq = 0;
        int v_tablen = 0;
        String v_tabseqstr = "";

        if (v_order.equals("indate")) {

            v_order = "a.indate";

        }

        try {

            connMgr = new DBConnectionManager();

            if (!v_type.equals("ALL")) {

                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type);

            } else {

                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE IN ('PQ','MM')";

            }

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {

                v_tabseq = ls.getInt(1);
                v_tabseqstr += v_tabseq + ",";

            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            v_tablen = v_tabseqstr.length() - 1;
            v_tabseqstr = StringManager.substring(v_tabseqstr, 0, v_tablen);

            list = new ArrayList<DataBox>();

            if (v_type.equals("ALL")) {

                sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1,a.CONTENTS \n");
                sql.append("         , (select z.CONTENTS from TZ_HOMEQNA z where a.tabseq=z.tabseq and a.seq=z.seq and z.types='1' and a.categorycd=z.categorycd) as ans \n");
                sql.append("         , '' EDUDATE \n");
                sql.append(" FROM    ( SELECT  *  FROM  TZ_HOMEQNA  WHERE  TYPES = '0' ) A \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                sql.append(" WHERE   A.INUSERID = B.USERID \n");
                sql.append("   AND   A.GRCODE = B.GRCODE \n");
                sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                sql.append("   AND   C.TYPE IN('PQ','CU','BU','OO','MM') \n");
                sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = E.SEQ(+) \n");
                sql.append("   AND   C.TYPE      = 'SUBJ' \n");

                if (!ss_grcode.equals("ALL")) {

                    sql.append("   AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");

                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {

                    sql.append("   AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");

                } else if (!v_startdate.equals("") && v_enddate.equals("")) {

                    sql.append("   AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");

                }

                if (!v_searchtext.equals("")) { //    �˻�� ������

                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�

                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�

                        sql.append("   AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");

                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�

                        sql.append("   AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�

                        sql.append("   AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    }
                }

                if (!v_categorycd.equals("00")) {

                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");

                }

                sql.append(" UNION ALL \n");
                sql.append(" SELECT  A.SEQ, A.KIND TYPES, A.TITLE , A.SUBJ, A.YEAR, C.SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE,A.INUSERID, '' UPFILE,  A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,  A.SUBJSEQ,   A.CATEGORYCD, 0 CNT \n");
                sql.append("         , '' TYPE, B.NAME, C.SCSUBJ,C.SUBJSEQGR, 0 FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0) REPLYSTATE, A.OKYN1 ,a.CONTENTS \n");
                sql.append("         , (select z.CONTENTS from TZ_QNA z where a.SUBJ=z.SUBJ and a.YEAR=z.YEAR and z.SUBJSEQ=a.SUBJSEQ and a.LESSON=z.LESSON and a.SEQ=z.SEQ and z.KIND='1') as ans \n");
                sql.append("         , (SELECT SUBSTR(EDUSTART,1,4)||'.'||SUBSTR(EDUSTART,5,2)||'.'||SUBSTR(EDUSTART,7,2)||'~'||SUBSTR(EDUEND,1,4)||'.'||SUBSTR(EDUEND,5,2)||'.'||SUBSTR(EDUEND,7,2) AS EDUDATE ");
                sql.append("            FROM TZ_SUBJSEQ WHERE SUBJ = D.SUBJ AND YEAR = D.YEAR AND SUBJSEQ = D.SUBJSEQ ) AS EDUDATE \n");
                sql.append(" FROM    ( SELECT  * FROM  TZ_QNA WHERE  KIND = '0' ) A \n");
                sql.append("         , ( SELECT  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ, COUNT(*) REPLYSTATE FROM  TZ_QNA WHERE  KIND > '0' GROUP BY  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ ) D \n");
                sql.append("         , TZ_MEMBER B, VZ_SCSUBJSEQ C \n");
                sql.append(" WHERE A.INUSERID = B.USERID(+) \n");
                sql.append(" AND A.SUBJ = C.SUBJ(+) \n");
                sql.append(" AND A.YEAR=C.YEAR(+) \n");
                sql.append(" AND A.SUBJSEQ = C.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJSEQ = D.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJ = D.SUBJ(+) \n");
                sql.append(" AND A.YEAR = D.YEAR(+) \n");
                sql.append(" AND A.LESSON = D.LESSON(+) \n");
                sql.append(" AND A.SEQ   = D.SEQ(+) \n");

                if (!ss_grcode.equals("ALL")) {

                    sql.append(" AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");

                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {

                    sql.append(" AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");

                } else if (!v_startdate.equals("") && v_enddate.equals("")) {

                    sql.append(" AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");

                }

                if (!v_searchtext.equals("")) { //    �˻�� ������

                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�

                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�

                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");

                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�

                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�

                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    }

                }

                if (!v_categorycd.equals("00")) {

                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");

                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {

                    sql.append(" ORDER BY REPLYSTATE, INDATE DESC, SEQ DESC,TYPES ASC ");

                } else {

                    sql.append(" ORDER BY " + v_order + v_orderType);

                }

            } else {

                sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                sql.append(" FROM    (SELECT  *  FROM  TZ_HOMEQNA WHERE  TYPES = '0' ) A \n");
                sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                sql.append(" WHERE   A.INUSERID = B.USERID \n");
                sql.append("   AND   A.GRCODE = B.GRCODE \n");
                sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = E.SEQ(+) \n");

                if (v_type.equals("ALL")) {

                    sql.append("    AND C.TYPE IN('PQ','CU','BU','OO', 'MM')  \n");

                } else if (v_type.equals("MM")) {

                    sql.append("    AND C.TYPE IN('CU','BU','OO', 'MM') \n");

                } else {

                    sql.append("    AND C.TYPE = '" + v_type + "' \n");

                }

                if (!ss_grcode.equals("ALL")) {

                    sql.append("    AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");

                }

                sql.append("    AND A.TABSEQ IN( " + v_tabseqstr + " ) \n");

                if (!v_startdate.equals("") && !v_enddate.equals("")) {

                    sql.append("  AND A.INDATE BETWEEN '" + v_startdate + "' AND '" + v_enddate + "999999' \n");

                } else if (!v_startdate.equals("") && v_enddate.equals("")) {

                    sql.append(" AND A.INDATE > '" + v_startdate + "' \n");

                }

                if (!v_searchtext.equals("")) { //    �˻�� ������

                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�

                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�

                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");

                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�

                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�

                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��

                    }

                }

                if (!v_categorycd.equals("00")) {

                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");

                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {

                    sql.append(" ORDER BY REPLYSTATE, A.INDATE DESC, A.SEQ DESC, A.TYPES ASC ");

                } else {

                    sql.append(" ORDER BY " + v_order + v_orderType);

                }

            }

            pstmt = connMgr.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ls = new ListSet(pstmt);

            /* ������ ������. �Ϻη� ���.. */
            System.out.println(sql.toString());
            /*****************************/

            ls.setPageSize(999999); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.

            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * �Խ��� ��� ��ü New
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectVocListAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String sql1 = "";

        PreparedStatement pstmt = null;

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_categorycd = box.getStringDefault("p_categorycd", "00");
        //String v_startdate  = box.getString("p_startdate");
        //String v_enddate    = box.getString("p_enddate");
        String v_startdate = "20130201";
        String v_enddate = "20130205";
        String ss_grcode = box.getStringDefault("s_grcode", "N000001");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //������ ����
        int v_pageno = box.getInt("p_pageno");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
        String s_userid = box.getSession("userid");

        String v_type = box.getStringDefault("p_type", "ALL");//��ü,Ȩ������,��ڿ���,������������ ������ �� �ִ� Ű
        int v_tabseq = 0;
        int v_tablen = 0;
        String v_tabseqstr = "";

        if (v_order.equals("indate")) {
            v_order = "a.indate";
        }

        try {
            connMgr = new DBConnectionManager();
            if (!v_type.equals("ALL")) {
                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type);
            } else {
                sql1 = "SELECT TABSEQ FROM TZ_BDS WHERE TYPE IN ('PQ','MM','KB')";
            }
            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                v_tabseq = ls.getInt(1);
                v_tabseqstr += v_tabseq + ",";
            }

            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            v_tablen = v_tabseqstr.length() - 1;
            v_tabseqstr = StringManager.substring(v_tabseqstr, 0, v_tablen);

            list = new ArrayList<DataBox>();

            if (v_type.equals("ALL")) {
                sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                sql.append(" FROM    ( SELECT  *  FROM  TZ_HOMEQNA  WHERE  TYPES = '0' ) A \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                sql.append("         ,( SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                sql.append(" WHERE   A.INUSERID = B.USERID \n");
                sql.append("   AND   A.GRCODE = B.GRCODE \n");
                sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                sql.append("   AND   C.TYPE IN('PQ','CU','BU','OO','MM','KB') \n");
                sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                sql.append("   AND   A.SEQ       = E.SEQ(+) \n");

                if (!ss_grcode.equals("ALL")) {
                    sql.append("   AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append("   AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append("   AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������        		
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�        		
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�        			
                        sql.append("   AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�        			
                        sql.append("   AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�        			
                        sql.append("   AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    }
                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                sql.append(" UNION ALL \n");
                sql.append(" SELECT  DISTINCT A.SEQ, A.KIND TYPES, A.TITLE , A.SUBJ, A.YEAR, C.SCSUBJNM \n");
                sql.append("         , A.GRCODE, A.INDATE,A.INUSERID, '' UPFILE,  A.ISOPEN \n");
                sql.append("         , A.LUSERID, A.LDATE,  A.SUBJSEQ,   A.CATEGORYCD, 0 CNT \n");
                sql.append("         , '' TYPE, B.NAME, C.SCSUBJ,C.SUBJSEQGR, 0 FILECNT \n");
                sql.append("         , NVL(D.REPLYSTATE, 0) REPLYSTATE, A.OKYN1 \n");
                sql.append(" FROM    ( SELECT  * FROM  TZ_QNA WHERE  KIND = '0' ) A \n");
                sql.append("         , ( SELECT  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ, COUNT(*) REPLYSTATE FROM  TZ_QNA WHERE  KIND > '0' GROUP BY  SUBJ, YEAR, SUBJSEQ, LESSON, SEQ ) D \n");
                sql.append("         , TZ_MEMBER B, VZ_SCSUBJSEQ C \n");
                sql.append(" WHERE A.INUSERID = B.USERID(+) \n");
                sql.append(" AND A.SUBJ = C.SUBJ(+) \n");
                sql.append(" AND A.YEAR=C.YEAR(+) \n");
                sql.append(" AND A.SUBJSEQ = C.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJSEQ = D.SUBJSEQ(+) \n");
                sql.append(" AND A.SUBJ = D.SUBJ(+) \n");
                sql.append(" AND A.YEAR = D.YEAR(+) \n");
                sql.append(" AND A.LESSON = D.LESSON(+) \n");
                sql.append(" AND A.SEQ   = D.SEQ(+) \n");

                if (!ss_grcode.equals("ALL")) {
                    sql.append(" AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append(" AND SUBSTRING(A.INDATE,1,8) BETWEEN '" + v_startdate + "' AND '" + v_enddate + "' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append(" AND SUBSTRING(A.INDATE,1,8) > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������        		
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�        		
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�        			
                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�        			
                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�        			
                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��        			
                    }
                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {
                    sql.append(" ORDER BY REPLYSTATE, INDATE DESC, SEQ DESC,TYPES ASC ");
                } else {
                    sql.append(" ORDER BY " + v_order + v_orderType);
                }
            } else {
                if (v_type.equals("KB") && v_gadmin.equals("P")) {
                    sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                    sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                    sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                    sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                    sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                    sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                    sql.append(" FROM    (SELECT  *  FROM  TZ_HOMEQNA WHERE  TYPES = '0' ) A \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                    sql.append("         , TZ_MEMBER B, TZ_BDS C, TZ_SUBJMAN D \n");
                    sql.append(" WHERE   A.INUSERID = B.USERID \n");
                    sql.append("   AND   A.GRCODE = B.GRCODE \n");
                    sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                    sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                    sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                    sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = E.SEQ(+) \n");
                    sql.append("   AND   D.USERID    = '" + s_userid + "' \n");
                    sql.append("   AND   D.SUBJ    = A.CATEGORYCD \n");

                } else {

                    sql.append(" SELECT  A.SEQ, A.TYPES, A.TITLE, '' SUBJ, '' YEAR, '' SCSUBJNM \n");
                    sql.append("         , A.GRCODE, A.INDATE, A.INUSERID, A.UPFILE, A.ISOPEN \n");
                    sql.append("         , A.LUSERID, A.LDATE,'' SUBJSEQ,  A.CATEGORYCD \n");
                    sql.append("         , A.CNT,C.TYPE, B.NAME, '' SCSUBJ,'' SUBJSEQGR \n");
                    sql.append("         , NVL(E.FILECNT, 0) FILECNT \n");
                    sql.append("         , NVL(D.REPLYSTATE, 0 ) REPLYSTATE, A.OKYN1 \n");
                    sql.append(" FROM    (SELECT  *  FROM  TZ_HOMEQNA WHERE  TYPES = '0' ) A \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) REPLYSTATE FROM  TZ_HOMEQNA WHERE  TYPES > '0' GROUP BY  TABSEQ, SEQ ) D \n");
                    sql.append("         ,(SELECT  TABSEQ, SEQ, COUNT(*) FILECNT FROM  TZ_HOMEFILE WHERE  TYPES = '0' GROUP BY   TABSEQ, SEQ ) E \n");
                    sql.append("         , TZ_MEMBER B, TZ_BDS C \n");
                    sql.append(" WHERE   A.INUSERID = B.USERID \n");
                    sql.append("   AND   A.GRCODE = B.GRCODE \n");
                    sql.append("   AND   A.TABSEQ = C.TABSEQ \n");
                    sql.append("   AND   A.TABSEQ IN( " + v_tabseqstr + " ) \n");
                    sql.append("   AND   A.TABSEQ    = D.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = D.SEQ(+) \n");
                    sql.append("   AND   A.TABSEQ    = E.TABSEQ(+) \n");
                    sql.append("   AND   A.SEQ       = E.SEQ(+) \n");
                }

                if (v_type.equals("ALL")) {
                    sql.append("    AND C.TYPE IN('PQ','CU','BU','OO', 'MM')  \n");
                } else if (v_type.equals("MM")) {
                    sql.append("    AND C.TYPE IN('CU','BU','OO', 'MM') \n");
                } else {
                    sql.append("    AND C.TYPE = '" + v_type + "' \n");
                }

                if (!ss_grcode.equals("ALL")) {
                    sql.append("    AND A.GRCODE = " + SQLString.Format(ss_grcode) + " \n");
                }

                sql.append("    AND A.TABSEQ IN( " + v_tabseqstr + " ) \n");

                if (!v_startdate.equals("") && !v_enddate.equals("")) {
                    sql.append("  AND A.INDATE BETWEEN '" + v_startdate + "' AND '" + v_enddate + "999999' \n");
                } else if (!v_startdate.equals("") && v_enddate.equals("")) {
                    sql.append(" AND A.INDATE > '" + v_startdate + "' \n");
                }

                if (!v_searchtext.equals("")) { //    �˻�� ������            	
                    v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�				
                    if (v_select.equals("title")) { //    �������� �˻��Ҷ�					
                        sql.append(" AND A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    } else if (v_select.equals("content")) { //    �������� �˻��Ҷ�					
                        sql.append(" AND A.CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��					
                    } else if (v_select.equals("name")) { //    �̸����� �˻��Ҷ�					
                        sql.append(" AND B.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n"); //   Oracle 9i ��					
                    }
                }

                if (v_gadmin.equals("F") || v_gadmin.equals("P")) {

                }

                if (!v_categorycd.equals("00")) {
                    sql.append(" AND A.CATEGORYCD = '" + v_categorycd + "' \n");
                }

                if (v_order.equals("type"))
                    v_order = "type";
                if (v_order.equals("title"))
                    v_order = "title";
                if (v_order.equals("name"))
                    v_order = "name";
                if (v_order.equals("indate"))
                    v_order = "indate";
                if (v_order.equals("upfile"))
                    v_order = "upfile";
                if (v_order.equals("replystate"))
                    v_order = "replystate";

                if (v_order.equals("") || v_order.equals("name")) {
                    sql.append(" ORDER BY REPLYSTATE, A.INDATE DESC, A.SEQ DESC, A.TYPES ASC ");
                } else {
                    sql.append(" ORDER BY " + v_order + v_orderType);
                }
            }

            pstmt = connMgr.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ls = new ListSet(pstmt);

            ls.setPageSize(row); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.

            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * VOC Total LIst NEW JEFF
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectVocTotalList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();

        PreparedStatement pstmt = null;

        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append(" select tabseq,seq,gubun1,gubun2,title,a.indate,inuserid,b.name, SUBJ, okyn1 from \n");
            sql.append("         (select  tabseq,seq,gubun1,gubun2,title,indate,inuserid, categorycd SUBJ, okyn1 from tz_homeqna \n");
            sql.append("         where indate like '2013%' and TYPES = '0'  \n");

            sql.append("         union    \n");

            sql.append("         select   \n");
            sql.append("         99 as tabseq,seq,gubun,gubun2,title,indate,inuserid, categorycd SUBJ, okyn1 from tz_qna  \n");
            sql.append("         where indate like '2013%' and kind = 0) a, tz_member b where  a.inuserid = b.userid order by a.indate desc \n");

            pstmt = connMgr.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ls = new ListSet(pstmt);

            ls.setPageSize(row); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.

            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }
}