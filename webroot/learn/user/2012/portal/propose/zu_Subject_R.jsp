<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","3");
	
	
	String v_area = box.get("p_area");
	String sub_title_img ="";  // 서브이미지
	String sub_title_name =""; // 서브 이미지 설명
	/*if (v_area.equals("")) {
		box.put("submenu","1");
	} else if (v_area.equals("B0")) {
		box.put("submenu","2");
	} else if (v_area.equals("G0")) {
		box.put("submenu","3");
	}else if (v_area.equals("K0")) {
		box.put("submenu","4");
	}
	*/
	
	if (v_area.equals("")) {
		box.put("submenu","1");  
		sub_title_img = "/images/2012/sub/page2/title/online.gif";
		sub_title_name ="전체과정";
	} else if (v_area.equals("B0")) {  // 방송
		box.put("submenu","2");
		sub_title_img = "/images/2012/sub/page2/title/online_cast.gif";
		sub_title_name ="방송영상";
	} else if (v_area.equals("G0")) { // 게임
		box.put("submenu","3");
		sub_title_img = "/images/2012/sub/page2/title/online_game.gif";
		sub_title_name ="게임개발";
	}else if (v_area.equals("K0")) { // 문화
		box.put("submenu","4");
		sub_title_img = "/images/2012/sub/page2/title/online_culture.gif";
		sub_title_name ="문화콘텐츠";
	}
	
	
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	//String type = box.get("type");
	String v_upperclass = box.get("p_upperclass");
	if (box == null) box = RequestManager.getBox(request);
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	DataBox subjectPreview = (DataBox) request.getAttribute("subjectPreview");
	List<DataBox> subjseqList = (List<DataBox>)request.getAttribute("subjseqList");
	List<DataBox> selectSubjChasiList = (List<DataBox>)request.getAttribute("selectSubjChasiList");
	List<DataBox> selectChkUser = (List<DataBox>)request.getAttribute("selectChkUser");
	List<DataBox> lessonList = (List<DataBox>)request.getAttribute("lessonList");
	List<DataBox> tutorList = (List<DataBox>)request.getAttribute("tutorList");
	//선수과목
	ArrayList listPre = (ArrayList)request.getAttribute("subjectPre");
	//후수과목
	ArrayList listNext = (ArrayList)request.getAttribute("subjectNext");
	boolean isSeq = subjseqList != null && subjseqList.size() != 0;
	String sugang_YN=subjectPreview.getString("d_sugang_YN");
    String grcode=box.getSession("tem_grcode");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script language="JavaScript">
<!--//
//LIST
function whenList(){
	document.form1.target = "_self";
	document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
	document.form1.p_process.value = 'SubjectList';
	document.form1.p_rprocess.value = '';
	self.location.href = "/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList";
}

// 과정검색 엔터처리
function search_enter(e)  {
	if (e.keyCode =='13'){  subjSearch();  }
}

// 과정검색
/* function subjSearch() {
	if (document.form1.p_lsearchtext.value=="") {
		alert("검색어를 입력해주세요");
		return;
	}
	document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";	//
	document.form1.p_process.value = "SubjSearch";
	document.form1.submit();
} */

function subjSearch() {
	document.form1.p_process.value = "SubjectList";
	document.form1.submit();
}



// 인기과정
function whenSubjBest(){
	document.form1.target = "_self";
	document.form1.p_process.value= "SubjectListBest";
	document.form1.action= "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
}



// 전체과정보기 (이전화면)
//function whenSubjList() {

//	document.form1.p_actionurl.value = "/servlet/controller.study.InterestServlet";
//	var actionurl = document.form1.p_actionurl.value;

//	document.form1.target = "_self";
//	document.form1.p_process.value=document.form1.p_rprocess.value;
//	document.form1.p_process.value="InterestList";
//	if(actionurl != null && actionurl != "") {
//		document.form1.action=document.form1.p_actionurl.value;
//		document.form1.action = "/servlet/controller.study.InterestServlet";
//	}
//	document.form1.submit();
//}

