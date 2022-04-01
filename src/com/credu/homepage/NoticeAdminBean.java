//**********************************************************
//  1. ��      ��: �������� ����
//  2. ���α׷��� : NoticeAdminBean.java
//  3. ��      ��: �������� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0��������
//  6. ��      ��: ��â�� 2005. 7.  14
//  7. ��      ��: �̳��� 05.11.17 _ connMgr.setOracleCLOB ����
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.course.EduGroupData;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class NoticeAdminBean {

    private ConfigSet config;
    private int row;
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; //	  �������� ���õ� ����÷�� ����

    public NoticeAdminBean() {
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

    //=========�����ȭ�� ����Ʈ ����=========

    /**
     * ��ü���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ü���� ����Ʈ
     * @throws Exception
     */
    public ArrayList selectListNoticeAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        //String sql = "";
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_defaultcomp = "";

        v_defaultcomp = selectDefalutComp(box);

        String v_search = box.getString("p_search");
        String v_searchtext = box.getString("p_searchtext");
        String v_selcomp = box.getStringDefault("p_selcomp", v_defaultcomp);
        //�����׷� ����Ʈ �ڽ� (09.10.22)
        String v_grcode = box.getString("s_grcode");
        // String tem_grcode   = box.getSession("tem_grcode");
        String v_selGroup = box.getStringDefault("p_selGroup", "ALL"); //�����ְ�

        int v_tabseq = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql.append(" select                                \n");
            sql.append("         rownum                        \n");
            sql.append("         , a.seq                       \n");
            sql.append("         , a.addate                    \n");
            sql.append("         , a.adtitle                   \n");
            sql.append("         , a.adname                    \n");
            sql.append("         , a.cnt                       \n");
            sql.append("         , a.luserid                   \n");
            sql.append("         , a.ldate                     \n");
            sql.append("         , a.isall                     \n");
            sql.append("         , a.useyn                     \n");
            sql.append("         , a.popup                     \n");
            sql.append("         , a.loginyn                   \n");
            sql.append("         , a.gubun                     \n");
            sql.append("         , a.aduserid                  \n");
            sql.append("         , a.onoffgubun                \n");
            sql.append(" 	    ,(  select  count(realfile)    \n");
            sql.append(" 	          from  tz_boardfile       \n");
            sql.append(" 	         where  tabseq  = a.TABSEQ \n");
            sql.append(" 	           and  seq     = a.seq    \n");
            sql.append(" 	    ) filecnt                      \n");
            sql.append(" from    TZ_NOTICE  a                  \n");
            sql.append(" where isall = 'Y'                     \n");

            if (!v_selcomp.equals("ALL")) {

                //�Ѱ������ڰ� �ƴѰ�� �ش� �����ְ��� ��Ÿ������ ��
                if (!v_selGroup.equals("ALL")) {
                    sql.append(" and grcodecd like '%" + v_selGroup + "%' \n");
                }
            }

            //���� �׷� ���ý� �߰�(09.10.22)
            if (!v_grcode.equals("ALL")) {
                sql.append(" and grcodecd like '%" + v_grcode + "%' \n");
            }

            // �˻�� �ִ� ���
            if (!v_searchtext.equals("")) {
                if (v_search.equals("adtitle")) { // �������� �˻��Ҷ�
                    sql.append(" and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("adcontents")) { // �������� �˻��Ҷ�
                    sql.append(" and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }

            sql.append(" and tabseq = " + v_tabseq + " \n");
            sql.append(" order by seq desc ");
            System.out.println("notice all sql = " + sql.toString());
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * ��������ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ(��ü���� ����)
     * @throws Exception
     */
    public ArrayList selectListNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();

        //�����׷� ����Ʈ �ڽ� (09.10.22)
        String v_grcode = box.getStringDefault("s_grcode", "ALL");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_defaultcomp = "";
        v_defaultcomp = selectDefalutComp(box);

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_selcomp = box.getStringDefault("p_selcomp", v_defaultcomp);

        String v_selGroup = box.getStringDefault("p_selGroup", "ALL"); //�����ְ�

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //            head_sql.append(" select                                                    \n");
            //            head_sql.append("         rownum                                            \n");
            //            head_sql.append("         , a.seq                                           \n");
            //            head_sql.append("         , a.addate                                        \n");
            //            head_sql.append("         , a.adtitle                                       \n");
            //            head_sql.append("         , a.adname                                        \n");
            //            head_sql.append("         , a.cnt                                           \n");
            //            head_sql.append("         , a.luserid                                       \n");
            //            head_sql.append("         , a.ldate                                         \n");
            //            head_sql.append("         , a.isall                                         \n");
            //            head_sql.append("         , a.useyn                                         \n");
            //            head_sql.append("         , a.popup                                         \n");
            //            head_sql.append("         , a.loginyn                                       \n");
            //            head_sql.append("         , a.aduserid                                      \n");
            //            head_sql.append("         , a.gubun                                         \n");
            //            head_sql.append("         , a.onoffgubun                                         \n");
            //            head_sql.append("         , b.filecnt                                       \n");
            //            head_sql.append("                                                           \n");
            //
            //            body_sql.append(" from    TZ_NOTICE  a                                      \n");
            //            body_sql.append("         , (                                               \n");
            //            body_sql.append("             select  tabseq, seq, count(realfile) filecnt  \n");
            //            body_sql.append("               from  tz_boardfile                          \n");
            //            body_sql.append("             group by  tabseq, seq                         \n");
            //            body_sql.append("         ) b                                               \n");
            //            body_sql.append(" where a.tabseq  = b.tabseq(+)                             \n");
            //            body_sql.append("   and a.seq     = b.seq(+)                                \n");
            //            body_sql.append("   and a.isall   = 'N'                                     \n");
            //            body_sql.append("   and a.tabseq = " + v_tabseq + "                        \n");

            sql.append("SELECT  X.SEQ                       \n");
            sql.append("    ,   X.ADDATE                    \n");
            sql.append("    ,   X.ADTITLE                   \n");
            sql.append("    ,   X.ADNAME                    \n");
            sql.append("    ,   X.CNT                       \n");
            sql.append("    ,   X.LUSERID                   \n");
            sql.append("    ,   X.LDATE                     \n");
            sql.append("    ,   X.ISALL                     \n");
            sql.append("    ,   X.USEYN                     \n");
            sql.append("    ,   X.POPUP                     \n");
            sql.append("    ,   X.LOGINYN                   \n");
            sql.append("    ,   X.GUBUN                     \n");
            sql.append("    ,   X.USELIST                   \n");
            sql.append("    ,   X.TABSEQ                    \n");
            sql.append("    ,   X.ADCONTENT                 \n");
            sql.append("    ,   X.ADUSERID                  \n");
            sql.append("    ,   X.GRCODECD                  \n");
            sql.append("    ,   X.ONOFFGUBUN                \n");
            sql.append("    ,   (                           \n");
            sql.append("        SELECT  COUNT (REALFILE)    \n");
            sql.append("          FROM  TZ_BOARDFILE        \n");
            sql.append("         WHERE  TABSEQ = X.TABSEQ   \n");
            sql.append("           AND  SEQ = X.SEQ         \n");
            sql.append("        ) AS FILECNT                \n");
            sql.append("    ,   COUNT( SEQ ) OVER( ) AS TOT_CNT \n");
            sql.append("  FROM  TZ_NOTICE X                 \n");
            sql.append(" WHERE  X.TABSEQ = ").append(v_tabseq).append(" \n");

            if (!v_selcomp.equals("ALL")) {

                // �Ѱ������ڰ� �ƴѰ�� �ش� �����ְ��� ��Ÿ������ ��
                if (!v_selGroup.equals("ALL")) {

                    sql.append("   AND  GRCODECD LIKE '%").append(v_selGroup).append("%' \n");
                }
            }

            // ���� �׷� ���ý� �߰�(09.10.22)
            if (!v_grcode.equals("ALL")) {
                sql.append("  AND  GRCODECD LIKE '%").append(v_grcode).append("%' \n");
            }

            // �˻�� ������
            if (!v_searchtext.equals("")) {
                if (v_search.equals("adtitle")) {
                    sql.append("   AND  ADTITLE LIKE '%").append(v_searchtext).append("%'  \n");
                } else if (v_search.equals("adcontents")) {
                    sql.append("   AND  ADCONTENT LIKE '%").append(v_searchtext).append("%'    \n");
                }
            }
            sql.append(" ORDER  BY X.ISALL DESC, X.SEQ DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            int total_row_count = 0;
            if (ls.next()) {
                total_row_count = ls.getInt("TOT_CNT");
                ls.moveFirst();
            }

            ls.setPageSize(v_pagesize); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

    //=========�����ȭ�� ����Ʈ ��=========

    /**
     * �������� ����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int isOk = 0;
        int isOk2 = 0;
        int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
        String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String v_adtitle = box.getString("p_adtitle");
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_loginyn = box.getString("p_login");
        String v_useyn = box.getString("p_use");
        String v_compcd = box.getString("p_compcd");
        int v_popwidth = box.getInt("p_popsize1");
        int v_popheight = box.getInt("p_popsize2");
        int v_popxpos = box.getInt("p_popposition1");
        int v_popypos = box.getInt("p_popposition2");
        String v_popup = box.getString("p_popup");
        // String v_upfile = box.getNewFileName("p_file1");
        // String v_realfile = box.getRealFileName("p_file1");
        String v_useframe = box.getString("p_useframe");
        String v_uselist = box.getString("p_uselist");
        String v_isall = box.getString("p_isAllvalue");
        String v_grcodecd = box.getString("p_grocdecd");
        String v_onoffgugun = box.getString("p_onoffgugun");
        String v_community_yn = box.getString("p_community_yn");

        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* come.credu.homepage.NoticeAdminBean insertNotice(�������� ���� �Ϸ� ��ȣ ��ȸ) */\n");
            sql.append("SELECT  MAX(SEQ)    \n");
            sql.append("  FROM  TZ_NOTICE   \n");
            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }

            sql.setLength(0);
            sql.append("/* come.credu.homepage.NoticeAdminBean insertNotice(�������� ���) */\n");
            sql.append("INSERT  INTO    TZ_NOTICE ( \n");
            sql.append("        TABSEQ  \n");
            sql.append("    ,   SEQ \n");
            sql.append("    ,   GUBUN   \n");
            sql.append("    ,   STARTDATE   \n");
            sql.append("    ,   ENDDATE \n");
            sql.append("    ,   ADDATE  \n");
            sql.append("    ,   ADTITLE \n");
            sql.append("    ,   ADNAME  \n");
            sql.append("    ,   ADCONTENT   \n");
            sql.append("    ,   CNT \n");
            sql.append("    ,   LUSERID \n");
            sql.append("    ,   LDATE   \n");
            sql.append("    ,   LOGINYN \n");
            sql.append("    ,   USEYN   \n");
            sql.append("    ,   COMPCD  \n");
            sql.append("    ,   POPWIDTH    \n");
            sql.append("    ,   POPHEIGHT   \n");
            sql.append("    ,   POPXPOS \n");
            sql.append("    ,   POPYPOS \n");
            sql.append("    ,   POPUP   \n");
            sql.append("    ,   USELIST \n");
            sql.append("    ,   USEFRAME    \n");
            sql.append("    ,   ISALL   \n");
            sql.append("    ,   GRCODECD    \n");
            sql.append("    ,   ADUSERID    \n");
            sql.append("    ,   ONOFFGUBUN  \n");
            sql.append("    ,   COMMUNITY_YN    \n");
            sql.append(") VALUES (  \n");
            sql.append("        ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append(")   \n");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_gubun);
            pstmt.setString(index++, v_startdate);
            pstmt.setString(index++, v_enddate);
            pstmt.setString(index++, v_adtitle);
            pstmt.setString(index++, s_name);

            //pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            pstmt.setString(index++, v_content);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_loginyn);
            pstmt.setString(index++, v_useyn);
            pstmt.setCharacterStream(index++, new StringReader(v_compcd), v_compcd.length());
            //		 pstmt.setString(index++, v_compcd);
            pstmt.setInt(index++, v_popwidth);
            pstmt.setInt(index++, v_popheight);
            pstmt.setInt(index++, v_popxpos);
            pstmt.setInt(index++, v_popypos);
            //pstmt.setString(index++, v_upfile);       //����
            //pstmt.setString(index++, v_realfile);     //����
            pstmt.setString(index++, v_popup); //�˾�����
            pstmt.setString(index++, v_uselist); //����Ʈ��뿩��
            pstmt.setString(index++, v_useframe); //�����ӻ�뿩��
            pstmt.setString(index++, v_isall); //��ü��������
            pstmt.setCharacterStream(index++, new StringReader(v_grcodecd), v_grcodecd.length());
            //		 pstmt.setString(index++, v_grcodecd);
            pstmt.setString(index++, s_userid); //�����׷��ڵ�
            pstmt.setString(index++, v_onoffgugun); //�¿�������
            pstmt.setString(index++, v_community_yn); //Ŀ�´�Ƽ��� ����

            isOk = pstmt.executeUpdate();
            /*
			 */
            //           sql2 = "select adcontent from TZ_NOTICE where tabseq = " + v_tabseq + " and seq = " + v_seq ;
            //           //System.out.println("adcontent====>>>>>"+sql2);
            //           connMgr.setOracleCLOB(sql2, v_content);       //      (��Ÿ ���� ���)

            //           sql2 = "select compcd from tz_notice where seq = " + v_seq + "and tabseq = " + v_tabseq;
            //           connMgr.setOracleCLOB(sql2, v_compcd);       //      (��Ÿ ���� ���)

            //           sql2 = "select grcodecd from tz_notice where seq = " + v_seq + "and tabseq = " + v_tabseq;
            //           connMgr.setOracleCLOB(sql2, v_grcodecd);       //      (��Ÿ ���� ���)
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);

            if (isOk > 0 && isOk2 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
            Log.err.println("isOk==> " + isOk);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
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
     * �������� �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;

        box.put("p_type", "HN");

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String v_adtitle = box.getString("p_adtitle");
        String v_adcontent = box.getString("p_adcontent");
        String v_loginyn = box.getString("p_login");
        String v_useyn = box.getString("p_use");
        String v_compcd = box.getString("p_compcd");
        int v_popwidth = box.getInt("p_popsize1");
        int v_popheight = box.getInt("p_popsize2");
        int v_popxpos = box.getInt("p_popposition1");
        int v_popypos = box.getInt("p_popposition2");
        String v_popup = box.getString("p_popup");
        String v_uselist = box.getString("p_uselist");
        String v_useframe = box.getString("p_useframe");
        String v_isall = box.getString("p_isAllvalue");
        String v_grcodecd = box.getString("p_grocdecd");
        String v_onoffgubun = box.getString("p_onoff");
        String v_community_yn = box.getString("p_community_yn");

        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");

        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        Vector v_savefile = new Vector();
        Vector v_filesequence = new Vector();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            for (int i = 0; i < v_upfilecnt; i++) {
                if (!box.getString("p_fileseq" + i).equals("")) {
                    v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                    v_filesequence.addElement(box.getString("p_fileseq" + i)); //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
                }
            }

            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_adcontent = (String) NamoMime.setNamoContent(v_adcontent);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }

            sql.append("UPDATE  TZ_NOTICE                                      \n");
            sql.append("   SET  GUBUN = ?                                      \n");
            sql.append("    ,   STARTDATE = ?                                  \n");
            sql.append("    ,   ENDDATE = ?                                    \n");
            sql.append("    ,   ADTITLE = ?                                    \n");
            sql.append("    ,   ADNAME = ?                                     \n");
            sql.append("    ,   ADCONTENT = ?                                  \n");
            sql.append("    ,   LUSERID = ?                                    \n");
            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n");
            sql.append("    ,   LOGINYN = ?                                    \n");
            sql.append("    ,   USEYN = ?                                      \n");
            sql.append("    ,   COMPCD = ?                                     \n");
            sql.append("    ,   POPWIDTH = ?                                   \n");
            sql.append("    ,   POPHEIGHT = ?                                  \n");
            sql.append("    ,   POPXPOS = ?                                    \n");
            sql.append("    ,   POPYPOS = ?                                    \n");
            sql.append("    ,   POPUP = ?                                      \n");
            sql.append("    ,   USELIST = ?                                    \n");
            sql.append("    ,   USEFRAME = ?                                   \n");
            sql.append("    ,   ISALL = ?                                      \n");
            sql.append("    ,   GRCODECD = ?                                   \n");
            sql.append("    ,   ONOFFGUBUN = ?                                 \n");
            sql.append("    ,   COMMUNITY_YN = ?                               \n");
            sql.append(" WHERE  TABSEQ = ?                                     \n");
            sql.append("   AND  SEQ = ?                                        \n");

            pstmt = connMgr.prepareStatement(sql.toString());
            int index = 1;
            pstmt.setString(index++, v_gubun);
            pstmt.setString(index++, v_startdate);
            pstmt.setString(index++, v_enddate);
            pstmt.setString(index++, v_adtitle);
            pstmt.setString(index++, s_name);
            pstmt.setCharacterStream(index++, new StringReader(v_adcontent), v_adcontent.length());
            // pstmt.setString(index++, v_adcontent);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_loginyn);
            pstmt.setString(index++, v_useyn);
            pstmt.setCharacterStream(index++, new StringReader(v_compcd), v_compcd.length());
            // pstmt.setString(index++, v_compcd);
            pstmt.setInt(index++, v_popwidth);
            pstmt.setInt(index++, v_popheight);
            pstmt.setInt(index++, v_popxpos);
            pstmt.setInt(index++, v_popypos);
            pstmt.setString(index++, v_popup);
            pstmt.setString(index++, v_uselist);
            pstmt.setString(index++, v_useframe);
            pstmt.setString(index++, v_isall);
            pstmt.setCharacterStream(index++, new StringReader(v_grcodecd), v_grcodecd.length());
            pstmt.setString(index++, v_onoffgubun);
            pstmt.setString(index++, v_community_yn);
            //			pstmt.setString(index++, v_grcodecd);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();
            /*
             * 05.11.16 �̳��� sql2 =
             * "select adcontent from tz_notice where seq = " + v_seq +
             * "and tabseq = " + v_tabseq; connMgr.setOracleCLOB(sql2,
             * v_adcontent); // (��Ÿ ���� ���)
             * 
             * sql2 = "select compcd from tz_notice where seq = " + v_seq +
             * "and tabseq = " + v_tabseq; connMgr.setOracleCLOB(sql2,
             * v_compcd); // (��Ÿ ���� ���)
             * 
             * sql2 = "select grcodecd from tz_notice where seq = " + v_seq +
             * "and tabseq = " + v_tabseq; connMgr.setOracleCLOB(sql2,
             * v_grcodecd); // (��Ÿ ���� ���)
             */

            isOk3 = this.deleteUpFile(connMgr, box, v_filesequence); //	   ������ ������ �ִٸ�	����table���� ����
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box); //		����÷���ߴٸ� ����table��	insert

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile); //	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
     * �������� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int isOk2 = 1;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        Vector v_savefile = box.getVector("p_savefile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql = " delete from TZ_NOTICE           ";
            sql += "   where tabseq = ? and seq = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_tabseq);
            pstmt.setInt(2, v_seq);
            isOk = pstmt.executeUpdate();

            if (v_upfilecnt > 0) {

                pstmt.close();

                sql = "delete from	TZ_BOARDFILE where tabseq = ? and seq =	?";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setInt(1, v_tabseq);
                pstmt.setInt(2, v_seq);

                isOk2 = pstmt.executeUpdate();
            }

            if (isOk > 0 && isOk2 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile); //	 ÷������ ����
                }
            } else
                connMgr.rollback();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
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
     * ��������ȭ�� �󼼺���
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    public DataBox selectViewNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        String v_seq = box.getString("p_seq");
        String v_process = box.getString("p_process");

        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector = new Vector();

        try {
            connMgr = new DBConnectionManager();

            sql += " select  \n";
            sql += "   a.seq,      \n";
            sql += "   a.gubun,    \n";
            sql += "   ltrim(rtrim(startdate)) startdate,  \n";
            sql += "   ltrim(rtrim(enddate)) enddate,      \n";
            sql += "   a.addate,                     \n";
            sql += "   a.adtitle,                    \n";
            sql += "   a.adname,                     \n";
            sql += "   a.adcontent,                  \n";
            sql += "   a.cnt,                        \n";
            sql += "   a.luserid,                    \n";
            sql += "   a.ldate,                      \n";
            sql += "   a.loginyn,                    \n";
            sql += "   a.useyn, a.onoffgubun,         \n";
            sql += "   a.compcd  as compcd, 		 \n";
            sql += "   a.popwidth,                   \n";
            sql += "   a.popheight,                  \n";
            sql += "   a.popxpos,                    \n";
            sql += "   a.popypos,                    \n";
            sql += "   a.popup,                      \n";
            sql += "   a.uselist,                    \n";
            sql += "   a.useframe,                   \n";
            sql += "   a.isall,                      \n";
            sql += "   a.aduserid,                   \n";
            sql += "   a.grcodecd,                   \n";
            sql += "   a.community_yn,               \n";
            sql += "   b.realfile,                   \n";
            sql += "   b.savefile,                   \n";
            sql += "   b.fileseq                     \n";
            sql += " from TZ_NOTICE a , TZ_BOARDFILE B   \n";
            sql += "  where a.seq    = " + StringManager.makeSQL(v_seq);
            sql += "    and a.tabseq = " + v_tabseq;
            sql += "    and a.tabseq  =  b.tabseq(+) ";
            sql += "    and a.seq  =  b.seq(+) ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            // ��ȸ�� ����
            if (!v_process.equals("popupview")) {
                connMgr.executeUpdate("update TZ_NOTICE set cnt = cnt + 1 where seq = " + v_seq);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("NoticeSql = " + sql + "\r\n" + ex.getMessage());
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
     * �������� Ȩ������ ����ȭ�� ����Ʈ(�ֽ�5��)
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ(�ֽ�3�� ��ü���� ����)
     * @throws Exception
     */
    public ArrayList selectListNoticeMain(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        String v_login = "";
        String v_comp = box.getSession("comp");

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_comp = v_comp.substring(0, 4);
            v_login = "Y";
        }

        int v_tabseq = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql += " select * from ( select rownum rnum,  seq, addate, adtitle, adname, cnt, luserid, ldate from TZ_NOTICE  ";
            sql += "    where ";
            //sql += "      gubun = 'N' ";
            sql += "      tabseq = " + v_tabseq;
            sql += "      and useyn  = 'Y'";

            if (v_login.equals("Y")) { //�α����� �Ѱ��
                sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
                sql += "      and ( gubun = 'N' and compcd like '%" + v_comp + "%') ";
            } else { //�α������� �������
                sql += "      and ( loginyn = 'AL' or loginyn = 'N' )";
                sql += "      and gubun = 'Y'";
            }
            sql += "      and  ( ( popup = 'N' ) or (popup = 'Y' and uselist = 'Y') )";
            sql += "    order by seq desc  ) where rnum < 4 ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //data = new NoticeData();
                //data.setSeq(ls.getInt("seq"));
                //data.setAddate(ls.getString("addate"));
                //data.setAdtitle(ls.getString("adtitle"));
                //data.setAdname(ls.getString("adname"));
                //data.setAdcontent(ls.getString("adcontent"));
                //data.setCnt(ls.getInt("cnt"));
                //data.setLuserid(ls.getString("luserid"));
                //data.setLdate(ls.getString("ldate"));
                //list.add(data);
                dbox = ls.getDataBox();
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

    //======================Ȩ������Main���==================

    /**
     * �˾����� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �˾����� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectListNoticePopupHome(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        String v_login = "";
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_login = "Y";
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql += " select           \n";
            sql += "   rownum,        \n";
            sql += "   seq,           \n";
            sql += "   addate,        \n";
            sql += "   adtitle,       \n";
            sql += "   adname,        \n";
            sql += "   adcontent,     \n";
            sql += "   cnt,           \n";
            sql += "   uselist,       \n";
            sql += "   useframe,      \n";
            sql += "   popwidth,      \n";
            sql += "   popheight,      \n";
            sql += "   popxpos,      \n";
            sql += "   popypos,      \n";
            sql += "   luserid,       \n";
            sql += "   ldate          \n";
            sql += " from TZ_NOTICE   \n";
            sql += "    where         \n";
            sql += "      tabseq = " + v_tabseq;
            sql += "      and popup = 'Y'";
            sql += "      AND TO_CHAR (SYSDATE, 'YYYYMMDD')>=startdate and TO_CHAR (SYSDATE, 'YYYYMMDD')<=enddate ";

            if (v_login.equals("Y")) { //�α����� �Ѱ��
                sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
                sql += "      and ( grcodecd like '%" + tem_grcode + "%' )";
            } else { //�α������� �������
                //�α��� �����õǰų� ��ü�� ���õȰ��
                sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%" + tem_grcode + "%' )";
            }

            sql += "      and useyn= 'Y'";
            /*
             * sql += "      and startdate <= " +
             * StringManager.makeSQL(v_today); sql += "      and enddate   >= "
             * + StringManager.makeSQL(v_today);
             */
            sql += "    order by enddate desc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
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

    /**
     * ��ü���� ����Ʈ (�ֽ� ��ü ���� 3����)
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ü���� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectListNoticeTop(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_login = "";
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_login = "Y";
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.NoticeAdminBean selectListNoticeTop (���� �������� ��� ��ȸ) */\n");
            sql.append("SELECT  *                                                                               \n");
            sql.append("  FROM  (                                                                               \n");
            sql.append("        SELECT  SEQ                                                                     \n");
            sql.append("            ,   ADDATE                                                                  \n");
            sql.append("            ,   ADTITLE                                                                 \n");
            sql.append("            ,   ADNAME                                                                  \n");
            sql.append("            ,   CNT                                                                     \n");
            sql.append("            ,   LUSERID                                                                 \n");
            sql.append("            ,   LDATE                                                                   \n");
            sql.append("            ,   ONOFFGUBUN                                                              \n");
            sql.append("            ,   TO_CHAR(TO_DATE(SYSDATE-8),'YYYYMMDDHH24MISS') AS AGODATE               \n");
            sql.append("            ,   RANK() OVER(ORDER BY SEQ DESC) AS RNK                                   \n");
            sql.append("          FROM  TZ_NOTICE                                                               \n");
            sql.append("         WHERE  TABSEQ = ").append(v_tabseq).append("                                   \n");
            sql.append("           AND  USEYN= 'Y'                                                              \n");
            sql.append("           AND  (POPUP = 'N' OR (POPUP = 'Y' AND USELIST='Y') )                         \n");

            if (v_login.equals("Y")) { //�α����� �Ѱ��
                sql.append("           AND  ( LOGINYN = 'AL' OR LOGINYN = 'Y' )                                 \n");
                sql.append("           AND  ( GRCODECD LIKE '%").append(tem_grcode).append("%' )                \n");
            } else {
                sql.append("           AND  ( ( LOGINYN = 'AL' OR LOGINYN = 'N' ) AND GRCODECD LIKE '%").append(tem_grcode).append("%' )   \n");
            }
            sql.append("        )                                                                               \n");
            sql.append(" WHERE  RNK < 6                                                                         \n");
            sql.append(" ORDER  BY RNK                                                                          \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                //data = new NoticeData();
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
     * ��ü���� ����Ʈ (Ȩ������more)
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ü���� ����Ʈ
     * @throws Exception
     */
    public ArrayList selectListNoticeAllHome(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_login = "";
        String v_comp = box.getSession("comp");
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_comp = v_comp.substring(0, 4);
            v_login = "Y";
        }

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql += " select  \n";
            // ������ : 05.11.17 ������ : �̳��� _ rownum ���� �������� ���� �߻���Ŵ
            sql += "    top 3      \n";
            sql += "    seq,         \n";
            sql += "    addate,      \n";
            sql += "    adtitle,     \n";
            sql += "    adname,      \n";
            sql += "    cnt,         \n";
            sql += "    luserid,     \n";
            sql += "    ldate,       \n";
            sql += "    isall,       \n";
            sql += "    useyn,       \n";
            sql += "    popup,       \n";
            sql += "    loginyn,     \n";
            sql += "    gubun,       \n";
            sql += "    compcd,      \n";
            sql += "    uselist,      \n";
            sql += "    aduserid,     \n";
            sql += "    filecnt       \n";
            sql += " from             \n";
            sql += " (select          \n";
            //
            //sql += "    rownum,        \n";
            sql += "    x.seq,         \n";
            sql += "    x.addate,      \n";
            sql += "    x.adtitle,     \n";
            sql += "    x.adname,      \n";
            sql += "    x.cnt,         \n";
            sql += "    x.luserid,     \n";
            sql += "    x.ldate,       \n";
            sql += "    x.isall,       \n";
            sql += "    x.useyn,       \n";
            sql += "    x.popup,       \n";
            sql += "    x.loginyn,     \n";
            sql += "    x.gubun,       \n";
            sql += "    x.compcd,      \n";
            sql += "    x.uselist,     \n";
            sql += "    x.tabseq,      \n";
            sql += "    x.adcontent,   \n";
            sql += "    x.aduserid,    \n";
            sql += "    x.grcodecd,    \n";
            sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
            sql += "  from       \n";
            sql += "    TZ_NOTICE x ) a";
            sql += "  where isall = 'Y' ";
            sql += "      and tabseq = " + v_tabseq;
            sql += "      and useyn= 'Y'";
            sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

            if (v_login.equals("Y")) { //�α����� �Ѱ��
                sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
                sql += "      and ( compcd like '%" + v_comp + "%' )";
            } else { //�α������� �������
                //�α��� �����õǰų� ��ü�� ���õȰ��
                sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%" + tem_grcode + "%' )";
            }

            if (!v_searchtext.equals("")) { //    �˻�� ������
                v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�
                box.put("p_pageno", String.valueOf(v_pageno));
                if (v_search.equals("adtitle")) { //    �������� �˻��Ҷ�
                    sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adcontents")) { //    �������� �˻��Ҷ�
                    sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adname")) { //    �ۼ��ڷ� �˻��Ҷ�
                    sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            //          sql += "    and rownum < 4";
            sql += "    order by seq desc                                                                    ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
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

    /**
     * �Ϲ� ��������ȭ�� ����Ʈ(Ȩ������more)
     * 
     * @param box receive from the form object and session
     * @return ArrayList �������� ����Ʈ(��ü���� ����)
     * @throws Exception
     */
    public ArrayList selectListNoticeHome(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        // ������ : 05.11.16 ������ : �̳��� _ totalcount �ϱ����� ��������
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_login = "";
        String v_comp = box.getSession("comp");
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        int v_pageno = box.getInt("p_pageno");

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_comp = v_comp.substring(0, 4);
            v_login = "Y";
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            //2005.11.18_�ϰ���  : Oracle -> Mssql rownum ����
            head_sql += " select  \n";
            //head_sql += "    rownum,      \n";
            head_sql += "    seq,         \n";
            head_sql += "    addate,      \n";
            head_sql += "    adtitle,     \n";
            head_sql += "    adname,      \n";
            head_sql += "    cnt,         \n";
            head_sql += "    luserid,     \n";
            head_sql += "    ldate,       \n";
            head_sql += "    isall,       \n";
            head_sql += "    useyn,       \n";
            head_sql += "    popup,       \n";
            head_sql += "    loginyn,     \n";
            head_sql += "    gubun,       \n";
            head_sql += "    compcd,      \n";
            head_sql += "    uselist,      \n";
            head_sql += "    aduserid,     \n";
            head_sql += "    filecnt       \n";
            body_sql += " from             \n";
            body_sql += " (select          \n";
            //body_sql += "    rownum,        \n";
            body_sql += "    x.seq,         \n";
            body_sql += "    x.addate,      \n";
            body_sql += "    x.adtitle,     \n";
            body_sql += "    x.adname,      \n";
            body_sql += "    x.cnt,         \n";
            body_sql += "    x.luserid,     \n";
            body_sql += "    x.ldate,       \n";
            body_sql += "    x.isall,       \n";
            body_sql += "    x.useyn,       \n";
            body_sql += "    x.popup,       \n";
            body_sql += "    x.loginyn,     \n";
            body_sql += "    x.gubun,       \n";
            body_sql += "    x.compcd,      \n";
            body_sql += "    x.uselist,     \n";
            body_sql += "    x.tabseq,      \n";
            body_sql += "    x.adcontent,   \n";
            body_sql += "    x.aduserid,    \n";
            body_sql += "    x.grcodecd,    \n";
            body_sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
            body_sql += "  from       \n";
            body_sql += "    TZ_NOTICE x ) a";
            body_sql += "  where ";
            body_sql += "  isall = 'N' ";
            body_sql += "      and tabseq = " + v_tabseq;
            body_sql += "      and useyn= 'Y'";
            body_sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

            if (v_login.equals("Y")) { //�α����� �Ѱ��
                body_sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
                body_sql += "      and ( compcd like '%" + v_comp + "%' )";
            } else { //�α������� �������
                //�α��� �����õǰų� ��ü�� ���õȰ��
                body_sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%" + tem_grcode + "%' )";
            }

            if (!v_searchtext.equals("")) { //    �˻�� ������
                v_pageno = 1; //      �˻��� ��� ù��° �������� �ε��ȴ�
                if (v_search.equals("adtitle")) { //    �������� �˻��Ҷ�
                    body_sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adcontents")) { //    �������� �˻��Ҷ�
                    body_sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adname")) { //    �ۼ��ڷ� �˻��Ҷ�
                    body_sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += " order by seq desc ";
            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     ��ü row ���� ��ȯ�Ѵ�
            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            row = 7;
            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.

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

    //------------------------------------------------------------------------------------------------

    // ===============================ȸ�� select method===============================
    /**
     * �������� �� select
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ����Ʈ
     * @throws Exception
     */
    public ArrayList<NoticeData> selectComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        NoticeData data = null;

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = "";

        if (!s_gadmin.equals("")) {
            v_gadmin = s_gadmin.substring(0, 1);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql += " select compnm, comp from tz_comp ";
            sql += "    where comptype='2'";
            sql += "    and isused = 'Y'";

            if (v_gadmin.equals("K")) { //ȸ�������
                sql += "    and   comp IN (select comp from tz_compman where userid='" + s_userid + "' and gadmin='" + s_gadmin + "')";
            } else if (v_gadmin.equals("H")) { //�����׷������
                sql += "    and   comp IN (select comp from tz_grcomp where grcode in (select grcode from tz_grcodeman where userid = '" + s_userid + "' and gadmin='" + s_gadmin + "' ) ) ";
            }
            sql += "    order by companynm";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new NoticeData();
                data.setCompnm(ls.getString("compnm"));
                data.setComp(ls.getString("comp").substring(0, 4));
                list.add(data);
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

    // 05.12.15 �̳��� _ �߰� (�����׷캰 select)
    public ArrayList selectEduGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        EduGroupData data = null;

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = "";

        if (!s_gadmin.equals("")) {
            v_gadmin = s_gadmin.substring(0, 1);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            sql += " select grcodenm, grcode from tz_grcode ";

            if (v_gadmin.equals("H")) { //�����׷������
                sql += "    and   comp IN (select grcode from tz_grcodeman where userid = '" + s_userid + "' and gadmin='" + s_gadmin + "' )  ";
            }
            sql += "    order by grcodenm";

            ls = connMgr.executeQuery(sql);
            System.out.println("edu sql : " + sql);
            while (ls.next()) {
                data = new EduGroupData();
                data.setGrcodenm(ls.getString("grcodenm"));
                data.setGrcode(ls.getString("grcode"));
                list.add(data);
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
     * �������� default comp ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ����Ʈ
     * @throws Exception
     */

    public String selectDefalutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = "";
        String returnValue = "";

        if (!s_gadmin.equals("")) {
            v_gadmin = s_gadmin.substring(0, 1);
        }

        try {
            connMgr = new DBConnectionManager();

            sql += " select compnm, comp from tz_comp ";
            sql += "    where comptype='2'";
            sql += "    and isused = 'Y'";

            if (v_gadmin.equals("K")) { //ȸ�������
                sql += "    and   comp IN (select comp from tz_compman where userid='" + s_userid + "' and gadmin='" + s_gadmin + "')";
            } else if (v_gadmin.equals("H")) { //�����׷������
                sql += "    and   comp IN (select comp from tz_grcomp where grcode in (select grcode from tz_grcodeman where userid = '" + s_userid + "' and gadmin='" + s_gadmin + "' ) ) ";
            }
            sql += "    order by companynm";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                //data = new NoticeData();
                //data.setCompnm(ls.getString("compnm"));
                //data.setComp(ls.getString("comp").substring(0,4));
                //dbox = ls.getDataBox();
                returnValue = ls.getString("comp");
                returnValue = returnValue.substring(0, 4);
            }

            if (v_gadmin.equals("A")) {
                returnValue = "ALL";
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
        return returnValue;
    }

    /**
     * ���Ѻ� ȸ�縮��Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���Ѻ� ȸ�縮��Ʈ
     * @throws Exception
     */

    public ArrayList selectCompany(RequestBox box, String compcd) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        NoticeData data = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql += " select compnm, comp from tz_comp ";
            sql += "    where comptype='2' and substring(comp,0,4) in (" + compcd + ")";
            sql += "    and isused = 'Y'";
            sql += "    order by companynm";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new NoticeData();
                data.setCompnm(ls.getString("compnm"));
                //data.setComp(ls.getString("comp").substring(0,4));
                list.add(data);
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
     * ���Ѻ� ȸ�縮��Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���Ѻ� ȸ�縮��Ʈ
     * @throws Exception
     */

    public ArrayList selectGrcode(RequestBox box, String grcodecd) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            System.out.println("grcodecd : " + grcodecd);
            sql += " select grcodenm, grcode from tz_grcode ";
            sql += "    where grcode in (" + grcodecd + ")";
            sql += "    order by grcodenm";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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

    // ===============================ȸ�� select method end===============================

    /**
     * �������� ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   ���ε�Ǵ� ������ ������	�˰� �ڵ��ؾ��Ѵ�  --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
            System.out.println("v_newFileName : " + v_newFileName[i]);
        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
            //----------------------	�ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq),	0) from	tz_boardfile	where tabseq = " + p_tabseq + " and seq = " + p_seq;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);
                    isOk2 = pstmt2.executeUpdate();
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
        return isOk2;
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
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk3 = 1;
        String v_type = box.getString("p_type");
        int v_seq = box.getInt("p_seq");

        try {

            //----------------------   ��Խ�������������  ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

            sql3 = "delete from tz_boardfile where tabseq = " + v_tabseq + " and seq =? and fileseq = ?";
            pstmt3 = connMgr.prepareStatement(sql3);

            for (int i = 0; i < p_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));
                pstmt3.setInt(1, v_seq);
                pstmt3.setInt(2, v_fileseq);
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
     * ��������ȭ�� ��������������
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ����������
     * @throws Exception
     */
    public NoticeData selectViewPre(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        NoticeData data = null;

        int v_tabseq = box.getInt("p_tabseq");
        String v_seq = box.getString("p_seq");
        String v_gubun = box.getString("p_gubun");
        String v_gubun_query = "";

        if (v_gubun.equals("Y")) { // ��ü�����ϰ��
            v_gubun_query = "('Y')";
        } else { // ��ü�������ƴҰ��(�Ϲ�,�˾�)
            v_gubun_query = "('N','P')";
        }

        try {
            connMgr = new DBConnectionManager();

            sql += " select seq,gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_NOTICE ";
            sql += "  where gubun in " + v_gubun_query;
            sql += "    and tabseq = " + v_tabseq;
            sql += "    and seq   <  " + StringManager.makeSQL(v_seq);
            sql += "  order by seq desc                                                                       ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new NoticeData();
                data.setSeq(ls.getInt("seq"));
                data.setGubun(ls.getString("gubun"));
                data.setAddate(ls.getString("addate"));
                data.setAdtitle(ls.getString("adtitle"));
                data.setAdname(ls.getString("adname"));
                data.setAdcontent(ls.getString("adcontent"));
                data.setCnt(ls.getInt("cnt"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
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
        return data;
    }

    /**
     * ��������ȭ�� ��������������
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ����������
     * @throws Exception
     */
    public NoticeData selectViewNext(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        NoticeData data = null;

        int v_tabseq = box.getInt("p_tabseq");
        String v_seq = box.getString("p_seq");
        String v_gubun = box.getString("p_gubun");

        String v_gubun_query = "";

        if (v_gubun.equals("Y")) { // ��ü�����ϰ��
            v_gubun_query = "('Y')";
        } else { // ��ü�������ƴҰ��(�Ϲ�,�˾�)
            v_gubun_query = "('N','P')";
        }

        try {
            connMgr = new DBConnectionManager();

            sql += " select seq, gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_NOTICE ";
            sql += "  where gubun in " + v_gubun_query;
            sql += "    and tabseq = " + v_tabseq;
            sql += "    and seq  >  " + StringManager.makeSQL(v_seq);
            sql += "  order by seq asc                                                                        ";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new NoticeData();
                data.setSeq(ls.getInt("seq"));
                data.setGubun(ls.getString("gubun"));
                data.setAddate(ls.getString("addate"));
                data.setAdtitle(ls.getString("adtitle"));
                data.setAdname(ls.getString("adname"));
                data.setAdcontent(ls.getString("adcontent"));
                data.setCnt(ls.getInt("cnt"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
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
        return data;
    }

    /**
     * ���� ��Ƽ�� �̸�����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public String popupView(RequestBox box) throws Exception {

        String v_content = StringUtil.removeTag(box.getString("p_content"));

        /*********************************************************************************************/
        // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
        try {
            v_content = (String) NamoMime.setNamoContent(v_content);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
        /*********************************************************************************************/

        return v_content;
    }
}
