<%
//**********************************************************
//  1. 제      목: 고객센타 > 학습환경시스템
//  2. 프로그램명 : ku_systemMedia.jsp
//  3. 개      요: 학습환경시스템 (동영상)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process    = box.getString("p_process");
    String  v_title      = "";
	String s_userid		 = box.getSession("userid");
	String s_name		 =  box.getSession("name");
                  
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--


//-->
</script>



<form name = "form1" method = "post">
	  <input type = "hidden" name = "p_process"   value = "">
	  <input type = "hidden" name = "p_userid"    value = "">


						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 학습환경 시스템</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_system.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_img_media.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="94"><a href="/servlet/controller.homepage.KHomePageHelpServlet?p_process=selectHelp" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/kocca/customer/tab_internet_on.gif',1)"><img src="/images/user/kocca/customer/tab_internet.gif" name="Image37" width="94" height="30" border="0"></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="94"><img src="/images/user/kocca/customer/tab_media_on.gif" width="94" height="30"></td>
                            <td width="3">&nbsp;</td>
                            <td width="94">&nbsp;</td>
                            <td width="3">&nbsp;</td>
                            <td width="94">&nbsp;</td>
                            <td width="295">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="8" ></td>
                          </tr>
                          <tr> 
                            <td height="1" colspan="8" class="linecolor_cource02"></td>
                          </tr>
                          <tr>
                            <td height="10" colspan="8"></td>
                          </tr>
                          <tr> 
                            <td height="17" colspan="8" class="font_ex2">동영상 강좌를 
                              들으실 때 끊김 현상이 생긴다면, 동영상 속도를 측정해 보세요.<br>
                              인터넷 회선속도 측정과 동영상 속도 측정의 결과값은 다를 수 있으며, 동영상 속도 측정결과 
                              속도가 지나치게 느릴 경우 <br>
                              인터넷 회선속도 측정을 통해 회선 속도도 함께 체크해 주시길 바랍니다.</td>
                          </tr>
                        </table> 
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/kocca/customer/st_media1.gif"></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top"><table width="680" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                                <tr  class="lcolor"> 
                                  <td height="3" colspan="2" class="linecolor_customer02"></td>
                                </tr>
                                <tr> 
                                  <td width="170" align="center" bgcolor="#D3E7F5"><strong>구분</strong></td>
                                  <td width="550" align="center" bgcolor="#E7F7FE"><strong>시스템 
                                    정보</strong></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_dbtit2">OS</td>
                                  <td align="center" >Windows XP </td>
                                </tr>
                                <tr> 
                                  <td height="26" class="tbl_dbtit1">IE</td>
                                  <td height="26" align="center" >Mozilla/4.0 
                                    (compatible; MSIE 6.0; Windows NT 5.1; SV1) 
                                  </td>
                                </tr>
                                <tr> 
                                  <td height="26" class="tbl_dbtit1">CPU</td>
                                  <td height="26" align="center" >x86 </td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/customer/st_media2.gif"></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/help_300.gif"></td>
                            <td valign="top"><img src="/images/user/kocca/customer/help_500.gif"></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="365">
							<script language = "javascript" src = "/script/user_patch.js"></script>
							<script language='javascript'>object_embed("/asf/test_mynameis.asf","300","250");</script><!--embed src="/asf/test_mynameis.asf" width="300" height="250"></embed--></td>
                            <td width="355" valign="top"><script language='javascript'>object_embed("mms://speedtest.hvod.nefficient.co.kr/speedtest/test1.wmv","300","250");</script><!--embed src="mms://speedtest.hvod.nefficient.co.kr/speedtest/test1.wmv" width=300 height=250></embed--></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                          </tr>
                        </table>

	</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->