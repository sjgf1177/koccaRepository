//**********************************************************
//  1. 제      목: 검색
//  2. 프로그램명: SearchAdminBean.java
//  3. 개      요: 검색모듈
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//**********************************************************

package com.credu.common;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.MemberData;

/**
 * @author s.j Jung
 * 
 */
public class SearchAdminBean {
    public SearchAdminBean() {
    }

    /**
     * 취업 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 취업 상세보기
     * @throws Exception
     */
    public ArrayList<DataBox> searchComp(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        String v_searchtext = box.getString("p_searchtext");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();

            headSql.append(" SELECT  B.COMP, B.COMPNM, D.NAME                                  \n ");
            headSql.append("         , B.TELNO, B.FAXNO, D.EMAIL                               \n ");
            bodySql.append(" FROM    TZ_COMPCLASS B, TZ_MEMBER D                               \n ");
            bodySql.append(" WHERE   B.USERID        = D.USERID(+)                             \n ");

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                bodySql.append(" AND  UPPER(B.COMPNM) LIKE UPPER('%").append(v_searchtext).append("%') \n");
            }

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();

            int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            list = new ArrayList<DataBox>();

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount", new Integer(totalrowcount));

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("Sql = " + sql + "\r\n" + ex.getMessage());
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
     * 그룹코드 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 그룹코드 검색리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchGrcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        SearchData data = null;

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = "  select grcode, grcodenm  ";
            body_sql += "    from TZ_GRCODE         ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("grcode")) { //    CODE로 검색할때
                    body_sql += " where grcode like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("grcodenm")) { //    그룹이름으로 검색할때
                    body_sql += " where grcodenm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by grcode asc     ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            //int total_row_count  = ls.getTotalCount();  //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("grcode"));
                data.setCodenm(ls.getString("grcodenm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 과정 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정 검색리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = "  select subj, subjnm, isonoff  ";
            body_sql += "    from TZ_SUBJ     ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("subj")) { //    코드로 검색할때
                    body_sql += " where subj like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("subjnm")) { //    명칭으로 검색할때
                    body_sql += " where subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by subj asc     ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            // int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("subj"));
                data.setCodenm(ls.getString("subjnm"));
                data.setIsonoff(ls.getString("isonoff"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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

    public ArrayList<SearchData> searchSubjseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        // String head_sql = "";
        String body_sql = "";
        String order_sql = "";
        String online = "";
        String offline = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");
        String v_gadmin = box.getSession("gadmin");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();
            // head_sql = "select tmp1,tmp2,tmp3,tmp4,tmp5,tmp6,tmp7 from ( \n";
            online = "select subj as tmp1, year  as tmp2,subjseq  as tmp3,subjnm  as tmp4,propstart, \n";
            online += "substr(propstart,1,4) || '/' || substr(propstart,5,2) || '/' || substr(propstart,7,2)  as tmp5, \n";
            online += "substr(edustart,1,4) || '/' || substr(edustart,5,2) || '/' || substr(edustart,7,2)  as tmp6 \n";
            online += ", (select a.grcodenm from tz_grcode a where a.grcode = tz_subjseq.grcode) as tmp7 \n";
            online += "from TZ_SUBJseq  \n";
            online += "where len(propstart)=10 \n";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("subj")) { //    코드로 검색할때
                    online += " and subj like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("subjnm")) { //    명칭으로 검색할때
                    online += " and subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            offline = "select subj as tmp1, year  as tmp2,subjseq  as tmp3,subjnm  as tmp4,propstart, \n";
            offline += "substr(propstart,1,4) || '/' || substr(propstart,5,2) || '/' || substr(propstart,7,2)  as tmp5, \n";
            offline += "substr(edustart,1,4) || '/' || substr(edustart,5,2) || '/' || substr(edustart,7,2)  as tmp6 \n";
            offline += ", (select a.grcodenm from tz_grcode a where a.grcode = 'N000001') as tmp7 \n";
            offline += "from TZ_OFFSUBJseq  \n";
            offline += "where len(propstart)=10 \n";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("subj")) { //    코드로 검색할때
                    offline += " and subj like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("subjnm")) { //    명칭으로 검색할때
                    offline += " and subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            if (v_gadmin.equals("A1") || v_gadmin.equals("B1"))
                body_sql = online + " union all \n" + offline;
            else if (v_gadmin.equals("B1"))
                body_sql = online;
            else if (v_gadmin.equals("B2"))
                body_sql = offline;

            //            body_sql += ")x \n";

            order_sql = "   order by propstart desc    ";

            sql = body_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) from (" + body_sql + " )x";
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            // int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();
                data.setTmp1(ls.getString("tmp1"));
                data.setTmp2(ls.getString("tmp2"));
                data.setTmp3(ls.getString("tmp3"));
                data.setTmp4(ls.getString("tmp4"));
                data.setTmp5(ls.getString("tmp5"));
                data.setTmp6(ls.getString("tmp6"));
                data.setTmp7(ls.getString("tmp7"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 부서 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 부서 검색 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = " select comp, compnm, comptype, groupsnm, companynm, gpmnm, deptnm, partnm ";
            body_sql += "from TZ_COMP                                                               ";
            body_sql += " where comptype = 4                                                        ";
            body_sql += "   and isUsed   = 'Y'                                                      ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("comp")) { //    코드로 검색할때
                    body_sql += " and comp like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("deptnm")) { //    명칭으로 검색할때
                    body_sql += " and deptnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by comp asc     ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("comp"));
                data.setCodenm(ls.getString("compnm"));
                data.setTmp1(ls.getString("comptype"));
                data.setTmp2(ls.getString("groupsnm"));
                data.setTmp3(ls.getString("companynm"));
                data.setTmp4(ls.getString("gpmnm"));
                data.setTmp5(ls.getString("deptnm"));
                data.setTmp6(ls.getString("partnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 회사 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 부서 검색 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchGrpComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        StringBuilder sql = new StringBuilder();
        StringBuilder countSql = new StringBuilder();

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        String v_key2 = box.getString("p_key2");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            if (v_key2.equals("p_producer")) { //제공회사 - 베타업체
                sql.append("SELECT  COMP         \n");
                sql.append("    ,   COMPNM       \n");
                sql.append("    ,   COMPTYPE     \n");
                sql.append("    ,   GROUPSNM     \n");
                sql.append("    ,   COMPANYNM    \n");
                sql.append("    ,   GPMNM        \n");
                sql.append("    ,   DEPTNM       \n");
                sql.append("    ,   PARTNM       \n");
                sql.append("  FROM  TZ_COMP      \n");
                sql.append(" WHERE  COMPTYPE = 2 \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("")) { //    코드로 검색할때
                        sql.append("   AND  COMP LIKE '%").append(v_searchtext).append("%'  \n");
                    } else if (v_search.equals("companynm")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(COMPNM) LIKE UPPER ('%").append(v_searchtext).append("%'   \n");
                    }
                }
                sql.append(" ORDER  BY COMP, COMPNM ASC  \n");

            } else if (v_key2.equals("p_owner")) { //소유회사 - TZ_CPINFO
                sql.append("SELECT  CPSEQ AS COMP                        \n");
                sql.append("    ,   CPNM AS COMPNM                       \n");
                sql.append("    ,   2 AS COMPTYPE                        \n");
                sql.append("    ,   '' AS GROUPSNM                       \n");
                sql.append("    ,   CPNM AS COMPANYNM                    \n");
                sql.append("    ,   '' AS GPMNM                          \n");
                sql.append("    ,   '' AS DEPTNM                         \n");
                sql.append("    ,   '' AS PARTNM                         \n");
                sql.append("    ,   '1' AS SEQ                           \n");
                sql.append("  FROM  TZ_CPINFO                            \n");
                sql.append(" WHERE  (COMPGUBUN = 'S' OR COMPGUBUN = 'M') \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("")) { //    코드로 검색할때
                        sql.append("   AND  CPRESNO LIKE '%").append(v_searchtext).append("%'   \n");
                    } else if (v_search.equals("companynm")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(CPNM) LIKE UPPER ('%").append(v_searchtext).append("%')   \n");
                    }
                }
                sql.append(" ORDER  BY SEQ, COMPNM ASC   \n");

            } else if (v_key2.equals("p_cpinfo")) { //외주업체검색
                sql.append("SELECT  CPSEQ AS COMP                        \n");
                sql.append("    ,   CPNM AS COMPNM                       \n");
                sql.append("    ,   2 AS COMPTYPE                        \n");
                sql.append("    ,   '' AS GROUPSNM                       \n");
                sql.append("    ,   CPNM AS COMPANYNM                    \n");
                sql.append("    ,   '' AS GPMNM                          \n");
                sql.append("    ,   '' AS DEPTNM                         \n");
                sql.append("    ,   '' AS PARTNM                         \n");
                sql.append("  FROM  TZ_CPINFO                            \n");
                sql.append(" WHERE  (COMPGUBUN = 'S' OR COMPGUBUN = 'M') \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("")) { //    코드로 검색할때
                        sql.append("   AND  CPRESNO LIKE '%").append(v_searchtext).append("%' \n");
                    } else if (v_search.equals("companynm")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(CPNM) LIKE UPPER ( '%").append(v_searchtext).append("%')    \n");
                    }
                }
                sql.append(" ORDER BY CPNM ASC   \n");

            } else if (v_key2.equals("p_bpinfo")) { //베타업체검색
                sql.append("SELECT  CPSEQ AS COMP                        \n");
                sql.append("    ,   CPNM AS COMPNM                       \n");
                sql.append("    ,   2 AS COMPTYPE                        \n");
                sql.append("    ,   '' AS GROUPSNM                       \n");
                sql.append("    ,   CPNM AS COMPANYNM                    \n");
                sql.append("    ,   '' AS GPMNM                          \n");
                sql.append("    ,   '' AS DEPTNM                         \n");
                sql.append("    ,   '' PARTNM                            \n");
                sql.append("  FROM  TZ_CPINFO                            \n");
                sql.append(" WHERE  (COMPGUBUN = 'T' OR COMPGUBUN = 'M'  \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("")) { //    코드로 검색할때
                        sql.append("   AND  CPRESNO LIKE '%").append(v_searchtext).append("%'  \n");
                    } else if (v_search.equals("companynm")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(CPNM) LIKE UPPER ( '%").append(v_searchtext).append("%') \n");
                    }
                }
                sql.append(" ORDER BY CPNM ASC   \n");

            } else if (v_key2.equals("p_cpbpinfo")) { //외주업체검색
                sql.append("SELECT  CPSEQ AS COMP        \n");
                sql.append("    ,   CPNM AS COMPNM       \n");
                sql.append("    ,   2 AS COMPTYPE        \n");
                sql.append("    ,   '' AS GROUPSNM       \n");
                sql.append("    ,   CPNM AS COMPANYNM    \n");
                sql.append("    ,   '' AS GPMNM          \n");
                sql.append("    ,   '' AS DEPTNM         \n");
                sql.append("    ,   '' AS PARTNM         \n");
                sql.append("  FROM  TZ_CPINFO            \n");
                sql.append(" WHERE  COMPGUBUN = 'M'      \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("")) { //    코드로 검색할때
                        sql.append("   AND  CPRESNO LIKE '%").append(v_searchtext).append("%'   \n");
                    } else if (v_search.equals("companynm")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(CPNM) LIKE UPPER( '%").append(v_searchtext).append("%' ) \n");
                    }
                }
                sql.append(" ORDER BY CPNM ASC   \n");

            } else {
                sql.append("SELECT  COMP         \n");
                sql.append("    ,   COMPNM       \n");
                sql.append("    ,   COMPTYPE     \n");
                sql.append("    ,   GROUPSNM     \n");
                sql.append("    ,   COMPANYNM    \n");
                sql.append("    ,   GPMNM        \n");
                sql.append("    ,   DEPTNM       \n");
                sql.append("    ,   PARTNM       \n");
                sql.append("  FROM  TZ_COMP      \n");
                sql.append(" WHERE  COMPTYPE = 2 \n");
                sql.append("   AND  ISUSED = 'Y' \n");

                if (!v_searchtext.equals("")) { //    검색어가 있으면
                    if (v_search.equals("comp")) { //    코드로 검색할때
                        sql.append("   AND  COMP LIKE '%").append(v_searchtext).append("%' \n");
                    } else if (v_search.equals("companynm") || v_search.equals("")) { //    명칭으로 검색할때
                        sql.append("   AND  UPPER(COMPANYNM) LIKE UPPER( '%").append(v_searchtext).append("%') \n");
                    }
                }
                sql.append(" ORDER BY COMP ASC   \n");
            }

            // sql = head_sql + body_sql + head_sql1 + body_sql1 + group_sql + order_sql;
            ls = connMgr.executeQuery(sql.toString());

            countSql.append("SELECT COUNT(*) FROM (").append(sql.toString()).append(") A    \n");
            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql.toString()); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("comp"));
                data.setCodenm(ls.getString("compnm"));
                data.setTmp1(ls.getString("comptype"));
                data.setTmp2(ls.getString("groupsnm"));
                data.setTmp3(ls.getString("companynm"));
                data.setTmp4(ls.getString("gpmnm"));
                data.setTmp5(ls.getString("deptnm"));
                data.setTmp6(ls.getString("partnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 회원조회 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 회원 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = "  select distinct grcode, userid, resno, name, crypto.dec('normal',email) email, comptel, comptext, registgubun, jikup";
            body_sql += "    from TZ_MEMBER   ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("userid")) { //    ID로 검색할때
                    body_sql += " where state = 'Y' and upper(userid) like   upper(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("name")) { //    이름으로 검색할때
                    body_sql += " where state = 'Y' and name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            } else {
                body_sql += " where userid='notselect'";
            }
            order_sql += "   order by name asc, userid asc  ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   	// 전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("userid"));
                data.setCodenm(ls.getString("name"));
                data.setTmp1(ls.getString("resno"));
                data.setTmp2(ls.getString("email"));
                data.setTmp3(ls.getString("comptext"));
                data.setTmp4(ls.getString("registgubun"));
                data.setTmp5(ls.getString("comptel"));
                data.setTmp6(ls.getString("jikup"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                if (ls.getString("grcode").equals("N000001")) {
                    //====================================================
                    // 개인정보 복호화 -HTJ                	
                    //EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,Constants.APP_IV);
                    //if (!ls.getString("email").equals("")) data.setTmp2(encryptUtil.decrypt(ls.getString("email")));
                    //====================================================
                }

                list.add(data);
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
     * 결재상신대상 회원조회 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 회원 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchAppMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_searchtext");
        int v_pageno = box.getInt("p_pageno");
        //        String v_comp  = box.getSession("comp");
        String v_jikwi = box.getSession("jikwi");

        System.out.println("v_jikwi====" + v_jikwi);

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = "  select userid, resno, name, jikwi, get_jikwinm(jikwi,comp) jikwinm,  ";
            head_sql += "         email, cono, comp, get_compnm(comp,2,2) compnm                ";
            body_sql += "    from TZ_MEMBER                                                     ";
            body_sql += "  where                                                                ";
            body_sql += "  office_gbn = 'Y'                                                     ";
            body_sql += "  and jikwi  <= '" + v_jikwi + "' ";
            //sql += "  and deptcod = (select deptcod from tz_member where userid = '"+box.getSession("userid")+"')\n";
            body_sql += "  and ( (divinam + deptnam) = (select (divinam + deptnam) from tz_member where userid = '" + box.getSession("userid") + "') or \n";
            body_sql += "  ( select deptmresno from tz_comp where tz_comp.comp = '" + box.getSession("comp") + "' ) = TZ_MEMBER.userid  or ( deptcod = (select deptcod from tz_member where userid = '" + box.getSession("userid") + "') ) )\n";
            //sql += "  and comp    = '"+v_comp+"' ";
            body_sql += "  and userid  != '" + box.getSession("userid") + "' ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("userid")) { //    ID로 검색할때
                    body_sql += "  and userid like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("cono")) { //    사번으로 검색할때
                    body_sql += "  and cono like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("name")) { //    이름으로 검색할때
                    body_sql += " and name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            order_sql += "   order by comp asc, jikwi asc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);
            System.out.println("팀장조회====>" + sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //  현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //  전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   	//  전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("userid"));
                data.setCodenm(ls.getString("name"));
                data.setTmp1(ls.getString("resno"));
                data.setTmp2(ls.getString("email"));
                data.setTmp3(ls.getString("cono"));
                data.setTmp4(ls.getString("jikwi"));
                data.setTmp5(ls.getString("jikwinm"));
                data.setTmp6(ls.getString("comp"));
                data.setTmp7(ls.getString("compnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 컨텐츠 제공업체/담당자 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 베타테스트 컨텐츠 제공업체/담당자 검색 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchBetaCompany(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = " select userid, usernm, betacpno, betacpnm  ";
            body_sql += " from TZ_BETACPINFO                          ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("companyno")) { //   사업자등록번호로 검색할때
                    body_sql += " where betacpno like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("companyname")) { //    명칭으로 검색할때
                    body_sql += " where betacpnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by betacpno asc     ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //  현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //  전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   	//  전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setUserid(ls.getString("userid"));
                data.setUsernm(ls.getString("usernm"));
                data.setCode(ls.getString("betacpno"));
                data.setCodenm(ls.getString("betacpnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 교육주관처 검색
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육주관처 검색 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchCompany(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = " select companyno, companyname, telephone, musername  ";
            body_sql += "from TZ_CONSIGNCOM                                    ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("companyno")) { //   사업자등록번호로 검색할때
                    body_sql += " where companyno like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("companyname")) { //    명칭으로 검색할때
                    body_sql += " where companyname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by companyno asc     ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //  현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //  전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   	//  전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("companyno"));
                data.setCodenm(ls.getString("companyname"));
                data.setTmp1(ls.getString("telephone"));
                data.setTmp2(ls.getString("musername"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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
     * 개인정보조회
     * 
     * @param box receive from the form object and session
     * @return MemberData
     */
    public MemberData selectPersonalInformation(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        Connection conn = null;
        MemberData data = null;
        ListSet ls = null;
        String sql = "";

        String v_userid = box.getString("p_userid");
        // String v_grcode = box.getString("s_grcode");
        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();

            // 개인정보 복호화- HTJ 
            sql = "select \n";
            sql += "  resno, name, crypto.dec('normal',email) email, comptel, crypto.dec('normal',hometel) hometel ,cono, pwd, 	resno1, resno2,  \n";
            sql += "  crypto.dec('normal',handphone) handphone, post1, post2, addr, addr2, jikupnm ,	 \n";
            sql += "		 case when  membergubun = 'P' then  '개인' \n";
            sql += "	   	  when  membergubun = 'C' then  '기업'   	 \n";
            sql += "	   	  when  membergubun = 'U' then  '대학교'    \n";
            sql += "	   	  else '-' end   as membergubunnm , grcode 			 \n";
            sql += "from TZ_MEMBER \n";
            sql += "where userid = " + SQLString.Format(v_userid);
            //sql += "and grcode = "+SQLString.Format(v_grcode);

            //System.out.println("개인정보 조회:"+sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new MemberData();
                //data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComptel(ls.getString("comptel"));
                data.setHometel(ls.getString("hometel"));
                data.setHandphone(ls.getString("handphone"));
                data.setCono(ls.getString("cono"));
                data.setPwd(ls.getString("pwd"));
                data.setPost1(ls.getString("post1"));
                data.setPost2(ls.getString("post2"));
                data.setAddr(ls.getString("addr"));
                data.setAddr2(ls.getString("addr2"));
                data.setJikupnm(ls.getString("jikupnm"));
                data.setUserid(v_userid);
                data.setMembergubunnm(ls.getString("membergubunnm"));
                data.setResno1(ls.getString("resno1"));
                data.setResno2(ls.getString("resno2"));
                data.setGrcode(ls.getString("grcode"));

                //if (v_grcode.equals("N000001")) {
                //====================================================
                // 개인정보 복호화-HTJ
                /*
                 * SeedCipher seed = new SeedCipher(); if
                 * (!ls.getString("resno2").equals(""))
                 * data.setResno2(seed.decryptAsString
                 * (Base64.decode(ls.getString("resno2")), seed.key.getBytes(),
                 * "UTF-8")); if (!ls.getString("email").equals(""))
                 * data.setEmail
                 * (seed.decryptAsString(Base64.decode(ls.getString("email")),
                 * seed.key.getBytes(), "UTF-8")); if
                 * (!ls.getString("handphone").equals(""))
                 * data.setHandphone(seed
                 * .decryptAsString(Base64.decode(ls.getString("handphone")),
                 * seed.key.getBytes(), "UTF-8")); if
                 * (!ls.getString("hometel").equals(""))
                 * data.setHometel(seed.decryptAsString
                 * (Base64.decode(ls.getString("hometel")), seed.key.getBytes(),
                 * "UTF-8")); if (!ls.getString("addr2").equals(""))
                 * data.setAddr2
                 * (seed.decryptAsString(Base64.decode(ls.getString("addr2")),
                 * seed.key.getBytes(), "UTF-8"));
                 */
                /*
                 * if (!ls.getString("resno2").equals(""))
                 * data.setResno2(encryptUtil.decrypt(ls.getString("resno2")));
                 * if (!ls.getString("email").equals(""))
                 * data.setEmail(encryptUtil.decrypt(ls.getString("email"))); if
                 * (!ls.getString("handphone").equals(""))
                 * data.setHandphone(encryptUtil
                 * .decrypt(ls.getString("handphone"))); if
                 * (!ls.getString("hometel").equals("") &&
                 * !ls.getString("hometel").equals("-"))
                 * data.setHometel(encryptUtil
                 * .decrypt(ls.getString("hometel"))); if
                 * (!ls.getString("addr2").equals(""))
                 * data.setAddr2(encryptUtil.decrypt(ls.getString("addr2")));
                 */
                //====================================================
                //}

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
        return data;
    }

    /**
     * 협력업체 개인정보조회
     * 
     * @param box receive from the form object and session
     * @return MemberData
     */
    public DataBox selectOutPersonalInformation(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        Connection conn = null;
        DataBox dbox = null;
        ListSet ls = null;
        String sql = "";

        String v_userid = box.getString("p_userid");
        try {
            connMgr = new DBConnectionManager();
            sql = "select resno, name, email, comptel, hometel, cono, pwd ";
            sql += " from TZ_OUTMEMBER ";
            sql += " where userid = " + SQLString.Format(v_userid);

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
     * 교수/튜터 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교수/튜터 리스트
     * @throws Exception
     */
    public ArrayList<SearchData> searchTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SearchData> list = null;
        SearchData data = null;

        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        String v_search = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        String v_gubun = box.getString("p_key2");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<SearchData>();

            head_sql = "  select userid, resno, name, email, phone ";
            body_sql += "    from TZ_TUTOR   ";
            body_sql += "   where isgubun  = " + StringManager.makeSQL(v_gubun);

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                if (v_search.equals("userid")) { //    ID로 검색할때
                    body_sql += " and userid like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("name")) { //    이름으로 검색할때
                    body_sql += " and name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            } else {
                body_sql += " and userid='notselect'";
            }
            order_sql += "   order by name asc, userid asc  ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            //int total_row_count = ls.getTotalCount();   	// 전체 row 수를 반환한다

            while (ls.next()) {
                data = new SearchData();

                data.setCode(ls.getString("userid"));
                data.setCodenm(ls.getString("name"));
                data.setTmp1(ls.getString("resno"));
                data.setTmp2(ls.getString("email"));
                data.setTmp3(ls.getString("phone"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalpagecount(total_page_count);

                list.add(data);
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

}
