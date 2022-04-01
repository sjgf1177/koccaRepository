<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.lang.String" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />
<%
//DEFINED class&variable START
RequestBox box = (RequestBox)request.getAttribute("requestbox");
if (box == null) box = RequestManager.getBox(request);

String isEvent = box.getString("p_iseventchk").toLowerCase();

box.setSession("isevent","Y");

if(isEvent.equals("dpc")) {	//동부산대학교
%>
	<script>
	 parent.document.mform1.p_isevent.value = "Y";
	 alert("인증확인이 되었습니다.");
	</script>
<%
} else {
%>
	<script>
	 parent.document.mform1.p_isevent.value = "N";
	 alert("인증실패하였습니다!");
	</script>
<%
}
%>