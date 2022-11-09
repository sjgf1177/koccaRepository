<%
    //**********************************************************
//  1. 제      목: 종합 통계(교육인원)엑셀
//  2. 프로그램명: za_EduUsersStatistics_E.jsp
//  3. 개      요: 교육인원 통계 조회 엑셀
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 2022.11.04
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
    response.setHeader("Content-Disposition", "inline; filename=za_EduUsersStatistics_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");

    int v_total	= 0;
    int i = 0;
    String _gm1 = "";
    String _gm2 = "";
    String _gm3 = "";
    String _lvnm = "";

    ArrayList list = null;
    list = (ArrayList)request.getAttribute("selectEduUsersStatisticListExcel");
%>
<html>
<head>
    <title></title>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onload="aaa();">
<table cellspacing="1" cellpadding="5" class="table_out" border="1">
    <tr>
        <td colspan="16" class="table_top_line"></td>
    </tr>
    <tr style="text-align: center;">
        <td style="text-align: center;">No</td>
        <td style="text-align: center;">교육그룹</td>
        <td style="text-align: center;">과정명</td>
        <td style="text-align: center;">차수</td>
        <td style="text-align: center;">장르</td>
        <td style="text-align: center;">대분류</td>
        <td style="text-align: center;">소분류</td>
        <td style="text-align: center;">난이도</td>
        <td style="text-align: center;">아이디</td>
        <td style="text-align: center;">성명</td>
        <td style="text-align: center;">성별</td>
        <td style="text-align: center;">생년월일</td>
        <td style="text-align: center;">지역</td>
        <td style="text-align: center;">직업</td>
        <td style="text-align: center;">연령대</td>
        <td style="text-align: center;">신청일자</td>
        <td style="text-align: center;">학습여부</td>
        <td style="text-align: center;">수료여부</td>
        <td style="text-align: center;">설문참여여부</td>
        <td style="text-align: center;">설문일자</td>
        <td style="text-align: center;">답안</td>
        <td style="text-align: center;">평균</td>
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
        <td style="text-align: left;">[<%= dbox.getString("d_edu_type") %>] <%= dbox.getString("d_grcodenm") %></td>
        <td style="text-align: left;">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></td>
        <td style="text-align: left;">[<%= dbox.getString("d_year") %>] <%= dbox.getString("d_grseqnm") %></td>
        <td style="text-align: center;"><%= _gm1 %></td>
        <td style="text-align: center;"><%= _gm2 %></td>
        <td style="text-align: center;"><%= _gm3 %></td>
        <td style="text-align: center;"><%= _lvnm %></td>
        <td style="text-align: center;"><%= dbox.getString("d_user_id") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_user_nm") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_sex_nm") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_brthdy") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_region") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_job_nm") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_age2") %></td>
        <td style="text-align: center;" style="padding: 0 7px;"><%= dbox.getString("d_appdt") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_learn_yn") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_graduated_yn") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_suleach2_yn") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_suldt") %></td>
        <td style="text-align: left;"><%= dbox.getString("d_answers") %></td>
        <td style="text-align: center;"><%= dbox.getString("d_distcode1_avg") %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
