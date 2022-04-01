<%
/**
 * file name : za_MasterFormMenu_L.jsp
 * date      : 2003/08/18
 * programmer: LeeSuMin
 * function  : 마스터폼 메뉴 리스트화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    DataBox dbox = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj      = box.getString("p_subj");
    String  c_gadmin    = box.getSession("s_gadmin");
    if(c_gadmin==null || c_gadmin.equals(""))   c_gadmin="A1";

    BetaMasterFormData  da  =(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
    String  v_subjnm        = da.getSubjnm();

    ArrayList           list1 = (ArrayList)request.getAttribute("BetaMasterFormMfMenu");
    BetaMfMenuData  data = null;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function insert() {
    if(blankCheck(document.form1.p_file.value)){
      alert("업로드할 파일을 선택해 주세요");
      return;
    }
    if (document.form1.p_file.value.length > 0 ){
      var data = document.form1.p_file.value;
      data = data.toUpperCase(data);
      if (data.indexOf(".ZIP") < 0) {
        alert("파일종류는 ZIP 파일만 가능합니다.");
        return;
      }
    }

    document.form1.action = "/servlet/controller.beta.BetaMasterFormServlet";
    document.form1.p_process.value = "menuImageInsert";
    document.form1.submit();
}
//-->
</SCRIPT>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="" enctype="multipart/form-data">
   <input type="hidden" name="p_process" value="">
   <input type="hidden" name="p_subj"    value="<%=p_subj%>">
   
<table width="95%" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7" align=center>
  <tr>
    <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>
            <td align="center" bgcolor="#FFFFFF">


            <br>
            <!----------------- title 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="bottom">&nbsp;<img src="/images/admin/contents/icon1.gif" width="12" height="7" border="0">
                    <b><font color="#107AAD" face="굴림" size="2.5"><%=v_subjnm%></font></b></td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
            <!----------------- title 끝 ----------------->

            <!----------------- 분기 시작 ----------------->
            <table width="95%" border="0" cellspacing="1" cellpadding="5">
              <tr>
                <td colspan="7" class="table_top_line"></td>
              </tr>
              <tr>
                <td width="10%" height="25" class="table_title"><b>No</b></td>
                <td class="table_title"><b>Menu-Code</b></td>
                <td class="table_title"><b>Menu-Name</b></td>
                <td class="table_title"><b>isRequired</b></td>
                <td class="table_title"><b>Program</b></td>
                <td class="table_title"><b>Parameters</b></td>
                <td class="table_title"><b>Image File</b></td>
              </tr>
<%
            for (int i=0; i<list1.size(); i++) {
                dbox  = (DataBox)list1.get(i);
%>
              <tr>
                <td align="center" bgcolor="#EEEEEE" height="25"><%=(i+1)%></td>
                <td class="table_02_1"><%=dbox.getString("d_menu")%></td>
                <td class="table_02_1"><%=dbox.getString("d_menunm")%></td>
                <td class="table_02_1"><%=dbox.getString("d_isrequired")%></td>
                <td class="table_02_1"><%=dbox.getString("d_pgm")%></td>
                <td class="table_02_1"><%=dbox.getString("d_pgram1")%>,<%=dbox.getString("d_pgram2")%>,<%=dbox.getString("d_pgram3")%>,<%=dbox.getString("d_pgram4")%>,<%=dbox.getString("d_pgram5")%></td>
                <td class="table_02_1">m<%=dbox.getString("d_menu")%>.gif , m<%=dbox.getString("d_menu")%>u.gif</td>
              </tr>

<%          }  %>
            </table>
            <!----------------- 분기 현황 끝 ----------------->
			<br><br>
            <!----------------- 이미지 등록 안내----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td valign="bottom">&nbsp;<img src="/images/admin/contents/icon1.gif" width="12" height="7" border="0">
                    <b><font color="#107AAD" face="굴림" size="2.5">메뉴이미지 등록</font></b> &nbsp; (zip 파일만 가능합니다.)</td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
            <br><br>

            <!----------------- 이미지 등록 안내 끝 ----------------->
            <table width="95%" border="0" cellspacing="1" cellpadding="5">
              <tr>
                <td colspan="3" class="table_top_line"></td>
              </tr>
              <tr>
                <td class="table_title">이미지파일</td>
                <td class="table_title"><input name="p_file" type="FILE" class="input" size="70"></td>
                <td class="table_title"><a href="javascript:insert()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
              </tr>
            </table>
            <br>

            <!----------------- 저장, 닫기 버튼 시작 ----------------->
            <table width="95%" border="0" cellspacing="0" cellpadding="0" align='enter'>
              <tr align=center>
                <td style="padding-top=10"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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
