<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ include file ="../layout/head.jsp" %>
<body>
<!-- accessibility_menu -->
<ul id="accessibility_menu">
	<li><a href="#content">본문으로 넘어가기</a></li>
	<li><a href="#header">대 메뉴로 넘어가기</a></li>
	<li><a href="#footer">카피라이터로 넘어가기</a></li>
</ul>
<!--// accessibility_menu -->
<hr />

<!-- header -->
<div id="header">
	<div class="topwarp">
		<h1><img src="/images/potal/learn/user/portal/onlinecommon/logo.gif" alt="KOCCA" /></h1>

		<dl class="homemenu">
			<dt>홍길동님 반갑습니다</dt>
			<dd><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/topmenu1.gif" alt="로그인" /></a></dd>
			<dd><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/topmenu2.gif" alt="회원가입" /></a></dd>
			<dd><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/topmenu3.gif" alt="사이트맵" /></a></dd>
		</dl>

		<div class="class_search">
			<a href="#" class="tit"><img src="/images/potal/learn/user/portal/onlinecommon/search_tit1_off.gif" alt="온라인 과정" /></a>
			<div id="search_tit" style="display:none;">
				<a href="#" class="tit"><img src="/images/potal/learn/user/portal/onlinecommon/search_tit2_on.gif" alt="오프라인 과정" /></a>
			</div>
			<input type="text" />
			<a href="#" class="btn"><img src="/images/potal/learn/user/portal/onlinebtn/btn_search.gif" alt="검색" /></a>
		</div>
	</div>

	<ul class="gnb">
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_on.gif" alt="온라인과정" /></a>
			<div id="gnb_sub">
				<ul class="sub1">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu2_off.gif" alt="오프라인과정" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub2">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu3_off.gif" alt="나의강의실" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub3">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu4_off.gif" alt="학습지원센터" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub4">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu5_off.gif" alt="정보광장" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub5">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu6_off.gif" alt="커뮤니티" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub6">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu7_off.gif" alt="아카데미소개" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a>
			<div id="gnb_sub">
				<ul class="sub7">
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_1_on.gif" alt="게임개발" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_2_off.gif" alt="문화콘텐츠" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_3_off.gif" alt="교육과정안내" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_4_off.gif" alt="연간교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_5_off.gif" alt="월별교육일정" /></a></li>
					<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/menu1_6_off.gif" alt="수강신청안내" /></a></li>
				</ul>
			</div>
		</li>
		<li class="allmenu"><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/allmenu.gif" alt="전체메뉴" /></a></li>
	</ul>

	<dl class="online_visual">
		<dt><img src="/images/potal/learn/user/portal/onlinecommon/onlin_visual_tit.gif" alt="콘텐츠 아카데미 온라인과정" /></dt>
		<dd><img src="/images/potal/learn/user/portal/onlinecommon/onlin_visual_txt.gif" alt="온라인과정은 웹기반의 첨단 방송교육시스템으로 시간과 공간의 제약없이 누구나 방송교육을 받을 수 있는 '열린' 교육공간입니다." /></dd>
	</dl>
</div>
<!--// header -->
<hr />

