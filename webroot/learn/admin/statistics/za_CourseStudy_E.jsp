<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 학습현황 엑셀
//  2. 프로그램명 : za_CourseStudy_E.jsp
//  3. 개      요: 학습현황
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseStudy_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String	v_grcode		= "";
    String	v_gyear			= "";
    String	v_grseq			= "";  
    String	v_grseqnm		= "";
    String	v_edustart		= "";
    String	v_eduend		= "";
    int		v_subj_cnt		= 0;
    int		v_user_cnt		= 0;
    int		v_grad_cnt		= 0;
	double	v_grad_rate		= 0;
    double	v_sul_rate		= 0;
    double	v_biyong		= 0;
    double	v_price			= 0;
    int		v_sul_cnt2		= 0;
    double	v_sul_cnt_rate	= 0;
	
    int		v_total_subj_cnt	= 0;
    int		v_total_user_cnt	= 0;
    int		v_total_grad_cnt	= 0;
	double	v_total_grad_rate	= 0;
    double	v_total_sul_rate	= 0;
    double	v_total_biyong		= 0;
    double	v_total_price		= 0;
    int		v_total_sul_cnt2	= 0;
    double	v_total_sul_cnt_rate= 0;
    
    String	v_total_grad_rate_rate	= "";
    String	v_total_sul_rate_rate	= "";
    String	v_total_sul_cnt_rate_rate	= "";           
	
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

    int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMdd"));  //오늘 날짜 일시 초기화
    int i_eduend   = 0;

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>학습현황</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
        <tr>
          <td>교육차수</td>
          <td>교육기간</td>
          <td>과정수</td>
          <td>수강인원</td>
          <td>수료인원</td>
          <td>수료율</td>
          <td>만족도</td>
          <td>과정금액</td>
          <td>실결제액</td>
          <td>설문 응시인원</td>
          <td>설문 응시율</td>
       </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

			    v_grcode		= dbox.getString("d_grcode");
				v_gyear			= dbox.getString("d_gyear");
				v_grseq			= dbox.getString("d_grseq");
				v_grseqnm		= dbox.getString("d_grseqnm");
				v_edustart		= dbox.getString("d_edustart");
				v_eduend		= dbox.getString("d_eduend");
			    v_subj_cnt		= dbox.getInt("d_subj_cnt");
			    v_user_cnt		= dbox.getInt("d_user_cnt");
			    v_grad_cnt		= dbox.getInt("d_grad_cnt");
				v_grad_rate		= dbox.getDouble("d_grad_rate");
			    v_sul_rate		= dbox.getDouble("d_sul_rate");
			    v_biyong		= dbox.getDouble("d_biyong");
			    v_price			= dbox.getDouble("d_price");
			    v_sul_cnt2		= dbox.getInt("d_sul_cnt2");
			    v_sul_cnt_rate	= dbox.getDouble("d_sul_cnt_rate");
			    
			    if(!v_eduend.equals("")){
			        i_eduend = Integer.parseInt(v_eduend.substring(0,8));
			    }
%>
        <tr>
          <td><%= v_gyear %>-<%= v_grseq %> <%= v_grseqnm %></td>
          <td><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
          <td><%= v_subj_cnt %></td>
          <td><%= v_user_cnt %></td>
<%
			if (v_today >= i_eduend) {

			    v_total_subj_cnt	+= v_subj_cnt;		 
			    v_total_user_cnt	+= v_user_cnt;		
			    v_total_grad_cnt	+= v_grad_cnt;		
				v_total_grad_rate	+= v_grad_rate;		
			    v_total_sul_rate	+= v_sul_rate;	
			    v_total_biyong		+= v_biyong;		
			    v_total_price		+= v_price;			
			    v_total_sul_cnt2	+= v_sul_cnt2;		
			    v_total_sul_cnt_rate+= v_sul_cnt_rate;

%>
          <td><%= v_grad_cnt %></td>
          <td><%= v_grad_rate %></td>
          <td><%= v_sul_rate %>%</td>
<%
			} else {
%>
          <td colspan="3">운영중</td>
<%
			}
%>
          <td><%= new java.text.DecimalFormat("###,###,###.##").format(v_biyong) %></td>
          <td><%= new java.text.DecimalFormat("###,###,###.##").format(v_price) %></td>
          <td><%= v_sul_cnt2 %></td>
          <td><%= v_sul_cnt_rate %>%</td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
        	<td align="center" bgcolor="#FFFFFF" height="100" colspan="23">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { 
		v_total_grad_rate_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_grad_rate / i );
			v_total_sul_rate_rate		= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_rate	/ i );
			v_total_sul_cnt_rate_rate	= new java.text.DecimalFormat("###,###,###.##").format(v_total_sul_cnt_rate / i );
%>
	    <tr>
          <td colspan="2"><%= v_gyear %>년 총계</td>
          <td><%= v_total_subj_cnt %></td>
          <td><%= v_total_user_cnt %></td>
          <td><%= v_total_grad_cnt %></td>
          <td><%= v_total_grad_rate_rate %>%</td>
          <td><%= v_total_sul_rate_rate %>%</td>
          <td><%= new java.text.DecimalFormat("###,###,###.##").format(v_total_biyong) %></td>
          <td><%= new java.text.DecimalFormat("###,###,###.##").format(v_total_price) %></td>
          <td><%= v_total_sul_cnt2 %></td>
          <td><%= v_total_sul_cnt_rate_rate %></td>
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