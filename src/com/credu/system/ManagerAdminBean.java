//**********************************************************
//  1. 제      목: 학습운영맵
//  2. 프로그램명 : ManagerAdminBean.java
//  3. 개      요: 학습운영맵
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 운영자 관리(ADMIN)
 * 
 * @date : 2004. 11
 * @author : S.W.Kang
 */
@SuppressWarnings("unchecked")
public class ManagerAdminBean {

    public ManagerAdminBean() {
    }

    /**
     * 운영자 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        int isOk1 = 0;
        // int isOk2 = 0;
        // int isOk3 = 0;
        // int isOk4 = 0;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        // String v_isdeleted = "Y";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // 관리자 TABLE 삭제 -- isdeleted 필드에 Y 세팅
            sql1 = "  delete from TZ_MANAGER 	  ";
            sql1 += "   where userid = ?		  ";
            sql1 += "     and gadmin = ?		  ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_userid);
            pstmt1.setString(2, v_gadmin);
            isOk1 = pstmt1.executeUpdate();

            // 관리그룹 TABLE 삭제
            sql2 = " delete from TZ_GRCODEMAN    ";
            sql2 += "  where userid  = ?          ";
            sql2 += "    and gadmin  = ?          ";

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_userid);
            pstmt2.setString(2, v_gadmin);
            pstmt2.executeUpdate();

            // 관리회사/부서삭제 TABLE 삭제
            sql3 = " delete from TZ_COMPMAN      ";
            sql3 += "  where userid  = ?          ";
            //            sql3 += "    and gadmin  = ?          ";

            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_userid);
            //            pstmt3.setString(2, v_gadmin);
            pstmt3.executeUpdate();

            // 관리과정 TABLE 삭제
            sql4 = " delete from TZ_SUBJMAN      ";
            sql4 += "  where userid  = ?          ";
            sql4 += "    and gadmin  = ?          ";

            pstmt4 = connMgr.prepareStatement(sql4);
            pstmt4.setString(1, v_userid);
            pstmt4.setString(2, v_gadmin);
            pstmt4.executeUpdate();

            if (isOk1 > 0)
                connMgr.commit(); //  그룹, 과정, 회사(부서) 담당자 외에는 하위 삭제 테이블이 없으므로
            else
                connMgr.rollback();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        //        return isOk1*isOk2;
        return isOk1;
    }

    /**
     * 부서관리자 관리부사조건쿼리
     * 
     * @param box receive from the form object and session
     * @return String 조건쿼리
     */
    public String getManagerDept(String v_userid, String v_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        int i = 0;

        try {
            connMgr = new DBConnectionManager();
            sql = " select comp comp from TZ_COMPMAN  ";
            sql += "  where userid = " + StringManager.makeSQL(v_userid);
            sql += "    and gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by comp asc            ";
            ls = connMgr.executeQuery(sql);
            //System.out.println("comp_sql="+sql);

            while (ls.next()) {
                if (i == 0)
                    result = " ( ";
                else
                    result += ", ";

                result += StringManager.makeSQL(ls.getString("comp"));
                i++;
            }
            if (i > 0)
                result += " ) ";
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
     * 운영자 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int isOk1 = 0;
        int v_cnt = 0; // 중복체크

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_grtype = box.getString("p_grtype");
        String v_isdeleted = "N";
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        String v_commented = box.getString("p_commented");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = "select count(*) from TZ_MANAGER ";
            sql += " where userid  = " + StringManager.makeSQL(v_userid);
            sql += "   and gadmin = " + StringManager.makeSQL(v_gadmin);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_cnt = ls.getInt(1);
            }

            if (v_cnt > 0) { // 기존 등록되어있으면
                isOk = 0;
            } else {
                sql1 = "insert into TZ_MANAGER(userid, gadmin, grtype, isdeleted, fmon, tmon, commented, luserid, ldate)  ";
                sql1 += "               values (?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))        ";

                pstmt = connMgr.prepareStatement(sql1);
                pstmt.setString(1, v_userid);
                pstmt.setString(2, v_gadmin);
                pstmt.setString(3, v_grtype);
                pstmt.setString(4, v_isdeleted);
                pstmt.setString(5, v_fmon);
                pstmt.setString(6, v_tmon);
                pstmt.setString(7, v_commented);
                pstmt.setString(8, s_userid);

                isOk = pstmt.executeUpdate();
            }

            isOk1 = insertManagerSub(box);

            System.out.println("isOk=" + isOk);

            if (isOk > 0 && isOk1 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
     * 운영자 등록할때 - 세부정보
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertManagerSub(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        // int v_cnt = 0;       // 중복체크

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");

        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneedoutcomp = "";
        String v_isneeddept = "";

        System.out.println("v_gadminview = " + v_gadminview);

        // 코드분할 (권한코드 + "," + 교육그룹필요여부  + "," + 과정코드필요여부 + "," + 회사코드필요여부 + "," + 부서코드필요여부)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");

        // 그룹/과정/회사/부서 필요여부
        if (st.hasMoreElements()) {
            v_gadmin = st.nextToken();
            v_isneedgrcode = st.nextToken();
            v_isneedsubj = st.nextToken();
            v_isneedcomp = st.nextToken();
            v_isneeddept = st.nextToken();
            //v_isneedoutcomp = (String)st.nextToken();
        }

        // 그룹코드
        Vector v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";
        // 과정코드
        Vector v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";
        // 회사코드
        Vector v_vcomp = box.getVector("p_company");
        String v_scomp = "";

        // 회사코드
        Vector v_voutcomp = box.getVector("p_outcompany");
        String v_soutcomp = "";

        // 부서코드
        Vector v_vdept = box.getVector("p_dept");
        String v_sdept = "";

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            isOk = 1; // 그룹/과정/부서 코드 모두 없을경우(UltraVisor,SuperVisor)
            // 그룹코드 필요여부
            if (v_isneedgrcode.equals("Y")) {
                for (int i = 0; i < v_vgrcode.size(); i++) {
                    v_sgrcode = (String) v_vgrcode.elementAt(i);
                    sql = "insert into TZ_GRCODEMAN(userid, gadmin, grcode, luserid, ldate)         ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_sgrcode);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // 과정코드 필요여부
            if (v_isneedsubj.equals("Y")) {
                for (int i = 0; i < v_vsubj.size(); i++) {
                    v_ssubj = (String) v_vsubj.elementAt(i);
                    sql = "insert into TZ_SUBJMAN(userid, gadmin, subj, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_ssubj);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // 회사코드 필요여부
            if (v_isneedcomp.equals("Y")) {
                for (int i = 0; i < v_vcomp.size(); i++) {
                    v_scomp = (String) v_vcomp.elementAt(i);
                    sql = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_scomp);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // 외부업체회사코드 필요여부
            if (v_isneedoutcomp.equals("Y")) {
                for (int i = 0; i < v_voutcomp.size(); i++) {
                    v_soutcomp = (String) v_voutcomp.elementAt(i);
                    sql = "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_soutcomp);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // 부서코드 필요여부
            if (v_isneeddept.equals("Y")) {
                for (int i = 0; i < v_vdept.size(); i++) {
                    v_sdept = (String) v_vdept.elementAt(i);
                    sql = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_sdept);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }

            System.out.println("insertManagerSub=====>>>>" + isOk);
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
     * 운영자화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 운영자 리스트
     */
    public ArrayList selectListManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext").trim();
        String v_search = box.getString("p_search").trim();
        String s_gadmin = box.getStringDefault("s_gadmin", "ALL"); //select box gadmin
        String ss_gadmin = box.getSession("gadmin");
        String ss_grtype = box.getSession("grtype");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서
        StringTokenizer st = new StringTokenizer(s_gadmin, ",");

        if (st.hasMoreElements()) {
            s_gadmin = st.nextToken();
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql = " Select a.userid userid, a.gadmin gadmin, c.gadminnm gadminnm, \n ";
            sql += "        a.grtype grtype, a.isdeleted isdeleted, a.fmon fmon, a.tmon tmon, \n";
            sql += "        a.commented commented, a.luserid luserid, a.ldate ldate, \n";
            sql += "        b.name name, get_compnm(b.comp,2,2) compnm \n";
            sql += "   From TZ_MANAGER a, TZ_MEMBER b, TZ_GADMIN c\n";
            sql += "  Where a.userid = b.userid \n";
            sql += "    and a.gadmin = c.gadmin\n";
            sql += "    and a.isdeleted = 'N'\n ";
            sql += "    and c.isView = 'Y' \n";
            //			sql += "    and b.grcode = 'N000001' \n";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("name")) { //    제목으로 검색할때
                    sql += " and b.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("userid")) { //    내용으로 검색할때
                    sql += " and upper(b.userid) like upper(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")\n";
                }
            }
            if (!s_gadmin.equals("ALL")) {
                sql += " and c.gadmin = " + StringManager.makeSQL(s_gadmin);
            }
            if (!StringManager.substring(ss_gadmin, 0, 1).equals("A")) {
                sql += " and a.grtype = " + StringManager.makeSQL(ss_grtype);
            }

            if (v_orderColumn.equals("")) {
                sql += " order by a.gadmin asc , b.name asc";
            } else {
                sql += " order by b." + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);
            //			while (ls.next()) {
            //				dbox = ls.getDataBox();
            //				list.add(dbox);
            //			}

            int total_row_count = BoardPaging.getTotalRow(connMgr, sql, true); //     전체 row 수를 반환한다
            int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
            int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");
            ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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
     * 운영자화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return DataBox 조회한 상세정보
     */
    public DataBox selectViewManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();

            sql = " select a.userid userid, a.gadmin gadmin, c.gadminnm gadminnm,          ";
            sql += "        a.grtype grtype, a.isdeleted isdeleted, a.fmon fmon, a.tmon tmon,   ";
            sql += "        a.commented commented, a.luserid luserid, a.ldate ldate,        ";
            sql += "        b.name name, get_compnm(b.comp,2,2) compnm                  ";
            sql += "   from TZ_MANAGER a, TZ_MEMBER b, TZ_GADMIN c                          ";
            sql += "  where a.userid = b.userid                                             ";
            sql += "    and a.gadmin = c.gadmin                                             ";
            sql += "    and a.userid  = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);

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
     * 운영자화면 상세보기 - 회사
     * 
     * @param box receive from the form object and session
     * @return ArrayList 관리 회사 리스트
     */
    public ArrayList selectViewManagerComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.groupsnm , b.companynm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
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
     * 운영자화면 상세보기 - 부서
     * 
     * @param box receive from the form object and session
     * @return ArrayList 관리 부서 리스트
     */
    public ArrayList selectViewManagerDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.groupsnm , b.companynm , b.gpmnm, b.deptnm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
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
     * 운영자화면 상세보기 - 그룹
     * 
     * @param box receive from the form object and session
     * @return ArrayList 관리 교육그룹 리스트
     */
    public ArrayList selectViewManagerGrcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.grcode, b.grcodenm from TZ_GRCODEMAN a , TZ_GRCODE b   ";
            sql += "  where a.grcode = b.grcode                                      ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.grcode asc                                           ";
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
     * 운영자화면 상세보기 - 회사
     * 
     * @param box receive from the form object and session
     * @return ArrayList 관리 회사 리스트
     */
    public ArrayList selectViewManagerOutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.cpnm from TZ_OUTCOMPMAN a , TZ_CPINFO b   ";
            sql += "  where a.comp = b.cpseq                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                System.out.println(sql);
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
     * 운영자화면 상세보기 - 과정
     * 
     * @param box receive from the form object and session
     * @return ArrayList 관리 과정 리스트
     */
    public ArrayList selectViewManagerSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.subj, b.subjnm from TZ_SUBJMAN a , TZ_SUBJ b   ";
            sql += "  where a.subj = b.subj                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.subj asc                                     ";

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
     * 운영자 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int isOk1 = 0;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_grtype = box.getString("p_grtype");
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        String v_commented = box.getString("p_commented");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = " update TZ_MANAGER set grtype = ? , fmon = ?, tmon = ?, commented = ?, luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where userid  = ?  and gadmin = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_grtype);
            pstmt.setString(2, v_fmon);
            pstmt.setString(3, v_tmon);
            pstmt.setString(4, v_commented);
            pstmt.setString(5, s_userid);
            pstmt.setString(6, v_userid);
            pstmt.setString(7, v_gadmin);

            isOk = pstmt.executeUpdate();
            isOk1 = updateManagerSub(box);

            if (isOk > 0 && isOk1 > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 운영자 수정할때 - 세부정보
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateManagerSub(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        // int v_cnt = 0;       // 중복체크

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");
        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneeddept = "";
        String v_isneedoutcomp = "";

        // 코드분할 (권한코드 + "," + 교육그룹필요여부  + "," + 과정코드필요여부 + "," + 회사코드필요여부 + "," + 부서코드필요여부)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");
        // int j = 0;

        // 그룹/과정/회사/부서 필요여부
        if (st.hasMoreElements()) {
            v_gadmin = st.nextToken();
            v_isneedgrcode = st.nextToken();
            v_isneedsubj = st.nextToken();
            v_isneedcomp = st.nextToken();
            v_isneeddept = st.nextToken();
            //v_isneedoutcomp = (String)st.nextToken();
        }

        //System.out.println(v_gadmin       );
        //System.out.println(v_isneedgrcode );
        //System.out.println(v_isneedsubj   );
        //System.out.println(v_isneedcomp   );
        //System.out.println(v_isneeddept   );
        //System.out.println(v_isneedoutcomp);

        // 그룹코드 - v_gadmin(H)
        Vector v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";

        // 과정코드 - v_gadmin(F,P)
        Vector v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";

        // 회사코드 - v_gadmin(K)
        Vector v_vcomp = box.getVector("p_company");
        String v_scomp = "";

        // 회사코드 - v_gadmin(S,T,M)
        Vector v_voutcomp = box.getVector("p_outcompany");
        String v_soutcomp = "";

        // 부서코드 - v_gadmin(K)
        Vector v_vdept = box.getVector("p_dept");
        String v_sdept = "";

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            isOk = 1; // 그룹/과정/부서 코드 모두 없을경우(UltraVisor,SuperVisor)
            // 그룹코드 필요여부
            if (v_isneedgrcode.equals("Y")) {
                // 기존 데이타 삭제
                sql1 = " delete from TZ_GRCODEMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // 등록
                sql2 = "insert into TZ_GRCODEMAN(userid, gadmin, grcode, luserid, ldate)        ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vgrcode.size(); i++) {
                    v_sgrcode = (String) v_vgrcode.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_sgrcode);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // 과정코드 필요여부
            if (v_isneedsubj.equals("Y")) {
                // 기존 데이타 삭제
                sql1 = " delete from TZ_SUBJMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // 등록
                sql2 = "insert into TZ_SUBJMAN(userid, gadmin, subj, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vsubj.size(); i++) {
                    v_ssubj = (String) v_vsubj.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_ssubj);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // 회사코드 필요여부
            if (v_isneedcomp.equals("Y")) {
                // 기존 데이타 삭제
                sql1 = " delete from TZ_COMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // 등록
                sql2 = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vcomp.size(); i++) {
                    v_scomp = (String) v_vcomp.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_scomp);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // 외주코드 필요여부
            if (v_isneedoutcomp.equals("Y")) {
                // 기존 데이타 삭제
                sql1 = " delete from TZ_OUTCOMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // 등록
                sql2 = "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_voutcomp.size(); i++) {
                    v_soutcomp = (String) v_voutcomp.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_soutcomp);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }

            // 부서코드 필요여부
            if (v_isneeddept.equals("Y")) {
                // 기존 데이타 삭제
                sql1 = " delete from TZ_COMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // 등록
                sql2 = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vdept.size(); i++) {
                    v_sdept = (String) v_vdept.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_sdept);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

}
