<%
/**
 * file name : z_EduStart_fmenu.jsp
 * date      : 2003/08/21
 * programmer: LeeSuMin
 * function  : 마스터폼 Menu Frame
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%//@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "hip.CheckTestBean" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
    
	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	
	String	v_subj	= da.getSubj();
	String	v_imgURL	= "/contents/" + v_subj + "/docs/menuimg";
%>
<html>
	<head>
	<title>Education...</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<style type = "text/css">
		@font-face { font-family: morris9,돋움; src: url(/website/morris9.mwf); }
		
		a:link{text-decoration:none; color:#FFFFFF; font-family: morris9,돋움; src: url(/website/morris9.mwf);  }
		a:visited{text-decoration:none; color:#FFFFFF; font-family: morris9,돋움; src: url(/website/morris9.mwf);}
		a:hover { text-decoration:underline; color:#FFFFFF; font-family: morris9,돋움; src: url(/website/morris9.mwf);}
		td{font-family:morris9,돋움; font-size:12px; line-height:15px; color:#545A6A; src: url(/website/morris9.mwf);}
		
		a.rollover img { border-width:0px; display:inline; }
		a.rollover img.over { display:none; }
		
		a.rollover:hover { border:0px }
		a.rollover:hover img { display:none; }
		a.rollover:hover img.over { display:inline; }		
	</style> 
	</head>

	<body background="<%=v_imgURL%>/menu_bg.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table border="0" cellpadding="0" cellspacing="0" align="center" valign="TOP" leftmargin="0" topmargin="0" background="<%=v_imgURL%>/menu_bg.gif">
	<%
		if ( da.getContenttype().equals("O") && da.getIsImageMenu().equals("Y") ) {
	%>
		<tr>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m01.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m01u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');" src="<%=v_imgURL%>/m02.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m02u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');" src="<%=v_imgURL%>/m03.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m03u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m04.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m04u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m05.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m05u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m06.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m06u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');" src="<%=v_imgURL%>/m07.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m07u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m08.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m08u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');" src="<%=v_imgURL%>/m09.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m09u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m10.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m10u.gif" class="over"></a></TD>
		  <TD height=45><a class="rollover" href="javascript:" onClick="javascript:alert('미리보기에서는 지원하지 않습니다.');"><img style="cursor:pointer;" src="<%=v_imgURL%>/m11.gif"><img style="cursor:pointer;" src="<%=v_imgURL%>/m11u.gif" class="over"></a></TD>
		</tr>
	<%
		}
		else {
	%>
		<tr><td></td></tr>
	<%
		}
	%>
	</TABLE>
	</body>
</html>