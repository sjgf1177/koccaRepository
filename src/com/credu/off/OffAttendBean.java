//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class OffAttendBean {

	public OffAttendBean() {
	}

	/**
	 * 설명 :
	 * @param box
	 * @return
	 * @throws Exception
	 * @author swchoi
	 */
	@SuppressWarnings("unchecked")
	public DataBox listPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox  result = null;
		String t = null;

		try {
			connMgr = new DBConnectionManager();

			box.sync("s_subjcode");
			box.sync("s_year");
			box.sync("s_subjseq");
			box.sync("s_term");
			/**
			 * 출석 정보
			 */
			sql.append("SELECT A.USERID || A.ATTENDDAY field1, A.STATE field2\n");
			sql.append("FROM TZ_OFFATTEND A\n");
			sql.append("WHERE A.SUBJ = ':s_subjcode'\n");
			sql.append("AND A.YEAR = ':s_year'\n");
			sql.append("AND A.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND A.TERM = ':s_subjterm'\n");
			sql.append("ORDER BY A.USERID\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result = ls.getAllDataMap();

			/**
			 * 수강생이름, 출결통계
			 */
			sql.delete(0, sql.length());
			/*sql.append("SELECT 'lee1'  USERID, '누군가' NAME\n");
			sql.append(", 0 attend\n");
			sql.append(", 0 absent\n");
			sql.append(", 0 late\n FROM DUAL\nUNION ALL\n");//*/
			sql.append("SELECT B.USERID, C.STUDENTNO, B.NAME\n");
			sql.append(", SUM(DECODE(A.STATE, '3', 1, 0)) attend\n");
			sql.append(", SUM(DECODE(A.STATE, '0', 1, 0)) absent\n");
			sql.append(", SUM(DECODE(A.STATE, '2', 1, 0)) late\n");
			sql.append("FROM TZ_OFFATTEND A, TZ_MEMBER B, TZ_OFFSTUDENT C\n");
			sql.append("WHERE B.USERID = C.USERID\n");
			sql.append("AND A.SUBJ(+) = C.SUBJ\n");
			sql.append("AND A.YEAR(+) = C.YEAR\n");
			sql.append("AND A.SUBJSEQ(+) = C.SUBJSEQ\n");
			sql.append("AND C.SUBJ = ':s_subjcode'\n");
			sql.append("AND C.YEAR = ':s_year'\n");
			sql.append("AND C.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND A.TERM(+) = ':s_subjterm'\n");
			sql.append("GROUP BY B.USERID, C.STUDENTNO, B.NAME\n");
			sql.append("ORDER BY USERID\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result.put("keyList", ls.getAllDataList());

			/**
			 * 날짜
			 */
			sql.delete(0, sql.length());
			sql.append("WITH t as (\n");
			sql.append("\tselect TERMSTART, TERMEND, TO_DATE(TERMEND, 'YYYY-MM-DD') - TO_DATE(TERMSTART, 'YYYY-MM-DD') TYM\n");
			sql.append("\tfrom TZ_OFFTERM\n");
			sql.append("\tWHERE SUBJ = ':s_subjcode'\n");
			sql.append("\tAND YEAR = ':s_year'\n");
			sql.append("\tAND SUBJSEQ = ':s_subjseq'\n");
			sql.append("\tAND TERM = ':s_subjterm' )\n");
			sql.append("SELECT * FROM (SELECT\n");
			sql.append("\tTO_CHAR(TO_DATE(TERMSTART, 'YYYY-MM-DD') + LEVEL-1, 'YYYYMMDD') yyyymmdd,");
			sql.append("\tTO_CHAR(TO_DATE(TERMSTART, 'YYYY-MM-DD') + LEVEL-1, 'MM/dd') mmdd,");
			sql.append("\n\tto_char(TO_DATE(TERMSTART, 'YYYY-MM-DD') + LEVEL-1, 'dY', 'NLS_DATE_LANGUAGE=KOREAN') day\n");
			sql.append("FROM t\n");
			sql.append("CONNECT BY LEVEL <= TYM+1)");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result.put("dateList", ls.getAllDataList());
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, t);
			throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

}
