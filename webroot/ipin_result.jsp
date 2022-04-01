<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page language="java" import="Kisinfo.Check.IPINClient" %>

<%

		/********************************************************************************************************************************************
		NICE신용평가정보 Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		서비스명 : 가상주민번호서비스 (IPIN) 서비스
		페이지명 : 가상주민번호서비스 (IPIN) 결과 페이지
		*********************************************************************************************************************************************/
		
		String sSiteCode				= "B014";			// IPIN 서비스 사이트 코드		(NICE신용평가정보에서 발급한 사이트코드)
		String sSitePw					= "20727176";			// IPIN 서비스 사이트 패스워드	(NICE신용평가정보에서 발급한 사이트패스워드)
		
		String sRtnMsg					= "";			// 처리결과 메세지
		
		String i_sex         = "";
		String i_memberyear  = "";
		String i_membermonth = "";
		String i_memberday   = "";
		String i_username	 = "";
		String i_dupinfo	 = "";
		String i_birthdate	 = "";
		String i_vnumber	 = "";
		
		// 사용자 정보 및 CP 요청번호를 암호화한 데이타입니다.
		String sResponseData = request.getParameter("enc_data");
		
		// CP 요청번호 : ipin_main.jsp 에서 세션 처리한 데이타
		String sCPRequest = (String)session.getAttribute("CPREQUEST");
		
		
		
		// 객체 생성
		IPINClient pClient = new IPINClient();
		
		
		/*
		┌ 복호화 함수 설명  ──────────────────────────────────────────────────────────
		Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
		
		fnResponse 함수는 결과 데이타를 복호화 하는 함수이며,
		fnResponseExt 함수는 결과 데이타 복호화 및 CP 요청번호 일치여부도 확인하는 함수입니다. (세션에 넣은 sCPRequest 데이타로 검증)
		
		따라서 귀사에서 원하는 함수로 이용하시기 바랍니다.
		└────────────────────────────────────────────────────────────────────
		*/
		int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData);
		//int iRtn = pClient.fnResponse(sSiteCode, sSitePw, sResponseData, sCPRequest);
		
		// Method 결과값에 따른 처리사항
		if (iRtn == 1)
		{
		
		/*
			다음과 같이 사용자 정보를 추출할 수 있습니다.
			사용자에게 보여주는 정보는, '이름' 데이타만 노출 가능합니다.
		
			사용자 정보를 다른 페이지에서 이용하실 경우에는
			보안을 위하여 암호화 데이타(sResponseData)를 통신하여 복호화 후 이용하실것을 권장합니다. (현재 페이지와 같은 처리방식)
			
			만약, 복호화된 정보를 통신해야 하는 경우엔 데이타가 유출되지 않도록 주의해 주세요. (세션처리 권장)
			form 태그의 hidden 처리는 데이타 유출 위험이 높으므로 권장하지 않습니다.
		*/
		i_memberyear  = pClient.getBirthDate().substring(0,4); 
		i_membermonth = pClient.getBirthDate().substring(4,6);
		i_memberday   = pClient.getBirthDate().substring(6,8);
		i_sex 		  = pClient.getGenderCode().substring(0,1);
		i_username 	  = pClient.getName();
		i_dupinfo	  = pClient.getDupInfo();
		i_birthdate	  = pClient.getBirthDate();
		i_vnumber	  = pClient.getVNumber();
				
		String sVNumber			= pClient.getVNumber();			// 가상주민번호 (13자리이며, 숫자 또는 문자 포함)
		String sName			= pClient.getName();			// 이름
		String sDupInfo			= pClient.getDupInfo();			// 중복가입 확인값 (DI - 64 byte 고유값)
		String sAgeCode			= pClient.getAgeCode();			// 연령대 코드 (개발 가이드 참조)
		String sGenderCode		= pClient.getGenderCode();		// 성별 코드 (개발 가이드 참조)
		String sBirthDate		= pClient.getBirthDate();		// 생년월일 (YYYYMMDD)
		String sNationalInfo	= pClient.getNationalInfo();	// 내/외국인 정보 (개발 가이드 참조)
		String sCPRequestNum	= pClient.getCPRequestNO();		// CP 요청번호
		
		sRtnMsg = "정상 처리되었습니다.";
		
		}
		else if (iRtn == -1 || iRtn == -4)
		{
		sRtnMsg =	"iRtn 값, 서버 환경정보를 정확히 확인하여 주시기 바랍니다.";
		}
		else if (iRtn == -6)
		{
		sRtnMsg =	"당사는 한글 charset 정보를 euc-kr 로 처리하고 있으니, euc-kr 에 대해서 허용해 주시기 바랍니다.<BR>	한글 charset 정보가 명확하다면 ..<BR><B>iRtn 값, 서버 환경정보를 정확히 확인하여 메일로 요청해 주시기 바랍니다.</B>";
		}
		else if (iRtn == -9)
		{
		sRtnMsg = "입력값 오류 : fnResponse 함수 처리시, 필요한 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.";
		}
		else if (iRtn == -12)
		{
		sRtnMsg = "CP 비밀번호 불일치 : IPIN 서비스 사이트 패스워드를 확인해 주시기 바랍니다.";
		}
		else if (iRtn == -13)
		{
		sRtnMsg = "CP 요청번호 불일치 : 세션에 넣은 sCPRequest 데이타를 확인해 주시기 바랍니다.";
		}
		else
		{
		sRtnMsg = "iRtn 값 확인 후, NICE신용평가정보 개발 담당자에게 문의해 주세요.";
		}
%>


<%@page import="com.credu.library.RequestBox"%>
<%@page import="com.credu.library.RequestManager"%>



<html>
<head>	
	<script language="JavaScript" type="text/JavaScript">
	function init(){		
		document.form1.action = "/servlet/controller.homepage.MainMemberJoinServlet";
		document.form1.p_process.value  = "CheckResno";
		document.form1.submit();
	}	
	</script>
</head>

<body onload="javascript:init();">

<!-- 사용자 정보는 '이름' 외에는 화면에 노출하시면 안됩니다.
	 사용자 정보를 통신해야 하는 경우엔, 아래와 같이 암호화 정보로 통신 후 복호화하여 이용하시기 바랍니다.
	 만약, 복호화 된 데이타를 통신해야 하는 경우에는 정보보안을 위하여 주의해 주시기 바랍니다. -->
	 
<table border="0">
<form name="form1" method="post">
	<input type="hidden" name="enc_data" value="<%= sResponseData %>">
	<input type="hidden" name="p_process" value="">
	<input type="hidden" name="i_sex" value="<%=i_sex %>">
	<input type="hidden" name="i_memberyear" value="<%=i_memberyear %>">
	<input type="hidden" name="i_membermonth" value="<%=i_membermonth %>">
	<input type="hidden" name="i_memberday" value="<%=i_memberday %>">
	<input type="hidden" name="i_username" value="<%=i_username %>">
	<input type="hidden" name="i_dupinfo" value="<%=i_dupinfo %>">
	<input type="hidden" name="i_birthdate" value="<%=i_birthdate %>"/>
	<input type="hidden" name="i_vnumber" value="<%=i_vnumber %>"/>	
	<input type="hidden" name="i_irtn" value="<%=iRtn %>"/>
	
	
	
</form>
</table>

</body>
</html>