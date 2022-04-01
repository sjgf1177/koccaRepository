<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
//DEFINED class&variable START
RequestBox box = (RequestBox)request.getAttribute("requestbox");
if (box == null) box = RequestManager.getBox(request);

int p_userCnt = box.getInt("p_userCnt");
String p_message = box.getString("p_message");

box.setSession("isevent","Y");

if(p_message.equals("Y")) {
	if(p_userCnt > 0) {
	%>
		<script>
		 parent.document.mform1.p_isevent.value = "Y";
		 alert("추천회원 확인이 되었습니다\n\n회원님은 이벤트 기간 동안 1과목을\n1만원에 수강하실 수 있습니다");
		</script>
	<%
	} else {
	%>
		<script>
		 parent.document.mform1.p_isevent.value = "N";
		 alert("회원아이디를 다시 한 번 확인하십시오!");
		</script>
	<%
	}
}
%>