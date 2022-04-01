// **********************************************************
// 1. 제 목: 로긴관리
// 2. 프로그램명 : MemberInfoBean.java
// 3. 개 요: 로그인,패스워드찾기
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2003. 7. 2
// 7. 수 정:
// **********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

@SuppressWarnings("unchecked")
public class MemberInfoBean {

    ConfigSet cs = null;

    public MemberInfoBean() {
    }

    /**
     * Login 정보 변경 (lgcnt:로그인횟수, lglast:최종로그인시간, lgip:로그인ip
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : success 2 : fail
     */
    public int updateLoginData(String p_userid, String p_userip) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        String v_userid = p_userid;
        String v_userip = p_userip;

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_MEMBER                       ";
            sql += " set lgcnt=lgcnt+1, lglast= to_char(sysdate, 'YYYYMMDDHH24MISS'), lgip=" + StringManager.makeSQL(v_userip);
            sql += " where userid = " + StringManager.makeSQL(v_userid);

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
     * 개인정보 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    // public ArrayList memberInfoView(RequestBox box) throws Exception {
    public DataBox memberInfoView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select userid, resno, name, email, pwd, eng_name, ";
            sql += "        post1, post2, addr, addr2, hometel, handphone, comptel,  validation,     ";
            sql += "        comptext, comp_addr1, comp_addr2, comp_post1, comp_post2, degree, jikup, ";
            sql += "          ismailing, isopening, islettering, lgfirst, lgcnt, lglast ";
            sql += " from TZ_MEMBER                                               ";
            sql += " where userid = " + StringManager.makeSQL(v_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                // list.add(dbox);
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
     * 수강 신청을 한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int memberSubjLimit(RequestBox box) throws Exception {

        cs = new ConfigSet();

        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        StringBuilder sql = new StringBuilder();
        int isOk = 0;

        String s_userid = box.getSession("userid");
        String s_grcode = box.getSession("tem_grcode");
        String subj = box.get("p_subj");
        String subjseq = box.get("p_subjseq");
        String year = box.get("p_year");

        // String v_grseq = null;
        // String v_biyong = null;
        int v_studentlimit = 0;

        try {

            // 수강신청 종료 시점 이전에 수강신청 창을 열어놓은 상태에서 아무런 행동을 하지 않고,
            // 수간신청 종료 시간이 지난 후에 신청 버튼을 클릭할 경우 수강신청이 되지 않도록 방지
            if (!isPossibleSubjApplyByDate(box)) {
                isOk = 4;
            } else {

                connMgr = new DBConnectionManager();

                // 현재 차수 정보 , 유/무료 과정 판단

                sql.append("/* MemberInfoBean.memberSubjLimit(현재 차수 정보 , 유/무료 과정 판단) */    \n");
                sql.append("SELECT                                          \n");
                sql.append("        GRSEQ                                   \n");
                sql.append("    ,   BIYONG                                  \n");
                sql.append("    ,   STUDENTLIMIT                            \n");
                sql.append("  FROM  TZ_SUBJSEQ                              \n");
                sql.append(" WHERE  GRCODE = '").append(s_grcode).append("' \n");
                sql.append("   AND  SUBJ = '").append(subj).append("'       \n");
                sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");
                sql.append("   AND  GYEAR = '").append(year).append("'      \n");

                // sql.append(" SELECT GRSEQ, BIYONG,STUDENTLIMIT FROM TZ_SUBJSEQ \n");
                // sql.append(" WHERE GRCODE = " + SQLString.Format(s_grcode) + " \n");
                // sql.append(" AND SUBJ = " + SQLString.Format(subj) + " \n");
                // sql.append(" AND SUBJSEQ = " + SQLString.Format(subjseq) + " \n");
                // sql.append(" AND GYEAR = " + SQLString.Format(year) + " \n");

                ls1 = connMgr.executeQuery(sql.toString());

                if (ls1.next()) {

                    // String pre_grseq = null;
                    // v_biyong = ls1.getString("biyong");
                    // int fsubj_cnt = Integer.parseInt(cs.getProperty("kocca.freesubj.cnt")); // 설정된 신청 제한수

                    // v_grseq = ls1.getString("grseq");
                    v_studentlimit = ls1.getInt("STUDENTLIMIT");

                    int fsubj_cnt = 1; // 설정된 신청 제한수

                    String grcode = box.getSession("tem_grcode");

                    // if( v_biyong.equals("0") && grcode.equals("N000001"))
                    if (grcode.equals("N000001") || grcode.equals("N000055") 
                    //		|| grcode.equals("N000030") 송파구청 신청 제한수 해제
                    		|| grcode.equals("N000058") || grcode.equals("N000060") || grcode.equals("N000061") || grcode.equals("N000063") || grcode.equals("N000018")
                            || grcode.equals("N000064") || grcode.equals("N000022") || grcode.equals("N000081") || grcode.equals("N000083")) {
                        int usubj_cnt = 0;

                        if (grcode.equals("N000001")) {
                            fsubj_cnt = 5; // 한국콘텐츠아카데미. 
                                           // 기존 월 1회 1차수 차수별 3개과정에서 월 2차수 월별 3개과정으로 수정(2014년 7월 1일)
                                           // 월 2차수 차수별 5개 과정으로 수정 (2014년 11월 1일) 
                            
                        } else if (grcode.equals("N000022")) {
                            fsubj_cnt = 5; // 문화체육관광부
                            
                        } else if (grcode.equals("N000083")) {
                            fsubj_cnt = 3; // 한국문화콘텐츠고등학교 2014년 10월 22일 수강가능한 과정을 1개에서 3개로 수정
                            
                        } else {
                            fsubj_cnt = 2; // 그 외 if 문에 걸린 모든 ASP 사이트
                        }

                        // 교육정원이 다 찼는지 여부를 확인
                        sql.setLength(0);

                        sql.append("/* MemberInfoBean.memberSubjLimit (교육정원 체크) */\n");
                        sql.append("SELECT  COUNT(*) CNT                                \n");
                        sql.append("  FROM  TZ_PROPOSE A                                \n");
                        sql.append(" WHERE  A.SUBJ = '").append(subj).append("'         \n");
                        sql.append("   AND  A.YEAR = '").append(year).append("'         \n");
                        sql.append("   AND  A.SUBJSEQ = '").append(subjseq).append("'   \n");
                        sql.append("   AND  (A.CANCELKIND IS NULL OR A.CANCELKIND = '') \n");

                        ls2 = connMgr.executeQuery(sql.toString());

                        if (ls2.next()) {
                            usubj_cnt = ls2.getInt("cnt");

                            if (usubj_cnt >= v_studentlimit) {
                                isOk = 2;
                            }
                        }

                        //                        sql.setLength(0);
                        //                        sql.append(" SELECT COUNT(*) CNT \n");
                        //                        sql.append("  FROM TZ_SUBJSEQ A \n");
                        //                        sql.append("  left join TZ_PROPOSE B on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq and b.userid="
                        //                                + SQLString.Format(s_userid) + " \n");
                        //                        sql.append("  where NVL(A.BIYONG, 0) = 0 \n");
                        //                        sql.append("    and (B.CANCELKIND IS NULL OR B.CANCELKIND = '') \n");
                        //                        sql.append("    AND a.GRCODE = " + SQLString.Format(s_grcode) + " \n");
                        //                        sql.append("    and b.userid is not null \n");
                        //                        // sql.append("    and substr(PROPSTART,1,6)=to_char(sysdate,'YYYYMM') \n"); //수강신청기간이 늘리거나 하면 안됨 수정
                        //                        // sql.append("    and substr(PROPEND,1,6)=to_char(sysdate,'YYYYMM') \n");
                        //                        sql.append("    AND B.YEAR = " + SQLString.Format(year) + " \n");
                        //                        sql.append("    and a.grseq = " + SQLString.Format(v_grseq) + " \n"); // 매 차수 마다 수강신청이 3개과정으로 제한 이 되어있음..where 절 조건에 grseq(차수)를 추가함.
                        //

                        sql.setLength(0);
                        sql.append("/* com.credu.homepage.MemberInfoBean memberSubjLimit (수강신청건수 조회) */   \n");
                        sql.append("SELECT  COUNT(USERID) AS APPLY_CNT  \n");
                        sql.append("  FROM  TZ_GRSEQ A                  \n");
                        sql.append("    ,   TZ_SUBJSEQ B                \n");
                        sql.append("    ,   TZ_PROPOSE C                \n");
                        sql.append(" WHERE  A.GRCODE = '").append(s_grcode).append("'   \n");
                        sql.append("   AND  A.HOMEPAGEYN = 'Y'          \n");
                        sql.append("   AND  A.STAT = 'Y'                \n");
                        sql.append("   AND  A.GRCODE = B.GRCODE         \n");
                        sql.append("   AND  A.GYEAR = B.GYEAR           \n");
                        sql.append("   AND  A.GRSEQ = B.GRSEQ           \n");
                        sql.append("   AND  B.YEAR = C.YEAR             \n");
                        sql.append("   AND  B.SUBJ = C.SUBJ             \n");
                        sql.append("   AND  B.SUBJSEQ = C.SUBJSEQ       \n");
                        sql.append("   AND  C.USERID = '").append(s_userid).append("'       \n");
                        sql.append("   AND  TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN B.PROPSTART AND B.PROPEND \n");
                        sql.append("   AND  (C.CANCELKIND IS NULL OR C.CANCELKIND = '')     \n");

                        // sql.setLength(0);
                        // sql.append("/* (수강신청건수 조회) */           \n");
                        // sql.append("SELECT  COUNT(D.USERID) AS APPLY_CNT                                            \n");
                        // sql.append("  FROM  (                                                                       \n");
                        // sql.append("        SELECT                                                                  \n");
                        // sql.append("                LAG(A.GYEAR) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GYEAR         \n");
                        // sql.append("            ,   LAG(A.GRSEQ) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GRSEQ         \n");
                        // sql.append("            ,   A.GRCODE                                                        \n");
                        // sql.append("            ,   A.GYEAR                                                         \n");
                        // sql.append("            ,   A.GRSEQ                                                         \n");
                        // sql.append("            ,   A.GRSEQNM                                                       \n");
                        // sql.append("            ,   B.PROPSTART                                                     \n");
                        // sql.append("            ,   B.PROPEND                                                       \n");
                        // sql.append("            ,   LEAD(A.GYEAR) OVER(ORDER BY A.GYEAR, A.GRSEQ) NEXT_GYEAR        \n");
                        // sql.append("            ,   LEAD(A.GRSEQ) OVER(ORDER BY A.GYEAR, A.GRSEQ) NEXT_GRSEQ        \n");
                        // sql.append("          FROM  TZ_GRSEQ A                                                      \n");
                        // sql.append("            ,   (                                                               \n");
                        // sql.append("                SELECT  GRCODE                                                  \n");
                        // sql.append("                    ,   GYEAR                                                   \n");
                        // sql.append("                    ,   GRSEQ                                                   \n");
                        // sql.append("                    ,   PROPSTART                                               \n");
                        // sql.append("                    ,   PROPEND                                                 \n");
                        // sql.append("                  FROM  TZ_SUBJSEQ                                              \n");
                        // sql.append("                 WHERE  GRCODE = '").append(grcode).append("'                   \n");
                        // sql.append("                 GROUP BY GRCODE, GYEAR, GRSEQ, PROPSTART, PROPEND              \n");
                        // sql.append("                ) B                                                             \n");
                        // sql.append("         WHERE  A.GRCODE = '").append(grcode).append("'                         \n");
                        // sql.append("           AND  A.HOMEPAGEYN = 'Y'                                              \n");
                        // sql.append("           AND  A.STAT = 'Y'                                                    \n");
                        // sql.append("           AND  A.GYEAR = B.GYEAR                                               \n");
                        // sql.append("           AND  A.GRCODE = B.GRCODE                                             \n");
                        // sql.append("           AND  A.GRSEQ = B.GRSEQ                                               \n");
                        // sql.append("         ORDER  BY A.GYEAR, A.GRSEQ                                             \n");
                        // sql.append("        ) V1                                                                    \n");
                        // sql.append("    ,   TZ_SUBJSEQ C                                                            \n");
                        // sql.append("    ,   TZ_PROPOSE D                                                            \n");
                        // sql.append(" WHERE  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN V1.PROPSTART AND V1.PROPEND    \n");
                        // 
                        // if (grcode.equals("N000001")) {
                            // B2C의 경우 2014년 7월을 기점으로 월 2차수 교육 운영 시작
                            // 학습자는 이전 차수와 현재 차수를 합하여 3개까지만 신청 가능
                            // sql.append("   AND  ( ( V1.PREV_GYEAR = C.GYEAR AND  V1.PREV_GRSEQ = C.GRSEQ)   \n");
                            // sql.append("        OR ( V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ) )          \n");
                        // } else {
                            // B2B는 이전 운영하던 방식으로 진행
                            // sql.append("   AND  V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ                  \n");
                        // }
                        // sql.append("   AND  C.YEAR = D.YEAR                                                         \n");
                        // sql.append("   AND  C.SUBJ = D.SUBJ                                                         \n");
                        // sql.append("   AND  C.SUBJSEQ = D.SUBJSEQ                                                   \n");
                        // sql.append("   AND  D.USERID = '").append(s_userid).append("'                               \n");
                        // sql.append("   AND  (D.CANCELKIND IS NULL OR D.CANCELKIND = '')     \n");

                        ls2 = connMgr.executeQuery(sql.toString());

                        if (ls2.next()) {
                            usubj_cnt = ls2.getInt("apply_cnt");
                            // 현재 수강신청된 과정 수와 지금 신청한 과정수를 더해야 하기 때문에 +1를 한다.
                            if ((usubj_cnt + 1) > fsubj_cnt) {
                                if (grcode.equals("N000001")) {
                                    isOk = 2; // 한국콘텐츠
                                } else {
                                    isOk = 3; // 홈앤쇼핑 송파구청 디지텍고
                                }
                            }
                        }
                        sql.setLength(0);
                    } else { // 유료과정
                        isOk = 0;
                    }
                } else {
                    isOk = 0;
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box) + "\r\n" + ex.getMessage());
        } finally {
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
        }

        return isOk;
    }

    /**
     * 수강신청 - 개인추가정보 조회
     * 
     * @param box receive from the form object and session
     * @return ProposeCourseData
     */
    public DataBox memberInfoViewNew(RequestBox box) throws Exception {
        box.sync("p_subj", "s_subj");
        box.sync("p_subjseq", "s_subjseq");
        box.sync("p_year", "s_year");
        box.sync("p_grcode", "s_grcode");

        DBConnectionManager connMgr = null;
        DataBox dbox = new DataBox("result");
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        int onOff = box.getInt("onOff");

        if (box.get("p_userid").length() == 0) {
            box.put("p_userid", box.getSession("userid"));
        }

        String p_term_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            /**
             * 기본정보
             */
            sql.append("/* MemberInfoBean memberInfoViewNew (과정 신청시 사용자 기본정보 조회) */\n");
            sql.append("SELECT  D.USERID                                                        \n");
            sql.append("    ,   D.NAME                                                          \n");
            sql.append("    ,   D.RESNO                                                         \n");
            sql.append("    ,   D.RESNO1                                                        \n");
            sql.append("    ,   D.RESNO2                                                        \n");
            sql.append("    ,   crypto.dec('normal', D.EMAIL) AS EMAIL                          \n");
            sql.append("    ,   D.PWD                                                           \n");
            sql.append("    ,   D.ENG_NAME                                                      \n");
            sql.append("    ,   D.POST1                                                         \n");
            sql.append("    ,   D.POST2                                                         \n");
            sql.append("    ,   D.ADDR                                                          \n");
            sql.append("    ,   D.ADDR2                                                         \n");
            sql.append("    ,   crypto.dec('normal', D.HOMETEL) AS HOMETEL                      \n");
            sql.append("    ,   crypto.dec('normal', D.HANDPHONE) AS HANDPHONE                  \n");
            sql.append("    ,   D.COMPTEL                                                       \n");
            sql.append("    ,   D.COMPTEXT                                                      \n");
            sql.append("    ,   D.COMP_ADDR1                                                    \n");
            sql.append("    ,   D.COMP_ADDR2                                                    \n");
            sql.append("    ,   D.COMP_POST1                                                    \n");
            sql.append("    ,   D.COMP_POST2                                                    \n");
            sql.append("    ,   D.DEGREE                                                        \n");
            sql.append("    ,   D.COMPTEXT   , D.DEPTNAM,  D.jiKCHAEKNM                            \n");
            sql.append("    ,   IMG_PATH                                                        \n");
            sql.append("    ,   D.VALIDATION                                                    \n");
            sql.append("    ,   DECODE(D.SEX, '1', '남', '2', '여', '미등록 ') AS SEX           \n");
            sql.append("    ,   D.JIKUP                                                         \n");
            sql.append("    ,   D.ISMAILING                                                     \n");
            sql.append("    ,   D.ISOPENING                                                     \n");
            sql.append("    ,   D.ISLETTERING                                                   \n");
            sql.append("    ,   D.LGFIRST                                                       \n");
            sql.append("    ,   D.LGCNT                                                         \n");
            sql.append("    ,   D.LGLAST                                                        \n");
            sql.append("    ,   D.ISSMS                                                         \n");
            sql.append("    ,   E.MILITARYSTATUS                                                \n");
            sql.append("    ,   E.MILITARYSTART                                                 \n");
            sql.append("    ,   E.MILITARYEND                                                   \n");
            sql.append("    ,   E.MILITARYMEMO                                                  \n");
            sql.append("    ,   D.FAX, D.GRCODE                                                 \n");
            sql.append("    ,   (                                                               \n");
            sql.append("        SELECT  COUNT(*)                                                \n");
            sql.append("          FROM  TZ_OFFPROPOSE                                           \n");
            sql.append("         WHERE  SUBJ = ':p_subj'                                        \n");
            sql.append("           AND  YEAR = ':p_year'                                        \n");
            sql.append("           AND  SUBJSEQ = ':p_subjseq'                                  \n");
            sql.append("        ) AS NOW_SUKANG_COUNT                                           \n");
            sql.append("    ,   STUDENTLIMIT                                                    \n");
            sql.append("    ,   NVL(STUDENTWAIT, 0) AS SYUDENTWAIT                              \n");
            sql.append("    ,   PRIVATE_YESNO                                                   \n");
            sql.append("    ,   IMG_PATH                                                        \n");
            sql.append("    ,   G.SAVEFILENM                                                    \n");
            sql.append("    ,   G.REALFILENM                                                    \n");
            sql.append("  FROM  TZ_MEMBER D                                                     \n");
            sql.append("  LEFT  JOIN TZ_MEMBER_MILITARY E ON D.USERID = E.USERID                \n");
            sql.append("  LEFT  JOIN TZ_OFFSUBJSEQ F ON SUBJ = ':p_subj'                        \n");
            sql.append("   AND  YEAR = ':p_year'                                                \n");
            sql.append("   AND  SUBJSEQ = ':p_subjseq'                                          \n");
            sql.append("  LEFT  JOIN TZ_OFFPROPOSEFILE G ON G.SUBJ = ':p_subj'                  \n");
            sql.append("   AND  G.YEAR = ':p_year'                                              \n");
            sql.append("   AND  G.SUBJSEQ = ':p_subjseq'                                        \n");
            sql.append("   AND  G.SEQ = '1'                                                     \n");
            sql.append("   AND  G.USERID = ':p_userid'                                          \n");
            sql.append(" WHERE  D.USERID = ':p_userid'                                          \n");
            sql.append("   AND  D.GRCODE = NVL('").append(p_term_grcode).append("', D.GRCODE)   \n");

            ls1 = connMgr.executeQuery(sql.toString(), box);

            if (ls1.next()) {
                dbox.putAll(ls1.getDataBox());

                if (p_term_grcode.equals("N000001")) {
                    // ====================================================
                    // 개인정보 복호화-HTJ
                    /*
                     * SeedCipher seed = new SeedCipher(); if
                     * (!dbox.getString("d_resno2").equals(""))
                     * dbox.put("d_resno2"
                     * ,seed.decryptAsString(Base64.decode(dbox
                     * .getString("d_resno2")), seed.key.getBytes(), "UTF-8"));
                     * if (!dbox.getString("d_email").equals(""))
                     * dbox.put("d_email"
                     * ,seed.decryptAsString(Base64.decode(dbox
                     * .getString("d_email")), seed.key.getBytes(), "UTF-8"));
                     * if (!dbox.getString("d_hometel").equals(""))
                     * dbox.put("d_hometel"
                     * ,seed.decryptAsString(Base64.decode(dbox
                     * .getString("d_hometel")), seed.key.getBytes(), "UTF-8"));
                     * if (!dbox.getString("d_handphone").equals(""))
                     * dbox.put("d_handphone"
                     * ,seed.decryptAsString(Base64.decode(
                     * dbox.getString("d_handphone")), seed.key.getBytes(),
                     * "UTF-8")); if (!dbox.getString("d_addr2").equals(""))
                     * dbox
                     * .put("d_addr2",seed.decryptAsString(Base64.decode(dbox
                     * .getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                     */

                    /*
                     * EncryptUtil encryptUtil = new
                     * EncryptUtil(Constants.APP_KEY,Constants.APP_IV); if
                     * (!dbox.getString("d_resno2").equals(""))
                     * dbox.put("d_resno2"
                     * ,encryptUtil.decrypt(dbox.getString("d_resno2"))); if
                     * (!dbox.getString("d_email").equals(""))
                     * dbox.put("d_email"
                     * ,encryptUtil.decrypt(dbox.getString("d_email"))); if
                     * (!dbox.getString("d_hometel").equals(""))
                     * dbox.put("d_hometel"
                     * ,encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                     * (!dbox.getString("d_handphone").equals(""))
                     * dbox.put("d_handphone"
                     * ,encryptUtil.decrypt(dbox.getString("d_handphone"))); if
                     * (!dbox.getString("d_addr2").equals(""))
                     * dbox.put("d_addr2"
                     * ,encryptUtil.decrypt(dbox.getString("d_addr2")));
                     */
                    // ====================================================
                }
            }
            if (onOff != 0 && box.get("p_subj").length() > 0)
                dbox.putAll(membeSubjectInfo(connMgr, box, onOff));

            dbox = memberAddInfo(connMgr, box, dbox);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
     * 개인의 수강과정관련 추가정보를 조회한다.(온라인과 오프라인으로 구분)
     * 
     * @param connMgr
     * @param box
     * @param dbox
     * @return
     * @throws Exception
     */
    public DataBox membeSubjectInfo(DBConnectionManager connMgr, RequestBox box, int onOff) throws Exception {
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String process = box.getString("p_process");

        if (box.get("p_userid").length() == 0) {
            box.put("p_userid", box.getSession("userid"));
        }

        try {
            connMgr = new DBConnectionManager();

            if (onOff == 1) { // 온라인 과정

                sql.append("SELECT\n");
                sql.append("    A.SUBJ, A.SUBJNM, B.MIDDLECLASS, B.MIDDLECLASSNM CLASSNAME, A.AUTOCONFIRM\n");
                sql.append("    , A.SUBJSEQ, A.BIYONG, A.PROPSTART, A.PROPEND, A.EDUSTART, A.EDUEND, B.NEEDINPUT\n");
                sql.append("    , E.YEUNSUNO,E.YEONSUNM,E.INTRO,E.vision,E.MOTIVE,E.ETC,E.TCAREERYEAR, E.TCAREERMONTH\n");
                sql.append("FROM tz_subjseq A, VZ_SUBJCLASS B, TZ_PROPOSE_ADDINFO E\n");
                sql.append("WHERE   A.SUBJ = B.SUBJ\n");
                sql.append("    AND A.SUBJ = ':p_subj'\n");
                sql.append("    AND A.subjseq = ':p_subjseq'\n");
                sql.append("    AND A.year = ':p_year'\n");
                sql.append("    AND A.SUBJ = E.SUBJ(+)\n");
                sql.append("    AND A.SUBJSEQ = E.SUBJSEQ(+)\n");
                sql.append("    AND A.YEAR = E.YEAR(+)\n");
                sql.append("    AND E.USERID(+) = ':p_userid'\n");

                ls1 = connMgr.executeQuery(sql.toString(), box);

                if (ls1 != null && ls1.next()) {
                    dbox = ls1.getDataBox();
                }

            } else { // 오프라인 과정

                if (process.equals("SubjectEduProposePage")) { // 오프라인과정 신청 화면

                    sql.append("/* MemberInfoBean.membeSubjectInfo (오프라인 수강신청 정보 조회) */\n");
                    sql.append("SELECT                      \n");
                    sql.append("        A.SUBJ              \n");
                    sql.append("    ,   A.SUBJSEQ           \n");
                    sql.append("    ,   A.YEAR              \n");
                    sql.append("    ,   A.SUBJNM            \n");
                    sql.append("    ,   B.MIDDLECLASS       \n");
                    sql.append("    ,   A.BIYONG            \n");
                    sql.append("    ,   A.PROPSTART         \n");
                    sql.append("    ,   A.PROPEND           \n");
                    sql.append("    ,   A.EDUSTART          \n");
                    sql.append("    ,   A.EDUEND            \n");
                    sql.append("    ,   RPAD(A.NEEDINPUT, 11, '0') AS NEEDINPUT \n");
                    sql.append("    ,   A.SPECIALS          \n");
                    sql.append("  FROM  TZ_OFFSUBJSEQ A     \n");
                    sql.append("    ,   VZ_OFFSUBJCLASS B   \n");
                    sql.append(" WHERE  A.SUBJ = B.SUBJ     \n");
                    sql.append("   AND  A.SUBJ = ':p_subj'  \n");
                    sql.append("   AND  A.SUBJSEQ = ':p_subjseq'    \n");
                    sql.append("   AND  A.YEAR = ':p_year'  \n");

                    ls1 = connMgr.executeQuery(sql.toString(), box);

                    if (ls1 != null && ls1.next()) {
                        dbox = ls1.getDataBox();
                    }

                    ls1.close();
                    ls1 = null;

                    sql.setLength(0);
                    sql.append("/* MemberInfoBean.membeSubjectInfo (사용자 경력 정보조회) */\n");
                    sql.append("SELECT  *                                                   \n");
                    sql.append("  FROM  (                                                   \n");
                    sql.append("        SELECT  SUBJ            \n");
                    sql.append("            ,   YEAR            \n");
                    sql.append("            ,   SUBJSEQ         \n");
                    sql.append("            ,   USERID          \n");
                    sql.append("            ,   YEUNSUNO        \n");
                    sql.append("            ,   YEONSUNM        \n");
                    sql.append("            ,   INTRO           \n");
                    sql.append("            ,   VISION          \n");
                    sql.append("            ,   MOTIVE          \n");
                    sql.append("            ,   ETC             \n");
                    sql.append("            ,   TCAREERYEAR     \n");
                    sql.append("            ,   TCAREERMONTH    \n");
                    sql.append("            ,   RANK() OVER( ORDER BY MOD_DT DESC, SUBJ DESC) AS RNK   \n");
                    sql.append("          FROM  TZ_OFFPROPOSE_ADDINFO                       \n");
                    sql.append("         WHERE  USERID = ':p_userid'                        \n");
                    sql.append("        )                                                   \n");
                    sql.append(" WHERE  RNK = 1                                             \n");

                    ls1 = connMgr.executeQuery(sql.toString(), box);

                    if (ls1 != null && ls1.next()) {
                        dbox.put("d_userid", ls1.getString("userid"));
                        dbox.put("d_yeunsuno", ls1.getString("yeunsuno"));
                        dbox.put("d_yeonsunm", ls1.getString("yeonsunm"));
                        dbox.put("d_intro", ls1.getString("intro"));
                        dbox.put("d_vision", ls1.getString("vision"));
                        dbox.put("d_motive", ls1.getString("motive"));
                        dbox.put("d_etc", ls1.getString("etc"));
                        dbox.put("d_tcareeryear", ls1.getString("tcareeryear"));
                        dbox.put("d_tcareermonth", ls1.getString("tcareermonth"));
                    }

                } else {

                    sql.append("/* MemberInfoBean.membeSubjectInfo (오프라인 수강신청 정보 조회) */\n");
                    sql.append("SELECT                              \n");
                    sql.append("        A.SUBJ                      \n");
                    sql.append("    ,   A.SUBJNM                    \n");
                    sql.append("    ,   B.MIDDLECLASS               \n");
                    sql.append("    ,   A.SUBJSEQ                   \n");
                    sql.append("    ,   A.BIYONG                    \n");
                    sql.append("    ,   A.PROPSTART                 \n");
                    sql.append("    ,   A.PROPEND                   \n");
                    sql.append("    ,   A.EDUSTART                  \n");
                    sql.append("    ,   A.EDUEND                    \n");
                    sql.append("    ,   RPAD(A.NEEDINPUT, 11, '0') AS NEEDINPUT \n");
                    sql.append("    ,   E.YEUNSUNO                  \n");
                    sql.append("    ,   E.YEONSUNM                  \n");
                    sql.append("    ,   E.INTRO                     \n");
                    sql.append("    ,   E.VISION                    \n");
                    sql.append("    ,   E.MOTIVE                    \n");
                    sql.append("    ,   E.ETC                       \n");
                    sql.append("    ,   A.SPECIALS                  \n");
                    sql.append("    ,   E.TCAREERYEAR               \n");
                    sql.append("    ,   E.TCAREERMONTH              \n");
                    sql.append("  FROM  TZ_OFFSUBJSEQ A             \n");
                    sql.append("    ,   VZ_OFFSUBJCLASS B           \n");
                    sql.append("    ,   TZ_OFFPROPOSE_ADDINFO E     \n");
                    sql.append(" WHERE  A.SUBJ = B.SUBJ             \n");
                    sql.append("   AND  A.SUBJ = ':p_subj'          \n");
                    sql.append("   AND  A.SUBJSEQ = ':p_subjseq'    \n");
                    sql.append("   AND  A.YEAR = ':p_year'          \n");
                    sql.append("   AND  A.SUBJ = E.SUBJ(+)          \n");
                    sql.append("   AND  A.SUBJSEQ = E.SUBJSEQ(+)    \n");
                    sql.append("   AND  A.YEAR = E.YEAR(+)          \n");
                    sql.append("   AND  E.USERID(+) = ':p_userid'   \n");

                    ls1 = connMgr.executeQuery(sql.toString(), box);

                    if (ls1 != null && ls1.next()) {
                        dbox = ls1.getDataBox();
                    }

                    //                sql.append(" WHERE  A.SUBJ = B.SUBJ             \n");
                    //                sql.append("   AND  A.SUBJ = ':p_subj'          \n");
                    //                sql.append("   AND  A.SUBJSEQ = ':p_subjseq'    \n");
                    //                sql.append("   AND  A.YEAR = ':p_year'          \n");
                    //                sql.append("   AND  A.SUBJ = E.SUBJ(+)          \n");
                    //                sql.append("   AND  A.SUBJSEQ = E.SUBJSEQ(+)    \n");
                    //                sql.append("   AND  A.YEAR = E.YEAR(+)          \n");
                    //                sql.append("   AND  E.USERID(+) = ':p_userid'   \n");
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
     * 개인의 추가정보를 조회한다.(공통)
     * 
     * @param connMgr
     * @param box
     * @param dbox
     * @return
     * @throws Exception
     */
    public DataBox memberAddInfo(DBConnectionManager connMgr, RequestBox box, DataBox dbox) throws Exception {
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        if (box.get("p_userid").length() == 0)
            box.put("p_userid", box.getSession("userid"));

        try {
            connMgr = new DBConnectionManager();
            /**
             * 학력정보
             */
            sql.append("SELECT\n");
            sql.append("USERID,SEQ,SCHOOLNAME,PLACE,MAJOR,STATUS,(SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0094' AND CODE = A.STATUS) STATUSNM,SSTART,SEND\n");
            sql.append("FROM TZ_MEMBER_STUDYINFO a\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("schoolList", ls1.getAllDataList());
            /**
             * 자격증
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append("SELECT\n");
            sql.append("USERID,SEQ,LICENSENAME,GETDATE,PUBLISH\n");
            sql.append("FROM TZ_MEMBER_LICENSE\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("licenseList", ls1.getAllDataList());
            /**
             * 경력
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append("SELECT\n");
            sql
                    .append("USERID,SEQ,\n(SELECT gubunnm FROM TZ_CODEGUBUN WHERE GUBUN = A.JIKUP) JIKUP,\n(SELECT CODENM FROM TZ_CODE WHERE GUBUN = A.JIKUP AND CODE = A.JIKJONG) JIKJONG,\nOFFICENAME,POSITION, WORKSTART,WORKEND,(SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0095' AND CODE = A.WORKSTATUS) WORKSTATUS\n");
            sql.append("FROM TZ_MEMBER_WORKINFO A\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("careerList", ls1.getAllDataList());

            /**
             * 공모 및 대회 수상 이력
             */
            ls1.close();
            sql.delete(0, sql.length());
            sql.append("SELECT\n");
            sql.append("USERID,SEQ,COMPETITION_NM,COMPETITION_DETAIL,COMPETITION_DATE\n");
            sql.append("FROM TZ_MEMBER_COMPETITION\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("competitionList", ls1.getAllDataList());
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
     * 개인정보 저장(공통)
     * 
     * @param box receive from the form object and session
     * @return is_Ok 1 : update ok 2 : update fail
     * @throws Exception
     */
    public int memberInfoUpdateNew(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql3 = new StringBuffer();

        int onOff = box.getInt("onOff");
        int is_Ok = 0;

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            box.sync("p_comptext");
            box.sync("p_handphone");
            box.sync("p_jikchaeknm");
            box.sync("p_deptnam");

            String privateyn = box.getString("p_privateYn");

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (회사명 갱신) */\n");
            if (privateyn.equals("")) {
                sql3.append("UPDATE TZ_MEMBER   \n");
                sql3.append("   SET COMPTEXT = nvl(':p_comptext', COMPTEXT) \n");
                sql3.append("     , JIKCHAEKNM = nvl(':p_jikchaeknm', JIKCHAEKNM)    \n");
                sql3.append("     , HANDPHONE = nvl(CRYPTO.ENC('normal', ':p_handphone'), HANDPHONE)    \n");
                sql3.append("     , DEPTNAM = nvl(':p_deptnam', DEPTNAM)    \n");
                sql3.append(" WHERE USERID = ':s_userid'    \n");
            } else {
                sql3.append("UPDATE TZ_MEMBER   \n");
                sql3.append("   SET COMPTEXT = nvl(':p_comptext', COMPTEXT) \n");
                sql3.append("     , JIKCHAEKNM = nvl(':p_jikchaeknm', JIKCHAEKNM)    \n");
                sql3.append("     , HANDPHONE = nvl(CRYPTO.ENC('normal', ':p_handphone'), HANDPHONE)    \n");
                sql3.append("     , DEPTNAM = nvl(':p_deptnam', DEPTNAM)    \n");
                sql3.append("   ,   PRIVATE_YESNO = ':p_privateYn'  \n");
                sql3.append(" WHERE USERID = ':s_userid'    \n");
            }

            pstmt = connMgr.prepareStatement(sql3.toString(), box);
            pstmt.executeUpdate();
            pstmt.close();

            // 1. p_militarystatus
            box.sync("p_militarystatus");
            box.sync("p_militarymemo");
            box.sync("p_militarystart");
            box.sync("p_militaryend");

            sql3.setLength(0);

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (병역정보 삭제) */\n");
            sql3.append(" DELETE TZ_MEMBER_MILITARY WHERE USERID = ':s_userid' ");

            pstmt = connMgr.prepareStatement(sql3.toString(), box);
            pstmt.executeUpdate();
            pstmt.close();

            sql3.setLength(0);

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (병역정보 신규 등록) */\n");
            sql3.append("INSERT INTO TZ_MEMBER_MILITARY (   \n");
            sql3.append("       USERID          \n");
            sql3.append("   ,   MILITARYSTATUS  \n");
            sql3.append("   ,   MILITARYSTART   \n");
            sql3.append("   ,   MILITARYEND     \n");
            sql3.append("   ,   MILITARYMEMO    \n");
            sql3.append("   ) VALUES (          \n");
            sql3.append("       ':s_userid'     \n");
            sql3.append("   ,   ':p_militarystatus' \n");
            sql3.append("   ,   ':p_militarystart'  \n");
            sql3.append("   ,   ':p_militaryend'    \n");
            sql3.append("   ,   ':p_militarymemo'   \n");
            sql3.append("   )    \n");

            pstmt = connMgr.prepareStatement(sql3.toString(), box);
            pstmt.executeUpdate();
            pstmt.close();

            // 1.end

            sql3.setLength(0);

            if (box.get("p_subj").length() > 0) {

                if (onOff == 1) {
                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (온라인과정 신청 추가 정보 삭제) */\n");
                    sql3.append("DELETE TZ_PROPOSE_ADDINFO  \n");
                    sql3.append(" WHERE SUBJ = ':p_subj'    \n");
                    sql3.append("   AND YEAR = ':p_year'    \n");
                    sql3.append("   AND SUBJSEQ = ':p_subjseq'  \n");
                    sql3.append("   AND USERID = ':s_userid'    \n");
                } else {
                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (오프라인과정 신청 추가 정보 삭제) */\n");
                    sql3.append("DELETE TZ_OFFPROPOSE_ADDINFO   \n");
                    sql3.append(" WHERE SUBJ = ':p_subj'        \n");
                    sql3.append("   AND YEAR = ':p_year'        \n");
                    sql3.append("   AND SUBJSEQ = ':p_subjseq'  \n");
                    sql3.append("   AND USERID = ':s_userid'    \n");
                }

                pstmt = connMgr.prepareStatement(sql3.toString(), box);
                pstmt.executeUpdate();
                pstmt.close();

                sql3.setLength(0);

                if (onOff == 1) {

                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (온라인과정 신청 추가 정보 입력) */\n");
                    sql3.append("INSERT INTO TZ_PROPOSE_ADDINFO (   \n");
                    sql3.append("       SUBJ    \n");
                    sql3.append("   ,   YEAR    \n");
                    sql3.append("   ,   SUBJSEQ \n");
                    sql3.append("   ,   USERID  \n");

                    // 2.연수 /자기소개
                    if (!box.getString("p_yeunsuno").equals("")) {
                        sql3.append("   ,   YEUNSUNO    \n");
                        sql3.append("   ,   YEONSUNM    \n");
                    }
                    if (!box.getString("p_intro").equals("")) {
                        sql3.append("   ,   INTRO   \n");
                        sql3.append("   ,   VISION  \n");
                        sql3.append("   ,   ETC     \n");
                        sql3.append("   ,   MOTIVE  \n");
                    }
                    if (!box.getString("p_tcareeryear").equals("")) {
                        sql3.append("   ,   TCAREERYEAR \n");
                    }
                    if (!box.getString("p_tcareermonth").equals("")) {
                        sql3.append("   ,   TCAREERMONTH    \n");
                    }
                    sql3.append("   ,   REG_DT  \n");
                    sql3.append("   ,   MOD_DT  \n");

                    sql3.append(") VALUES ( \n");
                    sql3.append("       ':p_subj'   \n");
                    sql3.append("   ,   ':p_year'   \n");
                    sql3.append("   ,   ':p_subjseq'\n");
                    sql3.append("   ,   ':s_userid' \n");

                    if (!box.getString("p_yeunsuno").equals("")) {
                        sql3.append("   ,   ':p_yeunsuno'   \n");
                        sql3.append("   ,   ':p_yeonsunm'   \n");
                    }
                    if (!box.getString("p_intro").equals("")) {
                        sql3.append("   ,   ':p_intro'  \n");
                        sql3.append("   ,   ':p_vision' \n");
                        sql3.append("   ,   ':p_etc'    \n");
                        sql3.append("   ,   ':p_motive' \n");
                    }
                    if (!box.getString("p_tcareeryear").equals("")) {
                        sql3.append("   ,   ':p_tcareeryear'    \n");
                    }
                    if (!box.getString("p_tcareermonth").equals("")) {
                        sql3.append("   ,   ':p_tcareermonth'   \n");
                    }

                    sql3.append("   ,   SYSDATE  \n");
                    sql3.append("   ,   SYSDATE  \n");
                    sql3.append(")  \n");

                } else {

                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (오프라인과정 신청 추가 정보 입력) */\n");
                    sql3.append("INSERT INTO TZ_OFFPROPOSE_ADDINFO (    \n");
                    sql3.append("       SUBJ    \n");
                    sql3.append("   ,   YEAR    \n");
                    sql3.append("   ,   SUBJSEQ \n");
                    sql3.append("   ,   USERID  \n");

                    if (!box.getString("p_yeunsuno").equals("")) {
                        sql3.append("   ,   YEUNSUNO    \n");
                        sql3.append("   ,   YEONSUNM    \n");
                    }
                    if (!box.getString("p_intro").equals("")) {
                        sql3.append("   ,   INTRO   \n");
                        sql3.append("   ,   VISION  \n");
                        sql3.append("   ,   ETC     \n");
                        sql3.append("   ,   MOTIVE  \n");
                    }
                    if (!box.getString("p_tcareeryear").equals("")) {
                        sql3.append("   ,   TCAREERYEAR \n");
                    }
                    if (!box.getString("p_tcareermonth").equals("")) {
                        sql3.append("   ,   TCAREERMONTH    \n");
                    }

                    sql3.append("   ,   REG_DT  \n");
                    sql3.append("   ,   MOD_DT  \n");

                    sql3.append(") VALUES ( \n");
                    sql3.append("       ':p_subj'   \n");
                    sql3.append("   ,   ':p_year'   \n");
                    sql3.append("   ,   ':p_subjseq'\n");
                    sql3.append("   ,   ':s_userid' \n");

                    if (!box.getString("p_yeunsuno").equals("")) {
                        sql3.append("   ,   ':p_yeunsuno'   \n");
                        sql3.append("   ,   ':p_yeonsunm'   \n");
                    }

                    if (!box.getString("p_intro").equals("")) {
                        sql3.append("   ,   ':p_intro'  \n");
                        sql3.append("   ,   ':p_vision' \n");
                        sql3.append("   ,   ':p_etc'    \n");
                        sql3.append("   ,   ':p_motive' \n");
                    }

                    if (!box.getString("p_tcareeryear").equals("")) {
                        sql3.append("   ,  ':p_tcareeryear' \n");
                    }
                    if (!box.getString("p_tcareermonth").equals("")) {
                        sql3.append("   ,   ':p_tcareermonth'   \n");
                    }

                    sql3.append("   ,   SYSDATE  \n");
                    sql3.append("   ,   SYSDATE  \n");

                    sql3.append(")  \n");

                }

                pstmt = connMgr.prepareStatement(sql3.toString(), box);
                pstmt.executeUpdate();
                pstmt.close();

                sql3.setLength(0);

            }

            is_Ok = 1;
            connMgr.commit();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3.toString());
            throw new Exception("sql = " + sql3.toString() + "\r\n" + ex.getMessage());
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
        String sql = "";
        String sql2 = "";
        int is_Ok = 0;

        String v_handphone = box.getString("p_handphone");
        // String v_userid = box.getString("p_userid");
        String v_userid = box.getSession("userid");
        String v_pwd = box.getString("p_pwd");
        String v_eng_name = box.getString("p_eng_name");
        String v_post1 = box.getString("p_post1");
        String v_post2 = box.getString("p_post2");
        String v_addr = box.getString("p_home_addr1");
        String v_addr2 = box.getString("p_home_addr2");
        String v_comp_post1 = box.getString("p_comp_post1");
        String v_comp_post2 = box.getString("p_comp_post2");
        String v_comp_addr1 = box.getString("p_comp_addr1");
        String v_comp_addr2 = box.getString("p_comp_addr2");
        String v_email = box.getString("p_email");
        String v_comptext = box.getString("p_comptext");
        String v_jikup = box.getString("p_jikup");
        String v_degree = box.getString("p_degree");
        String v_hometel = box.getString("p_hometel");
        String v_comptel = box.getString("p_comptel");
        String v_ismailing = box.getString("p_ismailing");
        String v_islettering = box.getString("p_islettering");
        String v_isopening = box.getString("p_isopening");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_MEMBER set   ";
            sql += "    pwd = ?, email = ?, post1 = ?, post2 = ?, addr = ?, addr2 = ?,  ";
            sql += "    hometel = ?, handphone = ?, comptel = ?,  comp_post1= ?,  ";
            sql += "    comp_post2 = ?, comp_addr1 = ?, comp_addr2 = ?, ";
            sql += "    comptext = ?, jikup = ?, degree = ?,  ismailing= ?, islettering= ?, isopening= ?, ";
            sql += "    ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') , eng_name = ?";
            sql += " where userid = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_pwd);
            pstmt.setString(2, v_email);
            pstmt.setString(3, v_post1);
            pstmt.setString(4, v_post2);
            pstmt.setString(5, v_addr);
            pstmt.setString(6, v_addr2);
            pstmt.setString(7, v_hometel);
            pstmt.setString(8, v_handphone);
            pstmt.setString(9, v_comptel);
            pstmt.setString(10, v_comp_post1);
            pstmt.setString(11, v_comp_post2);
            pstmt.setString(12, v_comp_addr1);
            pstmt.setString(13, v_comp_addr2);
            pstmt.setString(14, v_comptext);
            pstmt.setString(15, v_jikup);
            pstmt.setString(16, v_degree);
            pstmt.setString(17, v_ismailing);
            pstmt.setString(18, v_islettering);
            pstmt.setString(19, v_isopening);
            pstmt.setString(20, v_eng_name);
            pstmt.setString(21, v_userid);

            is_Ok = pstmt.executeUpdate();

            // update TZ_TUTOR table
            sql2 = " update TZ_TUTOR ";
            sql2 += " set     post1=?,";
            sql2 += "        post2=?, ";
            sql2 += "        add1=?, ";
            sql2 += "        add2=?, ";
            sql2 += "        phone=?, ";
            sql2 += "        handphone=?, ";
            sql2 += "        email=?, ";
            sql2 += "        luserid=?,";
            sql2 += "   ldate=to_char(sysdate, 'YYYYMMDD') where userid=? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_post1);
            pstmt2.setString(2, v_post2);
            pstmt2.setString(3, v_addr);
            pstmt2.setString(4, v_addr2);
            pstmt2.setString(5, v_comptel);
            pstmt2.setString(6, v_handphone);
            pstmt2.setString(7, v_email);
            pstmt2.setString(8, v_userid);
            pstmt2.setString(9, v_userid);

            pstmt2.executeUpdate();

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
     * 직위 셀렉트 박스
     * 
     * @param userid 유저아이디
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @return
     * @throws Exception
     */
    public static String getJikwiSelect(String name, String selected, String event) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        int cnt = 0;

        result = "  <SELECT name='" + name + "' " + event + "  style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 120px;height:19px;font-size:9pt;'> \n";
        result += "  <option value=''> == 선택 == </option>";

        try {
            connMgr = new DBConnectionManager();

            sql = " select jikwi, jikwinm    ";
            sql += "   from tz_jikwi                   ";
            sql += "  where grpcomp        = 'ZZZZ'    ";
            sql += " order by jikwi asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                result += " <option value=" + ls.getString("jikwi");
                if (selected.equals(ls.getString("jikwi"))) {
                    result += " selected ";
                }
                result += ">" + ls.getString("jikwinm") + "</option> \n";
                cnt++;
            }
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
        String sql = "";
        int cnt = 0;

        result = "  <SELECT name='" + name + "' " + event + "  style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 120px;height:19px;font-size:9pt;'> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select b.gadmin gadmin, b.gadminnm gadminnm    ";
            sql += "   from tz_manager a, tz_gadmin b               ";
            sql += "  where a.gadmin    = b.gadmin                  ";
            sql += "    and a.userid    = " + StringManager.makeSQL(userid);
            sql += "    and a.isdeleted = 'N'                       ";
            sql += "    and to_char(sysdate,'yyyymmdd') between a.fmon and a.tmon ";
            sql += " order by b.gadmin asc";

            ls = connMgr.executeQuery(sql);

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
     * 개인정보 조회
     * 
     * @param box receive from the form object and session
     * @return DataBox 개인정보
     * @throws Exception
     */
    public DataBox getMemberInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " select userid, resno, name, email, pwd, eng_name, ";
            sql += "        post1, post2, addr, addr2, hometel, handphone, comptel,  validation,     ";
            sql += "        comptext, comp_addr1, comp_addr2, comp_post1, comp_post2, degree, jikup, ";
            sql += "          ismailing, isopening, islettering, lgfirst, lgcnt, lglast ";
            sql += "   from TZ_MEMBER                                               ";
            sql += "  where userid = " + StringManager.makeSQL(v_userid);

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
     * 학습자가 수강신청을 할 때, 해당 과정의 수강신청 가능여부를 확인한다. 학습자가 수강신청 창을 장시간 열어 놓아도 세션이 종료되지
     * 않아 수강신청기간이 지난 후에도 신청이 되어 있는 경우가 발생하고 있다. 이를 미연에 방지하고자 수강신청 프로세스를 진행하기 전에
     * 현재 일자로 수강신청 여부를 확인한다. 참고로 사이트 세션 만료 시간이 설정되지 않은 것으로 보인다.
     * 
     * @param grCode
     * @param gYear
     * @param grSeq
     * @return
     * @throws Exception
     */
    private boolean isPossibleSubjApplyByDate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sb = new StringBuilder();

        boolean isPossibleApply = false;
        try {
            connMgr = new DBConnectionManager();

            sb.append("/* MemberInfoBean.isPossibleSubjApplyByDate (수강신청 가능 여부 확인 쿼리) */ \n");
            sb.append("SELECT  COUNT(A.GRSEQ) AS POSSIBLE_CNT                   \n");
            sb.append("  FROM  TZ_GRSEQ A                                       \n");
            sb.append("    ,   TZ_SUBJSEQ B                                     \n");
            sb.append(" WHERE  A.GRCODE = '" + box.getSession("tem_grcode") + "'\n");
            sb.append("   AND  A.GYEAR = '" + box.getString("p_year") + "'      \n");
            // sb.append("   AND  A.GRSEQ = '" + box.getString("grseq") + "'    \n");
            sb.append("   AND  B.SUBJ = '" + box.getString("p_subj") + "'       \n");
            sb.append("   AND  B.SUBJSEQ = '" + box.getString("p_subjseq") + "' \n");
            sb.append("   AND  A.HOMEPAGEYN = 'Y'                               \n");
            sb.append("   AND  A.STAT = 'Y'                                     \n");
            sb.append("   AND  A.GRCODE = B.GRCODE                              \n");
            sb.append("   AND  A.GYEAR = B.GYEAR                                \n");
            sb.append("   AND  A.GRSEQ = B.GRSEQ                                \n");
            sb.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.PROPSTART AND B.PROPEND     \n");

            ls = connMgr.executeQuery(sb.toString());
            ls.next();

            isPossibleApply = (ls.getInt("possible_cnt") > 0) ? true : false;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
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

        return isPossibleApply;
    }

    /**
     * 오프라인 과정중에서 참가정보를 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox selectOffApplyInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        DataBox dbox = null;

        String year = box.getString("p_year");
        String subj = box.getString("p_subj");
        String subjseq = box.getString("p_subjseq");
        String userid = box.getStringDefault("p_userid", box.getSession("userid"));
        StringBuilder sql = new StringBuilder();

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  APPLY_NAME          \n");
            sql.append("    ,   APPLY_SESSION       \n");
            sql.append("    ,   APPLY_BELONG_TITLE  \n");
            sql.append("    ,   NICKNAME            \n");
            sql.append("    ,   PRIVATE_SNS         \n");
            sql.append("  FROM  TZ_OFFPROPOSE   \n");
            sql.append(" WHERE  YEAR = '").append(year).append("' \n");
            sql.append("   AND  SUBJ = '").append(subj).append("' \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");
            sql.append("   AND  USERID = '").append(userid).append("' \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                dbox = ls.getDataBox();
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return dbox;
    }
}
