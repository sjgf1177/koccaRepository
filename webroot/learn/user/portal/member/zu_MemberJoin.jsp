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

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<%if( box.getSession("tem_grcode").equals("N000001")) { %>
<link href="/css/portal/homepage_renewal/member.css" rel="stylesheet" type="text/css" />
<%}%>

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

	//var v_name  = document.form1.p_username.value;
	//var v_resno1 = document.form1.p_resno1.value;
	//var v_resno2 = document.form1.p_resno2.value;
	var parentName = document.form1.pname.value;
	var parentResno1 = document.form1.presno1.value;
	var parentResno2 = document.form1.presno2.value;
	var parentEmail1 = document.form1.pemail1.value;
	var parentEmail2 = document.form1.pemail2.value;
	var checkradio = document.form1.checkradio.value;
	var cName  = document.form1.pp_username.value;
	var cResno1 = document.form1.pp_resno1.value;
	var cResno2 = document.form1.pp_resno2.value;
	
	if(document.form1.checkradio.value.length>0){
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
	    //document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";    //실명인증 체크 하지 않음경우
	    document.form1.action = "/certi/nc_p.jsp";                                 //실명인증 체크 페이지
	    document.form1.checkflag.value   = "true";
	    
	    document.form1.submit(); 
		
	}else{


		/*if(!TrimString(v_name)){
		alert("이름을 입력하세요");
		document.form1.p_username.focus();
		return;
	}

	if(!TrimString(v_resno1) || document.form1.p_resno1.value.length < 6){
		alert("주민등록번호를 바르게 입력하세요");
		document.form1.p_resno1.value = "";
		document.form1.p_resno1.focus();
		return;
	}
	if(!TrimString(v_resno2) || document.form1.p_resno2.value.length < 7){
		alert("주민등록번호를 바르게 입력하세요");
		document.form1.p_resno2.value = "";
		document.form1.p_resno2.focus();
		return;
	}

    if(!regcheck(v_resno1+v_resno2))
    {
        $("[name='p_resno1']").val("");
        $("[name='p_resno2']").val("");
        $("[name='p_resno1']").focus();
        return;
    }
    */


    // 회원인지 확인
    //document.form1.p_username.value = v_name;
    //document.form1.p_resno1.value   = v_resno1;
    //document.form1.p_resno2.value   = v_resno2;
    //document.form1.action="/servlet/controller.homepage.MainMemberJoinServlet";    //실명인증 체크 하지 않음경우
    document.form1.action = "/certi/nc_p.jsp";                                 //실명인증 체크 페이지
    document.form1.checkflag.value   = "true";
    
    document.form1.submit();   
	}                   
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
			//document.form1.p_username.value="";
			//document.form1.p_resno1.value="";
			//document.form1.p_resno2.value="";
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
		//self.close();
	}
	
	
</script>

<!-- 아이핀 스크립트 종료 -->

<!-- 스크립트영역종료 -->

<!-- form 시작 -->

<!-- 아이핀 form 시작 -->

<!-- 가상주민번호 서비스 팝업을 호출하기 위해서는 다음과 같은 form이 필요합니다. -->
<form name="form_ipin" method="post">
	<input type="hidden" name="m" value="pubmain">						<!-- 필수 데이타로, 누락하시면 안됩니다. -->
    <input type="hidden" name="enc_data" value="<%= sEncData %>">		<!-- 위에서 업체정보를 암호화 한 데이타입니다. -->
    
    <!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다.
    	 해당 파라미터는 추가하실 수 없습니다. -->
    <input type="hidden" name="param_r1" value="">
    <input type="hidden" name="param_r2" value="">
    <input type="hidden" name="param_r3" value="">
    
    <!--<a href="javascript:fnPopup();"><img src="http://image.creditbank.co.kr/static/img/vno/new_img/bt_17.gif" width=218 height=40 border=0></a>-->
</form>

<form name="vnoform" method="post">
	<input type="hidden" name="enc_data">								<!-- 인증받은 사용자 정보 암호화 데이타입니다. -->
	
	<!-- 업체에서 응답받기 원하는 데이타를 설정하기 위해 사용할 수 있으며, 인증결과 응답시 해당 값을 그대로 송신합니다.
    	 해당 파라미터는 추가하실 수 없습니다. -->    
    <input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno" value="" />
    <input type="hidden"	name="checkflag" value="" />
    <input type="hidden"	name="checkradio" value="" />  
