<%
    String bottom_line_dir = "coures";

    if (topmenu.equals("1"))       bottom_line_dir ="course";
    else if (topmenu.equals("2"))  bottom_line_dir ="apply";
    else if (topmenu.equals("3"))  bottom_line_dir ="myclass";
    else if (topmenu.equals("4"))  bottom_line_dir ="aboutus";
    else if (topmenu.equals("5"))  bottom_line_dir ="openkocca";
    else if (topmenu.equals("6"))  bottom_line_dir ="customer";
    else if (topmenu.equals("7"))  bottom_line_dir ="member";



%>
                      </td>
                      <td width="21">&nbsp;</td>
                    </tr>
                    <tr valign="bottom" bgcolor="#FFFFFF">
                      <td colspan="3"><img src="/images/user/kocca/<%=bottom_line_dir%>/type2/bo_center.gif"></td>
                    </tr>
                  </table>
                  <table  width="721" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td height="20" colspan="3">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>

      <!-- footer -->
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td width="916"> <table width="916" height="77" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="4" valign="top" >&nbsp;</td>
                <td valign="top" > <table width="908" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td  height="6" colspan="5"></td>
                    </tr>
                    <tr>
                      <td height="1" colspan="5" class="linecolor_board5"></td>
                    </tr>
                    <tr valign="top">
                      <td height="35" colspan="5">
                        <table width="908" border="0" cellpadding="0" cellspacing="0" bgcolor="<%=tem_footbgcolor2%>">
                          <tr>
                           <!----------------- 관련사이트 시작 ----------------->
                           <%@ include file="/learn/user/kocca/include/relateSite.jsp"%>
                           <!----------------- 관련사이트     ----------------->
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr>
                      <td height="1" colspan="5" class="linecolor_board5"></td>
                    </tr>
                    <tr>
                      <td height="6" colspan="5"></td>
                    </tr>
                    <tr>
                      <td  colspan="5"><table width="908" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="58" height="20">&nbsp;</td>
                            <td width="2" height="20">&nbsp;</td>
                            <td width="160" height="20">&nbsp;</td>
                            <td width="5" height="20"><img src="/images/user/kocca/homepage/type1/footer_vline02.gif" ></td>
                            <td height="20" class="tbl_bleft2"><img src="<%=tem_imgpath%><%=tem_footcopyright%>"></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>