<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 과정 평가 응시
//  2. 프로그램명: zu_ExamUserPaper_I.jsp
//  3. 개      요: 과정 평가 응시
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.1.14
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    DataBox    dbox0 = (DataBox)request.getAttribute("ExamPaperData");

	String v_subj     		= dbox0.getString("d_subj"); 
	String v_subjseq    	= dbox0.getString("d_subjseq"); 
    String v_gyear     		= dbox0.getString("d_year"); 
    String v_papernum 		= dbox0.getString("d_papernum"); 
    String v_subjnm 		= dbox0.getString("d_subjnm"); 
    String v_lesson 		= dbox0.getString("d_lesson"); 
    String v_examtype 		= dbox0.getString("d_examtype"); 
    String v_examtypenm 	= dbox0.getString("d_examtypenm"); 
    String v_examcnt 		= dbox0.getString("d_examcnt"); 
    String v_exampoint 		= dbox0.getString("d_exampoint"); 
    int    v_examtime  		= dbox0.getInt("d_examtime"); 
	String v_isopenanswer	= dbox0.getString("d_isopenanswer"); 
	String v_isopenexp 		= dbox0.getString("d_isopenexp"); 
    int    v_userretry  	=  box.getInt("p_userretry");
    
	String v_started		= FormatDate.getDate("yyyyMMddHHmmss");

    Vector    v0  = (Vector)request.getAttribute("ExamQuestionData");
 
	String v_exam = "";

    for (int i=0; i < v0.size(); i++) {
        v_exam += (String)v0.get(i);
        if (i<v0.size()-1) {
            v_exam += ",";
        }
    }

    String v_userid = box.getSession("userid");

    String v_urldir = conf.getProperty("url.upload") + "exam/";
	//String v_urldir = conf.getProperty("dir.upload.exam");

	SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);
	
	EduStartBean	bean		= new EduStartBean();
	EduScoreData    scoredata	= new EduScoreData();
					scoredata	= bean.SelectEduScore(box);
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>평가</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
function init() {
    this.moveTo(0,0);
    this.resizeTo(screen.availWidth,screen.availHeight);
}

function whenSelect(xx){
    opener.parent.etop.jumpTo(xx);
    window.close();
}
function whenBr(lesson,branch){
    opener.parent.etop.whenBranchFromProcessWin(lesson,branch);
    window.close();
}
function whenExamSelect(ptype,lesson){
    //var url = '/servlet/controller.contents.EduStart?p_process=exam&p_lesson='+lesson+'&p_type='+ptype;
    //opener.parent.ebody.location = url;
    opener.parent.etop.goExam(lesson,ptype);
    window.close();
}
function goReport(urls){
    window.open(urls,"Reports","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=500").focus();
}

function onload() {
  window.self.close();
}

// 시간을 설정 하세요
var limit= <%=v_examtime%>+":01" // 1분 30초

if (document.images){
	var parselimit=limit.split(":")
	parselimit=parselimit[0]*60+parselimit[1]*1
}

function begintimer(){
	if (!document.images)
		return;
	if (parselimit==1)
		whenAutoSubmit();
	else{ 
		parselimit-=1
		curmin=Math.floor(parselimit/60)
		cursec=parselimit%60
		if (curmin!=0)
			curtime=curmin+" 분 "+cursec+" 초"
		else
			curtime="0 : "+cursec+" 초"
		numberCountdown.innerText=curtime
		setTimeout("begintimer()",1000)
	}
}

function whenAutoSubmit(){
	var i=0;
	var exam ="";
	var examnum = "";
	var a_name ="";
	var b_name="";
	var c_name="";
	var c_value="";
	var b_type=""; 
	var result="";
	var temp=""; 
	var textarr ="";
	var answercnt=0;
	var replycnt =0;

	var v_current = new Date();
	var v_ended = "";
	var v_temp = "";

	v_ended += String(v_current.getFullYear());

	v_temp = String((v_current.getMonth()+1));
	if(v_temp.length == 1){
    	v_ended += "0" + v_temp;       
	}else{
 		v_ended += v_temp;
	}
	v_temp = String(v_current.getDate());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
 	v_ended += v_temp;
	}
	v_temp = String(v_current.getHours());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 v_ended += v_temp;
	}
	v_temp = String(v_current.getMinutes());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 v_ended += v_temp;
	}
	v_temp = String(v_current.getSeconds());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 v_ended += v_temp;
	}
