<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************/
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
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
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	String type = box.get("type");
	if (box == null) box = RequestManager.getBox(request);
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	DataBox subjectPreview = (DataBox) request.getAttribute("subjectPreview");
	List<DataBox> selectChkUser = (List<DataBox>)request.getAttribute("selectChkUser");
	List<DataBox> tutorList = (List<DataBox>)request.getAttribute("tutorList");
	boolean isSeq = true;//수강 가능인원이 있을때로 변경
	
	isSeq = subjectPreview.getString("d_flagyn").equals("Y") ? true : false; // 수강신청 버튼 on/off 설정, 신청인원에 따른 flag 미적용(추후)

     Date date = new Date();
     SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmm");
     String strdate = simpleDate.format(date).substring(0,10);
     String nflage="";
     if(subjectPreview.getString("d_propend").compareTo(strdate)>0)
        nflage="Y";
     else
        nflage="N";
     box.put("topmenu","4");
     String v_area = box.get("p_area");
 	if (v_area.equals("")) {
 		box.put("submenu","1");
 	} else if (v_area.equals("B0")) {
 		box.put("submenu","1");
 	} else if (v_area.equals("G0")) {
 		box.put("submenu","2");
 	}else if (v_area.equals("K0")) {
 		box.put("submenu","3");
 	}
 	
 	String sub_title_img ="";  // 서브이미지
	String sub_title_name =""; // 서브 이미지 설명
	String sub_title_navi =""; // 서브 이미지 설명
	
	 if (type.equals("S01")) {
		box.put("submenu","1");
		sub_title_img = "/images/2012/sub/page3/title/offline_cast.gif";
		sub_title_name = "오프라인과정 방송영상 분야";
		sub_title_navi = "방송영상";
	} else if (type.equals("S02")) {
		box.put("submenu","2");
		sub_title_img = "/images/2012/sub/page3/title/offline_making.gif";
		sub_title_name = "오프라인과정 게임개발 분야";
		sub_title_navi = "게임제작";
	} else if (type.equals("S03")) {
		box.put("submenu","3");
		sub_title_img = "/images/2012/sub/page3/title/offline_plan.gif";
		sub_title_name = "오프라인과정 기획창작 분야";
		sub_title_navi = "기획창작";
	} else {
		box.put("submenu","1");
		sub_title_img = "/images/2012/sub/page3/title/offline_cast.gif";
		sub_title_name = "오프라인과정 방송영상 분야";
		sub_title_navi = "전체";
	}
	
	
	String full_toggle = "off";
	String sub1_toggle = "off";
	String sub2_toggle = "off";
	String sub3_toggle = "off";
	String sub4_toggle = "off";
	
	if ("001".equals(v_area))        { sub2_toggle = "on"; 
	} else if ("002".equals(v_area)) { sub3_toggle = "on"; 				
	} else if ("003".equals(v_area)) { sub4_toggle = "on"; 	
	} else if ("004".equals(v_area)) { sub1_toggle = "on"; 
	} else { full_toggle = "on"; 				
	}
	
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script language="JavaScript">
<!--//
//LIST
function whenList(){
	document.form1.target = "_self";
	document.form1.action='/servlet/controller.off.ProposeOffServlet';
	document.form1.p_process.value = 'SubjectList';
	document.form1.p_rprocess.value = '';
	self.location.href = "/servlet/controller.off.ProposeOffServlet?p_process=SubjectList";
}

// 과정검색 엔터처리
function search_enter(e)  {
	if (e.keyCode =='13'){  subjSearch();  }
}

// 과정검색
function subjSearch() {
	document.form1.p_process.value = "SubjectList";
	document.form1.submit();
}

// 인기과정
function whenSubjBest(){
	document.form1.target = "_self";
	document.form1.p_process.value= "SubjectListBest";
	document.form1.action= "/servlet/controller.off.ProposeOffServlet";
	document.form1.submit();
}



// 전체과정보기 (이전화면)
function whenSubjList() {
    var actionurl = document.form1.p_actionurl.value;

    document.form1.target = "_self";
    document.form1.p_process.value = document.form1.p_rprocess.value;
    if(actionurl != null && actionurl != "") {
		document.form1.action = document.form1.p_actionurl.value;
    }
    document.form1.submit();
}

// 차수정보조회
function whenSelSubjInfo(subjseq,year) {
	var url = "/servlet/controller.off.ProposeOffServlet";

	var new_Open = window.open(url,"seqInfo",'scrollbars=yes,width=680,height=600,resizable=no');
	document.form1.target = "seqInfo";
	document.form1.p_process.value = "SeqPreviewPage";
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_year.value = year;
	document.form1.submit();
}

//수강신청
function whenSubjPropose() {
	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}
	if(!confirm("<%= subjectPreview.getString("d_subjnm") %> 과정을 수강신청하시겠습니까?")) {
	 return;
	}
	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "SubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.action = "/servlet/controller.off.ProposeOffServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//콘텐츠 평가보기
