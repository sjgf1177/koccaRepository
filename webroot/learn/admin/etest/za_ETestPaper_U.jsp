<%
/**
 * file name : za_ETestPaper_U.jsp
 * date      : 2003/09/04
 * programmer:
 * function  : 평가문제지 수정
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

    String  v_upperclass = box.getStringDefault("p_upperclass","ALL");
    String  v_subj       = box.getString("p_subj");
    String  v_year       = box.getString("p_year");
    String  v_subjseq    = box.getString("p_subjseq");
    int     v_papernum   = box.getInt("p_papernum");

    DataBox dbox = (DataBox)request.getAttribute("ETestPaperMaster");
    ArrayList list = (ArrayList)request.getAttribute("QuestionList");
    ArrayList selectedList = (ArrayList)request.getAttribute("SelectedQuestionList");
%>
<html>
<head>
<title>평가문제지수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_exam.css" type="text/css">
<script language="JavaScript">
<!--
function ChooseQuestion() {
  var boxLength    = document.form3.s_choosed.length;
  var selectedItem = document.form3.s_questions.selectedIndex;
  var selectedText = (boxLength+1) + '-' + document.form3.s_questions.options[selectedItem].text;
  var selectedValue= document.form3.s_questions.options[selectedItem].value;
  var i;
  var isNew = true;
  if (boxLength != 0) {
    for (i = 0; i < boxLength; i++) {
      thisitem = document.form3.s_choosed.options[i].value;
      if (thisitem == selectedValue) {
        isNew = false;
        break;
      }
    }
  }
  if (isNew) {
    newoption = new Option(selectedText, selectedValue, false, false);
    document.form3.s_choosed.options[boxLength] = newoption;
  }
  document.form3.s_questions.selectedIndex=-1;
}
function RemoveQuestion() {
  var boxLength = document.form3.s_choosed.length;
  arrSelected = new Array();
  var count = 0;
  for (i = 0; i < boxLength; i++) {
    if (document.form3.s_choosed.options[i].selected) {
      arrSelected[count] = document.form3.s_choosed.options[i].value;
    }
    count++;
  }
  var x;
  for (i = 0; i < boxLength; i++) {
    for (x = 0; x < arrSelected.length; x++) {
      if (document.form3.s_choosed.options[i].value == arrSelected[x]) {
        document.form3.s_choosed.options[i] = null;
      }
    }
    boxLength = document.form3.s_choosed.length;
  }
}
function getChoosedQuestion() {
  var strValues = "";
  var boxLength = document.form3.s_choosed.length;
  var count = 0;
  if (boxLength != 0) {
    for (i = 0; i < boxLength; i++) {
      if (count == 0) {
        strValues = document.form3.s_choosed.options[i].value;
      }
      else {
        strValues = strValues + "," + document.form3.s_choosed.options[i].value;
      }
      count++;
    }
  }
  document.form2.p_etestsubjs.value = strValues;
  document.form2.p_cnttotal.value = count;
  return count;
}

function move(index,to) {
  var list = document.form3.s_choosed;
  var total = list.options.length-1;
  if (index == -1) return false;
  if (to == +1 && index == total) return false;
  if (to == -1 && index == 0) return false;
  var items = new Array;
  var values = new Array;

  for (i = total; i >= 0; i--) {
    items[i] = list.options[i].text;
    values[i] = list.options[i].value;
  }

  for (i = total; i >= 0; i--) {
    if (index == i) {
      list.options[i + to] = new Option(items[i],values[i], 0, 1);
      list.options[i] = new Option(items[i + to], values[i + to]);
      i--;
    } else {
      list.options[i] = new Option(items[i], values[i]);
    }
  }
  list.focus();
}
function UpdatePaper() {
  var choosed = getChoosedQuestion();
  if (choosed < 1) {
      alert('선택된 문제가 없습니다.');
      return;
  }
  document.form2.p_process.value = 'ETestPaperUpdate';
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}
function goMasterListPage() {
  document.form2.p_process.value = 'ETestPaperListPage';
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}
function ViewQuestion() {
  var boxLength = document.form3.s_choosed.length;
  for (i = 0; i < boxLength; i++) {
    if (document.form3.s_choosed.options[i].selected) {
      document.form2.p_examnum.value = document.form3.s_choosed.options[i].value;
    }
  }
  window.self.name = "winQuestionData";
  window.open("", "openQuestionData", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=617, height=426, top=0, left=0");
  document.form2.target = "openQuestionData";
  document.form2.action = "/servlet/controller.etest.ETestServlet";
  document.form2.p_process.value = "ETestQuestionDataPage";
  document.form2.submit();
  document.form2.target = "_self";
}
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
          <td><img src="/images/admin/etest/test_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명, 년도, 차수 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="3">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table width="98%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td width="10%" height="25">◈<b> 과정명</b></td>
                      <td width="25%">: <%=box.getString("p_subjnm")%>(<%=v_subj%>)</td>
                      <td width="10%">◈<b> 년도</b></td>
                      <td width="22%">: <%=v_year%></td>
                      <td width="10%">◈<b> 차수</b></td>
                      <td width="22%">: <%=v_subjseq%></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 과정명, 년도, 차수 끝 ----------------->
      <br>
      <br>
      <!----------------- 평가문제지 수정 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestPaperServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="p_upperclass" value="<%=v_upperclass%>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_papernum"   value="<%=String.valueOf(v_papernum)%>">
        <input type="hidden" name="p_etestsubjs"  value="">
        <input type="hidden" name="p_cnttotal"   value="">
        <input type="hidden" name="p_examnum"    value="">

        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title_01">문항수<br>(상/중/하 : 전체)</td>
          <td class="table_title_01">문항수<br>(주관식/객관식)</td>
          <td class="table_title_01">기본점수</td>
        </tr>
        <tr>
          <td class="table_02_1">
            <%=dbox.getInt("d_cntlevel1") + "/" + dbox.getInt("d_cntlevel2") + "/" + dbox.getInt("d_cntlevel3") + " : " + (dbox.getInt("d_cntlevel1")+dbox.getInt("d_cntlevel2")+dbox.getInt("d_cntlevel3"))%></td>
          <td class="table_02_1">
            <%=dbox.getInt("d_cntsubjectetest")+  "/ " + (dbox.getInt("d_cntlevel1")+dbox.getInt("d_cntlevel2")+dbox.getInt("d_cntlevel3")-dbox.getInt("d_cntsubjectetest"))%></td>
          <td class="table_02_1">
            <input name="p_grbase" type="text" class="input" size="4" value="<%=dbox.getFloat("d_grbase")%>" maxlength="4">
          </td>
        </tr>
      </form>
      </table>
      <br>
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title_01">문제</td>
          <td class="table_02_2">
            <table width="70%" cellspacing="0" cellpadding="0" class="form_table">
              <tr>
                <td>
                  <select name="s_questions" size="12" style="width:850;" onchange="ChooseQuestion();">
<%  
        DecimalFormat df = new DecimalFormat("00");
        for (int i=0; i<list.size(); i++) {
            DataBox dbox1  = (DataBox)list.get(i); %>
                    <option value="<%=dbox1.getInt("d_etestnum")%>"><%="("+df.format(dbox1.getInt("d_etestnum"))+") ["+dbox1.getString("d_etesttypenm")+"]["+dbox1.getString("d_levelsnm")+"] "+dbox1.getString("d_text") %>
<%    }  %>
                  </select>
                </td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
              <tr>
                <td align="right"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title_01">선택된 문제</td>
          <td class="table_02_2">
            <table width="70%" cellspacing="0" cellpadding="0" class="form_table">
              <tr>
                <td>
                  <select name="s_choosed" size="12" style="width:850;">
<%  
        for (int i=0; i<selectedList.size(); i++) {
            DataBox dbox2  = (DataBox)selectedList.get(i); %>
                    <option value="<%=dbox2.getInt("d_etestnum")%>"><%=df.format(i+1)+"-("+df.format(dbox2.getInt("d_etestnum"))+") ["+dbox2.getString("d_etesttypenm")+"]["+dbox2.getString("d_levelsnm")+"] "+dbox2.getString("d_text") %>
<%    }  %>
                  </select>
                </td>
                <td align="center">
                  <input type="button" value="↑" onClick="move(this.form.s_choosed.selectedIndex,-1)"><br><br>
                  <input type="button" value="↓" onClick="move(this.form.s_choosed.selectedIndex,+1)">
                </td>
              </tr>
              <tr>
                <td height="5" colspan="2"></td>
              </tr>
              <tr>
                <td align="right"><a href="javascript:ViewQuestion()"><img src="/images/admin/button/btn_questview.gif" border="0"></a>&nbsp;&nbsp;<a href="javascript:RemoveQuestion()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
                <td></td>
              </tr>
            </table>
          </td>
        </tr>
      </form>
      </table>
      <!----------------- 평가문제지 수정 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:UpdatePaper()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goMasterListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>