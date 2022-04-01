<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import="com.credu.homepage.LoginBean" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%

    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.setSession("tem_grcode","N000055");
    //box.setSession("userid",box.getString("p_userid"));
    //box.setSession("pw",box.getString("p_pw"));
    box.setSession("s_gubun", 3);
    
    System.out.println("11s_gubun::::::::::::"+box.getSession("s_gubun"));
    
    String z_aspgubun	 = box.getString("s0");
    String z_name		 = box.getString("s1");
    String z_birthday	 = box.getString("s2");
    String z_sex		 = box.getString("s3");
    String z_userid		 = "hns_"+box.getString("s4");
    String z_pwd		 = box.getString("s5");
    String z_tel		 = box.getString("s6");
    String z_handphone	 = box.getString("s7");
    String z_email		 = box.getString("s8"); 

%>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript" src = "/script/mainscript.js"></script>
<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--

function myPageMove(userid, aspgubun){

	document.form1.hns_name.value		 = '<%=z_name%>';
	document.form1.hns_birthday.value	 = '<%=z_birthday%>';
	document.form1.hns_sex.value		 = '<%=z_sex%>';
	document.form1.hns_userid.value		 = '<%=z_userid%>';
	document.form1.hns_pwd.value		 = '<%=z_pwd%>';
	document.form1.hns_tel.value		 = '<%=z_tel%>';
	document.form1.hns_handphone.value	 = '<%=z_handphone%>';
	document.form1.hns_email.value		 = '<%=z_email%>';

	document.form1.p_id.value		 = userid;
	document.form1.p_aspgubun.value = aspgubun;
	document.form1.action = "/servlet/controller.homepage.MainServlet";
	document.form1.p_process.value = "ASP_Login";
	document.form1.submit();

}


//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<body onload="javascript:myPageMove('<%=z_userid %>','<%=z_aspgubun%>')">

<form name="form1"  action="" method="post">
    <input type='hidden' name='p_process'   value="">
    <input type='hidden' name='p_aspgubun'  value="">
    <input type='hidden' name='gubun'       value="60">
    <!-- input type='hidden' name='gubun'   value="3"> 
    <input type='hidden' name='menuid'      value="11" -->
    <input type='hidden' name='p_grcode'    value="N000055">
    <input type='hidden' name='p_id'    value="">
    
    <input type='hidden' name='hns_name'    value="">
    <input type='hidden' name='hns_birthday'    value="">
    <input type='hidden' name='hns_sex'    value="">
    <input type='hidden' name='hns_userid'    value="">
    <input type='hidden' name='hns_pwd'    value="">
    <input type='hidden' name='hns_tel'    value="">
    <input type='hidden' name='hns_handphone'        value="">
    <input type='hidden' name='hns_email'    value="">

		<table>
			<tr>
			    <td></td>
			</tr>
		</table>

</form>
</body>

<%@page import="com.credu.library.RequestBox"%>
<%@page import="com.credu.library.RequestManager"%></html>