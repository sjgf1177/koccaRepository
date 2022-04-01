<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 수료현황 - 개인별  현황 엑셀
//  2. 프로그램명 : za_CoursePassPerson_E.jsp
//  3. 개      요: 수료현황 - 전체  현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.11
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_CoursePassPerson_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String	v_userid		= "";
    String	v_name			= "";
    String	v_resno			= "";
    String	v_upperclass	= "";
    String	v_middleclassname	= "";
    String	v_lowerclassname	= "";
    String	v_subj			= "";
    String	v_year			= "";
    String	v_subjnm		= "";
    String	v_subjseq		= "";
    String	v_edustart		= "";
    String	v_eduend		= "";
    String	v_grcode		= "";
    String	v_gyear			= "";
    String	v_grseq			= "";
    String	v_isgraduated	= "";
    double	v_score			= 0;
    double	v_tstep			= 0;
    double	v_mtest			= 0;
    double	v_ftest			= 0;
    double	v_htest			= 0;
    double	v_report		= 0;
    double	v_etc1			= 0;
    double	v_etc2			= 0;
    double	v_avtstep		= 0;
    double	v_avmtest		= 0;
    double	v_avftest		= 0;
    double	v_avhtest		= 0;
    double	v_avreport		= 0;
    double	v_avetc1		= 0;
    double	v_avetc2		= 0;
    int		v_exam_cnt		= 0;
    int		v_sul_cnt1		= 0;
    int		v_sul_cnt2		= 0;
    double	v_sul_rate		= 0;
    int		v_qna_cnt		= 0;           
	
    double	v_total_score	= 0;
    double	v_total_tstep	= 0;
    double	v_total_mtest	= 0;
    double	v_total_ftest	= 0;
    double	v_total_htest	= 0;
    double	v_total_report	= 0;
    double	v_total_etc1	= 0;
    double	v_total_etc2	= 0;
    int		v_total_exam_cnt= 0;
    int		v_total_sul_cnt	= 0;
    int		v_total_sul_cnt1= 0;
    int		v_total_sul_cnt2= 0;
    double	v_total_sul_rate= 0;
    int		v_total_qna_cnt	= 0;           
    
    String	v_total_score_rate		= "";
    String	v_total_tstep_rate		= "";
    String	v_total_mtest_rate		= "";
    String	v_total_ftest_rate		= "";
    String	v_total_htest_rate		= "";
    String	v_total_report_rate		= "";
    String	v_total_etc1_rate		= "";
    String	v_total_etc2_rate		= "";
    String	v_total_exam_cnt_rate	= "";
    String	v_total_sul_cnt_rate	= "";
    String	v_total_sul_cnt1_rate	= "";
    String	v_total_sul_cnt2_rate	= "";
    String	v_total_sul_rate_rate	= "";
    String	v_total_qna_cnt_rate	= "";           
	
    int     i           		= 0;
    
    String	ss_grcode			= box.getString("s_grcode");		//교육그룹
    String	ss_gyear			= box.getString("s_gyear");			//년도
    String	ss_grseq			= box.getString("s_grseq");			//교육차수
    String	ss_grseqnm			= box.getString("s_grseqnm");		//교육차수명
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_option1			= box.getString("s_option1");		//통계구분
    String  ss_option2			= box.getString("s_option2");		//통계별 구분2
    String  ss_option3			= box.getString("s_option3");		//통계별 구분3
    
    if (ss_enddate.equals("")) {
    	ss_startdate	= FormatDate.getDateAdd(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd", "month", -1);
    	ss_enddate		= FormatDate.getFormatDate(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd");
    }
    
    String  v_action	= box.getString("p_action");
	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>전체 수료현황</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
        <tr>
          <td>No</td>
          <td>성명</td>
          <td>아이디</td>
          <td>주민번호</td>
          <td>분야</td>
          <td>난이도</td>
          <td>교육기간</td>
          <td>과정명</td>
          <td>진도율(%)</td>
          <td>리포트</td>
          <td>중간평가</td>
          <td>최종평가</td>
          <td>형성평가</td>
          <td>참여도</td>
          <td>기타</td>
          <td>총점</td>
          <td>평가응시수</td>
          <td>설문응시수</td>
          <td>수료여부</td>
          <td>사전설문</td>
          <td>고객만족도</td>
          <td>만족도</td>
          <td>나의상담내역</td>
       </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				v_userid			= dbox.getString("d_userid");
			    v_name				= dbox.getString("d_name");
			    v_resno				= dbox.getString("d_resno");
			    v_grcode			= dbox.getString("d_grcode");
				v_gyear				= dbox.getString("d_gyear");
				v_grseq				= dbox.getString("d_grseq");
				v_upperclass		= dbox.getString("d_upperclass");
				v_middleclassname	= dbox.getString("d_middleclassname");
				v_lowerclassname	= dbox.getString("d_lowerclassname");
				v_edustart			= dbox.getString("d_edustart");
				v_eduend			= dbox.getString("d_eduend");
				v_subj				= dbox.getString("d_subj");
				v_year				= dbox.getString("d_year");
				v_subjseq			= dbox.getString("d_subjseq");
				v_subjnm			= dbox.getString("d_subjnm");
			    v_isgraduated		= dbox.getString("d_isgraduated");
			    v_score				= dbox.getDouble("d_score");
			    v_tstep				= dbox.getDouble("d_tstep");
			    v_mtest				= dbox.getDouble("d_mtest");
			    v_ftest				= dbox.getDouble("d_ftest");
			    v_htest				= dbox.getDouble("d_htest");
			    v_report			= dbox.getDouble("d_report");
			    v_etc1				= dbox.getDouble("d_etc1");
			    v_etc2				= dbox.getDouble("d_etc2");
			    v_avtstep			= dbox.getDouble("d_avtstep");
			    v_avmtest			= dbox.getDouble("d_avmtest");
			    v_avftest			= dbox.getDouble("d_avftest");
			    v_avhtest			= dbox.getDouble("d_avhtest");
			    v_avreport			= dbox.getDouble("d_avreport");
			    v_avetc1			= dbox.getDouble("d_avetc1");
			    v_avetc2			= dbox.getDouble("d_avetc2");
			    v_exam_cnt			= dbox.getInt("d_exam_cnt");
			    v_sul_cnt1			= dbox.getInt("d_sul_cnt1");
			    v_sul_cnt2			= dbox.getInt("d_sul_cnt2");
			    v_sul_rate			= dbox.getDouble("d_sul_rate");
			    v_qna_cnt			= dbox.getInt("d_qna_cnt");       
			    
			    v_total_score		+= v_score;	 
			    v_total_tstep		+= v_tstep;
			    v_total_mtest		+= v_mtest;
			    v_total_ftest		+= v_ftest;	
			    v_total_htest		+= v_htest;	
			    v_total_report		+= v_report;	
			    v_total_etc1		+= v_etc1;	
			    v_total_etc2		+= v_etc2;	
			    v_total_exam_cnt	+= v_exam_cnt;
			    v_total_sul_cnt		+= v_sul_cnt1 + v_sul_cnt2;
			    v_total_sul_rate	+= v_sul_rate;
			    v_total_qna_cnt		+= v_qna_cnt;	
%>
        <tr>
          <td><%= i + 1 %></td>
          <td><%= v_name %></td>
          <td><%= v_userid %></td>
          <td><%= v_resno %></td>
          <td><%= v_middleclassname %></td>
          <td><%= v_lowerclassname %></td>
          <td><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
          <td><%= v_subjnm %></td>
          <td><%= v_tstep %>%</td>
          <td><%= v_report %></td>
          <td><%= v_mtest %></td>
          <td><%= v_ftest %></td>
          <td><%= v_htest %></td>
          <td><%= v_etc1 %></td>
          <td><%= v_etc2 %></td>
          <td><%= v_score %></td>
          <td><%= v_exam_cnt %></td>
          <td><%= v_sul_cnt1 + v_sul_cnt2 %></td>
          <td><%= v_isgraduated %></td>
          <td><%= (v_sul_cnt1 > 0) ? "Y" : "N" %></td>
          <td><%= (v_sul_cnt2 > 0) ? "Y" : "N" %></td>
          <td><%= v_sul_rate %>%</td>
          <td><%= v_qna_cnt %></td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="23">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { 
			    v_total_score_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_score	 / i );
			    v_total_tstep_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_tstep	 / i );
			    v_total_mtest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_mtest	 / i );
			    v_total_ftest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_ftest	 / i );
			    v_total_htest_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_htest	 / i );
			    v_total_report_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_report	 / i );
			    v_total_etc1_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_etc1	 / i );
			    v_total_etc2_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_etc2	 / i );
			    v_total_exam_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_exam_cnt / i );
			    v_total_sul_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt / i );
			    v_total_sul_cnt1_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt1 / i );
			    v_total_sul_cnt2_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt2 / i );
			    v_total_sul_rate_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_rate / i );
			    v_total_qna_cnt_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_qna_cnt	 / i );	
%>
	    <tr>
          <td colspan="7">총계</td>
          <td><%= i %></td>
          <td><%= v_total_tstep_rate		%></td>
          <td><%= v_total_report_rate	%></td>
          <td><%= v_total_mtest_rate		%></td>
          <td><%= v_total_ftest_rate		%></td>
          <td><%= v_total_htest_rate		%></td>
          <td><%= v_total_etc1_rate		%></td>
          <td><%= v_total_etc2_rate		%></td>
          <td><%= v_total_score_rate		%></td>
          <td><%= v_total_exam_cnt_rate	%></td>
          <td><%= v_total_sul_cnt_rate	%></td>
          <td></td>
          <td></td>
          <td></td>
          <td><%= v_total_sul_rate_rate	%></td>
          <td><%= v_total_qna_cnt_rate	%></td>
        </tr>
<%			}
		}
%>
        </table>
      </td>
  </tr>
</table>
</body>
</html>