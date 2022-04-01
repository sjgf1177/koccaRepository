<%
//**********************************************************
//  1. 제	  목: 과정수정 화면
//  2. 프로그램명: za_off_Recruit_I.jsp
//  3. 개	  요: 과정수정 화면
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.17
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
<%@ page import = " com.dunet.common.util.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	//DEFINED in relation to select START
	String  ss_grcode	= box.getString("s_grcode");		   //교육그룹
	String  ss_gyear	 = box.getString("s_gyear");			//년도
	String  ss_upperclass = box.getString("s_upperclass");	  //과정대분류
	String  ss_middleclass = box.getString("s_middleclass");	//과정중분류
	String  ss_lowerclass = box.getString("s_lowerclass");	  //과정소분류
	String  ss_subjcourse= box.getString("s_subjcourse");	   //과정
	String  ss_subjseq   = box.getString("s_subjseq");		  //과정 차수
	//DEFINED in relation to select END
	
    DataBox data = (DataBox)request.getAttribute("resultData");
	String  p_upperclass = data.getString("d_upperclass");	  //과정대분류
	String  p_middleclass = data.getString("d_middleclass");	//과정중분류
	String  p_lowerclass = data.getString("d_lowerclass");	  //과정소분류
	String  p_needinput = data.getString("d_needinput");	  //필수입력
%>
<html>
<head>
<title>집합과정 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<SCRIPT LANGUAGE="JavaScript">
<!--

