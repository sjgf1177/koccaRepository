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
<style type="text/css">
td .tdText1 { margin:0px; padding: 6px 10px 6px 10px; }
</style>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내 
                    <!--
                    <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                    -->
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
                    한국콘텐츠아카데미(이하 "아카데미")는 개인정보 보호법 제30조에 따라 정보주체의 개인정보를 보호하고 이와 관련한 고충을 신속하고 원활하게 처리할 수 있도록 하기 위하여 다음과 같이 개인정보 처리지침을 수립&middot;공개합니다.
                </div>

                <div class="privaterule">
                    <h3 class="subTit2">제1조(개인정보의 처리목적)</h3>
                    <div class="txt">
                        ① 아카데미는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 개인정보 보호법 제18조에 따라 별도의 동의를 받는 등 필요한 조치를 이행할 예정입니다.
                    </div>

                    <div class="txt">
                        <p class="title">1. 홈페이지 회원 가입 및 관리</p>
                        회원 가입 의사 확인, 회원제 서비스 제공에 따른 본인 식별&middot;인증, 회원자격 유지&middot;관리, 각종 고지&middot;통지, 고충처리 등을 목적으로 개인정보를 처리합니다.
                    </div>

                    <div class="txt">
                        <p class="title">2. 튜터, 강사 정보 관리</p>
                        아카데미 운영을 위한 튜터 모집, 강사 모집 등을 목적으로 개인정보를 처리합니다.
                    </div>

                    <div class="txt">
                        <p class="title">3. 각종 민원사무 처리</p>
                        개인정보 열람, 개인정보 정정·삭제, 개인정보 처리정지 요구, 개인정보 유출사고 신고, 개인정보 침해 신고 접수&middot처리 등 민원처리를 목적으로 개인정보를 처리합니다.
                    </div>

                    <div class="txt">
                        <p class="title">4. 마케팅 및 광고에 활용</p>
                        아카데미에서 제공하는 메일링 서비스, SMS 알림 서비스, 이벤트, 행사정보 및 홍보목적으로 개인정보를 처리합니다.
                    </div>

                    <div class="txt">
                        ② 아카데미가 개인정보 보호법 제32조에 따라 등록&middot;공개하는 개인정보파일의 수집근거 및 처리목적은 다음과 같습니다.
                    </div>

                    <table class="dataTable7" summary="이 표는 개인정보 보호법 제32조에 따라 등록 공개하는 개인정보파일의 수집근거 및 처리목적 표로 개인정보파일 명칭, 수집근거/처리목적, 개인정보 항목, 보유기간 으로 구성되어 있습니다.">
                        <caption>개인정보의 수집 및 보유</caption>
                        <colgroup>
                            <col style="width:7%" />
                            <col style="width:18%" />
                            <col style="width:22%" />
                            <col style="width:30%" />
                            <col />
                        </colgroup>
                        <tr class="first">
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">순번</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">개인정보파일 명칭</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">수집근거/처리목적</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">개인정보항목</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">보유기간</th>
                        </tr>
                        <tr>
                            <td class="seq">1</td>
                            <td>회원명부</td>
                            <td>정보주체의 동의/ 홈페이지 회원 신상 정보 관리</td>
                            <td>
                                - 개인회원 : 성명, 휴대폰번호, 이메일주소, 생년월일, 전화번호, 지역, 직업, 종사분야, 가입경로<br/>
                                - 기업회원 : 담당자 이메일, 담당자 휴대전화, 기업 내 서비스 이용 회원 정보(휴대전화번호, 이메일주소, 개인식별정보(CI))
                            </td>
                            <td>
                                탈퇴 시 또는 최종 접속일로부터 3년<br/>
                                기업 회원의 경우 담당자 정보는 삭제 요청 시까지
                            </td>
                        </tr>
                        <tr>
                            <td class="seq">2</td>
                            <td>오프라인 교육신청 정보</td>
                            <td>정보주체의 동의/ 신청자 평가 및 선발</td>
                            <td>
                                경력, 공모 및 수상 내역, 병역 여부, 자격증 정보, 학력, 자기소개, 지원동기
                            </td>
                            <td>
                                탈퇴 시 또는 3년
                            </td>
                        </tr>
                        <tr>
                            <td class="seq">3</td>
                            <td>수강이력정보</td>
                            <td>정보주체의 동의/ 교육 서비스 운영 및 통계 처리</td>
                            <td>
                                수강신청 정보, 수강현황 정보, 수료 정보, 생년월일, 성별
                            </td>
                            <td>
                                최종 수료일로부터 5년
                            </td>
                        </tr>
                        <tr>
                            <td class="seq">4</td>
                            <td>튜터정보</td>
                            <td>정보주체의 동의/ 튜터링 서비스 제공</td>
                            <td>
                                성명, 휴대폰번호, 이메일주소, 소속, 이력
                            </td>
                            <td>
                                계약 종료 시
                            </td>
                        </tr>
                        <tr>
                            <td class="seq">5</td>
                            <td>강사정보</td>
                            <td>정보주체의 동의 / 교육 과정 개발 및 강의</td>
                            <td>
                                성명, 휴대폰번호, 이메일주소, 소속
                            </td>
                            <td>
                                개발 과정 서비스 종료 시까지
                            </td>
                        </tr>

                    </table>
                    <p class="tblbtm">※ 기타 아카데미의 개인정보 파일 등록사항 공개는 안전행정부 개인정보보호 종합지원 포털(www.privacy.go.kr) -> 개인 -> 개인정보민원 -> 개인정보열람등 요구 -> 개인정보파일 목록검색 메뉴를 활용해 주시기 바랍니다. </p>


                    <h3 class="subTit2">제2조(개인정보의 처리 및 보유기간)</h3>
                    <div class="txt">
                        ① 아카데미는 법령에 따른 개인정보 보유&middot;이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의 받은 개인정보 보유&middot;이용기간 내에서 개인정보를 처리&middot;보유합니다. 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.
                    </div><br/>

                    <div class="txt">
                        <p class="title">1. 홈페이지 회원 가입 및 관리</p>
                        - 개인회원 : 회원 탈퇴 시 또는 최종 접속일이 3년이 경과한 시점<br/>
                        - 기업회원 : 담당자 정보는 삭제 요청 시까지, 기업 내 서비스 이용 회원의 경우 회원 탈퇴 시 또는 최종접속일이 3년이 경과한 시점<br/>
                        다만, 다음의 사유에 해당하는 경우에는 해당 사유 종료 시까지<br/>
                        1) 관계 법령 위반에 따른 수사&middot;조사 등이 진행중인 경우에는 해당 수사&middot;조사 종료 시까지<br/>
                        2) 홈페이지 이용에 따른 채권&middot;채무관계 잔존 시에는 해당 채권&middot;채무관계 정산 시까지<br/>
                        2. 튜터, 강사 정보 : 주민등록번호, 계좌번호, 주소는 계약 종료 시까지, 성명, 휴대폰번호, 이메일 주소, 소속 정보는 삭제 요청 시까지<br/>

                    </div>


                    <h3 class="subTit2">제3조(개인정보의 제3자 제공)</h3>
                    <div class="txt">
                        아카데미는 정보주체의 동의 없이는 수집ㆍ보유하고 있는 개인정보를 제3자에게 제공하지 않습니다. 다만, 「개인정보보호법」 제17조 제1항 및 제18조 제2항에 해당하는 경우에만 정보주체의 동의 없이 개인정보를 제3자에게 제공할 수 있습니다.
                    </div>

                    <h3 class="subTit2">제4조(개인정보처리의 위탁)</h3>
                    <div class="txt">
                        ① 아카데미는 안정적인 회원제 교육서비스를 제공하기 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.
                    </div>

                    <table class="dataTable7" summary="이 표는 개인정보 처리업무 위탁업체 정보 표로 수탁업체, 위탁업무내용, 개인정보항목, 보유 및 이용기간으로 구성되어 있습니다.">
                        <caption>개인정보의 수집 및 보유</caption>
                        <colgroup>
                            <col style="width:25%" />
                            <col style="width:22%" />
                            <col style="width:28%" />
                            <col style="width:25%" />
                        </colgroup>
                        <tr class="first">
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">수탁업체</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">위탁업무내용</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">개인정보항목</th>
                            <th scope="col" style="margin:0; padding:8px 0px 8px 0px; text-align:center;">보유 및 이용기간</th>
                        </tr>
                        <tr>
                            <td>(주)휴넷</td>
                            <td>홈페이지 및 회원 관리</td>
                            <td>회원명부, 수강이력</td>
                            <td>위탁계약 종료 시까지</td>
                        </tr>
                        <tr>
                            <td>(주)휴넷</td>
                            <td>튜터 관리</td>
                            <td>성명, 주민등록번호, 주소, 소속, 휴대폰번호, 이메일주소, 이력사항, 계좌번호</td>
                            <td>위탁계약 종료 시까지</td>
                        </tr>
                        <tr>
                            <td>(주)휴넷</td>
                            <td>강사 관리</td>
                            <td>성명, 주민등록번호, 주소, 소속, 휴대폰번호, 이메일주소, 이력사항, 계좌번호</td>
                            <td>위탁계약 종료 시까지</td>
                        </tr>
                        <!--<tr>
                            <td>(주)콤텍정보통신</td>
                            <td>정보전산처리 및 하드웨어관리</td>
                            <td>회원명부, 수강이력</td>
                            <td>위탁계약 종료 시까지</td>
                        </tr>
                    --></table>
                    <br/>

                    <div class="txt">
                        ② 아카데미는 위탁계약 체결 시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적&middot;관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리&middot;감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.
                    </div>
                    <div class="txt">
                        ③ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.
                    </div>


                    <h3 class="subTit2">제5조(정보주체의 권리&middot;의무 및 행사방법)</h3>
                    <div class="txt">
                        ① 정보주체는 아카데미에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.
                    </div>

                    <p class="title">1. 개인정보 열람요구</p>
                    <p class="title">2. 오류 등이 있을 경우 정정 요구</p>
                    <p class="title">3. 삭제요구</p>
                    <p class="title">4. 처리정지 요구</p>

                    <div class="txt">
                        ② 제1항에 따른 권리 행사는 아카데미에 대해 서면, 전화, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 아카데미는 이에 대해 지체없이 조치하겠습니다.
                    </div>
                    <div class="txt">
                        ③ 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 아카데미는 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.
                    </div>
                    <div class="txt">
                        ④ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.
                    </div>
                    <div class="txt">
                        ⑤ 정보주체는 개인정보 보호법 등 관계법령을 위반하여 아카데미가 처리하고 있는 정보주체 본인이나 타인의 개인정보 및 사생활을 침해하여서는 아니 됩니다.
                    </div>


                    <h3 class="subTit2">제6조(처리하는 개인정보 항목)</h3>
                    <div class="txt">
                        아카데미는 다음의 개인정보 항목을 처리하고 있습니다.
                    </div>
                    <div class="txt">
                        ① 홈페이지 회원 가입 및 관리
                    </div>
                    <div class="txt">
                        <p class="title">1. 일반 회원</p>
                        - 필수항목 : 성명, 성별, 연락처, 휴대폰번호, 지역, 이메일, 직업, 종사분야, 가입경로, 생년월일<br/>
                        - 선택항목 : 게임자격증 보유여부, 정보 수신여부
                    </div>
                    <div class="txt">
                        <p class="title">2. 14세 미만 회원</p>
                        - 필수항목 : 성명, 성별, 연락처, 휴대폰번호, 지역, 이메일, 직업, 종사분야, 가입경로, 생년월일, 보호자성명, 보호자 이메일<br/>
                        - 선택항목 : 게임자격증 보유여부, 정보 수신여부
                    </div>
                    <div class="txt">
                        <p class="title">3. 기업 회원</p>
                        - 필수항목 : 담당자 이메일, 담당자 휴대전화, 기업 내 서비스 이용 회원 정보(휴대전화번호, 이메일주소, 개인식별정보(CI))
                    </div>

                    <div class="txt">
                        ② 오프라인 교육 신청
                    </div>
                    <div class="txt">
                        <p class="title">1. 오프라인 교육 신청</p>
                        - 필수항목 : 경력, 공모 및 수상 내역, 병역 여부, 자격증 정보, 학력, 자기소개, 지원동기
                    </div>

                    <div class="txt">
                        ③ 수강이력 정보
                    </div>
                    <div class="txt">
                        <p class="title">1. 수강신청 정보</p>
                        - 필수항목 : 개인별 수강신청 정보
                    </div>
                    <div class="txt">
                        <p class="title">2. 수료 정보</p>
                        - 필수항목 : 개인별 수료 정보
                    </div>

                    <div class="txt">
                        ④ 튜터, 강사 정보 수집 및 관리
                    </div>
                    <div class="txt">
                        <p class="title">1. 튜터</p>
                        - 필수항목 : 성명, 휴대폰번호, 이메일주소, 소속, 이력사항
                    </div>
                    <div class="txt">
                        <p class="title">2. 강사</p>
                        - 필수항목 : 성명, 휴대폰번호, 이메일주소, 소속, 이력사항
                    </div>

                    <div class="txt">
                        ⑤ 상담 및 온라인 신청 접수
                    </div>
                    <div class="txt">
                        <p class="title">1. 홈페이지 개선의견</p>
                        - 필수항목 : 이메일주소
                    </div>

                    <div class="txt">
                        ⑥ 인터넷 서비스 이용과정에서 아래 개인정보 항목이 자동으로 생성되어 수집될 수 있습니다.
                    </div>
                    <p class="title">1. IP주소, 접속 기록 등</p>
                    <br/>


                    <h3 class="subTit2">제7조(개인정보의 파기)</h3>
                    <div class="txt">
                        ① 아카데미는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체없이 해당 개인정보를 파기합니다.
                    </div>
                    <div class="txt">
                        ② 정보주체로부터 동의받은 개인정보 보유기간이 경과하거나 처리목적이 달성되었음에도 불구하고 다른 법령에 따라 개인정보를 계속 보존하여야 하는 경우에는, 해당 개인정보(또는 개인정보파일)을 별도의 데이터베이스(DB)로 옮기거나 보관장소를 달리하여 보존합니다.
                    </div>
                    <div class="txt">
                        ③ 개인정보 파기의 절차 및 방법은 다음과 같습니다.
                    </div>
                    <div class="txt">
                        <p class="title">1. 파기절차</p>
                        정보주체가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.
                    </div>
                    <div class="txt">
                        <p class="title">2. 파기기한</p>
                        정보주체의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 15일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 30일 이내에 그 개인정보를 파기합니다.
                    </div>
                    <div class="txt">
                        <p class="title">3. 파기방법</p>
                        개인정보가 기록된 출력물은 분쇄하거나 소각합니다. 전자적 파일 형태로 저장된 개인정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.
                    </div>


                    <h3 class="subTit2">제8조(개인정보의 안전성 확보조치)</h3>
                    <div class="txt">
                        ① 아카데미는 개인정보의 안전성 확보를 위해 다음과 같은 조치를 취하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">1. 개인정보 취급 직원의 최소화</p>
                        개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">2. 정기적인 자체 감사 실시</p>
                        개인정보 취급 관련 안정성 확보를 위해 정기적(연 2회)으로 자체 감사를 실시하고 있습니다.
                    </div>

                    <div class="txt">
                        <p class="title">3. 내부관리계획의 수립 및 시행</p>
                        개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.
                    </div>

                    <div class="txt">
                        <p class="title">4. 개인정보의 암호화</p>
                        정보주체의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">5. 해킹 등에 대비한 기술적 대책</p>
                        아카데미는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신&middot;점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">6. 개인정보에 대한 접근 제한</p>
                        개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여, 변경, 말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">7. 문서보안을 위한 잠금장치 사용</p>
                        개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">8. 비인가자에 대한 출입 통제</p>
                        개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.
                    </div>


                    <h3 class="subTit2">제9조(개인정보 보호책임자)</h3>
                    <div class="txt">
                        아카데미는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
                    </div>
                    <div class="txt">
                        <p class="title">▶ 개인정보 보호책임자</p>
                            성명 : 김한곤 <br />
                            직책 : 전략지원 부원장 <br />
                            연락처 : 전화번호 02-3153-1253, 팩스 02-3153-1108, 이메일주소 privacy@kocca.kr<br />
                            ※ 개인정보 보호 담당부서로 연결됩니다.
                    </div>
                    <div class="txt">
                        <p class="title">▶ 개인정보 보호 담당부서 책임자</p>
                            부서명 : 창의인재양성팀 <br />
                            담당자 : 임호수 <br />
                            연락처 : 전화번호 02-2161-0063, 팩스 : 02-2161-0078, lakelim@kocca.kr
                    </div>

                    <h3 class="subTit2">제10조(개인정보 열람청구)</h3>
                    <div class="txt">
                        ① 정보주체는 개인정보 보호법 제35조에 따른 개인정보의 열람 청구를 개인정보 보호 담담부서에 할 수 있습니다. 아카데미는 정보주체의 개인정보 열람청구가 신속하게 처리되도록 노력하겠습니다.
                    </div>
                    <div class="txt">
                        ② 정보주체께서는 제1항의 열람청구 접수&middot;처리부서 이외에, 안정행정부의 '개인정보보호 종합지원 포털' 웹사이트(www.privacy.go.kr)를 통하여서도 개인정보 열람청구를 하실 수 있습니다.
                    </div>
                    <div class="txt">
                        ▶ 안정행정부 개인정보보호 종합지원 포털 → 개인정보 민원 → 개인정보 열람 등 요구 (공공아이핀을 통한 실명인증 필요)
                    </div>


                    <h3 class="subTit2">제11조(권익침해 구제방법)</h3>
                    <div class="txt">
                        정보주체는 아래의 기관에 대해 개인정보 침해에 대한 피해구제, 상담 등을 문의하실 수 있습니다.<br/>
                        &lt;아래의 기관은 아카데미와는 별개의 기관으로서, 아카데미의 자체적인 개인정보 불만처리, 피해구제 결과에 만족하지 못하시거나 보다 자세한 도움이 필요하시면 문의하여 주시기 바랍니다&gt;
                    </div>
                    <div class="txt">
                        <p class="title">▶ 개인정보 침해신고센터 (한국인터넷아카데미 운영)</p>
                            - 소관업무 : 개인정보 침해사실 신고, 상담 신청<br/>
                            - 홈페이지 : privacy.kisa.or.kr <br/>
                            - 전화 : (국번없이) 118 <br/>
                            - 주소 : (138-950) 서울시 송파구 중대로 135 한국인터넷아카데미 개인정보침해신고센터
                    </div>
                    <div class="txt">
                        <p class="title">▶ 개인정보 분쟁조정위원회 (한국인터넷아카데미 운영)</p>
                        - 소관업무 : 개인정보 분쟁조정신청, 집단분쟁조정 (민사적 해결)<br/>
                        - 홈페이지 : privacy.kisa.or.kr<br/>
                        - 전화 : (국번없이) 118<br/>
                        - 주소 : (138-950) 서울시 송파구 중대로 135 한국인터넷아카데미 개인정보침해신고센터
                    </div>
                    <div class="txt">
                        <p class="title">▶ 대검찰청 사이버범죄수사단 : 02-3480-3573 (<a href="http://www.spo.go.kr" target="_blank">www.spo.go.kr</a>)</p>
                    </div>
                    <div class="txt">
                        <p class="title">▶ 경찰청 사이버테러대응센터 :1566-0112 (<a href="http://www.netan.go.kr" target="_blank">www.netan.go.kr</a>)</p>
                    </div>

                    <h3 class="subTit2">제12조(개인정보 처리방침 변경)</h3>
                    <div class="txt">
                        ① 이 개인정보 처리방침은 2014. 5. 21 고지합니다.
                    </div>
                    <div class="txt">
                        ② 이 개인정보 처리방침은 2014. 5. 28 부터 적용됩니다.
                    </div>
                    <div class="txt">
                        ③ 이전의 개인정보 처리방침은 아래에서 확인하실 수 있습니다.
                    </div>
                    <div class="txt">
                        - 2014년 5월 20일 이전 내용 <a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=Personal" target="_self">[보기]</a>
                    </div>

                </div>
            </div>
            <!-- //#subCont -->





        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>