//**********************************************************
//  1. 제      목: 학습창 접속통계
//  2. 프로그램명: StudyCountBean.java
//  3. 개      요: 접속통계
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2003. 7. 7
//  7. 수      정:
/*
'01', '진도/목차', '/servlet/controller.contents.EduStart?p_process=eduList'
'02', '자료방2-HTML file', 'data/data.html',
'03', '질문방', '/servlet/controller.study.SubjQnaStudyServlet?p_process=SubjQnaFrame'
'04', '학습도우미', 'help/start.html',
'21', '자료방1-게시판형태', '/servlet/controller.study.StudyDataServlet',
'22', '게시판', '/servlet/controller.study.StudyBoardServlet',
'31', '토론방', '/servlet/controller.study.ToronServlet',
'33', '화상특강', 'vod/main.html',
'34', 'Report(Project)', '/servlet/controller.study.ProjectServlet?p_process=choicePage'
'35', '용어사전', '/servlet/controller.study.DicSubjStudyServlet?p_process=selectListPre'
'40', '관련Site', 'linksite.html',
'91', '평가', '/servlet/controller.study.StudyExamServlet',
'93', '수강생현황', '/servlet/controller.study.StudentListServlet?p_process=select'
'94', '강사소개', '/servlet/controller.study.TutorInfoServlet',
'95', '설문', '/servlet/controller.study.StudySulmunServlet',

// 과정별 메뉴 접속 정보 추가
box.put("p_menu","01");
StudyCountBean scBean = new StudyCountBean();
scBean.writeLog(box);
 */
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;


public class StudyCountBean {

	public StudyCountBean() {}

