<%
/**
 * file name : mScreenFset.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen FrameSet
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

/*============ 테스트용 ======================*/
//    box.setSession("userid","lee1");
//    box.setSession("gadmin","A1");
//    box.setSession("resno","6906031010912");
//    box.setSession("name","김연남");
//    box.setSession("email","jeong@credu.com");
//    box.setSession("usergubun","KDGI");
/*============ 테스트용 ======================*/

   String v_gadmin = box.getSession("gadmin");
   String v_systemgubun = box.getString("p_systemgubun");
//   if (v_gadmin.equals("")) box.setSession("gadmin", "A1");

   if (v_gadmin.equals("") || v_gadmin.equals("ZZ")) {
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
    alert("권한이 없습니다");
    parent.document.location.href="/learn/admin/kocca/login.jsp";
    // self.close();
//-->
</SCRIPT>
<%
   } else {
%>

	<html>
	<head>
	<title>한국콘텐츠아카데미 LMS</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	</head>

  		<frameset cols="*" rows="0,105,1*,0,0" frameborder="NO" border="0" framespacing="0">
	  		<frame src="mScreenFtops.jsp"  name="ftop" scrolling="NO" frameborder="NO">
	  		<frame src="mScreenFmenu.jsp?p_systemgubun=<%=v_systemgubun%>"  name="fmenu" scrolling="NO" frameborder="NO">
            <frameset cols="150,*" rows="*" frameborder="NO" border="0" framespacing="0">
                <frame src="mScreenFsubMenu.jsp"  name="fleft" scrolling="NO" frameborder="NO">
			<% if (v_gadmin.equals("V1")) { %>
				<frame src="/servlet/controller.study.StudyStatusAdminServlet?p_process=PersonalSearchPage"  id="fbody" name="fbody" scrolling="AUTO" frameborder="NO">
			<% } else { %>
				<frame src="mScreenFbody.jsp"  id="fbody" name="fbody" scrolling="AUTO" frameborder="NO">
			<% } %>
	  		<frame src="mScreenFdown.jsp"  name="fdown" scrolling="NO" frameborder="NO">
		</frameset>
		<noframes><body bgcolor="#FFFFFF">

	</body></noframes>
	</html>
<%
   }
%>
