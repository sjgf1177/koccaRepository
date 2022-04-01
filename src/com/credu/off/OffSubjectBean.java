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

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.List;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.namo.SmeNamoMime;

public class OffSubjectBean {

	public OffSubjectBean() {
	}

	/**
	 *  삭제할때
	 * @param box	  receive from the form object and session
	 * @return isOk	1:delete success,0:delete fail
	 * @throws Exception
	 */
	public int delete(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();;
		int isOk = 0;


		try {
			connMgr = new DBConnectionManager();

			sql.append(" delete from TZ_OFFLECTURE A	\n");
			sql.append("where (SUBJ||YEAR||SUBJSEQ||TERM) = (':p_subj' || ':p_year' || ':p_subjseq' || ':p_term') and lecture =  ?\n");
			pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
			Object lecture = box.getObject("d_lecture");
			if (lecture!=null && lecture.getClass().isArray()) {
				String[]d_lecture = (String[])lecture;
				for (String temp : d_lecture) {
					pstmt.setString(1, temp);
					pstmt.addBatch();
				}
			}
			else if (lecture!=null) {
				pstmt.setString(1, (String) lecture);
				pstmt.addBatch();
			}
			else {
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
				return 0;
			}
			pstmt.executeBatch();
			isOk = 1;
		}
		catch (Exception ex) {
			System.out.println(ex);
			ErrorManager.getErrorStackTrace(ex, box, sql+ "\r\n");
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	private boolean existLecture(DBConnectionManager connMgr, RequestBox box) throws Exception {
		boolean result = false;
		int cnt = 0;
		ListSet ls = null;
		String sql  = "";
		try {
			sql = " select count(*) cnt \nfrom TZ_OFFLECTURE\nwhere (SUBJ||YEAR||SUBJSEQ||TERM||LECTURE) = (':p_subj'||':p_year'||':p_subjseq'||':p_term'||':p_lecture')";
			ls = connMgr.executeQuery(sql, box, true);
			if  (ls.next()) {
				cnt = ls.getInt("cnt");
			}

			if (cnt == 0 ) {
				result = false;
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
		}
		return result;
	}

	/**
	새로운 차수 등록 - 오프라인
	@param box	  receive from the form object and session
	@return isOk	1:insert success,0:insert fail
	 */
	public int InsertSubject(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		int isOk = 0;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			String v_subj = box.getString("p_subj");

			/*********************************************************************************************/
			// 나모에디터 본문 처리
			ConfigSet conf = new ConfigSet();
			SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
			boolean result = namo.parse(); // 실제 파싱 수행
			if (!result) { // 파싱 실패시
				System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
				return 0;
			}
			if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
				String v_server = conf.getProperty("autoever.url.value");
				String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
				String refUrl = conf.getProperty("url.namo");
				; // 웹에서 저장된 파일을 접근하기 위한 경로
				String prefix = "offSubj" + v_subj; // 파일명 접두어
				result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
			}
			if (!result) { // 파일저장 실패시
				System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
				return 0;
			}
			/*********************************************************************************************/
			if (box.get("p_lecture").length()==0 || existLecture(connMgr, box)) {
				sql.append("INSERT INTO TZ_OFFLECTURE (\n");
				sql.append("	SUBJ,YEAR,SUBJSEQ,TERM,LECTURE,LECTURENM,LECTUREEN,POINT,TUTORID,ISUSE,EXPLAIN,ETC,LUSERID,LDATE\n");
				sql.append(") VALUES (\n");
				sql.append("	':p_subj', ':p_year', ':p_subjseq', ':p_term', (SELECT NVL(MAX(lecture), 0)+1 FROM TZ_OFFLECTURE WHERE (SUBJ||YEAR||SUBJSEQ||TERM) = (':p_subj' || ':p_year' || ':p_subjseq' || ':p_term' )), ':p_lecturenm', ':p_lectureen', ':p_point', ':p_tutorid', ':p_isuse', ?, ':p_etc', ?, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
				sql.append(")\n");
			}
			else {
				sql.append("UPDATE TZ_OFFLECTURE SET\n");
				sql.append("(LECTURENM,LECTUREEN,POINT,TUTORID,ISUSE,ETC,LDATE) = \n");
				sql.append("(SELECT ':p_lecturenm', ':p_lectureen', ':p_point', ':p_tutorid', ':p_isuse', ':p_etc', TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS') FROM DUAL), EXPLAIN=?, LUSERID=?\n");
				sql.append("where (SUBJ||YEAR||SUBJSEQ||TERM||LECTURE) = (':p_subj' || ':p_year' || ':p_subjseq' || ':p_term' || ':p_lecture')");
			}
			pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));

			int i = 1;
			//			pstmt.setString(i++, namo.getContent());
			pstmt.setCharacterStream(i++, new StringReader(namo.getContent()), namo.getContent().length());
			pstmt.setString(i++, box.getSession("userid"));

			isOk = pstmt.executeUpdate();


			if (isOk==1) {
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
			throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
	/**
	 * 설명 :
	 * @param box
	 * @return
	 * @throws Exception
	 * @author swchoi
	 */
	public List<DataBox> listPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		List<DataBox>  result = null;
		String t = null;

		try {
			connMgr = new DBConnectionManager();

			sql.append("SELECT	/*	OffSubjectBean.java:184	*/\n");
			sql.append("\tB.SUBJNM, A.SUBJ,A.YEAR,A.SUBJSEQ,A.TERM,A.LECTURE,A.LECTURENM,LECTUREEN,POINT,(SELECT NAME FROM TZ_MEMBER WHERE USERID=TUTORID) tutornm, TUTORID,A.ISUSE,/*A.EXPLAIN,*/ETC,A.LUSERID,A.LDATE,C.TERMSTART,C.TERMEND\n");
			sql.append("FROM TZ_OFFLECTURE A, TZ_OFFSUBJ B, TZ_OFFTERM C\n");
			sql.append("WHERE 1=1	\n");
			sql.append("	AND A.SUBJ = B.SUBJ\n");
			sql.append("	AND A.SUBJ = C.SUBJ\n");
			sql.append("	AND A.SUBJSEQ = C.SUBJSEQ\n");
			sql.append("	AND A.YEAR = C.YEAR\n");
			sql.append("	AND A.TERM = C.TERM\n");
			sql.append("	AND 1 = DECODE(NVL(:s_subjcode, 0), A.SUBJ, 1, 'ALL', 1, 0)	\n");
			sql.append("	AND 1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("	AND 1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("	AND 1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("	AND B.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, B.SUBJNM) || '%'\n");

			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);

			result = ls.getAllDataList();
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

	/**
	 * 설명 : 상세 조회한다.
	 * @param box
	 * @return
	 * @throws Exception
	 * @author swchoi
	 */
	public DataBox selectPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox  result = null;

		try {
			connMgr = new DBConnectionManager();

			sql.append("SELECT	/*	상세 조회한다. OffSubjectBean.java:227	*/\n");
			sql.append("\t(SELECT SUBJNM FROM TZ_OFFSUBJ WHERE SUBJ=B.SUBJ) SUBJNM, B.SUBJ,B.YEAR,B.SUBJSEQ,B.TERM,LECTURE,LECTURENM,LECTUREEN,POINT,(SELECT NAME FROM TZ_MEMBER WHERE USERID=A.TUTORID) TUTORNM,TUTORID,ISUSE,EXPLAIN,ETC,LUSERID,LDATE\n");
			sql.append("FROM TZ_OFFLECTURE A, (SELECT ':p_subj' SUBJ, ':p_year' YEAR, ':p_subjseq' SUBJSEQ,':p_term' TERM FROM DUAL) B\n");
			sql.append("WHERE 1=1\n");
			sql.append("AND A.SUBJ(+) = B.SUBJ\n");
			sql.append("AND A.YEAR(+) = B.YEAR\n");
			sql.append("AND A.SUBJSEQ(+) = B.SUBJSEQ\n");
			sql.append("AND A.TERM(+) = B.TERM\n");
			sql.append("AND A.LECTURE(+) = ':p_lecture'\n");

			ls = connMgr.executeQuery(sql.toString(), box, true);

			if(ls.next())
				result = ls.getDataBox();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/**
	과정정보 수정 - 오프라인
	@param box	  receive from the form object and session
	@return isOk	1:insert success,0:insert fail
	 */
	public int update(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;

		StringBuffer sql = new StringBuffer();

		int isOk = 0;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//v_subj = getMaxSubjcode(connMgr, box.getString("p_upperclass"), box.getString("p_middleclass"));
			String v_subj = box.getString("p_subj");

			/*********************************************************************************************/
			// 나모에디터 본문 처리
			ConfigSet conf = new ConfigSet();
			SmeNamoMime namo = new SmeNamoMime(box.getString("p_explain")); // 객체생성
			boolean result = namo.parse(); // 실제 파싱 수행
			if (!result) { // 파싱 실패시
				System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
				return 0;
			}
			if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
				String v_server = conf.getProperty("autoever.url.value");
				String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
				String refUrl = conf.getProperty("url.namo");
				; // 웹에서 저장된 파일을 접근하기 위한 경로
				String prefix = "offSubj" + v_subj; // 파일명 접두어
				result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
			}
			if (!result) { // 파일저장 실패시
				System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
				return 0;
			}
			/*********************************************************************************************/

			sql.append("UPDATE TZ_OFFSUBJSEQ SET\n");
			sql.append("	SUBJ				=':p_subj',\n");
			sql.append("	YEAR			=':p_year',\n");
			sql.append("	SUBJSEQ		=':p_subjseq',\n");
			sql.append("	SEQ				=':p_seq',\n");
			sql.append("	PROPSTART	=':p_propstart',\n");
			sql.append("	PROPEND		=':p_propend',\n");
			sql.append("	EDUSTART	=':p_edustart',\n");
			sql.append("	EDUEND		=':p_eduend',\n");
			//				sql.append("	ISCLOSED		=':p_isclosed',\n");
			sql.append("	SUBJNM		=':p_subjnm',\n");
			sql.append("	STUDENTLIMIT	=':p_studentlimit',\n");
			sql.append("	BIYONG			=':p_biyong',\n");
			sql.append("	ISGOYONG	=':p_isgoyong',\n");
			sql.append("	GRADSCORE	=':p_gradscore',\n");
			sql.append("	GRADSTEP	=':p_gradstep',\n");
			sql.append("	GRADREPORT=':p_gradreport',\n");
			sql.append("	GRADEXAM	=':p_gradexam',\n");
			sql.append("	WSTEP			=':p_wstep',\n");
			sql.append("	WMTEST		=':p_wmtest',\n");
			sql.append("	WFTEST		=':p_wftest',\n");
			sql.append("	WHTEST		=':p_whtest',\n");
			sql.append("	WREPORT		=':p_wreport',\n");
			sql.append("	WETC1			=':p_wetc1',\n");
			sql.append("	WETC2			=':p_wetc2',\n");
			sql.append("	GRADFTEST	=':p_gradftest',\n");
			sql.append("	GRADHTEST	=':p_gradhtest',\n");
			sql.append("	PLACE			=':p_place',\n");
			sql.append("	CHARGER		=':p_charger',\n");
			sql.append("	BILLBEGINDT	=':p_billbegindt',\n");
			sql.append("	BILLENDDT	=':p_billenddt',\n");
			sql.append("	STARTCANCELDATE= ':p_startcanceldate',\n");
			sql.append("	ENDCANCELDATE	=':p_endcanceldate',\n");
			sql.append("	SPECIALS		=':p_specials',\n");
			sql.append("	TUTORID		=':p_tutorid',\n");
			sql.append("	TERMTOTAL	=':p_term',\n");
			sql.append("	ISUSE			=':p_isuse',\n");
			sql.append("	ISVISIBLE		=':p_isvisible',\n");
			sql.append("	ISINTRODUCTION	=':p_isintroduction',\n");
			sql.append("	EDUDAYS		=':p_edudays',\n");
			sql.append("	EDUDAYSTYPE	=':p_edudaystype',\n");
			sql.append("	GOYONGPRICEMAJOR =':p_goyongpricemajor',\n");
			sql.append("	GOYONGPRICEMINOR =':p_goyongpriceminor',\n");
			sql.append("	SUBJTARGET	=':p_subjtarget',\n");
			sql.append("	INTRO			=':p_intro',\n");
			sql.append("	EXPLAIN		=?,\n");
			sql.append("	NEEDINPUT	=':p_needinput',\n");
			sql.append("	LUSERID		=	?,\n");
			sql.append("	LDATE			=	TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')\n");
			sql.append("WHERE\n");
			sql.append("	SUBJ				=':p_subj'\n");
			sql.append("AND	SUBJSEQ	=':p_subjseq'\n");
			sql.append("AND	seq		=':p_seq'\n");
			sql.append("AND	year		=':p_year'\n");

			System.out.println(connMgr.replaceParam(sql.toString(), box, true));
			pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
			int i = 1;
			pstmt.setCharacterStream(i++, new StringReader(namo.getContent()), namo.getContent().length());
			pstmt.setString(i++, box.getSession("userid"));

			isOk = pstmt.executeUpdate();

			if (isOk==1) {
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
			throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


}
