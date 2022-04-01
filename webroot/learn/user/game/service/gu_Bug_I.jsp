<%
//**********************************************************
//  1. 제      목: service > 운영자에게 
//  2. 프로그램명 : gu_HomePageContactUs_I.jsp
//  3. 개      요: 운영자에게 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.10
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int    v_tabseq		= box.getInt("p_tabseq");
    String v_process	= box.getString("p_process");
    int    v_pageno		= box.getInt("p_pageno");
    String v_searchtext	= box.getString("p_searchtext");
    String v_search		= box.getString("p_search");
    int    v_upfilecnt	= box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    String v_indate		= "";
    String s_userid		= box.getSession("userid");
    String s_username	= box.getSession("name");
    String s_email		= box.getSession("email");
	String content		= "";
	String width		= "650";
	String height		= "200";
   // String leftmenu = box.getString("leftmenu");
	
	box.put("leftmenu","06");                       // 메뉴 아이디 세팅
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//입력된 내용 저장
function insert() {
	if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
	}else {
		form1.content.value = document.all.txtDetail.value;
    }
    
    //alert(form1.content.value);

if (blankCheck(document.all.p_title.value)) {
    alert("제목을 입력하세요!");
    document.all.p_title.focus();
    return;
}   
    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }

    document.form1.p_pageno.value = "1";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.action = "/servlet/controller.homepage.HomePageBugBoardServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//내용입력을 취소하고 리스트로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.MainServlet";
     //document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//글자수 체크
function realsize( value ) {
 var len = 0;
 if ( value == null ) return 0;
 for(var i=0;i<value.length;i++){
     var c = escape(value.charAt(i));
     if ( c.length == 1 ) len ++;
     else if ( c.indexOf("%u") != -1 ) len += 2;
     else if ( c.indexOf("%") != -1 ) len += c.length/3;
 }
 return len;
}

//-->
</script>
		  <!-- center start -->
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq %>">
	<input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
	<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
	<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
	<input type = "hidden" name = "p_process"	 value = "">

	<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_bug.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > 버그신고</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td valign="top"><img src="/images/user/game/service/text_bug.gif"></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_ser5"></td>
    <td width="639" height="2" class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" >
  <tr> 
    <td width="81" class="tbl_grc"><img src="/images/user/game/service/text_subject02.gif"></td>
    <td width="7" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td class="font_specialroom_list"><input name="p_title" type="text" class="input" style="width:300px"></td>
  </tr>
  <tr> 
    <td width="81" class="tbl_grc"><img src="/images/user/game/service/text_writer02.gif" height="11"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td bgcolor="F8F7EF" class="tbl_bleft"><%=s_username%></td>
  </tr>
  <tr> 
    <td colspan="3" class="tbl_gleft">
		<!-- DHTML Editor  -->
		<%@ include file="/include/DhtmlEditor.jsp" %>
		<!-- DHTML Editor  --> 
	</td>
  </tr>
    <tr> 
    <td width="81" class="tbl_grc"><img src="/images/user/game/service/text_file.gif"></td>
    <td valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td class="tbl_gleft"><input name="p_file3" type="FILE" class="input" size="60">
      &nbsp;<a href="#"><!--img src="/images/user/game/button/btn_find_long.gif" border="0"--></a></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="1" colspan="2" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom"><a href="javascript:insert()"><img src="/images/user/game/button/btn_confirm.gif" border="0"></a></td>
  </tr>
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
</table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->

