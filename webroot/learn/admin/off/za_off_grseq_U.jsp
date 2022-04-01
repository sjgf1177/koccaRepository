<%
//**********************************************************
//  1. 제	  목: 오프라인- 차수 수정 화면
//  2. 프로그램명: za_off_Recruit_I.jsp
//  3. 개	  요: 오프라인- 차수 수정 화면
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.23
//  7. 수	  정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	//DEFINED in relation to select START
	String  ss_subjseq   = box.getString("s_subjseq");		  //과정 차수
	//DEFINED in relation to select END
	ArrayList detail = (ArrayList)request.getAttribute("detail");

    DataBox data = (DataBox)request.getAttribute("resultData");
	String  ss_gyear	 = data.getString("d_year");			//년도
	String p_subj = data.getString("d_subj");
	String p_charger = data.getString("d_charger");
	String  p_upperclass = data.getString("d_upperclass");	  //과정대분류
	String  p_middleclass = data.getString("d_middleclass");	//과정중분류
	String  p_lowerclass = data.getString("d_lowerclass");	  //과정소분류
	String  p_needinput = data.getString("d_needinput");	  //필수입력    
%>
<html>
<head>
<title>오프라인 차수 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
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
	$("#p_propstart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edustart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_billbegindt1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_billenddt1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_startcanceldate1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_endcanceldate1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

//수정 시작
function updateSubject() {
	if(!chkData()) {
	  return;
	}
    changeNeedInput();

    if (confirm("차수정보를 수정 하시겠습니까?")) {
        document.form1.s_action.value = "go";
        document.form1.p_specials.value = document.form1.p_new.value + document.form1.p_hit.value + document.form1.p_recom.value + document.form1.p_lecture.value;
        document.form1.p_process.value = 'update';
        document.form1.submit();
    }
}
//수정 끝
function DeleteSubject() {
    if (confirm("차수정보를 삭제 하시겠습니까?")) {
        document.form1.p_process.value = 'delete';
        document.form1.submit();
    }
}

function goSubjectListPage() {
	document.form1.s_action.value = "go";
	document.form1.p_process.value  = 'listPage';
	document.form1.submit();
}


//공통 체크 시작

function whenUsebook() {
	//교재여부 체크
	if (document.form1.p_usebook.value=="Y") {
		document.form1.p_bookname.disabled = false;
		document.form1.p_bookprice.disabled = false;
	} else {
		document.form1.p_bookname.disabled = true;
		document.form1.p_bookname.value = "";
		document.form1.p_bookprice.disabled = true;
		document.form1.p_bookprice.value = 0;
	}
}

function whenIsgoyong() {
	//고용보험 사용여부체크
	if (document.form1.p_isgoyong.value=="Y") {
		document.form1.p_goyongpricemajor.disabled = false;
		document.form1.p_goyongpriceminor.disabled = false;
	} else {
		document.form1.p_goyongpricemajor.disabled = true;
		document.form1.p_goyongpriceminor.disabled = true;
		document.form1.p_goyongpricemajor.value = 0;
		document.form1.p_goyongpriceminor.value = 0;
	}
}

function whenIsuse() {
	//과정사용여부
	if (document.form1.p_isuse.value=="Y") {
		document.form1.p_isvisible.disabled = false;
	} else {
		document.form1.p_isvisible.disabled = true;
		document.form1.p_isvisible.value = "N";
	}
}

//공통체크 끝


function chkData() {
  ff = document.form1;
  var wec = document.Wec;
  ff.p_explain.value = wec.MIMEValue;
  if(getObjectLength("p_subjtarget")>100) {
		 alert("교육대상의 내용이 너무 깁니다. 100바이트 이하로 작성하여 주십시요.");
		 document.form1.p_subjtarget.focuse();
		 return false;
	 }
	 if(getObjectLength("p_intro")>4000) {
		 alert("교육대상의 내용이 너무 깁니다. 4000바이트 이하로 작성하여 주십시요.");
		 document.form1.p_intro.focuse();
	 }
  if(!dateChk()) {
	  return false;
  }
  if (document.form1.p_subj.value=='ALL' || document.form1.p_subj.value=='') { 
	alert('과정코드를 선택하세요.');
	return false;
  }
  if (!number_chk_noalert(document.form1.p_biyong.value)) {
	alert('수강료가 잘못입력되었습니다.');
	return false;
  }
  if (!number_chk_noalert(document.form1.p_edudays.value)) {
	alert('학습일차가 잘못입력되었습니다.');
	return false;
  }
  if (!number_chk_noalert(document.form1.p_point.value)) {
	alert('이수점수가 잘못입력되었습니다.');
	return false;
  }
  if (!number_chk_noalert(document.form1.p_studentlimit.value)) {
	alert('정원이 잘못입력되었습니다.');
	return false;
  }
  
  if (!number_chk_noalert(document.form1.p_gradscore.value)) {
	alert('이수기준-점수가 잘못입력되었습니다.');
	return false;
  }
  
  if (!number_chk_noalert(document.form1.p_gradstep.value)) {
	alert('이수기준-진도율이 잘못입력되었습니다.');
	return false;
  }
  
  if (!number_chk_noalert(document.form1.p_gradexam.value)) {
	alert('이수기준-평가가 잘못입력되었습니다.');
	return false;
  }
  
  if (!number_chk_noalert(document.form1.p_gradreport.value)) {
	alert('이수기준-리포트가 잘못입력되었습니다.');
	return false;
  }
  
	if (ff.p_gradscore.value > 100) {
		alert('이수기준-점수가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradstep.value > 100) {
		alert('이수기준-진도율이 100 이상입니다.');
		return false;
	}
	if (ff.p_gradexam.value > 100) {
		alert('이수기준-중간평가가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradftest.value > 100) {
		alert('이수기준-최종평가가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradhtest.value > 100) {
		alert('이수기준-형성평가가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradreport.value > 100) {
		alert('이수기준-리포트가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradetc1.value > 100) {
		alert('이수기준-참여도가 100 이상입니다.');
		return false;
	}
	if (ff.p_gradetc2.value > 100) {
		alert('이수기준-기타가 100 이상입니다.');
		return false;
	}

  if (!number_chk_noalert(document.form1.p_goyongpricemajor.value)) {
	alert('환급액이 잘못입력되었습니다.');
	return false;
  }

  if (!number_chk_noalert(document.form1.p_edulimit.value)) {
		alert('1일최대학습량이 잘못입력되었습니다.');
		return false;
  }
  
  if (!whenChaneWValue()) return false;
  
  if (blankCheck(document.form1.p_subjnm.value)) {
	alert('과정명을 입력하십시요.');
	document.form1.p_subjnm.focus();
	return false;
  }

 if (document.form1.p_explain.value.length==0) {
		alert('교육내용을 입력하십시요.');
		return false;
 }
    /* off line는 사용하지 않음
  var weight = document.form1.p_wscore.value=weight;
	if (weight < 100) {
		alert('가중치가 100% 보다 적습니다');
		return false;
		} else if (weight > 100) {
		alert('가중치가 100% 보다 많습니다.');
		return false;
		}
    */
  return true;
}

function fnPreview() {
	  var url = "/servlet/controller.course.SubjectServlet?p_process=buyBagPreviewPage&p_needinput="+$("#p_needinput").val();

	  open_window("",url,"0","0","720","600");
}

// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
  if (tmp1 == 'p_cuser') {
	document.form1.p_cuseridnm.value = name;
	document.form1.p_cuserid.value   = userid;
  } else if (tmp1 == 'p_muser') {
	document.form1.p_museridnm.value = name;
	document.form1.p_muserid.value   = userid;
	document.form1.p_musertel.value   = tmp3;
  }
}


//담당교수 검색
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = '';//document.form1.p_museridnm.value;
  var p_key2  = '1';
  var url = "/servlet/controller.library.SearchServlet?p_process=tutor&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

function teachPopup()
{

  var p_gubun = 'name';
  var p_key1  = '';//document.form1.p_museridnm.value;
  var p_key2  = '1';
  var url = "/servlet/controller.off.OffGrseqAdminServlet?p_process=teachDetailPage&p_subj=<%=p_subj%>&p_year=<%=data.get("d_year")%>&p_subjseq=<%=data.get("d_subjseq")%>";
  open_window("",url,"0","0","600","350");
      

//    farwindow = window.open("", "openWinDetail", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 600, height = 250, top=0, left=0");
//    document.form1.target = "openWinDetail";
//    document.form1.action = "/servlet/controller.off.OffGrseqAdminServlet";
//    document.form1.p_process.value = 'teachDetailPage';
//    document.form1.submit();
//
//    farwindow.window.focus();
//    document.form1.target = window.self.name;
}

function teachWrite()
{
    var r=$(".a1a").length;
    if (r==0)
    {
        alert("등록된 강좌가 없습니다.등록후 이용하세요");
        return;
    }
    var olddt="";
    for(var i=0;i<r;i++)
    {
        var r1=$(".b1b:eq("+i+")").text().substr(0,10);
        r1=r1.substr(0,4)+r1.substr(5,2)+r1.substr(8,10);
        if(olddt!=r1)
        {
            olddt=r1;
            document.form1.p_printdate.value=r1;
            var p_gubun = 'name';
            var p_key1  = '';//document.form1.p_museridnm.value;
            var p_key2  = '1';
            var url = "/servlet/controller.off.OffGrseqAdminServlet?p_process=teachDailyPrint&p_printdate="+r1+"&p_subj=<%=p_subj%>&p_year=<%=data.get("d_year")%>&p_subjseq=<%=data.get("d_subjseq")%>";
            open_window("",url,"0","0","1000","600");
        }
//        document.form1.p_process.value = 'teachDailyPrint';
//        document.form1.submit();
    }
}
function deleteTeachDetail(seq)
{
    $("[name='p_process']").val("teachDetailDelete");
    $("[name='p_seq1']").val(seq);
    document.form1.submit();
}
// 담당교수 검색 후 처리
function receiveTutor(userid, name, resno, email, phone, tmp1, tmp2, tmp3, tmp4, tmp5){
	addMuser(userid, name);
//  document.form1.p_museridnm.value = name;
//  document.form1.p_muserid.value   = userid;
//  document.form1.p_musertel.value   = phone;
}

//제작회사 검색
function searchProducer() {
  var p_key1  = document.form1.p_producernm.value;
  var p_key2  = '';
//  var p_key2  = 'p_producer';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

//소유회사 검색
function searchOwner() {
  var p_key1  = document.form1.p_ownernm.value;
  var p_key2  = 'p_owner';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 회사 검색 후 처리
function receiveGrpComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3) {
//  if (tmp1 == 'p_producer') {
  if (tmp1 == '') {
	document.form1.p_producernm.value = compnm;
	document.form1.p_producer.value   = comp;
  } else if (tmp1 == 'p_owner') {
	document.form1.p_ownernm.value = compnm;
	document.form1.p_owner.value   = comp;
  }
}

// 대중소분류 선택
function whenSelection() {
	document.form1.p_process.value = 'insertPage';
	//대분류 선택시 중분류를 ALL로 초기화
	document.form1.s_middleclass.value = '';
	document.form1.submit();
}

function whenChaneWValue() {
	  if (!number_chk_noalert(document.form1.p_wstep.value)) {
		alert('가중치-진도율(%)이 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wmtest.value)) {
		alert('가중치-중간평가(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wftest.value)) {
		alert('가중치-최종평가(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_whtest.value)) {
		alert('가중치-형성평가(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wreport.value)) {
		alert('가중치-리포트(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wact.value)) {
		alert('가중치-액티비티(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wetc1.value)) {
		alert('가중치-참여도(%)가 잘못입력되었습니다.');
		return false;
	  }
	  if (!number_chk_noalert(document.form1.p_wetc2.value)) {
		alert('가중치-기타(%)가 잘못입력되었습니다.');
		return false;
	  }

	  var weight =  (10*document.form1.p_wstep.value
		+ 10*document.form1.p_wmtest.value + 10*document.form1.p_wftest.value
		+ 10*document.form1.p_whtest.value + 10*document.form1.p_wreport.value
		+ 10*document.form1.p_wact.value + 10*document.form1.p_wetc1.value
		+ 10*document.form1.p_wetc2.value)/10;
	  document.form1.p_wscore.value=weight;
	  return true;
}
// 날자형식변환
function dateChk(){
    ff = document.form1;

	if(isNull(ff.p_startcanceldate1, "수강신청취소기간")){
	    ff.p_startcanceldate.value=make_date(ff.p_startcanceldate1.value)+ff.p_startcanceldate2.options[ff.p_startcanceldate2.selectedIndex].value;
	}
	else {
		location.href="#chk004";
		return false;
	}
	if(isNull(ff.p_endcanceldate1, "수강신청취소기간")){
	    ff.p_endcanceldate.value=make_date(ff.p_endcanceldate1.value)+ff.p_endcanceldate2.options[ff.p_endcanceldate2.selectedIndex].value;
	}
	else {
		location.href="#chk004";
		return false;
	}
	if(isNull(ff.p_propstart1, "수강신청기간")){
	    ff.p_propstart.value=make_date(ff.p_propstart1.value)+ff.p_propstart2.options[ff.p_propstart2.selectedIndex].value;
	}
	else {
		location.href="#chk001";
		return false;
	}
	if(isNull(ff.p_propend1, "수강신청기간")){
        ff.p_propend.value=make_date(ff.p_propend1.value)+ff.p_propend2.options[ff.p_propend2.selectedIndex].value;
    }
	else {
		location.href="#chk001";
		return false;
	}

	if(isNull(ff.p_edustart1, "학습기간")){
        ff.p_edustart.value=make_date(ff.p_edustart1.value)+ff.p_edustart2.options[ff.p_edustart2.selectedIndex].value;
    }
	else {
		location.href="#chk002";
		return false;
	}
	if(isNull(ff.p_eduend1, "학습기간")){
        ff.p_eduend.value=make_date(ff.p_eduend1.value)+ff.p_eduend2.options[ff.p_eduend2.selectedIndex].value;
    }
	else {
		location.href="#chk002";
		return false;
	}
	if(isNull(ff.p_billbegindt1, "결제기간")){
	    ff.p_billbegindt.value=make_date(ff.p_billbegindt1.value)+ff.p_billbegindt2.options[ff.p_billbegindt2.selectedIndex].value;
	}
	else {
		location.href="#chk003";
		return false;
	}
	if(isNull(ff.p_billenddt1, "결제기간")){
	    ff.p_billenddt.value=make_date(ff.p_billenddt1.value)+ff.p_billenddt2.options[ff.p_billenddt2.selectedIndex].value;
	}
	else {
		location.href="#chk003";
		return false;
	}


    return true;
}

function detailPopup(seq)
{
     var p_gubun = 'name';
  var p_key1  = '';//document.form1.p_museridnm.value;
  var p_key2  = '1';
  var url = "/servlet/controller.off.OffGrseqAdminServlet?p_process=teachDetailSelect&p_subj=<%=p_subj%>&p_year=<%=data.get("d_year")%>&p_subjseq=<%=data.get("d_subjseq")%>&p_seq1="+seq;
  open_window("",url,"0","0","600","250");
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="">
<div id="existSubjSeqDiv"></div>

<form name="form1"  method = "POST" action="/servlet/controller.off.OffGrseqAdminServlet">
	<input type="hidden" name="p_process"	 value="">
    <input type="hidden" name="p_seq1">
    <input type="hidden" name="p_userid">
	<input type="hidden" name="s_action">

	<input type="hidden" name="p_specials"	value="">
    <input type="hidden" name="p_printdate"	value="">

	<input type="hidden" name = "p_isoutsourcing" value="N">
	<input type="hidden" name="p_edulimit" value="0">
	<input type="hidden" name="p_musertel" value="">
<% if(data.get("d_isterm").equals("N")) { %>
		  <input type="hidden" name="p_term" value="1" class="input"  size="2" maxlength="1" >
<%} %>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
          <td><img src="/images/admin/portal/s.1_off01.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
	  <%=box.getString("p_warnmsg")%><br>
	  <br>
	  <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td colspan="5" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td class="table_title" rowspan="25"><b>과정정보</b></td> 
			<td colspan="4" class="table_01"><font color="red">(*)</font> 표시는 필수 입력입니다.</td>
		  </tr>
		  <tr> 
			<td class="table_01">교육구분</td>
			<td class="table_02_2" > 과정코드 : <input type="text" class="input" name="p_subj" size="10" maxlength="10" value="<%= data.get("d_subj") %>" readonly></td>
		  </tr>
		  <tr> 
			<td  class="table_01">과정명<font color="red">(*)</font></td>
			<td class="table_02_2"><input type="text" class="input" name="p_subjnm1" size="38" maxlength="100" value="<%= data.get("d_subjnm") %>" tabindex="" disabled="disabled">
                <input type="hidden" class="input" name="p_subjnm" size="38" maxlength="100" value="<%= data.get("d_subjnm") %>">
			</td>
		  </tr>
		  <tr> 
			<td  class="table_01">과정특성</td>
<%
                String specials = data.get("d_specials");
                String p_new   = (specials.length() > 0 ? specials.substring(0,1) : "N");
                String p_hit   = (specials.length() > 1 ? specials.substring(1,2) : "N");
                String p_recom = (specials.length() > 2 ? specials.substring(2,3) : "N");
                String p_lecture = (specials.length() > 3 ? specials.substring(3,4) : "N");
%>
			<td class="table_02_2"> 신규 
			  <select name="p_new"  class="inputpsearch" tabindex=17>
                <option value='Y' <% if(p_new.equals("Y") ||p_new.equals("")) out.print(" selected"); %>>Y</option>
                <option value='N' <% if(p_new.equals("N")) out.print(" selected"); %>>N</option>
              </select>
              HIT
              <select name="p_hit"  class="inputpsearch" tabindex=15>
                <option value='Y' <% if(p_hit.equals("Y") ||p_hit.equals("")) out.print(" selected"); %>>Y</option>
                <option value='N' <% if(p_hit.equals("N")) out.print(" selected"); %>>N</option>
              </select>
              추천
              <select name="p_recom"  class="inputpsearch" tabindex=16>
                <option value='Y' <% if( p_recom.equals("Y") || p_recom.equals("")) out.print(" selected");%>>Y</option>
                <option value='N' <% if( p_recom.equals("N")) out.print(" selected");%>>N</option>
			  </select> 
			  특강
              <select name="p_lecture"  class="inputpsearch" tabindex=16>
                <option value='Y' <% if( p_lecture.equals("Y") || p_lecture.equals("")) out.print(" selected");%>>Y</option>
                <option value='N' <% if( p_lecture.equals("N")) out.print(" selected");%>>N</option>
			  </select> 
			</td>
		  </tr>
		  <tr>
			<td  class="table_01">
				담당교수 &nbsp;
			</td>
			<td height="34" class="table_02_2" >
				<kocca_select:select name="p_tutorid" sqlNum="offmuserList.1"  param="<%= p_subj %>"
				onChange="" attr=" " selectedValue="" isLoad="true" all="none" />
			</td>
		  </tr>
<% if(!data.get("d_isterm").equals("N")) { %>
		  <tr> 
			<td height="31" class="table_01">총 학기수</td>
			<td class="table_02_2" > <input name="p_term" value="4" <%= data.get("d_isterm").equals("N") ? "disabled" : ""%> class="input"  size="2" maxlength="1" >학기 
			</td>
		  </tr>
<%} %>

		  <tr> 
			<td height="31" class="table_01">과정사용여부</td>
			<td class="table_02_2" > <select name="p_isuse"  class="inputpsearch" onChange="whenIsuse()" tabindex=26>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isuse").equals("N") ? "selected" : ""  %>>N</option>
			  </select> 
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">학습자에게 보여주기</td>
			<td class="table_02_2" ><select name="p_isvisible"  class="inputpsearch" tabindex=27>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isvisible").equals("N") ? "selected" : ""  %>>N</option>
			  </select><select style="display:none" name="p_isintroduction"  class="inputpsearch" tabindex=28>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isintroduction").equals("N") ? "selected" : ""  %>>N</option>
			  </select> 
			  </td>
		  </tr>
		  <tr> 
			<td class="table_01">과정소개사용여부</td>
			<td class="table_02_2"> 
			  <select name="p_isintroduction"  class="inputpsearch" tabindex=28>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isintroduction").equals("N") ? "selected" : ""  %>>N</option>
			  </select> 
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">교육기간</td>
			<td class="table_02_2">
				<input type="text" class="input" name="p_edudays" size="10" maxlength="5" value="<%= data.get("d_edudays") %>" tabindex=28>
				<select name="p_edudaystype"  class="inputpsearch" tabindex=28>
					<option value='Y'>년</option>
					<option value='M' <%= data.get("d_edudaystype").equals("M") ? "selected" : ""  %>>월</option>
					<option value='W' <%= data.get("d_edudaystype").equals("W") ? "selected" : ""  %>>주</option>
					<option value='D' <%= data.get("d_edudaystype").equals("D") ? "selected" : ""  %>>일</option>
				</select>
			  </td>
		  </tr>
		  <tr> 
			<td class="table_01">교육장소</td>
			<td class="table_02_2"><input type="text" class="input" name="p_place" size="50" value="<%= data.get("d_place") %>" tabindex=29></td>
		  </tr>
		  <tr> 
			<td  class="table_01">교육정원</td>
			<td class="table_02_2"><input type="text" class="input" name="p_studentlimit" size="10" maxlength="5" value="<%= data.get("d_studentlimit") %>" tabindex=30>
			  명</td>
		  </tr>
          <tr> 
			<td  class="table_01">교육정원 대기자수</td>
			<td class="table_02_2"><input type="text" class="input" name="p_studentwait" size="10" maxlength="5" value="<%= data.get("d_studentwait") %>" tabindex=30>
			  명</td>
		  </tr>
		  <tr> 
			<td class="table_01">수강료</td>
			<td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="<%= data.get("d_biyong") %>" tabindex=31>
			  원</td>
		  </tr>
          <tr>
            <td align="center" class="table_01">운영담당(교육문의)</td>
            <td class="table_02_2">
				<input type="text" class="input" name="p_charger" value="<%= data.get("d_charger") %>" size="50"/>
            </td>
          </tr>
		  <tr>
			<td class="table_01">고용보험여부</td>
			<td class="table_02_2"> 
			  <select name="p_isgoyong"  class="inputpsearch" onChange="whenIsgoyong()" tabindex=31>
				<option value='Y' >Y</option>
				<option value='N' <%= data.get("d_isgoyong").equals("N") ? "selected" : ""  %>>N</option>
			  </select>
              &nbsp;&nbsp;&nbsp; 교육시간 :  <input type="text" class="input" name="p_edu_sumtime" size="5" maxlength="5"  value="<%= data.get("d_edu_sumtime") %>">
              &nbsp;&nbsp;&nbsp; 해당분야 :  <select name="p_filedapply">
                <option value="A" <%=data.get("d_filedapply").equals("A") ? "selected":"" %>>정보통신</option>
              </select>
			</td>
		  </tr>
		  <tr> 
			<td  class="table_01">환급금액</td>
			<td class="table_02_2" >
			  대규모기업(300명이상기업) : <input type="text" class="input" name="p_goyongpricemajor" size="15" maxlength="10" value="<%= data.get("d_goyongpricemajor") %>" <%= data.get("d_isgoyong").equals("N") ? "disabled" : ""  %> tabindex=36> 원
			  &nbsp;&nbsp;&nbsp;우선지원기업(중소기업) : <input type="text" class="input" name="p_goyongpriceminor" size="15" maxlength="10" value="<%= data.get("d_goyongpriceminor") %>" <%= data.get("d_isgoyong").equals("N") ? "disabled" : ""  %> tabindex=36> 원
			</td>
		  </tr>
          <tr>
			<td class="table_01">수료정보</td>
			<td class="table_02_2">
				<select name="p_suryo_hideshow_yn"  class="inputpsearch" tabindex=28>
					<option value='Y' <%= data.get("d_suryo_hideshow_yn").equals("Y") ? "selected" : ""  %>>보이기</option>
                    <option value='N' <%= data.get("d_suryo_hideshow_yn").equals("N") ? "selected" : ""  %>>숨기기</option>
				</select>
			  </td>
		  </tr>
		  <tr>
			<td colspan="4" class="table_top_line"></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>년도</b></td>
			<td class="table_02_2"><input type="text" name="p_year" id="p_year" value="<%= ss_gyear %>" class="input" size="4" maxlength=4 onChange="existSubjSeq();"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>차수</b></td>
			<td class="table_02_2">
				<input type="text" name="p_subjseq" id="p_subjseq" value="<%= data.get("d_subjseq") %>" readonly class="input" size="4" maxlength=4/>차 &nbsp;&nbsp;&nbsp;
				<input type="text" name="p_seq" id="p_seq" value="<%= data.get("d_seq") %>" readonly class="input" size="4" maxlength=4/>회</td>
		  </tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>수강신청기간</b></td>
			  <td class="table_02_2">
				 <a name="#chk001"></a><input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_propstart", '-') %>">
				일&nbsp;&nbsp;
				<select name="p_propstart2"id="p_propstart2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_propstart"))%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				<input name="p_propend1"id="p_propend1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_propend", '-') %>">
				일&nbsp;
				<select name="p_propend2"id="p_propend2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_propend"))%>
				</select>
				시&nbsp;
              	<input type=hidden name="p_propstart" value="<%= data.get("d_propstart") %>">
              	<input type=hidden name="p_propend" value="<%= data.get("d_propend") %>">
			  </td>
			</tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>학습 기간</b></td>
			  <td class="table_02_2">   
				<a name="#chk002"></a><input name="p_edustart1" id="p_edustart1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_edustart", '-') %>">
				일&nbsp;&nbsp;
				<select name="p_edustart2"id="p_edustart2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_edustart"))%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				<input name="p_eduend1" id="p_eduend1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_eduend", '-') %>">
				일&nbsp;&nbsp;
				<select name="p_eduend2" id="p_eduend2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_eduend"))%>
				</select>
				시&nbsp;
              	<input type=hidden name="p_edustart" value="<%= data.get("d_edustart") %>">
              	<input type=hidden name="p_eduend" value="<%= data.get("d_eduend") %>">
			  </td>
			</tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>결제 기간</b></td>
			  <td class="table_02_2">   
				<a name="#chk003"></a><input name="p_billbegindt1" id="p_billbegindt1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_billbegindt", '-') %>">
				일&nbsp;&nbsp;
				<select name="p_billbegindt2" id="p_billbegindt2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_billbegindt"))%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				<input name="p_billenddt1" id="p_billenddt1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_billenddt", '-') %>">
				일&nbsp;&nbsp;
				<select name="p_billenddt2" id="p_billenddt2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_billenddt"))%>
				</select>
				시&nbsp;
              	<input type=hidden name="p_billbegindt" value="<%= data.get("d_billbegindt") %>">
              	<input type=hidden name="p_billenddt" value="<%= data.get("d_billenddt") %>">
			  </td>
			</tr>
			 <tr>
			   <td class="table_01" height="25"><b>수강신청취소기간</b></td>
			   <td class="table_02_2">
			   	<a name="#chk004"></a><input name="p_startcanceldate1" id="p_startcanceldate1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_startcanceldate", '-') %>" onMouseover="if(this.value=='') this.value=p_propstart1.value"> 일&nbsp;&nbsp;
				<select name="p_startcanceldate2" id="p_startcanceldate2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_startcanceldate"))%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				<input name="p_endcanceldate1" id="p_endcanceldate1" type="text" class="datepicker_input1" size="10" value="<%= data.getDate("d_endcanceldate", '-') %>" onMouseover="if(this.value=='') this.value=p_propend1.value"> 일&nbsp;&nbsp;
				<select name="p_endcanceldate2" id="p_endcanceldate2">
				  <%=FormatDate.getDateOptions(0,23,data.getHour("d_endcanceldate"))%>
				</select>
				시&nbsp;
              	<input type=hidden name="p_startcanceldate" value="<%= data.get("d_startcanceldate") %>">
              	<input type=hidden name="p_endcanceldate" value="<%= data.get("d_endcanceldate") %>">
			   </td>
			 </tr>
		</table>

	   </td></tr><tr><td align="center" valign="top"><br>

		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td class="table_title" rowspan=2>수료정보</td>
			<td class="table_01" rowspan=2>이수기준<input type="hidden" name="p_point" value="100"></td>
			<td class="table_01">총점</td>
			<td class="table_01">진도율</td>
			<td class="table_01">중간평가</td>
			<td class="table_01">최종평가</td>
			<td class="table_01">형성평가</td>
			<td class="table_01">리포트</td>
			<td class="table_01">참여도</td>
			<td class="table_01">기타</td>
		</tr>
		<tr>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="<%= data.get("d_gradscore") %>" tabindex=52></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="<%= data.get("d_gradstep") %>" tabindex=53></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="<%= data.get("d_gradexam") %>" tabindex=54></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradftest" size="10" maxlength="5" value="<%= data.get("d_gradftest") %>" tabindex=55></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradhtest" size="10" maxlength="5" value="<%= data.get("d_gradhtest") %>" tabindex=56></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="<%= data.get("d_gradreport") %>" tabindex=57></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradetc1" size="10" maxlength="5" value="<%= data.get("d_gradetc1") %>" tabindex=58></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradetc2" size="10" maxlength="5" value="<%= data.get("d_gradetc2") %>" tabindex=59></td>
		</tr>
		  <tr> 
			<td class="table_title" rowspan=2>점수정보<br>가중치(%)의 합은 100이어야 합니다.</td>
			<td class="table_01" rowspan=2>가중치(%)</td>
			<td class="table_01">합계(%)</td>
			<td class="table_01">진도율</td>
			<td class="table_01">중간평가</td>
			<td class="table_01">최종평가</td>
			<td class="table_01">형성평가</td>
			<td class="table_01">리포트</td>
			<td class="table_01">참여도</td>
			<td class="table_01">기타</td>
		</tr>
		<tr>
			<td class="table_02_2" ><input type="text" class="input" name="p_wscore" size="10" maxlength="5" value="" tabindex=60 readonly></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="<%= data.get("d_wstep") %>" tabindex=61 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wmtest" size="10" maxlength="5" value="<%= data.get("d_wmtest") %>" tabindex=62 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="<%= data.get("d_wftest") %>" tabindex=63 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_whtest" size="10" maxlength="5" value="<%= data.get("d_whtest") %>" tabindex=64 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="<%= data.get("d_wreport") %>" tabindex=65 onchange="whenChaneWValue()"> 
			  <input type="hidden" name="p_wact" value="<%= data.get("d_wact") %>"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="<%= data.get("d_wetc1") %>" tabindex=66 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wetc2" size="10" maxlength="5" value="<%= data.get("d_wetc2") %>" tabindex=67 onchange="whenChaneWValue()"></td>
		  </tr>
		</table>

      </td></tr><tr><td align="center" valign="top"><br>
       <table width="100%" cellpadding="0" cellspacing="0" border="0">
           <tr>
               <td align="right" style="padding-right:15px">
                   <table border="0" cellpadding="0" cellspacing="0" width="100%">
                       <tr>
                           <td>&nbsp;</td>
                           <td width="92px"><a href="javascript:teachWrite();"><img style="border:none" src="/images/admin/button/btn_daily.gif" alt="훈련일지"/></a></td>
                           <td width="45px"><a href="javascript:teachPopup();"><img style="border:none" src="/images/admin/button/btn_register.gif" alt="등록"/></a></td>
                       </tr>
                       <tr>
                           <td colspan="3" height="2px"></td>
                       </tr>
                   </table>

               </td>
           </tr>
           <tr>
               <td align="center">
                   <table cellspacing="1" cellpadding="5" class="table_out" border="0">
                      <tr>
                        <td class="table_01" width='30px'>번호</td>
                        <td class="table_01" width='100px'>일정 및 시간</td>
                        <td class="table_01">강좌명</td>
                        <td class="table_01" width="80px">강사명</td>
                        <td class="table_01" width="15px">&nbsp;</td>
                    </tr>
                        <%
                            if(detail.size()>0)
                            {
                                for(int i=0;i<detail.size();i++)
                                {
                                    DataBox tmp=(DataBox)detail.get(i);
                                    String dt="";
                                    String ti="";
                                    if(!tmp.get("d_sub_dt").equals(""))
                                        dt=tmp.get("d_sub_dt").substring(0,4)+"/"+tmp.get("d_sub_dt").substring(4,6)+"/"+tmp.get("d_sub_dt").substring(6,8);
                                    if(!tmp.get("d_sub_dt").equals(""))
                                        ti=tmp.get("d_sub_start_time").substring(0,2)+":"+tmp.get("d_sub_start_time").substring(2,4);
                                    out.print("<tr>");
                                    out.print("<td class='table_02_2' style='text-align:center'>");
                                    out.print((i+1));
                                    out.print("</td>");
                                    out.print("<td class='table_02_2 b1b' style='text-align:center' >");
                                    out.print(dt + " " + ti);
                                    out.print("</td>");
                                    out.print("<td class='table_02_2'>");
                                    out.print("<a href=\"javascript:detailPopup('"+tmp.get("d_seq")+"');\">"+tmp.get("d_sub_title")+"</a>");
                                    out.print("</td>");
                                    out.print("<td class='table_02_2' style='text-align:center'>");
                                    out.print(tmp.get("d_name"));
                                    out.print("</td>");
                                    out.print("<td class='table_02_2 a1a' style='text-align:center'>");
                                    out.print("<img src='/images/admin/portal/memo_del.gif' alt='삭제' onclick=\"deleteTeachDetail('"+tmp.get("d_seq")+"');\" style='cursor:pointer'>");
                                    out.print("</td>");
                                    out.print("</tr>");
                                }
                            }
                        %>
                    </table>
               </td>
           </tr>
       </table>


	   </td></tr><tr><td align="center" valign="top">
		<br>
	  <!----------------- 과정맛보기 등록 시작 ----------------->
	  <table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
		  <td width="16%" class="table_title"><b>수강신청 입력정보</b></td>
		  <td class="table_02_2">
		  		<table width="100%" border="0" cellpadding="0" cellspacing="0"><tr><td width="87%">
		  		<input type="hidden" id="p_needinput" name="p_needinput" value="<%=p_needinput%>" />
				 <kocca_select:select name="needinput" sqlNum="0001"  param="0078" onChange="changeNeedInput"
					selectedValue="<%=p_needinput%>" isLoad="true" all="true" isCheckBox="Y" />
		  		<script type="text/javascript">
		  			function changeNeedInput() {
                          var result="";
                          $("[name='needinput']").each(function(){
                              var r = $(this).attr("checked");
                              if(r)
                                result += "1";
                              else
                                result += "0";
                          });

		  				$("[name='needinput']").val(result);
                        $("#p_needinput").val(result);
		  			}
		  		</script>
					<td  align="right"><a href="javascript:fnPreview();"><img style="border:none" src="/images/admin/button/btn_preview.gif" alt="입력양식 미리보기"/></a></td>
					</tr>
				</table>
		  </td>
		</tr>
		<tr>
		  <td class="table_title"><b>교육대상</b></td>
		  <td class="table_02_2">
			<textarea name="p_subjtarget" onkeyup="getObjectLength('p_subjtarget')" onchange="getObjectLength('p_subjtarget', 100)" cols="60" rows="3" style="width:85%" tabindex=70><%= data.get("d_subjtarget") %></textarea> <font id="p_subjtarget_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
		  </td>
		</tr>
		<tr>
		  <td class="table_title"><b>교육목적</b></td>
		  <td class="table_02_2">
			<textarea name="p_intro" onkeyup="getObjectLength('p_intro')" onchange="getObjectLength('p_intro', 4000)" cols="60" rows="3" style="width:85%" tabindex=71><%= data.get("d_intro") %></textarea> <font id="p_intro_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
		  </td>
		</tr>
		<tr>
		  <td class="table_title"><b>교육내용</b></td>
		  <td class="table_02_2">
                <!-- 나모 액티브 스퀘어  시작 -->
				<input type= "hidden" name= "p_explain"  id= "p_explain"  value= "<%=com.dunet.common.util.StringUtil.convertHtmlchars(data.getString("d_explain"))%>">
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted('p_explain');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
		  </td>
		</tr>
	  </table>
	  <!----------------- 과정맛보기 등록 끝 ----------------->
	  <br>
	  <!----------------- 등록, 리스트 버튼 시작 ----------------->
	  <table border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td align="center"><a href="javascript:updateSubject()" tabindex=68><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
		  <td width=8></td>
		  <td align="center"><a href="javascript:goSubjectListPage()" tabindex=69><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
            <td align="center" style="display:none">
                <a href="javascript:DeleteSubject()" tabindex=75><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		</tr>
	  </table>
	  <!----------------- 등록, 리스트 버튼 끝 ----------------->
	  <br>
		</td>
	</tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
