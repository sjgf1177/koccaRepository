<%
//**********************************************************
//  1. 제      목: 교육과정정보 수정관리
//  2. 프로그램명: za_cpcourse_u.jsp
//  3. 개      요: 교육과정정보 수정관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.23
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 0;
//String v_subj = "", v_subjnm = "", v_isonoff = "", v_preurl = "", v_onoff = "", v_cpnm = "", v_cpsubj = "";

//CPSubjectData data = (CPSubjectData)request.getAttribute("SubjectData");

//ArrayList list = (ArrayList)request.getAttribute("selectCourseList");

String s_gadmin = box.getSession("gadmin");

String v_gadmin = s_gadmin.substring(0,1);


DataBox dbox = (DataBox)request.getAttribute("SubjectData");
DataBox dbox1 = (DataBox)request.getAttribute("CpParamData");


String v_subj       = "";
String v_subjnm     = "";
String v_isonoff    = "";
String v_cuserid    = "";
String v_owner      = "";
String v_eduurl     = "";
String v_preurl     = "";
String v_cpsubj     = "";
String v_conturl    = "";
String v_edumans    = "";
String v_intro      = "";
String v_explain    = "";
String v_musertel   = "";
String v_crdate     = "";

String v_point      = "";
String v_gradscore  = "";
String v_gradstep   = "";
String v_wstep      = "";
String v_wmtest     = "";
String v_wftest     = "";
String v_wreport    = "";
String v_wact       = "";
String v_wetc1      = "";
String v_wetc2      = "";
String v_edutimes   = "";
String v_whtest     = "";
String v_gradreport = "";
String v_gradexam   = "";
String v_gradftest  = "";
String v_gradhtest  = "";
String v_biyong     = "";
String v_bookprice  = "";
String v_cpapproval = "";
String v_introducefilenamereal = "";
String v_introducefilenamenew    = "";
String v_informationfilenamereal = "";
String v_informationfilenamenew  = "";
String v_usebook ="";

String v_useridparam    = "";
String v_nameparam      = "";
String v_resnoparam     = "";
String v_conoparam      = "";
String v_pwdparam       = "";
String v_deptnmparam    = "";
String v_jikwiparam     = "";
String v_jikwinmparam   = "";
String v_compparam      = "";
String v_companynmparam = "";
String v_subjparam      = "";
String v_subjseqparam   = "";
String v_gadminparam    = "";
String v_param1         = "";
String v_paramvalue1    = "";
String v_param2         = "";
String v_paramvalue2    = "";
String v_param3         = "";
String v_paramvalue3    = "";
String v_param4         = "";
String v_paramvalue4    = "";
String v_param5         = "";
String v_paramvalue5    = "";
String v_param6         = "";
String v_paramvalue6    = "";



if(dbox != null){

  v_subj       = dbox.getString("d_subj");
  v_subjnm     = dbox.getString("d_subjnm");
  v_isonoff    = dbox.getString("d_isonoff");
  v_cuserid    = dbox.getString("d_cuserid");
  v_owner      = dbox.getString("d_owner");
  v_eduurl     = dbox.getString("d_eduurl");
  v_preurl     = dbox.getString("d_preurl");
  v_cpsubj     = dbox.getString("d_cpsubj");
  v_conturl    = dbox.getString("d_conturl");
  v_edumans    = dbox.getString("d_edumans");
  v_intro      = dbox.getString("d_intro");
  v_explain    = dbox.getString("d_explain");

  v_point      = dbox.getString("d_point");
  v_gradscore  = dbox.getString("d_gradscore");
  v_gradstep   = dbox.getString("d_gradstep");
  v_wstep      = dbox.getString("d_wstep");
  v_wmtest     = dbox.getString("d_wmtest");
  v_wftest     = dbox.getString("d_wftest");
  v_wreport    = dbox.getString("d_wreport");
  v_wact       = dbox.getString("d_wact");
  v_wetc1      = dbox.getString("d_wetc1");
  v_wetc2      = dbox.getString("d_wetc2");
  v_edutimes   = dbox.getString("d_edutimes");
  v_whtest     = dbox.getString("d_whtest");
  v_gradreport = dbox.getString("d_gradreport");
  v_gradexam   = dbox.getString("d_gradexam");
  v_gradftest  = dbox.getString("d_gradftest");
  v_gradhtest  = dbox.getString("d_gradhtest");
  v_bookprice  = dbox.getString("d_bookprice");
  v_biyong     = dbox.getString("d_biyong");
  v_musertel   = dbox.getString("d_musertel");
  v_crdate     = dbox.getString("d_crdate");
  v_cpapproval = dbox.getString("d_cpapproval");

  v_introducefilenamereal   = dbox.getString("d_introducefilenamereal");
  v_introducefilenamenew    = dbox.getString("d_introducefilenamenew");
  v_informationfilenamereal = dbox.getString("d_informationfilenamereal");
  v_informationfilenamenew  = dbox.getString("d_informationfilenamenew");
  v_usebook  = dbox.getString("d_usebook");

}


