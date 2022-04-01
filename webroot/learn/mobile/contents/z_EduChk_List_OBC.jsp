<%
//********************************************************************
//  1. 제      목: 마스터폼 -진도/목차화면 - Normal
//  2. 프로그램명 : z_EduChk_List.jsp
//  3. 개      요: 마스터폼 -진도/목차화면 - Normal
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 7
//  7. 수      정:
//********************************************************************
%>
<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
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
	
    int v_pageno = box.getInt("p_pageno");
%>
<%@ include file="/learn/mobile/include/common.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta name="format-detection" content="telephone=no"/>
<title><%=v_title.toString()%></title>
<link rel="stylesheet" type="text/css" href="/css/m_common.css" />
<script type="text/javascript" language="javascript" src="/js/common.js"></script>
<script type="text/javascript" language="javascript">
	//페이지 이동
	function goListPage()
	{
		var obj = document.listForm;
		
		obj.method = "post";
		obj.p_process.value = "<%="030001000000".equals(v_menuid)? "onLineStudyingList":"onLineStudyendList"%>";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}
</script>

</head>
<body class="other_sub">
<div id="sub_header">	
	<%@ include file="/learn/mobile/include/header.jsp" %>	
</div>
<div id="kocca_subContent">	
	
	<form name="listForm" method="post" action="/servlet/controller.mobile.course.CourseServlet">
		<input type="hidden" name="p_process" value="onLineStudyendList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
	<div class="mp_view_top">
		<button id="sub_searchSubmit" onclick="return goListPage();">
		<span>목록</span>
		</button>
	</div>			
	</form>
	<div class="mp-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="20%" />
		<col width="20%" />
		<col width="20%" />
		<col width="%" />
	</colgroup>
	<tr>
		<th class="th_left">과정명</th>
		<td class="td_ct"><%= GetCodenm.get_subjnm(p_subj) %></td>
		<th class="th_left">교육기간</th>
		<td class="td_ct02"><%= FormatDate.getFormatDate(sd.getEdustart(),"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(sd.getEduend(),"yyyy/MM/dd") %></td>
	</tr>	
	<tr>
		<th class="th_left">최근학습일</th>
		<td class="td_ct"><%= FormatDate.getFormatDate(v_edudate,"yyyy.MM.dd") %></td>
		<th class="th_left">남은교육기간</th>
		<td class="td_ct02"><%= FormatDate.getDate("yyyy.MM.dd") %> ~ <%= FormatDate.getFormatDate(sd.getEduend(),"yyyy.MM.dd") %></td>
	</tr>	
	<tr>
		<th class="th_left">학습시간</th>
		<td class="td_ct"><%= v_edutime %></td>
		<th class="th_left">나의진도율</th>
		<td class="td_ct02"><%= sd.getTstep() %>%</td>
	</tr>	
	<tr>
		<th class="th_left">학십횟수</th>
		<td class="td_ct"><%= v_educount %>회</td>
		<th class="th_left">권장진도율</th>
		<td class="td_ct02"><%= v_promotion %>%</td>
	</tr>				
	</table>
	</div>

	
	<div class="h_area">
	<h3 class="h">학습진행현황</h3>
	</div>			

	<div class="mp-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="15%" />
		<col width="25%" />
		<col width="20%" />
		<col width="20%" />
		<col width="20%" />
	</colgroup>
	<tr>
		<th class="th_left"></th>
		<th class="th_left">이수기준점수</th>
		<th class="th_left">가중치(%)</th>
		<th class="th_left">취득점수</th>
		<th class="th_left01">환산점수</th>
	</tr>
<%
	Hashtable 	ht;
	int			htsize		= 0;
	String		v_datatype	= "";

	if(sd!=null) {
	    htsize=sd.eduScoreList.size();
	    for (int i=0; i<htsize; i++) {
	        if((Hashtable)sd.getEduScoreList() != null){
	            ht  = (Hashtable)sd.getEduScoreList();
	            sds = (EduScoreDataSub)ht.get(String.valueOf(i));
	            v_datatype=get_datatypenm(sds.getDatatype());
%>
					<tr>
						<th class="th_left"><%= get_datatypenm(sds.getDatatype()) %></th>
						<td class="td_ct01"><%= sds.getGradscore() %> 점</td>
						<td class="td_ct01"><%= sds.getWeight() %> %</td>
						<td class="td_ct01"><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getScore()) %> 점</td>
						<td class="td_ct02"><%= new java.text.DecimalFormat("###,###,###.#").format(sds.getAvscore()) %> 점</td>
					</tr>
<%
				v_totscore=v_totscore+sds.getAvscore();
			}
		}

	    //복습
		if(v_iseduend.equals("Y")) {
			if(sd.getIsgraduated().equals("Y")) v_isgraduated="수료";
			else v_isgraduated="미수료";
		} else {
			if(v_totscore > sd.getGradscore()) {
				v_isgraduated="수료";
			} else {
				v_isgraduated="미수료";
			}
		}
%>
					<tr>
						<th class="th_left">취득점수</th>
						<td class="td_ct01"><%= sd.getGradscore() %> 점</td>
						<td class="td_ct01"></td>
						<td class="td_ct01">본인의 총 취득점수</td>
						<td class="td_ct02"><%= new java.text.DecimalFormat("###,###,###.#").format(v_totscore) %> 점</td>
					</tr>
					<tr>
						<th colspan="4"class="th_left">수료여부</th>
						<td class="td_ct02"><%=v_isgraduated%></td>
					</tr>
<%		}       %>				
	</table>
	</div>
