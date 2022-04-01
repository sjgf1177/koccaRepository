//**********************************************************
//  1. 제      목: 관심과정 Servlet
//  2. 프로그램명: InterestBean.java
//  3. 개      요: 관심과정 사용자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
package com.credu.study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class InterestBean {

    public InterestBean() {
    }

    /**
     * 관심과정 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ArrayList<DataBox>> selectInterestList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        ArrayList<ArrayList<DataBox>> list2 = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_user_id = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            list2 = new ArrayList<ArrayList<DataBox>>();

            sql = " select distinct  a.userid, e.name, a.subj, '' grcode,   b.subjnm, '' biyong, get_name(muserid) musernm,  \n";
            sql += "  d.classname, '' propstart, '' propend, a.indate, '' edustart, '' eduend , b.sphere , 'Y' as isgubun \n"; //isgubun은 보낼row가 과정줄인지,차수줄인지
            sql += " from   TZ_INTEREST a,  TZ_SUBJ  b,  TZ_grsubj  c,  TZ_SUBJATT d, TZ_MEMBER e    \n";
            sql += " where  a.subj = b.subj   \n";
            sql += " and  a.subj = c.subjcourse   \n";
            sql += " and  a.userid = e.userid   \n";
            sql += " and b.upperclass = d.upperclass    \n";
            //sql+=" and b.middleclass = d.middleclass   \n";
            sql += " and a.grcode = c.grcode \n";
            sql += " and d.middleclass='000'   \n";
            sql += " and d.lowerclass='000'   \n";
            sql += " and a.grcode = '" + v_grcode + "'   \n";
            sql += " and a.USERID=  '" + v_user_id + "'  ";

            System.out.println(" InterestBean 관심과정 과정:" + sql);

            ls = connMgr.executeQuery(sql);
            System.out.println("InterestBean 관심과정 리스트 조회 selectInterestList : " + sql);

            String v_subjcode = "";
            String v_year = "";
            while (ls.next()) {

                dbox = ls.getDataBox();
                list = new ArrayList<DataBox>();
                list.add(dbox);
                list2.add(list);
                v_subjcode = dbox.getString("d_subj");
                v_year = FormatDate.getDate("yyyy");

                list2.add(this.selectInterestListSubjseq(connMgr, v_subjcode, v_grcode, v_year));
                list = null;

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
        return list2;
    }

    /**
     * 해당과정의 차수들 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectInterestListSubjseq(DBConnectionManager connMgr, String v_subjcode, String v_grcode, String v_year) throws Exception {
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {

            list = new ArrayList<DataBox>();

            sql = " select subj, subjseq, year,  propstart,  propend,  edustart,  eduend,  studentlimit,  subjseqgr, isonoff,    'N' as isgubun ,        \n";
            sql += "        /*(select proposetype from vz_mastersubjseq where subj = a.subj and subjseq = a.subjseq and year = a.year) proposetype,   \n";
            sql += "        (select mastercd from vz_mastersubjseq where subj = a.subj and subjseq = a.subjseq and year = a.year) mastercd, */         \n";
            sql += "        (select count(*) cnt from tz_propose where subj = a.subj and year = a.year and subjseq = a.subjseq   and isnull(cancelkind,' ') not in ('F','P') ) propcnt, \n";
            sql += "   		(select preurl from tz_subj where a.subj = subj ) preurl ";
            sql += "   from VZ_SCSUBJSEQ a  \n";
            sql += "  where scsubj = " + SQLString.Format(v_subjcode);
            sql += "  and grcode = " + SQLString.Format(v_grcode);
            sql += "    and to_char(sysdate,'YYYYMMDDHH24') <= a.propend  \n";
            sql += "    and gyear  = " + SQLString.Format(v_year);
            sql += " order by subjseqgr \n";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox = ls.getDataBox();
                list.add(dbox);

            }
        } catch (Exception ex) {
            //ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * 나의 관심과정 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectMyInterestList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        String v_user_id = box.getSession("userid");
        // String v_year = FormatDate.getDate("yyyy");

        try {

            connMgr = new DBConnectionManager();

            list1 = new ArrayList<DataBox>();

            sql.append(" SELECT * FROM ( \n"); //나의관심과정 수강신청 후 리스트에서 삭제
            sql.append(" select A.USERID, E.NAME, A.SUBJ, '' GRCODE, B.SUBJNM, B.UPPERCLASS,b.PREURL, \n");
            sql.append("         GET_NAME (MUSERID) MUSERNM, D.CLASSNAME, A.INDATE, B.SPHERE, \n");
            sql.append("         F.SUBJSEQ, F.YEAR, F.PROPSTART, F.PROPEND, F.EDUSTART, F.EDUEND, \n");
            sql.append("         F.STUDENTLIMIT, F.SUBJSEQGR, F.ISONOFF, F.BIYONG, \n");
            sql.append("         (SELECT COUNT (*) CNT FROM TZ_PROPOSE z WHERE z.SUBJ = f.SUBJ AND z.YEAR = f.YEAR AND z.SUBJSEQ = f.SUBJSEQ AND z.USERID = a.userid AND ISNULL (z.CANCELKIND, ' ') NOT IN ('F', 'P')) PROPCNT \n");
            sql.append("    FROM TZ_INTEREST A \n");
            sql.append("    left join  TZ_SUBJ B on a.subj=b.subj \n");
            sql.append("    left join  TZ_GRSUBJ C on a.subj=C.SUBJCOURSE and A.GRCODE = c.GRCODE \n");
            sql.append("    left join  TZ_SUBJATT D on B.UPPERCLASS = D.UPPERCLASS AND D.MIDDLECLASS = '000' AND D.LOWERCLASS = '000' \n");
            sql.append("    left join  TZ_MEMBER E on A.USERID = E.USERID and A.GRCODE = E.GRCODE \n");
            sql.append("    left join VZ_SCSUBJSEQ f on a.subj=f.subj and A.GRCODE = f.GRCODE and TO_CHAR (SYSDATE, 'YYYYMMDDHH24') <= f.PROPEND \n");
            sql.append("    where a.userid=" + SQLString.Format(v_user_id) + " AND A.GRCODE = " + SQLString.Format(v_grcode));
            sql.append("	  and b.subjnm is not null \n"); //삭제된 과정이 노출
            sql.append("ORDER BY SUBJ, SUBJSEQ \n");
            sql.append(") X WHERE X.PROPCNT <= 0 "); //나의관심과정 수강신청 후 리스트에서 삭제

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
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

        return list1;
    }

    /**
     * 관심과정 리스트 삭제
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int deleteInterest(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        int isOk = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");
        String v_subj = box.getString("p_subj");

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = "delete  from  TZ_INTEREST   \n";
            sql += " where subj=   " + SQLString.Format(v_subj);
            sql += " and grcode = " + SQLString.Format(v_grcode);
            sql += " and USERID=  " + SQLString.Format(v_user_id);
            //System.out.println("deleteInterest sql:"+sql);

            isOk = connMgr.executeUpdate(sql);
            connMgr.commit();

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 관심과정 리스트 등록
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int insertInterest(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");
        String v_subj = box.getString("p_subj");

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //-------------------------------------------------------
            // 기 등록여부 체크  있으면 리턴 / 없으면 등록
            sql2 = " select count(*) cnt from tz_interest    \n ";
            sql2 += " where grcode = ? \n";
            sql2 += " and subj= ?  \n ";
            sql2 += " and userid= ? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_grcode);
            pstmt2.setString(2, v_subj);
            pstmt2.setString(3, v_user_id);

            rs = pstmt2.executeQuery();

            if (rs.next()) {

                isOk = rs.getInt("cnt");
            }

            if (isOk > 0) { // 있으면 -1로 리턴
                isOk = -1;
            } else { //없으면 등록

                //------------------------------------------------------
                // 등록
                sql = " insert  tz_interest( grcode, subj, userid, indate ) \n  " + " values(  ?, 	?, 	?,	 to_char(sysdate, 'YYYYMMDD')    )  \n";
                System.out.println("insertInterest sql:" + sql);

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_user_id);

                isOk = pstmt.executeUpdate();

                connMgr.commit();
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e10) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            connMgr.setAutoCommit(true);
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
