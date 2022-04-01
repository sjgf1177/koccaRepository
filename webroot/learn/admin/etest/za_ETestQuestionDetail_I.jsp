<%
/**
 * file name : za_ExamQuestionDetail_I.jsp
 * date      : 2003/09/01
 * programmer:
 * function  : 평가 문항관리 - 등록페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String v_seltext = "";
    String v_isanswer = "";

    String  ss_subj    = box.getString("s_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    int     v_etestnum = box.getInt("p_etestnum");    
    String v_reloadlist = box.getString("p_reloadlist");

%>
<html>
<head>
<title>평가문항관리-디테일</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_exam.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function InsertQuestion() {
  ActionPage('QuestionInsert');
}
function UpdateQuestion() {
  ActionPage('QuestionUpdate');
}
function DeleteQuestion() {
  if (confirm("평가문제를 삭제 하시겠습니까?")) {
    ActionPage('QuestionDelete');
  }
}
function ActionPage(p_process) {
  if (p_process == 'QuestionDelete') {
    document.form2.action = "/servlet/controller.etest.ETestQuestionServlet";
  }
  else {
    if(!chkData()) {
      return;
    }
    document.form2.action = "/servlet/controller.etest.ETestQuestionServlet";
  }
  document.form2.p_process.value = p_process;
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}
function chkData() {
  if (blankCheck(document.form2.p_text.value)) {
    document.form2.p_text.focus();
    alert('문제를 입력하십시요.');
    return false;
  }
  var ss_etesttype = document.form2.s_etesttype.value;
  var v_seltextcnt = 0;
  var v_test      = '';
  var v_isanswer = false;
  var i=1;
  for (i=1; i<=9; i++) {
    v_test = eval('document.form2.p_seltext'+i+'.value');
    if (!blankCheck(v_test)) {
      v_seltextcnt++;
    }
  }
  
  /* 객관식/다답식 문제 */
  if (ss_etesttype == '1' || ss_etesttype == '3') {  
    if (v_seltextcnt < 2) {
      document.form2.p_seltext1.focus();
      alert('문제 보기를 2개이상 입력하십시요.');
      return false;
    }
  }
  else {
    if (v_seltextcnt == 0) {
      document.form2.p_seltext1.focus();
      alert('정답을 입력하십시요.');
      return false;
    }
  }

  var v_answercnt = 0;
  for (k=1; k<=9; k++) {
    v_isanswer = eval('document.form2.p_isanswer'+k+'.checked');
    if (v_isanswer) {
      v_test = eval('document.form2.p_seltext'+k+'.value');
      if (blankCheck(v_test) && (ss_etesttype=='1'||ss_etesttype=='3')) {
        document.form2.p_seltext1.focus();
        alert('정답을 선택한 보기가 없습니다.');
        return false;
      }
      v_answercnt++;
    }
  }

  /* 객관식 문제 */
  if (ss_etesttype == '1') {
    if (v_answercnt == 0) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 선택해 주십시요.');
      return false;
    } else if (v_answercnt > 1) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 1개만 선택해 주십시요.');
      return false;
    }
  }
  
  /* 다답식 문제 */
  if (ss_etesttype == '3') {  
    if (v_answercnt == 0) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 선택해 주십시요.');
      return false;
    } else if (v_answercnt == 1) {
      document.form2.p_seltext1.focus();
      alert('정답여부를 2개 이상 선택해 주십시요.');
      return false;
    }
  }
  
  for(i=0;i<document.form2.length;i++) {
    if (document.form2.elements[i].type=="checkbox") {
      if (document.form2.elements[i].checked==true) {
        document.form2.elements[i].value = 'Y';
      } else {
        document.form2.elements[i].value = 'N';
      }
    }
  }    
  return true;
}
function ReloadPage(p_action) {
  document.form2.p_process.value = 'QuestionDetailPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
function openExamText() {
  if (document.form2.s_subj.value == '') {
    alert('과정코드를 선택하세요');
    return;
  }
  window.self.name = "winExamTextList";
  window.open("", "openExamText", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=617, height=426, top=0, left=0");
  document.form2.target = "openExamText";
  document.form2.action = "/servlet/controller.etest.ETestServlet";
  document.form2.p_process.value = "QuestionTextListPage";
  document.form2.submit();
}
function receiveText(addseq) {
  document.form2.p_addseq.value = addseq;
}
<% if (v_reloadlist.equals("true")) { %>
parent.list.ReloadPage('go');
<% } %>
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
    <br>
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right">
            <a href="javascript:InsertQuestion()"><img src="/images/admin/etest/record1_butt.gif" width="37" height="18" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <!----------------- 평가문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
	<form name="form2" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="s_subj"       value="<%=ss_subj%>">
        <input type="hidden" name="p_subjnm"       value="<%=ss_subj%>">
        <input type="hidden" name="p_etestnum"    value="<%=String.valueOf(v_etestnum)%>">
        <input type="hidden" name="p_reloadlist" value="">

        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="15%" class="table_title_01">난이도</td>
          <td width="30%" class="table_02_2">
            <%= ETestBean.getCodenm(false, false, "s_levels", ETestBean.ETEST_LEVEL)%><% //-- getCodenm(isChange, isALL, selectbox name, gubun code) ---%>
          </td>
          <td width="15%" class="table_title_01">문제분류</td>
          <td width="30%" class="table_02_2">
            <%= ETestBean.getCodenm(false, false, "s_etesttype", ETestBean.ETEST_TYPE)%>
          </td>
        </tr>

<input name="p_addseq" type="hidden">
<!--
        <tr>
          <td width="15%" class="table_title_01">지문선택</td>
          <td width="30%" class="table_02_2">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="167">
                  <input name="p_addseq" type="text" class="input" size="25" value="" readonly>
                </td>
                <td><a href="javascript:openExamText()"><img src="/images/admin/etest/search2_butt.gif" width="58" height="18" border="0"></a></td>
              </tr>
            </table>
          </td>
          <td width="15%" class="table_title_01"></td>
          <td width="30%" class="table_02_2"></td>
        </tr>
-->
        <tr>
          <td colspan="4" class="table_02_1">
            <table cellspacing="0" cellpadding="0" class="s_table" width="550">
              <tr>
                <td bgcolor="#FFFFFF" align="center">
                  <table cellspacing="1" cellpadding="1" class="s_table">
                    <tr>
                      <td rowspan="9" width="50%" class="table_02_1">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                          <tr>
                            <td>
                              <textarea name="p_text" cols="70" rows="7" class="input"></textarea>
                            </td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                          <tr>
                            <td>
                              <textarea name="p_exptext" cols="70" rows="7" class="input"></textarea>
                            </td>
                          </tr>
                        </table>
                      </td>

<%  
    for (int k=1; k <= 9; k++) {
        %>
                    
                      <td class="table_02_1"><%=String.valueOf(k)%></td>
                      <td class="table_02_1">
                        <input name="p_seltext<%=k%>" type="text" class="input" size="50" value=``>
                      </td>
                      <td class="table_02_1">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                          <tr>
                            <td width="21%">
                              <input type="checkbox" name="p_isanswer<%=k%>" value=">" >
                            </td>
                            <td>정답여부</td>
                          </tr>
                        </table>
                      </td>
                    </tr><tr>
<%
    }     
    %>                    
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title_01">이미지파일</td>
          <td colspan="3" class="table_02_2">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_image" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title_01">오디오파일</td>
          <td colspan="3" class="table_02_2">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_audio" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title_01">동영상파일</td>
          <td colspan="3" class="table_02_2">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td width="100%">
                  <input name="p_motion" type="FILE" class="input" size="40">&nbsp;
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </form>
      </table>
      <!----------------- 평가 문항관리-등록 끝 ------------
----->
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
