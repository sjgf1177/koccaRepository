<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<c:set var="pageno" value="${param.p_pageno eq null || param.p_pageno eq ''? 1 : param.p_pageno}" />
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}" />


<script type="text/javascript">
//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaCounselListPage";
	document.form1.submit();

}


//Q&A상세화면 페이지로 이동
function viewHomeqna(seq, upfilecnt, userid, types, type) {
	document.form1.action				= "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value		= "MyQnaCounselHomeQnaViewPage";
	document.form1.p_seq.value			= seq;
	document.form1.p_userid.value		= userid;
	document.form1.p_types.value		= types;
	document.form1.pp_type.value		= type;
	document.form1.p_upfilecnt.value	= upfilecnt;
	document.form1.submit();


}

//과정질문 상세화면 페이지로 이동
function viewQna(seq,subj,year,subjseq) {
	document.form1.action = "/servlet/controller.study.MyQnaServlet";
	document.form1.p_process.value = "MyQnaCounselQnaViewPage";
	document.form1.p_seq.value     = seq;
	document.form1.p_subj.value    = subj;
	document.form1.p_year.value    = year;
	document.form1.p_subjseq.value = subjseq;
	document.form1.submit();
}
</script>
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet" onsubmit="javascript:selectList();">
	<input type="hidden" name="p_process"	value="">
	<input type="hidden" name="p_pageno" 	value="<c:out value="${param.p_pageno }" />">
	<input type="hidden" name="p_pagesize"  value="<c:out value="${param.p_pagesize }" />"/>
	<input type="hidden" name="p_seq"		value="">
	<input type="hidden" name="p_userid"	value="">
	<input type="hidden" name="p_upfilecnt"	value="">
	<input type="hidden" name="p_types"		value="">
	<input type="hidden" name="pp_type"		value="">
	<input type="hidden" name="p_tabseq"	value="">
	<input type="hidden" name="p_subj">
	<input type="hidden" name="p_year">
	<input type="hidden" name="p_subjseq">
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
                                <div class="sub_boarder_body">
                                    <table class="td_align_left2">
                                        <colgroup>
                                            <col width="8%">
                                            <col width="15%">
                                            <col width="auto">
                                            <col width="15%">
                                            <col width="15%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>번호</th>
                                                <th>게시판 분류</th>
                                                <th>제목</th>
                                                <th>작성일자</th>
                                                <th>답변여부</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach items="${MyQnaCounselListPage }" var="list" varStatus="status">
                                        		<tr>
	                                                <td><c:out value="${list.d_dispnum }" /></td>
	                                                <td>
	                                                	<c:choose>
	                                                		<c:when test="${list.d_type eq 'PQ' }">홈페이지</c:when>
	                                                		<c:when test="${list.d_type eq 'OFF' }">유선상담</c:when>
	                                                		<c:when test="${list.d_type eq 'SUBJ' }">과정질문방</c:when>
	                                                		<c:otherwise>1vs1상담</c:otherwise>
	                                                	</c:choose>
	                                                </td>
	                                                <td>
	                                                	<c:choose>
	                                                		<c:when test="${list.d_type eq 'SUBJ' }">
	                                                			<a href="javascript:viewQna('<c:out value="${list.d_no }" />','<c:out value="${list.d_subj }" />','<c:out value="${list.d_year }" />','<c:out value="${list.d_subjseq }" />');"><c:out value="${list.d_title }" /></a>
	                                                		</c:when>
	                                                		<c:otherwise>
	                                                			<a href="javascript:viewHomeqna('<c:out value="${list.d_no }" />','<c:out value="${list.d_upfilecnt }" />','<c:out value="${sessionScope.userid }" />','<c:out value="${list.d_types }" />','<c:out value="${list.d_type }" />');"><c:out value="${list.d_title }" /></a>
	                                                		</c:otherwise>
	                                                	</c:choose>
	                                                </td>
	                                                <td>
	                                                	<fmt:parseDate value="${list.d_sdate }" var="sdate" pattern="yyyyMMddHHmmss" />
	                                                	<fmt:formatDate value="${sdate}" pattern="yyyy.MM.dd"/>
	                                                </td>
	                                                <td>
	                                                	<c:choose>
	                                                		<c:when test="${list.d_status eq '2' }"><span class="point_orange">답변준비중</span></c:when>
	                                                		<c:when test="${list.d_status eq '3' }"><span class="point_blue">답변완료</span></c:when>
	                                                		<c:otherwise><span class="point_black">열람전</span></c:otherwise>
	                                                	</c:choose>
	                                                </td>
	                                            </tr>
	                                            <c:set var="totalpage" value="${list.d_totalpage }" />
                                        	</c:forEach>
                                        	<c:if test="${fn:length(MyQnaCounselListPage) <= 0 }">
                                        		<tr>
	                                                <td colspan="5">등록된 상담내역이 없습니다.</td>
	                                            </tr>
                                        	<c:set var="totalpage" value="1" />
                                        	</c:if>
                                        </tbody>
                                    </table>
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