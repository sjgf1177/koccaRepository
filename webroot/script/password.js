$(document).ready( function() {
    // alert(navigator.userAgent);
});
    
    /**
     * 비밀번호비밀번호 보안등급 체크
     * http://ysksoft.com
     */
    function fnGetPasswordLevel( pwdValue ) {
        // var elNewPasswd = document.getElementById( pwdObj.id );
        // var elPasswordLevel = document.getElementById("password_level");
        var result_level = checkPassword.main( pwdValue );

        return result_level["code"];

        /*
        var level_color = "";
        var level_txt = "";
        switch (result_level["code"]) {
            case 1:
                level_color = "#00D200";
                level_txt = "낮음";
                break;
            case 2:
                level_colorlevel_color = "#FF7837";
                level_txt = "보통";
                break;
            case 3:
                level_color = "#FF0000"; "#FF0000";
                level_txt = "높음";
                break;
            case 1000:
            case 2000:
            case 3000:
            case 4000:
                elPasswordLevel.innerHTML = "<font color=\"red\">" + result_level["msg"] + "</font>";
                return false;
                break;
            default:
                return false;
                break;
        }
        elPasswordLevel.innerHTML = "보안등급 : <font color="" + level_color + "" style=\"font-weight:bold\">" + level_txt + "</font>";
        */
    }

    var checkPassword = {
        aResultSecure: [],
        sPassword: "",
        sCheckRegexp1: /^[a-zA-Z]/,
        sCheckRegexp2: /[a-zA-Z0-9\`\~\!\@\#\$\%\^\&\*\(\)\-\_\+\{\}\[\]\,\.]/,
        sRegexp1: /[a-z]/,
        sRegexp2: /[A-Z]/,
        sRegexp3: /[0-9]/,
        sRegexp4: /[\`\~\!\@\#\$\%\^\&\*\(\)\-\_\+\{\}\[\]\,\.]/,

        main: function (sPassword) {

            this.aResultSecure["code"] = 0;
            this.aResultSecure["msg"] = false;
            this.sPassword = sPassword;
            this.sResultRegexp = this.checkRegexp();

            // 기본 검사
            // if (this.checkDefaultPassword() == true) {

                this.checkPasswordLevel1();

                // 낮은 단계 비밀번호 검사
                // 문자그룹 중에서 2가지만 조합하여 8자리~9자리
                // return 1
                this.checkPasswordLevel2();

                // 중간 단계단계 비밀번호 검사
                // 문자그룹 중에서중에서 2가지만 조합하여 10자리~13자리
                // 문자그룹문자그룹 중에서 영문대문자/영문소문자/숫자 조합하여 8자리 이상
                // return 2
                this.checkPasswordLevel3();

                // 높은 단계 비밀번호 검사검사
                // 문자그룹 중에서 2가지만 조합하여조합하여 14자리 이상
                // 문자그룹 중에서중에서 영문대문자/영문소문자/숫자 조합하여 9자리 이상
                // 문자그룹 중에서 특수문자 포함 3가지 이상 조합하여 8자리 이상
                // return 3
                this.checkPasswordLevel4();

                if (this.aResultSecure["code"] == 0) {
                    this.aResultSecure["code"] = 1000;
                    this.aResultSecure["msg"] = "영문+숫자, 혹은 영문+특수문자 등 비밀번호를 조합하여 입력해 주세요.";
                }
            // }
            return this.aResultSecure;
        },

        checkRegexp: function () {
            var rStr = "";
            if (this.sRegexp1.test(this.sPassword)) {   // 영문 소문자
                rStr += "1";
            }
            if (this.sRegexp2.test(this.sPassword)) {   // 영문 대문자
                rStr += "2";
            }
            if (this.sRegexp3.test(this.sPassword)) {   // 숫자
                rStr += "3";
            }
            if (this.sRegexp4.test(this.sPassword)) {   // 허용된 특수문자
                rStr += "4";
            }
            return rStr;
        },

        checkPasswordLevel1: function () {
            var sTemp = "";
            for (var x = 0; x < this.sPassword.length; x++) {
                sTemp = this.sPassword.substr((x * 1), 1);
                if (!this.sCheckRegexp2.test(sTemp)) {
                    this.aResultSecure["code"] = 1;
                    this.aResultSecure["msg"] = "[" + sTemp + "]는 사용 불가능한 특수문자입니다.";
                    break;
                }
            }
        },

        checkPasswordLevel2: function () {
            if (this.sPassword.length >= 8 && this.sPassword.length <= 9) {
                if (this.sResultRegexp.length == 2) {
                    this.aResultSecure["code"] = 2;
                    this.aResultSecure["msg"] = "2가지 조합이며 길이가 8자에서 9자 사이입니다.";
                }
            }
        },

        checkPasswordLevel3: function () {
            if (this.sPassword.length >= 10 && this.sPassword.length <= 13) {
                if (this.sResultRegexp.length == 2) {
                    this.aResultSecure["code"] = 3;
                }
            }
            if (this.sPassword.length >= 8) {
                if (this.sResultRegexp == "123") {
                    this.aResultSecure["code"] = 3;
                }
            }
        },

        checkPasswordLevel4: function () {
            if (this.sPassword.length >= 14) {
                if (this.sResultRegexp.length == 2) if (this.sResultRegexp.length == 2) {
                    this.aResultSecure["code"] = 4;
                }
            }
            if (this.sPassword.length >= 9) {
                if (this.sResultRegexp == "123") {
                    this.aResultSecure["code"] = 4;
                }
            }
            if (this.sPassword.length >= 8) {
                if (this.sResultRegexp.length >= 2 && this.sResultRegexp.indexOf("4") > -1) {
                    this.aResultSecure["code"] = 4;
                }
            }
        },

        checkPasswordLevel5: function () {
            if (this.sPassword.length >= 14) {
                if (this.sResultRegexp.length == 2) if (this.sResultRegexp.length == 2) {
                    this.aResultSecure["code"] = 4;
                }
            }
            if (this.sPassword.length >= 9) {
                if (this.sResultRegexp == "123") {
                    this.aResultSecure["code"] = 4;
                }
            }
            if (this.sPassword.length >= 8) {
                if (this.sResultRegexp.length >= 2 && this.sResultRegexp.indexOf("4") > -1) {
                    this.aResultSecure["code"] = 4;
                }
            }
        }

    }

    /**
     * 비밀번호 변경하기
     */
    function Submit_PWDCheck(){
        var pwdForm = document.passwordForm;

        var oldPwd = pwdForm.p_pwd_old.value;
        var newPwd = pwdForm.p_pwd.value;
        var newPwdRe = pwdForm.p_pwd_re.value;

        var chk_num = "";
        var chk_lower = "";
        var chk_upper = "";
        var chk_special = "";

        // 비밀번호 길이 제한

        var p_pwd_len =  pwdForm.p_pwd.value.length;
        var pass_old = pwdForm.p_pwd_old.value;
        var p_pwd_len_old = pass_old.length;

        chk_num = pwdForm.p_pwd.value.search(/[0-9]/g);
        chk_lower = pwdForm.p_pwd.value.search(/[a-z]/ig);
        chk_upper = pwdForm.p_pwd.value.search(/[A-Z]/ig);
        chk_special = pwdForm.p_pwd.value.search(/[^a-zA-Z0-9]/g);

        if ( oldPwd.length <= 0){
            alert("현재 비밀번호를 입력해주세요.");
            pwdForm.p_pwd_old.focus();
            return;
        } else if (p_pwd_len <= 0){
            alert("새 비밀번호를 입력해주세요.");
            pwdForm.p_pwd.focus();
            return;
        } else {
            if (pwdForm.p_pwd.value != "") {
                //if (!/^[a-zA-Z0-9]{9,14}$/.test(pwdForm.p_pwd.value) || (chk_num < 0 || chk_lower< 0 || chk_upper < 0)){
                if ( p_pwd_len < 9 || p_pwd_len > 14 || chk_lower< 0 || chk_upper < 0 ||  (chk_num < 0 && chk_special< 0 ) ){
                    alert("비밀번호는 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.");
                    pwdForm.p_pwd.value="";
                    pwdForm.p_pwd.focus();
                    return;
                }

                if( pwdForm.p_pwd.value == pwdForm.p_userid.value){
                    alert("아이디와 동일한 비밀번호는 사용 하실 수 없습니다.");
                    pwdForm.p_pwd.value="";
                    pwdForm.p_pwd.focus();return;
                }

                if( (pwdForm.p_pwd_re.value) == ""){
                    alert("새 비밀번호확인을 입력하세요");
                    pwdForm.p_pwd_re.focus();return;
                 }

                if(pwdForm.p_pwd.value != pwdForm.p_pwd_re.value){
                    alert("새 비밀번호가 다르게 입력되었습니다.");
                    pwdForm.p_pwd.value;
                    pwdForm.p_pwd_re.value = "";
                    pwdForm.p_pwd_re.focus();
                    return;
                }
            }
        }

        // 비밀번호 변경
        pwdForm.target = "_self";
        pwdForm.action = https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
        pwdForm.p_process.value = "ChangePwdOk";
        pwdForm.submit();
    }

    var gPasswordLevel = "";

    /**
     * 보안등급 안내 문구 toggle 함수
     */
    function fnToggleDiv( isShow ) {
        if ( isShow ) {
            $(".secure_info").css( {"display" : "inline-block"} );
        } else {
            $(".secure_info").css( {"display" : "none"} );
        }
    }

    function fnCheckPasswordLevel() {
        var pwd = $("#oNewPwd").val();
        gPasswordLevel = fnGetPasswordLevel(pwd);

        var msg = "미확인";
        if ( gPasswordLevel == 1) {
            msg = "<font style='color:#ff0000;'>불가</font>";
        } else if ( gPasswordLevel == 2) {
            msg = "<font style='color:#ff8000;'>낮음</font>";
        } else if ( gPasswordLevel == 3) {
            msg = "<font style='color:#80ff80;'>보통</font>";
        } else if ( gPasswordLevel == 4) {
            msg = "<font style='color:#0000ff;'>높음</font>";
        }


        $("#oLevelMessage").html(msg);
    }

    function fnUpdateUserPassword() {
        var docForm = document.form1;
        var oldPwd = docForm.p_pwd_old.value;
        var newPwd = docForm.p_pwd.value;
        var newPwdRe = docForm.p_pwd_re.value;

        var secureLevel = "";

        if (oldPwd.length == 0) {
            alert("현재 비밀번호를 입력하세요.");
            docForm.p_pwd_old.focus();
            return;
        } else if (newPwd.length == 0) {
            alert("새로운 비밀번호를 입력하세요.");
            docForm.p_pwd.focus();
            return;
        } else {
            alert(secureLevel);
        }
    }