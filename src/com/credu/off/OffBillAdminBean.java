//**********************************************************
//1. 제      목: 수강료결제 관리 (어드민)
//2. 프로그램명: OffBillAdminBean.java
//3. 개      요: 행정서비스
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.12.21
//7. 수      정:
//**********************************************************
package com.credu.off;

import java.util.ArrayList;

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
public class OffBillAdminBean {

	private ConfigSet config;
	private int row;

	public OffBillAdminBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
    수강료 결제관리  리스트
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

		String v_searchtext = box.getString("s_subjsearchkey");

		String  ss_year     = box.getStringDefault("s_year","ALL");         //년도
		String  ss_subjcode   = box.getStringDefault("s_subjcode","ALL");   //과정
		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
		String  ss_paystatus= box.getStringDefault("s_paystatus","ALL");    //결제상태
		String  ss_paymethod= box.getStringDefault("s_paymethod","ALL");    //결제방법
		String  ss_membergubun= box.getStringDefault("s_membergubun","ALL");    //결제방법
		String  ss_action   = box.getString("s_action");

		String  v_startdate     = box.getString("s_startdate");
		String  v_enddate       = box.getString("s_enddate");
		v_startdate		= v_startdate.replace("-", "");
		v_enddate		= v_enddate.replace("-", "");

		String  v_startappdate  = box.getString("s_startappdate");
		String  v_endappdate    = box.getString("s_endappdate");
		v_startappdate	= v_startappdate.replace("-", "");
		v_endappdate	= v_endappdate.replace("-", "");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
		String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

		String sql    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " \nSELECT a.tid, a.userid, a.usernm, b.membergubun, c.codenm, ";
			head_sql += "\n        a.goodname, NVL (TRIM (a.inputdate), a.ldate) inputdate, a.price, ";
			head_sql += "\n        a.resultcode, a.paymethod, buyername, a.pgauthdate, a.cancelyn, ";
			head_sql += "\n CASE ";
			head_sql += "\n    WHEN a.cancelyn = 'Y' ";
			head_sql += "\n       THEN 'R' ";
			head_sql += "\n    WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
			head_sql += "\n       THEN 'Y' ";
			head_sql += "\n    WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
			head_sql += "\n       THEN 'N' ";
			head_sql += "\n END paystatus ";
			body_sql  = "\n   FROM tz_offbillinfo a INNER JOIN tz_member b ON a.userid = b.userid ";
			body_sql += "\n        INNER JOIN tz_code c ON b.membergubun = c.code AND c.gubun = '0029' ";
			body_sql += "\n  WHERE a.resultcode <> '01' ";
			body_sql += "\n    AND a.tid IN (SELECT pp.tid ";
			body_sql += "\n                                           FROM tz_offsubj sb INNER JOIN tz_offsubjseq ss ON sb.subj = ss.subj ";
			body_sql += "\n                                                           inner join tz_offpropose pp on ss.subj = pp.subj and ss.year = pp.year and ss.subjseq = pp.subjseq ";
			body_sql += "\n                                          WHERE (1=1)  ";

			if (!ss_subjcode.equals("ALL")) {
				body_sql += "\n                                          AND sb.subj = "+SQLString.Format(ss_subjcode);
			}
			if (!ss_uclass.equals("ALL")) {
				body_sql += "\n                                          AND sb.upperclass = "+SQLString.Format(ss_uclass);
			}
			if (!ss_mclass.equals("ALL")) {
				body_sql += "\n                                          AND sb.middleclass = "+SQLString.Format(ss_mclass);
			}
			if (!ss_lclass.equals("ALL")) {
				body_sql += "\n                                          AND sb.lowerclass = "+SQLString.Format(ss_lclass);
			}
			if (!ss_year.equals("ALL")) {
				body_sql += "\n                                          AND ss.year = "+SQLString.Format(ss_year);
			}
			if(!v_startappdate.equals("") ){
				body_sql += "\n                                          AND pp.appdate >="+SQLString.Format(v_startappdate);
			}
			if(!v_endappdate.equals("") ){
				body_sql += "\n                                          AND pp.appdate <="+SQLString.Format(v_endappdate);
			}

			body_sql += "\n                                         )  ";

			if (!ss_membergubun.equals("ALL")) {
				body_sql += "\n  AND b.membergubun = "+SQLString.Format(ss_membergubun);
			}

