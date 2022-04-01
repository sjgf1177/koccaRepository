<%
//**********************************************************
//  1. 제      목: ID 찾기 결과
//  2. 프로그램명 : zu_FindId_R.jsp
//  3. 개      요: ID 찾기 결과
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>


<%@page import="java.util.List"%><jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","1");
	box.put("submenu","3");

    String v_name   = box.getString("p_name");
    List<DataBox> useridlist = (List<DataBox>)request.getAttribute("userid");
    int listsize = useridlist.size();
    String v_userid = "";
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--//

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- form 시작 -->
<form name="form1"  action="" method="post" action="">
	<input type="hidden"	name="p_process"	value="" />	
	
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/member_idpass.gif" alt="회원정보변경"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>아이디/비밀번호 찾기</u></td>
			</tr>
		</table>
		<div class="idpass">
			<div class="idfinish">
				<% if (listsize > 0) { %>
				<h6>회원님의 아이디는 
				<%
					for(int i = 0 ; i < listsize ; i ++){
            	  	DataBox dbox = (DataBox)useridlist.get(i);
              		v_userid = dbox.getString("d_userid");
              	%>   
				<span><strong><%=v_userid %></strong></span>&nbsp;
				<%} %>
				입니다.</h6>
				<%	} else {%>
				<h6>입력하신 정보와 일치하는 정보가 없습니다</h6>
				<% } %>
				<br>
				<button type="button" class="btn_nomal_white" onclick="history.go(-1)" title="이전 화면으로 돌아갑니다.">뒤로가기</button>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
