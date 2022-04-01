
<%
//**********************************************************
//  1. 제      목: API 
//  2. 프로그램명: API.jsp
//  3. 개      요: API 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 12. 06
//  7. 수      정:
//*********************************************************** 
%>


<%@ page contentType="text/html; charset=euc-kr"  %>

<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>

<%
	/*---------------------------------------
	  캐쉬 제거 및 한글처리 
	 ---------------------------------------*/		
	response.setHeader("cache-control", "no-cache"); 
	response.setHeader("expires", "0"); 
	response.setHeader("pragma", "no-cache");
	
	request.setCharacterEncoding("euc-kr");

	RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

	/*---------------------------------------
	   변수 및 파라미터값
	 ---------------------------------------*/	
	String commit_value = request.getParameter("commit_value");
	String p_oid = request.getParameter("p_oid");
	String p_lesson = request.getParameter("p_lesson");

	String  p_userid    = box.getSession("userid");    
    String  s_name      = box.getSession("name");
    String  p_subj      = box.getSession("s_subj");
    String  p_year      = box.getSession("s_year");
    String  p_subjseq   = box.getSession("s_subjseq");			out.println("p_oid="+p_oid);    out.println("p_lesson="+p_lesson);	out.println("commit_value="+commit_value);	out.println("p_userid="+p_userid);	out.println("s_name="+s_name);	out.println("p_subj="+p_subj);	out.println("p_year="+p_year);	out.println("p_subjseq="+p_subjseq);%>

<HTML>

<HEAD>
<META http-equiv="expires" CONTENT="Tue, 05 DEC 2004 01:00:00 GMT">
<META http-equiv="Pragma" CONTENT="no-cache">

