<%
//**********************************************************
//  1. 제      목: 회원가입_본인확인
//  2. 프로그램명 : zu_MemberJoin.jsp
//  3. 개      요: 회원가입_본인확인
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
<%@page import="Kisinfo.Check.IPIN2Client"%>

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) box = RequestManager.getBox(request);

    // 실명인증 관련
    //===========================================
    HttpSession s = request.getSession(true);
    s.putValue("NmChkSec","98u9iuhuyg87");
    //===========================================
    	
//   	<!-- 아이핀관련 시작 -->

	/********************************************************************************************************************************************
		NICE신용평가정보 Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		서비스명 : 가상주민번호서비스 (IPIN) 서비스
		페이지명 : 가상주민번호서비스 (IPIN) 호출 페이지
	*********************************************************************************************************************************************/
	
	String sSiteCode				= "B014";			// IPIN 서비스 사이트 코드		(NICE신용평가정보에서 발급한 사이트코드)
	String sSitePw					= "20727176";			// IPIN 서비스 사이트 패스워드	(NICE신용평가정보에서 발급한 사이트패스워드)
	
	String sEncData					= "";			// 암호화 된 데이타
	
	String sRtnMsg					= "";			// 처리결과 메세지
	
	
	
	/*
	┌ sReturnURL 변수에 대한 설명  ─────────────────────────────────────────────────────
		NICE신용평가정보 팝업에서 인증받은 사용자 정보를 암호화하여 귀사로 리턴합니다.
		따라서 암호화된 결과 데이타를 리턴받으실 URL 정의해 주세요.
		
		* URL 은 http 부터 입력해 주셔야하며, 외부에서도 접속이 유효한 정보여야 합니다.
		* 당사에서 배포해드린 샘플페이지 중, ipin_process.jsp 페이지가 사용자 정보를 리턴받는 예제 페이지입니다.
		
		아래는 URL 예제이며, 귀사의 서비스 도메인과 서버에 업로드 된 샘플페이지 위치에 따라 경로를 설정하시기 바랍니다.
		예 - http://www.test.co.kr/ipin_process.jsp, https://www.test.co.kr/ipin_process.jsp, https://test.co.kr/ipin_process.jsp
	└────────────────────────────────────────────────────────────────────
	*/
	
	String sReturnURL				= "http://edu.kocca.or.kr/ipin_process.jsp";
	//String sReturnURL				= "http://localhost:7001/ipin_process.jsp";
	//"http://localhost:7001/ipin_process.jsp";
	//"http://edu.kocca.or.kr/ipin_process.jsp";
	
	/*
	┌ sCPRequest 변수에 대한 설명  ─────────────────────────────────────────────────────
		[CP 요청번호]로 귀사에서 데이타를 임의로 정의하거나, 당사에서 배포된 모듈로 데이타를 생성할 수 있습니다.
		
		CP 요청번호는 인증 완료 후, 암호화된 결과 데이타에 함께 제공되며
		데이타 위변조 방지 및 특정 사용자가 요청한 것임을 확인하기 위한 목적으로 이용하실 수 있습니다.
		
		따라서 귀사의 프로세스에 응용하여 이용할 수 있는 데이타이기에, 필수값은 아닙니다.
	└────────────────────────────────────────────────────────────────────
	*/
	String sCPRequest				= "";
	
	// 객체 생성
	IPIN2Client pClient = new IPIN2Client();
	
	
	// 앞서 설명드린 바와같이, CP 요청번호는 배포된 모듈을 통해 아래와 같이 생성할 수 있습니다.
	sCPRequest = pClient.getRequestNO(sSiteCode);
	
	// CP 요청번호를 세션에 저장합니다.
	// 현재 예제로 저장한 세션은 ipin_result.jsp 페이지에서 데이타 위변조 방지를 위해 확인하기 위함입니다.
	// 필수사항은 아니며, 보안을 위한 권고사항입니다.
	session.setAttribute("CPREQUEST" , sCPRequest);
	
	
	// Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
	int iRtn = pClient.fnRequest(sSiteCode, sSitePw, sCPRequest, sReturnURL);
	
	// Method 결과값에 따른 처리사항
	if (iRtn == 0)
	{
	
		// fnRequest 함수 처리시 업체정보를 암호화한 데이터를 추출합니다.
		// 추출된 암호화된 데이타는 당사 팝업 요청시, 함께 보내주셔야 합니다.
		sEncData = pClient.getCipherData();
		
		sRtnMsg = "정상 처리되었습니다.";
	
	}
	else if (iRtn == -1 || iRtn == -2)
	{
		sRtnMsg =	"배포해 드린 서비스 모듈 중, 귀사 서버환경에 맞는 모듈을 이용해 주시기 바랍니다.<BR>" +
					"귀사 서버환경에 맞는 모듈이 없다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
	}
	else if (iRtn == -9)
	{
		sRtnMsg = "입력값 오류 : fnRequest 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
	}
	else
	{
		sRtnMsg = "iRtn 값 확인 후, NICE신용평가정보 개발 담당자에게 문의해 주세요.";
	}

