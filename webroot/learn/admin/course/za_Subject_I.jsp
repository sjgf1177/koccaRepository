<%
//**********************************************************
//  1. 제      목: 과정등록 화면
//  2. 프로그램명: za_Subject_I.jsp
//  3. 개      요: 과정등록 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 12. 11
//  7. 수      정:
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
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_upperclass = box.getStringDefault("s_upperclass", "ALL");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    //DEFINED in relation to select END

    String  v_sgkind        = box.getStringDefault("p_proposetype","");    //수강신청방식
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

//등록 시작
function InsertSubject() {
    if(!chkData()) {
      return;
    }

    wf_page = "InsertSubject";
    if(wf_flag == "off"){
        if ( confirm("과정을 등록하시겠습니까?") ) {
            submitWebFilter('form2');
        } else {
            return;
        }
    }else{
        document.form2.s_action.value = "go";
        document.form2.p_process.value = 'insert';
        document.form2.p_specials.value = document.form2.p_new.value + document.form2.p_hit.value + document.form2.p_recom.value; // 신규 / 인기 / 추천
        document.form2.submit();
    }
}
//등록 끝

function goSubjectListPage() {
    document.form2.s_action.value = "go";
    document.form2.p_process.value  = 'listPage';
    document.form2.submit();
}


//공통 체크 시작

function whenUsebook() {
    //교재여부 체크
    if (document.form2.p_usebook.value=="Y") {
        document.form2.p_bookname.disabled = false;
        document.form2.p_bookprice.disabled = false;
    } else {
        document.form2.p_bookname.disabled = true;
        document.form2.p_bookname.value = "";
        document.form2.p_bookprice.disabled = true;
        document.form2.p_bookprice.value = 0;
    }
}

function whenIsgoyong() {
    //고용보험 사용여부체크
    if (document.form2.p_isgoyong.value=="Y") {
        document.form2.p_goyongprice_major.disabled = false;
//        document.form2.p_goyongprice_minor.disabled = false;
    } else {
        document.form2.p_goyongprice_major.disabled = true;
//        document.form2.p_goyongprice_minor.disabled = true;
        document.form2.p_goyongprice_major.value = 0;
//        document.form2.p_goyongprice_minor.value = 0;
    }
}

function whenIsuse() {
    //과정사용여부
    if (document.form2.p_isuse.value=="Y") {
        document.form2.p_isvisible.disabled = false;
    } else {
        document.form2.p_isvisible.disabled = true;
        document.form2.p_isvisible.value = "N";
    }
}

function whenIsordinary() {
    //상시,수시 선택체크
    if (document.form2.p_isordinary.value=="Y") {
        document.form2.p_sphere1.disabled = false;
        document.form2.p_sphere2.disabled = true;
        document.form2.p_sphere2.value = "ALL";
    } else {
        document.form2.p_sphere1.disabled = true;
        document.form2.p_sphere1.value = "ALL";
        document.form2.p_sphere2.disabled = false;
    }
}


/**
 * 자료 등록전 유효성 여부를 확인한다.
 */
