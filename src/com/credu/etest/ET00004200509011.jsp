
<%@ page contentType='text/html;charset=MS949'%>
<%@ page errorPage='/learn/library/error.jsp'%>
<%@ page import='java.util.*'%>
<%@ page import='java.text.*'%>
<%@ page import='com.credu.system.*'%>
<%@ page import='com.credu.library.*'%>
<%@ page import='com.credu.etest.*'%>
<jsp:useBean id='conf' class='com.credu.library.ConfigSet' scope='page' />
<%                                                                                  
      RequestBox box = null;                                                     
      box = (RequestBox)request.getAttribute("requestbox");                                                   
      String v_started = FormatDate.getDate("yyyyMMddHHmmss");                                                        
      String v_enddt = "200509122300";  v_enddt = box.getString("p_enddt"); int v_etesttime = 10;                                                       
      try { int v_remaintime = FormatDate.getMinDifference(v_started+"001",v_enddt+"001")+2;                                                        
      if(v_remaintime < v_etesttime){     v_etesttime = v_remaintime; } }catch (Exception ex) { }                                                   
      String v_userid = box.getSession("userid");                                       
 %>
<html>
<head>
<title>::: 오토에버시스템즈 :::</title>
<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>
<link href='/css/user_style_study.css' rel='stylesheet' type='text/css'>
<script language='javascript' src='/script/cresys_lib.js'></script>
<script language='VBScript' src='/script/cresys_lib.vbs'></script>
<SCRIPT LANGUAGE='JavaScript'>                                           
          <!--                                                                     
                                                       
             function init() {                                            
                    this.moveTo(0,0);                                   
                    this.resizeTo(screen.availWidth,screen.availHeight);                                   
                 var v_current = new Date();                                    
                 var v_started = '';                                    
                 var v_temp = '';                                    
                                                     
                 v_started += String(v_current.getFullYear());                                   
                 v_temp = String((v_current.getMonth()+1));                                    
                 if(v_temp.length == 1){                                    
                          v_started += '0' + v_temp;                                   
                 }else{                                    
                          v_started += v_temp;                           
                 }                                    
                 v_temp = String(v_current.getDate());                                    
                 if(v_temp.length == 1){                                    
                          v_started += '0' + v_temp;                                 
                 }else{                                    
                          v_started += v_temp;                           
                 }                                     
                 v_temp = String(v_current.getHours());                                     
                 if(v_temp.length == 1){                                     
                          v_started += '0' + v_temp;                                  
                 }else{                                     
                          v_started += v_temp;                           
                 }                                     
                 v_temp = String(v_current.getMinutes());                                    
                 if(v_temp.length == 1){                                     
                          v_started += '0' + v_temp;                                  
                 }else{                                   
                          v_started += v_temp;                           
                 }                                    
                 v_temp = String(v_current.getSeconds());                                    
                 if(v_temp.length == 1){                                     
                          v_started += '0' + v_temp;                               
                 }else{                                    
                          v_started += v_temp;                           
                 }                                     
        document.form2.p_started.value = v_started;                                            
             }                                            
                                                       
             var limit = <%=v_etesttime%>+':01'                                                   
                                                                                             
             if (document.images){                                            
                var parselimit=limit.split(':')                                     
                parselimit=parselimit[0]*60+parselimit[1]*1                                    
             }                                        
             function begintimer(){                                        
                 if (!document.images)                                    
                     return                                
                 if (parselimit==1)                                    
                     whenAutoSubmit();                                
                 else{                                     
                     parselimit-=1                               
                     curmin=Math.floor(parselimit/60)                                
                     cursec=parselimit%60                                
                     if (curmin!=0)                                
                          curtime=curmin+'분 '+cursec+'초 '                          
                     else                                
                          curtime='0 : '+cursec                          
                     numberCountdown.innerText=curtime                                 
                     setTimeout('begintimer()',1000)                                
                 }                                     
             }                                        
                                                     
             function whenAutoSubmit(){                                        
                 var i=0;                                     
                 var b_name='';                                     
                 var c_name='';                                    
                 var c_value='';                                    
                 var b_type='';                                     
                 var result='';                                    
                 var temp='';                                      
                 var textarr ='';                                     
                 var answercnt=0;                                    
                 var replycnt =0;                                    
                                                     
                 var v_current = new Date();                                    
                 var v_ended = '';                                    
                 var v_temp = '';                                    
                                                     
                 v_ended += String(v_current.getFullYear());                                   
                 v_temp = String((v_current.getMonth()+1));                                    
                 if(v_temp.length == 1){                                    
                          v_ended += '0' + v_temp;                                   
                 }else{                                    
                          v_ended += v_temp;                           
                 }                                    
                 v_temp = String(v_current.getDate());                                    
                 if(v_temp.length == 1){                                    
                          v_ended += '0' + v_temp;                                 
                 }else{                                    
                          v_ended += v_temp;                           
                 }                                     
                 v_temp = String(v_current.getHours());                                     
                 if(v_temp.length == 1){                                     
                          v_ended += '0' + v_temp;                                  
                 }else{                                     
                          v_ended += v_temp;                           
                 }                                     
                 v_temp = String(v_current.getMinutes());                                    
                 if(v_temp.length == 1){                                     
                          v_ended += '0' + v_temp;                                  
                 }else{                                   
                          v_ended += v_temp;                           
                 }                                    
                 v_temp = String(v_current.getSeconds());                                    
                 if(v_temp.length == 1){                                     
                          v_ended += '0' + v_temp;                               
                 }else{                                    
                          v_ended += v_temp;                           
                 }                                     
                                                     
                 for(i=0;i<document.form3.length;i++) {                                     
                      c_name  = document.form3.elements[i].name;                               
                      c_value = document.form3.elements[i].value;                               
                      if (i == 0) {                               
                             b_name=c_name;                        
                      } else {                               
                             if (c_name != b_name) {                         
                                    answercnt++;                 
                                    if (b_type=='text' || temp !='') {                 
                                           replycnt++;          
                                    }                 
                                    if (answercnt==1) {                  
                                           result = temp;         
                                    } else {                 
                                           result = result + ','+ temp;          
                                    }                 
                                    b_name = c_name;                 
                                    temp = '';                 
                             }                        
                      }                                
                      if (document.form3.elements[i].type=='checkbox') {                               
                             b_type='checkbox';                         
                             if (document.form3.elements[i].checked==true) {                        
                                   temp = temp+c_value+':';                  
                             }else{                        
                                   temp = 0;                  
                             }                        
                      } else if (document.form3.elements[i].type=='radio') {                               
                             b_type='radio';                        
                             if (document.form3.elements[i].checked==true) {                        
                                   temp = c_value;                  
                             }else{                        
                                   if(temp.length < 1){ temp = 0; }else{  temp = temp;}                  
                             }                        
                      } else if (document.form3.elements[i].type=='text'||document.form3.elements[i].type=='textarea') {                               
                             b_type='text';                        
                             temp  = '';                        
                             textarr = c_value.split(',');                        
                             for(var j=0; j<textarr.length; j++) {                        
                                   temp = temp + textarr[j];                  
                             }                        
                             temp = temp + ' ';                        
                      }                               
                 }                                    
                 if (b_type=='text' || temp !='') {                                    
                       replycnt++;                              
                 }                                    
                 answercnt++;                                    
                 if (answercnt==1) {                                    
                        result = temp;                                 
                 } else {                                    
                        result = result + ','+ temp + ',';                              
                 }                                     
                                                     
        document.form2.p_answer.value = result;   //alert(result);                                          
        document.form2.p_ended.value = v_ended;                                            
        document.form2.p_process.value = 'ETestUserResultInsert';                                             
        document.form2.submit();                                             
        }                                                   
        function whenSubmit(){                                            
                 var i=0;                                    
                 var b_name='';                                    
                 var c_name='';                                    
                 var c_value='';                                    
                 var b_type='';                                     
                 var result='';                                    
                 var temp='';                                      
                 var textarr ='';                                      
                 var answercnt=0;                                     
                 var replycnt =0;                                     
                                                     
                 var v_current = new Date();                                    
                 var v_ended = '';                                    
                 var v_temp = '';                                     
                 v_ended += String(v_current.getFullYear());                                    
                                                     
                 v_temp = String((v_current.getMonth()+1));                                    
                 if(v_temp.length == 1){                                     
                          v_ended += '0' + v_temp;                                  
                 }else{                                    
                          v_ended += v_temp;                            
                 }                                    
                 v_temp = String(v_current.getDate());                                    
                 if(v_temp.length == 1){                                    
                          v_ended += '0' + v_temp;                                  
                 }else{                                     
                          v_ended += v_temp;                           
                 }                                     
                 v_temp = String(v_current.getHours());                                    
                 if(v_temp.length == 1){                                    
                          v_ended += '0' + v_temp;                                   
                 }else{                                    
                          v_ended += v_temp;                            
                 }                                    
                 v_temp = String(v_current.getMinutes());                                    
                 if(v_temp.length == 1){                                    
                          v_ended += '0' + v_temp;                                   
                 }else{                                    
                          v_ended += v_temp;                            
                 }                                    
                 v_temp = String(v_current.getSeconds());                                    
                 if(v_temp.length == 1){                                     
                          v_ended += '0' + v_temp;                                   
                 }else{                                   
                          v_ended += v_temp;                           
                 }                                     
                                                     
                 for(i=0;i<document.form3.length;i++) {                                    
                          c_name  = document.form3.elements[i].name;                            
                          c_value = document.form3.elements[i].value;                           
                          if (i == 0) {                             
                                  b_name=c_name;                   
                          } else {                           
                                  if (c_name != b_name) {                   
                                          answercnt++;           
                                          if (b_type=='text' || temp !='') {           
                                                replycnt++;     
                                          }           
                                          if (answercnt==1) {           
                                                result = temp;     
                                          } else {           
                                                result = result + ','+ temp;     
                                          }           
                                                b_name = c_name;     
                                                temp = '';     
                                          }           
                                  }                   
                           if (document.form3.elements[i].type=='checkbox') {                           
                                 b_type='checkbox';                    
                                 if (document.form3.elements[i].checked==true) {                     
                                          temp = temp+c_value+':';           
                                 }                    
                           } else if (document.form3.elements[i].type=='radio') {                           
                                  b_type='radio';                   
                                  if (document.form3.elements[i].checked==true) {                    
                                          temp = c_value;           
                                  }                   
                           } else if (document.form3.elements[i].type=='text'||document.form3.elements[i].type=='textarea') {                          
                                  b_type='text';                   
                                  temp  = '';                    
                                  textarr = c_value.split(',');                    
                                  for(var j=0; j<textarr.length; j++) {                    
                                         temp = temp + textarr[j];             
                                  }                   
                                  temp = temp + ' ';                   
                           }                          
                 }                                      
                 if (b_type=='text' || temp !='') {                                    
                          replycnt++;                           
                 }                                    
                 answercnt++;                                    
                 if (answercnt==1) {                                     
                         result = temp;                                
                 } else {                                    
                         result = result + ','+ temp + ',';                            
                 }                                    
                                                     
                 if (replycnt < answercnt) {                                    
                         alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");                           
                         return;                            
                 }                                     
                                                     
        document.form2.p_answer.value = result;                                               
        document.form2.p_ended.value = v_ended;                                             
        document.form2.p_process.value = 'ETestUserResultInsert';                                             
        document.form2.submit();                                            
        }                                              
         -->                                                          
         </SCRIPT>

