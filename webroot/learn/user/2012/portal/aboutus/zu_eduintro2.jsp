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
<form name="form1" method="post" action="/" >
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
          		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_01_off.gif" /></a></span>              	
           		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_02_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab2" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit02.gif" alt="산업계직무재교육과정" /></p>
                    <p class="mgt10"> <img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>산업계 직무 재교육과정은 콘텐츠 산업계 재직자를 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량 및 글로벌 경쟁력 강화를 목적으로 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 창작 역량을 갖춘 전문인 육성</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육대상 : 현업인 대상의 콘텐츠 분야 종사자</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육운영 : 1-10일 / 단기 교육 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;교육과정</p>
                    <table border="0" cellpadding="3" cellspacing="0" id="tab_form2" summary="산업계 직무 재교육 과정 및 기간 상세 내역입니다.">
                      <colgroup>
                      <col width="15%" />
                      <col width="" />
                      <col width="20%" />
                      <col width="10%" />
                      </colgroup>
                      <thead>
                        <tr>
                          <td class="tit_table">분야</td>
                          <td class="tit_table">과정명</td>
                          <td class="tit_table">교육기간</td>
                          <td class="tit_table">교육인원</td>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th rowspan="12" scope="row">국가인적<br/>
                          	자원개발<br/>			                         
				                        중소기업<br/>
				                        컨소시엄<br/>
				                        직무과정<br/>
							(노동부)
							</th>
                          <td>Final Cut Pro과정</td>
                          <td align="center">'12.01.09~01.13</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>iPhone 제작과정</td>
                          <td align="center">'12.01.09~01.13</td>
                          <td align="center">15</td>
                        </tr>
                        <tr>
                          <td>콘텐츠 기획서 작성 전략  과정</td>
                          <td align="center">'12.02.20~2.21</td>
                          <td align="center">15</td>
                        </tr>
                        <tr>
                          <td>비즈니스 스킬업 과정</td>
                          <td align="center">'12.02.1~2.2</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>Protools 강사과정</td>
                          <td align="center">'12.02.13~02.17</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>디지털미디어 워크플로우 과정</td>
                          <td align="center">'12.03.12~03.14</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>레벨 디자인 방법론 과정</td>
                          <td align="center">'12.04.4~04.6</td>
                          <td align="center">10</td>
                        </tr>
                        <tr>
                          <td>콘텐츠기획(영화,드라마,웹툰,애니,공연)과정</td>
                          <td align="center">'12.05.8~05.10</td>
                          <td align="center">10</td>
                        </tr>
                        <tr>
                          <td>Avid MC 101과정</td>
                          <td align="center">'12.02.6~2.10</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>Final Cut Pro과정</td>
                          <td align="center">'12.02.20~2.24</td>
                          <td align="center">5</td>
                        </tr>
                        <tr>
                          <td>상표권, 저작권, 라이센싱  계약 사례 과정</td>
                          <td align="center">'12.06.11~06.15</td>
                          <td align="center">10</td>
                        </tr>
                        <tr>
                          <td colspan="2" align="center">합 계</td>
                          <td align="center">90</td>
                        </tr>
                        
                        <tr>
                          <th rowspan="10" scope="row">문화콘텐츠<br>
                            직무과정</th>
                          <td>창의적 콘텐츠 소재발견을 위한 세미나</td>
                          <td align="center">‘12.05.16~05.17</td>
                          <td align="center">15</td>
                        </tr>
                        <tr>
                          <td>다문화 콘텐츠 제작의 이해</td>
                          <td align="center">5월 중</td>
                          <td align="center">15</td>
                        </tr>
                        <tr>
                          <td>국제다큐멘터리 공동제작 가이드</td>
                          <td align="center">‘12.06.19~06.20</td>
                          <td align="center">20</td>
                        </tr>
                        <tr>
                          <td>OSMU를 위한 앱 창작과정</td>
                          <td align="center">‘12.09.3~10.12</td>
                          <td align="center">10</td>
                        </tr>
                        <tr>
                          <td>공연예술, 기획에서 수출까지</td>
                          <td align="center">‘12.10.16.~10.17</td>
                          <td align="center">20</td>
                        </tr>
                        <tr>
                          <td>청소년을 위한 창의과정 해피미디어 세상</td>
                          <td align="center">10월 중</td>
                          <td align="center">15</td>
                        </tr>
                        <tr>
                          <td colspan="2" align="center">합 계</td>                          
                          <td align="center">95</td>
                        </tr>                        
                      </tbody>
                    </table>
                    <p class="mgt10">※ 상기과정은 변동될 수 있으며 교육내용과 교육일정 등은 한국콘텐츠아카데미(<a href="http://edu.kocca.or.kr" target="_blank">http://edu.kocca.or.kr</a>)을 참조하시기 바랍니다.</p>
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
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</p>
                    <Br>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
                    <Br>
                    <Br>
                    <p  id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;방송영상분야 : 황영성 대리 (02-3219-6540,<a href="mailto:dew1777@kocca.kr">dew1777@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;게임제작분야 : 박승준 차장 (02-3219-6527,<a href="mailto:jun@kocca.kr">jun@kocca.kr</a>)</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;기획창작분야 : 김상완 주임 (02-3219-6532,<a href="mailto:kkarisw@kocca.kr">kkarisw@kocca.kr</a>)<br />
                      <br />
                    </p></td>
                </tr>
              </table>
            </div>
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
		<li><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><span>국제인증과정</span></a></li>
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

	<p><img src="/images/portal/common/h4_46.gif" alt="산업계직무재교육과정" /></p>
	<p><img src="/images/portal/common/txt_8_6.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p>산업계 직무 재교육과정은 콘텐츠 산업계 재직자를 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량 및 글로벌 경쟁력 강화를
	목적으로 3D입체, CG, 게임, HD 제작 분야 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_01.gif" alt="개요" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 창작 역량을 갖춘 전문인 육성</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육대상 : 현업인 대상의 콘텐츠 분야 종사자</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육운영 : 1-10일 / 단기 교육 운영</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;교육과정</p>


	<table cellspacing="0" summary="산업계 직무 재교육 과정 및 기간 상세 내역입니다." class="basicTable tableTh01">
		<caption>산업계 직무 재교육 과정 및 기간</caption>
		<thead>
			<tr>
				<th>분야</th>
				<th width="40%">과정명</th>
				<th>교육기간</th>
				<th>교육인원</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th scope="row" >합계</th>
				<td>30개 과정</td>
				<td>&nbsp;</td>
				<td>500명</td>
			</tr>
		</tfoot>
		<tbody>
			<tr>
				<th scope="row" rowspan="23">중소기업<br>컨소시엄<br>직무과정</th>
				<td class="alignL">iPhone 제작 과정</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">안드로이드 제작 과정</td>
				<td>3일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">UDK 스크립트 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">SNG제작사례 워크샵</td>
				<td>1일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">스마트폰 성공사례 세미나</td>
				<td>1일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">미들웨어(GFX)심화 워크샵</td>
				<td>1일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">콘텐츠 분야 개발 팀장(PD) 리더쉽 과정</td>
				<td>2일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">게임 제작 Risk Mangement 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">기획자를 위한 Lua 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">레벨 디자인 방법론 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">상표권, 저작권, 라이센싱 계약 사례 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">콘텐츠기획(영화,드라마,웹툰,애니,공연) 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">스토리텔링 소재발굴과 구상 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">콘텐츠 기획서 작성 전략 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">해외마케팅 사례분석 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">비즈니스 스킬업 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">HD 특수촬영 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">디지털방송콘텐츠플래너과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">디지털미디어 워크플로우 과정</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">Avid MC 101과정</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">Avid MC 201과정</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">Final Cut Pro과정</td>
				<td>5일</td>
				<td>10명</td>
			</tr>
			<tr>
				<td class="alignL">Protools 강사 과정</td>
				<td>5일</td>
				<td>5명</td>
			</tr>
			<tr>
				<th scope="row" rowspan="10">문화콘텐츠<br>직무과정</th>
				<td class="alignL">문화콘텐츠성공사례(OSMU) 과정</td>
				<td>3일</td>
			    <td>30명</td>
			</tr>
			<tr>
				<td class="alignL">방송분야 교수연수 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">창의적사고(공익,영화,광고,공연) 과정</td>
				<td>2일</td>
				<td>30명</td>
			</tr>
			<tr>
				<td class="alignL">SNS - 미디어, 출판의 방향성 연구 과정</td>
				<td>2일</td>
				<td>20명</td>
			</tr>
			<tr>
				<td class="alignL">게임분야 공무원 교육 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">유아용 교육 콘텐츠 기획 과정</td>
				<td>2일</td>
				<td>20명</td>
			</tr>
			<tr>
				<td class="alignL">(기자대상)문화콘텐츠 이해 과정</td>
				<td>3일</td>
				<td>20명</td>
			</tr>
			<tr>
				<td class="alignL">연예 매니지먼트 과정</td>
				<td>2일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">문화콘텐츠분야 공무원 교육 과정</td>
				<td>3일</td>
				<td>15명</td>
			</tr>
			<tr>
				<td class="alignL">게임분야 교수연수 과정</td>
				<td>2일</td>
				<td>20명</td>
			</tr>
		</tbody>
	</table>
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 상기과정은 변동될 수 있으며 교육내용과 교육일정 등은 한국콘텐츠아카데미(<a href="http://edu.kocca.or.kr" target="_blank">edu.kocca.or.kr</a>)을 참조하시기 바랍니다.</p>

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

	<p><img src="/images/portal/common/stit_03.gif" alt="운영방법" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육 신청/선발</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr">http://edu.kocca.or.kr</a></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</p>
	<Br>

	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;<b>교육과정 운영</b></p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육강사 : 현장 실무전문가, 학계 전문가 등</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</p>
	<Br>
	<Br>


	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;방송영상분야 : 제작인력양성팀 황영성 대리 (02-3219-6540, <a href="mailto:dew1777@kocca.kr">dew1777@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;게임제작분야 : 제작인력양성팀 김용준 과장 (02-3219-6532, <a href="mailto:newtype@kocca.kr">newtype@kocca.kr</a>)</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;기획창작분야 : 제작인력양성팀 신화범 과장 (02-3219-6531, <a href="mailto:hbshin@kocca.kr">hbshin@kocca.kr</a>)</p>

		
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


