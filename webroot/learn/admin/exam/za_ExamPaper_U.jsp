<%
/**
 * file name : za_ExamPaper_U.jsp
 * date      : 2003/09/03
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
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	String v_reloadlist = box.getString("p_reloadlist");
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스

    String  v_subj       = box.getString("p_subj");
    String  v_gyear     = box.getString("p_gyear");
    String  v_subjseq     = box.getString("p_subjseq");
	String  v_subjseqgr     = box.getString("p_subjseqgr");
	String  v_lesson     = box.getString("p_lesson");
    String  v_examtype      = box.getString("p_examtype");
    String  v_ingstate      = box.getString("p_ingstate");
    int  v_papernum      = box.getInt("p_papernum");

	boolean v_isResult = ((Boolean)request.getAttribute("isExamResult")).booleanValue();

%>
<html>
<head>
<title>평가문제지 수정</title>
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
function UpdateNext() {
  if (!chkData()) {
    return;
  }
  document.form1.p_process.value = 'ExamPaperUpdateNextPage';
  document.form1.p_step.value  = '2';
  document.form1.submit();
}
function DeletePaper() {
  //if (confirm("평가문제지를 삭제하시면 해당과정차수의 모든 평가문제지가 삭제됩니다. \n그래도 삭제하시겠습니까?")) {
  if (confirm("현재 선택한 문제지가 삭제됩니다. \n그래도 삭제하시겠습니까?")) {
    document.form1.p_process.value = 'ExamPaperDelete';
    document.form1.p_reloadlist.value = 'true';
    document.form1.p_action.value = "go";
    document.form1.submit();
  }
}
function chkData() {


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

<form name="form1" method="post" action="/servlet/controller.exam.ExamPaperServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_action"      value="">
    <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
    <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
    <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
    <input type="hidden" name="p_subj"        value="<%=v_subj%>">
    <input type="hidden" name="p_lesson"      value="<%=v_lesson%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>">
	<input type="hidden" name="p_subjseqgr"      value="<%=v_subjseqgr%>">
    <input type="hidden" name="p_examtype"       value="<%=v_examtype%>">
    <input type="hidden" name="p_papernum"       value="<%=v_papernum%>">
    <input type="hidden" name="p_reloadlist" value="">
    <input type="hidden" name="p_step"        value="">
	<input type="hidden" name="p_isResult"        value="<%=v_isResult%>">
    <input type="hidden" name="p_startdt"        value="">
    <input type="hidden" name="p_enddt"        value="">


<%
	DataBox dbox = (DataBox)request.getAttribute("ExamPaperData");	
%>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/exam/e_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
		 <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
          <td class=sub_title>과정명 
            : <%=GetCodenm.get_subjnm(v_subj)%>(<%=v_subj%>)&nbsp;&nbsp;&nbsp;&nbsp;과정차수 : <%=StringManager.cutZero(v_subjseqgr)%>차</td>
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->

      <!----------------- 평가마스터 수정 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="16%" class="table_title">평가타입</td>
          <td width="81%" class="table_02_2"><%=dbox.getString("d_examtypenm")%></td>
        </tr>
        <tr> 
          <td class="table_title">평가일차</td>
          <td class="table_02_2"><%=dbox.getString("d_lesson")%></td>
        </tr>
        <tr> 
          <td class="table_title">평가범위</td>
          <td class="table_02_2"><%=dbox.getString("d_lessonstart")%> ~ <%=dbox.getString("d_lessonend")%></td>
		  <input type="hidden" name="p_lessonstart"       value="<%=dbox.getString("d_lessonstart")%>">
          <input type="hidden" name="p_lessonend"       value="<%=dbox.getString("d_lessonend")%>">
        </tr>
        <!--tr> 
          <td class="table_title">평가시작시간</td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy/MM/dd HH:mm")%></td>
        </tr>
        <tr> 
          <td class="table_title">평가종료시간</td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy/MM/dd HH:mm")%></td>
        </tr-->
        
        <% 
    String v_startdate   = "";  
    String v_starthour   = "";  
    String v_startminute = "";  
    if (dbox.getString("d_startdt").length() >= 8) {
        v_startdate = dbox.getString("d_startdt").substring(0,8);
        v_startdate = FormatDate.getFormatDate(v_startdate,"yyyy-MM-dd");
    } 
    if (dbox.getString("d_startdt").length() >= 10) {
        v_starthour = dbox.getString("d_startdt").substring(8,10);
    } 
    if (dbox.getString("d_startdt").length() >= 12) {
        v_startminute = dbox.getString("d_startdt").substring(10,12);
    } 

	System.out.println("d_startdt:"+dbox.getString("d_startdt"));
%>
        <tr> 
          <td class="table_title">시작시간</td>
          <td class="table_02_2">
            일자 <input <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_startdate" id="p_startdate" type="text" class="datepicker_input1" size="10" value="<%=v_startdate%>" value=""> &nbsp;
            시간
            <select <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_starthour">
              <%=FormatDate.getDateOptions(0, 23, StringManager.toInt(v_starthour))%>
            </select>
            &nbsp;분
            <select <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_startminute">
              <%=FormatDate.getDateOptions(0, 59, StringManager.toInt(v_startminute))%>
            </select>
          </td>
        </tr>
<% 
    String v_enddate   = "";  
    String v_endhour   = "";  
    String v_endminute = "";  
    if (dbox.getString("d_enddt").length() >= 8) {
        v_enddate = dbox.getString("d_enddt").substring(0,8);
        v_enddate = FormatDate.getFormatDate(v_enddate,"yyyy-MM-dd");
    } 
    if (dbox.getString("d_enddt").length() >= 10) {
        v_endhour = dbox.getString("d_enddt").substring(8,10);
    } 
    if (dbox.getString("d_enddt").length() >= 12) {
        v_endminute = dbox.getString("d_enddt").substring(10,12);
    } 
%>
        <tr> 
          <td class="table_title">종료시간</td>
          <td class="table_02_2">
            일자 <input <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_enddate" id="p_enddate" type="text" class="datepicker_input1" size="10" value="<%=v_enddate%>"> &nbsp;
            시간
            <select <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_endhour">
              <%=FormatDate.getDateOptions(0, 23, StringManager.toInt(v_endhour))%>
            </select>
            &nbsp;분
            <select <%if("3".equals(v_ingstate)){%>readonly<%}%> name="p_endminute">
              <%=FormatDate.getDateOptions(0, 59, StringManager.toInt(v_endminute))%>
            </select>
          </td>
        </tr>
        
        <tr>
          <td class="table_title">결과점수공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenanswer" type="radio" size="5" value="Y" <% if(dbox.getString("d_isopenanswer").equals("Y")) { %>  checked<% } %>>&nbsp;예 
            <input name="p_isopenanswer" type="radio" size="5" value="N" <% if(dbox.getString("d_isopenanswer").equals("N")) { %>  checked<% } %>>&nbsp;아니오
          </td>
        </tr>
        <tr>
          <td class="table_title">결과정답(해설)공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenexp" type="radio" size="5" value="Y" <% if(dbox.getString("d_isopenexp").equals("Y")) { %>  checked<% } %>>&nbsp;예 
            <input name="p_isopenexp" type="radio" size="5" value="N" <% if(dbox.getString("d_isopenexp").equals("N")) { %>  checked<% } %>>&nbsp;아니오 
          </td>
        </tr>
        <!--<tr>
          <td class="table_title">시험시간</td>
          <td class="table_02_2"> 
            <input name="p_examtime" type="text" class="input" size="4" value="<%=dbox.getString("d_examtime")%>">분
          </td>
        </tr>-->
		<input name="p_examtime" type="hidden" class="input" size="4" value="0">
        <tr>
          <td class="table_title">재응시</td>
          <td class="table_02_2"> 
		    <input name="p_retrycnt" type="text" class="input" size="3" value="<%=dbox.getInt("d_retrycnt")%>">회
          </td>
        </tr>
        <tr>
          <td class="table_title">제한진도율</td>
          <td class="table_02_2"><input name="p_progress" type="text" class="input" size="3" value="<%=dbox.getInt("d_progress")%>" maxlength="3">%
		  </td>
        </tr>
      </table>
      <!----------------- 평가마스터 수정 끝 ----------------->
      <br>
      <!----------------- 수정, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><a href="javascript:UpdateNext()"><img src="/images/admin/button/btn_next.gif"  border="0"></a></td>
<%
		//if(!v_isResult) {	 %>
			<td width=8></td>
            <td><a href="javascript:DeletePaper()"><img src="/images/admin/button/btn_del.gif"  border="0"></a></td>
<%    //}	%>
			<td width=8></td>
           <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
        </tr>
      </table>
      <!-----------------  수정, 리스트 버튼 끝 ----------------->
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

