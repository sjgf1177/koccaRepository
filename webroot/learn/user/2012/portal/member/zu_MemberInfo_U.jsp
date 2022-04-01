<%
//**********************************************************
//  1. 제      목: 회원가입_회원정보 수정
//  2. 프로그램명 :zu_MemberInfo_Ujsp
//  3. 개      요: 회원가입_회원정보 수정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","1");
    box.put("submenu","4");
    
    String p_eventgubun = box.getString("p_eventgubun"); 

    DataBox dbox  = (DataBox)request.getAttribute("memberView");
    DataBox edbox = (DataBox)request.getAttribute("getEventCourse");  //이벤트 과정 가져오기..

    String v_handphone 		= "";
    String v_resno1     	= "";
    String v_resno2     	= "";
	String v_userid	 		= "";
	String v_pwd	   		= "";
	String v_name   		= "";
	String v_eng_name  		= "";
	String v_sex     		= "";
	String v_post1   		= "";
	String v_post2	   		= "";
	String v_home_addr1		= "";
	String v_home_addr2		= "";
	String v_home_addr		= "";
	String v_email	   		= "";
	String v_hometel  		= "";
	String v_ismailing   	= "";
    String v_issms   	    = "";
    String v_workfieldcd	= "";
    String v_workfieldnm	= "";
    String v_memberyear   	= "";
    String v_membermonth	= "";
    String v_memberday	    = "";
    String v_jikup	        = "";
    String v_compnm  	    = "";
    String v_registerMotive = "";
    String v_registerRoute  = "";
    String v_eventCourse    = "";
    String v_eventseq		= "";
    String s_eventseq		= "";
    
    // 생년월일 컬럼이 비여있으면 주민번호에서 생년월일을 가져온다.
    String v_resno1_Year    = "";
    String v_resno1_Month   = "";
    String v_resno1_Day     = "";

    if (dbox != null ) {
        v_handphone 	 = dbox.get("d_handphone");
        v_resno1     	 = dbox.get("d_resno1");
        v_resno2     	 = dbox.get("d_resno2");
        v_userid	 	 = dbox.get("d_userid");
        v_pwd	   		 = dbox.get("d_pwd");
        v_name   		 = dbox.get("d_name");
        v_eng_name  	 = dbox.get("d_eng_name");
        v_sex       	 = dbox.get("d_sex");
        v_post1   		 = dbox.get("d_post1");
        v_post2	   		 = dbox.get("d_post2");
        v_home_addr1	 = dbox.get("d_addr");
        v_home_addr2	 = dbox.get("d_addr2");
        v_home_addr		 = dbox.get("d_addr").substring(0,2);
        v_email	   		 = dbox.get("d_email");
        v_hometel  		 = dbox.get("d_hometel");
        v_ismailing   	 = dbox.get("d_ismailing");
        v_issms   	     = dbox.get("d_ismailing");
        v_workfieldcd	 = dbox.get("d_workfieldcd");
        v_workfieldnm	 = dbox.get("d_workfieldnm");
        v_memberyear	 = dbox.get("d_memberyear");
        v_membermonth	 = dbox.get("d_membermonth");
        v_memberday	     = dbox.get("d_memberday");
        v_jikup	         = dbox.get("d_jikup");
        v_compnm	     = dbox.get("d_compnm");
        v_registerMotive = dbox.get("d_registerMotive");
        v_registerRoute	 = dbox.get("d_registerRoute");
        v_eventCourse	 = dbox.get("d_eventCourse");
        v_eventseq		 = dbox.get("d_eventseq");
        
        if(!v_resno1.equals("")){
        v_resno1_Year    = 19+v_resno1.substring(0, 2);
        v_resno1_Month   = v_resno1.substring(2, 4);
        v_resno1_Day     = v_resno1.substring(4, 6);
        }
        
    }
    
    //이벤트 참가자 비교
    if(v_eventseq.equals("3")){
    	s_eventseq = "34";	
    }else if(v_eventseq.equals("")){
    	s_eventseq = "4";
    }

    
    //이벤트 과정 가져오기..
    String v_subjnm = "";

    if (edbox != null ) {
        v_subjnm 	 = edbox.get("d_subjnm");
    }
    
	String[]handphone = new String[3];
	int index = 0;
	StringTokenizer st = new StringTokenizer(v_handphone, "-");
	while(st.hasMoreTokens()) {
		handphone[index++] = st.nextToken();
	}

	String[]hometel = new String[3];
	index = 0;
	st = new StringTokenizer(v_hometel, "-");
	while(st.hasMoreTokens()) {
		hometel[index++] = st.nextToken();
	}

	String[]email = new String[2];
	index = 0;
	st = new StringTokenizer(v_email, "@");
	while(st.hasMoreTokens()) {
		email[index++] = st.nextToken();
	}
	
	String[]home_addr = new String[2];
	index = 0;
	st = new StringTokenizer(v_home_addr, "@");
	while(st.hasMoreTokens()) {
		home_addr[index++] = st.nextToken();
	}
	
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//


