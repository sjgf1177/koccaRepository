 <%
//**********************************************************
//  1. 제      목: 수강료 납부 등록내용 수정
//  2. 프로그램명: za_OffBillRegistAdmin_U
//  3. 개      요: 수강료 납부 등록내용 수정(팝업)
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.23
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

    String  ss_year			    = box.getString("s_year");			//년도
	String  ss_subj             = box.getString("s_subjcode");	  	//과정
	String  ss_subjseq          = box.getString("s_subjseq");	  	//과정
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_paystatus		= box.getString("s_paystatus");	    //결제상태
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
	String  s_subjsearchkey     = box.getString("s_subjsearchkey");
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String  v_dispnum	= "";
    
    String  p_year			    = box.getString("p_year");			//년도
	String  p_subj              = box.getString("p_subj");	  	//과정
	String  p_subjseq           = box.getString("p_subjseq");	  	//과정
    
    int     v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
    int     v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
    
	String  v_subj	      = "";
	String  v_subjnm      = "";
	String  v_year	      = "";
	String  v_subjseq     = "";
	String  v_seq         = "";
	String  v_billreqnm   = "";
	double  v_biyong      = 0;
	double  v_realbiyong  = 0;
	String  v_billbegindt = "";
	String  v_billenddt   = "";
	String  v_comments    = "";
	
	String  v_userid      = "";
	String  v_name        = "";
	String  v_studentno   = "";

	DataBox dbox = (DataBox)request.getAttribute("OffBillRegistInfo");
    if (dbox != null) {
		v_subj	      = dbox.getString("d_subj");
		v_subjnm      = dbox.getString("d_subjnm");
		v_year	      = dbox.getString("d_year");
		v_subjseq     = dbox.getString("d_subjseq");
		v_seq         = dbox.getString("d_seq");
		v_billreqnm   = dbox.getString("d_billreqnm");
		v_biyong      = dbox.getDouble("d_biyong");
		v_realbiyong  = dbox.getDouble("d_realbiyong");
		v_billbegindt = dbox.getString("d_billbegindt");
		v_billenddt   = dbox.getString("d_billenddt");
		v_comments    = dbox.getString("d_comments");
    }
    
    ArrayList list = (ArrayList)request.getAttribute("searchOffBillStudentList");

