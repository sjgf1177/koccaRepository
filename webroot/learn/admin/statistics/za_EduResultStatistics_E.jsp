<%
    //**********************************************************
//  1. 제      목: 종합 통계(교육성과)엑셀
//  2. 프로그램명: za_EduResultStatistics_E.jsp
//  3. 개      요: 교육성과 통계 조회 엑셀
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 2022.11.07
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
    response.setContentType("application/vnd.ms-text");
    response.setHeader("Content-Disposition", "inline; filename=za_EduResultStatistics_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");

    int v_total	= 0;
    int i = 0;
    String _gm1 = "";
    String _gm2 = "";
    String _gm3 = "";
    String _lvnm = "";

    ArrayList list = null;
    list = (ArrayList)request.getAttribute("selectEduResultStatisticListExcel");
%>
<html>
<head>
    <title></title>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="aaa();">
<table cellspacing="1" cellpadding="5" class="table_out" border="1">
    <tr>
        <td colspan="17" class="table_top_line"></td>
    </tr>
    <tr style="text-align: center;">
        <td style="text-align: center">No</td>
        <td style="text-align: center">교육구분</td>
        <td style="text-align: center">교육그룹</td>
        <td style="text-align: center">과정명</td>
        <td style="text-align: center">차수</td>
        <td style="text-align: center">교육시작일</td>
        <td style="text-align: center">교육종료일</td>
        <td style="text-align: center">장르</td>
        <td style="text-align: center">대분류</td>
        <td style="text-align: center">소분류</td>
        <td style="text-align: center">난이도</td>
        <td style="text-align: center">신청자 수</td>
        <td style="text-align: center">학습자 수</td>
        <td style="text-align: center">수료자 수</td>
        <td style="text-align: center">설문자 수</td>
        <td style="text-align: center">수료율</td>
        <td style="text-align: center">평균평점</td>
    </tr>
    <%
        DataBox dbox = null;

        if( list.size() != 0 ){		// 검색된 내용이 있다면
            v_total = list.size();

            for(i = 0; i < v_total; i++) {
                dbox = (DataBox)list.get(i);

                if(dbox.getString("d_upperclass").equals("X01")){
                    _gm1 = "폐지";
                    _gm2 = "";
                    _gm3 = "";
                    _lvnm = "";
                }else{
                    _gm1 = dbox.getString("d_gnm1");
                    _gm2 = dbox.getString("d_gnm2");
                    _gm3 = dbox.getString("d_gnm3");
                    _lvnm = dbox.getString("d_lvnm");
                }
    %>
    <tr>
        <td style="text-align: center;"><%= list.size() - i %></td>
        <td style="text-align: center;"><%= dbox.getString("d_edu_type") %></td>
        <td style="text-align: left;"><%= dbox.getString("d_grcodenm") %></td>
        <td style="text-align: left">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></td>
        <td style="text-align: left;">[<%= dbox.getString("d_year") %>] <%= dbox.getString("d_grseqnm") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_sdt") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_edt") %></td>
        <td style="text-align: center;"><%= _gm1 %></td>
        <td style="text-align: center;"><%= _gm2 %></td>
        <td style="text-align: center;"><%= _gm3 %></td>
        <td style="text-align: center;"><%= _lvnm %></td>
        <td style="text-align: right;"><%= dbox.getInt("d_ap_cnt") %></td>
        <td style="text-align: right;"><%= dbox.getInt("d_st_cnt") %></td>
        <td style="text-align: right;"><%= dbox.getInt("d_gr_cnt") %></td>
        <td style="text-align: right;"><%= dbox.getInt("d_su_cnt") %></td>
        <td style="text-align: right;"><%= String.format("%.1f", (double)dbox.getInt("d_gr_cnt") / (double)dbox.getInt("d_st_cnt") * 100) %></td>
        <td style="text-align: right;"><%= String.format("%.1f", (double)dbox.getInt("d_dis_sum") / (double)dbox.getInt("d_su_cnt") / 20) %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
