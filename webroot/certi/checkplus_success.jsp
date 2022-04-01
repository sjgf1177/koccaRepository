<%
//**********************************************************
//  1. 제      목: 한국신용평가정보 - CheckPlus 본인인증 성공
//  2. 프로그램명 : checkplus_fail.jsp
//  3. 개      요: 한국신용평가정보 - CheckPlus 본인인증 성공
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2011-03-17
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    Kisinfo.Check.CPClient kisCrypt = new  Kisinfo.Check.CPClient();

    String sEncodeData = request.getParameter("EncodeData");
    String sPlainData = "";
    String sSiteCode = "G1091";				// 한신평정보로부터 부여받은 사이트 코드
    String sSitePassword = "OGUNHRYMMD3M";		// 한신평정보로부터 부여받은 사이트 패스워드
    String sCipherTime = "";						// 복호화한 시간
    String sRequestNumber = "";						// 요청 번호
    String sResponseNumber = "";					// 인증 고유번호
    String sAuthType = "";							// 인증 수단
    String sMessage = "";
    
    int iReturn = kisCrypt.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = kisCrypt.getPlainData();
        sCipherTime = kisCrypt.getCipherDateTime();
        
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = kisCrypt.fnParse(sPlainData);
        
        sRequestNumber = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType = (String)mapresult.get("AUTH_TYPE");
        
        String session_sRequestNumber = (String)box.getSession("REQ_SEQ");
        if(!sRequestNumber.equals(session_sRequestNumber))
        {
            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            sResponseNumber = "";
            sAuthType = "";
        }
    }
    else if( iReturn == -1)
    {
        sMessage = "복호화 시스템 에러입니다.";
    }    
    else if( iReturn == -4)
    {
        sMessage = "복호화 처리오류입니다.";
    }    
    else if( iReturn == -5)
    {
        sMessage = "복호화 해쉬 오류입니다.";
    }    
    else if( iReturn == -6)
    {
        sMessage = "복호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else if( iReturn == -12)
    {
        sMessage = "사이트 패스워드 오류입니다.";
    }    
    else
    {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }

    //비밀번호 재생성(업데이트)
    LoginBean bean = new LoginBean();
    box.put("p_userid_2", box.getSession("sUserid"));
    box.put("p_resno1_2", box.getSession("sJuminid1"));
    box.put("p_resno2_2", box.getSession("sJuminid2"));
    box.put("p_name_2", box.getSession("sName"));
    String v_tmp_pwd = bean.selectTempPassword1(box);
%>

<html>
<head>
    <title>패스워드 재발급 - 본인 인증</title>
<link type="text/css" href="/css/2013/common.css" rel="stylesheet" />
<link type="text/css" href="/css/2013/sub.css" rel="stylesheet" />
<link type="text/css" href="/css/2013/popup.css" rel="stylesheet" />

<SCRIPT LANGUAGE="JavaScript">
<!--
    // 등록
    function insert() {
        var fm=document.form1;
        var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;

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

        fm.p_checks.value = "1";        
        fm.p_title.value = "[KOCCA] 임시 비밀번호는 <%=v_tmp_pwd %> 입니다. 로그인 후 신규 비밀번호로 변경해주세요.";
        fm.p_handphone.value = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;
        
        document.form1.action = "/servlet/controller.library.SMSServlet";
        document.form1.p_process.value = "certicheck";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>
<body onload="javascript:window.resizeTo(459,500);">

     <%--필요에 따라 사용할 것
    <table width=480 border=1>
        <tr>
            <td>복호화한 시간</td>
            <td><%= sCipherTime %> (YYMMDDHHMMSSSSS)</td>
        </tr>
        <tr>
            <td>요청 번호</td>
            <td><%= sRequestNumber %></td>
        </tr>            
        <tr>
            <td>한신평응답 번호</td>
            <td><%= sResponseNumber %></td>
        </tr>            
        <tr>
            <td>인증수단</td>
            <td><%= sAuthType %></td>
        </tr>
    </table><br><br>  
   --%>
    <!-- <%= sMessage %> -->

<form name = "form1" method = "post">
  <input type='hidden' name = 'p_process' value=''>
  <input type='hidden' name ="p_checks" value=""/>
  <input type="hidden"	name="p_handphone" value="" />
  <input type="hidden"	name="p_title" value="" />
  <!-- tz_humantouc insert parameter start-->
  
  
<div id="popup" style="width:443px; height:450px;">
	<h2><img src="/images/2013/support/password_sms_title.gif" alt="패스워드 SMS 발송 서비스" /></h2>
	<div class="title">
			<span>휴대폰 패스워드 발급 확인</span>
		</div>
	<!-- pcont -->
	<div class="pcont">
		<span class="pcont_topl"></span><span class="pcont_topr"></span>
		<!-- inner -->
		<div class="inner">
			<p class="txt1">고객님의 개인정보는 항상 암호화되어 처리되고, 본인인증으로만 사용아혀 보관하지 않습니다.</p>
			<p class="txt2">
				<strong>본인증이 완료</strong> 되었습니다.<br />입력하신 휴대폰 정보로 임시 비밀번호를 발송해 드립니다.
			</p>
			<div class="mbBox">
				휴대폰번호
				<select name="p_handphone1" id="p_handphone1">
                    <option value="010">010</option>
                    <option value="011">011</option>
                    <option value="016">016</option>
                    <option value="017">017</option>
                    <option value="018">018</option>
                    <option value="019">019</option>
                </select>-
				<input type="text" name="p_handphone2" id="p_handphone2" maxlength="4" class="inputText" size="4" title="휴대폰번호 중간자리 입력" style="width:70px" /> -
				<input type="text" name="p_handphone3" id="p_handphone3" maxlength="4" class="inputText" size="4" title="휴대폰번호 끝자리 입력" style="width:70px" />
			</div>
			<span class="tips">※ 본 서비스는 요금이 부과되지 않습니다.</span>
			<!-- btn_area -->
			<div class="btn_area">
				<a href="javascript:insert()"><img src="/images/2013/btn/btn_send.gif" alt="전송" /></a>
				<a href="#" onclick="self.close();return false;"><img src="/images/2013/btn/btn_lyclose.gif" alt="닫기" /></a>
			</div>
			<!--// btn_area -->
		</div>
		<!--// inner -->
		<span class="pcont_btml"></span><span class="pcont_btmr"></span>
	</div>
	<!--// pcont -->
	<p class="btm_logo"><img src="/images/2013/common/popfoot_logo.gif" alt="kocca 한국콘텐츠아카데미" /></p>
</div>
</form>
</body>
</html>