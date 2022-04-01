<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명: zu_SubjSearch_L.jsp
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
    box.put("leftmenu","03");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");

    String  v_lsearch         = box.getString("p_lsearch");
    String  v_lsearchtext     = box.getString("p_lsearchtext");

    String  v_upperclass      =  "";
    String  v_upperclassnm    =  "";
    String  v_isonoff         =  "";
    String  v_course          =  "";
    String  v_cyear           =  "";
    String  v_courseseq       =  "";
    String  v_coursenm        =  "";
    String  v_subj            =  "";
    String  v_year            =  "";
    String  v_subjnm          =  "";
    String  v_subjseq         =  "";
    String  v_propend         =  "";
    String  v_edustart        =  "";
    String  v_eduend          =  "";
    String  v_preurl          =  "";
    String  v_indate          =  "";
    String  v_proposetype     =  "";
    String  v_isnewcourse      =  "";
    String  v_isnewupperclass =  "";
    String  v_proposeyn       =  "";
    String  v_isonoff_value   =  "";
	String  v_subjnm_value    =  "";
    String  v_upperclassnm_value =  "";
    String  v_prop_start      =  "";
    String  v_prop_end        =  "N";
    String  v_prop_img        =  "";
    String  v_owner           = "";
    String  v_eduend_check    = "";
    int     v_studentlimit    =  0;
    int     v_stucnt          =  0;
    int     v_rowspan         =  0;
    int     v_rowspan_cnt     =  0;
    int     v_total           =  0;
    int     i                 =  0;
    double  v_distcode_avg    = 0.0;
    double  v_distcode1_avg   = 0.0;
    int  v_distcode_avg_value  = 0;
    int  v_distcode1_avg_value = 0;
    DecimalFormat df = new DecimalFormat("##0.0");

    ProposeCourseBean bean = new ProposeCourseBean();
    String  s_userid = box.getSession("userid");
    String  s_jikwi  = box.getSession("jikwi");
    String  v_prop_check = "";
    
    String v_lsearchsubject = box.getString("p_lsearchsubject");
    String ss_luserid   = box.getSession("userid");
    String ss_lusername = box.getSession("name");
    
    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
function whenSelection(process) {
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.p_process.value = process;  
    document.form1.submit();
}
    
function whenPreShow(url) {
    open_window("openShow",url,"100","100","800","717","","","",true,true);  
}    

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

        document.form1.action = "/servlet/controller.propose.MainSubjSearchServlet";   // 
        document.form1.p_process.value = "SubjSearch";
        document.form1.submit();
    }

    function whenSubjInfo(subj,subjnm,isonoff,upperclass,upperclassnm,process){
      document.form1.p_subj.value = subj;
      document.form1.p_subjnm.value = subjnm;
      document.form1.p_isonoff.value = isonoff;
      document.form1.p_upperclass.value = upperclass;
      document.form1.p_upperclassnm.value = upperclassnm;
      document.form1.p_process.value  = 'SubjectPreviewPage';
      document.form1.p_rprocess.value = 'SubjSearch';
      document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
      document.form1.submit();
    }                        

//-->
</script>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='topmenu'  value=''>
    <input type='hidden' name='leftmenu' value=''>
    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_cyear'>
    <input type='hidden' name='p_courseseq'>
    <input type='hidden' name='p_coursenm'>
    <input type='hidden' name='p_isonoff'>
    <input type='hidden' name='p_upperclass'>
    <input type='hidden' name='p_upperclassnm'>
    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>

    <input type='hidden' name='p_actionurl' value='/servlet/controller.propose.MainSubjSearchServlet'>
    <input type='hidden' name='p_rprocess'>


          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 수강신청/안내 &gt; 전체과정검색</td>
              </tr>
              <tr> 
                <td align="center" valign="top">
                <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/apply/<%=tem_subimgpath%>/tit_search.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="right" valign="top">
                      <table width="262" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="73"> 
                              <select name="p_lsearch">
                                <option value="isonoff" <% if (v_lsearch.equals("isonoff")) out.print("selected"); %>>구분</option>
                                <option value="upperclass" <% if (v_lsearch.equals("upperclass")) out.print("selected");%>>분류</option>
                                <option value="subjnm" <% if(v_lsearch.equals("subjnm")) out.print("selected");%>>과정명</option>
                              </select>
                            </td>
                            <td width="131"><input type="text" class="input" name="p_lsearchtext" value="<%=v_lsearchtext%>" onkeypress="search_enter(event)" size="20"></td>
                            <td width="58"><a href="javascript:subjSearch()"><img src="/images/user/button/btn_search.gif" alt="검색"></a></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="5"></td>
                    </tr>
                    <tr> 
                      <td valign="top"> 
                        <!-- 전체과정검색table  -->
                        <table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                          <tr  class="lcolor"> 
                            <td height="3" colspan="6" class="lcolor"></td>
                          </tr>
                          <tr> 
                            <td width="38" class="tbl_gtit2">구분</td>
                            <td width="79" class="tbl_gtit">분류</td>
                            <td width="247" class="tbl_gtit2">과정명</td>
                            <td width="104" class="tbl_gtit">만족도</td>
                            <td width="104" class="tbl_gtit2">콘텐츠평가</td>
                            <td width="51"  class="tbl_gtit">도입년도</td>
                          </tr>

