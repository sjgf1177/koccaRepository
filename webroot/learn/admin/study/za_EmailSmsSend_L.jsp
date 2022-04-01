<%
//**********************************************************
//  1. 제      목: PROJECT DETAIL LIST
//  2. 프로그램명: za_ProjectDetail_L.jsp
//  3. 개      요: 리포트 제출자 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }
    function projectList() {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectSubmitList';
        document.form1.submit();
    }    
    function projectDetailList(subj,subjnm,year,subjseq,ordseq,lesson) {
		document.form1.target="_self";
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectDetailList';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_ordseq.value = ordseq;
        document.form1.p_lesson.value = lesson;
        document.form1.submit();
    }
    function open_projectSubmit(seq,projgrp) {
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openProjectSubmit","","100","100","548","550","1");      
        document.form1.target = "openProjectSubmit";
        document.form1.action = "/servlet/controller.study.ProjectAdminServlet";
        document.form1.p_process.value = "ProjectSubmitOpenPage";
        document.form1.p_seq.value = seq;
        document.form1.p_projgrp.value = projgrp;
        document.form1.submit();
        document.form1.target = window.self.name;        
    }
	
	function open_projectSearch() {
        window.self.name = "projectSearch";     //opener 객체에 name 을 지정한다
        open_window("openProjectSearch","","100","100","1024","768","1");      
        document.form1.target = "openProjectSearch";
        document.form1.action = "/search/ReportSearch";
        document.form1.p_process.value = "";
        document.form1.submit();
        document.form1.target = window.self.name;        
    }         
-->    	
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td align="left">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>

          <td align="center" bgcolor="#FFFFFF">
  <table width="615" border="0" cellspacing="0" cellpadding="0" height="520">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title08.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>  
	          <!----------------- title 끝 ----------------->
        <br>
		        <!-------------소제목 시작-------------------------->
 <table width="600" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>EMAIL/SMS 발송 내역</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td></td>
          </tr>
        </table>

        <br>
        <!----------------- 리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
		    <td width="300"><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
            테스트 과정 &gt;  1 차수 </td>
            <td width="300" height="20" align="right" style=padding-right:8px>성명(사번) : 홍길동(123456)</td>
          </tr>
        </table>

		<br>
        <!----------------- 추가, 리스트 버튼 끝 ----------------->

        <!----------------- 리포트 출제리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%" ><b>NO</b></td>
            <td class="table_title" width="10%"><b>구분</b></td>
            <td class="table_title" width="10%"><b>발송일</b></td>
            <td class="table_title" width="20%"><b>제목</b></td>
            <!-- <td class="table_title" width="45%"><b>내용</b></td> -->
            <td class="table_title" width="10%"><b>비고</b></td>
          </tr>
          <tr> 
            <td class="table_01">1</td>
            <td class="table_02_1" align="center">EMAIL</td>
            <td class="table_02_1" align="center">2005.06.01</td>
            <td class="table_02_1">XXX교육신청완료</td>
            <!-- <td class="table_02_1" align="center">XXX 교육 신청이 완료되었습니다.</td> -->
            <td class="table_02_1" align="center"></td>
          </tr>
          <tr> 
            <td class="table_01">2</td>
            <td class="table_02_1" align="center">SMS</td>
            <td class="table_02_1" align="center">2005.06.05</td>
            <td class="table_02_1">교육안내</td>
            <!-- <td class="table_02_1" align="center">XXX 교육안내입니다.</td> -->
            <td class="table_02_1" align="center"></td>
          </tr>
        </table>
        <!----------------- Activity 리스트 끝 ----------------->
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>    
</table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
</body>
</html>
