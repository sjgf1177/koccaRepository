<%
//**********************************************************
//  1. 제      목: 홈페이지 고용보험 안내
//  2. 프로그램명: zu_HomePageWork_L.jsp
//  3. 개      요: 홈페이지 고용보험 안내
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
	box.put("leftmenu","03");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topGuide.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//위탁훈련 의뢰절차 탭으로 이동한다.
function Process01() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove01";
    document.form1.submit();
}

//환급신청방법 및 절차  탭으로 이동한다.
function Process02() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove02";
    document.form1.submit();
}
//수료조건 탭으로 이동한다.
function Process03() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove03";
    document.form1.submit();
}
//환급기간 탭으로 이동한다.
function Process04() {
    document.form1.action = "/servlet/controller.homepage.HomePageWorkServlet";
    document.form1.p_process.value = "selectListMove04";
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
                      <td width="126" valign="top"><img src="/images/user/guide/tab_goyoung_on.gif"> 
                      </td>
                      <td width="150" valign="top" ><a href="javascript:Process01()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image28','','/images/user/guide/tab_train_on.gif',1)"><img src="/images/user/guide/tab_train.gif" name="Image28"  border="0"></a></td>
                      <td width="171" valign="top" ><a href="javascript:Process02()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/guide/tab_wprocess_on.gif',1)"><img src="/images/user/guide/tab_wprocess.gif" name="Image29" border="0"></a></td>
                      <td width="94" valign="top" ><a href="javascript:Process03()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/guide/tab_completion_on.gif',1)"><img src="/images/user/guide/tab_completion.gif" name="Image30" width="94" height="22" border="0"></a></td>
                      <td width="99" valign="top"><a href="javascript:Process04()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/guide/tab_gigan_on.gif',1)"><img src="/images/user/guide/tab_gigan.gif" name="Image31" width="99" height="22" border="0"></a></td>
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
                            <td><img src="/images/user/guide/sst_goyoung.gif"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/guide/work_text1.gif"></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td align="center"><table width="574" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="89"><img src="/images/user/guide/work_num1.gif"></td>
                                  <td width="485">고용보험에 의한 교육비 환급을 받기 위해서는 그 사업장이 
                                    반드시 고용보험에 가입되어져 <br>
                                    있어야만 합니다.</td>
                                </tr>
                                <tr bgcolor="#F3E7BC"> 
                                  <td height="1" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/guide/work_num2.gif"></td>
                                  <td>고용보험환급지정과정을 학습하신 분은, 고용보험의 직업능력개발사업에 의한 
                                    훈련비 <br>
                                    지원이 가능합니다.</td>
                                </tr>
                                <tr bgcolor="#F3E7BC"> 
                                  <td height="1" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td><img src="/images/user/guide/work_num3.gif"></td>
                                  <td>환급금액은 회사가 납부한 고용보험료 금액내에서 환급가능하며 교육비 납입과 
                                    환급의 주체는개인수강생이 아닌 사업주에게 국한됩니다.</td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                  <td>&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"> <table width="616" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td colspan="2"><img src="/images/user/guide/work_st_01.gif" ></td>
                                </tr>
                                <tr> 
                                  <td height="10" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td width="22">&nbsp;</td>
                                  <td width="594">- 수강료중 일부 환급<br>
                                    - 우선지원대상기업 (수강료의 100%), 대기업 (수강료의 80% )<br> 
                                    &nbsp;<font color="#CC3300">&nbsp;&nbsp;환급액은 
                                    과정에 따라 변동이 있을 수 있습니다. </font></td>
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

<!-- footer -->
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
