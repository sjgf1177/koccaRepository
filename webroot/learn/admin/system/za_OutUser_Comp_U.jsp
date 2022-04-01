<%
//**********************************************************
//  1. 제      목: OutUserComp_U 협력(계열사) 수정화면
//  2. 프로그램명 : za_OutUser_Comp_U.jsp
//  3. 개      요: OutUserComp_U 협력(계열사) 수정화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2004. 12. 27
//  7. 수      정: 신선철 2004. 12. 27
//***********************************************************
%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    //int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 1 ;

    String v_comp = "", v_compnm = "", v_coname = ""; 
    String v_gubun = "", v_userid = "", v_name = "", v_pwd = "", v_email = "", v_str_tmp = "";
    String v_compresno = "", v_compresno1 = "",  v_compresno2 = "", v_compresno3 = "";
    String v_telno     = "", v_telno1     = "",  v_telno2     = "", v_telno3 = "";
    String v_faxno     = "", v_faxno1     = "",  v_faxno2     = "", v_faxno3 = "";
    String v_handphone = "", v_handphone1 = "",  v_handphone2 = "", v_handphone3 = "";
    String v_hometel   = "", v_hometel1   = "",  v_hometel2   = "", v_hometel3    = "";
    String v_resno     = "", v_resno1     = "",  v_resno2     = "";
    String v_zip1  = "", v_zip2  = "", v_comp_addr1 = "", v_comp_addr2 = ""; 
    String v_post1 = "", v_post2 = "", v_home_addr1 = "", v_home_addr2 = "", v_homepage="";  

    int v_index1 = 0, v_index2 = 0;

    String s_userid = box.getSession("userid");
    String s_usernm = box.getSession("username");

    DataBox dbox = (DataBox)request.getAttribute("select2outcomp");
    if (dbox != null) {
        v_comp = dbox.getString("d_comp");
        v_compnm = dbox.getString("d_compnm");
        v_compresno = dbox.getString("d_compresno");
        v_gubun = dbox.getString("d_gubun");
        v_homepage = dbox.getString("d_homepage");

        if(v_compresno.length() > 0){
            v_index1       =   v_compresno.indexOf('-');
            v_compresno1   =   v_compresno.substring(0,v_index1);
            v_str_tmp      =   v_compresno.substring(v_index1+1);
            v_index2       =   v_str_tmp.indexOf('-');
            v_compresno2   =   v_str_tmp.substring(0,v_index2);
            v_compresno3   =   v_str_tmp.substring(v_index2+1);
        }

        v_coname = dbox.getString("d_coname");
        v_telno = dbox.getString("d_telno");
        if(v_telno.length() > 0 && v_telno.indexOf('-') != -1){
            v_index1   =   v_telno.indexOf('-');
            v_telno1   =   v_telno.substring(0,v_index1);
            v_str_tmp  =   v_telno.substring(v_index1+1);
            v_index2   =   v_str_tmp.indexOf('-');
            v_telno2   =   v_str_tmp.substring(0,v_index2);
            v_telno3   =   v_str_tmp.substring(v_index2+1);
        }

        v_faxno = dbox.getString("d_faxno");
        if(v_faxno.length() > 0 && v_faxno.indexOf('-') != -1){
            v_index1   =   v_faxno.indexOf('-');
            v_faxno1   =   v_faxno.substring(0,v_index1);
            v_str_tmp  =   v_faxno.substring(v_index1+1);
            v_index2   =   v_str_tmp.indexOf('-');
            v_faxno2   =   v_str_tmp.substring(0,v_index2);
            v_faxno3   =   v_str_tmp.substring(v_index2+1);
        }

        v_comp_addr1 = dbox.getString("d_compaddr1");
        v_comp_addr2 = dbox.getString("d_compaddr2");
        v_zip1 = dbox.getString("d_zip1");
        v_zip2 = dbox.getString("d_zip2");

        v_userid = dbox.getString("d_userid");
        if(v_userid.equals("")){
           v_userid = "";
        }
        v_resno =  dbox.getString("d_resno");
        if(v_resno.length() == 13){
            v_resno1 =  v_resno.substring(0,6);;
            v_resno2 =  v_resno.substring(6);
        }
        v_name = dbox.getString("d_name");
        v_pwd = dbox.getString("d_pwd");
        v_email = dbox.getString("d_email");

        v_handphone = dbox.getString("d_handphone");

        if(v_handphone.length() > 0 && v_handphone.indexOf('-') != -1){
          v_index1       =   v_handphone.indexOf('-');
          v_handphone1   =   v_handphone.substring(0,v_index1);
          v_str_tmp      =   v_handphone.substring(v_index1+1);
          v_index2       =   v_str_tmp.indexOf('-');
          v_handphone2   =   v_str_tmp.substring(0,v_index2);
          v_handphone3   =   v_str_tmp.substring(v_index2+1);
        }

        v_hometel = dbox.getString("d_hometel");

        if(v_hometel.length() > 0 && v_hometel.indexOf('-') != -1){
          v_index1       =   v_hometel.indexOf('-');
          v_hometel1   =   v_hometel.substring(0,v_index1);
          v_str_tmp      =   v_hometel.substring(v_index1+1);
          v_index2       =   v_str_tmp.indexOf('-');
          v_hometel2   =   v_str_tmp.substring(0,v_index2);
          v_hometel3   =   v_str_tmp.substring(v_index2+1);
        }

        v_home_addr1 = dbox.getString("d_addr");
        v_home_addr2 = dbox.getString("d_addr2");
        v_post1 = dbox.getString("d_post1");
        v_post2 = dbox.getString("d_post2");
    }
   
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript" type="text/javascript">


