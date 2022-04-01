<%
//**********************************************************
//  1. 제      목: ID 중복검색창
//  2. 프로그램명 : zu_IdCheck.jsp
//  3. 개      요: ID 중복검색창 
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String v_userid = box.getString("p_userid");
	String v_check  = (String)request.getAttribute("check");
	int isOk		= box.getInt("isOk");

	String checkId = "";
	String result  = "";
	String v_process = "CheckId";
	box.put("p_process", v_process);

%>

<script language="javascript">

function check(){
	
	var userid = document.IDCheck_Form.p_userid.value;

		if(userid == ""){
			alert("아이디를 입력해주세요..");
			document.IDCheck_Form.p_userid.focus();
			return;
		}else{
			document.IDCheck_Form.action="/servlet/controller.homepage.MainMemberJoinServlet?p_userid="+userid+"&p_process=CheckId"
			document.IDCheck_Form.submit();
		}
}

function windowclose(userid){
	opener.document.form1.p_chk.value= "Y";
	opener.document.form1.p_userid.value= userid;
	self.close();
}

/* 패스워드 엔터키 체크 */
function password_enter(e) {
    if (e.keyCode =='13'){  check();  }
}
</script>


<html><head><title>아이디 검색</title></head>
<body onLoad="document.IDCheck_Form.p_userid.focus();">
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<%if( box.getSession("tem_grcode").equals("N000001")) { %>
<link href="/css/portal/homepage_renewal/popup.css" rel="stylesheet" type="text/css" />
<%}else{ %>
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<%} %>
	<form method="post" action="ID_Check.jsp" name="IDCheck_Form">
	
	<%if( box.getSession("tem_grcode").equals("N000001")) { %>
	
	<!-- 팝업사이즈 396x314 -->
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="popup">
  <tr>
    <td height="61" id="title"><img src="/images/portal/homepage_renewal/member/tit_pop_01.jpg" alt="id검색" /></td>
  </tr>
  <tr>
    <td height="76" align="center" bgcolor="#FFFFFF"><span class="blue_txt">[아이디]</span><span class="table_text">는 6자 이상 12자 이하로 입력해 주십시오.<br />
    원하는 아이디를 입력해 주십시오.</span><br /></td>    
  </tr>
  <%
			String userid;
			userid = request.getParameter("p_userid");

			if(isOk == 0){
				checkId = box.getString("p_userid");
			}
		%>
  <tr>
    <td align="center" bgcolor="#FFFFFF"><table width="350" border="0" cellpadding="0" cellspacing="1" bgcolor="ADBBD8">
      <tr>
        <td height="34" bgcolor="F0F0F0"><table border="0" align="center" cellpadding="3" cellspacing="0">
            <tr>
              <td><input type="text" name="p_userid" value="<%=checkId%>" maxlength="12" class=type1 style="width:174px"  onkeypress="password_enter(event)"/></td>
              <td><img src="/images/portal/homepage_renewal/member/btn_pop_search.gif" alt="조회" onclick="javascript:check();"/></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <%
		if(userid != null){
			
		
			checkId = box.getString("p_userid");
			if(isOk == 0){
		%>
  <tr>
    <td height="76" align="center" bgcolor="#FFFFFF"><span class="table_text">----- 아이디 중복검색 결과 -----<br />
    <span class="blue_txt">[<%=checkId%>]</span>는 사용할 수 있습니다.</span></td>
  </tr>
  <tr>
    <td height="60" align="center">
    	<img src="/images/portal/homepage_renewal/member/btn_popuse.gif" alt="사용"  onclick="javascript:windowclose('<%=checkId%>')"/></td>
  </tr>
  <%}else{ %>
  	<tr>
    <td height="76" align="center" bgcolor="#FFFFFF"><span class="table_text">----- 아이디 중복검색 결과 -----<br />
    <span class="blue_txt">[<%=checkId%>]</span>는 이미 사용하고 있습니다.</span></td>
  </tr>
  <%} }%>
</table>
 	
 	<%}else{ %>
	 
	  <table width="300" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr> 
    <td width="300"><img src="/images/user/game/member/pop_tit_idsearch.gif" width="375"></td>
  </tr>
  <tr> 
    <td><table width="300" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif"> 
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr> 
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF"> <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td align="center"><font color="7E573A"><strong>[아이디]</strong>는 
                  6자 이상 12자 이하로 입력해 주십시오.</font></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td align="center"><font color="#000000">원하는 아이디를 입력해주십시오.</font></td>
              </tr>
              <tr> 
                <td height="5" align="center"></td>
              </tr>
              <tr> 
                <td align="center"><table width="350" border="0" cellpadding="0" cellspacing="0" background="/images/user/game/member/ba_bg.gif">
		<%
			String userid;
			userid = request.getParameter("p_userid");

			if(isOk == 0){
				checkId = box.getString("p_userid");
			}
		%>
                    <tr> 
                      <td width="15"><img src="/images/user/game/member/ba_f.gif"></td>
                      <td width="319" align="center"><input type="text" name="p_userid" value="<%=checkId%>" maxlength="12" class=type1 style=width:175>  
                        <a href="javascript:check()"><img src="/images/user/game/button/b_j.gif" align="absmiddle"></a> 
                      </td>
                      <td width="16" align="right"><img src="/images/user/game/member/ba_tail.gif"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td height="1" bgcolor="#663333"></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
	<%
		if(userid != null){ 
	%>
              <tr> 
                <td align="center"><strong>------ 아이디 중복검색 결과 ------</strong></td>
              </tr>
            </table>
            <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
		<%
			checkId = box.getString("p_userid");
			if(isOk == 0){
		%>
              <tr> 
                <td colspan="2" align="center"><font color="#CC6600"><strong>[ 
                  <%=checkId%> ]</strong></font>는 사용할 수 있습니다.</td>
              </tr>
              <tr> 
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:windowclose('<%=checkId%>')"><img src="/images/user/game/button/btn_use.gif"></a></td>
              </tr>
	<%		
			}else{ 
	%>
			  <tr> 
                <td colspan="2" align="center"><font color="#CC6600"><strong>[ 
                  <%=checkId%> ]</strong></font>는 이미 사용하고 있습니다.</td>
              </tr>
		      <tr> 
                <td colspan="2" align="right" height="40">&nbsp;</td>
              </tr>
	<%		}
		}
	%>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr> 
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>

<%} %>
  	</form>
</body></html>

				<script language="JavaScript">
				<!--
					document.IDCheck_Form.p_userid.value="";
					document.IDCheck_Form.p_userid.focus();
				//-->
				</script>
