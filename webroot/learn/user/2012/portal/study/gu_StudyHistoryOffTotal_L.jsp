<%
//**********************************************************
//  1. 제      목: STUDY HISTORY LIST
//  2. 프로그램명: zu_StudyHistoryOffTotal_L.jsp
//  3. 개      요: 개인교육이력 off-line
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.25
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","2");
	box.put("submenu","2");

    String  v_user_id   		= box.getSession("userid");
    String  v_process   		= box.getString("p_process");
    String  v_selectcode		= box.getStringDefault("p_selectcode", "ALL");
	String	v_grcode			= box.getSession("tem_grcode");

    String  v_upperclass		= "";
    String  v_upperclassnm		= "";
    String  v_subj      		= "";
    String  v_year      		= "";
    String  v_subjnm    		= "";
    String  v_subjseq   		= "";
    String  v_edustart  		= "";
    String  v_eduend    		= "";
    String  v_kind      		= "1";
    String  v_isgraduated		= "";
    String  v_isgraduated_txt	= "";
    String  v_stustatus 		= "";
    String  v_stustatusnm		= "";
    double	v_score				= 0d;
	String  v_isterm			= "";

    int     i           		= 0;

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;

    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   

    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudyHistoryOffList");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

// 학습현황 OPEN
function whenEdulist(subjnm, iseduend, url) {
    var ulink = url + "&p_subjnm=" + subjnm + "&p_iseduend=" + iseduend;
    var new_Open = window.open(ulink,"eduList",'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
}

//과정상세정보 POPUP
function whenSubjInfoPopup(subj,subjnm,isonoff){
    window.self.name = "SubjList";
    window.open("", "openSubjInfo", "scrollbars=no,width=720,height=350,scrollbars=yes,resizable=yes'");
    document.form1.target = "openSubjInfo"

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_process.value = 'SubjectPreviewPopup';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//학기별점수 POPUP
function whenTermScorePopup(subj,year,subjseq){
    window.self.name = "scoreList";
    window.open("", "opentermScore", "scrollbars=no,width=400,height=277,scrollbars=auto,resizable=yes'");
    document.form1.target = "opentermScore"

    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'EducationStudyingOffTermScorePopup';
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//점수 POPUP
function whenScorePopup(subj,year,subjseq){
    window.self.name = "scoreList";
    window.open("", "openScore", "scrollbars=no,width=550,height=277,scrollbars=no,resizable=no'");
    document.form1.target = "openScore"

    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_process.value = 'EducationStudyingOffScorePopup';
    document.form1.action='/servlet/controller.study.MyClassServlet';
    document.form1.submit();

    document.form1.target = window.self.name;
}

//수료증 출력
function suRoyJeung(subj,year,subjseq,userid){
	window.self.name = "SuryoJeung";
	var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=830,height=800,scrollbars=yes,resizable=NO');
	
	document.form1.target = "openSuryoJeungPrint";
	document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
	
	//document.form1.action = "/learn/admin/polity/RexViewer.jsp?pRptNames=__Diploma_Kocca&pRptParams=lee1^N000001^2006^cbu001^0001";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_scsubjseq.value = subjseq;
	document.form1.p_userid.value = userid;
	document.form1.p_process.value = "DiplomaOffPrint";
	document.form1.submit();
    document.form1.target = window.self.name;
}

// 학습창 OPEN
function studyOpen(url, iurl) {
<%      if (box.getSession("gadmin").equals("ZZ")) {  %>
            var s_url = url+"&p_iurl="+iurl;
//	    alert(s_url);
           if (url == '') {
               alert("정보가 없습니다");
           } else {
               if (iurl == 0)
                open_window('openApp',s_url,0,0,0,0,false,false,true,true,true);
               else
                open_window('openApp',s_url,0,0,1024,768,false,false,'yes','yes','yes');
           }
<%      } else {                                      %>
            alert ("학습자 권한이 아닙니다.");
<%      }                                             %>
}

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_pageno.value = "1";
	document.form1.p_process.value = "StudyHistoryOffList";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyClassServlet";
	document.form1.p_process.value = "StudyHistoryOffList";
	document.form1.submit();

}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_sel'       value="<%=box.getString("p_sel")%>">
    <input type='hidden' name='p_subj'      value="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='s_grcode'    value="<%=v_grcode%>">
    <input type='hidden' name='p_year'      value="">
    <input type='hidden' name='s_subj'      value="">
    <input type='hidden' name='p_scsubjseq' value="">
    <input type='hidden' name='p_subjseq'   value="">
    <input type='hidden' name='p_userid'    value="">
    <input type='hidden' name='p_kind'      value="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 ><u>나의 교육이력</u></td>
			</tr>
		</table>
		<div class="concept a02">
			<div class="titlemsg"><img src="/images/2012/sub/page1/title/learning_finish.gif" alt="나의 교육이력"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.study.MyClassServlet?p_process=StudyHistoryList" tabindex="171" title="회원님의 온라인과정 교육이력을 확인할 수 있습니다"><img src="/images/2012/sub/page1/tabmenu/online_off.png" alt="온라인과정"></a></li>
				<li><img src="/images/2012/sub/page1/tabmenu/offline_on.png" alt="오프라인과정"></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0">
					<colgroup><col width="90"><col width="*"><col width="140"><col width="80"><col width="80"><col width="80"></colgroup>
					<tr class="listhead">
						<th>카테고리
							<!-- <select class="classification">
								<option>전체</option>
								<option>기획창작</option>
								<option>방송영상</option>
								<option>게임제작</option>
								<option>경영지원</option>
							</select> -->
						</th>
						<th>과정명</th>
						<th>교육기간</th>
						<th>성적보기</th>
						<th>상태</th>
						<th>수료증</th>
					</tr>
					
					 <%
						for(i = 0; i < list.size(); i++) {
							DataBox dbox = (DataBox)list.get(i);
				
							v_subj        	= dbox.getString("d_subj");
							v_year        	= dbox.getString("d_year");
							v_subjseq     	= dbox.getString("d_subjseq");
							v_subjnm      	= dbox.getString("d_subjnm");
							v_edustart    	= dbox.getString("d_edustart");
							v_eduend      	= dbox.getString("d_eduend");
							v_isgraduated 	= dbox.getString("d_isgraduated");
							v_stustatus 	= dbox.getString("d_stustatus");
							v_stustatusnm 	= dbox.getString("d_stustatusnm");
							v_upperclassnm 	= dbox.getString("d_upperclassnm");
				            v_score			= dbox.getDouble("d_score");
							v_isterm 	    = dbox.getString("d_isterm");
				
				            v_dispnum	= dbox.getInt("d_dispnum");
				            v_totalpage	= dbox.getInt("d_totalpage");
				            
							v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
							v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy.MM.dd");
				
							if ("Y".equals(v_isterm)) {
								v_isgraduated_txt = v_stustatusnm;
							} else {
								if(v_isgraduated.equals("Y"))       v_isgraduated_txt = "수료";
								else if(v_isgraduated.equals("N"))  v_isgraduated_txt = "미수료";
							}
					%>
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst"><%= v_upperclassnm %></td>
						<td class="title"><%= v_subjnm %></td>
						<td><%= v_edustart %>~ <%= v_eduend %></td>
						<td>
							<%if ("N".equals(v_isterm)) {  %>
								<button type="button" tabindex="<%="2"+i+"1"%>" onclick="javascript:whenScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="btn_small_gray" title="클릭하시면 수료증을 인쇄할 수 있습니다"><%= v_score %></button>
							<%}else{%>
								<button type="button" tabindex="<%="2"+i+"1"%>" onclick="javascript:whenTermScorePopup('<%= v_subj %>','<%= v_year %>', '<%= v_subjseq %>')" class="btn_small_gray" title="클릭하시면 수료증을 인쇄할 수 있습니다">학기별</button>
								
							<%}%>
						 </td>
						<td><%= v_isgraduated_txt %><%if(v_isgraduated_txt.equals("")){%>&nbsp;<%} %></td>
						<td>
							<%if("Y".equals(v_isgraduated)){%>
							<button type="button" tabindex="<%="2"+i+"2"%>" onclick="javascript:suRoyJeung('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>','<%=v_user_id%>')" class="btn_small_gray" title="클릭하시면 수료증을 인쇄할 수 있습니다">인쇄</button>
							<%}else{%> - <%}%>
							</td>
					</tr>
					<% } %>
					<%	if(i == 0){	 %>
				  <tr><td colspan="5" align="center" class="linefirst">학습중인 과정이 없습니다.</td></tr>
				  <% } %>
				</table>
			</div>
			<div class="pageset">
				<%=PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
