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
				msg = "����Ҽ� ���� ��й�ȣ �Դϴ�.";
				$("#chkMsg").css("color", "red");
				
			}else if(/(\w)\1\1/.test(newpw)){
				msg = "����Ҽ� ���� ��й�ȣ �Դϴ�.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.search(id) > -1){
				msg = "����Ҽ� ���� ��й�ȣ �Դϴ�.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.search(/\s/) != -1){
				msg = "����Ҽ� ���� ��й�ȣ �Դϴ�.";
				$("#chkMsg").css("color", "red");
				
			}else if(newpw.length > 16 ){
				msg = "����Ҽ� ���� ��й�ȣ �Դϴ�.";
				$("#chkMsg").css("color", "red");
				
			}else{
				msg = "����Ҽ� �ִ� ��й�ȣ �Դϴ�.";
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
			/* �׸� ���� ���� */
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
					alert("ȸ����� �Է��Ͽ� �ֽʽÿ�.");
					$(".companyName").focus();
					return;
				}
			}

			if(($(":radio[name='p_cate']:checked").length < 1) && $(":radio[name='p_cate']").length > 0) {
				alert("�о߸� �������ּ���.");
				$("#p_cate_1").focus();
				return;
			}

			if((!$("#p_comp_addr > option:selected").val()) && $("#p_comp_addr").length > 0) {
				alert("����� �������� �������ּ���.");
				$("#p_comp_addr").focus();
				return;
			}
			
			if($("input:text[name='p_kor_name']").val() == ""){
				alert("�̸��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name=p_kor_name]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_year']").val() == "" || $("input:text[name='p_birth_year']").val().length < 4){
				alert("������� �⵵ 4�ڸ��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name=p_birth_year]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_month']").val() == ""){
				alert("������� ���� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name=p_birth_month]").focus();
				return;
			}
			
			if($("input:text[name='p_birth_day']").val() == ""){
				alert("������� ���� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_birth_day']").focus();
				return;
			}
			
			
			if(!$("input:radio[name='p_sex']").is(':checked')){
				alert("������ �����Ͻʽÿ�.");
				$("input:radio[name='p_sex']").focus();
				return;
			}
			
			if($("input:text[name='p_id']").val() == ""){
				alert("���̵� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_id']").focus();
				return;
			}
			
			if(!idExists){
				alert("���̵� �ߺ�Ȯ���� �Ͻñ� �ٶ��ϴ�.");
				$("input:text[name='p_id']").focus();
	            return;
			}
			
			if($("input:password[name='p_pw1']").val() == ""){
				alert("��й�ȣ�� �Է��Ͽ� �ֽʽÿ�.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("input:password[name='p_pw1']").val().length < 8){
				alert("��й�ȣ�� 8�ڸ� �̻��̾�� �մϴ�.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("input:password[name='p_pw2']").val() == ""){
				alert("��й�ȣ Ȯ���� �Է��Ͽ� �ֽʽÿ�.");
				$("input:password[name='p_pw2']").focus();
				return;
			}
			
			if($("input:password[name='p_pw2']").val().length < 8){
				alert("��й�ȣ Ȯ���� 8�ڸ� �̻��̾�� �մϴ�.");
				$("input:password[name='p_pw2']").focus();
				return;
			}
			
			if($("input:password[name='p_pw1']").val() != $("input:password[name='p_pw2']").val()){
				alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				$("input:password[name='p_pw1']").focus();
				return;
			}
			
			if($("select[name='p_tel0']").val() == ""){
				alert("��ȭ��ȣ ���ڸ��� �����Ͽ� �ֽʽÿ�.");
				$("select[name='p_tel0']").focus();
				return;
			}
			
			if($("input:text[name='p_tel1']").val() == ""){
				alert("��ȭ��ȣ �߰��ڸ��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_tel1']").focus();
				return;
			}
			
			if($("input:text[name='p_tel2']").val() == ""){
				alert("��ȭ��ȣ ���ڸ��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_tel2']").focus();
				return;
			}
			
			if($("select[name='p_mobil0']").val() == ""){
				alert("�޴��� ���ڸ��� �����Ͽ� �ֽʽÿ�.");
				$("select[name='p_mobil0']").focus();
				return;
			}
			
			if($("input:text[name='p_mobil1']").val() == ""){
				alert("�޴��� �߰��ڸ��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_mobil1']").focus();
				return;
			}
			
			if($("input:text[name='p_mobil2']").val() == ""){
				alert("�޴��� ���ڸ��� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_mobil2']").focus();
				return;
			}
			
			if($("input:text[name='p_email1']").val() == ""){
				alert("�̸��� ���̵� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_email1']").focus();
				return;
			}
			
			if($("input:text[name='p_email2']").val() == ""){
				alert("�̸���  �ּҸ� �Է��Ͽ� �ֽʽÿ�.");
				$("input:text[name='p_email2']").focus();
				return;
			}
			
			if(!emailExists){
	            alert("�̸��� �ߺ�Ȯ���� �Ͻñ� �ٶ��ϴ�.");
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
			alert("���̵� �Է��Ͽ� �ֽʽÿ�.");
			$("input:text[name='p_id']").focus();
			return;
		}
		
		if(id.length < 6 || id.length > 30){
			alert("���̵�� �� 6~30�ڷθ� ������ �Ǿ�� �մϴ�.");
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
					$("#id_alter_area").text("����Ҽ� �ִ� ���̵� �Դϴ�.");
					alert("����Ҽ� �ִ� ���̵� �Դϴ�.");
					return;
				}else{
					idExists = false;
					$("#id_alter_area").css("color","#FF0000");
					$("#id_alter_area").text("����Ҽ� ���� ���̵� �Դϴ�.");
					alert("����Ҽ� ���� ���̵� �Դϴ�.");
					return;
				}
			}
		});
	}
	
	function existsEmail(){
		var email1 = $.trim($("input:text[name='p_email1']").val());
		var email2 = $.trim($("input:text[name='p_email2']").val());
		if(email1 == "" || email2 == ""){
			alert("�̸����� �Է��Ͽ� �ֽʽÿ�.");
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
					$("#email_alter_area").text("����Ҽ� �ִ� �̸��� �Դϴ�.");
					alert("����Ҽ� �ִ� �̸��� �Դϴ�.");
					return;
				}else{
					emailExists = false;
					$("#email_alter_area").css("color","#FF0000");
					$("#email_alter_area").text("����Ҽ� ���� �̸��� �Դϴ�.");
					alert("����Ҽ� ���� �̸��� �Դϴ�.");
					return;
				}
			}
		});
	}

	function fnclean() {
		$("#upload01").val("");
		$("#fnm").val("");
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
                                <h1>ȸ������</h1>
                            </div>
                            <div class="sub_contents_body">
                                <div class="sub_info_body join_box">
									<div class="signUp_step_bar">
										<div class="step_wrap">
											<p>
												<i class="bi bi-check-circle"></i>
												�������
											</p>
										</div>

										<div class="step_wrap active">
											<p>
												<i class="bi bi-check-circle-fill"></i>
												�����Է�
											</p>
										</div>

										<div class="step_wrap">
											<p>
												<i class="bi bi-check-circle"></i>
												���ԿϷ�
											</p>
										</div>
									</div>

                                    <h3>ȸ������</h3>
                                    <div class="sub_course_view_wrap">
                                        <div class="info_box">
                                        	<form name="form_join" id="form_join" method="POST" enctype="multipart/form-data">
                                        		<input type="hidden" id="p_process" name="p_process" value="" />
                                        		<input type="hidden" id="menuid" name="menuid" value="" />
                                        		<input type="hidden" id="gubun" name="gubun" value="" />
	                                            <ul class="">
		                                                <c:choose>
	                                                    	<c:when test="${sessionScope.tem_grcode eq 'N000210'}">
																<h4 style="color: red;">�� �����ȳ�, ������ �߱� ���� ���� ��Ȯ�� ������ �Է����ּ���.</h4>
	                                                    		<li>
		                                                    		<p><span>*</span>ȸ���</p>
			                                                        <div>
			                                                        	<input type="text" style="width:100%" name="p_deptnm" id="p_deptnm" title="ȸ���" class="companyName">
																		<span style="width: 100%;">�� ����ڰ� ���� â���� �� �Ҽ��� ���� ���, ���ҼӾ����� �� üũ���ּ���.</span>
																		<span>
																		<input type="checkbox" name="p_notcompchk" id="p_notcompchk" value="Y" title="�ҼӾ���">
																		<label for="p_notcompchk">�ҼӾ���</label>
																		</span>
			                                                        </div>
			                                                    </li>
																<li>
																	<p><span>*</span>�о�</p>
																	<div>
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
																		<input type="text" name="p_cate_txt" id="p_cate_txt" title="��Ÿ" disabled>
																	</div>
																</li>
																<li>
																	<p><span>*</span>����� ������</p>
																	<div>
																		<label for="p_comp_location"></label>
																		<select class="email3" name="p_comp_location" id="p_comp_location" title="����� ������ ����">
																			<option value="" title="����">����</option>
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
																	</div>
																</li>
		                                                        <li>
			                                                        <p><span>*</span>����(�ѱ�)</p>
			                                                        <div><input type="text" name="p_kor_name" id="p_kor_name" title="����(�ѱ�)" ></div>
			                                                    </li>
			                                                    <li class="birthform_box">
			                                                        <p><span>*</span>�������(�� 1972�� 01�� 01��)</p>
			                                                        <div>
			                                                            <input type="text" name="p_birth_year" id="p_birth_year" maxlength="4" class="year" title="������� (�⵵)"><span>��</span>
																		<input type="text" name="p_birth_month" id="p_birth_month" maxlength="2" class="month" title="������� (��)"><span>��</span>
			                                                            <input type="text" name="p_birth_day" id="p_birth_day" maxlength="2" class="day" title="������� (��)"><span>��</span>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>����</p>
			                                                        <div>
			                                                            <input type="radio" name="p_sex" value="2" id="gender2" title="����">
			                                                            <label for="gender2">����</label>
			                                                            <input type="radio" name="p_sex" value="1" id="gender1" title="����">
			                                                            <label for="gender1">����</label>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>���̵�</p>
			                                                        <div>
			                                                            <input type="text" name="p_id" id="p_id" maxlength="16" title="���̵� 6~16���� ����, ���� ����" placeholder="���̵�� 6~30���� �ҹ��� ����, ������ �������� ���� �� �ֽ��ϴ�." />
			                                                            <a href="javascript:existsId();" class="btn btn-purple ml10">�ߺ�Ȯ��</a>
			                                                            <p id="id_alter_area" style="font-size:12px;"></p>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>��й�ȣ</p>
			                                                        <div>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="��й�ȣ (8�� �̻��� ���� Ȥ�� �������� �Է����ּ���. ���ӵ� ����, ����, �ֹι�ȣ �� �˱⽬�� ���ڴ� �����ּ���.)" />
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <p class="sm_text point_sgray mt10">
																			* ��й�ȣ�� 8 ~ 16�� �̾�� �ϸ�, ����/�빮��/�ҹ���/Ư�����ڸ� ��� �����ؾ� �մϴ�.<br>
																			* ID�� ������ ��й�ȣ, ���Ϲ���, ���ӹ��� ���� ����Ͻ� �� �����ϴ�.
																		</p>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>��й�ȣ Ȯ��</p>
			                                                        <div>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="��й�ȣ Ȯ�� (��й�ȣ�� �����ϰ� �Է����ּ���.)">
			                                                        </div>
			                                                    </li>
			                                                    <li class="number_box">
			                                                        <p><span>*</span>��ȭ��ȣ</p>
			                                                        <div><label for="p_tel0"></label>
			                                                            <select class="phone1 text-center" name="p_tel0" id="p_tel0" title="��ȭ��ȣ ���ڸ� ����">
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
			                                                            <span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone2 text-center" name="p_tel1" id="p_tel1" maxlength="4" title="��ȭ��ȣ �߰��ڸ� �Է�">
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>

			                                                            <input type="text" class="phone3 text-center" name="p_tel2" id="p_tel2" maxlength="4" title="��ȭ��ȣ ���ڸ� �Է�">
			                                                        </div>
			                                                    </li>
			                                                    <li class="number_box">
			                                                        <p><span>*</span>�޴���</p>
			                                                        <div><label for="p_mobil0"></label>
			                                                            <select class="phone1 text-center" name="p_mobil0" id="p_mobil0" title="�޴��� ���ڸ� ����">
			                                                                <option value="010" title="010">010</option>
			                                                                <option value="011" title="011">011</option>
			                                                                <option value="016" title="016">016</option>
			                                                                <option value="017" title="017">017</option>
			                                                                <option value="018" title="018">018</option>
			                                                                <option value="019" title="019">019</option>
			                                                            </select>
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone2 text-center" name="p_mobil1" id="p_mobil1" maxlength="4" title="�޴��� �߰��ڸ� �Է�"><span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone3 text-center" name="p_mobil2" id="p_mobil2" maxlength="4" title="�޴��� ���ڸ� �Է�">
			                                                        </div>
			                                                    </li>
			                                                    <li class="email_box">
			                                                        <p><span>*</span>�̸���</p>
			                                                        <div>
			                                                            <input type="text" class="email1" name="p_email1" id="p_email1" title="�̸���  ���̵�">@
			                                                            <input type="text" class="email2" name="p_email2" id="p_email2" title="�̸���  �ּ�">
			                                                            <label for="p_email20"></label>
			                                                            <select class="email3" name="p_email20" id="p_email20" title="�̸���  �ּ� ����">
			                                                                <option value="" title="�����Է�">�����Է�</option>
			                                                                <option value="naver.com" title="naver.com">naver.com</option>
			                                                                <option value="gmail.com" title="gmail.com">gmail.com</option>
			                                                                <option value="hanmail.net" title="hanmail.net">hanmail.net</option>
			                                                                <option value="nate.com" title="nate.com">nate.com</option>
			                                                            </select>
			                                                            <a href="javascript:existsEmail();" class="btn btn-purple mb5" title="�̸��� �ߺ�Ȯ��">�ߺ�Ȯ��</a>
			                                                            <span id="email_alter_area" style="font-size: 12px;"></span>
			                                                            <span class="sm_text point_sgray mt10">* �ѱ���������������� �����ϴ� ������ ��� ȸ������ ���������� �ʿ��� ������ �ַ� ����Ͻô� �̸��� �ּҸ� �Է��� �ּ���.</span>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p>�������� ����</p>
			                                                        <div class="d-flex align-items-center">
			                                                            <input type="checkbox" name="p_agreed" id="p_agreed" value="Y" title="�������� ���� (���� �� �̺�Ʈ �ҽ��� E-Mail Ȥ�� SMS���� ���� �ϰڽ��ϴ�.)">
			                                                            <label for="p_agreed" style="padding-left: 10px; width: 93%;">���� �� �̺�Ʈ �ҽ��� E-Mail Ȥ�� SMS���� ���� �ϰڽ��ϴ�.</label>
			                                                        </div>
			                                                    </li>
	                                                    	</c:when>
	                                                    	<c:otherwise>
	                                                    		<li>
			                                                        <p><span>*</span>����(�ѱ�)</p>
			                                                        <div><input type="text" name="p_kor_name" id="p_kor_name" title="����(�ѱ�)"></div>
			                                                    </li>
			                                                    <li class="birthform_box">
			                                                        <p><span>*</span>�������(�� 1972�� 01�� 01��)</p>
			                                                        <div>
			                                                            <input type="text" name="p_birth_year" id="p_birth_year" maxlength="4" class="year" title="������� (�⵵)"><span>��</span>
			                                                            <input type="text" name="p_birth_month" id="p_birth_month" maxlength="2" class="month" title="������� (��)"><span>��</span>
			                                                            <input type="text" name="p_birth_day" id="p_birth_day" maxlength="2" class="day" title="������� (��)"><span>��</span>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>����</p>
			                                                        <div>
			                                                            <input type="radio" name="p_sex" value="2" id="gender2" title="����">
			                                                            <label for="gender2">����</label>
			                                                            <input type="radio" name="p_sex" value="1" id="gender1" title="����">
			                                                            <label for="gender1">����</label>
			                                                        </div>
																	<c:if test="${sessionScope.tem_grcode eq 'N000241'}">
																		<span class="sm_text point_sgray mt10">
																			�� ������ ���� ��ü���� �����ϴ� ������ �ƴϸ�,<br />
																			&nbsp;&nbsp;&nbsp;&nbsp;�α�������� �м��� ���� �����ڷ�� Ȱ����� �ȳ� �帳�ϴ�.<br />
																			&nbsp;&nbsp;&nbsp;&nbsp;������� �м��� ���ѵ� �������� �������� ���� ��Ź�帳�ϴ�.
																		</span>
																	</c:if>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>���̵�</p>
			                                                        <div class="join_input_box">
			                                                            <input type="text" name="p_id" id="p_id" maxlength="16" title="���̵� 6~16���� ����, ���� ����" placeholder="���̵�� 6~30���� ����, ������ �������� ���� �� �ֽ��ϴ�." />
			                                                            <a href="javascript:existsId();" class="btn btn-purple ml10">�ߺ�Ȯ��</a>
			                                                            <span id="id_alter_area" style="font-size: 12px;"></span>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>��й�ȣ</p>
			                                                        <div>
			                                                            <input type="password" name="p_pw1" id="p_pw1" title="��й�ȣ (8�� �̻��� ���� Ȥ�� �������� �Է����ּ���. ���ӵ� ����, ����, �ֹι�ȣ �� �˱⽬�� ���ڴ� �����ּ���.)" />
			                                                            <div id="chkMsg" style="font-size:12px;"></div>
					                                                    <span class="sm_text point_sgray mt10">
																			* ��й�ȣ�� 8 ~ 16�� �̾�� �ϸ�, ����/�빮��/�ҹ���/Ư�����ڸ� ��� �����ؾ� �մϴ�.<br>
																			* ID�� ������ ��й�ȣ, ���Ϲ���, ���ӹ��� ���� ����Ͻ� �� �����ϴ�.
																		</span>

			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p><span>*</span>��й�ȣ Ȯ��</p>
			                                                        <div>
			                                                            <input type="password" name="p_pw2" id="p_pw2" title="��й�ȣ Ȯ�� (��й�ȣ�� �����ϰ� �Է����ּ���.)">
			                                                        </div>
			                                                    </li>
																<li>
																	<p>������ ����</p>
																	<div class="input-file">
																		<input type="text" id="fnm" readonly="readonly" class="file-name" disabled/>
																		<label for="upload01" class="btn btn-purple">���� ���ε�</label>
																		<a href="javascript:fnclean();" class="btn btn-purple ml10">�ʱ�ȭ</a>
																		<input type="file" name="p_file" id="upload01" class="file-upload" accept="image/gif, image/jpeg, image/png"/>
																		<span class="sm_text point_sgray mt10">* ������ ���� �̵�� �� �������� ǥ��˴ϴ�.</span>
																	</div>
																</li>
			                                                    <li class="number_box">
			                                                        <p><span>*</span>��ȭ��ȣ</p>
			                                                        <div><label for="p_tel0"></label>
			                                                            <select class="phone1 text-center" name="p_tel0" id="p_tel0" title="��ȭ��ȣ ���ڸ� ����">
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
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone2 text-center" name="p_tel1" id="p_tel1" maxlength="4" title="��ȭ��ȣ �߰��ڸ� �Է�">
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone3 text-center" name="p_tel2" id="p_tel2" maxlength="4" title="��ȭ��ȣ ���ڸ� �Է�">
			                                                        </div>
			                                                    </li>
			                                                    <li class="number_box">
			                                                        <p><span>*</span>�޴���</p>
			                                                        <div><label for="p_mobil0"></label>
			                                                            <select class="phone1 text-center" name="p_mobil0" id="p_mobil0" title="�޴��� ���ڸ� ����">
			                                                                <option value="010" title="010">010</option>
			                                                                <option value="011" title="011">011</option>
			                                                                <option value="016" title="016">016</option>
			                                                                <option value="017" title="017">017</option>
			                                                                <option value="018" title="018">018</option>
			                                                                <option value="019" title="019">019</option>
			                                                            </select>
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone2 text-center" name="p_mobil1" id="p_mobil1" maxlength="4" title="�޴��� �߰��ڸ� �Է�">
																		<span class="d-inline-block" style="margin: 0 8px;">-</span>
			                                                            <input type="text" class="phone3 text-center" name="p_mobil2" id="p_mobil2" maxlength="4" title="�޴��� ���ڸ� �Է�">
			                                                        </div>
			                                                    </li>
			                                                    <li class="email_box">
			                                                        <p><span>*</span>�̸���</p>
			                                                        <div>
			                                                            <input type="text" class="email1" name="p_email1" id="p_email1" title="�̸���  ���̵�">@
			                                                            <input type="text" class="email2" name="p_email2" id="p_email2" title="�̸���  �ּ�">
			                                                            <label for="p_email20"></label>
			                                                            <select class="email3 mr10" name="p_email20" id="p_email20" title="�̸���  �ּ� ����">
			                                                                <option value="" title="�����Է�">�����Է�</option>
			                                                                <option value="naver.com" title="naver.com">naver.com</option>
			                                                                <option value="gmail.com" title="gmail.com">gmail.com</option>
			                                                                <option value="hanmail.net" title="hanmail.net">hanmail.net</option>
			                                                                <option value="nate.com" title="nate.com">nate.com</option>
			                                                            </select>
			                                                            <a href="javascript:existsEmail();" class="btn btn-purple mb5" title="�̸��� �ߺ�Ȯ��">�ߺ�Ȯ��</a>
			                                                            <span id="email_alter_area" style="font-size: 12px;"></span>
			                                                            <span class="sm_text point_sgray mt10">* �ѱ���������������� �����ϴ� ������ ��� ȸ������ ���������� �ʿ��� ������ �ַ� ����Ͻô� �̸��� �ּҸ� �Է��� �ּ���.</span>
			                                                        </div>
			                                                    </li>
			                                                    <li>
			                                                        <p>�������� ����</p>
			                                                        <div class="d-flex align-items-center">
			                                                            <input type="checkbox" name="p_agreed" id="p_agreed" value="Y" title="�������� ���� (���� �� �̺�Ʈ �ҽ��� E-Mail Ȥ�� SMS���� ���� �ϰڽ��ϴ�.)">
			                                                            <label for="p_agreed" style="padding-left: 10px; width: 93%;">���� �� �̺�Ʈ �ҽ��� E-Mail Ȥ�� SMS���� ���� �ϰڽ��ϴ�.</label>
			                                                        </div>
			                                                    </li>
		                                                    	<li>
		                                                    		<p>
			                                                        	�ҼӺμ�
			                                                        </p>
			                                                        <div><input type="text" style="width:100%" name="p_deptnm" id="p_deptnm" title="�ҼӺμ�"></div>
			                                                    </li>
	                                                    	</c:otherwise>
	                                                   	</c:choose>
                                                   	</tbody>
	                                            </ul>
                                            </form>
                                            <div class="agree_btn_box">
                                                <a href="javascript:void(0);" title="���" class="btn btn-outline-secondary btn-lg btn_cancel" id="btn_cancel">���</a>
                                                <a href="javascript:void(0);" title="����" class="btn btn-purple btn_next" id="btn_next">����</a>
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
	.sub_info_body ul li:last-child{
		border-bottom: none;
	}
	.input-file [type="text"]{
		width: 70%;
	}
</style>