%>
<html>
<head>
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
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<Script language="javascript">
$(document).ready(function(){
	$("#p_billbegindt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_billenddt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

// 수강생 조회
function student_search() {
    window.self.name = "winSelectView";
    open_window("openWinView","","650","300","560","470");
    document.form1.target = "openWinView";
    document.form1.action = "/servlet/controller.off.OffBillRegistAdminServlet";
    document.form1.p_process.value = "SearchStudentOpenPage";
    document.form1.submit();
}

//계산서 등록
function InsertOffBill() {

    if (blankCheck(document.form1.p_billreqnm.value)) {
    	document.form1.p_billreqnm.focus();
        alert("결제명을 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_biyong.value)) {
    	document.form1.p_biyong.focus();
        alert("수강료를 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_realbiyong.value)) {
    	document.form1.p_realbiyong.focus();
        alert("실결제액을 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_billbegindt.value)) {
    	document.form1.p_billbegindt.focus();
        alert("납부시작일을 입력해주십시오. ");
        return;
    }
    if (blankCheck(document.form1.p_billenddt.value)) {
    	document.form1.p_billenddt.focus();
        alert("납부종료일을 입력해주십시오. ");
        return;
    }

	//alert(document.all.p_billstudent.length);
	if(document.all.p_billstudent.length) {

		document.all.p_billstudents.value = "";

		for (var i = 1;i < document.all.p_billstudent.length; i++){
			document.all.p_billstudents.value += "," + document.all.p_billstudent[i].value;
		}	 
	}
	
    window.self.name = "winSelectView";
    document.form1.target = "winSelectView";
    document.form1.action = "/servlet/controller.off.OffBillRegistAdminServlet";
    document.form1.p_process.value = "offbillUpdate";
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

function spanclear(p_spid,p_sphid) {
	document.getElementById(p_spid).innerHTML = "";
	document.getElementById(p_sphid).innerHTML = "";
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
          <td><img src="/images/admin/portal/s.1_32.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
            <br>

<form name="form1" method = "post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_action"      value="<%= v_action %>">
    <input type="hidden" name="p_orderColumn" value="">
    <input type="hidden" name="p_orderType"   value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno"      value="<%=v_pageno%>">
    <input type="hidden" name="p_pagesize"    value="<%=v_pagesize%>">

    <input type="hidden" name="p_year"          value="<%=p_year%>">
    <input type="hidden" name="p_subj"          value="<%=p_subj%>">
    <input type="hidden" name="p_subjseq"       value="<%=p_subjseq%>">
    <input type="hidden" name="p_uclass"        value="<%=ss_upperclass%>">
    <input type="hidden" name="p_mclass"        value="<%=ss_middleclass%>">
    <input type="hidden" name="p_lclass"        value="<%=ss_lowerclass%>">
    <input type="hidden" name="p_startdate"     value="<%=ss_startdate%>">
    <input type="hidden" name="p_enddate"       value="<%=ss_enddate%>">
    <input type="hidden" name="p_billstudent"   value="">
    <input type="hidden" name="p_billstudents"  value="">
    <input type="hidden" name="p_subjsearchkey" value="<%=s_subjsearchkey%>">
    <input type="hidden" name="p_seq"           value="<%=v_seq%>">

    <input type="hidden" name="s_year"          value="<%=ss_year%>">
    <input type="hidden" name="s_subjcode"      value="<%=ss_subj%>">
    <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>">
    <input type="hidden" name="s_lowerclass"    value="<%=ss_lowerclass%>">
    <input type="hidden" name="s_startdate"     value="<%=ss_startdate%>">
    <input type="hidden" name="s_enddate"       value="<%=ss_enddate%>">
    <input type="hidden" name="s_subjsearchkey" value="<%=s_subjsearchkey%>">


    <input type="hidden" name="p_userid" >
    <input type="hidden" name="p_tid" >

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.off.OffBillRegistAdminServlet">
    <input type="hidden" name="p_returnprocess"   value="selectList">
  
        <table class="box_table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="30%"><b>연도</b></td>
            <td class="table_02_2" ><%= v_year %>년</td>
          </tr>
          <tr>
            <td class="table_title" ><b>과정명</b></td>
            <td class="table_02_2" ><%= v_subjnm %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>차수</b></td>
            <td class="table_02_2" ><%= v_subjseq %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제명</b></td>
            <td class="table_02_2" ><input name="p_billreqnm" type="text" class="input" size="60" value="<%= v_billreqnm %>"/></td>
          </tr>
          <tr>
            <td class="table_title" ><b>수강료</b></td>
            <td class="table_02_2" ><input name="p_biyong" type="text" class="input" size="30" value="<%= new java.text.DecimalFormat("##0").format(v_biyong) %>" onKeyDown=keyDownCheck('N'); onKeyPress=keyPressCheck('N') onFocus='SetNumObj(this)' onKeyUp='fnKeyUpCheck(this);' style='text-align:right;'/>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>실결제액</b></td>
            <td class="table_02_2" ><input name="p_realbiyong" type="text" class="input" size="30" value="<%= new java.text.DecimalFormat("##0").format(v_realbiyong) %>" onKeyDown=keyDownCheck('N'); onKeyPress=keyPressCheck('N') onFocus='SetNumObj(this)' onKeyUp='fnKeyUpCheck(this);' style='text-align:right;'/>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>납부기간</b></td>
            <td class="table_02_2" ><input name="p_billbegindt" id="p_billbegindt" value="<%= FormatDate.getFormatDate(v_billbegindt,"yyyy-MM-dd") %>" type="text" class="datepicker_input1" size="10">&nbsp;
		  				  			~&nbsp;<input name="p_billenddt" id="p_billenddt" value="<%= FormatDate.getFormatDate(v_billenddt,"yyyy-MM-dd") %>" type="text" class="datepicker_input1" size="10"></td>
          </tr>
          <tr>
            <td class="table_title" ><b>납부대상</b></td>
            <td class="table_02_2" >
              <a href="javascript:student_search();">[대상자찾기]</a>
              <div id="div_billstudent" style="display:none">
              <table id="tb_billstudent" width="100%">
                <tr><td height="5"></td></tr>
                <tr>
                  <td width="25%">ID</td>
                  <td width="25%">성명</td>
                  <td width="30%">학번</td>
                  <td width="20%">삭제</td>
                </tr>
              </table>
              </div>
              <span id="spn_addstudent">
<%
		for(int i = 0; i < list.size(); i++) {
		
		    dbox  = (DataBox)list.get(i);
		    
		    v_userid      = dbox.getString("d_userid");
		    v_name        = dbox.getString("d_name");
		    v_studentno   = dbox.getString("d_studentno");
%>
                <span id="spn_<%= v_userid %>">
                  <table id="tb_<%= v_userid %>" width="100%">
                    <tr><td width="25%"><%= v_userid %></td>
                        <td width="25%"><%= v_name %></td>
                        <td width="30%"><%= v_studentno %></td>
                        <td width="20%"><a href="javascript:spanclear('spn_<%= v_userid %>','spn_<%= v_userid %>_hidden')">[삭제]</a></td>
                    </tr>
                  </table>
                </span>
<%
		}
%>
              </span>
              <span id="spn_addstudent_hidden">
<%
		for(int i = 0; i < list.size(); i++) {
		
		    dbox  = (DataBox)list.get(i);
		    
		    v_userid      = dbox.getString("d_userid");
%>
                <span id="spn_<%= v_userid %>_hidden">
                  <input type="hidden" name="p_billstudent" value="<%= v_userid %>"/>
                </span>
<%
		}
%>
              </span>
            </td>
          </tr>
          <tr>
            <td class="table_title" ><b>비고</b></td>
            <td class="table_02_2" ><textarea name="p_comments" cols="100" rows="3" tabindex=70><%= v_comments %></textarea></td>
          </tr>
        </table>

        <!----------------- 닫기 버튼 시작 ----------------->
        <table cellspacing="0" cellpadding="0" >
          <tr>
            <td align="right" style="padding-top=10"><a href="javascript:InsertOffBill()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
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
