<%
/**
 * file name : za_SulmunSubjQuestion_I2.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 설문 문항관리 - 디테일페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
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
    String  v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getString("p_grcode");        //교육주관
	String v_gubunnm = "";
	    v_gubunnm = "과정설문";

    String  v_subj = box.getString("p_subj");
    String  v_sultype = box.getString("p_sultype");
    
    ArrayList  list = (ArrayList)request.getAttribute("ScaleQuestionExampleData");
 
    String v_reloadlist = box.getString("p_reloadlist");
	int v_scalecode = Integer.parseInt(box.getString("p_scalecode"));

%>
<html>
<head>
<title>설문문제관리-등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('SulmunQuestionInsert');
}
function ActionPage(p_process) {
	  if(!chkData()) {
         return;
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
	document.form2.p_selcount6.value = "7";
  }

  return true;
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>

function changeScale(flag) {

  if(flag == 1){
  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;
  }else{
  var v_scalecode = document.form2.p_scalecode6.options[document.form2.p_scalecode6.selectedIndex].value;
  }

  if(v_scalecode > 0){
	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionInsertS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.submit();
  } 
  }
function changeScaleC() {

  var v_scalecode = document.form2.p_scalecode5.options[document.form2.p_scalecode5.selectedIndex].value;

	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionInsertS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.p_sultype.value = "5";
		document.form2.submit();
 
  }
-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="javascript:display('<%=v_sultype%>')">
<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
		 <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  <%=v_gubunnm%> 신규문제 등록</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunSubjQuestionServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_scalecode"  value="<%=v_scalecode%>">
        <input type="hidden" name="p_reloadlist"  value="">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
<script language="JavaScript">
<!--
function display(flag) {
   
	var v_sultype = "";
	if(flag == "0"){
	    v_sultype = document.form2.p_sultype.options[document.form2.p_sultype.selectedIndex].value;
	}else {
	    v_sultype = flag;
	}
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
          <td class="table_title">설문분류</td>
          <td class="table_02_1"><%=CodeConfigBean.getCodeGubunSelect (SulmunSubjBean.DIST_CODE, "", 1, "p_distcode", box.getString("p_distcode"), "", 0)%></td>
          <td class="table_title">문제분류</td>
          <td class="table_02_1">
		  <SELECT name="p_sultype" onchange="display('0');"> 
            <option value=1>단일선택</option> 
            <option value=2>복수선택</option> 
            <option value=3>서술형</option> 
            <option value=4>복합형</option> 
            <option value=5>5점척도</option> 
            <option value=6>7점척도</option> 
		   <script>document.form2.p_sultype.value = <%=v_sultype%>;</script>
          </SELECT> 
          </td>
		</tr>

      <input type="hidden" name="p_sulreturn"  value="Y">
		<tr>
          <td class="table_title">문제</td>
          <td class="table_02_1" valign="top" colspan="3">
            <textarea name="p_sultext" cols="60" rows="12" class="input"><%=box.getString("p_sultext")%></textarea>
		  </td>
	   </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype1" STYLE="display:">
	    <input type="hidden" name="p_selmax1"  value="">
	    <input type="hidden" name="p_scalecode1"  value="">	
	    <input type="hidden" name="p_selcount1"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value="">
			 <input type="hidden" name="p_selpoint1"  value="0">
          </td>
        </tr>
<% } %>
	  </table>

	  <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype2" STYLE="display:none">
	    <input type="hidden" name="p_scalecode2"  value="">	
	    <input type="hidden" name="p_selcount2"  value="">		
		<tr>
    	  <td class="table_title">복수 선택수</td>
          <td class="table_02_1">
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
          </SELECT> 
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value="">
			 <input type="hidden" name="p_selpoint2"  value="0">
          </td>
        </tr>
<% } %>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype3" STYLE="display:none">
	    <input type="hidden" name="p_selmax3"  value="">
	    <input type="hidden" name="p_selcount3"  value="">
	    <input type="hidden" name="p_seltext3"  value="">	
	    <input type="hidden" name="p_scalecode3"  value="">	
  	    <input type="hidden" name="p_selpoint3"  value="0">
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype4" STYLE="display:none">
	    <input type="hidden" name="p_selmax4"  value="">
	    <input type="hidden" name="p_scalecode4"  value="">	
	    <input type="hidden" name="p_selcount4"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value="">
			 <input type="hidden" name="p_selpoint4"  value="0">
          </td>
        </tr>
<% } %>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype5" STYLE="display:none">
	    <input type="hidden" name="p_selmax5"  value="">
	    <input type="hidden" name="p_selcount5"  value="">		
		<tr>
    	  <td class="table_title">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "S", "p_scalecode5", v_scalecode, "onchange=changeScale('1')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 5; k++) {
       if (v_sultype.equals("5")){
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>' readonly>
          </td>
          <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점
          </td>
		  <input type="hidden" name="p_selpoint5"  value="<%=dbox.getInt("d_selpoint")%>">		  
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='' readonly>
			 <input type="hidden" name="p_selpoint5"  value="0">	
          </td>
          <td class="table_02_1">
          </td>
        </tr>
<%
      }
    }
%>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="sultype6" STYLE="display:none">
	    <input type="hidden" name="p_selmax6"  value="">
	    <input type="hidden" name="p_selcount6"  value="">	
		<tr>
    	  <td class="table_title">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("7", v_grcode, "S", "p_scalecode6", v_scalecode, "onchange=changeScale('2')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 7; k++) {
       if (v_sultype.equals("6")){
        DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>' readonly>
          </td>
          <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점
          </td>
		  <input type="hidden" name="p_selpoint6"  value="<%=dbox.getInt("d_selpoint")%>">	
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='' readonly>
			 <input type="hidden" name="p_selpoint6"  value="0">	
          </td>
          <td class="table_02_1">
          </td>
        </tr>
<%
      }
    }
%>
	  </table>



      </form>
	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" >
        <tr>
          <td><a href="javascript:InsertQuestion()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>
