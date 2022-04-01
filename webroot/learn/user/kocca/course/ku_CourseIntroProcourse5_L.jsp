<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroProcourse5_L.jsp
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
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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
<body onload="MM_preloadImages('/images/user/kocca/course/tab_project_on.gif','/images/user/kocca/course/tab_market_on.gif','/images/user/kocca/course/tab_copyright_on.gif','/images/user/kocca/course/tab_location_on.gif','/images/user/kocca/course/tab_story_on.gif','/images/user/kocca/course/tab_ct_on.gif','/images/user/kocca/course/tab_pd_on.gif')">

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
                            <td><img src="/images/user/kocca/course/text_main.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="19"></td>
                          </tr>
                        </table>
                        <!--메뉴탭부분 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="82"><img src="/images/user/kocca/course/tab_infopro_on.gif" width="82" height="30" border="0" /></td>
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
                            <td width="98"><a href="javascript:whenTabSelect('008')"><img src="/images/user/kocca/course/tab_pd.gif" name="Image14" width="98" height="30" border="0"  id="Image14" onmouseover="MM_swapImage('Image14','','/images/user/kocca/course/tab_pd_on.gif',1)" onmouseout="MM_swapImgRestore()" /></a></td>
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
                        <!--전문가과정 안내 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="184"><div align="center"><img src="/images/user/kocca/course/text_sub_infopro.gif" w></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_sub_infopro02.gif" ></td>
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
                                  <td> <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_one.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="1" background="/images/user/kocca/course/bg_dot_line.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="7"></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73"><img src="/images/user/kocca/course/box_orientation.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_orientation.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73"><img src="/images/user/kocca/course/box_ouroffice.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_ouroffice.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73"><img src="/images/user/kocca/course/box_eduoffice.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_eduoffice.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="30"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_two.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="1" background="/images/user/kocca/course/bg_dot_line.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="7"></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73"><img src="/images/user/kocca/course/box_team.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_team.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="30"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_three.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="1" background="/images/user/kocca/course/bg_dot_line.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="7"></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73"><img src="/images/user/kocca/course/box_offline.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_offline.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="630" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr> 
                                        <td width="73" height="21"><img src="/images/user/kocca/course/box_community.gif" ></td>
                                        <td width="10">&nbsp;</td>
                                        <td><img src="/images/user/kocca/course/box_text_community.gif" ></td>
                                      </tr>
                                    </table>
                                    <table width="631" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="35"></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_pass.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_top.gif"></td>
                                      </tr>
                                      <tr bgcolor="#F9F7E8"> 
                                        <td width="19">&nbsp;</td>
                                        <td width="35" valign="top"><img src="/images/user/kocca/course/i_pass.gif" ></td>
                                        <td width="26">&nbsp;</td>
                                        <td width="552"><p><img src="/images/user/kocca/course/b_box.gif"> 
                                            한국 문화 콘덴츠진흥원 원장 명의의 과정 수료증을 수여합니다<br>
                                            <img src="/images/user/kocca/course/b_box.gif" > 
                                            수료이후에도 1년간 수강한 교육내용을 열람 가능합니다.<br>
                                            <img src="/images/user/kocca/course/b_box.gif" > 
                                            사이버문화 콘텐츠아카데미의 세미나 및 특강 등 행사에 초대합니다.<br>
                                            <img src="/images/user/kocca/course/b_box.gif" > 
                                            한국문화톤덴츠진흥원의 각종인력양성프로그램 참가시 우선 혜택을 
                                            드립니다.</p></td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_bottom.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="20" colspan="4">&nbsp;</td>
                                      </tr>
                                    </table></td>
                                  <td width="23">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_bottom.gif" width="680" height="22"></td>
                          </tr>
                          <tr> 
                            <td height="20">&nbsp;</td>
                          </tr>
                        </table>


</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
