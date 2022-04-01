//**********************************************************
//  1. 제      목: 학습제약조건 BEAN
//  2. 프로그램명: StudyControlBean.java
//  3. 개      요: 학습제약조건 BEAN
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: S.W.Kang 2005. 03. 02
//  7. 수      정:
//**********************************************************
package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class StudyControlBean {

    public StudyControlBean() {
    }

    /**
     * 학습제약조건 정보 조회
     * 
     * @param box receive from the form object and session
     * @return SelectStudyControl
     */
    public ArrayList<DataBox> SelectStudyControl(RequestBox box, String v_isholiday) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_comp = box.getString("s_company"); // 회사구분
        String v_gubun = box.getString("p_gubun"); // L : 어학 대분류, W : 직무관리 대분류
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT seq, ";
            sql += "		company, ";
            sql += "		get_compnm(company,2,2) companynm, ";
            sql += "		gubun, ";
            sql += "		startdt, ";
            sql += "		enddt, ";
            sql += "		day1, ";
            sql += "        day2, ";
            sql += "		starttime, ";
            sql += "		endtime, ";
            sql += "		isuse, ";
            sql += " 		isholiday ";
            sql += " FROM TZ_STUDYCONTROL WHERE 1 = 1 ";
            
            if (!v_comp.equals("ALL"))
                sql += "  AND company = '" + v_comp + "'";
            if (!v_gubun.equals("") && !v_gubun.equals("ALL"))
                sql += "  AND gubun = '" + v_gubun + "'";
            if (!v_isholiday.equals(""))
                sql += "  AND isholiday = '" + v_isholiday + "'";

            if (v_orderColumn.equals("")) {
                sql += " order by company, gubun ";
            } else {
                sql += " order by " + v_orderColumn + v_orderType;
            }

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
     * 학습제약조건 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertStudyControl(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int v_seq = 0;

        String v_company = box.getString("s_company");
        String v_gubun = box.getString("p_gubun");
        String v_startdt = box.getString("p_startdt");
        String v_enddt = box.getString("p_enddt");
        String v_day1 = box.getString("p_day1");
        String v_day2 = box.getString("p_day2");
        String v_starttime = box.getString("p_starttime");
        String v_endtime = box.getString("p_endtime");
        String v_isuse = box.getString("p_isuse");
        String v_isholiday = box.getString("p_isholiday");
        String s_userid = box.getSession("userid");

        v_startdt = StringManager.replace(v_startdt, "-", "");
        v_enddt = StringManager.replace(v_enddt, "-", "");

        try {
            connMgr = new DBConnectionManager();

            sql = "select max(seq) from TZ_STUDYCONTROL  ";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1);
            }
            ls.close();

            sql = "insert into TZ_STUDYCONTROL(seq,company,gubun,startdt,enddt,day1,day2,starttime,endtime,isuse,isholiday,luserid,ldate) ";
            sql += " values (?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'))          ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setInt(1, v_seq + 1);
            pstmt.setString(2, v_company);
            pstmt.setString(3, v_gubun);
            pstmt.setString(4, v_startdt);
            pstmt.setString(5, v_enddt);
            pstmt.setString(6, v_day1);
            pstmt.setString(7, v_day2);
            pstmt.setString(8, v_starttime);
            pstmt.setString(9, v_endtime);
            pstmt.setString(10, v_isuse);
            pstmt.setString(11, v_isholiday);
            pstmt.setString(12, s_userid);

            isOk = pstmt.executeUpdate();
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 학습제약조건 수정할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateStudyControl(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        // String v_company = box.getString("s_company");
        String v_startdt = box.getString("p_startdt");
        String v_enddt = box.getString("p_enddt");
        String v_day1 = box.getString("p_day1");
        String v_day2 = box.getString("p_day2");
        String v_starttime = box.getString("p_starttime");
        String v_endtime = box.getString("p_endtime");
        String v_isuse = box.getString("p_isuse");
        String s_userid = box.getSession("userid");
        int v_seq = box.getInt("p_seq");

        v_startdt = StringManager.replace(v_startdt, "-", "");
        v_enddt = StringManager.replace(v_enddt, "-", "");

        try {
            connMgr = new DBConnectionManager();

            sql = "update TZ_STUDYCONTROL ";
            sql += "   set startdt=?, ";
            sql += " 	  enddt=?, ";
            sql += "       day1=?, ";
            sql += "		  day2=?, ";
            sql += "		  starttime=?, ";
            sql += "		  endtime=?, ";
            sql += "		  isuse=?, ";
            sql += "		  luserid=?, ";
            sql += "		  ldate=to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += " where seq=?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_startdt);
            pstmt.setString(2, v_enddt);
            pstmt.setString(3, v_day1);
            pstmt.setString(4, v_day2);
            pstmt.setString(5, v_starttime);
            pstmt.setString(6, v_endtime);
            pstmt.setString(7, v_isuse);
            pstmt.setString(8, s_userid);
            pstmt.setInt(9, v_seq);

            isOk = pstmt.executeUpdate();
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 학습제약조건 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int deleteStudyControl(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql = "delete from TZ_STUDYCONTROL where seq=?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setInt(1, v_seq);

            isOk = pstmt.executeUpdate();
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 학습제약조건 예외자 조회
     * 
     * @param box receive from the form object and session
     * @return SelectStudyControl
     */
    public ArrayList<DataBox> SelectStudyControlExp(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;

        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_comp = box.getString("s_company"); // 회사구분
        String v_gubun = box.getString("p_gubun"); // L : 어학 대분류, W : 직무관리 대분류

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT seq, ";
            sql += "		company, ";
            sql += "		get_compnm(company,2,2) companynm, ";
            sql += "		gubun, ";
            sql += "		userid, ";
            sql += "		get_name(userid) usernm, ";
            sql += "		reason, ";
            sql += " 		ldate ";
            sql += " FROM TZ_STUDYCONTROLEXP WHERE 1 = 1";
            if (!v_comp.equals("") && !v_comp.equals("ALL"))
                sql += "  AND company = '" + v_comp + "'";
            if (!v_gubun.equals("") && !v_gubun.equals("ALL"))
                sql += "  AND gubun = '" + v_gubun + "'";

            sql += "  ORDER BY company, gubun  ";

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
     * 학습제약조건 예외자 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertStudyControlExp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int v_cnt = 0;

        String v_company = box.getString("s_company");
        String v_gubun = box.getString("p_gubun");
        String v_userid = box.getString("p_userid");
        String v_reason = box.getString("p_reason");
        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(userid) from TZ_STUDYCONTROLEXP where company='" + v_company + "' and gubun ='" + v_gubun + "' and userid = '" + v_userid + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_cnt = ls.getInt(1);
                isOk = 99;
            }
            ls.close();

            if (v_cnt == 0) { // 중복된 아이디가 없을때
                sql = "insert into TZ_STUDYCONTROLEXP(seq,company,gubun,userid,reason,luserid,ldate) ";
                sql += " values ((select NVL(max(seq),0)+1 from TZ_STUDYCONTROLEXP),?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'))          ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, v_company);
                pstmt.setString(2, v_gubun);
                pstmt.setString(3, v_userid);
                pstmt.setString(4, v_reason);
                pstmt.setString(5, s_userid);

                isOk = pstmt.executeUpdate();
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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
     * 학습제약조건 예외자 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int deleteStudyControlExp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql = "delete from TZ_STUDYCONTROLEXP where seq=?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setInt(1, v_seq);

            isOk = pstmt.executeUpdate();
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
            if (pstmt != null) {
                try {
                    pstmt.close();
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

}