//<!-- 아이피관련 끝 -->
%>

<%@ page import = "com.credu.homepage.LoginBean" %>
<%@ page import="java.net.InetAddress" %>
<%
	InetAddress inet= InetAddress.getLocalHost();
	String ipSplit = inet.getHostAddress();
	String [] ipnum=ipSplit.replace(".","/").split("/");

	String topmenu = box.getString("topmenu");
	if ((topmenu == null) || (topmenu.equals("")) ) topmenu = "1";
	String submenu1 = box.getString("submenu");
	
	String notSelected = "ratemenu";
	String selected = "ratemenuon";
	int zindex = 0;
	
	String topWarp_userid = box.getSession("userid");
  	String topWarp_usernm = box.getSession("name");
  	String topWarp_gadmin = box.getSession("gadmin");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta name="description" content="한국콘텐츠아카데미" />
<meta name="keywords" content="kocca, 한국콘텐츠아카데미" />
<title>kocca 한국콘텐츠아카데미(<%=ipnum[3]%>)</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>

<script type="text/javascript" src="/js/2013/caption.js"></script>
<script type="text/javascript" src="/js/2013/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="/js/2013/jquery.selectbox-0.6.1.js"></script>
<script type="text/javascript" src="/js/2013/jquery.cycle2.min.js"></script>
<script type="text/javascript" src="/js/2013/common.js"></script>
<script type="text/javascript" src="/js/2013/lib.js"></script>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" language="JavaScript" src="/script/mainscript.js"></script>
<style type="text/css" media="screen">@import "/css/2013/common.css";</style>
<style type="text/css" media="screen">@import "/css/2013/layout.css";</style>
<style type="text/css" media="screen">@import "/css/2013/sub.css";</style>
<script>
/* 패스워드 엔터키 체크 */
function password_enter(e) {
  if (e.keyCode =='13'){  login_main();  }
}
</script>

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
window.name = "Parent_window";
// 이름과 주민번호 공백 없애기
function TrimString(v_String){
   /*왼쪽 트림 */
   len = v_String.length;
   for(i=0;i<len;i++){
      if(v_String.substring(0,1) == " ") v_String = v_String.substring(1);
      else	  break;
   }
   /* 오른쪽 트림*/
   len = v_String.length;
   for(i=len;i>0;i--){
      if(v_String.substring(i-1) == " ") v_String = v_String.substring(0,i-1);
      else	  break;
   }
	return v_String;
}

//자동이동
function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }

