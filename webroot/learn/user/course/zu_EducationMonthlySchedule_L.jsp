<%
//**********************************************************
//  1. 제      목: EDUCATION MONTHLY SCHEDULE
//  2. 프로그램명: zu_EducationMonthlySchedule_L.jsp
//  3. 개      요: 월간 교육 일정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "bean" class = "com.credu.propose.ProposeCourseBean"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅

    String  v_process   = box.getString("p_process");
    String  v_select    = box.getStringDefault("p_select","TOTAL");
    String  v_grcode    = box.getStringDefault("p_grcode","ALL");
    String  v_course    =  "";
    String  v_Bcourse   =  ""; //이전 코스
    String  v_Bcourseseq=  ""; //이전 코스 차수
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_Bsubj     =  ""; //이전 과정
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr = "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_edustart1 =  "";
    String  v_eduend1   =  "";
    String  v_isonoff   =  "";
    String  v_proposetype= "";
    String  v_proposeyn =  "";
    String  v_proposeend=  "";
    String  v_isonoff_value="";
    String  v_gubun_class_type= "";
    String  v_temp_code =  "";
    String  v_title     =  "";
    String  v_start     =  "";
    String  v_last      =  "";
    String  v_code      =  "";
    String  v_name      =  "";
    String  v_month     =  box.getStringDefault("p_month",FormatDate.getDate("MM"));
    int     v_studentlimit= 0;
    int     v_chk       =   0;
    int     v_stucnt    =   0;
    int     li1         =   0;
    int     li2         =   0;
    int     li3         =   0;
    int     sum         =   0;
    int     begin       =   0;
    int     end         =   0;
    int     oldbegin    =   0;
    int     oldend      =   0;
    int     v_total     =   0;
    int     i           =   0;
    boolean v_firstok   = false;

    ArrayList list      = null;
    //DEFINED class&variable END
    //out.print(v_month);
    list = (ArrayList)request.getAttribute("EducationMonthlyList");
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
	// 과정검색 엔터처리
    function search_enter(e)  {
        if (e.keyCode =='13'){  subjSearch();  }
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

    function whenSelect(sel){
        document.form1.p_select.value = sel;
        //alert(sel);
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
        document.form1.p_process.value = 'EducationMonthlySchedule';
        document.form1.submit();
    }

function whenSubjInfo(subj,year,subjseq,isonoff,subjnm) {
    //alert("1234");
    //window.self.name = "SeqPreviewPagePop";
    //var url = "/servlet/controller.propose.ProposeCourseServlet";
    
     var new_Open = window.open("/servlet/controller.propose.ProposeCourseServlet","seqInfo",'scrollbars=yes,width=747,height=600,resizable=no');
     document.form1.target = "seqInfo";
     document.form1.p_process.value = "SeqPreviewPage";
     document.form1.p_subj.value = subj;
     document.form1.p_year.value = year;
     document.form1.p_subjseq.value = subjseq;
     document.form1.p_isonoff.value = isonoff;
     document.form1.p_subjnm.value = subjnm;
     document.form1.action = "/servlet/controller.propose.ProposeCourseServlet";
     document.form1.submit();
}

function whenCourseSubjView(course,cyear,courseseq,coursenm){
    window.self.name = "schdule";
    open_window("openInfo","","100","100","478","220","","","","yes","");
    document.form1.target = "openInfo";
    document.form1.p_course.value = course;
    document.form1.p_cyear.value = cyear;
    document.form1.p_courseseq.value = courseseq;
    document.form1.p_coursenm.value = coursenm;
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.p_process.value = 'CourseSubjOpenPage';
    document.form1.submit();
    document.form1.target = window.self.name;
}

function selectMonth2(sel){
    document.form1.p_select.value = sel;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    //alert(document.form1.p_month.value);
    document.form1.p_process.value = 'EducationMonthlySchedule';
    document.form1.submit();
}
//-->
</script>


<form name="form1" method="post" enctype = "multipart/form-data">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='topmenu'  value=''>
    <input type='hidden' name='leftmenu' value=''>
    <input type='hidden' name='p_course'>
    <input type='hidden' name='p_cyear'>
    <input type='hidden' name='p_courseseq'>
    <input type='hidden' name='p_coursenm'>
    <input type='hidden' name='p_subjnm'>
    <input type='hidden' name='p_subj'>
    <input type='hidden' name='p_year'>
    <input type='hidden' name='p_subjseq'>
    <input type='hidden' name='p_proposestart'>
    <input type='hidden' name='p_proposeend'>
    <input type='hidden' name='p_proposeyn'>
    <input type='hidden' name='p_proposetype'>
    <input type='hidden' name='p_purl'>
    <input type='hidden' name='p_isonoff'>
    <input type='hidden' name='p_select' value='<%=v_select%>'>


          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 수강신청/안내 &gt; 연간교육일정</td>
              </tr>
              <tr> 
                <td align="center" valign="top">
                <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="75"><img src="/images/user/support/search_img.gif" alt="과정검색"></td>
                      <td width="75">
                        <select name="p_lsearch">
                          <option value="isonoff">구분</option>
                          <option value="upperclass">분류</option>
                          <option value="subjnm">과정명</option>
                        </select>
                      </td>
                      <td width="132"><input type="text" class="input" name="p_lsearchtext" onkeypress="search_enter(event)" size="20"></td>
                      <td width="394"><a href="javascript:subjSearch()"><img src="/images/user/button/btn_search.gif" alt="검색"></a></td>
                    </tr>
                    <tr> 
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/apply/<%=tem_subimgpath%>/tit_month.jpg" ></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>

                  <!-- 과정구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="5" colspan="4"></td>
                      </tr>
                      <tr>
                        <td width="98" valign="top">
                            <% if(v_select.equals("TOTAL") || v_select=="" ){ %>
                              <a href="javascript:whenSelect('TOTAL')"><img src="/images/user/apply/tab_total_on.gif" alt="전체"></a>
                            <% }else{ %>
                              <a href="javascript:whenSelect('TOTAL')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/apply/tab_total_on.gif',1)"><img src="/images/user/apply/tab_total.gif" alt="전체" name="Image37" border="0"></a>
                            <% } %>
                         </td>

                            <td>
                            <% if(v_select.equals("ON")){ %>
                              <a href="javascript:whenSelect('ON')"><IMG src="/images/user/apply/tab_cyber1_on.gif" alt="사이버과정" border="0"></a>
                            <% }else{ %>
                              <td width="98" valign="top" ><a href="javascript:whenSelect('ON')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/apply/tab_cyber1_on.gif',1)"><img src="/images/user/apply/tab_cyber1.gif" alt="사이버과정" name="Image37" border="0"></a>
                            <% } %>
                            </td>
                            <td>
                            <% if(v_select.equals("OFF")){ %>
                              <a href="javascript:whenSelect('OFF')"><IMG src="/images/user/apply/tab_jib1_on.gif" alt="집합과정" border="0"></a>
                            <% }else{ %>
                              <a href="javascript:whenSelect('OFF')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image29','','/images/user/apply/tab_jib1_on.gif',1)"><img src="/images/user/apply/tab_jib1.gif" alt="집합과정" name="Image29" width="98" height="22" border="0"></a>
                            <% } %>
                            </td>
                        <td width="381" ></td>
                    </tr>
                    <tr>
                      <td height="20" colspan="4"></td>
                    </tr>
                  </table>


                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="560" valign="top"><img src="/images/user/apply/year_text.gif" width="201" height="17"> 
                      </td>
                      <td width="172" align="right" valign="top">
<%
  
                    SelectEduBean selectEdu = new SelectEduBean();
                    String  gyear    = box.getStringDefault("gyear",FormatDate.getDate("yyyy"));

                    String yearStr  = "";
                    String monthStr  = "";
                    yearStr = "<select name='gyear' onChange=whenSelect('"+v_select+"') style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 65px;height:19px;font-size:9pt;'>";

                    ArrayList gyearList = selectEdu.getGyear(box);
                    for(int k = 0; k < gyearList.size(); k++) {
                    DataBox dbox = (DataBox)gyearList.get(k);
                    yearStr += "<option value = '"+ dbox.getString("d_gyear")+"' ";
                    if (gyear.equals(dbox.getString("d_gyear"))) {
                      yearStr += " selected";
                    }
                    yearStr += " >";
                    yearStr += dbox.getString("d_gyear");
                    yearStr += "</option>";
                    }
                    yearStr += "</select>";
                    //out.print(yearStr);

                    monthStr  = "<select name='p_month' onchange=javascript:selectMonth2('"+v_select+"') ";
                    monthStr += "style='border-style:solid;border-width: 1px 1px 1px 1px;border-color:cccccc;color:333333;font-size:9pt;background-color:none;width : 50px;height:19px;font-size:9pt;'>";
                    DecimalFormat df = new DecimalFormat("00");

                    for(int l=1; l<=12; l++){
                    monthStr += "<option value="+df.format(l);
                    if (v_month.equals(df.format(l))) {
                      monthStr +=  "  selected";
                    }
                    monthStr += ">"+(l)+"월</option>";
                    }
                    monthStr += "</select>";
%>

                       <table width="162" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="58"> 
                                <%=yearStr%> 
                            </td>&nbsp;
                            <td width="53">
                                <%=monthStr%>
                            </td>&nbsp;
                            <td width="61"><A HREF="javascript:selectMonth2('<%=v_select%>')"><img src="/images/user/button/btn_search.gif" alt="검색색" border="0"></A></td>
                         </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="2"></td>
                    </tr>
                  </table>

                  <table width="695" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td> 
                      <table width="675"border="2" cellspacing="0" 
                         cellpadding="0"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
                         <tr  class="lcolor"> 
                            <td height="3" colspan="32" class="lcolor"></td>
                          </tr>
                          <tr>
                            <td width="148" class="tbl_gtit">과정명</td>
                            <td width="17" class="year_textb">1</td>
                            <td width="17" class="year_textb">2</td>
                            <td width="17" class="year_textb">3</td>
                            <td width="17" class="year_textb">4</td>
                            <td width="17" class="year_textb">5</td>
                            <td width="17" class="year_textb">6</td>
                            <td width="17" class="year_textb">7</td>
                            <td width="17" class="year_textb">8</td>
                            <td width="17" class="year_textb">9</td>
                            <td width="17" class="year_textb">10</td>
                            <td width="17" class="year_textb">11</td>
                            <td width="17" class="year_textb">12</td>
                            <td width="17" class="year_textb">13</td>
                            <td width="17" class="year_textb">14</td>
                            <td width="17" class="year_textb">15</td>
                            <td width="17" class="year_textb">16</td>
                            <td width="17" class="year_textb">17</td>
                            <td width="17" class="year_textb">18</td>
                            <td width="17" class="year_textb">19</td>
                            <td width="17" class="year_textb">20</td>
                            <td width="17" class="year_textb">21</td>
                            <td width="17" class="year_textb">22</td>
                            <td width="17" class="year_textb">23</td>
                            <td width="17" class="year_textb">24</td>
                            <td width="17" class="year_textb">25</td>
                            <td width="17" class="year_textb">26</td>
                            <td width="17" class="year_textb">27</td>
                            <td width="17" class="year_textb">28</td>
                            <td width="17" class="year_textb">29</td>
                            <td width="17" class="year_textb">30</td>
                            <td width="17" class="year_textb">31</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td width="695" valign="top">
                       <div style='width:695px;height:200px;overflow:auto;scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#FFFFFF;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'>
                          <table width="675" border="2" cellspacing="0" cellpadding="0"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">

                                <%
            for(i = 0; i < list.size(); i++) {
                ProposeCourseData data  = (ProposeCourseData)list.get(i);
                v_course        =  data.getCourse();
                v_cyear         =  data.getCyear();
                v_courseseq     =  data.getCourseseq();
                v_coursenm      =  data.getCoursenm();
                v_subj          =  data.getSubj();
                v_year          =  data.getYear();
                v_subjnm        =  data.getSubjnm();
                v_subjseq       =  data.getSubjseq();
                v_subjseqgr     =  data.getSubjseqgr();
                v_edustart      =  data.getEdustart();
                v_eduend        =  data.getEduend();
                v_isonoff       =  data.getIsonoff();
                v_proposetype   =  data.getProposetype();
                
                v_proposeend    =  data.getProposeend();
                v_studentlimit  =  data.getStudentlimit();
                v_stucnt        =  data.getStucnt();

                if(v_isonoff.equals("ON"))      {   v_isonoff_value =   "사이버";
                }else if(v_isonoff.equals("OFF")){  v_isonoff_value =   "집합";     }


                if(v_isonoff.equals("ON"))        { v_gubun_class_type = "/images/user/apply/year_cyber_bg.gif";  
                }else if(v_isonoff.equals("OFF")) { v_gubun_class_type = "/images/user/apply/year_jib_bg.gif";    
                }

                int idx = 0;
                idx = v_subjnm.indexOf("[통신]");
                if(idx >-1) { v_gubun_class_type = "graph3"; }  //통신

                //월간 그래프 관련 START

                //날짜까지 substring
                if(!v_edustart.equals("")){   v_edustart = v_edustart.substring(0,8);
                }else{                        v_edustart = "11111111";                 }

                if(!v_eduend.equals("")){     v_eduend = v_eduend.substring(0,8);
                }else{                        v_eduend = v_edustart;                   }

                    v_start   =   v_year.substring(0,4) + v_month + "01";   //YYYYMM01
                    v_last    =   v_year.substring(0,4) + v_month + "31";   //YYYYMM31

                v_edustart1 = (Integer.parseInt(v_edustart) < Integer.parseInt(v_start)) ? v_start : v_edustart;   //20041129 20041201
                v_eduend1 = (Integer.parseInt(v_eduend) > Integer.parseInt(v_last)) ? v_last : v_eduend;  // 20041203 20041231
//out.println(v_edustart1);
//out.println(v_eduend1);
                
                li1 = bean.datediff(v_start,v_edustart1);                                  //달의 시작부터 교육시작일까지의 차
                li2 = bean.datediff(v_edustart1,v_eduend1);                                //교육기간
                li3 = bean.datediff(v_eduend1,v_last);                                     //교육종료부터 달의 말일까지의 차
                
                sum = li1 + li2 + li3;
                //if(v_month.equals("01")||v_month.equals("03")||v_month.equals("05")||v_month.equals("07")||v_month.equals("08")||v_month.equals("10")||v_month.equals("12")){
                //  sum += 1;
                //}
                //out.print(sum);

//out.println(li1+",  "+li2+",  "+li3+"<br>");

                li1 = (int) Math.round(((float)li1 / sum * 100));               
                li2 = (int) Math.round(((float)li2 / sum * 100));
                if (li2 == 0) li2 = 1;
                li3 = 100 - (li1 + li2);

//out.println(li1+",  "+li2+",  "+li3);

                begin = li1;                //시작위치
                end = li1 + li2;
                //월간 그래프 관련 END

                    if ( !v_Bsubj.equals(v_subj) ) {    // 과정이 바뀐경우
                        if ( v_chk != 0 ) {             //처음인지 여부
%>
                                    <td width="<%= (100 - oldend) %>%"></td>
                                  </tr>
                                </table>
                              </td>
                            </tr>
                        
                        <%
                            v_firstok = false;
                            v_chk = 0;
                        }
%>
                            <tr> 
                              <td width="129" class="tblfont_gleft2"><%=v_subjnm%></td>
                              <td width="540" background="/images/user/apply/month_bg.gif">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0" >
                                  <tr> 
<%
                    }
                        v_chk = 1;
                    if (!v_firstok) { //

                        out.println(" <td width=\""+li1+"%\"></td> ");

                        //out.println(" <td width=\""+li2+"%\" background=\""+v_gubun_class_type+"\"   title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'>&nbsp;</td> ");
                        out.println(" <td width=\""+li2+"%\"  title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"차\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'><a href=\"javascript:whenSubjInfo('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_isonoff+"','"+v_subjnm+"')\"><img src=\""+v_gubun_class_type+"\" width='100%' height='12' border=0></a></td> ");
                        v_firstok = true;
                    }else if (begin <= oldend){
                      li2 = end - oldend;
                      if (li2 < 0)  li2 = 0;
                      if (li2 == 0) li2 = 1;
                        //out.println(" <td width=\""+li2+"%\" background=\""+v_gubun_class_type+"\"   title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'>&nbsp;</td> ");
                        out.println(" <td width=\""+li2+"%\"  title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"차\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'><a href=\"javascript:whenSubjInfo('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_isonoff+"','"+v_subjnm+"')\"><img src=\""+v_gubun_class_type+"\" width='100%' height='12' border=0></a></td> ");
                    }else{
                        li3 =  begin - oldend;
                        out.println(" <td width=\""+li3+"%\">&nbsp;</td> ");
                        //out.println(" <td width=\""+li2+"%\" background=\""+v_gubun_class_type+"\"   title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'>&nbsp;</td> ");
                        out.println(" <td width=\""+li2+"%\"  title=\"차수 :  "+StringManager.cutZero(v_subjseqgr)+"차\n교육기간 : "+FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")+"~"+FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd")+"\"  style='cursor:hand'><a href=\"javascript:whenSubjInfo('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_isonoff+"','"+v_subjnm+"')\"><img src=\""+v_gubun_class_type+"\" width='100%' height='12' border=0></a></td> ");
                    }

                    v_Bsubj = v_subj;
                    oldend = end;
                    

        }   // for end

          if ( v_chk == 1 ) {

%>
                                        <td height="12" width="<%= (100 - end) %>"></td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>
<%
          }
          if(i == 0){
          %>
          <tr>
            <td height="20" colspan="2" align="center">등록된과정이 없습니다.</td>
          </tr>
          <%
          }
    %>

                          </table>
                       </div>
                      </td>
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