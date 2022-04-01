
<%@ page contentType="text/html; charset=euc-kr"  %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>


<%
       String strCourse_children = request.getParameter("course_children");

	   System.out.println("Api_InsertApi_InsertApi_InsertApi_Insert");
       String strCourse_info_sco = request.getParameter("course_info_sco");
       String strLecture_info_detail = request.getParameter("lecture_info_detail");
       String strUser_sco_info = request.getParameter("user_sco_info");

	   StringTokenizer eleTok = new StringTokenizer(strUser_sco_info, "*", false);
        
       
		
    String strStudent_preference = request.getParameter("student_preference");
       String strObjectives = request.getParameter("objectives");
       String strInteractions = request.getParameter("interactions");
       String strInteractions_objectives = request.getParameter("interactions_objectives");
       String strInteractions_correct_responses = request.getParameter("interactions_correct_responses");
       String strDiagnostic_info = request.getParameter("diagnostic_info"); 

       String strError_info = request.getParameter("error_info");
       String strMember_info = request.getParameter("member_info");
       String strError_init = request.getParameter("error_init");
       String strRecord_count = request.getParameter("record_count");

		SCOBean sco = new SCOBean();

	//	Log.sys.println("strStudent_preference="+strStudent_preference);
	//	Log.sys.println("interactions="+strInteractions);
	//	Log.sys.println("strMember_info="+strMember_info);
	//	Log.sys.println("strRecord_count="+strRecord_count);

	//	System.out.println("strStudent_preference="+strStudent_preference);
	//	System.out.println("strMember_info="+strMember_info);


		sco.ScoJindo(strCourse_children, strCourse_info_sco, strLecture_info_detail, strUser_sco_info, strStudent_preference, strObjectives,strInteractions, strInteractions_objectives, strInteractions_correct_responses, strError_info , strDiagnostic_info, strMember_info, strError_init, strRecord_count );


/*sco.ScoJindo(strCourse_children, strCourse_info_sco, strLecture_info_detail, strUser_sco_info,  strError_info , strMember_info, strError_init, strRecord_count );
*/
       
%>