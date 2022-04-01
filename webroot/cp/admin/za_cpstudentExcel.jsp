<%
//**********************************************************
//  1. 제      목: 교육과정 입과인원 엑셀다운로드
//  2. 프로그램명: za_cpstudentExcel.jsp
//  3. 개      요: 교육과정 입과인원 엑셀다운로드
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.29
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>입과인원 엑셀 다운로드</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript">
<!--//
function whenSelection(arg){
	document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
	document.form1.p_process.value = "studentExcel";
	document.form1.submit();
}

function excelDown(gubun){
	document.form1.action = "/servlet/controller.cp.CPEduStudentServlet";
	document.form1.p_process.value = "studentExcelDown";
	document.form1.p_downgubun.value = gubun;
	document.form1.submit();
}
//-->
</script>
</head>

<body>
<form name="form1">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_downgubun" value="">
<table width="518" border="0" cellpadding="10" cellspacing="1" bgcolor="#999999" style="font-size:12px">
  <tr>
    <td bgcolor="#FFFFFF"><strong>엑셀 다운로드</strong><br>
      <br>
      <font color="#006699">교육시작일과 종료일을 조건으로 기간에 포함되는 모든 인원을 다운로드 할 수 있습니다.</font><br><br>
      <table width="100%" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr> 
          <td align="center">
			<table width="100%" cellpadding="0" cellspacing="0" class="form_table_bg">
		      <tr> 
		        <td height="7"></td>
		      </tr>
		      <tr> 
		        <td>
			      <table border="0" cellspacing="0" cellpadding="0">
			        <tr> 
			          <td width="10"></td>
			          <td><%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  --></td>
			          <td width="20"></td>
			          <td> <%=SelectCompanyBean.getGrcomp(box, false, true)%><!-- getGyear(RequestBox, isChange)   교육년도  --></td>
			          <td width="20"></td>
			          <td ><a href="javascript:whenSelection('go');"><img src="/images/admin/button/b_go.gif"  border="0"></a><br></td>
			        </tr>
			        <tr> 
			          <td height="5" colspan='5'></td>
			        </tr>
			        <tr>
			          <td></td>
			          <td>업체명 :&nbsp; <%= CPCommonBean.getCP(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    교육기관  -->&nbsp; </td>
			          <td></td>
			          <td colspan='3'> </td>
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
                  
      <table width="100%" border="0" cellpadding="5" cellspacing="1" bgcolor="#006699" style="font-size:12px">
        <tr bgcolor="#E8F8FF"> 
          <td width="20%"><div align="center">교육시작일</div></td>
          <td width="80%">
            <%= CPCommonBean.getYear(box, false, false, "2002", "p_syear")%>
            <select name="p_smon" size="1">
            <%for(int i=1;i<13;i++){%>
              <option value="<%=i%>"<%if(StringManager.toInt(FormatDate.getDate("MM")) == i){%> selected<%}%>><%=i%>월</option>
            <%}%>
            </select>
            <select name="p_sday" size="1">
            <%for(int i=1;i<32;i++){%>
              <option value="<%=i%>"<%if(StringManager.toInt(FormatDate.getDate("dd")) == i){%> selected<%}%>><%=i%>일</option>
            <%}%>
            </select>
            
          </td>
        </tr>
        <tr bgcolor="#E8F8FF"> 
          <td><div align="center">교육종료일</div></td>
          <td>
            <%= CPCommonBean.getYear(box, false, false, "2002", "p_eyear")%>
            <select name="p_emon" size="1">
            <%for(int i=1;i<13;i++){%>
              <option value="<%=i%>"<%if(StringManager.toInt(FormatDate.getDate("MM")) == i){%> selected<%}%>><%=i%>월</option>
            <%}%>
            </select>
            <select name="p_eday" size="1">
            <%for(int i=1;i<32;i++){%>
              <option value="<%=i%>"<%if(StringManager.toInt(FormatDate.getDate("dd")) == i){%> selected<%}%>><%=i%>일</option>
            <%}%>
            </select>
          </td>
        </tr>
        <tr bgcolor="#E8F8FF"> 
          <td>&nbsp; </td>
          <td><a href="javascript:excelDown('1')">[전체확정자]</a> <a href="javascript:excelDown('2')">[전체취소자]</a> <!--a href="javascript:excelDown('3')">[전체신청자]</a--></td>
        </tr>
      </table>
      <br>
      <font color="#CC0066">인원이 많을경우 다운로드 시간이 오래 지연될수 있으니 가급적 최소한의 필요한 기간을 입력하여 
      다운로드를 권장합니다.</font><br>
    </td>
  </tr>
</table>
</form>
</body>
</html>