<SCRIPT LANGUAGE="JAVASCRIPT">

	/***********************************************************
	  전역변수정의
	************************************************************/	
	var obj_code = null;	
	var user_id = null;	
	var user_name= null;

	var course_children = new Array(7);
	var course_info_sco = new Array(13);
	var lecture_info_detail = new Array(9);
	var user_sco_info = new Array(16);
	var student_preference = new Array(5);

	var objectives = new Array(1);
	for ( var i=0; i<1; i++){objectives[i] = new Array(7);}

	var interactions = new Array(1);	
	for ( var i=0; i<1; i++){interactions[i] = new Array(9);}

	var interactions_objectives = new Array(1);
	for ( var i=0; i<1; i++){interactions_objectives[i] = new Array(4);}

	var interactions_correct_responses = new Array(1);
	for ( var i=0; i<1; i++){interactions_correct_responses[i] = new Array(4);}
	
	var error_info = new Array(11);
	for ( var i=0; i<11; i++){error_info[i] = new Array(2);}

	var diagnostic_info = new Array(11);
	for ( var i=0; i<11; i++){diagnostic_info[i] = new Array(2);}

	var member_info = new Array(10);
	var error_init = new Array(2);
	var record_count = new Array(6);
	
	var currentErrorCode = "0";
	var isLMSInitialized = false;

	var nObjCount = 0;
	var nInteractCount = 0;
	var nInteractObjCount = 0;
	var ninteractCorResCount = 0;
	var nErrorCount = 0;
	var nDiagnosticCount = 0;

	/*******************************************************************************
								<CheckInitialization 함수>                              
	*******************************************************************************/
	function CheckInitialization()
	{
		if (isLMSInitialized==false)
		{
			currentErrorCode = "301";
		}
		return isLMSInitialized;
	}

	/*******************************************************************************
								<LMSInitialize 함수>                              
	*******************************************************************************/
	function LMSInitialize(emptyString)
	{
		var sResult;    
	    sResult = "false";
	    
	    if ((emptyString != null) && emptyString.length!=0 ) 	// emptyString 문자열검사	       
	    {			
	    	currentErrorCode = "201";	    	
	    }
	    else 
	    {	    			
	        if (isLMSInitialized==true)       		// 이미 Initialize 되어있으면
	        {				
	        	currentErrorCode = "101";
	    	}
	        else											// 이미 Initialize 되어있지않으면
	        {   
	        	var LMSDBConnect_check = LMSDBConnect();

	            if (LMSDBConnect_check == "false") 
	            {
	                currentErrorCode = "201";
	        	}
	            else
	            {
	                isLMSInitialized = true;      	// Initialize 세팅
	                currentErrorCode = "0";      	// 에러코드 Clear
	                sResult = "true";
	        	}
	    	}
		}		
	    return sResult;	    
	}

	/*******************************************************************************
								<LMSCommit 함수>                              
	*******************************************************************************/
	function LMSCommit(emptyString)
	{		
		var sResult="false";	  
	   	if ((emptyString!=null) && (emptyString.length!=0 ))	// emptyString 문자열검사	    
		{	
				currentErrorCode = "201";	        
				return sResult;		        
		}		
	    var CheckInitialization_check = CheckInitialization();	    	
	
	    if (CheckInitialization_check == false)
	    {	    	
	    	return sResult;
	    }	 
	    
		member_info[0] = "set";	
		LMSDBDisConnect();		
		sResult="true";
				
		return sResult;
	}


	/*******************************************************************************
								<LMSFinish 함수>                              
	*******************************************************************************/
	function LMSFinish(emptyString)
	{

	    var sResult = "false";
	    currentErrorCode = "0";
   	    
	   	if ((emptyString!=null) && (emptyString.length!=0 ))	// emptyString 문자열검사	    
		{	
				currentErrorCode = "201";	        
				return sResult;		        
		}		
	    else 
	    {
	    	var CheckInitialization_check = CheckInitialization();	    	

	    	if (CheckInitialization_check == true)
	    	{	
				if (user_sco_info[11] == "browse" )
				{
					var sTemp = LMSCommit(null);				
				}				
				isLMSInitialized = false;
				sResult = "true";
				user_id = "";
				user_name = "";	    						
	    	}
	    	else
	    	{				
	    		currentErrorCode = "301";
	    	}	    	
		}
	    return sResult;	    

	}


	/*******************************************************************************
								<LMSGetValue 함수>                              
	*******************************************************************************/
	function LMSGetValue(parameter)
	{		
		
		var sResult;
		var i;
		var sNum;
		
		sResult = "";
		currentErrorCode = "0";
		
		var CheckInitialization_check = CheckInitialization();	
	    
		if (CheckInitialization_check == false)
	    {
	    	return sResult;
	    }	    
	    if (parameter=="cmi.core._children") {
	    	sResult = course_children[0];
	    }
	    else if(parameter=="cmi.core.student_id") {
	    	sResult = member_info[1];
	    }
		else if(parameter=="cmi.core.student_name") {		    
		    sResult = member_info[2];
		}
		else if(parameter=="cmi.core.lesson_location") {
		    sResult = user_sco_info[3];
		}
		else if(parameter=="cmi.core.credit") {
			sResult = user_sco_info[4];
		}
		else if(parameter=="cmi.core.lesson_status") {
		    sResult = user_sco_info[5];
		}
		else if(parameter=="cmi.core.entry") {
		    sResult = user_sco_info[6];
		}
		else if(parameter=="cmi.core.score._children") {
		    sResult = course_children[1];
		}
		else if(parameter=="cmi.core.score.raw") {
		    sResult = user_sco_info[7];		
		}
		else if(parameter=="cmi.core.score.max") {
		    sResult = user_sco_info[8];
		}
		else if(parameter=="cmi.core.score.min") {
		    sResult = user_sco_info[9];
		}
		else if(parameter=="cmi.core.total_time") {
		    sResult = user_sco_info[10];
		}
		else if(parameter=="cmi.core.lesson_mode") {
		    sResult = user_sco_info[11];
		}
		else if(parameter=="cmi.core.exit") {
		    currentErrorCode = "404";
		}
		else if(parameter=="cmi.core.session_time") {
		    currentErrorCode = "404";
		}
		else if(parameter=="cmi.suspend_data") {
		    sResult = user_sco_info[14];
		}
		else if(parameter=="cmi.suspend_data._children") {
		    currentErrorCode = "202";
		}
		else if(parameter=="cmi.suspend_data._count") {
		    currentErrorCode = "203";
		}
		else if(parameter=="cmi.launch_data") {
		    sResult = course_info_sco[12];
		}
		else if(parameter=="cmi.launch_data._children") {
		    currentErrorCode = "202";
		}
		else if(parameter=="cmi.launch_data._count") {
		    currentErrorCode = "203";
		}
		else if(parameter=="cmi.comments") {
		    sResult = user_sco_info[15];
		}
		else if(parameter=="cmi.comments_from_lms") {
		    sResult = lecture_info_detail[7];
		}
		else if(parameter=="cmi.student_data._children") {
		    sResult = course_children[4];
		}
		else if(parameter=="cmi.student_data.mastery_score") {
		    sResult = course_info_sco[11];
		}
		else if(parameter=="cmi.student_data.max_time_allowed") {
		    sResult = course_info_sco[9];
		}
		else if(parameter=="cmi.student_data.time_limit_action") {
		    sResult = course_info_sco[10];
		}
		else if(parameter=="cmi.student_preference._children") {
		    sResult = course_children[5];
		}
		else if(parameter=="cmi.student_preference.audio") {
		    sResult = student_preference[1];
		}
		else if(parameter=="cmi.student_preference.language") {
		    sResult = student_preference[2];
		}
		else if(parameter=="cmi.student_preference.speed") {
		    sResult = student_preference[3];
		}
		else if(parameter=="cmi.student_preference.text") {
		    sResult = student_preference[4];	
    	}
        else
        {
        	if (parameter.length>=19){
	        	if( (parameter.substring(0,15) == "cmi.objectives." ) && (parameter.length >= 19) ) 
	 			{ 				
	 				if (parameter.indexOf("objectives._children")> -1) {
	 					sResult = course_children[2];
	 				}
	 				else if (parameter.indexOf("_count")> -1) {
	 					sResult = String(nObjCount);
	 				} 				
					else if ( (parameter.substring(parameter.length-3,parameter.length) == ".id") && (parameter.length >= 19) ) {  								
	 					sNum = GetNValue(parameter, 15);  					
	 					for (i=0; i<nObjCount; i++ ) { 					
	 						if (objectives[i][1] == sNum) {
	 							sResult = objectives[i][2];	 							
	 						}
	 					} 					 					
	 				}
	 				else if (parameter.indexOf("score._children")> -1) {
	 					sResult = course_children[3];
	 				} 	
	 				else if ( (parameter.substring(parameter.length-4,parameter.length) == ".raw") && (parameter.length >= 20) ) {
	 					sNum = GetNValue(parameter, 15);
	  					for (i=0; i<nObjCount; i++ ) { 					
	 						if (objectives[i][1] == sNum) {
	 							sResult = objectives[i][3];
	 						}
	 					}
	 				} 
	 				else if ( (parameter.substring(parameter.length-4,parameter.length) == ".max") && (parameter.length >= 20) ) {				 			
	 					sNum = GetNValue(parameter, 15);
	  					for (i=0; i<nObjCount; i++ ) { 					
	 						if (objectives[i][1] == sNum) {
	 							sResult = objectives[i][4];
	 						}
	 					} 					 					
	 				} 
	 				else if ( (parameter.substring(parameter.length-4,parameter.length) == ".min") && (parameter.length >= 20) ) { 				
	 					sNum = GetNValue(parameter, 15);
	  					for (i=0; i<nObjCount; i++ ) { 					
	 						if (objectives[i][1] == sNum) {
	 							sResult = objectives[i][5];
	 						}
	 					} 	
	 				} 
	 				else if ( (parameter.substring(parameter.length-7,parameter.length) == ".status") && (parameter.length >= 23) ) { 				 				
	 					sNum = GetNValue(parameter, 15);
	  					for (i=0; i<nObjCount; i++ ) { 					
	 						if (objectives[i][1] == sNum) {
	 							sResult = objectives[i][6];
	 						}
	 					} 	
	 				}
	 				else {
	 					currentErrorCode = "201";
	 				}
	 			}						
				
	 			else if( (parameter.substring(0,17) == "cmi.interactions.") && (parameter.length >= 21) ) 		 			
	 			{ 				
					if (parameter.indexOf("cmi.interactions._children")> -1) {
	 					sResult = course_children[6];
	 				}
	 				else if ( parameter.substring(parameter.length-7,parameter.length) == "._count") {
	 					if (parameter.indexOf("cmi.interactions._count")> -1) {
	 						sResult = nInteractCount;
	 					}
	 					else if ( parameter.substring(parameter.length-18,parameter.length) == ".objectives._count") {
	 						sResult = InteractionsObjCount(parameter);
	 					}
	 					else if ( parameter.substring(parameter.length-25,parameter.length) == ".correct_responses._count") {
	 						sResult = InteractionsCorResCount(parameter);
	 					}
	 					else {
	 						currentErrorCode = "201";
	 					}
	 				}
	 				else if (parameter.indexOf("id")> -1) {
	 					currentErrorCode = "404";
	 				}
	 				else if (parameter.indexOf("time")> -1) {
	 					currentErrorCode = "404";
	 				}
	 				else if (parameter.indexOf("type")> -1) {
	 					currentErrorCode = "404";
	 				}
	 				else if (parameter.indexOf("pattern")> -1) {
	 					currentErrorCode = "404";
	 				}
	 				else if (parameter.indexOf("weighting")> -1) {
	 					currentErrorCode = "404";
	 				}
	 				else if (parameter.indexOf("student_response")> -1) {
	 					currentErrorCode = "404";
	 				} 	
	 				else if (parameter.indexOf("result")> -1) {
	 					currentErrorCode = "404";
	 				} 	
	 				else if (parameter.indexOf("latency")> -1) {
	 					currentErrorCode = "404";
	 				} 	
	 				else {
	 					currentErrorCode = "404";
	 				} 	 				
	 			}
	 			else
	 			{
	 				currentErrorCode = "201";
	 			}
	 		}
	 		else{
	 			currentErrorCode = "201";
	 		}

		}
		return sResult;
	}





	/*******************************************************************************
								<LMSSetValue 함수>                              
	*******************************************************************************/
	function LMSSetValue(parameter, value)
	{
		
		
		var sResult = "true";
		var i;
		var bCheck = true;
		var bResult = true;	
		var sCredit;
		var sN1;
		var sN2;
		currentErrorCode = "0";


		var CheckInitialization_check = CheckInitialization();	   
		if (CheckInitialization_check == false)
		{
			sResult = "false";
			return sResult;
		}	
		
		if (user_sco_info[11] == "review")	//'재학습인지 확인
		{
			bCheck = false;
		}
				
		sCredit = user_sco_info[4];
		
		if ((bCheck == true) || (parameter == "cmi.core.lesson_location") || (parameter == "cmi.core.session_time"))
		{
		
			if(parameter == "cmi.core._children") {
				currentErrorCode = "402";
				sResult = "false";
			}
			else if(parameter == "cmi.core.student_id") {
				currentErrorCode = "403";
				sResult = "false";
			}
			else if(parameter == "cmi.core.student_name") {
				currentErrorCode = "403";
				sResult = "false";
			}		
			else if(parameter == "cmi.core.lesson_location") {			
				if (value == null){	
					user_sco_info[3] = value;
				}
				else if (value.length > 255) {
					currentErrorCode = "405";
					sResult = "false";
				}
				else {
					user_sco_info[3] = value;
				}
			}	
			else if(parameter == "cmi.core.credit") {
				currentErrorCode = "403";
				sResult = "false";
			}					
			
			else if(parameter == "cmi.core.lesson_status") {
				
				if ((value == "passed") || (value == "failed") || (value == "completed") || (value == "incomplete") || (value == "browsed")) {
					if (user_sco_info[11] != "browse") {					
						if (sCredit == "credit") {						
							if ( (value == "passed") || (value == "failed") || (value == "completed") ) {							
								if ( (user_sco_info[7] == "") || (course_info_sco[11] == "") || (user_sco_info[7]==null) || (course_info_sco[11]==null) || (user_sco_info[7].length==0 ) || (course_info_sco[11].length ==0) ) {
									user_sco_info[5] = value;
								}
								else {
									if ( eval(user_sco_info[7]) >= eval(course_info_sco[11]) ) {
										user_sco_info[5] = value;									
									}
									else {
										user_sco_info[5] = "failed";
									}								
								}
							}
							else {
								user_sco_info[5] = value;							
							}
						}
						else {
							user_sco_info[5] = value;
							
						}
					}
				}  		
				else {
					currentErrorCode = "405";
					sResult = "false";    	
				}
			}       

			else if(parameter == "cmi.core.entry") {
				currentErrorCode = "403";
				sResult = "false";
			}
			else if(parameter == "cmi.core.score._children") {
				currentErrorCode = "402";
				sResult = "false";
			}                 
			else if(parameter == "cmi.core.score.raw") {
				if (!(isNaN(value))) {    			
					if ( parseFloat(value) >= 0 ) {
						user_sco_info[7] = value;
					}
					else {
						currentErrorCode = "405";
						sResult = "false";			
					}    				
				}
				else {   
					currentErrorCode = "405";
					sResult = "false";
				}
			}                      
			else if(parameter == "cmi.core.score.max") {
				if (!(isNaN(value))) {   
					if ( parseFloat(value) >= 0 ) {
						user_sco_info[8] = value;
					}
					else {
						currentErrorCode = "405";
						sResult = "false";			
					}    				
				}
				else {    	
					currentErrorCode = "405";
					sResult = "false";
				}
			}                    
			else if(parameter == "cmi.core.score.min") {
				if (!(isNaN(value))) {
					if ( parseFloat(value) >= 0 ) {
						user_sco_info[9] = value;
					}
					else {
						currentErrorCode = "405";
						sResult = "false";			
					}    				
				}
				else {    	
					currentErrorCode = "405";
					sResult = "false";
				}
			}  
			else if(parameter == "cmi.core.total_time") {
				currentErrorCode = "403";
				sResult = "false";
			} 
			else if(parameter == "cmi.core.lesson_mode") {
				currentErrorCode = "403";
				sResult = "false";
			} 
			else if(parameter == "cmi.core.exit") {    		
				if ( (value==null) || (value.length==0) || (value == "") ){
					user_sco_info[12] = ""; 
				}
				else {
					if ( (value == "time-out") || (value == "suspend") || (value == "logout") || (value==null)  ) {//(value == "")) ) {	    
							user_sco_info[12] = value; 	    			
					}
					else {
						currentErrorCode = "405";
						sResult = "false";
					}
				}
			}
			else if(parameter == "cmi.core.session_time") {
				var SetSessionTime_check = SetSessionTime(value);	
				if (SetSessionTime_check != true) {
					currentErrorCode = "405";
					sResult = "false";
				}
				else {
					user_sco_info[13] = value;
				}
			}
			else if (parameter == "cmi.suspend_data") {
				if (value == null){
					user_sco_info[14] = value;
				}
				else if (value.length > 4096 ) {
					currentErrorCode = "405";
					sResult = "false";				
				}
				else {
					user_sco_info[14] = value;
				}
			}
			else if (parameter == "cmi.launch_data") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.comments") {
										
				if (value == null){
					value = "";
				} 			
				if ( (value.length + user_sco_info[15].length) <= 4096) {
					user_sco_info[15] = user_sco_info[15] + value;
				}
				else {
					currentErrorCode = "405";
					sResult = "false";					
				}			
			}                       
			else if (parameter == "cmi.comments_from_lms") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.comments_from_lms") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_data._children") {
				currentErrorCode = "402";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_data.mastery_score") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_data.max_time_allowed") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_data.time_limit_action") {
				currentErrorCode = "403";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_preference._children") {
				currentErrorCode = "402";
				sResult = "false";				
			}
			else if (parameter == "cmi.student_preference.audio") {
				if (!(isNaN(value))) {  
					if ( (parseFloat(value) >= -1) && (parseFloat(value) <=100)  ) {
						student_preference[1] = value;
					}
					else {
						currentErrorCode = "405";
						sResult = "false";					
					}
				}
				else {
					currentErrorCode = "405";
					sResult = "false";		
				}	
			}
			else if (parameter == "cmi.student_preference.language") {
				if (value==null) {value="";}
				if (value.length > 255 ) {
					currentErrorCode = "405";
					sResult = "false";				
				}
				else {
					student_preference[2] = value;
				}
			}  	
			else if (parameter == "cmi.student_preference.speed") {
				if (!(isNaN(value))) {  
					if ( (parseFloat(value) >= -100) && (parseFloat(value) <=100)  ) {
						student_preference[3] = value;
					}
					else {
						currentErrorCode = "405";
						sResult = "false";					
					}
				}
				else {
					currentErrorCode = "405";
					sResult = "false";		
				}	
			}
			else if (parameter == "cmi.student_preference.text") {
				if (!(isNaN(value))) {  
					if ( (parseFloat(value) >= -1) && (parseFloat(value) <=1)  ) {
					student_preference[4] = value;
					}
					else{
						currentErrorCode = "405";
						sResult = "false";						
					}
				}
				else {
					currentErrorCode = "405";
					sResult = "false";					
				}
			}

			else {					
				if (parameter.length>=19)

					
					if( (parameter.substring(0,15) == "cmi.objectives.") && (parameter.length >= 19) ) {				
						if (parameter == "cmi.objectives._children") {
							currentErrorCode = "402";
							sResult = "false";
						}
						else if (parameter == "cmi.objectives._count") {
							currentErrorCode = "402";
							sResult = "false";
						}
						else if ( (parameter.substring(parameter.length-3,parameter.length) == ".id") && (parameter.length >= 19) ) {
							
							if ( (value==null) || (value.length==0) ) {		 				
								currentErrorCode = "405";
								sResult = "false";	 						 			
							}
							else {
								if ( !(isNaN(parameter.substring(15,parameter.length-3))) ) {
									if ( value.length > 255 ) {
										currentErrorCode = "405";
										sResult = "false";		    	
									}
									else {							
										bResult = ObjectivesSetValue(parameter, value, 2); 	                        
										if (bResult == false) {
											currentErrorCode = "201";
											sResult = "false";                        
										}
									}
								}
								else {
									currentErrorCode = "201";
									sResult = "false";						
								}
							}
						}
						else if ( parameter.indexOf("score._children")> -1 ) {
							currentErrorCode = "402";
							sResult = "false";					
						}
	



						else if ( (parameter.substring(parameter.length-10,parameter.length) == ".score.raw") && (parameter.length >= 26) ) { 					 					
							if ( (!(isNaN(parameter.substring(15,parameter.length-10)))) &&  (!(isNaN(value))) ) {
								if ( (parseFloat(value)>=0) && (parseFloat(value)<= 100) ) {
									bResult = ObjectivesSetValue(parameter, value, 3);
									if (bResult == false) {
										currentErrorCode = "405";
										sResult = "false";                        
									}						
								}
								else {
									currentErrorCode = "405";
									sResult = "false"; 							
								}
							}
							else {
								currentErrorCode = "405";
								sResult = "false"; 						
							}
						}
						else if ( (parameter.substring(parameter.length-10,parameter.length) == ".score.max") && (parameter.length >= 26) ) { 				
							if ( (!(isNaN(parameter.substring(15,parameter.length-10)))) &&  (!(isNaN(value))) ) {  					 
								if ( (parseFloat(value)>=0) && (parseFloat(value)<= 100) ) {
									bResult = ObjectivesSetValue(parameter, value, 4);
									if (bResult == false) {
										currentErrorCode = "405";
										sResult = "false";                        
									}						
								}
								else {
									currentErrorCode = "405";
									sResult = "false"; 							
								}
							}
							else {
								currentErrorCode = "405";
								sResult = "false"; 						
							}
						}					
						else if ( (parameter.substring(parameter.length-10,parameter.length) == ".score.min") && (parameter.length >= 26) ) {
							if ( (!(isNaN(parameter.substring(15,parameter.length-10)))) &&  (!(isNaN(value))) ) { 
								if ( (parseFloat(value)>=0) && (parseFloat(value)<= 100) ) {
									bResult = ObjectivesSetValue(parameter, value, 5);
									if (bResult == false) {
										currentErrorCode = "405";
										sResult = "false";                        
									}						
								}
								else {
									currentErrorCode = "405";
									sResult = "false"; 							
								}
							}
							else {
								currentErrorCode = "405";
								sResult = "false"; 						
							}
						}			
						else if ( (parameter.substring(parameter.length-7,parameter.length) == ".status") && (parameter.length >= 23) ) { 					
							if ( !(isNaN(parameter.substring(15,parameter.length-7))) ) { 
							
								if ( value == "passed" || value == "failed" || value == "completed" || value == "incomplete" || value == "browsed" || value == "not attempted" ) {
								
									bResult = ObjectivesSetValue(parameter, value, 6);
									
									if (bResult == false) {
									currentErrorCode = "201";
									sResult = "false";                        
									}
								}
								else
								{
								currentErrorCode = "405";
								sResult = "false"; 		                    		
								}
							}
							else {
								currentErrorCode = "201";
								sResult = "false"; 						
							}
						}
						else {
							currentErrorCode = "201";
							sResult = "false";		
						}
							
					}   			
		
					else if( (parameter.substring(0,17) == "cmi.interactions.") && (parameter.length >= 21) ) {	

						if (parameter.indexOf("_children")> -1) {
							currentErrorCode = "402";
							sResult = "false";			
						}
						else if (parameter.indexOf("_count")> -1) {
							currentErrorCode = "402";
							sResult = "false";			
						}
						else if ( parameter.substring(parameter.length-3,parameter.length) == ".id")  {

							if ( (parameter.indexOf(".objectives.") > -1) && (parameter.length >= 34) ) {						
								sN1 = GetNValue(parameter, 17);
								sN2 = GetNValue(parameter, 29 + sN1.length);                       

								if(	 (!(isNaN(sN1)))	&& (!(  isNaN(sN2)  )) ){
								
									if ((value=="") || (value==null) || (value.length > 255) ) {
										currentErrorCode = "405";
										sResult = "false";		    	
									}
									else {									
										bResult = InteractionsObjectivesSetValue(parameter, value, sN1, sN2);
										if ( bResult == false) {
											currentErrorCode = "201";
											sResult = "false";
										}
									}	                    	
								}								
								else {
									currentErrorCode = "201";
									sResult = "false";
								}                        
							}
							else if ( parameter.length >= 21 ) {	

								if ( !(isNaN(parameter.substring(17,parameter.length-3))) )  { 	                            
									if ((value=="") || (value==null) || (value.length > 255) ) {
										currentErrorCode = "405";
										sResult = "false";		    	
									}
									else {
										bResult = InteractionsSetValue(parameter, value, 2);                          	
										if ( bResult == false) {
											currentErrorCode = "201";
											sResult = "false";
										} 
									}	                            
								}
								else {
									currentErrorCode = "201";
									sResult = "false";
								}                        
							}
							else {
								currentErrorCode = "201";
								sResult = "false";				
							}
						}				



						else if ( (parameter.substring(parameter.length-5,parameter.length) == ".time")  && (parameter.length >= 23) ) {
							if ( !(isNaN(parameter.substring(17,parameter.length-5))) )  { 
								
								if ( CMITime_Check(value) ) {
									bResult = InteractionsSetValue(parameter, value, 3);
									if ( bResult == false) {
										currentErrorCode = "201";
										sResult = "false";
									} 
								}
								else {
									currentErrorCode = "405";
									sResult = "false";		                    	
								}
							}
							else {
								currentErrorCode = "201";
								sResult = "false";
							}                  
						}


						else if ( (parameter.substring(parameter.length-5,parameter.length) == ".type")  && (parameter.length >= 23) ) {		
							
							if ( !(isNaN(parameter.substring(17,parameter.length-5))) )  {
								
								if ( (value!=null) && (value == "true-false" || value == "choice" || value == "fill-in" || value == "matching" || value == "performance" || value == "sequencing" || value == "likert" || value == "numeric") ) {
									bResult = InteractionsSetValue(parameter, value, 4);
									if ( bResult == false) {
										currentErrorCode = "201";
										sResult = "false";
									} 
								}
								else {
									currentErrorCode = "405";
									sResult = "false";     								
								}	                    
							
							}
							else {
								currentErrorCode = "201";
								sResult = "false";                    
							}
						}				
						else if ( (parameter.indexOf(".correct_responses.")> -1) && (parameter.substring(parameter.length-8,parameter.length) == ".pattern")  && (parameter.length >= 46) ) {
							sN1 = GetNValue(parameter, 17);
							sN2 = GetNValue(parameter, 36 + sN1.length);		
							if ( !(isNaN(sN1) ) && !(isNaN(sN2)) ) { 
								bResult = InteractionsResponsesSetValue(parameter, value, sN1, sN2);
								if ( bResult == false) {
									currentErrorCode = "201";
									sResult = "false";
								} 
							}
							else {
								currentErrorCode = "201";
								sResult = "false";
							}   
						}		
						
						else if ( (parameter.substring(parameter.length-10,parameter.length) == ".weighting")  && (parameter.length >= 28) ) {
							if ( !(isNaN(parameter.substring(17,parameter.length-10))) )  { 	
								if ( !(isNaN(value))) {				
									bResult = InteractionsSetValue(parameter, value, 5);
									if ( bResult == false) {
										currentErrorCode = "201";
										sResult = "false";
									} 
								}
								else {
									currentErrorCode = "405";
									sResult = "false";		                		
								}
							
							}
							else {
								currentErrorCode = "201";
								sResult = "false";
							}                  					
						}
						else if ( (parameter.substring(parameter.length-17,parameter.length) == ".student_response")  && (parameter.length >= 35) ) {
							if ( !(isNaN(parameter.substring(17,parameter.length-17))) )  { 					
								bResult = InteractionsSetValue(parameter, value, 6);
								if ( bResult == false) {
									currentErrorCode = "201";
									sResult = "false";
								} 
							}
							else {
								currentErrorCode = "201";
								sResult = "false";
							}                  					
						}					
						else if ( (parameter.substring(parameter.length-7,parameter.length) == ".result")  && (parameter.length >= 25) ) {					
							if ( !(isNaN(parameter.substring(17,parameter.length-7))) )  { 
								if ( value == "correct" || value == "wrong" || value == "unanticipated" || value == "neutral" || !(isNaN(value)) ) {	
									bResult = InteractionsSetValue(parameter, value, 7);
									if ( bResult == false) {
										currentErrorCode = "201";
										sResult = "false";
									} 
								}
								else {
									currentErrorCode = "405";
									sResult = "false";		                  		
								}
							}
							else {
								currentErrorCode = "201";
								sResult = "false";
							}                  					
						}	
						else if ( (parameter.substring(parameter.length-8,parameter.length) == ".latency")  && (parameter.length >= 26) ) {
							if ( !(isNaN(parameter.substring(17,parameter.length-8))) )  { 	
								if ( CMITimespan_check(value) ) {					
									bResult = InteractionsSetValue(parameter, value, 8);
									if ( bResult == false) {
										currentErrorCode = "201";
										sResult = "false";	                            
									}
								}
								else {
									currentErrorCode = "405";
									sResult = "false";		                  		
								}
							}
							else {
								currentErrorCode = "201";
								sResult = "false";      	                                                  
							}
							
							
						}
						else {
							currentErrorCode = "201";
							sResult = "false";		                    	
						}	
				
					}
					
					else {			
						currentErrorCode = "201";
						sResult = "false";	                
					}
				else {			
				currentErrorCode = "201";
				sResult = "false";
					  
				}
			} //end select 
			
		}		

		return sResult;
	}



	/*******************************************************************************
							<LMSDBConnect 함수>  	
	*******************************************************************************/
	function LMSDBConnect()
	{
		return "true";
	}


	/*******************************************************************************
							<LMSDBDisConnect 함수>  	
	*******************************************************************************/
	function LMSDBDisConnect()
	{
		for ( var i=0; i<course_children.length; i++)	{ course_children[i]=course_children[i]+"*"; }	
		document.apiForm.course_children.value=course_children;
		
		for ( var i=0; i<course_info_sco.length; i++)	{ course_info_sco[i]=course_info_sco[i]+"*"; }	
		document.apiForm.course_info_sco.value=course_info_sco;
			
		for ( var i=0; i<lecture_info_detail.length; i++)	{ lecture_info_detail[i]=lecture_info_detail[i]+"*"; }	
		document.apiForm.lecture_info_detail.value=lecture_info_detail;
		
		for ( var i=0; i<user_sco_info.length; i++)	{ user_sco_info[i]=user_sco_info[i]+"*"; }	
		document.apiForm.user_sco_info.value=user_sco_info;

		for ( var i=0; i<student_preference.length; i++)	{ student_preference[i]=student_preference[i]+"*"; }	
		document.apiForm.student_preference.value=student_preference;

		

		for ( var i=0; i<objectives.length; i++)	{ 		
			for(var j=0; j<7; j++){objectives[i][j]=objectives[i][j]+"@"; }
			objectives[i]=objectives[i]+"*"; }	
		document.apiForm.objectives.value=objectives;

		for ( var i=0; i<interactions.length; i++){ 		
			for(var j=0; j<9; j++){interactions[i][j]=interactions[i][j]+"@"; }
			interactions[i]=interactions[i]+"*"; }	
		document.apiForm.interactions.value=interactions;

        
		for ( var i=0; i<interactions_objectives.length; i++){ 		
			for(var j=0; j<4; j++){interactions_objectives[i][j]=interactions_objectives[i][j]+"@"; }
			interactions_objectives[i]=interactions_objectives[i]+"*"; }	
		document.apiForm.interactions_objectives.value=interactions_objectives;
		
		for ( var i=0; i<interactions_correct_responses.length; i++){ 		
			for(var j=0; j<4; j++){interactions_correct_responses[i][j]=interactions_correct_responses[i][j]+"@"; }
			interactions_correct_responses[i]=interactions_correct_responses[i]+"*"; }	
		document.apiForm.interactions_correct_responses.value=interactions_correct_responses;

		for ( var i=0; i<error_info.length; i++){ 		
			for(var j=0; j<2; j++){error_info[i][j]=error_info[i][j]+"@"; }
			error_info[i]=error_info[i]+"*"; }	
		document.apiForm.error_info.value=error_info;

		for ( var i=0; i<2; i++){ 		
			for(var j=0; j<diagnostic_info[j].length; j++){diagnostic_info[i][j]=diagnostic_info[i][j]+"@"; }
			diagnostic_info[i]=diagnostic_info[i]+"*"; }	
		document.apiForm.diagnostic_info.value=diagnostic_info;



		for ( var i=0; i<member_info.length; i++)	{ member_info[i]=member_info[i]+"*"; }	
		document.apiForm.member_info.value=member_info;
		

		for ( var i=0; i<error_init.length; i++)	{ error_init[i]=error_init[i]+"*"; }	
		document.apiForm.error_init.value=error_init;

		for ( var i=0; i<record_count.length; i++)	{ record_count[i]=record_count[i]+"*"; }	
		document.apiForm.record_count.value=record_count;
		
		
		apiForm.method	= "post";
		apiForm.target		= "lmsHiddenFrame";
		//apiForm.action	= "/servlet/controller.contents.SetApiServlet";
		//alert("ddddd");
		apiForm.action	= "API_Insert.jsp";
		apiForm.submit();

	}


	/*******************************************************************************
							<GetNValue 함수>  	
	*******************************************************************************/
	/* n값을 축출해낸다.  ex)cmi.interactions.0.id 이면 0값을 축출해냄 */
	function GetNValue(value, n)
	{	
		var sResult = "";
		var nNPos = 0;    	    		
		while ( (value.substring(n + nNPos, n + nNPos + 1) != "."))
		{	
		    sResult = sResult + value.substring(n + nNPos,  n + nNPos + 1);
		    nNPos = nNPos + 1;
		}			
		return sResult;

	}

	/*******************************************************************************
								<Count 함수>    	                          
	*******************************************************************************/
	/*	InteractionsObjCount의 count값을 return해준다.*/
	function InteractionsObjCount(parameter)
	{
	    var nCount=0;
	    var i=0;
	    var sN1=null;
	        
	    sN1 = GetNValue(parameter, 17);
	    for (i=0; i<nInteractObjCount; i++)
	    {
	        if ( (interactions_objectives[i][0] == user_sco_info[0]) && (interactions_objectives[i][1] == sN1) )
	        {
	            nCount = nCount + 1;
	    	}
		}
	    return nCount;	    
	}
	
	/*	InteractionsCorResCount count값을 return해준다.*/
	function InteractionsCorResCount(parameter)
	{
	    var nCount=0;
	    var i=0;
	    var sN1=null;
	        
	    sN1 = GetNValue(parameter, 17);
	    for (i=0; i<ninteractCorResCount; i++)
	    {
	        if ( (interactions_correct_responses[i][0] == user_sco_info[0]) && (interactions_correct_responses[i][1] == sN1) )
	        {
	            nCount = nCount + 1;
	    	}
		}    
	    return nCount;
	}





	/*******************************************************************************
								<object관련 SetValue 함수>      
	*******************************************************************************/
	/*	ObjectivesSetValue의 값을 배열에 넣는다. */
	function ObjectivesSetValue(parameter, value, n)
	{
	    var bExist = false;
	    var bCheck = true;
	    var sNum= GetNValue(parameter, 15);
	    
		nObjCount = parseInt(nObjCount);

		if ( (parseInt(sNum)>=0) && (parseInt(sNum) <= nObjCount) )        
		{	    	
			for ( var i=0; i<nObjCount; i++)		//'기존에 입력된 n값이 있으면 업데이트
			{
				if ( (objectives[i][0] == user_sco_info[0]) && (objectives[i][1] == sNum) ) {
					bExist = true;
					objectives[i][n] = value;
				}
			}
			if (bExist==false) {
				nObjCount = nObjCount + 1;   
				record_count[0] = nObjCount;	          					
									 
				objectives[nObjCount - 1][0] = user_sco_info[0];
				objectives[nObjCount - 1][1] = sNum;
				objectives[nObjCount - 1][2] = "";
				objectives[nObjCount - 1][3] = "";
				objectives[nObjCount - 1][4] = "";
				objectives[nObjCount - 1][5] = "";
				objectives[nObjCount - 1][6] = "";
				objectives[nObjCount - 1][n] = value;
			}
			bCheck = true;
		}
		else {
			bCheck = false;
		}
		    
	    return bCheck;
	}

	/*	InteractionsSetValue의 값을 배열에 넣는다.
		parameter: cmi data model 명, value: 세팅값, n: 2차원배열에서 저장될 배열 */
	function InteractionsSetValue(parameter, value, n){
		var bExist = false;
	    var bCheck = true;
	    var sNum = GetNValue(parameter, 17);
		
		nInteractCount = parseInt(nInteractCount);

	    if ( parseInt(sNum) >= 0 && parseInt(sNum) <= nInteractCount )
	    {  	
			for ( var i=0; i<nInteractCount; i++)		//기존에 입력된 n값이 있으면 업데이트
	    	{	
	    		if ( interactions[i][0] == user_sco_info[0] && interactions[i][1] == sNum)  
				{
	                bExist = true;
	                interactions[i][n] = value;	 					
	    		}
	    	}	   
			if (bExist==false) 
			{
				nInteractCount = nInteractCount + 1;  
	            record_count[1] = nInteractCount;			

	            interactions[nInteractCount - 1][0] = user_sco_info[0];
	            interactions[nInteractCount - 1][1] = sNum;
	            interactions[nInteractCount - 1][2] = "";
	            interactions[nInteractCount - 1][3] = "";
	            interactions[nInteractCount - 1][4] = "";
	            interactions[nInteractCount - 1][5] = "";
	            interactions[nInteractCount - 1][6] = "";
	            interactions[nInteractCount - 1][7] = "";
	            interactions[nInteractCount - 1][8] = "";
	            interactions[nInteractCount - 1][n] = value;	         
				
			}
			bCheck = true;
	    }
	    else {			
	    	bCheck = false;
	    }	    
	    return bCheck;        
	}

	/*	InteractionsObjectivesSetValue의 값을 배열에 넣는다. */
	function InteractionsObjectivesSetValue(parameter, value, sN1, sN2) {
	    var bExist = false;
	    var bCheck = true;
	    var i = 0;

		nInteractCount = parseInt(nInteractCount);
		nInteractObjCount = parseInt(nInteractObjCount);
	    
	    if ( (parseInt(sN1) >= 0) && (parseInt(sN1) <= nInteractCount) && (parseInt(sN2) >= 0) && (parseInt(sN2) <= nInteractObjCount) ) {
	    	for ( i=0; i<nInteractObjCount; i++)
	    	{
	    		if ( (interactions_objectives[i][0] == user_sco_info[0]) && (interactions_objectives[i][1] == sN1) && (interactions_objectives[i][2] == sN2) ) {
	                bExist = true;
	                interactions_objectives[i][3] = value;
	    		}
	    	}    	
			if (bExist==false) {
	            nInteractObjCount = nInteractObjCount + 1;     
	            record_count[2] = nInteractObjCount;

	            interactions_objectives[nInteractObjCount - 1][0] = user_sco_info[0];
	            interactions_objectives[nInteractObjCount - 1][1] = sN1;
	            interactions_objectives[nInteractObjCount - 1][2] = sN2;
	            interactions_objectives[nInteractObjCount - 1][3] = value;        
			}
			bCheck = true;
	    }
	    else {
	    	bCheck = false;
	    }
	    return bCheck; 	
	}

	/*	InteractionsResponsesSetValue의 값을 배열에 넣는다.
		parameter: data model명, value: 세팅값, sN1:배열 첫자리순서, sN2:배열 두번째자리 순서 */
	function InteractionsResponsesSetValue(parameter, value, sN1, sN2) {
	    var bExist = false;
	    var bCheck = true;
	    var i = 0;

		nInteractCount = parseInt(nInteractCount);
		ninteractCorResCount = parseInt(ninteractCorResCount);
  
	    if ( (parseInt(sN1) >= 0) && (parseInt(sN1) <= nInteractCount) && (parseInt(sN2) >= 0) && (parseInt(sN2) <= ninteractCorResCount) ) {
	    	for ( i=0; i<ninteractCorResCount; i++)
	    	{
	    		if ( (interactions_correct_responses[i][0] == user_sco_info[0]) && (interactions_correct_responses[i][1] == sN1) && (interactions_correct_responses[i][2] == sN2) ) {
	                bExist = true;
	                interactions_correct_responses[i][3] = value;
	    		}
	    	}    	
			if (bExist==false) {
	            ninteractCorResCount = ninteractCorResCount + 1;   
	            record_count[3] = ninteractCorResCount;
	
	            interactions_correct_responses[ninteractCorResCount - 1][0] = user_sco_info[0];
	            interactions_correct_responses[ninteractCorResCount - 1][1] = sN1;
	            interactions_correct_responses[ninteractCorResCount - 1][2] = sN2;
	            interactions_correct_responses[ninteractCorResCount - 1][3] = value;        
			}
			bCheck = true;
	    }
	    else {
	    	bCheck = false;
	    }
	    return bCheck;  	
	    	
	}


	/*******************************************************************************
								<SetSessionTime  함수>                              
	*******************************************************************************/
	function SetSessionTime(value) 
	{
	
	    var i = 0;
	    var j = 0;
	    var bCheck = true;
	    var sTemp = new Array(4); 
		sTemp[0]="";
		sTemp[1]="";
		sTemp[2]="";
		sTemp[3]="";
		
		for (i=0; i < value.length; i++)			// 구분
		{
			if (  (value.substring(i,i+1) != ":") && (value.substring(i,i+1) != ".") ) {
				sTemp[j] = sTemp[j] + value.substring(i,i+1);		
			}
			else {
				j = j +1;
			}			
		}


		if ( (j<2) || (j>3) )							//'데이타체크
		{
			bCheck = false;
		}
		else
		{		
			for ( i=0; i<=j; i++)
			{            
	            if ( isNaN(sTemp[i]) ) {			//'숫자값체크
	            	bCheck = false;               
	            }
	            
	            if ( i == 0 ) {							//'길이체크(시)
	            	if ( (sTemp[i].length > 4) || (sTemp[i].length <2) ) {
	            		bCheck = false;            	
	            	}
	            }            
	            else if ((i==1) || (i==2)) {			//'길이체크(분,초)
	            	if ( sTemp[i].length != 2 ) {
	            		bCheck = false;            		
	            	}
	            }            
	            else {									//'길이체크
	            	if ( (sTemp[i].length > 2) && (sTemp[i].length<1) ) {
	            		bCheck = false;            		
	            	}            		
	        	}
			}
		}
		
		if ( bCheck == true ) 
		{			
			if (value.substring(2,3) == ":")  {
				value = "00"+value;		
			}
			if (value.substring(3,4) == ":")  {
				value = "0"+value;
			}
			if ( value.length == 10 ) {
				value = value + ".00";
			}
			if ( value.length == 12 ) {
				value = value + "0";
			}

			if ( user_sco_info[10].substring(12,13) == "*" ) {				
				user_sco_info[10] = user_sco_info[10].substring(0,12) + "0*";
			}
			
			var nHour = eval(user_sco_info[10].substring(0,4)) + eval(value.substring(0,4));
			var nMinute = eval(user_sco_info[10].substring(5,7)) + eval(value.substring(5,7));
			var nSecond = eval(user_sco_info[10].substring(8,10)) + eval(value.substring(8,10));
			var nMiliSecond = eval(user_sco_info[10].substring(11,13)) + eval(value.substring(11,13));

			var sSecond = null;
			var sMiliSecond = null;
			var sMinute = null;
			
			//'------- 초(밀리)단위계산 -------
			if (nMiliSecond >= 100) {
				nSecond = nSecond + 1;
	            nMiliSecond = nMiliSecond - 100;
			}
			if (nMiliSecond < 10) {
				sMiliSecond = "0" + String(nMiliSecond);
			}
			else
			{
				sMiliSecond = String(nMiliSecond);
			}
	
			//'------- 초단위계산 -------
			if (nSecond >= 60) {
				nMinute = nMinute + 1;
	            nSecond = nSecond - 60;
			}
			if (nSecond < 10) {
				sSecond = "0" + String(nSecond);
			}
			else
			{
				sSecond = String(nSecond);
			}
			//'------- 분단위계산 -------
			if (nMinute >= 60) {
				nHour = nHour + 1;
	            nMinute = nMinute - 60;
			}
			if (nMinute < 10) {
				sMinute = "0" + String(nMinute);
			}
			else
			{
				sMinute = String(nMinute);
			}		
	
			//'------- 시간단위계산 -------			
			var nLength = String(nHour).length;			
			var sHour=null;
			if ( nLength == 1) {
				sHour = "000" + String(nHour);
			}
			else if ( nLength == 2) {
				sHour = "00" + String(nHour);
			}
			else if ( nLength == 3) {
				sHour = "0" + String(nHour);
			}
			else {
				sHour = String(nHour);
			}
			
			user_sco_info[10] = sHour + ":" + sMinute + ":" + sSecond + "." + sMiliSecond;		
		}	

		return bCheck;
	}	
	

	/*******************************************************************************
								<시간관련 함수>    	                          
	*******************************************************************************/
	/*	CMITime형인 check하는 함수*/	
	function CMITime_Check(value) 
	{		
	
		var ele_name;
		ele_name = value.split(":");

		var eleTokens = ele_name.length;

		if ( eleTokens == 3) 						//":"로 구분한 인자는 3개여야 함
		{					
			if ( (ele_name[0].length==2) && (ele_name[1].length==2) && (ele_name[2].length>=2) && (ele_name[2].length <=5) ) 		
			{
				if ( !(isNaN(ele_name[0])) && !(isNaN(ele_name[1])) && !(isNaN(ele_name[2])) ) 
				{
					var iHour = eval(ele_name[0]);
					if ( iHour < 0 || iHour > 24 ) {
						return false;
					}
					
					var iMin = eval(ele_name[1]);
					if ( iMin < 0 || iMin > 60 ) {
						return false;
					}
					
					var iSec = parseFloat(ele_name[0]);
					if ( iSec < 0 || iSec > 60 ) {
						return false;
					}
					
					return true;							
				}
				else
				{
					return false;
				}
			}
			else  {
				return false;
			}
		}
		else {
			return false;
		}
	}



	/*	CMITimespan형인 check하는 함수 */	
	function CMITimespan_check(value) 
	{		

		var ele_name;
		ele_name = value.split(":");

	
		var eleTokens = ele_name.length;

		if ( eleTokens == 3) 						//":"로 구분한 인자는 3개여야 함
		{						
			if ( (ele_name[0].length>=2) && (ele_name[0].length<=4) && (ele_name[1].length==2) && (ele_name[2].length>=2) && (ele_name[2].length <=5) ) 		
			{
				if ( !(isNaN(ele_name[0])) && !(isNaN(ele_name[1])) && !(isNaN(ele_name[2])) ) 
				{				
					var iSec = parseFloat(ele_name[2]);

					if ( iSec < 0 || iSec > 60 ) {					
						return false;						
					}
					
					if ( ele_name[2].length >= 3 ) {
						if ( !(ele_name[2].substring(2,3) == ".") ) {
							return false;
						}
					}						
					return true;								
				}
				else
				{					
					return false;
				}
			}
			else  {				
				return false;
			}
		}
		else {			
			return false;
		}
	}	


