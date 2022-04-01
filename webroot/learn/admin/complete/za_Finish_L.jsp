<%
//**********************************************************
//  1. 제      목: 수료처리 리스트
//  2. 프로그램명: za_Finish_L.java
//  3. 개      요: 수료처리 리스트 및 처리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2004. 12. 20
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	String  v_action       = box.getString("p_action");

	ArrayList list = null;
	
	if (v_action.equals("go")) {
    	list = (ArrayList)request.getAttribute("CompleteList");
    }
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<title>수료처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--

// 조회(GO버튼) 실행
function whenSelection(p_action) {
  if (p_action=="go"){
     if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
         alert("교육그룹을 선택하세요.");
         return;
      }
      top.ftop.setPam();
  }
  document.form1.target = "_self";
  document.form1.p_process.value = 'listPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}

// 수료처리 신청
function whenComplete(p_subj, p_year, p_subjseq, p_isonoff, p_student) {
  if (p_student==0) {
	  alert("수강생이 없습니다.");
	  return;
  }
  if (confirm("수료처리를 하시겠습니까?")) {
    document.form1.target = "_self";

    if(p_isonoff == "ON"){
      document.form1.p_process.value = 'subjectComplete';
    }else{
      document.form1.p_process.value = 'offSubjectCompleteNew';
    }

    document.form1.p_subj.value = p_subj;
    document.form1.p_year.value = p_year;
    document.form1.p_subjseq.value = p_subjseq;
    document.form1.submit();
  }
}

// 수료처리 취소 신청
function whenCancel(p_subj, p_year, p_subjseq, p_process, p_student) {
    if (p_student==0) {
	    alert("수강생이 없습니다.");
	    return;
    }
    if (confirm("수료처리를 취소 하시겠습니까?")) {
		document.form1.target = "_self";                 
  	    document.form1.p_process.value = p_process;                   

        document.form1.p_subj.value = p_subj;
        document.form1.p_year.value = p_year;
        document.form1.p_subjseq.value = p_subjseq;
        document.form1.submit();
     }
}


function whenScoreCompute(p_subj, p_year, p_subjseq) {
	if (confirm("수료처리를 하시겠습니까?")) {
	  document.form1.target = "_self";
	  document.form1.p_process.value = 'scoreCompute';
	  document.form1.p_subj.value = p_subj;
	  document.form1.p_year.value = p_year;
	  document.form1.p_subjseq.value = p_subjseq;
	  //document.form1.submit();
    }
}

function whenCourseCompute(p_course, p_cyear, p_courseseq) {
  document.form1.target = "_self";
  document.form1.p_process.value   = 'courseScoreCompute';
  document.form1.p_course.value    = p_course;
  document.form1.p_cyear.value     = p_cyear;
  document.form1.p_courseseq.value = p_courseseq;
  document.form1.submit();
}

// 수료처리 상세화면으로 이동
function selectSubj(subj, year, subjseq, isclosed, subjnm, isonoff, isapproval, subjseqgr) {
  document.form1.target = "_self";
  document.form1.p_process.value = "subjectSelect";
  document.form1.p_subj.value    = subj;
  document.form1.p_year.value    = year;
  document.form1.p_subjseq.value = subjseq;
  document.form1.p_isclosed.value = isclosed;
  document.form1.p_subjnm.value = subjnm;
  document.form1.p_isonoff.value = isonoff;
  document.form1.p_isapproval.value = isapproval;
  document.form1.p_subjseqgr.value = subjseqgr;
  document.form1.submit();
}

function whenApproval(subj,year,subjseq) {
   document.form1.target = "_self";
   document.form1.p_subj.value = subj;
   document.form1.p_year.value = year;
   document.form1.p_subjseq.value = subjseq;
   document.form1.p_process.value = "requestApprovalPage";

   window.self.name = "winChiefApproval";
   var url = "/servlet/controller.complete.FinishServlet";
   var new_Open = window.open(url,"proposeChief",'scrollbars=yes,width=550,height=200,scrollbars=no,resizable=no');

   document.form1.target = "proposeChief";
   document.form1.submit();
}