// 개인정보 입력화면으로
function memberJoinOK(){
	
	var parentName = document.form1.pname.value;
	var parentResno1 = document.form1.presno1.value;
	var parentResno2 = document.form1.presno2.value;
	var parentEmail1 = document.form1.pemail1.value;
	var parentEmail2 = document.form1.pemail2.value;
	var checkradio = document.form1.checkradio.value;
	var cName  = document.form1.pp_username.value;
	var cResno1 = document.form1.pp_resno1.value;
	var cResno2 = document.form1.pp_resno2.value;

	
		//alert("14미만");
		if(document.form1.pagree.checked == false){
			alert("법정대리인의 동의를 해주세요");
			return;
		}
		if(!TrimString(parentName)){
			alert("보호자 이름을 입력하세요");
			document.form1.pname.focus();
			return;
		}
		if(!TrimString(parentResno1) || document.form1.presno1.value.length < 6){
			alert("주민등록번호를 바르게 입력하세요");
			document.form1.presno1.value = "";
			document.form1.presno1.focus();
			return;
		}
		if(!TrimString(parentResno2) || document.form1.presno2.value.length < 7){
			alert("주민등록번호를 바르게 입력하세요");
			document.form1.presno2.value = "";
			document.form1.presno2.focus();
			return;
		}
		if(!TrimString(parentEmail1)){
			alert("보호자 이메일을 바르게 입력하세요");
			document.form1.pemail1.focus();
			return;
		}
		if(!TrimString(parentEmail2)){
			alert("보호자 이메일을 바르게 입력하세요");
			document.form1.pemail2.focus();
			return;
		}
		if(!TrimString(cName)){
			alert("이름을 입력하세요");
			document.form1.pemail2.focus();
			return;
		}
		if(!TrimString(cResno1)){
			alert("주민등록번호를 바르게 입력하세요");
			document.form1.pemail2.focus();
			return;
		}
		if(!TrimString(cResno2)){
			alert("주민등록번호를 바르게 입력하세요");
			document.form1.pemail2.focus();
			return;
		}

		if(!regcheck(cResno1+cResno2))
	    {
	        $("[name='pp_resno1']").val("");
	        $("[name='pp_resno2']").val("");
	        $("[name='pp_resno1']").focus();
	        return;
	    }
		if(!regcheck(parentResno1+parentResno2))
	    {
	        $("[name='parentResno1']").val("");
	        $("[name='parentResno2']").val("");
	        $("[name='parentResno1']").focus();
	        return;
	    }
		
		// 회원인지 확인		
	    document.form1.pname.value = parentName;
	    document.form1.presno1.value   = parentResno1;
	    document.form1.presno2.value   = parentResno2;
	    document.form1.pemail1.value   = parentEmail1;
	    document.form1.pemail2.value   = parentEmail2;
	    
	    document.form1.p_username.value = cName;
	    document.form1.p_resno1.value   = cResno1;
	    document.form1.p_resno2.value   = cResno2;
	    document.form1.action = "/certi/nc_p.jsp";                                 //실명인증 체크 페이지
	    document.form1.checkflag.value   = "true";
	    
	    document.form1.submit(); 
		
}


// 숫자만 들어가게 만든다.
function onlyNumber()
{
    if(((event.keyCode<48)||(event.keyCode>57))) {
        event.returnValue=false;
    }
}

