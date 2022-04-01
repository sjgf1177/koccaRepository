<%
//**********************************************************
//  1. 제      목: 프로그램 다운로드
//  2. 프로그램명 : ku_swDown.jsp
//  3. 개      요: 프로그램 다운로드
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
                            <td  class="location" > HOME > 고객센타 &gt; 프로그램다운로드</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_down.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_img_down.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><a href="http://www.4csoft.com/new/dn_tutor_t.php?sub=1" target="_blank"><img src="/images/user/kocca/customer/swdown_09.gif" border="0"></a></td>
                          </tr>
						  <tr>
                            <td><a href="http://e-learninghmc.yeslearn.co.kr/data/flashmx_trial_en.exe" target="_blank"><img src="/images/user/kocca/customer/swdown_01.gif" border="0"></a></td>
                          </tr>
                          <tr>
                            <td><a href="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" target="_blank"><img src="/images/user/kocca/customer/swdown_02.gif" border="0"></a></td>
                          </tr>
                          <tr>
                            <td><a href="http://www.macromedia.com/shockwave/download/download.cgi?Lang=Korean&P5_Language=Korea" target="_blank"><img src="/images/user/kocca/customer/swdown_03.gif" border="0"></a></td>
                          </tr>
                          <tr>
                            <td><a href="http://e-learninghmc.yeslearn.co.kr/data/rp7com.exe" target="_blank"><img src="/images/user/kocca/customer/swdown_04.gif" border="0"></a></td>
                          </tr>
                          <tr>
                            <td><a href="http://java.com/ko/download/windows_automatic.jsp" target="_blank"><img src="/images/user/kocca/customer/swdown_05.gif" border="0"></a></td>
                          </tr>
						  <tr>
                            <td><a href="http://www.microsoft.com/korea/windows/windowsmedia/" target="_blank"><img src="/images/user/kocca/customer/swdown_06.gif" border="0"></a></td>
                          </tr>
						  <tr>
                            <td><a href="http://www.adobe.com/kr/products/acrobat/readstep2.html" target="_blank"><img src="/images/user/kocca/customer/swdown_07.gif" border="0"></a></td>
                          </tr>
						  <tr>
                            <td><a href="http://www.microsoft.com/korea/windows/directx/default.mspx" target="_blank"><img src="/images/user/kocca/customer/swdown_08.gif" border="0"></a></td>
                          </tr>
                        </table> 
	</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->