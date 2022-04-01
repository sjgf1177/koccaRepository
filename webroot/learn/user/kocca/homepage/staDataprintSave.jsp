<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<%@ page import com.credu.homepage.* %>
<%@ page import com.credu.common.* %>
<%@ page import com.credu.system.* %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<title>통계자료 출력</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<script language="JavaScript">
<!--
	function getQuery() {
		var frm = document.frmList;

		if(frm.pDataQuery.value.toUpperCase().indexOf("INSERT ") >= 0) {
			alert("INSERT문은 사용할 수 없습니다.");
			return;

		} else if(frm.pDataQuery.value.toUpperCase().indexOf("UPDATE ") >= 0) {
			alert("UPDATE문은 사용할 수 없습니다.");
			return;

		} else if(frm.pDataQuery.value.toUpperCase().indexOf("DELETE ") >= 0) {
			alert("DELETE문은 사용할 수 없습니다.");
			return;

		} else if(frm.pDataQuery.value.toUpperCase().indexOf("SELECT") != 0) {
			alert("반드시 SELECT문으로 시작해야 합니다.");
			return;

		} else {
			frm.submit();
		}

		function checkCode() {
			var key = String.fromCharCode(event.keyCode);
			var re = new RegExp('[0-9]');

			if(!re.test(key)) {
				event.returnValue = false;
			}
		}
		
	}
//-->
</script>

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="30" topmargin="30" rightmargin="0" marginwidth="11" marginheight="30">

<table border="0" cellspacing="0" cellpadding="0">		
	<tr>
		<td>	
			<!-----타이틀----->
   	        <table border=0 cellspacing=0 cellpadding=0 class="TABLE70B">
   	        	<tr height=2>
   	            	<td colspan=7 class="TLINE"></td>
   	            </tr>
   	            <tr height=23>
   	            	<td width=1 class="TLINE"></td>
   	                <td width=100 class="TITNEW"></td>
   	                <td width=5 bgcolor=#FFFFFF></td>
   	                <td align=center bgcolor=#FFFFFF>
						<font color="#003366"><b>통계자료 출력</b></font></td>
   	                <td width=5 bgcolor=#FFFFFF></td>
   	                <td width=100 class="TITNEW"></td>
   	                <td width=1 class="TLINE"></td>
   	            </tr>
   	            <tr height=4>
   	                <td colspan=7 class="TLINE"></td>
   	            </tr>
   	        </table><br><br>
			<!-----타이틀 끝----->
           	
<form name=frmList method=post action="staDataprintList.jsp">

			<!--------검색-------->
			<table border="0" cellspacing="1" cellpadding="0" class="TABLE70B">
				<tr>
					<td colspan="2"></td>
				</tr>
               	<tr height=30 class="LISTNEW">
                   	<td class="SUB12L">설명</td>
                   	<td class="SUB58R" align="left">
						<font color="red">
						* SELECT 문만 실행가능합니다. INSERT, UPDATE, DELETE문은 실행할 수 없습니다.<br>
						* '컬럼 갯수'를 반드시 입력하셔야 합니다.<br>
						* SELECT할 컬럼 수와 '컬럼 갯수'는 반드시 일치해야 합니다.<br>
						* 쿼리문을 잘못된 경우 데이터베이스에 LOCK이 걸릴 수 있으므로 주의하시기 바랍니다.<br>
						* 많은 데이타를 가져오는 경우 시간이 오래 걸릴 수 있습니다.
						</font>  	                            	
                   	</td>
               	</tr>             
               	<tr>
					<td colspan="2"></td>
				</tr>
			</table><br><br>
          	<!--------검색끝-------->

			<!--------본문-------->			
			<table border="0" cellspacing="1" cellpadding="0" class="TABLE70B">
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr height="25">
					<td class="SUB12L">컬럼 갯수</td>
					<td class="SUB58R" align=left><input type=text name="pColumnCount" value="1" class="COL_NUM" maxlength="3" onKeyPress="javascript:checkCode();"></td>
				</tr>

				<tr>
					<td class="SUB12L">쿼리문</td>
					<td class="SUB58R"><textarea name=pDataQuery rows=15 cols=75>SELECT</textarea></td>
				</tr>	
				<tr>
					<td colspan="2"></td>
				</tr>				
			</table>
           	<!--------본문끝-------->
            <br><br>

            <!----- 버튼 ----->
			<table border="0" cellspacing="0" cellpadding="0" class="TABLE70F">
				<tr height=25>
					<td align="right">
			            <a href="javascript:getQuery();"> 실행 </a>
			    	</td>
				</tr>
			</table>    	
          	<!-----버튼 끝 ----->
            
		</td>
	</tr>
</table>

</form>

</body>
</html>

