<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_L.jsp
//  3. 개      요: 선택과정차수별 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	int     v_pageno   = box.getInt("p_pageno");
	int     v_pagesize = box.getInt("p_pagesize");
	int     v_totalrowcount =  0;

    int     v_seq       = 0;
    String  v_title     = "";
    String  v_strdate   = "";
    String  v_enddate   = "";
    String  v_answer    = "";
    String  v_userid    = "";
    String  v_name      = "";
    String  v_win_yn    = "";
    String  v_indate    = "";
    
    String v_tempAnswer = "";
    
    int     v_selEvent     = box.getInt("p_selEvent");
    String  v_selWinYn     = box.getString("p_selWinYn");
    String  v_selStrDate   = box.getString("p_startdate");
    String  v_selEndDate   = box.getString("p_enddate");
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    
    ArrayList selectRegList    = (ArrayList)request.getAttribute("selectRegList");       // 이벤트 신청자 리스트
    ArrayList eventList        = (ArrayList)request.getAttribute("eventList");           // 이벤트 목록
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<title>이벤트 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
         document.form1.p_process.value = "selectRegList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
         document.form1.p_process.value = "selectRegList";
         document.form1.submit();
    }

    function search() {
    	var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_edate.value);

        document.form1.p_startdate.value  = st_date;
        document.form1.p_enddate.value    = ed_date;

        document.form1.action = "/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "selectRegList";
        document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "selectRegList";
        document.form1.submit();
    }

  //정렬
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_process.value = 'selectRegList';    
        document.form1.p_orderColumn.value = column;
        
        document.form1.submit();
    }

    function goEventList() {
        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function totalWinner() {
        var totalWinYn        = document.getElementById("p_totalWinYn");

        totalWinYn.value      = "Y";

        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "updateRegList";
        document.form1.submit();
    }

    function totalLoser() {
        var totalWinYn        = document.getElementById("p_totalWinYn");

        totalWinYn.value      = "N";

        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "updateRegList";
        document.form1.submit();
    }

    function saveCurrent() {
        var totalWinYn        = document.getElementById("p_totalWinYn");

        totalWinYn.value      = "A";

        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "updateRegList";
        document.form1.submit();
    }

    function excelDown() {
    	document.form1.target = '_self';
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = "excelDown";
        document.form1.submit();
    }

    function openAnswer(answer){
        window.open("about:blank", "popUpView", "top=0, left=0, width=400, height=150, status=no, resizable=no");

        document.form1.action="/servlet/controller.homepage.EventAdminServlet";
        document.form1.p_process.value = "popupAnswer";
        document.form1.p_answer.value = answer;
        document.form1.target="popUpView";
        document.form1.submit();
        
        document.form1.target="_self";
    }
        
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "get">
    <input type = "hidden" name = "p_process" >
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_pagesize"   value = "<%=v_pagesize%>">
    <input type = "hidden" name = "p_startdate"  value = "">
    <input type = "hidden" name = "p_enddate"    value = "">
    <input type = "hidden" name = "p_seq"        value = "">
    <input type = "hidden" name = "p_orderColumn">
    <input type = "hidden" name = "p_orderType"  value="<%=v_orderType%>">
    <input type = "hidden" name = "p_totalWinYn"  id = "p_totalWinYn">
    <input type = "hidden" name = "p_answer"      id = "p_answer">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr>
            <td><img src="/images/admin/portal/s.1_15.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="250"> 
              이벤트명 <select name="p_selEvent">
              <option value="">::전체::</option>
              <%
                if (eventList != null && eventList.size() > 0) {
                	for(int i = 0 ; i < eventList.size() ; i++) {
                		DataBox dbox = (DataBox)eventList.get(i);
                		
                		v_seq   = dbox.getInt("d_seq");
                		v_title = dbox.getString("d_title");
              %>
                <option value="<%=v_seq %>" <%=v_seq == v_selEvent ? "selected" : "" %>><%=v_title %></option>
              <%
                	}
                }
              %>
              </select>
            </td>
            <td width="370" align="center">
              담청여부 <select name="p_selWinYn">
               <option value="">::전체::</option>
               <option value="Y" <%=v_selWinYn.equals("Y") ? "selected" : "" %>>당첨</option>
               <option value="N" <%=v_selWinYn.equals("N") ? "selected" : "" %>>탈락</option>
              </select>
            </td>
<td width="300" align="right">
이벤트 기간 <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_selStrDate, "yyyy/MM/dd") %>'>
~
              <input name="p_edate" id="p_edate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_selEndDate, "yyyy/MM/dd") %>'>
