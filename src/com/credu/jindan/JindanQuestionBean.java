//**********************************************************
//1. 제      목: 진단테스
//2. 프로그램명: JindanQuestionBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//**********************************************************

package com.credu.jindan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.SelectionUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JindanQuestionBean {

    private ConfigSet config;
    private int row;

    public JindanQuestionBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 평가 문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String ss_subjcourse = box.getString("s_subjcourse");
        String v_action = box.getStringDefault("p_action", "change");

        int v_pageno = box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                //문제리스트 가져오기
                list = getQuestionList(connMgr, ss_subjcourse, v_pageno, v_pagesize);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 평가 문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> getQuestionList(DBConnectionManager connMgr, String p_subj, int v_pageno, int v_pagesize) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        try {
            head_sql = "select a.subj,   a.jindannum, a.jindantype, a.jindantext,    a.exptext, \n";
            head_sql += "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio,  \n";
            head_sql += " 	  a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, \n";
            head_sql += "       b.codenm    jindantypenm,  c.codenm    levelsnm  \n";
            body_sql += "  from tz_jindan   a,  \n";
            body_sql += "       tz_code   b,  \n";
            body_sql += "       tz_code   c   \n";
            body_sql += "   where a.jindantype = b.code  \n";
            body_sql += "   and a.levels   = c.code  \n";
            body_sql += "   and a.subj     = " + SQLString.Format(p_subj);
            body_sql += "   and b.gubun    = " + SQLString.Format(JindanBean.JINDAN_TYPE);
            body_sql += "   and c.gubun    = " + SQLString.Format(JindanBean.JINDAN_LEVEL);
            order_sql += " order by a.jindannum  \n";

            sql = head_sql + body_sql + group_sql + order_sql;

            //System.out.println(" JindanQuestionBean  진단테스트 문제리스트 getQuestionList:"+sql);
            ls = connMgr.executeQuery(sql);

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
        }
        return list;
    }

    /**
     * 문제 내용 보기
     * 
     * @param box receive from the form object and session
     * @return QuestionExampleData 평가문제
     */
    public ArrayList<DataBox> selectExampleData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String v_subj = box.getString("p_subj");
        // String v_grcode = box.getString("p_grcode");
        int v_jindannum = box.getInt("p_jindannum");
        String v_action = box.getStringDefault("p_action", "change");

        try {
            if (v_action.equals("go") && v_jindannum > 0) {
                connMgr = new DBConnectionManager();

                //진단문제 가져오기
                list = getExampleData(connMgr, v_subj, v_jindannum);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 문제 내용 보기
     * 
     * @param box receive from the form object and session
     * @return QuestionExampleData 평가문제
     */
    public ArrayList<DataBox> getExampleData(DBConnectionManager connMgr, String p_subj, int p_examnum) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.jindannum,  a.jindantype, ";
            sql += "       a.jindantext,      a.exptext,   a.levels,  a.selcount,  a.saveimage,   a.saveaudio, ";
            sql += "       a.savemovie,  a.saveflash, a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql += "       b.selnum,   b.seltext,  b.isanswer,  ";
            sql += "       c.codenm    jindantypenm, ";
            sql += "       d.codenm    levelsnm    ";
            sql += "  from tz_jindan      a, ";
            sql += "       tz_jindansel   b, ";
            sql += "       tz_code      c, ";
            sql += "       tz_code      d  ";
            sql += " where a.subj      =  b.subj(+) ";
            sql += "   and a.jindannum   =  b.jindannum(+) ";
            sql += "   and a.jindantype  = c.code ";
            sql += "   and a.levels    = d.code ";
            sql += "   and a.subj      = " + SQLString.Format(p_subj);
            sql += "   and a.jindannum   = " + SQLString.Format(p_examnum);
            sql += "   and c.gubun     = " + SQLString.Format(JindanBean.JINDAN_TYPE);
            sql += "   and d.gubun     = " + SQLString.Format(JindanBean.JINDAN_LEVEL);
            sql += " order by a.jindannum, b.selnum ";

            ls = connMgr.executeQuery(sql);
            //System.out.println("문제내용 sql:"+sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
        }
        return list;
    }

    /**
     * 풀등록 에서 평가문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> selectQuestionPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String v_subj = box.getString("p_subj");
        String v_action = box.getStringDefault("p_action", "change");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getQuestionPool(connMgr, v_subj);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
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
     * 풀등록 에서 평가문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> getQuestionPool(DBConnectionManager connMgr, String p_subj) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,   a.jindannum,  a.jindantype, ";
            sql += "       a.jindantext,    a.exptext,     ";
            sql += "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
            sql += "       b.codenm    jindantypenm, ";
            sql += "       c.codenm    levelsnm,    ";
            sql += "       d.subjnm    subjnm    ";
            sql += "  from tz_jin   a, ";
            sql += "       tz_code   b, ";
            sql += "       tz_code   c,  ";
            sql += "       tz_subj   d  ";
            sql += "   where a.jindantype = b.code ";
            sql += "   and a.levels   = c.code ";
            sql += "   and a.subj   = d.subj ";
            sql += "   and a.subj    ! = " + SQLString.Format(p_subj);
            sql += "   and b.gubun    = " + SQLString.Format(JindanBean.JINDAN_TYPE);
            sql += "   and c.gubun    = " + SQLString.Format(JindanBean.JINDAN_LEVEL);
            sql += " order by a.subj, a.jindannum ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
        }
        return list;
    }

    /**
     * 평가 문제 를 찾기위한 Pool
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectQuestionPoolList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";

        try {

            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");

            String v_action = box.getString("p_action");
            String v_subj = box.getString("p_subj");

            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql = "select a.subj,   a.examnum,  a.examtype, ";
                sql += "       a.examtext,    a.exptext,     ";
                sql += "       a.levels,   a.selcount,  a.saveimage,   a.saveaudio, a.savemovie, a.saveflash,  a.realimage,   a.realaudio, a.realmovie, a.realflash, ";
                sql += "       b.codenm    examtypenm, ";
                sql += "       c.codenm    levelsnm,    ";
                sql += "       d.subjnm    subjnm    ";
                sql += "  from tz_jindan   a, ";
                sql += "       tz_code   b, ";
                sql += "       tz_code   c,  ";
                sql += "       tz_subj   d  ";
                sql += "   where a.examtype = b.code ";
                sql += "   and a.levels   = c.code ";
                sql += "   and a.subj   = d.subj ";
                sql += "   and a.subj     != " + SQLString.Format(v_subj);
                sql += "   and b.gubun    = " + SQLString.Format(JindanBean.JINDAN_TYPE);
                sql += "   and c.gubun    = " + SQLString.Format(JindanBean.JINDAN_LEVEL);

                if (ss_searchtype.equals("1")) { // 과정명
                    sql += "  and d.subjnm like " + SQLString.Format("%" + ss_searchtext + "%");
                } else if (ss_searchtype.equals("2")) { // 문제
                    sql += "  and a.examtext like " + SQLString.Format("%" + ss_searchtext + "%");
                } else if (ss_searchtype.equals("3")) { // 난이도
                    sql += "  and c.codenm like " + SQLString.Format("%" + ss_searchtext + "%");
                } else if (ss_searchtype.equals("4")) { // 문제분류
                    sql += "  and b.codenm like " + SQLString.Format("%" + ss_searchtext + "%");
                }

                sql += " order by a.subj, a.examnum ";

                ls = connMgr.executeQuery(sql);//System.out.println(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();

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
     * 문제 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_luserid = box.getSession("userid");
        String v_subj = box.getString("p_subj");
        // String v_grcode = box.getString("p_grcode");
        int v_jindannum = 0;
        String v_jindantype = box.getString("p_jindantype");
        String v_jindantext = box.getString("p_jindantext");
        String v_exptext = box.getString("p_exptext");
        String v_levels = box.getString("p_levels");

        int v_selnum = 0;
        // Vector v_seltexts = box.getVector("p_seltext");
        String v_seltext = "";
        // Vector v_isanswers = box.getVector("p_isanswer");
        String v_isanswer = "";

        // int v_checked_selnum = 0;

        int v_selcount = box.getInt("p_selcount1");
        String v_saveimage = box.getNewFileName("p_img1");
        String v_realimage = box.getRealFileName("p_img1");
        String v_saveaudio = box.getNewFileName("p_audio1");
        String v_realaudio = box.getRealFileName("p_audio1");
        String v_savemovie = box.getNewFileName("p_movie1");
        String v_realmovie = box.getRealFileName("p_movie1");
        String v_saveflash = box.getNewFileName("p_flash1");
        String v_realflash = box.getRealFileName("p_flash1");

        try {
            // 입력할문제번호 가져오기
            v_jindannum = getExamnumSeq(v_subj);

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //문제입력
            isOk = inserttz_jindan(connMgr, v_subj, v_jindannum, v_jindantype, v_jindantext, v_exptext, v_levels, v_selcount, v_saveimage, v_saveaudio, v_savemovie, v_saveflash, v_realimage, v_realaudio, v_realmovie, v_realflash, v_luserid);

            //보기입력
            sql = "insert into TZ_JINDANSEL(subj, jindannum, selnum, seltext, isanswer, luserid, ldate) ";
            sql += " values (?, ?, 	?, 	?, 	?, 	?, 	?)";
            //System.out.println("보기입력:"+sql);

            pstmt = connMgr.prepareStatement(sql);

            for (int i = 1; i <= 10; i++) {
                v_seltext = box.getString("p_seltext" + String.valueOf(i));
                if (!v_seltext.trim().equals("")) {
                    v_selnum++;
                    v_isanswer = box.getString("p_isanswer" + String.valueOf(i));
                    isOk = inserttz_jindansel(pstmt, v_subj, v_jindannum, v_selnum, v_seltext, v_isanswer, v_luserid);
                }
            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
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
     * 진단문제등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int inserttz_jindan(DBConnectionManager connMgr, String p_subj, int p_jindannum, String p_jindantype, String p_jindantext, String p_exptext, String p_levels, int p_selcount, String p_saveimage, String p_saveaudio, String p_savemovie,
            String p_saveflash, String p_realimage, String p_realaudio, String p_realmovie, String p_realflash, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert tz_jindan table
            sql = " insert into TZ_JINDAN \n";
            sql += " (subj,        jindannum,     jindantype,  jindantext,        exptext,  \n";
            sql += "  levels,       selcount,  saveimage,     saveaudio,     savemovie,   saveflash,   \n";
            sql += "  realimage,     realaudio,     realmovie,   realflash,  \n";
            sql += "  luserid,      ldate   )  \n";
            sql += " values  \n";
            sql += " (?,         ?,          ?, 	?,         ?,  \n";
            sql += "  ?,         ?,          ?,          ?,            ?,              ?,   \n";
            sql += "  ?,             ?,            ?,              ?, ";
            sql += "  ?,         ?  )  \n";

            //System.out.println("진단문제등록:"+sql);
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_subj);
            pstmt.setInt(2, p_jindannum);
            pstmt.setString(3, p_jindantype);
            pstmt.setString(4, p_jindantext);
            pstmt.setString(5, p_exptext);
            pstmt.setString(6, p_levels);
            pstmt.setInt(7, p_selcount);
            pstmt.setString(8, p_saveimage);
            pstmt.setString(9, p_saveaudio);
            pstmt.setString(10, p_savemovie);
            pstmt.setString(11, p_saveflash);
            pstmt.setString(12, p_realimage);
            pstmt.setString(13, p_realaudio);
            pstmt.setString(14, p_realmovie);
            pstmt.setString(15, p_realflash);
            pstmt.setString(16, p_luserid);
            pstmt.setString(17, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 문제 보기 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int inserttz_jindansel(PreparedStatement pstmt, String p_subj, int p_examnum, int p_selnum, String p_seltext, String p_isanswer, String p_luserid) throws Exception {
        int isOk = 0;

        try {
            pstmt.setString(1, p_subj);
            pstmt.setInt(2, p_examnum);
            pstmt.setInt(3, p_selnum);
            pstmt.setString(4, p_seltext);
            pstmt.setString(5, p_isanswer);
            pstmt.setString(6, p_luserid);
            pstmt.setString(7, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }

    /**
     * 일련번호 구하기
     * 
     * @param p_subj
     * @return int
     */
    public int getExamnumSeq(String p_subj) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "jindannum");
        maxdata.put("seqtable", "tz_jindan");
        maxdata.put("paramcnt", "1");
        maxdata.put("param0", "subj");
        maxdata.put("subj", SQLString.Format(p_subj));

        return SelectionUtil.getSeq(maxdata);
    }

    /**
     * 문제 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        ResultSet rs = null;

        String v_subj = box.getString("p_subj");
        // String v_grcode = box.getString("p_grcode");
        int v_jindannum = box.getInt("p_jindannum");
        String v_jindantype = box.getString("p_jindantype");
        String v_jindantext = box.getString("p_jindantext");
        String v_exptext = box.getString("p_exptext");
        String v_levels = box.getString("p_levels");

        int v_selnum = 0;
        // Vector v_seltexts = box.getVector("p_seltext");
        String v_seltext = "";
        // Vector v_isanswers = box.getVector("p_isanswer");
        String v_isanswer = "";

        // int v_checked_selnum = 0;

        String v_luserid = box.getSession("userid");

        int v_selcount = box.getInt("p_selcount1");

        String v_saveimage = box.getNewFileName("p_img1");
        String v_realimage = box.getRealFileName("p_img1");
        String v_saveaudio = box.getNewFileName("p_audio1");
        String v_realaudio = box.getRealFileName("p_audio1");
        String v_savemovie = box.getNewFileName("p_movie1");
        String v_realmovie = box.getRealFileName("p_movie1");
        String v_saveflash = box.getNewFileName("p_flash1");
        String v_realflash = box.getRealFileName("p_flash1");

        String v_imgfile = box.getString("p_img2");
        String v_audiofile = box.getString("p_audio2");
        String v_moviefile = box.getString("p_movie2");
        String v_flashfile = box.getString("p_flash2");
        String v_realimgfile = box.getString("p_img3");
        String v_realaudiofile = box.getString("p_audio3");
        String v_realmoviefile = box.getString("p_movie3");
        String v_realflashfile = box.getString("p_flash3");
        int sulcnt = 10; // 2005.8.20 by 정은년 

        if (v_saveimage.length() == 0) {
            v_saveimage = v_imgfile;
        }
        if (v_saveaudio.length() == 0) {
            v_saveaudio = v_audiofile;
        }
        if (v_savemovie.length() == 0) {
            v_savemovie = v_moviefile;
        }
        if (v_saveflash.length() == 0) {
            v_saveflash = v_flashfile;
        }
        if (v_realimage.length() == 0) {
            v_realimage = v_realimgfile;
        }
        if (v_realaudio.length() == 0) {
            v_realaudio = v_realaudiofile;
        }
        if (v_realmovie.length() == 0) {
            v_realmovie = v_realmoviefile;
        }
        if (v_realflash.length() == 0) {
            v_realflash = v_realflashfile;
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 수정여부 체크
            sql = " select count(*) cnt from tz_jindanresult where subj='" + v_subj + "' and ','||jindan||',' like '%," + v_jindannum + ",%' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0)
                    isOk = -2;
            }

            if (isOk == 0) {
                isOk = updatetz_jindan(connMgr, v_subj, v_jindannum, v_jindantype, v_jindantext, v_exptext, v_levels, v_selcount, v_saveimage, v_saveaudio, v_savemovie, v_saveflash, v_realimage, v_realaudio, v_realmovie, v_realflash, v_luserid);

                isOk = deletetz_jindansel(connMgr, v_subj, v_jindannum);

                sql = "insert into TZ_JINDANSEL(subj, jindannum, selnum, seltext, isanswer, luserid, ldate) ";
                sql += " values (?, ?, ?, ?, ?, ?, ?)";
                pstmt = connMgr.prepareStatement(sql);

                if (v_jindantype.equals("3"))
                    sulcnt = 2; // OX식 

                for (int i = 1; i <= sulcnt; i++) {
                    v_seltext = box.getString("p_seltext" + String.valueOf(i));
                    if (!v_seltext.trim().equals("")) {
                        v_selnum++;
                        v_isanswer = box.getString("p_isanswer" + String.valueOf(i));
                        isOk = inserttz_jindansel(pstmt, v_subj, v_jindannum, v_selnum, v_seltext, v_isanswer, v_luserid);

                    }
                }

            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
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
            if (rs != null) {
                try {
                    rs.close();
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
     * 문제 삭제
     * 
     * @param box
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int deleteQuestion(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_subj = box.getString("p_subj");
        int v_examnum = box.getInt("p_jindannum");
        String v_duserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk = deletetz_jindan(connMgr, v_subj, v_examnum, v_duserid);
            if (isOk > 0) {
                isOk = deletetz_jindansel(connMgr, v_subj, v_examnum);
            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk > 0) {
                box.put("p_sulnum", String.valueOf("0"));
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
     * 문제 수정
     * 
     * @param box
     * @return int
     */
    public int updatetz_jindan(DBConnectionManager connMgr, String p_subj, int p_jindannum, String p_jindantype, String p_jindantext, String p_exptext, String p_levels, int p_selcount, String p_saveimage, String p_saveaudio, String p_savemovie,
            String p_saveflash, String p_realimage, String p_realaudio, String p_realmovie, String p_realflash, String p_luserid) throws Exception {

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update tz_jindan table

            sql = " update TZ_JINDAN ";
            sql += " set       jindantype = ?, ";
            sql += "        jindantext     = ?, ";
            sql += "        exptext  = ?, ";
            sql += "        levels   = ?, ";
            sql += "        selcount   = ?, ";
            sql += "        saveimage   = ?, ";
            sql += "        saveaudio = ?, ";
            sql += "        savemovie = ?, ";
            sql += "        saveflash      = ?, ";
            sql += "        realimage   = ?, ";
            sql += "        realaudio = ?, ";
            sql += "        realmovie  = ?, ";
            sql += "        realflash  = ?, ";
            sql += "        luserid  = ?, ";
            sql += "        ldate    = ?  ";
            sql += "  where subj     = ?  ";
            sql += "    and jindannum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_jindantype);
            pstmt.setString(2, p_jindantext);
            pstmt.setString(3, p_exptext);
            pstmt.setString(4, p_levels);
            pstmt.setInt(5, p_selcount);
            pstmt.setString(6, p_saveimage);
            pstmt.setString(7, p_saveaudio);
            pstmt.setString(8, p_savemovie);
            pstmt.setString(9, p_saveflash);
            pstmt.setString(10, p_realimage);
            pstmt.setString(11, p_realaudio);
            pstmt.setString(12, p_realmovie);
            pstmt.setString(13, p_realflash);
            pstmt.setString(14, p_luserid);
            pstmt.setString(15, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(16, p_subj);
            pstmt.setInt(17, p_jindannum);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 문제 삭제
     * 
     * @param box
     * @return int
     */
    public int deletetz_jindan(DBConnectionManager connMgr, String p_subj, int p_examnum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        ResultSet rs = null;

        try {

            sql = " select count(*) cnt from tz_jinresult where subj='" + p_subj + "' and ','||jin||',' like '%," + p_examnum + ",%' ";
            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0)
                    isOk = -2;
            }
            rs.close();

            if (isOk == 0) {

                sql = " delete from TZ_JIN ";
                sql += "  where subj     = ?  ";
                sql += "    and jindannum  = ?  ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, p_subj);
                pstmt.setInt(2, p_examnum);

                isOk = pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 문제 보기 삭제
     * 
     * @param box
     * @return int
     */
    public int deletetz_jindansel(DBConnectionManager connMgr, String p_subj, int p_examnum) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        // int isOk = 0;

        try {
            //delete tz_jindanSEL table
            sql = " delete from TZ_JINDANSEL ";
            sql += "  where subj     = ?  ";
            sql += "    and jindannum   = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setInt(2, p_examnum);

            pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return 1;
    }

    /**
     * 과정 컨텐츠타입 구하기
     * 
     * @param box receive from the form object and session
     * @return string
     */
    public String getContentType(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        String p_subj = box.getString("p_subj");
        String result = "";
        try {
            connMgr = new DBConnectionManager();
            sql = " select contenttype from tz_subj where subj=" + SQLString.Format(p_subj);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                result = dbox.getString("d_contenttype");
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
        return result;
    }
}
