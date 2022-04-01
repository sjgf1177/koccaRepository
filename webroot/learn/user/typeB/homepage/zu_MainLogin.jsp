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

<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_0.jsp">
                    	<jsp:param value="990" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>로그인</span>
                                <div class="linemap_wrap">
                                    <ul>
                                        <li>
                                            <a href="#">
                                                <span>
                                                    <img src="/common/image/home_icon.png" alt="메인">
                                                </span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#"><span>회원/로그인</span></a>
                                        </li>
                                        <li>
                                            <a href="#"><span>로그인</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_boarder_body login_box">
                                    <form name="form1" id="form1" method="POST">
                                    	<input type="hidden" name="p_process" value="" />
                                    	<input type="hidden" name="gubun" value="" />
                                    	<input type="hidden" name="menuid" value="" />
                                        <input type="text" name="p_id" id="p_id" title="아이디입력" placeholder="아이디입력111" />
                                        <input type="password" name="p_pw" id="p_pw" title="비밀번호입력" placeholder="비밀번호입력" />
                                        <input type="button" value="로그인" class="btn_login" onclick="javascript:ASP_login();" title="로그인"/>
                                    </form>
                                    <ul>
                                        <li><a href="javascript:mainmenu('1');">회원가입</a></li>
                                        <li><a href="javascript:mainmenu('2');">아이디 / 비밀번호 찾기</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>
