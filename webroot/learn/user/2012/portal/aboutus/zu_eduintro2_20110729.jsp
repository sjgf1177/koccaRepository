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

<form name="form1" method="post">
    <input type = "hidden" name = "p_process">
    <input type = "hidden" name = "gubun" value = "7">
    <input type = "hidden" name = "menuid">
    <input type = "hidden" name = "tabid" value="<%= box.get("tabid") %>">
    
<% if      ("1".equals(box.get("tabid")) || "".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>차세대인재양성교육</strong></p></h2><% }
   else if ("2".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>산업계직무재교육</strong></p></h2><% }
   else if ("3".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>온라인교육</strong></p></h2><% }
   else if ("4".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내" 	  class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>수탁교육</strong></p></h2><% }
   else if ("5".equals(box.get("tabid"))) { %> <h2><img src="/images/portal/online/h2_tit7.gif" alt="교육사업안내"     class="fl_l" /><p class="category">Home &gt; 아카데미소개 &gt; <strong>국제인증과정</strong></p></h2><% } 
%>
    
    <ul class="tabwrap">
		<li><a href="javascript:selectArea('1');" class="tab_online<%= box.get("tabid").equals("1") || box.get("tabid").equals("") ? " tab_online_on" : "" %>"><span>차세대인재양성교육</span></a></li>
		<li><a href="javascript:selectArea('2');" class="tab_online<%= box.get("tabid").equals("2") ? " tab_online_on" : "" %>"><span>산업계직무재교육</span></a></li>
		<li><a href="javascript:selectArea('3');" class="tab_online<%= box.get("tabid").equals("3") ? " tab_online_on" : "" %>"><span>온라인교육</span></a></li>
		<li><a href="javascript:selectArea('4');" class="tab_online<%= box.get("tabid").equals("4") ? " tab_online_on" : "" %>"><span>수탁교육</span></a></li>
		<li><a href="javascript:selectArea('5');" class="tab_online<%= box.get("tabid").equals("5") ? " tab_online_on" : "" %>"><span>국제인증과정</span></a></li>
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
    <td>산업계 직무 재교육과정은 콘텐츠 산업계 재직자를 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량 및 글로벌 경쟁력 강화를 목적으로 3D입체, CG, 게임, HD 제작 분야 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_01.gif" alt="개요" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 고도 창작 역량을&nbsp;갖춘 전문인 육성 <br />
      - 교육대상 : 현업인 대상의 콘텐츠 분야 종사자 <br />
      - 교육운영 : 1-10일 / 단기 교육 운영 <br />
- 교육내용 : 
편집, 음향, CGI, HD제작, 3D입체, 게임, 경영 및 비즈니스, 공익정책 과정 등 </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_02.gif" alt="운영방안" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공&nbsp; <br />
      - 분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영 <br />
      - 국내ㆍ외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공 <br />
      - 풍부한 현장 경험과 강의 경험을 보유한 국내ㆍ외 전문 강사진을 활용 교육 서비스 제공 <br />
    - 철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고&nbsp;&nbsp; <br /></td>
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
    <td class="pd_l25">- 신청방법 : 온라인 신청 / http://edu.kocca.or.kr <br />
    - 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등 </td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육과정 운영 </strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 교육방법 : 강의 및 실습교육, 제작현장 실습 교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영 <br />
      - 교육강사 : 현장실무전문가, 학계전문가 등 <br />
      - 교육시스템 및 장비 : 국내 최고의 디지털 제작 시스템 및 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영 <br />
    - 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재) <br /></td>
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
    <td class="pd_l25">-계좌이체 : 우리은행 1005-500-541698 / 예금주 : 한국콘텐츠진흥원 <br />      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(입금확인 후 교육 종료시 계산서 발급) <br />
    - 카드결제 : 한국콘텐츠 아카데미 홈페이지(http://edu.kocca.or.kr)에서 온라인 결제 <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 계노동부 교육비 환급</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 노동부 직업능력개발 훈련과정으로 지정된 교육과정에 한해 고용보험 가입자가 납부한 교육비의 20～30% 환급(노동부) <br />
      - 교육 종료 후 해당지역 노동사무소에 교육비 환급신청서를 제출 <br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 교육취소 및 교육비 환불</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 교육비는 과정별 납부기간 내에 완납하여야 하며, 사정에 의한 교육불참시 교육시작 7일 전까지 통보시 전액 환불, <br />
      &nbsp;그 이후의 교육취소 통보에는 환불 불가 <br />
&nbsp;      (본원의 오프라인 교육은 제작 장비가 수반되어 교육대상자의 사전 확정이 필수적으로 요망됨)&nbsp;<br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 폐강</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 참가인원이 교육정원의 50% 이하일 경우에는 교육이 취소될 수 있으며 이 경우, 납부한 교육비는 전액 환불해 드립니다.<br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>참고사항</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 교육비 일부는 국고에서 지원되며 지방참가자의 경우, 숙식비는 소속사 부담임<br /></td>
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
    <td class="pd_l15">&bull; 방송영상분야 : ☎ 02-2161-0072 / e-mail : offline@kocca.kr / Fax : 02-2161-0078 <br />      &bull; 게임제작분야 : ☎ 02-3219-6541 / e-mail : offline@kocca.krr / Fax : 02-2161-0078
<p></p></td>
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