function chkData() {
    ff = document.form2;

    if (document.form2.p_upperclass.value=='ALL' || document.form2.p_upperclass.value=='') {
        alert('대분류를 선택하세요.');
        return false;
    }

    if (document.form2.p_middleclass.value=='ALL' || document.form2.p_middleclass.value=='') {
        alert('중분류를 선택하세요.');
        return false;
    }

    if (document.form2.p_lowerclass.value=='ALL' || document.form2.p_middleclass.value=='') {
        document.form2.p_lowerclass.value='000';
    }
    /*
      if (document.form2.p_lowerclass.value=='ALL' || document.form2.p_lowerclass.value=='') {
        alert('소분류를 선택하세요.');
        return false;
      }

      //소유회사 체크
      if (document.form2.p_owner.value=="") {
        alert("콘텐츠 소유회사를 선택하세요.");
        return;
      }
    */
      //if (!number_chk_noalert(document.form2.p_score.value)) {
      //  alert('학점배점이 잘못입력되었습니다.');
      //  return false;
      //}
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

    if (!number_chk_noalert(document.form2.p_edutimes.value)) {
        alert('학습시간이 잘못입력되었습니다.');
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


    if (!number_chk_noalert(document.form2.p_bookprice.value)) {
        alert('교재비가 잘못입력되었습니다.');
        return false;
    }

    if (!number_chk_noalert(document.form2.p_goyongprice_major.value)) {
        alert('환급액이 잘못입력되었습니다.');
        return false;
    }

    //  if (!number_chk_noalert(document.form2.p_goyongprice_minor.value)) {
    //    alert('환급액(중소기업)이 잘못입력되었습니다.');
    //    return false;
    //  }

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
        alert('가중치-참여도(%)가 잘못입력되었습니다.');
        return false;
    }

    if (!number_chk_noalert(document.form2.p_wetc2.value)) {
        alert('가중치-기타(%)가 잘못입력되었습니다.');
        return false;
    }

    var weight = parseFloat(document.form2.p_wstep.value,10) + parseFloat(document.form2.p_wmtest.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_whtest.value,10) + parseFloat(document.form2.p_wreport.value,10) + parseFloat(document.form2.p_wact.value,10) + parseFloat(document.form2.p_wetc1.value,10) + parseFloat(document.form2.p_wetc2.value,10);

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

    if (document.form2.p_isordinary.value == "Y") {         // 상시일경우
        document.form2.p_sphere.value = document.form2.p_sphere1.value;
    } else {                                                // 수시일경우
        document.form2.p_sphere.value = document.form2.p_sphere2.value;
    }

    return true;
}

//콘텐츠담당 검색
function searchCuser() {
  var p_gubun = 'name';
  var p_key1  = document.form2.p_cuseridnm.value;
  var p_key2  = 'p_cuser';
  var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

function performBuyBagPreviewPage() {
      var url = "/servlet/controller.course.SubjectServlet?p_process=buyBagPreviewPage&p_needinput="+$("#p_needinput").val();
      open_window("",url,"0","0","720","400","","","","yes");
}

// 멤버 검색 후 처리
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
  if (tmp1 == 'p_cuser') {
    document.form2.p_cuseridnm.value = name;
    document.form2.p_cuserid.value   = userid;
  } else if (tmp1 == 'p_muser') {
    document.form2.p_museridnm.value = name;
    document.form2.p_muserid.value   = userid;
    document.form2.p_musertel.value   = tmp3;
  }
}


