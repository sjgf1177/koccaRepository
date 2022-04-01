<%
//**********************************************************
//  1. 제      목: 프로세스 관리
//  2. 프로그램명 : za_MenuSubProcess_L.jsp
//  3. 개      요: 프로세스 리스트
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
<SCRIPT LANGUAGE="JavaScript">
<!--
    function move_menu() {
        document.form1.action = "/servlet/controller.system.MenuSubAdminServlet";
        document.form1.p_menu.value = "";
        document.form1.p_seq.value = "";
        document.form1.p_modulenm.value = "";
        document.form1.p_process.value    = "selectMenu";
        document.form1.submit();
    }

    function move_menusub() {
        document.form1.action = "/servlet/controller.system.MenuSubAdminServlet";
        document.form1.p_seq.value = "";
        document.form1.p_modulenm.value = "";
        document.form1.p_process.value    = "select";
        document.form1.submit();
    }

    function view(process) {
        document.form1.action = "/servlet/controller.system.MenuSubProcessAdminServlet";
        document.form1.p_processvalue.value = process;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
        document.form1.action = "/servlet/controller.system.MenuSubProcessAdminServlet";
        document.form1.p_process.value    = "insertPage";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"      value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext"   value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_grcode"       value = "<%=v_grcode%>">
    <input type = "hidden" name = "p_menu"         value = "<%=v_menu%>">
    <input type = "hidden" name = "p_seq"          value = "<%=v_seq%>">
    <input type = "hidden" name = "p_modulenm"     value = "<%=v_modulenm%>">
    <input type = "hidden" name = "p_processvalue" value = "">

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
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr> 
            <td align="center"> 
              <table class="form_table_bg" cellspacing="0" cellpadding="0" >
                <tr> 
                  <td height="7" colspan="3"></td>
                </tr>
                <tr valign="middle"> 
                  <td width="600" height="26" style="padding-left=10;padding-right=10"> 메뉴명 : &nbsp;<a href="javascript:move_menu()"><strong><%=v_menunm%></strong></a>&nbsp;&nbsp; 모듈명 : &nbsp;<a href="javascript:move_menusub()"><strong><%=v_modulenm%></strong></a>&nbsp; </td>
                  <td align="right">&nbsp;&nbsp;</td>
                  <td width="50" style="padding-left=10;padding-right=10"> 
                    <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border=0></a>
                  </td>
                </tr>
                <tr> 
                  <td height="7" colspan="3"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <br>

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="6" class="table_top_line"></td>
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
            <td class="table_02_5"><a href="javascript:view('<%=v_processvalue%>')"><%=v_processvalue%></a></td>
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
