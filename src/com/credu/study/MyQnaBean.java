//**********************************************************
//1. 제      목: 나의 질문방
//2. 프로그램명: MyQnaServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년
//7. 수      정: 이나연 05.11.24 _(+)  수정
//
//**********************************************************
package com.credu.study;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 
 * @author kocca
 *
 */
public class MyQnaBean {

    private ConfigSet config;
    private int row;

    public MyQnaBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 나의 질문방 학습관련 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public ArrayList<DataBox> SelectMyQnaStudyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        // 수정일 : 05.11.14 수정자 : 이나연 _ totalcount 하기 위한 쿼리수벙
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String body_wsql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_user_id = box.getSession("userid");
        String v_grcode = box.getStringDefault("p_grcode", box.getSession("tem_grcode"));
        int v_pageno = box.getInt("p_pageno");

        System.out.println("v_grcode : " + v_grcode);

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            if (!v_searchtext.equals("")) { // 검색어가 있으면
                if (v_select.equals("title")) { // 제목으로 검색할때
                    body_wsql = " and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_select.equals("content")) { // 내용으로 검색할때 // Oracle 9i 용
                    body_wsql = " and a.contents like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            head_sql = "  SELECT  a.subj, a.year, a.subjseq, b.scsubjnm, b.edustart, b.eduend , a.title, a.contents, a.kind, a.seq, a.upfile, a.indate, a.togubun,  ";
            head_sql += "         (select  count(realfile) cnt from tz_qnafile where subj=a.subj and year=a.year and subjseq=a.subjseq and lesson=a.lesson and seq=a.seq and kind=a.kind) upfilecnt    ";
            body_sql += " FROM   tz_qna a, vz_scsubjseq b  ";
            body_sql += " WHERE a.subj=b.scsubj and   a.year=b.scyear and    a.subjseq=b.scsubjseq   and a.grcode=" + StringManager.makeSQL(v_grcode);
            //+ "   and a.inuserid="+SQLString.Format(v_user_id)+" "
            //+ "   and a.seq in (select t.seq from tz_qna t where t.inuserid="+SQLString.Format(v_user_id)+") "
            body_sql += "     and a.seq in (select t.seq from tz_qna t where t.inuserid=" + SQLString.Format(v_user_id) + "  and t.subj = b.subj and t.subjseq = b.subjseq and t.year = b.year and kind = 0) ";
            body_sql += body_wsql;
            order_sql += " ORDER BY a.subj, a.year, a.subjseq, a.seq desc, a.kind asc ";

            sql = head_sql + body_sql + body_wsql + group_sql + order_sql;
            System.out.println(" MyQnaBean   sql >>> " + sql);
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            System.out.println(" MyQnaBean   count >>> " + count_sql);
            int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            // int total_row_count  = ls.getTotalCount();

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
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
     * 나의 질문방 사이트관련 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public ArrayList<DataBox> SelectMyQnaSiteList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";
        String wsql = "";
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_user_id = box.getSession("userid");
        String v_grcode = box.getStringDefault("p_grcode", box.getSession("tem_grcode"));
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            if (!v_searchtext.equals("")) { // 검색어가 있으면
                if (v_select.equals("title")) { // 제목으로 검색할때
                    wsql = " and lower(title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_select.equals("content")) { // 내용으로 검색할때 // Oracle 9i 용
                    wsql = " and contents like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            head_sql1 = "  SELECT a.tabseq, b.codenm, a.seq , a.types, a.title, a.contents, a.indate, a.upfile , a.cnt,  ";
            head_sql1 += "          (  select count(realfile) cnt from tz_homefile where tabseq=a.tabseq and seq=a.seq  and types = a.types ) upfilecnt    ";
            body_sql1 += " FROM   tz_homeqna a , tz_code b  ";
            body_sql1 += " WHERE a.categorycd = b.code      ";
            body_sql1 += " 		and b.gubun='0046' and b.levels='1' and a.grcode=" + StringManager.makeSQL(v_grcode);
            body_sql1 += " 		and a.seq in (select t.seq from tz_homeqna t where t.tabseq = a.tabseq and t.seq = a.seq and t.types = '0' and t.inuserid=" + SQLString.Format(v_user_id) + " ) ";
            body_sql1 += wsql;
            order_sql1 += " ORDER BY tabseq, seq desc, types asc ";
            ;

            sql1 = head_sql1 + body_sql1 + order_sql1;
            System.out.println(sql1);
            ls = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
     * 나의 질문방 학습 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public DataBox selectMyQnaStudy(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            //sql = " select title, contents, indate, inuserid, ";
            ////sql += " (select name from tz_member where userid='"+v_userid+"' ) name ";
            //sql += " get_name(inuserid) name ";
            //sql += " from tz_qna where  ";
            //sql += " subj = '"+v_subj+"'";
            //sql += " and year = '"+v_year+"'";
            //sql += " and subjseq = '"+v_subjseq+"'";
            ////sql += " and seq="+v_seq+" and kind='"+v_types+"' and inuserid='"+v_userid+"' ";
            //sql += " and seq="+v_seq+" and kind='"+v_types+"' ";

            sql += " select a.title, a.contents, a.indate, a.inuserid, get_name(a.inuserid) name,    b.fileseq, b.realfile, b.savefile  ";
            sql += " from  tz_qna a, tz_qnafile b                                                                                       ";
            // 수정일 : 05.11.24 수정자 : 이나연 _(+)  수정
            //          sql += " where   a.subj = b.subj(+) and a.year = b.year(+) and a.subjseq = b.subjseq(+) and a.lesson = b.lesson(+)  and a.seq = b.seq(+)   and a.kind = b.kind(+)  ";
            sql += " where   a.subj = b.subj(+) and a.year = b.year(+) and a.subjseq = b.subjseq(+) and a.lesson = b.lesson(+)  and a.seq = b.seq(+)   and a.kind = b.kind(+)  ";
            sql += "  and a.subj = '" + v_subj + "'";
            sql += "  and a.year = '" + v_year + "'";
            sql += "  and a.subjseq = '" + v_subjseq + "'";
            sql += "  and a.seq=" + v_seq + " and a.kind='" + v_types + "' ";

            //System.out.println(sql);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                // 수정일 : 05.11.24 수정자 : 이나연
                //                realfileVector.addElement(ls.getString("realfile"));
                //                savefileVector.addElement(ls.getString("savefile"));
                //				fileseqVector.addElement(String.valueOf(ls.getInt("fileseq")));
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

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
     * 나의 질문방 사이트 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public DataBox selectMyQnaSite(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        int v_tabseq = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();

            sql = "select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, b.fileseq, b.realfile, b.savefile, a.indate ,c.name, a.cnt ";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c";
            // 수정일 : 05.11.24 수정자 : 이나연 _(+)  수정
            //          sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
            //          sql += " and a.inuserid = c.userid(+)";
            sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
            sql += "   and a.inuserid = c.useri(+)d";
            sql += "   and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types = " + SQLString.Format(v_types);

            //System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = " + v_seq + " and types = " + SQLString.Format(v_types);
            connMgr.executeUpdate(sql);

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
     * 나의 질문방 학습 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    /*
     * public DataBox selectMyQnaStudy(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; ListSet ls = null; String sql = "";
     * DataBox dbox = null;
     * 
     * int v_seq = box.getInt("p_seq"); String v_types =
     * box.getString("p_types"); String v_userid = box.getString("p_userid");
     * String v_subj = box.getString("p_subj"); String v_year =
     * box.getString("p_year"); String v_subjseq = box.getString("p_subjseq");
     * 
     * try { connMgr = new DBConnectionManager(); //sql =
     * " select title, contents, indate, inuserid, "; ////sql +=
     * " (select name from tz_member where userid='"+v_userid+"' ) name "; //sql
     * += " get_name(inuserid) name "; //sql += " from tz_qna where  "; //sql +=
     * " subj = '"+v_subj+"'"; //sql += " and year = '"+v_year+"'"; //sql +=
     * " and subjseq = '"+v_subjseq+"'"; ////sql +=
     * " and seq="+v_seq+" and kind='"+v_types+"' and inuserid='"+v_userid+"' ";
     * //sql += " and seq="+v_seq+" and kind='"+v_types+"' ";
     * 
     * 
     * sql +=
     * " select a.title, a.contents, a.indate, a.inuserid, get_name(a.inuserid) name,    b.fileseq, b.realfile, b.savefile  "
     * ; sql +=
     * " from  tz_qna a, tz_qnafile b                                                                                       "
     * ; sql +=
     * " where   a.subj = b.subj(+) and a.year = b.year(+) and a.subjseeq = b.subjseeq(+) and a.lesson = b.lesson(+)  and a.seq = b.seq(+)   and a.kind = b.kind(+)  "
     * ; sql += "  and a.subj = '"+v_subj+"'"; sql +=
     * "  and a.year = '"+v_year+"'"; sql +=
     * "  and a.subjseq = '"+v_subjseq+"'"; sql +=
     * "  and a.seq="+v_seq+" and a.kind='"+v_types+"' ";
     * 
     * 
     * 
     * 
     * ls = connMgr.executeQuery(sql); if (ls.next()) { dbox = ls.getDataBox();
     * }
     * 
     * } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box, sql);
     * throw new Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally
     * { if(ls != null) {try {ls.close();} catch(Exception e){}} if(connMgr !=
     * null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     * return dbox; }
     */

    /**
     * 나의 질문방 사이트 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    /*
     * public DataBox selectMyQnaSite(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; ListSet ls = null; String sql = "";
     * DataBox dbox = null;
     * 
     * int v_seq = box.getInt("p_seq"); int v_tabseq = box.getInt("p_tabseq");
     * String v_types = box.getString("p_types"); String v_userid =
     * box.getString("p_userid");
     * 
     * try { connMgr = new DBConnectionManager(); sql =
     * " select title, contents, indate, inuserid, upfile, "; //sql +=
     * " (select name from tz_member where userid='"+v_userid+"' ) name "; sql
     * += " get_name(inuserid) name, cnt "; sql +=
     * " from tz_homeqna where tabseq="
     * +v_tabseq+" and seq="+v_seq+" and types='"+v_types+"' ";
     * Log.info.println("myqnasite>>"+sql);
     * 
     * ls = connMgr.executeQuery(sql); if (ls.next()) { dbox = ls.getDataBox();
     * }
     * 
     * sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq +
     * " and seq = "+v_seq + " and types = " + SQLString.Format(v_types);
     * connMgr.executeUpdate(sql);
     * 
     * } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box, sql);
     * throw new Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally
     * { if(ls != null) {try {ls.close();} catch(Exception e){}} if(connMgr !=
     * null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     * return dbox; }
     */

    /**
     * 나의 상담내역 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public ArrayList<DataBox> SelectMyQnaCounselList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        String v_type = box.getStringDefault("p_type", "ALL");

        String v_userid = box.getSession("userid");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            head_sql1 = " select type, tabseq, no, userid, cuserid, title, status, sdate, ldate, subj, year, subjseq, types, upfilecnt ";
            body_sql1 += "\n   from ( ";
            /*
             * 유선상담제외 if ("ALL".equals(v_type)) { body_sql1 +=
             * "\n         select 'OFF' type, null tabseq, a.no, a.userid, a.cuserid, a.title, "
             * ; body_sql1 +=
             * "\n                a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq, '' types,"
             * ; body_sql1 += "\n                0 upfilecnt "; body_sql1 +=
             * "\n           from TZ_SANGDAM a "; body_sql1 +=
             * "\n          where a.userid = " +
             * StringManager.makeSQL(v_userid);
             * 
             * if ( !v_searchtext.equals("")) { // 검색어가 있으면 if
             * (v_select.equals("title")) { // 제목으로 검색할때 body_sql1 +=
             * "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%"
             * + v_searchtext + "%")+")"; } else if (v_select.equals("content"))
             * { // 제목 + 내용으로 검색할때 // Oracle 9i 용 body_sql1 +=
             * "\n and ((lower(a.title) like lower ( " +
             * StringManager.makeSQL("%" + v_searchtext +
             * "%")+")) OR (a.ctext like " + StringManager.makeSQL("%" +
             * v_searchtext + "%") + "))"; } } } if ("ALL".equals(v_type)) {
             * body_sql1 += "\n         union all "; }
             */
            if ("ALL".equals(v_type) || "PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) {
                body_sql1 += "\n         select b.type, a.tabseq, a.seq no, a.inuserid userid, a.inuserid cuserid, a.title, ";
                body_sql1 += "\n                a.okyn1 status, a.indate sdate, a.ldate, '' subj, SUBSTR(indate,1,4) year, '' subjseq, a.types, ";
                body_sql1 += "\n                (select count(*) from tz_homefile where tabseq = a.tabseq and seq = a.seq) upfilecnt ";
                body_sql1 += "\n           from tz_homeqna a, tz_bds b ";
                body_sql1 += "\n          where a.inuserid = " + StringManager.makeSQL(v_userid);
                body_sql1 += "\n            and a.types    = '0' ";
                body_sql1 += "\n            and a.tabseq   = b.tabseq ";
                if (!"ALL".equals(v_type)) {
                    body_sql1 += "\n        and b.type     = " + StringManager.makeSQL(v_type);
                }
                body_sql1 += "\n            and b.type     <> 'KB' ";
                if (!v_searchtext.equals("")) { // 검색어가 있으면
                    if (v_select.equals("title")) { // 제목으로 검색할때
                        body_sql1 += "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                    } else if (v_select.equals("content")) { // 제목 + 내용으로 검색할때 // Oracle 9i 용
                        body_sql1 += "\n and ((lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")) OR (a.contents like " + StringManager.makeSQL("%" + v_searchtext + "%") + "))";
                    }
                }
            }
            if ("ALL".equals(v_type)) {
                body_sql1 += "\n         union all ";
            }
            if ("ALL".equals(v_type) || "SUBJ".equals(v_type)) {
                body_sql1 += "\n         select 'SUBJ' type, null tabseq, a.seq no, a.inuserid userid, a.inuserid cuserid, a.title, ";
/*
                body_sql1 += "\n                CASE WHEN a.cnt = 0 ";
                body_sql1 += "\n                     THEN '1' ";
                body_sql1 += "\n                     WHEN a.cnt > 0 AND (select count (*) ";
                body_sql1 += "\n                                            from tz_qna ";
                body_sql1 += "\n                                           where subj = a.subj ";
                body_sql1 += "\n                                             and year = a.year ";
                body_sql1 += "\n                                             and subjseq = a.subjseq ";
                body_sql1 += "\n                                             and seq = a.seq ";
                body_sql1 += "\n                                             and kind > 0) > 0 ";
                body_sql1 += "\n                     THEN '3' ";
                body_sql1 += "\n                     ELSE '2' ";
                body_sql1 += "\n                END status, ";
*/
                body_sql1 += "\n                a.okyn1 status, ";
                body_sql1 += "\n                a.indate sdate, a.ldate, a.subj, a.year, a.subjseq, '' types,";
                body_sql1 += "\n                0 upfilecnt ";
                body_sql1 += "\n           from tz_qna a, dual "; //dual 지우면 에러남
                body_sql1 += "\n          where a.inuserid = " + StringManager.makeSQL(v_userid);
                body_sql1 += "\n            and a.kind = '0' ";

                if (!v_searchtext.equals("")) { // 검색어가 있으면
                    if (v_select.equals("title")) { // 제목으로 검색할때
                        body_sql1 += "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                    } else if (v_select.equals("content")) { // 제목 + 내용으로 검색할때 // Oracle 9i 용
                        body_sql1 += "\n and ((lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")) OR (a.contents like " + StringManager.makeSQL("%" + v_searchtext + "%") + "))";
                    }
                }
            }
            body_sql1 += "\n         ) ";
            order_sql1 += "\n    order by sdate desc ";

