<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
//페이지 이동
function goPage(pageNum) {
	document.form1.p_action.value = "go";
	document.form1.p_pageno.value = pageNum;
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

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm) {
	if('${sessionScope.userid}' == '') {
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

//맛보기
function whenPreShow(url, subj, width, height, d_wj_classkey) {
	if(width == '' || width == 0 || width == null){
		width = 800;
	}
	if(height == '' || height == 0 || height == null){
		height = 600;
	}
	prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
	open_window("openShow",url,"100","100",width,height,"","","",true,true);
}

function fnSelect(){
	document.form1.action="/servlet/controller.homepage.AspMenuMainServlet";
    document.form1.p_process.value = "REQUEST";
    document.form1.p_pageno.value = "1";
    document.form1.submit();
}
</script>

<c:set var="pageno" value="${param.p_pageno eq null || param.p_pageno eq ''? 1 : param.p_pageno}" />
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}" />

<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_9.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>수강신청</span>
                                <div class="linemap_wrap">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <span>
                                                    <img src="/common/image/home_icon.png" alt="메인">
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#"><span>수강신청</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>수강신청</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_board_header">
                                    <div class="board_search_box">
                                    	<form name="form1" action="/servlet/controller.homepage.AspMenuMainServlet" method="post">
											<input type="hidden" name="p_process"	value="<c:out value="${param.p_process }" />"/>
											<input type="hidden" name="p_rprocess"	value=""/>
											<input type="hidden" name="p_year"	value=""/>
											<input type="hidden" name="p_subj"	value=""/>
											<input type="hidden" name="p_subjseq"	value=""/>
											<input type="hidden" name="p_subjnm"	value=""/>
											<input type="hidden" name="p_order"	value="<c:out value="${param.p_order }" />"/>
											<input type="hidden" name="p_iscourseYn"	value=""/>
											<input type="hidden" name="p_upperclass"	value="<c:out value="${param.p_upperclass }" />"/>
											<input type="hidden" name="p_upperclassnm"	value=""/>
											<input type="hidden" name="p_action"	value="<c:out value="${param.p_action }" />"/>
											<input type="hidden" name="p_pageno"	value="<c:out value="${param.p_pageno }" />"/>
											<input type="hidden" name="p_pagesize"  value="<c:out value="${param.p_pagesize }" />"/>
											<input type="hidden" name="p_area" id="p_area"	value="<c:out value="${param.p_area }" />"/>
											<input type="hidden" name="p_wj_classkey" value=""/>
											<input type="hidden" name="gubun" value="${param.gubun }" />
										    <input type="hidden" name="menuid" value="${param.menuid }" />
											<input type="text" name="p_searchtext" id="p_searchtext" class="board_search" value="<c:out value="${param.p_searchtext }" />" title="검색어를 입력해주세요" onkeypress="if(event.keyCode==13) {javascript:fnSelect()(); return false;}">
											<input type="button" class="btn_board_search" value="" onclick="javascript:fnSelect();">
										</form>
	                                </div>
                                </div>
                                <div class="tab_style_con">
                                    <ul class="tab_style_1 four">
                                    	<li <c:if test="${param.p_area eq '' || param.p_area eq null || param.p_area eq 'ALL'}"> class="active"</c:if>><a href="javascript:selectArea('');"><span>전체</span></a></li>
                                        <li <c:if test="${param.p_area eq 'B0' }"> class="active"</c:if>><a href="javascript:selectArea('B0');"><span>방송영상</span></a></li>
                                        <li <c:if test="${param.p_area eq 'G0' }"> class="active"</c:if>><a href="javascript:selectArea('G0');"><span>게임</span></a></li>
                                        <li <c:if test="${param.p_area eq 'K0' }"> class="active"</c:if>><a href="javascript:selectArea('K0');"><span>문화</span></a></li>
                                    </ul>
                                </div>
                                <div class="sub_thumb_body">
                                	<c:if test="${fn:length(SubjectList) <= 0 }">
                                		<div class="thumb_box" style="text-align: center;">
                                			<span class="thumb_title">
                              				신청가능한 과정이 없습니다.
                              				</span>
                                		</div>
                                	</c:if>
                                	<c:forEach items="${SubjectList }" var="list" varStatus="status">
                                		<div class="thumb_box">
	                                        <div class="thumb_imgBox">
	                                            <img src="<c:out value="${list.d_introducefilenamenew }" />" alt="<c:out value="${list.d_subjnm }" />">
	                                        </div>
	                                        <div class="thumb_con">
	                                            <div class="thumb_top_tag">
	                                            	<c:if test="${list.d_ishit eq 'Y' }"><span class="tab_type_popular"></span></c:if>
	                                            	<c:if test="${list.d_isnew eq 'Y' }"><span class="tag_type_new"></span></c:if>
	                                            	<c:if test="${list.d_isrecom eq 'Y' }"><span class="tag_type_recommend"></span></c:if>
	                                            </div>
	                                            <a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');">
	                                            	<span class="thumb_title">
	                                            		<c:out value="${list.d_subjnm }" />
	                                            	</span>
	                                            </a>
	                                            <ul class="thumb_con_Info">
	                                                <li class="blue">
	                                                	<span>교육기간</span> : <c:out value="${list.d_edustart }" /> ~ <c:out value="${list.d_eduend }" /></li>
	                                                <li><span>수강신청기간</span> : <c:out value="${list.d_propstart }" /> ~ <c:out value="${list.d_propend }" /></li>
	                                            </ul>
	                                            <div class="thumb_button">
	                                            	<c:choose>
	                                            		<c:when test="${list.d_existpropose }">
	                                            			<a href="javascript:void(0);" class="applycomplete_btn">신청완료</a>
	                                            		</c:when>
	                                            		<c:otherwise>
	                                            			<c:if test="${list.d_propyn eq 'Y' }">
				                                                <a href="javascript:whenSubjPropose('<c:out value="${list.d_subj }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_subjnm }" />');" class="apply_btn">신청</a>
	                                            			</c:if>
	                                            		</c:otherwise>
	                                            	</c:choose>
	                                            	<c:if test="${sessionScope.tem_grcode ne 'N000210'}">
		                                            	<c:if test="${list.d_preurl ne '' }">
		                                                	<a href="javascript:whenPreShow('<c:out value="${list.d_preurl }" />','<c:out value="${list.d_subj }" />','<c:out value="${list.d_prewidth }" />', '<c:out value="${list.d_preheight }" />', '<c:out value="${list.d_wj_classkey }" />');" class="gustation_btn">맛보기</a>
		                                                </c:if>
	                                                </c:if>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <c:set var="totalpage" value="${list.d_totalpage }" />
                                	</c:forEach>
                                </div>
                                ${pu:typeB_printPageListDiv(totalpage, pageno, pagesize) }
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>