<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String v_cate 	  = "";
	String v_comp_loc = "";

	DataBox dbox = (DataBox)request.getAttribute("ASP_Edit_Login");
	if (dbox != null) {
		v_cate 	   = dbox.getString("d_cate_field");
		v_comp_loc = dbox.getString("d_comp_loct");
	}
%>
<script type="text/javascript">
	$(document).ready(function(){
		if('<c:out value="${ASP_Edit_Login.d_ismailing}" />' == 'Y'){
			$("[name='p_agreed_Yn']").attr("checked", true);
            $("[name='p_agreed']").val("Y");
		}else{
			$("[name='p_agreed_Yn']").attr("checked", false);
            $("[name='p_agreed']").val("N");
		}

		if('<c:out value="${ASP_Edit_Login.d_isnotcomp}" />' == 'Y'){
			$("[name='p_notcompchk']").attr("checked", true);
			$("#p_deptnm").attr("disabled", true);
			$("#p_deptnm").val("");
		}else{
			$("[name='p_notcompchk']").attr("checked", false);
			$("#p_deptnm").attr("disabled", false);
		}

		$("#p_notcompchk").click(function(){
			var checked = $("#p_notcompchk").is(":checked");

			if(!checked) {
				$("#p_deptnm").attr("disabled", false);
				$("#p_deptnm").val("${ASP_Edit_Login.d_deptnam }");
				$("#p_deptnm").focus();
			}else {
				$("#p_deptnm").attr("disabled", true);
				$("#p_deptnm").val("");
			}
		});

		$("input:radio[name=p_cate]").click(function(){
			if($("input[name=p_cate]:checked").val() == "CT009") {
				$("#p_cate_txt").attr("disabled", false);
				$("#p_cate_txt").val("${ASP_Edit_Login.d_cate_txt}");
				$("#p_cate_txt").focus();
			}else {
				$("#p_cate_txt").attr("disabled", true);
				$("#p_cate_txt").val("");
			}
		});

		if('<c:out value="${ASP_Edit_Login.d_cate_field}" />' == 'CT009'){
			$("#p_cate_txt").attr("disabled", false);
		}else{
			$("#p_cate_txt").attr("disabled", true);
			$("#p_cate_txt").val("");
		}
		
		$(document).on("keyup", "#p_tel1, #p_tel2, #p_mobil1, #p_mobil2", function(){
			$(this).val($(this).val().replace(/[^0-9]/g,""));
		});
		
		$("#p_pw1").on("keyup", function(){
			var newpw = $("#p_pw1").val();
			var id = "<c:out value='${ASP_Edit_Login.d_userid }'/>";
			
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
		
		$(document).on("click", "#btn_change_pwd", function(){
			var r=$.trim($("[name='p_usepw']").val());
	        var r1=$.trim($("[name='p_pw1']").val());
	        var r2=$.trim($("[name='p_pw2']").val());
	        
	        if(r==""){ alert("현재 암호를 입력하세요."); $("[name='p_usepw']").focus(); return; }
	        if(r1.length<8){ alert("암호의 최소 길이는 8자리 입니다.다시 입력하세요."); $("[name='p_pw1']").focus(); return; }
	        if(r1==""){ alert("새로운 암호를 입력하세요."); $("[name='p_pw1']").focus(); return; }
	        if(r2.length<8){ alert("암호의 최소 길이는 8자리 입니다.다시 입력하세요."); $("[name='p_pw2']").focus(); return; }
	        if(r2==""){ alert("새로운 확인 암호를 다시 입력하세요."); $("[name='p_pw2']").focus(); return; }
	        if(r1!=r2){ alert("암호가 서로 일치하지 않습니다."); $("[name='p_pw1']").val(""); $("[name='p_pw2']").val(""); $("[name='p_pw1']").focus(); return; }

	        $.ajax({
				url : '/servlet/controller.homepage.MainServlet',
				type : 'post',
				data : {p_usepw:r, p_pw1:r1, p_pw2:r2, p_process:"ASP_Login", menuid:"0", gubun:"100"},
				success:function(data){
					var r10=$.trim(data);
	        		if(r10=="0"){
	                    alert("암호가 일치하지 않거나 없는 데이타 입니다.");
	        			return;
	        		}else{
	                    alert("암호가 정상적으로 변경 되었습니다.");
	                    $("[name='p_usepw']").val('');
	                    $("[name='p_pw1']").val('');
	                    $("[name='p_pw2']").val('');
	                    return;
	        		}
				}
	        });
		});
		
		$(document).on("change", "input[name='p_agreed_Yn']", function(){
			if($("[name='p_agreed_Yn']").is(":checked")){
				$("[name='p_agreed']").val("Y");
			}else{
				$("[name='p_agreed']").val("N");
			}
		});
		
		$(document).on("click", "#btn_next", function(){
			/* if($(".companyName").length > 0 && $('.companyName').val() == ""){
				alert("회사명을 입력하여 주십시오.");
				$(".companyName").focus();
				return;
			} */
			
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
			
			document.form_modify.p_process.value = "ASP_Login_Update";
            document.form_modify.menuid.value = "0";
            document.form_modify.gubun.value = "";
            document.form_modify.action = "/servlet/controller.homepage.MainServlet";
            document.form_modify.submit();
		});
	});

</script>
<section class="container d-flex">
	<div class=""></div>
	<div class="subContainer">
		<div class="sub_section">
			<div class="sub_contents_body">
				<div class="sub_board_header">

                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <span style="margin-top: 30px;">개인정보 변경</span>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body join_box">
                                    <p class="sub_course_view_title">회원정보</p>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                        	<form name="form_modify" id="form_modify" method="POST">
                                        		<input type="hidden" name="p_process" value="" />
                                        		<input type="hidden" name="menuid" value="0" />
                                        		<input type="hidden" name="gubun" value="4" />
                                        		
	                                            <table class="write_table th_align_left1">
	                                                <colgroup>
	                                                    <col width="30%">
	                                                    <col width="auto">
	                                                </colgroup>
	                                                <tbody>
	                                                	<c:choose>
	                                                    	<c:when test="${sessionScope.tem_grcode eq 'N000210'}">
		                                                        <tr>
		                                                    		<th>
			                                                        	회사명
			                                                        </th>
			                                                        <td>
			                                                        	<input type="text" name="p_deptnm" id="p_deptnm" title="회사명" value="<c:out value="${ASP_Edit_Login.d_deptnam }"/>">
																		<span style="width: 100%;">※ 사업자가 없는 창작자 등 소속이 없는 경우, ‘소속없음’ 에 체크해주세요.</span>
																		<span>
																			<input type="checkbox" name="p_notcompchk" id="p_notcompchk" checked value="Y" title="소속없음">
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
																			<input type="radio" name="p_cate" value="<%=sCode %>" id="p_cate_<%=i %>" <%=sCode.equals(v_cate) ? "checked" : ""%> title="<%=sCodeNm %>">
																			<label for="p_cate_<%=i %>"><%=sCodeNm %></label>
																		<%

																				}
																			}
																		%>
																		<input type="text" name="p_cate_txt" id="p_cate_txt" title="기타" disabled value="<c:out value="${ASP_Edit_Login.d_cate_txt }"/>">
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
																				<option value="<%=sCode %>" title="<%=sCodeNm %>" <%=sCode.equals(v_comp_loc) ? "selected" : ""%>><%=sCodeNm %></option>
																			<%

																					}
																				}
																			%>
																		</select>
																	</td>
																</tr>
		                                                        <tr>
			                                                        <th><span>*</span>성명(한글)</th>
			                                                        <td><c:out value="${ASP_Edit_Login.d_name }" /> </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>아이디</th>
			                                                        <td><c:out value="${ASP_Edit_Login.d_userid }" /></td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>현재 비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_usepw" id="p_usepw" title="현재 비밀번호" />
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>새로운 비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="새로운 비밀번호 (8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.)">
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <span>* 비밀번호는 8 ~ 16자 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.</span>
					                                                    <span>* ID와 동일한 비밀번호, 동일문자, 연속문자 등은 사용하실 수 없습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>새로운 비밀번호 확인</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="새로운 비밀번호 확인">
			                                                            <a href="javascript:void(0);" title="비빌번호 변경" id="btn_change_pwd" class="btn btn-purple">비밀번호 변경</a>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span><label for="p_tel0">전화번호</label></th>
			                                                        <td>
			                                                            <select class="phone1" name="p_tel0" id="p_tel0" title="전화번호 앞자리 선택">
			                                                                <option value="02" title="02" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '02' }"> selected </c:if>>02</option>
			                                                                <option value="031" title="031" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '031' }"> selected </c:if>>031</option>
			                                                                <option value="032" title="032" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '032' }"> selected </c:if>>032</option>
			                                                                <option value="033" title="033" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '033' }"> selected </c:if>>033</option>
			                                                                <option value="041" title="041" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '041' }"> selected </c:if>>041</option>
			                                                                <option value="042" title="042" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '042' }"> selected </c:if>>042</option>
			                                                                <option value="043" title="043" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '043' }"> selected </c:if>>043</option>
			                                                                <option value="044" title="044" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '044' }"> selected </c:if>>044</option>
			                                                                <option value="051" title="051" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '051' }"> selected </c:if>>051</option>
			                                                                <option value="052" title="052" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '052' }"> selected </c:if>>052</option>
			                                                                <option value="053" title="053" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '053' }"> selected </c:if>>053</option>
			                                                                <option value="054" title="054" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '054' }"> selected </c:if>>054</option>
			                                                                <option value="055" title="055" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '055' }"> selected </c:if>>055</option>
			                                                                <option value="061" title="061" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '061' }"> selected </c:if>>061</option>
			                                                                <option value="062" title="062" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '062' }"> selected </c:if>>062</option>
			                                                                <option value="063" title="063" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '063' }"> selected </c:if>>063</option>
			                                                                <option value="064" title="064" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '064' }"> selected </c:if>>064</option>
			                                                                <option value="070" title="070" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '070' }"> selected </c:if>>070</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_tel1" id="p_tel1" maxlength="4" title="전화번호 중간자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_hometel,'-')[1]}" />">
			                                                            -
			                                                            <input type="text" class="phone3" name="p_tel2" id="p_tel2" maxlength="4" title="전화번호 끝자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_hometel,'-')[2]}" />">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span><label for="p_mobil0">휴대폰</label></th>
			                                                        <td>
			                                                        	<select class="phone1" name="p_mobil0" id="p_mobil0" title="휴대폰 앞자리 선택">
			                                                                <option value="010" title="010" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '010' }"> selected </c:if>>010</option>
			                                                                <option value="011" title="011" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '011' }"> selected </c:if>>011</option>
			                                                                <option value="016" title="016" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '016' }"> selected </c:if>>016</option>
			                                                                <option value="017" title="017" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '017' }"> selected </c:if>>017</option>
			                                                                <option value="018" title="018" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '018' }"> selected </c:if>>018</option>
			                                                                <option value="019" title="019" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '019' }"> selected </c:if>>019</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_mobil1" id="pmobil1" maxlength="4" title="휴대폰 중간자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_handphone,'-')[1]}" />">
			                                                            -
			                                                            <input type="text" class="phone3" name="p_mobil2" id="pmobil2" maxlength="4" title="휴대폰 끝자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_handphone,'-')[2]}" />">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>이메일</th>
			                                                        <td>
			                                                        	<c:set var="email1" value="${fn:split(ASP_Edit_Login.d_email,'@')[0]}" /> 
			                                                        	<c:set var="email2" value="${fn:split(ASP_Edit_Login.d_email,'@')[1]}" />
			                                                            <c:choose>
			                                                            	<c:when test="${email1 ne '' || email1 ne null || email2 ne '' || email2 ne null}">
			                                                            		<input type="text" class="email1" name="p_email1" id="p_email1" value="${email1 }" title="이메일  아이디">
			                                                            		@
			                                                            		<input type="text" class="email2" name="p_email2" id="p_email2" value="${email2 }" title="이메일  주소">
			                                                            	</c:when>
			                                                            	<c:otherwise>
			                                                            		이메일 등록을 원할시 운영자에게 연락바랍니다.(02-2161-0077)
			                                                            	</c:otherwise>
			                                                            </c:choose>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th>정보수신 여부</th>
			                                                        <td>
			                                                        	<input type="hidden" name="p_agreed" id="p_agreed" title="정보수신 여부 전송값" />
			                                                            <input type="checkbox" name="p_agreed_Yn" id="p_agreed_Yn" title="정보수신 여부 (서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.)" />
			                                                            <label for="p_agreed_Yn">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</label>
			                                                        </td>
			                                                    </tr>
		                                                        
	                                                    	</c:when>
	                                                    	<c:otherwise>
	                                                    		<tr>
			                                                        <th><span>*</span>성명(한글)</th>
			                                                        <td><c:out value="${ASP_Edit_Login.d_name }" /> </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>아이디</th>
			                                                        <td><c:out value="${ASP_Edit_Login.d_userid }" /></td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>현재 비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_usepw" id="p_usepw" title="현재 비밀번호" />
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>새로운 비밀번호</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="새로운 비밀번호 (8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.)">
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <span>* 비밀번호는 8 ~ 16자 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.</span>
					                                                    <span>* ID와 동일한 비밀번호, 동일문자, 연속문자 등은 사용하실 수 없습니다.</span>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>비밀번호 확인</th>
			                                                        <td>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="새로운 비밀번호 확인">
			                                                            <a href="javascript:void(0);" title="비빌번호 변경" id="btn_change_pwd" class="btn btn-purple">비밀번호 변경</a>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span><label for="p_tel0">전화번호</label></th>
			                                                        <td>
			                                                            <select class="phone1" name="p_tel0" id="p_tel0" title="전화번호 앞자리 선택">
			                                                                <option value="02" title="02" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '02' }"> selected </c:if>>02</option>
			                                                                <option value="031" title="031" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '031' }"> selected </c:if>>031</option>
			                                                                <option value="032" title="032" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '032' }"> selected </c:if>>032</option>
			                                                                <option value="033" title="033" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '033' }"> selected </c:if>>033</option>
			                                                                <option value="041" title="041" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '041' }"> selected </c:if>>041</option>
			                                                                <option value="042" title="042" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '042' }"> selected </c:if>>042</option>
			                                                                <option value="043" title="043" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '043' }"> selected </c:if>>043</option>
			                                                                <option value="044" title="044" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '044' }"> selected </c:if>>044</option>
			                                                                <option value="051" title="051" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '051' }"> selected </c:if>>051</option>
			                                                                <option value="052" title="052" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '052' }"> selected </c:if>>052</option>
			                                                                <option value="053" title="053" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '053' }"> selected </c:if>>053</option>
			                                                                <option value="054" title="054" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '054' }"> selected </c:if>>054</option>
			                                                                <option value="055" title="055" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '055' }"> selected </c:if>>055</option>
			                                                                <option value="061" title="061" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '061' }"> selected </c:if>>061</option>
			                                                                <option value="062" title="062" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '062' }"> selected </c:if>>062</option>
			                                                                <option value="063" title="063" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '063' }"> selected </c:if>>063</option>
			                                                                <option value="064" title="064" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '064' }"> selected </c:if>>064</option>
			                                                                <option value="070" title="070" <c:if test="${fn:split(ASP_Edit_Login.d_hometel,'-')[0] eq '070' }"> selected </c:if>>070</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_tel1" id="p_tel1" maxlength="4" title="전화번호 중간자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_hometel,'-')[1]}" />">
			                                                            -
			                                                            <input type="text" class="phone3" name="p_tel2" id="p_tel2" maxlength="4" title="전화번호 끝자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_hometel,'-')[2]}" />">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span><label for="p_mobil0">휴대폰</label></th>
			                                                        <td>
			                                                        	<select class="phone1" name="p_mobil0" id="p_mobil0" title="휴대폰 앞자리 선택">
			                                                                <option value="010" title="010" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '010' }"> selected </c:if>>010</option>
			                                                                <option value="011" title="011" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '011' }"> selected </c:if>>011</option>
			                                                                <option value="016" title="016" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '016' }"> selected </c:if>>016</option>
			                                                                <option value="017" title="017" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '017' }"> selected </c:if>>017</option>
			                                                                <option value="018" title="018" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '018' }"> selected </c:if>>018</option>
			                                                                <option value="019" title="019" <c:if test="${fn:split(ASP_Edit_Login.d_handphone,'-')[0] eq '019' }"> selected </c:if>>019</option>
			                                                            </select>
			                                                            -
			                                                            <input type="text" class="phone2" name="p_mobil1" id="pmobil1" maxlength="4" title="휴대폰 중간자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_handphone,'-')[1]}" />">
			                                                            -
			                                                            <input type="text" class="phone3" name="p_mobil2" id="pmobil2" maxlength="4" title="휴대폰 끝자리" value="<c:out value="${fn:split(ASP_Edit_Login.d_handphone,'-')[2]}" />">
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th><span>*</span>이메일</th>
			                                                        <td>
			                                                        	<c:set var="email1" value="${fn:split(ASP_Edit_Login.d_email,'@')[0]}" /> 
			                                                        	<c:set var="email2" value="${fn:split(ASP_Edit_Login.d_email,'@')[1]}" />
			                                                            <c:choose>
			                                                            	<c:when test="${email1 ne '' || email1 ne null || email2 ne '' || email2 ne null}">
			                                                            		<input type="text" class="email1" name="p_email1" id="p_email1" value="${email1 }" title="이메일  아이디">
			                                                            		@
			                                                            		<input type="text" class="email2" name="p_email2" id="p_email2" value="${email2 }" title="이메일  주소">
			                                                            	</c:when>
			                                                            	<c:otherwise>
			                                                            		이메일 등록을 원할시 운영자에게 연락바랍니다.(02-2161-0077)
			                                                            	</c:otherwise>
			                                                            </c:choose>
			                                                        </td>
			                                                    </tr>
			                                                    <tr>
			                                                        <th>정보수신 여부</th>
			                                                        <td>
			                                                        	<input type="hidden" name="p_agreed" id="p_agreed" title="정보수신 여부 전송값" />
			                                                            <input type="checkbox" name="p_agreed_Yn" id="p_agreed_Yn" title="정보수신 여부 (서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.)" />
			                                                            <label for="p_agreed_Yn">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</label>
			                                                        </td>
			                                                    </tr>
	                                                    		<tr>
		                                                    		<th>
			                                                        	소속부서
			                                                        </th>
			                                                        <td><input type="text" style="width:100%" name="p_deptnm" id="p_deptnm" title="소속부서" value="<c:out value="${ASP_Edit_Login.d_deptnam }"/>"></td>
		                                                       	</tr>
	                                                    	</c:otherwise>
                                                    	</c:choose>
	                                                </tbody>
	                                            </table>
	                                        </form>
                                            <div class="agree_btn_box">
                                                <a href="javascript:history.go(-1);" class="btn_cancel btn btn-outline-secondary" id="btn_cancel" title="취소">취소</a>
                                                <a href="javascript:void(0);" class="btn_next btn btn-purple" id="btn_next" title="변경">변경</a>
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

<style>
	.info_box table td{
		border:none;
		background-color: transparent;
	}

	@media all and (max-width: 640px){
		.sub_info_body{margin-top: 0;}
	}

</style>