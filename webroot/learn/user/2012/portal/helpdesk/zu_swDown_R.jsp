<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","5");
    box.put("submenu","4");

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
     }
    
    
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->

<SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}

function move(tab){
	document.form1.p_process.value= "Help";
	document.form1.p_code.value = tab;
	document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
	document.form1.submit();
}

</SCRIPT>

<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post" action="">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">
		<input type = "hidden" name = "p_code"      value = "">
		
		<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고있는 콘텐츠의 위치입니다.">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>학습환경 도우미</u></td>
			</tr>
		</table>
		<div class="concept d04">
			<div><img src="/images/2012/sub/page4/title/help.gif" alt="아카데미 온라인 교육 전 학습환경을 설정할 수 있습니다"></div>
			<ul class="pagetab">
				<li><a href="javascript:menuForward('4','05');" tabindex="121" title="기획 과정만 확인합니다"><img src="/images/2012/sub/page4/tabmenu/test_off.png" alt="학습환경 테스트"></a></li>
				<li><img src="/images/2012/sub/page4/tabmenu/sw_on.png" alt="소프트웨어 다운로드"></li>
			</ul>
		</div>
		<br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="122" title="온라인강의를 수강하기 위한 필요 소프트웨어입니다">강의필수 소프트웨어</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="123" title="특정 강의시 필요한 전문 툴(시험버전)을 다운로드할 수 있습니다">전문 툴 시험버전</a></dd>
		</dl>
		<table class="swdownload" cellpadding="0" cellspacing="0" id="skip01" summary="학습에 필요한 액티브 소프트웨어를 다운받아 설치할 수 있습니다">
			<tr>
				<th id="sw1"><div><img src="/images/2012/sub/page4/guide/icon_flash_player.gif" alt="flash player(플래시 플레이어)"></div></th>
				<td name="sw1">
					<h4>Adobe Flash Player</h4>
					<h6>온라인과정의 교육은 대부분 플래시로 제작되어 있습니다. 학습을 하기 위해서는 이 S/W가 필수입니다.</h6>
					<a href="http://get.adobe.com/kr/flashplayer/" target="_blank" tabindex="181" title="어도비 플래시 플레이어 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw2"><div><img src="/images/2012/sub/page4/guide/icon_shokwave.gif" alt="Adobe ShockWave Player(어도비 쇼크웨이브 플레이어)"></div></th>
				<td name="sw2">
					<h4>Adobe ShockWave Player</h4>
					<h6>ShockWave로 제작된 강의 또는 미디어를 보기 위해서는 이 S/W가 필요합니다.</h6>
					<a href="http://get.adobe.com/kr/shockwave/" target="_blank" tabindex="182" title="어도비 쇼크웨이브 플레이어 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
			<tr>
				<th id="sw3"><div><img src="/images/2012/sub/page4/guide/icon_real.gif" alt="RealPlayer(리얼 플레이어)"></div></th>
				<td name="sw3">
					<h4>RealPlayer</h4>
					<h6>ra, rm 등으로 이루어진 강의를 볼 수 있는 프로그램입니다.</h6>
					<a href="http://kr.real.com/" target="_blank" tabindex="183" title="리얼 플레이어 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw4"><div><img src="/images/2012/sub/page4/guide/icon_java.gif" alt="Java 6(자바6)"></div></th>
				<td name="sw4">
					<h4>ORACLE Java 7</h4>
					<h6>XP초기버전 사용자는 자바 가상머신이 설치되어 있지 않습니다. 강의를 받기 위해서는 필수인 S/W입니다.</h6>
					<a href="http://java.com/ko/download/index.jsp" target="_blank" tabindex="184" title="오라클 자바7 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
			<tr>
				<th id="sw5"><div><img src="/images/2012/sub/page4/guide/icon_winmedia.gif" alt="Windows Media Player(윈도우 미디어 플레이어)"></div></th>
				<td name="sw5">
					<h4>Windows Media Player</h4>
					<h6>강의 중 동영상이 나오지 않을 경우 이 S/W를 설치해야 하는 경우가 있습니다.</h6>
					<a href="http://www.microsoft.com/ko-KR/download/details.aspx?id=20426" tabindex="185" title="윈도우 미디어 플레이어 다운로드 홈페이지로 이동합니다 (새창)" target="_blank"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw6"><div><img src="/images/2012/sub/page4/guide/icon_hangul.gif" alt="hangul viewer(한글 뷰어)"></div></th>
				<td name="sw6">
					<h4>한글과 컴퓨터 오피스 뷰어 2010 SE+</h4>
					<h6>강의자료 중 한글파일(hwp)로 작성된 자료가 있습니다. 자료를 확인하실 때 설치합니다.</h6>
					<a href="http://www.hancom.com/downLoad.downPU.do?mcd=002" tabindex="186" title="한글과 컴퓨터 오피스 뷰어 다운로드 홈페이지로 이동합니다 (새창)" target="_blank"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
			<tr>
				<th id="sw7"><div><img src="/images/2012/sub/page4/guide/icon_leader.gif" alt="Adobe Reader X(어도비 리더 X)"></div></th>
				<td name="sw7">
					<h4>Adobe Reader X</h4>
					<h6>강의자료 중 PDF파일로 된 아크로뱃리더 파일이 있습니다. 자료를 확인하실 때 설치합니다.</h6>
					<a href="http://get.adobe.com/kr/reader/?promoid=HTEHD" target="_blank" tabindex="187" title="어도비 리더 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw8"><div><img src="/images/2012/sub/page4/guide/icon_ppt.gif" alt="MS Office PowerPoint Viewer(마이크로소프트 오피스 파워포인트 뷰어)"></div></th>
				<td name="sw8">
					<h4>MS Office PowerPoint Viewer</h4>
					<h6>강의자료 중 파워포인트(ppt)파일로 작성된 자료가 있습니다. 자료를 확인하실 때 설치합니다.</h6>
					<a href="http://www.microsoft.com/ko-kr/download/details.aspx?id=13" target="_blank" tabindex="188" title="마이크로소프트 오피스 파워포인트 뷰어 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
		</table>
		<table class="swdownload" cellpadding="0" cellspacing="0" id="skip02" summary="특정 과정 학습시 필요한 무료체험버전 소프트웨어를 다운받을 수 있습니다">
			<tr>
				<th id="sw9"><div><img src="/images/2012/sub/page4/guide/icon_flash.gif" alt="Adobe Flash CS6 (무료시험버전)(어도비 플래시 CS6)"></div></th>
				<td name="sw9">
					<h4>Adobe Flash CS6 (무료 시험버전)</h4>
					<h6>게임,영화 및 애니메이션 등 다양한 분야에서 주로 사용되는 툴입니다. </h6>
					<a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=flash&loc=ko" target="_blank" tabindex="191" title="어도비 플래시 CS6 (무료시험버전) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw10"><div><img src="/images/2012/sub/page4/guide/icon_aftereffect.gif" alt="Adobe After Effect CS6 (무료 시험버전)(어도비 에프터 이펙트 CS6)"></div></th>
				<td name="sw10">
					<h4>Adobe After Effect CS6 (무료 시험버전)</h4>
					<h6>영상 특수효과를 제작할 수 있는 툴입니다. 영화 및 게임에서 인지도가 매우 높습니다.</h6>
					<a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=after_effects" target="_blank" tabindex="192" title="어도비 에프터이펙트 CS6 (무료시험버전) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
			<tr>
				<th id="sw11"><div><img src="/images/2012/sub/page4/guide/icon_premiere.gif" alt="Adobe Premiere CS6 (무료시험버전)(어도비 프리미어 CS6)"></div></th>
				<td name="sw11">
					<h4>Adobe Premiere Pro CS6 (무료 시험버전)</h4>
					<h6>영화, 애니메이션등의 영상을 편집과 인코딩을 할 수 있는 전문 툴입니다.</h6>
					<a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=premiere_pro" target="_blank" tabindex="193" title="어도비 프리미어 CS6 (무료시험버전) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw12"><div><img src="/images/2012/sub/page4/guide/icon_photoshop.gif" alt="Adobe Photoshop CS6 (무료 시험버전)(어도비 포토샵 CS6)"></div></th>
				<td name="sw12">
					<h4>Adobe Photoshop CS6 (무료 시험버전)</h4>
					<h6>모든 이미지 및 사진을 편집하거나 제작할 수 있는 대표적인 툴입니다.</h6>
					<a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=photoshop" target="_blank" tabindex="194" title="어도비 포토샵 CS6 (무료시험버전) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
			<tr>
				<th id="sw13"><div><img src="/images/2012/sub/page4/guide/icon_3dsmax.gif" alt="Autodesk 3ds Max (무료체험판)(오토데스크 3Ds 맥스)"></div></th>
				<td name="sw13">
					<h4>Autodesk 3ds Max (무료체험판)</h4>
					<h6>게임,영화,애니메이션,이미지 등 3D구현의 가장 널리 사용되는 툴입니다.</h6>
					<a href="http://www.autodesk.co.kr/adsk/servlet/download/item?siteID=1169528&id=17089546" target="_blank" tabindex="195" title="오토데스크 3Ds 맥스 (무료체험판) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
				<th id="sw14"><div><img src="/images/2012/sub/page4/guide/icon_maya.gif" alt="Autodesk Maya (무료체험판)(오토데스크 마야)"></div></th>
				<td name="sw14">
					<h4>Autodesk Maya (무료체험판)</h4>
					<h6>3D애니메이션 제작에 특화된 대표적인 3D그래픽 툴입니다.</h6>
					<a href="http://www.autodesk.co.kr/adsk/servlet/download/item?siteID=1169528&id=17089766" target="_blank" tabindex="196" title="오토데스크 마야 (무료체험판) 다운로드 홈페이지로 이동합니다 (새창)"><img src="/images/2012/sub/page4/btn_godownload.gif" alt="다운로드 바로가기"></a>
				</td>
			</tr>
		</table>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
