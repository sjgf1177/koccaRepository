<%
/**
 * file name : za_SulmunSubjPaperPool_I.jsp
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
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
    
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
//	String  v_subjcourse = box.getString("s_subjcourse");out.println("v_subjcourse" + v_subjcourse);
    String  v_subj = box.getString("p_subj");//out.println("v_subj" + v_subj);
    String  v_grcode       = box.getStringDefault("p_grcode", box.getString("s_grcode"));
	String  v_gyear       = box.getStringDefault("p_gyear", box.getString("s_gyear"));
	String  v_subjseq    = box.getString("p_subjseq");
	String  v_subjseqgr   = StringManager.cutZero(box.getString("p_subjseqgr"));
	int     v_sulpapernum  = box.getInt("p_sulpapernum");//out.println("v_sulpapernum" + v_sulpapernum);

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
function MemberSearch(p_action) {
//  if (p_action == 'go') {
//    if (chkData() == false) {
//      return; 
//    }
//  }
  document.form2.p_process.value = 'PaperPoolListPage';
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
    alert('설문지를 선택하세요');
    return;
  }

  document.form2.p_process.value = 'SulmunPaperInsertPool';
  document.form2.p_reloadlist.value = 'true';
  document.form2.p_action.value  = 'go';
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
//  opener.ReloadPage('go');
  return selectedcnt; 
}
function PreviewPaper(p_grcode, p_subj, p_sulpapernum, p_sulpapernm) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openWinQuestion";
  document.form3.p_process.value = "SulmunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_sulpapernum.value= p_sulpapernum; 
  document.form3.p_sulpapernm.value= p_sulpapernm;
  document.form3.submit();
 
  farwindow.window.focus();
  document.form2.target = window.self.name;
}

function press_enter(e) { 
	if (e.keyCode =='13'){  MemberSearch('go');  }
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

	    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  	    <!----------------- title 끝 ----------------->
	    <br>
		        <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>Pool등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->

      <!----------------- 분류 시작 ----------------->
      <table width="98%" cellpadding="0" cellspacing="0" class="table1">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunSubjPaperServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"       value="<%=v_grcode%>">
		<input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
		<input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>">
	    <input type="hidden" name="p_subjseqgr"      value="<%=v_subjseqgr%>">
        <input type="hidden" name="p_sulpapernum" value="">
        <input type="hidden" name="p_reloadlist" value="">
        <tr> 
          <td width="85"> 
            <select name="s_searchtype" class="input">
              <option value="1" <% if (ss_searchtype.equals("1")) { %>selected<% } %>>과정명</option>
              <option value="2" <% if (ss_searchtype.equals("2")) { %>selected<% } %>>설문지명</option>
            </select>
          </td>
          <td width="138"> 
            <input name="s_searchtext" type="text" class="input" value="<%=ss_searchtext%>" onkeypress="press_enter(event)">
          </td>
          <td valign="middle"><a href="javascript:MemberSearch('go')" class="c"><img src="/images/admin/button/btn_search02.gif" border="0"></a></td>
		</tr>
        <tr> 
          <td height="7"></td>
        </tr>
      </table>
      <!----------------- 분류 끝 ----------------->
      <!----------------- 개인별 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="open_table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title">N0.</td>
          <td class="table_title">과정명</td>
          <td class="table_title">설문지명</td>
          <td class="table_title">문제수</td>
          <td class="table_title">기능</td>
          <td class="table_title">등록</td>
        </tr>
<%  
         ArrayList list = (ArrayList)request.getAttribute("SulmunPaperPool");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); 
              
%>
        <tr>
          <td class="table_01"><%=i+1%></td>
          <td class="table_02_1"><%=dbox.getString("d_subjnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_sulpapernm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_totcnt")%></td>
          <td class="table_02_1"><a href="javascript:PreviewPaper('<%=v_grcode%>','<%=dbox.getString("d_subj")%>','<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','<%=dbox.getString("d_sulpapernm")%>')"><img src="/images/admin/button/b_preview.gif" border="0"></a></td>
          <td class="table_02_1">
            <input type="radio" name="p_checks" value="<%=dbox.getString("d_subj")%>|<%=dbox.getString("d_sulpapernum")%>">
          </td>
        </tr>
<%  
      }  %>

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
 </form>   
</table>
<form name="form3" method="post" action="/servlet/controller.research.SulmunSubjPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_sulpapernum" value="">
  <input type="hidden" name="p_sulpapernm" value="">
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
