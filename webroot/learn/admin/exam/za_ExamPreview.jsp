<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 학습평가
//  2. 프로그램명 : za_ExamPreview.jsp
//  3. 개      요: 학습평가창
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.01.29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String p_lessonstart = box.getString("p_lessonstart"); 

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    DataBox    dbox0 = (DataBox)request.getAttribute("ExamPaperData");

		String v_subj     	= dbox0.getString("d_subj"); 
		String v_subjseq    = dbox0.getString("d_subjseq"); 
    String v_gyear     	= dbox0.getString("d_year"); 
    String v_papernum 	= dbox0.getString("d_papernum"); 
    String v_subjnm 		= dbox0.getString("d_subjnm"); 
    String v_lesson 		= dbox0.getString("d_lesson"); 
    String v_examtype 	= dbox0.getString("d_examtype"); 
    String v_examtypenm = dbox0.getString("d_examtypenm"); 
    String v_examcnt 		= dbox0.getString("d_examcnt"); 
    String v_exampoint 	= dbox0.getString("d_exampoint"); 
    int    v_examtime  	=  dbox0.getInt("d_examtime"); 
        

    int    v_userretry  =  box.getInt("p_userretry"); 

	String v_started = FormatDate.getDate("yyyyMMddHHmmss");

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

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>학습진단하기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css"/>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
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
return
if (parselimit==1){
//whenAutoSubmit();
}else{ 
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
    c_name  = document.form3.elements[i].name;
    c_value = document.form3.elements[i].value;
    if (i == 0) {
      b_name=c_name;
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
      }
    }
    if (document.form3.elements[i].type=="checkbox") {
      b_type="checkbox";
      if (document.form3.elements[i].checked==true) {
        temp = temp+c_value+':';
      }else{
	    temp = 0;
	  }
    } else if (document.form3.elements[i].type=="radio") {
      b_type="radio";
      if (document.form3.elements[i].checked==true) {
        temp = c_value;
      }else{
	    temp = 0;
	  }
    } else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
      b_type="text";
      temp  = "";
      textarr = c_value.split(",");
      for(var j=0; j<textarr.length; j++) {
        temp = temp + textarr[j];
      }
	  temp = temp + " ";
    }
  }
  if (b_type=="text" || temp !="") {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + " ,"+ temp + " ,";
  }
  
//  if (replycnt < answercnt) {
//    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
//    return;
//  }
  document.form2.p_answer.value = result;
  document.form2.p_ended.value = v_ended;
  document.form2.p_process.value = 'ExamUserResultInsert';
  document.form2.submit();
}

function whenSubmit(){
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
    c_name  = document.form3.elements[i].name;
    c_value = document.form3.elements[i].value;
    if (i == 0) {
      b_name=c_name;
    } else {
      if (c_name != b_name) {
        answercnt++;
        if (b_type=="text" || temp !="") {
          replycnt++;
        }
        if (answercnt==1) {
          result = temp;
        } else {
          result = result + ","+ temp;
        }
        b_name = c_name;
        temp = "";
      }
    }
    if (document.form3.elements[i].type=="checkbox") {
      b_type="checkbox";
      if (document.form3.elements[i].checked==true) {
        temp = temp+c_value+':';
      }
    } else if (document.form3.elements[i].type=="radio") {
      b_type="radio";
      if (document.form3.elements[i].checked==true) {
        temp = c_value;
      }
    } else if (document.form3.elements[i].type=="text"||document.form3.elements[i].type=="textarea") {
      b_type="text";
      temp  = "";
      textarr = c_value.split(",");
      for(var j=0; j<textarr.length; j++) {
        temp = temp + textarr[j];
      }
	  temp = temp + " ";
    }
  }
  if (b_type=="text" || temp !="") {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else {
    result = result + ","+ temp + ",";
  }
  
  if (replycnt < answercnt) {
    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
    return;
  }
  document.form2.p_answer.value = result;
  document.form2.p_ended.value = v_ended;
  document.form2.p_process.value = 'ExamUserResultInsert';
  document.form2.submit();
}
-->
</script>
</head>
<body id="popup">
<form name="form2" method="post" action="/servlet/controller.exam.ExamUserServlet">
    <input type="hidden" name="p_subj"        value="<%=v_subj%>"/>
    <input type="hidden" name="p_subjseq"     value="<%=v_subjseq%>"/>
    <input type="hidden" name="p_gyear"       value="<%=v_gyear%>"/>
    <input type="hidden" name="p_papernum"    value="<%=v_papernum%>"/>
    <input type="hidden" name="p_userid"      value="<%=v_userid%>"/>
    <input type="hidden" name="p_lesson"      value="<%=v_lesson%>"/>
    <input type="hidden" name="p_examtype"    value="<%=v_examtype%>"/>
    <input type="hidden" name="p_exam"        value="<%=v_exam%>"/>
    <input type="hidden" name="p_started"     value="<%=v_started%>"/>
    <input type="hidden" name="p_examcnt"     value="<%=v_examcnt%>"/>
    <input type="hidden" name="p_exampoint"   value="<%=v_exampoint%>"/>
    <input type="hidden" name="p_userretry"   value="<%=v_userretry%>"/>
    <input type="hidden" name="p_ended"       value=""/>
    <input type="hidden" name="p_answer"      value=""/>
    <input type="hidden" name="p_process"     value=""/>
    <input type="hidden" name="p_lessonstart" value="<%=p_lessonstart%>"/>
    
