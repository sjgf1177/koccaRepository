<%
/**
 * file name : za_SingleClass_L.jsp
 * date      : 2003/7/29
 * programmer:
 * function  : 단일클래스 구성
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subj    = box.getString("p_subj");
    String  v_year    = box.getString("p_year");
    String  v_subjseq = box.getString("p_subjseq01");
    String  v_class   = box.getString("p_class");
    Hashtable  subjinfo = (Hashtable)request.getAttribute("SubjectInfo");

    String v_subjnm   = (String)subjinfo.get("p_subjnm");
    String v_edustart =  (String)subjinfo.get("p_edustart");
    String v_eduend   =  (String)subjinfo.get("p_eduend");
    String v_edustart_view = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
    String v_eduend_view   = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

    String v_name       = "";
    String v_ttype      = "";
    String v_userid     = "";
    String v_ttype_view = "";
    String v_ischeck    = ""; //2009.10.28 추가, 저장 되어 있는 강사는 체크되어서 나오도록 변경

    ArrayList  list = (ArrayList)request.getAttribute("SubTutorList");
    DataBox dbox = null;
%>
<html>
<head>
<title>부강사지정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function  whenInsert(){
  document.form2.p_process.value = "subTutorInsert";
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="535" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr> 
    <td> 
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr> 
          <td align="center" bgcolor="#FFFFFF"> 
          <br>
            <!----------------- title 시작 ----------------->
            <table cellspacing="0" cellpadding="3" class="form_table_out">
              <tr> 
                <td bgcolor="#C6C6C6" align="center"> 
                  <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                    <tr> 
                      <td height="3"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="middle"> 
                        <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                          <tr> 
                            <td height="25" width="16%">◈<b> 과정코드</b></td>
                            <td width="24%">: <%=v_subj%></td>
                            <td height="20" width="12%">◈<b> 과정명</b></td>
                            <td width="48%">: <%=v_subjnm%></td>
                          </tr>
                          <tr> 
                            <td height="25" width="16%">◈<b> 연도</b></td>
                            <td width="24%">: <%=v_year%></td>
                            <td height="25" width="12%">◈<b> 차수</b></td>
                            <td width="48%">: <%=v_subjseq%></td>
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
            <!----------------- title 끝 ----------------->
            <br>
            <!----------------- 과정 시작 ----------------->           
            <table cellspacing="1" cellpadding="5" class="table_out">
            <form name="form2" method="post" action="/servlet/controller.study.ClassServlet">
              <input type="hidden" name="p_process"    value="">
              <input type="hidden" name="s_grcode"     value="<%=box.getString("s_grcode")%>">
              <input type="hidden" name="s_gyear"      value="<%=box.getString("s_gyear")%>">
              <input type="hidden" name="s_grseq"      value="<%=box.getString("s_grseq")%>">
              <input type="hidden" name="s_uclass"     value="<%=box.getString("s_uclass")%>">
              <input type="hidden" name="s_subjcourse" value="<%=box.getString("s_subjcourse")%>">
              <input type="hidden" name="s_subjseq"    value="<%=box.getString("s_subjseq")%>">
              <input type="hidden" name="s_comp"       value="<%=box.getString("s_comp")%>">
              <input type="hidden" name="p_subj"       value="<%=v_subj%>">
              <input type="hidden" name="p_year"       value="<%=v_year%>">
              <input type="hidden" name="p_subjseq01"  value="<%=v_subjseq%>">
              <input type="hidden" name="p_class"      value="<%=v_class%>">
                    <input type="hidden" name="p_call_url"   value="<%=box.getString("p_call_url")%>">
              <tr> 
                 <td colspan="5" class="table_top_line"></td>
              </tr>
              <tr> 
                <td width="6%"  class="table_title"><b>NO</b></td>
                <td class="table_title"><b>성명</b></td>
                <td class="table_title"><b>주강사여부</b></td>
                <td class="table_title"><b>지정기간</b></td>
                <td class="table_title" width="4%">&nbsp;</td>
              </tr>
<%

    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); 
        v_name    = dbox.getString("d_name");
        v_ttype   = dbox.getString("d_ttype");
        v_userid  = dbox.getString("d_userid");
        v_ischeck = dbox.getString("d_ischeck");//2009.10.28 쿼리에서는 이부분을 주석처리했음, 따라서 값을 가져오지 않음, 파일로 강사를 등록한 경우에 문제가 발생함

        v_ttype_view = v_ttype.equals("M") ? "Y" : "N";
%>
              <tr> 
                <td class="table_01"><%=String.valueOf(i+1)%></td>
                <td class="table_02_1"><%=v_name%></td>
                <td class="table_02_1"><%=v_ttype_view%> </td>
                <td class="table_02_1"><%=v_edustart_view%>~<%=v_eduend_view%></td>
                <td class="table_02_1">
<%      if (!v_ttype.equals("M")) { %><input type="checkbox" name="p_checks" value="<%=v_userid%>" <%= "Y".equals(v_ischeck) ? " CHECKED" : "" %>><% } %>
                </td>
              </tr>
<%  }  %>
            </form>
            </table>
            <!----------------- 과정 끝 ------------------->
            <!----------------- 저장 버튼 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="right" style="padding-top=10"><a href="javascript:whenInsert()"><img src="/images/admin/student/save1_butt.gif" width="37" height="18" border="0"></a><a href="javascript:window.close()"><img src="/images/admin/course/close1_butt.gif" width="37" height="18" border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장 버튼 끝 ----------------->
            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
