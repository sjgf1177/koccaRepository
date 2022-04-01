<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String  v_process  = box.getString("p_process");

	String s_userid   = box.getSession("userid");
	String s_username = box.getSession("name");

	HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>

	upperMap   = ClassifySubjectBean.getMenuId(box);
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>

<script language="javascript">

	function selectArea( val ) {
		document.form1.tabid.value = val;
		document.form1.p_process.value = "subjectallinformation";
		document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
		document.form1.submit();
	}

</script>

<form name="form1" method="post" id="toptop">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">
    <input type = "hidden" name = "tabid" value="<%= box.get("tabid") %>">

    <ul class="tabwrap">
		<li><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><span>과정안내</span></a></li>
		<li><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") ? " tab_online_on" : "" %>"><span>문화콘텐츠</span></a></li>
		<li><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><span>게임콘텐츠</span></a></li>
		<li><a href="javascript:selectArea('4');" class="tab_online<%= box.get("tabid").equals("4") ? " tab_online_on" : "" %>"><span>방송콘텐츠</span></a></li>
	</ul>

	<table class="row_list">
        <tbody>
          <tr></tr>
        </tbody>
        <colgroup>
        <col width="100%" />
        </colgroup>

      </table>

<table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20px">&nbsp;</td>
  </tr>
  <tr>
    <td height="25">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <td>
                    <strong>&lt;2011년 교육운영 계획 및 커리큘럼&gt;</strong>
                </td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
    <td><table class="row_list">
      <tbody>
        <tr></tr>
      </tbody>
      <colgroup>
        <col width="5%" />
        <col width="7%" />
        <col width="11%" />
        <col width="13%" />
        <col width="14%" />
        <col width="" />
        <col width="8%" />
        <col width="8%" />
        </colgroup>
      <thead>
        <tr>
          <th>No </th>
          <th>구분</th>
          <th>대분류</th>
          <th>중분류</th>
          <th>소분류</th>
          <th>과정명</th>
          <th>교육비</th>
          <th style="display:none">제작<br />
            연도</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="ta_l fs_s lp_1">1</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">[입문] 문화콘텐츠 비즈니스 창업론</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 리더십 및 관리</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 비즈니스 창업론</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">공연기획과 홍보마케팅 실무</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">예술경영의 실무</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">대중문화</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">-</td>
          <td class="ta_l">대중문화예술인의 이해</td>
          <td class="data">무료</td>
          <td class="endend">2010</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">-</td>
          <td class="ta_l">간단하게 배우는 문화콘텐츠 법률</td>
          <td class="data">15,000</td>
          <td class="endend">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">-</td>
          <td class="ta_l">디자인과 상표(저작권심화)</td>
          <td class="data">15,000</td>
          <td class="endend">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">-</td>
          <td class="ta_l">저작권(저작권기본)</td>
          <td class="data">15,000</td>
          <td class="endend">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">대중문화예술인과 인권</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">법률</td>
          <td class="ta_l">대중문화예술인의 법적권리</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠산업</td>
          <td class="ta_l">대중문화분석</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠산업</td>
          <td class="ta_l">문화콘텐츠론</td>
          <td class="data">15,000</td>
          <td class="endend">2004</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">14</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠산업</td>
          <td class="ta_l">문화콘텐츠연출론</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">15</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">문화콘텐츠와 뉴미디어</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">16</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">세계의 애니메이션 역사와 사례</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">17</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">시간 속의 만화와 캐릭터</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">18</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">재미있는 광고 읽기</td>
          <td class="data">무료</td>
          <td class="endend">20</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">19</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">재미있는 디자인 읽기</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">20</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">재미있는 영화 읽기</td>
          <td class="data">무료</td>
          <td class="endend">209</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">21</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의 콘텐츠</td>
          <td class="ta_l">재미있는 TV 읽기</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">22</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠 입문</td>
          <td class="ta_l">[입문] 모바일 콘텐츠 전략</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">23</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠 입문</td>
          <td class="ta_l">[입문] 문화콘텐츠 해외마케팅</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">24</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠 입문</td>
          <td class="ta_l">[입문] 문화콘텐츠와 스토리디자인</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">25</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠 입문</td>
          <td class="ta_l">[입문] 유비쿼터스사회와 문화콘텐츠</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">26</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">스마트폰과 문화</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">27</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">쉽게 배우는 전시기획</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">28</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">대중음악콘텐츠기획과 해외진출</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">29</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">축제기획과 문화콘텐츠</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">30</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">콘텐츠서비스운영전략</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">31</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">OSMU를 위한 창조적 발상</td>
          <td class="data">15,000</td>
          <td class="endend">2004</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">32</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">문화콘텐츠 디렉터론</td>
          <td class="data">문화</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">33</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">문화콘텐츠 상품기획</td>
          <td class="data">15,000</td>
          <td class="endend">2004</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">34</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">문화콘텐츠 크리에이터</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">35</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">문화콘텐츠 프로듀싱</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">36</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠 기획</td>
          <td class="ta_l">문화콘텐츠의 투자경제성평가</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">37</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">문화콘텐츠 성공사례</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">38</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">해외사례로 본 문화와 캐릭터</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">39</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">감독과 배우가 이야기하는 나의 영화인생</td>
          <td class="data">무료</td>
          <td class="endend">-</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">40</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">문화계 거장들의 인생 이야기</td>
          <td class="data">무료</td>
          <td class="endend">-</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">41</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">미국 영화</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">42</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">미디어와 뮤직비즈니스</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">43</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">아시아 영화</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">44</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">에듀테인먼트 사례</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">45</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">영화산업의 사례연구</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">46</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">음악산업의 사례연구</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">47</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">전문가들의 명사특강</td>
          <td class="data">무료</td>
          <td class="endend">-</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">48</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠사례연구</td>
          <td class="ta_l">프로듀서가 이야기하는<br/>우리 프로그램 이렇게 만들었다</td>
          <td class="data">무료</td>
          <td class="endend">-</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">49</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">11가지 법칙으로 끝내는 스토리텔링</td>
          <td class="data">유료</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">50</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">디지털스토리텔링</td>
          <td class="data">유료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">51</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">문화콘텐츠 스토리텔링 사례</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">52</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">스토리텔링 개론</td>
          <td class="data">유료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">53</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">스토리텔링의 이해</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">54</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">헐리우드 애니메이션 스토리텔링 전략</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">55</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">기술로 본 모바일 콘텐츠</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">56</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">문화콘텐츠 기술론</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">57</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">사례로 알아보는 CT</td>
          <td class="data">15,000</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">58</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">이머징테크날로지와 문화콘텐츠</td>
          <td class="data">무료</td>
          <td class="endend">2009</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">59</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">차세대 기술 CT개론</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">60</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">차세대 블루오션 CT비즈니스 전략</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">61</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">첨단기술 애니메이션</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">62</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">-</td>
          <td class="ta_l">해외진출전략</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">63</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">글로벌 비즈니스</td>
          <td class="ta_l">글로벌 비즈니스</td>
          <td class="ta_l">글로컬문화콘텐츠의 이해와 사례</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">64</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화기호학과 마케팅</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">65</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">분야별로 배우는 콘텐츠 마케팅</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">66</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">사례로 알아보는 소셜네트워크</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">67</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">제작워크숍</td>
          <td class="ta_l">문화콘텐츠 제작</td>
          <td class="ta_l">음반제작워크숍</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
      </tbody>
      <tbody>
      </tbody>
    </table></td>
  </tr>
</table>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->