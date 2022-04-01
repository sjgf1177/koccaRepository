                  <table width="195" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="right"><img src="/images/user/kocca/member/type2/lm_tit.gif"></td>
                    </tr>
                  </table> 
            <%if(!box.getSession("userid").equals("")){%>
                    <table width="195" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><a href="/servlet/controller.homepage.KMemberInfoServlet?p_process=memberUpdatePage"><img src="/images/user/kocca/member/type2/lm_modify.gif" name="Image3" id="Image3" onMouseOver="MM_swapImage('Image3','','/images/user/kocca/member/type2/lm_modify_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                      </tr>
                    </table>
            <%}else{%>
                    <table width="195" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><a href="/servlet/controller.homepage.KMemberJoinServlet?p_process=join"><img src="/images/user/kocca/member/type2/lm_join.gif" name="Image2" id="Image2" onMouseOver="MM_swapImage('Image2','','/images/user/kocca/member/type2/lm_join_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                      </tr>
                    </table>
                    <table width="195" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><a href="/servlet/controller.homepage.KLoginServlet?p_process=golosspwd"><img src="/images/user/kocca/member/type2/lm_idpw.gif" name="Image1" id="Image1" onMouseOver="MM_swapImage('Image1','','/images/user/kocca/member/type2/lm_idpw_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                      </tr>
                    </table>
            <%}%>
                    <table width="195" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><a href="/servlet/controller.homepage.KMemberJoinServlet?p_process=personal"><img src="/images/user/kocca/member/type2/lm_policy.gif" name="Image4" id="Image4" onMouseOver="MM_swapImage('Image4','','/images/user/kocca/member/type2/lm_policy_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                      </tr>
                    </table>
                    <table width="195" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><a href="/servlet/controller.homepage.KMemberJoinServlet?p_process=user"><img src="/images/user/kocca/member/type2/lm_use.gif" name="Image5" id="Image5" onMouseOver="MM_swapImage('Image5','','/images/user/kocca/member/type2/lm_use_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                      </tr>
                    </table>