//수정 시작
function updateSubject() {
	if(!chkData()) {
	  return;
	}

	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'update';
	document.form1.p_specials.value = document.form1.p_new.value + document.form1.p_hit.value + document.form1.p_recom.value + document.form1.p_lecture.value;
	document.form1.submit();
}
//수정 끝
function DeleteSubject() {
  if (confirm("과정정보를 삭제 하시겠습니까?")) {
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

  if( document.form1.p_area[0].checked == false && document.form1.p_area[1].checked == false && document.form1.p_area[2].checked == false ) {
	alert("과정코드 구분을 선택하세요.");
	return false;
  }
  
  if (document.form1.p_subj.value=='ALL' || document.form1.p_subj.value=='') { 
	alert('과정코드를 선택하세요.');
	return false;
  }

  if (document.form1.p_upperclass.value=='ALL' || document.form1.p_upperclass.value=='') { 
	alert('대분류를 선택하세요.');
	return false;
  }
  
  if (document.form1.p_middleclass.value=='ALL' || document.form1.p_middleclass.value=='') { 
	alert('중분류를 선택하세요.');
	return false;
  }
  if (document.form1.p_lowerclass.value=='ALL' || document.form1.p_middleclass.value=='') { 
	document.form1.p_lowerclass.value='000';
  }
  if (!number_chk_noalert(document.form1.p_biyong.value)) {
	alert('수강료가 잘못입력되었습니다.');
	return false;
  }
  if (!number_chk_noalert(document.form1.p_edudays.value)) {
	alert('학습일차가 잘못입력되었습니다.');
	return false;
  }
  /*
  if (!number_chk_noalert(document.form1.p_point.value)) {
	alert('이수점수가 잘못입력되었습니다.');
	return false;
  }
  */
  if (!number_chk_noalert(document.form1.p_studentlimit.value)) {
	alert('정원이 잘못입력되었습니다.');
	return false;
  }
  
  if (!number_chk_noalert(document.form1.p_gradscore.value)) {
	alert('이수기준-점수가 잘못입력되었습니다.');
	return false;
  }
  /*
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
*/
  if (!number_chk_noalert(document.form1.p_goyongpricemajor.value)) {
	alert('환급액이 잘못입력되었습니다.');
	return false;
  }

  if (!number_chk_noalert(document.form1.p_edulimit.value)) {
		alert('1일최대학습량이 잘못입력되었습니다.');
		return false;
  }

//  if (!number_chk_noalert(document.form1.p_goyongpriceminor.value)) {
//	alert('환급액(중소기업)이 잘못입력되었습니다.');
//	return false;
//  }
/*  if (!number_chk_noalert(document.form1.p_wstep.value)) {
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
  
  if (!whenChaneWValue()) return false;
  */
  if (blankCheck(document.form1.p_subjnm.value)) {
	alert('과정명을 입력하십시요.');
	document.form1.p_subjnm.focus();
	return false;
  }

 if (document.form1.p_explain.value.length==0) {
		alert('교육내용을 입력하십시요.');
		return false;
 } 
 /*
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
	  var url = "/servlet/controller.course.SubjectServlet?p_process=buyBagPreviewPage&p_needinput=" + $("#p_needinput").val();
      
	  open_window("",url,"0","0","720","600","","","","Y");
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
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="whenChaneWValue();">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form1" enctype="multipart/form-data" method = "POST" action="/servlet/controller.off.OffRecruitAdminServlet">
	<input type="hidden" name="p_process"	 value="">
	<input type="hidden" name="s_grcode"		value="<%=ss_grcode%>">	 <!--in relation to select-->
	<input type="hidden" name="s_gyear"		 value="<%=ss_gyear%>">	  <!--in relation to select-->
	<input type="hidden" name="s_upperclass"	value="<%=ss_upperclass%>"> <!--in relation to select-->
	<input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
	<input type="hidden" name="s_lowerclass"	value="<%=ss_lowerclass%>"><!--in relation to select-->
	<input type="hidden" name="s_subjcourse"	value="<%=ss_subjcourse%>"> <!--in relation to select-->
	<input type="hidden" name="s_subjseq"	   value="<%=ss_subjseq%>">	<!--in relation to select-->
	<input type="hidden" name="s_action">

	<input type="hidden" name="p_specials"	value="">

	<input type="hidden" name = "p_isoutsourcing" value="N">
	<input type="hidden" name="p_edulimit" value="0">
	<input type="hidden" name="p_musertel" value="">
	

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off12.gif" ></td>
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
			<td class="table_02_2" > 과정코드 : <input type="text" class="input" name="p_subj" size="10" maxlength="10" value="<%= data.get("d_subj") %>" readonly>
				 <kocca_select:select name="p_area" sqlNum="0002"  param="101" type="2.2"
					onChange="" attr=" " selectedValue='<%= data.get("d_area") %>' isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr> 
			<td  class="table_01">과정명<font color="red">(*)</font></td>
			<td class="table_02_2"><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value="<%= data.get("d_subjnm") %>" tabindex=""></td>
		  </tr>
		  <tr> 
			<td class="table_01">과정분류<font color="red">(*)</font></td>
			<td height="50" class="table_02_2">
			<script type="text/javascript">
			var isFirst = 0;
			function fnSearchBoxs_middleclass() {
				if(isFirst++>0)
				changep_lowerclass($("#p_upperclass").val(), $("#p_middleclass").val());
			}
			</script>
				대분류 <kocca_select:select name="p_upperclass" sqlNum="off.0002"  param=" "
				onChange="changep_middleclass(this.value);" attr=" " selectedValue="<%= p_upperclass %>" isLoad="true" all="true" /><br/>
				중분류 <kocca_select:select name="p_middleclass" sqlNum="off.0003"  param="<%= p_upperclass %>"
				onChange="fnSearchBoxs_middleclass();" afterScript="fnSearchBoxs_middleclass" attr=" " selectedValue="<%= p_middleclass %>" isLoad="true" all="true" /><br/>
				소분류 <kocca_select:select name="p_lowerclass" sqlNum="off.0004"  param="<%= p_upperclass %>"  param2="<%= p_middleclass %>"
				onChange="" attr=" " selectedValue="<%=p_lowerclass %>" isLoad="true" all="true" />
			  <input type="hidden" name="p_proposetype" value="1">
			</td> 
		  </tr>

		  <tr>
			<td class="table_01">수료증출력가능여부</td>
			<td height="50" class="table_02_2">
			  <select name="p_suryoyn"  class="inputpsearch" tabindex=17>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_suryoyn").equals("N") ? "selected" : ""  %>>N</option>
			  </select>
				<input type="text" class="input" name="p_suryotitle" size="38" maxlength="100" value="<%= data.get("d_suryotitle") %>" tabindex="">
			</td>
		  </tr>
          <tr>
            <td class="table_01">연관검색어</td>
            <td height="50" class="table_02_2">
                <input type="text" class="input" name="p_search_nm" size="60" maxlength="100" value="<%= data.get("d_search_nm") %>" tabindex="" >
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
				<!--운영담당-->담당교수 &nbsp;
				<a href="javascript:searchMuser()" tabindex="21"><img src="/images/admin/button/b_resarchbutt.gif" align="absmiddle" border="0"></a>
			</td>
			<td height="34" class="table_02_2" >
					<div id="muserList"></div>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script>
<!--
//muserList.1
	$(document).ready(function()	{
		listMuser();
	});
	function listMuser() {
		$.post("/servlet/controller.common.TagServlet"
				, {sqlNum:"offmuserList.1", param:"<%= data.get("d_subj") %>",type:4,objNm:"muser"}
				, function(data) {
					$("#muserList").html(data);
				});
	}
	function addMuser(id, name) {
		var innerDiv = $("#muserList").html();
		if (innerDiv.indexOf("결과없음")!=-1) innerDiv = "";
		innerDiv = innerDiv + "<div id='muserDiv' class='muserClass'>"+name+"("+id+")"
				+"<img alt='위로' src='/images/admin/templet/b_atop.gif' align='absMiddle' vspace='10' border='0' onclick=\"upmUser(this);\"/>"
				+"<img alt='아래로' src='/images/admin/templet/b_adown.gif' align='absMiddle' vspace='10' border='0' onclick=\"downmUser(this);\"/>"
				+"<img alt='삭제' src='/images/admin/button/btn_del.gif' align='absMiddle' vspace='10' border='0' id=muserDelete onclick=\"$('#muserCnt').val($('#muserCnt').val()*1-1);$(this).parent().remove();\"><input type='hidden' name='muser' value='"+id+"'/></div>";
		$("#muserList").html(innerDiv);
	}
	function upmUser(selectedDiv) {
		var selectedDiv = $($(selectedDiv).parent());
		var inHtml = selectedDiv.html();

		if(selectedDiv.prev().html()!=null) {
			selectedDiv.html(selectedDiv.prev().html());
			selectedDiv.prev().html(inHtml);
		}
	}
	function downmUser(selectedDiv) {
		var selectedDiv = $($(selectedDiv).parent());
		var inHtml = selectedDiv.html();
		if(selectedDiv.next().html()!=null) {
			selectedDiv.html(selectedDiv.next().html());
			selectedDiv.next().html(inHtml);
		}
	}
-->
</script>
			</td>
		  </tr>
		  <tr> 
			<td height="31" class="table_01">학기사용여부</td>
			<td class="table_02_2" > <select name="p_isterm"  class="inputpsearch" onChange="whenIsuse()" tabindex=26>
				<option value='Y'>Y</option>
				<option value='N' <%= data.get("d_isterm").equals("N") ? "selected" : ""  %>>N</option>
			  </select> 
			</td>
		  </tr>
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
			  </select>
			  <select style='display:none;' name="p_isintroduction"  class="inputpsearch" tabindex=28>
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
					<option value='M' <%= data.get("d_edudaystype").equals("M") ? "selected" : ""  %>>개월</option>
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
			<td  class="table_01">교육정원 대기자</td>
			<td class="table_02_2"><input type="text" class="input" name="p_studentwait" size="10" maxlength="5" value="<%= data.get("d_studentwait") %>" tabindex=30>
			  명</td>
		  </tr>
		  <tr> 
			<td class="table_01">수강료</td>
			<td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="<%= data.get("d_biyong") %>" tabindex=31>
			  원</td>
		  </tr>
          <tr>
			<td class="table_01">이달의과정 추천수</td>
			<td class="table_02_2"><input type="text" class="input" name="p_hitnumber" size="10" maxlength="7" value="<%=data.get("d_hitnumber")%>" tabindex=32></td>
		  </tr>
		  <tr>
			<td class="table_01">고용보험여부</td>
			<td class="table_02_2"> 
			  <select name="p_isgoyong"  class="inputpsearch" onChange="whenIsgoyong()" tabindex=31>
				<option value='Y' >Y</option>
				<option value='N' <%= data.get("d_isgoyong").equals("N") ? "selected" : ""  %>>N</option>
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
			<td class="table_01">학점인증과정</td>
			<td class="table_02_2">
			  <select name="p_isunit">
				<option value='N'>N</option>
				<option value='Y' <%= data.get("d_isunit").equals("Y") ? "selected" : ""  %>>Y</option>
			  </select>
			</td>
		  </tr>
		</table>
	   </td></tr><tr><td align="center" valign="top"><br>
	   <input type="hidden" name="p_point" value="100">
	    <input type="hidden" name="p_gradscore" value="0">
		<input type="hidden" name="p_gradstep" value="0" >
		<input type="hidden" name="p_gradexam" value="0" >
		<input type="hidden" name="p_gradftest" value="0" >
		<input type="hidden" name="p_gradhtest" value="0" >
		<input type="hidden" name="p_gradreport" value="0" >
		<input type="hidden" name="p_gradetc1" value="0" >
		<input type="hidden" name="p_gradetc2" value="0" >
		<input type="hidden" name="p_wact" value="0">
		<input type="hidden" name="p_wscore" value="0" >
		<input type="hidden" name="p_wstep" value="0" >
		<input type="hidden" name="p_wmtest" value="0" >
		<input type="hidden" name="p_wftest" value="0" >
		<input type="hidden" name="p_whtest" value="0" >
		<input type="hidden" name="p_wreport" value="0" >
		<input type="hidden" name="p_wetc1" value="0" >
		<input type="hidden" name="p_wetc2" value="0" >
		<!-- 
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
			<td class="table_02_2" ><input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="<%//= data.get("d_gradscore") %>" tabindex=52></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="<%//= data.get("d_gradstep") %>" tabindex=53></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="<%//= data.get("d_gradexam") %>" tabindex=54></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradftest" size="10" maxlength="5" value="<%//= data.get("d_gradftest") %>" tabindex=55></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradhtest" size="10" maxlength="5" value="<%//= data.get("d_gradhtest") %>" tabindex=56></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="<%//= data.get("d_gradreport") %>" tabindex=57></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradetc1" size="10" maxlength="5" value="<%//= data.get("d_gradetc1") %>" tabindex=58></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_gradetc2" size="10" maxlength="5" value="<%//= data.get("d_gradetc2") %>" tabindex=59></td>
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
			<td class="table_02_2" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="<%//= data.get("d_wstep") %>" tabindex=61 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wmtest" size="10" maxlength="5" value="<%//= data.get("d_wmtest") %>" tabindex=62 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="<%//= data.get("d_wftest") %>" tabindex=63 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_whtest" size="10" maxlength="5" value="<%//= data.get("d_whtest") %>" tabindex=64 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="<%//= data.get("d_wreport") %>" tabindex=65 onchange="whenChaneWValue()"> 
			  <input type="hidden" name="p_wact" value="<%//= data.get("d_wact") %>"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="<%//= data.get("d_wetc1") %>" tabindex=66 onchange="whenChaneWValue()"></td>
			<td class="table_02_2" ><input type="text" class="input" name="p_wetc2" size="10" maxlength="5" value="<%//= data.get("d_wetc2") %>" tabindex=67 onchange="whenChaneWValue()"></td>
		  </tr>
		</table>
		 -->
	   </td></tr><tr><td align="center" valign="top">
		<br>
	  <!----------------- 과정맛보기 등록 시작 ----------------->
	  <table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
		  <td width="16%" class="table_title"><b>수강신청 입력정보</b></td>
		  <td width="81%" class="table_02_2">
		  		<table><tr><td width="80%">
		  		<input type='hidden' id='p_needinput' name='p_needinput'/>
				 <kocca_select:select name="needinput" sqlNum="0001"  param="0078" onChange="changeNeedInput"
					selectedValue="<%=p_needinput%>" isLoad="true" all="true" isCheckBox="Y" />
		  		<script>
		  			function changeNeedInput() {
		  				$("#p_needinput").val('');
			  			for (needInput_i = 0; needInput_i < document.all.needinput.length; needInput_i++) {
				  			if (document.all.needinput[needInput_i].checked) {
                                $("#p_needinput").val($("#p_needinput").val()+'1');
                            } else {
                                $("#p_needinput").val($("#p_needinput").val()+'0');
                            }
			  			}
		  			}
		  		</script>
					</td><td width="5%">&nbsp;</td>
					<td><a href="javascript:fnPreview();"><img style="border:none" src="/images/admin/button/btn_preview.gif" alt="입력양식 미리보기"/></a></td>
					</tr>
				</table>
		  </td>
		</tr>
		<tr>
		  <td width="16%" class="table_title"><b>교육대상</b></td>
		  <td width="81%" class="table_02_2">
			<textarea name="p_subjtarget" cols="100" rows="3" tabindex=70><%= data.get("d_subjtarget") %></textarea>
		  </td>
		</tr>
		<tr>
		  <td class="table_title"><b>교육목적</b></td>
		  <td class="table_02_2">
			<textarea name="p_intro" cols="100" rows="3" tabindex=71><%= data.get("d_intro") %></textarea>
		  </td>
		</tr>
		<tr>
		  <td class="table_title"><b>교육내용</b></td>
		  <td class="table_02_2">
		   	<!-- 나모 Editor  -->
			   <!--p align="left">
						<input name="p_explain" type="hidden" value="<%= data.getHtmlString("d_explain") %>"/>
						<script language = "javascript" src = "/script/user_patch.js"></script>
						<script language='javascript'>object_namopatch('100%','350');</script>
			   </p-->
			   <!-- 나모 Editor  -->
                <!-- 나모 액티브 스퀘어  시작 -->
				<input type= "hidden" name= "p_explain"  id= "p_explain"  value= "<%=StringUtil.convertHtmlchars(data.getString("d_explain"))%>">
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted('p_explain');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
		  </td>
		</tr>        
		<tr>
          <td width="16%" class="table_title"><b>메인화면이미지</b></td>
          <td width="81%" class="table_02_2">
<%  if (!data.getString("d_submainfilenamereal").equals("")) {      %>
            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= data.getString("d_submainfilenamenew")%>&p_realfile=<%= data.getString("d_submainfilenamereal")%>'><%=data.getString("d_submainfilenamereal")%></a>&nbsp;
             <input type = "checkbox" name = "p_submainfile0" value = "1"> (삭제시 체크)
<%  }                                                       %>
            <input type="hidden" name = "p_submainfile1" value="<%=data.getString("d_submainfilenamereal")%>">
            <input type="hidden" name = "p_submainfile2" value="<%=data.getString("d_submainfilenamenew")%>">
            <input name="p_submainfile" type="file" class="input" size="118" maxlength="100" tabindex=65>&nbsp;
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
            <td align="center">
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
