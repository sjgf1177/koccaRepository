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
    
    String tap			= "";
    
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

function changeTap(process){
    var objDl   = document.getElementsByName("objDl");
    var objDd   = document.getElementsByName("objDd");


    if(idx == '1'){
    	objDd[0].style.display  = "block";
    	objDd[1].style.display  = "none";
    }else if(idx == '2' ){
    	objDd[0].style.display  = "none";
    	objDd[1].style.display  = "block";
    }    
}  


</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->

<form name = "form1" enctype = "multipart/form-data" method = "post" action="">
	<input type = "hidden" name = "p_process"   value = "">
	<input type = "hidden" name = "p_tab"		value = "">
	<input type = "hidden" name = "p_code"      value = "">
	
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>학습환경 도우미</u></td>
			</tr>
		</table>
		<div class="concept d04">
			<div><img src="/images/2012/sub/page4/title/help.gif" alt="아카데미 온라인 교육 전 학습환경을 설정할 수 있습니다"></div>
			<ul class="pagetab">
				<li><img src="/images/2012/sub/page4/tabmenu/test_on.png" alt="학습환경 테스트"></li>
				<li><a href="javascript:menuForward('4','03');" tabindex="121" title="제작 과정만 확인합니다"><img src="/images/2012/sub/page4/tabmenu/sw_off.png" alt="소프트웨어 다운로드"></a></li>
			</ul>
		</div>
		<br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="122" title="1~3단계로 이동합니다">학습환경 테스트 1~3단계</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="123" title="4~5단계로 이동합니다">학습환경 테스트 4~6단계</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="124" title="7~10단계로 이동합니다">학습환경 테스트 7~10단계</a></dd>
		</dl>
		<div class="internetguide">
			동영상 재생시 문제가 있으시다면, 현재 사용 중인 인터넷 회선의 연결속도를 확인해 주세요.<br>
			회선 속도 테스트는 하루에 20~30분 간격을 두고 반드시 여러차례 해주셔야 정확한 속도를 알 수 있습니다.
		</div>
		<div class="guidestep">
			<table id="skip01" class="stepimage" cellpadding="0" cellspacing="0">
				<colgroup><col width="302"><col width="37"><col width="302"><col width="*"><col width="302"></colgroup>
				<tr>
					<td class="alignL"><img src="/images/2012/sub/page4/guide/test01.gif" class="stepst" alt="Step01 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignC"><img src="/images/2012/sub/page4/guide/test02.gif" class="stepst" alt="Step02 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignR"><img src="/images/2012/sub/page4/guide/test03.gif" class="stepst" alt="Step03 참고사진"></td>
				</tr>
				<tr class="division">
					<td>(Step 01)</td>
					<td></td>
					<td>(Step 02)</td>
					<td></td>
					<td>(Step 03)</td>
				</tr>
			</table>
			<p><span>Step 01.</span> 한국정보화진흥원 사이트에 접속합니다. 접속 후 상단 메뉴에서 <b>품질측정테스트 → 인터넷 속도</b> 또는 메인에 <b>인터넷 속도</b>를 선택합니다. <a href="http://speed.nia.or.kr/" tabindex="172" target="_blank" title="인터넷 품질측정 테스트로 이동합니다."><u>(한국정보화진흥원 바로가기)</u></a></p>
			<p><span>Step 02.</span> 정보 수집 약관 동의화면에서 <b>약관을 읽고 동의 후</b> 속도 측정의 진행을 원한다면, 약관에 동의 후 다음을 클릭합니다.</p>
			<p><span>Step 03.</span> 측정을 처음 시도하는 경우 다음과 같은 웹 페이지의 메시지가 나옵니다. 테스트 진행을 위해서는 "<b>확인</b>"버튼을 클릭하여 설치프로그램을 다운로드합니다.</p>
			<br><br>
			<table id="skip02" class="stepimage" cellpadding="0" cellspacing="0">
				<colgroup><col width="302"><col width="37"><col width="302"><col width="*"><col width="302"></colgroup>
				<tr>
					<td class="alignL"><img src="/images/2012/sub/page4/guide/test04.gif" class="stepst" alt="Step04 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignC"><img src="/images/2012/sub/page4/guide/test05.gif" class="stepst" alt="Step05 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignR"><img src="/images/2012/sub/page4/guide/test06.gif" class="stepst" alt="Step06 참고사진"></td>
				</tr>
				<tr class="division">
					<td>(Step 04)</td>
					<td></td>
					<td>(Step 05)</td>
					<td></td>
					<td>(Step 06)</td>
				</tr>
			</table>
			<p><span>Step 04.</span> 다운로드가 완료되면, 위 이미지와 같이 "<b>사용자 계정 컨트롤</b>" 윈도우 나옵니다. 이 때 "<b>예</b>"를 선택하여, 프로그램 설치를 진행합니다.</p>
			<p><span>Step 05.</span> 설치를 정상적으로 진행하기 위해, "<b>Next</b>"버튼을 설치게이지 화면이 나오기 전까지 클릭합니다.</p>
			<p><span>Step 06.</span> 설치 화면에서 PC환경에 따라 <b>약 1~3분 정도</b>가 소요됩니다.</p>
			<br><br>
			<table id="skip03" class="stepimage" cellpadding="0" cellspacing="0">
				<colgroup><col width="302"><col width="37"><col width="302"><col width="*"><col width="302"></colgroup>
				<tr>
					<td class="alignL"><img src="/images/2012/sub/page4/guide/test07.gif" class="stepst" alt="Step07 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignC"><img src="/images/2012/sub/page4/guide/test08.gif" class="stepst" alt="Step08 참고사진"></td>
					<td class="alignC"><img src="/images/2012/common/icon_arrow.gif" alt="arrow"></td>
					<td class="alignR"><img src="/images/2012/sub/page4/guide/test09.gif" class="stepst" alt="Step09 참고사진"></td>
				</tr>
				<tr class="division">
					<td>(Step 07)</td>
					<td></td>
					<td>(Step 08)</td>
					<td></td>
					<td>(Step 09)</td>
				</tr>
			</table>
			<p><span>Step 07.</span> 설치가 완료되면, "<b>Close</b>"버튼을 클릭하여 윈도우를 닫아줍니다.</p>
			<p><span>Step 08.</span> 프로그램 설치가 완료 후 회원님이 현재 이용하는 회선과, 현재 PC를 이용하는 위치와 주거형태를 옳바르게 선택한 후 "<b>다음</b>"버튼을 클릭합니다.</p>
			<p><span>Step 09.</span> "<b>시작</b>"버튼을 클릭하여 측정을 시작합니다. (간혹 "현재 동시 이용자가 많습니다" 라는 메세지가 나오는 경우가 있습니다. 이러한 경우에는 몇 분 뒤 다시 진행합니다.)</p>
			<p><span>Step 10.</span> 측정에는 <b>약 1~3분 정도 소요</b>되며, 인터넷 속도 측정이 완료됩니다. <b>다운로드 속도가 1.00 Mbps 미만일 경우</b> 온라인 교육의 진행이 어려울 수 있습니다.</p>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