// sorting 조회
function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.complete.FinishServlet">
	<input type="hidden" name="p_process"    value="">
	<input type="hidden" name="p_action"   value="">

    <input type="hidden" name="p_subj"    value="">
    <input type="hidden" name="p_year"    value="">
    <input type="hidden" name="p_subjseq" value="">
    <input type="hidden" name="p_subjseqgr" value="">
    <input type="hidden" name="p_isapproval" value="">
    <input type="hidden" name="p_subjnm"    value="">
    <input type="hidden" name="p_isclosed" value="">
    <input type="hidden" name="p_isonoff" value="">
    <input type="hidden" name="p_returnurl" value="/servlet/controller.complete.FinishServlet">
    <input type="hidden" name="p_rprocess" value="listPage">

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
        <!-- 교육그룹, 년도 시작 -->
        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
                      
						<!------------------- 조건검색 시작 ------------------------->
				  		 <table cellspacing="0" cellpadding="0" width="99%">
				  		 	<tr>
				  		 		<td>
				  		 			<table border="0" cellspacing="0" cellpadding="0" width="99%">
				  		 				<tr>
				  		 					<td width="25%"><font color="red">★</font> 
											<!-- 교육그룹 시작 -->
				  								<%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
											<!-- 교육그룹 종료 -->
				  							</td>
				  							<td width="75%">
											<!-- 교육년도, 분류 시작 -->
				  								<%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
												<%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
				  								<!--input type="hidden" name="s_grseq"  value="0001"-->
				  								&nbsp;<%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
				  								&nbsp;<%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				  								&nbsp;<%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
											<!-- 교육년도, 분류 끝 -->
				  							</td>
				  						</tr>
				  					</table>
								</td>
							</tr>
							<tr>
								<td height="3"></td>
							</tr>
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="99%">
										<tr>
											<td width="65%">&nbsp;&nbsp;&nbsp;-
											<!-- 과정,차수,회사 시작 -->
				  								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				  								&nbsp;<%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
											<!-- 과정,차수,회사 종료 -->
				  							</td>
				  							<td  width="10%" align="right">	
				  				    			<%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') -->
				  				    		</td>
				  				 		</tr>
				  				 	</table>
				  				</td>
				  			</tr>
				  		</table>
				  		<!-------------------- 조건검색 끝 ---------------------------->
					  </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-- 교육그룹, 년도 끝 -->

      <br>
      <br>
      <!----------------- 수료처리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td rowspan="2" width="5%" class="table_title">NO</td>
          <!--td rowspan="2" class="table_title" width="50"><a href="javascript:whenOrder('office')" class="e">회사명</a></td-->
          <td rowspan="2" class="table_title" width="40"><a href="javascript:whenOrder('isonoff')" class="e">구분</a></td>
		  <td rowspan="2" class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정</a></td>
          <td rowspan="2" class="table_title"><a href="javascript:whenOrder('subjseqgr')" class="e">차수</a></td>
          <td rowspan="2" class="table_title" width="90"><a href="javascript:whenOrder('edustart')" class="e">교육기간</a></td>
          <td colspan="3" class="table_title">인원</a></td>
          <td rowspan="2" class="table_title"><a href="javascript:whenOrder('isclosed')" class="e">수료처리</a></td>
          <!--<td rowspan="2" class="table_title"><a href="javascript:whenOrder('approvalstatus')" class="e">이력결재</a></td>-->
          <td rowspan="2" class="table_title"><a href="javascript:whenOrder('isoutsourcing')" class="e">위탁교육</a></td>
          <td rowspan="2" class="table_title"><a href="javascript:whenOrder('iscpresult')" class="e">외주업체<br>결과등록</a></td>
          <td rowspan="2" class="table_title"><a href="javascript:whenOrder('iscpflag')" class="e">외주업체<br>결과승인</a></td>
        </tr>
        <tr> 
          <td class="table_title"><a href="javascript:whenOrder('studentcnt')" class="e">교육</td>
          <td class="table_title"><a href="javascript:whenOrder('stoldycnt')" class="e">수료</td>
          <td class="table_title"><a href="javascript:whenOrder('stoldncnt')" class="e">미수료</td>
        </tr>
