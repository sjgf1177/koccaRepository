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
	box.put("topmenu","8");
	box.put("submenu","1");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>


<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">

$(document).ready(function(){
		
});

//입력된 내용 저장
function insert_check() {
	var f = document.form1;
	var subjgubun = "";
	if(check_value()){
		f.reg7.value = f.reg.value + "-" + $("input[name='sex']:checked").val();
		f.comptel.value = f.comptel1.value +"-"+ f.comptel2.value +"-"+ f.comptel3.value;
		f.handphone.value = f.mobile1.value +"-"+ f.mobile2.value +"-"+ f.mobile3.value;
		f.email.value = f.email1.value +"@"+ f.email2.value;

		//지원분야 체크박스로 중복 설정할 경우 값을 담는다
		//for(var i = 0; i< $("[name='subjgubun']:checked").length; i++){
		//	subjgubun += "," + $("[name='subjgubun']:checked").eq(i).val();
		//}
		//if(subjgubun != ""){
		//	f.p_subjgubun.value = subjgubun.substring(1); 
		//}
		f.p_subjgubun.value = $("input[name='subjgubun']:checked").val();

	    document.form1.action = "/servlet/controller.off.OffLecturerCollusionServlet";
	    document.form1.p_process.value = "insert";
	    document.form1.submit();
	}else{
		return;
	}
}

function check_value(){
	var f = document.form1;
	if(f.name.value == ""){
		alert("성명을 입력해 주세요");
		f.name.focus();
		return false;
	}
	if(f.p_file1.value == ""){
		alert("프로필 사진을 등록해 주세요");
		f.p_file1.focus();
		return false;
	}
	var c_file1 = f.p_file1.value;
	c_file1 = c_file1.substring(c_file1.lastIndexOf(".")+1).toLowerCase();
	if(c_file1 != "gif" && c_file1 != "jpg" && c_file1 != "png"){
		alert("프로필 사진은 gif, jpg, png 파일만 등록 할 수 있습니다.");
		f.p_file1.focus();
		return false;
	}
	
	if(f.reg.value == ""){
		alert("생년월일을 입력해 주세요");
		f.reg.focus();
		return false;
	}
	if(f.compnm.value == ""){
		alert("직장명을 입력해 주세요");
		f.compnm.focus();
		return false;
	}
	if(f.compaddr.value == ""){
		alert("주소를 입력해 주세요");
		f.compaddr.focus();
		return false;
	}
	if(f.comptel1.value == "" || f.comptel2.value == "" || f.comptel3.value == ""){
		alert("전화번호를 입력해 주세요");
		f.comtel1.focus();
		return false;
	}
	if(f.mobile1.value == "" || f.mobile2.value == "" || f.mobile3.value == ""){
		alert("핸드폰번호를 입력해 주세요");
		f.mobile3.focus();
		return false;
	}
	if(f.email1.value == "" || f.email2.value == ""){
		alert("이메일을 입력해 주세요");
		f.email1.focus();
		return false;
	}
	
	var lectgubun = $("input[name='lectgubun']:checked").val();
	if(lectgubun == "C"){
		if(f.p_file2.value == ""){
			alert("강의계획서를 등록해 주세요");
			f.p_file2.focus();
			return false;
		}
		var c_file2 = f.p_file2.value;
		c_file2 = c_file2.substring(c_file2.lastIndexOf(".")+1).toLowerCase();
		if(c_file2 != "hwp"){
			alert("강의계획서는 한글 파일만 등록 할 수 있습니다.");
			f.p_file1.focus();
			return false;
		}
	}

	if(f.p_file3.value != ""){
		var c_file3 = f.p_file3.value;
		c_file3 = c_file3.substring(c_file3.lastIndexOf(".")+1).toLowerCase();
		if(c_file3 != "zip" && c_file3 != "alz" && c_file3 != "rar" && c_file3 != "ace"){
			alert("증명자료는 zip, alz, rar, ace 파일만 등록 할 수 있습니다.");
			f.p_file3.focus();
			return false;
		}
	}
	
	return true;
}