            /*
             * if ("OFF".equals(v_type)) { head_sql1 =
             * " select 'OFF' type, null tabseq, a.no, a.userid, a.cuserid, a.title, "
             * ; head_sql1 +=
             * "\n        a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq "
             * ; body_sql1 = "   from TZ_SANGDAM a "; body_sql1 +=
             * "\n  where a.userid = " + StringManager.makeSQL(v_userid);
             * 
             * if ( !v_searchtext.equals("")) { // 검색어가 있으면 if
             * (v_select.equals("title")) { // 제목으로 검색할때 body_sql1 +=
             * "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%"
             * + v_searchtext + "%")+")"; } else if (v_select.equals("content"))
             * { // 제목 + 내용으로 검색할때 // Oracle 9i 용 body_sql1 +=
             * "\n and ((lower(a.title) like lower ( " +
             * StringManager.makeSQL("%" + v_searchtext +
             * "%")+")) OR (a.ctext like " + StringManager.makeSQL("%" +
             * v_searchtext + "%") + "))"; } }
             * 
             * order_sql1 = "  order by sdate desc "; }
             */

            sql1 = head_sql1 + body_sql1 + order_sql1;
            System.out.println(sql1);
            ls = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
     * 선택된 QnA 과정질문방의 상세내용 select
     */
    public DataBox selectMyQnaCounselQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_categorycd = box.getString("p_categorycd");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();

