<%
/**
 * file name : za_DamunQuestionPool_I.jsp
 * date      : 2003/09/25
 * programmer:
 * function  : 다면평가 Pool에서 등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    String  v_subj = box.getString("p_subj");
    String  v_grcode = box.getString("p_grcode");
 
    String  ss_searchtype = box.getString("s_searchtype");
    String  ss_searchtext = box.getString("s_searchtext");

	String v_reloadlist = box.getString("p_reloadlist");    
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// Enter키
function keypress(event){
	if(event.keyCode=="13") MemberSearch('go');
	return;
}


function MemberSearch(p_action) {
//  if (p_action == 'go') {
//    if (chkData() == false) {
//      return; 
//    }
//  }
  document.form2.p_process.value = 'QuestionPoolListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
function chkData() {
  if (blankCheck(document.form2.s_searchtext.value)) {
    alert('검색어를 입력하십시요.');
    document.form2.s_searchtext.focus();
    return false;
  }
}
function InsertQuestion() {
  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('문제를 선택하세요');
    return;
  }

  document.form2.p_process.value = 'DamunQuestionInsertPool';
  document.form2.p_reloadlist.value = 'true';
  document.form2.p_action.value  = 'insert';
  document.form2.submit();
}
function chkSelected() {
  var selectedcnt = 0;
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        if (document.form2.p_checks[i].checked == true) {
	      selectedcnt++;
	    }  
      }
    } else {
      if (document.form2.p_checks.checked == true) {
	    selectedcnt++;
      }
    }
  }
  return selectedcnt; 
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

	    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  	    <!----------------- title 끝 ----------------->
	    <br>

      <!----------------- 분류 시작 ----------------->
      <table width="98%" cellpadding="0" cellspacing="0" class="table1">
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjQuestionServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"       value="<%=v_grcode%>">
        <input type="hidden" name="p_reloadlist" value="">
        <tr> 
          <td width="90">  &nbsp;&nbsp; 
            <select name="s_searchtype" class="input">
              <option value="1" <% if (ss_searchtype.equals("1")) { %>selected<% } %>>과정명</option>
              <option value="2" <% if (ss_searchtype.equals("2")) { %>selected<% } %>>문제</option>
            </select>
          </td>
          <td width="138"> 
            <input name="s_searchtext" type="text" class="input" value="<%=ss_searchtext%>" onKeyDown="javascript:keypress(event);">
          </td>
          <td valign="middle"><a href="javascript:MemberSearch('go')" class="c"><img src="/images/admin/button/btn_search02.gif" border="0"></a></td>
		</tr>
        <tr> 
          <td height="7"></td>
        </tr>
      </table>
      <!----------------- 분류 끝 ----------------->
      <!----------------- 개인별 등록 시작 ----------------->
      <table width="98%" cellspacing="1" cellpadding="5" class="open_table_out" border=0>
        <tr> 
          <td colspan="5" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">N0.</td>
          <td class="table_title">과정명</td>
          <td class="table_title" width='300'>문제</td>
          <td class="table_title">문제분류</td>
          <td class="table_title">등록</td>
        </tr>
<%  
         ArrayList list = (ArrayList)request.getAttribute("DamunQuestionPool");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); 
              
%>
        <tr>
          <td class="table_01"><%=i+1%></td>
          <td class="table_02_1"><%=dbox.getString("d_subjnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_damuntext")%></td>
          <td class="table_02_1"><%=dbox.getString("d_damuntypenm")%></td>
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_subj")%>|<%=dbox.getString("d_damunnum")%>">
          </td>
        </tr>
<%  
      }  %>
      </form>   
      </table>
      <!----------------- 개인별 등록 끝 ----------------->
	  <br><br>
      <!----------------- 등록, 닫기 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" >
        <tr>
		  <td align="left">&nbsp;</td>
		  <td align="right" >
		  <a href="javascript:InsertQuestion()"><img src="/images/admin/button/btn_apply.gif" border="0"></a>
		  <a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></td>
		</tr>
      </table>
      <!----------------- 등록, 닫기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
