<%
//**********************************************************
// 1. 파일명 : zu_MemberInfo_I.jsp
// 2. 프로그램명 : 회원정보 입력 화면
// 3. 설명 : 홈페이지 회원 가입시에 필요한 정보를 입력하는 화면이다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : ?
// 7. 수정 이력 :
//          2015-03-02 : 현업의 요청으로 회원 가입시 필요한 정보 일부를 삭제한다.
//                      삭제된 항목은 아래와 같다. (실제 삭제는 하지 않고 주석처리)
//                          - 전화번호, 지역, 직업, 종사분야, 가입경로, 게임자격증 여부
//***********************************************************
%>

<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.system.CodeConfigBean" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<jsp:useBean id="getCodenm" class="com.credu.common.GetCodenm" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String certiType = box.getString("certiType");

    if ( certiType.equals("") || !box.getSession("userid").equals("") ) {
%>
<!DOCTYPE html>
<html>
<title>한국콘텐츠아카데미</title>
<script type="text/javascript">
    alert("잘못된 접근입니다.");
    location.href = "/";
</script>
<body>
</body>
</html>
<%
    } else {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date dt = new Date();
        String currentYear = sdf.format(dt);
        String mobileNo = box.getStringDefault("mobileNo", "");
        String email = box.getStringDefault("email", "");
        String userName = box.getStringDefault("userName", "");
        String birthDate = box.getStringDefault("birthDate", "");
        String gender = box.getStringDefault("gender", "");
        String nationalInfo = box.getStringDefault("nationalInfo", "");
        String dupInfo = box.getStringDefault("dupInfo", "");

        gender = gender.equals("0") ? "2" : gender;

        String memberYear = "";
        String memberMonth = "";
        String memberDay = "";

        String mobileExNo = "";
        String mobileMiddleNo = "";
        String mobileLastNo = "";

        String emailId = "";
        String emailDomain = "";

        if (certiType.toUpperCase().equals("MOBILE")) {
            memberYear = birthDate.substring(0, 4);
            memberMonth = birthDate.substring(4, 6);
            memberDay = birthDate.substring(6, 8);

            if ( mobileNo.length() == 11 ) {
                mobileExNo = mobileNo.substring(0, 3);
                mobileMiddleNo = mobileNo.substring(3, 7);
                mobileLastNo = mobileNo.substring(7, 11);
            } else if ( mobileNo.length() == 10 ) {
                mobileExNo = mobileNo.substring(0, 3);
                mobileMiddleNo = mobileNo.substring(3, 6);
                mobileLastNo = mobileNo.substring(6, 10);
            }
        }

        if ( certiType.toUpperCase().equals("EMAIL") ) {
            emailId = email.substring(0, email.lastIndexOf("@") );
            emailDomain = email.substring(email.lastIndexOf("@") + 1, email.length() );
        }

        box.put("title_str", "회원정보입력 - 회원가입 - 이용안내 - 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<script type="text/javascript" src="/script/jquery.form.js"></script>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->
<script type="text/javascript">
<!--

    /**
     * ID 등록 여부를 체크한다.
     */
    function fnExistsId() {
        var regForm = document.form1;
        var userid = regForm.p_userid.value;

        if(userid == "") {
            alert("아이디를 입력하여 주십시오.");
            regForm.p_userid.focus();
            return;
        }

        if( userid.length < 6 || userid.length > 12) {
            alert("아이디는 총 6~12자로만 조합이 되어야 합니다.");
            $("#div_Area").css("color","#FF0000");  //   ID 가 존재
            $("#div_Area").text("사용할수 없는 아이디 입니다.");
            regForm.p_chk.value = "N";
            return;
        }

        var chk_num = userid.search(/[0-9]/g);
        var chk_lower = userid.search(/[a-z]/ig);

        //if (!/^[a-z0-9]{6,12}$/.test(fm.p_userid.value) ||  (chk_num < 0 || chk_lower< 0) ){

        if (!/^[a-z0-9]{6,12}$/.test( userid )){
            alert("아이디는 영문 소문자, 숫자를 사용하여 6-12자리로 입력해 주세요.");
            //alert("아이디는 영문 소문자, 숫자를 혼합하여 6-12자리로 입력해 주세요.");
            regForm.p_userid.focus();
            $("#div_Area").css("color","#FF0000");      //   ID 가 존재
            $("#div_Area").text("사용할수 없는 아이디입니다.");
            regForm.p_chk.value = "N";
            return;
        }

        regForm.p_process.value = "ASP_Login";
        regForm.menuid.value = "0";
        regForm.gubun.value = "20";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
            type:'post',
            data:{userId:userid},
            success: function(data){
               var r1 = $.trim(data);
               $("#div_Area").text("");
               idExists = false;

               if(r1 == "0") {
                   $("#div_Area").css("color","#FF0000");   // 기 등록된 ID 가 존재
                   $("#div_Area").text("사용할수 없는 아이디입니다.");
                   regForm.p_chk.value = "N";

               } else {
                   idExists=true;
                   $("#div_Area").css("color","#0000FF");   // 기 등록된 ID 없음
                   $("#div_Area").text("사용할수 있는 아이디입니다.");
                   regForm.p_chk.value = "Y";
               }
            }
        });
    }

    /**
     * 회원 가입 정보 유효성을 확인한 후 회원 등록을 한다
     */
    function fnRegisterMember(){
        var regForm = document.form1;
        var v_email = regForm.p_email1.value + '@' + regForm.p_email2.value;

        var chk_num = "";
        var chk_lower = "";
        var chk_upper = "";
        var chk_special = "";

        var userId = regForm.p_userid.value;

        if( regForm.p_kor_name.value == "" ){
            alert("이름을 입력하여 주십시오.");
            regForm.p_kor_name.focus();
            return;
        }

        if( userId == "" ){
            alert("아이디를 입력하여 주십시오.");
            regForm.p_userid.focus();
            return;
        }

        chk_num = userId.search(/[0-9]/g);
        chk_lower = userId.search(/[a-z]/ig);

        //if (!/^[a-z0-9]{6,12}$/.test(userId) ||  (chk_num < 0 || chk_lower< 0) ){
        //    alert("아이디는 영문 소문자, 숫자를 혼합하여 6-12자리로 입력해 주세요.");
        if (!/^[a-z0-9]{6,12}$/.test(userId)){
            alert("아이디는 영문 소문자, 숫자를 사용하여 6-12자리로 입력해 주십시오.");
            regForm.p_userid.focus();
            return;
        }

        if(regForm.p_chk.value != "Y"){
            alert("아이디 중복체크를 하십시오.");
            return;
        }

        // 비밀번호 길이 제한
        var p_pwd_len =  regForm.p_pwd.value.length;

        chk_num = regForm.p_pwd.value.search(/[0-9]/g);
        chk_lower = regForm.p_pwd.value.search(/[a-z]/ig);
        chk_upper = regForm.p_pwd.value.search(/[A-Z]/ig);
        chk_special = regForm.p_pwd.value.search(/[^a-zA-Z0-9]/g);

        if(p_pwd_len <= 0){
            alert("비밀번호를 입력해 주십시오.");
            regForm.p_pwd.focus();
            return;
        } else {
            // if (!/^[a-zA-Z0-9]{9,14}$/.test(regForm.p_pwd.value) || (chk_num < 0 || chk_lower< 0 || chk_upper < 0)){
            // if ( p_pwd_len < 9 || p_pwd_len > 14 || chk_lower< 0 || chk_upper < 0 ||  (chk_num < 0 && chk_special< 0 ) ){
            // alert("비밀번호는 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.");

            if (chk_lower < 0 || chk_upper < 0 ||  (chk_num < 0 && chk_special < 0 ) ){
                alert("비밀번호는 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 입력해 주십시오.");
                regForm.p_pwd.value = "";
                regForm.p_pwd.focus();
                return;
            }
        }

        if( regForm.p_pwd.value == userId){
            alert("아이디와 동일한 비밀번호는 사용 하실 수 없습니다.");
            regForm.p_pwd.value = "";
            regForm.p_pwd.focus();
            return;
        }

        if( (regForm.p_pwd_re.value) == ""){
            alert("비밀번호확인을 입력해 주십시오");
            regForm.p_pwd_re.focus();
            return;
        }

        if(regForm.p_pwd.value != regForm.p_pwd_re.value){
            alert("비밀번호가 다르게 입력되었습니다.");
            regForm.p_pwd_re.value ="";
            regForm.p_pwd.focus();
            return;
        }

        if (regForm.p_pwd.value.length < 8 ) {
            alert("비밀번호는 8자리 이상 입력해 주십시오.");
            regForm.p_pwd.focus();
            return;
        }

        if ( $("input:radio[name=p_sex]").length > 0 ) {
            if ( $("input:radio[name=p_sex]:checked").length == 0 ) {
                alert("성별을 선택하십시오.");
                $("input:radio[name=p_sex]").eq(0).focus();
                return;
            }
        }

        if( regForm.p_memberyear.value == "" ){
            alert("생년월일을 입력해 주십시오.");
            regForm.p_memberyear.focus();
            return;
        } else if( regForm.p_membermonth.value == "" ){
            alert("생년월일을 입력해 주십시오.");
            regForm.p_membermonth.focus();
            return;
        } else if( regForm.p_memberday.value == "" ){
            alert("생년월일을 입력해 주십시오.");
            regForm.p_memberday.focus();
            return;
        }

        /*
        if( regForm.p_hometel1.value == "" ){
            alert("전화번호를 입력해 주십시오.");
            regForm.p_hometel1.focus();
            return;

        } else if( (regForm.p_hometel2.value) == "" || regForm.p_hometel2.length > 3 ) {
            alert("전화번호를 바르게 입력해 주십시오.");
            regForm.p_hometel2.focus();
            return;

        } else if( (regForm.p_hometel3.value) == "" || regForm.p_hometel3.length > 4 ) {
            alert("전화번호를 바르게 입력해 주십시오.");
            regForm.p_hometel3.focus();
            return;
        }
        */

        if( (regForm.p_handphone1.value) == "" ){
            alert("핸드폰 번호를 입력해 주십시오.");
            regForm.p_handphone1.focus();
            return;

        } else if( (regForm.p_handphone2.value) == "" || regForm.p_handphone2.length > 3 ) {
            alert("핸드폰 번호를 바르게 입력해 주십시오.");
            regForm.p_handphone2.focus();
            return;

        } else if( (regForm.p_handphone3.value) == "" || regForm.p_handphone3.length > 4 ) {
            alert("핸드폰 번호를 바르게 입력해 주십시오.");
            regForm.p_handphone3.focus();
            return;
        }

        /*
        if( (regForm.p_home_addr1.value) == "" ){
            alert("지역을 선택하세요.");
            regForm.p_home_addr1.focus();
            return;
        }
        */

        if (regForm.p_email1.value =="" || regForm.p_email2.value == ""){
            alert("Email 주소를 바르게 입력해 주십시오.");
            regForm.p_email1.focus();
            return;
        }

        // e-mail check
        if(fnCheckEmailValidation(v_email) == false) {
            alert("정확한 E-Mail을 입력해 주십시오.");
            regForm.p_email1.focus();
            return;
        }

        /*
        if( regForm.p_jikup.value == "" ){
            alert("직업을 선택하십시오.");
            regForm.p_jikup.focus();
            return;
        }

        if( regForm.p_workfieldcd.value == "" ){
            alert("종사분야를 선택하십시오.");
            regForm.p_workfieldcd.focus();
            return;
        }

        if( regForm.p_registerRoute.value == "" ){
            alert("가입경로를 선택하십시오.");
            regForm.p_registerRoute.focus();
            return;
        }
        */

        if ( confirm("입력하신 정보로 회원가입을 하시겠습니까?") ) {
            regForm.target = "_self";
            regForm.action =  https_host() + "/servlet/controller.member.MemberJoinCompleteServlet";
            // regForm.action =  https_host() + "/learn/user/2013/portal/member/zu_MemberRegister.jsp";
            regForm.p_handphone.value = regForm.p_handphone1.value + '-' + regForm.p_handphone2.value + '-' + regForm.p_handphone3.value;
            // regForm.p_hometel.value = regForm.p_hometel1.value + '-' + regForm.p_hometel2.value + '-' + regForm.p_hometel3.value;
            regForm.p_email.value = v_email;
            regForm.p_process.value = "JoinOk";
            regForm.submit();
        }
    }


    /**
     * 이메일 및 이메일형태 ID검사
     */
    function fnCheckEmailValidation(strEmail) {
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

        if ( !regDoNot.test(strEmail) && regMust.test(strEmail) ) {
            return true;

        } else {
            return false;
        }
    }

    /**
     * 이메일 목록을 선택했을 때
     */
    function fnEmailChange() {
        var r=$("[name='p_email3']").val();
        $("[name='p_email2']").val(r);
        if(r == ""){
            $("[name='p_email2']").attr("readonly",false);
        }else{
            $("[name='p_email2']").attr("readonly",true);
        }
    }

    function workfieldChange() {
        if (document.form1.p_workfieldcd.value == "99") {
            document.getElementById('divWork').innerHTML = "<input name='p_workfieldnm' type='text' class='inp_txt_01' id='p_workfieldnm' size='30' maxlength='15' />";
            document.form1.p_workfieldnm.value="";
        } else {
            document.getElementById('divWork').innerHTML="";
        }
    }

    /**
     * 메일 및 SMS 수신동의 여부
     */
    function fhCheckMailingYN() {
        var r = $("[name='p_ismailing']").val();
        if(r == "Y") {
            $("[name='p_ismailing']").val("N");
        } else {
            $("[name='p_ismailing']").val("Y");
        }
    }

    /**
     * 비밀번호 보안 등급을 체크한다.
     */
    function fnCheckPaswordLevel(password) {
        var desc = new Array();
        desc[0] = "<label style=\"color:#ff0000;\">매우 낮음</label>"; // 매우부족
        desc[1] = "<label style=\"color:#ff0000;\">매우 낮음</label>"; // 조금부족
        desc[2] = "<label style=\"color:#808080;\">약함</label>"; // 보통수준
        desc[3] = "<label style=\"color:#0000ff;\">보통</label>"; // 양호수준
        desc[4] = "<label style=\"color:#808000;\">양호</label>"; // 좋습니다
        desc[5] = "<label style=\"color:#008000;\">매우 양호</label>"; // 매우좋음
        var score = 0;

        //if password length == 0, do nothing
        if (password.length == 0) return;
        //if password bigger than 8 give 1 point
        if (password.length > 7) score++;

        //if password has both lower and uppercase characters give 1 point
        if ( ( password.match(/[a-z]/) ) || ( password.match(/[A-Z]/) ) ) score++;
        //if password has at least one number give 1 point
        if (password.match(/\d+/)) score++;
        //if password has at least one special caracther give 1 point
        if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;
        //if password bigger than 12 give another 1 point
        if (password.length > 12) score++;

        //document.getElementById("passwordDescription").innerHTML = desc[score];
        $("#oPwdLevelArea").html( desc[score] );
    }

    /**
     * 비밀번호와 비밀번호확인 값을 비교한다.
     */
    function fnCheckPasswordRe(rePassword) {
        var password = $("#f_pw2").val();
        if ( rePassword != "" ) {
            if (rePassword.length < password.length ) {
                $("#oPwdReArea").html("<label style=\"color:#fca500;\">재입력된 비밀번호가 너무 짧음</label>");
            } else if ( password != rePassword ) {
                $("#oPwdReArea").html("<label style=\"color:#fca500;\">비밀번호가 맞지 않음</label>");
            } else {
                $("#oPwdReArea").html("<label style=\"color:#008000;\">비밀번호 확인</label>");
            }
        } else {
            $("#oPwdReArea").html("");
        }
    }

    function fnSetLastDay() {
        var lastDayArr = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
        var year = $("#oMemberYear").val();
        var month = $("#oMemberMonth").val();

        var monthIndex = -1;
        var newDaySize = 0;
        var currDaySize = 0;
        var dayIndex = -1;

        if ( year != "" && month != "") {
            if (( year%400 == 0 ) || (year%4 == 0 && year%100 != 0 ) ) {
               lastDayArr[1] = 29;
            }

            monthIndex = $("#oMemberMonth option").index($("#oMemberMonth option:selected"));
            dayIndex = $("#oMemberDay option").index($("#oMemberDay option:selected"));
            newDaySize = lastDayArr[monthIndex - 1];
            currDaySize = $("#oMemberDay option:first").nextAll().size();

            if ( newDaySize != currDaySize ) {
                $("#oMemberDay option:first").nextAll().remove();
                for( var k = 1 ; k <= newDaySize; k++ ) {
                    if ( k < 10 ) {
                        $("#oMemberDay").append("<option value=\"" + ("0" + k) + "\">" + ("0" + k) + "</option>");
                    } else {
                        $("#oMemberDay").append("<option value=\"" + k + "\">" + k + "</option>");
                    }
                }

                $("#oMemberDay option:eq(" + dayIndex + ")").attr("selected", "selected");
            }
        } else {
            return;
        }
    }
