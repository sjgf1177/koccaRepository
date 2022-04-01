<%
//********************************************************************
//  1. 제      목: 마스터폼 -진도/목차화면 - Normal
//  2. 프로그램명 : z_EduChk_List_YESL.jsp
//  3. 개      요: 마스터폼 -진도/목차화면 - Normal
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 7
//  7. 수      정:
//********************************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  p_subj, p_year, p_subjseq, p_userid;
	String  v_edutime = "";  // 학습시간
	String  v_edudate = "";  // 최근학습일
	int     v_educount= 0;   // 강의실접근횟수

    // 추가
    DecimalFormat df = new DecimalFormat("###.00");    
    double  v_totscore =0.0;
    double  v_ftestscore = 0.0;
    double  v_htestscore = 0.0;
    double  v_reportscore = 0.0;
    String v_isgraduated = "";
    
    
    String  p_isFromLMS = box.getString("p_isFromLMS");
    if (p_isFromLMS.equals("Y")){
        p_subj      = box.getString("p_subj");
        p_year      = box.getString("p_year");
        p_subjseq   = box.getString("p_subjseq");
        p_userid    = box.getString("p_userid");
    }else{
        p_subj      = box.getSession("s_subj");
        p_year      = box.getSession("s_year");
        p_subjseq   = box.getSession("s_subjseq");
        p_userid    = box.getSession("userid");
    }
    
	if (p_subj.equals("")) p_subj=box.getString("p_subj");
	
	p_subj      = box.getStringDefault("p_subj"    , box.getSession("s_subj"));
  p_year      = box.getStringDefault("p_year"    , box.getSession("s_year"));
  p_subjseq   = box.getStringDefault("p_subjseq" , box.getSession("s_subjseq"));
  p_userid    = box.getStringDefault("p_userid"  , box.getSession("userid"));


	String p_subjnm = box.getString("p_subjnm");  // 과정명
	String p_iurl   = box.getString("p_iurl");    // 외주url(외주:'1')
	String v_iseduend="";
	v_iseduend= box.getString("p_iseduend");    // 복습(복습:'Y')
		
    if(p_iurl.equals("")) p_iurl="0";
    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율

    MasterFormData  md  = (MasterFormData)request.getAttribute("MasterFormData");
    ArrayList   brlist  = null;
    //if (md.getIsmfbranch().equals("Y")){        //분기식 과정이면 분기선택 링크 생성.
    //    brlist = (ArrayList)request.getAttribute("BranchList");
    //}

    ArrayList  list = (ArrayList)request.getAttribute("EduList");
    EduListData x = null;

    ArrayList  list2 = (ArrayList)request.getAttribute("EduTime");  // 학습시간
    EduListData edutime = null;
	if(list2.size()>0) {
		edutime = (EduListData)list2.get(0);
		v_edutime = edutime.getTotal_time();
		v_edudate = edutime.getFirst_edu();
		v_educount= edutime.getLesson_count();
    }

    EduScoreData    sd = (EduScoreData)request.getAttribute("EduScore");
    EduScoreDataSub sds;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
</head>
<script language="javascript">
    function init() {
        this.moveTo(0,0);
        this.resizeTo(880,900);
    }

    function whenSelect(xx){
        opener.parent.etop.jumpTo(xx);
        window.close();
    }
    function whenBr(lesson,branch){
        opener.parent.etop.whenBranchFromProcessWin(lesson,branch);
        window.close();
    }
    function whenExamSelect(ptype,lesson){
        //var url = '/servlet/controller.contents.EduStart?p_process=exam&p_lesson='+lesson+'&p_type='+ptype;
        //opener.parent.ebody.location = url;
        opener.parent.etop.goExam(lesson,ptype);
        window.close();
    }
    function goReport(urls){
        window.open(urls,"Reports","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=700,height=500").focus();
    }
</script>


<body topmargin="0" leftmargin="0" onLoad='init();'>
<%=p_isFromLMS%>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="850" height="100%">
  <tr>
    <td>
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr>
          <td align="center" valign="top" class="body_color">
<br>
<form name='form1' method='post'>
            <!----------------- 타이틀 시작 ----------------->
  <table border=0 cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    <tr> 
      <td><img src="/images/user/study/course_title.gif"></td>
      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      <td><img src="/images/user/study/gongi_tail.gif"></td>
    </tr>
  </table> <br>
