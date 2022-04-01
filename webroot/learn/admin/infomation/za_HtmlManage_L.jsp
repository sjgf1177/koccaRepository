<%
//**********************************************************
//  1. 제      목: Notice Templet
//  2. 프로그램명 : za_NoticeTemplet_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box        = (RequestBox)request.getAttribute("requestbox");
    String  v_process     = box.getString("p_process");


    int    v_seq      = 0;              // 일련번호
    String v_title    = "";         // 제목
    String v_indate   = "";         // 등록일
    String v_code = "";         // HTML 경로

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list      = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    // 등록
    function insert() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 검색
    function search() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    // 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.infomation.HtmlManageAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    // 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.infomation.HtmlManageAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process"     value = "">
    <input type = "hidden" name = "p_seq"     value = "">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_18.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>

      <!----------------- 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" height="20"><a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 공지사항 시작 ----------------->

      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" height="25" class="table_title"><b>NO</b></td>
          <td class="table_title">제목</td>
          <td width="10%" class="table_title">코드</td>
          <td width="10%" class="table_title">등록일</td>
        </tr>
<%
            for(int i = 0; i < list.size(); i++) {
                DataBox dbox   = (DataBox)list.get(i);

                v_seq         = dbox.getInt("d_seq");
                v_title       = dbox.getString("d_title");
                v_indate      = dbox.getString("d_indate");
                v_code        = dbox.getString("d_code");

                v_indate      = FormatDate.getFormatDate(v_indate,"yyyy/MM/dd");
                v_dispnum     = dbox.getInt("d_dispnum");
%>

          <tr>
            <td class="table_01" height="29"><%=list.size() - i%></td>
            <td class="table_02_2"><a href="javascript:view('<%=v_seq%>')"><%=v_title%></a></td>
            <td class="table_02_1"><%=v_code%></td>
            <td class="table_02_1"><%=v_indate%></td>
          </tr>
<%
        }
%>
      </table>
        <!----------------- 리스트 조회 끝 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle"> 
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>