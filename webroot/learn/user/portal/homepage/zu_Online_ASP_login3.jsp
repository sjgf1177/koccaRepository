<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<table width="690px" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td>
        	<!--탑이미지영역 시작--->
			<%@ include file="/learn/user/portal/include/topMembership.jsp" %>  
        </td>
        </tr>
        <tr>
        <td>
        
        <!--메인영역--->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr><td width="20">&nbsp;</td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="230" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/asp/membership/left_title.gif" width="230" height="38" /></td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/leftm1_o.gif" width="230" height="41" /></td>
              </tr>
              <tr>
                <td><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image25','','/images/asp/membership/leftm2_o.gif',1)"><img src="/images/asp/membership/leftm2_a.gif" name="Image25" width="230" border="0" id="Image25" /></a></td>
              </tr>
            </table></td>
            <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="38" align="left" valign="top"><img src="/images/asp/membership/title_1.gif" width="84" height="31" /></td>
              </tr>
              <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <!-- <td><img src="/images/asp/membership/join1_a.gif" width="178" height="91" /></td> -->
                    <td><img src="/images/asp/membership/join2_a.gif" width="245" height="91" /></td>
                    <td><img src="/images/asp/membership/join3_a.gif" width="245" height="91" /></td>
                    <td><img src="/images/asp/membership/join4_o.gif" width="199" height="91" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td><img src="/images/asp/membership/stitle_5.gif" width="690" height="24" /></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
              </tr>

             <tr>
                <td><table width="482" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="143" valign="top" background="/images/asp/membership/joinimg_04.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="65" align="center" class="b"><%=box.getString("p_kor_name")%>님의 회원가입이 완료되었습니다.</td>
                      </tr>
                      <tr>
                        <td height="60" align="center"><a href="/servlet/controller.homepage.MainServlet"><img src="/images/asp/membership/btn_home.gif" alt="홈으로" width="73" height="49" /></a></td>
                      </tr>
                    </table></td>
                  </tr>
                </table></td>
             </tr>
             
            
              <tr>
                <td height="50">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="20">&nbsp;</td>
          </tr></table>
       
        </td>
        </tr>
    </table>
