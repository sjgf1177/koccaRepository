<%
//**********************************************************
//  1. 제      목: 운영자관리
//  2. 프로그램명 : za_Manager_L.jsp
//  3. 개      요: 운영자관리 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
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
    String  v_process     = box.getString("p_process");
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
    String  gadmin        = box.getSession("gadmin");  // 권한
    String  ss_gadmin     = box.getStringDefault("s_gadmin","ALL");       //gadmin select
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

    String v_userid    = "";
    String v_gadmin    = "";
    String v_gadminnm  = "";
    String v_grtype    = "";
    String v_isdeleted = "";
    String v_fmon      = "";
    String v_tmon      = "";
    String v_commented = "";
    String v_name       = "";

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
	// 운영자 등록
    function insert() {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
	// 상세 화면 이동
    function view(userid, gadmin) {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_userid.value   = userid;
        document.form1.p_gadmin.value  = gadmin;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

	// 리스트 조회
    function search() {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
	
	// 권한 수정
    function changeGadmin() {
        document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
    
    // 정렬
    function whenOrder(column) {
	
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	    document.form1.s_action.value = "go";
        document.form1.action="/servlet/controller.system.ManagerAdminServlet";	
        document.form1.p_process.value = "select";   
        document.form1.p_orderColumn.value = column;
    
        document.form1.submit();
    }
//-->
</SCRIPT>


</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_userid"   value = "">
    <input type = "hidden" name = "p_gadmin"  value = "">
    <input type = "hidden" name = "p_orderColumn"   value = "">
    <input type = "hidden" name = "p_orderType"     value = "<%=v_orderType%>">
    <input type = "hidden" name = "p_action"        value = "">
    <input type = "hidden" name = "s_action"        value = "">
<%
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
%>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
	    document.form1.p_pageno.value = index;
	    document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
	    document.form1.p_process.value = "select";
	    document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.system.ManagerAdminServlet";
		document.form1.p_process.value = "select";
		document.form1.submit();
	}
	function pagesize(pageSize) {
	    document.form1.target = "_self";
	    document.form1.p_pageno.value = 1;
	    document.form1.p_pagesize.value = pageSize;
	    document.form1.action = '/servlet/controller.system.ManagerAdminServlet';
	    document.form1.p_process.value = "select";
	    document.form1.submit();
	}
//-->
</SCRIPT>
    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

        <!----------------- 관리자검색 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="65" align="left">
              <%= GadminAdminBean.getGadminSelectGrtype("s_gadmin",ss_gadmin,"onChange=\"changeGadmin()\"",gadmin,1) %>
            </td>
            <td width="834" align="right">
              <select name="p_search">
                <option value='name' <% if (v_search.equals("name")) out.println("selected"); %> >성명</option>
                <option value='userid' <% if (v_search.equals("userid")) out.println("selected"); %> >ID</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
            </td>
			<td width=7></td>
            <td width="32" align="right"><a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0"></a></td>
			<td width=7></td>
            <td width="32"><a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a></td> 
          </tr>
          <tr>
            <td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
          </tr>
        </table>
        <!----------------- 관리자검색 끝 ----------------->

        <!----------------- 관리자 리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td width="13%" class="table_title"><a href="javascript:whenOrder('userid')" class="e"><b>ID</b></a></td>
            <td width="15%" class="table_title"><a href="javascript:whenOrder('name')" class="e"><b>성명</b></a></td>
            <td width="17%" class="table_title"><b>권한</b></td>
          </tr>
<%

            for(int i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);
                
                v_userid    = dbox.getString("d_userid");
                v_gadmin    = dbox.getString("d_gadmin");
                v_gadminnm  = dbox.getString("d_gadminnm");
                v_grtype      = dbox.getString("d_grtype");
                v_isdeleted = dbox.getString("d_isdeleted");
                v_fmon      = dbox.getString("d_fmon");
                v_tmon      = dbox.getString("d_tmon");
                v_commented = dbox.getString("d_commented");

                v_name      = dbox.getString("d_name");

                v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");
                v_totalrowcount = dbox.getInt("d_totalrowcount");

%>

          <tr>
            <td class="table_01" height="25"><%=i+1%></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1"><a href="javascript:view('<%=v_userid%>','<%=v_gadmin%>')"><%=v_gadminnm%></a></td>
          </tr>
<%
        }
%>

        </table>
        <!----------------- 관리자 리스트 끝 ----------------->
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->
       <br>

      </td>
  </tr>
</table>
</form>
</body>
</html>