//-->
</script>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">


<!-- form 시작 -->
<form id="oRegisterMemberForm" name="form1" action="" method="post">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="menuid" value="" />
    <input type="hidden" name="gubun" value="" />
    <input type="hidden" name="p_chk" value="" />
    <input type="hidden" name="p_handphone" value="" />
    <input type="hidden" name="p_hometel" value="" />
    <input type="hidden" name="p_email" value="" />
    <input type="hidden" name="certiType" value="<%= certiType %>" />
    <input type="hidden" name="p_dupinfo" value="<%= dupInfo %>" />

    <div id="mainMenu">
        <!-- 학습지원 -->
        <ul class="location">
            <li>홈</li>
            <li>이용안내</li>
            <li>
                회원가입 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" class="active">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </li>
            <!-- 2015-01-16 개편 변경

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
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </li>

            //-->                    
        </ul>
        <!-- 서브메인용 -->
    </div>
    <!-- //#mainMenu -->


    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">
        <h2 class="subTit">회원가입<!-- <img src="/images/2013/support/join_title.gif" alt="회원가입" /> //--></h2>

        <div id="subCont">
            <div class="stepimgBox">
                <img src="/images/2013/support/private_img3.jpg" alt="회원가입의 4단계 약관동의, 본인확인, 정보입력, 가입완료 중에서 현재 3단계 정보입력 단계입니다." />
            </div>
            <p class="stepSubtxt">회원정보는 개인정보 취급방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지 않습니다. <a href="http://edu.kocca.or.kr/servlet/controller.homepage.MainMemberJoinServlet?p_process=Personal" target="_blank">개인정보처리방침</a></p>

            <div class="both">
                <h4 class="subTit"><img src="/images/2013/support/joininfo_title1.gif" alt="로그인 정보입력" /></h4>
                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />필수 입력 사항</p>
            </div>
            <div class="tableWrap space2">
                <table class="dataTable6" summary="이 표는 내 정보관리 기본정보수정 표료 성명(한글), 아이디, 성별, 생년월일, 전화번호, 모바일번호, 주소(행정구역), 이메일, 직업, 종사분야, 소속, 정보수신 여부 내용으로 구성 되어 있습니다.">
                    <caption>내 정보관리 기본정보수정 표</caption>
                    <colgroup>
                        <col width="18%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dName">성명(한글)</label></th>
                        <td>
