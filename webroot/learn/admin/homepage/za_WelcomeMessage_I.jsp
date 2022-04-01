<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_I.jsp
//  3. 개      요: 과정차수별공지사항 과정차수별등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String s_username  = box.getSession("name");
    
    String  v_process  = box.getString("p_process");

    int     v_pageno      = box.getInt("p_pageno");
    
    int     v_year       = box.getInt("p_year");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    
    String  v_content       = "";
    String  v_name          = "";
    String  v_type          = "";
    String  v_startdate     = "";
    String  v_enddate       = "";
    String  v_month         = "";
    
%>

<html>
<head>
<title>::: 명언 노출 :::</title>
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
	$("#p_startdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_enddate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

    // 저장
    function insert_check() {

        var frm = document.form1;
        var obj = document.getElementsByName("p_type");

        for (var i = 0 ; i < obj.length ; i ++ ) {
            if(obj[i].checked){
                if ( obj[i].value == "B1") {
                	if (frm.p_year.options[0].selected) {
                        alert("년도를 선택하세요.");
                        frm.p_year.focus();
                        return;
                    }
                    if (frm.p_month.options[0].selected) {
	                    alert("월을 선택하세요.");
	                    frm.p_month.focus();
	                    return;
                    }
                }

                if ( obj[i].value == "A1" && ( frm.p_startdate.value == "" || frm.p_enddate.value == "")) {
                    alert("일자을 선택하세요.");
                    frm.p_startdate.focus();
                    return;
                }
            }
        }

        if (realsize(frm.p_content.value) > 2000) {
            alert("내용은 한글기준 1000자를 초과하지 못합니다.");
            frm.p_content.focus();
            return;
        }

        if (frm.p_content.value == "") {
            alert("내용을 입력하세요.");
            frm.p_content.focus();
            return;
        }
        
        frm.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        frm.p_process.value = "insert";
        frm.submit();
    }

    // 전체목록으로 이동
    function list() {
    	var frm = document.form1;
        
        frm.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        frm.p_process.value = "selectList";
        frm.submit();
    }

    function selectType(p) {

    	var frm = document.form1;
    	var typeObj    = document.getElementsByName("p_type");
        var typeObjLen = typeObj.length;
            
    	if (p == "A1") {
        	frm.p_startdate.disabled = false;
        	frm.p_enddate.disabled = false;
        	frm.p_year.disabled = true;
        	frm.p_month.disabled = true;
    	} else {
    		frm.p_startdate.disabled = true;
            frm.p_enddate.disabled = true;
            frm.p_year.disabled = false;
            frm.p_month.disabled = false;
    	}
    }
//
-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="selectType('A1');">
<form name="form1" method="post" enctype="multipart/form-data" >
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.1_13.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

        <!----------------- 전체공지 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td width="35%" height="25" class="table_02_2"><%=s_username%></td>
            <td width="15%" class="table_title"><strong>작성일</strong></td>
            <td width="35%" height="25" class="table_02_2">
              <%= FormatDate.getDate("yyyy/MM/dd") %>
            </td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>노출 설정</strong></td>
            <td colspan="3" height="25" class="table_02_2">
              <table>
                <tr>
                  <td width="40%">
                    <input type="radio" name="p_type" value="A1" checked onclick="selectType('A1');"/>기간별 설정
                  </td>
                  <td>
                    <input name="p_startdate" id="p_startdate" type="text" class="datepicker_input1" size="10" value="<%=v_startdate%>" disabled> 일 ~
                    <input name="p_enddate" id="p_enddate" type="text" class="datepicker_input1" size="10" value="<%=v_enddate%>"> 일
                  </td>  
                </tr>
                <tr>
                  <td width="40%">
                    <input type="radio" name="p_type" value="B1" onclick="selectType('B1');">월별 설정
                  </td>
                  <td>
                    <select name="p_year" disabled>
                        <option value="">선택</option>
                        <%
                        GregorianCalendar   calendar    = new GregorianCalendar();
                        int currentYear = 0 ;
                        
                        currentYear = calendar.get(Calendar.YEAR);
                        
                        for(int i = 0 ; i < 2 ; i++) {
                        %>
                        	<option value="<%=currentYear +i%>" <%=currentYear +i == currentYear ? "selected" : "" %>><%=currentYear +i %></option>
                        <% }%>	
                    </select>년 
                    <select name="p_month" disabled>
                      <option value ="">선택</option>
                      <%  for(int i = 1 ; i <= 12 ; i++) { %>
                      <option value = "<%=i < 10 ? "0"+i : i %>"><%=i %></option>  
                      <%  }%> 
                    </select>월
                  </td>
                </tr>
              </table>
              
            </td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td colspan="3" height="25" class="table_02_2">
                <textarea name="p_content" style="width:100%;height:50px"></textarea>
            </td>
          </tr>

        </table>
        <!----------------- 전체공지 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width=8></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
