<%
/**
 * file name : za_ExamQuestionPool_I.jsp
 * date      : 2003/09/25
 * programmer:
 * function  : 평가 Pool 등록
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    String  v_subj = box.getString("p_subj");
 
    int     v_uppageno   = box.getInt("p_uppageno");
    int     v_uppagesize = box.getInt("p_uppagesize");

    //페이지수
    if (v_uppageno == 0)  v_uppageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    //페이지당 레코드수
    if (v_uppagesize == 0)  v_uppagesize = 10;

    String  ss_searchtype = box.getString("s_searchtype");
    String  ss_searchtext = box.getString("s_searchtext");

	String v_reloadlist = box.getString("p_reloadlist");  
	
    int     v_totalpage		 = 0;
    int     v_rowcount		 = 1;
    int     v_totalrowcount	 = 0;
    int     v_rowspan		 = 0;
    int     v_total			 = 0;
    int		v_dispnum		 = 0;

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

// 찾아보기 검색
function MemberSearch(p_action) {
  if (p_action == 'go') {
    if (chkData() == false) {
      return; 
    }
    
  } 

  document.form2.p_process.value = 'QuestionPoolListPage';
  document.form2.p_uppageno.value = 1;
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}

// 검색어 입력체크
function chkData() {
  if (blankCheck(document.form2.s_searchtext.value)) {
    alert('검색어를 입력하십시요.');
    document.form2.s_searchtext.focus();
    return false;
  }
}

// 문제 보기
function previewQuestion(p_subj, p_examnum, p_examtype) {

  document.form2.p_process.value = 'ExamQuestionPreview';
  document.form2.p_subj.value  = p_subj;
  document.form2.p_examnum.value  = p_examnum;
  document.form2.p_examtype.value  = p_examtype;
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}

// 문제선택등록
function InsertQuestion() {
  var chkselected = chkSelected();
  if (chkselected < 1) {
    alert('문제를 선택하세요.');
    return;
  }
   alert('Pool추가를 이용하여 문제를 등록하면 각 문제들이 과정1차시로 기본세팅됩니다.\n\n문제의 차시정보를 수정하려면 문제명을 클릭하여 수정하시기 바랍니다.'); 
  document.form2.p_process.value = 'ExamQuestionInsertPool';
  document.form2.p_reloadlist.value = 'true';
  document.form2.p_action.value  = 'insert';
  document.form2.submit();
}

// 페이지 이동
function go(index) {
     document.form2.target = "_self";
     document.form2.p_uppageno.value = index;
     document.form2.action = '/servlet/controller.exam.ExamQuestionServlet';
     document.form2.p_process.value = "QuestionPoolListPage";
     document.form2.p_action.value  = 'go';
     document.form2.submit();
}

// 페이지 이동
function goPage(pageNum) {
     document.form2.target = "_self";
     document.form2.p_uppageno.value = pageNum;
     document.form2.action = '/servlet/controller.exam.ExamQuestionServlet';
     document.form2.p_process.value = "QuestionPoolListPage";
     document.form2.p_action.value  = 'go';
     document.form2.submit();
}

// 페이지 사이즈 변경
function pagesize(pageSize) {
     document.form2.target = "_self";
     document.form2.p_uppageno.value = 1;
     document.form2.p_uppagesize.value = pageSize;
     document.form2.action = '/servlet/controller.exam.ExamQuestionServlet';
     document.form2.p_process.value = "QuestionPoolListPage";
     document.form2.p_action.value  = 'go';
     document.form2.submit();
}

// 문제선택 여부
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
          <td><img src="/images/admin/exam/e_title01.gif" ></td>
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
      <form name="form2" method="post" action="/servlet/controller.exam.ExamQuestionServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_examnum"       value="">
        <input type="hidden" name="p_examtype"       value="">
        <input type="hidden" name="p_reloadlist" value="">
        <input type="hidden" name="p_uppageno" value="<%=v_uppageno%>">
        <input type="hidden" name="p_uppagesize" value="<%=v_uppagesize%>">
        <tr> 
          <td width="85"> 
            <select name="s_searchtype" class="input">
              <option value="1" <% if (ss_searchtype.equals("1")) { %>selected<% } %>>과정명</option>
              <option value="2" <% if (ss_searchtype.equals("2")) { %>selected<% } %>>문제</option>
              <option value="3" <% if (ss_searchtype.equals("3")) { %>selected<% } %>>난이도</option>
              <option value="4" <% if (ss_searchtype.equals("4")) { %>selected<% } %>>문제분류</option>
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
      <table cellspacing="1" cellpadding="5" class="open_table_out">
        <tr> 
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width='5%' class="table_title">N0.</td>
          <td width='20%'  class="table_title">과정명</td>
          <td class="table_title">문제</td>
          <td width='10%'  class="table_title">난이도</td>
          <td width='10%'  class="table_title">문제분류</td>
          <td width='5%'  class="table_title">등록</td>
        </tr>
<%  
         ArrayList list = (ArrayList)request.getAttribute("ExamQuestionPool");
            for (int i=0; i<list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i); 
                String s_subj = dbox.getString("d_subj");
				int s_examnum = dbox.getInt("d_examnum");
				String s_examtype = dbox.getString("d_examtype");
				
	            //페이지 나누기
				v_totalpage     = dbox.getInt("d_totalpage");
	            v_rowcount      = dbox.getInt("d_rowcount");
	            v_totalrowcount = dbox.getInt("d_totalrowcount");
	            v_dispnum       = dbox.getInt("d_dispnum");
               
%>
        <tr>
          <td class="table_01"><%=v_dispnum%></td>
          <td class="table_02_2"><%=dbox.getString("d_subjnm")%></td>
          <td class="table_02_2"><a href="javascript:previewQuestion('<%=s_subj%>','<%=s_examnum%>','<%=s_examtype%>')"><%=dbox.getString("d_examtext")%></a></td>
          <td class="table_02_1"><%=dbox.getString("d_levelsnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_examtypenm")%></td>
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox.getString("d_subj")%>|<%=dbox.getString("d_examnum")%>">
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
     <!----------------- page navi 시작 ----------------->
     <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
       <tr>
         <td align="right" valign="absmiddle">
           <%= PageUtil.printPageSizeList(v_totalpage, v_uppageno, row, v_uppagesize, v_totalrowcount) %>
         </td>
       </tr>
     </table>
     <!----------------- page navi 끝 ----------------->
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
