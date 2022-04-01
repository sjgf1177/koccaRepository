<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page language="java" import="SafeNC.kisinfo.*"%>
<%
	//**********************************************************************************************
	//한국신용평가정보 공공기관용 안심실명확인 서비스
	//작성일 : 2006.10.23
    //**********************************************************************************************

	 //한신평에서 인증후 결과값을 enc_data 에 담아 보내드립니다. (main 에서의 enc_data와는 다릅니다.) 이때, 한글은 euc-kr 로 보냅니다. 
	 //한신평에서 보내는 값은 
	 //1. 안심키값 : 주민번호 대체키 (13byte) - 업체에서 관리할 사용자의 유티크한 값입니다. 
	 //2. 성명 		:	안심실명 사용자 이름
	 //3. 생년월일 : 사용자 생년월일
	 //4. 성별 		:남-M, 여-F, 외국인남-A, 외국인여-B
	 //위의 4가지의 정보가 있으며, 원하실 경우 안심키값과 성명만 넘겨드릴수 있습니다. 전산담당자에게 문의 주세요.


	String enc_data = "";
	String sMsg = "";
    String nc_safeid 	= "";	//안심키값(13자리)
    String nc_username 	= "";	//성명
    String nc_jumindate	= "";	//생년월일
    String nc_gender 	= "";	//성별
    String isIpin		= "N";	//아이핀여부( 아이핀 Y / 실명인증 N )
    int iReturn			= 0;	//리턴값
	
	//인증 후 사용자의 정보가 암호화 되어 반환된다.
	enc_data = request.getParameter("enc_data");		
	
	//out.println("리턴데이타 : " + enc_data + "<br><br><br>");

	if (enc_data != "") {
	    SafeNCCipher safeNC = new SafeNCCipher();
	    
		//**********************************************************************************************
		// ReturnURL로부터 수신된 암호화 데이터(AES_CBC_PAD, SHA256, BASE64 Encoded Data)	
		// 메소드를 호출하여 복호화된 값을 가져옵니다.
		//**********************************************************************************************	    
        iReturn = safeNC.response(enc_data);		
        
        //한신평팝업에서 리턴값 2,3,50(명의도용차단)번에 해당하는 사용자는 처리되고 있습니다.
				//2, 3, 50 외의 해당하는 리턴값을 처리해주세요.
        
        if( iReturn > 0 ) {                      
			if (iReturn == 1) {		
				sMsg = "본인맞음";						//안심실명 처리결과값 1 : 본인인증성공. ==> 다음단계로 처리해 주시면 됩니다.
			}else if (iReturn == 2){	
				sMsg = "본인아님";						//본인이아닌경우 : 한신평에서 처리합니다.
			}else if (iReturn == 3) {		
			   sMsg = "자료없음";      			//자료업음 : 한신평에서 처리합니다. 
			}else if (iReturn == 4) {	
			   sMsg = "시스템오류";      
			}else if (iReturn == 5) {		
			   sMsg = "체크섬오류";      
			}else if (iReturn == 10) {
			   sMsg = "사이트코드오류";      
			}else if (iReturn == 11) {
			   sMsg = "중단된사이트";      
			}else if (iReturn == 12) {	
			   sMsg = "비밀번호오류";      
			}else{
				sMsg = "기타오류입니다." ;   //해당 리턴오류값을 확인하여 처리하여 주세요.     
			}

		    //out.println ("실명확인 처리결과(iReturn) = [" + iReturn + "] " + sMsg + "<BR>");   	
		    //out.println ("안심키값(13자리:주민번호대체값) = [" + safeNC.getSafeID() + "]<BR>");
		    //out.println ("성명 = [" + safeNC.getUserName() + "]<BR>");
		    //out.println ("생년월일 = [" + safeNC.getJuminDate() + "]<BR>");	      		
		    //out.println ("성별 = [" + safeNC.getGender() + "]<BR>");		      			
		    //out.println ("REQUEST_SEQ = [" + safeNC.getRequestSEQ() + "]<BR>");
		    //out.println ("RESERVED1 = [" + safeNC.getReserved1() + "]<BR>");
		    //out.println ("RESERVED2 = [" + safeNC.getReserved2() + "]<BR>");
		    //out.println ("RESERVED3 = [" + safeNC.getReserved3() + "]<BR>");  
		    
		    
		    nc_safeid		=	safeNC.getSafeID();
		    nc_username		=	safeNC.getUserName();
		    nc_jumindate	=	safeNC.getJuminDate();
		    nc_gender		=	safeNC.getGender();
		}		    	    	
	}else{
		out.println ("처리할 데이타가 없습니다.");
	}
%>

<html>
<head>
	<script Language="JavaScript">
	   function fnLoad(){
			
		   if(<%=iReturn%> == "1"){
				//alert("실명인증이 완료되었습니다");

				//한국신용평가정보 안심 실명확인 팝업페이지를 띄웁니다.	 
				   //window.open('', 'popup','width=410, height=555');
				   //document.form.target = "_self";
				   //document.form.action = "https://cert.namecheck.co.kr/certnc_input.asp"
				   //document.form.submit();
				   parent.opener.parent.document.form1.nc_safeid.value = "<%=nc_safeid%>";
				   parent.opener.parent.document.form1.nc_username.value = "<%=nc_username%>";
				   parent.opener.parent.document.form1.nc_jumindate.value = "<%=nc_jumindate%>";
				   parent.opener.parent.document.form1.nc_gender.value = "<%=nc_gender%>";
				   parent.opener.parent.document.form1.nc_cert.value = "Y";
				   
				   
				   parent.opener.parent.document.form1.target = "Parent_window";
				   parent.opener.parent.document.form1.action = "/servlet/controller.homepage.MainMemberJoinServlet";
				   parent.opener.parent.document.form1.p_process.value = "CheckResno";
				   parent.opener.parent.document.form1.submit();

				   self.close();
		   }else{
			   alert("입력하신 정보가 올바르지 않습니다.");
			   self.close();
		   }
	   	   
	   }
	</script> 

<!-- 
		enc_data 의 값을 한신평으로 보내줍니다. enc_data의 값이 없으면, 정상적으로 서비스를 받을수 없습니다. 
		enc_data 의 값을 받지 못하시면 SafeNc.jar 파일을 다시 한번 확인해 주시고, 서버 재기동 해 주세요.
		
 -->

</head>		
<!-- <body onload="javascript:fnPopup();">  -->
<body onload="JavaScript:fnLoad()">
<form method="post" name="form">
 	<!--<a href="JavaScript:fnLoad();">안심실명확인</a>
--></form>
</body>
</html>	
