<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
function changeCategory(aa) {
    document.form1.faqcategory.value=aa;
    $("#p_searchtxt").val("");
    document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
    document.form1.p_process.value    = "selectList";
    document.form1.submit();
}

function searchTxt() {
	if(document.form1.p_searchtxt.value ==""){
		alert("내용을 입력하세요");
		return;

	}
	document.form1.action = "/servlet/controller.homepage.HomePageFAQServlet";
	document.form1.p_process.value = "selectList";
	document.form1.submit();
}
</script>

<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_4.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>FAQ</span>
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
                                            <a href="#"><span>학습지원센터</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>FAQ</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_board_header">
                                    <div class="list_title">
                                        <span><span>·</span>자주 질문되는 사항에 대한 답변입니다. 이곳에서 해결이 안되면 Q&A에 질문주시기 바랍니다.</span>
                                    </div>
                                </div>
                                <div class="sub_boarder_body">
                                    <div class="board_search_box" style="width:100%; text-align: right; margin-bottom:20px;">
                                        <form name="form1" method="post" onsubmit="javascript:searchTxt();">
											<input type="hidden" name="p_process"   value="">
											<input type="hidden" name="p_pageno"    value="">
											<input type="hidden" name="p_seq"       value="">
											<input type="hidden" name="p_userid"    value="">
											<input type="hidden" name="faqcategory" value="<c:out value="${param.faqcategory }" />">
											<input type="hidden" name="gubun" value="${param.gubun }" />
    										<input type="hidden" name="menuid" value="${param.menuid }" />
                                            <input type="text" class="board_search" id="p_searchtxt" name="p_searchtxt" value="<c:out value="${param.p_searchtxt }"/>" title="검색어를 입력해주세요">
                                            <input type="submit" class="btn_board_search" title="검색" value="">
                                        </form>
                                    </div>
                                    <div class="tab_style_con">
                                        <ul class="tab_style_2 three">
                                       		<li <c:if test="${param.faqcategory eq '' || param.faqcategory eq 'ALL' || param.faqcategory eq null }"> class="active" </c:if>>
                                                <a href="javascript:changeCategory('ALL');"><span>전체</span></a>
                                            </li>
                                        	<c:forEach items="${cateList }" var="list" varStatus="status">
                                        		<li <c:if test="${param.faqcategory eq list.d_faqcategory }"> class="active" </c:if>>
	                                                <a href="javascript:changeCategory('<c:out value="${list.d_faqcategory }" />');"><span><c:out value="${list.d_faqcategorynm }" />(<c:out value="${list.d_cnt }" />)</span></a>
	                                            </li>
                                        	</c:forEach>
                                        </ul>
                                    </div>
                                    <ul class="faq_con">
                                    	<c:forEach items="${selectList }" var="list" varStatus="status">
                                    		<li>
	                                            <div class="faq_tit">
	                                                <span><c:out value="${list.d_title }" /></span>
	                                                <span>
	                                                    <img src="/common/image/faq_direc_off.png" alt="faq off 화살표" class="direc_off">
	                                                    <img src="/common/image/faq_direc_on.png" alt="faq on 화살표" class="direc_on">
	                                                </span>
	                                            </div>
	                                            <div class="faq_reply">
	                                                <div>
	                                                    <div class="answer">
	                                                        <c:out value="${list.d_contents }" escapeXml="false" />
	                                                    </div>
	                                                </div>
	                                            </div>
	                                        </li>
                                    	</c:forEach>
                                    	<c:if test="${fn:length(selectList) <= 0 }">
                                    		<li>
                                    			<div class="faq_tit">
	                                                <span>등록된 FAQ 내역이 없습니다.</span>
	                                                <span>
	                                                    <img src="/common/image/faq_direc_off.png" alt="faq off 화살표" class="direc_off">
	                                                    <img src="/common/image/faq_direc_on.png" alt="faq on 화살표" class="direc_on">
	                                                </span>
	                                            </div>
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

<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>
