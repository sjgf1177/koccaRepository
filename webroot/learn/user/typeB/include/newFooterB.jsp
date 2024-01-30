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
                                <span class="footer_util_item"><a href="https://www.kocca.kr/kocca/subPage.do?menuNo=204912" target="_blank">개인정보처리방침</a></span>
<%--                            <hr class="footer_util_line">--%>
                                <span class="footer_util_item"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL_TYPEB');">이메일무단수집거부</a></span>
<!--                             <hr class="footer_util_line"> -->
<!--                             <span class="footer_util_item"><a href="javascript:mainmenu('50');">아카데미 소개</a></span> -->
                            </div>

                            <div class="app_download">
                                <!-- <a href="https://edu.kocca.kr/edu/main/contents.do?menuNo=500091" target="_blank" style="display:inline-block; float: right; margin-left:20px;">
                                    <img src="/common/image/icon_kocca_app.png" alt="아카데미앱 다운로드" style="width: 27px;"> 아카데미앱 다운로드
                                </a> -->
                                <c:if test='${sessionScope.tem_grcode ne "N000134"}'>
                                    <a href="https://edu.kocca.kr" target="_blank" style="display:inline-block;">
                                        <img src="/common/image/edukocca_link.png" alt="아카데미 바로가기">
                                    </a>
                                </c:if>
                                <c:if test='${sessionScope.tem_grcode eq "N000134"}'>
                                    <a href="https://www.facebook.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_facebook_black.png" alt="페이스북 바로가기">
                                    </a>
                                    <a href="https://twitter.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_twitter_black.png" alt="트위터 바로가기">
                                    </a>
                                    <a href="https://blog.naver.com/edukocca" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_blog_black.png" alt="블로그 바로가기">
                                    </a>
                                    <a href="https://www.youtube.com/channel/UCJ78W_fNjOW7A-lZ6uEClgA" target="_blank" style="display:inline-block; margin-right: 15px;">
                                        <img src="/common/image/ico_youtube_black.png" alt="유튜브 바로가기">
                                    </a>
                                    <a href="https://www.instagram.com/edu.kocca/" target="_blank" style="display:inline-block;">
                                        <img src="/common/image/ico_instar_black.png" alt="인스타그램 바로가기">
                                    </a>
                                </c:if>
                            </div>
                            <!--
                            <div class="footer_relation_site">
                                <select class="relation_site" name="relation_site" id="relation_site" onchange="window.open(this.options[this.selectedIndex].value,'_blank')">
                                    <option value="">관련사이트</option>
                                    <option value="https://edu.kocca.kr">콘텐츠인재캠퍼스</option>
                                </select>
                            </div>
                            -->
                        </div>

                        <div class="" style="margin: 40px 0 20px 0;">
                            <img alt="KOCCA 한국콘텐츠진흥원" src="/common/image/footer_benner.png" style="width: 120px; height: 56px; margin: 0;">
                        </div>

                        <div class="footer_copyright">
                            <p>본 페이지에 게시된 이메일 주소가 자동 수집되는 것을 거부하며, 이를 위반 시 정보통신법에 의해 처벌됨을 유념하시기 바랍니다.</p>
                            <ul>
                                <li>홍릉분원 : [02456] 서울특별시 동대문구 회기로 66</li>
                                <li>본원 : [58217] 전라남도 나주시 교육길 35 한국콘텐츠진흥원 | 사업자번호 : 105-82-17272</li>
<%--                                <li>온라인교육 02-6310-0770</li>--%>

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