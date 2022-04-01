//**********************************************************
//1. 제      목: 포럼 공지사항
//2. 프로그램명: OpenForumNoticeBean.java
//3. 개      요: 포럼 공지사항
//4. 환      경: JDK 1.0
//5. 버      젼: 0.1
//6. 작      성: Administrator 2005.12.18
//7. 수      정:
//
//**********************************************************

package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OpenForumNoticeBean {
    private ConfigSet config;
    private static int row = 7;
    // private static String v_type = "PQ";
    private static final String FILE_TYPE = "p_file"; //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 1; //    페이지에 세팅된 파일첨부 갯수

    public void HomeNoticeBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //이 모듈의 페이지당 row 수를 셋팅한다
            row = 10; //강제로 지정
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 자료실 테이블번호
     * 
     * @param box receive from the form object and session
     * @return int 자료실 테이블번호
     * @throws Exception
     */
    public int selectTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_type = box.getStringDefault("p_type", "FD");
        String v_grcode = box.getStringDefault("p_grcode", "0000000");
        // String v_comp = box.getStringDefault("p_comp", "0000000000");
        String v_subj = box.getStringDefault("p_subj", "0000000000");
        String v_year = box.getStringDefault("p_year", "0000");
        String v_subjseq = box.getStringDefault("p_subjseq", "0000");

        try {
            connMgr = new DBConnectionManager();

            sql = " select tabseq from TZ_BDS      ";
            sql += "  where type    = " + StringManager.makeSQL(v_type);
            sql += "    and grcode  = " + StringManager.makeSQL(v_grcode);
            sql += "    and subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("tabseq");
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
        return result;
    }

    /**
     * 포럼 공지사항 메인사용 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectDirectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        // String group_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        int v_tabseq = box.getInt("p_tabseq");
        // String v_login = "";
        // String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_process = box.getStringDefault("p_process", "mainList");

        // if (box.getSession("userid").equals("")) {
        //    v_login = "N";
        // } else {
        //    v_login = "Y";
        // }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (!v_process.equals("main")) {
                head_sql = "Select  \n";
            } else {
                head_sql = "select * from ( select rownum rnum,  \n";
            }

            head_sql += "    seq,         \n";
            head_sql += "    addate,      \n";
            head_sql += "    adtitle,     \n";
            head_sql += "    adname,      \n";
            head_sql += "    cnt,         \n";
            head_sql += "    luserid,     \n";
            head_sql += "    ldate,       \n";
            head_sql += "    isall,       \n";
            head_sql += "    useyn,       \n";
            head_sql += "    loginyn,     \n";
            head_sql += "    gubun,       \n";
            head_sql += "    uselist,      \n";
            head_sql += "    aduserid,     \n";
            head_sql += "    filecnt       \n";
            body_sql += " from             \n";
            body_sql += " (select          \n";
            //body_sql += "    rownum,        \n";
            body_sql += "    x.seq,         \n";
            body_sql += "    x.addate,      \n";
            body_sql += "    x.adtitle,     \n";
            body_sql += "    x.adname,      \n";
            body_sql += "    x.cnt,         \n";
            body_sql += "    x.luserid,     \n";
            body_sql += "    x.ldate,       \n";
            body_sql += "    x.isall,       \n";
            body_sql += "    x.useyn,       \n";
            body_sql += "    x.loginyn,     \n";
            body_sql += "    x.gubun,       \n";
            body_sql += "    x.uselist,     \n";
            body_sql += "    x.tabseq,      \n";
            body_sql += "    x.adcontent,   \n";
            body_sql += "    x.aduserid,    \n";
            body_sql += "    x.grcodecd,    \n";
            body_sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
            body_sql += "  from       \n";
            body_sql += "    TZ_NOTICE x ) a";
            body_sql += "  where ";
            body_sql += "      tabseq = " + v_tabseq;

            if (!v_searchtext.equals("")) {
                v_pageno = 1;
                if (v_search.equals("adtitle")) {
                    body_sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adcontents")) {
                    body_sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adname")) {
                    body_sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            order_sql += "    order by seq desc) where rnum < 5";

            sql = head_sql + body_sql + order_sql;
            System.out.println("회원포럼 sql : " + sql);
            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;

            row = 7;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
            ls.setPageSize(row); //	 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //	   현재페이지번호를	세팅한다.
            int total_page_count = ls.getTotalPage(); //	 전체 페이지 수를 반환한다

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
     * 포럼 공지사항 등록
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 리스트
     * @throws Exception
     */
    public int insertNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
        String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_adtitle = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_loginyn = box.getString("p_login");
        String v_useyn = box.getString("p_use");
        String v_upfile = box.getNewFileName("p_file1");
        String v_realfile = box.getRealFileName("p_file1");
        String v_isall = box.getString("p_isAllvalue");
        // String v_grcodecd = box.getString("p_grocdecd");

        String s_userid = box.getSession("userid");
        String s_luserid = box.getSession("userid");
        String s_name = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select max(seq) from TZ_NOTICE  where tabseq = '" + v_tabseq + "' ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql1 = " insert into TZ_NOTICE(               			   \n";
            sql1 += " tabseq, seq, aduserid, addate, adtitle, adname,     \n"; // 6
            sql1 += " adcontent, cnt, luserid, ldate,      			   \n"; // 4
            sql1 += " loginyn, useyn, upfile, 			   			   \n"; // 3
            sql1 += " realfile, isall, gubun							)  \n"; // 2
            sql1 += " values (											";
            sql1 += " ?,            ?,            ?,            to_char(sysdate, 'YYYYMMDDHH24MISS'), "; // 4
            //		sql1 += " ?, 		    ?,            empty_clob(),			?,											";	// 4
            sql1 += " ?, 		    ?,            ?,			?,											"; // 4
            sql1 += " ?,			to_char(sysdate, 'YYYYMMDDHH24MISS'), 							"; // 2
            sql1 += " ?,            ?,            ?, 		    ?,											"; // 4
            sql1 += " ?           ,?                       		) 		"; // 1

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_adtitle);
            pstmt.setString(index++, s_name);
            pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //		pstmt.setString(index++,  v_content);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, s_luserid);
            pstmt.setString(index++, v_loginyn);
            pstmt.setString(index++, v_useyn);
            pstmt.setString(index++, v_upfile); //파일
            pstmt.setString(index++, v_realfile); //파일
            pstmt.setString(index++, v_isall); //전체공지여부
            pstmt.setString(index++, v_gubun);

            isOk = pstmt.executeUpdate();

            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);
            //		sql2 = "select adcontent from tz_notice where seq = " + v_seq + "and tabseq = " + v_tabseq;
            //		connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)

            if (isOk > 0 && isOk2 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
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
     * 포럼 공지사항 상세내용 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 조회
     * @throws Exception
     */
    public DataBox selectViewNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        int v_tabseq = box.getInt("p_tabseq");
        String v_seq = box.getString("p_seq");
        System.out.println("v_seq " + v_seq);
        String v_process = box.getString("p_process");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();

            sql += " select  \n";
            sql += "   a.seq,      \n";
            sql += "   a.gubun,    \n";
            sql += "   a.aduserid,  \n";
            sql += "   a.addate,                     \n";
            sql += "   a.adtitle,                    \n";
            sql += "   a.adname,                     \n";
            sql += "   a.adcontent,                  \n";
            sql += "   a.cnt,                        \n";
            sql += "   a.luserid,                    \n";
            sql += "   a.ldate,                      \n";
            sql += "   a.loginyn,                    \n";
            sql += "   a.useyn,                      \n";
            sql += "   a.uselist,                    \n";
            sql += "   a.isall,                      \n";
            sql += "   b.realfile,                   \n";
            sql += "   b.savefile,                   \n";
            sql += "   b.fileseq                     \n";
            sql += " from TZ_NOTICE a , TZ_BOARDFILE B   \n";
            sql += "  where a.seq    = " + StringManager.makeSQL(v_seq);
            sql += "    and a.tabseq = " + v_tabseq;
            sql += "    and a.tabseq  =  b.tabseq(+) ";
            sql += "    and a.seq  =  b.seq(+) ";

            ls = connMgr.executeQuery(sql);
            System.out.println("공지 상세 sql" + sql);
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

            // 조회수 증가
            if (!v_process.equals("popupview")) {
                connMgr.executeUpdate("update TZ_NOTICE set cnt = cnt + 1 where seq = " + v_seq);
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
     * 포럼 공지사항 수정
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 리스트
     * @throws Exception
     */
    public int updateNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // String sql = "";
        String sql1 = "";
        // String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        // int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        // String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_adtitle = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        // String v_loginyn = box.getString("p_login");
        // String v_useyn = box.getString("p_use");
        String v_upfile = box.getNewFileName("p_file1");
        // String v_realfile = box.getRealFileName("p_file1");
        // String v_isall = box.getString("p_isAllvalue");
        // String v_grcodecd = box.getString("p_grocdecd");

        String s_userid = box.getSession("userid");
        // String s_name = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //	       sql1 =  " update TZ_NOTICE set adtitle = ?, adcontent = empty_clob(), luserid = ?, upfile = ?, 	";
            sql1 = " update TZ_NOTICE set adtitle = ?, adcontent = ?, luserid = ?, upfile = ?, 	";
            sql1 += " ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') 			";
            sql1 += "  where tabseq = ? and seq = ? 									";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setString(index++, v_adtitle);
            pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //pstmt.setString(index++,  v_content);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_upfile);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);

            isOk = pstmt.executeUpdate();
            //   			sql2 = "select adcontent from TZ_NOTICE where tabseq = " + v_tabseq + " and  seq = " + v_seq+ " ";
            //            connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)

            if (!v_upfile.equals("")) {
                isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);
            }

            if (isOk > 0 && isOk2 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
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
     * 포럼 공지사항 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        // String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3 = 1;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select upfile from tz_notice where tabseq = '" + v_tabseq + "' and seq = '" + v_seq + "' ";
            ls = connMgr.executeQuery(sql);
            ls.next();
            String v_file = ls.getString("upfile");
            ls.close();

            sql1 = " delete from TZ_NOTICE";
            sql1 += "  where tabseq = ? and seq = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);

            isOk1 = pstmt1.executeUpdate();

            if (!v_file.equals("")) {
                isOk2 = this.deleteUpFile(connMgr, box);
            }

            if (isOk1 > 0 && isOk2 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        }

        catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2;
    }

    //	=========================================  파일 업로드  =====================================================

    /**
     * 공지사항 새로운 자료파일 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   업로드되는 파일의 형식을	알고 코딩해야한다  --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
            //----------------------	자료 번호 가져온다 ----------------------------
            sql = "select NVL(max(fileseq),	0) from	tz_boardfile	where tabseq = " + p_tabseq + " and seq = " + p_seq;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();

            //------------------------------------------------------------------------------------

            //////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		실제 업로드	되는 파일만	체크해서 db에 입력한다
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);
                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //	일반파일, 첨부파일 있으면 삭제..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk2;
    }

    /**
     * 선택된 자료파일 DB에서 삭제
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence 선택 파일 갯수
     * @return
     * @throws Exception
     */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;
        // String sql = "";
        String sql3 = "";
        // ListSet ls = null;
        int isOk3 = 1;
        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");

        try {
            sql3 = "delete from tz_boardfile where tabseq = " + v_tabseq + " and seq = '" + v_seq + "' and fileseq = '1'  ";
            pstmt3 = connMgr.prepareStatement(sql3);
            isOk3 = pstmt3.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    //	 ===========================================================================================================================
}
