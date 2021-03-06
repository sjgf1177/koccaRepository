//**********************************************************
//  1. 제      목: QNA 관리
//  2. 프로그램명 : QnaBean.java
//  3. 개      요: QNA 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  14
//  7. 수      정: 이나연 05.11.16 _ totalcount 하기위한 쿼리수정
//**********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HomePageContactAdminBean {
    private ConfigSet config;
    private int row;
    private String v_gubun = "05";

    public HomePageContactAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA 리스트
     */
    public ArrayList<DataBox> selectListContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql = "";
        String count_sql = "";
        StringBuffer head_sql = new StringBuffer();
        StringBuffer body_sql = new StringBuffer();
        String group_sql = "";
        String order_sql = "";

        // String sql1 = "";
        DataBox dbox = null;

        String v_grcode = box.getStringDefault("s_grcode", "N000001");
        String v_searchtext = box.getString("p_searchtext");
        // String v_select 	= box.getString("p_select");
        this.v_gubun = box.getString("s_gubun");
        String v_category = box.getString("s_category");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql.append(" SELECT a.seq, b.name, b.email, get_jikwinm(b.jikwi, b.comp) as jikwinm, b.cono, \n");
            head_sql.append("    a.quetitle,get_compnm(comp, 2, 4) as post, a.ismail \n");

            body_sql.append("  FROM TZ_CONTACT a, TZ_MEMBER b \n");
            //body_sql.append(" WHERE  (a.QUEID = b.USERID) and gubun = " + SQLString.Format(v_gubun)+" \n");
            body_sql.append(" WHERE a.QUEID = b.USERID \n");

            if (!v_grcode.equals("ALL")) {
                body_sql.append("   AND	a.GRCODE = " + SQLString.Format(v_grcode) + " \n");
            }

            if (!v_category.equals("ALL")) {
                body_sql.append("  AND a.categorycd = " + SQLString.Format(v_gubun) + " \n");
            }

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                v_pageno = 1; //      검색할 경우 첫번째 페이지가 로딩된다

                //    제목으로 검색할때
                body_sql.append(" and a.quetitle like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
            }
            order_sql += " order by addate desc ";

            sql = head_sql.toString() + body_sql.toString() + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            System.out.println("sql : " + sql);
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
     * 화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return HomePageQnaData 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectViewContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_cono = box.getString("p_cono");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT a.seq, b.name, b.email, a.QUEID, ";
            sql += " get_jikwinm(b.jikwi, b.comp) as jikwinm, a.quecontent, ";
            sql += " a.quetitle, get_compnm( comp, 2, 4 ) AS post, a.ANSTITLE, a.ANSCONTENT ";
            sql += " FROM TZ_CONTACT a, TZ_MEMBER b ";
            sql += " WHERE  (a.QUEID = b.USERID)  and b.cono = " + SQLString.Format(v_cono) + " and a.seq = " + v_seq + "and gubun = " + SQLString.Format(v_gubun);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2003.12.25  변경     -------------------------------------------------------------------
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
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // ListSet ls = null;
        // Connection conn = null;
        PreparedStatement pstmt1 = null;
        String sql = "";

        int isOk1 = 1;
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_CONTACT";
            sql += "  where seq = ?  and gubun =  " + SQLString.Format(v_gubun);
            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setInt(1, v_seq);

            isOk1 = pstmt1.executeUpdate();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

    /**
     * 운영자에게 답변하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");

        //String v_ansid      = box.getString("p_ansid");
        String v_anstitle = box.getString("p_anstitle");
        String v_anscontent = box.getString("p_anscontent");

        String v_ismail = "Y"; // 답변 작성되면서 Y 로 세팅

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_CONTACT set ansid = ? , anstitle = ?, anscontent = ?, ismail = ?,      ";
            sql += "                       luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where seq = ? and gubun = " + SQLString.Format(v_gubun);

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, s_userid);
            pstmt.setString(2, v_anstitle);
            pstmt.setString(3, v_anscontent);
            pstmt.setString(4, v_ismail);
            pstmt.setString(5, s_userid);
            pstmt.setInt(6, v_seq);

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
     * 작성자에게 폼메일 발송
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : send ok 2 : send fail
     * @throws Exception
     */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0; //  메일발송 성공 여부

        int v_seq = box.getInt("p_seq");

        // String v_ansid = box.getSession("p_userid");
        String v_anstitle = box.getString("p_anstitle");
        String v_anscontent = box.getString("p_anscontent");

        try {
            connMgr = new DBConnectionManager();

            sql = " select a.seq seq, a.queid queid, a.quetitle quetitle, a.quecontent quecontent, ";
            sql += "        b.cono cono, b.name name, b.email email                 ";
            sql += "   from TZ_CONTACT a, TZ_MEMBER b                                               ";
            sql += "  where a.queid = b.userid                                                      ";
            sql += "    and a.seq  = " + v_seq;

            ls = connMgr.executeQuery(sql);

            ////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "contactus.html";

            FormMail fmail = new FormMail(v_sendhtml); //      폼메일발송인 경우

            MailSet mset = new MailSet(box); //      메일 세팅 및 발송

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (ls.next()) {

                // String v_queid = ls.getString("queid");
                String v_quetitle = ls.getString("quetitle");
                String v_quecontent = ls.getString("quecontent");
                // String v_name = ls.getString("name");
                String v_toEmail = ls.getString("email");
                String v_toCono = ls.getString("cono");

                v_quecontent = StringManager.replace(v_quecontent, "\n", "<br>");
                v_anscontent = StringManager.replace(v_anscontent, "\n", "<br>");

                String v_mailTitle = v_anstitle;

                mset.setSender(fmail); //  메일보내는 사람 세팅

                fmail.setVariable("quetitle", v_quetitle);
                fmail.setVariable("quecontent", v_quecontent);
                fmail.setVariable("anscontent", v_anscontent);
                //				fmail.setVariable("v_name", v_name);

                String v_mailContent = fmail.getNewMailContent();

                boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", v_sendhtml);

                if (isMailed)
                    is_Ok = 1; //      메일발송에 성공하면
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
        return is_Ok;
    }
}
