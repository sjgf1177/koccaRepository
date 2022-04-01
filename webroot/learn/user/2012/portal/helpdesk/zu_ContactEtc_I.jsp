<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.cp.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","7");
	box.put("submenu","7");
    
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>Contact Us</u></td>
			</tr>
		</table>
		<div class="concept g07">
			<div><img src="/images/2012/sub/page7/title/title_contactus.png" alt="Contact Us(문의/신청하기), 단체수강신청(ASP) 및 고객제안, 기업문의 등을 접수합니다"></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus" tabindex="170" title="단체수강(ASP)를 신청할 수 있습니다"><img src="/images/2012/sub/page7/tabmenu/contactus01.png" alt="단체 수강 신청"></a></li>
				<li><img src="/images/2012/sub/page7/tabmenu/contactus02on.png" alt="문의 및 제안"></li>
			</ul>
		</div>
		<div class="grouptip">
			<h5>한국콘텐츠아카데미는 고객 여러분의 모든 의견을 겸허하게 듣도록 하겠습니다.</h5>
		</div>
		<form id="corpinsert" method="post" enctype="multipart/form-data" action="/servlet/controller.homepage.HomePageContactServlet">
   		<input type = "hidden" name="p_process"    value = "insertContactEtc">
		<div class="namefont"><h3>문의 및 제안 작성</h3><p><img src="/images/2012/common/icon_check.gif" alt="필수입력">는 필수 입력 항목입니다.</p></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="단체수강신청의 기본정보를 입력합니다">
				<colgroup><col width="160"><col width="250"><col width="160"><col width="*"></colgroup>
				<tr>
					<th>성명 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3"><input type="text" name="username" tabindex="171" title="이름을 입력해주세요" style="width:160px;"></td>
				</tr>
				<tr>
					<th>연락처 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td>
						<dl class="colbath">
							<dd><input type="text" name="tel1" tabindex="172" title="지역번호 또는 이동번호를 입력해주세요" style="width:20px; text-align:center;" maxlength="3"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="tel2" tabindex="173" title="가운데 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="tel3" tabindex="174" title="끝 4자리 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>이메일 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td>
						<dl class="colbath">
							<dd><input type="text" name="email1" tabindex="175" style="width:100px;" title="이메일 아이디를 입력해주세요"></dd>
							<dd class="hyphen">@</dd>
							<dd><input type="text" name="email2" id="inmeildomain" tabindex="176" style="width:130px;" title="이메일 도메인을 입력해주세요"></dd>
							<dd>
								<select id="selectmailtype" class="select" tabindex="177" title="직접입력 또는 자주사용되는 메일 번호를 선택해주세요">
									<option>직접입력</option>
									<option>chol.com</option>
									<option>daum.net</option>
									<option>dreamwiz.com</option>
									<option>empal.com</option>
									<option>freechal.com</option>
									<option>gmail.com</option>
									<option>hanafos.com</option>
									<option>hanmail.net</option>
									<option>hanmir.com</option>
									<option>hitel.net</option>
									<option>hotmail.com</option>
									<option>korea.com</option>
									<option>lycos.co.kr</option>
									<option>naver.com</option>
									<option>nate.com</option>
									<option>netian.com</option>
									<option>paran.com</option>
									<option>yahoo.co.kr</option>
								</select>
							</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>분류 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<select class="select" name="category" tabindex="178" title="분류를 선택해주세요">
							<option value="1">일반문의</option>
							<option value="2">사업문의</option>
							<option value="3">고객제안</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>주제 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3"><input type="text" name="title" tabindex="179" title="주제를 입력해주세요" style="width:700px;"></td>
				</tr>
				<tr>
					<th>내용 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3"><textarea name="content" id="incontact" title="내용을 입력해주세요" tabindex="180" style="width:700px; height:300px;"></textarea></td>
				</tr>
				<tr>
					<th>관련링크</th>
					<td colspan="3"><input type="text" name="link" class="unqueryform" tabindex="181" title="관련링크가 있을 경우 링크주소를 입력해주세요" style="width:700px;" value="http://"></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td colspan="3"><input type="FILE" name ="p_file" class="unqueryform" tabindex="182" title="첨부할 파일이 있을 경우 파일을 선택하여 등록해주세요" style="width:350px;"></td>
				</tr>
			</table>
		</div>
		<div class="contactbtm"><input type="image" tabindex="183" src="/images/2012/sub/page7/btn_register.gif" alt="위 내용으로 신청합니다"></div>
		</form>
	</div>

<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
