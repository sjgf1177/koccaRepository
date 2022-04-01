<%
//**********************************************************
//  1. 제      목: 학습지원센터 이용방법
//  2. 프로그램명: zu_HomePageWay_L.jsp
//  3. 개      요: 학습지원센터 이용방법
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
	box.put("leftmenu","07");                       // 메뉴 아이디 세팅

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 학습지원센터 이용법</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_center.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- qna table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10"><img src="/images/user/support/st_use_01.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10" align="center">
					  <img src="/images/user/support/use_img1.jpg" border="0" usemap="#Map">
                        <map name="Map">
                          <area shape="rect" coords="8,120,78,149" href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">
                          <area shape="rect" coords="163,141,222,168" href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">
                          <area shape="rect" coords="326,121,376,151" href="/servlet/controller.homepage.HomePageBoardServlet?p_process=selectList">
                          <area shape="rect" coords="490,100,538,127" href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectList">
                        </map></td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10"><img src="/images/user/support/st_use_02.gif" width="333" height="16"></td>
                    </tr>
                    <tr> 
                      <td height="10">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="10" align="center"><img src="/images/user/support/use_img2.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="10">&nbsp;</td>
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
