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

//	box.put("leftmenu","06");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->



<script language="JavaScript" type="text/JavaScript">
<!--

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


//리스트 페이지로 이동한다.
function List() {
    document.form1.action = "/servlet/controller.homepage.HomePageHelpServlet";
    document.form1.p_process.value = "selectList";
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
                      <td width="98" valign="top"><a href="javascript:List()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/support/help_tab_internet_on.gif',1)"><img src="/images/user/support/help_tab_internet.gif" name="Image29"  border="0"></a> 
                      </td>
                      <td width="98" valign="top" ><img src="/images/user/support/help_tab_movie_on.gif" ></td>
                      <td width="98" valign="top" >&nbsp;</td>
                      <td width="381" ></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/support/help_img2.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/st_help_pc.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="2" class="line_color_com"></td>
                          </tr>
                          <tr> 
                            <td width="117" class="tbl_dbtit1"><strong>구분</strong></td>
                            <td width="538" class="tbl_bleft"><strong>시스템 정보</strong></td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">OS</td>
                            <td class="tbl_bleft">
<script LANGUAGE="JavaScript">
<!--
function checkOS() {
if(navigator.userAgent.indexOf('IRIX') != -1)
{ var OpSys = "Irix"; }
else if((navigator.userAgent.indexOf('Win') != -1) &&
(navigator.userAgent.indexOf('98') != -1))
{ var OpSys = "Windows 98"; }
else if((navigator.userAgent.indexOf('Win') != -1) &&
(navigator.userAgent.indexOf('95') != -1))
{ var OpSys = "Windows 95"; }
else if(navigator.appVersion.indexOf("16") !=-1)
{ var OpSys = "Windows 3.1"; }
else if (navigator.appVersion.indexOf("5.1") !=-1)
{ var OpSys= "Windows XP"; }  
else if (navigator.appVersion.indexOf("NT") !=-1)
{ var OpSys= "Windows NT"; }  
else if(navigator.appVersion.indexOf("SunOS") !=-1)
{ var OpSys = "SunOS"; }
else if(navigator.appVersion.indexOf("Linux") !=-1)
{ var OpSys = "Linux"; }  
else if(navigator.userAgent.indexOf('Mac') != -1)
{ var OpSys = "Macintosh"; }
else if(navigator.appName=="WebTV Internet Terminal")
{ var OpSys="WebTV"; }
else if(navigator.appVersion.indexOf("HP") !=-1)
{ var OpSys="HP-UX"; } 
else { var OpSys = "other"; }
return OpSys;
}
// -->
</script>
<script LANGUAGE="JavaScript">
<!--
var OpSys = checkOS();
document.write(OpSys);
// -->
</script>
                            </td>
                          </tr>
                          <tr> 
                            <td height="26" class="tbl_dbtit1">IE</td>
                            <td height="26" class="tbl_bleft">
                                <script>
                                //document.write(navigator.appName)
                                document.write(navigator.userAgent)
                                </script>
                            </td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">CPU</td>
                            <td class="tbl_bleft">
                                <script>
                                document.write(navigator.cpuClass)
                                </script>
                            </td>
                          </tr>
<!--
                          <tr> 
                            <td class="tbl_dbtit1">Memory</td>
                            <td class="tbl_bleft">Total : 511M | Free : 32M</td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">DiectX</td>
                            <td class="tbl_bleft">DirectX 9.0c installed</td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit1">WMP</td>
                            <td class="tbl_bleft">8.0.0.4487</td>
                          </tr>
                          <tr> 
                            <td class="tbl_dbtit2">Video</td>
                            <td class="tbl_bleft">RADEON 9600 SERIES ; 6.14.0010.6467</td>
                          </tr>
-->
                        </table></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/st_help_movie.gif" width="333" height="16"></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><table width="675" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/support/300k.gif"></td>
                            <td valign="top"><img src="/images/user/support/500k.gif"></td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="319"><script language = "javascript" src = "/script/user_patch.js"></script>
							<script language='javascript'>
								object_embed("mms://218.144.185.153/credu/4912_05/0502.wmv","300","250");
							</script><!--embed src="mms://218.144.185.153/credu/4912_05/0502.wmv" width="300" height="250"></embed--></td>
                            <td width="356" valign="top"><script language='javascript'>
								object_embed("mms://218.144.185.153/credu/4912_11/1102.wmv","300","250");
							</script><!--embed src="mms://218.144.185.153/credu/4912_11/1102.wmv" width=300 height=250></embed--></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
			  </form>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
