<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp"/>
<c:set var="pageno" value="${param.p_pageno eq null || param.p_pageno eq ''? 1 : param.p_pageno}"/>
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}"/>

<script type="text/javascript">
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_pageno.value = pageNum;
		document.form1.action = "/servlet/controller.study.MyClassServlet";
		document.form1.p_process.value = "EducationStudyingSubjectPage";
		document.form1.submit();
	}

	//학습현황 OPEN
	function whenEdulist(iurl, iseduend, url) {
		var ulink = url + "&p_iurl=" + iurl + "&p_iseduend=" + iseduend;
		var new_Open = window.open(ulink, "eduList", 'scrollbars=no,width=350,height=240,scrollbars=yes,resizable=no');
	}

	//학습창 OPEN
	function studyOpen(url, iurl) {
		<c:if test="${sessionScope.gadmin eq 'ZZ'}" >
		document.form1.lessonReurl.value = url;
		document.form1.lessonReiurl.value = iurl;

		var s_url = url + "&p_iurl=" + iurl;
		//alert(s_url);
		if (url == '') {
			alert("정보가 없습니다");
		} else {
			if (iurl == 0) {
				open_window('openApp', s_url, 0, 0, 1024, 768, false, false, false, true, true);
			} else {
				open_window('openApp', s_url, 0, 0, 1000, 700, false, false, false, 'yes', 'yes');
			}
		}
		</c:if>
		<c:if test="${sessionScope.gadmin ne 'ZZ'}" >
		alert("학습자 권한이 아닙니다.");
		</c:if>
	}

	//학습창 OPEN (외부강좌 : 웅진패스원)
	function studyOpen2(url, iurl, wj_classkey, edustart) {
		<c:if test="${sessionScope.gadmin eq 'ZZ'}" >
		document.form1.lessonReurl.value = url;
		document.form1.lessonReiurl.value = iurl;
		var s_url = url + "&p_iurl=" + iurl;

		if (url == '') {
			alert("정보가 없습니다");
		} else {
			s_url = "http://kocca.campus21.co.kr/enterclass.asp?userid=${sessionScope.userid}&classkey=" + wj_classkey + "&edustart=" + edustart;
			open_window('openApp', s_url, 0, 0, 1024, 768, false, false, 'yes', 'yes', 'yes');
		}
		</c:if>
		<c:if test="${sessionScope.gadmin ne 'ZZ'}" >
		alert("학습자 권한이 아닙니다.");
		</c:if>
	}

	//과정상세정보 POPUP
	function whenSubjInfoPopup(subj, subjnm, isonoff) {
		window.self.name = "SubjList";
		window.open("", "openSubjInfo", "scrollbars=no,width=720,height=800,scrollbars=yes,resizable=yes'");

		document.form1.target = "openSubjInfo"
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.form1.p_isonoff.value = isonoff;
		document.form1.p_process.value = 'SubjectPreviewPopup';
		document.form1.action = '/servlet/controller.propose.ProposeCourseServlet';
		document.form1.submit();

		document.form1.target = window.self.name;
	}
</script>

<form name="form1" method="post">
	<input type='hidden' name='p_process'>
	<input type='hidden' name='p_subj' value="">
	<input type='hidden' name='p_year' value="">
	<input type='hidden' name='p_subjseq' value="">
	<input type='hidden' name='p_scsubjseq' value="">
	<input type='hidden' name='p_subjnm' value="">
	<input type='hidden' name='p_userid' value="">
	<input type='hidden' name='p_gubun' value="">
	<input type='hidden' name='p_rejectedreason' value="">
	<input type='hidden' name='p_isonoff' value="">
	<input type='hidden' name='p_kind' value="">
	<input type='hidden' name='p_grcode' value="${sessionScope.tem_grcode }">
	<input type='hidden' name='s_grcode' value="${sessionScope.tem_grcode }">
	<input type="hidden" name="p_pageno" value="<c:out value="${param.p_pageno }" />">
	<input type="hidden" name="p_pagesize" value="<c:out value="${param.p_pagesize }" />"/>
	<!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
	<input type='hidden' name='lessonRepopup' value="1">
	<input type='hidden' name='lessonReurl' value="">
	<input type='hidden' name='lessonReiurl' value="">
	<!-- <input type='hidden' name='wj_classkey' value="">-->
	<input type="hidden" name="gubun" value="${param.gubun }"/>
	<input type="hidden" name="menuid" value="${param.menuid }"/>
</form>
<section class="container d-flex myclass01">
	<div class=""></div>
	<div class="subContainer">
		<div class="sub_section">
			<div class="sub_contents_body">
				<div class="sub_board_header">
					<jsp:include page="/learn/user/typeB/include_left/left_3.jsp">
						<jsp:param value="${param.menuid }" name="left_active"/>
					</jsp:include>

					<div class="subContainer">
						<div class="sub_section">
