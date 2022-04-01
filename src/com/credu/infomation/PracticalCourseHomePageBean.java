//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.infomation;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 */
public class PracticalCourseHomePageBean {

    private ConfigSet config;
    private int row;

    public PracticalCourseHomePageBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 실무강좌 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 실무강좌 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String countSql = "";
        String sql = "";
        DataBox dbox = null;

        String v_search = box.getString("p_search");
        String v_searchtext = box.getString("p_searchtext");

        String v_selDtlCd = box.getStringDefault("p_selDtlCd", "ALL");
        String v_selContentType = box.getStringDefault("p_selContentType", "ALL");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            headSql.append(" SELECT                                                   \n ");
            headSql.append("         A.NUM, A.CLSFCD, A.DTLCD, A.TITLE, A.CONTENTTYPE \n ");
            headSql.append("         , A.CNT, A.PROFESSOR, A.INDATE, A.NAME           \n ");
            headSql.append("         , A.MAINYN, A.USECHK, B.CODENM CONTENTTYPENM     \n ");
            headSql.append("         , C.CODENM DTLNM, A.USECHK                       \n ");

            bodySql.append(" FROM    TZ_PORTFOLIO A, TZ_CODE B, TZ_CODE C             \n ");
            bodySql.append(" WHERE   A.CONTENTTYPE   = B.CODE(+)                      \n ");
            bodySql.append(" AND     B.GUBUN(+)      = '0085'                         \n ");
            bodySql.append(" AND     A.DTLCD         = C.CODE(+)                      \n ");
            bodySql.append(" AND     C.GUBUN(+)      = '0086'                         \n ");
            bodySql.append(" AND     A.USECHK        = 'Y'                            \n ");

            if (!v_searchtext.equals("")) { //    검색어가 있으면                                         
                if (v_search.equals("title")) { //    제목으로 검색할때  
                    bodySql.append(" AND     A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("content")) { //    내용으로 검색할때  
                    bodySql.append(" AND     A.CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }

            if (!v_selDtlCd.equals("ALL")) {
                bodySql.append(" AND     A.DTLCD = " + StringManager.makeSQL(v_selDtlCd) + " \n");
            }

            if (!v_selContentType.equals("ALL")) {
                bodySql.append(" AND     A.CONTENTTYPE = " + StringManager.makeSQL(v_selContentType) + " \n");
            }
            orderSql = " ORDER BY     A.INDATE DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();

            int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql);
            // int total_page_count = ls.getTotalPage(); //전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); //현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalrowcount));
                list.add(dbox);
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
     * 화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectView(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT                                  \n ");
            sql.append("         A.CLSFCD                        \n ");
            sql.append("         , A.DTLCD                       \n ");
            sql.append("         , A.NUM                         \n ");
            sql.append("         , A.USERID                      \n ");
            sql.append("         , A.NAME                        \n ");
            sql.append("         , A.TITLE                       \n ");
            sql.append("         , A.CONTENT                     \n ");
            sql.append("         , A.PROFESSOR                   \n ");
            sql.append("         , A.UPFILE                      \n ");
            sql.append("         , A.SVRFILE                     \n ");
            sql.append("         , A.IMAGEURL                    \n ");
            sql.append("         , A.PICTUREURL                  \n ");
            sql.append("         , A.GUBUN                       \n ");
            sql.append("         , A.GENRE                       \n ");
            sql.append("         , A.INSPECTOR                   \n ");
            sql.append("         , A.RUNNINGTIME                 \n ");
            sql.append("         , A.PRODUCTION                  \n ");
            sql.append("         , A.ANALYZE                     \n ");
            sql.append("         , A.CNT                         \n ");
            sql.append("         , A.USECHK                      \n ");
            sql.append("         , A.SPLECTURENM                 \n ");
            sql.append("         , A.SPCONTENT                   \n ");
            sql.append("         , A.MAINYN                      \n ");
            sql.append("         , A.CONTENTTYPE                 \n ");
            sql.append("         , A.INDATE                      \n ");
            sql.append("         , A.LUSERID                     \n ");
            sql.append("         , A.LDATE                       \n ");
            sql.append("         , A.PROFESSORIMG                \n ");
            sql.append("         , A.WIDTH		                 \n ");
            sql.append("         , A.HEIGHT		                 \n ");
            sql.append("         , B.CODENM CONTENTTYPENM        \n ");
            sql.append(" FROM                                    \n ");
            sql.append("         TZ_PORTFOLIO A                  \n ");
            sql.append("         , TZ_CODE    B                  \n ");
            sql.append(" WHERE                                   \n ");
            sql.append("         A.CONTENTTYPE = B.CODE(+)       \n ");
            sql.append(" AND     B.GUBUN(+)    = '0085'          \n ");
            sql.append(" AND     A.NUM    = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
            }

            if (v_process.equals("selectView")) {
                connMgr.executeUpdate(" update TZ_PORTFOLIO set cnt = nvl(cnt, 0) + 1 where num = " + v_seq);
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
        return dbox;
    }

}
