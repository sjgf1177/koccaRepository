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
			alert("과정구분을 선택해 주세요.");
			$("#relation_site").focus();
			return;
		}
		
		if($.trim($("#p_searchtext").val()) == null || $.trim($("#p_searchtext").val()) == ""){
			alert("검색어를 입력해 주세요.");
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
</script>
	<section>
        <div class="wrapper">
            <div class="container">
                <div class="row main_contents_searchBox">
                    <div class="col-12 col-center">
                        <div class="col-lg-6 col-md-8 col-sm-12 col-center course_search_box">
                        	<form name="formMainSearch" method="POST">
                        		<input type="hidden" name="p_process" value="" />
                        		<input type="hidden" name="p_lecture_cls" value="<c:out value="${param.p_lecture_cls eq '' || param.p_lecture_cls eq null? 'ALL' : param.p_lecture_cls}" />" />
	                            <select class="relation_site" name="relation_site" id="relation_site" >
	                                <option value="">과정구분</option>
	                                <option value="SUBJ">정규과정</option>
	                                <option value="OPEN">열린강좌</option>
	                            </select>
	                            <input type="text" name="p_searchtext" id="p_searchtext" onkeypress="if(event.keyCode==13) {main_search(); return false;}" placeholder="검색어 입력">
	                            <a class="btn_main_search" href="javascript:main_search();"></a>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                        <div class="box_ct">
                            <span class="ct_title">정규과정</span>
                            <span class="btn_more"><a href="javascript:menuForward('9','02')">더보기</a></span>

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
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                        <div class="box_ct">
                            <span class="ct_title">열린과정</span>
                            <span class="btn_more float-right"><a href="javascript:menuForward('5','01');">더보기</a></span>

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
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                        <div class="box_ct">
                            <span class="ct_title">학습지원센터</span>
                            <div class="div4_box">
                                <div class="div4_box_con">
	                            	<a href="javascript:menuForward('4','01');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_faq.jpg" alt="FAQ">
	                                    </div>
	                                    <span>FAQ</span>
	                                    <p>자주 질문되는 사항에 대한<br/>답변입니다.</p>
	                                </a>
                                </div>
                                <div class="div4_box_con">
                                	<a href="javascript:menuForward('4','02');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_qna.jpg" alt="Q&A">
	                                    </div>
	                                    <span>Q&A</span>
	                                    <p>궁금하신 내용에 대한<br/>질문사항과 답변입니다.</p>
	                                </a>
                                </div>
                                <div class="div4_box_con">
                                	<a href="javascript:menuForward('4','14');" style="text-decoration: none !important;">
	                                    <div class="div_4_imgBox">
	                                        <img src="/common/image/icon_remote.jpg" alt="원격지원">
	                                    </div>
	                                    <span>원격지원</span>
	                                    <p>전문상담원이 원격지원<br/>서비스를 제공합니다.</p>
                                	</a>
                                </div>
                                <div class="div4_box_con">
                                    <div class="div_4_imgBox">
                                        <img src="/common/image/call.jpg" alt="온라인교육">
                                    </div>
                                    <span>교육문의</span>
                                    <p style="font-size:16px; font-weight: bold;">02-6310-0770</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                    	<a href="javascript:menuForward('3','11');">
	                        <div class="image_banner_con">
	                            <div class="box_ct">    
	                               	<img src="/common/image/banner_main_mypage2.jpg" alt="나의강의실 바로가기"/>
	                               	<!-- 
	                                <span class="banner_title">나의강의실</span>
	                                <a href="javascript:menuForward('3','11');">바로가기</a>
	                                <p class="banner_con">온라인과정은 방송영상, 게임개발, 문화콘텐츠 관련 콘텐츠를 웹기반 교육시스템을 통해 시간과 공간의 제약없이 누구나 교육을 받을 수 있는 '열린'공간입니다.</p>
	                                -->
	                            </div>
	                        </div>
                    	</a>
                        <!-- 
                        <div class="image_banner_con">
                            <div class="box_ct">    
                               	<img src="/common/image/banner_main_mypage.jpg" alt="나의강의실 바로가기"/>
                                <span class="banner_title">나의강의실</span>
                                <a href="javascript:menuForward('3','11');">바로가기</a>
                                <p class="banner_con">온라인과정은 방송영상, 게임개발, 문화콘텐츠 관련 콘텐츠를 웹기반 교육시스템을 통해 시간과 공간의 제약없이 누구나 교육을 받을 수 있는 '열린'공간입니다.</p>
                            </div>
                        </div>
                         -->
                    </div>
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                        <div class="box_ct">
                            <span class="ct_title">공지사항</span>
                            <span class="btn_more float-right"><a href="javascript:menuForward('4','07');">더보기</a></span>

                            <div class="list_box">
                                <ul>
                                	<c:forEach items="${noticeList }" var="list" varStatus="sataus">
                                		<li>
                                			<a href="javascript:viewNotice('<c:out value="${list.d_seq }" />');">
                                				<span class="abbreviation"><c:out value="${list.d_adtitle }" /></span>
                                           		<span>
                                           			<fmt:parseDate value="${list.d_addate }" var="noticeDate" pattern="yyyyMMddHHmmss" />
                                           			<fmt:formatDate value="${noticeDate }" pattern="yyyy-MM-dd" />
                                           		</span>
                                			</a>
                                		</li>
                                	</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="col-xl-3 col-lg-4 col-md-6 col-sm-12">
                        <div class="box_ct swiper-container" style="padding:0" id="main_banner_slide">
                            <div class="swiper-wrapper">
                            	<c:choose>
									<c:when test="${sessionScope.tem_grcode eq 'N000134'}">
		                                <div>
		                                    <a href="/servlet/controller.homepage.HomeNoticeServlet">
		                                        <span style="display: none;">학습자 권한이 없습니다. 오류 발생 시 조치 사항 안내</span>
		                                        <img src="/common/image/banner4.png" title="학습자 권한이 없습니다. 오류 발생 시 조치 사항 안내" alt="학습자 권한이 없습니다. 오류 발생 시 조치 사항 안내">
		                                    </a>
		                                </div>
                            		</c:when>
	                                <c:otherwise>
	                            	 	<div class="swiper-slide">
		                                    <a href="#">
		                                        <span style="display: none;">융합형 인재양성의 허브 한국콘텐츠아카데미에서는 여려분의 다양한 꿈과 희망을 응원합니다.</span>
		                                        <img src="/common/image/banner1.jpg" title="융합형 인재양성의 허브 한국콘텐츠아카데미에서는 여려분의 다양한 꿈과 희망을 응원합니다." alt="융합형 인재양성의 허브 한국콘텐츠아카데미에서는 여려분의 다양한 꿈과 희망을 응원합니다.">
		                                    </a>
		                                </div>
		                                <div class="swiper-slide">
		                                    <a href="#">
		                                        <span style="display: none;">언제 어디서나 누구나 방송영상, 게임, 문화 콘텐츠 분야의 꿈을 펼칠실 분들을 위한 맞춤 온라인 강의</span>
		                                        <img src="/common/image/banner2.jpg" title="언제 어디서나 누구나 방송영상, 게임, 문화 콘텐츠 분야의 꿈을 펼칠실 분들을 위한 맞춤 온라인 강의" alt="언제 어디서나 누구나 방송영상, 게임, 문화 콘텐츠 분야의 꿈을 펼칠실 분들을 위한 맞춤 온라인 강의">
		                                    </a>
		                                </div>
                            	 	</c:otherwise>
                            	 </c:choose>
                            </div>
						<c:if test="${sessionScope.tem_grcode ne 'N000134'}">
							<div class="swiperControlBox">
							    <div class="col-12 swiper-pagination"></div>
							    <div class="swiper-button-prev"></div>
							    <div class="swiper-button-next"></div>
							    <div class="swiper-autoplay-stop"></div>
							</div>
						</c:if>
						
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
