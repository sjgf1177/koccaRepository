<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: gu_SubjSearch_L.jsp
//  3. 개      요: 과정조회
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
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");

    String  v_lsearch         = box.getString("p_lsearch");
    String  v_lsearchtext     = box.getString("p_lsearchtext");

    String  v_subj            =  "";
    String  v_subjnm          =  "";
	String  v_sphere          =  "";
    String  v_isordinary      =  "";
    String  v_isworkshop      =  "";
    String  v_isunit          =  "";

    String  v_subjtype_value  =  "";
    String  v_subjnm_value    =  "";


    int     v_total           =  0;
    int     i                 =  0;
    double  v_distcode_avg    = 0.0;
    double  v_distcode1_avg   = 0.0;
    int  v_distcode_avg_value  = 0;
    int  v_distcode1_avg_value = 0;
    DecimalFormat df = new DecimalFormat("##0.0");

    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

    // 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();}
    }

    // 과정검색
    function subjSearch() {
        if (document.form1.p_lsearchtext.value=="") {
            alert("검색어를 입력해주세요");
            return;
        }

        document.form1.action = "/servlet/controller.propose.KMainSubjSearchServlet";   //
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }

 // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.form1.p_subj.value    = subj;
        document.form1.p_sphere.value = sphere;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjSearch';
        document.form1.action='/servlet/controller.course.KCourseIntroServlet';
        document.form1.target = "_self";
        document.form1.submit();
    }

    function whenPreShow(url) {
        open_window("openShow",url,"100","100","800","717","","","",true,true);
    }

//-->
</script>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_select'   value="">
    <input type='hidden' name='p_subj'     value="">
    <input type='hidden' name='p_sphere'   value="">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td class="location" > HOME > 과정소개 > 과정검색</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_intro.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="19"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/text_course_search.gif" ></td>
                          </tr>
                          <tr>
                            <td height="1" background="/images/user/kocca/course/bg_dot_line03.gif"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td width="42"><img src="/images/user/kocca/course/text_course_fraction.gif" ></td>
                                  <td width="31">
                                    <select name="p_lsearch" class="input2">
                                        <option value="">:::::전체:::::</option>
                                        <option value="isordinary='Y'" <% if (v_lsearch.equals("isordinary='Y'")) out.print("selected");%>>상시과정</option>
                                        <option value="isordinary='N'" <% if (v_lsearch.equals("isordinary='N'")) out.print("selected");%>>수시과정</option>
                                        <option value="iscourse='Y'"   <% if(v_lsearch.equals("iscourse='Y'"))    out.print("selected");%>>전문가과정</option>
                                        <option value="isworkshop='Y'" <% if(v_lsearch.equals("isworkshop='Y'"))  out.print("selected");%>>워크숍과정</option>
                                        <option value="isunit='Y'"     <% if(v_lsearch.equals("isunit='Y'"))      out.print("selected");%>>학점인증과정</option>
                                    </select>
                                  </td>
                                  <td width="10">&nbsp;</td>
                                  <td width="31"><img src="/images/user/kocca/course/text_course_name.gif" ></td>
                                  <td width="135"> <input type="text" class="input" name="p_lsearchtext" value="<%=v_lsearchtext%>" onkeypress="search_enter(event)" size="20"> 
                                  </td>
                                  <td width="48" align="right"><a href="javascript:subjSearch()"><img src="/images/user/kocca/button/btn_search.gif"  border="0"></a></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="680" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#DFDFE0"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="4" class="linecolor_cource"></td>
                          </tr>
                          <tr> 
                            <td width="73" class="tbl_ytit">분류</td>
                            <td width="450" class="tbl_ytit">과정명</td>
<!--                            <td width="145" class="tbl_ytit2">만족도<br></td> -->
                            <td width="86"  class="tbl_ytit2">기능</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="4" ></td>
                          </tr>
<%
                    v_total = list.size();
                    for(i = 0; i < v_total; i++) {
                        DataBox dbox  = (DataBox)list.get(i);

                        v_subj         = dbox.getString("d_subj");
                        v_subjnm       = dbox.getString("d_subjnm");
						v_sphere       = dbox.getString("d_sphere");
                        v_isordinary     = dbox.getString("d_isordinary");
                        v_isworkshop     = dbox.getString("d_isworkshop");
                        v_isunit         = dbox.getString("d_isunit");

                        v_distcode1_avg = dbox.getDouble("distcode1_avg");
                        v_distcode_avg  = dbox.getDouble("distcode_avg");

                        v_distcode_avg_value  = (int)((v_distcode_avg*100)/7);
                        v_distcode1_avg_value = (int)((v_distcode1_avg*100)/7);

                        if (v_isordinary.equals("Y")) {
                            v_subjtype_value  =  "상시과정";
                        } else {
                            v_subjtype_value  =  "수시과정";
                        }
                        if (v_isworkshop.equals("Y")) {
                            v_subjtype_value  =  "워크샵과정";
                        }
                        if (v_isunit.equals("Y")) {
                            v_subjtype_value  =  "학점인증과정";
                        }

                        v_subjnm_value       = v_subjnm;
                        if (!v_lsearchtext.equals("")) {
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>");
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toLowerCase(), "<font color=\"red\">" + v_lsearchtext.toLowerCase() + "</font>");
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toUpperCase(), "<font color=\"red\">" + v_lsearchtext.toUpperCase() + "</font>");
                        }
%>

                          <tr> 
                            <td height="26" class="tbl_grc"><%=v_subjtype_value%></td>
                            <td bgcolor="#ECF2E3" class="tbl_bleft"><font color="#000000"><%=v_subjnm%></font></td>
<!--
                            <td align="center"><table width="135" height="17" border="0" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td width="22" bgcolor="F7F7E3" class="font_08_black">3.8</td>
                                  <td width="9" bgcolor="F7F7E3" ><div align="left"><img src="/images/user/kocca/course/vline_dot.gif" ></div></td>
                                  <td width="86" bgcolor="F7F7E3" class="tbl_gleft"><img src="/images/user/kocca/course/star_on.gif" ><img src="/images/user/kocca/course/star_on.gif" width="12" height="11"><img src="/images/user/kocca/course/star_on.gif" width="12" height="11"><img src="/images/user/kocca/course/star_half.gif" width="12" height="11"><img src="/images/user/kocca/course/star_off.gif" width="12" height="11"> 
                                  </td>
                                </tr>
                              </table></td>
-->
                            <td class="tbl_grc"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_sphere%>')"><img src="/images/user/kocca/button/btn_in_view.gif" border="0"></a></td>
                          </tr>
<%
             }
%>
<%           if (v_total == 0 ) {                 %>
  <tr>
    <td colspan="4" class="tbl_grc">검색된 과정이 없습니다.</td>
  </tr>
<%           }                                    %>



                          <tr> 
                            <td height="5" colspan="4"></td>
                          </tr>
                        </table>


</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->