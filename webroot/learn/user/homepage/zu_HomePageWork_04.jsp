<%
//**********************************************************
//  1. 제      목: 고용보험 환급기간
//  2. 프로그램명: zu_HomePageWork_04.jsp
//  3. 개      요: 고용보험 환급기간
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 26
//  7. 수      정: 이연정 2005. 7. 26
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
	box.put("leftmenu","04");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topGuide.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//위탁훈련 의뢰절차 탭으로 이동한다.
function Process() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//환급신청방법 및 절차  탭으로 이동한다.
function Process01() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove01";
    document.form1.submit();
}
//수료조건 탭으로 이동한다.
function Process02() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove02";
    document.form1.submit();
}
//환급기간 탭으로 이동한다.
function Process03() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove03";
    document.form1.submit();
}


//-->
</script>
		  <!-- center start -->
		    <form name="form1">
		    <input type="hidden" name="p_process">
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 교육안내 &gt; 노동부관련 정보</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/<%=tem_subimgpath%>/tit_workinfo.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/guide/st_goyong.gif"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 구분탭 -->
                  <table width="640" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="126" valign="top"><a href="javascript:Process()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/guide/tab_goyoung_on.gif',1)"><img src="/images/user/guide/tab_goyoung.gif" name="Image33"  border="0"></a> 
                      </td>
                      <td width="150" valign="top" ><a href="javascript:Process01()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/user/guide/tab_train_on.gif',1)"><img src="/images/user/guide/tab_train.gif" name="Image34"  border="0"></a></td>
                      <td width="171" valign="top" ><a href="javascript:Process02()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','/images/user/guide/tab_wprocess_on.gif',1)"><img src="/images/user/guide/tab_wprocess.gif" name="Image35" width="171" height="22" border="0"></a></td>
                      <td width="94" valign="top" ><a href="javascript:Process03()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','/images/user/guide/tab_completion_on.gif',1)"><img src="/images/user/guide/tab_completion.gif" name="Image36" width="94" height="22" border="0"></a></td>
                      <td width="99" valign="top"><img src="/images/user/guide/tab_gigan_on.gif" ></td>
                    </tr>
                    <tr> 
                      <td height="22" colspan="5"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr align="center"> 
                      <td width="675" height="150" colspan="2" valign="top" > 
                        <table width="641" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/sst_gigan.gif"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/guide/work_text5.gif"></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"> <table width="616" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td colspan="2"><img src="/images/user/guide/work_st_05.gif" ></td>
                                </tr>
                                <tr> 
                                  <td height="10" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td width="22">&nbsp;</td>
                                  <td width="594" valign="top"><strong>1) 노동부 
                                    지방관서 <br>
                                    </strong><br> <font color="#FFFFFF">... 
                                    </font>인터넷으로 관할 지방노동관서 연락처를 확인하시고, 환급관련 서류작성 
                                    문의도 지방노동관서로 <br> <font color="#FFFFFF">... 
                                    </font>직접 해 주십시오!! </td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                  <td valign="top">&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                  <td valign="top"><strong>2) 지방노동관서 연락처 확인방법<br>
                                    </strong><br>
                                    <font color="#FFFFFF">....</font>- 노동부 한글 
                                    홈페이지에 접속한다(http://www.molab.go.kr/Korean/Korean.html) 
                                    <br>
                                    <font color="#FFFFFF">....</font>- 홈페이지 좌측 
                                    메뉴바중 지방노동관서를 클릭한다. <br>
                                    <font color="#FFFFFF">....</font>- 수강생이 속한 
                                    지역의 지방노동관서를 클릭한다(예, 서울중부지방노동사무소) <br>
                                    <font color="#FFFFFF">....</font>- 화면 좌측에 
                                    있는 메뉴바중 전화 및 약도를 클릭한다. <br>
                                    <font color="#FFFFFF">....</font>- 설명된 각종 
                                    전화번호 중에서 고용안정센터를 찾아서 전화하면 고용보험 환급에 대해서 자세히 
                                    <br>
                                    <font color="#FFFFFF">....</font><font color="#FFFFFF">..</font> 
                                    안내해 드립니다.<br></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2">&nbsp;</td>
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