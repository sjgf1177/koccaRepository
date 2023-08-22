<%@ page contentType="text/html;charset=euc-kr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
	<footer>
        <div class="wrapper">
            <div class="container">
                <div class="row footer_wrap">
                	<div class="col-md-1 dn">
                		<img alt="KOCCA �ѱ������������" src="/common/image/kocca_logo.png" style="width: 100%; padding-right: 10px;">
                	</div>
                    <div class="col-md-7 footer_text_con">
                        <div class="footer_util_text">
                            <span class="footer_util_item"><a href="http://www.kocca.kr/cop/contents.do?menuNo=200931" target="_blank">��������ó����ħ</a></span>
                            <hr class="footer_util_line">
                            <span class="footer_util_item"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL_TYPEB');">�̸��Ϲ��ܼ����ź�</a></span>
<!--                             <hr class="footer_util_line"> -->
<!--                             <span class="footer_util_item"><a href="javascript:mainmenu('50');">��ī���� �Ұ�</a></span> -->
                        </div>
                        <div class="footer_copyright">
                            <p>�� �������� �Խõ� �̸��� �ּҰ� �ڵ� �����Ǵ� ���� �ź��ϸ�, �̸� ���� �� ������Ź��� ���� ó������ �����Ͻñ� �ٶ��ϴ�.</p>
                            <ul>
                                <li>ȫ���п� : [02456] ����Ư���� ���빮�� ȸ��� 66</li>
                                <li>���� : [58217] ���󳲵� ���ֽ� ������ 35 �ѱ������������ | ����ڹ�ȣ : 105-82-17272</li>
                                <li>�¶��α��� 02-6310-0770</li>
                                <li>Copyright 2015. Korea Creative Content Agency all rights reserved.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-md-4 footer_icon_con">
                        <div class="app_download">
                            <!-- <a href="https://edu.kocca.kr/edu/main/contents.do?menuNo=500091" target="_blank" style="display:inline-block; float: right; margin-left:20px;">
                                <img src="/common/image/icon_kocca_app.png" alt="��ī���̾� �ٿ�ε�" style="width: 27px;"> ��ī���̾� �ٿ�ε�
                            </a> -->
                            <c:if test='${sessionScope.tem_grcode ne "N000134"}'>
                        	<a href="https://edu.kocca.kr" target="_blank" style="display:inline-block; float: right;">
                                <img src="/common/image/icon_edukocca_web.png" alt="��ī���� �ٷΰ���" style="width: 27px;"> ������ī �ٷΰ���
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
                </div>
            </div>
        </div>
    </footer>

<style>
    @media screen and (max-width: 640px) {
        .app_download{
            margin-top: 15px;
        }
    }
</style>