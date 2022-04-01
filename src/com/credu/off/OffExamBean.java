//**********************************************************
//  1. 제	  목:  성적관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi  2009. 12.16
//  7. 수	  정: swchoi  2009. 12.16
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

@SuppressWarnings("unchecked")
public class OffExamBean {

	public OffExamBean() {
	}

	/**
	 * 설명 :
	 * @param box
	 * @return
	 * @throws Exception
	 * @author swchoi
	 */
	public DataBox listPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox  result = null;
		String t = null;
		box.put("p_orderType", box.getStringDefault("p_orderType", " asc"));

		try {
			connMgr = new DBConnectionManager();

			box.sync("s_subjcode");
			box.sync("s_year");
			box.sync("s_subjseq");
			box.sync("s_term");
			/**
			 * 성적 정보
			 */
			sql.append("SELECT USERID || 'HHH' || LECTURE FIELD1,NVL(SCORE, 0) FIELD2\n");
			sql.append("FROM TZ_OFFLECTURESCORE A\n");
			sql.append("WHERE A.SUBJ = ':s_subjcode'\n");
			sql.append("AND A.YEAR = ':s_year'\n");
			sql.append("AND A.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND A.TERM = ':s_subjterm'\n");
			sql.append("ORDER BY A.USERID\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result = ls.getAllDataMap();

			/**
			 * 수강생이름, 성적통계
			 */
			sql.delete(0, sql.length());
			sql.append("SELECT STUDENTNO, A.USERID, NAME, NVL(B.RANK, 0) RANK, NVL(B.SCORE, 0) SCORE\n");
			sql.append("FROM TZ_OFFSTUDENT A, TZ_OFFTERMSTUDENT B, TZ_MEMBER C\n");
			sql.append("WHERE A.SUBJ = B.SUBJ(+)\n");
			sql.append("AND A.SUBJSEQ = B.SUBJSEQ(+)\n");
			sql.append("AND A.YEAR = B.YEAR(+)\n");
			sql.append("AND A.USERID = B.USERID(+)\n");
			sql.append("AND A.USERID = C.USERID\n");
			sql.append("AND A.SUBJ = ':s_subjcode'\n");
			sql.append("AND A.YEAR = ':s_year'\n");
			sql.append("AND A.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND B.TERM(+) = ':s_subjterm'\n");
			sql.append("ORDER BY ");
			sql.append(box.getStringDefault("p_orderColumn", "RANK"));
			sql.append(box.getString("p_orderType"));

			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result.put("keyList", ls.getAllDataList());

			/**
			 * 날짜
			 */
			sql.delete(0, sql.length());
			sql.append("SELECT LECTURE, LECTURENM, POINT\n");
			sql.append("FROM TZ_OFFLECTURE\n");
			sql.append("\tWHERE SUBJ = ':s_subjcode'\n");
			sql.append("\tAND YEAR = ':s_year'\n");
			sql.append("\tAND SUBJSEQ = ':s_subjseq'\n");
			sql.append("\tAND TERM = ':s_subjterm'\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result.put("lectureList", ls.getAllDataList());
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

	public DataBox listPageOneTerm(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox  result = null;
		String t = null;
		box.put("p_orderType", box.getStringDefault("p_orderType", " asc"));

		try {
			connMgr = new DBConnectionManager();

			box.sync("s_subjcode");
			box.sync("s_year");
			box.sync("s_subjseq");
			sql.append("SELECT\n");
			sql.append("	A.EDUSTART,A.EDUEND,\n");
			sql.append("	GRADSCORE,GRADSTEP,GRADFTEST, GRADHTEST, GRADREPORT,GRADEXAM,\n");
			sql.append("	WSTEP,WMTEST,WFTEST,WHTEST,WREPORT,WETC1,WETC2,\n");
			sql.append("	SUM(DECODE(ISGRADUATED, 'Y', 1, 0)) ISGRADUATEDY,\n");
			sql.append("	SUM(DECODE(ISGRADUATED, 'N', 1, 0)) ISGRADUATEDN\n");
			sql.append("FROM TZ_OFFSUBJSEQ A, TZ_OFFSTUDENT C\n");
			sql.append("WHERE 1=1\n");
			sql.append("AND A.SUBJ = ':s_subjcode'\n");
			sql.append("AND A.YEAR = ':s_year'\n");
			sql.append("AND A.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND A.SUBJ = C.SUBJ(+)\n");
			sql.append("AND A.SUBJSEQ = C.SUBJSEQ(+)\n");
			sql.append("AND A.YEAR = C.YEAR(+)\n");
			sql.append("AND A.SEQ = 1\n");
			sql.append("GROUP BY A.EDUSTART,A.EDUEND,\n");
			sql.append("GRADSCORE,GRADSTEP,GRADFTEST, GRADHTEST,GRADREPORT,GRADEXAM,\n");
			sql.append("WSTEP,WMTEST,WFTEST,WHTEST,WREPORT,WETC1,WETC2\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			if(ls.next())
				result = ls.getDataBox();

			/**
			 * 성적 정보
			 */
			sql.delete(0, sql.length());
			sql.append("SELECT\n");
			sql.append("	A.USERID, A.ISGRADUATED, B.NAME,A.SUBJ,A.YEAR,A.SUBJSEQ,C.TERM,\n");
			sql.append("	SCORE,RANK,\n");
			sql.append("	C.AVTSTEP,C.AVMTEST,C.AVHTEST,C.AVFTEST,C.AVREPORT,C.AVETC1,C.AVETC2,\n");
			sql.append("	C.TSTEP,C.MTEST,C.HTEST,C.FTEST,C.REPORT,C.ETC1,C.ETC2\n");
			sql.append("FROM TZ_OFFSTUDENT A, TZ_MEMBER B, TZ_OFFTERMSTUDENT C\n");
			sql.append("WHERE A.USERID = B.USERID\n");
			sql.append("AND A.SUBJ = C.SUBJ(+)\n");
			sql.append("AND A.SUBJSEQ = C.SUBJSEQ(+)\n");
			sql.append("AND A.YEAR = C.YEAR(+)\n");
			sql.append("AND A.USERID = C.USERID(+)\n");
			sql.append("AND A.SUBJ = ':s_subjcode'\n");
			sql.append("AND A.YEAR = ':s_year'\n");
			sql.append("AND A.SUBJSEQ = ':s_subjseq'\n");
			sql.append("AND C.TERM(+) = 1\n");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result.put("resultList", ls.getAllDataList());

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
