<%
//**********************************************************
//  1. 제      목: 메뉴관리
//  2. 프로그램명 : za_Menu_U.jsp
//  3. 개      요: 메뉴관리 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
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

    String v_searchtext = box.getString("p_searchtext");

//    String v_grcode = box.getString("p_grcode");
    String v_grcode = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_upper  = box.getString("p_upper");
    String v_parent = box.getString("p_parent");
    int    v_levels = box.getInt("p_levels");

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

            v_printupper   = "          <tr>                                                                                   ";
            v_printupper  += "            <td width='15%' height='26' class='table_title'><strong>상위메뉴코드</strong></td>     ";
            v_printupper  += "            <td class='table_02_2'>"+v_upper+"</td>                                              ";
            v_printupper  += "          </tr>                                                                                  ";

            v_printuppernm   = "          <tr>                                                                                 ";
            v_printuppernm  += "            <td width='15%' height='26' class='table_title'><strong>상위메뉴코드명</strong></td> ";
            v_printuppernm  += "            <td class='table_02_2'>"+v_up_menunm+"</td>                                        ";
            v_printuppernm  += "          </tr>                                                                                ";
        }
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
    String v_systemgubun = "";
    String v_systemnm    = "";
    int    v_orders      = 0;

    String v_isdisplay_chk1  = "";
    String v_isdisplay_chk2  = "";


    MenuData data = (MenuData)request.getAttribute("selectMenu");

    v_menu      = data.getMenu();
    v_menunm    = data.getMenunm();
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
    v_systemgubun = data.getSystemgubun();

    if ("Y".equals(v_isdisplay)) v_isdisplay_chk1 = "checked";
    else v_isdisplay_chk2 = "checked";
    
