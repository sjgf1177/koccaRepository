<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

    String	p_url	= box.getString("p_url");
%>
<html>
<head>
<title></title>
<script language="javascript">
<!--
    var timerID = null;
    var istimer = false;

	function startclock() {
		setTimeout('timer()',10000);
	}

	function timer(){
		istimer=true;
	}

	function setEduChk() {
		if (istimer) {
			clearTimeout(timerID);
			istimer=false;

			opener.parent.etop.eduChk();
			//setTimeout("opener.parent.etop.eduChk()",1000);
			opener.parent.etop.goNext();
			//			alert("process...");
			//setTimeout("opener.parent.etop.goNext()",1000);
		}
    }
//-->
</script>
</head>

<frameset rows="0,*" border="0" onLoad="startclock();" onUnLoad="setEduChk();">
	<frame name="hidden" scrolling="no" marginwidth="0" marginheight="0" src="about:blank">
    <frame name="contents" scrolling="auto" marginwidth="0" marginheight="0" src="<%=p_url%>">
</frameset>
<noframes></noframes>

</html>