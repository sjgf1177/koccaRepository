<%
//**********************************************************
//  1. 제      목: 외주업체 정보 수정화면
//  2. 프로그램명: zu_cpcomp_U.jsp
//  3. 개      요: 외주업체 정보 수정화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.16
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_cpseq = "", v_email = "", v_cpnm = "", v_cpresno = "", v_homesite = "", v_address = "", v_ldate = "", v_userid = "", v_name = "", v_comptel = "", v_pwd = "", v_compgubun="";
int i = 0, v_totalpage = 0, v_rowcount = 1, v_index1 = 0, v_index2 = 0;
String v_cpresno1 = "", v_cpresno2 = "", v_cpresno3 = "", v_str_tmp = "",v_comptel1 = "",v_comptel2 = "",v_comptel3 = "", v_handphone="", v_handphone1="",v_handphone2="",v_handphone3="";

String v_captinnm       = "";
String v_busicategory   = "";
int    v_employeetot    = 0;
int    v_employeeje     = 0;
int    v_employeebi     = 0;
String v_estabyear      = "";
String v_estabmon       = "";
String v_estabday       = "";
int    v_capital        = 0;
int    v_capitalplan    = 0;
int    v_capitalresult  = 0;
int    v_ownsubj        = 0;
int    v_goyonownsubj   = 0;
int    v_bigoyonownsubj = 0;
int    v_devsubj        = 0;
int    v_goyondevsubj   = 0;
int    v_bigoyondevsubj = 0;
int    v_devsystem      = 0;
String v_familysite     = "";
String v_vision         = "";
String v_mission        = "";
String v_mainbusi       = "";
String v_subbusi        = "";
String v_ownsolution    = "";
String v_prizerecord    = "";
String v_auth           = "";
String v_systemnm       = "";
String v_special        = "";
String v_bigo           = "";
String v_resno = "";



//out.println(box.getString("vflag"));

