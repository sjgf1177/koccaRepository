<%
//**********************************************************
//  1. 제      목: 대분류예산설정
//  2. 프로그램명: za_ClassifyBudget_L.jsp
//  3. 개      요: 대분류예산설정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2005. 02. 22
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
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
	
	DataBox dbox = (DataBox)request.getAttribute("BudgetInfo"); //예산정보
	
    ArrayList list = (ArrayList)request.getAttribute("StudentList"); //수강신청현황

  //DEFINED in relation to select START
    String  s_grcode    = box.getString("grcode");           //교육그룹
    String  s_gyear     = box.getString("gryear");            //교육차수
    String  s_grseq     = box.getString("grseq");            //교육차수
    String  s_gubun     = box.getString("gubun");            //교육차수

//    if(s_grcode.equals("")) s_grcode  = box.getString("s_grcode");           //교육그룹
//    if(s_gyear.equals("")) s_gyear    = box.getString("s_gyear");            //교육차수
//    if(s_grseq.equals(""))  s_grseq   = box.getString("s_grseq");            //교육차수


%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="Javascript">
$(document).ready(function(){
	$("#p_propstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
// 예산설정 수정 저장	
	function whenSave() {
		document.form1.p_process.value= "saveInfo";
		document.form1.r_process.value= "selectListPage";
		document.form1.submit();
	}

    function whenList() {
        document.form1.target = "_self";
        document.form1.p_process.value = 'selectListPage';  
        document.form1.submit();
    }
-->
</script>

</head>

<body topmargin=0 leftmargin=0>
<form name="form1" method="post" action="/servlet/controller.system.ClassifyBudgetServlet">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="r_process" value="">
<input type="hidden" name="s_grcode"  value="<%=s_grcode%>">
<input type="hidden" name="s_gyear"   value="<%=s_gyear%>">
<input type="hidden" name="s_grseq"   value="<%=s_grseq%>">
<input type="hidden" name="s_gubun"   value="<%=s_gubun%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/sutitle_001.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>

	   <br>
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="15"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td>
						[교육그룹] : <b><%=GetCodenm.get_grcodenm(s_grcode)%></b> [교육차수] : <b><%=GetCodenm.get_grseqnm(s_grcode, s_gyear, s_grseq)%></b> [구분] : <b><%=CodeConfigBean.getCodeName("0049", s_gubun)%></b>
                      </td>
					  <td width="10%" align="center"><a href="javascript:whenList()"><img src="/images/admin/button/btn_list.gif" border="0" tabindex=73></a></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="15"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
	  <br>
      <!-------------소제목 시작-------------------------->
	  <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>예산설정 수정</td> 
          <td width="10%" align="right"><a href="javascript:whenSave()"><img src="/images/admin/button/btn_modify.gif" border="0" tabindex=73></a></td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
      <!--------------소제목 끝---------------------------->

	  <!----------------- 예산설정 시작 ----------------->      
        <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
          <tr> 
            <td width="15%" height="33" class="table_title">수강신청 기간</td>
            <td width="25%" colspan="3" class="table_02_2">
			  <input tpye="text" name="p_propstart" id="p_propstart" class="datepicker_input1" size=10 value="<%=(dbox!=null)?FormatDate.getFormatDate(dbox.getString("d_propstart"),"yyyy-MM-dd"):""%>">
              ~ 
              <input tpye="text" name="p_propend" id="p_propend" class="datepicker_input1" size=10 value="<%=(dbox!=null)?FormatDate.getFormatDate(dbox.getString("d_propend"),"yyyy-MM-dd"):""%>">
			</td>
            <td width="15%" height="37" class="table_title">예산설정금액</td>
            <td width="25%" colspan="3" class="table_02_2"><input type="text" name="p_budget" size="20"  maxlength="10" class="input" value="<%=(dbox!=null)?dbox.getString("d_budget"):""%>" onkeyup="numeric_chk(this)"> 원</td>
            <td width="10%" class="table_title">사용여부</td>
            <td width="10%" class="table_02_2">
<%if(dbox!=null) {%>
			  <select name="p_isuse" class="inputpsearch">
                <option value="Y" <%=(dbox.getString("d_isuse").equals("Y"))?"selected":""%>>Y</option>
                <option value="N" <%=(dbox.getString("d_isuse").equals("N"))?"selected":""%>>N</option>
              </select>
<%}else{%>
			  <select name="p_isuse" class="inputpsearch">
                <option value="Y">Y</option>
                <option value="N">N</option>
              </select>
<%}%>
		    </td>
          </tr>
        </table>
	  <!----------------- 예산설정 끝 ----------------->

      <br>
      <br>
<% if (dbox!=null && dbox.getString("d_isuse").equals("Y")) { %>
	 <!-------------소제목 시작-------------------------->
	 <table width="970" border="0" cellspacing="0" cellpadding="0">
       <tr> 
         <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
         <td class=sub_title>수강신청현황</td>
		 <td width="30%" align="right">
			<%  int v_sumprice = 0;
				if(list.size()>0){
					for (int i=0; i<list.size(); i++) {
						DataBox dbox2 = (DataBox)list.get(i); 
						v_sumprice += dbox2.getInt("d_sumbiyong");
					} 
				}
			%>								
		   <b>신청 총계 : <%=v_sumprice%>원</b>
		  </td>
       </tr>
       <tr> 
         <td height=6></td>
         <td height=6></td>
       </tr>
      </table> 
      <!--------------소제목 끝---------------------------->

	  <!----------------- 수강신청현황 시작 ----------------->
      <table width="575" border="0" cellspacing="1" cellpadding="0" class="table_out">
        <tr> 
          <td width="300" height="20" class="table_title">과정명</td>
          <td width="90" class="table_title">수강료</td>
          <td width="90" class="table_title">신청인원</td>
          <td width="90" class="table_title">금액</td>
        </tr>
	<%  
		if(list.size()>0){
			for (int i=0; i<list.size(); i++) {
			DataBox dbox2 = (DataBox)list.get(i); %>
        <tr> 
          <td class="table_02_2"><%=dbox2.getString("d_subjnm")%></td>
          <td class="table_02_1"><%=dbox2.getInt("d_avgbiyong")%></td>
          <td class="table_02_1"><%=dbox2.getInt("d_stdcnt")%></td>
          <td class="table_02_1"><%=StringManager.replace(dbox2.getString("d_sumbiyong"),".0","")%></td>
        </tr>
		<%	}
		}else { %>
		<tr><td colspan=4 class="table_02_1">수강신청중인 학습자가 없습니다.</td></tr>
		<% } %>
      </table>
<% } %>	  
      <!--------------수강신청현황 끝---------------------------->     
	 <br><br><br>
	 
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>

</body>
</html>
