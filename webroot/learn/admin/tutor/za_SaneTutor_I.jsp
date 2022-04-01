<%
//**********************************************************
//  1. 제      목: 사내 TUTOR INSERT PAGE
//  2. 프로그램명: za_SaneTutor_I.jsp
//  3. 개      요: 사내 강사 등록 page
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 8. 18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_isgubun    =  box.getString("p_isgubun");
    String p_process    =  "TutorInsert";
    //DEFINED class&variable END

    //강사검색 popup창 parameter 시작
    String v_pagegubun  = box.getString("p_pagegubun"); //강사/교육기관검색 Popop창에서 호출된경우는 value="popup"
    String v_tutorgubun = box.getString("p_tutorgubun");
    String v_searchtext = box.getString("p_searchtext");
    //강사검색 popup창 parameter 끝

%>

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
<script language = "javascript" src = "/script/tutor.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_ldate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    //주소찾기
    function searchPost() {
        window.self.name = "personalInform";
        open_window("openPost","","100","100","367","500","","","","yes","");
        document.form1.target = "openPost";
        document.form1.action='/servlet/controller.library.PostSearchServlet';
        document.form1.p_process.value = 'SearchPostOpenPage';
        document.form1.submit();
    }

    function changedOn(x) {
<!--                //alert("changedOn");-->
<!--            if (x == 1) {-->
<!--                //alert("sane :  "+x);-->
<!--                do_sane();-->
<!--            } -->
<!--            else if (x == 2) {-->
<!--                //alert("saoi :  "+x);-->
<!--                do_saoi();-->
<!--            }-->

        do_sane();
    }

    //사내강사,그룹사강사 일때 화면 전환
    function do_sane() {
        //사내강사 ==> 교수 화면으로
        //성명 검색 버튼 Disable
        //아이디/PW Enabled
        //ReadOnly Disable
        srchnm.style.display ='';
        srchid.style.display ='none';
        pwd.style.display ='none';
        addr.style.display ='none';
        chk_readOnly(true);
        document.all.span_userid.innerHTML = "<strong>* 아이디</strong>";
//          document.all.span_resno.innerHTML = "";
/*
        if (document.form1.p_isgubun[0].checked) {
            document.form1.p_tutorgubun.value = "1";
        } else if (document.form1.p_isgubun[2].checked) {
            document.form1.p_tutorgubun.value = "3";
        }

    */
        //alert(document.form1.p_tutorgubun.value);
        document.form1.p_tutorgubun.value = "1";

        auth();
        value_ini();

    }

    //사외강사 권한별 화면 전환
    function do_saoi() {
        //사외강사 ==> 강사 화면으로
         srchnm.style.display ='none';
         srchid.style.display ='';
         pwd.style.display ='';
         addr.style.display ='';
         chk_readOnly(false);
         document.all.span_userid.innerHTML = "<strong>* 아이디</strong>";
//           document.all.span_resno.innerHTML = "주민등록번호";
/*
        if (document.form1.p_isgubun[1].checked) {
            document.form1.p_tutorgubun.value = "2";
        }
*/
        document.form1.p_tutorgubun.value = "2";
         auth();
         value_ini();

<!--                        //사내강사 ==> 교수 화면으로-->
<!--            //성명 검색 버튼 Disable-->
<!--            //아이디/PW Enabled-->
<!--            //ReadOnly Disable-->
<!--            srchnm.style.display ='';-->
<!--            srchid.style.display ='none';-->
<!--            pwd.style.display ='none';-->
<!--            addr.style.display ='none';-->
<!--            chk_readOnly(true);-->
<!--            document.all.span_userid.innerHTML = "<strong>* 아이디</strong>";-->
<!--//          document.all.span_resno.innerHTML = "";-->
<!--/*-->
<!--            if (document.form1.p_isgubun[0].checked) {-->
<!--                document.form1.p_tutorgubun.value = "1";-->
<!--            } else if (document.form1.p_isgubun[2].checked) {-->
<!--                document.form1.p_tutorgubun.value = "3";-->
<!--            }-->
<!--        -->
<!--        */-->
<!--            //alert(document.form1.p_tutorgubun.value);-->
<!--            document.form1.p_tutorgubun.value = "1";-->
<!--            -->
<!--            auth();-->
<!--            value_ini();-->

    }
    //입력 필드별 권한부여
    function chk_readOnly(gubun) {
        if (gubun) {
            //사내강사는 입력할 수 없도록 설정
            document.form1.p_userid.readOnly = true;
            document.form1.p_phone.readOnly = true;
            document.form1.p_handphone.readOnly = true;
            document.form1.p_comp.readOnly = true;
            document.form1.p_email.readOnly = true;
            //document.form1.p_jik.readOnly = true;
            document.form1.p_addr.readOnly = true;
            document.form1.p_resno.readOnly = true;
        } else {
            //사외강사는 입력할 수 있도록 설정
            document.form1.p_userid.readOnly = false;
            document.form1.p_phone.readOnly = false;
            document.form1.p_handphone.readOnly = false;
            document.form1.p_comp.readOnly = false;
            document.form1.p_email.readOnly = false;
            //document.form1.p_jik.readOnly = false;
            document.form1.p_addr.readOnly = false;
            document.form1.p_resno.readOnly = false;
        }
    }

    //변수 기본값 셋팅
    function value_ini() {

        document.form1.p_userid.value = "";
//          document.form1.p_loginid.value = "";
        document.form1.p_loginpw.value = "";
        document.form1.p_idchk.value = "";
        document.form1.p_name.value = "";
        document.form1.p_phone.value = "";
        document.form1.p_handphone.value = "";
        document.form1.p_comp.value = "";
        document.form1.p_compcd.value = "";
        document.form1.p_email.value = "";
        //document.form1.p_jik.value = "";
        document.form1.p_post1.value = "";
        document.form1.p_post2.value = "";
        document.form1.p_addr.value = "";
        document.form1.p_resno.value = "";

    }

    //권한부여
    function auth() {
        ff = document.form1;
        <% if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
        if (ff.p_manager[0].checked) { //권한부여
            term.style.display = '';
            subj.style.display = '';

            //사외강사
            if(ff.p_isgubun.value == 1) {
                //loginid.style.display ='none';
            } else {
                //loginid.style.display ='';
            }
        } else {//권한 미부여
            term.style.display ='none';
            subj.style.display ='none';
            //loginid.style.display ='none';
        }
        <% } %>

    }

    //과정조회화면 팝업
    function open_window(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }

    //취소 조회화면으로 이동
    function cancel() {
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
        document.form1.p_process.value = "TutorList";
        document.form1.submit();
    }

    function gubunCheck(){
        document.form1.target = "_self";
        if(document.form1.p_isgubun.value == 1){
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
            document.form1.p_process.value = "SaneTutorInsertPage";
            document.form1.submit();
        }else if(document.form1.p_isgubun.value == 2){
            document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
            document.form1.p_process.value = "SaoiTutorInsertPage";
            document.form1.submit();
        }
    }

    //과정찾기
    function searchSubj() {
        var url = "/servlet/controller.library.SearchServlet?p_process=subjseq&p_gubun=subjnm&p_key1=&p_key2=";
        open_window("",url,"0","0","100","100");
    }

    //과정 매칭
    function receiveSubj(subj,subjnm){
        var j = document.form1.p_subj.length;
        var f_exist = "";
        for(var i=0;i<j;i++){
            if(document.form1.p_subj.options[i].value==subj){
                f_exist = "Y";
                alert(subjnm+"은 이미 선정되어 있습니다");
            }
        }
        if (f_exist != "Y"){
            document.form1.p_subj.options[j] = new Option(subjnm,subj);
            document.form1.p_subj.options[j].selected = true;
        }
    }

    function receiveSubjseq(subj,subjnm,key,year,subjseq){
        var j = document.form1.p_subj.length;
        var f_exist = "";
        var tmp=subj+"/"+year+"/"+subjseq;

        for(var i=0;i<j;i++){
            if(document.form1.p_subj.options[i].value==tmp){
                f_exist = "Y";
                alert(subjnm+"은 이미 선정되어 있습니다");
            }
        }
        if (f_exist != "Y"){
            document.form1.p_subj.options[j] = new Option(subjnm,tmp);
            document.form1.p_subj.options[j].selected = true;
        }
    }

    //과정 매칭정보 삭제
    function delSubj(){
        for(var i = 0 ;i<document.form1.p_subj.length;i++){
            if(document.form1.p_subj.options[i].selected==true)
                document.form1.p_subj.options[i] = null;
            }
    }

    //강사정보 저장
    function insert() {
        ff = document.form1;

        /*공통 체크
        if (ff.p_subjclass.value=="") {
            alert("강의분야를 선택하세요.");
            return;
        }
        */

    <%  if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
		// 권한부여시에만 과정체크
		if(ff.p_manager[0].checked) {
		    if (ff.p_sdate.value=="") {
		        alert("권한사용기간을 입력하세요.");
		        return;
		    }
		
		    if (ff.p_ldate.value=="") {
		        alert("권한사용기간을 입력하세요.");
		        return;
		    }
		
		    if ( ff.p_subj.length == 0 ) {
		        alert("강의과정을 지정해주십시오. ");
		        return;
		    }
		}
    <% } %>
		
		if (blankCheck(ff.p_name.value)) {
		    alert("성명을 입력하세요!");
		    ff.p_name.focus();
		    return;
		}
		
		if (blankCheck(ff.p_userid.value)) {
		    if(ff.p_isgubun.value == 1) {
		        alert("임직원조회버튼을 클릭하여 사내강사를 선택하세요.");
		    } else {
		        alert("아이디를 입력하세요.");
		    }
		    ff.p_userid.focus();
		    return;
		}

