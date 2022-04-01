<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    box.put("topmenu","1");
    box.put("submenu","7");
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
    }
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_email.gif" alt="이메일부단수집거부"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>이메일무단수집거부</u></td>
			</tr>
		</table>
		<div class="emailbox">
			<ul class="emailmsg">
				<li class="danger">
					본 홈페이지에 게시된 이메일 주소가 전자우편 수집 프로그램이나 그 밖의 기술적
					장치를 이용하여 무단으로 수집되는 것을 거부하며, 이를 위반시 정보통신망법에
					의해 형사처벌됨을 유념하시기 바랍니다.
				</li>
				<li class="speaking">한국콘텐츠진흥원 아카데미는 이메일 주소 수집을 거부합니다.</li>
				<li class="laws">
					<span>정보통신망법 제 50조의 2 (전자우편주소의 무단 수집행위 등 금지)</span><br><br>
					<p>
						① 누구든지 전자우편주소의 수집을 거부하는 의사가 명시된 인터넷 홈페이지에서 자동으로 전자우편주소를<br>
					    &nbsp; &nbsp; 수집하는 프로그램 그 밖의 기술적 장치를 이용하여 전자우편주소를 수집하여서는 안된다.
					</p>
					<p>
						② 누구든지 제 1항의 규정을 위반하여 수집된 전자우편주소를 판매 유통하여서는 아니된다.
					</p>
					<p>
						③ 누구든지 제 1항 및 제 2항의 규정에 의하여 수집·판매 및 유통이금지된 전자우편주소임을 알고 이를 정보<br>
					   &nbsp; &nbsp; 전송에 이용하여서는 아니 된다.
					</p>
					<br>
					<p>
						※ 만일, 위와 같은 기술적 장치를 사용한 이메일주소 무단수집 피해를 당하신 경우 <b>불법스팸대응센터</b><br>
					  &nbsp; &nbsp; <b>전용전화 (1336)나 홈페이지(www.spamcop.or.kr)</b>의 신고창을 통하여 신고하여 주시기 바랍니다.
					</p>
				</li>
			</ul>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>