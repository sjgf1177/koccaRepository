// **********************************************************
// 1. 제 목: 로긴관리
// 2. 프로그램명 : LoginBean.java
// 3. 개 요: 로그인,패스워드찾기
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2003. 7. 2
// 7. 수 정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
// **********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.credu.system.CountBean;
import com.credu.system.MemberData;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;
import com.dunet.common.util.StringUtil;

import ipin.Interop;

@SuppressWarnings("unchecked")
public class LoginBean {

    public LoginBean() {
    }

    /**
     * 로그인 (세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok -1 : ID 오류 -2 : 퇴직자 -3 : 비밀번호 오류
     * @throws Exception
     */
    public int loginSSO(RequestBox box, String userid, String passwd, String userip) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql1 = "";
        MemberData data = null;
        int is_Ok = 0;
        String v_userid = userid;
        String v_userip = userip;
        String v_isEvent = "N";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // userid,resno,name,email, comp, jikup, cono
            sql1 = " select userid, resno, name, email, pwd, eng_name, post1, post2, addr, addr2  \n";
            sql1 += "        comp_post1, comp_post2, comp_addr1, comp_addr2, comptel, comptext, degree,  \n";
            sql1 += "        ismailing, islettering, isopening, isevent,lglast  \n";
            sql1 += " from TZ_MEMBER                     \n";
            sql1 += " where userid = " + StringManager.makeSQL(v_userid);

            // 탈퇴하지 않은 회원만.. 통합 전 state = 'Y' 통합 후 LEAVE_DATE is null
            // sql1 += "\n and state = 'Y'";
            sql1 += "\n and LEAVE_DATE is null";
            /**
             * 관리자와 유저의 GRCODE 단위로 권한체크 필요시 주석 제거 or 추가 2010.03.22
             */
            sql1 += "\n and GRCODE = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            ls = connMgr.executeQuery(sql1);

            // 로그인 결과
            // 1 : 성공
            // -1 : 사용자 없음
            // -2 : 퇴직자
            // -3 : 비밀번호 오류

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setLglast(ls.getString("lglast"));
                data.setComptel(ls.getString("comptel"));
                // 이벤트
                v_isEvent = ls.getString("isevent");