	/**
	 * 로그 작성
	 * @param box       receive from the form object and session
	 * @return is_Ok    1 : log ok      2 : log fail
	 * @throws Exception
	 */
	public int writeLog(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		String sql = "";
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		int cnt  = 0;
		int is_Ok = 0;

		String v_grcode     = box.getSession("tem_grcode");
		String v_subj       = box.getStringDefault("p_subj", box.getSession("subj"));
		String v_year       = box.getStringDefault("p_year", box.getSession("year"));
		String v_subjseq    = box.getStringDefault("p_subjseq", box.getSession("subjseq"));
		String v_menu       = box.getString("p_menu");
		String s_userid     = box.getSession("userid");

		try {
			/*
System.out.println("v_grcode : '" + v_grcode + "'");
System.out.println("v_subj : '" + v_subj + "'");
System.out.println("v_year : '" + v_year + "'");
System.out.println("v_subjseq : '" + v_subjseq + "'");
System.out.println("v_menu : '" + v_menu + "'");
			 */
			if(!v_grcode.equals("") && !v_subj.equals("") && !v_year.equals("") && !v_subjseq.equals("") && !v_menu.equals("")){
				//메뉴가 정상적으로 등록되지 않았을 경우
				connMgr = new DBConnectionManager();
				connMgr.setAutoCommit(false);////

				sql1  = " select count(*) cnt ";
				sql1 += " from TZ_STUDYCOUNT   ";
				sql1 += " where grcode = "+ StringManager.makeSQL(v_grcode);
				sql1 += "   and subj  = "+ StringManager.makeSQL(v_subj);
				sql1 += "   and year  = "+ StringManager.makeSQL(v_year);
				sql1 += "   and subjseq  = "+ StringManager.makeSQL(v_subjseq);
				sql1 += "   and menu = "+ StringManager.makeSQL(v_menu);
				sql1 += "   and userid = "+ StringManager.makeSQL(s_userid);

				ls = connMgr.executeQuery(sql1);

				if (ls.next()) {
					cnt = ls.getInt("cnt");
				}

				if (cnt > 0) {                         // update
					sql2  = " update TZ_STUDYCOUNT set cnt = cnt + 1                                 ";
					sql2 += " where grcode    = ?  and subj = ?  and year = ? and subjseq = ? and menu = ? and userid = ? ";
					pstmt = connMgr.prepareStatement(sql2);

					pstmt.setString(1,  v_grcode);
					pstmt.setString(2,  v_subj);
					pstmt.setString(3,  v_year);
					pstmt.setString(4,  v_subjseq);
					pstmt.setString(5,  v_menu);
					pstmt.setString(6,  s_userid);

				}else {                                // insert
					sql3  = " insert into TZ_STUDYCOUNT(grcode, subj, year, subjseq, menu, userid, cnt) ";
					sql3 += " values (?, ?, ?, ?, ?, ?, ?       )                                       ";
					pstmt = connMgr.prepareStatement(sql3);

					pstmt.setString(1,  v_grcode);
					pstmt.setString(2,  v_subj);
					pstmt.setString(3,  v_year);
					pstmt.setString(4,  v_subjseq);
					pstmt.setString(5,  v_menu);
					pstmt.setString(6,  s_userid);
					pstmt.setInt(7,  1);
				}
				is_Ok = pstmt.executeUpdate();
				if(is_Ok == 1 ) {
					connMgr.commit();
				} else {
					connMgr.rollback();
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return is_Ok;
	}



	/**
	 * My Activity
	 * @param  box          receive from the form object and session
	 * @return result       My Activity
	 * @throws Exception
	 */
	public ArrayList SelectActivityList(RequestBox box) throws Exception   {
		DBConnectionManager connMgr = null;
		ArrayList list1     = null;
		ListSet ls = null;
		DataBox dbox = null;
		String sql    = "";

		String v_grcode = box.getSession("tem_grcode");
		String v_subj = box.getString("subj");
		String v_year = box.getString("year");
		String v_subjseq = box.getString("subjseq");
		String s_userid = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = "select b.menu, b.menunm, NVL(a.cnt,0) cnt from ";
			sql += "    (select grcode, subj, year, subjseq, menu, sum(cnt) cnt from TZ_STUDYCOUNT";
			sql += "        where grcode    ="+ StringManager.makeSQL(v_grcode);
			sql += "        and subj        ="+ StringManager.makeSQL(v_subj);
			sql += "        and year        ="+ StringManager.makeSQL(v_year);
			sql += "        and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql += "        and userid      ="+ StringManager.makeSQL(s_userid);
			sql += "        group by grcode, subj, year, subjseq, menu, userid ) a, TZ_MFSUBJ b ";
			sql += " where a.subj(+)= b.subj and a.menu(+)=b.menu and b.subj="+StringManager.makeSQL(v_subj)+" and b.pgmtype='servlet'";
			sql += " order by b.orders";
			System.out.println("SelectActivityList.sql = " + sql);
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list1.add(dbox);
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
		return list1;
	}


	/*학습시간 조회 리스트
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectLearningTime(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls          = null;
		ArrayList list1     = null;
		DataBox dbox = null;
		String  sql        = "";

		String  v_subj      = box.getString("subj");        //과정
		String  v_subjseq   = box.getString("subjseq"); //과정 차수
		String  v_year      = box.getString("year");    //년도
		String  s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = "select min(A.first_edu) first_edu,\n";
			sql+= "  max(A.first_end) first_end,\n";
			sql+= "  sum(lesson_count) lesson_count,\n";
			sql+= " (\n";
			sql+= "     select tstep from TZ_STUDENT\n";
			sql+= "     where subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "\n         and year        ="+ StringManager.makeSQL(v_year);
			sql+= "\n         and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql+= "\n         and userid      ="+ StringManager.makeSQL(s_userid);
			sql+= "\n ) tstep,\n";
			sql+="   FLOOR( ( sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))) *60*60 + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2)))*60 + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2))) ) / (60*60)) total_time,\n";
			sql+="   FLOOR(mod( ( (sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60) + sum( to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2))*60) + sum(to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2))) )/60 ) , 60) ) total_minute,\n";
			sql+="   mod(( sum(to_number(SUBSTR(A.total_time, 1, INSTR(A.total_time, ':')-1))*60*60 + to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2))*60 + to_number(SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2)))) , 60) total_sec\n";
			sql+= " from TZ_PROGRESS A\n";
			sql+= " where subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "\n     and year        ="+ StringManager.makeSQL(v_year);
			sql+= "\n     and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql+= "\n     and userid      ="+ StringManager.makeSQL(s_userid);
			sql+= "\n group by subj, year, subjseq, userid";
			///SUBSTR(A.total_time,1,2) //SUBSTR(A.total_time,4,2)//SUBSTR(A.total_time, INSTR(A.total_time, ':')+1, 2) // SUBSTR(A.total_time,7,2)//SUBSTR(A.total_time, INSTR(A.total_time, ':')+4, 2)
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list1.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

	/*최근 학습일
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public String selectStudyDate(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls          = null;
		String  sql        = "";
		String result = "";

		String  v_subj      = box.getString("subj");        //과정
		String  v_subjseq   = box.getString("subjseq"); //과정 차수
		String  v_year      = box.getString("year");    //년도
		String  s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql+= " select ldate from TZ_SUBJLOGINID                ";
			sql+= "  where subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "    and year        ="+ StringManager.makeSQL(v_year);
			sql+= "    and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql+= "    and userid      ="+ StringManager.makeSQL(s_userid);
			sql+= "  order by ldate desc    ";
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				result = ls.getString("ldate");
				if (result.length() >=12) {
					result = FormatDate.getFormatDate(result,"yyyy.MM.dd HH:mm");
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/*강의실 접근횟수
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public String selectStudyCount(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls          = null;
		String  sql        = "";
		String result = "";

		String  v_subj      = box.getString("subj");        //과정
		String  v_subjseq   = box.getString("subjseq"); //과정 차수
		String  v_year      = box.getString("year");    //년도
		String  s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql+= " select count(*) cnt from TZ_SUBJLOGINID                ";
			sql+= " where subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "     and year        ="+ StringManager.makeSQL(v_year);
			sql+= "     and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql+= "     and userid      ="+ StringManager.makeSQL(s_userid);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				result = String.valueOf(ls.getInt("cnt"));
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/*진도율
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public String selectStudyStep(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls          = null;
		String  sql        = "";
		String result = "";

		String  v_subj      = box.getString("subj");        //과정
		String  v_subjseq   = box.getString("subjseq"); //과정 차수
		String  v_year      = box.getString("year");    //년도
		String  s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql+= " select tstep from TZ_STUDENT                                   ";
			sql+= "  where subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "    and year        ="+ StringManager.makeSQL(v_year);
			sql+= "    and subjseq     ="+ StringManager.makeSQL(v_subjseq);
			sql+= "    and userid      ="+ StringManager.makeSQL(s_userid);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				result = String.valueOf(ls.getFloat("tstep"));
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}


	/*게시판 등록수 리스트
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectBoardCnt(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls          = null;
		ArrayList list1     = null;
		DataBox dbox = null;
		String  sql        = "";

		String  v_subj      = box.getString("subj");        //과정
		String  v_subjseq   = box.getString("subjseq"); //과정 차수
		String  v_year      = box.getString("year");    //년도
		String  s_userid    = box.getSession("userid");
		String v_grcode = box.getSession("tem_grcode");

		try {
			connMgr = new DBConnectionManager();
			list1 = new ArrayList();

			sql = "select                                                       ";
			sql+= "  ( select count(b.seq) from TZ_BDS a, TZ_BOARD b            ";
			sql+= "     where a.tabseq = b.tabseq and a.type = 'SB'";
			sql+= "         and a.grcode    ="+ StringManager.makeSQL(v_grcode);
			sql+= "         and a.subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and b.userid    ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) boardcnt,                                                ";
			sql+= "  ( select count(b.seq) from TZ_BDS a, TZ_BOARD b            ";
			sql+= "     where a.tabseq = b.tabseq and a.type = 'SD'";
			sql+= "         and a.grcode    ="+ StringManager.makeSQL(v_grcode);
			sql+= "         and a.subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and b.userid    ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) datacnt,                                                 ";
			sql+= "  ( select count(a.seq) from TZ_QNA a                        ";
			sql+= "     where kind = '0'                                        ";
			sql+= "         and a.subj      ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and a.inuserid  ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) qnacnt,                                                  ";
			sql+= "  ( select count(a.userid) from TZ_EXAMRESULT a          ";
			sql+= "     where a.subj        ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and a.userid    ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) examcnt,                                                 ";
			sql+= "  ( select count(a.userid) from TZ_SULEACH a             ";
			sql+= "     where a.subj        ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.grcode    ="+ StringManager.makeSQL(v_grcode);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and a.userid    ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) sulcnt,                                                  ";
			sql+= "  ( select max(a.indate) from TZ_PROJREP a                   ";
			sql+= "     where a.subj        ="+ StringManager.makeSQL(v_subj);
			sql+= "         and a.year      ="+ StringManager.makeSQL(v_year);
			sql+= "         and a.subjseq   ="+ StringManager.makeSQL(v_subjseq);
			sql+= "         and a.projid    ="+ StringManager.makeSQL(s_userid);
			sql+= "  ) reportindate";
			sql+= " from dual ";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list1.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}


}