<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page language="java" import="SafeNC.kisinfo.*"%>
<%
	//**********************************************************************************************	
	//한국신용평가정보 공공기관용 안심실명확인 서비스
	//사이트코드,사이트패스워드,return_url ,요청 seq 값 암호화한다.
	//작성일 : 2006.10.23
    //**********************************************************************************************	
   
   
	//암심실명서비는 웹방식으로 모듈을 통해 암호화한 값을 한신평에 넘겨주며, 한신평 팝업창을 호출합니다.  
  //한신평에서 사용자의 주민번호와 이름을 받아 인증절차를 거치며, 결과값을 리턴url로 암호화하여 보냅니다.
 	//암호화된 값은 certnc_return.jsp 파일에서 복호화하여 업체에게 사용자 정보를 알려줍니다.
     
   
  // 필수 수정사항 : pSite_code, pSite_pwd, pReturn_url 3가지 입니다. 
    	
	String pSite_code 	= "K674";				// 한신평에서 발급받은 사이트 id 
	String pSite_pwd  	= "26023508";					// 한신평에서 발급받은 사이트 비밀번호
	String pSeqid	   	= "1234567890";				// CP요청번호 :  한신평 프로세스 완료 후 입력한 값을 반환해 드립니다. 
																					//							 안심실명은 업체 중간에 한신평 프로세스가 진행되는 사항이기에 
																					//			 				 필요시 사용할수 있는 변수를 넣었습니다. 참고해 주세요
	
	String pReturn_url	= "http://edu.kocca.or.kr/certi/certnc_return.jsp";		// 결과값을 리턴 받으실 URL(업체에서 결과값을 받으실 URL입니다.)
	//String pReturn_url	= "http://localhost:7001/certi/certnc_return.jsp";		// 결과값을 리턴 받으실 URL(업체에서 결과값을 받으실 URL입니다.)
	//http://localhost:7001/certi/ipin_process.jsp 테스트용
	//http://edu.kocca.or.kr/certi/ipin_process.jsp 실서버용
	
	String pReserved1	= "test1";					// 기타 Reserved data1 : 한신평 프로세스 진행후 받으실 값입니다. 필요시에 사용해 주세요.
	String pReserved2	= "test2";					// 기타 Reserved data2 : 한신평 프로세스 진행후 받으실 값입니다. 필요시에 사용해 주세요.
	String pReserved3	= "test3";					// 기타 Reserved data3 : 한신평 프로세스 진행후 받으실 값입니다. 필요시에 사용해 주세요.
	String enc_data		= "";
	
	// 데이타를 암호화,복호화 하는 모듈입니다.
	SafeNCCipher safeNC = new SafeNCCipher();
	
	//**********************************************************************************************
	// 데이타를 암호화 합니다. 
	//**********************************************************************************************	
	
	if( safeNC.request(pSite_code,pSite_pwd,pSeqid,pReturn_url,pReserved1,pReserved2,pReserved3) == 0 ) {
		enc_data = safeNC.getEncParam();		
	}else {
		enc_data = "";
	}               
%>

<html>
<head>
	<script Language="JavaScript">
	   function fnPopup(){
	   	   //한국신용평가정보 안심 실명확인 팝업페이지를 띄웁니다.	 
		   //window.open('', 'popup','width=410, height=555');
		   document.form.target = "_self";
		   document.form.action = "https://cert.namecheck.co.kr/certnc_input.asp"
		   document.form.submit();
	   }
	</script> 

<!-- 
		enc_data 의 값을 한신평으로 보내줍니다. enc_data의 값이 없으면, 정상적으로 서비스를 받을수 없습니다. 
		enc_data 의 값을 받지 못하시면 SafeNc.jar 파일을 다시 한번 확인해 주시고, 서버 재기동 해 주세요.
		
 -->

</head>		
<body onload="javascript:fnPopup();">
<form method="post" name="form">
 	<input type="hidden" name="enc_data" value="<%=enc_data%>">
</form>
</body>
</html>	
