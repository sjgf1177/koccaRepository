<%
//**********************************************************
//  1. 제      목: 업체등록관리(베타테스트시스템)
//  2. 프로그램명 : za_ContentCP_L.jsp
//  3. 개      요: 업체등록관리 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 26
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.beta.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox")
    String  v_process     = box.getString("p_process");
    String ss_gadmin      = box.getStringDefault("s_gadmin","ALL");       //gadmin

    String v_gadmin    = "", v_gadminnm  = "",  v_comments  = "", v_padmin    = "";
    String v_userid= "", v_usernm= "" , v_compnm="", v_email="", v_tel="", v_seq="";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<title>::: 현대기아자동차 통합교육관리시스템:::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function insert() {
        document.form1.action = "/servlet/controller.beta.ContentCPServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    function view(seq) {
        document.form1.action = "/servlet/controller.beta.ContentCPServlet";
        document.form1.p_seq.value  = seq;
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function search() {
        document.form1.action = "/servlet/controller.beta.ContentCPServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    
//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_seq" value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/b_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

        <!----------------- 권한 리스트 시작 ----------------->
        <table height="40" cellpadding="0"  cellspacing="1"  class="form_table_out">
	<tr valign="middle">
            <td align="right" style="padding-left=10;padding-right=10" bgcolor="#F1FCFF"> 
              <a href='javascript:search()'><img src="/images/admin/system/search1_butt.gif" border=0></a>
              <a href='javascript:insert()'><img src="/images/admin/system/record1_butt.gif" border=0></a>
	</td>
          </tr>
        </table>
        <br>
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="10%" height="25" class="table_title"><b>NO</b></td>
            <td width="30%" class="table_title"><b>업체명</b></td>
            <td width="10%" class="table_title"><b>담당자</b></td>
            <td width="30%" class="table_title"><b>담당자 e-mail</b></td>
            <td width="20%" class="table_title"><b>담당자 Tel</b></td>
          </tr>
<%
            for(int i=0 ; i<list.size() ; i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_seq       = dbox.getString("d_betaseq");
                v_compnm    = dbox.getString("d_betacpnm");
                v_userid    = dbox.getString("d_userid");
                v_usernm    = dbox.getString("d_usernm");
                v_email     = dbox.getString("d_email");
                v_tel       = dbox.getString("d_tel");
                
%>
          <tr>
            <td class="table_02_1"><%=i+1%></td>
            <td class="table_02_1"><a href="javascript:view('<%=v_seq%>');"><%=v_compnm%></a></td>
            <td class="table_02_1"><%=v_usernm%></td>
            <td class="table_02_1"><%=v_email%></td>
            <td class="table_02_1"><%=v_tel%></td>
          </tr>
<%
            }
%>
        </table>
        <!----------------- 권한 리스트 끝 ----------------->
       <br>

      </td>
  </tr>
  <tr>
  	<td>
  		<table>
  			<tr>
  				<td></td>
  			</tr>
  		</table>
  	</td>	
  </tr>	
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
