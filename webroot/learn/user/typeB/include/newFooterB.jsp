<%@ page contentType="text/html;charset=euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
	<footer class="<c:if test="${param.gubun ne '2' and param.gubun ne '3' and param.gubun ne '4' and param.gubun ne '10'}">d-flex</c:if>">
        <div></div>
        <div class="wrapper">
            <div class="">
                <div class="row footer_wrap">

                    <div class=footer_back_con">
                        <div class="footer_util_top d-flex justify-content-between">
                            <div style="margin: 0;">
                                <span class="footer_util_item"><a href="https://www.kocca.kr/kocca/subPage.do?menuNo=204912" target="_blank">��������ó����ħ</a></span>
<%--                            <hr class="footer_util_line">--%>
                                <span class="footer_util_item"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL_TYPEB');">�̸��Ϲ��ܼ����ź�</a></span>
<!--                             <hr class="footer_util_line"> -->
<!--                             <span class="footer_util_item"><a href="javascript:mainmenu('50');">��ī���� �Ұ�</a></span> -->
                            </div>

                            <div class="app_download">
                                <!-- <a href="https://edu.kocca.kr/edu/main/contents.do?menuNo=500091" target="_blank" style="display:inline-block; float: right; margin-left:20px;">
                                    <img src="/common/image/icon_kocca_app.png" alt="��ī���̾� �ٿ�ε�" style="width: 27px;"> ��ī���̾� �ٿ�ε�
                                </a> -->
                                <c:if test='${sessionScope.tem_grcode ne "N000134"}'>
                                    <a href="https://edu.kocca.kr" target="_blank" style="display:inline-block;">
                                        <img src="/common/image/edukocca_link.png" alt="��ī���� �ٷΰ���">
                                    </a>
                                </c:if>
                                <c:if test='${sessionScope.tem_grcode eq "N000134"}'>
                                    <a href="https://www.facebook.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_facebook_black.png" alt="���̽��� �ٷΰ���">
                                    </a>
                                    <a href="https://twitter.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_twitter_black.png" alt="Ʈ���� �ٷΰ���">
                                    </a>
                                    <a href="https://blog.naver.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_blog_black.png" alt="��α� �ٷΰ���">
                                    </a>
                                    <a href="https://www.youtube.com/channel/UCJ78W_fNjOW7A-lZ6uEClgA" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_youtube_black.png" alt="��Ʃ�� �ٷΰ���">
                                    </a>
                                    <a href="https://www.instagram.com/edu.kocca/" target="_blank" style="display:inline-block;">
                                        <img src="/common/image/ico_instar_black.png" alt="�ν�Ÿ�׷� �ٷΰ���">
                                    </a>
                                </c:if>
                            </div>
                            <!--
                            <div class="footer_relation_site">
                                <select class="relation_site" name="relation_site" id="relation_site" onchange="window.open(this.options[this.selectedIndex].value,'_blank')">
                                    <option value="">���û���Ʈ</option>
                                    <option value="https://edu.kocca.kr">����������ķ�۽�</option>
                                </select>
                            </div>
                            -->
                        </div>

                        <div class="" style="margin: 40px 0 20px 0;">
                            <img alt="KOCCA �ѱ������������" src="/common/image/footer_benner.png" style="width: 120px; height: 56px; margin: 0;">
                        </div>

                        <div class="footer_copyright">
                            <p>�� �������� �Խõ� �̸��� �ּҰ� �ڵ� �����Ǵ� ���� �ź��ϸ�, �̸� ���� �� ������Ź��� ���� ó������ �����Ͻñ� �ٶ��ϴ�.</p>
                            <ul>
                                <li>ȫ���п� : [02456] ����Ư���� ���빮�� ȸ��� 66</li>
                                <li>���� : [58217] ���󳲵� ���ֽ� ������ 35 �ѱ������������ | ����ڹ�ȣ : 105-82-17272</li>
<%--                                <li>�¶��α��� 02-6310-0770</li>--%>

                                <li style="margin-top: 30px">Copyright 2015. Korea Creative Content Agency all rights reserved.</li>
                            </ul>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </footer>

<style>

</style>