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

	box.put("topmenu","4");
	String type = box.get("type");	
	String v_area = box.get("p_area");
	
	String sub_title_img ="";  // 서브이미지
	String sub_title_name =""; // 서브 이미지 설명
	String sub_title_navi =""; // 서브 이미지 설명
    
    String  v_today        =  FormatDate.getDate("yyyyMMddHH");
	
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
		box.put("submenu","0");
		sub_title_img = "/images/2012/sub/page3/title/offline_total.gif";
		sub_title_name = "오프라인과정 방송영상 분야";
		sub_title_navi = "전체";
	}
	
	//box.put("submenu","1");


	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	
    String p_menuid = box.get("menuid"); //방송 01,게임 02,기획 03
    String p_middleclass = box.get("s_mclassnm");  //산업계직무재교육 001,차세대인재양성교육 002, 수탁교육 003
	if (box == null) box = RequestManager.getBox(request);
	List<DataBox> subjList = (List<DataBox>)request.getAttribute("SubjectList");		 // 과정 리스트
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	
	System.out.println("jsp_gubun::::::::::::::::"+box.getSession("s_gubun"));
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script language="JavaScript">
<!--//
// 과정검색 엔터처리
function search_enter(e)  {
	if (e.keyCode =='13'){  subjSearch();  }
}

// 과정검색
function subjSearch() {
	document.form1.p_process.value = "SubjectList";
	document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, year, subjseq, seq){
document.form1.p_subj.value = subj;
document.form1.p_year.value = year;
document.form1.p_subjseq.value = subjseq;
document.form1.p_seq.value = seq;
document.form1.p_process.value = 'SubjectPreviewPage';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.off.ProposeOffServlet';
document.form1.target = "_self";
document.form1.submit();
}

//전체과정보기
function whenSubjectListAll() {
document.form1.p_process.value = 'SubjectListAll';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.off.ProposeOffServlet';
document.form1.submit();
}
//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, seq) {
	if(<%= box.getSession("userid").length()==0 %>) {
		alert("로그인이 필요합니다.");
		return;
	}
	if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
	 return;
	}
	var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
	document.form1.p_process.value = "SubjectEduProposePage";
	document.form1.target = "proposeWindow";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.p_seq.value = seq;
	document.form1.action = "/servlet/controller.off.ProposeOffServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//관심버튼 클릭
