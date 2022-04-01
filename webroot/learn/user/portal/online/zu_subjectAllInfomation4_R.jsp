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
        </tbody>
    </table>
      <table class="row_list">
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
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">경영일반</td>
            <td class="ta_l">-</td>
            <td class="ta_l">성공기업의 성공 CEO[01-10]</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">2</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">방송콘텐츠 유통과 마케팅</td>
            <td class="data">무료</td>
            <td class="endend">2006</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">3</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">콘텐츠 비즈니스와 투자전략의 이론과 실제</td>
            <td class="data">무료</td>
            <td class="endend">2006</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">4</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">[연수]미디어 매체별 리터러시1</td>
            <td class="data">무료</td>
            <td class="endend">2004</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">5</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">[연수]미디어 매체별 리터러시2</td>
            <td class="data">무료</td>
            <td class="endend">2005</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">6</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">[연수]미디어교육</td>
            <td class="data">무료</td>
            <td class="endend">2003</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">7</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어속의 양성평등</td>
            <td class="data">무료</td>
            <td class="endend">2006</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">8</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">방송영상기획</td>
            <td class="ta_l">[연수]영상미학</td>
            <td class="data">무료</td>
            <td class="endend">2005</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">9</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">방송영상기획</td>
            <td class="ta_l">[연수]정재형의 영화로 보는 영상미학</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">10</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">방송영상기획</td>
            <td class="ta_l">방송영상 콘텐츠기획</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">11</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">방송영상기획</td>
            <td class="ta_l">방송콘텐츠 스토리텔링</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">12</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">[연수]비주얼 스토리텔링</td>
            <td class="data">무료</td>
            <td class="endend">2002</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">13</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">다큐멘터리 트레일러 만들기와 피칭전략</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">14</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">디지털미디어 스토리텔링</td>
            <td class="data">무료</td>
            <td class="endend">2006</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">15</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">블로그 기자되기</td>
            <td class="data">무료</td>
            <td class="endend">2008</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">16</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">창조적 사고와 통하는 글쓰기</td>
            <td class="data">무료</td>
            <td class="endend">2006</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">17</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">TV 다큐멘터리 글쓰기</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">18</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">[연수]1인뉴스 제작</td>
            <td class="data">무료</td>
            <td class="endend">2005</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">19</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">[연수]VJ 프로그램 제작</td>
            <td class="data">무료</td>
            <td class="endend">2002</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">20</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">뉴패러다임 UCC</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">21</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">Adobe Premiere Pro CS5</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">22</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">After Effect CS5</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">23</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">Avid Media Composer Adanced</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">24</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]방송제작 실무</td>
            <td class="data">무료</td>
            <td class="endend">2002</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">25</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]스튜디오프로그램제작</td>
            <td class="data">무료</td>
            <td class="endend">2002</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">26</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]영상제작전문가특강(연출론)</td>
            <td class="data">무료</td>
            <td class="endend">2003</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">27</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]영상제작전문가특강(영상론)</td>
            <td class="data">무료</td>
            <td class="endend">2003</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">28</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]영상촬영 기본</td>
            <td class="data">무료</td>
            <td class="endend">2002</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">29</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">[연수]HDTV 제작</td>
            <td class="data">무료</td>
            <td class="endend">2005</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">30</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">3D 영상제작개론</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">31</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">3D 입체촬영실무</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">32</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">디지털 포맷과 워크플로어</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">33</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송아나운싱</td>
            <td class="data">무료</td>
            <td class="endend">2008</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">34</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송음향 기초</td>
            <td class="data">15,000</td>
            <td class="endend">2010</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">35</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">빛의 예술, 방송조명</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">36</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">영상촬영의 이해</td>
            <td class="data">무료</td>
            <td class="endend">2008</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">37</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">Autodesk Maya를 이용한 입체영상 제작</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">38</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">TV 다큐멘터리의 이론과 실제</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">39</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">[연수]프로툴즈 음향편집</td>
            <td class="data">무료</td>
            <td class="endend">2007</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">40</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">파이널컷프로</td>
            <td class="data">무료</td>
            <td class="endend">2008</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">41</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">3D 입체편집</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">42</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">디지털 영상 색 보정</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
          </tr>
		  <tr>
            <td class="ta_l fs_s lp_1">43</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">Final cut pro의 Motion제작 실무</td>
            <td class="data">15,000</td>
            <td class="endend">2011</td>
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