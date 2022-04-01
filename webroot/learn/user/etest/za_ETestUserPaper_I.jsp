<%
/**
 * file name : zu_ETestUserPaper_I.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 평가 응시
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    DataBox    dbox0 = (DataBox)request.getAttribute("ETestPaperData");

	String v_subj     = dbox0.getString("d_subj"); 
	String v_subjseq     = dbox0.getString("d_subjseq"); 
    String v_gyear     = dbox0.getString("d_year"); 
    String v_papernum = dbox0.getString("d_papernum"); 
    String v_subjnm = dbox0.getString("d_subjnm"); 
    String v_lesson = dbox0.getString("d_lesson"); 
    String v_etesttype = dbox0.getString("d_etesttype"); 
    String v_etesttypenm = dbox0.getString("d_etesttypenm"); 
    String v_etestcnt = dbox0.getString("d_etestcnt"); 
    String v_etestpoint = dbox0.getString("d_etestpoint"); 
    int    v_etesttime  =  dbox0.getInt("d_etesttime"); 
    int    v_retry  =  dbox0.getInt("d_retry"); 

    int    v_realretry  =  box.getInt("p_realretry"); 

	String v_started = FormatDate.getDate("yyyyMMddHHmmss");

    Vector    v0  = (Vector)request.getAttribute("ETestQuestionData");
 
	String v_etest = "";

        for (int i=0; i < v0.size(); i++) {
            v_etest += (String)v0.get(i);
            if (i<v0.size()-1) {
                v_etest += ",";
            }
        }


    String v_userid = box.getSession("userid");

    String v_urldir = "/upload/etest/";
%>
<html>
<head>
<title>평가 응시</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
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
    function whenETestSelect(ptype,lesson){
        //var url = '/servlet/controller.contents.EduStart?p_process=etest&p_lesson='+lesson+'&p_type='+ptype;
        //opener.parent.ebody.location = url;
        opener.parent.etop.goETest(lesson,ptype);
        window.close();
    }
    function goReport(urls){
        window.open(urls,"Reports","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=500").focus();
    }

function onload() {
  window.self.close();
}


// 시간을 설정 하세요
var limit= <%=v_etesttime%>+":01" // 1분 30초
if (document.images){
var parselimit=limit.split(":")
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
curtime="평가 종료까지 "+curmin+" 분 "+cursec+" 초 가 남았습니다"
else
curtime="평가 종료까지 "+cursec+" 초가 남았습니다"
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
  v_ended += String((v_current.getMonth()+1));
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
      if (eval(b_name.indexOf("|")) > 0) {
        answercnt++;
        if (b_type=="text" || temp !="") {
          replycnt++;
        }
        if (answercnt==1) {
          result = temp;
        } else {
          result = result + temp  ;
        }
        b_name = c_name;
        temp = "";	  
	  } else if (c_name != b_name ) {
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
		 if (eval(c_name.indexOf("|"))>0){
            for(var j=0; j<textarr.length; j++) {
               temp =  temp + textarr[j];
            }
			temp = ":" + temp + ":";
		 } else {
             for(var j=0; j<textarr.length; j++) {
                 temp = temp + textarr[j];
             }
         }
	}
  }
  if (b_type=="text" || temp !="") {
    replycnt++;
  }
  answercnt++;
  if (answercnt==1) {
    result = temp;    
  } else if (eval(b_name.indexOf("|"))>0) {
    result = result + temp  ;  
  }else {
    result = result + ","+ temp;
  }

//alert(result);

//  if (replycnt < answercnt) {
//    alert("응답하지 않은 문제가 있습니다.모든 문제에 응답해주시길 바랍니다.");
//    return;
//  }
  document.form2.p_ended.value = v_ended;
  document.form2.p_answer.value = result;
  document.form2.p_process.value = "ETestUserResultInsert";
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
  document.form2.p_process.value = 'ETestUserResultInsert';
  document.form2.submit();
}
-->
</SCRIPT>
</head>
<body onLoad='init();begintimer();'>
<form name="form2" method="post" action="/servlet/controller.etest.ETestUserServlet">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_papernum"      value="<%=v_papernum%>">
    <input type="hidden" name="p_userid"      value="<%=v_userid%>">
    <input type="hidden" name="p_lesson"      value="<%=v_lesson%>">
    <input type="hidden" name="p_etesttype"      value="<%=v_etesttype%>">
    <input type="hidden" name="p_etest"      value="<%=v_etest%>">
    <input type="hidden" name="p_started"      value="<%=v_started%>">
    <input type="hidden" name="p_etestcnt"      value="<%=v_etestcnt%>">
    <input type="hidden" name="p_etestpoint"      value="<%=v_etestpoint%>">
    <input type="hidden" name="p_retry"      value="<%=v_retry%>">
    <input type="hidden" name="p_realretry"      value="<%=v_realretry%>">
    <input type="hidden" name="p_ended"      value="">
    <input type="hidden" name="p_answer"      value="">
    <input type="hidden" name="p_process"      value="">
</form>
<form name="form3" method="post" action="/servlet/controller.etest.ETestUserServlet">
<!----------------- 타이틀 시작 ----------------->
<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
  <tr>                 
		<td background="/images/user/research/b_title_dot.gif"><img src="/images/user/study/exam_title1.gif" alt="학습평가" width="240" height="37" border="0"></td>
		<td background="/images/user/research/b_title_dot.gif">&nbsp;</td>
		<td background="/images/user/research/b_title_dot.gif" width= 100 align='right'></td>
  </tr>
  <tr><td height="7" align=""></td></tr>
  <tr><td></td><td height="7" align="right"><b><div align="center" id="numberCountdown"></div></b></td></tr>
</table>
<!----------------- 타이틀 끝 ---------------->
<br>
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
	<tr> 
		<td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
		<td><b>과정명 : [<%=v_subjnm%>] 연도 : [<%=v_gyear%>] 차수 : [<%=v_subjseq%>] 일차 : [<%=v_lesson%>] 평가타입 : [<%=v_etesttypenm%>]</b></td>
		<td align="right"><a href="javascript:history.go(-1)"><img src="/images/admin/research/list1_butt.gif" alt="리스트" width="48" height="18" border="0"></a></td>
	</tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<!----------------- 학습평가 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr> 
    <td class="board_color_line"></td>
  </tr>
</table>
<table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
  <tr> 
    <td> 
      <table cellspacing="1" cellpadding="3" class="table2">
<% 
        ArrayList    blist = (ArrayList)request.getAttribute("PaperQuestionETestpleList");
    
	for (int i=0; i < blist.size(); i++) {
        ArrayList list = (ArrayList)blist.get(i);  
        DataBox dbox = (DataBox)list.get(0);  
%>
        <tr> 
          <td class="board_title_bg0"><%="["+String.valueOf(i+1)+"]" + dbox.getString("d_etesttext")%></td>
        </tr>


<!-- 문제관련 이미지, 음성, 동영상 파일 시작 -->
<%      if (!dbox.getString("d_saveimage").equals("")) { %> 
        <tr> 
          <td bgcolor="#FFFFFF"> 
            <table cellspacing="0" cellpadding="0" class="img_table_out">
              <tr> 
                <td> 
                  <table cellspacing="2" cellpadding="2">
                    <tr> 
                      <td class="img_table" height="26"><img src='<%=v_urldir+dbox.getString("d_saveimage")%>'></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
<%      }  %>  

<%      if (!dbox.getString("d_saveaudio").equals("")) { %> 
        <tr> 
          <td class="board_text4"> <img src="/images/user/study/voice_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_saveaudio")%>"><%=dbox.getString("d_realaudio")%></a></td>
        </tr>
<%      }  %>  

<%      if (!dbox.getString("d_savemovie").equals("")) { %> 
        <tr> 
          <td class="board_text3"> <img src="/images/user/study/meida_icon1.gif" width="20" height="17" border="0"><a href="<%=v_urldir+dbox.getString("d_savemovie")%>"><%=dbox.getString("d_realmovie")%></a></td>
        </tr>
<%      }  %>        

<!-- 문제관련 이미지, 음성, 동영상 파일 끝 -->

<%      if (dbox.getString("d_etesttype").equals(ETestBean.OBJECT_QUESTION)) {   // 단일선택 
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="radio" name="<%=dbox.getInt("d_etestnum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>
<%              } 
            }
        } else if (dbox.getString("d_etesttype").equals(ETestBean.SUBJECT_QUESTION)) {  // 주관식  %>
        <tr> 
          <td class="board_text3"> 
            <textarea name="<%=dbox.getInt("d_etestnum")%>" cols="90" rows="5" class="input"></textarea>
          </td>
        </tr>
<%             
            
        } else if (dbox.getString("d_etesttype").equals(ETestBean.MULTI_QUESTION)) {  // 다답식
            for (int j=0; j < list.size(); j++) {
                dbox  = (DataBox)list.get(j); 
                if (dbox != null) { %>
        <tr> 
          <td class="board_text4"> 
            <input type="checkbox" name="<%=dbox.getInt("d_etestnum")%>" value="<%=dbox.getInt("d_selnum")%>" class='inputradio'><%=dbox.getInt("d_selnum")%>. <%=dbox.getString("d_seltext")%></td>
        </tr>

<%         }
		}
	 }
	} %>
      </form>
      </table>
    </td>
  </tr>
</table>
<!----------------- 학습평가 끝 ---------------->
<br>
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
    <tr>                
        <td align="right"><a href="javascript:whenSubmit()"><img src="/images/user/study/presentation_butt1.gif" alt="제출" width="54" height="20" border="0"></a></td>
    </tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
<br>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
