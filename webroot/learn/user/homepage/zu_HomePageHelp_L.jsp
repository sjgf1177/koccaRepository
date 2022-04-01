<%
//**********************************************************
//  1. 제      목: 홈페이지 학습환경 도우미
//  2. 프로그램명: zu_HomePageHelp_L.jsp
//  3. 개      요: 홈페이지 학습환경 도우미화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	box.put("leftmenu","06");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->



<script language="JavaScript" type="text/JavaScript">
<!--
//클릭시 설명글 열림
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}

//동영상 리스트 페이지로 이동한다.
function moveList() {
    document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
    document.form1.p_process.value = "selectListMove";
    document.form1.submit();
}


//-->
</script>

		  <!-- center start -->
  		   <form name="form1">
		   <input type="hidden" name="p_process">
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 학습환경 도우미</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_help.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="98" valign="top"><img src="/images/user/support/help_tab_internet_on.gif"> 
                      </td>
                      <td width="98" valign="top" ><a href="javascript:moveList()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/support/help_tab_movie_on.gif',1)"><img src="/images/user/support/help_tab_movie.gif" name="Image28"  border="0"></a></td>
                      <td width="98" valign="top" >&nbsp;</td>
                      <td width="381" ></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/support/help_img.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/st_help_inter1.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top"><table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="2" class="line_color_com"></td>
                          </tr>
                          <tr> 
                            <td width="167" class="tbl_dbtit1"><strong>구분</strong></td>
                            <td width="488" align="center"><strong>최소 인터넷 회선속도</strong></td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">일반동영상</td>
                            <td align="center" >최소 300 Kbps ~ 400 Kbps</td>
                          </tr>
                          <tr> 
                            <td height="26" class="tbl_dbtit1">AutoEverSystems 
                              동영상</td>
                            <td height="26" align="center" >최소 500 Kbps ~ 700 
                              Kbps</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td><span style="CURSOR: hand" 
                              onClick=comment(menu1);><img src="/images/user/support/st_help_inter2.gif"></span></td>
                    </tr>
                    <tr> 
                      <td height="5" background="/images/user/support/stu_box_vline2.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td valign="top" style="PADDING-LEFT: 12px"><SPAN id=menu1 
                              style="DISPLAY: none"> 
                        <TABLE cellSpacing=0 cellPadding=0 width="80%" 
                              border=0>
                          <TBODY>
                            <TR> 
                              <TD height=38>1. 한국전산원 사이트에 접속합니다. <A 
                                href="http://speed.nca.or.kr/" 
                                target=_blank>http://speed.nca.or.kr/</A><BR>
                                2. 가정에서 ADSL 서비스를 받고 계시다면 초고속 인터넷 품질측정 → <FONT color=#ff5400>품질테스트</FONT>를 
                                선택합니다. </TD>
                            </TR>
                            <TR> 
                              <TD valign="top"><img src="/images/user/support/help_test_01.jpg"></TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=25>학교, 회사, PC방 등에서는 전용선 품질측정 → <FONT 
                                color=#ff5400>품질테스트</FONT>를 선택합니다. </TD>
                            </TR>
                            <TR> 
                              <TD><img src="/images/user/support/help_test_02.jpg"></TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=38>3. [품질테스트]를 선택하시면 별도의 프로그램을 설치하게 되는데, 
                                → 이때 <font color="#ff5400">설치</font>를 선택하여 주세요. 
                                <br>
                                “설치 안함”을 선택하시면 속도측정을 하실 수 없습니다. </TD>
                            </TR>
                            <TR> 
                              <TD align="center" valign="top"><img src="/images/user/support/help_test_03.jpg" width="410" height="155"></TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=38>4. 현재 접속상태의 종류, 현재 위치입력 란의 서비스 종류, 
                                위치등의 옵션을 선택하시고, <BR>
                                측정자 이름과 전자우편을 모두 기입하신 후 <FONT color=#ff5400>Test 
                                Start</FONT> 버튼을 클릭합니다. </TD>
                            </TR>
                            <TR> 
                              <TD align="center"><img src="/images/user/support/help_speed_05.gif"></TD>
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
                              <TD align="center" valign="top"><img src="/images/user/support/help_speed_06.gif"></TD>
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
                      <td><span style="CURSOR: hand" onclick=comment(menu2);><img src="/images/user/support/st_help_inter3.gif"></span></td>
                    </tr>
                    <tr>
                      <td height="5" background="/images/user/support/stu_box_vline2.gif"></td>
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
                              <TD align="center"><img src="/images/user/support/help_speed_7.gif"></TD>
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
                              <TD align="center"><img src="/images/user/support/help_speed_8.gif"></TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=60>- 왕복지연율 : 10ms 이하 정상, 20ms ~ 30ms 
                                이상 훨씬 크게 초과될 경우가 지속적으로 발생한다면 인터넷 회선업체에 문의해 주시기 
                                바랍니다 <BR>
                                - 손실율 : 0.00% 정상, 손실율이 지속적으로 발생할 경우 인터넷 회선업체에 
                                문의해 주시기 바랍니다. </TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=60>3. Bandwidth Gaugea에서 인터넷 <FONT 
                                color=#ff5400>회선업체 평균과 사용자 측정값</FONT>의 Download 
                                속도를 비교합니다.<BR>
                                사용자 측정값이 인터넷 회선업체 평균값의 70~80% 미만일 경우에는 인터넷 회선업체에 
                                문의 바랍니다.</TD>
                            </TR>
                            <TR> 
                              <TD align="center"><img src="/images/user/support/help_speed_9.gif"></TD>
                            </TR>
                            <TR> 
                              <TD>&nbsp;</TD>
                            </TR>
                            <TR> 
                              <TD height=20>4. 사용자 시스템 정보를 확인합니다. </TD>
                            </TR>
                            <TR> 
                              <TD align="center"><img src="/images/user/support/help_speed_10.gif"></TD>
                            </TR>
                            <TR> 
                              <TD 
                          height=30></TD>
                            </TR>
                          </TBODY>
                        </TABLE>
                        </span></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
			  </form>
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