function Submit_PWDCheck(){
    var fm=document.form1;

    var chk_num = "";  
    var chk_lower = "";
    var chk_upper = "";
    var chk_special = "";

    // 비밀번호 길이 제한
   
    var p_pwd_len =  fm.p_pwd.value.length;
    var pass_old = fm.p_pwd_old.value;
    var p_pwd_len_old = pass_old.length;

    chk_num = fm.p_pwd.value.search(/[0-9]/g);
    chk_lower = fm.p_pwd.value.search(/[a-z]/ig);
    chk_upper = fm.p_pwd.value.search(/[A-Z]/ig);
    chk_special = fm.p_pwd.value.search(/[^a-zA-Z0-9]/g);

    if (p_pwd_len_old <= 0){
        alert("현재 비밀번호를 입력해주세요.");
        fm.p_pwd_old.focus();
        return;
    } else if (p_pwd_len <= 0){
        alert("새 비밀번호를 입력해주세요.");
        fm.p_pwd.focus(); 
        return;
    }else{
        if (fm.p_pwd.value != "") {
            //if (!/^[a-zA-Z0-9]{9,14}$/.test(fm.p_pwd.value) || (chk_num < 0 || chk_lower< 0 || chk_upper < 0)){
            if ( p_pwd_len < 9 || p_pwd_len > 14 || chk_lower< 0 || chk_upper < 0 ||  (chk_num < 0 && chk_special< 0 ) ){
                alert("비밀번호는 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.");
                fm.p_pwd.value="";
                fm.p_pwd.focus();
                return;
            }

            if( fm.p_pwd.value == fm.p_userid.value){
                alert("아이디와 동일한 비밀번호는 사용 하실 수 없습니다.");
                fm.p_pwd.value="";
                fm.p_pwd.focus();return;
            }
            
            if( (fm.p_pwd_re.value) == ""){
                alert("새 비밀번호확인을 입력하세요");
                fm.p_pwd_re.focus();return;
             }

            if(fm.p_pwd.value != fm.p_pwd_re.value){
                alert("새 비밀번호가 다르게 입력되었습니다.");
                fm.p_pwd.value;
                fm.p_pwd_re.value ="";
                fm.p_pwd_re.focus();
                return;
            }
        }
    }

    // 비밀번호 변경
    document.form1.target="_self";
    document.form1.action= https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.p_process.value="ChangePwdOk";
    document.form1.submit();
}

