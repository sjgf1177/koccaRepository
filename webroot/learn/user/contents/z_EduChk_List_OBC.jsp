<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//********************************************************************
//  1. 제      목: 마스터폼 -진도/목차화면
//  2. 프로그램명 : z_EduChk_List_OBC.jsp
//  3. 개      요: 마스터폼 -진도/목차화면
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
    DecimalFormat df = new DecimalFormat("###.00");
    String  p_subj, p_year, p_subjseq, p_userid;
    String  v_isgraduated="";
    double  v_totscore =0.0;
    double  v_ftestscore = 0.0;
    double  v_htestscore = 0.0;
    double  v_reportscore = 0.0;
	String  v_edutime = "";  // 학습시간
	String  v_edudate = "";  // 최근학습일
	int     v_educount= 0;   // 강의실접근횟수

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

    p_subj      = box.getStringDefault("p_subj"    , box.getSession("s_subj"));
    p_year      = box.getStringDefault("p_year"    , box.getSession("s_year"));
    p_subjseq   = box.getStringDefault("p_subjseq" , box.getSession("s_subjseq"));
    p_userid    = box.getStringDefault("p_userid"  , box.getSession("userid"));

	String p_subjnm = box.getString("p_subjnm");  // 과정명
	String p_iurl   = box.getString("p_iurl");    // 외주url(외주:'1')
	String v_iseduend = "";
	v_iseduend= box.getString("p_iseduend");    // 복습(복습:'Y')

    if(p_iurl.equals("")) p_iurl="0";
    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율

    MasterFormData  md  = (MasterFormData)request.getAttribute("MasterFormData");
    ArrayList   brlist  = null;

    ArrayList  list = (ArrayList)request.getAttribute("EduList");  // 진도율
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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>과정상세정보</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="javascript">
    function init() {
        this.moveTo(0,0);
        //this.resizeTo(880,screen.availHeight);
        this.resizeTo(1024,900);
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


    // 설문 팝업
    function PopSulmun() {
        var subj = '<%=p_subj%>';
        var year = '<%=p_year%>';
        var subjseq = '<%=p_subjseq%>';
        var url = "/servlet/controller.study.StudySulmunServlet?&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
    }

    // 리포트 팝업
    function PopReport() {
        var subj = '<%=p_subj%>';
        var year = '<%=p_year%>';
        var subjseq = '<%=p_subjseq%>';
        var url = "/servlet/controller.study.ProjectServlet?p_process=choicePage&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
    }

    // 평가 팝업
    function PopExam() {
        var subj = '<%=p_subj%>';
        var year = '<%=p_year%>';
        var subjseq = '<%=p_subjseq%>';
        var url = "/servlet/controller.study.StudyExamServlet?&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
        window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
    }
</script>
</head>
<body id="pop_study" onLoad='init();'><!-- popup size : 890* -->
<%=p_isFromLMS%>
	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit2.gif" alt="과정상세정보" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">

				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info">
						<dt><span>과정명</span></dt>
						<dd><%= GetCodenm.get_subjnm(p_subj) %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(sd.getEdustart(),"yyyy/MM/dd") %>
                                                ~<%= FormatDate.getFormatDate(sd.getEduend(),"yyyy/MM/dd") %></dd>
					</dl>
					
					<div class="study_info">
						<table class="study_info_board">
						<colgroup><col width="81px" /><col width="114px" /><col width="103px" /><col width="318px" /></colgroup>
						<tr>
							<th>최근학습일</th>
							<td><%= FormatDate.getFormatDate(v_edudate,"yyyy.MM.dd") %></td>
							<th>남은 교육기간</th>
							<td><%= FormatDate.getDate("yyyy.MM.dd") %>~<%= FormatDate.getFormatDate(sd.getEduend(),"yyyy.MM.dd") %></td>
						</tr>
						<tr>
							<th>학습시간</th>
							<td><%= v_edutime %></td>
							<th>나의 진도율</th>
							<td><span class="rate_me"><%= sd.getTstep() %>%</span> <span class="bar_yellow"><span style="width:<%= sd.getTstep() %>%;"></span></span></td>
						</tr>
						<tr>
							<th>학습횟수</th>
							<td><%= v_educount %>회</td>
							<th>권장 진도율</th>
							<td><span class="rate"><%= v_promotion %>%</span> <span class="bar_gray"><span style="width:<%= v_promotion %>%;"></span></span></td>
						</tr>
					</table>
					</div>
				</div></div></div>

				<div class="content">
					<h3><img src="/images/portal/myclass/study/pop_stit3.gif" alt="학습진행상황" /></h3>
					<table class="b_list">
					<colgroup><col width="32%" /><col width="17%" /><col width="17%" /><col width="17%" /><col width="17%" /></colgroup>
					<thead>
					<tr>
						<th></th>
						<th>이수 기준점수</th>
						<th>가중치(%)</th>
						<th>취득점수</th>
						<th class="end">환산점수</th>
					</tr>
					</thead>
					<tbody>
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
						<td class="ta_l pd_l34"><%= get_datatypenm(sds.getDatatype()) %></td>
						<td><%= sds.getGradscore() %> 점</td>
						<td><%= sds.getWeight() %> %</td>
						<td><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getScore()) %> 점</td>
						<td class="end"><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getAvscore()) %> 점</td>
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
                                        if(sd.getIsgraduated().equals("Y")){
                                        	 v_isgraduated="수료";
                                        } else {
                                        	v_isgraduated="미수료";
                                        }

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
						<td class="ta_l pd_l34">취득점수</td>
						<td><%= sd.getGradscore() %> 점</td>
						<td></td>
						<td>본인의 총 취득점수</td>
						<td class="end"><%= new java.text.DecimalFormat("###,###,###.#").format(v_totscore) %> 점</td>
					</tr>
					<tr>
						<td class="ta_l pd_l34"  colspan="4">수료여부</td>
						<td class="end"><%=v_isgraduated%></td>
					</tr>
