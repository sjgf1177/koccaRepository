<%
//**********************************************************
// 1. 파일명 : zu_MemberInfo_U.jsp
// 2. 프로그램명 : 회원정보 수정 화면
// 3. 설명 : 회원 정보를 수정하는 화면이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : ?
// 7. 수정 이력 :
//    7.1. 회원 정보 일부 삭제
//      일자: 2015-03-02
//      내용: 현업의 요청으로 회원 정보 일부를 삭제한다.
//            삭제된 항목은 아래와 같다. (실제 삭제는 하지 않고 주석처리)
//          - 전화번호, 지역, 직업, 종사분야, 소속, 게임자격증 여부
//    7.2. 불필요 import 제거 및 정리
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.RequestManager" %>

<jsp:useBean id = "conf" class="com.credu.library.ConfigSet" scope="page" />
<jsp:useBean id = "getCodenm" class="com.credu.common.GetCodenm" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    DataBox dbox = (DataBox)request.getAttribute("memberView");

    String v_handphone = "";
    String v_userid = "";
    String v_name = "";
    String v_sex = "";
    // String v_home_addr = "";
    String v_email = "";
    // String v_hometel = "";
    String v_ismailing = "";
    // String v_workfieldcd = "";
    String v_memberyear = "";
    String v_membermonth = "";
    String v_memberday = "";
    // String v_jikup = "";
    String v_compnm = "";
    // String v_gamelicyn = "";

    // 생년월일 컬럼이 비여있으면 주민번호에서 생년월일을 가져온다.
    String v_resno1_Year = "";
    String v_resno1_Month = "";
    String v_resno1_Day = "";

    if (dbox != null ) {
       v_handphone = dbox.get("d_handphone");
       v_userid = dbox.get("d_userid");
       v_name = dbox.get("d_name");
       v_sex = dbox.get("d_sex");
       // v_home_addr = dbox.get("d_addr").substring(0,2);
       v_email = dbox.get("d_email");
       // v_hometel = dbox.get("d_hometel");
       v_ismailing = dbox.get("d_ismailing");
       // v_workfieldcd = dbox.get("d_workfieldcd");
       v_memberyear = dbox.get("d_memberyear");
       v_membermonth = dbox.get("d_membermonth");
       v_memberday = dbox.get("d_memberday");
       // v_jikup = dbox.get("d_jikup");
       // v_compnm = dbox.get("d_compnm");
       // v_gamelicyn = dbox.get("d_gamelicyn");
   }

    String[]handphone = new String[3];
    int index = 0;
    StringTokenizer st = new StringTokenizer(v_handphone, "-");
    while(st.hasMoreTokens()) {
        handphone[index++] = st.nextToken();
    }

    String[]hometel = new String[3];
    index = 0;
    /*
    st = new StringTokenizer(v_hometel, "-");
    while(st.hasMoreTokens()) {
        hometel[index++] = st.nextToken();
    }
    */

    String[]email = new String[2];
    index = 0;
    st = new StringTokenizer(v_email, "@");
    while(st.hasMoreTokens()) {
        email[index++] = st.nextToken();
    }

    String[]home_addr = new String[2];
    index = 0;
    /*
    st = new StringTokenizer(v_home_addr, "@");
    while(st.hasMoreTokens()) {
        home_addr[index++] = st.nextToken();
    }
    */

    box.put("title_str", "기본정보수정 - 정보관리 - 개인메뉴");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->

<script type="text/javascript">
/**
 * 변경된 회원정보를 저장한다.
 */
