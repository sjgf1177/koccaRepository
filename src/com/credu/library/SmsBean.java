//**********************************************************
//  1. 제      목: SMS 발송
//  2. 프로그램명: SmsBean.java
//  3. 개      요: SMS 발송
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 8. 1
//  7. 수      정: 정상진 2005. 8. 1
//**********************************************************
package com.credu.library;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

/**
 * SMS 발송
 * 
 * @date : 2003. 5
 * @author : s.j. Jung
 */
@SuppressWarnings("unchecked")
public class SmsBean {

    public SmsBean() {
    }

    /**
     * SMS 전송
     * 
     * @param box
     *            receive from the form object and session
     * @return isOk 전송횟수
     */
    public int sendSms(RequestBox box) throws Exception {

        int isOk = 0;
        boolean isSend = false;
        Vector v_vchecks = box.getVector("p_checks");
        Vector v_vhandphone = box.getVector("p_handphone");
        // String v_schecks = "";
        String v_shandphone = "";
        String v_msg = box.getString("p_title");
        String v_allChk_inja = box.getString("p_allChk_inja");

        // p_tran_date : 추가.. 전송예정시간.. 없으면 즉시 나간다. 현재 값을 가져오지 않기 때문에 즉시나감
        // by swchoi

        String v_tran_date = box.getString("p_tran_date");

        // Vector v_vsubj = box.getVector("p_subj");
        // Vector v_vyear = box.getVector("p_year");
        // Vector v_vsubjseq = box.getVector("p_subjseq");

        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_subjnm = "";
        String v_seqgrnm = "";
        String sql1 = "";
        ListSet ls1 = null;
        DBConnectionManager connMgr = null;

        String v_touch = box.getString("p_touch");

        // 발신번호 properites 에서 가지고 오기
        ConfigSet conf = new ConfigSet();
        String p_fromPhone = box.getStringDefault("from", conf.getProperty("sms.admin.comptel"));

        // HISTORY 저장
        DataBox dbox = null;
        MailSet mset = new MailSet(box); // 메일 세팅 및 발송
        try {
            connMgr = new DBConnectionManager();

            for (int i = 0; i < v_vchecks.size(); i++) {
                // v_schecks = (String) v_vchecks.elementAt(i);
                v_shandphone = (String) v_vhandphone.elementAt(i);
                isSend = this.sendSMSMsg(v_shandphone, p_fromPhone, v_msg, v_tran_date);
                if (isSend)
                    isOk++;
            }

            // 2009.11.03 추가 - 오류수정
            dbox = new DataBox("selectbox");

            if (v_allChk_inja.split(",").length > 3) {
                // 과정정보
                String[] tmp = v_allChk_inja.split("/");

                for (int i = 0; i < tmp.length; i++) {
                    String[] tmp1 = tmp[i].split(",");

                    v_subj = tmp1[1];
                    v_year = tmp1[2];
                    v_subjseq = tmp1[3];
                    String v_userid = tmp1[0];

                    sql1 = " select a.subj, a.year, a.subjseq, a.subjnm, b.grseqnm ";
                    sql1 += "\n   from tz_subjseq a, tz_grseq b              ";
                    sql1 += "\n  where a.grcode = b.grcode ";
                    sql1 += "\n    and a.gyear = b.gyear ";
                    sql1 += "\n    and a.grseq = b.grseq ";
                    sql1 += "\n    and subj = " + StringManager.makeSQL(v_subj);
                    sql1 += "\n    and year = " + StringManager.makeSQL(v_year);
                    sql1 += "\n    and subjseq = " + StringManager.makeSQL(v_subjseq);

                    ls1 = connMgr.executeQuery(sql1);
                    while (ls1.next()) {
                        v_subjnm = ls1.getString("subjnm");
                        v_seqgrnm = ls1.getString("grseqnm");
                    }
                    // HISTORY 저장
                    // dbox.put("d_subj", v_msubj);
                    // dbox.put("d_year", v_myear);
                    // dbox.put("d_subjseq", v_msubjseq);
                    dbox.put("d_subj", v_subj);
                    dbox.put("d_year", v_year);
                    dbox.put("d_subjseq", v_subjseq);
                    dbox.put("d_userid", v_userid);
                    dbox.put("d_touch", v_touch);
                    dbox.put("d_ismail", "2");
                    dbox.put("d_title", v_msg);
                    if (isSend)
                        dbox.put("d_isok", "Y");
                    else
                        dbox.put("d_isok", "N");
                    dbox.put("d_ismailopen", "N");
                    dbox.put("d_subjnm", v_subjnm);
                    dbox.put("d_seqgrnm", v_seqgrnm);

                    mset.insertHumanTouch(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
        }
        return isOk;
    }

    /**
     * SMS 전송 (디비 저장)
     * 
     * @parameter p_toPhone SMS 수신자 번호
     * @parameter p_fromPhone SMS 발신자 번호
     * @parameter p_msg 메세지
     * @return isOk 등록 성공여부
     */
    public boolean sendSMSMsg(String p_toPhone, String p_fromPhone, String p_msg) throws Exception {
        return sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
    }

    /**
     * SMS 전송 (디비 저장)
     * 
     * @parameter p_toPhone SMS 수신자 번호
     * @parameter p_fromPhone SMS 발신자 번호
     * @parameter p_msg 메세지
     * @return isOk 등록 성공여부
     */
    public boolean sendSMSMsg(String p_toPhone, String p_fromPhone, String p_msg, String tran_date) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        boolean isOk = false;
        int a = 0;

        try {
            connMgr = new DBConnectionManager();

            // insert TZ_EXAM MASTER table
            sql = " insert into em_smt_tran (mt_pr, date_client_req, mt_refkey,content, callback, service_type, broadcast_yn, msg_status, recipient_num)";
            sql += " values (sq_em_smt_tran_01.nextval,  sysdate, 'academy', ?, ?, '0', 'N', '1', ?) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_msg);
            pstmt.setString(2, p_fromPhone);
            pstmt.setString(3, p_toPhone);

            a = pstmt.executeUpdate();
            if (a > 0)
                isOk = true;
            else
                isOk = false;
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
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
     * SMS 발송 대상자 명단 리스트
     * 
     * @param box
     *            receive from the form object and session
     * @return ArrayList
     */
    public ArrayList sendUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        String sql1 = "";
        String where_query = "";

        // String v_userid = box.getSession("userid");

        Vector v_vchecks = box.getVector("p_checks");
        String v_schecks = "";

        // where_query = "('xxxx'";
        where_query = "( ''";// + SQLString.Format(v_userid);
        for (int i = 0; i < v_vchecks.size(); i++) {
            v_schecks = (String) v_vchecks.elementAt(i);
            if (v_schecks.indexOf(',') > 0) {
                v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
            }

            where_query = where_query + "," + SQLString.Format(v_schecks);
        }
        where_query += ")";

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql1 += " select get_compnm(comp,2,2) companynm, userid, name, get_jikwinm(jikwi, comp) jikwinm, crypto.dec('normal',handphone) handphone, grcode ";
            sql1 += "\n   from TZ_MEMBER                  ";
            sql1 += "\n  where userid in " + where_query;
            // sql1+= "\n and grcode = nvl(" +
            // SQLString.Format(box.get("s_grcode"))+", grcode)";
            sql1 += "\n and (handphone is not null or handphone!='')";
            sql1 += "\n order by name                     ";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                // if (dbox.get("d_grcode").equals("N000001")) {
                // ====================================================
                // 개인정보 복호화 - HTJ
                /*
                 * SeedCipher seed = new SeedCipher(); if
                 * (!dbox.getString("d_handphone").equals(""))
                 * dbox.put("d_handphone"
                 * ,seed.decryptAsString(Base64.decode(dbox
                 * .getString("d_handphone")), seed.key.getBytes(), "UTF-8"));
                 */

                // if (!dbox.getString("d_handphone").equals(""))
                // dbox.put("d_handphone",
                // encryptUtil.decrypt(dbox.getString("d_handphone")));
                // ====================================================
                // }

                list1.add(dbox);
            }

        } catch (Exception ex) {
            Log.err.println(this.getClass().getName() + "." + this.getClass().getMethods().toString() + " : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex, box, sql1);
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
        return list1;
    }

}