if(dbox1 != null){
  v_useridparam    = dbox1.getString("d_useridparam");
  v_nameparam      = dbox1.getString("d_nameparam");
  v_resnoparam     = dbox1.getString("d_resnoparam");
  v_conoparam      = dbox1.getString("d_conoparam");
  v_pwdparam       = dbox1.getString("d_pwdparam");
  v_deptnmparam    = dbox1.getString("d_deptnmparam");
  v_jikwiparam     = dbox1.getString("d_jikwiparam");
  v_jikwinmparam   = dbox1.getString("d_jikwinmparam");
  v_compparam      = dbox1.getString("d_compparam");
  v_companynmparam = dbox1.getString("d_companynmparam");
  v_subjparam      = dbox1.getString("d_subjparam");
  v_subjseqparam   = dbox1.getString("d_subjseqparam");
  v_gadminparam    = dbox1.getString("d_gadminparam");
  v_param1         = dbox1.getString("d_param1");
  v_paramvalue1    = dbox1.getString("d_paramvalue1");
  v_param2         = dbox1.getString("d_param2");
  v_paramvalue2    = dbox1.getString("d_paramvalue2");
  v_param3         = dbox1.getString("d_param3");
  v_paramvalue3    = dbox1.getString("d_paramvalue3");
  v_param4         = dbox1.getString("d_param4");
  v_paramvalue4    = dbox1.getString("d_paramvalue4");
  v_param5         = dbox1.getString("d_param5");
  v_paramvalue5    = dbox1.getString("d_paramvalue5");
  v_param6         = dbox1.getString("d_param6");
  v_paramvalue6    = dbox1.getString("d_paramvalue6");
}



String v_searchtext = box.getString("p_searchtext");
int v_pageno = box.getInt("p_pageno");
String v_year = box.getString("p_year");
String v_cp = box.getString("p_cp");

//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");

%>

