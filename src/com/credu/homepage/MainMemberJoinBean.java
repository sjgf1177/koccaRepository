// **********************************************************
// 1. 제 목: 회원가입
// 2. 프로그램명: MemberJoinBean.java
// 3. 개 요: 회원가입
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이나연 05.12.16
// 7. 수 정:
// 2015-03-02 : 현업의 요청으로 아래의 내용을 수정함.
// insertMember 메서드 수정. 회원 가입시 다음 항목을 받지 않음.
// - 전화번호, 지역, 직업, 종사분야, 가입경로, 게임자격증 여부
// **********************************************************
package com.credu.homepage;

import ipin.Interop;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

// import com.daumsoft.mileage.*;

public class MainMemberJoinBean {

    public MainMemberJoinBean() {
    }

    /**
     * 회원등록
     * 
     * @param box receive from the form object and session
     * @return String
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int insertMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // String sql1 = "";
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_name = box.getStringDefault("p_kor_name", box.getSession("name"));
        String v_eng_name = box.getString("p_eng_name");
        String v_email = box.getString("p_email");
        // String v_resno = box.getStringDefault("p_resno",box.getSession("p_resno"));
        // String v_resno1 = box.getStringDefault("p_resno1",box.getSession("p_resno1"));
        // String v_resno2 = box.getStringDefault("p_resno2",box.getSession("p_resno2"));
        // String v_post1 = box.getString("p_post1");
        // String v_post2 = box.getString("p_post2");
        // String v_home_addr1 = box.getString("p_home_addr1");
        // String v_home_addr2 = box.getString("p_home_addr2");
        // String v_hometel = box.getString("p_hometel");
        String v_handphone = box.getString("p_handphone");
        String v_ismailing = box.getStringDefault("p_ismailing", "N");
        String v_islettering = "N";
        String v_isopening = "N";
        String v_membergubun = "P";
        String v_registgubun = box.getStringDefault("p_registgubun", box.getSession("grtype"));
        String v_state = "Y";
        String v_validation = box.getStringDefault("p_validation", "1"); // 실명인증
        // 관련 홈페이지 가입은 == 1
        String v_issms = v_ismailing;
        // String v_workfieldcd = box.getString("p_workfieldcd");
        // String v_workfieldnm = box.getString("p_workfieldnm");
        String v_dupinfo = box.getString("p_dupinfo");
        // String v_conninfo = box.getString("p_conninfo");
        // String v_safeid = box.getString("p_safeid");
        // String v_vnumber = box.getString("p_vnumber");
        String vv_username = box.getStringDefault("pp_username", "");
        String vv_email = box.getStringDefault("pp_email", "");
        // String v_gamelicyn = box.getStringDefault("p_gamelicyn", "N");

        // String v_sex = box.getStringDefault("p_sex",
        // box.getSession("p_resno2").substring(0,1));
        String v_sex = box.getStringDefault("p_sex", "");
        /*
         * if (box.getSession("p_resno2").equals("")) { v_sex = box.getString("p_sex"); } else { v_sex = box.getStringDefault("p_sex",
         * box.getSession("p_resno2").substring(0, 1)); }
         */
        // String v_comp_post1 = box.getString("p_comp_post1");
        // String v_comp_post2 = box.getString("p_comp_post2");
        // String v_comp_addr1 = box.getString("p_comp_addr1");
        // String v_comp_addr2 = box.getString("p_comp_addr2");
        // String v_comptel = box.getString("p_comptel");
        // String v_comptext = box.getString("p_comptext");
        // String v_degree = box.getStringDefault("p_degree", "");
        // String v_jikup = box.getString("p_jikup");

        String v_memberyear = box.getString("p_memberyear");
        String v_membermonth = box.getString("p_membermonth");
        String v_memberday = box.getString("p_memberday");
        // String v_compnm = box.getString("p_compnm");
        // String v_registerMotive = box.getString("p_registerMotive");
        // String v_registerRoute = box.getString("p_registerRoute");
        // String v_recommendid = box.getString("p_recommendid");

        String certiType = box.getString("certiType");

        String grPrefix = "", mobileUserid = "";

