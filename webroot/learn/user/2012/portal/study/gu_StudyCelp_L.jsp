<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String  v_user_id   = box.getSession("userid");
    box.put("topmenu","2");
	box.put("submenu","8");
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
function goCelp(userid) {
    var ulink = "http://koccacelp.campus21.co.kr/Start.asP?UserID="+ userid;
    var new_Open = window.open(ulink,"celp",'scrollbars=no,width=1050,height=700,scrollbars=yes,resizable=no');
}
//-->
</SCRIPT>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->


<div class="content">
	<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
		<tr>
			<td class="pagetitle"></td>
			<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 나의강의실 > <u>자기역량진단</u></td>
		</tr>
	</table>
	<div><img src="/images/2012/sub/page1/about_celp.gif" alt="자기역량진단이란? 비전 진단과 리더십 진단을 통해 조직의 비전 및 목표를 달성하는데 필요한 역량을 확인하는 교육 서비스를 말합니다."></div>
	<div class="fixing">
		<div class="checkpoint">
			<dl class="celpdetail">
				<dt><img src="/images/2012/sub/page1/celp_part.gif" alt="자기역량진단 프로그램 체계"></dt>
				<dd>커뮤니케이션, 코칭, 네트워킹, 문제해결, 성과관리, 전문성 추구, 변화 혁신, 팀 빌딩, 팀웍 등의 체계적인 리더십 진단과 지배, 안정, 성휘, 친애, 자율 등의 비전 진단을 통해 비전 및 목표를 달성하는데 필요한 역량을 제시합니다.</dd>
			</dl>
			<dl class="celpdetail">
				<dt><img src="/images/2012/sub/page1/celp_check.gif" alt="지난 진단 결과와 현재 진단 결과의 대조"></dt>
				<dd>지난 진단 결과를 재확인 할 수 있습니다. 지난 결과와 현재의 진단 결과를 통해 본인의 역량 발전의 정도를 확인하여, 미흡한 역량 부분을 발전시킬 수 있습니다. </dd>
			</dl>
		</div>
	</div>
	<div class="opencelp"><a href="javascript:goCelp('<%=v_user_id%>')" title="(새창) 내 역량 진단하기"><img src="/images/2012/sub/page1/btn_celp.gif" alt="내 역량 진단하기"></a></div>
</div>

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->