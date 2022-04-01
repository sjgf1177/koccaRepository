<%
//**********************************************************
//  1. 제      목: 주제어 등록
//  2. 프로그램명 : ku_InsertTheme.jsp
//  3. 개      요: 주제어 등록창 
//  4. 환      경: JDK 1.4
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
	ArrayList list		= null;
	String v_process = box.getString("p_process");

	int v_subjectseq	= 0;
	String v_grcode		= "";
	String v_subject	= "";
	String v_tyear		= "";	
	String v_tmonth		= "";
	String v_tmpmonth	= "";

	String v_insert = box.getStringDefault("p_insert","N");
	String v_isOk   = box.getString("isOk");

	String v_title = box.getString("p_title");

%>

<script language="javascript">

// 달 체크 
function check(year, month){
	document.title_form.action="/servlet/controller.homepage.OpenForumUserServlet";
	document.title_form.p_process.value = "checkTheme";	
	document.title_form.p_tmonth.value   = month;
	document.title_form.p_tyear.value    = year;
	document.title_form.submit();
}

//등록
function insert(month){
	if (blankCheck(document.title_form.p_title.value)) {
		alert("제목을 입력하세요");
		document.title_form.p_title.focus();
		return;
	}

	if (realsize(document.title_form.p_title.value) > 20) {
		alert("제목은 한글기준 200자를 초과하지 못합니다.");
		document.title_form.p_title.focus();
		return;
	}
	document.title_form.action = "/servlet/controller.homepage.OpenForumUserServlet";
    document.title_form.p_process.value = "insertTheme";
	document.title_form.p_tmpmonth.value = month ;
    document.title_form.submit();
}


function windowsclose(){
	self.close();
}


//입력 내용의 공백을 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}
//글자수를 체크
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


</script>



<html><head><title>주제어 등록</title></head>
<body onLoad="document.title_form.p_title.focus();">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">


<form method="post" action="ku_InsertTheme.jsp" name="title_form">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_tmonth">
	<input type = "hidden" name = "p_tmpmonth">
	<input type = "hidden" name = "p_tyear">



<table width="300" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr> 
    <td width="300"><img src="/images/user/kocca/openkocca/pop_tit_j.gif" width="375"></td>
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
          <td align="center" valign="top" bgcolor="#FFFFFF"> 
		    <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center"><font color="#663399"><strong>[주제]</strong>는 
                  10자 이하로 입력해 주십시오.</font></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td align="center"><font color="#000000">이달의 주제를 입력해주십시오.</font></td>
              </tr>
              <tr> 
                <td height="5" align="center"></td>
              </tr>
              <tr> 
                <td align="center"><table width="350" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/openkocca/box_02.gif">
                    <tr> 
                      <td><img src="/images/user/kocca/openkocca/box_01.gif" width="350" height="10"></td>
                    </tr>
                    <tr>
<%			
	Calendar cal 	= Calendar.getInstance();
	int year		= cal.get(Calendar.YEAR);
		 v_tyear	= String.valueOf(year);
	int month 		= cal.get(Calendar.MONTH)+2;
	if(month <10)
		v_tmonth 	= "0"+String.valueOf(month);
	else
		v_tmonth 	= String.valueOf(month);

%>						
                      <td align="left">&nbsp;&nbsp;<strong>[ 2006 ]</strong>년<strong> [ 02 ]</strong>월</td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td align="center"> <font color="#663399">주제어&nbsp;&nbsp;</font><input type="text" name="p_title" class=type1 style=width:175 value="<%=v_title%>"> 
                        <a href="javascript:check('<%=v_tyear%>','<%=v_tmonth%>')">
                        <img src="/images/user/kocca/button/b_j.gif" align="absmiddle"></a> 
                      </td>
                    </tr>
                    <tr> 
                      <td align="center"><img src="/images/user/kocca/openkocca/box_03.gif" width="350" height="10"></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
              </tr>
	<%

		if (v_process.equals("checkTheme")){

				OpenForumUserBean bean = new OpenForumUserBean();
				list = bean.SelectTheme(box); 
			    
			if( list.size() == 1 ){
				if( v_isOk.equals("0") ){
				for( int i = 0 ; i < list.size() ; i++ ){
					DataBox dbox = (DataBox)list.get(i);	
					v_grcode	 = dbox.getString("d_grcode");
					v_subjectseq = dbox.getInt("d_subjectseq");
					v_subject	 = dbox.getString("d_subject");
					v_tyear		 = dbox.getString("d_tyear");
					String v_month		 = dbox.getString("d_tmonth");
	%>
              <tr> 
                <td height="1" bgcolor="#663399"></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
			<tr> 
                <td align="center"><strong>------ 이달의 주제어 ------</strong></td>
              </tr>
            </table>
            <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
			  <tr> 
                <td colspan="2" align="center"><strong>[ 
                  <%=v_month%> ] 월</strong>의 주제어는 <font color="#663399"><strong>[ 
                  <%=v_subject%> ]</strong></font> 입니다</td>
              </tr>
              <tr> 
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
			  <tr> 
                <td colspan="2" align="center"><font color="#663399"><strong>[ 
                  <%=box.getString("p_title")%> ]</strong></font>는 <strong>[ 
                  <%=v_tmonth%> ] 월</strong> 주제어로 사용가능합니다.</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:insert(<%=v_tmonth%>)"><img src="/images/user/kocca/button/btn_input.gif"></a></td>
              </tr>	
<%			}
		}else{
			%>
			 <tr> 
                <td colspan="2" align="center"><font color="#663399"><strong>[ 
                  <%=box.getString("p_tmpmonth")%> ] 월</strong> 주제어는 이미 등록되어 있습니다.</td>
              </tr>
			  <tr> 
                <td colspan="2" align="right"><a href="javascript:windowsclose()"><img src="/images/user/kocca/button/btn_close.gif"></a></td>
              </tr>	
<%	}	}	}
	
%>

<% if( v_process.equals("insertThemePage") ) {
				
		OpenForumUserBean bean = new OpenForumUserBean();
		list = bean.SelectTheme(box); 
		
	if( list.size() == 1 ){
		if( v_isOk.equals("1") ){
		for( int i = 0 ; i < list.size() ; i++ ){
			DataBox dbox = (DataBox)list.get(i);	
			v_grcode	 = dbox.getString("d_grcode");
			v_subjectseq = dbox.getInt("d_subjectseq");
			v_subject	 = dbox.getString("d_subject");
			v_tyear		 = dbox.getString("d_tyear");
			String v_month		 = dbox.getString("d_tmonth");
			%>
			  <tr> 
                <td colspan="2" align="center"><font color="#663399"><strong>[ 
                 <%=v_subject%> ]</strong></font> <strong>[ 
                  <%=v_month%> ] 월</strong> 주제어로 등록되었습니다.</td>
              </tr>
			  <tr> 
                <td colspan="2" align="right"><a href="javascript:windowsclose()"><img src="/images/user/kocca/button/btn_close.gif"></a></td>
              </tr>	
<%
		}}}}%>
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
</body></html>

