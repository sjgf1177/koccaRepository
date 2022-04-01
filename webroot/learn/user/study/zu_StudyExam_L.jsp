<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 과정 평가 리스트
//  2. 프로그램명: zu_StudyExam_L.jsp
//  3. 개      요: 과정 평가 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율
    String v_mylesson   = (String)request.getAttribute("mylesson");       // 자기차시
	int v_mylesson1 = 0;
	if(!v_mylesson.equals("")){
	     v_mylesson1 = Integer.parseInt(v_mylesson);
	}

    ArrayList blist = (ArrayList)request.getAttribute("ExamUserList");
    ArrayList list  = (ArrayList)request.getAttribute("ExamUserResultList");
    ArrayList list1 = (ArrayList)request.getAttribute("ExamUserRetryList");

	DataBox dbox01 = null;
	DataBox dbox02 = null;
	if(blist.size() > 0){
	    dbox01 = (DataBox)blist.get(0);
	}else{
	    dbox01 = new DataBox("resoponsebox");
	}
	
	int count = 0;

	SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);
	
	EduStartBean	bean		= new EduStartBean();
	EduScoreData    scoredata	= new EduScoreData();
					scoredata	= bean.SelectEduScore(box);

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>평가</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
// 평가지 열기
function examWright(plessonstart, p_lesson, p_examtype, p_papernum, p_userretry){

    document.form1.action = "/servlet/controller.exam.ExamUserServlet";
    document.form1.p_process.value = 'ExamUserPaperListPage';
    document.form1.p_lessonstart.value = plessonstart;  // 차시 시작
    document.form1.p_lesson.value = p_lesson;
    document.form1.p_examtype.value = p_examtype;
    document.form1.p_papernum.value = p_papernum;
    document.form1.p_userretry.value = p_userretry;
	document.form1.submit();
}

