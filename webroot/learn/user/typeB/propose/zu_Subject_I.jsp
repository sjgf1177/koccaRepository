<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>한국콘텐츠아카데미 수강신청</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <!--<link href="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" rel="stylesheet">-->
    <!--<link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">-->
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <!--<script src="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
    <!--<script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>-->

	<!-- Bootstrap-5.3.1 -->
	<link href="/common/js/bootstrap-5.3.1-dist/css/bootstrap.css" rel="stylesheet">
	<script src="/common/js/bootstrap-5.3.1-dist/js/bootstrap.bundle.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
    
    <script type="text/javascript">
	    function whenPropose() {
			if(($(":checkbox[name='p_sul']:checked").length < 1) && $(":checkbox[name='p_sul']").length > 0) {
				alert("교육 수강목적을 선택해주세요.");
				return;
			}

			if(($(":radio[name='p_cate']:checked").length < 1) && $(":radio[name='p_cate']").length > 0) {
				alert("분야를 선택해주세요.");
				$("#p_cate_1").focus();
				return;
			}
	
			if(!confirm("${resultbox.d_subjnm} 과정을 수강신청 하시겠습니까?"))  return;
			
	        document.form1.p_process.value = "SubjectEduPropose";
	        document.form1.target = "_self";
	        document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
	        document.form1.submit();
		}
	    
	    function whenCancel() {
			if (confirm('정말 취소하시겠습니까?')) {
				alert('수강신청을 취소하였습니다.');
				window.close();
			}
		}

		$(document).ready(function() {
			$("input:radio[name=p_cate]").click(function () {
				if ($("input[name=p_cate]:checked").val() == "CT009") {
					$("#p_cate_txt").attr("disabled", false);
					$("#p_cate_txt").focus();
				} else {
					$("#p_cate_txt").attr("disabled", true);
					$("#p_cate_txt").val("");
				}
			});
		});
    </script>
