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
    String  p_subgubun     = box.getString("p_subgubun");

    box.put("title_str", "교육사업 안내 - 아카데미소개");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 아카데미 소개 -->
            <div>
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>
                    교육사업 안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu" class="active">교육사업 안내</a></li>
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
            </div>

            <div class="introCategory">
                <ul>
                    <li style="width:445px;<%= p_subgubun.equals("1")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=1">콘텐츠 창의인재 인프라 구축 지원 사업</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("2")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=2">콘텐츠 융합 아카데미 교육과정</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("3")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=3">국내대학 융합형 교육 지원사업</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("4")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=4">현업인 직무 재교육과정</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("5")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=5">해외석사과정지원사업</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("6")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=6">온라인 교육과정</a></li>
                    <li style="width:445px;<%= p_subgubun.equals("7")? "font-weight:bold;" : "" %>"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_subgubun=7">콘텐츠 창의인재 동반사업</a></li>
                </ul>
            </div>

        </div>
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">


            <div id="subCont">
                <div class="privaterule">

                <%
                    if(p_subgubun.equals("1") || p_subgubun.equals("") ){
                %>

                <h2 class="subTit">■ 콘텐츠 창의인재 인프라 구축 지원사업</h2>


                 청소년의 콘텐츠 창작 개발역량의 조기 발굴 및 체계적 육성을 위해 청소년 대상 콘텐츠 창작 공모전을 개최하고, 콘텐츠 창의체험스쿨 운영 지원, 콘텐츠 특성화고 산학연계<br/>프로젝트형 교육운영을 지원하는 사업<br/><br/>

                <p class="title">● 사업목적 </p><br/>
                <div class="txt">
                    콘텐츠를 활용한 체험교육을 통해 청소년의 콘텐츠 창작 개발역량 조기발굴 및 체계적 육성<br/><br/>
                </div>

                <p class="title">● 사업내용</p><br/>
                <div class="txt">
                    - 청소년 대상 콘텐츠 창작 공모전 개최 : 콘텐츠 분야를 중심으로 한 청소년 대상 전국 규모의 공모전 실시<br/>
                    - 콘텐츠 창의체험스쿨 운영 지원 : 청소년들의 콘텐츠 분야에 대한 관심해소와 진로체험 및 재능 발굴을 위해 콘텐츠 창의체험스쿨 지역거점센터 운영 지원(3개소)<br/>
                    - 콘텐츠 특성화고 산학연계 프로젝트형 교육운영 지원 : 콘텐츠 창의인재 조기 육성을 위한 콘텐츠 특성화 고교 산학협력 프로그램 운영 지원(4개교)<br/><br/>
                </div>

                <p class="title">● 추진일정</p><br/>
                <div class="txt">
                    - 콘텐츠 창작 공모전 : 5월 공고 <br/>
                    - 콘텐츠 창의체험스쿨 지역거점센터 지원 : 6월 공고 예정<br/>
                    - 콘텐츠 특성화고 교육운영 지원 : 4월 공고 <br/><br/>
                </div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    - 청소년 대상 콘텐츠 창작 공모전 : 전국 중&middot;고등학교 재학생 <br/>
                    - 콘텐츠 창의체험스쿨 운영 지원 : 청소년들에게 콘텐츠 분야 다양한 창작교육과 직무 체험을 경험할 수 있는 프로그램 운영기관<br/>
                    - 콘텐츠 특성화고 산학연계 프로젝트형 교육운영 지원 : 전국 콘텐츠 관련 특성화고교<br/><br/>
                </div>

                    <p class="title">● 교육문의</p> - 한국콘텐츠진흥원 창의인재양성팀 (02-2161-0065, shcheon@kocca.kr)<br/>
                </div>

                <%
                } else if(p_subgubun.equals("2")){
                %>


                <!-- 콘텐츠 아카데미 교육과정------>
                <h2 class="subTit">■ 콘텐츠 융합 아카데미 교육과정</h2>
                <div class="txt">빅킬러 콘텐츠를 개발할 수 있는 기술간, 콘텐츠간 융합교육을 위해 필수기술교육, 맞춤 멘토링, 시제품 개발을 위한 프로젝트형 교육과정을 통해 기술기반 창업인재양성을 지원
                </div>

                <p class="title">● 교육목적</p><br/>
                <div class="txt">콘텐츠 장르간, 기술간 융합 교육을 통해 새로운 융합콘텐츠를 발굴·개발할 수 있는 창작자 양성 및 스타트업 배출</div>

				<p class="title">● 교육내용</p><br/>
                <div class="txt">다양한 프로젝트를 수행할 수 있는 필수기술교육 + 멘토링(프로젝트 맞춤형 기술 및 비즈니스전략 등) + 프로젝트(시제품 개발지원)</div>
                
                <p class="title">● 교육과정</p><br/>
                <div class="txt">
                    <table border="1" width="640px">
                        <tr>
                            <th>트랙</th>
                            <th>구분</th>
                            <th>기간</th>
                            <th>교육내용</th>
                        </tr>
                        <tr>
                            <td rowspan="4">융합</td>
                            <td>Pre 캠프<br/>(융합 마인드 함양)</td>
                            <td>2주</td>
                            <td>(공통)기업가 정신, 기술트렌드, 미래학, 경제학, UX, 기업분석 등</td>
                        </tr>
                        <tr>
                            <td>1단계</td>
                            <td>3개월<br/>(장르 이해 교육)</td>
                            <td>(공통) 콘텐츠 장르별 사업구조, 제작현황, 소비현황 등 이해<br/>
                            	(3개 파트별) 필수 기술 교육<br/>
                                (팀프로젝트) 콘텐츠 장르별 신사업 발굴 과제 수행<br/>
                            </td>
                        </tr>
                        <tr>
                            <td>2단계<br/>(장르 융합 교육)</td>
                            <td>3개월</td>
                            <td>(공통) 장르 융합 사업추진전략, 마케팅전략, 콜라보 전략 등 이해<br/>
                                (5개 전공별) 필수 기술 교육<br/>
                                (팀프로젝트) 장르 융합형 신사업 발굴 과제 수행<br/>
                            </td>
                        </tr>
                        <tr>
                            <td>3단계<br/>(상업화 검증)</td>
                            <td>2.5개월</td>
                            <td>(팀프로젝트) 신사업 검증용 프로토타입 제작<br/>
                                (프로젝트별) 기술 멘토링<br/>
                            </td>
                        </tr>
                        <tr>
                            <td>창업</td>
                            <td>투자유치용<br/>프로토타입제작</td>
                            <td>9개월</td>
                            <td>(팀프로젝트) 투자유치용 프로토타입 제작<br/>
                                (프로젝트별) 재무 및 경영 컨설팅, 투자유치기법, 사업계획서 등
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="txt">
                    - 콘텐츠 전문기업(카카오, 네이버, 오큘러스VR, 게임개발사 등)과 협업 프로젝트 추진<br/>
                    - 팀별 프로젝트는 허들방식을 도입한 경쟁시스템 도입, 우수 교육생에 한해 콘텐츠진흥원 지원사업 연계
                </div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    웨어러블, IOT, 가상환경, 아트테크, 로보틱스 등 새롭게 등장한 분야의 융합 콘텐츠 창작에 관심이 많고 기업가 정신을 갖춘 청년 70명 내외
                </div>

                <p class="title">● 교육문의</p><br/>
                - 창의인재양성팀 김문주 과장 02-2161-0067 / gale@kocca.kr
                - 창의인재양성팀 안정권 사원 02-2161-0072 / jkwind22@kocca.kr
                </div>

                <%
                } else if(p_subgubun.equals("5")){
                %>

                <h2 class="subTit">■ 해외석사과정 지원사업</h2>

                <div class="txt">
                    융합형 프로젝트 중심의 콘텐츠 관련 해외석사 장학사업으로 카네기멜론대학교(CMU) 석사과정 ETC(엔터테인먼트기술)/HCII(인간-컴퓨터 인터랙션) 과정을 지원
                </div>
                <p class="title">● 교육목적</p><br/>
                <div class="txt">
                    콘텐츠분야 융합형 교육지원을 통해 창의력과 글로벌 경쟁력을 갖춘 핵심인재 양성</div>

                <p class="title">● 교육내용</p><br/>
                <div class="txt">
                    - CMU 등록금(tuition) 50% 지원(학기당 약 $10,000)<br/>
                      ※ 개인부담 : 등록금 차액, 현지체재비 등<br/>
                    - 국비장학생 선발 후 전체학생 대상 오리엔테이션 프로그램 실시<br/>
                      &middot; [오리엔테이션] 현지 적응을 위한 제반 안내, 졸업자 멘토링<br/>
                      &middot; [특강] 석사과정에 유용한 강의 등 추진<br/>
                      &middot; [멘토-멘티 운영] 전년도 장학생-금년도 장학생 연계</div>

                <p class="title">● 교육과정</p><br/>
                <div class="txt">
                    - CMU ETC<br/>
                      &middot; Masters of Entertainment Technology<br/>
                      &middot; 예술과 기술을 융합형 독특한 커리큘럼으로 엔터테인먼트기술 분야 고급 인재 양성 과정<br/>
                      &middot; 2년 과정(4학기)<br/>
                    - CMU HCII<br/>
                      &middot; Masters in Human-Computer Interaction<br/>
                      &middot; 인간과 컴퓨터의 상호작용에 관한 이론과 콘텐츠 응용학문 관련 고급 인재 양성 과정<br/>
                      &middot; 1년 과정(3학기)</div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    한국 국적자로서 콘텐츠 관련 전공 대학졸업자(졸업예정자) 또는 이와 동등한 학력을 겸비한 자로 해당과정 이수 가능자<br/>
                    ※ 졸업 후 콘텐츠 분야 국내 기업 취업 또는 창업&middot;창직 의무화 실천 가능자</div>

                <p class="title">● 교육문의</p><br/>
                - 한국콘텐츠진흥원 창의인재양성팀(02-3219-6527, ejkim@kocca.kr)</div>
                </div>
                <%
                } else if(p_subgubun.equals("3")){
                %>

                <h2 class="subTit">■ 국내대학 융합형 교육 지원사업</h2>
                <div class="txt">
                    콘텐츠 분야의 글로벌 교육환경을 구축하고 융합형 창의인재 양성을 위한 예술-인문-기술의 다학제&middot;산학&middot;해외 연계 교육과정 운영을 지원하는 사업<br/>
                </div>

                <p class="title">● 교육목적</p><br/>
                <div class="txt">
                    콘텐츠분야 융합형 교육지원을 통해 창의력과 글로벌 경쟁력을 갖춘 핵심인재 양성<br/><br/>
                </div>

                <p class="title">● 교육내용</p><br/>
                <div class="txt">
                    단년도 / 다년도(2년) 구분하여 지정/자유공모를 통해 창의적 커리큘럼 기획 / 융합형 교육 과정    운영이 가능한 대학에 1억원 지원<br/><br/>
                </div>

                <p class="title">● 교육과정</p><br/>
                <div class="txt">
                    4~5월 중 공모에 참여한 대학의 선정평가를 통해 지원과정 내용 확정 <br/><br/>
                </div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    콘텐츠 분야 융합형 교육 프로그램 개발 및 관련학과 운영이 가능한 고등교육법에 의한 국내 대학(교) (대학원 포함)<br/><br/>
                </div>

                <p class="title">● 교육문의</p><br/>
                - 한국콘텐츠진흥원 창의인재양성팀(02-2161-0066, ejkim@kocca.kr)</div>


                <%
                } else if(p_subgubun.equals("4")){
                %>

                <!---------------------->
                <h2 class="subTit">■ 현업인 직무 재교육과정</h2>
                <div class="txt">콘텐츠 분야 현업인 대상 전문가 특강, 직무집중과정, 해외연수 등을 통한 업무역량 강화와 전문성 향상을 도모하는 교육과정
                </div>

                <p class="title">● 교육목적</p><br/>
                <div class="txt">콘텐츠 분야 현업인의 직무역량 강화 및 전문성 향상</div>

                <p class="title">● 교육내용</p><br/>
                <div class="txt">
                    <table border="1" width="640px">
                        <tr>
                            <th>과정명</th>
                            <th>교육일정</th>
                            <th>교육형태</th>
                            <th>교육내용</th>
                        </tr>
                        <tr>
                            <td rowspan="3">콘텐츠<br/>창의마스터클래스</td>
                            <td>과정별 주 1회<br/>(6월~11월)</td>
                            <td>전문가 특강</td>
                            <td>&middot; 주제별 장르심화&middot;융합형 집중교육과정 운영<br/>
                                - 트렌드/환경 이해, 신규기술/플랫폼 사례분석 등 12개 과정</td>
                        </tr>
                        <tr>
                            <td>연 2회</td>
                            <td>찾아가는 현장교육</td>
                            <td>&middot; 찾아가는 사내교육<br/>
                                - 콘텐츠 장르특화 기업 사내연수 지원 (2회)
                            </td>
                        </tr>
                        <tr>
                            <td>연 1회</td>
                            <td>해외연수</td>
                            <td>&middot;  콘텐츠 장르별 전략지역 해외연수과정 운영 (1회)<br/>
                                - 현지 기업방문, 비즈니스교류회 등 개최
                            </td>
                        </tr>
                        <tr>
                            <td>콘텐츠 인사이트</td>
                            <td>월 1회 이상<br/>(5월~12월)</td>
                            <td>세미나</td>
                            <td>&middot; 콘텐츠분야 성공, 도전사례 공유 열린 세미나 (8회)<br/>
                                - 콘텐츠 기획, 제작의 협업 노하우 공유
                            </td>
                        </tr>
                    </table>
                </div>

                <p class="title">● 교육과정</p><br/>
                <div class="txt">
                    - 콘텐츠 창의마스터클래스·콘텐츠 인사이트 교육생 모집 : 매월 둘째주 월요일 온라인 모집 <br/>
                    - 찾아가는 현장교육·해외연수 : 6월 중
                </div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    콘텐츠 분야 현업인, 프리랜서 등 교육과정별 지원자격 상이(향후 공고시 자세한 내용 안내예정)
                </div>

                <p class="title">● 교육문의</p><br/>
                - 한국콘텐츠진흥원 창의인재양성팀 02-2161-0072, imim@kocca.kr
                </div>

                <%
                } else if(p_subgubun.equals("5")){
                %>

                <h2 class="subTit">■ 해외석사과정 지원사업</h2>

                <div class="txt">
                    융합형 프로젝트 중심의 콘텐츠 관련 해외석사 장학사업으로 카네기멜론대학교(CMU) 석사과정 ETC(엔터테인먼트기술)/HCII(인간-컴퓨터 인터랙션) 과정을 지원
                </div>
                <p class="title">● 교육목적</p><br/>
                <div class="txt">
                    콘텐츠분야 융합형 교육지원을 통해 창의력과 글로벌 경쟁력을 갖춘 핵심인재 양성</div>

                <p class="title">● 교육내용</p><br/>
                <div class="txt">
                    - CMU 등록금(tuition) 50% 지원(학기당 약 $10,000)<br/>
                      ※ 개인부담 : 등록금 차액, 현지체재비 등<br/>
                    - 국비장학생 선발 후 전체학생 대상 오리엔테이션 프로그램 실시<br/>
                      &middot; [오리엔테이션] 현지 적응을 위한 제반 안내, 졸업자 멘토링<br/>
                      &middot; [특강] 석사과정에 유용한 강의 등 추진<br/>
                      &middot; [멘토-멘티 운영] 전년도 장학생-금년도 장학생 연계</div>

                <p class="title">● 교육과정</p><br/>
                <div class="txt">
                    - CMU ETC<br/>
                      &middot; Masters of Entertainment Technology<br/>
                      &middot; 예술과 기술을 융합형 독특한 커리큘럼으로 엔터테인먼트기술 분야 고급 인재 양성 과정<br/>
                      &middot; 2년 과정(4학기)<br/>
                    - CMU HCII<br/>
                      &middot; Masters in Human-Computer Interaction<br/>
                      &middot; 인간과 컴퓨터의 상호작용에 관한 이론과 콘텐츠 응용학문 관련 고급 인재 양성 과정<br/>
                      &middot; 1년 과정(3학기)</div>

                <p class="title">● 지원자격</p><br/>
                <div class="txt">
                    한국 국적자로서 콘텐츠 관련 전공 대학졸업자(졸업예정자) 또는 이와 동등한 학력을 겸비한 자로 해당과정 이수 가능자<br/>
                    ※ 졸업 후 콘텐츠 분야 국내 기업 취업 또는 창업&middot;창직 의무화 실천 가능자</div>

                <p class="title">● 교육문의</p><br/>
                - 한국콘텐츠진흥원 창의인재양성팀(02-3219-6527, ejkim@kocca.kr)</div>


                <!---■ 온라인교육과정------------------->
                <%
                } else if(p_subgubun.equals("6")){
                %>
                <h2 class="subTit">■ 온라인 교육과정</h2>
                <h4 class="subTit_h4_gray">사이버콘텐츠아카데미  http://edu.kocca.or.kr</h4>
                <!--
                <div class="academiSec gray">
                    <ul>
                        <li> 콘텐츠산업 예비 인력 및 현업종사자, 한국콘텐츠아카데미 회원 대상의 정규 강좌와 열린 강좌</li>
                        <li> 정규과정 분야 131개 과정 서비스</li>
                        <li> 열린강좌 199개 강좌 서비스</li>
                        <li> 분야별 전문 튜터의  튜터링 서비스 제공</li>
                        <li> 전과정 무료 강의</li>
                    </ul>
                </div>
                <h4 class="subTit_h4_gray">교육분야 및 체계</h4>
                <div class="academiSec gray gimg">
                    <ul>
                        <li>기능과 산업을 고려한 다중분류체계 구성</li>
                    </ul>
                </div>
                <h4 class="subTit_h4_gray">교육 신청/선발</h4>
                <div class="academiSec gray">
                    <ul>
                        <li>신청방법 : 온라인 신청 / http://edu.kocca.or.kr</li>
                        <li>신청기준 : 한국콘텐츠아카데미 회원, 신청 회원수 제한 없음</li>
                    </ul>
                </div>

                <h4 class="subTit_h4_gray">교육과정운영</h4>
                <div class="academiSec gray">
                    <ul>
                        <li>교육방법 : 학습자 개인 PC 또는 모바일에서 온라인 학습</li>
                        <li>과정운영 : 매월 2회 개강(1일, 15일</li>
                        <li>운영내용 : 방송영상 / 게임 / 문화콘텐츠 기획, 제작, 비즈니스 분야 등 131개 과정 운영 (열린강좌 199개 강좌 운영)</li>
                        <li>학습기간 : 교육 시작일 기준 학습 1개월, 교육 종료일 기준 복습 3개월</li>
                        <li>교육비용 : 무료</li>
                    </ul>
                </div>
                //-->
                <div style="padding: 15px 0 0 24px; margin-bottom: 25px;line-height: 18px;">
                    <ul>
                        <li> 1. 콘텐츠산업 예비인력과 현업종사자 대상의 열린교육</li>
                        <li> 2. 게임, 방송영상, 문화 분야 과정 서비스</li>
                        <li> 3. 분야별 전문 튜터의 튜터링 서비스 제공</li>
                        <li> 4. <b>전과정 무료</b> 강의. 매월 1일, 15일 2회 과정 오픈</li>
                        <li> 5. 학습기간 : 학습 1개월, 복습 3개월</li>
                    </ul>
                </div>

                <h4 class="subTit_h4_gray">분야별 교육 체계</h4>
                <div style="padding: 15px 0 0 24px; margin-bottom: 25px;line-height: 18px;">
                    <ul>
                        <li>콘텐츠 사업군과 직무를 고려한 Learning Map</li>
                        <li><img src="/images/2013/introduce/intro_gaphic6.jpg"></li>
                    </ul>
                </div>

                <h4 class="subTit_h4_gray">기업/단체 맞춤교육</h4>
                <div style="padding: 15px 0 0 24px; margin-bottom: 25px;line-height: 18px;">
                    <ul>
                        <li><img src="/images/2013/introduce/intro_gaphic7.jpg"></li>
                        <li style="float: right;margin-right: 140px;margin-bottom: 25px;">
                            <a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus"><img src="/images/2013/introduce/btn_contact.jpg"></a>
                        </li>
                    </ul>
                </div>



                <h4 class="subTit_h4_gray">교육문의</h4>
                <div class="academiSec gray">
                    <ul>
                        <li>한국콘텐츠아카데미 온라인교육팀<br/>
                        Tel : 02-2161-0077<br/>
                        e-Mail : academy@kocca.kr</li>
                    </ul>
                </div>
                </div>
                <!---■ 해외석사과정 지원사업------------------->
                <%
                } else if(p_subgubun.equals("7")){
                %>

                <!---------------------->
                <h2 class="subTit">■ 콘텐츠 창의인재 동반사업</h2>

                <div class="txt">
                    콘텐츠 창조분야에 특화된 장기간의 멘토링 숙련과정을 지속 운영하여 창의인재의 장기적 육성과 청년일자리 창출을 지원
                </div>

                <p class="title">● 교육목적 </p><br/>
                <div class="txt">콘텐츠 분야별 전문가(멘토)의 도제식 창의숙성과정을 통한 청년 인재의 창작능력 개발과 일자리 창출 
                </div>

                <p class="title">● 교육내용 </p><br/>
                <div class="txt">
                - 미래 가능성 있는 창작자 양성을 위해 플랫폼기관의 네트워킹과 콘텐츠분야의 최고 전문가에 의한 프로젝트 중심의 현장 밀착형 멘토링 지원<br/>
				- 창의교육생(멘티)은 플랫폼 기관과 근로계약을 체결하고, 멘토와 플랫폼 기관이 진행하는 다양한 프로젝트에 참여(매월 약 116만원 지급, 4대보험 포함)<br/>
                - 플랫폼 기관과 멘토를 통해 창의교육생의 프로젝트 개발 및 창업, 진로상담, 취업지원, 등 다양한 경력경로 코칭 
                </div>

                <p class="title">● 교육과정 </p><br/>
                <div class="txt">
                    멘토 프로젝트 참여 및 멘티 프로젝트 개발, 플랫폼기관의 창의숙련과정 등을 통한 창작역량 개발프로그램 운영 
                </div>

                <p class="title">● 지원자격 </p><br/>
                <div class="txt">
                - 플랫폼 기관 : 콘텐츠 창조분야의 도제식 멘토링/훈련이 가능한 설립 3년 이상의 법인<br/>
                - 멘토 : 해당분야 10년 내외의 경력을 쌓은 전문가(플랫폼 기관에서 위촉)<br/>
                - 창의교육생(멘티) : 창작의 소질과 소양을 갖춘 예비취업자(만 18세～35세로 창작분야 종사 희망자를 서류/면접을 통해 선발)<br/>
                </div>

                <p class="title">● 교육문의</p><br/>
                - 한국콘텐츠진흥원 창의인재양성팀(02-2161-0061, hskim@kocca.kr)
                </div>
                <%
                }
                %>



            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>