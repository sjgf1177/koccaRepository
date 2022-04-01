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
    
    DataBox dbox1 = (DataBox)request.getAttribute("subjectSelect");
    
	String v_subjnm 	= dbox1.getString("d_subjnm");    	//과정명
	String v_edugubun 	= dbox1.getString("d_edugubun");  	//교육유형코드
	String v_edugubunnm = dbox1.getString("d_edugubunnm");	//교육유형명
	String v_subjgubun 	= dbox1.getString("d_subjgubun"); 	//과정구분(A:LMS등록과정,B:강사/예산관리시스템 등록 과정)
	
	//계획
	String v_edustarta 	= dbox1.getString("d_edustarta"); 	//교육시작일(계획)
	String v_eduenda 	= dbox1.getString("d_eduenda");   	//교육종료일(계획)
	String v_edutargeta = dbox1.getString("d_edutargeta");	//교육대상(계획)
	String v_edutmema 	= dbox1.getString("d_edutmema");  	//교육총인원(계획)
	String v_edutseqa 	= dbox1.getString("d_edutseqa");  	//교육총차수(계획)
	String v_seqpermema = dbox1.getString("d_seqpermema");	//차수당인원(계획)
	String v_eduplacea 	= dbox1.getString("d_eduplacea"); 	//교육장소(계획)
	
	String v_gyamt 		= dbox1.getString("d_gyamt");     	//고용보험환급예상금액
	
	String v_inusercomp = dbox1.getString("d_inusercomp");  //등록자 소속 코드
	
	String v_today = FormatDate.getDate("yyyy-MM-dd");
	
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
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT language="JavaScript">
<!--

function goSubjectListPage() {
  document.form2.p_process.value  = 'SubjList';
  document.form2.submit();
}

function InsertSubject() {
  document.form2.p_process.value = 'SubjInsert';
  document.form2.submit();
}

function UpdateSubject() {

<%//총괄관리자/강사관리자만 수정 가능 %>
<% if (!box.getSession("gadmin").substring(0,1).equals("A") && !box.getSession("gadmin").substring(0,1).equals("R")) { %>
  if('<%=v_eduenda%>' > '<%=v_today%>') {
  	alert("교육종료일이 지나서 수정할 수 없습니다.");
  	return;
  }
<% } %>
  document.form2.p_process.value = 'SubjUpdatePage';
  document.form2.submit();
}

function DeleteSubject() {
  document.form2.p_process.value = 'SubjDelete';
  document.form2.submit();
}

/*
function sw_file_add(ext) { 
		var intloopi,intloopj,intloopk ;
		intloopj = document.form2.txtcurrnum.value ;
		if(intloopj==1) {sw_file_add_form.innerHTML = '';}
		uf = "";
		for(intloopi=1;intloopi<intloopj;intloopi++) {
			eval('sw_file_add_form' + uf).innerHTML = "<input type='text' name='txtcurr' size='50' style='width:90%' " + ext + " value='"+(uf+2)+" 차시 : '><div id='sw_file_add_form" + (uf+1) + "'></div>";
			uf++; 
		}
		document.Lecreqfm.totfilenum.value = intloopj ; //최초 한개를 포함
	}
*/
function searchTutor(p_id) {
  var url = "/servlet/controller.budget.BudgetAdminServlet?p_process=searchTutor&p_id="+p_id;
  open_window("",url,"0","0","200","200");
}

function receiveTutor(p_id, tutorGubun , tutorName, tutorID) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	
	v_id.value=tutorID;
	v_name.value = tutorName;	
}

