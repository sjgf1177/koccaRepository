//**********************************************************
//1. ��      ��: ��� �������
//2. ���α׷���: ��� ������ ����� 
//3. ��      ��: 
//4. ȯ      ��: 
//5. ��      ��: 
//6. ��      ��: 
//7. ��      ��:
//**********************************************************
package com.credu.statistics;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.propose.ProposeBean;

public class StatisticsAdminBean {

    public StatisticsAdminBean() {
    }

    /**
     * ������ ������Ȳ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectStatisticsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        // int v_pageno = 1;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        // String ss_edustart = box.getStringDefault("s_edustart", ""); //����������
        // String ss_eduend = box.getStringDefault("s_eduend", ""); //����������

        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");

        // String v_order = box.getString("p_order"); //������ �÷���
        // String v_orderType = box.getString("p_orderType"); //������ ����

        ProposeBean probean = new ProposeBean();
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += " select a.scsubj, a.scyear, a.scsubjseq, a.scsubjnm, count(a.userid) stucnt, \n";
            sql += "		a.scpropstart, a.scpropend, a.scedustar, a.sceduend, a.scbiyong \n";
            sql += " from(select b.scsubj, b.scyear, b.scsubjseq, b.scsubjnm, a.userid, \n";
            sql += " 			 b.scpropstart, b.scpropend, b.scedustar, b.sceduend, b.scbiyong \n";
            sql += " 	  from tz_propose a , vz_scsubjseq b \n";
            sql += " 	  where a.subj = b.subj \n";
            sql += " 		and  a.year = b.year \n";
            sql += " 		and a.subjseq = b.subjseq \n";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and b.year = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and b.scupperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and b.scmiddleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_subjcourse.equals("ALL"))
                sql += " and b.scsubj = " + SQLString.Format(ss_subjcourse) + "\n";
            if (!ss_subjseq.equals("ALL"))
                sql += " and b.scsubjseq = " + SQLString.Format(ss_subjseq) + "\n";

            sql += " 	  group by  b.scsubj, b.scyear, b.scsubjseq, b.scsubjnm, a.userid, \n";
            sql += " 				b.scpropstart, b.scpropend, b.scedustar, b.sceduend, b.scbiyong \n";
            sql += " 	  )a \n";
            sql += " group by  a.scsubj, a.scyear, a.scsubjseq, a.scsubjnm, \n";
            sql += " 		   a.scpropstart, a.scpropend,  a.scedustar, a.sceduend,a.scbiyong \n";

            if (!v_startdate.equals("")) {//
                sql += "  and substring(c.edustart, 1 , 8)  =" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and substring(c.eduend, 1 , 8 ) =" + SQLString.Format(v_enddate);
            }

            System.out.println("StatisticsAdminBean =========== : selectStatisticsList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

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
     * ���� ������Ȳ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectStatisticsMonthList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        // int v_pageno = 1;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�����з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        // String ss_edustart = box.getStringDefault("s_edustart", ""); //����������
        // String ss_eduend = box.getStringDefault("s_eduend", ""); //����������

        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");

        // String v_order = box.getString("p_order"); //������ �÷���
        // String v_orderType = box.getString("p_orderType"); //������ ����

        ProposeBean probean = new ProposeBean();
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += "select a.submonth, count(a.userid) stucnt, sum(a.scbiyong) totalbiyong \n";
            sql += "from(select b.scsubj, b.scyear, b.scsubjseq, b.scsubjnm, a.userid, b.scpropstart, \n";
            sql += "	b.scpropend, b.scedustar, b.sceduend, b.scbiyong ,  substring(b.sceduend,5,2) submonth\n";
            sql += "		from tz_propose a , vz_scsubjseq b \n";
            sql += "	 	where a.subj = b.subj \n";
            sql += "	 			and  a.year = b.year \n";
            sql += "	 			and a.subjseq = b.subjseq \n";

            if (!ss_grcode.equals("ALL")) {
                sql += " and b.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and b.year = " + SQLString.Format(v_year) + "\n";
            }

            if (!ss_grseq.equals("ALL"))
                sql += " and b.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and b.scupperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and b.scmiddleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_subjcourse.equals("ALL"))
                sql += " and b.scsubj = " + SQLString.Format(ss_subjcourse) + "\n";
            if (!ss_subjseq.equals("ALL"))
                sql += " and b.scsubjseq = " + SQLString.Format(ss_subjseq) + "\n";

            sql += "	 	  	group by  b.scsubj, b.scyear, b.scsubjseq, b.scsubjnm, a.userid, \n";
            sql += "	 				b.scpropstart, b.scpropend, b.scedustar, b.sceduend, b.scbiyong ,  substring(b.sceduend,5,2) \n";
            sql += "	 	  	)a \n";
            sql += "	 	group by  a.submonth \n";

            if (!v_startdate.equals("")) {//
                sql += "  and substring(c.edustart, 1 , 8)  =" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and substring(c.eduend, 1 , 8 ) =" + SQLString.Format(v_enddate);
            }

            //		System.out.println("StatisticsAdminBean ���� : selectStatisticsMonthList: "+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

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
     * ������Ȳ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectStatisticsSaleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        // int v_pageno = 1;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //�з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //�о�
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //���̵�
        // String ss_startdate = box.getStringDefault("s_startdate", ""); //�����Ⱓ �˻� ������
        // String ss_enddate = box.getStringDefault("s_enddate", ""); //�����Ⱓ �˻� ������
        // String ss_option1 = box.getStringDefault("s_option1", ""); //��豸��
        String ss_option2 = box.getStringDefault("s_option2", "ALL"); //��躰 ����

        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");

        // String v_order = box.getString("p_order"); //������ �÷���
        // String v_orderType = box.getString("p_orderType"); //������ ����

        String v_year = "";

        if (v_year.equals(""))
            v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "SELECT   a.tid, a.userid, a.subj, a.YEAR, a.subjseq, a.subjnm, a.edustart, ";
            sql += "         a.eduend, a.biyong, a.RESULT, a.pgauthdate, a.pgauthtime, ";
            sql += "         a.inputname, a.price, a.RANK, ";
            sql += "         NVL ((SELECT isuseyn ";
            sql += "                 FROM tz_tax ";
            sql += "                WHERE subj = a.subj AND YEAR = a.YEAR AND subjseq = a.subjseq), ";
            sql += "              'N' ";
            sql += "             ) istaxyn, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = '000') milleclassname, ";
            sql += "         (SELECT sa.classname ";
            sql += "            FROM tz_subjatt sa ";
            sql += "           WHERE sa.upperclass = a.upperclass ";
            sql += "             AND sa.middleclass = a.middleclass ";
            sql += "             AND sa.lowerclass = a.lowerclass) lowerclassname, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN price ";
            sql += "            ELSE 0 ";
            sql += "         END AS realtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN SUM (biyong) OVER (PARTITION BY tid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS subtotal, ";
            sql += "         CASE ";
            sql += "            WHEN RANK = 1 ";
            sql += "               THEN COUNT (*) OVER (PARTITION BY tid) ";
            sql += "            ELSE 0 ";
            sql += "         END AS rowspan ";
            sql += "    FROM (SELECT   a.tid, b.userid, b.subj, b.YEAR, b.subjseq, c.subjnm, ";
            sql += "                   c.biyong, d.upperclass, d.middleclass, d.lowerclass, ";
            sql += "                   c.edustart, c.eduend, paymethod, ";
            sql += "                   CASE ";
            sql += "                      WHEN resultcode = '00' ";
            sql += "                         THEN '����' ";
            sql += "                      ELSE '����' ";
            sql += "                   END AS RESULT, a.pgauthdate, a.pgauthtime, a.inputname, a.price, ";
            sql += "                   RANK () OVER (PARTITION BY b.tid ORDER BY b.subj) AS RANK ";
            sql += "              FROM tz_billinfo a INNER JOIN tz_propose b ON a.tid = b.tid ";
            sql += "                   INNER JOIN tz_subjseq c ";
            sql += "                   ON b.subj = c.subj ";
            sql += "                 AND b.YEAR = c.YEAR ";
            sql += "                 AND b.subjseq = c.subjseq ";
            sql += "                   INNER JOIN tz_subj d ON b.subj = d.subj ";

            if (!ss_grcode.equals("ALL")) {
                sql += " and c.grcode = " + SQLString.Format(ss_grcode) + "\n";
                sql += " and c.year = " + SQLString.Format(v_year) + "\n";
            }
            if (!ss_grseq.equals("ALL"))
                sql += " and c.grseq = " + SQLString.Format(ss_grseq);
            if (!ss_uclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(ss_uclass) + "\n";
            if (!ss_mclass.equals("ALL"))
                sql += " and d.middleclass = " + SQLString.Format(ss_mclass) + "\n";
            if (!ss_lclass.equals("ALL"))
                sql += " and d.lowerclass = " + SQLString.Format(ss_lclass) + "\n";
            if (!ss_option2.equals("ALL")) {
                if ("CARD".equals(ss_option2)) { //ī��
                    sql += " and a.paymethod IN ('VCard', 'Card')";
                } else if ("DIRECT".equals(ss_option2)) { //������ü
                    sql += " and a.paymethod = 'DirectBank";
                } else if ("VBANK".equals(ss_option2)) { //�������
                    sql += " and a.paymethod = 'VBank";
                } else if ("BOOK".equals(ss_option2)) { //�������Ա�
                    sql += " and a.paymethod = 'BankBook";
                }
            }

            if (!v_startdate.equals("")) {//
                sql += "  and a.pgauthdate  >=" + SQLString.Format(v_startdate);
            }

            if (!v_enddate.equals("")) {//
                sql += "  and a.pgauthdate <=" + SQLString.Format(v_enddate);
            }

            sql += "          ORDER BY b.tid, b.userid, b.userid, b.YEAR) a ";
            sql += "ORDER BY YEAR DESC, tid, userid, RANK, pgauthdate DESC ";

            System.out.println("StatisticsAdminBean =========== : selectStatisticsSaleList: " + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

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

}