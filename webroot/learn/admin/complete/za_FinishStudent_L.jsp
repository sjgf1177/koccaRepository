<%
//**********************************************************
//  1. 제      목: 수료처리-수강생현황 리스트
//  2. 프로그램명: za_FinishStudent_L.java
//  3. 개      요: 수료처리-수강생현황 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 6. 30
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	String  ss_action       = box.getString("s_action");
    
    String  v_subj			= box.getString("p_subj");
    String  v_year			= box.getString("p_year");
    String  v_subjseq		= box.getString("p_subjseq");
    String  v_subjnm		= box.getString("p_subjnm");
    
    ArrayList list = (ArrayList)request.getAttribute("CompleteStudentList");
    DataBox dbox2 = (DataBox)request.getAttribute("SubjseqData");
    
    //버튼 제어에 필요한 변수들
    String v_isclosed   = dbox2.getString("isclosed");
    String v_iscpresult = dbox2.getString("iscpresult");
    String v_iscpflag   = dbox2.getString("iscpflag");
    String v_isoutsrc   = dbox2.getString("isoutsourcing");
    String v_isapproval = dbox2.getString("d_isapproval");
    String v_isonoff	= dbox2.getString("isonoff");
    
    int    v_stoldcnt   = dbox2.getInt("d_stoldcnt");
    int    v_studentcnt = dbox2.getInt("d_studentcnt");
    
    int    v_stoldycnt   = dbox2.getInt("d_stoldycnt");
    int    v_stoldncnt   = dbox2.getInt("d_stoldncnt");
    
    int is_complete  = 0;                                // 수료여부
    String v_eduend  = FormatDate.getFormatDate(dbox2.getString("eduend"),"yyyyMMdd");
	String v_date    = FormatDate.getDate("yyyyMMdd");   // 현재날짜

    // 수료여부          
    if (!v_eduend.equals("")) {
        if (Integer.parseInt(v_date) > Integer.parseInt(v_eduend)) is_complete=1;
    }
                        
    String  v_subjseqgr		= dbox2.getString("subjseqgr");
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
      
