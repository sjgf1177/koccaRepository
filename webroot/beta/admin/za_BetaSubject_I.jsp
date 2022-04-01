<%
//**********************************************************
//  1. 제      목: 과정등록 화면-베타테스트시스템
//  2. 프로그램명: za_Subject_I.jsp
//  3. 개      요: 과정등록 화면-베타테스트시스템
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 27
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.beta.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    String  ss_subjcourse   = box.getString("s_subjcourse");    //과정&코스
	
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
  document.form2.submit();
}
function goSubjectListPage() {
  document.form2.p_process.value  = 'listPage';
  document.form2.submit();
}

function chkData() {
  if (!number_chk_noalert(document.form2.p_framecnt.value)) {
    alert('총프레임수가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_edudays.value)) {
    alert('학습일차가 잘못입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form2.p_edulimit.value)) {
    alert('1일최대학습량이 잘못입력되었습니다.');
    return false;
  }
  
  if (blankCheck(document.form2.p_subjnm.value)) {
    alert('과정명을 입력하십시요.');
	document.form2.p_subjnm.focus();
    return false;
  }
  
  if (blankCheck(document.form2.p_betacompany.value)) {
    alert('회사명을 입력하십시오.');
    document.form2.p_betacompany.focus();
    return false;
  }
  
  if (blankCheck(document.form2.p_betacompanyno.value)) {
    alert('회사코드를 입력하십시오.');
    document.form2.p_betacompanyno.focus();
    return false;
  }
  
  if (blankCheck(document.form2.p_betauserid.value)) {
    alert('회사명을 입력하십시오.');
    document.form2.p_betauserid.focus();
    return false;
  }
  
  if (blankCheck(document.form2.p_betausernm.value)) {
    alert('회사코드를 입력하십시오.');
    document.form2.p_betausernm.focus();
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
// 멤버 검색 후 처리
function searchProducer() {
  var p_key1  = document.form2.p_producernm.value;
  var p_key2  = 'p_producer';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

// 콘텐츠제공 회사 검색 후 처리
function searchBetaCompany() {
  var p_key1 = document.form2.p_betacompany.value;
  var p_key2 = 'p_betacompany';
  var url = "/servlet/controller.library.SearchServlet?p_process=betacompany&p_companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

// 회사 선택후 처리
function receiveBetaComp(comp,compnm,userid,usernm,tmp1) {
    if (tmp1 == 'p_betacompany') {
        document.form2.p_betacompanyno.value = comp;
        document.form2.p_betacompany.value = compnm;
        document.form2.p_betauserid.value = userid;
        document.form2.p_betausernm.value = usernm;
    }
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

// 회사 검색 후 처리
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

<form name="form2" method="post" action="/servlet/controller.beta.BetaSubjectServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="s_subjcourse "  value="<%=ss_subjcourse %>">
    <input type="hidden" name="p_isonoff"     value="ON">
    <input type="hidden" name="p_specials"    value="">
    <input type="hidden" name="p_crdate"      value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
        <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/b_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <%=box.getString("p_warnmsg")%><br>
      <br>
	  	          <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title>사이버과정추가</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->

      <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="20%">과정명</td>
            <td class="table_02_2"  width="80%"><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value=""></td>
          </tr>
          <tr>
            <td class="table_title">콘텐츠타입</td>
            <td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect ("0007", "B", 1, "p_contenttype", "", "", 0)%> </td>
          </tr>
          <tr> 
            <td class="table_title">콘텐츠경로</td>
            <td class="table_02_2"><input type="text" class="input" name="p_dir" size="38" maxlength="100" value=""></td>            
          </tr>
          <tr>
            <td class="table_title">VOD경로</td>
            <td class="table_02_2"><input type="text" class="input" name="p_vodurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td class="table_title">학습URL</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_eduurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td class="table_title">맛보기URL</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_previewurl" size="38" maxlength="100" value=""></td>
          </tr>
          <tr> 
            <td class="table_title">1일최대학습량</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_edulimit" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td class="table_title">총프레임수</td>
            <td class="table_02_2"><input type="text" class="input" name="p_framecnt" size="10" maxlength="10" value="0"></td>
          </tr>
          <tr> 
            <td class="table_title">학습일차</td>
            <td class="table_02_2" width="27%"><input type="text" class="input" name="p_edudays" size="10" maxlength="5" value="0"></td>
          </tr>
          <tr> 
            <td height="31" class="table_title">사용여부</td>
            <td class="table_02_2" width="27%" > <select name="p_isuse"  class="inputpsearch">
                <option value='Y' <% if(box.getString("p_isuse").equals("Y") ||box.getString("p_isuse").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isuse").equals("N")) out.println(" selected");%>>N</option>
              </select> 
          </tr>
          <!--tr> 
            <td class="table_title">콘텐츠담당</td>
            <td class="table_02_2" width="27%"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="160"> 이&nbsp;&nbsp;&nbsp;름:&nbsp; <input name="p_cuseridnm" type="text" class="input" size="14" maxlength=13 value=""> 
                    <br>
                    아이디:&nbsp; <input name="p_cuserid" type="text" class="input" size="14" maxlength=13 value="" readonly> 
                  </td>
                  <td><a href="javascript:searchCuser()"><img src="/images/admin/course/search2_butt.gif" width="37" height="18" border="0"></a></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr> 
            <td class="table_title">제공회사</td>
            <td class="table_02_2" width="27%">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td width="160"> 이름 : 
                        <input type="text" class="input" name="p_producernm" size="14" maxlength="13" value=""> 
                        <br>
                        코드 : 
                        <input type="text" class="input" name="p_producer" size="14" maxlength="13" value="" readonly> 
                        </td>
                        <td><a href="javascript:searchProducer()"><img src="/images/admin/course/search2_butt.gif" width="37" height="18" border="0"></a></td>
                    </tr>
                </table>
            </td>
          </tr-->
          <!--------------콘텐츠 제공회사/담당자 조회/선택------------------>
          <tr> 
            <td class="table_title">제공회사</td>
            <td class="table_02_2" width="27%">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                        <td width="200"> 회사 : 
                        <input type="text" class="input" name="p_betacompany" size="14" maxlength="13" value=""> 
                        <br>
                        코드 : 
                        <input type="text" class="input" name="p_betacompanyno" size="14" maxlength="13" value="" readonly>
                        <br>
                        담당자 ID :
                        <input type="text" class="input" name="p_betauserid" size="10" maxlength="10" value="" readonly>
                        <br>
                        담당자성명 :
                        <input type="text" class="input" name="p_betausernm" size="10" maxlength="10" value="" readonly>
                        </td>
                        <td><a href="javascript:searchBetaCompany()"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a></td>
                    </tr>
                </table>
            </td>
          </tr>
          <!--------------콘텐츠 제공회사/담당자 선택 끝-------------------->
          <tr> 
            <td class="table_title">제작일자</td>
            <td class="table_02_2" width="27%" >
            <input name="p_crdate_view" id="p_crdate_view" type="text" class="datepicker_input1" size="10">
            </td>
          </tr>
        </table>
      <br>
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertSubject()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
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
