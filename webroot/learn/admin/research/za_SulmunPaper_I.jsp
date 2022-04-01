<%
/**
 * file name : za_SulmunQuestionList.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 설문 문항관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    String  v_grcode       = box.getStringDefault("p_grcode",SulmunBean.DEFAULT_GRCODE);
    String  v_gubun        = box.getStringDefault("p_gubun",  SulmunBean.DEFAULT_SUBJ);
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_company   = box.getString("s_company");       //회사
    String  ss_selgubun  = box.getString("s_selgubun");
    String  ss_seltext   = box.getString("s_seltext");
    String  ss_seldept   = box.getString("s_seldept");

    String  v_subjnm    = box.getString("p_subjnm");
    int     v_sulpapernum  = box.getInt("p_sulpapernum");
    String  v_sulpapername = box.getString("p_sulpapername");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String  v_startdate = box.getString("p_startdate");
    String  v_enddate   = box.getString("p_enddate");
    
    PaperData data1 = (PaperData)request.getAttribute("PaperData");
    if (data1 == null) {
        data1 = new PaperData();
    }
    String  v_request   = box.getString("p_request");
	String v_grpcomp    = "";
	String v_gpm        = "";
	String v_dept       = "";
	String v_jikun      = "";
	String v_jikup      = "";

    if (!v_request.equals("PaperUpdatePage")) {
        v_grpcomp = data1.getGrpcomp(0);
        v_gpm     = data1.getGpm(0);
        v_dept    = data1.getDept(0);
        v_jikun   = data1.getJikun(0);
        v_jikup   = data1.getJikup(0);
    }
%>
<html>
<head>
<title>설문지-문제등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_research.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_startdate_view").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_enddate_view").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function ChooseQuestion() {
    var boxLength    = document.form3.p_choosed.length;
    var selectedItem = document.form3.p_questions.selectedIndex;
    var selectedText = (boxLength+1) + '-' + document.form3.p_questions.options[selectedItem].text;
    var selectedValue= document.form3.p_questions.options[selectedItem].value;
    var i;
    var isNew = true;
    if (boxLength != 0) {
        for (i = 0; i < boxLength; i++) {
            thisitem = document.form3.p_choosed.options[i].value;
            if (thisitem == selectedValue) {
                isNew = false;
                break;
            }
        }
    }
    if (isNew) {
        newoption = new Option(selectedText, selectedValue, false, false);
        document.form3.p_choosed.options[boxLength] = newoption;
    }
    document.form3.p_questions.selectedIndex=-1;
}

function CancelChoosedQuestion() {
    var boxLength = document.form3.p_choosed.length;
    arrSelected = new Array();
    var count = 0;
    for (i = 0; i < boxLength; i++) {
        if (document.form3.p_choosed.options[i].selected) {
            arrSelected[count] = document.form3.p_choosed.options[i].value;
        }
        count++;
    }
    var x;
    for (i = 0; i < boxLength; i++) {
        for (x = 0; x < arrSelected.length; x++) {
            if (document.form3.p_choosed.options[i].value == arrSelected[x]) {
                document.form3.p_choosed.options[i] = null;
            }
        }
        boxLength = document.form3.p_choosed.length;
    }
}

function getChoosedQuestion() {
    var strValues = "";
    var boxLength = document.form3.p_choosed.length;
    var count = 0;
    if (boxLength != 0) {
        for (i = 0; i < boxLength; i++) {
            if (count == 0) {
                strValues = document.form3.p_choosed.options[i].value;
            }
            else {
                strValues = strValues + "," + document.form3.p_choosed.options[i].value;
            }
            count++;
        }
    }
    document.form2.p_sulnums.value = strValues;
    document.form2.p_totcnt.value  = count;
    return count;
}

function InsertPaper() {
    var choosed = getChoosedQuestion();
    if (choosed < 1) {
        alert('선택된 문제가 없습니다.');
        return;
    }
    if(!chkData()) {
      return;
    }


    document.form2.p_process.value = "PaperInsert";
    document.form2.p_action.value = "go";
    document.form2.submit();
}

function UpdatePaper() {
    var choosed = getChoosedQuestion();
    if (choosed < 1) {
        alert('선택된 문제가 없습니다.');
        return;
    }
    if(!chkData()) {
      return;
    }
<%  if (v_gubun.equals("WHOLE")) {  %>
    var v_date = make_date(form2.p_startdate_view.value);
    form2.p_startdate.value = v_date;
    v_date = make_date(form2.p_enddate_view.value);
    form2.p_enddate.value = v_date;
<%  } %>
    document.form2.p_process.value = "PaperUpdate";
    document.form2.p_action.value = "go";
    document.form2.submit();
}

function chkData() {
  if (blankCheck(document.form2.p_sulpapername.value)) {
    alert('설문지명을 입력하십시요.');
    document.form2.p_sulpapername.focus();
    return false;
  }
<%  if (v_gubun.equals("WHOLE")) {  %>
  if (blankCheck(document.form2.p_startdate_view.value)) {
    alert('설문기간을 입력하십시요.');
    return false;
  }
  if (blankCheck(document.form2.p_enddate_view.value)) {
    alert('설문기간을 입력하십시요.');
    return false;
  }
  var v_date = make_date(form2.p_startdate_view.value);
  form2.p_startdate.value = v_date;
  v_date = make_date(form2.p_enddate_view.value);
  form2.p_enddate.value = v_date;
<%  } %>
  return true;
}

function DeletePaper() {
    if (confirm("설문지를 삭제 하시겠습니까?")) {
        document.form2.p_process.value = "PaperDelete";
        document.form2.p_action.value = "go";
        document.form2.submit();
    }
}

function goPaperListPage() {
    document.form2.p_process.value = 'PaperPage';
    document.form2.p_action.value = 'go';
    document.form2.submit();
}

function move(index,to) {
    var list = document.form3.p_choosed;
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

function whenSelection(p_action ) {
//  document.form2.p_action.value  = p_action;
  document.form2.p_action.value  = "go";
  sulpapernum =  document.form2.p_sulpapernum.value;

  if (sulpapernum > 0) {
    document.form2.p_process.value = 'PaperUpdatePage';
  } else {
    document.form2.p_process.value = 'PaperInsertPage';
  }
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
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/images/admin/research/title_bg.gif"><img src="/images/admin/research/research_title<%=v_title_img%>.gif" width="259" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 설문지명, 등록버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="heed_table">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
        <input type="hidden" name="p_process"     value="<%=v_process%>">
        <input type="hidden" name="p_action"      value="">
        <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
        <input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
        <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
        <input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
        <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
        <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_sulpapernum" value="<%=String.valueOf(v_sulpapernum)%>">
        <input type="hidden" name="p_totcnt"      value="">
        <input type="hidden" name="p_sulnums"     value="">
        <input type="hidden" name="p_request"     value="PaperUpdatePage">
<%  if (v_gubun.equals("WHOLE")) {  %> 
        <input type="hidden" name="p_startdate"   value="">
        <input type="hidden" name="p_enddate"     value="">
<%        if (v_process.equals("PaperUpdatePage")) { %>
        <tr>
          <td>
            <table cellpadding="0" cellspacing="0" class="table1">
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
                <td align="left"><%="회사 : "+data1.getGrpcompnm(0)+" / 사업부 : "+data1.getGpmnm(0)+" / 부서 : "+data1.getDeptnm(0)+" / 직군 : "+data1.getJikunnm(0)+" / 직급 : "+data1.getJikupnm(0)%></td>
              </tr>
              <tr> 
                <td height="3"></td>
              </tr>
            </table>
          </td>
        </tr>
<%      }                        %>
        <tr>
          <td>
            <table cellspacing="0" cellpadding="3" class="box_form_table_out">
              <tr>
                <td bgcolor="#C6C6C6" align="center">
                  <table cellspacing="0" cellpadding="0" class="box_form_table_bg">
                    <tr>
                      <td height="7"></td>
                    </tr>
                    <tr>
                      <td align="center">
                        <table width="99%" cellspacing="0" cellpadding="0" class="box_form_table">
                          <tr>
                            <td valign="bottom" align="left" colspan="3">
                              <%@ include file="/jsp/admin/include/za_SelectCompany.jsp" %>
                              기간: <input name="p_startdate_view" id="p_startdate_view" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(data1.getStartdate(0),"yyyy-MM-dd")%>" size="10">
                              -<input name="p_enddate_view" id="p_enddate_view" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(data1.getEnddate(0),"yyyy-MM-dd")%>" size="10">
                            </td>
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
          </td>
        </tr>
<%  } %>
        <tr>
          <td>
            <table cellpadding="0" cellspacing="0" class="table1">
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
          <td valign="bottom" align="left"><% if (v_gubun.equals("SUBJ")) { %>과정명 : [<%=v_subjnm%>]<% } %>  설문지명 : <input name="p_sulpapername" type="text" class="input" value="<%=v_sulpapername%>" size="40"></td>
<%    if (v_sulpapernum == 0) { %>
          <td align="right" width="44"><a href="javascript:InsertPaper()"><img src="/images/admin/research/record1_butt.gif" width="37" height="18" border="0"></a></td>
<%    } else if (v_sulpapernum > 0) { %>
          <td align="right" width="88"><a href="javascript:UpdatePaper()"><img src="/images/admin/research/save1_butt.gif" width="37" height="18" border="0"></a> <a href="javascript:DeletePaper()"><img src="/images/admin/research/del1_butt.gif" width="37" height="18" border="0"></a></td>
<%    } %>
          <td align="right" width="54"><a href="javascript:goPaperListPage()"><img src="/images/admin/research/list1_butt.gif" width="48" height="18" border="0"></a></td>
              </tr>
              <tr> 
                <td height="3"></td>
              </tr>
            </table>
          </td>
        </tr>
        </tr>
        <tr>
          <td height="3" colspan="3"></td>
        </tr>
      </form>
      </table>
<%  if (v_gubun.equals("WHOLE")) {  %> 
        <br>
        <br>
        <br>
        <br>
        <br>
<%  } %>
      <!----------------- 설문지명,  등록버튼 끝 ----------------->
      <!----------------- 설문지 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table1">
      <form name="form3" method="post" action="">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title_01">설문문제</td>
          <td class="table_title_01">선택문제</td>
        </tr>
        <tr>
          <td class="table_02_1">
            <table width="75%" cellspacing="0" cellpadding="0" class="form_table">
              <tr>
                <td align="center"><select name="p_questions" size="20" style="width:450;" onchange="ChooseQuestion();">
<%  ArrayList list1 = (ArrayList)request.getAttribute("TargetQuestionList");
    ArrayList list2 = (ArrayList)request.getAttribute("PaperQuestionList");
    if (list1 == null) {
        list1 = new ArrayList();
    }
    if (list2 == null) {
        list2 = new ArrayList();
    }
    QuestionData data  = null;
    for (int i=0; i<list1.size(); i++) {
        data  = (QuestionData)list1.get(i); %>
                  <option value="<%=String.valueOf(data.getSulnum())%>"><%="("+String.valueOf(data.getSulnum())+") ["+data.getSultypenm()+"] "+data.getSultext() %>
<%  }  %>
                </select>
                </td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
          </td>
          <td class="table_02_1">
            <table width="75%" cellspacing="0" cellpadding="0" class="form_table">
              <tr>
                <td align="center"> <select name="p_choosed" size="20" style="width:450;">
<%  for (int i=0; i<list2.size(); i++) {
        data = (QuestionData)list2.get(i); %>
              <option value="<%=String.valueOf(data.getSulnum())%>"><%=String.valueOf(i+1)+"-("+String.valueOf(data.getSulnum())+") ["+data.getSultypenm()+"] "+data.getSultext() %>
<%  }  %>
                 </select>
                </td>
                <td>
                <input type="button" value="↑" onClick="move(this.form.p_choosed.selectedIndex,-1)"><br><br>
                <input type="button" value="↓" onClick="move(this.form.p_choosed.selectedIndex,+1)">
                </td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td colspan="2" align="right"><a href="javascript:CancelChoosedQuestion()"><img src="/images/admin/research/del2_butt.gif" width="58" height="18" border="0"></a></td>
        </tr>
      </form>
      </table>
      <!----------------- 설문지 관리 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/jsp/library/getJspName.jsp" %></td></tr>     

</table>

</body>
</html>
