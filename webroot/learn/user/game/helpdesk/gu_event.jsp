<%
//**********************************************************
//  1. 제      목: HelpDesk > 학습환경도우미
//  2. 프로그램명 : gu_event.jsp
//  3. 개      요: 학습환경도우미 (인터넷)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","05"); 

    String  v_process     = box.getString("p_process");
                  
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topEvent.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->



<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/helpdesk/title_event.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 이벤트</td>
  </tr>
  <tr> 
    <td align="center" style="padding-top:20;padding-bottom:20">
    	<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td><img src="http://game.connect.or.kr/news/1217_board/event.png"></td>
				</tr>
				<tr>
					<td align="right" style="padding-top:10;padding-right:10">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td><a href='#' onfocus="this.blur();" onClick="JavaScript:window.open('http://game.connect.or.kr/servlet/controller.course.PostScriptServlet?p_process=allList','F_POPUP', 'status=0,scrollbars=yes,resizable=0,width=720, height=600');"><img src="http://game.connect.or.kr/news/1217_board/board_list.png" border="0"></a> <a href='#' onfocus="this.blur();" onClick="JavaScript:window.open('http://game.connect.or.kr/servlet/controller.course.PostScriptServlet?p_process=myList','F_POPUP', 'status=0,scrollbars=yes,resizable=0,width=720, height=600');"><img src="http://game.connect.or.kr/news/1217_board/board_write.png" border="0"></a></td>
							</tr>
						</table>	
					</td>
				</tr>
			</table>
    </td>
  </tr>
</table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