//담당교수 검색
function searchMuser() {
  var p_gubun = 'name';
  var p_key1  = '';//document.form2.p_museridnm.value;
  var p_key2  = '1';
  var url = "/servlet/controller.library.SearchServlet?p_process=tutor&p_gubun="+ p_gubun +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 담당교수 검색 후 처리
function receiveTutor(userid, name, resno, email, phone, tmp1, tmp2, tmp3, tmp4, tmp5){
    addMuser(userid, name);
//  document.form2.p_museridnm.value = name;
//  document.form2.p_muserid.value   = userid;
//  document.form2.p_musertel.value   = phone;
}

//제작회사 검색
function searchProducer() {
  var p_key1  = document.form2.p_producernm.value;
  var p_key2  = '';
//  var p_key2  = 'p_producer';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}

//소유회사 검색
function searchOwner() {
  var p_key1  = document.form2.p_ownernm.value;
  var p_key2  = 'p_owner';
  var url = "/servlet/controller.library.SearchServlet?p_process=grpcomp&p_gubun=companynm&p_key1=&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
// 회사 검색 후 처리
function receiveGrpComp(comp , compnm, comptype, groupnm, companynm, tmp1, tmp2, tmp3) {
//  if (tmp1 == 'p_producer') {
  if (tmp1 == '') {
    document.form2.p_producernm.value = compnm;
    document.form2.p_producer.value   = comp;
  } else if (tmp1 == 'p_owner') {
    document.form2.p_ownernm.value = compnm;
    document.form2.p_owner.value   = comp;
  }
}

// 대중소분류 선택
function whenSelection() {
    document.form2.p_process.value = 'insertPage';
    //대분류 선택시 중분류를 ALL로 초기화
    document.form2.s_middleclass.value = '';
    document.form2.submit();
}

    /**
     * 모바일 사용여부 값에 따라 모바일 URL 값을 설정함.
     */
    function fnSetMobileUrlStatus( mobileUseYn ) {
        if( mobileUseYn == "Y" ) {
            $("#oMobilePreurl").attr("disabled", false);
        } else {
            $("#oMobilePreurl").attr("disabled", true);
        }
    }
    $(function() {
    	$("#oUpperclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetMiddleClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oMiddleclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetLowerClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });
    });

    /**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"\">== 전체 ==</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }


    /**
     * 검색조건에서 중분류 선택시 callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form id="oSubjRegForm" name="form2" enctype="multipart/form-data" method="POST" action="/servlet/controller.course.SubjectServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="s_grcode"        value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"         value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
    <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
    <input type="hidden" name="s_lowerclass"    value="<%=ss_lowerclass%>"><!--in relation to select-->
    <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="s_action">

    <input type="hidden" name="p_isonoff"     value="ON">
    <input type="hidden" name="p_specials"    value="">
    <input type="hidden" name="p_crdate"      value="">

    <input type="hidden" name = "p_isoutsourcing" value="N">
    <input type="hidden" name="p_edulimit" value="0">
    <input type="hidden" name="p_musertel" value="">
    <input type="hidden" name="s_area" value="ALL" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/c_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <%=box.getString("p_warnmsg")%><br>
      <br>
      <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out" border="0">
          <tr>
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" rowspan="29"><b>과정정보</b></td>
            <td colspan="7" class="table_01"><font color="red">(*)</font> 표시는 필수 입력입니다.</td>
          </tr>
          <tr>
            <td class="table_01">과정코드</td>
            <td class="table_02_2" ><font color="red">자동생성</font>
                 <kocca_select:select name="p_area" sqlNum="0002"  param="101" type="2.2"
                    onChange="" attr=" " selectedValue="G0" isLoad="true" all="none" />
            </td>
            <td  rowspan="5" class="table_title"><b>콘텐츠정보</b></td>
            <td colspan="2" class="table_01">콘텐츠등급</td>
            <td class="table_02_2" ><%=CodeConfigBean.getCodeGubunSelect ("0056", "", 1, "p_contentgrade", "A", " tabindex=1", 0)%></td>
          </tr>
          <tr>
            <td class="table_01">콘텐츠타입<font color="red">(*)</font></td>
            <td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect ("0007", "", 1, "p_contenttype", "N", " tabindex=1", 0)%> </td>
            <td colspan="2" rowspan="2" class="table_01">제작회사</td>
            <td class="table_02_2" >이름 :
              <input type="text" class="input" name="p_producernm" size="14" maxlength="13" value="" tabindex=41>
              &nbsp; <a href="javascript:searchProducer()"><img src="/images/admin/button/b_resarchbutt.gif" align="absmiddle" border="0" tabindex=42></a></td>
          </tr>
          <tr>
            <td  class="table_01">과정명<font color="red">(*)</font></td>
            <td class="table_02_2"><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value="" tabindex=""></td>
            <td class="table_02_2">코드 :
              <input type="text" class="input" name="p_producer" size="14" maxlength="13" value="" readonly></td>
          </tr>
          <tr>
            <td class="table_01" rowspan="2">과정분류<font color="red">(*)</font></td>
            <td height="50" class="table_02_2" rowspan="2">
            <%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
	대분류 
	<kocca:selectBox name="p_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" /><br/>
	중분류 
<%
    if ( !ss_middleclass.equals("") ) {
%>
									<kocca:selectBox  name="p_middleclass" id="oMiddleclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" /><br/>
<%
    } else {
%>
                                    <select name="p_middleclass" id="oMiddleclass">
                                    	<option value="">== 전체 ==</option>
                                    </select><br/>
<%
    }
%>	
	소분류
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                    <kocca:selectBox name="p_lowerclass" id="oLowerclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" /><br/>
<%
    } else {
%>
                                    <select name="p_lowerclass" id="oLowerclass">
                                        <option value="">== 전체 ==</option>
                                    </select>
<%
    }
%>	
	
              <!--수강신청방식 Default 일반신청(1)-->
              <input type="hidden" name="p_proposetype" value="1">
            <td colspan="2" class="table_01">제작일자</td>
            <td class="table_02_2" ><input name="p_crdate_view" id="p_crdate_view" type="text" class="datepicker_input1" size="10" tabindex=45></td>
          </tr>
          <tr>
              <td colspan="2" class="table_01">러닝타임</td>
              <td class="table_02_2" ><input name="p_running_time" id="p_running_time" type="text" class="datepicker_input1" size="10" tabindex=46></td>
          </tr>
          <tr>
            <td class="table_01">과정유형</td>
            <td height="50" class="table_02_2">
                 <%-- <kocca_select:select name="p_subjtype" sqlNum="0001"  param="0076" selectedValue="" isLoad="true" all="false" isCheckBox="N" /> --%>
                 <kocca:selectBox name="p_subjtype" id="p_subjtype" optionTitle="== 전체 ==" type="sqlID" sqlID="code.list.0001" param="0076" selectedValue="" isLoad="true" /><br/>
              <td class="table_title"><b>이달의<br>추천과정</b></td>
              <td colspan="2" class="table_01">추천수</td>
              <td class="table_02_2" ><input type="text" name="hitnumber" value="0" size="10"></td>
          </tr>
          <tr>
            <td class="table_01">기본/실무</td>
            <td height="50" class="table_02_2" colspan="5">
                <select id="oBizType" name="bizType">
                    <option value="">-- 선택 --</option>
                    <option value="F01">기본</option>
                    <option value="F02">실무</option>
                </select>
            </td>
          </tr>
          <tr>
            <td class="table_01">자동승인여부</td>
            <td height="50" class="table_02_2" colspan="5">
                <input name="p_autoconfirm" type="radio" value="Y" class="input">예 <input name="p_autoconfirm" type="radio" class="input" value="N" checked>아니요
                &nbsp;&nbsp;&nbsp;&nbsp;(선택시 수강신청과 동시에 승인)
            </td>
          </tr>
          <tr>
            <td class="table_01">수료증출력가능여부</td>
            <td height="50" class="table_02_2" colspan="5">
              <select name="p_suryoyn"  class="inputpsearch" tabindex=17>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select>
                <input type="text" class="input" name="p_suryotitle" size="38" maxlength="100" value="" tabindex="">
            </td>
          </tr>
          <tr>
            <td class="table_01">연관검색어</td>
            <td height="50" class="table_02_2" colspan="5">
                <input type="text" class="input" name="p_search_nm" size="60" maxlength="100" value="" tabindex="" ></td>
            </td>
          </tr>
          <tr>
            <td  class="table_01">과정특성</td>
            <td class="table_02_2"> 신규
              <select name="p_new"  class="inputpsearch" tabindex=17>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select>
              HIT
              <select name="p_hit"  class="inputpsearch" tabindex=15>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select>
              추천
              <select name="p_recom"  class="inputpsearch" tabindex=18>
                <option value='Y'>Y</option>
                <option value='N' selected>N</option>
              </select>
            </td>
            <td rowspan="4" class="table_title"><b>학습정보</b></td>
            <td colspan="2" class="table_01">학습방법(WBT%)</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_ratewbt" size="10" maxlength="3" value="0" tabindex=47></td>
          </tr>
          <tr>
            <td  rowspan="2" class="table_01"><!--운영담당-->담당교수<font color="red">(*)</font></td>
            <td height="34" class="table_02_2" >
                <a href="javascript:searchMuser()" tabindex="21"><img src="/images/admin/button/b_resarchbutt.gif" align="absmiddle" border="0"></a>
            </td>
            <td colspan="2" class="table_01">학습방법(VOD%)</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_ratevod" size="10" maxlength="3" value="0" tabindex=48></td>
          </tr>
          <tr>
            <td height="34" class="table_02_2" >
                    <div id="muserList"></div>
<script>
<!--
//muserList.1
    $(document).ready(function()    {
        listMuser();
    });
    function listMuser() {
        $.post("/servlet/controller.common.TagServlet"
                , {sqlNum:"muserList.1", param:"",type:4,objNm:"muser"}
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
            <td colspan="2" rowspan="2" class="table_01">복습가능여부</td>
            <td class="table_02_2" rowspan="2"><select name="p_isablereview"  class="inputpsearch" tabindex=51>
                <option value='Y' <% if(box.getString("p_isablereview").equals("Y") ||box.getString("p_isablereview").equals("")) out.println(" selected");%>>Y</option>
                <option value='N' <% if(box.getString("p_isablereview").equals("N")) out.println(" selected");%>>N</option>
              </select>
              / 복습기간 : <input type="text" class="input" name="p_reviewdays" size="3" maxlength="3" value="0" tabindex=51>
                           <select name="p_reviewtype"  class="inputpsearch" tabindex=51>
                                <option value="D">일</option>
                                <option value="W">주</option>
                                <option value="M">개월</option>
                                <option value="Y">년</option>
                           </select>
            </td>
          </tr>
          <tr>
              <td height="31" class="table_01">강사정보</td>
              <td class="table_02_2" >
                  <input type="text" class="input" name="p_tutor2" size="50" maxlength="200" value="" tabindex="" ></td>
              </td>
          </tr>
          <tr>
            <td height="31" class="table_01">과정사용여부</td>
            <td class="table_02_2" > <select name="p_isuse"  class="inputpsearch" onChange="whenIsuse()" tabindex=26>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select>
            </td>
            <td rowspan="14" class="table_title"><b>점수정보</b></td>
            <td rowspan="6" class="table_01">이<br>수<br>기<br>준 </td>
            <td  class="table_01">총 점
              <input type="hidden" name="p_point" value="100"></td>
            <td class="table_02_2" > <input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="0" tabindex=52></td>
          </tr>
          <tr>
            <td class="table_01">학습자에게 보여주기</td>
            <td class="table_02_2" ><select name="p_isvisible"  class="inputpsearch" tabindex=27>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select></td>
            <td class="table_01">진도율</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="0" tabindex=53></td>
          </tr>
          <tr>
            <td class="table_01">과정소개사용여부</td>
            <td class="table_02_2">
              <select name="p_isintroduction"  class="inputpsearch" tabindex=28>
                <option value='Y' selected>Y</option>
                <option value='N'>N</option>
              </select>
            </td>
            <td class="table_01">중간평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="0" tabindex=54></td>
          </tr>
          <tr>
            <td  class="table_01">총프레임수</td>
            <td class="table_02_2"><input type="text" class="input" name="p_framecnt" size="10" maxlength="10" value="0" tabindex=30></td>
            <td class="table_01">최종평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradftest" size="10" maxlength="5" value="0" tabindex=55></td>
          </tr>
          <tr>
            <td class="table_01">수강료</td>
            <td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="0" tabindex=31>
              원</td>
            <td class="table_01">형성평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradhtest" size="10" maxlength="5" value="0" tabindex=56></td>
          </tr>
          <tr>
            <td class="table_01">학습일차</td>
            <td class="table_02_2"><input type="text" class="input" name="p_edudays" size="10" maxlength="5" value="0" tabindex=32></td>
            <td class="table_01">리포트</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="0" tabindex=57></td>
          </tr>
          <tr>
            <td  class="table_01">학습기간</td>
            <td class="table_02_2"><input type="text" class="input" name="p_eduperiod" size="5" maxlength="2" value="0" tabindex=32> 주</td>
            <td rowspan="8" class="table_01">가<br>
              중<br>
              치<br>
              (%)<br> <font color="red">(*)</font></td>

            <td class="table_01">진도율</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="0" tabindex=58></td>
          </tr>
          <tr>
            <td class="table_01">학습시간</td>
            <td class="table_02_2"><input type="text" class="input" name="p_edutimes" size="10" maxlength="5" value="0" tabindex=33>
              시간</td>
            <td class="table_01">중간평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wmtest" size="10" maxlength="5" value="0" tabindex=59></td>
          </tr>
          <tr>
            <td  class="table_01">정원</td>
            <td class="table_02_2"><input type="text" class="input" name="p_studentlimit" size="10" maxlength="5" value="0" tabindex=34>
              명</td>
            <td class="table_01">최종평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="0" tabindex=60></td>
          </tr>
          <tr>
            <td class="table_01">교재여부</td>
            <td class="table_02_2" ><select name="p_usebook"  class="inputpsearch" onChange="whenUsebook()" tabindex=35>
                <option value='Y' >Y</option>
                <option value='N' selected>N</option>
              </select></td>
            <td class="table_01">형성평가</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_whtest" size="10" maxlength="5" value="0" tabindex=61></td>
          </tr>
          <tr>
            <td height="36" class="table_01">교재명</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_bookname" size="38" maxlength="100" value="" disabled tabindex=36></td>
            <td class="table_01">리포트</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="0" tabindex=62>
              <input type="hidden" name="p_wact" value="0"></td>
          </tr>
          <tr>
            <td height="36" class="table_01">교재비</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_bookprice" size="10" maxlength="5" value="0" disabled tabindex=38>
              원</td>
            <td class="table_01">참여도</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="0" tabindex=63></td>
          </tr>
          <tr>
            <td class="table_01">고용보험여부</td>
            <td class="table_02_2">
              <select name="p_isgoyong"  class="inputpsearch" onChange="whenIsgoyong()" tabindex=28>
                <option value='Y' >Y</option>
                <option value='N' selected>N</option>
              </select>
            </td>
            <td class="table_01">기타</td>
            <td class="table_02_2" ><input type="text" class="input" name="p_wetc2" size="10" maxlength="5" value="0" tabindex=64></td>
          </tr>
          <tr>
            <td class="table_01">환급금액</td>
            <td class="table_02_2" >
              <input type="text" class="input" name="p_goyongprice_major" size="15" maxlength="10" value="" disabled tabindex=36> 원<br>
              <!--<input type="text" class="input" name="p_goyongprice_minor" size="15" maxlength="10" value="" disabled tabindex=36> 원-->
            </td>
            <td colspan="2" class="table_01">가중치(%)의 합은 100이어야 합니다.</td>
          </tr>
        <tr>
            <td class="table_title" rowspan="3"><b></b></td>
            <td class="table_01">상시/수시</td>
            <td class="table_02_2" colspan="5">
                <select name="p_isordinary" onChange="whenIsordinary()" >
                    <option value='N'>수시</option>
                    <option value='Y'>상시</option>
                </select>&nbsp;&nbsp;&nbsp;&nbsp; <br>
                분야(상시) <%=CodeConfigBean.getCodeGubunSelect ("0067", "", 1, "p_sphere1", "", "disabled", 2)%><br>
                분야(수시) <%=CodeConfigBean.getCodeGubunSelect ("0068", "", 1, "p_sphere2", "", "", 2)%>
                <input type="hidden" name="p_sphere" value="">
            </td>
        </tr>
        <tr>
            <td class="table_01">워크숍</td>
            <td class="table_02_2" colspan="5">
              <select name="p_isworkshop">
                <option value='N'>N</option>
                <option value='Y'>Y</option>
              </select>
            </td>
        </tr>
        <tr>
            <td class="table_01">학점인증과정</td>
            <td class="table_02_2" colspan="5">
              <select name="p_isunit">
                <option value='N'>N</option>
                <option value='Y'>Y</option>
              </select>  학점 : <input type="text" class="input" name="p_degree" id="p_degree" size="4" maxlength="4" value="0"> 점
            </td>
        </tr>

        <tr>
            <td class="table_title" rowspan="5"><b>URL 정보</b></td>
            <td class="table_01">학습 URL</td>
            <td class="table_02_2" colspan="5">
                <input type="text" class="input" name="p_eduurl" size="100" maxlength="150" value=""  tabindex=36>
            </td>
        </tr>
        <tr>
            <td class="table_01">미리보기 URL</td>
            <td class="table_02_2" colspan="5">
                <input type="text" class="input" name="p_preurl" size="100" maxlength="150" value=""  tabindex=36>
                <br>
                WIDTH:<input type="text" class="input" name="p_prewidth" size="8" maxlength="4" value="1024"> &nbsp;
                HEIGHT:<input type="text" class="input" name="p_preheight" size="8" maxlength="4" value="768">
            </td>
        </tr>
        <tr>
            <td class="table_01">학습목차 URL</td>
            <td class="table_02_2" colspan="5">
                <input type="text" class="input" name="p_conturl" size="100" maxlength="150" value=""  tabindex=36>
            </td>
        </tr>
        <tr>
            <td class="table_01">모바일 사용여부</td>
            <td class="table_02_2" colspan="5">
                <select id="oMobileUseYn" name="mobileUseYn" onchange="fnSetMobileUrlStatus(this.value);">
                    <option value="Y">예</option>
                    <option value="N">아니오</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="table_01">모바일 미리보기 URL</td>
            <td class="table_02_2" colspan="5">
                <input type="text" class="input" id="oMobilePreurl" name="p_mobile_preurl" size="100" maxlength="150" value=""  tabindex=36>
            </td>
          </tr>
        </table>
        <br>
      <!----------------- 과정맛보기 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="16%" class="table_title"><b>수강신청 입력정보</b></td>
          <td width="81%" class="table_02_2">
                <table><tr><td width="80%">
                <input type='hidden' id='p_needinput' name='p_needinput' value="1000000"/>
                 <kocca_select:select name="needinput" sqlNum="0001"  param="0078" onChange="changeNeedInput" type="2"
                    selectedValue="1000000" isLoad="true" all="true" isCheckBox="Y" />
                <script>
                    function changeNeedInput() {
                        $("#p_needinput").val('');
                        for (needInput_i = 0; needInput_i < document.all.needinput.length; needInput_i++) {
                            if (document.all.needinput[needInput_i].checked) $("#p_needinput").val($("#p_needinput").val()+'1');
                            else $("#p_needinput").val($("#p_needinput").val()+'0');
                        }
                    }
                </script>
                    </td><td width="5%">&nbsp;</td>
                    <td><a href="javascript:performBuyBagPreviewPage();"><img style="border:none" src="/images/admin/button/btn_preview.gif" alt="입력양식 미리보기"/></a></td>
                    </tr>
                </table>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>소개이미지</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_introducefile" type="file" class="input" size="118" maxlength="100" tabindex=65>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>파일</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_informationfile" type="file" class="input" size="118" maxlength="100" tabindex=65>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>메인화면이미지</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_submainfile" type="file" class="input" size="118" maxlength="100" tabindex=65>
          </td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>교육대상</b></td>
          <td width="81%" class="table_02_2">
            <textarea name="p_edumans"  id="p_edumans" onkeyup="getObjectLength('p_edumans')" onchange="getObjectLength('p_edumans', 4000)" style="width:88%" rows="3" tabindex=65></textarea> <font id="p_edumans_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육목적</b></td>
          <td class="table_02_2">
            <textarea name="p_intro" id="p_intro" onkeyup="getObjectLength('p_intro')" onchange="getObjectLength('p_intro', 4000)" style="width:88%" rows="3" tabindex=66></textarea> <font id="p_intro_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육내용</b></td>
          <td class="table_02_2">
            <textarea name="p_explain" id="p_explain" onkeyup="getObjectLength('p_explain')" onchange="getObjectLength('p_explain', 4000)" style="width:88%" rows="5" tabindex=67></textarea> <font id="p_explain_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>강의교재 및 참고문헌</b></td>
          <td class="table_02_2">
            <textarea name="p_memo" id="p_memo" onkeyup="getObjectLength('p_memo')" onchange="getObjectLength('p_memo', 4000)" style="width:88%" rows="5" tabindex=67></textarea> <font id="p_memo_length" FONT-FAMILY="Tahoma" font-size="10px" color="#CB1F02"></font>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b><label for="p_subjsummary">과정개요</label></b></td>
          <td class="table_02_2">
            <input type="text" name="p_subjsummary" id="p_subjsummary" value="" maxlength="100" size="100" class="input" />
          </td>
        </tr>
        <tr>
          <td class="table_title"><b><label for="p_subjtags">TAGS</label></b></td>
          <td class="table_02_2">
            <input type="text" name="p_subjtags" id="p_subjtags" value="" maxlength="80" size="100" class="input" />
          </td>
        </tr>
      </table>
      <!----------------- 과정맛보기 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertSubject()" tabindex="68"><img src="/images/admin/button/btn_copen.gif" border="0"></a></td>
          <td width="8"></td>
          <td align="center"><a href="javascript:goSubjectListPage()" tabindex="69"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
<!--  웹필터 수정 -->
<%@ include file="/webfilter/webfilter/inc/initCheckWebfilter.jsp"%>
<!--  웹필터 수정 -->
</body>
</html>