<!-- container -->
<div id="container">
	<div id="contentWrap">
		<dl id="snb">
			<dt><img src="/images/potal/learn/user/portal/onlinecommon/snb_tit1.gif" alt="온라인과정" /></dt>
			<dd><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/snb_stit1_on.gif" alt="방송영상" /></a></dd>
		</dl>

		<div id="content">
			<h2><img src="/images/potal/learn/user/portal/onlineonline/h2_tit1.gif" alt="방송영상" class="fl_l" /><p class="category">Home > 온라인과정 > <strong>방송영상</strong></p></h2>

			<p class="board_comment"><strong>[안내]</strong> 과정들은  KOCCA 인증 수료증 발급과정, 인기과정, 신규과정, 추천과정으로 구분됩니다.</p>

			<p class="searchbox">
				<select class="mg_r12">
					<option>과정특성별 보기</option>
				</select>
				<img src="/images/potal/learn/user/portal/onlineboard/serbox_classtit.gif" alt="과정검색" />
				<input type="text" class="inbox" style="width:285px;" />
				<input type="image" src="/images/potal/learn/user/portal/onlinebtn/btn_serbox_search.gif" class="va_m mg_r12" alt="검색" />
				<select>
					<option>과정 10개씩</option>
				</select>
			</p>

			<table class="row_list">
			<thead>
			<tr>
				<th>분야 <a href=""><img src="/images/potal/learn/user/portal/onlinebtn/btn_select_down.gif" class="btn_down" /></a>
					<div id="pulldown" style="display:none; width:100px; left:20px; top:185px;">
						<dl class="sp_select">
							<dt>분야선택</dt>
							<dd>
								<ul class="list">
									<li><a href="">전체</a></li>
									<li><a href="">기획/구성</a></li>
									<li><a href="">촬영</a></li>
									<li><a href="">편집</a></li>
									<li><a href="">음향</a></li>
									<li><a href="">조명</a></li>
									<li><a href="">그래픽</a></li>
									<li><a href="">진행</a></li>
									<li><a href="">1인제작</a></li>
									<li><a href="">유통</a></li>
									<li><a href="">미디어교육</a></li>
									<li><a href="">정책/공익</a></li>
									<li><a href="">인증교육</a></li>
								</ul>
							</dd>
						</dl>
					</div>
				</th>
				<th>난이도 <a href=""><img src="/images/potal/learn/user/portal/onlinebtn/btn_select_down.gif" class="btn_down" /></a></th>
				<th>과정명</th>
				<th>교육기간 <a href=""><img src="/images/potal/learn/user/portal/onlinebtn/btn_select_down.gif" class="btn_down" /></a><br /><span class="stit">(수강신청기간)</span></th>
				<th>수강신청 <a href=""><img src="/images/potal/learn/user/portal/onlinebtn/btn_select_down.gif" class="btn_down" /></a></th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">촬영</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">편집</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end"><a href="" class="board_btn"><span>맛보기</span></a><br /><a href="" class="btn_gr mg_t3"><span>신청</span></a></td>
			</tr>
			<tr>
				<td class="ta_l">기획/구성</td>
				<td>초급</td>
				<td class="ta_l"><img src="/images/potal/learn/user/portal/onlineico/ico_box_witness.gif" alt="인증" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_popular.gif" alt="인기" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_new.gif" alt="신규" /> <img src="/images/potal/learn/user/portal/onlineico/ico_box_recom.gif" alt="추천" /><br />정재형의 영화로 보는 영상미학<br /><span class="slist">수강료: 30,000원</span><span class="slist">교육정원: 9명</span></td>
				<td class="data">2009.11.16~2009.12.25<br /><span class="sdata">2009.11.16~2009.12.25</span></td>
				<td class="end stxt">마감</td>
			</tr>
			</tbody>
			</table>

			<div class="pagination">
				<a href="#" class="prev">이전</a>
				<strong>1</strong> 
				<a href="#">2</a>
				<a href="#">3</a>
				<a href="#">4</a>
				<a href="#">5</a>
				<a href="#">6</a>
				<a href="#">7</a>
				<a href="#">8</a>
				<a href="#">9</a>
				<a href="#" class="next">다음</a>
			</div>

			<p class="bo_warp">
				<select class="fl_l">
					<option>과정특성별 보기</option>
				</select>
				<select class="fl_r">
					<option>과정 10개씩</option>
				</select>
			</p>

			<br /><br /><br />

			<h3><img src="/images/potal/learn/user/portal/onlineonline/stit_corseintro.gif" alt="과정소개" class="tit" /> <p class="btn"><a href="" class="btn_ac1"><span>수강신청</span></a><a href="" class="btn_gr"><span>강좌 맛보기</span></a><a href="" class="btn_gr"><span>관심과정담기</span></a><a href="" class="btn_gr"><span>목록</span></a></p></h3>

			<table class="view">
			<colgroup><col width="20%" /><col width="35%" /><col width="15%" /><col width="30%" /></colgroup>
			<tr>
				<th>과정명</th>
				<td colspan="3" class="end">아비드 미디어 컴포유저</td>
			</tr>
			<tr>
				<th>분야</th>
				<td>기획/구성 <a href="" class="board_btn"><span class="arrow">관련과정보기 <img src="/images/potal/learn/user/portal/onlineico/ico_btn_arrow.gif" alt="" class="va_2" /></span></a></td>
				<th>난이도</th>
				<td class="end">중급</td>
			</tr>
			<tr>
				<th>교수</th>
				<td colspan="3" class="end">
					<dl class="professor">
						<dt><img src="/images/potal/learn/user/portal/onlinethumb/photo_professor.jpg" alt="교수사진" /></dt>
						<dd class="mg_t6"><span class="tit">이름 :</span><span class="con">김용규 </span></dd>
						<dd><span class="tit">소속 :</span><span class="con">MBC플러스</span></dd>
						<dd><span class="tit">소개 :</span><span class="con">MBC플러스 조명감독 <br />
							서울산업대학교 산업대학원 매체공학(공학석사) <br />
							서울산업대학교 IT정책전문대학원 방송통신정책(공학박사수료) <br />
							서울산업대학교 IT정책전문대학원 방송통신정책(공학박사수료)</span></dd>
					</dl>
					<a href="" class="board_btn fl_r"><span class="arrow">교수전체보기 <img src="/images/potal/learn/user/portal/onlineico/ico_btn_arrow.gif" alt="" class="va_2" /></span></a>
				</td>
			</tr>
			<tr>
				<th class="stit">과정유형</th>
				<td>인증교육과정</td>
				<th>수강료</th>
				<td class="end">중급</td>
			</tr>
			<tr>
				<th class="stit">교육대상</th>
				<td colspan="3" class="end">현업/일반인</td>
			</tr>
			<tr>
				<th class="stit">교육목적</th>
				<td colspan="3" class="end">비선형 영상편집 시스템 Avid Media Composer의 특징 및 새로운 기능 습득</td>
			</tr>
			<tr>
				<th class="stit">교육내용</th>
				<td colspan="3" class="end">게임애니메이션의 기본적인 이해와 좋은 게임 애니메이션 기획을 위한 준비와 관련된 내용</td>
			</tr>
			<tr>
				<th class="stit">교재/참고문헌</th>
				<td colspan="3" class="end">없음</td>
			</tr>
			<tr>
				<th class="stit">선수과목</th>
				<td colspan="3" class="end">선수과목이 없습니다. </td>
			</tr>
			<tr>
				<th class="stit">후수과목</th>
				<td colspan="3" class="end">후수과목이 없습니다. </td>
			</tr>
			</table>

			<br /><br /><br />

			<h3><img src="/images/potal/learn/user/portal/onlineonline/stit_corseintro.gif" alt="과정소개" /></h3>
			<table class="list">
			<thead>
			<tr>
				<th class="ta_l">왼쪽정렬</th>
				<th>타이틀 가운데 정렬</th>
				<th class="ta_r end">오른쪽 정렬</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td class="ta_l">왼쪽 데이타</td>
				<td>가운데 정렬 데이타</td>
				<td class="ta_r end">오른쪽 데이타</td>
			</tr>
			<tr>
				<td class="ta_l">왼쪽 데이타</td>
				<td>가운데 정렬 데이타</td>
				<td class="ta_r end">오른쪽 데이타</td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