//if(!box.getString("vflag").equals("0")) {
	
	DataBox dbox = (DataBox)request.getAttribute("selectComp");
	
	v_cpseq     = dbox.getString("d_cpseq");
	v_cpnm      = dbox.getString("d_cpnm");
	v_cpresno   = dbox.getString("d_cpresno");
	v_homesite  = dbox.getString("d_homesite");
	v_address   = dbox.getString("d_address");
	v_ldate     = dbox.getString("d_ldate");
	v_userid    = dbox.getString("d_userid");
	v_pwd       = dbox.getString("d_pwd");
	v_name      = dbox.getString("d_name");
	v_resno     = dbox.getString("d_resno");
	v_email     = dbox.getString("d_email");
	v_comptel   = dbox.getString("d_comptel");
	v_handphone = dbox.getString("d_handphone");
	v_compgubun = dbox.getString("d_compgubun");
	
    v_captinnm       = dbox.getString("d_captinnm");
    v_busicategory   = dbox.getString("d_busicategory");
    v_employeetot    = dbox.getInt   ("d_employeetot");
    v_employeeje     = dbox.getInt   ("d_employeeje");
    v_employeebi     = dbox.getInt   ("d_employeebi");
    v_estabyear      = dbox.getString("d_estabyear");
    v_estabmon       = dbox.getString("d_estabmon");
    v_estabday       = dbox.getString("d_estabday");
    v_capital        = dbox.getInt   ("d_capital");
    v_capitalplan    = dbox.getInt   ("d_capitalplan");
    v_capitalresult  = dbox.getInt   ("d_capitalresult");
    v_ownsubj        = dbox.getInt   ("d_ownsubj");
    v_goyonownsubj   = dbox.getInt   ("d_goyonownsubj");
    v_bigoyonownsubj = dbox.getInt   ("d_bigoyonownsubj");
    v_devsubj        = dbox.getInt   ("d_devsubj");
    v_goyondevsubj   = dbox.getInt   ("d_goyondevsubj");
    v_bigoyondevsubj = dbox.getInt   ("d_bigoyondevsubj");
    v_devsystem      = dbox.getInt   ("d_devsystem");
    v_familysite     = dbox.getString("d_familysite");
    v_vision         = dbox.getString("d_vision");
    v_mission        = dbox.getString("d_mission");
    v_mainbusi       = dbox.getString("d_mainbusi");
    v_subbusi        = dbox.getString("d_subbusi");
    v_ownsolution    = dbox.getString("d_ownsolution");
    v_prizerecord    = dbox.getString("d_prizerecord");
    v_auth           = dbox.getString("d_auth");
    v_systemnm       = dbox.getString("d_systemnm");
    v_special        = dbox.getString("d_special");
    v_bigo           = dbox.getString("d_bigo");
	    
	//v_cpresno1 = v_cpresno.substring(1,3);
	//v_cpresno2 = v_cpresno.substring(5,6);
	//v_cpresno3 = v_cpresno.substring(8,12);
	
	//사업자등록번호 구분자(-) 가려서 가져오기
	if(v_cpresno.length() > 0){
		v_index1       =   v_cpresno.indexOf('-');
		v_cpresno1    =   v_cpresno.substring(0,v_index1);
		v_str_tmp     =   v_cpresno.substring(v_index1+1);
		v_index2       =   v_str_tmp.indexOf('-');
		v_cpresno2   =   v_str_tmp.substring(0,v_index2);
		v_cpresno3   =   v_str_tmp.substring(v_index2+1);
	}
	
	//전화번호 구분자(-) 가려서 가져오기
	if(v_comptel.length() > 0){
		v_index1       =   v_comptel.indexOf('-');
		v_comptel1    =   v_comptel.substring(0,v_index1);
		v_str_tmp     =   v_comptel.substring(v_index1+1);
		v_index2       =   v_str_tmp.indexOf('-');
		v_comptel2   =   v_str_tmp.substring(0,v_index2);
		v_comptel3   =   v_str_tmp.substring(v_index2+1);
	}
	
	
	//전화번호 구분자(-) 가려서 가져오기
	if(v_handphone.length() > 0){
		v_index1     = v_handphone.indexOf('-');
		v_handphone1 = v_handphone.substring(0,v_index1);
		v_str_tmp    = v_handphone.substring(v_index1+1);
		v_index2     = v_str_tmp.indexOf('-');
		v_handphone2 = v_str_tmp.substring(0,v_index2);
		v_handphone3 = v_str_tmp.substring(v_index2+1);
	}
	
	//int v_pageno = box.getInt("p_pageno"); 
	
	String s_userid = box.getSession("userid");
	String s_usernm = box.getSession("username");
     
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function checkForm(){
	var tf = document.form1;
	var cpresno = "";

	//if(tf.p_cpresno1.value.length == 0 || tf.p_cpresno2.value.length == 0 || tf.p_cpresno3.value.length == 0){

	for(i=1;i<4;i++){
		cpresno = eval('tf.p_cpresno'+i);
		//if(cpresno.value.length == 0){
		//	alert("사업자 등록번호를 입력해주세요.");
		//	cpresno.focus();
		//	return;
		//	break;
		//}
	}
	
	//alert(tf.p_compgubun.value);
	if(tf.p_cpnm.value.length == 0){
		alert("업체명을 입력해주세요.");
		tf.p_cpnm.focus();
		return;
	}
	if(tf.p_pwd.value.length == 0){
		alert("비밀번호를 입력해주세요.");
		tf.p_pwd.focus();
		return;
	}
	if(tf.p_pwd.value != tf.p_pwd2.value){
		alert("비밀번호 확인을 다시 해주세요.");
		tf.p_pwd2.focus();
		return;		
	}
	if(tf.p_name.value.length == 0){
		alert("담당자명을 입력해주세요.");
		tf.p_name.focus();
		return;
	}
	MoveLink(5);
}

