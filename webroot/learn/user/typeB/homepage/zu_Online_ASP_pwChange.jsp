<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<script type="text/javascript">
	$(document).ready(function(){
		$("#p_pw1").on("keyup", function(){
			var newpw = $("#p_pw1").val();
			var id = "<c:out value='${param.p_id}'/>";
			
			var reg = /^.*(?=.{8,16})(?=.*[0-9!@#$%^&*()_+=<>?])(?=.*[a-zA-Z]).*$/;
			var msg = "";
			if(false === reg.test(newpw)) {
				msg = "사용할수 없는 비밀번호 입니다.1";
				$("#chkMsg").css("color", "red");
				$("#chkPassVal").val("false");
			}else if(/(\w)\1\1/.test(newpw)){
				msg = "사용할수 없는 비밀번호 입니다.2";
				$("#chkMsg").css("color", "red");
				$("#chkPassVal").val("false");
			}else if(newpw.search(id) > -1){
				msg = "사용할수 없는 비밀번호 입니다.3";
				$("#chkMsg").css("color", "red");
				$("#chkPassVal").val("false");
			}else if(newpw.search(/\s/) != -1){
				msg = "사용할수 없는 비밀번호 입니다.4";
				$("#chkMsg").css("color", "red");
				$("#chkPassVal").val("false");
			}else if(newpw.length > 16 ){
				msg = "사용할수 없는 비밀번호 입니다.5";
				$("#chkMsg").css("color", "red");
				$("#chkPassVal").val("false");
			}else{
				msg = "사용할수 있는 비밀번호 입니다.6";
				$("#chkMsg").css("color", "green");
				$("#chkPassVal").val("true");
			}
			console.log(msg);
			$("#chkMsg").text(msg);
			
		});
	});
	
	function changePw(){
		var r=$.trim($("[name='p_usepw']").val());
        var r1=$.trim($("[name='p_pw1']").val());
        var r2=$.trim($("[name='p_pw2']").val());
        
        if(r==""){ alert("현재 비밀번호를 입력하세요."); $("[name='p_usepw']").focus(); return; }
        if(r1==""){ alert("새 비밀번호를 입력하세요."); $("[name='p_pw1']").focus(); return; }
        if(r2==""){ alert("새 비밀번호 확인을 입력하세요"); $("[name='p_pw2']").focus(); return; }
        if(r1!=r2){ alert("암호가 서로 일치하지 않습니다."); $("[name='p_pw1']").val(""); $("[name='p_pw2']").val(""); $("[name='p_pw1']").focus(); return; }
		if($("#chkPassVal").val() == "true"){
			document.form_passChange.p_process.value = "ASP_Login";
	        document.form_passChange.menuid.value = "0";
	        document.form_passChange.gubun.value = "100";
	        document.form_passChange.action = "/servlet/controller.homepage.MainServlet";
	        document.form_passChange.submit();
		} else {
			alert("사용할 수 없는 비밀번호를 입력하셨습니다.");
			$("[name='p_pw1']").focus(); return;
		}
		
	}
	
	function nextChangePw(){
		document.form_passChange.target="_self";
	    document.form_passChange.action="/servlet/controller.homepage.MainServlet";
	    document.form_passChange.p_process.value="nextChange";
	    document.form_passChange.submit();
	}
</script>

<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                            	<span>비밀번호 변경</span>
                            </div>
                            <div class="sub_contents_body">
								<div class="sub_info_body join_box">
                                	<div class="list_title" style="font-size:16px;padding:0 20px; text-align: center;">
                                        <p style="font-weight:600; ">"회원님의 소중한 개인정보보호를 위해 비밀번호를 변경해주세요"</p>
                                        <p>한국콘텐츠아카데미에서는 회원님의 개인정보보호를 위해 3개월 이상 비밀번호를 변경하지 않은 경우 비밀번호 변경을 안내하고 있습니다.</p>
                                        <p>변경 시, 타인이 추측하기 쉬운 비밀번호는 도용위험성이 높으니, 영어/숫자/특수문자를 조합하여 안전하게 설정해주시기 바랍니다.</p>
                                    </div>
                                    <div class="sub_course_view_wrap">
                                    	<div class="info_box">
                                    		<form name="form_passChange" id="form_passChange" method="POST">
                                    			<input type="hidden" name="chkPassVal" id="chkPassVal" value="" />
                                    			<input type="hidden" name="p_process" value="" />
                                        		<input type="hidden" name="menuid" value="" />
                                        		<input type="hidden" name="gubun" value="" />
                                    			<table class="write_table th_align_left1">
	                                                <colgroup>
	                                                    <col width="30%">
	                                                    <col width="auto">
	                                                </colgroup>
	                                                <tbody>
	                                                	<tr>
	                                                		<th>현재 비밀번호</th>
	                                                		<td>
	                                                			<input type="password" name="p_usepw" id="p_usepw" title="현재 비밀번호" />
	                                                		</td>
	                                                	</tr>
	                                                	<tr>
	                                                		<th>새 비밀번호</th>
	                                                		<td>
	                                                			<input type="password" name="p_pw1" id="p_pw1" title="새로운 비밀번호 (8자 이상의 숫자 혹은 영문으로 입력해주세요. 연속된 숫자, 생일, 주민번호 등 알기쉬운 숫자는 피해주세요.)">
	                                                			<div id="chkMsg" style="font-size:12px;"></div>
			                                                    <span>* 비밀번호는 8 ~ 16자 이어야 하며, 숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.</span>
			                                                    <span>* ID와 동일한 비밀번호, 동일문자, 연속문자 등은 사용하실 수 없습니다.</span>
			                                                </td>
	                                                	</tr>
	                                                	<tr>
	                                                		<th>새 비밀번호 확인</th>
	                                                		<td><input type="password" name="p_pw2" id="p_pw2" title="새로운 비밀번호 확인"></td>
	                                                	</tr>
	                                                </tbody>
												</table>

                                    		</form>
                                    	</div>
                                    	<div style="padding-top:20px; text-align: center;">
											<button onclick="changePw();" class="btn_small btn btn-purple">비밀번호 변경</button>
											<button onclick="nextChangePw();" class="btn_small btn btn-outline-secondary">다음에 변경하기</button>
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
	.btn_small{padding:10px; border:1px solid #eeeeee; font-size:14px;margin:5px;}
	.info_box table td{background-color: transparent;}
</style>