function syllabus_chek(){
	var lectgubun = $("input[name='lectgubun']:checked").val();
	if(lectgubun == "C"){
		$("#syllabus").attr("style","");
	}
	else if(lectgubun == "S"){
		$("#syllabus").attr("style","display:none;");
	}
}

function numberCheck(loc) {
	if(/[^0123456789]/g.test(loc.value)) {
	alert("숫자만 입력해 주세요!");
	loc.value = "";
	loc.focus();
	}
}

//체크박스 중복 최대 개수 제한
function checkboxLimit(frm,obj,num){
	var str = obj.name;
	var len = frm.length;
	var chk = 0;
	for(i=0; i<len; i++){
		if(frm[i].name == str && frm[i].checked == true){
			chk++;
		}
		if(chk > num){
			alert(num + "개 까지 선택할 수 있습니다.");
			obj.checked = false;
			break;
		}
	}
}

</script>
<!-- 스크립트영역종료 -->
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 &gt; <u>강사공모제</u></td>
			</tr>
		</table>
		<div>
			<div><img src="/images/2012/sub/lectur/recruit.png" alt="2014년 한국콘텐츠아카데미 강사를 모집합니다."></div>
		</div>
		<div class="grouptip">
			<h5>
			한국콘텐츠진흥원 한국콘텐츠아카데미 교육운영을 위한 강사를 모집합니다.
			</h5>
		</div>
		
		<form id="groupinsert" name="form1" method="post" enctype="multipart/form-data" action="/servlet/controller.off.OffLecturerCollusionServlet">
   		<input type = "hidden" name="p_process"    value = "insert">
   		<input type = "hidden" name="reg7">
   		<input type = "hidden" name="comptel">
   		<input type = "hidden" name="fax">
   		<input type = "hidden" name="hometel">
   		<input type = "hidden" name="handphone">
   		<input type = "hidden" name="email">
   		<input type = "hidden" name="p_subjgubun">
   		
		<div class="namefont"><h3>기본정보입력</h3><p><img src="/images/2012/common/icon_check.gif" alt="필수입력">는 필수 입력 항목입니다.</p></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="강사공모제 기본정보를 입력합니다">
				<colgroup><col width="130"><col width="*"><col width="130"><col width="*"></colgroup>
				<tr>
					<th>모집분류 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<input type="radio" name="lectgubun" value="C" class="radio" onclick="syllabus_chek()" id="lectgubun_c" tabindex="171" title="전문강사를 선택합니다" checked="checked"><label for="lectgubun_c" class="label" title="전문강사를 선택합니다">전문강사</label>
						<input type="radio" name="lectgubun" value="S" class="radio" onclick="syllabus_chek()" id="lectgubun_s" tabindex="172" title="실습강사를 선택합니다"><label for="lectgubun_s" class="label" title="실습강사를 선택합니다">실습강사</label>
					</td>
				</tr>
				<tr>
					<th>성명 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td><input type="text" name="name" tabindex="173" title="성명을 입력해주세요" style="width:220px;"></td>
					<td class="addtitle2">사진 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<input type="FILE" name="p_file1" tabindex="174" title="강사이력카드에 사용될 프로필 사진을 업로드 해주세요" style="width:350px;">
					</td>
				</tr>
				<tr>
					<th>생년월일 <img src="/images/2012/common/icon_check.gif" alt="필수입력"><br/>(예 : 990701)</th>
					<td><input type="text" name="reg" tabindex="175" title="생년월일을 입력해주세요" style="width:220px;" onkeyup="numberCheck(this);" maxlength="6"></td>
					<td class="addtitle2">성별 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<input type="radio" name="sex" value="1" class="radio" id="sex_male" tabindex="176" title="남성을 선택합니다" checked="checked"><label for="sex_male" class="label" title="남성을 선택합니다">남성</label>
						<input type="radio" name="sex" value="2" class="radio" id="sex_female" tabindex="177" title="여성을 선택합니다"><label for="sex_female" class="label" title="여성을 선택합니다">여성</label>
					</td>
				</tr>
				<tr>
					<th>직장명 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3"><input type="text" name="compnm" tabindex="178" title="직장명을 입력해주세요" style="width:220px;"></td>
				</tr>
				<tr>
					<th>주소 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3"><input type="text" name="compaddr" tabindex="179" title="주소를 입력해주세요" style="width:635px;"></td>
				</tr>
				<tr>
					<th>전화 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td>
						<dl class="colbath">
							<dd>
								<select class="select" name="comptel1" tabindex="180" title="지역번호를 선택해주세요">
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
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="comptel2" tabindex="181" title="전화번호 가운데 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="comptel3" tabindex="182" title="전화번호 끝 4자리 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="addtitle2">핸드폰 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></td>
					<td>
						<dl class="colbath">
							<dd>
								<select class="select" name="mobile1" tabindex="183" title="핸드폰번호를 선택해주세요">
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
							<dd><input type="text" name="mobile2" tabindex="184" title="핸드폰 가운데 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">-</dd>
							<dd><input type="text" name="mobile3" tabindex="185" title="핸드폰 끝 4자리 번호를 입력해주세요" style="width:30px; text-align:center;" maxlength="4" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
				</tr>
				<tr>
					<th>이메일 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<dl class="colbath">
							<dd><input type="text" name="email1" tabindex="186" style="width:100px;" title="이메일 아이디를 입력해주세요"></dd>
							<dd class="hyphen">@</dd>
							<dd><input type="text" name="email2" id="inmeildomain" tabindex="187" style="width:130px;" title="이메일 도메인을 입력해주세요"></dd>
							<dd>
								<select id="selectmailtype" class="select" tabindex="188" title="직접입력 또는 자주사용되는 메일 번호를 선택해주세요">
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
				<tr id="syllabus">
					<th>강의계획서 <img src="/images/2012/common/icon_check.gif" alt="필수입력"></th>
					<td colspan="3">
						<input type="FILE" name="p_file2" tabindex="189" title="강의계획서를 선택해 주세요" style="width:350px;">
						<a href="/servlet/controller.library.DownloadServlet?p_savefile=/upload/KOCCA_Syllabus.hwp&p_realfile=강의계획서.hwp" target="_blank" tabindex="189" title="(새창) 강의계획서 파일을 다운로드 합니다"> <img src="/images/2012/sub/lectur/btn_teachprogram.gif" alt="강의계획서 다운로드"> </a>
					</td>
				</tr>
				<tr>
					<th>지원분야</th>
					<td colspan="3">
						<input name="subjgubun" type="radio" tabindex="192" value="게임디자인(기획)" checked="checked" /> 게임디자인(기획)
						&nbsp;<input name="subjgubun" type="radio" tabindex="193" value="게임프로그래밍" /> 게임프로그래밍
						&nbsp;<input name="subjgubun" type="radio" tabindex="194" value="게임그래픽" /> 게임그래픽
						&nbsp;<input name="subjgubun" type="radio" tabindex="195" value="VFX" /> VFX
						&nbsp;<input name="subjgubun" type="radio" tabindex="196" value="VJ과정" /> VJ과정
						<!--
						[게임제작]&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="게임디자인(기획)" tabindex="192" onclick="checkboxLimit(this.form,this,3)" /> 게임디자인(기획)&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="게임프로그래밍" tabindex="193" onclick="checkboxLimit(this.form,this,3)" /> 게임프로그래밍&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="게임그래픽" tabindex="194" onclick="checkboxLimit(this.form,this,3)" /> 게임그래픽&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="모바일게임제작" tabindex="195" onclick="checkboxLimit(this.form,this,3)" /> 모바일게임제작&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="게임퍼블리싱" tabindex="196" onclick="checkboxLimit(this.form,this,3)" /> 게임퍼블리싱
						<br/>
						[영상제작]&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="VFX" tabindex="197" onclick="checkboxLimit(this.form,this,3)" /> VFX&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="모션그래픽" tabindex="198" onclick="checkboxLimit(this.form,this,3)" /> 모션그래픽&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="VJ과정" tabindex="199" onclick="checkboxLimit(this.form,this,3)" /> VJ과정
						<br/>
						[3D]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="Production" tabindex="200" onclick="checkboxLimit(this.form,this,3)" /> Production&nbsp;&nbsp;
						<input name="subjgubun" type="checkbox" value="Post-Production" tabindex="201" onclick="checkboxLimit(this.form,this,3)" /> Post-Production
						-->
					</td>
				</tr>
				<tr>
					<th>지원분야<br/>상세내용<br/><br/><a href="/images/2012/sub/lectur/reference.jpg" target="_blank" tabindex="202" title="(새창) 지원분야 레퍼런스 보기"> <img src="/images/2012/sub/lectur/btn_reference.gif" alt="지원분야 레퍼런스 보기"> </a></th>
					<td colspan="3">
						<textarea id="etc" name="etc" title="지원분야에 대한 상세 과정내용을 입력해주세요" tabindex="203" style="width:700px;"></textarea>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- --------------------------학력------------------------------------- -->
		<div class="namefont"><h3>학력</h3></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="강사공모제 학력정보를 입력합니다">
				<colgroup><col width="80"><col width="200"><col width="200"><col width="*"></colgroup>
				<tr>
					<th>No</th>
					<th>학 교<br/>(기관)</th>
					<th>기 간<br/>(20120101~20120301)</th>
					<th>내 용</th>
				</tr>
				<tr>
					<td class="contents">1</td>
					<td class="contents_l">
						<input type="text" name="h_position1" class="unqueryform" tabindex="205" style="width:90%;"  title="학력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="h_edustart1" tabindex="206" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="h_eduend1" tabindex="207" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="h_contents1" class="unqueryform" tabindex="208" style="width:98%;"  title="학력 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">2</td>
					<td class="contents_l">
						<input type="text" name="h_position2" class="unqueryform" tabindex="209" style="width:90%;"  title="학력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="h_edustart2" tabindex="210" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="h_eduend2" tabindex="211" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="h_contents2" class="unqueryform" tabindex="212" style="width:98%;"  title="학력 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">3</td>
					<td class="contents_l">
						<input type="text" name="h_position3" class="unqueryform" tabindex="213" style="width:90%;"  title="학력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="h_edustart3" tabindex="214" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="h_eduend3" tabindex="215" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="h_contents3" class="unqueryform" tabindex="216" style="width:98%;"  title="학력 내용을 입력해주세요">
					</td>
				</tr>
			</table>
		</div>
		
		<!-- --------------------------주요경력------------------------------------- -->
		<div class="namefont"><h3>주요경력</h3></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="강사공모제 학력정보를 입력합니다">
				<colgroup><col width="80"><col width="200"><col width="200"><col width="*"></colgroup>
				<tr>
					<th>No</th>
					<th>근무처<br/>(기관명)</th>
					<th>기 간<br/>(20120101~20120301)</th>
					<th>직 무</th>
				</tr>
				<tr>
					<td class="contents">1</td>
					<td class="contents_l">
						<input type="text" name="c_position1" class="unqueryform" tabindex="221" style="width:90%;"  title="주요경력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="c_edustart1" tabindex="222" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="c_eduend1" tabindex="223" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="c_contents1" class="unqueryform" tabindex="224" style="width:98%;"  title="주요경력 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">2</td>
					<td class="contents_l">
						<input type="text" name="c_position2" class="unqueryform" tabindex="226" style="width:90%;"  title="주요경력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="c_edustart2" tabindex="227" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="c_eduend2" tabindex="228" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="c_contents2" class="unqueryform" tabindex="229" style="width:98%;"  title="주요경력 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">3</td>
					<td class="contents_l">
						<input type="text" name="c_position3" class="unqueryform" tabindex="231" style="width:90%;"  title="주요경력 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="c_edustart3" tabindex="232" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="c_eduend3" tabindex="233" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="c_contents3" class="unqueryform" tabindex="234" style="width:98%;"  title="주요경력 내용을 입력해주세요">
					</td>
				</tr>
			</table>
		</div>
		
		<!-- --------------------------교수기록------------------------------------- -->
		<div class="namefont"><h3>교수기록</h3></div>
		<div class="contactform contactformtopcolor1">
			<table class="insertform" cellpadding="0" cellspacing="0" summary="강사공모제 교수기록을 입력합니다">
				<colgroup><col width="80"><col width="200"><col width="200"><col width="*"></colgroup>
				<tr>
					<th>No</th>
					<th>기관명<br/>(학교)</th>
					<th>기 간<br/>(20120101~20120301)</th>
					<th>교과내용</th>
				</tr>
				<tr>
					<td class="contents">1</td>
					<td class="contents_l">
						<input type="text" name="p_position1" class="unqueryform" tabindex="240" style="width:90%;"  title="교수기록 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="p_edustart1" tabindex="241" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="p_eduend1" tabindex="242" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="p_contents1" class="unqueryform" tabindex="243" style="width:98%;"  title="교수기록 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">2</td>
					<td class="contents_l">
						<input type="text" name="p_position2" class="unqueryform" tabindex="245" style="width:90%;"  title="교수기록 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="p_edustart2" tabindex="246" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="p_eduend2" tabindex="247" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="p_contents2" class="unqueryform" tabindex="248" style="width:98%;"  title="교수기록 내용을 입력해주세요">
					</td>
				</tr>
				<tr>
					<td class="contents">3</td>
					<td class="contents_l">
						<input type="text" name="p_position3" class="unqueryform" tabindex="250" style="width:90%;"  title="교수기록 기관명을 입력해주세요">
					</td>
					<td class="contents_l">
						<dl class="colbath">
							<dd><input type="text" name="p_edustart3" tabindex="251" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
							<dd class="hyphen">~</dd>
							<dd><input type="text" name="p_eduend3" tabindex="252" title="기간을 입력해주세요" style="width:80px; text-align:center;" maxlength="8" onkeyup="numberCheck(this);"></dd>
						</dl>
					</td>
					<td class="contents_l">
						<input type="text" name="p_contents3" class="unqueryform" tabindex="253" style="width:98%;"  title="교수기록 내용을 입력해주세요">
					</td>
				</tr>
			</table>
		</div>
		
		<div class="namefont"><h3>주요경력 및 교수기록 증명자료</h3><h4>증명자료는 압축하여 등록해 주세요. (zip, alz, rar, ace 만 등록가능)</h4></div>
		<div class="contactform contactformtopcolor1">
			<input type="FILE" name="p_file3" tabindex="254" title="강의계획서를 선택해 주세요" style="width:99%;margin-top: 5px;">
		</div>
		<!-- --------------------------교수기록------------------------------------- -->
		<div class="namefont"><h3>주요작품 및 저서</h3></div>
		<div class="contactform contactformtopcolor1">
			<textarea id="worknbook" name="worknbook" title="주요작품 및 저서를 입력해 주세요" tabindex="260" style="width:99%;margin-top: 5px;" rows="10"></textarea>
		</div>
		<div class="contactbtm">
		<a href='javascript:insert_check()'><img src="/images/2012/sub/page7/btn_request.gif" alt="위 내용으로 신청합니다"></a>
		</div>
		</form>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
