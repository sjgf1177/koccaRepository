<%
/**
 * file name : za_DamunSubjPaper_U.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 다면평가지 수정 페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String  v_process   = box.getString("p_process");
    String  v_grcode       = box.getStringDefault("p_grcode",DamunSubjBean.DEFAULT_GRCODE);
    String  v_gubun        = box.getStringDefault("p_gubun",  DamunSubjBean.DEFAULT_SUBJ);
    String  v_gyear        = box.getString("p_gyear");
    String  v_subj        = box.getString("p_subj");
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  v_subjnm    = box.getString("p_subjnm");
    int     v_damunpapernum  = box.getInt("p_damunpapernum");
    String  v_damunpapernm = box.getString("p_damunpapernm");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String  v_fdamunstart = box.getString("p_fdamunstart");
    String  v_fdamunend   = box.getString("p_fdamunend");
    String  v_sdamunstart = box.getString("p_sdamunstart");
    String  v_sdamunend   = box.getString("p_sdamunend");

    String v_reloadlist = box.getString("p_reloadlist");    

    ArrayList list1 = (ArrayList)request.getAttribute("DamunSubjQuestionList");
    ArrayList list2 = (ArrayList)request.getAttribute("DamunSubjPaperQuestionList");
	DataBox dbox0 = (DataBox)request.getAttribute("DamunPaperData");
    String  v_d_gubun        = box.getStringDefault("p_d_gubun","1");
%>
<html>
<head>
<title>다면평가지 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
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
	$("#p_fdamunstart_view2").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_fdamunend_view2").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_sdamunstart_view2").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_sdamunend_view2").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
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
    document.form2.p_damunnums.value = strValues;
    document.form2.p_totcnt.value  = count;
    return count;
}

function UpdatePaper() {
    if(!chkData()) {
      return;
    }
	var choosed = getChoosedQuestion();
    if (choosed < 1) {
        alert('선택된 문제가 없습니다.');
        return;
    }

    document.form2.p_process.value = "DamunPaperUpdate";
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.submit();
}

function chkData() {

  var v_d_gubun = document.form2.p_d_gubun.options[document.form2.p_d_gubun.selectedIndex].value;

  if (v_d_gubun == 2){
  if (blankCheck(document.form2.p_damunpapernm.value)) {
    alert('다면평가지명을 입력하십시요.');
    document.form2.p_damunpapernm.focus();
    return false;
  }
  if (blankCheck(document.form2.p_fdamunstart_view2.value)) {
    alert('1차 평가기간을 입력하십시요.');
    return false;
  }
  if (blankCheck(document.form2.p_fdamunend_view2.value)) {
    alert('1차 평가기간을 입력하십시요.');
    return false;
  }
  if (blankCheck(document.form2.p_sdamunstart_view2.value)) {
    alert('2차 평가기간을 입력하십시요.');
    return false;
  }
  if (blankCheck(document.form2.p_sdamunend_view2.value)) {
    alert('2차 평가기간을 입력하십시요.');
    return false;
  }
  
  form2.p_damunpapernm.value = form2.p_damunpapernm.value;
  var v_date = make_date(form2.p_fdamunstart_view2.value);
  form2.p_fdamunstart.value = v_date;
  v_date = make_date(form2.p_fdamunend_view2.value);
  form2.p_fdamunend.value = v_date;
  if (form2.p_fdamunend.value < form2.p_fdamunstart.value) {
    alert('1차 평가 종료기간을 수정하십시요.');
    return false;  
  }  
  var v_date1 = make_date(form2.p_sdamunstart_view2.value);
  form2.p_sdamunstart.value = v_date1;
  v_date1 = make_date(form2.p_sdamunend_view2.value);
  form2.p_sdamunend.value = v_date1;
  if (form2.p_sdamunend.value < form2.p_sdamunstart.value) {
    alert('2차 평가 종료기간을 수정하십시요.');
    return false;  
  }  
  }

  return true;
}

function DeletePaper() {
    if (confirm("다면평가지를 삭제 하시겠습니까?")) {
        document.form2.p_process.value = "DamunPaperDelete";
        document.form2.p_reloadlist.value = 'true';
        document.form2.p_action.value = "go";
        document.form2.submit();
    }
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
function display() {
	
	var v_d_gubun = document.form2.p_d_gubun.value;
	    document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.multiestimate.DamunSubjPaperServlet";
		document.form2.p_process.value = "DamunPaperUpdatePage";
        document.form2.p_action.value  = "go";
		document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
	 <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->

      <!----------------- 다면평가지명, 등록버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="heed_table">
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjPaperServlet">
        <input type="hidden" name="p_process"     value="<%=v_process%>">
        <input type="hidden" name="p_action"      value="">
        <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
        <input type="hidden" name="s_grcode"      value="<%=v_grcode%>">
        <input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
        <input type="hidden" name="p_gyear"       value="<%=v_gyear%>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
        <input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
        <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
        <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_damunpapernum" value="<%=String.valueOf(v_damunpapernum)%>">
        <input type="hidden" name="p_totcnt"      value="">
        <input type="hidden" name="p_damunnums"     value="">
        <input type="hidden" name="p_fdamunstart"   value="">
        <input type="hidden" name="p_fdamunend"     value="">
        <input type="hidden" name="p_sdamunstart"   value="">
        <input type="hidden" name="p_sdamunend"     value="">
		<input type="hidden" name="p_reloadlist"  value="">

        <tr>
          <td>
            <table cellspacing="0" cellpadding="1" class="form_table_out">
              <tr>
                <td bgcolor="#C6C6C6" align="center">
                  <table cellspacing="0" cellpadding="0" class="form_table_bg">
                    <tr>
                      <td height="7"></td>
                    </tr>
                    <tr>
                      <td align="center">
                        <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
						  <tr>
						  <td valign="bottom" align="left" width=10%>&nbsp;&nbsp;<font color=#E0448E><b>문제분류 : </font></b></td>
		    			    <td valign="bottom" align="left">
							 <SELECT name="p_d_gubun" onchange="display();"> 
                               <option value="1" <%if(v_d_gubun.equals("1")) out.print("selected"); %>>사전/사후 동시</option> 
                               <option value="2" <%if(v_d_gubun.equals("2")) out.print("selected"); %>>사전/사후 분리</option> 
							   <script>document.form2.p_d_gubun.value = <%=v_d_gubun%>;</script>
                             </SELECT>
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
      </table>

      <table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
          <td>
            <table cellspacing="0" cellpadding="3" class="form_table_out">
              <tr>
                <td bgcolor="#C6C6C6" align="center">
                  <table cellspacing="0" cellpadding="0" class="form_table_bg">
                    <tr>
                      <td height="7"></td>
                    </tr>
                    <tr>
                      <td align="center">
                        <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
						  <tr>
		    			    <td valign="bottom" align="left">
              				 평가지명 : <input name="p_damunpapernm" type="text" class="input" value="<%=v_damunpapernm%>" size="40"></td>
						  </tr>
                          <tr>
                            <td valign="bottom" align="left" colspan="3">
                              1차 기간 : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="p_fdamunstart_view2" id="p_fdamunstart_view2" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(dbox0.getString("d_fdamunstart"),"yyyy-MM-dd")%>" size="10">
                              -<input name="p_fdamunend_view2" id="p_fdamunend_view2" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(dbox0.getString("d_fdamunend"),"yyyy-MM-dd")%>" size="10">
                            </td>
                            <td valign="bottom" align="left" colspan="3">
                              2차 기간 : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="p_sdamunstart_view2" id="p_sdamunstart_view2" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(dbox0.getString("d_sdamunstart"),"yyyy-MM-dd")%>" size="10">
                              -<input name="p_sdamunend_view2" id="p_sdamunend_view2" type="text" class="datepicker_input1" value="<%=FormatDate.getFormatDate(dbox0.getString("d_sdamunend"),"yyyy-MM-dd")%>" size="10">
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
		</table>
    	<br><br><br>
		<table class="heed_table">
        <tr>
          <td>
            <table cellpadding="0" cellspacing="0" class="table1">
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
			    <td align="left">&nbsp;</td>
               <td align="right" width="54"><a href="javascript:UpdatePaper()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			   <td width=4></td>
               <td align="right" width="54"><a href="javascript:DeletePaper()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
			   <td width=4></td>
               <td align="right" width="54"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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


      <!----------------- 다면평가지명,  등록버튼 끝 ----------------->
      <!----------------- 다면평가지 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title">다면평가문제</td>
          <td class="table_title">선택문제</td>
        </tr>
        <tr>
          <td class="table_02_1">
            <table width="75%" cellspacing="0" cellpadding="0" class="form_table">
              <tr>
                <td align="center"><select name="p_questions" size="20" style="width:450;" onchange="ChooseQuestion();">
<%    
    if (list1 == null) {
        list1 = new ArrayList();
    }
    if (list2 == null) {
        list2 = new ArrayList();
    }
    DataBox dbox  = null;
    for (int i=0; i<list1.size(); i++) {
        dbox  = (DataBox)list1.get(i); %>
                  <option value="<%=String.valueOf(dbox.getInt("d_damunnum"))%>"><%="("+String.valueOf(dbox.getInt("d_damunnum"))+") ["+dbox.getString("d_damuntypenm")+"] "+dbox.getString("d_damuntext") %>
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
        dbox = (DataBox)list2.get(i); %>
              <option value="<%=String.valueOf(dbox.getInt("d_damunnum"))%>"><%=String.valueOf(i+1)+"-("+String.valueOf(dbox.getInt("d_damunnum"))+")["+dbox.getString("d_damuntypenm")+"] "+dbox.getString("d_damuntext") %>
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
          <td class="table_02_1" colspan="2" align="right"><a href="javascript:CancelChoosedQuestion()"><img src="/images/admin/button/btn_questdelet.gif" border="0"></a></td>
        </tr>
      </form>
      </table>
      <!----------------- 다면평가지 관리 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>

</body>
</html>
