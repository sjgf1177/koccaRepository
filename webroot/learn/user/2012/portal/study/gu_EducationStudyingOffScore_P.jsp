<%
//**********************************************************
//  1. 제      목: 오프라인 과정 단기 점수 팝업
//  2. 프로그램명: gu_EducationStudyingOffScore_P.jsp
//  3. 개      요: 수강중인 오프라인과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009/11/25
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	String	v_subj		= "";
	String	v_year		= "";
	String	v_subjseq	= "";
	double	v_score		= 0d;
	double	v_rank		= 0d;
	double	v_tstep		= 0d;
	double	v_mtest		= 0d;
	double	v_ftest		= 0d;
	double	v_htest		= 0d;
	double	v_report	= 0d;
	double	v_etc1		= 0d;
	double	v_etc2		= 0d;
	double	v_avtstep	= 0d;
	double	v_avmtest	= 0d;
	double	v_avftest	= 0d;
	double	v_avhtest	= 0d;
	double	v_avreport	= 0d;
	double	v_avetc1	= 0d;
	double	v_avetc2	= 0d;
    
	DataBox dbox = (DataBox)request.getAttribute("EducationStudyingOffScoreList");
    if (dbox != null) {
    	v_subj		= dbox.getString("d_subj");
    	v_year		= dbox.getString("d_year");
    	v_subjseq	= dbox.getString("d_subjseq");
    	v_score		= dbox.getDouble("d_score");
    	v_rank		= dbox.getDouble("d_rank");
    	v_tstep		= dbox.getDouble("d_tstep");
    	v_mtest		= dbox.getDouble("d_mtest");
    	v_ftest		= dbox.getDouble("d_ftest");
    	v_htest		= dbox.getDouble("d_htest");
    	v_report	= dbox.getDouble("d_report");
    	v_etc1		= dbox.getDouble("d_etc1");
    	v_etc2		= dbox.getDouble("d_etc2");
    	v_avtstep	= dbox.getDouble("d_avtstep");
    	v_avmtest	= dbox.getDouble("d_avmtest");
    	v_avftest	= dbox.getDouble("d_avftest");
    	v_avhtest	= dbox.getDouble("d_avhtest");
    	v_avreport	= dbox.getDouble("d_avreport");
    	v_avetc1	= dbox.getDouble("d_avetc1");
    	v_avetc2	= dbox.getDouble("d_avetc2");
    }    
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>성적보기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/common.js"></script>
</head>
<body id="popup"><!-- popup size : 550* -->
	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit2.gif" alt="성적보기" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<table class="list">
			<colgroup><col width="18%" /><col width="12%" /><col width="12%" /><col width="12%" /><col width="12%" /><col width="12%" /><col width="12%" /><col width="12%" /></colgroup>
			<thead>
			<tr>
				<th>진도율(점수)</th>
				<th>중간평가</th>
				<th>최종평가</th>
				<th>형성평가</th>
				<th>리포트</th>
				<th>참여도</th>
				<th>기타</th>
				<th class="end">총점</th>
			</tr>
			</thead>
			<tbody>
			<tr>
			    <td><%= v_tstep %>(<%= v_avtstep %>)</td>
                <td><%= v_mtest %>(<%= v_avmtest %>)</td>
                <td><%= v_ftest %>(<%= v_avftest %>)</td>
                <td><%= v_htest %>(<%= v_avhtest %>)</td>
                <td><%= v_report %>(<%= v_avreport %>)</td>
                <td><%= v_etc1 %>(<%= v_avetc1 %>)</td>
                <td><%= v_etc2 %>(<%= v_avetc2 %>)</td>
                <td class="end"><%= v_score %></td>
			</tr>
			</tbody>
			</table>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:self.close()" class="btn_gr"><span>확인</span></a></p>
	</div>
</body>
</html>