// 결과보기
function IndividualResult(lesson, type, papernum) {
  window.self.name = "winIndividualResult";
  farwindow = window.open("", "openIndividualResult", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=930, height=600, top=0, left=0");
  document.form1.target = "openIndividualResult";
  document.form1.action = "/servlet/controller.exam.ExamUserServlet";
  document.form1.p_process.value = "ExamUserPaperResult";

  //document.form1.p_subj.value     = p_subj;
  //document.form1.p_gyear.value    = p_year;
  //document.form1.p_subjseq.value  = p_subjseq;
  document.form1.p_lesson.value     = lesson;
  document.form1.p_examtype.value   = type;
  document.form1.p_papernum.value   = papernum;
  //document.form1.p_userid.value   = p_userid;

  document.form1.submit();
  farwindow.window.focus();
  document.form1.target = window.self.name;
}

// 평가지 열기
//function examInformation(p_subj, p_year, p_subjseq, p_examtype, p_lesson, p_papernum){
//
//    document.form1.action = "/servlet/controller.exam.ExamUserServlet";
//    document.form1.p_process.value = 'ExamUserPaperResult';
//    document.form1.p_subj.value = p_subj;
//    document.form1.p_year.value = p_year;
//    document.form1.p_subjseq.value = p_subjseq;
//    document.form1.p_examtype.value = p_examtype;
//    document.form1.p_lesson.value = p_lesson;
//    document.form1.p_papernum.value = p_papernum;
//	document.form1.submit();
//
//
//}
//-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" action="/servlet/controller.exam.ExamUserServlet">
	<input type="hidden" name="p_process"      value=""/>
	<input type="hidden" name="p_action"       value=""/>
	<input type="hidden" name="p_subj"         value="<%=dbox01.getString("d_subj")%>"/>
	<input type="hidden" name="p_year"         value=""/>
	<input type="hidden" name="p_grcode"       value="<%=dbox01.getString("d_grcode")%>"/>
	<input type="hidden" name="p_gyear"        value="<%=dbox01.getString("d_year")%>"/>
	<input type="hidden" name="p_subjseq"      value="<%=dbox01.getString("d_subjseq")%>"/>
	<input type="hidden" name="p_lesson"       value=""/>
	<input type="hidden" name="p_papernum"     value=""/>
	<input type="hidden" name="p_examtype"     value=""/>
	<input type="hidden" name="p_userretry"    value=""/>
	<input type="hidden" name="p_userid"       value="<%=box.getSession("userid")%>"/>
    <input type="hidden" name="p_lessonstart"  value=""/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit7.gif" alt="평가" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
<%	if (scoredata != null) { %>
				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= subjdata.getSubjnm() %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				
				<div class="content">
					
					<table class="list">
					<colgroup><col width="6%" /><col width="6%" /><col width="10%" /><col width="7%" /><col width="8%" /><col width="8%" /><col width="10%" /><col width="10%" /><col width="10%" /><col width="6%" /><col width="10%" /><col width="9%" /></colgroup>
					<thead>
					<tr>
						<th>번호</th>
						<th>차시</th>
						<th>시험유형</th>
						<th>문제수</th>
						<th>총점</th>
						<th>응시가능</th>
						<th>평가시작<br />기간</th>
						<th>평가종료<br />기간</th>
						<th>응시여부</th>
						<th>응시<br />점수</th>
						<th>결과보기</th>
						<th class="end">현진도/<br />응시진도</th>
					</tr>
					</thead>
					<tbody>
<%
	for (int i=0; i<blist.size(); i++) {
		DataBox	dbox = (DataBox)blist.get(i);

		String	isExam	= (String)list.get(i);
		String	isRetry = (String)list1.get(i);
		int		v_retry = 0;
		String	v_retry2= "";
		String	v_today	= FormatDate.getDate("yyyyMMddHHmmss");
		int		diff1	= 0;
		int		diff2	= 0;
				diff1	= FormatDate.getSecDifference(dbox.getString("d_startdt"), v_today);	//오늘날짜와 시작일과 차이
				diff2	= FormatDate.getSecDifference(v_today,dbox.getString("d_enddt"));		//종료일과 오늘날짜의 차이
		//System.out.println("diff1:"+diff1+"/diff2"+diff2);

		if(isRetry.equals("-1")){		//		현재 해당 평가를 아직 응시하지 않은경우
			v_retry	= dbox.getInt("d_retrycnt");
		} else if(isRetry.equals("0")){		//		재응시 평가가 있는 경우 중 현재 평가  재응시가 끝난경우
			v_retry	= Integer.parseInt(isRetry);
			v_retry2= "-";
		}
//		else if(isRetry.equals("1")){		//		재응시 평가가 있는 경우 중 현재 평가  재응시가 끝난경우
//		   v_retry = Integer.parseInt(isRetry);
//		}
		else if(!isRetry.equals("X")) {
//			   v_retry = Integer.parseInt(isRetry)-1;
			v_retry = Integer.parseInt(isRetry);
		}
		else if(isRetry.equals("X")) {
			v_retry2 = "-";
		}

		count ++;
%>
					<tr>
						<td class="num"><%= String.valueOf(count) %></td>
						<td class="num"><%= dbox.getString("d_lesson") %></td>
						<td>
<%		if(Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress"))) {  //  진도율제한을 넘어선 경우
			if(!isRetry.equals("X")) {			//		재응시가 있는 평가 경우
				if(( v_retry > 0 || !v_retry2.equals("-")) && ( diff1 > 0 && diff2 > 0 )) {		 // 시험기간체크
%>
							<a href="javascript:examWright('<%= dbox.getString("d_lessonstart") %>','<%= dbox.getString("d_lesson") %>','<%= dbox.getString("d_examtype") %>','<%= dbox.getString("d_papernum") %>','<%= v_retry %>')"><%= dbox.getString("d_examtypenm") %></a>
<%
				} else {	
%>
							<%= dbox.getString("d_examtypenm") %>
<%				}
			} else {			//		재응시가 없는 평가 경우 진도율과 제출여부만 체크 & 시험기간체크
				if(isExam.equals("0") && ( diff1 > 0 && diff2 > 0 )) {
%>
							<a href="javascript:examWright('<%= dbox.getString("d_lessonstart") %>','<%= dbox.getString("d_lesson") %>','<%= dbox.getString("d_examtype") %>','<%= dbox.getString("d_papernum") %>','<%= v_retry %>')"><%= dbox.getString("d_examtypenm") %></a>
<%				} else {	%>
							<%= dbox.getString("d_examtypenm") %>
<%				}
			}
		} else { %>
							<%=dbox.getString("d_examtypenm")%>
<%		} %>
						</td>
						
						
						<td class="num"><%= String.valueOf(dbox.getInt("d_examcnt")) %></td>
						<td class="num"><%= String.valueOf(dbox.getString("d_totalscore")) %></td>
						<td><%= isRetry.equals("X") || isRetry.equals("0") ? v_retry2 : v_retry + "" %></td>
						<td class="num"><%= FormatDate.getFormatDate(dbox.getString("d_startdt"),"yyyy.MM.dd") %><br /><%= FormatDate.getFormatDate(dbox.getString("d_startdt"),"HH:mm") %></td>
						<td class="num"><%= FormatDate.getFormatDate(dbox.getString("d_enddt"),"yyyy.MM.dd") %><br /><%= FormatDate.getFormatDate(dbox.getString("d_enddt"),"HH:mm")%></td>


<%		if((Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress"))) && (v_retry>0 || !v_retry2.equals("-")) && ( diff1 > 0 && diff2 > 0 ) ){ %>
						<td class="num"><a href="javascript:examWright('<%= dbox.getString("d_lessonstart") %>','<%= dbox.getString("d_lesson") %>','<%= dbox.getString("d_examtype") %>','<%= dbox.getString("d_papernum") %>','<%= v_retry %>')" class="board_btn2"><span>응시하기</span></a></td>
<%		} else if((Double.parseDouble(v_progress)<Double.parseDouble(dbox.getString("d_progress")))){%>
						<td><a class="state"><span class="finish">진도미달</span></a></td>
<%		}else if(isExam.equals("1")){%>
						<td><a class="state"><span class="finish">응시완료</span></a></td>
<%		}else if(isRetry.equals("X") && isExam.equals("0") && ( diff1 > 0 && diff2 > 0 )){%>
						<td class="num"><a href="javascript:examWright('<%= dbox.getString("d_lessonstart") %>','<%= dbox.getString("d_lesson") %>','<%= dbox.getString("d_examtype") %>','<%= dbox.getString("d_papernum") %>','<%= v_retry %>')" class="board_btn2"><span>응시하기</span></a></td>
<%		}else if(diff1 < 0 || diff2 < 0){%>
 						<td><a class="state"><span class="finish">시험기간아님</span></a></td>
<%		}%>
						<td class="num"><%= dbox.getString("d_score") %></td>
<%		if(dbox.getString("d_isopenanswer").equals("Y")){
			if(dbox.getString("d_score").equals("-")){%>
						<td>-</td>
<%			} else { 
				if(dbox.getInt("d_resultcnt") > 0){ %>
						<td><a href="javascript:IndividualResult('<%= dbox.getString("d_lesson") %>','<%= dbox.getString("d_examtype") %>','<%= dbox.getString("d_papernum") %>')" class="board_btn2"><span>결과보기</span></a></td>
<%
				} else {
%>
						<td><a class="state"><span class="finish">미응시</span></a></td>
<%				}
			}
		} else { %>
						<td>-</td>
<%		} %>
						<td class="end num"><%= v_progress %> / <%= dbox.getString("d_progress") %></td>
					</tr>
<%	} %>
<%
	if(blist.size() == 0){ %>        
                    <tr> 
                    	<td colspan="12">평가 내역이 없습니다.</td>                  
                    </tr>
<%	} %>  
					</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>