<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String  v_action    = box.getString("p_action");

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "blue";
%>
<html>
<head>
<title>나이별 학습자 경로 분석</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<script language="JavaScript">
<!--


// 엑셀출력
function goExcel(p_action) {
    window.self.name = "JobDetailExcelList";
    open_window("openExcelList","","100","100","700","400","yes","yes","yes","yes","yes");  
    document.form1.target = "openExcelList";
    document.form1.action='/servlet/controller.analysis.InclinationAdminServlet';
    document.form1.p_process.value = 'jobDetailExcelList';
    document.form1.submit();
}

// 다른 탭 이동
function changeTabpage(p_gubun) {

    if(p_gubun == "CARRER") { 
		document.form1.p_process.value = "carrerRouteList";    
	} else if(p_gubun == "GENDER") {
		document.form1.p_process.value = "genderRouteList";  
	} else if(p_gubun == "AGE"){
		document.form1.p_process.value = "ageRouteList";  
	} else if(p_gubun == "JOB"){
		document.form1.p_process.value = "jobRouteList"; 
	}

    document.form1.target = "_self";	
	document.form1.action = "/servlet/controller.analysis.InclinationAdminServlet";
	document.form1.p_action.value = 'change';
	//document.form1.p_gubun.value = p_gubun;
    //document.form1.s_grcode.value = "";
	document.form1.submit();
}

-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.analysis.InclinationAdminServlet">
	<input type="hidden" name="p_process"  value="">
	<input type="hidden" name="p_action"   value="<%=v_action%>">


<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/analysis/tit_analysis_stu1.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

	<!--------------------닫기 버튼 시작---------------------------->
  	  <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
	    <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class=sub_title>
		  학습자 경로정보</td>
		  
		  <td align="right">
			<a href="javascript:javascript:self.close()"><img src="/images/user/kocca/button/btn_close.gif" border="0"></a>
		  </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
	  </table>
	<!---------------------닫기 버튼 끝------------------------------>

        <!----------------- FORM 시작 ----------------->
	   <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>교육그룹</strong></td>
            <td height="25" class="table_02_2">없음3</td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>과정명</strong></td>
            <td height="25" class="table_02_2">없음3</td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>직업</strong></td>
            <td height="25" class="table_02_2">없음3</td>
          </tr>
        </table>
        <!----------------- FORM 끝 ----------------->
        <br>
		<br>


      <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
		  <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('JOB')" class="c">직업별</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td> 
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('CARRER')" class="c">학력별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('GENDER')" class="c">성별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
		  <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_left"></td>
                <td class="<%=v_tab_color5%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_middle">나이별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
	      <td>&nbsp;</td>
        </tr>
      </table>

  	  <table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			<td colspan="11" class="table_top_line"></td>
		  </tr>

		  <tr>
			<td class="table_title" width="20%">직업</td>
			<td class="table_title" width="20%">인원</td>
		  </tr>

	  </table>
	 


	  </table>
	<!---------------------리스트 끝------------------------------>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</form>                  
</body>
</html>