</form>
<form name="form3" method="post" action="/servlet/controller.exam.ExamUserServlet">

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit9.gif" alt="학습평가" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			
			<h3 class="pop_tit_txt">[과정명] : <b><%=v_subjnm%></b>&nbsp; / <b>[평가타입]</b> : <b><%=v_examtypenm%></b></h3>
			<h3 class="pop_tit_txt"><font color="red">주관식 문제의 경우 답을 적으실 때 띄어쓰기를 하지 마십시요.</font> &nbsp;[문제수:<%=v_examcnt%>]</b></h3>
<% 

    ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionExampleList");
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);
        if(list.size()!=0)
        {
        DataBox dbox = (DataBox)list.get(0);  
%>
			<table class="write">
			<tbody>
			<tr>
				<th class="end ta_l"><strong><%=String.valueOf(i+1)+". " + dbox.getString("d_examtext")%></strong></th>
			</tr>

<%      
        if (!dbox.getString("d_saveimage").equals("")) { %> 
			<tr>
	            <td class="img_table" height="26"><img src='<%=v_urldir+dbox.getString("d_saveimage")%>'/></td>
	        </tr>
<%      }  %>  

<%      if (!dbox.getString("d_saveaudio").equals("")) { %>
			<tr> 
                <td class="board_text4"><img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"/><a href="<%=v_urldir+dbox.getString("d_saveaudio")%>"><%=dbox.getString("d_realaudio")%></a></td>
            </tr>
<%      }  %>  

<%      if (!dbox.getString("d_savemovie").equals("")) { %>
			<tr> 
                <td class="board_text3"><img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"/><a href="<%=v_urldir+dbox.getString("d_savemovie")%>"><%=dbox.getString("d_realmovie")%></a></td>
            </tr>
<%      }  %>        

			<tr>
				<td class="examine">
<%      if (dbox.getString("d_examtype").equals(ExamBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
					<dl class="<%= (j==0) ? "examine_list first" : "examine_list" %>">
						<dt><input name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_examnum")%>" type="radio" value="<%=dbox.getInt("d_selnum")%>" /></dt>
						<dd><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></dd>				
					</dl>
<%              } 
            }

        } else if (dbox.getString("d_examtype").equals(ExamBean.OX_QUESTION)) {   // ox선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
					<dl class="<%= (j==0) ? "examine_list first" : "examine_list" %>">
						<dt><input name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_examnum")%>" type="radio" value="<%=dbox.getInt("d_selnum")%>" /></dt>
						<dd><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></dd>				
					</dl>
<%              } 
            }            
        } else if (dbox.getString("d_examtype").equals(ExamBean.SUBJECT_QUESTION)) {  // 주관식  
%>
					<dl class="examine_list first">
						<dt></dt>
						<dd><textarea name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_examnum")%>" cols="90" rows="5" class="input"></textarea></dd>				
					</dl>
<%             
            
        } else if (dbox.getString("d_examtype").equals(ExamBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { 
%>
					<dl class="<%= (j==0) ? "examine_list first" : "examine_list" %>">
						<dt><input name="<%=dbox.getString("d_subj")%>_<%=dbox.getInt("d_examnum")%>" type="checkbox" value="<%=dbox.getInt("d_selnum")%>" /></dt>
						<dd><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></dd>				
					</dl>
<%            	}
			}
		}
%>
				</td>
			</tr>
			<tr>
				<td height="20">&nbsp;</td>
			</tr>
			</tbody>
			</table>
<%
        }
	}
%>
		</div>
	</div>
	</form>
	<div id="pop_footer">
		<p class="f_btn"><a class="btn_gr"><span>제출</span></a><a href="javascript:self.close()" class="btn_gr"><span>취소</span></a></p>
	</div>
</body>
</html>