<%		}       %>
					</tbody>
					</table>

<% if(p_iurl.equals("0")){ %>
						<%
						    ArrayList    examdata = (ArrayList)request.getAttribute("ExamData");
							ArrayList    examresultdata = (ArrayList)request.getAttribute("ExamResultData");
						%>
					<h3 class="mg_t30"><img src="/images/portal/myclass/study/pop_stit4.gif" alt="평가" /></h3>
					<table class="view">
					<colgroup><col width="15%" /><col width="35%" /><col width="15%" /><col width="35%" /></colgroup>
					<tr>
						<th>최종평가</th>
						<td><%= examdata.get(2) %> 개중 <%= examresultdata.get(2) %> 개 응시</td>
						<th>중간평가</th>
						<td class="end"><%= examdata.get(0) %> 개중 <%= examresultdata.get(0) %> 개 응시</td>
					</tr>
					<tr>
						<th>형성평가</th>
						<td><%= examdata.get(1) %> 개중 <%= examresultdata.get(1) %> 개 응시</td>
						<th>리포트</th>
						<td class="end"><%= box.getString("p_report") %> 개중 <%= box.getString("p_reportdata") %> 개 제출</td>
					</tr>
					<tr>
						<th>사전설문</th>
						<td>
<%		if(box.getString("p_ispaper2").equals("0")){%>
							<span>설문지 없음</span>
<%		} else {
			if(box.getString("p_suldata").equals("0")){%>
							<a href="javascript:PopSulmun();" class="board_btn2"><span>응시하기</span>
<%			}else if(box.getString("p_suldata").equals("1")){%>
							<span>응시완료</span>
<%			}
		}
%>
						</td>
						<th>사후설문</th>
						<td class="end">
<%		if(box.getString("p_ispaper").equals("0")){%>
                            <span>설문지 없음</span>
<%		} else {%>
<%			if(box.getString("p_contentsdata").equals("0")){%>
							<a href="javascript:PopSulmun();" class="board_btn2"><span>응시하기</span>
<%			}else if(box.getString("p_contentsdata").equals("1")){%>
                            <span>응시완료</span>
<%			}
		}
%>
						</td>
					</tr>
					</table>

					<h3 class="mg_t30"><img src="/images/portal/myclass/study/pop_stit2.gif" alt="차시정보" /></h3>
					<table class="list">
					<colgroup><col width="15%" /><col width="25%" /><col width="15%" /><col width="15%" /><col width="15%" /><col width="15%" /></colgroup>
					<thead>
					<tr>
						<th colspan="2">Module/Lesson</th>
						<th>Object</th>
						<th>수강</th>
						<th>최종학습일</th>
						<th class="end">총 학습시간</th>
					</tr>
					</thead>
					<tbody>
<%
                            String v_module="", v_lesson="",v_brlink="";
                            boolean f_span = false;
                            MfBranchData    bData;
                            for (int i=0; i<list.size(); i++) {
                                        x  = (EduListData)list.get(i); %>
                                        <tr>
<%                              if(!v_module.equals(x.getModule())){    %>
                                            <td class="ta_l" rowspan="<%=x.getRowspan()%>"><%=x.getModulenm()%></td>
<%                                  v_module = x.getModule();
                                            }
                                    if(!v_lesson.equals(x.getLesson())){    %>
                                            <td class="ta_l" rowspan="<%=x.getRowspan_lesson()%>"><%=x.getLesson()%> - <%=x.getSdesc()%></td>
<%                                  v_lesson = x.getLesson();
                                        		f_span = true;
                                            }

                                      if(x.getRecordType().equals("STEP")){ %>
                                            <td class="ta_l">
                                              	<%=x.getOidnm()%>
                                            </td>
                                            <% if ((x.getIsEducated()).equals("P")){ %>
                                            <td class="ta_l" colspan="5"></td>
                                            <% } else {%>
                                            <td><%=get_isEducatedTxt(x.getIsEducated())%></td>
                                            <td><%= StringManager.chkNull(FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm")) %></td>
											<td class="end"><%= x.getTotal_time() %></td>
                                            <% } %>
<%	                                      if(f_span) {	%>
<%                                        f_span = false;
                                    	  } 			%>
<%                                    } else if(x.getRecordType().equals("EXAM")){    %>
                                            <td class="ta_l"><%=get_examtypenm(x.getPtype())%></td>
                                            <td><%=get_isEducatedTxt(x.getIsEducated())%></td>
                                            <td><%= StringManager.chkNull(FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm")) %></td>
											<td class="end"><%= x.getTotal_time() %></td>
<%                                    }           %>
                                        </tr>
<%                           }         %>
					</tbody>
					</table>
<%}%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%!
public String   get_isEducatedTxt(String val){
    if(val.equals("Y"))
        return  "<img src=\"/images/portal/ico/ico_yes.gif\" alt=\"\" />";
    else if(val.equals("P"))
        return "";
    else
        return  "<img src=\"/images/portal/ico/ico_no.gif\" alt=\"\" />";
}

public String   get_examtypenm(String val){
    if(val.equals("M"))         return  "중간 평가";
    else if(val.equals("T"))    return  "최종 평가";
    else                        return  "QUIZ";
}
public String   get_datatypenm(String val){
    if      (val.equals("STEP"  ))      return  "진도";
    else if (val.equals("MTEST" ))      return  "중간 평가";
    else if (val.equals("FTEST" ))      return  "최종 평가";
    else if (val.equals("HTEST" ))      return  "형성 평가";
    else if (val.equals("REPORT"))      return  "Report";
    else if (val.equals("ACT"   ))      return  "Activity";
    else if (val.equals("ETC1"  ))      return  "참여도";
    else if (val.equals("ETC2"  ))      return  "기타";

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