</div>
<!--// container -->
<hr />

<!-- footer -->
<div id="footer">
	<div class="footer">
		<p class="logo"><img src="/images/potal/learn/user/portal/onlinecommon/footer_logo.gif" alt="kocca" /></p>

		<div class="copywarp">
			<ul>
				<li class="fir"><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu3.gif" alt="개인정보 처리방침" /></a></li>
				<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu1.gif" alt="아카데미소개" /></a></li>
				<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu2.gif" alt="자주묻는질문" /></a></li>	
				<!--<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu4.gif" alt="이용약관" /></a></li>-->							
				<li><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu5.gif" alt="이메일무단수집거부" /></a></li>
				<li class="end"><a href=""><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu6.gif" alt="1:1문의" /></a></li>
			</ul>
			<p><span class="copyright"><img src="/images/potal/learn/user/portal/onlinecommon/footer_add.gif" alt="서울시 양천구 목동동로 203-1 방송회관 8층 대표전화 : 02-3219-6534 팩스 : 02-2161-0078 | 사업자등록번호: 117-82-04412" class="mg_b4" /><br /><img src="/images/potal/learn/user/portal/onlinecommon/footer_copyright.gif" alt="copyright &copy; 2001~2010  한국콘텐츠아카데미 (목동) 원장 이재웅 All Right Reserved" /></span><a href="" class="familysite"><img src="/images/potal/learn/user/portal/onlinecommon/footer_menu_familysite.gif" alt="Family Site" /></a></p>
		</div>
	</div>
</div>
<!--// footer -->
</body>
</html>