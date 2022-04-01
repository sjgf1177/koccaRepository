<%
//**********************************************************
//  1. 제      목: OutUserComp_I 협력(계열사) 등록화면
//  2. 프로그램명 : za_OutUser_Comp_I.jsp
//  3. 개      요: OutUserComp_I 협력(계열사) 등록화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2004. 12. 26
//  7. 수      정: 신선철 2004. 12. 26
//***********************************************************
%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.homepage.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 1 ;

    String v_compnm = "";

    String s_userid = box.getSession("userid");
    String s_usernm = box.getSession("username");

    MemberInfoBean bean = new MemberInfoBean();

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript" type="text/javascript">

function insertAction() {
    if(document.form1.p_compnm.value == ""){
       alert("회사명을 입력하세요!");
       document.form1.p_compnm.focus();
       return;
    }

    var p_compresno = document.form1.p_compresno1.value + document.form1.p_compresno2.value + document.form1.p_compresno3.value;
    if(p_compresno!="" && !rnocheck(p_compresno)){
            alert("정확한 사업자등록번호를 입력하세요!");
            document.form1.p_compresno1.focus();
            return;
    }

    if (confirm("저장하시겠습니까?")) {
        document.form1.p_compresno.value = document.form1.p_compresno1.value +"-"+ document.form1.p_compresno2.value +"-"+ document.form1.p_compresno3.value;
        document.form1.p_telno.value     = document.form1.p_telno1.value     +"-"+ document.form1.p_telno2.value     +"-"+ document.form1.p_telno3.value;
        document.form1.p_faxno.value     = document.form1.p_faxno1.value     +"-"+ document.form1.p_faxno2.value     +"-"+ document.form1.p_faxno3.value;
        document.form1.p_hometel.value   = document.form1.p_hometel1.value   +"-"+ document.form1.p_hometel2.value   +"-"+ document.form1.p_hometel3.value;
        document.form1.p_handphone.value = document.form1.p_handphone1.value +"-"+ document.form1.p_handphone2.value +"-"+ document.form1.p_handphone3.value;

        document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
        document.form1.target = "_self";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }else{
        return;
    }

}

// 우편번호검색
function searchPost(gubun) {
    document.form1.p_postgubun.value = gubun;
    window.self.name = "PersonalSelect";
    open_window("openPost","","100","100","417","400","","","","yes","");
    document.form1.target = "openPost";
    document.form1.action='/servlet/controller.library.PostSearchServlet';
    document.form1.p_process.value = 'SearchPostOpenPage';
    document.form1.submit();
    document.form1.target = window.self.name;
}

function receivePost(post1, post2, addr, postgubun){
    if( postgubun == "1" ){
        document.form1.p_home_post1.value = post1;
        document.form1.p_home_post2.value = post2;
        document.form1.p_home_addr1.value = addr;
        document.form1.p_home_addr2.focus();
    }else if( postgubun == "2" ){
        document.form1.p_comp_post1.value = post1;
        document.form1.p_comp_post2.value = post2;
        document.form1.p_comp_addr1.value = addr;
        document.form1.p_comp_addr2.focus();
    }
}


//아이디 중복체크
function CheckUserid(){
    var tf = document.form1;
    window.self.name = "PersonalSelect";
    open_window("userList","","100","100","617","400","","","","yes","");
    document.form1.target = "userList";
    document.form1.action='/servlet/controller.system.OutUserAdminServlet';
    document.form1.p_process.value = 'userList';
    document.form1.submit();
    document.form1.target = window.self.name;
}
function putUserInfo(userInfo) {
	var f1 = document.form1;
	f1.p_userid.value = userInfo.v_userid;
	f1.p_name.value = userInfo.v_name;
	f1.p_pwd.value = userInfo.v_pwd;
	f1.p_email.value = userInfo.v_email;
	f1.p_hometel1.value = userInfo.v_hometel1;
	f1.p_hometel2.value = userInfo.v_hometel2;
	f1.p_hometel3.value = userInfo.v_hometel3;
	f1.p_hometel.value = userInfo.v_hometel;
	f1.p_handphone1.value = userInfo.v_handphone1;
	f1.p_handphone2.value = userInfo.v_handphone2;
	f1.p_handphone3.value = userInfo.v_handphone3;
	f1.p_handphone.value = userInfo.v_handphone;
	f1.p_home_post1.value = userInfo.v_post1;
	f1.p_home_post2.value = userInfo.v_post2;
	f1.p_home_addr1.value = userInfo.v_addr;
	f1.p_home_addr2.value = userInfo.v_addr2;
}

function changechk(chk){
  document.form1.p_isidchk.value = "N";
  return;
}

function moveFocus(num,fromform,toform)
    {
        var str = fromform.value.length;
        if(str == num)
       toform.focus();
    }

</script>