function whenSubjList() {

	var actionurl = document.form1.p_actionurl.value;
    
	document.form1.target = "_self";

     <% if(!grcode.equals("N000001")) { %>
        document.form1.p_upperclass.value =""; //전체조회
     <%}%>

	document.form1.p_process.value=document.form1.p_rprocess.value;
	if(actionurl != null && actionurl != "")
		   document.form1.action=document.form1.p_actionurl.value;
	document.form1.submit();
}

// 차수정보조회
function whenSelSubjInfo(subjseq,year) {
	var url = "/servlet/controller.propose.ProposeCourseServlet";

	var new_Open = window.open(url,"seqInfo",'scrollbars=yes,width=680,height=600,resizable=no');
	document.form1.target = "seqInfo";
	document.form1.p_process.value = "SeqPreviewPage";
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_year.value = year;
	document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm) {
	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}
	if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")) {
	 return;
	}
	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "SubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//장바구니 담기
function whenSubjBasket(subj,year,subjseq, subjnm, billYn, price) {

	if(!confirm(subjnm+" 과정을 장바구니에 담으시겠습니까?")){
	 return;
	}

	document.form1.target = "_self";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_price.value = price;
	document.form1.p_process.value = "InsertBasketSubj";
	document.form1.action = "/servlet/controller.account.AccountManagerServlet";
	document.form1.submit();
}

//장바구니 담기(수강신청)
function whenSubjBasket2(subj,year,subjseq, subjnm, billYn, price) {

	if(!confirm(subjnm+" 과정을 수강신청 하시겠습니까?")){
	 return;
	}
	document.form1.target = "_self";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_price.value = price;
	document.form1.p_process.value = "InsertBasketSubj";
	document.form1.action = "/servlet/controller.account.AccountManagerServlet";
	document.form1.submit();
}

//콘텐츠 평가보기
function ContentResearchInfo(){
  document.form1.target = "_self";
  document.form1.p_process.value = "ContentResearch";
  document.form1.action="/servlet/controller.propose.ProposeCourseServlet";
  document.form1.submit();
}

//나의강의실
function goPersonHistory(){
  document.form1.target = "_self";
  document.form1.p_process.value = "EducationSubjectPage";
  document.form1.action="/servlet/controller.study.MyClassServlet";
  document.form1.submit();
}

//강의목차보기
function whenLectureList(isoutsourcing, conturl){
	window.self.name = "LectureList";
	if(isoutsourcing == "N"){
	 var url = "/servlet/controller.propose.ProposeCourseServlet";
	 var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=746,height=550,resizable=no');
	 document.form1.target="LectureList1";
	 document.form1.p_process.value="LectureList";
	 document.form1.submit();
	}else{
	  if (conturl == '') {
		alert("강의목차정보가 없습니다.");
	 }
	 url = conturl;
	 var new_Open = window.open(url,"LectureList1",'scrollbars=yes,width=800,height=600,resizable=no');
	}
}

// 맛보기
function whenPreShow(url, subj, d_wj_classkey) {    
    if (d_wj_classkey != '') {  //경영직무교육(웅진패스원)
        if (<%=grcode.equals("N000031") %>)  {
            // 로그
            prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
            open_window("openShow","http://samplezone.campus21.co.kr/classpreview.asp?classkey="+d_wj_classkey,"100","100","720","400","","","",true,true);
        }
        return;
     } else {
        // 로그
        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
        open_window("openShow",url,"100","100","<%= subjectPreview.get("d_prewidth", "800") %>","<%= subjectPreview.get("d_preheight", "600") %>","","","",true,true);
     }
}


