<%
//**********************************************************
//  1. 제      목: 외주업체 리스트화면
//  2. 프로그램명: zu_cpcomp_L.jsp
//  3. 개      요: 외주업체 관리자 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.15
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_cpseq="", v_email = "", v_cpnm = "", v_cpresno = "", v_homesite = "", v_address = "", v_ldate = "", v_userid = "", v_pwd = "", v_name = "", v_comptel = "", v_compgubun= "";
int i = 0,  v_totalpage = 0, v_rowcount = 1;

ArrayList list = (ArrayList)request.getAttribute("selectCompList");
   
//int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");
String s_usernm = box.getSession("username");

String s_gadmin = box.getSession("gadmin");
String v_gadmin = "";

if(!s_gadmin.equals("")){
  v_gadmin = StringManager.substring(s_gadmin, 0, 1);
}

     
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주업체조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MoveLink(act,cpseq) {
    document.form1.target = "_self";
	if(act == "1"){
		//리스트 페이지로
		document.form1.p_process.value   = "";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();	
	}
	else if(act == "2"){
		//상세보기 페이지로
		document.form1.p_process.value   = "select";
		document.form1.p_cpseq.value   	 = cpseq;	
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
	else if(act == "3"){
		//등록페이지로
	    document.form1.p_process.value   = "insertPage";
	    document.form1.action            = "/servlet/controller.cp.CPCompServlet";
	    document.form1.submit();
	}
}

// 레포팅출력
function whenExcel() {
	window.self.name = "CpCompSelectExcel";
	open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
	document.form1.target = "openExcel";
	document.form1.p_process.value = "compSelectExcel";
	document.form1.action='/servlet/controller.cp.CPCompServlet';
	document.form1.submit();
}

//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
<input type = "hidden" name = "p_process" value = "">
<input type = "hidden" name = "p_cpseq" value = "">
</form>
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	  <table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <br>     
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="87%"></td>
          <td align="right"><a href="javascript:whenExcel()" onfocus="this.blur()"><img src="/images/admin/button/btn_excelprint.gif" border="0"  valign="top"></a></td>
          <%if(!v_gadmin.equals("S")&&!v_gadmin.equals("T")&&!v_gadmin.equals("M")){%>
            <td align="right"><a href="JavaScript:MoveLink(3)"><img src="/images/admin/button/btn_register.gif" border="0"></a></td>
          <%}else{%>
            <td align="right">&nbsp;</td>
          <%}%>
        </tr>
        <tr> 
          <td align="right" height=6></td>
        </tr>
      </table>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	  <tr>
        <td colspan="7" class="table_top_line"></td>
      </tr>
      <tr> 
        <td class="table_title" width="8%">NO</td>
        <td class="table_title"  width="30%">업체명</td>
        <td class="table_title"  width="8%">업체형태</td>
        <td class="table_title"  width="12%">담당자</td>
        <td class="table_title"  width="17%">Userid(Password)</td>
        <td class="table_title"  width="15%">담당자 이메일</td>
        <td class="table_title"  width="10%">담당자 전화번호</td>
      </tr>
      <%
      String v_comptype = "";
      for(i = 0; i < list.size(); i++) {
      
        
          DataBox dbox = (DataBox)list.get(i);  
      
      	//sql = "select a.cpseq, a.cpnm, a.cpresno, a.homesite, a.address, a.ldate, b.userid, b.name, b.email, b.comptel ";
      
          //v_cpseq = dbox.getInt("d_cpseq");
          v_cpseq = dbox.getString("d_cpseq");
          v_cpnm   = dbox.getString("d_cpnm");
          v_cpresno   = dbox.getString("d_cpresno");
          v_homesite    = dbox.getString("d_homesite");
          v_address    = dbox.getString("d_address");
          v_ldate      = dbox.getString("d_ldate");
          v_userid  = dbox.getString("d_userid");
          v_pwd = dbox.getString("d_pwd");
          v_name  = dbox.getString("d_name");
          v_email  = dbox.getString("d_email");
          v_comptel   = dbox.getString("d_comptel");
          v_compgubun = dbox.getString("d_compgubun");
          //v_totalpage = dbox.getInt("d_totalpage");
          //v_rowcount = dbox.getInt("d_rowcount");  
          
          if(v_compgubun.equals("S")){
            v_comptype = "CP";
          }else if(v_compgubun.equals("T")){
            v_comptype = "BP";
          }else if(v_compgubun.equals("M")){
            v_comptype = "CP+BP";
          }
          
      //------------------------------------------------------------------------------------------------------
      
      %>
        <tr> 
          <td class="table_02_1"><%=i+1%></td>
          <td class="table_02_1"><a href="JavaScript:MoveLink(2,'<%=v_cpseq%>')"><%=v_cpnm%></a></td>
          <td class="table_02_1"><%=v_comptype%></td>
          <td class="table_02_1"><%=v_name%></td>
          <td class="table_02_1"><%=v_userid%>(<%=v_pwd%>)</td>
          <td class="table_02_1"><%=v_email%></td>
          <td class="table_02_1"><%=v_comptel%></td>
        </tr>
<%
}
%>
      </table>
      
    </td>
  </tr>
  <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %> 
    </td>
  </tr>
</table>

</body>
</html>

