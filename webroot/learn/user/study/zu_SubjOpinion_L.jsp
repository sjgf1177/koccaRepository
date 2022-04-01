<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: SUBJ OPINION LIST
//  2. 프로그램명: zu_SubjOpinion_L.jsp
//  3. 개      요: 과정 학습자 의견보기 리스트
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2011.02.28
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_gadminYn = box.getString("p_gadminYn");    //관리자여부
    String  v_subj     = box.getString("p_subj");        //과정코드
    String  v_year     = box.getString("p_year");        //년도
    String  v_subjseq  = box.getString("p_subjseq");     //과정 차수
    String  v_lesson   = box.getString("p_lesson");
    String  v_lessonnm = box.getString("p_lessonnm");
    String  v_pageno = box.getString("p_pageno");
    String  v_opinseq = box.getString("p_opinseq");
    String  v_question  = box.getString("p_question");

    String  v_grcode= box.getSession("tem_grcode");
    String  v_userid= box.getSession("userid");

    String  v_usernm="",  v_contents="", v_indate="";

    int i = 0;
    int	v_cnt = 0;
    int	v_dispnum = 0;
    int	v_rowcount = 1;


    // 페이징관련
    int v_totalpage = 0;
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_viewpageno = box.getInt("p_viewpageno");
	if (v_viewpageno == 0) v_viewpageno = 1;
	
    ArrayList list1  = (ArrayList)request.getAttribute("SubjMyOpinionList");
    ArrayList list2  = (ArrayList)request.getAttribute("SubjOpinionList");

    SubjectBean	subjbean = new SubjectBean();
	SubjectData	subjdata = new SubjectData();
    subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    box.put("needNoDetailRead", "true");
	scoredata	= bean.SelectEduScore(box);
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>학습자 의견보기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
    // 등록
    function opinion_insert() {
  		document.form1.target = "_self";
        document.form1.p_process.value = 'SubjOpinionInsertPage';
        document.form1.submit();
    }

    // 상세보기
    function opinion_select(grcode, subj, year, subjseq, lesson, userid, pageno, opinseq) {
        document.form1.target = "_self";
        document.form1.p_process.value = 'SubjOpinionDetail';
        document.form1.p_grcode.value = grcode;
        document.form1.p_subj.value = subj;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_lesson.value = lesson;
        document.form1.p_userid.value = userid;
        document.form1.p_pageno.value = pageno;
        document.form1.p_opinseq.value = opinseq;
        document.form1.submit();
    }

    //페이지 이동
    function goPage(pageNum) {
    	document.form1.p_viewpageno.value = pageNum;
    	document.form1.action = "/servlet/controller.study.SubjOpinionServlet";
    	document.form1.p_process.value = "SubjOpinionList";
    	document.form1.submit();
    }
//-->
</script>

</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" action="/servlet/controller.study.SubjOpinionServlet">
		<input type="hidden" name="p_process"  value="SubjOpinionList"/>
		<input type="hidden" name="p_subj"     value="<%=v_subj%>"/>
		<input type="hidden" name="p_year"     value="<%=v_year%>"/>
		<input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>"/>
		<input type="hidden" name="p_lesson"   value="<%=v_lesson%>"/>
		<input type="hidden" name="p_lessonnm" value="<%=v_lessonnm%>"/>
		<input type="hidden" name="p_grcode"   value="<%=v_grcode%>"/>
        <input type="hidden" name="p_userid"   value="<%=v_userid%>"/>
        <input type="hidden" name="p_pageno"   value="<%=v_pageno%>"/>
        <input type="hidden" name="p_opinseq"  value="<%=v_opinseq%>"/>
        <input type="hidden" name="p_question" value="<%=v_question%>"/>
		<input type="hidden" name="p_gadminYn" value="<%=v_gadminYn%>"/>
        <input type = "hidden" name = "p_viewpageno"  value = "<%=v_viewpageno%>"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit14.gif" alt="학습자 의견보기" /></h1>
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
                        <dd>&nbsp;&nbsp;&nbsp;&nbsp;</dd>
                        <dt><span>차시</span></dt>
                        <dd class="class_day"><%= v_lesson %>  <%= v_lessonnm%></dd>
					</dl>
				</div></div></div>
