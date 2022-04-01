<%
//**********************************************************
//  1. 제      목: 나의강의실 가기
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.10 이나연 
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.common.*" %>
<%

    String  my_userid    = box.getSession("userid");
    String  my_name      = box.getSession("name");

    String  my_subj          = box.getString("subj");
    String  my_subjnm        = "";
    String  my_year          = box.getString("year");
    String  my_subjseq       = box.getString("subjseq");

    String my_eduurl  = "";

	String s_grcode = box.getSession("tem_grcode");
	String s_grtype = GetCodenm.get_grtype(box,s_grcode);

%>

<Script language="javascript">

function studyOpenMenu() {
<%      if (box.getSession("gadmin").equals("ZZ")) {  %>	
	var url = document.myform1.p_course.value;
    open_window('openApp',url,0,0,1024,768,false,false,'yes','yes','yes');
<%      } else {                                      %>
            alert ("학습자 권한이 아닙니다.");
<%      }                                             %>
}

//-->
</Script>

<form name="myform1" method="post" action="">
	<input type="hidden" name="p_process" value="">
                  
                  <table width="195" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td align="center"> 
                        <select name="p_course" style="width:150;" onchange="javascript:studyOpenMenu();" >
                            <option >:: 강의실 바로가기 :: </option>
                            <%=(new MyClassBean().selectMyCourseMenu(box))%>
                        </select>                     
                      </td>
                    </tr>
                  </table>
                  <!-- 커뮤니티바로가기 -->
                  <table width="195" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td align="center"> 
                        <select name="p_static_cmuno" onchange="javascript:uf_moveMyCommunity();"  style="width:150">
                            <option >:: 커뮤니티 바로가기 :: </option>
                            <%=(new CommunityIndexBean()).selectMyCuminity(my_userid, box.getString("p_static_cmuno"), s_grtype)%>
                        </select> 
                      </td>
                    </tr>
                  </table>
</form>

<SCRIPT LANGUAGE="JavaScript">
<!--

//개인커뮤니티 홈으로 이동
function uf_moveMyCommunity() {

   var url = "/servlet/controller.community.CommunityIndexServlet?p_process=selectmyindex&p_cmuno="+document.myform1.p_static_cmuno.value+"&p_static_cmuno="+document.myform1.p_static_cmuno.value;

   var v_win = window.open(url, "selfTest1","toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");

   //document.myform1.p_process.value = "selectmyindex";
   document.myform1.submit();

}


//-->
</SCRIPT>   