<%
        if ( certiType.equals("MOBILE") ) {
%>
                                <%= userName %>
                            <input type="hidden" id="oKorName" name="p_kor_name" value="<%= userName %>" />
<%
        } else {
%>
                            <input type="text" id="oKorName" name="p_kor_name" value="" class="inputText" size="30" />
<%
        }
%>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_identity">아이디</label></th>
                        <td>
                            <input type="text" name="p_userid" id="f_identity" class="inputText" size="30" />
                            <a href="javascript:fnExistsId();"><img src="/images/2013/btn/btn_doubleid.gif" width="90" height="22" alt="id 중복확인" /></a>
                            <span id="div_Area" style="width:200px;margin-left:10px"></span><br />
                            <span class="rd0">아이디는 <strong>6~12자의 영숫자 조합</strong>으로 만들 수 있습니다.</span>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_pw2">비밀번호</label></th>
                        <td>
                            <input type="password" name="p_pwd" id="f_pw2" class="inputPw" maxlength="30" size="30" onkeyup="fnCheckPaswordLevel(this.value);" onblur="fnCheckPasswordRe(document.form1.p_pwd_re.value);"/>
                            <span id="oPwdLevelArea" style="width:200px;margin-left:10px; font-weight:bold;"></span>
                            <span class="rd3">
                                비밀번호는 8~30자의 영문 대소문자/숫자/특수문자 혼용 사용이 가능합니다.<br/>
                                2가지 조합인 경우 10자리 이상, 3가지 조합인 경우 8자리 이상을 권장합니다.<br/>
                                영문 대/소문자와 숫자 또는 특수문자를 혼합하여 사용하시고,<br />
                                생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주십시오.
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_pw3">비밀번호 확인</label></th>
                        <td>
                            <input type="password" name="p_pwd_re" id="f_pw3" class="inputPw" maxlength="30" size="30" onkeyup="fnCheckPasswordRe(this.value);" />
                            <span id="oPwdReArea" style="width:200px;margin-left:10px; font-weight:bold;"></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- //tableWrap -->

            <!-- 0925 수정 -->
            <div class="both">
                <h4 class="subTit"><img src="/images/2013/support/joininfo_title2.gif" alt="회원 상세정보 입력" /></h4>
                <p class="sidetxt"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />필수 입력 사항</p>
            </div>
            <!-- //0925 수정 -->
            <div class="tableWrap space2">
                <table class="dataTable6" summary="이 표는 내 정보관리 기본정보수정 표료 성명(한글),아이디,성별,생년월일,전화번호,모바일번호,주소(행정구역),이메일,직업,종사분야,소속,정보수신 여부 내용으로 구성 되어 있습니다.">
                    <caption>내 정보관리 기본정보수정 표</caption>
                    <colgroup>
                        <col width="18%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" />성별</th>
                        <td>
