// **********************************************************
// 1. 제 목:
// 2. 프로그램명: OverLapBean.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: 이창훈 2005. 7. 16
// 7. 수 정:
// **********************************************************
package com.credu.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class OverLapBean {

    public OverLapBean() {
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param userid USER ID
     * @return String Return @ 신청가능하면 true (중복안됨) @ 신청불가능하면 false(중복됨)
     */
    public String isExitMember(String userid) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";
        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitMember(connMgr, userid);
        } catch (Exception ex) {
        }

        finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param userid USER ID
     * @return String Return @ 신청가능하면 true (중복안됨) @ 신청불가능하면 false(중복됨)
     */
    public String isExitMember(DBConnectionManager connMgr, String userid) throws Exception {
        PreparedStatement pstmt1 = null;
        ResultSet rs1 = null;
        String sql = "";
        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            sql += "select count(*) CNT from tz_member where userid = '" + userid + "' \n";
            pstmt1 = connMgr.prepareStatement(sql);
            rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                cnt = rs1.getInt("CNT");
                if (cnt > 0) {
                    errvalue = "0"; // 정상
                } else {
                    errvalue = "1"; // 인사DB에 존재하지 않습니다.
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
        }
        return errvalue;
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param box receive from the form object and session
     * @return String Return @ 신청가능하면 true (중복안됨) @ 신청불가능하면 false(중복됨)
     */
    public String isExitMember(String userid, String isretired, String isemtpty, String isstoped) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitMember(connMgr, userid, isretired, isemtpty, isstoped);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * Member 존재 여부 체크
     * 
     * @param box receive from the form object and session
     * @return String Return @ 신청가능하면 true (중복안됨) @ 신청불가능하면 false(중복됨)
     */
    public String isExitMember(DBConnectionManager connMgr, String userid, String isretired, String isemtpty, String isstoped) throws Exception {
        // DataBox dbox= null;
        ListSet ls1 = null;

        String sql = "";

        // int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {
            sql += "select office_gbn from tz_member where userid = '" + userid + "' \n";

            ls1 = connMgr.executeQuery(sql);

            if (ls1.next()) {
                if (ls1.getString("office_gbn").equals("N") && isretired.equals("Y")) {
                    errvalue = "2"; // 퇴직자입니다.
                } else {
                    errvalue = "0"; // 정상
                }

            } else {
                errvalue = "1"; // 인사DB에 존재하지 않습니다.
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }

        finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        }

        return errvalue;
    }

    /**
     * 문제가 설문지에 매핑되어있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S1" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulPaper(String grcode, String sulgubun, int sulnum) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitSulPaper(connMgr, grcode, sulgubun, sulnum);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * 문제가 설문지에 매핑되어있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S1" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulPaper(DBConnectionManager connMgr, String grcode, String sulgubun, int sulnum) throws Exception {
        // DataBox dbox= null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql = " select								\n";
            sql += "	count(*) cnt						\n";
            sql += " from								 \n";
            sql += "	tz_sulpaper						\n";
            sql += " where								\n";
            sql += "	subj = '" + sulgubun + "'	\n";

            // 수정일 : 05.11.04 수정자 : 이나연 _ || 수정
            sql += "	and ','||sulnums||',' like '%," + sulnum + ",%' \n";
            sql += "and grcode = '" + grcode + "'	\n";

            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
                if (cnt > 0) {
                    errvalue = "S1";
                } else {
                    errvalue = "0";
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return errvalue;
    }

    /**
     * 설문 문제에 응시자가 있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S2" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulEach(String grcode, String sulgubun, int sulnum) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitSulEach(connMgr, grcode, sulgubun, sulnum);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * 문제가 설문지에 매핑되어있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S2" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulEach(DBConnectionManager connMgr, String grcode, String sulgubun, int sulnum) throws Exception {
        // DataBox dbox = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql += " select														  \n";
            sql += "	count(*) cnt												  \n";
            sql += " from															\n";
            sql += "	tz_suleach													\n";
            sql += " where															\n";
            sql += "	1=1															\n";

            if (sulgubun.equals("ALL")) {
                sql += "	AND grcode != 'ALL' and ( subj != 'COMMON' and subj != 'TARGET' and subj != 'CP' ) \n";
            } else if (sulgubun.equals("COMMON")) {
                sql += "	AND subj = 'COMMON'\n";
            } else if (sulgubun.equals("TARGET")) {
                sql += "	AND subj = 'TARGET'\n";
            } else if (sulgubun.equals("CONTENTS")) {
                sql += "	AND grcode = 'ALL' and ( subj != 'COMMON' and subj != 'TARGET' and subj != 'CP' ) \n";
            } else if (sulgubun.equals("CP")) {
                sql += "	AND subj = 'CP'	\n";
            }
            // 수정일 : 05.11.04 수정자 : 이나연 _ || 수정
            // sql+= "	and ','||sulnums||',' like '%,"+sulnum+",%' \n";
            sql += "	and ','||sulnums||',' like '%," + sulnum + ",%' \n";
            sql += "and grcode = '" + grcode + "'	\n";
            /*
             * 추가조건 검토필요 sql += "and year = '"+year+"'	\n"; sql += "and gubun = '"+year+"'	\n";
             */

            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
                if (cnt > 0) {
                    errvalue = "S2";
                } else {
                    errvalue = "0";
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return errvalue;
    }

    /**
     * 문제가 설문지에 매핑되어있는지 여부 - 추가 2006.1.12
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S2" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulEach(String sulgubun, int sulnum, RequestBox box) throws Exception {
        // DataBox dbox = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DBConnectionManager connMgr = null;

        String sql = "";
        String v_grcode = box.getSession("tem_grcode");

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            connMgr = new DBConnectionManager();

            sql += " select														  \n";
            sql += "	count(*) cnt												  \n";
            sql += " from															\n";
            sql += "	tz_suleach													\n";
            sql += " where															\n";
            sql += "	1=1															\n";

            if (sulgubun.equals("ALL")) {
                sql += "	AND grcode != 'ALL' and ( subj != 'COMMON' and subj != 'TARGET' and subj != 'CP' ) \n";
            } else if (sulgubun.equals("COMMON")) {
                sql += "	AND subj = 'COMMON' \n";
            } else if (sulgubun.equals("TARGET")) {
                sql += "	AND subj = 'TARGET' \n";
            } else if (sulgubun.equals("REGIST")) {
                sql += "	AND grcode = '" + v_grcode + "' \n";
                sql += "	AND subj = 'REGIST' \n";
            } else if (sulgubun.equals("CP")) {
                sql += "	AND subj = 'CP'	\n";
            }
            sql += "	and ','||sulnums||',' like '%," + sulnum + ",%' \n";

            System.out.println("문제가 설문지에 쓰였는지:" + sql);

            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
                if (cnt > 0) {
                    errvalue = "S2";
                } else {
                    errvalue = "0";
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return errvalue;
    }

    /**
     * 설문지에 응시자가 있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S3" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulPaperApply(String sulgubun, int sulpapernum) throws Exception {
        DBConnectionManager connMgr = null;
        String errvalue = "";

        try {
            connMgr = new DBConnectionManager();
            errvalue = isExitSulPaperApply(connMgr, sulgubun, sulpapernum);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return errvalue;
    }

    /**
     * 설문지에 응시자가 있는지 여부
     * 
     * @param box receive from the form object and session
     * @return String Return @ "S3" 매핑되어 있음 @ "0" 매핑되어 있지 않음
     */
    public String isExitSulPaperApply(DBConnectionManager connMgr, String sulgubun, int sulpapernum) throws Exception {
        // DataBox dbox = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "";

        int cnt = 0;
        // boolean chkseq = true;
        String errvalue = "";

        try {

            sql += " select														  \n";
            sql += "	count(*) cnt												  \n";
            sql += " from															\n";
            sql += "	tz_suleach													\n";
            sql += " where															\n";
            sql += "	1=1															\n";

            if (sulgubun.equals("ALL")) {
                sql += "	AND grcode != 'ALL' and ( subj != 'COMMON' and subj != 'TARGET' and subj != 'CP' ) \n";
            } else if (sulgubun.equals("COMMON")) {
                sql += "	AND subj = 'COMMON'\n";
            } else if (sulgubun.equals("TARGET")) {
                sql += "	AND subj = 'TARGET'\n";
            } else if (sulgubun.equals("CONTENTS")) {
                sql += "	AND grcode = 'ALL' and ( subj != 'COMMON' and subj != 'TARGET' and subj != 'CP' )\n";
            } else if (sulgubun.equals("CP")) {
                sql += "	AND subj = 'CP'	\n";
            }
            sql += "	and sulpapernum = " + sulpapernum + " \n";

            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("cnt");
                if (cnt > 0) {
                    errvalue = "S3";
                } else {
                    errvalue = "0";
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return errvalue;
    }

}