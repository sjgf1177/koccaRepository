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


<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">
    
<table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25"><strong>&lt;온라인 교육/방송분야 - 2010년 교육운영 계획 및 커리큘럼&gt;</strong></td>
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
            <th>제작<br />
            연도</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td class="ta_l fs_s lp_1">1</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">블로그 기자되기<br /></td>
            <td class="data">15,000</td>
            <td class="end">2008</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">2</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">창조적 사고와 통하는 글쓰기</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">3</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">창작</td>
            <td class="ta_l">창작일반</td>
            <td class="ta_l">TV다큐멘터리 글쓰기</td>
            <td class="data">15,000</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">4</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l"><span class="data">방송영상기획</span></td>
            <td class="ta_l">영상미학</td>
            <td class="data">무료</td>
            <td class="end">2005</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">5</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l"><span class="data">방송영상기획</span></td>
            <td class="ta_l">영화로 보는 영상미학</td>
            <td class="data">15,000</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">6</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l"><span class="data">방송영상기획</span></td>
            <td class="ta_l">방송영상콘텐츠기획</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">7</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l"><span class="data">방송영상기획</span></td>
            <td class="ta_l">방송콘텐츠 스토리텔링 </td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">8</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">다큐멘터리트레일러 만들기와 피칭전략</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">9</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">비주얼 스토리텔링</td>
            <td class="data">무료</td>
            <td class="end">2002</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">10</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">기획</td>
            <td class="ta_l">콘텐츠기획</td>
            <td class="ta_l">디지털미디어 스토리텔링</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">11</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">영상촬영 기본<br /></td>
            <td class="data">무료</td>
            <td class="end">2002</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">12</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">VJ프로그램제작</td>
            <td class="data">무료</td>
            <td class="end">2002</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">13</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">1인 뉴스제작</td>
            <td class="data">무료</td>
            <td class="end">2005</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">14</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">어도비 프리미어 프로 CS4</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">15</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">아비드 미디어컴포저 어드밴스</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">16</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">디지털 편집</td>
            <td class="ta_l">애프터 이펙트 CS4</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">17</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">1인제작</td>
            <td class="ta_l">뉴패러다임, UCC</td>
            <td class="data">무료</td>
            <td class="end">2007 </td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">18</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">스튜디오프로그램제작</td>
            <td class="data">무료</td>
            <td class="end">2002</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">19</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">HDTV 제작</td>
            <td class="data">무료</td>
            <td class="end">2005</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">20</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송제작전문가특강_연출론</td>
            <td class="data">무료</td>
            <td class="end">2003</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">21</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송제작전문가특강_영상론<br /></td>
            <td class="data">무료</td>
            <td class="end">2003</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">22</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송제작실무</td>
            <td class="data">무료</td>
            <td class="end">2002</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">23</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">빛의예술, 방송조명</td>
            <td class="data">15,000</td>
            <td class="end">2007 </td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">24</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">영상촬영의 이해</td>
            <td class="data">15,000</td>
            <td class="end">2008</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">25</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">TV다큐멘터리의 이론과 실제</td>
            <td class="data">15,000</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">26</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송아나운싱</td>
            <td class="data">15,000</td>
            <td class="end">2008</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">27</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">방송제작</td>
            <td class="ta_l">제작일반</td>
            <td class="ta_l">방송음향기초</td>
            <td class="data">30,000</td>
            <td class="end">2010</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">28</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">아비드 미디어컴포우져 </td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">29</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">프로툴즈 음향편집</td>
            <td class="data">15,000</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">30</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">제작</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">편집</td>
            <td class="ta_l">파이널컷프로</td>
            <td class="data">15,000</td>
            <td class="end">2008</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">31</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">경영일반</td>
            <td class="ta_l">&nbsp;</td>
            <td class="ta_l">성공기업성공CEO<br /></td>
            <td class="data">무료</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">32</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">디지털미디어와 콘텐츠의 이해</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">33</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어 융합환경의 이해</td>
            <td class="data">무료</td>
            <td class="end">2007</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">34</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">방송콘텐츠 유통&middot;마케팅</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">35</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 경영</td>
            <td class="ta_l">-</td>
            <td class="ta_l">콘텐츠 비즈니스와 투자전략의 이론과 실제</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">36</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 교육</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어교육의 이해</td>
            <td class="data">무료</td>
            <td class="end">2003</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">37</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 교육</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어매체별리터러시Ⅰ</td>
            <td class="data">무료</td>
            <td class="end">2004</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">38</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 교육</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어매체별리터러시Ⅱ</td>
            <td class="data">무료</td>
            <td class="end">2005</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">39</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">미디어 교육</td>
            <td class="ta_l">-</td>
            <td class="ta_l">미디어속의 양성평등</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">40</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">법률</td>
            <td class="ta_l">-</td>
            <td class="ta_l">방송저작권 실무Ⅰ</td>
            <td class="data">무료</td>
            <td class="end">2006</td>
          </tr>
          <tr>
            <td class="ta_l fs_s lp_1">41</td>
            <td class="ta_l">방송</td>
            <td class="ta_l">경영지원</td>
            <td class="ta_l">법률</td>
            <td class="ta_l">-</td>
            <td class="ta_l">방송저작권 실무Ⅱ</td>
            <td class="data">무료</td>
            <td class="end">2007</td>
          </tr>
        </tbody>
        <tbody>
        </tbody>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="25"><strong>&lt;온라인 교육/게임분야 - 2010년 교육운영 계획 및 커리큘럼&gt;</strong></td>
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
          <th>제작<br />
            연도</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="ta_l fs_s lp_1">1</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">기획자가 알아야할 3D 게임그래픽(new)<br /></td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">게임산업</td>
          <td class="ta_l">게임산업연구</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">스토리보드(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">게임기획</td>
          <td class="ta_l">기획자가 알아야할 2D 게임그래픽(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">게임산업</td>
          <td class="ta_l">게임분석론2(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">기초 게임시나리오 창작실습</td>
          <td class="data">30,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">아이디어발상(New)</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">창작일반</td>
          <td class="ta_l">게임연출론</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
            /사운드</td>
          <td class="ta_l">그래픽<br />
            일반</td>
          <td class="ta_l">게임 UCC 제작법(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽<br />
일반</td>
          <td class="ta_l">게임 모델링 노멀맵(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임<br />
            프로그래밍</td>
          <td class="ta_l">3D<br />
            프로그래밍</td>
          <td class="ta_l">3D엔진프로그래밍<br /></td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임<br />
프로그래밍</td>
          <td class="ta_l">3D<br />
프로그래밍</td>
          <td class="ta_l">3D공간구조프로그래밍</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임<br />
프로그래밍</td>
          <td class="ta_l">3D<br />
프로그래밍</td>
          <td class="ta_l">3D Data Exporting Techniques</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">14</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임<br />
프로그래밍</td>
          <td class="ta_l">3D<br />
프로그래밍</td>
          <td class="ta_l">3D 프로그래밍2</td>
          <td class="data">15,000</td>
          <td class="end">2004</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">15</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임<br />
프로그래밍</td>
          <td class="ta_l">3D<br />
프로그래밍</td>
          <td class="ta_l">3D 고급 렌더링 테크닉</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">16</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인일반</td>
          <td class="ta_l">MOD 게임제작 및 활용(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">17</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인일반</td>
          <td class="ta_l">재미이론(new)</td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">18</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임디자인</td>
          <td class="ta_l">디자인일반</td>
          <td class="ta_l">게임디자인2(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">19</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">3D그래픽</td>
          <td class="ta_l">3D게임 텍스처(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">20</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">3D그래픽</td>
          <td class="ta_l">3D그래픽 제작 실무(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">21</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">3D그래픽</td>
          <td class="ta_l">3DMax 이해하기(new)<br /></td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">22</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">게임캐릭터</td>
          <td class="ta_l">게임캐릭터디자인1(new)</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">23</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">게임캐릭터</td>
          <td class="ta_l">게임캐릭터디자인2(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">24</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">포트폴리오 구성과 제작</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">25</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">디자인일반</td>
          <td class="ta_l">게임디자인1(New)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">26</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">그래픽스 이론(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">27</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">디지털 드로잉1(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">28</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">디지털 드로잉2(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">29</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">배경그래픽 기초(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">30</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임그래픽<br />
/사운드</td>
          <td class="ta_l">그래픽일반</td>
          <td class="ta_l">애니메이션개론(New)</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">31</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
프로그래밍</td>
          <td class="ta_l">OS</td>
          <td class="ta_l">운영체제</td>
          <td class="data">15,000</td>
          <td class="end">2001</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">32</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
프로그래밍</td>
          <td class="ta_l">네트워크</td>
          <td class="ta_l">네트워크 이론</td>
          <td class="data">15,000</td>
          <td class="end">2001</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">33</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">C#</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">34</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">객체지향 프로그래밍1</td>
          <td class="data">15,000</td>
          <td class="end">2002</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">35</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">객체지향 프로그래밍2</td>
          <td class="data">15,000</td>
          <td class="end">2003</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">36</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">게임툴 프로그래밍 설계방법론(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">37</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">게임툴프로그래밍1(new)<br /></td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">38</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">알고리즘<br /></td>
          <td class="data">15,000</td>
          <td class="end">2001</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">39</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">오픈소스게임엔진-네뷰라디바이스2</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">40</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">자료구조</td>
          <td class="data">15,000</td>
          <td class="end">2001</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">41</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">DirectX1(new)</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">42</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">DirectX10(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">43</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">DirectX2(new)</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">44</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">프로그램 <br />
            일반</td>
          <td class="ta_l">게임수학(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">45</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">게임 <br />
            프로그래밍</td>
          <td class="ta_l">3D<br />
            프로그래밍</td>
          <td class="ta_l">3D프로그래밍1(new)</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">46</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">게임마케팅연구</td>
          <td class="data">15,000</td>
          <td class="end">2003</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">47</td>
          <td class="ta_l">게임</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">사업계획서 작성요령과 해외진출을 위한 방법론(new)</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
      </tbody>
      <tbody>
      </tbody>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="25"><strong>&lt;온라인 교육/콘텐츠 기획창작분야 - 2010년 교육운영 계획 및 커리큘럼&gt;</strong></td>
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
          <th>제작<br />
            연도</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="ta_l fs_s lp_1">1</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠<br />
            산업</td>
          <td class="ta_l">문화콘텐츠 비즈니스<br /></td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠<br />
            산업</td>
          <td class="ta_l">세계문화콘텐츠 시장</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠<br />
            산업</td>
          <td class="ta_l">유비쿼터스 사회와 문화콘텐츠</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">문화콘텐츠와 뉴미디어</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">세계의 애니메이션 역사와 사례</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠<br />
            산업</td>
          <td class="ta_l">대중문화분석</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">재미있는 광고읽기</td>
          <td class="data">15,000</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">재미있는 디자인 읽기</td>
          <td class="data">15,000</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">문화콘텐츠<br />
            산업</td>
          <td class="ta_l">21세기 문화변동과 콘텐츠</td>
          <td class="data">무료</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠입문</td>
          <td class="ta_l">[입문] 모바일 콘텐츠 전략 </td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠입문</td>
          <td class="ta_l">[입문] 문화콘텐츠 해외 마케팅</td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠입문</td>
          <td class="ta_l">[입문] 문화콘텐츠와 스토리디자인</td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">콘텐츠입문</td>
          <td class="ta_l">[입문] 유비쿼터스 사회와 문화콘텐츠</td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">14</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">시간속의 만화와 캐릭터</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">15</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">재미있는 영화읽기</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">16</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기초이론</td>
          <td class="ta_l">생활속의<br />
            콘텐츠</td>
          <td class="ta_l">재미있는 TV읽기</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">17</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">콘텐츠서비스운영전략</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">18</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">애니메이션 기획</td>
          <td class="data">무료</td>
          <td class="end">2004</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">19</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">문화콘텐츠 디렉터론</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">20</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">문화콘텐츠 크리에이터</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">21</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">문화콘텐츠 프로젝트 관리실무Ⅱ <br /></td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">22</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">문화콘텐츠의 투자경제성평가</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">23</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">문화콘텐츠 프로듀싱 </td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">24</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">new 영화산업의 사례연구</td>
          <td class="data">무료</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">25</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">미디어와 뮤직비즈니스</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">26</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">에듀테인먼트 사례</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">27</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">음악산업의 사례연구</td>
          <td class="data">무료</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">28</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">해외기업의 성공사례_캐릭터/출판만화/음반/모바일/게임</td>
          <td class="data">무료</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">29</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">콘텐츠기획</td>
          <td class="ta_l">대중음반 기획</td>
          <td class="data">무료</td>
          <td class="end">2004</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">30</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">해외사례로 본 문화와 캐릭터</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">31</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">모바일 콘텐츠<br /></td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">32</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">문화콘텐츠 성공사례</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">33</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">미국영화</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">34</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
            사례연구</td>
          <td class="ta_l">아시아영화</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">35</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">스토리텔링의 이해</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">36</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">헐리우드 애니메이션 스토리텔링 전략</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">37</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">문화콘텐츠와 스토리 디자인</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">38</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">디지털 스토리텔링</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">39</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">스토리텔링</td>
          <td class="ta_l">문화콘텐츠 스토리텔링 사례</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">40</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">사례연구</td>
          <td class="ta_l">문화콘텐츠<br />
사례연구</td>
          <td class="ta_l">뉴미디어_게임</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">41</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">첨단기술 애니메이션<br /></td>
          <td class="data">10,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">42</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">차세대기술 CT개론</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">43</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">차세대 블루오션 CT비즈니스전략</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">44</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">사례로 알아보는 CT </td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">45</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">문화콘텐츠 기술론</td>
          <td class="data">15,000</td>
          <td class="end">2006</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">46</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">이머징테크날로지와 문화콘텐츠</td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">47</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">기획</td>
          <td class="ta_l">창작</td>
          <td class="ta_l">첨단기술</td>
          <td class="ta_l">기술로 본 모바일 콘텐츠 </td>
          <td class="data">무료</td>
          <td class="end">2009</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">48</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">제작워크숍</td>
          <td class="ta_l">문화콘텐츠<br />
            제작</td>
          <td class="ta_l">콘텐츠제작도구론</td>
          <td class="data">무료</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">49</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">제작</td>
          <td class="ta_l">제작워크숍</td>
          <td class="ta_l">문화콘텐츠<br />
            제작</td>
          <td class="ta_l">음반제작워크숍</td>
          <td class="data">무료</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">50</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">-</td>
          <td class="ta_l">해외진출전략</td>
          <td class="data">15,000</td>
          <td class="end">2008</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">51</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">글로벌<br />
            비즈니스</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 해외 마케팅<br /></td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">52</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">글로벌<br />
비즈니스</td>
          <td class="ta_l">-</td>
          <td class="ta_l">일본 문화 콘텐츠 산업의 이해</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">53</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">글로벌<br />
비즈니스</td>
          <td class="ta_l">-</td>
          <td class="ta_l">중국 문화 콘텐츠 산업의 이해</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">54</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">애니메이션 마케팅</td>
          <td class="data">무료</td>
          <td class="end">2004</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">55</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">프리젠테이션방법론 </td>
          <td class="data">무료</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">56</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">글로벌마케팅</td>
          <td class="data">15,000</td>
          <td class="end">2005</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">57</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">유통</td>
          <td class="ta_l">마케팅</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화기호학과 마케팅</td>
          <td class="data">15,000</td>
          <td class="end">2006        </td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">58</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">[입문] 문화콘텐츠 비즈니스 창업론</td>
          <td class="data">무료</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">59</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 경영전략</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">60</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 리더십 및 관리</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">61</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 비즈니스 창업론</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">62</td>
          <td class="ta_l">문화</td>
          <td class="ta_l">경영지원</td>
          <td class="ta_l">경영일반</td>
          <td class="ta_l">-</td>
          <td class="ta_l">문화콘텐츠 CEO 경영사례</td>
          <td class="data">15,000</td>
          <td class="end">2007</td>
        </tr>
      </tbody>
      <tbody>
      </tbody>
    </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="50">&nbsp;</td>
  </tr>
  <tr>
    <td>온라인교육과정은 콘텐츠산업의국가 경쟁력 제고 및 콘텐츠에 대한 다양한 교육기회 제공을 목적으로 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야 등의 교육 콘텐츠를 개발하여 온라 교육시스템을 활용해 운영되는 교육프로그램입니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_01.gif" alt="개요" class="tit" /></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 운영목적</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 콘텐츠 제작 기술과 고도 창작 역량을 갖춘  방송, 게임, 문화콘텐츠 창의인재 양성<br />
    - 문화콘텐츠에 대한 다양한 교육 기회를 제공 및 저변 확대</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육대상</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 전국 방송사, 지역 민방, 케이블TV, 방송 유관기관 등 방송영상산업 분야 종사자   방송예비인력, 전국시도교육청 소속 초,<br />
&nbsp; 중등 교원 및 일반인 등 <br />
      - 문화콘텐츠 산업에 관심있는 예비인력 및 종사자, 일반인, 대학생<br />
    - 일반인 및 콘텐츠산업 관련산업 분야 종사자</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="middle" /> <strong>교육운영</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 전과정 매월 개강 1일~ 말일까지 <br />
    - (4주-12주) 원격교원연수원 과정/ 자격인증과정/대학연계 산학협력과정/정책공익과정 등        다양한 형태의 교육서비스 <br />
&nbsp;&nbsp;    제공을 통한 교육 기회 확대</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" /> <strong>교육내용</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 국내 유일의 방송제작 및 미디어교육분야의 원겨교육연수원 운영을 통한 교원대상 승진학점 
인정과정 운영 <br />
&nbsp;&nbsp; (교육과학기술부 인가번호 제 04-02호)<br />
- 대학연계 정규 학점 인정과정 및 교수자료 서비스등 학계지원과정<br />
- 미디어교육학회 인증 미디어교육교사양성과정 운영<br />
- 장애인, CEO, 시민단체 회원대상 정책공익과정 운영<br />
- 고객수요에 부응한 맞춤형 수탁과정 운영<br />
- 세부내용 : 기획창작과정, 제작실무과정, 1인제작과정, 전문가특강과정, 디지털편집과정, 
<br />
&nbsp; 미디어경영콘텐츠 비즈니스 과정, 미디어교육과정 등<br />
- 문화콘텐츠 일반 ,문화콘텐츠 산업 장르(영화,만화,캐릭터,애니메이션 등)       문화콘텐츠 제작창작 (스토리텔링, <br />
&nbsp;&nbsp; 사례연구 등), 문화콘텐츠 경영지원 등 <br />
- 3D 고급 렌더링 테크닉, 게임 모델링 노멀맵(new), 사례를 통한 마케팅 연구(New) <br />
&nbsp;&nbsp;게임툴프로그래밍1(new), 3D프로그래밍1(new), 3D그래픽 제작 실무(new) 등 </td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_02.gif" alt="운영방안" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">-  지역, 연령, 시간의 구애없이 폭넓은 사용자층에게 온라인 교육을 통한 교육기회 확대 <br />
      - 현업운영 시스템에 맞는 맞춤식 교육과정 운영으로 고객 수요창출 및 교육효과 극대화<br />
      - 소외계층인 장애인대상으로 무료과정을 운영함으로서 공익성 확대 <br />
      - 분야별 교육요구도 조사, 미디어 트렌드 분석을 통한 제작 현장 중심의 교육과정 운영 <br />
      - 풍부한 현장 경험과 강의 경험을 보유한 국내ㆍ외 전문 강사진을 활용 교육 서비스 제공<br />
      - 방송전문 온라인튜터를 활용한 1:1 맞춤식 학습관리 운영<br />
      - 철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고 <br />
      - 무료교육과정 운영을 통한 본원 온라인 교육사업의 교육사업의 공적기능 강화 및 일부 전략과정 
유료화를 통한 <br />
&nbsp; 교육효과 제고 </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_03.gif" alt="운영방법" class="tit" /></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육 신청/선발</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 신청방법 : 온라인 신청 / http://edu.kocca.or.kr<br />
- 선발기준 : 교육신청 선착순 모집</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육과정 운영 </strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 교육방법 : 인터넷을 통한 동영상 강의 및 이론 교육 등 과정별 커리큘럼에 의한 현장 중심의     과정 운영 <br />
      - 교육강사 : 콘텐츠 산업 분야 전문가, 학계전문가 <br />
      - 교육시스템 및 장비 : 학습자별 개인 PC에서 온라인 학습 <br />
&nbsp;&nbsp;단, 미디어교육교사양성과정에 한해 최적의 장비로 오프라인 실습 제공 <br />
- 교육장소 : 교육장소의 제한 없음 <br />
&nbsp;&nbsp;단, 미디어교육교사양성과정에 한해 콘텐츠아카데미 멀티미디어 강의실 </td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_04.gif" alt="교육비 안내" class="tit" /></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 계좌이체 및 카드결제</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">카드결제 및 가상계좌 : 콘텐츠아카데미 홈페이지(http://edu.kocca.or.kr)에서 결제</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 교육취소 및 교육비 환불</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 교육비는 과정별 납부기간 내에 완납하여야 하며, 수강 신청 후 신청 마감일 까지 미입금의 경우는 취소처리됨 <br />
- 수강신청 기간내 취소할 경우 100% 환불<br />
- 학습일로부터 7일 이내에 취소할 경우 50% 환불<br />
- 학습일로부터 7일 경과후 취소 불가(환불 불가)</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&bull; 사이버콘텐츠아카데미 ☎ 02-2161-0077 / e-mail : academy@kocca.or.kr / Fax : 02-2161-0078</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><strong>&lt;전문 튜터의 1:1 맞춤식 학습관리&gt;</strong></td>
  </tr>
  <tr>
    <td class="pd_l15"><img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 온전문 튜터들의 철저한 학습관리와 신속한 답변 처리, 유용한 자료제공 등 
학습자 중심의 종합학습서비스 제공<br />
<img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 라인 상에서 학습자와 튜터 상호간의 질문과 토론 등 다양한 상호작용을 통한
1:1 맞춤 교육<br />
<img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 맞춤형 메일 서비스<br />
&nbsp;&nbsp;- 진도별로 차별화된 개인 맞춤형 메일 서비스 제공<br />
&nbsp;&nbsp;- 나만의 학습상태에 따른 권장진도, 레포트 및 평가에 대한 안내사항 전달<br />
<img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 개인별 sms 서비스<br />
&nbsp;&nbsp;- 매주 sms를 통한 진도안내 및 학습독려<br />
&nbsp;&nbsp;- 공지사항 sms로 수시로 전달<br />
<img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 반복 학습 서비스<br />
&nbsp;&nbsp;- 과정 종료 후 일정기간 동안(무료과정:2주, 유료과정:4주) 학습과정에 대한 반복학습 서비스<br />
<img src="/images/portal/ico/ico_comment_arrow.gif" width="3" height="5" /> 커뮤니티 서비스<br />
&nbsp;&nbsp;- 학습자 사이에 관심분야를 공유하고 원활한 커뮤니케이션을 도모하기
위한 커뮤니티 활동 지원</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>

</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


