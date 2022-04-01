<%
//**********************************************************
//  1. 제      목: 회원가입_회원정보
//  2. 프로그램명 :zu_MemberInfo_I.jsp
//  3. 개      요: 회원가입_회원정보
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page language="java" import="Kisinfo.Check.IPINClient" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","1");
	box.put("submenu","2");

	String v_username = box.getString("p_username");
    String v_resno    = box.getString("p_resno");
	String v_resno1   = box.getString("p_resno1");
    String v_resno2   = box.getString("p_resno2");
	String v_userid   = "";
	String v_check    = (String)request.getAttribute("check");
	String v_process  = box.getString("p_process");
    //pkh
	//String v_degree   = box.getString("d_degree");
	//String v_jikup    = box.getString("d_jikup");
	//String v_comptext = box.getSession("tem_grcode");
	String v_sex         = "";
	String v_memberyear  = "";
	String v_membermonth = "";
	String v_memberday   = "";
	
	//보호자 정보
	String vv_username = box.getString("pp_username");
	String vv_email1 = box.getString("pp_email1");
	String vv_email2 = box.getString("pp_email2");
	
	//아이핀 관련
	String i_sex         = box.getString("i_sex");
	String i_birthdate	 = box.getString("i_birthdate");
	String i_username	 = box.getString("i_username");	
	String i_dupinfo	 = box.getString("i_dupinfo").equals("")? box.getSession("p_dupinfo") : box.getString("i_dupinfo");
	String i_vnumber	 = box.getString("i_vnumber");
	String i_irtn		 = box.getString("i_irtn");	
	
	//안심실명인증
	String nc_safeid = box.getString("nc_safeid");
	String nc_username = box.getString("nc_username");
	String nc_jumindate = box.getString("nc_jumindate");
	String nc_gender = box.getString("nc_gender");
	String nc_cert = box.getString("nc_cert");
	
	//아이핀 인증일 경우 성별 값 넣어주기
	if(i_irtn.equals("1")){
		if(i_sex.equals("0")){
			v_sex = "2";
		}else if(i_sex.equals("1")){
			v_sex = "1";
		}
	}
	//아이핀 인증이 아닌 경우 성별 값 넣어주기
	if(!box.getString("p_resno2").equals("")){
		v_sex = box.getString("p_resno2").substring(0,1);
		if(box.getString("p_resno2").substring(0,1).equals("3")){
			v_sex="1";
		}else if(box.getString("p_resno2").substring(0,1).equals("4")){
			v_sex="2";
		}
	}
	// 안심실명인증일 경우 성별 값 넣어주기
	if(nc_cert.equals("Y")){
		if( nc_gender.equals("M") || nc_gender.equals("A") ){
			v_sex="1";
		}else if( nc_gender.equals("F") || nc_gender.equals("B") ){
			v_sex="2";
		}
	}	
	//아이핀 인증일 경우 생년월일 넣어주기
	if(i_irtn.equals("1")){
		v_memberyear  = i_birthdate.substring(0,4);
		v_membermonth = i_birthdate.substring(4,6);
		v_memberday   = i_birthdate.substring(6,8); 
	}	
	//아이핀 인증이 아닌 경우 생년월일 넣어주기
	if(!box.getString("p_resno1").equals("")){
		v_memberyear  = "19"+box.getString("p_resno1").substring(0,2); 
		v_membermonth = box.getString("p_resno1").substring(2,4);
		v_memberday   = box.getString("p_resno1").substring(4,6); 
	}
	//안심실명일경우 생년월일 넣어주기
	if(nc_cert.equals("Y")){
		v_memberyear  = nc_jumindate.substring(0,4); 
		v_membermonth = nc_jumindate.substring(4,6);
		v_memberday   = nc_jumindate.substring(6,8);
	}
	
	
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<script language="javascript" src="/script/jquery.form.js"></script>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<style type="text/css">
body {
	padding: 0px;
	}
h1 a {
	color: #000;
	}
input {
	border: 1px solid #999;
	margin: 0 5px;
	}
.password_strength {
	padding: 0 5px;
	display: inline-block;
	}
.password_strength_1 {
	background-color: #fcb6b1;
	}
.password_strength_2 {
	background-color: #fccab1;
	}
