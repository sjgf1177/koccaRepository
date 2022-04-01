//**********************************************************
//  1. 제      목: 대분류예산설정 BEAN
//  2. 프로그램명: SubjectBean.java
//  3. 개      요: 대분류예산설정 BEAN
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2005. 02. 22
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

public class ClassifyBudgetBean {

    public ClassifyBudgetBean() {
    }

    /**
     * 예산설정 정보 조회
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectBudgetList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getString("s_grcode");
        String v_gryear = box.getString("s_gyear");
        String v_grseq = box.getString("s_grseq");
        String v_gubun = box.getString("s_gubun");

        try {
            connMgr = new DBConnectionManager();

            sql = "select grcode,";
            sql += "       gryear,";
            sql += "       grseq,";
            sql += "       gubun,";
            sql += "       propstart,";
            sql += "       propend,";
            sql += "       budget,";
            sql += "       isuse ";
            //            sql+= "       maxsubjcnt ";
            sql += "     from   tz_budget where 1 = 1 ";
            if (!v_grcode.equals("") && !v_grcode.equals("ALL"))
                sql += " and grcode = '" + v_grcode + "'";
            if (!v_grcode.equals("ALL") && !v_gryear.equals(""))
                sql += " and gryear = '" + v_gryear + "'";
            if (!v_grcode.equals("ALL") && !v_grseq.equals("") && !v_grseq.equals("ALL"))
                sql += " and grseq = '" + v_grseq + "'";
            if (!v_gubun.equals("ALL"))
                sql += " and gubun = '" + v_gubun + "'";

            ls = connMgr.executeQuery(sql);
            list = new ArrayList();

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
     * 예산설정 정보 조회
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox SelectBudgetInfo(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getString("grcode");
        String v_gryear = box.getString("gryear");
        String v_grseq = box.getString("grseq");
        String v_gubun = box.getString("gubun");

        try {
            connMgr = new DBConnectionManager();

            sql = "select propstart,";
            sql += "       propend,";
            sql += "       budget,";
            sql += "       isuse ";
            //            sql+= "       maxsubjcnt ";
            sql += "  from tz_budget ";
            sql += "  where grcode = '" + v_grcode + "'";
            sql += "    and gryear = '" + v_gryear + "'";
            sql += "    and grseq  = '" + v_grseq + "'";
            sql += "    and gubun  = '" + v_gubun + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
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
     * 신청현황 조회
     * 
     * @param box receive from the form object and session
     * @return TutorData
     */
    @SuppressWarnings("unchecked")
    public ArrayList SelectStudentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        DataBox dbox2 = null;

        String v_grcode = box.getString("grcode");
        String v_gryear = box.getString("gryear");
        String v_grseq = box.getString("grseq");
        String v_gubun = box.getString("gubun");

        String v_propstart = "";
        String v_propend = "";

        try {

            dbox2 = this.SelectBudgetInfo(box);

            if (dbox2 != null) {
                v_propstart = dbox2.getString("d_propstart");
                v_propend = dbox2.getString("d_propend");

                sql = " select a.subj, a.subjnm, avg(a.biyong) avgbiyong,     ";
                sql += "    count(b.userid) stdcnt, sum(a.biyong) sumbiyong    ";
                sql += "  from vz_scsubjseq a, tz_student b, tz_classfymatch c ";
                sql += "  where a.propstart >= '" + v_propstart + "00' ";
                sql += "    and a.propend <= '" + v_propend + "23' ";
                sql += "    and a.subj    = b.subj ";
                sql += "    and a.year    = b.year ";
                sql += "    and a.subjseq = b.subjseq ";
                sql += "    and a.grcode  ='" + v_grcode + "'";
                sql += "    and a.gyear   = '" + v_gryear + "'";
                sql += "    and a.grseq   = '" + v_grseq + "'";
                sql += "    and c.matchcode  = '" + v_gubun + "'";
                sql += "    and c.upperclass = a.scupperclass ";
                sql += "  group by a.subj, a.subjnm ";

                connMgr = new DBConnectionManager();
                list = new ArrayList();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    dbox.put("d_subj", ls.getString("subj"));
                    dbox.put("d_subjnm", ls.getString("subjnm"));
                    dbox.put("d_avgbiyong", new Integer(ls.getInt("avgbiyong")));
                    dbox.put("d_stdcnt", new Integer(ls.getInt("stdcnt")));
                    dbox.put("d_sumbiyong", new Double(ls.getDouble("sumbiyong")));
                    ;

                    list.add(dbox);
                }

            }//end if
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
     * 예산정보 저장
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateBudgetInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk = 0;
        int cnt = 0;

        String v_userid = box.getSession("userid");

        String v_grcode = box.getString("s_grcode");
        String v_gryear = box.getString("s_gyear");
        String v_grseq = box.getString("s_grseq");
        String v_gubun = box.getString("s_gubun");
        String v_propstart = box.getString("p_propstart");
        String v_propend = box.getString("p_propend");
        String v_budget = box.getString("p_budget");
        String v_isuse = box.getString("p_isuse");

        v_propstart = StringManager.replace(v_propstart, "-", "");
        v_propend = StringManager.replace(v_propend, "-", "");

        sql1 = "select  count(grcode) cnt";
        sql1 += "  from   TZ_BUDGET ";
        sql1 += " where grcode = '" + v_grcode + "'";
        sql1 += "  and  gryear = '" + v_gryear + "'";
        sql1 += "  and  grseq = '" + v_grseq + "'";
        sql1 += "  and  gubun = '" + v_gubun + "'";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt > 0) {
                //update TZ_BUDGET table
                sql2 = "update TZ_BUDGET";
                sql2 += " set   propstart=?, ";
                sql2 += "   propend=?,";
                sql2 += "   budget=?,";
                sql2 += "   isuse=?,";
                sql2 += "   luserid=?,";
                sql2 += "   ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')";
                sql2 += " where     grcode = ? ";
                sql2 += "   and     gryear = ? ";
                sql2 += "   and     grseq = ? ";
                sql2 += "   and		gubun = ? ";

                pstmt1 = connMgr.prepareStatement(sql2);

                pstmt1.setString(1, v_propstart);
                pstmt1.setString(2, v_propend);
                pstmt1.setString(3, v_budget);
                pstmt1.setString(4, v_isuse);
                pstmt1.setString(5, v_userid);
                pstmt1.setString(6, v_grcode);
                pstmt1.setString(7, v_gryear);
                pstmt1.setString(8, v_grseq);
                pstmt1.setString(9, v_gubun);

                isOk = pstmt1.executeUpdate();

            } else {
                sql3 = " insert into TZ_BUDGET(grcode, gryear, grseq, gubun, propstart, propend, budget, isuse, luserid,  ldate) ";
                sql3 += " values (?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

                pstmt2 = connMgr.prepareStatement(sql3);

                pstmt2.setString(1, v_grcode);
                pstmt2.setString(2, v_gryear);
                pstmt2.setString(3, v_grseq);
                pstmt2.setString(4, v_gubun);
                pstmt2.setString(5, v_propstart);
                pstmt2.setString(6, v_propend);
                pstmt2.setString(7, v_budget);
                pstmt2.setString(8, v_isuse);
                pstmt2.setString(9, v_userid);

                isOk = pstmt2.executeUpdate();
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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

}
