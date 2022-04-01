<%
//**********************************************************
//  1. 제      목: 과정등록 화면
//  2. 프로그램명: za_Subject_I.jsp
//  3. 개      요: 과정등록 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 12. 15
//  7. 수      정: 
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
    
    String v_subj = box.getString("p_subj"); //과정코드
    System.out.println("v_subj = " + v_subj);
    
    DataBox dbox = (DataBox)request.getAttribute("subjectSelect");
    
    ArrayList  list1 = (ArrayList)request.getAttribute("budgetmemSubjList");
    ArrayList  list2 = (ArrayList)request.getAttribute("budgetSubjList");
    
	String v_subjnm 	= dbox.getString("d_subjnm");		//과정명                                                
	String v_edugubun 	= dbox.getString("d_edugubun");    //교육유형코드                                          
	String v_edugubunnm = dbox.getString("d_edugubunnm");  //교육유형명                                            
	String v_subjgubun 	= dbox.getString("d_subjgubun");   //과정구분(A:LMS등록과정,B:강사/예산관리시스템 등록 과정
	String v_edustarta 	= dbox.getString("d_edustarta");   //교육시작일                                            
	String v_eduenda 	= dbox.getString("d_eduenda");     //교육종료일                                            
	String v_edutargeta = dbox.getString("d_edutargeta");  //교육대상                                              
	String v_edutmema 	= dbox.getString("d_edutmema");    //교육총인원                                            
	String v_edutseqa 	= dbox.getString("d_edutseqa");    //교육총차수                                            
	String v_seqpermema = dbox.getString("d_seqpermema");  //차수당인원                                            
	String v_eduplacea 	= dbox.getString("d_eduplacea");   //교육장소                                              
	String v_gyamt 		= dbox.getString("d_gyamt");       //고용보험환급예상금액     
	
	String  ss_year		 = box.getStringDefault("s_year","");    		//년도	
    String  ss_edugubun   = box.getStringDefault("s_edugubun","ALL");    //교육유형
    String  ss_company    = box.getStringDefault("s_company","ALL");    	//회사
    String  ss_edudept    = box.getStringDefault("s_edudept","ALL");    	//교육주관부서
    String  ss_srchsubjnm = box.getStringDefault("p_srchsubjnm","");		//과정명
    String  ss_me		 = box.getString("p_me"); //자기가 등록한 과정

	
