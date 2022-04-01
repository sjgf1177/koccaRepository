<%
    String v_tab = box.getStringDefault("p_process","tab1");
    String v_tab_string = "";

    int v_tab_num = Integer.parseInt(v_tab.substring(3,4));
%>
<script language="JavaScript" type="text/JavaScript">

function MoveTab(tab)
{
    location.href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=" + tab;
}
//-->
</script>
            <!-- Left Menu -->
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/user/game/aboutus/type2/lm_tit.gif"></td>
              </tr>
            </table>
<%
        int i = 0;
        for(i = 1; i < 8 ; i++)
        {
            if(v_tab_num == i) v_tab_string = "/images/user/game/aboutus/type2/lm_0" + i + "on.gif";
            else v_tab_string = "/images/user/game/aboutus/type2/lm_0" + i + ".gif";
%>
			<% if (i!=6) { %>
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top">
                  <a href="javascript:MoveTab('tab<%=i%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image<%=i%>','','/images/user/game/aboutus/type2/lm_0<%=i%>on.gif',1)"><img src="<%=v_tab_string%>" name="Image<%=i%>"  border="0"></a><br>
                </td>
              </tr>
            </table>
            <% } %>
<%      } %>

<!-- //Left Menu -->
