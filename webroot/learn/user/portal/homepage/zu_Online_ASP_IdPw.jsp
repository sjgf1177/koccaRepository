<%@ page import="com.credu.library.RequestBox"%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<script type="text/javascript">

    function findId()
    {
        var id=$("[name='p_name']").val();
        if(id=="")
        {
            alert("이름을 입력 하여 주십시요.");
            $("[name='p_name']");
            return;
        }

        var resno=$("[name='p_resno1']").val()+$("[name='p_resno2']").val();
        if(!regcheck(resno))
        {
            $("[name='p_resno1']").val("");
            $("[name='p_resno2']").val("");
            $("[name='p_resno1']").focus();
            return;
        }
        if(resno=="")
        {
            alert("주민등록번호를 입력 하여 주십시요.");
            $("[name='p_resno1']");
            return;
        }
        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "40";

        $("[name='form1']").ajaxSubmit({
           url:'/servlet/controller.homepage.MainServlet',
          contentType : "application/x-www-form-urlencoded;charset=UTF-8",
           type:'post',
           data:{p_resno:resno,p_name:id},
           success: function(data){
               var r1=$.trim(data);
               $("#div_Area").html("");
               $("#div_Area").css("vertical-align","middle");
               $("#div_Area").attr("align","center");
               if(r1!="")
                $("#div_Area").html("회원님의 아이디는 <b>"+r1+"</b> 입니다.");
               else
                $("#div_Area").html("회원님의 정보가 존재하지 않습니다.");
           }
        });
    }

    function findPw()
    {
        var name=$("[name='p_name10']").val();
        if(name=="")
        {
            alert("이름을 입력 하여 주십시요.");
            $("[name='p_name10']");
            return;
        }

        var id=$("[name='p_id10']").val();
        if(id=="")
        {
            alert("이름을 입력 하여 주십시요.");
            $("[name='p_id10']");
            return;
        }

        var resno=$("[name='p_resno10']").val()+$("[name='p_resno11']").val();
        if(!regcheck(resno))
        {
            $("[name='p_resno10']").val("");
            $("[name='p_resno11']").val("");
            $("[name='p_resno10']").focus();
            return;
        }

        if(resno=="")
        {
            alert("주민등록번호를 입력 하여 주십시요.");
            $("[name='p_resno10']");
            return;
        }
        document.form1.p_process.value = "ASP_Login";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "50";

        $("[name='form1']").ajaxSubmit({
           url:'/servlet/controller.homepage.MainServlet',
          contentType : "application/x-www-form-urlencoded;charset=UTF-8",
           type:'post',
           data:{p_name:name,p_id:id},
           success: function(data){
               var r1=$.trim(data);
               $("#div_AreaPw").html("");
               $("#div_AreaPw").css("vertical-align","middle");
               $("#div_AreaPw").attr("align","center");
               if(r1!="")
                $("#div_AreaPw").html("회원님의 임시비밀번호는 <b>"+r1+"</b> 입니다.");
               else
                $("#div_AreaPw").html("회원님의 정보가 존재하지 않습니다.");
           }
        });
    }


    /**
     * ASP 사용자 아이디 찾기
     */
    function efindId(){
        var email = $("[name='p_email']").val();
        if(email == "") {
            alert("이메일을 입력 하여 주십시요.");
            $("[name='p_email']").focus();
            return;
        }

        document.form1.p_process.value = "ASP_Find_Idpw";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "40";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
            contentType : "application/x-www-form-urlencoded;charset=UTF-8",
            type:'post',
            data:{p_email:email},
            success: function(data){
                var r1 = $.trim(data);
                var r2 = r1.split(":");
                $("#div_Area").html("");
                $("#div_Area").css("vertical-align","middle");
                $("#div_Area").attr("align","center");

                if(r1!="" && r2[0]=="true")
                 $("#div_Area").html(" <b>"+r2[1]+"</b>(으)로 메일이 발송되었습니다.");
                else if(r2[0]=="false")
                 $("#div_Area").html("회원님의 정보가 존재하지 않습니다.");
            }
        });
    }

