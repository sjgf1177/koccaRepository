<%
//**********************************************************
//  1. 제      목: 비밀번호 변경
//  2. 프로그램명 :zu_PwdChange.jsp
//  3. 개      요: 비밀번호 변경
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String v_userid	 		= box.getSession("userid");
%>


<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!--<style type="text/css">
body {
	padding: 50px;
	}
h1 a {
	color: #000;
	}
input {
	border: 1px solid #999;
	margin: 0 5px;
	}
.password_strength {
	padding: 0 5px;
	display: inline-block;
	}
.password_strength_1 {
	background-color: #fcb6b1;
	}
.password_strength_2 {
	background-color: #fccab1;
	}
.password_strength_3 {
	background-color: #fcfbb1;
	}
.password_strength_4 {
	background-color: #dafcb1;
	}
.password_strength_5 {
	background-color: #bcfcb1;
	}
</style>

--><!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

function Submit_Check(){
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

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post">
	<input type="hidden"	name="p_process"	value="" />
    <input type="hidden"	name="p_userid"	value="<%= v_userid %>" />
    
    	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<table>
		<tr>
			<td>
			
			<!--타이틀부분-->
      <table width="672" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="30" colspan="2" class="h_road">&nbsp;</td>
        </tr>
        <tr>
          <td><img src="/images/portal/homepage_renewal/member/stitle_04.gif" alt="비밀번호변경" /></td>
          <td class="h_road">Home &gt; 이용안내  &gt; <strong>비밀번호변경</strong></td>
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
          <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="join_form">
            <tr>
              <th class="tit_table01">현재 비밀번호 확인 *</th>
              <td class="tit_table02"><input name="p_pwd_old" type="password" class="inp_txt_01" id="p_pwd_old" size="20" maxlength="" style="width:130px; height:20px;" /></td>
            </tr>
            <tr>
              <th>새 비밀번호 *</th>
              <td height="65" class="table_right"><input name="p_pwd" type="password" class="inp_txt_01" id="p_pwd" size="20" maxlength="" style="width:130px; height:20px;" />
                  <p style="padding-top:3px">* 영문 대/소문자와 숫자 또는 특수문자를 혼합하여 9-14자리로 입력해 주세요.<br />
                    *   연속된 숫자, 생일번호, 주민등록번호 등 알기 쉬운 숫자는 피해주세요.</p></td>
            </tr>
            <tr>
              <th>새 비밀번호 확인 *</th>
              <td class="table_right"><input name="p_pwd_re" type="password" class="inp_txt_01" id="p_pwd_re" size="20" maxlength="" style="width:130px; height:20px;" /></td>
            </tr>

          </table></td>
        </tr>
        <tr>
        	<td height="30"></td>
        </tr>
        <tr>
        	<td id="btn"><a href="javascript:Submit_Check();"><img src="/images/portal/homepage_renewal/member/btn_confirm.gif" alt="확인" /></a> <a href="/servlet/controller.homepage.MainServlet"><img src="/images/portal/homepage_renewal/member/btn_cancle.gif" alt="취소" /></a></td>
        </tr>
    </table></td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
			
			<%}else{ %>

        <h2><img src="/images/portal/member/membership/h7_tit.gif" alt="비밀번호변경"class="fl_l" /><p class="category">Home &gt; 이용안내 &gt; <strong>비밀번호변경</strong></p></h2>

        <p class="mg_t30"></p>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">
        <tr>
         <td bgcolor="#d4d9ee" style="border-top:1px solid #96a5e1;"><table width="100%" border="0" cellspacing="1" cellpadding="0">
           <tr>
             <td width="125" class="chart001">현재 비밀번호 *</td>
             <td class="chart002">
               <table width="100%" border="0" cellspacing="0" cellpadding="0">
                 <tr>
                   <td height="27"><input name="p_pwd_old" type="password" class="inp_txt_01" id="p_pwd_old" size="20" maxlength="" /></td>
                 </tr>
               </table> </td>
           </tr>
           <tr>
             <td width="125" class="chart001">새 비밀번호 *</td>
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
                <td width="125" class="chart001">새 비밀번호 확인 *</td>
                <td class="chart002">
                <input name="p_pwd_re" type="password" class="inp_txt_01" id="p_pwd_re" size="20" maxlength="" /></td>
           </tr>
         </table>
           </td>
        </tr>

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