</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
  <input type= "hidden" name = "p_process"   value = "">
  <input type= "hidden" name = "p_ischk" value = "0">
  <input type= "hidden" name= "p_isoverlap" value= "N">
  <input type= "hidden" name = "p_isidchk"   value= "N">
  <input type= "hidden" name = "p_postgubun" value= "">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
    
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/tit_new_bman.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>회사등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
       
      <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="17%">분류</td>
          <td class="table_02_2" width="83%"><input name="p_gubun" type="radio" value="1" checked>콘텐츠제작업체 &nbsp;&nbsp;&nbsp;&nbsp; <input name="p_gubun" type="radio" value="2">채용업체&nbsp;&nbsp;&nbsp;&nbsp; <input name="p_gubun" type="radio" value="3">교육그룹
          </td>
        </tr>
        <tr> 
          <td class="table_title" width="17%">*회사명</td>
          <td class="table_02_2" width="83%"><input name="p_compnm" type="text" class="input" style="width:150" maxlength="20">
          </td>
        </tr>
        <tr> 
          <td class="table_title">사업자등록번호</td>
          <td class="table_02_2">
            <input name="p_compresno1" type="text" class="input" style="width:100" maxlength="3" onblur="numeric_chk(this)"> - 
            <input name="p_compresno2" type="text" class="input" style="width:70" maxlength="2" onblur="numeric_chk(this)"> - 
            <input name="p_compresno3" type="text" class="input" style="width:100" maxlength="5" onblur="numeric_chk(this)">
            <input name="p_compresno" type="hidden" value="">
          </td>
        </tr>
        <tr> 
          <td class="table_title">대표이름</td>
          <td class="table_02_2"><input name="p_coname" type="text" class="input" style="width:150" maxlength="10"></td>
        </tr>
        <tr> 
          <td class="table_title">대표전화번호</td>
          <td class="table_02_2">
            <input name="p_telno1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)"> - 
            <input name="p_telno2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)"> - 
            <input name="p_telno3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_telno"  type="hidden" value="">
          </td>
        </tr>
        <tr> 
          <td class="table_title">팩스번호</td>
          <td class="table_02_2">
            <input name="p_faxno1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)"> - 
            <input name="p_faxno2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)"> - 
            <input name="p_faxno3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_faxno" type="hidden" value="">
          </td>
        </tr>
        <tr>
          <td class="table_title">우편번호</td>
          <td class="table_02_2">
            <input type="text" class="input" name="p_comp_post1" size="5" maxlength="3" onblur="numeric_chk(this)" readonly> - <input type="text" class="input" name="p_comp_post2"  size="5" maxlength="3" onblur="numeric_chk(this)" readonly>
            &nbsp;<a href="javascript:searchPost(2)"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
        </tr>
        <tr> 
          <td class="table_title">사업장주소</td>
          <td class="table_02_2"><input name="p_comp_addr1" type="text" class="input" style="width:350" maxlength="50">&nbsp;&nbsp;<input name="p_comp_addr2" type="text" class="input" style="width:350" maxlength="50"></td>
        </tr>
        <tr> 
          <td class="table_title">홈페이지</td>
          <td class="table_02_2">http://<input name="p_homepage" type="text" class="input" style="width:280" maxlength="100"> &nbsp;&nbsp;(예:www.gameacademy.or.kr)</td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td ><img src="/images/admin/common/icon.gif" ></td>
          <td  class=sub_title>담당자 정보등록</td>
          <td width="82%" class=dir_txt>(나중에 입력하시려면 입력항목을 비워두시면 됩니다.)</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
      
      <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="17%">*아이디</td>
          <td class="table_02_2" width="35%"><table><tr><td><input name="p_userid" type="text" class="input" style="width:150" maxlength="20" onkeypress="javascript:changechk('id')" ></td><td><a href="javascript:CheckUserid()"><img src="/images/admin/portal/bt_search_ID.gif" border="0"></a></td></tr></table></td>
          <td class="table_title" width="15%">*성명</td>
          <td class="table_02_2" width="33%"><input name="p_name" type="text" class="input" style="width:120" maxlength="10"></td>
        </tr>
        <tr> 
          <td class="table_title">*비밀번호</td>
          <td class="table_02_2" colspan=3><input name="p_pwd" type="password" class="input" style="width:150" maxlength="20"></td>
        </tr>
        <tr> 
          <td class="table_title">E-mail</td>
          <td class="table_02_2" colspan=3><input name="p_email" type="text" class="input" style="width:150" maxlength="50"></td>
        </tr>
        <tr> 
          <td class="table_title">자택전화</td>
          <td class="table_02_2"> 
            <input name="p_hometel1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)"> -
            <input name="p_hometel2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)"> -
            <input name="p_hometel3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_hometel"  type="hidden" value="">
          </td>
          <td class="table_title">핸드폰</td>
          <td class="table_02_2">
            <input name="p_handphone1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)"> -
            <input name="p_handphone2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)"> -
            <input name="p_handphone3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_handphone" type="hidden" value="">
          </td>
        </tr> 
        <tr>
          <td class="table_title">우편번호</td>
          <td class="table_02_2" colspan="3">
            <input type="text" class="input" name="p_home_post1" size="5" maxlength="3" onblur="numeric_chk(this)" readonly> - <input type="text" class="input" name="p_home_post2"  size="5" maxlength="3" onblur="numeric_chk(this)" readonly>
            &nbsp;<a href="javascript:searchPost(1)"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
        </tr>
        <tr> 
          <td class="table_title">자택주소</td>
          <td class="table_02_2"  colspan="3"><input name="p_home_addr1" type="text" class="input" style="width:350" maxlength="50">&nbsp;&nbsp;<input name="p_home_addr2" type="text" class="input" style="width:350" maxlength="50"></td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right" valign="top"><a href="javascript:insertAction()" onfocus=this.blur()>
           <img src="/images/admin/button/btn_save.gif" border="0">
           </td>
          <td width="8"></td>
          <td width="32" align="right" valign="top"><a href="javascript:history.back();" onfocus=this.blur()>
                                                    <!-- selectAction() <--이거대신 history.back();-->
           <img src="/images/admin/button/btn_cancel.gif" border="0">
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>
</form> 
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>