<html>
<head>
<title>사이버과정수정</title>
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
	$("#p_crdate1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function UpdateSubject() {
    if(!chkData()) {
      return;
    }
  document.form2.p_process.value = 'update';
  document.form2.submit();
}
function DeleteSubject() {
  if (confirm("과정정보를 삭제 하시겠습니까?")) {
    document.form2.p_process.value = 'delete';
    document.form2.submit();
  }
}
function goSubjectListPage() {
  document.form2.p_process.value  = 'listPage';
  document.form2.submit();
}
function chkData() {
	ff = document.form2;
	ff.p_crdate.value=make_date(ff.p_crdate1.value);

	//alert(ff.p_crdate.value);
	//return;

//alert(document.form2.p_edutimes.value);
	if (!number_chk_noalert(document.form2.p_edutimes.value)) {
		alert('학습시간이 잘못입력되었습니다.');
		return false;
	}


/*
	if (!number_chk_noalert(document.form2.p_point.value)) {
		alert('이수점수가 잘못입력되었습니다.');
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
	if (!number_chk_noalert(ff.p_gradexam.value)) {
		alert('이수기준-중간평가가 잘못입력되었습니다.');
		return false;
	}
	if (!number_chk_noalert(ff.p_gradftest.value)) {
		alert('이수기준-최종평가가 잘못입력되었습니다.');
		return false;
	}
	if (!number_chk_noalert(ff.p_gradhtest.value)) {
		alert('이수기준-형성평가가 잘못입력되었습니다.');
		return false;
	}

	if (!number_chk_noalert(ff.p_gradreport.value)) {
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
  if (!number_chk_noalert(document.form2.p_wreport.value)) {
    alert('가중치-리포트(%)가 잘못입력되었습니다.');
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

  var weight =  parseFloat(document.form2.p_wstep.value,10) +  parseFloat(document.form2.p_wmtest.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_whtest.value,10) + parseFloat(document.form2.p_wreport.value,10) +  parseFloat(document.form2.p_wact.value,10) + parseFloat(document.form2.p_wetc1.value,10) + parseFloat(document.form2.p_wetc2.value,10);
  if (weight < 100) {
    alert('가중치가 100% 보다 적습니다');
    return false;
  } else if (weight > 100) {
    alert('가중치가 100% 보다 많습니다.');
    return false;
  }

*/

/*
  var weight =  parseFloat(document.form2.p_wstep.value,10) + parseFloat(document.form2.p_wmtest.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_wreport.value,10) + parseFloat(document.form2.p_wetc1.value,10) + parseFloat(document.form2.p_wetc2.value,10);
  if (weight < 100) {
    alert('가중치가 100% 보다 적습니다');
    return false;
  } else if (weight > 100) {
    alert('가중치가 100% 보다 많습니다.');
    return false;
  }
*/
  /*
  if (blankCheck(document.form2.p_subjnm.value)) {
    alert('과정명을 입력하십시요.');
    return false;
  }
  */

  return true;
  //return false;
}


function cpApproval( p_cpapproval){

  if(!chkData()) {
    return;
  }

  document.form2.action="/servlet/controller.cp.CPCourseServlet";
  document.form2.p_cpapproval.value = p_cpapproval;
  document.form2.p_process.value = 'CpApproval';
  document.form2.submit();

}


