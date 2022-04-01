<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "개인정보처리방침 - 이용안내");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내 
                <!--
                    <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>-->
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>

                    -->
                </li>
                <li>
                    개인정보처리방침 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <!--<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>-->
<%
    if ( !isUserLogin ) {
%>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
<%
    }
%>
                        <!--<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>-->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew" class="active">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
            </ul>
            <!-- 서브메인용 -->
            <div class="introCategory">
                <ul>
                    <!--<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>-->
<%
    if ( !isUserLogin ) {
%>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
<%
    }
%>
                    <!--<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>-->
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew"><strong><u>개인정보처리방침</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </div>
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">개인정보처리방침<!--<img src="/images/2013/support/privaterule_title.gif" alt="개인정보처리방침" />//--></h2>

            <div id="subCont">
                <div class="noticBox lt">
                    한국콘텐츠진흥원 콘텐츠아카데미(이하 "콘텐츠아카데미"가 취급하는 모든 개인정보는 관련법령에 근거하거나 정보주체의 동의에 의하여 수집
                    · 보유 및 처리되고 있습니다.<br />
                    「개인정보보호법」은 이러한 개인정보의 취급에 대한 일반적 규범을 제시하고 있으며 , 콘텐츠아카데미는 이러한 법령의 규정에 따라 수집 ·보유
                    및 처리하는 개인정보를 공공업무의 적절한 수행과 국민의 권익을 보호하기 위해 적법하고 적정하게 취급할 것입니다. <br />
                    또한, 콘텐츠아카데미는 관련 법령에서 규정한 바에 따라 콘텐츠아카데미에서 보유하고 있는 개인정보에 대한 열람청구권 및 정정청구권 등
                    여러분의 권익을 존중하며, 여러분은 이러한 법령상 권익의 침해 등에 대하여 행정심판법에서 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br /><br />

                    콘텐츠아카데미의 개인정보처리방침은 콘텐츠아카데미가 운영하는 여러 홈페이지에서 이용자 여러분의 개인정보를 보호하기 위한「 홈페이지
                    이용자의 개인정보보호 」와 소관업무를 수행하는데 필요한 개인정보 취급에 대한「 컴퓨터에 의해 처리되는 개인정보 보호 」, 「 영상정보처
                    리기기에 의해 처리되는 개인정보 보호 」 세 가지로 구성되어 있습니다.
                </div>

                <div class="privaterule">
                    <h3 class="subTit2">Ⅰ. 홈페이지 이용자의 개인정보 보호</h3>
                    <div class="txt">
                        <p class="title">자동으로 수집, 저장되는 정보 </p>
                        여러분이 콘텐츠아카데미 홈페이지를 이용할 경우 다음의 정보는 자동적으로 수집, 저장됩니다.<br />
                        이용자 여러분의 인터넷 도메인명과 콘텐츠아카데미 홈페이지를 방문할 때 거친 웹사이트의 주소 <br />
                        이용자의 브라우저 종류 및 OS <br />
                        방문일시 등 <br />
                        위와 같이 자동 수집·저장되는 정보는 이용자 여러분에게 보다 나은 서비스를 제공하기 위해 홈페이지의 개선과 보완을 위한 통계분석, 이용자와 웹사이
                        트간의 원활한 의사소통 등을 위해 이용되어질 것입니다. 다만, 법령의 규정에 따라 이러한 정보를 제출하게 되어 있을 경우도 있다는 것을 유념<br />하시기 바랍니다.
                    </div>

                    <p class="title">이메일 및 웹 서식 등을 통한 수집정보 </p>
                    <div class="txt">
                    이용자 여러분은 우편, 전화 또는 온라인 전자서식 등을 통한 전자적 방법을 통해 의사를 표시할 수 있습니다. 이러한 방법의 선택에 있어 몇 가지 유의
                    사항을 알려드립니다.
                    여러분이 홈페이지에 기재한 사항은 다른 사람들이 조회 또는 열람할 수도 있습니다.
                    여러분이 기재한 사항은 관련 법규에 근거하여 필요한 다른 사람과 공유될 수 있으며, 관련법령의 시행과 정책개발의 자료로도 사용될 수 있습니다.
                    또한, 이러한 정보는 타 부처와 공유되거나 , 필요에 의하여 제공될 수도 있습니다.
                    홈페이지 보안을 위해 관리적·기술적 노력을 하고 있으나, 만약의 침해사고 시 문제가 될 수 있는 민감한 정보의 기재는 피해주시기 바랍니다.
                    </div>

                    <p class="title">웹사이트에서 운영하는 보안조치 </p>
                    <div class="txt">
                    홈페이지의 보안 또는 지속적인 서비스를 위해, 콘텐츠아카데미는 네트워크 트래픽의 통제(Monitor)는 물론 불법적으로 정보를 변경하는 등의 시도를 탐
                    지하기 위해 여러 가지 프로그램을 운영하고 있습니다.
                    여러분이 홈페이지에 기재한 사항은 다른 사람들이 조회 또는 열람할 수도 있습니다.
                    이용자 여러분이 홈페이지에 기재한 내용 중 개인정보가 포함되어 있는 경우 개인정보를 삭제 조치 후 게시하여야 합니다.
                    </div>

                    <div class="bdbox">"개인정보"란 살아 있는 개인에 관한 정보로서 성명, 주민등록번호 및 영상 등을 통하여 개인을 알아볼 수 있는 정보(해당 정보만으로는 특정 개인
                    을 알아볼 수 없더라도 다른 정보와 쉽게 결합하여 알아볼 수 있는 것을 포함한다.)를 말한다. <개인정보보호법 제2조제1항> </div>

                    <p class="title">링크 사이트 · 웹 페이지 </p>
                    <div class="txt">
                    콘텐츠아카데미가 운영하는 여러 웹페이지에 포함된 링크 또는 배너를 클릭하여 다른 사이트 또는 웹페이지로 옮겨갈 경우 개인정보보호방침은 그 사이
                    트 운영기관이 게시한 방침이 적용되므로 새로 방문한 사이트의 방침을 확인하시기 바랍니다.
                    </div>


                    <p class="title">웹사이트 이용 중 다른 사람의 개인정보 취득 </p>
                    <div class="txt">콘텐츠아카데미가 운영하는 웹사이트에서 이메일 주소 등 식별할 수 있는 개인정보를 취득하여서는 아니 됩니다. 허위, 기타 부정한 방법으로 이러한
                    개인정보를 열람 또는 제공받은 자는 관계 법규에 의하여 처벌을 받을 수 있습니다.
                    </div>

                    <h3 class="subTit2">Ⅱ. 컴퓨터에 의해 처리되는 개인정보보호</h3>

                    <p class="title">개인정보의 수집 및 보유 </p>
                    <div class="txt">
                    콘텐츠아카데미는 법령의 규정과 정보주체의 동의에 의해서만 개인정보를 수집·보유합니다.
                    콘텐츠아카데미에서 법령의 규정에 근거하여 수집·보유하고 있는 주요 개인정보파일은 다음과 같습니다.
                    </div>

                    <table class="dataTable7" summary="이표는 개인정보의 수집 및 보유 표로 보유목적 보유근거 수집방법 대상범위 대상인원수 보유기간 열람예정일 사용부서 열람청구부서 및 주소 열람제한 항목 열람제한 사유 제공기관 제공근거 제공항목 기록항목 으로 구성되어 있습니다.">
                        <caption>개인정보의 수집 및 보유</caption>
                        <colgroup>
                            <col style="width:20%" />
                            <col />
                            <col style="width:20%" />
                            <col />
                        </colgroup>
                        <tr class="first">
                            <th scope="row">보유목적</th>
                            <td colspan="3">홈페이지 서비스 제공을 위한 회원관리</td>
                        </tr>
                        <tr>
                            <th scope="row">보유근거</th>
                            <td colspan="3">개인정보보호법</td>
                        </tr>
                        <tr>
                            <th scope="row">수집방법</th>
                            <td colspan="3">홈페이지를 통한 개인동의</td>
                        </tr>
                        <tr>
                            <th scope="row">대상범위</th>
                            <td>홈페이지를 통한 개인동의</td>
                            <th scope="row">대상인원수</th>
                            <td>56,019명</td>
                        </tr>
                        <tr>
                            <th scope="row">보유기간</th>
                            <td>정보주체인 개인이 직접 삭제여부관리</td>
                            <th scope="row">열람예정일</th>
                            <td>수시</td>
                        </tr>
                        <tr>
                            <th scope="row">사용부서</th>
                            <td colspan="3">해당 업무부서의 담당자와 시스템 관리자만 접근하여 활용할 수 있다.</td>
                        </tr>
                        <tr>
                            <th scope="row">열람청구부서 및 주소</th>
                            <td colspan="3">부서 - 제작인력양성팀, 주소 - 서울 양천구 목동동로 233 방송회관 8층</td>
                        </tr>
                        <tr>
                            <th scope="row">열람제한 항목</th>
                            <td>없음</td>
                            <th scope="row">열람제한 사유</th>
                            <td>없음</td>
                        </tr>
                        <tr>
                            <th scope="row">제공기관</th>
                            <td>없음</td>
                            <th scope="row">제공근거</th>
                            <td>없음</td>
                        </tr>
                        <tr>
                            <th scope="row">제공항목</th>
                            <td colspan="3">없음</td>
                        </tr>
                        <tr>
                            <th scope="row">기록항목</th>
                            <td colspan="3">성명,아이디,비밀번호,전자우편,연락처,주소,직종,종사분야,소속</td>
                        </tr>
                    </table>
                    <p class="tblbtm">콘텐츠아카데미가 보유하고 있는 국민 여러분의 개인정보를 관계법령에 따라 적법하고 적정하게 처리하여, 권익이 침해 받지 않도록 노력할 것입니다.</p>

                    <p class="title">개인정보의 이용 및 제공의 제한 </p>
                    <div class="txt">
                        콘텐츠아카데미가 수집·보유하고 있는 개인정보는 일반 행정정보와 달리 이용 및 제공에 엄격한 제한이 있는 정보입니다. <br />
                        「개인정보보호법」제18조(개인정보의 허용 제공 제한)에 의거 수집, 보유 가능한 경우 <br />
                        정보주체로부터 별도의 동의를 받은 경우
                        정보주체 또는 그 법정대리인이 의사표시를 할 수 없는 상태에 있거나 주소불명 등으로 사전 동의를 받을 수 없는 경우로서 명백히 정보주체 또는 제3자<br />
                        의 급박한 생명, 신체, 재산의 이익을 위하여 필요하다고 인정되는 경우 <br />
                        통계작성 및 학술연구 등의 목적을 위하여 필요한 경우로서 특정 개인을 알아볼 수 없는 형태로 개인정보를 제공하는 경우 <br />
                        개인정보를 목적 외의 용도로 이용하거나 이를 제3자에게 제공하지 아니하면 다른 법률에서 정하는 소관 업무를 수행할 수 없는 경우로서 보호위원회의 <br />
                        심의·의결을 거친 경우 <br />
                        조약, 그 밖의 국제협정의 이행을 위하여 외국정부 또는 구제기구에 제공하기 위하여 필요한 경우 <br />
                        범죄의 수사와 공소의 제기 및 유지를 위하여 필요한 경우 <br />
                        법원의 재판 업무 수행을 위하여 필요한 경우 <br />
                        형(刑) 및 감호, 보호처분의 집행을 위하여 필요한 경우 <br />
                        기타 대통령령이 정하는 특별한 사유가 있는경우
                        콘텐츠아카데미는 위 법령 및 기타 개별법에 근거하여 다른 기관에 제공하는 개인정보 현황은 없으며, 국민 여러분의 개인정보를 관계법령에 따라 적법
                        하고 적정하게 처리하여, 권익이 침해 받지 않도록 노력할 것입니다.
                        콘텐츠아카데미는 개인정보의 이용 및 제공에 있어 관계법령을 엄수하여 부당하게 이용되지 않도록 노력하겠습니다.
                    </div>

                    <p class="title">개인정보파일의 열람 및 정정청구 </p>
                    <div class="txt">
                        콘텐츠아카데미가 보유하고 있는 개인정보화일은 「개인정보보호법」 <br />
                        (다른 법률에 규정이 있는 경우는 해당 법률)의 구정이 정하는 바에 따라 열람을 청구할 수 있습니다. <br />
                        열람청구 절차(「개인정보보호법」의 경우) 다음사항은 법 제35조 규정에 의하여 열람을 제한 할 수 있습니다. <br />
                        다음 사항에 해당하는 업무로서 당해업무의 수행에 중대한 지장을 초래하는 경우<br />
                        조세의 부과·징수 또는 환급에 관한 업무 <br />
                        학력·기능 및 채용에 관한 시험, 자격의 심사, 보상금·급부금의 산정 등 평가 또는 판단에 관한 업무 <br />
                        다른 법률에 따라 진행 중인 감사 및 조사에 관한 업무 <br />
                        다른 사람의 생명·신체를 해할 우려가 있거나 다른 사람의 재산과 기타 이익을 부당하게 침해할 우려가 있는 경우<br />
                        본인의 개인정보를 열람한 정보주체는 다음의 경우 정정을 청구할 수 있습니다. <br />
                        기타 대통령령에 정하는 사유
                    </div>

                    <p class="title">정정 청구의 범위(삭제요청) </p>
                    <div class="txt">
                        사실과 다르게 기록된 정보의 정정<br />
                        특정항목에 해당사실이 없는 내용에 대한 삭제 <br />
                        정정 청구 방법(「공공기관의 개인정보보호에 관한 법률」의 경우) <br />
                        정정·삭제청구서(별지 제8호 서식)을 작성하여 해당 개인정보파일 부서에 제출
                    </div>

                    <p class="title">개인정보의 파기 절차 및 방법  </p>
                    <div class="txt">
                        정보통신망이용촉진및정보보호등에관한법률 등 관련 법률 및/또는 본 개인정보처리방침에서 예외적으로 보관하는 것으로 정한 바를 제외하고는 아카데<br />
                        미는 개인 정보의 수집 및 이용목적이 달성된 후에는 해당 정보를 지체 없이 파기합니다. 파기 절차 및 방법은 다음과 같습니다.<br />
                        1. 파기 절차 <br />
                        - 귀하께서 서비스 가입 등을 위해 입력하신 정보는 목적이 달성된 후 내부 방침 및 기타 관련 법령에 의한 정보보호 사유에 따라(수집 및 보유 참조) 일정
                        기간 저장된 후 파기되어집니다.<br />
                        - 동 개인정보는 법률에 의한 경우가 아니고서는 보유되어지는 이외의 다른 목적으로 이용되지 않습니다.<br />
                        2. 파기방법<br />
                        - 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.<br />
                        - 전자적 파일 형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용하여 삭제합니다
                    </div>

                    <p class="title"> 개인정보의 위탁 관리 </p>
                    <div class="txt">
                        콘텐츠아카데미에서 관리하는 개인정보의 처리를 다른 공공기관 또는 다른 전문기관에 위탁하는 경우, 그 업무에 종사하거나 종사하였던 자에 대해 직무
                        상 알게 된 개인정보를 누설 또는 권한 없이 처리하거나 타인의 이용에 제공하는 등 부당한 목적을 위하여 사용하지 않도록 위탁부서에서 아래에 대한 사
                        항에 관하여 필요한 제한이나 절차를 정하고 수탁기관으로 하여금 준수토록하고 있으며, 실태점검도 실시하고 있습니다.
                        재 위탁 금지에 관한 사항
                        개인정보파일의 복사에 관한 사항
                        개인정보의 관리상황 검사 및 소속직원의 교육에 관한 사항
                        수탁기관에서 준수하여야 할 의무를 위반한 경우의 손해배상 등에 관한 사항
                    </div>

                    <p class="title">이용자 및 법정대리인의 권리와 그 행사방법 </p>
                    <div class="txt">
                        이용자 및 법정 대리인은 언제든지 등록되어 있는 자신 혹은 당해 만 14세 미만 아동의 개인정보를 조회하거나 수정할 수 있으며 가입해지를 요청할 수도
                        있습니다. <br />
                        이용자 혹은 만 14세 미만 아동의 개인정보 조회/수정을 위해서는 ‘개인정보변경’(또는 ‘회원정보수정’ 등)을 가입해지(동의철회)를 위해서는 “회원탈퇴”
                        를 클릭하여 본인 확인 절차를 거치신 후 직접 열람, 정정 또는 탈퇴가 가능합니다.  <br />
                        혹은 개인정보보호책임자에게 서면, 전화 또는 이메일로 연락하시면 지체 없이 조치하겠습니다.  <br />
                        여러분의 개인정보의 오류에 대한 정정을 요청하신 경우에는 정정을 완료하기 전까지 당해 개인정보를 이용 또는 제공하지 않습니다. <br /> 또한 잘못된 개인정
                        보를 제3자에게 이미 제공한 경우에는 정정 처리결과를 제3자에게 지체 없이 통지하여 정정이 이루어지도록 하겠습니다.  <br />콘텐츠아카데미는 이용자 혹은
                        법정 대리인의 요청에 의해 해지 또는 삭제된 개인정보는 “콘텐츠아카데미가 수집하는 개인정보의 보유 및 이용기간”에 명시된 바에 따라 처리하고 그 외
                        의 용도로 열람 또는 이용할 수 없도록 처리하고 있습니다.
                    </div>

                    <p class="title">홈페이지 사용의 안정성 확보를 위한 보안조치  </p>
                    <div class="txt">
                    콘텐츠아카데미는 이용자들의 개인정보를 취급함에 있어 개인정보 분실, 도난, 누출, 변조 또는 훼손되지 않도록 안전성 확보를 위하여 다음과 같은 기술
                    적/관리적 대책을 강구하고 있습니다.
                    </div>

                    <p class="title2">기술적 대책</p>
                    <div class="bdbox">해킹을 대비한 침입차단시스템을 통한 접근통제 및 암호화통신, 주요개인정보 암호화 등의 대책과 보안성 확보를 위한 기술 장치를 주기적으로 검토하고 도입 운용하려고 노력하고 있습니다. </div>

                    <p class="title2">관리자 대책</p>
                    <div class="bdbox">외부 전문기관의 컨설팅을 통한 주기적인 보안점검과 직원들의 보안교육을 실시하고 있으며, 사내 개인정보보호전담기구 영을 통하여 개인정보처리방침 이행사항을 점검하고 문제를 발견 시 즉시 수정하고 바로 잡을 수 있도록 노력하고 있습니다.</div>

                    <h3 class="subTit2">Ⅲ. 영상정보처리기기에 의해 처리되는 개인정보보호</h3>
                    <div class="txt">
                        콘텐츠아카데미는 범죄예방·증거확보·시설안전·화재예방 등 공익을 위하여 영상정보처리기기를 설치·운영함에 있어서 법령의 규정에 의해서만 화상정보
                        를 수집·보유하며, 콘텐츠아카데미가 보유하고 있는 영상정보처리기기 화상은 관계법령에 따라 적법하고 적정하게 처리하여, 여러분의 권익이 침해받지
                        않도록 노력할 것입니다.
                        영상정보처리기기 화상정보 수집 및 보유
                        콘텐츠아카데미는 영상정보처리기기의 설치목적에 부합하는 필요 최소한의 범위 안에서 개인영상정보를 수집하고, 수집된 화상정보는 보유기관 만료시
                        즉시 삭제하고 있습니다.
                    </div>

                    <p class="title">영상정보처리기기 화상정보의 이용 및 제공의 제한 </p>
                    <div class="txt">
                        콘텐츠아카데미가 보유하고 있는 영상정보처리기기 화상정보는 『개인정보보호법』(다른 법률에 규정이 있는 경우는 해당 법률)의 규정이 정하는 바에
                        따라, 정보주체는 화상정보의 존재확인 및 열람·삭제를 요청할 수 있습니다.
                        콘텐츠아카데미는 열람, 삭제 등 청구를 받았을 때에는 지체 없이 필요한 조치를 취하며, 이때에 콘텐츠아카데미는 열람, 삭제 등 청구를 한 자가 본인이
                        거나 정당한 대리인인지를 주민등록증, 운전면허증, 여권 등의 신분증명서를 제출 받아 확인할 수 있습니다.
                        다만, 다음 각호의 1에 해당하는 경우에는 청구사항에 대하여 거부할 수 있으며, 이 경우 `10일 이내`에 정보주체에게 통지하여 드립니다.
                        - 범죄수사·공소유지·재판수행에 중대한 지장을 초래하는 경우
                        - 특정 정보주체의 화상정보만을 삭제하는 것이 기술적으로 현저히 곤란한 경우
                        - 제1항에 따른 요청에 필요한 조치를 취함으로써 타인의 사생활권이 침해될 우려가 큰 경우
                        - 기타 열람 등의 요청을 거절할 만한 정당한 공익적 사유가 존재하는 경우
                    </div>

                    <p class="title">영상정보처기기 위탁관리  </p>
                    <div class="txt">
                        콘텐츠아카데미에서 설치한 영상정보처리기기는 일정한 요건을 갖춘 다른 공공기관이나 영상정보처리기기 전문 민간업체 등의 전문기관에 영상정보처리
                        기 설치 및 관리에 관한 사무를 위탁할 수 있습니다.
                        ※ 수탁기관의 요건
                        - 개인정보보호에 필요한 전문 장비 및 기술을 갖춘 기관
                        - 수탁 받은 업무를 수행하는데 필요한 전문 인력을 갖춘 기관
                        영상정보처리기기 위탁 관리시에는 수탁기관으로 하여금 영상정보처리기기 화상정보에 대한 접근제한, 재 위탁 금지, 화상정보 외부 무단유출 금지, 관리
                        상황 검사 및 소속직원의 교육 등 사무의 범위와 개인정보보호 의무사항을 준수하도록 하고 있습니다.
                    </div>

                    <p class="title">영상정보처리기기 안내판 설치 </p>
                    <div class="txt">
                        콘텐츠아카데미는 영상정보처리기기를 설치한 장소마다 ‘설치 목적·장소, 촬영 범위·시간, 관리 책임자·연락처’ 등을 명시한 안내판을 설치하여 여러분들
                        이 쉽게 인식할 수 있도록 하고 있으며, 다음과 같은 경우는 예외적으로 처리하고 있습니다.
                        영상정보처리기기 안내판 설치안내판 설치 예외사항
                        - 공공기관 건물 안에 다수의 CCTV를 설치한 경우 : 건물입구에만 설치
                        - 개인정보침해 위험이 적은 경우 또는 안내판 설치가 어려운 경우 : 안내판 미설치 또는 홈페이지에 관련 사실 게재
                        - 안내판 설치로 인해 보안취약점 노출이 우려되는 군사시설, 국가중요시설, 보안목표 시설 등 : 안내판 미설치
                    </div>

                    <p class="title">영상정보의 관리 및 보관, 파기</p>
                    <div class="txt">
                        정보주체의 동의 또는 법령의 규정 등에 의하여 개인영상정보를 수집 목적 이외의 용도로 이용하거나 제3자에게 제공하는 경우에는 아래와 같은 사항을
                        「개인영상정보 관리대장」에 기록하고 이를 관리합니다. <br />
                        - 개인영상정보 파일의 명칭 <br />
                        - 이용하거나 제공받은 자의 명칭 <br />
                        - 이용 또는 제공의 목적 <br />
                        - 법령상 이용 또는 제공근거가 있는 경우 그 근거 <br />
                        - 이용 또는 제공의 기간이 정하여져 있는 경우에는 그 기간 <br />
                        - 이용 또는 제공의 형태 <br />
                        - 개인영상정보 파기 일시 (사전에 파기 시기 등을 정한 자동 삭제의 경우에는 파기 주기 등)
                        - 개인영상정보 파기 담당자
                        콘텐츠아카데미는 수집한 개인영상정보를 본 방침에 명시한 보관 기간이 만료한 때에는 지체 없이 파기하며, 개인영상정보의 파기 방법은 아래와 같습니
                        다. <br />
                        - 개인영상정보가 기록된 출력물(사진 등) 등은 파쇄 또는 소각 <br />
                        - 전자기적(電磁氣的) 파일 형태의 개인영상정보는 복원이 불가능한 기술적 방법으로 영구 삭제
                    </div>

                    <h3 class="subTit2">Ⅳ. 권익침해 구제방법</h3>
                    <div class="txt">
                        공공기관의개인정보보호에관한법률」 제12조(처리정보의 열람) 제1항 및 제14조제1항(처리정보의 정정)의 규정에 의한 청구에 대하여 공공기관의 장이
                        행한 처분 또는 부작위로 인하여 권리 또는 이익이 침해를 받은 자는 행정심판법이 정하는 바에 따라 행정심판을 청구할 수 있습니다.<br />
                        ※ 행정심판에 대해 자세한 사항은 법제처 (http://www.moleg.go.kr) 홈페이지를 참고하시기 바랍니다.<br />
                        [국무총리행정심판위원회(www.simpan.go.kr) 전화번호 안내] <br />
                        상담전화 : 1588-1517 <br />
                    </div>

                    <p class="title">개인정보침해사항의 신고 및 개인정보보호 파일 문의  </p>
                    <div class="txt">
                        콘텐츠아카데미 개인정보보호책임자 : 김한곤 부원장 <br />
                        이메일 : kimhg@kocca.kr <br />
                        전화번호 : 02-3153-1103 Fax : 02-3153-1115 <br />
                        콘텐츠아카데미 개인정보보호취급자 : 웹서비스 담당자
                        이메일 : imnm@naver.com <br />
                        전화번호 : 02-3219-5514 Fax : 02-3219-5723<br />
                        주소 : 우)121 - 904 서울시 마포구 월드컵북로 400(상암동 1602) 한국콘텐츠진흥원 개인정보보호책임자 <br />
                        법령의 규정 등에 의하여 수집한 개인정보가 수집 및 처리목적에 맞게 이용될 수 있도록 항시 지도· 감독하겠습니다.
                    </div>


                    <p class="title">개인정보침해사항의 신고 </p>
                    <div class="imgbox">
                        <img src="/images/2013/support/img_prv.gif" alt="" />
                    </div>
                </div>
            </div>
            <!-- //#subCont -->





        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>