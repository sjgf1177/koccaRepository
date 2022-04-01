
<%
//**********************************************************
//  1. 제      목: 스콤 콘텐츠 리스트
//  2. 프로그램명 : za_ScoContentLocateView.jsp
//  3. 개      요: 패키지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11. 24
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

	MasterFormBean bean = new MasterFormBean();
    
	ArrayList  list = bean.SelectScoLocateView(box);
	SCOData d = null;

	ErrorManager.systemOutPrintln(box);	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">

<title>콘텐츠위치조회</title>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
<tr>
    <td align="center" valign="top"> 

     <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/tit_contents.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
        <br>
      <!----------------- 매출매입내역 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="40%" class="table_title" >&nbsp;패키지명</td>
          <td width="60%" height="25" class="table_title">&nbsp;콘텐츠 위치</td>
        </tr>

		<%
        for (int i=0; i<list.size(); i++) {
			d = (SCOData)list.get(i);  
		%>

        <tr> 
          <td height="25" class="table_02_2">&nbsp;<%=d.getScoTitle()%></td>
          <td class="table_02_2">&nbsp; <%=conf.getProperty("dir.scorealpath")%><%=d.getScolocate()%></td>
        </tr>
		<% 
		}
		%>
      </table>
      <br>
        <!----------------- 저장, 닫기 버튼 시작 ----------------->
        
      <table width="11%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
      </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
    </td>
  </tr>
  <tr><td>



    </td></tr> 
</table>

</body>
</html>
<%@ include file = "/learn/library/getJspName.jsp" %>