/*
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
			              result = result + ' ,'+ temp;          
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
			      temp = 0;                   
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
	       result = result + ' ,'+ temp + ' ,';                             
	}                                     
                                                   
	document.form2.p_answer.value = result;                                             
	document.form2.p_ended.value = v_ended;                                            
	document.form2.p_process.value = 'ExamUserResultInsert';       
	document.form2.p_exam.value = examnum;
	document.form2.submit();                                             
}      */   
  
	for(i=0;i<document.form3.length;i++) {
		//if(document.form3.elements[i].name != "p_exam") {
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;//alert(c_value);
		if (i == 0) {
			b_name=c_name;
			c_name = document.form3.elements[i].name + ",";
			examnum += c_name;
		} else {
			if (c_name != b_name) {
				answercnt++;
				if (b_type=="text" || temp !="") {
					replycnt++;
				}
				if (answercnt==1) {
					result = temp;
				} else {
					result = result + " ,"+ temp;
				}
				b_name = c_name;
				temp = "";
				c_name = document.form3.elements[i].name + ",";
		    	examnum += c_name;
			}
		}
	//   if (document.form3.elements[i].type=="checkbox") {
	//	  b_type="checkbox";
	//	  if (document.form3.elements[i].checked==true) {
	//		temp = temp+c_value+':';
	//	  }else{
	//		temp = 0;
	//	  }
	//	} else 
		if (document.form3.elements[i].type=="radio") {
			b_type="radio";
			if (document.form3.elements[i].checked==true) {
				temp = c_value;
	  		}
	  		else if(temp == "") {
				temp = 0;
	  		}//alert("temp" + temp);
		} else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
			b_type="text";
			temp  = "";
			textarr = c_value.split(",");
			for(var j=0; j<textarr.length; j++) {
				temp = temp + textarr[j];
			}
			if(temp == '') {
				temp ="-";
			}
			else {
				temp = temp + " ";
			}
		}
//	}
//	if(document.form3.elements[i].name == "p_exam") {
//	  a_name = document.form3.elements[i].value + ",";
//	  exam +=  a_name;
//    }
//	result += temp;
	}
//  if (b_type=="text" || temp !="") {
//    replycnt++;
//  }
	answercnt++;
	if (answercnt==1) {
	  result = temp;    
	} else {
	  result = result + " ,"+ temp + " ,";
	}
  
//  alert("examnum" + examnum);
//  alert("result" + result);
  
//  if (replycnt < answercnt) {
//    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
//    return;
//  }
	document.form2.p_answer.value = result;
	document.form2.p_ended.value = v_ended;
	document.form2.p_process.value = 'ExamUserResultInsert';
	document.form2.p_exam.value = examnum;
	document.form2.submit();
}

