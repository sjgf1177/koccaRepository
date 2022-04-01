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


<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//입력된 내용 저장
function insert_check() {    
    document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
    document.form1.p_process.value = "insertContactUs";
    document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

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
				<li><img src="/images/2012/sub/page7/tabmenu/contactus01on.png" alt="단체 수강 신청"></li>
				<li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactetc" tabindex="170" title="고객제안,기업문의,일반문의를 할 수 있습니다"><img src="/images/2012/sub/page7/tabmenu/contactus02.png" alt="문의 및 제안"></a></li>
			</ul>
		</div>
		<div class="grouptip">
			<h5>
			한국콘텐츠진흥원 사이버아카데미에서는 기관,기업,학교 대상으로 단체수강서비스를 제공하고 있습니다.<br>
			게임, 문화, 방송 분야의 135개 과정이 무료로 제공됩니다.<br>
			기업에서는 직무교육의 일환으로 활용되고, 학교에서는 학점 연계 및 부교재의 교육 형태로 이루어지고 있습니다.<br>
			단체수강서비스는 자체 사이버연수원을 개설해드리며 체계적인 관리를 위한 교육담당자 관리 페이지도 제공됩니다.<br>
			사이버콘텐츠아카데미 소개 자료를 통해 더 자세한 내용 확인 가능하며, 궁금하신 점은 아래 번호로 언제든지 연락주시기 바랍니다.
			</h5>
			<a href="/upload/contactus/kocca_cyber_academy_2013_guide.pdf" target="_blank" tabindex="171" title="(새창) 사이버콘텐츠아카데미 소개(과정목록 포함) 파일을 다운로드 합니다"><img src="/images/2012/sub/page7/btn_groupguide.gif" alt="단체학습안내서 다운로드"></a>
			<h5>
			사이버콘텐츠아카데미 교육담당 : <b>02-2161-0077 / <a href="mailto::edu_kocca@naver.com" alt="edu_kocca@naver.com">edu_kocca@naver.com</a></b>
			</h5>
		</div>
		
		<form id="groupinsert" name="form1" method="post" enctype="multipart/form-data" action="/servlet/controller.homepage.HomePageContactServlet">
   		<input type = "hidden" name="p_process"    value = "insertContactUs">
		<div class="namefont"><h3>기본정보입력</h3><p><img src="/images/2012/common/icon_check.gif" alt="필수입력">는 필수 입력 항목입니다.</p></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="단체수강신청의 기본정보를 입력합니다">
				<colgroup><col width="160"><col width="120"><col width="290"><col width="120"><col width="*"></colgroup>
				<tr>
					<th>단체명 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="4"><input type="text" name="company" tabindex="173" title="단체명을 입력해주세요" style="width:220px;"></td>
				</tr>
				<tr>
					<th>주소 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="4"><input type="text" name="addr" tabindex="174" title="단체 소재지를 입력해주세요" style="width:395px;"></td>
				</tr>
				<tr>
					<th rowspan="2">담당자</th>
					<td class="addtitle">부서/직급</td>
					<td><input type="text" name="com_dept" class="unqueryform" tabindex="175" title="부서와 직급을 입력해주세요" style="width:220px;"></td>
					<td class="addtitle">유선전화 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<dl class="colbath">
							<dd>
								<select class="select" name="tel1" tabindex="176" title="지역번호를 선택해주세요">
									<option value="">지역번호 선택</option>
									<option value="070">070</option>
									<option value="02">02</option>
									<option value="031">031</option>
									<option value="032">032</option>
									<option value="033">033</option>
									<option value="041">041</option>
									<option value="042">042</option>
									<option value="043">043</option>
									<option value="051">051</option>
									<option value="052">052</option>
									<option value="053">053</option>
									<option value="054">054</option>
									<option value="055">055</option>
									<option value="061">061</option>
									<option value="062">062</option>
									<option value="063">063</option>
									<option value="064">064</option>
								</select>
							</dd>
							<dd class="hyphen">-</ddname="tel1" >
							<dd><input type="text" name="tel2" tabindex="177" title="유선전화 가운데 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="tel3" tabindex="178" title="유선전화 끝 4자리 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<td class="addtitle">성명 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td><input type="text" name="com_name" tabindex="179" title="담당자 실명을 입력해주세요" style="width:220px;"></td>
					<td class="addtitle">모바일번호 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<dl class="colbath">
							<dd>
								<select class="select" name="mobile1" tabindex="180" title="이동번호를 선택해주세요">
									<option value="">이동번호 선택</option>
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
									<option value="019">019</option>
								</select>
							</dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="mobile2" tabindex="181" title="모바일 가운데 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="mobile3" tabindex="182" title="모바일 끝 4자리 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4"></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>이메일 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="2">
						<dl class="colbath">
							<dd><input type="text" name="email1" tabindex="183" style="width:100px;" title="이메일 아이디를 입력해주세요"></dd>
							<dd class="hyphen">@</dd>
							<dd><input type="text" name="email2" id="inmeildomain" tabindex="184" style="width:130px;" title="이메일 도메인을 입력해주세요"></dd>
							<dd>
								<select id="selectmailtype" class="select" tabindex="185" title="직접입력 또는 자주사용되는 메일 번호를 선택해주세요">
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
					<td class="addtitle">홈페이지</td>
					<td>
						<input type="text" name="homepage" class="unqueryform" tabindex="186" title="홈페이지가 있다면 홈페이지 주소를 입력해주세요" value="http://" style="width:260px;">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="namefont"><h3>학습정보입력</h3><p><img src="/images/2012/common/icon_check.gif" alt="필수입력">는 필수 입력 항목입니다.</p></div>
		<div class="contactform contactformtopcolor2">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="단체수강신청의 학습정보를 입력합니다">
				<colgroup><col width="160"><col width="270"><col width="160"><col width="*"></colgroup>
				<tr>
					<th>신청분야 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<input type="radio" name="category" value="1" class="radio" id="boardcastiing" tabindex="191" title="방송분야를 신청합니다"><label for="boardcastiing" class="label" title="방송분야를 신청합니다">방송</label>
						<input type="radio" name="category" value="2" class="radio" id="game" tabindex="192" title="게임분야를 신청합니다"><label for="game" class="label" title="게임분야를 신청합니다">게임</label>
						<input type="radio" name="category" value="3" class="radio" id="culture" tabindex="193" title="문화분야를 신청합니다"><label for="culture" class="label" title="문화분야를 신청합니다">문화</label>
					</td>
				</tr>
				<tr>
					<th>수강신청기간 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td>
						<dl class="colbath">
							<dd><input type="text" name="edustart" tabindex="194" id="startdate" title="수강신청시작일을 선택해주세요" style="width:80px; text-align:center;"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="eduend" tabindex="195" id="enddate" title="수강신청종료일을 선택해주세요" style="width:80px; text-align:center;"></dd>
						</dl>
					</td>
					<td class="addtitle">학습시작일 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td><input type="text" name="edustartday" tabindex="196" id="learningstart" title="학습시작일을 선택해주세요" style="width:80px; text-align:center;"></td>
				</tr>
				<tr>
					<th>학습기간 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td>
						<select class="select" name="eduperiod" tabindex="197" title="수강기간을 선택해주세요">
							<option value="1">1개월</option>
							<option value="2">2개월</option>
							<option value="3">3개월</option>
						</select>
					</td>
					<td class="addtitle">예상입과인원 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<dl class="colbath">
							<dd><input type="text" name="edustudent" tabindex="198" title="예상입과인원을 입력해주세요" maxlength="5" style="width:40px; text-align:center;"></dd>
							<dd class="hyphen">명</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>과정선택제한 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<dl class="colbath">
							<dd class="hyphen">1인 최대</dd>
							<dd>
								<select class="select" name="edusubjcnt" tabindex="199" title="수강기간을 선택해주세요">
									<option value="1">1개 과정</option>
									<option value="2">2개 과정</option>
									<option value="3">3개 과정</option>
								</select>
							</dd>
							<dd class="hyphen">을 선택할 수 있도록 설정합니다</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>LOGO업로드 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<input type="FILE" name="p_file" tabindex="200" title="ASP에서 사용될 귀하 단체의 로고를 업로드해주세요" style="width:350px;">
						<p>고객님께서 소속하신 단체(기업,학교,기관)의 로고이미지(gif,jpg,png 등)을 등록해주세요.</p>
					</td>
				</tr>
				
				<tr>
					<th>신청과정 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3" id="requestbox">
						<input type="checkbox" id="allcourse" name="apply_gubun" value="1" tabindex="201" class="checkbox" title="전체과정을 신청합니다"><label for="allcourse" class="label" title="전체과정을 신청합니다">전체과정</label>
						<input type="checkbox" id="boardcastcourse" name="apply_gubun" value="2" tabindex="202" class="checkbox" title="전체과정을 신청합니다"><label for="boardcastcourse" class="label" title="전체과정을 신청합니다">방송분야 과정</label>
						<input type="checkbox" id="gamecourse" name="apply_gubun" value="3" tabindex="203" class="checkbox" title="전체과정을 신청합니다"><label for="gamecourse" class="label" title="전체과정을 신청합니다">게임분야 과정</label>
						<input type="checkbox" id="culturecourse" name="apply_gubun" value="4" tabindex="204" class="checkbox" title="전체과정을 신청합니다"><label for="culturecourse" class="label" title="전체과정을 신청합니다">문화분야 과정</label>
						<br>
						<textarea name="apply_subj" title="개별 추가 필요 시 과정명을 입력해주세요" tabindex="205" style="width:700px;"></textarea>
					</td>
				</tr>
				
				<tr>
					<th>기타문의</th>
					<td colspan="3"><textarea id="addcourse" name="etc" title="위 입력내용 외 문의사항이 있다면 입력해주세요" tabindex="206" style="width:700px;"></textarea></td>
				</tr>
				<tr>
					<th>요청도메인 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<dl class="colbath">
							<dd class="hyphen">http://</dd>
							<dd><input type="text" name="domain" tabindex="207" title="수강 홈페이지로 이용할 도메인 이름을 입력해주세요" style="width:120px;"></dd>
							<dd class="hyphen">.edukocca.or.kr</dd>
						</dl>
					</td>
				</tr>
			</table>
		</div>
		<div class="contactbtm">
		<!-- <a href='javascript:insert_check()'><img src="/images/2012/sub/page7/btn_request.gif" alt="위 내용으로 신청합니다"></a> -->
		<input type="image" tabindex="208" src="/images/2012/sub/page7/btn_request.gif" alt="위 내용으로 신청합니다"> 
		</div>
		</form>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
