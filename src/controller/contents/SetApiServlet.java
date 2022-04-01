
//**********************************************************
//  1. 제      목: SCO Object SETAPI SERVLET
//  2. 프로그램명: SETAPISERVLET.java
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
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DBConnectionManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
@WebServlet("/servlet/controller.contents.SetApiServlet")
public class SetApiServlet extends HttpServlet
{

    public SetApiServlet()
    {
    }

    public void init(ServletConfig config)
        throws ServletException
    {
        super.init(config);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException
    {
        res.setContentType("text/html");
        String strCourse_children = req.getParameter("course_children");    //  필수항목

		Log.sys.println(strCourse_children);
        String strCourse_info_sco = req.getParameter("course_info_sco");
        String strLecture_info_detail = req.getParameter("lecture_info_detail");
        String strUser_sco_info = req.getParameter("user_sco_info");    //  필수항목
		Log.sys.println(strUser_sco_info);
        String strStudent_preference = req.getParameter("student_preference");    //  필수항목
        String strObjectives = req.getParameter("objectives");
        String strInteractions = req.getParameter("interactions");
        String strInteractions_objectives = req.getParameter("interactions_objectives");
        String strInteractions_correct_responses = req.getParameter("interactions_correct_responses");
        String strError_info = req.getParameter("error_info");    //  필수항목
        String strDiagnostic_info = req.getParameter("diagnostic_info");    //  필수항목
        String strMember_info = req.getParameter("member_info");    //  필수항목
        String strError_init = req.getParameter("error_init");
        String strRecord_count = req.getParameter("record_count");    //  필수항목
        String course_children[] = setArray(strCourse_children);
        String course_info_sco[] = setArray(strCourse_info_sco);
        String lecture_info_detail[] = setArray(strLecture_info_detail);
        String user_sco_info[] = setArray(strUser_sco_info);
        String student_preference[] = setArray(strStudent_preference);
        String objectives[][] = setMultiArray(strObjectives, 7);
        String interactions[][] = setMultiArray(strInteractions, 9);
        String interactions_objectives[][] = setMultiArray(strInteractions_objectives, 4);
        String interactions_correct_responses[][] = setMultiArray(strInteractions_correct_responses, 4);
        String error_info[][] = setMultiArray(strError_info, 2);
        String diagnostic_info[][] = setMultiArray(strDiagnostic_info, 2);
        String member_info[] = setArray(strMember_info);
        String error_init[] = setArray(strError_init);
        String record_count[] = setArray(strRecord_count);
        String userid = member_info[1];
        String user_name = member_info[2];
        String p_oid = member_info[3];
        String Commit_value = member_info[0];
		String p_subj = member_info[4];
		String p_year = member_info[5];
		String p_subjseq = member_info[6];
		String p_lesson = member_info[7];
		String gojindo = member_info[8];

        int nObjCount = Integer.parseInt(record_count[0]);
        int nInteractCount = Integer.parseInt(record_count[1]);
        int nInteractObjCount = Integer.parseInt(record_count[2]);
        int ninteractCorResCount = Integer.parseInt(record_count[3]);
        int nErrorCount = Integer.parseInt(record_count[4]);
        int nDiagnosticCount = Integer.parseInt(record_count[5]);
        int IdxNo = 1;
        String sResult = "true";
        String currentErrorCode = "0";
		String first_end="";
        boolean exit = false;
		String sql  = "";
        String v_totaltime = "";
		int total_count = 0;
		int jindo_count = 0;
		String jindo_pct = "";
		int jindo_pre_count = 0;
        
		DBConnectionManager connMgr = null;
        ListSet ls = null, ls2=null ;
		PreparedStatement pstmt = null;  

        try
        {   connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            sResult = "true";

			Log.sys.println("SetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApi");


			if (gojindo.equals("1")) { // 일 진도제한에 걸리지 않는다면 진도체크한다.
			  
				sql = " update   tz_object  set datafromlms =  ?  where oid = ?  ";  
				
				pstmt = connMgr.prepareStatement(sql);
				pstmt.setString(1,course_info_sco[12]);
				pstmt.setString(2, p_oid);

				pstmt.executeUpdate();
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }

				sql = " update  tz_subjobj  set commentsfromlms = ? where subj = ? and oid = ? ";   
				

				pstmt = connMgr.prepareStatement(sql);
				pstmt.setString(1, lecture_info_detail[7]);
				pstmt.setString(2, p_subj);
				pstmt.setString(3, p_oid);

				pstmt.executeUpdate();
				if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
				

				String strJindoCk = "incomplete";
				if(user_sco_info[5].trim().equals("passed") || user_sco_info[5].trim().equals("failed") || user_sco_info[5].trim().equals("completed") || user_sco_info[5].trim().equals("browsed"))
				strJindoCk = "complete";

				Log.sys.println("strJindoCk="+strJindoCk);
				Log.sys.println("strJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCk="+strJindoCk);

				v_totaltime = user_sco_info[10].substring(user_sco_info[10].length()-11);

				if (strJindoCk.equals("complete")) {


					sql = " select count(*) jindo_pre_count from tz_progress "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' and lessonstatus ='complete'  ";		

					ls = connMgr.executeQuery(sql);
					if(ls.next())
					{
						jindo_pre_count = ls.getInt("jindo_pre_count");
					}
					if(ls != null) { try { ls.close(); }catch (Exception e1) {} }
				    
					if (jindo_pre_count == 0)
					{
						sql = "update tz_progress set lessonstatus = '" + strJindoCk + "'  , ldate =  to_char(sysdate,'YYYYMMDDHH24MISS')  "
						+ " , first_end = to_char(sysdate,'YYYYMMDDHH24MISS') , session_time = '"+user_sco_info[13]+"' , total_time = '"+v_totaltime+"' "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";		
						Log.sys.println(sql);
					} else {
						sql = "update tz_progress set  session_time = '"+user_sco_info[13]+"' , total_time = '"+v_totaltime+"' "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";		
						Log.sys.println(sql);
					}
						
						connMgr.executeQuery(sql);	


					sql = " select count(*) as total_count from tz_object a, tz_subjobj b "
						+ " where  a.oid = b.oid and b.SUBJ = '" + p_subj + "' and trim(starting) is not null ";

					ls = connMgr.executeQuery(sql);
					if(ls.next())
					{
						total_count = ls.getInt("total_count");
					}
					if(ls != null) { try { ls.close(); }catch (Exception e1) {} }
					
					sql = " select count(*) jindo_count from tz_progress "
						+ " where subj= '" + p_subj + "'  and userid ='" + userid + "'  and lessonstatus ='complete' ";

					ls = connMgr.executeQuery(sql);
					if(ls.next())
					{
						jindo_count = ls.getInt("jindo_count");
					}
					 double d = (double)Math.round((double)jindo_count/total_count*100*100)/100;

					 Log.sys.println( "jindo_count="+jindo_count+" total_count="+total_count);                 
					 DecimalFormat df = new DecimalFormat("###.#"); 
					 df.format(d);
					
					if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

					sql = "update tz_student set tstep = " + d + " where subj = '" + p_subj + "' and  userid = '" + userid + "' ";                
					Log.sys.println(sql);
					connMgr.executeQuery(sql);	

					
				} else {

					Log.sys.println("strJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCkstrJindoCk="+strJindoCk);
					
					sql = "update tz_progress set lessonstatus = '"+strJindoCk+"'   "
						+ " ,  session_time = '"+user_sco_info[13]+"', total_time = '"+v_totaltime+"' "
						+ " where subjseq = '"+p_subjseq+"' and lesson = '"+p_lesson+"'  and oid = '"+p_oid+"' "
						+ " and subj = '"+p_subj+"' and year ='"+p_year+"' and userid = '"+userid+"' ";
				   
				  
					Log.sys.println(sql);
					connMgr.executeQuery(sql);	

			
						
				}	
				
				sql = " select oid from tz_user_scoinfo "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
						
				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{
					
					sql = "update tz_user_scoinfo set "
						+ " core_lesson_location = ? ,"
						+ " core_credit = ? , "
						+ " core_lesson_status = ? ,  "
						+ " core_entry = ? ,  "
						+ " core_score_raw =  ? , "
						+ " core_score_max = ? ,  "
						+ " core_score_min = ? ,  "
						+ " core_total_time = ? ,  "
						+ " core_lesson_mode = ? ,  "
						+ " core_exit = ? ,  "
						+ " core_session_time = ? ,  "
						+ " suspend_data = ? ,  "
						+ " Comments = ?   "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? ";	
						
						
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, user_sco_info[3]);
						pstmt.setString(2, user_sco_info[4]);
						pstmt.setString(3, user_sco_info[5]);
						pstmt.setString(4, user_sco_info[6]);
						pstmt.setString(5, user_sco_info[7]);
						pstmt.setString(6, user_sco_info[8]);
						pstmt.setString(7, user_sco_info[9]);
						pstmt.setString(8, user_sco_info[10]);
						pstmt.setString(9, user_sco_info[11]);
						pstmt.setString(10, user_sco_info[12]);
						pstmt.setString(11, user_sco_info[13]);
						pstmt.setString(12, user_sco_info[14]);
						pstmt.setString(13, user_sco_info[15]);
						pstmt.setString(14, p_subjseq);
						pstmt.setString(15, p_lesson);
						pstmt.setString(16, p_oid);
						pstmt.setString(17, p_subj);
						pstmt.setString(18, p_year);
						pstmt.setString(19, userid);

						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
					
				} else {
					
					 sql = " insert into tz_user_scoinfo ( "
						+ " subj, year, userid, subjseq, lesson, oid , core_lesson_location, core_credit, core_lesson_status, "
						+ " core_entry, core_score_raw, core_score_max, core_score_min, core_total_time, core_lesson_mode, "
						+ " core_exit, core_session_time, suspend_data, Comments ) values ( "
						+ " ?, ?, ?, ?, ?,?,?,? ,? ,? ,? ,? ,? ,? ,? ,? , ? ,?,? )";

						
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setString(7, user_sco_info[3]);
						pstmt.setString(8, user_sco_info[4]);
						pstmt.setString(9, user_sco_info[5]);
						pstmt.setString(10, user_sco_info[6]);
						pstmt.setString(11, user_sco_info[7]);
						pstmt.setString(12, user_sco_info[8]);
						pstmt.setString(13, user_sco_info[9]);
						pstmt.setString(14, user_sco_info[10]);
						pstmt.setString(15, user_sco_info[11]);
						pstmt.setString(16, user_sco_info[12]);
						pstmt.setString(17, user_sco_info[13]);
						pstmt.setString(18, user_sco_info[14]);
						pstmt.setString(19, user_sco_info[15]);

						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }					
				}


				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				sql = "select oid from tz_student_pre "
				+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
				+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' ";
							
				ls = connMgr.executeQuery(sql);
				if(ls.next())
				{
					sql = "update tz_student_pre set "
						+ " student_preference_audio = ?,"
						+ " student_preference_language = ? , "
						+ " student_preference_speed = ?,  "
						+ " student_preference_text = ?   "								
						+ " where subjseq =? and lesson = ? and oid = ? "
						+ " and subj = ? and year =? and userid = ? ";					
						
					   
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, student_preference[1]);
						pstmt.setString(2, student_preference[2]);
						pstmt.setString(3, student_preference[3]);
						pstmt.setString(4, student_preference[4]);
						pstmt.setString(5, p_subjseq);
						pstmt.setString(6, p_lesson);
						pstmt.setString(7, p_oid);
						pstmt.setString(8, p_subj);
						pstmt.setString(9, p_year);
						pstmt.setString(10, userid);
						
						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				} else {
					sql = " insert into tz_student_pre ( "
						+ " subj, year, userid, subjseq, lesson, oid , student_preference_audio, student_preference_language,  "
						+ " student_preference_speed, student_preference_text ) values ( "
						+ " ?, ?, ?, ?, ?, ?,  ? , ? , ? , ? )";
											
						pstmt = connMgr.prepareStatement(sql);
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setString(7, student_preference[1]);
						pstmt.setString(8, student_preference[2]);
						pstmt.setString(9, student_preference[3]);
						pstmt.setString(10, student_preference[4]);
						
						pstmt.executeUpdate();
						if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				}

				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				for(int i = 0; i < nObjCount; i++)
				{
					sql = " select oid from tz_objectives "
						+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
						+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
						+ " and objectives_num = "+ Integer.parseInt(objectives[i][1]) +" ";
				
					ls = connMgr.executeQuery(sql);

				sql = "update tz_objectives set "
						+ " objectives_id = ? ,"
						+ " objectives_score_row = ? , "
						+ " objectives_score_max = ? ,  "
						+ " objectives_score_min = ? ,  "		
						+ " objectives_status = ?  "	
						+ " where subjseq = ? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and objectives_num = ? "; 

				pstmt = connMgr.prepareStatement(sql);
		
				for(exit = false; ls.next(); exit = true)
					{
						pstmt.setString(1, objectives[i][2]);
						pstmt.setString(2, objectives[i][3]);
						pstmt.setString(3, objectives[i][4]);
						pstmt.setString(4, objectives[i][5]);
						pstmt.setString(5, objectives[i][6]);
						pstmt.setString(6, p_subjseq);
						pstmt.setString(7, p_lesson);
						pstmt.setString(8, p_oid);
						pstmt.setString(9, p_subj);
						pstmt.setString(10, p_year);
						pstmt.setString(11, userid);
						pstmt.setInt(12, Integer.parseInt(objectives[i][1]));
						
						pstmt.executeUpdate();														
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				
					sql = " insert into tz_objectives ( "
						+ " subj, year, userid, subjseq, lesson, oid , objectives_num,  objectives_id, objectives_score_row,  "
						+ " objectives_score_max, objectives_score_min, objectives_status ) values ( "
						+ " ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?  )";
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{ 	
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(objectives[i][1]));
						pstmt.setString(8, objectives[i][2]);
						pstmt.setString(9, objectives[i][3]);
						pstmt.setString(10, objectives[i][4]);
						pstmt.setString(11, objectives[i][5]);
						pstmt.setString(12, objectives[i][6]);
						
						pstmt.executeUpdate();																
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }					
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} } 

				for(int i = 0; i < nInteractCount; i++)
				{
					sql = "select oid from tz_interactions "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions[i][1]) +" "; 			
				
					ls = connMgr.executeQuery(sql);	
					
					sql = "update tz_interactions set "
						+ " interactions_id = ? ,"
						+ " interactions_time = ? , "
						+ " interactions_type = ? ,  "
						+ " interactions_weighting= ? ,  "		
						+ " interactions_student_response = ? ,  "	
						+ " interactions_result = ? ,  "	
						+ " interactions_latency = ?  "	
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and interactions_num = ? "; 
						
					pstmt = connMgr.prepareStatement(sql);
					
					for(exit = false; ls.next(); exit = true)
					{
						pstmt.setString(1, interactions[i][2]);
						pstmt.setString(2, interactions[i][3]);
						pstmt.setString(3, interactions[i][4]);
						pstmt.setString(4, interactions[i][5]);
						pstmt.setString(5, interactions[i][6]);
						pstmt.setString(6, interactions[i][7]);
						pstmt.setString(7, interactions[i][8]);
						pstmt.setString(8, p_subjseq);
						pstmt.setString(9, p_lesson);
						pstmt.setString(10, p_oid);
						pstmt.setString(11, p_subj);
						pstmt.setString(12, p_year);
						pstmt.setString(13, userid);
						pstmt.setInt(14, Integer.parseInt(interactions[i][1]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_interactions ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num , interactions_id, interactions_time,  "
						+ " interactions_type, interactions_weighting, interactions_student_response,interactions_result, interactions_latency ) values ( "
						+ " ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?, ?, ?, ? )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{					
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(interactions[i][1]));
						pstmt.setString(8, interactions[i][2]);
						pstmt.setString(9, interactions[i][3]);
						pstmt.setString(10, interactions[i][4]);
						pstmt.setString(11, interactions[i][5]);
						pstmt.setString(12, interactions[i][6]);
						pstmt.setString(13, interactions[i][7]);
						pstmt.setString(14, interactions[i][8]);

						pstmt.executeUpdate();						
					} 
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }		
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				for(int i = 0; i < nInteractObjCount; i++)
				{
					sql = "select oid from tz_inter_obj "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions_objectives[i][1])+"  and  interactions_objectives_num = "+ Integer.parseInt(interactions_objectives[i][2]) +" "; 			
				
					ls = connMgr.executeQuery(sql);

					sql = "update tz_inter_obj set "
						+ " interactions_objectives_id = ? "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year =? and userid = ? "
						+ " and interactions_num = ?  and  interactions_objectives_num = ? "; 			
						
					pstmt = connMgr.prepareStatement(sql);

					for(exit = false; ls.next(); exit = true)
					{	
						pstmt.setString(1, interactions_objectives[i][3]);					
						pstmt.setString(2, p_subjseq);
						pstmt.setString(3, p_lesson);
						pstmt.setString(4, p_oid);
						pstmt.setString(5, p_subj);
						pstmt.setString(6, p_year);
						pstmt.setString(7, userid);
						pstmt.setInt(8, Integer.parseInt(interactions_objectives[i][1]));
						pstmt.setInt(9, Integer.parseInt(interactions_objectives[i][2]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_inter_obj ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num, interactions_objectives_num,  "
						+ " interactions_objectives_id ) values ( "
						+ " '" + p_subj + "', '" + p_year + "', '" + userid + "', '" + p_subjseq + "', '" + p_lesson + "', '" + p_oid + "', "
						+ " "+ Integer.parseInt(interactions_objectives[i][1]) + ",  "+ Integer.parseInt(interactions_objectives[i][2]) +" , '"+interactions_objectives[i][3]+"' )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{
						pstmt.setString(1, p_subj);
						pstmt.setString(2, p_year);
						pstmt.setString(3, userid);
						pstmt.setString(4, p_subjseq);
						pstmt.setString(5, p_lesson);
						pstmt.setString(6, p_oid);
						pstmt.setInt(7, Integer.parseInt(interactions_objectives[i][1]));
						pstmt.setInt(8, Integer.parseInt(interactions_objectives[i][2]));
						pstmt.setString(9, interactions_objectives[i][3]);					

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }			
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				for(int i = 0; i < ninteractCorResCount; i++)
				{
					sql = "select oid from tz_inter_cor "
					+ " where subjseq ='" + p_subjseq + "' and lesson = '" + p_lesson + "'  and oid = '" + p_oid + "' "
					+ " and subj = '" + p_subj + "' and year ='" + p_year + "' and userid = '" + userid + "' "
					+ " and interactions_num = "+ Integer.parseInt(interactions_correct_responses[i][1])+"  and  nteractions_cor_res_num = "+ Integer.parseInt(interactions_correct_responses[i][2]) +" "; 			
				
					ls = connMgr.executeQuery(sql);

					sql = "update tz_inter_cor set "
						+ " interactions_cor_res_pattern = ? "					
						+ " where subjseq =? and lesson = ?  and oid = ? "
						+ " and subj = ? and year = ? and userid = ? "
						+ " and interactions_num = ?  and  nteractions_cor_res_num = ? "; 			
						
					pstmt = connMgr.prepareStatement(sql);

					for(exit = false; ls.next(); exit = true)
					{					
						pstmt.setString(1, interactions_objectives[i][3]);					
						pstmt.setString(2, p_subjseq);
						pstmt.setString(3, p_lesson);
						pstmt.setString(4, p_oid);
						pstmt.setString(5, p_subj);
						pstmt.setString(6, p_year);
						pstmt.setString(7, userid);
						pstmt.setInt(8, Integer.parseInt(interactions_correct_responses[i][1]));
						pstmt.setInt(9, Integer.parseInt(interactions_correct_responses[i][2]));

						pstmt.executeUpdate();					
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }	

					sql = " insert into tz_inter_cor ( "
						+ " subj, year, userid, subjseq, lesson, oid , interactions_num, interactions_cor_res_num,  "
						+ " interactions_cor_res_pattern ) values ( "
						+ " '" + p_subj + "', '" + p_year + "', '" + userid + "', '" + p_subjseq + "', '" + p_lesson + "', '" + p_oid + "', "
						+ " "+ Integer.parseInt(interactions_correct_responses[i][1]) + ",  "+ Integer.parseInt(interactions_correct_responses[i][2]) +" , '"+interactions_correct_responses[i][3]+"' )";
						
					pstmt = connMgr.prepareStatement(sql);

					if(!exit)
					{					
						pstmt.setString(1, p_subjseq);
						pstmt.setString(2, p_lesson);
						pstmt.setString(3, p_oid);
						pstmt.setString(4, p_subj);
						pstmt.setString(5, p_year);
						pstmt.setString(6, userid);
						pstmt.setInt(7, Integer.parseInt(interactions_correct_responses[i][1]));
						pstmt.setInt(8, Integer.parseInt(interactions_correct_responses[i][2]));
						pstmt.setString(9, interactions_objectives[i][3]);					

						pstmt.executeUpdate();						
					}
					if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }		
				}
				if(ls != null) { try { ls.close(); }catch (Exception e1) {} }

				Log.sys.println("SetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApiSetApi");

				connMgr.commit();
			}

			res.setContentType("text/html");
            res.setLocale(Locale.KOREA);
            PrintWriter out = res.getWriter();
            out.println("<script>");
             for(int i = 0; i < member_info.length; i++)
                out.println("parent.member_info[" + i + "] = '" + member_info[i] + "'");
            out.println("</script>");
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

    public String[] setArray(String strArray)
    {
        StringTokenizer eleTok = new StringTokenizer(strArray, "|", false);
        int eleTokens = eleTok.countTokens();
        String ele_name[] = new String[eleTokens];
        for(int i = 0; i < eleTokens; i++)
        {
            ele_name[i] = eleTok.nextToken();
            if(i != 0)
                ele_name[i] = ele_name[i].substring(1, ele_name[i].length());
        }

        return ele_name;
    }

    public String[][] setMultiArray(String strArray, int intArrayNum)
    {
        StringTokenizer eleTok = new StringTokenizer(strArray, "|", false);
        int eleTokens = eleTok.countTokens();
        String ele_name[] = new String[eleTokens];
        String return_name[][] = new String[eleTokens][intArrayNum];
        for(int i = 0; i < eleTokens; i++)
        {
            ele_name[i] = eleTok.nextToken();
            if(i != 0)
                ele_name[i] = ele_name[i].substring(1, ele_name[i].length());
            StringTokenizer eleTok1 = new StringTokenizer(ele_name[i], "^", false);
            int eleTokens1 = eleTok1.countTokens();
            String ele_name1[] = new String[eleTokens1];
            for(int j = 0; j < eleTokens1; j++)
            {
                ele_name1[j] = eleTok1.nextToken();
                if(j != 0)
                    return_name[i][j] = ele_name1[j].substring(1, ele_name1[j].length());
            }
        }
        return return_name;
    }
}