<%	} %>
                <div class="content">
					<table width="90%" border="0" cellspacing="0" cellpadding="0" class="mg_t18">										
					    <tr><td	 width="30px"><img src="/images/portal/ico/ico_qna_q.gif" alt="질문" /></td>
                            <td	><%=v_question %></td>
                        </tr>
                    </table>
                </div>

				<div class="content">
					<div class="h3_wrap">
						<h3><img src="/images/portal/myclass/study/pop_stit18.gif" alt="나의 의견" /></h3>
					</div>
					
					<table class="board_list">
					<colgroup><col width="10%" /><col width="62%" /><col width="10%" /><col width="10%" /><col width="8%" /></colgroup>
					<thead>
					<tr class="gray">
						<th><p>번호 </p></th>
						<th><p>의견</p></th>
                        <th><p>작성자</p></th>
						<th><p>작성일자</p></th>
						<th class="end"><p>조회수</p></th>
					</tr>
					</thead>
					<tbody>
<%
	for(i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);

		v_userid= dbox.getString("d_userid");
		v_usernm= dbox.getString("d_name");
        v_contents = dbox.getString("d_contents");
		v_indate  = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy.MM.dd");
		v_cnt = dbox.getInt("d_cnt");
		v_dispnum = dbox.getInt("d_dispnum");
%>
					<tr onmouseover="className='over'" onmouseout="className=' '">
						<td class="num"><%= v_dispnum %></td>
						<td class="ta_l"><a href="javascript:opinion_select('<%=v_grcode%>', '<%=v_subj%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_lesson%>', '<%=v_userid%>', '<%=v_pageno%>', '<%=v_opinseq%>');"><%= v_contents %></a></td>
						<td><%= v_usernm %></td>
						<td class="num"><%= v_indate %></td>
						<td class="num end"><%= v_cnt %></td>
					</tr>
<%
	}
%>
<%	if(i == 0){   %>    
                    <tr> 
                      <td colspan="5">등록된 내용이 없습니다.</td>
                    </tr>    
<%	} %>            
					</tbody>
					</table>
					<!--<p class="list_btn"><a href="javascript:opinion_insert();" class="btn_gr"><span>등록</span></a></p>-->
				</div>


                <div class="content">
					<div class="h3_wrap">
						<h3><img src="/images/portal/myclass/study/pop_stit19.gif" alt="동료 학습자 의견" /></h3>
					</div>

					<table class="board_list">
					<colgroup><col width="10%" /><col width="62%" /><col width="10%" /><col width="10%" /><col width="8%" /></colgroup>
					<thead>
					<tr class="gray">
						<th><p>번호 </p></th>						
						<th><p>의견</p></th>
                        <th><p>작성자</p></th>
						<th><p>작성일자</p></th>
						<th class="end"><p>조회수</p></th>
					</tr>
					</thead>
					<tbody>
<%
	for(i = 0; i < list2.size(); i++) {
		DataBox dbox = (DataBox)list2.get(i);
		v_totalpage = dbox.getInt("d_totalpage");

		v_userid= dbox.getString("d_userid");
		v_usernm= dbox.getString("d_name");
        v_contents = dbox.getString("d_contents");
		v_indate  = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy.MM.dd");
		v_cnt = dbox.getInt("d_cnt");
		v_dispnum = dbox.getInt("d_dispnum");
%>
					<tr onmouseover="className='over'" onmouseout="className=' '">
						<td class="num"><%= v_dispnum %></td>
                        <td class="ta_l"><a href="javascript:opinion_select('<%=v_grcode%>', '<%=v_subj%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_lesson%>', '<%=v_userid%>', '<%=v_pageno%>', '<%=v_opinseq%>');"><%= v_contents %></a></td>
                        <td><%= v_usernm %></td>
						<td class="num"><%= v_indate %></td>
						<td class="num end"><%= v_cnt %></td>
					</tr>
<%
	}
%>
<%	if(i == 0){   %>
                    <tr>
                      <td colspan="5">등록된 내용이 없습니다.</td>
                    </tr>
<%	} %>
					</tbody>
					</table>

                    <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_viewpageno, 0, 10, v_rowcount) %>

				</div>


			</div>
		</div>
	</div>
</form>

</body>
</html>
