<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: PROJECT LIST
//  2. 프로그램명: zu_Project_L.jsp
//  3. 개      요: 리포트 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 04
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_lesson    = "";
    String  v_title     = "";
    String  v_reptype   = "";
    String  v_expiredate    = "";
    String  v_deadlinesdate    = "";
    String  v_deadlineedate   = "";
    String  v_ldate   = "";

    String  v_projgrp   = "";
    String  v_rep_value = "";
    String  v_jud_value = "";
    int     v_ordseq    =  0;
    int     v_projseq   =  0;
    int     v_score     =  0;
    int     v_score2    =  0;
    int     v_cnt       =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END
    box.put("needNoDetailRead", "true");

    list = (ArrayList)request.getAttribute("projectList");
/*
    SubjseqData dataseq = new SubjseqData();
    GrseqBean bean   = new GrseqBean();

    dataseq = bean.SelectSubjseqData(box);
*/

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
<title>레포트</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
    // 레포트 제출
    function whenProjectSubmit(projseq, ordseq,lesson,reptype,projgrp,getcnt) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectHandlingPage';
        form.p_projseq.value = projseq;
        form.p_ordseq.value = ordseq;
        form.p_lesson.value = lesson;
        form.p_reptype.value = reptype;
        form.p_projgrp.value = projgrp;
        form.p_getcnt.value = getcnt;
        document.form1.submit();
    }
    function whenCoworkerJudge(ordseq,lesson,reptype,projgrp) {
        form = document.form1;
        form.action='/servlet/controller.study.ProjectServlet';
        form.p_process.value = 'ProjectJudgePage';
        form.p_ordseq.value = ordseq;
        form.p_lesson.value = lesson;
        form.p_reptype.value = reptype;
        form.p_projgrp.value = projgrp;
        document.form1.submit();
    }
-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post">
	<input type="hidden" name="p_process"/>
	<input type="hidden" name="p_subj" value="<%=v_subj%>"/>
	<input type="hidden" name="p_year" value="<%=v_year%>"/>
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>
	<input type="hidden" name="p_projseq"/>
	<input type="hidden" name="p_ordseq"/>
	<input type="hidden" name="p_lesson"/>
	<input type="hidden" name="p_reptype"/>
	<input type="hidden" name="p_projgrp"/>
	<input type="hidden" name="p_getcnt"/><!--제출갯수-->

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit8.gif" alt="레포트" /></h1>
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

					<table class="board_list">
					<colgroup><col width="10%" /><col width="40%" /><col width="17%" /><col width="11%" /><col width="9%" /><col width="19%" /></colgroup>
					<thead>
					<tr class="gray">
						<th><p>번호 </p></th>
						<th><p>제목</p></th>
						<th><p>제출기간</p></th>
						<th><p>제출여부</p></th>
						<th><p>제출일</p></th>
						<th class="end"><p>취득점수/만점</p></th>
					</tr>
					</thead>
					<tbody>
<%
            for(i = 0; i < list.size(); i++) {
                ProjectData data  = (ProjectData)list.get(i);
                v_ordseq        = data.getOrdseq();
                v_projseq       = data.getProjseq();
                v_lesson        = data.getLesson();
                v_title         = data.getTitle();
                v_reptype       = data.getReptype();
                v_ldate         = data.getLdate();

                v_expiredate        = data.getExpiredate();
                v_deadlinesdate         = data.getDeadlinesdate();
                v_deadlineedate       = data.getDeadlineedate();

                if (v_deadlineedate.equals("")) {
                    v_deadlineedate = v_expiredate;
                }

                v_projgrp       = data.getProjgrp();
                v_score         = data.getScore();      //만점
                v_score2        = data.getScore2();     //취득점수
                v_cnt           = data.getCnt();
                if(v_cnt == 0){
                    v_rep_value = "<a href=\"javascript:whenProjectSubmit('" + v_projseq + "', '" + v_ordseq + "','" + v_lesson + "','" + v_reptype+ "','" + v_projgrp + "', '" + v_cnt + "')\" class=\"board_btn2\"><span>제출하기</span></a>";
                    v_jud_value = "미평가";
                    v_ldate     = "-";
                }else{
                    v_rep_value = "<a class=\"board_btn2\"><span>제출완료</span></a>";
                    v_jud_value = v_score2 + "";
                    v_ldate = FormatDate.getFormatDate(v_ldate,"yyyy.MM.dd");
                 }

              if(v_reptype.equals("C")){
          %>
                      <% }else{ %>
					<tr onmouseover="className='over'" onmouseout="className=' '">
						<td class="num"><%= i + 1 %></td>
						<td class="ta_l"><a href="javascript:whenProjectSubmit('<%=v_projseq%>', '<%=v_ordseq%>','<%=v_lesson%>','<%=v_reptype%>','<%=v_projgrp%>', '<%=v_cnt%>')"><%= v_title %></a></td>
						<td class="num"><%=FormatDate.getFormatDate(v_deadlinesdate,"yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_deadlineedate,"yyyy.MM.dd")%></td>
						<td><%= v_rep_value %></td>
						<td class="num"><%= v_ldate %></td>
						<td class="end"><%= v_jud_value %>/<%= v_score %></td>
					</tr>
                      <% }
            } %>
<%	if(i == 0){   %>    
                    <tr> 
                      <td class="board_text1" colspan="6">등록된 내용이 없습니다.</td>
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