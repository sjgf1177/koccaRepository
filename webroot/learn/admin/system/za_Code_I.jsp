<%
//**********************************************************
//  1. 제      목: 대분류코드 등록
//  2. 프로그램명 : za_Code_I.jsp
//  3. 개      요: 대분류코드 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");


    String  v_gubun         = "";
    String  v_gubunnm       = "";
    int     v_maxlevel      = 0;
    String  v_issystem      = "";
    String  v_issystem_view = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
		//코드 등록시
    function insert_check() {
        if (document.form1.p_gubunnm.value == "") {
            alert("대분류코드명을 입력하세요");
            document.form1.p_gubunnm.focus();
            return;
        }
        if (realsize(document.form1.p_gubunnm.value) > 100) {
            alert("대분류코드명은 한글기준 50자를 초과하지 못합니다.");
            document.form1.p_gubunnm.focus();
            return;
        }
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }
		
		//리스트 화면 이동
    function list() {
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>




<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
        <!----------------- form 시작 ----------------->
        <br>
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" height="25" class="table_title"><strong>대분류코드</strong></td>
            <td class="table_02_2">* 시스템 자동부여</td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>대분류코드명</strong></td>
            <td height="25" class="table_02_2">
              <input name="p_gubunnm"   type="text" size="110" maxlength="50" class="input">
              <input name="p_maxlevel" type="hidden" value="1">
            </td>
          </tr>
          <tr> 
            <td width="15%" height="25" class="table_title"><strong>코드자동등록여부</strong></td>
            <td class="table_02_2">
              <select name = 'p_issystem'>
                <option value='N'>자동등록</option>
                <option value='Y'>수동등록</option>
              </select>
            </td>
          </tr>
        </table>

        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="right" class="ms"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table> 
        <!----------------- form 끝 ----------------->

        <!----------------- List Title 시작 ----------------->
        <table width="97%" height="25" border="0" cellpadding="0" cellspacing="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td class=sub_title>대분류코드</td>
          </tr>
        </table>
        <!----------------- List Title 끝 ----------------->

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="3" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="30%" height="25" class="table_title"><b>대분류코드</b></td>
            <td width="55%" class="table_title"><b>대분류코드명</b></td>
            <td width="15%" class="table_title"><b>코드자동등록여부</b></td>
          </tr>
<%
            for(int i = 0; i < list.size(); i++) {

                CodeData data  = (CodeData)list.get(i);
                v_gubun       = data.getGubun();
                v_gubunnm     = data.getGubunnm();
                v_maxlevel    = data.getMaxlevel();
                v_issystem    = data.getIssystem();
                if (v_issystem.equals("Y")) v_issystem_view = "수동등록";
                else v_issystem_view = "자동등록";

%>
          <tr>
            <td height="25" class="table_01"><%=v_gubun%></td>
            <td class="table_02_2"><%=v_gubunnm%></td>
            <td class="table_02_1"><%=v_issystem_view%></td>
          </tr>

<%
        }
%>

        </table>
        <!----------------- List 끝 ----------------->

        <br>
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %> 
</form>
</body>
</html>
