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


<form name="form1" method="post" action="">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "1">
    <input type = "hidden" name = "menuid">
    
<table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" align="center"><font size="4"><strong>2010 한국콘텐츠아카데미 직무 교육일정표(과정별)</strong></font></td>
  </tr>
  <tr>
    <td><table class="row_list">
      <colgroup>
        <col width="12%" />
        <col width="5%" />
        <col width="" />
        <col width="15%" />
        <col width="10%" />
        <col width="10%" />
        <col width="10%" />
        </colgroup>
      <thead>
        <tr>
          <th>구분 </th>
          <th>No </th>
          <th>과정명</th>
          <th>교육일정</th>
          <th>기간</th>
          <th>인원</th>
          <th>교육비</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td rowspan="7" class="ta_l fs_s lp_1">편집 (7)</td>
          <td class="ta_l fs_s lp_1">1</td>
          <td class="ta_l">Avid  MCA Version4 101과정 1차<br /></td>
          <td class="data">4.12~4.16<br /></td>
          <td class="data">5일</td>
          <td class="data">10</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">Avid  MCA Version4 101과정 2차</td>
          <td class="data">6. 7~6.11</td>
          <td class="data">5일</td>
          <td class="data">10</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">Avid  MCA Version4 101과정 3차</td>
          <td class="data">9. 6~9.10</td>
          <td class="data">5일</td>
          <td class="data">10</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">AvidMCAVersion4 201과정 </td>
          <td class="data">11. 1~11. 5</td>
          <td class="data">5일</td>
          <td class="data">10</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">Edius  Version5 Editing과정</td>
          <td class="data">4. 5~ 4. 9</td>
          <td class="data">5일</td>
          <td class="data">13</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">Final Cut Pro Version7 Level 1과정 1차 </td>
          <td class="data">4.26~ 4.30</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">Final Cut Pro Version7 Level 1과정 2차</td>
          <td class="data">9.27~ 10. 1</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td rowspan="4" class="ta_l fs_s lp_1">음향 (4)</td>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">Pro Tools Version8 110 과정 1차<br /></td>
          <td class="data">10. 4~10. 8<br /></td>
          <td class="data">5일</td>
          <td class="data">5</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">Pro Tools Version8 110 과정 2차</td>
          <td class="data">10.25~10.29</td>
          <td class="data">5일</td>
          <td class="data">5</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">ProToolsVersion8  201과정</td>
          <td class="data">11. 8~11.12</td>
          <td class="data">5일</td>
          <td class="data">5</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">ProTools Version8 210M과정</td>
          <td class="data">11.22~11.26</td>
          <td class="data">5일</td>
          <td class="data">5</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td rowspan="5" class="ta_l fs_s lp_1">CGI (5)</td>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">Color/Motion level 1과정 1차</td>
          <td class="data">5.10~5.14</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">Color/Motion  level 1과정 2차</td>
          <td class="data">11.15~11.19</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">14</td>
          <td class="ta_l">Motion Graphic 과정 1차</td>
          <td class="data">4.19~4.23</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">15</td>
          <td class="ta_l">Motion Graphic 과정 2차</td>
          <td class="data">10.18~10.22</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">16</td>
          <td class="ta_l">특수효과 제작 전문가과정(미국)</td>
          <td class="data">8월중</td>
          <td class="data">2주</td>
          <td class="data">20</td>
          <td class="end">미정</td>
        </tr>
        <tr>
          <td rowspan="7" class="ta_l fs_s lp_1">HD제작 (7)</td>
          <td class="ta_l fs_s lp_1">17</td>
          <td class="ta_l">HD 촬영 Set-up 과정</td>
          <td class="data">5.26~5.28<br /></td>
          <td class="data">3일</td>
          <td class="data">12</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">18</td>
          <td class="ta_l">HDV 제작과정</td>
          <td class="data">6.14~6.18</td>
          <td class="data">5일</td>
          <td class="data">14</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">19</td>
          <td class="ta_l">Red one 실무제작 워크숍 1차</td>
          <td class="data">4.27~4.30</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">20</td>
          <td class="ta_l">Red one 실무제작 워크숍 2차</td>
          <td class="data">10.12~10.15</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">21</td>
          <td class="ta_l">디지털 미디어 워크플로우 과정</td>
          <td class="data">6.23~ 6.25</td>
          <td class="data">3일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">22</td>
          <td class="ta_l">드라마 '추노' 제작사례 워크숍</td>
          <td class="data">4.27</td>
          <td class="data">1일</td>
          <td class="data">60</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">23</td>
          <td class="ta_l">HD특수촬영 과정</td>
          <td class="data">9. 8~9.10</td>
          <td class="data">3일</td>
          <td class="data">14</td>
          <td class="end">200,000</td>
        </tr>
        <tr>
          <td rowspan="6" class="ta_l fs_s lp_1">3D 입체 (6)</td>
          <td class="ta_l fs_s lp_1">24</td>
          <td class="ta_l">3D월드포럼 워크숍<br /></td>
          <td class="data">3.3</td>
          <td class="data">1일</td>
          <td class="data">50</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">25</td>
          <td class="ta_l">3D입체 촬영 실무 제작 워크숍 1차</td>
          <td class="data">10.5~10.8</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">26</td>
          <td class="ta_l">3D입체 촬영 실무 제작 워크숍 2차</td>
          <td class="data">10.26~10.29</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">27</td>
          <td class="ta_l">3D입체 편집 실무 제작 워크숍 1차</td>
          <td class="data">8.31~9.3</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">28</td>
          <td class="ta_l">3D입체 편집 실무 제작 워크숍 2차</td>
          <td class="data">11.9~11.12</td>
          <td class="data">4일</td>
          <td class="data">12</td>
          <td class="end">300,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">29</td>
          <td class="ta_l">3D 창작기술 과정(일본)</td>
          <td class="data">10개월</td>
          <td class="data">1개월</td>
          <td class="data">15</td>
          <td class="end">미정</td>
        </tr>
        <tr>
          <td rowspan="13" class="ta_l fs_s lp_1">게임 제작(13)</td>
          <td class="ta_l fs_s lp_1">30</td>
          <td class="ta_l">iPhone  제작과정<br /></td>
          <td class="data">9월</td>
          <td class="data">5일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">31</td>
          <td class="ta_l">안드로이드  제작과정</td>
          <td class="data">10월</td>
          <td class="data">5일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">32</td>
          <td class="ta_l">웹 게임 개발 과정</td>
          <td class="data">7월</td>
          <td class="data">5일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">33</td>
          <td class="ta_l">웹 2.0 시대의 게임 트랜드 이해</td>
          <td class="data">4월</td>
          <td class="data">2일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">34</td>
          <td class="ta_l">SNS 게임 개발을 위한 글로벌 이슈</td>
          <td class="data">6월</td>
          <td class="data">1일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">35</td>
          <td class="ta_l">미들웨어기술세미나(UDK)과정</td>
          <td class="data">6.21~6.22</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">36</td>
          <td class="ta_l">미들웨어기술세미나(GFX)과정 1차</td>
          <td class="data">5.13~5.14</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">37</td>
          <td class="ta_l">미들웨어기술세미나(GFX)과정 2차</td>
          <td class="data">6.9~6.10</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">38</td>
          <td class="ta_l">미들웨어기술세미나(GFX)과정 3차</td>
          <td class="data">6.29~6.30</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">39</td>
          <td class="ta_l">게임  밸런스 디자인과정</td>
          <td class="data">6월</td>
          <td class="data">4일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">40</td>
          <td class="ta_l">MMORPG의 레벨디자인과 컨셉디자인 방법</td>
          <td class="data">6.25</td>
          <td class="data">1일</td>
          <td class="data">20</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">41</td>
          <td class="ta_l">게임제작사례워크숍 1차 -FPS-</td>
          <td class="data">6.15</td>
          <td class="data">1일</td>
          <td class="data">50</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">42</td>
          <td class="ta_l">게임제작사례워크숍 2차</td>
          <td class="data">9월</td>
          <td class="data">1일</td>
          <td class="data">45</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td rowspan="5" class="ta_l fs_s lp_1"> 경영 및<br />
            비즈니스<br />
            (5)</td>
          <td class="ta_l fs_s lp_1">43</td>
          <td class="ta_l">캐릭터 비즈니스<br /></td>
          <td class="data">7.6~7.7</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">44</td>
          <td class="ta_l">게임 분야 개발 팀장(PD) 리더쉽</td>
          <td class="data">11월</td>
          <td class="data">3일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">45</td>
          <td class="ta_l">게임 제작 Risk Mangement</td>
          <td class="data">8월중</td>
          <td class="data">3일</td>
          <td class="data">15</td>
          <td class="end">50,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">46</td>
          <td class="ta_l">중국 콘텐츠비즈니스 전문가과정</td>
          <td class="data">6.24~7.8</td>
          <td class="data">12</td>
          <td class="data">20</td>
          <td class="end">700,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">47</td>
          <td class="ta_l">중국 콘텐츠리더 초청과정(2회)</td>
          <td class="data">미정</td>
          <td class="data">1개월</td>
          <td class="data">20</td>
          <td class="end">&nbsp;</td>
        </tr>
        <tr>
          <td rowspan="5" class="ta_l fs_s lp_1">공익 정책
            (5)</td>
          <td class="ta_l fs_s lp_1">48</td>
          <td class="ta_l">게임분야 교수연수과정<br /></td>
          <td class="data">8월중</td>
          <td class="data">2일</td>
          <td class="data">20</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">49</td>
          <td class="ta_l">게임분야 공무원 연수</td>
          <td class="data">10월중</td>
          <td class="data">4일</td>
          <td class="data">15</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">50</td>
          <td class="ta_l">미디어관련학과  교수제작 워크숍</td>
          <td class="data">8.16~8.20</td>
          <td class="data">5일</td>
          <td class="data">20</td>
          <td class="end">100,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">51</td>
          <td class="ta_l">장애인 미디어 제작 과정</td>
          <td class="data">6.3~11</td>
          <td class="data">7일</td>
          <td class="data">9</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">52</td>
          <td class="ta_l">유소년 디지털미디어 워크숍</td>
          <td class="data">7.28~30</td>
          <td class="data">3일</td>
          <td class="data">37</td>
          <td class="end">무료</td>
        </tr>
        <tr>
          <td rowspan="5" class="ta_l fs_s lp_1">수탁(4)</td>
          <td class="ta_l fs_s lp_1">53</td>
          <td class="ta_l">건국대 HD제작실무과정</td>
          <td class="data">1.11~1.15</td>
          <td class="data">5일</td>
          <td class="data">13</td>
          <td class="end">&nbsp;</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">54</td>
          <td class="ta_l">문성정보고 영상제작과정</td>
          <td class="data">1.18~1.22</td>
          <td class="data">5일</td>
          <td class="data">13</td>
          <td class="end">&nbsp;</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">55</td>
          <td class="ta_l">KBS 3D입체제작실무</td>
          <td class="data">2.23~2.26</td>
          <td class="data">4일</td>
          <td class="data">8</td>
          <td class="end">&nbsp;</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">56</td>
          <td class="ta_l">디지털 색보정</td>
          <td class="data">3.8~3.12</td>
          <td class="data">5일</td>
          <td class="data">4</td>
          <td class="end">&nbsp;</td>
        </tr>
      </tbody>
    </table></td>
  </tr>
  <tr>
    <td height="25"></td>
  </tr>
  <tr>
    <td height="25" align="center"><font size="4"><strong>2010 한국콘텐츠아카데미 차세대 인재양성 교육일정표</strong></font></td>
  </tr>
  <tr>
    <td><table class="row_list">
      <colgroup>
        <col width="12%" />
        <col width="5%" />
        <col width="" />
        <col width="15%" />
        <col width="10%" />
        <col width="10%" />
        <col width="10%" />
        </colgroup>
      <thead>
        <tr>
          <th>구분 </th>
          <th>No </th>
          <th>과정명</th>
          <th>교육일정</th>
          <th>기간</th>
          <th>인원</th>
          <th>교육비</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td rowspan="7" class="ta_l fs_s lp_1">방송?영상<br>(7)</td>
          <td class="ta_l fs_s lp_1">1</td>
          <td class="ta_l">3D입체영상기획</td>
          <td class="data">10.3~10.8</td>
          <td class="data">5개월</td>
          <td class="data">10</td>
          <td class="end">500,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">2</td>
          <td class="ta_l">3D입체촬영</td>
          <td class="data">10.3~10.8</td>
          <td class="data">5개월</td>
          <td class="data">10</td>
          <td class="end">500,000</td>
        </tr>
       <tr>
          <td class="ta_l fs_s lp_1">3</td>
          <td class="ta_l">3D입체영상편집</td>
          <td class="data">10.3~10.8</td>
          <td class="data">5개월</td>
          <td class="data">10</td>
          <td class="end">500,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">4</td>
          <td class="ta_l">3D입체 그래픽 </td>
          <td class="data">10.3~10.8</td>
          <td class="data">5개월</td>
          <td class="data">10</td>
          <td class="end">500,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">5</td>
          <td class="ta_l">CG (VFX)</td>
          <td class="data">10.3~10.10</td>
          <td class="data">8개월</td>
          <td class="data">12</td>
          <td class="end">800,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">6</td>
          <td class="ta_l">사운드 디자인</td>
          <td class="data">10.3~10.10</td>
          <td class="data">10개월</td>
          <td class="data">10</td>
          <td class="end">500,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">7</td>
          <td class="ta_l">CG 모션 그래픽</td>
          <td class="data">10.3~10.8</td>
          <td class="data">5개월</td>
          <td class="data">14</td>
          <td class="end">500,000</td>
        </tr>
        <tr>
          <td rowspan="4" class="ta_l fs_s lp_1">게임 제작<br>(4)</td>
          <td class="ta_l fs_s lp_1">8</td>
          <td class="ta_l">게임디자인(기획)</td>
          <td class="data">10.3~10.12</td>
          <td class="data">10개월</td>
          <td class="data">12</td>
          <td class="end">1,000,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">9</td>
          <td class="ta_l">게임프로그램</td>
          <td class="data">10.3~10.12</td>
          <td class="data">10개월</td>
          <td class="data">15</td>
          <td class="end">1,000,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">10</td>
          <td class="ta_l">게임그래픽</td>
          <td class="data">10.3~10.12</td>
          <td class="data">10개월</td>
          <td class="data">15</td>
          <td class="end">1,000,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">11</td>
          <td class="ta_l">차세대 게임</td>
          <td class="data">10.3~10.12</td>
          <td class="data">10개월</td>
          <td class="data">12</td>
          <td class="end">1,000,000</td>
        </tr>
        <tr>
          <td rowspan="5" class="ta_l fs_s lp_1">기획?창작<br>(2)</td>
          <td class="ta_l fs_s lp_1">12</td>
          <td class="ta_l">콘텐츠 기획창작</td>
          <td class="data">10.3~10.12</td>
          <td class="data">10개월</td>
          <td class="data">20</td>
          <td class="end">1,000,000</td>
        </tr>
        <tr>
          <td class="ta_l fs_s lp_1">13</td>
          <td class="ta_l">콘텐츠 비즈니스</td>
          <td class="data">10.3~10.8</td>
          <td class="data">6개월</td>
          <td class="data">30</td>
          <td class="end">600,000</td>
        </tr>
      </tbody>
    </table></td>
  </tr>
  <tr>
    <td height="50">&nbsp;</td>
  </tr>
  <tr>
    <td>산업계 직무 재교육과정은 콘텐츠 산업계 재직자를 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량 및 글로벌 경쟁력 강화를 목적으로 3D입체, CG, 게임, HD 제작 분야 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_01.gif" alt="개요" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 고도 창작 역량을                  갖춘 전문인 육성<br />