            sql += " select a.subj, a.year, a.subjseq,  a.lesson,                                         ";
            sql += "        a.seq,  a.kind,  a.title,  a.contents, a.grcode,                              ";
            sql += "        b.scsubjnm,  b.subjseqgr,  a.inuserid,                                        ";
            sql += "        a.categorycd,  a.indate ,  get_name(a.inuserid) name,                     ";
            sql += "        okyn1,   okuserid1,  okyn2,  okuserid2,  okdate1,  okdate2,                   ";
            sql += "        (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,            ";
            sql += "        (select codenm from tz_code                                                   ";
            sql += "          where gubun = '0046' and levels = '1' and code = a.categorycd) categorynm,  ";
            sql += "        c.fileseq,c.realfile, c.savefile, a.cnt                                              ";
            sql += "   from tz_qna a, tz_qnafile c , vz_scsubjseq b                                       ";
            sql += "  where a.subj   = b.subj                                                             ";
            sql += "    and a.year    = b.year                                                            ";
            sql += "    and a.subjseq = b.subjseq                                                         ";
            sql += "    and a.subj     =  c.subj(+)                                                           ";
            sql += "    and a.year     =  c.year(+)                                                           ";
            sql += "    and a.subjseq  =  c.subjseq(+)                                                        ";
            sql += "    and a.lesson   =  c.lesson(+)                                                         ";
            sql += "    and a.seq      =  c.seq(+)                                                            ";
            sql += "    and a.kind     =  c.kind       		(+)                                              ";
            sql += "    and a.kind=0                                                                      ";
            sql += "    and a.seq = " + v_seq + "                                                             ";
            sql += "    and a.subj= '" + v_subj + "'                                                          ";
            sql += "    and a.year = '" + v_year + "'                                                         ";
            sql += "    and a.subjseq = '" + v_subjseq + "'                                                   ";
            if (!v_categorycd.equals("")) {
                sql += "   and categorycd = '" + v_categorycd + "'                                              ";
            }
            //System.out.println(" QnaAdmin sql7 >>> "+sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

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
     * QNA 과정질문방 답변 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectMyQnaCounselQnaListA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_categorycd = box.getString("p_categorycd");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            //------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();

