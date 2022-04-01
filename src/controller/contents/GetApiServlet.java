//**********************************************************
//  1. 제      목: SCO Object GETAPI SERVLET
//  2. 프로그램명: GETAPISERVLET.java
//  3. 개      요: SCO관리에 관련된 Servlet
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.2
//  6. 작      성: 김기수 2004. 11.11
//  7. 수      정: 김기수 2004. 11.11
//**********************************************************

package controller.contents;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DBConnectionManager;
import com.credu.library.ListSet;


@WebServlet("/servlet/controller.contents.GetApiServlet")
public class GetApiServlet extends HttpServlet
{

    public GetApiServlet()
    {
    }

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

	

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
		
        res.setContentType("text/html");
        String userid = null;
        String user_name = null;
        String p_oid = null;
        String commit_value = null;
        String course_children[] = new String[7];
        String course_info_sco[] = new String[13];
        String lecture_info_detail[] = new String[9];
        String user_sco_info[] = new String[16];
        String student_preference[] = new String[5];
		
		String wholetime= String.valueOf(new Date().getTime());	
		
		System.out.println("wholetime : " + wholetime);


  /*    String objectives[][] = new String[50][7];
        String interactions[][] = new String[50][9];
        String interactions_objectives[][] = new String[50][4];
        String interactions_correct_responses[][] = new String[50][4];       
        String diagnostic_info[][] = new String[11][2]; */

        String member_info[] = new String[10];
        String error_init[] = new String[2]; 
    	String error_info[][] = new String[11][2];
        int record_count[] = new int[6];
        String sResult = "true";
        String currentErrorCode = "0";
        boolean exit = false;
		boolean gojindo = false;
		int nlimit = 0;
        int nObjCount = 0;
        int nInteractCount = 0;
        int nInteractObjCount = 0;
        int ninteractCorResCount = 0;
        int nErrorCount = 0;
        int nDiagnosticCount = 0;
		int nLimitCount = 0;
		int nPeriod = 0;
		int noid_count=0;
		String  sLimitLesson = "";
		String sql  = "";
		String lessonstatus="";
        
        String blCertyCk = "0";
		String p_subj  = "";
		String	p_year		= req.getParameter("p_year");
		String	p_subjseq	= req.getParameter("p_subjseq");
		String	p_lesson	= req.getParameter("p_lesson");
        member_info[0] = req.getParameter("commit_value");
        member_info[1] = req.getParameter("userid");
		member_info[2] = req.getParameter("username");
        member_info[3] = req.getParameter("p_oid");
		member_info[4] = req.getParameter("p_subj");
		member_info[5] = req.getParameter("p_year");
		member_info[6] = req.getParameter("p_subjseq");
		member_info[7] = req.getParameter("p_lesson");
		member_info[9] = wholetime;
		p_subj = req.getParameter("p_subj");
        commit_value = member_info[0];
        userid = member_info[1];
        user_name = member_info[2];
        p_oid = member_info[3];

		DBConnectionManager connMgr = null;
        ListSet ls = null, ls2=null;
		PreparedStatement pstmt = null;  		

        try
        {				
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);//// 			
			sql = "select userid , name from TZ_MEMBER where userid = '"+ userid + "' "; 			
			
            ls = connMgr.executeQuery(sql);
			exit = false;
            if(ls.next())
            {
                member_info[1] = ls.getString("userid");				
                member_info[2] = ls.getString("name");
                userid = member_info[1];
                user_name = member_info[2];
                p_oid = member_info[3];
                exit = true;
            }
			if(!exit)
            {
                currentErrorCode = "101";
                sResult = "false";
            }
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			// 1일 학습 제한

