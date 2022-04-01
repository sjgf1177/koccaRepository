//**********************************************************
//1. 제	  목: 대상자 설문 대상자 관리
//2. 프로그램명: SulmunTargetMemberBean.java
//3. 개	  요:
//4. 환	  경: JDK 1.3
//5. 버	  젼: 0.1
//6. 작	  성: lyh
//**********************************************************

package com.credu.research;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.library.ConfigSet;

;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunTargetMemberBean {
    private ConfigSet config;
    private int row;

    public SulmunTargetMemberBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 설문 대상자 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 설문 대상자
     */
    public ArrayList<DataBox> selectSulmunMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        try {

            String ss_grcode = box.getString("s_grcode");
            String ss_year = box.getString("s_gyear");
            String v_action = box.getString("p_action");
            int v_sulpapernum = box.getInt("s_sulpapernum");

            int v_pageno = box.getInt("p_pageno");
            int v_pagesize = box.getInt("p_pagesize");

            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                head_sql = "select a.subj,	 a.year,	a.subjseq, a.userid,	b.name , \n";
                head_sql += "			  (select count(userid) from tz_sulmailing where subj='TARGET' and grcode=" + SQLString.Format(ss_grcode) + " and ";
                head_sql += "		 year=" + SQLString.Format(ss_year) + " and sulpapernum=" + SQLString.Format(v_sulpapernum) + "  and  userid=a.userid)  ismailcnt , ";
                head_sql += "			  case when  b.membergubun = 'P' then  '개인'	 \n";
                head_sql += "			  when  b.membergubun = 'C' then  '기업'	  \n";
                head_sql += "			  when  b.membergubun = 'U' then  '대학교'	\n";
                head_sql += "			  else '-' end	as registgubunnm , NVL(b.indate,'-') as indate	\n";
                body_sql += "  from tz_sulmember	a,	\n";
                body_sql += "		tz_member	 b	\n";
                body_sql += " where a.userid  = b.userid	\n";
                body_sql += "	and a.subj	= 'TARGET'	\n";
                body_sql += "	and a.grcode	= " + SQLString.Format(ss_grcode);
                body_sql += "	and a.year	= " + SQLString.Format(ss_year);
                body_sql += "	and a.sulpapernum	= " + SQLString.Format(v_sulpapernum);
                order_sql += " order by a.userid ";

                sql = head_sql + body_sql + group_sql + order_sql;

                ls = connMgr.executeQuery(sql);
                System.out.println("selectSulmunMemberList:" + sql);

                count_sql = "select count(*) " + body_sql;
                int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql);

                ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
                ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.
                int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(total_page_count));
                    dbox.put("d_rowcount", new Integer(row));
                    dbox.put("d_totalrowcount", new Integer(totalrowcount));

                    list.add(dbox);
                }
                ls.close();
            }
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
        return list;
    }

    /**
     * 대상자를 찾기위한 회원 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대상자를 찾기위한 회원 리스트
     */
    public ArrayList<DataBox> selectMemberTargetList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        // String adminChkSql = "";
        // String addSql = "";

        try {
            // String ss_company = box.getStringDefault("s_company", "ALL"); //  해당 회사의 comp code
            // String ss_gpm = box.getStringDefault("s_gpm", "ALL");
            // String ss_dept = box.getStringDefault("s_dept", "ALL");
            // String ss_part = box.getStringDefault("s_part", "ALL");
            // String ss_jikwi = box.getStringDefault("s_jikwi", "ALL");
            // String ss_jikun = box.getStringDefault("s_jikun", "ALL");

            String ss_searchtype2 = box.getString("s_searchtype2"); //회원구분  전체 ALL/개인 P/기업 C/대학교 U
            String ss_searchtype = box.getString("s_searchtype"); //검색조건
            String ss_searchtext = box.getString("s_searchtext"); //검색어

            String v_action = box.getString("p_action");

            // session에서 문콘인지 게임인지 구분값가져와서 추가쿼리 만들기
            //String v_tem_type = box.getSession("tem_type");
            //System.out.println("v_tem_type:"+v_tem_type);
            //
            //if(v_tem_type.equals("GA")){		//게임이라면
            //	addSql = " and  a.registgubun = 'KGDI'	\n";
            //}else if(v_tem_type.equals("KA")){		//문콘이라면
            //	addSql = " and  a.registgubun = 'KOCCA'	\n";
            //}

            list = new ArrayList<DataBox>();

            String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
            String v_orderType = box.getString("p_orderType"); //정렬할 순서

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql = "select a.userid,  a.name ,  \n";
                sql += "			  case when  a.membergubun = 'P' then  '개인'	 \n";
                sql += "			  when  a.membergubun = 'C' then  '기업'	  \n";
                sql += "			  when  a.membergubun = 'U' then  '대학교'	\n";
                sql += "			  else '-' end	as membergubunnm	\n";
                sql += "  from tz_member a	\n";
                sql += "	where 1 = 1  \n";

                // 게임,문콘 회원구분추가쿼리
                //sql+= addSql;

                // 검색조건 추가쿼리
                if (ss_searchtype.equals("1")) { // id
                    sql += "  and a.userid like lower(" + SQLString.Format("%" + ss_searchtext + "%") + ")  \n";
                } else if (ss_searchtype.equals("2")) { // 성명
                    sql += "  and a.name like lower(" + SQLString.Format("%" + ss_searchtext + "%") + ") \n";
                }

                // 회원구분 추가쿼리
                if (!ss_searchtype2.equals("ALL")) { // 개인
                    sql += "  and a.membergubun = upper('" + ss_searchtype2 + "')	\n";
                }

                if (v_orderColumn.equals("")) {
                    sql += " order by a.name , a.userid  \n";
                } else {
                    sql += " order by " + v_orderColumn + v_orderType;
                }

                ls = connMgr.executeQuery(sql);

                System.out.println("설문대상자 조회 selectMemberTargetList: " + sql);
                //Log.info.println(">>>"+sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }
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
        return list;
    }

    /**
     * 대상자설문 대상자 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertSulmunMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;

        String ss_subj = box.getString("s_subj");
        String ss_grcode = box.getString("s_grcode");
        String ss_year = box.getString("s_gyear");
        String ss_subjseq = "0001";
        int v_sulpapernum = box.getInt("s_sulpapernum");
        String v_luserid = box.getSession("userid");

        Vector<?> v_checks = box.getVector("p_checks");
        String v_userid = "";
        int cnt = 0;
        int next = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql1 = "select userid from TZ_SULMEMBER";
            sql1 += " where subj = ? and grcode = ? and year = ? and subjseq = ? and sulpapernum = ? and userid = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            sql2 = "insert into TZ_SULMEMBER(subj, grcode, year, subjseq, sulpapernum, userid, luserid, ldate ) ";
            sql2 += " values (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < v_checks.size(); i++) {
                v_userid = (String) v_checks.elementAt(i);

                pstmt1.setString(1, ss_subj);
                pstmt1.setString(2, ss_grcode);
                pstmt1.setString(3, ss_year);
                pstmt1.setString(4, ss_subjseq);
                pstmt1.setInt(5, v_sulpapernum);
                pstmt1.setString(6, v_userid);

                try {
                    rs = pstmt1.executeQuery();

                    if (!rs.next()) {
                        pstmt2.setString(1, ss_subj);
                        pstmt2.setString(2, ss_grcode);
                        pstmt2.setString(3, ss_year);
                        pstmt2.setString(4, ss_subjseq);
                        pstmt2.setInt(5, v_sulpapernum);
                        pstmt2.setString(6, v_userid);
                        pstmt2.setString(7, v_luserid);
                        pstmt2.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));

                        isOk = pstmt2.executeUpdate();

                        cnt += isOk;
                        next++;
                    }
                } catch (Exception ex) {
                    ErrorManager.getErrorStackTrace(ex);
                    throw new Exception(ex.getMessage());
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }

            if (next == cnt) {
                connMgr.commit();
                isOk = cnt;
            } else {
                connMgr.rollback();
                isOk = -1;
            }
        } catch (Exception ex) {
            isOk = 0;
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
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
     * 대상자설문 대상자 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteSulmunMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        StringTokenizer st = null;

        String ss_subj = box.getString("s_subj");
        String ss_grcode = box.getString("s_grcode");
        String ss_year = box.getString("s_gyear");
        String ss_subjseq = "0001";
        int v_sulpapernum = box.getInt("s_sulpapernum");
        // String v_duserid = box.getSession("userid");

        Vector<?> v_checks = box.getVector("p_checks");
        String v_schecks = "";
        String v_userid = "";
        int cnt = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = " delete from TZ_SULMEMBER ";
            sql += " where subj	 = ?  ";
            sql += "	and grcode		= ?  ";
            sql += "	and year		= ?  ";
            sql += "	and subjseq		= ?  ";
            sql += "	and sulpapernum= ?  ";
            sql += "	and userid		= ?  ";

            pstmt = connMgr.prepareStatement(sql);

            for (int i = 0; i < v_checks.size(); i++) {
                v_schecks = (String) v_checks.elementAt(i);
                st = new StringTokenizer(v_schecks, ",");
                v_userid = (String) st.nextToken();

                pstmt.setString(1, ss_subj);
                pstmt.setString(2, ss_grcode);
                pstmt.setString(3, ss_year);
                pstmt.setString(4, ss_subjseq);
                pstmt.setInt(5, v_sulpapernum);
                pstmt.setString(6, v_userid);
                System.out.println("$$$$$$$$$$$$$$$$");
                System.out.println("1 : " + ss_subj);
                System.out.println("2 : " + ss_grcode);
                System.out.println("3 : " + ss_year);
                System.out.println("4 : " + ss_subjseq);
                System.out.println("5 : " + v_sulpapernum);
                System.out.println("6 : " + v_userid);
                isOk = pstmt.executeUpdate();
                cnt += isOk;
            }

            if (v_checks.size() == cnt) {
                connMgr.commit();
                isOk = cnt;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            isOk = 0;
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
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
     * 대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명) TZ_SULPAPER 이용
     */
    public static String getSulpaperSelect(String p_grcode, String p_gyear, String p_subj, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox dbox = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        result += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = "select grcode,		subj,		 ";
            sql += "		sulpapernum,  sulpapernm, year, ";
            sql += "		totcnt,		sulnums, sulmailing, sulstart, sulend, ";
            sql += "		'TARGET'	  subjnm ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode = " + StringManager.makeSQL(p_grcode);
            sql += "	and subj	= " + StringManager.makeSQL(p_subj);
            sql += "	and year	= " + StringManager.makeSQL(p_gyear);
            sql += " order by subj, sulpapernum asc";

            ls = connMgr.executeQuery(sql);

            // String v_null_test = "";
            // String v_subj_bef = "";

            while (ls.next()) {

                dbox = ls.getDataBox();

                result += " <option value=" + dbox.getInt("d_sulpapernum");
                if (selected == dbox.getInt("d_sulpapernum")) {
                    result += " selected ";
                }

                result += ">" + dbox.getString("d_sulpapernm") + "</option> \n";
            }
            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 대상자 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명) TZ_SULPAPER 이용
     */
    public static String getSulpaperSelect2(String p_grcode, String p_gyear, String p_subj, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        DataBox dbox = null;

        result = "  <SELECT name=" + name + " " + event + " > \n";

        result += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = "select grcode,		subj,		 ";
            sql += "		sulpapernum,  sulpapernm, year, ";
            sql += "		totcnt,		sulnums, sulmailing, sulstart, sulend, ";
            sql += "		'TARGET'	  subjnm ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode = " + StringManager.makeSQL(p_grcode);
            sql += "	and subj	= " + StringManager.makeSQL(p_subj);
            sql += "	and year	= " + StringManager.makeSQL(p_gyear);
            //sql+= "	and sulmailing != 'N' ";
            sql += " order by subj, sulpapernum asc";

            ls = connMgr.executeQuery(sql);

            // String v_null_test = "";
            // String v_subj_bef = "";

            while (ls.next()) {

                dbox = ls.getDataBox();

                result += " <option value=" + dbox.getInt("d_sulpapernum");
                if (selected == dbox.getInt("d_sulpapernum")) {
                    result += " selected ";
                }

                result += ">" + dbox.getString("d_sulpapernm") + "</option> \n";
            }
            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 설문메일보내기 정보 등록 tz_sulmailing 이용
     */
    public int insertSulmunMailing(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        StringTokenizer st = null;

        String v_grcode = box.getString("p_grcode");
        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_luserid = box.getSession("userid");
        Vector<?> v_checks = box.getVector("p_checks"); // userid, subj, gyear,subjseq
        String v_schecks = "";
        String v_userid = "";
        String v_subj = "";
        String v_gyear = "";
        String v_subjseq = "";
        String v_ismailcnt = "";
        String v_sulpapernm = "";

        // int cnt = 0;
        // int next = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////////

            // 설문명
            sql2 = " select sulpapernm from tz_sulpaper where sulpapernum = ? ";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setInt(1, v_sulpapernum);
            rs = pstmt2.executeQuery();
            if (rs.next()) {
                v_sulpapernm = rs.getString("sulpapernm");
            }
            rs.close();

            // tz_sulmailing(subj, grcode, year, subjseq, sulpapernum, userid, sulpapernm, name, companynm, deptnm, jikwinm, return ,email, luserid)
            sql1 = " insert into tz_sulmailing  ";
            sql1 += " select ?, ?, ?, ?, ?, ?,  ?, ";
            sql1 += " name, get_compnm(comp,2,2) companynm, get_deptnm(deptnam, userid) deptnm, get_jikwinm(jikwi,comp) jikwinm, 'N', email, ? ";
            sql1 += " from tz_member where userid=? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            for (int i = 0; i < v_checks.size(); i++) {
                v_schecks = (String) v_checks.elementAt(i);
                st = new StringTokenizer(v_schecks, ",");
                v_userid = (String) st.nextToken();
                v_subj = (String) st.nextToken();
                v_gyear = (String) st.nextToken();
                v_subjseq = (String) st.nextToken();
                v_ismailcnt = (String) st.nextToken();

                // tz_sulmailing 테이블에 없는 경우..
                if (Integer.parseInt(v_ismailcnt) < 1) {
                    pstmt1.setString(1, v_subj);
                    pstmt1.setString(2, v_grcode);
                    pstmt1.setString(3, v_gyear);
                    pstmt1.setString(4, v_subjseq);
                    pstmt1.setInt(5, v_sulpapernum);
                    pstmt1.setString(6, v_userid);
                    pstmt1.setString(7, v_sulpapernm);
                    pstmt1.setString(8, v_luserid);
                    pstmt1.setString(9, v_userid);

                    isOk = pstmt1.executeUpdate();
                }
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            isOk = 0;
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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