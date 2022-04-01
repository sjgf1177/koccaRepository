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


<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->



<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//
function checkId(){
	var userid = document.form1.p_userid.value;
	var p_userid_len = userid.length;

		if(p_userid_len > 0){
			if(p_userid_len > 5 && p_userid_len < 13){
                // ID 중복검사
                var url = "/servlet/controller.homepage.MainMemberJoinServlet?p_userid="+userid+"&p_process=CheckId";
                var v_win = window.open(url, "selfTest1"," left=20, top=20, width=394, height=312, menubar=no, scrollbars=no, resizable=yes");
			}else{
				alert("아이디를 6자 이상 12자 이하로 다시 입력해주세요.");
				return;
			}
		}else{
			alert("아이디를 입력해주세요.");
			return;
		}
	}


function IDText_Check () {
    var Str = document.form1.p_userid.value;
    var Chr="";
    var chkCnt=0;
    for (i=0; i<Str.length; i++) {
        Chr = Str.charAt(i);
        if ((Chr < "0" || Chr >"9" ) && (Chr <'a' || Chr >'z'))  {
        alert("영문소문자, 숫자 입력만 가능합니다.");
            document.form1.p_userid.value;
            document.form1.p_userid.focus();
            return false;
        }
    }
}

// 우편번호검색
function searchPost(gubun) {
	window.self.name = "PersonalSelect";
	open_window("openPost","","100","100","417","400","","","","yes","");
	document.form1.target = "openPost";
	document.form1.action='/servlet/controller.library.PostSearchServlet';
	document.form1.p_postgubun.value = gubun;
	document.form1.p_process.value = 'SearchPostOpenPage';
	document.form1.submit();
	document.form1.target = window.self.name;
}

function delAddr(no){
	if( no == 1 ){
		document.form1.p_home_addr2.value = ""
    }
}

function receivePost(post1, post2, addr, postgubun){
	if( postgubun == "1" ){
		document.form1.p_post1.value = post1;
		document.form1.p_post2.value = post2;
		document.form1.p_home_addr1.value = addr;
		document.form1.p_home_addr2.focus();
    }
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

    /*
    if( (fm.p_kor_name.value) == ""){
        alert("이름을 입력하세요");
        fm.p_kor_name.focus();return;
    }

    if( (fm.p_userid.value) == "" ){
        alert("아이디를 바르게 입력하세요");
        fm.p_userid.focus();return;
    }

    chk_num = fm.p_userid.value.search(/[0-9]/g);
    chk_lower = fm.p_userid.value.search(/[a-z]/ig);

    if (!/^[a-z0-9]{6,12}$/.test(fm.p_userid.value) ||  (chk_num < 0 || chk_lower< 0) ){
        alert("아이디는 영문 소문자, 숫자를 혼합하여 6-12자리로 입력해 주세요.");
        fm.p_userid.value;
        fm.p_userid.focus();
        return;
    }

    if(fm.p_chk.value != "Y"){
        alert("아이디 중복체크를 하세요"); return;
    }

    // 비밀번호 길이 제한
    var p_pwd_len =  fm.p_pwd.value.length;
    var pass_old = fm.p_pwd_old.value;
    var p_pwd_len_old = pass_old.length;

    chk_num = fm.p_pwd.value.search(/[0-9]/g);
    chk_lower = fm.p_pwd.value.search(/[a-z]/ig);
    chk_upper = fm.p_pwd.value.search(/[A-Z]/ig);
    chk_special = fm.p_pwd.value.search(/[^a-zA-Z0-9]/g);

    if ((p_pwd_len <= 0) && (p_pwd_len_old <= 0)){
        alert("비밀번호를 입력해주세요.");
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
                alert("비밀번호확인을 입력하세요");
                fm.p_pwd_re.focus();return;
             }

            if(fm.p_pwd.value != fm.p_pwd_re.value){
                alert("비밀번호가 다르게 입력되었습니다.");
                fm.p_pwd.value;
                fm.p_pwd_re.value ="";
                fm.p_pwd_re.focus();
                return;
            }
        }
    }
    */
    
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

    // 주소 (둘중하나만 입력)
    /*var v_post = fm.p_post1.value + fm.p_post2.value;

    if( v_post == "" ){
        alert("우편번호를 입력하세요");
        fm.p_post1.focus();	return;
    }

    if( (fm.p_home_addr1.value) == "" ){
        alert("주소를 입력하세요");
        fm.p_home_addr1.focus();	return;
    }else if( (fm.p_home_addr2.value) == "" ){
        alert("주소를 입력하세요");
        fm.p_home_addr2.focus();	return;
    }*/
    // -->

    if (fm.p_email1.value =="" || fm.p_email2.value == ""){
        alert("Email 주소를 바르게 입력하세요");
        fm.p_email1.focus();return;
    }

    /*
    // 이메일
    if(isValInput(fm.p_email, "이메일", 50, "U") == false)
    {
        return false;
    }
    */

    // e-mail check
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
    /*

    if( fm.p_registerMotive.value == "" ){
		alert("가입동기를 선태하십시오.");
		return;
    }

    if( fm.p_registerRoute.value == "" ){
		alert("가입경로를 선택하십시오.");
		return;
    }*/

// 이벤트  과정 선택값

    //if( fm.p_eventCourse.value == "" ){
	//    if( !fm.p_gameCourse.value == "" ) document.form1.p_eventCourse.value    = fm.p_gameCourse.value;
	//    if( !fm.p_cultureCourse.value == "" ) document.form1.p_eventCourse.value = fm.p_cultureCourse.value;
	//    if( !fm.p_airCourse.value == "" ) document.form1.p_eventCourse.value     = fm.p_airCourse.value;
    //}

    // 가입
    document.form1.target="_self";
    document.form1.action= https_host() + "/servlet/controller.homepage.MainMemberJoinServlet";
    document.form1.p_handphone.value = v_handphone;
    document.form1.p_hometel.value = v_hometel;
    document.form1.p_email.value = v_email;    
    document.form1.p_process.value="MemberInfoUpdateOk";
    document.form1.submit();
}