function Submit_Check(){
    var fm = document.form1;
    // var v_hometel = fm.p_hometel1.value+'-'+fm.p_hometel2.value+'-'+fm.p_hometel3.value;
    var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;
    var v_email = fm.p_email1.value+'@'+fm.p_email2.value;

    var chk_num = "";
    var chk_lower = "";
    var chk_upper = "";
    var chk_special = "";

    /*
    if( (fm.p_hometel1.value) == "" ){
        alert("전화번호를 입력하세요");
        fm.p_hometel1.focus(); return;
    } else if ( (fm.p_hometel2.value) == "" || fm.p_hometel2.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel2.focus(); return;
    } else if ( (fm.p_hometel3.value) == "" || fm.p_hometel3.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel3.focus(); return;
    }
    */

    if( (fm.p_handphone1.value) == "" ){
        alert("핸드폰 번호를 입력하세요");
        fm.p_handphone1.focus(); return;
    }else if( (fm.p_handphone2.value) == "" || fm.p_handphone2.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone2.focus(); return;
    }else if( (fm.p_handphone3.value) == "" || fm.p_handphone3.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone3.focus(); return;
    }

    if (fm.p_email1.value =="" || fm.p_email2.value == ""){
        alert("Email 주소를 바르게 입력하세요");
        fm.p_email1.focus();return;
    }

    if(is_mail(v_email) == false) {
        alert("정확한 E-Mail을 입력하십시오.");
        fm.p_email1.focus();
        return;
    }

    /*
    if( fm.p_jikup.value == "" ){
        alert("직업을 선택하십시오.");
        return;
    }

    if( fm.p_workfieldcd.value == "" ){
        alert("종사분야를 선택하십시오.");
        return;
    }

    if( fm.p_compnm.value == "" ){
        alert("소속을 입력하세요.");
        return;
    }
    */

    if ( confirm("회원정보를 변경하시겠습니까?") ) {
        document.form1.target="_self";
        document.form1.action= https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
        document.form1.p_handphone.value = v_handphone;
        // document.form1.p_hometel.value = v_hometel;
        document.form1.p_email.value = v_email;
        document.form1.p_process.value="MemberInfoUpdateOk";
        document.form1.submit();
    }
}

