<%@ page import="com.credu.library.RequestBox" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<script type="text/javascript">
	var idExists = false;
	var emailExists = false;
	
	$(document).ready(function(){
		$(document).on("keyup", "input:text[name=p_kor_name]", function(){
			$(this).val($(this).val().replace(/[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:\"\\]/g,""));
		});
		
		$(document).on("keyup", "input:text[name=p_id]", function(){
			$(this).val($(this).val().replace(/[^a-z0-9]/g,""));
			idExists = false;
		});

		$("input:radio[name=p_cate]").click(function(){
			if($("input[name=p_cate]:checked").val() == "CT009") {
				$("#p_cate_txt").attr("disabled", false);
				$("#p_cate_txt").focus();
			}else {
				$("#p_cate_txt").attr("disabled", true);
				$("#p_cate_txt").val("");
			}
		});

		$("#p_notcompchk").click(function(){
			var checked = $("#p_notcompchk").is(":checked");

			if(!checked) {
				$("#p_deptnm").attr("disabled", false);
				$("#p_deptnm").focus();
			}else {
				$("#p_deptnm").attr("disabled", true);
				$("#p_deptnm").val("");
			}
		});
		
		$("#p_pw1").on("keyup", function(){
			var newpw = $("#p_pw1").val();
			var id = $("#p_id").val();
			
			var reg = /^.*(?=.{8,16})(?=.*[0-9!@#$%^&*()_+=<>?])(?=.*[a-zA-Z]).*$/;
			var msg = "";
			if(false === reg.test(newpw)) {
				msg = "사용할수 없는 비밀번호 입니다.";
				$("#chkMsg").css("color", "red");
				
			}else if(/(\w)\1\1/.test(newpw)){
				msg = "사용할수 없는 비밀번호 입니다.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.search(id) > -1){
				msg = "사용할수 없는 비밀번호 입니다.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.search(/\s/) != -1){
				msg = "사용할수 없는 비밀번호 입니다.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.length > 16 ){
				msg = "사용할수 없는 비밀번호 입니다.";
				$("#chkMsg").css("color", "red");
				
			}else{
				msg = "사용할수 있는 비밀번호 입니다.";
				$("#chkMsg").css("color", "green");
				
			}
			$("#chkMsg").text(msg);
			
		});
		$(document).on("keyup", "#p_birth_year, #p_birth_month, #p_birth_day, #p_tel1, #p_tel2, #p_mobil1, #p_mobil2", function(){
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$(document).on("keyup", "input:text[name=p_email1], input:text[name=p_email2]", function(){
			emailExists = false;
		});
		
		$(document).on("keyup", ".companyName", function(){
			$(this).val($(this).val().replace(/\s/gi,""));
		});
		
		$(document).on("change", "#p_email20", function(){
	        $("#p_email2").val($("#p_email20").val());
	        emailExists = false;
		});
		
		$(document).on("click", "#btn_cancel", function(){
			location.href = '/';
		});
		
		$(document).on("click", "#btn_next", function(){
			/* 항목 공백 제거 */
			$("input:text[name='p_kor_name']").val($.trim($("input:text[name='p_kor_name']").val()));
			$("input:text[name='p_birth_year']").val($.trim($("input:text[name='p_birth_year']").val()));
			$("input:text[name='p_birth_month']").val(addZero($.trim($("input:text[name='p_birth_month']").val())));
			$("input:text[name='p_birth_day']").val(addZero($.trim($("input:text[name='p_birth_day']").val())));
			$("input:text[name='p_id']").val($.trim($("input:text[name='p_id']").val()));
			$("input:text[name='p_email1']").val($.trim($("input:text[name='p_email1']").val()));
			$("input:text[name='p_email2']").val($.trim($("input:text[name='p_email2']").val()));
			$(".companyName").val($.trim($(".companyName").val()));

			if($(".companyName").length > 0 && $('.companyName').val() == ""){
				if(!$("#p_notcompchk").is(":checked")) {
					alert("회사명을 입력하여 주십시오.");
					$(".companyName").focus();
					return;
				}
			}

			if(($(":radio[name='p_cate']:checked").length < 1) && $(":radio[name='p_cate']").length > 0) {
				alert("분야를 선택해주세요.");
				$("#p_cate_1").focus();
				return;
			}

			if((!$("#p_comp_addr > option:selected").val()) && $("#p_comp_addr").length > 0) {
				alert("사업장 소재지를 선택해주세요.");
				$("#p_comp_addr").focus();
				return;
			}
			
			if($("input:text[name='p_kor_name']").val() == ""){
				alert("이름을 입력하여 주십시오.");
				$("input:text[name=p_kor_name]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_year']").val() == "" || $("input:text[name='p_birth_year']").val().length < 4){
				alert("생년월일 년도 4자리를 입력하여 주십시오.");
				$("input:text[name=p_birth_year]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_month']").val() == ""){
				alert("생년월일 월을 입력하여 주십시오.");
				$("input:text[name=p_birth_month]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_day']").val() == ""){
				alert("생년월일 일을 입력하여 주십시오.");
				$("input:text[name='p_birth_day']").focus();
				return;
			}
			
			
			if(!$("input:radio[name='p_sex']").is(':checked')){
				alert("성별을 선택하십시오.");
				$("input:radio[name='p_sex']").focus();
				return;
			}
			
			if($("input:text[name='p_id']").val() == ""){
				alert("아이디를 입력하여 주십시오.");
				$("input:text[name='p_id']").focus();
				return;
			}
			
			if(!idExists){
				alert("아이디 중복확인을 하시기 바랍니다.");
				$("input:text[name='p_id']").focus();
	            return;
			}
			
			if($("input:password[name='p_pw1']").val() == ""){
				alert("비밀번호를 입력하여 주십시오.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("input:password[name='p_pw1']").val().length < 8){
				alert("비밀번호는 8자리 이상이어야 합니다.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("input:password[name='p_pw2']").val() == ""){
				alert("비밀번호 확인을 입력하여 주십시오.");
				$("input:password[name='p_pw2']").focus();
				return;
			}
			
			if($("input:password[name='p_pw2']").val().length < 8){
				alert("비밀번호 확인은 8자리 이상이어야 합니다.");
				$("input:password[name='p_pw2']").focus();
				return;
			}
			
			if($("input:password[name='p_pw1']").val() != $("input:password[name='p_pw2']").val()){
				alert("비밀번호가 일치하지 않습니다.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("select[name='p_tel0']").val() == ""){
				alert("전화번호 앞자리를 선택하여 주십시오.");
				$("select[name='p_tel0']").focus();
				return;
			}
			
			if($("input:text[name='p_tel1']").val() == ""){
				alert("전화번호 중간자리를 입력하여 주십시오.");
				$("input:text[name='p_tel1']").focus();
				return;
			}
			
			if($("input:text[name='p_tel2']").val() == ""){
				alert("전화번호 끝자리를 입력하여 주십시오.");
				$("input:text[name='p_tel2']").focus();
				return;
			}
			
			if($("select[name='p_mobil0']").val() == ""){
				alert("휴대폰 앞자리를 선택하여 주십시오.");
				$("select[name='p_mobil0']").focus();
				return;
			}
			
			if($("input:text[name='p_mobil1']").val() == ""){
				alert("휴대폰 중간자리를 입력하여 주십시오.");
				$("input:text[name='p_mobil1']").focus();
				return;
			}
			
			if($("input:text[name='p_mobil2']").val() == ""){
				alert("휴대폰 끝자리를 입력하여 주십시오.");
				$("input:text[name='p_mobil2']").focus();
				return;
			}
			
			if($("input:text[name='p_email1']").val() == ""){
				alert("이메일 아이디를 입력하여 주십시오.");
				$("input:text[name='p_email1']").focus();
				return;
			}
			
			if($("input:text[name='p_email2']").val() == ""){
				alert("이메일  주소를 입력하여 주십시오.");
				$("input:text[name='p_email2']").focus();
				return;
			}
			
			if(!emailExists){
	            alert("이메일 중복확인을 하시기 바랍니다.");
	            $("input:text[name='p_email1']").focus();
	            return;
	        }
			
			if($("input:checkbox[name='p_agreed']").is(':checked')){
				$("input:checkbox[name='p_agreed']").val("Y");
			}else{
				$("input:checkbox[name='p_agreed']").val("N");
			}
			
			document.form_join.p_process.value = "ASP_Login_Insert";
            document.form_join.menuid.value = "0";
            document.form_join.gubun.value = "4";
            document.form_join.action = "/servlet/controller.homepage.MainServlet";
            document.form_join.submit();
		});
	});
	
	function addZero(num){
		return parseInt(num, 10) < 10 ? "0" + parseInt(num, 10) : num;
	}
	
	function existsId(){
		var id = $("#p_id").val();
		if(id == ""){
			alert("아이디를 입력하여 주십시오.");
			$("input:text[name='p_id']").focus();
			return;
		}
		
		if(id.length < 6 || id.length > 30){
			alert("아이디는 총 6~30자로만 조합이 되어야 합니다.");
			$("input:text[name='p_id']").focus();
			return;
		}
		
		$.ajax({
			url : '/servlet/controller.homepage.MainServlet',
			type : 'post',
			data : {userId:id, p_process:"ASP_Login", menuid:"0", gubun:"20"},
			success : function(data){
				var result = $.trim(data);
				$("#id_alter_area").text("");
				idExists=false;
				
				if(result == "1"){
					idExists = true;
					$("#id_alter_area").css("color","#0000FF");
					$("#id_alter_area").text("사용할수 있는 아이디 입니다.");
					alert("사용할수 있는 아이디 입니다.");
					return;
				}else{
					idExists = false;
					$("#id_alter_area").css("color","#FF0000");
					$("#id_alter_area").text("사용할수 없는 아이디 입니다.");
					alert("사용할수 없는 아이디 입니다.");
					return;
				}
			}
		});
	}
	
	function existsEmail(){
		var email1 = $.trim($("input:text[name='p_email1']").val());
		var email2 = $.trim($("input:text[name='p_email2']").val());
		if(email1 == "" || email2 == ""){
			alert("이메일을 입력하여 주십시오.");
			if(email1 == ""){
				$("input:text[name='p_email1']").focus();
			}else{
				$("input:text[name='p_email2']").focus();
			}
			return;
		}
		
		$.ajax({
			url : '/servlet/controller.homepage.MainServlet',
			type : 'post',
			data : {userEmail:email1+"@"+email2, p_process:"ASP_Login", menuid:"0", gubun:"25"},
			success : function(data){
				var result = $.trim(data);
				$("#email_alter_area").text("");
				emailExists=false;
				
				if(result == "1"){
					emailExists = true;
					$("#email_alter_area").css("color","#0000FF");
					$("#email_alter_area").text("사용할수 있는 이메일 입니다.");
					alert("사용할수 있는 이메일 입니다.");
					return;
				}else{
					emailExists = false;
					$("#email_alter_area").css("color","#FF0000");
					$("#email_alter_area").text("사용할수 없는 이메일 입니다.");
					alert("사용할수 없는 이메일 입니다.");
					return;
				}
			}
		});
	}
</script>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <jsp:include page="/learn/user/typeB/include_left/left_0.jsp">
                    	<jsp:param value="1" name="left_active"/>
                    </jsp:include>
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span>회원가입</span>
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
                                            <a href="#"><span>회원가입</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body join_box">
                                    <div class="signUp_step_bar">
                                        <div class="step_wrap">
                                            <p>
                                                <span>STEP1</span>이용약관 동의
                                            </p>
                                        </div>
                                        <div class="step_line_wrap">
                                            <div class="step_right_arrow"></div>
                                        </div>
                                        <div class="step_wrap active">
                                            <p>
                                                <span>STEP2</span>회원정보 입력
                                            </p>
                                        </div>
                                        <div class="step_line_wrap">
                                            <div class="step_right_arrow"></div>
                                        </div>
                                        <div class="step_wrap">
                                            <p>
                                                <span>STEP3</span>가입완료
                                            </p>
                                        </div>
                                    </div>
                                    <p class="sub_course_view_title">회원정보</p>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                        	<form name="form_join" id="form_join" method="POST">
                                        		<input type="hidden" name="p_process" value="" />
                                        		<input type="hidden" name="menuid" value="" />
                                        		<input type="hidden" name="gubun" value="" />
	                                            <table class="write_table th_align_left1">
	                                                <colgroup>
	                                                    <col width="30%">
	                                                    <col width="auto">
	                                                </colgroup>
	                                               	<tbody>
		                                                <c:choose>
	                                                    	<c:when test="${sessionScope.tem_grcode eq 'N000210'}">
																<h4 style="color: red;">※ 교육안내, 수료증 발급 등을 위해 정확한 정보를 입력해주세요.</h4>
	                                                    		<tr>
		                                                    		<th><span>*</span>회사명</th>
			                                                        <td>
			                                                        	<input type="text" style="width:100%" name="p_deptnm" id="p_deptnm" title="회사명" class="companyName">
																		<span style="width: 100%;">※ 사업자가 없는 창작자 등 소속이 없는 경우, ‘소속없음’ 에 체크해주세요.</span>
																		<span>
																		<input type="checkbox" name="p_notcompchk" id="p_notcompchk" value="Y" title="소속없음">
																		<label for="p_notcompchk">소속없음</label>
																		</span>
			                                                        </td>
			                                                    </tr>
																<tr>
																	<th><span>*</span>분야</th>
																	<td>
																		<%
																			List cateList = CodeAdminBean.selectListCode("0122");

																			if(cateList != null && cateList.size() > 0 ){
																				String sCode   = "";
																				String sCodeNm = "";
																				for(int i = 0 ; i < cateList.size() ; i++){
																					DataBox codeBox = (DataBox)cateList.get(i);

																					sCode   = codeBox.getString("d_code");
																					sCodeNm = codeBox.getString("d_codenm");

																		%>
																			<input type="radio" name="p_cate" value="<%=sCode %>" id="p_cate_<%=i %>" title="<%=sCodeNm %>">
																			<label for="p_cate_<%=i %>"><%=sCodeNm %></label>
																		<%

																				}
																			}
																		%>
																		<input type="text" name="p_cate_txt" id="p_cate_txt" title="기타" disabled>
																	</td>
																</tr>
																<tr>
																	<th><span>*</span>사업장 소재지</th>
																	<td>
																		<label for="p_comp_location"></label>
																		<select class="email3" name="p_comp_location" id="p_comp_location" title="사업장 소재지 선택">
																			<option value="" title="선택">선택</option>
																			<%
																				List cLoctList = CodeAdminBean.selectListCode("0123");

																				if(cLoctList != null && cLoctList.size() > 0 ){
																					String sCode   = "";
																					String sCodeNm = "";
																					for(int i = 0 ; i < cLoctList.size() ; i++){
																						DataBox codeBox = (DataBox)cLoctList.get(i);

																						sCode   = codeBox.getString("d_code");
																						sCodeNm = codeBox.getString("d_codenm");

																			%>
																				<option value="<%=sCode %>" title="<%=sCodeNm %>"><%=sCodeNm %></option>
																			<%

																					}
																				}
																			%>
																		</select>
																	</td>
																</tr>
		                                                        <tr>
			                                                        <th><span>*</span>성명(한글)</th>
			                                                        <td><input type="text" name="p_kor_name" id="p_kor_name" title="성명(한글)"></td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>생년월일</th>
			                                                        <td>
			                                                            <input type="text" name="p_birth_year" id="p_birth_year" maxlength="4" class="year" title="생년월일 (년도)">년
			                                                            <input type="text" name="p_birth_month" id="p_birth_month" maxlength="2" class="month" title="생년월일 (월)">월
			                                                            <input type="text" name="p_birth_day" id="p_birth_day" maxlength="2" class="day" title="생년월일 (일)">일
			                                                            <span>예(1972년 01월 01일)</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>성별</th>
			                                                        <td>
			                                                            <input type="radio" name="p_sex" value="2" id="gender2" title="여자">
			                                                            <label for="gender2">여자</label>
			                                                            <input type="radio" name="p_sex" value="1" id="gender1" title="남자">
			                                                            <label for="gender1">남자</label>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>아이디</th>
			                                                        <td>
			                                                            <input type="text" name="p_id" id="p_id" maxlength="16" title="아이디 6~16자의 영문, 숫자 조합" />
			                                                            <a href="javascript:existsId();">중복확인</a>
			                                                            <span id="id_alter_area"></span>
			                                                            <span>* 아이디는 6~30자의 소문자 영문, 숫자의 조합으로 만들 수 있습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="비밀번호 (8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.)" />
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <span>* 비밀번호는 8 ~ 16자 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.</span>
					                                                    <span>* ID와 동일한 비밀번호, 동일문자, 연속문자 등은 사용하실 수 없습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>비밀번호 확인</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="비밀번호 확인 (비밀번호와 동일하게 입력해주세요.)">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>전화번호</th>
			                                                        <td><label for="p_tel0"></label>
			                                                            <select class="phone1" name="p_tel0" id="p_tel0" title="전화번호 앞자리 선택">
			                                                                <option value="02" title="02">02</option>
			                                                                <option value="031" title="031">031</option>
			                                                                <option value="032" title="032">032</option>
			                                                                <option value="033" title="033">033</option>
			                                                                <option value="041" title="041">041</option>
			                                                                <option value="042" title="042">042</option>
			                                                                <option value="043" title="043">043</option>
			                                                                <option value="044" title="044">044</option>
			                                                                <option value="051" title="051">051</option>
			                                                                <option value="052" title="052">052</option>
			                                                                <option value="053" title="053">053</option>
			                                                                <option value="054" title="054">054</option>
			                                                                <option value="055" title="055">055</option>
			                                                                <option value="061" title="061">061</option>
			                                                                <option value="062" title="062">062</option>
			                                                                <option value="063" title="063">063</option>
			                                                                <option value="064" title="064">064</option>
			                                                                <option value="070" title="070">070</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_tel1" id="p_tel1" maxlength="4" title="전화번호 중간자리 입력">-
			                                                            <input type="text" class="phone3" name="p_tel2" id="p_tel2" maxlength="4" title="전화번호 뒷자리 입력">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>휴대폰</th>
			                                                        <td><label for="p_mobil0"></label>
			                                                            <select class="phone1" name="p_mobil0" id="p_mobil0" title="휴대폰 앞자리 선택">
			                                                                <option value="010" title="010">010</option>
			                                                                <option value="011" title="011">011</option>
			                                                                <option value="016" title="016">016</option>
			                                                                <option value="017" title="017">017</option>
			                                                                <option value="018" title="018">018</option>
			                                                                <option value="019" title="019">019</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_mobil1" id="p_mobil1" maxlength="4" title="휴대폰 중간자리 입력">-
			                                                            <input type="text" class="phone3" name="p_mobil2" id="p_mobil2" maxlength="4" title="휴대폰 뒷자리 입력">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>이메일</th>
			                                                        <td>
			                                                            <input type="text" class="email1" name="p_email1" id="p_email1" title="이메일  아이디">@
			                                                            <input type="text" class="email2" name="p_email2" id="p_email2" title="이메일  주소">
			                                                            <label for="p_email20"></label>
			                                                            <select class="email3" name="p_email20" id="p_email20" title="이메일  주소 선택">
			                                                                <option value="" title="직접입력">직접입력</option>
			                                                                <option value="naver.com" title="naver.com">naver.com</option>
			                                                                <option value="gmail.com" title="gmail.com">gmail.com</option>
			                                                                <option value="hanmail.net" title="hanmail.net">hanmail.net</option>
			                                                                <option value="nate.com" title="nate.com">nate.com</option>
			                                                            </select>
			                                                            <a href="javascript:existsEmail();" title="이메일 중복확인">중복확인</a>
			                                                            <span id="email_alter_area"></span>
			                                                            <span>* 한국콘텐츠진흥원에서 제공하는 웹진을 비롯 회원정보 수정정관련 필요한 것으로 주로 사용하시는 이메일 주소를 입력해 주세요.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th>정보수신 여부</th>
			                                                        <td>
			                                                            <input type="checkbox" name="p_agreed" id="p_agreed" value="Y" title="정보수신 여부 (서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.)">
			                                                            <label for="p_agreed">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</label>
			                                                        </td>
			                                                    </tr>
	                                                    	</c:when>
	                                                    	<c:otherwise>
	                                                    		<tr>
			                                                        <th><span>*</span>성명(한글)</th>
			                                                        <td><input type="text" name="p_kor_name" id="p_kor_name" title="성명(한글)"></td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>생년월일</th>
			                                                        <td>
			                                                            <input type="text" name="p_birth_year" id="p_birth_year" maxlength="4" class="year" title="생년월일 (년도)">년
			                                                            <input type="text" name="p_birth_month" id="p_birth_month" maxlength="2" class="month" title="생년월일 (월)">월
			                                                            <input type="text" name="p_birth_day" id="p_birth_day" maxlength="2" class="day" title="생년월일 (일)">일
			                                                            <span>예(1972년 01월 01일)</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>성별</th>
			                                                        <td>
			                                                            <input type="radio" name="p_sex" value="2" id="gender2" title="여자">
			                                                            <label for="gender2">여자</label>
			                                                            <input type="radio" name="p_sex" value="1" id="gender1" title="남자">
			                                                            <label for="gender1">남자</label>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>아이디</th>
			                                                        <td>
			                                                            <input type="text" name="p_id" id="p_id" maxlength="16" title="아이디 6~16자의 영문, 숫자 조합" />
			                                                            <a href="javascript:existsId();">중복확인</a>
			                                                            <span id="id_alter_area"></span>
			                                                            <span>* 아이디는 6~30자의 영문, 숫자의 조합으로 만들 수 있습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="비밀번호 (8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.)" />
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <span>* 비밀번호는 8 ~ 16자 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.</span>
					                                                    <span>* ID와 동일한 비밀번호, 동일문자, 연속문자 등은 사용하실 수 없습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>비밀번호 확인</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="비밀번호 확인 (비밀번호와 동일하게 입력해주세요.)">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>전화번호</th>
			                                                        <td><label for="p_tel0"></label>
			                                                            <select class="phone1" name="p_tel0" id="p_tel0" title="전화번호 앞자리 선택">
			                                                                <option value="02" title="02">02</option>
			                                                                <option value="031" title="031">031</option>
			                                                                <option value="032" title="032">032</option>
			                                                                <option value="033" title="033">033</option>
			                                                                <option value="041" title="041">041</option>
			                                                                <option value="042" title="042">042</option>
			                                                                <option value="043" title="043">043</option>
			                                                                <option value="044" title="044">044</option>
			                                                                <option value="051" title="051">051</option>
			                                                                <option value="052" title="052">052</option>
			                                                                <option value="053" title="053">053</option>
			                                                                <option value="054" title="054">054</option>
			                                                                <option value="055" title="055">055</option>
			                                                                <option value="061" title="061">061</option>
			                                                                <option value="062" title="062">062</option>
			                                                                <option value="063" title="063">063</option>
			                                                                <option value="064" title="064">064</option>
			                                                                <option value="070" title="070">070</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_tel1" id="p_tel1" maxlength="4" title="전화번호 중간자리 입력">-
			                                                            <input type="text" class="phone3" name="p_tel2" id="p_tel2" maxlength="4" title="전화번호 뒷자리 입력">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>휴대폰</th>
			                                                        <td><label for="p_mobil0"></label>
			                                                            <select class="phone1" name="p_mobil0" id="p_mobil0" title="휴대폰 앞자리 선택">
			                                                                <option value="010" title="010">010</option>
			                                                                <option value="011" title="011">011</option>
			                                                                <option value="016" title="016">016</option>
			                                                                <option value="017" title="017">017</option>
			                                                                <option value="018" title="018">018</option>
			                                                                <option value="019" title="019">019</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_mobil1" id="p_mobil1" maxlength="4" title="휴대폰 중간자리 입력">-
			                                                            <input type="text" class="phone3" name="p_mobil2" id="p_mobil2" maxlength="4" title="휴대폰 뒷자리 입력">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>이메일</th>
			                                                        <td>
			                                                            <input type="text" class="email1" name="p_email1" id="p_email1" title="이메일  아이디">@
			                                                            <input type="text" class="email2" name="p_email2" id="p_email2" title="이메일  주소">
			                                                            <label for="p_email20"></label>
			                                                            <select class="email3" name="p_email20" id="p_email20" title="이메일  주소 선택">
			                                                                <option value="" title="직접입력">직접입력</option>
			                                                                <option value="naver.com" title="naver.com">naver.com</option>
			                                                                <option value="gmail.com" title="gmail.com">gmail.com</option>
			                                                                <option value="hanmail.net" title="hanmail.net">hanmail.net</option>
			                                                                <option value="nate.com" title="nate.com">nate.com</option>
			                                                            </select>
			                                                            <a href="javascript:existsEmail();" title="이메일 중복확인">중복확인</a>
			                                                            <span id="email_alter_area"></span>
			                                                            <span>* 한국콘텐츠진흥원에서 제공하는 웹진을 비롯 회원정보 수정정관련 필요한 것으로 주로 사용하시는 이메일 주소를 입력해 주세요.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th>정보수신 여부</th>
			                                                        <td>
			                                                            <input type="checkbox" name="p_agreed" id="p_agreed" value="Y" title="정보수신 여부 (서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.)">
			                                                            <label for="p_agreed">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</label>
			                                                        </td>
			                                                    </tr>
		                                                    	<tr>
		                                                    		<th>
			                                                        	소속부서
			                                                        </th>
			                                                        <td><input type="text" style="width:100%" name="p_deptnm" id="p_deptnm" title="소속부서"></td>
			                                                    </tr>
	                                                    	</c:otherwise>
	                                                   	</c:choose>
                                                   	</tbody>
	                                            </table>
                                            </form>
                                            <div class="agree_btn_box">
                                                <a href="javascript:void(0);" title="취소" class="btn_cancel" id="btn_cancel">취소</a>
                                                <a href="javascript:void(0);" title="가입" class="btn_next" id="btn_next">가입</a>
                                            </div>
                                        </div>
                                    </div>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>