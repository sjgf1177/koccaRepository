<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />


<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                	<jsp:include page="/learn/user/typeB/include_left/left_9.jsp">
                    	<jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>수강신청안내</span>
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
                                            <a href="#"><span>수강신청</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>수강신청안내</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="courseRagistrationGuid">
                                    <span>1.</span><p>로그인후, 수강신청 메뉴로 이동 합니다. 과정명을 클릭하면 과정안내 페이지로 이동이 됩니다.</p>
                                    <img src="/common/image/online/guide/guide_01.png" alt="" style="border: 1px solid;">
                                    <span>2.</span><p>과정에 대해 상세 안내 페이지 입니다. [수강신청] 버튼을 클릭하여 수강신청을 합니다.</p>
                                    <img src="/common/image/online/guide/guide_02.png" alt="" style="border: 1px solid;">
                                    <span>3.</span><p>수강신청 화면 입니다. 휴대전화 및 이메일 주소를 확인후,[수강신청] 버튼을 클릭하면 수강신청이 완료 됩니다.</p>
                                    <img src="/common/image/online/guide/guide_03.png" alt="" style="border: 1px solid;">
                                    <span>4.</span><p>수강신청확인/취소 화면 입니다. 현재 수강신청 중인 과정을 확인할 수 있으며, 신청기간 중 수강취소를 할 수 있습니다.</p>
                                    <img src="/common/image/online/guide/guide_04.png" alt="" style="border: 1px solid;">
                                    <span>5.</span><p>학습기간이 시작되면 [나의 강의실] > [수강중인과정]에서 수강신청 하신 과정을 볼 수 있습니다. [학습하기]를 클릭하여 학습을 시작하실 수 있습니다.(단, 학습은 학습시작일 이후에만 가능합니다.)</p>
                                    <img src="/common/image/online/guide/guide_05.png" alt="" style="border: 1px solid;">
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