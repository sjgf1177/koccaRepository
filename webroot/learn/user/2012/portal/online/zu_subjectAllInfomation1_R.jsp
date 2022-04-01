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
      <td height="20px"></td>
  </tr>
  <tr>
    <td >온라인교육과정은 콘텐츠산업의국가 경쟁력 제고 및 콘텐츠에 대한 다양한 교육기회 제공을 목적으로 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야 등의 교육 콘텐츠를 개발하여 온라 교육시스템을 활용해 운영되는 교육프로그램입니다.</td>
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