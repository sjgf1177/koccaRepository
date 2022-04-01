<%
//**********************************************************
//  1. 제      목: 패스워드 찾기
//  2. 프로그램명 : zu_FindIdPwd.jsp
//  3. 개      요: 패스워드 찾기
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("submenu","3");

    String s_userid  = box.getSession("userid");

    //===================================================================
    // 본인인증(휴대폰인증) 관련 항목
    //===================================================================
    String sUserid = box.getString("sUserid");
    String sJuminid1 = box.getString("sJuminid1");
    String sJuminid2 = box.getString("sJuminid2");
    String sName = box.getString("sName");
    String sEncData = box.getString("sEncData");
    String sMessage = box.getString("sMessage");
    String sIsOk = box.getString("sIsOk");

    box.setSession("sUserid", sUserid);
    box.setSession("sJuminid1", sJuminid1);
    box.setSession("sJuminid2", sJuminid2);
    box.setSession("sName", sName);
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

// ID 찾기
function searchId() {
	
	if(document.form1.p_agree1.checked == false ){
		alert('주민등록번호 처리에 동의를 해주세요');
		return;	
	}

    if(document.form1.p_name.value == ""){
        alert("이름을 입력하세요!");
        form1.p_name.focus();
        return;
    }

    if (document.form1.p_resno1.value=="") {
        alert("주민등록번호를 정확히 입력하세요.");
        form1.p_resno1.focus();
        return;
    }

    if (document.form1.p_resno2.value=="") {
        alert("주민등록번호를 정확히 입력하세요.");
        form1.p_resno2.focus();
        return;
    }

    document.form1.p_process.value = "FindIdResult";
    document.form1.action = "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.submit();

}

// 패스워드 찾기
 function searchPwd() {

    if(document.form1.p_userid_2.value == ""){
        alert("아이디를 입력하세요!");
        form1.p_userid_2.focus();
        return;
    }

    if(document.form1.p_name_2.value == ""){
        alert("이름을 입력하세요!");
        form1.p_name_2.focus();
        return;
    }
    if (document.form1.gubun.value == "1") {

	    if (document.form1.p_email_2.value=="") {
	        alert("이메일을 정확히 입력하세요.");
	        form1.p_email_2.focus();
	        return;
	    }
    } else if(document.form1.gubun.value == "2") {
    	if (document.form1.p_resno_2_1.value=="") {
            alert("주민등록번호를 정확히 입력하세요.");
            form1.p_resno_2_1.focus();
            return;
        }

        if (document.form1.p_resno_2_2.value=="") {
            alert("주민등록번호를 정확히 입력하세요");
            form1.p_resno_2_2.focus();
            return;
        }
       
    }
    

   /*  if (document.form1.p_resno2_2.value=="") {
        alert("주민등록번호를 정확히 입력하세요.");
        form1.p_resno2_2.focus();
        return;
    } */
    if (document.form1.gubun.value == "1") {
    	 document.form1.p_process.value = "SendMail";
    } else {
    	document.form1.p_process.value = "CheckCerti";
    }
    
    //document.form1.p_process.value = "CheckCerti";
    document.form1.action = "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.submit();
}


// 메인페이지로
function indexList() {
    document.form1.p_process.value = "";
    document.form1.action = "/servlet/controller.homepage.MainServlet";
    document.form1.submit();
}

//자동이동
function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }

// 숫자만 들어가게 만든다.
function onlyNumber()
{
    if(((event.keyCode<48)||(event.keyCode>57)))
    event.returnValue=false;
}

   // SMS 전송
    function whenSMS() {

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "09";

      window.self.name = "StudentMemberList";
      open_window("openSMS","","100","100","620","354");
      document.form1.target = "openSMS";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendSMS';
      document.form1.submit();
    }
//-->
</script>
<!-- 스크립트영역종료 -->