            head_sql = "select  a.subj,a.year,a.subjseq,a.lesson,a.seq,a.kind,a.title, b.scsubjnm,b.subjseqgr,a.inuserid, a.contents, a.categorycd, a.indate ,c.name, ";
            head_sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            head_sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm,  ";
            head_sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm  ";
            body_sql += " from tz_qna a, vz_scsubjseq b, tz_member c   ";
            body_sql += " where a.subj = b.subj and a.inuserid  =  c.userid(+) and a.year=b.year and a.subjseq = b.subjseq and a.kind>0 ";
            body_sql += "       and a.seq = " + v_seq + " and a.subj= '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq = '" + v_subjseq + "'  ";

            if (!v_categorycd.equals("")) {
                body_sql += " and categorycd = '" + v_categorycd + "'";
            }

            order_sql += " order by seq desc ";
            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));
                list.add(dbox);
            }

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

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
     * QNA Upload Filelist
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public ArrayList<DataBox> fileCourseList(String v_subj, String v_year, String v_subjseq, int v_repseq, String v_repkind) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        String sql1 = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();
            sql1 = "select fileseq,realfile, savefile";
            sql1 += " from tz_qnafile                        ";
            sql1 += " where                                  ";
            sql1 += "   subj = '" + v_subj + "'                ";
            sql1 += "   and year = '" + v_year + "'           ";
            sql1 += "   and subjseq  =  '" + v_subjseq + "'    ";
            sql1 += "   and seq = " + v_repseq;
            sql1 += "   and kind = '" + v_repkind + "' ";
            System.out.println(" QnaAdmin sql9 " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
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
        return list;
    }

    /**
     * 선택된 자료실 게시물 상세내용 select
     */
    public DataBox selectMyQnaCounselHomeQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");
        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");
        String s_userid = box.getSession("userid");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            if (!"".equals(vv_type)) {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            sql = "select a.tabseq, a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd,a.grcode, b.fileseq,b.realfile, b.savefile, a.indate ,c.name,d.type, ";
            sql += " okyn1, okuserid1, okyn2, okuserid2, okdate1, okdate2, ";
            sql += " (select grcodenm from tz_grcode where grcode = a.grcode) grcodenm, ";
            sql += " (select codenm from tz_code  where gubun  = '0046' and levels = '1' and code = a.categorycd) categorynm, a.cnt ";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c,tz_bds d ";
            sql += " where    \n";
            sql += " a.tabseq  =  b.tabseq(+)   \n";
            sql += " and a.tabseq  =  d.tabseq(+)   \n";
            sql += " and a.seq  =  b.seq(+)         \n";
            sql += " and a.types  =  b.types(+)     \n";
            sql += " and a.inuserid  =  c.userid(+)   \n";
            sql += " and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types = " + v_types;

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                if ((dbox.getString("d_okyn1").equals("") ? "1" : dbox.getString("d_okyn1")).equals("1")) {

                    if (updateRepStatus(v_tabseq, v_seq, "2", s_userid) > 0) {
                        dbox.put("d_okyn1", "2");
                    }
                }
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }

            sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = " + v_seq + " and types = " + v_types;
            connMgr.executeUpdate(sql);

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);
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
     * QNA 답변 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectMyQnaCounselHomeQnaListA(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql1 = "";
        DataBox dbox = null;

        String v_categorycd = box.getStringDefault("p_categorycd", "00");
        int v_seq = box.getInt("p_seq");
        //int v_tabseq = box.getInt("p_tabseq");
        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            if (!"".equals(vv_type)) {
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            sql += " select a.tabseq, a.seq , a.types, a.title, a.contents, a.indate, a.inuserid, ";
            sql += "        a.upfile, a.isopen, a.luserid, a.ldate, b.name,a.cnt, a.categorycd ";
            sql += "   from TZ_HOMEQNA a, tz_member b ";
            sql += "  where   ";
            sql += "  a.inuserid  =  b.userid(+)";
            sql += "  and   a.tabseq   = " + v_tabseq;
            sql += "  and   a.seq      = " + v_seq;
            sql += "  and   a.types  != '0'";

            if (!v_categorycd.equals("")) {
                sql += " and a.categorycd = '" + v_categorycd + "'";
            }

            sql += " order by seq desc, types asc ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);

                System.out.println("types:::" + dbox.getString("d_types"));
                //여기서 tz_homefile select 후 벡터값으로 지정
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
     * QNA 답변 상태 변경
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateRepStatus(int v_tabseq, int v_seq, String repstatus, String s_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" UPDATE TZ_HOMEQNA                                        \n ");
            sql.append(" SET                                                      \n ");
            sql.append("     OKYN1       = ?                                      \n ");
            sql.append("     , LUSERID   = ?                                      \n ");
            sql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')   \n ");
            sql.append(" WHERE                                                    \n ");
            sql.append("         TABSEQ  = ?                                      \n ");
            sql.append(" AND     SEQ     = ?                                      \n ");
            sql.append(" AND     TYPES   = 0                                         ");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, repstatus);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * QNA Upload Filelist
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public ArrayList<DataBox> fileList(int v_tabseq, int v_seq, String types) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        String sql1 = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            sql1 = "select fileseq,realfile, savefile";
            sql1 += " from tz_homefile ";
            sql1 += " where tabseq = " + v_tabseq + "  and seq = " + v_seq + " and types =  '" + types + "'";
            System.out.println(" QnaAdmin sql8 " + sql1);
            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list.add(dbox);
            }
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
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
        return list;
    }

    /**
     * 선택된 자료실 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_process = box.getString("p_process");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");

        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            int v_tabseq = 0;
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            if (!"".equals(vv_type)) {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_tabseq = ls.getInt(1);
            }
            ls.close();
            ls = null;

            //------------------------------------------------------------------------------------
            sql = "select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ";
            sql += " a.isopen, b.realfile, b.savefile, b.fileseq, d.codenm categorynm , a.okyn1, a.isopen";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c, tz_code d ";

            // 수정일 : 05.11.09 수정자 : 이나연 _(+)  수정
            //              sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
            //              sql += " and a.inuserid = c.userid(+)";
            sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ";
            sql += " and a.inuserid  =  c.userid(+) ";
            sql += " and a.categorycd  =  d.code(+) ";
            sql += " and d.gubun(+)    =  '0088' ";
            sql += " and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types = " + SQLString.Format(v_types);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

                if (realfileVector != null)
                    dbox.put("d_realfile", realfileVector);
                if (savefileVector != null)
                    dbox.put("d_savefile", savefileVector);
                if (fileseqVector != null)
                    dbox.put("d_fileseq", fileseqVector);
            }

            if (v_process.equals("selectView")) {
                sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = " + v_seq + " and types = " + SQLString.Format(v_types);
                connMgr.executeUpdate(sql);
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
     * 선택된 자료실 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public DataBox selectAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer selectSql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            if (!"".equals(vv_type)) {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            selectSql.append("select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ");
            selectSql.append(" a.isopen, b.realfile, b.savefile, b.fileseq, d.codenm categorynm ");
            selectSql.append(" from tz_homeqna a, tz_homefile b, tz_member c, tz_code d ");

            // 수정일 : 05.11.09 수정자 : 이나연 _(+)  수정
            //              sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
            //              sql += " and a.inuserid = c.userid(+)";
            selectSql.append(" where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ");
            selectSql.append(" and a.inuserid  =  c.userid(+) ");
            selectSql.append(" and a.categorycd  =  d.code(+) ");
            selectSql.append(" and d.gubun(+)    =  '0088' ");
            selectSql.append(" and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types = 1 ");

            sql = selectSql.toString();

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

            }
            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

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
     * 선택된 자료실 게시물 상세내용 select BtoC용
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public ArrayList<DataBox> selectHomeAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer selectSql = new StringBuffer();
        DataBox dbox = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        int v_seq = box.getInt("p_seq");

        String vv_type = box.getString("pp_type");
        String v_type = box.getString("p_type");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            if (!"".equals(vv_type)) {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(vv_type);
            } else {
                sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            }
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            selectSql.append("select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ");
            selectSql.append(" a.isopen, b.realfile, b.savefile, b.fileseq, d.codenm categorynm ");
            selectSql.append(" from tz_homeqna a, tz_homefile b, tz_member c, tz_code d ");

            // 수정일 : 05.11.09 수정자 : 이나연 _(+)  수정
            //              sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
            //              sql += " and a.inuserid = c.userid(+)";
            selectSql.append(" where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ");
            selectSql.append(" and a.inuserid  =  c.userid(+) ");
            selectSql.append(" and a.categorycd  =  d.code(+) ");
            selectSql.append(" and d.gubun(+)    =  '0088' ");
            selectSql.append(" and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types != 0 ");

            sql = selectSql.toString();

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
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

}