<%
            if ( certiType.equals("MOBILE") ) {
%>
                            <span class="rd0"><%= gender.equals("1") ? "남성" : "여성" %></span>
                            <input type="hidden" name="p_sex" id="oSex" value="<%= gender %>" />
                            <input type="hidden" name="p_memberyear" id="oMemberyear" value="<%= memberYear %>" />
                            <input type="hidden" name="p_membermonth" id="oMembermonth" value="<%= memberMonth %>" />
                            <input type="hidden" name="p_memberday" id="oMemberday" value="<%= memberDay %>" />
<%
            } else {
%>
                            <span class="rd0"><label><input type="radio" name="p_sex" value="1" id="male" /> 남성</label></span>
                            <span class="rd1"><label><input type="radio" name="p_sex" value="2" id="female" /> 여성</label></span>
<%
            }
%>
                        </td>
                    </tr>
<%
            if ( certiType.toUpperCase().equals("EMAIL") ) {
%>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="oMobileExNo">생년월일</label></th>
                        <td>
                            <select class="select" name="p_memberyear" id="oMemberYear" onchange="fnSetLastDay();" style="width:55px;">
                                <option value="">선택</option>
<%
                for ( int i = Integer.parseInt(currentYear) ; i > 1919 ; i-- ) {
                    out.println("\t\t\t\t<option value=\"" + i + "\">" + i + "</option>");
                }
%>
                            </select>
                            -
                            <select class="select" name="p_membermonth" id="oMemberMonth" onchange="fnSetLastDay();" style="width:55px;">
                                <option value="">선택</option>
<%
                for ( int i = 1 ; i < 13 ; i++ ) {
                    if( i < 10 ) {
                        out.println("\t\t\t\t<option value=\"" + ("0" + i) + "\">" + ("0" + i) + "</option>");
                    } else {
                        out.println("\t\t\t\t<option value=\"" + i + "\">" + i + "</option>");
                    }
                }
%>
                            </select>
                            -
                            <select class="select" name="p_memberday" id="oMemberDay" style="width:55px;">
                                <option value="">선택</option>
<%
                for ( int i = 1 ; i < 32 ; i++ ) {
                    if( i < 10 ) {
                        out.println("\t\t\t\t<option value=\"" + ("0" + i) + "\">" + ("0" + i) + "</option>");
                    } else {
                        out.println("\t\t\t\t<option value=\"" + i + "\">" + i + "</option>");
                    }
                }
%>
                            </select>
                        </td>
                    </tr>
<%
            }
