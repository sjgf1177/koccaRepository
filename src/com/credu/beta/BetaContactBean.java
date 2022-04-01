// **********************************************************
// 1. 제 목: beta2004-12-16 자료실
// 2. 프로그램명: BulletinBeanjava
// 3. 개 요: beta 자료실
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이정한 2003. 4. 26
// 7. 수 정: 이정한 2003. 4. 26
// **********************************************************

package com.credu.beta;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.dunet.common.util.StringUtil;

/**
 * 자료실(HomePage) 관련 beta Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class BetaContactBean {
    // private ConfigSet config;
    // private int row;
    // private String v_type = "FB";
    private String v_hyunuserid = "lee1";
    private String v_kiauserid = "vlc";

    public BetaContactBean() {
        try {
            // config = new ConfigSet();
            // row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataBox selectMail(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String s_userid = box.getSession("userid");

        try {
            // list = new ArrayList();
            // System.out.println("public DataBox selectMail(RequestBox box) throws Exception 호출");
            connMgr = new DBConnectionManager();

            sql = " select name, email, cono  ";
            sql += " from TZ_member                                         ";
            sql += " where userid  = " + SQLString.Format(s_userid);

            // System.out.println("sql ========>" + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                // System.out.println("v_email = " + dbox.getString("d_email"));
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

    /*
     * 메일보내기
     */
    public boolean sendMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        DataBox dbox = null;
        boolean isMailed = false;
        ArrayList<DataBox> list = null;

        String v_toEmail = "";

        String v_name = box.getString("p_name");
        // System.out.println("v_name ================= " + v_name);

        String v_email = box.getString("p_email");
        // System.out.println("v_email ================= " + v_email);

        String v_toCono = box.getString("p_cono");
        // System.out.println("v_toCono ================= " + v_toCono);

        String v_mailTitle = StringUtil.removeTag(box.getString("p_title"));
        // System.out.println("v_mailTitle ================== " + v_mailTitle);

        String v_content = StringUtil.removeTag(box.getString("p_content"));
        // System.out.println("content ================= " + v_content);

        // ////////////////// 프리메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////
        // String v_sendhtml = "freeMailForm.html";
        // FormMail fmail = new FormMail(v_sendhtml);
        MailSet mset = new MailSet(box); // 메일 세팅 및 발송
        // mset.setSender(fmail); // 메일보내는 사람 세팅
        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();
            // ---------------------- 게시판 번호 가져온다 ----------------------------
            sql = "select email from tz_member where (userid =" + SQLString.Format(v_hyunuserid) + "or  userid = " + SQLString.Format(v_kiauserid) + ")";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            // ------------------------------------------------------------------------------------

            String v_mailContent = v_content + "답변메일은 " + v_name + "님의 " + v_email + "로 보내주세요!!";

            for (int i = 0; i < list.size(); i++) {
                dbox = (DataBox) list.get(i);
                v_toEmail = dbox.getString("d_email");
                // System.out.println("v_toEmail =======================>" + v_toEmail);
                // System.out.println("메일보내기");
                isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", "");
            }
            ls.close();

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
        // System.out.println("리턴 = " + isMailed);
        return isMailed;
    }

    /**
     * 선택된 자료실 게시물 상세내용 select /
     * 
     * public boolean selectPds(RequestBox box) throws Exception {
     * 
     * boolean isMailed = false;
     * 
     * String v_mailTitle = box.getString("P_title"); String v_Email = box.getString("p_email"); String v_mailContent = box.getString("p_content"); String
     * v_toCono = ""; String v_fromname = box.getString("p_name"); String v_toEmail = "test@test.com"; String v_sendhtml = "";
     * 
     * MailSet mset = new MailSet(box); // 메일 세팅 및 발송 //mset.setSender(v_Email); // 메일보내는 사람 세팅 try { System.out.println("메일보내기"); isMailed =
     * mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", v_sendhtml); } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box); }
     * 
     * System.out.println("리턴 = " + isMailed); return isMailed; }
     */

}
