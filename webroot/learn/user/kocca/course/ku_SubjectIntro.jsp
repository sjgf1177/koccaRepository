<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_SubjectIntro.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");
    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
   
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  >
    <input type='hidden' name='p_subj'     value="">

	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td  class="location" > HOME > 수강신청 > 수강신청 취소/안내</td>
	  </tr>
	</table>
	<table width="680" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td><img src="/images/user/kocca/apply/type1/tit_guide.gif"></td>
	  </tr>
	  <tr> 
		<td height ="250" ></td>
	  </tr>
	</table>


</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
