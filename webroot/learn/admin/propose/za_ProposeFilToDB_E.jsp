<%
//**********************************************************
//  1. 제      목: 과정/ 차수코드 및 수강생 리스트
//  2. 프로그램명: za_ClassStudentList_E.jsp
//  3. 개      요: 과정코드 및 수강생 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.28
//  7. 수      정:
//***********************************************************
%>
<%@ page language="java" contentType="application/x.ms-excel; charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
 try {
	response.setHeader("Content-Disposition", "inline; filename=SubjCode.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseqgr = "";
    String  v_subjseq   = "";
	String  v_subjnm    = "";


    ArrayList list = (ArrayList)request.getAttribute("SubjCodeList");
%>  
<html>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<head>
<style>
	td.accountnum	{mso-number-format:\@}
</style>
<title></title>
</head>

<body>

  <table border="1">
  <tr align="center">
    <td>사용자ID</td>
    <td>차수</td>
    <td>과정</td>
    <td>과정명</td>
  </tr>
<%
    for(int i = 0; i < list.size(); i++) {
      DataBox dbox = (DataBox)list.get(i);
      v_subj          = dbox.getString("d_subj");
      v_year          = dbox.getString("d_year");
      v_subjseq       = dbox.getString("d_subjseq");
      v_subjseqgr     = dbox.getString("d_subjseqgr");
	  v_subjnm        = dbox.getString("d_subjnm");
%>
        <tr>
            <td class="accountnum">&nbsp;</td>
            <td class="accountnum"><%=v_subjseqgr%></td>
            <td class="accountnum"><%=v_subj%></td>
            <td class="accountnum"><%=v_subjnm%></td>
        </tr>
<%
    }

%>
</table>   
</body>
</html>
<%
 }
catch(Exception e) {System.out.println(e);}
%>