-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form2" enctype= "multipart/form-data"  method="post" action="/servlet/controller.cp.CPCourseServlet">
    <input type="hidden" name="p_process"		value="">
    <input type="hidden" name="p_cpapproval"    value="">
	<input type="hidden" name="p_pageno"		value="<%=v_pageno%>">
    <input type="hidden" name="p_year"			value="<%=v_year%>">
    <input type="hidden" name="p_cp"			value="<%=v_cp%>">
    <input type="hidden" name="p_searchtext"	value="<%=v_searchtext%>">
    <input type="hidden" name="p_subj"			value="<%=v_subj%>">
	<input type="hidden" name="p_subjnm"		value="<%=v_subjnm%>">
	<input type="hidden" name="p_crdate"		value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td background="/images/admin/community/title_bg.gif"><img src="/images/admin/cp/cp_tit_course.gif" border="0"></td>
        </tr>
      </table>
        <br>
        <table width="780" border="0" cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td rowspan="8" class="table_title">과정정보</td>
            <td class="table_01">과정명</td>
            <td class="table_02_2"><%=v_subjnm%> &nbsp;<font color="red"><b>[<%=TextComBean.getApprovalTxt(v_cpapproval, "cpsubj")%>]</b></font></td>
            <td rowspan="8" class="table_title">URL 정보</td>
            <td class="table_01">학습 URL</td>
            <td class="table_02_2"><input type="text" class="input" name="p_eduurl" size="38" maxlength="100" value="<%=v_eduurl%>"></td>
          </tr>
          <tr>
            <td class="table_01">과정코드</td>
            <td class="table_02_2"><%=v_subj%></td>
            <td class="table_01">미리보기 URL</td>
            <td class="table_02_2"><input type="text" class="input" name="p_preurl" size="38" maxlength="100" value="<%=v_preurl%>"></td>
          </tr>
          <tr>
            <td class="table_01">외주업체과정코드</td>
            <td class="table_02_2"><input type="text" class="input" name="p_cpsubj" size="38" maxlength="100" value="<%=v_cpsubj%>"></td>
            <td class="table_01">학습목차 URL</td>
            <td class="table_02_2"><input type="text" class="input" name="p_conturl" size="38" maxlength="100" value="<%=v_conturl%>"></td>
          </tr>
          <tr>
            <td class="table_01">학습시간</td>
            <td class="table_02_2"><input type="text" class="input" name="p_edutimes" size="10" maxlength="5" value="<%=v_edutimes%>" tabindex=31> 시간 </td>
            <td class="table_01" colspan="2"></td>
          </tr>
          <tr>
            <td class="table_01">교재유무</td>
            <td class="table_02_2">
			<select name="p_usebook"  class="inputpsearch" tabindex=26>
                <option value='Y' <%if(v_usebook.equals("Y")){out.print("selected");}%>>Y</option>
                <option value='N' <%if(v_usebook.equals("N")){out.print("selected");}%>>N</option>
              </select>
			</td>
            <td class="table_01" colspan="2"></td>
          </tr>
          <tr>
            <td class="table_01">교재금액</td>
            <td class="table_02_2"><input type="text" class="input" name="p_bookprice" size="10" maxlength="5" value="<%=v_bookprice%>" tabindex=31></td>
            <td class="table_01" colspan="2"></td>
          </tr>
          <tr>
            <td class="table_01">과정금액</td>
            <td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="5" value="<%=v_biyong%>" tabindex=31> </td>
            <td class="table_01" colspan="2"></td>
          </tr>
          <tr>
            <td class="table_01">과정개발일자</td>
            <td class="table_02_2">
            <%if(v_crdate.length() > 0){%>
              <input type="text" class="datepicker_input1" name="p_crdate1" id="p_crdate1" size="15" value="<%=FormatDate.getFormatDate(StringManager.substring(v_crdate,0,8),"yyyy-MM-dd")%>" tabindex=31>
            <%}else{%>
              <input type="text" class="datepicker_input1" name="p_crdate1" id="p_crdate1" size="15" value="" tabindex=31 readonly>
            <%}%>
            </td>
            <td class="table_01" colspan="2"></td>
          </tr>
          <tr>
            <td rowspan="8" class="table_title"><p>점수정보<br>
                <br>
                (이수기준)</p></td>
            <td class="table_01">진도율</td>
            <td class="table_02_2"><%=v_gradstep%><input type="hidden" class="input" name="p_gradstep" size="10" maxlength="5" value="<%=v_gradstep%>"></td>
            <td rowspan="8" class="table_title"><p>점수정보<br>
                <br>
                (가중치%)</p></td>
            <td class="table_01">진도율</td>
            <td class="table_02_2"><%=v_wstep%><input type="hidden" class="input" name="p_wstep" size="10" maxlength="5" value="<%=v_wstep%>"></td>
          </tr>
          <tr>
            <td class="table_01">중간평가</td>
            <td class="table_02_2"><%=v_gradexam%><input type="hidden" class="input" name="p_gradexam" size="10" maxlength="5" value="<%=v_gradexam%>"></td>
            <td class="table_01">중간평가</td>
            <td class="table_02_2"><%=v_wmtest%><input type="hidden" class="input" name="p_wmtest" size="10" maxlength="5" value="<%=v_wmtest%>"></td>
          </tr>
          <tr>
            <td class="table_01">최종평가</td>
            <td class="table_02_2"><%=v_gradftest%><input type="hidden" class="input" name="p_gradftest" size="10" maxlength="5" value="<%=v_gradftest%>"></td>
            <td class="table_01">최종평가</td>
            <td class="table_02_2"><%=v_wftest%><input type="hidden" class="input" name="p_wftest" size="10" maxlength="5" value="<%=v_wftest%>"></td>
          </tr>
          <tr>
            <td class="table_01">형성평가</td>
            <td class="table_02_2"><%=v_gradhtest%><input type="hidden" class="input" name="p_gradhtest" size="10" maxlength="5" value="<%=v_gradhtest%>"></td>
            <td class="table_01">형성평가</td>
            <td class="table_02_2"><%=v_whtest%><input type="hidden" class="input" name="p_whtest" size="10" maxlength="5" value="<%=v_whtest%>"></td>
          </tr>
          <tr>
            <td class="table_01">리포트</td>
            <td class="table_02_2" ><%=v_gradreport%><input type="hidden" class="input" name="p_gradreport" size="10" maxlength="5" value="<%=v_gradreport%>"></td>
            <td class="table_01">리포트</td>
            <td class="table_02_2"><%=v_wreport%><input type="hidden" class="input" name="p_wreport" size="10" maxlength="5" value="<%=v_wreport%>"></td>
          </tr>
          <tr>
            <td class="table_01">점수<input type="hidden" name="p_point" value="100"></td>
            <td class="table_02_2"><%=v_gradscore%><input type="hidden" class="input" name="p_gradscore" size="10" maxlength="5" value="<%=v_gradscore%>"></td>
            <td class="table_01">기타1</td>
            <td class="table_02_2"><%=v_wetc1%><input type="hidden" class="input" name="p_wetc1" size="10" maxlength="5" value="<%=v_wetc1%>"></td>
          </tr>
          <tr>
			<td colspan="2" rowspan="2" class="table_01">&nbsp;</td>
            <td class="table_01">기타2</td>
            <td class="table_02_2"><%=v_wetc2%><input type="hidden" class="input" name="p_wetc2" size="10" maxlength="5" value="<%=v_wetc2%>"></td>
          </tr>
          <tr>
            <td class="table_01" colspan="4" align="center">가중치(%)의 합은 100이 되어야 합니다.</td>
          </tr>
          <input type="hidden" name="p_wact" value="0">
        </table>
        <p>
          <!----------------- 과정맛보기 등록 시작 ----------------->
        </p>
        <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="16%" class="table_title"><b>소개이미지</b></td>
          <td width="81%" class="table_02_2">
            <%  if (!v_introducefilenamereal.equals("")) {      %>
            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_introducefilenamenew%>&p_realfile=<%=v_introducefilenamereal%>'><%=v_introducefilenamereal%></a>&nbsp;
            <input type = "checkbox" name = "p_introducefile0" value = "1"> (삭제시 체크)
            <%  }                                                       %>
            <input type="hidden" name = "p_introducefile1" value="<%=v_introducefilenamereal%>">
            <input type="hidden" name = "p_introducefile2" value="<%=v_introducefilenamenew%>">
            <input name="p_introducefile" type="file" class="input" size="118" maxlength="100" >
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>파일</b></td>
          <td width="81%" class="table_02_2">
            <%  if (!v_informationfilenamereal.equals("")) {      %>
            &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=v_informationfilenamenew%>&p_realfile=<%= v_informationfilenamereal%>'><%=v_informationfilenamereal%></a>&nbsp;
             <input type = "checkbox" name = "p_informationfile0" value = "1"> (삭제시 체크)
            <%  }                                                       %>
            <input type="hidden" name = "p_informationfile1" value="<%=v_informationfilenamereal%>">
            <input type="hidden" name = "p_informationfile2" value="<%=v_informationfilenamenew%>">
            <input name="p_informationfile" type="file" class="input" size="118" maxlength="100" >
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>교육대상</b></td>
          <td width="81%" class="table_02_2">

			 <textarea name="p_edumans" cols="100" rows="5"><%=v_edumans%></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육목적</b></td>
          <td class="table_02_2">
            <textarea name="p_intro" cols="100" rows="5"><%=v_intro%></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육내용</b></td>
          <td class="table_02_2">
            <textarea name="p_explain" cols="100" rows="5"><%=v_explain%></textarea>
          </td>
        </tr>
      </table>
      <!----------------- 과정맛보기 등록 끝 ----------------->
      <br>


      <!----------------- 파라미터정보 시작----------------->
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" class="table_out">
          <tr align="center" bgcolor="#EAF4FD">
            <td height="26" colspan="3" nowrap class="tdtitle"><div align="center"> 기본 파라메터</div></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap class="table_title">Value</td>
            <td height="26" nowrap class="table_title">Param</td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">아이디</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_useridparam" maxlength="16"  class="input" value="<%=v_useridparam%>" ></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">성명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_nameparam" maxlength="16"  class="input" value="<%=v_nameparam%>" ></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">주민등록번호</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_resnoparam" maxlength="16"   class="input" value="<%=v_resnoparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">사번</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_conoparam" maxlength="16"  class="input"  value="<%=v_conoparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">비밀번호</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_pwdparam" maxlength="16"  class="input"  value="<%=v_pwdparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">부서명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_deptnmparam" maxlength="16"  class="input"  value="<%=v_deptnmparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">직위코드</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_jikwiparam" maxlength="16"  class="input"  value="<%=v_jikwiparam%>"></td>
          </tr>

          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">직위명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_jikwinmparam" maxlength="16"  class="input"  value="<%=v_jikwinmparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">회사코드</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_compparam" maxlength="16"  class="input"  value="<%=v_compparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">회사명</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_companynmparam" maxlength="16"  class="input"  value="<%=v_companynmparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">과정코드</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_subjparam" maxlength="16"  class="input"  value="<%=v_subjparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">차수코드</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_subjseqparam" maxlength="16"  class="input"  value="<%=v_subjseqparam%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF">권한키</td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_gadminparam" maxlength="16"  class="input"  value="<%=v_gadminparam%>"></td>
          </tr>
        </table>
		        <br>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC" class="table_out">
          <tr align="center" bgcolor="#EAF4FD">
            <td height="26" colspan="2" nowrap class="tdtitle"><div align="center"> 추가 파라메터</div></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td width="388" nowrap class="table_title">Param</td>
            <td width="348" height="26" nowrap class="table_title">Value</td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param1" maxlength="16"  class="input"  value="<%=v_param1%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue1" maxlength="16"  class="input"  value="<%=v_paramvalue1%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param2" maxlength="16"  class="input"  value="<%=v_param2%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue2" maxlength="16"  class="input"  value="<%=v_paramvalue2%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param3" maxlength="16"  class="input"  value="<%=v_param3%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue3" maxlength="16"  class="input"  value="<%=v_paramvalue3%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param4" maxlength="16"  class="input"  value="<%=v_param4%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue4" maxlength="16"  class="input"  value="<%=v_paramvalue4%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param5" maxlength="16"  class="input"  value="<%=v_param5%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue5" maxlength="16"  class="input"  value="<%=v_paramvalue5%>"></td>
          </tr>
          <tr align="center" bgcolor="#EAF4FD">
            <td nowrap bgcolor="#FFFFFF"><input type="text" name="p_param6" maxlength="16"  class="input"  value="<%=v_param6%>"></td>
            <td height="26" nowrap bgcolor="#FFFFFF"><input type="text" name="p_paramvalue6" maxlength="16"  class="input"  value="<%=v_paramvalue6%>"></td>
          </tr>
        </table>
      <!----------------- 파라미터정보 끝----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
        <%
          if(v_gadmin.equals("A")){
            if(v_cpapproval.equals("B")){
            %>
              <td valign="middle" align="center"><a href="javascript:cpApproval('Y')"><img src="/images/admin/button/btn_approve.gif"  border="0"></a>&nbsp;</td>
		      <td valign="middle" align="center"><a href="javascript:cpApproval('N')"><img src="/images/admin/button/btn_reject.gif"  border="0"></a>&nbsp;</td>
		      <td valign="middle" align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;</td>
            <%
            }else if(v_cpapproval.equals("Y")){
            %>
              <td valign="middle" align="center"><a href="javascript:cpApproval('C')"><img src="/images/admin/button/btn_ap_cancel.gif" border="0"></a>&nbsp;</td>
              <td valign="middle" align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;</td>
            <%
            }else{
            %>
              <td valign="middle" align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;</td>
            <%
            }
          }else{
            if(v_cpapproval.equals("") || v_cpapproval.equals("N")){
        %>
           <td valign="middle" align="center"><a href="javascript:cpApproval('B')"><img src="/images/admin/button/btn_sangshin.gif"  border="0"></a>&nbsp;</td>
		   <td valign="middle" align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;</td>
        <%
            }else if(v_cpapproval.equals("C")){
        %>
           <td valign="middle" align="center"><a href="javascript:cpApproval('B')"><img src="/images/admin/button/btn_resang.gif" border="0"></a>&nbsp;</td>
           <td valign="middle" align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;</td>
        <%
            }
          }%>
            <td valign="middle" align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
        </td>
    </tr>
    <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %>
    </td>
  </tr>
</table>

</form>

</body>
</html>