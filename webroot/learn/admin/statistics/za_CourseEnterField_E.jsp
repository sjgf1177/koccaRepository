<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 입과현황 - 분야별 일반  현황 엑셀
//  2. 프로그램명 : za_CourseEnterField_E.jsp
//  3. 개      요: 입과현황 - 분야별 일반  현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.09
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseEnterField_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

	String	v_grcode			= "";
	String	v_gyear				= "";
	String	v_grseq				= "";
	String	v_upperclass		= "";
	String	v_middleclass		= "";
	String	v_middleclassname	= "";
	String	v_lowerclass		= "";
	String	v_lowerclassname	= "";
	int		v_rank				= 0;
	int		v_subj_cnt			= 0;
	int		v_class_cnt			= 0;
	int		v_total				= 0;
	int		v_rowspan			= 0;
	double	v_lowerclass_rate	= 0;
	int		v_middleclass_cnt	= 0;
	int		v_total_cnt			= 0;
	double	v_middleclass_rate	= 0;
    int     i           		= 0;
    
    int		v_total_subj_cnt	= 0;
    int		v_total_class_cnt	= 0;
    int		v_total_middleclass_cnt	= 0;
    
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
        <td align="center" height="30"><font size="3"><b>분야별 일반 입과현황</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
        <tr>
          <td width="5%" rowspan="2">No</td>
          <td colspan="2">구분</td>
          <td rowspan="2">개설과목(개)</td>
          <td colspan="2">수강인원(명)</td>
          <td colspan="2">수강생비율(%)</td>
        </tr>
        <tr>
          <td>분야</td>
          <td>난이도</td>
          <td>난이도별</td>
          <td>분야별</td>
          <td>난이도별</td>
          <td>분야별</td>
        </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				v_grcode			= dbox.getString("d_grcode");			
				v_gyear				= dbox.getString("d_gyear");		
				v_grseq				= dbox.getString("d_grseq");
				v_upperclass		= dbox.getString("d_upperclass");
				v_middleclass		= dbox.getString("d_middleclass");
				v_middleclassname	= dbox.getString("d_middleclassname");
				v_lowerclass		= dbox.getString("d_lowerclass");
				v_lowerclassname	= dbox.getString("d_lowerclassname");
				v_rank				= dbox.getInt("d_rank");
				v_subj_cnt			= dbox.getInt("d_subj_cnt");
				v_class_cnt			= dbox.getInt("d_class_cnt");
				v_total				= dbox.getInt("d_total");
				v_rowspan			= dbox.getInt("d_rowspan");
				v_lowerclass_rate	= dbox.getDouble("d_lowerclass_rate");	
				v_middleclass_cnt	= dbox.getInt("d_middleclass_cnt");
				v_total_cnt			= dbox.getInt("d_total_cnt");
				v_middleclass_rate	= dbox.getDouble("d_middleclass_rate");	
				
			    v_total_subj_cnt	+= v_subj_cnt;
			    v_total_class_cnt	+= v_class_cnt;
			    v_total_middleclass_cnt	+= v_middleclass_cnt;

%>
        <tr>
          <td><%= i + 1 %></td>
<%
			if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><%= v_middleclassname %></td>
<%
			}
%>
          <td><%= v_lowerclassname %></td>
          <td><%= v_subj_cnt %></td>
          <td><%= v_class_cnt %></td>
<%
			if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><%= v_middleclass_cnt %></td>
<%
			}
%>
          <td><%= v_lowerclass_rate %></td>
<%
			if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><%= v_middleclass_rate %></td>
<%
			}
%>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="3">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { %>
	    <tr>
          <td colspan="3">합계</td>
          <td><%= v_total_subj_cnt %></td>
          <td><%= v_total_class_cnt %></td>
          <td><%= v_total_middleclass_cnt %></td>
          <td colspan="2">100%</td>
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