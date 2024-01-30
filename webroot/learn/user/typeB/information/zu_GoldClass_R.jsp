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
        var prePage = document.form1.p_prePage.value;

        if(prePage == "Main") {
            document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
            document.form1.target="_self";
            document.form1.p_process.value = "mainPage";
            document.form1.submit();
        } else {
            document.form1.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
            document.form1.p_process.value = "selectPreGoldClassList";
            document.form1.target="_self";
            document.form1.submit();
        }
    }

    function openGoldClass(seq,w,h){

        if (w.length>0) {
            window.open("", "ViewVod", "top=0, left=0, width="+w+", height="+h+", status=no, resizable=no");
        }else{
            window.open("", "ViewVod", "top=0, left=0, width=1008, height=570, status=no, resizable=no");
        }
        document.form1.action="/servlet/controller.infomation.GoldClassHomePageServlet";
        document.form1.p_process.value = "popUpVod";
        document.form1.p_seq.value = seq;
        document.form1.target="ViewVod";
        document.form1.submit();
        document.form1.target="_self";
    }
</script>

<form name = "form1" method = "post">
    <input type = "hidden" name = "p_seq"         value = "<c:out value="${param.p_seq }" />">
    <input type = "hidden" name = "p_searchtext"  value = "<c:out value="${param.p_searchtext }" />">
    <input type = "hidden" name = "p_search"      value = "<c:out value="${param.p_search }" />">
    <input type = "hidden" name = "pageNo"      value = "<c:out value="${param.pageNo }" />">
    <input type = "hidden" name = "p_prePage"     value = "<c:out value="${param.p_prePage }" />">
    <input type = "hidden" name = "p_process"     value = "">
    <input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
</form>
<section class="container d-flex">
    <div class=""></div>
    <div class="subContainer">
        <div class="sub_section">
            <div class="sub_contents_body">
                <div class="sub_board_header">
                    <%--<jsp:include page="/learn/user/typeB/include_left/left_5.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>--%>

                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <%--                                <p class="sub_course_view_title">과정 소개</p>--%>
                                    <div class="sub_board_header col-right">
                                        <a href="javascript:selectList();" class="list_btn btn">목록으로</a>
                                        <%--                                    <div class="list_title">--%>
                                        <%--                                        <span>열린강좌는 무료로 제공되는 공개강좌로 재미와 깊이가 함께 존재합니다.</span>--%>
                                        <%--                                        <span>훌륭한 명사의 강좌를 만나보실 수 있으며, 누구나 참여할 수 있는 열린 교육공간입니다.</span>--%>
                                        <%--                                    </div>--%>
                                    </div>
                                    <div class="sub_course_alert_box">
                                        <p><c:out value="${selectOffExpert.d_lecnm }" /></p>
                                    </div>
                                    <div class="video_watch_w">

                                        <!-- 영상 들어가는 곳-->
                                        <div class="video">
                                            <video src="<c:out value="${selectOffExpert.d_vodurl}" />" autoplay  controls style="width: 100%; height: auto;"></video>
                                        </div>
                                        <!--// 영상 들어가는 곳-->
                                    </div>
                                    <div class="sub_course_view_wrap">

                                        <div class="info_box">
                                            <table class="tutor">
                                                <colgroup>
                                                    <col width="25%">
                                                    <col width="auto">
                                                </colgroup>
                                                <tbody>
                                                <c:if test="${not empty selectOffExpert.d_tutornm or not empty electOffExpert.d_tutorcareer}">
                                                    <tr>
                                                        <th>강사소개</th>
                                                        <td>
                                                                <%--<img src="/servlet/controller.library.DownloadServlet?p_savefile=<c:out value="${selectOffExpert.d_tutorimg }" />" alt="강사">--%>
                                                            <span>이름 : <c:out value="${selectOffExpert.d_tutornm }" /></span>
                                                            <span>
                                                            약력<br/>
                                                            <c:set var="tutorcareer" value="${fn:replace(selectOffExpert.d_tutorcareer, CRLF, BR)}" />
                                                            <c:set var="tutorcareer" value="${fn:replace(tutorcareer, LF, BR)}" />
                                                            <c:set var="tutorcareer" value="${fn:replace(tutorcareer, CR, BR)}" />
                                                            <c:out value="${tutorcareer }" escapeXml="false" />
                                                        </span>
                                                            <span></span>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${not empty selectOffExpert.d_intro}">
                                                    <tr>
                                                        <th>강좌소개 (강좌개요)</th>
                                                        <td>
                                                            <c:set var="intro" value="${fn:replace(selectOffExpert.d_intro, CRLF, BR)}" />
                                                            <c:set var="intro" value="${fn:replace(intro, LF, BR)}" />
                                                            <c:set var="intro" value="${fn:replace(intro, CR, BR)}" />
                                                            <c:out value="${intro }" escapeXml="false" />
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:if test="${not empty selectOffExpert.d_contents}">
                                                    <tr>
                                                        <th>강좌내용</th>
                                                        <td>
                                                            <c:set var="contents" value="${fn:replace(selectOffExpert.d_contents, CRLF, BR)}" />
                                                            <c:set var="contents" value="${fn:replace(contents, LF, BR)}" />
                                                            <c:set var="contents" value="${fn:replace(contents, CR, BR)}" />
                                                            <c:out value="${contents }" escapeXml="false" />
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                </tbody>
                                            </table>
                                            <%--                                        <div class="course_button">--%>
                                            <%--                                            <a href="javascript:openGoldClass('<c:out value="${param.p_seq }" />', '<c:out value="${selectOffExpert.d_width_s }" />','<c:out value="${selectOffExpert.d_height_s }" />');" class="apply_btn btn btn-purple big_btn">강좌보기</a>--%>
                                            <%--                                            <a href="javascript:selectList();" class="list_btn btn btn-outline-secondary">목록으로</a>--%>
                                            <%--                                        </div>--%>
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
<style>
    .info_box table tr{
        border-bottom: none;
    }
</style>
</html>