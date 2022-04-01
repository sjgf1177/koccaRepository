<%
//**********************************************************
//  1. 제      목: 설문지관리
//  2. 프로그램명: za_SulmunCpPaper_I.java
//  3. 개      요: 전체설문 - 설문지등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 6. 30
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    // 새창 close 시키고 부모창 리로드 
	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String  v_process   = box.getString("p_process");
    String  v_grcode       = box.getStringDefault("p_grcode",SulmunCpBean.DEFAULT_GRCODE);
    String  v_gubun        = box.getStringDefault("p_gubun",  SulmunCpBean.DEFAULT_SUBJ);

    String  v_subjnm    = box.getString("p_subjnm");
    int     v_sulpapernum  = box.getInt("p_sulpapernum");
    String  v_sulpapernm = box.getString("p_sulpapernm");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String  v_sulstart = box.getString("p_sulstart");
    String  v_sulend   = box.getString("p_sulend");

    String v_reloadlist = box.getString("p_reloadlist");    
%>
<html>
<head>
<title>설문지-문제등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 설문문제 선택
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

// 문항삭제
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

// 선택된 문항여부 체크
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

// 문제지 등록
function InsertPaper() {
    var choosed = getChoosedQuestion();
    if (choosed < 1) {
        alert('선택된 문제가 없습니다.');
        return;
    }
    //alert(document.form2.p_sulnums.value);
    //return;

	document.form2.p_process.value = "SulmunPaperInsert";
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.submit();
}

// 문항 이동
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
          <td><img src="/images/admin/research/r_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 설문지명, 등록버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="heed_table">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunCpPaperServlet">
        <input type="hidden" name="p_process"     value="<%=v_process%>">
        <input type="hidden" name="p_action"      value="">
        <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
        <input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
        <input type="hidden" name="p_sulpapernum" value="<%=String.valueOf(v_sulpapernum)%>">
        <input type="hidden" name="p_totcnt"      value="">
        <input type="hidden" name="p_sulnums"     value="">
        <input type="hidden" name="p_sulstart"   value="">
        <input type="hidden" name="p_sulend"     value="">
		<input type="hidden" name="p_reloadlist"  value="">

        <tr>
          <td>
            <table cellpadding="0" cellspacing="0" class="table1">
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
               <td valign="bottom" align="left">
               설문지명 : <input name="p_sulpapernm" type="text" class="input" value="업체설문" size="60" >
               <!--&nbsp;&nbsp;제한 진도율 : <input name="p_progress" type="text" class="input" value="80" size="3" maxlength="3">-->
               </td>
                <td align="right" width="44"><a href="javascript:InsertPaper()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
				<td width=8></td>
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

      <!----------------- 설문지명,  등록버튼 끝 ----------------->
      <!----------------- 설문지 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
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

<%  ArrayList list1 = (ArrayList)request.getAttribute("SulmunPaperInsertPage");
    if (list1 == null) {
        list1 = new ArrayList();
    }
    DataBox dbox  = null;
    for (int i=0; i<list1.size(); i++) {
        dbox  = (DataBox)list1.get(i); %>
                  <option value="<%=String.valueOf(dbox.getInt("d_sulnum"))%>"><%="("+String.valueOf(dbox.getInt("d_sulnum"))+") ["+dbox.getString("d_distcodenm")+"]"+"["+dbox.getString("d_sultypenm")+"] "+dbox.getString("d_sultext") %>
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
          <td class="table_02_1" colspan="2" align="right"><a href="javascript:CancelChoosedQuestion()"><img src="/images/admin/button/btn_questdelet.gif"  border="0"></a></td>
        </tr>
      </form>
      </table>
      <!----------------- 설문지 관리 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
</body>
</html>
