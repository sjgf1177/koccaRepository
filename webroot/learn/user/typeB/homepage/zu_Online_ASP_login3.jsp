<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_0.jsp">
                    	<jsp:param value="1" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>회원가입</span>
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
                                            <a href="#"><span>회원/로그인</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>회원가입</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body join_box">
                                    <div class="signUp_step_bar">
                                        <div class="step_wrap">
                                            <p>
                                                <span>STEP1</span>
                                                이용약관 동의
                                            </p>
                                        </div>
                                        <div class="step_line_wrap">
                                            <div class="step_right_arrow"></div>
                                        </div>
                                        <div class="step_wrap">
                                            <p>
                                                <span>STEP2</span>
                                                회원정보 입력
                                            </p>
                                        </div>
                                        <div class="step_line_wrap">
                                            <div class="step_right_arrow"></div>
                                        </div>
                                        <div class="step_wrap active">
                                            <p>
                                                <span>STEP3</span>
                                                가입완료
                                            </p>
                                        </div>
                                    </div>
                                    <div class="signup_complete">
                                        <span><c:out value="${param.p_kor_name }" />님의 회원가입이 완료되었습니다.</span>
                                    </div>
                                    <div class="agree_btn_box btn_complete">
                                        <a href="/" class="btn_home">홈으로</a>
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