function updateAction() {
    if(document.form1.p_compnm.value == ""){
       alert("회사명을 입력하세요!");
       return;
    }
    
    var p_compresno = document.form1.p_compresno1.value + document.form1.p_compresno2.value + document.form1.p_compresno3.value;
    if(p_compresno!="" && !rnocheck(p_compresno)){
            alert("정확한 사업자등록번호를 입력하세요!");
            document.form1.p_compresno1.focus();
            return;
    }

    if((document.form1.p_userid.value != "") || (document.form1.p_name.value != "") || (document.form1.p_pwd.value != "")){

<%
    if (v_userid.equals("")) {
%>
        if(document.form1.p_name.value == ""){
           alert("성명을 입력하세요!");
           document.form1.p_name.value ="";
           document.form1.p_name.focus();
           return;
        }

        if(!useridCheck(document.form1.p_userid.value)){
                alert("정확한 아이디를 입력하세요!");
                document.form1.p_userid.value ="";
                document.form1.p_userid.focus();
                return;
        }

        if(document.form1.p_isidchk.value == "N"){
          alert("담당자ID 중복체크를 해주세요");
          document.form1.p_userid.focus();
          return;
        }

        //if (resnocheck(document.form1.p_resno1.value, document.form1.p_resno2.value) == false) {
        //    document.form1.p_resno1.focus();
        //    return;
        //}
        
<%
    }
%>
        if(!document.form1.p_email.value=="" && !isValidEmail(document.form1.p_email)){
                alert("정확한 이메일을 입력하세요!");
                document.form1.p_email.value ="";
                document.form1.p_email.focus();
                return;
        }

    }

    if (confirm("저장하시겠습니까?")) {
        document.form1.p_compresno.value = document.form1.p_compresno1.value +"-"+ document.form1.p_compresno2.value +"-"+ document.form1.p_compresno3.value;
        document.form1.p_telno.value     = document.form1.p_telno1.value     +"-"+ document.form1.p_telno2.value     +"-"+ document.form1.p_telno3.value;
        document.form1.p_faxno.value     = document.form1.p_faxno1.value     +"-"+ document.form1.p_faxno2.value     +"-"+ document.form1.p_faxno3.value;
        document.form1.p_hometel.value   = document.form1.p_hometel1.value   +"-"+ document.form1.p_hometel2.value   +"-"+ document.form1.p_hometel3.value;
        document.form1.p_handphone.value = document.form1.p_handphone1.value +"-"+ document.form1.p_handphone2.value +"-"+ document.form1.p_handphone3.value;

        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();    
    }else{
        return;
    }

}

