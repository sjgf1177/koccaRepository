<%
//**********************************************************
//  1. 제      목: 학습제약조건
//  2. 프로그램명: za_EduTimeControl_L.jsp
//  3. 개      요: 학습제약조건
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: S.W.Kang 2005. 03. 02
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
	
	//DataBox dbox 	= (DataBox)request.getAttribute("BudgetInfo"); //예산정보
	
    //ArrayList list 	= (ArrayList)request.getAttribute("StudentList"); //수강신청현황
    
	String  v_comp 	= box.getStringDefault("p_comp",box.getSession("comp").substring(0,4)); //구분 : 0101(현대), 0102(기아)
	String  v_gubun = box.getStringDefault("p_gubun","E02"); //구분 : E01(직무), E02(어학), E03(경영특별)

%>
<html>
<head>
<title>예산설정</title>
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
	function whenSubmit() {
		document.form1.p_process.value="ListPage";
		document.form1.submit();
	}
	
	function whenSave() {
		if (document.form1.p_propstart.value > document.form1.p_propend.value) {
            alert("수강신청 시작일은 수가신청 종료일 이전이어야 합니다.");
            return ;
		}

		document.form1.p_process.value= "saveInfo";
		document.form1.submit();
	}
-->
</script>

</head>

<body topmargin=0 leftmargin=0>
	<form name="form1" method="post" action="/servlet/controller.system.ClassifyBudgetServlet">
	<input type="hidden" name="p_process" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
		<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        	<tr> 
          		<td>학습제약조건</td>
          		<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        	</tr>
      	</table>

	   	<br>
	   	<table width="970" cellpadding="1" cellspacing="0" class="form_table_out">
        	<tr> 
          		<td align="center">
          			<table width="970" cellpadding="0" cellspacing="0" class="form_table_bg">
              			<tr> 
                			<td height="7"></td>
              			</tr>
              			<tr>
                			<td>
                				<table  border="0" cellspacing="0" cellpadding="0">
                    				<tr>
                      					<td width=20>&nbsp;</td>
                      					<td  align="right">회사</td>
                      					<td> &nbsp; <select name="p_comp">
                          					<option value="0101" <%if (v_comp.equals("0101")) out.print("selected"); %>>현대자동차</option>
                          					<option value="0202" <%if (v_comp.equals("0202")) out.print("selected"); %>>기아자동차</option>
                        					</select> &nbsp; </td>
                      					<td width=10></td>
                      					<td  align="right">구분</td>
                      					<td> &nbsp; <select name="p_gubun">
                          					<option value="E01" <%if (v_gubun.equals("E01")) out.print("selected"); %>>직무</option>
                          					<option value="E02" <%if (v_gubun.equals("E02")) out.print("selected"); %>>외국어</option>
                          					<option value="E03" <%if (v_gubun.equals("E03")) out.print("selected"); %>>경영특별</option>
                        					</select> &nbsp; </td>
                      					<td width=10></td>
                      					<td><a href="javascript:whenSubmit();"><img src="/images/admin/button/b_go.gif"  border="0"></a></td>
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
	  	<br>
        <!-------------소제목 시작-------------------------->
 		<table width="970" border="0" cellspacing="0" cellpadding="0">
        	<tr> 
          		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          		<td class=sub_title>예산설정</td>
        	</tr>
        	<tr> 
          		<td height=6></td>
          		<td height=6></td>
        	</tr>
      	</table> 
        <!--------------소제목 끝---------------------------->
      
        <table width="970"  cellpadding="0" cellspacing="1" class="table_out">
          <tr> 
          	<td width="16%" height="33" class="table_title">학습제약 기간</td>
           	<td colspan="3" class="table_02_2"><input tpye="text" name="p_propstart" id="p_propstart" class="datepicker_input1" size=10 value="<%//=FormatDate.getFormatDate(dbox.getString("d_propstart"),"yyyy-MM-dd")%>">
              ~ 
              <input tpye="text" name="p_propend" id="p_propend" class="datepicker_input1" size=10 value="<%//=FormatDate.getFormatDate(dbox.getString("d_propend"),"yyyy-MM-dd")%>"></td>
          </tr>
          <tr> 
            <td height="30" class="table_title">대분류 코드</td>
            <td colspan="3" class="table_02_2"><//%=v_gubun%></td>
          </tr>
        </table>
      	<br>
        <!----------------- 등록, 리스트 버튼 시작 ----------------->
      	<table border="0" cellspacing="0" cellpadding="0">
        	<tr>
            	<td align="center"><a href="javascript:whenSave()"><img src="/images/admin/button/btn_save.gif" border="0" tabindex=73></a></td>
				<td width=8></td>

          	</tr>
        </table>
        <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>

</body>
</html>