%>
<html>
<head>
<title>사이버과정등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT language="JavaScript">
$(document).ready(function(){
	$("#p_edustarta").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduenda").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
//등록 스크립트 시작//
function UpdateSubject() {
	ff = document.form2;
	
	
	if (ff.p_subjnm.value==""){
		alert("과정명을 입력하세요.");
		ff.p_subjnm.focus();
		return;
	}

	if (ff.p_edugubun.value=="ALL"){
		alert("교육유형을 선택하세요.");
		return;
	}

	if (ff.p_edustarta.value==""){
		alert("교육시작일을 입력하세요.");
		ff.p_edustarta.focus();
		return; 
	}
	
	if (ff.p_eduenda.value==""){
		alert("교육종료을 입력하세요.");
		ff.p_eduenda.focus();
		return;
	}
	
	if (ff.p_edustarta.value > ff.p_eduenda.value) {
		alert("교육종료일이 시작일보다 이전입니다.");
		return;
	}
	

	if (ff.p_edutmema.value==""){
		alert("교육인원정보가 잘못 입력되어 있습니다.");
		return;
	}
	
	if (ff.p_edutseqa.value==""){
		alert("교육총차수를 입력하세요.");
		ff.p_edutseqa.focus();
		return;
	}
	
	
	if (!chk_num(ff.p_seqpermema.value)){
		alert("교육인원정보가 잘못 입력되어 있습니다.");
		return;
	}
	
	if (ff.p_seqpermema.value=='0'){
		alert("교육인원을 입력하세요.");
		return;
	}
	
	if (!chk_num(ff.p_gyamt.value)){
		alert("고용보험환급예상금액이 잘못 입력되어 있습니다.");
		ff.p_gyamt.focus();
		return;
	}


	//프로그램 강사료를 더해서 예산 강사료와 비교

	var is_ok = true; //숫자 유효성 검사
	var chk_value = "";
	var total = 0; //프로그램 총 강사료
	
	for (var i=1 ;  i<=15 ; i++) {
		//숫자 유효성 검사 후 총 강사료 계산
		chk_value = eval('ff.p_eduprice' + i + ".value");
		if (chk_value!='') {
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		}
	}
	
	
	if (!is_ok) {
		alert('시간당 강사료는 숫자로만 입력가능합니다.');
		return;
	}
	
	if (total!=0) {
		if (ff.p_budget01a.value + ff.p_budget02a.value == 0) {
			alert("사내강사 또는 사외강사료를 입력하세요");
			ff.p_budget01a.focus();
			return;
		}
	}
	
	if (confirm("저장하시겠습니까?")) {
  		document.form2.p_process.value = 'SubjUpdate';
 		document.form2.submit();
 	}
}

function cal_total(p_inputname) {
	ff = document.form2;
	var is_ok = true; //숫자 유효성 검사
	var total = 0; //교육총인원
	var chk_value = "";
	
	var cnt = 0;
	
	if (p_inputname=="ff.p_edumem")
		cnt = <%=list1.size()%>;
	else 
		cnt = <%=list2.size()%>;
	
	//인원입력 체크
	for (var i=1 ;  i<=cnt ; i++) {
		if (i<10) {
			//숫자 유효성 검사 후 총인원 계산
			chk_value = eval(p_inputname + "0" + i + "a.value");
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		} else {
			//숫자 유효성 검사 후 총인원 계산
			chk_value = eval(p_inputname + i + "a.value");
			if (chk_num(chk_value)) {
				total += parseInt(chk_value);
			} else {
				is_ok = false;
			}
		}
	}

	if (is_ok) {
		if (p_inputname=="ff.p_edumem") {
			ff.p_edutmema.value = total;
		} else {
			ff.p_plancodetot.value = total;
		}
	} else {
		if (p_inputname=="ff.p_edumem") {
			ff.p_edutmema.value = "";
			ff.p_seqpermema.value = "";
		} else {
			ff.p_plancodetot.value = "";
		}
		return;
	}
	
	
	if (p_inputname=="ff.p_edumem") {
		//교육총차수 체크
		if (!chk_num(ff.p_edutseqa.value)) {
			ff.p_edutseqa.value = "";
			ff.p_seqpermema.value = "";
			return;
		}
		//차수당 인원 계산
		ff.p_seqpermema.value = parseInt(ff.p_edutmema.value / ff.p_edutseqa.value);
	} else {
		//예산 총금액 계산
	}

}

function chk_num(v_value) {
  if (v_value=="") return false;
  
  for (var i = 0 ;i < v_value.length ;i++ )  {
    if ((v_value.substring(i,i+1) < "0" || v_value.substring(i,i+1) > "9" )) {
      return false;
    }
  }
  return true;
}
//등록 스크립트 종료//


function goSubjectListPage() {
	document.form2.p_process.value = 'SubjList';
  	document.form2.submit();
}


function searchTutor(p_id) {
  var url = "/servlet/controller.budget.BudgetAdminServlet?p_process=searchTutor&p_id="+p_id;
  open_window("",url,"0","0","200","200");
}

function receiveTutor(p_id, tutorGubun , tutorName, tutorID) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	var v_tutorgubun = eval("document.form2.p_tutorgubun" + p_id);
	var v_tutorgubunnm = eval("document.form2.p_tutorgubunnm" + p_id);
	
	v_id.value=tutorID;
	v_name.value = tutorName;	
	v_tutorgubun.value = tutorGubun;

	if (tutorGubun == 'I') {
		v_tutorgubunnm.value = '사내강사';
	} else if (tutorGubun == 'O') {
		v_tutorgubunnm.value = '사외강사';
	} else {
		v_tutorgubunnm.value = '교육기관';
	}
}

function tutorCancel(p_id) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	var v_tutorgubun = eval("document.form2.p_tutorgubun" + p_id);
	var v_tutorgubunnm = eval("document.form2.p_tutorgubunnm" + p_id);
	v_id.value = "";
	v_name.value = "";
	v_tutorgubun.value = "";
	v_tutorgubunnm.value = "";
	
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form2" method="post" action="/servlet/controller.budget.BudgetAdminServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_subj"     value="<%=v_subj%>">

    <input type="hidden" name="s_year"     		value="<%=ss_year%>">		<!-- 년도 -->
    <input type="hidden" name="s_edugubun"     	value="<%=ss_edugubun%>">	<!-- 교육유형 -->
    <input type="hidden" name="s_company"     	value="<%=ss_company%>">	<!-- 회사 -->
    <input type="hidden" name="s_edudept"     	value="<%=ss_edudept%>">	<!-- 교육주관부서 -->
    <input type="hidden" name="p_srchsubjnm"    value="<%=ss_srchsubjnm%>">	<!-- 과정명 -->
    <input type="hidden" name="p_me"     		value="<%=ss_me%>">			<!-- 자기가 등록한 과정 -->

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/t_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
	   <br>
	           <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>개요/예산정보</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        
      <!--------------소제목 끝---------------------------->
        <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title_bold"><b>과정명</b></td>
            <td width="68%" class="table_02_2">
            <%//v_subjgubun.equals("A") - LMS 등록과정은 정보를 수정할 수 없음 %>
            <% if (v_subjgubun.equals("A")) { %>
            	<%=v_subjnm%>
            <input name="p_subjnm" type="hidden" value="<%=v_subjnm%>">
            <% } else { %>
            <input name="p_subjnm" type="text" class="input" size="118" maxlength="100" value="<%=v_subjnm%>">
            <% } %>
            </td>
            
          </tr>
          <tr> 
            <td class="table_title_bold"><b>교육유형</b></td>
            <td class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect("0024", "", 1, "p_edugubun", v_edugubun, "", 2)%></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>교육대상</b></td>
            <td class="table_02_2"><input name="p_edutargeta" type="text" class="input" size="118" maxlength="100" value="<%=v_edutargeta%>"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>교육장소</b></td>
            <td class="table_02_2"><input name="p_eduplacea" type="text" class="input" size="118" maxlength="100" value="<%=v_eduplacea%>"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>교육시작일</b></td>
            <td class="table_02_2"><input name="p_edustarta" id="p_edustarta" type="text" class="datepicker_input1" size="10" value="<%=v_edustarta%>"></td>
          </tr>
          <tr> 
            <td class="table_title_bold"><b>교육종료일</b></td>
            <td class="table_02_2"><input name="p_eduenda" id="p_eduenda" type="text" class="datepicker_input1" size="10" value="<%=v_eduenda%>"></td>
          </tr>
          <tr align="center"> 
            <td colspan="2" bgcolor="#FFFFFF" style=padding-top:5;padding-bottom:5;><table width="98%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="50%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td height="14" colspan="2" class="table_title_bold"><b>교육인원</b></td>
                      </tr>
					  <!-- 인원정보 시작 -->
                <%  for (int i=0; i<list1.size(); i++) {
				        DataBox dbox1 = (DataBox)list1.get(i); %> 
                <tr> 
                  <td width="37%" class="table_title_l" style="padding-left:30px;"><%=dbox1.getString("d_codenm")%></td>
                  <td width="63%" class="table_01"> <input name="p_edumem<%=dbox1.getString("d_code")%>a" type="text" class="input_r" size="20" maxlength="10" value="<%=dbox1.getInt("d_plandata")%>" onkeyup="cal_total('ff.p_edumem')">
                    명 </td>
                </tr>
                <%  } %>
                <input type="hidden" name="p_edumemcnt" value="<%=list1.size()%>">
                <!-- 교육인원정보 끝 -->
                    </table></td>
                  <td width="1%">&nbsp;</td>
                  <td width="49%"><table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td colspan="2" class="table_title_bold"><b>예산</b></td>
                      </tr>
					  <!-- 예산정보 시작 -->
				<%  int v_sumcost = 0;
				    for (int i=0; i<list2.size(); i++) {
				        DataBox dbox2 = (DataBox)list2.get(i); 
				        v_sumcost+=dbox2.getInt("d_plandata");
				%>
                <tr> 
                  <td width="47%" class="table_title_l" style="padding-left:40px;"><%=dbox2.getString("d_codenm")%></td>
                  <td width="53%" class="table_01"> <input name="p_budget<%=dbox2.getString("d_code")%>a" type="text" class="input_r" size="20" maxlength="10" value="<%=dbox2.getInt("d_plandata")%>" onkeyup="cal_total('ff.p_budget')">
                    원 </td>
                </tr>
				<%  } %>
				<tr> 
                  <td width="47%" class="table_title_l" style="padding-left:40px;">계</td>
                  <td width="53%" class="table_01"> <input name="p_plancodetot" type="text" class="input_r" size="20" value="<%=v_sumcost%>" readonly>
                    원 </td>
                </tr>
                <input type="hidden" name="p_budgetcnt" value="<%=list2.size()%>">
                <!-- 예산정보 끝 -->
                    </table></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td class="table_title">교육총인원</td>
            <td width="81%" class="table_02_2"><input name="p_edutmema" type="text" class="input_r" size="20" value="<%=v_edutmema%>" readonly></td>
          </tr>
          <tr> 
            <td class="table_title">교육총차수</td>
            <td class="table_02_2"><input name="p_edutseqa" type="text" class="input_r" size="20" maxlength="100" value="<%=v_edutseqa%>" onkeyup="cal_total('ff.p_edumem')"></td>
          </tr>
          <tr> 
            <td class="table_title">차수당인원</td>
            <td class="table_02_2"><input name="p_seqpermema" type="text" class="input_r" size="20" value="<%=v_seqpermema%>" readonly></td>
          </tr>
          <tr> 
            <td class="table_title">고용보험환급예상금액</td>
            <td class="table_02_2"><input name="p_gyamt" type="text" class="input_r" size="20" maxlength="100" value="<%=v_gyamt%>">
              원 </td>
          </tr>
        </table>
        <br>
		<!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>프로그램</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->

        <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td width="3%" class="table_title">번호</td>
            <td width="30%" class="table_title">교과명</td>
            <td width="22%" class="table_title">강사명/교육기관명</td>
            <td width="16%" class="table_title">사번/사업자번호</td>
            <td width="8%" class="table_title">강사구분</td>
            <td width="7%" class="table_title">차수당<br>교육시간(hr)</td>
            <td width="14%" class="table_title">시간당강사료(원)</td>
          </tr>
          
          <%ArrayList  list3 = (ArrayList)request.getAttribute("SubjProgramList");
          	for (int i=0; i<list3.size(); i++) {
		        DataBox dbox3 = (DataBox)list3.get(i); %>
          <%// for (int i=1;i<16;i++) { %>
          <tr> 
            <td class="table_02_2"><%=i+1%></td>
            <td class="table_02_2"><input name="p_lessonnm<%=i+1%>" type="text" class="input" size="45" maxlength="100" value="<%=dbox3.getString("d_lessonnm")%>"></td>
            <td class="table_02_2"> <input name="p_tutorname<%=i+1%>" type="text" class="input" size="20" maxlength="30" readonly onclick="searchTutor('<%=i+1%>')" value="<%=dbox3.getString("d_tutorname")%>"> 
              <a href="javascript:searchTutor('<%=i+1%>');">검색</a> <a href="javascript:tutorCancel('<%=i%>');">취소</a></td>
            <td class="table_02_1"><input name="p_tutorid<%=i+1%>" type="text" class="input" size="20" maxlength="20" readonly value="<%=dbox3.getString("d_tutorid")%>"></td>
            <td class="table_02_1"><input name="p_tutorgubun<%=i+1%>" type="hidden" class="input" size="4" value="<%=dbox3.getString("d_tutorgubun")%>">
			<input name="p_tutorgubunnm<%=i+1%>" type="text" class="input" size="8" value="<%=dbox3.getString("d_tutorgubunnm")%>" readonly=""></td>
            <td class="table_02_1"><input name="p_eduhr<%=i+1%>" type="text" class="input_r" size="4 maxlength="3" value="<%=dbox3.getInt("d_eduhr")%>"></td>
            <td class="table_02_1"><input name="p_eduprice<%=i+1%>" type="text" class="input_r" size="10" maxlength="8" value="<%=dbox3.getInt("d_price")%>"></td>
          </tr>
          <% } %>
          
          
          <%for (int i=list3.size()+1;i<16;i++) { %>
          <tr> 
            <td class="table_02_2"><%=i%></td>
            <td class="table_02_2"><input name="p_lessonnm<%=i%>" type="text" class="input" size="45" maxlength="100" value=""></td>
            <td class="table_02_2"> <input name="p_tutorname<%=i%>" type="text" class="input" size="20" maxlength="30" readonly onclick="searchTutor('<%=i%>')" value=""> 
              <a href="javascript:searchTutor('<%=i%>');">검색</a> <a href="javascript:tutorCancel('<%=i%>');">취소</a></td>
            <td class="table_02_1"><input name="p_tutorid<%=i%>" type="text" class="input" size="20" maxlength="20" readonly value=""></td>
            <td class="table_02_1"><input name="p_tutorgubun<%=i%>" type="hidden" value="">
			<input name="p_tutorgubunnm<%=i%>" type="text" class="input" size="8" value="" readonly></td>
            <td class="table_02_1"><input name="p_eduhr<%=i%>" type="text" class="input_r" size="4 maxlength="3" value=""></td>
            <td class="table_02_1"><input name="p_eduprice<%=i%>" type="text" class="input_r" size="10" maxlength="8" value=""></td>
          </tr>
          <% } %>
          
          
          <input type="hidden" name="p_lessoncnt" value="15">
          <!--input type="text" name="txtcurr" size="50" style="width:90%;" class="input_write" value="1개">
          <div id="sw_file_add_form"></div-->
        </table>  
        <!----------------- 과정맛보기 등록 끝 ----------------->
        <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif"  border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
