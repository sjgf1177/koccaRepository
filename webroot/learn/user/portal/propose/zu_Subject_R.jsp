<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
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

<%@ include file="/learn/user/portal/include/top.jsp"%>

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
function subjSearch() {
	if (document.form1.p_lsearchtext.value=="") {
		alert("검색어를 입력해주세요");
		return;
	}
	document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";	//
	document.form1.p_process.value = "SubjSearch";
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

	if (confirm(subjnm + " 과정을 관심과정으로 등록하시겠습니까?")) {
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
	
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>	
	
	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <% if      ("C01".equals(v_upperclass)) { %> <td><img src="/images/portal/homepage_renewal/online/stitle_02.gif" alt="기획" /></td><td class="h_road">Home &gt; 온라인 과정  &gt; <strong>기획</strong></td><% }
		   else if ("C02".equals(v_upperclass)) { %> <td><img src="/images/portal/homepage_renewal/online/stitle_03.gif" alt="제작" /></td><td class="h_road">Home &gt; 온라인 과정  &gt; <strong>제작</strong></td><% }
		   else if ("C03".equals(v_upperclass)) { %> <td><img src="/images/portal/homepage_renewal/online/stitle_04.gif" alt="유통" /></td><td class="h_road">Home &gt; 온라인 과정  &gt; <strong>유통</strong></td><% }
		   else if ("C04".equals(v_upperclass)) { %> <td><img src="/images/portal/homepage_renewal/online/stitle_05.gif" alt="경영지원" /></td><td class="h_road">Home &gt; 온라인 과정  &gt; <strong>경영지원</strong></td><% }   
		%>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td width="116"><img src="/images/portal/homepage_renewal/online/sstitle_001.gif"/></td>
        <%	if (isSeq && "Y".equals(sugang_YN)) { %>        
			<td width="392" class="h_road">
				<a href="javascript:whenSubjPropose(
									'<%=box.get("p_subj")%>',
									'<%=box.get("p_year")%>',
									'<%=box.get("p_subjseq")%>',
									'<%=box.get("p_subjnm")%>');" class="btn_red">
					<img src="/images/portal/homepage_renewal/online/btn_sct.jpg" width="87" height="25" />
				</a>
			</td>		
		<%} %>
        <td width="98" class="h_road">
        	<a href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl")%>','<%= box.get("p_subj")%>', '<%= box.get("p_wj_classkey")%>' )" class="btn_gr">
        		<img src="/images/portal/homepage_renewal/online/btn_study_view.jpg" width="98" height="25" />
        	</a>
        </td>
        <td width="46" class="h_road">
        	<a href="javascript:whenSubjList();" class="btn_gr">
        		<img src="/images/portal/homepage_renewal/online/btn_list.jpg" width="46" height="25" />
        	</a>
        </td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="2" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td class="tit_table" width="10%">제목</td>
        <td class="ct_tit_table_lright"><strong><%= subjectPreview.getString("d_subjnm") %></strong></td>
      </tr>
      <tr>
        <td class="tit_table">교육대상</td>
        <td class="ct_tit_table_lright"><%= subjectPreview.getHtmlString("d_edumans") %></td>
      </tr>
      <tr>
        <td class="tit_table">교육목적</td>
        <td class="ct_tit_table_lright"><%= subjectPreview.getHtmlString("d_intro") %></td>
      </tr>
      <tr>
        <td class="tit_table">교육내용</td>
        <td class="ct_tit_table_lright"><%= subjectPreview.getHtmlString("d_explain") %></td>
      </tr>
      <tr>
        <td class="tit_table">강의교재/참고</td>
        <td class="ct_tit_table_lright"><%= subjectPreview.getHtmlString("d_memo") %></td>
      </tr>

      <tr>
        <td height="1" colspan="2" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="2" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/online/sstitle_003.gif"/></td>
        </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">차시</td>
        <td class="tit_table_right">강의주제</td>
        </tr>
<%	for (DataBox lessonListDataBox : lessonList) {	%>
      <tr>
        <td class="ct_tit_table"><%= lessonListDataBox.get("d_lesson")  %></td>
        <td class="ct_tit_table_lright"><%= lessonListDataBox.get("d_sdesc")  %></td>
      </tr>      
<%	} %>
      
      <tr>
        <td height="1" colspan="2" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="2" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    
<%	if (isSeq) { %>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/online/sstitle_004.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="4" bgcolor="#7692CB"></td>
      </tr>
<%	for(DataBox subjseqListDataBox : subjseqList) {	%>
      <tr>
        <td width="150" class="tit_table">수강신청 시작일</td>
        <td class="ct_tit_table_left"><%= subjseqListDataBox.getDate("d_propstart") %></td>
        <td width="150" class="tit_table">수강신청 종료일</td>
        <td class="ct_tit_table_lright"><%= subjseqListDataBox.getDate("d_propend") %></td>
      </tr>
      <tr>
        <td class="tit_table">교육시작일</td>
        <td class="ct_tit_table_left"><%= subjseqListDataBox.getDate("d_edustart") %></td>
        <td class="tit_table">교육종료일</td>
        <td class="ct_tit_table_lright"><%= subjseqListDataBox.getDate("d_eduend") %></td>
      </tr>
      <tr>
        <td class="tit_table">교육정원</td>
        <td class="ct_tit_table_left">		
				<%
				String slimit = "";
				if( subjseqListDataBox.getInt("d_studentlimit") == 0 ) {
					slimit = "제한없음";
				} else {
					slimit = subjseqListDataBox.getCommaInt("d_studentlimit") + " 명";
				}
				%>
				<%= slimit %>		
		</td>
        <td class="tit_table">교육비</td>
        <td class="ct_tit_table_lright">
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
        <td class="tit_table">교육문의</td>
        <td colspan="3" class="ct_tit_table_lright">
        	온라인 교육 운영 담당자 : 02-2161-0077 academy@kocca.kr</td>
        </tr>

      <tr>
        <td height="1" colspan="4" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="4" bgcolor="F7F7F7"></td>
      </tr>
      <%} %>
    </table>
    <%{ 
    	DataBox subjseqListDataBox = subjseqList.get(subjseqList.size()-1);	%>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/online/sstitle_005.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">수료기준</td>
        <td class="tit_table">총점</td>
        <td class="tit_table_right">진도율</td>
      </tr>
      <tr>
        <td class="ct_tit_table"> 점수</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_gradscore") %></td>
        <td class="ct_tit_table_right"><%= subjseqListDataBox.get("d_gradstep") %>% 이상</td>
      </tr>

      <tr>
        <td height="1" colspan="3" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="3" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="40"><img src="/images/portal/homepage_renewal/common/icon_blue.jpg"/></td>
        <td><img src="/images/portal/homepage_renewal/online/sstitle_006.gif"/></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">평가기준</td>
        <td class="tit_table">진도율  </td>
        <td class="tit_table">중간평가 </td>
        <td class="tit_table">최종평가 </td>
        <td class="tit_table">형성평가 </td>
        <td class="tit_table">레포트 </td>
        <td class="tit_table">참여도</td>
        <td class="tit_table_right">기타</td>
      </tr>
      <tr>
        <td class="ct_tit_table">가중치(%) </td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_wstep") %>%</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_wmtest") %>%</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_wftest") %>%</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_whtest") %>%</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_wreport") %>%</td>
        <td class="ct_tit_table"><%= subjseqListDataBox.get("d_wetc1") %>%</td>
        <td class="ct_tit_table_right"><%= subjseqListDataBox.get("d_wetc2") %>%</td>
      </tr>
      <tr>
        <td height="1" colspan="8" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="8" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <%} } %>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <%	if (isSeq && "Y".equals(sugang_YN)) { %>        
			<td width="528" class="h_road">
				<a href="javascript:whenSubjPropose(
									'<%=box.get("p_subj")%>',
									'<%=box.get("p_year")%>',
									'<%=box.get("p_subjseq")%>',
									'<%=box.get("p_subjnm")%>');" class="btn_red">
					<img src="/images/portal/homepage_renewal/online/btn_sct.jpg" width="87" height="25" />
				</a>
			</td>		
		<%} %>
        <td width="98" class="h_road">
        	<a href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl")%>','<%= box.get("p_subj")%>', '<%= box.get("p_wj_classkey")%>' )" class="btn_gr">
        		<img src="/images/portal/homepage_renewal/online/btn_study_view.jpg" width="98" height="25" />
        	</a>
        </td>
        <td width="46" class="h_road">
        	<a href="javascript:whenSubjList();" class="btn_gr">
        		<img src="/images/portal/homepage_renewal/online/btn_list.jpg" width="46" height="25" />
        	</a>
        </td>
      </tr>
    </table>
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
	
	<%}else{ %>	

<% if      ("C01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit01.gif" alt="기획"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>기획</strong></p></h2><% }
   else if ("C02".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit02.gif" alt="제작"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>제작</strong></p></h2><% }
   else if ("C03".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit03.gif" alt="유통"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>유통</strong></p></h2><% }
   else if ("C04".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit04.gif" alt="경영지원" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>경영지원</strong></p></h2><% }
   else if ("C05".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit05.gif" alt="특강"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>특강</strong></p></h2><% }
   else if ("B01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit1.gif" alt="방송영상" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>방송영상</strong></p></h2><% }
   else if ("G01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit2.gif" alt="게임개발" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>게임개발</strong></p></h2><% }
   else if ("K01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit3.gif" alt="문화콘텐츠" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>문화콘텐츠</strong></p></h2><% } 
%>

			<h3 class="mg_tn">
				<img src="/images/portal/common/stit_corseintro.gif" alt="과정소개" class="tit" />
				<p class="btn">
<%	if (isSeq && "Y".equals(sugang_YN)) { %>				<font id="existpropose1"><a href="javascript:whenSubjPropose(
									'<%=box.get("p_subj")%>',
									'<%=box.get("p_year")%>',
									'<%=box.get("p_subjseq")%>',
									'<%=box.get("p_subjnm")%>');" class="btn_red"><span>수강신청</span></a></font>	<%	} %>
				<a href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl")%>','<%= box.get("p_subj")%>', '<%= box.get("p_wj_classkey")%>' )" class="btn_gr"><span>강좌 맛보기</span></a>
                    <% if(grcode.equals("N000001")) { %>
				<a href="javascript:fnInterestInsert('<%=box.get("p_subj")%>','<%=box.get("p_subjnm")%>')" class="btn_gr"><span>관심과정담기</span></a>
                    <%}%>
				<a href="javascript:whenSubjList();" class="btn_gr"><span>목록</span></a></p></h3>
			<table class="view">
			<colgroup><col width="20%" /><col width="80%" /></colgroup>
			<tr>
				<th>과정명</th>
				<td class="end"><b>&lt;&lt;<%= subjectPreview.get("d_areaname") %>&gt;&gt;</b>&nbsp;&nbsp;<%= subjectPreview.getString("d_subjnm") %></td>
			</tr>
			<tr>
				<th>분류</th>
				<td class="end"><%= subjectPreview.getString("d_lowerclassnm") %></td>
			</tr>
			<tr>
				<th>분야</th>
				<td class="end"><%= subjectPreview.getString("d_middleclassnm") %> <a href="javascript:$('#s_mclassnm').val('<%=subjectPreview.getString("d_middleclass")%>');whenSubjList();" class="board_btn2"><span class="arrow">관련과정보기</span></a></td>
			</tr>
			<tr>
				<th>과정유형</th>
				<td class="end"><%= subjectPreview.getString("d_subjtypenm") %></td>
			</tr>
<!--			<tr>
				<th>수강료</th>
				<td class="end"><%= subjectPreview.getString("d_biyong") %></td>
			</tr>  -->
			<tr>
				<th>교육대상</th>
				<td class="end"><%= subjectPreview.getHtmlString("d_edumans") %></td>
			</tr>
			<tr>
				<th>교육목적</th>
				<td class="end"><%= subjectPreview.getHtmlString("d_intro") %></td>
			</tr>
			<tr>
				<th>강의교재/참고</th>
				<td class="end"><%= subjectPreview.getHtmlString("d_memo") %></td>
			</tr>
			<tr>
				<th>선수과목</th>
				<td class="end">
	<%  if (listPre == null || listPre.size()==0) {
			out.println("선수과목이 없습니다.");
		} else {
			for(int p = 0; p < listPre.size(); p++) {
					DataBox dboxp = (DataBox)listPre.get(p);
					String v_subjnmp = dboxp.getString("d_subjnm");
					out.println(v_subjnmp);
					out.println("<br>");
			}
		}
	%>
				</td>
			</tr>
			<tr>
				<th>후수과목</th>
				<td class="end">
	<%  if (listNext == null || listNext.size()==0) {
			out.println("후수과목이 없습니다.");
		} else {
			for(int n = 0; n < listNext.size(); n++) {
					DataBox dboxn = (DataBox)listNext.get(n);
					String v_subjnmn = dboxn.getString("d_subjnm");
					out.println(v_subjnmn);
					out.println("<br>");
			}
		}
	%>
				</td>
			</tr>
			</table>

			<h3><img src="/images/portal/common/stit_educon.gif" alt="교육안내" /></h3>
			<table class="view">
			<colgroup><col width="20%" /><col width="80%" /></colgroup>
			<tr>
				<td class="end"><%= subjectPreview.getHtmlString("d_explain") %></td>
			</tr>
			</table>

			<h3><img src="/images/portal/common/stit_nxtinfo.gif" alt="차시정보" /></h3>
			<table class="list">
			<colgroup><col width="10%" /><col width="70%" /><col width="20%" /></colgroup>
			<thead>
			<tr>
				<th>차시</th>
				<th>강의주제</th>
				<th class="end">교수</th>
			</tr>
			</thead>
			<tbody>
<%	for (DataBox lessonListDataBox : lessonList) {	%>
			<tr>
				<td><%= lessonListDataBox.get("d_lesson")  %></td>
				<td class="ta_l"><%= lessonListDataBox.get("d_sdesc")  %></td>
				<td class="ta_l end"><%= lessonListDataBox.get("d_name")  %></td>
			</tr>
<%	} %>
			</tbody>
			</table>

<%	if (isSeq) { %>	
			<h3><img src="/images/portal/common/stit_classinfo.gif" alt="수강안내" /></h3>
			<table class="view">
			<colgroup><col width="20%" /><col width="30%" /><col width="20%" /><col width="30%" /></colgroup>
<%	for(DataBox subjseqListDataBox : subjseqList) {	%>
			<tr>
				<th>수강신청 시작일</th>
				<td><%= subjseqListDataBox.getDate("d_propstart") %></td>
				<th>수강신청 종료일</th>
				<td class="end"><%= subjseqListDataBox.getDate("d_propend") %></td>
			</tr>
			<tr>
				<th>교육시작일</th>
				<td><%= subjseqListDataBox.getDate("d_edustart") %></td>
				<th>교육종료일</th>
				<td class="end"><%= subjseqListDataBox.getDate("d_eduend") %></td>
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
				<%= slimit %>
				</td>
				<th>교육비</th>
				<td class="end">
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
				<td colspan="3" class="end">
					온라인 교육 운영 담당자 : 
<%
	if (subjectPreview.getString("d_area").equals("B0")) out.println(" 02-6441-3258");
	/* else if (subjectPreview.getString("d_area").equals("G0")) out.println(" 02)3219-5720"); */
	else if (subjectPreview.getString("d_area").equals("G0")) out.println(" 02-6441-3258");
	else if (subjectPreview.getString("d_area").equals("K0")) out.println(" 02-6441-3258");
	else {
%>
					<%=subjseqListDataBox.getString("d_mastername")%><% if (subjseqListDataBox.getString("d_mastercomptel").length()>0) {  %> (<%=subjseqListDataBox.getString("d_mastercomptel")%>)<% } %><br/>
					<%=subjseqListDataBox.getString("d_masteremail")%>
<%	} %>
<%if(subjseqListDataBox.getBoolean("d_existpropose")) {%>
					<input type="hidden" name="d_existpropose" id="d_existpropose" value="1"/>
<%	}	%>
			  	</td>
			</tr>
<%	}	 %>
			</table>
<%	{
		DataBox subjseqListDataBox = subjseqList.get(subjseqList.size()-1);	%>
			<h3><img src="/images/portal/common/stit_completion.gif" alt="수료" /></h3>
			<table class="list">
			<thead>
			<tr>
				<th>수료기준</th>
				<th>총점</th>
				<th class="end">진도율</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>점수</td>
				<td><%= subjseqListDataBox.get("d_gradscore") %></td>
				<td class="end"><%= subjseqListDataBox.get("d_gradstep") %>% 이상</td>
			</tr>
			</tbody>
			</table>

			<h3><img src="/images/portal/common/stit_valuation.gif" alt="평가" /></h3>
			<table class="list">
			<thead>
			<tr>
				<th>평가기준</th>
				<th>진도율</th>
				<th>중간평가</th>
				<th>최종평가</th>
				<th>형성평가</th>
				<th>레포트</th>
				<th>참여도</th>
				<th class="end">기타</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>가중치(%)</td>
				<td><%= subjseqListDataBox.get("d_wstep") %>%</td>
				<td><%= subjseqListDataBox.get("d_wmtest") %>%</td>
				<td><%= subjseqListDataBox.get("d_wftest") %>%</td>
				<td><%= subjseqListDataBox.get("d_whtest") %>%</td>
				<td><%= subjseqListDataBox.get("d_wreport") %>%</td>
				<td><%= subjseqListDataBox.get("d_wetc1") %>%</td>
				<td class="end"><%= subjseqListDataBox.get("d_wetc2") %>%</td>
			</tr>
			</tbody>
			</table>
<%	}	}	 %>
			<h3><img src="/images/portal/common/stit_profeinfo.gif" alt="교수정보" class="tit" /><p class="btn">
				<a href="javascript:whenTutorShow();" class="board_btn2" id="show"><span class="arrow">교수전체보기</span></a>
				<a href="javascript:whenTutorHide();" class="board_btn2" id="hide" style="display:none"><span class="arrow">추가교수정보 닫기</span></a>
			</p></h3>
			<table class="view">
			<colgroup><col width="20%" /><col width="80%" /></colgroup>
<% boolean isFirst = true;
	for (DataBox tutorListBox : tutorList) { %>
			<tr id='tutorList<%= isFirst %>' style="display:<%= isFirst?"" : "none" %>">
				<th>교수정보</th>
				<td colspan="3" class="end">
					<dl class="professor">
						<dt><img src="<%= tutorListBox.get("d_phototerms") %>" alt="교수사진"  width="85" height="113" /></dt>
						<dd class="mg_t6"><span class="tit">이름 :</span><span class="con"><%= tutorListBox.get("d_name") %> </span></dd>
						<dd><span class="tit">소속 :</span><span class="con"><%= tutorListBox.get("d_comp") %></span></dd>
						<dd><span class="tit">소개 :</span><span class="con"><%= tutorListBox.getHtmlString("d_career") %></span></dd>
					</dl>
				</td>
			</tr>
<%	} %>
			</table>

			<p class="f_btn">
<% if (isSeq && "Y".equals(sugang_YN)) { %><font id="existpropose2"><a href="javascript:whenSubjPropose(
									'<%=box.get("p_subj")%>',
									'<%=box.get("p_year")%>',
									'<%=box.get("p_subjseq")%>',
									'<%=box.get("p_subjnm")%>');" class="btn_red"><span>수강신청</span></a></font><% } %>
									<a href="javascript:whenPreShow('<%= subjectPreview.get("d_preurl")%>','<%= box.get("p_subj")%>', '<%= box.get("p_wj_classkey")%>' )" class="btn_gr"><span>강좌 맛보기</span></a>
                                    <% if(grcode.equals("N000001")) { %>
									    <a href="javascript:fnInterestInsert('<%=box.get("p_subj")%>','<%=box.get("p_subjnm")%>')" class="btn_gr"><span>관심과정담기</span></a>
                                    <%}%>
									<a href="javascript:whenSubjList();" class="btn_gr"><span>목록</span></a></p>
									
	<%} %>
			</form>
<!-- form 끝 -->
<script>if($("#d_existpropose").val()==1)whenProposeHide();</script>

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->