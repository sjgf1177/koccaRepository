<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import="com.credu.homepage.LoginBean" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%

    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.setSession("tem_grcode","N000041");
    box.setSession("userid",box.getString("p_userid"));
    box.setSession("s_gubun", 3);
    
    System.out.println("11s_gubun::::::::::::"+box.getSession("s_gubun"));

%>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript" src = "/script/mainscript.js"></script>
<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--

// userid 존재 여부 체크
function kocscUseridCheck(userid){
alert("test");
	document.form1.p_userid.value = userid;
	document.form1.action = "/servlet/controller.contents.EduStart";
	document.form1.p_process.value = "KOCSC_Userid_Check";
	document.form1.submit();
}

// 학습창 OPEN
function myPageMove(userid, aspgubun){

	document.form1.p_userid.value = userid;
	document.form1.p_id.value = userid;
	document.form1.p_aspgubun.value = aspgubun;
	document.form1.action = "/servlet/controller.homepage.MainServlet";
	document.form1.p_process.value = "ASP_Login";
	document.form1.submit();
}


//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<body onload="myPageMove('<%=box.getString("p_userid") %>', '<%=box.getString("p_aspgubun") %>')">
<form name="form1"  action="" method="post">
    <input type='hidden' name='p_process'   value="">
    <input type='hidden' name='p_userid'    value="">
    <input type='hidden' name='p_aspgubun'  value="">
    <input type='hidden' name='gubun'       value="60">
    <!-- input type='hidden' name='gubun'   value="3"> 
    <input type='hidden' name='menuid'      value="11" -->
    <input type='hidden' name='p_grcode'    value="N000041">
    <input type='hidden' name='p_id'        value="">
    <!-- input type='hidden' name='p_pw'    value="kocsc001" -->

		<table>
			<tr>
			    <td></td>
			</tr>
		</table>

</form>
</body>
</html>


