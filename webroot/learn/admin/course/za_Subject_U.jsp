<%
//**********************************************************
//  1. 제      목: 과정관리
//  2. 프로그램명: za_Subject_U.jsp
//  3. 개      요: 과정수정 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 11. 11
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
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");


    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수


    //DEFINED in relation to select END


    String v_pagegubun      = box.getString("p_pagegubun"); //과정승인Page에서 넘어온경우 "Approval" 값을 가짐


    SubjectData data = (SubjectData)request.getAttribute("SubjectData");
%>
<html>
<head>
	<title>과정수정</title>
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

	function UpdateSubject() {
	    if(!chkData()) {
	      return;
	    }
	
	    if ( confirm("과정정보를 수정하시겠습니까?") ) {
	        document.form2.s_action.value = "go";
	        document.form2.p_process.value = 'update';
	        document.form2.p_specials.value = document.form2.p_new.value + document.form2.p_hit.value + document.form2.p_recom.value;
	        document.form2.submit();
	    } else {
	        return;
	    }
	}
	
	function DeleteSubject() {
	  if (confirm("과정정보를 삭제 하시겠습니까?")) {
	    document.form2.p_process.value = 'delete';
	    document.form2.submit();
	  }
	}

	//공통 체크 시작
	function whenIsessential() {
	    //승진과정여부
	    if (document.form2.p_subjtype.checked) {
	        document.form2.p_isessential.disabled = false;
	    } else {
	        document.form2.p_isessential.value = "0";
	        document.form2.p_isessential.disabled = true;
	    }
	}

	function whenRndjijung() {
	    if (document.form2.p_rndjijung.checked) {
	        //지정과정인 경우
	        document.form2.p_rndcreditadd.disabled = false;
	        document.form2.p_rndcreditdeduct.disabled = false;
	        document.form2.p_rndcreditreq.disabled = true;
	        document.form2.p_rndcreditchoice.disabled = true;
	        document.form2.p_rndcreditreq.value = 0.0;
	        document.form2.p_rndcreditchoice.value = 0.0;
	    } else {
	        //지정과정이 아닌 경우
	        document.form2.p_rndcreditadd.value = 0.0;
	        document.form2.p_rndcreditdeduct.value = 0.0;
	        document.form2.p_rndcreditadd.disabled = true;
	        document.form2.p_rndcreditdeduct.disabled = true;
	        document.form2.p_rndcreditreq.disabled = false;
	        document.form2.p_rndcreditchoice.disabled = false;
	    }
	}
	
	function performBuyBagPreviewPage() {
	      var url = "/servlet/controller.course.SubjectServlet?p_process=buyBagPreviewPage&p_needinput="+$("#p_needinput").val();
	      open_window("",url,"0","0","720","400","","","","yes");
	}

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
	    } else {
	        document.form2.p_goyongprice_major.disabled = true;
	        document.form2.p_goyongprice_major.value = 0;
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
	//공통체크 끝

	function ApprovalSubject() {
		if (confirm("과정정보를 승인처리 하시겠습니까?")) {
			document.form2.p_process.value = 'approvalSubject';
			document.form2.submit();
		}
	}

	function goSubjectListPage() {
 		<% if (v_pagegubun.equals("Approval")) { %>
		document.form2.p_process.value  = 'approvalListPage';
 		<% } else { %>
		document.form2.p_process.value  = 'listPage';
		<% } %>
		document.form2.s_action.value = "go";
		document.form2.submit();
	}
	
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
		if (!number_chk_noalert(ff.p_gradexam.value)) {
			alert('이수기준-평가가 잘못입력되었습니다.');
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
			alert('이수기준-평가가 100 이상입니다.');
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
			alert('환급액(대기업)이 잘못입력되었습니다.');
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

		var weight =  parseFloat(document.form2.p_wstep.value,10) +  parseFloat(document.form2.p_wmtest.value,10) + parseFloat(document.form2.p_wftest.value,10) + parseFloat(document.form2.p_whtest.value,10) + parseFloat(document.form2.p_wreport.value,10) +  parseFloat(document.form2.p_wact.value,10) + parseFloat(document.form2.p_wetc1.value,10) + parseFloat(document.form2.p_wetc2.value,10);
		if (weight < 100) {
			alert('가중치가 100% 보다 적습니다');
			return false;
		} else if (weight > 100) {
			alert('가중치가 100% 보다 많습니다.');
			return false;
		}
		if (blankCheck(document.form2.p_subjnm.value)) {
			alert('과정명을 입력하십시요.');
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
	
	function searchCuser() {
		var p_gubun = 'name';
		var p_key1  = document.form2.p_cuseridnm.value;
		var p_key2  = 'p_cuser';
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
	}

	function searchProducer() {
		var p_key1  = document.form2.p_producernm.value;
		var p_key2  = '';
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
		if (tmp1 == '') {
			document.form2.p_producernm.value = compnm;
			document.form2.p_producer.value   = comp;
		} else if (tmp1 == 'p_owner') {
			document.form2.p_ownernm.value = compnm;
			document.form2.p_owner.value   = comp;
		}
	}

    /**
     * 모바일 사용여부 값에 따라 모바일 URL 값을 설정함.
     */
    function fnSetMobileUrlStatus( mobileUseYn ) {
        if( mobileUseYn == "Y" ) {
            $("#oMobilePreurl").attr("readonly", false).attr("style", "color:#000000;");
        } else {
            $("#oMobilePreurl").attr("readonly", true).attr("style", "color:#efefef;");
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
	</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form2" enctype = "multipart/form-data" method = "POST" action="/servlet/controller.course.SubjectServlet">
	    <input type="hidden" name="p_process"      value="">
	    <input type="hidden" name="s_grcode"        value="<%=ss_grcode%>">     <!--in relation to select-->
	    <input type="hidden" name="s_gyear"         value="<%=ss_gyear%>">      <!--in relation to select-->
	    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
	    <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
	    <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>"> <!--in relation to select-->
	    <input type="hidden" name="s_subjseq"       value="<%=ss_subjseq%>">    <!--in relation to select-->
	    <input type="hidden" name="s_action">
	
	    <input type="hidden" name="p_isonoff"      value="ON">
	    <input type="hidden" name="p_specials"     value="<%=data.getSpecials()%>">
	    <input type="hidden" name="p_subj"         value="<%=data.getSubj()%>">
	    <input type="hidden" name="p_crdate"       value="<%=data.getCrdate()%>">
	    <input type="hidden" name="p_pagegubun"    value="<%=v_pagegubun%>">
	
	    <input type="hidden" name="p_isoutsourcing" value="N">
	    <input type="hidden" name="p_musertel" value="">
	    <input type="hidden" name="p_edulimit" value="<%=String.valueOf(data.getEdulimit())%>">
	    <input type="hidden" name="s_area" value="<%= box.getString("s_area") %>" />

		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
			<tr>
				<td align="center" valign="top">
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="/images/admin/course/c_title01.gif" ></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
						</tr>
					</table>
					<br>
					<table cellspacing="1" cellpadding="5" class="table_out">
						<tr>
							<td colspan="8" class="table_top_line"></td>
						</tr>
						<tr>
							<td class="table_title" rowspan="27"><b>과정정보</b></td>
							<td colspan="6" class="table_01"><font color="red">(*)</font> 표시는 필수 입력입니다.</td>
						</tr>
						<tr>
							<td class="table_01">교육구분</td>
							<td class="table_02_2" >
								사이버 [<%=data.getSubj()%>]
								<kocca_select:select name="p_area" sqlNum="0002"  param="101" type="2.2" onChange="" attr=" " selectedValue='<%= data.getArea() %>' isLoad="true" all="none" />
							</td>
							<td  rowspan="4" class="table_title"><b>콘텐츠정보</b> </td>
							<td colspan="2" class="table_01">콘텐츠등급</td>
							<td class="table_02_2" ><%=CodeConfigBean.getCodeGubunSelect ("0056", "", 1, "p_contentgrade", data.getContentgrade(), " tabindex=1", 0)%></td>
						</tr>
						<tr>
							<td class="table_01">콘텐츠타입</td>
							<td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect ("0007", "", 1, "p_contenttype", data.getContenttype(), " tabindex=1", 0)%> </td>
							<td colspan="2" rowspan="2" class="table_01">제작회사</td>
							<td class="table_02_2" >
								<input type="hidden" name="p_dir" value="<%=data.getDir()%>"> 이름 : <input type="text" class="input" name="p_producernm" size="14" maxlength="13" value="<%=data.getProducernm()%>" tabindex=41> <a href="javascript:searchProducer()"><img src="/images/admin/button/b_resarchbutt.gif" align="absmiddle" border="0" tabindex=42></a>
							</td>
						</tr>
						<tr>
							<td class="table_01">과정명<font color="red">(*)</font></td>
							<td class="table_02_2"><input type="text" class="input" name="p_subjnm" size="38" maxlength="100" value="<%=data.getSubjnm()%>" tabindex=2></td>
							<td class="table_02_2">코드 : <input type="text" class="input" name="p_producer" size="14" maxlength="13" value="<%=data.getProducer()%>" readonly tabindex=43></td>
						</tr>
						<tr>
							<td class="table_01">과정분류<font color="red">(*)</font></td>
							<td height="50" class="table_02_2">
								대분류
									<kocca:selectBox name="p_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%=data.getUpperclass() %>" isLoad="true" /><br/>
								중분류 
								<%if ( !data.getMiddleclass().equals("") ) { %>
									<kocca:selectBox  name="p_middleclass" id="oMiddleclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%=data.getUpperclass() %>" selectedValue="<%=data.getMiddleclass() %>" isLoad="true" /><br/>
								<% } else { %>
									<select name="p_middleclass" id="oMiddleclass">
										<option value="">== 전체 ==</option>
									</select><br/>
								<% } %>	
								소분류
								<%if ( !data.getLowerclass().equals("") ) { %>
									<kocca:selectBox name="p_lowerclass" id="oLowerclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%=data.getUpperclass() + ',' + data.getMiddleclass() %>" selectedValue="<%=data.getLowerclass() %>" isLoad="true" /><br/>
								<% } else { %>
									<select name="p_lowerclass" id="oLowerclass">
									    <option value="">== 전체 ==</option>
									</select>
								<% } %>
								<!--수강신청방식 Default 일반신청(1)-->
								<input type="hidden" name="p_proposetype" value="1">
							</td>
							<td colspan="2" class="table_01">제작일자</td>
							<td class="table_02_2" ><input name="p_crdate_view" id="p_crdate_view" type="text" class="datepicker_input1" size="10" value="<%=FormatDate.getFormatDate(data.getCrdate(),"yyyy-MM-dd")%>" tabindex=47></td>
						</tr>
						<tr>
							<td class="table_01">과정유형</td>
							<td height="50" class="table_02_2">
								<kocca:selectBox name="p_subjtype" id="p_subjtype" optionTitle="== 전체 ==" type="sqlID" sqlID="code.list.0001" param="0076" selectedValue="" isLoad="true" /><br/>
							</td>
							<td class="table_title"><b>이달의<br>추천과정</b></td>
							<td colspan="2" class="table_01">추천수</td>
							<td class="table_02_2" ><input type="text" name="hitnumber" value="<%=data.getHitnumber()%>" size="10"></td>
						</tr>
						<tr>
							<td class="table_01">기본/실무</td>
							<td height="50" class="table_02_2" colspan="5">
								<select id="oBizType" name="bizType">
									<option value="">-- 선택 --</option>
									<option value="F01" <%= data.getBizType().equals("F01") ? "selected=\"selected\"" : "" %>>기본</option>
									<option value="F02" <%= data.getBizType().equals("F02") ? "selected=\"selected\"" : "" %>>실무</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="table_01">수시과정여부</td>
							<td height="50" class="table_02_2" colspan="5">
								<input name="p_autoconfirm" type="radio" value="Y" class="input" checked>예 <input name="p_autoconfirm" type="radio" class="input" value="N" <%="N".equals(data.getAutoconfirm())?"checked":""%>>아니요 &nbsp;&nbsp;&nbsp;(선택시 수강신청과 동시에 승인)
							</td>
						</tr>
						<tr>
							<td class="table_01">수료증출력가능여부</td>
							<td height="50" class="table_02_2" colspan="5">
								<select name="p_suryoyn"  class="inputpsearch" tabindex=17>
									<option value='Y'>Y</option>
									<option value='N' <%="N".equals(data.getSuryoyn())?"selected":""%>>N</option>
								</select>
								<input type="text" class="input" name="p_suryotitle" size="38" maxlength="100" value="<%=data.getSuryotitle()%>" tabindex="">
							</td>
						</tr>
						<tr>
							<td class="table_01">연관검색어</td>
							<td height="50" class="table_02_2" colspan="5">
								<input type="text" class="input" name="p_search_nm" size="60" maxlength="100" value="<%=data.getSearch_nm()%>" tabindex="3" >
							</td>
						</tr>
						<tr>
							<td class="table_01">과정특성
							<%
				                String specials = data.getSpecials();
				                String p_new   = (specials.length() > 0 ? specials.substring(0,1) : "N");
				                String p_hit   = (specials.length() > 1 ? specials.substring(1,2) : "N");
				                String p_recom = (specials.length() > 2 ? specials.substring(2,3) : "N");
							%>
							</td>
							<td class="table_02_2">
								신규
								<select name="p_new"  class="inputpsearch" tabindex=15>
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
							</td>
							<td rowspan="3" class="table_title"><b>학습정보</b></td>
							<td colspan="2" class="table_01">학습방법(WBT%)</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_ratewbt" size="10" maxlength="3" value="<%=String.valueOf(data.getRatewbt())%>" tabindex=48></td>
						</tr>
						<tr>
							<td  rowspan="2" class="table_01"><!--운영담당-->담당교수<font color="red">(*)</font></td>
							<td height="34" class="table_02_2" >
								<a href="javascript:searchMuser()" tabindex="21"><img src="/images/admin/button/b_resarchbutt.gif" align="absmiddle" border="0"></a>
							</td>
							<td colspan="2" class="table_01">학습방법(VOD%)</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_ratevod" size="10" maxlength="3" value="<%=String.valueOf(data.getRatevod())%>" tabindex=49></td>
						</tr>
						<tr>
							<td height="34" class="table_02_2" >
								<div id="muserList"></div>
								<script>
									<!--
									//muserList.1
									$(document).ready(function(){
										listMuser();
									});
									
									function listMuser() {
										$.post(
											"/servlet/controller.common.TagServlet"
											, {sqlNum:"muserList.1", param:"<%=data.getSubj()%>",type:4,objNm:"muser"}
											, function(data) { $("#muserList").html(data); }
										);
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
							<td colspan="2" class="table_01">복습가능여부</td>
							<td class="table_02_2">
								<select name="p_isablereview"  class="inputpsearch" tabindex=53>
									<option value='Y' <% if(data.getIsablereview().equals("Y") ||data.getIsablereview().equals("")) out.print(" selected");%>>Y</option>
									<option value='N' <% if(data.getIsablereview().equals("N")) out.print(" selected");%>>N</option>
								</select>
								/ 복습기간 : <input type="text" class="input" name="p_reviewdays" size="3" maxlength="3" value="<%=data.getReviewdays()%>" tabindex=53>
	                           <select name="p_reviewtype"  class="inputpsearch" tabindex=51>
	                                <option value="D" <% if (data.getReviewtype().equals("D")) out.println(" selected"); %>>일</option>
	                                <option value="W" <% if (data.getReviewtype().equals("W")) out.println(" selected"); %>>주</option>
	                                <option value="M" <% if (data.getReviewtype().equals("M")) out.println(" selected"); %>>개월</option>
	                                <option value="Y" <% if (data.getReviewtype().equals("Y")) out.println(" selected"); %>>년</option>
	                           </select>
							</td>
						</tr>
						<tr>
							<td height="31" class="table_01">과정사용여부</td>
							<td class="table_02_2" >
								<select name="p_isuse"  class="inputpsearch" onChange="whenIsuse()" tabindex=24>
									<option value='Y' <% if(data.getIsuse().equals("Y") ||data.getIsuse().equals("")) out.print(" selected");%>>Y</option>
									<option value='N' <% if(data.getIsuse().equals("N")) out.print(" selected");%>>N</option>
								</select>
							</td>
							<td rowspan="14" class="table_title"><b>점수정보</b></td>
							<td rowspan="6" class="table_01">이<br>수<br>기<br>준</td>
							<td  class="table_01">총 점<input type="hidden" name="p_point" value="100"></td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradscore" size="10" maxlength="5" value="<%=String.valueOf(data.getGradscore())%>" tabindex=54></td>
						</tr>
						<tr>
							<td class="table_01">학습자에게 보여주기</td>
							<td class="table_02_2" >
								<select name="p_isvisible"  class="inputpsearch" tabindex=25>
									<option value='Y' <% if (data.getIsvisible().equals("Y")) out.print("selected"); %>>Y</option>
									<option value='N' <% if (data.getIsvisible().equals("N")) out.print("selected"); %>>N</option>
								</select>
							</td>
							<td class="table_01">진도율</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradstep" size="10" maxlength="5" value="<%=String.valueOf(data.getGradstep())%>" tabindex=55></td>
						</tr>
						<tr>
							<td height="31" class="table_01">과정소개사용여부</td>
							<td class="table_02_2">
								<select name="p_isintroduction"  class="inputpsearch" tabindex=24>
									<option value='Y' <% if (data.getIsintroduction().equals("Y")) out.print("selected"); %>>Y</option>
									<option value='N' <% if (data.getIsintroduction().equals("N")) out.print("selected"); %>>N</option>
								</select>
							</td>
							<td class="table_01">중간평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradexam" size="10" maxlength="5" value="<%=String.valueOf(data.getGradexam())%>" tabindex=56></td>
						</tr>
						<tr>
							<td class="table_01">총프레임수</td>
							<td class="table_02_2"><input type="text" class="input" name="p_framecnt" size="10" maxlength="10" value="<%=String.valueOf(data.getFramecnt())%>" tabindex=28></td>
							<td class="table_01">최종평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradftest" size="10" maxlength="5" value="<%=String.valueOf(data.getGradftest())%>" tabindex=57></td>
						</tr>
						<tr>
							<td class="table_01">수강료</td>
							<td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="<%=String.valueOf(data.getBiyong())%>" tabindex=29>원</td>
							<td class="table_01">형성평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradhtest" size="10" maxlength="5" value="<%=String.valueOf(data.getGradhtest())%>" tabindex=58></td>
						</tr>
						<tr>
							<td class="table_01">학습일차</td>
							<td class="table_02_2"><input type="text" class="input" name="p_edudays" size="10" maxlength="5" value="<%=String.valueOf(data.getEdudays())%>" tabindex=30></td>
							<td class="table_01">리포트</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_gradreport" size="10" maxlength="5" value="<%=String.valueOf(data.getGradreport())%>" tabindex=59></td>
						</tr>
						<tr>
							<td  class="table_01">학습기간</td>
							<td class="table_02_2"><input type="text" class="input" name="p_eduperiod" size="5" maxlength="2" value="<%=String.valueOf(data.getEduperiod())%>" tabindex=32> 주</td>
							<td rowspan="8" class="table_01">가<br>중<br>치<br>(%)<br> <font color="red">(*)</font></td>
							<td class="table_01">중간평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wmtest" size="10" maxlength="5" value="<%=String.valueOf(data.getWmtest())%>" tabindex=61></td>
						</tr>
						<tr>
							<td class="table_01">학습시간</td>
							<td class="table_02_2"><input type="text" class="input" name="p_edutimes" size="10" maxlength="5" value="<%=String.valueOf(data.getEdutimes())%>" tabindex=31>시간 </td>
							<td class="table_01">진도율</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wstep" size="10" maxlength="5" value="<%=String.valueOf(data.getWstep())%>" tabindex=60></td>
						</tr>
						<tr>
							<td class="table_01">정원</td>
							<td class="table_02_2"><input type="text" class="input" name="p_studentlimit" size="10" maxlength="5" value="<%=String.valueOf(data.getStudentlimit())%>" tabindex=32>명</td>
							<td class="table_01">최종평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wftest" size="10" maxlength="5" value="<%=String.valueOf(data.getWftest())%>" tabindex=62></td>
						</tr>
						<tr>
							<td  class="table_01">교재여부</td>
							<td class="table_02_2">
								<select name="p_usebook"  class="inputpsearch" onChange="whenUsebook()" tabindex=33>
									<option value='Y' <% if(data.getUsebook().equals("Y")) out.print(" selected");%>>Y</option>
									<option value='N' <% if(data.getUsebook().equals("N")) out.print(" selected");%>>N</option>
								</select>
							</td>
							<td class="table_01">형성평가</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_whtest" size="10" maxlength="5" value="<%=String.valueOf(data.getWhtest())%>" tabindex=63></td>
						</tr>
						<tr>
							<td  class="table_01">교재명</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_bookname" size="38" maxlength="100" value="<%=data.getBookname()%>" tabindex=34></td>
							<td class="table_01">리포트</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wreport" size="10" maxlength="5" value="<%=String.valueOf(data.getWreport())%>" tabindex=64> <input type="hidden" name="p_wact" value="0"></td>
						</tr>
						<tr>
							<td height="36"  class="table_01">교재비</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_bookprice" size="10" maxlength="5" value="<%=String.valueOf(data.getBookprice())%>" <% if(data.getUsebook().equals("N")) out.print(" disabled");%> tabindex=35>원</td>
							<td class="table_01">참여도</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wetc1" size="10" maxlength="5" value="<%=String.valueOf(data.getWetc1())%>" tabindex=65></td>
						</tr>
						<tr>
							<td class="table_01">고용보험여부</td>
							<td class="table_02_2">
								<select name="p_isgoyong" onChange="whenIsgoyong()" class="inputpsearch" tabindex=26>
									<option value='Y' <% if( data.getIsgoyong().equals("Y") ||  data.getIsgoyong().equals("")) out.print(" selected");%>>Y</option>
									<option value='N' <% if( data.getIsgoyong().equals("N")) out.print(" selected");%>>N</option>
								</select>
							</td>
							<td class="table_01">기타</td>
							<td class="table_02_2" ><input type="text" class="input" name="p_wetc2" size="10" maxlength="5" value="<%=String.valueOf(data.getWetc2())%>" tabindex=64></td>
						</tr>
						<tr>
							<td class="table_01">환급금액</td>
							<td class="table_02_2" >
								<input type="text" class="input" name="p_goyongprice_major" size="10" maxlength="7" value="<%=String.valueOf(data.getGoyongpricemajor())%>" tabindex=27> 원 <br>
		                		<!--
		                		중소기업 <input type="text" class="input" name="p_goyongprice_minor" size="10" maxlength="7" value="<%=String.valueOf(data.getGoyongpriceminor())%>" tabindex=27> 원
		                		-->
		                	</td>
		                	<td colspan="2" class="table_01">가중치(%)의 합은 100이어야 합니다.</td>
		                </tr>
		                <tr>
							<td class="table_title" rowspan="3"><b></b></td>
							<td class="table_01">상시/수시</td>
							<td class="table_02_2" colspan="5">
								<select name="p_isordinary" onChange="whenIsordinary()">
									<option value='N' <% if( data.getIsordinary().equals("N")) out.print(" selected");%>>수시</option>
									<option value='Y' <% if( data.getIsordinary().equals("Y")) out.print(" selected");%>>상시</option>
								</select>
								<br>
								<%
						            String v_sphere1 = "ALL";
						            String v_sphere2 = "ALL";
						            String v_optsphere1 = "disabled";
						            String v_optsphere2 = "disabled";
						            if (data.getIsordinary().equals("Y")) {
						                v_sphere1    = data.getSphere();
						                v_optsphere1 = "";
						            } else {
						                v_sphere2    = data.getSphere();
						                v_optsphere2 = "";
						            }
								%>
								분야(상시) <%=CodeConfigBean.getCodeGubunSelect ("0067", "", 1, "p_sphere1", v_sphere1, v_optsphere1, 2)%>
								분야(수시) <%=CodeConfigBean.getCodeGubunSelect ("0068", "", 1, "p_sphere2", v_sphere2, v_optsphere2, 2)%>
								<input type="hidden" name="p_sphere" value="<%=data.getSphere()%>">
							</td>
						</tr>
						<tr>
							<td class="table_01">워크숍</td>
							<td class="table_02_2" colspan="5">
								<select name="p_isworkshop">
									<option value='N' <% if( data.getIsworkshop().equals("N")) out.print(" selected");%>>N</option>
									<option value='Y' <% if( data.getIsworkshop().equals("Y")) out.print(" selected");%>>Y</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="table_01">학점인증과정</td>
							<td class="table_02_2" colspan="5">
								<select name="p_isunit">
									<option value='N' <% if( data.getIsunit().equals("N")) out.print(" selected");%>>N</option>
									<option value='Y' <% if( data.getIsunit().equals("Y")) out.print(" selected");%>>Y</option>
								</select>
								학점 : <input type="text" class="input" name="p_degree" id="p_degree" size="4" maxlength="4" value="<%=data.getDegree()%>"> 점
							</td>
						</tr>
						<tr>
							<td class="table_title" rowspan="5"><b>URL 정보</b></td>
							<td class="table_01">학습 URL</td>
							<td class="table_02_2" colspan="5"><input type="text" class="input" name="p_eduurl" size="100" maxlength="150" value="<%=data.getEduurl()%>"  tabindex=36></td>
						</tr>
						<tr>
							<td class="table_01">미리보기 URL</td>
							<td class="table_02_2" colspan="5">
								<input type="text" class="input" name="p_preurl" size="100" maxlength="150" value="<%=data.getPreurl()%>"  tabindex=36><br>
								WIDTH: <input type="text" class="input" name="p_prewidth" size="8" maxlength="4" value="<%=data.getPrewidth()%>"> &nbsp;
								HEIGHT: <input type="text" class="input" name="p_preheight" size="8" maxlength="4" value="<%=data.getPreheight()%>">
							</td>
						</tr>
						<tr>
							<td class="table_01">학습목차 URL</td>
							<td class="table_02_2" colspan="5">
								<input type="text" class="input" name="p_conturl" size="100" maxlength="150" value="<%=data.getConturl()%>"  tabindex=36>
							</td>
						</tr>
						<tr>
							<td class="table_01">모바일 사용여부</td>
							<td class="table_02_2" colspan="5">
								<select id="oMobileUseYn" name="mobileUseYn" onchange="fnSetMobileUrlStatus(this.value);">
									<option value="Y" <%= data.getMobileUseYn().equals("Y") ? "selected='selected'" : ""%>>예</option>
									<option value="N" <%= data.getMobileUseYn().equals("N") ? "selected='selected'" : ""%>>아니오</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="table_01">모바일 미리보기 URL</td>
							<td class="table_02_2" colspan="5"><input type="text" class="input" id="oMobilePreurl" name="p_mobile_preurl" size="100" maxlength="150" value="<%=data.getMobile_preurl()%>" tabindex=36 <%= data.getMobileUseYn().equals("N") ? "readonly" : ""%>/></td>
						</tr>
					</table>
					<br>
			
					<!----------------- 과정맛보기 등록 시작 ----------------->
					<table cellspacing="1" cellpadding="5" class="table_out">
						<tr>
							<td width="16%" class="table_title"><b>수강신청 입력정보</b></td>
							<td width="81%" class="table_02_2">
								<table>
									<tr>
										<td width="80%">
											<input type='hidden' id='p_needinput' name='p_needinput' value="<%=data.getNeedinput()%>"/>
											<kocca_select:select name="needinput" sqlNum="0001"  param="0078" onChange="changeNeedInput" selectedValue="<%=data.getNeedinput()%>" isLoad="true" all="true" isCheckBox="Y" />
											<script>
												function changeNeedInput() {
													$("#p_needinput").val('');
													
													for (needInput_i = 0; needInput_i < document.all.needinput.length; needInput_i++) {
														if (document.all.needinput[needInput_i].checked) $("#p_needinput").val($("#p_needinput").val()+'1');
														else $("#p_needinput").val($("#p_needinput").val()+'0');
													}
												}
											</script>
										</td>
										<td width="5%">&nbsp;</td>
										<td><a href="javascript:performBuyBagPreviewPage();"><img style="border:none" src="/images/admin/button/btn_preview.gif" alt="입력양식 미리보기"/></a></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="16%" class="table_title"><b>소개이미지</b></td>
							<td width="81%" class="table_02_2">
								<% if (!data.getIntroducefilenamereal().equals("")) {      %>
									<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= data.getIntroducefilenamenew()%>&p_realfile=<%= data.getIntroducefilenamereal()%>'><%=data.getIntroducefilenamereal()%></a>&nbsp;<input type = "checkbox" name = "p_introducefile0" value = "1"> (삭제시 체크)
								<% } %>
								<input type="hidden" name = "p_introducefile1" value="<%=data.getIntroducefilenamereal()%>">
								<input type="hidden" name = "p_introducefile2" value="<%=data.getIntroducefilenamenew()%>">
								<input name="p_introducefile" type="file" class="input" size="118" maxlength="100" tabindex=65>
							</td>
						</tr>
						<tr>
							<td width="16%" class="table_title"><b>파일</b></td>
							<td width="81%" class="table_02_2">
								<% if (!data.getInformationfilenamereal().equals("")) { %>
									<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= data.getInformationfilenamenew()%>&p_realfile=<%= data.getInformationfilenamereal()%>'><%=data.getInformationfilenamereal()%></a>&nbsp;<input type = "checkbox" name = "p_informationfile0" value = "1"> (삭제시 체크)
								<% } %>
								<input type="hidden" name="p_informationfile1" value="<%=data.getInformationfilenamereal()%>">
								<input type="hidden" name="p_informationfile2" value="<%=data.getInformationfilenamenew()%>">
								<input name="p_informationfile" type="file" class="input" size="118" maxlength="100" tabindex=65>
							</td>
						</tr>
						<tr>
							<td width="16%" class="table_title"><b>메인화면이미지</b></td>
							<td width="81%" class="table_02_2">
								<% if (!data.getSubmainfilenamereal().equals("")) { %>
									<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= data.getSubmainfilenamenew()%>&p_realfile=<%= data.getSubmainfilenamereal()%>'><%=data.getSubmainfilenamereal()%></a>&nbsp;<input type = "checkbox" name = "p_submainfile0" value = "1"> (삭제시 체크)
								<% } %>
								<input type="hidden" name = "p_submainfile1" value="<%=data.getSubmainfilenamereal()%>">
								<input type="hidden" name = "p_submainfile2" value="<%=data.getSubmainfilenamenew()%>">
								<input name="p_informationfile" type="file" class="input" size="118" maxlength="100" tabindex=65>
							</td>
						</tr>
						<tr>
							<td width="16%" class="table_title"><b>교육대상</b></td>
							<td width="81%" class="table_02_2"><textarea name="p_edumans" cols="100" rows="3"><%=data.getEdumans()%></textarea></td>
						</tr>
						<tr>
							<td class="table_title"><b>교육목적</b></td>
							<td class="table_02_2"><textarea name="p_intro" cols="100" rows="3" tabindex=71><%=data.getIntro()%></textarea></td>
						</tr>
						<tr>
							<td class="table_title"><b>교육내용</b></td>
							<td class="table_02_2"><textarea name="p_explain" cols="100" rows="5" tabindex=72><%=data.getExplain()%></textarea></td>
						</tr>
						<tr>
							<td class="table_title"><b>강의교재 및 참고문헌</b></td>
							<td class="table_02_2"><textarea name="p_memo" cols="100" rows="5" tabindex=67><%=data.getMemo()%></textarea></td>
						</tr>
						<tr>
							<td class="table_title"><b><label for="p_subjsummary">과정개요</label></b></td>
							<td class="table_02_2"><input type="text" name="p_subjsummary" id="p_subjsummary" value="<%=data.getSubjSummary()%>" maxlength="80" size="100" class="input" /></td>
						</tr>
						<tr>
							<td class="table_title"><b><label for="p_subjtags">TAGS</label></b></td>
							<td class="table_02_2"><input type="text" name="p_subjtags" id="p_subjtags" value="<%=data.getSubjTags()%>" maxlength="80" size="100" class="input" /></td>
						</tr>
					</table>
					<!----------------- 과정맛보기 등록 끝 ----------------->
					<br>
			
					<!----------------- 등록, 리스트 버튼 시작 ----------------->
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_save.gif" border="0" tabindex=73></a></td>
							<td width=8></td>
							<% if (v_pagegubun.equals("Approval")) { %>
							<td align="center"><a href="javascript:ApprovalSubject()"><img src="/images/admin/button/btn_ok02.gif"  border="0" tabindex=74></a></td>
							<td width=8></td>
							<% } %>
							<td align="center">
								<a href="<% if (data.getSubjseqcount() == 0 ) { if (data.getSubjobjcount() == 0 ) { %>javascript:DeleteSubject()<% } else  {%>javascript:alert('과정에 콘텐츠가 매핑되어있어 삭제할 수 없습니다.')<% } %><%} else {%>javascript:alert('과정차수 정보가 있어 삭제할 수 없습니다.')<% } %>">
    		                    <img src="/images/admin/button/btn_del.gif" border="0" tabindex=75></a>
		                    </td>
							<td width=8></td>
							<td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif"  border="0" tabindex=76></a></td>
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
