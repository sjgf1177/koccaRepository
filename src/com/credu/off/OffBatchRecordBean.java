//**********************************************************
//  1. ��	  ��:  ����
//  2. ���α׷��� : Bean.java
//  3. ��	  ��:  ����
//  4. ȯ	  ��: JDK 1.5
//  5. ��	  ��: 1.0
//  6. ��	  ��: __����__ 2009. 10. 19
//  7. ��	  ��: __����__ 2009. 10. 19
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
				sql.append("SELECT /*	�б�(����)�� ���ø�	*/\n/*	ID, �̸�, ������, �����	*/\nA.USERID, C.NAME,(SELECT SUBJNM FROM TZ_OFFSUBJSEQ WHERE SUBJ = A.SUBJ AND SUBJSEQ = A.SUBJSEQ AND YEAR = A.YEAR AND SEQ = 1) SUBJNM, B.LECTURENM,\n");
				sql.append("/*	�����ڵ�, ����, �б�, �����ڵ�, ����	*/\nA.SUBJ, A.SUBJSEQ, B.TERM, B.LECTURE, (SELECT SCORE FROM TZ_OFFLECTURESCORE WHERE A.SUBJ = SUBJ\n");
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
	���ο� ���� ��� - ��������
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
				 * �б� - ���� �������
				 * 1. �б⺰ ����(TZ_OFFTERMSTUDENT) ����, ����, �б⿡ �ش�Ǵ� ��� ������ ����(���� ����� ������ �߻��� �� ������ �ʱ�ȭ)
				 * 2. ����, �б⺰ ������ ��ȸ
				 * 3. ���� ����(TZ_OFFLECTURESCORE) �ʱ�ȭ(�л� ���κ�)
				 * 4. ����, �б⺰ �����Ͽ� ���� �л� ���δ� ���� ����(TZ_OFFLECTURESCORE) ���
				 * 5. �б⺰ ����(TZ_OFFTERMSTUDENT) ���
				 * 
				 * NO|	ID			�̸�		������	�����		�����ڵ�		����		�б�		�����ڵ�	����
				 * 			userid	name		subjnm	lecturenm	subj			subjseq		TERM		lecture		score
				 * 	0		1				2			3			4				5					6			7			8			9

				 */
				final int USERID = 1;			//�����ID
				final int SUBJ = 5;
				final int SUBJSEQ = 6;
				final int TERM = 7;
				final int LECTURE_POSITION = 8;
				final int SCORE = 9;

				/*
				 * 1. �б⺰ ����(TZ_OFFTERMSTUDENT) �ʱ�ȭ
				 */
				deleteSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.grade.delete"), box);
				delete_pstmt = connMgr.prepareStatement(deleteSql);
				/*	1. ����	*/

				deleteLectureSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.delete"), box);
				insertLectureSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.lecture.insert"), box);
				insertLectureGradeSql = connMgr.replaceParam(cs.getProperty("ajax.list.lecture.grade.insert"), box);//��ϵ� ������������ ��ϵǱ� ������ �߰����� �κ��� ����

				deleteLecture_pstmt = connMgr.prepareStatement(deleteLectureSql);
				insertLecture_pstmt = connMgr.prepareStatement(insertLectureSql);
				insertLectureGrade_pstmt = connMgr.prepareStatement(insertLectureGradeSql);

				/*
				 * 2. ����, �б⺰ ������ ��ȸ
				 */
				//				List<DataBox> lectureList = acceptResultList(connMgr, box);
				/*	2. ����	*/

				for (int i=2; i < sheet.getRows() ; i++ ) {//�л��� ���� ���� : 0-�����������, 1-������
					if(i == 2) {
						if (getCell(0,0).indexOf(box.get("s_year"))==-1) {
							result="�ش� ������ �ڷᰡ �ƴմϴ�. �ٽ� �����Ͽ� �ֽʽÿ�.";
							throw new Exception("�ش� ������ �ڷᰡ �ƴմϴ�. �ٽ� �����Ͽ� �ֽʽÿ�.\n"+getCell(0,0));
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
					 * 3. ���� ����(TZ_OFFLECTURESCORE) �ʱ�ȭ(�л� ���κ�)
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
					/*	3. ����	*/

					/*
					 * 4. ����, �б⺰ �����Ͽ� ���� �л� ���δ� ���� ����(TZ_OFFLECTURESCORE) ���
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
					for (int lecture=0; lecture<lectureList.size(); lecture++) {//���� ���� ���� // �̷��� �ϰ� �;�����.. �ð� ����� ����
						insertLecture_pstmt.setString(index++, lectureList.get(lecture).get(LECTURE));//LECTURE
						insertLecture_pstmt.setString(index++, getCell(USERID,i));//USERID
						insertLecture_pstmt.setString(index++, getCell(LECTURE_POSITION+lecture,i));//SCORE
						insertLecture_pstmt.addBatch();
						index = 1;
					}//���� ���� ����*/
					/*	4. ����	*/
				}//�л��� ���� ����

				delete_pstmt.executeUpdate();

				deleteLecture_pstmt.executeBatch();
				insertLecture_pstmt.executeBatch();
				/*
				 * 5. �б⺰ ����(TZ_OFFTERMSTUDENT) ���
				 */
				insertLectureGrade_pstmt.executeUpdate();//��ϵ� ������������ ��ϵǱ� ������ �߰����� �κ��� ����
				/*	5. ����	*/
			}
			else {
				/**
				 * ������ �������.
				 * 1. ������-���������� �ʱ�ȭ
				 * 2. ������-���������� ��� : ����ġ�� �����Ͽ� �߰������� ���Ῡ�� å��.
				 * 3. ���������� : ���Ῡ�� ����

				 * sheet - filed �뵵(����)
				 *	NO	|	ID			|������	|�����ڵ�	|����			|���Ῡ��		|�⼮������(������)	|�߰���	|������	|������	|����		|������	|��Ÿ
				 *			| d_userid	|subjnm	|subj			|d_subjseq	|TERM			|TSTEP					|MTEST		|HTEST		|FTEST		|REPORT	|ETC1		|ETC2
				 * 	0				1			2				3				4				5					6						7				8				9				10			11			12
				 */
				final int USERID = 1;			//�����ID
				final int SUBJ = 3;
				final int SUBJSEQ = 4;
				final int TSTEP = 6;
				final int MTEST = 7;
				final int HTEST = 8;
				final int FTEST = 9;
				final int REPORT = 10;
				final int ETC1 = 11;
				final int ETC2 = 12;

				final int ISGRADUATED = 5;	//���Ῡ��
				insertTermScoreSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.insert"), box);
				updateStudentSql = connMgr.replaceParam(cs.getProperty("ajax.list.lecture.isgraduated.update"), box);
				deleteSql = connMgr.replaceParam(cs.getProperty("ajax.list.batchFile.delete"), box);

				delete_pstmt = connMgr.prepareStatement(deleteSql);
				insertTerm_pstmt = connMgr.prepareStatement(insertTermScoreSql);
				updateStudent_pstmt = connMgr.prepareStatement(updateStudentSql);
				/**
				 * �ش� �л� ���� �ʱ�ȭ �� ���� : 0-�����������, 1-������
				 */
				for (int i=2; i < sheet.getRows() ; i++ ) {
					/**
					 * 1. �ش� �л� ���� �ʱ�ȭ
					 */
					index = 1;
					//					delete_pstmt.setString(index++, getCell(USERID,i));
					debugSql = connMgr.debugSql(delete_pstmt, deleteSql, getCell(USERID,i), index++);
					delete_pstmt.addBatch();
					/**
					 * 2. �б����� ��� - ����
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
					 * 3. �������� ���� - ����
					 */
					index = 1;
					updateStudent_pstmt.setString(index++, getCell(ISGRADUATED,i));
					updateStudent_pstmt.setString(index++, getCell(USERID,i));
					updateStudent_pstmt.addBatch();

					index = 1;
				}

				/**
				 * ����
				 */
				delete_pstmt.executeBatch();
				insertTerm_pstmt.executeBatch();
				updateStudent_pstmt.executeBatch();
			}


			connMgr.commit();
		}
		catch(Exception ex) {
			result = "������ �����Ͽ����ϴ�";
			connMgr.rollback();
		} finally {
			//÷�����ϻ���
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
