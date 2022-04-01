<%
//**********************************************************
//  1. 제      목: 과정운영 등록관리
//  2. 프로그램명: za_cpcourseop_l.jsp
//  3. 개      요: 교육과정 차수 등록관리 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.21
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 0;
String v_subj = "", v_subjnm = "", v_cpnm = "", v_cpsubjseq = "", v_year = "";
String v_subjseq = "", v_subjseqgr = "", v_propstart = "", v_propend = "", v_edustart = "", v_eduend = "";
  
String v_searchtext = box.getString("p_searchtext");
int v_pageno = box.getInt("p_pageno"); 

//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript">
<!--//
function whenSelection(ss_action){
    document.form1.p_action.value = ss_action;
    
	if (ss_action=="go") {
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
		    alert("교육그룹을 선택하세요.");
		    return ;
		}
		top.ftop.setPam();
	}
    
	document.form1.action = "/servlet/controller.cp.CPCourseServlet";
	document.form1.p_process.value = "";
	document.form1.p_pageno.value = "1";
	document.form1.submit();
}

function go(index) {
     document.form1.p_pageno.value = index;
     document.form1.p_action.value = "go";     
     document.form1.action = "/servlet/controller.cp.CPCourseServlet";
     document.form1.p_process.value = "";     
     document.form1.submit();
}  

function listpage(pageNum) {
	document.form1.p_pageno.value = pageNum;
	document.form1.p_action.value = "go";
	document.form1.action = "/servlet/controller.cp.CPCourseServlet";
	document.form1.p_process.value =";     
	document.form1.submit();
}  

function selectList() {
	document.form1.action = "/servlet/controller.cp.CPCourseServlet";
	document.form1.p_process.value = "";   
	document.form1.p_pageno.value = "1istPage3";
	document.form1.submit();
}  

function eduinfo(year,subj,subjseq,subjseqgr) {
	document.form1.action = "/servlet/controller.cp.CPCourseServlet";
	document.form1.p_year.value = year;
	document.form1.p_subj.value = subj;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_subjseqgr.value = subjseqgr;
	document.form1.p_process.value = "insertPage";
	document.form1.submit();
}  
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form1" action="" method="post">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_action" value="">
<input type="hidden" name="p_year" value="">
<input type="hidden" name="p_subj" value="">
<input type="hidden" name="p_subjseq" value="">
<input type="hidden" name="p_subjseqgr" value="">
<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>과정운영 현황 등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>  
	  <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	          <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>

        <tr> 
          <td class="table_title" width="10%" rowspan="3">메일발송</td>
          <td class="table_02_1" width="20%">1차</td>
          <td class="table_02_1" width="60%">전체학습자발송</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%">2차</td>
          <td class="table_02_1" width="60%">우수자,부진자 분류 발송</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%">3차</td>
          <td class="table_02_1" width="60%">미수료자 독려메일</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_title" width="10%" rowspan="6">전화체크</td>
          <td class="table_02_1" width="20%" rowspan="3">1개월</td>
          <td class="table_02_1" width="60%">1주차 0%~5%까지 미접속자</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="60%">2주차 5%이하</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="60%">4주차 10%이하</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%">2개월</td>
          <td class="table_02_1" width="60%">자체관리</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%" rowspan="2">3개월</td>
          <td class="table_02_1" width="60%">총점 70이하 학습자 관리</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="60%">마지막주 70점이하 관리</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_title" width="10%" rowspan="3">공지사항</td>
          <td class="table_02_1" width="20%">시작전 1개월</td>
          <td class="table_02_1" width="60%">입과공지(수료기준,평가방법,문의처)</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%">2개월</td>
          <td class="table_02_1" width="60%"></td>
          <td class="table_02_1"  width="10%"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%">3개월</td>
          <td class="table_02_1" width="60%">종료일주일전 9일 종료안내공지</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_title" width="10%">평가안내</td>
          <td class="table_02_1" width="20%"></td>
          <td class="table_02_1" width="60%">/9일종료되는 평가 안내공지는 올라가 있는지?</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_title" width="10%" rowspan="2">미수료통보</td>
          <td class="table_02_1" width="20%"></td>
          <td class="table_02_1" width="60%">미수료확정자 전화안내</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
        <tr> 
          <td class="table_02_1" width="20%"></td>
          <td class="table_02_1" width="60%">학습연장후 최종 미수료자 전화</td>
          <td class="table_02_1"  width="10%"><input type="checkbox"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="10" height=6></td>
          <td align="right" height=6></td>
        </tr>
        <tr> 
          <td width="896" align="right" ></td>
          <td width="32" align="right"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
          <td width="10">&nbsp;</td>
          <td width="32" align="right"><a href="javascript:listpage(1)"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
<table width="1000" border="0" cellpadding="5" cellspacing="1">
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
<br>
</form>
</body>
</html>
