<%
/**
 * file name : zu_OpenSulmun.jsp
 * date      : 2011/08/02
 * programmer:
 * function  : 교육서비스 만족도 조사 설문
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	
//	System.out.println("userid : "+box.getString("p_edusulUserid")+" / s_userid : "+box.getSession("s_userid"));

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>교육 서비스 만족도 조사 설문</title>

<script language="JavaScript" type="text/javascript">
<!--

	// 설문 INSERT..
	function eduservicesulInsert(answer){

		var f = document.form1;

		f.p_onlineanswer1.value = answer[0];
		f.p_onlineanswer2.value = answer[1];
		f.p_onlineanswer3.value = answer[2];
		f.p_onlineanswer4.value = answer[3];
		f.p_onlineanswer5.value = answer[4];
		f.p_serviceanswer1.value = answer[5];
		f.p_serviceanswer2.value = answer[6];
		f.p_serviceanswer3.value = answer[7];
		f.p_serviceanswer4.value = answer[8];
		f.p_serviceanswer5.value = answer[9];
		f.p_webanswer1.value = answer[10];
		f.p_webanswer2.value = answer[11];
		f.p_webanswer3.value = answer[12];
		f.p_webanswer4.value = answer[13];
		f.p_webanswer5.value = answer[14];
		f.p_webanswer6.value = answer[15];
		f.p_contributeanswer1.value = answer[16];
		f.p_contributeanswer2.value = answer[17];
		f.p_contributeanswer3.value = answer[18];
		f.p_contributeanswer4.value = answer[19];
		f.p_contributeanswer5.value = answer[20];
//		f.p_userid.value = "";

		document.form1.action = "/servlet/controller.study.MyClassServlet";
        document.form1.p_process.value = "EduSulmunInsert";
        document.form1.submit();
	}
	
	function alert_1(){
		alert("모든 설문에 체크하여 주세요.");
	}
	function sul_close(){
		alert("설문에 응해 주셔서 감사합니다.");
		window.open('about:blank','_self');
		opener=window;
		window.close();
	}

//-->
</script>

</head>

<body>
<form name="form1" method="post" action="">
	<input type="hidden" name="p_onlineanswer1" value="" >
	<input type="hidden" name="p_onlineanswer2" value="" >
	<input type="hidden" name="p_onlineanswer3" value="" >
	<input type="hidden" name="p_onlineanswer4" value="" >
	<input type="hidden" name="p_onlineanswer5" value="" >
	<input type="hidden" name="p_serviceanswer1" value="" >
	<input type="hidden" name="p_serviceanswer2" value="" >
	<input type="hidden" name="p_serviceanswer3" value="" >
	<input type="hidden" name="p_serviceanswer4" value="" >
	<input type="hidden" name="p_serviceanswer5" value="" >
	<input type="hidden" name="p_webanswer1" value="" >
	<input type="hidden" name="p_webanswer2" value="" >
	<input type="hidden" name="p_webanswer3" value="" >
	<input type="hidden" name="p_webanswer4" value="" >
	<input type="hidden" name="p_webanswer5" value="" >
	<input type="hidden" name="p_webanswer6" value="" >
	<input type="hidden" name="p_contributeanswer1" value="" >
	<input type="hidden" name="p_contributeanswer2" value="" >
	<input type="hidden" name="p_contributeanswer3" value="" >
	<input type="hidden" name="p_contributeanswer4" value="" >
	<input type="hidden" name="p_contributeanswer5" value="" >
	<input type="hidden" name="p_userid" value="<%=box.getString("p_edusulUserid") %>" >
	<input type="hidden" name="p_process" value="" >
	
	<table background="/images/portal/common/edusulinfo/bg.png" border=0 cellpadding=0 cellspacing=0 width=600>
	<tr height=67>
		<td>&nbsp;</td>
	</tr>
	<tr height=600>
		<td align=center>
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="580" height="570">
				<param name="movie" value="/images/portal/common/edusulinfo/result_chk.swf">
				<param name="quality" value="high">
				<param name="menu" value="false">
				<param name="wmode" value="transparent">
				<param name="allowScriptAccess" value="always" />
				<embed src="/images/portal/common/edusulinfo/result_chk.swf" quality="high" menu="false" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="580" height="570"></embed>
			</object>
		</td>
	</tr>
<!-- 	<tr height=30>
		<td>&nbsp;</td>
	</tr> -->
</table>	
</form>
</body>
</html>
