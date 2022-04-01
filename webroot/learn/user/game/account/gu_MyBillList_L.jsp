<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.account.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process      = box.getString("p_process");
    
    ArrayList list = (ArrayList)request.getAttribute("MyBillList");
    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
    function detail() {
        farwindow = window.open("", "openWin", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
        document.topdefaultForm.target = "open"
        document.topdefaultForm.action = "/servlet/controller.account.AccountManagerServlet";
        document.topdefaultForm.submit();

        farwindow.window.focus();
    }    

//-->
</SCRIPT>



	<!-- title -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td width="720" height="35" align="right"  background="/images/user/game/account/tit_state.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
		  HOME > 수강신청 > 결제현황</td>
	  </tr>
	  <tr> 
		<td height="20"></td>
	  </tr>
	</table>
	
	
	<!-- 결제현황 시작  -->

	<table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="5"><img src="/images/user/game/account/t_p01.gif" width="192" height="31"></td>
          </tr>
    </table>
	<table width="720" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
	  <tr  class="lcolor"> 
		<td height="3" colspan="8" class="linecolor_my"></td>
	  </tr>
		  <tr> 
			<td class="tbl_gtit2">NO</td>
			<td class="tbl_gtit2">구분</td>
			<td class="tbl_gtit2">과정명</td>
			<td class="tbl_gtit2">결제금액</td>
			<td class="tbl_gtit2">결제방법</td>
			<td class="tbl_gtit2">결제일</td>
			<td class="tbl_gtit2">환불여부</td>
			<td class="tbl_gtit2">환불일</td>
		  </tr>
	  </tr>
	  <% for (int i=0; i<list.size(); i++) { 
	  		DataBox dbox = (DataBox)list.get(i); 
	  		
	  		String v_gubunnm 		= dbox.getString("d_gubunnm");
	  		String v_goodname 		= dbox.getString("d_goodname");
	  		int    v_price			= dbox.getInt("d_price");
	  		String v_paymethodnm 	= dbox.getString("d_paymethodnm");
	  		String v_pgauthdate 	= dbox.getString("d_pgauthdate");
	  		String v_cancelyn 		= dbox.getString("d_cancelyn");
	  		String v_cancelresult 	= dbox.getString("d_cancelresult");
	  		String v_canceldate 	= dbox.getString("d_canceldate");
	  		
	  %>
	  <tr> 
		<td class="tbl_grc"><%=i+1%></td>
		<td class="tbl_grc"><%=v_gubunnm%></td>
		<td class="tbl_gleft"><%=v_goodname%></td>
		<td align="right" style="padding-right:10px;"><%=new java.text.DecimalFormat("###,###,##0").format(v_price)%></td>
		<td class="tbl_grc"><%=v_paymethodnm%></td>
		<td class="tbl_grc"><%=FormatDate.getFormatDate(v_pgauthdate,"yyyy/MM/dd")%></td>
		<td class="tbl_grc"><%=v_cancelyn%></td>
		<td class="tbl_grc"><%=FormatDate.getFormatDate(v_canceldate,"yyyy/MM/dd")%></td>
	  </tr>
	  <% } %>
	
	  <% if (list.size()==0) { %>
	  <tr>
 		<td colspan=8 align=center>등록된 내용이 없습니다.</td>
	  </tr>
	  <% } %>

	</table>
	
	<!-- 결제현황 끝 -->
	
	<br>
	

	

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->