</head>
<body leftmargin='0' topmargin='0' bottommargin='0' marginwidth='0'
	marginheight='0' onLoad='init();begintimer();'>
<form name='form2' method='post'
	action='/servlet/controller.etest.ETestUserServlet'><input
	type='hidden' name='p_etestsubj' value='ET00004'> <input
	type='hidden' name='p_gyear' value='2005'> <input type='hidden'
	name='p_etestcode' value='0901'> <input type='hidden'
	name='p_etestnum' value='1'> <input type='hidden'
	name='p_etest' value='2,1'> <input type='hidden'
	name='p_started' value=''> <input type='hidden'
	name='p_etestcnt' value='2'> <input type='hidden'
	name='p_etestpoint' value='50'> <input type='hidden'
	name='p_ended' value=''> <input type='hidden' name='p_answer'
	value=''> <input type='hidden' name='p_process' value=''>
</form>
<form name='form3' method='post'
	action='/servlet/controller.etest.ETestUserServlet'>
<table cellpadding='0' cellspacing='10' bgcolor='#EEEEEE' width='670'
	height='100%'>
	<tr>
		<td>
		<table cellpadding='0' cellspacing='1' bgcolor='#BEBEBE'
			class='table2' height='100%'>
			<tr>
				<td align='center' valign='top' class='body_color'><!----------------- 외곽   ---------------->
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>
					<!----------------- 타이틀   ---------------->
					<tr>
						<td>&nbsp; &nbsp; &nbsp; <img
							src='/images/user/etest/eTest_pop01.gif'></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<!----------------- 타이틀 끝  ---------------->
					<tr>
						<td align='center'>
						<table border='0' cellpadding='0' cellspacing='0' width='630'
							align='center'>
							<tr>
								<td valign='top' align='center'>
								<table width='630' border='0' cellpadding='0' cellspacing='0'
									background='/images/user/etest/test_box_bg.gif'>
									<tr>
										<td><img src='/images/user/etest/test_box_top.gif'></td>
									</tr>
									<tr>
										<td align='center'>
										<table width='600' border='0' cellspacing='0' cellpadding='0'>
											<tr>
												<td width='87' rowspan='5'><img
													src='/images/user/etest/test_img.jpg'></td>
												<td width='73' height='33'><img
													src='/images/user/etest/text_name.gif'></td>
												<td width='226' class='tblfont_sindotit'>동자테스트</td>
												<td width='61'><img
													src='/images/user/etest/text_year.gif'></td>
												<td width='153' class='tblfont_gleft2'>2005</td>
											</tr>
											<tr>
												<td height='5' colspan='4'
													background='/images/user/etest/stu_box_vline2.gif'></td>
											</tr>
											<tr>
												<td colspan='5' class='tblfont_gleft2'>시험시간 - <b>10분
												00초</b></td>
											</tr>
											<tr>
												<td colspan='5' class='tblfont_gleft2'>남은시간 - <b><font
													color='red'><span id='numberCountdown'></span></font></b></td>
											</tr>
											<tr>
												<td colspan='5' class='tblfont_gleft2'>문제수 - <b>2</b></td>
											</tr>
										</table>
										</td>
									</tr>
									<tr>
										<td><img src='/images/user/etest/test_box_bo.gif'></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td height=8>
								<table width='100%' border='0' cellspacing='5' cellpadding='10'>
									<tr>                                                                                                                           
                                 <td class='tblfont_gleft2'><b>시험시간이 경과하면 자동으로 시험지가 제출되오니 유의하시기 바랍니다.                       
                                 <br> &nbsp; &nbsp;  주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시오. </b></td>                                    
                               </tr></table>
								</td>
							</tr>
							<tr>
								<td><!----------------- 학습평가 시작 ---------------->
								<table width='630' border='0' cellspacing='1' cellpadding='0'
									class=boardskin1_out>
									<tr>
										<td>
										<table cellspacing='1' cellpadding='3' class='table2'>
											<tr>
												<td class='board_title_bg0'>[1] 동자테스트1</td>
											</tr>
											<tr>
												<td bgcolor='#FFFFFF'>
												<table cellspacing='0' cellpadding='0' class='img_table_out'>
													<tr>
														<td>
														<table cellspacing='2' cellpadding='2'>
															<tr>
																<td class='img_table' height='26'><img
																	src='/dp/etest/ETestQuestion_img1_200509121806221_lee1.jpg'></td>
															</tr>
														</table>
														</td>
													</tr>
												</table>
												</td>
											</tr>
											<tr>
												<td class='board_text4'><img
													src='/images/user/study/voice_icon1.gif' width='20'
													height='17' border='0'> <a
													href='/dp/etest/ETestQuestion_audio1_200509121806262_lee1.mp3'
													target='_new'>김창렬 - 약속.mp3</a></td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='2' value='1' class='inputradio'> 1.1</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='2' value='2' class='inputradio'> 2.2</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='2' value='3' class='inputradio'> 3.3</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='2' value='4' class='inputradio'> 4.4</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='2' value='5' class='inputradio'> 5.5</td>
											</tr>
											<tr>
												<td class='board_title_bg0'>[2] 동자테스트1</td>
											</tr>
											<tr>
												<td bgcolor='#FFFFFF'>
												<table cellspacing='0' cellpadding='0' class='img_table_out'>
													<tr>
														<td>
														<table cellspacing='2' cellpadding='2'>
															<tr>
																<td class='img_table' height='26'><img
																	src='/dp/etest/ETestQuestion_img1_200509121806061_lee1.jpg'></td>
															</tr>
														</table>
														</td>
													</tr>
												</table>
												</td>
											</tr>
											<tr>
												<td class='board_text4'><img
													src='/images/user/study/voice_icon1.gif' width='20'
													height='17' border='0'> <a
													href='/dp/etest/ETestQuestion_audio1_200509121806102_lee1.mp3'
													target='_new'>김창렬 - 약속.mp3</a></td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='1' value='1' class='inputradio'> 1.1</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='1' value='2' class='inputradio'> 2.2</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='1' value='3' class='inputradio'> 3.3</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='1' value='4' class='inputradio'> 4.4</td>
											</tr>
											<tr>
												<td class='board_text4'>&nbsp;&nbsp;<input type='radio'
													name='1' value='5' class='inputradio'> 5.5</td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</form>
								<!----------------- 학습평가 끝 ----------------> <br>
								<!----------------- 제출 버튼 시작 ---------------->
								<table cellspacing='0' cellpadding='0'
									class='open_board_table_out1' align='center'>
									<tr>
										<td align='right'><a href='javascript:whenSubmit()'><img
											src='/images/user/study/presentation_butt1.gif' width='54'
											height='20' border='0'></a></td>
										<td align='right' width='65'><a
											href='javascript:self.close()'><img
											src='/images/user/study/close_butt.gif' width='54'
											height='20' border='0'></a></td>
									</tr>
								</table>
								<!----------------- 제출 버튼 끝 -----------------> <br>
								</td>
							</tr>
						</table>

						</td>
					</tr>
				</table>
				<!----------------- 외곽   ----------------></td>
			</tr>
		</table>

		</td>
	</tr>
</table>
<br>
</body>
</html>

