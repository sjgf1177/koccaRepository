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
		document.form1.p_process.value = "edu";
		document.form1.action = "/servlet/controller.homepage.HomePageAboutUsServlet";
		document.form1.submit();
	}

</script>

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
           		<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_02_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_03_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_04_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('6');" class="tab_online<%= box.get("tabid").equals("6") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_05_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('7');" class="tab_online<%= box.get("tabid").equals("7") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_06_on.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('8');" class="tab_online<%= box.get("tabid").equals("8") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_07_off.gif" /></a></span>
				<span style="padding-right: 1px; float: left; margin: 0px;"><a href="javascript:selectArea('9');" class="tab_online<%= box.get("tabid").equals("9") ? " tab_online_on" : "" %>"><img src="/images/portal/homepage_renewal/academy/tab_08_off.gif" /></a></span>
			</td>
		  </tr>
		  <tr><td><img src="/images/portal/homepage_renewal/academy/line_tabunder.gif" /></td></tr>
		  <tr><td>
            <div id="tab6" style="padding-top: 20px;">
              <table width="671"  border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td><p><img src="/images/portal/homepage_renewal/academy/cont_tit05.gif" alt="기업참여형 교육과정" /></p>
                    <p class="mgt10"><img src="/images/portal/homepage_renewal/academy/cont_stit.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
                    <Br>
                    <Br>
                    <p>창의인재양성사업은 문화콘텐츠산업 국내외 전문가들이 프로젝트에 함께 참여하면서 현장 중심의 실무교육을 통해 노하우를 전수 받도록 인턴십을 지원하는 프로그램으로, 콘텐츠 창조분야에 특화한 멘토와 창의인재 간 창의숙성을 위한 도제식 순련과정을 통해 청년일자리를 창출하는 사업입니다.</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle04.gif" alt="개요" /></p>                    
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;사업 및 교육 대상 : 창작의 소질과 소양을 갖춘 예비취업자 및 콘텐츠 관련 기관</p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;사업형태 : 지원사업</p>
                    <Br>
                    <Br>
                    <p id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle05.gif" alt="운영방향" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;창의숙성과정</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 콘텐츠분야 창의숙성과정 운영이 가능한 학교와 기업을‘플랫폼기관’으로 선정하여 운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 현장 경력 10년 이상의 전문가를 창의숙성과정 ‘멘토’로 발굴해 개별 교육 실시</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 플랫폼기관 선정 → 멘토 확정 → 교육생 모집 및 선정 → 프로젝트 교육 진행 (10개월 내외)</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 교육생 모집은 플랫폼기관을 통해 이루어지며, 모집일정은 진흥원 홈페이지와 아카데미 홈페이지 공고 예정 (3~4월)</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선정 교육생 대상으로 프로젝트 기간 중 인턴십 비용 지급</p>
                    <br/>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;온라인커뮤니티‘스마트월드’구축 및 교육운영</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 창의숙성과정에 참여하는 멘토 및 교육생 DB구축 및 커뮤니티 공간 마련</p>
                    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 사업참여자 이력,포트폴리오,결과물의 효율적 관리 및 정기 교육 실시 등의 사업지원 시스템 구축</p>
                    <Br>
                    <Br>
                    <p  id="s_stit"><img src="/images/portal/homepage_renewal/academy/s_stitle07.gif" alt="교육관련 문의" /></p>
                    <p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/homepage_renewal/academy/s_bul.gif" align="absmiddle" />&nbsp;제작인력양성팀 지현승 대리 (02-3219-6537, <a href="mailto:hsji@kocca.kr">hsji@kocca.kr</a>)</p></td>
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
<link rel="stylesheet" href="/css/20110727.css" type="text/css">    
<table width="690"  border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td>
		<p><img src="/images/portal/common/h4_42.gif" alt="3D 입체콘텐츠 전문인력양성" /></p>
	<p><img src="/images/portal/common/txt_8_2.gif" alt="한국콘텐츠진흥원이 추진하는 인력양성사업 안내입니다." /></p>
	<Br>
	<Br>

	<p>기업참여형 교육과정은 문화콘텐츠산업 국내외 전문가들이 프로젝트에 함께 참여하면서 현장 중심의 실무교육을 통해 노하우를<br />
	전수 받도록 전수 받도록 인턴십을 지원하는 프로그램과 지역의 문화산업 발전을 위해 아카데미 교육을 실시토록 지원하는<br />
	프로그램으로 운영됩니다.</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_01.gif" alt="개요" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;사업목적 : 프로듀서,디렉터,작가 등 국내외 전문가를 활용, 실무교육 및 프로젝트 운영을 통해 글로벌 경쟁력을 갖춘 핵심인재를 양성함</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;사업 및 교육대상 : 문화콘텐츠산업 종사자 및 관련 업체</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;사업형태 : 지원사업</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_10.gif" alt="사업내용" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;국내 전문가 현장 프로젝트 도제식 교육 통한 인턴십 지원</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 도제교육 가능 분야 및 전문가 선별</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 전문가가 직접 교육생 선발 후 프로젝트형 교육 진행(10개월 내외)</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 교육생 모집은 전문가의 자체 모집으로 이루어지며, 일부 공모를 통해 교육생 선발이 있을 수 있음</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선정 교육생 대상으로 인턴십 비용 지급</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;지역아카데미 지원 통한 지역 콘텐츠 전문인력양성</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 지원대상 : 지역 아카데미</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 선정방식 : 기존 운영하던 게임아카데미 중 평가 통해 선정 후 개별 교육 실시</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 세부 교육생 모집은 선정 된 지역아카데미에서 자체 실시함</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;해외전문가 초청 워크숍 및 세미나 개최</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 콘텐츠 산업 해외 전문가를 초청하여 글로벌 노하우 전수의 기회를 국내 콘텐츠산업 종사자에게 제공함</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 스토리, 3D 등 국내 취약 분야 및 최신 트랜드 고려하여 초청강사 선정</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 교육내용 : 워크숍 및 세미나, 멘토링 개최 (1주 이내, 연 1~2회)</p>
	<Br>
	<Br>

	<p><img src="/images/portal/common/stit_11.gif" alt="사업추진일정" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;기업참여형 교육 : 4월 ~ 12월</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;지역아카데미 운영 : 5월 ~ 12월</p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;해외전문가 초청 워크숍 및 세미나 : 9월 ~ 12월</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;※ 일정은 변동될 수 있음</p>
	<Br>
	<Br>


	<p><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" /></p>
	<p>&nbsp;&nbsp;&nbsp;<img src="/images/portal/common/bul_dot01.gif" align="absmiddle" />&nbsp;제작인력양성팀 지현승 대리 (02-3219-6537, <a href="mailto:hsji@kocca.kr">hsji@kocca.kr</a>)</p>
	</td>
  </tr>
</table>

<%} %>
<input type="submit" class="close" title="실행">
</form>
<!-- form 끝 -->

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->


