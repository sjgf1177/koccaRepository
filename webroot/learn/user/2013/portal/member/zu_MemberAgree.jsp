<%
//**********************************************************
//  1. 제      목: 회원가입_약관동의
//  2. 프로그램명 : zu_MemberAgree.jsp
//  3. 개      요: 회원가입_약관동의
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_username = box.getString("p_username");
    String v_process = box.getString("p_process");

    // 2014-07-11 2014 창조산업 일자리 페스티벌을 위해 임시적으로 추가해 놓은 항목
    // 화면의 모든 체크 박스에 v_event 변수값을 활용하여 값이 있을 경우 자동으로 체크되어 있도록
    // 적용되어 있음.
    String v_event = box.getStringDefault("p_event", "");

    box.put("title_str", "약관동의 - 회원가입 - 이용안내 - 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>


<style type="text/css">
    .oPrivateInfoTable{border:1 solid; border-color:#000000;}
    .oPrivateInfoTable th, td { padding: 4px; }
</style>

<!-- 스크립트영역 -->
<script type="text/JavaScript">
/**
 * 약관 동의 여부를 체크한 후 모두 동의 시 회원정보입력 화면으로 이동한다.
 */
function fnCheckMemberJoinAgree() {
    var f = document.form1;
    var cnt = 0;
    cnt = $("input[name=p_agree]:checked").length;

    // 약관동의
    if(cnt != 3){
        alert("이용약관에 동의해 주세요");
        return;

    } else {
        var agreeForm = document.form1;
        agreeForm.action="/servlet/controller.member.MemberJoinCertiServlet";
        agreeForm.p_process.value = "CheckAgree";
        agreeForm.submit();
    }
}

/**
 * 모든 약관 동의 체크박스를 선택한다.
 */
function fnAgreeAllChecked() {
    $('input[name=p_agree]').attr('checked',$("#agreeAll").is(":checked"));
}
</script>
<!-- container-new 시작 //-->
<div id="container-new">

<!-- form 시작 -->
<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="p_username" value="" />
    <input type="hidden" name="p_event" value="<%= v_event %>" />

    <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>이용안내</li>
                <li>회원가입 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <!-- <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" >이용안내</a></li> //-->
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" class="active">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                <!-- 2015-01-16 개편수정
                <li>학습지원</li>
                <li>
                    이용안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                    </ul>
                </li>
                <li>
                    회원가입 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" class="active">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
                //-->
            </ul>
            <!-- 서브메인용 -->
            <div class="introCategory">
                <ul>
                    <!--<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>-->
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin"><strong><u>회원가입</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                    <!--<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>-->
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
                <!-- 2015-01-16 개편수정 
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin"><strong><u>회원가입</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
                //-->
            </div>
            <!--// 서브베인용 -->
        </div>
        <!-- //#mainMenu -->


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">

            <h2 class="subTit">회원가입<!-- <img src="/images/2013/support/join_title.gif" alt="회원가입" />//--></h2>

            <div id="subCont">
                <div class="stepimgBox">
                    <img src="/images/2013/support/private_img1.jpg" alt="회원가입의 4단계 약관동의, 본인확인, 정보입력, 가입완료 중에서 현재 1단계 약관동의 단계입니다." />
                </div>

                <div class="agreement">
                    <strong>회원가입을 위해서 아래 한국콘텐츠진흥원아카데미 이용약관에 대한 안내를 읽고 동의해 주세요.</strong>
                    <br/><br/>
                    <input type="checkbox" name="p_agreeAll" id="agreeAll" class="checbox" onclick="javascript:fnAgreeAllChecked();" <%= (v_event.equals("contentsjob") ) ? "checked=checked" : "" %> /><label for="agreeAll"> <strong>한국콘텐츠진흥원아카데미의 약관에 모두 동의합니다.</strong></label>
                </div>

                <!-- 1 사이트 이용약관 시작 //-->
                <div class="agreement">
                    <h3 class="agr" style="font-size:14px;">사이트 이용약관<!-- <img src="/images/2013/support/ag_title1.gif" alt="사이트 이용약관" /> //--></h3>
                    <div class="txtBox" tabindex="0">
                        <h4 style="font-weight:bold;">제1장 총칙</h4>

                        <h5>제1조 목적</h5>

                        <div>
                        1. 이 약관은 "한국콘텐츠아카데미 (이하 "아카데미"라 한다.)에서 운영하는 웹서비스의 이용조건 및 절차에 관한 사항을 명시합니다.<br/>
                        2. 회원가입에 대한 내용을 충분히 숙지하신 후 가입하여 주시고, 이 약관은 본 아카데미에서 운영하는 모든 웹서비스를 이용하는 모든 회원에게 적용되며 이를 읽고 이해하는 것은 귀하의 책임입니다.<br/>
                        </div>

                        <h5>제2조 약관의 공지 및 효력 </h5>

                        <div>
                        1. 이 약관의 내용은 웹서비스 화면에 게시하거나 기타의 방법으로 회원에게 공지함으로써 효력을 발생합니다.<br/>
                        2. 아카데미는 이 약관을 변경할 수 있으며, 변경된 약관은 제1항과 같은 방법으로 공지함으로써 효력을 발생합니다.<br/>
                        3. 회원가입이란 아카데미에서 제공하는 웹서비스에 가입하는 것으로 웹서비스의 조건과 규정에 합의함을 의미하며, 아카데미는 웹서비스의 조건과 조항을 항시 통고와 함께 또는 통고 없이 전부 또는 일부를 바꿀 권리를 갖습니다. 이런 변화에 대해 빠른 회신을 못하거나, 고지 받지 못했다는 사유로 이의를 제기할 수 없으므로 추후에 착오 없으시길 당부 드립니다.<br/>
                        </div>

                        <h5>제3조 약관 외 준칙</h5>
                        <div>
                        이 약관에 명시되지 않은 사항은 전기통신기본법, 전기통신사업법, 전자거래 기본법, 전자서명법, 통신판매법 및 기타 관계법령의 규정에 의합니다.
                        </div>

                        <h5>제4조 용어의 정의</h5>

                        <div>
                        이 약관에서 사용하는 용어의 정의는 다음과 같습니다.<br/>

                        1. 회원 : 아카데미의 웹서비스 이용에 관한 계약을 체결한 자<br/>
                        2. 아이디(ID) : 회원 식별과 회원의 서비스 이용을 위하여 회원이 선정하고 아카데미가 승인하는 문자와 숫자의 조합<br/>
                        3. 비밀번호 : 회원이 통신상의 자신의 비밀을 보호하기 위해 선정한 문자와 숫자의 조합<br/>
                        4. 탈퇴 : 회원이 서비스 이용 이후 그 이용계약을 종료 시키는 의사표시<br/>
                        5. 휴면 계정 : 최근 3년 동안 로그인 하지 않은 회원<br/>
                        휴면계정에 해당하는 회원은 개인정보의 보호 및 관리를 위하여 개인정보 및 학습이력이 삭제될 수 있습니다.<br/>
                        (단, 휴면계정 일괄 삭제 후에는 기존에 사용하던 아이디로 아카데미 사이트에 재가입할 수 없으니 이점 유의하시기 바랍니다.)<br/>
                        </div>


                        <h4 style="font-weight:bold;">제2장 서비스 이용계약</h4>

                        <h5>제1조 이용 계약의 성립</h5>
                        <div>
                        1. 서비스 가입 신청 시 본 약관을 읽고 "약관에 동의합니다" 버튼을 누르면 이 약관에 동의하는 것으로 간주됩니다.<br/>
                        2. 이용계약은 회원 정보 입력 후 완료를 선택하게 되면 성립합니다.<br/>
                        3. 회원에 가입하여 서비스를 이용하고자 하는 희망자는 아카데미에서 요청하는 개인신상정보를 제공해야 합니다.<br/>
                        4. 이용계약은 회원 1인당 1개의 ID로 체결하는 것을 원칙으로 합니다.<br/>
                        </div>

                        <h5>제2조 이용신청</h5>

                        <div>
                        이용신청은 온라인상의 가입신청 양식에 준합니다.
                        </div>

                        <h5>제3조 이용신청의 승낙</h5>

                        <div>
                        회원이 제2장 제2조에서 정한 모든 사항을 정확히 기재하여 이용신청을 하였을 때 승낙합니다.
                        </div>

                        <h5>제4조 이용신청의 불 승낙</h5>

                        <div>
                        1.웹서비스는 다음에 해당하는 이용신청에 대하여 등록을 거부하거나 등록 후에라도 회원에게 고지하지 않고 회원정보를 수정 또는 삭제할 수 있습니다.<br/>
                        &nbsp;1) 다른 사람의 명의를 사용하여 신청하였을 경우<br/>
                        &nbsp;2) 이용 신청 시 필요내용을 허위로 기재하여 신청하였을 경우<br/>
                        &nbsp;3) 사회의 안녕질서 및 미풍양속을 저해할 목적으로 신청하였을 경우<br/>
                        &nbsp;4) 신용정보의 이용과 보호에 관한 법률에 의거 신용불량자로 등록되어 있는 경우<br/>
                        &nbsp;5) 기타 이용신청자의 귀책사유로 이용승낙이 곤란한 경우<br/>
                        </div>

                        <div>
                        2.아카데미는 다음에 해당하는 이용신청에 대하여 승낙 제한 사유가 해소될 때까지 승낙을 하지 않을 수 있습니다.<br/>
                        &nbsp;1) 아카데미가 설비의 여유가 없는 경우<br/>
                        &nbsp;2) 아카데미의 기술상 지장이 있는 경우<br/>
                        &nbsp;3) 기타 아카데미가 필요하다고 인정되는 경우<br/>
                        </div>

                        <div>
                        3. 아카데미는 이용신청이 불,승낙 되거나 승낙을 제한하는 경우에는 이를 이용신청자에게 알려야 합니다.
                        </div>

                        <h5>제5조 계약사항의 변경</h5>

                        <div>
                        회원은 이용 신청 시 기재한 사항이 변경되었을 경우에는 온라인 수정을 해야 합니다.
                        </div>

                        <h5>제6조 회원정보의 공유</h5>

                        <div>
                        1. 아카데미가 더 좋은 서비스를 위하여 타 기관과 제휴, 인수, 분사, 합병 시 회원의 정보는 공유될 수 있습니다.<br/>
                        2.각종 경품이 제공되는 이벤트의 경우, 경품 협찬사와의 협의에 의해 당첨자 등록정보를 공유할 수 있습니다.<br/>
                        3. 1항, 2항의 사유가 발생할 경우 아카데미는 회원에게 해당 사실을 공지해야 합니다.<br/>
                        </div>

                        <h5>제7조 추가적인 회원정보의 사용</h5>

                        <div>
                        아카데미는 회원이 커뮤니티 서비스 이용 시 제공하는 회원의 추가 정보를 관리용, 통계용의 정보로만 사용합니다.
                        </div>

                        <h5>제8조 정보의 제공</h5>

                        <div>
                        아카데미는 회원이 서비스 이용 및 아카데미의 각종 행사 또는 정보서비스에 대해서는 전자우편이나 서신우편 등의 방법으로 회원들에게 제공할 수 있습니다.
                        </div>

                        <h5>제9조 개인정보보호 의무</h5>

                        <div>
                        1. 아카데미는 이용자의 개인정보를 수집할 때 반드시 당해 이용자의 동의를 받습니다.<br/>
                        2. 개인정보를 내부 관리용, 통계용 및 제2장 제6조 이외의 용도로 이용하거나 이용자의 동의 없는 제3자에게 제공, 분실, 도난, 유출, 변조 시 그에 따른 이용자의 피해에 대한 모든 책임은 아카데미가 집니다.<br/>
                        3. 개인정보의 보호 및 사용에 대해서는 관련법 및 아카데미의 개인정보처리방침이 적용됩니다. 다만, 아카데미의 공식 사이트 이외의 링크된 사이트에서는 아카데미의 개인정보처리방침이 적용되지 않습니다.<br/>
                        </div>


                        <h4 style="font-weight:bold;">제3장 서비스 이용</h4>

                        <h5>제1조 서비스 이용</h5>

                        <div>
                        서비스 이용은 아카데미의 업무상 또는 기술상 특별한 지장이 없는 한 연중무휴, 1일 24시간을 원칙으로 합니다. 단, 정기점검 등 서비스 개선을 위하여 아카데미가 필요하다고 인정되는 때에는 미리 공지한 후 서비스가 일시 중지될 수 있습니다.
                        </div>

                        <h5>제2조 서비스 제공의 중지</h5>

                        <div>
                        아카데미는 다음 항에 해당하는 경우 서비스의 제공을 중지할 수 있습니다.<br/>
                        1. 설비의 보수 등을 위하여 부득이한 경우<br/>
                        2. 전기통신사업법에 규정된 기간통신사업자가 전기통신서비스를 중지하는 경우<br/>
                        3. 기타 서비스를 제공할 수 없는 사유가 발생한 경우<br/>
                        </div>

                        <h5>제3조 파일정보의 소거 및 회원 권한의 삭제</h5>

                        <div>
                        1. 아카데미는 서비스용 설비의 용량에 여유가 없다고 판단되면 필요에 따라 회원의 정보 및 신상정보를 삭제할 수 있습니다.<br/>
                        2. 아카데미는 서비스 운영상 또는 보안에 문제가 있다고 판단되는 회원의 정보 및 신상정보를 사전통지 없이 검색할 수 있습니다.<br/>
                        3. 제1항의 경우에 아카데미는 해당 사항을 사전에 서비스 또는 전자우편을 통하여 공지합니다.<br/>
                        </div>

                        <h4 style="font-weight:bold;">제4장 서비스 사용 제한 및 계약 해지</h4>

                        <h5>제1조 서비스 사용 제한</h5>

                        <div>
                        1. 회원은 서비스의 사용에 있어서 다음 각 호에 해당되지 않도록 하여야 하며, 이에 해당하는 경우 서비스 사용을 제한할 수 있습니다.<br/>
                        &nbsp;1) 다른 회원의 아이디(ID)를 부정 사용하는 행위<br/>
                        &nbsp;2) 범죄행위를 목적으로 하거나 기타 범죄행위와 관련된 행위<br/>
                        &nbsp;3) 선량한 풍속, 기타 사회질서를 해하는 행위<br/>
                        &nbsp;4) 타인의 명예를 훼손하거나 모욕하는 행위<br/>
                        &nbsp;5) 타인의 지적재산권 등의 권리를 침해하는 행위<br/>
                        &nbsp;6) 해킹행위 또는 컴퓨터바이러스의 유포행위<br/>
                        &nbsp;7) 타인의 의사에 반하여 광고성 정보 등 일정한 내용을 지속적으로 전송하는 행위<br/>
                        &nbsp;8) 서비스의 안전적인 운영에 지장을 주거나 줄 우려가 있는 일체의 행위<br/>
                        &nbsp;9) 기타 관계법령에 위배되는 행위<br/>
                        </div>

                        <h5>제2조 계약 해지</h5>

                        <div>
                        1. 회원이 이용계약을 해지하고자 하는 때에는 본인이 서비스 또는 전자우편을 통하여 해지하고자 하는 날의 1일전까지(단, 해지일이 법정공휴일인 경우 공휴일 개시 2일전까지) 이를 아카데미에 신청하여야 합니다.
                        2. 아카데미는 회원이 제4장 제1조의 내용을 위반하고, 아카데미 소정의 기간 이내에 이를 해소하지 아니하는 경우 서비스 이용계약을 해지할 수 있습니다.
                        3. 아카데미는 제2항에 의해 해지된 회원이 다시 이용신청을 하는 경우 일정기간 그 승낙을 제한할 수 있습니다.
                        </div>

                        <h4 style="font-weight:bold;">제5장 책임</h4>

                        <h5>제1조 회원의 의무</h5>

                        <div>
                        1. 회원아이디(ID) 및 비밀번호에 관한 모든 관리의 책임은 회원에게 있습니다.<br/>
                        2. 회원아이디(ID) 및 비밀번호는 아카데미의 사전승낙 없이는 다른 사람에게 양도, 임대, 대여할 수 없습니다.<br/>
                        3. 자신의 회원아이디(ID)가 부정하게 사용된 경우, 회원은 반드시 아카데미에 그 사실을 통보해야 합니다.<br/>
                        4. 회원은 이용신청서의 기재내용 중 변경된 내용이 있는 경우 서비스를 통하여 그 내용을 아카데미에 통지하여야 합니다.<br/>
                        5. 회원은 이 약관 및 관계법령에서 규정한 사항을 준수해야 합니다.<br/>
                        </div>

                        <h5>제2조 아카데미의 의무</h5>

                        <div>
                        1. 아카데미는 제3장 제1조 및 제2조에서 정한 경우를 제외하고 이 약관에서 정한 바에 따라 회원이 신청한 서비스 제공 개시일에 서비스를 이용할 수 있도록 합니다.<br/>
                        2. 아카데미는 이 약관에서 정한 바에 따라 계속적, 안정적으로 서비스를 제공할 의무가 있습니다. 단, 부득이한 경우로 회원 개인정보의 손실이 발생했을 지라도 아카데미는 전혀 책임지지 않습니다.<br/>
                        3. 아카데미는 회원의 개인신상정보를 본인의 승낙 없이 타인에게 누설, 배포하지 않습니다.단, 개인정보보호법, 전기통신관련법령 등 관계법령에 의해 국가기관 등의 요구가 있는 경우에는 그러하지 않습니다.<br/>
                        4. 아카데미는 회원으로부터 제기되는 의견이나 불만이 정당하다고 인정되는 경우에는 즉시 처리해야 합니다. 다만 즉시 처리가 곤란한 경우에는 회원에게 그 사유와 처리일정을 통보하여야 합니다.<br/>
                        </div>

                        <h5>제3조 게시물 또는 내용물의 삭제</h5>

                        <div>
                        아카데미는 서비스의 게시물 또는 내용물이 제4장 제1조의 규정에 위반되거나 아카데미 소정의 게시기간을 초과하는 경우 사전 통지나 동의 없이 이를 삭제할 수 있습니다.
                        </div>

                        <h4 style="font-weight:bold;">제6장 손해배상 및 면책조항</h4>

                        <h5>제1조 손해배상</h5>

                        <div>
                        아카데미는 서비스 이용과 관련하여 회원에게 어떠한 손해가 발생하더라도 동 손해가 아카데미의 중대한 과실에 의한 경우를 제외하고 이에 대하여 책임을 지지 않습니다.
                        </div>

                        <h5>제2조 면책조항</h5>

                        <div>
                        1. 아카데미는 천재지변 또는 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 관한 책임이 면제됩니다.<br/>
                        2. 아카데미는 회원의 귀책사유로 인한 서비스 이용의 장애에 대하여 책임을 지지 않습니다.<br/>
                        3. 아카데미는 회원이 서비스를 이용하여 기대하는 손익이나 서비스를 통하여 얻은 자료로 인한 손해에 관하여 책임을 지지 않습니다.<br/>
                        4. 아카데미는 회원이 서비스에 게재한 정보, 자료, 사실의 신뢰도, 정확성 등 내용에 관하여는 책임을 지지 않습니다.<br/>
                        5. 회원 아이디(ID)와 비밀번호의 관리 및 이용 상의 부주의로 인하여 발생되는 손해 또는 제3자에 의한 부정사용 등에 대한 책임은 모두 회원에게 있습니다.<br/>
                        6. 회원이 제4장 제1조, 기타 이 약관의 규정을 위반함으로 인하여 아카데미가 회원 또는 제3자에 대하여 책임을 부담하게 되고, 이로써 아카데미에게 손해가 발생하게 되는 경우, 이 약관을 위반한 회원은 아카데미에게 발생하는 모든 손해를 배상하여야 하며, 동 손해로부터 아카데미를 면책시켜야 합니다.<br/>
                        </div>

                        <h4 style="font-weight:bold;">제7장 커뮤니티 이용</h4>

                        <h5>제1조 커뮤니티 이용 제한</h5>

                        <div>
                        1. 아카데미는 이용자들이 자신의 의견을 개진하고, 공동의 관심사를 공유할 수 있도록 커뮤니티 서비스를 운영하고 있습니다.<br/>
                        2. 다음 사항은 커뮤니티 이용을 제한할 수 있는 항목들이며, 이들 항목에만 국한되는 것은 아닙니다.<br/>
                        &nbsp;1) 타인의 프라이버시 및 공표 공표권과 같은 법적권리를 훼손, 악용, 도용, 위협하거나 괴롭히거나 또는 달리 위반하는 행위<br/>
                        &nbsp;2) 부적절, 모독적, 명예 훼손적, 침해적, 음란, 상스럽거나 또는 불법적인 제목, 이름, 자료 또는 정보를 출판, 우송, 게시, 배포 또는 유포시키는 행위<br/>
                        &nbsp;3) 지적소유권법에 의하여 보호되는 소프트웨어 또는 기타 자료를 포함하는 파일을 업로드 하는 행위, 다만 이용자가 그에 대한 권리를 소유 또는 관리하는 경우, 또는 필요한 동의를 모두 얻은 경우는 제외합니다.<br/>
                        &nbsp;4) 타인의 컴퓨터를 손상시킬 수 있는 바이러스, 오염된 화일, 또는 기타 유사한 소프트웨어 또는 프로그램을 포함하는 자료를 업로드 하는 행위<br/>
                        &nbsp;5) 상업적인 목적으로 상품 또는 서비스를 광고 또는 판매하는 행위<br/>
                        &nbsp;6) 자료조사, 콘테스트, 피라미드 체계를 행하거나 행운의 편지를 보내는 행위<br/>
                        &nbsp;7) 다른 커뮤니티 이용자가 게재한 것으로 적법하게 배포될 수 없는 것으로 이용자가 알고 있거나 합리적으로 알 것으로 기대되는 파일을 다운로드하는 행위<br/>
                        &nbsp;8) 업로드 된 파일에 포함된 소프트웨어 또는 기타 자료의 저자표시, 법률상 또는 기타 적절한 유의사항, 또는 상표명, 또는 그 출처 내지 근원이 되는 표식을 위조 또는 제거하는 행위<br/>
                        &nbsp;9) 다른 이용자가 커뮤니티를 사용하거나 즐기는 것을 제한하거나 금지시키는 행위<br/>
                        &nbsp;10) 공공질서 및 미풍양속에 반하는 음란한 내용이나, 특정 종교를 선전·선교하거나 비방하는 내용, 기타 지역감정을 유발시키는 등의 비 양식적인 내용을 게재하는 행위 3. 아카데미는 언제든지 어떤 이유로든 아무런 통지 없이 귀하가 커뮤니티의 일부 또는 전부에 접속하는 것을 종료시킬 수 있는 권한을 보유합니다.<br/>
                        </div>

                        <h5>제2조 아카데미의 책임</h5>

                        <div>
                        1. 아카데미는 커뮤니티를 검열할 의무를 지지 않습니다. 그러나, 아카데미는 적용되는 법률, 규정, 법적 절차 또는 정부의 요청을 충족하기 위하여 정보를 공개하거나, 또는 아카데미의 전적인 재량으로 정보 내지 자료의 전부 또는 일부를 편집 또는 송부 거절하거나, 또는 이를 제거할 권리를 보유합니다.<br/>
                        2. 귀하는 모든 커뮤니티 서비스가 공공적인 통신으로 사적인 통신이 아니며, 그 결과 이용자의 통신이 귀하가 알지 못하게 다른 사람에 의하여 읽힐 수 있음을 인정합니다. 누구에 관한 것인지 식별할 수 있는 이용자 또는 이용자의 자녀들에 관한 정보를 커뮤니티에 보낼 때는 항상 주의를 기울여야 합니다. 아카데미는 커뮤니티에 있는 내용, 메시지 또는 정보를 관리하거나 이를 승인하지 아니합니다.<br/>
                        3. 아카데미는 커뮤니티에 관한 책임 및 이용자가 커뮤니티에 참여함으로써 발생하는 어떠한 사건에 대한 책임을 명백히 부인합니다.<br/>
                        </div>

                        <h4 style="font-weight:bold;">제8장 기타</h4>

                        <h5>제1조 약관의 변동</h5>

                        <div>
                        이용약관 개정 시 최소 7일 이전 고지의 의무가 있고 소비자에게 불리한 경우 30일 이전 고지해야 합니다.
                        </div>

                        <h5>제 2조 분쟁의 해결</h5>

                        <div>
                        1. 아카데미와 회원은 서비스와 관련하여 발생한 분쟁을 원만하게 해결하기 위하여 필요한 모든 노력을 하여야 합니다.<br/>
                        2. 제1항의 규정에도 불구하고, 동 분쟁으로 인하여 소송이 제기될 경우 민사소송법상의 관할법원을 전속적 관할 법원으로 합니다.<br/>
                        </div>

                        <h5>부칙</h5>
                        <div>이 개인정보 처리방침은 2014. 3. 11 고지합니다.<br />
                        이 개인정보 처리방침은 2014. 3. 14 부터 적용됩니다.<br />
                        </div>
                    </div>
                    <input type="checkbox" name="p_agree" id="ag" class="checbox" <%= (v_event.equals("contentsjob") ) ? "checked=checked" : "" %> /> <label for="ag">위 약관에 동의합니다</label>
                </div>
                <!-- 1 사이트 이용약관 끝 //-->

                <!-- 2 개인정보 수집 이용동의 시작 //-->
                <div class="agreement">
                    <h3 class="agr" style="font-size:14px;">개인정보 수집 &middot; 이용 동의<!-- <img src="/images/2013/support/ag_title2.gif" alt="수집하는 개인정보의 항목" /> //--></h3>
                    <div class="txtBox" tabindex="0">
                        <h4>개인정보 수집 &middot; 이용 동의</h4>
                        <div>한국콘텐츠아카데미에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.</div>
                        <div>콘텐츠아카데미는 회원제 교육 서비스제공하기 위한 필수정보와 정보주체의 필요에 맞는 서비스를 제공하기 위한 선택정보를 구분하여 수집하고 있습니다. 선택정보를 입력하지 않은 경우에도 서비스 이용에는 제한이 없습니다.</div>

                        <h5>1. 개인정보 수집 항목 및 수집 방법</h5>

                        <div>
                        가. 수집하는 개인정보 항목
                            <table class="oPrivateInfoTable" width="98%" border="1" summary="이 표는 수집하는 개인정보 항목에 관한 표로 회원구분, 필수항목, 선택항목, 보유기간을 포함하고 있습니다.">
                                <caption>수집하는 개인정보 항목 표</caption>
                                <colgroup>
                                    <col width="18%" />
                                    <col width="28%" />
                                    <col width="28%" />
                                    <col width="26%" />
                                </colgroup>
                                <tr>
                                    <thead>
                                        <th>회원구분</th>
                                        <th>필수항목</th>
                                        <th>선택항목</th>
                                        <th>보유기간</th>
                                    </thead>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">일반회원</td>
                                    <td>성명, 성별, 연락처, 휴대폰번호, 지역, 이메일, 직업, 종사분야, 가입경로, 생년월일</td>
                                    <td>게임자격증 보유여부, 정보 수신여부</td>
                                    <td>회원 탈퇴 시까지 또는 최종 접속일로부터 3년이 경과한 시점</td>
                                </tr>
                                <!--
                                <tr>
                                    <td style="text-align:center;">14세미만 회원</td>
                                    <td>성명, 성별, 연락처, 휴대폰번호, 지역, 이메일, 직업, 종사분야, 가입경로, 생년월일, 보호자성명, 보호자 이메일</td>
                                    <td>게임자격증 보유여부, 정보 수신여부</td>
                                    <td>회원 탈퇴 시까지 또는 최종 접속일로부터 3년이 경과한 시점</td>
                                </tr>
                                //-->
                                <tr>
                                    <td style="text-align:center;">기업회원</td>
                                    <td>담당자 이메일, 담당자 휴대전화, 기업 내 서비스 이용 회원 정보(휴대전화번호, 이메일주소, 개인식별정보(CI))</td>
                                    <td>- </td>
                                    <td>담당자 : 삭제 요청시까지기업 내 서비스 이용<br/>회원 : 회원 탈퇴 시 또는 최종접속일로부터 3년이 경과한 시점</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">튜터</td>
                                    <td>성명, 휴대폰번호, 이메일소속, 이력사항</td>
                                    <td>-</td>
                                    <td>계약 종료 시까지</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">강사</td>
                                    <td>성명, 휴대폰번호, 이메일소속</td>
                                    <td>-</td>
                                    <td>개발 과정 서비스 종료 시까지</td>
                                </tr>
                            </table>
                            * 홈페이지를 이용하는 경우 다음의 정보는 로그인 기록을 통하여 자동으로 수집&middot;저장됩니다.<br/>- 접속로그, 접속IP, 방문일시
                        </div>

                        <div>
                        나. 개인정보 수집 방법<br/>
                        홈페이지 회원 가입, 이메일을 통한 일괄 가입, 오프라인 교육 신청, 이메일을 통한 튜터, 강사 지원<br/>
                        </div>

                        <h5>2. 개인정보 수집&middot;처리목적 및 보유&middot;이용기간</h5>
                        <div>
                        콘텐츠아카데미는 정보주체의 회원 가입일로부터 서비스를 제공하는 기간 동안에 한하여 콘텐츠아카데미 서비스를 이용하기 위한 최소한의 개인정보를 보유 및 이용 하게 됩니다. 회원가입 등을 통해 개인정보의 수집, 이용, 제공 등에 대해 동의하신 내용은 언제든지 철회하실 수 있습니다. 회원 탈퇴를 요청하거나 수집&middot;처리목적을 달성하거나 보유&middot;이용기간이 종료한 경우, 사업 폐지 등의 사유발생시 당해 개인정보를 지체 없이 파기합니다.<br/>
                            <table class="oPrivateInfoTable" width="98%" border="1" style="border:1 solid; border-color:#000000;" summary="이 표는 개인정보 수집, 처리목적 및 보유, 이용기간에 관한 표로 순번, 개인정보파일명칭, 수집근거/처리목적, 개인정보 항목, 보유기간을 포함하고 있습니다.">
                                <caption>개인정보 수집 처리목적 및 보유 이용기간 표</caption>
                                <colgroup>
                                    <col width="6%" />
                                    <col width="20%" />
                                    <col width="22%" />
                                    <col width="30%" />
                                    <col width="22%" />
                                </colgroup>
                                <tr>
                                    <thead>
                                        <th>순번</th>
                                        <th>개인정보파일명칭</th>
                                        <th>수집근거/처리목적</th>
                                        <th>개인정보 항목</th>
                                        <th>보유기간</th>
                                    </thead>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">1</td>
                                    <td>회원명부</td>
                                    <td>정보주체의 동의/ 홈페이지 회원 신상 정보 관리</td>
                                    <td>- 개인회원 : 성명, 휴대폰번호, 이메일주소, 생년월일, 전화번호, 지역, 직업, 종사분야, 가입경로<br/>
                                        - 기업회원 : 담당자 이메일, 담당자 휴대전화, 기업 내 서비스 이용 회원 정보(휴대전화번호, 이메일주소, 개인식별정보(CI))
                                    </td>
                                    <td>탈퇴 시 또는 최종 접속일로부터 3년<br/>기업 회원의 경우 담당자 정보는 삭제 요청 시까지</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">2</td>
                                    <td>오프라인 교육 신청 정보</td>
                                    <td>정보주체의 동의/ 신청자 평가 및 선발</td>
                                    <td>경력, 공모 및 수상 내역, 병역 여부, 자격증 정보, 학력, 자기소개, 지원동기</td>
                                    <td>탈퇴 시 또는 3년</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">3</td>
                                    <td>수강이력 정보</td>
                                    <td>정보주체의 동의/ 교육 서비스 운영 및 통계 처리</td>
                                    <td>수강신청 정보, 수강현황 정보, 수료 정보, 생년월일, 성별</td>
                                    <td>최종 수료일로부터 5년</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">4</td>
                                    <td>튜터 정보</td>
                                    <td>정보주체의 동의/ 튜터링 서비스 제공</td>
                                    <td>성명 휴대폰번호, 이메일주소, 소속, 이력</td>
                                    <td>계약 종료 시</td>
                                </tr>
                                <tr>
                                    <td style="text-align:center;">5</td>
                                    <td>강사 정보</td>
                                    <td>정보주체의 동의/ 교육 과정 개발 및 강의</td>
                                    <td>성명 휴대폰번호, 이메일주소, 소속</td>
                                    <td>개발 과정 서비스 종료까지</td>
                                </tr>
                            </table>
                            * 개인정보 수집&middot;이용에 관한 사항의 동의를 거부할 수 있습니다. 동의 거부 시 콘텐츠아카데미 홈페이지에 회원가입이 되지 않으며, 회원제 서비스의 이용이 제한됩니다.
                        </div>
                    </div>
                    <input type="checkbox" name="p_agree" id="ag2" class="checbox" <%= (v_event.equals("contentsjob") ) ? "checked=checked" : "" %> /> <label for="ag2">위 약관에 동의합니다</label>
                </div>
                <!-- 2 개인정보 수집 이용동의 끝 //-->

                <!-- 3 개인정보 취급위탁 동의 시작//-->
                <div class="agreement">
                    <h3 class="agr" style="font-size:14px;">개인정보 취급위탁 동의<!-- <img src="/images/2013/support/ag_title3.gif" alt="개인정보의 수집 및 이용 목적" /> //--></h3>
                    <div class="txtBox" tabindex="0">
                        <h4>개인정보 취급위탁 동의</h4>
                        <div>한국콘텐츠아카데미는 정보주체의 동의 없이는 수집ㆍ보유하고 있는 개인정보를 제3자에게 제공하지 않습니다. 다만, 「개인정보보호법」 제17조 제1항 및 제18조 제2항에 해당하는 경우에만 정보주체의 동의 없이 개인정보를 제3자에게 제공할 수 있습니다.</div>

                        <div>1. 한국콘텐츠아카데미는 안정적인 회원제 교육서비스를 제공하기 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</div>
                        <div>
                            <table class="oPrivateInfoTable" width="98%" border="1" summary="이 표는 한국콘텐츠아카데미 사이트의 위탁운영 업무를 담당하는 업체의 정보를 가지고 있으며, 수탁업체, 위탁업무내용, 개인정보항목, 보유 및 이용기간 정보를 포함하고 있습니다.">
                                <caption>위탁업체 정보 표</caption>
                                <colgroup>
                                    <col width="20%" />
                                    <col width="26%" />
                                    <col width="28%" />
                                    <col width="26%" />
                                </colgroup>
                                <tr>
                                    <thead>
                                        <th>수탁업체</th>
                                        <th>위탁업무내용</th>
                                        <th>개인정보항목</th>
                                        <th>보유 및 이용기간</th>
                                    </thead>
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
                        </div>

                        <div>2. 한국콘텐츠아카데미는 위탁계약 체결 시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적·관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리·감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</div>

                        <div>3. 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체 없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</div>

                    </div>
                    <input type="checkbox" name="p_agree" id="ag3" class="checbox" <%= (v_event.equals("contentsjob") ) ? "checked=checked" : "" %> /> <label for="ag3">위 약관에 동의합니다</label>
                </div>
                <!-- 3 개인정보 취급위탁 동의 끝 //-->

                <div class="btnArea wd1">
                    <button type="button" class="btnText" onclick="javascript:fnCheckMemberJoinAgree();"><span>약관에 동의합니다.</span></button>
                    <a href="javascript:history.go(-1);"  class="btnText2"><span>약관에 동의하지 않습니다.</span></a>
                </div>
            </div>
            <!-- //#subCont -->





        </div>
</form>



</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>