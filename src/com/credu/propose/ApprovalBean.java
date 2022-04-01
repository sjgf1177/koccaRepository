// **********************************************************
// 1. �� ��: ��������OPERATION BEAN
// 2. ���α׷���: GrseqBean.java
// 3. �� ��:
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 0.1
// 6. �� ��: LeeSuMin 2003. 07. 16
// 7. �� ��:
// **********************************************************
package com.credu.propose;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class ApprovalBean {

    public ApprovalBean() {
    }

    /**
     * ������û ���θ���Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������û ���δ�� ��� ����Ʈ
     */
    public ArrayList<DataBox> SelectApprovalScreenList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList<DataBox> list1 = null;
        StringBuffer sb = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        StringBuffer where = new StringBuffer();
        DataBox dbox = null;

        String p_grcode = box.getString("s_grcode");
        String p_gyear = box.getString("s_gyear");
        String p_grseq = box.getStringDefault("s_grseq", "ALL");
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // �����з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // �����з�
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // �����з�
        String p_subjcourse = box.getString("s_subjcourse");
        String p_subjseq = box.getString("s_subjseq");

        String p_membergubun = box.getString("p_membergubun"); // ȸ��<br>�з�
        String pp_chkfinal = box.getString("pp_chkfinal"); // ���λ���
        String p_paymethod = box.getString("p_paymethod"); // �������
        String p_resultcode = box.getString("p_resultcode"); // ����<br>���
        String p_biyong = box.getString("p_biyong"); // ������ ��/���� ����

        String p_appstatus = box.getString("p_appstatus");
        String v_appstart = box.getString("s_appstart"); // ������û������
        String v_append = box.getString("s_append"); // ������û������
        String v_datefield = box.getString("s_datefield"); // ������û&�������� �����ʵ�

        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); // ������ ����

        String ss_area = box.getString("s_area");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            if (!v_appstart.equals("") && !v_append.equals("")) {
                v_appstart = StringManager.replace(v_appstart, "-", "");
                v_append = StringManager.replace(v_append, "-", "");

                if (v_datefield.equals("pgauthdate")) {// ��������
                    sql.append("\nSELECT A.*, (case left(a.scsubj,1) when 'C0' then ");
                    sql.append("	\n	( SELECT max(tid) FROM tz_basketbill BB ");
                    sql.append("\n	 WHERE BB.course = a.scsubj AND BB.courseyear = a.scyear ");
                    sql.append("\n	AND BB.courseseq = a.scsubjseq AND BB.userid = b.userid) else ");
                    sql.append("	\n	( SELECT max(tid) FROM tz_basketbill  bb ");
                    sql.append("\n	WHERE bb.subj = a.subj AND bb.year = a.year AND bb.subjseq = a.subjseq ");
                    sql.append("\n	AND bb.userid = a.userid) end ) TID ");

                    where.append(" and b.pgauthdate between '" + v_appstart + "' and '" + v_append + "'	");
                } else { // ������û����
                // sql.append("\nSELECT A.*, (case left(a.scsubj,1) when 'C' then ");
                    sql.append("\nSELECT A.*, (case left(a.scsubj,1) when 'C0' then ");
                    sql.append("	\n	( SELECT max(tid) FROM tz_basketbill BB ");
                    sql.append("\n	WHERE BB.course = a.scsubj AND BB.courseyear = a.scyear ");
                    sql.append("\n	AND BB.courseseq = a.scsubjseq AND BB.userid = a.userid) else ");
                    sql.append("	\n	( SELECT max(tid) FROM tz_basketbill bb ");
                    sql.append("\n WHERE bb.subj = a.subj AND bb.year = a.year AND bb.subjseq = a.subjseq ");
                    sql.append("\n	AND bb.userid = a.userid ) end ) TID ");

                    where.append("\n	and substring(b.appdate,1,8) between '" + v_appstart + "' and '" + v_append + "' ");
                }
            } else {
                sql.append("\n SELECT A.*, (case left(a.scsubj,1) when 'C0' then ");
                sql.append("	\n	( SELECT max(tid) FROM tz_basketbill BB ");
                sql.append("\n	WHERE BB.course = a.scsubj AND BB.courseyear = a.scyear ");
                sql.append("\n	AND BB.courseseq = a.scsubjseq AND BB.userid = a.userid) else ");
                sql.append("	\n	( SELECT max(tid) FROM tz_basketbill bb ");
                sql.append("\n	WHERE bb.subj = a.subj AND bb.year = a.year ");
                sql.append("\n	AND bb.subjseq = a.subjseq AND bb.userid = a.userid ) end ) TID ");
            }

            sb.append("\nSELECT A.*, ");
            sb.append("\n        b.resultcode, b.resultmsg, b.paymethod, ");
            sb.append("\n        b.mid, b.goodname, b.usernm, b.price, b.buyername, b.buyertel,	");
            sb.append("\n        b.buyeremail, b.resulterrcode,b.pgauthdate, b.pgauthtime, b.cancelyn,	");
            sb.append("\n        b.cancelresult, b.canceldate, b.canceltime, b.inputname, b.inputdate,	");
            sb.append("\n        case paymethod  when 'Card' then '�ſ�ī��' when 'VCard' then '�ſ�ī��'	");
            sb.append("\n when 'DirectBank' then '������ü' when 'BankBook' then '������' when 'FreePay' then '�������' end paymethodnm	");
            sb.append("\nFROM (	");
            sb.append(sql.toString());
            sb.append("\nFROM (	");
            sb.append("\n	SELECT ");
            sb.append("\n		A.scsubjnm,A.SUBJ,A.YEAR,A.SUBJSEQ, A.SCSUBJ, A.SCYEAR, A.SCSUBJSEQ, A.SUBJSEQGR, A.SUBJNM,	");
            sb.append("\n		A.ISCLOSED,A.ISBELONGCOURSE, A.SUBJCNT, A.COURSENM , A.COURSE, a.biyong, ");
            sb
                    .append("\n		b.userid, b.appdate, b.isdinsert, b.chkfirst, b.chkfinal, b.ischkfirst, b.billstat, b.rejectkind, b.rejectedreason,B.CANCELDATE needcancel,	");
            sb
                    .append("\n		c.membergubun,c.name, crypto.dec('normal', c.email) email, crypto.dec('normal', c.handphone) handphone,c.resno, c.resno1, c.resno2, c.comptext,c.jikup, c.degree, c.workfieldnm, ");
            sb.append("\n		GET_CODENM('0070', c.jikup) jikupnm, GET_CODENM('0069', c.degree) degreenm,f.grseqnm, ");
            sb.append("\n		G.mastercd, H.masternm,H.isedutarget, ");
            sb.append("\n		count(b.userid) as usercnt, NVL(MIN(A.EDUSTART),'2010112208') EDUSTART,  c.deptnam ");
            sb.append("\n	from VZ_SCSUBJSEQ  a, tz_propose b, tz_member c, tz_grseq f, tz_mastersubj G, tz_mastercd H ");
            sb.append("\n	where a.subj=b.subj	");
            sb.append("\n	    and a.year=b.year ");
            sb.append("\n	    and a.subjseq=b.subjseq ");
            sb.append("\n	    and a.grseq=f.grseq ");
            sb.append("\n	    and a.grcode=f.grcode ");
            sb.append("\n	    and a.grcode=c.grcode ");
            sb.append("\n	    and a.gyear=f.gyear	");
            sb.append("\n	    and b.userid=c.userid ");
            // sb.append("\n	    and  NVL(LENGTH(TRIM(B.cancelkind)),0) = 0 ");
            //sb.append("\n	    and b.appdate is not null ");
            sb.append("\n	    AND a.subj=G.subjcourse(+) and a.year=G.year(+) and a.subjseq=G.subjseq(+) ");
            sb.append("\n	    AND G.mastercd = H.mastercd(+) ");
            sb.append(where.toString());
            sb.append("\n    and a.grcode=" + SQLString.Format(p_grcode));
            sb.append("\n    and a.gyear=" + SQLString.Format(p_gyear));

            if (!p_grseq.equals("ALL")) {
                sb.append("\n	and a.grseq=" + SQLString.Format(p_grseq));
            }
            if (!ss_uclass.equals("ALL")) {
                sb.append("\n	and a.scupperclass =" + SQLString.Format(ss_uclass));
            }
            if (!ss_mclass.equals("ALL")) {
                sb.append("\n	and a.scmiddleclass=" + SQLString.Format(ss_mclass));
            }
            if (!ss_lclass.equals("ALL")) {
                sb.append("\n	and a.sclowerclass =" + SQLString.Format(ss_lclass));
            }
            if (!p_subjcourse.equals("ALL")) {
                sb.append("\n	and a.scsubj=" + SQLString.Format(p_subjcourse));
            }
            if (!p_subjseq.equals("ALL")) {
                sb.append("\n	and a.scsubjseq=" + SQLString.Format(p_subjseq));
            }

            if (p_membergubun.length() > 0 && !p_membergubun.equals("ALL")) {
                sb.append("\n	and membergubun=" + SQLString.Format(p_membergubun));// ȸ��<br>�з�
            }
            if (pp_chkfinal.length() > 0 && !pp_chkfinal.equals("ALL")) {
                sb.append("\n	and b.chkfinal=" + SQLString.Format(pp_chkfinal));// ���λ���
            }
            if (!p_biyong.equals("ALL")) {
                if (p_biyong.equals("Y")) {
                    sb.append("\n   and a.biyong > 0 ");
                } else {
                    sb.append("\n   and a.biyong <= 0 ");
                }
            }

            // p_step : 1-�������, 2-����
            // p_appstatus : B-��ó��, Y-����, N-�ݷ�
            if (!p_appstatus.equals("ALL")) {
                sb.append("\n	and b.chkfinal = " + SQLString.Format(p_appstatus));
            }

            if (!ss_area.equals("T"))
                sb.append(" and a.area=" + SQLString.Format(ss_area));

            sb.append("\n	GROUP BY ");
            sb.append("\n		A.scsubjnm,A.SUBJ,A.YEAR,A.SUBJSEQ, A.SCSUBJ, A.SCYEAR, A.SCSUBJSEQ, A.SUBJSEQGR, A.SUBJNM,	");
            sb.append("\n		A.ISCLOSED,A.ISBELONGCOURSE, A.SUBJCNT, A.COURSENM , A.COURSE, a.biyong, ");
            sb
                    .append("\n		b.userid, b.appdate, b.isdinsert, b.chkfirst, b.chkfinal, b.ischkfirst, b.billstat, b.rejectkind, b.rejectedreason,B.CANCELDATE, ");
            sb.append("\n		c.membergubun,c.name, c.email  , c.handphone,c.resno, c.resno1, c.resno2, c.comptext,c.jikup, c.degree, F.grseqnm, ");
            sb.append("\n		G.mastercd,H.masternm,H.isedutarget, c.deptnam, c.workfieldnm ");
            sb.append("\n	) A) A LEFT OUTER JOIN tz_billinfo b ON A.TID = b.tid ");
            if (p_paymethod.length() > 0 && !p_paymethod.equals("ALL")) {
                sb.append("\n	and paymethod=" + SQLString.Format(p_paymethod));// �������
            }
            if (p_resultcode.length() > 0 && !p_resultcode.equals("ALL")) {
                sb.append("\n	and resultcode=" + SQLString.Format(p_resultcode));// ����<br>���
            }

            if (!v_order.equals("subj")) {
                sb.append("\n	order by a.course, scsubj, scyear, scsubjseq, scsubjnm ");
            } else {
                sb.append("\n	order by a.course, a.sub j" + v_orderType);
            }
            ls = connMgr.executeQuery(sb.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();

                // if (p_grcode.equals("N000001")) {
                // ====================================================
                // �������� ��ȣȭ - HTJ
                /*
                 * SeedCipher seed = new SeedCipher(); if (!dbox.getString("d_resno2").equals(""))
                 * dbox.put("d_resno2",seed.decryptAsString(Base64.decode(dbox.getString("d_resno2")), seed.key.getBytes(), "UTF-8")); if
                 * (!dbox.getString("d_email").equals("")) dbox.put("d_email",seed.decryptAsString(Base64.decode(dbox.getString("d_email")),
                 * seed.key.getBytes(), "UTF-8")); if (!dbox.getString("d_hometel").equals(""))
                 * dbox.put("d_hometel",seed.decryptAsString(Base64.decode(dbox.getString("d_hometel")), seed.key.getBytes(), "UTF-8")); if
                 * (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone",seed.decryptAsString(Base64.decode(dbox.getString("d_handphone")),
                 * seed.key.getBytes(), "UTF-8")); if (!dbox.getString("d_addr2").equals(""))
                 * dbox.put("d_addr2",seed.decryptAsString(Base64.decode(dbox.getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                 */

                /*
                 * if (!dbox.getString("d_resno2").equals("")) dbox.put("d_resno2", encryptUtil.decrypt(dbox.getString("d_resno2"))); if
                 * (!dbox.getString("d_email").equals("")) dbox.put("d_email", encryptUtil.decrypt(dbox.getString("d_email"))); if
                 * (!dbox.getString("d_hometel").equals("")) dbox.put("d_hometel", encryptUtil.decrypt(dbox.getString("d_hometel"))); if
                 * (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone", encryptUtil.decrypt(dbox.getString("d_handphone"))); if
                 * (!dbox.getString("d_addr2").equals("")) dbox.put("d_addr2", encryptUtil.decrypt(dbox.getString("d_addr2")));
                 */
                // ====================================================
                // }
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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
     * OffLine ������û ���θ���Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������û ���δ�� ��� ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList OffLineSelectApprovalScreenList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList list1 = null;
        String sql = "";
        DataBox dbox = null;

        String p_grcode = box.getString("s_grcode");
        String p_gyear = box.getString("s_gyear");
        String p_grseq = box.getStringDefault("s_grseq", "ALL");
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // �����з�
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // �����з�
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // �����з�
        String p_subjcourse = box.getString("s_subjcourse");
        String p_subjseq = box.getString("s_subjseq");

        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); // ������ ����

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList();

            sql = " select scsubj, scyear, scsubjseq, subjseqgr, scsubjnm, NVL(min(a.edustart),'2010112208') edustart, e.biyong,  "
                    + "		b.userid, b.comp, c.jikup, b.appdate, b.isdinsert, b.chkfirst,	"
                    + "		b.chkfinal, b.ischkfirst, b.billstat, b.rejectkind, b.rejectedreason,	c.membergubun,  "
                    + "		c.name, c.comptel, c.cono, get_compnm(c.comp,2,2) companynm,get_deptnm(c.deptnam,'') compnm, get_jikupnm(c.jikup,c.comp,c.jikupnm) jikupnm, get_jikwinm(c.jikwi,c.comp) jikwinm, f.grseqnm, a.isclosed,"
                    + "		(select count(*) from tz_propose where subj=a.subj and year=a.year and subjseq=a.subjseq and chkfinal='N' and userid=b.userid) b_cnt, " // �ݷ���
                                                                                                                                                                 // Ƚ��
                    + "		(select mastercd from tz_mastersubj where subjcourse=a.subj and year=a.year and subjseq=a.subjseq ) mastercd , "
                    + "		(select masternm from tz_mastercd where mastercd = (select mastercd from tz_mastersubj where subjcourse=a.subj and year=a.year and subjseq=a.subjseq )) masternm, "
                    + "		(select isedutarget from tz_mastercd where mastercd = (select mastercd from tz_mastersubj where subjcourse=a.subj and year=a.year and subjseq=a.subjseq )) isedutarget "
                    + "	from VZ_SCSUBJSEQ  a, tz_propose b, tz_member c, tz_subj e, tz_grseq f  "
                    + "  where a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq"
                    + "	and b.userid=c.userid and b.subj=e.subj and f.grseq=a.grseq and f.grcode=a.grcode and f.gyear=a.gyear	" + "	and a.grcode="
                    + SQLString.Format(p_grcode) + "	and a.gyear=" + SQLString.Format(p_gyear);
            if (!p_grseq.equals("ALL")) {
                sql += "	and a.grseq=" + SQLString.Format(p_grseq);
            }
            sql += "	and NVL(b.cancelkind,'0') = '0'	";
            // �˻���Ŀ� ���� ���� �˻����� �б�
            // if(!p_searchkind.equals("master")){
            if (!ss_uclass.equals("ALL")) {
                sql += " and a.scupperclass ='" + ss_uclass + "' ";
            }
            if (!ss_mclass.equals("ALL")) {
                sql += " and a.scmiddleclass='" + ss_mclass + "' ";
            }
            if (!ss_lclass.equals("ALL")) {
                sql += " and a.sclowerclass ='" + ss_lclass + "' ";
            }
            if (!p_subjcourse.equals("ALL")) {
                sql += " and a.scsubj='" + p_subjcourse + "' ";
            }
            if (!p_subjseq.equals("ALL")) {
                sql += " and a.scsubjseq='" + p_subjseq + "' ";
                // }
                // else{
                // sql +=" and a.mastercd='"+p_mastercd+"' ";
                // }
            }

            sql += "  group by scsubj, scyear, scsubjseq, subjseqgr, scsubjnm, e.biyong,  a.subj, a.year, a.subjseq,";
            sql += "			b.userid, b.comp, c.jikup, b.appdate, b.isdinsert, b.chkfirst,	c.membergubun,";
            sql += "			b.chkfinal, b.ischkfirst, b.billstat, b.rejectkind, b.rejectedreason, b.userid,";
            sql += "			c.name, c.comptel, c.cono, get_compnm(c.comp,2,2), get_deptnm(c.deptnam,''), ";
            sql += " get_jikupnm(c.jikup,c.comp,c.jikupnm), get_jikwinm(c.jikwi,c.comp), f.grseqnm,a.isclosed  ";

            if (v_order.equals("subj")) {
                v_order = "a.subj";
            }

            if (v_order.equals("")) {
                sql += " order by scsubj, scyear, scsubjseq, scsubjnm";
            } else {
                sql += " order by " + v_order + v_orderType;
            }

            System.out.println("sql_approval====>" + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
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
     * �������� ���� ������û ���θ���Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������û ���δ�� ��� ����Ʈ
     */
    public ArrayList<DataBox> SelectOffLineApprovalScreenList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String p_grcode = box.getString("s_grcode");
        String v_subjgubun = box.getString("s_subjgubun");
        String v_appstatus = box.getString("p_appstatus");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); // ������ ����

        String v_propstart = box.getString("p_propstart");
        String v_propend = box.getString("p_propend");
        String v_subergubun = "";

        if (p_grcode.equals("G01")) {
            v_subergubun = "gamesuperyn";
        } else {
            v_subergubun = "koccasuperyn";
        }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " Select ";
            sql += " 	p.seq,  p.grcode, p.userid, p.ldate, p.status, s.subjnm, s.dday, s.starttime, s.endtime, ";
            sql += " 	s.place, m.name, m.email, m.handphone, m." + v_subergubun
                    + " as superyn, m.memberGubun, c.codenm as gubunNm , p.ldate, s.propstart, s.propend, ";

            sql += "			m.resno, m.comptext,	";
            sql += "			m.jikup, m.degree, ";
            sql += "		(select codenm from tz_code where gubun = '0070' and code = m.jikup) jikupnm,  ";
            sql += "		(select codenm from tz_code where gubun = '0069' and code = m.degree) degreenm ";

            sql += " From TZ_OFFLINEPROPOSE p ";
            sql += " 	Left Outer Join TZ_OFFLINESUBJ s ON s.seq = p.seq ";
            sql += " 	Join TZ_CODE c ON s.subjgubun = c.code ";
            sql += "  	Join TZ_MEMBER m	ON m.userid = p.userid ";
            sql += "		Left Outer Join VZ_SCSUBJSEQ  a on s.subj = a.scsubj ";
            sql += " Where c.gubun = '0061'";

            // �˻���Ŀ� ���� ���� �˻����� �б�
            if (!v_subjgubun.equals("ALL")) {
                sql += " and  c.code ='" + v_subjgubun + "' ";
            }

            // if (!ss_uclass.equals("ALL")) sql +=" and a.scupperclass ='"+ss_uclass+"' ";
            // if (!ss_mclass.equals("ALL")) sql +=" and a.scmiddleclass='"+ss_mclass+"' ";
            // if (!ss_lclass.equals("ALL")) sql +=" and a.sclowerclass ='"+ss_lclass+"' ";
            // if (!p_subjcourse.equals("ALL")) sql +=" and a.subj='"+p_subjcourse+"' ";

            // if (!p_subjseq.equals("ALL")) sql +=" and a.scsubjseq='"+p_subjseq+"' ";

            if (!v_appstatus.equals("ALL")) {
                sql += " and p.status='" + v_appstatus + "' ";
            }
            if (!v_propstart.equals("") && !v_propend.equals("")) {
                sql += " and substring(s.dday,1,4) || '-' || substring(s.dday,5,2) || '-' || substring(s.dday,7,2) between ";
                sql += " '" + v_propstart + "' ";
                sql += " and '" + v_propend + "' ";
            }

            if (v_order.equals("subj")) {
                v_order = " a.subj";
            }

            if (v_order.equals("")) {
                sql += " order by m." + v_subergubun + ", p.propstart";
            } else {
                sql += " order by m." + v_subergubun + ", " + v_order + v_orderType;
            }

            System.out.println("sql_approval====>" + sql);
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
     * �������� ���ܽ���ó��
     * 
     * @param box receive from the form object and session
     * @return int �����(0,1)
     **/
    public int OffLineApprovalProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null, pstmt2 = null;
        int isOk = 0;
        int i = 0;
        ListSet ls = null;
        String sql = "", sql2 = "";
        Vector<String> v_tmp0 = box.getVector("p_status");
        String tmpArr = "";
        String[] tmpArr2 = null;
        String v_userid = "";
        String v_seq = "";
        String v_status = "";
        String v_grcode = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " update TZ_OFFLINEPROPOSE set status = ?";
            sql += " Where grcode=? and userid=? and seq = ? ";

            sql2 = " Delect TZ_OFFLINEPROPOSE ";
            sql2 += " Where grcode=? and userid=? and seq = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt2 = connMgr.prepareStatement(sql2);

            for (i = 0; i < v_tmp0.size(); i++) {
                tmpArr = v_tmp0.elementAt(i).toString();
                tmpArr2 = tmpArr.split(":");

                v_seq = tmpArr2[0];
                v_grcode = tmpArr2[1];
                v_userid = tmpArr2[2];
                v_status = tmpArr2[3];
                System.out.println(v_seq + " : " + v_grcode + " : " + v_userid + " : " + v_status);
                if (v_status.equals("Y")) {
                    pstmt.setString(1, v_status);
                    pstmt.setString(2, v_grcode);
                    pstmt.setString(3, v_userid);
                    pstmt.setString(4, v_seq);

                    isOk = pstmt.executeUpdate();
                } else if (v_status.equals("W")) {
                    pstmt.setString(1, v_status);
                    pstmt.setString(2, v_grcode);
                    pstmt.setString(3, v_userid);
                    pstmt.setString(4, v_seq);

                    isOk = pstmt.executeUpdate();
                } else if (v_status.equals("N")) {
                    pstmt.setString(1, v_status);
                    pstmt.setString(2, v_grcode);
                    pstmt.setString(3, v_userid);
                    pstmt.setString(4, v_seq);

                    isOk = pstmt.executeUpdate();
                } else if (v_status.equals("D")) {
                    pstmt2.setString(1, v_status);
                    pstmt2.setString(2, v_grcode);
                    pstmt2.setString(3, v_userid);
                    pstmt2.setString(4, v_seq);

                    isOk = pstmt2.executeUpdate();
                }
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (isOk > 0) {
                connMgr.commit();
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
     * get Row Count
     * 
     * @param ArrayList ��û����Ʈ
     * @param String ��û����/�ڽ��ڵ�,
     * @return int ����
     */
    public int getRowCnt(ArrayList<ApprovalScreenData> list1, String p_scsubj, String p_scyear, String p_scsubjseq) {
        int ncnt = 0;
        ApprovalScreenData data = null;

        for (int i = 0; i < list1.size(); i++) {
            data = (ApprovalScreenData) list1.get(i);

            if (data.getScsubj().equals(p_scsubj) && data.getScyear().equals(p_scyear) && data.getScsubjseq().equals(p_scsubjseq)) {
                ncnt++;
            }
        }
        return ncnt;
    }

    /**
     * ���α��Ѱ� ���
     * 
     * @param box receive from the form object and session
     * @return int ���α��Ѱ�(0,1,2,3) == ���α���ǥ ======================================================================* �μ���� ȸ���� �׷��� �����̻� 1�����ι̻��� 0 2 2 2
     *         1�����λ��� 1 3 3 3
     * 
     *         0 : ���α��� ���� 1: 1�����θ� 2: �������θ� 3: 1��/�������� ��ΰ���
     * 
     *         ==> �������� �̻�� �����׷��� ��쿡�� ���������� �����ϵ��� �Ѵ�. ����: �����׷��������� 1��/�������� ��뿩�δ� Ȩ�������� ������û ���μ����� ������ �ְԵǳ� ������û�� �ڵ����� 1��/�������ε��� ������� ���������� �� �ֵ��� �ϱ� ����
     *         ------------------------------------------------------------------------------------*
     **/
    public int getApprovalAuth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String p_grcode = box.getString("s_grcode");
        String p_gadmin = box.getSession("gadmin");

        String v1 = "", v2 = "";
        int v_appauth = 0;

        // String v_Grseqnm = box.getString("p_grtype");

        try {
            connMgr = new DBConnectionManager();

            sql = " select  NVL(chkfirst,'Y') chkfirst, NVL(chkfinal,'Y') chkfinal from tz_grcode where grcode=" + SQLString.Format(p_grcode);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v1 = ls.getString("chkfirst");
                v2 = ls.getString("chkfinal");
            }

            sql = " select  applevel from tz_gadmin where gadmin=" + SQLString.Format(p_gadmin);
            ls = connMgr.executeQuery(sql);
            ls.next();
            v_appauth = ls.getInt("applevel");

            if (v1.equals("Y") && v2.equals("Y")) {
            } else if (v1.equals("Y")) {
                if (v_appauth > 1) {
                    v_appauth = 1;
                }
            } else if (v2.equals("Y")) {
                if (v_appauth < 2) {
                    v_appauth = 0;
                }
                if (v_appauth == 3) {
                    v_appauth = 1;
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
        return v_appauth;
    }

    /**
     * ���ܽ���ó��
     * 
     * @param box receive from the form object and session
     * @return int �����(0,1)
     **/
    @SuppressWarnings("unchecked")
    public int ApprovalProcess(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null;
        int isOk = 0;
        int isOk1 = 0;
        int isOk3 = 0;
        ListSet ls = null, ls2 = null, ls3 = null;
        String sql = "", sql2 = "", sql3 = "", sql4 = "", sql0 = "";
        ;
        String p_grcode = box.getString("p_grcode");

        int p_step = box.getInt("p_step");

        Vector vec_param = box.getVector("p_params");
        Vector vec_rejectkind = box.getVector("p_rejectkind");
        Vector vec_rejectedreason = box.getVector("p_rejectedreason");

        Vector vec_chk = null;
        if (p_step == 1) {
            vec_chk = box.getVector("p_chkfirst");
        } else {
            vec_chk = box.getVector("p_chkfinal");
        }

        String v_luserid = box.getSession("userid"); // ���Ǻ������� ����� id�� �����´�.
        String v_param = ""; // scsubj,scyear,scsubjseq,userid
        String v_rejectkind = "";
        String v_rejectedreason = "";
        String v_codegubun = "";
        String v_grtype = "";

        String v_scsubj = "", v_scyear = "", v_scsubjseq = "", v_userid = "", v_chk;
        String v_targetColumn = "chkfirst";
        Hashtable insertData = null;
        ProposeBean propBean = new ProposeBean();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);// /////////////

            if (p_step == 2) {
                v_targetColumn = "chkfinal";
            }

            sql = "select chkfinal from tz_grcode where grcode=" + StringManager.makeSQL(p_grcode);
            ls = connMgr.executeQuery(sql);
            ls.next();

            sql = " update tz_propose set " + v_targetColumn + "=? ";

            sql2 = sql + "					,luserid  =?" // 2
                    + "					,ldate	=to_char(sysdate,'YYYYMMDDHH24MISS') " //
                    + "  where userid=? " // 3
                    // ������ : 05.11.17 ������ : �̳��� _ || ����
                    // + "	and subj||year||subjseq " //
                    // + "		in (select subj||year||subjseq from tz_subjseq " //
                    + "	and subj||year||subjseq " //
                    + "		in (select subj||year||subjseq from tz_subjseq " //
                    + "			where course=? and cyear=? and courseseq=?)"; // 4 5

            sql = sql + "					,rejectkind  =?" // 2
                    + "					,rejectedreason  =?" // 3
                    + "					,luserid  =?" // 4
                    + "					,ldate	=to_char(sysdate,'YYYYMMDDHH24MISS') " + "  where userid=? and subj=? and year=? and subjseq=?";// 5 6 7 8

            pstmt = connMgr.prepareStatement(sql);
            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < vec_param.size(); i++) {
                v_param = vec_param.elementAt(i).toString(); // �����ڵ�����Userid...
                v_chk = vec_chk.elementAt(i).toString(); // ��ó��/����/�ݷ�/����
                v_rejectkind = "";
                v_rejectedreason = "";

                if (v_chk.equals("N")) {
                    v_rejectkind = vec_rejectkind.elementAt(i).toString();
                    v_rejectedreason = vec_rejectedreason.elementAt(i).toString();
                }

                StringTokenizer arr_tmp = new StringTokenizer(v_param, ",");
                // �����ڵ� 4�ڸ� �Ͻ�
                v_scsubj = arr_tmp.nextToken();
                v_scyear = arr_tmp.nextToken();
                v_scsubjseq = arr_tmp.nextToken();
                // v_iscourseYn =
                arr_tmp.nextToken();
                // v_course =
                arr_tmp.nextToken();
                v_userid = arr_tmp.nextToken();

                sql0 = " Select subj, subjseq ";
                sql0 += " From VZ_SCSUBJSEQ ";
                sql0 += " where ";
                sql0 += " 	scsubj=" + SQLString.Format(v_scsubj);
                sql0 += " 	and year=" + SQLString.Format(v_scyear);
                sql0 += " 	and scsubjseq=" + SQLString.Format(v_scsubjseq);

                System.out.println("subj_code_sql = " + sql0);

                ls3 = connMgr.executeQuery(sql0);

                insertData = new Hashtable();
                if (!(v_chk.equals("D"))) // ����ó���� �ƴҶ�
                {
                    while (ls3.next()) {
                        pstmt.setString(1, v_chk);
                        pstmt.setString(2, v_rejectkind);
                        pstmt.setString(3, v_rejectedreason);
                        pstmt.setString(4, v_userid);
                        pstmt.setString(5, v_userid);
                        pstmt.setString(6, ls3.getString("subj"));
                        // pstmt.setString(6, v_scsubj);
                        pstmt.setString(7, v_scyear);
                        // pstmt.setString(8, v_scsubjseq);
                        pstmt.setString(8, ls3.getString("subjseq"));
                        System.out.println("subj = " + ls3.getString("subj"));
                        System.out.println("subjseq = " + ls3.getString("subjseq"));
                        isOk = pstmt.executeUpdate();

                        // tz_propose update ���� && �������� && '����'ó���� ��� tz_student�� Record����
                        if ((isOk > 0) && (p_step == 2)) {

                            insertData.clear();
                            insertData.put("connMgr", connMgr);
                            insertData.put("subj", ls3.getString("subj"));
                            // insertData.put("subj", v_scsubj);
                            insertData.put("year", v_scyear);
                            // insertData.put("subjseq", v_scsubjseq);
                            insertData.put("subjseq", ls3.getString("subjseq"));
                            insertData.put("userid", v_userid);
                            insertData.put("isdinsert", "N");
                            insertData.put("chkfirst", "");
                            insertData.put("chkfinal", v_chk);
                            insertData.put("box", box);

                            if (v_chk.equals("Y")) {
                                isOk = propBean.insertStudent(insertData);
                            } else {
                                isOk = propBean.deleteStudent(insertData);
                            }
                            isOk1 = 1;
                        }
                        isOk = isOk * isOk1;

                        if (isOk > 0) {
                            connMgr.commit();
                        } else {
                            connMgr.rollback();
                        }
                    }
                    // tz_propose update ���� && �������� && '����'ó���� ��� tz_student�� Record ��

                    if (p_grcode.equals("N000002")) {
                        v_codegubun = "0072";
                        v_grtype = "KGDI";
                    } else {
                        v_codegubun = "0071";
                        v_grtype = "KOCCA";
                    }
                    sql3 = " Select code From TZ_CODE Where gubun = '" + v_codegubun + "'";
                    ls2 = connMgr.executeQuery(sql3);

                    while (ls2.next()) {
                        sql4 = " Insert Into TZ_MEMBER_MILEAGE ";
                        sql4 += " (userid, grtype, point, usememo,luserid, ldate) ";
                        sql4 += " Values(?, ?, ?, '������û', ?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

                        pstmt3 = connMgr.prepareStatement(sql4);
                        pstmt3.setString(1, v_userid);
                        pstmt3.setString(2, v_grtype);

                        pstmt3.setString(3, ls2.getString("code"));
                        pstmt3.setString(4, v_luserid);

                        System.out.println("ls2.getString('code') = " + ls2.getString("code"));
                        isOk3 = pstmt3.executeUpdate();
                    }

                } else { // /����ó����
                    while (ls3.next()) {
                        insertData.clear();
                        insertData.put("connMgr", connMgr);
                        insertData.put("subj", ls3.getString("subj"));
                        insertData.put("year", v_scyear);
                        insertData.put("subjseq", v_scsubjseq);
                        insertData.put("userid", v_userid);

                        isOk = propBean.deleteStudent(insertData);
                        isOk1 = propBean.deletePropose(insertData);
                        isOk3 = 1;
                    }
                }
            }
            isOk = isOk * isOk1 * isOk3;
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
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
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
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
     * ��������
     * 
     * @param box receive from the form object and session
     * @return DataBox
     */
    public DataBox getSubjInfomat(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        // ArrayList list1 = null;
        ProposeBean probean = new ProposeBean();

        String sql = "";
        String ss_subj = box.getString("s_subjcourse");
        String ss_subjseq = box.getString("s_subjseq");
        String ss_grcode = box.getString("s_grcode");
        String ss_gyear = box.getString("s_gyear");
        String ss_grseq = box.getString("s_grseq");
        String v_year = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subj, ss_subjseq);

        try {
            connMgr = new DBConnectionManager();
            // list1 = new ArrayList();

            sql = " select					\n";
            sql += "	a.subjnm,			\n";
            sql += "	a.subjseq,			\n";
            sql += "	a.edustart,			\n";
            sql += "	a.eduend,			\n";
            sql += "	(select mastercd from tz_mastersubj where subjcourse =" + SQLString.Format(ss_subj) + " and subjseq = " + SQLString.Format(ss_subjseq)
                    + " and year = " + SQLString.Format(v_year) + ") mastercd, \n";
            sql += "	(select masternm from tz_mastercd where mastercd =(select mastercd from tz_mastersubj where subjcourse  =" + SQLString.Format(ss_subj)
                    + " and subjseq = " + SQLString.Format(ss_subjseq) + " and year = " + SQLString.Format(v_year) + ") ) masternm, \n";
            sql += "	(select proposetype from tz_mastercd where mastercd =(select mastercd from tz_mastersubj where subjcourse  ="
                    + SQLString.Format(ss_subj) + " and subjseq = " + SQLString.Format(ss_subjseq) + " and year = " + SQLString.Format(v_year)
                    + ") ) proposetype, \n";
            sql += "	(select isedutarget from tz_mastercd where mastercd =(select mastercd from tz_mastersubj where subjcourse  ="
                    + SQLString.Format(ss_subj) + " and subjseq = " + SQLString.Format(ss_subjseq) + " and year = " + SQLString.Format(v_year)
                    + ") ) isedutarget, \n";
            sql += "	(select count(userid) from tz_edutarget where mastercd =(select mastercd from tz_mastersubj where subjcourse  ="
                    + SQLString.Format(ss_subj) + " and subjseq = " + SQLString.Format(ss_subjseq) + " and year = " + SQLString.Format(v_year)
                    + ") ) educnt \n";
            sql += " from vz_scsubjseq a \n";
            sql += " where		\n";
            sql += " a.subj=" + SQLString.Format(ss_subj) + "\n";
            sql += " and a.subjseq=" + SQLString.Format(ss_subjseq) + "\n";
            sql += " and a.year=" + SQLString.Format(v_year) + "\n";

            System.out.println("getSubjInfomat.sql = " + sql);

            ls1 = connMgr.executeQuery(sql);
            if (ls1 != null) {
                if (ls1.next()) {
                    dbox = ls1.getDataBox();
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
}
