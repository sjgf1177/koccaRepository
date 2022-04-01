<%
//**********************************************************
//  1. 제      목: 과정등록 화면
//  2. 프로그램명: za_cpsubject_i.jsp
//  3. 개      요: 과정등록 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정: 이창훈 2004. 12. 31
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
	String  ss_grcode	  	= box.getStringDefault("s_grcode","ALL");    	 //교육주관(SELECT BOX)
	String  p_grcode  		= box.getString("p_grcode");					//교육주관
    String  ss_upperclass   = box.getString("s_upperclass");    //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");   //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");    //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");    //과정&코스
    String  v_sgkind   		= box.getStringDefault("p_proposetype","");    //수강신청방식
	
%>
<html>
<head>
<title>사이버과정등록</title>
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
	$("#p_crdate_view").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function InsertSubject() {
    if(!chkData()) {
      return;
    }
  document.form2.p_process.value = 'insert';
  document.form2.p_specials.value = document.form2.p_new.value + document.form2.p_hit.value + document.form2.p_recom.value;
  document.form2.submit();
}
function goSubjectListPage() {
  document.form2.p_process.value  = 'listPage';
  document.form2.submit();
}

function chkData() {
  if (document.form2.s_upperclass.value=='ALL') { 
    alert('대분류를 선택하세요.');
    return false;
  }
  
  if (document.form2.s_middleclass.value=='ALL') { 
    alert('중분류를 선택하세요.');
    return false;
  }

  if (!number_chk_noalert(document.form2.p_score.value)) {
    alert('학점배점이 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_ratewbt.value)) {
    alert('학습방법(WBT%)이 잘못입력되었습니다.');
    return false;
  }
    if (!number_chk_noalert(document.form2.p_ratevod.value)) {
    alert('학습방법(VOD%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_framecnt.value)) {
    alert('총프레임수가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_biyong.value)) {
    alert('수강료가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_edudays.value)) {
    alert('학습일차가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_point.value)) {
    alert('이수점수가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_studentlimit.value)) {
    alert('정원이 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_gradscore.value)) {
    alert('이수기준-점수가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_gradstep.value)) {
    alert('이수기준-진도율이 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_gradexam.value)) {
    alert('이수기준-평가가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_gradreport.value)) {
    alert('이수기준-리포트가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_bookprice.value)) {
    alert('교재비가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_wstep.value)) {
    alert('가중치-진도율(%)이 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wmtest.value)) {
    alert('가중치-중간평가(%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wftest.value)) {
    alert('가중치-최종평가(%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_whtest.value)) {
    alert('가중치-형성평가(%)가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_wreport.value)) {
    alert('가중치-리포트(%)가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_wact.value)) {
    alert('가중치-액티비티(%)가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_edulimit.value)) {
    alert('1일최대학습량이 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_wetc1.value)) {
    alert('가중치-기타1(%)가 잘못입력되었습니다.');
    return false;
  }
  
  if (!number_chk_noalert(document.form2.p_wetc2.value)) {
    alert('가중치-기타2(%)가 잘못입력되었습니다.');
    return false;
  }
  
  var weight =  parseFloat(document.form2.p_wstep.value,10) + parseFloat(document.form2.p_wmtest.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_whtest.value,10) + parseFloat(document.form2.p_wreport.value,10) + parseFloat(document.form2.p_wact.value,10) + parseFloat(document.form2.p_wetc1.value,10) + parseFloat(document.form2.p_wetc2.value,10);
  if (weight < 100) {
    alert('가중치가 100% 보다 적습니다');
    return false;
  } else if (weight > 100) {
    alert('가중치가 100% 보다 많습니다.');
    return false;
  }
  if (blankCheck(document.form2.p_subjnm.value)) {
    alert('과정명을 입력하십시요.');
	document.form2.p_subjnm.focus();
    return false;
  }
  var v_crdate = make_date(form2.p_crdate_view.value);
  form2.p_crdate.value = v_crdate;

  return true;
}
function searchCuser() {
  var p_gubun = 'name';
  var p_key1  = document.form2.p_cuseridnm.value;
  var p_key2  = 'p_cuser';
  var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = document.form2.p_museridnm.value;
  var p_key2  = 'p_muser';
  var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
  if (tmp1 == 'p_cuser') {
    document.form2.p_cuseridnm.value = name;
    document.form2.p_cuserid.value   = userid;
  } else if (tmp1 == 'p_muser') {
    document.form2.p_museridnm.value = name;
    document.form2.p_muserid.value   = userid;
  }
}
function searchProducer() {
  var p_key1  = document.form2.p_producernm.value;
  var p_key2  = 'p_producer';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
function searchOwner() {
  var p_key1  = document.form2.p_ownernm.value;
  var p_key2  = 'p_owner';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 회사 검색 후 처리
function receiveGrpComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3) {
  if (tmp1 == 'p_producer') {
    document.form2.p_producernm.value = compnm;
    document.form2.p_producer.value   = comp;
  } else if (tmp1 == 'p_owner') {
    document.form2.p_ownernm.value = compnm;
    document.form2.p_owner.value   = comp;
  }
}

function whenSelection() {
    document.form2.p_process.value = 'insertPage';
	//대분류 선택시 중분류를 ALL로 초기화
	document.form2.s_middleclass.value = '';
    document.form2.submit();
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form2" method="post" action="/servlet/controller.cp.CPCourseServlet">
    <input type="hidden" name="p_process"     value="">
	<input type="hidden" name="s_grcode"		value="<%=ss_grcode%>">
	<input type="hidden" name="p_grcode"		value="<%=p_grcode%>">	
    <input type="hidden" name="p_upperclass"  value="<%=ss_upperclass %>">
    <input type="hidden" name="p_middleclass"  value="<%=ss_middleclass%>">
    <input type="hidden" name="p_lowerclass "  value="<%=ss_lowerclass %>">
    <input type="hidden" name="s_subjcourse "  value="<%=ss_subjcourse %>">
    <input type="hidden" name="p_isonoff"     value="ON">
    <input type="hidden" name="p_specials"    value="">
    <input type="hidden" name="p_middleclass" value="0000">
    <input type="hidden" name="p_lowerclass"  value="0000">
    <input type="hidden" name="p_crdate"      value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
        <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <%=box.getString("p_warnmsg")%><br>
      <br>
      <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="7%" rowspan="30"><b>과정정보</b></td>
            <td colspan="2" class="table_01">교육구분</td>
            <td class="table_02_2" >사이버</td>
            <td width="7%" rowspan="5" class="table_title"><b>URL정보</b></td>
            <td colspan="2" class="table_01">콘텐츠경로</td>
            <td class="table_02_2"><input type="text" class="input" name="p_dir" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">콘텐츠타입</td>
            <td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect ("0007", "", 1, "p_contenttype", "", "", 0)%> </td>
            <td colspan="2" class="table_01">VOD경로</td>
            <td class="table_02_2"><input type="text" class="input" name="p_vodurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">과정명</td>
            <td class="table_02_2"  width="27%"><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value=""></td>
            <td colspan="2" class="table_01">학습URL</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_eduurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">과정분류</td>
            <td class="table_02_2" width="27%"> 
              <!--
			    <% SelectSubjBean selectSubj = new SelectSubjBean();      %> 
			    <select name = "p_2upperclass" onChange = "" class="inputpsearch">
                <%	ArrayList upperList = selectSubj.getOnlyUpperClass(box);
                for(int k = 0; k < upperList.size(); k++) {
                    DataBox selectbox = (DataBox)upperList.get(k);
                %>
                <option value = "<%= selectbox.getString("d_upperclass")%>" <% if ((box.getString("p_upperclass").equals(selectbox.getString("d_upperclass")))) out.print("selected"); %>>
                <%= selectbox.getString("d_classname")%></option>
                <%   }  %>
              </select>  -->
              <%= SelectSubjBean.getUpperClass(box, true, true, false)%>
              <!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
              <%= SelectSubjBean.getMiddleClass(box, true, true, false)%>
              <!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
              <%= SelectSubjBean.getLowerClass(box, false, true, false)%>
            <td colspan="2" class="table_01">맛보기URL</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_preurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">수강신청방식</td>
            <td class="table_02_2" width="27%"> <%=CodeConfigBean.getCodeSelect("sugang_kind","p_proposetype",v_sgkind,"",0)%> </td>
            <td colspan="2" class="table_01">학습목차URL</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_conturl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td width="2%" rowspan="3" class="table_01">결<br>
              재<br>
              여<br>
              부 <br> </td>
            <td height="31" class="table_01">차수개설주관팀장</td>
            <td class="table_02_2" ><select name="p_usesubjseqapproval"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_usesubjseqapproval").equals("Y") ||box.getString("p_usesubjseqapproval").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_usesubjseqapproval").equals("N")) out.println(" selected");%>>N</option>
              </select></td>
            <td width="7%" rowspan="8" class="table_title"><b>학습정보</b></td>
            <td colspan="2" class="table_01">학습방법(WBT%)</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_ratewbt" size="10" maxlength="3" value="0"></td>
            <%//CodeConfigBean.getCodeGubunSelect (SubjectBean.LANGUAGE_GUBUN, "", 1, "p_language", "", "", 0)%>
          </tr>
          <tr> 
            <td   width="12%" height="31" class="table_01">수강신청현업팀장</td>
            <td class="table_02_2" width="27%" ><select name="p_useproposeapproval"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_useproposeapproval").equals("Y") ||box.getString("p_useproposeapproval").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_useproposeapproval").equals("N")) out.println(" selected");%>>N</option>
              </select></td>
            <td colspan="2" class="table_01">학습방법(VOD%)</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_ratevod" size="10" maxlength="3" value="0"></td>
          </tr>
          <tr> 
            <td   width="12%" class="table_01">주관팀장수강승인</td>
            <td class="table_02_2" width="27%"><select name="p_usemanagerapproval"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_usemanagerapproval").equals("Y") ||box.getString("p_usemanagerapproval").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_usemanagerapproval").equals("N")) out.println(" selected");%>>N</option>
              </select></td>
            <td colspan="2" class="table_01">학습환경</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_env" size="38" maxlength="100" value=""></td>
          </tr>
          <tr>
            <td colspan="2" class="table_01">과정구분</td>
            <td class="table_02_2" >
				<input type = "radio" name = "p_isessential" value="D" checked> 일반&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type = "radio" name = "p_isessential" value="S"        > 신임과정<br>
            <input type = "radio" name = "p_isessential" value="W"        > 향상과정
            <input type = "radio" name = "p_isessential" value="T"        > 승진후보자과정
			</td>
            <td colspan="2" class="table_01">&nbsp;</td>
            <td class="table_02_2" >&nbsp;</td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">학점배점</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_score" size="10" maxlength="3" value="0">
              점</td>
            <td colspan="2" class="table_01">강사설명</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_tutor" size="38" maxlength="20" value=""></td>
          </tr>
          <tr> 
            <td colspan="2" rowspan="5" class="table_01">학점배점<br>
              (연구개발) </td>
            <td height="25" class="table_02_2" >필수 
              <input type="text" class="input" name="p_rndcreditreq" size="10" maxlength="3" value="0">
              점</td>
            <td colspan="2" class="table_01">교재명</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_bookname" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td class="table_02_2" >선택 
              <input type="text" class="input" name="p_rndcreditchoice" size="10" maxlength="3" value="0">
              점</td>
            <td colspan="2" class="table_01">1일최대학습량</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_edulimit" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td class="table_02_2" >지정 
              <input type="text" class="input" name="p_rndcreditadd" size="10" maxlength="3" value="0">
              가점 </td>
            <td colspan="2" class="table_01">복습가능여부</td>
            <td class="table_02_2" ><select name="p_isablereview"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_isablereview").equals("Y") ||box.getString("p_isablereview").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isablereview").equals("N")) out.println(" selected");%>>N</option>
              </select></td>
          </tr>
          <tr> 
            <td width="27%" height="34" class="table_02_2" >지정 
              <input type="text" class="input" name="p_rndcreditdeduct" size="10" maxlength="3" value="0">
              감점 </td>
            <td width="7%" rowspan="13" align="center" class="table_title"><b>점수정보</b></td>
            <td colspan="2" class="table_01">이수점수</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_point" size="10" maxlength="5" value="100"></td>
          </tr>
          <tr> 
            <td height="34" class="table_02_2" ><input type="checkbox" name="p_rndjijung" value="Y">
              지정과정여부 
              <input type="checkbox" name="p_rndjikmu" value="Y">
              직무과정여부</td>
            <td   width="4%" rowspan="4" class="table_01">이<br>
              수<br>
              기<br>
              준 </td>
            <td class="table_01">점 수</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">과정특성</td>
            <td class="table_02_2"  width="27%"> 신규 
              <select name="p_new"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_new").equals("Y") ||box.getString("p_new").equals("")) out.println(" selected"); %>>Y</option>
                <option value='N' <% if(box.getString("p_new").equals("N")) out.println(" selected"); %>>N</option>
              </select>
              히트 
              <select name="p_hit"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_hit").equals("Y") ||box.getString("p_hit").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_hit").equals("N")) out.println(" selected"); %>>N</option>
              </select>
              추천 
              <select name="p_recom"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_recom").equals("Y") ||box.getString("p_recom").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_recom").equals("N")) out.println(" selected");%>>N</option>
              </select> </td>
            <td class="table_01">진도율</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">콘텐츠담당</td>
            <td class="table_02_2" width="27%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="160"> 이&nbsp;&nbsp;&nbsp;름:&nbsp; <input name="p_cuseridnm" type="text" class="input" size="14" maxlength=13 value=""> 
                    <br>
                    아이디:&nbsp; <input name="p_cuserid" type="text" class="input" size="14" maxlength=13 value="" readonly> 
                  </td>
                  <td><a href="javascript:searchCuser()"><img src="/images/admin/button/b_resarchbutt.gif" border="0"></a></td>
                </tr>
              </table></td>
            <td class="table_01">평 가</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">운영담당</td>
            <td class="table_02_2" width="27%" > <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="160"> 이&nbsp;&nbsp;&nbsp;름:&nbsp; <input name="p_museridnm" type="text" class="input" size="14" maxlength=13 value=""> 
                    <br>
                    아이디:&nbsp; <input name="p_muserid" type="text" class="input" size="14" maxlength=13 value="" readonly> 
                  </td>
                  <td><a href="javascript:searchMuser()"><img src="/images/admin/button/b_resarchbutt.gif" border="0"></a></td>
                </tr>
              </table></td>
            <td class="table_01">리포트</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td height="31" colspan="2" class="table_01">사용여부</td>
            <td class="table_02_2" width="27%" > <select name="p_isuse"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_isuse").equals("Y") ||box.getString("p_isuse").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isuse").equals("N")) out.println(" selected");%>>N</option>
              </select> 
            <td rowspan="8" class="table_01">가<br>
              중<br>
              치<br>
              (%) </td>
            <td class="table_01">진도율</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">위탁교육여부</td>
            <td class="table_02_2" width="27%" > <select name="p_isoutsourcing"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_isoutsourcing").equals("Y") ||box.getString("p_isoutsourcing").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isoutsourcing").equals("N")) out.println(" selected");%>>N</option>
              </select> </td>
            <td class="table_01">중간평가</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wmtest" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">고용보험여부</td>
            <td class="table_02_2" width="27%"> <select name="p_isgoyong"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_isgoyong").equals("Y") ||box.getString("p_isgoyong").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isgoyong").equals("N")) out.println(" selected");%>>N</option>
              </select> &nbsp;&nbsp;&nbsp; 환급금액 &nbsp;&nbsp;:&nbsp;&nbsp; <input type="text" class="input" name="p_goyongprice" size="10" maxlength="7" value="0"> 
            </td>
            <td class="table_01">최종평가</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">총프레임수</td>
            <td class="table_02_2"><input type="text" class="input" name="p_framecnt" size="10" maxlength="10" value="0"></td>
            <td class="table_01">형성평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_whtest" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">수강료</td>
            <td class="table_02_2" width="27%"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="0"></td>
            <td class="table_01">리포트</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">학습일차</td>
            <td class="table_02_2" width="27%"><input type="text" class="input" name="p_edudays" size="10" maxlength="5" value="0"></td>
            <td class="table_01">액티비티</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wact" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">정원</td>
            <td class="table_02_2" width="27%"><input type="text" class="input" name="p_studentlimit" size="10" maxlength="5" value="0"></td>
            <td class="table_01">기타1</td>
            <td class="table_02_2" width="33%" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">교재여부</td>
            <td class="table_02_2" ><select name="p_usebook"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_usebook").equals("Y") ||box.getString("p_usebook").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_usebook").equals("N")) out.println(" selected");%>>N</option>
              </select></td>
            <td class="table_01">기타2</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wetc2" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td height="36" colspan="2" class="table_01">교재비</td>
            <td class="table_02_2" width="27%" ><input type="text" class="input" name="p_bookprice" size="10" maxlength="5" value="0"></td>
            <td colspan="4" rowspan="4" align="center" class="table_title">&nbsp;</td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">제공회사</td>
            <td class="table_02_2" width="27%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="160"> 이름 : 
                    <input type="text" class="input" name="p_producernm" size="14" maxlength="13" value=""> 
                    <br>
                    코드 : 
                    <input type="text" class="input" name="p_producer" size="14" maxlength="13" value="" readonly> 
                  </td>
                  <td><a href="javascript:searchProducer()"><img src="/images/admin/button/b_resarchbutt.gif" border="0"></a></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">소유회사</td>
            <td class="table_02_2" width="27%" > <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="160"> 이름 : 
                    <input type="text" class="input" name="p_ownernm" size="14" maxlength="13" value="" readonly> 
                    <br>
                    코드 : 
                    <input type="text" class="input" name="p_owner" size="14" maxlength="13" value=""> 
                  </td>
                  <td><a href="javascript:searchOwner()"><img src="/images/admin/button/b_resarchbutt.gif" border="0"></a></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td colspan="2" class="table_01">제작일자</td>
            <td class="table_02_2" width="27%" >
            <input name="p_crdate_view" id="p_crdate_view" type="text" class="datepicker_input1" size="10">
            </td>
          </tr>
        </table>
      <br>
      <!----------------- 과정맛보기 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="16%" class="table_title"><b>교육대상</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_edumans" type="text" class="input" size="118" maxlength="100">
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육목적</b></td>
          <td class="table_02_2">
            <textarea name="p_intro" cols="100" rows="3"></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육내용</b></td>
          <td class="table_02_2">
            <textarea name="p_explain" cols="100" rows="5"></textarea>
          </td>
        </tr>
      </table>
      <!----------------- 과정맛보기 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table width="11%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertSubject()"><img src="/images/admin/button/btn_apply.gif" border="0"></a>&nbsp;</td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
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