</td>
            <td width="100" align="right" valign="middle"> <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
              <!--a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a-->
            </td>
          </tr>
          <tr>
            <td colspan="4" align="right">
            <p>
              <a href="javascript:goEventList()"><img src="/images/admin/portal/s.2_31.gif" border="0"></a>
              <a href="javascript:totalWinner()"><img src="/images/admin/portal/s.2_32.gif" border="0"></a>
              <a href="javascript:totalLoser()"><img src="/images/admin/portal/s.2_33.gif" border="0"></a>
              <a href="javascript:saveCurrent()"><img src="/images/admin/portal/s.2_34.gif" border="0"></a>
              <a href="javascript:excelDown()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
              </p>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title"><a href="javascript:whenOrder('a.title')" class="e"><b>이벤트명</b></a></td>
            <td class="table_title" width="20%"><a href="javascript:whenOrder('a.strdate')" class="e"><b>이벤트 기간</b></a></td>
            <td class="table_title" width="15%"><b>입력내용</b></td>
            <td class="table_title" width="5%"><b>ID</b></td>
            <td class="table_title" width="5%" height="25"><b>성명</b></td>
            <td class="table_title" width="8%" ><b>당첨여부</b></td>
            <td class="table_title" width="8%" ><a href="javascript:whenOrder('a.indate')" class="e"><b>참여일시</b></a></td>
          </tr>

          <%
            for(int i = 0; i < selectRegList.size(); i++) {

                DataBox dbox = (DataBox)selectRegList.get(i);

                v_seq      = dbox.getInt("d_seq");
                v_title    = dbox.getString("d_title");
                v_strdate  = dbox.getString("d_strdate");
                v_enddate  = dbox.getString("d_enddate");
                v_answer   = dbox.getString("d_answer");
                v_indate   = dbox.getString("d_ldate");
                v_userid   = dbox.getString("d_userid");
                v_name     = dbox.getString("d_name");
                v_win_yn   = dbox.getString("d_win_yn");

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                
                v_tempAnswer = v_answer.toString();
                
                v_tempAnswer    = StringUtil.getByteString(v_tempAnswer, 30);

%>
          <tr>
            <td class="table_01" ><%=v_dispnum %></td>
            <td class="table_02_2"><%=v_title%></td>
            <td class="table_02_1"><%=FormatDate.getFormatDate(v_strdate,"yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd")%></td>
            <td class="table_02_1" ><a href="javascript:openAnswer('<%=v_answer%>');"><%=v_tempAnswer%></a></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1" >
              <select name="p_flagYn">
                <option value="Y" <%=v_win_yn.equals("Y") ? "selected" : "" %>>당첨</option>
                <option value="N" <%=!v_win_yn.equals("Y") ? "selected" : "" %>>탈락</option>
              </select>
              <input type="hidden" name="ary_seq"    value="<%=v_seq %>">
              <input type="hidden" name="ary_userid" value="<%=v_userid %>">
            </td>
            <td class="table_02_1"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd HH:mm ss")%></td>
          </tr>
<%
        }
%>
        </table>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">
            <p>
              <a href="javascript:goEventList()"><img src="/images/admin/portal/s.2_31.gif" border="0"></a>
              <a href="javascript:totalWinner()"><img src="/images/admin/portal/s.2_32.gif" border="0"></a>
              <a href="javascript:totalLoser()"><img src="/images/admin/portal/s.2_33.gif" border="0"></a>
              <a href="javascript:saveCurrent()"><img src="/images/admin/portal/s.2_34.gif" border="0"></a>
              <a href="javascript:excelDown()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
              </p>
            </td>
          </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

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

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>