<%		if (v_action.equals("go")) { 
		
			if (list.size()==0) { %>
			<tr><td colspan=13 class="table_02_1">검색된 과정이 없습니다.</td></tr>
<%			} else { 
			
			
			for (int i=0; i<list.size(); i++) {
            	DataBox dbox = (DataBox)list.get(i); 
            	
            	String v_subjnm   = dbox.getString("subjnm");
            	String v_isclosed = dbox.getString("isclosed");
            	String v_approval = dbox.getString("d_approvalstatus");
            	int    v_student  = dbox.getInt("d_studentcnt");
            	
            	String v_isonoff = dbox.getString("d_isonoff");
            	String v_subj    = dbox.getString("d_subj");
            	String v_year    = dbox.getString("d_year");
            	String v_subjseq = dbox.getString("d_subjseq");
            		
            	%>
        <tr> 
          <td class="table_01" ><%=(i+1)%></td>
          <!--td class="table_02_1" >&nbsp;</td-->          
          <td class="table_02_1"><%=dbox.getString("isonoffnm")%></td>
          <td class="table_02_2">
          <% if (v_student==0) { %>
          	<a href="javascript:alert('수강생이 없습니다.');" class='b'><%=v_subjnm%></a>
          <% } else { %>
          	<a href="javascript:selectSubj('<%=dbox.getString("subj")%>','<%=dbox.getString("year")%>','<%=dbox.getString("subjseq")%>','<%=dbox.getString("isclosed")%>','<%=v_subjnm%>','<%=dbox.getString("isonoff")%>','<%=dbox.getString("approvalstatus")%>','<%=dbox.getString("subjseqgr")%>');" class='b'> <%=dbox.getString("subjnm")%></a>
          <% } %>
          </td>
          <td class="table_02_1"><%=StringManager.cutZero(dbox.getString("subjseqgr"))%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("edustart"),"yyyy/MM/dd")%>~<br> <%=FormatDate.getFormatDate(dbox.getString("eduend"),"yyyy/MM/dd")%></td>
          <td class="table_02_1"><%=v_student%></td>		<!--교육-->
          <td class="table_02_1"><%=dbox.getInt("d_stoldycnt")%></td>			<!--수료-->
          <td class="table_02_1"><%=dbox.getInt("d_stoldncnt")%></td>			<!--미수료-->
          <td class="table_02_1"><!-- 수료처리 -->
              <% if (v_isclosed.equals("Y")){ %><!--수료-->
			  <% if(v_isonoff.equals("ON")){%>
                <a href="javascript:whenCancel('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','subjectCompleteCancel', <%=v_student%>);"><img src='/images/admin/button/btn_soorucancel.gif' border=0></a>&nbsp;
                <%}else{%>
                <a href="javascript:whenCancel('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','offSubjectCompleteCancel', <%=v_student%>);"><img src='/images/admin/button/btn_soorucancel.gif' border=0></a>&nbsp;
                <%}%>
              <% }else{ %><!--미수료-->
                <%if(v_isonoff.equals("ON")){%>
                  <a href="javascript:whenComplete('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>', '<%=v_isonoff%>', <%=v_student%>)"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;
                <%}else{%>
                  <a href="javascript:whenComplete('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>', '<%=v_isonoff%>', <%=v_student%>)"><img src='/images/admin/button/btn_sooruok.gif' border=0></a>&nbsp;
                <%}
                }%>
          </td> 
      		  <!--<td class="table_02_1"><%if (v_approval.equals("Y")) out.print("결재완료"); else if (v_approval.equals("B")) out.print("상신중"); else out.print("");%></td>--> <!-- 결재 상태 -->
      		  <td class="table_02_1"><%=dbox.getString("isoutsourcing")%></td> <!-- 위탁교육여부 -->
      		  <td class="table_02_1"><%=dbox.getString("iscpresult")%></td> <!-- 외주업체결과등록 -->
      		  <td class="table_02_1"><%=dbox.getString("iscpflag")%></td> <!-- 외주업체결과승인 -->
          </td>
        </tr>
<% 			}
		}
	} %>
      </table>
      <!----------------- 수료처리 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