/*******************************************************************************
							<Error 관련 함수>                              
*******************************************************************************/
	function LMSGetLastError()
	{
	    return currentErrorCode;
	}        
	
	
	function LMSGetErrorString(errorCode)
	{
	    return ErrorCodes(errorCode);
	}     
	
	
	function ErrorCodes(errorCode)
	{		
	    var sResult="";
	    var i=0;
	        
		for (i=0; i < nErrorCount; i++)
		{
			if ( error_info[i][0] == errorCode )
			{
			    sResult = error_info[i][1];
			}
		}	    
	    return sResult;	
	}	
	
	
	function LMSGetDiagnostic(errorCode)
	{
	    return DiagnosticErrorCodes(errorCode);
	}
	
	
	function DiagnosticErrorCodes(errorCode)
	{		
	    var sResult="";
	    var i=0;
	        
		for (i=0; i < nDiagnosticCount; i++)
		{
			if ( diagnostic_info[i][0] == errorCode ) 
			{
			    sResult = diagnostic_info[i][1];
			}
		}	    
	    return sResult;
	}
	


	function init()	
	{  		
		 API = top.lmsApiFrame;					
	}
	

	function timeMsg()
	{
		
//		var userNameMsg = member_info[2];
		
//		if (userNameMsg.indexOf("*")> -1)	//만약에 commit을 하지 않고 종료하면
//			userNameMsg = userNameMsg.substring(0,userNameMsg.length-1)	
		
		//userNameMsg = userNameMsg.replace("*", "");  // "|"을 없앤다.

 //       var userNameMsg_2 = "";
//		for ( i = 0; i < userNameMsg.length ; i++ )
//		{
//				if ( userNameMsg.charAt(i) != "*" ) {
//					userNameMsg_2 = userNameMsg_2 + userNameMsg.charAt(i)		
//				}
//		} 

//		userNameMsg = userNameMsg_2;
				
//		var strStudy_time = user_sco_info[13];
		
		/* 시간 추출해 내기 */	
//		var str_time_		 	= "";
//		var str_minute_     	= "";
//		var intTemp 			= 0;
					
//		if (strStudy_time == null || strStudy_time == "" )
//		{		
//			strStudy_time	=  "0분";								
//		}
//		else{
						
//			var sessionTimeMsg = strStudy_time.split(":");
			
//			var eleTokens = sessionTimeMsg.length;
		
//			if ( eleTokens >2){ 							//":"로 구분한 인자는 3개 이상이어야 함			
//				str_time_ 		=  sessionTimeMsg[0]
//				str_minute_		=  sessionTimeMsg[1];						
				
//				intTemp = eval(str_time_)*60;
//				intTemp = intTemp+eval(str_minute_);						
//				strStudy_time = String(intTemp)+"분";						
//			}
//			else{
//				strStudy_time = strStudy_time+"분";
//			}
//		}	

//		alert(userNameMsg+" 님이 학습하신 시간은 "+strStudy_time+"입니다.");


		for ( var i=0; i<course_children.length; i++)	{ course_children[i]=course_children[i]+"*"; }	
		document.apiForm.course_children.value=course_children;

		
		
		for ( var i=0; i<course_info_sco.length; i++)	{ course_info_sco[i]=course_info_sco[i]+"*"; }	
		document.apiForm.course_info_sco.value=course_info_sco;
			
		for ( var i=0; i<lecture_info_detail.length; i++)	{ lecture_info_detail[i]=lecture_info_detail[i]+"*"; }	
		document.apiForm.lecture_info_detail.value=lecture_info_detail;
		
		for ( var i=0; i<user_sco_info.length; i++)	{ user_sco_info[i]=user_sco_info[i]+"*"; }	
		document.apiForm.user_sco_info.value=user_sco_info;

		for ( var i=0; i<student_preference.length; i++)	{ student_preference[i]=student_preference[i]+"*"; }	
		document.apiForm.student_preference.value=student_preference;

		

/*		for ( var i=0; i<objectives.length; i++)	{ 		
			for(var j=0; j<7; j++){objectives[i][j]=objectives[i][j]+"@"; }
			objectives[i]=objectives[i]+"*"; }	
		document.apiForm.objectives.value=objectives;

		for ( var i=0; i<interactions.length; i++){ 		
			for(var j=0; j<9; j++){interactions[i][j]=interactions[i][j]+"@"; }
			interactions[i]=interactions[i]+"*"; }	
		document.apiForm.interactions.value=interactions;

        
		for ( var i=0; i<interactions_objectives.length; i++){ 		
			for(var j=0; j<4; j++){interactions_objectives[i][j]=interactions_objectives[i][j]+"@"; }
			interactions_objectives[i]=interactions_objectives[i]+"*"; }	
		document.apiForm.interactions_objectives.value=interactions_objectives;
		
		for ( var i=0; i<interactions_correct_responses.length; i++){ 		
			for(var j=0; j<4; j++){interactions_correct_responses[i][j]=interactions_correct_responses[i][j]+"@"; }
			interactions_correct_responses[i]=interactions_correct_responses[i]+"*"; }	
		document.apiForm.interactions_correct_responses.value=interactions_correct_responses; */

		for ( var i=0; i<error_info.length; i++){ 		
			for(var j=0; j<2; j++){error_info[i][j]=error_info[i][j]+"@"; }
			error_info[i]=error_info[i]+"*"; }	
		document.apiForm.error_info.value=error_info;

	/*	for ( var i=0; i<2; i++){ 		
			for(var j=0; j<diagnostic_info[j].length; j++){diagnostic_info[i][j]=diagnostic_info[i][j]+"@"; }
			diagnostic_info[i]=diagnostic_info[i]+"*"; }	
		document.apiForm.diagnostic_info.value=diagnostic_info;
*/


		for ( var i=0; i<member_info.length; i++)	{ member_info[i]=member_info[i]+"*"; }	
		document.apiForm.member_info.value=member_info;
		

		for ( var i=0; i<error_init.length; i++)	{ error_init[i]=error_init[i]+"*"; }	
		document.apiForm.error_init.value=error_init;

		for ( var i=0; i<record_count.length; i++)	{ record_count[i]=record_count[i]+"*"; }	
		document.apiForm.record_count.value=record_count;
		
		
		apiForm.method	= "post";
		apiForm.target		= "lmsHiddenFrame";
		//apiForm.action	= "/servlet/controller.contents.SetApiServlet";
		//alert("ddddd");
		apiForm.action	= "API_Insert.jsp";
		apiForm.submit();

	}

	
	