.password_strength_3 {
	background-color: #fcfbb1;
	}
.password_strength_4 {
	background-color: #dafcb1;
	}
.password_strength_5 {
	background-color: #bcfcb1;
	}
</style>

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

function existsId()
{
    var r=$.trim($("[name='p_userid']").val());
    if(r=="")
    {
        alert("아이디를 입력하여 주십시오.");
        return;
    }

    if(r.length<6 || r.length>12)
    {
        alert("아이디는 총 6~12자로만 조합이 되어야 합니다.");
        return;
    }

    var fm=document.form1;
	var chk_num = fm.p_userid.value.search(/[0-9]/g);
    var chk_lower = fm.p_userid.value.search(/[a-z]/ig);
    if (!/^[a-z0-9]{6,12}$/.test(fm.p_userid.value) ||  (chk_num < 0 || chk_lower< 0) ){
        alert("아이디는 영문 소문자, 숫자를 혼합하여 6-12자리로 입력해 주세요.");
        fm.p_userid.value;
        fm.p_userid.focus();
        return;
    }

    document.form1.p_process.value = "ASP_Login";
    document.form1.menuid.value = "0";
    document.form1.gubun.value = "20";

    $("[name='form1']").ajaxSubmit({
        url:'/servlet/controller.homepage.MainServlet',
        type:'post',
        data:{userId:r},
        success: function(data){
           var r1=$.trim(data);
           $("#div_Area").text("");
           idExists=false;
           if(r1=="0")
           {
               $("#div_Area").css("color","#FF0000");      //   ID 가 존재
               $("#div_Area").text("사용할수 없는 아이디 입니다.");
           }
           else
           {
               idExists=true;
               $("#div_Area").css("color","#0000FF");      //   ID 없음
               $("#div_Area").text("사용할수 있는 아이디 입니다.");               
               document.form1.p_chk.value = "Y";
           }
        }
    });
}


function checkId(){
	var userid = document.form1.p_userid.value;
	var p_userid_len = userid.length;

		if(p_userid_len > 0){
			if(p_userid_len > 5 && p_userid_len < 13){
                // ID 중복검사
                var url = "/servlet/controller.homepage.MainMemberJoinServlet?p_userid="+userid+"&p_process=CheckId";
                var v_win = window.open(url, "selfTest1"," left=20, top=20, width=394, height=312, menubar=no, scrollbars=no, resizable=yes");
			}else{
				alert("아이디를 6자 이상 12자 이하로 다시 입력해주세요.");
				return;
			}
		}else{
			alert("아이디를 입력해주세요.");
			return;
		}
	}


function IDText_Check () {
    var Str = document.form1.p_userid.value;
    var Chr="";
    var chkCnt=0;
    for (i=0; i<Str.length; i++) {
        Chr = Str.charAt(i);
        if ((Chr < "0" || Chr >"9" ) && (Chr <'a' || Chr >'z'))  {
        alert("영문소문자, 숫자 입력만 가능합니다.");
            document.form1.p_userid.value;
            document.form1.p_userid.focus();
            return false;
        }
    }
}

// 우편번호검색
function searchPost(gubun) {
	window.self.name = "PersonalSelect";
	open_window("openPost","","100","100","417","400","","","","yes","");
	document.form1.target = "openPost";
	document.form1.action='/servlet/controller.library.PostSearchServlet';
	document.form1.p_postgubun.value = gubun;
	document.form1.p_process.value = 'SearchPostOpenPage';
	document.form1.submit();
	document.form1.target = window.self.name;
}

function delAddr(no){
	if( no == 1 ){
		document.form1.p_home_addr2.value = ""
    }
}

function receivePost(post1, post2, addr, postgubun){
	if( postgubun == "1" ){
		//document.form1.p_post1.value = post1;
		//document.form1.p_post2.value = post2;
		document.form1.p_home_addr1.value = addr;
		document.form1.p_home_addr2.focus();
    }
}

