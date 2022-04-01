
<%
//**********************************************************
//  1. 제      목: 홈페이지 공지사항
//  2. 프로그램명 : CenterB.jsp
//  3. 개      요: 홈페이지 공지사항
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>					   
						

                        <table width="460" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="223" valign="top">
							<!-- 인기강좌 -->
							<jsp:include page="/learn/user/kocca/homepage/Good_course.jsp" flush="true" />
							</td>
                            <td width="14">&nbsp;</td>
                            <td width="223" valign="top">
							<!-- 회원포럼 -->
							<jsp:include page="/learn/user/kocca/homepage/Forum.jsp" flush="true" />
							</td>
                          </tr>
                        </table>