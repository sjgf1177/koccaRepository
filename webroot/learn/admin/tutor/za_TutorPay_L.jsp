<%
//**********************************************************
//  1. 제      목: 강사수강료관리
//  2. 프로그램명: za_TutorDistribution_L.jsp
//  3. 개      요: 강사수강료관리 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

	String v_tutorcode		= "";
	String v_tutorcodenm	= "";
	int v_price				= 0 ;
	int v_addprice			= 0;
	int i					= 0;

    //DEFINED class&variable END

    ArrayList list      = null;

	list = (ArrayList)request.getAttribute("payList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--          
function whenUpdate() {
    document.form1.action = "/servlet/controller.tutor.TutorPayAdminServlet";
    document.form1.p_process.value= "update";
    document.form1.submit();
}
-->     
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
<input type="hidden" name="p_process" value="<%=v_process%>">

    
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/tutor/tit_new_03.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 --------------- -->
      <br>
      <br>
      <br>

      <!-----------------  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
            <td align="right" height="20">&nbsp;</td>
            <td align="right" width="65">
            <a href="javascript:whenUpdate()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            </td>            
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>
      <!-----------------  버튼 끝 ----------------->
      <!-----------------  시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr> 
            <td colspan="23" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="3%" class="table_title"><b>NO</b></td>
            <td class="table_title" width="20%">강사구분</td>
            <td class="table_title" width="20%">인당강사료</td>
            <td class="table_title" width="20%">추가금액</td>
          </tr>
<%
	for( i = 0 ; i < list.size() ; i++ ){
		DataBox dbox = (DataBox)list.get(i);		  

		v_tutorcode		= dbox.getString("d_tutorcode");  
		v_tutorcodenm	= dbox.getString("d_tutorcodenm");  
		v_price			= dbox.getInt("d_price");
		v_addprice		= dbox.getInt("d_addprice");
%>

          <tr>
			<input type="hidden" name="p_tutorcode" value="<%=v_tutorcode%>">
            <td class="table_01"><%=i+1%></td>
            <td class="table_02_1"><%=v_tutorcodenm%></td>
            <td class="table_02_1"><input type="text" name="p_price" class="input" value="<%=v_price%>" align="right" onblur="numeric_chk(this);"></td>   
            <td class="table_02_1"><input type="text" name="p_addprice" class="input" value="<%=v_addprice%>" onblur="numeric_chk(this);"></td>    
          </tr>
<%		
	}
%>		
      </table>      

     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</form>
</body>
</html>