/*
		alert(ff.p_isgubun.value);
		
		if(ff.p_isgubun.value == 2) {
		
		    //사외 강사는 아이디 체크,비밀번호를 체크한다.
		        if (ff.p_userid.value=="") {
		            alert("Login 아이디를 입력하세요.")
		            ff.p_userid.focus();
		            return;
		        }
		
		        if(ff.p_isidchk.value == "N"){
		            alert("아이디 중복체크를 해주세요");
		            ff.p_userid.focus();
		            return;
		        }
		
		        if (ff.p_loginpw.value=="") {
		            alert("비밀번호를 입력하세요.")
		            ff.p_loginpw.focus();
		            return;
		        }
		
		        if (ff.p_loginpw2.value=="") {
		            alert("비밀번호를 입력하세요.")
		            ff.p_loginpw2.focus();
		            return;
		        }
		
		        if (ff.p_loginpw.value!=ff.p_loginpw2.value) {
		            alert("비밀번호를 확인하세요.")
		            ff.p_loginpw.focus();
		            return;
		        }
		
		        //주민번호 유효성 체크
		        //p_userid
		        if (!validPersono(ff.p_resno.value.substring(0,6), ff.p_resno.value.substring(6,13))) {
		
		            ff.p_resno.focus();
		            return;
		        }
		}
*/
		var st_date = make_date(ff.p_sdate.value);
		var ed_date = make_date(ff.p_ldate.value);
		ff.p_fmon.value = st_date;
		ff.p_tmon.value = ed_date;
		
		
		
		//사외강사는 경력,학력,소속입력 필수
		//사외강사는 전화번호,이동전화중 하나는 입력 필수
		if(ff.p_isgubun.value == 2) {
/*
			if (ff.p_phone.value=='' && ff.p_handphone.value=='') {
			    alert('전화번호 또는 휴대폰번호를 입력하세요.');
			    return;
			}
			
			if (ff.p_comp.value=='') {
			    alert('현재소속을 입력하세요.');
			    ff.p_comp.focus();
			    return;
			}
			
			if (ff.p_academic.value=='') {
			    alert('학력을 입력하세요.');
			    ff.p_academic.focus();
			    return;
			}
			
			if (ff.p_career.value=='') {
			    alert('경력을 입력하세요.');
			    ff.p_career.focus();
			    return;
			}
*/
		}
		
		
		var islimit1 = true;
		var file1 = document.form1.p_file1.value;
		
		
		if(file1!="") {islimit1 = limitFile(file1);}
		if(!islimit1){  return; }
		
		for(i=0;i<document.form1.p_subj.length;i++) {
		    document.form1.p_subj[i].selected = true;
		}
		document.form1.target = "_self";
		document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
		document.form1.p_process.value = "TutorInsert";
		document.form1.submit();
    }

