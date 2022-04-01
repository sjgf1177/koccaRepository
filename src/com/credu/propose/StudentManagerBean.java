//**********************************************************
//  1. ��      ��: PROPOSE STATUS ADMIN BEAN
//  2. ���α׷���: StudentManagerBean.java
//  3. ��      ��: ��û ��Ȳ ������ bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��:
//  7. ��      ��: ������
//**********************************************************
package com.credu.propose;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.common.SubjComBean;
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

@SuppressWarnings("unchecked")
public class StudentManagerBean {
    private ConfigSet config;
    private int row;

    public StudentManagerBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //�� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��û��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectStduentMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        DataBox dbox = null;

        String sql1 = "";
        // String v_eduterm = ""; //�����Ⱓ����
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        String v_searchtxt = box.getStringDefault("p_searchtext", "ZZZ"); //����������

        String ss_membergubun = box.getString("p_membergubun"); //�������
        String ss_chkfinal = box.getStringDefault("p_chkfinal_l", "ALL"); //��������
        String ss_ejectpossible = box.getStringDefault("p_ejectpossible", "ALL"); //�̼�����

        String s_scoremin = box.getString("p_scoremin");
        String s_scoremax = box.getString("p_scoremax");

        String ss_area = box.getStringDefault("s_area", "T");

        String ss_action = box.getString("s_action");
        String v_order = box.getString("p_order"); //������ �÷���
        String v_orderType = box.getString("p_orderType"); //������ ����

        // SubjComBean scbean = new SubjComBean();
        ProposeBean probean = new ProposeBean();

        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                sql1 = " SELECT C.COURSENM , C.COURSE, C.SUBJ, C.YEAR, C.SUBJNM, C.SUBJSEQ, C.SUBJSEQGR, B.NAME,   ";
                sql1 += "\n    B.MEMBERGUBUN,  B.USERID, B.CONO, B.NAME, C.EDUSTART, C.EDUEND, ";
                sql1 += "	\n	B.COMP, B.JIKUP, B.RESNO, B.RESNO1, B.RESNO2, B.COMPTEL,B.JIKWI, GET_JIKWINM(B.JIKWI,B.COMP) JIKWINM," + " GET_COMPNM(B.COMP,2,2) COMPNM, RTRIM(LTRIM(B.DEPTNAM)) DEPTNAM,B.OFFICE_GBN, B.WORK_PLC, B.WORK_PLCNM,";
                sql1 += "\n CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') < C.PROPSTART THEN '1'" + "\n   WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN C.PROPSTART AND C.PROPEND THEN '2'"
                        + "\n   WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN C.PROPEND AND C.EDUSTART THEN '3'" + "\n   WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN C.EDUSTART AND C.EDUEND THEN '4'"
                        + "\n   WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') > C.EDUEND THEN '5'" + "\n  ELSE '0' END AS EDUTERM,";
                sql1 += "\n     C.isclosed, B.email,  C.isonoff, A.appdate, A.chkfirst, A.chkfinal, ";
                sql1 += "\n     D.isgraduated as rejectpossible, D.SCORE, C.isbelongcourse, C.subjcnt";
                sql1 += "\n from TZ_PROPOSE A, TZ_MEMBER B, VZ_SCSUBJSEQ C, TZ_STUDENT D";
                sql1 += "\n  where A.userid = B.userid ";
                sql1 += "\n     and A.subj = C.subj ";
                sql1 += "\n     and A.year = C.year ";
                sql1 += "\n     and C.grcode = B.grcode ";
                sql1 += "\n     and A.subjseq = C.subjseq   and A.appdate is not null ";
                sql1 += "\n     and A.USERID = D.USERID(+) ";
                sql1 += "\n     and A.SUBJ = D.SUBJ(+) ";
                sql1 += "\n     and A.SUBJSEQ = D.SUBJSEQ(+) ";
                sql1 += "\n     and A.YEAR = D.YEAR(+) ";
                if (!v_searchtxt.equals("ZZZ")) {
                    sql1 += "   and (B.userid like '%" + v_searchtxt + "%' or B.name like '%" + v_searchtxt + "%')\n";
                }

                if (!s_scoremin.equals(""))
                    sql1 += "	AND NVL(D.SCORE,0) >= " + SQLString.Format(s_scoremin) + "\n";
                if (!s_scoremax.equals(""))
                    sql1 += "	AND NVL(D.SCORE,0) <= " + SQLString.Format(s_scoremax) + "\n";

                if (!ss_area.equals("T"))
                    sql1 += "	AND C.AREA = " + SQLString.Format(ss_area) + "\n";

