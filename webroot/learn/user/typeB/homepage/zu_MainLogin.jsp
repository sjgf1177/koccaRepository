<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script language="JavaScript" type="text/JavaScript">
	$(document).ready(function(){
		$("#p_id").focus();
		
		$(document).on("keydown", "#p_pw", function(e){
			if (e.keyCode == 13){
				ASP_login();
			}
		});
	});

    // 로그인
    function ASP_login() {
    	if($("#p_id").val() == null || $("#p_id").val() == ""){
    		alert("아이디를 입력해주세요.");
    		$("#p_id").focus();
    		return;
    	}
    	
		if($("#p_pw").val() == null || $("#p_pw").val() == ""){
			alert("비밀번호를 입력해주세요.");
			$("#p_pw").focus();
			return;
    	}
		
       	mainmenu("5");
    }
</script>

<div class="d-flex align-items-center py-4 bg-body-tertiary h-100">
        <main class="form-signin w-100 m-auto">
            <form name="form1" id="form1" method="POST">
                <input type="hidden" name="p_process" value="" />
                <input type="hidden" name="gubun" value="" />
                <input type="hidden" name="menuid" value="" />

                <div class="text-center mb-5">
                    <img src="/images/newmain_20100625/<c:out value="${sessionScope.tem_grcode }"/>.png" alt="로고" onerror="this.src='/images/2023/logo.png'">
                    <p style="color: #c352b4; font-weight: 600; font-size: 18px; margin-top: 5px;">온라인 교육 전용 누리집</p>
                </div>

                <h1 class="h3 mb-5 fw-normal text-center">로그인</h1>
                <p style="font-weight: 600; font-size: 15px;">※ 예술인경력정보시스템과 별도로 회원가입 후 이용하실 수 있습니다.</p>


                <div class="form-floating">
                    <input type="text" class=" innut_id" id="p_id" name="p_id" placeholder="아이디입력" title="아이디입력" style="margin-bottom: 5px;">
                    <label for="p_id">아이디입력</label>
                </div>
                <div class="form-floating">
                    <input type="password" class=" input_pw" id="p_pw" name="p_pw" placeholder="비밀번호입력" title="비밀번호입력" style="margin-bottom: 30px;">
                    <label for="p_pw">비밀번호입력</label>
                </div>

                <button class="btn btn-purple w-100 py-2" type="button" value="로그인" onclick="javascript:ASP_login();" title="로그인"/>로그인</button>

                <div class="mt-4 mb-3 text-body-secondary d-flex justify-content-md-center">
                    <a href="javascript:mainmenu('1');" style="color: #8f9191;">회원가입</a>
                    <a href="javascript:mainmenu('2');" style="color: #8f9191;">아이디 / 비밀번호 찾기</a>
                </div>
            </form>
        </main>
    </div>
</body>
</html>