</head>
<body>
	<form name="form1" action="/servlet/controller.propose.ProposeCourseServlet" method="post">
		<input type="hidden" name="p_upperclass"value="${param.p_upperclass }"/>
		<input type="hidden" name="p_process"	value="${param.p_process }"/>
		<input type="hidden" name="p_rprocess"	value=""/>
		<input type="hidden" name="p_year"	    value="${param.p_year }"/>
		<input type="hidden" name="p_subj"	    value="${param.p_subj }"/>
		<input type="hidden" name="p_subjseq"	value="${param.p_subjseq }"/>
		<input type="hidden" name="p_chkfirst"	value="${resultbox.d_autoconfirm }"/><%// 자동승인여부 %>
		<input type="hidden" name="p_subjnm"	value="${resultbox.d_subjnm }"/>
		<input type="hidden" name="p_iscourseYn"	value=""/>
	    <input type="hidden" name="p_privateYn"	value=""/>
	    <input type="hidden" name="p_area"	    value="${param.p_area }"/>
	    <input type="hidden" name="p_favoryn"	value="${param.p_favoryn }"/>
	    
	    <input type="hidden" name="p_name" value="${resultbox.d_name }"/>
	    <input type="hidden" name="p_biyong" value="${resultbox.d_biyong }"/>
	    <input type="hidden" name="p_handphone" id="p_handphone" value="${resultbox.d_handphone }" />
	    <input type="hidden" name="p_email" id="p_email" value="${resultbox.d_email }" />
	    <section>
	        <div class="wrapper layer_board_table_wrap">
	            <div class="container">
	                <div class="row">
	                    <div class="col-12">
	                        <div class="subContainer">
	                            <div class="sub_section">
	                                <div class="sub_contents_header">
	                                    <span>수강신청</span>
	                                </div>
	                                <div class="sub_contents_body">
	                                	<!--
	                                    <div class="sub_board_header">
	                                        <div class="popupTitle">
	                                            <p>개인정보 활용 동의</p>
	                                            <span>매년 기획재정부에서 실시하고 있는 공공기관 고객만족도 조사에 조사 대상자로 선정될 수 있으며, 등록된 개인정보 중 성명,연령,연락처가 활용될수 있습니다.</span>
	                                            <br/><br/>
	                                            <input type="checkbox" name="p_privatetyes" id="p_privatetyes" title="개인정보 활용 동의" /> <label for="p_privatetyes">&nbsp;&nbsp;동의합니다.</label>
	                                        </div>
	                                    </div>
	                                    -->
	                                    <div class="sub_info_body">
	                                        <p class="sub_course_view_title" style="font-weight: 600;">신청과정</p>
	                                        <table class="td_align_left">
	                                            <colgroup>
	                                                <col width="20%">
	                                                <col width="30%">
	                                                <col width="20%">
	                                                <col width="30%">
	                                            </colgroup>
	                                            <tbody>
	                                                <tr>
	                                                    <th>과정명</th>
	                                                    <td colspan="3"><c:out value="${resultbox.d_subjnm }" /></td>
	                                                </tr>
	                                                <tr>
	                                                    <th>신청자명</th>
	                                                    <td><c:out value="${resultbox.d_name }" /></td>
	                                                    <th>차수</th>
	                                                    <td><c:out value="${resultbox.d_subjseq }" /> 차</td>
	                                                </tr>
	                                                <tr>
	                                                    <th>수강신청기간</th>
	                                                    <td>
	                                                    	<fmt:parseDate value="${resultbox.d_propstart }" var="propstart" pattern="yyyyMMddHH" />
	                                           				<fmt:formatDate value="${propstart }" pattern="yyyy-MM-dd" />
	                                           				 ~ 
	                                           				<fmt:parseDate value="${resultbox.d_propend }" var="propend" pattern="yyyyMMddHH" />
	                                           				<fmt:formatDate value="${propend }" pattern="yyyy-MM-dd" />
	                                           			</td>
	                                                    <th>과정교육기간</th>
	                                                    <td>
	                                                    	<fmt:parseDate value="${resultbox.d_edustart }" var="edustart" pattern="yyyyMMddHH" />
	                                           				<fmt:formatDate value="${edustart }" pattern="yyyy-MM-dd" />
	                                           				 ~ 
	                                           				<fmt:parseDate value="${resultbox.d_eduend }" var="eduend" pattern="yyyyMMddHH" />
	                                           				<fmt:formatDate value="${eduend }" pattern="yyyy-MM-dd" />
	                                                    </td>
	                                                </tr>
	                                                <tr>
	                                                    <th>휴대전화</th>
	                                                    <td><c:out value="${resultbox.d_handphone }" /></td>
	                                                    <th>이메일</th>
	                                                    <td><c:out value="${resultbox.d_email }" /></td>
	                                                </tr>
	                                            </tbody>
	                                        </table>
	                                    </div>
										<div>
											<c:if test="${sessionScope.tem_grcode eq 'N000210'}">
												<div style="margin-top: 30px;">
													<p class="sub_course_view_title" style="font-weight: 600;">교육 수강목적(다중 선택 가능)</p>
													<%
														List sulList = CodeAdminBean.selectListCode("0124");

														if(sulList != null && sulList.size() > 0 ){
															String sCode   = "";
															String sCodeNm = "";
															for(int i = 0 ; i < sulList.size() ; i++){
																DataBox codeBox = (DataBox)sulList.get(i);

																sCode   = codeBox.getString("d_code");
																sCodeNm = codeBox.getString("d_codenm");

													%>
														<div style="margin-bottom: 10px">
															<input type="checkbox" name="p_sul" value="<%=sCode %>" id="p_sul_<%=i %>" title="<%=sCodeNm %>" style="margin-right: 7px;">
															<label for="p_sul_<%=i %>"><%=sCodeNm %></label>
														</div>
													<%
																if("C004".equals(sCode)) {
													%>
														<label>관련사업명(생략가능)</label><input type="text" name="p_busiNm" id="p_busiNm"style="width: 100%;"/>
													<%
																}
															}
														}
													%>
												</div>
												<div style="margin-top: 30px;">
													<p class="sub_course_view_title" style="font-weight: 600;">분야</p>
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
												</div>
											</c:if>
										</div>
	                                    <div class="popup_btnBox">
	                                        <a href="javascript:whenPropose();" class="btn_courseRegistration">수강신청</a>
	                                        <a href="javascript:whenCancel();" class="btn_courseCancel">취소</a>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </section>
	</form>
</body>
</html>
