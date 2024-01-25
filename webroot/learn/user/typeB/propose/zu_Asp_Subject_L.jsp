<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
	//������ �̵�
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}

	//������ �̵�
	function selectArea(area) {
		$("#p_area").val(area);
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "REQUEST";
		document.form1.p_pageno.value = 0;
		document.form1.submit();
	}

	//���� ���뺸��
	function whenSubjInfo(subj,subjnm,courseyn,upperclass,upperclassnm, year, subjseq, wj_classkey){
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.form1.p_iscourseYn.value = courseyn;
		document.form1.p_upperclass.value = upperclass;
		document.form1.p_upperclassnm.value = upperclassnm;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
		document.form1.p_wj_classkey.value = wj_classkey;
		document.form1.p_process.value = 'SubjectPreviewPage';
		document.form1.p_rprocess.value = 'SubjectList';
		document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
		document.form1.target = "_self";
		document.form1.submit();
	}

	//������û
	function whenSubjPropose(subj,year,subjseq, subjnm) {
		if('${sessionScope.userid}' == '') {
			alert("�α����� �ʿ��մϴ�.");
			return;
		}

		if(!confirm(subjnm+"������ ������û�Ͻðڽ��ϱ�?")) {
			return;
		}

		var new_Open = "";

		if('${sessionScope.tem_grcode}' == "N000210"){
			new_Open = window.open("","proposeWindow",'scrollbars=yes,width=1000,height=800,resizable=no');
		}else{
			new_Open = window.open("","proposeWindow",'scrollbars=yes,width=1000,height=600,resizable=no');
		}

		document.form1.p_process.value = "SubjectEduProposePage";
		document.form1.target = "proposeWindow";
		document.form1.p_subj.value = subj;
		document.form1.p_year.value = year;
		document.form1.p_subjseq.value = subjseq;
		document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
		document.form1.submit();
		document.form1.target = "_self";
	}

	//������
	function whenPreShow(url, subj, width, height, d_wj_classkey) {
		if(width == '' || width == 0 || width == null){
			width = 800;
		}
		if(height == '' || height == 0 || height == null){
			height = 600;
		}
		prelog_url = "/servlet/controller.propose.ProposeCourseServlet?p_process=insertPreviewLog&p_subj="+subj;
		open_window("openShow",url,"100","100",width,height,"","","",true,true);
	}

	function fnSelect(){
		document.form1.action="/servlet/controller.homepage.AspMenuMainServlet";
		document.form1.p_process.value = "REQUEST";
		document.form1.p_pageno.value = "1";
		document.form1.submit();
	}
</script>

<c:set var="pageno" value="${param.p_pageno eq null || param.p_pageno eq ''? 1 : param.p_pageno}" />
<c:set var="pagesize" value="${param.p_pagesize eq null || param.p_pagesize eq ''? 10 : param.p_pagesize}" />

