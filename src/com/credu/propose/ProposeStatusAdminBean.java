//**********************************************************
//  1. 제      목: PROPOSE STATUS ADMIN BEAN
//  2. 프로그램명: ProposeStatusAdminBean.java
//  3. 개      요: 신청 현황 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//**********************************************************
package com.credu.propose;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class ProposeStatusAdminBean {
    private ConfigSet config;
    private int row;

    public ProposeStatusAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 신청명단 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        // ArrayList list = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;

        // 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql1 = "";
        String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String group_sql1 = "";
        String order_sql1 = "";

        // String sql1 = "";
        // String sql2 = "";
        // ProposeStatusData data1 = null;
        // ProposeStatusData data2 = null;
        // String v_Bcourse = ""; //이전코스
        // String v_course = ""; //현재코스
        // String v_Bcourseseq = ""; //이전코스차수
        // String v_courseseq = ""; //현재코스차수
        int v_pageno = box.getInt("p_pageno");
        // int l = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_grseqnm = box.getStringDefault("s_grseqnm", "ALL"); //교육차수명
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_edustart = box.getStringDefault("s_edustart", ""); //교육시작일
        String ss_eduend = box.getStringDefault("s_eduend", ""); //교육종료일
        // String ss_seltext = box.getStringDefault("s_seltext", "ALL"); //검색분류별 검색내용
        String ss_action = box.getString("s_action");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        // ManagerAdminBean bean = null;
        // String v_sql_add = "";
        // String v_userid = box.getSession("userid");
        // String s_gadmin = box.getSession("gadmin");

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();
                // list2 = new ArrayList();

                head_sql1 = " Select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,C.subjseqgr,";
                head_sql1 += " B.membergubun, B.userid,B.cono,B.name,A.isproposeapproval,A.appdate,C.edustart,C.eduend,A.chkfirst,A.chkfinal,";
                head_sql1 += " crypto.dec('normal',B.email) email,B.ismailing,C.isonoff, C.isbelongcourse, C.subjcnt, ";
                head_sql1 += " (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm ";
                body_sql1 += " from TZ_PROPOSE A,TZ_MEMBER B,VZ_SCSUBJSEQ C where 1 = 1  ";

                if (!ss_grcode.equals("ALL"))
                    body_sql1 += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_grseq.equals("ALL"))
                    body_sql1 += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    body_sql1 += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_subjcourse.equals("ALL"))
                    body_sql1 += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    body_sql1 += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);
                // 수정일 : 05.11.07 수정자 : 이나연 _substr 수정
                if (!ss_edustart.equals(""))
                    body_sql1 += " and C.edustart >= " + SQLString.Format(ss_edustart + "00"); //자리수 맞춤
                if (!ss_eduend.equals(""))
                    body_sql1 += " and C.eduend <= " + SQLString.Format(ss_eduend + "00");

                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("") && ss_eduend.equals(""))
                    body_sql1 += " and C.gyear = " + SQLString.Format(ss_gyear);

                body_sql1 += " and NVL(A.cancelkind,' ') not in ('P','F')  and A.appdate is not null ";
                body_sql1 += " and A.userid=B.userid and B.grcode = C.grcode and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";

                if (v_orderColumn.equals("grseq"))
                    v_orderColumn = "C.grseq";
                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "C.subj";
                if (v_orderColumn.equals("userid"))
                    v_orderColumn = "b.userid ";
                if (v_orderColumn.equals("name"))
                    v_orderColumn = "b.name ";

                if (v_orderColumn.equals("")) {
                    order_sql1 += " order by C.course,C.subj,C.year,C.subjseq";
                } else {
                    order_sql1 += " order by C.course," + v_orderColumn + v_orderType;
                }

                sql1 = head_sql1 + body_sql1 + group_sql1 + order_sql1;

                System.out.println(" propose sql1 >>>>>  " + sql1);
                ls1 = connMgr.executeQuery(sql1);

                count_sql1 = "select count(*)" + body_sql1;
                System.out.println(" propose count1 >>> " + count_sql1);
                int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1);

                row = 10;
                ls1.setPageSize(row); //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
                int totalpagecount = ls1.getTotalPage(); //전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  	//전체 row 수를 반환한다

                while (ls1.next()) {
                    dbox = ls1.getDataBox();

                    dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(totalpagecount));
                    dbox.put("d_rowcount", new Integer(row));
                    dbox.put("d_total_row_count", new Integer(total_row_count));

                    if (ss_grcode.equals("N000001")) {
                        //====================================================
                        // 개인정보 복호화 - HTJ
                        /*
                         * SeedCipher seed = new SeedCipher(); if
                         * (!dbox.getString("d_resno2").equals(""))
                         * dbox.put("d_resno2"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_resno2")), seed.key.getBytes(),
                         * "UTF-8")); if (!dbox.getString("d_email").equals(""))
                         * dbox
                         * .put("d_email",seed.decryptAsString(Base64.decode(
                         * dbox.getString("d_email")), seed.key.getBytes(),
                         * "UTF-8")); if
                         * (!dbox.getString("d_hometel").equals(""))
                         * dbox.put("d_hometel"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_hometel")), seed.key.getBytes(),
                         * "UTF-8")); if
                         * (!dbox.getString("d_handphone").equals(""))
                         * dbox.put("d_handphone"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_handphone")), seed.key.getBytes(),
                         * "UTF-8")); if (!dbox.getString("d_addr2").equals(""))
                         * dbox
                         * .put("d_addr2",seed.decryptAsString(Base64.decode(
                         * dbox.getString("d_addr2")), seed.key.getBytes(),
                         * "UTF-8"));
                         * 
                         * 
                         * if (!dbox.getString("d_resno2").equals(""))
                         * dbox.put("d_resno2",
                         * encryptUtil.decrypt(dbox.getString("d_resno2"))); if
                         * (!dbox.getString("d_email").equals(""))
                         * dbox.put("d_email",
                         * encryptUtil.decrypt(dbox.getString("d_email"))); if
                         * (!dbox.getString("d_hometel").equals(""))
                         * dbox.put("d_hometel",
                         * encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                         * (!dbox.getString("d_handphone").equals(""))
                         * dbox.put("d_handphone",
                         * encryptUtil.decrypt(dbox.getString("d_handphone")));
                         * if (!dbox.getString("d_addr2").equals(""))
                         * dbox.put("d_addr2",
                         * encryptUtil.decrypt(dbox.getString("d_addr2")));
                         */
                        //====================================================
                    }

                    list1.add(dbox);
                }

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * 신청명단 리스트(Excel)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeExcelMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        // ArrayList list = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;

        // 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql1 = "";
        // String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String group_sql1 = "";
        String order_sql1 = "";

        // String sql1 = "";
        // String sql2 = "";
        // ProposeStatusData data1 = null;
        // ProposeStatusData data2 = null;
        // String v_Bcourse = ""; //이전코스
        // String v_course = ""; //현재코스
        // String v_Bcourseseq = ""; //이전코스차수
        // String v_courseseq = ""; //현재코스차수
        // int v_pageno = box.getInt("p_pageno");
        // int l = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_grseqnm = box.getStringDefault("s_grseqnm", "ALL"); //교육차수명
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_edustart = box.getStringDefault("s_edustart", ""); //교육시작일
        String ss_eduend = box.getStringDefault("s_eduend", ""); //교육종료일
        // String ss_seltext = box.getStringDefault("s_seltext", "ALL"); //검색분류별 검색내용
        String ss_action = box.getString("s_action");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        // ManagerAdminBean bean = null;
        // String v_sql_add = "";
        // String v_userid = box.getSession("userid");
        // String s_gadmin = box.getSession("gadmin");

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();
                // list2 = new ArrayList();

                head_sql1 = " Select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,C.subjseqgr,";
                head_sql1 += " B.membergubun, B.userid,B.cono,B.name,A.isproposeapproval,A.appdate,C.edustart,C.eduend,A.chkfirst,A.chkfinal,";
                head_sql1 += " crypto.dec('normal',B.email) email,B.ismailing,C.isonoff, C.isbelongcourse, C.subjcnt, ";
                head_sql1 += " (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm ";
                body_sql1 += " from TZ_PROPOSE A,TZ_MEMBER B,VZ_SCSUBJSEQ C where 1 = 1  ";

                if (!ss_grcode.equals("ALL"))
                    body_sql1 += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_grseq.equals("ALL"))
                    body_sql1 += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    body_sql1 += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_subjcourse.equals("ALL"))
                    body_sql1 += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    body_sql1 += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);
                // 수정일 : 05.11.07 수정자 : 이나연 _substr 수정
                if (!ss_edustart.equals(""))
                    body_sql1 += " and C.edustart >= " + SQLString.Format(ss_edustart + "00"); //자리수 맞춤
                if (!ss_eduend.equals(""))
                    body_sql1 += " and C.eduend <= " + SQLString.Format(ss_eduend + "00");

                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("") && ss_eduend.equals(""))
                    body_sql1 += " and C.gyear = " + SQLString.Format(ss_gyear);

                body_sql1 += " and NVL(A.cancelkind,' ') not in ('P','F') and A.appdate is not null ";
                body_sql1 += " and A.userid=B.userid and B.grcode = C.grcode and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";

                if (v_orderColumn.equals("grseq"))
                    v_orderColumn = "C.grseq";
                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "C.subj";
                if (v_orderColumn.equals("userid"))
                    v_orderColumn = "b.userid ";
                if (v_orderColumn.equals("name"))
                    v_orderColumn = "b.name ";

                if (v_orderColumn.equals("")) {
                    order_sql1 += " order by C.course, C.subj,C.year,C.subjseq";
                } else {
                    order_sql1 += " order by C.course, " + v_orderColumn + v_orderType;
                }

                sql1 = head_sql1 + body_sql1 + group_sql1 + order_sql1;

                System.out.println(" propose sql1 >>>>>  " + sql1);
                ls1 = connMgr.executeQuery(sql1);
                while (ls1.next()) {
                    dbox = ls1.getDataBox();

                    if (ss_grcode.equals("N000001")) {
                        //====================================================
                        // 개인정보 복호화
                        /*
                         * SeedCipher seed = new SeedCipher(); if
                         * (!dbox.getString("d_resno2").equals(""))
                         * dbox.put("d_resno2"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_resno2")), seed.key.getBytes(),
                         * "UTF-8")); if (!dbox.getString("d_email").equals(""))
                         * dbox
                         * .put("d_email",seed.decryptAsString(Base64.decode(
                         * dbox.getString("d_email")), seed.key.getBytes(),
                         * "UTF-8")); if
                         * (!dbox.getString("d_hometel").equals(""))
                         * dbox.put("d_hometel"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_hometel")), seed.key.getBytes(),
                         * "UTF-8")); if
                         * (!dbox.getString("d_handphone").equals(""))
                         * dbox.put("d_handphone"
                         * ,seed.decryptAsString(Base64.decode
                         * (dbox.getString("d_handphone")), seed.key.getBytes(),
                         * "UTF-8")); if (!dbox.getString("d_addr2").equals(""))
                         * dbox
                         * .put("d_addr2",seed.decryptAsString(Base64.decode(
                         * dbox.getString("d_addr2")), seed.key.getBytes(),
                         * "UTF-8"));
                         */
                        /*
                         * if (!dbox.getString("d_resno2").equals(""))
                         * dbox.put("d_resno2",
                         * encryptUtil.decrypt(dbox.getString("d_resno2"))); if
                         * (!dbox.getString("d_email").equals(""))
                         * dbox.put("d_email",
                         * encryptUtil.decrypt(dbox.getString("d_email"))); if
                         * (!dbox.getString("d_hometel").equals(""))
                         * dbox.put("d_hometel",
                         * encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                         * (!dbox.getString("d_handphone").equals(""))
                         * dbox.put("d_handphone",
                         * encryptUtil.decrypt(dbox.getString("d_handphone")));
                         * if (!dbox.getString("d_addr2").equals(""))
                         * dbox.put("d_addr2",
                         * encryptUtil.decrypt(dbox.getString("d_addr2")));
                         */
                        //====================================================
                    }

                    list1.add(dbox);
                }

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * 신청취소명단 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeCancelMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        // ArrayList list2 = null;
        DataBox dbox = null;
        // 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql2 = "";
        String count_sql2 = "";
        String head_sql2 = "";
        String body_sql2 = "";
        String group_sql2 = "";
        String order_sql2 = "";

        // String sql1 = "";
        // String sql2 = "";
        // ProposeStatusData data1 = null;
        // ProposeStatusData data2 = null;
        // String v_Bcourse = ""; //이전코스
        // String v_course = ""; //현재코스
        // String v_Bcourseseq = ""; //이전코스차수
        // String v_courseseq = ""; //현재코스차수
        int v_pageno = box.getInt("p_pageno");
        // int l = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_grseqnm = box.getStringDefault("s_grseqnm", "ALL"); //교육차수명
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        //String  ss_company    = box.getStringDefault("s_company","ALL");     //회사
        String ss_edustart = box.getStringDefault("s_edustart", ""); //교육시작일
        String ss_eduend = box.getStringDefault("s_eduend", ""); //교육종료일
        //String  ss_selgubun   = box.getString("s_selgubun");                 //직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        // String ss_seltext = box.getStringDefault("s_seltext", "ALL"); //검색분류별 검색내용
        //String  ss_seldept    = box.getStringDefault("s_seldept","ALL");     //사업부별 부서 검색내용
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서
        String search_userid = box.getString("s_userid"); //검색할 사용자ID
        String search_username = box.getString("s_username"); //검색할 사용자 이름
        String v_area = box.getString("p_area"); //교육구분

        // ManagerAdminBean bean = null;
        // String v_sql_add = "";
        // String v_userid = box.getSession("userid");
        // String s_gadmin = box.getSession("gadmin");

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();
                // list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,compnm,
                //jikwinm,userid,cono,name,canceldate,cancelkind,reason,email,ismailing,isonoff
                head_sql2 = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq, C.subjseqgr, ";
                head_sql2 += "       B.membergubun, B.userid,B.cono,B.name,A.canceldate,A.cancelkind,A.reason, ";
                head_sql2 += "       B.email,B.ismailing,C.isonoff,  ";
                head_sql2 += "       (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm ";
                head_sql2 += " 		, NVL(D.paymoney, 0) as paymoney, D.paystat , C.isbelongcourse, C.area ";
                //sql1+= "from TZ_CANCEL A,TZ_MEMBER B,VZ_SCSUBJSEQ C, TZ_PROPOSE D ";
                body_sql2 += " from TZ_CANCEL A,TZ_MEMBER B,VZ_SCSUBJSEQ C  , TZ_BILLING D";
                //sql1+= " where a.subj=d.subj and a.year=d.year and a.subjseq=d.subjseq and a.userid=d.userid and d.cancelkind='P'  ";
                body_sql2 += " where A.userid=B.userid and B.grcode = C.grcode and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                body_sql2 += " 	and A.userid  =  D.userid(+) and A.subj  =  D.subj(+) and A.year  =  D.year(+) and A.subjseq  =  D.subjseq(+) ";

                if (!ss_grcode.equals("ALL"))
                    body_sql2 += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_grseq.equals("ALL"))
                    body_sql2 += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_gyear.equals("ALL"))
                    body_sql2 += " and C.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_uclass.equals("ALL"))
                    body_sql2 += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_subjcourse.equals("ALL"))
                    body_sql2 += " and C.scsubj = " + SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))
                    body_sql2 += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);
                // 수정일 : 05.11.07 수정자 : 이나연 _substr 수정
                //              if (!ss_company.equals("ALL"))    sql1+= " and substr(B.comp,1,4) = '"+StringManager.substring(ss_company, 0, 4)+"'";
                //if (!ss_company.equals("ALL"))    body_sql2+= " and substring(B.comp,1,4) = '"+StringManager.substring(ss_company, 0, 4)+"'";
                if (!ss_edustart.equals(""))
                    body_sql2 += " and C.edustart >= " + SQLString.Format(ss_edustart + "00");
                if (!ss_eduend.equals(""))
                    body_sql2 += " and C.eduend <= " + SQLString.Format(ss_eduend + "00");

                if (!search_userid.equals(""))
                    body_sql2 += " and A.userid like '%" + search_userid + "%'";
                if (!search_username.equals(""))
                    body_sql2 += " and B.NAME like '%" + search_username + "%'";
                if (!v_area.equals(""))
                    body_sql2 += " and C.area = " + SQLString.Format(v_area);

                /*
                 * // 부서장일경우 //System.out.println("s_gadmin="+s_gadmin); if
                 * (s_gadmin.equals("K7")) { bean = new ManagerAdminBean();
                 * v_sql_add = bean.getManagerDept(v_userid, s_gadmin); if
                 * (!v_sql_add.equals("")) body_sql2 += " and B.comp in " +
                 * v_sql_add; // 관리부서검색조건쿼리 }
                 */

                if (v_orderColumn.equals("grseq"))
                    v_orderColumn = "C.grseq";
                if (v_orderColumn.equals("subj"))
                    v_orderColumn = "C.subj";
                //if (v_orderColumn.equals("compnm1")) v_orderColumn = "get_compnm(b.comp,2,2)";
                //if (v_orderColumn.equals("compnm2")) v_orderColumn = "get_deptnm(b.deptnam,'')";
                if (v_orderColumn.equals("userid"))
                    v_orderColumn = "b.userid ";
                if (v_orderColumn.equals("name"))
                    v_orderColumn = "b.name ";
                //if (v_orderColumn.equals("jiknm"))   v_orderColumn = "get_jikwinm(b.jikwi,b.comp)";

                if (v_orderColumn.equals("")) {
                    order_sql2 += " order by C.course, C.subj,C.year,C.subjseq";
                } else {
                    order_sql2 += " order by C.course, " + v_orderColumn + v_orderType;
                }

                sql2 = head_sql2 + body_sql2 + group_sql2 + order_sql2;

                System.out.println(" propose sql2 >>>>> " + sql2);
                ls1 = connMgr.executeQuery(sql2);

                count_sql2 = "select count(*) " + body_sql2;
                System.out.println("propose count2  >>>> " + sql2);
                int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql2);

                ls1.setPageSize(row); //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count); //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage(); //전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  	//전체 row 수를 반환한다
                /*
                 * while (ls1.next()) { data1=new ProposeStatusData();
                 * data1.setGrseq(ls1.getString("grseq"));
                 * data1.setGrseqnm(ls1.getString("grseqnm"));
                 * data1.setCourse(ls1.getString("course"));
                 * data1.setCyear(ls1.getString("cyear"));
                 * data1.setCourseseq(ls1.getString("courseseq"));
                 * data1.setCoursenm(ls1.getString("coursenm"));
                 * data1.setSubj(ls1.getString("subj"));
                 * data1.setYear(ls1.getString("year"));
                 * data1.setSubjseq(ls1.getString("subjseq"));
                 * data1.setSubjseqgr(ls1.getString("subjseqgr"));
                 * data1.setSubjnm(ls1.getString("subjnm"));
                 * //data1.setCompanynm(ls1.getString("companynm"));
                 * //data1.setCompnm(ls1.getString("compnm"));
                 * //data1.setJikwinm(ls1.getString("jikwinm"));
                 * //data1.setJikupnm(ls1.getString("jikupnm"));
                 * data1.setUserid(ls1.getString("userid"));
                 * data1.setCono(ls1.getString("cono"));
                 * data1.setName(ls1.getString("name"));
                 * data1.setCanceldate(ls1.getString("canceldate"));
                 * data1.setCancelkind(ls1.getString("cancelkind"));
                 * data1.setReason(ls1.getString("reason"));
                 * data1.setEmail(ls1.getString("email"));
                 * data1.setIsmailing(ls1.getString("ismailing"));
                 * data1.setIsonoff(ls1.getString("isonoff"));
                 * data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                 * data1.setTotalPageCount(total_page_count);
                 * data1.setRowCount(row);
                 * 
                 * list1.add(data1); }
                 */
                while (ls1.next()) {
                    dbox = ls1.getDataBox();

                    dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(total_page_count));
                    dbox.put("d_rowcount", new Integer(row));
                    dbox.put("d_totalrow", new Integer(total_row_count));

                    list1.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql1 = " + sql2 + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * 신청 인원 조회 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ProposeStatusData> selectProposeMemberCountList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<ProposeStatusData> list1 = null;
        ArrayList<ProposeStatusData> list2 = null;
        String sql1 = "";
        String sql2 = "";
        ProposeStatusData data1 = null;
        ProposeStatusData data2 = null;
        String v_Bcourse = ""; //이전코스
        String v_course = ""; //현재코스
        String v_Bcourseseq = ""; //이전코스차수
        String v_courseseq = ""; //현재코스차수
        int v_pageno = box.getInt("p_pageno");
        // int l = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        // String ss_grseqnm = box.getStringDefault("s_grseqnm", "ALL"); //교육차수명
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_edustart = box.getStringDefault("s_edustart", ""); //교육시작일
        String ss_eduend = box.getStringDefault("s_eduend", ""); //교육종료일
        // String ss_seltext = box.getStringDefault("s_seltext", "ALL"); //검색분류별 검색내용
        // String ss_seldept = box.getStringDefault("s_seldept", "ALL"); //사업부별 부서 검색내용
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<ProposeStatusData>();
                list2 = new ArrayList<ProposeStatusData>();

                //select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq,
                //propstart,propend,edustart,eduend,studentlimit,procnt,cancnt,isonoff
                sql1 = "select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq, subjseqgr, \n";
                sql1 += "propstart,propend,edustart,eduend,studentlimit, \n";
                sql1 += "(select grseqnm from tz_grseq where grcode = b.grcode and gyear = b.gyear and grseq = b.grseq) grseqnm, \n";
                sql1 += "(select count(subj) from TZ_PROPOSE where subj=B.subj and year=B.year and subjseq=B.subjseq \n";
                //sql1+= "and cancelkind is null) procnt, ";
                sql1 += ") procnt, ";
                sql1 += "(select count(subj) from TZ_PROPOSE where subj=B.subj and year=B.year and subjseq=B.subjseq \n";
                sql1 += "and cancelkind in('P','F')) cancnt,B.isonoff ";
                sql1 += "from VZ_SCSUBJSEQ B where 1 = 1  ";

                if (!ss_grcode.equals("ALL")) {
                    sql1 += " and B.grcode = " + SQLString.Format(ss_grcode);
                }
                sql1 += " and B.gyear = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL")) {
                    sql1 += " and B.grseq = " + SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
                    sql1 += " and B.scupperclass = " + SQLString.Format(ss_uclass);
                }
                if (!ss_subjcourse.equals("ALL")) {
                    sql1 += " and B.scsubj = " + SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
                    sql1 += " and B.scsubjseq = " + SQLString.Format(ss_subjseq);
                }
                if (!ss_edustart.equals("")) {
                    sql1 += " and B.edustart >= " + SQLString.Format(ss_edustart + "00");
                }
                if (!ss_eduend.equals("")) {
                    sql1 += " and B.eduend <= " + SQLString.Format(ss_eduend + "00");
                }
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                    sql1 += " and B.gyear = " + SQLString.Format(ss_gyear);
                }
                //sql1+= " and userid=A.userid and subj=B.subj and year=B.year and subjseq=B.subjseq ";
                if (!v_orderColumn.equals("")) {
                    v_orderColumn = "A." + v_orderColumn;
                    sql1 += " order by B.course,B.cyear,B.courseseq,B.subj,B.year,B.subjseq," + v_orderColumn;
                } else {
                    sql1 += " order by B.course,B.cyear,B.courseseq,B.subj,B.year,B.subjseq ";
                }

                System.out.println("sql11111==>" + sql1);
                ls1 = connMgr.executeQuery(sql1);

                ls1.setPageSize(row); //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno); //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage(); //전체 페이지 수를 반환한다
                int total_row_count = ls1.getTotalCount(); //전체 row 수를 반환한다

                while (ls1.next()) {
                    data1 = new ProposeStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setGrseqnm(ls1.getString("grseqnm"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setPropstart(ls1.getString("propstart"));
                    data1.setPropend(ls1.getString("propend"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setStudentlimit(ls1.getInt("studentlimit"));
                    data1.setProcnt(ls1.getInt("procnt"));
                    data1.setCancnt(ls1.getInt("cancnt"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                }
                for (int i = 0; i < list1.size(); i++) {
                    data2 = (ProposeStatusData) list1.get(i);
                    v_course = data2.getCourse();
                    v_courseseq = data2.getCourseseq();
                    System.out.println("subj=" + data2.getSubj() + "  grseq=" + data2.getGrseq());
                    System.out.println("v_course=" + v_course.equals("000000") + "  v_Bcourse=" + v_Bcourse.equals(v_course) + "  v_Bcourseseq=" + v_Bcourseseq.equals(v_courseseq));
                    if (!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))) {
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A where ";
                        //sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        //sql2+= "and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2 += " A.grcode = " + SQLString.Format(ss_grcode) + " and ";
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2 += " A.grseq = " + SQLString.Format(ss_grseq) + " and ";
                        }
                        if (!ss_uclass.equals("ALL")) {
                            sql2 += " A.scupperclass = " + SQLString.Format(ss_uclass) + " and ";
                        }
                        if (!ss_subjcourse.equals("ALL")) {
                            sql2 += " A.scsubj = " + SQLString.Format(ss_subjcourse) + " and ";
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2 += " A.scsubjseq = " + SQLString.Format(ss_subjseq) + " and ";
                        }
                        if (!ss_edustart.equals("")) {
                            sql2 += " A.edustart >= " + SQLString.Format(ss_edustart + "00") + " and ";
                        }
                        if (!ss_eduend.equals("")) {
                            sql2 += " A.eduend <= " + SQLString.Format(ss_eduend + "00") + " and ";
                        }
                        //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                        if (ss_edustart.equals("") && ss_eduend.equals("")) {
                            sql2 += " A.gyear = " + SQLString.Format(ss_gyear) + " and ";
                        }
                        sql2 += " A.course = " + SQLString.Format(v_course) + " and A.courseseq = " + SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);
                        if (ls2.next()) {
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                    } else {
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse = v_course;
                    v_Bcourseseq = v_courseseq;
                    list2.add(data2);
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return list2;
    }

    /**
     * 폼메일 발송
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int cnt = 0; //  메일발송이 성공한 사람수
        //p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_check1 = new Vector();
        Vector v_check2 = new Vector();
        Vector v_check3 = new Vector();
        Vector v_check4 = new Vector();
        v_check1 = box.getVector("p_checks");
        v_check2 = box.getVector("p_subj");
        v_check3 = box.getVector("p_year");
        v_check4 = box.getVector("p_subjseq");
        Enumeration em1 = v_check1.elements();
        Enumeration em2 = v_check2.elements();
        Enumeration em3 = v_check3.elements();
        Enumeration em4 = v_check4.elements();
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_isonoff = "";
        String v_edustart = "";

        try {
            connMgr = new DBConnectionManager();

            ////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail8.html";
            FormMail fmail = new FormMail(v_sendhtml); //      폼메일발송인 경우
            MailSet mset = new MailSet(box); //      메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 사이버연수원 운영자입니다.";
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            while (em1.hasMoreElements()) {
                v_userid = (String) em1.nextElement();
                v_subj = (String) em2.nextElement();
                v_year = (String) em3.nextElement();
                v_subjseq = (String) em4.nextElement();

                //select subjnm,isonoff,edustart,name,ismailing,cono,email
                sql = "select  B.subjnm,B.isonoff,D.name,D.ismailing,D.cono,D.email, ";
                sql += "(select to_char(edustart,'yyyymmdd') - 2 from TZ_SUBJSEQ where subj=A.subj and year=A.year and subjseq = '0002') edustart ";
                sql += " from TZ_PROPOSE A,VZ_SCSUBJSEQ B,TZ_MEMBER D ";
                sql += " where A.userid = " + SQLString.Format(v_userid);
                sql += " and A.subj = " + SQLString.Format(v_subj);
                sql += " and A.year = " + SQLString.Format(v_year);
                sql += " and A.subjseq = " + SQLString.Format(v_subjseq);
                sql += " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid and B.grcode = D.grcode ";
                //sql+= " group by B.subjnm,B.isonoff,B.edustart,D.name,D.ismailing,D.cono,D.email ";
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    String v_toEmail = ls.getString("email");
                    String v_toCono = ls.getString("cono");
                    String v_ismailing = ls.getString("ismailing");
                    //String v_toEmail =  "jj1004@dreamwiz.com";

                    mset.setSender(fmail); //메일보내는 사람 세팅

                    if (ls.getString("isonoff").equals("ON")) {
                        v_isonoff = "사이버";
                    } else {
                        v_isonoff = "집합";
                    }
                    v_edustart = FormatDate.getFormatDate(v_edustart, "yyyy/MM/dd");

                    fmail.setVariable("subjnm", ls.getString("subjnm"));
                    fmail.setVariable("edustart", ls.getString("edustart"));
                    fmail.setVariable("isonoff", v_isonoff);
                    fmail.setVariable("toname", ls.getString("name"));

                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_ismailing, v_sendhtml);
                    if (isMailed)
                        cnt++; //      메일발송에 성공하면
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
        return cnt;
    }
}