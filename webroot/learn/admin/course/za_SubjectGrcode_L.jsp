<%
//**********************************************************
//  1. 제      목: 교육그룹별 과정코드 등록화면
//  2. 프로그램명: za_SubjectGrcode_L.jsp
//  3. 개      요: 교육그룹별 과정코드 등록화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    
    String  ss_subj      = box.getStringDefault("s_subj","ALL");
    String v_subj     = box.getString("p_subj");
    String v_subjnm   = box.getString("p_subjnm");

    ArrayList list1 = (ArrayList)request.getAttribute("TargetGrcodeList");
    ArrayList list2 = (ArrayList)request.getAttribute("SelectedGrcodeList");
%>
<html>
<head>
<title>코스수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function ChooseGrcode() {
    var boxLength = document.form2.p_selgrcodes.length;
    var selectedItem = document.form2.p_grcodes.selectedIndex;
    var selectedText = document.form2.p_grcodes.options[selectedItem].text;
    var selectedValue = document.form2.p_grcodes.options[selectedItem].value;
    var i;
    var isNew = true;
    if (boxLength != 0) {
        for (i = 0; i < boxLength; i++) {
            thisitem = document.form2.p_selgrcodes.options[i].text;
            if (thisitem == selectedText) {
                isNew = false;
                break;
            }
        }
    }

    if (isNew) {
        newoption = new Option(selectedText, selectedValue, false, false);
        document.form2.p_selgrcodes.options[boxLength] = newoption;
    }

    document.form2.p_grcodes.selectedIndex=-1;
}

function CancelChooseGrcode() {
    var boxLength = document.form2.p_selgrcodes.length;
    arrSelected = new Array();
    var count = 0;
    for (i = 0; i < boxLength; i++) {
        if (document.form2.p_selgrcodes.options[i].selected) {
            arrSelected[count] = document.form2.p_selgrcodes.options[i].value;
        }
        count++;
    }

    var x;
    for (i = 0; i < boxLength; i++) {
        for (x = 0; x < arrSelected.length; x++) {
            if (document.form2.p_selgrcodes.options[i].value == arrSelected[x]) {
                document.form2.p_selgrcodes.options[i] = null;
            }
        }
        boxLength = document.form2.p_selgrcodes.length;
    }
}

function UpdateSubjectGrcode() {
    if (!getSelectedGrcodes()) {
        alert("교육그룹을 선택하세요");
        return;
    }
    document.form2.p_process.value = 'relatedGrcodeInsert';
    document.form2.submit();
}

function getSelectedGrcodes() {
    var strValues = "";
    var boxLength = document.form2.p_selgrcodes.length;
    var count = 0;
    if (boxLength != 0) {
        for (i = 0; i < boxLength; i++) {
            if (count == 0) {
                strValues = document.form2.p_selgrcodes.options[i].value;
            }
            else {
                strValues = strValues + ";" + document.form2.p_selgrcodes.options[i].value;
            }
            count++;
        }
    }
    document.form2.p_selectedgrcodes.value = strValues;
    return (strValues.length == 0) ? false : true;
}

function goSubjectListPage() {
    document.form2.p_process.value    = 'listPage';
    document.form2.p_upperclass.value = "";
    document.form2.p_subj.value       = "";
    document.form2.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.course.SubjectServlet">
    <input type="hidden" name="p_process"          value="">
    <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass"       value="<%=ss_middleclass%>">
    <input type="hidden" name="s_subj"             value="<%=ss_subj%>">
    <input type="hidden" name="p_subj"             value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"           value="<%=v_subjnm%>">
    <input type="hidden" name="p_upperclass"       value="">
    <input type="hidden" name="p_selectedgrcodes"  value="">
    <input type="hidden" name="s_grcode"           value="<%=ss_grcode%>">
    
    <input type="hidden" name="s_action"  value="go"> 

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 교육그룹 연결 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="16%" class="table_title"><b>과정코드</b></td>
          <td width="81%" class="table_02_2" colspan="3"><%=v_subj%></td>
        </tr>
        <tr> 
          <td class="table_title"><b>과정명</b></td>
          <td class="table_02_2" colspan="3"> <%=v_subjnm%></td>
        </tr>
        <tr> 
            <td class="table_title" colspan="2" width="50%"><b>교육그룹 선택</b></td>
            <td class="table_title" colspan="2" width="50%"><b>선택된 교육그룹</b></td>
        </tr>
        <tr> 
          <td class="table_02_2" colspan="2" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="left" bgcolor="#FFFFFF">
                  <select name="p_grcodes" size=20 style="width:400;" onchange="ChooseGrcode();">
<%  
    SelectionData data  = null;
    for (int i=0; i<list1.size(); i++) {
        data  = (SelectionData)list1.get(i); %>
                    <option value="<%=data.getCode()%>"><%=data.getName()%>
<%  }  %>
                  </select>
                </td>
              </tr>
            </table>
          </td>
          <td class="table_02_2" colspan="2"> 
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="left" bgcolor="#FFFFFF">
                <select multiple name="p_selgrcodes" size=20  style="width:400;">
<%  for (int i=0; i<list2.size(); i++) {
        data  = (SelectionData)list2.get(i); %>
                  <option value="<%=data.getCode()%>"><%=data.getName()%>
<%  }  %>
                </select>
            &nbsp;<a href="javascript:CancelChooseGrcode()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 교육그룹 연결 끝 ----------------->
      <br>
      <!----------------- 저장, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><a href="javascript:UpdateSubjectGrcode()"><img src="/images/admin/button/btn_save.gif"  border="0"></a></td>
          <td width=8></td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif"  border="0"></a></td>
        </tr>
      </table>
      <!----------------- 저장, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>