</form>

<!-- 아이핀 form 끝 -->

<form name="form1"  action="" method="post" >
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_resno" value="" />
    <input type="hidden"	name="checkflag" value="" />
    <input type="hidden"	name="checkradio" value="" />   
    
    <input type="hidden"	name="p_username" value="" />
    <input type="hidden"	name="p_resno1" value="" />
    <input type="hidden"	name="p_resno2" value="" />
        
    <!-- 아이핀관련 시작 -->
    <input type="hidden" name="enc_data">
    <!-- 아이핀관련 끝 -->
    <!-- 안심실명인증관련 시작-->
    <input type="hidden" name="enc_data" value="">
 	<input type="hidden" name="nc_safeid" value="">
 	<input type="hidden" name="nc_username" value="">
 	<input type="hidden" name="nc_jumindate" value="">
 	<input type="hidden" name="nc_gender" value="">
 	<input type="hidden" name="nc_cert" value="">
 	<!-- 안심실명인증관련 시작-->
    
              <%if( box.getSession("tem_grcode").equals("N000001")) { %>
      
      <table>   
	      <tr>
	      	<td>
	      	
	      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/member/stitle_01.gif" alt="회원가입" /></td>
        <td class="h_road">Home &gt; 이용안내  &gt; <strong>회원가입</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <!--타이틀부분//-->
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
          
          <!--process-->
          <table width="672" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td height="34"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td><img src="/images/portal/homepage_renewal/member/step_02.gif" alt="" /></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <!--process//-->
          
          </td>
        </tr>
        <tr>
          <td height="15"></td>
        </tr>
      </table>
      
     
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td id="s_stit"><img src="/images/portal/homepage_renewal/member/s_stitle_03.gif" alt="본인확인" /></td>
        </tr>
        <tr>
          <td><!-- <p>보다 많은 사용자들의 원활한 서비스 이용과 온라인 상에서의 익명 사용으로 인한 피해 등을 방지하기 위해<br />
            회원님에 대한 실명제를 시행하고 있습니다. 회원으로 가입하신 사용자의 정보는 사전 동의 없이는 절대로 <br />
            공개되지 않으며, 모든 회원 정보는 개인정보 보호정책에 의해 보호받고 있습니다.</p>
            <p>&nbsp;</p> -->
            <p>관련 법률에 따라 다른 사람의 주민등록번호를 도용하여 인터넷 서비스에 가입하는 경우는 명백한 범죄행위로<br />
              <span class="blue_txt">3년 이하의 징역 또는 1천만원 이하의 벌금</span>에 처해지므로 반드시 <span class="blue">본인 실명</span>으로만 가입해 주시기 바랍니다.</p>
            <p>&nbsp;</p>
            <!-- <p>외국인 회원 가입문의는 한국콘텐츠아카데미 운영팀으로 별도 문의해주시기 바랍니다.<br />
          Tel. 02-2161-0077   &nbsp;&nbsp;&nbsp;E-mail. academy@kocca.or.kr </p> --></td>
        </tr>
        <tr>
          <td>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18 " >
           <tr>
		   		<td style="font-size: 0px;" id="namechk">
		   			<a href="javascript:chageImg('1');" ><img src="/images/portal/homepage_renewal/member/btn_namechk_on.gif" alt="실명확인" name="image991" id="image991" /></a>
                    <a href="javascript:chageImg('2');" ><img src="/images/portal/homepage_renewal/member/btn_ipin_off.gif" alt="공공 i-Pin 인증" name="image992" id="image992"/></a>
				</td>
           </tr>
           <table id="tab1" width="100%" border="0" cellspacing="0" cellpadding="0" >           
            <tr>
          <td id="namechk_box"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="center"><table width="643" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="50">2006년 9월 24일 부터 개정되는 주민등록법에 의해 타인의 주민번호로 온라인 회원 가입을 하는 등 <br />
타인의 주민등록번호를 부정 사용하게 되면 3년 이하의 징역 또는 1천만원 이하의 벌금이 부과될 수 있습니다.</td>
                    </tr>
                    <tr>
                      <td><table width="100%" border="0" cellpadding="4" cellspacing="1">
                        <tr>
                          <td height="30"><table border="0" align="center" cellpadding="3" cellspacing="0">
                              <tr>
                                <td class="obox"><input name="radio" type="radio" class="input_search2" id="radio" value="radio1" checked onclick="javascript:changeJoinType('1')"/></td>
                                <td>일반회원(14세이상) </td>
                                <td><span class="obox">
                                  <input name="radio" type="radio" class="input_search2" id="radio2" value="radio2" onclick="javascript:changeJoinType('2')"/>
                                </span></td>
                                <td>일반회원(14세미만) </td>
                                <!-- <td><span class="obox">
                                  <input name="radio" type="radio" class="input_search2" id="radio3" value="radio3" />
                                </span></td>
                                <td>외국인회원(외국인 등록번호를 가진 고객님)</td> -->
                              </tr>
                            </table></td>
                        </tr>
                        <tr>
                          <td bgcolor="#FFFFFF">
                          	<div class="joinType" id="joinType1" style="display:;">
	                          <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                            <tr>
	                              <!--
	                              <td width="8%"><div align="center">이름</div></td>
	                              <td width="21%"><input name="p_username" type="text" id="p_username" size="20" /></td>
	                              <td width="15%"><div align="center">주민등록번호</div></td>
	                              <td width="38%"><input name="p_resno1" type="text" id="p_resno1" size="15" maxlength="6" onkeyup="moveFocus(6,this,this.form.p_resno2);" onKeypress="onlyNumber();" />
	                                -
	                              <input name="p_resno2" type="password" id="p_resno2" size="15" maxlength="7" onKeypress="onlyNumber();"/></td>
	                              -->
	                              <td width="18%"><a href="javascript:ncPopup();"><img src="/images/portal/homepage_renewal//member/btn_ok01.jpg" /></a></td>
	                            </tr>
	                          </table>
	                         </div>
	                         <div class="joinType" id="joinType2" style="display:none;">
	                          <table width="630" border="0" cellspacing="0" cellpadding="4">
								  <tr>
									<td bgcolor="FFFFFF" colspan="4"><br><span class="btxt">14세 미만 고객님은 사이버콘텐츠아카데미 회원가입을 위해 보호자 동의가 필요합니다.</span><br><br>
										본 사이트 회원가입은 '정보통신망 이용촉진에 관한 법률' 제 31조의 규정에 따라<br>
										만 14세 미만의 아동의 개인정보를 이용하는 경우 보호자 또는 법정대리인의 동의가 있어야 회원가입이 가능합니다.<Br><Br>
										<strong>아래 주민등록번호를 입력해주세요. (실명인증 사이트)</strong><Br>&nbsp;
										
										<div align=right><input type="checkbox" id="pagree" name="pagree">자녀가 본 사이트 회원으로 등록하는데 동의합니다.</div>
									</td>
								  </tr>
		                            <tr>
		                              <td width="90"><div align="left" class=btxt>보호자 이름</div></td>
		                              <td align="left" width="150"><input name="pname" type="text" id="pname" size="20" /></td>
		                              <td width="140"><div align="left" class=btxt>보호자 주민등록번호</div></td>
		                              <td align="left"><input name="presno1" type="text" id="presno1" size="14" maxlength="6" onkeyup="moveFocus(6,this,this.form.presno2);" onKeypress="onlyNumber();" />-<input name="presno2" type="password" id="presno2" size="14" maxlength="7" onKeypress="onlyNumber();" /></td>
		                            </tr>
									<tr>
										<td bgcolor="#E5E5E5" height="1" colspan="4"></td>
									</tr>
									<tr>
										<td><div align="left" class=btxt>보호자 이메일</div></td>
										<td colspan="3" align="left">
											<input name=pemail1 type="text" id="pemail1" size="15" /> @ <input name="pemail2" type="text" id="pemail2" size="15" />
											<select name="pemail3" id="pemail3" onchange="emailChange();">
						                       <option value="">직접입력</option>
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
									<tr>
										<td bgcolor="#E5E5E5" height="1" colspan="4"></td>
									</tr>
		
									<tr>
		                              <td width="90"><div align="left" class=btxt>회원 이름</div></td>
		                              <td align="left" width="150"><input name="pp_username" type="text" id="pp_username" size="20" /></td>
		                              <td width="130"><div align="left" class=btxt>회원 주민등록번호</div></td>
		                              <td align="left"><input name="pp_resno1" type="text" id="pp_resno1" size="14" maxlength="6" onkeyup="moveFocus(6,this,this.form.pp_resno2);" onKeypress="onlyNumber();" />-<input name="pp_resno2" type="password" id="pp_resno2" size="14" maxlength="7" onKeypress="onlyNumber();"/></td>
		                            </tr>
		                            <tr>
									  <td bgcolor="ffffff" colspan="4"><a href="javascript:memberJoinOK()"><img src="/images/portal/homepage_renewal//member/btn_ok01.jpg" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									</tr>
								</table>
							</div>
                          </td>
                        </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>        
           </table>
           <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
           <tr>
             <td id="namechk_box"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td width="25">&nbsp;</td>
                   <td width="226" height="60" align="left" valign="top"><b>공공 아이핀(I_PIN)은 인터넷상의 개인식별번호를 의미하여,<br/>
대면확인이어려운 인터넷에서 주민등록번호를 사용하지 않고<br/>
확인 할 수 있는 수단입니다. </b>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td align="left"><a href="javascript:ipinPopup();"><img src="/images/portal/homepage_renewal/member/btn_ipin_ok.jpg" alt="확인"/></a></td>
                    </tr>
                </table></td>
                  <td width="1" bgcolor="#b7b7b7"></td>
                 <td width="230" id="description">입력하신 정보는 본인확인용으로 사용되며, 회원가입이 완료되면 사용자 확인을 위해 (주민번호 등 중요정보는 암호화 처리)회원정보에 저장됩니다. 회원정보는 무단으로 공개되지 않으며, 개인정보보호방침에 의해 보호받고 있습니다.</td>
                <td>&nbsp;</td>
               </tr>
            </table></td>
            </tr>
           </table>
     </table></td>
        </tr>
        <tr>
			<td height="20">&nbsp;</td>
		</tr>
        <tr>
			<td align="center"><p>외국인 회원 가입문의는 한국콘텐츠아카데미 운영팀으로 별도 문의해주시기 바랍니다.<br />
          Tel. 02-2161-0077   &nbsp;&nbsp;&nbsp;E-mail. academy@kocca.or.kr </p></td>
		 </tr>
      </table>
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
	      	
	      	<%}else{ %>

    <h2><img src="/images/portal/member/membership/h1_tit.gif" alt="회원가입" class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>회원가입</strong></p></h2>

    <p><img src="/images/portal/member/membership/member_info_2.gif"  alt="본인확인" /></p>
    <p class="mg_t30"><img src="/images/portal/member/membership/stit_m1.gif"  alt="본인확인" /></p>
    <p class="mg_t15 mg_l15 lh_17">보다 많은 사용자들의 원활한 서비스 이용과 온라인 상에서의 익명 사용으로 인한 피해 등을 방지하기 위해<br />
        회원님에 대한 실명제를 시행하고 있습니다. 회원으로 가입하신 사용자의 정보는 사전 동의 없이는 절대로 <br />
        공개되지 않으며, 모든 회원 정보는 개인정보 보호정책에 의해 보호받고 있습니다.<br /><br />
        * 관련 법률에 따라 다른 사람의 주민등록번호를 도용하여 인터넷 서비스에 가입하는 경우는 명백한 범죄행위로<br />
        <span style="color:#3DA5D6;">3년 이하의 징역 또는 1천만원 이하의 벌금</span>에 처해지므로 반드시
        <span style="color:#3DA5D6;">본인 실명</span>으로만 가입해 주시기 바랍니다. <br /><br/>
        외국인 회원 가입문의는 한국콘텐츠아카데미 운영팀으로 별도 문의해주시기 바랍니다.<br />
        Tel. 02-2161-0077&nbsp;&nbsp;   E-mail. <a href="mailto:academy@kocca.or.kr">academy@kocca.or.kr</a>
    </p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18 " >
           <tr>
		   		<td style="font-size: 0px;">
		   			<a href="javascript:chageImg('1');" ><img src="/images/portal/member/membership/box_img_1_1.gif" alt="실명확인" name="image991" width="91" height="21" border="0" id="image991" /></a>
                    <a href="javascript:chageImg('2');" ><img src="/images/portal/member/membership/box_img_1_4.gif" alt="공공 i-Pin 인증" name="image992" width="131" height="21" border="0" id="image992"/></a>
				</td>
           </tr>
           <table id="tab1" width="100%" border="0" cellspacing="0" cellpadding="0" >
           <tr>
           	<td><img src="/images/portal/member/membership/box_img_1_5.gif" width="690" height="16" /></td>
           </tr>
           <tr>
             <td  background="/images/portal/member/membership/box_img_3.gif" alt=""><table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td width="25">&nbsp;</td>
                   <td height="25" align="left" valign="bottom" class="fw_b fc_n">이용자 본인의 이름과 주민번호를 정확히 입력해 주십시오.</td>
                 </tr>
               <tr>
                 <td width="25">&nbsp;</td>
                 <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td align="left"><table border="0" align="left" cellpadding="0" cellspacing="0">
                       <tr>
                         <td width="80" align="left">이름</td>
                         <td width="170"><span class="pd_t7">
                           <input name="p_username" type="text" class="inp_txt_01" id="p_username" size="32" maxlength="30" title="이름"/>
                         </span></td>
                       </tr>
                       <tr>
                         <td align="left">주민등록번호</td>
                         <td><span class="pd_t7">
                           <input name="p_resno1" type="text" class="inp_txt_01" id="p_resno1" title="주민등록번호앞자리" size="14" maxlength="6" onkeyup="moveFocus(6,this,this.form.p_resno2);" onKeypress="onlyNumber();"/> -
                             <input name="p_resno2" type="password" class="inp_txt_01" id="p_resno2" title="주민등록번호뒷자리"size="14" maxlength="7" onKeypress="onlyNumber();"/>
                         </span></td>
                       </tr>
                    </table></td>
                    <td><a href="javascript:memberJoinOK()"><img src="/images/portal/member/membership/btn_joinok.gif" alt="확인" width="84" height="43" /></a></td>
                   </tr>
                </table></td>
                 <td><img src="/images/portal/member/membership/box_img_4.gif" width="27" height="79" /></td>
                 <td align="left" class="fs_s fc_96">입력하신 정보는 본인확인용으로 사용되며, 회원<br />
                                                        가입이 완료되면 사용자 확인을 위해 (주민번호 등 <br />
                                                        중요정보는 암호화 처리)회원정보에 저장됩니다.<br />
                                                        회원정보는 무단으로 공개되지 않으며,<br />
                                                        <span style="color:#DC143C;">개인정보보호방침에 의해 보호받고 있습니다.</span></td>
                <td>&nbsp;</td>
               </tr>
            </table></td>
            </tr>
           <tr>
             <td><img src="/images/portal/member/membership/box_img_2.gif" width="690" height="12" /></td>
           </tr>
           </table>
           <table id="tab2" width="100%" border="0" cellspacing="0" cellpadding="0" style="display:none;">
           <tr>
           	<td><img src="/images/portal/member/membership/box_img_1_5.gif" width="690" height="16" /></td>
           </tr>
           <tr>
             <td  background="/images/portal/member/membership/box_img_3.gif" alt=""><table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td width="25">&nbsp;</td>
                   <td height="25" align="left" valign="bottom" class="fw_b fc_n">공공 아이핀(I_PIN)은 인터넷상의 개인식별번호를 의미하여,<br/> 대면확인이어려운 인터넷에서 주민등록번호를 사용하지 않고<br/> 확인 할 수 있는 수단입니다. </td>
                 </tr>
               <tr>
                 <td width="25">&nbsp;</td>
                 <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                     <td align="left"><a href="javascript:fnPopup();"><img src="/images/portal/member/membership/I_PIN_btn_joinok.gif" alt="확인" width="200" height="43" /></a></td>
                    </tr>
                </table></td>
                 <td><img src="/images/portal/member/membership/box_img_4.gif" width="27" height="79" /></td>
                 <td align="left" class="fs_s fc_96">입력하신 정보는 본인확인용으로 사용되며, 회원<br />
                                                        가입이 완료되면 사용자 확인을 위해 (주민번호 등 <br />
                                                        중요정보는 암호화 처리)회원정보에 저장됩니다.<br />
                                                        회원정보는 무단으로 공개되지 않으며,<br />
                                                        <span style="color:#DC143C;">개인정보보호방침에 의해 보호받고 있습니다.</span></td>
                <td>&nbsp;</td>
               </tr>
            </table></td>
            </tr>
           <tr>
             <td><img src="/images/portal/member/membership/box_img_2.gif" width="690" height="12" /></td>
           </tr>
           </table>
     </table>
     <%} %>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->