// 사내강사 임직원조회
function tutor_search() {
    if (blankCheck(document.form1.p_name.value)) {
        alert("강사명을 입력해주십시오. ");
        return;
    }
    window.self.name = "winSelectView";
    open_window("openWinView","","100","100","560","400");
    document.form1.p_mode2.value = document.form1.p_name.value;
    document.form1.target = "openWinView";
    document.form1.action = "/servlet/controller.tutor.TutorAdminServlet";
    document.form1.p_process.value = "SearchTutorOpenPage";
    document.form1.submit();
}

// 회원아이디 조회
function mem_check(){
    var tf = document.form1;

    if(tf.p_userid.value.length == 0){
        alert("아이디를 입력하셔야 합니다.");
        tf.p_userid.focus();
        return;
    }

    if(!useridCheck(tf.p_userid.value)){
        alert("아이디를  영문, 숫자로 입력하셔야 합니다.");
        tf.p_userid.value="";
        tf.p_userid.focus();
        return;
    }
    else{
        //document.form1.p_process.value   = "usercheck";
        //document.form1.action            = "/servlet/controller.cp.CPCompServlet";
        //document.form1.target            = "chk_frame";
        //document.form1.submit();
        //document.form1.target            = "";

//          window.self.name = "exituserchk";
        //(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
        open_window("userchk","","100","100","400","250","no","no","yes","no","no");
        document.form1.target = "userchk";
        document.form1.p_isidchk.value = "Y";
        document.form1.action='/servlet/controller.cp.CPCompServlet';
        document.form1.p_process.value = 'usercheck';
        document.form1.submit();

    }
}

