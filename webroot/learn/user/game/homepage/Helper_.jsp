

<%
//**********************************************************
//  1. 제      목: 홈페이지 교육문의
//  2. 프로그램명 : Helper.jsp
//  3. 개      요: 홈페이지 교육문의
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.21
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

%>

            <!--  교육문의 table start -->
            <TABLE style="TABLE-LAYOUT: fixed" cellSpacing=0 cellPadding=0 width=190>
            <tr>
            <td align="center">
            <a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage"><img src="/images/user/game/homepage/type1/right_qna.gif" border="0"></a></td>
            </tr>
            </table>
                <!--<TABLE style="TABLE-LAYOUT: fixed" cellSpacing=0 cellPadding=0 width=190>
                  <TR> 
                    <TD><IMG src="/images/user/game/homepage/type1/right_tel01.gif"></TD>
                  </TR>
                  <TR> 
                    <TD valign="top" background="/images/user/game/homepage/type1/right_tel01_bg.gif"><table width="190" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                          <td width="66" rowspan="2" valign="top"><img src="/images/user/game/homepage/type1/right_tel021.gif"></td>
                          <td width="124" height="23"><img src="/images/user/game/homepage/type1/right_tel_text.gif" align="absmiddle"> 
                            02)512-5682<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;02)540-8514 </td>
                        </tr>
                        <tr> 
                          <td><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage"><img src="/images/user/game/homepage/type1/right_admin.gif"></a></td>
                        </tr>
                      </table></TD>
                  </TR>
                  <TR> 
                    <TD height=12><IMG src="/images/user/game/homepage/type1/right_tel04.gif"></TD>
                  </TR>
				   <TR> 
                    <TD height=10></TD>
                  </TR>
                </TABLE>-->
                <!--  교육문의 table end -->