<%
//**********************************************************
//  1. 제      목: 메뉴 리스트
//  2. 프로그램명 : za_Menu_L.jsp
//  3. 개      요: 메뉴 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 6
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

    String  v_searchtext    = box.getString("p_searchtext");

//    String v_grcode = box.getString("p_grcode");
    String v_grcode = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_upper  = box.getString("p_upper");
    String v_parent = box.getString("p_parent");
    int    v_levels = box.getInt("p_levels");
    if (v_levels == 0) {v_levels=1;}
    if (v_levels == 1) {v_upper = "";}

    /*==========   상위메뉴정보     ======================*/
    String v_up_menunm = "";
    String v_up_upper  =  "";
    String v_up_parent =  "";
    String v_printupper   = "";
    String v_printuppernm = "";

    if (!v_upper.equals("")) {
        MenuData updata  = MenuAdminBean.getMenuInfo(v_grcode,v_upper);
        if (updata != null) {
            v_up_menunm =  updata.getMenunm();
            v_up_upper  =  updata.getUpper();
            v_up_parent =  updata.getParent();

            v_printupper   = "상위메뉴코드 : <a href=\"javascript:move_list('" + v_up_upper + "', '" + v_up_parent + "', '" + (v_levels-1) + "')\"><strong>" + v_upper + "</strong></a>";
            v_printuppernm = "상위메뉴명 : " + v_up_menunm ;
        }
    } else {
        v_parent = "";         // 최상위단계에서는 부모값이 없음
    }

    String v_menu        = "";
    String v_menunm      = "";
    String v_pgm         = "";
    String v_para1       = "";
    String v_para2       = "";
    String v_para3       = "";
    String v_para4       = "";
    String v_para5       = "";
    String v_para6       = "";
    String v_para7       = "";
    String v_para8       = "";
    String v_para9       = "";
    String v_para10      = "";
    String v_para11      = "";
    String v_para12      = "";
    String v_isdisplay   = "";
    String v_isdisplaynm = "";
    int    v_orders      = 0;

    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function move_list(menu, parent, levels) {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_searchtext.value = "";
        document.form1.p_levels.value     = levels;
        document.form1.p_upper.value      = menu;
        document.form1.p_parent.value     = parent;
        document.form1.p_process.value    = "select";
        document.form1.submit();
    }

    function view(menu) {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_menu.value   = menu;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    function search() {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_grcode"  value = "<%=v_grcode%>">
    <input type = "hidden" name = "p_menu"    value = "">
    <input type = "hidden" name = "p_levels"  value = "<%=v_levels%>">
    <input type = "hidden" name = "p_upper"   value = "<%=v_upper%>">
    <input type = "hidden" name = "p_parent"  value = "<%=v_parent%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title01.gif" ></td>
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
                  <td height="7"></td>
                </tr>
                <tr> 
                  <td height="26" style="padding-left=10;padding-right=10"><%=v_printupper%> </td>
                </tr>
              </table>
              <table class="form_table_bg" cellspacing="0" cellpadding="0" >
                <tr valign="middle"> 
                  <td width="200" height="26" style="padding-left=10;padding-right=10"><%=v_printuppernm%>&nbsp;&nbsp; </td>
                  <td width="490" align="right">메뉴명 
                    <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>"> 
                  </td>
                  <td width="245" style="padding-left=10;padding-right=10"> 
                    <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border=0></a>
                    <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border=0></a>
                    <a href='/servlet/controller.system.MenuAuthAdminServlet?p_process=updatePage&p_grcode=<%=v_grcode%>'><img src="/images/admin/button/btn_menuright.gif" border=0></a>
                  </td>
                </tr>
                <tr> 
                  <td height="7" colspan="3"></td>
                </tr>
              </table></td>
          </tr>
        </table>
        <br>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//관심버튼 클릭
function whenSimpleSave(menu) {

	$.post("/servlet/controller.common.AjaxServlet"
			, {	 sqlNum:"adminMenu.updateUse",rerurnFunction:"search"
				, p_menu:menu
			}
			, function(data) {
				$("#ajaxDiv").html(data);
			});
}
//-->
</SCRIPT>
<div id="ajaxDiv"></div>

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" height="25" class="table_title"><b>메뉴코드</b></td>
            <td width="25%" class="table_title"><b>메뉴코드명</b></td>
            <td width="30%" class="table_title"><b>관련 Program</b></td>
            <td width="10%" class="table_title"><b>메뉴순서</b></td>
            <td width="10%" class="table_title"><b>사용여부</b></td>
            <td width="10%" class="table_title"><b>하위코드</b></td>
          </tr>

<%
            for(int i = 0; i < list.size(); i++) {

                MenuData data  = (MenuData)list.get(i);
                v_menu      = data.getMenu();
                v_menunm    = data.getMenunm();
                v_upper     = data.getUpper();
                v_parent    = data.getParent();
                v_pgm       = data.getPgm();
                v_para1     = data.getPara1();
                v_para2     = data.getPara2();
                v_para3     = data.getPara3();
                v_para4     = data.getPara4();
                v_para5     = data.getPara5();
                v_para6     = data.getPara6();
                v_para7     = data.getPara7();
                v_para8     = data.getPara8();
                v_para9     = data.getPara9();
                v_para10    = data.getPara10();
                v_para11    = data.getPara11();
                v_para12    = data.getPara12();
                v_isdisplay = data.getIsdisplay();

                v_orders    = data.getOrders();

                if (v_isdisplay.equals("Y")) v_isdisplaynm = "사용";
                else v_isdisplaynm = "미사용";

%>
          <tr> 
            <td height="25" class="table_01"> <%=v_menu%> </td>
            <td class="table_02_1"><a href="javascript:view('<%=v_menu%>')"><%=v_menunm%></a></td>
            <td class="table_02_1"><%=v_pgm+" &"+v_para1+"&"+v_para2+"&"+v_para3+"&"+v_para4+"&"+v_para5+"&"+v_para6+" &"+v_para7+"&"+v_para8+"&"+v_para9+"&"+v_para10+"&"+v_para11+"&"+v_para12%></td>
            <td class="table_02_1"><%=v_orders%></td>
            <td class="table_02_1"><a href="javascript:whenSimpleSave('<%=v_menu%>')"><%=v_isdisplaynm%></a></td>
            <td class="table_03_1"><a href="javascript:move_list('<%=v_menu%>','<%=v_parent%>','<%=v_levels+1%>')"><img src="/images/admin/button/b_downcode.gif" align="absmiddle" border="0"></a></td>
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