<section class="container d-flex">
	<div class=""></div>
	<div class="subContainer">
		<div class="sub_section">
			<div class="sub_contents_header" style="padding-bottom: 0; margin-bottom: 0;">
				<span style="margin-top: 30px;">���԰���</span>
			</div>
			<div class="sub_contents_body">
				<div class="sub_board_header">
					<jsp:include page="/learn/user/typeB/include_left/left_9.jsp">
						<jsp:param value="${param.menuid }" name="left_active"/>
					</jsp:include>

					<div class="d-flex justify-content-lg-between">
						<div class="board_search_box">
							<form name="form1" action="/servlet/controller.homepage.AspMenuMainServlet" method="post">
								<input type="hidden" name="p_process"	value="<c:out value="${param.p_process }" />"/>
								<input type="hidden" name="p_rprocess"	value=""/>
								<input type="hidden" name="p_year"	value=""/>
								<input type="hidden" name="p_subj"	value=""/>
								<input type="hidden" name="p_subjseq"	value=""/>
								<input type="hidden" name="p_subjnm"	value=""/>
								<input type="hidden" name="p_order"	value="<c:out value="${param.p_order }" />"/>
								<input type="hidden" name="p_iscourseYn"	value=""/>
								<input type="hidden" name="p_upperclass"	value="<c:out value="${param.p_upperclass }" />"/>
								<input type="hidden" name="p_upperclassnm"	value=""/>
								<input type="hidden" name="p_action"	value="<c:out value="${param.p_action }" />"/>
								<input type="hidden" name="p_pageno"	value="<c:out value="${param.p_pageno }" />"/>
								<input type="hidden" name="p_pagesize"  value="<c:out value="${param.p_pagesize }" />"/>
								<input type="hidden" name="p_area" id="p_area"	value="<c:out value="${param.p_area }" />"/>
								<input type="hidden" name="p_wj_classkey" value=""/>
								<input type="hidden" name="gubun" value="${param.gubun }" />
								<input type="hidden" name="menuid" value="${param.menuid }" />
								<div class="input-group mb-3">
									<input type="text" name="p_searchtext" id="p_searchtext" class="form-control input board_search" value="<c:out value="${param.p_searchtext }" />" title="�˻�� �Է����ּ���." placeholder="�˻�� �Է����ּ���." onkeypress="if(event.keyCode==13) {javascript:fnSelect()(); return false;}">
									<input type="button" class="btn btn-outline-secondary input btn_board_search" value="" onclick="javascript:fnSelect();">
								</div>
							</form>
						</div>
						<ul class="radio-row-box">
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
								<label class="form-check-label" for="inlineRadio1">��ü</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
								<label class="form-check-label" for="inlineRadio2">��û����</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3">
								<label class="form-check-label" for="inlineRadio3">����</label>
							</li>
						</ul>
					</div>
				</div>

				<div class="sub_thumb_body">
					<div class="newcontent-slide-wrap">
                        <span class="title">
                            NEW �ű� Ŭ����
                        </span>
						<div class="swiper-container newcontent-slide-box">
							<ul class="swiper-wrapper">
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1172&pLectureCls=GC01" tabindex="-1">
											<!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class06.png" alt="" class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class06.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>ũ�������͸� ����� ũ��������</h3>
										<p class="new_info_box">
											ä�� �����ú��� �����ð� �귣������!<br>
											�������� ���۰� ���� å������.<br>
											ũ�������Ϳ� �Բ��ϴ� ������� �̾߱�
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1166&pLectureCls=GC04" tabindex="-1">
											<!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class07.png" alt="" class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class07.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>������ â��, ���ǿ� ���Ͽ�</h3>
										<p class="new_info_box">
											���� ������? â�ۿ� ���� ����!<br>
											��ü�� ��ǰ ���� ��ʿ� �Բ� �˾ƺ���<br>
											���� â�۰� �ǹ� �̾߱�
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1186&pLectureCls=GC05" tabindex="-1">
                                            <!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class08.png" alt="" class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class08.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>������� ����� ���ټ� ���� ����</h3>
										<p class="new_info_box">
											������ ��� �� �ִ� ����??<br>
											���� ������ ���ټ��� ��̸� ��� ��´�!<br>
											������� ���� ���� ���Ǽ� ��� ������Ʈ<br>
											* �� ������ �������ڸ��� �������ε� �����˴ϴ�.
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1180&pLectureCls=GC01" tabindex="-1">
                                            <!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class09.png" alt="" class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class09.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>������, ���丮�ڸ��� ���</h3>
										<p class="new_info_box">
											Ž�� �������� �����̹�, ������ ���ɱ���!<br>
											�¶��� �������� ������ ������ ������<br>
											������, ����Ƽ �׸��� ������ �̾߱�
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://url.kr/4kizp9" tabindex="-1">
											<!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class05.png" alt="">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class05.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>������ ��Ƽ��Ʈ�� ����� �����</h3>
										<p class="new_info_box">
											�������� ����� �޴� K-POP ��Ƽ��Ʈ�� �޲۴�!<br>
											���� ����, �Ŵ�����Ʈ �׸��� ���� ���� ��ȹ����,
											���� ����� �پ��� ������ ������ �ñ��ϴٸ�?<br>
											���� �ٷ� ������ī���� Ȯ���ϼ���!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://url.kr/o8z1ua" tabindex="0">
											<!-- 360px �ʰ� ������ -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class04.png" alt="��θ� ���� ��ſ� : �踮������
                                            ������, �ɸ��� �庮�� ���ֱ� ���� ù ����.
                                            �踮�� ����(Barrier ? free), �󸶳� �˰� ��Ű���?
                                            ��������� �� �踮�������� �ñ��ϴٸ�?
                                            ���� ������ī���� Ȯ���غ�����!
                                            " class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class04.png" alt="��θ� ���� ��ſ� : �踮������
                                            ������, �ɸ��� �庮�� ���ֱ� ���� ù ����.
                                            �踮�� ����(Barrier ? free), �󸶳� �˰� ��Ű���?
                                            ��������� �� �踮�������� �ñ��ϴٸ�?
                                            ���� ������ī���� Ȯ���غ�����!" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>��θ� ���� ��ſ� : �踮������</h3>
										<p class="new_info_box">
											������, �ɸ��� �庮�� ���ֱ� ���� ù ����.<br>
											�踮�� ����(Barrier ? free), �󸶳� �˰� ��Ű���?<br>
											��������� �� �踮�������� �ñ��ϴٸ�?<br>
											���� ������ī���� Ȯ���غ�����!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1103&pLectureCls=GC02" tabindex="-1">
                                            <!-- 360px �ʰ� ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class03.png" alt="����ȸ���� ��� �� (feat.����)
                                            ���� ��� ���, ������ ����ϰ� �ִٸ�?
                                            �ǹ������� �پ��� ���� Ssul�� ����
                                            ���� ȸ�簡 ���ϴ� ��������
                                            ���� ����� ��ü �������� �� �˷��帳�ϴ�!
                                            ���� �ٷ� ������ī���� Ȯ���غ�����.
                                            " class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class03.png" alt="����ȸ���� ��� �� (feat.����)
                                            ���� ��� ���, ������ ����ϰ� �ִٸ�?
                                            �ǹ������� �پ��� ���� Ssul�� ����
                                            ���� ȸ�簡 ���ϴ� ��������
                                            ���� ����� ��ü �������� �� �˷��帳�ϴ�!
                                            ���� �ٷ� ������ī���� Ȯ���غ�����.
                                            " class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>����ȸ���� ��� �� (feat.����)</h3>
										<p class="new_info_box">
											���� ��� ���, ������ ����ϰ� �ִٸ�?<br/>
											�ǹ������� �پ��� ���� Ssul�� ����<br/>
											���� ȸ�簡 ���ϴ� ��������<br/>
											���� ����� ��ü �������� �� �˷��帳�ϴ�!<br/>
											���� �ٷ� ������ī���� Ȯ���غ�����.
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?pSeq=1092&pageIndex2=&pLectureCls=&&menuNo=500085&searchCnd=&searchWrd=&p_type=K0&p_gcd1=&p_gcd2=&p_level=&p_sort=N&pGubun1=K0&p_list_type=C&pageIndex=1" tabindex="-1">
                                            <!-- 360px �ʰ� ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class02.png" alt="���� ȸ�翡���� � ���� �ұ�?
                                            ������ IP�ν� ���ο� �ѷ� ��ȭ�� �ڸ��ű��� ����!
                                            ���� ������� �������� ������ �ϰ� �ִ� ���������
                                            ���� � ���� �ϰ� �ִ��� �ñ��ϴٸ�?
                                            ���� �ٷ� ������ī���� Ȯ���ϼ���!
                                            " class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class02.png" alt="���� ȸ�翡���� � ���� �ұ�?
                                            ������ IP�ν� ���ο� �ѷ� ��ȭ�� �ڸ��ű��� ����!
                                            ���� ������� �������� ������ �ϰ� �ִ� ���������
                                            ���� � ���� �ϰ� �ִ��� �ñ��ϴٸ�?
                                            ���� �ٷ� ������ī���� Ȯ���ϼ���!
                                            " class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>���� ȸ�翡���� � ���� �ұ�?</h3>
										<p class="new_info_box">
											������ IP�ν� ���ο� �ѷ� ��ȭ�� �ڸ��ű��� ����!<br/>
											���� ������� �������� ������ �ϰ� �ִ� ���������<br/>
											���� � ���� �ϰ� �ִ��� �ñ��ϴٸ�?<br/>
											���� �ٷ� ������ī���� Ȯ���ϼ���!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?pSeq=1087&pageIndex2=&pLectureCls=&&menuNo=500085&searchCnd=&searchWrd=&p_type=B0&p_gcd1=&p_gcd2=&p_level=&p_sort=N&pGubun1=B0&p_list_type=C&pageIndex=1" tabindex="-1">
                                            <!-- 360px �ʰ� ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class.png" alt="" class="pc-slide-snail">
											<!-- 360px ����(�����) ������ -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>������ ���� �����, OTT �̾߱�</h3>
										<p class="new_info_box">
											������, ũ��, �پ��ϰ� ���� ������ OTT!
											������ ������� �ϴ� ���� �ñ��� ������� ���� �غ��߽��ϴ�.
											OTT�÷��� �����ڵ�� �پ��� �����о߿� ���� �̾߱Ⱑ �ñ��ϴٸ�?
											������ī���� Ȯ���ϼ���!
										</p>
									</div>
								</li>
							</ul>
						</div>
						<div class="swiper_btn_box">
							<div><button type="button" class="swiper-button-prev" title="���� ��ʺ���"></button></div>
							<div><button type="button" class="swiper-button-next" title="���� ��ʰ���"></button></div>
						</div>
					</div>
					<div class="d-flex justify-content-between flex-wrap">
						<!-- �ش��ʿ����� 4�� ���� ���� �� �ؿ� �����ġ �ϱ�-->
						<div class="header-benner-box">
							<a href="https://edu.kocca.kr/edu/onlineEdu/themeLecture/list.do?menuNo=500157" class="d-inblock"><img src="/images/2023/B2B_benner01.png" class="img-fluid" alt="������ī �׸����� �ٷΰ��� ���" ></a>
						</div>
						<!-- �ش��ʿ����� 4�� ���� ���� �� �ؿ� �����ġ �ϱ�-->
						<c:if test="${fn:length(SubjectList) <= 0 }">
							<p class="text-center">
								��ϵ� ���԰��� ������ �����ϴ�.
							</p>
						</c:if>
						<c:forEach items="${SubjectList }" var="list" varStatus="status">
							<div class="thumb_box card">
								<a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');" class="thumb_imgBox">
									<img src="<c:out value="${list.d_introducefilenamenew }" />" alt="<c:out value="${list.d_subjnm }" />">
									<%--<img src="/images/2023/CB23003.png" alt="������ ȣ��">--%>
								</a>
								<div class="card-body thumb_con">
									<div class="thumb_top_tag">
										<c:if test="${list.d_ishit eq 'Y' }"><span class="tab_type_popular"></span></c:if>
										<c:if test="${list.d_isnew eq 'Y' }"><span class="tag_type_new"></span></c:if>
										<c:if test="${list.d_isrecom eq 'Y' }"><span class="tag_type_recommend"></span></c:if>
									</div>
									<a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');"  class="card-title">
									<span class="thumb_title textline2">
										<c:out value="${list.d_subjnm }" />
									</span>
									</a>
									<ul class="thumb_con_Info card-text">
										<li>
											<span>������û�Ⱓ : <c:out value="${list.d_edustart }" /> ~ <c:out value="${list.d_eduend }" /></span>
										</li>
									</ul>
										<%--<div class="thumb_button">
                                            <c:choose>
                                                <c:when test="${list.d_existpropose }">
                                                    <a href="javascript:void(0);" class="applycomplete_btn">��û�Ϸ�</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${list.d_propyn eq 'Y' }">
                                                        <a href="javascript:whenSubjPropose('<c:out value="${list.d_subj }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_subjnm }" />');" class="apply_btn  btn btn-purple">��û</a>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${sessionScope.tem_grcode ne 'N000210'}">
                                                <c:if test="${list.d_preurl ne '' }">
                                                    <a href="javascript:whenPreShow('<c:out value="${list.d_preurl }" />','<c:out value="${list.d_subj }" />','<c:out value="${list.d_prewidth }" />', '<c:out value="${list.d_preheight }" />', '<c:out value="${list.d_wj_classkey }" />');" class="gustation_btn btn btn-outline-secondary">������</a>
                                                </c:if>
                                            </c:if>
                                        </div>--%>
								</div>
							</div>
							<c:set var="totalpage" value="${list.d_totalpage }" />
						</c:forEach>
					</div>

				</div>
				${pu:typeB_printPageListDiv(totalpage, pageno, pagesize) }

			</div>
		</div>
	</div>
	</div>
</section>
<script>
	var swiper = new Swiper('.newcontent-slide-box', {
		/*loop: true,*/
		/*autoplay : 3000 */
		speed : 700
		,direction: 'horizontal' // �����̵� ��������� ����(vertical�ϸ� �������� ������)
		,slidesPerView: 1 // �ѹ��� ���̴� �����̵� ����
		,spaceBetween: 10 // �����̵� ������ ���� px ����
		//������ swiper ����ǥ
		,nextButton: '.newcontent-slide-wrap .swiper-button-next'
		,prevButton: '.newcontent-slide-wrap .swiper-button-prev'

		/*,onSlideChangeStart : function() { //�����̵尡 ������ �ٲ������ ����
			//alert(123);
			$(".main_new_contents .swiper-slide a").attr('tabindex','-1');
			$(".main_new_contents .swiper-slide-active a").attr('tabindex','0');;
		},*/

	});
</script>

<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp" />
<!-- footer -->
</body>
</html>