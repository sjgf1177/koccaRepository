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
        <table cellpadding="0" cellspacing="0" width="223" height="100%">
        <tr height="114">
            <td align="right"  valign="bottom" >
                <img src="/images/user/game/aboutus/type1/lm_tit.gif" ><br></td>
        </tr>
        <tr bgcolor=white>
            <td align="right" valign="top" background="/images/user/game/aboutus/type1/lm_bg.gif"> 
<%
		int i = 0;
		for(i = 1; i < 5 ; i++)
		{
			if(v_tab_num == i) v_tab_string = "/images/user/game/aboutus/type1/lm_0" + i + "on.gif";
			else v_tab_string = "/images/user/game/aboutus/type1/lm_0" + i + ".gif";
			%>
			
			<% if (i!=6) { %>
				<a href="javascript:MoveTab('tab<%=i%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image<%=i%>','','/images/user/game/aboutus/type1/lm_0<%=i%>on.gif',1)"><img src="<%=v_tab_string%>" name="Image<%=i%>" width="223" height="31" border="0"></a><br>
			<% } %>
			
				
<%		} %>
                <img src="/images/user/game/aboutus/type1/lm_bo.gif" ><br>
          <img src="/images/user/game/aboutus/type1/lm_bot.gif" width="223" height="1"><br></td>
        </tr>
        </table>
<!-- //Left Menu -->