function regcheck(num){
   var month='(0[1-9]|1[0-2])';
   var date='(0[1-9]|[1-2][0-9]|3[0-1])';
   var regMonth = new RegExp(month);
   var regDate = new RegExp(date);

   if(!regMonth.test(num.substring(2,4))) {
      alert("정상적인 주민등록번호가 아닙니다");
      return false;
   }

   if(!regDate.test(num.substring(4,6))) {
      alert("정상적인 주민등록번호가 아닙니다");
      return false;
   }

   var tab=[2,3,4,5,6,7,8,9,2,3,4,5];
   var sum=0,i=0;

   for (i=0;i<12;i++) sum+=parseInt(num.charAt(i))*tab[i];

    if (((11-(sum%11))%10!=num.charAt(12))) {
        alert("정상적인 주민등록번호가 아닙니다");
        return false;
    }

   return true;
}

	function chageImg(idx){
		
		if(parseInt(idx)==1){
			document.getElementById("image991").src = "/images/portal/homepage_renewal/member/btn_namechk_on.gif";					
			document.getElementById("image992").src = "/images/portal/homepage_renewal/member/btn_ipin_off.gif";					
			document.getElementById("tab1").style.display = "block";
			document.getElementById("tab2").style.display = "none";
			}
		else if(parseInt(idx)==2){
		document.getElementById("image991").src = "/images/portal/homepage_renewal/member/btn_namechk_off.gif";		
		document.getElementById("image992").src = "/images/portal/homepage_renewal/member/btn_ipin_on.gif";		
		document.getElementById("tab1").style.display = "none";
		document.getElementById("tab2").style.display = "block";
		}
	}

	// 아이핀 팝업 띄우기	
	
	function fnPopup(){
		window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_ipin.target = "popupIPIN2";
		document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
		document.form_ipin.submit();
	}

	function changeJoinType(idx){
		if(parseInt(idx)==1){					
			document.getElementById("joinType1").style.display = "block";
			document.getElementById("joinType2").style.display = "none";
			document.form1.pname.value="";
			document.form1.presno1.value="";
			document.form1.presno2.value="";
			document.form1.pemail1.value="";
			document.form1.pemail2.value="";
			form1.pp_username.value="";
			form1.pp_resno1.value="";
			form1.pp_resno2.value="";
			document.form1.pagree.checked = false;
			document.form1.checkradio.value = "";
		}
		else if(parseInt(idx)==2){	
			document.getElementById("joinType1").style.display = "none";
			document.getElementById("joinType2").style.display = "block";
			document.form1.checkradio.value = "14";
		}
	}

	function emailChange() {
	    var r=$("[name='pemail3']").val();
	    $("[name='pemail2']").val(r);
	}

	// 실명인증 팝업 띄우기
	function ncPopup(){
		window.open('/certi/certnc_main.jsp','nc','width=410, height=555');
	}

	// 아이핀팝업띄우기
	function ipinPopup(){
		window.open('', 'popupIPIN2', 'width=450, height=550, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		document.form_ipin.target = "popupIPIN2";		
		document.form_ipin.action = "/certi/ipin_main.jsp";
		document.form_ipin.submit();
	}

	function joinyearcheck(obj){
		if (obj == "on") {
			document.getElementById("year14min").style.display = "block";
			document.getElementById("year14max").style.display = "none";
		} else {
			document.getElementById("year14min").style.display = "none";
			document.getElementById("year14max").style.display = "block";
		}
	}

</script>

<!-- 아이핀 스크립트 종료 -->

<!-- 스크립트영역종료 -->

<!-- form 시작 -->

<!-- 아이핀 form 시작 -->
</head>
<body style="background-image:url('');">
<form name="topdefaultForm" method="post" action="/" id="topdefaultForm" >
  <input type='hidden' name='p_process'   value="" />
  <input type='hidden' name='gubun'       value='0' />
  <input type='hidden' name='menuid'      value='' />
  <input type='hidden' name='url'         value='' />
  <input type='hidden' name='p_cmuno'     value='' />
  <input type='hidden' name='searchClass' value='' />
  <input type='hidden' name='p_auth'      value='' />
  <input type='hidden' name='p_topuserid' value='' />
  <input type='hidden' name='p_toppwd'    value='' />
  <input type='hidden' name='p_area'    value='' />
  <input type='hidden' name='p_lsearchtext'    value='' />
  <input type="submit" class="close" title="실행" />
</form>
<!-- 가상주민번호 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
<form name="form_ipin" method="post" action="/">
	<input type="hidden" name="m" value="pubmain" />						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
    <input type="hidden" name="enc_data" value="<%= sEncData %>" />		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
    
    <!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다.
    	 해당 파라미터는 추가하실 수 없습니다. -->
    <input type="hidden" name="param_r1" value="" />
    <input type="hidden" name="param_r2" value="" />
    <input type="hidden" name="param_r3" value="" />
</form>

<form name="vnoform" method="post" action="">
	<input type="hidden" name="enc_data" />								<!-- 인증받은 사용자 정보 암호화 데이타입니다. -->
	
	<!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다.
    	 해당 파라미터는 추가하실 수 없습니다. -->    
    <input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno" value="" />
    <input type="hidden"	name="checkflag" value="" />
    <input type="hidden"	name="checkradio" value="" />  
</form>

<!-- 아이핀 form 끝 -->

<form name="form1"  action="" method="post" action="">
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno" value="" />
    <input type="hidden"	name="checkflag" value="" />
    <input type="hidden"	name="checkradio" value="" />   
    
    <input type="hidden"	name="p_username" value="" />
    <input type="hidden"	name="p_resno1" value="" />
    <input type="hidden"	name="p_resno2" value="" />
        
    <!-- 아이핀관련 시작 -->
    <input type="hidden" name="enc_data" />
    <!-- 아이핀관련 끝 -->
    <!-- 안심실명인증관련 시작-->
    <input type="hidden" name="enc_data" value="" />
 	<input type="hidden" name="nc_safeid" value="" />
 	<input type="hidden" name="nc_username" value="" />
 	<input type="hidden" name="nc_jumindate" value="" />
 	<input type="hidden" name="nc_gender" value="" />
 	<input type="hidden" name="nc_cert" value="" />
 	<!-- 안심실명인증관련 시작-->
	<div id="container">
		<!-- 컨텐츠 영역 시작 -->
		<div id="contents">
		
			<h2 class="subTit"><img src="/images/2013/support/join_title.gif" alt="회원가입" /></h2>
			
			<div id="subCont">
				<div class="stepimgBox">
					<img src="/images/2013/support/private_img2.gif" alt="본인확인 단계" />
				</div>
				<div class="joinBox">
					<ul class="tab">
						<li><a href="#"><img src="/images/2013/support/tabjoin1_off.jpg" alt="실명확인" /></a></li>
						<li><a href="#"><img src="/images/2013/support/tabjoin2_off.jpg" alt="공공 i-PIN 인증" /></a></li>
					</ul>
					<div class="cont">
						<p>2006년 9월 24일 부터 개정되는 주민등록법에 의해 타인의 주민번호로 
						온라인 회원 가입을 하는 등 타인의 주민등록번호를 부정 사용하게 되면 
						3년 이하의 징역 또는 1천만원 이하의 벌금이 부과될 수 있습니다.</p>
						<ul>
							<li><input type="radio" name="peopletype" id="rd1" onclick="joinyearcheck('off')" checked /> <label for="rd1">일반회원(14세 이상)</label></li>
							<li><input type="radio" name="peopletype" id="rd2" onclick="joinyearcheck('on')" /> <label for="rd2">일반회원(14세 이하)</label></li>
						</ul>
						<div id="year14max" class="btnArea wd2">
							<button type="button" class="btnText" onclick="javascript:ncPopup();"><span>안심실명확인</span></button>
						</div>
						
						<div id="year14min" style="display: none;">
							<p class="txt1">14세 미만 고객님은 사이버콘텐츠아카데미 회원가입을 위해 보호자<br />동의가 필요합니다.</p>
							<p class="txt2">본 사이트 회원가입은 ‘정보통신망 이용촉진에 관한 법률’ 제 31조의<br />
							규정에 따라 만 14세 미만의 아동의 개인정보를 이용하는 경우 보호자 <br />
							또는 법정대리인의 동의가 있어야 회원가입이 가능합니다.</p>
	
							<div class="tableWrap9">
								<table class="dataTable9" summary="이 표는 보호자 성명,보호자 주민등록번호,보호자 이메일,회원 성명,회원 주민등록번호 내용으로 구성 되어 있습니다.">
									<caption>일반회원 14세 이상 표</caption>
									<colgroup>
										<col width="36%"/>
										<col width="*"/>
									</colgroup>
									<tbody>
									<tr>
										<td colspan="2" class="first"><input type="checkbox" name="pagree" value="1" id="agreen" class="checkBox"/><label for="ck1"> 자녀가 본 사이트 회원으로 등록하는대 동의합니다.</label></td>
									</tr>
									<tr>
										<th scope="row"><label for="dName">보호자 성명</label></th>
										<td><input type="text" name="pname" id="dName" class="inputText" size="18" /></td>
									</tr>
									<tr>
										<th scope="row"><label for="idnum">보호자 주민등록번호</label></th>
										<td><input type="text" name="presno1" id="idnum" class="inputText" size="6" title="주민번호 앞 6자리 입력"/> - <input type="password" name="presno2"  class="inputPw" size="7" title="주민번호 뒤 7자리 입력" /></td>
									</tr>
									<tr>
										<th scope="row"><label for="dEmail">보호자 이메일</label></th>
										<td>
											<input type="text" name="pemail1" id="dEmail" class="inputText" size="8" title="이메일 아이디 입력"/> @
											<input type="text" name="pemail2" class="inputText" size="8" title="이메일 주소 입력"/>
<!--											 <select class="select" title="이메일주소 선택"><option>직접입력</option></select>-->
										</td>
									</tr>
									<tr>
										<th scope="row"><label for="dName1">회원 성명</label></th>
										<td><input type="text" name="pp_username" id="dName1" class="inputText" size="18" /></td>
									</tr>
									<tr>
										<th scope="row"><label for="idnum2">회원 주민등록번호</label></th>
										<td><input type="text" name="pp_resno1" id="idnum2" class="inputText" size="6" title="주민번호 앞 6자리 입력"/> - <input type="password" name="pp_resno2"  class="inputPw" size="7" title="주민번호 뒤 7자리 입력" /></td>
									</tr>
									</tbody>
								</table>
							</div>
							<div class="btnArea wd2">
								<button type="button" class="btnText" onclick="javascript:memberJoinOK();"><span>안심실명확인</span></button>
							</div>
						</div>
					</div>
					
					<div class="cont" style="display:none;">
						<p>공공 아이핀(I-PIN)은 인터넷상의 개인식별번호를 의미하며,
						대면 확인이 어려운 인터넷에서 주민등록번호를 사용하지 않고 확인할 
						수 있는 수단입니다.</p> 
						<div class="btnArea btnOuter wd2">
							<button type="button" class="btnText" onclick="javascript:ipinPopup();"><span>공공(I-PIN) 인증</span></button>
						</div>
						<p>입력하신 정보는 본인확인용으로 사용되며, 회원가입이 완료되면<br />
						사용자 확인을 위해 (주민번호 등 중요정보는 암호화 처리)회원정보에<br />
						저장됩니다. 회원정보는 무단으로 공개되지 않으며, 개인정보보호방침<br />
						에 의해 보호받고 있습니다.</p>
					</div>
				</div>
				<!--//실명확인 -->

				<div class="joinBox_info">
					관련 법률에 따라 다른 사람의 주민등록번호를 도용하여<br />
					인터넷서비스에 가입하는 경우는 명백한 범죄행위로 <strong>3년<br />
					이하의 징역또는 1천만원 이하의 벌금</strong>에 처해지므로<br />
					반드시 본인 실명으로만 가입해 주시기 바랍니다.<br /><br />

					<p>우측에 위치하는 상단의 “<strong>실명확인</strong>”과 “<strong>공공 I-PIN인증</strong>” 
					두 가지 방법 중 한 가지를 선택하여 본인확인을 진행하여 
					주시기 바랍니다.</p>
				</div>
				<!--//joinBox_info -->
		
			</div>
			<!-- //#subCont -->
		
		</div>
		</div>
</form>
</body>
</html>
