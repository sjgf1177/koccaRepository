        <!-- Left Menu -->
        <table width="223" height="100%" cellpadding="0" cellspacing="0">
        <tr height="114">
            <td align="right" valign="bottom">
                <img src="/images/user/game/apply/type1/lm_tit.gif" ><br></td>
        </tr>
        <tr bgcolor=white>
            <td align="right" valign="top" background="/images/user/game/apply/type1/lm_bg.gif">   
<%

    ArrayList lmenu_list = TempletBean.getMenuList(tem_grcode, topmenu, "C");
    String lmenu_gubun       = "";
    String lmenu_menuid      = "";
    String lmenu_kind        = "";
    String lmenu_position    = "";
    String lmenu_orders      = "";
    String lmenu_menuname    = "";
    String lmenu_menuurl     = "";
    String lmenu_menuimg     = "";
	String lmenu_menuoverimg = "";
	String lmenu_selmenuimg  = "";

    for(int m = 0; m < lmenu_list.size(); m++) {
        DataBox lmenu_dbox = (DataBox)lmenu_list.get(m);

        lmenu_gubun       = lmenu_dbox.getString("d_gubun");
        lmenu_menuid      = lmenu_dbox.getString("d_menuid");
        lmenu_kind        = lmenu_dbox.getString("d_kind");
        lmenu_position    = lmenu_dbox.getString("d_position");
        lmenu_orders      = lmenu_dbox.getString("d_orders");
        lmenu_menuname    = lmenu_dbox.getString("d_menuname");
        lmenu_menuurl     = lmenu_dbox.getString("d_menuurl");
        lmenu_menuimg     = lmenu_dbox.getString("d_menuimg");
		lmenu_menuoverimg = lmenu_dbox.getString("d_menuoverimg");
		if (leftmenu.equals(lmenu_menuid)) lmenu_selmenuimg = lmenu_menuoverimg;
		else                             lmenu_selmenuimg = lmenu_menuimg;

%>			
				<!--  <%=lmenu_menuname%> table start -->
                <a href="javascript:menuForward('<%=topmenu%>', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2<%=m%>','','/images/user/game/apply/type1/<%=lmenu_menuoverimg%>',1)"><img src="/images/user/game/apply/type1/<%=lmenu_selmenuimg%>" name="Image2<%=m%>" border="0"></a><br>
				<!--  <%=lmenu_menuname%> table end -->
<%}%>
                <img src="/images/user/game/apply/type1/lm_bo.gif"><br>
          <img src="/images/user/game/apply/type1/lm_bot.gif" width="223" height="1"><br></td>
        </tr>
        </table>
        <!-- //Left Menu -->
        
        
