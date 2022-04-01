<%
/**
 * file name : za_SulmunQuestion_U.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 설문 문항관리 - 디테일페이지
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
%>
<%
    String  v_gubun = box.getStringDefault("p_gubun", SulmunQuestionBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getString("p_grcode");        //교육주관
    String  v_distcode    = box.getString("p_distcode");        //문제분류
    String  v_subj = box.getString("p_subj");
    String  v_action       = box.getString("p_action");
    String  v_process       = box.getString("p_process");
	String v_gubunnm = "";
	if(v_gubun.equals("TARGET")){
	    v_gubunnm = "대상자설문";
	}else if(v_gubun.equals("COMMON")){
	    v_gubunnm = "일반설문";
	}else if(v_gubun.equals("CONTENTS")){
	    v_gubunnm = "콘텐츠평가";
	}else{
	    v_gubunnm = "과정설문";
	}

	SulmunQuestionExampleData  data = (SulmunQuestionExampleData)request.getAttribute("SulmunQuestionExampleData");
	ScaleQuestionExampleData  sdata = (ScaleQuestionExampleData)request.getAttribute("ScaleQuestionExampleData");
   
    String v_reloadlist = box.getString("p_reloadlist");

	int v_sulnum = data.getSulnum();
	int v_scalecode = data.getScalecode();

	String v_sultype = data.getSultype();

%>
<html>
<head>
<title>설문문제관리-수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_research.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('QuestionInsert');
}
function UpdateQuestion(flag) {
  ActionPage('QuestionUpdate', flag);
}
function DeleteQuestion() {
  if (confirm("설문 문제를 삭제 하시겠습니까?")) {
    ActionPage('QuestionDelete');
  }
}
function ActionPage(p_process, flag) {
  if (flag == "1") {
     if(!chkDataC()) {
         return;
     }
  }else{
	  if(!chkData()) {
         return;
      }
  }
  document.form2.p_process.value = p_process;
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}
function chkData() {
  if (blankCheck(document.form2.p_sultext.value)) {
    document.form2.p_sultext.focus();
    alert('설문문제명을  입력하십시요.');
    return false;
  }

  var v_sultype = document.form2.p_sultype.options[document.form2.p_sultype.selectedIndex].value;
  var v_selcount = 0;
  var v_seltextcnt = 0;

  if (v_sultype == "1"){
    for (k=0; k<document.form2.p_seltext1.length; k++) {
      if (!blankCheck(document.form2.p_seltext1[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('설문문제 보기를  입력하십시요.');
      return false;
     }     
	v_selcount = v_seltextcnt;
	document.form2.p_selcount1.value = v_selcount;

  } else if (v_sultype == "2") {
    for (k=0; k<document.form2.p_seltext2.length; k++) {
      if (!blankCheck(document.form2.p_seltext2[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('설문문제 보기를  입력하십시요.');
      return false;
     }     

	if (v_seltextcnt < document.form2.p_selmax2.value) {
      alert('복수 선택수가 보기보다 많습니다.');
      return false;	 
	 }    
	v_selcount = v_seltextcnt;
	document.form2.p_selcount2.value = v_selcount;

  } else if (v_sultype == "4") {
    for (k=0; k<document.form2.p_seltext4.length; k++) {
      if (!blankCheck(document.form2.p_seltext4[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('설문문제 보기를  입력하십시요.');
      return false;
     }     
	v_selcount = v_seltextcnt;
	document.form2.p_selcount4.value = v_selcount;
  }  else if (v_sultype == "5") {
	if (1 > document.form2.p_scalecode5.value || document.form2.p_scalecode5.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount5.value = "5";
  }  else if (v_sultype == "6") {
	if (1 > document.form2.p_scalecode6.value || document.form2.p_scalecode6.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount5.value = "7";
  }

  return true;
}
function chkDataC() {
  if (blankCheck(document.form2.p_sultext.value)) {
    document.form2.p_sultext.focus();
    alert('설문문제명을  입력하십시요.');
    return false;
  }

  var v_selcount = 0;
  var v_seltextcnt = 0;

	if (1 > document.form2.p_scalecode5.value || document.form2.p_scalecode5.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount5.value = "5";

  return true;
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
function changeScale(flag) {

  if(flag == 1){
  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;
  }else{
  var v_scalecode = document.form2.p_scalecode6.options[document.form2.p_scalecode6.selectedIndex].value;
  }
	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunQuestionServlet";
		document.form2.p_process.value = "QuestionDetailUpdateS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.submit();
 
  }
function changeScaleC() {

  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;

	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunQuestionServlet";
		document.form2.p_process.value = "QuestionDetailUpdateS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.p_sultype.value = 5;
		document.form2.submit();
 
  }
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>

<% if (v_gubun.equals("CONTENTS")) { %>     

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
	
     
	
     
	<!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/tit_pop_sulmunman.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
     
     
     
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td valign="bottom" height="20">
		  <%=v_gubunnm%> 문제 수정</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunQuestionServlet">
        <input type="hidden" name="p_process" value="<%=v_process%>">
        <input type="hidden" name="p_action"  value="<%=v_action%>">
        <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
		<input type="hidden" name="p_sulnum"  value="<%=v_sulnum%>">
        <input type="hidden" name="p_scalecode"  value="<%=v_scalecode%>">
		<input type="hidden" name="p_reloadlist"  value="">
        <input type="hidden" name="p_sulreturn"  value="0">
        <input type="hidden" name="p_sultype"  value="<%=v_sultype%>">

        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
		<tr>
          <td class="table_title_01">평가분류</td>
          <td class="table_02_1" colspan="3"> 
		  <%=CodeConfigBean.getCodeGubunSelect (SulmunQuestionBean.DIST_CODE, "", 0, "p_distcode", box.getString("p_distcode"), "", 0)%>
		  </td>
		</tr>

		<tr>
          <td class="table_title_01">문제</td>
          <td class="table_02_1" colspan="3"> 
            <textarea name="p_sultext" cols="60" rows="12" class="input"><%=data.getSultext()%></textarea>
		  </td>
	   </table>

      <table cellspacing="1" cellpadding="5" class="table_out">
	    <input type="hidden" name="p_selmax5"  value="">
	    <input type="hidden" name="p_selcount5"  value="">	
		<tr>
    	  <td class="table_title_01">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "S", "p_scalecode5", v_scalecode, "onchange=changeScaleC()")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
          <td class="table_02_1"> 
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(1), "`", "'")%>' readonly>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(1)%>점</td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
          <td class="table_02_1"> 
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(2), "`", "'")%>' readonly>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(2)%>점</td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
          <td class="table_02_1"> 
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(3), "`", "'")%>' readonly>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(3)%>점</td>        
		</tr>
        <tr>
          <td class="table_01">4번</td>
          <td class="table_02_1"> 
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(4), "`", "'")%>' readonly>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(4)%>점</td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
          <td class="table_02_1"> 
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(5), "`", "'")%>' readonly>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(5)%>점</td>
        </tr>
	  </table>

      </form>
	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td align="right" style="padding-top=10"><a href="javascript:UpdateQuestion('1');"><font color="black">수정</font></a></td>
          <td align="right" style="padding-top=10"><a href="javascript:DeleteQuestion();"><font color="black">삭제</font></a></td>
          <td align="right" style="padding-top=10"><a href="javascript:window.self.close();"><font color="black">닫기</font></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>


<% } else { 

	String table1 = "";
	String table2 = "";
	String table3 = "";
	String table4 = "";
	String table5 = "";
	String table6 = "";
	if(v_sultype.equals("1")){
	   table1 = "display:";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	}else if(v_sultype.equals("2")){
	   table1 = "display:none";
	   table2 = "display:";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	}else if(v_sultype.equals("3")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	}else if(v_sultype.equals("4")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:";
	   table5 = "display:none";
	   table6 = "display:none";
	}else if(v_sultype.equals("5")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:";
	   table6 = "display:none";
	}else if(v_sultype.equals("6")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:";
	}
%>

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td valign="bottom" height="20">
		  <%=v_gubunnm%> 문제 수정</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunQuestionServlet">
        <input type="hidden" name="p_process" value="<%=v_process%>">
        <input type="hidden" name="p_action"  value="<%=v_action%>">
        <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
		<input type="hidden" name="p_sulnum"  value="<%=v_sulnum%>">
        <input type="hidden" name="p_scalecode"  value="<%=v_scalecode%>">
		<input type="hidden" name="p_reloadlist"  value="">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
<script language="JavaScript">
<!--
function display() {
    
	var v_sultype = document.form2.p_sultype.options[document.form2.p_sultype.selectedIndex].value;
	if(v_sultype=="1"){ 
		sultype1.style.display="";
        sultype2.style.display="none";
        sultype3.style.display="none";
        sultype4.style.display="none";
        sultype5.style.display="none";
        sultype6.style.display="none";
    }else if(v_sultype=="2"){ 
		sultype1.style.display="none";
        sultype2.style.display="";
        sultype3.style.display="none";
        sultype4.style.display="none";
        sultype5.style.display="none";
        sultype6.style.display="none";
    }else if(v_sultype=="3"){ 
		sultype1.style.display="none";
        sultype2.style.display="none";
        sultype3.style.display="";
        sultype4.style.display="none";
        sultype5.style.display="none";
        sultype6.style.display="none";
    }else if(v_sultype=="4"){ 
		sultype1.style.display="none";
        sultype2.style.display="none";
        sultype3.style.display="none";
        sultype4.style.display="";
        sultype5.style.display="none";
        sultype6.style.display="none";
    }else if(v_sultype=="5"){ 
		sultype1.style.display="none";
        sultype2.style.display="none";
        sultype3.style.display="none";
        sultype4.style.display="none";
        sultype5.style.display="";
        sultype6.style.display="none";
    }else if(v_sultype=="6"){ 
		sultype1.style.display="none";
        sultype2.style.display="none";
        sultype3.style.display="none";
        sultype4.style.display="none";
        sultype5.style.display="none";
        sultype6.style.display="";
    }
}
-->
</script>
		<tr>
          <td class="table_title_01">설문분류</td>
          <td class="table_02_1">
		  <%=CodeConfigBean.getCodeGubunSelect (SulmunQuestionBean.DIST_CODE, "", 1, "p_distcode", data.getDistcode(), "", 0)%>
		  </td>
          <td class="table_title_01">문제분류</td>
          <td class="table_02_1">
		  <SELECT name="p_sultype" onchange="display();"> 
            <option value=1>단일선택</option> 
            <option value=2>복수선택</option> 
            <option value=3>서술형</option> 
            <option value=4>복합형</option> 
            <option value=5>5점척도</option> 
            <option value=6>7점척도</option> 
          </SELECT> 
		  <script>document.form2.p_sultype.value = <%=data.getSultype()%>;</script>
          </td>
		</tr>

<% if (v_gubun.equals("COMMON")) { %>     
		<tr>
    	  <td class="table_title_01">답변유형</td>
          <td class="table_02_1" colspan="3">
		  <SELECT name="p_sulreturn"> 
            <option value=1>필수</option> 
            <option value=2>선택</option> 
          </SELECT> 
		  <script>document.form2.p_sulreturn.value = <%=data.getSulreturn()%>;</script>
		  </td>
		</tr>
<% } %>

		<tr>
          <td class="table_title_01">문제</td>
          <td class="table_02_1" valign="top" colspan="3">
            <textarea name="p_sultext" cols="60" rows="12" class="input"><%=data.getSultext()%></textarea>
		  </td>
	   </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype1" STYLE="<%=table1%>">
	    <input type="hidden" name="p_selmax1"  value="">
	    <input type="hidden" name="p_scalecode1"  value="">	
	    <input type="hidden" name="p_selcount1"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(1), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(2), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(3), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">4번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(4), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(5), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">6번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(6), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">7번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(7), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">8번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(8), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">9번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(9), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">10번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(10), "`", "'")%>'>
          </td>
        </tr>
	  </table>
      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype2" STYLE="<%=table2%>">
	    <input type="hidden" name="p_scalecode2"  value="">	
	    <input type="hidden" name="p_selcount2"  value="">		
		<tr>
    	  <td class="table_title_01">복수 선택수</td>
          <td class="table_02_1" align="left" colspan="3">
		  <SELECT name="p_selmax2"> 
            <option value=1>1</option> 
            <option value=2>2</option> 
            <option value=3>3</option> 
            <option value=4>4</option> 
            <option value=5>5</option> 
            <option value=6>6</option> 
            <option value=7>7</option> 
            <option value=8>8</option> 
            <option value=9>9</option> 
            <option value=10>10</option> 
		  <script>document.form2.p_selmax2.value = <%=data.getSelmax()%>;</script>
          </SELECT> 
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(1), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(2), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(3), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">4번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(4), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(5), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">6번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(6), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">7번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(7), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">8번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(8), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">9번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(9), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">10번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(10), "`", "'")%>'>
          </td>
        </tr>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype3" STYLE="<%=table3%>">
	    <input type="hidden" name="p_selmax3"  value="">
	    <input type="hidden" name="p_selcount3"  value="">
	    <input type="hidden" name="p_seltext3"  value="">	
	    <input type="hidden" name="p_scalecode3"  value="">	
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype4" STYLE="<%=table4%>">
	    <input type="hidden" name="p_selmax4"  value="">
	    <input type="hidden" name="p_scalecode4"  value="">	
	    <input type="hidden" name="p_selcount4"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(1), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(2), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(3), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">4번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(4), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(5), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">6번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(6), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">7번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(7), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">8번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(8), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">9번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(9), "`", "'")%>'>
          </td>
        </tr>
        <tr>
          <td class="table_01">10번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(data.getSeltext(10), "`", "'")%>'>
          </td>
        </tr>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype5" STYLE="<%=table5%>">
	    <input type="hidden" name="p_selmax5"  value="">
	    <input type="hidden" name="p_selcount5"  value="">	
		<tr>
    	  <td class="table_title_01">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "S", "p_scalecode5", v_scalecode, "onchange=changeScale('1')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
		  <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(1), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(1)%>점</td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
		  <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(2), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(2)%>점</td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
		  <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(3), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(3)%>점</td>
        </tr>
        <tr>
          <td class="table_01">4번</td>
		  <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(4), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(4)%>점</td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
		  <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(5), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(5)%>점</td>
        </tr>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype6" STYLE="<%=table6%>">
	    <input type="hidden" name="p_selmax6"  value="">
	    <input type="hidden" name="p_selcount6"  value="">	
		<tr>
    	  <td class="table_title_01">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("7", v_grcode, "S", "p_scalecode6", v_scalecode, "onchange=changeScale('2')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title_01">보기</td>
		</tr>
        <tr>
          <td class="table_01">1번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(1), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(1)%>점</td>
        </tr>
        <tr>
          <td class="table_01">2번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(2), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(2)%>점</td>
        </tr>
        <tr>
          <td class="table_01">3번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(3), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(3)%>점</td>
        </tr>
        <tr>
          <td class="table_01">4번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(4), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(4)%>점</td>
        </tr>
        <tr>
          <td class="table_01">5번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(5), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(5)%>점</td>
        </tr>
        <tr>
          <td class="table_01">6번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(6), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(6)%>점</td>
        </tr>
        <tr>
          <td class="table_01">7번</td>
		  <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(sdata.getSeltext(7), "`", "'")%>'>
          </td>
		  <td class="table_02_1"><%=sdata.getSelpoint(7)%>점</td>
        </tr>
	  </table>
      </form>
	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td align="right" style="padding-top=10"><a href="javascript:UpdateQuestion('1');"><font color="black">수정</font></a></td>
          <td align="right" style="padding-top=10"><a href="javascript:DeleteQuestion();"><font color="black">삭제</font></a></td>
          <td align="right" style="padding-top=10"><a href="javascript:window.self.close();"><font color="black">닫기</font></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
<% } %>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>
