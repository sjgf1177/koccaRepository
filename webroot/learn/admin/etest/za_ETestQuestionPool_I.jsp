<%
/**
 * file name : za_ETestQuestionPool_I.jsp
 * date      : 2003/09/25
 * programmer:
 * function  : 평가 Pool 등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    String  v_etestsubj = box.getString("p_etestsubj");
 
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
// 검색
function MemberSearch(p_action) {
  if (p_action == 'go') {
    if (chkData() == false) {
      return; 
    }
  }
  document.form2.p_process.value = 'QuestionPoolListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}

// 검색어 유무체크
function chkData() {
  if (blankCheck(document.form2.s_searchtext.value)) {
    alert('검색어를 입력하십시요.');
    document.form2.s_searchtext.focus();
    return false;
  }
}

// 문제내용보기
function previewQuestion(p_etestsubj, p_etestnum, p_etesttype) {
  document.form2.p_process.value = 'ETestQuestionPreview';
  document.form2.p_etestsubj.value  = p_etestsubj;
  document.form2.p_etestnum.value  = p_etestnum;
  document.form2.p_etesttype.value  = p_etesttype;
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}

// 문제선택등록
function InsertQuestion() {
  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('문제를 선택하세요');
    return;
  }

  document.form2.p_process.value = 'ETestQuestionInsertPool';
  document.form2.p_reloadlist.value = 'true';
  document.form2.p_action.value  = 'insert';
  document.form2.submit();
}

// 체크박스 선택 
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

// Enter키
function keypress(event){
	if(event.keyCode=="13") MemberSearch('go');
	return;
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
          <td><img src="/images/admin/etest/test_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	     <!----------------- title 끝 ----------------->
	    <br>
      <!----------------- 분류 시작 ----------------->
      <table width="98%" cellpadding="0" cellspacing="0" class="table1">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestQuestionServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_etestsubj"      value="<%=v_etestsubj%>">
        <input type="hidden" name="p_etestnum"       value="">
        <input type="hidden" name="p_etesttype"      value="">
        <input type="hidden" name="p_reloadlist" value="">
        <tr> 
          <td width="15">&nbsp;</td>
          <td width="85">
            <select name="s_searchtype" class="input">
              <option value="1" <% if (ss_searchtype.equals("1")) { %>selected<% } %>>그룹명</option>
              <option value="2" <% if (ss_searchtype.equals("2")) { %>selected<% } %>>문제분류</option>              
              <option value="3" <% if (ss_searchtype.equals("3")) { %>selected<% } %>>문제</option>
              <option value="4" <% if (ss_searchtype.equals("4")) { %>selected<% } %>>난이도</option>
            </select>
          </td>
          <td width="138"> 
            <input name="s_searchtext" type="text" class="input" value="<%=ss_searchtext%>"  onKeyDown="javascript:keypress(event);">
          </td>
          <td valign="middle"><a href="javascript:MemberSearch('go')" class="c"><img src="/images/admin/button/btn_search02.gif" border="0"></a></td>
		</tr>
        <tr> 
          <td height="7"></td>
        </tr>
      </table>
      <!----------------- 분류 끝 ----------------->
      <!----------------- 개인별 등록 시작 ----------------->
      <table width="97%" cellpadding="5" cellspacing="1" class="open_table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">N0.</td>
          <td class="table_title">그룹명</td>
          <td class="table_title">문제분류</td>          
          <td class="table_title">문제</td>
          <td class="table_title">난이도</td>
          <td class="table_title">등록</td>
        </tr>
<%  
         ArrayList list = (ArrayList)request.getAttribute("ETestQuestionPool");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); 
                String s_etestsubj = dbox.getString("d_etestsubj");
				int s_etestnum = dbox.getInt("d_etestnum");
				String s_etesttype = dbox.getString("d_etesttype");
%>
        <tr>
          <td class="table_02_1"><%=i+1%>번</td>
          <td class="table_02_1"><%=dbox.getString("d_etestsubjnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_etestgubunnm")%></td>          
          <td class="table_02_1"><a href="javascript:previewQuestion('<%=s_etestsubj%>','<%=s_etestnum%>','<%=s_etesttype%>')" class='b'><%=dbox.getString("d_etesttext")%></a></td>
          <td class="table_02_1"><%=dbox.getString("d_levelsnm")%></td>
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_etestsubj")%>|<%=dbox.getString("d_etestnum")%>">
          </td>
        </tr>
<%  
      }  %>
      </form>   
      </table>
      <!----------------- 개인별 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 닫기 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" align=center>
        <tr> 
		  <td><a href="javascript:InsertQuestion()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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