			if (gojindo == false) {   // 차시가 오늘 진도 나간거와 틀리다면

				sql = "select nvl(edulimit,0) limit from tz_subjseq  "
					+ " where subj= '" + p_subj + "' "   
					+ "   and year= '" + p_year + "' "
					+ "   and subjseq='" + p_subjseq + "'";

				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
					nlimit = ls.getInt("limit"); // 일일 진도 차수 카운트
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

                 sql = "select count(*) as oid_count from tz_object a, tz_subjobj b where a.oid =b.oid and  "
					 + " b.subj= '" + p_subj + "' and trim(a.starting) is not null "   ;
				
			 	ls = connMgr.executeQuery(sql);
				while (ls.next()) {				
					noid_count = ls.getInt("oid_count");	// 오늘 학습한 차수 카운트					
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }


				sql = "select count(oid) limitcnt  from tz_progress"	
					+ " where subj= '" + p_subj + "' "   
					+ "   and year='" + p_year + "' "
					+ "   and subjseq='" + p_subjseq + "'"
					+ "   and userid= '" + userid + "' "				
					+ "   and substr(first_end,1,8) = to_char(sysdate,'YYYYMMDD') ";

				ls = connMgr.executeQuery(sql);
				while (ls.next()) {				
					nLimitCount = ls.getInt("limitcnt");	// 오늘 학습한 차수 카운트					
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }
                
				double oneday_jindo_pnt = (double)Math.round((double)nLimitCount/noid_count*100*100)/100;
                double nlimit_1 = (double)(nlimit);

				if ( (nlimit_1 >= oneday_jindo_pnt) || (nlimit == 0) ) {  // 진도 제한에 걸리지 않는다면
				    gojindo = true;					
				} 			
			}

