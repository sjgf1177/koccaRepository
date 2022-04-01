//**********************************************************
//  1. 제      목: 가족회원가입 빈
//  2. 프로그램명: UserRegBean.java
//  3. 개      요: 가족회원가입 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 2. 15
//  7. 수      정:
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
 *외주업체관리
 *<p>
 * 제목:UserRegBean.java
 * </p>
 *<p>
 * 설명:외주업체관리 빈
 * </p>
 *<p>
 * Copyright: Copright(c)2004
 * </p>
 *<p>
 * Company: VLC
 * </p>
 * 
 * @author 이창훈
 *@version 1.0
 */

public class UserRegBean {

    public UserRegBean() {
    }

    /**
     * 외주업체리스트
     * 
     * @param box
     *            receive from the form object and session
     * @return ArrayList 외주업체 리스트
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
            } // 꼭 닫아준다
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
     * 선택된 회사정보 상세내용
     * 
     * @param box
     *            receive from the form object and session
     * @return DataBox 외주업체 상세정보
     */
    @SuppressWarnings("unchecked")
    public DataBox selectComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_seq = "";

        if (box.getSession("gadmin").equals("S1")) {
            // 외주업체 담당자라면 해당 회사정보코드를 알아낸다.
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

            // 관리자가 외주업체계정으로 접속했을경우의 처리
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
     * 새로운 외주업체 정보 등록
     * 
     * @param box
     *            receive from the form object and session
     * @return int 외주업체 등록 정상처리여부(1:update success,0:update fail)
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

            // ---------------------- 외주업체 seq번호를 가져온다 ----------------------------
            sql = "select NVL(max(cpseq), '00000') from tz_cpinfo";
            ls = connMgr.executeQuery(sql);
            ls.next();
            String v_seq = CodeConfigBean.addZero(StringManager.toInt(ls.getString(1)) + 1, 5);
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// 외주업체정보 table 에 입력 ///////////////////////////////////////////////////////////////////
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

            // //////////////////////////////// member table 에 회원정보 입력 ///////////////////////////////////////////////////////////////////
            sql2 = "insert into tz_member(userid, pwd, name, email, comptel, usergubun)";
            sql2 += " values (?, ?, ?, ?, ?, 'O') ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);
            pstmt2.setString(2, v_pwd);
            pstmt2.setString(3, v_name);
            pstmt2.setString(4, v_email);
            pstmt2.setString(5, v_comtel);

            isOk2 = pstmt2.executeUpdate();

            // //////////////////////////////// tz_manager table 에 회원정보 입력 ///////////////////////////////////////////////////////////////////
            sql3 = "insert into tz_manager(userid, gadmin, fmon, tmon, commented, luserid, ldate)";
            sql3 += " values (?, 'S1', to_char(sysdate, 'YYYYMMDD'), to_char(sysdate+1825, 'YYYYMMDD'), '";
            sql3 += v_cpnm + " 외주업체 담당자', ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

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
     * 선택된 회사정보 수정
     * 
     * @param box
     *            receive from the form object and session
     * @return int 외주업체 수정 정상처리여부(1:update success,0:update fail)
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

            // 외주업체정보 table 수정
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

            // member table 회원정보 수정
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
     * 선택된 회사정보 삭제
     * 
     * @param box
     *            receive from the form object and session
     * @return int 외주업체 삭제 정상처리여부(1:update success,0:update fail)
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

            // 유저테이블 삭제
            sql2 = "delete from tz_member where userid = ?";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_userid);

            isOk2 = pstmt2.executeUpdate();

            // manager테이블 삭제
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
     * 등록된 유저아이디인지 체크한다.
     * 
     * @param box
     *            receive from the form object and session
     * @return DataBox 카운트
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
     * 외주업체회사코드 조회
     * 
     * @param p_userid
     *            외주업체담당자 아이디
     * @return String 외주업체코드
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
