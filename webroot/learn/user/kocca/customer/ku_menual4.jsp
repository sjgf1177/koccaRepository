<%
//**********************************************************
//  1. 제      목: HelpDesk > 학습환경도우미
//  2. 프로그램명 : ku_menual4.jsp
//  3. 개      요: 학습환경도우미 (인터넷)
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
	box.put("leftmenu","03"); 

    String  v_process     = box.getString("p_process");
                  
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT>
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


function move(tab){
	document.form1.p_process.value= "Help";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.homepage.KHomePageHelpServlet";
	document.form1.submit();
}

</SCRIPT>

<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>


<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 온라인학습이용안내</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_menual.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_img_menual.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="right"><a href="javascript:move(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','/images/user/kocca/customer/tab_01on.gif',1)"><img src="/images/user/kocca/customer/tab_01.gif" name="Image35" border="0"></a><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/kocca/customer/tab_02on.gif',1)"><img src="/images/user/kocca/customer/tab_02.gif" name="Image33" border="0"></a><a href="javascript:move(3)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/user/kocca/customer/tab_03on.gif',1)"><img src="/images/user/kocca/customer/tab_03.gif" name="Image34" border="0"></a><img src="/images/user/kocca/customer/tab_04on.gif"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/apply/guide_box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/kocca/apply/guide_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"><table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/kocca/customer/st_g_result.gif" ></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext2" >1. 학습완료 후 마이클래스의 
                                    수강이력을 클릭합니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">학습완료된 과정들은 수강이력에 정보가 남습니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">①이수가 완료된 과정은 성적을 확인 하실 
                                    수 있으며 ②수료증을 발급받을 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/customer/menual_img5.gif" width="606" height="261"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              
                            </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/kocca/apply/guide_box_bo.gif"></td>
                          </tr>
                        </table>
	</form>




<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
