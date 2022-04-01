//**********************************************************
//  1. 제      목: 상담 관리
//  2. 프로그램명 : CounselAdminBean.java
//  3. 개      요: 상담 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//**********************************************************

package com.credu.study;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 상담 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class CounselAdminBean {

    private ConfigSet config;
    private int row;

    public static String COUNSEL_KIND = "0047";

    public CounselAdminBean() {
        try {
            config = new ConfigSet();
            //row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
            row = Integer.parseInt(config.getProperty("page.manage.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 상담화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 상담 리스트
     */
    public ArrayList<DataBox> selectListCounsel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        PreparedStatement pstmt = null;
        String v_userid = box.getString("p_userid");
        String v_type = box.getStringDefault("s_type", "ALL");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //sql  = " select a.no, a.userid, a.cuserid, b.name, a.title, a.mcode, c.codenm mcodenm, a.etime,  ";
            //sql += "        a.ftext, a.ctext, a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq, a.gubun ";
            //sql += "   from TZ_SANGDAM a, TZ_MEMBER b, TZ_CODE c         ";
            //sql += "  where a.cuserid = b.userid                         ";
            //sql += "    and a.mcode   = c.code                           ";
            //sql += "    and c.gubun   = " + StringManager.makeSQL(COUNSEL_KIND);
            //sql += "    and a.userid  = " + StringManager.makeSQL(v_userid);
            //if (!v_mcode.equals("ALL")) {
            //    sql += "    and a.mcode   = " + StringManager.makeSQL(v_mcode);
            //}
            //sql += " order by no desc";

            sql = " select type, tabseq, no, userid, cuserid, title, status, sdate, ldate, subj, year, subjseq, types, upfilecnt \n";
            sql += "   from ( \n";
            if ("ALL".equals(v_type)) {
                sql += "         select 'OFF' type, null tabseq, a.no, a.userid, a.cuserid, a.title, \n";
                sql += "                a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq, '' types,\n";
                sql += "                0 upfilecnt \n";
                sql += "           from TZ_SANGDAM a \n";
                sql += "          where a.userid = " + StringManager.makeSQL(v_userid);
            }
            if ("ALL".equals(v_type)) {
                sql += "         union all \n";
            }
            if ("ALL".equals(v_type) || "PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) {
                sql += "         select b.type, a.tabseq, a.seq no, a.inuserid userid, a.inuserid cuserid, a.title, \n";
                sql += "                a.okyn1 status, a.indate sdate, a.ldate, '' subj, SUBSTR(indate,1,4) year, '' subjseq, a.types, \n";
                sql += "                (select count(*) from tz_homefile where tabseq = a.tabseq and seq = a.seq) upfilecnt \n";
                sql += "           from tz_homeqna a, tz_bds b \n";
                sql += "          where a.inuserid = " + StringManager.makeSQL(v_userid);
                sql += "            and a.types    = '0' \n";
                sql += "            and a.tabseq   = b.tabseq \n";
                if (!"ALL".equals(v_type)) {
                    sql += "        and b.type     = " + StringManager.makeSQL(v_type);
                }
                sql += "            and b.type     <> 'KB' \n";
            }
            if ("ALL".equals(v_type)) {
                sql += "         union all \n";
            }
            if ("ALL".equals(v_type) || "SUBJ".equals(v_type)) {
                sql += "         select 'SUBJ' type, null tabseq, a.seq no, a.inuserid userid, a.inuserid cuserid, a.title, \n";
                sql += "                a.okyn1 status, a.indate sdate, a.ldate, a.subj, a.year, a.subjseq, '' types,\n";
                sql += "                0 upfilecnt \n";
                sql += "           from tz_qna a \n";
                sql += "          where a.inuserid = " + StringManager.makeSQL(v_userid);
                sql += "            and a.kind = '0' \n";
            }
            sql += "         ) \n";
            sql += "    order by sdate desc \n";

            if ("OFF".equals(v_type)) {
                sql = " select 'OFF' type, null tabseq, a.no, a.userid, a.cuserid, a.title, \n";
                sql += "        a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq \n";
                sql += "   from TZ_SANGDAM a \n";
                sql += "  where a.userid = " + StringManager.makeSQL(v_userid);
                sql += "  order by sdate desc \n";
            }

            //System.out.println("selectListCounsel" + sql);
            //ls = connMgr.executeQuery(sql);

            //while (ls.next()) {
            //    dbox = ls.getDataBox();

            //    list.add(dbox);
            //}

            pstmt = connMgr.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ls = new ListSet(pstmt);

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount(); //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * 상담화면 리스트 - 과정별
     * 
     * @param box receive from the form object and session
     * @return ArrayList 상담 리스트
     */
    public ArrayList<DataBox> selectListCounselSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        String v_userid = box.getString("p_userid");
        String v_mcode = box.getStringDefault("s_mcode", "ALL");
        String v_subjyearseq = box.getString("p_subj");
        StringTokenizer st = null;

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        if (v_subjyearseq.indexOf("/") > 0) {
            st = new StringTokenizer(v_subjyearseq, "/");
            if (st.hasMoreElements()) {
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();
            }
        }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select a.no, a.userid, a.cuserid, b.name, a.title, a.mcode, c.codenm mcodenm, a.etime,  ";
            sql += "        a.ftext, a.ctext, a.status, a.sdate, a.ldate, a.subj, a.year, a.subjseq, a.gubun ";
            sql += "   from TZ_SANGDAM a, TZ_MEMBER b, TZ_CODE c         ";
            sql += "  where a.cuserid = b.userid                         ";
            sql += "    and a.mcode   = c.code                           ";
            sql += "    and c.gubun   = " + StringManager.makeSQL(COUNSEL_KIND);
            sql += "    and a.userid  = " + StringManager.makeSQL(v_userid);
            sql += "    and a.subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and a.year    = " + StringManager.makeSQL(v_year);
            sql += "    and a.subjseq = " + StringManager.makeSQL(v_subjseq);
            if (!v_mcode.equals("ALL")) {
                sql += "    and a.mcode   = " + StringManager.makeSQL(v_mcode);
            }
            sql += " order by no desc";

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
     * 상담화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return DataBox 조회한 상세정보
     */
    public DataBox selectViewCounsel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        int v_no = box.getInt("p_no");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT NO, USERID, CUSERID, TITLE, FTEXT, CTEXT, SUBJ, STATUS, LDATE, \n";
            sql += "        ETIME, MCODE, SDATE, SUBJ, YEAR, SUBJSEQ, GUBUN \n";
            sql += "        ,(SELECT DISTINCT NAME FROM TZ_MEMBER WHERE USERID = SD.CUSERID) CNAME \n";
            sql += "   FROM TZ_SANGDAM SD \n";
            sql += "  WHERE USERID  = " + StringManager.makeSQL(v_userid);
            sql += "    AND NO      = " + v_no;

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
     * 상담 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertCounsel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;

        int v_no = 0;
        String v_userid = box.getString("p_userid");
        String v_title = box.getString("p_title");
        String v_ftext = box.getString("p_ftext");
        String v_ctext = box.getString("p_ctext");
        String v_status = box.getString("p_status");
        String v_mcode = box.getString("p_mcode");
        String v_etime = box.getString("p_etime");
        String v_sdate = box.getString("p_sdate");
        String v_gubun = box.getString("p_gubun");
        String s_userid = box.getSession("userid");
        String v_subjyearseq = box.getString("p_subj");
        StringTokenizer st = null;

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        if (v_subjyearseq.indexOf("/") > 0) {
            st = new StringTokenizer(v_subjyearseq, "/");
            if (st.hasMoreElements()) {
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();
            }
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select max(no) from TZ_SANGDAM ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_no = ls.getInt(1) + 1;
            } else {
                v_no = 1;
            }

            sql1 = "insert into TZ_SANGDAM(no, userid, cuserid, title, ftext, ctext, status, mcode, etime, sdate, ldate ,subj, year, subjseq, gubun)  ";
            sql1 += "            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?)  ";

            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(1, v_no);
            pstmt.setString(2, v_userid);
            pstmt.setString(3, s_userid);
            pstmt.setString(4, v_title);
            //           pstmt.setCharacterStream(5,  new StringReader(v_ftext), v_ftext.length());
            pstmt.setString(5, v_ftext);
            pstmt.setString(6, v_ctext);
            pstmt.setString(7, v_status);
            pstmt.setString(8, v_mcode);
            pstmt.setString(9, v_etime);
            pstmt.setString(10, v_sdate);
            pstmt.setString(11, v_subj);
            pstmt.setString(12, v_year);
            pstmt.setString(13, v_subjseq);
            pstmt.setString(14, v_gubun);

            isOk = pstmt.executeUpdate();
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
     * 상담 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateCounsel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_no = box.getInt("p_no");
        String v_userid = box.getString("p_userid");
        String v_title = box.getString("p_title");
        String v_ftext = box.getString("p_ftext");
        String v_ctext = box.getString("p_ctext");
        String v_status = box.getString("p_status");
        String v_mcode = box.getString("p_mcode");
        String v_etime = box.getString("p_etime");
        String v_sdate = box.getString("p_sdate");
        String v_gubun = box.getString("p_gubun");
        String s_userid = box.getSession("userid");
        String v_subjyearseq = box.getString("p_subj");
        StringTokenizer st = null;

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        if (v_subjyearseq.indexOf("/") > 0) {
            st = new StringTokenizer(v_subjyearseq, "/");
            if (st.hasMoreElements()) {
                v_subj = st.nextToken();
                v_year = st.nextToken();
                v_subjseq = st.nextToken();
            }
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_SANGDAM set cuserid = ? , title = ?, ftext = ? , ctext = ? , status = ? , mcode = ?, etime = ?, sdate=?,  ";
            sql += "                       ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'), subj = ?, year = ?, subjseq = ?, gubun = ?   ";
            sql += "  where userid = ? and no = ?                                                   ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, s_userid);
            pstmt.setString(2, v_title);
            //            pstmt.setCharacterStream(3,  new StringReader(v_ftext), v_ftext.length());
            pstmt.setString(3, v_ftext);
            pstmt.setString(4, v_ctext);
            pstmt.setString(5, v_status);
            pstmt.setString(6, v_mcode);
            pstmt.setString(7, v_etime);
            pstmt.setString(8, v_sdate);
            pstmt.setString(9, v_subj);
            pstmt.setString(10, v_year);
            pstmt.setString(11, v_subjseq);
            pstmt.setString(12, v_gubun);
            pstmt.setString(13, v_userid);
            pstmt.setInt(14, v_no);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 상담 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteCounsel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_no = box.getInt("p_no");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_SANGDAM            ";
            sql += "  where userid = ? and no = ?      ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_userid);
            pstmt.setInt(2, v_no);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 과정 리스트
     * 
     * @param p_userid 사용자아이디
     * @param name select box name
     * @param selected select box 선택값
     * @return result select box text
     */
    public static String getSubjSelect(String p_userid, String name, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        String code = "";
        String value = "";

        result = "  <SELECT name=" + name + " > \n";
        result += " <option value=''>과정을 선택하세요</option> \n";
        try {
            connMgr = new DBConnectionManager();

            sql = " select a.subj || '/' || a.year || '/' || a.subjseq subj, b.subjnm from tz_student a, tz_subj b ";
            sql += " where a.subj = b.subj ";
            sql += "   and a.userid = " + StringManager.makeSQL(p_userid);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                code = ls.getString("subj");
                value = ls.getString("subjnm");

                result += " <option value=" + code;
                if (selected.equals(code)) {
                    result += " selected ";
                }

                result += ">" + value + "</option> \n";
            }
            result += " <option value=0000/0000/0000";
            if (selected.equals("0000/0000/0000")) {
                result += " selected ";
            }
            result += ">========= 기타 =========</option> \n";
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
}
