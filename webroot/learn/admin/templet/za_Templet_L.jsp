<%
//**********************************************************
//  1. 제      목: 템플릿 관리화면
//  2. 프로그램명: za_Templet_L.jsp
//  3. 개      요: 템플릿 관리화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 06. 21
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.templet.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_process= box.getString("p_process");
    String v_grcode    = "";
    String v_grcodenm  =  "";
    String v_type      = "";
    String v_type_view = "";

    ArrayList  list = (ArrayList)request.getAttribute("EduGroupList");

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

//관리수정화면으로 이동
function whenMove(process, grcode, grcodenm){
    document.form1.p_grcode.value = grcode;
    document.form1.p_grcodenm.value = grcodenm;
    document.form1.p_process.value = process;
    document.form1.action="/servlet/controller.templet.TempletServlet"
    document.form1.submit();
}


function whenGrtype(){
    document.form1.p_process.value = "listPage";
    document.form1.action="/servlet/controller.templet.TempletServlet"
    document.form1.submit();
}
// 메뉴관리 (팝업)
function menuPopup() {
//    window.self.name = "TempletMain";
    open_window("menuPopup","","30","30","1050","600",'','','','yes','','','');
    document.form1.target = "menuPopup";
    document.form1.action='/servlet/controller.course.TempletMenuAdminServlet';
    document.form1.p_process.value = 'select';
    document.form1.submit();

//    document.form1.target = window.self.name;
}

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_grcode"   value="">
    <input type="hidden" name="p_grcodenm"   value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/course/c_title01_1.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
     <!----------------- title 끝 ----------------->
      <br>
      <br>
      <!----------------- 교육그룹 시작 ----------------->
<%/* if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("A")) { %>
      <table width="97%" cellspacing="1" cellpadding="5" >
        <tr> 
          <td colspan="8" align="left">
          <%=CodeConfigBean.getCodeGubunSelect("0060","",1,"p_grtype",box.getString("p_grtype"),"onChange=\"whenGrtype()\"",3) %>
          </td>
        </tr>
      </table>
<% } else  {                                                   %>
        <%//=CodeConfigBean.getCodeName("grtype",box.getSession("grtype")) %>
        <input type="hidden" name="p_grtype" value="<%=box.getSession("grtype")%>">
 <% }                                                          */ %>
      <table width="97%" cellspacing="1" cellpadding="5" >
        <tr> 
          <td colspan="8" align="right">
			<a href="javascript:menuPopup();"><img src="/images/admin/portal/s.2_01.gif" alt="사용자 메뉴관리" style="border=0"/></a><br></br>
          </td>
        </tr>
      </table>
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="3%" rowspan="2" class="table_title"><b>NO</b></td>
          <td width="23%" rowspan="2" class="table_title"><b>교육그룹</b></td>
          <td width="12%" rowspan="2" class="table_title"><b>Template Type</b></td>
          <td width="10%" rowspan="2" class="table_title"><b>기본정보</b></td>
          <td height="25" colspan="2" class="table_title"><b>콘텐츠/메뉴 관리</b></td>
          <td width="14%" rowspan="2" class="table_title"><b>Template 보기</b></td>
        </tr>
        <tr> 
          <td width="10%" height="25" class="table_title"><b>Main</b></td>
          <td width="10%" class="table_title"><b>Sub</b></td>
        </tr>
<%



    for (int i=0; i<list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);

        v_grcode = dbox.getString("d_grcode");
        v_grcodenm = dbox.getString("d_grcodenm");
        v_type = dbox.getString("d_type");
        if (!v_type.equals("")) v_type_view = "Type " + v_type;
        else v_type_view = "";
%>
        <tr> 
          <td align="center" bgcolor="#EEEEEE" height="25"><%=(i+1)%></td>
          <td class="table_02_1" align="center"><%=v_grcodenm%></td>
          <td class="table_02_1" align="center"><%=v_type_view%> </td>
          <td align="center" class="table_02_1"><a href="javascript:whenMove('updateManagerPage', '<%=v_grcode%>', '<%=v_grcodenm%>')">관리</a><br> </td>
          <td align="center" class="table_02_1"><%if (!v_type.equals("")) {%><a href="javascript:whenMove('updateMainPage', '<%=v_grcode%>')">수정</a><%}%></td>
          <td align="center" class="table_02_1"><%if (!v_type.equals("")) {%><a href="javascript:whenMove('updateSubPage', '<%=v_grcode%>')">수정</a><%}%></td>
          <td align="center" class="table_02_1"><%if (!v_type.equals("")) {%><a href="/servlet/controller.homepage.MainServlet?tem_grcode=<%=v_grcode%>&tem_type=<%=v_type%>" target="new">보기</a><%}%></td>
        </tr>
<%  }    %>

      </table>
        <!----------------- 교육그룹 끝 ----------------->
        <br>
    </td>
  </tr>
</table>

</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