<% if(!v_iseduend.equals("Y")){ %>     
  
    <!--외주인경우-->
    <% if(p_iurl.equals("1")){ %>    

            <table width="773" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="663" class="font_ex">아래 성적은 매주 화요일마다 업데이트가 됩니다. <br>
                  오늘의 점수와 차이가 있을 수 있습니다. 자세한 내용은 과정내의 성적보기를 참조하세요</td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
    <% } %>
            <!----------------- 타이틀 끝 ---------------->                   
			<!----------------- 나의 진도율, 권장 진도율 시작 ----------------->
            <table width="786" border="0" cellspacing="0" cellpadding="0">
              <tr >
                <td  width="13" background="/images/user/study/box_t_bg.gif"><img src="/images/user/study/box_t_head.gif"></td>
                <td width="100%" background="/images/user/study/box_t_bg.gif">&nbsp;</td>
                <td align="right" width="13" background="/images/user/study/box_t_bg.gif"><img src="/images/user/study/box_t_tail.gif"></td>
              </tr>
              <tr>
                <td background="/images/user/study/box_l_bg.gif">&nbsp;</td>
                <td><table width="750" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="146" height="27" class="tblfont_sindoleft">나의 
                        진도율&nbsp:&nbsp<font color="#006699"><!--%=v_progress%--><%=sd.getTstep()%>%</font></td>
                      <td width="254"><table width="100%" border="1" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#cccccc" >
                          <tr> 
                            <td><table width="<%=sd.getTstep()+1%>%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="7" background="/images/user/study/stu_graph03.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7" ></td>
                          </tr>
                        </table></td>
                      <td width="22" rowspan="2" align="center"><img src="/images/user/study/stu_box_vline.gif" width="7" height="55"></td>
                      <td width="115" class="tblfont_sindoleft">남은 학습기간 : </td>
                      <td width="213" class="tbl_gleft"><%=FormatDate.getDate("yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(sd.getEduend(),"yyyy/MM/dd")%></td>
                    </tr>
                    <tr> 
                      <td class="tblfont_sindoleft">권장 진도율 : <font color="#006699"><%=v_promotion%>%</font></td>
                      <td><table width="100%" border="1" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#cccccc" >
                          <tr> 
                            <td><table width="<%=v_promotion+1%>%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="7" background="/images/user/study/stu_graph02.gif"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                      <td colspan="2">&nbsp;</td>
                    </tr>
					<!--
                    <tr> 
                      <td colspan="5" class="tblfont_sindoleft">
					  <table width="717" border="0" cellspacing="0" cellpadding="0">
                          <tr > 
                            <td background="/images/user/study/stu_box_vline2.gif" height="4" colspan="6"></td>
                          </tr>
                          <tr > 
                            <td height="4" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td width="70"><font color="#000000"><strong>학습시간 
                              :</strong></font> </td>
                            <td width="168"><%=v_edutime%></td>
                            <td width="79"><font color="#000000"><strong>최근학습일:</strong></font> 
                            </td>
                            <td width="159"><%=FormatDate.getFormatDate(v_edudate,"yyyy/MM/dd")%></td>
                            <td width="106"><font color="#000000"><strong>강의실접근횟수 
                              :</strong> </font></td>
                            <td width="135"><%=v_educount%> 번</td>
                          </tr>
                        </table></td>
                    </tr>
					-->
                  </table></td>
                <td background="/images/user/study/box_r_bg.gif">&nbsp;</td>
              </tr>
              <tr >
                <td width="13" background="/images/user/study/box_bo_bg.gif"><img src="/images/user/study/box_bo_head.gif"></td>
                <td background="/images/user/study/box_bo_bg.gif">&nbsp;</td>
                <td width="13" background="/images/user/study/box_bo_bg.gif"><img src="/images/user/study/box_bo_tail.gif"></td>
              </tr>
            </table>
            <% } %>
</form>            
            <!----------------- 나의 진도율, 권장 진도율 끝 ----------------->
            <br>
            <!----------------- 과정 시작 ----------------------------------->
            <table width="786" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="116" valign="top"><img src="/images/user/study/stu_leftimg.gif" width="116" height="79"></td>
                <td colspan="2">
				<table width="650" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="84"><img src="/images/user/study/stu_courname.gif" width="81" height="14"></td>
                      <td width="566" class="tblfont_sindotit"><%=GetCodenm.get_subjnm(p_subj)%></td>
                    </tr>
                    <tr > 
                      <td background="/images/user/study/stu_box_vline2.gif" height="4" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/study/stu_gigan.gif" width="81" height="14"></td>
                      <td class="tblfont_gleft2"><%=FormatDate.getFormatDate(sd.getEdustart(),"yyyy/MM/dd")%>
                                                ~<%=FormatDate.getFormatDate(sd.getEduend(),"yyyy/MM/dd")%></td>
                    </tr>
                  </table></td>
              </tr>
            </table> 
            <!----------------- 과정 끝 ----------------->
            <br>





            <!----------------- 점수현황 ----------------->

                        <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                            <tr>
                                <td class="board_color_line"></td>
                            </tr>
                        </table>
                        <table cellspacing="0" cellpadding="0" class="open_board_table_out2" align="center">
                            <tr>
                                <td>
                                    <table cellspacing="1" cellpadding="3" class="table2">
                                        <tr>
                                            <td class="board_title_bg1">&nbsp;</td>
                                            <td class="board_title_bg1" width="20%">이수기준점수</td>
                                            <td class="board_title_bg1" width="20%">가중치(%)</td>
                                            <td class="board_title_bg1" width="20%">취득점수</td>
                                            <td class="board_title_bg1" width="20%">환산점수</td>
                                        </tr>

<%                     Hashtable ht;
                           int htsize=0;
                           String v_datatype="";
                           if(sd!=null) {
                               htsize=sd.eduScoreList.size();
                               for (int i=0; i<htsize; i++) {
                                   //sds = (EduScoreDataSub)sd.eduScoreList.get(String.valueOf(i));
                                   if((Hashtable)sd.getEduScoreList() != null){
                                       ht  = (Hashtable)sd.getEduScoreList();
                                       sds = (EduScoreDataSub)ht.get(String.valueOf(i));
                                       v_datatype=get_datatypenm(sds.getDatatype());
  %>
                                        <tr>
                                            <td class="board_text8"><%=get_datatypenm(sds.getDatatype())%></td>
                                            <td class="board_text1"><%=sds.getGradscore()%></td>
                                            <td class="board_text1"><%=sds.getWeight()%></td>
                                            <td class="board_text1"><%=sds.getScore()%></td>
                                            <td class="board_text1"><%=sds.getAvscore()%></td>
                                        </tr>
<%										v_totscore=v_totscore+sds.getAvscore();

											if(sds.getDatatype().equals("FTEST")){
												v_ftestscore = sds.getScore();
											}
											if(sds.getDatatype().equals("HTEST")){
												v_htestscore = sds.getScore();
											}
											if(sds.getDatatype().equals("REPORT")){
												v_reportscore = sds.getScore();
											}

									}
                                }       
                                //복습
                                if(v_iseduend.equals("Y")) {
                                        if(sd.getIsgraduated().equals("Y")) v_isgraduated="수료";	
                                        else v_isgraduated="미수료";
                                }else{
                                    if(v_totscore >= sd.getGradscore() && v_ftestscore >= sd.getGradftest() && v_htestscore >= sd.getGradhtest() && v_reportscore >= sd.getGradreport()) {
                                    	v_isgraduated="수료";	
                                    }
                                    else{
                                    	v_isgraduated="미수료";
                                	}
                                }
%>
                                        <tr>
                                            <!--td class="board_course_detail1">수료기준 점수</td>
                                            <td class="board_course_detail1"><%=sd.getGradscore()%>점</td-->
                                            <td class="board_course_detail1">이수기준 총취득점수</td>
                                            <td class="board_course_detail1"><%=sd.getGradscore()%></td>
                                            <td class="board_course_detail1"></td>
                                            <td class="board_course_detail1"><font color='red'>본인의 총 취득점수</font></td>
                                            <td class="board_course_detail1"><%//df.format(v_totscore)%><%=v_totscore%>점</td>
                                        </tr>
                                        <tr>
                                            <td class="board_course_detail1" colspan="4">수료여부</td>
                                            <td class="board_course_detail1"><b>[<%=v_isgraduated%>]</b></td>
                                        </tr>
                                        <!--tr>
                                            <td class="board_course_detail1">수료기준 진도율</td>
                                            <td class="board_course_detail1"><%=sd.getGradstep()%>%</td>
                                            <td class="board_course_detail1">본인의 진도율</td>
                                            <td class="board_course_detail1"><%=sd.getTstep()%>%</td>
                                            <td class="board_course_detail1"></td>                                            
                                        </tr-->
                                        <!--tr>
                                            <td class="board_course_detail1">수료여부</td>
                                            <td class="board_course_detail2"" colspan="4"><b>[<%=sd.getIsgraduated()%>]</b> : <%=get_reason(sd.getIsgraduated())%></td>
                                        </tr-->

<%                          }       %>
                                    </table>
                                </td>
                            </tr>
                        </table>
            <br>
            




<% if(p_iurl.equals("0")){ %>  
            <!-------------------학습 진행 상황 시작---------------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr> 
                <td><img src="/images/user/study/study_ing.gif" border="0"></td>
              </tr>
              <tr>
                <td height=8></td>
              </tr>
            </table>
						<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
                            <tr>
                                <td class="board_color_line"></td>
                            </tr>
                        </table>
                        <table cellspacing="1" cellpadding="3" class="open_board_table_out2" align="center">
						<%
						    ArrayList    examdata = (ArrayList)request.getAttribute("ExamData");
							ArrayList    examresultdata = (ArrayList)request.getAttribute("ExamResultData");
						%>
                            <tr>
                                <td class="board_title_bg1" width="25%">최종평가</td>
                                <td class="board_title_bg3" width="25%">
								<font color="red"><%=examdata.get(2)%> </font>개중 <font color="red"><%=examresultdata.get(2)%> </font>개 응시
								</td>
                                <td class="board_title_bg1" width="25%">중간평가</td>
                                <td class="board_title_bg3" width="25%">
								<font color="red"><%=examdata.get(0)%> </font>개중 <font color="red"><%=examresultdata.get(0)%> </font>개 응시
								</td>
                            </tr>
                            <tr>
                                <td class="board_title_bg1">형성평가</td>
                                <td class="board_title_bg3">
								<font color="red"><%=examdata.get(1)%> </font>개중 <font color="red"><%=examresultdata.get(1)%> </font>개 응시
								</td>
                                <td class="board_title_bg1">리포트</td>
                                <td class="board_title_bg3"><font color="red"><%=box.getString("p_report")%> </font>개중 <font color="red"><%=box.getString("p_reportdata")%> </font>개 제출</td>
                            </tr>
                            <tr>
                                <td class="board_title_bg1">과정설문</td>
                                <td class="board_title_bg3">
								<%if(box.getString("p_suldata").equals("0")){%>
								<font color="red">미응시</font>
								<%}else if(box.getString("p_suldata").equals("1")){%>
								<font color="red">응시완료</font>
								<%}%>
								</td>
                                <td class="board_title_bg1">콘텐츠평가</td>
                                <td class="board_title_bg3">
								<%if(box.getString("p_ispaper").equals("0")){%>
									설문지 없음
                                <%}else{%>
									<%if(box.getString("p_contentsdata").equals("0")){%>
										<a href="javascript:PopSulmun();">
										  <u><font color="red">미응시</font></u>
										</a>
									<%}else if(box.getString("p_contentsdata").equals("1")){%>
									<font color="red">응시완료</font>
									<%}%>
                                <%}%>
								</td>
                            </tr>
                        </table>
                    <!--------------------학습 진행 상황 종료----------------->
            <br>
      
<%}%>
            <!----------------- 닫기버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td height="11"></td>
              </tr>
              <tr> 
                <td align="right">
                    <a href="javascript:window.close()"><img src="/images/user/study/close_butt.gif" width="54" height="20" border="0"></a>
                </td>
              </tr>
            </table>
            <!----------------- 닫기버튼 끝 ---------------->

            <br>
            <br>

          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>

</body>
</html>

<%!
public String   get_isEducatedTxt(String val){
    if(val.equals("Y"))
        return  "<font color=blue><b>O</b></font>";
    else
        return  "<font color=red><b>X</b></font>";
}

public String   get_examtypenm(String val){
    if(val.equals("M"))         return  "중간 평가";
    else if(val.equals("T"))    return  "최종 평가";
    else                        return  "QUIZ";
}
public String   get_datatypenm(String val){
    if      (val.equals("STEP"  ))      return  "1.진도";
    else if (val.equals("MTEST" ))      return  "중간 평가";
    else if (val.equals("FTEST" ))      return  "2.최종 평가";
    else if (val.equals("REPORT"))      return  "3.Report";
    else if (val.equals("ACT"   ))      return  "Activity";
    else if (val.equals("ETC1"  ))      return  "참여도";
    else if (val.equals("ETC2"  ))      return  "기여도";

    return " ";
}
public String   get_reason(String val){
    if  (val.equals("Y")){
        return "축하합니다. 수료기준을 통과하셨습니다";
    }else{
        return "수료기준 취득점수/진도율에 미달하여 미수료상태입니다";
    }
}
%>