function Submit_Check(){
    var fm=document.form1;
    var v_hometel = fm.p_hometel1.value+'-'+fm.p_hometel2.value+'-'+fm.p_hometel3.value;
    var v_handphone = fm.p_handphone1.value+'-'+fm.p_handphone2.value+'-'+fm.p_handphone3.value;
    var v_email = fm.p_email1.value+'@'+fm.p_email2.value;

    var chk_num = "";
    var chk_lower = "";
    var chk_upper = "";
    var chk_special = "";

   if( (fm.p_hometel1.value) == "" ){
        alert("전화번호를 입력하세요");
        fm.p_hometel1.focus(); return;
    }else if( (fm.p_hometel2.value) == "" || fm.p_hometel2.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel2.focus(); return;
    }else if( (fm.p_hometel3.value) == "" || fm.p_hometel3.length > 4 ){
        alert("전화번호를 바르게 입력하세요");
        fm.p_hometel3.focus(); return;
    }

    if( (fm.p_handphone1.value) == "" ){
        alert("핸드폰 번호를 입력하세요");
        fm.p_handphone1.focus(); return;
    }else if( (fm.p_handphone2.value) == "" || fm.p_handphone2.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone2.focus(); return;
    }else if( (fm.p_handphone3.value) == "" || fm.p_handphone3.length > 4 ){
        alert("핸드폰 번호를 바르게 입력하세요");
        fm.p_handphone3.focus(); return;
    }

    if (fm.p_email1.value =="" || fm.p_email2.value == ""){
        alert("Email 주소를 바르게 입력하세요");
        fm.p_email1.focus();return;
    }

    if(is_mail(v_email) == false)
    {
        alert("정확한 E-Mail을 입력하십시오.");
        fm.p_email1.focus();
        return;
    }

    if( fm.p_jikup.value == "" ){
		alert("직업을 선택하십시오.");
		return;
    }
    
    if( fm.p_workfieldcd.value == "" ){
		alert("종사분야를 선택하십시오.");
		return;
    }

    if( fm.p_compnm.value == "" ){
		alert("소속을 입력하세요.");
		return;
    }
   
    // 가입
    document.form1.target="_self";
    document.form1.action= https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.p_handphone.value = v_handphone;
    document.form1.p_hometel.value = v_hometel;
    document.form1.p_email.value = v_email;    
    document.form1.p_process.value="MemberInfoUpdateOk";
    document.form1.submit();
}

//-------------------------------------------------------
// 이메일 및 이메일형태 ID검사
//-------------------------------------------------------
function is_mail(strEmail)
{
    /** 금지사항
     - @가 2개이상
     - .이 붙어서 나오는 경우
     -  @.나  .@이 존재하는 경우
     - 맨처음이.인 경우 **/
    var regDoNot = /(@.*@)|(\.\.)|(@\.)|(\.@)|(^\.)/;

    /** 필수사항
     - @이전에 하나이상의 문자가 있어야 함
     - @가 하나있어야 함
     - Domain명에 .이 하나 이상 있어야 함
     - Domain명의 마지막 문자는 영문자 2~4개이어야 함 **/
    var regMust = /^[a-zA-Z0-9\-\.\_]+\@[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,4})$/;

    if ( !regDoNot.test(strEmail) && regMust.test(strEmail) )
    {
        return true;
    }
    else
    {
        return false;
    }
}

function emailChange() {
    var r=$("[name='p_email3']").val();
    $("[name='p_email2']").val(r);
}

function workfieldChange() {
    if (document.form1.p_workfieldcd.value == "99") {
        document.getElementById('divWork').innerHTML = "<input name='p_workfieldnm' type='text' class='inp_txt_01' id='p_workfieldnm' size='30' maxlength='15' value='<%= v_workfieldnm %>'>";
       // document.form1.p_workfieldnm.value="";
    } else {
        document.getElementById('divWork').innerHTML="";
    }
}

