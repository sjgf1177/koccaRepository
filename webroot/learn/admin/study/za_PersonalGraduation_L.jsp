<%
//**********************************************************
//  1. 제      목: PERSONAL SELECT LIST
//  2. 프로그램명: za_PersonalGraduation_L.jsp
//  3. 개      요: 개인 수료과정 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 11. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%
    //DEFINED class&variable START
    String v_grcode     = "";
    String v_grcodenm   = "";
    String v_grseqnm   = "";
    String v_gyear      = "";
    String v_grseq      = "";
    String v_subj       = "";
    String v_year       = "";
    String v_subjnm     = "";
    String v_isnewcourse= "";
    String v_course     = "";
    String v_cyear      = "";
    String v_courseseq  = "";
    String v_coursenm   = "";
    String v_subjseq    = "";
    String v_subjseqgr  = "";
    String v_isonoff    = "";
    String v_edustart   = "";
    String v_eduurl     = "";
    String v_eduend     = "";
    String v_onoff_value= "";
    String v_owner      = "";
    String v_gradtxt    = "";
    String v_credit     = "";
    String v_upperclassname     = "";
    int    v_tstep      =  0;
    int    v_mtest      =  0;
    int    v_ftest      =  0;
    int    v_htest      =  0;
    int    v_report     =  0;
    int    v_etc1       =  0;
    int    v_etc2       =  0;
    int    v_avtstep    =  0;
    int    v_avmtest    =  0;
    int    v_avftest    =  0;
    int    v_avhtest    =  0;
    int    v_avreport   =  0;
    int    v_avetc1     =  0;
    int    v_avetc2     =  0;
    int    v_avscore    =  0;
    int    v_score      =  0;
    int    v_point      =  0;
    int     v_rowspan    = 0;
    int     i            = 0;
    ArrayList list2      = null;
    //DEFINED class&variable END
    list2 = (ArrayList)request.getAttribute("PersonalGraduationList");
%>
 <br>
                        <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
                          <tr>
                            <td colspan="16" class="table_top_line"></td>
                          </tr>
                          <tr>
                            <td class="table_title" width="8%"><b>교육그룹</b></td>
                            <td class="table_title" width="4%" ><b>년도</b></td>
                            <td class="table_title" width="5%" ><b>교육차수</b></td>
                            <td class="table_title" width="19%"><font class="text_color04">과정</font></td>
                            <td class="table_title" width="5%" ><b>과정차수</b></td>
                            <td class="table_title" width="7%"><b>분류</b></td>
                            <td class="table_title" width="15%"><b>교육기간</b></td>
                            <td class="table_title" width="5%"><b>진도율<br>(점수)</b></td>
                            <td class="table_title" width="4%" ><b>중간평가</b></td>
                            <td class="table_title" width="4%" ><b>최종평가</b></td>
                            <td class="table_title" width="4%" ><b>형성평가</b></td>
                            <td class="table_title" width="4%" ><b>리포트</b></td>
                            <td class="table_title" width="4%" ><b>참여도</b></td>
                            <td class="table_title" width="4%" ><b>기타</b></td>
                            <td class="table_title" width="4%" ><b>총점</b></td>
                            <td class="table_title" width="4%" ><b>이수여부</b></td>
                          </tr>
                      </table>
                      <div style='width:960px;height:230px;overflow:auto;visibility:visible;border:1'>
                      <table class="box_table_out_1" cellspacing="1" cellpadding="5" width="940" >