			if(!ss_paymethod.equals("ALL") ){
				body_sql += "\n  AND a.paymethod ="+SQLString.Format(ss_paymethod);
			}

			if (!ss_paystatus.equals("ALL")) {
				body_sql += "\n AND (CASE ";
				body_sql += "\n             WHEN a.cancelyn = 'Y' ";
				body_sql += "\n                THEN 'R' ";
				body_sql += "\n             WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
				body_sql += "\n                THEN 'Y' ";
				body_sql += "\n             WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
				body_sql += "\n                THEN 'N' ";
				body_sql += "\n          END) = "+SQLString.Format(ss_paystatus);
			}

			if(!v_startdate.equals("") ){
				body_sql += "\n  AND a.pgauthdate >="+SQLString.Format(v_startdate);
			}
			if(!v_enddate.equals("") ){
				body_sql += "\n  AND a.pgauthdate <="+SQLString.Format(v_enddate);
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				body_sql +="\n   AND a.goodname like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";
			}

			if(v_orderColumn.equals("")) {
				order_sql += "\n ORDER BY a.ldate desc, a.pgauthdate desc, a.tid desc";
			} else {
				order_sql += "\n ORDER BY " + v_orderColumn + v_orderType + ", a.ldate desc, a.tid desc ";
			}

			sql= head_sql+ body_sql+group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;

			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
			ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.

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
    수강료 결제관리 리스트(Excel)
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectExcelList(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		ArrayList list     = null;
		DataBox dbox = null;

		String v_searchtext = box.getString("s_subjsearchkey");

		String  ss_year     = box.getStringDefault("s_year","ALL");         //년도
		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
		String  ss_paystatus= box.getStringDefault("s_paystatus","ALL");    //결제상태
		String  ss_paymethod= box.getStringDefault("s_paymethod","ALL");    //결제방법
		String  ss_membergubun= box.getStringDefault("s_membergubun","ALL");    //결제방법
		String  ss_action   = box.getString("s_action");

		String  v_startdate     = box.getString("s_startdate");
		String  v_enddate       = box.getString("s_enddate");
		v_startdate		= v_startdate.replace("-", "");
		v_enddate		= v_enddate.replace("-", "");

		String  v_startappdate  = box.getString("s_startappdate");
		String  v_endappdate    = box.getString("s_endappdate");
		v_startappdate	= v_startappdate.replace("-", "");
		v_endappdate	= v_endappdate.replace("-", "");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
		String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

		String sql    	  = "";

		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " SELECT a.tid, a.userid, a.usernm, b.membergubun, c.codenm, ";
			head_sql += "        a.goodname, NVL (TRIM (a.inputdate), a.ldate) inputdate, a.price, ";
			head_sql += "        a.resultcode, a.paymethod, buyername, a.pgauthdate, a.cancelyn, ";
			head_sql += " CASE ";
			head_sql += "    WHEN a.cancelyn = 'Y' ";
			head_sql += "       THEN 'R' ";
			head_sql += "    WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
			head_sql += "       THEN 'Y' ";
			head_sql += "    WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
			head_sql += "       THEN 'N' ";
			head_sql += " END paystatus ";
			body_sql  = "   FROM tz_offbillinfo a INNER JOIN tz_member b ON a.userid = b.userid ";
			body_sql += "        INNER JOIN tz_code c ON b.membergubun = c.code AND c.gubun = '0029' ";
			body_sql += "  WHERE a.resultcode <> '01' ";
			body_sql += "    AND a.tid IN (SELECT pp.tid ";
			body_sql += "                                           FROM tz_offsubj sb INNER JOIN tz_offsubjseq ss ON sb.subj = ss.subj ";
			body_sql += "                                                           inner join tz_offpropose pp on ss.subj = pp.subj and ss.year = pp.year and ss.subjseq = pp.subjseq ";
			body_sql += "                                          WHERE (1=1)  ";

			if (!ss_uclass.equals("ALL")) {
				body_sql += "                                          AND sb.upperclass = "+SQLString.Format(ss_uclass);
			}
			if (!ss_mclass.equals("ALL")) {
				body_sql += "                                          AND sb.middleclass = "+SQLString.Format(ss_mclass);
			}
			if (!ss_lclass.equals("ALL")) {
				body_sql += "                                          AND sb.lowerclass = "+SQLString.Format(ss_lclass);
			}
			if (!ss_year.equals("ALL")) {
				body_sql += "                                          AND ss.year = "+SQLString.Format(ss_year);
			}
			if(!v_startappdate.equals("") ){
				body_sql += "                                          AND pp.appdate >="+SQLString.Format(v_startappdate);
			}
			if(!v_endappdate.equals("") ){
				body_sql += "                                          AND pp.appdate <="+SQLString.Format(v_endappdate);
			}

			body_sql += "                                         )  ";

			if (!ss_membergubun.equals("ALL")) {
				body_sql += "  AND b.membergubun = "+SQLString.Format(ss_membergubun);
			}

			if(!ss_paymethod.equals("ALL") ){
				body_sql += "  AND a.paymethod ="+SQLString.Format(ss_paymethod);
			}

			if (!ss_paystatus.equals("ALL")) {
				body_sql += " AND (CASE ";
				body_sql += "             WHEN a.cancelyn = 'Y' ";
				body_sql += "                THEN 'R' ";
				body_sql += "             WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
				body_sql += "                THEN 'Y' ";
				body_sql += "             WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
				body_sql += "                THEN 'N' ";
				body_sql += "          END) = "+SQLString.Format(ss_paystatus);
			}

			if(!v_startdate.equals("") ){
				body_sql += "  AND a.pgauthdate >="+SQLString.Format(v_startdate);
			}
			if(!v_enddate.equals("") ){
				body_sql += "  AND a.pgauthdate <="+SQLString.Format(v_enddate);
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				body_sql +="   AND a.goodname like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";
			}

			if(v_orderColumn.equals("")) {
				order_sql += " ORDER BY a.ldate desc, a.pgauthdate desc, a.tid desc";
			} else {
				order_sql += " ORDER BY " + v_orderColumn + v_orderType + ", a.ldate desc, a.tid desc ";
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

	/**
	 * 결제 정보 조회
      @param box      receive from the form object and session
      @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList selectPayInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox                = null;
		ListSet ls                  = null;
		ArrayList list              = null;
		String sql                  = "";

		String	v_tid    = box.getString("p_tid");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql += " SELECT a.tid, a.goodname, a.buyername, a.pgauthdate, a.price, c.subjnm, a.paymethod,";
			sql += "        c.biyong, SUM (c.biyong) OVER (PARTITION BY a.tid) total_biyong, ";
			sql += "        (SUM (c.biyong) OVER (PARTITION BY a.tid) - a.price) discount ";
			sql += "   FROM tz_offbillinfo a INNER JOIN tz_offpropose b ON a.tid = b.tid ";
			sql += "        INNER JOIN tz_offsubjseq c ";
			sql += "        ON b.subj = c.subj AND b.YEAR = c.YEAR AND b.subjseq = c.subjseq AND b.seq = c.seq";
			sql += "  WHERE a.tid = "+SQLString.Format(v_tid);

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

	/** 수강료 결제관리 세부정보 정보 가져오기
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public DataBox selectOffBillInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         = null;
		DataBox dbox = null;

		String sql    		= "";
		String v_tid    	= box.getString("p_tid");

		try {
			connMgr = new DBConnectionManager();

			sql =  " SELECT a.tid, c.subj, c.subjnm, c.YEAR, c.subjseq, c.seq, c.biyong, a.price, a.paymethod, ";
			sql += "        a.pgauthdate, a.pgauthtime, a.buyername, a.buyeremail, a.buyertel, ";
			sql += "        a.resultcode, a.cancelyn, a.canceldate, a.canceltime, a.resultmsg, ";
			sql += "        CASE ";
			sql += "           WHEN a.cancelyn = 'Y' ";
			sql += "              THEN 'R' ";
			sql += "           WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
			sql += "              THEN 'Y' ";
			sql += "           WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
			sql += "              THEN 'N' ";
			sql += "        END paystatus ";
			sql += "   FROM tz_offbillinfo a INNER JOIN tz_offpropose b ON a.tid = b.tid ";
			sql += "        INNER JOIN tz_offsubjseq c ";
			sql += "        ON b.subj   = c.subj ";
			sql += "      AND b.YEAR    = c.YEAR ";
			sql += "      AND b.subjseq = c.subjseq ";
			sql += "      AND b.seq     = c.seq ";
			sql += "  WHERE a.tid = "+ SQLString.Format(v_tid);

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
}