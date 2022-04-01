<%
/**
 * file name : za_DamunCommonQuestion_U.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 다면평가 문항관리 - 디테일페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
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
    String  v_gubun = box.getStringDefault("p_gubun", DamunCommonBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getString("p_grcode");        //교육주관
    String  v_subj = box.getString("s_subjcourse");
    String  v_action       = box.getString("p_action");
    String  v_process       = box.getString("p_process");
	String v_gubunnm = "";
	    v_gubunnm = "일반 다면평가";

    ArrayList  list = (ArrayList)request.getAttribute("DamunQuestionExampleData");
    DataBox dbox0 = (DataBox)list.get(0);
	int v_damunnum = dbox0.getInt("d_damunnum");
	int v_scalecode = dbox0.getInt("d_scalecode");
	int v_fscalecode = dbox0.getInt("d_fscalecode");
	int v_sscalecode = dbox0.getInt("d_sscalecode");
	String v_damuntype = dbox0.getString("d_damuntype");

    ArrayList  list1 = (ArrayList)request.getAttribute("ScaleQuestionExampleData");
	ArrayList blist1 = new ArrayList();
	ArrayList blist2 = new ArrayList();

	String v_reloadlist = box.getString("p_reloadlist");

	String table1 = "";
	String table2 = "";
	String table3 = "";
	String table4 = "";
	String table5 = "";
	String table6 = "";
	String table7 = "";
	if(v_damuntype.equals("1")){
	   table1 = "display:";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	   table7 = "display:none";
	}else if(v_damuntype.equals("2")){
	   table1 = "display:none";
	   table2 = "display:";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	   table7 = "display:none";
	}else if(v_damuntype.equals("3")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	   table7 = "display:none";
	}else if(v_damuntype.equals("4")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:";
	   table5 = "display:none";
	   table6 = "display:none";
	   table7 = "display:none";
	}else if(v_damuntype.equals("5")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:";
	   table6 = "display:none";
	   table7 = "display:none";
	}else if(v_damuntype.equals("6")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:";
	   table7 = "display:none";
	}else if(v_damuntype.equals("7")){
	   table1 = "display:none";
	   table2 = "display:none";
	   table3 = "display:none";
	   table4 = "display:none";
	   table5 = "display:none";
	   table6 = "display:none";
	   table7 = "display:";
	}
%>

<html>
<head>
<title>다면평가문제관리-수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('DamunQuestionInsert');
}
function UpdateQuestion() {
  ActionPage('DamunQuestionUpdate');
}
function DeleteQuestion() {
  if (confirm("다면평가 문제를 삭제 하시겠습니까?")) {
    ActionPage('DamunQuestionDelete');
  }
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
  if (blankCheck(document.form2.p_damuntext.value)) {
    document.form2.p_damuntext.focus();
    alert('다면평가문제명을  입력하십시요.');
    return false;
  }

  var v_damuntype = document.form2.p_damuntype.options[document.form2.p_damuntype.selectedIndex].value;
  var v_selcount = 0;
  var v_seltextcnt = 0;

  if (v_damuntype == "1"){
    for (k=0; k<document.form2.p_seltext1.length; k++) {
      if (!blankCheck(document.form2.p_seltext1[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('다면평가문제 보기를  입력하십시요.');
      return false;
     }     
	v_selcount = v_seltextcnt;
	document.form2.p_selcount1.value = v_selcount;

  } else if (v_damuntype == "2") {
    for (k=0; k<document.form2.p_seltext2.length; k++) {
      if (!blankCheck(document.form2.p_seltext2[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('다면평가문제 보기를  입력하십시요.');
      return false;
     }     

	if (v_seltextcnt < document.form2.p_selmax2.value) {
      alert('복수 선택수가 보기보다 많습니다.');
      return false;	 
	 }    
	v_selcount = v_seltextcnt;
	document.form2.p_selcount2.value = v_selcount;

  } else if (v_damuntype == "4") {
    for (k=0; k<document.form2.p_seltext4.length; k++) {
      if (!blankCheck(document.form2.p_seltext4[k].value)) {
        v_seltextcnt++;
      }
    }
    if (v_seltextcnt == 0) {
      alert('다면평가문제 보기를  입력하십시요.');
      return false;
     }     
	v_selcount = v_seltextcnt;
	document.form2.p_selcount4.value = v_selcount;
  }  else if (v_damuntype == "5") {
	if (1 > document.form2.p_fscalecode5.value || document.form2.p_fscalecode5.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount5.value = "5";
  }  else if (v_damuntype == "6") {
	if (1 > document.form2.p_fscalecode6.value || document.form2.p_fscalecode6.value=="") {
      alert('척도을 선택해 주세요,');
      return false;	 
	 }    
	document.form2.p_selcount6.value = "7";
  }   else if (v_damuntype == "7") {
	if (1 > document.form2.p_fscalecode7.value || document.form2.p_fscalecode7.value=="") {
      alert('제1척도을 선택해 주세요,');
      return false;	 
	 }
	 if (1 > document.form2.p_sscalecode7.value || document.form2.p_sscalecode7.value=="") {
      alert('제2척도을 선택해 주세요,');
      return false;	 
	 }
	document.form2.p_selcount7.value = "10";
  }

  return true;
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
function changeScale(flag) {

  var v_damuntype = document.form2.p_damuntype.options[document.form2.p_damuntype.selectedIndex].value;
  var v_scalecode = 0;
  var v_fscalecode = 0;
  var v_sscalecode = 0;
  var v_scaleflag = flag;
  if(v_damuntype == 5){
   v_scalecode = document.form2.p_fscalecode5.options[document.form2.p_fscalecode5.selectedIndex].value;
  }else if(v_damuntype==6){
   v_scalecode = document.form2.p_fscalecode6.options[document.form2.p_fscalecode6.selectedIndex].value;
  }else if(v_damuntype==7){
   v_fscalecode = document.form2.p_fscalecode7.options[document.form2.p_fscalecode7.selectedIndex].value;
   v_sscalecode = document.form2.p_sscalecode7.options[document.form2.p_sscalecode7.selectedIndex].value;
  }

  if(v_scalecode > 0 || v_fscalecode > 0 || v_sscalecode > 0){
	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.multiestimate.DamunCommonQuestionServlet";
		document.form2.p_process.value = "DamunQuestionUpdateS";
		document.form2.p_scalecode.value = v_scalecode;
		document.form2.p_fscalecode.value = v_fscalecode;
		document.form2.p_sscalecode.value = v_sscalecode;
		document.form2.p_damuntype.value = v_damuntype;
		document.form2.submit();
  }
  }
-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  <%=v_gubunnm%> 문제 수정</td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunCommonQuestionServlet">
        <input type="hidden" name="p_process" value="<%=v_process%>">
        <input type="hidden" name="p_action"  value="<%=v_action%>">
        <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
		<input type="hidden" name="p_damunnum"  value="<%=v_damunnum%>">
        <input type="hidden" name="p_scalecode"  value="">
		<input type="hidden" name="p_fscalecode"  value="<%=v_fscalecode%>">
        <input type="hidden" name="p_sscalecode"  value="<%=v_sscalecode%>">
		<input type="hidden" name="p_reloadlist"  value="">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
<script language="JavaScript">
<!--
function display(flag) {
   
	var v_damuntype = "";
	if(flag == "0"){
	    v_damuntype = document.form2.p_damuntype.options[document.form2.p_damuntype.selectedIndex].value;
	}else {
	    v_damuntype = flag;
	}
	if(v_damuntype=="1"){ 
		damuntype1.style.display="";
        damuntype2.style.display="none";
        damuntype3.style.display="none";
        damuntype4.style.display="none";
        damuntype5.style.display="none";
        damuntype6.style.display="none";
        damuntype7.style.display="none";
    }else if(v_damuntype=="2"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="";
        damuntype3.style.display="none";
        damuntype4.style.display="none";
        damuntype5.style.display="none";
        damuntype6.style.display="none";
        damuntype7.style.display="none";
    }else if(v_damuntype=="3"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="none";
        damuntype3.style.display="";
        damuntype4.style.display="none";
        damuntype5.style.display="none";
        damuntype6.style.display="none";
        damuntype7.style.display="none";
    }else if(v_damuntype=="4"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="none";
        damuntype3.style.display="none";
        damuntype4.style.display="";
        damuntype5.style.display="none";
        damuntype6.style.display="none";
        damuntype7.style.display="none";
    }else if(v_damuntype=="5"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="none";
        damuntype3.style.display="none";
        damuntype4.style.display="none";
        damuntype5.style.display="";
        damuntype6.style.display="none";
        damuntype7.style.display="none";
    }else if(v_damuntype=="6"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="none";
        damuntype3.style.display="none";
        damuntype4.style.display="none";
        damuntype5.style.display="none";
        damuntype6.style.display="";
        damuntype7.style.display="none";
    }else if(v_damuntype=="7"){ 
		damuntype1.style.display="none";
        damuntype2.style.display="none";
        damuntype3.style.display="none";
        damuntype4.style.display="none";
        damuntype5.style.display="none";
        damuntype6.style.display="none";
        damuntype7.style.display="";
    }
}
-->
</script>
		<tr>
          <td class="table_title">문제분류</td>
          <td class="table_02_1">
		  <SELECT name="p_damuntype" onchange="display('0');"> 
            <option value=1>단일선택</option> 
            <option value=2>복수선택</option> 
            <option value=3>서술형</option> 
            <option value=4>복합형</option> 
            <option value=5>5점척도</option> 
            <option value=6>7점척도</option> 
            <option value=7>GAP비교</option> 
          </SELECT> 
		  <script>document.form2.p_damuntype.value = <%=dbox0.getString("d_damuntype")%>;</script>
          </td>
		</tr>

      <input type="hidden" name="p_damunreturn"  value="Y">

		<tr>
          <td class="table_title">문제</td>
          <td class="table_02_1"> 
            <textarea name="p_damuntext" cols="60" rows="12" class="input"><%=dbox0.getString("d_damuntext")%></textarea>
		  </td>
	   </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype1" STYLE="<%=table1%>">
	    <input type="hidden" name="p_selmax1"  value="">
	    <input type="hidden" name="p_fscalecode1"  value="">	
	    <input type="hidden" name="p_sscalecode1"  value="">	
	    <input type="hidden" name="p_selcount1"  value="">	
	    <input type="hidden" name="p_scalename1"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
       if (v_damuntype.equals("1") && list.size() >= k ){
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>'>
          </td>
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext1" type="text" class="input" size="60" value=''>
	    <input type="hidden" name="p_selpoint1"  value="0">	
          </td>
        </tr>
<%
      }
    }
%>
	  </table>

	  <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype2" STYLE="<%=table2%>">
	    <input type="hidden" name="p_fscalecode2"  value="">	
	    <input type="hidden" name="p_sscalecode2"  value="">	
	    <input type="hidden" name="p_selcount2"  value="">		
	    <input type="hidden" name="p_scalename2"  value="">	
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
		  <script>document.form2.p_selmax2.value = <%=dbox0.getInt("d_selmax")%>;</script>
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
       if (v_damuntype.equals("2") && list.size() >= k ){
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>'>
	    <input type="hidden" name="p_selpoint2"  value="0">	
          </td>
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext2" type="text" class="input" size="60" value=''>
	    <input type="hidden" name="p_selpoint2"  value="0">	
          </td>
        </tr>
<%
      }
    }
%>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype3" STYLE="<%=table3%>">
	    <input type="hidden" name="p_selmax3"  value="">
	    <input type="hidden" name="p_selcount3"  value="">
	    <input type="hidden" name="p_seltext3"  value="">	
	    <input type="hidden" name="p_fscalecode3"  value="">	
	    <input type="hidden" name="p_sscalecode3"  value="">	
	    <input type="hidden" name="p_scalename3"  value="">	
	    <input type="hidden" name="p_selpoint3"  value="0">	
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype4" STYLE="<%=table4%>">
	    <input type="hidden" name="p_selmax4"  value="">
	    <input type="hidden" name="p_fscalecode4"  value="">	
	    <input type="hidden" name="p_sscalecode4"  value="">	
	    <input type="hidden" name="p_selcount4"  value="">	
	    <input type="hidden" name="p_scalename4"  value="">	
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    for (int k=1; k <= 10; k++) {
       if (v_damuntype.equals("4") && list.size() >= k ){
         DataBox dbox = (DataBox)list.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>'>
	    <input type="hidden" name="p_selpoint4"  value="0">	
          </td>
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext4" type="text" class="input" size="60" value=''>
	    <input type="hidden" name="p_selpoint4"  value="0">	
          </td>
        </tr>
<%
      }
    }
%>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype5" STYLE="<%=table5%>">
	    <input type="hidden" name="p_selmax5"  value="">
	    <input type="hidden" name="p_selcount5"  value="">	
	    <input type="hidden" name="p_sscalecode5"  value="">	
		<tr>
    	  <td class="table_title">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "D", "p_fscalecode5", v_fscalecode, "onchange=changeScale('1')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    if(list1.size() > 0){
       blist1 = (ArrayList)list1.get(0);
	 } 
	for (int k=1; k <= 5; k++) {
       if (v_damuntype.equals("5") && blist1.size()>0){

        DataBox dbox = (DataBox)blist1.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>'>
	         <input type="hidden" name="p_scalename5"  value="<%=dbox.getString("d_scalename")%>">
          </td>
		  <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점</td>
	    <input type="hidden" name="p_selpoint5"  value="<%=dbox.getInt("d_selpoint")%>">	
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext5" type="text" class="input" size="60" value=''>
	         <input type="hidden" name="p_scalename5"  value="">
	    <input type="hidden" name="p_selpoint5"  value="0">	
          </td>
		  <td class="table_02_1"></td>
        </tr>
<%
      }
    }
%>
	  </table>

      <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype6" STYLE="<%=table6%>">
	    <input type="hidden" name="p_selmax6"  value="">
	    <input type="hidden" name="p_selcount6"  value="">	
	    <input type="hidden" name="p_sscalecode6"  value="">	
		<tr>
    	  <td class="table_title">척도명</td>
          <td class="table_02_1" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("7", v_grcode, "D", "p_fscalecode6", v_fscalecode, "onchange=changeScale('2')")%>
		  </td>
		</tr>		
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    if(list1.size() > 0){
       blist1 = (ArrayList)list1.get(0);
	 } 
	for (int k=1; k <= 7; k++) {
       if (v_damuntype.equals("6") && blist1.size()>0){

        DataBox dbox = (DataBox)blist1.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>'>
	         <input type="hidden" name="p_scalename6"  value="<%=dbox.getString("d_scalename")%>">
          </td>
		  <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점</td>
	    <input type="hidden" name="p_selpoint6"  value="<%=dbox.getInt("d_selpoint")%>">	
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext6" type="text" class="input" size="60" value=''>
	         <input type="hidden" name="p_scalename6"  value="">
	    <input type="hidden" name="p_selpoint6"  value="0">	
          </td>
		  <td class="table_02_1"></td>
        </tr>
<%
      }
    }
%>
      </table>
 
	  <table cellspacing="1" cellpadding="5" class="table_out" ID="damuntype7" STYLE="<%=table7%>">
	    <input type="hidden" name="p_selmax7"  value="">
	    <input type="hidden" name="p_selcount7"  value="">	
		<tr>
    	  <td class="table_title">제1척도명</td>
          <td class="table_01" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "D", "p_fscalecode7", v_fscalecode, "onchange=changeScale('3')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  
    if(list1.size() > 0){
       blist1 = (ArrayList)list1.get(0);
	 } 
	for (int k=1; k <= 5; k++) {
       if (v_damuntype.equals("7") && blist1.size()>0){

        DataBox dbox = (DataBox)blist1.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext7" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>' readonly>
	         <input type="hidden" name="p_scalename7"  value="<%=dbox.getString("d_scalename")%>">
          </td>
		  <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점</td>
	    <input type="hidden" name="p_selpoint7"  value="<%=dbox.getInt("d_selpoint")%>">	
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext7" type="text" class="input" size="60" value='' readonly>
	         <input type="hidden" name="p_scalename7"  value="">
	    <input type="hidden" name="p_selpoint7"  value="0">	
          </td>
          <td class="table_02_1">
          </td>
        </tr>
<%
      }
    }
%>
		<tr>
    	  <td class="table_title">제2척도명</td>
          <td class="table_01" colspan="3" align="left">
		  <%=ScaleBean.getScaleCodeSelect ("5", v_grcode, "D", "p_sscalecode7", v_sscalecode, "onchange=changeScale('3')")%>
		  </td>
		</tr>
		<tr>
		  <td></td>
		</tr>
		<tr>
		  <td colspan="4" class="table_title">보기</td>
		</tr>
<%  

    if(list1.size() > 0){
       blist2 = (ArrayList)list1.get(1);
	 } 

	for (int k=1; k <= 5; k++) {
       if (v_damuntype.equals("7") && blist2.size()>0){

        DataBox dbox = (DataBox)blist2.get(k-1);   
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext7" type="text" class="input" size="60" value='<%=StringManager.replace(dbox.getString("d_seltext"), "`", "'")%>' readonly>
	         <input type="hidden" name="p_scalename7"  value="<%=dbox.getString("d_scalename")%>">
          </td>
		  <td class="table_02_1"><%=dbox.getInt("d_selpoint")%>점</td>
	    <input type="hidden" name="p_selpoint7"  value="<%=dbox.getInt("d_selpoint")%>">	
        </tr>
<%
	   } else {
%>
        <tr>
          <td class="table_01"><%=String.valueOf(k)%>번</td>
          <td class="table_02_1">
             <input name="p_seltext7" type="text" class="input" size="60" value='' readonly>
	         <input type="hidden" name="p_scalename7"  value="">
	    <input type="hidden" name="p_selpoint7"  value="0">	
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
      <table cellspacing="0" cellpadding="0">
        <tr>
          <td align="right"><a href="javascript:UpdateQuestion();"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="right"><a href="javascript:DeleteQuestion();"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="right"><a href="javascript:window.self.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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