function fnInterestInsert(subj,year,subjseq,courseyn,subjnm) {

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
	else alert("관심과정 등록이 실패하였습니다.\n"+data.result);
}
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
	//페이지 이동
	function selectArea(area,mid) {
		//document.form1.type.value = area;
		document.form1.p_area.value = area;
		if( mid != '' ) {
			document.form1.menuid.value = mid;
		}
		//document.form1.s_mclassnm.value = "";
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "SubjectList";
		document.form1.p_pageno.value = 0;
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.p_process.value = "SubjectList";
		document.form1.submit();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->

<form name="form1" action="/servlet/controller.off.ProposeOffServlet" method="post">
	<input type="hidden" name="type"	value="<%=type%>"/>
	<input type="hidden" name="p_upperclass"	value="<%=type%>"/>
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value=""/>
	<input type="hidden" name="p_subjseq"	value=""/>
	<input type="hidden" name="p_seq"	value=""/>
	<input type="hidden" name="gubun" value="2">
	<input type="hidden" name="p_area" id="p_area"	value="<%= box.get("p_area") %>"/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
	<input type="hidden" name="menuid" value='<%= box.get("menuid") %>'>	
    <input type="hidden" name="p_topuserid"	value=""/>
    <input type="hidden" name="p_toppwd"	value=""/>
	
	<%
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
				<li class="insert"><input type="text" name="s_subjnm" tabindex="171" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:subjSearch();" tabindex="172" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></li>
			</ul>
			<ul class="pagetabC">
				<li><a href="javascript:selectArea('004','');" tabindex="173"  title="3D 입체콘텐츠 전문인력양성 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/3dsolid_<%=sub1_toggle%>.png" alt="3D 입체콘텐츠 전문인력양성"></a></li>
				<li><a href="javascript:selectArea('001','');" tabindex="174" title="산업계 직무 재교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/industry_<%=sub2_toggle%>.png" alt="산업계 직무 재교육"></a></li>
				<li><a href="javascript:selectArea('002','');" tabindex="175" title="차세대 인재양성교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/talent_<%=sub3_toggle%>.png" alt="차세대 인재양성교육"></a></li>
<!--				<li><a href="javascript:selectArea('003','');" tabindex="176" title="수탁교육 부문을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/brokerage_<%=sub4_toggle%>.png" alt="수탁교육"></a></li>-->
			</ul>
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0" summary="오프라인 과정 목록입니다">
					<colgroup><col width="120"><col width="*"><col width="80"><col width="140"><col width="140"><col width="100"></colgroup>
					<tr class="listhead">
						<th>분야</th>
						<th>과정명</th>
						<th>교육비</th>
						<th>신청기간</th>
						<th>교육기간</th>
						<th>수강신청</th>
					</tr>
					<%
					int i = 0;
					for(DataBox dbox : subjList) { 
		    		String biyong = "";
		    		String slimit = "";
		    		String propyn = dbox.getString("d_propyn");
		    		if( dbox.getInt("d_biyong") == 0 ) {
		    			biyong = "무료";
		    		} else {
		    			biyong = dbox.getCommaInt("d_biyong") + "원";
		    		}
		    		if( dbox.getInt("d_studentlimit") == 0 ) {
		    			slimit = "제한없음";
		    		} else {
		    			slimit = dbox.getCommaInt("d_studentlimit") + "명";
		    		}
					%>
		
					<tr class="listmiddle" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td class="linefirst">
						<%=dbox.get("d_areaname") %></td>
						<td class="title" id="bboardfocus<%=i%>">
						<a href="javascript:whenSubjInfo(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_seq")%>')" onfocus="boardfocus('b',<%=i%>)" onblur="boardfocus('b',<%=i%>)" tabindex="<%="2"+i+"1"%>" title="[<%=dbox.getDate("d_edustart", ".").substring(0,10)%>~<%=dbox.getDate("d_eduend", ".").substring(0,10)%>] <%=dbox.get("d_subjnm")%>을(를) 소개합니다"><%=dbox.get("d_subjnm")%></a>
						</td>
						<td><%= biyong %></td>
						<td><%=dbox.getDate("d_propstart", ".").substring(0,10)%>~<%=dbox.getDate("d_propend", ".").substring(0,10)%></td>
						<td><%=dbox.getDate("d_edustart", ".").substring(0,10)%>~<%=dbox.getDate("d_eduend", ".").substring(0,10)%></td>
						<td class="learnactive">
						<%if(dbox.get("d_me_sukang_yn").equals("Y")) {%>신청완료<%	} else if( dbox.get("d_isprop").equals("Y") ) { %>
						<button type="button" tabindex="<%="2"+i+"2"%>" onClick="javascript:whenSubjPropose(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_year")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_seq")%>')" class="btn_nomal_darkmint" title="[<%=dbox.get("d_subjnm")%>]과정을 신청합니다">수강신청</button>
						
						<% } else if (StringManager.toInt(dbox.get("d_propstart")) >= StringManager.toInt(v_today)){ %> <button type="button" tabindex="<%="2"+i+"3"%>" class="btn_nomal_darkmint" title="신청전" onClick="alert('수강신청 기간이 아닙니다.')">신청대기</button>
						<% } else { %> <button type="button" tabindex="<%="2"+i+"3"%>" class="btn_nomal_darkgray" title="마감">마감</button> <% } i++; %>
						</td>
					</tr>
					<%	
						v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
						v_totalrowcount = dbox.getInt("d_totalrowcount");
						v_rowcount	  = dbox.getInt("d_rowcount");
					}	
					if( subjList.size() < 1) { 
					%>
						<tr><td colspan="5" align="center" class="linefirst">등록된 과정이 없습니다.</td></tr>
					<% } %>
				</table>
			</div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
