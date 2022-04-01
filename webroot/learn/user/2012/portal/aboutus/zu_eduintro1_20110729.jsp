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
    <td>차세대 인재 양성과정은 미래 콘텐츠 산업을 이끌 청년 리더를 대상으로 고도의 창작역량과 디지털 콘텐츠 제작 기술력을 <br />
    갖춘 대한민국 1%의 창의 인재양성을 위해 문화체육관광부의 지원으로 추진되며 교육비는 국고 지원으로 운영되는 인재<br />
    양성프로그램입니다.</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_01.gif" alt="개요" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 운영목적 : 대한민국 콘텐츠 분야의 1%의 글로벌 창의 인재 양성 <br />
      - 교육대상 : 콘텐츠 관련학과 졸업자, 콘텐츠 관련분야 경험자 <br />
      - 교육운영 : 5~10개월 / 중ㆍ장기 교육 운영 <br />
    - 교육비&nbsp; : 50~100만원 </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_02.gif" alt="운영방안" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">- 콘텐츠 제작분야&nbsp; 우수 인재 선발, 실무형 핵심 엘리트 양성 <br />
      - 3D입체, 게임, 기획창작, 비지니스 등 콘텐츠분야 창작 전문 인력 양성 <br />
      - 국내외 최고 강사진 활용, 영상 콘텐츠산업계 Job-matching 교육 추진 <br />
    - 콘텐츠 산업계 엘리트 청년 인력 공급 체계 마련 <br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_03.gif" alt="운영방법" class="tit" /></td>
  </tr>
  <tr>
    <td height="25" class="pd_l15"><strong>① 교육 신청/ 선발 -&gt; ② 교육과정 운영 -&gt; ③ 최종교육평가 -&gt; ④ 취업 지원</strong></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육 신청/선발</strong></td>
  </tr>
  <tr>
    <td class="pd_l25">- 교육 신청/선발 <br />      - 신청방법 : 온라인 신청 <br />
      - 제출서류 : (공통)교육지원서 <br />      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(선택)포트폴리오, 관련분야 자격증 <br />
      - 선발절차 : 1차 서류심사 -&gt;2차 전문가 구술 면접 <br />
      - 선발방법 : 원내외 교육 대상자 선발 심사위원회 선발 <br />      &nbsp; &bull; 1차 서류심사 : 전공유무, 학업계획, 관련분야 경력 등 심사 <br />      &nbsp; &bull; 2차 전문가 구술 면접 : 지원분야 기초지식, 경력, 학업계획 등 심사 <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>교육과정 운영 </strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 교육방법 : 강의 및 실습교육, 제작현장 실습 교육, 프로젝트 제작 <br />
      - 교육내용 : 관련분야 실무 능력 배양 중심의 커리큘럼 구성 <br />
      - 교육강사 : 현장실무전문가, 학계전문가 등으로 구성, 운영 <br />
      - 교육시스템 및 장비 : 국내 최고의 디지털 제작시스템 및 교육 장비를 활용한 <br />
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제작 실무 중심의 과정 운영 <br />
    - 교육생관리 : 개인 실무 역량 및 이력 강화를 위한 출결, 성적, 이력 관리 <br /></td>
  </tr>
  <tr>
    <td class="pd_l15">&nbsp;</td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong>취업지원</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"><p>        - 성적 우수자 우선 취업 기회 제공 <br />
      - 콘텐츠 제작사와 연계한&nbsp; 취업지원 프로그램 운영 <br />
      - 채용박람회, 전시회(지스타 등) 작품 전시를 통한 취업 지원 <br />
      - 교육생 DB 구축을 통한 체계적 취업 관리 운영 <br />
    </p></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_04.gif" alt="교육비 안내" class="tit" /></td>
  </tr>
  <tr>
    <td height="20" class="pd_l15"><img src="/images/portal/ico/ico_bul_gcircle.gif" width="13" height="13" align="absmiddle" /> <strong> 계좌이체 및 카드결제</strong></td>
  </tr>
  <tr>
    <td class="pd_l25"> - 계좌이체 : 우리은행 1005-500-541698 / 예금주 : 한국콘텐츠진흥원<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(입금확인 후 교육 종료시 계산서 발급)<br />
- 카드결제 : 한국콘텐츠 아카데미 홈페이지(http://edu.kocca.or.kr)에서 온라인 결제<br /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><img src="/images/portal/common/stit_05.gif" alt="교육관련 문의" class="tit" /></td>
  </tr>
  <tr>
    <td class="pd_l15">&bull; 방송영상분야 : ☎ 02-2161-0072 / e-mail : offline@kocca.kr / Fax : 02-2161-0078 <br />  &bull; 게임제작분야 : ☎ 02-3219-6541 / e-mail : offline@kocca.kr / Fax : 02-3219-6501 <br />  &bull; 콘텐츠기획분야 : ☎ 02-3219-6545 / e-mail : offline@kocca.kr / Fax : 02-3219-6501
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