- 교육대상 : 현업인 대상의 콘텐츠 분야 종사자<br />
- 교육운영 : 1-10일 / 단기 교육 운영 <br />
- 교육내용 : 편집, 음향, CGI, HD제작, 3D입체, 게임, 경영 및 비즈니스, 공익정책, 수탁교육과정 등 </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_02.gif" alt="운영방안" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공 <br />
      - 분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영 <br />
      - 국내ㆍ외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공<br />
      - 풍부한 현장 경험과 강의 경험을 보유한 국내ㆍ외 전문 강사진을 활용 교육 서비스 제공<br />
    - 철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고 </td>
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
- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육과정 운영 </strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 교육방법 : 강의 및 실습교육, 제작현장 실습 교육 등 과정별 커리큘럼에 의한 제작 현장 중심의                과정 운영<br />
- 교육강사 : 현장실무전문가, 학계전문가 등 <br />
- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영<br />
- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</td>
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
    <td class="pd_l25">- 계좌이체 : 한국시티은행 124-50434-242 / 예금주 : 한국콘텐츠진흥원 
(입금확인 후 교육 종료시 계산서 발급)<br />
- 카드결제 : 한국콘텐츠 아카데미 홈페이지(<a href="http://edu.kocca.or.kr" target="_blank">http://edu.kocca.or.kr</a>)에서 온라인 결재 </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 노동부 교육비 환급</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 노동부 직업능력개발 훈련과정으로 지정된 교육과정에 한해 고용보험 가입자가 납부한 교육비의 20～30%를 <br />
&nbsp;&nbsp;노동부로부터 환급 가능<br />
- 노동부 환급 과정의 경우, 교육 종료 후 해당지역 노동사무소에 교육비 환급신청서를 제출</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 교육취소 및 교육비 환불</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 교육비는 과정별 납부기간 내에 완납하여야 하며, 사정에 의한 교육불참시 교육시작 7일 전까지 통보시 전액 환불, <br />
&nbsp;      그 이후의 교육취소 통보에는 환불 불가<br />
&nbsp;&nbsp;(본원의 오프라인 교육은 제작 장비가 수반되어 교육대상자의 사전 확정이 필수적으로 요망됨) </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 폐강</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">  참가인원이 교육정원의 50% 이하일 경우에는 교육이 취소될 수 있으며 이 경우, 납부한 교육비는 전액 환불해 드립니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 참고사항</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">   교육비 일부는 국고에서 지원되며 지방참가자의 경우, 숙식비는 소속사 부담임</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&bull; 방송영상분야 : ☎ 02-2161-0072 / e-mail : offline@kocca.kr / Fax : 02-2161-0078<br />
&bull; 게임제작분야 : ☎ 02-3219-6541 / e-mail : offline@kocca.kr / Fax : 02-2161-0078</td>
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


