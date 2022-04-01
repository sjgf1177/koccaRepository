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
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String p_specials = box.get("p_specials");
	String p_action = box.get("p_action");
	//String type = box.get("type");
	String v_upperclass = box.get("p_upperclass");
	if (box == null) box = RequestManager.getBox(request);
	List<DataBox> subjList = (List<DataBox>)request.getAttribute("SubjectList");		 // 과정 리스트
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
    String v_grcode=box.getSession("tem_grcode");
	String p_order = box.getString("p_order");
	
	String  v_creduURL		= "/contents/credu/start.html";
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
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
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, wj_classkey){
document.form1.p_subj.value = subj;
document.form1.p_subjnm.value = subjnm;
document.form1.p_iscourseYn.value = courseyn;
document.form1.p_upperclass.value = upperclass;
document.form1.p_upperclassnm.value = upperclassnm;
document.form1.p_year.value = year;
document.form1.p_subjseq.value = subjseq;
document.form1.p_wj_classkey.value = wj_classkey;
document.form1.p_process.value = 'SubjectPreviewPage';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
document.form1.target = "_self";
document.form1.submit();
}


var creduGateUrl;
var creduWin;
//맛보기
function whenPreShow(url, subj, d_preheight, d_prewidth, d_wj_classkey) {
             
	if(url.indexOf("ksp.credu.com") > 0 ){
		creduGateUrl = url;
		//open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
		creduWin = window.open("<%=v_creduURL%>", "creduPre", "top=100px,left=100px,width=1000px,height=600px");
	}else{
		
		if (d_wj_classkey != '') {    //교양(웅진패스원)
		  if (<%=v_grcode.equals("N000031") %>)  {
		      // 로그
		      prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
		
		      open_window("openShow","http://samplezone.campus21.co.kr/classpreview.asp?classkey="+d_wj_classkey,"100","100","720","400","","","",true,true);           
		  }
		  return;
		} else {
		      // 로그
		      prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
		
		      open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
		}
	}
		
	
}

//크레듀 제휴과정 링크정보 가져오기
function creduURL(){
	$.ajax({
            type : "post"
        ,   url : "/servlet/controller.contents.EduStart"
        ,   dataType : "json"
        ,   data : {
        	url : creduGateUrl,
        	p_process : "creduSubj"
        }
        ,   success : function(ajaxData) {
        		creduWin.loadswf(ajaxData.videoURL, 945, 540);
        	}
        ,   error :  function(arg1, arg2) {
                alert("오류가 발생하여 플레이 할 수 없습니다.");
            }
    });
}

//전체과정보기
function whenSubjectListAll() {
document.form1.p_process.value = 'SubjectListAll';
document.form1.p_rprocess.value = 'SubjectList';
document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
document.form1.submit();
}
//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, billYn, wj_classkey) {

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
	document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	document.form1.submit();
	document.form1.target = "_self";
}

