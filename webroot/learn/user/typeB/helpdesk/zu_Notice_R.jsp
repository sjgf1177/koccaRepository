<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
function listnotice(){
	document.nform2.p_process.value = "List";
    document.nform2.action = "/servlet/controller.homepage.HomeNoticeServlet";
    document.nform2.submit();
}
</script>

<form name="nform2" method="post">
	<input type = "hidden" name = "p_process" >
	<input type = "hidden" name = "p_pageno" value = "<c:out value="${param.p_pageno }" />">
	<input type = "hidden" name = "p_searchtext" value = "<c:out value="${param.p_searchtext }" />">
	<input type = "hidden" name = "p_search" value = "<c:out value="${param.p_search }" />">
	<input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
</form>
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
                                <span>공지사항</span>
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
                                            <a href="#"><span>공지사항</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_board_header">
                                    <div class="list_title">
                                        <span><span>·</span>학습지원센터에 오신것을 환영합니다.</span>
                                        <span class="point_orange"><span>·</span>콘텐츠아카데미에서 알려드리는 새소식을 확인하실 수 있습니다.</span>
                                    </div>
                                </div>
                                <div class="sub_info_body">
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                            <table>
                                                <colgroup>
                                                    <col width="15%">
                                                    <col width="auto">
                                                    <col width="15%">
                                                    <col width="auto">
                                                </colgroup>
                                                <tbody>
                                                    <tr>
                                                        <th>제목</th>
                                                        <td colspan="3"><c:out value="${selectNotice.d_adtitle }" /></td>
                                                    </tr>
                                                    <tr>
                                                        <th>작성일자</th>
                                                        <td>
                                                        	<fmt:parseDate value="${selectNotice.d_addate }" var="addate" pattern="yyyyMMddHHmmss" />
                                           					<fmt:formatDate value="${addate }" pattern="yyyy.MM.dd" />
                                                        </td>
                                                        <th>조회</th>
                                                        <td><c:out value="${selectNotice.d_cnt }" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="4">
                                                        	<c:out value="${selectNotice.d_adcontent }" escapeXml="false"/>
                                                        </td>
                                                    </tr>
                                                    <c:if test="${fn:length(selectNotice.d_savefile) > 0 }" >
                                                    <tr>
                                                    	<th>첨부파일</th>
                                                        <td colspan="3">
                                                        	<c:forEach items="${selectNotice.d_realfile }" var="item" varStatus="status">
                                                        		<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<c:out value="${selectNotice.d_savefile[status.index] }" />&p_realfile=<c:out value="${selectNotice.d_realfile[status.index] }" />'><c:out value="${selectNotice.d_realfile[status.index] }" /></a><br/>
                                                        	</c:forEach>
                                                        </td>
                                                    </tr>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                            <div class="course_button">
                                                <a href="javascript:listnotice();" class="list_btn">목록가기</a>
<!--                                                 <a href="#" class="link_copy_btn">링크복사</a> -->
                                            </div>
                                        </div>
                                    </div>
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