            <!-- Left Menu -->
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><img src="/images/user/game/service/type2/lm_tit.gif"></td>
              </tr>
            </table>
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
        else                               lmenu_selmenuimg = lmenu_menuimg;

%>
            <!--  <%=lmenu_menuname%> table start -->
            <table width="216" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td valign="top"><a href="javascript:menuForward('<%=topmenu%>', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2<%=m%>','','/images/user/game/service/type2/<%=lmenu_menuoverimg%>',1)"><img src="/images/user/game/service/type2/<%=lmenu_selmenuimg%>" name="Image2<%=m%>" border="0"></a></td>
              </tr>
            </table>
            <!--  <%=lmenu_menuname%> table end -->
<%  }
%>

            <!-- //Left Menu -->