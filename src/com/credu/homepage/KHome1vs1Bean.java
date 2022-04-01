// **********************************************************
// 1. 제 목: 1:1상담
// 2. 프로그램명: KHome1vs1Bean.java
// 3. 개 요: 1:1상담
// 4. 환 경: JDK 1.4
// 5. 버 젼: 0.1
// 6. 작 성:
// 7. 수 정:
//
// **********************************************************

package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 * 
 */
public class KHome1vs1Bean {
    // private ConfigSet config;
    // private static int row = 10;
    private String v_type = "OO"; // "PQ" // 운영자에게 이므로 type 바꿈 (06.01.11 이나연)

    public KHome1vs1Bean() {
        try {
            // config = new ConfigSet();
            // row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
            // row = 10; // 강제로 지정
            // System.out.println("....... row.....:"+row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 메일전송시 사용하는 사용자정보
     * 
     * @param box receive from the form object and session
     * @return ArrayList 사용자 정보 데이터
     * @throws Exception
     */
    public ArrayList<DataBox> selectSendMailData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        String sql = "";
        DataBox dbox = null;
        String v_parent_userid = box.getString("p_parent_userid");
        try {
            connMgr = new DBConnectionManager();
            sql = " select a.userid         userid  , a.resno          resno     , a.pwd          pwd"
                    + "      , a.name           name    , a.email          email     , a.cono         cono"
                    + "      , a.post1          post1   , a.post2          post2     , a.addr         addr"
                    + "      , a.hometel        hometel , a.handphone      handphone , a.comptel      comptel"
                    + "      , a.tel_line       tel_line, a.comp           comp      , a.indate       indate"
                    + "      , a.lgcnt          lgcnt   , a.lglast         lglast    , a.lgip         lgip"
                    + "      , a.jikup          jikup   , a.jikupnm        jikupnm   , a.jikwi        jikwi"
                    + "      , a.jikwinm        jikwinm , a.office_gbn     office_gbn, a.office_gbnnm office_gbnnm"
                    + "      , a.work_plc       work_plc, a.work_plcnm     work_plcnm, a.deptcod      deptcod"
                    + "      , a.deptnam        deptnam , a.ldate          ldate     , a.lgfirst      lgfirst"
                    + "      , a.ismailing      ismailing,a.addr2          addr2     , b.gubun        gubun" + "   from tz_member a,tz_compclass b"
                    + "  where a.comp = b.comp " + "     and a.userid        = '" + v_parent_userid + "'";
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
     * 사용자 정보 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 사용자 정보 데이터
     * @throws Exception
     */
    public ArrayList<DataBox> selectTz_Member(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        String sql = "";
        DataBox dbox = null;
        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            sql = " select a.userid         userid  , a.resno          resno     , a.pwd          pwd"
                    + "      , a.name           name    , a.email          email     , a.cono         cono"
                    + "      , a.post1          post1   , a.post2          post2     , a.addr         addr"
                    + "      , a.hometel        hometel , a.handphone      handphone , a.comptel      comptel"
                    + "      , a.tel_line       tel_line, a.comp           comp      , a.indate       indate"
                    + "      , a.lgcnt          lgcnt   , a.lglast         lglast    , a.lgip         lgip"
                    + "      , a.jikup          jikup   , a.jikupnm        jikupnm   , a.jikwi        jikwi"
                    + "      , a.jikwinm        jikwinm , a.office_gbn     office_gbn, a.office_gbnnm office_gbnnm"
                    + "      , a.work_plc       work_plc, a.work_plcnm     work_plcnm, a.deptcod      deptcod"
                    + "      , a.deptnam        deptnam , a.ldate          ldate     , a.lgfirst      lgfirst"
                    + "      , a.ismailing      ismailing,a.addr2          addr2     , b.gubun        gubun" + "   from tz_member a,tz_compclass b"
                    + "  where a.comp = b.comp " + "     and a.userid        = '" + s_userid + "'";
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
     * 등록할때(질문)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */

    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "", sql1 = "";
        int isOk1 = 1;
        int v_cnt = 0;
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");
        String v_types = "0";
        String s_userid = "";
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_isopen = "Y";

        // if (s_gadmin.equals("A1")){
        // s_userid = "운영자";
        // }else{
        s_userid = box.getSession("userid");
        // }
        // Vector newFileNames = box.getNewFileNames("p_file");

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- 어떤게시판인지정보를 가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt("tabseq");
            ls.close();

            // ------------------------------------------------------------------------------------
            // ---------------------- 게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(seq), 0) maxseq from TZ_HOMEQNA where tabseq = '" + v_tabseq + "'";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt("maxseq") + 1;
            ls.close();

            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, grcode, cnt, categorycd)                      ";
            sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,'00') ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setString(4, v_title);
            pstmt1.setString(5, v_contents);
            pstmt1.setString(6, s_userid);
            pstmt1.setString(7, v_isopen);
            pstmt1.setString(8, s_userid);
            pstmt1.setString(9, s_grcode);
            pstmt1.setInt(10, v_cnt);

            isOk1 = pstmt1.executeUpdate();

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

}
