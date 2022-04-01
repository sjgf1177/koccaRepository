<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>
<%@ page errorPage="/jsp/library/error.jsp"%>
<%@ page import="com.credu.library.DataBox"%>
<%@ page import="java.util.ArrayList"%>

<%@ include file="/mobile/jsp/include/init.jsp"%>

<%
	// 공지사항 목록
	ArrayList newsList = (ArrayList) request.getAttribute("newsList");
	DataBox dbox = null;

	String pageTitle = "아카데미소개";

	String titleTag = "";

	if (isApp) {
		titleTag = "아카데미소개";
	} else if (isWeb) {
		titleTag = "아카데미소개 | 한국콘텐츠아카데미";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<title><%=titleTag%></title>
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/json2.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script>
    var isApp = eval("<%= isApp %>");
	/**
	 * 화면 초기화
	 */
	$(document).ready(
			function() {
				$('.panel-category .panel-header a').click(
						function() {
							var panelBody = $(this).parent().parent().children(
									'.panel-body');

							if (panelBody.length > 0) {
								if (panelBody.is(':hidden')) {
									if ($(this).children('i').hasClass(
											'icon-ar-down')) {
										$('.panel-category .panel-header a i')
												.removeClass('icon-ar-up')
												.addClass('icon-ar-down');
										$(this).children('i').removeClass(
												'icon-ar-down').addClass(
												'icon-ar-up');
									}
									// $('.panel-body').hide();
									panelBody.show();
								} else {
									if ($(this).children('i').hasClass(
											'icon-ar-down')
											|| $(this).children('i').hasClass(
													'icon-ar-up')) {
										$('.panel-category .panel-header a i')
												.removeClass('icon-ar-up')
												.addClass('icon-ar-down');
									}
									panelBody.hide();
								}
								return false;
							} else {
								return true;
							}
						});

				$("#oMap img").attr({
					"width" : "",
					"height" : ""
				}).css({
					"max-width" : "100%"
				});

				$("#oPrVideo").attr({
					"width" : "",
					"height" : ""
				}).css({
					"max-width" : "100%"
				});
			});

	var currListCnt = 0;

	/**
	 * 보도자료 목록 더보기
	 */
	function fnViewMoreNewsList() {

		currListCnt = $(".article-list").children("li").length;

		var i = 0;

		$
				.ajax({
					type : "get",
					url : "/servlet/controller.mobile.information.InformationServlet",
					dataType : "json",
					data : "process=newsList&currListCnt=" + currListCnt,
					success : function(data) {

						var list = "";
						var totalListCnt = 0;
						var targetObj = $(".article-list");

						$
								.each(
										data.newsList,
										function() {

											list = "<li>\n";
											list += "   <a href=\"javascript:fnViewNewsDetail("
													+ this.d_seq + ");\">\n";
											list += "       <span class=\"thumb\">\n";
											list += "           <img src=\"" + this.d_savefile + "\" alt=\"\">\n";
											list += "       </span>\n";
											list += "       <span class=\"body\">\n";
											list += "           <span class=\"subject\">\n";
											list += "               "
													+ this.d_title + "\n";
											list += "           </span>\n";
											// list += "           <span class=\"text\">\n";
											// list += "               내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다\n";
											// list += "           </span>\n";
											list += "       </span>\n";
											list += "   </a>\n";
											list += "</li>\n";

											targetObj.append(list);
											totalListCnt = this.d_totcnt;
											i++;
										});

						currListCnt = targetObj.children("li").length;

						if (currListCnt == totalListCnt) {
							$(".btn-more").hide();
						}

					},
					complete : function(arg1, arg2) {
						// alert("result : " + arg2);
					},
					error : function(arg1, arg2) {
					}
				});
	}

	/**
	 * 보도자료 상세 화면 이동
	 */
	function fnViewNewsDetail(seq) {
		fnMoveNavi(
				"2",
				"/servlet/controller.mobile.information.NewsServlet?process=selectNewsDetail&seq="
						+ seq, "");
	}

	/**
	 * 홍보영상 보기
	 */
	function fnViewIntroMovie() {
		// $(".view-movie-frame").remove();

		// var movieStr = "";

		$.ajax({
			type : "get",
			url : "/mobile/jsp/information/intro_movie.json",
			dataType : "json",
			success : function(data) {
				// window.open("", "asdf", "");
				// location.target = "_blank";
				// location.href = data.movieURL;
				// movieStr = "<div class=\"view-movie-frame\">\n";
				// movieStr += "   <video autoplay loop poster=\"/images/portal/academy/pr_video_20140729.jpg\" width=\"240\" height=\"160\" id=\"oPrVideo\">\n";
				// movieStr += "       <source src=\"" + data.movieURL + "\" type=\"video/mp4\">\n";
				// movieStr += "   </video>\n";
				// movieStr += "</div>\n";

				// $("#oIntroMovie").append(movieStr);

				// $("#oPrVideo").play();

				var v = {
					"playVideo" : {
						"url" : data.movieURL
					}
				};

				if (isApp) {
					location.href = data.movieURL;
					// document.location = "newin:@" + JSON.stringify(v);
				} else {
					location.href = data.movieURL;
				}

				return;
			}
		});

	}
</script>
</head>
<body>

	<!-- header -->
	<%@ include file="/mobile/jsp/include/header.jsp"%>
	<!-- /header -->

	<%@ include file="/mobile/jsp/include/slidemenu.jsp"%>

	<div class="container">
		<!--<div class="panel panel-category">
        <div class="panel-header">
            <a href="#">아카데미 소개 <i class="icon icon-ar-down"></i></a>
        </div>
        <div class="panel-body panel-body-collapse panel-body-info">
        콘텐츠분야 인재양성의 중심에 있는 한국콘텐츠아카데미는 미래 콘텐츠 산업을 선도할 콘텐츠 분야
        핵심인재를 양성하고 있습니다. 차세대 인재 양성, 현업인 직무 재교육, 온라인 평생교육을 통해 콘텐츠
        강국으로의 발돋움에 힘을 보태고 있습니다.
        </div>
    </div>
    -->
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="#">진흥원장 인사말 <i class="icon icon-ar-down"></i></a>
			</div>
			<div class="panel-body panel-body-collapse panel-body-info">
				한국콘텐츠아카데미는 한국콘텐츠진흥원의 콘텐츠전문인력양성기관입니다. <br />
				<br /> 한국콘텐츠아카데미는 창의/융합/감성 등의 키워드를 핵심가치로 콘텐츠 산업 현장에서 필요한 융합형 창의인재를
				양성하기 위해 노력하고 있습니다. <br />
				<br /> 콘텐츠의 경쟁력은 창작자의 창의능력에서 비롯됩니다. 우수한 창작역량과 융합기술력을 갖춘 인재를 양성하기
				위해서는 체계적인 교육 커리큘럼과 교육인프라가 필요합니다. <br />
				<br /> 한국콘텐츠아카데미는 국내외 최고 강사진의 풍부한 교육 노하우, 국내 최고의 콘텐츠 제작교육 인프라를 바탕으로
				콘텐츠 산업 예비인력교육, 현업인 직무교육, 전략지역 해외연수 및 온라인 교육과정 등을 운영하고 있습니다. 이를 통해
				글로벌 인재 양성을 위한 콘텐츠 산업 공적 연수기관으로서의 역할과 기능 수행에 최선을 다하겠습니다. <br />
				<br /> 앞으로도 많은 관심과 성원 부탁드립니다.
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="#">교육사업 안내 <i class="icon icon-ar-down"></i></a>
			</div>
			<div class="panel-body panel-body-collapse panel-body-info">


				<h4 class="subTit_h4_gray">사이버콘텐츠아카데미 http://edu.kocca.kr</h4>
				<div class="academiSec gray" style="padding-bottom: 16px;">
					<ul>
						<li>콘텐츠산업 예비 인력 및 현업종사자, 한국콘텐츠아카데미 회원 대상의 정규과정과 열린강좌 서비스</li>
						<li>분야별 전문 튜터의 튜터링 서비스 제공</li>
						<li>전과정 무료 강의</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육 신청/선발</h4>
				<div class="academiSec gray" style="padding-bottom: 16px;">
					<ul>
						<li>신청방법 : 온라인 신청 / http://edu.kocca.kr</li>
						<li>신청기준 : 한국콘텐츠아카데미 회원, 신청 회원수 제한 없음</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육과정운영</h4>
				<div class="academiSec gray" style="padding-bottom: 16px;">
					<ul>
						<li>교육방법 : 학습자 개인 PC 또는 모바일에서 온라인 학습</li>
						<li>과정운영 : 상시학습 체제</li>
						<li>운영내용 : 방송영상 / 게임 / 문화콘텐츠 기획, 제작, 비즈니스 분야 등 운영</li>
						<li>학습기간 : 교육 시작일 기준 학습 1개월</li>
						<li>교육비용 : 무료</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육문의</h4>
				<div class="academiSec gray">
					<ul>
						<li>한국콘텐츠아카데미 온라인교육팀<br />Tel : 02-6310-0770
						</li>
					</ul>
				</div>

			</div>
		</div>

		<!-- 
    <div class="panel panel-category">
        <div class="panel-header">
            <a href="#">홍보영상 <i class="icon icon-ar-down"></i></a>
        </div>
        <div class="panel-body panel-body-collapse panel-body-info" id="oIntroMovie">
            <div class="view-movie-frame">
                <a href="javascript:fnViewIntroMovie();">
                    <img src="/images/portal/academy/pr_video_20140729.jpg" alt="">
                    <i class="icon-play"><i></i></i>
                </a>
            </div>
            
            <div class="view-movie-frame">
                <video autoplay loop poster="/images/portal/academy/pr_video_20140729.jpg" width="320" height="180" id="oPrVideo">
                    <source src="http://w.hunet.hscdn.com/hunet/M_b2b/kocca/mobile/pr/kocca_intro.mp4" type="video/mp4">
                </video>
            </div>
            //
        </div>
    </div>
     -->

		<%-- <div class="panel panel-category" id="oNewsPanel">
			<div class="panel-header">
				<a href="#">보도자료 <i class="icon icon-ar-down"></i></a>
			</div>
			<div class="panel-body panel-body-collapse panel-body-info">
				<ul class="article-list">
					<%
						if (newsList.size() > 0) {
							for (int i = 0; i < newsList.size(); i++) {
								dbox = (DataBox) newsList.get(i);
					%>
					<li><a
						href="javascript:fnViewNewsDetail(<%=dbox.getInt("d_seq")%>);">
							<span class="thumb"> <img
								src="<%=dbox.getString("d_savefile")%>" alt="">
						</span> <span class="body"> <span class="subject"> <%=dbox.getString("d_title")%>
							</span> <!--
                            <span class="text">
                                내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다 내용 입니다
                            </span>
                            //-->
						</span>
					</a></li>
					<%
						}
						} else {
					%>
					<li class="no-data">등록된 내용이 없습니다.</li>
					<%
						}
					%>
				</ul>
				<%
					if (newsList.size() > 0) {
				%>
				<a href="javascript:fnViewMoreNewsList();" class="btn-more">더보기
					<i class="icon icon-cir-plus"></i>
				</a>
				<%
					}
				%>
			</div>
		</div> --%>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="#">오시는길 <i class="icon icon-ar-down"></i></a>
			</div>
			<div class="panel-body panel-body-collapse panel-body-info" id="oMap">
				<div
					style="font: 400 12px/normal dotum, sans-serif; width: 98%; color: rgb(51, 51, 51); position: relative; font-size-adjust: none; font-stretch: normal;">
					<div>
						<a
							href="http://map.daum.net/?urlX=509811.25&amp;urlY=1136370.0&amp;name=%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C%20%EB%8F%99%EB%8C%80%EB%AC%B8%EA%B5%AC%20%ED%9A%8C%EA%B8%B0%EB%A1%9C%2066&amp;map_type=TYPE_MAP&amp;from=roughmap"
							target="_blank"><img width="598" height="298" class="map"
							style="border: 1px solid rgb(204, 204, 204); border-image: none;"
							src="http://cfile186.uf.daum.net/image/2354A25058DC89B728B2BF"></a>
					</div>
				</div>

			</div>
		</div>
		<%
			if (isApp) {
		%>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="javascript:fnShowBrowser('http://facebook.com/edukocca');"
					target="_blank">페이스북</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="javascript:fnShowBrowser('http://twitter.com/edukocca');"
					target="_blank">트위터</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="javascript:fnShowBrowser('https://blog.naver.com/edukocca');"
					target="_blank">블로그</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a
					href="javascript:fnShowBrowser('http://www.youtube.com/user/ContentKoreaLab');"
					target="_blank">You Tube</a>
			</div>
		</div>
		<%
			} else {
		%>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="http://facebook.com/edukocca" target="_blank">페이스북</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="http://twitter.com/edukocca" target="_blank">트위터</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="https://blog.naver.com/edukocca" target="_blank">블로그</a>
			</div>
		</div>
		<div class="panel panel-category">
			<div class="panel-header">
				<a href="https://www.youtube.com/channel/UCJ78W_fNjOW7A-lZ6uEClgA"
					target="_blank">You Tube</a>
			</div>
		</div>
		<%
			}
		%>
	</div>

</body>
</html>