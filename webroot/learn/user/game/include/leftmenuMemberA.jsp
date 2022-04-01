        <!-- Left Menu -->
        <table cellpadding="0" cellspacing="0" width="223" height="100%">
        <tr height="114">
            <td align="right" valign="bottom">
                <img src="/images/user/game/member/type1/lm_tit.gif"><br>
            </td>
        </tr>
        <tr bgcolor=white valign="top">
            <td align="right" valign="top" background="/images/user/game/member/type1/lm_bg.gif"> 
                <%if(!box.getSession("userid").equals("")){%><a href="/servlet/controller.homepage.MemberInfoServlet?p_process=memberUpdatePage" onMouseOver="MM_swapImage('Image1','','/images/user/game/member/type1/lm_02on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/game/member/type1/lm_02.gif" name="Image1" width="223" height="31" border="0" id="Image1"></a><br>        
                <%}else{%><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=join"><img src="/images/user/game/member/type1/lm_01.gif" name="Image1" id="Image1" onMouseOver="MM_swapImage('Image1','','/images/user/game/member/type1/lm_01on.gif',1)" onMouseOut="MM_swapImgRestore()"></a><br>        
                
                <a href="/servlet/controller.homepage.LoginServlet?p_process=golosspwd" onMouseOver="MM_swapImage('Image1','','/images/user/game/member/type1/lm_03on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/game/member/type1/lm_03.gif" name="Image1" width="223" height="31" border="0" id="Image1"></a><br>
                <%}%>
                <a href="/servlet/controller.homepage.MemberJoinServlet?p_process=personal" onMouseOver="MM_swapImage('Image1','','/images/user/game/member/type1/lm_04on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/game/member/type1/lm_04.gif" name="Image1" width="223" height="31" border="0" id="Image1"></a><br>
                <a href="/servlet/controller.homepage.MemberJoinServlet?p_process=user" onMouseOver="MM_swapImage('Image1','','/images/user/game/member/type1/lm_05on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/user/game/member/type1/lm_05.gif" name="Image1" width="223" height="31" border="0" id="Image1"></a><br>
                <img src="/images/user/game/member/type1/lm_bo.gif"><br>
                <img src="/images/user/game/member/type1/lm_bot.gif" width="223" height="100%"><br>
            </td>
        </tr>
        </table>
        <!-- //Left Menu -->
