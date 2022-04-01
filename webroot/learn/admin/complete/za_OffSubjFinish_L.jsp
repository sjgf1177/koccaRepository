<%
/**
 * file name : za_OffSubjFinish_L.jsp
 * date      : 2004/01/29
 * programmer:
 * function  : 집합과정 개인별 수료처리
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	String  ss_action    = box.getString("s_action");
	
    String  v_subj    = box.getString("p_subj");
    String  v_year    = box.getString("p_year");
    String  v_subjseq = box.getString("p_subjseq");
    
    String  v_subjnm		= box.getString("p_subjnm");
	
	

    DataBox dbox2 = (DataBox)request.getAttribute("SubjseqData");
    ArrayList list = (ArrayList)request.getAttribute("OffCompleteList");
    StoldData data = null;
    
    //버튼 제어에 필요한 변수들
    String v_isclosed   = dbox2.getString("isclosed");
    String v_iscpresult = dbox2.getString("iscpresult");
    String v_iscpflag   = dbox2.getString("iscpflag");
    String v_isoutsrc   = dbox2.getString("isoutsourcing");
    String v_isapproval = dbox2.getString("d_isapproval");
    String v_isonoff	= dbox2.getString("isonoff");
    String  v_subjseqgr		= dbox2.getString("subjseqgr");
    
    int    v_stoldcnt   = dbox2.getInt("d_stoldcnt");
    
    //out.println(" v_isclosed = " + v_isclosed );
    //out.println(" v_iscpresult = " + v_iscpresult);
    //out.println(" v_iscpflag = " + v_iscpflag);
    //out.println(" v_isoutsrc = " + v_isoutsrc);
    //out.println(" v_isapproval = " + v_isapproval);
    //out.println("auth = " + box.getSession("gadmin"));
    //out.println("v_isonoff="+v_isonoff);
    //out.println("v_stoldcnt="+v_stoldcnt);
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>

<html>
<head>
<title>집합과정 개인별 수료처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 저장
function whenComplete() {
  if (chkSelected() < 1) {
    alert('학습자를 선택하세요');
    return;
  }
  makeValue();
  document.form3.target="_self";
  document.form3.action="/servlet/controller.complete.FinishServlet";
  document.form3.p_process.value = 'offSubjectCompleteNew';
  document.form3.submit();
}

function whenCancel() {
  if (confirm("이수처리를 취소 하시겠습니까?")) {
  	document.form3.target="_self";
  	document.form3.action="/servlet/controller.complete.FinishServlet";
    document.form3.p_process.value = 'offSubjectCompleteCancel';
    document.form3.submit();
  }
}

// 엑셀출력
	function whenExcel() {
		alert("EXCEL 결과 Upload시 EXCEL DATA를 다른 이름으로 저장 후 \r확장자를 XLS로 저장하여 업로드 하시기 바랍니다.\r\r확인 버튼을 클릭하시면 다운로드를 시작합니다. ");
        window.self.name = "OffStudentList";
        open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form3.target = "openExcel";
        document.form3.action="/servlet/controller.complete.FinishServlet";
        document.form3.p_process.value = "OffStudentExcel";
        document.form3.submit();
    }
    
function makeValue() {
  var v_value = '';
  for (i=document.form1.length-1; i>=0; i--) {
    if (document.form1.elements[i].name == 'p_checks') {
      if (document.form1.elements[i].checked) {
        if (v_value=='') {
          v_value=document.form1.elements[i].value;
        } else {
          v_value+=':'+document.form1.elements[i].value;
        }
      } else {
        i-=4;
      }
    } else if (document.form1.elements[i].name == 'p_isclosed') {  
    
    } else {
      v_value+=','+document.form1.elements[i].value;
    }
    
  }
  v_value+=':';

  document.form3.p_value.value = v_value;
  //if (document.form1.p_isclosed.checked) {
  //	document.form3.p_isclosed.value = 'Y';
  //} else {
  //	document.form3.p_isclosed.value = 'N';
  //}
}

    function whenAllSelect() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = true;
          }
        } else {
          document.form1.p_checks.checked = true;
        }
      } 
    }
    
    function whenAllSelectCancel() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = false;
          }
        } else {
          document.form1.p_checks.checked = false;
        }
      }
    }

function chkSelected() {
  var selectedcnt = 0;
  if(document.form1.all['p_checks'] == '[object]') {
    if (document.form1.p_checks.length > 0) {
      for (i=0; i<document.form1.p_checks.length; i++) {
        if (document.form1.p_checks[i].checked == true) {
          selectedcnt++;
        }
      }
    } else {
      if (document.form1.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }
  return selectedcnt;
}

function whenChangeGradu(userid, name, jikwi, compnm, notgraduetc) {
		document.form3.p_userid.value = userid;
   		document.form3.p_name.value = name;
   		document.form3.p_jikwinm.value = jikwi;
   		document.form3.p_compnm.value = compnm;
   		document.form3.p_notgraduetc.value = notgraduetc;
   
        window.self.name = "projectDetail";     //opener 객체에 name 을 지정한다
        open_window("openChangeSubmit","","100","100","548","350","1");      
        document.form3.target = "openChangeSubmit";
        document.form3.action = "/servlet/controller.complete.FinishServlet";
        document.form3.p_process.value = "changeNotgraduPage";
        document.form3.submit();
        document.form3.target = window.self.name;        
    }
	
function whenOrder(column) {
	if (document.form3.p_orderType.value == " asc") {
		document.form3.p_orderType.value = " desc";
	} else {
		document.form3.p_orderType.value = " asc";
	}

    document.form3.p_orderColumn.value = column;

    whenSelection("go");
}


function whenSelection(p_action) {
  document.form3.target = "_self";
  document.form3.action="/servlet/controller.complete.FinishServlet";
  document.form3.p_process.value = 'offSubjectInputPage';
  document.form3.p_action.value = p_action;
  document.form3.submit();
}

function whenClose() {
	opener.whenSelection("go");
	self.close();
}

function whenChangeIsGradu(userid, isgraduated) {
	if (confirm("이수정보를 변경 하시겠습니까?")) {
		document.form3.action="/servlet/controller.complete.FinishServlet";
		document.form3.p_userid.value = userid;
   		document.form3.p_isgraduated.value = isgraduated;
        document.form3.p_process.value = "changeGraduated";
        document.form3.submit();
    }
}

function chkeckall(){
    if(document.form5.p_chkeckall.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
}

// 집합 일괄 업로드
function openOffSubjInputExcel() {
  window.self.name = "winOffSubjResultInputExcel";
  window.open("", "openOffSubjResultInputExcel", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=1015, height=600, top=0, left=0");
  document.form3.target = "openOffSubjResultInputExcel";
  document.form3.action = "/servlet/controller.complete.FinishInputServlet";
  document.form3.p_process.value = "listPage";

  document.form3.submit();
}

// 새로고침
function winRefresh() {
  document.form3.target="_self";
  document.form3.action="/servlet/controller.complete.FinishServlet";
  document.form3.p_process.value = 'offSubjectInputPage';
  document.form3.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="860" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" bgcolor="#FFFFFF">
            <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title10.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	              <!----------------- title 끝 ----------------->
            <br>
			        <!-------------소제목 시작-------------------------->
 <table width="95%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>집합과정 개인별 수료처리</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
           
           
           			<!----------------- 교육주관, 년도, 교육차수, 분류, 코스, 과정, 차수 시작 ----------------->
                        <table cellspacing="1" cellpadding="5" class="table_out">
                          <tr> 
                              <td width="13%" height="25" class="table_title"><b> 교육주관</b></td>
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
                              <td class="table_title" height="25"><b>수료처리완료</b</td>
                              <td class="table_02_2" colspan="3"><% if (v_isclosed.equals("Y")) out.print("처리완료"); 
                              	     else out.print("미처리"); 
                              	  %>
                              </td>

<!--
                              <td class="table_title" height="25"><b>수료처리완료</b</td>
                              <td class="table_02_2"><% if (v_isclosed.equals("Y")) out.print("처리완료"); 
                              	     else out.print("미처리"); 
                              	  %>
                              </td>
                              <td class="table_title"><b> 이력결재상태</b</td>
                              <td class="table_02_2"><% if (v_isapproval.equals("Y")) 
                            			out.print("결재완료");
                            	   else if (v_isapproval.equals("Y")) {
                            	   		out.print("상신중");
                            	   } else {
                            	   		out.print("미상신");
                            	   }
                            	%></td>
-->
                          </tr>

                        </table>
            <!----------------- 교육그룹, 년도, 교육차수, 분류, 코스, 과정, 차수 끝 ----------------->

            <br>
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
            <br>
            <!----------------- 이수처리실행, 취소 버튼 시작 ----------------->
            <table width="95%" border="0" cellpadding="0" cellspacing="0" border=1>
              	  
  
              <tr>
                <td align="left" width="535"></td>
				    <td width="143" align="right" >&nbsp;</td>
				<td width=7></td>  
				    <td width="32" align="right" >&nbsp;</td>
				<td width=7></td>
                <td width="32" align="right"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
				<td width=7></td>
                <td width="150" align="right"><a href="javascript:openOffSubjInputExcel()">결과EXCEL업로드</td>
				<td width=7></td>
                <td width="32" align="right"><a href="javascript:whenComplete()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
				<td width=8></td>
                <td align="right" width="32"><a href="javascript:whenClose();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
                <td width=7></td>
                <td align="right" width="80"><a href="javascript:winRefresh();">새로고침</a></td>
              </tr>
              <tr>
                <td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
				<td height="3"></td>
              </tr>
            </table>
            <!----------------- 이수처리실행, 취소 버튼 시작 ----------------->
            <!----------------- 집합과정 개인별 수료처리 시작 ----------------->
            <table cellspacing="1" cellpadding="5" class="open_table_out">
              <tr> 
                <td colspan="14" class="table_top_line"></td>
              </tr>
              <tr> 
                <td width="26" class="table_title">NO</td>
                <td width="35" class="table_title"><a href="javascript:whenOrder('userid')" class="e">사번</a></td>
                <td class="table_title" width="41"><a href="javascript:whenOrder('jikwinm')" class="e">직위</a></td>
                <td class="table_title" width="47"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
                <td width="35" class="table_title"><a href="javascript:whenOrder('compnm')" class="e">소속</a></td>
                <td width="45" class="table_title"><a href="javascript:whenOrder('tstep')" class="e">출석률</a></td>
                <td width="36" class="table_title"><a href="javascript:whenOrder('ftest')" class="e">평가</a></td>
                <td width="51" class="table_title"><a href="javascript:whenOrder('report')" class="e">리포트</a></td>
                <td width="36" class="table_title"><a href="javascript:whenOrder('etc1')" class="e">기타</a></td>
                <td class="table_title" width="42"><a href="javascript:whenOrder('score')" class="e">취득<br>
                  점수</a></td>
                <td class="table_title" width="30"><a href="javascript:whenOrder('isgraduated')" class="e">이수<br>
                  여부</a></td>
                <td class="table_title" width="54"><a href="javascript:whenOrder('notgraduetcdesc')" class="e">미이수<br>
                  사유</a></td>
                <td class="table_title" width="42">미이수<br>
                  사유변경</td>
                <td width="39" class="table_title">
                <form name="form5">
                <input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall()"> 
                </form>
                </td>
              </tr>
          <form name="form1" method="post" action="/servlet/controller.complete.FinishServlet">
          
		
          	<input type="hidden" name="p_isclosed" value="<%=v_isclosed%>"> 
            <%  for (int i=0; i<list.size(); i++) {
        		data  = (StoldData)list.get(i); 
        		String v_isgradated = data.getIsgraduated();
        	%>
                    
              <tr> 
                <td class="table_01"><%=(i+1)%></td>
                <td class="table_02_1"><%=data.getUserid()%></td>
                <td class="table_02_1"><%=data.getJikwinm()%></td>
                <td class="table_02_1"><%=data.getName()%></td>
                <td class="table_02_1"><%=data.getCompnm()%></td>
                
                <%//유계불참이면 입력할 수 없음 - TZ_CODE GUBUN='0028' 10(유계불참),11(유계불참(해외출장)) %>
                <%// if (data.getNotgraduetc().equals("10") || data.getNotgraduetc().equals("11")) { %>
                <% if (!data.getNotgraduetc().equals("")) { %>
                <td class="table_02_1"><%=data.getTstep()%></td>
                <td class="table_02_1"><%=data.getFtest()%></td>
                <td class="table_02_1"><%=data.getReport()%></td>
                <td class="table_02_1"><%=data.getEtc1()%></td>
                <% } else { %>
                
                <td class="table_02_1"><input name="p_tsteps"  type="text" class="input" size="4" maxlength="4" value=<%=data.getTstep()%>></td>
                <td class="table_02_1"><input name="p_ftests"  type="text" class="input" size="4" maxlength="4" value=<%=data.getFtest()%>></td>
                <td class="table_02_1"><input name="p_reports" type="text" class="input" size="4" maxlength="4" value=<%=data.getReport()%>></td>
                <td class="table_02_1"><input name="p_etc1s"   type="text" class="input" size="4" maxlength="4" value=<%=data.getEtc1()%>></td>
                <% } %>
                <td class="table_02_1"><%=data.getScore()%></td>
                <td class="table_02_1">
                <% if (v_stoldcnt!=0) {
	            		if (v_isgradated.equals("Y"))  {
		            			if (!v_isapproval.equals("Y") && !v_isapproval.equals("B")) {  %>
	            				<a href="javascript:whenChangeIsGradu('<%=data.getUserid()%>','N')">이수</a>
	            			<%	} else { 
	            					out.print("이수");
	            				}
	               		} else if (v_isgradated.equals("N")) {
	               			 if (!v_isapproval.equals("Y") && !v_isapproval.equals("B")) {   %>
	               				<a href="javascript:whenChangeIsGradu('<%=data.getUserid()%>','Y')">미이수</a>
	               		<%	} else { 
	               				out.print("미이수");
	               			}
	               		}	 %>
	            <%   } else {out.print("미처리");} %>
           </td>
                <td class="table_02_1">
			  <%if (data.getNotgraduetc().equals("")) {
		  			if (!data.getNotgraducddesc().equals(""))
			  			out.print(data.getNotgraducddesc());
			  		} else {
			  		out.print(data.getNotgraduetcdesc());
			  	}
			  %> </td>
                      <td class="table_02_1">
                       <% if (data.getIsgraduated().equals("N")) { %> <a href="javascript:whenChangeGradu('<%=data.getUserid()%>','<%=data.getName()%>','<%=data.getJikwinm()%>','<%=data.getCompnm()%>','<%=data.getNotgraduetc()%>')"><img src="/images/admin/button/b_sayouchange.gif" border="0"></a> 
                        <% } %></td>
                      <td class="table_02_1">
                      <%//유계불참이면 입력할 수 없음 - TZ_CODE GUBUN='0028' 10(유계불참),11(유계불참(해외출장)) %>
                		<%// if (data.getNotgraduetc().equals("10") || data.getNotgraduetc().equals("11")) { %>
                		<% if (!data.getNotgraduetc().equals("")) { %>
                		
                		<% } else { %>
                      	<input type="checkbox" name="p_checks" value="<%=data.getUserid()%>" >
                      	<% } %>
                      
                      </td>
              </tr>
                    <%  } %>
                  </form>
            </table>
            <!----------------- 집합과정 개인별 수료처리 끝 ----------------->
            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
<form name="form3" method="post" action="/servlet/controller.complete.FinishServlet">
  <input type="hidden" name="p_process"  value="">
  <input type="hidden" name="p_action"  value="">
  <input type="hidden" name="p_subj"     value="<%=v_subj%>">
  <input type="hidden" name="p_year"     value="<%=v_year%>">
  <input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
  <input type="hidden" name="p_value"    value="">
  <input type="hidden" name="p_isclosed" value="<%=v_isclosed%>">
  <input type="hidden" name="p_userid" value="">
  <input type="hidden" name="p_name" value="">
  <input type="hidden" name="p_jikwinm" value="">
  <input type="hidden" name="p_compnm" value="">
  <input type="hidden" name="p_notgraduetc" value="">
  <input type="hidden" name="p_frompage" value="off">
  <input type="hidden" name="p_isgraduated" value="">
  
      	<input type="hidden" name="s_grcode"      value="<%=ss_grcode%>">
  		<input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
  		<input type="hidden" name="s_grseq"       value="<%=ss_grseq%>">
  		<input type="hidden" name="s_subjcourse"  value="<%=v_subj%>">
  		<input type="hidden" name="s_subjseq"     value="<%=v_subjseq%>">

  
  
  <input type="hidden" name="p_orderColumn">
  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">


</body>
</html>
