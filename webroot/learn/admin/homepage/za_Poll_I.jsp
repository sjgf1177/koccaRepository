<%
//**********************************************************
//  1. 제      목: Poll
//  2. 프로그램명 : za_Poll_I.jsp
//  3. 개      요: Poll 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process    = box.getString("p_process");
    String v_searchtext = box.getString("p_searchtext");

%>
<html>
<head>
<title>eIBIs</title>
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
	$("#p_ldate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

    function insert_check() {
        var checkcnt = 0;
        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_ldate.value);
        
        if(document.form1.s_grcode.value == '----' || document.form1.s_grcode.value == '' ){
          alert("교육그룹을 선택하여 주십시오");
          return;
        }
        
        if (blankCheck(document.form1.p_title.value)) {
            alert("질문을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }

        if (realsize(document.form1.p_title.value) > 150) {
            alert("질문은 한글기준 150자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

        if(blankCheck(document.form1.p_sdate.value)){
        alert("설문 시작일을 입력하세요.");
        return;
      }

        if(blankCheck(document.form1.p_ldate.value)){
        alert("설문 마감일을 입력하세요.");
        return;
        }

        if(st_date > ed_date){
        alert("polling 시작일이 마감일보다 큽니다.");
        return;
        }

       for(i = 0; i < document.form1.elements.length; i++) {
           if(document.form1.elements[i].name == 'p_seltxt') {
               if (!(document.form1.elements[i].value== '') ) checkcnt++;
           }
       }

        if (checkcnt < 2 ) {
            alert("지문은 두개이상 입력되어야합니다.");
            return;
        }

        quotation_chk();
        document.form1.p_started.value = st_date;
        document.form1.p_ended.value   = ed_date;
        document.form1.p_searchtext.value = "";

        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
    
    function whenSelection() {
        
    }

//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "post">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext%>">
		<input type = "hidden" name = "p_started"    value = "">
		<input type = "hidden" name = "p_ended"      value = "">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/homepage/title_bg.gif" class="page_title"><img src="/images/admin/homepage/homepage_title2.gif" border="0"></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- CyberPoll관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="18%" height="25" ><b>질문번호</b></td>
            <td class="table_02_2"  width="79%"> * 시스템 자동 부여</td>
          </tr>
		  <tr>
            <td width="18%" height="25" class="table_title"><b>교육그룹</b></td>
			<td class="table_02_2" width="79%">
				<!------------------- 교육그룹 시작 ------------------------->
				<%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
			</td>
		  </tr>
          <tr>
            <td class="table_title" height="25"><b>질문</b></td>
            <td class="table_02_2">
              <input name="p_title" type="text" class="input" size="112">
            </td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>기간설정</b></td>
            <td class="table_02_2">
              <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value=''>
              ~
              <input name="p_ldate" id="p_ldate" class="datepicker_input1" type="text" size="10" value=''>
            </td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>지문</b></td>
            <td class="table_02_2">

              <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
               for(int i = 1; i < 11; i++) {
%>
                <tr>
                  <td><b><%=i%></b>
                    <input name="p_seltxt" type="text" class="input" size="110">
                  </td>
                </tr>
               <tr><td height="4"></td></tr>
<%
              }
%>
              </table>

            </td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>메인등록</b></td>
            <td class="table_02_2">
              <input type="radio" name="p_f_use" value="Y" checked>메인등록
              <input type="radio" name="p_f_use" value="N">등록안함
            </td>
          </tr>
        </table>
        <!----------------- CyberPoll관리 끝 ----------------->




        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr><td height="6"></td></tr>
          <tr>
            <td>* 지문 1,2는 반드시 입력해 주세요.</td>
          </tr>
        </table>

        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;&nbsp;</td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
        <br>

      </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     



</table>

</form>
</body>
</html>
