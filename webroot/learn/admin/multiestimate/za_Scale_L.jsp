<%
/**
 * file name : za_Scale_L.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 척도 문제관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    int v_classdepth = conf.getInt("subj.class.depth");

    String  v_process      = box.getString("p_process");
    String  v_grcode    = box.getString("p_grcode");        //교육주관
	String  v_scaletype  = box.getStringDefault("p_scaletype",ScaleBean.DEFAULT_TYPE);

    String  s_gadmin = box.getSession("gadmin");
  %>
<html>
<head>
<title>척도관리-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function detailSelect(p_scalecode) {
        document.form2.target = "openWinScale";
		document.form2.action = "/servlet/controller.multiestimate.ScaleServlet";
		document.form2.p_process.value = "ScaleUpdatePage";
		document.form2.p_scalecode.value = p_scalecode;
		document.form2.submit();
}
function insert() {

        document.form2.target = "openWinScale";
		document.form2.action = "/servlet/controller.multiestimate.ScaleServlet";
		document.form2.p_process.value = "ScaleInsertPage";
		document.form2.p_scalecode.value = 0;
		document.form2.submit();

}
function ReloadPage(p_action) {
  document.form2.p_process.value = 'QuestionListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.multiestimate.ScaleServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
    <input type="hidden" name="p_scaletype"  value="<%=v_scaletype%>">
    <input type="hidden" name="p_scalecode"  value="">

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/pop_scale.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
      <br>

      <table cellspacing="0" cellpadding="0" class="table_out">
        <tr>
          <td bgcolor="#636563">
            <table cellspacing="1" cellpadding="0" class="s_table">
              <tr>
                <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                  <!----------------- 추가버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
					<tr>
                      <td align="right"><a href="javascript:insert()" class="c"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
                    </tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->

                  <table cellspacing="1" cellpadding="5" class="table_out">
                    <tr>
                      <td colspan="4" class="table_top_line"></td>
                    </tr>

                    <tr>
                      <td width="5%" class="table_title">NO</td>
                      <td class="table_title">척도</td>
                      <td class="table_title">척도분류</td>
                    </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("ScaleList");
    DataBox dbox = null;
    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); 
        String s_gubunnm ="";   
		if(dbox.getString("d_s_gubun").equals("5")){
		     s_gubunnm = "5점척도";
		} else if(dbox.getString("d_s_gubun").equals("7")){
		     s_gubunnm = "7점척도";
		}
		%>
		            <tr>
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_scalecode"))%></td>
                      <td class="table_02_1"><a href="javascript:detailSelect('<%=String.valueOf(dbox.getInt("d_scalecode"))%>','go')" class="e"><%=dbox.getString("d_scalename")%></a></td>
                      <td class="table_02_1"><%=s_gubunnm%></td>
                    </tr>
<%  } %>

                  </table>
                  <!----------------- 공통다면평가 끝 ----------------->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>

</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