// 맛보기
function studyOpen(url,subj) {
	// 로그
	prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;

	open_window('openApp',url,0,0,800,600,false,false,true,true,true);
}
//관심버튼 클릭
function fnInterestInsert(subj,subjnm) {

	if (confirm(subjnm + " 과정을 관심과정으로 등록하시곘습니까?")) {
			$.post("/servlet/controller.common.AjaxServlet"
					, {checkSqlNum:"interest.exist", sqlNum:"interest.insert", p_subj:subj,rerurnFunction:"resultInterest"}
					, function(data) {
						$("#ajaxDiv").html(data);
					});
	}
}
function resultInterest(data) {
	if (data.result=='true') alert("관심과정으로 등록했습니다."); 
	else alert(data.result);
}
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.submit();
	}
	function whenTutorShow() {
		$('#show').hide();
		$('#hide').show();
		$('#tutorListfalse').show();
	}
	function whenTutorHide() {
		$('#show').show();
		$('#hide').hide();
		$('#tutorListfalse').hide();
	}
	function whenProposeHide() {
		$("#existpropose1").hide();
		$("#existpropose2").hide();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
	<!--input type="hidden" name="type"	value="<%//=type%>"/-->
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	value="<%=box.getStringDefault("p_rprocess", box.get("p_process"))%>"/>
	<input type="hidden" name="p_actionurl" value="<%=box.get("p_actionurl")%>"/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value="<%=box.get("p_subj")%>"/>
	<input type="hidden" name="p_subjseq"	value="<%=box.get("p_subjseq")%>"/>
	<input type="hidden" name="p_subjnm"	value="<%=box.get("p_subjnm")%>"/>
	<input type="hidden" name="p_upperclass"	value="<%=box.get("p_upperclass")%>"/>
	<input type="hidden" name="p_upperclassnm"	value="<%=box.get("p_upperclassnm")%>"/>
	<input type="hidden" id="s_mclassnm" name="s_mclassnm"	value="<%=box.get("s_mclassnm")%>"/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
	<%
		String imgurl = subjectPreview.getString("d_introducefilenamenew");
							
		if (imgurl.equals("")) {
			imgurl = "/images/2012/common/not_image.gif";
		} else {
			imgurl = "http://edu.kocca.or.kr/upload/bulletin/"+imgurl;
		}
	%>
	
	<div class="content">
	
		<%
			String full_toggle = "off";
			String sub1_toggle = "off";
			String sub2_toggle = "off";
			String sub3_toggle = "off";
			String sub4_toggle = "off";
			
			if ("C01".equals(v_upperclass))        { sub1_toggle = "on"; //기획 이미지ON
			} else if ("C02".equals(v_upperclass)) { sub2_toggle = "on"; //제작				
			} else if ("C03".equals(v_upperclass)) { sub3_toggle = "on"; //유통	
			} else if ("C04".equals(v_upperclass)) { sub4_toggle = "on"; //경영지원
			} else { full_toggle = "on"; 				
			}
		%>
			
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 온라인과정 > <u><%=sub_title_name %></u></td>
			</tr>
		</table>
		<div class="concept b01">
			<div><img src="<%=sub_title_img%>" alt="<%=sub_title_name%>"></div>
			
			<ul class="coursesearch">
				<li class="insert"><input type="text" tabindex="121" name="s_subjnm" class="insearch" onkeypress="search_enter(event);" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:subjSearch();" tabindex="122" title="과정검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
			<ul class="pagetabB">
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" tabindex="123" title="기획 과정만 확인합니다"><img src="/images/2012/sub/page2/tabmenu/total_<%=full_toggle%>.png" alt="전체"></a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=C01" tabindex="124" title="기획 과정만 확인합니다"><img src="/images/2012/sub/page2/tabmenu/plan_<%=sub1_toggle%>.png" alt="기획"></a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=C02" tabindex="125" title="제작 과정만 확인합니다"><img src="/images/2012/sub/page2/tabmenu/making_<%=sub2_toggle%>.png" alt="제작"></a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=C03" tabindex="126" title="유통 과정만 확인합니다"><img src="/images/2012/sub/page2/tabmenu/circulation_<%=sub3_toggle%>.png" alt="유통"></a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_upperclass=C04" tabindex="127" title="경영지원 과정만 확인합니다"><img src="/images/2012/sub/page2/tabmenu/operation_<%=sub4_toggle%>.png" alt="경영지원"></a></li>
			</ul>
		</div>
		<div class="onlinecourse">
			<div class="listback"><button type="button" tabindex="128" onclick="javascript:whenSubjList();" class="btn_small_gray" title="과정목록으로">목록으로</button></div>
			<dl class="skipcontent">
				<dt>본문 바로가기</dt>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="129" title="교육목표로 이동합니다">교육목표</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="130" title="교육내용로 이동합니다">교육내용</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="131" title="강의참고로 이동합니다">강의참고</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="132" title="차시정보로 이동합니다">차시정보</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip05" tabindex="133" title="수강안내로 이동합니다">수강안내</a></dd>
			</dl>
			<br><br>
			<table class="linecolor" cellspacing="0" cellpadding="0" summary="4색 라인">
				<colgroup><col width="25%"><col width="25%"><col width="25%"><col width="*"></colgroup>
				<tr>
					<td class="color1"></td>
					<td class="color2"></td>
					<td class="color3"></td>
					<td class="color4"></td>
				</tr>
			</table>
			<table class="coursedetailheader" cellspacing="0" cellpadding="0" id="skip01" summary="과정명과 교육목표를 알고, 수강신청을 할 수 있습니다">
				<colgroup><col width="60%"><col width="*"></colgroup>
				<tr>
					<th>
						<h1><%= subjectPreview.getString("d_subjnm") %></h1>
						<h4><%= subjectPreview.getHtmlString("d_edumans") %></h4>
						<img src="/images/2012/sub/page2/subject_learning_target.gif" alt="교육목표에 대해 알아볼까요?">
						<h6>
							<%= subjectPreview.getHtmlString("d_intro") %>
						</h6>
					</th>
					<td>
						<img src="<%=imgurl%>" class="courseimage" alt="<%= subjectPreview.getString("d_subjnm") %>">
						<div class="courseoption">
						<%	if (isSeq && "Y".equals(sugang_YN)) { %>
							<a href="javascript:whenSubjPropose(
									'<%=box.get("p_subj")%>',
									'<%=box.get("p_year")%>',
									'<%=box.get("p_subjseq")%>',
									'<%=box.get("p_subjnm")%>');" tabindex="209" title="이 과정에 대해 수강신청을 할 수 있습니다"><img src="/images/2012/common/btn_request.gif" alt="수강신청"></a>
						<% }%>									
							<a href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl")%>','<%= box.get("p_subj")%>', '<%= box.get("p_wj_classkey")%>' )" tabindex="210" title="이 과정의 1차시를 체험할 수 있습니다"><img src="/images/2012/common/btn_sample.gif" alt="샘플강의"></a>
						</div>
					</td>
				</tr>
			</table>
			<table class="learningcontent" cellspacing="0" cellpadding="0" id="skip02" summary="교육내용에 대해 알아봅니다">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page2/subject_learning_content.gif" alt="교육내용"></th>
					<td>
						<%= subjectPreview.getHtmlString("d_explain") %>
					</td>
				</tr>
			</table>
			<table class="learningcontent" cellspacing="0" cellpadding="0" id="skip03" summary="강의에 참고사항을 알아봅니다">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page2/subject_learning_other.gif" alt="강의참고"></th>
					<td>
						<%= subjectPreview.getHtmlString("d_memo") %>
					</td>
				</tr>
			</table>
			<img src="/images/2012/sub/page2/subject_course_step.gif" id="skip04" class="subject" alt="차시정보">
			<div class="coursestepbox">
				<table class="coursestep" cellspacing="0" cellpadding="0" summary="차시정보를 안내합니다">
					<colgroup><col width="50"><col width="*"></colgroup>
					<thead>
						<tr>
							<th>차시</th>
							<th>강의주제</th>
						</tr> 
					</thead>
					<tbody>
					<%	for (DataBox lessonListDataBox : lessonList) {	%>
					
						<tr onmouseover="this.style.borderBottom='1px solid #2979d3'" onmouseout="this.style.borderBottom='0px'">
							<td class="step"><%= lessonListDataBox.get("d_lesson")  %></td>
							<td class="stepname"><%= lessonListDataBox.get("d_sdesc")  %></td>
						</tr>					
										      
					<%	} %>

					</tbody>
				</table>
			</div>
			<img src="/images/2012/sub/page2/subject_course_guide.gif" id="skip05" class="subject" alt="수강안내">
			<%	if (isSeq) { %>
			<div class="courseguidebox">
			<%	for(DataBox subjseqListDataBox : subjseqList) {	%>
				<div class="rable1">
					<img src="/images/2012/sub/page2/subject_course_about.gif" alt="수강정보">
					<table class="courseguide" cellspacing="0" cellpadding="0" summary="수강신청기간, 교육기간, 정원, 교육문의 등을 알아봅니다">
						<colgroup><col width="120"><col width="130"><col width="120"><col width="*"></colgroup>
						<tr>
							<th>수강신청 시작일</th>
							<td><%= subjseqListDataBox.getDate("d_propstart") %></td>
							<th>수강신청 종료일</th>
							<td><%= subjseqListDataBox.getDate("d_propend") %></td>
						</tr>
						<tr>
							<th>교육시작일</th>
							<td><%= subjseqListDataBox.getDate("d_edustart") %></td>
							<th>교육종료일</th>
							<td><%= subjseqListDataBox.getDate("d_eduend") %></td>
						</tr>
						<tr>
							<th>교육정원</th>
							<td>
							<%
								String slimit = "";
								if( subjseqListDataBox.getInt("d_studentlimit") == 0 ) {
									slimit = "제한없음";
								} else {
									slimit = subjseqListDataBox.getCommaInt("d_studentlimit") + " 명";
								}
								%>
								<%= slimit %></td>
							<th>교육비</th>
							<td>
								<% 
									String biyong = "";
									if( subjseqListDataBox.getInt("d_biyong") == 0 ) {
										biyong = "무료";
									} else {
										biyong = subjseqListDataBox.getCommaInt("d_biyong") + " 원";
									}
								%>
								<%= biyong %>
							</td>
						</tr>
						<tr>
							<th>교육문의</th>
							<td colspan="3">온라인 교육 운영 담당자 : 02-2161-0077 academy@kocca.kr</td>
						</tr>
					</table>
				</div>
				<div class="rable2">
					<img src="/images/2012/sub/page2/subject_course_clear.gif" alt="수료정보">
					<table class="courseguide" cellspacing="0" cellpadding="0" summary="수료기준에 대해 알아봅니다">
						<colgroup><col width="120"><col width="*"></colgroup>
						<tr>
							<th>수료기준</th>
							<td>점수</td>
						</tr>
						<tr>
							<th>총점</th>
							<td><%= subjseqListDataBox.get("d_gradscore") %></td>
						</tr>
						<tr>
							<th>진도율</th>
							<td><%= subjseqListDataBox.get("d_wstep") %>% 이상</td>
						</tr>
					</table>
				</div>
				<div class="clearL"></div>
				<div class="rable3">
					<img src="/images/2012/sub/page2/subject_course_evaluation.gif" alt="평가정보">
					<table class="courseguide" cellspacing="0" cellpadding="0" summary="평가기준에 대해 알아봅니다">
						<colgroup><col width="117"><col width="117"><col width="117"><col width="117"><col width="117"><col width="117"><col width="118"><col width="118"></colgroup>
						<tr>
							<th>평가기준</th>
							<th>진도율</th>
							<th>중간평가</th>
							<th>최종평가</th>
							<th>형성평가</th>
							<th>레포트</th>
							<th>참여도</th>
							<th>기타</th>
						</tr>
						<tr>
							<td class="evaluation">가중치(%)</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wstep") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wmtest") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wmtest") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wftest") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wreport") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wetc1") %>%</td>
							<td class="evaluation"><%= subjseqListDataBox.get("d_wetc2") %>%</td>
						</tr>
					</table>
				</div>
			</div>
			<% }} %>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
