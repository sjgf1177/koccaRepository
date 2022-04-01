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
    <td height="20">&nbsp;</td>
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
        <col width="12%" />
        <col width="12%" />
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
          <td class="ta_l">게임</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">사업계획서 작성요령과 해외진출을 위한 방법론</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">게임산업</td>
          <td class="ta_l">게임분석론2</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">게임산업</td>
          <td class="ta_l">게임산업연구</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">게임 기획 및 그래픽 실무자를 위한 게임기술 이해</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">기획자가 알아야 할 2D 게임그래픽</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">기획자가 알아야 할 3D 게임그래픽</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">스토리보드</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">게임연출론</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">기초 게임시나리오 창작실습</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">아이디어발상</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">사례를 통한 게임마케팅 연구</td>
          <td class="data">15,000</td>
          <td class="endend">2003</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">3D 그래픽</td>
          <td class="ta_l">3D 게임 텍스처</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">3D 그래픽</td>
          <td class="ta_l">3D 그래픽 제작 실무</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">14</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">게임캐릭터</td>
          <td class="ta_l">게임캐릭터디자인1</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">15</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">게임캐릭터</td>
          <td class="ta_l">게임캐릭터디자인2</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">16</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">그래픽 일반</td>
          <td class="ta_l">게임 모델링 노멀맵</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">17</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">그래픽 일반</td>
          <td class="ta_l">그래픽스 이론</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">18</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">그래픽 일반</td>
          <td class="ta_l">배경그래픽 기초</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">19</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">그래픽 일반</td>
          <td class="ta_l">애니메이션개론</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">20</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽/<br>사운드</td>
          <td class="ta_l">그래픽 일반</td>
          <td class="ta_l">포트폴리오 구성과 제작</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">21</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인 일반</td>
          <td class="ta_l">게임디자인1</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">22</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인 일반</td>
          <td class="ta_l">게임디자인2</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">23</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인 일반</td>
          <td class="ta_l">재미이론</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">24</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인 일반</td>
          <td class="ta_l">MOD 게임제작 및 활용</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">25</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">3D프로그래밍</td>
          <td class="ta_l">3D 공간구조프로그래밍</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">26</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">3D프로그래밍</td>
          <td class="ta_l">3D 엔진프로그래밍</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">27</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">3D프로그래밍</td>
          <td class="ta_l">3D 프로그래밍1</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">28</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">3D프로그래밍</td>
          <td class="ta_l">3D Data Exporting Techniques</td>
          <td class="data">무료</td>
          <td class="endend">2005</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">29</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">네트워크</td>
          <td class="ta_l">네트워크 이론</td>
          <td class="data">15,000</td>
          <td class="endend">2001</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">30</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 언어</td>
          <td class="ta_l">C#</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">31</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">아이폰 및 아이패드용 게임 제작(기초)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">32</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">아이폰 및 아이패드용 게임 제작(실무)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">33</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">안드로이드 게임 제작(기초)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">34</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">안드로이드 게임 제작(실무)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">35</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">UDK 활용 방법(스크립트)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">36</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">UDK 활용 방법(툴)</td>
          <td class="data">15,000</td>
          <td class="endend">2011</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">37</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">게임수학</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">38</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">게임툴프로그래밍 설계방법론</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">39</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">게임툴프로그래밍1</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">40</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">알고리즘</td>
          <td class="data">15,000</td>
          <td class="endend">2001</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">41</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">오픈소스게임엔진-네뷰라디바이스2</td>
          <td class="data">무료</td>
          <td class="endend">2008</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">42</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">자료구조</td>
          <td class="data">15,000</td>
          <td class="endend">2001</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">43</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">DirectX1</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">44</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">DirectX10</td>
          <td class="data">무료</td>
          <td class="endend">2007</td>
        </tr>
		<tr>
          <td class="ta_l fs_s lp_1">45</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임프로그래밍</td>
          <td class="ta_l">프로그램 일반</td>
          <td class="ta_l">DirectX2</td>
          <td class="data">무료</td>
          <td class="endend">2006</td>
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