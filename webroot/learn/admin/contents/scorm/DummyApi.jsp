<%-- 
/**
 * @Filename		DummyApi.jsp
 * @Modifier		Sean Lee
 * @Creator		Sean Lee
 * @Version		1.0
 * @Date			2004-07-22
 * @Author			Copyright (c) 2004 by Daewoo Infomation Systems All Rights Reserved.
 * @Descript		더미용 API
 */
--%>
<%@ page contentType="text/html; charset=euc-kr" errorPage="../../common/errPage/errMsg.jsp"%>
<%
	 
	/*---------------------------------------
	  캐쉬 제거 및 한글처리
	 ---------------------------------------*/		
	response.setHeader("cache-control", "no-cache"); 
	response.setHeader("expires", "0"); 
	response.setHeader("pragma", "no-cache");
	
	request.setCharacterEncoding("euc-kr");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../images/admin.css" rel="stylesheet" type="text/css"> 

<script language="javascript">
<!--

	function LMSInitialize(emptyString)
	{			
		var sResult="true";
		return sResult;		
	}


	function LMSFinish(emptyvar)
	{
		var sResult="true";
		return sResult;
	}


	function LMSGetValue(parameter)
	{	
		var sResult = "";
		if (parameter == "cmi.core.lesson_location")
		{
			sResult=" ";
		} else {
			sResult="true";
		}
		
		return sResult;
	}


	function LMSSetValue(parameter, value)
	{
		var sResult="true";				
		return sResult;
	}


	function LMSCommit(emptyvar)
	{		
		var sResult="true";
		return sResult;
	}


	function LMSGetLastError()
	{
	   var sResult="0"; 
		return sResult;
	}        
	
	
	function LMSGetErrorString(errorCode)
	{
	    var sResult="true";
		return sResult;
	}


	function LMSGetDiagnostic(errorCode)
	{
	    var sResult="true";
		return sResult;
	}   
	

	function init()	
	{  		
		 API = top.etop;		
	}

	function goNext()
	{  		
		 
	}
	
	function goBack()
	{  		
		 
	}
	
	
//-->
</script>

</head>

<body leftmargin="0" topmargin="0" onLoad="init()">
</body>

</html>
