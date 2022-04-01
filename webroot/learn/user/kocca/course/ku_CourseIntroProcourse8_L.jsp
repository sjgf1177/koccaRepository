<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroProcourse1_L.jsp
//  3. 개      요: 전문가 과정
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
    box.put("leftmenu","04");                       // 메뉴 아이디 세팅
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
      document.form1.p_process.value  = 'SubjectListProf';
      document.form1.action='/servlet/controller.course.KCourseIntroServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }
//-->
</SCRIPT>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<body onLoad="MM_preloadImages('images/user/kocca/course/tab_market_on.gif','/images/user/kocca/course/tab_copyright_on.gif','/images/user/kocca/course/tab_location_on.gif','/images/user/kocca/course/tab_story_on.gif','/images/user/kocca/course/tab_ct_on.gif','/images/user/kocca/course/tab_infopro_on.gif','/images/user/kocca/course/tab_project_on.gif','/images/user/kocca/course/tab_market_on.gif')">

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_sphere'   value="<%=v_sphere%>">
    <input type='hidden' name='p_subj'     value="">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 과정소개 > 전문가과정</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_prof.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/text_main.gif" width="680" height="140" ></td>
                          </tr>
                          <tr> 
                            <td height="19"></td>
                          </tr>
                        </table>
                        <!--메뉴탭부분 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="82"><a href="javascript:whenTabSelect('005')"><img src="/images/user/kocca/course/tab_infopro.gif" name="Image7" width="82" height="30" border="0"  id="Image7" onMouseOver="MM_swapImage('Image7','','/images/user/kocca/course/tab_infopro_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="82"><a href="javascript:whenTabSelect('001')"><img src="/images/user/kocca/course/tab_project.gif" name="Image8" width="82" height="30" border="0"  id="Image8" onmouseover="MM_swapImage('Image8','','/images/user/kocca/course/tab_project_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="82"><a href="javascript:whenTabSelect('002')"><img src="/images/user/kocca/course/tab_market.gif" name="Image9" width="81" height="30" border="0"  id="Image9" onmouseover="MM_swapImage('Image9','','/images/user/kocca/course/tab_market_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="82"><a href="javascript:whenTabSelect('003')"><img src="/images/user/kocca/course/tab_copyright.gif" name="Image10" width="82" height="30" border="0" id="Image10" onmouseover="MM_swapImage('Image10','','/images/user/kocca/course/tab_copyright_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="82"><a href="javascript:whenTabSelect('004')"><img src="/images/user/kocca/course/tab_location.gif" name="Image11" width="81" height="30" border="0"  id="Image11" onmouseover="MM_swapImage('Image11','','/images/user/kocca/course/tab_location_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td>&nbsp;</td>
                            <td width="82"><a href="javascript:whenTabSelect('006')"><img src="/images/user/kocca/course/tab_story.gif" name="Image12" width="81" height="30" border="0"  id="Image12" onmouseover="MM_swapImage('Image12','','/images/user/kocca/course/tab_story_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td>&nbsp;</td>
                            <td width="66"><a href="javascript:whenTabSelect('007')"><img src="/images/user/kocca/course/tab_ct.gif" name="Image13" width="66" height="30" border="0"  id="Image13" onmouseover="MM_swapImage('Image13','','/images/user/kocca/course/tab_ct_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
                            <td>&nbsp;</td>
                            <td width="98"><img src="/images/user/kocca/course/tab_pd_on.gif" name="Image14" width="98" height="30" border="0"  id="Image14"></td>
                          </tr>
                          <tr>
                            <td height="5" colspan="15" ></td>
                          </tr>
                          <tr>
                            <td height="1" colspan="15" class="linecolor_cource02"></td>
                          </tr>
                          <tr>
                            <td height="17" colspan="15">&nbsp;</td>
                          </tr>
                        </table>
                        <!--전략기획전문가 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="144"><div align="center"><img src="/images/user/kocca/course/text_sub_pd01.gif"></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" width="1" height="43" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_sub_pd02.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20" colspan="3" >&nbsp;</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td background="/images/user/kocca/course/bg_box.gif"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="26">&nbsp;</td>
                                  <td><table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="2"></td>
                                      </tr>
                                      <tr> 
                                        <td width="77" valign="top"><img src="/images/user/kocca/course/st_small_course.gif" width="96" height="14"></td>
                                        <td width="554"><img src="/images/user/kocca/course/im_pd.gif" width="230" height="80"></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="15"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/st_small_pageandprofer.gif" width="198" height="14"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
                                    
                                      <tr> 
                                        <td width="18" height="20" ><img src="/images/user/kocca/course/i_contents.gif" width="11" height="13">                                        </td>
                                        <td width="479"><strong>문화콘텐츠 프로듀싱</strong></td>
                                        <td width="76"><div align="right"></div></td>
                                        <td width="58"><!--a href="#"><img src="/images/user/kocca/course/i_sample_class.gif" border="0"></a--></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><table width="629" border="1" cellpadding="0" cellspacing="0" bordercolor="#cccccc">
                                            <tr> 
                                              <td><table width="627" border="0" cellspacing="0" cellpadding="0">
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                  <tr> 
                                                    <td width="7">&nbsp;</td>
                                                    <td width="95"><table width="86" border="0" cellpadding="0" cellspacing="3" bgcolor="#e7e7e7">
                                                        <tr> 
                                                          <td bgcolor="#FFFFFF"><table width="80" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
                                                              <tr> 
                                                                <td><img src="/images/user/kocca/course/pd_jjw.jpg" width="80" height="70" border="0"></td>
                                                              </tr>
                                                            </table></td>
                                                        </tr>
                                                      </table></td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td width="150" class="tbl_grc">정진완<br>(이글픽쳐스 교수)</td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td>&nbsp;</td>
                                                    <td width="360" bgcolor="#F7F1C5"  class="tbl_gleft"><p>문화콘텐츠 프로듀싱의 기획, 창작, 개발, 제작, 유통, 유지 단계를 이해하여 국내외 문화콘텐츠 프로듀싱의 나가야 할 방향에 대해서 알 수 있다.</p></td>
                                                    <td>&nbsp;</td>
                                                  </tr>
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                </table></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
 
                                      <tr> 
                                        <td width="17" height="20" ><img src="/images/user/kocca/course/i_contents.gif" > 
                                        </td>
                                        <td width="480"><strong>문화콘텐츠의 투자경제성평가</strong> 
                                        </td>
                                        <td width="76"><div align="right"></div></td>
                                        <td width="58"><!--a href="#"><img src="/images/user/kocca/course/i_sample_class.gif" border="0"></a--></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><table width="629" border="1" cellpadding="0" cellspacing="0" bordercolor="#cccccc">
                                            <tr> 
                                              <td><table width="627" border="0" cellspacing="0" cellpadding="0">
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                  <tr> 
                                                    <td width="7">&nbsp;</td>
                                                    <td width="95"><table width="86" border="0" cellpadding="0" cellspacing="3" bgcolor="#e7e7e7">
                                                        <tr> 
                                                          <td bgcolor="#FFFFFF"><table width="80" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
                                                              <tr> 
                                                                <td><img src="/images/user/kocca/course/pd_nkm.jpg" width="80" height="70" border="0"></td>
                                                              </tr>
                                                            </table></td>
                                                        </tr>
                                                      </table></td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td width="150" class="tbl_grc">남기문<br>(삼성전자 교수)</td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td>&nbsp;</td>
                                                    <td width="360" bgcolor="#F7F1C5"  class="tbl_gleft"><p>문화콘텐츠 투자환경 및 투자자의 특징을 이해하고, 투자실무와 이론을체계적으로 학습하여, 성공적인 자금 유치를 위한 종합적, 전문적인 지식과 실무를 습득할 수 있다.</p></td>
                                                    <td>&nbsp;</td>
                                                  </tr>
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                </table></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
 
                                      <tr> 
                                        <td width="17" height="20" ><img src="/images/user/kocca/course/i_contents.gif" > 
                                        </td>
                                        <td width="480"><strong>문화콘텐츠 프로젝트 관리실무Ⅱ</strong></td>
                                        <td width="76"><div align="right"></div></td>
                                        <td width="58"><!--a href="#"><img src="/images/user/kocca/course/i_sample_class.gif" border="0"></a--></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><table width="629" border="1" cellpadding="0" cellspacing="0" bordercolor="#cccccc">
                                            <tr> 
                                              <td><table width="627" border="0" cellspacing="0" cellpadding="0">
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                  <tr> 
                                                    <td width="7">&nbsp;</td>
                                                    <td width="95"><table width="86" border="0" cellpadding="0" cellspacing="3" bgcolor="#e7e7e7">
                                                        <tr> 
                                                          <td bgcolor="#FFFFFF"><table width="80" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
                                                              <tr> 
                                                                <td><img src="/images/user/kocca/course/pd_cjw.jpg" width="80" height="70" border="0"></td>
                                                              </tr>
                                                            </table></td>
                                                        </tr>
                                                      </table></td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td width="150" class="tbl_grc">최재원<br>(바른손 영화사업본부 대표)</td>
                                                    <td width="1"><img src="/images/user/kocca/course/vline.gif" ></td>
                                                    <td>&nbsp;</td>
                                                    <td width="360" bgcolor="#F7F1C5"  class="tbl_gleft"><p>일반적인 프로젝트 관리기법과 문화콘텐츠 관련 프로젝트의 특성의 차이점을 인식하여 문화콘텐츠 프로젝트 현장에 대한 이해와 적용도를 높일 수 있다.</p></td>
                                                    <td>&nbsp;</td>
                                                  </tr>
                                                  <tr> 
                                                    <td height="7" colspan="8"></td>
                                                  </tr>
                                                </table></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="5" colspan="4"></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td height="15"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/st_small_dayplan.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td>
                                          <table width="625" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
                                            <tr  class="lcolor"> 
                                              <td height="3" colspan="4" class="linecolor_cource"></td>
                                            </tr>
                                            <tr> 
                                              <td width="40" class="tbl_ytit">주</td>
                                              <td width="121" class="tbl_ytit">강좌</td>
                                              <td class="tbl_ytit">운영내용<br></td>
                                              <td width="103"  class="tbl_ytit">비고</td>
                                            </tr>
                                            <tr> 
                                              <td height="5" colspan="4" ></td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">1주</td>
                                              <td rowspan="4" bgcolor="#ECF2E3" class="tbl_grc"><font color="#000000">문화콘텐츠<br>프로듀싱</font></td>
                                              <td class="tbl_bleft">오리엔테이션 &amp; 
                                                1차 오프특강<br></td>
                                              <td class="tbl_grc">과정통합</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">2주</td>
                                              <td class="tbl_bleft">☎ 1차 오피스아워</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">3주</td>
                                              <td class="tbl_bleft">각 주차별 온라인 학습</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">4주</td>
                                              <td class="tbl_bleft">☎ 2차 오피스아워</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="5" colspan="4"></td>
                                            </tr>
                                            <tr> 
                                              <td height="29" class="tbl_grc">5주</td>
                                              <td rowspan="4" bgcolor="#E6EACA" class="tbl_grc"><font color="#000000">투자경제성평가</font></td>
                                              <td class="tbl_bleft">2차 오프특강</td>
                                              <td class="tbl_grc">과정통합</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">6주</td>
                                              <td class="tbl_bleft">☎ 3차 오피스아워</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">7주</td>
                                              <td class="tbl_bleft">각 주차별 온라인 학습</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">8주</td>
                                              <td class="tbl_bleft">☎ 4차 오피스아워<br></td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="5" colspan="4" ></td>
                                            </tr>
                                            <tr> 
                                              <td height="30" class="tbl_grc">9주</td>
                                              <td rowspan="4" bgcolor="#ECF2E3" class="tbl_grc"><font color="#000000">문화콘텐츠<br>프로젝트<br>관리실무Ⅱ</font></td>
                                              <td class="tbl_bleft">3차 오프특강</td>
                                              <td class="tbl_grc">과정통합</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">10주</td>
                                              <td class="tbl_bleft">☎ 5차 오피스아워</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">11주</td>
                                              <td class="tbl_bleft">각 주차별 온라인 학습</td>
                                              <td class="tbl_grc">전문가 과정별</td>
                                            </tr>
                                            <tr> 
                                              <td height="26" class="tbl_grc">12주</td>
                                              <td class="tbl_bleft">☎ 6차 오피스아워<br></td>
                                              <td class="tbl_grc">전문가 과정별 </td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="15"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/st_small_complete.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_top.gif" ></td>
                                      </tr>
                                      <tr bgcolor="#F9F7E8"> 
                                        <td width="19">&nbsp;</td>
                                        <td width="35" valign="top"><img src="/images/user/kocca/course/i_spack.gif" ></td>
                                        <td width="26">&nbsp;</td>
                                        <td width="552"><strong>수료기준</strong> 
                                          : 진도율 70점이상 &amp; 총점 60점이상<br> <strong>평가기준</strong> 
                                          : 진도율 40% , 총괄평가 15% , 팀프로젝트 30%, 참여율(토론,세미나 
                                          등)15%</td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_bottom.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="15"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/st_small_pay.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_top.gif" ></td>
                                      </tr>
                                      <tr bgcolor="#F9F7E8"> 
                                        <td width="19">&nbsp;</td>
                                        <td width="35" valign="top"><img src="/images/user/kocca/course/i_pay.gif" ></td>
                                        <td width="26">&nbsp;</td>
                                        <td width="552"><strong>12주 10만원 </strong> 
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_bottom.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="20" colspan="4">&nbsp;</td>
                                      </tr>
                                    </table> </td>
                                  <td width="23">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_bottom.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                        </table>


</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
