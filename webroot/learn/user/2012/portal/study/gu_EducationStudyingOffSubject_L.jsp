<%
//**********************************************************
//  1. 제      목: EDUCATION STUDYING OFF SUBJECT PAGE
//  2. 프로그램명: zu_EducationStudyingOffSubject_L.jsp
//  3. 개      요: 수강중인 과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009/11/23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","2");
	box.put("submenu","1");

    String server = conf.getProperty("game.url.value");

    String  v_process   = box.getString("p_process");
    String  v_user_id   = box.getSession("userid");

    String  v_upperclass =  "";
    String  v_upperclassnm =  "";
    String  v_subj       =  "";
    String  v_year       =  "";
    String  v_subjnm     =  "";
    String  v_subjseq    =  "";
    String  v_edustart   =  "";
    String  v_eduend     =  "";
    String  v_isterm  = "";
    String  v_stustatus   = "";
    String  v_stustatusnm = "";
    
    int     i           =  0;
    int     l           =  0;

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

    ArrayList list1     = null;
    //DEFINED class&variable END

    list1 = (ArrayList)request.getAttribute("EducationStudyingOffSubjectList");   //study.MyClassBean.selectEducationStudyingOffSubjectList(box)
    //out.println(list3.size());
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
// 점수 POPUP
function whenScorePopup(subj,year,subjseq){
    window.self.name = "scoreList";
    window.open("", "openScore", "scrollbars=no,width=400,height=277,scrollbars=auto,resizable=yes'");
    document.form1.target = "openScore"

    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'EducationStudyingOffTermScorePopup';
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pageno.value = "1";
	document.form1.p_process.value = "EducationStudyingOffSubjectPage";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "EducationStudyingOffSubjectPage";
	document.form1.submit();

}
//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj'      value ="">
    <input type='hidden' name='p_year'      value ="">
    <input type='hidden' name='p_subjseq'   value ="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='p_userid'    value ="">
    <input type='hidden' name='p_gubun'     value ="">
    <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">


	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>수강중인 과정</u></td>
			</tr>
		</table>
		<div class="concept a01">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/learning_now.gif" alt="수강중인 과정"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage" tabindex="171" title="회원님께서 수강중인 온라인 과정을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/online_off.png" alt="온라인과정"></a></li>
				<li><img src="/images/2012/sub/page1/tabmenu/offline_on.png" alt="오프라인과정"></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="90"><col width="*"><col width="160"><col width="100"><col width="120"></colgroup>
					<tr class="listhead">
						<th>카테고리</th>
						<th>과정명</th>
						<th>교육기간</th>
						<th>성적보기</th>
						<th>상태</th>
					</tr>
					<%
          for(i = 0; i < list1.size(); i++) {
              DataBox dbox    = (DataBox)list1.get(i);

              v_upperclass    =  dbox.getString("d_upperclass");
              v_upperclassnm  =  dbox.getString("d_upperclassnm");
              v_subj          =  dbox.getString("d_subj");
              v_year          =  dbox.getString("d_year");
              v_subjnm        =  dbox.getString("d_subjnm");
              v_subjseq       =  dbox.getString("d_subjseq");
              v_edustart      =  dbox.getString("d_edustart");
              v_eduend        =  dbox.getString("d_eduend");
              v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
              v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
              v_isterm        = dbox.getString("d_isterm");
              v_stustatus     = dbox.getString("d_stustatus");
              v_stustatusnm   = dbox.getString("d_stustatusnm");

              v_dispnum	= dbox.getInt("d_dispnum");
              v_totalpage	= dbox.getInt("d_totalpage");
              		                
              if ("N".equals(v_isterm)) {
              	v_stustatusnm = "수강중";
              }
					%>
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= v_upperclassnm %></td>
						<td class="title" ><%=v_subjnm %></td>
						<td><%= v_edustart %>~<%= v_eduend %></td>
						<td>
						<% if ("Y".equals(v_isterm)) { %>
						<button type="button" onClick="javascript:whenScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>');" tabindex="<%=i+200%>" class="btn_small_gray" title="이 과정의 학습현황을 확인할 수 있습니다">보기</button>
						<% } else { %> -
						<% } %>
						</td>
						<td><%= v_stustatusnm %></td>
					</tr>
					
					<% } %>
					<% if(i == 0){	%>
			    	<tr><td colspan="5" align="center" class="linefirst">학습중인 과정이 없습니다.</td></tr>
			    <% } %>
				</table>
			</div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
