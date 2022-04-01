<%
//**********************************************************
//  1. 제      목: 강사배점관리
//  2. 프로그램명: za_TutorDistribution_L.jsp
//  3. 개      요: 강사배점관리 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    //DEFINED class&variable END

    DataBox dbox = (DataBox)request.getAttribute("measure");

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
    document.form1.action = "/servlet/controller.tutor.TutorDistributionAdminServlet";
    document.form1.p_process.value= "updatePage";
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
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class=sub_title><img src="/images/admin/common/icon.gif">강사 배점관리
          </td>
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
            <a href="javascript:whenUpdate()"><img src="/images/admin/tutor/modify1_butt.gif" border="0"></a>
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
            <td class="table_title" width="20%">기본급</td>
            <td class="table_02_1" colspan="5" width="80%"><%=dbox.getString("va_basepay")%>원</td>    
          </tr>
          <tr> 
            <td class="table_title" rowspan="3">세금적용비율</td>
            <td class="table_02_1" colspan="5">내부 강사 : <%=dbox.getString("va_tax1")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_1" colspan="5">전문 강사 : <%=dbox.getString("va_tax3")%>%</td>
          </tr>
          <tr> 
            <td class="table_02_1" colspan="5">외부 강사 : <%=dbox.getString("va_tax2")%>%</td>
          </tr>
          <tr> 
            <td class="table_title" rowspan="2">강사 만족도</td>
            <td class="table_title" width="16%"><%=dbox.getString("c_okrate1")%>점 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_okrate2")%>점 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_okrate3")%>점 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_okrate4")%>점 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_okrate5")%>점 미만</td>    
          </tr>
          <tr>
            <td class="table_02_1" width="16%"><%=dbox.getString("va_okrate1")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_okrate2")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_okrate3")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_okrate4")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_okrate5")%></td>    
          </tr>
          <tr> 
            <td class="table_title" rowspan="2">참여도</td>
            <td class="table_title" width="16%"><%=dbox.getString("c_actrate1")%>회 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_actrate2")%>회 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_actrate3")%>회 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_actrate4")%>회 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_actrate5")%>회 미만</td>    
          </tr>
          <tr> 
            <td class="table_02_1" width="16%"><%=dbox.getString("va_actrate1")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_actrate2")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_actrate3")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_actrate4")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_actrate5")%></td>    
          </tr>
          <tr> 
            <td class="table_title" rowspan="2">운영자평가</td>
            <td class="table_title" width="16%"><%=dbox.getString("c_manrate1")%></td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_manrate2")%></td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_manrate3")%></td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_manrate4")%></td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_manrate5")%></td>    
          </tr>
          <tr> 
            <td class="table_02_1" width="16%"><%=dbox.getString("va_manrate1")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_manrate2")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_manrate3")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_manrate4")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_manrate5")%></td>    
          </tr>
          <tr> 
            <td class="table_title" rowspan="2">수료인원</td>
            <td class="table_title" width="16%"><%=dbox.getString("c_grcnt1")%>명 이상 </td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_grcnt2")%>명 이상 </td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_grcnt3")%>명 이상 </td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_grcnt4")%>명 이상</td>    
            <td class="table_title" width="16%"><%=dbox.getString("c_grcnt5")%>명 미만</td>    
          </tr>
          <tr> 
            <td class="table_02_1" width="16%"><%=dbox.getString("va_grcnt1")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_grcnt2")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_grcnt3")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_grcnt4")%></td>    
            <td class="table_02_1" width="16%"><%=dbox.getString("va_grcnt5")%></td>    
          </tr>
      </table>      

     <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</form>
</body>
</html>