<%
	if(p_iurl.equals("0")){
%>
<%
		ArrayList    examdata = (ArrayList)request.getAttribute("ExamData");
		ArrayList    examresultdata = (ArrayList)request.getAttribute("ExamResultData");
%>
	<div class="h_area">
	<h3 class="h">평가</h3>
	</div>			

	<div class="mp-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="17%" />
		<col width="28%" />
		<col width="17%" />
		<col width="%" />
	</colgroup>
	<tr>
		<th class="th_left">최종평가</th>
		<td class="td_ct"><%= examdata.get(2) %>개중<%= examresultdata.get(2) %>개 응시</td>
		<th class="th_left">중간평가</th>
		<td class="td_ct02"><%= examdata.get(0) %> 개중 <%= examresultdata.get(0) %> 개 응시</td>
	</tr>	
	<tr>
		<th class="th_left">형성평가</th>
		<td class="td_ct"><%= examdata.get(1) %> 개중 <%= examresultdata.get(1) %> 개 응시</td>
		<th class="th_left">리포트</th>
		<td class="td_ct02"><%= box.getString("p_report") %> 개중 <%= box.getString("p_reportdata") %> 개 제출</td>
	</tr>	
	<tr>
		<th class="th_left">사전설문</th>
		<td class="td_ct">
<%		if(box.getString("p_ispaper2").equals("0")){%>
							설문지 없음
<%		} else {
			if(box.getString("p_suldata").equals("0")){%>
							미응시
<%			}else if(box.getString("p_suldata").equals("1")){%>
							응시완료
<%			}
		}
%>		
		</td>
		<th class="th_left">사후설문</th>
		<td class="td_ct02">
<%		if(box.getString("p_ispaper").equals("0")){%>
                            설문지 없음	
<%		} else {%>
<%			if(box.getString("p_contentsdata").equals("0")){%>
							 응시하기< 
<%			}else if(box.getString("p_contentsdata").equals("1")){%>
                             응시완료 
<%			}
		}
%>		
		</td>
	</tr>						
	</table>
	</div>

	<div class="h_area">
	<h3 class="h">성적</h3>
	</div>			

	<div class="mp-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="15%" />
		<col width="25%" />
		<col width="20%" />
		<col width="20%" />
		<col width="20%" />
	</colgroup>
	<tr>
		<th class="th_left">성적요소</th>
		<th class="th_left">구분</th>
		<th class="th_left">응시기간</th>
		<th class="th_left">제출일</th>
		<th class="th_left01">취득점수</th>
	</tr>
<%
		ArrayList    ExamList1 = (ArrayList)request.getAttribute("ExamUserList");
		ArrayList    ExamList2 = (ArrayList)request.getAttribute("ExamUserResultList");
		int totCnt = 0;
		int rowCnt = ExamList1.size();
        
		for (int i=0; i<ExamList1.size(); i++) {
			DataBox dbox = (DataBox)ExamList1.get(i);
%>
					<tr>
<%			if (i==0 && rowCnt>1) {  %>
						<td class="td_ct01" rowspan="<%= rowCnt %>"><span>시험</span></td>
<%			}else if (rowCnt==1) {  %>
						<td class="td_ct01"><span>시험</span></td>
<%			}   %>
						<td class="td_ct01"><%= dbox.getString("d_examtypenm") %></td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy/MM/dd HH:mm") %> ~ <%= FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy/MM/dd HH:mm") %></td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd HH:mm") %></td>
						<td class="td_ct02"><%= dbox.getString("d_score") %></td>
					</tr>
<%
		}
        totCnt += rowCnt;

        ArrayList    ProjectList = (ArrayList)request.getAttribute("ProjectList");
        rowCnt = ProjectList.size();
        for (int i=0; i<ProjectList.size(); i++) {
            ProjectData data  = (ProjectData)ProjectList.get(i);
            String v_title1  = data.getTitle();
            String v_expiredate        = data.getExpiredate();
            String v_deadlinesdate         = data.getDeadlinesdate();
            String v_deadlineedate       = data.getDeadlineedate();
            String v_ldate       = data.getLdate();

            if (v_deadlineedate.equals("")) {
                v_deadlineedate = v_expiredate;
            }
            int v_score        = data.getScore2();
%>
					<tr>
<%      	if (i==0 && rowCnt>1) {  %>
						<td class="td_ct01" rowspan="<%= rowCnt %>"><span>리포트</span></td>
<%      	}else if (rowCnt==1) {  %>
						<td class="td_ct01"><span>리포트</span></td>
<%      	}   %>
						<td class="td_ct01"><%= v_title1 %></td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(v_deadlinesdate,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_deadlineedate,"yyyy/MM/dd") %></td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd HH:mm") %></td>
						<td class="td_ct02"><%= v_score %></td>
					</tr>
<%
        }
        totCnt += rowCnt;

        ArrayList  TopicList = (ArrayList)request.getAttribute("TopicList");
        rowCnt = TopicList.size();
        for (int i=0; i<TopicList.size(); i++) {
            ToronData data  = (ToronData)TopicList.get(i);

             String v_title1     = data.getTitle();
             String v_started   = data.getStarted();
             String v_ended     = data.getEnded();
             String v_addate    = data.getLdate();

             int v_score       = data.getPosition();
%>
					<tr>
<%      if (i==0 && rowCnt>1) {  %>
						<td class="td_ct01" rowspan="<%= rowCnt %>"><span>토론</span></a></td>
<%      }else if (rowCnt==1) {  %>
						<td class="td_ct01"><span>토론</span></td>
<%      }   %>
						<td class="td_ct01"><%= v_title1 %></td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(v_started,"yyyy/MM/dd HH") %>시 ~ <%= FormatDate.getFormatDate(v_ended,"yyyy/MM/dd HH") %>시</td>
						<td class="td_ct01"><%= FormatDate.getFormatDate(v_addate,"yyyy/MM/dd") %></td>
						<td class="td_ct02"><%= v_score %></td>
					</tr>
<%
        }
        totCnt += rowCnt;

        ArrayList  SulmunList = (ArrayList)request.getAttribute("SulmunSubjUserList");
        rowCnt = SulmunList.size();
        for (int i=0; i<SulmunList.size(); i++) {
            DataBox dbox1 = (DataBox)SulmunList.get(i);
%>
					<tr>
<%      	if (i==0 && rowCnt>1) {  %>
						<td class="td_ct01" rowspan="<%= rowCnt %>"><span>과정설문</span></td>
<%      	} else if (rowCnt==1) {  %>
						<td class="td_ct01"><span>과정설문</span></td>
<%      	}   %>
						<td class="td_ct01"><%= dbox1.getString("d_sulpapernm") %></td>
						<td class="td_ct02" colspan="3">
<%      	if (dbox1.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox1.getString("d_progress")))){  %>
                                    응시가능
<%      	} else if (dbox1.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)<Double.parseDouble(dbox1.getString("d_progress")))){  %>
                                    진도미달
<%      	} else if (dbox1.getString("d_eachcnt").equals("1")){  %>
                                    응시완료
<%      	}   %>
						</td>
					</tr>
