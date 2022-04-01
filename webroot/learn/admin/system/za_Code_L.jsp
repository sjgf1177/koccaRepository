<%
//**********************************************************
//  1. 제      목: 대분류코드 리스트
//  2. 프로그램명 : za_Code_L.jsp
//  3. 개      요: 대분류코드 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
//    String  v_searchtext    = box.getString("p_searchtext");

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
    function sub_list(gubun) {
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_gubun.value   = gubun;
        document.form1.p_levels.value   = 1;
        document.form1.p_process.value = "selectSubList";
        document.form1.submit();
    }

    function view(gubun) {
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_gubun.value   = gubun;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }


//-->
</SCRIPT>

</head>




<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_gubun"   value = "">
    <input type = "hidden" name = "p_levels"  value = "">


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
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr> 
            <td align="center"> 
              <table class="form_table_bg" cellspacing="0" cellpadding="0">
                <tr> 
                  <td height="7" colspan="3"></td>
                </tr>
                <tr valign="middle"> 
                  <td width="200" height="26" style="padding-left=10;padding-right=10">&nbsp; </td>
                  <td align="right"> </td>
                  <td width="160" align="right"style="padding-left=10;padding-right=10"> 
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
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="30%" height="25" class="table_title"><b>대분류코드</b></td>
            <td width="40%" class="table_title"><b>대분류코드명</b></td>
            <td width="15%" class="table_title"><b>코드자동등록여부</b></td>
            <td width="15%" class="table_title"><b>하위소분류코드</b></td>
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
            <td class="table_02_2"><a href="javascript:view('<%=v_gubun%>')"><%=v_gubunnm%></a></td>
            <td class="table_02_1"><%=v_issystem_view%></td>
            <td class="table_03_1"><a href="javascript:sub_list('<%=v_gubun%>')"><img src="/images/admin/button/b_downcodeb.gif" align="absmiddle" border="0"></a></td>
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