function MoveLink(act,cpseq) {
	if(act == "1"){
		//리스트 페이지로
		document.form1.p_process.value   = "";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();	
	}
	else if(act == "2"){
		//상세보기 페이지로
		document.form1.p_process.value   = "select";
		document.form1.p_cpseq.value   	 = cpseq;	
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
	else if(act == "3"){
		//등록페이지로
	    document.form1.p_process.value   = "insertPage";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
	else if(act == "4"){
		//등록처리 페이지로
	    document.form1.p_process.value   = "insert";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
	else if(act == "5"){
		//수정등록처리 페이지로
	    document.form1.p_process.value   = "update";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
}

//-->
</script>
</head>

<%
	//외주업체 담당자라면 일부항목은 수정할 수 없게함.
%>
<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_cpseq" value="<%=v_cpseq%>">
<input type="hidden" name="p_userid" value="<%=v_userid%>">
<input type="hidden" name="p_sourcecomp" value="<%=v_compgubun%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>정보수정</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>  
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	  <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="16%">업체명</td>
          <td class="table_02_2"  width="84%">
          <%if(box.getSession("gadmin").equals("S1") || box.getSession("gadmin").equals("M1") || box.getSession("gadmin").equals("T1")){%>
            <%=v_cpnm%><input type="hidden" name="p_cpnm" value="<%=v_cpnm%>">
          <%}else{%>
            <input name="p_cpnm" type="text" class="input" style="width:200" value="<%=v_cpnm%>">
          <%}%>
          </td>
        </tr>
        <%if(box.getSession("gadmin").equals("S1") || box.getSession("gadmin").equals("M1") || box.getSession("gadmin").equals("T1")){%>
          <input type="hidden" name="p_compgubun" value="<%=v_compgubun%>">
        <%}else{%>
        <tr> 
          <td class="table_title" width="16%">업체형태</td>
          <td class="table_02_2"  width="84%">
          <input type="radio" name="p_compgubun" value="S" <%if(v_compgubun.equals("S")){out.println("checked");}%>> CP &nbsp;
          <input type="radio" name="p_compgubun" value="T" <%if(v_compgubun.equals("T")){out.println("checked");}%>> BP &nbsp;
          <input type="radio" name="p_compgubun" value="M" <%if(v_compgubun.equals("M")){out.println("checked");}%>> CP + BP
          </td>
        </tr>        
        <%}%>
        <tr> 
          <td class="table_title">담당자ID</td>
          <td class="table_02_2"><%=v_userid%></td>
        </tr>
        <tr> 
          <td class="table_title">비밀번호</td>
          <td class="table_02_2"><input name="p_pwd" type="password" class="input" style="width:100" maxlength="20" value="<%=v_pwd%>"></td>
        </tr>
        <tr> 
          <td class="table_title">비밀번호확인</td>
          <td class="table_02_2"><input name="p_pwd2" type="password" class="input" style="width:100" maxlength="20" value="<%=v_pwd%>"></td>
        </tr>
        <tr> 
          <td class="table_title">담당자명</td>
          <td class="table_02_2">
          <%if(box.getSession("gadmin").equals("S1") || box.getSession("gadmin").equals("M1") || box.getSession("gadmin").equals("T1")){%>
            <%=v_name%><input type="hidden" name="p_name" value="<%=v_name%>">
          <%}else{%>
            <input name="p_name" type="text" class="input" style="width:100" value="<%=v_name%>">
          <%}%>
          </td>
        </tr>
        <tr> 
          <td class="table_title">주민등록번호</td>
          <td class="table_02_2"><input name="p_resno" type="text" class="input" size="20" maxlength="13" onkeyup="numeric_chk(this)" value="<%=v_resno%>">(- 없이)</td>
        </tr>
        <tr> 
          <td class="table_title">E-Mail</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5><input name="p_email" type="text" class="input" style="width:200" value="<%=v_email%>"></td>
        </tr>
        <tr>
          <td class="table_title">회사전화</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  <input name="p_comtel1" type="text" class="input" style="width:50" maxlength="3" value="<%=v_comptel1%>">
            - 
            <input name="p_comtel2" type="text" class="input" style="width:70" maxlength="4" value="<%=v_comptel2%>">
            - 
            <input name="p_comtel3" type="text" class="input" style="width:70" maxlength="4" value="<%=v_comptel3%>">
			</td>
        </tr>
        <tr>
          <td class="table_title">핸드폰</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  <input name="p_handphone1" type="text" class="input" style="width:50" maxlength="3" value="<%=v_handphone1%>">
            - 
            <input name="p_handphone2" type="text" class="input" style="width:70" maxlength="4" value="<%=v_handphone2%>">
            - 
            <input name="p_handphone3" type="text" class="input" style="width:70" maxlength="4" value="<%=v_handphone3%>">
			</td>
        </tr>
      </table>
      
            
      <!--****************************************************************추가정보 시작****************************************************************-->
      <BR>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>추가정보</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>  
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	    <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <!--*************************************************회사개요시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="10">회<br>사<br>개<br>요</td>
          <td class="table_title" width="12%" colspan='2'>홈페이지</td>
          <td class="table_02_2"  width="84%"><input name="p_homesite" type="text" class="input" style="width:600" value="<%=v_homesite%>"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>FAMILYSITE</td>
          <td class="table_02_2"  width="84%"><textarea name="p_familysite" cols="80" rows="5"><%=v_familysite%></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>대표이사</td>
          <td class="table_02_2"  width="84%" ><input name="p_captinnm" type="text" class="input" style="width:600" value="<%=v_captinnm%>"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>업종</td>
          <td class="table_02_2"  width="84%"><input name="p_busicategory" type="text" class="input" style="width:600" value="<%=v_busicategory%>"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>사업자등록번호</td>
          <td class="table_02_2"  width="84%"><input name="p_cpresno1" type="text" class="input" maxlength="3" style="width:100" value="<%=v_cpresno1%>">
            - 
            <input name="p_cpresno2" type="text" class="input" maxlength="2" style="width:60" value="<%=v_cpresno2%>">
            - 
            <input name="p_cpresno3" type="text" class="input" maxlength="5" style="width:100" value="<%=v_cpresno3%>"></td>
        </tr>
        <tr> 
          <td class="table_title" width="3%" rowspan='3'>현<br>재</td>
          <td class="table_title" width="12%">종업원(TOTAL)</td>
          <td class="table_02_2"  width="84%"><input name="p_employeetot" type="text" class="input" style="width:40" value="<%=v_employeetot%>">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%">정규직</td>
          <td class="table_02_2"  width="84%"><input name="p_employeeje" type="text" class="input" style="width:40" value="<%=v_employeeje%>">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%">비정규직</td>
          <td class="table_02_2"  width="84%"><input name="p_employeebi" type="text" class="input" style="width:40" value="<%=v_employeebi%>">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>설립년월일</td>
          <td class="table_02_2"  width="84%"><input name="p_estabyear" type="text" class="input" size="4" maxlength=4 value="<%=v_estabyear%>">년<input name="p_estabmon" type="text" class="input" size="4" maxlength=2 value="<%=v_estabmon%>">월<input name="p_estabday" type="text" class="input" size="4" maxlength=2 value="<%=v_estabday%>">일</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>주소</td>
          <td class="table_02_2"  width="84%"><input name="p_address" type="text" class="input" style="width:600" value="<%=v_address%>"></td>
        </tr>
        <!--*************************************************회사개요끝*************************************************-->
        
        <!--*************************************************PR시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="4">P<br><br><br>R</td>
          <td class="table_title" width="12%" colspan='2'>Vision</td>
          <td class="table_02_2"  width="84%"><textarea name="p_vision" cols="80" rows="5"><%=v_vision%></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>Mission</td>
          <td class="table_02_2"  width="84%"><textarea name="p_mission" cols="80" rows="5"><%=v_mission%></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="3%" rowspan='2'>중<br>점<br>사<br>업<br>영<br>역</td>
          <td class="table_title" width="12%" >주력사업</td>
          <td class="table_02_2"  width="84%" height="120"><textarea name="p_mainbusi" cols="80" rows="5"><%=v_mainbusi%></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" >신규사업</td>
          <td class="table_02_2"  width="84%" height="120"><textarea name="p_subbusi" cols="80" rows="5"><%=v_subbusi%></textarea></td>
        </tr>
        <!--*************************************************PR끝*************************************************-->
        
        <!--*************************************************IR시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="3">I<br><br><br>R</td>
          <td class="table_title" width="12%" colspan='2'>자본금</td>
          <td class="table_02_2"  width="84%"><input name="p_capital" type="text" class="input" style="width:80" value="<%=v_capital%>">
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>매출계획</td>
          <td class="table_02_2"  width="84%"><input name="p_capitalplan" type="text" class="input" style="width:80" value="<%=v_capitalplan%>"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>매출실적</td>
          <td class="table_02_2"  width="84%"><input name="p_capitalresult" type="text" class="input" style="width:80" value="<%=v_capitalresult%>"></td>
        </tr>
        <!--*************************************************IR 끝*************************************************-->
        
        <!--*************************************************실적1*************************************************-->
        <tr> 
          <td class="table_title" width="4%"  rowspan="2">실<br>적<br>1</td>
          <td class="table_title" width="12%" colspan='2'>수상경력</td>
          <td class="table_02_2"  width="84%"><textarea name="p_prizerecord" cols="80" rows="5"><%=v_prizerecord%></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>인증획득</td>
          <td class="table_02_2"  width="84%"><textarea name="p_auth" cols="80" rows="5"><%=v_auth%></textarea></td>
        </tr>
        <!--*************************************************실적1 끝*************************************************-->
        
        <!--*************************************************실적2 시작*************************************************-->
        <tr>
          <td class="table_title" width="4%" rowspan="6">실<br>적<br>2</td>
          <td class="table_title" width="12%" colspan='2'>자체보유과정수(total)</td>
          <td class="table_02_2"  width="84%"><input name="p_ownsubj" type="text" class="input" style="width:40" value="<%=v_ownsubj%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="3%" rowspan="2">&nbsp;</td>
          <td class="table_title" width="12%">고용보험적용</td>
          <td class="table_02_2"  width="84%"><input name="p_goyonownsubj" type="text" class="input" style="width:40" value="<%=v_goyonownsubj%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%">고용보험비적용</td>
          <td class="table_02_2"  width="84%"><input name="p_bigoyonownsubj" type="text" class="input" style="width:40" value="<%=v_bigoyonownsubj%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>개발/납품 과정수(total)</td>
          <td class="table_02_2"  width="84%"><input name="p_devsubj" type="text" class="input" style="width:40" value="<%=v_devsubj%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="3%" rowspan="2">&nbsp;</td>
          <td class="table_title" width="12%">고용보험적용</td>
          <td class="table_02_2"  width="84%"><input name="p_goyondevsubj" type="text" class="input" style="width:40" value="<%=v_goyondevsubj%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%">고용보험비적용</td>
          <td class="table_02_2"  width="84%"><input name="p_bigoyondevsubj" type="text" class="input" style="width:40" value="<%=v_bigoyondevsubj%>"></td>
        </tr>
        <!--*************************************************실적2 끝*************************************************-->
        
        <!--*************************************************실적3 시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="3">실<br>적<br>3</td>
          <td class="table_title" width="12%" colspan='2'>보유솔루션</td>
          <td class="table_02_2"  width="84%"><textarea name="p_ownsolution" cols="80" rows="5"><%=v_ownsolution%></textarea></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>개발납품시스템수</td>
          <td class="table_02_2"  width="84%"><input name="p_devsystem" type="text" class="input" style="width:40" value="<%=v_devsystem%>"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>시스템명</td>
          <td class="table_02_2"  width="84%"><textarea name="p_systemnm" cols="80" rows="5"><%=v_systemnm%></textarea></td>
        </tr>
        <!--*************************************************실적3 끝*************************************************-->
        
        <!--*************************************************특징/비고*************************************************-->
        <tr> 
          <td class="table_title" width="16%" colspan='3'>특징</td>
          <td class="table_02_2"  width="84%"><textarea name="p_special" cols="80" rows="5"><%=v_special%></textarea></td>
        </tr>
        <tr>
          <td class="table_title" width="16%" colspan=3>비고</td>
          <td class="table_02_2"  width="84%"><textarea name="p_bigo" cols="80" rows="5"><%=v_bigo%></textarea></td>
        </tr>
        <!--*************************************************실적3 끝*************************************************-->
      </table>
      <!--****************************************************************추가정보 끝****************************************************************-->
      
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="11" height=6></td>
        </tr>
        <tr> 
          <td width="895" align="right" ></td>
          <td align="right" valign="top"><a href="JavaScript:checkForm()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
          <td width="11">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %> 
    </td>
  </tr>
</table>
</form>
</body>
</html>