                if (ss_membergubun.length() > 0 && !ss_membergubun.equals("��ü") && !ss_membergubun.equals("ALL"))
                    sql1 += " and B.membergubun = " + SQLString.Format(ss_membergubun);
                if (!ss_grcode.equals("ALL"))
                    sql1 += " and C.grcode = " + SQLString.Format(ss_grcode);
                if (!ss_gyear.equals("ALL"))
                    sql1 += " and C.gyear  = " + SQLString.Format(ss_gyear);
                if (!ss_grseq.equals("ALL"))
                    sql1 += " and C.grseq = " + SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))
                    sql1 += " and C.scupperclass = " + SQLString.Format(ss_uclass);
                if (!ss_subjcourse.equals("ALL"))
                    sql1 += " and C.scsubj = " + SQLString.Format(ss_subjcourse) + " and C.year ='" + v_year + "'";
                if (!ss_subjseq.equals("ALL"))
                    sql1 += " and C.scsubjseq = " + SQLString.Format(ss_subjseq);

                if (!ss_chkfinal.equals("ALL"))
                    sql1 += " and A.chkfinal = " + SQLString.Format(ss_chkfinal);
                if (!ss_ejectpossible.equals("ALL"))
                    sql1 += " and D.ISGRADUATED = " + SQLString.Format(ss_ejectpossible);

                if (v_order.equals("subj"))
                    v_order = "a.subj";
                if (v_order.equals("userid"))
                    v_order = "b.userid";
                if (v_order.equals("name"))
                    v_order = "b.name";

                if (v_order.equals("")) {
                    sql1 += " order by C.course, C.subj,C.year,C.subjseq";
                } else {
                    sql1 += " order by C.course, " + v_order + v_orderType;
                }

                ls1 = connMgr.executeQuery(sql1);

                while (ls1.next()) {
                    dbox = ls1.getDataBox();
                    //�����Ⱓ���� ������ �̵��Ͽ���.
                    //					v_eduterm = scbean.getEduTerm(ls1.getString("subj"), ls1.getString("subjseq"), ls1.getString("year"));

                    /**
                     * �������� outdata =
                     * probean.getMeberInfo(ls1.getString("userid"));
                     * dbox.put("d_workplc", outdata.get("work_plcnm"));
                     * dbox.put("d_deptnam", outdata.get("deptnam"));
                     * dbox.put("d_officegbn", outdata.get("officegbn"));
                     * dbox.put("d_gubuntxt", outdata.get("gubuntxt"));
                     */

                    dbox.put("d_workplc", dbox.get("work_plcnm"));
                    dbox.put("d_deptnam", dbox.get("deptnam"));
                    dbox.put("d_officegbn", dbox.get("officegbn"));
                    dbox.put("d_gubuntxt", dbox.get("gubuntxt"));
                    //					dbox.put("d_eduterm",    v_eduterm);

                    if (ss_grcode.equals("N000001")) {
                        //====================================================
                        // �������� ��ȣȭ - HTJ
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
     * ����ڻ���
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int stduentMemberDelete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql0 = "";
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        //        int isOk4           = 0;
        int isOk5 = 0;
        int isOk6 = 0;
        int isOk7 = 0;
        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();
        Hashtable insertData = new Hashtable();
        String v_user_id = box.getSession("userid");

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_course = "";
        String v_iscourseYn = "";
        String v_subj_value = "";

        //        String  v_eduterm   = "";
        //        String  v_mastercd  = "";
        //        String  v_appstatus = "";
        //        String  v_appstatus1 = "";
        String v_gubun = "2";
        //        boolean  v_isedutarget = false;
        ProposeBean probean = new ProposeBean();

        SubjComBean csbean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {

                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    v_course = st1.nextToken();
                    v_iscourseYn = st1.nextToken();
                    break;
                }

                if (v_iscourseYn.equals("Y")) {
                    v_subj_value = v_course;
                } else {
                    v_subj_value = v_subj;
                }

                sql0 = " Select subj From VZ_SCSUBJSEQ ";
                sql0 += " Where	scsubj = '" + v_subj_value + "' and ";
                sql0 += " 	subjseq = '" + v_subjseq + "'  and year = '" + v_year + "' ";

                ls = connMgr.executeQuery(sql0);

                while (ls.next()) {
                    v_subj = ls.getString("subj");

                    //	                v_mastercd    =
                    csbean.getMasterCode(v_subj, v_year, v_subjseq);
                    //	                v_isedutarget =
                    csbean.IsEduTarget(v_subj, v_year, v_subjseq, v_userid);
                    System.out.println("success1");

                    insertData.clear();
                    insertData.put("connMgr", connMgr);
                    insertData.put("subj", v_subj);
                    insertData.put("subjseq", v_subjseq);
                    insertData.put("year", v_year);
                    insertData.put("userid", v_userid);
                    insertData.put("gubun", v_gubun); //
                    insertData.put("cancelkind", "D");
                    insertData.put("reason", "��ڻ���");
                    insertData.put("luserid", v_user_id);
                    insertData.put("reasoncd", "99");

                    //��û���̺����
                    isOk1 = probean.deletePropose(insertData);
                    System.out.println("isOk1 = " + isOk1);
                    //�л����̺����
                    if (probean.getOverStuCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                        isOk2 = probean.deleteStudent(insertData);
                        System.out.println("success5");
                    } else {
                        isOk2 = 1;
                    }
                    System.out.println("isOk2 = " + isOk2);
                    //�ݷ����̺����
                    if (probean.getStuRejCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                        isOk3 = probean.deleteStudentreject(insertData);
                    } else {
                        isOk3 = 1;
                    }
                    System.out.println("isOk3 = " + isOk3);
                    //�������/�ݷ����̺� ���� ->��� (�������� ���)
                    if (probean.getCancelCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                        // ������Ʈ��.
                        //isOk5 = probean.deleteCancel(insertData);
                        isOk5 = probean.updateCancel(insertData);
                    } else {
                        isOk5 = 1;
                    }
                    System.out.println("isOk5 = " + isOk5);
                    //�������̺� ����
                    if (probean.getOverStoldCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                        isOk6 = probean.deleteStold(insertData);
                    } else {
                        isOk6 = 1;
                    }
                    System.out.println("isOk6 = " + isOk6);
                    //�������̺� ����
                    if (probean.getOverProgressCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                        isOk7 = probean.deleteProgress(insertData);
                    } else {
                        isOk7 = 1;
                    }
                    System.out.println("isOk7 = " + isOk7);
                    if (isOk1 > 0 && isOk2 > 0 && isOk5 > 0 && isOk7 > 0) {
                        isOk = 1;
                        connMgr.commit();
                    } else {
                        isOk = 0;
                        connMgr.rollback();
                    }
                }

            }
        } catch (Exception ex) {
            if (connMgr != null)
                try {
                    connMgr.rollback();
                } catch (Exception e) {
                }
            ErrorManager.getErrorStackTrace(ex, box, "");
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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
     * ��û �ο� ��ȸ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectProposeMemberCountList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList list1 = null;
        ArrayList list2 = null;
        String sql1 = "";
        String sql2 = "";
        ProposeStatusData data1 = null;
        ProposeStatusData data2 = null;
        String v_Bcourse = ""; //�����ڽ�
        String v_course = ""; //�����ڽ�
        String v_Bcourseseq = ""; //�����ڽ�����
        String v_courseseq = ""; //�����ڽ�����
        int v_pageno = box.getInt("p_pageno");
        //        int     l             = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        //        String  ss_grseqnm    = box.getStringDefault("s_grseqnm","ALL");   //����������
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        String ss_edustart = box.getStringDefault("s_edustart", ""); //����������
        String ss_eduend = box.getStringDefault("s_eduend", ""); //����������
        //        String  ss_seltext    = box.getStringDefault("s_seltext","ALL");   //�˻��з��� �˻�����
        //        String  ss_seldept    = box.getStringDefault("s_seldept","ALL");   //����κ� �μ� �˻�����
        String ss_action = box.getString("s_action");
        String v_orderColumn = box.getString("p_orderColumn"); //������ �÷���

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq,
                //propstart,propend,edustart,eduend,studentlimit,procnt,cancnt,isonoff
                sql1 = "select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq, \n";
                sql1 += "propstart,propend,edustart,eduend,studentlimit, \n";
                sql1 += "(select grseqnm from tz_grseq where grcode = b.grcode and gyear = b.gyear and grseq = b.grseq) grseqnm, \n";
                sql1 += "(select count(subj) from TZ_PROPOSE where subj=B.subj and year=B.year and subjseq=B.subjseq \n";
                sql1 += "and cancelkind is null) procnt, ";
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
                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
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

                //System.out.println(sql1);
                ls1 = connMgr.executeQuery(sql1);

                ls1.setPageSize(row); //�������� row ������ �����Ѵ�
                ls1.setCurrentPage(v_pageno); //������������ȣ�� �����Ѵ�.
                int total_page_count = ls1.getTotalPage(); //��ü ������ ���� ��ȯ�Ѵ�
                int total_row_count = ls1.getTotalCount(); //��ü row ���� ��ȯ�Ѵ�

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
                        //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
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
     * ������ �߼�
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int cnt = 0; //  ���Ϲ߼��� ������ �����
        //p_checks�� �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
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

            ////////////////////  ������ �߼� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail8.html";
            FormMail fmail = new FormMail(v_sendhtml); //      �����Ϲ߼��� ���
            MailSet mset = new MailSet(box); //      ���� ���� �� �߼�
            String v_mailTitle = "�ȳ��ϼ���? ���̹������� ����Դϴ�.";
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
                sql += " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid ";
                //sql+= " group by B.subjnm,B.isonoff,B.edustart,D.name,D.ismailing,D.cono,D.email ";
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    String v_toEmail = ls.getString("email");
                    String v_toCono = ls.getString("cono");
                    String v_ismailing = ls.getString("ismailing");
                    //String v_toEmail =  "jj1004@dreamwiz.com";

                    mset.setSender(fmail); //���Ϻ����� ��� ����

                    if (ls.getString("isonoff").equals("ON")) {
                        v_isonoff = "���̹�";
                    } else {
                        v_isonoff = "����";
                    }
                    v_edustart = FormatDate.getFormatDate(v_edustart, "yyyy/MM/dd");

                    fmail.setVariable("subjnm", ls.getString("subjnm"));
                    fmail.setVariable("edustart", ls.getString("edustart"));
                    fmail.setVariable("isonoff", v_isonoff);
                    fmail.setVariable("toname", ls.getString("name"));

                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_ismailing, v_sendhtml);
                    if (isMailed)
                        cnt++; //      ���Ϲ߼ۿ� �����ϸ�
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

    /**
     * ���氡������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectPossibleChangeSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        //        ArrayList list      = null;
        ArrayList list1 = null;
        DataBox dbox = null;

        String sql1 = "";
        //        String sql2         = "";
        //        ProposeStatusData data1=null;
        //        ProposeStatusData data2=null;
        //        String  v_Bcourse   = ""; //�����ڽ�
        //        String  v_course    = ""; //�����ڽ�
        //        String  v_Bcourseseq= ""; //�����ڽ�����
        //        String  v_courseseq = ""; //�����ڽ�����
        //        int v_pageno = box.getInt("p_pageno");
        //        int     l           = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        //        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //�⵵
        //        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //��������
        //        String  ss_grseqnm  = box.getStringDefault("s_grseqnm","ALL");   //����������
        //        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        //        String  ss_company  = box.getStringDefault("s_company","ALL");   //ȸ��
        //        String  ss_edustart = box.getStringDefault("s_edustart","");  //����������
        //        String  ss_eduend   = box.getStringDefault("s_eduend","");    //����������
        //        String  ss_selgubun = box.getString("s_selgubun");               //������:JIKUN,���޺�:JIKUP,����κ�:GPM
        //        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");   //�˻��з��� �˻�����
        //        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");   //����κ� �μ� �˻�����
        //        String  ss_action   = box.getString("s_action");
        //        String  v_orderColumn= box.getString("p_orderColumn");           //������ �÷���

        //ManagerAdminBean bean = null;
        //        String  v_sql_add   = "";
        //        String  v_userid    = box.getSession("userid");
        //        String  s_gadmin    = box.getSession("gadmin");
        String v_eduterm = "";
        String v_edutermtxt = "";
        //MyClassBean bean = new MyClassBean();
        SubjComBean scbean = new SubjComBean();

        try {
            //if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql1 += " select \n";
            sql1 += "    A.subjseq, \n";
            sql1 += "    A.subjseqgr, \n";
            sql1 += "    A.year,\n";
            sql1 += "    A.subjnm,\n";
            sql1 += "    A.propstart,\n";
            sql1 += "    A.propend,\n";
            sql1 += "    A.edustart,\n";
            sql1 += "    A.eduend,\n";
            sql1 += "    A.studentlimit,\n";
            sql1 += "    (select count(userid) from vz_student where a.subj = subj and a.subjseq = subjseq and a.year = year) stucnt,\n";
            sql1 += "    (select count(userid) from tz_propose where a.subj = subj and a.subjseq = subjseq and a.year = year) propcnt,\n";
            sql1 += "    A.isclosed\n";
            sql1 += " from             \n";
            sql1 += "  VZ_SCSUBJSEQ A\n";
            sql1 += " where  \n";
            sql1 += "  A.subj = '" + ss_subjcourse + "'\n";
            sql1 += "  and A.grcode = " + SQLString.Format(ss_grcode) + " \n";
            sql1 += "  and A.subjseq != " + SQLString.Format(ss_subjseq) + " \n";
            sql1 += "  order by subjseq";

            System.out.println("selectPossibleChangeSeq===>" + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                v_eduterm = scbean.getEduTerm(ss_subjcourse, ls1.getString("subjseq"), ls1.getString("year"));
                if (v_eduterm.equals("0")) {
                    v_edutermtxt = "-";
                } else if (v_eduterm.equals("1")) {
                    v_edutermtxt = "��û��";
                } else if (v_eduterm.equals("2")) {
                    v_edutermtxt = "������û�Ⱓ";
                } else if (v_eduterm.equals("3")) {
                    v_edutermtxt = "�������";
                } else if (v_eduterm.equals("4")) {
                    v_edutermtxt = "�����Ⱓ";
                } else if (v_eduterm.equals("5")) {
                    v_edutermtxt = "�����Ϸ�";
                }

                if (ls1.getString("isclosed").equals("Y")) {
                    v_edutermtxt += "<br><font color=red>(�̷»����Ϸ�)</font>";
                } else if (ls1.getString("isclosed").equals("N")) {
                    v_edutermtxt += "<br><font color=blue>(�̷»�����)</font>";
                }

                dbox.put("d_edutermtxt", v_edutermtxt);
                dbox.put("d_eduterm", v_eduterm);

                list1.add(dbox);
            }

            //}
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
     * ��������ó��
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int updateChangeSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt4 = null;
        //        PreparedStatement pstmt5 = null;
        ListSet ls1 = null;
        ListSet ls3 = null;
        String sql1 = "";
        //        String sql2         = "";
        //        String sql3         = "";
        //        String sql4         = "";
        //        int cancel_cnt      = 0;
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;
        int isOk5 = 0;
        int isOk6 = 0;

        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();
        Hashtable insertData = new Hashtable();
        String v_user_id = box.getSession("userid");

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        String v_targetsubj = box.getString("p_subj");
        String v_targetsubjseq = box.getString("p_subjseq");
        String v_targetyear = box.getString("p_year");

        //        String  v_eduterm   = "";
        String v_mastercd = "";

        boolean v_isedutarget = false;
        ProposeBean probean = new ProposeBean();

        SubjComBean csbean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                //v_userid = (String)em.nextElement();

                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    break;
                }

                //System.out.println("connMgr="+connMgr);
                //update TZ_PROPOSE table

                //v_eduterm = getEduTerm(v_subj, v_subjseq, v_year);

                v_mastercd = csbean.getMasterCode(v_subj, v_year, v_subjseq);
                v_isedutarget = csbean.IsEduTarget(v_subj, v_year, v_subjseq, v_userid);
                System.out.println("success1");

                //������������ Delete
                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_subj);
                insertData.put("subjseq", v_subjseq);
                insertData.put("year", v_year);
                insertData.put("userid", v_userid);

                isOk1 = probean.deletePropose(insertData);

                if (probean.getOverStuCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk2 = probean.deleteStudent(insertData);
                    System.out.println("success5");
                } else {
                    isOk2 = 1;
                }

                if (probean.getStuRejCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk2 = probean.deleteStudentreject(insertData);
                } else {
                    isOk2 = 1;
                }

                if (!v_mastercd.equals("") && v_isedutarget) {
                    insertData.put("mastercd", v_mastercd);
                    isOk4 = probean.deleteEduTarget(insertData);
                } else {
                    isOk4 = 1;
                }

                if (probean.getOverStoldCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk3 = probean.deleteStold(insertData);
                } else {
                    isOk3 = 1;
                }

                //������ ���� Insert
                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_targetsubj);
                insertData.put("year", v_targetyear);
                insertData.put("subjseq", v_targetsubjseq);
                insertData.put("userid", v_userid);
                insertData.put("luserid", v_user_id);
                insertData.put("isdinsert", "Y");
                insertData.put("chkfirst", "Y");
                insertData.put("chkfinal", "Y");

                if (isOk1 > 0 && isOk2 > 0) {
                    isOk5 = probean.insertPropose(insertData);
                    isOk6 = probean.insertStudent(insertData);
                }

                if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0 && isOk5 > 0 && isOk6 > 0) {
                    isOk = 1;
                    connMgr.commit();
                } else {
                    isOk = 0;
                    connMgr.rollback();
                }

            }

        } catch (Exception ex) {
            if (connMgr != null)
                try {
                    connMgr.rollback();
                } catch (Exception e) {
                }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
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
        return isOk;
    }

    /**
     * ���� ���� ����
     * 
     * @param box receive from the form object and session
     * @return DataBox ���� ���� ����
     */
    public DataBox isClosedInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        DataBox dbox = null;

        String sql1 = "";
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        //        String  ss_action     = box.getString("s_action");
        //        String v_isClosed     = "";
        String v_eduterm = "";
        ProposeBean probean = new ProposeBean();
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
        if (v_year.equals(""))
            v_year = ss_gyear;

        SubjComBean scbean = new SubjComBean();

        try {
            //if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();

            sql1 += " select A.isonoff, A.subjseqgr,  A.isclosed,  ";
            //sql1+= "        decode(A.studentlimit, 0, 1000000, A.studentlimit) studentlimit, ";
            sql1 += " case A.studentlimit ";
            sql1 += "	When 0 Then 1000000 ";
            sql1 += "	Else A.studentlimit ";
            sql1 += " End as studentlimit, ";
            sql1 += "        (select count(userid) from tz_propose where a.subj = subj and a.subjseq = subjseq and a.year = year) propcnt";
            sql1 += "   from VZ_SCSUBJSEQ A                ";
            sql1 += " where A.scsubj   = " + SQLString.Format(ss_subjcourse);
            sql1 += "  and A.scyear    = " + SQLString.Format(v_year);
            sql1 += "  and A.scsubjseq = " + SQLString.Format(ss_subjseq);
            sql1 += "  order by subjseq ";

            System.out.println("isClosedInfo.sql = " + sql1);

            ls1 = connMgr.executeQuery(sql1);

            v_eduterm = scbean.getEduTerm(ss_subjcourse, ss_subjseq, v_year);

            if (ls1.next()) {
                dbox = ls1.getDataBox();
                dbox.put("d_eduterm", v_eduterm);
            }
            //}
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
     * ��û���ɿ���(��û �ο�) üũ
     * 
     * @param box receive from the form object and session
     * @return String
     */
    public String isOverflowStudent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;

        String sql1 = "";
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        String ss_action = box.getString("s_action");
        String v_isaddpossible = "";

        ProposeBean probean = new ProposeBean();
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql1 += " select * from (select rownum rnum, \n";
                sql1 += "    'Y' isaddpossible\n";
                sql1 += " from             \n";
                sql1 += "  VZ_SCSUBJSEQ A\n";
                sql1 += " where  \n";
                sql1 += "  A.scsubj = '" + ss_subjcourse + "'\n";
                sql1 += "  and A.scsubjseq = " + SQLString.Format(ss_subjseq) + " \n";
                sql1 += "  and A.scyear    = " + SQLString.Format(v_year) + " \n";
                sql1 += "  and ";
                //sql1 += " decode(a.studentlimit,0, 1000000,  a.studentlimit)  ";
                sql1 += " case A.studentlimit ";
                sql1 += "	When 0 Then 1000000 ";
                sql1 += "	Else A.studentlimit ";
                sql1 += " End  ";
                sql1 += " > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year) ";
                sql1 += "  order by subjseq ) where rnum = 1";

                System.out.println(sql1);

                ls1 = connMgr.executeQuery(sql1);

                if (ls1.next()) {
                    v_isaddpossible = ls1.getString("isaddpossible");
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return v_isaddpossible;
    }

    /* ������ ��������� �԰�ó�� */
    public int AcceptTargetMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1 = "";
        ProposeBean probean = new ProposeBean();
        int isOk = 0, isOk1 = 0, isOk2 = 0, isOk4 = 0;

        /* ���� �� ���� ���� ���� selected Params */
        String v_grcode = box.getString("s_grcode"); //�����׷�
        String v_gyear = box.getString("s_gyear"); //�⵵
        String v_grseq = box.getString("s_grseq"); //��������
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getString("s_subjseq");
        String v_year = probean.getSubjYear(v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);

        if (v_year.equals(""))
            v_year = v_gyear;

        //p_subj�� �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        String v_userid = "";
        String type = box.getBoolean("billYn") ? "i_userid" : "p_checks";
        Vector v_checks = box.getVector(type);
        Enumeration em = v_checks.elements();
        Hashtable insertData = new Hashtable();
        String v_luserid = box.getSession("userid");

        boolean v_isedutarget = false;
        String v_mastercd = "";

        SubjComBean csbean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                v_userid = (String) em.nextElement();

                insertData.clear();
                System.out.println(v_subj);
                System.out.println(v_year);
                System.out.println(v_subjseq);
                System.out.println(v_userid);

                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_subj);
                insertData.put("year", v_year);
                insertData.put("subjseq", v_subjseq);
                insertData.put("userid", v_userid);
                insertData.put("luserid", v_luserid);
                insertData.put("isdinsert", "Y");
                insertData.put("chkfirst", "Y");
                insertData.put("chkfinal", "Y");
                insertData.put("tid", box.get("tid"));
                insertData.put("grcode", v_grcode);

                isOk1 = probean.insertPropose(insertData);
                isOk2 = probean.insertStudent(insertData);

                //�����Ͱ����� ���εǾ� �ִ� ������ �̰� ������û ����ڷ� ������ �Ǿ� ���� �ʴٸ�
                //����� ���̺� Insert�Ѵ�.
                v_mastercd = csbean.getMasterCode(v_subj, v_year, v_subjseq);
                v_isedutarget = csbean.IsEduTarget(v_subj, v_year, v_subjseq, v_userid);

                if (!v_mastercd.equals("") && !v_isedutarget) {
                    insertData.put("mastercd", v_mastercd);
                    isOk4 = probean.insertEduTarget(insertData);
                } else {
                    isOk4 = 1;
                }

                isOk = isOk1 * isOk2 * isOk4;
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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
     * �н��� �߰����ɿ���
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public String isStuAddPossible(String p_subj, String p_subjseq, String p_year, String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String sql1 = "";
        String sql2 = "";
        //        String sql3       = "";

        String v_subj = p_subj;
        String v_subjseq = p_subjseq;
        //        String  v_year    = p_year;
        String v_userid = p_userid;
        //        String  v_isstuaddpossible = "";
        String v_propossible = "Y";
        String v_stoldpossible = "Y";
        String v_isOk = "N";
        //        ProposeBean probean = new ProposeBean();
        ///String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
        //if (v_year.equals("")) v_year = ss_gyear;

        try {
            //if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();

            //sql1+= " select \n";
            //sql1+= "    'Y' isaddpossible\n";
            //sql1+= " from             \n";
            //sql1+= "  VZ_SCSUBJSEQ A\n";
            //sql1+= " where  \n";
            //sql1+= "  A.subj = '"+ss_subjcourse+"'\n";
            //sql1+= "  and A.subjseq = "+SQLString.Format(ss_subjseq)+" \n";
            //sql1+= "  and A.year    = "+SQLString.Format(v_year)+" \n";
            //sql1+= "  and decode(a.studentlimit,0, 1000000,  a.studentlimit) > (select count(userid) from tz_propose where subj = a.subj and subjseq = a.subjseq and year = a.year) ";
            //sql1+= "  order by subjseq";

            //��û������ ������ �߰��ȵ�
            sql1 += " select \n";
            sql1 += "   'N' propossible \n";
            sql1 += " from  \n";
            sql1 += "   tz_propose  \n";
            sql1 += " where \n";
            sql1 += "   userid = '" + v_userid + "' \n";
            sql1 += "   AND subj = '" + v_subj + "'  \n";
            sql1 += "   and subjseq = '" + v_subjseq + "'  \n";
            sql1 += "   and year = '" + p_year + "' \n";
            sql1 += "   and chkfinal != 'N' \n";
            System.out.println("prop_sql1==>" + sql1);

            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                v_propossible = ls1.getString("propossible");
            }

            //�̼������ ���� �ִٸ� �߰� �ȵ�
            sql2 += " select \n";
            sql2 += "   'N' stoldpossible\n";
            sql2 += " from  \n";
            sql2 += "   tz_stold \n";
            sql2 += " where \n";
            sql2 += "   userid = '" + v_userid + "' \n";
            sql2 += "   AND subj = '" + v_subj + "'  \n";
            sql2 += "   and subjseq = '" + v_subjseq + "'  \n";
            sql2 += "   and year = '" + p_year + "' \n";
            sql2 += "   and isgraduated = 'Y' \n";

            System.out.println(sql2);
            ls2 = connMgr.executeQuery("prop_sql2==>" + sql2);

            if (ls2.next()) {
                v_stoldpossible = ls2.getString("stoldpossible");
            }

            if (v_propossible.equals("Y") && v_stoldpossible.equals("Y")) {
                v_isOk = "Y";
            }

            //}
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql1);
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
        return v_isOk;
    }

    /**
     * ���氡������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectPossibleAddSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        DataBox dbox = null;

        String sql1 = "";
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        String v_eduterm = "";
        String v_isclosed = "";
        String v_edutermtxt = "";
        SubjComBean scbean = new SubjComBean();

        ProposeBean probean = new ProposeBean();
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            //if(ss_action.equals("go")){
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql1 += " select \n";
            sql1 += "    A.subj,\n";
            sql1 += "    A.subjseq, \n";
            sql1 += "    A.subjseqgr, \n";
            sql1 += "    A.year,\n";
            sql1 += "    A.subjnm,\n";
            sql1 += "    A.propstart,\n";
            sql1 += "    A.propend,\n";
            sql1 += "    A.edustart,\n";
            sql1 += "    A.eduend,\n";
            sql1 += "    A.studentlimit,\n";
            sql1 += "    (select count(userid) from tz_student where a.subj = subj and a.subjseq = subjseq and a.year = year) stucnt,\n";
            sql1 += "    (select count(userid) from tz_propose where a.subj = subj and a.subjseq = subjseq and a.year = year) propcnt,\n";
            sql1 += "    A.isclosed\n";
            sql1 += " from             \n";
            sql1 += "  VZ_SCSUBJSEQ A\n";
            sql1 += " where  \n";
            sql1 += "  A.subj = '" + ss_subjcourse + "'\n";
            sql1 += "  and A.grcode = " + SQLString.Format(ss_grcode) + " \n";
            sql1 += "  and A.year = " + SQLString.Format(v_year) + " \n";
            //sql1+= "  and A.subjseq != "+SQLString.Format(ss_subjseq)+" \n";
            sql1 += "  order by subjseq";

            System.out.println("selectPossibleAddSeq===>" + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                v_eduterm = scbean.getEduTerm(ss_subjcourse, ls1.getString("subjseq"), ls1.getString("year"));
                if (v_eduterm.equals("0")) {
                    v_edutermtxt = "-";
                } else if (v_eduterm.equals("1")) {
                    v_edutermtxt = "��û��";
                } else if (v_eduterm.equals("2")) {
                    v_edutermtxt = "������û�Ⱓ";
                } else if (v_eduterm.equals("3")) {
                    v_edutermtxt = "�������";
                } else if (v_eduterm.equals("4")) {
                    v_edutermtxt = "�����Ⱓ";
                } else if (v_eduterm.equals("5")) {
                    v_edutermtxt = "�����Ϸ�";
                }

                //�̷°������
                v_isclosed = ls1.getString("isclosed");

                if (v_isclosed.equals("Y")) {
                    v_edutermtxt += "<br><font color=red>(�̷»����Ϸ�)</font>";
                } else if (v_isclosed.equals("N")) {
                    v_edutermtxt += "<br><font color=blue>(�̷»�����)</font>";
                }

                dbox.put("d_edutermtxt", v_edutermtxt);
                dbox.put("d_eduterm", v_eduterm);

                list1.add(dbox);
            }

            //}
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
     * ���ð��� ȸ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList selectPossibleCompany(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList list1 = null;
        DataBox dbox = null;

        String sql1 = "";

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql1 += " select  \n";
            sql1 += "   a.comp,\n";
            sql1 += "   b.companynm\n";
            sql1 += " from \n";
            sql1 += "   tz_grcomp a,\n";
            sql1 += "   tz_comp b \n";
            sql1 += " where \n";
            sql1 += "   a.grcode = '" + ss_grcode + "' \n";
            sql1 += "   and b.comp = a.comp\n";

            System.out.println("selectPossibleCompany===>" + sql1);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list1;
    }

    /* �ݷ�ó�� */
    public int SaveRejectProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1 = "";
        String sql0 = "";
        ListSet ls4 = null;
        //        int cancel_cnt      = 0;
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;

        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();
        Hashtable insertData = new Hashtable();

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_course = "";
        String v_year = "";
        String v_subjseq = "";
        String v_tmp_subj_value = "";
        String v_subj_value = "";
        String v_iscourseYn = "";
        String v_cancelkind = "F";
        String v_reason = box.getString("p_rejreasonnm");
        String v_reasoncd = box.getString("p_rejreasoncd");

        //        String  v_eduterm   = "";
        //        String  v_mastercd  = "";
        //        String  v_appstatus = "";
        //        String  v_gubun = "2";
        //        boolean  v_isedutarget = false;
        ProposeBean probean = new ProposeBean();

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    v_course = st1.nextToken();
                    v_iscourseYn = st1.nextToken();
                    break;
                }

                /*
                 * System.out.println("subj = "+v_subj);
                 * System.out.println("year = "+v_year);
                 * System.out.println("subjseq = "+v_subjseq);
                 * System.out.println("userid = "+v_userid);
                 * System.out.println("course = "+v_course);
                 * System.out.println("iscourseYn = "+v_iscourseYn);
                 */

                if (v_iscourseYn.equals("Y")) {
                    v_tmp_subj_value = v_course;
                } else {
                    v_tmp_subj_value = v_subj;
                }

                sql0 = " Select subj, subjcnt ";
                sql0 += " From VZ_SCSUBJSEQ ";
                sql0 += " where ";
                sql0 += " 	scsubj=" + SQLString.Format(v_tmp_subj_value);
                sql0 += " 	and year=" + SQLString.Format(v_year);
                sql0 += " 	and subjseq=" + SQLString.Format(v_subjseq);

                ls4 = connMgr.executeQuery(sql0);

                while (ls4.next()) {
                    v_subj_value = ls4.getString("subj");

                    insertData.clear();
                    insertData.put("connMgr", connMgr);
                    insertData.put("subj", v_subj_value);
                    insertData.put("year", v_year);
                    insertData.put("subjseq", v_subjseq);
                    insertData.put("userid", v_userid);
                    insertData.put("luserid", v_luserid);
                    insertData.put("cancelkind", v_cancelkind);
                    insertData.put("chkfinal", "N");
                    insertData.put("rejectkind", v_reasoncd);
                    insertData.put("rejectedreason", v_reason);

                    isOk1 = probean.updatePropose(insertData);

                    insertData.put("targettb", "TZ_STUDENTREJECT");
                    insertData.put("sourcetb", "TZ_STUDENT");
                    isOk2 = probean.insertStudentreject(insertData);

                    if (probean.getOverStuCount(v_subj_value, v_year, v_subjseq, v_userid) > 0) {
                        isOk3 = probean.deleteStudent(insertData);
                    } else {
                        isOk3 = 1;
                    }

                    if (probean.getOverStoldCount(v_subj_value, v_year, v_subjseq, v_userid) > 0) {
                        isOk3 = probean.deleteStold(insertData);
                    } else {
                        isOk3 = 1;
                    }

                    insertData.put("reason", v_reason);
                    insertData.put("reasoncd", v_reasoncd);

                    isOk4 = probean.insertCancel(insertData);

                    isOk = isOk1 * isOk2 * isOk3 * isOk4;
                }
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
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

    /* �ݷ��� ���� ó�� */
    public int SaveApprovalProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql1 = "";
        //        int cancel_cnt      = 0;
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;

        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();
        Hashtable insertData = new Hashtable();

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        //        String v_cancelkind = "F";
        //        String v_reason     = box.getString("p_rejreasonnm");
        //        String v_reasoncd   = box.getString("p_rejreasoncd");

        //        String  v_comp      = box.getSession("comp");
        //        String  v_company   = v_comp.substring(0,4);

        //        String  v_eduterm   = "";
        //        String  v_mastercd  = "";
        //        String  v_appstatus = "";
        //        String  v_gubun = "2";
        //        boolean  v_isedutarget = false;
        ProposeBean probean = new ProposeBean();

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    break;
                }

                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_subj);
                insertData.put("year", v_year);
                insertData.put("subjseq", v_subjseq);
                insertData.put("userid", v_userid);
                insertData.put("luserid", v_luserid);
                insertData.put("chkfinal", "Y");
                insertData.put("rejectkind", "");
                insertData.put("rejectedreason", "");

                isOk1 = probean.updatePropose(insertData);

                insertData.put("targettb", "TZ_STUDENT");
                insertData.put("sourcetb", "TZ_STUDENTREJECT");

                if (probean.getOverStuCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk2 = probean.deleteStudent(insertData);
                } else {
                    isOk2 = 1;
                }

                if (probean.getStuRejCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk2 = probean.insertStudentreject(insertData);
                    isOk3 = probean.deleteStudentreject(insertData);
                } else {
                    isOk2 = 1;
                    isOk3 = 1;
                }

                if (probean.getCancelCount(v_subj, v_year, v_subjseq, v_userid) > 0) {
                    isOk4 = probean.deleteCancel(insertData);
                } else {
                    isOk4 = 1;
                }

                isOk = isOk1 * isOk2 * isOk3 * isOk4;
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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
     * ��뺸�� �������� ó��
     * 
     * @param box receive from the form object and session
     * @return int 1 : update
     */
    public int SaveNogoyongProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 1;
        int isOk1 = 0;

        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    break;
                }

                sql1 = "update TZ_STUDENT ";
                sql1 += "   set isgoyong = 'N' ,luserid = ?, ldate = ? ";
                sql1 += " where subj = ? and year = ? and subjseq = ? and userid = ? ";
                pstmt = connMgr.prepareStatement(sql1);

                pstmt.setString(1, v_luserid);
                pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_year);
                pstmt.setString(5, v_subjseq);
                pstmt.setString(6, v_userid);

                isOk1 = pstmt.executeUpdate();
                if (isOk1 == 0)
                    isOk = 0;
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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
     * ��뺸�� ������ ó��
     * 
     * @param box receive from the form object and session
     * @return int 1 : update
     */
    public int SaveGoyongProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 1;
        int isOk1 = 0;

        Vector v_check1 = new Vector();
        v_check1 = box.getVector("p_checks");
        Enumeration em = v_check1.elements();

        StringTokenizer st1 = null;
        String v_checks = "";
        String v_userid = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            while (em.hasMoreElements()) {
                v_checks = (String) em.nextElement();

                st1 = new StringTokenizer(v_checks, ",");
                while (st1.hasMoreElements()) {
                    v_userid = st1.nextToken();
                    v_subj = st1.nextToken();
                    v_year = st1.nextToken();
                    v_subjseq = st1.nextToken();
                    break;
                }

                sql1 = "update TZ_STUDENT ";
                sql1 += "   set isgoyong = 'Y' ,luserid = ?, ldate = ? ";
                sql1 += " where subj = ? and year = ? and subjseq = ? and userid = ? ";
                pstmt = connMgr.prepareStatement(sql1);

                pstmt.setString(1, v_luserid);
                pstmt.setString(2, FormatDate.getDate("yyyyMMddHHmmss"));
                pstmt.setString(3, v_subj);
                pstmt.setString(4, v_year);
                pstmt.setString(5, v_subjseq);
                pstmt.setString(6, v_userid);

                isOk1 = pstmt.executeUpdate();
                if (isOk1 == 0)
                    isOk = 0;
            }

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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

}