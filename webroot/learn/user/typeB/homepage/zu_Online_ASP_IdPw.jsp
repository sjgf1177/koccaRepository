<%@ page import="com.credu.library.RequestBox"%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<script type="text/javascript">

	$(document).ready(function(){
		$(document).on("click", "#btn_find_id", function(){
			if($.trim($("input:text[name='p_email_id']").val()) == ""){
				alert("이메일을 입력 하여 주십시오.");
				$("input:text[name='p_email_id']").focus();
				return;
			}
			ajaxFind('40', $("input:text[name='p_email_id']").val());
		});
		
		$(document).on("click", "#btn_find_pw", function(){
			
			if($.trim($("input:text[name='p_email_pw']").val()) == ""){
				alert("이메일을 입력 하여 주십시오.");
				$("input:text[name='p_email_pw']").focus();
				return;
			}
			console.log($("input:text[name='p_email_pw']").val());
			ajaxFind('50', $("input:text[name='p_email_pw']").val());
		});
	});
	
	function ajaxFind(gubun, email){
        alert("aaa : " + gubun + " // " + email)
		$.ajax({
			url : '/servlet/controller.homepage.MainServlet',
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			type : 'post',
			data : {p_email:email, p_process:"ASP_Find_Idpw", menuid:"0", gubun:gubun},
			success : function(data){
				var r1 = $.trim(data);
                var r2 = r1.split(":");
				console.log(data);
                if(r1!="" && r2[0]=="true")
                	alert(r2[1]+"(으)로 메일이 발송되었습니다.");
                else if(r2[0]=="false")
                	alert("회원님의 정보가 존재하지 않습니다.");
			}        ,   	error:function(request, status, error){

                alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);

            }
		});
	}
	
</script>
<section>
    <div class="wrapper">
        <div class="container">
            <div class="row">
                <div class="col-12 mxw-640">
                    <div class="subContainer">
                        <div class="sub_section">
                            <div class="sub_contents_header">
                                <h2>아이디/패스워드 찾기</h2>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body">
                                    <p class="sub_course_view_title">아이디 찾기</p>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                            <table class="write_table">
                                                <colgroup>
                                                    <col width="20%">
                                                    <col width="auto">
                                                    <col width="20%">
                                                </colgroup>
                                                <tbody>
                                                    <tr>
                                                        <th><span>*</span>이메일</th>
                                                        <td><input type="text" name="p_email_id" id="p_email_id" title="아이디 찾기 이메일 입력 (회원가입시 작성하신 이메일주소를 입력해주세요.)" placeholder="이메일주소를 입력해주세요."></td>
                                                        <td><a href="javascript:void(0);" class="btn btn-primary w-100 ml10" id="btn_find_id">확인</a></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                 </div>
                                 
                                 <div class="sub_info_body" style="margin-top:50px;">
                                    <p class="sub_course_view_title">비밀번호 찾기</p>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                            <table class="write_table">
                                                <colgroup>
                                                    <col width="20%">
                                                    <col width="auto">
                                                    <col width="20%">
                                                </colgroup>
                                                <tbody>
                                                    <tr>
                                                        <th><span>*</span>이메일</th>
                                                        <td><input type="text" name="p_email_pw" id="p_email_pw" title="비밀번호 찾기 이메일 입력 (회원가입시 작성하신 이메일주소를 입력해주세요.)" placeholder="이메일주소를 입력해주세요."></td>
                                                        <td><a href="javascript:void(0);" class="btn btn-primary w-100 ml10" id="btn_find_pw">확인</a></td>
                                                    </tr>
                                                </tbody>
                                            </table>
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