<%
                for(i = 0; i < list2.size(); i++) {
                    //StudyStatusData data2  = (StudyStatusData)list2.get(i);

                    DataBox dbox    = (DataBox)list2.get(i);
                    v_grcode     = dbox.getString("d_grcode");
                    v_grcodenm   = dbox.getString("d_grcodenm");
                    v_grseqnm    = dbox.getString("d_grseqnm");
                    v_gyear      = dbox.getString("d_gyear");
                    v_grseq      = dbox.getString("d_grseq");
                    v_course     = dbox.getString("d_course");
                    v_cyear      = dbox.getString("d_cyear");
                    v_courseseq  = dbox.getString("d_courseseq");
                    v_coursenm   = dbox.getString("d_coursenm");
                    v_subj       = dbox.getString("d_subj");
                    v_year       = dbox.getString("d_year");
                    v_subjnm     = dbox.getString("d_subjnm");
                    v_subjseq    = dbox.getString("d_subjseq");
                    v_subjseqgr  = dbox.getString("d_subjseqgr");
                    v_isonoff    = dbox.getString("d_isonoff");
                    v_edustart   = dbox.getString("d_edustart");
                    v_eduend     = dbox.getString("d_eduend");
                    v_tstep      = dbox.getInt("d_tstep");
                    v_mtest      = dbox.getInt("d_mtest");
                    v_ftest      = dbox.getInt("d_ftest");
                    v_htest      = dbox.getInt("d_htest");
                    v_report     = dbox.getInt("d_report");
                    v_etc1       = dbox.getInt("d_etc1");
                    v_etc2       = dbox.getInt("d_etc2");
                    v_avtstep    = dbox.getInt("d_avtstep");
                    v_avmtest    = dbox.getInt("d_avmtest");
                    v_avftest    = dbox.getInt("d_avftest");
                    v_avhtest    = dbox.getInt("d_avhtest");
                    v_avreport   = dbox.getInt("d_avreport");
                    v_avetc1     = dbox.getInt("d_avetc1");
                    v_avetc2     = dbox.getInt("d_avetc2");
                    v_score      = dbox.getInt("d_score");
                    v_point      = dbox.getInt("d_point");
                    v_gradtxt    = dbox.getString("d_graduatxt");
                    v_upperclassname    = dbox.getString("d_upperclassname");

                    v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");



                    v_owner = GetCodenm.get_owner(v_subj);

                    v_eduurl    = "/servlet/controller.contents.EduStart?p_isFromLMS=Y&p_subj="+v_subj;
                    v_eduurl    +="&p_year="+v_year+"&p_subjseq="+v_subjseq+"&p_userid="+v_userid+"&p_process=eduList";

                    //if(v_isonoff.equals("ON"))      {  v_onoff_value  = "사이버과정"; }
                    //else if(v_isonoff.equals("OFF")){  v_onoff_value  = "집합과정";   }
                    //else if(v_isonoff.equals("CON")){  v_onoff_value =   "위탁교육";   }
                    //else if(v_isonoff.equals("REP")){  v_onoff_value =   "통신교육";   }
                    //else                            {  v_onoff_value =   "";          }

               %>
                          <tr>
                            <td class="table_01"   width="8%"><%=v_grcodenm%></td>
                            <td class="table_02_1" width="4%" ><%=v_gyear%></td>
                            <td class="table_02_1" width="5%" ><%=v_grseq%></td>
                            <td class="table_02_2" width="19%"><font class="text_color04"><%=v_subjnm%></font></td>
                            <td class="table_02_1" width="5%" ><%=StringManager.cutZero(v_subjseqgr)%>차</td>
                            <td class="table_02_1" width="7%"><%=v_upperclassname%></td>
                            <td class="table_02_1" width="15%"><%=v_edustart%>~<%=v_eduend%></td>
                            <td class="table_02_1" width="5%"><%=v_tstep%>(<%=v_avtstep%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_mtest%>(<%=v_avmtest%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_ftest%>(<%=v_avftest%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_htest%>(<%=v_avhtest%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_report%>(<%=v_avreport%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_etc1%>(<%=v_avetc1%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_etc2%>(<%=v_avetc2%>)</td>
                            <td class="table_02_1" width="4%" ><%=v_score%></td>
                            <td class="table_02_1" width="4%" ><%=v_gradtxt%></td>
                          </tr>
          <%
              }
          if(i == 0){
          %>
              <tr>
                <td class="table_02_1" colspan="16">등록된 내용이 없습니다</td>
              </tr>
         <%  } %>
                        </table>
                        <!----------------- 수료과정 끝 ----------------->

                        <table>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        </div>