<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/KoccaPageUtilTaglib" prefix="pu" %>
<%@ page isELIgnored="false" %>
<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp" />

<script type="text/javascript">
	//페이지 이동
	function goPage(pageNum) {
		document.form1.p_action.value = "go";
		document.form1.p_pageno.value = pageNum;
		document.form1.p_process.value = "REQUEST";
		document.form1.submit();
	}

	//페이지 이동
	function selectArea(area) {
		$("#p_area").val(area);
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "REQUEST";
		document.form1.p_pageno.value = 0;
		document.form1.submit();
	}

	//과정 내용보기
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

	//수강신청
	function whenSubjPropose(subj,year,subjseq, subjnm) {
		if('${sessionScope.userid}' == '') {
			alert("로그인이 필요합니다.");
			return;
		}

		if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")) {
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

	//맛보기
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
				<span style="margin-top: 30px;">정규과정</span>
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
									<input type="text" name="p_searchtext" id="p_searchtext" class="form-control input board_search" value="<c:out value="${param.p_searchtext }" />" title="검색어를 입력해주세요." placeholder="검색어를 입력해주세요." onkeypress="if(event.keyCode==13) {javascript:fnSelect()(); return false;}">
									<input type="button" class="btn btn-outline-secondary input btn_board_search" value="" onclick="javascript:fnSelect();">
								</div>
							</form>
						</div>
						<ul class="radio-row-box">
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked>
								<label class="form-check-label" for="inlineRadio1">전체</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
								<label class="form-check-label" for="inlineRadio2">신청가능</label>
							</li>
							<li class="form-check form-check-inline">
								<input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3">
								<label class="form-check-label" for="inlineRadio3">마감</label>
							</li>
						</ul>
					</div>
				</div>

				<div class="sub_thumb_body">
					<div class="newcontent-slide-wrap">
                        <span class="title">
                            NEW 신규 클래스
                        </span>
						<div class="swiper-container newcontent-slide-box">
							<ul class="swiper-wrapper">
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1172&pLectureCls=GC01" tabindex="-1">
											<!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class06.png" alt="" class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class06.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>크리에이터를 만드는 크리에이터</h3>
										<p class="new_info_box">
											채널 컨설팅부터 마케팅과 브랜딩까지!<br>
											콘텐츠의 시작과 끝을 책임진다.<br>
											크리에이터와 함께하는 사람들의 이야기
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1166&pLectureCls=GC04" tabindex="-1">
											<!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class07.png" alt="" class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class07.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>무한한 창작, 음악에 관하여</h3>
										<p class="new_info_box">
											음악 콘텐츠? 창작엔 끝이 없다!<br>
											매체별 작품 제작 사례와 함께 알아보는<br>
											음악 창작과 실무 이야기
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1186&pLectureCls=GC05" tabindex="-1">
                                            <!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class08.png" alt="" class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class08.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>장애인을 고려한 접근성 높은 게임</h3>
										<p class="new_info_box">
											누구나 즐길 수 있는 게임??<br>
											게임 콘텐츠 접근성과 재미를 모두 잡는다!<br>
											장애인을 위한 게임 편의성 향상 프로젝트<br>
											* 본 과정은 ‘수어자막‘ 버전으로도 제공됩니다.
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1180&pLectureCls=GC01" tabindex="-1">
                                            <!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class09.png" alt="" class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class09.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>콘텐츠, 스토리텔링을 담다</h3>
										<p class="new_info_box">
											탐사 보도부터 서바이벌, 스포츠 예능까지!<br>
											온라인 콘텐츠로 만나는 현장의 생생함<br>
											진정성, 리얼리티 그리고 공감의 이야기
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://url.kr/4kizp9" tabindex="-1">
											<!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class05.png" alt="">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class05.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>빛나는 아티스트를 만드는 사람들</h3>
										<p class="new_info_box">
											전세계의 사랑을 받는 K-POP 아티스트를 꿈꾼다!<br>
											신인 개발, 매니지먼트 그리고 각종 공연 기획까지,
											음악 산업의 다양한 직무와 역량이 궁금하다면?<br>
											지금 바로 에듀코카에서 확인하세요!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://url.kr/o8z1ua" tabindex="0">
											<!-- 360px 초과 섬네일 -->
                                            <img src="https://edu.kocca.kr/edu/images/renew2022/new_class04.png" alt="모두를 위한 즐거움 : 배리어프리
                                            물리적, 심리적 장벽을 없애기 위한 첫 걸음.
                                            배리어 프리(Barrier ? free), 얼마나 알고 계신가요?
                                            콘텐츠산업 내 배리어프리가 궁금하다면?
                                            지금 에듀코카에서 확인해보세요!
                                            " class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class04.png" alt="모두를 위한 즐거움 : 배리어프리
                                            물리적, 심리적 장벽을 없애기 위한 첫 걸음.
                                            배리어 프리(Barrier ? free), 얼마나 알고 계신가요?
                                            콘텐츠산업 내 배리어프리가 궁금하다면?
                                            지금 에듀코카에서 확인해보세요!" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>모두를 위한 즐거움 : 배리어프리</h3>
										<p class="new_info_box">
											물리적, 심리적 장벽을 없애기 위한 첫 걸음.<br>
											배리어 프리(Barrier ? free), 얼마나 알고 계신가요?<br>
											콘텐츠산업 내 배리어프리가 궁금하다면?<br>
											지금 에듀코카에서 확인해보세요!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?menuNo=500085&pSeq=1103&pLectureCls=GC02" tabindex="-1">
                                            <!-- 360px 초과 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class03.png" alt="게임회사의 모든 것 (feat.직무)
                                            게임 기업 취업, 이직을 희망하고 있다면?
                                            실무진들의 다양한 직무 Ssul은 물론
                                            게임 회사가 원하는 인재상부터
                                            게임 산업의 전체 구조까지 다 알려드립니다!
                                            지금 바로 에듀코카에서 확인해보세요.
                                            " class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class03.png" alt="게임회사의 모든 것 (feat.직무)
                                            게임 기업 취업, 이직을 희망하고 있다면?
                                            실무진들의 다양한 직무 Ssul은 물론
                                            게임 회사가 원하는 인재상부터
                                            게임 산업의 전체 구조까지 다 알려드립니다!
                                            지금 바로 에듀코카에서 확인해보세요.
                                            " class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>게임회사의 모든 것 (feat.직무)</h3>
										<p class="new_info_box">
											게임 기업 취업, 이직을 희망하고 있다면?<br/>
											실무진들의 다양한 직무 Ssul은 물론<br/>
											게임 회사가 원하는 인재상부터<br/>
											게임 산업의 전체 구조까지 다 알려드립니다!<br/>
											지금 바로 에듀코카에서 확인해보세요.
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?pSeq=1092&pageIndex2=&pLectureCls=&&menuNo=500085&searchCnd=&searchWrd=&p_type=K0&p_gcd1=&p_gcd2=&p_level=&p_sort=N&pGubun1=K0&p_list_type=C&pageIndex=1" tabindex="-1">
                                            <!-- 360px 초과 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class02.png" alt="웹툰 회사에서는 어떤 일을 할까?
                                            콘텐츠 IP로써 새로운 한류 문화로 자리매김한 웹툰!
                                            웹툰 산업에서 선도적인 역할을 하고 있는 기업에서는
                                            과연 어떤 일을 하고 있는지 궁금하다면?
                                            지금 바로 에듀코카에서 확인하세요!
                                            " class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class02.png" alt="웹툰 회사에서는 어떤 일을 할까?
                                            콘텐츠 IP로써 새로운 한류 문화로 자리매김한 웹툰!
                                            웹툰 산업에서 선도적인 역할을 하고 있는 기업에서는
                                            과연 어떤 일을 하고 있는지 궁금하다면?
                                            지금 바로 에듀코카에서 확인하세요!
                                            " class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>웹툰 회사에서는 어떤 일을 할까?</h3>
										<p class="new_info_box">
											콘텐츠 IP로써 새로운 한류 문화로 자리매김한 웹툰!<br/>
											웹툰 산업에서 선도적인 역할을 하고 있는 기업에서는<br/>
											과연 어떤 일을 하고 있는지 궁금하다면?<br/>
											지금 바로 에듀코카에서 확인하세요!
										</p>
									</div>
								</li>
								<li class="swiper-slide">
                                    <span class="new_snail_box">
                                        <a href="https://edu.kocca.kr/edu/onlineEdu/openLecture/view.do?pSeq=1087&pageIndex2=&pLectureCls=&&menuNo=500085&searchCnd=&searchWrd=&p_type=B0&p_gcd1=&p_gcd2=&p_level=&p_sort=N&pGubun1=B0&p_list_type=C&pageIndex=1" tabindex="-1">
                                            <!-- 360px 초과 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/new_class.png" alt="" class="pc-slide-snail">
											<!-- 360px 이하(모바일) 섬네일 -->
											<img src="https://edu.kocca.kr/edu/images/renew2022/m_new_class.png" alt="" class="mobile-slide-snail">
                                        </a>
                                    </span>
									<div class="new_txt_box pc-slide-txt">
										<h3>콘텐츠 뒤의 사람들, OTT 이야기</h3>
										<p class="new_info_box">
											빠르고, 크고, 다양하게 퍼져 나가는 OTT!
											콘텐츠 기업에서 하는 일이 궁금한 사람들을 위해 준비했습니다.
											OTT플랫폼 현직자들과 다양한 직무분야에 대한 이야기가 궁금하다면?
											에듀코카에서 확인하세요!
										</p>
									</div>
								</li>
							</ul>
						</div>
						<div class="swiper_btn_box">
							<div><button type="button" class="swiper-button-prev" title="이전 배너보기"></button></div>
							<div><button type="button" class="swiper-button-next" title="다음 배너가기"></button></div>
						</div>
					</div>
					<div class="d-flex justify-content-between flex-wrap">
						<!-- 해당배너영역은 4개 과정 나온 후 밑에 배너위치 하기-->
						<div class="header-benner-box">
							<a href="https://edu.kocca.kr/edu/onlineEdu/themeLecture/list.do?menuNo=500157" class="d-inblock"><img src="/images/2023/B2B_benner01.png" class="img-fluid" alt="에듀코카 테마과정 바로가기 배너" ></a>
						</div>
						<!-- 해당배너영역은 4개 과정 나온 후 밑에 배너위치 하기-->
						<c:if test="${fn:length(SubjectList) <= 0 }">
							<p class="text-center">
								등록된 정규강좌 내역이 없습니다.
							</p>
						</c:if>
						<c:forEach items="${SubjectList }" var="list" varStatus="status">
							<div class="thumb_box card">
								<a href="javascript:whenSubjInfo('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />','<c:out value="${list.d_scupperclass }" />','<c:out value="${list.d_uclassnm }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_wj_classkey }" />');" class="thumb_imgBox">
									<img src="<c:out value="${list.d_introducefilenamenew }" />" alt="<c:out value="${list.d_subjnm }" />">
									<%--<img src="/images/2023/CB23003.png" alt="섬네일 호출">--%>
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
											<span>수강신청기간 : <c:out value="${list.d_edustart }" /> ~ <c:out value="${list.d_eduend }" /></span>
										</li>
									</ul>
										<%--<div class="thumb_button">
                                            <c:choose>
                                                <c:when test="${list.d_existpropose }">
                                                    <a href="javascript:void(0);" class="applycomplete_btn">신청완료</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:if test="${list.d_propyn eq 'Y' }">
                                                        <a href="javascript:whenSubjPropose('<c:out value="${list.d_subj }" />','<c:out value="${list.d_scyear }" />','<c:out value="${list.d_subjseq }" />','<c:out value="${list.d_subjnm }" />');" class="apply_btn  btn btn-purple">신청</a>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${sessionScope.tem_grcode ne 'N000210'}">
                                                <c:if test="${list.d_preurl ne '' }">
                                                    <a href="javascript:whenPreShow('<c:out value="${list.d_preurl }" />','<c:out value="${list.d_subj }" />','<c:out value="${list.d_prewidth }" />', '<c:out value="${list.d_preheight }" />', '<c:out value="${list.d_wj_classkey }" />');" class="gustation_btn btn btn-outline-secondary">맛보기</a>
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
		,direction: 'horizontal' // 슬라이드 진행방향은 수평(vertical하면 수직으로 움직임)
		,slidesPerView: 1 // 한번에 보이는 슬라이드 갯수
		,spaceBetween: 10 // 슬라이드 사이의 간격 px 단위
		//구버전 swiper 방향표
		,nextButton: '.newcontent-slide-wrap .swiper-button-next'
		,prevButton: '.newcontent-slide-wrap .swiper-button-prev'

		/*,onSlideChangeStart : function() { //슬라이드가 완전히 바뀌었을때 실행
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