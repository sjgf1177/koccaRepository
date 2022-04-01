<%
//**********************************************************
//  1. 제      목: 프로세스 리스트
//  2. 프로그램명 : za_MenuSubProcess_I.jsp
//  3. 개      요: 프로세스 등록
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

    String v_searchtext    = box.getString("p_searchtext");
    String v_grcode   = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_menu     = box.getString("p_menu");
    String v_menunm   = MenuAdminBean.getMenuName(v_grcode, v_menu);
    int v_seq         = box.getInt("p_seq");
    String v_modulenm = box.getString("p_modulenm");

    String v_processvalue    = "";
    String v_servlettype     = "";
    String v_method          = "";
	String v_servlettypeview = "";

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

    function insert_check() {
        if (document.form1.p_processvalue.value == "") {
            alert("프로세스를 입력하세요");
            document.form1.p_processvalue.focus();
            return;
        }
        if (realsize(document.form1.p_processvalue.value) > 50) {
            alert("프로세스 50자를 초과하지 못합니다.");
            document.form1.p_processvalue.focus();
            return;
        }
        document.form1.action = "/servlet/controller.system.MenuSubProcessAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.system.MenuSubProcessAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_grcode"      value = "<%=v_grcode%>">
    <input type = "hidden" name = "p_menu"        value = "<%=v_menu%>">
    <input type = "hidden" name = "p_seq"         value = "<%=v_seq%>">
    <input type = "hidden" name = "p_modulenm"    value = "<%=v_modulenm%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->


      <br>
        <!----------------- form 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr class="table_02_2"> 
            <td height="26" class="table_title" ><strong>메뉴코드</strong></td>
            <td height="25" class="table_02_2"><%=v_menu%></td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>메뉴코드명</strong></td>
            <td height="25" class="table_02_2"><%=v_menunm%></td>
          </tr>
          <tr class="table_02_2"> 
            <td height="26" class="table_title" ><strong>모듈번호</strong></td>
            <td height="25" class="table_02_2"><%=v_seq%></td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>모듈명</strong></td>
            <td height="25" class="table_02_2"><%=v_modulenm%></td>
          </tr>
          <tr> 
            <td height="26" class="table_title">프로세스</td>
            <td height="25" class="table_02_2"><input name="p_processvalue" type="text" class="input" size="60"></td>
          </tr>
          <tr> 
            <td height="26" class="table_title"><strong>서블릿타입</strong></td>
            <td height="25" class="table_02_2">
              <input type="radio" name="p_servlettype"  class="input" size="18" value='1' checked>조회&nbsp;
			  <input type="radio" name="p_servlettype"  class="input" size="18" value='2'>조회(쓰기액션있음)&nbsp;
			  <input type="radio" name="p_servlettype"  class="input" size="18" value='4'>쓰기액션&nbsp;
            </td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>함수명</strong></td>
            <td height="25" class="table_02_2"><input name="p_method" type="text" class="input" size="60"> 
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
        <br>

        <!----------------- List Title 시작 ----------------->
        <table width="97%" height="25" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td valign="absmiddle" style="padding-top=25;padding-bottom=5">&nbsp;<img src="/images/admin/system/icon2.gif" width="12" height="8" border="0"><b><font color="#107AAD">프로세스코드</font></b></td>
          </tr>
        </table>
        <!----------------- List Title 끝 ----------------->

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" height="25" class="table_title"><b>메뉴코드</b></td>
            <td width="10%" class="table_title"><b>번호</b></td>
            <td width="25%" class="table_title"><b>프로세스</b></td>
            <td width="20%" class="table_title"><b>서블릿타입</b></td>
            <td width="30%" class="table_title"><b>함수명</b></td>
          </tr>
<%
            for(int i = 0; i < list.size(); i++) {
                MenuSubProcessData data  = (MenuSubProcessData)list.get(i);
                v_processvalue     = data.getProcess();
                v_servlettype = data.getServlettype();
                v_method      = data.getMethod();

				if (v_servlettype.equals("1"))      v_servlettypeview = "조회";
				else if (v_servlettype.equals("2")) v_servlettypeview = "조회(쓰기액션있음)";
				else if (v_servlettype.equals("4")) v_servlettypeview = "쓰기액션";

%>
          <tr> 
            <td height="25" class="table_02_5"> <%=v_menu%> </td>
            <td class="table_02_5"><%=v_seq%></td>
            <td class="table_02_5"><%=v_processvalue%></td>
            <td class="table_02_5"><%=v_servlettypeview%></td>
            <td class="table_02_5"><%=v_method%></td>
          </tr>
<%
        }
%>
        </table>
        <!----------------- List 끝 ----------------->
    </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
