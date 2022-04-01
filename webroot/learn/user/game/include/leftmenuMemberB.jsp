            <!-- Left Menu -->
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/user/game/member/type2/lm_tit.gif"></td>
              </tr>
            </table>

<%if(box.getSession("userid").equals("")){%>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=join" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/user/game/member/type2/lm_01on.gif',1)"><img src="/images/user/game/member/type2/lm_01.gif" name="Image24" border="0"></a></td>
              </tr>
            </table>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="/servlet/controller.homepage.LoginServlet?p_process=golosspwd" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/game/member/type2/lm_03on.gif',1)"><img src="/images/user/game/member/type2/lm_03.gif" name="Image29" border="0"></a></td>
              </tr>
            </table>
<%}else{%>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePage" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','/images/user/game/member/type2/lm_02on.gif',1)"><img src="/images/user/game/member/type2/lm_02.gif" name="Image25" border="0"></a></td>
              </tr>
            </table>
<%}%>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=personal" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/game/member/type2/lm_04on.gif',1)"><img src="/images/user/game/member/type2/lm_04.gif" name="Image30" border="0"></a></td>
              </tr>
            </table>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=user" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','/images/user/game/member/type2/lm_05on.gif',1)"><img src="/images/user/game/member/type2/lm_05.gif" name="Image32" border="0"></a></td>
              </tr>
            </table>
            <!-- //Left Menu -->