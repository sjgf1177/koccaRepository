<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 평가 결과보기
//  2. 프로그램명: zu_ExamPaperResult_L.jsp
//  3. 개      요: 평가  결과보기
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.1.14
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	ArrayList    blist = (ArrayList)request.getAttribute("UserPaperResult");
	ArrayList    blist2 = (ArrayList)request.getAttribute("UserPaperResult2");

	DataBox v_box = (DataBox)blist2.get(0);
	Vector v_answer = (Vector)blist2.get(1);
	Vector v_corrected = (Vector)blist2.get(2);
	
	Vector  v_averageV = (Vector)request.getAttribute("ExamResultAverage");    

	//double v_average = box.getDouble("p_average");
	//int v_personcnt = box.getInt("p_personcnt");
	double v_average = Double.parseDouble((String)v_averageV.get(2));
	int v_personcnt = Integer.parseInt((String)v_averageV.get(1));
	
	int v_position = v_personcnt - v_box.getInt("d_overman");
	double v_percent = (v_position / v_personcnt ) * 100 ;

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
</head>
<body id="pop_study"><!-- popup size : 890* -->
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
					<div class="info_box">
						<div>
							<dl>
								<dt>시험유형</dt>
								<dd>: <%= v_box.getString("d_examtypenm") %></dd>
							</dl>
							<dl>
								<dt>응시시간</dt>
								<dd>:  <%= FormatDate.getFormatDate( v_box.getString("d_started"),"yyyy-MM-dd HH:mm:ss") %> ~ <%= FormatDate.getFormatDate( v_box.getString("d_ended"),"yyyy-MM-dd HH:mm:ss") %></dd>
							</dl>
						</div>
					</div>
					
					<h3><img src="/images/portal/myclass/study/pop_stit9.gif" alt="평가결과" /></h3>
					<table class="b_list">
					<colgroup><col width="20%" /><col width="20%" /><col width="20%" /><col width="20%" /><col width="20%" /></colgroup>
					<thead>
					<tr>
						<th>문제수</th>
						<th>정답수</th>
						<th>득점</th>
						<th>석차/응시자</th>
						<th class="end">전체평균</th>
					</tr>
					</thead>
					<tbody>
					<tr>
						<td><%= v_box.getInt("d_examcnt") %></td>
						<td><%= v_box.getInt("d_answercnt") %></td>
						<td><%= v_box.getInt("d_score") %></td>
						<td><%= v_personcnt-(v_personcnt-v_box.getInt("d_overman"))+1 %>/<%= v_personcnt %></td>
						<td class="end"><%= v_average %></td>
					</tr>
					</tbody>
					</table>