function checkYN()
{
    var r=$("[name='p_ismailing']").val();
    if(r=="Y")
        $("[name='p_ismailing']").val("N");
    else
        $("[name='p_ismailing']").val("Y");
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="/" method="post" action="">
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_handphone" />
  <input type="hidden"	name="p_hometel" />
  <input type="hidden"	name="p_email" />
	<input type="hidden"	name="p_postgubun" value=""/>
	<input type="hidden"	name="p_chk" />
	<input type="hidden"	name="p_eventCourse" value="<%=v_eventCourse %>"/ >
	<!-- input type="hidden"	name="p_gameCourse" value=""/>
	<input type="hidden"	name="p_cultureCourse" value=""/>
	<input type="hidden"	name="p_airCourse" value=""/ -->
  <input type="hidden"	name="p_kor_name" value="<%=v_name%>" />
  <input type="hidden"	name="p_userid"	value="<%= v_userid %>" />
  <input type="hidden"	name="p_eventseq"	value="<%= s_eventseq %>" />
	<input type="hidden"	name="p_eventgubun"   value="<%=p_eventgubun%>"/>
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_modify.gif" alt="회원정보변경"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>회원정보변경</u></td>
			</tr>
		</table>
		<div class="membermodify">
			<table class="persnaltip" cellpadding="0" cellspacing="0">
				<tr>
					<td>회원정보는 개인정보 취급방침에 따라 안전하게 보호되며, 회원님의 명백한 동의 없이 공개 또는 제 3자에게 제공되지 않습니다. <a href="javascript:menuForward('90','06');" tabindex="170" title="개인정보처리방침으로 이동합니다.">개인정보처리방침</a></td>
					<td class="checks"><img src="/images/2012/common/icon_check.gif" alt="필수입력사항"> 필수입력사항</td>
				</tr>
			</table>
			<ul class="layout">
				<li class="leftspace">
					<table class="password" cellpadding="0" cellspacing="0">
						<colgroup><col width="160"><col width="*"></colgroup>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">현재 비밀번호 확인</th>
							<td><input type="password" name="p_pwd_old" id="txtfield1" onfocus="choicefield('1');" class="password" tabindex="171" title="현재 사용하시는 비밀번호를 입력해주세요."></td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">새 비밀번호</th>
							<td>
								<input type="password" name="p_pwd" id="txtfield2" onfocus="choicefield('2');" class="password" tabindex="172" title="새로 변경할 비밀번호를 입력해주세요.">
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">새 비밀번호 확인</th>
							<td><input type="password" name="p_pwd_re" id="txtfield3" onfocus="choicefield('3');" class="password" tabindex="173" title="새로 변경할 비밀번호를 다시 한번 입력해주세요."></td>
						</tr>
					</table>
					<p class="passwordtip">
						* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.<br>
						* 연속된 숫자, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주세요.
					</p>
					<div class="footer"><a href="javascript:Submit_PWDCheck();" tabindex="174" title="현재의 비밀번호를 위에 입력한 새로운 비밀번호로 변경합니다"><image src="/images/2012/sub/page0/btn_password_confirm.gif" alt="비빌번호를 변경합니다"></a></div>
				</li>
				<li class="rightspace">
					<table class="about" cellpadding="0" cellspacing="0">
						<colgroup><col width="120"><col width="*"></colgroup>
						<tr>
							<th>성명(한글)</th>
							<td><%=v_name %></td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">아이디</th>
							<td><%=v_userid %></td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">성별</th>
							<td>
								<table class="layoutdefault" cellpadding="0" cellspacing="0">
									<tr>
										<td><input type="radio" class="radio" id="male" name="p_sex" value="1"  <%= v_sex.equals("1") ? "checked" : "" %> tabindex="181" title="방향키 좌.우를 이용하여 성별을 선택해주세요(현재 남성)"><label for="male" class="label">남성</label></td>
										<td style="width:20px;"></td>
										<td><input type="radio" class="radio" id="female" name="p_sex" value="2"  <%= v_sex.equals("2") ? "checked" : "" %> tabindex="181" title="방향키 좌.우를 이용하여 성별을 선택해주세요(현재 여성)"><label for="female" class="label">여성</label></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">생년월일</th>
							<td>
								<table class="layoutdefault" cellpadding="0" cellspacing="0">
									<tr>
										<td><input type="text" name="p_memberyear" id="txtfield4" onfocus="choicefield('4');" value="<%=v_memberyear %>" class="txt" style="width:36px;" maxlength="4" tabindex="182" title="태어나신 년도를 입력해주세요">년</td>
										<td><input type="text" name="p_membermonth" id="txtfield5" onfocus="choicefield('5');" value="<%=v_membermonth %>" class="txt" style="width:20px;" maxlength="2" tabindex="183" title="생년월일의 월을 입력해주세요">월</td>
										<td><input type="text" name="p_memberday" id="txtfield6" onfocus="choicefield('6');" value="<%=v_memberday %>" class="txt" style="width:20px;" maxlength="2" tabindex="184" title="생년월일의 일자를 입력해주세요">일</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">전화번호</th>
							<td>
								<table class="layoutdefault" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<select class="select" name="p_hometel1" value="<%= hometel[0]%> id="txtfield7" onfocus="choicefield('7');" tabindex="185" title="지역번호를 선택해주세요">
												<option value="02" <%= hometel[0].equals("02") ? "selected" : "" %>>02</option>
							                    <option value="031" <%= hometel[0].equals("031") ? "selected" : "" %>>031</option>
							                    <option value="032" <%= hometel[0].equals("032") ? "selected" : "" %>>032</option>
							                    <option value="033" <%= hometel[0].equals("033") ? "selected" : "" %>>033</option>
							                    <option value="041" <%= hometel[0].equals("041") ? "selected" : "" %>>041</option>
							                    <option value="042" <%= hometel[0].equals("042") ? "selected" : "" %>>042</option>
							                    <option value="043" <%= hometel[0].equals("043") ? "selected" : "" %>>043</option>
							                    <option value="044" <%= hometel[0].equals("044") ? "selected" : "" %>>044</option>
							                    <option value="051" <%= hometel[0].equals("051") ? "selected" : "" %>>051</option>
							                    <option value="052" <%= hometel[0].equals("052") ? "selected" : "" %>>052</option>
							                    <option value="053" <%= hometel[0].equals("053") ? "selected" : "" %>>053</option>
							                    <option value="054" <%= hometel[0].equals("054") ? "selected" : "" %>>054</option>
							                    <option value="055" <%= hometel[0].equals("055") ? "selected" : "" %>>055</option>                    
							                    <option value="061" <%= hometel[0].equals("061") ? "selected" : "" %>>061</option>
							                    <option value="062" <%= hometel[0].equals("062") ? "selected" : "" %>>062</option>
							                    <option value="063" <%= hometel[0].equals("063") ? "selected" : "" %>>063</option>
							                    <option value="064" <%= hometel[0].equals("064") ? "selected" : "" %>>064</option>
							                    <option value="070" <%= hometel[0].equals("070") ? "selected" : "" %>>070</option>     
											</select>
										</td>
										<td>&nbsp;-&nbsp;<input type="text" name="p_hometel2" value="<%= hometel[1]%>" id="txtfield8" onfocus="choicefield('8');" class="txt" style="width:36px;" maxlength="4" tabindex="186" title="유선번호 앞 3~4자리를 입력해주세요"></td>
										<td>&nbsp;-&nbsp;<input type="text" name="p_hometel3" value="<%= hometel[2]%>" id="txtfield9" onfocus="choicefield('9');" class="txt" style="width:36px;" maxlength="4" tabindex="187" title="유선번호 뒤 4자리를 입력해주세요"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">모바일번호</th>
							<td>
								<table class="layoutdefault" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<select class="select" name="p_handphone1" id="txtfield10" onfocus="choicefield('10');" tabindex="188" title="이동통신번호를 입력해주세요">
												<option value="010" <%= handphone[0].equals("010") ? "selected" : "" %>>010</option>
		                    <option value="011" <%= handphone[0].equals("011") ? "selected" : "" %>>011</option>
		                    <option value="016" <%= handphone[0].equals("016") ? "selected" : "" %>>016</option>
		                    <option value="017" <%= handphone[0].equals("017") ? "selected" : "" %>>017</option>
		                    <option value="018" <%= handphone[0].equals("018") ? "selected" : "" %>>018</option>
		                    <option value="019" <%= handphone[0].equals("019") ? "selected" : "" %>>019</option>
											</select>
										</td>
										<td>&nbsp;-&nbsp;<input type="text" name="p_handphone2" value="<%= handphone[1]%>" id="txtfield11" onfocus="choicefield('11');" class="txt" style="width:36px;" maxlength="4" tabindex="189" title="모바일 앞3~4자리를 입력해주세요"></td>
										<td>&nbsp;-&nbsp;<input type="text" name="p_handphone3" value="<%= handphone[2]%>" id="txtfield12" onfocus="choicefield('12');" class="txt" style="width:36px;" maxlength="4" tabindex="190" title="모바일 뒤4자리를 입력해주세요"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">주소(행정구역)</th>
							<td>
								<select class="select" name="p_home_addr1" id="txtfield13" onfocus="choicefield('13');" tabindex="191" title="거주지역을 선택해주세요">
								  <option value="강원" <%= home_addr[0].equals("강원") ? "selected" : "" %>>강원</option>
                  <option value="경기" <%= home_addr[0].equals("경기") ? "selected" : "" %>>경기</option>
                  <option value="경남" <%= home_addr[0].equals("경남") ? "selected" : "" %>>경남</option>
                  <option value="경북" <%= home_addr[0].equals("경북") ? "selected" : "" %>>경북</option>
								  <option value="광주" <%= home_addr[0].equals("광주") ? "selected" : "" %>>광주</option>
								  <option value="대구" <%= home_addr[0].equals("대구") ? "selected" : "" %>>대구</option>
								  <option value="대전" <%= home_addr[0].equals("대전") ? "selected" : "" %>>대전</option>
								  <option value="부산" <%= home_addr[0].equals("부산") ? "selected" : "" %>>부산</option>
								  <option value="서울" <%= home_addr[0].equals("서울") ? "selected" : "" %>>서울</option>
								  <option value="울산" <%= home_addr[0].equals("울산") ? "selected" : "" %>>울산</option>
								  <option value="인천" <%= home_addr[0].equals("인천") ? "selected" : "" %>>인천</option>
								  <option value="전남" <%= home_addr[0].equals("전남") ? "selected" : "" %>>전남</option>
								  <option value="전북" <%= home_addr[0].equals("전북") ? "selected" : "" %>>전북</option>
								  <option value="제주" <%= home_addr[0].equals("제주") ? "selected" : "" %>>제주</option>
								  <option value="충남" <%= home_addr[0].equals("충남") ? "selected" : "" %>>충남</option>
								  <option value="충북" <%= home_addr[0].equals("충북") ? "selected" : "" %>>충북</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">이메일</th>
							<td>
								<table class="layoutdefault" cellpadding="0" cellspacing="0">
									<tr>
										<td><input type="text" name="p_email1"  value="<%= email[0]%>" id="txtfield14" onfocus="choicefield('14');" class="txt" style="width:90px;" tabindex="192" title="메일 아이디를 입력해주세요"></td>
										<td>@<input type="text" name="p_email2" value="<%= email[1]%>" id="txtfield15" onfocus="choicefield('15');" class="txt" style="width:90px;" tabindex="193" title="메일 서버를 입력해주세요"></td>
										<td>
											<select class="select" name="p_email3" onchange="emailChange();" id="txtfield16" onfocus="choicefield('16');" tabindex="194" title="메일서버를 선택해주세요">
											   <option value="" >직접입력</option>
											   <option value="naver.com" >naver.com</option>
	                       <option value="chol.com" >chol.com</option>
	                       <option value="dreamwiz.com" >dreamwiz.com</option>
	                       <option value="empal.com" >empal.com</option>
	                       <option value="freechal.com" >freechal.com</option>
	                       <option value="gmail.com" >gmail.com</option>
	                       <option value="hanafos.com" >hanafos.com</option>
	                       <option value="hanmail.net" >hanmail.net</option>
	                       <option value="hanmir.com" >hanmir.com</option>
	                       <option value="hitel.net" >hitel.net</option>
	                       <option value="hotmail.com" >hotmail.com</option>
	                       <option value="korea.com" >korea.com</option>
	                       <option value="lycos.co.kr" >lycos.co.kr</option>
	                       <option value="nate.com" >nate.com</option>
	                       <option value="netian.com" >netian.com</option>
	                       <option value="paran.com" >paran.com</option>
	                       <option value="yahoo.com" >yahoo.com</option>
	                       <option value="yahoo.co.kr" >yahoo.co.kr</option>
											</select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">직업</th>
							<td>
								<select class="select" name="p_jikup" id="txtfield17" onfocus="choicefield('17');" tabindex="195" title="직업을 선택해주세요">
									<option value="01" <%=v_jikup.equals("01") ? "selected" : ""%> >고등학생 이하</option>
									<option value="02" <%=v_jikup.equals("02") ? "selected" : ""%>>대학생 (대학원)</option>
									<option value="03" <%=v_jikup.equals("03") ? "selected" : ""%>>공무직 (공직)</option>
									<option value="04" <%=v_jikup.equals("04") ? "selected" : ""%>>공공기관</option>
									<option value="05" <%=v_jikup.equals("05") ? "selected" : ""%>>프리랜서</option>
									<option value="06" <%=v_jikup.equals("06") ? "selected" : ""%>>예비취업자</option>
									<option value="07" <%=v_jikup.equals("07") ? "selected" : ""%>>회사원</option>
									<option value="08" <%=v_jikup.equals("08") ? "selected" : ""%>>주부</option>
									<option value="09" <%=v_jikup.equals("09") ? "selected" : ""%>>군인</option>
									<option value="10" <%=v_jikup.equals("10") ? "selected" : ""%>>교직원</option>
									<option value="11" <%=v_jikup.equals("11") ? "selected" : ""%>>의료인</option>
									<option value="12" <%=v_jikup.equals("12") ? "selected" : ""%>>법조인</option>
									<option value="13" <%=v_jikup.equals("13") ? "selected" : ""%>>언론인</option>
									<option value="14" <%=v_jikup.equals("14") ? "selected" : ""%>>종교인</option>
									<option value="15" <%=v_jikup.equals("15") ? "selected" : ""%>>연예인</option>
									<option value="16" <%=v_jikup.equals("16") ? "selected" : ""%>>체육인</option>
									<option value="17" <%=v_jikup.equals("17") ? "selected" : ""%>>교수</option>
									<option value="18" <%=v_jikup.equals("18") ? "selected" : ""%>>강사</option>
									<option value="19" <%=v_jikup.equals("19") ? "selected" : ""%>>자영업</option>
									<option value="99" <%=v_jikup.equals("99") ? "selected" : ""%>>기타</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">종사분야</th>
							<td>
								<select class="select" name="p_workfieldcd"  id="txtfield18" onfocus="choicefield('18');" tabindex="196" title="종사분야를 선택해주세요">
									 <option value=01 <%=v_workfieldcd.equals("01") ? "selected" : ""%>>게임</option> 
									 <option value=02 <%=v_workfieldcd.equals("02") ? "selected" : ""%>>만화</option> 
									 <option value=03 <%=v_workfieldcd.equals("03") ? "selected" : ""%>>모바일</option> 
									 <option value=04 <%=v_workfieldcd.equals("04") ? "selected" : ""%>>방송</option> 
									 <option value=05 <%=v_workfieldcd.equals("05") ? "selected" : ""%>>애니메이션</option> 
									 <option value=06 <%=v_workfieldcd.equals("06") ? "selected" : ""%>>에듀테인먼트</option> 
									 <option value=07 <%=v_workfieldcd.equals("07") ? "selected" : ""%>>영화</option> 
									 <option value=08 <%=v_workfieldcd.equals("08") ? "selected" : ""%>>음악</option> 
									 <option value=09 <%=v_workfieldcd.equals("09") ? "selected" : ""%>>캐릭터</option> 
									 <option value=10 <%=v_workfieldcd.equals("10") ? "selected" : ""%>>출판</option> 
									 <option value=11 <%=v_workfieldcd.equals("11") ? "selected" : ""%>>인쇄</option> 
									 <option value=12 <%=v_workfieldcd.equals("12") ? "selected" : ""%>>영상</option> 
									 <option value=13 <%=v_workfieldcd.equals("13") ? "selected" : ""%>>디자인</option> 
									 <option value=14 <%=v_workfieldcd.equals("14") ? "selected" : ""%>>광고</option> 
									 <option value=15 <%=v_workfieldcd.equals("15") ? "selected" : ""%>>공연</option> 
									 <option value=16 <%=v_workfieldcd.equals("16") ? "selected" : ""%>>미술품</option> 
									 <option value=17 <%=v_workfieldcd.equals("17") ? "selected" : ""%>>공예품</option> 
									 <option value=18 <%=v_workfieldcd.equals("18") ? "selected" : ""%>>패션</option> 
									 <option value=19 <%=v_workfieldcd.equals("19") ? "selected" : ""%>>전시</option> 
									 <option value=20 <%=v_workfieldcd.equals("20") ? "selected" : ""%>>SW</option> 
									 <option value=21 <%=v_workfieldcd.equals("21") ? "selected" : ""%>>HW</option> 
									 <option value=22 <%=v_workfieldcd.equals("22") ? "selected" : ""%>>신문</option> 
									 <option value=99 <%=v_workfieldcd.equals("99") ? "selected" : ""%>>기타</option>
								</select>
							</td>
						</tr>
						<tr>
							<th><img src="/images/2012/common/icon_check.gif" alt="필수입력사항">소속</th>
							<td><input type="text" name="p_compnm"  value="<%=v_compnm %>" id="txtfield19" onfocus="choicefield('19');" class="txt" style="width:160px;" tabindex="197" title="소속을 입력해주세요"></td>
						</tr>
						<tr>
							<th>정보수신 여부</th>
							<td><input type="checkbox" name="p_ismailing" onclick="checkYN();" value="<%= v_ismailing %>" <% if(v_ismailing.equals("Y")) out.println("checked='checked'");%>  class="checkbox" id="mailsms" tabindex="198" title="서비스 및 이벤트 소식을 E-mail 혹은 SMS로 수신에 동의하신 다면 체크해주세요"><label for="mailsms" class="label">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로<br>&nbsp;&nbsp;&nbsp; 수신 하겠습니다. </label></td>
						</tr>
					</table>
					<table class="footer" cellspacing="0" cellpadding="0">
						<tr>
							<td class="alignR"><a href="javascript:Submit_Check();" tabindex="199" title="위의 입력한 내용으로 회원정보를 변경합니다"><img src="/images/2012/sub/page0/btn_modify_confirm.gif" alt="회원정보를 변경합니다"></a></td>
							<td class="alignL"><a href="javascript:history.go(-1);" tabindex="200" title="회원정보 변경을 취소합니다" tabindex="75"><img src="/images/2012/sub/page0/btn_modify_cancel.gif" alt="회원정보 변경을 취소합니다"></a></td>
						</tr>
					</table> 
				</li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
