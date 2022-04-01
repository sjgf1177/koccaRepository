//**********************************************************
//  1. 제      목: Correction ADMIN BEAN
//  2. 프로그램명: CorrectionAdminBean.java
//  3. 개      요: 첨삭관리 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package com.credu.tutor;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class CorrectionAdminBean {
    // private ConfigSet config;
    // private int row;

    public CorrectionAdminBean() {
        try {
            // config = new ConfigSet();
            // row = Integer.parseInt(config.getProperty("page.manage.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
            //row = 100;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 첨삭 목록
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox1 = null;

        String v_user_id = box.getSession("userid"); // 로그인 아이디
        String v_subj = box.getString("s_subjcourse"); // 로그인 아이디

        sql1 = "select seq, subj, userid, comments, luserid, ldate";
        sql1 += " from TZ_Correction";
        sql1 += " where userid = '" + v_user_id + "'";
        if (!v_subj.equals("----"))
            sql1 += " and subj = '" + v_subj + "'";
        sql1 += " order by ldate desc";

        //	   System.out.println("==== selectList :  "+sql1);

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox1 = ls1.getDataBox();
                list1.add(dbox1);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
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
        return list1;

    }

    /**
     * 첨삭 등록
     * 
     * @param box receive from the form object and session
     * @return int isOk
     */
    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        String sql = "";
        ListSet ls = null;
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("s_subjcourse"); //과정
        String v_comments = box.getString("p_comment"); //첨삭내용

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            //select NVL(max(seq),	0)+1  from TZ_Correction where subj = '1020'
            //sql	= "select NVL(max(seq),	0)+1 from	TZ_Correction where subj = '"+v_subj+"'";
            sql = "select NVL(max(seq),	0)+1 from	TZ_Correction ";
            ls = connMgr.executeQuery(sql);
            //			System.out.println("---- Correction Insert Seq :  "+sql);
            ls.next();
            String v_seq = ls.getString(1);
            ls.close();

            sql1 = "insert into TZ_CORRECTION(seq,subj,userid,comments,luserid,ldate) ";
            sql1 += "values(?,?,?,?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setString(1, v_seq);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_user_id);
            pstmt.setString(4, v_comments);
            pstmt.setString(5, v_user_id);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
     * 첨삭 수정
     * 
     * @param box receive from the form object and session
     * @return int isOk
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;
        int v_seq = box.getInt("p_seq");
        String seed = box.getString("e_subj");
        //일련번호
        String v_user_id = box.getSession("userid"); // 로그인 아이디
        String v_comments = box.getString("p_comments" + v_seq); //첨삭내용

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql1 = "update TZ_CORRECTION set comments='" + v_comments + "',luserid='" + v_user_id + "',ldate= to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql1 += "where seq=" + v_seq + " and subj='" + seed + "' ";
            //			  System.out.println("---- Cor Update Sql :  "+sql1);

            pstmt = connMgr.prepareStatement(sql1);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            //			  System.out.println("---- Correction Update Exception -----------------");
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
     * 첨삭 삭제
     * 
     * @param box receive from the form object and session
     * @return int isOk
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            sql1 = "delete TZ_CORRECTION where seq=?";
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setInt(1, v_seq);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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

}