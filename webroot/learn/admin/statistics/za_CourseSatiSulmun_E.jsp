<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 만족도 설문 엑셀
//  2. 프로그램명 : za_CourseSatiSulmun_E.jsp
//  3. 개      요: 수료현황 - 전체  현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.12
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseSatiSulmun_E.xls");
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
    String	v_subj				= "";
    String	v_subjnm			= "";
    String	v_grseqnm			= "";
    double	v_distcode1_avg		= 0; 
    int		v_rank				= 0;
    int		v_rank2				= 0;
    int		v_rowspan			= 0;
    int		v_rowspan2			= 0;
	
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
          <td>교육차수</td>
          <td>분야</td>
          <td>과정명</td>
          <td>만족도 점수</td>
       </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

			    v_grcode			= dbox.getString("d_grcode");
				v_gyear				= dbox.getString("d_gyear");
				v_grseq				= dbox.getString("d_grseq");
				v_grseqnm			= dbox.getString("d_grseqnm");
				v_upperclass		= dbox.getString("d_upperclass");
				v_middleclassname	= dbox.getString("d_middleclassname");
				v_subj				= dbox.getString("d_subj");
				v_subjnm			= dbox.getString("d_subjnm");
				v_distcode1_avg		= dbox.getDouble("d_distcode1_avg");
				v_rank				= dbox.getInt("d_rank");
				v_rank2				= dbox.getInt("d_rank2");
				v_rowspan			= dbox.getInt("d_rowspan");
				v_rowspan2			= dbox.getInt("d_rowspan2");
%>
        <tr>
          <td><%= i + 1 %></td>
<%
			if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><b><%= v_gyear %>-<%= v_grseq %><br/><%= v_grseqnm %></b></td>
<%
			}
%>
<%
			if (v_rowspan2 != 0) {
%>
          <td rowspan="<%= v_rowspan2 %>"><%= v_middleclassname %></td>
<%
			}
%>
          <td><%= v_subjnm %></td>
          <td><%= v_distcode1_avg %>%</td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="5">등록된 내용이 없습니다.</td>
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