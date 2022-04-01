//**********************************************************
//1. ��      ��: ��꼭 ���� (����)
//2. ���α׷���: TaxbillAdminBean.java
//3. ��      ��: ��������
//4. ȯ      ��: JDK 1.5
//5. ��      ��: 1.0
//6. ��      ��: 2009.12.17
//7. ��      ��:
//**********************************************************
package com.credu.polity;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;


@SuppressWarnings("unchecked")
public class TaxbillAdminBean {

	private ConfigSet config;
	private int row;

	public TaxbillAdminBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
    ��꼭  ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectList(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		ArrayList list     = null;
		DataBox dbox = null;

		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
		int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		String v_searchtext = box.getString("p_searchtext");

		String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //�����׷�
		String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //�⵵
		String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //��������
		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
		String  ss_paystatus= box.getStringDefault("s_paystatus","ALL");    //��������

		String  v_startdate     = box.getString("s_startdate");
		String  v_enddate       = box.getString("s_enddate");
		v_startdate		= v_startdate.replace("-", "");
		v_enddate		= v_enddate.replace("-", "");


		String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
		String  v_orderType     = box.getString("p_orderType");           		//������ ����

		String sql    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " SELECT   a.grcode, a.gyear, a.grseq, c.grseqnm, a.tid, b.goodname, ";
			head_sql += "          (SELECT MIN (edustart) ";
			head_sql += "             FROM tz_subjseq ";
			head_sql += "            WHERE grcode = a.grcode ";
			head_sql += "              AND gyear = a.gyear ";
			head_sql += "              AND grseq = a.grseq) edustart, ";
			head_sql += "          (SELECT MAX (eduend) ";
			head_sql += "             FROM tz_subjseq ";
			head_sql += "            WHERE grcode = a.grcode ";
			head_sql += "              AND gyear = a.gyear ";
			head_sql += "              AND grseq = a.grseq) eduend, ";
			head_sql += "          a.studentcnt, a.price, ";
			head_sql += "          CASE ";
			head_sql += "             WHEN b.cancelyn = 'Y' ";
			head_sql += "                THEN 'R' ";
			head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
			head_sql += "                THEN 'Y' ";
			head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
			head_sql += "                THEN 'N' ";
			head_sql += "          END paystatus, ";
			head_sql += "          b.userid, (SELECT NAME ";
			head_sql += "                       FROM tz_member ";
			head_sql += "                      WHERE userid = b.userid) NAME, b.pgauthdate ";
			body_sql  = "     FROM tz_taxbill a INNER JOIN tz_billinfo b ";
			body_sql += "          ON a.grcode = b.grcode AND a.tid = b.tid ";
			body_sql += "          INNER JOIN tz_grseq c ";
			body_sql += "          ON a.grcode = c.grcode AND a.gyear = c.gyear AND a.grseq = c.grseq ";
			body_sql += "    WHERE b.resultcode <> '01' ";
			body_sql += "      AND (a.grcode, a.gyear, a.grseq) IN (SELECT ss.grcode, ss.gyear, ss.grseq ";
			body_sql += "                                             FROM tz_subj sb INNER JOIN tz_subjseq ss ";
			body_sql += "                                                  ON sb.subj = ss.subj ";
			body_sql += "                                            WHERE (1=1) ";

			if (!ss_uclass.equals("ALL")) {
				body_sql += "                                          AND sb.upperclass = "+SQLString.Format(ss_uclass);
			}
			if (!ss_mclass.equals("ALL")) {
				body_sql += "                                          AND sb.middleclass = "+SQLString.Format(ss_mclass);
			}
			if (!ss_lclass.equals("ALL")) {
				body_sql += "                                          AND sb.lowerclass = "+SQLString.Format(ss_lclass);
			}

			body_sql += "                                           ) ";

			if (!ss_grcode.equals("ALL")) {
				body_sql += " AND a.grcode = "+SQLString.Format(ss_grcode);
			}
			if (!ss_gyear.equals("ALL")) {
				body_sql += " AND a.gyear = "+SQLString.Format(ss_gyear);
			}
			if (!ss_grseq.equals("ALL")) {
				body_sql += " AND a.grseq = "+SQLString.Format(ss_grseq);
			}
			if (!ss_paystatus.equals("ALL")) {
				body_sql += " AND (CASE ";
				body_sql += "             WHEN b.cancelyn = 'Y' ";
				body_sql += "                THEN 'R' ";
				body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
				body_sql += "                THEN 'Y' ";
				body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
				body_sql += "                THEN 'N' ";
				body_sql += "          END) = "+SQLString.Format(ss_paystatus);
			}

			if(!v_startdate.equals("") ){
				body_sql += "  AND b.pgauthdate >="+SQLString.Format(v_startdate);
			}
			if(!v_enddate.equals("") ){
				body_sql += "  AND b.pgauthdate <="+SQLString.Format(v_enddate);
			}

			if ( !v_searchtext.equals("")) {      //    �˻�� ������
				body_sql +=" and b.goodname like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";
			}

			if(v_orderColumn.equals("")) {
				order_sql += " order by ldate desc, b.pgauthdate desc, a.tid desc";
			} else {
				order_sql += " order by " + v_orderColumn + v_orderType + ", ldate desc, a.tid desc ";
			}

			sql= head_sql+ body_sql+group_sql+ order_sql;

			//System.out.println("#############"+sql);

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;

			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     ��ü row ���� ��ȯ�Ѵ�
			ls.setPageSize(v_pagesize);             		//  �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(row));
				dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);

			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}
	public ArrayList selectSearchTaxBillList(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		ArrayList list     = null;

		StringBuffer sqlBuffer    	  = new StringBuffer();
		String sql = null;

		try {
			connMgr = new DBConnectionManager();
			list = null;

			sqlBuffer.append("select\n");
			sqlBuffer.append("a.GRCODE,a.GYEAR,a.GRSEQ,B.GRSEQNM,a.NOTE,C.GOODNAME,\n");
			sqlBuffer.append("a.STUDENTCNT-(SELECT COUNT(USERID) FROM TZ_PROPOSE WHERE TID = A.TID) usecnt,(SELECT COUNT(USERID) FROM TZ_PROPOSE WHERE TID = A.TID) || '/' || a.STUDENTCNT PROPOSECNT,\n");
			sqlBuffer.append("a.STUDENTCNT,a.TID,a.PRICE, C.USERNM\n");
			sqlBuffer.append("from tz_taxbill a, tz_GRSEQ B, TZ_BILLINFO C\n");
			sqlBuffer.append("WHERE A.GRCODE = B.GRCODE\n");
			sqlBuffer.append("AND A.GYEAR = B.GYEAR\n");
			sqlBuffer.append("AND A.GRSEQ = B.GRSEQ\n");
			sqlBuffer.append("AND A.TID = C.TID\n");
			sqlBuffer.append("AND A.GRCODE = ':s_grcode'\n");
			sqlBuffer.append("AND A.GYEAR = ':s_gyear'\n");
			sqlBuffer.append("AND A.GRSEQ = ':s_grseq'\n");
			sqlBuffer.append("AND C.GOODNAME like '%'||':p_searchtext'||'%'\n");
			sql = connMgr.replaceParam(sqlBuffer, box);
			ls = connMgr.executeQuery(sql);

			list = ls.getAllDataList();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception(ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}


	/**
    ��꼭 ����Ʈ(Excel)
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectExcelList(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		ArrayList list     = null;
		DataBox dbox = null;

		String v_searchtext = box.getString("p_searchtext");

		String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //�����׷�
		String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //�⵵
		String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //��������
		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
		String  ss_paystatus= box.getStringDefault("s_paystatus","ALL");    //��������

		String  v_startdate     = box.getString("s_startdate");
		String  v_enddate       = box.getString("s_enddate");
		v_startdate		= v_startdate.replace("-", "");
		v_enddate		= v_enddate.replace("-", "");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
		String  v_orderType     = box.getString("p_orderType");           		//������ ����

		String sql    	  = "";

		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " SELECT   a.grcode, a.gyear, a.grseq, c.grseqnm, a.tid, b.goodname, ";
			head_sql += "          (SELECT MIN (edustart) ";
			head_sql += "             FROM tz_subjseq ";
			head_sql += "            WHERE grcode = a.grcode ";
			head_sql += "              AND gyear = a.gyear ";
			head_sql += "              AND grseq = a.grseq) edustart, ";
			head_sql += "          (SELECT MAX (eduend) ";
			head_sql += "             FROM tz_subjseq ";
			head_sql += "            WHERE grcode = a.grcode ";
			head_sql += "              AND gyear = a.gyear ";
			head_sql += "              AND grseq = a.grseq) eduend, ";
			head_sql += "          a.studentcnt, a.price, ";
			head_sql += "          CASE ";
			head_sql += "             WHEN b.cancelyn = 'Y' ";
			head_sql += "                THEN 'R' ";
			head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
			head_sql += "                THEN 'Y' ";
			head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
			head_sql += "                THEN 'N' ";
			head_sql += "          END paystatus, ";
			head_sql += "          b.userid, (SELECT NAME ";
			head_sql += "                       FROM tz_member ";
			head_sql += "                      WHERE userid = b.userid) NAME, b.pgauthdate ";
			body_sql  = "     FROM tz_taxbill a INNER JOIN tz_billinfo b ";
			body_sql += "          ON a.grcode = b.grcode AND a.tid = b.tid ";
			body_sql += "          INNER JOIN tz_grseq c ";
			body_sql += "          ON a.grcode = c.grcode AND a.gyear = c.gyear AND a.grseq = c.grseq ";
			body_sql += "    WHERE b.resultcode <> '01' ";
			body_sql += "      AND (a.grcode, a.gyear, a.grseq) IN (SELECT ss.grcode, ss.gyear, ss.grseq ";
			body_sql += "                                             FROM tz_subj sb INNER JOIN tz_subjseq ss ";
			body_sql += "                                                  ON sb.subj = ss.subj ";
			body_sql += "                                            WHERE (1=1) ";

			if (!ss_uclass.equals("ALL")) {
				body_sql += "                                          AND sb.upperclass = "+SQLString.Format(ss_uclass);
			}
			if (!ss_mclass.equals("ALL")) {
				body_sql += "                                          AND sb.middleclass = "+SQLString.Format(ss_mclass);
			}
			if (!ss_lclass.equals("ALL")) {
				body_sql += "                                          AND sb.lowerclass = "+SQLString.Format(ss_lclass);
			}

			body_sql += "                                           ) ";

			if (!ss_grcode.equals("ALL")) {
				body_sql += " AND a.grcode = "+SQLString.Format(ss_grcode);
			}
			if (!ss_gyear.equals("ALL")) {
				body_sql += " AND a.gyear = "+SQLString.Format(ss_gyear);
			}
			if (!ss_grseq.equals("ALL")) {
				body_sql += " AND a.grseq = "+SQLString.Format(ss_grseq);
			}
			if (!ss_paystatus.equals("ALL")) {
				body_sql += " AND (CASE ";
				body_sql += "             WHEN b.cancelyn = 'Y' ";
				body_sql += "                THEN 'R' ";
				body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
				body_sql += "                THEN 'Y' ";
				body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
				body_sql += "                THEN 'N' ";
				body_sql += "          END) = "+SQLString.Format(ss_paystatus);
			}

			if(!v_startdate.equals("") ){
				body_sql += "  AND b.pgauthdate >="+SQLString.Format(v_startdate);
			}
			if(!v_enddate.equals("") ){
				body_sql += "  AND b.pgauthdate <="+SQLString.Format(v_enddate);
			}

			if ( !v_searchtext.equals("")) {      //    �˻�� ������
				body_sql +=" and b.goodname like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";
			}

			if(v_orderColumn.equals("")) {
				order_sql += " order by ldate desc, b.pgauthdate desc, a.tid desc";
			} else {
				order_sql += " order by " + v_orderColumn + v_orderType + ", ldate desc, a.tid desc ";
			}

			sql= head_sql+ body_sql+group_sql+ order_sql;

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);

			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/** ��꼭 ������������� ��ϵ� �׷��� ���� ��������
     @param box      receive from the form object and session
     @return ArrayList
	 */
	public DataBox selectGroupInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		DataBox dbox = null;

		String sql    		= "";
		String v_grcode 	= box.getString("p_grcode");
		String v_gyear	 	= box.getString("p_gyear");
		String v_grseq	 	= box.getString("p_grseq");

		try {
			connMgr = new DBConnectionManager();

			sql += " SELECT a.grcode, a.grcodenm, b.gyear, b.grseq, b.grseqnm ";
			sql += "   FROM tz_grcode a INNER JOIN tz_grseq b ON a.grcode = b.grcode ";
			sql += "  WHERE b.grcode = " + SQLString.Format(v_grcode) + " AND b.gyear = " + SQLString.Format(v_gyear) + " AND b.grseq = " + SQLString.Format(v_grseq) + " ";

			System.out.println("sql = " + sql);

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}

	/**
	 * �߱޴���� ��ȸ
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectSearchMember(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		DataBox dbox = null;
		String sql       = "";
		StringBuffer headSql   = new StringBuffer();
		StringBuffer bodySql   = new StringBuffer();
		String orderSql  = "";
		String countSql  = "";

		String v_search     = box.getString("p_mode1");
		String v_searchtext = box.getString("p_mode2");
		int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
		//int v_pageno = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			headSql.append(" select membergubun, userid, resno, name, email, hometel, handphone, \n");
			headSql.append("        comptel, comp, comptext, post1, post2, addr, addr2           \n");
			bodySql.append(" from TZ_MEMBER                                                      \n");
			bodySql.append(" where 1=1 ");

			if ( !v_searchtext.equals("")) {                            //    �˻�� ������
				if (v_search.equals("userid")) {                            //    ID�� �˻��Ҷ�
					bodySql.append(" and userid like   " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				} else if (v_search.equals("name")) {                   //    �̸����� �˻��Ҷ�
					bodySql.append(" and name like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}
			}
			orderSql = "order by comp asc, name asc";

			sql = headSql.toString() + bodySql.toString() + orderSql;
			System.out.println("sql sql =-===>>> "+sql);
			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql;

			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     ��ü row ���� ��ȯ�Ѵ�

			ls.setPageSize(10);             		//  �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(row));
				dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
	 * �������� ���� ��ȸ
      @param box      receive from the form object and session
      @return ArrayList
	 */
	public ArrayList selectGrInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox                = null;
		ListSet ls                  = null;
		ArrayList list              = null;
		String sql                  = "";

		String	v_tid    = box.getString("p_tid");
		String	v_grcode = box.getString("p_grcode");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql += " select b.grseqnm, a.studentcnt, a.price ";
			sql += "   from tz_taxbill a inner join tz_grseq b on a.grcode = b.grcode and a.gyear = b.gyear and a.grseq = b.grseq ";
			sql += "  where a.tid     = "+SQLString.Format(v_tid);
			sql += "    and a.grcode  = "+SQLString.Format(v_grcode);

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;

	}

	/**
	 * �԰��ο� ��ȸ
      @param box      receive from the form object and session
      @return ArrayList
	 */
	public ArrayList selectSearchStudent(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox                = null;
		ListSet ls                  = null;
		ArrayList list              = null;
		String sql                  = "";

		String	v_tid    = box.getString("p_tid");
		String	v_grcode = box.getString("p_grcode");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql += " select b.userid, c.name, c.comptext, d.subjnm, d.biyong, a.appdate, e.grseqnm, f.studentcnt, f.price ";
			sql += "   from tz_propose a inner join tz_student b on a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq ";
			sql += "                     inner join tz_member c on a.userid = c.userid ";
			sql += "                     inner join tz_subjseq d on a.subj = d.subj and a.year = d.year and a.subjseq = d.subjseq ";
			sql += "                     inner join tz_grseq e on d.grcode = e.grcode and d.gyear = e.gyear and d.grseq = e.grseq ";
			sql += "                     inner join tz_taxbill f on a.tid = f.tid ";
			sql += "  where a.tid     = "+SQLString.Format(v_tid);
			sql += "    and f.grcode  = "+SQLString.Format(v_grcode);


			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;

	}

	public int insert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet ls = null;
		String sql         = "";
		int isOk            = 0;

		String v_userid = box.getSession("userid");
		String v_name   = box.getSession("name");

		String v_grcode     = box.getString("p_grcode");
		String v_gyear      = box.getString("p_gyear");
		String v_grseq      = box.getString("p_grseq");
		String v_goodname   = box.getString("p_goodname");
		int    v_studentcnt = box.getInt("p_studentcnt");
		double v_price      = box.getDouble("p_price");
		String v_note       = box.getString("p_note");
		String v_taxuserid  = box.getString("p_userid");

		//tid : ���翬���Ͻú���
		GregorianCalendar	calendar	= new GregorianCalendar();

		String	sYear	= Integer.toString(calendar.get(Calendar.YEAR));
		String	sMonth	= Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String	sDate	= Integer.toString(calendar.get(Calendar.DATE));

		if (sMonth.length() < 2)	sMonth	= "0" + sMonth;
		if (sDate.length()  < 2)	sDate	= "0" + sDate;

		String	sHour	= Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		String	sMinute	= Integer.toString(calendar.get(Calendar.MINUTE));
		String	sSecond	= Integer.toString(calendar.get(Calendar.SECOND));

		if (sHour.length()   < 2)	sHour	= "0" + sHour;
		if (sMinute.length() < 2)	sMinute	= "0" + sMinute;
		if (sSecond.length() < 2)	sSecond	= "0" + sSecond;

		String v_tid = sYear + sMonth + sDate + sHour + sMinute + sSecond;
		String v_mid = sYear + sMonth + sDate;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			//��꼭 ���̺�
			sql  = " INSERT INTO tz_taxbill ";
			sql += "  (grcode, tid, gyear, grseq, studentcnt, price, note, luserid, ";
			sql += "   ldate ";
			sql += "  ) ";
			sql += " VALUES (?,?,?,?,?,?,?,?, ";
			sql += "   TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
			sql += "  ) ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1,  v_grcode);
			pstmt.setString(2,  v_tid);
			pstmt.setString(3,  v_gyear);
			pstmt.setString(4,  v_grseq);
			pstmt.setInt(5,  v_studentcnt);
			pstmt.setDouble(6,  v_price);
			pstmt.setString(7,  v_note);
			pstmt.setString(8,  v_userid);
			isOk = pstmt.executeUpdate();

			//billinfo ���̺�
			sql  = " INSERT INTO tz_billinfo ";
			sql += "          (grcode, tid, gubun, resultcode, resultmsg, paymethod, mid, ";
			sql += "           goodname, userid, usernm, price, point, buyername, buyertel, ";
			sql += "           buyeremail, resulterrcode, price1, price2, cancelyn, inputname, ";
			sql += "           inputdate, luserid, ldate, receive, phone, post1, post2, addr1, ";
			sql += "           addr2, multiyn, agencyyn) ";
			sql += " SELECT ?, ?, 'N', '99', '[TaxBill|�̰���]', ";
			sql += "        'TaxBill', ?, ?, userid, NAME, ?, 0, NAME, ";
			sql += "        handphone, email, '00', 0, 0, 'N', ?, ";
			sql += "        TO_CHAR (SYSDATE, 'yyyymmdd'), ?, ";
			sql += "        TO_CHAR (SYSDATE, 'yyyymmddhh24miss'), NAME, handphone, post1, ";
			sql += "        post2, addr, addr2, 'N', 'N' ";
			sql += "   FROM tz_member ";
			sql += "  WHERE trim(userid) = ? ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString (1,  v_grcode);
			pstmt.setString (2,  v_tid);
			pstmt.setString (3,  v_mid);
			pstmt.setString (4,  v_goodname);
			pstmt.setDouble (5,  v_price);
			pstmt.setString (6,  v_name);
			pstmt.setString (7,  v_userid);
			pstmt.setString (8,  v_taxuserid);

			isOk = pstmt.executeUpdate();

			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+ sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/** ��꼭 �������� ���� ��������
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public DataBox selectTaxBillInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		DataBox dbox = null;

		String sql    		= "";
		String v_grcode 	= box.getString("p_grcode");
		String v_tid    	= box.getString("p_tid");

		try {
			connMgr = new DBConnectionManager();

			sql += " SELECT   a.grcode, a.gyear, a.grseq, c.grseqnm, a.tid, b.goodname, ";
			sql += "          (SELECT MIN (edustart) ";
			sql += "             FROM tz_subjseq ";
			sql += "            WHERE grcode = a.grcode ";
			sql += "              AND gyear = a.gyear ";
			sql += "              AND grseq = a.grseq) edustart, ";
			sql += "          (SELECT MAX (eduend) ";
			sql += "             FROM tz_subjseq ";
			sql += "            WHERE grcode = a.grcode ";
			sql += "              AND gyear = a.gyear ";
			sql += "              AND grseq = a.grseq) eduend, ";
			sql += "          a.studentcnt, a.price, ";
			sql += "          a.note, ";
			sql += "          CASE ";
			sql += "             WHEN b.cancelyn = 'Y' ";
			sql += "                THEN 'R' ";
			sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
			sql += "                THEN 'Y' ";
			sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
			sql += "                THEN 'N' ";
			sql += "          END paystatus, ";
			sql += "          b.userid, (SELECT NAME ";
			sql += "                       FROM tz_member ";
			sql += "                      WHERE userid = b.userid) NAME, b.pgauthdate, b.pgauthtime, ";
			sql += "          b.cancelyn, b.canceldate, b.canceltime,";
			sql += "          b.buyeremail, b.buyertel, b.paymethod ";
			sql += "     FROM tz_taxbill a INNER JOIN tz_billinfo b ";
			sql += "          ON a.grcode = b.grcode AND a.tid = b.tid ";
			sql += "          INNER JOIN tz_grseq c ";
			sql += "          ON a.grcode = c.grcode AND a.gyear = c.gyear AND a.grseq = c.grseq ";
			sql += "    WHERE a.tid = "+ SQLString.Format(v_tid);
			sql += "      and a.grcode = "+ SQLString.Format(v_grcode);

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}

	public int update(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		ListSet ls = null;
		String sql         = "";
		int isOk            = 0;

		String v_userid = box.getSession("userid");

		String v_grcode     = box.getString("p_grcode");
		String v_tid        = box.getString("p_tid");
		String v_paystatus  = box.getString("p_paystatus");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			//��꼭 ���̺�

			sql  = " UPDATE tz_billinfo ";
			sql += "    SET ";

			if ("N".equals(v_paystatus)) { //�̰���
				sql += "        resultcode = '99', ";
				sql += "        resultmsg = '[TaxBill|�̰���]', ";
				sql += "        pgauthdate = NULL, ";
				sql += "        pgauthtime = NULL, ";
				sql += "        cancelyn = 'N', ";
				sql += "        cancelresult = NULL, ";
				sql += "        canceldate = NULL, ";
				sql += "        canceltime = NULL, ";
			} else if ("Y".equals(v_paystatus)) {
				sql += "        resultcode = '00', ";
				sql += "        resultmsg = '[TaxBill|����]', ";
				sql += "        pgauthdate = TO_CHAR (SYSDATE, 'yyyymmdd'), ";
				sql += "        pgauthtime = TO_CHAR (SYSDATE, 'hh24miss'), ";
				sql += "        cancelyn = 'N', ";
				sql += "        cancelresult = NULL, ";
				sql += "        canceldate = NULL, ";
				sql += "        canceltime = NULL, ";
			} else if ("R".equals(v_paystatus)) {
				sql += "        cancelyn = 'Y', ";
				sql += "        cancelresult = '00', ";
				sql += "        canceldate = TO_CHAR (SYSDATE, 'yyyymmdd'), ";
				sql += "        canceltime = TO_CHAR (SYSDATE, 'hh24miss'), ";
			}

			sql += "        ldate = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'), ";
			sql += "        luserid = ? ";
			sql += "  WHERE grcode = ?  ";
			sql += "    AND tid    = ? ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1,  v_userid);
			pstmt.setString(2,  v_grcode);
			pstmt.setString(3,  v_tid);

			isOk = pstmt.executeUpdate();

			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+ sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

}