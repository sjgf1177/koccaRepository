<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<script type="text/javascript">
//수강신청 취소 팝업
function cancelApply(p_tid, p_paymethod){
    window.self.name = "winProposePage";
    farwindow = window.open("", "openWinCancelApply", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 550, height = 450, top=0, left=0");
    document.form1.target = "openWinCancelApply"
    document.form1.p_tid.value = p_tid;
    document.form1.p_paymethod.value = p_paymethod;
	document.form1.p_process.value = 'ProposeCancelApplyPage';
    document.form1.action = "/servlet/controller.study.MyClassServlet";
    document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

//수강취소
function cancelPropose(p_tid, p_paymethod){

    if(!confirm("수강을 취소하시겠습니까?\n수강신청내용과 결제정보가 취소됩니다.")){
        return;
    }

    document.form1.target = "_self"
    document.form1.p_tid.value = p_tid;
    document.form1.p_paymethod.value = p_paymethod;
	document.form1.p_process.value = 'CancelPropose';
    document.form1.action = "/servlet/controller.study.MyClassServlet";
    document.form1.submit();
}
</script>

<form name= "form1" method= "post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_userid'    >
    <input type='hidden' name='p_subj'      >
    <input type='hidden' name='p_year'      >
    <input type='hidden' name='p_subjseq'   >
    <input type='hidden' name='p_subjnm'    >
    <input type='hidden' name='p_tid'	    >
    <input type='hidden' name='p_paymethod' >
</form>
<section class="container d-flex">
    <div class=""></div>
    <div class="subContainer">
        <div class="sub_section">
            <div class="sub_contents_body">
                <div class="sub_board_header">
                    <jsp:include page="/learn/user/typeB/include_left/left_3.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
<%--                    <div class="list_title">--%>
<%--                        <span>수강신청이 완료된 과정목록입니다. 신청한 과정명과 교육기간을 확인하세요.</span>--%>
<%--                    </div>--%>
                    <div class="subContainer">
                        <div class="sub_section">

                            <div class="sub_contents_body">
                                <div class="sub_boarder_body">
									<ul class="my_card_list_box">
									<c:forEach items="${ProposeHistoryList }" var="list" varStatus="status">
										<fmt:parseDate value="${list.d_edustart }" var="edustart" pattern="yyyyMMddHH" />
										<fmt:parseDate value="${list.d_eduend }" var="eduend" pattern="yyyyMMddHH" />
										<fmt:parseDate value="${list.d_appdate }" var="appdate" pattern="yyyyMMddHHmmss" />
										<li class="d-flex">
											<div class="tnail_box">
												<img src="https://test.edukocca.or.kr/upload/bulletin/2022/GoldClassAdmin_img_file_202208301403281_lee1.jpg" alt="섬네일 호출">
											</div>
											<div class="info_text_box">
												<h5><c:out value="${list.d_subjnm }" /></h5>
												<p>교육기간 : <fmt:formatDate value="${edustart }" pattern="yyyy.MM.dd" /> ~ <fmt:formatDate value="${eduend }" pattern="yyyy.MM.dd" /><br/>
													수강신청일 : <c:if test="${list.d_appdate ne '' }">
														<fmt:formatDate value="${appdate }" pattern="yyyy.MM.dd" />
													</c:if>
												</p>

											</div>
											<div class="state_box">
												<c:choose>
													<c:when test="${list.d_chkfirst eq 'Y' && list.d_chkfinal eq 'Y' }"><span class="complete">승인완료</span></c:when>
													<c:when test="${list.d_chkfirst eq 'Y' && list.d_chkfinal eq 'B' }"><span class="incomplete">승인대기</span></c:when>
													<c:when test="${list.d_chkfirst eq 'Y' && list.d_chkfinal eq 'N' }"><span class="gray">반려</span></c:when>
													<c:otherwise><span class="gray">-</span></c:otherwise>
												</c:choose>
											</div>
											<div class="btn_box">
												<span>
												<c:choose>
													<c:when test="${list.d_refundyn eq 'N' && list.d_refundableyn eq 'Y' }">
														<c:choose>
															<c:when test="${list.d_canceldate ne '' }">
																<b class="gray">취소요청일<br/>
																	<c:out value="${list.d_canceldate }" />
																</b>
															</c:when>
															<c:otherwise>
																<c:choose>
																	<c:when test="${list.d_cancelableyn eq 'Y' }">
																		<a href="javascript:cancelPropose('<c:out value="${list.d_tid }" />','<c:out value="${list.d_paymethod }" />');" class="btn btn-secondary btn_view">수강취소</a>
																	</c:when>
																	<c:otherwise>
																		<a href="javascript:cancelApply('<c:out value="${list.d_tid }" />','<c:out value="${list.d_paymethod }" />');" class="btn btn-secondary btn_view">취소요청</a>
																	</c:otherwise>
																</c:choose>
															</c:otherwise>
														</c:choose>
													</c:when>
													<c:otherwise>
														<c:choose>
															<c:when test="${list.d_refundyn eq 'Y' }">
																<b class="gray">취소일<br/>
																	<fmt:parseDate value="${list.d_refunddate }" var="refunddate" pattern="yyyyMMddHHmmss" />
																	<fmt:formatDate value="${refunddate }" pattern="yyyy.MM.dd"/>
																</b>
															</c:when>
															<c:otherwise>
																<b class="gray">기간만료</b>
															</c:otherwise>
														</c:choose>
													</c:otherwise>
												</c:choose>
												</span>
											</div>
										</li>
										</c:forEach>
										<c:if test="${fn:length(ProposeHistoryList) <= 0 }">
											<li class="text-center">
												<span>수강신청 확인/취소 내역이 없습니다.</span>
											</li>
										</c:if>
									</ul>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<style>
	@media all and (max-width: 640px){
		.sub_boarder_body{margin-top: 30px;}
	}

</style>
<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>