//    if (v_systemgubun.equals("1")) v_systemnm = "메인시스템";
//    else if(v_systemgubun.equals("2")) v_systemnm= "게이트시스템";
    
    ArrayList list = (ArrayList)request.getAttribute("selectList");
    if (list == null) list = new ArrayList();
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
        if (document.form1.p_menunm.value == "") {
            alert("메뉴명를 입력하세요");
            document.form1.p_menunm.focus();
            return;
        }
        if (realsize(document.form1.p_menunm.value) > 40) {
            alert("메뉴명는 한글기준 20자를 초과하지 못합니다.");
            document.form1.p_menunm.focus();
            return;
        }
        if (!numeric_chk(document.form1.p_orders)) return;

        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
    
    function view(menu) {
        document.form1.action = "/servlet/controller.system.MenuAdminServlet";
        document.form1.p_menu.value   = menu;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_grcode"      value = "<%=v_grcode%>">
    <input type = "hidden" name = "p_levels"      value = "<%=v_levels%>">
    <input type = "hidden" name = "p_upper"       value = "<%=v_upper%>">
    <input type = "hidden" name = "p_parent"      value = "<%=v_parent%>">
    <input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_menu"        value = "<%=v_menu%>">

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
        <!----------------- form 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <%=v_printupper%>
          <%=v_printuppernm%>
          <tr class="table_02_2"> 
            <td height="26" class="table_title" ><strong>메뉴코드</strong></td>
            <td height="25" class="table_02_2"><%=v_menu%></td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>메뉴코드명</strong></td>
            <td height="25" class="table_02_2"><input name="p_menunm" type="text" class="input" size="130" value='<%=v_menunm%>'></td>
          </tr>
          <tr> 
            <td height="26" class="table_title"><strong>관련 Program</strong></td>
            <td height="25" class="table_02_2"><input name="p_pgm" type="text" class="input" size="130" value='<%=v_pgm%>'></td>
          </tr>
          <tr> 
            <td height="26" class="table_title"><strong>Parameter</strong></td>
            <td height="25" class="table_02_2">
              <input name="p_para1" type="text" class="input" size="18" value='<%=v_para1%>'> & 
              <input name="p_para2" type="text" class="input" size="18" value='<%=v_para2%>'> & 
              <input name="p_para3" type="text" class="input" size="18" value='<%=v_para3%>'> & 
              <input name="p_para4" type="text" class="input" size="18" value='<%=v_para4%>'> & 
              <input name="p_para5" type="text" class="input" size="18" value='<%=v_para5%>'> & 
              <input name="p_para6" type="text" class="input" size="18" value='<%=v_para6%>'> & 
              <input name="p_para7" type="text" class="input" size="18" value='<%=v_para7%>'> & 
              <input name="p_para8" type="text" class="input" size="18" value='<%=v_para8%>'> &
              <input name="p_para9" type="text" class="input" size="18" value='<%=v_para9%>'> & 
              <input name="p_para10" type="text" class="input" size="18" value='<%=v_para10%>'> & 
              <input name="p_para11" type="text" class="input" size="18" value='<%=v_para11%>'> & 
              <input name="p_para12" type="text" class="input" size="18" value='<%=v_para12%>'>
            </td>
          </tr>
          <tr> 
            <td width="15%" height="26" class="table_title"><strong>메뉴순서</strong></td>
            <td height="25" class="table_02_2"><input name="p_orders" type="text" class="input" size="18" value='<%=v_orders%>'> 
            </td>
          </tr>
          <tr>
            <td height="26" class="table_title"><strong>사용여부</strong></td>
            <td height="26" align="left" valign="absmiddle" class="table_02_2"> 
              <input type="radio" name="p_isdisplay" value="Y" <%=v_isdisplay_chk1%>>사용 
              <input type="radio" name="p_isdisplay" value="N" <%=v_isdisplay_chk2%>>미사용
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
		     <td width="1%"><img src="/images/admin/common/icon.gif" ></td>  
            <td class=sub_title>메뉴코드</td>
          </tr>
        </table>
        <!----------------- List Title 끝 ----------------->

        <!----------------- List 시작 ----------------->
        <table width="97%" border="0" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="20%" height="25" class="table_title"><b>메뉴코드</b></td>
            <td width="30%" class="table_title"><b>메뉴코드명</b></td>
            <td width="30%" class="table_title"><b>관련 Program</b></td>
            <td width="10%" class="table_title"><b>메뉴순서</b></td>
            <td width="10%" class="table_title"><b>사용여부</b></td>
          </tr>

<%
            for(int i = 0; i < list.size(); i++) {

                MenuData data2  = (MenuData)list.get(i);
                v_menu      = data2.getMenu();
                v_menunm    = data2.getMenunm();
                v_upper     = data2.getUpper();
                v_parent    = data2.getParent();
                v_pgm       = data2.getPgm();
                v_para1     = data2.getPara1();
                v_para2     = data2.getPara2();
                v_para3     = data2.getPara3();
                v_para4     = data2.getPara4();
                v_para5     = data2.getPara5();
                v_para6     = data2.getPara6();
                v_para7     = data2.getPara7();
                v_para8     = data2.getPara8();
                v_para9     = data2.getPara9();
                v_para10    = data2.getPara10();
                v_para11    = data2.getPara11();
                v_para12    = data2.getPara12();
                v_isdisplay = data2.getIsdisplay();
                v_orders    = data2.getOrders();

                if (v_isdisplay.equals("Y")) v_isdisplaynm = "사용";
                else v_isdisplaynm = "미사용";
%>
          <tr> 
            <td height="25" class="table_01"><%=v_menu%></td>
            <td class="table_02_1"><a href="javascript:view('<%=v_menu%>')"><%=v_menunm%></a></td>
            <td class="table_02_1"><%=v_pgm+" &"+v_para1+"&"+v_para2+"&"+v_para3+"&"+v_para4+"&"+v_para5+"&"+v_para6+" &"+v_para7+"&"+v_para8+"&"+v_para9+"&"+v_para10+"&"+v_para11+"&"+v_para12%></td>
            <td class="table_02_1"><%=v_orders%></td>
            <td class="table_02_1"><%=v_isdisplaynm%></td>
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
