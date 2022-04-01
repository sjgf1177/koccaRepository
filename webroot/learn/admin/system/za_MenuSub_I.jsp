<%
//**********************************************************
//  1. 제      목: 모듈 리스트
//  2. 프로그램명 : za_MenuSub_I.jsp
//  3. 개      요: 모듈 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 16
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
    String v_grcode         = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_menu           = box.getString("p_menu");
    String v_menunm         = MenuAdminBean.getMenuName(v_grcode, v_menu);
    String v_systemgubun    = box.getString("p_systemgubun");

    int    v_seq      = 0;
    String v_servlet  = "";
    String v_modulenm = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

    ArrayList list2 = (ArrayList)request.getAttribute("selectGadmin");
    String v_gadmin   = "";
    String v_gadminnm = "";
    String v_systemnm = "";

//    if (v_systemgubun.equals("1")) v_systemnm = "메인시스템";
//    else if(v_systemgubun.equals("2")) v_systemnm= "게이트시스템";
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
        if (document.form1.p_servlet.value == "") {
            alert("서블릿을 입력하세요");
            document.form1.p_servlet.focus();
            return;
        }
        if (realsize(document.form1.p_servlet.value) > 50) {
            alert("서블릿은 50자를 초과하지 못합니다.");
            document.form1.p_servlet.focus();
            return;
        }

        if (document.form1.p_modulenm.value == "") {
            alert("모듈명을 입력하세요");
            document.form1.p_modulenm.focus();
            return;
        }
        if (realsize(document.form1.p_modulenm.value) > 50) {
            alert("모듈명은 한글기준 25자를 초과하지 못합니다.");
            document.form1.p_modulenm.focus();
            return;
        }
        
        document.form1.action = "/servlet/controller.system.MenuSubAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.system.MenuSubAdminServlet";
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
    <input type = "hidden" name = "p_seq"         value = "">
    <input type = "hidden" name = "p_systemgubun" value = "<%=v_systemgubun%>">

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
            <td height="25" class="table_02_2">* 시스템 자동부여</td>
          </tr>
          <tr> 
            <td height="26" class="table_title">서블릿</td>
            <td height="25" class="table_02_2"><input name="p_servlet" type="text" class="input" size="60"></td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>모듈명</strong></td>
            <td height="25" class="table_02_2"><input name="p_modulenm" type="text" class="input" size="60"></td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title" colspan='2'><strong>권한</strong></td>
          </tr>
<%
            for(int i = 0; i < list2.size(); i++) {
                GadminData data2  = (GadminData)list2.get(i);
                v_gadmin   = data2.getGadmin();
                v_gadminnm = data2.getGadminnm();
%>
          <tr class="table_02_2"> 
            <td height="26" class="table_title" ><strong><%=v_gadminnm%></strong></td>
            <td height="25" class="table_02_2">
               <input type = "hidden"   name = "p_gadmin<%=i%>"  value = "<%=v_gadmin%>">
               <input type = "checkbox" name = "p_controlR<%=i%>"  value = "r"> R
               <input type = "checkbox" name = "p_controlW<%=i%>"  value = "w"> W
			</td>
          </tr>
<%
            }
%>
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
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td class=sub_title>프로세스코드</td>
          </tr>
        </table>
        <!----------------- List Title 끝 ----------------->

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" height="25" class="table_title"><b>메뉴코드</b></td>
            <td width="10%" class="table_title"><b>번호</b></td>
            <td width="30%" class="table_title"><b>서블릿</b></td>
            <td width="30%" class="table_title"><b>모듈이름</b></td>
          </tr>

<%
            for(int i = 0; i < list.size(); i++) {

                MenuSubData data  = (MenuSubData)list.get(i);
                v_menu      = data.getMenu();
                v_servlet   = data.getServlet();
                v_seq       = data.getSeq();
                v_modulenm  = data.getModulenm();

%>
          <tr> 
            <td height="25" class="table_02_5"> <%=v_menu%> </td>
            <td class="table_02_5"><%=v_seq%></td>
            <td class="table_02_5"><%=v_servlet%></td>
            <td class="table_02_5"><%=v_modulenm%></td>
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
