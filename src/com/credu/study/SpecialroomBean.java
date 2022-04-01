//**********************************************************
//  1. 제      목: SpecialroomBean BEAN
//  2. 프로그램명: SpecialroomBean.java
//  3. 개      요: 특강실 사용자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
package com.credu.study;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class SpecialroomBean {

    private ConfigSet config;
    private int row;

    public SpecialroomBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * 특강실 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroom(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        DataBox dbox = null;

        // String v_tem_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        int v_pageno = box.getInt("p_pageno");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // 특강실 자료 가져오기
            head_sql1 += " select a.seq, a.grcode, b.codenm,  a.subjnm , a.readcnt , a.ldate   \n";
            body_sql1 += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql1 += " where a.subjgubun = b.code  \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.grcode='N000002'   \n"; //게임으로 하드코딩
            order_sql1 += " order by  a.seq desc  \n";

            sql = head_sql1 + body_sql1 + order_sql1;
            ls = connMgr.executeQuery(sql);
            //System.out.println("특강실 리스트 selectSpecialroom: "+sql);

            count_sql1 = "select count(*)" + body_sql1;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1);

            row = 10; //하드코딩
            ls.setPageSize(row); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); //전체 페이지 수를 반환한다

            while (ls.next()) {

                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_total_row_count", new Integer(total_row_count));

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
     * 특강실 상세 보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql2 = "";
        DataBox dbox = null;

        String v_grcode = box.getString("grcode");
        // String v_user_id = box.getSession("userid");
        String v_seq = box.getString("seq");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //조회수 증가
            sql2 = " update   tz_offlinesubj  " + " set  readcnt = (isnull(readcnt,0) + 1)    " + "  where  grcode='N000002' " + "  and  seq = " + v_seq + "    \n";

            connMgr.executeUpdate(sql2);

            // 특강실 자료 가져오기
            sql = "select a.seq, b.codenm,  a.subjseq, a.subjnm, a.ldate,  a.readcnt,  \n" + " a.tuserid, a.tname, a.dday, a.starttime, a.endtime, a.place, a.limitmember, a.target , a.content    \n" + " from  tz_offlinesubj a , tz_code b    \n "
                    + " where a.subjgubun = b.code  \n" + " and a.useYn= 'Y'   " + "  and a.seq = " + v_seq + "    \n" + "  and a.grcode = '" + v_grcode + "'  ";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetail: "+sql);

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
     * 특강실 이전글 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetailPre(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //특강실 이전글 가져오기 
            sql = " 	select * from ( select rownum rnum,  seq, grcode,  subjnm   \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='N000002'   \n" //아직 게임과 문콘의 구분코드를 정하지않았으므로 하드코딩함.
                    + "  and seq < " + v_seq + "  \n" + "	 order by seq	) where rnum < 2";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetailPre: "+sql);

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
     * 특강실 다음글 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetailNext(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //특강실 다음글 가져오기 
            sql = " 	select seq, grcode,  subjnm   \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='N000002'   \n" //아직 게임과 문콘의 구분코드를 정하지않았으므로 하드코딩함.
                    + "  and seq > " + v_seq + "  and rownum = 1\n" + "	 order by seq			";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetailNext: "+sql);

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
     * 특강실 검색내용 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomSearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = ""; //쿼리
        String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        // int v_seq = StringManager.toInt(box.getString("seq"));
        int v_pageno = box.getInt("p_pageno");

        String v_select = box.getString("select"); // 검색조건
        String v_search = (box.getString("search")).trim(); // 검색어

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql1 = " select a.seq, a.grcode, b.codenm,  a.subjnm , a.readcnt , a.ldate   \n";
            body_sql1 += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql1 += " where a.subjgubun = b.code  \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.grcode='N000002'   \n"; //게임으로 하드코딩
            //+" and b.gubun='0035'   \n"	 //구분하드코딩

            //조건 추가쿼리 만들기
            if (v_select.equals("title") && !v_search.equals("")) {
                body_sql1 += " and  a.subjnm like '%" + v_search + "%' ";

            } else if (v_select.equals("content") && !v_search.equals("")) {
                body_sql1 += " and  a.content like '%" + v_search + "%' ";

            } else if (v_select.equals("all") && !v_search.equals("")) {
                body_sql1 += "and ( a.subjnm like '%" + v_search + "%' ";
                body_sql1 += " or  a.content like '%" + v_search + "%' ) ";
            }

            order_sql1 += " order by  a.seq desc  \n";

            sql = head_sql1 + body_sql1 + order_sql1;
            //System.out.println("SpecialroomBean 특강실 검색내용 가져오기 selectSpecialroomSearch:"+sql);		

            ls = connMgr.executeQuery(sql);

            count_sql1 = "select count(*)" + body_sql1;

            //페이징관련
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1);
            row = 10;
            ls.setPageSize(row); //페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); //전체 페이지 수를 반환한다

            while (ls.next()) {

                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_total_row_count", new Integer(total_row_count));

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

}
