
<%
//**********************************************************
//  1. 제      목: 인기과정, 개설예정과정
//  2. 프로그램명 : PopCourse.jsp
//  3. 개      요: 인기과정, 개설예정과정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.21
//  7. 수      정:
//***********************************************************
%>

<table cellpadding="0" cellspacing="0" width="536">
		<col width=""><col width="24"><col width="">
		<tr valign="top">
		<td>
		<!-- 인기과정 -->
            <jsp:include page="/learn/user/game/homepage/PopCourse.jsp" flush="true" />
		<!-- //인기과정 -->
		</td>
		<td></td>
		<td>
		<!-- 개설예정과정 -->
            <jsp:include page="/learn/user/game/homepage/NewCourse.jsp" flush="true" />
		<!-- //개설예정과정 -->
		</td>
		</tr>
		<tr height="20"><td colspan="3"></td></tr>
		</table>