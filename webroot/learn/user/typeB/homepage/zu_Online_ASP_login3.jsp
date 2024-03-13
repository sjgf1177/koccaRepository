<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12 mxw-640">
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <h1>ȸ������</h1>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body join_box">
                                    <div class="signUp_step_bar">
                                        <div class="step_wrap">
                                            <p>
                                                <i class="bi bi-check-circle"></i>
                                                �������
                                            </p>
                                        </div>

                                        <div class="step_wrap">
                                            <p>
                                                <i class="bi bi-check-circle"></i>
                                                �����Է�
                                            </p>
                                        </div>

                                        <div class="step_wrap active">
                                            <p>
                                                <i class="bi bi-check-circle-fill"></i>
                                                ���ԿϷ�
                                            </p>
                                        </div>
                                    </div>
                                    <div class="signup_complete">
                                        <span>
                                            <c:if test="${not empty param.p_kor_name }">
                                                <c:out value="${param.p_kor_name }" />����
                                            </c:if>
                                            <c:if test="${not empty requestbox.p_kor_name }">
                                                <c:out value="${requestbox.p_kor_name }" />����
                                            </c:if>
                                            ȸ�������� �Ϸ�Ǿ����ϴ�.
                                        </span>
                                    </div>
                                    <div class="agree_btn_box btn_complete">
                                        <a href="javascript:mainmenu('990');" class="btn btn-primary">Ȩ����</a>
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