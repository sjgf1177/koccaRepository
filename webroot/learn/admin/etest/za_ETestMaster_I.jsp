<%
/**
 * file name : za_ETestMaster_I.jsp
 * date      : 
 * programmer:
 * function  : 평가마스터 등록
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

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

	String v_reloadlist = box.getString("p_reloadlist");

    String  v_etestsubj       = box.getString("p_etestsubj");
    String  v_gyear       = box.getString("p_gyear");

%>
<html>
<head>
<title>평가마스터등록</title>
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
<script language="JavaScript">
$(document).ready(function(){
	$("#p_startdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_enddate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function InsertMaster() {    
    if(!chkData()) {
      return;
    }
  document.form1.p_process.value = 'ETestMasterInsert';
  document.form1.p_action.value  = 'go';
  document.form1.p_reloadlist.value = 'true';
  document.form1.submit();
}
function chkData() {

  if (blankCheck(document.form1.p_etesttext.value)) {
    alert('테스트명을 입력하십시요.');
    document.form1.p_etesttext.focus();
    return false;
  }
  if (blankCheck(document.form1.p_startdate.value)) {
    alert('시작시간이 잘못 입력되었습니다.');
    return false;
  }
  if (blankCheck(document.form1.p_enddate.value)) {
    alert('종료시간이 잘못 입력되었습니다.');
    return false;
  }
  var v_startdt = make_date(form1.p_startdate.value) + form1.p_starthour.value + form1.p_startminute.value; 
  var v_enddt = make_date(form1.p_enddate.value) + form1.p_endhour.value + form1.p_endminute.value;  
  var v_gap = v_enddt - v_startdt; 
  document.form1.p_startdt.value = v_startdt; 
  document.form1.p_enddt.value = v_enddt; 

  if ( 1 > v_gap) {
    alert('종료시간을 수정하십시요.');
    return false;  
  }
  if (!number_chk_noalert(document.form1.p_etesttime.value) || 1 > document.form1.p_etesttime.value) {
    alert('시험시간이  잘못 입력되었습니다.');
    document.form1.p_etesttime.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_etestlimit.value) || 1 > document.form1.p_etestlimit.value) {
    alert('정원이  잘못 입력되었습니다.');
    document.form1.p_etestlimit.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_etestpoint.value) || 1 > document.form1.p_etestpoint.value) {
    document.form1.p_etestpoint.focus();
    alert('문제당 배점이 잘못 입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form1.p_etestcnt.value) || 1 > document.form1.p_etestcnt.value) {
    document.form1.p_etestcnt.focus();
    alert('문제수가 잘못 입력되었습니다.');
    return false;
  }
  if (document.form1.p_etestpoint.value * document.form1.p_etestcnt.value != 100) {
    document.form1.p_etestpoint.focus();
    alert('총점수는 100점입니다. 문제당 배점이나 문제수를 조정해 주십시요.');
    return false;
  }

  if (!number_chk_noalert(document.form1.p_cntlevel1M1.value) || document.form1.v_cntlevel1M1.value < document.form1.p_cntlevel1M1.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel1M1.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_cntlevel2M1.value) || document.form1.v_cntlevel2M1.value < document.form1.p_cntlevel2M1.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel2M1.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_cntlevel3M1.value) || document.form1.v_cntlevel3M1.value < document.form1.p_cntlevel3M1.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel3M1.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_cntlevel1M2.value) || document.form1.v_cntlevel1M2.value < document.form1.p_cntlevel1M2.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel1M2.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_cntlevel2M2.value) || document.form1.v_cntlevel2M2.value < document.form1.p_cntlevel2M2.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel2M2.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_cntlevel3M2.value) || document.form1.v_cntlevel3M2.value < document.form1.p_cntlevel3M2.value) {
    alert('난이도별 문항수가 잘못입력되었습니다.');
    document.form1.p_cntlevel3M2.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_retrynum.value)) {
    alert('총응시횟수가  잘못입력되었습니다.');
    document.form1.p_retrynum.focus();
    return false;
  }
  if (!number_chk_noalert(document.form1.p_papercnt.value) || 1 > document.form1.p_papercnt.value ) {
    alert('시험지수가  잘못입력되었습니다.');
    document.form1.p_papercnt.focus();
    return false;
  }

  var v_etestcnt = parseInt(document.form1.p_cntlevel1M1.value,10) + parseInt(document.form1.p_cntlevel2M1.value,10) + parseInt(document.form1.p_cntlevel3M1.value,10) + parseInt(document.form1.p_cntlevel1M2.value,10) + parseInt(document.form1.p_cntlevel2M2.value,10) + parseInt(document.form1.p_cntlevel3M2.value,10);

  if (v_etestcnt != document.form1.p_etestcnt.value) {
    document.form1.p_cntlevel1M1.focus();
    alert('입력한 문제수는 ' + v_etestcnt + '개 입니다. \n문제수에 맞게 문제 배분을 해 주세요.');
    return false;
  }

	var s_level1text = document.form1.p_cntlevel1M1.value + "," + document.form1.p_cntlevel1M2.value;
	var s_level2text = document.form1.p_cntlevel2M1.value + "," + document.form1.p_cntlevel2M2.value;
	var s_level3text = document.form1.p_cntlevel3M1.value + "," + document.form1.p_cntlevel3M2.value; 
	var s_cntlevel1 = parseInt(document.form1.p_cntlevel1M1.value) + parseInt(document.form1.p_cntlevel1M2.value); 
	var s_cntlevel2 = parseInt(document.form1.p_cntlevel2M1.value) + parseInt(document.form1.p_cntlevel2M2.value); 
	var s_cntlevel3 = parseInt(document.form1.p_cntlevel3M1.value) + parseInt(document.form1.p_cntlevel3M2.value); 

   document.form1.p_level1text.value = s_level1text;
   document.form1.p_level2text.value = s_level2text;
   document.form1.p_level3text.value = s_level3text;

   document.form1.p_cntlevel1.value = s_cntlevel1;
   document.form1.p_cntlevel2.value = s_cntlevel2;
   document.form1.p_cntlevel3.value = s_cntlevel3;
  
  return true;
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

<form name="form1" method="post" action="/servlet/controller.etest.ETestMasterServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_action"      value="">
    <input type="hidden" name="p_etestsubj"        value="<%=v_etestsubj%>">
    <input type="hidden" name="p_gyear"        value="<%=v_gyear%>">
    <input type="hidden" name="p_step"        value="">
    <input type="hidden" name="p_totalscore"        value="100">
    <input type="hidden" name="p_level1text"        value="">
    <input type="hidden" name="p_level2text"        value="">
    <input type="hidden" name="p_level3text"        value="">
    <input type="hidden" name="p_cntlevel1"        value="">
    <input type="hidden" name="p_cntlevel2"        value="">
    <input type="hidden" name="p_cntlevel3"        value="">
    <input type="hidden" name="p_startdt"        value="">
    <input type="hidden" name="p_enddt"        value="">
    <input type="hidden" name="p_reloadlist" value="">

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명 시작 ----------------->
      <!--<table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>그룹명 
            : </td>
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>-->
      <!----------------- 과정명 끝 ----------------->

      <!----------------- 평가마스터 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="16%" class="table_title">테스트명</td>
          <td width="81%" class="table_02_2"><input name="p_etesttext" type="text" class="input" size="50"></td>
        </tr>
        <tr> 
          <td class="table_title">시작시간</td>
          <td class="table_02_2">
            일자 <input name="p_startdate" id="p_startdate" type="text" class="datepicker_input1" size="10" value=""> &nbsp;
            시간
            <select name="p_starthour">
              <%=FormatDate.getDateOptions(0, 23)%>
            </select>
            &nbsp;분
            <select name="p_startminute">
              <%=FormatDate.getDateOptions(0, 59)%>
            </select>
          </td>
        </tr>
        <tr> 
          <td class="table_title">종료시간</td>
          <td class="table_02_2">
            일자 <input name="p_enddate" id="p_enddate" type="text" class="datepicker_input1" size="10" value=""> &nbsp;
            시간
            <select name="p_endhour">
              <%=FormatDate.getDateOptions(0, 23)%>
            </select>
            &nbsp;분
            <select name="p_endminute">
              <%=FormatDate.getDateOptions(0, 59)%>
            </select>
          </td>
        </tr>
        <tr> 
          <td class="table_title">시험시간</td>
          <td class="table_02_2"><input name="p_etesttime" type="text" class="input" size="5" value="0" maxlength="5">분</td>
        </tr>
		<tr> 
          <td class="table_title">정원</td>
          <td class="table_02_2"><input name="p_etestlimit" type="text" class="input" size="5" value="0" maxlength="5">명</td>
        </tr>
        <tr> 
          <td width="16%" class="table_title">문제당 배점</td>
          <td width="" class="table_02_2"><input name="p_etestpoint" type="text" class="input" size="5" value="0">점</td>
        </tr>
        <tr> 
          <td width="16%" class="table_title">문제수</td>
          <td class="table_02_2"><input name="p_etestcnt" type="text" class="input" size="5" value="0"></td>
        </tr>

<%
    ArrayList  typelist = (ArrayList)request.getAttribute("ETestLevelsData");
	ArrayList levellist0 = null;
	ArrayList levellist1 = null;
	DataBox dbox = null;

    String v_cntlevel1M1 = "";
    String v_cntlevel2M1 = "";
    String v_cntlevel3M1 = "";
    String v_cntlevel1M2 = "";
    String v_cntlevel2M2 = "";
    String v_cntlevel3M2 = "";

		levellist0 = (ArrayList)typelist.get(0);
		levellist1 = (ArrayList)typelist.get(1);

		dbox = (DataBox)levellist0.get(0);
        v_cntlevel1M1 = dbox.getString("d_levelscount");
		dbox = (DataBox)levellist0.get(1);
        v_cntlevel2M1 = dbox.getString("d_levelscount");
		dbox = (DataBox)levellist0.get(2);
        v_cntlevel3M1 = dbox.getString("d_levelscount");
		dbox = (DataBox)levellist1.get(0);
        v_cntlevel1M2 = dbox.getString("d_levelscount");
		dbox = (DataBox)levellist1.get(1);
        v_cntlevel2M2 = dbox.getString("d_levelscount");
		dbox = (DataBox)levellist1.get(2);
        v_cntlevel3M2 = dbox.getString("d_levelscount");

%>
        <tr>
          <td class="table_title">객관식</td>
          <td class="table_02_2">
		  상(<input name="p_cntlevel1M1" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel1M1%>)&nbsp;
		  중(<input name="p_cntlevel2M1" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel2M1%>)&nbsp;
		  하(<input name="p_cntlevel3M1" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel3M1%>)&nbsp;
		  <input type="hidden" name="v_cntlevel1M1"        value="<%=v_cntlevel1M1%>">
		  <input type="hidden" name="v_cntlevel2M1"        value="<%=v_cntlevel2M1%>">
		  <input type="hidden" name="v_cntlevel3M1"        value="<%=v_cntlevel3M1%>">
          </td>
        </tr>
        <tr>
          <td class="table_title">주관식</td>
          <td class="table_02_2"> 
		  상(<input name="p_cntlevel1M2" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel1M2%>)&nbsp;
		  중(<input name="p_cntlevel2M2" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel2M2%>)&nbsp;
		  하(<input name="p_cntlevel3M2" type="text" class="input" size="3" value="0">&nbsp;/ <%=v_cntlevel3M2%>)&nbsp;
		  <input type="hidden" name="v_cntlevel1M2"        value="<%=v_cntlevel1M2%>">
		  <input type="hidden" name="v_cntlevel2M2"        value="<%=v_cntlevel2M2%>">
		  <input type="hidden" name="v_cntlevel3M2"        value="<%=v_cntlevel3M2%>">
          </td>
        </tr>
		<tr>
          <!--td class="table_title">결과정답공개</td 0301 수정 jkh-->
          <td class="table_title">결과점수공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenanswer" type="radio" size="5" value="Y">&nbsp;예 
            <input name="p_isopenanswer" type="radio" size="5" value="N" checked>&nbsp;아니오 
          </td>
        </tr>
        <tr>
          <td class="table_title">결과해설공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenexp" type="radio" size="5" value="Y">&nbsp;예 
            <input name="p_isopenexp" type="radio" size="5" value="N" checked>&nbsp;아니오 
          </td>
        </tr>
        <tr>
          <td class="table_title">총응시횟수</td>
          <td class="table_02_2"> 
            <input name="p_retrynum" type="text" class="input" size="4" value="0">
          </td>
        </tr>
        <tr>
          <td class="table_title">시험지수</td>
          <td class="table_02_2"> 
            <input name="p_papercnt" type="text" class="input" size="4" value="0">
          </td>
        </tr>
      </table>
      <!----------------- 평가마스터 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><a href="javascript:InsertMaster()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td> 
        </tr>
      </table>
      <!-----------------  등록, 리스트 버튼 끝 ----------------->
      <br>
      <br>
      <br>
    <!--  <font color= blue>(분기식과정에 해당할 경우에는 분기와 분기 아닌 일차가 같은 평가범위에 있을 수 없습니다.)</font>   -->
    </td>
  </tr>
</table>

</form>  
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