			sql = "select lessonstatus from  TZ_PROGRESS "
			+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
			+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";

			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
               lessonstatus = ls.getString("lessonstatus");
			} 

			if (lessonstatus.equals("complete"))
			{
				gojindo = true;	
			}

			// 복습기간 진도 제한, 미리보기 진도 제한

			sql = "select count(*) as periodcnt  from tz_subjseq"	
				+ " where subj= '" + p_subj + "' "   
				+ " and year='" + p_year + "' "
				+ " and subjseq='" + p_subjseq + "'"						
				+ " and to_char(sysdate,'YYYYMMDDHH24') between edustart and eduend ";


			ls = connMgr.executeQuery(sql);
			while (ls.next()) {				
				nPeriod = ls.getInt("periodcnt");									
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			if ( nPeriod == 0 ) { //학습기간이 아니면 진도 체크 안된다.
			    gojindo = false;
			}
			
			//if 복습이면 gojindo = false;
			if(p_year.equals("2000")) {
				gojindo = false;
			}

			//if p_subjseq=0000은 베타테스트  gojindo = true;
			if(p_subjseq.equals("0000")) {
				gojindo = true;
			}

			if (gojindo == true ) {
			    member_info[8] = "1";
			} else {
				member_info[8] = "2";			    
			}

			if (gojindo == true) {
				sql = "select oid from  TZ_PROGRESS "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
           
							
				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{	
					System.out.println("진도 테이블에 이미 있다");	
					sql = "update tz_progress set lesson_count = lesson_count + 1  "						
						+ " where subjseq = '"+p_subjseq+"' and lesson = '"+p_lesson+"'  and oid = '"+p_oid+"' "
						+ " and subj = '"+p_subj+"' and year ='"+p_year+"' and userid = '"+userid+"' ";

						connMgr.executeQuery(sql);
						
				 
				} else {
					sql = "insert into TZ_PROGRESS (subj, year, userid, subjseq, lesson, oid , ldate,  first_edu, lesson_count ) "
						+ " values ( '" + p_subj + "', '" + p_year + "', '" + userid + "', '" + p_subjseq + "', '" + p_lesson + "', '" + p_oid + "', " 
						+ " to_char(sysdate,'YYYYMMDDHH24MISS'), to_char(sysdate,'YYYYMMDDHH24MISS'), 1) ";	

				
					connMgr.executeQuery(sql);	
					
				}
				connMgr.commit();
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }			    
			}			

			sql = "SELECT core_children,core_score_children,objectives_children,objectives_score_children, "
			    + "student_data_children,student_preference_children,interactions_children FROM  TZ_COURSE_CHILDREN" ;
	
				
            ls = connMgr.executeQuery(sql);			
            if(ls.next())
            {
                course_children[0] = ls.getString("core_children");
                course_children[1] = ls.getString("core_score_children");
                course_children[2] = ls.getString("objectives_children");
                course_children[3] = ls.getString("objectives_score_children");
                course_children[4] = ls.getString("student_data_children");
                course_children[5] = ls.getString("student_preference_children");
                course_children[6] = ls.getString("interactions_children");				
            }else
            {
                currentErrorCode = "101";
                sResult = "false";
            }
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = " select oid, highoid, sdesc, starting, prerequisites, maxtimeallowed, timelimitaction, masteryscore, datafromlms "
				+ " from TZ_OBJECT where oid = '" + p_oid + "' " ;
				
            ls = connMgr.executeQuery(sql);			
            if(ls.next())
            {
                course_info_sco[0] = ls.getString("oid");
                course_info_sco[1] = ls.getString("highoid");
                course_info_sco[2] = course_info_sco[0];
                course_info_sco[3] = ls.getString("sdesc");
                course_info_sco[4] = "chasi_depth";
                course_info_sco[5] = "identifier";
                course_info_sco[6] = "identifierref";
                course_info_sco[7] = ls.getString("starting");
                course_info_sco[8] = ls.getString("prerequisites");
                course_info_sco[9] = ls.getString("maxtimeallowed");
                course_info_sco[10] = ls.getString("timelimitaction");
                course_info_sco[11] = ls.getString("masteryscore");	
				course_info_sco[12] = ls.getString("datafromlms");	
            }else
            {
                currentErrorCode = "101";
                sResult = "false";
            }
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = "select commentsfromlms from tz_subjobj TZ_SUBJOBJ "
			    + "where subj = '"+ p_subj +"' and oid = '" + p_oid + "' "; 
				
            ls = connMgr.executeQuery(sql);
			if(ls.next())
            {
				lecture_info_detail[7] = ls.getString("commentsfromlms");
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			

			sql = " SELECT core_lesson_location,core_credit,core_lesson_status,core_entry,core_score_raw, "
			    + " core_score_max,core_score_min,core_total_time,core_lesson_mode,core_exit,core_session_time," 
				+ " suspend_data,Comments from TZ_USER_SCOINFO "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
            

			//System.out.println(sql);
				
				
            ls = connMgr.executeQuery(sql);			
            if(ls.next())
            {
                user_sco_info[0] = course_info_sco[0];
                user_sco_info[1] = "chasi_id";
                user_sco_info[2] = userid;
                user_sco_info[3] = ls.getString("core_lesson_location");
                user_sco_info[4] = ls.getString("core_credit");
                user_sco_info[5] = ls.getString("core_lesson_status");
                user_sco_info[6] = ls.getString("core_entry");
                user_sco_info[7] = ls.getString("core_score_raw");
                user_sco_info[8] = ls.getString("core_score_max");
                user_sco_info[9] = ls.getString("core_score_min");
                user_sco_info[10] = ls.getString("core_total_time");
                user_sco_info[11] = ls.getString("core_lesson_mode");
                user_sco_info[12] = ls.getString("core_exit");
                user_sco_info[13] = ls.getString("core_session_time");
                user_sco_info[14] = ls.getString("suspend_data");
                user_sco_info[15] = ls.getString("Comments");
                if((user_sco_info[11].equals("browse") || user_sco_info[11].equals("normal") || user_sco_info[11].equals("review")) && user_sco_info[11].equals("browse"))
                    user_sco_info[5] = "browsed";
                if(user_sco_info[12] != null)
                {
                    if(user_sco_info[12].trim().equals("suspend"))
                        user_sco_info[6] = "resume";
                    else
                        user_sco_info[6] = "";
                } else
                {
                    user_sco_info[6] = "";
                }				
            }else
            {
                user_sco_info[0] = course_info_sco[0];
                user_sco_info[1] = "chasi_id";
                user_sco_info[2] = userid;
                user_sco_info[3] = "";
                user_sco_info[4] = "no-credit";
                user_sco_info[5] = "not attempted";
                user_sco_info[6] = "ab-initio";
                user_sco_info[7] = "";
                user_sco_info[8] = "";
                user_sco_info[9] = "";
                user_sco_info[10] = "0000:00:00.00";
                user_sco_info[11] = "normal";
                user_sco_info[12] = "";
                user_sco_info[13] = "";
                user_sco_info[14] = "";
                user_sco_info[15] = "";                
            }
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = " SELECT oid,student_preference_audio,student_preference_language,student_preference_speed," 
				+ " student_preference_text  FROM TZ_STUDENT_PRE   "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";

				
				
            ls = connMgr.executeQuery(sql);			
            if(ls.next())
            {
                student_preference[0] = ls.getString("oid");
                student_preference[1] = ls.getString("student_preference_audio");
                student_preference[2] = ls.getString("student_preference_language");
                student_preference[3] = ls.getString("student_preference_speed");
                student_preference[4] = ls.getString("student_preference_text");
            } else
            {
                student_preference[0] = course_info_sco[0];
                student_preference[1] = "0";
                student_preference[2] = "";
                student_preference[3] = "0";
                student_preference[4] = "0";
            }
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

	/*		sql = " SELECT oid , objectives_num,objectives_id,objectives_score_row,objectives_score_max,objectives_score_min,"   
				+ " objectives_status FROM TZ_OBJECTIVES   "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
				+ " ORDER BY objectives_num ";

				
				
            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				objectives[nObjCount][0] = ls.getString("oid");
                objectives[nObjCount][1] = ls.getString("objectives_num");
                objectives[nObjCount][2] = ls.getString("objectives_id");
                objectives[nObjCount][3] = ls.getString("objectives_score_row");
                objectives[nObjCount][4] = ls.getString("objectives_score_max");
                objectives[nObjCount][5] = ls.getString("objectives_score_min");
                objectives[nObjCount][6] = ls.getString("objectives_status");
                nObjCount++;
                record_count[0] = nObjCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = " SELECT oid,interactions_num,interactions_id,interactions_time,interactions_type,interactions_weighting"   
				+ " ,interactions_student_response,interactions_result,interactions_latency FROM TZ_INTERACTIONS  "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
				+ " ORDER BY interactions_num ";

            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				interactions[nInteractCount][0] = ls.getString("obj_code");
                interactions[nInteractCount][1] = ls.getString("interactions_num");
                interactions[nInteractCount][2] = ls.getString("interactions_id");
                interactions[nInteractCount][3] = ls.getString("interactions_time");
                interactions[nInteractCount][4] = ls.getString("interactions_type");
                interactions[nInteractCount][5] = ls.getString("interactions_weighting");
                interactions[nInteractCount][6] = ls.getString("interactions_student_response");
                interactions[nInteractCount][7] = ls.getString("interactions_result");
                interactions[nInteractCount][8] = ls.getString("interactions_latency");
                nInteractCount++;
                record_count[1] = nInteractCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = " SELECT oid,interactions_num,interactions_objectives_num,interactions_objectives_id"   
				+ " FROM  TZ_INTER_OBJ "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
				+ " ORDER BY interactions_num, interactions_objectives_num ";

				
				
            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				interactions_objectives[nInteractObjCount][0] = ls.getString("oid");
                interactions_objectives[nInteractObjCount][1] = ls.getString("interactions_num");
                interactions_objectives[nInteractObjCount][2] = ls.getString("interactions_objectives_num");
                interactions_objectives[nInteractObjCount][3] = ls.getString("interactions_objectives_id");
                nInteractObjCount++;
                record_count[2] = nInteractObjCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = " SELECT oid,interactions_num,interactions_cor_res_num,interactions_cor_res_pattern"   
				+ " FROM TZ_INTER_COR "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
				+ " ORDER BY  interactions_num, interactions_cor_res_num ";
                
				

            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				interactions_correct_responses[ninteractCorResCount][0] = ls.getString("oid");
                interactions_correct_responses[ninteractCorResCount][1] = ls.getString("interactions_num");
                interactions_correct_responses[ninteractCorResCount][2] = ls.getString("interactions_cor_res_num");
                interactions_correct_responses[ninteractCorResCount][3] = ls.getString("interactions_cor_res_pattern");
                ninteractCorResCount++;
                record_count[3] = ninteractCorResCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = "SELECT error_code,error_content FROM TZ_ERROR_INFO ORDER BY error_code ";

            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				error_info[nErrorCount][0] = ls.getString("error_code");
                error_info[nErrorCount][1] = ls.getString("error_content");
                nErrorCount++;
                record_count[4] = nErrorCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			sql = "SELECT error_code,error_content FROM TZ_DIAGNOSTIC_INFO  ORDER BY error_code ";
							
            ls = connMgr.executeQuery(sql);	
			
			while(ls.next()) {
				diagnostic_info[nDiagnosticCount][0] = ls.getString("error_code");
                diagnostic_info[nDiagnosticCount][1] = ls.getString("error_content");
                nDiagnosticCount++;
                record_count[5] = nDiagnosticCount;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

			*/

			record_count[0] = 0;
			record_count[1] = 0;
			record_count[2] = 0;
			record_count[3] = 0;
			record_count[4] = 0;
			record_count[5] = 0;

			

			
			res.setContentType("text/html");
            res.setLocale(Locale.KOREA);
            PrintWriter out = res.getWriter();
            out.println("<script>");
            int i = 0;
            int j = 0;
			for(i = 0; i < member_info.length; i++)
                out.println("parent.member_info[" + i + "] = '" + member_info[i] + "';");

            for(i = 0; i < course_children.length; i++)
                out.println("parent.course_children[" + i + "] = '" + course_children[i] + "';");

            for(i = 0; i < course_info_sco.length; i++)
                out.println("parent.course_info_sco[" + i + "] = \"" + course_info_sco[i] + "\";");

            for(i = 0; i < lecture_info_detail.length; i++)
                out.println("parent.lecture_info_detail[" + i + "] = '" + lecture_info_detail[i] + "';");

            for(i = 0; i < user_sco_info.length; i++)
                out.println("parent.user_sco_info[" + i + "] = '" + user_sco_info[i] + "';");

            for(i = 0; i < student_preference.length; i++)
                out.println("parent.student_preference[" + i + "] = '" + student_preference[i] + "';");

       /*     for(i = 0; i < nObjCount; i++)
            {
                out.println("parent.objectives[" + i + "] = new Array(7)");
                for(j = 0; j < 7; j++)
                    out.println("parent.objectives[" + i + "][" + j + "] = '" + objectives[i][j] + "';");

            }

            for(i = 0; i < nInteractCount; i++)
            {
                out.println("parent.interactions[" + i + "] = new Array(9)");
                for(j = 0; j < 9; j++)
                    out.println("parent.interactions[" + i + "][" + j + "] = '" + interactions[i][j] + "';");

            }

           for(i = 0; i < nInteractObjCount; i++)
            {
                out.println("parent.interactions_objectives[" + i + "] = new Array(4)");
                for(j = 0; j < 4; j++)
                    out.println("parent.interactions_objectives[" + i + "][" + j + "] = '" + interactions_objectives[i][j] + "';");

            }

            for(i = 0; i < ninteractCorResCount; i++)
            {
                out.println("parent.interactions_correct_responses[" + i + "] = new Array(4)");
                for(j = 0; j < 4; j++)
                    out.println("parent.interactions_correct_responses[" + i + "][" + j + "] = '" + interactions_correct_responses[i][j] + "';");

            } */

             for(i = 0; i < nErrorCount; i++)
            {
                out.println("parent.error_info[" + i + "] = new Array(2)");
                for(j = 0; j < 2; j++)
                    out.println("parent.error_info[" + i + "][" + j + "] = '" + error_info[i][j] + "';");

            }

			/*

            for(i = 0; i < nDiagnosticCount; i++)
            {
                out.println("parent.diagnostic_info[" + i + "] = new Array(2)");
                for(j = 0; j < 2; j++)
                    out.println("parent.diagnostic_info[" + i + "][" + j + "] = '" + diagnostic_info[i][j] + "';");

            }*/

            for(i = 0; i < error_init.length; i++)
                out.println("parent.error_init[" + i + "] = '" + error_init[i] + "';");

            
            for(i = 0; i < record_count.length; i++)
                out.println("parent.record_count[" + i + "] = '" + record_count[i] + "';");

           out.println("parent.nObjCount ='" + record_count[0] + "';");
           out.println("parent.nInteractCount ='" + record_count[1] + "';");
           out.println("parent.nInteractObjCount ='" + record_count[2] + "';");
           out.println("parent.ninteractCorResCount ='" + record_count[3] + "';");
           out.println("parent.nErrorCount ='" + record_count[4] + "';");
           out.println("parent.nDiagnosticCount ='" + record_count[5] + "';");


          out.println("</script>");  /*   */
			
connMgr.commit();

        }     
        catch(SQLException e)
        {
            e.printStackTrace();
			if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
        }
		catch(Exception e)
        {
            e.printStackTrace();			
        }        
        finally
        {			
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }    
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            
        }
        return;
    }
}
