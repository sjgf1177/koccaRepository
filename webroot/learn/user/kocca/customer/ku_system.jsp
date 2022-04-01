<%
//**********************************************************
//  1. 제      목: 고객센타 > 학습환경시스템 
//  2. 프로그램명 : ku_system.jsp
//  3. 개      요: 학습환경시스템 (인터넷)
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
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_system.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_img_internet.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="94"><img src="/images/user/kocca/customer/tab_internet_on.gif" width="94" height="30"></td>
                            <td width="3">&nbsp;</td>
                            <td width="94"><a href="/servlet/controller.homepage.KHomePageHelpServlet?p_process=selectHelpMove" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image25','','/images/user/kocca/customer/tab_media_on.gif',1)"><img src="/images/user/kocca/customer/tab_media.gif" name="Image25" width="94" height="30" border="0"></a></td>
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
                            <td height="17" colspan="8" class="font_ex2">동영상 재생시 문제가 있으시다면, 현재 
                              사용 중인 인터넷 회선의 연결속도를 확인해 주세요.<br>
                              회선 속도 테스트는 하루에 20~30분 간격을 두고 반드시 여러차례 해주셔야 정확한 속도를 
                              알 수 있습니다.</td>
                          </tr>
                        </table> 
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/customer/st_internet1.gif"></td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="680" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="2" class="linecolor_customer02"></td>
                          </tr>
                          <tr> 
                            <td width="208" align="center" bgcolor="#D3E7F5"><strong>구분</strong></td>
                            <td width="452" align="center" bgcolor="#E7F7FE"><strong>최소 
                              인터넷 회선속도</strong></td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">일반동영상</td>
                            <td align="center" >최소 300 Kbps ~ 400 Kbps</td>
                          </tr>
                          <tr> 
                            <td height="26" class="tbl_dbtit1">문화콘텐츠아카데미 동영상</td>
                            <td height="26" align="center" >최소 500 Kbps ~ 700 
                              Kbps</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><span style="CURSOR: hand" 
                              onClick=comment(menu1);><img src="/images/user/kocca/customer/st_internet2.gif"></span></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td height="5" background="/images/user/kocca/customer/stu_box_vline2.gif"></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top" style="PADDING-LEFT: 35px"><SPAN id=menu1 
                              style="DISPLAY: none"> 
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=38>1. 한국전산원 사이트에 접속합니다. <A 
                                href="http://speed.nca.or.kr/" 
                                target=_blank>http://speed.nca.or.kr/</A><BR>
                                      2. 가정에서 ADSL 서비스를 받고 계시다면 초고속 인터넷 품질측정 → 
                                      <FONT color=#ff5400>품질테스트</FONT>를 선택합니다. 
                                    </TD>
                                  </TR>
                                  <TR> 
                                    <TD valign="top"><img src="/images/user/kocca/customer/help_test_01.jpg"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=25>학교, 회사, PC방 등에서는 전용선 품질측정 → 
                                      <FONT 
                                color=#ff5400>품질테스트</FONT>를 선택합니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD><img src="/images/user/kocca/customer/help_test_02.jpg"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=38>3. [품질테스트]를 선택하시면 별도의 프로그램을 
                                      설치하게 되는데, → 이때 <font color="#ff5400">설치</font>를 
                                      선택하여 주세요. <br>
                                      “설치 안함”을 선택하시면 속도측정을 하실 수 없습니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center" valign="top"><img src="/images/user/kocca/customer/help_test_03.jpg" width="410" height="155"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=38>4. 현재 접속상태의 종류, 현재 위치입력 란의 서비스 
                                      종류, 위치등의 옵션을 선택하시고, <BR>
                                      측정자 이름과 전자우편을 모두 기입하신 후 <FONT color=#ff5400>Test 
                                      Start</FONT> 버튼을 클릭합니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center"><img src="/images/user/kocca/customer/help_speed_05.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=38>5. 화면 우측상단에서 <FONT 
                                color=#ff5400>테스트 끝</FONT>을 확인하셨다면, 인터넷 품질측정이 
                                      정상적으로 이루어진 것입니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center" valign="top"><img src="/images/user/kocca/customer/help_speed_06.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD 
                          height=30></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </SPAN></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><span style="CURSOR: hand" onclick=comment(menu2);><img src="/images/user/kocca/customer/st_internet3.gif"></span></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td height="5" background="/images/user/kocca/customer/stu_box_vline2.gif"></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td style="PADDING-LEFT: 12px"><span id=menu2 
                              style="DISPLAY: none"> 
                              <TABLE cellSpacing=0 cellPadding=0 width="95%" 
                              border=0>
                                <TBODY>
                                  <TR> 
                                    <TD height=20>1. Download test 의 <FONT 
                                color=#ff5400>인터넷 다운로드 속도</FONT>를 확인합니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center"><img src="/images/user/kocca/customer/help_speed_7.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>2. General Ping Test 의 인터넷 <FONT 
                                color=#ff5400>왕복지연율</FONT>과 <FONT 
                                color=#ff5400>손실율</FONT>을 확인합니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center"><img src="/images/user/kocca/customer/help_speed_8.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=60>- 왕복지연율 : 10ms 이하 정상, 20ms ~ 
                                      30ms 이상 훨씬 크게 초과될 경우가 지속적으로 발생한다면 인터넷 회선업체에 
                                      문의해 주시기 바랍니다 <BR>
                                      - 손실율 : 0.00% 정상, 손실율이 지속적으로 발생할 경우 인터넷 
                                      회선업체에 문의해 주시기 바랍니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=60>3. Bandwidth Gaugea에서 인터넷 <FONT 
                                color=#ff5400>회선업체 평균과 사용자 측정값</FONT>의 Download 
                                      속도를 비교합니다.<BR>
                                      사용자 측정값이 인터넷 회선업체 평균값의 70~80% 미만일 경우에는 인터넷 
                                      회선업체에 문의 바랍니다.</TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center"><img src="/images/user/kocca/customer/help_speed_9.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD>&nbsp;</TD>
                                  </TR>
                                  <TR> 
                                    <TD height=20>4. 사용자 시스템 정보를 확인합니다. </TD>
                                  </TR>
                                  <TR> 
                                    <TD align="center"><img src="/images/user/kocca/customer/help_speed_10.gif"></TD>
                                  </TR>
                                  <TR> 
                                    <TD height=30></TD>
                                  </TR>
                                </TBODY>
                              </TABLE>
                              </span></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table>
	</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->