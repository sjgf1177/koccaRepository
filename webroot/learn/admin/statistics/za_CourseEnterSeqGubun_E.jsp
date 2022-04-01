<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 입과현황 - 차수별 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  현황
//  2. 프로그램명 : za_CourseEnterSeqGubun_E.jsp
//  3. 개      요: 입과현황 - 차수별 일반  현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.10
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseEnterSeqGubun_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

	String	v_grcode	= "";
	String	v_gyear		= "";
	String	v_grseq		= "";
	String	v_grseqnm	= "";
	String	v_subj		= "";
	String	v_subjnm	= "";
	int		v_rank		= 0;
	int		v_rowspan	= 0;
    int     i           = 0;
    int     n           = 1;
    String	stat_text	= "";
    
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
    
    if ("MEM".equals(ss_option3)) {
    	stat_text	= "회원구분별";
    } else if ("GEN".equals(ss_option3)) {
    	stat_text	= "성별";
    } else if ("YEA".equals(ss_option3)) {
    	stat_text	= "연령대별";
    } else if ("SCH".equals(ss_option3)) {
    	stat_text	= "학력별";
    } else if ("CUL".equals(ss_option3)) {
    	stat_text	= "문화산업종사자별";
    } else if ("NCU".equals(ss_option3)) {
    	stat_text	= "비문화사업종사자별";
    }

    if (ss_enddate.equals("")) {
    	ss_startdate	= FormatDate.getDateAdd(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd", "month", -1);
    	ss_enddate		= FormatDate.getFormatDate(FormatDate.getDate("yyyyMMdd"), "yyyy-MM-dd");
    }
    
    String  v_action	= box.getString("p_action");
	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
	
	ArrayList  listGubun	= (ArrayList)request.getAttribute("selectGubunList");

	int [] v_total_cnt			= new int [listGubun.size()];

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>수강생별 <%= stat_text %> 입과현황</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
        <tr>
          <td width="5%">No</td>
          <td>교육차스</td>
          <td>과정명</td>
<%
			for (int j = 0; j < listGubun.size() ; j++) {
				DataBox dboxGubun = (DataBox)listGubun.get(j);
%>
          <td><%= dboxGubun.getString("d_codenm") %></td>
<%
			}
%>
        </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list	= (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

				v_grcode	= dbox.getString("d_grcode");
				v_gyear		= dbox.getString("d_gyear");
				v_grseq		= dbox.getString("d_grseq");
				v_grseqnm	= dbox.getString("d_grseqnm");
				v_subj		= dbox.getString("d_subj");
				v_subjnm	= dbox.getString("d_subjnm");
				v_rank		= dbox.getInt("d_rank");
				v_rowspan	= dbox.getInt("d_rowspan");
%>
        <tr>
<%
			if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><%= n %></td>
          <td rowspan="<%= v_rowspan %>"><%= v_gyear %>-<%= v_grseq %><%= v_grseqnm %></td>
<%
				n++;
			}
%>
          <td><%= v_subjnm %></td>
<%
			for (int j = 0; j < listGubun.size() ; j++) {
				DataBox dboxGubun = (DataBox)listGubun.get(j);
%>
          <td><%= dbox.getInt("d_cnt"+(j+1)) %></td>
<%
					v_total_cnt[j]		 += dbox.getInt("d_cnt"+(j+1));
			}
%>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="<%= listGubun.size() + 3 %>">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { %>
	    <tr>
          <td colspan="3">합계</td>
<%
			for (int j = 0; j < listGubun.size() ; j++) {
				DataBox dboxGubun = (DataBox)listGubun.get(j);
%>
          <td><%= v_total_cnt[j] %></td>
<%
			}
%>
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