function whenSubmit(){
	var exam ="";
	var a_name ="";
	var examnum = "";
	var i=0;
	var b_name="";
	var c_name="";
	var c_value="";
	var b_type=""; 
	var result="";
	var temp=""; 
	var textarr ="";
	var answercnt=0;
	var replycnt =0;
	
	var v_current = new Date();
	var v_ended = "";
	var v_temp = "";
	
	v_ended += String(v_current.getFullYear());
	
	v_temp = String((v_current.getMonth()+1));
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
		v_ended += v_temp;
	}
	v_temp = String(v_current.getDate());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 	v_ended += v_temp;
	}
	v_temp = String(v_current.getHours());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 	v_ended += v_temp;
	}
	v_temp = String(v_current.getMinutes());
	if(v_temp.length == 1){
	    v_ended += "0" + v_temp;       
	}else{
	 	v_ended += v_temp;
	}
	v_temp = String(v_current.getSeconds());
	if(v_temp.length == 1){
		v_ended += "0" + v_temp;       
	}else{
	 	v_ended += v_temp;
	}
  
	for(i=0;i<document.form3.length;i++) {
	//  alert(document.form3.p_exam.length);
//	if(document.form3.elements[i].name != "p_exam") {
		c_name  = document.form3.elements[i].name;
		c_value = document.form3.elements[i].value;
		if (i == 0) {
			b_name=c_name;
			c_name = document.form3.elements[i].name + ",";
			examnum += c_name;
		} else {
			if (c_name != b_name) {
				answercnt++;
	//		alert("temp" + temp.length);
			if (b_type=="text" && temp.length > 1) {
				replycnt++;//alert("replycnt1" + replycnt);
			}
			else if(b_type!="text" && temp.length > 0) {
				replycnt++;//alert("replycnt1" + replycnt);
			}
			if (answercnt==1) {
				result = temp;
			} else {
				result = result + ","+ temp;
			}
			b_name = c_name;
			temp = "";
			c_name = document.form3.elements[i].name + ",";
	    	examnum += c_name;
		}

	}
	//	alert(result);
	/*    if (document.form3.elements[i].type=="checkbox") {
		  b_type="checkbox";
		  if (document.form3.elements[i].checked==true) {
			temp = temp+c_value+':';
		  }
		  c_name = document.form3.elements[i].name + ",";
		  examnum += c_name;alert(i + " : "  + examnum);
		} else */
		
	if (document.form3.elements[i].type=="radio") {
		b_type="radio";
		if (document.form3.elements[i].checked==true) {
			temp = c_value;
	//		c_name = document.form3.elements[i].name + ",";
	//		examnum += c_name;
		}
	} else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
		b_type="text";
		temp  = "";
		textarr = c_value.split(",");
		for(var j=0; j<textarr.length; j++) {
			temp = temp + textarr[j];
		}
 			temp = temp + " ";
	//	  c_name = document.form3.elements[i].name + ",";
	//	  examnum += c_name;
		}
//	}
//	if(document.form3.elements[i].name == "p_exam") {
//	  a_name = document.form3.elements[i].value + ",";
//	  exam +=  a_name;
//    }
	} 
//  alert("temp" + temp.length);
	if (b_type=="text" && temp.length > 1) {
		replycnt++;//alert("replycnt1" + replycnt);
	}
	else if(b_type!="text" && temp.length > 0) {
		replycnt++;//alert("replycnt1" + replycnt);
	}
	answercnt++;
	if (answercnt==1) {
		result = temp;    
	} else {
		result = result + ","+ temp + ",";
	}  
  
 // alert("result>>"+result);
 // alert("replycnt" + replycnt);
//  alert("answercnt" + answercnt);
	if (replycnt < answercnt) {
		alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
		return;
	}
	document.form2.p_answer.value = result;
	document.form2.p_ended.value = v_ended;
	document.form2.p_process.value = 'ExamUserResultInsert';
	document.form2.p_exam.value = examnum;
	document.form2.submit();
}
-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form2" method="post" action="/servlet/controller.exam.ExamUserServlet">
    <input type="hidden" name="p_subj"         value="<%=v_subj%>"/>
    <input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>"/>
    <input type="hidden" name="p_gyear"        value="<%=v_gyear%>"/>
    <input type="hidden" name="p_papernum"     value="<%=v_papernum%>"/>
    <input type="hidden" name="p_userid"       value="<%=v_userid%>"/>
    <input type="hidden" name="p_lesson"       value="<%=v_lesson%>"/>
    <input type="hidden" name="p_examtype"     value="<%=v_examtype%>"/>
    <input type="hidden" name="p_exam"         value=""/>
    <input type="hidden" name="p_started"      value="<%=v_started%>"/>
    <input type="hidden" name="p_examcnt"      value="<%=v_examcnt%>"/>
    <input type="hidden" name="p_exampoint"    value="<%=v_exampoint%>"/>
    <input type="hidden" name="p_userretry"    value="<%=v_userretry%>"/>
    <input type="hidden" name="p_ended"        value=""/>
    <input type="hidden" name="p_answer"       value=""/>
    <input type="hidden" name="p_process"      value=""/>
	<input type="hidden" name="p_isopenanswer" value="<%=v_isopenanswer%>"/>
