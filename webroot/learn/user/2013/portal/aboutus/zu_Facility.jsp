<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "시설소개 - 아카데미소개");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 아카데미 소개 -->
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>
                    시설소개 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility" class="active">시설소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
<!-- 2015-01-08 개편삭제
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                    </ul>
                </li>
            </ul>
<!-- 2015-01-08 개편삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility"><strong><u>시설소개</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>
-->
        </div>


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit">시설소개<!--<img src="../images/2013/introduce/facility_title.gif" alt="시설소개" />//--></h2>

            <div id="subCont">

                <!-- <h4 class="subTit_h4_gray">영상촬영 등 교육 지원시설</h4>
                <div class="tableWrap2">
                    <table class="layoutTable space3">
                        <colgroup>
                            <col width="22%"/>
                            <col width="36%"/>
                            <col width="*"/>
                        </colgroup>
                        <tr>
                            <th scope="col">주요시설</th>
                            <th scope="col">내역, 규모</th>
                            <th scope="col">설치장비</th>
                        </tr>
                        <tr>
                            <td>촬영장비실 등</td>
                            <td class="left">
                                촬영기기 및 부대장비
                            </td>
                            <td class="left">
                                <ul>
                                    <li>- 연수용 카메라 및 편집 장비류</li>
                                    <li>ㆍHDW Carmera (HDW-750 3대, HDW-F900 2대),</li>
                                    <li>- HDTV Digital Recorder & Player</li>
                                    <li>ㆍHDTV Monitor, HDTV Waveform Monitor, CG (HD)</li>
                                    <li>ㆍHDTV W / S, V / S Monitor, PGM Monitor,</li>
                                    <li>- SYC PLUSE GEN / Astro</li>
                                    <li>ㆍHDV Camera (HVR-ZIN 19대, HVR-V1 5대),</li>
                                    <li>- DVCAM카메라</li>
                                    <li>ㆍHDV Camera (JVC 100U 1대) Tripod,</li>
                                    <li>- 스탠다드, 패데스탈 등</li>
                                    <li>ㆍCAMERA (CanonEOS7D,<br />
                                    &nbsp;&nbsp;&nbsp;CannonEOS5DMarkII 3D촬영 세트)</li>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>-->
                <!-- //tableWrap2 -->

                <h4 class="subTit_h4_gray">아카데미시설</h4>
                <div class="tableWrap2 noclose">
                    <table class="layoutTable space4">
                        <colgroup>
                            <col width="15%"/>
                            <col width="*"/>
                        </colgroup>
                        <tr class="line2">
                            <th scope="row" class="th_tit">8F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>창의인재양성팀</li>
                                </ul>
                                <ul class="listTy">
                                    <li>아카데미 도서관</li>
                                </ul>
                                <ul class="listTy">
                                    <li>현업인 직무과정 강의실</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">7F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>게임 그래픽 강의실</li>
                                    <li>게임 프로젝트 강의실</li>
                                </ul>
                                <ul class="listTy">
                                    <li>게임 디자인 기획</li>
                                    <li>게임 프로젝트 개발실</li>
                                </ul>
                                <ul class="listTy">
                                    <li>게임 세미나실</li>
                                    <li>기획창작 강의실</li>
                                </ul>
                                <ul class="listTy">
                                    <li>게임 프로그래밍 강의실</li>
                                    <li>취업 상담실</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">5F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>Digital Editing Room</li>
                                </ul>
                                <ul class="listTy">
                                    <li>S3D Graphic Room</li>
                                </ul>
                                <ul class="listTy">
                                    <li>멀티미디어강의실</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">4F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>Digital Editing Room I</li>
                                </ul>
                                <ul class="listTy">
                                    <li>Digital Editing Room II</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">3F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>회견장</li>
                                </ul>
                                <ul class="listTy">
                                    <li>회의장</li>
                                </ul>
                                <ul class="listTy">
                                    <li>국제회의장</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">2F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>S3D Post-Production</li>
                                </ul>
                                <ul class="listTy">
                                    <li>S3D Pre-Production</li>
                                </ul>
                                <ul class="listTy">
                                    <li>S3D Production</li>
                                </ul>
                            </td>
                        </tr>
                        <tr class="line2">
                            <th scope="row" class="th_tit">1F</th>
                            <td class="left">
                                <ul class="listTy">
                                    <li>S3D Training Center</li>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- //tableWrap2 -->
            </div>
            <!-- //#subCont -->



        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>