function Submit_Check(){
    var fm=document.form1;
    var v_hometel = fm.p_hometel1.value+'-'+fm.p_hometel2.value+'-'+fm.p_hometel3.value;
    var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;
    var v_email = fm.p_email1.value+'@'+fm.p_email2.value;
    
    var chk_num = "";
    var chk_lower = "";
    var chk_upper = "";
    var chk_special = "";

    //if( (fm.p_kor_name.value) == ""){
    //    alert("이름을 입력하세요");
    //    fm.p_kor_name.focus();return;
    //}

    if( (fm.p_userid.value) == "" ){
        alert("아이디를 바르게 입력하세요");
        fm.p_userid.focus();return;
    }

    chk_num = fm.p_userid.value.search(/[0-9]/g);
    chk_lower = fm.p_userid.value.search(/[a-z]/ig);

    if (!/^[a-z0-9]{6,12}$/.test(fm.p_userid.value) ||  (chk_num < 0 || chk_lower< 0) ){
        alert("아이디는 영문 소문자, 숫자를 혼합하여 6-12자리로 입력해 주세요.");
        fm.p_userid.value;
        fm.p_userid.focus();
        return;
    }

    if(fm.p_chk.value != "Y"){
        alert("아이디 중복체크를 하세요"); return;
    }
	/*
	if( fm.p_sex[0].checked == false && fm.p_sex[1].checked == false ) {
		alert("성별을 선택하십시오.");
		return;
	}
	*/
	//if( fm.p_memberyear.value == "" ){
	//	alert("생년월일을 입력해 주세요.");
	//	return;
	//}
	//if( fm.p_membermonth.value == "" ){
	//	alert("생년월일을 입력해 주세요.");
	//	return;
	//}
	//if( fm.p_memberday.value == "" ){
	//	alert("생년월일을 입력해 주세요.");
	//	return;
	//}

    // 비밀번호 길이 제한
    var p_pwd_len =  fm.p_pwd.value.length;

    chk_num = fm.p_pwd.value.search(/[0-9]/g);
    chk_lower = fm.p_pwd.value.search(/[a-z]/ig);
    chk_upper = fm.p_pwd.value.search(/[A-Z]/ig);
    chk_special = fm.p_pwd.value.search(/[^a-zA-Z0-9]/g);

    if(p_pwd_len <= 0){
        alert("비밀번호를 입력해주세요.");
        fm.p_pwd.focus();         
        return;
    }else{
        //if (!/^[a-zA-Z0-9]{9,14}$/.test(fm.p_pwd.value) || (chk_num < 0 || chk_lower< 0 || chk_upper < 0)){
        if ( p_pwd_len < 9 || p_pwd_len > 14 || chk_lower< 0 || chk_upper < 0 ||  (chk_num < 0 && chk_special< 0 ) ){
            alert("비밀번호는 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.");
            fm.p_pwd.value="";
            fm.p_pwd.focus();
            return;
        }
    }

    if( fm.p_pwd.value == fm.p_userid.value){
        alert("아이디와 동일한 비밀번호는 사용 하실 수 없습니다.");
        fm.p_pwd.value="";
        fm.p_pwd.focus();return;
    }

    if( (fm.p_pwd_re.value) == ""){
        alert("비밀번호확인을 입력하세요");
        fm.p_pwd_re.focus();return;
    }

    if(fm.p_pwd.value != fm.p_pwd_re.value){
        alert("비밀번호가 다르게 입력되었습니다.");
        fm.p_pwd.value;
        fm.p_pwd_re.value ="";
        fm.p_pwd.focus();
        return;
    }

   if( (fm.p_hometel1.value) == "" ){
        alert("전화번호를 입력하세요");
        fm.p_hometel1.focus(); return;
    }else if( (fm.p_hometel2.value) == "" || fm.p_hometel2.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel2.focus(); return;
    }else if( (fm.p_hometel3.value) == "" || fm.p_hometel3.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel3.focus(); return;
    }

    if( (fm.p_handphone1.value) == "" ){
        alert("핸드폰 번호를 입력하세요");
        fm.p_handphone1.focus(); return;
    }else if( (fm.p_handphone2.value) == "" || fm.p_handphone2.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone2.focus(); return;
    }else if( (fm.p_handphone3.value) == "" || fm.p_handphone3.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone3.focus(); return;
    }

    // 주소 (둘중하나만 입력)
    //var v_post = fm.p_post1.value + fm.p_post2.value;

    //if( v_post == "" ){
    //    alert("우편번호를 입력하세요");
    //    fm.p_post1.focus();	return;
    //}

    if( (fm.p_home_addr1.value) == "" ){
        alert("주소를 입력하세요");
        fm.p_home_addr1.focus();	return;
    }//else if( (fm.p_home_addr2.value) == "" ){
     //   alert("주소를 입력하세요");
     //   fm.p_home_addr2.focus();	return;
    //}
    // -->

    if (fm.p_email1.value =="" || fm.p_email2.value == ""){
        alert("Email 주소를 바르게 입력하세요");
        fm.p_email1.focus();return;
    }

    /*
   // 이메일
    if(isValInput(fm.p_email, "이메일", 50, "U") == false)
    {
        return false;
    }
    */

    // e-mail check
    if(is_mail(v_email) == false)
    {
        alert("정확한 E-Mail을 입력하십시오.");
        fm.p_email1.focus();
        return;
    }

    if( fm.p_jikup.value == "" ){
		alert("직업을 선택하십시오.");
		return;
    }

    if( fm.p_workfieldcd.value == "" ){
		alert("종사분야를 선택하십시오.");
		return;
    }

    //if( fm.p_compnm.value == "" ){
	//	alert("소속을 입력하세요.");
	//	return;
    //}

    //if( fm.p_registerMotive.value == "" ){
	//	alert("가입동기를 선태하십시오.");
	//	return;
    //}

    if( fm.p_registerRoute.value == "" ){
		alert("가입경로를 선택하십시오.");
		return;
    }
    
    //alert( fm.p_sex.value. + '/' +  )

    // 가입
    document.form1.target="_self";
    document.form1.action= https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.p_handphone.value = v_handphone;
    document.form1.p_hometel.value = v_hometel;
    document.form1.p_email.value = v_email;
    document.form1.p_process.value="JoinOk";
    document.form1.submit();
}