//-------------------------------------------------------
// 이메일 및 이메일형태 ID검사
//-------------------------------------------------------
function is_mail(strEmail)
{
    /** 금지사항
     - @가 2개이상
     - .이 붙어서 나오는 경우
     -  @.나  .@이 존재하는 경우
     - 맨처음이.인 경우 **/
    var regDoNot = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;

    /** 필수사항
     - @이전에 하나이상의 문자가 있어야 함
     - @가 하나있어야 함
     - Domain명에 .이 하나 이상 있어야 함
     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
    var regMust = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/;

    if ( !regDoNot.test(strEmail) && regMust.test(strEmail) ){
        return true;
    }else{
        return false;
    }
}

function emailChange() {
    var r=$("[name='p_email3']").val();
    $("[name='p_email2']").val(r);
    if(r == ""){
        $("[name='p_email2']").attr("readonly",false);
    }else{
        $("[name='p_email2']").attr("readonly",true);
    }
}

function checkYN()
{
    var r=$("[name='p_ismailing']").val();
    if(r=="Y")
        $("[name='p_ismailing']").val("N");
    else
        $("[name='p_ismailing']").val("Y");
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- container-new 시작 //-->
<div id="container-new">

    <!-- form 시작 -->
    <form name="form1"  action="/" method="post" action="">
        <input type="hidden"    name="p_process"    value="" />
        <input type="hidden"    name="p_handphone" />
        <input type="hidden"    name="p_hometel" />
        <input type="hidden"    name="p_email" />
        <input type="hidden"    name="p_postgubun" value=""/>
        <input type="hidden"    name="p_chk" />
        <input type="hidden"    name="p_kor_name" value="<%=v_name%>" />
        <input type="hidden"    name="p_userid" value="<%= v_userid %>" />

        <div id="mainMenu">
            <!-- 학습지원 -->
            <ul class="location">
                <li>개인메뉴</li>
                <li>정보관리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
                        <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육</a></li>
                        <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE">내가작성한 글</a></li>
                        <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                        <li><a href="#" class="active">정보관리</a></li>
                    </ul>
                </li>
                <!-- 2015-01-09 개편 변경
                <li>홈</li>
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
                    내 정보 관리 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate" class="active">내 정보 관리</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                        <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                    </ul>
                </li>
            //-->
            </ul>
            <!-- 서브메인용 -->
            <!-- 2015-01-09 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap">사이트맵</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate"><strong><u>내 정보 관리</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </div>

            //-->
        </div>
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit">정보관리<!--<img src="/images/2013/support/myinform_title.gif" alt="내정보관리" />//--></h2>

            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate"><img src="/images/2013/support/tabinfo1_on.jpg" alt="기본정보수정" /></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=ChangePwd"><img src="/images/2013/support/tabinfo2_off.jpg" alt="비밀번호변경" /></a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberWithdraw"><img src="/images/2013/support/tabinfo3_off.jpg" alt="회원탈퇴" /></a></li>
                </ul>
                <h3 class="blind">문의 및 제안 현재위치</h3>

                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />는 필수 입력 항목입니다.</p>
                <div class="tableWrap space2">
                    <table class="dataTable2" summary="이 표는 내 정보관리 기본정보수정 표료 성명(한글),아이디,성별,생년월일,전화번호,모바일번호,주소(행정구역),이메일,직업,종사분야,소속,정보수신 여부 내용으로 구성 되어 있습니다.">
                        <caption>내 정보관리 기본정보수정 표</caption>
                        <colgroup>
                            <col width="18%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                        <tr>
                            <th scope="row"><label for="f_dName">성명(아이디)</label></th>
                            <td><%=v_name %> (<%=v_userid %>)</td>
                        </tr>
                        <!--<tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_identity">아이디</label></th>
                            <td><%=v_userid %></td>
                        </tr>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />성별</th>
                            <td>
                                <span class="rd0"><label><input type="radio" name="p_sex" id="male" value="1"  <%= v_sex.equals("1") ? "checked" : ""%> disabled="disabled" /> 남성</label></span>
                                <span class="rd1"><label><input type="radio" name="p_sex" id="female" value="2"  <%= v_sex.equals("2") ? "checked" : ""%> disabled="disabled" /> 여성</label></span>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_birth">생년월일</label></th>
                            <td>
                                <input type="hidden" name="p_memberyear" class="inputText" size="4" value="<%=v_memberyear %>" />
                                <input type="hidden" name="p_membermonth" class="inputText" size="2" title="태어난 달 입력" value="<%=v_membermonth %>" />
                                <input type="hidden" name="p_memberday" class="inputText" size="2" title="태어난 일 입력" value="<%=v_memberday %>" />
                                <%=v_memberyear %>&nbsp;년&nbsp;&nbsp;<%=v_membermonth %>&nbsp;월&nbsp;&nbsp;<%=v_memberday %>&nbsp;일
                            </td>
                        </tr>-->
                        <!--
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_tel">전화번호</label></th>
                            <td>
                                <select class="select" name="p_hometel1" id="f_tel">
                                    <option value="070" <%--  hometel[0].equals("070") ? "selected" : ""  --%>>070</option>
                                    <option value="02"  <%--  hometel[0].equals("02")  ? "selected" : ""  --%>>02</option>
                                    <option value="051" <%--  hometel[0].equals("051") ? "selected" : ""  --%>>051</option>
                                    <option value="053" <%--  hometel[0].equals("053") ? "selected" : ""  --%>>053</option>
                                    <option value="032" <%--  hometel[0].equals("032") ? "selected" : ""  --%>>032</option>
                                    <option value="062" <%--  hometel[0].equals("062") ? "selected" : ""  --%>>062</option>
                                    <option value="042" <%--  hometel[0].equals("042") ? "selected" : ""  --%>>042</option>
                                    <option value="052" <%--  hometel[0].equals("052") ? "selected" : ""  --%>>052</option>
                                    <option value="031" <%--  hometel[0].equals("031") ? "selected" : ""  --%>>031</option>
                                    <option value="033" <%--  hometel[0].equals("033") ? "selected" : ""  --%>>033</option>
                                    <option value="044" <%--  hometel[0].equals("044") ? "selected" : ""  --%>>044</option>
                                    <option value="043" <%--  hometel[0].equals("043") ? "selected" : ""  --%>>043</option>
                                    <option value="041" <%--  hometel[0].equals("041") ? "selected" : ""  --%>>041</option>
                                    <option value="063" <%--  hometel[0].equals("063") ? "selected" : ""  --%>>063</option>
                                    <option value="061" <%--  hometel[0].equals("061") ? "selected" : ""  --%>>061</option>
                                    <option value="054" <%--  hometel[0].equals("054") ? "selected" : ""  --%>>054</option>
                                    <option value="055" <%--  hometel[0].equals("055") ? "selected" : ""  --%>>055</option>
                                    <option value="064" <%--  hometel[0].equals("064") ? "selected" : ""  --%>>064</option>
                                </select>
                                - <input type="text" name="p_hometel2" value="<%--  hometel[1] --%>" class="inputText" size="4" maxlength="4" title="국번 입력" />
                                - <input type="text" name="p_hometel3" value="<%--  hometel[2] --%>" class="inputText" size="4" maxlength="4" title="뒤 4자리입력" />
                            </td>
                        </tr>
                        //-->
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_mobile">휴대전화번호</label></th>
                            <td>
                                <select class="select" name="p_handphone1" id="f_mobile">
                                    <option value="010" <%= handphone[0].equals("010") ? "selected" : "" %>>010</option>
                                    <option value="011" <%= handphone[0].equals("011") ? "selected" : "" %>>011</option>
                                    <option value="016" <%= handphone[0].equals("016") ? "selected" : "" %>>016</option>
                                    <option value="017" <%= handphone[0].equals("017") ? "selected" : "" %>>017</option>
                                    <option value="018" <%= handphone[0].equals("018") ? "selected" : "" %>>018</option>
                                    <option value="019" <%= handphone[0].equals("019") ? "selected" : "" %>>019</option>
                                </select>
                                - <input type="text" name="p_handphone2" value="<%= handphone[1]%>" class="inputText" size="4" maxlength="4" title="가운데 자리 입력" />
                                - <input type="text" name="p_handphone3" value="<%= handphone[2]%>" class="inputText" size="4" maxlength="4" title="뒤 4자리입력" />
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_area">지역</label></th>
                            <td>
                                <select class="select" name="p_home_addr1" id="f_area">
                                    <option value="강원" <%--  home_addr[0].equals("강원") ? "selected" : ""  --%>>강원</option>
                                    <option value="경기" <%--  home_addr[0].equals("경기") ? "selected" : ""  --%>>경기</option>
                                    <option value="경남" <%--  home_addr[0].equals("경남") ? "selected" : ""  --%>>경남</option>
                                    <option value="경북" <%--  home_addr[0].equals("경북") ? "selected" : ""  --%>>경북</option>
                                    <option value="광주" <%--  home_addr[0].equals("광주") ? "selected" : ""  --%>>광주</option>
                                    <option value="대구" <%--  home_addr[0].equals("대구") ? "selected" : ""  --%>>대구</option>
                                    <option value="대전" <%--  home_addr[0].equals("대전") ? "selected" : ""  --%>>대전</option>
                                    <option value="부산" <%--  home_addr[0].equals("부산") ? "selected" : ""  --%>>부산</option>
                                    <option value="서울" <%--  home_addr[0].equals("서울") ? "selected" : ""  --%>>서울</option>
                                    <option value="울산" <%--  home_addr[0].equals("울산") ? "selected" : ""  --%>>울산</option>
                                    <option value="인천" <%--  home_addr[0].equals("인천") ? "selected" : ""  --%>>인천</option>
                                    <option value="전남" <%--  home_addr[0].equals("전남") ? "selected" : ""  --%>>전남</option>
                                    <option value="전북" <%--  home_addr[0].equals("전북") ? "selected" : ""  --%>>전북</option>
                                    <option value="제주" <%--  home_addr[0].equals("제주") ? "selected" : ""  --%>>제주</option>
                                    <option value="충남" <%--  home_addr[0].equals("충남") ? "selected" : ""  --%>>충남</option>
                                    <option value="충북" <%--  home_addr[0].equals("충북") ? "selected" : ""  --%>>충북</option>
                                </select>
                            </td>
                        </tr>
                        //-->
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dEmail">이메일</label></th>
                            <td>
                                <input type="text" name="p_email1"  value="<%= email[0]%>" id="f_dEmail" class="inputText" size="17" title="이메일 아이디 입력"/> @
                                <input type="text" name="p_email2"  value="<%= email[1]%>" class="inputText" size="30" title="이메일 도메인 입력"/>
                                <select class="select" name="p_email3" onchange="emailChange();" title="이메일주소 선택">
                                    <option value="" >직접입력</option>
                                    <option value="gmail.com" >gmail.com</option>
                                    <option value="hanmail.net">hanmail.net</option>
                                    <option value="daum.net">daum.net</option>
                                    <option value="hotmail.com">hotmail.com</option>
                                    <option value="nate.com">nate.com</option>
                                    <option value="naver.com">naver.com</option>
                                </select>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dJob">직업</label></th>
                            <td>
                                <select id="f_dJob" name="p_jikup" class="select">
                                    <option value="01" <%-- =v_jikup.equals("01") ? "selected" : "" --%> >고등학생 이하</option>
                                    <option value="02" <%-- =v_jikup.equals("02") ? "selected" : "" --%>>대학생 (대학원)</option>
                                    <option value="03" <%-- =v_jikup.equals("03") ? "selected" : "" --%>>공무직 (공직)</option>
                                    <option value="04" <%-- =v_jikup.equals("04") ? "selected" : "" --%>>공공기관</option>
                                    <option value="05" <%-- =v_jikup.equals("05") ? "selected" : "" --%>>프리랜서</option>
                                    <option value="06" <%-- =v_jikup.equals("06") ? "selected" : "" --%>>예비취업자</option>
                                    <option value="07" <%-- =v_jikup.equals("07") ? "selected" : "" --%>>회사원</option>
                                    <option value="08" <%-- =v_jikup.equals("08") ? "selected" : "" --%>>주부</option>
                                    <option value="09" <%-- =v_jikup.equals("09") ? "selected" : "" --%>>군인</option>
                                    <option value="10" <%-- =v_jikup.equals("10") ? "selected" : "" --%>>교직원</option>
                                    <option value="11" <%-- =v_jikup.equals("11") ? "selected" : "" --%>>의료인</option>
                                    <option value="12" <%-- =v_jikup.equals("12") ? "selected" : "" --%>>법조인</option>
                                    <option value="13" <%-- =v_jikup.equals("13") ? "selected" : "" --%>>언론인</option>
                                    <option value="14" <%-- =v_jikup.equals("14") ? "selected" : "" --%>>종교인</option>
                                    <option value="15" <%-- =v_jikup.equals("15") ? "selected" : "" --%>>연예인</option>
                                    <option value="16" <%-- =v_jikup.equals("16") ? "selected" : "" --%>>체육인</option>
                                    <option value="17" <%-- =v_jikup.equals("17") ? "selected" : "" --%>>교수</option>
                                    <option value="18" <%-- =v_jikup.equals("18") ? "selected" : "" --%>>강사</option>
                                    <option value="19" <%-- =v_jikup.equals("19") ? "selected" : "" --%>>자영업</option>
                                    <option value="99" <%-- =v_jikup.equals("99") ? "selected" : "" --%>>기타</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dJobpart">종사분야</label></th>
                            <td>
                                <select id="f_dJobpart" name="p_workfieldcd" class="select">
                                    <option value=01 <%-- =v_workfieldcd.equals("01") ? "selected" : "" --%>>게임</option>
                                    <option value=02 <%-- =v_workfieldcd.equals("02") ? "selected" : "" --%>>만화</option>
                                    <option value=03 <%-- =v_workfieldcd.equals("03") ? "selected" : "" --%>>모바일</option>
                                    <option value=04 <%-- =v_workfieldcd.equals("04") ? "selected" : "" --%>>방송</option>
                                    <option value=05 <%-- =v_workfieldcd.equals("05") ? "selected" : "" --%>>애니메이션</option>
                                    <option value=06 <%-- =v_workfieldcd.equals("06") ? "selected" : "" --%>>에듀테인먼트</option>
                                    <option value=07 <%-- =v_workfieldcd.equals("07") ? "selected" : "" --%>>영화</option>
                                    <option value=08 <%-- =v_workfieldcd.equals("08") ? "selected" : "" --%>>음악</option>
                                    <option value=09 <%-- =v_workfieldcd.equals("09") ? "selected" : "" --%>>캐릭터</option>
                                    <option value=10 <%-- =v_workfieldcd.equals("10") ? "selected" : "" --%>>출판</option>
                                    <option value=11 <%-- =v_workfieldcd.equals("11") ? "selected" : "" --%>>인쇄</option>
                                    <option value=12 <%-- =v_workfieldcd.equals("12") ? "selected" : "" --%>>영상</option>
                                    <option value=13 <%-- =v_workfieldcd.equals("13") ? "selected" : "" --%>>디자인</option>
                                    <option value=14 <%-- =v_workfieldcd.equals("14") ? "selected" : "" --%>>광고</option>
                                    <option value=15 <%-- =v_workfieldcd.equals("15") ? "selected" : "" --%>>공연</option>
                                    <option value=16 <%-- =v_workfieldcd.equals("16") ? "selected" : "" --%>>미술품</option>
                                    <option value=17 <%-- =v_workfieldcd.equals("17") ? "selected" : "" --%>>공예품</option>
                                    <option value=18 <%-- =v_workfieldcd.equals("18") ? "selected" : "" --%>>패션</option>
                                    <option value=19 <%-- =v_workfieldcd.equals("19") ? "selected" : "" --%>>전시</option>
                                    <option value=20 <%-- =v_workfieldcd.equals("20") ? "selected" : "" --%>>SW</option>
                                    <option value=21 <%-- =v_workfieldcd.equals("21") ? "selected" : "" --%>>HW</option>
                                    <option value=22 <%-- =v_workfieldcd.equals("22") ? "selected" : "" --%>>신문</option>
                                    <option value=99 <%-- =v_workfieldcd.equals("99") ? "selected" : "" --%>>기타</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dGroup">소속</label></th>
                            <td>
                                <input type="text" name="p_compnm"  value="<%=v_compnm %>" id="f_dGroup" class="inputText" size="45" />
                            </td>
                        </tr>
                        //-->

                        <tr>
                            <th scope="row">정보수신 여부</th>
                            <td>
                                <span class="rd0"><label><input type="checkbox" name="p_ismailing" onclick="checkYN();" value="<%= v_ismailing %>" <% if(v_ismailing.equals("Y")) out.println("checked='checked'");%> />&nbsp;&nbsp;서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신하겠습니다.</label></span>
                            </td>
                        </tr>
                        <!--
                        <tr>
                            <th scope="row"><label for="f_gamelicyn">게임자격증</label></th>
                            <td>
                                <select class="select" name="p_gamelicyn" id="f_gamelicyn">
                                    <option value="Y" <%-- =v_gamelicyn.equals("Y") ? "selected" : "" --%>>보유</option>
                                    <option value="N" <%-- =v_gamelicyn.equals("N") ? "selected" : "" --%>>미보유</option>
                                </select>
                            </td>
                        </tr>
                        //-->
                        </tbody>
                    </table>
                </div>
                <!-- //tableWrap -->

                <div class="btnArea">
                    <button type="button" onclick="javascript:Submit_Check();"class="btnText"><span>위 내용으로 변경합니다.</span></button>
                    <a href="javascript:history.go(-1);"  class="btnText2"><span>회원정보 변경을 취소합니다.</span></a>
                </div>

            </div>
            <!-- //#subCont -->

        </div>
    </form>

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>