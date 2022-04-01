<%
//**********************************************************
//  1. 제      목: 로그인
//  2. 프로그램명 : zu_login.jsp
//  3. 개      요: 로그인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
    box.put("leftmenu","5");

    String lmenu_gubun       = "";
    String lmenu_menuid      = "";
    String lmenu_kind        = "";
    String lmenu_position    = "";
    String lmenu_orders      = "";
    String lmenu_menuname    = "";
    String lmenu_menuurl     = "";

    ArrayList lmenu_list1 = (ArrayList)request.getAttribute("lmenu_list1");
    ArrayList lmenu_list2 = (ArrayList)request.getAttribute("lmenu_list2");
    ArrayList lmenu_list3 = (ArrayList)request.getAttribute("lmenu_list3");
    ArrayList lmenu_list4 = (ArrayList)request.getAttribute("lmenu_list4");
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topHome.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

//-->
</script>

          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 사이트맵</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/common/<%=tem_subimgpath%>/tit_map.jpg" ></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="200" align="center" valign="top"><table width="620" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="301" rowspan="3" align="center" valign="top"><table width="300" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td align="center"><table width="260" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="131"><img src="/images/user/common/site_aimg.gif"></td>
                                        <td width="129" valign="top"> 
                                          <table width="126" border="0" cellspacing="0" cellpadding="0">
<%
    // 수강신청
    for (int i=0; i<lmenu_list1.size(); i++) {
        DataBox dbox1 = (DataBox)lmenu_list1.get(i);

        lmenu_gubun       = dbox1.getString("d_gubun");
        lmenu_menuid      = dbox1.getString("d_menuid");
        lmenu_kind        = dbox1.getString("d_kind");
        lmenu_position    = dbox1.getString("d_position");
        lmenu_orders      = dbox1.getString("d_orders");
        lmenu_menuname    = dbox1.getString("d_menuname");
        lmenu_menuurl     = dbox1.getString("d_menuurl");

%>
                                            <tr> 
                                              <td  class="tbl_siteleft"><img src="/images/user/common/bl_a1.gif" align="absmiddle">
                                              <a href="javascript:menuForward('1', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')"><%=lmenu_menuname%></a></td>
                                            </tr>
                                            <tr> 
                                              <td height="1" background="/images/user/common/line_bg.gif"></td>
                                            </tr>
<%
    }
%>

                                          </table>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
                                <tr>
                                  <td height="20">&nbsp;</td>
                                </tr>
                                <tr>
                                  <td align="center" valign="top"><table width="260" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="131" valign="top"><img src="/images/user/common/site_simg.gif" width="122" height="122"></td>
                                        <td width="129" valign="top">
                                          <table width="126" border="0" cellspacing="0" cellpadding="0">
<%
    // 학습지원센터
    for (int i=0; i<lmenu_list3.size(); i++) {
        DataBox dbox3 = (DataBox)lmenu_list3.get(i);

        lmenu_gubun       = dbox3.getString("d_gubun");
        lmenu_menuid      = dbox3.getString("d_menuid");
        lmenu_kind        = dbox3.getString("d_kind");
        lmenu_position    = dbox3.getString("d_position");
        lmenu_orders      = dbox3.getString("d_orders");
        lmenu_menuname    = dbox3.getString("d_menuname");
        lmenu_menuurl     = dbox3.getString("d_menuurl");

%>

                                            <tr> 
                                              <td  class="tbl_siteleft"><img src="/images/user/common/bl_a1.gif" align="absmiddle">
                                                <a href="javascript:menuForward('3', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')"><%=lmenu_menuname%></a></td>
                                            </tr>
                                            <tr> 
                                              <td height="1" background="/images/user/common/line_bg.gif"></td>
                                            </tr>
<%
    }
%>

                                          </table>
                                        </td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="319" height="197" align="center" valign="top">
							  <table width="260" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="131" valign="top"><img src="/images/user/common/site_cimg.gif"></td>
                                  <td width="129" valign="top">
								    <table width="126" border="0" cellspacing="0" cellpadding="0">
<%
    // 나의강의실
    for (int i=0; i<lmenu_list2.size(); i++) {
        DataBox dbox2 = (DataBox)lmenu_list2.get(i);

        lmenu_gubun       = dbox2.getString("d_gubun");
        lmenu_menuid      = dbox2.getString("d_menuid");
        lmenu_kind        = dbox2.getString("d_kind");
        lmenu_position    = dbox2.getString("d_position");
        lmenu_orders      = dbox2.getString("d_orders");
        lmenu_menuname    = dbox2.getString("d_menuname");
        lmenu_menuurl     = dbox2.getString("d_menuurl");

%>

                                            <tr> 
                                              <td  class="tbl_siteleft"><img src="/images/user/common/bl_a1.gif" align="absmiddle">
                                                <a href="javascript:menuForward('2', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')"><%=lmenu_menuname%></a></td>
                                            </tr>
                                            <tr> 
                                              <td height="1" background="/images/user/common/line_bg.gif"></td>
                                            </tr>
<%
    }
%>

                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"><table width="260" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="131"><img src="/images/user/common/site_gimg.gif"></td>
                                  <td width="129" valign="top">
								    <table width="126" border="0" cellspacing="0" cellpadding="0">
<%
    // 교육안내
    for (int i=0; i<lmenu_list4.size(); i++) {
        DataBox dbox4 = (DataBox)lmenu_list4.get(i);

        lmenu_gubun       = dbox4.getString("d_gubun");
        lmenu_menuid      = dbox4.getString("d_menuid");
        lmenu_kind        = dbox4.getString("d_kind");
        lmenu_position    = dbox4.getString("d_position");
        lmenu_orders      = dbox4.getString("d_orders");
        lmenu_menuname    = dbox4.getString("d_menuname");
        lmenu_menuurl     = dbox4.getString("d_menuurl");

%>

                                            <tr> 
                                              <td  class="tbl_siteleft"><img src="/images/user/common/bl_a1.gif" align="absmiddle">
                                                <a href="javascript:menuForward('4', '<%=lmenu_menuid%>', '<%=lmenu_orders%>')"><%=lmenu_menuname%></a></td>
                                            </tr>
                                            <tr> 
                                              <td height="1" background="/images/user/common/line_bg.gif"></td>
                                            </tr>
<%
    }
%>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table> </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->