//관심버튼 클릭
function fnInterestInsert(subj,year,subjseq,courseyn,subjnm) {

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
	else alert("관심과정 등록이 실패하였습니다.\n"+data.result);
}
	//페이지 이동
	function go(index) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = index;
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}
	//페이지 이동
	function selectArea(area) {
		$("#p_area").val(area);
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "REQUEST";
		document.form1.p_pageno.value = 0;
		document.form1.submit();
	}
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}
	function pagesize(pageSize) {
		document.form1.target = "_self";
		document.form1.p_pageno.value = 1;
		document.form1.p_pagesize.value = pageSize;
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}

	function goSort(tmp) {
        $("[name='p_order']").val(tmp);
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.homepage.AspMenuMainServlet" method="post">
	<input type="hidden" name="p_process"	value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value=""/>
	<input type="hidden" name="p_subjseq"	value=""/>
	<input type="hidden" name="p_subjnm"	value=""/>
	<input type="hidden" name="p_order"	value="<%=p_order %>"/>
	<input type="hidden" name="p_iscourseYn"	value=""/>
	<input type="hidden" name="p_upperclass"	value="<%=v_upperclass %>"/>
	<input type="hidden" name="p_upperclassnm"	value=""/>
	<input type="hidden" name="p_action"	value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"  value="<%=v_pagesize%>"/>
	<input type="hidden" name="p_area" id="p_area"	value="<%= box.get("p_area") %>"/>
    <input type="hidden" name="p_wj_classkey" value=""/>

	<% // 각 upperclass 에 따라 이미지명 수작업  해주어야 함. ex) /경로/<%=v_upperclass+".gif"  %>
<% if      ("C01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit01.gif" alt="기획"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>기획</strong></p></h2><% }
   else if ("C02".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit02.gif" alt="제작"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>제작</strong></p></h2><% }
   else if ("C03".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit03.gif" alt="유통"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>유통</strong></p></h2><% }
   else if ("C04".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit04.gif" alt="경영지원" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>경영지원</strong></p></h2><% }
   else if ("C05".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit05.gif" alt="특강"     class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>특강</strong></p></h2><% }
   else if ("B01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit1.gif" alt="방송영상" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>방송영상</strong></p></h2><% }
   else if ("G01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit2.gif" alt="게임개발" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>게임개발</strong></p></h2><% }
   else if ("K01".equals(v_upperclass)) { %> <h2><img src="/images/portal/online/h2_tit3.gif" alt="문화콘텐츠" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>문화콘텐츠</strong></p></h2><% }
%>
            <ul class="tabwrap">
                <li>※ 교육기간,수강료를 클릭하시면 오름차순으로 정렬이 됩니다.</li>
            </ul>
            <ul class="tabwrap">
                <li style="color: red">※ 과정 학습 시에는 인터넷 익스플로러 사용을 권장합니다.</li>
            </ul>
            <br>
			<ul class="tabwrap">
				<li><a href="javascript:selectArea('');" class="tab_online<%= box.get("p_area").equals("") ? " tab_online_on" : "" %>"><span>전체</span></a></li>           
				<li><a href="javascript:selectArea('B0');" class="tab_online<%= box.get("p_area").equals("B0") ? " tab_online_on" : "" %>"><span>방송영상</span></a></li>
				<li><a href="javascript:selectArea('G0');" class="tab_online<%= box.get("p_area").equals("G0") ? " tab_online_on" : "" %>"><span>게임개발</span></a></li>                
				<li><a href="javascript:selectArea('K0');" class="tab_online<%= box.get("p_area").equals("K0") ? " tab_online_on" : "" %>"><span>문화콘텐츠</span></a></li>
				
                <% if(v_grcode.equals("N000031")) {%>
                <li><a href="javascript:selectArea('W0');" class="tab_online<%= box.get("p_area").equals("W0") ? " tab_online_on" : "" %>"><span>교양</span></a></li>
                <%}%>
			</ul>

			<table class="row_list">
			<thead>
			<tr>
				<th>
					<kocca_select:select name="s_mclassnm" sqlNum="course.0003"  param='<%= v_upperclass %>'
					onChange='go(1);' attr=" 분야" selectedValue='<%= box.get("s_mclassnm") %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
				<th>
					<kocca_select:select name="s_sclowerclass" sqlNum="course.0004"  param='<%= v_upperclass %>' param2='<%= box.get("s_mclassnm").trim().length()==0 ? "ALL" : box.get("s_mclassnm")%>'
					onChange="go(1);" attr=" 분류" selectedValue='<%= box.get("s_sclowerclass") %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
				<th>
					과정명
				</th>
				<th><a href="javascript:goSort('date')">교육기간<br /><span class="stit">(수강신청기간)</span></a></th>
				<th>수강신청</th>
			</tr>
			</thead>
			<tbody>
<%	for(DataBox dbox : subjList) {
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
			<tr>
				<td class="ta_l"><%=dbox.get("d_mclassnm")%></td>
				<%--<td><%= dbox.get("d_sclowerclass").equals("001") ? "콘텐츠기획" : dbox.get("d_sclowerclass").equals("002") ? "게임기획" : "방송영상기획"%></td>--%>
                <td><%= dbox.get("d_lclassnm")%></td>
				<td class="ta_l">
					<% if(dbox.get("d_isunit").equals("Y")) {%><img src="/images/portal/ico/ico_box_witness.gif" alt="인증" /> <% } %>
					<% if(dbox.get("d_ishit").equals("Y")) {%><img src="/images/portal/ico/ico_box_popular.gif" alt="인기" /> <% } %>
					<% if(dbox.get("d_isnew").equals("Y")) {%><img src="/images/portal/ico/ico_box_new.gif" alt="신규" /> <% } %>
					<% if(dbox.get("d_isrecom").equals("Y")) {%><img src="/images/portal/ico/ico_box_recom.gif" alt="추천" /> <% } %>
					<!-- 관심과정담기가 필요시 다시 복구<a href="javascript:fnInterestInsert(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_isonoff")%>',
									'<%=dbox.get("d_scupperclass")%>',
									'<%=dbox.get("d_uclassnm")%>')"
									class="board_btn"><span class="pd_l6 pd_r5">관심과정담기</span></a> -->
									<br />
					<b>&lt;&lt;<%= dbox.get("d_areaname") %>&gt;&gt;</<%= dbox.get("d_areaname") %>></b><a href="javascript:whenSubjInfo(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_isonoff")%>',
									'<%=dbox.get("d_scupperclass")%>',
									'<%=dbox.get("d_uclassnm")%>',
									'<%=dbox.get("d_scyear")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_wj_classkey")%>')"><%=dbox.get("d_subjnm")%></a><br />
					<span class="slist">교육정원: <%= slimit %></span></td>
				<td class="data"><%=dbox.get("d_edustart")%>~<%=dbox.get("d_eduend")%><br /><span class="sdata"><%=dbox.get("d_propstart")%>~<%=dbox.get("d_propend")%></span></td>
				<td class="end">
					<a href="javascript:whenPreShow('<%= dbox.get("d_preurl")%>'
											,'<%= dbox.get("d_subj")%>'
											, '<%= dbox.get("d_prewidth", "800") %>'
											,'<%= dbox.get("d_preheight", "600") %>'
											,'<%= dbox.get("d_wj_classkey")%>' )" class="board_btn"><span class="pd_l6 pd_r5">맛보기</span></a><br />

<%if(dbox.getBoolean("d_existpropose")) {%>신청완료<%	}	else { if( propyn.equals("Y") ) {%>
					<a href="javascript:whenSubjPropose(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_scyear")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_subjnm")%>',
						 		   	'<%= dbox.get("d_wj_classkey")%>')" class="btn_gr mg_t3"><span>신청</span></a><br/>
<%}	} %>
				</td>
			</tr>
<%	v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
		v_totalrowcount = dbox.getInt("d_totalrowcount");
		v_rowcount	  = dbox.getInt("d_rowcount");
	}	 %>

<%
	if( subjList.size() < 1) {
%>
		<tr>
			<td colspan="5" align="center">등록된 과정이 없습니다.</td>
		</tr>
<%
	}
%>
		</tbody>
			</table>
		<!-- total 시작 -->
		 <%= PageUtil.printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- total 끝 -->
			</form>
<!-- form 끝 -->
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