</form>
<form name="form3" method="post" action="/servlet/controller.exam.ExamUserServlet">

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit7.gif" alt="평가" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
<%	if (scoredata != null) { %>
				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= subjdata.getSubjnm() %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				
				<div class="content">
					<div class="info_box">
						<div>
							<dl>
								<dt>시험유형</dt>
								<dd>: <%= v_examtypenm %></dd>
							</dl>
							<dl>
								<dt>문제수</dt>
								<dd>: <%= v_examcnt %></dd>
							</dl>
							<dl>
								<dt>유의사항</dt>
								<dd>: 주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시요.</dd>
							</dl>
						</div>

						<p class="f_btn"><a href="javascript:whenSubmit()" class="btn_gr"><span>제출</span></a><a href="javascript:history.go(-1)" class="btn_gr"><span>목록</span></a></p>
					</div>
					
					<table class="write">
						<tbody>
<% 
    ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);  
           
%>
						<tr>
							<th class="end ta_l"><strong><%= String.valueOf(i+1)+"." + dbox.getString("d_examtext") %></strong></th>
						</tr>
<%
		if (!dbox.getString("d_saveimage").equals("")) { %> 
						<tr>
							<th class="end ta_l"><strong><img src='<%= v_urldir+dbox.getString("d_saveimage") %>'/></strong></th>
						</tr>
<%      }  %>  

<%      if (!dbox.getString("d_saveaudio").equals("")) { %> 
						<tr>
							<th class="end ta_l"><strong><img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"/><a href="<%= v_urldir+dbox.getString("d_saveaudio") %>"><%= dbox.getString("d_realaudio") %></a></strong></th>
						</tr>
<%      }  %>  

<%      if (!dbox.getString("d_savemovie").equals("")) { %> 
						<tr>
							<th class="end ta_l"><strong><img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"/><a href="<%= v_urldir+dbox.getString("d_savemovie") %>"><%= dbox.getString("d_realmovie") %></a></strong></th>
						</tr>
<%      }  %>        
						<tr>
							<td class="examine">
							
							
<%      if (dbox.getString("d_examtype").equals(ExamBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
								<dl class="examine_list first ov_fl">
									<dt><input name="<%= dbox.getInt("d_examnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" style="margin-top:-1px;" /></dt>
									<dd style="line-height:20px;"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>				
								</dl>
<%              } 
            }

        } else if (dbox.getString("d_examtype").equals(ExamBean.OX_QUESTION)) {   // ox선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
								<dl class="examine_list first ov_fl">
									<dt><input name="<%= dbox.getInt("d_examnum") %>" type="radio" value="<%= dbox.getInt("d_selnum") %>" style="margin-top:-1px;" /></dt>
									<dd style="line-height:20px;"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>				
								</dl>
<%              } 
            }            
        } else if (dbox.getString("d_examtype").equals(ExamBean.SUBJECT_QUESTION)) {  // 주관식  %>
								<dl class="examine_list first ov_fl">
									<dt></dt>
									<dd style="line-height:20px;"><textarea name="<%= dbox.getInt("d_examnum") %>" cols="90" rows="5"></textarea></dd>				
								</dl>
<%             
            
        } else if (dbox.getString("d_examtype").equals(ExamBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
								<dl class="examine_list first ov_fl">
									<dt><input type="checkbox" name="<%= dbox.getInt("d_examnum") %>" value="<%= dbox.getInt("d_selnum") %>" style="margin-top:-1px;"/></dt>
									<dd style="line-height:20px;"><%= dbox.getInt("d_selnum") %>. <%= dbox.getString("d_seltext") %></dd>				
								</dl>
<%            }
		   }
	    }
%>
							</td>
						</tr>
<%
	}
%>
						</tbody>
					</table>
					
					<p class="list_btn"><a href="javascript:whenSubmit()" class="btn_gr"><span>제출</span></a><a href="javascript:history.go(-1)" class="btn_gr"><span>목록</span></a></p>

				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>