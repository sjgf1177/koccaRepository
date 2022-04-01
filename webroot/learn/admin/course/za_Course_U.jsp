<%
/**
 * file name : za_Course_U.jsp
 * date      : 2003/07/08
 * programmer: 
 * function  : 전문가 수정화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
      box = RequestManager.getBox(request);
    }
    String v_course     = box.getString("p_course");
    String v_coursenm   = box.getString("p_coursenm");
    String v_gradscore  = box.getString("p_gradscore");
    String v_gradfailcnt= box.getString("p_gradfailcnt");
    String v_biyong     = box.getString("p_biyong");
    String v_subjcnt    = box.getString("p_subjcnt");
    String v_upperclass_i = box.getString("p_upperclass_i");

    String v_onoffgubun = box.getStringDefault("p_gubun","ALL");
    String v_upperclass = box.getStringDefault("s_upperclass","ALL");

    ArrayList list1 = (ArrayList)request.getAttribute("TargetSubjectList");
    ArrayList list2 = (ArrayList)request.getAttribute("SelectedSubjectList");
%>
<html>
<head>
<title>전문가수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function SelectSubject() {
  getSelectedSubjCodes();
  getSelectedSubjTexts();
  document.form2.p_process.value = 'updatePage';
  document.form2.submit();
}
// 셀렉트 선택후 검색
function whenSelection(ss_action) {
    SelectSubject();
}


function ChooseSubject() {
  var boxLength = document.form2.p_selsubjs.length;
  var selectedItem = document.form2.p_subjects.selectedIndex;
  var selectedText = document.form2.p_subjects.options[selectedItem].text;
  var selectedValue = document.form2.p_subjects.options[selectedItem].value;
  var i;
  var isNew = true;
  if (boxLength != 0) {
    for (i = 0; i < boxLength; i++) {
      thisitem = document.form2.p_selsubjs.options[i].text;
      if (thisitem == selectedText) {
        isNew = false;
        break;
      }
    }
  }

  if (isNew) {
    newoption = new Option(selectedText, selectedValue, false, false);
    document.form2.p_selsubjs.options[boxLength] = newoption;
  }

  document.form2.p_subjects.selectedIndex=-1;
}

function CancelChoosedSubject() {
  var boxLength = document.form2.p_selsubjs.length;
  arrSelected = new Array();
  var count = 0;
  for (i = 0; i < boxLength; i++) {
    if (document.form2.p_selsubjs.options[i].selected) {
      arrSelected[count] = document.form2.p_selsubjs.options[i].value;
    }
    count++;
  }

  var x;
  for (i = 0; i < boxLength; i++) {
    for (x = 0; x < arrSelected.length; x++) {
      if (document.form2.p_selsubjs.options[i].value == arrSelected[x]) {
        document.form2.p_selsubjs.options[i] = null;
      }
    }
    boxLength = document.form2.p_selsubjs.length;
  }
}

function UpdateCourse() {
  if (!getSelectedSubjCodes()) {
    alert("과정코드 선택안함");
    return;
  }
  if (!getSelectedSubjTexts()) {
    alert("과정코드 선택안함");
    return;
  }
  if (document.form2.p_selsubjs.length == 1) {
    alert("과정이 1개만 선택되었습니다.");
    return;
  }
	if(!chkData()) {
	  return;
	}
  document.form2.p_subjcnt.value = document.form2.p_selsubjs.length;
  document.form2.p_process.value = 'update';
  document.form2.submit();
}

function chkData() {
  if (blankCheck(document.form2.p_coursenm.value)) {
    alert('전문가명을 입력하십시요.');
    document.form2.p_coursenm.focus();
    return false;
  }
  if (blankCheck(document.form2.p_gradscore.value) || !number_chk_noalert(document.form2.p_gradscore.value)) {
    alert('이수기준-평균점수가 잘못입력되었습니다.');
    document.form2.p_gradscore.focus();
    return false;
  }
/*
  if (blankCheck(document.form2.p_gradfailcnt.value) || !number_chk_noalert(document.form2.p_gradfailcnt.value)) {
    alert('이수기준-과락갯수가 잘못입력되었습니다.');
    document.form2.p_gradfailcnt.focus();
    return false;
  }
*/
  return true;
}

function DeleteCourse() {
  if (confirm("전문가정보를 삭제 하시겠습니까?")) {
    document.form2.p_process.value = 'delete';
    document.form2.submit();
  }
}

