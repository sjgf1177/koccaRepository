package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

/**
 *
 * ȸ�� �˻� bean
 *
 * @author ������
 * @since 2004. 12. 20
 *
 */
@SuppressWarnings("unchecked")
public class MemberSearchBean {

    // private

    public MemberSearchBean() {
    }

    /**
     * ȸ���˻���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList searchMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        StringBuilder sb = new StringBuilder();

        String ss_grcode = box.getString("s_grcode"); // �����׷�
        String ss_membergubun = box.getString("s_membergubun"); // ȸ���з�
        String ss_userid = box.getString("s_userid"); // ID
        String ss_username = box.getString("s_username"); // ����
        String ss_state = box.getString("s_state"); // ȸ������
        String ss_ismailing = box.getString("s_ismailing"); // ���Ż���
        String ss_regstartdate = box.getString("s_regstartdate"); // ���Խ�����
        String ss_regenddate = box.getString("s_regenddate"); // ����������

        String v_orderColumn = box.getString("p_orderColumn"); // ������ �÷���
        String v_orderType = box.getString("p_orderType"); // ������ ����

        int row = 100; // �������� row ����
        int v_pageno = box.getInt("p_pageno");
        int total_row_count = 0;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sb.append("SELECT   COUNT(A.USERID) OVER() AS TOTCNT  \n");
            sb.append("     ,   A.MEMBERGUBUN           \n");
            sb.append("     ,   A.USERID                \n");
            sb.append("     ,   A.NAME                  \n");
            sb.append("     ,   CRYPTO.DEC('normal', A.EMAIL) AS EMAIL  \n");
            sb.append("     ,   A.PWD                   \n");
            sb.append("     ,   A.GRCODE                \n");
            sb.append("     ,   A.RESNO1                \n");
            sb.append("     ,   A.RESNO2                \n");
            sb.append("     ,   DECODE(A.STATE, 'Y', '����', 'Ż��') AS STATE   \n");
            sb.append("     ,   A.INDATE                \n");
            sb.append("     ,   A.LDATE                 \n");
            sb.append("     ,   B.CODENM AS MEMBERGUBUNNM   \n");
            sb.append("  FROM   TZ_MEMBER A             \n");
            sb.append("     ,   TZ_CODE B               \n");
            sb.append(" WHERE   B.GUBUN = '0029'        \n");
            sb.append("   AND   CODE = A.MEMBERGUBUN    \n");

            if (!ss_grcode.equals("") && !ss_grcode.equals("ALL") && !ss_grcode.equals("----")) { // �����׷�
                sb.append("   AND   GRCODE = '" + ss_grcode + "'\n");
            }

            if (!ss_membergubun.equals("ALL")) { // ȸ���з�
                sb.append("   AND   MEMBERGUBUN = '" + ss_membergubun + "'  \n");
            }

            if (!ss_userid.equals("")) { // ID
                sb.append("   AND   USERID LIKE '%" + ss_userid + "%'   \n");
            }

            if (!ss_username.equals("")) { // ����
                sb.append("   AND   NAME LIKE '%" + ss_username + "%'   \n");
            }

            if (!ss_regstartdate.equals("") || !ss_regenddate.equals("")) { // ������
                sb.append("   AND   SUBSTR(A.INDATE, 0, 8) BETWEEN '" + ss_regstartdate + "' AND '" + ss_regenddate + "'    \n");
            }

            if (!ss_state.equals("ALL")) { // ȸ������
                sb.append("   AND   A.STATE = '" + ss_state + "'  \n");
            }

            if (!ss_ismailing.equals("ALL")) { // ���Ż���
                if (ss_ismailing.equals("Y")) {
                    sb.append("   AND   (A.ISMAILING = 'Y' OR A.ISMAILING IS NULL)  \n");
                } else if (ss_ismailing.equals("N")) {
                    sb.append("   AND   A.ISMAILING = 'N'   \n");
                }
            }

            if (v_orderColumn.equals("")) {
                sb.append(" ORDER   BY A.MEMBERGUBUN, A.NAME");
            } else {
                sb.append(" ORDER   BY " + v_orderColumn + v_orderType);
            }

            ls = connMgr.executeQuery(sb.toString());

            System.out.println("������ > ȯ�漳�� > ȸ����ȸ query\n" + sb.toString());

            // BoardPaging.getTotalRow(connMgr, count_sql);
            if (ls.next()) {
                total_row_count = ls.getInt("TOTCNT");
            }

            System.out.println("\ntotal_row_count : " + total_row_count);

            ls.moveFirst();

            ls.setPageSize(row); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�
            // int total_row_count = ls.getTotalCount(); //��ü row���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                // if (dbox.getString("d_grcode").equals("N000001")) {
                // ====================================================
                // �������� ��ȣȭ-HTJ
                // EncryptUtil encryptUtil = new
                // EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
                // if (!dbox.getString("d_email").equals(""))
                // dbox.put("d_email",encryptUtil.decrypt(dbox.getString("d_email")));
                // if (!dbox.getString("d_resno2").equals(""))
                // dbox.put("d_resno",dbox.getString("d_resno1")+"-"+encryptUtil.decrypt(dbox.getString("d_resno2")));
                // ====================================================
                // }

                dbox.put("d_Dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_Totalpagecount", new Integer(total_page_count));

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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
     * �������� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox selectMemberInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        String sql = "";
        String v_userid = box.getString("p_userid");
        String v_grcode = "";

        if ("ALL".equals(box.getString("s_grcode"))) {
            v_grcode = box.getString("p_grcode");
        } else {
            v_grcode = box.getString("s_grcode");
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select a.membergubun, a.userid, a.name, a.eng_name, a.eng_name, crypto.dec('normal',a.email) email, a.pwd,         ";
            sql += "			substr( a.resno, 1, 6 ) || '-' || substr( a.resno, 7, 13 ) resno,                    ";
            sql += " 		case a.state When 'Y' Then '����' Else 'Ż��' End as state, a.state statecd,   a.validation,        ";
            sql += "			a.post1, a.post2, a.addr, a.addr2, crypto.dec('normal',a.hometel) hometel, a.comptel, crypto.dec('normal',a.handphone) handphone , a.comptext, ";
            sql += "			a.comp_post1, a.comp_post2, a.comp_addr1, a.comp_addr2, a.jikup, a.degree,        ";
            sql += "			a.ismailing, a.isopening, a.islettering,  a.registgubun, a.indate, a.ldate        ";
            sql += "	   from	tz_member a ";
            sql += "   where a.userid = '" + v_userid + "'";
            sql += "   and a.grcode = '" + v_grcode + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();

                // if (ss_grcode.equals("N000001")) {
                // ====================================================
                // �������� ��ȣȭ - HTJ
                /*
                 * EncryptUtil encryptUtil = new
                 * EncryptUtil(Constants.APP_KEY,Constants.APP_IV); if
                 * (!dbox.getString("d_resno2").equals(""))
                 * dbox.put("d_resno2",encryptUtil
                 * .decrypt(dbox.getString("d_resno2"))); if
                 * (!dbox.getString("d_email").equals(""))
                 * dbox.put("d_email",encryptUtil
                 * .decrypt(dbox.getString("d_email"))); if
                 * (!dbox.getString("d_hometel").equals(""))
                 * dbox.put("d_hometel"
                 * ,encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                 * (!dbox.getString("d_handphone").equals(""))
                 * dbox.put("d_handphone"
                 * ,encryptUtil.decrypt(dbox.getString("d_handphone"))); if
                 * (!dbox.getString("d_addr2").equals(""))
                 * dbox.put("d_addr2",encryptUtil
                 * .decrypt(dbox.getString("d_addr2")));
                 */
                // ====================================================
                // }
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
        return dbox;
    }

    /**
     * �λ�DB�˻���� ����Ʈ �������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList searchMemberListExcel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmtInsert = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        String sql = "";
        String sql2 = "";

        String ss_grcode = box.getString("s_grcode"); // �����׷�
        String ss_membergubun = box.getString("s_membergubun"); // ȸ���з�
        String ss_userid = box.getString("s_userid"); // ID
        String ss_username = box.getString("s_username"); // ����
        String ss_state = box.getString("s_state"); // ȸ������
        String ss_ismailing = box.getString("s_ismailing"); // ���λ���
        String ss_regstartdate = box.getString("s_regstartdate"); // �����Ͻ���
        String ss_regenddate = box.getString("s_regenddate"); // ����������

        // �α����� ����ϱ�
        String v_userid = box.getSession("userid");
        String v_srchword = box.getString("p_srchword"); // �˻���

        String v_orderColumn = box.getString("p_orderColumn"); // ������ �÷���
        String v_orderType = box.getString("p_orderType"); // ������ ����

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.membergubun, case when  a.membergubun = 'P' then  '����'  when  membergubun = 'C'  ";
            sql += "	 	then  '���' when  membergubun = 'U' then  '���б�' else '-' end   as membergubunnm,  ";
            sql += "          a.userid, a.name, a.eng_name, crypto.dec('normal',a.email) email, a.grcode,   ";
            // sql+=
            // "			substr( a.resno, 1, 6 ) || '-' || substr( a.resno, 7, 13 ) resno,              ";
            // sql+= "			a.resno1, a.resno2,              ";
            sql += " 		case a.state When 'Y' Then '����' Else 'Ż��' End as state, a.state stateCd,  a.validation, ";
            sql += "			a.post1, a.post2, a.addr, crypto.dec('normal',a.hometel) hometel, a.comptel, crypto.dec('normal',a.handphone) handphone, comptext,     ";
            sql += "			a.comp_post1, a.comp_post2, a.comp_addr1, a.comp_addr2, a.jikup, a.degree, a.jikwi,";
            sql += "         (select codenm from tz_code where gubun = '0070' and code = a.jikup) jikupnm,  ";
            sql += "         (select codenm from tz_code where gubun = '0069' and code = a.degree) degreenm,";
            sql += "			a.ismailing, a.isopening, a.islettering,  a.registgubun, a.indate, a.ldate ";
            sql += "	from  	tz_member a ";
            sql += "	where	1=1 ";

            if (!ss_grcode.equals("") && !ss_grcode.equals("ALL") && !ss_grcode.equals("----")) { // �����׷�
                // �˻�
                sql += " and grcode = '" + ss_grcode + "'";
            }

            if (!ss_membergubun.equals("ALL")) { // ȸ���з� �˻�
                sql += "and membergubun = '" + ss_membergubun + "' ";
            }
            if (!ss_userid.equals("")) { // ID �˻�
                sql += " and userid like '%" + ss_userid + "%'";
            }
            if (!ss_username.equals("")) { // ���� �˻�
                sql += " and name like '%" + ss_username + "%'";
            }
            if (!ss_state.equals("ALL")) { // ȸ������
                sql += " and state = '" + ss_state + "'";
            }
            if (!ss_regstartdate.equals("")) { // ������ (����) �˻�
                sql += " and substr(INDATE,0,8) >= '" + ss_regstartdate + "'";
            }
            if (!ss_regenddate.equals("")) { // ������(����) �˻�
                sql += " and substr(INDATE,0,8) <= '" + ss_regenddate + "'";
            }
            if (!ss_ismailing.equals("ALL")) { // ���Ż���
                if (ss_ismailing.equals("Y")) {
                    sql += " and (a.ismailing = 'Y' or a.ismailing is null) ";
                } else if (ss_ismailing.equals("N")) {
                    sql += " and a.ismailing = 'N' ";
                }
            }

            if (v_orderColumn.equals("")) {
                sql += " order by userid, name";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);

            double rowcnt = 0;

            while (ls.next()) {
                dbox = ls.getDataBox();
                rowcnt++;
                // if (dbox.getString("d_grcode").equals("N000001")) {
                // ====================================================
                // �������� ��ȣȭ - HTJ
                /*
                 * EncryptUtil encryptUtil = new
                 * EncryptUtil(Constants.APP_KEY,Constants.APP_IV); if
                 * (!dbox.getString("d_email").equals(""))
                 * dbox.put("d_email",encryptUtil
                 * .decrypt(dbox.getString("d_email"))); // if
                 * (!dbox.getString("d_addr2").equals(""))
                 * dbox.put("d_addr2",encryptUtil
                 * .decrypt(dbox.getString("d_addr2"))); if
                 * (!dbox.getString("d_hometel").equals(""))
                 * dbox.put("d_hometel"
                 * ,encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                 * (!dbox.getString("d_handphone").equals(""))
                 * dbox.put("d_handphone"
                 * ,encryptUtil.decrypt(dbox.getString("d_handphone")));
                 */
                // if (!dbox.getString("d_resno2").equals(""))
                // dbox.put("d_resno",dbox.getString("d_resno1")+"-"+encryptUtil.decrypt(dbox.getString("d_resno2")));

                // ====================================================
                // }

                list.add(dbox);
            }

            // �α������� ����
            sql2 = " Insert into tz_srchmemberlog (logdate,userid,srchword,rowcnt) ";
            sql2 += " values(to_char(sysdate, 'YYYYMMDDHH24MISS') , ? , ? , ?)";

            pstmtInsert = connMgr.prepareStatement(sql2);

            pstmtInsert.setString(1, v_userid);
            pstmtInsert.setString(2, v_srchword);
            pstmtInsert.setDouble(3, rowcnt);
            pstmtInsert.executeUpdate();

            // connMgr.commit();
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
            if (pstmtInsert != null) {
                try {
                    pstmtInsert.close();
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
        return list;
    }

    /**
     * �λ�DB�˻���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectLog(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");

        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        int row = 10; // �������� row ����
        String v_userid = box.get("chkbox");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            head_sql = " select a.logdate, a.userid,B.name,a.srchword, a.rowcnt\n";
            body_sql += "   from tz_srchmemberlog a, tz_member B\nWHERE A.USERID = B.USERID\n";
            body_sql += " and a.userid='" + v_userid + "'\n";
            order_sql += "  order by a.logdate desc                                        ";
            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�
            // int total_row_count = ls.getTotalCount(); //��ü row���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_Dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_Totalpagecount", new Integer(total_page_count));

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
     * �������� ����
     * 
     * @param box receive from the form object
     * @return is_Ok 1 : ok 2 : fail
     * @throws Exception
     */
    /*
     * public int setModify(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; PreparedStatement pstmt = null;
     * String sql = ""; int is_Ok = 0;
     * 
     * String v_userid = box.getString("p_userid"); String v_state =
     * box.getString("p_state");
     * 
     * try { connMgr = new DBConnectionManager();
     * 
     * sql = " update TZ_MEMBER set state = ? "; sql +=
     * "  where userid = ?                   "; pstmt =
     * connMgr.prepareStatement(sql);
     * 
     * pstmt.setString(1, v_state); pstmt.setString(2, v_userid); is_Ok =
     * pstmt.executeUpdate(); } catch(Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, box, sql); throw new
     * Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally {
     * if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
     * if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception
     * e10) {} } }
     * 
     * return is_Ok; }
     */
    public int setModify(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        String sql1 = "";
        int is_Ok = 0;

        String v_userid = box.getString("p_userid");
        String v_tmpemail = "";
        String v_tmpehometel = "";
        String v_tmphandphone = "";

        ListSet ls = null;
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " select * from tz_member";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                v_userid = ls.getString("userid");
                dbox = ls.getDataBox();
                // ====================================================
                // �������� ��ȣȭ
                EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

                if (!dbox.getString("d_email").equals(""))
                    v_tmpemail = encryptUtil.decrypt(dbox.getString("d_email"));
                if (!dbox.getString("d_hometel").equals(""))
                    v_tmpehometel = encryptUtil.decrypt(dbox.getString("d_hometel"));
                if (!dbox.getString("d_handphone").equals(""))
                    v_tmphandphone = encryptUtil.decrypt(dbox.getString("d_handphone"));
                // if (!dbox.getString("d_addr2").equals("")) v_tmpaddr2 =
                // encryptUtil.decrypt(dbox.getString("d_addr2"));
                // ====================================================

                sql1 = "update TZ_MEMBER set email = '" + v_tmpemail + "' , hometel = '" + v_tmpehometel + "' , handphone = '" + v_tmphandphone
                        + "' ";
                sql1 += " where userid = '" + v_userid + "'      ";
                // pstmt = connMgr.prepareStatement(sql);
                System.out.println("sql1 �̾�....=" + sql1);
                is_Ok = connMgr.executeUpdate(sql1);

                /*
                 * pstmt.setString(1, v_tmpemail); pstmt.setString(2,
                 * v_tmpehometel); pstmt.setString(3, v_tmphandphone);
                 * pstmt.setString(4, v_tmpaddr2); pstmt.setString(5, v_userid);
                 * is_Ok = pstmt.executeUpdate();
                 */

            }

            if (is_Ok > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            is_Ok = 0;
            connMgr.rollback();
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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;

    }

    /**
     * ȸ�� ��й�ȣ �ʱ�ȭ
     * 
     * @param box receive from the form object and session
     * @return int ����ó������(1 : ����, 0 : ����)
     */
    public int resetUserPwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // ListSet ls = null;
        PreparedStatement pstmt = null;
        StringBuffer sbSQL = new StringBuffer("");
        int isOk = 1;
        String v_userid = box.getString("p_userid");
        String v_pwd = "";
        String v_grcode = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            v_grcode = box.getString("s_grcode");

            v_pwd = HashCipher.createHash(v_userid); // ���̵�� �����ϰ� ��й�ȣ ����

            sbSQL.setLength(0);
            sbSQL.append("/* resetUserPwd (����� ��й�ȣ �ʱ�ȭ ����) */\n");
            sbSQL.append("UPDATE  TZ_MEMBER    \n");
            sbSQL.append("   SET  PWD = '").append(v_pwd).append("'             \n");
            sbSQL.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n");
            sbSQL.append(" WHERE  USERID = '").append( v_userid ).append( "'    \n");
            sbSQL.append("   AND  GRCODE = '").append( v_grcode ).append("'     \n");

            pstmt = connMgr.prepareStatement(sbSQL.toString());

            isOk = pstmt.executeUpdate();
            pstmt.close();
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception ex) {
                }
            }
            isOk = 0;

            ErrorManager.getErrorStackTrace(e, box, "");
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
            /*
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            */
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e) {
                }
            }
        }

        return isOk;
    }
    
    /**
     * ���� ���ſ��� ����
     * @param box
     * @return
     * @throws Exception
     */
    public int changeUserMailing(RequestBox box) throws Exception {
    	DBConnectionManager connMgr = null;
    	PreparedStatement pstmt = null;
    	StringBuffer sbSQL = new StringBuffer("");
    	int isOk = 1;
    	String v_userid = box.getString("p_userid");
    	String v_ismailing = box.getString("p_ismailing");
    	String v_grcode = "";
    	
    	try {
    		connMgr = new DBConnectionManager();
    		connMgr.setAutoCommit(false);
    		
    		v_grcode = box.getString("s_grcode");
    		
    		
    		sbSQL.setLength(0);
    		sbSQL.append("/* changeUserMailing (����� ���ϼ��ſ��� ���� ����) */\n");
    		sbSQL.append("UPDATE  TZ_MEMBER    \n");
    		sbSQL.append("   SET  ISMAILING = '").append(v_ismailing).append("' \n");
    		sbSQL.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n");
    		sbSQL.append(" WHERE  USERID = '").append( v_userid ).append( "'    \n");
    		sbSQL.append("   AND  GRCODE = '").append( v_grcode ).append("'     \n");
    		
    		pstmt = connMgr.prepareStatement(sbSQL.toString());
    		
    		isOk = pstmt.executeUpdate();
    		pstmt.close();
    		if (isOk > 0) {
    			connMgr.commit();
    		} else {
    			connMgr.rollback();
    		}
    		
    	} catch (Exception e) {
    		if (connMgr != null) {
    			try {
    				connMgr.rollback();
    			} catch (Exception ex) {
    			}
    		}
    		isOk = 0;
    		
    		ErrorManager.getErrorStackTrace(e, box, "");
    		throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
    	} finally {
    		/*
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
    		 */
    		if (pstmt != null) {
    			try {
    				pstmt.close();
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.setAutoCommit(true);
    			} catch (Exception e) {
    			}
    		}
    		if (connMgr != null) {
    			try {
    				connMgr.freeConnection();
    			} catch (Exception e) {
    			}
    		}
    	}
    	
    	return isOk;
    }

}