        try {
            // 개인정보 암호화
            v_pwd = HashCipher.createHash(v_pwd);

            connMgr = new DBConnectionManager();

            // B2C를 제외한 교육 그룹별 접두어 조회
            if (!v_grcode.equals("N000001")) {

                sql.setLength(0);
                sql.append("SELECT  GR_PREFIX   \n");
                sql.append("  FROM  TZ_GRCODE   \n");
                sql.append(" WHERE  GRCODE = '").append(v_grcode).append("'\n");

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
            }
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.homepage.MainMemberJoinBean insertMember(회원정보등록) */  \n");
            sql.append("INSERT  INTO TZ_MEMBER( \n");
            sql.append("        GRCODE          \n");
            sql.append("    ,   USERID          \n");
            sql.append("    ,   PWD             \n");
            sql.append("    ,   NAME            \n");
            sql.append("    ,   ENG_NAME        \n");
            sql.append("    ,   EMAIL           \n");
            // sql.append("    ,   ADDR            \n");
            // sql.append("    ,   HOMETEL         \n");
            sql.append("    ,   HANDPHONE       \n");
            sql.append("    ,   ISMAILING       \n");
            sql.append("    ,   ISLETTERING     \n");
            sql.append("    ,   ISOPENING       \n");
            sql.append("    ,   MEMBERGUBUN     \n");
            sql.append("    ,   REGISTGUBUN     \n");
            sql.append("    ,   STATE           \n");
            sql.append("    ,   VALIDATION      \n");
            sql.append("    ,   INDATE          \n");
            sql.append("    ,   LDATE           \n");
            sql.append("    ,   ISSMS           \n");
            // sql.append("    ,   WORKFIELDCD     \n");
            // sql.append("    ,   WORKFIELDNM     \n");
            sql.append("    ,   SEX             \n");
            // sql.append("    ,   DEGREE          \n");
            // sql.append("    ,   JIKUP           \n");
            sql.append("    ,   MEMBERYEAR      \n");
            sql.append("    ,   MEMBERMONTH     \n");
            sql.append("    ,   MEMBERDAY       \n");
            // sql.append("    ,   REGISTERMOTIVE  \n");
            // sql.append("    ,   REGISTERROUTE   \n");
            sql.append("    ,   DUPINFO         \n");
            sql.append("    ,   PARENTNAME      \n");
            sql.append("    ,   PARENTEMAIL     \n");
            // sql.append("    ,   GAMELICYN       \n");
            sql.append("    ,   CERTI_TYPE      \n");
            sql.append("    ,   MOBILE_USERID   \n");
            sql.append(") VALUES (                                                              \n");
            sql.append("        ?                                       /* GRCODE           */  \n");
            sql.append("    ,   ?                                       /* USERID           */  \n");
            sql.append("    ,   ?                                       /* PWD              */  \n");
            sql.append("    ,   ?                                       /* NAME             */  \n");
            sql.append("    ,   ?                                       /* ENG_NAME         */  \n");
            sql.append("    ,   CRYPTO.ENC('normal', ?)                 /* EMAIL            */  \n");
            // sql.append("    ,   ?                                       /* ADDR             */  \n");
            // sql.append("    ,   CRYPTO.ENC('normal',?)                  /* HOMETEL          */  \n");
            sql.append("    ,   CRYPTO.ENC('normal',?)                  /* HANDPHONE        */  \n");
            sql.append("    ,   ?                                       /* ISMAILING        */  \n");
            sql.append("    ,   ?                                       /* ISLETTERING      */  \n");
            sql.append("    ,   ?                                       /* ISOPENING        */  \n");
            sql.append("    ,   ?                                       /* MEMBERGUBUN      */  \n");
            sql.append("    ,   ?                                       /* REGISTGUBUN      */  \n");
            sql.append("    ,   ?                                       /* STATE            */  \n");
            sql.append("    ,   ?                                       /* VALIDATION       */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* INDATE           */  \n");
            sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    /* LDATE            */  \n");
            sql.append("    ,   ?                                       /* ISSMS            */  \n");
            // sql.append("    ,   ?                                       /* WORKFIELDCD      */  \n");
            // sql.append("    ,   ?                                       /* WORKFIELDNM      */  \n");
            sql.append("    ,   ?                                       /* SEX              */  \n");
            // sql.append("    ,   ?                                       /* DEGREE           */  \n");
            // sql.append("    ,   ?                                       /* JIKUP            */  \n");
            sql.append("    ,   ?                                       /* MEMBERYEAR       */  \n");
            sql.append("    ,   ?                                       /* MEMBERMONTH      */  \n");
            sql.append("    ,   ?                                       /* MEMBERDAY        */  \n");
            // sql.append("    ,   ?                                       /* REGISTERMOTIVE   */  \n");
            // sql.append("    ,   ?                                       /* REGISTERROUTE    */  \n");
            sql.append("    ,   ?                                       /* DUPINFO          */  \n");
            sql.append("    ,   ?                                       /* PARENTNAME       */  \n");
            sql.append("    ,   ?                                       /* PARENTEMAIL      */  \n");
            // sql.append("    ,   ?                                       /* GAMELICYN        */  \n");
            sql.append("    ,   ?                                       /* CERTI_TYPE       */  \n");
            sql.append("    ,   ?                                       /* MOBILE_USERID    */  \n");
            sql.append(")                                                                       \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            int i = 1;

            pstmt.setString(i++, v_grcode);
            pstmt.setString(i++, v_userid);
            pstmt.setString(i++, v_pwd);
            pstmt.setString(i++, v_name);
            pstmt.setString(i++, v_eng_name);
            pstmt.setString(i++, v_email);
            // pstmt.setString(i++, v_resno);
            // pstmt.setString(i++, v_post1);
            // pstmt.setString(i++, v_post2);
            // pstmt.setString(i++, v_home_addr1);
            // pstmt.setString(i++, v_home_addr2);
            // pstmt.setString(i++, v_hometel);
            pstmt.setString(i++, v_handphone);
            pstmt.setString(i++, v_ismailing);
            pstmt.setString(i++, v_islettering);
            pstmt.setString(i++, v_isopening);
            pstmt.setString(i++, v_membergubun);
            pstmt.setString(i++, v_registgubun);
            pstmt.setString(i++, v_state);
            pstmt.setString(i++, v_validation);
            pstmt.setString(i++, v_issms);
            // pstmt.setString(i++, v_resno1);
            // pstmt.setString(i++, v_resno2);

            // pstmt.setString(i++, v_workfieldcd);
            // pstmt.setString(i++, v_workfieldnm);
            pstmt.setString(i++, v_sex);
            // pstmt.setString(i++, v_comp_post1);
            // pstmt.setString(i++, v_comp_post2);
            // pstmt.setString(i++, v_comp_addr1);
            // pstmt.setString(i++, v_comp_addr2);
            // pstmt.setString(i++, v_comptel);
            // pstmt.setString(i++, v_comptext);
            // pstmt.setString(i++, v_degree);

            // pstmt.setString(i++, v_jikup);
            pstmt.setString(i++, v_memberyear);
            pstmt.setString(i++, v_membermonth);
            pstmt.setString(i++, v_memberday);
            // pstmt.setString(i++, v_compnm);

            // pstmt.setString(i++, v_registerMotive);
            // pstmt.setString(i++, v_registerRoute);
            // pstmt.setString(i++, v_recommendid);
            pstmt.setString(i++, v_dupinfo);
            // pstmt.setString(i++, v_vnumber);

            pstmt.setString(i++, vv_username);
            pstmt.setString(i++, vv_email);
            // pstmt.setString(i++, v_conninfo);
            // pstmt.setString(i++, v_safeid);
            // pstmt.setString(i++, v_gamelicyn);
            pstmt.setString(i++, certiType);
            pstmt.setString(i++, mobileUserid);

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

    /**
     * 개인정보 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public DataBox memberInfoView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select userid, resno, resno1, resno2, name, crypto.dec('normal',email) email, pwd, eng_name, sex, ";
            sql += "        post1, post2, addr, addr2, crypto.dec('normal',hometel) hometel, crypto.dec('normal',handphone) handphone, comptel,  validation,     ";
            sql += "        comptext, comp_addr1, comp_addr2, comp_post1, comp_post2, degree, jikup, ";
            sql += "		ismailing, isopening, islettering, issms, lgfirst, lgcnt, lglast , ";
            sql += "        workfieldcd,  workfieldnm, grcode, memberyear, membermonth, memberday, ";
            sql += "        compnm, registerMotive, registerRoute, recommendid, eventCourse, eventseq, gamelicyn";
            sql += " from TZ_MEMBER                                               ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);
            sql += " and grcode = " + StringManager.makeSQL(box.getSession("tem_grcode"));

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
     * 개인정보 조회_이벤트 과정 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public DataBox getEventCourse(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox edbox = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select name, userid, eventcourse, subj, subjnm ";
            sql += " from tz_member a, tz_subj b                    ";
            sql += " where a.eventcourse = b.subj                   ";
            sql += "   and userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                edbox = ls.getDataBox();
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

        return edbox;
    }

    /**
     * 개인정보 저장
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : update ok 2 : update fail
     * @throws Exception
     */
    public int memberInfoUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql = new StringBuffer();
        // String sql = "";
        // String sql2 = "";
        int is_Ok = 0;

        String v_grcode = box.getSession("tem_grcode");
        String v_userid = box.getString("p_userid");
        String v_pwd_old = box.getString("p_pwd_old"); // 기존 패스워드
        String v_pwd = box.getString("p_pwd"); // 신규 패스워드

        String v_email = box.getString("p_email");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_handphone = box.getString("p_handphone");
        String v_ismailing = box.getStringDefault("p_ismailing", "N");
        String v_memberyear = box.getString("p_memberyear");
        String v_membermonth = box.getString("p_membermonth");
        String v_memberday = box.getString("p_memberday");
        String v_home_addr1 = box.getString("p_home_addr1");
        String v_hometel = box.getString("p_hometel");
        String v_workfieldcd = box.getString("p_workfieldcd");
        String v_workfieldnm = box.getString("p_workfieldnm");
        String v_jikup = box.getString("p_jikup");
        String v_compnm = box.getString("p_compnm");
        String v_home_addr2 = box.getString("p_home_addr2");
        String v_comptel = box.getString("p_comptel");

        // String v_issms = v_ismailing;
        // String v_degree = box.getString("p_degree");
        // String v_registerMotive = box.getString("p_registerMotive");
        // String v_registerRoute = box.getString("p_registerRoute");
        // String v_eventCourse = box.getString("p_eventCourse");
        // String s_eventseq = box.getString("p_eventseq");
        // String v_gamelicyn = box.getString("p_gamelicyn");
        // String p_eventyn = "";
        // String v_comp_post1 = box.getString("p_comp_post1");
        // String v_comp_post2 = box.getString("p_comp_post2");
        // String v_comp_addr1 = box.getString("p_comp_addr1");
        // String v_comp_addr2 = box.getString("p_comp_addr2");
        // String v_comptext = box.getString("p_comptext");

        // 튜터 데이터 (암호화하지않음)
        String vv_email = v_email;
        String vv_home_addr2 = v_home_addr2;
        String vv_handphone = v_handphone;

        try {
            connMgr = new DBConnectionManager();

            if (v_grcode.equals("N000001")) {
                sql.setLength(0);
                sql.append("UPDATE  TZ_MEMBER SET   \n");
                // sql.append("        HOMETEL = CRYPTO.ENC('normal', ?) \n");
                sql.append("        HANDPHONE = CRYPTO.ENC('normal', ?) \n");
                // sql.append("    ,   ADDR = ? \n");
                sql.append("    ,   EMAIL = CRYPTO.ENC('normal', ?) \n");
                // sql.append("    ,   JIKUP = ? \n");
                // sql.append("    ,   WORKFIELDCD = ? \n");
                // sql.append("    ,   COMPNM = ? \n");
                sql.append("    ,   ISMAILING = ?\n");
                // sql.append("    ,   GAMELICYN = ?\n");
                sql.append(" WHERE  USERID = ?\n");
                sql.append("   AND  GRCODE = 'N000001' \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                int i = 1;
                // pstmt.setString(i++, v_hometel);
                pstmt.setString(i++, v_handphone);
                // pstmt.setString(i++, v_home_addr1);
                pstmt.setString(i++, v_email);
                // pstmt.setString(i++, v_jikup);
                // pstmt.setString(i++, v_workfieldcd);
                // pstmt.setString(i++, v_compnm);
                pstmt.setString(i++, v_ismailing);
                // pstmt.setString(i++, v_gamelicyn);
                pstmt.setString(i++, v_userid);

                is_Ok = pstmt.executeUpdate();
            } else {

                sql.setLength(0);
                sql.append("UPDATE  TZ_MEMBER   SET \n");
                sql.append("        EMAIL = crypto.enc('normal', ? )    \n");
                sql.append("    ,   ADDR = ?    \n");
                sql.append("    ,   HOMETEL = crypto.enc('normal', ? )  \n");
                sql.append("    ,   HANDPHONE = crypto.enc('normal', ?  \n");
                sql.append("    ,   ISMAILING = ?   \n");
                sql.append("    ,   WORKFIELDCD = ? \n");
                sql.append("    ,   WORKFIELDNM = ? \n");
                sql.append("    ,   JIKUP =  ?  \n");
                sql.append("    ,   COMPNM = ?  \n");
                sql.append("    ,   MEMBERYEAR = ?  \n");
                sql.append("    ,   MEMBERMONTH = ? \n");
                sql.append("    ,   MEMBERDAY = ?   \n");
                sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append(" WHERE  USERID = ?  \n");
                sql.append("   AND  GRCODE = ?  \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                if (!v_pwd.equals("")) {
                    v_pwd = HashCipher.createHash(v_pwd);
                } else {
                    v_pwd = v_pwd_old;
                }
                // ===========================
                int i = 1;
                // pstmt.setString(i++, v_pwd);
                pstmt.setString(i++, v_email);
                pstmt.setString(i++, v_home_addr1);
                pstmt.setString(i++, v_hometel);
                pstmt.setString(i++, v_handphone);
                pstmt.setString(i++, v_ismailing);
                pstmt.setString(i++, v_workfieldcd);
                pstmt.setString(i++, v_workfieldnm);
                pstmt.setString(i++, v_jikup);
                pstmt.setString(i++, v_compnm);
                pstmt.setString(i++, v_memberyear);
                pstmt.setString(i++, v_membermonth);
                pstmt.setString(i++, v_memberday);

                // pstmt.setString(i++, v_comp_post1);
                // pstmt.setString(i++, v_comp_post2);
                // pstmt.setString(i++, v_comp_addr1);
                // pstmt.setString(i++, v_comp_addr2);
                // pstmt.setString(i++, v_comptel);
                // pstmt.setString(i++, v_comptext);
                // pstmt.setString(i++, v_degree);
                // pstmt.setString(i++, v_jikup);

                pstmt.setString(i++, v_userid);
                pstmt.setString(i++, v_grcode);

                is_Ok = pstmt.executeUpdate();
            }

            // update TZ_TUTOR table
            sql.setLength(0);
            sql.append("UPDATE  TZ_TUTOR    \n");
            sql.append("   SET  POST1 = ?   \n");
            sql.append("    ,   POST2 = ?   \n");
            sql.append("    ,   ADD1 = ?    \n");
            sql.append("    ,   ADD2 = ?    \n");
            sql.append("    ,   PHONE = ?   \n");
            sql.append("    ,   HANDPHONE = ?   \n");
            sql.append("    ,   EMAIL = ?   \n");
            sql.append("    ,   LUSERID = ? \n");
            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDD')    \n");
            sql.append(" WHERE  USERID = ?  \n");

            pstmt2 = connMgr.prepareStatement(sql.toString());

            pstmt2.setString(1, v_post1);
            pstmt2.setString(2, v_post2);
            pstmt2.setString(3, v_home_addr1);
            pstmt2.setString(4, vv_home_addr2);
            pstmt2.setString(5, v_comptel);
            pstmt2.setString(6, vv_handphone);
            pstmt2.setString(7, vv_email);
            pstmt2.setString(8, v_userid);
            pstmt2.setString(9, v_userid);

            pstmt2.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return is_Ok;
    }

    /**
     * 회원 탈퇴
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : update ok 2 : update fail
     * @throws Exception
     */
    public int memberWithdrawUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid = box.getString("p_userid");
        String v_pwd = box.getString("p_pwd");
        String v_leave_reason = box.getString("p_leave_reason");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            // 개인정보 암호화
            if (!v_pwd.equals("")) {
                v_pwd = HashCipher.createHash(v_pwd);
            }

            sql = " select count(*) cnt \n";
            sql += " from TZ_MEMBER a  \n";
            sql += " where a.userid = " + StringManager.makeSQL(v_userid);
            sql += " and a.grcode = " + StringManager.makeSQL(v_grcode);
            sql += " and a.pwd = " + StringManager.makeSQL(v_pwd);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt(1) == 0) {
                    is_Ok = -1;
                    return is_Ok;
                }
            }

            sql = " update TZ_MEMBER set   ";
            sql += "    pwd=null, name = name||'-WITHDRAW',  eng_name = 'WITHDRAW', state = 'N',  leave_reason = ?, ";
            sql += "    email=null, resno=null, resno1=null, resno2=null,  hometel=null, handphone=null, ";
            sql += "    post1=null, post2=null, addr=null, addr2=null, ";
            sql += "    leave_date = to_char(sysdate, 'YYYYMMDDHH24MISS'), ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'),  ";
            sql += "    dupinfo=null ";
            sql += " where userid = ?  ";
            sql += " and grcode = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_leave_reason);
            pstmt.setString(2, v_userid);
            pstmt.setString(3, v_grcode);

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

    /**
     * 비밀번호 변경
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1: update ok, 2: update fail
     * @throws Exception
     */
    public int pwdUpdate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;

        String v_userid = box.getString("p_userid");
        String v_pwd_old = box.getString("p_pwd_old"); // 기존 패스워드
        String v_pwd = box.getString("p_pwd"); // 신규 패스워드
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            // 암호화 모듈 적용
            if (!v_pwd_old.equals("")) {
                v_pwd_old = HashCipher.createHash(v_pwd_old);
            }

            if (!v_pwd.equals("")) {
                v_pwd = HashCipher.createHash(v_pwd);
            }

            sql = " select count(*) cnt \n";
            sql += " from TZ_MEMBER a  \n";
            sql += " where a.userid = " + StringManager.makeSQL(v_userid);
            sql += " and a.grcode = " + StringManager.makeSQL(v_grcode);
            sql += " and a.pwd = " + StringManager.makeSQL(v_pwd_old);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt(1) == 0) {
                    is_Ok = -1;
                    return is_Ok;
                }
            }

            sql = " update TZ_MEMBER set   ";
            sql += " pwd = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += " where userid = ?  ";
            sql += " and grcode = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_pwd);
            pstmt.setString(2, v_userid);
            pstmt.setString(3, v_grcode);

            is_Ok = pstmt.executeUpdate();

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
     * 회원가입시 가입확인여부
     * 
     * @param box receive from the form object and session
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int checkResno(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;

        String v_resno1 = box.getString("p_resno1");
        String v_resno2 = box.getString("p_resno2");
        String v_resno = v_resno1 + v_resno2;
        String v_name = box.getString("p_username");
        // String iRtn = box.getString("i_irtn");
        String i_dupinfo = box.getStringDefault("i_dupinfo", box.getSession("dupinfo"));
        try {

            connMgr = new DBConnectionManager();

            sql = " select count(*) cnt \n";
            sql += " from TZ_MEMBER a  \n";
            sql += " where a.state = 'Y' and a.dupinfo = " + StringManager.makeSQL(i_dupinfo);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                is_Ok = ls.getInt(1);
                box.put("p_resno", v_resno);
                box.put("p_resno1", v_resno1);
                box.put("p_resno2", v_resno2); // 암호화전의 값 리턴
                box.put("p_username", v_name);
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
     * 아이디 존재 여부 검색
     * 
     * @param box receive from the form object and session
     * @return String 아이디 존재 여부 검색
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    public int checkID(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        // ArrayList list = null;
        ListSet ls = null;
        int is_Ok = 0;
        String sql = "";
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select count(*) cnt from TZ_MEMBER where USERID = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                is_Ok = ls.getInt(1);
                box.put("p_userid", v_userid);
            }

            return is_Ok;

        } catch (Exception ex) {
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
    }

    /**
     * ID 검색
     * 
     * @param box receive from the form object and session
     * @return String ID 검색 결과
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getUserid(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";

        String v_name = box.getString("p_name");
        String v_resno1 = box.getString("p_resno1");
        String v_resno2 = box.getString("p_resno2");
        String v_resno = v_resno1 + v_resno2;
        String v_dupinfo = "";

        String[] info = null;
        String flag = "JID";

        ArrayList result = null;

        Interop interop = new Interop();
        info = interop.Interop(v_resno, flag).split(";");

        v_dupinfo = info[0];

        try {

            connMgr = new DBConnectionManager();
            result = new ArrayList();

            // 아이디로 비교
            sql1 = " select userid      ";
            sql1 += " from TZ_MEMBER  ";
            sql1 += " where dupinfo  = " + StringManager.makeSQL(v_dupinfo);
            sql1 += "   and name   = " + StringManager.makeSQL(v_name);
            sql1 += "	and state = 'Y' ";

            sql1 = connMgr.replaceParam(sql1, box);
            ls1 = connMgr.executeQuery(sql1);

            result = ls1.getAllDataList(connMgr, sql1, box, true);

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

}