function getSelectedSubjCodes() {
  var strValues = "";
  var boxLength = document.form2.p_selsubjs.length;
  var count = 0;
  if (boxLength != 0) {
    for (i = 0; i < boxLength; i++) {
      if (count == 0) {
        strValues = document.form2.p_selsubjs.options[i].value;
      }
      else {
        strValues = strValues + ";" + document.form2.p_selsubjs.options[i].value;
      }
      count++;
    }
  }
  document.form2.p_selectedsubjcodes.value = strValues;
  return (strValues.length == 0) ? false : true;
}

function getSelectedSubjTexts() {
  var strValues = "";
  var boxLength = document.form2.p_selsubjs.length;
  var count = 0;
  if (boxLength != 0) {
    for (i = 0; i < boxLength; i++) {
      if (count == 0) {
        strValues = document.form2.p_selsubjs.options[i].text;
      }
      else {
        strValues = strValues + ";" + document.form2.p_selsubjs.options[i].text;
      }
      count++;
    }
  }
  document.form2.p_selectedsubjtexts.value = strValues;
  return (strValues.length == 0) ? false : true;
}

function goCourseListPage() {
  document.form2.p_process.value  = 'listPage';
  document.form2.p_course.value   = '';
  document.form2.p_coursenm.value = '';
  document.form2.submit();
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr> 
          <td><img src="/images/admin/course/c_title10.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
     <!----------------- title 끝 ----------------->
      <br>
      <br>
      <!----------------- 전문가 등록 시작 ----------------->
      <table width="97%" class="table_out" cellspacing="1" cellpadding="5">
      <form name="form2" method="post" action="/servlet/controller.course.CourseServlet">
        <input type="hidden" name="p_process"        value="">
        <input type="hidden" name="p_course"         value="<%=v_course%>">
        <input type="hidden" name="p_upperclass_i"   value="<%=v_upperclass_i%>">
        <input type="hidden" name="p_middleclass"    value="COU">
        <input type="hidden" name="p_lowerclass"     value="000">        
        <input type="hidden" name="p_selectedsubjcodes"  value="">
        <input type="hidden" name="p_selectedsubjtexts"  value="">
		<input type="hidden" name="p_subjcnt"       value="">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>전문가코드</b></td>
          <td class="table_02_2" width="81%"><%=v_course%></td>
        </tr>
        <tr>
          <td class="table_title"><b>전문가명</b></td>
          <td class="table_02_2">
            <input name="p_coursenm" type="text" class="input" size="60" value="<%=v_coursenm%>">
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>구분</b></td>
          <td class="table_02_2">
            교육구분<%= CodeConfigBean.getCodeGubunSelect ("0004", "", 1, "p_gubun", v_onoffgubun, " onChange=\"SelectSubject()\"", 2) %>
            <%= SelectSubjBean.getUpperClass(box, true, true, true)%>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>과정 선택</b></td>
          <td class="table_02_2"><select name="p_subjects" size=8 style="width:650;" onchange="ChooseSubject();">
<%  SubjectInfoData data  = null;
    for (int i=0; i<list1.size(); i++) {
        data  = (SubjectInfoData)list1.get(i); %>
            <option value="<%=data.getSubj()%>"><%=data.getDisplayname()%>
<%  }  %>
          </select></td>
        </tr>
        <tr>
          <td class="table_title"><b>선택된 과정</b></td>
          <td class="table_02_2">
            <table width="75%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><select multiple name="p_selsubjs" size=8  style="width:650;">
<%  for (int i=0; i<list2.size(); i++) {
        data  = (SubjectInfoData)list2.get(i); %>
                <option value="<%=data.getSubj()%>"><%=data.getDisplayname()%>
<%  }  %>
                </select></td>
              </tr>
              <tr><td height="5"></td></tr>
              <tr>
                <td align="right"><a href="javascript:CancelChoosedSubject()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>이수기준-평균점수</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_gradscore" size="10" maxlength="3" value="<%=v_gradscore%>"></td>
        </tr>
<!--
        <tr>
          <td class="table_title"><b>이수기준-과락갯수</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_gradfailcnt" size="10" maxlength="3" value="<%=v_gradfailcnt%>"></td>
        </tr>
-->
        <input type="hidden" name="p_gradfailcnt" value="0">
        <tr>
          <td class="table_title"><b>수강료</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_biyong" size="10" maxlength="7" value="<%=v_biyong%>"> 원</td>
        </tr>
      </table>
      <!----------------- 전문가 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 삭제, 리스트 버튼 시작 ----------------->
      <table width="16%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:UpdateCourse()"><img src="/images/admin/button/btn_save.gif"  border="0"></a></td>
          <td align="center"><a href="javascript:DeleteCourse()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
          <td align="center"><a href="javascript:goCourseListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 삭제, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
