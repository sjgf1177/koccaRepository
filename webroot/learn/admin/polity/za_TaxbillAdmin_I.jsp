 <%
//**********************************************************
//  1. 제      목: 계산서 등록
//  2. 프로그램명: za_TaxbillAdmin_I
//  3. 개      요: 계산서 등록(팝업)
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.18
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

    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");

    String  ss_grcode			= box.getString("s_grcode");		//교육그룹
    String  ss_gyear			= box.getString("s_gyear");			//년도
    String  ss_grseq			= box.getString("s_grseq");			//교육차수
    String  ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_paystatus		= box.getString("s_paystatus");	    //결제상태
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String  v_orderColumn  = box.getString("p_orderColumn"); //정렬칼럼
	String  v_dispnum	= "";
    
    
    int     v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
    int     v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

	String  v_grcode	= "";
	String  v_grcodenm	= "";
	String  v_gyear		= "";
	String  v_grseq		= "";
	String  v_grseqnm	= "";

	DataBox dbox = (DataBox)request.getAttribute("groupInfo");
    if (dbox != null) {
		v_grcode	= dbox.getString("d_grcode");
		v_grcodenm	= dbox.getString("d_grcodenm");
		v_gyear		= dbox.getString("d_gyear");
		v_grseq		= dbox.getString("d_grseq");
		v_grseqnm	= dbox.getString("d_grseqnm");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<Script language="javascript">

// 회원조회
function member_search() {
    if (blankCheck(document.form1.p_name.value)) {
        alert("회원명을 입력해주십시오. ");
        return;
    }
    window.self.name = "winSelectView";
    open_window("openWinView","","100","100","560","470");
    document.form1.p_mode2.value = document.form1.p_name.value;
    document.form1.target = "openWinView";
    document.form1.action = "/servlet/controller.polity.TaxbillAdminServlet";
    document.form1.p_process.value = "SearchMemberOpenPage";
    document.form1.submit();
}

//계산서 등록
function InsertTax() {

    if (blankCheck(document.form1.p_goodname.value)) {
    	document.form1.p_goodname.focus();
        alert("결제명을 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_studentcnt.value)) {
    	document.form1.p_studentcnt.focus();
        alert("입과인원수를 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_price.value)) {
    	document.form1.p_price.focus();
        alert("결제액을 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_note.value)) {
    	document.form1.p_note.focus();
        alert("비고를 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_name.value)) {
    	document.form1.p_name.focus();
        alert("발급대상을 회원조회를 통해 입력해 주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_userid.value)) {
    	document.form1.p_userid.focus();
        alert("발급대상을 회원조회를 통해 입력해 주십시오. ");
        return;
    }

    window.self.name = "winSelectView";
    document.form1.target = "winSelectView";
    document.form1.action = "/servlet/controller.polity.TaxbillAdminServlet";
    document.form1.p_process.value = "taxInsert";
    document.form1.submit();
}

//숫자만 입력 KeyDown 이벤트 (keyPressCheck와 동시사용해야함, 한글은 적용 안됨)
function keyDownCheck(P) {
   var sKeyCode  = event.keyCode;
   var sKeyValue = String.fromCharCode(sKeyCode);
   var sKeyCheck = "";
   var sTmpKey1  = 0;
   var sTmpKey2  = 0;

   if (P == "Y") {
      sKeyCheck = "-0123456789";
      sTmpKey1  = 109;
      sTmpKey2  = 189;
   }
   else {
      sKeyCheck = "0123456789";
      sTmpKey1  = 8;
      sTmpKey2  = 8;
   }
   if (sKeyCheck.indexOf(sKeyValue) > -1 || sKeyCode == sTmpKey1 || sKeyCode == sTmpKey2 || sKeyCode == 8 || sKeyCode == 9 || sKeyCode == 110 || sKeyCode == 190 || sKeyCode == 46 || sKeyCode == 16 || sKeyCode == 17 || (sKeyCode >= 96 && sKeyCode <= 105)|| (sKeyCode >= 35 && sKeyCode <= 40) ) {
      event.returnValue = true;
   }
   else {
    event.keyCode = 0;
      event.returnValue = false;
   }
}

// 숫자만 입력 KeyPress 이벤트 (keyDownCheck와 동시사용해야함, 한글은 적용 안됨)
function keyPressCheck(P) {
   var sKeyCode  = event.keyCode;
   var sKeyValue = String.fromCharCode(sKeyCode);
   var sKeyCheck = "";
   if (P == "Y") {
      sKeyCheck = "-0123456789";
   }
   else {
      sKeyCheck = "0123456789";
   }
   if (sKeyCheck.indexOf(sKeyValue) > -1 || sKeyCode == 46) {
      event.returnValue = true;
   }
   else {
    event.keyCode = 0;
      event.returnValue = false;
   }
}

</Script>
<script language="VbScript">
'숫자로 바꾸기
Public Function SetNum(i_sInVals)
   SetNum = Replace(i_sInVals, ",", "")
End Function
 
Dim sPubSelYN
sPubSelYN = True
Public Function SetNumObj(what)
   On Error Resume Next
 
   what.value = SetNum(what.value)
 
   If sPubSelYN Then
      what.select()
   End If
 
   sPubSelYN = True
End Function
 
Public Sub fnKeyUpCheck(what)
   If InStr(what.value, " ") Then
      what.value = Trim(what.value)
   End If
 
   If what.value = "" Then
      Exit Sub
   End If
 
   sKeyTmpVal = what.value
 
   If Len(sKeyTmpVal) = 1 And Left(sKeyTmpVal, 1) = "-" Then
   Else
      If Not IsNumeric(sKeyTmpVal) And Len(sKeyTmpVal) > 0 Then
         sPubSelYN = False
         what.blur()
         what.focus()
         'what.value = sKeyTmpVal& " "
         'what.value = Replace(sKeyTmpVal, right(sKeyTmpVal,1), "")
         If Right(sKeyTmpVal, 1) = "-" Then
            what.value = "-" & Replace(sKeyTmpVal, right(sKeyTmpVal,1), "")
         Else
            what.value = Replace(sKeyTmpVal, right(sKeyTmpVal,1), "")
         End If
      End If
   End If
 
   If what.value = "" Then
      what.value = "0"
   End If
 
   '각자 페이지에서 KeyUp 후 해야될 일이 있을때 fnPlus를 각 페이지에서 정의해서 쓰면 된다
   On Error Resume Next
   fnPlus()
   Err.Clear
End Sub
</script>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td align="left">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>
            <td align="center" bgcolor="#FFFFFF">
			 <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_admin01.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
            <br>

<form name="form1" method = "post">
  <input type="hidden" name="p_process" value="<%=v_process%>">
  <input type="hidden" name="p_mode1"   value = "name">
  <input type="hidden" name="p_mode2"   value = "">
  <input type="hidden" name="p_grcode"  value = "<%= v_grcode %>">
  <input type="hidden" name="p_gyear"   value = "<%= v_gyear %>">
  <input type="hidden" name="p_grseq"   value = "<%= v_grseq %>">

  <input type="hidden" name="p_orderColumn"  value="<%=v_orderColumn%>">
  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
  <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
  <input type="hidden" name="p_pagesize"  value = "<%=v_pagesize%>">

  <input type="hidden" name="p_grseqnm"    value="<%=ss_grseqnm%>">
  <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
  <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
  <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">


  <input type="hidden" name="s_grcode"      value = "<%= ss_grcode %>">
  <input type="hidden" name="s_gyear"       value = "<%= ss_gyear %>">
  <input type="hidden" name="s_grseq"       value = "<%= ss_grseq %>">
  <input type="hidden" name="s_grseqnm"     value="<%= ss_grseqnm %>">
  <input type="hidden" name="s_upperclass"  value="<%= ss_upperclass %>">
  <input type="hidden" name="s_middleclass" value="<%= ss_middleclass %>">
  <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
  <input type="hidden" name="s_paystatus"   value="<%=ss_paystatus%>">
  <input type="hidden" name="s_startdate"   value="<%=ss_startdate%>">
  <input type="hidden" name="s_enddate"     value="<%=ss_enddate%>">
  <input type="hidden" name="p_action"      value="go">
  <input type="hidden" name="p_searchtext"  value="<%= v_searchtext %>">
  <input type="hidden" name="p_search"      value="<%= v_search %>">
  
        <table class="box_table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="30%"><b>교육그룹</b></td>
            <td class="table_02_2" ><%= v_grcodenm %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>연도</b></td>
            <td class="table_02_2" ><%= v_gyear %>년</td>
          </tr>
          <tr>
            <td class="table_title" ><b>교육차수</b></td>
            <td class="table_02_2" ><%= v_grseqnm %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제명</b></td>
            <td class="table_02_2" ><input name="p_goodname" type="text" class="input" size="60" value=""/></td>
          </tr>
          <tr>
            <td class="table_title" ><b>입과인원수</b></td>
            <td class="table_02_2" ><input name="p_studentcnt" type="text" class="input" size="30" value="" onKeyDown=keyDownCheck('N'); onKeyPress=keyPressCheck('N') onFocus='SetNumObj(this)' onKeyUp='fnKeyUpCheck(this);' style='text-align:right;'/>명</td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제액</b></td>
            <td class="table_02_2" ><input name="p_price" type="text" class="input" size="30" value="" onKeyDown=keyDownCheck('N'); onKeyPress=keyPressCheck('N') onFocus='SetNumObj(this)' onKeyUp='fnKeyUpCheck(this);' style='text-align:right;'/>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>비고</b></td>
            <td class="table_02_2" ><input name="p_note" type="text" class="input" size="60" value=""/></td>
          </tr>
          <tr>
            <td class="table_title" ><b>발급대상</b></td>
            <td class="table_02_2" >
              <input name="p_name" type="text" class="input" size="30" value=""/>&nbsp;<a href="javascript:member_search()"><img src="/images/admin/button/btn_imsearch.gif" border="0" align="absmiddle"></a>
              <input name="p_userid" type="hidden" value=""/>
            </td>
          </tr>
        </table>

        <!----------------- 닫기 버튼 시작 ----------------->
        <table cellspacing="0" cellpadding="0" >
          <tr>
            <td align="right" style="padding-top=10"><a href="javascript:InsertTax()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
  		  <td width=8></td>
            <td align="right" style="padding-top=10"><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 닫기 버튼 끝 ----------------->
        <br>




          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
