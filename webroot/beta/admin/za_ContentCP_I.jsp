<%
//**********************************************************
//  1. 제      목: 업체 관리-등록(베타테스트시스템)
//  2. 프로그램명 : za_ContentCP_I.jsp
//  3. 개      요: 업체 관리-등록(베타테스트시스템)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.beta.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_pageno    = box.getString("p_pageno");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String ss_gadmin    = box.getStringDefault("s_gadmin","ALL");       //gadmin

%>

<html>
<head>
<title>::: 현대기아자동차 통합교육관리시스템 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function insert_check() {
    var ff = document.form1;
    var compno1=ff.p_compno1.value;
    var compno2=ff.p_compno2.value;
    var compno3=ff.p_compno3.value;
    
        if (realsize(compno1) < 3 ){
            alert("사업자등록번호 길이가 맞지 않습니다. \r\n다시한번 확인하시기 바랍니다.");
            ff.p_compno1.focus();
            return;
        } else if(blankCheck(compno1)) {
            alert("첫번째 사업자 등록번호를 입력하십시오.");
            ff.p_compno1.focus();
            return;
        } else if(realsize(compno2) < 2) {
            alert("사업자등록번호 길이가 맞지 않습니다. \r\n다시한번 확인하시기 바랍니다.");
            ff.p_compno2.focus();
            return;
        } else if(blankCheck(compno2)){
            alert("두번째 사업자 등록번호를 입력하십시오.");
            ff.p_compno2.focus();
            return;
        }
        if(realsize(compno3) < 5) {
            alert("사업자등록번호 길이가 맞지 않습니다.\r\n 다시한번 확인하시기 바랍니다.");
            ff.p_compno3.focus();
            return;
        } else if(blankCheck(compno3)){
            alert("세번째 사업자 등록번호를 입력하십시오.");
            ff.p_compno3.focus();
            return;
        }
        
        if (blankCheck(ff.p_compnm.value)) {
            alert("회사명을 입력하십시오.");
            ff.p_compnm.focus();
            return;
        }
        if (blankCheck(ff.p_userid.value)) {
            alert("담당자ID를 입력하십시오.");
            ff.p_userid.focus();
            return;
        }
        if (blankCheck(ff.p_usernm.value)) {
            alert("담당자성명을 입력하십시오.");
            ff.p_usernm.focus();
            return;
        }
        
        if (blankCheck(ff.p_passwd.value)) {
            alert("비밀번호를 입력하십시오.");
            ff.p_passwd.focus();
            return;
        } else if (blankCheck(ff.p_passwd1.value)) {
            alert("비밀번호 확인을 입력하십시오.");
            ff.p_passwd1.focus();
            return;
        }
        if (document.form1.p_passwd.value != document.form1.p_passwd1.value ) {
            alert("비밀번호가 서로 일치하지 않습니다.");
            document.form1.p_passwd.focus();
	        return;
	    }
        
        
        document.form1.p_compno.value   = document.form1.p_compno1.value+"-"+document.form1.p_compno2.value+"-"+document.form1.p_compno3.value;
        document.form1.action = "/servlet/controller.beta.ContentCPServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.beta.ContentCPServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
    
    
    function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }
// 숫자만 들어가게 만든다.
    function onlyNumber()
    {
        if(((event.keyCode<48)||(event.keyCode>57)))
        event.returnValue=false;
    }
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>

<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno%>">
    <input type = "hidden" name = "s_gadmin"     value = "<%=ss_gadmin%>">
    <input type = "hidden" name = "p_compno"     value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/b_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 업체등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr width="20%"> 
                <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
                <td class="table_title">사업자등록번호</td>
                <td class="table_02_2"><input type="text" name="p_compno1" onkeyup="moveFocus(3,this,this.form.p_compno2);" size="3" maxlength="3" OnKeypress="onlyNumber();">
                                      - <input type="text" name="p_compno2" onkeyup="moveFocus(2,this,this.form.p_compno3);" size="2" maxlength="2" OnKeypress="onlyNumber();">
                                      - <input type="text" name="p_compno3" onkeyup="moveFocus(5,this,this.form.p_compnm);" size="5" maxlength="5" OnKeypress="onlyNumber();"></td>
          </tr>
          <tr>
                <td class="table_title">회사명</td>
                <td class="table_02_2"><input type="text" name="p_compnm" size="20" maxlength="20"></td>
          </tr>
          <tr>
                <td class="table_title">담당자ID</td>
                <td class="table_02_2"><input type="text" name="p_userid" size="10" maxlength="10"></td>
          </tr>
          <tr>
                <td class="table_title">담당자성명</td>
                <td class="table_02_2"><input type="text" name="p_usernm" size="10" maxlength="10"></td>
          </tr>
          <tr>
                <td class="table_title">비밀번호</td>
                <td class="table_02_2"><input type="password" name="p_passwd" size="10" maxlength="10"></td>
          </tr>
          <tr>
                <td class="table_title">비밀번호확인</td>
                <td class="table_02_2"><input type="password" name="p_passwd1" size="10" maxlength="10"></td>
          </tr>
          <tr>
                <td class="table_title">홈페이지</td>
                <td class="table_02_2"><input type="text" name="p_homesite" size="50" maxlength="50"></td>
          </tr>
          <tr>
                <td class="table_title">주소</td>
                <td class="table_02_2"><input type="text" name="p_address" size="70" maxlength="70"></td>
          </tr>
          <tr>
                <td class="table_title">E-mail</td>
                <td class="table_02_2"><input type="text" name="p_email" size="30" maxlength="30"></td>
          </tr>
          <tr>
                <td class="table_title">연락처</td>
                <td class="table_02_2"><input type="text" name="p_tel" size="12" maxlength="12" OnKeypress="onlyNumber();"></td>
          </tr>
	    </table>
        <!----------------- 업체등록 끝 ----------------->
        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
