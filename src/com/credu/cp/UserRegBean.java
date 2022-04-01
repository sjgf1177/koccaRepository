//**********************************************************
//  1. ��      ��: ����ȸ������ ��
//  2. ���α׷���: UserRegBean.java
//  3. ��      ��: ����ȸ������ ���α׷�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ��â�� 2005. 2. 15
//  7. ��      ��:
//**********************************************************

package com.credu.cp;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeConfigBean;

/**
 *���־�ü����
 *<p>
 * ����:UserRegBean.java
 * </p>
 *<p>
 * ����:���־�ü���� ��
 * </p>
 *<p>
 * Copyright: Copright(c)2004
 * </p>
 *<p>
 * Company: VLC
 * </p>
 * 
 * @author ��â��
 *@version 1.0
 */

public class UserRegBean {

    public UserRegBean() {
    }

    /**
     * ���־�ü����Ʈ
     * 
     * @param box
     *            receive from the form object and session
     * @return ArrayList ���־�ü ����Ʈ
     */
    public ArrayList<DataBox> selectCompList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        
        DataBox dbox = null;

        // int v_pageno = box.getInt("p_pageno");
        // int v_pageno = 1;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("SELECT  A.CPSEQ \n");
            sql.append("    ,   A.CPNM  \n");
            sql.append("    ,   A.CPRESNO   \n");
            sql.append("    ,   A.HOMESITE  \n");
            sql.append("    ,   A.ADDRESS   \n");
            sql.append("    ,   A.LDATE     \n");
            sql.append("    ,   B.USERID    \n");
            sql.append("    ,   B.NAME      \n");
            sql.append("    ,   B.EMAIL     \n");
            sql.append("    ,   B.COMPTEL   \n");
            sql.append("  FROM  TZ_CPINFO A \n");
            sql.append("    ,   TZ_MEMBER B \n");
            sql.append(" WHERE  A.USERID = B.USERID \n");
            sql.append(" ORDER  BY A.CPNM ASC \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            } // �� �ݾ��ش�
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
     * ���õ� ȸ������ �󼼳���
     * 
     * @param box
     *            receive from the form object and session
     * @return DataBox ���־�ü ������
     */
    @SuppressWarnings("unchecked")
    public DataBox selectComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_seq = "";

        if (box.getSession("gadmin").equals("S1")) {
            // ���־�ü ����ڶ�� �ش� ȸ�������ڵ带 �˾Ƴ���.
            v_seq = this.selectCPseq(box.getSession("userid"));
            v_seq = CodeConfigBean.addZero(StringManager.toInt(v_seq), 5);
        } else {
            v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select a.cpseq, a.cpnm, a.cpresno, a.homesite, a.address, a.ldate, b.userid, b.pwd, b.name, b.email, b.comptel ";
            sql += " from tz_cpinfo a, tz_member b ";
            sql += " where a.userid = b.userid and a.cpseq = " + SQLString.Format(v_seq);

            ls = connMgr.executeQuery(sql);

            // �����ڰ� ���־�ü�������� ������������� ó��
            box.put("vflag", "0");

            while (ls.next()) {
                dbox = ls.getDataBox();
                box.put("vflag", "1");
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
     * ���ο� ���־�ü ���� ���
     * 
     * @param box
     *            receive from the form object and session
     * @return int ���־�ü ��� ����ó������(1:update success,0:update fail)
     */
    public int insertComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        String v_cpresno = box.getString("p_cpresno1") + "-" + box.getString("p_cpresno2") + "-" + box.getString("p_cpresno3");
        String v_cpnm = box.getString("p_cpnm");
        String v_homesite = box.getString("p_homesite");
        String v_address = box.getString("p_address");
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getString("p_name");
        String v_email = box.getString("p_email");
        String v_comtel = box.getString("p_comtel1") + "-" + box.getString("p_comtel2") + "-" + box.getString("p_comtel3");

        String s_userid = box.getSession("userid");
        // String s_usernm = box.getSession("username");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- ���־�ü seq��ȣ�� �����´� ----------------------------
            sql = "select NVL(max(cpseq), '00000') from tz_cpinfo";
            ls = connMgr.executeQuery(sql);
            ls.next();
            String v_seq = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 5);
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// ���־�ü���� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql1 = "insert into tz_cpinfo(cpseq, userid, cpresno, cpnm, homesite, address, luserid, ldate)";
            sql1 += " values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_seq);
            pstmt1.setString(2, v_userid);
            pstmt1.setString(3, v_cpresno);
            pstmt1.setString(4, v_cpnm);
            pstmt1.setString(5, v_homesite);
            pstmt1.setString(6, v_address);
            pstmt1.setString(7, s_userid);

            isOk1 = pstmt1.executeUpdate();

            // //////////////////////////////// member table �� ȸ������ �Է� ///////////////////////////////////////////////////////////////////
            sql2 = "insert into tz_member(userid, pwd, name, email, comptel, usergubun)";
            sql2 += " values (?, ?, ?, ?, ?, 'O') ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);
            pstmt2.setString(2, v_pwd);
            pstmt2.setString(3, v_name);
            pstmt2.setString(4, v_email);
            pstmt2.setString(5, v_comtel);

            isOk2 = pstmt2.executeUpdate();

            // //////////////////////////////// tz_manager table �� ȸ������ �Է� ///////////////////////////////////////////////////////////////////
            sql3 = "insert into tz_manager(userid, gadmin, fmon, tmon, commented, luserid, ldate)";
            sql3 += " values (?, 'S1', to_char(sysdate, 'YYYYMMDD'), to_char(sysdate+1825, 'YYYYMMDD'), '";
            sql3 += v_cpnm + " ���־�ü �����', ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt3.setString(1, v_userid);
            pstmt3.setString(2, s_userid);

            isOk3 = pstmt3.executeUpdate();

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
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
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
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
            if (pstmt3 != null) {
                try {
                    pstmt2.close();
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
        return isOk1 * isOk2 * isOk2;
    }

    /**
     * ���õ� ȸ������ ����
     * 
     * @param box
     *            receive from the form object and session
     * @return int ���־�ü ���� ����ó������(1:update success,0:update fail)
     */
    public int updateComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        // String v_seq = box.getString("p_cpseq");
        String v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);
        String v_cpresno = box.getString("p_cpresno1") + "-" + box.getString("p_cpresno2") + "-" + box.getString("p_cpresno3");
        String v_cpnm = box.getString("p_cpnm");
        String v_homesite = box.getString("p_homesite");
        String v_address = box.getString("p_address");
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getString("p_name");
        String v_email = box.getString("p_email");
        String v_comtel = box.getString("p_comtel1") + "-" + box.getString("p_comtel2") + "-" + box.getString("p_comtel3");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ���־�ü���� table ����
            sql1 = "update tz_cpinfo set cpresno = ?, cpnm = ?, homesite = ?, address = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += "  where cpseq = ? ";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_cpresno);
            pstmt1.setString(2, v_cpnm);
            pstmt1.setString(3, v_homesite);
            pstmt1.setString(4, v_address);
            pstmt1.setString(5, s_userid);
            pstmt1.setString(6, v_seq);

            isOk1 = pstmt1.executeUpdate();

            // member table ȸ������ ����
            sql2 = "update tz_member set pwd =?, name =?, email =?, comptel =?";
            sql2 += " where userid = ? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_pwd);
            pstmt2.setString(2, v_name);
            pstmt2.setString(3, v_email);
            pstmt2.setString(4, v_comtel);
            pstmt2.setString(5, v_userid);

            isOk2 = pstmt2.executeUpdate();

            if (isOk1 > 0 && isOk2 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
            Log.info.println(this, box, "update process to " + v_seq);
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
     * ���õ� ȸ������ ����
     * 
     * @param box
     *            receive from the form object and session
     * @return int ���־�ü ���� ����ó������(1:update success,0:update fail)
     */
    public int deleteComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        String v_seq = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_cpseq")), 5);

        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = "delete from tz_cpinfo where cpseq = ?";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_seq);

            isOk1 = pstmt1.executeUpdate();

            // �������̺� ����
            sql2 = "delete from tz_member where userid = ?";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);

            isOk2 = pstmt2.executeUpdate();

            // manager���̺� ����
            sql3 = "delete from tz_manager where userid = ? and gadmin = 'S1' ";

            pstmt3 = connMgr.prepareStatement(sql3);

            pstmt3.setString(1, v_userid);

            isOk3 = pstmt3.executeUpdate();

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }

            Log.info.println(this, box, "delete process to " + v_seq);
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
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
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
        return isOk1 * isOk2 * isOk3;
    }

    /**
     * ��ϵ� �������̵����� üũ�Ѵ�.
     * 
     * @param box
     *            receive from the form object and session
     * @return DataBox ī��Ʈ
     */
    public DataBox userCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_resno = box.getString("p_resno");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(userid) as cnt from tz_member where userid = " + SQLString.Format(v_userid);
            sql += " and substr(resno,1,6) = " + SQLString.Format(v_resno);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * ���־�üȸ���ڵ� ��ȸ
     * 
     * @param p_userid
     *            ���־�ü����� ���̵�
     * @return String ���־�ü�ڵ�
     */
    public String selectCPseq(String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_cpseq = "";

        try {
            connMgr = new DBConnectionManager();

            sql = "select cpseq, cpnm ";
            sql += " from tz_cpinfo ";
            sql += " where userid = " + SQLString.Format(p_userid);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                // dbox = ls.getDataBox();
                v_cpseq = ls.getString("cpseq");
            }
        } catch (Exception ex) {
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
        return v_cpseq;
    }
}