</SCRIPT>
<TITLE>API</TITLE>
</HEAD>

<BODY >

<iframe name="lmsHiddenFrame" frameBorder=1 width=300 height=150></iframe>
<iframe name="lmsGetHiddenFrame" frameBorder=1 width=300 height=100></iframe>

<form name = "apiForm">
<input type="hidden" name="course_children">
<input type="hidden" name="course_info_sco">
<input type="hidden" name="lecture_info_detail">
<input type="hidden" name="user_sco_info">
<input type="hidden" name="student_preference">

<input type="hidden" name="objectives">
<input type="hidden" name="interactions">
<input type="hidden" name="interactions_objectives">
<input type="hidden" name="interactions_correct_responses">
<input type="hidden" name="error_info">
<input type="hidden" name="diagnostic_info">

<input type="hidden" name="member_info">
<input type="hidden" name="error_init">
<input type="hidden" name="record_count">

<script language="javascript">
<!--
	
	//서블릿에 인자값과 함께 호출한후 모든 값을 가져온후 자바스크립트의 배열에 값을 넘겨준다.
	
	var p_userid = "<%=p_userid%>";
	var p_oid = "<%=p_oid%>";
	var commit_value = "<%=commit_value%>";
	var p_subj = "<%=p_subj%>";
	var p_lesson = "<%=p_lesson%>";
	var p_year = "<%=p_year%>";
	var p_subjseq = "<%=p_subjseq%>";
	var s_name = "<%=s_name%>";
		lmsGetHiddenFrame.document.location.href="/servlet/controller.contents.GetApiServlet?userid="+p_userid+"&p_oid="+p_oid+"&commit_value="+commit_value+"&p_subj="+p_subj+"&p_lesson="+p_lesson+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&username="+s_name;
	
//-->
</script>
</form>
</BODY>	
</HTML>