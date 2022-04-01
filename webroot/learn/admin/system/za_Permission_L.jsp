<%
//**********************************************************
//  1. 제      목: 권한관리
//  2. 프로그램명 : za_Permission_L.jsp
//  3. 개      요: 권한관리 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process     = box.getString("p_process");
    String ss_gadmin      = box.getStringDefault("s_gadmin","ALL");       //gadmin

    String v_gadmin    = "";
    String v_gadminnm  = "";
    String v_comments  = "";
    String v_padmin    = "";
    int    v_gadminseq = 0;

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	// 등록
    function insert() {
        document.form1.action = "/servlet/controller.system.PermissionServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
	// 상세보기
    function view(padmin, gadmin) {
        document.form1.action = "/servlet/controller.system.PermissionServlet";
        document.form1.p_padmin.value  = padmin;
        document.form1.p_gadmin.value  = gadmin;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_gadmin"  value = "">
    <input type = "hidden" name=  "p_padmin"  value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

        <!----------------- 권한 리스트 시작 ----------------->
        <table height="40" cellpadding="0"  cellspacing="1"  class="form_table_out">
			<tr valign="middle">
            <td align="right" style="padding-left=10;padding-right=10" class="form_table_bg"> 
              <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border=0></a>
			</td>
          </tr>
        </table>
        <br>
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="10%" height="25" class="table_title"><b>권한코드</b></td>
            <td width="20%" class="table_title"><b>권한명</b></td>
            <td width="70%" class="table_title"><b>권한사용용도</b></td>
            <!--td width="10%" class="table_title"><b>권한순서</b></td-->
          </tr>
<%

            for(int i = 0; i < list.size(); i++) {

                PermissionData data  = (PermissionData)list.get(i);

                v_gadmin    = data.getGadmin();
                v_gadminnm  = data.getGadminnm();
                v_comments  = data.getComments();
                v_padmin	= data.getPadmin();
                v_gadminseq = data.getGadminseq();

%>

          <tr>
            <td class="table_02_1"><%=v_gadmin%></td>
            <td class="table_02_1"><a href="javascript:view('<%=v_padmin%>','<%=v_gadmin%>')"><%=v_gadminnm%></a></td>
            <td class="table_02_1"><%=v_comments%></td>
            <!--td class="table_02_1"><%=v_gadminseq%></td-->
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
