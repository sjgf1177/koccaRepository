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

		var new_Open = "";

		if('${sessionScope.tem_grcode}' == "N000210"){
			new_Open = window.open("","proposeWindow",'scrollbars=yes,width=1000,height=800,resizable=no');
		}else{
			new_Open = window.open("","proposeWindow",'scrollbars=yes,width=1000,height=600,resizable=no');
		}

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

<section class="container d-flex">
	<div class=""></div>
	<div class="subContainer">
		<div class="sub_section">
			<div class="sub_contents_body">
				<div class="sub_board_header">
					<jsp:include page="/learn/user/typeB/include_left/left_9.jsp">
						<jsp:param value="${param.menuid }" name="left_active"/>
					</jsp:include>
					<div class="lnb_wrap">
						<ul class="lnb_con d-flex">
							<li <c:if test="${param.p_area eq '' || param.p_area eq null || param.p_area eq 'ALL'}"> class="on"</c:if>><a href="javascript:selectArea('');"><span>전체</span></a></li>
							<li <c:if test="${param.p_area eq 'B0' }"> class="on"</c:if>><a href="javascript:selectArea('B0');"><span>방송영상</span></a></li>
							<li <c:if test="${param.p_area eq 'G0' }"> class="on"</c:if>><a href="javascript:selectArea('G0');"><span>게임</span></a></li>
							<li <c:if test="${param.p_area eq 'K0' }"> class="on"</c:if>><a href="javascript:selectArea('K0');"><span>문화</span></a></li>
						</ul>
					</div>
					<div class="d-flex justify-content-lg-between">
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
								<div class="input-group mb-3">
									<input type="text" name="p_searchtext" id="p_searchtext" class="form-control input board_search" value="<c:out value="${param.p_searchtext }" />" title="검색어를 입력해주세요." placeholder="검색어를 입력해주세요." onkeypress="if(event.keyCode==13) {javascript:fnSelect()(); return false;}">
									<input type="button" class="btn btn-outline-secondary input btn_board_search" value="" onclick="javascript:fnSelect();">
								</div>
							</form>
						</div>
						<ul class="radio-row-box">
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">
								<label class="form-check-label" for="inlineRadio1">전체</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
								<label class="form-check-label" for="inlineRadio2">신청가능</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3">
								<label class="form-check-label" for="inlineRadio3">마감</label>
							</li>
						</ul>
					</div>
				</div>

				<div class="sub_thumb_body d-flex justify-content-between flex-wrap">
					<!-- 배너 영역 4개 과정 나온 후 밑에 배너위치 하기-->
					<div class="header-benner-box">
						<a href="" class="d-inblock"><img src="" class="img-fluid" alt="에듀코카 테마과정 바로가기 배너" ></a>
					</div>
					<c:if test="${fn:length(SubjectList) <= 0 }">
						<p class="text-center">
							등록된 열린강좌 내역이 없습니다.
						</p>
					</c:if>
					<c:forEach items="${SubjectList }" var="list" varStatus="status">
						<div class="thumb_box card">
							<a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');" class="thumb_imgBox">
								<img src="<c:out value="${list.d_introducefilenamenew }" />" alt="<c:out value="${list.d_subjnm }" />">
							</a>
							<div class="card-body thumb_con">
								<div class="thumb_top_tag">
									<c:if test="${list.d_ishit eq 'Y' }"><span class="tab_type_popular"></span></c:if>
									<c:if test="${list.d_isnew eq 'Y' }"><span class="tag_type_new"></span></c:if>
									<c:if test="${list.d_isrecom eq 'Y' }"><span class="tag_type_recommend"></span></c:if>
								</div>
								<a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');"  class="card-title">
									<span class="thumb_title textline2">
										<c:out value="${list.d_subjnm }" />
									</span>
								</a>
								<ul class="thumb_con_Info card-text">
									<li class="point_purple">
										<span>수강신청기간</span> : <c:out value="${list.d_edustart }" /> ~ <c:out value="${list.d_eduend }" /></li>
								</ul>
								<div class="thumb_button">
									<c:choose>
										<c:when test="${list.d_existpropose }">
											<a href="javascript:void(0);" class="applycomplete_btn">신청완료</a>
										</c:when>
										<c:otherwise>
											<c:if test="${list.d_propyn eq 'Y' }">
												<a href="javascript:whenSubjPropose('<c:out value="${list.d_subj }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_subjnm }" />');" class="apply_btn  btn btn-purple">신청</a>
											</c:if>
										</c:otherwise>
									</c:choose>
									<c:if test="${sessionScope.tem_grcode ne 'N000210'}">
										<c:if test="${list.d_preurl ne '' }">
											<a href="javascript:whenPreShow('<c:out value="${list.d_preurl }" />','<c:out value="${list.d_subj }" />','<c:out value="${list.d_prewidth }" />', '<c:out value="${list.d_preheight }" />', '<c:out value="${list.d_wj_classkey }" />');" class="gustation_btn btn btn-outline-secondary">맛보기</a>
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
</section>


<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>