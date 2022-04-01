<%
//**********************************************************
//  1. 제      목: 고용보험 환급신청방법 및 절차
//  2. 프로그램명: zu_HomePageWork_02.jsp
//  3. 개      요: 고용보험 환급신청방법 및 절차
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
                      <td width="126" valign="top"><a href="javascript:Process()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/guide/tab_goyoung_on.gif',1)"><img src="/images/user/guide/tab_goyoung.gif" name="Image33"  border="0"></a> 
                      </td>
                      <td width="150" valign="top" ><a href="javascript:Process01()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/user/guide/tab_train_on.gif',1)"><img src="/images/user/guide/tab_train.gif" name="Image34"  border="0"></a></td>
                      <td width="171" valign="top" ><img src="/images/user/guide/tab_wprocess_on.gif" ></td>
                      <td width="94" valign="top" ><a href="javascript:Process03()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image30','','/images/user/guide/tab_completion_on.gif',1)"><img src="/images/user/guide/tab_completion.gif" name="Image30"  border="0"></a></td>
                      <td width="99" valign="top"><a href="javascript:Process04()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/guide/tab_gigan_on.gif',1)"><img src="/images/user/guide/tab_gigan.gif" name="Image31"  border="0"></a></td>
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
                            <td><img src="/images/user/guide/sst_process.gif" width="312" height="14"></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/guide/work_text3.gif" width="641" height="48"></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"> <table width="616" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="28" colspan="2"><img src="/images/user/guide/work_st_03.gif"></td>
                                </tr>
                                <tr> 
                                  <td height="10" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td rowspan="3"><img src="/images/user/guide/work_03_img1.gif"></td>
                                  <td><strong>1. 교육환급서류양식 </strong></td>
                                </tr>
                                <tr> 
                                  <td height="3"></td>
                                </tr>
                                <tr> 
                                  <td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;-회사별훈련비내역서<br> 
                                    &nbsp;&nbsp;&nbsp;&nbsp;- 정산내역서</td>
                                </tr>
                                <tr> 
                                  <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td width="132" rowspan="3"><img src="/images/user/guide/work_03_img2.gif"></td>
                                  <td width="484" valign="top"> <strong>2. (세금)계산서 
                                    사본</strong></td>
                                </tr>
                                <tr> 
                                  <td height="3"></td>
                                </tr>
                                <tr> 
                                  <td class="font_ex">+++지방노동부관서에 따라 제출서류는 차이가 
                                    있을 수 있습니다 </td>
                                </tr>
                                <tr> 
                                  <td height="20" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td colspan="2"><img src="/images/user/guide/work_st_04.gif"  ></td>
                                </tr>
                                <tr> 
                                  <td height="10" colspan="2"></td>
                                </tr>
                                <tr> 
                                  <td colspan="2">교육수료증 발급은 교육이 끝난 후 오토에버시스템즈(주)에서 
                                    발급합니다.</td>
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
