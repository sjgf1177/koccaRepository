<%
//**********************************************************
//  1. 제	  목: 교육차수 과정 일괄수정 화면
//  2. 프로그램명: za_Grseq_Assign_U.jsp
//  3. 개	  요: 교육차수 과정 일괄수정 화면
//  4. 환	  경: JDK 1.4
//  5. 버	  젼: 1.0
//  6. 작	  성: 노희성 2004. 11. 11
//  7. 수	  정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) {
		box = RequestManager.getBox(request);
	}
	String v_grcode = box.getString("p_grcode");
	String v_gyear = box.getString("p_gyear");
	String v_grseq = box.getString("p_grseq");
	box.put("s_grcode", v_grcode);
	box.put("s_gyear", v_gyear);
	box.put("s_grseq", v_grseq);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript">

function updateScore(){
	var f = document.form1;
	var gradscore = f.p_gradscore.value; // 수료점수
	
	var gradstep = f.p_gradstep.value;	//진도율
	var gradexam = f.p_gradexam.value;  //중간평가
	var gradftest = f.p_gradftest.value;  // 최종평가
	var gradhtest = f.p_gradhtest.value;	// 형성평가
	var gradreport = f.p_gradreport.value;	//레포트
	var gradetc1 = f.p_gradetc1.value;	//참여도

	var wstep = f.p_wstep.value;	//진도율(%)
	var wmtest = f.p_wmtest.value;  //중간평가(%)
	var wftest = f.p_wftest.value;  // 최종평가(%)
	var whtest = f.p_whtest.value;	// 형성평가(%)
	var wreport = f.p_wreport.value;	//레포트(%)
	var wetc1 = f.p_wetc1.value;	//참여도(%)

	num_che_0_100('수료점수', gradscore);
	num_che_0_100('진도율', gradstep);
	num_che_0_100('중간평가', gradexam);
	num_che_0_100('최종평가', gradftest);
	num_che_0_100('형성평가', gradhtest);
	num_che_0_100('레포트', gradreport);
	num_che_0_100('참여도', gradetc1);
	num_che_0_100('진도율 가중치', wstep);
	num_che_0_100('중간평가 가중치', wmtest);
	num_che_0_100('최종평가 가중치', wftest);
	num_che_0_100('형성평가 가중치', whtest);
	num_che_0_100('레포트 가중치', wreport);
	num_che_0_100('참여도 가중치', wetc1);

	var wtot = parseFloat(f.p_wstep.value,10) + parseFloat(f.p_wmtest.value,10) + parseFloat(f.p_wftest.value,10) + parseFloat(f.p_whtest.value,10)+ parseFloat(f.p_wreport.value,10) + parseFloat(f.p_wetc1.value,10);
	if(wtot != 100){ alert("가중치의 합은 100이여야 합니다."); return;}

	f.action='/servlet/controller.course.GrseqServlet';
	f.p_process.value = 'updateSubjScore';
	f.target = "_self";
	f.submit();
}

function num_che_0_100(pname,num){
	var msg = "OK";
	if(num == ''){		msg = pname+"를 입력해 주세요."; alert(msg); return;}
	if(num > 100){		msg = pname+" 100보다 큽니다."; alert(msg); return;}
	else if(num < 0){		msg = pname+" 0보다 작습니다."; alert(msg); return;}
}

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name = "form1" method = "post" action="/servlet/controller.course.GrseqServlet">
	<input type="hidden" name="p_process"   value="updateSubjScore">
	<input type="hidden" name="s_grcode"	value="<%=v_grcode%>">
	<input type="hidden" name="p_grcode"	value="<%=v_grcode%>">
	<input type="hidden" name="p_gyear"	 value="<%=v_gyear%>">
	<input type="hidden" name="p_grseq"	 value="<%=v_grseq%>">


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr>
		  <td><img src="/images/admin/course/c_title02.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
		<br>
		<br>

		<!----------------- 교육그룹, (대분류, 중분류, 소분류, 과정) 검색 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
			<tr>
				<td colspan="2" class="table_top_line"></td>
			</tr>
			<tr>
				<td width="16%" height="25" class="table_title"><b>교육그룹</b></td>
				<td class="table_02_1" width="81%"> <b><%=GetCodenm.get_grcodenm(v_grcode)%></b></td>
			</tr>
			<tr>
				<td class="table_title" height="25"><b>교육차수</b></td>
				<td class="table_02_1"> <b><%=GetCodenm.get_grseqnm(v_grcode, v_gyear, v_grseq)%> (<%=v_gyear%>년 <%=v_grseq%>차)</b></td>
			</tr>
		</table>
		<br/>
		<table class="table_out" cellspacing="1" cellpadding="5" width="95%">
			<tr>
				<td colspan="4" class="table_top_line"></td>
			</tr>
			<tr>
				<td colspan="4" class="table_02_2">
					※ 초기에 노출된 점수는 해당 교육 차수와 관계없이 지정된 값입니다.<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;수정하고자 하는 값을 입력 후 저장버튼을 클릭 시 해당 교육차수에 대한 모든 과정이 수정됩니다.<br/>
					&nbsp;&nbsp;&nbsp;&nbsp;변경된 사항을 확인 하고자 하실 경우 차수개설 리스트에서 해당 과정 상세보기를 통하여 확인하실 수 있습니다.<br/><br/>
					
					※ 가중치의 총 합은 100% 이여야 합니다.<br/>
					※ 각 점수는 0~100 사이 값이여야 합니다.<br/>
				</td>
			</tr>
			<tr>
				<td width="16%" height="25" class="table_title"><b>수료점수</b></td>
				<td colspan="3" class="table_02_2"> <input type="text" name="p_gradscore" size="4" value="60" /> 점</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>진도율</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradstep" size="4"  value="60" /> 점 이상</td>
				<td width="16%" class="table_title"><b>진도율 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_wstep" size="4"  value="100" /> %</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>중간평가</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradexam" size="4"  value="0" /> 점 이상</td>
				<td width="16%" class="table_title"><b>중간평가 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_wmtest" size="4"  value="0" /> %</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>최종평가</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradftest" size="4"  value="0" /> 점 이상</td>
				<td width="16%" class="table_title"><b>최종평가 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_wftest" size="4"  value="0" /> %</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>형성평가</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradhtest" size="4"  value="0" /> 점 이상</td>
				<td width="16%" class="table_title"><b>형성평가 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_whtest" size="4"  value="0" /> %</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>리포트</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradreport" size="4"  value="0" /> 점 이상</td>
				<td width="16%" class="table_title"><b>리포트 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_wreport" size="4"  value="0" /> %</td>
			</tr>
			<tr>
				<td width="16%" class="table_title"><b>참여도</b></td>
				<td class="table_02_2"> <input type="text" name="p_gradetc1" size="4"  value="0" /> 점 이상</td>
				<td width="16%" class="table_title"><b>참여도 가중치</b></td>
				<td class="table_02_2"> <input type="text" name="p_wetc1" size="4"  value="0" /> %</td>
			</tr>
		</table>

		<br>
		<!----------------- 저장, 취소 버튼 시작 ----------------->
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">
					<a href="javascript:updateScore();"><img src="/images/admin/button/btn_save.gif" border="0"></a>
					<a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a>
				</td>
			</tr>
		</table>
		<!----------------- 저장, 취소 버튼 끝 ----------------->

		<br>
	  </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>








