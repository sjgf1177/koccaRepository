<%
//**********************************************************
//  1. 제      목: 홈페이지 온라인 메뉴얼
//  2. 프로그램명: zu_HomePageOnline4_L.jsp
//  3. 개      요: 홈페이지 온라인 메뉴얼
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 25
//  7. 수      정: 이연정 2005. 7. 25
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
    String s_userid     = box.getSession("userid");
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topGuide.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function menuMove(process) {

      document.form1.action = "/servlet/controller.homepage.HomePageOnlineServlet";
      document.form1.p_process.value = process;
      document.form1.submit();
    }

//-->
</SCRIPT>
<form name = "form1">
<input type="hidden" name="p_process" value="">
</form>
          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 교육안내 &gt; 온라인메뉴얼</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/<%=tem_subimgpath%>/tit_online.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="5"></td>
                    </tr>
                    <tr> 
                      <td width="95" valign="top"><a href="javascript:menuMove('selectList')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','/images/user/guide/tab_online_01_on.gif',1)"><img src="/images/user/guide/tab_online_01.gif" name="Image35" border="0"></a> 
                      </td>
                      <td width="113" valign="top" ><a href="javascript:menuMove('selectList2_1')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','/images/user/guide/tab_online_02_on.gif',1)"><img src="/images/user/guide/tab_online_02.gif" name="Image36" border="0"></a></td>
                      <td width="128" valign="top" ><a href="javascript:menuMove('selectList3')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/guide/tab_online_03_on.gif',1)"><img src="/images/user/guide/tab_online_03.gif" name="Image37" border="0"></a></td>
                      <td width="100" valign="top" ><img src="/images/user/guide/tab_online_04_on.gif"></td>
                      <td width="239" align="right" >&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="5"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online4.gif"></td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307"><img src="/images/user/guide/online_g01.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" align="absmiddle"> 
                              <font color="#000000">온라인 메뉴얼</font> </td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td>사이트 이용시/학습활동시 쉽게 사용할 수 있도록 각 메뉴별로 설명되어 있습니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_g02.jpg"></td>
                      <td valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num2.gif" align="absmiddle"> 
                              <font color="#000000">과정체계도</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top"><font color="#CC3300"><strong>①</strong></font>은 
                              전 과정에 대한 올해의 과정체계도를 PDF파일로 한꺼번에 다운로드 받으실 수 있으며,<strong><font color="#CC3300">②</font></strong>는 
                              과정별로 과정체계도를 이미지로 보실 수 있습니다. <br>
                              오른쪽 버튼을 누르면 해당하는 과정의 체계도의 확대이미지와 PDF파일을 보실수 있습니다.<br> 
                            </td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_g04.jpg"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num3.gif" align="absmiddle"> 
                              <font color="#000000">노동부 관련정보</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">온라인 학습에 관한 고용보험정보를 자세히 보실 수 있습니다.<br> 
                              <br></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_g03.jpg"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num4.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">운영프로세스</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">운영되고 있는 과정들의 직무/어학별 교육과정 절차를 자세히 
                              알려줍니다. <br> </td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td valign="top">&nbsp;</td>
                      <td align="right" valign="top"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table></td>
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
