//**********************************************************
//  1. ��      ��: ����OPERATION BEAN
//  2. ���α׷���: SubjectBean.java
//  3. ��      ��: ����OPERATION BEAN
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 11. 14
//  7. ��      ��:
//**********************************************************
package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.SelectionData;

@SuppressWarnings("unchecked")
public class SubjectBean {

    public final static String LANGUAGE_GUBUN = "0017";
    public final static String ONOFF_GUBUN = "0004";

    public SubjectBean() {
    }

    /**
     * ��������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��������Ʈ
     */
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuilder sql = new StringBuilder();
        SubjectData data = null;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����ְ�
        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //�����з�
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //����&�ڽ�
        String ss_specials = box.getStringDefault("s_specials", "ALL"); //����Ư��
        String ss_area = box.getStringDefault("s_area", "ALL");

        String v_orderColumn = box.getString("p_orderColumn"); //������ �÷���
        String v_orderType = box.getString("p_orderType"); //������ ����

        try {
            if (ss_grcode.equals("ALL")) {
                sql.append("/* cme.credu.course.SubjectBean SelectSubjectList (������ ��������Ʈ ��ȸ) */");
                sql.append("SELECT  A.UPPERCLASS    \n");
                sql.append("    ,   B.CLASSNAME     \n");
                sql.append("    ,   A.ISONOFF       \n");
                sql.append("    ,   C.CODENM        \n");
                sql.append("    ,   A.SUBJ          \n");
                sql.append("    ,   A.SUBJNM        \n");
                sql.append("    ,   A.MUSERID       \n");
                sql.append("    ,   (               \n");
                sql.append("        SELECT  NAME    \n");
                sql.append("          FROM  TZ_MEMBER   \n");
                sql.append("         WHERE  USERID = A.MUSERID  \n");
                sql.append("           AND  GRCODE = 'N000001'  \n");
                sql.append("        ) AS NAME       \n");
                sql.append("    ,   A.ISUSE         \n");
                sql.append("    ,   '' GRCODE       \n");
                sql.append("    ,   A.ISAPPROVAL    \n");
                sql.append("    ,   A.ISINTRODUCTION    \n");
                sql.append("    ,   A.INTRODUCEFILENAMEREAL \n");
                sql.append("    ,   A.INTRODUCEFILENAMENEW  \n");
                sql.append("    ,   A.INFORMATIONFILENAMEREAL   \n");
                sql.append("    ,   A.INFORMATIONFILENAMENEW    \n");
                sql.append("    ,   A.SUBMAINFILENAMEREAL   \n");
                sql.append("    ,   A.SUBMAINFILENAMENEW    \n");
                sql.append("    ,   GET_CODENM('0101', A.AREA) AS AREA  \n");
                sql.append("    ,   A.WJ_CLASSKEY   \n");
                sql.append("    ,   A.SEARCH_NM     \n");
                sql.append("    ,   A.MOBILE_USE_YN \n");
                sql.append("  FROM  TZ_SUBJ A       \n");
                sql.append("    ,   TZ_SUBJATT B    \n");
                sql.append("    ,   TZ_CODE C       \n");
                sql.append(" WHERE  A.UPPERCLASS = B.UPPERCLASS \n");
                sql.append("   AND  A.ISONOFF = C.CODE  \n");
                sql.append("   AND  B.MIDDLECLASS = '000'   \n");
                sql.append("   AND  B.LOWERCLASS = '000'    \n");
                sql.append("   AND  C.GUBUN = '").append(ONOFF_GUBUN).append("'     \n");
            } else {
                sql.append("SELECT  A.UPPERCLASS    \n");
                sql.append("    ,   B.CLASSNAME     \n");
                sql.append("    ,   A.ISONOFF       \n");
                sql.append("    ,   C.CODENM        \n");
                sql.append("    ,   A.SUBJ          \n");
                sql.append("    ,   A.SUBJNM        \n");
                sql.append("    ,   A.MUSERID       \n");
                sql.append("    ,   (               \n");
                sql.append("        SELECT  NAME    \n");
                sql.append("          FROM  TZ_MEMBER   \n");
                sql.append("         WHERE  USERID = A.MUSERID  \n");
                sql.append("           AND  GRCODE = 'N000001'  \n");
                sql.append("        ) AS NAME \n");
                sql.append("    ,   A.ISUSE         \n");
                sql.append("    ,   E.GRCODE        \n");
                sql.append("    ,   A.ISAPPROVAL    \n");
                sql.append("    ,   A.ISINTRODUCTION    \n");
                sql.append("    ,   A.INTRODUCEFILENAMEREAL \n");
                sql.append("    ,   A.INTRODUCEFILENAMENEW  \n");
                sql.append("    ,   A.INFORMATIONFILENAMEREAL   \n");
                sql.append("    ,   A.INFORMATIONFILENAMENEW    \n");
                sql.append("    ,   A.SUBMAINFILENAMEREAL   \n");
                sql.append("    ,   A.SUBMAINFILENAMENEW    \n");
                sql.append("    ,   GET_CODENM('0101', A.AREA) AS AREA  \n");
                sql.append("    ,   A.WJ_CLASSKEY   \n");
                sql.append("    ,   A.SEARCH_NM     \n");
                sql.append("    ,   A.MOBILE_USE_YN \n");
                sql.append("  FROM  TZ_SUBJ A       \n");
                sql.append("    ,   TZ_SUBJATT B    \n");
                sql.append("    ,   TZ_CODE C       \n");
                sql.append("    ,   TZ_GRSUBJ E     \n");
                sql.append(" WHERE  A.UPPERCLASS  = B.UPPERCLASS    \n");
                sql.append("   AND  A.ISONOFF = C.CODE      \n");
                sql.append("   AND  B.MIDDLECLASS = '000'   \n");
                sql.append("   AND  B.LOWERCLASS = '000'    \n");
                sql.append("   AND  A.SUBJ = E.SUBJCOURSE   \n");
                sql.append("   AND  C.GUBUN = '").append(ONOFF_GUBUN).append("' \n");
            }

            //�����׷�
            if (isNotAll(ss_grcode)) {
                sql.append("   AND  E.GRCODE = '").append(ss_grcode).append("' \n");
            }

            if (!ss_subjcourse.equals("ALL")) {
                sql.append("   AND  A.SUBJ = '").append(ss_subjcourse).append("' \n");
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL")) {
                        sql.append("   AND  A.UPPERCLASS = '").append(ss_upperclass).append("' \n");
                    }
                    if (!ss_middleclass.equals("ALL")) {
                        sql.append("   AND  A.MIDDLECLASS = '").append(ss_middleclass).append("' \n");
                    }
                    if (!ss_lowerclass.equals("ALL")) {
                        sql.append("   AND  A.LOWERCLASS = '").append(ss_lowerclass).append("' \n");
                    }
                }
            }
            if (isNotAll(ss_specials)) {
                sql.append("   AND  A.SPECIALS LIKE '").append(ss_specials).append("' \n");
            }
            if (isNotAll(ss_area)) {
                sql.append("   AND  A.AREA = '").append(box.get("s_area")).append("' \n");
            }

            if (v_orderColumn.equals("")) {
                sql.append(" ORDER  BY A.UPPERCLASS, A.SUBJ \n");
            } else {
                sql.append(" ORDER  BY ").append(v_orderColumn).append(" " ).append(v_orderType);
            }

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new SubjectData();
                data.setUpperclass(ls.getString("upperclass"));
                data.setClassname(ls.getString("classname"));
                data.setIsonoff(ls.getString("isonoff"));
                data.setCodenm(ls.getString("codenm"));
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setName(ls.getString("name"));
                data.setIsuse(ls.getString("isuse"));
                data.setIsapproval(ls.getString("isapproval"));

                data.setIsintroduction(ls.getString("isintroduction"));
                data.setIntroducefilenamereal(ls.getString("introducefilenamereal"));
                data.setIntroducefilenamenew(ls.getString("introducefilenamenew"));
                data.setInformationfilenamereal(ls.getString("informationfilenamereal"));
                data.setInformationfilenamenew(ls.getString("informationfilenamenew"));

                data.setArea(ls.getString("area"));
                data.setMobileUseYn( ls.getString("mobile_use_yn") );
                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

    private boolean isAll(String param) {
        return "ALL".equals(param);
    }

    private boolean isNotAll(String param) {
        return !isAll(param);
    }

    /**
     * ��������Ÿ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��������Ÿ
     */
    public SubjectData SelectSubjectData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        SubjectData data = null;

        StringBuffer sql = new StringBuffer();

        String v_subj = box.getString("p_subj");

        try {

            connMgr = new DBConnectionManager();

            sql.append("SELECT  (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS=A.UPPERCLASS AND MIDDLECLASS='000' AND LOWERCLASS='000') UPPERCLASSNM, \n");
            sql.append("        (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS=A.UPPERCLASS AND MIDDLECLASS=A.MIDDLECLASS AND LOWERCLASS='000') MIDDLECLASSNM, \n");
            sql.append("        (SELECT CLASSNAME FROM TZ_SUBJATT WHERE UPPERCLASS=A.UPPERCLASS AND MIDDLECLASS=A.MIDDLECLASS AND LOWERCLASS=A.LOWERCLASS) LOWERCLASSNM, \n");
            sql.append("        A.SUBJ,       A.SUBJNM,         A.ISONOFF,        A.SUBJCLASS,                     \n");
            sql.append("        A.UPPERCLASS, A.MIDDLECLASS,    A.LOWERCLASS,     A.SPECIALS,      A.CONTENTTYPE,  \n");
            sql.append("        A.MUSERID,    A.MUSERTEL,       A.CUSERID,        A.ISUSE,         A.ISGOYONG,     \n");
            sql.append("        A.ISPROPOSE,  A.BIYONG,         A.EDUDAYS,        A.STUDENTLIMIT,  A.USEBOOK,      \n");
            sql.append("        A.BOOKPRICE,  A.OWNER,          A.PRODUCER,       A.CRDATE,        A.LANGUAGE,   \n");
            sql.append("        A.SERVER,     A.DIR,            A.EDUURL,         A.VODURL,        A.PREURL,     \n");
            sql.append("        A.RATEWBT,    A.RATEVOD,        A.FRAMECNT,       A.ENV,                         \n");
            sql.append("        A.TUTOR,      A.BOOKNAME,       A.SDESC,          A.WARNDAYS,      A.STOPDAYS,   \n");
            sql.append("        A.POINT,      A.EDULIMIT,       A.GRADSCORE,      A.GRADSTEP,                 \n");
            sql.append("        A.WSTEP,      A.WMTEST,         A.WFTEST,         A.WREPORT,                  \n");
            sql.append("        A.WACT,       A.WETC1,          A.WETC2,          A.GOYONGPRICE,              \n");
            sql.append("        A.PLACE,      A.PLACEJH,        A.ISPROMOTION,  A.ISESSENTIAL,    A.SCORE,          A.EDUMANS SUBJTARGET,       \n");
            sql.append("        A.INUSERID,   A.INDATE,         A.LUSERID,        A.LDATE,                    \n");
            sql.append("        (SELECT NAME FROM TZ_MEMBER WHERE USERID=A.CUSERID) CUSERIDNM,  \n");
            sql.append("        (SELECT NAME FROM TZ_MEMBER WHERE USERID=A.MUSERID) MUSERIDNM,  \n");
//            sql.append("        ISNULL((SELECT BETACPNM FROM TZ_BETACPINFO WHERE BETACPNO = A.PRODUCER),(SELECT COMPANYNM FROM TZ_COMP WHERE COMP=A.PRODUCER)) PRODUCERNM, \n");
            sql.append("        '' AS PRODUCERNM, \n");
            sql.append("        ISNULL((SELECT CPNM FROM TZ_CPINFO WHERE CPSEQ = A.OWNER),(SELECT COMPANYNM FROM TZ_COMP WHERE COMP=A.OWNER )) OWNERNM, \n");
            sql.append("        A.PROPOSETYPE, A.EDUMANS,             \n");
            sql.append("        A.EDUTIMES,   A.EDUTYPE,        A.INTRO,          A.EXPLAIN ,                 \n");
            sql.append("        A.WHTEST,     A.GRADREPORT,     A.GRADEXAM,A.GRADFTEST,A.GRADHTEST,       A.ISABLEREVIEW, \n");
            sql.append("        A.ISOUTSOURCING,    A.CONTURL, A.ISAPPROVAL, BOOKFILENAMEREAL,BOOKFILENAMENEW, \n");
            sql.append("        (SELECT COUNT(*) FROM TZ_SUBJSEQ WHERE SUBJ=A.SUBJ) SUBJSEQCOUNT, \n");
            sql.append("        (SELECT COUNT(*) FROM TZ_SUBJOBJ WHERE SUBJ=A.SUBJ) SUBJOBJCOUNT, \n");
            sql.append("        ISVISIBLE,ISALLEDU, \n");
            sql.append("        A.ISINTRODUCTION, A.EDUPERIOD, A.GOYONGPRICEMAJOR, A.GOYONGPRICEMINOR, A.INTRODUCEFILENAMEREAL, A.INTRODUCEFILENAMENEW, \n");
            sql.append("        A.INFORMATIONFILENAMEREAL, A.INFORMATIONFILENAMENEW, CONTENTGRADE, A.MEMO, A.ISORDINARY, A.ISWORKSHOP, A.ISUNIT, SPHERE, PREWIDTH, PREHEIGHT, REVIEWDAYS, REVIEWTYPE,\n");
            sql.append("		SUBJTYPE,AUTOCONFIRM, SURYOYN, SURYOTITLE, NEEDINPUT, \n");
            sql.append("        A.SUBMAINFILENAMEREAL, A.SUBMAINFILENAMENEW, A.AREA, A.DEGREE,A.HITNUMBER, A.SEARCH_NM, A.MOBILE_PREURL, A.BIZ_TYPE, NVL(A.MOBILE_USE_YN, 'N') AS MOBILE_USE_YN \n");
            sql.append("        , A.SUBJSUMMARY, A.TAGS \n");
            sql.append("  FROM  TZ_SUBJ A \n");
            sql.append(" WHERE  A.SUBJ     =  " + SQLString.Format(v_subj) + " \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new SubjectData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                data.setIsonoff(ls.getString("isonoff"));
                data.setSubjclass(ls.getString("subjclass"));
                data.setUpperclass(ls.getString("upperclass"));
                data.setUpperclassnm(ls.getString("upperclassnm"));
                data.setMiddleclass(ls.getString("middleclass"));
                data.setMiddleclassnm(ls.getString("middleclassnm"));
                data.setLowerclass(ls.getString("lowerclass"));
                data.setLowerclassnm(ls.getString("lowerclassnm"));
                data.setSpecials(ls.getString("specials"));
                data.setContenttype(ls.getString("contenttype"));
                data.setMuserid(ls.getString("muserid"));
                data.setMusertel(ls.getString("musertel"));
                data.setCuserid(ls.getString("cuserid"));
                data.setIsuse(ls.getString("isuse"));
                data.setIsapproval(ls.getString("isapproval"));
                data.setIsgoyong(ls.getString("isgoyong"));
                data.setIspropose(ls.getString("ispropose"));
                data.setBiyong(ls.getInt("biyong"));
                data.setEdudays(ls.getInt("edudays"));
                data.setStudentlimit(ls.getInt("studentlimit"));
                data.setUsebook(ls.getString("usebook"));
                data.setBookprice(ls.getInt("bookprice"));
                data.setOwner(ls.getString("owner"));
                data.setProducer(ls.getString("producer"));
                data.setCrdate(ls.getString("crdate"));
                data.setLanguage(ls.getString("language"));
                data.setServer(ls.getString("server"));
                data.setDir(ls.getString("dir"));
                data.setEduurl(ls.getString("eduurl"));
                data.setVodurl(ls.getString("vodurl"));
                data.setPreurl(ls.getString("preurl"));
                data.setConturl(ls.getString("conturl"));
                data.setRatewbt(ls.getInt("ratewbt"));
                data.setRatevod(ls.getInt("ratevod"));
                data.setFramecnt(ls.getInt("framecnt"));
                data.setEnv(ls.getString("env"));
                data.setTutor(ls.getString("tutor"));
                data.setBookname(ls.getString("bookname"));
                data.setSdesc(ls.getString("sdesc"));
                data.setWarndays(ls.getInt("warndays"));
                data.setStopdays(ls.getInt("stopdays"));
                data.setPoint(ls.getInt("point"));
                data.setEdulimit(ls.getInt("edulimit"));
                data.setGradscore(ls.getInt("gradscore"));
                data.setGradstep(ls.getInt("gradstep"));
                data.setWstep(ls.getDouble("wstep"));
                data.setWmtest(ls.getDouble("wmtest"));
                data.setWftest(ls.getDouble("wftest"));
                data.setWreport(ls.getDouble("wreport"));
                data.setWact(ls.getDouble("wact"));
                data.setWetc1(ls.getDouble("wetc1"));
                data.setWetc2(ls.getDouble("wetc2"));
                data.setGoyongprice(ls.getInt("goyongprice"));
                data.setPlace(ls.getString("place"));
                data.setPlacejh(ls.getString("placejh"));
                data.setIspromotion(ls.getString("ispromotion"));
                data.setIsessential(ls.getString("isessential"));
                data.setScore(ls.getInt("score"));
                data.setSubjtarget(ls.getString("subjtarget"));
                data.setInuserid(ls.getString("inuserid"));
                data.setIndate(ls.getString("indate"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));

                data.setCuseridnm(ls.getString("cuseridnm"));
                data.setMuseridnm(ls.getString("museridnm"));
                data.setProducernm(ls.getString("producernm"));
                data.setOwnernm(ls.getString("ownernm"));
                data.setProposetype(ls.getString("proposetype"));
                data.setEdumans(ls.getString("edumans"));
                data.setEdutimes(ls.getInt("edutimes"));
                data.setEdutype(ls.getString("edutype"));
                data.setIntro(ls.getString("intro"));
                data.setExplain(ls.getString("explain"));

                data.setWhtest(ls.getDouble("whtest"));
                data.setGradexam(ls.getInt("gradexam"));//�̼�����-�߰���
                data.setGradftest(ls.getInt("gradftest"));//�̼�����-������
                data.setGradhtest(ls.getInt("gradhtest"));//�̼�����-������
                data.setGradreport(ls.getInt("gradreport"));

                data.setIsablereview(ls.getString("isablereview"));
                data.setIsoutsourcing(ls.getString("isoutsourcing"));

                data.setBookfilenamereal(ls.getString("bookfilenamereal"));
                data.setBookfilenamenew(ls.getString("bookfilenamenew"));

                data.setSubjseqcount(ls.getInt("subjseqcount"));

                data.setIsvisible(ls.getString("isvisible"));
                data.setSubjobjcount(ls.getInt("subjobjcount"));
                data.setIsalledu(ls.getString("isalledu"));

                data.setIsintroduction(ls.getString("isintroduction"));
                data.setEduperiod(ls.getInt("eduperiod"));
                data.setGoyongpricemajor(ls.getInt("goyongpricemajor"));
                data.setGoyongpriceminor(ls.getInt("goyongpriceminor"));
                data.setIntroducefilenamereal(ls.getString("introducefilenamereal"));
                data.setIntroducefilenamenew(ls.getString("introducefilenamenew"));
                data.setInformationfilenamereal(ls.getString("informationfilenamereal"));
                data.setInformationfilenamenew(ls.getString("informationfilenamenew"));
                data.setContentgrade(ls.getString("contentgrade"));
                data.setMemo(ls.getString("memo"));
                data.setIsordinary(ls.getString("isordinary"));
                data.setIsworkshop(ls.getString("isworkshop"));
                data.setIsunit(ls.getString("isunit"));
                data.setSphere(ls.getString("sphere"));

                data.setPrewidth(ls.getInt("prewidth"));
                data.setPreheight(ls.getInt("preheight"));

                data.setReviewdays(ls.getInt("reviewdays"));
                data.setReviewtype(ls.getString("reviewtype"));

                data.setSubjtype(ls.getString("subjtype"));
                data.setAutoconfirm(ls.getString("autoconfirm"));
                data.setSuryoyn(ls.getString("suryoyn"));
                data.setSuryotitle(ls.getString("suryotitle"));
                data.setNeedinput(ls.getString("needinput"));

                data.setSubmainfilenamereal(ls.getString("submainfilenamereal"));
                data.setSubmainfilenamenew(ls.getString("submainfilenamenew"));
                data.setArea(ls.getString("area"));
                data.setDegree(ls.getString("degree"));
                data.setHitnumber(ls.getString("hitnumber"));
                data.setSearch_nm(ls.getString("search_nm"));
                data.setMobile_preurl(ls.getString("mobile_preurl"));
                data.setBizType(ls.getString("biz_type"));
                data.setMobileUseYn(ls.getString("mobile_use_yn"));
                data.setSubjSummary(ls.getString("subjsummary"));
                data.setSubjTags(ls.getString("tags"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

        return data;
    }

    /**
     * ���ο� �����ڵ� ��� - ���̹�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        // PreparedStatement pstmt2 = null;

        StringBuilder sql = new StringBuilder();
        // String sql2 = "";
        // String sql3 = "";

        int isOk = 0;
        // int isOk2 = 0;

        String v_subj = "";
        String v_luserid = box.getSession("userid");
        String v_grcode = box.getString("s_grcode"); //��������Ʈ������ SELECTBOX �����ְ� �ڵ�

        String v_dir = "";
        String v_contenttype = box.getString("p_contenttype");
        //v_dir�� p_contenttype�� Normal,OBC,SCORM����϶� dp/content/�����ڵ带 �־��ش�.
        int idx = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            v_subj = getSubjcode(connMgr, box.getString("p_area"));

            // �ߺ� üũ
            if (!checkSubjcode(connMgr, v_subj)) {

                //v_dir�� p_contenttype�� Normal,OBC,SCORM����϶� content/�����ڵ带 �־��ش�.
                if (v_contenttype.equals("N") || v_contenttype.equals("M") || v_contenttype.equals("O") || v_contenttype.equals("S")) {
                    v_dir = "contents/" + v_subj;
                } else {
                    v_dir = box.getString("p_dir");
                }

                sql.setLength(0);
                sql.append("/* com.credu.course.SubjectBean InsertSubject (�������� ���) */\n");
                sql.append("INSERT  INTO  TZ_SUBJ ( \n");
                sql.append("        SUBJ,           SUBJNM,         ISONOFF,            SUBJCLASS,          \n"); // 01 ~ 04
                sql.append("        UPPERCLASS,     MIDDLECLASS,    LOWERCLASS,         SPECIALS,           \n"); // 05 ~ 08
                sql.append("        MUSERID,        CUSERID,        ISUSE,              ISGOYONG,           \n"); // 09 ~ 12
                sql.append("        ISPROPOSE,      BIYONG,         EDUDAYS,            STUDENTLIMIT,       \n"); // 13 ~ 16
                sql.append("        USEBOOK,        BOOKPRICE,      OWNER,              PRODUCER,           \n"); // 17 ~ 20
                sql.append("        CRDATE,         LANGUAGE,       SERVER,             DIR,                \n"); // 21 ~ 24
                sql.append("        EDUURL,         VODURL,         PREURL,             CONTURL,            \n"); // 25 ~ 28
                sql.append("        RATEWBT,        RATEVOD,        FRAMECNT,           ENV,                \n"); // 28 ~ 32
                sql.append("        TUTOR,          BOOKNAME,       SDESC,              WARNDAYS,           \n"); // 32 ~ 36
                sql.append("        STOPDAYS,       POINT,          EDULIMIT,           GRADSCORE,          \n"); // 37 ~ 40
                sql.append("        GRADSTEP,       WSTEP,          WMTEST,             WFTEST,             \n"); // 41 ~ 44
                sql.append("        WREPORT,        WACT,           WETC1,              WETC2,              \n"); // 45 ~ 48
                sql.append("        GOYONGPRICE,    INUSERID,       INDATE,             LUSERID,            \n"); // 49 ~ 52
                sql.append("        LDATE,          PROPOSETYPE,    EDUMANS,            INTRO,              \n"); // 53 ~ 56
                sql.append("        EXPLAIN,        ISESSENTIAL,    SCORE,              CONTENTTYPE,        \n"); // 57 ~ 60
                sql.append("        GRADEXAM,       GRADREPORT,     WHTEST,             ISOUTSOURCING,      \n"); // 61 ~ 64
                sql.append("        ISABLEREVIEW,   MUSERTEL,       GRADFTEST,          GRADHTEST,          \n"); // 65 ~ 68
                sql.append("        ISVISIBLE,      ISALLEDU,       EDUTIMES,           ISAPPROVAL,         \n"); // 69 ~ 72
                sql.append("        ISINTRODUCTION, EDUPERIOD,      GOYONGPRICEMAJOR,   GOYONGPRICEMINOR,   \n"); // 73 ~ 76
                sql.append("        INTRODUCEFILENAMEREAL,          INTRODUCEFILENAMENEW,                   \n"); // 77 ~ 78
                sql.append("        INFORMATIONFILENAMEREAL,        INFORMATIONFILENAMENEW,                 \n"); // 79 ~ 80
                sql.append("        CONTENTGRADE,   MEMO,           ISORDINARY,         ISWORKSHOP,         \n"); // 81 ~ 84
                sql.append("        ISUNIT,         SPHERE,         PREWIDTH,           PREHEIGHT,          \n"); // 85 ~ 88
                sql.append("        REVIEWDAYS,     REVIEWTYPE,     SUBJTYPE,           AUTOCONFIRM,        \n"); // 89 ~ 92
                sql.append("        SURYOYN,        SURYOTITLE,     NEEDINPUT,          SUBMAINFILENAMEREAL,\n"); // 93 ~ 96
                sql.append("        SUBMAINFILENAMENEW,             AREA,               DEGREE,             \n"); // 97 ~ 99
                sql.append("        HITNUMBER,      SEARCH_NM,      MOBILE_PREURL,      BIZ_TYPE,           \n"); // 100 ~ 103
                sql.append("        MOBILE_USE_YN                                                           \n"); // 104
                sql.append("        , SUBJSUMMARY, TAGS		                                                            \n"); // 104
                sql.append(" ) VALUES ( \n");
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 1 ~ 16
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 17 ~ 32
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 33 ~ 48
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 49 ~ 64
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 65 ~ 80
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,          \n"); // 81 ~ 96
                sql.append("        ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?                                           \n"); // 97 ~ 104
                sql.append("        , ?, ?							                                            \n"); // 97 ~ 104
                sql.append(" )  \n");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(idx++, v_subj); //�����ڵ�
                pstmt.setString(idx++, box.getString("p_subjnm")); //������
                pstmt.setString(idx++, box.getString("p_isonoff")); //���̹�/���ձ���
                pstmt.setString(idx++, box.getString("p_upperclass") + box.getString("p_middleclass") + box.getStringDefault("p_lowerclass", "000")); //�з��ڵ�
                pstmt.setString(idx++, box.getString("p_upperclass")); //��з�
                pstmt.setString(idx++, box.getString("p_middleclass")); //�ߺз�
                pstmt.setString(idx++, box.getStringDefault("p_lowerclass", "000")); //�Һз�
                pstmt.setString(idx++, box.getString("p_specials")); //����Ư��(YYY)
                pstmt.setString(idx++, box.getString("p_muserid")); //���� ID
                pstmt.setString(idx++, box.getString("p_cuserid")); //��������� ID
                pstmt.setString(idx++, box.getString("p_isuse")); //��뿩��(Y/N)
                pstmt.setString(idx++, box.getString("p_isgoyong")); //��뺸�迩��(Y/N)
                pstmt.setString(idx++, box.getStringDefault("p_ispropose", "Y")); //[X]������û����(Y/N)
                pstmt.setInt(idx++, box.getInt("p_biyong")); //������
                pstmt.setInt(idx++, box.getInt("p_edudays")); //�н�����
                pstmt.setInt(idx++, box.getInt("p_studentlimit")); //����
                pstmt.setString(idx++, box.getString("p_usebook")); //�����뿩��
                pstmt.setString(idx++, box.getStringDefault("p_bookprice", "0")); //�����
                pstmt.setString(idx++, box.getString("p_owner")); //����������
                pstmt.setString(idx++, box.getString("p_producer")); //����������
                pstmt.setString(idx++, box.getString("p_crdate")); //��������
                pstmt.setString(idx++, box.getString("p_language")); //[X]����
                pstmt.setString(idx++, box.getString("p_server")); //[X]����
                pstmt.setString(idx++, v_dir); //���������
                pstmt.setString(idx++, box.getString("p_eduurl")); //����URL
                pstmt.setString(idx++, box.getString("p_vodurl")); //VOD���
                pstmt.setString(idx++, box.getString("p_preurl")); //������URL
                pstmt.setString(idx++, box.getString("p_conturl")); //�н�����URL
                pstmt.setInt(idx++, box.getInt("p_ratewbt")); //�н����(WBT%)
                pstmt.setInt(idx++, box.getInt("p_ratevod")); //�н����(VOD%)
                pstmt.setInt(idx++, box.getInt("p_framecnt")); //�������Ӽ�
                pstmt.setString(idx++, box.getString("p_env")); //�н�ȯ��
                pstmt.setString(idx++, box.getString("p_tutor")); //���缳��
                pstmt.setString(idx++, box.getStringDefault("p_bookname", "")); //�����
                pstmt.setString(idx++, box.getString("p_sdesc")); //[X]���
                pstmt.setInt(idx++, box.getInt("p_warndays")); //[X]�н����������
                pstmt.setInt(idx++, box.getInt("p_stopdays")); //[X]�н�����������
                pstmt.setInt(idx++, box.getInt("p_point")); //�̼�����
                pstmt.setInt(idx++, box.getInt("p_edulimit")); //1���ִ��н���
                pstmt.setInt(idx++, box.getInt("p_gradscore")); //�̼�����-����
                pstmt.setInt(idx++, box.getInt("p_gradstep")); //�̼�����-������
                pstmt.setDouble(idx++, box.getDouble("p_wstep")); //����ġ-������
                pstmt.setDouble(idx++, box.getDouble("p_wmtest")); //����ġ-�߰���
                pstmt.setDouble(idx++, box.getDouble("p_wftest")); //����ġ-������
                pstmt.setDouble(idx++, box.getDouble("p_wreport")); //����ġ-����Ʈ
                pstmt.setDouble(idx++, box.getDouble("p_wact")); //����ġ-��Ƽ��Ƽ
                pstmt.setDouble(idx++, box.getDouble("p_wetc1")); //����ġ-������
                pstmt.setDouble(idx++, box.getDouble("p_wetc2")); //����ġ-��Ÿ
                pstmt.setString(idx++, box.getStringDefault("p_goyongprice", "0")); //��뺸��ȯ�ޱݾ�
                pstmt.setString(idx++, v_luserid); //������
                pstmt.setString(idx++, FormatDate.getDate("yyyyMMddHHmmss")); //������
                pstmt.setString(idx++, v_luserid); //����������
                pstmt.setString(idx++, FormatDate.getDate("yyyyMMddHHmmss")); //����������(ldate)
                pstmt.setString(idx++, box.getString("p_proposetype")); //������û����(TZ_CODE GUBUN='0019')
                pstmt.setString(idx++, box.getStringDefault("p_edumans", "")); //������ �������
                pstmt.setString(idx++, box.getStringDefault("p_intro", "")); //������ ��������
                pstmt.setString(idx++, box.getStringDefault("p_explain", "")); //������ ��������
                pstmt.setString(idx++, box.getStringDefault("p_isessential", "0")); //��������
                pstmt.setInt(idx++, box.getInt("p_score")); //��������
                pstmt.setString(idx++, v_contenttype); //������Ÿ��

                //------------------------------------------------------------------------------//
                pstmt.setString(idx++, box.getString("p_gradexam")); //�̼�����(��)
                pstmt.setString(idx++, box.getString("p_gradreport")); //�̼�����(����Ʈ)
                pstmt.setString(idx++, box.getString("p_whtest")); //����ġ(������)
                pstmt.setString(idx++, box.getString("p_isoutsourcing")); //��Ź��������
                pstmt.setString(idx++, box.getString("p_isablereview")); //�������ɿ���
                pstmt.setString(idx++, box.getString("p_musertel")); //���� ��ȭ��ȣ
                pstmt.setString(idx++, box.getString("p_gradftest")); //�̼�����-������
                pstmt.setString(idx++, box.getString("p_gradhtest")); //�̼�����-������
                pstmt.setString(idx++, box.getStringDefault("p_isvisible", "Y")); //�н��ڿ��� �����ֱ�
                pstmt.setString(idx++, box.getString("p_isalledu")); //���米������
                pstmt.setInt(idx++, box.getInt("p_edutimes")); //�н��ð�

                pstmt.setString(idx++, "Y"); //�������ο��� (���⼱ �׻� Y)

                pstmt.setString(idx++, box.getString("p_isintroduction")); //�����Ұ� ��뿩��
                pstmt.setString(idx++, box.getString("p_eduperiod")); //�н��Ⱓ
                pstmt.setString(idx++, box.getString("p_goyongprice_major")); //��뺸�� ȯ�޾�(����)
                pstmt.setString(idx++, box.getString("p_goyongprice_minor")); //��뺸�� ȯ�޾�(�߼ұ��)
                pstmt.setString(idx++, box.getRealFileName("p_introducefile")); //�����Ұ� �̹���
                pstmt.setString(idx++, box.getNewFileName("p_introducefile")); //�����Ұ� �̹���
                pstmt.setString(idx++, box.getRealFileName("p_informationfile")); //����(����)
                pstmt.setString(idx++, box.getNewFileName("p_informationfile")); //����(����)
                pstmt.setString(idx++, box.getString("p_contentgrade")); // ���������
                pstmt.setString(idx++, box.getString("p_memo")); // ���
                pstmt.setString(idx++, box.getString("p_isordinary")); // ���/����
                pstmt.setString(idx++, box.getString("p_isworkshop")); // ��ũ��
                pstmt.setString(idx++, box.getString("p_isunit")); // ��������
                pstmt.setString(idx++, box.getString("p_sphere")); // �о�(����)

                pstmt.setInt(idx++, box.getInt("p_prewidth")); // ������ â width
                pstmt.setInt(idx++, box.getInt("p_preheight")); // ������ â height

                pstmt.setInt(idx++, box.getInt("p_reviewdays")); // �����Ⱓ
                pstmt.setString(idx++, box.getString("p_reviewtype")); // �����Ⱓ ����(D:��, W:��, M:��, Y:��)

                pstmt.setString(idx++, box.getString("p_subjtype")); // ��������
                pstmt.setString(idx++, box.getString("p_autoconfirm")); // ���ð�������
                pstmt.setString(idx++, box.getString("p_suryoyn")); // ��������°��ɿ���
                pstmt.setString(idx++, box.getString("p_suryotitle")); // �������������
                pstmt.setString(idx++, box.getString("p_needinput")); // ������û �Է�����

                pstmt.setString(idx++, box.getRealFileName("p_submainfile")); //������� ȭ��� �̹���
                pstmt.setString(idx++, box.getNewFileName("p_submainfile")); //������� ȭ��� �̹���
                pstmt.setString(idx++, box.getString("p_area")); //�����ҼӺо�
                pstmt.setString(idx++, box.getString("p_degree")); //�����ҼӺо�
                pstmt.setString(idx++, box.getString("hitnumber")); //��õ��
                pstmt.setString(idx++, box.getString("p_search_nm")); //�����˻���
                pstmt.setString(idx++, box.getString("p_mobile_preurl")); // ����� �̸����� URL
                pstmt.setString(idx++, box.getString("bizType")); // 
                pstmt.setString(idx++, box.getString("mobileUseYn")); // ����� ���� ����
                pstmt.setString(idx++, box.getString("p_subjsummary")); // �±�
                pstmt.setString(idx++, box.getString("p_subjtags")); // �±�

                isOk = pstmt.executeUpdate();

                //�����׷��� �����ϰ� �߰��ϴ� ���� �ش� �����׷쿡 ���� TZ_PREVIEW�� INSERT�Ѵ�.
                if (!v_grcode.equals("ALL")) {
                    box.put("p_grcode", v_grcode);
                    box.put("p_subj", v_subj);
                    //	                isOk2 =
                    InsertPreview(box);
                }
                Object muser = box.getObject("muser");
                if (muser != null) {
                    saveMuser(connMgr, v_subj, muser, v_luserid);
                }

                //�����׷� �����ڴ� �ش� �����׷����� INSERT�ϰ� �Ѵ�.
                if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("H")) {
                    this.SaveGrSubj(connMgr, v_grcode, v_subj, v_luserid);
                }

                //���ܰ����ڴ� ���ܱ����׷����� INSERT�ϰ� �Ѵ�.
                if (box.getSession("gadmin").equals("B1")) {
                    this.SaveGrSubj(connMgr, "N000001", v_subj, v_luserid);
                }

                //���Ӱ����ڴ� ���ӱ����׷����� INSERT�ϰ� �Ѵ�.
                if (box.getSession("gadmin").equals("B2")) {
                    this.SaveGrSubj(connMgr, "N000002", v_subj, v_luserid);
                }

                //�Ѱ������ڴ� �ش� �����׷����� INSERT�ϰ� �Ѵ�.
                if (box.getSession("gadmin").equals("A1")) {
                    this.SaveGrSubj(connMgr, v_grcode, v_subj, v_luserid);
                }
                //���������� �ڷ�� Insert
                //	            int isOk5 =
                this.InsertBds(connMgr, v_subj);

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
        return isOk;
    }

    private boolean saveMuser(DBConnectionManager connMgr, String v_subj, Object muser, String v_luserid) throws Exception {
        boolean result = false;
        String sql = " delete tz_subjtutor where SUBJ=? ";
        PreparedStatement pstmt = connMgr.prepareStatement(sql);
        pstmt.setString(1, v_subj);
        pstmt.executeUpdate();

        sql = " insert into tz_subjtutor(SUBJ,USERID,SEQ,LUSERID,LDATE) values (?,?,(select nvl(max(SEQ),0)+1 from tz_subjtutor where SUBJ=?),?,to_char(sysdate, 'YYYYMMDDHHMISS'))";
        pstmt = connMgr.prepareStatement(sql);
        if (muser.getClass().isArray()) {
            Object[] temp = (Object[]) muser;
            for (Object muserid : temp) {
                pstmt.setString(1, v_subj);
                pstmt.setString(2, (String) muserid);
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_luserid);
                pstmt.executeUpdate();
            }
        } else {
            pstmt.setString(1, v_subj);
            pstmt.setString(2, (String) muser);
            pstmt.setString(3, v_subj);
            pstmt.setString(4, v_luserid);
            pstmt.executeUpdate();
        }
        result = true;

        return result;
    }

    /**
     * �����׷� ����
     * 
     * @param DBConnectionManager DB Connection Manager
     * @param v_grcode �����׷�
     * @param v_subj �����ڵ�
     * @param v_luserid ������
     * @return isOk 1:insert success,0:insert fail
     */
    public int SaveGrSubj(DBConnectionManager connMgr, String v_grcode, String v_subj, String v_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            sql = " insert into tz_grsubj" + " (grcode, subjcourse, isnew, disseq, grpcode, grpname, luserid, ldate) " + " values (?,?,'N',0,'','',?,to_char(sysdate,'YYYYMMDDHH24MISS'))";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_luserid);
            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * ���ο� �����ڵ� ��� - ���̹�
     * 
     * @param box receive from the form object and session
     * @return String 1:insert success,0:insert fail
     */
    public String getMaxSubjcode(DBConnectionManager connMgr, String v_upperclass, String v_middleclass) throws Exception {
        String v_subjcode = "";
        String v_maxsubj = "";
        //        int    v_maxno   = 0;

        ListSet ls = null;
        String sql = "";
        try {
            sql = " select max(subj)+1 maxsubj";
            sql += "   from tz_subj ";
            //sql = "select to_char(max(isnull(subj,0)+1),'0000')

            //�����ڵ� =  ��з�(3) + �ߺз�(3) + �Ϸù�ȣ(4)
            //sql = " select max(substr(subj,7,4)) maxsubj";
            //sql+= "   from tz_subj where upperclass = '" + v_upperclass + "' and middleclass = '" + v_middleclass + "'";
            System.out.println("max sql =" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_maxsubj = ls.getString("maxsubj");
            }

            if (v_maxsubj.equals("")) {
                v_subjcode = "1000";
                //v_subjcode = v_upperclass + v_middleclass + "0001";
            } else {
                //                v_maxno = Integer.valueOf(v_maxsubj.substring(1)).intValue();
                //v_subjcode = "S" + new DecimalFormat("000").format(v_maxno+1);
                //v_subjcode = v_upperclass + v_middleclass + new DecimalFormat("0000").format(v_maxno+1);
                //v_subjcode = new DecimalFormat("0000").format(v_maxno+1);
                v_subjcode = v_maxsubj;
                System.out.println("v_subjcode" + v_subjcode);
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
        }
        return v_subjcode;
    }

    /**
     * ���� �ڵ� �����
     * 
     * @param connMgr
     * @param v_area
     * @return
     * @throws Exception
     */
    public String getSubjcode(DBConnectionManager connMgr, String v_area) throws Exception {
        String v_subjcode = "";

        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        try {

            sql.append(" SELECT \n");
            sql.append(" 'C' || SUBSTR('" + v_area + "',1,1) || TO_CHAR(SYSDATE, 'YY') || LPAD(TO_NUMBER(NVL(MAX(SUBSTR(SUBJ, -3)),0)) + 1, 3, 0) AS MAXVAL \n");
            sql.append("   FROM TZ_SUBJ \n");
            sql.append("  WHERE SUBJ LIKE 'C' || SUBSTR('" + v_area + "',1,1) || TO_CHAR(SYSDATE, 'YY') || '%' ");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                v_subjcode = ls.getString("maxval");
            }

            sql.setLength(0);

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
        }

        return v_subjcode;
    }

    /**
     * �����ڵ� üũ
     * 
     * @param box receive from the form object and session
     * @return String 1:insert success,0:insert fail
     */
    public boolean checkSubjcode(DBConnectionManager connMgr, String v_subj) throws Exception {
        boolean result = true;
        int cnt = 0;
        ListSet ls = null;
        String sql = "";
        try {
            sql = " select count(*) cnt ";
            sql += "   from tz_subj     ";
            sql += " where subj =  " + SQLString.Format(v_subj);
            System.out.println("�����ڵ� �ߺ�üũ :" + sql);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt == 0) {
                result = false;
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
        }
        return result;
    }

    /**
     * ���ο� �����ڵ� ��� - ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertOffSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int isOk2 = 1;

        String v_subj = "";
        String v_luserid = box.getSession("userid");
        String v_grcode = box.getStringDefault("s_grcode", "ALL"); //��������Ʈ������ SELECTBOX �����ְ� �ڵ�

        try {
            connMgr = new DBConnectionManager();

            //v_subj = getMaxSubjcode(connMgr, box.getString("p_upperclass"), box.getString("p_middleclass"));
            v_subj = box.getString("p_subj");
            // �ߺ� üũ
            if (!checkSubjcode(connMgr, v_subj)) {

                //insert TZ_SUBJ table
                sql = "insert into TZ_SUBJ ( ";
                sql += " subj,      subjnm,     isonoff,     subjclass, ";
                sql += " upperclass,middleclass,lowerclass,  specials,  ";
                sql += " muserid,   cuserid,    isuse,       isgoyong,   ispropose, ";
                sql += " biyong,    edudays,    studentlimit,usebook,  ";
                sql += " bookprice, owner,      producer,    crdate,     language,  ";
                sql += " server,    dir,        eduurl,      vodurl,     preurl,    ";
                sql += " ratewbt,   ratevod,    framecnt,    env,      ";
                sql += " tutor,     bookname,   sdesc,       warndays,   stopdays, ";
                sql += " point,     edulimit,   gradscore,   gradstep, ";
                sql += " wstep,     wmtest,     wftest,      wreport,  ";
                sql += " wact,      wetc1,      wetc2,       goyongprice, ";
                sql += " place,     edumans,    isessential, score,     ";
                sql += " inuserid,  indate,     luserid,     ldate,     proposetype, ";
                sql += " edutimes,  edutype,    intro,       explain,  ";
                sql += " gradexam,  gradreport, bookfilenamereal, bookfilenamenew, "; //62 ~ 65
                sql += " conturl,musertel,gradftest,gradhtest,isvisible,isalledu,placejh, "; //70 ~ 72
                sql += " isapproval, isintroduction, eduperiod, goyongpricemajor, goyongpriceminor,";
                sql += " introducefilenamereal, introducefilenamenew, informationfilenamereal, informationfilenamenew, memo) ";
                sql += " values (";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?,    ";
                sql += " ?,    ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?, ";
                sql += " ?,    ?,    ?,    ?,       ";
                sql += " ?,    ?,    ?,    ?,   ?,  ";
                sql += " ?,    ?,    ?,    ?,";
                sql += " ?,        ?,      ?,      ?,"; //62 ~ 65
                sql += " ?,  ?,    ?,     ?, ?,?,?, "; //70 ~ 72
                sql += " ?,  ?, ?,?,?,?,?, ?, ?, ?)";

                connMgr.setAutoCommit(false);
                pstmt = connMgr.prepareStatement(sql);
                //System.out.println("sql ==>" + sql );

                pstmt.setString(1, v_subj); //�����ڵ�
                pstmt.setString(2, box.getString("p_subjnm")); //������
                pstmt.setString(3, box.getString("p_isonoff")); //���̹�/���ձ���
                pstmt.setString(4, box.getString("p_upperclass") + box.getString("p_middleclass") + box.getStringDefault("p_lowerclass", "000")); //�з��ڵ�
                pstmt.setString(5, box.getString("p_upperclass")); //��з�
                pstmt.setString(6, box.getString("p_middleclass")); //�ߺз�
                pstmt.setString(7, box.getStringDefault("p_lowerclass", "000")); //�Һз�
                pstmt.setString(8, box.getString("p_specials")); //����Ư��
                pstmt.setString(9, box.getString("p_muserid")); //����ID
                pstmt.setString(10, box.getString("p_cuserid")); //[X]���������ID
                pstmt.setString(11, box.getString("p_isuse")); //��뿩��
                pstmt.setString(12, box.getString("p_isgoyong")); //[X]��뺸�迩��(Y/N)
                pstmt.setString(13, box.getString("p_ispropose")); //[X]������û����(Y/N)
                pstmt.setInt(14, box.getInt("p_biyong")); //������
                pstmt.setInt(15, box.getInt("p_edudays")); //�����Ⱓ(��)
                pstmt.setInt(16, box.getInt("p_studentlimit")); //����������
                pstmt.setString(17, box.getString("p_usebook")); //[X]�����뿩��
                pstmt.setString(18, box.getStringDefault("p_bookprice", "0")); //[X]�����
                pstmt.setString(19, box.getString("p_owner")); //[X]����������
                pstmt.setString(20, box.getString("p_producer")); //[X]����������
                pstmt.setString(21, box.getString("p_crdate")); //[X]��������
                pstmt.setString(22, box.getString("p_language")); //[X]����
                pstmt.setString(23, box.getString("p_server")); //[X]����
                pstmt.setString(24, box.getString("p_dir")); //[X]���������
                pstmt.setString(25, box.getString("p_eduurl")); //[X]����URL
                pstmt.setString(26, box.getString("p_vodurl")); //[X]VOD���
                pstmt.setString(27, box.getString("p_preurl")); //������URL
                pstmt.setInt(28, box.getInt("p_ratewbt")); //[X]�н����(WBT%)
                pstmt.setInt(29, box.getInt("p_ratevod")); //[X]�н����(VOD%)
                pstmt.setInt(30, box.getInt("p_framecnt")); //[X]�������Ӽ�
                pstmt.setString(31, box.getString("p_env")); //[X]�н�ȯ��
                pstmt.setString(32, box.getString("p_tutor")); //[X]���缳��
                pstmt.setString(33, box.getStringDefault("p_bookname", "")); //[X]�����
                pstmt.setString(34, box.getString("p_sdesc")); //[X]���
                pstmt.setInt(35, box.getInt("p_warndays")); //[X]�н����������
                pstmt.setInt(36, box.getInt("p_stopdays")); //[X]�н�����������
                pstmt.setInt(37, box.getInt("p_point")); //�̼�����
                pstmt.setInt(38, box.getInt("p_edulimit")); //[X]1���ִ��н���
                pstmt.setInt(39, box.getInt("p_gradscore")); //�̼�����-����
                pstmt.setInt(40, box.getInt("p_gradstep")); //�̼�����-�⼮��
                pstmt.setDouble(41, box.getDouble("p_wstep")); //����ġ-�⼮��
                pstmt.setDouble(42, box.getDouble("p_wmtest")); //[X]����ġ-�߰���
                pstmt.setDouble(43, box.getDouble("p_wftest")); //����ġ-������
                pstmt.setDouble(44, box.getDouble("p_wreport")); //����ġ-����Ʈ
                pstmt.setDouble(45, box.getDouble("p_wact")); //[X]����ġ-��Ƽ��Ƽ
                pstmt.setDouble(46, box.getDouble("p_wetc1")); //����ġ-������
                pstmt.setDouble(47, box.getDouble("p_wetc2")); //[X]����ġ-��Ÿ
                pstmt.setString(48, box.getStringDefault("p_goyongprice", "0")); //[X]��뺸��ȯ�ޱݾ�
                pstmt.setString(49, box.getString("p_place")); //�������
                pstmt.setString(50, box.getString("p_edumans")); //������ �������
                pstmt.setString(51, box.getStringDefault("p_isessential", "0")); //��������
                pstmt.setInt(52, box.getInt("p_score")); //��������
                pstmt.setString(53, v_luserid); //������
                pstmt.setString(54, FormatDate.getDate("yyyyMMddHHmmss")); //������
                pstmt.setString(55, v_luserid); //����������
                pstmt.setString(56, FormatDate.getDate("yyyyMMddHHmmss")); //����������(ldate)
                pstmt.setString(57, box.getString("p_proposetype")); //������û����(TZ_CODE GUBUN='0019')
                pstmt.setInt(58, box.getInt("p_edutimes")); //�����ð�
                pstmt.setString(59, box.getString("p_edutype")); //��������
                pstmt.setString(60, box.getString("p_intro")); //������ ��������
                pstmt.setString(61, box.getString("p_explain")); //������ ��������

                //------------------------------------------------------------------------------//
                pstmt.setString(62, box.getString("p_gradexam")); //�̼�����(��)
                pstmt.setString(63, box.getString("p_gradreport")); //�̼�����(����Ʈ)
                pstmt.setString(64, box.getRealFileName("p_file")); //�������ϸ�
                pstmt.setString(65, box.getNewFileName("p_file")); //��������DB��
                pstmt.setString(66, box.getString("p_conturl")); //�н�����URL
                pstmt.setString(67, box.getString("p_musertel")); //���� ��ȭ��ȣ
                pstmt.setString(68, box.getString("p_gradftest")); //�̼�����-������
                pstmt.setString(69, box.getString("p_gradhtest")); //�̼�����-������
                pstmt.setString(70, box.getStringDefault("p_isvisible", "Y")); //�н��ڿ��� �����ֱ�
                pstmt.setString(71, box.getString("p_isalledu")); //���米������
                pstmt.setString(72, box.getString("p_placejh")); //�������
                pstmt.setString(73, "Y"); //�������ο��� (���⼱ �׻� Y)
                pstmt.setString(74, box.getString("p_isintroduction")); //�����Ұ� ��뿩��
                pstmt.setString(75, box.getString("p_eduperiod")); //�н��Ⱓ
                pstmt.setString(76, box.getString("p_goyongprice_major")); //��뺸�� ȯ�޾�(����)
                pstmt.setString(77, box.getString("p_goyongprice_minor")); //��뺸�� ȯ�޾�(�߼ұ��)
                pstmt.setString(78, box.getRealFileName("p_introducefile")); //�����Ұ� �̹���
                pstmt.setString(79, box.getNewFileName("p_introducefile")); //�����Ұ� �̹���
                pstmt.setString(80, box.getRealFileName("p_informationfile")); //����(����)
                pstmt.setString(81, box.getNewFileName("p_informationfile")); //����(����)
                pstmt.setString(82, box.getString("p_memo")); // ���

                isOk = pstmt.executeUpdate();

                //box.put("s_upperclass",box.getString("p_upperclass"));
                //box.put("s_middleclass",box.getString("p_middleclass"));

                //�����׷��� �����ϰ� �߰��ϴ� ���� �ش� �����׷쿡 ���� TZ_PREVIEW�� INSERT�Ѵ�.
                if (!v_grcode.equals("ALL")) {
                    box.put("p_grcode", v_grcode);
                    box.put("p_subj", v_subj);
                    isOk2 = InsertPreview(box);
                }

                //	            int isOk3 = 0;

                //�����׷� �����ڴ� �ش� �����׷����� INSERT�ϰ� �Ѵ�.
                if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("H")) {
                    //	                isOk3 =
                    this.SaveGrSubj(connMgr, v_grcode, v_subj, v_luserid);
                }

                //���ܰ����ڴ� ���ܱ����׷����� INSERT�ϰ� �Ѵ�.
                if (box.getSession("gadmin").equals("B1")) {
                    //	                isOk3 =
                    this.SaveGrSubj(connMgr, "N000001", v_subj, v_luserid);
                }

                //���Ӱ����ڴ� ���ӱ����׷����� INSERT�ϰ� �Ѵ�.
                if (box.getSession("gadmin").equals("B2")) {
                    //	                isOk3 =
                    this.SaveGrSubj(connMgr, "N000002", v_subj, v_luserid);
                }

                //���������� �ڷ�� Insert
                //	            int isOk5 = this.InsertBds(connMgr,v_subj);

                if (isOk == 1 && isOk2 == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
        return isOk;
    }

    /**
     * ���õ� �����ڵ� ���� - ���̹�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int UpdateSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuilder sql = new StringBuilder();
        int isOk = 0;
        int idx = 1;

        String v_luserid = box.getSession("userid");
        String v_introducefilenamereal = box.getRealFileName("p_introducefile");
        String v_introducefilenamenew = box.getNewFileName("p_introducefile");
        String v_informationfilenamereal = box.getRealFileName("p_informationfile");
        String v_informationfilenamenew = box.getNewFileName("p_informationfile");
        String v_submainfilenamereal = box.getRealFileName("p_submainfile");
        String v_submainfilenamenew = box.getNewFileName("p_submainfile");
        String v_introducefile0 = box.getStringDefault("p_introducefile0", "0");
        String v_informationfile0 = box.getStringDefault("p_informationfile0", "0");
        String v_submainfile0 = box.getStringDefault("p_submainfile0", "0");
        String v_hitnumber = box.getStringDefault("hitnumber", "0");

        if (v_introducefilenamereal.length() == 0) {
            if (v_introducefile0.equals("1")) {
                v_introducefilenamereal = "";
                v_introducefilenamenew = "";
            } else {
                v_introducefilenamereal = box.getString("p_introducefile1");
                v_introducefilenamenew = box.getString("p_introducefile2");
            }
        }

        if (v_informationfilenamereal.length() == 0) {
            if (v_informationfile0.equals("1")) {
                v_informationfilenamereal = "";
                v_informationfilenamenew = "";
            } else {
                v_informationfilenamereal = box.getString("p_informationfile1");
                v_informationfilenamenew = box.getString("p_informationfile2");
            }
        }

        if (v_submainfilenamereal.length() == 0) {
            if (v_submainfile0.equals("1")) {
                v_submainfilenamereal = "";
                v_submainfilenamenew = "";
            } else {
                v_submainfilenamereal = box.getString("p_submainfile1");
                v_submainfilenamenew = box.getString("p_submainfile2");
            }
        }

        String v_dir = "";
        String v_contenttype = box.getString("p_contenttype");
        //v_dir�� p_contenttype�� Normal,OBC,SCORM����϶� dp/content/�����ڵ带 �־��ش�.

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //v_dir�� p_contenttype�� Normal,OBC,SCORM����϶� dp/content/�����ڵ带 �־��ش�.
            if (v_contenttype.equals("N") || v_contenttype.equals("M") || v_contenttype.equals("O") || v_contenttype.equals("S")) {
                v_dir = "contents/" + box.getString("p_subj");
            } else {
                v_dir = box.getString("p_dir");
            }

            sql.append("/* com.credu.course.SubjectBean UpdateSubject (�¶��� �������� ����) */\n");
            sql.append("UPDATE  TZ_SUBJ                         \n");
            sql.append("   SET  SUBJNM = ?,                     \n");
            sql.append("        ISONOFF = ?,                    \n");
            sql.append("        SUBJCLASS = ?,                  \n");
            sql.append("        UPPERCLASS = ?,                 \n");
            sql.append("        MIDDLECLASS = ?,                \n");
            sql.append("        LOWERCLASS = ?,                 \n");
            sql.append("        SPECIALS = ?,                   \n");
            sql.append("        MUSERID = ?,                    \n");
            sql.append("        CUSERID = ?,                    \n");
            sql.append("        ISUSE = ?,                      \n");
            sql.append("        ISGOYONG = ?,                   \n");
            sql.append("        ISPROPOSE = ?,                  \n");
            sql.append("        BIYONG = ?,                     \n");
            sql.append("        EDUDAYS = ?,                    \n");
            sql.append("        STUDENTLIMIT = ?,               \n");
            sql.append("        USEBOOK = ?,                    \n");
            sql.append("        BOOKPRICE = ?,                  \n");
            sql.append("        OWNER = ?,                      \n");
            sql.append("        PRODUCER = ?,                   \n");
            sql.append("        CRDATE = ?,                     \n");
            sql.append("        LANGUAGE = ?,                   \n");
            sql.append("        DIR = NVL(DIR, ?),              \n");
            sql.append("        EDUURL = ?,                     \n");
            sql.append("        PREURL = ?,                     \n");
            sql.append("        RATEWBT = ?,                    \n");
            sql.append("        RATEVOD = ?,                    \n");
            sql.append("        FRAMECNT = ?,                   \n");
            sql.append("        ENV = ?,                        \n");
            sql.append("        TUTOR = ?,                      \n");
            sql.append("        BOOKNAME = ?,                   \n");
            sql.append("        SDESC = ?,                      \n");
            sql.append("        WARNDAYS = ?,                   \n");
            sql.append("        STOPDAYS = ?,                   \n");
            sql.append("        POINT = ?,                      \n");
            sql.append("        EDULIMIT = ?,                   \n");
            sql.append("        GRADSCORE = ?,                  \n");
            sql.append("        GRADSTEP = ?,                   \n");
            sql.append("        WSTEP = ?,                      \n");
            sql.append("        WMTEST = ?,                     \n");
            sql.append("        WFTEST = ?,                     \n");
            sql.append("        WREPORT = ?,                    \n");
            sql.append("        WACT = ?,                       \n");
            sql.append("        WETC1 = ?,                      \n");
            sql.append("        WETC2 = ?,                      \n");
            sql.append("        GOYONGPRICE = ?,                \n");
            sql.append("        LUSERID = ?,                    \n");
            sql.append("        LDATE = ?,                      \n");
            sql.append("        PROPOSETYPE = ?,                \n");
            sql.append("        EDUMANS = ?,                    \n");
            sql.append("        INTRO = ?,                      \n");
            sql.append("        EXPLAIN = ?,                    \n");
            sql.append("        CONTENTTYPE = ?,                \n");
            sql.append("        GRADEXAM = ?,                   \n");
            sql.append("        GRADREPORT = ?,                 \n");
            sql.append("        WHTEST = ?,                     \n");
            sql.append("        ISABLEREVIEW = ?,               \n");
            sql.append("        SCORE = ?,                      \n");
            sql.append("        ISOUTSOURCING = ?,              \n");
            sql.append("        CONTURL = ?,                    \n");
            sql.append("        ISESSENTIAL = ?,                \n");
            sql.append("        MUSERTEL = ?,                   \n");
            sql.append("        GRADFTEST = ?,                  \n");
            sql.append("        GRADHTEST = ?,                  \n");
            sql.append("        ISVISIBLE = ?,                  \n");
            sql.append("        ISALLEDU = ?,                   \n");
            sql.append("        EDUTIMES = ?,                   \n");
            sql.append("        ISINTRODUCTION = ?,             \n");
            sql.append("        EDUPERIOD = ?,                  \n");
            sql.append("        GOYONGPRICEMAJOR = ?,           \n");
            sql.append("        GOYONGPRICEMINOR = ?,           \n");
            sql.append("        INTRODUCEFILENAMEREAL = ?,      \n");
            sql.append("        INTRODUCEFILENAMENEW = ?,       \n");
            sql.append("        INFORMATIONFILENAMEREAL = ?,    \n");
            sql.append("        INFORMATIONFILENAMENEW = ?,     \n");
            sql.append("        CONTENTGRADE = ?,               \n");
            sql.append("        MEMO = ?,                       \n");
            sql.append("        ISORDINARY = ?,                 \n");
            sql.append("        ISWORKSHOP = ?,                 \n");
            sql.append("        ISUNIT = ?,                     \n");
            sql.append("        SPHERE = ?,                     \n");
            sql.append("        PREWIDTH = ?,                   \n");
            sql.append("        PREHEIGHT = ?,                  \n");
            sql.append("        REVIEWDAYS = ?,                 \n");
            sql.append("        REVIEWTYPE = ?,                 \n");
            sql.append("        SUBJTYPE = ?,                   \n"); // ��������
            sql.append("        AUTOCONFIRM = ?,                \n"); // ���ð�������
            sql.append("        SURYOYN = ?,                    \n"); // ��������°��ɿ���
            sql.append("        SURYOTITLE = ?,                 \n"); // ����������
            sql.append("        NEEDINPUT = ?,                  \n"); // ������û �Է�����
            sql.append("        SUBMAINFILENAMEREAL = ?,        \n");
            sql.append("        SUBMAINFILENAMENEW = ?,         \n");
            sql.append("        AREA = ?,                       \n");
            sql.append("        DEGREE = ?,                     \n");
            sql.append("        HITNUMBER = ?,                  \n");
            sql.append("        SEARCH_NM = ?,                  \n");
            sql.append("        MOBILE_PREURL = ?,              \n");
            sql.append("        BIZ_TYPE = ?,                   \n");
            sql.append("        MOBILE_USE_YN = ?               \n");
            sql.append("        , SUBJSUMMARY = ?         	    \n");
            sql.append("        , TAGS = ?         			    \n");
            sql.append(" WHERE  SUBJ = ?                        \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(idx++, box.getString("p_subjnm"));
            pstmt.setString(idx++, box.getString("p_isonoff"));
            pstmt.setString(idx++, box.getString("p_upperclass") + box.getString("p_middleclass") + box.getStringDefault("p_lowerclass", "000")); //�з��ڵ�
            pstmt.setString(idx++, box.getString("p_upperclass")); //��з�
            pstmt.setString(idx++, box.getString("p_middleclass")); //�ߺз�
            pstmt.setString(idx++, box.getStringDefault("p_lowerclass", "000")); //�Һз�
            pstmt.setString(idx++, box.getString("p_specials"));
            pstmt.setString(idx++, box.getString("p_muserid"));
            pstmt.setString(idx++, box.getString("p_cuserid"));
            pstmt.setString(idx++, box.getString("p_isuse"));
            pstmt.setString(idx++, box.getString("p_isgoyong"));
            pstmt.setString(idx++, box.getString("p_ispropose"));
            pstmt.setInt(idx++, box.getInt("p_biyong"));
            pstmt.setInt(idx++, box.getInt("p_edudays"));
            pstmt.setInt(idx++, box.getInt("p_studentlimit"));
            pstmt.setString(idx++, box.getString("p_usebook"));
            pstmt.setString(idx++, box.getStringDefault("p_bookprice", "0"));
            pstmt.setString(idx++, box.getString("p_owner"));
            pstmt.setString(idx++, box.getString("p_producer"));
            pstmt.setString(idx++, box.getString("p_crdate"));
            pstmt.setString(idx++, box.getString("p_language"));
            pstmt.setString(idx++, v_dir);
            pstmt.setString(idx++, box.getString("p_eduurl"));
            pstmt.setString(idx++, box.getString("p_preurl"));
            pstmt.setInt(idx++, box.getInt("p_ratewbt"));
            pstmt.setInt(idx++, box.getInt("p_ratevod"));
            pstmt.setInt(idx++, box.getInt("p_framecnt"));
            pstmt.setString(idx++, box.getString("p_env"));
            pstmt.setString(idx++, box.getString("p_tutor"));
            pstmt.setString(idx++, box.getStringDefault("p_bookname", ""));
            pstmt.setString(idx++, box.getString("p_sdesc"));
            pstmt.setInt(idx++, box.getInt("p_warndays"));
            pstmt.setInt(idx++, box.getInt("p_stopdays"));
            pstmt.setInt(idx++, box.getInt("p_point"));
            pstmt.setInt(idx++, box.getInt("p_edulimit"));
            pstmt.setInt(idx++, box.getInt("p_gradscore"));
            pstmt.setInt(idx++, box.getInt("p_gradstep"));
            pstmt.setDouble(idx++, box.getDouble("p_wstep"));
            pstmt.setDouble(idx++, box.getDouble("p_wmtest"));
            pstmt.setDouble(idx++, box.getDouble("p_wftest"));
            pstmt.setDouble(idx++, box.getDouble("p_wreport"));
            pstmt.setDouble(idx++, box.getDouble("p_wact"));
            pstmt.setDouble(idx++, box.getDouble("p_wetc1"));
            pstmt.setDouble(idx++, box.getDouble("p_wetc2"));
            pstmt.setString(idx++, box.getStringDefault("p_goyongprice", "0"));
            pstmt.setString(idx++, v_luserid);
            pstmt.setString(idx++, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(idx++, box.getString("p_proposetype"));
            pstmt.setString(idx++, box.getString("p_edumans"));
            pstmt.setString(idx++, box.getString("p_intro"));
            pstmt.setString(idx++, box.getString("p_explain"));
            pstmt.setString(idx++, box.getString("p_contenttype"));

            //------------------------------------------------------------------------------//
            pstmt.setInt(idx++, box.getInt("p_gradexam")); //�̼�����(��)
            pstmt.setInt(idx++, box.getInt("p_gradreport")); //�̼�����(����Ʈ)
            pstmt.setInt(idx++, box.getInt("p_whtest")); //����ġ(������)
            pstmt.setString(idx++, box.getString("p_isablereview")); //�������ɿ���
            pstmt.setInt(idx++, box.getInt("p_score")); //��������
            pstmt.setString(idx++, box.getString("p_isoutsourcing")); //��Ź��������
            pstmt.setString(idx++, box.getString("p_conturl")); //�н�����URL
            pstmt.setString(idx++, box.getStringDefault("p_isessential", "0")); //��������
            pstmt.setString(idx++, box.getString("p_musertel")); //���� ��ȭ��ȣ
            pstmt.setString(idx++, box.getString("p_gradftest")); //�̼�����-������
            pstmt.setString(idx++, box.getString("p_gradhtest")); //�̼�����-������
            pstmt.setString(idx++, box.getStringDefault("p_isvisible", "N")); //�н��ڿ��� �����ֱ�
            pstmt.setString(idx++, box.getString("p_isalledu")); //���米������
            pstmt.setString(idx++, box.getString("p_edutimes")); //�н��ð�

            pstmt.setString(idx++, box.getString("p_isintroduction")); //�н��Ұ� ��뿩��
            pstmt.setInt(idx++, box.getInt("p_eduperiod")); //�н��Ⱓ
            pstmt.setInt(idx++, box.getInt("p_goyongprice_major")); //��뺸�� ȯ�޾�(����)
            pstmt.setInt(idx++, box.getInt("p_goyongprice_minor")); //��뺸�� ȯ�޾�(�߼ұ��)
            pstmt.setString(idx++, v_introducefilenamereal); //�����Ұ� �̹���
            pstmt.setString(idx++, v_introducefilenamenew); //�����Ұ� �̹���
            pstmt.setString(idx++, v_informationfilenamereal); //����(����)
            pstmt.setString(idx++, v_informationfilenamenew); //����(����)
            pstmt.setString(idx++, box.getString("p_contentgrade")); //���������
            pstmt.setString(idx++, box.getString("p_memo")); // ���
            pstmt.setString(idx++, box.getString("p_isordinary")); // ���/����
            pstmt.setString(idx++, box.getString("p_isworkshop")); // ��ũ��
            pstmt.setString(idx++, box.getString("p_isunit")); // ��������
            pstmt.setString(idx++, box.getString("p_sphere")); // �о�(����)
            pstmt.setInt(idx++, box.getInt("p_prewidth")); // ������ â width
            pstmt.setInt(idx++, box.getInt("p_preheight")); // ������ â height
            pstmt.setInt(idx++, box.getInt("p_reviewdays")); // �����Ⱓ
            pstmt.setString(idx++, box.getString("p_reviewtype")); // �����Ⱓ ����(D:��,W:��,M:��,Y:��)

            pstmt.setString(idx++, box.getString("p_subjtype")); // ��������
            pstmt.setString(idx++, box.getString("p_autoconfirm")); // ���ð�������
            pstmt.setString(idx++, box.getString("p_suryoyn")); // ��������°��ɿ���
            pstmt.setString(idx++, box.getString("p_suryotitle")); // ����������
            pstmt.setString(idx++, box.getString("p_needinput")); // ������û �Է�����

            pstmt.setString(idx++, v_submainfilenamereal); //������� ȭ��� �̹���
            pstmt.setString(idx++, v_submainfilenamenew); //������� ȭ��� �̹���

            pstmt.setString(idx++, box.getString("p_area")); // ������û �Է�����
            pstmt.setString(idx++, box.getString("p_degree")); // ��������
            pstmt.setString(idx++, v_hitnumber); // ��õ��
            pstmt.setString(idx++, box.getString("p_search_nm")); // �����˻���

            pstmt.setString(idx++, box.getString("p_mobile_preurl"));
            pstmt.setString(idx++, box.getString("bizType"));
            pstmt.setString(idx++, box.getString("mobileUseYn"));
            pstmt.setString(idx++, box.getString("p_subjsummary"));
            pstmt.setString(idx++, box.getString("p_subjtags"));

            //------------------------------------------------------------------------------//
            pstmt.setString(idx++, box.getString("p_subj"));

            isOk = pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;

            //������ ���� ������ ����
            sql.setLength(0);
            sql.append("/* com.credu.course.SubjectBean UpdateSubject (�¶��� �������� ����) */\n");
            sql.append("UPDATE  TZ_SUBJSEQ  \n");
            sql.append("   SET  SUBJNM = ?  \n");
            sql.append(" WHERE  SUBJ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(1, box.getString("p_subjnm"));
            pstmt.setString(2, box.getString("p_subj"));

            pstmt.executeUpdate();

            Object muser = box.getObject("muser");
            if (muser != null) {
                saveMuser(connMgr, box.getString("p_subj"), muser, v_luserid);
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
        return isOk;
    }

    /**
     * ���õ� �����ڵ� ���� - ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int UpdateOffSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        ListSet ls = null;

        int isOk = 0;
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql2 = "select bookfilenamereal, bookfilenamenew from tz_subj where subj = '" + box.getString("p_subj") + "'";

            //���� ����
            String v_oldbookfilenamereal = "";
            String v_oldbookfilenamenew = "";
            ls = connMgr.executeQuery(sql2);
            if (ls.next()) {
                v_oldbookfilenamereal = ls.getString("bookfilenamereal");
                v_oldbookfilenamenew = ls.getString("bookfilenamenew");
            }

            String v_introducefilenamereal = box.getRealFileName("p_introducefile");
            String v_introducefilenamenew = box.getNewFileName("p_introducefile");
            String v_informationfilenamereal = box.getRealFileName("p_informationfile");
            String v_informationfilenamenew = box.getNewFileName("p_informationfile");

            String v_introducefile0 = box.getStringDefault("p_introducefile0", "0");
            String v_informationfile0 = box.getStringDefault("p_informationfile0", "0");

            if (v_introducefilenamereal.length() == 0) {
                if (v_introducefile0.equals("1")) {
                    v_introducefilenamereal = "";
                    v_introducefilenamenew = "";
                } else {
                    v_introducefilenamereal = box.getString("p_introducefile1");
                    v_introducefilenamenew = box.getString("p_introducefile2");
                }
            }

            if (v_informationfilenamereal.length() == 0) {
                if (v_informationfile0.equals("1")) {
                    v_informationfilenamereal = "";
                    v_informationfilenamenew = "";
                } else {
                    v_informationfilenamereal = box.getString("p_informationfile1");
                    v_informationfilenamenew = box.getString("p_informationfile2");
                }
            }

            //update TZ_SUBJ table
            sql = " update TZ_SUBJ ";
            sql += "    set subjnm       = ?, ";
            sql += "        isonoff      = ?, ";
            sql += "        subjclass    = ?, ";
            sql += "        upperclass   = ?, ";
            sql += "        middleclass  = ?, ";
            sql += "        lowerclass   = ?, ";
            sql += "        specials     = ?, ";
            sql += "        muserid      = ?, ";
            sql += "        cuserid      = ?, ";
            sql += "        isuse        = ?, ";
            sql += "        isgoyong     = ?, ";
            sql += "        ispropose    = ?, ";
            sql += "        biyong       = ?, ";
            sql += "        edudays      = ?, ";
            sql += "        studentlimit = ?, ";
            sql += "        usebook      = ?, ";
            sql += "        bookprice    = ?, ";
            sql += "        owner        = ?, ";
            sql += "        producer     = ?, ";
            sql += "        crdate       = ?, ";
            sql += "        language     = ?, ";
            //sql+= "        server       = ?, ";
            //sql+= "        dir          = ?, ";
            //sql+= "        eduurl       = ?, ";
            //sql+= "        vodurl       = ?, ";
            //sql+= "        preurl       = ?, ";
            sql += "        ratewbt      = ?, ";
            sql += "        ratevod      = ?, ";
            sql += "        framecnt     = ?, ";
            sql += "        env          = ?, ";
            sql += "        tutor        = ?, ";
            sql += "        bookname     = ?, ";
            sql += "        sdesc        = ?, ";
            sql += "        warndays     = ?, ";
            sql += "        stopdays     = ?, ";
            sql += "        point        = ?, ";
            sql += "        edulimit     = ?, ";
            sql += "        gradscore    = ?, ";
            sql += "        gradstep     = ?, ";
            sql += "        wstep        = ?, ";
            sql += "        wmtest       = ?, ";
            sql += "        wftest       = ?, ";
            sql += "        wreport      = ?, ";
            sql += "        wact         = ?, ";
            sql += "        wetc1        = ?, ";
            sql += "        wetc2        = ?, ";
            sql += "        goyongprice  = ?, ";
            sql += "        place        = ?, ";
            sql += "        edumans      = ?, ";
            sql += "        luserid      = ?, ";
            sql += "        ldate        = ?, ";
            sql += "        proposetype  = ?, ";
            sql += "        edutimes     = ?, ";
            sql += "        edutype      = ?, ";
            sql += "        intro        = ?, ";
            sql += "        explain      = ?, ";
            sql += "        bookfilenamereal = ?,";
            sql += "        bookfilenamenew = ?, ";
            sql += "        gradexam = ?, ";
            sql += "        gradreport = ?, ";
            sql += "        isessential          = ?, ";
            sql += "        score                = ?, ";
            sql += "        musertel             = ?,";
            sql += "        gradftest            = ?,";
            sql += "        gradhtest            = ?, ";
            sql += "        isvisible            = ?, ";
            sql += "        isalledu             = ?, ";
            sql += "        placejh              = ?, ";
            sql += "        isintroduction       = ?, ";
            sql += "        eduperiod            = ?, ";
            sql += "        goyongpricemajor     = ?, ";
            sql += "        goyongpriceminor     = ?, ";
            sql += "        introducefilenamereal   = ?, ";
            sql += "        introducefilenamenew    = ?, ";
            sql += "        informationfilenamereal = ?, ";
            sql += "        informationfilenamenew  = ?, ";
            sql += "        memo                    = ?  ";
            sql += "  where subj         = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, box.getString("p_subjnm"));
            pstmt.setString(2, box.getString("p_isonoff"));
            pstmt.setString(3, box.getString("p_upperclass") + box.getString("p_middleclass") + box.getStringDefault("p_lowerclass", "000"));
            pstmt.setString(4, box.getString("p_upperclass"));
            pstmt.setString(5, box.getString("p_middleclass"));
            pstmt.setString(6, box.getStringDefault("p_lowerclass", "000"));
            pstmt.setString(7, box.getString("p_specials"));
            pstmt.setString(8, box.getString("p_muserid"));
            pstmt.setString(9, box.getString("p_cuserid"));
            pstmt.setString(10, box.getString("p_isuse"));
            pstmt.setString(11, box.getString("p_isgoyong"));
            pstmt.setString(12, box.getString("p_ispropose"));
            pstmt.setInt(13, box.getInt("p_biyong"));
            pstmt.setInt(14, box.getInt("p_edudays"));
            pstmt.setInt(15, box.getInt("p_studentlimit"));
            pstmt.setString(16, box.getString("p_usebook"));
            pstmt.setString(17, box.getStringDefault("p_bookprice", "0"));
            pstmt.setString(18, box.getString("p_owner"));
            pstmt.setString(19, box.getString("p_producer"));
            pstmt.setString(20, box.getString("p_crdate"));
            pstmt.setString(21, box.getString("p_language"));
            //pstmt.setString(22, box.getString("p_server"));
            //pstmt.setString(22, box.getString("p_dir"));
            //pstmt.setString(24, box.getString("p_eduurl"));
            //pstmt.setString(25, box.getString("p_vodurl"));
            //pstmt.setString(26, box.getString("p_preurl"));
            pstmt.setInt(22, box.getInt("p_ratewbt"));
            pstmt.setInt(23, box.getInt("p_ratevod"));
            pstmt.setInt(24, box.getInt("p_framecnt"));
            pstmt.setString(25, box.getString("p_env"));
            pstmt.setString(26, box.getString("p_tutor"));
            pstmt.setString(27, box.getStringDefault("p_bookname", ""));
            pstmt.setString(28, box.getString("p_sdesc"));
            pstmt.setInt(29, box.getInt("p_warndays"));
            pstmt.setInt(30, box.getInt("p_stopdays"));
            pstmt.setInt(31, box.getInt("p_point"));
            pstmt.setInt(32, box.getInt("p_edulimit"));
            //
            pstmt.setInt(33, box.getInt("p_gradscore"));
            //pstmt.setDouble   (33, box.getDouble("p_gradscore"));

            pstmt.setInt(34, box.getInt("p_gradstep"));
            pstmt.setDouble(35, box.getDouble("p_wstep"));
            pstmt.setDouble(36, box.getDouble("p_wmtest"));
            pstmt.setDouble(37, box.getDouble("p_wftest"));
            pstmt.setDouble(38, box.getDouble("p_wreport"));
            pstmt.setDouble(39, box.getDouble("p_wact"));
            pstmt.setDouble(40, box.getDouble("p_wetc1"));
            pstmt.setDouble(41, box.getDouble("p_wetc2"));
            pstmt.setString(42, box.getStringDefault("p_goyongprice", "0"));
            pstmt.setString(43, box.getString("p_place"));
            pstmt.setString(44, box.getString("p_edumans"));
            pstmt.setString(45, v_luserid);
            pstmt.setString(46, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(47, box.getString("p_proposetype"));
            pstmt.setInt(48, box.getInt("p_edutimes"));
            pstmt.setString(49, box.getString("p_edutype"));
            pstmt.setString(50, box.getString("p_intro"));
            pstmt.setString(51, box.getString("p_explain"));
            //System.out.println("p_deletefile = " + box.getString("p_deletefile"));

            if (box.getString("p_deletefile").equals("Y")) {
                pstmt.setString(52, ""); //�������ϸ�
                pstmt.setString(53, ""); //��������DB��
            } else {
                if (!box.getRealFileName("p_file").equals("")) {
                    pstmt.setString(52, box.getRealFileName("p_file")); //�������ϸ�
                    pstmt.setString(53, box.getNewFileName("p_file")); //��������DB��
                } else {
                    pstmt.setString(52, v_oldbookfilenamereal); //�������ϸ�
                    pstmt.setString(53, v_oldbookfilenamenew); //��������DB��
                }
            }

            pstmt.setInt(54, box.getInt("p_gradexam")); //�̼�����(��)
            pstmt.setInt(55, box.getInt("p_gradreport")); //�̼�����(����Ʈ)
            pstmt.setString(56, box.getStringDefault("p_isessential", "0")); //��������
            //
            //pstmt.setString(65, box.getString("p_score"));              //
            pstmt.setDouble(57, box.getDouble("p_score")); //

            pstmt.setString(58, box.getString("p_musertel")); //���� ��ȭ��ȣ
            pstmt.setString(59, box.getString("p_gradftest")); //�̼�����-������
            pstmt.setString(60, box.getString("p_gradhtest")); //�̼�����-������
            pstmt.setString(61, box.getStringDefault("p_isvisible", "N")); //�н��ڿ��� �����ֱ�
            pstmt.setString(62, box.getString("p_isalledu")); //���米������
            pstmt.setString(63, box.getString("p_placejh")); //�������

            pstmt.setString(64, box.getString("p_isintroduction")); //�н��Ұ� ��뿩��
            pstmt.setInt(65, box.getInt("p_eduperiod")); //�н��Ⱓ
            pstmt.setInt(66, box.getInt("p_goyongprice_major")); //��뺸�� ȯ�޾�(����)
            pstmt.setInt(67, box.getInt("p_goyongprice_minor")); //��뺸�� ȯ�޾�(�߼ұ��)
            pstmt.setString(68, v_introducefilenamereal); //�����Ұ� �̹���
            pstmt.setString(69, v_introducefilenamenew); //�����Ұ� �̹���
            pstmt.setString(70, v_informationfilenamereal); //����(����)
            pstmt.setString(71, v_informationfilenamenew); //����(����)
            pstmt.setString(72, box.getString("p_memo")); // ���
            pstmt.setString(73, box.getString("p_subj"));

            isOk = pstmt.executeUpdate();

            //������ ���� ������ ����
            sql3 = "update tz_subjseq  set subjnm = ? where subj = ? ";
            pstmt2 = connMgr.prepareStatement(sql3);

            pstmt2.setString(1, box.getString("p_subjnm"));
            pstmt2.setString(2, box.getString("p_subj"));

            pstmt2.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

            //box.put("s_upperclass",box.getString("p_upperclass"));
            //box.put("s_middleclass",box.getString("p_middleclass"));

        } catch (Exception ex) {
            connMgr.rollback();
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
        return isOk;
    }

    /**
     * ���õ� �����ڵ� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int DeleteSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        //		int    isOk1 = 0;
        //        int    isOk2 = 0;
        //        int    isOk3 = 0;
        int isOk4 = 0;

        String v_subj = box.getString("p_subj");
        //        String v_contenttype = box.getString("p_contenttype");

        try {
            connMgr = new DBConnectionManager();

            //delete TZ_GRSUBJ table
            sql1 = "delete from TZ_GRSUBJ where subjcourse = ?";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_subj);
            //            isOk1 =
            pstmt1.executeUpdate();

            //delete TZ_BDS table
            sql2 = "delete from TZ_BDS where subj = ? and type ='SD' ";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_subj);
            //            isOk2 =
            pstmt2.executeUpdate();

            //delete TZ_SUBJ table
            sql3 = "delete from TZ_PREVIEW where subj = ? ";
            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_subj);
            //            isOk3 =
            pstmt3.executeUpdate();

            //delete TZ_SUBJ table
            sql4 = "delete from TZ_SUBJ where subj = ? ";
            pstmt4 = connMgr.prepareStatement(sql4);
            pstmt4.setString(1, v_subj);
            isOk4 = pstmt4.executeUpdate();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql4);
            throw new Exception("sql4 = " + sql4 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e2) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e3) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e4) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk4;
    }

    /**
     * �����׷� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����׷� ����Ʈ
     */
    public ArrayList TargetGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SelectionData data = null;

        try {
            sql = "select grcode code, grcodenm name ";
            sql += "  from tz_grcode ";
            sql += " order by grcode ";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

                list.add(data);
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
     * ���õ� �����׷� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����׷� ����Ʈ
     */
    public ArrayList SelectedGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SelectionData data = null;
        String v_subj = box.getString("p_subj");

        try {
            sql = "select a.grcode code, a.grcodenm name ";
            sql += "  from tz_grcode a, ";
            sql += "       tz_grsubj b  ";
            sql += " where a.grcode = b.grcode ";
            sql += "   and b.subjcourse = " + SQLString.Format(v_subj);
            sql += " order by a.grcode ";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

                list.add(data);
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
     * ���ο� �ڽ��ڵ� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int RelatedGrcodeInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        int isOk = 0;
        //        int isOk2 = 0;
        //        int isOk3 = 0;

        String v_subj = box.getString("p_subj");
        String v_grcode = "";
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //delete TZ_GRSUBJ table
            sql = " delete from TZ_GRSUBJ where subjcourse = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            isOk = pstmt.executeUpdate();

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

            String v_selectedgrcodes = box.getString("p_selectedgrcodes");
            StringTokenizer v_token = new StringTokenizer(v_selectedgrcodes, ";");

            //insert TZ_COURSESUBJ table
            sql = " insert into TZ_GRSUBJ(grcode, subjcourse, isnew, disseq, grpcode, grpname, luserid, ldate) ";
            sql += " values (?, ?, ?, ?, ?, ?, ?, ?)";

            //insert TZ_PREVIEW table
            //tz_preview ���̺��� tz_grsubj ���̺�ó�� ��� ����Ÿ ���� �� Insert���� �ʰ�
            //Insert�Ϸ��� ����Ÿ�� ���� ��쿡�� tz_subj ���̺��� ������ ������ tz_preview�� Insert�Ѵ�.
            sql2 = " insert into tz_preview(grcode, subj, edumans, intro, explain, memo, luserid, ldate ) ";
            sql2 += " select ?, subj, edumans,  intro, explain, memo, ?, ?                                 ";
            sql2 += "   from tz_subj a                                                                     ";
            sql2 += "  where subj    = '" + v_subj + "'                                                    ";
            sql2 += "    and not exists( select  grcode, subj                                              ";
            sql2 += "                      from  tz_preview b                                              ";
            sql2 += "                     where   b.grcode = ?                                             ";
            sql2 += "                       and   b.subj   = '" + v_subj + "')                             ";

            //�����ְ������ ����Ǿ� �ִ� �����ְ��� �����ϴ� ���
            //tz_grsubj ���̺� �����Ƿ� tz_preview ���̺� �ִ� �ڷᵵ �����Ѵ�.
            /*
             * 2005.11.08_�ϰ��� : mssql ���� ������ ��ü sql3 = " delete "; sql3+=
             * " from    tz_preview a "; sql3+= " where   a.subj = '" + v_subj +
             * "' and "; sql3+= "        not exists( select  grcode,"; sql3+=
             * "                            subj "; sql3+=
             * "                    from    tz_grsubj "; sql3+=
             * "                    where   grcode  = a.grcode and "; sql3+=
             * "                            subj    = a.subj)";
             */
            sql3 = "  delete  from    tz_preview  ";
            sql3 += "  where   subj = '" + v_subj + "' and not exists ";
            sql3 += "  	( ";
            sql3 += "  	select  g.grcode,  a.subj ";
            sql3 += "  	from    tz_grsubj g, tz_preview a ";
            sql3 += "  	where   g.grcode  = a.grcode and    g.subjcourse    = a.subj";
            sql3 += "  )";

            pstmt = connMgr.prepareStatement(sql);
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt3 = connMgr.prepareStatement(sql3);

            while (v_token.hasMoreTokens()) {
                v_grcode = v_token.nextToken();

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, "Y");
                pstmt.setInt(4, 0);
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setString(7, v_luserid);
                pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
                isOk = pstmt.executeUpdate();

                pstmt2.setString(1, v_grcode);
                pstmt2.setString(2, v_luserid);
                pstmt2.setString(3, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt2.setString(4, v_grcode);
                //                isOk2 =
                pstmt2.executeUpdate();
                //isOk2 = 1;

                if (isOk == 1) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }

            pstmt3.executeUpdate();
            connMgr.commit();
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
        return isOk;
    }

    /**
     * ���������� ��ϵ� �����׷� ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����׷� ����Ʈ
     */
    public ArrayList PreviewGrcodeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SelectionData data = null;
        String v_subj = box.getString("p_subj");
        System.out.println("p_subj=" + box.getString("p_subj"));

        try {
            sql = "select a.grcode code, a.grcodenm name ";
            sql += "  from tz_grcode  a, ";
            sql += "       tz_preview b  ";
            sql += " where a.grcode = b.grcode ";
            sql += "   and b.subj = " + SQLString.Format(v_subj);
            sql += " order by a.grcode ";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);
            System.out.println("sql ->" + sql);

            while (ls.next()) {
                data = new SelectionData();

                data.setCode(ls.getString("code"));
                System.out.println("code=" + ls.getString("code"));
                data.setName(ls.getString("name"));
                System.out.println("name=" + ls.getString("name"));

                list.add(data);
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
     * ���������� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertPreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //insert TZ_SUBJ table
            sql = "insert into TZ_PREVIEW ( grcode, subj, subjtext, edumans, intro,  ";
            sql += "                         explain, memo, expect, master, masemail, ";
            sql += "                         recommender, recommend, luserid, ldate)  ";
            sql += "                values ( ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )        ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, box.getString("p_grcode"));
            pstmt.setString(2, box.getString("p_subj"));
            pstmt.setString(3, box.getString("p_subjtext"));
            pstmt.setString(4, box.getString("p_edumans"));
            pstmt.setString(5, box.getString("p_intro"));
            pstmt.setString(6, box.getString("p_explain"));
            pstmt.setString(7, box.getString("p_memo"));
            pstmt.setString(8, box.getString("p_expect"));
            pstmt.setString(9, box.getString("p_master"));
            pstmt.setString(10, box.getString("p_masemail"));
            pstmt.setString(11, box.getString("p_recommender"));
            pstmt.setString(12, box.getString("p_recommend"));
            pstmt.setString(13, v_luserid);
            pstmt.setString(14, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            connMgr.rollback();
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
     * ��������Ÿ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return PreviewData ��������Ÿ
     */
    public PreviewData SelectPreviewData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreviewData data = null;
        String sql = "";
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");

        try {
            sql = " select grcode, subj, subjtext, edumans, intro, explain, expect, memo, ";
            sql += "        master, masemail, recommender, recommend, luserid, ldate       ";
            sql += "   from TZ_PREVIEW ";
            sql += "  where grcode = " + SQLString.Format(v_grcode);
            sql += "    and subj   = " + SQLString.Format(v_subj);

            connMgr = new DBConnectionManager();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new PreviewData();
                data.setGrcode(ls.getString("grcode"));
                data.setSubj(ls.getString("subj"));
                data.setSubjtext(ls.getString("subjtext"));
                data.setEdumans(ls.getString("edumans"));
                data.setIntro(ls.getString("intro"));
                data.setExplain(ls.getString("explain"));
                data.setExpect(ls.getString("expect"));
                data.setMemo(ls.getString("memo"));
                data.setMaster(ls.getString("master"));
                data.setMasemail(ls.getString("masemail"));
                data.setRecommender(ls.getString("recommender"));
                data.setRecommend(ls.getString("recommend"));
                data.setLuserid(ls.getString("luserid"));
                data.setLdate(ls.getString("ldate"));
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
        return data;
    }

    /**
     * ���������� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int UpdatePreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        //        String v_grcode = box.getString("p_grcode");
        //        String v_subj   = box.getString("p_subj");
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //insert TZ_SUBJ table
            sql = "update TZ_PREVIEW ";
            sql += "   set subjtext   = ?, ";
            sql += "       edumans    = ?, ";
            sql += "       intro      = ?, ";
            sql += "       explain    = ?, ";
            sql += "       memo       = ?, ";
            sql += "       expect     = ?, ";
            sql += "       master     = ?, ";
            sql += "       masemail   = ?, ";
            sql += "       recommender= ?, ";
            sql += "       recommend  = ?, ";
            sql += "       luserid    = ?, ";
            sql += "       ldate      = ?  ";
            sql += " where grcode     = ?  ";
            sql += "   and subj       = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, box.getString("p_subjtext"));
            pstmt.setString(2, box.getString("p_edumans"));
            pstmt.setString(3, box.getString("p_intro"));
            pstmt.setString(4, box.getString("p_explain"));
            pstmt.setString(5, box.getString("p_memo"));
            pstmt.setString(6, box.getString("p_expect"));
            pstmt.setString(7, box.getString("p_master"));
            pstmt.setString(8, box.getString("p_masemail"));
            pstmt.setString(9, box.getString("p_recommender"));
            pstmt.setString(10, box.getString("p_recommend"));
            pstmt.setString(11, v_luserid);
            pstmt.setString(12, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(13, box.getString("p_grcode"));
            pstmt.setString(14, box.getString("p_subj"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            connMgr.rollback();
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
     * ���õ� ���������� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int DeletePreview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");

        try {
            connMgr = new DBConnectionManager();
            //delete TZ_SUBJ table
            sql = "delete from TZ_PREVIEW where grcode = ? and subj = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_subj);
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * ���� ��������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ��������Ʈ
     */
    public ArrayList RelatedSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        SelectionData data = null;

        String v_grcode = box.getStringDefault("p_grcode", "N000001");
        String v_subj = box.getString("p_subj");
        String v_subjgubun = box.getString("p_subjgubun");

        try {
            sql = "select a.rsubj code, b.subjnm name";
            sql += "  from tz_subjrelate a, ";
            sql += "       tz_subj   b   ";
            sql += " where a.rsubj = b.subj  ";
            sql += "   and a.grcode = " + SQLString.Format(v_grcode);
            sql += "   and a.subj   = " + SQLString.Format(v_subj);
            sql += "   and a.gubun  = " + SQLString.Format(v_subjgubun);
            sql += " order by a.subj, b.subjnm";

            connMgr = new DBConnectionManager();
            list = new ArrayList();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SelectionData();

                data.setCode(ls.getString("code"));
                data.setName(ls.getString("name"));

                list.add(data);
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
     * ���õ� ���� ��������Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ��������Ʈ
     */
    public ArrayList SelectedRelatedSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        ArrayList list1 = null;
        list1 = new ArrayList();
        SubjectInfoData data = null;

        String v_subjectcodes = box.getString("p_selectedsubjcodes");
        String v_subjecttexts = box.getString("p_selectedsubjtexts");
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        String v_subjgubun = box.getString("p_subjgubun");

        try {
            if (!v_subjectcodes.equals("")) {
                StringTokenizer v_tokencode = new StringTokenizer(v_subjectcodes, ";");
                StringTokenizer v_tokentext = new StringTokenizer(v_subjecttexts, ";");

                String v_code = "";
                String v_text = "";

                while (v_tokencode.hasMoreTokens() && v_tokentext.hasMoreTokens()) {
                    v_code = v_tokencode.nextToken();
                    v_text = v_tokentext.nextToken();

                    data = new SubjectInfoData();

                    data.setSubj(v_code);
                    data.setDisplayname(v_text);

                    list1.add(data);
                }
            } else {
                if (!v_subj.equals("")) {

                    sql = "select c.rsubj, a.subjnm, a.isonoff, b.upperclass, b.classname, d.codenm ";
                    sql += "  from tz_subj         a, ";
                    sql += "       tz_subjatt      b, ";
                    sql += "       tz_subjrelate   c, ";
                    sql += "       tz_code         d  ";
                    sql += " where a.subjclass = b.subjclass ";
                    sql += "   and a.subj   = c.rsubj ";
                    sql += "   and a.isonoff= d.code ";
                    sql += "   and c.grcode = " + SQLString.Format(v_grcode);
                    sql += "   and c.subj   = " + SQLString.Format(v_subj);
                    sql += "   and c.gubun  = " + SQLString.Format(v_subjgubun);
                    sql += "   and d.gubun  = " + SQLString.Format(ONOFF_GUBUN);
                    sql += " order by c.subj ";

                    connMgr = new DBConnectionManager();
                    list1 = new ArrayList();
                    ls = connMgr.executeQuery(sql);

                    while (ls.next()) {
                        data = new SubjectInfoData();

                        data.setSubj(ls.getString("rsubj"));
                        data.setSubjnm(ls.getString("subjnm"));
                        data.setIsonoff(ls.getString("isonoff"));
                        data.setUpperclass(ls.getString("upperclass"));
                        data.setClassname(ls.getString("classname"));
                        data.setIsonoffnm(ls.getString("codenm"));

                        list1.add(data);

                    }
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list1;
    }

    /**
     * ������� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int RelatedSubjInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        String v_subjgubun = box.getString("p_subjgubun");
        String v_luserid = box.getSession("userid");
        String v_rsubj = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //delete TZ_SUBJRELATE table
            sql = "delete from TZ_SUBJRELATE where grcode = ? and subj = ? and gubun = ?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_grcode);
            pstmt.setString(2, v_subj);
            pstmt.setString(3, v_subjgubun);

            isOk = pstmt.executeUpdate();

            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

            String v_subjectcodes = box.getString("p_selectedsubjcodes");
            StringTokenizer v_token = new StringTokenizer(v_subjectcodes, ";");

            //insert TZ_SUBJRELATE table
            sql = "insert into TZ_SUBJRELATE(grcode, subj, gubun, rsubj, luserid, ldate) ";
            sql += " values (?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while (v_token.hasMoreTokens()) {
                v_rsubj = v_token.nextToken();

                pstmt.setString(1, v_grcode);
                pstmt.setString(2, v_subj);
                pstmt.setString(3, v_subjgubun);
                pstmt.setString(4, v_rsubj);
                pstmt.setString(5, v_luserid);
                pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
                isOk = pstmt.executeUpdate();
            }

            connMgr.commit();
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
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
        return isOk;
    }

    /**
     * ���������� TZ_BDS�� ���
     * 
     * @param connmgr, v_subj receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int InsertBds(DBConnectionManager connMgr, String v_subj) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        ListSet ls = null;
        String v_luserid = "SYSTEM";
        int v_tabseq;

        try {
            //connMgr = new DBConnectionManager();
            sql = "select isnull(max(tabseq),0)+1 from tz_bds";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_tabseq = ls.getInt(1);
            } else {
                v_tabseq = 1;
            }

            //insert TZ_SUBJ table
            sql = "insert into tz_bds (tabseq, type, grcode, comp, subj , ";
            sql += "                    year, subjseq, sdesc, ldesc, status, ";
            sql += "                    luserid, ldate) ";
            sql += " values ( ?,    ?,    ?,    ?, ";
            sql += " ?,    ?,    ?,    ?, ";
            sql += " ?,    ?,    ?,    ?) ";
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setInt(1, v_tabseq);
            pstmt.setString(2, "SD"); //������ �ڷ��
            pstmt.setString(3, "0000000");
            pstmt.setString(4, "0000000000");
            pstmt.setString(5, v_subj);
            pstmt.setString(6, "0000");
            pstmt.setString(7, "0000");
            pstmt.setString(8, v_subj + "���������ڷ��");
            pstmt.setString(9, "");
            pstmt.setString(10, "A");
            pstmt.setString(11, v_luserid);
            pstmt.setString(12, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            //if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

}
