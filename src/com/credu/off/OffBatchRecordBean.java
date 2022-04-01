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

import java.io.File;
import java.sql.PreparedStatement;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class OffBatchRecordBean {
	private Sheet sheet = null;

	public OffBatchRecordBean() {
	}

	public List<DataBox> getReportScoreList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		List<DataBox> result = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		String t = null;
		try {
			connMgr = new DBConnectionManager();
			boolean isTerm = box.getBoolean("isTerm");

			if(isTerm) {
				sql.append("SELECT /*	학기(과목)별 템플릿	*/\n/*	ID, 이름, 과정명, 과목명	*/\nA.USERID, C.NAME,(SELECT SUBJNM FROM TZ_OFFSUBJSEQ WHERE SUBJ = A.SUBJ AND SUBJSEQ = A.SUBJSEQ AND YEAR = A.YEAR AND SEQ = 1) SUBJNM, B.LECTURENM,\n");
				sql.append("/*	과정코드, 차수, 학기, 과목코드, 점수	*/\nA.SUBJ, A.SUBJSEQ, B.TERM, B.LECTURE, (SELECT SCORE FROM TZ_OFFLECTURESCORE WHERE A.SUBJ = SUBJ\n");
				sql.append("AND A.SUBJSEQ = SUBJSEQ\n");
				sql.append("AND A.YEAR = YEAR\n");
				sql.append("AND B.LECTURE = LECTURE\n");
				sql.append("AND B.TERM = TERM\n");
				sql.append("AND A.USERID = USERID\n");
				sql.append(")  SCORE\n");
				sql.append("FROM TZ_OFFSTUDENT A, tz_OFFLECTURE B, TZ_MEMBER C\n");
				sql.append("WHERE A.SUBJ = B.SUBJ\n");
				sql.append("AND A.SUBJSEQ = B.SUBJSEQ\n");
				sql.append("AND A.YEAR = B.YEAR\n");
				sql.append("AND A.USERID = C.USERID\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjcode', 'ALL'), A.SUBJ, 1, 'ALL', 1, 0)	\n");
				sql.append("AND 1 = DECODE(NVL(':s_year', 'ALL'), A.YEAR, 1, 'ALL', 1, 0)	\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjseq', 'ALL'), A.SUBJSEQ, 1, 'ALL', 1, 0)	\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjterm', 'ALL'), B.TERM, 1, 'ALL', 1, 0)	\n");
				sql.append("ORDER BY USERID, LECTURE");

			}
			else {
				sql.append("SELECT\n");
				sql.append("	B.USERID, (SELECT SUBJNM FROM TZ_OFFSUBJSEQ WHERE SUBJ = B.SUBJ AND SUBJSEQ = B.SUBJSEQ AND YEAR = B.YEAR AND SEQ = 1) SUBJNM,B.SUBJ,B.SUBJSEQ,NVL(trim(':s_subjterm'), '1') TERM,\n");
				sql.append("	A.AVTSTEP,A.AVMTEST,A.AVHTEST,A.AVFTEST,A.AVREPORT,A.AVETC1,A.AVETC2,A.SCORE,A.RANK,\n");
				sql.append("	A.TSTEP,A.MTEST,A.HTEST,A.FTEST,A.REPORT,A.ETC1,A.ETC2\n");
				sql.append("FROM TZ_OFFTERMSTUDENT A, TZ_OFFSTUDENT B\n");
				sql.append("WHERE B.USERID = A.USERID(+)\n");
				sql.append("AND B.SUBJ = A.SUBJ(+)\n");
				sql.append("AND B.YEAR = A.YEAR(+)\n");
				sql.append("AND B.SUBJSEQ = A.SUBJSEQ(+)\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjcode', 'ALL'), B.SUBJ, 1, 'ALL', 1, 0)\n");
				sql.append("AND 1 = DECODE(NVL(':s_year', 'ALL'), B.YEAR, 1, 'ALL', 1, 0)\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjseq', 'ALL'), B.SUBJSEQ, 1, 'ALL', 1, 0)\n");
				sql.append("AND 1 = DECODE(NVL(':s_subjterm', 'ALL'), A.TERM(+), 1, 'ALL', 1, 0)");
			}
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result = ls.getAllDataList();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, t);
			throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/*
	private List<DataBox> acceptResultList(DBConnectionManager connMgr, RequestBox box) throws Exception {
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		List<DataBox>  result = null;
		String t = null;

		try {
			sql.append("SELECT LECTURE, LECTURENM, POINT\n");
			sql.append("FROM TZ_OFFLECTURE\n");
			sql.append("\tWHERE SUBJ = ':s_subjcode'\n");
			sql.append("\tAND YEAR = ':s_year'\n");
			sql.append("\tAND SUBJSEQ = ':s_subjseq'\n");
			sql.append("\tAND TERM = ':s_subjterm'\n");
			sql.append("ORDER BY LECTURENM, LECTURE");
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result = ls.getAllDataList();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, t);
			throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
		}
		return result;
	}*/

	/**
	새로운 차수 등록 - 오프라인
	@param box	  receive from the form object and session
	@return isOk	1:insert success,0:insert fail
	 */
	public String insert(RequestBox box) throws Exception {
		ConfigSet cs = new ConfigSet();
		DBConnectionManager connMgr = null;

		String debugSql = null;
		String deleteSql = null;
		String deleteLectureSql = null;
		String insertLectureSql = null;
		String insertTermScoreSql = null;
		String updateStudentSql = null;
		String insertLectureGradeSql = null;

		PreparedStatement delete_pstmt = null;
		PreparedStatement insertTerm_pstmt = null;
		PreparedStatement updateStudent_pstmt = null;
		PreparedStatement deleteLecture_pstmt = null;
		PreparedStatement insertLecture_pstmt = null;
		PreparedStatement insertLectureGrade_pstmt = null;

		boolean isTerm = box.getBoolean("isTerm");


		String  v_newFileName  = box.getNewFileName("p_file");

		//		Cell cell = null;
		Workbook workbook = null;

		String result = null;

		try {

			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			System.out.println(box.get("p_fullupdir"));
			System.out.println(v_newFileName);
			workbook = Workbook.getWorkbook(new File(cs.getProperty("dir.home")+v_newFileName));
			sheet = workbook.getSheet(0);

			int index = 1;

			box.sync("p_term");

			if(isTerm) {
				/**
				 * 학기 - 과목별 점수등록
				 * 1. 학기별 점수(TZ_OFFTERMSTUDENT) 과정, 차수, 학기에 해당되는 모든 데이터 삭제(순위 저장시 오류가 발생할 수 있으니 초기화)
				 * 2. 과정, 학기별 과목목록 조회
				 * 3. 과목별 점수(TZ_OFFLECTURESCORE) 초기화(학생 개인별)
				 * 4. 과정, 학기별 과목목록에 따라 학생 개인당 과목별 점수(TZ_OFFLECTURESCORE) 등록
				 * 5. 학기별 점수(TZ_OFFTERMSTUDENT) 등록
				 * 
				 * NO|	ID			이름		과정명	과목명		과정코드		차수		학기		과목코드	점수
				 * 			userid	name		subjnm	lecturenm	subj			subjseq		TERM		lecture		score
				 * 	0		1				2			3			4				5					6			7			8			9

				 */
				final int USERID = 1;			//사용자ID
				final int SUBJ = 5;
				final int SUBJSEQ = 6;
				final int TERM = 7;
				final int LECTURE_POSITION = 8;
				final int SCORE = 9;

				/*
				 * 1. 학기별 점수(TZ_OFFTERMSTUDENT) 초기화
				 */
				deleteSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.grade.delete"), box);
				delete_pstmt = connMgr.prepareStatement(deleteSql);
				/*	1. 종료	*/

				deleteLectureSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.delete"), box);
				insertLectureSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.insert"), box);
				insertLectureGradeSql = connMgr.replaceParam(cs.getProperty("ajax.list.lecture.grade.insert"), box);//등록된 점수기준으로 등록되기 때문에 추가적인 부분은 없음

				deleteLecture_pstmt = connMgr.prepareStatement(deleteLectureSql);
				insertLecture_pstmt = connMgr.prepareStatement(insertLectureSql);
				insertLectureGrade_pstmt = connMgr.prepareStatement(insertLectureGradeSql);

				/*
				 * 2. 과정, 학기별 과목목록 조회
				 */
				//				List<DataBox> lectureList = acceptResultList(connMgr, box);
				/*	2. 종료	*/

				for (int i=2; i < sheet.getRows() ; i++ ) {//학생별 루프 시작 : 0-문서제목라인, 1-열제목
					if(i == 2) {
						if (getCell(0,0).indexOf(box.get("s_year"))==-1) {
							result="해당 연도의 자료가 아닙니다. 다시 선택하여 주십시요.";
							throw new Exception("해당 연도의 자료가 아닙니다. 다시 선택하여 주십시요.\n"+getCell(0,0));
						}
						debugSql = connMgr.debugSqlMake(deleteSql);
						debugSql = connMgr.debugSql(delete_pstmt,debugSql, getCell(SUBJ,i), index++);
						debugSql = connMgr.debugSql(delete_pstmt,debugSql, getCell(SUBJSEQ,i), index++);
						debugSql = connMgr.debugSql(delete_pstmt,debugSql, getCell(TERM,i), index++);
						System.out.println(debugSql);

						index = 1;
						debugSql = connMgr.debugSqlMake(insertLectureGradeSql);
						debugSql = connMgr.debugSql(insertLectureGrade_pstmt,debugSql, getCell(SUBJ,i), index++);
						debugSql = connMgr.debugSql(insertLectureGrade_pstmt,debugSql, getCell(SUBJSEQ,i), index++);
						debugSql = connMgr.debugSql(insertLectureGrade_pstmt,debugSql, getCell(TERM,i), index++);
						System.out.println(debugSql);
					}
					/*
					 * 3. 과목별 점수(TZ_OFFLECTURESCORE) 초기화(학생 개인별)
					 */
					index = 1;
					debugSql = connMgr.debugSqlMake(deleteLectureSql);
					debugSql = connMgr.debugSql(deleteLecture_pstmt,debugSql, getCell(SUBJ,i), index++);
					debugSql = connMgr.debugSql(deleteLecture_pstmt,debugSql, getCell(SUBJSEQ,i), index++);
					debugSql = connMgr.debugSql(deleteLecture_pstmt,debugSql, getCell(TERM,i), index++);
					debugSql = connMgr.debugSql(deleteLecture_pstmt,debugSql, getCell(LECTURE_POSITION,i), index++);
					debugSql = connMgr.debugSql(deleteLecture_pstmt,debugSql, getCell(USERID,i), index++);
					deleteLecture_pstmt.addBatch();
					System.out.println(debugSql);
					/*	3. 종료	*/

					/*
					 * 4. 과정, 학기별 과목목록에 따라 학생 개인당 과목별 점수(TZ_OFFLECTURESCORE) 등록
					 */
					index = 1;
					debugSql = connMgr.debugSqlMake(insertLectureSql);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(SUBJ,i), index++);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(SUBJSEQ,i), index++);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(TERM,i), index++);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(LECTURE_POSITION,i), index++);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(USERID,i), index++);
					debugSql = connMgr.debugSql(insertLecture_pstmt,debugSql, getCell(SCORE,i), index++);
					System.out.println(debugSql);
					insertLecture_pstmt.addBatch();

					index = 1;

					/*
					for (int lecture=0; lecture<lectureList.size(); lecture++) {//과목별 루프 시작 // 이렇게 하고 싶었으나.. 시간 관계상 버림
						insertLecture_pstmt.setString(index++, lectureList.get(lecture).get(LECTURE));//LECTURE
						insertLecture_pstmt.setString(index++, getCell(USERID,i));//USERID
						insertLecture_pstmt.setString(index++, getCell(LECTURE_POSITION+lecture,i));//SCORE
						insertLecture_pstmt.addBatch();
						index = 1;
					}//과목별 루프 종료*/
					/*	4. 종료	*/
				}//학생별 루프 종료

				delete_pstmt.executeUpdate();

				deleteLecture_pstmt.executeBatch();
				insertLecture_pstmt.executeBatch();
				/*
				 * 5. 학기별 점수(TZ_OFFTERMSTUDENT) 등록
				 */
				insertLectureGrade_pstmt.executeUpdate();//등록된 점수기준으로 등록되기 때문에 추가적인 부분은 없음
				/*	5. 종료	*/
			}
			else {
				/**
				 * 과정별 점수등록.
				 * 1. 차수별-수강생정보 초기화
				 * 2. 차수별-수강생정보 등록 : 가중치를 참조하여 추가정보와 수료여부 책정.
				 * 3. 수강생정보 : 수료여부 수정

				 * sheet - filed 용도(공통)
				 *	NO	|	ID			|과정명	|과정코드	|차수			|수료여부		|출석률점수(진도율)	|중간평가	|형성평가	|최종평가	|과제		|참여도	|기타
				 *			| d_userid	|subjnm	|subj			|d_subjseq	|TERM			|TSTEP					|MTEST		|HTEST		|FTEST		|REPORT	|ETC1		|ETC2
				 * 	0				1			2				3				4				5					6						7				8				9				10			11			12
				 */
				final int USERID = 1;			//사용자ID
				final int SUBJ = 3;
				final int SUBJSEQ = 4;
				final int TSTEP = 6;
				final int MTEST = 7;
				final int HTEST = 8;
				final int FTEST = 9;
				final int REPORT = 10;
				final int ETC1 = 11;
				final int ETC2 = 12;

				final int ISGRADUATED = 5;	//수료여부
				insertTermScoreSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.insert"), box);
				updateStudentSql = connMgr.replaceParam(cs.getProperty("ajax.list.lecture.isgraduated.update"), box);
				deleteSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.delete"), box);

				delete_pstmt = connMgr.prepareStatement(deleteSql);
				insertTerm_pstmt = connMgr.prepareStatement(insertTermScoreSql);
				updateStudent_pstmt = connMgr.prepareStatement(updateStudentSql);
				/**
				 * 해당 학생 정보 초기화 후 저장 : 0-문서제목라인, 1-열제목
				 */
				for (int i=2; i < sheet.getRows() ; i++ ) {
					/**
					 * 1. 해당 학생 정보 초기화
					 */
					index = 1;
					//					delete_pstmt.setString(index++, getCell(USERID,i));
					debugSql = connMgr.debugSql(delete_pstmt, deleteSql, getCell(USERID,i), index++);
					delete_pstmt.addBatch();
					/**
					 * 2. 학기점수 등록 - 강좌
					 */
					index = 1;
					/*					insertTerm_pstmt.setString(index++, getCell(USERID,i));
					insertTerm_pstmt.setString(index++, getCell(TSTEP,i));
					insertTerm_pstmt.setString(index++, getCell(MTEST,i));
					insertTerm_pstmt.setString(index++, getCell(FTEST,i));
					insertTerm_pstmt.setString(index++, getCell(HTEST,i));
					insertTerm_pstmt.setString(index++, getCell(REPORT,i));
					insertTerm_pstmt.setString(index++, getCell(ETC1,i));
					insertTerm_pstmt.setString(index++, getCell(ETC2,i));
					insertTerm_pstmt.setString(index++, getCell(TSTEP,i));
					insertTerm_pstmt.setString(index++, getCell(MTEST,i));
					insertTerm_pstmt.setString(index++, getCell(FTEST,i));
					insertTerm_pstmt.setString(index++, getCell(HTEST,i));
					insertTerm_pstmt.setString(index++, getCell(REPORT,i));
					insertTerm_pstmt.setString(index++, getCell(ETC1,i));
					insertTerm_pstmt.setString(index++, getCell(ETC2,i));
					insertTerm_pstmt.setString(index++, getCell(TSTEP,i));
					insertTerm_pstmt.setString(index++, getCell(MTEST,i));
					insertTerm_pstmt.setString(index++, getCell(HTEST,i));
					insertTerm_pstmt.setString(index++, getCell(FTEST,i));
					insertTerm_pstmt.setString(index++, getCell(REPORT,i));
					insertTerm_pstmt.setString(index++, getCell(ETC1,i));
					insertTerm_pstmt.setString(index++, getCell(ETC2,i));
					insertTerm_pstmt.setString(index++, getCell(SUBJ,i));
					insertTerm_pstmt.setString(index++, getCell(SUBJSEQ,i));*/
					debugSql = connMgr.debugSqlMake(insertTermScoreSql);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(USERID,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(TSTEP,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(MTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(FTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(HTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(REPORT,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC1,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC2,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(TSTEP,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(MTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(FTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(HTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(REPORT,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC1,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC2,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(TSTEP,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(MTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(HTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(FTEST,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(REPORT,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC1,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(ETC2,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(SUBJ,i), index++);
					debugSql = connMgr.debugSql(insertTerm_pstmt,debugSql, getCell(SUBJSEQ,i), index++);

					insertTerm_pstmt.addBatch();
					//					System.out.println(debugSql);


					/**
					 * 3. 수료정보 수정 - 강좌
					 */
					index = 1;
					updateStudent_pstmt.setString(index++, getCell(ISGRADUATED,i));
					updateStudent_pstmt.setString(index++, getCell(USERID,i));
					updateStudent_pstmt.addBatch();

					index = 1;
				}

				/**
				 * 저장
				 */
				delete_pstmt.executeBatch();
				insertTerm_pstmt.executeBatch();
				updateStudent_pstmt.executeBatch();
			}


			connMgr.commit();
		}
		catch(Exception ex) {
			result = "저장이 실패하였습니다";
			connMgr.rollback();
		} finally {
			//첨부파일삭제
			FileManager.deleteFile(cs.getProperty("dir.home")+cs.getProperty("dir.upload.accept")+v_newFileName);
			if(delete_pstmt != null) { try { delete_pstmt.close(); } catch (Exception e) {} }

			if(insertTerm_pstmt != null) { try { insertTerm_pstmt.close(); } catch (Exception e) {} }
			if(updateStudent_pstmt != null) { try { updateStudent_pstmt.close(); } catch (Exception e) {} }

			if(deleteLecture_pstmt != null) { try { deleteLecture_pstmt.close(); } catch (Exception e) {} }
			if(insertLecture_pstmt != null) { try { insertLecture_pstmt.close(); } catch (Exception e) {} }
			if(insertLectureGrade_pstmt != null) { try { insertLectureGrade_pstmt.close(); } catch (Exception e) {} }

			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	private String getCell(int x, int y) throws Exception{
		return StringManager.trim(sheet.getCell(x,y).getContents());
	}
}
