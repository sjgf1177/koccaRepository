<%
/**
 * file name : za_Subject_Off_I.jsp
 * date      : 2003/08/05
 * programmer:
 * function  : 집합과정등록 화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ page import = "com.credu.etest.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
%>
<html>
<head>
<title>설문,평가,온라인테스트 테스트</title>
<meta http-equiv="Content-Type" contㄴent="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function wholeSulmun() {
   window.self.name = "winSelectView";
   farwindow = window.open("", "wholeSulOpen", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
   document.form2.target = "wholeSulOpen";
   document.form2.action = "/servlet/controller.research.SulmunServlet";
   document.form2.submit();

   farwindow.window.focus();
   document.form2.target = window.self.name;
}
function commonSulmun() {
   window.self.name = "winSelectView";
   farwindow = window.open("", "commonSulOpen", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
   document.form3.target = "commonSulOpen";
   document.form3.action = "/servlet/controller.research.SulmunServlet";
   document.form3.submit();

   farwindow.window.focus();
   document.form3.target = window.self.name;
}
function subjExam() {
   window.self.name = "winSelectView";
   farwindow = window.open("", "subjExamOpen", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
   document.form4.target = "subjExamOpen";
   document.form4.action = "/servlet/controller.exam.ExamServlet";
   document.form4.submit();

   farwindow.window.focus();
   document.form4.target = window.self.name;
}
function onlineExam() {
   window.self.name = "winSelectView";
   farwindow = window.open("", "onlineExamOpen", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
   document.form5.target = "onlineExamOpen";
   document.form5.action = "/servlet/controller.etest.ETestServlet";
   document.form5.submit();

   farwindow.window.focus();
   document.form5.target = window.self.name;
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top"> 
      <!----------------- 과정 등록/수정/삭제/상세보기 (집합) 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
        <input type="hidden" name="p_process"     value="UserResultListPage">
        <input type="hidden" name="p_f_gubun"     value="W">
        <tr>
          <td class="table_title_01"><b>전체설문</b></td>
        </tr>
        <tr>
          <td class="table_02_2">
          과정코드<input type="text" class="input" name="p_subj" size="10" maxlength="10" value="WHOLE">
          연도    <input type="text" class="input" name="p_year" size="10" maxlength="10" value="2003">
          과정차수<input type="text" class="input" name="p_subjseq" size="10" maxlength="10" value="0001">
          교육그룹<input type="text" class="input" name="p_grcode" size="10" maxlength="10" value="N000003">
          설문지번호<input type="text" class="input" name="p_sulpapernum" size="10" maxlength="10" value="1">
          </td>
        </tr>
        <tr>
          <td class="table_1"><b><a href="javascript:wholeSulmun()">등록화면</a></b></td>
        </tr>
      </form>
      </table>
      <br>
      <br>
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="/servlet/controller.research.SulmunServlet">
        <input type="hidden" name="p_process"     value="UserResultListPage">
        <input type="hidden" name="p_f_gubun"     value="C">
        <tr>
          <td class="table_title_01"><b>공통설문</b></td>
        </tr>
        <tr>
          <td class="table_02_2">
          과정코드<input type="text" class="input" name="p_subj" size="10" maxlength="10" value="S00001">
          연도    <input type="text" class="input" name="p_year" size="10" maxlength="10" value="2003">
          과정차수<input type="text" class="input" name="p_subjseq" size="10" maxlength="10" value="0001">
          </td>
        </tr>
        <tr>
          <td class="table_1"><b><a href="javascript:commonSulmun()">등록화면</a></b></td>
        </tr>
      </form>
      </table>
      <br>
      <br>
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form4" method="post" action="/servlet/controller.exam.ExamServlet">
        <input type="hidden" name="p_process"   value="UserResultListPage">
        <tr>
          <td class="table_title_01"><b>과정평가</b></td>
        </tr>
        <tr>
          <td class="table_02_2">
          과정코드<input type="text" class="input" name="p_subj" size="10" maxlength="10" value="S00001">
          연도    <input type="text" class="input" name="p_year"    size="10" maxlength="10" value="2003">
          과정차수<input type="text" class="input" name="p_subjseq" size="10" maxlength="10" value="0001">
          일차    <input type="text" class="input" name="p_lesson" size="6" maxlength="2" value="05">
          중간/최종평가<input type="text" class="input" name="p_ptype" size="6" maxlength="4" value="M">
          </td>
        </tr>
        <tr>
          <td class="table_1"><b><a href="javascript:subjExam()">등록화면</a></b></td>
        </tr>
      </form>
      </table>
      <br>
      <br>
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form5" method="post" action="/servlet/controller.etest.ETestServlet">
        <input type="hidden" name="p_process"     value="UserResultListPage">
        <tr>
          <td class="table_title_01"><b>온라인테스트</b></td>
        </tr>
        <tr>
          <td class="table_02_2">
          과정코드<input type="text" class="input" name="p_subj" size="10" maxlength="10" value="OT00001">
          연도    <input type="text" class="input" name="p_year" size="10" maxlength="10" value="2003">
          과정차수<input type="text" class="input" name="p_subjseq" size="10" maxlength="10" value="0001">
          시험지번호<input type="text" class="input" name="p_papernum" size="10" maxlength="10" value="1">
          </td>
        </tr>
        <tr>
          <td class="table_1"><b><a href="javascript:onlineExam()">등록화면</a></b></td>
        </tr>
      </form>
      </table>
      <br>
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     


</table>
</body>
</html>