%>
                    <!--
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_tel">전화번호</label></th>
                        <td>
                            <select class="select" name="p_hometel1" id="f_tel" style="width:55px;">
                                <option value="">선택</option>
                                <option value="070">070</option>
                                <option value="02">02</option>
                                <option value="031">031</option>
                                <option value="032">032</option>
                                <option value="033">033</option>
                                <option value="041">041</option>
                                <option value="042">042</option>
                                <option value="043">043</option>
                                <option value="044">044</option>
                                <option value="051">051</option>
                                <option value="052">052</option>
                                <option value="053">053</option>
                                <option value="054">054</option>
                                <option value="055">055</option>
                                <option value="061">061</option>
                                <option value="062">062</option>
                                <option value="063">063</option>
                                <option value="064">064</option>
                            </select>
                            - <input type="text" name="p_hometel2" maxlength="4" class="inputText" size="6" title="국번 입력" />
                            - <input type="text" name="p_hometel3" maxlength="4" class="inputText" size="6" title="뒤 4자리입력" />
                        </td>
                    </tr>
                    //-->
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="oMobileExNo">휴대전화번호</label></th>
                        <td>
<%
            if ( certiType.equals("MOBILE") ) {
%>
                            <%= mobileExNo + "-" + mobileMiddleNo + "-" + mobileLastNo %>
                            <input type="hidden" name="p_handphone1" id="oMobileExNo"  value="<%= mobileExNo %>" />
                            <input type="hidden" name="p_handphone2" id="oMobileMiddleNo" value="<%= mobileMiddleNo %>" />
                            <input type="hidden" name="p_handphone3" id="oMobileLastNo" value="<%= mobileLastNo %>" />
<%
            } else {
%>
                            <select class="select" name="p_handphone1" id="oMobileExNo" style="width:55px;">
                                <option value="">선택</option>
                                <option value="010">010</option>
                                <option value="011">011</option>
                                <option value="016">016</option>
                                <option value="017">017</option>
                                <option value="018">018</option>
                                <option value="019">019</option>
                            </select>
                            - <input type="text" name="p_handphone2" maxlength="4" class="inputText" size="6" title="가운데 자리 입력" />
                            - <input type="text" name="p_handphone3" maxlength="4" class="inputText" size="6" title="뒤 4자리입력" />
<%
            }
