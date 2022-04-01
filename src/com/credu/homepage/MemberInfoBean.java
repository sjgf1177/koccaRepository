// **********************************************************
// 1. �� ��: �α����
// 2. ���α׷��� : MemberInfoBean.java
// 3. �� ��: �α���,�н�����ã��
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 7. 2
// 7. �� ��:
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
     * Login ���� ���� (lgcnt:�α���Ƚ��, lglast:�����α��νð�, lgip:�α���ip
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
     * �������� ��ȸ
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
     * ���� ��û�� �Ѵ�.
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

            // ������û ���� ���� ������ ������û â�� ������� ���¿��� �ƹ��� �ൿ�� ���� �ʰ�,
            // ������û ���� �ð��� ���� �Ŀ� ��û ��ư�� Ŭ���� ��� ������û�� ���� �ʵ��� ����
            if (!isPossibleSubjApplyByDate(box)) {
                isOk = 4;
            } else {

                connMgr = new DBConnectionManager();

                // ���� ���� ���� , ��/���� ���� �Ǵ�

                sql.append("/* MemberInfoBean.memberSubjLimit(���� ���� ���� , ��/���� ���� �Ǵ�) */    \n");
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
                    // int fsubj_cnt = Integer.parseInt(cs.getProperty("kocca.freesubj.cnt")); // ������ ��û ���Ѽ�

                    // v_grseq = ls1.getString("grseq");
                    v_studentlimit = ls1.getInt("STUDENTLIMIT");

                    int fsubj_cnt = 1; // ������ ��û ���Ѽ�

                    String grcode = box.getSession("tem_grcode");

                    // if( v_biyong.equals("0") && grcode.equals("N000001"))
                    if (grcode.equals("N000001") || grcode.equals("N000055") 
                    //		|| grcode.equals("N000030") ���ı�û ��û ���Ѽ� ����
                    		|| grcode.equals("N000058") || grcode.equals("N000060") || grcode.equals("N000061") || grcode.equals("N000063") || grcode.equals("N000018")
                            || grcode.equals("N000064") || grcode.equals("N000022") || grcode.equals("N000081") || grcode.equals("N000083")) {
                        int usubj_cnt = 0;

                        if (grcode.equals("N000001")) {
                            fsubj_cnt = 5; // �ѱ���������ī����. 
                                           // ���� �� 1ȸ 1���� ������ 3���������� �� 2���� ���� 3���������� ����(2014�� 7�� 1��)
                                           // �� 2���� ������ 5�� �������� ���� (2014�� 11�� 1��) 
                            
                        } else if (grcode.equals("N000022")) {
                            fsubj_cnt = 5; // ��ȭü��������
                            
                        } else if (grcode.equals("N000083")) {
                            fsubj_cnt = 3; // �ѱ���ȭ����������б� 2014�� 10�� 22�� ���������� ������ 1������ 3���� ����
                            
                        } else {
                            fsubj_cnt = 2; // �� �� if ���� �ɸ� ��� ASP ����Ʈ
                        }

                        // ���������� �� á���� ���θ� Ȯ��
                        sql.setLength(0);

                        sql.append("/* MemberInfoBean.memberSubjLimit (�������� üũ) */\n");
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
                        //                        // sql.append("    and substr(PROPSTART,1,6)=to_char(sysdate,'YYYYMM') \n"); //������û�Ⱓ�� �ø��ų� �ϸ� �ȵ� ����
                        //                        // sql.append("    and substr(PROPEND,1,6)=to_char(sysdate,'YYYYMM') \n");
                        //                        sql.append("    AND B.YEAR = " + SQLString.Format(year) + " \n");
                        //                        sql.append("    and a.grseq = " + SQLString.Format(v_grseq) + " \n"); // �� ���� ���� ������û�� 3���������� ���� �� �Ǿ�����..where �� ���ǿ� grseq(����)�� �߰���.
                        //

                        sql.setLength(0);
                        sql.append("/* com.credu.homepage.MemberInfoBean memberSubjLimit (������û�Ǽ� ��ȸ) */   \n");
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
                        // sql.append("/* (������û�Ǽ� ��ȸ) */           \n");
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
                            // B2C�� ��� 2014�� 7���� �������� �� 2���� ���� � ����
                            // �н��ڴ� ���� ������ ���� ������ ���Ͽ� 3�������� ��û ����
                            // sql.append("   AND  ( ( V1.PREV_GYEAR = C.GYEAR AND  V1.PREV_GRSEQ = C.GRSEQ)   \n");
                            // sql.append("        OR ( V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ) )          \n");
                        // } else {
                            // B2B�� ���� ��ϴ� ������� ����
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
                            // ���� ������û�� ���� ���� ���� ��û�� �������� ���ؾ� �ϱ� ������ +1�� �Ѵ�.
                            if ((usubj_cnt + 1) > fsubj_cnt) {
                                if (grcode.equals("N000001")) {
                                    isOk = 2; // �ѱ�������
                                } else {
                                    isOk = 3; // Ȩ�ؼ��� ���ı�û �����ذ�
                                }
                            }
                        }
                        sql.setLength(0);
                    } else { // �������
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
     * ������û - �����߰����� ��ȸ
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
             * �⺻����
             */
            sql.append("/* MemberInfoBean memberInfoViewNew (���� ��û�� ����� �⺻���� ��ȸ) */\n");
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
            sql.append("    ,   DECODE(D.SEX, '1', '��', '2', '��', '�̵�� ') AS SEX           \n");
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
                    // �������� ��ȣȭ-HTJ
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
     * ������ ������������ �߰������� ��ȸ�Ѵ�.(�¶��ΰ� ������������ ����)
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

            if (onOff == 1) { // �¶��� ����

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

            } else { // �������� ����

                if (process.equals("SubjectEduProposePage")) { // �������ΰ��� ��û ȭ��

                    sql.append("/* MemberInfoBean.membeSubjectInfo (�������� ������û ���� ��ȸ) */\n");
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
                    sql.append("/* MemberInfoBean.membeSubjectInfo (����� ��� ������ȸ) */\n");
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

                    sql.append("/* MemberInfoBean.membeSubjectInfo (�������� ������û ���� ��ȸ) */\n");
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
     * ������ �߰������� ��ȸ�Ѵ�.(����)
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
             * �з�����
             */
            sql.append("SELECT\n");
            sql.append("USERID,SEQ,SCHOOLNAME,PLACE,MAJOR,STATUS,(SELECT CODENM FROM TZ_CODE WHERE GUBUN = '0094' AND CODE = A.STATUS) STATUSNM,SSTART,SEND\n");
            sql.append("FROM TZ_MEMBER_STUDYINFO a\n");
            sql.append("WHERE USERID = ':p_userid'\n");
            ls1 = connMgr.executeQuery(sql.toString(), box);
            dbox.put("schoolList", ls1.getAllDataList());
            /**
             * �ڰ���
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
             * ���
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
             * ���� �� ��ȸ ���� �̷�
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
     * �������� ����(����)
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

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (ȸ��� ����) */\n");
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

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (�������� ����) */\n");
            sql3.append(" DELETE TZ_MEMBER_MILITARY WHERE USERID = ':s_userid' ");

            pstmt = connMgr.prepareStatement(sql3.toString(), box);
            pstmt.executeUpdate();
            pstmt.close();

            sql3.setLength(0);

            sql3.append("/* MemberInfoBean memberInfoUpdateNew (�������� �ű� ���) */\n");
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
                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (�¶��ΰ��� ��û �߰� ���� ����) */\n");
                    sql3.append("DELETE TZ_PROPOSE_ADDINFO  \n");
                    sql3.append(" WHERE SUBJ = ':p_subj'    \n");
                    sql3.append("   AND YEAR = ':p_year'    \n");
                    sql3.append("   AND SUBJSEQ = ':p_subjseq'  \n");
                    sql3.append("   AND USERID = ':s_userid'    \n");
                } else {
                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (�������ΰ��� ��û �߰� ���� ����) */\n");
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

                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (�¶��ΰ��� ��û �߰� ���� �Է�) */\n");
                    sql3.append("INSERT INTO TZ_PROPOSE_ADDINFO (   \n");
                    sql3.append("       SUBJ    \n");
                    sql3.append("   ,   YEAR    \n");
                    sql3.append("   ,   SUBJSEQ \n");
                    sql3.append("   ,   USERID  \n");

                    // 2.���� /�ڱ�Ұ�
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

                    sql3.append("/* MemberInfoBean memberInfoUpdateNew (�������ΰ��� ��û �߰� ���� �Է�) */\n");
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
     * �������� ����
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
     * ���� ����Ʈ �ڽ�
     * 
     * @param userid �������̵�
     * @param name ����Ʈ�ڽ���
     * @param selected ���ð�
     * @param event �̺�Ʈ��
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
        result += "  <option value=''> == ���� == </option>";

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
     * ���� ����Ʈ �ڽ�
     * 
     * @param userid �������̵�
     * @param name ����Ʈ�ڽ���
     * @param selected ���ð�
     * @param event �̺�Ʈ��
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

        result += ">�н���</option>";
        result += "  </SELECT> \n";

        if (cnt == 0)
            result = "";
        return result;
    }

    /**
     * �������� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return DataBox ��������
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
     * �н��ڰ� ������û�� �� ��, �ش� ������ ������û ���ɿ��θ� Ȯ���Ѵ�. �н��ڰ� ������û â�� ��ð� ���� ���Ƶ� ������ �������
     * �ʾ� ������û�Ⱓ�� ���� �Ŀ��� ��û�� �Ǿ� �ִ� ��찡 �߻��ϰ� �ִ�. �̸� �̿��� �����ϰ��� ������û ���μ����� �����ϱ� ����
     * ���� ���ڷ� ������û ���θ� Ȯ���Ѵ�. ����� ����Ʈ ���� ���� �ð��� �������� ���� ������ ���δ�.
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

            sb.append("/* MemberInfoBean.isPossibleSubjApplyByDate (������û ���� ���� Ȯ�� ����) */ \n");
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
     * �������� �����߿��� ���������� ��ȸ�Ѵ�.
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
