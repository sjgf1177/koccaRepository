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
<html>
<head>

<link rel="stylesheet" href="/css/admin_style_eudintro.css" type="text/css">
<script language="javascript">

	function selectArea( val ) {
		document.form1.tabid.value = val;
		document.form1.p_process.value = "edu";
		document.form1.action = "/servlet/controller.homepage.HomePageAboutUsServlet";
		document.form1.submit();
	}

</script>
</head>
<body>
<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "7">
    <input type = "hidden" name = "menuid">
    <input type = "hidden" name = "tabid" value="<%= box.get("tabid") %>">
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>
			<table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal//academy/stitle_03.gif" alt="교육사업 안내" /></td>
          <td class="h_road">Home &gt; 아카데미 소개  &gt; <strong>교육사업 안내</strong></td>
        </tr>
        <tr>
          <td height="12" colspan="2"></td>
        </tr>
        <tr>
          <td height="1" colspan="2" bgcolor="E5E5E5"></td>
        </tr>
      </table>
			 <!--타이틀부분//-->
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td height="15">
          <table width="672" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td>
          		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_01_on.gif" /></a></span>              	
           		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_02_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
          <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
              <tr>
              <td>
              <div id="tab1" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit01.gif" alt="차세대핵심인력양성" /></p>
                    <p class="mgt10"><img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>차세대 핵심 인력양성 교육과정은 예비취업자 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량과 글로벌 경쟁력 강화를 목적으로 방송영상, 게임, 기획창작 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 고도 창작 역량을 갖춘 전문인 육성</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육대상 : 콘텐츠 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자, 관련분야 경력자</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육운영 : 6~10개월 / 장기교육 운영</p>
                    <table width="100%" border="0" cellpadding="3" cellspacing="0" id="tab_form" summary="차세대 핵심 인력양성 교육운영 교육과정 및 기간 상세 내역입니다." >
                      <colgroup>
                      <col width="20%" />
                      <col width="" />
                      <col width="25%" />
                      <col width="10%" />
                      <col width="10%" />
                      </colgroup>
                      <thead>
                        <tr>
                          <td class="tit_table">분야</td>
                          <td class="tit_table">과정명</td>
                          <td class="tit_table">교육일정</td>
                          <td class="tit_table">교육기간</td>
                          <td class="tit_table">교육인원</td>
                        </tr>
                      </thead>
                      <tfoot>
                        <tr>
                          <th scope="row" >총 합 계</th>
                          <td colspan="3">&nbsp;</td>
                          <td>170</td>
                        </tr>
                      </tfoot>
                      <tbody>
                        <tr>
                          <th scope="row" rowspan="5">방송.영상</th>
                          <td>VFX (특수영상)</td>
                          <td>''12.3~11</td>
                          <td>9개월</td>
                          <td>15</td>
                        </tr>
                        <tr>
                          <td>모션 그래픽</td>
                          <td>''12.3~11</td>
                          <td>9개월</td>
                          <td>15</td>
                        </tr>
                        <tr>
                          <td>디지털 영상편집</td>
                          <td>''12.3~9/9~12</td>
                          <td>6/4개월</td>
                          <td>30</td>
                        </tr>
                        <tr>
                          <td>디지털 음향제작</td>
                          <td>''12.3~9</td>
                          <td>6개월</td>
                          <td>10</td>
                        </tr>
                        <tr>
                          <td colspan="3">합 계</td>
                          <td>70</td>
                        </tr>
                        <tr>
                          <th scope="row"  rowspan="5">게임</th>
                          <td>게임디자인(기획)</td>
                          <td>''12.3~12</td>
                          <td>10개월</td>
                          <td>15</td>
                        </tr>
                        <tr>
                          <td>게임프로그램</td>
                          <td>''12.3~12</td>
                          <td>10개월</td>
                          <td>13</td>
                        </tr>
                        <tr>
                          <td>게임그래픽</td>
                          <td>''12.3~12</td>
                          <td>10개월</td>
                          <td>18</td>
                        </tr>
						<tr>
                          <td>게임 프로젝트</td>
                          <td>''12.3~12</td>
                          <td>10개월</td>
                          <td>19</td>
                        </tr>
                        <tr>
                          <td colspan="3">합 계</td>
                          <td>65</td>
                        </tr>   
                        <tr>
                          <th scope="row"  rowspan="1">기획.창작</th>
                          <td>콘텐츠 기획창작</td>
                          <td>''12.3~12</td>
                          <td>10개월</td>
                          <td>20</td>
                        </tr>
                        <tr>
                          <th scope="row"  rowspan="1">비즈니스</th>
                          <td>국제콘텐츠 비즈니스</td>
                          <td>''12.3~11</td>
                          <td>9개월</td>
                          <td>15</td>
                        </tr>                          
                        <tr>
                          <th scope="row"  rowspan="1">&nbsp;</th>
                          <td colspan="3">합 계</td>
                          <td>35</td>
                        </tr>                   
                      </tbody>
                    </table>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle05.gif" alt="운영방향" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;국내·외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle06.gif" alt="운영방법" /></p>
                    <img src="/images/portal/homepage_renewal/academy/b_01_process_01_1.gif" alt="협업교육요구조사" /><img src="/images/portal/homepage_renewal/academy/b_01_process_01_2.gif" alt="교육과정개발" /> <img src="/images/portal/homepage_renewal/academy/b_01_process_01_3.gif" alt="교육신청/선발(1~2월)" /> <img src="/images/portal/homepage_renewal/academy/b_01_process_01_4.gif" alt="교육과정(3~11월)" /> <img src="/images/portal/homepage_renewal/academy/b_01_process_01_5.gif" alt="최종교육평가(12월)" /> <img src="/images/portal/homepage_renewal/academy/b_01_process_01_6.gif" alt="취업 지원" />
                    <p>&nbsp;&nbsp;&nbsp;※ 교육과정별 운영방법과 일정은 일부 차이가 있습니다.</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 특전</b></p>
                    <table width="100%" border="0" cellpadding="3" cellspacing="0" id="tab_form2" summary="차세대 핵심 인력양성 교육 특전 내역입니다." >
                      <colgroup>
                      <col width="20%" />
                      <col width="" />
                      </colgroup>
                      <tr>
                        <th>과정명</th>
                        <th>내용</th>
                      </tr>
                      <tr>
                        <td>공통</td>
                        <td><p>&nbsp;- 월 316,000원 장학금 지급(비즈니스 과정 제외)</p>
                          <p>&nbsp;- 한국콘텐츠진흥원 교육프로그램 우선 참여 기회 제공</p>
                          <p>&nbsp;&nbsp;&nbsp;&nbsp;※ 사이버콘텐츠아카데미, 전략지역 연수과정 등</p>
                          <p>&nbsp;- 국내 컨퍼런스 및 전시회 참가 지원</p></td>
                      </tr>
                      <tr>
                        <td>기획·창작</td>
                        <td><p>&nbsp;- 프로젝트 완성화를 위한 전담 멘토, 전문가활용 기회 확대 제공</p></td>
                      </tr>
                      <tr>
                        <td>국제콘텐츠 비즈니스</td>
                        <td><p>&nbsp;- 수료 후 우수자에게 관련분야 연수, 인턴십 기회 제공</p></td>
                      </tr>
                    </table>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;방송영상분야 : 김희숙 과장 (02-2161-0072, <a href="mailto:hskim@kocca.kr">hskim@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;게임제작분야 : 주성호 과장 (02-3219-6534, <a href="mailto:thinkju@kocca.kr">thinkju@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;기획창작분야 : 유윤옥 과장 (02-3219-6536, <a href="mailto:inami@kocca.kr">inami@kocca.kr</a>)<br />
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;비즈니스분야 : 박경은 주임 (02-3219-6541, <a href="mailto:pke329@kocca.kr">pke329@kocca.kr</a>)<br />
                      <br />
                    </p></td>
                </tr>
              </table>
            </div></td></table>
            </td>
        </tr>
      </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
			
			<%}else{ %>
    
<% if      ("1".equals(box.get("tabid")) || "".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>차세대인재양성교육</strong></p></h2><% }
   else if ("2".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>산업계직무재교육</strong></p></h2><% }
   else if ("3".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>온라인교육</strong></p></h2><% }
   else if ("4".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내" 	  class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>수탁교육</strong></p></h2><% }
   else if ("5".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>국제인증과정</strong></p></h2><% } 
   else if ("6".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>3D입체콘텐츠전문인력양성</strong></p></h2><% }
   else if ("7".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>기업참여형교육</strong></p></h2><% }
   else if ("8".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내" 	  class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>드라마프로듀서스쿨</strong></p></h2><% }
   else if ("9".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>게임국가기술자격검정</strong></p></h2><% } 
%>
    
    <ul class="tabwrap">
		<li><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><span>차세대인재양성교육</span></a></li>
		<li><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><span>산업계직무재교육</span></a></li>
		<li><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><span>온라인교육</span></a></li>
		<li><a href="javascript:selectArea('4');" class="tab_online<%= box.get("tabid").equals("4") ? " tab_online_on" : "" %>"><span>수탁교육</span></a></li>
		<li><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><span>국제인증과정</span></a></li><br>
		<li><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><span>3D입체콘텐츠전문인력양성</span></a></li>
		<li><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><span>기업참여형교육</span></a></li>
		<li><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><span>드라마프로듀서스쿨</span></a></li>
		<li><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><span>게임국가기술자격검정</span></a></li>
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
	<td>
		<p><img src="/images/portal/common/h4_45.gif" alt="차세대핵심인력양성" /></p>
	<p><img src="/images/portal/common/txt_8_5.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p>차세대 핵심 인력양성 교육과정은 예비취업자 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량과 글로벌 경쟁력 강화를 목적으로
	방송영상, 게임, 기획창작 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_01.gif" alt="개요" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 고도 창작 역량을 갖춘 전문인 육성</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육대상 : 콘텐츠 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자, 관련분야 경력자</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육운영 : 5~11개월 / 장기교육 운영</p>


	<table cellspacing="0" summary="차세대 핵심 인력양성 교육운영 교육과정 및 기간 상세 내역입니다." class="basicTable tableTh01">
		<caption>차세대 핵심 인력양성 교육운영 교육과정 및 기간</caption>
		<thead>
			<tr>
				<th>분야</th>
				<th width="30%">과정명</th>
				<th>교육일정</th>
				<th>교육기간</th>
				<th>교육인원</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th scope="row" >합계</th>
				<td>13개 과정</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>195명</td>
			</tr>
		</tfoot>
		<tbody>
			<tr>
				<th scope="row" rowspan="5">방송·영상</th>
				<td class="alignL">VFX 모델링·애니메이션</td>
				<td>'11.03~'11.12</td>
				<td>10개월</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">VFX 합성 랜더링</td>
				<td>'11.03~'11.12</td>
				<td>10개월</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">모션 그래픽</td>
				<td>'11.03~'11.11</td>
				<td>9개월</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">디지털 음향제작</td>
				<td>'11.05~'11.09</td>
				<td>5개월</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">C4D for Motiongrapher </td>
				<td>'11.05~'11.09</td>
				<td>5개월</td>
				<td>15명</td>
			</tr>	
			<tr>
				<th scope="row"  rowspan="5">게임제작</th>
				<td class="alignL">게임  디자인(기획)</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">게임 프로그램</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">게임 그래픽</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>10명</td>
			</tr>
            <tr>
				<td class="alignL">스마트폰 게임</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>10명</td>
			</tr>
            <tr>
				<td class="alignL">게임 프로젝트</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>40명</td>
			</tr>
			<tr>
				<th scope="row"  rowspan="3">기획창작/비즈니스</th>
				<td class="alignL">콘텐츠  기획창작</td>
				<td>'11.03~'12.01</td>
				<td>11개월</td>
				<td>20명</td>
			</tr>
            <tr>
				<td class="alignL">콘텐츠 프로젝트</td>
				<td>'11.03~'11.09</td>
				<td>6개월</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">국제 콘텐츠 비즈니스</td>
				<td>'11.03~'11.09</td>
				<td>6개월</td>
				<td>15명</td>
			</tr>
		</tbody>
	</table>

	<Br>
	<Br>
	<p><img src="/images/portal/common/stit_02.gif" alt="운영방향" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;국내·외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_03.gif" alt="운영방향" /></p>
	&nbsp;
		<img src="/images/portal/common/20110727add/b_01_process_01_1.gif" alt="협업교육요구조사" />
		<img src="/images/portal/common/20110727add/b_01_process_01_2.gif" alt="교육과정개발" />
		<img src="/images/portal/common/20110727add/b_01_process_01_3.gif" alt="교육신청/선발(1~2월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_4.gif" alt="교육과정(3~11월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_5.gif" alt="최종교육평가(12월)" />
		<img src="/images/portal/common/20110727add/b_01_process_01_6.gif" alt="취업 지원" />
	<p>&nbsp;&nbsp;&nbsp;※ 교육과정별 운영방법과 일정은 일부 차이가 있습니다.</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 특전</b></p>
	<table cellspacing="0" summary="차세대 핵심 인력양성 교육 특전 내역입니다." class="basicTable tableTh01" >
		<caption>차세대 핵심 인력양성 교육 특전</caption>
		<thead>
			<tr>
				<th width="25%">과정명</th>
				<th>내용</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>공통</td>
				<td class="alignL">
					<p>&nbsp;- 월 316,000원 장학금 지급</p>
					<p>&nbsp;- 한국콘텐츠진흥원 교육프로그램 우선 참여 기회 제공</p>
					<p>&nbsp;&nbsp;&nbsp;&nbsp;※ 사이버콘텐츠아카데미, 전략지역 연수과정 등</p>
					<p>&nbsp;- 국내 컨퍼런스 및 전시회 참가 지원</p>
				</td>
			</tr>
			<tr>
				<td>기획·창작</td>
				<td class="alignL">
					<p>&nbsp;- 프로젝트 완성화를 위한 전담 멘토, 전문가활용 기회 확대 제공</p>
				</td>
			</tr>
			<tr>
				<td>국제콘텐츠 비즈니스</td>
				<td class="alignL">
					<p>&nbsp;- 수료 후 우수자에게 관련분야 연수, 인턴쉽, 교환학생 기회 제공</p>
				</td>
			</tr>
		</tbody>
	</table>
	<Br>
	<Br>


	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;방송영상분야 : 제작인력양성팀 김희숙 과장 (02-2161-0072, <a href="mailto:hskim@kocca.kr">hskim@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;게임제작분야 : 제작인력양성팀 주성호 과장 (02-2161-0078, <a href="mailto:thinkju@kocca.kr">thinkju@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;기획창작/비지니스분야 : 제작인력양성팀 유윤옥 과장 (02-3219-6536, <a href="mailto:inami@kocca.kr">inami@kocca.kr</a>)</p>
	</td>
  </tr>
</table>

<%} %>

</form>
</body>
</html>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


