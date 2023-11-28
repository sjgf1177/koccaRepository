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
<section class="container d-flex noticedetail">
    <div class=""></div>
    <div class="subContainer">
        <div class="sub_section">
            <div class="sub_contents_body">
                <div class="sub_board_header">

                    <div class="subContainer">
                        <div class="sub_section">

                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                            <div class="sub_course_alert_box">
                                                <p><c:out value="${selectNotice.d_adtitle }" /></p>
                                                <div class="d-flex justify-content-between point_lightgray mt10">
                                                    <span style="margin: 0; font-size: 14px;">조회수 : <c:out value="${selectNotice.d_cnt }" /></span>
                                                    <span style="margin: 0; font-size: 14px;">
                                                        <fmt:parseDate value="${selectNotice.d_addate }" var="addate" pattern="yyyyMMddHHmmss" />
                                                        <fmt:formatDate value="${addate }" pattern="yyyy.MM.dd" />
                                                    </span>
                                                </div>
                                            </div>
                                            <div class="sub_course_view_wrap">
                                                <div class="info_box">
                                                    <table>
                                                        <colgroup>
                                                            <col width="25%">
                                                            <col width="auto">
                                                        </colgroup>
                                                        <tbody>
                                                        <tr>
                                                            <td>
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

                                                </div>
                                            </div>

                                            <div class="course_button">
                                                <a href="javascript:listnotice();" class="list_btn btn btn-outline-secondary">목록으로</a>
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