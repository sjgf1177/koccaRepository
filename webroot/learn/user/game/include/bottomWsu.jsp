<%
String tem_type_sub = box.getSession("tem_type_sub");
%>
    </td>
    </tr>
    </table>
    <!-- //CONTENTS -->
</td>
</tr>

<tr valign="top" height="137">
    <td>

 <!-- FOOTER -->

<table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td width="916">
	<table width="916"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" >
		  <table width="908" border="0" cellspacing="0" cellpadding="0">
              <!--tr>
                <td  height="6" colspan="5"><img src="<%=tem_imgpath%><%=tem_foottopimg%>"></td>
              </tr>
              <tr>
                <td  height="6" colspan="5"></td>
              </tr-->
<%
if(!tem_type_sub.equals("wsu")) {
%>
              <tr>
                <td height="1" colspan="5" class="linecolor_footer"></td>
              </tr>
              <tr valign="top">
                <td height="35" colspan="5">
					<!----------------- 관련사이트 시작 ----------------->
					<%@ include file="/learn/user/game/include/relateSite.jsp"%>
					<!----------------- 관련사이트 ----------------->
                </td>
              </tr>
              <tr>
                <td height="1" colspan="5" class="linecolor_footer"></td>
              </tr>
              <tr>
                <td height="6" colspan="5"></td>
              </tr>
<%
}
%>
              <tr>
                <td colspan="5">
				<table width="1000" bgColor="#F7F9E1" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="213" height="20" bgcolor="FFFFFF"><img src="<%=tem_imgpath%><%=tem_footleftimg%>"></td>
                      <td width="280" valign="top" bgcolor="FFFFFF"><img src="<%=tem_imgpath%><%=tem_footcopyright%>"></td>
<%
if(tem_type_sub.equals("wsu")) {
%>
		      <td width="415" bgcolor="FFFFFF"><img src="/images/user/game/homepage/type1/N000012_bottom_03.gif" border="0"></td>
<%
} else {
%>
                      <td width="415" bgcolor="FFFFFF"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab1"><img src="/images/user/game/homepage/type1/footer_m1.gif" border="0"></a><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=user"><img src="/images/user/game/homepage/type1/footer_m2.gif" border="0"></a><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=personal"><img src="/images/user/game/homepage/type1/footer_m3.gif" border="0"></a></td>
<%
}
%>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</BODY>
</HTML>