function CourseChange(gubun){
	var fm=document.form1;
	var v_gubun = gubun;

	if ( v_gubun == "CG" ) {
	    if( !fm.p_gameCourse.value == ""){
	        if( !fm.p_cultureCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다.");
				fm.p_gameCourse.value = ""
				return;            
	        }
	        if( !fm.p_airCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다.");  
				fm.p_gameCourse.value = ""       
				return;   
	        }
	    }
	} else if ( v_gubun == "CK" ){
	    if( !fm.p_cultureCourse.value == ""){
	        if( !fm.p_gameCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다.");  
				fm.p_cultureCourse.value = ""
				return;           
	        }
	        if( !fm.p_airCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다.");     
				fm.p_cultureCourse.value = ""       
				return; 
		    }
	    }
	} else {
	    if( !fm.p_airCourse.value == ""){
	        if( !fm.p_cultureCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다."); 
				fm.p_airCourse.value = ""
				return;            
	        }
	        if( !fm.p_gameCourse.value == ""){
				alert("하나의 과정만 선택 가능합니다.");  
				fm.p_airCourse.value = ""
				return;           
	        }
	    }
	}    
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

//임시 엑셀 파일 출력
function testExcel(){
//     document.form1.target = "_self";
     document.form1.action='/servlet/controller.homepage.MainMemberJoinServlet';
     document.form1.p_process.value = 'temporarilyExcel';
     document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post">
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
    <input type="hidden"	name="p_pwd_old"	value="<%= v_pwd %>" />
    <input type="hidden"	name="p_eventseq"	value="<%= s_eventseq %>" />
     <input type="hidden"	name="p_eventgubun"   value="<%=p_eventgubun%>"/>
    
    
    
              <%if( box.getSession("tem_grcode").equals("N000001")) { %>
      
      <table>   
	      <tr>
	      	
	      	<td width="672" valign="top"><!--타이틀부분-->
      <table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal/member/stitle_03.gif" alt="회원가입" /></td>
          <td class="h_road">Home &gt; 이용안내  &gt; <strong>개인정보변경</strong></td>
        </tr>
        <tr>
          <td height="12" colspan="2"></td>
        </tr>
        <tr>
          <td height="1" colspan="2" bgcolor="E5E5E5"></td>
        </tr>
      </table>
      <!--타이틀부분//-->
      <table width="672" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="15"></td>
        </tr>
        <tr>
          <td>
          </td>
        </tr>
      </table>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td id="s_stit"><img src="/images/portal/homepage_renewal/member/s_stitle_04.gif" alt="회원정보" /></td>
        </tr>
        <tr>
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="join_form">
            <tr>
              <td class="tit_table01">성명(한글)</td>
              <td class="tit_table02"><strong><%=v_name %></strong></td>
            </tr>
            <tr>
              <th>아이디 *</th>
              <td class="table_right"><strong><%=v_userid %></strong></td>
            </tr>
            <tr>
              <th>성별 *</th>
              <td class="table_right"><input type="radio" name="p_sex" id="radio" class="checkbox" value="1" <%= v_sex.equals("1") ? "checked" : "" %> />
                남자
                <input type="radio" name="p_sex" id="radio2" class="checkbox" value="2"  <%= v_sex.equals("2") ? "checked" : "" %> />
                여자 </td>
            </tr>
            <tr>
              <th>생년월일 *</th>
              <td class="table_right"><input type="text" name="p_memberyear"  value="<%=v_memberyear %>" id="textfield5" style="width:50px; height:20px;" />
                년
                <input type="text" name="p_membermonth" id="p_membermonth" value="<%=v_membermonth %>" style="width:50px; height:20px;" />
                월
  <input type="text" name="p_memberday" id="p_memberday"  value="<%=v_memberday %>"style="width:50px; height:20px;" />
                일</td>
            </tr>
            <!-- <tr>
              <th>비밀번호 *</th>
              <td height="65" class="table_right"><input type="text" name="textfield8" id="textfield8" style="width:130px; height:20px;" />
                  <p style="padding-top:3px">* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.<br />
                    *   연속된 숫자, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주세요.</p></td>
            </tr>
            <tr>
              <th>비밀번호 확인 *</th>
              <td class="table_right"><input type="text" name="textfield9" id="textfield9" style="width:130px; height:20px;" /></td>
            </tr> -->
            <tr>
              <th>전화번호 *</th>
              <td class="table_right">
              <select name="p_hometel1" id="p_hometel1" value="<%= hometel[0]%>">
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
                -
  <input name="p_hometel2" type="text" class="inp_txt_01" id="p_hometel2" size="10" maxlength="4" value="<%= hometel[1]%>" style="width:70px; height:20px;" />
                -
  <input name="p_hometel3" type="text" class="inp_txt_01" id="p_hometel3" size="10" maxlength="4" value="<%= hometel[2]%>" style="width:70px; height:20px;" /></td>
            </tr>
            <tr>
              <th>휴대폰 *</th>
              <td class="table_right">
              <select name="p_handphone1" id="p_handphone1">
                    <option value="010" <%= handphone[0].equals("010") ? "selected" : "" %>>010</option>
                    <option value="011" <%= handphone[0].equals("011") ? "selected" : "" %>>011</option>
                    <option value="016" <%= handphone[0].equals("016") ? "selected" : "" %>>016</option>
                    <option value="017" <%= handphone[0].equals("017") ? "selected" : "" %>>017</option>
                    <option value="018" <%= handphone[0].equals("018") ? "selected" : "" %>>018</option>
                    <option value="019" <%= handphone[0].equals("019") ? "selected" : "" %>>019</option>
                </select>
                -
  <input name="p_handphone2" type="text" class="inp_txt_01" id="p_handphone2" size="10" maxlength="4" value="<%= handphone[1]%>" style="width:70px; height:20px;" />
                -
  <input name="p_handphone3" type="text" class="inp_txt_01" id="p_handphone3" size="10" maxlength="4" value="<%= handphone[2]%>" style="width:70px; height:20px;" /></td>
            </tr>
            <tr>
              <th>주소(행정구역) *</th>
              <td class="table_right">
              	<select name="p_home_addr1" id="p_home_addr1" style="height:20px">
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
              <th>이메일 *</th>
              <td height="65" class="table_right">
              <input name="p_email1" type="text" class="inp_txt_01" id="p_email1" size="20" maxlength="20" value="<%= email[0]%>" style="width:130px; height:20px;" />
                @
                <input name="p_email2" type="text" class="inp_txt_01" id="p_email2" size="20"  maxlength="20" value="<%= email[1]%>" style="width:130px; height:20px;" />    
    			  <select name="p_email3" id="p_email3" onchange="emailChange();" style="height:20px">
                       <option value="">직접입력</option>
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
    <p style="padding-top:3px">* 한국콘텐츠아카데미에서 제공하는 웹진을 비롯 회원정보 수정관련 필요한 것으로<br />
      주로 사용하시는 이메일 주소를 입력해주세요.</p></td>
            </tr>
            <tr>
              <th>직업 *</th>
              <td class="table_right">
              	<%=CodeConfigBean.getCodeGubunSelect ("0104","", 1,"p_jikup", v_jikup,"",3) %>
              </td>
            </tr>
            <tr>
              <th>종사분야 *</th>
              <td class="table_right">
              	<%=CodeConfigBean.getCodeGubunSelect ("0103","", 1,"p_workfieldcd",  v_workfieldcd, "onChange='javascript:workfieldChange()'",3) %>
                <div id="divWork"></div>
              </td>
            </tr>
             <tr>
              <th>소속 *</th>
              <td class="table_right"><span style="padding-top:2px;">
                <input type="text" name="p_compnm"  value="<%=v_compnm %>" style="width:250px; height:20px;" />                
              </span></td>
            </tr>
			<tr>
              <th>정보수신 여부</th>
              <td class="table_right">
                <input type="checkbox" name="p_ismailing" id="p_ismailing" onclick="checkYN();" value="<%= v_ismailing %>" <% if(v_ismailing.equals("Y")) out.println("checked='checked'"); %> />
                                 서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.              </td>
            </tr>
            

          </table></td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
        </tr>
        <!--<tr>
          <td id="s_stit"><img src="/images/portal/homepage_renewal/member/s_stitle_08.gif" alt="부가정보" /></td>
        </tr>
        <tr>
          <td id="s_stit">이벤트 기간동안 개인정보를 변경하시는 회원분들에 한하여 현재 유료로 제공되고 있는 과정을 무료로 수강하실 수 있는<br />
          기회를 드립니다. 하단의 메뉴를 통하여 1개 과정을 선택해주세요.</td>
        </tr>
        <tr>
          <td>
          <%  if( v_eventCourse == "" ){ %>  
          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="join_form">
              <tr>
                <td class="tit_table01">게임
                </td>
                <td class="tit_table02">
                  <select name="p_gameCourse" id="p_gameCourse" onchange="CourseChange('CG');" style="height:20px">
                	<option value="">===선택===</option>             	
                	<option value="CG10005">게임 기획 및 그래픽 실무자를 위한 게임기술 이해</option>             	
                	<option value="CG10006">UDK 활용 방법(스크립트)</option>             	
                	<option value="CG10007">UDK 활용 방법(툴)</option>             	
                	<option value="CG10008">아이폰 및 아이패드용 게임 제작(기초)</option>             	
                	<option value="CG10009">아이폰 및 아이패드용 게임 제작(실무)</option>             	
                	<option value="CG10010">안드로이드 게임 제작(기초)</option>             	
                	<option value="CG10011">안드로이드 게임 제작(실무)</option>             	
				</select>
                </td>
              </tr>
              <tr>
                <th>문화콘텐츠</th>
                <td class="table_right">
                <select name="p_cultureCourse" id="p_cultureCourse" onchange="CourseChange('CK');" style="height:20px">
                	<option value="">===선택===</option>             	
                	<option value="CG10012">문화콘텐츠연출론</option>             	
                	<option value="CK10071">11가지 법칙으로 끝내는 스토리텔링</option>             	
                	<option value="CK10072">축제기획과 문화콘텐츠</option>             	
                	<option value="CK10073">대중음악콘텐츠기획과 해외진출</option>             	
                	<option value="CK10074">분야별로 배우는 콘텐츠 마케팅</option>             	
                	<option value="CK10075">예술경영의 실무</option>             	
                	<option value="CK10076">사례로 알아보는 소셜네트워크</option>             	
                	<option value="CK10077">글로컬문화콘텐츠의 이해와 사례</option>             	
                	<option value="CK10078">대중문화예술인의 법적권리</option>             	
                	<option value="CK10079">대중문화예술인과 인권</option>             	
                	<option value="CK10080">공연기획과 홍보마케팅 실무</option>             	
                	<option value="CK10081">스마트폰과 문화</option>             	
                	<option value="CK10082">쉽게 배우는 전시기획</option>             	
				</select>
                </td>
              </tr>
              <tr>
                <th>방송</th>
                <td class="table_right">
                <select name="p_airCourse" id="p_airCourse" onchange="CourseChange('CB');"  style="height:20px">
                	<option value="">===선택===</option>             	
                	<option value="CB10015">After Effect CS5</option>             	
                	<option value="CB10016">Avid Media Composer Advanced</option>             	
                	<option value="CB10017">Adobe Premiere Pro CS5</option>             	
                	<option value="CB10019">Final cut Pro의 Motion 제작 실무</option>             	
                	<option value="CB10020">디지털 영상 색 보정</option>             	
                	<option value="CB10021">Autodesk Maya를 이용한 입체영상 제작</option>             	
                	<option value="CB10023">3D 입체편집</option>             	
                	<option value="CB10024">3D 입체촬영실무</option>             	
                	<option value="CB10025">3D 영상제작개론</option>             	
                	<option value="CB10027">디지털 포맷과 워크플로우</option>             	
				</select>
                </td>
              </tr>
          </table>
          <%}else{ %>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" id="join_form">
            <tr>
              <td class="tit_table01">이벤트 선택 과정</td>
              <td class="tit_table02"><strong><%=v_subjnm %></strong></td>
            </tr>
            </table>      
          <%} %>
          </td>
        </tr>
        <tr>
          <td height="30">* 이벤트 과정은 2012년2월 1차 과정에 자동 입과됩니다.(학습기간 : 2012.02.01 ~ 02.29)</td>
        </tr>
        <tr>
          <td height="30">&nbsp;</td>
        </tr>-->
        <tr>
        	<td id="btn"><a href="javascript:Submit_Check();"><img src="/images/portal/homepage_renewal/member/btn_confirm.gif" alt="확인" /></a> <a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/homepage_renewal/member/btn_cancle.gif" alt="취소" /></a></td>
        </tr>
    </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
	      	
	      	<%}else{ %>
    


        <h2><img src="/images/portal/member/membership/h2_tit.gif" alt="개인정보변경" class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>개인정보변경</strong></p></h2>

        <p class="mg_t30"><img src="/images/portal/member/membership/stit_baseinfo.gif"  alt="기본정보" /></p>
        <p class="mg_t15"></p>
        <!-- p class="mg_t15"><a href="javascript:testExcel()">임시출력</a></p -->

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
           <tr>
             <td width="125" class="chart001">성명(한글)</td>
             <td class="chart002" colspan="3"><strong><%=v_name %></strong></td>
           </tr>
           
           <tr>
             <td width="125" class="chart001">성별 *</td>
             <td width="125" class="chart002">
<%           if( v_sex.equals("1")){ %>
			 	<input name="p_sex" type="radio" value="1" id="p_sex" checked disabled />남자 
				<input name="p_sex" type="radio" value="2" id="p_sex" disabled />여자
<%           }else if( v_sex.equals("2")){ %>
	 			<input name="p_sex" type="radio" value="1" id="p_sex" disabled />남자
	 			<input name="p_sex" type="radio" value="2" id="p_sex" checked disabled />여자
<%           } %>
			 </td>
             <td width="125" class="chart001">생년월일</td>
             <td width="200" class="chart002">
<%           if ( v_memberyear.equals("")){ %>
				 <input name="p_memberyear"  type="text" class="inp_txt_01" id="p_memberyear"  value="<%=v_resno1_Year %>"  size="5" maxlength="4" readonly />&nbsp;년&nbsp;&nbsp;
	 			 <input name="p_membermonth" type="text" class="inp_txt_01" id="p_membermonth" value="<%=v_resno1_Month %>" size="5" maxlength="2" readonly />&nbsp;월&nbsp;&nbsp;
	 			 <input name="p_memberday"   type="text" class="inp_txt_01" id="p_memberday"   value="<%=v_resno1_Day %>"   size="5" maxlength="2" readonly />&nbsp;일&nbsp;&nbsp;	
<%           } else { %>             
			     <input name="p_memberyear"  type="text" class="inp_txt_01" id="p_memberyear"  value="<%=v_memberyear %>"  size="5" maxlength="4" readonly />&nbsp;년&nbsp;&nbsp;
	 			 <input name="p_membermonth" type="text" class="inp_txt_01" id="p_membermonth" value="<%=v_membermonth %>" size="5" maxlength="2" readonly />&nbsp;월&nbsp;&nbsp;
	 			 <input name="p_memberday"   type="text" class="inp_txt_01" id="p_memberday"   value="<%=v_memberday %>"   size="5" maxlength="2" readonly />&nbsp;일&nbsp;&nbsp;	
<%           } %>
             </td>  
           </tr>
           
           
           
           
           <tr>
             <td width="125" class="chart001">아이디 *</td>
             <td class="chart002" colspan="3"><%=v_userid %></td>
           </tr>
            <%--
           <tr>
             <td width="125" class="chart001">비밀번호 *</td>
             <td class="chart002">
               <table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="27"><input name="p_pwd" type="password" class="inp_txt_01" id="p_pwd" size="20" maxlength="" /></td>
                 </tr>
                 <tr>
                   <td><span class="lh_20 fs_11 ls">* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.<br />
                    * 연속된 숫자, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주세요.</span></td>
                 </tr>
               </table> </td>
           </tr>
           <tr>
                <td width="125" class="chart001">비밀번호 확인 *</td>
                <td class="chart002">
                <input name="p_pwd_re" type="password" class="inp_txt_01" id="p_pwd_re" size="20" maxlength="" /></td>
           </tr>
           --%>
           <tr>
             <td width="125" class="chart001">전화번호 *</td>
             <td class="chart002" colspan="3">
                 <select name="p_hometel1" id="p_hometel1" value="<%= hometel[0]%>">
                    <option value="02" <%= hometel[0].equals("02") ? "selected" : "" %>>02</option>
                    <option value="051" <%= hometel[0].equals("051") ? "selected" : "" %>>051</option>
                    <option value="053" <%= hometel[0].equals("053") ? "selected" : "" %>>053</option>
                    <option value="032" <%= hometel[0].equals("032") ? "selected" : "" %>>032</option>
                    <option value="062" <%= hometel[0].equals("062") ? "selected" : "" %>>062</option>
                    <option value="042" <%= hometel[0].equals("042") ? "selected" : "" %>>042</option>
                    <option value="052" <%= hometel[0].equals("052") ? "selected" : "" %>>052</option>
                    <option value="031" <%= hometel[0].equals("031") ? "selected" : "" %>>031</option>
                    <option value="033" <%= hometel[0].equals("033") ? "selected" : "" %>>033</option>
                    <option value="043" <%= hometel[0].equals("043") ? "selected" : "" %>>043</option>
                    <option value="041" <%= hometel[0].equals("041") ? "selected" : "" %>>041</option>
                    <option value="063" <%= hometel[0].equals("063") ? "selected" : "" %>>063</option>
                    <option value="061" <%= hometel[0].equals("061") ? "selected" : "" %>>061</option>
                    <option value="054" <%= hometel[0].equals("054") ? "selected" : "" %>>054</option>
                    <option value="055" <%= hometel[0].equals("055") ? "selected" : "" %>>055</option>
                    <option value="064" <%= hometel[0].equals("064") ? "selected" : "" %>>064</option>
                </select>
                -
                <input name="p_hometel2" type="text" class="inp_txt_01" id="p_hometel2" size="10" maxlength="4" value="<%= hometel[1]%>" />
                -
                <input name="p_hometel3" type="text" class="inp_txt_01" id="p_hometel3" size="10" maxlength="4" value="<%= hometel[2]%>" />
             </td>
           </tr>
           <tr>
                <td width="125" class="chart001">휴대폰 * </td>
                <td class="chart002" colspan="3">
                <select name="p_handphone1" id="p_handphone1">
                    <option value="010" <%= handphone[0].equals("010") ? "selected" : "" %>>010</option>
                    <option value="011" <%= handphone[0].equals("011") ? "selected" : "" %>>011</option>
                    <option value="016" <%= handphone[0].equals("016") ? "selected" : "" %>>016</option>
                    <option value="017" <%= handphone[0].equals("017") ? "selected" : "" %>>017</option>
                    <option value="018" <%= handphone[0].equals("018") ? "selected" : "" %>>018</option>
                    <option value="019" <%= handphone[0].equals("019") ? "selected" : "" %>>019</option>
                </select>
                -
                <input name="p_handphone2" type="text" class="inp_txt_01" id="p_handphone2" size="10" maxlength="4" value="<%= handphone[1]%>" />
                -
                <input name="p_handphone3" type="text" class="inp_txt_01" id="p_handphone3" size="10" maxlength="4" value="<%= handphone[2]%>" />
                </td>
           </tr>
           <tr>
             <td width="125" class="chart001">주소 *</td>
             <td class="chart002" colspan="3">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="27">
                        <input name="p_post1" type="text" class="inp_txt_01" id="p_post1" size="10" readonly value="<%=v_post1%>" /> -
                        <input name="p_post2" type="text" class="inp_txt_01" id="p_post2" size="10" readonly value="<%=v_post2%>" />
                        <a href="javascript:searchPost(1);" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image24','','/images/portal/member/membership/btn_zip_o.gif',1)">
                            <img src="/images/portal/member/membership/btn_zip_a.gif" name="Image24" width="64" height="24" border="0" align="absmiddle" id="Image24" alt="우편번호"/></a></td>
                 </tr>
                 <tr>
                    <td><input name="p_home_addr1" type="text" class="inp_txt_01" id="p_home_addr1" size="40" readonly value="<%=v_home_addr1 %>" />
                      <input name="p_home_addr2" type="text" class="inp_txt_01" id="p_home_addr2" size="40" value="<%=v_home_addr2%>" maxlength="40" /></td>
                 </tr>
                </table></td>
           </tr>
           <tr>
             <td class="chart001">이메일 *</td>
             <td class="chart002" colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                 <td height="27"><input name="p_email1" type="text" class="inp_txt_01" id="p_email1" size="20" maxlength="20" value="<%= email[0]%>"/> @
                   <input name="p_email2" type="text" class="inp_txt_01" id="p_email2" size="20"  maxlength="20" value="<%= email[1]%>"/>
                   <select name="p_email3" id="p_email3" onchange="emailChange();">
                       <option value="">직접입력</option>
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
                   </select></td>
               </tr>
               <tr>
                 <td><span class="lh_20 fs_11 ls">* 한국콘텐츠아카데미에서 제공하는 웹진을 비롯 회원정보 수정관련 필요한 것으로<br />
                   주로 사용하시는 이메일 주소를 입력해주세요.</span></td>
               </tr>
             </table></td>
           </tr>
           <tr>
             <td class="chart001">정보수신 여부 *</td>
             <td class="chart002" colspan="3">
               <input type="checkbox" name="p_ismailing" id="p_ismailing" onclick="checkYN();" value="<%= v_ismailing %>" <% if(v_ismailing.equals("Y")) out.println("checked='checked'"); %> />
               <span class="lh_20 fs_11 ls">서비스 및 이벤트 소식을 E-Mail 혹은 SMS으로 수신 하겠습니다.</span>
             </td>
           </tr>
           <tr>
             <td class="chart001">직업 *</td>
             <td class="chart002" colspan="3">
                 <%=CodeConfigBean.getCodeGubunSelect ("0104","", 1,"p_jikup", v_jikup,"",3) %>
             </td>
           </tr>
           <tr>
             <td class="chart001">종사분야 *</td>
             <td class="chart002" colspan="3">
                 <%=CodeConfigBean.getCodeGubunSelect ("0103","", 1,"p_workfieldcd",  v_workfieldcd, "onChange='javascript:workfieldChange()'",3) %>
                <div id="divWork"></div>
             </td>
           </tr>
           <tr>
             <td class="chart001">소속 *</td>
             <td class="chart002" colspan="3">
             	<input type="text" name="p_compnm" id="" value="<%=v_compnm %>" size="50" maxlength="100" class="inp_txt_01" />
             </td>
           </tr>
           
         </table>
           </td>
        </tr>
        </table>
        
        <!-- 부가정보 입력 -->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
          <tr>
            <td style="padding-top:20px;">
              <img src="/images/portal/member/membership/addinfo.gif"  alt="부가정보" />
            </td>
          </tr>
        </table> 
        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
           <tr>
             <td width="125" class="chart001">가입동기 *</td>
             <td class="chart002" colspan="3">
             	<%=CodeConfigBean.getCodeGubunSelect ("0106","", 1,"p_registerMotive", v_registerMotive,"",3) %>
             </td>
           </tr>
           <tr>
             <td width="125" class="chart001">가입경로 *</td>
             <td class="chart002" colspan="3">
             	<%=CodeConfigBean.getCodeGubunSelect ("0105","", 1,"p_registerRoute", v_registerRoute,"",3) %>
             </td>
           </tr></table>
         </td>
         </tr>
         </table>
         
        <!-- 개인정보 변경 이벤트 서비스 시작 --> 
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
          <tr>
            <td style="padding-top:20px;">
              <img src="/images/portal/member/membership/event_service.gif"  alt="이벤트 서비스" />
            </td>
          </tr>
        </table> 
        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
          <tr>
            <td>
              이벤트 기간동안 개인정보를 변경하시는 회원분들에 한하여 현재 유료로 제공되고 있는 과정을 무료로 수강하실 수 있는 기회를 드립니다. 하단의 메뉴를 통하여 1개 과정을 선택해주세요. 
            </td>
          </tr>
        </table> 

<%  if( v_eventCourse == "" ){ %>        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
           <tr>
             <td width="125" class="chart001">게임</td>
             <td class="chart002" colspan="3">
			 	<select name="p_gameCourse" id="p_gameCourse" onchange="CourseChange('CG');">
                	<option value="">===선택===</option>             	
                	<option value="CG10005">게임 기획 및 그래픽 실무자를 위한 게임기술 이해</option>             	
                	<option value="CG10006">UDK 활용 방법(스크립트)</option>             	
                	<option value="CG10007">UDK 활용 방법(툴)</option>             	
                	<option value="CG10008">아이폰 및 아이패드용 게임 제작(기초)</option>             	
                	<option value="CG10009">아이폰 및 아이패드용 게임 제작(실무)</option>             	
                	<option value="CG10010">안드로이드 게임 제작(기초)</option>             	
                	<option value="CG10011">안드로이드 게임 제작(실무)</option>             	
				</select>
             </td>
           </tr>
           <tr>
           <td width="125" class="chart001">문화콘텐츠</td>
             <td class="chart002" colspan="3">
			 	<select name="p_cultureCourse" id="p_cultureCourse" onchange="CourseChange('CK');">
                	<option value="">===선택===</option>             	
                	<option value="CG10012">문화콘텐츠연출론</option>             	
                	<option value="CK10071">11가지 법칙으로 끝내는 스토리텔링</option>             	
                	<option value="CK10072">축제기획과 문화콘텐츠</option>             	
                	<option value="CK10073">대중음악콘텐츠기획과 해외진출</option>             	
                	<option value="CK10074">분야별로 배우는 콘텐츠 마케팅</option>             	
                	<option value="CK10075">예술경영의 실무</option>             	
                	<option value="CK10076">사례로 알아보는 소셜네트워크</option>             	
                	<option value="CK10077">글로컬문화콘텐츠의 이해와 사례</option>             	
                	<option value="CK10078">대중문화예술인의 법적권리</option>             	
                	<option value="CK10079">대중문화예술인과 인권</option>             	
                	<option value="CK10080">공연기획과 홍보마케팅 실무</option>             	
                	<option value="CK10081">스마트폰과 문화</option>             	
                	<option value="CK10082">쉽게 배우는 전시기획</option>             	
				</select>
             </td>
           </tr>
           <tr>
             <td width="125" class="chart001">방송</td>
             <td class="chart002" colspan="3">
			 	<select name="p_airCourse" id="p_airCourse" onchange="CourseChange('CB');">
                	<option value="">===선택===</option>             	
                	<option value="CB10015">After Effect CS5</option>             	
                	<option value="CB10016">Avid Media Composer Advanced</option>             	
                	<option value="CB10017">Adobe Premiere Pro CS5</option>             	
                	<option value="CB10019">Final cut Pro의 Motion 제작 실무</option>             	
                	<option value="CB10020">디지털 영상 색 보정</option>             	
                	<option value="CB10021">Autodesk Maya를 이용한 입체영상 제작</option>             	
                	<option value="CB10023">3D 입체편집</option>             	
                	<option value="CB10024">3D 입체촬영실무</option>             	
                	<option value="CB10025">3D 영상제작개론</option>             	
                	<option value="CB10027">디지털 포맷과 워크플로우</option>             	
				</select>
             </td>
           </tr></table>
         </td>
         </tr>
         </table>
<%} else { %>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
           <tr>
             <td width="125" class="chart001">이벤트 선택 과정</td>
             <td class="chart002" colspan="3">
             <%=v_subjnm %>
             </td>
           </tr></table>
         </td>
         </tr>
         </table>
<%} %>        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
          <tr>
            <td>
              * 이벤트 과정은 2012년2월 1차 과정에 자동 입과됩니다.(학습기간 : 2012.02.01 ~ 02.29)
            </td>
          </tr>
        </table> 
      
        <!-- 개인정보 변경 이벤트 서비스 시작 -->
        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td height="50" align="center">
             <a href="javascript:Submit_Check();"><img src="/images/portal/member/membership/btn_ok.gif" width="85" height="29" alt="확인" /></a>
             <a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/member/membership/btn_cancel.gif" width="85" height="29" alt="취소" /></a>
         </td>
        </tr>
        </table>
        
        <%} %>
</form>
<!-- form 끝 -->

<script type='text/javascript' src='/script/jquery.password_strength.js'></script>
<script language="JavaScript" type="text/JavaScript">
    $('form1').attr('autocomplete', 'off');
    $('#p_pwd').password_strength();
</script>

<!-- footer -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!--// footer -->