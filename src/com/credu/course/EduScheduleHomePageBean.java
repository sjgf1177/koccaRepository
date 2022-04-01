//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.course;

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

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class EduScheduleHomePageBean {

	private ConfigSet config;
	private int row;


	public EduScheduleHomePageBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * 온라인 월별 교육 일정
	 * @param box          receive from the form object and session
	 * @return ArrayList    온라인 월별 교육 일정
	 * @throws Exception
	 */
	public ArrayList schlMonthPlanList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null,ls2=null;
		ArrayList list = null;
		String sql  = "";
		StringBuffer headSql = new StringBuffer();
		StringBuffer bodySql = new StringBuffer();
		StringBuffer bodySql2 = new StringBuffer();
		String orderSql = "";
		String countSql = "";
		DataBox dbox = null;

		int   	v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
		int    	v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");



		String	v_month		= box.getString("p_month");
		String  v_year		= box.getString("p_year");

		GregorianCalendar   calendar    = new GregorianCalendar();
		if(v_year.equals("")){
			v_year	= Integer.toString(calendar.get(Calendar.YEAR));
		}

		if(v_month.equals("")){
			v_month = calendar.get(Calendar.MONTH) + 1 < 10 ? "0"+(calendar.get(Calendar.MONTH)+1) : Integer.toString(calendar.get(Calendar.MONTH)+1);
		}

		String  ss_grcode   = box.getSession("tem_grcode");  		//교육그룹
		String  ss_gyear    = v_year;     		                    //년도

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT  X.*\n");
			headSql.append(" FROM    (\n");
			headSql.append(" SELECT  A.SUBJ, B.SUBJNM, A.EDUSTART, A.EDUEND, A.PROPSTART\n");
			headSql.append("         , A.PROPEND, A.BIYONG, A.STUDENTLIMIT\n");
			headSql.append("         , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y'\n");
			headSql.append("                ELSE 'N'\n");
			headSql.append("         END STATUS\n");
			headSql.append("         , A.SUBJSEQ, A.YEAR, C.UPPERCLASS, C.CLASSNAME\n");
			bodySql.append(" FROM    TZ_SUBJSEQ A, TZ_SUBJ B, TZ_SUBJATT C \n");
			bodySql.append(" WHERE   A.SUBJ = B.SUBJ\n");
			//bodySql.append(" AND     A.YEAR = ").append(StringManager.makeSQL(v_year));
			bodySql.append(" AND     SUBSTR(A.EDUSTART, 5, 2) = ").append(StringManager.makeSQL(v_month));
			bodySql.append(" AND     B.UPPERCLASS = C.UPPERCLASS(+)\n");
			bodySql.append(" AND     C.MIDDLECLASS(+) = '000'\n");

            if(!box.getString("s_subjnm").equals(""))
                bodySql.append(" AND     A.SUBJNM like '%"+box.getString("s_subjnm")+"%' \n");

			bodySql2.append("         )    X\n");
			bodySql2.append("ORDER BY  STATUS DESC, SUBJ ASC, SUBJSEQ ASC, EDUSTART ASC\n");

			if (!ss_grcode.equals("ALL")) {			//교육그룹
				bodySql.append(" and A.grcode = ").append(SQLString.Format(ss_grcode));
			}
			if (!ss_gyear.equals("ALL") ) {
				bodySql.append(" and substring(A.edustart, 1, 4) = ").append(SQLString.Format(ss_gyear));
			}

			sql = headSql.toString() + bodySql.toString()+ bodySql2.toString();

			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString();

			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//  전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       					//  전체 페이지 수를 반환한다
			ls.setPageSize(v_pagesize);             							//  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);   					//  현재페이지번호를 세팅한다.
			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(10));
				dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
	 * 온라인 연간 교육 일정
	 * @param box          receive from the form object and session
	 * @return ArrayList    온라인 월별 교육 일정
	 * @throws Exception
	 */
	public ArrayList schlYearPlanList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null,ls2=null;
		ArrayList list = null;
		String sql  = "";
		StringBuffer headSql = new StringBuffer();
		StringBuffer bodySql = new StringBuffer();
		StringBuffer bodySql2 = new StringBuffer();
		String orderSql = "";
		String countSql = "";
		DataBox dbox = null;

		//int   	v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
		//int    	v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		String  v_year		= box.getString("p_year");
		String  v_upperclass	= box.getStringDefault("p_upperclass", "C01");
		String  v_middleclass 	= box.getStringDefault("p_middleclass", "ALL");

		if(v_year.equals("")){
			GregorianCalendar   calendar    = new GregorianCalendar();
			v_year	= Integer.toString(calendar.get(Calendar.YEAR));
		}

		String  ss_grcode   = box.getSession("tem_grcode");  		//교육그룹
		String  ss_gyear    = v_year;     		                    //년도

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT  X.*\n ");
			headSql.append("         , COUNT(*) OVER (PARTITION BY X.SUBJ) SUBJ_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_01) OVER (PARTITION BY X.MONTH_01, X.SUBJ) MONTH_01_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_02) OVER (PARTITION BY X.MONTH_02, X.SUBJ) MONTH_02_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_03) OVER (PARTITION BY X.MONTH_03, X.SUBJ) MONTH_03_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_04) OVER (PARTITION BY X.MONTH_04, X.SUBJ) MONTH_04_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_05) OVER (PARTITION BY X.MONTH_05, X.SUBJ) MONTH_05_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_06) OVER (PARTITION BY X.MONTH_06, X.SUBJ) MONTH_06_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_07) OVER (PARTITION BY X.MONTH_07, X.SUBJ) MONTH_07_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_08) OVER (PARTITION BY X.MONTH_08, X.SUBJ) MONTH_08_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_09) OVER (PARTITION BY X.MONTH_09, X.SUBJ) MONTH_09_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_10) OVER (PARTITION BY X.MONTH_10, X.SUBJ) MONTH_10_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_11) OVER (PARTITION BY X.MONTH_11, X.SUBJ) MONTH_11_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_12) OVER (PARTITION BY X.MONTH_12, X.SUBJ) MONTH_12_CNT\n ");
			headSql.append(" FROM    (\n ");
			headSql.append("         SELECT  A.SUBJ, B.SUBJNM, A.EDUSTART, A.EDUEND, A.PROPSTART\n ");
			headSql.append("                 , A.PROPEND, A.BIYONG, A.STUDENTLIMIT\n ");
			headSql.append("                 , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y'\n ");
			headSql.append("                        ELSE 'N'\n ");
			headSql.append("                 END STATUS\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '01', '01', '') MONTH_01\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '02', '02', '') MONTH_02\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '03', '03', '') MONTH_03\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '04', '04', '') MONTH_04\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '05', '05', '') MONTH_05\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '06', '06', '') MONTH_06\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '07', '07', '') MONTH_07\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '08', '08', '') MONTH_08\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '09', '09', '') MONTH_09\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '10', '10', '') MONTH_10\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '11', '11', '') MONTH_11\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '12', '12', '') MONTH_12\n ");
			headSql.append("                 , A.SUBJSEQ, A.YEAR, C.UPPERCLASS, C.CLASSNAME\n ");
			headSql.append("                 , SUBSTR(A.EDUSTART, 5, 2) MONTH\n ");
			bodySql.append("         FROM    TZ_SUBJSEQ A, TZ_SUBJ B, TZ_SUBJATT C\n ");
			bodySql.append("         WHERE   A.SUBJ = B.SUBJ\n ");
			bodySql.append(" AND     A.GRCODE = '").append(ss_grcode).append("'\n ");
			bodySql.append(" AND     A.GYEAR = '").append(ss_gyear).append("'\n ");
			bodySql.append(" AND     B.UPPERCLASS = C.UPPERCLASS(+)\n ");
			bodySql.append(" AND     C.MIDDLECLASS(+) = '000'\n ");
			bodySql.append(" AND     B.UPPERCLASS(+) = '").append(v_upperclass).append("'\n ");
			bodySql.append(" AND     B.ISUSE = 'Y' \n");
			bodySql2.append("         ORDER BY  SUBJ ASC, SUBJSEQ ASC, EDUSTART ASC\n ");
			bodySql2.append("         )    X\n ");

			if (!v_middleclass.equals("ALL") ) {
				bodySql.append(" AND     B.MIDDLECLASS = ").append(SQLString.Format(v_middleclass));
			}

			countSql= "SELECT COUNT(*) FROM (SELECT B.SUBJ "+ bodySql.toString() + "GROUP BY B.SUBJ) ";

			ls = connMgr.executeQuery(countSql);
			ls.next();

			int count = ls.getInt(1);

			sql = headSql.toString() + bodySql.toString() + bodySql2.toString();

			ls = connMgr.executeQuery(sql);



			//
			//int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//  전체 row 수를 반환한다
			//int total_page_count = ls.getTotalPage();       					//  전체 페이지 수를 반환한다
			//ls.setPageSize(v_pagesize);             							//  페이지당 row 갯수를 세팅한다
			//ls.setCurrentPage(v_pageno, total_row_count);   					//  현재페이지번호를 세팅한다.


			while (ls.next()) {
				dbox = ls.getDataBox();


				//dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				//dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				//dbox.put("d_rowcount", new Integer(10));
				//dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}

			if(list.size() > 0)  ((DataBox)list.get(0)).put("d_total_subj", count);
		}
		catch (Exception ex) {
			System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
	 * 오프라인 월별 교육 일정
	 * @param box          receive from the form object and session
	 * @return ArrayList    오프라인 월별 교육 일정
	 * @throws Exception
	 */
	public ArrayList offSchlMonthPlanList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null,ls2=null;
		ArrayList list = null;
		String sql  = "";
		StringBuffer headSql = new StringBuffer();
		StringBuffer bodySql = new StringBuffer();
		StringBuffer bodySql2 = new StringBuffer();
		String orderSql = "";
		String countSql = "";
		DataBox dbox = null;

		int   	v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
		int    	v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		String	v_month		= box.getString("p_month");
		String  v_year		= box.getString("p_year");

		GregorianCalendar   calendar    = new GregorianCalendar();
		if(v_year.equals("")){
			v_year	= Integer.toString(calendar.get(Calendar.YEAR));
		}

		if(v_month.equals("")){
			v_month = calendar.get(Calendar.MONTH) + 1 < 10 ? "0"+(calendar.get(Calendar.MONTH)+1) : Integer.toString(calendar.get(Calendar.MONTH)+1);
		}

		//String  ss_grcode   = box.getSession("tem_grcode");  		//교육그룹
		String  ss_gyear    = v_year;     		                    //년도

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT  X.*\n ");
			headSql.append(" FROM    (\n ");
			headSql.append(" SELECT  A.SUBJ, B.SUBJNM, A.EDUSTART, A.EDUEND, A.PROPSTART\n");
			headSql.append("         , A.PROPEND, A.BIYONG, A.STUDENTLIMIT\n");
			headSql.append("         , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y'\n");
			headSql.append("                ELSE 'N'\n");
			headSql.append("         END STATUS\n");
			headSql.append("         , A.SUBJSEQ, A.YEAR, C.UPPERCLASS, C.CLASSNAME, A.SEQ\n");
			bodySql.append(" FROM    TZ_OFFSUBJSEQ A, TZ_OFFSUBJ B, TZ_OFFSUBJATT C\n");
			bodySql.append(" WHERE   A.SUBJ = B.SUBJ\n");
			//bodySql.append(" AND     A.YEAR = ").append(StringManager.makeSQL(v_year));
			bodySql.append(" AND     SUBSTR(A.EDUSTART, 5, 2) = ").append(StringManager.makeSQL(v_month));
			bodySql.append(" AND     B.UPPERCLASS = C.UPPERCLASS(+)\n");
			bodySql.append(" AND     C.MIDDLECLASS(+) = '000'\n");
			bodySql2.append("         )    X\n ");
			bodySql2.append("ORDER BY  STATUS DESC, SUBJ ASC, SUBJSEQ ASC, EDUSTART ASC\n ");

			//if (!ss_grcode.equals("ALL")) {			//교육그룹
			//	bodySql.append(" and A.grcode = ").append(SQLString.Format(ss_grcode));
			//}
			if (!ss_gyear.equals("ALL") ) {
				bodySql.append(" and A.year = ").append(SQLString.Format(ss_gyear));
			}

			sql = headSql.toString() + bodySql.toString()+ bodySql2.toString();

			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString();

			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//  전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       					//  전체 페이지 수를 반환한다
			ls.setPageSize(v_pagesize);             							//  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);   					//  현재페이지번호를 세팅한다.
			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount", new Integer(10));
				dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
	 * 오프라인 연간 교육 일정
	 * @param box          receive from the form object and session
	 * @return ArrayList    오프라인 월별 교육 일정
	 * @throws Exception
	 */
	public ArrayList offSchlYearPlanList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null,ls2=null;
		ArrayList list = null;
		String sql  = "";
		StringBuffer headSql = new StringBuffer();
		StringBuffer bodySql = new StringBuffer();
		StringBuffer bodySql2 = new StringBuffer();
		String orderSql = "";
		String countSql = "";
		DataBox dbox = null;

		//int   	v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
		//int    	v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		String  v_year		    = box.getString("p_year");
		String  v_upperclass	= box.getStringDefault("p_upperclass", "S01");
		String  v_middleclass 	= box.getStringDefault("p_middleclass", "ALL");

		if(v_year.equals("")){
			GregorianCalendar   calendar    = new GregorianCalendar();
			v_year	= Integer.toString(calendar.get(Calendar.YEAR));
		}

		//String  ss_grcode   = box.getSession("tem_grcode");  		//교육그룹
		String  ss_gyear    = v_year;     		                    //년도

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT  X.*\n ");
			headSql.append("         , COUNT(*) OVER (PARTITION BY X.SUBJ) SUBJ_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_01) OVER (PARTITION BY X.MONTH_01, X.SUBJ) MONTH_01_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_02) OVER (PARTITION BY X.MONTH_02, X.SUBJ) MONTH_02_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_03) OVER (PARTITION BY X.MONTH_03, X.SUBJ) MONTH_03_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_04) OVER (PARTITION BY X.MONTH_04, X.SUBJ) MONTH_04_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_05) OVER (PARTITION BY X.MONTH_05, X.SUBJ) MONTH_05_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_06) OVER (PARTITION BY X.MONTH_06, X.SUBJ) MONTH_06_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_07) OVER (PARTITION BY X.MONTH_07, X.SUBJ) MONTH_07_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_08) OVER (PARTITION BY X.MONTH_08, X.SUBJ) MONTH_08_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_09) OVER (PARTITION BY X.MONTH_09, X.SUBJ) MONTH_09_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_10) OVER (PARTITION BY X.MONTH_10, X.SUBJ) MONTH_10_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_11) OVER (PARTITION BY X.MONTH_11, X.SUBJ) MONTH_11_CNT\n ");
			headSql.append("         , COUNT(X.MONTH_12) OVER (PARTITION BY X.MONTH_12, X.SUBJ) MONTH_12_CNT\n ");
			headSql.append(" FROM    (\n ");
			headSql.append("         SELECT  A.SUBJ, B.SUBJNM, A.EDUSTART, A.EDUEND, A.PROPSTART\n ");
			headSql.append("                 , A.PROPEND, A.BIYONG, A.STUDENTLIMIT\n ");
			headSql.append("                 , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y'\n ");
			headSql.append("                        ELSE 'N'\n ");
			headSql.append("                 END STATUS\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '01', '01', '') MONTH_01\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '02', '02', '') MONTH_02\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '03', '03', '') MONTH_03\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '04', '04', '') MONTH_04\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '05', '05', '') MONTH_05\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '06', '06', '') MONTH_06\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '07', '07', '') MONTH_07\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '08', '08', '') MONTH_08\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '09', '09', '') MONTH_09\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '10', '10', '') MONTH_10\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '11', '11', '') MONTH_11\n ");
			headSql.append("                 , DECODE(SUBSTR(A.EDUSTART, 5, 2), '12', '12', '') MONTH_12\n ");
			headSql.append("                 , A.SUBJSEQ, A.YEAR, C.UPPERCLASS, C.CLASSNAME\n ");
			headSql.append("                 , SUBSTR(A.EDUSTART, 5, 2) MONTH, A.SEQ\n ");
			bodySql.append("         FROM    TZ_OFFSUBJSEQ A, TZ_OFFSUBJ B, TZ_OFFSUBJATT C\n ");
			bodySql.append("         WHERE   A.SUBJ = B.SUBJ\n ");
			//bodySql.append(" AND     A.GRCODE = '").append(ss_grcode).append("'\n ");
			bodySql.append(" AND     A.YEAR = '").append(ss_gyear).append("'\n ");
			bodySql.append(" AND     B.UPPERCLASS = C.UPPERCLASS(+)\n ");
			bodySql.append(" AND     C.MIDDLECLASS(+) = '000'\n ");
			bodySql.append(" AND     B.UPPERCLASS(+) = '").append(v_upperclass).append("'\n ");
			bodySql2.append("         ORDER BY  SUBJ ASC, SUBJSEQ ASC, EDUSTART ASC\n ");
			bodySql2.append("         )    X\n ");

			if (!v_middleclass.equals("ALL") ) {
				bodySql.append(" AND     B.MIDDLECLASS = ").append(SQLString.Format(v_middleclass));
			}

			countSql= "SELECT COUNT(*) FROM (SELECT B.SUBJ "+ bodySql.toString() + "GROUP BY B.SUBJ) ";

			ls = connMgr.executeQuery(countSql);
			ls.next();

			int count = ls.getInt(1);

			sql = headSql.toString() + bodySql.toString() + bodySql2.toString();

			ls = connMgr.executeQuery(sql);



			//
			//int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//  전체 row 수를 반환한다
			//int total_page_count = ls.getTotalPage();       					//  전체 페이지 수를 반환한다
			//ls.setPageSize(v_pagesize);             							//  페이지당 row 갯수를 세팅한다
			//ls.setCurrentPage(v_pageno, total_row_count);   					//  현재페이지번호를 세팅한다.


			while (ls.next()) {
				dbox = ls.getDataBox();


				//dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				//dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				//dbox.put("d_rowcount", new Integer(10));
				//dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}

			if(list.size() > 0)  ((DataBox)list.get(0)).put("d_total_subj", count);
		}
		catch (Exception ex) {
			System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}
}
