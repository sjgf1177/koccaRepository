<%
	String topmenu_value1 = "m_apply.gif";
	String topmenu_value2 = "m_my.gif";
	String topmenu_value3 = "m_support.gif";
	String topmenu_value4 = "m_guide.gif";

	if (topmenu.equals("1")) {
		topmenu_value1 = "m_apply_on.gif";
	} else if (topmenu.equals("2")) {
		topmenu_value2 = "m_my_on.gif";
	} else if (topmenu.equals("3")) {
		topmenu_value3 = "m_support_on.gif";
	} else if (topmenu.equals("4")) {
		topmenu_value4 = "m_guide_on.gif";
	}

%>
            <!-- topmenu table -->
            <td align="right" valign="top"><table width="500" border="0" cellspacing="0" cellpadding="0">
              <tr align="right"> 
                <td width="625" height="42" colspan="2" valign="top"><a href="javascript:menuForward('1','01','1');" onfocus="this.blur()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image49','','/images/user/homepage/type2/m_apply_on.gif',1)"><img src="/images/user/homepage/type2/<%=topmenu_value1%>" name="Image49" border="0"></a><a href="javascript:menuForward('2','01','1');"  onfocus="this.blur()"onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image50','','/images/user/homepage/type2/m_my_on.gif',1)"><img src="/images/user/homepage/type2/<%=topmenu_value2%>" name="Image50" border="0"></a><a href="javascript:menuForward('3','01','1');" onfocus="this.blur()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','/images/user/homepage/type2/m_support_on.gif',1)"><img src="../../../images/user/homepage/type2/<%=topmenu_value3%>" name="Image51" border="0"></a><a href="javascript:menuForward('4','01','1');" onfocus="this.blur()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image52','','/images/user/homepage/type2/m_guide_on.gif',1)"><img src="/images/user/homepage/type2/<%=topmenu_value4%>" name="Image52" border="0"></a></td>
              </tr>
            </table></td>
