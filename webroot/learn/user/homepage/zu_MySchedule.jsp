
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.*" %>
<% 
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	String tem_imgpath = box.getSession("tem_imgpath");

	if (session.getAttribute("userid") == null) {
%>

<%
		String v_matchcode = box.getString("p_matchcode");
		if(v_matchcode.equals("")) v_matchcode ="W";

		// Best 교육과정 시작
		box.put("matchcode",v_matchcode);
		MainSubjSearchBean subjBean = new MainSubjSearchBean();
		ArrayList list2 = (ArrayList)subjBean.selectSubjTop3RecomList(box);

		int i = 0;
		String v_subj       = ""; 
		String v_subjnm     = "";
		String v_isonoff    = "";
		String v_introducefilenamenew	= "";
		String v_dir		= "/dp/bulletin/";
%>  

<SCRIPT LANGUAGE="JavaScript">
<!--
// 과정상세정보 POPUP
function whenSubjInfoPopup(subj,subjnm,isonoff){
    window.self.name = "SubjList";
    window.open("", "openSubjInfo", "scrollbars=no,width=750,height=550,scrollbars=yes,resizable=yes'");
    document.form1.target = "openSubjInfo"

    document.form1.p_subj.value = subj;
    document.form1.p_subjnm.value = subjnm;
    document.form1.p_isonoff.value = isonoff;
    document.form1.p_process.value = 'SubjectPreviewPopup';
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.submit();

    document.form1.target = window.self.name; 
}


// 추천과정 선택 (직무 OR 어학)
function whenSelection(matchcode){
	document.form1.target = '_self';
	document.form1.action="/servlet/controller.homepage.MainServlet";
    document.form1.p_matchcode.value = matchcode;
    document.form1.submit();
}

//-->
</SCRIPT>

                        <!-- lecture table start -->
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
<form name="form1" method="post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj'      value ="">
    <input type='hidden' name='p_subjnm'    value ="">
    <input type='hidden' name='p_isonoff'   value ="">
    <input type='hidden' name='p_matchcode' value ="W">
                          <tr>
                            <td colspan="2"><img src="<%=tem_imgpath%>lecture_tit.gif"></td>
                          </tr>
                          <tr>
                            <td><img src="<%=tem_imgpath%>lecture_text.gif"></td>
                            <td align="right">&nbsp;</td>
                          </tr>
                          <tr>
                            <td colspan="2"><table width="415" border="0" cellpadding="1" cellspacing="0" class="letable_out">
                                <tr>
                                  <td><table width="415" border="0" cellspacing="0" cellpadding="0" class="letable_in">
                                      <tr>
                                        <td class="padd01"> <table width="406" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                              <td width="59" valign="top"><table width="59" border="0" cellspacing="0" cellpadding="0">
<%if(v_matchcode.equals("W")){%>
                                                  <tr>
                                                    <td><img src="/images/user/homepage/lecture_t1_on.gif" width="59" height="19"></td>
                                                  </tr>
                                                  <tr>
                                                    <td><a href="javascript:whenSelection('L')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/user/homepage/lecture_t2_on.gif',1)"><img src="/images/user/homepage/lecture_t2.gif" name="Image24" width="59" height="19" border="0"></a></td>
                                                  </tr>
<%}else if(v_matchcode.equals("L")){%>
                                                  <tr>
                                                    <td><img src="/images/user/homepage/lecture_t2_on.gif" width="59" height="19"></td>
                                                  </tr>
                                                  <tr>
                                                    <td><a href="javascript:whenSelection('W')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','/images/user/homepage/lecture_t1_on.gif',1)"><img src="/images/user/homepage/lecture_t1.gif" name="Image24" width="59" height="19" border="0"></a></td>
                                                  </tr>
<%}%>
                                                  <tr>
                                                    <td>&nbsp;</td>
                                                  </tr>
                                                </table></td>
                                              <td width="347" valign="top" class="lecolor">
											    <table width="342" border="0" cellspacing="0" cellpadding="0">
<%
for(i = 0; i < list2.size(); i++) {
	DataBox dbox2 = (DataBox)list2.get(i);
	v_subj       = dbox2.getString("d_subj");
	v_subjnm     = dbox2.getString("d_subjnm");
	v_isonoff    = dbox2.getString("d_isonoff");
	v_introducefilenamenew	= dbox2.getString("d_introducefilenamenew");

	if(v_introducefilenamenew.equals("")) {
		v_introducefilenamenew = "/images/user/homepage/type1/lecture_img1.gif";
	}else {
		v_introducefilenamenew = v_dir + v_introducefilenamenew;
	}
%>
                                                  <tr>
                                                    <td width="25" height="18" align="center"><img src="/images/user/homepage/bl_lecture.gif"></td>
                                                    <td width="223" class="lec"><a href="javascript:whenSubjInfoPopup('<%=v_subj%>','<%=v_subjnm%>','<%=v_isonoff%>')" onMouseOver="MM_swapImage('Image25','','<%=v_introducefilenamenew%>',1)"><%=(v_subjnm.length()>16)?v_subjnm.substring(0,16)+"...":v_subjnm%></a></td>
	<%if(i==0){%>
                                                    <td width="94" rowspan="3" align="center"><img src="<%=v_introducefilenamenew%>" name="Image25" width="85" height="61"></td>
	<%}%>
                                                  </tr>
<%
}
if(i == 0){ 
%>
												  <tr>
													<td colspan="3">추천 과정이 없습니다.</td>
												  </tr>
<%
}
%>
                                                </table></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td height="15" colspan="2">&nbsp;</td>
                          </tr>
</form>
                        </table>

<% } else {         %>

<SCRIPT LANGUAGE="JavaScript">
<!--
// 학습창 OPEN
function scheduleOpen() {    
    open_window('openSchedule','/learn/user/homepage/zu_Schedule.jsp',100,100,300,310,true,true,true,true,true);
}

//-->
</SCRIPT>

<%

			
    String v_my_subj          = "";
    String v_my_year          = "";
    String v_my_subjnm        = "";
    String v_my_subjseq       = "";
    String v_my_edustart      = "";
    String v_my_eduend        = "";
    String v_my_edustart_value = "";
    String v_my_eduend_value   = "";
    String v_my_user_id       = box.getSession("userid");

    int ms = 0;


	Calendar myCalendar = Calendar.getInstance();

	int t_year = 0;
	int t_month = 0;
	int t_day = 0;
	int t_week = 0;
	int t_week_day = 0;
	int t_week_first = 0;
	int t_week_last = 0;
	int t_month_last = 0;
	String t_date = "";
	String r_month = "";

	int pYear    = 0;
	int pMonth   = 0;
	int pCurDay  = 0;
	
	String startDay = "";		// 한주의 시작일
	String endDay = "";			// 한주의 마지막일
	String tmpDay = "";

	
	t_year = myCalendar.get(myCalendar.YEAR);				// 현재 년도
	t_month = myCalendar.get(myCalendar.MONTH)+1;			// 현재 월
	t_day = myCalendar.get(myCalendar.DAY_OF_MONTH);		// 현재 일
	t_week_day = myCalendar.get(myCalendar.DAY_OF_WEEK)-1;	// 현재 요일
	t_week = myCalendar.get(myCalendar.WEEK_OF_MONTH);		// 현재 몇번째 주

	String [] weekly_date = new String [7];

	for(int i=0; i<7; i++){

		myCalendar.set(t_year,t_month-1,t_day-t_week_day+i);
		pYear    = myCalendar.get(Calendar.YEAR);
		pMonth   = myCalendar.get(Calendar.MONTH) + 1;
		pCurDay  = myCalendar.get(Calendar.DATE);

		tmpDay = pYear + "" + ((pMonth < 10) ? "0"+pMonth : ""+pMonth) + ((pCurDay < 10) ? "0"+pCurDay : ""+pCurDay);
		if(i == 0){
			startDay = tmpDay;		// 한주의 시작 날짜
		}else if(i== 6){		
			endDay = tmpDay;		// 한주의 마지막 날짜
		}
		
		weekly_date[i] = tmpDay;
	}

	box.put("edustart",startDay);
	box.put("eduend",endDay);

    MyScheduleBean myclassbean = new MyScheduleBean();
    ArrayList myclasslist = myclassbean.selectEducationSubjectList2(box);
%>

                        <!-- 나의일정table start -->
                        <table width="415" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="<%=tem_imgpath%>1myedu_tit.gif"></td>
                          </tr>
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td height="106" align="center" valign="top" background="<%=tem_imgpath%>my_table.gif" class="mycolor" style="padding-top:8px"> 
                              <table width="400" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="225" valign="top"> 
                                  <div style='width:230;height:90px;overflow:auto;scrollbar-3dlight-color:#B5B5B5;scrollbar-arrow-color:#B5B5B5;scrollbar-darkshadow-color:#FFFFFF;scrollbar-face-color:#FFFFFF;scrollbar-highlight-color:#FFFFFFc;scrollbar-shadow-color:#B5B5B5;'> 
                                      <table width="205" border="0" cellspacing="0" cellpadding="0" style="padding-left:3px">
<%
   for(ms = 0; ms < myclasslist.size(); ms++) {
        DataBox mydbox    = (DataBox)myclasslist.get(ms);

        v_my_subj          =  mydbox.getString("d_subj");
        v_my_year          =  mydbox.getString("d_year");
        v_my_subjnm        =  mydbox.getString("d_subjnm");
        v_my_subjseq       =  mydbox.getString("d_subjseq");
        v_my_edustart      =  mydbox.getString("d_edustart").substring(0,8);
        v_my_eduend        =  mydbox.getString("d_eduend").substring(0,8);

        v_my_edustart_value = FormatDate.getFormatDate(v_my_edustart,"yyyy/MM/dd");
        v_my_eduend_value   = FormatDate.getFormatDate(v_my_eduend,"yyyy/MM/dd");

	 for(int l = 0; l<7; l++){
		if(weekly_date[l].equals(v_my_edustart)){ 
%>
                                        <tr> 
                                          <td width="210" class="myday"><font color="006699"><%=v_my_edustart_value%></font> 
                                            [교육시작일] : <br> <%=v_my_subjnm%></td>
                                        </tr>
<%		}	// end if 
 		if(weekly_date[l].equals(v_my_eduend)){ %>
                                        <tr> 
                                          <td width="210" class="myday"><font color="006699"><%=v_my_eduend_value%></font> 
                                            [교육종료일] : <br> <%=v_my_subjnm%></td>
                                        </tr>
<%		}	// end if 
	  }	// end for
	}// end for 
%>
                                      </table>
                                    </div></td>
                                  <td valign="top"><table width="174" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="140" class="font04"><strong><%=t_year%>/<%=t_month%></strong> 
                                          (<%=t_week%>째주)</td>
                                        <td width="29"><a href="javascript:scheduleOpen()"><img src="/images/user/homepage/btn_more.gif" width="25" height="9" border="0"></a></td>
                                      </tr>
                                      <tr> 
                                        <td height="5" colspan="2"></td>
                                      </tr>
                                      <tr align="center"> 
                                        <td colspan="2"> 
                                          <!-- 일정달력 -->
                                          <table width="150"  cellspacing="1" cellpadding="3"   class="mytable_out">
                                            <tr> 
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_1.gif" width="6" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_2.gif" width="8" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_3.gif" width="6" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_4.gif" width="11" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_3.gif" width="6" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_5.gif" width="5" height="8"></td>
                                              <td class="myt_tit" width="12"><img src="/images/user/homepage/my_6.gif" width="6" height="8"></td>
                                            </tr>
                                            <tr>
											<% 
												for(int j=0; j<7; j++){

//													myCalendar.set(t_year,t_month-1,t_day-t_week_day+j);
//													pYear    = myCalendar.get(Calendar.YEAR);
//													pMonth   = myCalendar.get(Calendar.MONTH) + 1;
//													pCurDay  = myCalendar.get(Calendar.DATE);
											%>
												<td class="myt_td"><% if(j==0) out.println("<font color='red'>");%><% if(j==6) out.println("<font color='blue'>");%><%=weekly_date[j].substring(6,8)%></font></td>
											<%
											  }
											%>
											</tr>
                                            <tr class="myt_td"> 

<%for(int l = 0; l<7; l++){%>
											<td>
<%
    for(ms = 0; ms < myclasslist.size(); ms++) {
        DataBox mydbox    = (DataBox)myclasslist.get(ms);

        v_my_subj          =  mydbox.getString("d_subj");
        v_my_year          =  mydbox.getString("d_year");
        v_my_subjnm        =  mydbox.getString("d_subjnm");
        v_my_subjseq       =  mydbox.getString("d_subjseq");
        v_my_edustart      =  mydbox.getString("d_edustart").substring(0,8);
        v_my_eduend        =  mydbox.getString("d_eduend").substring(0,8);

        v_my_edustart_value = FormatDate.getFormatDate(v_my_edustart,"yyyy/MM/dd");
        v_my_eduend_value   = FormatDate.getFormatDate(v_my_eduend,"yyyy/MM/dd");

		if(weekly_date[l].equals(v_my_edustart)){ %>
												<img src='/images/user/homepage/bl_myday.gif' width='7' height='8' border='0' alt='교육시작일 : <%=v_my_subjnm%>'>
<%		}	// end if 
 		if(weekly_date[l].equals(v_my_eduend)){ 
%>
												<img src='/images/user/homepage/bl_myday.gif' width='7' height='8' border='0' alt='교육종료일 : <%=v_my_subjnm%>'>
<%
		}
	 }	// end for
%>
											</td>

<%	}// end for %>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td height="15">&nbsp;</td>
                          </tr>
                        </table>
<% }                %>

