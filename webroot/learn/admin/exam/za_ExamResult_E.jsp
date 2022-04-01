<%
/**
 * file name : za_ExamResult.jsp
 * date      : 2003/09/07
 * programmer:
 * function  : 평가문제지 평가점수 조회
 */
%>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.exam.*" %>   
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=ExamResult.xls");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode      = box.getStringDefault("s_grcode", "ALL");           //교육그룹
    String  ss_gyear       = box.getStringDefault("s_gyear", "ALL");            //년도
    String  ss_grseq       = box.getStringDefault("s_grseq", "ALL");            //교육차수
    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
    String  ss_subjseq     = box.getStringDefault("s_subjseq","ALL");

    String ss_lesson        = box.getString("s_lesson");        // 과정차시
    int ss_papernum        = box.getInt("s_papernum");        // 

    ArrayList  list = (ArrayList)request.getAttribute("ExamResultList");
	ExamResultData	data  = null;
	int totalretry = 3;
	
  Vector  v_average = (Vector)request.getAttribute("ExamResultAverage");     
 	
%>
<html>
<head>
<title>평가점수 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
</head>
<body text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">       
      <table cellspacing="1" cellpadding="5" border=1>
        <tr> 
          <td colspan="9"></td>
        </tr>
        <tr> 
          <td class="table_title">NO</td>
          <td class="table_title">회원구분</td>
          <td class="table_title">ID</td>
          <td class="table_title">성명</td>
          <td class="table_title">상태</td>
          <td class="table_title">평가일차</td>  
          <td width="120" class="table_title">완료일시</td>
          <td width="50" class="table_title">점수</td>
          <td width="60" align=center class="table_title">사용자응시횟수(재응시)</td>          
        </tr>
<%  for (int i=0; i<list.size(); i++) {
        data  = (ExamResultData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=data.getMembergubunnm()%></td>
           <td class="table_02_1">
          <%=data.getUserid()%>
          </td>
          <td class="table_02_1"><%=data.getName()%></td>
          <td class="table_02_1"><%=data.getStatus()%></td>
		  <td class="table_02_1"><%=data.getLesson()%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEnded(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=data.getScore()%></td>
          <td class="table_02_1">
          <%=data.getRetrycnt()%>
          (<%=data.getRetrycnt()%>)</td>
        </tr>
<%  } %>
      </table>
      <!----------------- 평가점수 조회 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</body>
</html>
