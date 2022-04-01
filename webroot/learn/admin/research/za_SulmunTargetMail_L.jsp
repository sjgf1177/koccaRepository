<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunTargetMail_L.java
//  3. 개      요: 설문 발송  조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 6. 30
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    String ems_url = conf.getProperty("ems.url.value"); // 메일    
    String  v_subjnm    = box.getString("p_subjnm");
    String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_subj      = "TARGET";
    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_sulpapernum       = box.getInt("s_sulpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");

	String v_flag = box.getString("p_flag");    
	if(v_flag.equals("asc")) v_flag = "desc";
	else v_flag = "asc";

    ArrayList list = (ArrayList)request.getAttribute("SulmunMailList");
    DataBox dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    String  v_action    = box.getString("p_action");

	String v_sulstart = "";
	String v_sulend = "";
    String v_ing = "";
	int v_update = 0;

    if (v_action.equals("go")){

           v_sulstart = FormatDate.getFormatDate(dbox0.getString("d_sulstart"),"yyyy-MM-dd");
	       v_sulend = FormatDate.getFormatDate(dbox0.getString("d_sulend"),"yyyy-MM-dd");

		   if(dbox0.getInt("d_sulpapernum") > 0){
          
		   long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
           long v_fend = Long.parseLong(dbox0.getString("d_sulend"));
              
                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>설문 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_fend){
		            v_ing = "설문 진행이 완료되었습니다.";
					v_update = 3;
		         } else if (v_fstart <= v_now && v_now <= v_fend){
		            v_ing = "설문이 진행 중입니다"; 
					v_update = 2;
		         }
		   }
	}
%>
<html>
<head>
<title>설문 발송 리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//독려메일발송 
function MailSend() {
  if (chkSelected() < 1) {
	alert('메일을 발송할 학습자를 선택하세요');
	return;
  }
  window.self.name = "ProposeMemberList";
  open_window("openFreeMail","","10","10","800","650");
  document.form2.target = "openFreeMail";
  document.form2.action='<%=ems_url%>/ems/MassMail.do';
  document.form2.p_process.value = 'SendFreeMail';
  document.form2.submit();
  //document.form2.target = window.self.name;      
}

// 독려메일발송
/*function MailEncoSend() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
	if (document.form2.s_gyear.value=="----"){
            alert("연도를 선택하세요");
            return;
    }
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
	if (chkSelected() < 1) {
		alert('메일을 보낼 대상자를 선택하세요');
		return;
	  }
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunMailEncoSendPage';
    document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
    document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.research.SulmunTargetMailServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}*/


function UserResult(p_userid) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
	if (document.form2.s_gyear.value=="----"){
            alert("연도를 선택하세요");
            return;
    }
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunUserPaperResult';
    document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
    document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    document.form2.p_action.value = "go";
    document.form2.p_userid.value = p_userid;
	document.form2.action = "/servlet/controller.research.SulmunTargetUserServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}

// 체크여부 확인
function chkSelected() {
  var selectedcnt = 0;
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        if (document.form2.p_checks[i].checked == true) {
          selectedcnt++;
        }
      }
    } else {
      if (document.form2.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }
  return selectedcnt;
}

// 조회 검색
function whenSelection(p_action) {
  document.form2.p_process.value = 'SulmunMailListPage';
  document.form2.p_action.value  = p_action;
  if (p_action == "go"){
	if (document.form2.s_grcode.value=="----"){
		alert("교육그룹을 선택하세요");
		return;
	}  
	if (document.form2.s_gyear.value=="----"){
		alert("연도를 선택하세요");
		return;
	}
	if (document.form2.s_sulpapernum.value=='0'){
		alert("설문지를 선택하세요");
		return;
	}
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  //top.ftop.setPam();
  }
  document.form2.submit();
}


// 전체 체크박스 checked
function whenAllSelect() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = true;
      }
    } else {
      document.form2.p_checks.checked = true;
    }
  } 
}

// 전체 체크박스 checked cancel
function whenAllSelectCancel() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = false;
      }
    } else {
      document.form2.p_checks.checked = false;
    }
  } 
}


