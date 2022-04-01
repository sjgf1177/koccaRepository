<%
//**********************************************************
//  1. 제      목: 서비스 > 구인구직
//  2. 프로그램명 : gu_jobLink.jsp
//  3. 개      요: 서비스 > 구인구직
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
 	box.put("leftmenu","04");                       // 메뉴 아이디 세팅

   
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--


//-->
</SCRIPT>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="css/user_style1.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_job.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > 구인구직</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>

<table width="720" height="400" border="0" cellspacing="0" cellpadding="0" align="center">

				<tr>
					<td width="40">&nbsp;</td>
					<td valign=top align=centerc>
					<iframe frameborder="0"  height=100% marginheight="0" marginwidth="0" name=job src="http://www.gamejob.co.kr/partner/cga/com/CL_total_list.html" width="700" cellspacing="0" cellpadding="0"  scrolling="auto"></iframe>
					</td>
				</tr>

</table>
<p>&nbsp;</p>
</body>
</html>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