%>
                        </td>
                    </tr>

                    <!--
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_area">지역</label></th>
                        <td>
                            <select class="select" name="p_home_addr1" id="f_area">
                                <option>거주지역 선택</option>
                                <option value="강원">강원</option>
                                <option value="경기">경기</option>
                                <option value="경남">경남</option>
                                <option value="경북">경북</option>
                                <option value="광주">광주</option>
                                <option value="대구">대구</option>
                                <option value="대전">대전</option>
                                <option value="부산">부산</option>
                                <option selected value="서울">서울</option>
                                <option value="울산">울산</option>
                                <option value="인천">인천</option>
                                <option value="전남">전남</option>
                                <option value="전북">전북</option>
                                <option value="제주">제주</option>
                                <option value="충남">충남</option>
                                <option value="충북">충북</option>
                            </select>
                        </td>
                    </tr>
                    //-->
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dEmail">이메일</label></th>
                        <td>
<%
            if ( certiType.equals("EMAIL") ) {
%>
                                <%= email %>
                                <input type="hidden" id="oEmailId" name="p_email1" value="<%= emailId %>" />
                                <input type="hidden" id="oEmailDomain" name="p_email2" value="<%= emailDomain %>" />
<%
            } else {
%>
                            <input type="text" name="p_email1" id="f_dEmail" class="inputText" size="17" title="이메일 아이디 입력"/> @
                            <input type="text" name="p_email2" class="inputText" size="30" title="이메일 도메인 입력"/>
                            <select class="select" name="p_email3" title="이메일주소 선택" onchange="fnEmailChange();">
                                <option value="">직접입력</option>
                                <option value="gmail.com" >gmail.com</option>
                                <option value="hanmail.net">hanmail.net</option>
                                <option value="daum.net">daum.net</option>
                                <option value="hotmail.com">hotmail.com</option>
                                <option value="nate.com">nate.com</option>
                                <option value="naver.com">naver.com</option>
                                <!--
                                <option value="chol.com" >chol.com</option>
                                <option value="dreamwiz.com" >dreamwiz.com</option>
                                <option value="empal.com" >empal.com</option>
                                <option value="freechal.com" >freechal.com</option>
                                <option value="hanafos.com" >hanafos.com</option>
                                <option value="hanmir.com" >hanmir.com</option>
                                <option value="hitel.net" >hitel.net</option>
                                <option value="korea.com" >korea.com</option>
                                <option value="lycos.co.kr" >lycos.co.kr</option>
                                <option value="netian.com" >netian.com</option>
                                <option value="paran.com" >paran.com</option>
                                <option value="yahoo.com" >yahoo.com</option>
                                <option value="yahoo.co.kr" >yahoo.co.kr</option>
                                //-->
                            </select>
  <%
            }
