<%
//**********************************************************
//  1. 제      목: 홈페이지 소프트웨어 리스트화면
//  2. 프로그램명: zu_HomePageSoftWare_L.jsp
//  3. 개      요: 홈페이지 소프트웨어 리스트화면
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

    String  v_process     = box.getString("p_process");
    String s_userid  = box.getSession("userid");

box.put("leftmenu","03");                       // 메뉴 아이디 세팅
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; S/W다운로드</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_swdown.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- qna table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/support/down_img.jpg" ></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"> <table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://e-learninghmc.yeslearn.co.kr/data/flashmx_trial_en.exe"><img src="/images/user/support/down_flashmx.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_flashmx.gif"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">flashmx_trial_en.exe를 다운받으셔서 setup을 
                              시키시고 사용하시면 30일만 쓰실수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" target="_new"><img src="/images/user/support/down_flashview.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_flashview.gif"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">당사에서 준비한 대부분 과정은 플래쉬를 포함하고 있는 경우가 
                              대부분입니다. 설치가 안되는 수강생분들께서는 미리 설치하시기 바랍니다.</td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://e-learninghmc.yeslearn.co.kr/data/viewer_inst1.exe"><img src="/images/user/support/down_penda.jpg" width="94" height="70" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_penda.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">파일 확장명이 *.cct 일 경우에 PenDA 프로그램을 다운받아서 
                              설치하시기 바랍니다.</td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://www.gva.co.kr/products/student.asp" target="_new"><img src="/images/user/support/down_gva.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_gva.gif"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">GVA로 제작된 콘텐츠를 다운로드 받아 학습하시기 위해서는 
                              이 소프트웨어가 필요합니다. </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://www.macromedia.com/shockwave/download/download.cgi?Lang=Korean&P5_Language=Korean" target="_new"><img src="/images/user/support/down_wave.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_wave.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">매크로미디어 Shockwave 플레이어입니다. 매크로미디어 
                              디렉터로 제작된 멀티미디어 콘텐츠를 정상적으로 보시려면 본 플러그인을 설치하세요.</td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="/portal/user/gold/down/HwpViewer.exe"><img src="/images/user/support/down_hansoft.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_hansoft.gif"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top"></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://java.com/ko/download/windows_automatic.jsp
" target="_blank"><img src="/images/user/support/down_java.jpg" width="94" height="70" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_java.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">XP 초기버젼 사용자는 자바 가상머신이 XP에 설치되어 있지 
                              않습니다. 학습 또는 사이트운영을 하시기 위해서는 이 소프트웨어가 필요합니다. </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://e-learninghmc.yeslearn.co.kr/data/ActiveTutor3.5.exe"><img src="/images/user/support/down_tutor.jpg" width="94" height="70" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_tutor.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">&quot;초보자를 위한 JAVA2프로그래밍&quot;과정을 
                              학습하시면 음성강의시 필요한 프로그램입니다. 다운받아 설치하시기 바랍니다. </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="25" align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><a href="http://e-learninghmc.yeslearn.co.kr/data/rp7com.exe"><img src="/images/user/support/down_real.jpg" border="0"></a></td>
                            <td width="501"><img src="/images/user/support/down_tit_real.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">ra, rm등으로 이루어진 강의를 볼수 있는 프로그램입니다. 
                              설치하시면 부가적인 기능도 활용할수 있습니다. <br>
                              최신 버전 및 플러그인은 http://www.real.com으로 가시면 이용하실수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="25" align="center" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"><table width="615" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="114" rowspan="4" valign="top"><img src="/images/user/support/down_nanumi.jpg" width="94" height="70"></td>
                            <td width="501"><img src="/images/user/support/down_tit_nanumi.gif" width="461" height="18"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td valign="top">전문직무,CyberMBA강좌를 학습하시려면 학습프로그램을 설치해야 
                              합니다. <br><br>
                               <table width="370" border="0" cellspacing="1" cellpadding="0">
                                              <tr bgcolor="#F0F0F0"> 
                                                <td width="93" height="25" align="center">프로그램명</td>
                                                <td width="178" height="25" align="center">환 
                                                  경</td>
                                                <td width="59" height="25" align="center">다운받기</td>
                                              </tr>
                                              <tr bgcolor="#FFFFFF"> 
                                                <td>나누미 (2.5버전)</td>
                                                <td>Window 95사용자</td>
                                                
                                  <td align="center"><a href="http://www.yeslearn.co.kr/anytime/contents/270/program/NaNumi2.5.exe"><img src="/images/user/button/btn_download.gif" border="0"></a></td>
                                              </tr>
                                              <tr bgcolor="#FFFFFF"> 
                                                <td>나누미(3.5버전)</td>
                                                <td>Window 98,ME, 2000,XP사용자</td>
                                                
                                  <td align="center"><a href="http://www.yeslearn.co.kr/anytime/contents/270/program/NaNumiPlayer3.5.exe"><img src="/images/user/button/btn_download.gif" border="0"></a></td>
                                              </tr>
                                              <tr bgcolor="#FFFFFF"> 
                                                <td>나누미(NT用)</td>
                                                <td>Window NT 사용자</td>
                                                
                                  <td align="center"><a href="http://www.yeslearn.co.kr/anytime/contents/270/program/nanumi_nt.exe"><img src="/images/user/button/btn_download.gif" border="0"></a></td>
                                              </tr>
                                            </table> 
                            </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/support/down_line.gif" ></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top">&nbsp;</td>
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