function efindPw(){
        var email=$("[name='p_email_pw']").val();
        if(email=="")
        {
            alert("이메일을 입력 하여 주십시요.");
            $("[name='p_email']");
            return;
        }

        document.form1.p_process.value = "ASP_Find_Idpw";
        document.form1.menuid.value = "0";
        document.form1.gubun.value = "50";

        $("[name='form1']").ajaxSubmit({
            url:'/servlet/controller.homepage.MainServlet',
           contentType : "application/x-www-form-urlencoded;charset=UTF-8",
            type:'post',
            data:{p_email_pw:email},
            success: function(data){
                var r1=$.trim(data);
                var r2 = r1.split(":");
                $("#div_AreaPw").html("");
                $("#div_AreaPw").css("vertical-align","middle");
                $("#div_AreaPw").attr("align","center");

                if(r1!="" && r2[0]=="true")
                 $("#div_AreaPw").html(" <b>"+r2[1]+"</b>(으)로 메일이 발송되었습니다.");
                else if(r2[0]=="false")
                 $("#div_AreaPw").html("회원님의 정보가 존재하지 않습니다.");
            }
         });
    }
</script>
<table width="690px" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
			<!--탑이미지영역 시작--->
			<%@ include file="/learn/user/portal/include/topMembership.jsp" %>  
		</td>
	</tr>
	<tr>
		<td>
			<!--메인영역--->
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="20">&nbsp;</td>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="230" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td><img src="/images/asp/membership/left_title.gif" width="230" height="38" /></td>
										</tr>
										<tr>
											<td><a href="javascript:mainmenu('1')" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image31','','/images/asp/membership/leftm1_o.gif',1)"><img src="/images/asp/membership/leftm1_a.gif" name="Image31" width="230" height="41" border="0" id="Image31" /></a></td>
										</tr>
										<tr>
											<td><img src="/images/asp/membership/leftm2_o.gif" width="230" /></td>
										</tr>
									</table>
								</td>
								<td valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="38" align="left" valign="top"><img src="/images/asp/membership/title_2.gif" width="203" height="31" /></td>
										</tr>
										<tr>
											<td height="35" align="left"><img src="/images/asp/membership/joinimg_08.gif" alt="회원가입시 작성하신 이메일주소를 입력해주세요." width="240" height="20" /></td>
										</tr>
										<tr>
											<td><img src="/images/asp/membership/stitle_6.gif" width="690" height="24" /></td>
										</tr>
										<tr>
											<td><br /></td>
										</tr>
										<tr>
											<td align="left">
												<table width="482" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="116" valign="top" background="/images/asp/membership/joinimg_07.gif" id="div_Area">
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td height="30">&nbsp;</td>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
																</tr>
																<tr>
																	<td>&nbsp;</td>
																	<td width="350">
																		<table border="0" cellspacing="5" cellpadding="0">
																			<tr>
																				<td align="right">이메일</td>
																				<td align="left"><input name="p_email" type="text" class="inp_txt_01" size="40" /></td>
																			</tr>
																		</table></td>
																	<td width="73"><a href="javascript:efindId();"><img src="/images/asp/membership/btn_ok.gif" alt="확인" width="73" height="49" /></a></td>
																	<td>&nbsp;</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td><img src="/images/asp/membership/stitle_7.gif" alt="비밀번호찾기" width="690" height="24" /></td>
										</tr>
										<tr>
											<td><br /></td>
										</tr>

										<tr>
											<td align="left">
												<table width="482" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<td height="116" valign="top" background="/images/asp/membership/joinimg_07.gif" id="div_AreaPw">
															<table width="100%" border="0" cellspacing="0" cellpadding="0">
																<tr>
																	<td height="30">&nbsp;</td>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
																	<td>&nbsp;</td>
																</tr>
																<tr>
																	<td>&nbsp;</td>
																	<td width="350">
																		<table border="0" cellspacing="5" cellpadding="0">
																			<tr>
																				<td align="right">이메일</td>
																				<td align="left"><input name="p_email_pw" type="text" class="inp_txt_01" size="40" /></td>
																			</tr>
																		</table>
																	</td>
																	<td width="73"><a href="javascript:efindPw();"><img src="/images/asp/membership/btn_ok.gif" alt="확인" width="73" height="49" /></a></td>
																	<td>&nbsp;</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="50">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td width="20">&nbsp;</td>
				</tr>
			</table>

		</td>
	</tr>
</table>
