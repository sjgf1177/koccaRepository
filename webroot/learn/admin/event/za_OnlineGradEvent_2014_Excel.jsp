<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.ListSet" %>
<%
    response.setHeader("Content-Disposition", "inline; filename=2014_propose_event_winner_list.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>회원DB검색</title>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table border="1" cellspacing="0" cellpadding="0">
    <tr>
        <td class="table_title"><b>순번</b></td>
        <td class="table_title"><b>회원ID</b></td>
        <td class="table_title"><b>회원성명</b></td>
        <td class="table_title"><b>핸드폰</b></td>
        <td class="table_title"><b>E-mail</b></td>
        <td class="table_title"><b>상품</b></td>
    </tr>

<%
    ArrayList<DataBox> list = this.selectWinnerPrizeList();
    ArrayList<DataBox> listWithMasking = this.selectWinnerPrizeListWithMasking();
    DataBox dbox = null;

    for (int i = 0; i < list.size(); i++) {
        dbox = (DataBox)list.get(i);
%>
    <tr>
        <td class="table_02_1"><%= (i + 1 ) %></td>
        <td class="table_02_1"><%= dbox.getString("d_userid") %></td>
        <td class="table_02_1"><%= dbox.getString("d_usernm") %></td>
        <td class="table_02_1"><%= dbox.getString("d_handphone") %></td>
        <td class="table_02_1"><%= dbox.getString("d_email") %></td>
        <td class="table_02_1">&nbsp;<%=dbox.getString("d_win_prize_nm")%> </td>
    </tr>
<%
    }
%>
</table>

<br/><br/>
<table border="1" cellspacing="0" cellpadding="0">
    <tr>
        <td class="table_title"><b>순번</b></td>
        <td class="table_title"><b>회원ID</b></td>
        <td class="table_title"><b>회원성명</b></td>
        <td class="table_title"><b>핸드폰</b></td>
        <td class="table_title"><b>E-mail</b></td>
        <td class="table_title"><b>상품</b></td>
    </tr>
<%
    for (int i = 0; i < listWithMasking.size(); i++) {
        dbox = (DataBox)listWithMasking.get(i);
%>
    <tr>
        <td class="table_02_1"><%= (i + 1 ) %></td>
        <td class="table_02_1"><%= dbox.getString("d_userid") %></td>
        <td class="table_02_1"><%= dbox.getString("d_usernm") %></td>
        <td class="table_02_1"><%= dbox.getString("d_handphone") %></td>
        <td class="table_02_1"><%= dbox.getString("d_email") %></td>
        <td class="table_02_1">&nbsp;<%=dbox.getString("d_win_prize_nm")%> </td>
    </tr>
<%
    }

%>
</table>
</body>
</html>

<%!
    private ArrayList<DataBox> selectWinnerPrizeList() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  USERID                                          \n");
            sql.append("    ,   USERNM                                          \n");
            sql.append("    ,   HANDPHONE                                       \n");
            sql.append("    ,   EMAIL                                           \n");
            sql.append("    ,   CASE WHEN WIN_LEVEL = 1 THEN 'iPhone 6 16G'     \n");
            sql.append("             WHEN WIN_LEVEL = 2 THEN '신세계 상품권 10만원'     \n");
            sql.append("             WHEN WIN_LEVEL = 3 THEN '2015년 프랭클린 플래너'   \n");
            sql.append("             WHEN WIN_LEVEL = 4 THEN '바나나맛 우유'    \n");
            sql.append("        END AS WIN_PRIZE_NM                             \n");
            sql.append("  FROM  TZ_EVENT_APPLICANT                              \n");
            sql.append(" WHERE  WIN_YN = 'Y'                                    \n");
            sql.append("   AND  SEQ = 2                                         \n");
            sql.append(" ORDER  BY WIN_LEVEL, USERNM                            \n");

            ls = connMgr.executeQuery(sql.toString());

            while ( ls.next() ) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            ls.close();
            ls = null;

        } catch(Exception e) {
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }
            if ( connMgr != null ) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }

        return list;
    }


    private ArrayList<DataBox> selectWinnerPrizeListWithMasking() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  SUBSTR(USERID, 1, 4) || LPAD('*', LENGTH(USERID) - 4, '*') AS USERID                                    \n");
            sql.append("    ,   SUBSTR(USERNM, 1, 1) || LPAD('*',LENGTH(USERNM)-2,'*') || SUBSTR(USERNM, LENGTH(USERNM), 1) AS USERNM   \n");
            sql.append("    ,   USERNM AS SORTNM    \n");
            sql.append("    ,   HANDPHONE                                       \n");
            sql.append("    ,   EMAIL                                           \n");
            sql.append("    ,   CASE WHEN WIN_LEVEL = 1 THEN 'iPhone 6 16G'     \n");
            sql.append("             WHEN WIN_LEVEL = 2 THEN '신세계 상품권 10만원'     \n");
            sql.append("             WHEN WIN_LEVEL = 3 THEN '2015년 프랭클린 플래너'   \n");
            sql.append("             WHEN WIN_LEVEL = 4 THEN '바나나맛 우유'    \n");
            sql.append("        END AS WIN_PRIZE_NM                             \n");
            sql.append("  FROM  TZ_EVENT_APPLICANT                              \n");
            sql.append(" WHERE  WIN_YN = 'Y'                                    \n");
            sql.append("   AND  SEQ = 2                                         \n");
            sql.append(" ORDER  BY WIN_LEVEL, SORTNM \n");

            ls = connMgr.executeQuery(sql.toString());

            while ( ls.next() ) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            ls.close();
            ls = null;

        } catch(Exception e) {
        } finally {
            if ( ls != null ) {
                ls.close();
                ls = null;
            }
            if ( connMgr != null ) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }

        return list;
    }
%>