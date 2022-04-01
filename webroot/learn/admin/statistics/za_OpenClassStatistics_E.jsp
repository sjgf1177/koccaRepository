<%
//**********************************************************
//  1. 제      목: 열린강좌 통계
//  2. 프로그램명 : za_OpenClassStatistics_E.jsp
//  3. 개      요: 열린강좌 통계
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016. 6. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_OpenClass_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%	
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	
	String searchCondition = box.getStringDefault("searchCondition", "year");
	String searchStartDate = box.getString("searchStartDate");
	String searchEndDate = box.getString("searchEndDate");
	
	String subTitle = (searchCondition.equals("year")) ? "년도별 통계" : "기간별 통계";
	
	DataBox dbox = null;
	
	// ArrayList<DataBox> viewCountTotalList = (ArrayList<DataBox>)request.getAttribute("selectViewCountTotList");
	ArrayList<DataBox> viewCountList = (ArrayList<DataBox>)request.getAttribute("selectViewCountList");
	ArrayList<DataBox> viewCountTotalListInPeriod = (ArrayList<DataBox>)request.getAttribute("selectViewCountTotalListInPeriod");
	
	Calendar oCalendar = Calendar.getInstance( );
	
	int currentYear = oCalendar.get(Calendar.YEAR);
%>

<html>
<head>
<title>열린강좌 통계  Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<%
    if ( searchCondition.equals("year") || searchCondition.equals("period") ) {
%>
            <table class="table_out" cellspacing="1" cellpadding="5" border="1">
                <tr>
                    <td colspan="18" style="font-weight:bold; font-size:15pt; background-color:#abcdef;"><%= subTitle %></td>
                </tr>
                <tr>
                    <td class="table_title" width="3%"><b>seq</b></td>
                    <td class="table_title" width="8%"><b>구분1</b></td>
                    <td class="table_title" width="6%"><b>구분2</b></td>
                    <td class="table_title"><b>과정명</b></td>
                    <td class="table_title" width="4%"><b>제작년도</b></td>
                    <td class="table_title" width="4%"><b>1월</b></td>
                    <td class="table_title" width="4%"><b>2월</b></td>
                    <td class="table_title" width="4%"><b>3월</b></td>
                    <td class="table_title" width="4%"><b>4월</b></td>
                    <td class="table_title" width="4%"><b>5월</b></td>
                    <td class="table_title" width="4%"><b>6월</b></td>
                    <td class="table_title" width="4%"><b>7월</b></td>
                    <td class="table_title" width="4%"><b>8월</b></td>
                    <td class="table_title" width="4%"><b>9월</b></td>
                    <td class="table_title" width="4%"><b>10월</b></td>
                    <td class="table_title" width="4%"><b>11월</b></td>
                    <td class="table_title" width="4%"><b>12월</b></td>
                    <td class="table_title" width="4%"><b>합계</b></td>
                </tr>
<%
        if ( viewCountTotalListInPeriod != null && viewCountTotalListInPeriod.size() > 0 ) {
            dbox = null;
            String styleTotal = "";
            for(int i = 0; i < viewCountTotalListInPeriod.size(); i++) {
                dbox = (DataBox)viewCountTotalListInPeriod.get(i);
                styleTotal = dbox.getString("d_seq").equals("99999") ? "style=\"font-weight:bold;\"" : "";
%>
                <tr>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_seq").equals("99999") ? "*" : dbox.getString("d_seq") %></td>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_areanm") %></td>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_lecture_clsnm") %></td>
                    <td class="table_02_1" align="left;" <%= styleTotal %>><%= dbox.getString("d_lecnm") %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= dbox.getString("d_creatyear") %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jan")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_feb")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_mar")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_apr")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_may")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jun")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jul")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_aug")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_sep")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_oct")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_nov")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_dec")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_tot_cnt")) %></td>
                </tr>
<%
            }
        } else {
%>
                <tr>
                    <td class="table_01" colspan="18">조회된 자료가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

<%
    }

    if( searchCondition.equals("daily") ) {
%>
            <table class="table_out" cellspacing="1" cellpadding="5"  border="1">
                <tr>
                    <td colspan="7"style="font-weight:bold; font-size:15pt; background-color:#abcdef;">일별 조회 건수</td>
                </tr>
                <tr>
                    <td class="table_title" width="5%" height="25"><b>seq</b></td>
                    <td class="table_title" width="10%"><b>구분1</b></td>
                    <td class="table_title" width="10%"><b>구분2</b></td>
                    <td class="table_title"><b>과정명</b></td>
                    <td class="table_title" width="10%"><b>제작년도</b></td>
                    <td class="table_title" width="10%"><b>날짜</b></td>
                    <td class="table_title" width="10%"><b>조회수</b></td>
                </tr>
<%
        String c_seq = "";
        String c_lecnm = "";
        String c_viewdate = "";
        String c_cnt = "";

        if ( viewCountList != null && viewCountList.size() > 0 ) {
            for(int i = 0; i < viewCountList.size(); i++) {

                DataBox cbox = (DataBox)viewCountList.get(i);
                c_seq = cbox.getString("d_seq");
                c_lecnm = cbox.getString("d_lecnm");
                c_viewdate = cbox.getString("d_viewdate");
                c_cnt = cbox.getString("d_cnt");
%>
                <tr>
                    <td class="table_01"><%=c_seq.equals("0") ? "*" : c_seq%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%= cbox.getString("d_areanm") %></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%= cbox.getString("d_lecture_clsnm") %></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=c_seq.equals("0") ? "합계" : c_lecnm%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%= cbox.getString("d_creatyear") %></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=FormatDate.getFormatDate(c_viewdate,"yyyy/MM/dd")%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=c_cnt%></td>
                </tr>
<%
            }
        } else {
%>
                <tr>
                    <td class="table_01" colspan="7">조회된 자료가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

        </td>
    </tr>
</table>
<%
    }
%>

</body>
</html>