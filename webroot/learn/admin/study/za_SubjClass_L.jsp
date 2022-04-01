<%
//**********************************************************
//  1. 제      목: 과정별 클래스 조회
//  2. 프로그램명 : za_SubjClass_L.jsp
//  3. 개      요: 과정별 클래스 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq01");
    String  v_class     = box.getStringDefault("p_class", "ALL");
    Hashtable  subjinfo = (Hashtable)request.getAttribute("SubjectInfo");
%>
<html>
<head>
<title>과정별 클래스 조회</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function whenSelection(p_action) {
  document.form1.p_process.value = 'subjClasslistPage';
  document.form1.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="690" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" bgcolor="#FFFFFF">
          <br>
            <table cellspacing="0" cellpadding="3" class="form_table_out">
              <tr>
                <td bgcolor="#C6C6C6" align="center">
                  <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                    <tr>
                      <td height="3"></td>
                    </tr>
                    <tr>
                      <td align="center" valign="middle">
                        <table cellspacing="0" cellpadding="0" width="99%" class=""form_table">
                          <tr>
                            <td width="13%" height="25">◈<b> 교육그룹</b></td>
                            <td width="35%">: <%=(String)subjinfo.get("p_grcodenm")%></td>
                            <td width="13%">◈<b> 년도</b></td>
                            <td>: <%=(String)subjinfo.get("p_gyear")%>년</td>
                          </tr>
                          <tr>
                            <!--td height="25">◈<b> 교육차수</b></td>
                            <td >: <%=(String)subjinfo.get("p_grseqnm")%></td-->
                            <td height="25">◈<b> 분류</b></td>
                            <td>: <%=(String)subjinfo.get("p_classname")%></td>
                            <td height="25">◈<b> 과정</b></td>
                            <td>: <%=(String)subjinfo.get("p_subjnm")%></td>
                          </tr>
                          <tr>
                            <td>◈<b> 차수</b></td>
                            <td>: <%=(String)subjinfo.get("p_subjseq")%>차</td>
                            <td height="25"></td>
                            <td></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
            <br>
            <!----------------- 클래스명 시작 ----------------->
            <table  cellpadding="0" cellspacing="0" class="table1">
            <form name="form1" method="post" action="/servlet/controller.study.ClassServlet">
              <input type="hidden" name="p_process" value="">
              <input type="hidden" name="p_subj"    value="<%=v_subj%>">
              <input type="hidden" name="p_year"    value="<%=v_year%>">
              <input type="hidden" name="p_subjseq01" value="<%=v_subjseq%>">
              <tr>
                <td align="right">
                  클래스명 <% SelectParam param = new SelectParam("p_class", "whenSelection('change')", true, 16, box.getHttpSession()); %><%=SelectionUtil.getSubjClass(v_subj, v_year, v_subjseq, v_class, param)%>
                </td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </form>
            </table>
            <!----------------- 클래스명 끝 ----------------->
            <!----------------- 과정 시작 ----------------->
            <table cellspacing="1" cellpadding="5" class="table_out">
              <tr>
                <td colspan="10" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="5%"class="table_title"><b>NO</b></td>
                <td class="table_title"><b>회사명</b></td>
                <td class="table_title"><b>ID</b></td>
                <td class="table_title"><b>성명</b></td>
                <td class="table_title"><b>클래스명</b></td>
                <td class="table_title"><b>주강사</b></td>
                <td class="table_title"><b>부강사</b></td>
              </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("SelectMemberList");
    ClassMemberData data = null;
    for (int i=0; i<list.size(); i++) {
        data = (ClassMemberData)list.get(i); %>
              <tr>
                <td class="table_01"><%=String.valueOf(i+1)%></td>
                <td class="table_02_1"><%=data.getComptext()%></td>
                <td class="table_02_1"><%=data.getUserid()%></td>
                <td class="table_02_1"><%=data.getName()%></td>
                <td class="table_02_1"><%=data.getClassnm()%></td>
                <td class="table_02_1"><%=data.getMtutor()%></td>
                <td class="table_02_1"><%=data.getStutor()%></td>
              </tr>
<%  } %>
            </table>
            <!----------------- 과정 끝 ----------------->
            <!----------------- 닫기 버튼 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table>
            <!----------------- 닫기 버튼 끝 ----------------->
            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