<!-- form 시작 -->
 <form name="form1" method="post" action="javascript:return;">
 <input type='hidden' name='p_process'>
 <input type='hidden' name='gubun' value="1">  
      
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_idpass.gif" alt="회원정보변경"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>아이디/비밀번호 찾기</u></td>
			</tr>
		</table>
		<div class="idpass">
			<ul class="searchbox">
				<li class="idspace">
					<div class="idbox">
						<b>아이디 찾기</b>
						<p>가입시 입력한 성명과 주민등록번호로 아이디를 찾을 수 있습니다.</p>
						<table class="membercheck" cellpadding="0" cellspacing="0">
							<colgroup><col width="100"><col width="*"></colgroup>
							<tr>
								<th>성명(한글)</td>
								<td><input type="text" tabindex="171" name="p_name" class="txt" style="width:184px;" title="실명을 한글로 입력해주세요."></td>
							</tr>
							<tr>
								<th>주민등록번호</td>
								<td>
									<dl class="aligns">
										<dd><input type="text" tabindex="172" name="p_resno1" class="txt" style="width:80px;" maxlength="6" title="주민번호 앞자리 6자리를 입력해주세요."></dd>
										<dd class="hyphen">-</dd>
										<dd><input type="password" tabindex="173" name="p_resno2" class="txt" style="width:80px;" maxlength="7" title="주빈번호 뒤의 7자리를 입력해주세요."></dd>
									</dl>
								</td>
							</tr>
						</table>
						<p>
							주민등록번호는 <u>한국콘텐츠아카데미에 별도 저장되지 않으며</u>, 신용평가기관을 통한 본인확인용으로만 이용됩니다.
							<br><br>* 개인정보보호법에 따라 이용동의를 받습니다.
						</p>
						<table class="usecheck" cellpadding="0" cellspacing="0">
							<tr>
								<td><input type="checkbox" tabindex="174" class="checkbox" id="regagreen" name="p_agree1" title="개인정보보호법에 따라 이용에 동의합니다."><label for="regagreen" class="label">주민등록번호 처리에 동의합니다.</label></td>
							</tr>
						</table>
						<div class="idfooter"><a href="javascript:searchId()" tabindex="175" title="위 정보로 회원님의 아이디를 검색합니다"><img src="/images/2012/sub/page0/btn_confirm.gif" alt="확인" title="아이디 찾기를 시작합니다"></a></div>
					</div>
				</li>
				<li class="passwordspace">
					<div class="passwordbox">
						<b>비밀번호 찾기</b>
						<p>회원가입 시 등록한 정보를 아래에 입력해 주세요.</p>
						<table class="membercheck topline" cellpadding="0" cellspacing="0">
							<colgroup><col width="100"><col width="*"></colgroup>
							<tr>
								<th colspan="2" class="typeselect">
									<input type="radio" tabindex="181" name="checktype" id="email" onclick="passwordchecktype('1')" checked><label for="email" class="label">이메일로 찾기</label>
									&nbsp; <input type="radio" tabindex="181" onclick="passwordchecktype('2')" name="checktype" id="mobile"><label for="mobile" class="label">모바일로 찾기</label>
								</th>
							</tr>
							<tr>
								<th>아이디</td>
								<td><input type="text" tabindex="182" name="p_userid_2" class="txt" style="width:184px;" title="아이디를 입력해주세요."></td>
							</tr>
							<tr>
								<th>성명(한글)</td>
								<td><input type="text" tabindex="183" name="p_name_2" class="txt" style="width:184px;" title="실명을 한글로 입력해주세요."></td>
							</tr>
						</table>
						<table class="membercheck underline" cellpadding="0" cellspacing="0" id="certtype1">
							<colgroup><col width="100"><col width="*"></colgroup>
							<tr>
								<th>메일주소</td>
								<td><input type="text" tabindex="184" class="txt" name="p_email_2" style="width:184px;" title="실명을 한글로 입력해주세요."></td>
							</tr>
						</table>
						<table class="membercheck underline" cellpadding="0" cellspacing="0" id="certtype2" style="display:none;">
							<colgroup><col width="100"><col width="*"></colgroup>
							<tr>
								<th>주민등록번호</td>
								<td>									
									<input type="text" tabindex="184" class="txt" name ="p_resno_2_1" style="width:50px;" maxlength="6" title="주민번호를 입력해주세요.">
									- <input type="text" tabindex="185" class="txt" name ="p_resno_2_2" style="width:50px;" maxlength="7" title="주민번호를 입력해주세요.">
								</td>
							</tr>
						</table>
						<p>
							입력하신 정보가 올바른 경우,<br>
							<span id="certmsg1">임시 비밀번호가  발급됩니다.</span>
							<span id="certmsg2" style="display:none;">임시 비밀번호가 회원님의 핸드폰으로 문자가 발송됩니다.</span>
						</p>
						<div class="passwordfooter"><a href="javascript:searchPwd()" tabindex="186" title="위 정보가 일치할 경우 임시비밀번호를 e메일 또는 SMS로 발급합니다"><img src="/images/2012/sub/page0/btn_confirm.gif" alt="확인" title="아이디 찾기를 시작합니다"></a></div>
					</div>
				</li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>

</form>
<%
    if (!sMessage.equals("")) {
        out.println("<script language='javascript'> alert('"+ sMessage +"'); </script>");
    }

    if (sIsOk.equals("0")) {
       out.println("<script language='javascript'> window.open('http://check.namecheck.co.kr/checkplus_new_model/checkplus.cb?m=checkplusSerivce&EncodeData="+ sEncData + "','', 'width=425, height=535');</script>");
    }
%>	

<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
