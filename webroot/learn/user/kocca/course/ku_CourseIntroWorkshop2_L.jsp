<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroWorkshop2_L.jsp
//  3. 개      요: 과정조회 (워크숍)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");

    String  v_sphere     = box.getString("p_sphere");


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenTabSelect(sphere){
      document.form1.p_sphere.value = sphere;
      document.form1.p_process.value  = 'SubjectListWorkshop';
      document.form1.action='/servlet/controller.course.KCourseIntroServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_sphere'   value="<%=v_sphere%>">
    <input type='hidden' name='p_subj'     value="">


                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME > 과정소개 > 워크숍과정</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_workshop.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/text_main_workshop.jpg" ></td>
                          </tr>
                          <tr>
                            <td height="19"></td>
                          </tr>
                        </table>
                        <!--롤오버탭 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="94"><img src="/images/user/kocca/course/tab_work02_on.gif" name="Image32"></td>
							<td width="3">&nbsp;</td>
							<td width="94"><a href="javascript:whenTabSelect('XXX')"><img src="/images/user/kocca/course/tab_work01.gif" name="Image311"  id="Image311" onMouseOver="MM_swapImage('Image311','','/images/user/kocca/course/tab_work01_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="490">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="5" colspan="12" ></td>
                          </tr>
                          <tr>
                            <td height="1" colspan="12" class="linecolor_cource02"></td>
                          </tr>
                          <tr>
                            <td height="17" colspan="12">&nbsp;</td>
                          </tr>
                        </table>
                        <!--장르별분야_서브텍스트 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="144"><div align="center"><img src="/images/user/kocca/course/text_t_workshop_info.gif" ></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_t_workshop_info_02.gif" ></td>
                          </tr>
                          <tr>
                            <td height="20" colspan="3" >&nbsp;</td>
                          </tr>
                        </table>
                        <!--장르별분야_내용 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/st_sub_box_top.gif"></td>
                          </tr>
                          <tr>
                            <td background="/images/user/kocca/course/bg_box.gif"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="27">&nbsp;</td>
                                  <td width="625">
                                  <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <!--tr>
                                        <td><img src="/images/user/kocca/course/st_workshop_pur.gif" ></td>
                                      </tr>
                                      <tr>
                                        <td height="20"></td>
                                      </tr-->
                                      <tr>
                                        <td><img src="/images/user/kocca/course/n_box_workshop_01.gif" ></td>
                                      </tr>
                                      <!--tr>
                                        <td height="5"></td>
                                      </tr>
                                      <tr>
                                        <td><img src="/images/user/kocca/course/box_workshop_02.gif" ></td>
                                      </tr>
                                      <tr>
                                        <td height="5"></td>
                                      </tr>
                                      <tr>
                                        <td><img src="/images/user/kocca/course/box_workshop_03.gif" ></td>
                                      </tr>
                                      <tr>
                                        <td height="5"></td>
                                      </tr-->
                                    </table>
                                    <!--개설과목 한줄 -->
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="15"></td>
                                      </tr>
                                      <tr>
                                        <td ><img src="/images/user/kocca/course/st_workshop_plan.gif" ></td>
                                      </tr>
                                      <tr>
                                        <td height="4"></td>
                                      </tr>
                                      <tr>
                                        <td >
                                          <table border="0" cellpadding="0" cellspacing="1" width="623" bgcolor="#C9C9C9">
                                            <tr height="25" bgcolor="#DDD4BA" align="center">
                                                <td width="28"><font color="000000">주</font></td>
                                                <td width="200"><font color="000000">강좌</font></td>
                                                <td width="240"><font color="000000">운영내용</font></td>
                                                <td width="155"><font color="000000">비고</font></td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>1</td>
                                                <td rowspan="8">
                                                   애니메이션워크숍<Br>
                                                   음반제작워크숍<Br>
                                                   캐릭터워크숍<Br>
                                                   모바일인터넷워크숍<Br>
                                                   출판만화워크숍<Br>
                                                   영화워크숍<Br>
												   영화감독워크숍<Br>
													만화가워크숍<Br>
                                                    <각 과정 단위 별 진행>
                                                </td>
                                                <td>오리엔테이션 & 1차 오프강의</td>
                                                <td>전체강좌 / 과정별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>2</td>
                                                <td>각 주차별 온라인 학습</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>3</td>
                                                <td>각 주차별 온라인 학습</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>4</td>
                                                <td>2차 오프 강의 및 토론</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>5</td>
                                                <td>각 주차별 온라인 학습</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>6</td>
                                                <td>각 주차별 온라인 학습</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>7</td>
                                                <td>3차 오프 강의 및 토론</td>
                                                <td>강좌별</td>
                                            </tr>
                                            <tr height="25" bgcolor="#FFFFFF" align="center">
                                                <td>8</td>
                                                <td>각 주차별 온라인 학습</td>
                                                <td>강좌별</td>
                                            </tr>
                                         </table><br>

                                        </td>
                                      </tr>
                                       <tr>
                                        <td height="10"></td>
                                      </tr>
                                      <tr>
                                        <td>
                                        <table width="625" border="0" cellspacing="0" cellpadding="0">
                                          <tr>
                                            <td height="5"><div align="left"><a name="curry"></a></div></td>
                                          </tr>
                                          <tr>
                                            <td><img src="/images/user/kocca/course/sst_curry.gif"></td>
                                          </tr>
                                          <tr>
                                            <td height="8"></td>
                                          </tr>
                                        </table>
                                        <table width="625" border="2" cellspacing="0"
                                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
                                          <tr  class="lcolor">
                                            <td height="3" colspan="4" class="linecolor_cource"></td>
                                          </tr>
                                          <tr>
                                            <td width="31" class="tbl_ytit">주</td>
                                            <td width="110" class="tbl_ytit">과정</td>
                                            <td width="357" class="tbl_ytit">운영내용<br></td>
                                            <td width="104"  class="tbl_ytit">비고</td>
                                          </tr>
                                          <tr>
                                            <td height="4" colspan="4"></td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">1주</td>
                                            <td class="tbl_grc" rowspan="9" bgcolor="#ecf2e3"><b>과정</b></td>
                                            <td class="tbl_bleft">오리엔테이션 & 1차 오프강의 </td>
                                            <td class="tbl_grc">전체강좌/과정별</td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">2주</td>
                                            <td class="tbl_bleft"> 각 주차별 온라인 학습</td>
                                            <td class="tbl_grc"> 과정별</td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">3주</td>
                                            <td class="tbl_bleft"> 각 주차별 온라인 학습</td>
                                            <td class="tbl_grc"> 과정별</td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">4주</td>
                                            <td class="tbl_bleft">2차 오프 강의 및 토론</td>
                                            <td class="tbl_grc">강좌별</td>
                                          </tr>
                                          <tr>
                                            <td height="4" ></td>
                                            <td height="4" ></td>
                                            <td height="4" ></td>
                                          </tr>
                                          <tr>
                                            <td height="29" class="tbl_grc">5주</td>
                                            <td class="tbl_bleft">각 주차별 온라인 학습</td>
                                            <td class="tbl_grc">과정별 </td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">6주</td>
                                            <td class="tbl_bleft">각 주차별 온라인 학습</td>
                                            <td class="tbl_grc">과정별 </td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">7주</td>
                                            <td class="tbl_bleft">3차 오프 강의 및 토론</td>
                                            <td class="tbl_grc">강좌별</td>
                                          </tr>
                                          <tr>
                                            <td height="26" class="tbl_grc">8주</td>
                                            <td class="tbl_bleft"> 각 주차별 온라인 학습</td>
                                            <td class="tbl_grc"> 과정별</td>
                                          </tr>
                                          <tr>
                                            <td height="4" colspan="4"></td>
                                          </tr>
                                        </table>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td height="20"></td>
                                      </tr>
                                    </table>
                                    <table width="625" border="2" cellspacing="0"
                                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
                                      <tr  class="lcolor">
                                        <td height="3" colspan="4" class="linecolor_cource"></td>
                                      </tr>
                                      <tr>
                                        <td bgcolor="#ebe7c2" width="40%" align="center"> 워크숍 과정 분야</td>
                                        <td class="tbl_ytit">연계 개별 상시과정</td>
                                      </tr>
                                       <tr>
                                        <td height="4" colspan="2"></td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">애니메이션 워크숍</td>
                                        <td class="tbl_gleft">
                                            애니메이션 산업의 연구<br>
                                            할리우스 에니메이션의 사례와 실제<br>
                                            애니메이션마케팅<br>
                                            애니메이션기획<br>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">음반제작 워크숍</td>
                                        <td class="tbl_gleft">
                                            음악 산업의 사례연구<br>
                                            대중음반마케팅<br>
                                            대중음반기획<br>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">캐릭터 워크숍</td>
                                        <td class="tbl_gleft">
                                            캐릭터 산업의 사례연구<br>
                                            캐릭터마케팅<br>
                                            캐릭터기획<br>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">모바일인터넷 워크숍</td>
                                        <td class="tbl_gleft">
                                            모바일인터넷 워크숍<br>
                                            모바일인터넷마케팅<br>
                                            모바일인터넷기획<br>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">출판만화 워크숍 </td>
                                        <td class="tbl_gleft">
                                            출판만화실무 출판<br>
                                            출판만화산업의 사례연구<br>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td class="tbl_grc">영화워크숍</td>
                                        <td class="tbl_gleft">
                                            영화실무<br>
                                            영화산업의 사례연구 <br>
                                        </td>
                                      </tr>
									  <tr>
                                        <td class="tbl_grc">영화감독 워크숍</td>
                                        <td class="tbl_gleft">
                                            영화실무<br>
                                            영화산업의 사례연구 <br>
                                        </td>
                                      </tr>
									  <tr>
                                        <td class="tbl_grc">만화가 워크숍</td>
                                        <td class="tbl_gleft">
                                            출판만화실무<br>
											출판만화산업의 사례연구
                                        </td>
                                      </tr>
                                    </table>
                                    </td>
                                  <td width="27">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/kocca/course/st_sub_box_bottom.gif" ></td>
                          </tr>
                        </table>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