                is_Ok = 1; // 성공

            } else {
                is_Ok = -1; // 사용자정보 없음
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (is_Ok == 1) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comptel", data.getComptel());
                box.setSession("userip", v_userip);
                box.setSession("isevent", v_isEvent);
                box.setSession("lglast", data.getLglast());

                box.setSession("gadmin", "ZZ");

                // box.getHttpSession().setAttribute("binding.SessionListener", new SessionListener(box));

                // 접속통계 누적 부분
                CountBean bean1 = new CountBean();
                bean1.writeLog(connMgr, box); // 로그 작성

                // LOGIN DATA UPDATE
                updateLoginData(connMgr, v_userid, v_userip, box.getSession("tem_grcode"));

                createMemberLoginLog(connMgr, box, "SSO");

            } else if (is_Ok == -3) {
                // 오류 회수 추가
                // is_Ok2 = updateLoginFail(connMgr, v_userid);

            }

            connMgr.commit();
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
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 로그인 (세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok -1 : ID 오류 -2 : 퇴직자 -3 : 비밀번호 오류
     * @throws Exception
     */
    public int login(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        MemberData data = null;

        int is_Ok = 0;

        String sql1 = "";
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_grcode = box.getSession("tem_grcode");

        StringBuilder sb = new StringBuilder();

        if (v_grcode.equals("")) {
            v_grcode = "N000001";
            box.setSession("tem_grcode", v_grcode);
        }

        if (v_userid.equals("")) { // 모바일
            v_userid = box.getString("p_topuserid");
        }

        if (v_pwd.equals("")) { // 모바일
            v_pwd = box.getString("p_toppwd");
        }

        String v_userip = box.getString("p_userip");

        String p_originalLoginUserid = box.getSession("p_originalLoginUserid");

        // String lgfirst = "";

        if (p_originalLoginUserid != null && p_originalLoginUserid.length() > 0) {
            v_userid = p_originalLoginUserid;
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // HTJ 복호화 모듈 변경
            sb.append("SELECT   USERID                                  \n");
            sb.append("     ,   RESNO                                   \n");
            sb.append("     ,   NAME                                    \n");
            sb.append("     ,   CRYPTO.DEC('normal', EMAIL) AS EMAIL    \n");
            sb.append("     ,   PWD                                     \n");
            sb.append("     ,   ENG_NAME                                \n");
            sb.append("     ,   POST1                                   \n");
            sb.append("     ,   POST2                                   \n");
            sb.append("     ,   ADDR                                    \n");
            sb.append("     ,   ADDR2                                   \n");
            sb.append("     ,   SEX                                     \n");
            sb.append("     ,   COMP_POST1                              \n");
            sb.append("     ,   COMP_POST2                              \n");
            sb.append("     ,   COMP_ADDR1                              \n");
            sb.append("     ,   COMP_ADDR2                              \n");
            sb.append("     ,   COMPTEL                                 \n");
            sb.append("     ,   COMPTEXT                                \n");
            sb.append("     ,   DEGREE                                  \n");
            sb.append("     ,   ISMAILING                               \n");
            sb.append("     ,   ISLETTERING                             \n");
            sb.append("     ,   ISOPENING                               \n");
            sb.append("     ,   CRYPTO.DEC('normal', HANDPHONE) AS HANDPHONE  \n");
            sb.append("     ,   LGLAST                                  \n");
            sb.append("     ,   RESNO1                                  \n");
            sb.append("     ,   RESNO2                                  \n");
            sb.append("     ,   LGFIRST                                 \n");
            sb.append("  FROM   TZ_MEMBER                               \n");
            sb.append(" WHERE   USERID = '").append(v_userid).append("' \n");

            // 탈퇴하지 않은 회원만. 통합 전 state = 'Y', 통합 후 LEAVE_DATE is null
            sb.append("  AND    LEAVE_DATE IS NULL                      \n");

            // 관리자와 유저의 GRCODE 단위로 권한체크 필요시 주석 제거 or 추가 2010.03.22
            sb.append("  AND    GRCODE = '" + v_grcode + "'             \n");

            ls = connMgr.executeQuery(sb.toString());

            // 로그인 결과
            // 1 : 성공
            // -1 : 사용자 없음
            // -2 : 퇴직자
            // -3 : 비밀번호 오류

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setResno1(ls.getString("resno1"));
                data.setResno2(ls.getString("resno2"));
                data.setName(ls.getString("name"));
                data.setComptel(ls.getString("comptel"));
                data.setHandphone(ls.getString("handphone"));
                data.setLglast(ls.getString("lglast"));

                String isGender = ls.getString("sex");

                if (!isGender.equals("2")) {
                    isGender = "1";
                }
                data.setSex(isGender);

                // 비밀번호 암호화. DB 암호화 모듈이 아닌 CLASS에서 암호화를 수행함.
                v_pwd = HashCipher.createHash(v_pwd);

                if (ls.getString("pwd").equals(v_pwd) || p_originalLoginUserid.length() > 0) {
                    is_Ok = 1; // 성공
                } else {
                    is_Ok = -3; // 비밀번호 오류
                }
            } else {
                is_Ok = -1; // 사용자정보 없음
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                    ErrorManager.getErrorStackTrace(e);
                    throw new Exception("로그인 오류 : " + e.getMessage());
                }
            }
            if (is_Ok == 1) {
                box.setSession("p_originalLoginUserid", box.get("p_originalLoginUserid"));
                String p_originalgadmin = box.getSession("p_originalgadmin");

                if (p_originalgadmin.length() > 0) {
                    box.setSession("gadmin", p_originalgadmin);
                    box.setSession("p_originalgadmin", "");
                } else {
                    box.setSession("p_originalgadmin", box.getSession("gadmin"));
                    box.setSession("gadmin", "ZZ");
                }
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("resno1", data.getResno1());
                box.setSession("resno2", data.getResno2());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comptel", data.getComptel());
                box.setSession("userip", v_userip);
                box.setSession("handphone", data.getHandphone());
                box.setSession("lglast", data.getLglast());
                box.setSession("gender", data.getSex());

                // 접속통계 누적 부분
                CountBean bean1 = new CountBean();
                bean1.writeLog(connMgr, box); // 로그 작성

                updateLoginData(connMgr, v_userid, v_userip, box.getSession("tem_grcode"));

                // 사용자별 로그인 정보를 기록한다.
                createMemberLoginLog(connMgr, box, "NORMAL");

            } else if (is_Ok == -3) {
                // 오류 회수 추가
                // is_Ok2 = updateLoginFail(connMgr, v_userid);

            }
            connMgr.commit();
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
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * LMS 관리자 로그인 (세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok -1 : ID 오류 -2 : 퇴직자 -3 : 비밀번호 오류
     * @throws Exception
     */
    public int adminLogin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        MemberData data = null;

        int is_Ok = 0;

        String sql1 = "";
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_grcode = box.getSession("tem_grcode");
        String v_auth = box.getString("p_auth");
        String v_gadminnm = "";

        StringBuilder sb = new StringBuilder();

        if (v_grcode.equals("")) {
            v_grcode = "N000001";
        }

        if (v_userid.equals("")) { // 모바일
            v_userid = box.getString("p_topuserid");
        }

        if (v_pwd.equals("")) { // 모바일
            v_pwd = box.getString("p_toppwd");
        }

        String v_userip = box.getString("p_userip");

        String p_originalLoginUserid = box.getSession("p_originalLoginUserid");

        // String lgfirst = "";

        if (p_originalLoginUserid != null && p_originalLoginUserid.length() > 0) {
            v_userid = p_originalLoginUserid;
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // HTJ 복호화 모듈 변경
            sb.append("SELECT   A.USERID                                  \n");
            sb.append("     ,   A.RESNO                                   \n");
            sb.append("     ,   A.NAME                                    \n");
            sb.append("     ,   CRYPTO.DEC('normal', A.EMAIL) AS EMAIL    \n");
            sb.append("     ,   A.PWD                                     \n");
            sb.append("     ,   A.ENG_NAME                                \n");
            sb.append("     ,   A.POST1                                   \n");
            sb.append("     ,   A.POST2                                   \n");
            sb.append("     ,   A.ADDR                                    \n");
            sb.append("     ,   A.ADDR2                                   \n");
            sb.append("     ,   A.SEX                                     \n");
            sb.append("     ,   A.COMP_POST1                              \n");
            sb.append("     ,   A.COMP_POST2                              \n");
            sb.append("     ,   A.COMP_ADDR1                              \n");
            sb.append("     ,   A.COMP_ADDR2                              \n");
            sb.append("     ,   A.COMPTEL                                 \n");
            sb.append("     ,   A.COMPTEXT                                \n");
            sb.append("     ,   A.DEGREE                                  \n");
            sb.append("     ,   A.ISMAILING                               \n");
            sb.append("     ,   A.ISLETTERING                             \n");
            sb.append("     ,   A.ISOPENING                               \n");
            sb.append("     ,   CRYPTO.DEC('normal', A.HANDPHONE) AS HANDPHONE  \n");
            sb.append("     ,   A.LGLAST                                  \n");
            sb.append("     ,   A.RESNO1                                  \n");
            sb.append("     ,   A.RESNO2                                  \n");
            sb.append("     ,   A.LGFIRST                                 \n");
            sb.append("     ,   B.GADMIN                                  \n");
            sb.append("     ,   C.GADMINNM                                \n");
            sb.append("  FROM   TZ_MEMBER A, TZ_MANAGER B, TZ_GADMIN C    \n");
            sb.append(" WHERE   A.USERID = '").append(v_userid).append("' \n");
            sb.append("  AND    A.USERID = B.USERID                       \n");
            sb.append("  AND    B.GADMIN = C.GADMIN                       \n");
            sb.append("  AND    B.GADMIN = '" + v_auth + "'               \n");

            // 탈퇴하지 않은 회원만. 통합 전 state = 'Y', 통합 후 LEAVE_DATE is null
            sb.append("  AND    A.LEAVE_DATE IS NULL                      \n");

            // 관리자와 유저의 GRCODE 단위로 권한체크 필요시 주석 제거 or 추가 2010.03.22
            sb.append("  AND    A.GRCODE = '" + v_grcode + "'             \n");

            ls = connMgr.executeQuery(sb.toString());

            // 로그인 결과
            // 1 : 성공
            // -1 : 사용자 없음
            // -2 : 퇴직자
            // -3 : 비밀번호 오류

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setResno1(ls.getString("resno1"));
                data.setResno2(ls.getString("resno2"));
                data.setName(ls.getString("name"));
                data.setComptel(ls.getString("comptel"));
                data.setHandphone(ls.getString("handphone"));
                data.setLglast(ls.getString("lglast"));
                data.setGadmin(ls.getString("gadmin"));

                String isGender = ls.getString("sex");
                v_gadminnm = ls.getString("gadminnm");

                if (!isGender.equals("2")) {
                    isGender = "1";
                }
                data.setSex(isGender);

                // 비밀번호 암호화. DB 암호화 모듈이 아닌 CLASS에서 암호화를 수행함.
                v_pwd = HashCipher.createHash(v_pwd);
                // ========================================

                if (ls.getString("pwd").equals(v_pwd) || p_originalLoginUserid.length() > 0) {
                    is_Ok = 1; // 성공
                } else {
                    is_Ok = -3; // 비밀번호 오류
                }
            } else {
                is_Ok = -1; // 사용자정보 없음
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                    ErrorManager.getErrorStackTrace(e);
                    throw new Exception("로그인 오류 : " + e.getMessage());
                }
            }
            if (is_Ok == 1) {
                box.setSession("p_originalLoginUserid", box.get("p_originalLoginUserid"));
                String p_originalgadmin = box.getSession("p_originalgadmin");

                if (p_originalgadmin.length() > 0) {
                    box.setSession("gadmin", p_originalgadmin);
                    box.setSession("p_originalgadmin", "");
                } else {
                    box.setSession("p_originalgadmin", box.getSession("gadmin"));
                    box.setSession("gadmin", "ZZ");
                }
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("resno1", data.getResno1());
                box.setSession("resno2", data.getResno2());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comptel", data.getComptel());
                box.setSession("userip", v_userip);
                box.setSession("handphone", data.getHandphone());
                box.setSession("lglast", data.getLglast());
                box.setSession("gender", data.getSex());
                box.setSession("gadmin", data.getGadmin());
                box.setSession("gadminnm", v_gadminnm);

                // 접속통계 누적 부분
                CountBean bean1 = new CountBean();
                bean1.writeLog(connMgr, box); // 로그 작성

                updateLoginData(connMgr, v_userid, v_userip, box.getSession("tem_grcode"));

                // 사용자별 로그인 정보를 기록한다.
                createMemberLoginLog(connMgr, box, "NORMAL");

                // 강사로그인시
                String v_serno = box.getSession("serno"); // 강사 접속 로그 일련번호
                int v_serno1 = 0;
                if (v_auth.equals("P1") && v_serno.equals("")) { // 권한이 강사일 경우
                    // 강사 로그인 정보에
                    // 입력한다.
                    TutorLoginBean tbean = new TutorLoginBean();
                    v_serno1 = tbean.tutorLogin(box);
                    box.setSession("serno", v_serno1);
                }
                // 검색조건에서 과정의 전체선택 가능여부를 결정한다.
                if (v_auth.equals("P1")) {
                    box.setSession("isSubjAll", "tutor");
                } else {
                    box.setSession("isSubjAll", "true");
                }

            } else if (is_Ok == -3) {
                // 오류 회수 추가
                // is_Ok2 = updateLoginFail(connMgr, v_userid);

            }
            connMgr.commit();
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
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 로그인 이력 정보를 저장한다.
     * 
     * @param connMgr
     * @param box
     * @throws Exception
     */
    private void createMemberLoginLog(DBConnectionManager connMgr, RequestBox box, String loginType) throws Exception {
        StringBuilder sb = new StringBuilder();
        PreparedStatement pstmt = null;
        int idx = 0;
        try {
            //System.out.println("hostip : " + box.getString("hostip"));
            sb.append("INSERT INTO TZ_LOG_MEMBER_LOGIN  \n");
            sb.append("     (   \n");
            sb.append("         USERID          \n");
            sb.append("     ,   LOGIN_DT        \n");
            sb.append("     ,   SESSION_ID      \n");
            sb.append("     ,   USER_IP         \n");
            sb.append("     ,   HOST_IP         \n");
            sb.append("     ,   GRCODE          \n");
            sb.append("     ,   LOGIN_TYPE      \n");
            sb.append("     )   VALUES (        \n");
            sb.append("         ?, SYSDATE, ?,  \n");
            sb.append("         ?, ?, ?, ?      \n");
            sb.append("     )                   \n");

            pstmt = connMgr.prepareStatement(sb.toString());

            pstmt.setString(++idx, box.getSession("userid"));
            pstmt.setString(++idx, box.getSessionId());
            pstmt.setString(++idx, box.getString("p_userip"));
            pstmt.setString(++idx, box.getString("hostip"));
            pstmt.setString(++idx, box.getSession("tem_grcode"));
            pstmt.setString(++idx, loginType);

            pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("사용자 로그인 이력 저장 오류 :\r\n" + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }

    }

    /**
     * 패스워드 검색
     * 
     * @param box receive from the form object and session
     * @return String 패스워드 검색 결과
     * @throws Exception
     */
    public String getPwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid");
        String v_name = box.getString("p_name_2");
        // String v_resno1 = box.getString("p_resno2_1");
        // String v_resno2 = box.getString("p_resno2_2");
        // String v_resno = v_resno1 + v_resno2;

        String result = "";

        try {
            // 개인정보 암호화
            // SeedCipher seed = new SeedCipher();
            // String v_resno2_enc = Base64.encode(seed.encrypt(v_resno2, seed.key.getBytes(), "UTF-8")).replace("\n","");

            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            // String v_resno2_enc = encryptUtil.encrypt(v_resno2);
            // ===========================

            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select pwd      ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " where userid = " + StringManager.makeSQL(v_userid);
            // sql1 += "   and resno1  = " + StringManager.makeSQL(v_resno1);
            // sql1 += "   and resno2  = " + StringManager.makeSQL(v_resno2_enc);
            sql1 += "   and name   = " + StringManager.makeSQL(v_name);
            sql1 += "   and grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            sql1 += "   and state  = 'Y' ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("pwd");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    /**
     * ID 검색
     * 
     * @param box receive from the form object and session
     * @return String ID 검색 결과
     * @throws Exception
     */
    public String getUserid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";

        String v_name = box.getString("p_name");
        // String v_resno1 = box.getString("p_resno1");
        // String v_resno2 = box.getString("p_resno2");
        String v_userid = box.getString("p_userid");
        // String v_email = box.getString("p_email");
        // String v_handphone = box.getString("p_handphone");
        // String v_resno = v_resno1 + v_resno2;
        String v_dupinfo = box.getString("p_dupinfo");

        String result = "";

        try {
            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select userid      ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " where dupinfo  = " + StringManager.makeSQL(v_dupinfo);
            sql1 += " and userid =" + StringManager.makeSQL(v_userid);
            // 메인페이지 사용자 일때

            sql1 += "   and name   = " + StringManager.makeSQL(v_name);
            sql1 += "   and state = 'Y' ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("userid");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    public String getHandphone(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid");
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select crypto.dec('normal',handphone) as handphone     ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " WHERE userid =" + StringManager.makeSQL(v_userid);
            sql1 += "   and state = 'Y' ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("handphone");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    // email 가져오기
    public String getEmail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_userid");
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select crypto.dec('normal',email) as email     ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " WHERE userid =" + StringManager.makeSQL(v_userid);
            sql1 += "   and state = 'Y' ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("email");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    /**
     * ID 검색
     * 
     * @param box receive from the form object and session
     * @return String ID 검색 결과
     * @throws Exception
     */
    public String getUseridHandPhone(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";

        String v_name = box.getString("p_name");
        String v_userid = box.getString("p_userid");
        String v_handphone = box.getString("p_handphone");
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select userid      ";
            sql1 += " from TZ_MEMBER  ";
            // 메인페이지 사용자 일때

            sql1 += " where crypto.dec('normal',handphone)  = " + StringManager.makeSQL(v_handphone);
            sql1 += " and userid =" + StringManager.makeSQL(v_userid);
            sql1 += " and name =" + StringManager.makeSQL(v_name);
            sql1 += "   and state = 'Y' ";
            // System.out.println(sql1);

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("userid");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    /**
     * 담당기관 구하기
     * 
     * @param box receive from the form object and session
     * @return String 담당기관
     * @throws Exception
     */
    public String getGrtype(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String ss_userid = box.getSession("userid");
        String ss_gadmin = box.getSession("gadmin");
        String result = "";
        String gadmin = "";
        ConfigSet conf = new ConfigSet();
        String upperclass = "";

        try {
            connMgr = new DBConnectionManager();
            // 관리자일경우 해당기관 세션세팅
            sql1 = " select grtype,gadmin  ";
            sql1 += "   from TZ_MANAGER     ";
            sql1 += "  where userid = " + StringManager.makeSQL(ss_userid);
            sql1 += "    and gadmin = " + StringManager.makeSQL(ss_gadmin);
            sql1 += "    and isdeleted = 'N' ";
            sql1 += "  order by gadmin asc   ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("grtype");
                gadmin = ls1.getString("gadmin");
                // 대분류코드 세팅
                if (StringManager.substring(gadmin, 0, 1).equals("A")) {
                    box.setSession("upperclass", "ALL");
                } else {
                    if (!result.equals("")) {
                        upperclass = conf.getProperty("gadmin." + result + ".upperclass");
                        box.setSession("upperclass", upperclass);
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("getGrtype = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    /**
     * 로그인 연동(세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int ssologin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        MemberData data = null;
        int is_Ok = 0;
        String v_userid = box.getString("p_userid");
        String v_userip = box.getString("p_userip");

        try {
            connMgr = new DBConnectionManager();
            // userid,resno,name,email, comp, jikup, cono
            sql1 = " select userid, resno, name, email,pwd, office_gbn, comp, jikup, cono, comptel,                               ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, (deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        nvl((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += " from TZ_MEMBER                     ";
            sql1 += " where userid = " + StringManager.makeSQL(v_userid);
            sql1 += "\n and GRCODE = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            // System.out.println(" login sql1 ==>"+ sql1);
            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                data.setCompgubun(ls.getString("compgubun"));
                data.setOffice_gbn(ls.getString("office_gbn"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setDeptnm(ls.getString("deptnm"));

                is_Ok = 1;
            }
            ls.close();

            if (is_Ok != 0) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("compgubun", data.getCompgubun());
                box.setSession("jikwinm", data.getJikwinm());
                box.setSession("deptnm", data.getDeptnm());

                box.setSession("userip", v_userip);

                box.setSession("gadmin", "ZZ");
                // LOGIN DATA UPDATE
                updateLoginData(connMgr, v_userid, v_userip, box.getSession("tem_grcode"));

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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 외부강의실 로그인 연동(세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int ssologin2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        MemberData data = null;
        int is_Ok = 0;
        String resno = box.getSession("resno");
        String comp = box.getSession("comp");

        try {
            connMgr = new DBConnectionManager();
            // userid,resno,name,email, comp, jikup, cono
            sql1 = " select userid, resno, name, email,pwd, office_gbn, comp, jikup, cono, comptel,                               ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            // 수정일 : 05.11.17 수정자 : 이나연 _ rownum 수정
            // sql1 +=
            // "        NVL((select compgubun from tz_compclass where comp=tz_member.comp and rownum=1),'') compgubun         ";
            sql1 += "        NVL((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2),'') compgubun         ";
            sql1 += "        jikwi, get_jikwinm(jikwi,comp) jikwinm, get_deptnm(deptnam,'') deptnm, get_compnm(comp,2,2) companynm, ";
            sql1 += "        NVL((select compgubun from ( select rownum rnum, compgubun from tz_compclass where comp=tz_member.comp ) where rnum < 2 ),'') compgubun         ";

            sql1 += " from TZ_MEMBER                     ";
            sql1 += " where resno = " + StringManager.makeSQL(resno);
            sql1 += "   and comp  = " + StringManager.makeSQL(comp);
            // System.out.println(" login sql2 ==>"+ sql1);
            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                data.setCompgubun(ls.getString("compgubun"));
                data.setOffice_gbn(ls.getString("office_gbn"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setDeptnm(ls.getString("deptnm"));

                is_Ok = 1;
            }
            ls.close();

            if (is_Ok != 0) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("compgubun", data.getCompgubun());
                box.setSession("jikwinm", data.getJikwinm());
                box.setSession("deptnm", data.getDeptnm());

                box.setSession("gadmin", "ZZ");

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

    /**
     * Login 정보 변경 (lgcnt:로그인횟수, lglast:최종로그인시간, lgip:로그인ip
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginData(String p_userid, String p_userip) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;

        try {
            connMgr = new DBConnectionManager();

            is_Ok = updateLoginData(connMgr, v_userid, v_userip);
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

        return is_Ok;
    }

    private int updateLoginData(String p_userid, String p_userip, String grcode) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;

        try {
            connMgr = new DBConnectionManager();

            is_Ok = updateLoginData(connMgr, v_userid, v_userip, grcode);
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

        return is_Ok;
    }

    /**
     * Login 정보 변경 (lgcnt:로그인횟수, lglast:최종로그인시간, lgip:로그인ip, lgfirst : 최초로그인
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginData(DBConnectionManager connMgr, String p_userid, String p_userip) throws Exception {
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;
        int cnt = 0;

        try {

            // 최초 로그인 체크
            sql = " select count(*) cnt from tz_member   ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            sql += "   and lgfirst is null                ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            // 정보 업데이트
            sql = " update TZ_MEMBER                       ";
            sql += " set lgcnt=lgcnt+1, lglast= to_char(sysdate, 'YYYYMMDDHH24MISS'), lgip=" + StringManager.makeSQL(v_userip);
            if (cnt > 0) {
                sql += " , lgfirst= to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            }
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            // System.out.println("Loindata sql ==>"+ sql);

            is_Ok = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("에러메소드(LoginBean.updateLoginData) \r\n sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return is_Ok;
    }

    /**
     * 
     * @param connMgr
     * @param p_userid
     * @param p_userip
     * @param grcode
     * @return
     * @throws Exception
     */
    private int updateLoginData(DBConnectionManager connMgr, String p_userid, String p_userip, String grcode) throws Exception {
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;
        int cnt = 0;

        try {

            // 최초 로그인 체크
            sql = " select count(*) cnt from tz_member   ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            sql += "   and lgfirst is null                ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            // 정보 업데이트
            sql = " update TZ_MEMBER                       ";
            sql += "\n set lgcnt = lgcnt + 1, lglast = to_char(sysdate, 'YYYYMMDDHH24MISS'), lgip = " + StringManager.makeSQL(v_userip);
            if (cnt > 0) {
                sql += " , lgfirst = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            }
            sql += "\n where userid = lower(" + StringManager.makeSQL(v_userid) + ")";
            sql += "\n and grcode = " + StringManager.makeSQL(grcode);
            // System.out.println("Loindata sql ==>"+ sql);

            is_Ok = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("에러메소드(LoginBean.updateLoginData) \r\n sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return is_Ok;
    }

    /**
     * 비밀번호 오류 회수 증가
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginFail(String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        int is_Ok = 0;
        String v_userid = p_userid;

        try {
            connMgr = new DBConnectionManager();
            is_Ok = updateLoginFail(connMgr, v_userid);
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
        return is_Ok;
    }

    /**
     * 비밀번호 오류 회수 증가
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginFail(DBConnectionManager connMgr, String p_userid) throws Exception {
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;

        try {

            sql = " update TZ_MEMBER                       ";
            sql += " set lgfail = lgfail + 1 ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);

            // System.out.println("updateLoginFail sql ==>"+ sql);
            is_Ok = connMgr.executeUpdate(sql);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return is_Ok;
    }

    /**
     * 정보동의 체크
     * 
     * @param box receive from the form object and session
     * @return is_Ok 0 : 정보동의필요 1 : 정보동의함
     * @throws Exception
     */
    public int firstCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = box.getSession("userid");
        // String v_pwd = box.getString("p_pwd");

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(validation,'0') validation  ";
            sql += " from TZ_MEMBER                       ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            // sql += "   and pwd    = "+ StringManager.makeSQL(v_pwd);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                is_Ok = StringManager.toInt(ls.getString("validation"));
            } else {
                is_Ok = 1;
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

    /**
     * 이메일 불러오기
     * 
     * @param box receive from the form object and session
     * @return return
     * @throws Exception
     */
    public String emailOpen(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String v_email = "";
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select email  ";
            sql += " from TZ_MEMBER                       ";
            sql += " where userid = lower(" + StringManager.makeSQL(v_userid) + ")";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_email = ls.getString("email");
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

        return v_email;
    }

    /**
     * 정보보호 확인
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int firstLogin(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_MEMBER set validation = ? ";
            sql += "  where userid = ?                   ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, "1");
            pstmt.setString(2, v_userid);
            is_Ok = pstmt.executeUpdate();
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

        return is_Ok;
    }

    public WholeSulData checkResearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        String sql = "";
        String sql1 = "";
        WholeSulData data = null;

        String v_userid = box.getSession("userid");
        String v_today = FormatDate.getDate("yyyyMMdd");

        if (!v_userid.equals("")) {
            String v_comp = box.getSession("comp");
            String v_grpcomp = "";
            String v_gpm = "";
            String v_dept = "";
            if (v_comp.length() == 10) {
                v_grpcomp = StringManager.substring(v_comp, 0, 4);
                v_gpm = StringManager.substring(v_comp, 4, 6);
                v_dept = StringManager.substring(v_comp, 6, 8);
            }
            String v_jikup = box.getSession("jikup");

            try {
                connMgr = new DBConnectionManager();

                sql = " select grcode, subj, year, subjseq, sulpapernum ";
                sql += "   from  TZ_SULPAPER_WHOLE                       ";
                sql += "  where decode(grpcomp,'0000'," + StringManager.makeSQL(v_grpcomp) + ",grpcomp) =" + StringManager.makeSQL(v_grpcomp);
                sql += "    and decode(gpm,'00'," + StringManager.makeSQL(v_gpm) + ",gpm) =" + StringManager.makeSQL(v_gpm);
                sql += "    and decode(dept,'00'," + StringManager.makeSQL(v_dept) + ",dept) =" + StringManager.makeSQL(v_dept);
                sql += "    and decode(jikup,'00'," + StringManager.makeSQL(v_jikup) + ",jikup) =" + StringManager.makeSQL(v_jikup);
                sql += "    and startdate <= " + StringManager.makeSQL(v_today);
                sql += "    and enddate   >= " + StringManager.makeSQL(v_today);
                sql += "   order by startdate desc, sulpapernum desc       ";
                ls = connMgr.executeQuery(sql);

                if (ls.next()) {
                    data = new WholeSulData();

                    data.setGrcode(ls.getString("grcode"));
                    data.setSubj(ls.getString("subj"));
                    data.setYear(ls.getString("year"));
                    data.setSubjseq(ls.getString("subjseq"));
                    data.setSulpapernum(ls.getInt("sulpapernum"));

                    sql1 = " select count(*) cnt from tz_suleach ";
                    sql1 += "  where subj        =  " + StringManager.makeSQL(data.getSubj());
                    sql1 += "    and year        =  " + StringManager.makeSQL(data.getYear());
                    sql1 += "    and subjseq     =  " + StringManager.makeSQL(data.getSubjseq());
                    sql1 += "    and sulpapernum =  " + data.getSulpapernum();
                    sql1 += "    and userid      =  " + StringManager.makeSQL(v_userid);
                    sql1 += "    and f_gubun     =  'W'         ";

                    ls1 = connMgr.executeQuery(sql1);
                    if (ls1.next()) {
                        if (ls1.getInt("cnt") > 0)
                            data = null;
                    }

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
        }

        return data;
    }

    /**
     * 비밀번호 분실 폼메일 발송
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : send ok 2 : send fail
     * @throws Exception
     */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ListSet ls1 = null;
        String sql = "";
        // String sql1 = "";
        int is_Ok = 0; // 메일발송 성공 여부
        String v_grtype = "";

        String v_userid = box.getString("p_userid_2");
        String v_name = box.getString("p_name_2");
        String v_email2 = box.getString("p_email_2");

        // String v_grcode = box.getSession("tem_grcode");
        String v_contents = "";

        try {

            connMgr = new DBConnectionManager();

            v_grtype = "KOCCA";

            String v_sendhtml = "LossPwd.html";
            FormMail fmail = new FormMail(v_sendhtml, v_grtype); // 폼메일발송인 경우

            ConfigSet conf = new ConfigSet();
            String v_fromEmail = "";
            String v_fromName = "";
            String v_comptel = "";

            if (v_grtype.equals("KGDI")) {
                v_fromEmail = conf.getProperty("mail.admin.email.kgdi");
                v_fromName = conf.getProperty("mail.admin.name.kgdi");
                v_comptel = conf.getProperty("mail.admin.comptel.kgdi");
            } else if (v_grtype.equals("KOCCA")) {
                v_fromEmail = conf.getProperty("mail.admin.email.kocca");
                v_fromName = conf.getProperty("mail.admin.name.kocca");
                v_comptel = conf.getProperty("mail.admin.comptel.kocca");
            } else {
                v_fromEmail = conf.getProperty("mail.admin.email");
                v_fromName = conf.getProperty("mail.admin.name");
                v_comptel = conf.getProperty("mail.admin.comptel");
            }
            box.put("p_fromEmail", v_fromEmail);
            box.put("p_fromName", v_fromName);
            box.put("p_comptel", v_comptel);
            MailSet mset = new MailSet(box); // 메일 세팅 및 발송

            String v_mailTitle = " 비밀번호 안내 메일입니다.";
            // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            sql = " select userid, name, crypto.dec('normal',email) email, pwd, ismailing, state ";
            sql += " from TZ_MEMBER                          ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += "   and name   = " + StringManager.makeSQL(v_name);
            sql += "   and crypto.dec('normal',email)   = " + StringManager.makeSQL(v_email2);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                String v_isMailing = "1"; // email로만 전송

                String v_toCono = ls.getString("userid");
                // String v_pwd = ls.getString("pwd"); //임시부여받은 패스워드(암호화설정됨)
                String v_pwd = box.get("tmp_pwd"); // 임시부여받은 패스워드(암호화미설정됨)
                String v_toEmail = ls.getString("email");
                String v_state = ls.getString("state");

                box.put("v_name", v_name);
                box.put("v_pwd", v_pwd);
                box.put("v_toCono", v_toCono);
                box.put("v_toEmail", v_toEmail);

                if (v_toEmail.equals("")) {
                    is_Ok = -2; // 메일주소 없음
                } else if (!v_state.equals("Y")) {
                    is_Ok = -3; // 탈퇴자
                } else {

                    mset.setSender(fmail); // 메일보내는 사람 세팅

                    fmail.setVariable("userid", v_userid);
                    fmail.setVariable("name", ls.getString("name"));
                    fmail.setVariable("pwd", v_pwd);

                    v_contents = v_name + "(" + v_userid + ")" + " 님의  임시 비밀번호는 (" + v_pwd + ") 입니다. 개인정보변경 접속 후 비밀번호 변경을 하시기 바랍니다.";
                    fmail.setVariable("content", v_contents);
                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);

                    if (isMailed)
                        is_Ok = 1; // 메일발송에 성공하면
                }
            } else {
                is_Ok = -1; // 사용자가 없음
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

    /**
     * 비밀번호 분실 폼메일 발송
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : send ok 2 : send fail
     * @throws Exception
     */
    public int sendFormMailNew(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0; // 메일발송 성공 여부
        String v_grtype = "";

        String v_userid = box.getString("p_userid_2");
        String v_email = box.getString("p_email_2");

        String v_contents = "";

        try {

            connMgr = new DBConnectionManager();
            v_grtype = "KOCCA";

            String v_sendhtml = "LossPwd.html";
            FormMail fmail = new FormMail(v_sendhtml, v_grtype); // 폼메일발송인 경우

            ConfigSet conf = new ConfigSet();
            String v_fromEmail = "";
            String v_fromName = "";
            String v_comptel = "";

            v_fromEmail = conf.getProperty("mail.admin.email.kocca");
            v_fromName = conf.getProperty("mail.admin.name.kocca");
            v_comptel = conf.getProperty("mail.admin.comptel.kocca");

            box.put("p_fromEmail", v_fromEmail);
            box.put("p_fromName", v_fromName);
            box.put("p_comptel", v_comptel);
            MailSet mset = new MailSet(box); // 메일 세팅 및 발송

            String v_mailTitle = " 비밀번호 안내 메일입니다.";

            sql = " select userid, name, crypto.dec('normal',email) email, pwd, ismailing, state ";
            sql += " from TZ_MEMBER                          ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and crypto.dec('normal',email)   = " + StringManager.makeSQL(v_email);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {

                String v_isMailing = "1"; // email로만 전송

                String v_toCono = ls.getString("userid");
                // String v_pwd = ls.getString("pwd"); //임시부여받은 패스워드(암호화설정됨)
                String v_pwd = box.get("tmp_pwd"); // 임시부여받은 패스워드(암호화미설정됨)
                String v_toEmail = ls.getString("email");
                String v_state = ls.getString("state");
                String v_name = ls.getString("name");

                box.put("v_name", v_name);
                box.put("v_pwd", v_pwd);
                box.put("v_toCono", v_toCono);
                box.put("v_toEmail", v_toEmail);

                if (v_toEmail.equals("")) {
                    is_Ok = -2; // 메일주소 없음
                } else if (!v_state.equals("Y")) {
                    is_Ok = -3; // 탈퇴자
                } else {

                    mset.setSender(fmail); // 메일보내는 사람 세팅

                    fmail.setVariable("userid", v_userid);
                    fmail.setVariable("name", ls.getString("name"));
                    fmail.setVariable("pwd", v_pwd);

                    v_contents = v_name + "(" + v_userid + ")" + " 님의  임시 비밀번호는 (" + v_pwd + ") 입니다. 로그인후 후 비밀번호 변경을 하시기 바랍니다.";
                    fmail.setVariable("content", v_contents);
                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);

                    if (isMailed)
                        is_Ok = 1; // 메일발송에 성공하면
                }
            } else {
                is_Ok = -1; // 사용자가 없음
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

    /**
     * 권한 셀렉트 박스
     * 
     * @param userid 유저아이디
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @return
     * @throws Exception
     */
    public static String getAuthSelect(String userid, String name, String selected, String event) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        String result = null;

        StringBuffer sql = new StringBuffer();

        int cnt = 0;

        result = " <SELECT name='" + name + "' id='" + name + "' " + event + "  class='va_m'> \n";

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT B.GADMIN GADMIN, B.GADMINNM GADMINNM \n");
            sql.append("   FROM TZ_MANAGER A, TZ_GADMIN B \n");
            sql.append("  WHERE A.GADMIN    = B.GADMIN \n");
            sql.append("    AND A.USERID    = " + StringManager.makeSQL(userid) + " \n");
            sql.append("    AND A.ISDELETED = 'N' \n");
            sql.append("    AND TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN A.FMON AND A.TMON \n");
            sql.append(" ORDER BY B.GADMIN ASC ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {

                result += " <option value=" + ls.getString("gadmin");
                if (selected.equals(ls.getString("gadmin"))) {
                    result += " selected ";
                }
                result += ">" + ls.getString("gadminnm") + "</option> \n";
                cnt++;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        result += "  <option value='ZZ'";

        if (selected.equals("ZZ") || selected.equals("")) {
            result += " selected ";
        }

        result += ">학습자</option>";
        result += "  </SELECT> \n";

        if (cnt == 0)
            result = "";
        return result;
    }

    /**
     * 권한 셀렉트 박스
     * 
     * @param name 셀렉트박스명
     * @return
     * @throws Exception
     */
    public static String getAuthSelect(String name) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        String result = null;

        StringBuffer sql = new StringBuffer();

        int cnt = 0;

        result = " <SELECT name='" + name + "' id='" + name + "' "
                + " style=\"width:202px; height:24px;\" onkeypress=\"password_enter(event)\" tabindex=\"3\" class='va_m'> \n";
        result += " <option value=\"\">= 선택 =</option> ";

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT GADMIN, GADMINNM    \n");
            sql.append("   FROM TZ_GADMIN           \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {

                result += " <option value=" + ls.getString("gadmin");
                result += ">" + ls.getString("gadminnm") + "</option> \n";
                cnt++;
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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

        if (cnt == 0)
            result = "";
        return result;
    }

    /**
     * SSO 로그인 (세션 세팅)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : login ok 2 : login fail
     * @throws Exception
     */
    public int loginSSO(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        MemberData data = null;
        int is_Ok = 0;
        String v_userid = box.getString("p_userid");
        String v_userip = box.getString("p_userip");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = " select userid, resno, name, email, ";
            sql1 += "        comp, jikup,  cono,comptel, jikwi, replace(get_compnm(comp,2,2),'/','') companynm , ";
            // 수정일 : 05.11.17 수정자 : 이나연 _ decode, rownum 수정
            // sql1 += "        decode(NVL((select deptmresno from tz_comp where deptmresno=tz_member.userid and rownum=1),''),'','N','Y') isdeptmnger ";
            sql2 += "        case NVL((select deptmresno from ( select rownum rnum,  deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
            sql2 += "               When  ''    Then 'N' ";
            sql2 += "               Else 'N' ";
            sql2 += "        End as isdeptmnger, ";
            sql1 += "        comp, jikup,  cono,comptel, jikwi, replace(get_compnm(comp,2,2),'/','') companynm  ";
            // sql1 += "        decode(NVL((select deptmresno from tz_comp where deptmresno=tz_member.userid and rownum=1),''),'','N','Y') isdeptmnger ";
            sql2 += "        case NVL((select deptmresno from ( select rownum rnum, deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
            sql2 += "               When  ''    Then 'N' ";
            sql2 += "               Else 'N' ";
            sql2 += "        End as isdeptmnger, ";
            sql1 += " from TZ_MEMBER                     ";
            //sql1 += " where office_gbn = 'Y' and userid = " + StringManager.makeSQL(v_userid); office_Gbn 은 사용하지 않은 필드 같음. 활동중인 것으로 수정
            sql1 += " where state = 'Y' and userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComp(ls.getString("comp"));
                data.setComptel(ls.getString("comptel"));
                data.setJikup(ls.getString("jikup"));
                data.setJikwi(ls.getString("jikwi"));
                data.setCono(ls.getString("cono"));
                data.setCompanynm(ls.getString("companynm"));
                //data.setIsdeptmnger(ls.getString("isdeptmnger"));

                is_Ok = 1;
            }
            ls.close();

            // USERID 에 없을경우 CONO에서 체크

            if (is_Ok == 0) {
                sql2 = " select userid, resno, name, email, ";
                sql2 += "        comp, jikup,  cono, comptel, jikwi, replace(get_compnm(comp,2,2),'/',''),     ";
                sql2 += "        case NVL((select deptmresno from ( select rownum rnum, deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
                sql2 += "               When  ''    Then 'N' ";
                sql2 += "               Else 'N' ";
                sql2 += "        End as isdeptmnger, ";
                sql2 += "        comp, jikup,  cono, comptel, jikwi, replace(get_compnm(comp,2,2),'/',''),     ";
                sql2 += "        case select deptmresno from ( select rownum rnum,  deptmresno from tz_comp where deptmresno=tz_member.userid ) where rnum < 2),'') ";
                sql2 += "               When  ''    Then 'N' ";
                sql2 += "               Else 'N' ";
                sql2 += "        End as isdeptmnger ";
                sql2 += " from TZ_MEMBER                     ";
                //sql2 += " where office_gbn = 'Y' and lower(cono)   = lower(" + StringManager.makeSQL(v_userid) + " )";
                sql1 += " where state = 'Y' and lower(cono)   = lower(" + StringManager.makeSQL(v_userid) + " )";

                ls = connMgr.executeQuery(sql2);

                if (ls.next()) {
                    data = new MemberData();
                    data.setUserid(ls.getString("userid"));
                    data.setResno(ls.getString("resno"));
                    data.setName(ls.getString("name"));
                    data.setEmail(ls.getString("email"));
                    data.setComp(ls.getString("comp"));
                    data.setComptel(ls.getString("comptel"));
                    data.setJikup(ls.getString("jikup"));
                    data.setJikwi(ls.getString("jikwi"));
                    data.setCono(ls.getString("cono"));
                    data.setCompanynm(ls.getString("companynm"));
                    data.setIsdeptmnger(ls.getString("isdeptmnger"));

                    is_Ok = 1;

                } else {
                    is_Ok = -1; // 정보 없음
                }
                ls.close();

            }

            if (is_Ok == 1) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comp", data.getComp());
                box.setSession("comptel", data.getComptel());
                box.setSession("jikup", data.getJikup());
                box.setSession("jikwi", data.getJikwi());
                box.setSession("cono", data.getCono());
                box.setSession("usergubun", data.getUsergubun());
                box.setSession("companynm", data.getCompanynm());
                box.setSession("isdeptmnger", data.getIsdeptmnger());

                box.setSession("gadmin", "ZZ");

                updateLoginData(v_userid, v_userip, box.getSession("tem_grcode"));

                //this.loginMileage(connMgr, v_userid, "ELN_LOGIN"); 테이블 없음
                connMgr.commit();

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

    public boolean loginMileage(DBConnectionManager connMgr, String v_userid, String v_mileageType) throws Exception {
        ListSet ls = null;
        String sql = "";

        boolean success = false;
        try {
            // 마일리지 점수 정보
            if (v_mileageType.equals("ELN_LOGIN")) {
                sql = "select point from site_pointitem where code = 1 ";
            } else if (v_mileageType.equals("ELN_REG_REPLY")) {
                sql = "select point from tz_pointitem where code = 2 ";
            } else {
                sql = "select 0 from dual;";
            }

            ls = connMgr.executeQuery(sql);

            ls.close();

            // 마일리지 점수 정보
            if (v_mileageType.equals("ELN_LOGIN")) {
                // success = mileManager.updateMileage(connMgr.getConnection(),
                // v_userid, v_date , IMileageItemCode.ELN_LOGIN,v_point);
            } else if (v_mileageType.equals("ELN_REG_REPLY")) {
                // success = mileManager.updateMileage(connMgr.getConnection(),
                // v_userid, v_date , IMileageItemCode.ELN_REG_REPLY,v_point);
            }

            // System.out.println("마일리지 success = " + success );
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
        return success;
    }

    /**
     * 유저메일전송 (TZ_USERMAIL 에 등록)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertUserMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;

        int v_seq = 0;
        String v_fuserid = box.getString("p_fuserid");
        String v_fusername = box.getString("p_fusername");
        String v_fuseremail = box.getString("p_fuseremail");
        String v_tuserid = box.getString("p_tuserid");
        String v_tusername = box.getString("p_tusername");
        String v_tuseremail = box.getString("p_tuseremail");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            // ---------------------- 최대값 구한다. ----------------------------
            sql = "select NVL(max(seq),0) from TZ_USERMAIL ";
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();

            // -------------------------------------------------------------------------
            sql1 = " insert into TZ_USERMAIL(seq, title, fuserid, fusername, fuseremail, tuserid,  ";
            sql1 += "                         tusername, tuseremail,contents, retmailing, ldate)     ";
            sql1 += "                values (?, ?, ?, ?, ?, ?, ?, ?, ?,'N', to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, v_fuserid);
            pstmt1.setString(index++, v_fusername);
            pstmt1.setString(index++, v_fuseremail);
            pstmt1.setString(index++, v_tuserid);
            pstmt1.setString(index++, v_tusername);
            pstmt1.setString(index++, v_tuseremail);
            // pstmt1.setString(index++, v_contents);
            pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            isOk1 = pstmt1.executeUpdate();
            /* 05.11.15 이나연 */
            // sql2 = "select contents from TZ_USERMAIL where seq = " + v_seq;
            // connMgr.setOracleCLOB(sql2, v_contents); // (기타 서버 경우)

            if (isOk1 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
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

    // 북마크 했을때 처리 방안
    public static String getCompanyUrl(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String v_hostname = box.getString("p_hostname");
        ListSet ls = null;
        String sql = "";
        String v_grcode = "";

        try {
            connMgr = new DBConnectionManager();
            sql = "select grcode from tz_grcode where domain like   '%" + v_hostname + "%'";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_grcode = ls.getString("grcode");
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
        return v_grcode;
    }

    public int loginForSSO(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // String sql1 = "";
        StringBuffer sql = new StringBuffer();
        MemberData data = null;
        int is_Ok = 0;
        String v_userid = box.getString("p_userid");
        String v_userip = box.getString("p_userip");
        String grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* LoginBean() loginForSSO (교유그룹기관 사용자 SSO 로그인) */  \n");
            sql.append("SELECT  USERID      \n");
            sql.append("    ,   RESNO       \n");
            sql.append("    ,   NAME        \n");
            sql.append("    ,   CRYPTO.DEC('normal', EMAIL) AS EMAIL    \n");
            sql.append("    ,   CRYPTO.DEC('normal', COMPTEL) AS COMPTEL    \n");
            sql.append("  FROM  TZ_MEMBER   \n");
            sql.append(" WHERE  USERID = '").append(v_userid).append("' \n");
            sql.append("   AND  GRCODE = '").append(grcode).append("'   \n");
            sql.append("   AND  STATE = 'Y' \n");

            ls = connMgr.executeQuery(sql.toString());

            // 로그인 결과
            // 1 : 성공
            // -1 : 사용자 없음
            // -2 : 퇴직자
            // -3 : 비밀번호 오류

            if (ls.next()) {
                data = new MemberData();
                data.setUserid(ls.getString("userid"));
                data.setResno(ls.getString("resno"));
                data.setName(ls.getString("name"));
                data.setEmail(ls.getString("email"));
                data.setComptel(ls.getString("comptel"));

                is_Ok = 1; // 성공
            } else {
                is_Ok = -1; // 사용자정보 없음
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (is_Ok == 1) {
                box.setSession("userid", data.getUserid());
                box.setSession("resno", data.getResno());
                box.setSession("name", data.getName());
                box.setSession("email", data.getEmail());
                box.setSession("comptel", data.getComptel());
                box.setSession("userip", v_userip);

                box.setSession("gadmin", "ZZ");

                // 접속통계 누적 부분
                CountBean bean1 = new CountBean();
                bean1.writeLog(connMgr, box); // 로그 작성
                
                // LOGIN DATA UPDATE
                updateLoginData(connMgr, v_userid, v_userip, box.getSession("tem_grcode"));
                createMemberLoginLog(connMgr, box, "ASP_SSO");
            } else if (is_Ok == -3) {
                // 오류 회수 추가
                // is_Ok2 = updateLoginFail(connMgr, v_userid);
            }

            connMgr.commit();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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

        return is_Ok;
    }

    public int exists_ID(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = box.getString("userId");

        try {
            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                is_Ok = 0;
            } else {
                is_Ok = 1;
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

    public int exists_EMAIL(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_useremail = box.getString("userEmail");

        try {
            connMgr = new DBConnectionManager();

            // 암호화
            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            v_useremail = encryptUtil.encrypt(v_useremail);

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where email = " + StringManager.makeSQL(v_useremail);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                is_Ok = 0;
            } else {
                is_Ok = 1;
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

    // ASP 가입 저장
    public int insertUser_Compony(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        int index = 1;

        String v_handphone = box.getString("p_mobil0") + "-" + box.getString("p_mobil1") + "-" + box.getString("p_mobil2");
        String v_resno = box.getStringDefault("p_resno", box.getSession("p_resno"));
        String v_userid = box.getString("p_id");
        String v_pwd = box.getString("p_pw1");
        String v_name = box.getStringDefault("p_kor_name", box.getSession("name"));
        String v_email = box.getString("p_email1") + "@" + box.getString("p_email2");
        String v_hometel = box.getString("p_tel0") + "-" + box.getString("p_tel1") + "-" + box.getString("p_tel2");
        String v_ismailing = box.getStringDefault("p_agreed", "N");
        String v_islettering = "N";
        String v_isopening = "N";
        String v_registgubun = box.getStringDefault("p_registgubun", box.getSession("grtype"));
        int v_validation = 0; // 실명인증 관련 홈페이지 가입은 == 1
        String p_organization = box.getString("p_organization");
        String p_deptnm = box.getString("p_deptnm");

        String v_memberyear = box.getString("p_birth_year");
        String v_membermonth = box.getString("p_birth_month");
        String v_memberday = box.getString("p_birth_day");
        String v_sex = box.getString("p_sex");

        // 사용 안함

        String dupinfo = box.getSession("dupinfo");
        String conninfo = box.getSession("conninfo");

        String v_eng_name = box.getString("p_eng_name");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_home_addr1 = box.getString("p_home_addr1");
        String v_home_addr2 = box.getString("p_home_addr2");
        String v_comp_post1 = box.getString("p_comp_post1");
        String v_comp_post2 = box.getString("p_comp_post2");
        String v_comp_addr1 = box.getString("p_comp_addr1");
        String v_comp_addr2 = box.getString("p_comp_addr2");
        String v_comptext = box.getString("p_comptext");
        String v_jikup = box.getString("p_jikup");
        String v_comptel = box.getString("p_comptel");
        String v_degree = box.getString("p_degree");
        String grcode = box.getSession("tem_grcode");
        String grPrefix = "", mobileUserid = "";

        try {
            connMgr = new DBConnectionManager();

            sql.setLength(0);
            sql.append("SELECT  GR_PREFIX   \n");
            sql.append("  FROM  TZ_GRCODE   \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("'\n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                grPrefix = ls.getString("GR_PREFIX");
            }

            ls.close();
            ls = null;

            if (!grPrefix.equals("")) {
                if (v_userid.indexOf(grPrefix) == 0) {
                    mobileUserid = v_userid;
                } else {
                    mobileUserid = grPrefix + v_userid;
                }
            }

            // 개인정보 암호화
            v_pwd = HashCipher.createHash(v_pwd);

            connMgr.setAutoCommit(false);
            sql.setLength(0);
            sql.append("INSERT  INTO    TZ_MEMBER ( \n");
            sql.append("        RESNO           \n");
            sql.append("    ,   USERID          \n");
            sql.append("    ,   PWD             \n");
            sql.append("    ,   NAME            \n");
            sql.append("    ,   ENG_NAME        \n");
            sql.append("    ,   POST1           \n");
            sql.append("    ,   POST2           \n");
            sql.append("    ,   ADDR            \n");
            sql.append("    ,   ADDR2           \n");
            sql.append("    ,   COMP_POST1      \n");
            sql.append("    ,   COMP_POST2      \n");
            sql.append("    ,   COMP_ADDR1      \n");
            sql.append("    ,   COMP_ADDR2      \n");
            sql.append("    ,   EMAIL           \n");
            sql.append("    ,   COMPTEXT        \n");
            sql.append("    ,   JIKUP           \n");
            sql.append("    ,   DEGREE          \n");
            sql.append("    ,   HANDPHONE       \n");
            sql.append("    ,   HOMETEL         \n");
            sql.append("    ,   COMPTEL         \n");
            sql.append("    ,   ISMAILING       \n");
            sql.append("    ,   ISLETTERING     \n");
            sql.append("    ,   ISOPENING       \n");
            sql.append("    ,   INDATE          \n");
            sql.append("    ,   LDATE           \n");
            sql.append("    ,   REGISTGUBUN     \n");
            sql.append("    ,   STATE           \n");
            sql.append("    ,   VALIDATION      \n");
            sql.append("    ,   GRCODE          \n");
            sql.append("    ,   MEMBERGUBUN     \n");
            sql.append("    ,   WORK_PLCNM      \n");
            sql.append("    ,   DEPTNAM         \n");
            sql.append("    ,   DUPINFO         \n");
            sql.append("    ,   CONNINFO        \n");
            sql.append("    ,   MEMBERYEAR      \n");
            sql.append("    ,   MEMBERMONTH     \n");
            sql.append("    ,   MEMBERDAY       \n");
            sql.append("    ,   SEX             \n");
            sql.append("    ,   MOBILE_USERID   \n");
            sql.append("    ,   PASSCHANGEDT    \n");
            sql.append(" ) VALUES ( \n");
            sql.append("        ?                                       /* RESNO        */  \n");
            sql.append("    ,   ?                                       /* USERID       */  \n");
            sql.append("    ,   ?                                       /* PWD          */  \n");
            sql.append("    ,   ?                                       /* NAME         */  \n");
            sql.append("    ,   ?                                       /* ENG_NAME     */  \n");
            sql.append("    ,   ?                                       /* POST1        */  \n");
            sql.append("    ,   ?                                       /* POST2        */  \n");
            sql.append("    ,   ?                                       /* ADDR         */  \n");
            sql.append("    ,   ?                                       /* ADDR2        */  \n");
            sql.append("    ,   ?                                       /* COMP_POST1   */  \n");
            sql.append("    ,   ?                                       /* COMP_POST2   */  \n");
            sql.append("    ,   ?                                       /* COMP_ADDR1   */  \n");
            sql.append("    ,   ?                                       /* COMP_ADDR2   */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* EMAIL        */  \n");
            sql.append("    ,   ?                                       /* COMPTEXT     */  \n");
            sql.append("    ,   ?                                       /* JIKUP        */  \n");
            sql.append("    ,   ?                                       /* DEGREE       */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* HANDPHONE    */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* HOMETEL      */  \n");
            sql.append("    ,   ?                                       /* COMPTEL      */  \n");
            sql.append("    ,   ?                                       /* ISMAILING    */  \n");
            sql.append("    ,   ?                                       /* ISLETTERING  */  \n");
            sql.append("    ,   ?                                       /* ISOPENING    */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* INDATE       */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* LDATE        */  \n");
            sql.append("    ,   ?                                       /* REGISTGUBUN  */  \n");
            sql.append("    ,   'Y'                                     /* STATE        */  \n");
            sql.append("    ,   ?                                       /* VALIDATION   */  \n");
            sql.append("    ,   ?                                       /* GRCODE       */  \n");
            sql.append("    ,   ?                                       /* MEMBERGUBUN  */  \n");
            sql.append("    ,   ?                                       /* WORK_PLCNM   */  \n");
            sql.append("    ,   ?                                       /* DEPTNAM      */  \n");
            sql.append("    ,   ?                                       /* DUPINFO      */  \n");
            sql.append("    ,   ?                                       /* CONNINFO     */  \n");
            sql.append("    ,   ?                                       /* MEMBERYEAR   */  \n");
            sql.append("    ,   ?                                       /* MEMBERMONTH  */  \n");
            sql.append("    ,   ?                                       /* MEMBERDAY    */  \n");
            sql.append("    ,   ?                                       /* SEX          */  \n");
            sql.append("    ,   ?                                       /* MOBILE_USERID*/  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* PASSCHANGEDT */  \n");
            sql.append(")   \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, v_resno);
            pstmt.setString(index++, v_userid);
            pstmt.setString(index++, v_pwd);
            pstmt.setString(index++, v_name);
            pstmt.setString(index++, v_eng_name);
            pstmt.setString(index++, v_post1);
            pstmt.setString(index++, v_post2);
            pstmt.setString(index++, v_home_addr1);
            pstmt.setString(index++, v_home_addr2);
            pstmt.setString(index++, v_comp_post1);
            pstmt.setString(index++, v_comp_post2);
            pstmt.setString(index++, v_comp_addr1);
            pstmt.setString(index++, v_comp_addr2);
            pstmt.setString(index++, v_email);
            pstmt.setString(index++, v_comptext);
            pstmt.setString(index++, v_jikup);
            pstmt.setString(index++, v_degree);
            pstmt.setString(index++, v_handphone);
            pstmt.setString(index++, v_hometel);
            pstmt.setString(index++, v_comptel);
            pstmt.setString(index++, v_ismailing);
            pstmt.setString(index++, v_islettering);
            pstmt.setString(index++, v_isopening);
            pstmt.setString(index++, v_registgubun);
            pstmt.setInt(index++, v_validation);
            pstmt.setString(index++, grcode);
            pstmt.setString(index++, "C");
            pstmt.setString(index++, p_organization);
            pstmt.setString(index++, p_deptnm);
            pstmt.setString(index++, dupinfo);
            pstmt.setString(index++, conninfo);
            pstmt.setString(index++, v_memberyear);
            pstmt.setString(index++, v_membermonth);
            pstmt.setString(index++, v_memberday);
            pstmt.setString(index++, v_sex);
            pstmt.setString(index++, mobileUserid);

            isOk = pstmt.executeUpdate();

            if (isOk != 0) {
                box.put("isOk", String.valueOf(isOk));
                box.setSession("userid", v_userid);
                box.setSession("name", v_name);
            }

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
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

    public String getASP_Userid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";

        String v_name = box.getString("p_name");
        String v_resno1 = box.getString("p_resno1");
        String v_resno2 = box.getString("p_resno2");
        String v_resno = v_resno1 + v_resno2;

        String result = "";

        try {

            connMgr = new DBConnectionManager();

            // 아이디로 비교
            sql1 = " select userid      ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " where resno  = " + StringManager.makeSQL(v_resno);
            sql1 += "   and name   = " + StringManager.makeSQL(v_name);
            sql1 += " and   grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            // sql1 += "    and state = 'Y' ";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = ls1.getString("userid");
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    private String getRandomText(int textSize, int rmSeed) {
        /*
         * 입력받은 수 만큼 렌덤 문자를 만들어 반환한다. 난수를 발생시켜 이에 대응하는 알파뱃 문자를 생성한다. 생성된 알파뱃을 연결해 하나의 랜덤 문자를 만든다.
         */

        String rmText = "";

        Random random = new Random(System.currentTimeMillis());

        int rmNum = 0;

        char ch = 'a';

        for (int i = 0; i < textSize; i++) {

            random.setSeed(System.currentTimeMillis() * rmSeed * i + rmSeed + i);

            rmNum = random.nextInt(25);

            ch += rmNum;

            rmText = rmText + ch;

            ch = 'a';

        }

        return rmText;
    }

    public String selectTempPassword(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        // int is_Ok = 0;

        String v_userid = "";
        String v_name = "";
        String v_email = "";
        String v_resno1 = "";
        String v_resno2 = "";
        String v_resno = "";
        String v_dupinfo = "";
        String flag = "JID";
        String[] info = null;
        if (box.getSession("tem_grcode").equals("N000001")) {
            v_userid = box.getString("p_userid_2");
            v_name = box.getString("p_name_2");
            v_email = box.getString("p_email_2");

            if (v_email.equals("")) {
                Interop interop = new Interop();
                info = interop.Interop(v_resno, flag).split(";");
                v_dupinfo = info[0];
            }

        } else {
            v_userid = box.getString("p_id10");
            v_name = box.getString("p_name10");
            v_resno1 = box.getString("p_resno10");
            v_resno2 = box.getString("p_resno11");
        }

        String return_Tmp = "";
        String return_Tmp_enc = "";

        try {

            // 개인정보 암호화
            // SeedCipher seed = new SeedCipher();
            // String v_resno2_enc = Base64.encode(seed.encrypt(v_resno2,
            // seed.key.getBytes(), "UTF-8")).replace("\n","");
            // if (!box.getSession("tem_grcode").equals("N000001")) {
            // v_resno2_enc = v_resno2;
            // }

            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            String v_resno2_enc = encryptUtil.encrypt(v_resno2);
            if (!box.getSession("tem_grcode").equals("N000001")) {
                v_resno2_enc = v_resno2;
            }
            // ===========================

            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and   name   = " + StringManager.makeSQL(v_name);
            // sql += " and   resno = "+
            // StringManager.makeSQL(v_resno1+v_resno2);
            if (box.getSession("tem_grcode").equals("N000001")) {
                if (v_email.equals(""))
                    // sql += " and   crypto.dec('normal',email) = "+
                    // StringManager.makeSQL(v_email);
                    sql += " and   dupinfo = " + StringManager.makeSQL(v_dupinfo);
                // sql += " and   resno1 = "+ StringManager.makeSQL(v_resno1);
                // sql += " and   resno2 = "+
                // StringManager.makeSQL(v_resno2_enc);
            } else {
                sql += " and   substr(resno, 1,6) = " + StringManager.makeSQL(v_resno1);
                sql += " and   substr(resno, 7,7) = " + StringManager.makeSQL(v_resno2_enc);
            }
            sql += " and   grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            sql += " and   state = 'Y' ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // 패스워드 재생성
                return_Tmp = getRandomText(8, 8);
                // ===========================
                // 패스워드 암호화
                return_Tmp_enc = HashCipher.createHash(return_Tmp);

                if (!box.getSession("tem_grcode").equals("N000001")) {
                    return_Tmp_enc = return_Tmp;
                }
                // ===========================

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);
                if (box.getSession("tem_grcode").equals("N000001")) {
                    sql = "update tz_member set pwd=? where userid=? and name=? and grcode=?";

                    pstmt = connMgr.prepareStatement(sql);

                    pstmt.setString(1, return_Tmp_enc);
                    pstmt.setString(2, v_userid);
                    pstmt.setString(3, v_name);
                    // pstmt.setString(4, v_dupinfo);
                    pstmt.setString(4, box.getSession("tem_grcode"));
                } else {
                    sql = "update tz_member set pwd=? where userid=? and name=? and substr(resno, 1,6)=? and substr(resno, 7,7)=? and grcode=?";

                    pstmt = connMgr.prepareStatement(sql);

                    pstmt.setString(1, return_Tmp_enc);
                    pstmt.setString(2, v_userid);
                    pstmt.setString(3, v_name);
                    pstmt.setString(4, v_resno1);
                    pstmt.setString(5, v_resno2_enc);
                    pstmt.setString(6, box.getSession("tem_grcode"));
                }

                // is_Ok = 0;
                pstmt.executeUpdate();
                connMgr.commit();
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

        return return_Tmp;
    }

    public String selectTempPasswordMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        // int is_Ok = 0;

        String v_userid = "";
        String v_name = "";
        String v_email = "";

        v_userid = box.getString("p_userid_2");
        v_name = box.getString("p_name_2");
        v_email = box.getString("p_email_2");

        String return_Tmp = "";
        String return_Tmp_enc = "";

        try {

            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and   name   = " + StringManager.makeSQL(v_name);
            sql += " and   crypto.dec('normal',email) = " + StringManager.makeSQL(v_email);
            sql += " and   state = 'Y' ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // 패스워드 재생성
                return_Tmp = getRandomText(8, 8);
                // ===========================
                // 패스워드 암호화
                return_Tmp_enc = HashCipher.createHash(return_Tmp);

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                sql = "update tz_member set pwd=? where userid=? and name=? and grcode=?";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, return_Tmp_enc);
                pstmt.setString(2, v_userid);
                pstmt.setString(3, v_name);
                pstmt.setString(4, box.getSession("tem_grcode"));

                // is_Ok = 0;
                pstmt.executeUpdate();
                connMgr.commit();
            } else {
                return_Tmp = "0";
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

        return return_Tmp;
    }

    public String selectTempPasswordHandphone(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";

        String v_userid = "";
        String v_name = "";

        v_userid = box.getString("p_userid_2");
        v_name = box.getString("p_name_2");

        String return_Tmp = "";
        String return_Tmp_enc = "";

        try {

            connMgr = new DBConnectionManager();

            // 패스워드 재생성
            return_Tmp = getRandomText(8, 8);
            // ===========================
            // 패스워드 암호화
            return_Tmp_enc = HashCipher.createHash(return_Tmp);

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "update tz_member set pwd=? where userid=? and name=? and grcode=?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, return_Tmp_enc);
            pstmt.setString(2, v_userid);
            pstmt.setString(3, v_name);
            pstmt.setString(4, box.getSession("tem_grcode"));

            // is_Ok = 0;
            pstmt.executeUpdate();
            connMgr.commit();

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

        return return_Tmp;
    }

    public String selectTempPassword1(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        // int is_Ok = 0;

        String v_userid = "";
        String v_name = "";
        // String v_email = "";
        String v_resno1 = "";
        String v_resno2 = "";
        String v_resno = "";
        String v_dupinfo = "";
        String flag = "JID";
        String[] info = null;

        v_userid = box.getString("p_userid_2");
        v_name = box.getString("p_name_2");

        v_resno1 = box.getString("p_resno1_2");
        v_resno2 = box.getString("p_resno2_2");
        v_resno = v_resno1 + v_resno2;

        // v_resno2 = "1111111";

        Interop interop = new Interop();
        info = interop.Interop(v_resno, flag).split(";");
        // System.out.println(info);
        v_dupinfo = info[0];

        String return_Tmp = "";
        String return_Tmp_enc = "";

        try {

            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and   name   = " + StringManager.makeSQL(v_name);

            sql += " and   dupinfo = " + StringManager.makeSQL(v_dupinfo);

            sql += " and   grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            sql += " and   state = 'Y' ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                // 패스워드 재생성
                return_Tmp = getRandomText(8, 8);
                // ===========================
                // 패스워드 암호화
                return_Tmp_enc = HashCipher.createHash(return_Tmp);

                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                sql = "update tz_member set pwd=? where userid=? and name=? and dupinfo=?  and grcode=?";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, return_Tmp_enc);
                pstmt.setString(2, v_userid);
                pstmt.setString(3, v_name);
                pstmt.setString(4, v_dupinfo);
                pstmt.setString(5, box.getSession("tem_grcode"));

                // is_Ok = 0;
                pstmt.executeUpdate();
                connMgr.commit();
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

        return return_Tmp;
    }

    public String getASP_Login(RequestBox box, HttpServletRequest request) throws Exception {
        //System.out.println("LoginBean -- getASP_Login");
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String v_id = box.getString("p_id");
        String v_pw = box.getString("p_pw");
        String v_name = box.getString("p_name");
        String v_servernm = request.getServerName();
        String result = "";
        String grcode = box.getSession("tem_grcode");
        String sso = box.getSession("tem_sso");
        String grPrefix = "", mobileUserid = "";

        StringBuilder sql = new StringBuilder();
        int index = 1;

        if (!box.getString("p_aspgubun").equals("edu1") && !box.getString("p_aspgubun").equals("kocsc") && !box.getString("p_aspgubun").equals("hns")
                && !box.getString("p_aspgubun").equals("N000083")) {
            v_pw = HashCipher.createHash(v_pw);
        }

        try {
            connMgr = new DBConnectionManager();
            //System.out.println(v_servernm);
            sql.append("SELECT  A.USERID    \n");
            sql.append("    ,   A.NAME      \n");
            sql.append("    ,   CRYPTO.DEC('normal', A.EMAIL) AS EMAIL  \n");
            sql.append("    ,   NVL(B.GADMIN,'ZZ') AS GADMIN    \n");
            sql.append("    ,   TO_CHAR((ADD_MONTHS(TO_DATE(A.PASSCHANGEDT, 'YYYYMMDDHH24MISS'), 3)), 'YYYYMMDDHH24MISS') AS PASSCHANGEDT     \n");
            sql.append("    ,   NVL(A.ISAGRE_CHK, 'N') AGREECHK  \n");
            sql.append("  FROM  TZ_MEMBER A         \n");
            sql.append("  LEFT  JOIN TZ_GRCODEMAN B \n");
            sql.append("    ON  A.USERID = B.USERID \n");
            sql.append("   AND  A.GRCODE = B.GRCODE \n");
            sql.append(" WHERE  A.USERID  = '").append(v_id).append("'  \n");
            sql.append("   AND  A.STATE = 'Y'   \n");

            if (box.getSession("tem_grcode").equals("N000022")) { // 문화체육관광부
                if (!v_name.equals("")) {
                    sql.append("   AND  A.NAME = '").append(v_name).append("' \n");
                } else {
                    //sql.append("   AND  A.PWD = '").append(v_pw).append("' \n");
                }
                sql.append("   AND A.GRCODE = '").append(box.getSession("tem_grcode")).append("'");
            } else if (box.getSession("tem_grcode").equals("N000032") || box.getSession("tem_grcode").equals("N000034") || box.getSession("tem_grcode").equals("N000108")) {
            } else {
                sql.append("   AND A.PWD = '").append(v_pw).append("'");
                sql.append("   AND A.GRCODE = '").append(box.getSession("tem_grcode")).append("'");

            }

            ls1 = connMgr.executeQuery(sql.toString());
            if (ls1.next()) {
                result = ls1.getString("userid");
                box.setSession("userid", ls1.getString("userid"));
                box.setSession("name", ls1.getString("name"));
                box.setSession("gadmin", ls1.getString("gadmin"));
                box.setSession("email", ls1.getString("email"));
                box.setSession("passchangedt", ls1.getString("passchangedt"));
                box.setSession("agreechk", ls1.getString("agreechk"));

                // 강사권한 체크 (2010.10.15)
                // =====================================================================================
                if (box.getSession("gadmin").equals("ZZ")) {

                    sql.setLength(0);
                    sql.append("SELECT  B.GADMIN AS GADMIN      \n");
                    sql.append("    ,   B.GADMINNM AS GADMINNM  \n");
                    sql.append("  FROM  TZ_MANAGER A    \n");
                    sql.append("    ,   TZ_GADMIN B     \n");
                    sql.append(" WHERE  A.GADMIN = B.GADMIN \n");
                    sql.append("   AND  A.USERID = '").append(v_id).append("'   \n");
                    sql.append("   AND  A.ISDELETED = 'N'   \n");
                    sql.append("   AND  TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN A.FMON AND A.TMON    \n");
                    sql.append(" ORDER BY B.GADMIN ASC  \n");

                    ls2 = connMgr.executeQuery(sql.toString());

                    // 접속통계 누적 부분
                    CountBean bean1 = new CountBean();
                    bean1.writeLog(connMgr, box); // 로그 작성

                    while (ls2.next()) {
                        box.setSession("gadmin", ls2.getString("gadmin"));
                    }
                }
                // =====================================================================================

            } else {
                if (((v_servernm.equals("mcst.kocca.or.kr") || v_servernm.equals("mcst.edukocca.or.kr")) && sso.equals("Y")) || (v_servernm.equals("narasarang.edukocca.or.kr") && sso.equals("Y"))  || v_servernm.equals("kbselife.edukocca.or.kr") || v_servernm.equals("k-ent.edukocca.or.kr") &&  !v_name.equals("")) {
                    String compnm = box.getString("p_compnm");
                    String deptnm = box.getString("p_deptnm");
                    String levelnm = box.getString("p_levelnm");
                    String email = box.getString("p_email");
                    String tel = box.getString("p_tel");
                    String mobile = box.getString("p_mobile");
                    String sex = box.getString("p_sex");

                    connMgr = new DBConnectionManager();
                    sql.setLength(0);
                    sql.append("SELECT  GR_PREFIX   \n");
                    sql.append("  FROM  TZ_GRCODE   \n");
                    sql.append(" WHERE  GRCODE = '").append(grcode).append("'\n");

                    ls = connMgr.executeQuery(sql.toString());
                    if (ls.next()) {
                        grPrefix = ls.getString("GR_PREFIX");
                    }

                    ls.close();
                    ls = null;
                    if (!grPrefix.equals("")) {
                        if (v_id.indexOf(grPrefix) == 0) {
                            mobileUserid = v_id;
                        } else {
                            mobileUserid = grPrefix + v_id;
                        }
                    }

                    connMgr.setAutoCommit(false);

                    if(v_servernm.equals("narasarang.edukocca.or.kr")){
                        sql.setLength(0);
                        sql.append("INSERT  INTO TZ_MEMBER (    \n");
                        sql.append("        USERID          \n");
                        sql.append("    ,   NAME          \n");
                        sql.append("    ,   PWD          \n");
                        sql.append("    ,   GRCODE          \n");
                        sql.append("    ,   INDATE          \n");
                        sql.append("    ,   STATE           \n");
                        sql.append("    ,   MOBILE_USERID   \n");
                        sql.append(" ) VALUES (    \n");
                        sql.append("        ?                                       /* USERID         */  \n");
                        sql.append("    ,   ?                                       /* NAME       */  \n");
                        sql.append("    ,   ?                                       /* PWD       */  \n");
                        sql.append("    ,   ?                                       /* GRCODE       */  \n");
                        sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* INDATE       */  \n");
                        sql.append("    ,   'Y'                                     /* STATE        */  \n");
                        sql.append("    ,   ?                                       /* MOBILE_USERID*/  \n");
                        sql.append("    ) \n");
                        
                        pstmt = connMgr.prepareStatement(sql.toString());
                        
                        pstmt.setString(index++, v_id);
                        pstmt.setString(index++, v_name);
                        pstmt.setString(index++, v_pw);
                        pstmt.setString(index++, grcode);
                        pstmt.setString(index++, mobileUserid);
                        
                    } else {
                        sql.setLength(0);
                        sql.append("INSERT  INTO TZ_MEMBER (    \n");
                        sql.append("        RESNO           \n");
                        sql.append("    ,   USERID          \n");
                        sql.append("    ,   PWD             \n");
                        sql.append("    ,   NAME            \n");
                        sql.append("    ,   ENG_NAME        \n");
                        sql.append("    ,   POST1           \n");
                        sql.append("    ,   POST2           \n");
                        sql.append("    ,   ADDR            \n");
                        sql.append("    ,   ADDR2           \n");
                        sql.append("    ,   COMP_POST1      \n");
                        sql.append("    ,   COMP_POST2      \n");
                        sql.append("    ,   COMP_ADDR1      \n");
                        sql.append("    ,   COMP_ADDR2      \n");
                        sql.append("    ,   EMAIL           \n");
                        sql.append("    ,   COMPTEXT        \n");
                        sql.append("    ,   JIKUP           \n");
                        sql.append("    ,   DEGREE          \n");
                        sql.append("    ,   HANDPHONE       \n");
                        sql.append("    ,   HOMETEL         \n");
                        sql.append("    ,   COMPTEL         \n");
                        sql.append("    ,   ISMAILING       \n");
                        sql.append("    ,   ISLETTERING     \n");
                        sql.append("    ,   ISOPENING       \n");
                        sql.append("    ,   INDATE          \n");
                        sql.append("    ,   LDATE           \n");
                        sql.append("    ,   REGISTGUBUN     \n");
                        sql.append("    ,   STATE           \n");
                        sql.append("    ,   VALIDATION      \n");
                        sql.append("    ,   GRCODE          \n");
                        sql.append("    ,   MEMBERGUBUN     \n");
                        sql.append("    ,   WORK_PLCNM      \n");
                        sql.append("    ,   DEPTNAM         \n");
                        sql.append("    ,   SEX         \n");
                        sql.append("    ,   MOBILE_USERID   \n");
                        sql.append(" ) VALUES (    \n");
                        sql.append("        ?                                       /* RESNO        */  \n");
                        sql.append("    ,   ?                                       /* USERID       */  \n");
                        sql.append("    ,   ?                                       /* PWD          */  \n");
                        sql.append("    ,   ?                                       /* NAME         */  \n");
                        sql.append("    ,   ?                                       /* ENG_NAME     */  \n");
                        sql.append("    ,   ?                                       /* POST1        */  \n");
                        sql.append("    ,   ?                                       /* POST2        */  \n");
                        sql.append("    ,   ?                                       /* ADDR         */  \n");
                        sql.append("    ,   ?                                       /* ADDR2        */  \n");
                        sql.append("    ,   ?                                       /* COMP_POST1   */  \n");
                        sql.append("    ,   ?                                       /* COMP_POST2   */  \n");
                        sql.append("    ,   ?                                       /* COMP_ADDR1   */  \n");
                        sql.append("    ,   ?                                       /* COMP_ADDR2   */  \n");
                        sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* EMAIL        */  \n");
                        sql.append("    ,   ?                                       /* COMPTEXT     */  \n");
                        sql.append("    ,   ?                                       /* JIKUP        */  \n");
                        sql.append("    ,   ?                                       /* DEGREE       */  \n");
                        sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* HANDPHONE    */  \n");
                        sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* HOMETEL      */  \n");
                        sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* COMPTEL      */  \n");
                        sql.append("    ,   ?                                       /* ISMAILING    */  \n");
                        sql.append("    ,   ?                                       /* ISLETTERING  */  \n");
                        sql.append("    ,   ?                                       /* ISOPENING    */  \n");
                        sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* INDATE       */  \n");
                        sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* LDATE        */  \n");
                        sql.append("    ,   ?                                       /* REGISTGUBUN  */  \n");
                        sql.append("    ,   'Y'                                     /* STATE        */  \n");
                        sql.append("    ,   ?                                       /* VALIDATION   */  \n");
                        sql.append("    ,   ?                                       /* GRCODE       */  \n");
                        sql.append("    ,   ?                                       /* MEMBERGUBUN  */  \n");
                        sql.append("    ,   ?                                       /* WORK_PLCNM   */  \n");
                        sql.append("    ,   ?                                       /* DEPTNAM      */  \n");
                        sql.append("    ,   ?                                       /* SEX      */  \n");
                        sql.append("    ,   ?                                       /* MOBILE_USERID*/  \n");
                        sql.append("    ) \n");
    
                        pstmt = connMgr.prepareStatement(sql.toString());
    
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, v_id);
                        pstmt.setString(index++, v_pw);
                        pstmt.setString(index++, v_name);
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, "");
                        if (email.equals("")) {
                            email = "@";
                        }
                        pstmt.setString(index++, email);
                        pstmt.setString(index++, "");
                        pstmt.setString(index++, levelnm);
                        pstmt.setString(index++, "");
                        if (mobile.equals("") || mobile.equals("--")) {
                            mobile = "010-0000-0000";
                        }
                        pstmt.setString(index++, mobile);
                        if (tel.equals("") || tel.equals("--")) {
                            tel = "010-0000-0000";
                        }
                        pstmt.setString(index++, tel);
                        pstmt.setString(index++, tel);
                        pstmt.setString(index++, "Y");
                        pstmt.setString(index++, "N");
                        pstmt.setString(index++, "N");
                        pstmt.setString(index++, "");
                        pstmt.setInt(index++, 0);
                        pstmt.setString(index++, grcode);
                        pstmt.setString(index++, "C");
                        pstmt.setString(index++, compnm);
                        pstmt.setString(index++, deptnm);
                        pstmt.setString(index++, sex);
                        pstmt.setString(index++, mobileUserid);
                    }

                    int isOk = pstmt.executeUpdate();

                    if (isOk != 0) {
                        box.put("isOk", String.valueOf(isOk));
                        box.setSession("userid", v_id);
                        box.setSession("name", v_name);
                    }

                    if (isOk == 1) {
                        connMgr.commit();
                    } else {
                        connMgr.rollback();
                    }
                } /*else if(v_servernm.equals("narasarang.edukocca.or.kr") && !v_name.equals("")){
                    System.out.println("1111");
                }*/
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }

            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
        }
        return result;
    }

    public int getASP_Login_Userid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_id");
        String v_grcode = box.getSession("tem_grcode");
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql1 = "SELECT USERID \n";
            sql1 += "FROM TZ_MEMBER \n";
            sql1 += "WHERE USERID = " + StringManager.makeSQL(v_userid) + "\n";
            sql1 += "   AND GRCODE = " + StringManager.makeSQL(v_grcode) + "\n";
            sql1 += "   AND STATE = 'Y'";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                result = 1;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    public int getASP_Login_Pwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String v_userid = box.getString("p_id");
        String v_pwd = box.getString("p_pw");
        String v_grcode = box.getSession("tem_grcode");
        int result = 0;

        try {
            
            v_pwd = HashCipher.createHash(v_pwd);

            connMgr = new DBConnectionManager();

            sql1 = "SELECT PWD \n";
            sql1 += "FROM TZ_MEMBER \n";
            sql1 += "WHERE USERID = " + StringManager.makeSQL(v_userid) + "\n";
            sql1 += "   AND GRCODE = " + StringManager.makeSQL(v_grcode) + "\n";
            sql1 += "   AND STATE = 'Y'";

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                if (ls1.getString("pwd").equals(v_pwd)) {
                    result = 1;
                } 
            }
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return result;
    }

    public DataBox ASP_Edit_Login(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select  userid,name, crypto.dec('normal',email) email, crypto.dec('normal',hometel) hometel, crypto.dec('normal',handphone) handphone, post1,post2,addr, WORK_PLCNM,DEPTNAM, ismailing   from tz_member where userid='"
                    + v_userid + "'";
            sql += " and grcode='" + box.getSession("tem_grcode") + "'";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox = ls.getDataBox();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("NoticeSql = " + sql + "\r\n" + ex.getMessage());
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

    public int changePwd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid = box.getSession("userid");
        String v_usepw = box.getString("p_usepw");
        String v_pw1 = box.getString("p_pw1");
        String v_pw2 = box.getString("p_pw2");

        v_usepw = HashCipher.createHash(v_usepw);
        v_pw1 = HashCipher.createHash(v_pw1);
        v_pw2 = HashCipher.createHash(v_pw2);

        try {
            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and   pwd   = " + StringManager.makeSQL(v_usepw);
            sql += " and   grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            // sql += " and state = 'Y' ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);
                sql = "update tz_member set "
                		+ "pwd=?, "
                		+ "passchangedt=to_char(sysdate, 'yyyymmddhh24miss') "
                		+ "where userid=? and grcode=?";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1, v_pw1);
                pstmt.setString(2, v_userid);
                pstmt.setString(3, box.getSession("tem_grcode"));

                is_Ok = pstmt.executeUpdate();
                connMgr.commit();
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

        return is_Ok;
    }

    public int ASP_updateUser(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;

        String v_handphone = box.getString("p_mobil0") + "-" + box.getString("p_mobil1") + "-" + box.getString("p_mobil2");
        String v_userid = box.getSession("userid");
        String v_email = box.getString("p_email1") + "@" + box.getString("p_email2");
        String v_hometel = box.getString("p_tel0") + "-" + box.getString("p_tel1") + "-" + box.getString("p_tel2");
        String v_ismailing = box.getString("p_agreed");
        // String v_organization = box.getString("p_organization");
        String v_deptnm = box.getString("p_deptnm");

        // 모바일과학고 학생 직원 구분 및 cono 학번
        String v_officegbnnm = box.getStringDefault("p_officegbnnm", "");
        String v_cono = box.getStringDefault("p_cono", "");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // sql1 =
            // " update TZ_MEMBER set handphone=?, email=?,hometel=?, ismailing=?,WORK_PLCNM=?,DEPTNAM=?  where userid=? and grcode=?  \n";
            sql1 = "";
            sql1 += "UPDATE \n";
            sql1 += "   TZ_MEMBER \n";
            sql1 += "SET \n";
            sql1 += "   HANDPHONE       = crypto.enc('normal',?), \n";
            sql1 += "   EMAIL           = crypto.enc('normal',?), \n";
            sql1 += "   HOMETEL         = crypto.enc('normal',?), \n";
            sql1 += "   ISMAILING       = ?, \n";
            sql1 += "   DEPTNAM         = ?, \n";
            sql1 += "   OFFICE_GBNNM    = ?, \n";
            sql1 += "   CONO            = ? \n";
            sql1 += "WHERE \n";
            sql1 += "   USERID          = ? \n";
            sql1 += "   AND GRCODE      = ?";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_handphone);
            pstmt.setString(2, v_email);
            pstmt.setString(3, v_hometel);
            pstmt.setString(4, v_ismailing);
            pstmt.setString(5, v_deptnm);
            pstmt.setString(6, v_officegbnnm);
            pstmt.setString(7, v_cono);
            pstmt.setString(8, v_userid);
            pstmt.setString(9, box.getSession("tem_grcode"));

            isOk = pstmt.executeUpdate();

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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

    public int userIdentify(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int is_Ok = 0;

        String v_name = box.getString("p_name");
        String v_resno1 = box.getString("p_resno1");
        String v_resno2 = box.getString("p_resno2");

        try {
            connMgr = new DBConnectionManager();

            sql = " select *  ";
            sql += " from TZ_MEMBER ";
            sql += " where name = " + StringManager.makeSQL(v_name);
            sql += " and   resno   = " + StringManager.makeSQL(v_resno1 + v_resno2);
            sql += " and   grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                is_Ok = 1;
            } else {
                is_Ok = 0;
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

        return is_Ok;
    }

    // 이벤트 참여 여부 체크
    public DataBox eventynCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox eventynCheck = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select name, userid, eventchangedate, eventyn, nextchange, nextchangedate   ";
            sql += " from TZ_MEMBER ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                eventynCheck = ls.getDataBox();
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

        return eventynCheck;
    }

    public int nextChange(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;

        String v_userid = box.getSession("userid");
        //System.out.println(v_userid);
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql1 = " update TZ_MEMBER set "
            		+ "nextchange='Y', "
            		+ "nextchangedate = to_char(sysdate, 'YYYYMMDDHH24MISS'), "
            		+ "passchangedt = TO_CHAR((ADD_MONTHS(sysdate, -2)), 'YYYYMMDDHH24MISS')  "
            		+ "where userid=?  \n";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_userid);

            isOk = pstmt.executeUpdate();

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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

    public int eduServiceSulCheck(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int is_Ok = 0;
        // int isOk = 0;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql1 = " SELECT COUNT(*) FROM TZ_EDUSERVICESUL WHERE USERID = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                is_Ok = ls.getInt(1);
            }

        } catch (Exception ex) {
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
        return is_Ok;
    }
    
    
    /**
     * 권한 리스트 가져오기
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public String selectAuthList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        
        String v_userid = box.getString("p_id");
        StringBuffer sql = new StringBuffer();
        
        DataBox dbox = null;
        String return_auth = "";
        
        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT B.GADMIN GADMIN, B.GADMINNM GADMINNM \n");
            sql.append("   FROM TZ_MANAGER A, TZ_GADMIN B \n");
            sql.append("  WHERE A.GADMIN    = B.GADMIN \n");
            sql.append("    AND A.USERID    = " + StringManager.makeSQL(v_userid) + " \n");
            sql.append("    AND A.ISDELETED = 'N' \n");
            sql.append("    AND TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN A.FMON AND A.TMON \n");
            sql.append(" ORDER BY B.GADMIN ASC ");

            ls = connMgr.executeQuery(sql.toString());
            
            while(ls.next()){
            	dbox = ls.getDataBox();
            	return_auth = return_auth + dbox.getString("d_gadmin") + "^" + dbox.getString("d_gadminnm") + "||";
            }
            
            if(!return_auth.equals("")){
            	return_auth = return_auth.substring(0, return_auth.length() - 2);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return return_auth;
    }
    
    public int ASP_updateAgreeChk(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = "";
            sql1 += "UPDATE TZ_MEMBER \n";
            sql1 += "   SET ISAGRE_CHK = 'Y', \n";
            sql1 += "   	AGREE_DT   = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n";
            sql1 += " WHERE USERID     = ? \n";
            sql1 += "   AND GRCODE     = ?";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, box.getSession("userid"));
            pstmt.setString(2, box.getSession("tem_grcode"));

            isOk = pstmt.executeUpdate();

            if (isOk == 1) {
            	box.setSession("agreechk", "Y");
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
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
}
