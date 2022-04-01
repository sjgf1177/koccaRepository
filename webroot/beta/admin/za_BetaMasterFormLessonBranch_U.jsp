<%
/**
 * file name : za_MasterFormLessonBranch_U.jsp
 * date      : 2003/08/18
 * programmer: LeeSuMin
 * function  : 마스터폼 Lesson's Branch 수정화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj      = box.getString("p_subj");
    String  c_gadmin    = box.getSession("s_gadmin");
    if(c_gadmin==null || c_gadmin.equals(""))   c_gadmin="A1";

    String  p_process   = box.getString("p_process");
    String  p_lesson    = box.getString("p_lesson");

    MasterFormData  da  =(MasterFormData)request.getAttribute("MasterFormData");
    String  v_subjnm    = da.getSubjnm();

    ArrayList       list1 = (ArrayList)request.getAttribute("MfLessonBranchData");
    MfBranchData    data = null;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language="javascript">
function whenSubmit(){
    document.f2.submit();
}
</script>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<table width="95%" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7" align=center>
  <tr>
    <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <FORM name = "f2" method = "post" action="/servlet/controller.beta.BetaMasterFormServlet">
            <input type='hidden' name='p_process'   value="updateLessonBranch">
            <input type='hidden' name='p_action'    value="">
            <input type='hidden' name='p_subj'      value="<%=p_subj%>">
            <input type='hidden' name='p_lesson'    value="<%=p_lesson%>">
          <tr>
            <td align="center" bgcolor="#FFFFFF">


            <br>
            <!----------------- title 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="bottom">&nbsp;<img src="/images/admin/contents/icon1.gif" width="12" height="7" border="0">
                    <b><font color="#107AAD" face="굴림" size="2.5"><%=v_subjnm%> - <%=p_lesson%> (<%=GetCodenm.get_lessonnm(p_subj,p_lesson)%>)</font></b></td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
            <!----------------- title 끝 ----------------->

            <!----------------- 분기 시작 ----------------->
            <table class="table_out cellspacing="1" cellpadding="5">
              <tr>
                <td colspan="3" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="15%" height="25" class="table_title"><b>분기</b></td>
                <td class="table_title"><b>분기 차시명</b></td>
                <td class="table_title"><b>시작URL</b></td>
              </tr>
<%          for (int i=0; i<list1.size(); i++) {
                data  = (MfBranchData)list1.get(i);
%>
              <tr>
                <td align="center" bgcolor="#EEEEEE" height="25"><%=data.getBranch()%></td>
                <td class="table_02_1">
                  <input name="p_branch" type="hidden" value="<%=data.getBranch()%>">
                  <input name="p_sdesc" type="text" class="input" value="<%=data.getSdesc()%>" size="20" maxlength=50>
                </td>
                <td class="table_02_1">
                  <input name="p_starting" type="text" class="input" value="<%=data.getStarting()%>" size="80" maxlength=150>
                </td>
              </tr>

<%          }  %>
            </table>
            <!----------------- 분기 현황 끝 ----------------->

            <!----------------- 저장, 닫기 버튼 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td align="right" style="padding-top=10"><a href="javascript:whenSubmit()"><img src="/images/admin/contents/save1_butt.gif" width="37" height="18" border="0"></a></td>
                <td align="right" style="padding-top=10" width="44"><a href="javascript:window.close()"><img src="/images/admin/contents/close1_butt.gif" width="37" height="18" border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장, 닫기 버튼 끝 ----------------->
            <br>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