<%
                    v_total = list.size();
                    for(i = 0; i < v_total; i++) {
                        DataBox dbox  = (DataBox)list.get(i);

                        v_isonoff      = dbox.getString("d_isonoff");
                        v_subj         = dbox.getString("d_subj");
                        v_upperclass   = dbox.getString("d_upperclass");
                        v_upperclassnm = dbox.getString("d_classname");
                        v_subjnm       = dbox.getString("d_subjnm");
                        v_indate       = dbox.getString("d_indate");
                        v_distcode1_avg = dbox.getDouble("distcode1_avg");
                        v_distcode_avg  = dbox.getDouble("distcode_avg");

                        v_distcode_avg_value  = (int)((v_distcode_avg*100)/7);
                        v_distcode1_avg_value = (int)((v_distcode1_avg*100)/7);
                        if(v_isonoff.equals("ON")) v_isonoff_value = "사이버";
                        else                       v_isonoff_value = "집합";
						v_subjnm_value       = v_subjnm;
						v_upperclassnm_value = v_upperclassnm;
                        if (!v_lsearchtext.equals("")&&v_lsearch.equals("isonoff")) {
                            v_isonoff_value = StringManager.replace(v_isonoff_value, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>"); 
                        } else if(!v_lsearchtext.equals("")&&v_lsearch.equals("upperclass")){
                            v_upperclassnm_value = StringManager.replace(v_upperclassnm, v_lsearchtext, "<font color=\"red\">" + v_lsearchtext + "</font>"); 
                        } else if(!v_lsearchtext.equals("")&&v_lsearch.equals("subjnm")){
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext 
, "<font color=\"red\">" + v_lsearchtext + "</font>"); 
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toLowerCase() 
, "<font color=\"red\">" + v_lsearchtext.toLowerCase() + "</font>"); 
                            v_subjnm_value = StringManager.replace(v_subjnm, v_lsearchtext.toUpperCase() 
, "<font color=\"red\">" + v_lsearchtext.toUpperCase() + "</font>"); 
                        }
%>

                          <tr> 
                            <td class="tbl_grc"><%=v_isonoff_value%></td>
                            <td class="tbl_bleft"><%=v_upperclassnm_value%></td>
                            <td class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>','<%=v_upperclass%>','<%=v_upperclassnm%>','SubjectPreviewPage')"><%=v_subjnm_value%></a></td>
                            <td valign="top" class="tbl_bleft">
                            <!-- 만족도 그래프 -->
                            <table width="100" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="80" colspan="3" valign="top">
                                  <table width="80" border="1" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#cccccc" >
                                      <tr>
                                        <td>
                                          <table width="<%=v_distcode1_avg_value%>%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td height="5" background="/images/user/apply/c_graph2_bg.gif"></td>
                                            </tr>
                                          </table>
                                        </td>
                                      </tr>
                                    </table> </td>
                                  <td width="20" align="center" valign="middle" class="font04"><%=df.format(v_distcode1_avg)%></td>
                                </tr>
                              </table></td>
                            <td valign="top" class="tbl_bleft">
                            <!-- 콘텐츠평가 그래프 -->
                            <table width="100" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="80" colspan="3" valign="top"> 
                                    <table width="80" border="1" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#cccccc" >
                                      <tr> 
                                        <td>
                                          <table width="<%=v_distcode_avg_value%>%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td  height="5" background="/images/user/apply/c_graph1_bg.gif"></td>
                                            </tr>
                                          </table>
                                        </td>
                                      </tr>
                                    </table></td>
                                  <td width="20" align="center" valign="middle" class="font04"><%=df.format(v_distcode_avg)%></td>
                                </tr>
                              </table></td>
                            <td class="tbl_grc"><%=v_indate%></td>
                          </tr>
<%
             }
%>
<%           if (v_total == 0 ) {                 %>
                          <tr> 
                            <td colspan="5" class="tbl_grc">검색된 과정이 없습니다.</td>
                          </tr>
<%           }                                    %>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                </td>
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