<%@ page import = "com.credu.templet.*" %>

          <td width="152" rowspan="2" valign="top" >
            <img src="/images/user/myclass/type1/lm_tit.gif" >
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
<a href="javascript:menuForward('<%=topmenu%>', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2<%=m%>','','/images/user/myclass/<%=lmenu_menuoverimg%>',1)"><img src="/images/user/myclass/<%=lmenu_selmenuimg%>" name="Image2<%=m%>" border="0"></a><%if(leftmenu.equals("04")&&lmenu_menuid.equals("04")){%><a href="1know01.htm" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image45','','/images/user/myclass/lm_041_on.gif',1)">
<img src="/images/user/myclass/lm_041.gif" name="Image45"  border="0"></a><table width="150" height='80' border=0><tr><td valign=top><%@ include file="/learn/user/study/zu_KnowMenuCategory_L.jsp" %></td></tr></table>
<a href="1knowpop.htm" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image46','','/images/user/myclass/lm_042_on.gif',1)">
<img src="/images/user/myclass/lm_042.gif" name="Image46" border="0"></a>
<%}%>
<%
    }
%>
          </td>
