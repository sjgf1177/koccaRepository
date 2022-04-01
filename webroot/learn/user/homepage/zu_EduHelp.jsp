<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
	EduGroupBean helpbean = new EduGroupBean();
	box.put("p_grcode", tem_grcode);

	EduGroupData helpdata = helpbean.SelectEduGroupData(box);
%>

				  <!-- ±³À°¹®ÀÇ start-->
                  <table width="158" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="96" align="center" background="/images/user/homepage/telmail_bg.gif" class="telcolor">
                       <table width="138" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="37" colspan="2" align="right">&nbsp;</td>
                          </tr>
                          <tr>
                            <td align="center" colspan="2"><img src="/images/user/homepage/type2/telmail_hour.gif" border="0"></td>
                          </tr>
						  						<tr>
                            <td width="37" align="right" valign="bottom"><img src="/images/user/homepage/telmail_tel.gif"></td>
                            <td width="101" valign="bottom"><font color="#000000"><%=helpdata.getMasterComptel()%></font></td>
                          </tr>
                          <tr>
                            <td colspan="2"><img src="/images/user/homepage/telmail_line.gif"></td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="2" align="right"></td>
                          </tr>
                          <tr align="center"> 
                            <td colspan="2">
                            	<!--a href="mailto:<%=helpdata.getMasterEmail()%>"-->
                            	<a href="/servlet/controller.homepage.HomePageContactServlet">
                            	<img src="/images/user/homepage/telmail_emailbtn.gif" border="0"></a></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="5" class="telcolor"><img src="/images/user/homepage/telmail_bo.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                  </table>
				  <!-- ±³À°¹®ÀÇ end -->