%>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dJob">직업</label></th>
                        <td>
                            <%-- = CodeConfigBean.getCodeGubunSelect ("0104","", 1,"p_jikup","","class='select' id='f_dJob'",3) --%>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_dJobpart">종사분야</label></th>
                        <td>
                            <%-- = CodeConfigBean.getCodeGubunSelect ("0103","", 1,"p_workfieldcd","","class='select' id='f_dJobpart''",3) --%>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><img src="/images/2013/introduce/ess_img.png" alt="필수항목 입력표시" /><label for="f_route">가입경로</label></th>
                        <td>
                            <%-- = CodeConfigBean.getCodeGubunSelect ("0105","", 1,"p_registerRoute","","class='select' id='f_route'",3) --%>
                        </td>
                    </tr>
                    //-->
                    <tr>
                        <th scope="row">정보수신 여부</th>
                        <td>
                            <span class="rd0"><label><input type="checkbox" name="p_ismailing" value="Y" onclick="fhCheckMailingYN();" checked />&nbsp;&nbsp;서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신하겠습니다.</label></span>
                        </td>
                    </tr>
                    <!--
                    <tr>
                        <th scope="row"><label for="f_gamelicyn">게임자격증</label></th>
                        <td>
                            <select class="select" name="p_gamelicyn" id="f_gamelicyn">
                                <option value="Y">보유</option>
                                <option selected value="N">미보유</option>
                            </select>
                        </td>
                    </tr>
                    //-->
                    </tbody>
                </table>
            </div>
            <!-- //tableWrap -->

            <!--h4 class="subTit"><img src="/images/2013/support/joininfo_title3.gif" alt="부가정보 입력" /></h4>
            <div class="tableWrap space2">
                <table class="dataTable6" summary="이 표는 내 정보관리 기본정보수정 표료 성명(한글),아이디,성별,생년월일,전화번호,모바일번호,주소(행정구역),이메일,직업,종사분야,소속,정보수신 여부 내용으로 구성 되어 있습니다.">
                    <caption>내 정보관리 기본정보수정 표</caption>
                    <colgroup>
                        <col width="18%"/>
                        <col width="*"/>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row"><label for="f_motivation">가입동기</label></th>
                        <td>
                            <select class="select" name="p_registerMotive" id="f_motivation">
                                <option value="">가입동기 선택</option>
                                <option value="01">취업</option>
                                <option value="02">재취업</option>
                                <option value="03">자기개발</option>
                                <option value="04">창업</option>
                                <option value="05">재직중 재교육</option>
                                <option value="06">기타</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row"><label for="f_nominators">추천인 아이디</label></th>
                        <td>
                            <input type="text" name="p_recommendid" id="f_nominators" class="inputText" size="27" >
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div-->
            <!-- //tableWrap -->

            <div class="btnArea wd3">
                <button type="button" class="btnText" onclick="javascript:fnRegisterMember();"><span>확인</span></button>
                <a href="/servlet/controller.homepage.MainServlet" class="btnText2" title="회원가입을 취소하고 홈페이지 첫 화면으로 이동합니다."><span>취소</span></a>
            </div>
        </div>
        <!-- //#subCont -->
    </div>
</form>

</div>
<!--  //container-new 끝 //-->
<!-- form 끝 -->
<script type='text/javascript' src='/script/jquery.password_strength.js'></script>
<script language="JavaScript" type="text/JavaScript">
    $('form1').attr('autocomplete', 'off');
</script>

<%@ include file="/learn/user/2013/portal/include/footer.html"%>

<%
    }
%>