%>
<html>
<head>
<title>수료처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable)
    {
        toolbar_str = toolbar ? 'yes' : 'no';
        menubar_str = menubar ? 'yes' : 'no';
        statusbar_str = statusbar ? 'yes' : 'no';
        scrollbar_str = scrollbar ? 'yes' : 'no';
        resizable_str = resizable ? 'yes' : 'no';
        window.open(url, name, 'left='+left+',top='+top+',width='+width+',height='+height+',toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str).focus();
    }
    
function whenSelection(p_action) {
  document.form1.target = "_self";
  document.form1.p_process.value = 'subjectSelect';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}


// 점수재계산
function ReRating() {
   //p_subj = document.form1.p_subj.value;
   //p_year = document.form1.p_year.value;
   //p_subjseq = document.form1.p_subjseq.value;
   p_isonoff = document.form1.p_isonoff.value;         
      
   if (confirm("점수재계산 하시겠습니까?")) {
        document.form1.target = "_self";
    
        if(p_isonoff == "ON"){
          document.form1.p_process.value = 'subjectCompleteRerating';        // 사이버
        }else{
          document.form1.p_process.value = 'offSubjectCompleteRerating';     // 집합
        }
    
        document.form1.submit();
  }
}


// 수료처리 
function whenComplete(v_process) {
	if (confirm("수료처리를 하시겠습니까?")) {
  		document.form1.target = "_self";
  		document.form1.p_process.value = v_process;
  		document.form1.submit();
  	}
}

// 목록으로
function listpage() {
    document.form1.target = "_self";
	document.form1.p_process.value = "listPage";
	document.form1.p_action.value = "go";
	document.form1.submit();
}

// 안쓰임
function whenChangeGradu(userid, name, jikwi, compnm, notgraduetc) {
	if (confirm("이수정보를 변경 하시겠습니까?")) {
		document.form1.p_userid.value = userid;
   		document.form1.p_name.value = name;
   		document.form1.p_jikwinm.value = jikwi;
   		document.form1.p_compnm.value = compnm;
   		document.form1.p_notgraduetc.value = notgraduetc;
   
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openChangeSubmit","","100","100","548","350","1");      
        document.form1.target = "openChangeSubmit";
        document.form1.action = "/servlet/controller.complete.FinishServlet";
        document.form1.p_process.value = "changeNotgraduPage";
        document.form1.submit();
        document.form1.target = window.self.name;        
    }
}

// 사원별 이수정보 변경
function whenChangeIsGradu(userid, isgraduated) {
	if (confirm("이수정보를 변경 하시겠습니까?")) {
		document.form1.p_userid.value = userid;
   		document.form1.p_isgraduated.value = isgraduated;
        document.form1.p_process.value = "changeGraduated";
        document.form1.submit();
    }
}

// 사원별 리스트 sorting
function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

function openOffSubjInput() {
  window.self.name = "winOffSubjResultInput";
  window.open("", "openOffSubjResultInput", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=880, height=600, top=0, left=0");
  document.form1.target = "openOffSubjResultInput";
  document.form1.p_process.value = "offSubjectInputPage";

  document.form1.submit();
}

function whenApproval() {
   document.form1.target = "_self";
   document.form1.p_process.value = "requestApprovalPage";

   window.self.name = "winChiefApproval";
   var url = "/servlet/controller.complete.FinishServlet";
   var new_Open = window.open(url,"proposeChief",'scrollbars=yes,width=550,height=200,scrollbars=no,resizable=no');

   document.form1.target = "proposeChief";
   document.form1.submit();
}

function whenCancel(v_process) {
  if (confirm("수료처리를 취소 하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = v_process;
      
      
      document.form1.submit();
    }
}

function whenOffComplete() {
  if (confirm("수료처리를 하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = 'offSubjIsClosed';
      document.form1.submit();
    }
}

function whenOutComplete() {
  if (confirm("수료처리를 하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = 'outSubjIsClosed';
      document.form1.submit();
    }
}

function whenApprovalCancel(){
  if (confirm("결재상신을 취소하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = 'requestApprovalCancel';
      document.form1.submit();
    }
}

function whenApprovalCancelAdmin(){
  if (confirm("이력결재를 취소하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = 'requestApprovalCancelAdmin';
      document.form1.submit();
    }
}

function whenOutSubjReject() {
  if (confirm("결과입력을 재요청 하시겠습니까?")) {
      document.form1.target = "_self";
      document.form1.p_process.value = 'outSubjReject';
      document.form1.submit();
    }
}


//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
      <form name="form1" method="post" action="/servlet/controller.complete.FinishServlet">
		<input type="hidden" name="p_process"   value="">
    	<input type="hidden" name="s_grcode"      value="<%=ss_grcode%>">
  		<input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
  		<input type="hidden" name="s_grseq"       value="<%=ss_grseq%>">
  		<input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
  		<input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
  		<input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
  		<input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
  		<input type="hidden" name="s_subjseq"     value="<%=ss_subjseq%>">
	
	
        <input type="hidden" name="p_action"   value="">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
        <input type="hidden" name="p_year"    value="<%=v_year%>">
        <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
        <input type="hidden" name="p_subjseqgr" value="<%=v_subjseqgr%>">
        <input type="hidden" name="p_isclosed" value="<%=v_isclosed%>">
        <input type="hidden" name="p_isapproval" value="<%=v_isapproval%>">
        <input type="hidden" name="p_isonoff" value="<%=v_isonoff%>">
        <input type="hidden" name="p_userid" value="">
        <input type="hidden" name="p_name" value="">
        <input type="hidden" name="p_jikwinm" value=""> 
        <input type="hidden" name="p_compnm" value="">
        <input type="hidden" name="p_notgraduetc" value="">
        <input type="hidden" name="p_isgraduated" value="">
        
        <input type="hidden" name="p_orderColumn">
		<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
		
	
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title10.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
      <br>

			<!----------------- 교육그룹, 년도, 교육차수, 분류, 코스, 과정, 차수 시작 ----------------->
                        <table cellspacing="1" cellpadding="5" class="table_out">
                          <tr> 
                              <td width="13%" height="25" class="table_title"><b> 교육그룹</b></td>
                              <td width="35%" class="table_02_2"><%=dbox2.getString("grcodenm")%></td>
                              <td width="13%" class="table_title"><b> 년도</b></td>
                              <td class="table_02_2"><%=dbox2.getString("gyear")%>년</td>
                          </tr>
                          <tr> 
                              <td height="25" class="table_title"><b>과정</b></td>
                              <td class="table_02_2"><%=dbox2.getString("subjnm")%></td>
                              <td class="table_title"><b>차수</b></td>
                              <td class="table_02_2"><%=StringManager.cutZero(v_subjseqgr)%>차</td>
                          </tr>

                          <tr> 
                              <td class="table_title" height="25"><b>수료처리완료</b> </td>
                              <td class="table_02_2" colspan='3'>
							      <% if (v_isclosed.equals("Y")) out.print("처리완료"); 
                              	     else                        out.print("미처리"); 
                              	  %>
                              </td>

                          </tr>
                          <tr> 
                              <td class="table_title" height="25"><b> 위탁과정여부</b></td>
                              <td class="table_02_2"><%=v_isoutsrc%></td>
                              <td class="table_title"><b>결과입력여부</b></td>
                              <td class="table_02_2"><%=v_iscpresult%></td>
                          </tr>
                          <tr> 
                              <td class="table_title" height="25"><b> 위탁과정최종확인</b></td>
                              <td class="table_02_2" colspan=3><%=v_iscpflag%></td>
                          </tr>
                          
                        </table>
            <!----------------- 교육그룹, 년도, 교육차수, 분류, 코스, 과정, 차수 끝 ----------------->
            
            
            <br>
            <!-- 사이버 과정 시작 -->
            <% if (v_isonoff.equals("ON")) { %>
            <!----------------- 교육기간, 수료기준 시작 ----------------->
                        
        <table  cellspacing="1" cellpadding="5" class="table_out" >
          <tr> 
            <td class="table_title" width="13%" height="25" ><b> 교육기간</b></td>
            <td class="table_02_2" colspan="5"><%=FormatDate.getFormatDate(dbox2.getString("edustart"),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(dbox2.getString("eduend"),"yyyy/MM/dd")%></td>
          </tr>
          <tr> 
            <td class="table_title" rowspan="6"><b> 이수기준</b></td>
            <td width="11%" height="25" class="table_02_2">점수</td>
            <td width="24%" class="table_02_2" ><%=dbox2.getInt("gradscore")%>점</td>
            <td class="table_title" width="13%" rowspan="7">가중치(%)</td>
            <td width="11%" class="table_02_2" >진도율</td>
            <td width="28%" class="table_02_2" ><%=dbox2.getDouble("wstep")%>%</td>
          </tr>
          <tr> 
            <td height="25" class="table_02_2" >진도율</td>
            <td class="table_02_2" ><%=dbox2.getInt("gradstep")%>점</td>
            <td class="table_02_2" >중간평가</td>
            <td class="table_02_2" ><%=dbox2.getDouble("wmtest")%>%</td>
          </tr>
          <tr> 
            <td height="25" class="table_02_2" >중간평가</td>
            <td class="table_02_2" ><%=dbox2.getInt("gradexam")%>점</td>
            <td class="table_02_2" >최종평가</td>
            <td class="table_02_2" ><%=dbox2.getDouble("wftest")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_2" height="25">최종평가</td>
            <td class="table_02_2" ><%=dbox2.getInt("gradftest")%>점</td>
            <td class="table_02_2" >형성평가</td>
            <td class="table_02_2" ><%=dbox2.getDouble("whtest")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_2" height="25">형성평가</td>
            <td class="table_02_2" ><%=dbox2.getInt("gradhtest")%>점</td>
            <td class="table_02_2" >리포트</td>
            <td class="table_02_2" ><%=dbox2.getDouble("wreport")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_2" height="25">리포트</td>
            <td class="table_02_2" ><%=dbox2.getInt("gradreport")%>점</td>
            <td class="table_02_2" height="25">기타1</td>
            <td class="table_02_2" ><%=dbox2.getDouble("wetc1")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_2" colspan="3">&nbsp;</td>
            <td class="table_02_2"  height="25">기타2</td>
            <td class="table_02_2" ><%=dbox2.getDouble("wetc2")%>%</td>
          </tr>
        </table>
            <!----------------- 교육기간, 수료기준 끝 ----------------->
            <!-- 사이버 과정 종료 -->
            <% } else { %>
            <!-- 집합과정 시작 -->
            <!----------------- 교육기간, 수료기준 시작 ----------------->
                        <table cellspacing="1" cellpadding="5" class="table_out" >
                          <tr> 
                            <td width="13%" height="25" class="table_title"><b>교육기간</b></td>
                            <td colspan="5" class="table_02_2"><%=FormatDate.getFormatDate(dbox2.getString("edustart"),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(dbox2.getString("eduend"),"yyyy/MM/dd")%></td>
                          </tr>
                          <tr> 
                            <td rowspan="4" class="table_title"><b>이수기준</b></td>
                            <td width="11%" height="25" class="table_02_2">점수</td>
                            <td width="24%" class="table_02_2"><%=dbox2.getInt("gradscore")%>점</td>
                            <td rowspan="4" class="table_title"><b>가중치(%)</b></td>
                            <td width="11%" class="table_02_2">출석률</td>
                            <td width="28%" class="table_02_2"><%=dbox2.getDouble("d_wstep")%>%</td>
                          </tr>
                          <tr> 
                            <td height="25" class="table_02_2">출석률</td>
                            <td class="table_02_2"><%=dbox2.getInt("gradstep")%>점</td>
                            <td class="table_02_2">평가</td>
                            <td class="table_02_2"><%=dbox2.getDouble("wftest")%>%</td>
                          </tr>
                          <tr> 
                            <td height="25" class="table_02_2">평가</td>
                            <td class="table_02_2"><%=dbox2.getInt("gradexam")%>점</td>
                            <td class="table_02_2">리포트</td>
                            <td class="table_02_2"><%=dbox2.getDouble("wreport")%>%</td>
                          </tr>
                          <tr> 
                            <td height="25" class="table_02_2">리포트</td>
                            <td class="table_02_2"><%=dbox2.getInt("gradreport")%>점</td>
                            <td class="table_02_2">기타</td>
                            <td class="table_02_2"><%=dbox2.getDouble("wetc1")%>%</td>
                          </tr>
                        </table>
            <!----------------- 교육기간, 수료기준 끝 ----------------->
            <!-- 집합과정 종료 -->
            <% } %>
      
          <br>
            <!----------------- 추가 버튼 시작 ----------------->
	      <table cellpadding="0" cellspacing="0" class="table1">
	        <tr>
	          <%if (v_isclosed.equals("Y")) { %>
	          <td align="left" height="20">
	          	<b>수료: <%=v_stoldycnt%>명 &nbsp;&nbsp;미수료: <%=v_stoldncnt%>명</b>
	          </td>
	          <% } %>
	          <td align="right" height="20">
	          <%
	           //**************** 수료처리 *****************//
	           if (is_complete==1) {  
	           
	           //버튼 설정 시작
	           //1. 외주업체 과정
	           //   수료처리
	           //   결과재요청
	           String v_outProcess = "외주<a href=\"javascript:whenComplete('outSubjIsClosed');\"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;";
	           String v_outReProcess = "<a href=\"javascript:whenOutSubjReject();\"><img src='/images/admin/button/btn_resultretry.gif' border=0></a>&nbsp;"; //"결과재요청 ";
	           
	           
	           //2. 공통
	           //   수료처리 취소
	           //   결재상신
	           
	           //String v_Cancel = "수료처리취소 ";
	           String v_Cancel =  "<a href=\"javascript:whenCancel('subjectCompleteCancel');\"><img src='/images/admin/button/btn_soorucancel.gif' border=0></a>&nbsp;";
	           String v_OffCancel =  "<a href=\"javascript:whenCancel('offSubjectCompleteCancel');\"><img src='/images/admin/button/btn_soorucancel.gif' border=0></a>&nbsp;";
	           
	           
	           //String v_Approval = "결재상신 ";
	           String v_Approval = "";
/*
	           //이수처리 하지 않은 수강생 있을시 결재상신 안됨.
	           if (v_studentcnt==v_stoldcnt) {
	           		v_Approval = "<a href=\"javascript:whenApproval();\"><img src='/images/admin/button/btn_galjeshangshin.gif' border=0></a>&nbsp;";
	           } else {
	           		v_Approval = "<a href=\"javascript:alert('이수처리하지 않은 학습자가 있습니다. 확인해 주세요.');\"><img src='/images/admin/button/btn_galjeshangshin.gif' border=0></a>&nbsp;";
	           }
*/
	           
	           //3. 사이버과정
	           //   수료처리
	           String v_Process = "<a href=\"javascript:whenComplete('subjectComplete');\"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;";
	           
	           
	           //4. 집합과정
	           //   수료처리(외주 수료처리와 동일하며 tz_student table의 isclosed 상태만 변경해줌)
	           //   점수입력
	           //String v_inputScore = "점수입력 ";
	           String v_offProcess = "";
	           
	           //if (v_stoldcnt==0) {
	           //		v_offProcess = "<a href=\"javascript:alert('먼저 결과입력처리를 하세요.');\"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;";
	           //} else {
	           		v_offProcess = "<a href=\"javascript:whenComplete('offSubjectCompleteNew');\"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;";
	           //}
	           String v_inputScore = "<a href=\"javascript:openOffSubjInput();\"><img src='/images/admin/button/btn_reslultinsertok.gif' border=0></a>&nbsp;";	           	           
	           //버튼 결재 종료          
	           
	            //버튼제어 시작
	          	//1. 외주업체 과정인 경우
	          	//1.1 수료처리 결재 완료가 아니고 상신중이 아닐때
	          	//1.1.1 업체가 결과를 입력했고 
	          	//1.1.1.1 수료처리를 하지 않은 경우
	          	//		  수료처리, 결과재요청
	          	
	          	//1.1.1.2 수료처리를 한 경우
	          	//		  수료처리 취소, 결재상신
	          	
	          	
	          	//2. 외주업체 과정이 아닌경우
	          	//2.1 수료처리 결재 완료가 아니고 상신중이 아닐떄
	          	//2.1.2 수료처리를 하지 않은 경우
	          	//		수료처리
	          	
	          	//2.1.2.1 집합과정인 경우
	          	//		  점수입력
	          	
	          	//2.1.3 수료처리를 한 경우
	          	//		수료처리 취소, 결재 상신
	          	
	          	
	          	//3. 결재상신중일때
	          	//	 결재상신 취소
	          	
	          	//4. 결재완료이고 총괄관리자면
	          	//	 이력결재반려
	          	
	          	//버튼제어 종료
	          		          	
	          	//out.print(v_isoutsrc);
	          	//out.print(v_isclosed);
	          	//out.print(v_isonoff);
	          	
	          	//1. 외주업체 과정인 경우
	          	if (v_isoutsrc.equals("Y")) {
	          			//1.1.1 업체가 결과를 입력했고 
	          			if (v_iscpresult.equals("Y")) {
	          				//1.1.1.1 수료처리를 하지 않은 경우
	          				if (v_isclosed.equals("N")) {
	          				  	//		  수료처리, 결과재요청
	          				  	out.print(v_outProcess);
	          				  	out.print(v_outReProcess);
	          				} else {
	          		          	//1.1.1.2 수료처리를 한 경우
	          					//		  수료처리 취소, 결재상신
	          					out.print(v_Cancel);
	          					//out.print(v_Approval);
	          				}
	          			}
	          	} else {
	          	//2. 외주업체 과정이 아닌경우
	          			//2.1.2 수료처리를 하지 않은 경우
	          			if (v_isclosed.equals("N")) {
	          				
	          				//2.1.2.1 집합과정인 경우
	          				//		  점수입력
	          				if (v_isonoff.equals("OFF")) {
	          					out.print(v_inputScore);
	          				}
	          				
	          				//		수료처리
	          				if (v_isonoff.equals("ON")) {
	          					out.print(v_Process);
	          				} else {
	          					out.print(v_offProcess);
	          				}

	          			} else {
	          				//2.1.3 수료처리를 한 경우
	          				//		  수료처리 취소, 결재상신
	          				if (v_isonoff.equals("ON")) {
	          					out.print(v_Cancel);
	          				} else {
	          					out.print(v_OffCancel);
	          				}
	          				out.print(v_Approval);
	          			}
	          	}

	           }else{ 
	           	   //**************** 점수재계산 *****************//          
                  out.print("<a href=\"javascript:ReRating()\">");
                  out.print("<img src='/images/admin/button/btn_rerating.gif' border=0 alt='점수재계산'>");
                  out.print("</a>&nbsp;");
	           }	          	
	           %>
	          </td>
	        </tr>
	        <tr>
	          <td height="3"></td>
	        </tr>
	      </table>
	      <!----------------- 추가 버튼 끝 ----------------->
          
         
      <br>
      <!----------------- 수료처리 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> <!--회사 과정 ID 성명 부서 직위 차수 구분 -->
            <td rowspan="2" width="27" class="table_title">NO</td>
            <td rowspan="2" class="table_title" width="135"><a href="javascript:whenOrder('compnm')" class="e">회사</a></td>
            <td width="50" rowspan="2" class="table_title"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
            <td rowspan="2" class="table_title" width="50"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
            <td rowspan="2" class="table_title" width="120"><a href="javascript:whenOrder('compnm')" class="e">부서</a></td>
            <td width="60" rowspan="2" class="table_title"><a href="javascript:whenOrder('jikwinm')" class="e">직위</a></td>
            <td colspan="<% if (v_isonoff.equals("OFF")) out.print(5); else out.print(8);%>" class="table_title">평 가 항 목</td>
            <td width="64" rowspan="2" class="table_title"><a href="javascript:whenOrder('score')" class="e">총점</a></td>            
            <td width="64" rowspan="2" class="table_title"><a href="javascript:whenOrder('a.samtotal')" class="e">삼진<br>아웃</a></td>
            <td width="64" rowspan="2" class="table_title"><a href="javascript:whenOrder('isgraduated')" class="e">수료</a></td>
            <td width="65" rowspan="2" class="table_title">미수료<br>
              사유변경</td>            
          </tr>
          <tr> 
            <td width="47" class="table_title">구분</td>
            <% if (v_isonoff.equals("OFF")) { %>
            <td width="47" class="table_title"><a href="javascript:whenOrder('avtstep')" class="e">출석률</a></td>
            <td width="42" class="table_title"><a href="javascript:whenOrder('avftest')" class="e">평가</a></td>
            <td width="42" class="table_title"><a href="javascript:whenOrder('avreport')" class="e">리포트</a></td>
            <td width="38" class="table_title"><a href="javascript:whenOrder('avetc1')" class="e">기타</a></td>
            <% } else  { %>
            <td width="47" class="table_title"><a href="javascript:whenOrder('avtstep')" class="e">진도율</a></td>
            <td width="42" class="table_title"><a href="javascript:whenOrder('avmtest')" class="e">중간평가</a></td>
            <td width="42" class="table_title"><a href="javascript:whenOrder('avhtest')" class="e">최종평가</a></td>
            <td width="38" class="table_title"><a href="javascript:whenOrder('avhtest')" class="e">형성평가</a></td>
            <td width="35" class="table_title"><a href="javascript:whenOrder('avreport')" class="e">리포트</a></td>
            <td width="25" class="table_title"><a href="javascript:whenOrder('avetc1')" class="e">기타1</a></td>
            <td width="30" class="table_title"><a href="javascript:whenOrder('avetc2')" class="e">기타2</a></td>
            <% } %>
          </tr>
          <% if (list.size()==0) { %>
          <!--tr> 
          <td class="table_02_1" colspan="<% if (v_isonoff.equals("OFF")) out.print(14); else out.print(18);%>"> 수강생이 없습니다.</td>
        </tr-->
          <%  } %>
          <% 
		
			for (int i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i); 
				
				String v_isgradated = dbox.getString("d_isgraduated");	
		  %>
          <tr> 
            <td rowspan="2" class="table_01"><%=(i+1)%>
            
               <a href="javascript:whenChangeGradu('<%=dbox.getString("d_userid")%>','<%=dbox.getString("d_name")%>','<%=dbox.getString("d_jikwinm")%>','<%=dbox.getString("d_compnm")%>','<%=dbox.getString("d_notgraduetc")%>')"><img src="/images/admin/button/b_sayouchange.gif" border="0">aa</a>
            </td>
            <td rowspan="2" class="table_02_1"><%=dbox.getString("d_companynm")%></td>
            <td rowspan="2" class="table_02_1"><%=dbox.getString("d_userid")%></td>
            <td rowspan="2" class="table_02_1"><%=dbox.getString("d_name")%></td>
            <td rowspan="2" class="table_02_1"><%=dbox.getString("d_compnm")%></td>
            <td rowspan="2" class="table_02_1"><%=dbox.getString("d_jikwinm")%></td>
            <td class="table_02_1">취득</td>
            <% if (v_isonoff.equals("OFF")) { %>
              <td class="table_02_1"><%=dbox.getDouble("d_tstep")%></td>
			  <td class="table_02_1"><%=dbox.getDouble("d_ftest")%></td>
			  <td class="table_02_1"><%=dbox.getDouble("d_report")%></td>
			  <td class="table_02_1"><%=dbox.getDouble("d_etc1")%></td>

            <% } else { %>
            <td class="table_02_1"><%=dbox.getDouble("d_tstep")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_mtest")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_ftest")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_htest")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_report")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_etc1")%></td>
            <td class="table_02_1"><%=dbox.getDouble("d_etc2")%></td>
            <% } %>
            <td rowspan="2" class="table_02_1">
            <% if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) { %>
            	<%=dbox.getDouble("d_score")%>
            <%   } else { out.print("-");} %>
            </td>
            <td  rowspan="2" class="table_02_1"><%=dbox.getDouble("d_samtotal")%></td>            
            <td rowspan="2" class="table_02_1" >
            
	            <% if (v_isclosed.equals("Y") || v_isoutsrc.equals("Y")) {
	            		if (v_isgradated.equals("Y"))  {
		            			if (v_isoutsrc.equals("N") ) {  %>
	            				<a href="javascript:whenChangeIsGradu('<%=dbox.getString("d_userid")%>','N')">이수</a>
	            			<%	} else { 
	            					out.print("이수");
	            				}
	               		} else { 
	               			 if (v_isoutsrc.equals("N")) {   %>
	               				<a href="javascript:whenChangeIsGradu('<%=dbox.getString("d_userid")%>','Y')">미이수</a>
	               		<%	} else { 
	               				out.print("미이수");
	               			}
	               		}	 %>
	            <%   } else {out.print("미처리");} %>
	            
	            
	           
            </td>
            <td  rowspan="2" class="table_03_1"> <a href="javascript:whenChangeGradu('<%=dbox.getString("d_userid")%>','<%=dbox.getString("d_name")%>','<%=dbox.getString("d_jikwinm")%>','<%=dbox.getString("d_compnm")%>','<%=dbox.getString("d_notgraduetc")%>')"><img src="/images/admin/button/b_sayouchange.gif" border="0"></a></td>   

                        
          </tr>
          <tr> 
            <td bgcolor="#FFFFCC">가중치</td>
            <% if (v_isonoff.equals("OFF")) { %>
              <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avtstep")%></td>
			  <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avftest")%></td>
			  <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avreport")%></td>
			  <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avetc1")%></td>
            <% } else { %>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avtstep")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avmtest")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avftest")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avhtest")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avreport")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avetc1")%></td>
            <td bgcolor="#FFFFCC" align=center><%=dbox.getDouble("d_avetc2")%></td>
            <% } %>
          </tr>
          <%  } 
          
 %>
        </table>
      <!----------------- 수료처리 끝 ----------------->
		<br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href="javascript:listpage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->

        <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
