<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page isELIgnored="false" %>
<script src="/common/js/swiper/swiper.min.js"></script>
<script type="text/javascript">
	function main_search(){
		if($("#relation_site").val() == null || $("#relation_site").val() == ""){
			alert("���������� ������ �ּ���.");
			$("#relation_site").focus();
			return;
		}
		
		if($.trim($("#p_searchtext").val()) == null || $.trim($("#p_searchtext").val()) == ""){
			alert("�˻�� �Է��� �ּ���.");
			$("#p_searchtext").focus();
			return;
		}
		
		if($("#relation_site").val() == "SUBJ"){
			document.formMainSearch.p_process.value = "REQUEST";
			document.formMainSearch.action = "/servlet/controller.homepage.AspMenuMainServlet";
			document.formMainSearch.submit();
		}else{
			document.formMainSearch.p_process.value = "mainPage";
			document.formMainSearch.action = "/servlet/controller.infomation.GoldClassHomePageServlet";
			document.formMainSearch.submit();
		}
	}

	function hiddenlayer(){
		$('.layerpopup-box, .layerbg-box').hide();
	}
</script>
	<section>
		<c:if test="${sessionScope.tem_grcode eq 'N000241'}">
			<script>

			</script>
			<div class="layerbg-box"></div>
			<div class="layerpopup-box">
				<div style="text-align: center">
					<h2>�˾� �޼���</h2>
					<div class="content-box">
						<c:set var="now" value="<%=new java.util.Date()%>" />
						<c:set var="nowDate"><fmt:formatDate value="${now}" pattern="yyyyMMddHHmmss" /></c:set>
						<c:if test="${nowDate <= '20230725235959'}">
						<p>
							�� 2023�� �ѱ������κ������ �������� �ȳ�<br><br>
							��ȭ������ ���������� ���汳�� ��������<br>
							(7�� 27�� 14:00~15:30 ���� ����)<br>
							<strong><a href="http://www.kawf.kr/notice/sub01View.do?selIdx=18319">-Ŭ�� �� �ȳ� �������� ����</a></strong><br><br>
							�� 2023�� �ѱ������κ������ �¶��� ���� ������ �ȳ�<br><br>
							��� ���´� ���� �������� �����ϼž� ������ �߱��� �����մϴ�<br><br>
							<strong>���� �������� �ݵ�� ����</strong>���ֽñ� �ٶ��ϴ�.<br><br>
						</p>
						</c:if>
						<c:if test="${nowDate > '20230725235959'}">
						<p>
							��¶��� ���� ���� �ȳ� (������û ���, ������ �߱� ���)<br>
							<strong>-Ŭ�� �� �ȳ� �������� ����</strong><br><br>
							2023�� �н���� �ȳ� ��ũ: <a href="https://bit.ly/3RzfWsl">(https://bit.ly/3RzfWsl)</a><br><br>
							�� 2023�� �ѱ������κ������ �¶��� ���� ������ �ȳ�<br><br>
							��� ���´� ���� �������� �����ϼž� ������ �߱��� �����մϴ�<br><br>
							<strong>���� �������� �ݵ�� ����</strong>���ֽñ� �ٶ��ϴ�.<br><br>
						</p>
						</c:if>
					</div>
					<div class="pop-btn-box"><button type="button" onclick="hiddenlayer();">Ȯ��</button></div>
				</div>

			</div>
			<style>
				.layerbg-box{
					position: fixed;
					top:0;
					width: 100%;
					height: 100%;
					background-color: rgba(0, 0, 0, 0.52);
					z-index: 1000;
				}
				.layerpopup-box{
					position: absolute;
					top:50%;
					left:50%;
					transform: translate(-50%, -50%);
					width: 800px;
					padding: 20px 20px;
					z-index: 1001;
					background-color: #fff;
					border: 1px solid #222;

				}
				.layerpopup-box h2{
					font-size: 24px;
					margin-bottom: 15px;
				}
				.layerpopup-box .content-box{
					font-size: 20px;
					line-height: 32px;
				}
				.pop-btn-box button{
					padding: 5px 8px;
					font-size: 14px;
				}
			</style>
		</c:if>
        <div class="wrapper">
            <div class="container">
                <div class="row main_contents_searchBox">
                    <div class="col-12 col-center">
                        <div class="col-lg-6 col-md-8 col-sm-12 col-center course_search_box">
                        	<form name="formMainSearch" method="POST">
                        		<input type="hidden" name="p_process" value="" />
                        		<input type="hidden" name="p_lecture_cls" value="<c:out value="${param.p_lecture_cls eq '' || param.p_lecture_cls eq null? 'ALL' : param.p_lecture_cls}" />" />
	                            <select class="relation_site" name="relation_site" id="relation_site" >
	                                <option value="">��������</option>
									<c:if test='${sessionScope.tem_grcode ne "N000179"}'>
	                                	<option value="SUBJ">���԰���</option>
									</c:if>
	                                <option value="OPEN">��������</option>
	                            </select>
	                            <input type="text" name="p_searchtext" id="p_searchtext" onkeypress="if(event.keyCode==13) {main_search(); return false;}" placeholder="�˻��� �Է�">
	                            <a class="btn_main_search" href="javascript:main_search();"></a>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="row">
					<c:if test="${sessionScope.tem_grcode ne 'N000179'}">
						<div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
							<div class="box_ct">
								<span class="ct_title">���԰���</span>
								<span class="btn_more"><a href="javascript:menuForward('9','02')">������</a></span>

								<div class="vertical_slide scroll-none">
									<ul>
										<c:forEach items="${subjectList }" var="list" varStatus="status">
											<li>
												<a href="javascript:viewSubject('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_classname }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');">
													<div class="img_box">
														<img src="<c:out value="${list.d_introducefilenamenew }" />" alt="<c:out value="${list.d_scsubjnm }" />">
													</div>
													<div class="edu_info">
														<span class="point_blue edu_title abbreviation"><c:out value="${list.d_scsubjnm }" /></span>
														<p class="edu_content abbreviation2"><c:out value="${list.d_intro }" /></p>
													</div>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:if>
					<div class="col-xl-3 <c:if test='${sessionScope.tem_grcode ne "N000179"}'>col-lg-4</c:if> col-md-6 col-sm-12">
						<div class="box_ct">
							<span class="ct_title">��������</span>
							<span class="btn_more float-right"><a href="javascript:menuForward('5','01');">������</a></span>

							<div class="vertical_slide scroll-none">
								<ul>
									<c:forEach items="${goldClassList }" var="list" varStatus="status">
										<li>
											<a href="javascript:viewContent('<c:out value="${list.d_seq }"/>', '<c:out value="${list.d_lecture_cls }"/>');">
												<div class="img_box">
													<img src="<c:out value="${list.d_vodimg }" />" alt="<c:out value="${list.d_lecnm }" />">
												</div>
												<div class="edu_info">
													<span class="point_blue edu_title abbreviation"><c:out value="${list.d_lecnm }" /></span>
													<p class="edu_content abbreviation2"><c:out value="${list.d_intro }" /></p>
												</div>
											</a>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
<%--					<c:if test="${sessionScope.tem_grcode eq 'N000134'}">
						<div class="col-xl-3 col-md-6 col-sm-12">
							<div class="box_ct">
								<span class="ct_title">��������</span>
								<span class="btn_more float-right"><a href="javascript:menuForward('4','07');">������</a></span>

								<div class="list_box">
									<ul>
										<c:forEach items="${noticeList }" var="list" varStatus="sataus">
											<li>
												<a href="javascript:viewNotice('<c:out value="${list.d_seq }" />');">
													<span class="abbreviation"><c:out value="${list.d_adtitle }" /></span>
													<span>
														<fmt:parseDate value="${list.d_addate }" var="noticeDate" pattern="yyyyMMddHHmmss" parseLocale="euc-kr"/>
														<fmt:formatDate value="${noticeDate }" pattern="yyyy-MM-dd" />
													</span>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:if>--%>
                    <div class="col-xl-3 <c:if test='${sessionScope.tem_grcode ne "N000179"}'>col-lg-4</c:if> col-md-6 col-sm-12">
                        <div class="box_ct">
                            <span class="ct_title">�н���������</span>
                            <div class="div4_box">
                                <div class="div4_box_con">
	                            	<a href="javascript:menuForward('4','01');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_faq.jpg" alt="FAQ">
	                                    </div>
	                                    <span>FAQ</span>
	                                    <p>���� �����Ǵ� ���׿� ����<br/>�亯�Դϴ�.</p>
	                                </a>
                                </div>
                                <div class="div4_box_con">
                                	<a href="javascript:menuForward('4','02');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_qna.jpg" alt="Q&A">
	                                    </div>
	                                    <span>Q&A</span>
	                                    <p>�ñ��Ͻ� ���뿡 ����<br/>�������װ� �亯�Դϴ�.</p>
	                                </a>
                                </div>
                                <div class="div4_box_con">
                                	<a href="javascript:menuForward('4','14');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_remote.jpg" alt="��������">
	                                    </div>
	                                    <span>��������</span>
	                                    <p>���������� ��������<br/>���񽺸� �����մϴ�.</p>
                                	</a>
                                </div>
                                <div class="div4_box_con">
                                    <div class="div_4_imgBox">
                                        <img src="/common/image/call.jpg" alt="�¶��α���">
                                    </div>
                                    <span>��������</span>
                                    <p style="font-size:16px; font-weight: bold;">02-6310-0770</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 <c:if test='${sessionScope.tem_grcode ne "N000179"}'>col-lg-4</c:if> col-md-6 col-sm-12">
						<c:if test='${sessionScope.tem_grcode ne "N000179"}'>
							<c:if test='${sessionScope.tem_grcode eq "N000134"}'>
							<a href="https://edu.kocca.kr" target="_blank">
								<div class="image_banner_con">
									<div class="box_ct">
										<img src="/common/image/edukocca_web_img01.png" alt="������ī �ٷΰ���"/>
									</div>
								</div>
							</a>
							</c:if>
							<c:if test='${sessionScope.tem_grcode ne "N000134"}'>
							<a href="javascript:menuForward('3','11');">
								<div class="image_banner_con">
									<div class="box_ct">
										<img src="/common/image/banner_main_mypage2.jpg" alt="���ǰ��ǽ� �ٷΰ���"/>
									</div>
								</div>
							</a>
							</c:if>
						</c:if>
						<c:if test='${sessionScope.tem_grcode eq "N000179"}'>
							<a href="javascript:menuForward('4','07');">
								<div class="image_banner_con">
									<div class="box_ct">
										<img src="/common/image/banner_main_mypage1792.png" alt="�������� �ٷΰ���"/>
									</div>
								</div>
							</a>
						</c:if>
                    </div>
					<c:if test="${sessionScope.tem_grcode ne 'N000179'}">
						<div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
							<div class="box_ct">
								<span class="ct_title">��������</span>
								<span class="btn_more float-right"><a href="javascript:menuForward('4','07');">������</a></span>

								<div class="list_box">
									<ul>
										<c:forEach items="${noticeList }" var="list" varStatus="sataus">
											<li>
												<a href="javascript:viewNotice('<c:out value="${list.d_seq }" />');">
													<span class="abbreviation"><c:out value="${list.d_adtitle }" /></span>
													<span>
														<fmt:parseDate value="${list.d_addate }" var="noticeDate" pattern="yyyyMMddHHmmss" parseLocale="euc-kr"/>
														<fmt:formatDate value="${noticeDate }" pattern="yyyy-MM-dd" />
													</span>
												</a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</c:if>
                    <div class="col-xl-3 <c:if test='${sessionScope.tem_grcode ne "N000179"}'>col-lg-4</c:if> col-md-6 col-sm-12">
						<c:if test='${sessionScope.tem_grcode ne "N000134"}'>
						<div class="box_ct swiper-container" style="padding:0" id="main_banner_slide">
							<div class="swiper-wrapper">
								<div class="swiper-slide">
									<a href="#">
										<span style="display: none;">������ ����缺�� ��� �ѱ���������ī���̿����� �������� �پ��� �ް� ����� �����մϴ�.</span>
										<img src="${(sessionScope.tem_grcode ne "N000179")? "/common/image/banner1.png" : "/common/image/banner1791.png"}" title="������ ����缺�� ��� �ѱ���������ī���̿����� �������� �پ��� �ް� ����� �����մϴ�." alt="������ ����缺�� ��� �ѱ���������ī���̿����� �������� �پ��� �ް� ����� �����մϴ�.">
									</a>
								</div>
								<div class="swiper-slide">
									<a href="#">
										<span style="display: none;">���� ��𼭳� ������ ��ۿ���, ����, ��ȭ ������ �о��� ���� ��ĥ�� �е��� ���� ���� �¶��� ����</span>
										<img src="${(sessionScope.tem_grcode ne "N000179")? "/common/image/banner2.jpg" : "/common/image/banner1792.png"}" title="���� ��𼭳� ������ ��ۿ���, ����, ��ȭ ������ �о��� ���� ��ĥ�� �е��� ���� ���� �¶��� ����" alt="���� ��𼭳� ������ ��ۿ���, ����, ��ȭ ������ �о��� ���� ��ĥ�� �е��� ���� ���� �¶��� ����">
									</a>
								</div>
							</div>
							<div class="swiperControlBox">
								<div class="col-12 swiper-pagination"></div>
								<div class="swiper-button-prev"></div>
								<div class="swiper-button-next"></div>
								<div class="swiper-autoplay-stop"></div>
							</div>
						</div>
						</c:if>
						<c:if test='${sessionScope.tem_grcode eq "N000134"}'>
							<div class="image_banner_con" style="margin: 0;">
								<div class="box_ct" style="padding: 0!important;">
									<iframe width="100%" height="100%" src="https://www.youtube.com/embed/ssNYj-IJg7U" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>
								</div>
							</div>
						</c:if>
                    </div>
                </div>
            </div>
        </div>
    </section>

