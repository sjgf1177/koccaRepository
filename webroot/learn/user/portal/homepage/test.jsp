
<%@ page contentType = "text/html;charset=euc-kr" %>














<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>한국콘텐츠아카데미</title>

<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />


<link rel="stylesheet" href="/css/portal/asp/newasp_A.css" type="text/css" />

<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script language="javascript" src="/script/mainscript.js"></script>
<script language="VBScript" src="/script/cresys_lib.vbs"></script>

</head>
<body id="asp" class="asp_sub" onload="MM_preloadImages('/images/newmain_20100625/top_m1_o.gif','/images/newmain_20100625/top_m2_o.gif','/images/newmain_20100625/top_m3_o.gif','/images/newmain_20100625/top_m4_o.gif','/images/newmain_20100625/top_m5_o.gif','/images/newmain_20100625/center_faq_o.gif','/images/newmain_20100625/center_down_o.gif','/images/newmain_20100625/center_book_o.gif','/images/newmain_20100625/center_qna_o.gif','/images/newmain_20100625/search_btn_o.gif','/images/newmain_20100625/cont_theme_o.gif','/images/newmain_20100625/cont_colum_o.gif','/images/newmain_20100625/btn_login_o.gif','/images/newmain_20100625/btn_myroom_o.gif','/images/newmain_20100625/btn_apply_o.gif','/images/newmain_20100625/quickm1_o.gif','/images/newmain_20100625/quickm2_o.gif','/images/newmain_20100625/quickm3_o.gif','/images/newmain_20100625/quickm4_o.gif')">
	<!-- accessibility_menu -->
	<ul id="accessibility_menu">
		<li><a href="#content">본문으로 넘어가기</a></li>
		<li><a href="#header">대 메뉴로 넘어가기</a></li>
		<li><a href="#banner">텍스트베너로 넘어가기</a></li>
		<li><a href="#footer">카피라이터로 넘어가기</a></li>
	</ul>
	<!--// accessibility_menu -->
	<hr />



	<div id="wrap">

		<div id="header">


			<!-- header -->



			<!--오버시 나타나는 메뉴 -->
			<div class="overmenu" style="display: none;">
				<div class="overmenu_cnt">
					<div class="overmenu_margin">
						<ul style="border-left: 1px solid #e1e1e1">
							<li><a href="javascript:menuForward('8', '01');">교육과정안내</a></li>
							<li><a href="javascript:menuForward('8', '02');">월별교육일정</a></li>
						</ul>
						<ul>
							<li><a href="javascript:menuForward('9', '01');">수강신청안내</a></li>
							<li><a href="javascript:menuForward('9', '02');">수강신청</a></li>
							<li><a href="javascript:menuForward('9', '03');">수강신청확인/취소</a></li>
						</ul>
						<ul>
							<li></li>
						</ul>
						<ul>
							<li><a href="javascript:menuForward('3', '11');">수강중인과정</a></li>
							<li><a href="javascript:menuForward('3', '02');">나의교육자료</a></li>
							<li><a href="javascript:menuForward('3', '12');">나의상담내역</a></li>
						</ul>
						<ul>
							<li><a href="javascript:menuForward('4', '07');">공지사항</a></li>
							<li><a href="javascript:menuForward('4', '01');">FAQ</a></li>
							<li><a href="javascript:menuForward('4', '02');">Q&amp;A</a></li>
							<li><a href="javascript:menuForward('4', '03');">S/W다운로드</a></li>
							<li><a href="javascript:menuForward('4', '05');">학습환경도우미</a></li>
						</ul>
					</div>
				</div>
			</div>
			<!--//오버시 나타나는 메뉴 -->

			<div class="header_topmenu">
				<div class="header_topmenu_mid">
					<p style="float: right;">
						<span>
							<!--회원가입 -->
							<img src="/images/portal/asp/typeA/main/topmenu_01.jpg" />
						</span> <span>
							<!--아이디패스워드찾기 -->
							<img src="/images/portal/asp/typeA/main/topmenu_02.jpg" />
						</span>
					</p>
					<div class="mainmenu">
						<h1>
							<a href="/servlet/controller.homepage.MainServlet"><img src="/images/newmain_20100625/N000057.gif" alt="logo" /></a>
						</h1>
						<!--상단메뉴 -->
						<ul>
							<li style="color: #353333"><a href="javascript:menuForward('8', '01');">과정안내</a></li>
							<li style="color: #353333"><a href="javascript:menuForward('9', '01');">수강신청</a></li>
							<li style="color: #353333"><a href="javascript:menuForward('5', '01');">열린강좌</a></li>
							<li style="color: #00aebc"><a href="javascript:menuForward('3', '11');">나의강의실</a></li>
							<li style="color: #00aebc"><a href="javascript:menuForward('4', '07');">학습지원센터</a></li>
						</ul>
						<!--//상단메뉴 -->

					</div>
					<div class="searchzone">
						<ul>
							<li style="float: left; width: 60px; font-size: 14px; border-right: 1px solid #717070; font-weight: 600; line-height: 11px; margin-top: 6px;">과정검색</li>
							<li style="float: left; width: 100px;"><input type="text" id="topWarp_searchText" name="topWarp_searchText" title="과정검색" style="width: 97px; height: 21px; border: none; margin-left: 3px;" onkeypress="javascript:fnKeyPressEnter(event, 'totalSubjSearch');" /></li>
							<li style="width: 25px; float: left;"><img src="/images/portal/asp/typeA/main/icon_search.jpg" style="cursor: pointer" onclick="totalSubjSearch();" /></li>
						</ul>
					</div>
				</div>
			</div>


			<!-- header -->


		</div>


		<!-- Top Visual 시작 -->





		<div class="sub_img">
			<div class="sub_text">

				<p class="sub_text_title">
					<span style="font-size: 15px; font-weight: 600; line-height: 36px;">콘텐츠아카데미</span><span style="font-size: 28px; font-weight: 600; margin-left: 5px; letter-spacing: -4px">온라인강좌</span>
				</p>
				<p class="sub_text_explain">
					온라인과정은 방송영상, 게임개발, 문화콘텐츠 관련 콘텐츠를 웹기반 교육시스템을 통해<br>시간과 공간의 제약없이 누구나 교육을 받을 수 있는 '열린'공간입니다.
				</p>

			</div>
		</div>
		<!-- Top Visual 종료 -->





		<!--// header -->
		<hr />
		<!-- container -->
		<div id="container">
			<div id="contentWrap">
				<!-- 서브메뉴영역 -->

				<div id="snb">
					<dl>

						<dt>
							<a href="javascript:menuMainForward('4', '/servlet/controller.homepage.SubMenuMainServlet?p_process=HELPDESK');"><img src="/images/portal/menu/4/snb_tit4.gif" alt="학습지원센터" /></a>
						</dt>

						<!-- <dd><a href="javascript:menuForward('4', '07');"><img src="/images/portal/menu/4/snb_stit1_on.gif" alt="공지사항" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '07');">공지사항</a>
						</dd>


						<!-- <dd><a href="javascript:menuForward('4', '01');"><img src="/images/portal/menu/4/snb_stit2_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="FAQ" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '01');">FAQ</a>
						</dd>


						<!-- <dd><a href="javascript:menuForward('4', '02');"><img src="/images/portal/menu/4/snb_stit3_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="QNA" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '02');">QNA</a>
						</dd>


						<!-- <dd><a href="javascript:menuForward('4', '03');"><img src="/images/portal/menu/4/menu_4_03_main_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="S/W다운로드" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '03');">S/W다운로드</a>
						</dd>


						<!-- <dd><a href="javascript:menuForward('4', '05');"><img src="/images/portal/menu/4/menu_4_05_main_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="학습환경도우미" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '05');">학습환경도우미</a>
						</dd>


						<!-- <dd><a href="javascript:menuForward('4', '09');"><img src="/images/portal/menu/4/menu_4_09_main_off.gif" onmouseover="imgover(this);" onmouseout="imgout(this);" alt="1:1문의" onblur="imgout(this);" /></a></dd> //-->
						<dd style="width: 120px; line-height: 12pt; padding-left: 4px; font-weight: bold; font-size: 10pt;">
							<a href="javascript:menuForward('4', '09');">1:1문의</a>
						</dd>


					</dl>

					<table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
							<td height="23">&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table width="200" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td colspan="2"><img src="/images/portal/homepage_renewal/common/txt_study_info.jpg" alt="?Ð½???¿ø¾?³?" width="200" height="28" /></td>
									</tr>
									<tr>
										<td><a href="javascript:menuForward(4,'01');"><img src="/images/portal/homepage_renewal/common/icon_01.jpg" alt="FAQ" /></a></td>
										<td><a href="javascript:menuForward(4,'02');"><img src="/images/portal/homepage_renewal/common/icon_02.jpg" alt="Q&amp;A" /></a></td>
									</tr>
									<tr>
										<td><a href="javascript:menuForward(4,'03');"><img src="/images/portal/homepage_renewal/common/icon_03.jpg" alt="S/W´?¿?·???" /></a></td>
										<td><a href="javascript:menuForward(4,'05');"><img src="/images/portal/homepage_renewal/common/icon_04.jpg" alt="?Ð½???°æ??¿?¹?" /></a></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td><img src="/images/portal/homepage_renewal/common/bn_ask.jpg" /></td>
						</tr>
					</table>

				</div>
				<div id="snb2"></div>

				<!-- 서브메뉴영역종료 -->



				<!-- 콘텐츠 영역 시작 -->
				<div id="content" style="margin-top: -9px;">
					<!-- Common 영역종료 -->

					<div id="ajaxDiv"></div>
					<!-- 스크립트영역종료 -->

					<!-- Form 영역 시작 -->
					<form name="nform1" method="post" onsubmit="javascript:select();">
						<input type="hidden" name="p_process"> <input type="hidden" name="p_pageno" value="0"> <input type="hidden" name="p_seq"> <input type="hidden" name="p_pagesize" value="0" />



						<h2>
							<img src="/images/portal/studying/h2_tit1.gif" alt="공지사항" class="fl_l" />
							<p class="category">
								Home > 학습지원센터 > <strong>공지사항</strong>
							</p>
						</h2>

						<p>
							<img src="/images/portal/studying/notice_borimg.gif" alt="학습지원센터에 오신것을 환영합니다.콘텐츠진흥원에서 알려드리는 새소식과 이벤트를 확인하실 수 있습니다." />
						</p>

						<p class="board_serach">
							<select name="p_search">
								<option value="adtitle">제목</option>
								<option value="adcontent">내용</option>
								<option value="adname">작성자</option>
							</select> <input type="text" name="p_searchtext" class="inbox" style="width: 135px;" value="" onkeypress="javascript:fnKeyPressEnter(event, 'select');" /> <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" />
						</p>

						<table class="board_list">
							<colgroup>
								<col width="63px" />
								<col width="460px" />
								<col width="106px" />
								<col width="62px" />
							</colgroup>
							<thead>
								<tr>
									<th><p>번호</p></th>
									<th><p>제목</p></th>
									<th><p>작성일자</p></th>
									<th class="end"><p>조회수</p></th>
								</tr>
							</thead>
							<tbody>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">6</td>
									<td class="ta_l"><a href="javascript:selectView(2028)"> 공지공지공지공지공지공지공지공지공지공지공지공지 </a></td>
									<td class="num">2016.11.16</td>
									<td class="num">9</td>
								</tr>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">5</td>
									<td class="ta_l"><a href="javascript:selectView(2027)"> test4 </a></td>
									<td class="num">2016.11.16</td>
									<td class="num">2</td>
								</tr>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">4</td>
									<td class="ta_l"><a href="javascript:selectView(2026)"> test34 </a></td>
									<td class="num">2016.11.16</td>
									<td class="num">1</td>
								</tr>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">3</td>
									<td class="ta_l"><a href="javascript:selectView(2025)"> test2 </a></td>
									<td class="num">2016.11.16</td>
									<td class="num">0</td>
								</tr>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">2</td>
									<td class="ta_l"><a href="javascript:selectView(2024)"> test1 </a></td>
									<td class="num">2016.11.16</td>
									<td class="num">0</td>
								</tr>

								<tr onmouseover="className='over'" onmouseout="className=' '">
									<td class="num">1</td>
									<td class="ta_l"><a href="javascript:selectView(2023)"> 테스트 공지사항입니다. 공지공지공지 공공 지지 </a></td>
									<td class="num">2016.11.14</td>
									<td class="num">2</td>
								</tr>

							</tbody>
						</table>

						<!-- 페이징 영역 시작 -->
						<div class="pagination">
							이전 <strong>1</strong>&nbsp;다음
						</div>
						<p class="bo_warp"></p>
						<!-- 페이징 영역 종료 -->


					</form>
					<!-- Form 영역 종료 -->


					<!-- footer 영역 시작 -->
				</div>
			</div>
			<div id="allmenu" style="display: none;"></div>
		</div>
		<!--// container -->
		<hr />










		<div id="footer">
			<div class="copyright">
				<div class="copyright_left">
					<h3 style="width: 109px; height: 75px; float: left;">
						<!--하단 카피라이트 빈 로고 -->
						<img src="/images/portal/asp/typeA/main/footer_logo.jpg" />
					</h3>
					<ul>
						<li style="color: #fff;"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_PRIVACY')">개인정보처리방침</a></li>
						<li><a href="javascript:menuForward(7,'01');">아카데미 소개</a></li>
						<li><a href="javascript:menuForward(4,'01');">자주묻는 질문</a></li>
						<li style="border: none;"><a href="javascript:menuMainForward('99', '/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL')">이메일무단수집거부</a></li>
					</ul>
					<p style="float: left; margin-left: 10px; margin-top: 20px; color: #cecece; line-height: 16px; font-size: 11px;">
						한국콘텐츠아카데미 | 주소 : 서울시 중구 청계천로 40<br />온라인 교육 02-6441-3258 / 현장교육 02-6441-3254
					</p>
				</div>
				<div class="gray_right">
					<p style="margin-top: 40px;">
						<span style="color: #cecece; margin-right: 5px;">Family site</span> <select name="fsite" style="color: #626262; font-size: 11px; height: 20px;">
							<option value>관련사이트 바로가기</option>

						</select>
					</p>
				</div>
			</div>
		</div>
	</div>


</body>
</html>
<!-- footer 영역 종료 -->

