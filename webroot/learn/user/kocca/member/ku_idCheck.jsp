<%
//**********************************************************
//  1. 제      목: ID 중복검색창
//  2. 프로그램명 : ku_idCheck.jsp
//  3. 개      요: ID 중복검색창 
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    String v_userid = box.getString("p_userid");
    String v_kor_name = box.getString("p_kor_name");
    String v_resno    = box.getString("p_resno");
    String v_check  = (String)request.getAttribute("check");
    //String isOk     = box.getString("isOk");
    int isOk     = box.getInt("isOk");
    
    //System.out.println("ku_idchk box get string isOk = " + isOk);
    //System.out.println(isOk=="0");

	//int x_int = 0;
	//x_int=box.getInt("isOk");
	//System.out.println("ku_idchk box get string isOk int = " + x_int);
	//System.out.println(x_int==0);
	
    
    String checkId = "";
    String result  = "";
    String v_process = "checkId";
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
            document.IDCheck_Form.action="/servlet/controller.homepage.KMemberJoinServlet?p_userid="+userid+"&p_process=checkId"
            document.IDCheck_Form.submit();
        }
}

function windowclose(userid){
    opener.document.mform1.p_chk.value= "Y";
    opener.document.mform1.p_userid.value= userid;
    self.close();
}
</script>


<html><head><title>아이디 검색</title></head>
<body onLoad="document.IDCheck_Form.p_userid.focus();">
    <form method="post" action="ID_Check.jsp" name="IDCheck_Form">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">

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
                  4자 이상 16자 이하로 입력해 주십시오.</font></td>
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
                      <td width="319" align="center"><input type="text" name="p_userid" value="<%=checkId%>" class=type1 style=width:175> 
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
                  <%=checkId%> ]</strong></font>는 사용할 수 없습니다.</td>
              </tr>
              <tr> 
                <td colspan="2" align="right" height="40">&nbsp;</td>
              </tr>
    <%      }
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

</form>


<script language="JavaScript">
<!--
    document.IDCheck_Form.p_userid.value="";
    document.IDCheck_Form.p_userid.focus();
//-->
</script>

</body></html>