function tutorCancel(p_id) {
	var v_id = eval("document.form2.p_tutorid" + p_id);
	var v_name = eval("document.form2.p_tutorname" + p_id);
	v_id.value = "";
	v_name.value = "";
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form2" method="post" action="/servlet/controller.budget.BudgetAdminServlet">
    <input type="hidden" name="p_process"   value="">
	<input type="hidden" name="p_subj"		value="<%=v_subj%>">
	
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
            <td width="15%" class="table_title_bold">과정명</td>
            <td width="68%" class="table_02_2"><%=v_subjnm%></td>
          </tr>
          <tr> 
            <td class="table_title_bold">교육유형</td>
            <td class="table_02_2"><%=v_edugubunnm%></td>
          </tr>
          <tr> 
            <td class="table_title_bold">교육대상</td>
            <td class="table_02_2"><%=v_edutargeta%></td>
          </tr>
          <tr> 
            <td class="table_title_bold">교육장소</td>
            <td class="table_02_2"><%=v_eduplacea%></td>
          </tr>
          <tr> 
            <td class="table_title_bold">교육시작일</td>
            <td class="table_02_2"><%=v_edustarta%></td>
          </tr>
          <tr> 
            <td class="table_title_bold">교육종료일</td>
            <td class="table_02_2"><%=v_eduenda%></td>
          </tr>
          <tr align="center"> 
            <td colspan="2" bgcolor="#FFFFFF" style=padding-top:5;padding-bottom:5;><table width="98%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="50%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td height="14" colspan="2" class="table_title_bold">교육인원</td>
                      </tr>
                      
					  <!-- 인원정보 시작 -->
                <%  ArrayList  list1 = (ArrayList)request.getAttribute("budgetmemSubjList");
				    for (int i=0; i<list1.size(); i++) {
				        DataBox dbox = (DataBox)list1.get(i); %>
                <tr> 
                  <td width="37%" class="table_title_l" style="padding-left:30px;"><%=dbox.getString("d_codenm")%></td>
                  <td width="63%" class="table_01">
                  <table>
                    <tr><td width="50%" class="table_01"><%=dbox.getInt("d_plandata")%> 명</td>
                        <td width="50%"></td>
                    </tr>
                  </table>
                  </td>
                </tr>
                <%  } %>
                <!-- 교육인원정보 끝 -->
                    </table></td>
                  <td width="1%">&nbsp;</td>
                  <td width="49%">
                  
                  <table width="100%" border="0" cellpadding="0" cellspacing="1" >
                      <tr> 
                        <td colspan="2" class="table_title_bold">예산</td>
                      </tr>
					  <!-- 예산정보 시작 -->
				<%  ArrayList  list2 = (ArrayList)request.getAttribute("budgetSubjList");
					int v_sumcost = 0;
				    for (int i=0; i<list2.size(); i++) {
				        DataBox dbox = (DataBox)list2.get(i); 
				        v_sumcost+=dbox.getInt("d_plandata");
				%>
                <tr> 
                  <td width="29%" class="table_title_l" style="padding-left:40px;"><%=dbox.getString("d_codenm")%></td>
                  <td width="34%" class="table_01">
                  <table>
                    <tr><td width="50%" class="table_01">
                  <% //총괄관리자/강사관리자를 제외하고 타부서 예산 조회 불가 %>
                  <%if (!box.getSession("gadmin").substring(0,1).equals("A")  && !box.getSession("gadmin").substring(0,1).equals("R") && !v_inusercomp.equals(box.getSession("comp"))) { %>
                  -
                  <%	} else { %>
                  		<%=dbox.getInt("d_plandata")%>
    	                원 
    	          <% } %>
    	          </td>
                        <td width="50%"></td>
                    </tr>
                  </table>
    	          </td>
                </tr>
				<%  } %>
				<tr> 
                  <td width="29%" class="table_title_l" style="padding-left:40px;">계</td>
                  <td width="34%" class="table_01">
                  <table>
                    <tr><td width="50%" class="table_01"><%=v_sumcost%>
                    원 </td>
                        <td width="50"%></td>
                    </tr>
                  </table></td>
                </tr>
                <!-- 예산정보 끝 -->
					  
                    </table>
                    
                    
                    </td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td class="table_title">교육총인원</td>
            <td width="81%" class="table_02_2"><%=v_edutmema%> 명</td>
          </tr>
          <tr> 
            <td class="table_title">교육총차수</td>
            <td class="table_02_2"><%=v_edutseqa%></td>
          </tr>
          <tr> 
            <td class="table_title">차수당인원</td>
            <td class="table_02_2"><%=v_seqpermema%> 명</td>
          </tr>
          <tr> 
            <td class="table_title">고용보험환급예상금액</td>
            <td class="table_02_2"><%=v_gyamt%> 원</td>
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
       	<!-- 예산정보 시작 -->
		<%  ArrayList  list3 = (ArrayList)request.getAttribute("SubjProgramList");
			if (list3.size()==0) { %>
		  <tr><td class="table_02_1" colspan="7" align=center>등록된 프로그램이 없습니다.</td></tr>
		<% } else {
		    for (int i=0; i<list3.size(); i++) {
		        DataBox dbox3 = (DataBox)list3.get(i); %>
          <%// for (int i=1;i<16;i++) { %>
          <tr> 
            <td class="table_02_2"><%=i+1%></td>
            <td class="table_02_2"><%=dbox3.getString("d_lessonnm")%></td>
            <td class="table_02_1"><%=dbox3.getString("d_tutorname")%></td>
            <td class="table_02_1"><%=dbox3.getString("d_tutorid")%></td>
            <td class="table_02_1"><%=dbox3.getString("d_tutorgubunnm")%></td>
            <td class="table_02_1"><%=dbox3.getInt("d_eduhr")%></td>
            <td class="table_02_1_r">
            <table><tr><td width="70%">
            <%=dbox3.getInt("d_price")%>
            </td>
            <td width="30%"></td>
            </tr></table>
            </td>
          </tr>
          <% }
          } %>
          <input type="hidden" name="p_lessoncnt" value="15">
        </table>  
        <!----------------- 과정맛보기 등록 끝 ----------------->
        <br>
       <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
          
          <a href="javascript:UpdateSubject()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
          
          <td>
		  <td width=8></td>
		  <%//총괄관리자/강사관리자만 삭제 가능 %>
		  <% if (box.getSession("gadmin").substring(0,1).equals("A") || box.getSession("gadmin").substring(0,1).equals("R")) { %>
          <td><a href="javascript:DeleteSubject()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		  <td width=8></td>
		  <% } %>
          <td><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
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
