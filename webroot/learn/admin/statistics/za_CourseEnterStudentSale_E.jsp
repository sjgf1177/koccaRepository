<%
//**********************************************************
//  1. 제      목: 과정운영 통계 - 매출현황
//  2. 프로그램명 : za_CourseSale_E.jsp
//  3. 개      요: 과정운영 통계 - 매출현황
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.08
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseEnterStudentSale_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

	RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String	v_tid				= "";
    String	v_userid			= "";
    String	v_name				= "";
    String	v_subj				= "";
    String	v_year				= "";
    String	v_subjseq			= "";
    String	v_subjnm			= "";
    String	v_edustart			= "";
    String	v_eduend			= "";
    String	v_paymethod			= "";
    double	v_biyong			= 0;
    String	v_result			= "";
    String	v_pgauthdate		= "";
    String	v_pgauthtime		= "";
    String	v_inputname			= "";
    double	v_price				= 0;
    int		v_rank				= 0;
    String	v_istaxyn			= "";
    String	v_middleclassname	= "";
    String	v_lowerclassname	= "";
    String	v_grseqnm			= "";
    double	v_realtotal			= 0;
    double	v_subtotal			= 0;
    int		v_rowspan			= 0;
    String	v_resno				= "";
    String	v_appdate			= "";
    String	v_comptext			= "";
    String	v_email				= "";
    String	v_handphone			= "";
    int     i           		= 0;
    
    double	v_totalbiyong		= 0;
    double	v_totaldiscount		= 0;
    double	v_totalsubtotal		= 0;
    double	v_totalrealtotal	= 0;
 	
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
        <td align="center" height="30"><font size="3"><b>수강생별 결제액별 입과현황</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">		
        <tr>
          <td width="5%">No</td>
          <td>성명</td>
          <td>아이디</td>
          <td>주민번호</td>
          <td>분야</td>
          <td>난이도</td>
          <td>과정코드</td>
          <td>과정금액</td>
          <td>차수</td>
          <td>과정명</td>
          <td>결제수단</td>
          <td>복수할인액</td>
          <td>실결제금액</td>
          <td>신청일</td>
          <td>소속</td>
          <td>EMAIL</td>
          <td>이동전화</td>
        </tr>
<%  	if (v_action.equals("go")) {
        	ArrayList  list = (ArrayList)request.getAttribute("selectList");

        	String	v_biyong_text		= "";
	        String	v_price_text		= "";
	        String	v_realtotal_text	= "";
	        String	v_subtotal_text		= "";
	        String	v_discount_text		= "";

        	for (i=0; i<list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);

		        v_tid				= dbox.getString("d_tid");
		        v_userid			= dbox.getString("d_userid");
		        v_name				= dbox.getString("d_name");
		        v_subj				= dbox.getString("d_subj");
		        v_year				= dbox.getString("d_year");
		        v_subjseq			= dbox.getString("d_subjseq");
		        v_subjnm			= dbox.getString("d_subjnm");
		        v_edustart			= dbox.getString("d_edustart");
		        v_eduend			= dbox.getString("d_eduend");
		        v_paymethod			= dbox.getString("d_paymethod");
		        v_biyong			= dbox.getDouble("d_biyong");
		        v_result			= dbox.getString("d_result");
		        v_pgauthdate		= dbox.getString("d_pgauthdate");
		        v_pgauthtime		= dbox.getString("d_pgauthtime");
		        v_inputname			= dbox.getString("d_inputname");
		        v_price				= dbox.getDouble("d_price");
		        v_rank				= dbox.getInt("d_rank");
		        v_istaxyn			= dbox.getString("d_istaxyn");
		        v_middleclassname	= dbox.getString("d_middleclassname");
		        v_lowerclassname	= dbox.getString("d_lowerclassname");
		        v_grseqnm			= dbox.getString("d_grseqnm");
		        v_realtotal			= dbox.getDouble("d_realtotal");
		        v_subtotal			= dbox.getDouble("d_subtotal");
		        v_rowspan			= dbox.getInt("d_rowspan");
		        v_resno				= dbox.getString("d_resno");
		        v_appdate			= dbox.getString("d_appdate");
		        v_comptext			= dbox.getString("d_comptext");
		        v_email				= dbox.getString("d_email");
		        v_handphone			= dbox.getString("d_handphone");
		        
		        v_biyong_text		= new java.text.DecimalFormat("###,###,##0").format(v_biyong);
		        v_price_text		= new java.text.DecimalFormat("###,###,##0").format(v_price);
		        v_realtotal_text	= new java.text.DecimalFormat("###,###,##0").format(v_realtotal);
		        v_subtotal_text		= new java.text.DecimalFormat("###,###,##0").format(v_subtotal);
		        v_discount_text		= new java.text.DecimalFormat("###,###,##0").format(v_subtotal - v_realtotal);
		        
		        v_totalbiyong		+= v_biyong;
		        v_totaldiscount		+= (v_subtotal - v_realtotal);
		        v_totalsubtotal		+= v_subtotal;
		        v_totalrealtotal	+= v_realtotal;

		        if ("Card".equals(v_paymethod) || "VCard".equals(v_paymethod)) {
		        	v_paymethod	= "신용카드";
		        } else if ("DirectBank".equals(v_paymethod)) {
		        	v_paymethod	= "계좌이체";
		        } else if ("VBank".equals(v_paymethod)) {
		        	v_paymethod	= "가상계좌";
		        } else if ("BankBook".equals(v_paymethod)) {
		        	v_paymethod	= "무통장입금";
		        }
%>
        <tr>
          <td><%= i + 1 %></td>
          <td><%= v_name %></td>
          <td><%= v_userid %></td>
          <td><%= StringManager.substring(v_resno,0,6) %>-<%= StringManager.substring(v_resno,7,13) %></td>
          <td><%= v_middleclassname %></td>
          <td><%= v_lowerclassname %></td>
          <td><%= v_subj %></td>
          <td><%= v_biyong_text %></td>
          <td><%= StringManager.cutZero(v_subjseq) %></td>
          <td><%= v_subjnm %></td>
          <td><%= v_paymethod %></td>
<%
      	     	if (v_rowspan != 0) {
%>
          <td rowspan="<%= v_rowspan %>"><%= v_discount_text %></td>
          <td rowspan="<%= v_rowspan %>"><%= v_realtotal_text %></td>
<%
            	}
%>
          <td class="table_02_1"><%= FormatDate.getFormatDate(v_appdate,"yyyy/MM/dd") %></td>
          <td class="table_02_1"><%= v_comptext %></td>
          <td class="table_02_1"><%= v_email %></td>
          <td class="table_02_1"><%= v_handphone %></td>
        </tr>
<%
        	}
			if (i == 0) {
%>
        <tr>
          <td align="center" bgcolor="#FFFFFF" height="100" colspan="17">등록된 내용이 없습니다.</td>
        </tr>
<%			} else { %>
	    <tr>
          <td></td>
          <td colspan="6">합계</td>
          <td><%= new java.text.DecimalFormat("###,###,##0").format(v_totalbiyong) %></td>
          <td colspan="3">합계</td>
          <td><%= new java.text.DecimalFormat("###,###,##0").format(v_totaldiscount) %></td>
          <td><%= new java.text.DecimalFormat("###,###,##0").format(v_totalrealtotal) %></td>
          <td colspan="4"></td>
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