<%--							<div class="sub_contents_header">--%>
<%--								<span>수강중인과정</span>--%>
<%--								<div class="list_title">--%>
<%--									<span>현재 수강중인 과정 목록입니다.</span>--%>
<%--									<span>수강중의 과정이 있을 경우 학습창으로 이동하실 수 있습니다.</span>--%>
<%--								</div>--%>
<%--							</div>--%>
							<div class="sub_contents_body">
								<div class="sub_boarder_body mb-5">
									<ul class="my_card_list_box">
										<c:forEach items="${EducationStudyingSubjectList }" var="list" varStatus="status">
											<li class="d-flex">
												<div class="tnail_box">
													<img src="https://test.edukocca.or.kr${list.d_introducefilenamenew}" alt="섬네일 호출">
												</div>
												<div class="info_text_box">
													<h5>
														<a href="javascript:whenSubjInfoPopup('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />');">
															<c:out value="${list.d_subjnm }"/>
														</a>
													</h5>
													<p>교육기간 :
														<fmt:parseDate value="${list.d_edustart }" var="edustart" pattern="yyyyMMddHH"/>
														<fmt:formatDate value="${edustart }" pattern="yyyy.MM.dd"/>
														~
														<fmt:parseDate value="${list.d_eduend }" var="eduend" pattern="yyyyMMddHH"/>
														<fmt:formatDate value="${eduend }" pattern="yyyy.MM.dd"/>
													</p>
													<!--<p>수강신청일 : 2023.06.07</p>-->
													<p class="mt-2">진도율</p>
													<div class="progress-box">
														<div class="progress" role="progressbar" aria-label="progressbar" aria-valuenow="${list.d_score}" aria-valuemin="0" aria-valuemax="100">
															<div class="progress-bar" style="width: ${list.d_score}%; margin:0;"></div>
														</div>
														<span>${list.d_score}%</span>
													</div>
												</div>
												<div class="state_box">
												<span>
													<c:set var="ieduurl" value="${(list.d_eduurl eq '' || list.d_eduurl eq null)? 0 : 1 }"/>
													<c:url value="/servlet/controller.contents.EduStart" var="edulist_value">
														<c:param name="p_process" value="eduList"/>
														<c:param name="p_subj" value="${list.d_subj }"/>
														<c:param name="p_year" value="${list.d_year }"/>
														<c:param name="p_subjseq" value="${list.d_subjseq }"/>
														<c:param name="p_userid" value="${sessionScope.userid }"/>
													</c:url>
													<c:choose>
														<c:when test="${list.d_isoutsourcing eq 'Y' }">
															<c:url value="/servlet/controller.contents.EduStart" var="eduUrl">
																<c:param name="FIELD1" value="${sessionScope.userid }"/>
																<c:param name="FIELD2" value="${list.d_year }"/>
																<c:param name="FIELD3" value="${list.d_cpsubj }"/>
																<c:param name="FIELD4" value="${list.d_cpsubjseq }"/>
																<c:param name="FIELD99" value="${list.d_company }"/>
																<c:param name="FIELD100" value="N"/>
																<c:param name="contenttype" value="${list.d_contenttype }"/>
																<c:param name="p_subj" value="${list.d_subj }"/>
																<c:param name="p_year" value="${list.d_year }"/>
																<c:param name="p_subjseq" value="${list.d_subjseq }"/>
															</c:url>
														</c:when>
														<c:otherwise>
															<c:if test="${list.d_eduurl eq '' || list.d_eduurl eq null }">
																<c:url value="/servlet/controller.contents.EduStart" var="eduUrl">
																	<c:param name="p_subj" value="${list.d_subj }"/>
																	<c:param name="p_year" value="${(list.d_year eq null || list.d_year eq '')? '2000' : list.d_year}"/>
																	<c:param name="p_subjseq" value="${(list.d_subjseq eq null || list.d_subjseq eq '')? '0001' : list.d_subjseq }"/>
																	<c:param name="contenttype" value="${list.d_contenttype }"/>
																</c:url>
															</c:if>
														</c:otherwise>
													</c:choose>
													<a href="javascript:whenEdulist('<c:out value="${ieduurl }" />','','<c:out value="${edulist_value }" />');"  class="btn_view btn btn-outline-purple">학습현황</a>
												</span>
											</div>
											<div class="btn_box">
												<span>
													<c:choose>
														<c:when test="${list.d_controlstudy > 0 }">
															<a href="javascript:controlstudy();" class="btn_view btn btn-purple">학습하기</a>
														</c:when>
														<c:otherwise>
															<c:if test="${list.d_isstudyyn eq 'Y'}">
																<c:choose>
																	<c:when test="${sessionScope.tem_grcode eq 'N000031' }">
																		<a href="javascript:studyOpen2('<c:out value="${eduUrl }" />','<c:out value="${ieduurl }" />>', '<c:out value="${list.d_wj_classkey }" />', '<c:out value="${list.d_edustartdt }" />');" class="btn_view btn btn-purple">학습하기</a>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:studyOpen('<c:out value="${eduUrl }" />','<c:out value="${ieduurl }" />');" class="btn_view btn btn-purple">학습하기</a>
																	</c:otherwise>
																</c:choose>
															</c:if>
														</c:otherwise>
													</c:choose>
												</span>
											</div>
										</li>
										<c:set var="totalpage" value="${list.d_totalpage }"/>
										</c:forEach>
										<c:if test="${fn:length(EducationStudyingSubjectList) <= 0 }">
											<li class="text-center">
												<span>수강중인 과정이 없습니다.</span>
											</li>
											<c:set var="totalpage" value="0"/>
										</c:if>
									</ul>

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
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp"/>
<!-- footer -->

</body>
</html>