function deleteAction() {
    if (confirm("삭제하시겠습니까?")) {
        document.form1.action = "/servlet/controller.system.OutUserAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    else {
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
    open_window("userList","","100","100","417","400","","","","yes","");
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

onload = function() { // 로드시 라디오 박스 체크설정
    var frm = document.form1; 
	if( <%=v_gubun%> == "1" ) {
		frm.p_gubun(0).checked = true;
	} else if(<%=v_gubun%> == "2"){
		frm.p_gubun(1).checked = true;
	} else if(<%=v_gubun%> == "3"){
		frm.p_gubun(2).checked = true;
	}
}

</script>

</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post" >
  <input type = "hidden" name = "p_process"   value = "">
  <input type = "hidden" name = "p_ischk" value = "0">
  <input type = "hidden" name= "p_isoverlap" value= "N">
  <input type = "hidden" name = "p_isidchk"   value= "Y">
  <input type = "hidden" name = "p_postgubun" value= "">
  <input type = "hidden" name = "p_comp"      value = "<%=v_comp%>">

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
          <td class=sub_title>회사정보수정</td>
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
          <td class="table_title" width="17%">*회사명</td>
          <td class="table_02_2" width="83%"><input name="p_gubun" type="radio" value="1" >콘텐츠제작업체 &nbsp;&nbsp;&nbsp;&nbsp; <input name="p_gubun" type="radio" value="2" >채용업체&nbsp;&nbsp;&nbsp;&nbsp; <input name="p_gubun" type="radio" value="3" >교육그룹
        </tr>
        <tr> 
          <td class="table_title" width="17%">*회사명</td>
          <td class="table_02_2" width="83%"><input name="p_compnm" type="text" class="input" style="width:150" value="<%=v_compnm%>"></td>
        </tr>
        <tr>
          <td class="table_title">사업자등록번호</td>
          <td class="table_02_2"><input name="p_compresno1" type="text" class="input" style="width:100" value="<%=v_compresno1%>" maxlength="3">- 
            <input name="p_compresno2" type="text" class="input" style="width:70" value="<%=v_compresno2%>" maxlength="2">- 
            <input name="p_compresno3" type="text" class="input" style="width:100" value="<%=v_compresno3%>" maxlength="5">
            <input name="p_compresno" type="hidden" value="">
          </td>
        </tr>
        <tr> 
          <td class="table_title">대표이름</td>
          <td class="table_02_2"><input name="p_coname" type="text" class="input" style="width:150" value="<%=v_coname%>"></td>
        </tr>
        <tr> 
          <td class="table_title">대표전화번호</td>
          <td class="table_02_2">
            <input name="p_telno1" type="text" class="input" style="width:70" value="<%=v_telno1%>" maxlength="3" onblur="numeric_chk(this)"> - 
            <input name="p_telno2" type="text" class="input" style="width:70" value="<%=v_telno2%>" maxlength="4" onblur="numeric_chk(this)"> - 
            <input name="p_telno3" type="text" class="input" style="width:70" value="<%=v_telno3%>" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_telno"  type="hidden" value="">
          </td>
        </tr>
        <tr> 
          <td class="table_title">팩스번호</td>
          <td class="table_02_2">
            <input name="p_faxno1" type="text" class="input" style="width:70" value="<%=v_faxno1%>" maxlength="3" onblur="numeric_chk(this)"> - 
            <input name="p_faxno2" type="text" class="input" style="width:70" value="<%=v_faxno2%>" maxlength="4" onblur="numeric_chk(this)"> - 
            <input name="p_faxno3" type="text" class="input" style="width:70" value="<%=v_faxno3%>" maxlength="4" onblur="numeric_chk(this)">
            <input name="p_faxno" type="hidden" value="">
          </td>
        </tr>
        <tr>
          <td class="table_title">우편번호</td>
          <td class="table_02_2">
            <input type="text" class="input" name="p_comp_post1" size="5" maxlength="3" value="<%=v_zip1%>" readonly> - <input type="text" class="input" name="p_comp_post2" size="5" maxlength="3" value="<%=v_zip2%>" readonly>
            &nbsp;<a href="javascript:searchPost(2)"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
        </tr>
        <tr> 
          <td class="table_title">사업장주소</td>
          <td class="table_02_2"><input name="p_comp_addr1" type="text" class="input" style="width:350" value="<%=v_comp_addr1%>">&nbsp;&nbsp;<input name="p_comp_addr2" type="text" class="input" style="width:350" value="<%=v_comp_addr2%>"></td>
        </tr>
        <tr> 
          <td class="table_title">홈페이지</td>
          <td class="table_02_2">http://<input name="p_homepage" type="text" class="input" style="width:280" maxlength="100" value="<%= v_homepage %>"> &nbsp;&nbsp;(예:www.gameacademy.or.kr)</td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>담당자 정보등록</td>
        </tr>
        <tr> 
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
          <td class="table_02_2" width="35%"><table><tr><td><input name="p_userid" type="text" class="input" style="width:150" value="<%=v_userid%>" maxlength="20" onkeypress="javascript:changechk('id')" <%if (!v_userid.equals("")){%>readOnly<%}%>></td><td><a href="javascript:CheckUserid()"><img src="/images/admin/button/btn_addcheck.gif" border="0"></a></td></tr></table></td>
          <td class="table_title" width="15%">*성명</td>
          <td class="table_02_2" width="33%"><input name="p_name" type="text" class="input" style="width:120" maxlength="10"  value=<%=v_name%> <%if (!v_userid.equals("")){%>readOnly<%}%>></td>
        </tr>
        <tr> 
          <td class="table_title">*비밀번호</td>
          <td class="table_02_2" colspan=3><input name="p_pwd" type="password" class="input" style="width:150" maxlength="20" value="<%=v_pwd%>"></td>
        </tr>
        <tr> 
          <td class="table_title">E-mail</td>
          <td class="table_02_2" colspan=3><input name="p_email" type="text" class="input" style="width:150" maxlength="50" value="<%=v_email%>"></td>
        </tr>
        <tr> 
          <td class="table_title">자택전화</td>
          <td class="table_02_2"> 
            <input name="p_hometel1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)" value="<%=v_hometel1%>"> -
            <input name="p_hometel2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)" value="<%=v_hometel2%>"> -
            <input name="p_hometel3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)" value="<%=v_hometel3%>">
            <input name="p_hometel"  type="hidden" value="">
          </td>
          <td class="table_title">핸드폰</td>
          <td class="table_02_2">
            <input name="p_handphone1" type="text" class="input" style="width:70" maxlength="3" onblur="numeric_chk(this)" value="<%=v_handphone1%>"> -
            <input name="p_handphone2" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)" value="<%=v_handphone2%>"> -
            <input name="p_handphone3" type="text" class="input" style="width:70" maxlength="4" onblur="numeric_chk(this)" value="<%=v_handphone3%>">
            <input name="p_handphone" type="hidden" value="">
          </td>
        </tr> 
        <tr>
          <td class="table_title">우편번호</td>
          <td class="table_02_2" colspan="3">
            <input type="text" class="input" name="p_home_post1" size="5" maxlength="3" onblur="numeric_chk(this)" value="<%=v_post1%>" readonly> - <input type="text" class="input" name="p_home_post2"  size="5" maxlength="3" onblur="numeric_chk(this)" value="<%=v_post2%>" readonly>
            &nbsp;<a href="javascript:searchPost(1)"><img src="/images/admin/button/btn_address.gif" border="0"></a></td>
        </tr>
        <tr> 
          <td class="table_title">자택주소</td>
          <td class="table_02_2"  colspan="3"><input name="p_home_addr1" type="text" class="input" style="width:350" maxlength="50" value="<%=v_home_addr1%>">&nbsp;&nbsp;<input name="p_home_addr2" type="text" class="input" style="width:350" maxlength="50" value="<%=v_home_addr2%>"></td>
        </tr>
      </table>
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="right"><a href="javascript:updateAction()" onfocus=this.blur()><img src="/images/admin/button/btn_save.gif"  border="0"></a></td>
          <td width="8"></td>
          <td width="32" align="right"><a href="javascript:deleteAction()" onfocus=this.blur()><img src="/images/admin/button/btn_del.gif"  border="0"></a></td>
          <td width="10"></td>
          <td width="32" align="right"><a href="javascript:history.go(-1);" onfocus=this.blur()><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form> 
<iframe name="chk_frame" width=500 height=0 frameborder=1 src="#"></iframe>
</body>
</html>