//-->
</script>


<!-- /////////////////////////////////////////////////////////-->
<!--
  파일확장자체크 INCLUDE

  1. page import = "com.credu.system.*" 추가
  2. 자바스크립트 호출부분 추가
    예제)
    //파일 확장자 필터링
    var islimit = true;
    var file = document.form1.p_file1.value;
    if(file!="") {
        islimit = limitFile(file);    //return값 true/false
    }

    if(islimit) {
        document.form1.submit();
    }else{
        return;
    }

-->
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!-- /////////////////////////////////////////////////////////-->


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="do_sane()">

<form name = "form1" method = "post" enctype ="multipart/form-data">
    <input type="hidden" name="p_process">
    <input type="hidden" name="isgubun" value="1">  <!--isgubun 1 : 사내강사 -->
    <input type="hidden" name="p_mode1"         value = "name">
    <input type="hidden" name="p_mode2"         value = "">
    <input type="hidden" name="p_compcd"        value = "">
    <input type="hidden" name="p_fmon"          value = "">
    <input type="hidden" name="p_tmon"          value = "">
    <input type="hidden" name="p_pagegubun"     value = "<%=v_pagegubun%>">
    <input type="hidden" name="p_tutorgubun"    value = "<%=v_tutorgubun%>">
    <input type="hidden" name="p_searchtext"    value = "<%=v_searchtext%>">
    <input type="hidden" name="p_ischk"         value= "0">
    <input type="hidden" name="p_isidchk"       value= "N">
    <input type="hidden" name="p_isoverlap"     value= "N">
    <input type="hidden" name="p_isinfo"        value= "N">
    <input type="hidden" name="p_tutorgubun"    value= "">
    <input type="hidden" name="p_grcode"    value= "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

       <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/tutor/tit_new_01.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

        <!----------------- form 시작 ----------------->

        <table class="form_table_out" cellspacing="1" cellpadding="0">
          <tr>
            <td>
              <table cellspacing="1" cellpadding="1" class="form_table_bg">


                <%//권한부여는 총괄관리자만 %>
                <%  if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A") || StringManager.substring(box.getSession("gadmin"),0,1).equals("B")) { %>
                <tr>
                    <td width="10%" class="table_title"><b>강사권한여부</b></td>
                    <td class="table_02_2" colspan="5">
                        <input type="radio" name="p_manager" value="Y" onClick="auth();">Yes
                        <input type="radio" name="p_manager" value="N" checked onClick="auth();">No
                    </td>
                </tr>
                <% } else { %>
                    <input type="hidden" name="p_manager" value="N">
                <% } %>

                <!--
                <tr>
                  <td width="15%" valign="middle"> &nbsp;강사구분 :

                    <input type="radio" name="p_isgubun" value="1" checked  onClick="do_sane();">교수
                    <input type="radio" name="p_isgubun" value="2" onClick="do_saoi();">강사
                    <input type="radio" name="p_isgubun" value="3" onClick="do_sane();">그룹사강사
                  </td>
                </tr>
                -->

              </table>
            </td>
          </tr>
        </table>

        <!----------------- form 끝 ----------------->
        <br>
        <!----------------- 강사등록 시작 ----------------->
        <table width="970" border="0" cellpadding="0" cellspacing="1" class="table_out">
			<tr>
				<td colspan="4" class="table_top_line"></td>
			</tr>
		
			<tr>
				<td width="120" class="table_title" align="center"><b>강사구분</b></td>
				<td class="table_02_2" colspan="3">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<select name="p_isgubun" style="width:100" onchange="changedOn(this.value);">
									<option value="1">교수</option>
									<option value="2">강사</option>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		
			<tr style="display:none;">
				<td width="10%" class="table_title" align="center"><b>강의분야</b></td>
				<td  class="table_02_2" colspan="5">
					<%=CodeConfigBean.getCodeGubunSelect ("0039", "", 1, "p_subjclass", "", "", 1)%>
				</td>
			</tr>
		
			<tr id=term style="display: none">
				<td class="table_title" align="center"><b>권한사용기간</b></td>
				<td class="table_02_2" colspan="3">
					<input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" value=''> ~ <input name="p_ldate" id="p_ldate" class="datepicker_input1" type="text" value=''>
				</td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>* 성명</b></td>
				<td class="table_02_2">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input name="p_name" type="text" class="input" maxlength="10"></td>
							<td><div id="srchnm">&nbsp;<a href="javascript:tutor_search()"><img src="/images/admin/button/btn_imsearch.gif" border="0"></a></div></td>
						</tr>
					</table>
				</td>
				<td class="table_title" align="center"><span id="span_userid"><strong>* 아이디</strong></span></td>
				<td class="table_02_2">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td><input name="p_userid" type="text" class="input" maxlength="13" readonly></td>
							<td><div id="srchid">&nbsp;<a href="javascript:mem_check()"><img src="/images/admin/button/btn_idcheck.gif" border="0"></a><input type="hidden" name="p_idchk" value="false"></div></td>
						</tr>
					</table>
				</td>
			</tr>
		
			<tr id="pwd" style="display: none">
				<td class="table_title" align="center"><strong>* 비밀번호</strong></td>
				<td class="table_02_2"> <input name="p_loginpw" type="password" class="input" maxlength="20"></td>
				<td class="table_title" align="center"><strong>* 비밀번호 확인</strong></td>
				<td class="table_02_2"><input name="p_loginpw2" type="password" class="input" maxlength="20"></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><strong>주민등록번호</strong></td>
				<td class="table_02_2"> <input name="p_resno" type="text" class="input" maxlength="13" onkeyup="numeric_chk(this)">(- 없이)</td>
				<td class="table_title" align="center"><b>E-mail</b></td>
				<td class="table_02_2"><input name="p_email" type="text" class="input" maxlength="40" readonly onkeyup="isValidEmail(this)"></td>
			</tr>
			
			<tr>
				<td class="table_title" align="center"><b>전화번호</b></td>
				<td class="table_02_2"><input name="p_phone" type="text" class="input" maxlength="13" readonly></td>
				<td class="table_title" align="center"><b>휴대폰</b></td>
				<td class="table_02_2"> <input name="p_handphone" type="text" class="input" maxlength="13" readonly></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>주소</b></td>
				<td class="table_02_2" colspan="3">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<input type="text" name="p_post1" value="" size="4" maxlength="3" class="input" readonly> - <input type="text" name="p_post2" value=""  size="4" maxlength="3" class="input" readonly>
							</td>
							<td id=addr style="display: none"><a href="javascript:searchPost()"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
						</tr>
						<tr>
							<td colspan="2">
								<input name="p_addr" type="text" class="input" maxlength="50" size="50"><br>
								<input name="p_addr2" type="text" class="input" maxlength="50" size="50">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		
		
			<tr>
				<td class="table_title" align="center"><b>현재소속</b></td>
				<td class="table_02_2" colspan = "3"><input name="p_comp" type="text" class="input" maxlength="40" readonly></td>
			</tr>
		
			<tr id=subj style="display: none">
				<td class="table_title" align="center"><b>강의과정</b></td>
				<td class="table_02_2" colspan="3">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<select name="p_subj" size=4  multiple></select> <a href="javascript:searchSubj()"><img src="/images/admin/button/btn_csearch.gif" border="0"></a><a href="javascript:delSubj()"><img src="/images/admin/button/btn_cdelete.gif" border="0"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>강사소개말</b></td>
				<td class="table_02_2" colspan="3"> <textarea name="p_intro" style="height:80"></textarea></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>학력사항</b></td>
				<td class="table_02_2" colspan="3"> <textarea name="p_academic" style="height:80"></textarea></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>경력사항</b></td>
				<td class="table_02_2" colspan="3"> <textarea name="p_career" style="height:80"></textarea></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>저서</b></td>
				<td class="table_02_2" colspan="3"><textarea name="p_book" style="height:80"></textarea></td>
			</tr>
		
			<tr>
				<td class="table_title" align="center"><b>사진파일</b></td>
				<td class="table_02_2" colspan="3"><input name="p_file1" type="file" class="input" maxlength="100" size="93" tabindex=65></td>
			</tr>
		</table>
        <!----------------- 강사등록 끝 ----------------->
        <br>
        <!----------------- 등록, 취소 버튼 시작----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center" class="ms"><a href="javascript:insert()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
            <td width=8></td>
            <td align="center" class="ms"><a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝----------------->
        <br>
        <br>
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>

