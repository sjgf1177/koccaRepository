<%
//**********************************************************
//  1. 제      목: 외주업체 정보 등록화면
//  2. 프로그램명: zu_cpcomp_I.jsp
//  3. 개      요: 외주업체 정보 등록화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005.7.27
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

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript" src="/script/cresys_lib.js"></script>
<script language="VBScript"   src="/script/cresys_lib.vbs"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
function checkForm(){
    var tf = document.form1;
	var cpresno = "";
	
	//if(tf.p_cpresno1.value.length == 0 || tf.p_cpresno2.value.length == 0 || tf.p_cpresno3.value.length == 0){
	
	//alert(tf.p_compgubunop.options[0].value);
	
	//var boxlength = tf.p_compgubunop.length;
	//var i ;
	
	//for(i = 0; i < boxlength ; i++){
	//  if(tf.p_compgubunop.options[i].selected){
	//    tf.p_compgubun.value = tf.p_compgubunop.options[i].value;
	//  }
	//}
	
	//alert(tf.p_compgubun.value);
	
	for(i=1;i<4;i++){
		cpresno = eval('tf.p_cpresno'+i);
		//alert(cpresno);
		//if(cpresno.value.length == 0){
		//	alert("사업자 등록번호를 입력해주세요.");
		//	cpresno.focus();
		//	return;
		//	break;
		//}
	}
	
	//return;
	if(tf.p_cpnm.value.length == 0){
		alert("업체명을 입력해주세요.");
		tf.p_cpnm.focus();
		return;
	}
	
	if(tf.p_compgubun.value == ""){
		alert("업체형태을 선택해주세요.");
		tf.p_cpnm.focus();
		return;
	}	
	if(tf.p_isidchk.value == "N"){
		alert("담당자ID 중복체크를 해주세요");
		tf.p_userid.focus();
		return;
	}
	if(tf.p_userid.value.length == 0){
		alert("담당자 아이디를 입력해주세요.");
		tf.p_userid.focus();
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
	
	//return;
	
    tf.p_process.value   = "insert";
    tf.target    = "_self";
    tf.action            = "/servlet/controller.cp.CPCompServlet";
    tf.submit();

	
	/*
	if(tf.p_ischk.value == 0){			
		tf.p_userid.focus();
		return;
	}
	else	
		MoveLink(4);
	*/
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
}

function CheckUserid(){
	var tf = document.form1;
	    
	if(tf.p_userid.value.length == 0){
		alert("아이디를 입력하셔야 합니다.");
		tf.p_userid.focus();
		return;
	}
	else{
	    //document.form1.p_process.value   = "usercheck";
	    //document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	   	//document.form1.target            = "chk_frame";
	    //document.form1.submit();
	    //document.form1.target            = "";

	    window.self.name = "exituserchk";
	    //(name, url, left, top, width, height, toolbar, menubar, statusbar, scrollbar, resizable)
        open_window("userchk","","100","100","400","250","no","no","yes","no","no");
        document.form1.target = "userchk";
        document.form1.p_isidchk.value = "Y";
        document.form1.action='/servlet/controller.cp.CPCompServlet';
        document.form1.p_process.value = 'usercheck';
        document.form1.submit();
        
	}   
}       
function changechk(){
  document.form1.p_isidchk.value = "N";
  return;
}
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>

<form name = "form1" method = "post">
<input type= "hidden" name= "p_process" value= "">
<input type= "hidden" name= "p_cpseq" value="">
<input type= "hidden" name= "p_ischk" value= "0">
<input type= "hidden" name= "p_isidchk" value= "N">
<input type= "hidden" name= "p_isoverlap" value= "N">
<!--input type= "hidden" name= "p_compgubun" value= ""-->

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
          <td class=sub_title>정보등록</td>
          
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>  
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	    <tr>
          <td colspan="3" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="4%" rowspan='10'>담<br><br>당<br><br>자</td>
          <td class="table_title" width="12%">업체명</td>
          <td class="table_02_2"  width="84%"><input name="p_cpnm" type="text" class="input" style="width:200"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%">업체형태</td>
          <td class="table_02_2"  width="84%">
          <input type="radio" name="p_compgubun" value="S" checked> CP &nbsp;
          <input type="radio" name="p_compgubun" value="T"> BP &nbsp;
          <input type="radio" name="p_compgubun" value="M"> CP + BP
          </td>
        </tr>
        <tr> 
          <td class="table_title">담당자ID</td>
          <td class="table_02_2"><table border="0"><tr><td><input name="p_userid" type="text" onkeypress="javascript:changechk()" class="input" style="width:100">&nbsp;</td><td><a href="javascript:CheckUserid()"><img src="/images/admin/button/btn_addcheck.gif" border="0"></a></td><td> *담당자ID는 한번등록한이후 변경이 불가합니다.</td></tr></table></td>
        </tr>
        <tr> 
          <td class="table_title">비밀번호</td>
          <td class="table_02_2"><input name="p_pwd" type="password" class="input" style="width:100"></td>
        </tr>
        <tr>
          <td class="table_title">비밀번호확인</td>
          <td class="table_02_2"><input name="p_pwd2" type="password" class="input" style="width:100"></td>
        </tr>
        <tr> 
          <td class="table_title">담당자명</td>
          <td class="table_02_2"><input name="p_name" type="text" class="input" style="width:100"></td>
        </tr>
        <tr> 
          <td class="table_title">주민등록번호</td>
          <td class="table_02_2"><input name="p_resno" type="text" class="input" size="20" maxlength="13" onkeyup="numeric_chk(this)">(- 없이)</td>
        </tr>
        <tr> 
          <td class="table_title">E-Mail</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5><input name="p_email" type="text" class="input" style="width:200"></td>
        </tr>
        <tr>
          <td class="table_title">회사전화</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  <input name="p_comtel1" type="text" class="input" maxlength="3" style="width:50">
            - 
            <input name="p_comtel2" type="text" class="input" maxlength="4" style="width:70">
            - 
            <input name="p_comtel3" type="text" class="input" maxlength="4" style="width:70">
			</td>
        </tr>
        <tr>
          <td class="table_title">핸드폰</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  <input name="p_handphone1" type="text" class="input" maxlength="3" style="width:50">
            - 
            <input name="p_handphone2" type="text" class="input" maxlength="4" style="width:70">
            - 
            <input name="p_handphone3" type="text" class="input" maxlength="4" style="width:70">
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
          <td class="table_02_2"  width="84%"><input name="p_homesite" type="text" class="input" style="width:600" value="http://"></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>FAMILYSITE</td>
          <td class="table_02_2"  width="84%"><textarea name="p_familysite" cols="80" rows="5"></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>대표이사</td>
          <td class="table_02_2"  width="84%" ><input name="p_captinnm" type="text" class="input" style="width:600" value=""></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>업종</td>
          <td class="table_02_2"  width="84%"><input name="p_busicategory" type="text" class="input" style="width:600" value=""></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>사업자등록번호</td>
          <td class="table_02_2"  width="84%"><input name="p_cpresno1" type="text" class="input" maxlength="3" style="width:100" onkeyup="numeric_chk(this)">
            - 
            <input name="p_cpresno2" type="text" class="input" maxlength="2" style="width:60" onkeyup="numeric_chk(this)">
            - 
            <input name="p_cpresno3" type="text" class="input" maxlength="5" style="width:100" onkeyup="numeric_chk(this)"></td>
        </tr>
        <tr> 
          <td class="table_title" width="3%" rowspan='3'>현<br>재</td>
          <td class="table_title" width="12%">종업원(TOTAL)</td>
          <td class="table_02_2"  width="84%"><input name="p_employeetot" type="text" class="input" style="width:40" onkeyup="numeric_chk(this)">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%">정규직</td>
          <td class="table_02_2"  width="84%"><input name="p_employeeje" type="text" class="input" style="width:40" onkeyup="numeric_chk(this)">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%">비정규직</td>
          <td class="table_02_2"  width="84%"><input name="p_employeebi" type="text" class="input" style="width:40" onkeyup="numeric_chk(this)">명</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" colspan='2'>설립년월일</td>
          <td class="table_02_2"  width="84%"><input name="p_estabyear" type="text" class="input" size="4" maxlength=4 onkeyup="numeric_chk(this)">년<input name="p_estabmon" type="text" class="input" size="4" maxlength=2 onkeyup="numeric_chk(this)">월<input name="p_estabday" type="text" class="input" size="4" maxlength=2 onkeyup="numeric_chk(this)">일 </td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>주소</td>
          <td class="table_02_2"  width="84%"><input name="p_address" type="text" class="input" style="width:600"></td>
        </tr>
        <!--*************************************************회사개요끝*************************************************-->
        
        <!--*************************************************PR시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="4">P<br><br><br>R</td>
          <td class="table_title" width="12%" colspan='2'>Vision</td>
          <td class="table_02_2"  width="84%"><textarea name="p_vision" cols="80" rows="5"></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>Mission</td>
          <td class="table_02_2"  width="84%"><textarea name="p_mission" cols="80" rows="5"></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="3%" rowspan='2'>중<br>점<br>사<br>업<br>영<br>역</td>
          <td class="table_title" width="12%" >주력사업</td>
          <td class="table_02_2"  width="84%" height="120"><textarea name="p_mainbusi" cols="80" rows="5"></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%" >신규사업</td>
          <td class="table_02_2"  width="84%" height="120"><textarea name="p_subbusi" cols="80" rows="5"></textarea></td>
        </tr>
        <!--*************************************************PR끝*************************************************-->
        
        <!--*************************************************IR시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="3">I<br><br><br>R</td>
          <td class="table_title" width="12%" colspan='2'>자본금</td>
          <td class="table_02_2"  width="84%"><input name="p_capital" type="text" class="input" style="width:80" onkeyup="numeric_chk(this)">억원
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>매출계획</td>
          <td class="table_02_2"  width="84%"><input name="p_capitalplan" type="text" class="input" style="width:80" onkeyup="numeric_chk(this)">억원</td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>매출실적</td>
          <td class="table_02_2"  width="84%"><input name="p_capitalresult" type="text" class="input" style="width:80" onkeyup="numeric_chk(this)">억원</td>
        </tr>
        <!--*************************************************IR 끝*************************************************-->
        
        <!--*************************************************실적1*************************************************-->
        <tr> 
          <td class="table_title" width="4%"  rowspan="2">실<br>적<br>1</td>
          <td class="table_title" width="12%" colspan='2'>수상경력</td>
          <td class="table_02_2"  width="84%"><textarea name="p_prizerecord" cols="80" rows="5"></textarea></td>
        </tr>
        <tr> 
          <td class="table_title" width="12%"  colspan='2'>인증획득</td>
          <td class="table_02_2"  width="84%"><textarea name="p_auth" cols="80" rows="5"></textarea></td>
        </tr>
        <!--*************************************************실적1 끝*************************************************-->
        
        <!--*************************************************실적2 시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="6">실<br>적<br>2</td>
          <td class="table_title" width="12%" colspan='2'>자체보유과정수(total)</td>
          <td class="table_02_2"  width="84%"><input name="p_ownsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="3%" rowspan="2">&nbsp;</td>
          <td class="table_title" width="12%">고용보험적용</td>
          <td class="table_02_2"  width="84%"><input name="p_goyonownsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%">고용보험비적용</td>
          <td class="table_02_2"  width="84%"><input name="p_bigoyonownsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>개발/납품 과정수(total)</td>
          <td class="table_02_2"  width="84%"><input name="p_devsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="3%" rowspan="2">&nbsp;</td>
          <td class="table_title" width="12%">고용보험적용</td>
          <td class="table_02_2"  width="84%"><input name="p_goyondevsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%">고용보험비적용</td>
          <td class="table_02_2"  width="84%"><input name="p_bigoyondevsubj" type="text" class="input" style="width:40"></td>
        </tr>
        <!--*************************************************실적2 끝*************************************************-->
        
        <!--*************************************************실적3 시작*************************************************-->
        <tr> 
          <td class="table_title" width="4%" rowspan="3">실<br>적<br>3</td>
          <td class="table_title" width="12%" colspan='2'>보유솔루션</td>
          <td class="table_02_2"  width="84%"><textarea name="p_ownsolution" cols="80" rows="5"></textarea></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>개발납품시스템수</td>
          <td class="table_02_2"  width="84%"><input name="p_devsystem" type="text" class="input" style="width:40"></td>
        </tr>
        <tr>
          <td class="table_title" width="12%" colspan=2>시스템명</td>
          <td class="table_02_2"  width="84%"><textarea name="p_systemnm" cols="80" rows="5"></textarea></td>
        </tr>
        <!--*************************************************실적3 끝*************************************************-->
        
        <!--*************************************************특징/비고*************************************************-->
        <tr> 
          <td class="table_title" width="16%" colspan='3'>특징</td>
          <td class="table_02_2"  width="84%"><textarea name="p_special" cols="80" rows="5"></textarea></td>
        </tr>
        <tr>
          <td class="table_title" width="16%" colspan=3>비고</td>
          <td class="table_02_2"  width="84%"><textarea name="p_bigo" cols="80" rows="5"></textarea></td>
        </tr>
        <!--*************************************************실적3 끝*************************************************-->
      </table>
      <!--****************************************************************추가정보 시작****************************************************************-->
      
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="11" height=6></td>
          <td width="32" height=6 align="right"></td>
        </tr>
        <tr> 
          <td width="895" align="right" ></td>
          <td width="32" align="right"><a href="JavaScript:checkForm()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
          <td width="11">&nbsp;</td>
          <td width="32" align="right"><a href="JavaScript:MoveLink(1)"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table></td>
  </tr>
  <%@ include file = "/learn/library/getJspName.jsp" %>
</table>
</form>

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
</body>
</html>