// 전체 체크박스 선택
function checkall(){
    if(document.form2.p_checkall.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
}

// 설문응답시간
function whenOrder(column) {
	document.form2.target = "_self";
	document.form2.action='/servlet/controller.research.SulmunTargetMailServlet';
	document.form2.p_process.value = 'SulmunMailListPage';
	document.form2.p_orderColumn.value = column;
	document.form2.p_flag.value = '<%=v_flag%>';
	document.form2.p_action.value = "go";
	document.form2.submit();
}

function whenAllSelect2() {
  if(document.form2.all['p_chkopen'] == '[object]') {
    if (document.form2.p_chkopen.length > 0) {
      for (i=0; i<document.form2.p_chkopen.length; i++) {
        if (document.form2.p_chkopen[i].value == "Y")
        	document.form2.p_checks[i].checked = true;
        else
        	document.form2.p_checks[i].checked = false;
      }
    } else {
      document.form2.p_checks.checked = false;
    }
  } 
}
function whenAllSelectCancel2() {
  if(document.form2.all['p_chkopen'] == '[object]') {
    if (document.form2.p_chkopen.length > 0) {
      for (i=0; i<document.form2.p_chkopen.length; i++) {
        if (document.form2.p_chkopen[i].value == "N")
        	document.form2.p_checks[i].checked = true;
        else
        	document.form2.p_checks[i].checked = false;
      }
    } else {
      document.form2.p_checks.checked = true;
    }
  } 
}

function checkall2(){
    if(document.form2.p_checkall2.checked){
      document.form2.p_checkall.checked = false;
      whenAllSelectCancel2();

    }
    else{
      document.form2.p_checkall.checked = false;
      whenAllSelect2();
    }
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailServlet">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr>
                      <td> <font color="red">★</font>  
	                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						    &nbsp; <font color="red">★</font>  	                       
						   <%= SelectEduBean.getDamunGyear(box, true, false )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
						    &nbsp; <font color="red">★</font>  						   
                           <%= SulmunTargetMailBean.getSulpaperSelect (ss_grcode, ss_gyear, ss_subj, "s_sulpapernum", ss_sulpapernum, "")%>
		                    <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                    	
					  </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <!----------------- 대상자추가, 대상자삭제 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
<%
   if(v_update< 0){
%>
        <tr align="right">
          <td><%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend%>" size="10" readonly>
			<%if(v_update <= 2){%><a href="javascript:MailSend()"><img src="/images/admin/button/btn_dogmailsend.gif" border="0" align="absmiddle"></a><%}%>            
		  </td>
        </tr>
<%} else {%>
        <tr align="right">
          <td><%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend%>" size="10" readonly>
			<%if(v_update <= 2){%><a href="javascript:MailSend()"><img src="/images/admin/button/btn_dogmailsend.gif" border="0" align="absmiddle"></a><%}%>            
		  </td>
        </tr>
<%}%>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 대상자추가, 대상자삭제 버튼 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="s_subj"       value="TARGET">
        <input type="hidden" name="p_subj"       value="TARGET">
        <input type="hidden" name="p_sulpapernum"       value="">
        <input type="hidden" name="p_mailnum"       value="">
        <input type="hidden" name="p_grcode"       value="">
        <input type="hidden" name="p_gyear"       value="">
        <input type="hidden" name="p_userid"       value="">
        <input type="hidden" name="p_action"     value="">
		<input type="hidden" name="p_orderColumn"     value="">
		<input type="hidden" name="p_flag"     value="">

        <tr>
          <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title">NO</td>
          <td class="table_title">회사</td>          
          <td class="table_title">사번(ID)</td>
          <td class="table_title">성명</td>
          <td class="table_title">부서</td>          
          <td class="table_title">직위</td>
          <td class="table_title">발송</td>
          <td class="table_title"><a href="javascript:whenOrder('resposetime')" class="e">설문응답시간</a></td>
		  <td class="table_title">
		  <input type="checkbox" name="p_checkall" onClick="javascript:checkall()">
		  <!--<input type="checkbox" name="p_checkall2" onClick="javascript:checkall2()">-->
		  </td>
        </tr>
<%
        for (int i=0; i<list.size(); i++) {
            DataBox dbox2  = (DataBox)list.get(i);      %>
        <tr>
          <td width="5%" class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=dbox2.getString("d_companynm")%></td>          
		  <td class="table_02_1"><%=dbox2.getString("d_cono")%>(<%=dbox2.getString("d_userid")%>)</td>
<%
          if (dbox2.getString("d_return").equals("Y")) {				
%>
          <td class="table_02_1"><a href="javascript:UserResult('<%=dbox2.getString("d_userid")%>')"><%=dbox2.getString("d_name")%></a></td>
<%
		  } else {	
%>        
          <td class="table_02_1"><%=dbox2.getString("d_name")%></td>
		  <!--input type="hidden" name="p_checks" value="<%=dbox2.getString("d_userid")%>|<%=dbox2.getString("d_name")%>"-->
<%
          }	
%>
          <td class="table_02_1"><%=dbox2.getString("d_asgnnm")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_jikwinm")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_ismailsend")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox2.getString("d_returntime"),"yyyy-MM-dd HH:mm:ss")%>
          <% if (dbox2.getString("d_returntime").equals("")) { %>
          		<input type="hidden" name="p_chkopen" value="N">
          <% } else { %>
          		<input type="hidden" name="p_chkopen" value="Y">
          <% } %>
          
          </td>
		  <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox2.getString("d_userid")%>|<%=dbox2.getString("d_name")%>">
          </td>
        </tr>
<%  }  %>
        </table>
      <br>
    </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
