<%
//**********************************************************
//  1. 제      목: TUTOR SELECT
//  2. 프로그램명: za_Tutor_R.jsp
//  3. 개      요: 강사 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");    

    DataBox dbox = (DataBox)request.getAttribute("inuserinfo");
    
    String v_inusercompnm = box.getString("p_inusercompnm");

    
    
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="500" border="0" cellspacing="0" cellpadding="0" height="250">
  <tr>
    <td align="center" valign="top"> 
<!-------------소제목 시작-------------------------->
<br>
 		<table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            
          <td class=sub_title>입력자 정보</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> <br>
      <!--------------소제목 끝---------------------------->
      <br>
	 
	 <!----------------- 강사등록 시작 ----------------->
        
      <table width="44%" cellpadding="5" cellspacing="1" class="table_out">
        <tr> 
          <td class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="25%" class="table_title"><b>교육주관</b></td>
          <td width="75%" height="16" class="table_02_2"><%=v_inusercompnm%></td>
        </tr>
        <tr> 
          <td class="table_title"><b>사 번</b></td>
          <td height="16" class="table_02_2"><%=dbox.getString("d_inuserid")%></td>
        </tr>
        <tr> 
          <td class="table_title"><b>성 명</b></td>
          <td class="table_02_2"><%=dbox.getString("d_inusername")%></td>
        </tr>
        <tr> 
          <td class="table_title"><b>직 위</b></td>
          <td class="table_02_2"><%=dbox.getString("d_inuserjikwinm")%></td>
        </tr>
        <tr> 
          <td class="table_title"><b>날 짜 </b></td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd hh:mm:ss")%></td>
        </tr>
      </table>
	  <br>
       <!----------------- 수정, 삭제, 취소 버튼 시작----------------->
		
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td>
            <a href="javascript:self.close()"><img src="/images/admin/system/close1_butt.gif"  border="0"></a>
            </td>
			
          </tr>
        </table>
		
        <!----------------- 수정, 삭제, 취소 버튼 끝----------------->
        <br>
        <br>

      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>

</body>
</html>
