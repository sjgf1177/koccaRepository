<%
/**
 * file name : za_ExamMaster_I.jsp
 * date      : 2003/09/03
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
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

	String v_reloadlist = box.getString("p_reloadlist");

    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
    String  v_ctype    = box.getString("p_ctype");  // 콘텐츠타입
    String  v_subj       = box.getString("p_subj");

%>
<html>
<head>
<title>평가마스터등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--

// 등록
function InsertNext() {
  if (!chkData()) {
    return;
  }
  document.form1.p_process.value = 'ExamMasterInsertNextPage';
  document.form1.p_step.value  = '2';
  document.form1.submit();
}

// 등록 여부 체크
function chkData() {
  //var v_examtime = parseInt(document.form1.p_examtime.value);
  //if (v_examtime < 1) {
  //  document.form1.p_examtime.focus();
  //  alert('시험시간을 입력해 주십시요.');
  //  return false;
  //}

  /*
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
  */
  
  if (document.form1.p_lesson.value.length < 1) {
    alert('과정의 차시가 등록되어 있지 않습니다.');
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
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index:99">
</div>

<form name="form1" method="post" action="/servlet/controller.exam.ExamMasterServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_action"      value="">
    <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
    <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
    <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
    <input type="hidden" name="p_subj"        value="<%=v_subj%>">
    <input type="hidden" name="p_step"        value="">
    <input type="hidden" name="p_startdt"        value="">
    <input type="hidden" name="p_enddt"        value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/exam/e_title02.gif" ></td>
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
            : <%=GetCodenm.get_subjnm(v_subj)%>(<%=v_subj%>)</td>
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->

      <!----------------- 평가마스터 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>

        <%if(v_ctype.equals("Y")){%>
        <tr> 
          <td width="16%" class="table_title">평가타입</td>
          <td width="81%" class="table_02_2">
<SELECT name=p_examtype  > 
 <option value=H>형성평가</option> 
 <option value=E>최종평가</option> 
 <!--<option value=M>중간평가</option> -->
  </SELECT>           
          </td>
        </tr>        
        <tr> 
          <td class="table_title">평가일차</td>
          <td class="table_02_2">
<SELECT name=p_lesson  > 
 <option value=001>001</option> 
 <option value=002>002</option> 
  </SELECT>            
          &nbsp;&nbsp;<font color="red">* [예스런타입]형성평가는 001, 최종평가는 002 입니다.</font>
          </td>
        </tr>
        <tr> 
          <td  class="table_title">평가범위</td>
          <td class="table_02_2">
<SELECT name=p_lessonstart  > 
 <option value=001>001</option> 
 <option value=002>002</option> 
  </SELECT>  ~ 
<SELECT name=p_lessonend  > 
 <option value=001>001</option> 
 <option value=002>002</option> 
  </SELECT>              
&nbsp;&nbsp;<font color="red">* [예스런타입]형성평가는 001~001, 최종평가는 002~002 입니다.</font>                       
          </td>
        </tr>        
        <%}else{%>
        <tr> 
          <td width="16%" class="table_title">평가타입</td>
          <td width="81%" class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect(ExamBean.PTYPE,"", 1, "p_examtype", "", "", 0)%></td>
        </tr>        
        <tr> 
          <td class="table_title">평가일차</td>
          <td class="table_02_2"><%=SelectBoxBean.get_SelectSubjLesson("p_lesson", "", v_subj)%> &nbsp;&nbsp;<font color="red">* 하나의 평가일차에 동일한 평가타입이 존재할 수 없으므로 주의하여 설정하시기 바랍니다.</font></td>
        </tr>
        <tr> 
          <td  class="table_title">평가범위</td>
          <td class="table_02_2"><%=SelectBoxBean.get_SelectSubjLesson("p_lessonstart", "", v_subj)%> ~ <%=SelectBoxBean.get_SelectSubjLesson("p_lessonend", "", v_subj)%></td>
        </tr>
        <%}%>

        <!--tr> 
          <td class="table_title">시작시간</td>
          <td class="table_02_2">
            일자 <input name="p_startdate" type="text" class="input" size="10" value="" OnClick="MiniCal(this)" readonly> &nbsp;
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
            일자 <input name="p_enddate" type="text" class="input" size="10" value="" OnClick="MiniCal(this)" readonly> &nbsp;
            시간
            <select name="p_endhour">
              <%=FormatDate.getDateOptions(0, 23)%>
            </select>
            &nbsp;분
            <select name="p_endminute">
              <%=FormatDate.getDateOptions(0, 59)%>
            </select>
          </td>
        </tr-->
        <tr>
          <td class="table_title">결과점수공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenanswer" type="radio" size="5" value="Y" checked>&nbsp;예 
            <input name="p_isopenanswer" type="radio" size="5" value="N">&nbsp;아니오 
          </td>
        </tr>
        <tr>
          <td class="table_title">결과정답(해설)공개</td>
          <td class="table_02_2"> 
            <input name="p_isopenexp" type="radio" size="5" value="Y" checked>&nbsp;예 
            <input name="p_isopenexp" type="radio" size="5" value="N">&nbsp;아니오 
          </td>
        </tr>
        <!--<tr>
          <td class="table_title">시험시간</td>
          <td class="table_02_2"> 
            <input name="p_examtime" type="text" class="input" size="4" value="0">분
          </td>
        </tr>-->
         <input name="p_examtime" type="hidden" class="input" size="4" value="0">
        <tr>
          <td class="table_title">재응시</td>
          <td class="table_02_2"><input name="p_retrycnt" type="text" class="input" size="3" value="0">회
		  </td>
        </tr>
        <tr>
          <td class="table_title">제한진도율</td>
          <td class="table_02_2"><input name="p_progress" type="text" class="input" size="3" value="0" maxlength="3">%
		  </td>
        </tr>
      </table>
      <!----------------- 평가마스터 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td ><a href="javascript:InsertNext()"><img src="/images/admin/button/btn_next.gif"  border="0"></a></td>
          <td width=8></td>
		  <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
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