<%
        }
        totCnt += rowCnt;

        if (totCnt==0) {
 %>
					<tr><td class="list_area_c" colspan="5">성적요소 데이타가 없습니다</td></tr>

<%		} %>				
	</table>
	</div>
	
	<div class="h_area">
	<h3 class="h">차시정보</h3>
	</div>


	<div class="mp-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="0%" />
		<col width="25%" />
		<col width="20%" />		
	</colgroup>
	<tr>
		<th class="th_left">차시</th>
		<th class="th_left">수강</th>
		<th class="th_left01">최종학습일</th>		
	</tr>
<%
                            String v_module="", v_lesson="",v_brlink="";
                            boolean f_span = false;
                            MfBranchData    bData;
                            for (int i=0; i<list.size(); i++) {
                                        x  = (EduListData)list.get(i); %>
                                        <tr>
<%                              if(!v_module.equals(x.getModule())){    %>
                                            <td class="td_lf01" rowspan="<%=x.getRowspan()%>"><%=x.getModulenm()%></td>
<%                                  v_module = x.getModule();
                                            }
                                    if(!v_lesson.equals(x.getLesson())){    %>
                                            <td class="td_lf01" rowspan="<%=x.getRowspan_lesson()%>"><%=x.getLesson()%> - <%=x.getSdesc()%></td>
<%                                  v_lesson = x.getLesson();
                                        		f_span = true;
                                            }

                                      if(x.getRecordType().equals("STEP")){ %>
                                            <td class="td_lf01">
                                              	<%=x.getOidnm()%>
                                            </td>
                                            <% if ((x.getIsEducated()).equals("P")){ %>
                                            <td class="td_lf01" colspan="5"></td>
                                            <% } else {%>
                                            <td class="td_lf01"><%=get_isEducatedTxt(x.getIsEducated())%></td>
                                            <td class="td_lf01"><%= StringManager.chkNull(FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm")) %></td>
											<td class="td_lf02"><%= x.getTotal_time() %></td>
                                            <% } %>
<%	                                      if(f_span) {	%>
<%                                        f_span = false;
                                    	  } 			%>
<%                                    } else if(x.getRecordType().equals("EXAM")){    %>
                                            <td class="td_lf01"><%=get_examtypenm(x.getPtype())%></td>
                                            <td class="td_lf01"><%=get_isEducatedTxt(x.getIsEducated())%></td>
                                            <td class="td_lf01"><%= StringManager.chkNull(FormatDate.getFormatDate(x.getLdate(),"yyyy/MM/dd-HH:mm")) %></td>
											<td class="td_lf02"><%= x.getTotal_time() %></td>
<%                                    }           %>
                                        </tr>
<%                           }         %>		
	</table>
<%
	}
%>
	</div>

	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->

</div>
</body>
</html>
<%!
public String   get_isEducatedTxt(String val){
    if(val.equals("Y"))
        return  "○";
    else if(val.equals("P"))
        return "";
    else
        return  "x";
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