<% 
    if((v_box.getString("d_isopenexp")).equals("Y")){
    String v_selfanswer = "";

		for (int i=0; i < blist.size(); i++) {
	        ArrayList list = (ArrayList)blist.get(i);  
	        DataBox dbox = (DataBox)list.get(0);  
%>
					<table class="write mg_t30">
						<colgroup><col width="7%" /><col width="79%" /><col width="7%" /><col width="7%" /></colgroup>
						<tr class="test">
							<th><%if(((String)v_corrected.get(i)).equals("1")){%><img src="/images/portal/ico/ico_yes_yellow.gif" alt="정답" /><%}else{%><img src="/images/portal/ico/ico_no_yellow.gif" alt="오답" /><%}%></th>
							<th class="ta_l"><strong><%= String.valueOf(i+1)+"." + dbox.getString("d_examtext") %></strong></th>
							<th>난이도</th>
							<th class="end">득점</th>
						</tr>
<%
			if (dbox.getString("d_examtype").equals(ExamBean.OBJECT_QUESTION)) {   // 단일선택
%>
						<tr class="test">
							<td class="test_list"><br /><br /><span class="test_yellow"><span>정답</span></span></td>
							<td class="ta_l test_list">
<%
				for (int j=0; j < list.size(); j++) {
                	dbox  = (DataBox)list.get(j); 
                	if (dbox != null) { 
                		
    					int v_answersel = 0;
    					if(StringManager.trim((String)v_answer.get(i)).length() == 0){
    						v_answersel = 0;
    					} else if(!((String)v_answer.get(i)).equals("")) {
    				    	v_answersel = Integer.parseInt((String)v_answer.get(i));
    				    }

    					if(dbox.getInt("d_selnum") == v_answersel) {
    						v_selfanswer = "<span class=\"test_blue\"><span>본인답</span></span>";
    					} else {
    						v_selfanswer = "";
    					}

						if(dbox.getString("d_isanswer").equals("Y")){ %>
							<%= dbox.getInt("d_selnum") %>. <em><%=dbox.getString("d_seltext")%></em> <%= v_selfanswer %><%= j==list.size() ? "" : "<br />" %>
<%
						} else {
%>
							<%= dbox.getInt("d_selnum") %>. <%=dbox.getString("d_seltext")%> <%= v_selfanswer %><%= j==list.size() ? "" : "<br />" %>
<%
						}
                	}
            	}
%>
							</td>
							<td class="test_list"><%= dbox.getString("d_levelsnm") %></td>
							<td class="test_list end"><%= ((String)v_corrected.get(i)).equals("1") ? v_box.getInt("d_exampoint") : "0" %></td>
						</tr>
<%
        	} else if (dbox.getString("d_examtype").equals(ExamBean.OX_QUESTION)) {   // ox선택 
%>
						<tr class="test">
							<td class="test_list"><br /><br /><span class="test_yellow"><span>정답</span></span></td>
							<td class="ta_l test_list">
<%
				for (int j=0; j < list.size(); j++) {
                	dbox  = (DataBox)list.get(j); 
                	if (dbox != null) { 
                		
    					int v_answersel = 0;
    					if(StringManager.trim((String)v_answer.get(i)).length() == 0){
    						v_answersel = 0;
    					} else if(!((String)v_answer.get(i)).equals("")) {
    				    	v_answersel = Integer.parseInt((String)v_answer.get(i));
    				    }

    					if(dbox.getInt("d_selnum") == v_answersel) {
    						v_selfanswer = "<span class=\"test_blue\"><span>본인답</span></span>";
    					} else {
    						v_selfanswer = "";
    					}
                		
						if(dbox.getString("d_isanswer").equals("Y")){ %>
							<%= dbox.getInt("d_selnum") %>. <em><%=dbox.getString("d_seltext")%></em> <%= v_selfanswer %><%= j==list.size() ? "" : "<br />" %>
<%
						} else {
%>
							<%= dbox.getInt("d_selnum") %>. <%=dbox.getString("d_seltext")%> <%= v_selfanswer %><br />
<%
						}
                	}
            	}
%>
							</td>
							<td class="test_list"><%= dbox.getString("d_levelsnm") %></td>
							<td class="test_list end"><%= ((String)v_corrected.get(i)).equals("1") ? v_box.getInt("d_exampoint") : "0" %></td>
						</tr>
<%
        	} else if (dbox.getString("d_examtype").equals(ExamBean.SUBJECT_QUESTION)) {  // 주관식 
%>
						<tr class="test">
							<td class="test_list"><br /><br /><span class="test_blue"><span>본인답</span></span></td>
							<td class="ta_l test_list">
								<%=dbox.getString("d_levelsnm")%>
							</td>
							<td class="test_list"><%= dbox.getString("d_levelsnm") %></td>
							<td class="test_list end"><%= ((String)v_corrected.get(i)).equals("1") ? v_box.getInt("d_exampoint") : "0" %></td>
						</tr>


						<tr class="test">
							<td class="test_list"><br /><br /><span class="test_yellow"><span>정답</span></span></td>
							<td class="ta_l test_list end" colspan="3">
<%
				for (int j=0; j < list.size(); j++) {
                	dbox  = (DataBox)list.get(j); 
                	if (dbox != null) { 
%>
								<%= j+1 %>. <%=dbox.getString("d_seltext")%><%= j==list.size() ? "" : "<br />" %>
<%
					}
		   		}
%>
							</td>
						</tr>
<%             
	        } else if (dbox.getString("d_examtype").equals(ExamBean.MULTI_QUESTION)) {  // 다답식
            	for (int j=0; j < list.size(); j++) {
                	dbox  = (DataBox)list.get(j); 
                	if (dbox != null) { %>
						<tr class="test">
							<td class="test_list"><br /><br /><span class="test_yellow"><span>정답</span></span></td>
							<td class="ta_l test_list"></td>
							<td class="test_list"></td>
							<td class="test_list end"></td>
						</tr>
<%
					}
				}	
	 		}
%>
						<tr class="test">
							<td class="test_list">해설</td>
							<td colspan="3" class="ta_l end"><%= dbox.getString("d_exptext") %></td>
						</tr>
					</table>
<%
		}
	}
%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>