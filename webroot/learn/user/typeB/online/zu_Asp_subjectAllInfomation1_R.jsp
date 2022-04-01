<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />
<script type="text/javascript">
function selectArea( val ) {
	document.form1.tabid.value = val;
	document.form1.p_process.value = "subjectallinformation";
	document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
	document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, wj_classkey){
	document.form2.p_subj.value = subj;
	document.form2.p_subjnm.value = subjnm;
	document.form2.p_iscourseYn.value = courseyn;
	document.form2.p_upperclass.value = upperclass;
	document.form2.p_upperclassnm.value = upperclassnm;
	document.form2.p_year.value = year;
	document.form2.p_subjseq.value = subjseq;
	document.form2.p_wj_classkey.value = wj_classkey;
	document.form2.p_process.value = 'SubjectPreviewPage';
	document.form2.p_rprocess.value = 'SubjectList';
	document.form2.action='/servlet/controller.propose.ProposeCourseServlet';
	document.form2.target = "_self";
	document.form2.submit();
}
</script>
<c:set value="${requestbox.tabid }" var="tabid" />
<form name="form1" method="post" id="toptop">
    <input type="hidden" name="p_process" />
    <input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
    <input type="hidden" name="tabid" value="<c:out value="${tabid }" />" />
</form>
<form name="form2" action="/servlet/controller.homepage.AspMenuMainServlet" method="post">
	<input type="hidden" name="p_process"	value="<c:out value="${param.p_process }" />"/>
	<input type="hidden" name="p_rprocess"	value=""/>
	<input type="hidden" name="p_year"	value=""/>
	<input type="hidden" name="p_subj"	value=""/>
	<input type="hidden" name="p_subjseq"	value=""/>
	<input type="hidden" name="p_subjnm"	value=""/>
	<input type="hidden" name="p_iscourseYn"	value=""/>
	<input type="hidden" name="p_upperclass"	value="<c:out value="${param.p_upperclass }" />"/>
	<input type="hidden" name="p_upperclassnm"	value=""/>
	<input type="hidden" name="p_wj_classkey" value=""/>
	<input type="hidden" name="p_area" id="p_area"	value="<c:out value="ALL" />"/>
	<input type="hidden" name="gubun" value="${param.gubun }" />
    <input type="hidden" name="menuid" value="${param.menuid }" />
</form>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_8.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>교육과정안내</span>
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
                                            <a href="#"><span>과정안내</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>교육과정안내</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="tab_style_con">
                                    <ul class="tab_style_1 four">
                                        <li <c:if test="${tabid eq '0' || tabid eq '' || tabid eq null }" > class="active"</c:if>>
                                            <a href="javascript:selectArea('0');"><span>전체</span></a>
                                        </li>
                                        <li <c:if test="${tabid eq '2'}" > class="active"</c:if>>
                                            <a href="javascript:selectArea('2');"><span>방송영상</span></a>
                                        </li>
                                        <li <c:if test="${tabid eq '1'}" > class="active"</c:if>>
                                            <a href="javascript:selectArea('1');"><span>게임</span></a>
                                        </li>
                                        <li <c:if test="${tabid eq '3'}" > class="active"</c:if>>
                                            <a href="javascript:selectArea('3');"><span>문화</span></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="sub_board_header">
                                    <div class="boarder_title">
                                        <span>교육운영 계획 및 커리큘럼</span>
                                    </div>
                                </div>
                                <div class="sub_boarder_body">
                                    <table>
                                        <colgroup>
                                            <col width="8%">
<%--                                             <col width=""> --%>
                                            <col width="auto">
                                            <col width="15%">
                                            <col width="16%">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>No</th>
<!--                                                 <th>분류</th> -->
                                                <th>과정명</th>
                                                <th>총<br />차시</th>
                                                <th>과정소개</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        	<c:forEach var="list" items="${SubjectList }" varStatus="status">
	                                        	<tr>
	                                                <td>${status.index + 1}</td>
<%-- 	                                                <td><c:out value="${list.d_uclassnm }" />&gt;<c:out value="${list.d_mclassnm }" />&gt;<c:out value="${list.d_lclassnm }" /></td> --%>
	                                                <td><c:out value="${list.d_subjnm }" /></td>
	                                                <td><c:out value="${list.d_chisi }" /></td>
	                                                <td>
                                                		<a class="btn_view goto_propose" href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');">
			                                            	상세보기
			                                            </a>
	                                                </td>
	                                            </tr>
                                        	</c:forEach>
                                        </tbody>
                                    </table>
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