function ContentResearchInfo(){
  document.form1.target = "_self";
  document.form1.p_process.value = "ContentResearch";
  document.form1.action="/servlet/controller.off.ProposeOffServlet";
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
	 var url = "/servlet/controller.off.ProposeOffServlet";
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

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.off.ProposeOffServlet" method="post">
	<input type="hidden" name="type"	value="<%=type%>"/>
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess" value="<%=box.getStringDefault("p_rprocess", box.get("p_process"))%>"/>
	<input type="hidden" name="p_actionurl" value="<%=box.get("p_actionurl")%>"/>
	<input type="hidden" name="p_year"	value="<%=box.get("p_year")%>"/>
	<input type="hidden" name="p_subj"	value="<%=box.get("p_subj")%>"/>
	<input type="hidden" name="p_subjseq"	value="<%=box.get("p_subjseq")%>"/>
	<input type="hidden" name="p_seq"	value="<%=box.get("p_seq")%>"/>
	<input type="hidden" name="p_subjnm"	value="<%=box.get("p_subjnm")%>"/>
	<input type="hidden" name="p_iscourseYn"	value=""/>
	<input type="hidden" name="p_upperclass"	value="<%= box.get("p_upperclass") %>"/>
	<input type="hidden" name="p_upperclassnm"	value="<%=box.get("p_upperclassnm")%>"/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
	
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치를 안내합니다">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 오프라인과정 > <u><%=sub_title_navi%></u></td>
			</tr>
		</table>
		<div class="concept c01">
			<div><img src="<%=sub_title_img%>" alt="<%=sub_title_name%>"></div>
			<ul class="coursesearch">
				<li class="insert"><input type="text" tabindex="121" name="s_subjnm" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:subjSearch();" tabindex="122" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
			<ul class="pagetabC">
				<li><a href="javascript:selectArea('004','');" tabindex="123" title="3D 입체콘텐츠 전문인력양성 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/3dsolid_<%=sub1_toggle%>.png" alt="3D 입체콘텐츠 전문인력양성"></a></li>
				<li><a href="javascript:selectArea('001','');" tabindex="124" title="산업계 직무 재교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/industry_<%=sub2_toggle%>.png" alt="산업계 직무 재교육"></a></li>
				<li><a href="javascript:selectArea('002','');" tabindex="125" title="차세대 인재양성교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/talent_<%=sub3_toggle%>.png" alt="차세대 인재양성교육"></a></li>
				<li><a href="javascript:selectArea('003','');" tabindex="126" title="수탁교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/brokerage_<%=sub4_toggle%>.png" alt="수탁교육"></a></li>
			</ul>
		</div>
		<div class="onlinecourse">
			<div class="listback"><button type="button" tabindex="127" onclick="javascript:whenSubjList();" class="btn_small_gray" title="과정목록으로">목록으로</button></div>
			<dl class="skipcontent">
				<dt>본문 바로가기</dt>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="128" title="교육목표로 이동합니다">교육목표</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="129" title="교육내용로 이동합니다">교육내용</a></dd>
				<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="130" title="수강안내로 이동합니다">수강안내</a></dd>
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
			<table class="coursedetailheader" cellspacing="0" cellpadding="0" id="skip01">
				<colgroup><col width="65%"><col width="*"></colgroup>
				<tr>
					<th>
						<h1 style="font-size: 28px;"><%= subjectPreview.getString("d_subjnm") %> </h1>
						<h4><%= subjectPreview.getString("d_subjtarget") %> </h4>
						<img src="/images/2012/sub/page2/subject_learning_target.gif" alt="교육목표에 대해 알아볼까요?">
						<h6>
							<%= subjectPreview.getString("d_intro") %>
						</h6>
					</th>
					<td>
						<img src="/images/2012/sub/page2/sample_offcourse.gif" class="courseimage" alt="교육의문">
						<div class="offlinecourseoption">
							<%	if (isSeq && nflage.equals("Y")) { %>
							<a href="javascript:whenSubjPropose()" tabindex="208" title="이 과정에 대해 수강신청을 할 수 있습니다"><img src="/images/2012/common/btn_request.gif" alt="수강신청"></a>
							<% } %>
						</div>
					</td>
				</tr>
			</table>
			<table class="learningcontent" cellspacing="0" cellpadding="0" id="skip02">
				<colgroup><col width="80"><col width="*"></colgroup>
				<tr>
					<th><img src="/images/2012/sub/page3/subject_learning_about.gif" alt="교육정보"></th>
					<td>
						<%= StringManager.removeHTMLHead(subjectPreview.getString("d_explain")) %>
					</td>
				</tr>
			</table>
			<%	if (isSeq) { %>	
			<img src="/images/2012/sub/page2/subject_course_guide.gif" class="subject" alt="수강안내">
			<div class="courseguidebox" id="skip03">
				<div class="rable4">
					<img src="/images/2012/sub/page2/subject_course_about.gif" alt="수강정보">
					<table class="courseguide" cellspacing="0" cellpadding="0" summary="수강안내">
						<colgroup><col width="120"><col width="350"><col width="120"><col width="*"></colgroup>
						<tr>
							<th>수강신청 시작일</th>
							<td><%= subjectPreview.getDate("d_propstart", ".") %></td>
							<th>수강신청 종료일</th>
							<td><%= subjectPreview.getDate("d_propend", ".") %></td>
						</tr>
						<tr>
							<th>교육시작일</th>
							<td><%= subjectPreview.getDate("d_edustart", ".") %></td>
							<th>교육종료일</th>
							<td><%= subjectPreview.getDate("d_eduend", ".") %></td>
						</tr>
						<tr>
							<th>교육정원</th>
							<td><%= subjectPreview.getCommaInt("d_studentlimit") %>명</td>
							<th>교육비</th>
							<td>
							<% 
								String biyong = "";
								if( subjectPreview.getCommaInt("d_biyong").equals("0") ) {
									biyong = "무료";
								} else {
									biyong = subjectPreview.getCommaInt("d_biyong") + " 원";
								}
								%>
							<%= biyong %>
						</td>
						</tr>
						<tr>
							<th>교육문의</th>
							<td colspan="3">
							<% if(!subjectPreview.getString("d_charger").equals("")){%>
								<%=subjectPreview.getString("d_charger") %>
								<%}else{ %>
									-
								<%} %>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<%} %>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
