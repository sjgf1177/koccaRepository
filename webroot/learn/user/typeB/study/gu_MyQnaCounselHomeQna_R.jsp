<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%
	pageContext.setAttribute("CR", "\r");
	pageContext.setAttribute("LF", "\n");
	pageContext.setAttribute("CRLF", "\r\n");
	pageContext.setAttribute("SP", "&nbsp;");
	pageContext.setAttribute("BR", "<br/>");
%>

<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<script type="text/javascript">
//리스트페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.study.MyQnaServlet";
    document.form1.p_process.value = "MyQnaCounselListPage";     
    document.form1.submit();    
}
</script>
<form name="form1" method="post">
	<input type="hidden" name="p_seq"         value="<c:out value="${param.p_seq }" />" />
	<input type="hidden" name="p_searchtext"  value="<c:out value="${param.p_searchtext }" />" />
	<input type="hidden" name="p_select"      value="<c:out value="${param.p_select }" />" />
	<input type="hidden" name="p_pageno"      value="<c:out value="${param.p_pageno }" />" />
	<input type="hidden" name="p_selCategory" value="<c:out value="${param.p_selCategory }" />" />
	<input type="hidden" name="p_process"     value="" />
	<input type="hidden" name="p_userid"      value="<c:out value="${sessionScope.userid }" />" />
	<input type="hidden" name="p_types"       value="<c:out value="${selectQna.d_types }" />" />
	<input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
</form>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_3.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>나의상담내역</span>
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
                                            <a href="#"><span>나의강의실</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>나의 상담내역</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_board_header">
                                   <div class="list_title">
                                        <span><span>·</span>나의 상담내역을 확인할 수 있는 공간입니다.</span>
                                        <span><span>·</span>Q&A, 과정질문방에 대한 답변을 확인할 수 있습니다.</span>
                                    </div>
                                </div>
                                <div class="sub_info_body">
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                            <table>
                                                <colgroup>
                                                    <col width="10%">
                                                    <col width="auto">
                                                    <col width="10%">
                                                    <col width="auto">
                                                    <col width="10%">
                                                    <col width="auto">
                                                    <col width="10%">
                                                    <col width="8%">
                                                </colgroup>
                                                <tbody>
                                                    <tr>
                                                        <th>제목</th>
                                                        <td colspan="7"><c:out value="${selectQna.d_title }" /></td>
                                                    </tr>
                                                    <tr>
                                                        <th>분류</th>
                                                        <td><c:out value="${selectQna.d_categorynm }" /></td>
                                                        <th>작성자</th>
                                                        <td><c:out value="${selectQna.d_name }" /></td>
                                                        <th>작성일자</th>
                                                        <td>
                                                        	<fmt:parseDate value="${selectQna.d_indate }" var="indate" pattern="yyyyMMddHHmmss" />
                                                        	<fmt:formatDate value="${indate }" pattern="yyyy.MM.dd"/>
                                                        </td>
                                                        <th>조회</th>
                                                        <td><c:out value="${selectQna.d_cnt }" /></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="8">
                                                        	<c:set var="contents" value="${fn:replace(selectQna.d_contents, CRLF, BR)}" />
															<c:set var="contents" value="${fn:replace(contents, CR, BR)}" />
															<c:set var="contents" value="${fn:replace(contents, LF, BR)}" />
															<c:out value="${contents }" escapeXml="false" />
                                                        </td>
                                                    </tr>
                                                    <c:if test="${fn:length(selectQna.d_savefile) > 0 }">
                                                    <tr>
                                                    	<th>첨부파일</th>
                                                        <td colspan="7">
                                                        	<c:forEach items="${selectQna.d_realfile }" var="item" varStatus="status">
                                                        		<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<c:out value="${selectQna.d_savefile[status.index] }" />&p_realfile=<c:out value="${selectQna.d_realfile[status.index] }" />'><c:out value="${selectQna.d_realfile[status.index] }" /></a><br/>
                                                        	</c:forEach>
                                                        </td>
                                                    </tr>
                                                    </c:if>
                                                </tbody>
                                            </table>
                                            <c:if test="${selectQna.d_okyn1 eq '3' }">
                                            	<p class="sub_course_view_title" style="margin-top:50px;">문의답변</p>
	                                            <table>
	                                                <colgroup>
	                                                    <col width="20%">
	                                                    <col width="auto">
	                                                    <col width="20%">
	                                                    <col width="auto">
	                                                </colgroup>
	                                                <tbody>
	                                                	<c:choose>
		                                                	<c:when test="${fn:length(selectAns) <= 0 }">
			                                                    <tr>
			                                                        <td colspan="4">
			                                                        	답변 준비중입니다.
			                                                        </td>
			                                                    </tr>
		                                                	</c:when>
		                                                	<c:otherwise>
		                                                		<tr>
			                                                        <th>제목</th>
			                                                        <td><c:out value="${selectAns.d_title }" /></td>
			                                                        <th>날짜</th>
			                                                        <td>
			                                                        	<fmt:parseDate value="${selectAns.d_indate }" var="asindate" pattern="yyyyMMddHHmmss" />
	                                                        			<fmt:formatDate value="${asindate }" pattern="yyyy.MM.dd"/>
	                                                        		</td>
			                                                    </tr>
			                                                    <tr>
			                                                        <td colspan="4">
			                                                        	<c:out value="${selectAns.d_contents }" escapeXml="false" />
			                                                        </td>
			                                                    </tr>
	                                                		</c:otherwise>
	                                                	</c:choose>
	                                                </tbody>
	                                            </table>
	                                        </c:if>
                                            <div class="course_button">
                                                <a href="javascript:selectList();" class="list_btn">목록가기</a>
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