//-------------------------------------------------------
// 이메일 및 이메일형태 ID검사
//-------------------------------------------------------
function is_mail(strEmail)
{
    /** 금지사항
     - @가 2개이상
     - .이 붙어서 나오는 경우
     -  @.나  .@이 존재하는 경우
     - 맨처음이.인 경우 **/
    var regDoNot = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;

    /** 필수사항
     - @이전에 하나이상의 문자가 있어야 함
     - @가 하나있어야 함
     - Domain명에 .이 하나 이상 있어야 함
     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
    var regMust = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/;

    if ( !regDoNot.test(strEmail) && regMust.test(strEmail) )
    {
        return true;
    }
    else
    {
        return false;
    }
}

function emailChange() {
    var r=$("[name='p_email3']").val();
    $("[name='p_email2']").val(r);
}

function workfieldChange() {
    if (document.form1.p_workfieldcd.value == "99") {
        document.getElementById('divWork').innerHTML = "<input name='p_workfieldnm' type='text' class='inp_txt_01' id='p_workfieldnm' size='30' maxlength='15' />";
        document.form1.p_workfieldnm.value="";
    } else {
        document.getElementById('divWork').innerHTML="";
    }
}

function checkYN()
{
    var r=$("[name='p_ismailing']").val();
    if(r=="Y")
        $("[name='p_ismailing']").val("N");
    else
        $("[name='p_ismailing']").val("Y");
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" action="">
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno1"	value="<%=v_resno1 %>" />
	<input type="hidden"	name="p_resno2"	value="<%=v_resno2 %>" />
	<input type="hidden"	name="p_resno"	value="<%=v_resno %>" />
	<input type="hidden"	name="p_sex"	value="<%=v_sex %>"/>
	<input type="hidden"	name="p_handphone" />
	<input type="hidden"	name="p_hometel" />
	<input type="hidden"	name="p_email" />
	<input type="hidden"	name="p_postgubun" value=""/>
	<input type="hidden"	name="p_chk" />
	<input type="hidden"	name="p_dupinfo" value="<%=box.getSession("dupinfo")%>" />
	<input type="hidden"	name="p_conninfo" value="<%=box.getSession("conninfo")%>" />
	<%if(!box.getString("p_username").equals("")){ %>
	<input type="hidden"	name="p_kor_name" value="<%=v_username%>" />
	<%}else if(i_irtn.equals("1")){ %>
	<input type="hidden"	name="p_kor_name" value="<%=i_username%>" />
	<%}else if(nc_cert.equals("Y")){ %>
	<input type="hidden"	name="p_kor_name" value="<%=nc_username%>" />
	<%}
	if(i_irtn.equals("1")){ %>    
	<input type="hidden"	name="p_vnumber" value="<%=i_vnumber%>" />
	<%}%>
	<input type="hidden"	name="pp_username" value="<%=vv_username%>" />
	<input type="hidden"	name="pp_email" value="<%=vv_email1%>@<%=vv_email2 %>" />
	<input type="hidden"	name="p_safeid" value="<%=nc_safeid%>" />
	<input type="hidden"	name="gubun" />    
	<input type="hidden"	name="menuid" />
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_join.gif" alt="회원가입"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>회원가입</u></td>
			</tr>
		</table>
		<table class="joinform" cellpadding="0" cellspacing="0">
			<colgroup><col width="50%"><col width="*"></colgroup>
			<tr>
				<td>
					<img src="/images/2012/sub/page0/join_step03.gif" alt="정보입력">
					<div class="persnaltip"><h6>회원정보는 개인정보 취급방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지 않습니다. <a href="page0_persnal.html" tabindex="171" title="개인정보처리방침으로 이동합니다"><u>개인정보처리방침</u></a></h6></div>
					<table class="required" cellpadding="0" cellspacing="0">
						<tr>
							<td class="alignL"><img src="/images/2012/sub/page0/subject_join_input01.gif" alt="로그인 정보입력"></td>
							<td class="alignR"><img src="/images/2012/common/icon_check.gif" alt="필수입력사항"> 필수입력사항</td>
						</tr>
					</table>
					<div class="logininfobox">
						<table class="logininfo" cellpadding="0" cellspacing="0">
							<colgroup><col width="120"><col width="*"></colgroup>
							<%if(!vv_username.equals("")){ %>
	          	<tr>
	              <th>보호자 성명</td>              
	              <td><%=vv_username %></td>             
	            </tr>
	            <tr>
	              <th>보호자 이메일</th>              
	              <td><%=vv_email1 %>@<%=vv_email2 %></td>             
	            </tr>
	            <tr>
	              <th>성명</th>
	              <%if(!box.getString("p_username").equals("")){ %>
	              <td><%=v_username%></td>
	             <%}else if(i_irtn.equals("1")){ %>
	             <td><%=i_username%></td>
	             <%} %>
	            </tr>
	            <%}else{ %>
	            <tr>
	              <th>성명</td>
	              <%if(!box.getString("p_username").equals("")){ %>
								<td><%=v_username%></td>
								<%}else if(i_irtn.equals("1")){//아이핀 %>
								<td><%=i_username%></td>
								<%}else if(nc_cert.equals("Y")){//안심실명 %>
								<td><%=nc_username%></td>
								<%} %>
	            </tr>
	            <%} %>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">아이디</th>
								<td>
									<table class="idsearch" cellpadding="0" cellspacing="0">
										<colgroup><col width="180"><col width="*"></colgroup>
										<tr>
											<td><input type="text" tabindex="172" name="p_userid" id="txtfield1" onfocus="choicefield('1');" class="txt" title="아이디를 입력해주세요"></td>
											<td><button type="button" tabindex="173" onclick="existsId();" class="checkbtn" title="좌측 입력란에 희망하는 아이디 입력 후 이 버튼을 클릭해주세요">ID 중복확인</button><span id="div_Area" style="width:200px;margin-left:10px"></span></p></td>
										</tr>
										<tr>
											<td colspan="2" class="idmsg"><h6>아이디는 <b>6~12자의 영숫자 조합</b>으로 만들 수 있습니다.</h6></td>
										</tr>										
									</table>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">비밀번호</th>
								<td>
									<input type="password" tabindex="174" name="p_pwd" id="txtfield2" onfocus="choicefield('2');" class="txt" title="지정하실 비밀번호를 입력해주세요">
									<div class="pointmsg">
										<h6>영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.</h6>
										<h6>생일번호,주민등록번호 등 알기 쉬운 숫자는 피해주세요.</h6>
									</div>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">비밀번호 확인</th>
								<td><input type="password" tabindex="175" name="p_pwd_re" id="txtfield3" onfocus="choicefield('3');" class="txt" title="지정하실 비밀번호를 한번 더 입력해주세요"></td>
							</tr>
						</table>
						<br><br>
						<div class="rightinput">
						<table class="required" cellpadding="0" cellspacing="0">
							<tr>
								<td class="alignL"><img src="/images/2012/sub/page0/subject_join_input02.gif" alt="회원 상세정보입력"></td>
								<td class="alignR"><img src="/images/2012/common/icon_check.gif" alt="필수입력사항"> 필수입력사항</td>
							</tr>
						</table>
					</div>
					<div class="userdetailbox">
						<table class="userdetail" cellpadding="0" cellspacing="0">
							<colgroup><col width="120"><col width="*"></colgroup>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">성별</th>
								<td>
									<table class="layoutdefault" cellpadding="0" cellspacing="0">
										<tr>
										<%
										if(!box.getString("p_resno2").equals("")){
											if(v_sex.equals("1")){ 
										%>
								   		<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1"  checked disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2"  disabled><label for="female" class="label">여성</label></td>
										<%
											} else if( v_sex.equals("2")){ 
										%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1" disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2" checked disabled><label for="female" class="label">여성</label></td>
										<% 
											} 
										} else if(i_irtn.equals("1")){
											if(i_sex.equals("1")){ %>
								   		<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1"  checked disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2"  disabled><label for="female" class="label">여성</label></td>
										<%
											} else if(i_sex.equals("0")){ %>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1" disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2" checked disabled><label for="female" class="label">여성</label></td>
										<%
											}
										} else if(nc_cert.equals("Y"))  {
										 	if(nc_gender.equals("M")){%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1"  checked disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2"  disabled><label for="female" class="label">여성</label></td>		
										<%
											} else if(nc_gender.equals("F")){ 
										%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1" disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2" checked disabled><label for="female" class="label">여성</label></td>
										 		
										<% 
											} else if(nc_gender.equals("A")){
										%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1"  checked disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2"  disabled><label for="female" class="label">여성</label></td>
										<%
											} else if(nc_gender.equals("B")){
										%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1" disabled><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2" checked disabled><label for="female" class="label">여성</label></td>
										<%
											} 
										} else {
										%>
											<td><input type="radio" tabindex="176" id="male" name="p_sex" class="radio" title="남성" value="1"><label for="male" class="label">남성</label></td>
											<td style="width:20px;"></td>
											<td><input type="radio" tabindex="176" id="female" name="p_sex" class="radio" title="여성" value="2"><label for="female" class="label">여성</label></td>
										<% } %>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">연락처</th>
								<td>
									<table class="layoutdefault" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<input name="p_memberyear"  type="hidden" class="inp_txt_01" id="p_memberyear"  value="<%=v_memberyear %>"  size="5" maxlength="4" readonly />
								 			 	<input name="p_membermonth" type="hidden" class="inp_txt_01" id="p_membermonth" value="<%=v_membermonth %>" size="5" maxlength="2" readonly />
								 			 	<input name="p_memberday"   type="hidden" class="inp_txt_01" id="p_memberday"   value="<%=v_memberday %>"   size="5" maxlength="2" readonly />
												<select class="select" tabindex="177" name="p_hometel1" id="txtfield4" onfocus="choicefield('4');">
													<option>지역번호 선택</option>
													<option value="070">070</option>
													<option value="02">02</option>
													<option value="031">031</option>
													<option value="032">032</option>
													<option value="033">033</option>
													<option value="041">041</option>
													<option value="042">042</option>
													<option value="043">043</option>
													<option value="051">051</option>
													<option value="052">052</option>
													<option value="053">053</option>
													<option value="054">054</option>
													<option value="055">055</option>
													<option value="061">061</option>
													<option value="062">062</option>
													<option value="063">063</option>
													<option value="064">064</option>
												</select>
											</td>
											<td>&nbsp;-&nbsp;<input type="text" tabindex="178" name="p_hometel2" id="txtfield5" onfocus="choicefield('5');" class="txt" style="width:36px;" maxlength="4"></td>
											<td>&nbsp;-&nbsp;<input type="text" tabindex="179" name="p_hometel3" id="txtfield6" onfocus="choicefield('6');" class="txt" style="width:36px;" maxlength="4"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">모바일</th>
								<td>
									<table class="layoutdefault" cellpadding="0" cellspacing="0">
										<tr>
											<td>
												<select class="select" tabindex="180" name="p_handphone1" id="txtfield7" onfocus="choicefield('7');">
													<option>이동번호 선택</option>
													<option value="010">010</option>
			                    <option value="011">011</option>
			                    <option value="016">016</option>
			                    <option value="017">017</option>
			                    <option value="018">018</option>
			                    <option value="019">019</option>
												</select>
											</td>
											<td>&nbsp;-&nbsp;<input type="text" tabindex="181" name="p_handphone2" id="txtfield8" onfocus="choicefield('8');" class="txt" style="width:36px;" maxlength="4"></td>
											<td>&nbsp;-&nbsp;<input type="text" tabindex="182" name="p_handphone3" id="txtfield9" onfocus="choicefield('9');" class="txt" style="width:36px;" maxlength="4"></td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">지역</th>
								<td>
									<select class="select" tabindex="183" name="p_home_addr1" id="txtfield10" onfocus="choicefield('10');">
										<option>거주지역 선택</option>
										<option value="강원">강원</option>
										<option value="경기">경기</option>
										<option value="경남">경남</option>
										<option value="경북">경북</option>
										<option value="광주">광주</option>
										<option value="대구">대구</option>
										<option value="대전">대전</option>
										<option value="부산">부산</option>
										<option selected value="서울">서울</option>
										<option value="울산">울산</option>
										<option value="인천">인천</option>
										<option value="전남">전남</option>
										<option value="전북">전북</option>
										<option value="제주">제주</option>
										<option value="충남">충남</option>
										<option value="충북">충북</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">이메일</th>
								<td>
									<table class="layoutdefault" cellpadding="0" cellspacing="0">
										<tr>
											<td><input type="text" tabindex="184" name="p_email1" id="p_email1" onfocus="choicefield('11');" class="txt" style="width:150px;"></td>
											<td>@<input type="text" tabindex="185" name="p_email2" id="p_email2" onfocus="choicefield('12');" class="txt" style="width:200px;"></td>
											<td>&nbsp;
												<select class="select" tabindex="186" name="p_email3" id="p_email3" onfocus="choicefield('13');" onchange="emailChange();">
													<option>직접입력</option>
													<option value="naver.com" >naver.com</option>
													<option value="chol.com" >chol.com</option>
													<option value="dreamwiz.com" >dreamwiz.com</option>
													<option value="empal.com" >empal.com</option>
													<option value="freechal.com" >freechal.com</option>
													<option value="gmail.com" >gmail.com</option>
													<option value="hanafos.com" >hanafos.com</option>
													<option value="hanmail.net" >hanmail.net</option>
													<option value="hanmir.com" >hanmir.com</option>
													<option value="hitel.net" >hitel.net</option>
													<option value="hotmail.com" >hotmail.com</option>
													<option value="korea.com" >korea.com</option>
													<option value="lycos.co.kr" >lycos.co.kr</option>
													<option value="nate.com" >nate.com</option>
													<option value="netian.com" >netian.com</option>
													<option value="paran.com" >paran.com</option>
													<option value="yahoo.com" >yahoo.com</option>
													<option value="yahoo.co.kr" >yahoo.co.kr</option>
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">직업</th>
								<td>
									<select class="select" tabindex="187" name="p_jikup" id="txtfield14" onfocus="choicefield('14');">										
										<option value="">직업 선택</option>
										<option value="01">고등학생 이하</option>
										<option value="02">대학생 (대학원)</option>
										<option value="03">공무직 (공직)</option>
										<option value="04">공공기관</option>
										<option value="05">프리랜서</option>
										<option value="06">예비취업자</option>
										<option value="07">회사원</option>
										<option value="08">주부</option>
										<option value="09">군인</option>
										<option value="10">교직원</option>
										<option value="11">의료인</option>
										<option value="12">법조인</option>
										<option value="13">언론인</option>
										<option value="14">종교인</option>
										<option value="15">연예인</option>
										<option value="16">체육인</option>
										<option value="17">교수</option>
										<option value="18">강사</option>
										<option value="19">자영업</option>
										<option value="99">기타</option> 
									</select>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">종사분야</th>
								<td>
									<select class="select" tabindex="188" name="p_workfieldcd" id="txtfield15" onfocus="choicefield('15');">
										<option value="">분야 선택</option>
										<option value="01">게임</option>
										<option value="02">만화</option>
										<option value="03">모바일</option>
										<option value="04">방송</option>
										<option value="05">애니메이션</option>
										<option value="06">에듀테인먼트</option>
										<option value="07">영화</option>
										<option value="08">음악</option>
										<option value="09">캐릭터</option>
										<option value="10">출판</option>
										<option value="11">인쇄</option>
										<option value="12">영상</option>
										<option value="13">디자인</option>
										<option value="14">광고</option>
										<option value="15">공연</option>
										<option value="16">미술품</option>
										<option value="17">공예품</option>
										<option value="18">패션</option>
										<option value="19">전시</option>
										<option value="20">소프트웨어</option>
										<option value="21">하드웨어</option>
										<option value="22">신문</option>
										<option value="99">기타</option> 
									</select>
								</td>
							</tr>
							<tr>
								<th><img src="/images/2012/common/icon_check.gif" align="absmiddle" alt="필수입력사항">가입경로</th>
								<td>
									<select class="select" tabindex="189" name="p_registerRoute" id="txtfield17" onfocus="choicefield('17');">
										<option value="">가입경로 선택</option>
										<option value="01">검색엔진</option>
										<option value="09">박람회</option>
										<option value="04">배너</option>
										<option value="02">신문기사</option>
										<!-- <option value="06">열린강의 행사장</option>-->
										<option value="03">이메일 광고</option>
										<option value="07">지인추천</option>
										<option value="05">학교/사내교육</option>
										<option value="08">기타</option>
										
									</select> 
								</td>
								</tr>
							<tr>
								<th>정보수신 여부</th>
								<td><input type="checkbox" tabindex="190" name="p_ismailing" id="mailsms" class="radio" title="SMS" value="Y" onclick="checkYN();" checked><label for="mailsms" class="label">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</label></td>
							</tr>
						</table>
					</div>
					<br><br>
					<div class="rightinput">
						<table class="required" cellpadding="0" cellspacing="0">
							<tr>
								<td class="alignL"><img src="/images/2012/sub/page0/subject_join_input03.gif" alt="부가정보입력"></td>
								<td class="alignR"></td>
							</tr>
						</table>
					</div>
					<div class="userdetailbox">
						<table class="userdetail" cellpadding="0" cellspacing="0">
							<colgroup><col width="120"><col width="*"></colgroup>
							<tr>
								<th>가입동기</th>
								<td>
									<select class="select" tabindex="191" name="p_registerMotive" id="txtfield16" onfocus="choicefield('16');">
										<option value="">가입동기 선택</option>
										<option value="01">취업</option>
										<option value="02">재취업</option>
										<option value="03">자기개발</option>
										<option value="04">창업</option>
										<option value="05">재직중 재교육</option>
										<option value="06">기타</option>
									</select> 
								</td>
							</tr>
							<tr>
								<th>추천인 아이디</th>
								<td><input type="text" tabindex="192" name="p_recommendid" id="txtfield18" onfocus="choicefield('18');" class="txt" style="width:160px;"></td>
							</tr>
						</table>
					</div>
					<table class="joinfooter" cellspacing="0" cellpadding="0">
						<colgroup><col width="50%"><col width="*"></colgroup>
						<tr>
							<td class="alignR"><a href="javascript:Submit_Check();" tabindex="193" title="위 기입란에 알맞게 기입하셨다면 이 버튼을 눌러주세요."><img src="/images/2012/sub/page0/btn_join_confirm.gif" alt="확인" title="입력을 모두 했다면 클릭하여 가입을 완료하세요"></a></td>
							<td class="alignL"><a href="/servlet/controller.homepage.MainServlet" tabindex="194" title="취소하고 가입 이전 화면으로 돌아갑니다."><img src="/images/2012/sub/page0/btn_join_cancel.gif" alt="취소"></a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</form>
<!-- form 끝 -->

<script type='text/javascript' src='/script/jquery.password_strength.js'></script>
<script language="JavaScript" type="text/JavaScript">
    $('form1').attr('autocomplete', 'off');
    //$('#p_pwd').password_strength();
</script>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>