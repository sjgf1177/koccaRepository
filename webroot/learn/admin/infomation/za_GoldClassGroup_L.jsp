<%
//**********************************************************
//  1. 제      목: 열린강좌 일괄관리
//  2. 프로그램명 : za_GoldClassGroup_L.jsp
//  3. 개      요: 열린강좌 일괄관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016.12.07
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.PageUtil" %>
<%@ page import="com.credu.library.StringManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    String v_grcode = "";
    String v_grcodenm = "";
    String v_domain = "";
    int v_goldclassCnt = 0;

    DataBox dbox = null;
    ArrayList<DataBox> list = (ArrayList<DataBox>)request.getAttribute("list");
%>
<html>
<head>
<title>열린강좌 운영관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<script type="text/javascript">
    $(document).ready( function() {

    });
    
    
    /**
    * 강좌관리 페이지로 이동
    */
    function fnInsertPage(grcode){
    	$('#p_grcode').val(grcode);
    	$('#p_process').val('insertPage');
    	$("#form1").attr({
    		action : '/servlet/controller.infomation.GoldClassGroupServlet',
    		target : '_self'
    	}).submit();
    }
    
    /**
    * 엑셀 업로드 페이지 이동
    */
    function excelUpload(){
    	$('#p_process').val('uploadPage');
    	$("#form1").attr({
    		action : '/servlet/controller.infomation.GoldClassGroupServlet',
    		target : '_self'
    	}).submit();
    }

</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<form id="form1" name="form1" >
    <input type="hidden" id="p_process" name="p_process" value="<%= v_process %>" />
    <input type="hidden" id="p_grcode" name="p_grcode" value="" />


	<table width="1000" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td align="center" valign="top">
	            <!-- title 시작 //-->
	            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
	                <tr>
	                    <td><img src="/images/admin/portal/s.1_19.gif" /></td>
	                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
	                </tr>
	            </table>
	            <!-- title 끝 //-->
	
	            <br />
	            <!-- form 시작 //-->
	            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
	                <tr>
	                    <td width="240" align="right" valign="middle">
	                        <a href="javascript:excelUpload()"><img src="/images/admin/button/btn_fileplus.gif" alt="파일로추가" /></a>
	                    </td>
	                </tr>
	                <tr>
	                    <td height="3"></td>
	                </tr>
	            </table>
	            <!-- form 끝 //-->
	
	
	            <!-- 열린강좌 관리 시작 //-->
	            <table class="table_out" cellspacing="1" cellpadding="5">
	                <tr>
	                    <td colspan="7" class="table_top_line"></td>
	                </tr>
	                <tr>
	                    <td class="table_title" width="10%"><b>그룹코드</b></td>
	                    <td class="table_title"><b>교육그룹</b></td>
	                    <td class="table_title" width="30%"><b>도메인</b></td>
	                    <td class="table_title" width="10%"><b>사용강좌</b></td>
	                    <td class="table_title" width="10%"><b>강좌관리</b></td>
	                </tr>
					<%
		            for(int i = 0; i < list.size(); i++) {
		
		                dbox = (DataBox)list.get(i);
						v_grcode = dbox.getString("d_grcode");
						v_grcodenm = dbox.getString("d_grcodenm");
						v_domain = dbox.getString("d_domain");
						v_goldclassCnt = dbox.getInt("d_goldclass_cnt");
					%>
						<tr>
							<td class="table_02_1"><%=v_grcode%></td>
							<td class="table_02_2"><%=v_grcodenm%></td>
							<td class="table_02_2"><a href="<%=v_domain%>" target="blank"><%=v_domain%></a></td>
							<td class="table_02_1"><%=v_goldclassCnt%></td>
							<td class="table_02_1">
								<button class="btn_basic" onclick="fnInsertPage('<%=v_grcode%>');">강좌관리</button>
							</td>
						</tr>
					<%
		        	}
					%>
	            </table>
	            <!-- 열린강좌 목록 끝 //-->
	
	            <br />
	        </td>
	    </tr>
	</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
