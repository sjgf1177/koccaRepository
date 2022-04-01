<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.ClassifySubjectBean" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String p_specials = box.get("s_specials");
	String p_action = box.get("p_action");
	
	String v_upperclass = box.get("p_upperclass");
	String v_lclassnm = box.getString("s_lclassnm");
	String v_mclassnm = box.getString("s_mclassnm");
	
	
	if (box == null) box = RequestManager.getBox(request);
	
	List<DataBox> subjList = (List)request.getAttribute("totalSubjectList");		 // 과정 리스트
	
	int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0)  v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_pagesize = box.getInt("p_pagesize");
	int	 v_totalrowcount =  0;
	
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperMap   = ClassifySubjectBean.getMenuId(box);
    
    String  v_creduURL		= "/contents/credu/start.html";
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<!-- 스크립트영역 -->
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--//

// 과정검색
function subjSearch() {
	document.form1.p_process.value = "TotalSubjectList";
	document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, menuid){
document.form1.p_subj.value = subj;
document.form1.p_subjnm.value = subjnm;
document.form1.p_iscourseYn.value = courseyn;
document.form1.p_upperclass.value = upperclass;
document.form1.p_upperclassnm.value = upperclassnm;
document.form1.p_year.value = year;
document.form1.p_subjseq.value = subjseq;
document.form1.menuid.value = menuid;
document.form1.p_process.value = 'SubjectPreviewPage';
document.form1.p_rprocess.value = 'TotalSubjectList';
document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
document.form1.target = "_self";
document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, billYn) {

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

	
	var creduGateUrl;
	var creduWin;
    //맛보기
    function whenPreShow(url, subj, d_preheight, d_prewidth) {
    	
    	if(url.indexOf("ksp.credu.com") > 0 ){
    		creduGateUrl = url;
    		//open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
    		creduWin = window.open("<%=v_creduURL%>", "creduPre", "top=100px,left=100px,width=1000px,height=600px");
    	}else{
	        // 로그
	        prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
	
	        open_window("openShow",url,"100","100",d_preheight,d_prewidth,"","","",true,true);
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
    

    function goSort(tmp) {
        $("[name='p_order']").val(tmp);
		document.form1.p_process.value = "TotalSubjectList";
		document.form1.submit();
	}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
	<input type="hidden" name="p_process"	    value="<%=box.get("p_process")%>"/>
	<input type="hidden" name="p_rprocess"	    value=""/>
	<input type="hidden" name="p_year"	        value=""/>
	<input type="hidden" name="p_subj"	        value=""/>
	<input type="hidden" name="p_subjseq"	    value=""/>
	<input type="hidden" name="p_subjnm"	    value=""/>
	<input type="hidden" name="p_iscourseYn"	value=""/>
	<input type="hidden" name="p_upperclass"	value=""/>
	<input type="hidden" name="p_upperclassnm"	value=""/>
	<input type="hidden" name="p_action"	    value="<%=p_action%>"/>
	<input type="hidden" name="p_pageno"	    value="<%=v_pageno%>"/>
	<input type="hidden" name="p_pagesize"      value="<%=v_pagesize%>"/>
	<input type="hidden" name="menuid"          value=""/>
    <input type="hidden" name="p_order"          value=""/>
	
	
		<h2><img src="/images/portal/common/h2_search.gif" alt="과정통합검색" class="fl_l" /><p class="category">Home &gt; 온라인과정 &gt; <strong>과정통합검색</strong></p></h2>

			<%--<p class="board_comment"><strong>[안내]</strong> 과정들은  KOCCA 인증 수료증 발급과정, 인기과정, 신규과정, 추천과정으로 구분됩니다.</p>--%>

			<p class="searchbox">
				<%--<img src="/images/portal/board/serbox_category.gif" style="padding:0 5px 0 0"> <kocca_select:select name="s_specials" sqlNum="specials"  param=" "  styleClass="mg_r12"--%>
	<%--onChange="" attr=" " selectedValue="<%= p_specials %>" isLoad="true" all="true" />--%>
				<img src="/images/portal/board/serbox_classtit.gif" alt="과정검색" class="mg_r7" />
				<input type="text" name="s_subjnm" class="inbox" style="width:280px;" value="<%= box.get("s_subjnm") %>" onkeypress="javascript:fnKeyPressEnter(event, 'subjSearch');"/>
				<input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" />
			</p>
            <ul class="tabwrap">
                <li>※ 교육기간,수강료를 클릭하시면 오름차순으로 정렬이 됩니다.</li>
            </ul>
            <br>
			<table class="row_list">
			<thead>
			<tr>
				<th>
					<kocca_select:select name="s_lclassnm" sqlNum="course.0005"  param=' '
					onChange='go(1);' attr=" 대분류" selectedValue='<%= v_lclassnm %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
				<th>
					<kocca_select:select name="s_mclassnm" sqlNum="course.0003"  param='<%= v_lclassnm %>' 
					onChange="go(1);" attr=" 중분류" selectedValue='<%= v_mclassnm %>' isLoad="true" all="true" type="3" styleClass=""/>
				</th>
				<th>과정명</th>
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
				<td class="ta_l"><%=dbox.get("d_uclassnm")%></td>
				<td><%=dbox.get("d_mclassnm")%></td>
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
					<a href="javascript:whenSubjInfo(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_subjnm")%>',
									'<%=dbox.get("d_isonoff")%>',
									'<%=dbox.get("d_scupperclass")%>',
									'<%=dbox.get("d_uclassnm")%>',
									'<%=dbox.get("d_scyear")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=upperMap !=null && upperMap.get(v_upperclass) != null ?  upperMap.get(v_upperclass) : "00" %>')"><%=dbox.get("d_subjnm")%></a><br />
					<span class="slist"><a href="javascript:goSort('biyong')">수강료:</a> <%= biyong %></span><span class="slist">교육정원: <%= slimit %></span></td>
				<td class="data"><%=dbox.get("d_edustart")%>~<%=dbox.get("d_eduend")%><br /><span class="sdata"><%=dbox.get("d_propstart")%>~<%=dbox.get("d_propend")%></span></td>
				<td class="end">
					<a href="javascript:whenPreShow('<%= dbox.get("d_preurl")%>'
											,'<%= dbox.get("d_subj")%>'
											, '<%= dbox.get("d_prewidth", "1012") %>'
											,'<%= dbox.get("d_preheight", "677") %>' )" class="board_btn"><span class="pd_l6 pd_r5">맛보기</span></a><br />
<%if(dbox.getBoolean("d_existpropose")) {%>신청완료<%	}	else { if( propyn.equals("Y") ) {%>                    
					<a href="javascript:whenSubjPropose(
									'<%=dbox.get("d_subj")%>',
									'<%=dbox.get("d_scyear")%>',
									'<%=dbox.get("d_subjseq")%>',
									'<%=dbox.get("d_subjnm")%>')" class="btn_gr mg_t3"><span>신청</span></a><br/>
<%}	} %>
				</td>
			</tr>
<%	v_totalpage	 = dbox.getInt("d_totalpage");// for 루프 안에 삽입
		v_totalrowcount = dbox.getInt("d_totalrowcount");
		v_rowcount	  = dbox.getInt("d_rowcount");
	}	 %>
			</tbody>
			</table>
<%
%>
		<!-- total 시작 -->
		 <%= PageUtil.printPageSizeListDiv(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		<!-- total 끝 -->
			</form>
<!-- form 끝 -->
<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->
