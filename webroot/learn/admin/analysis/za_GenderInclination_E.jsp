<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%
	response.setHeader("Content-Disposition", "attachment; filename=CarrerListEXE1.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_code     = "";
    String v_codenm   = "";

    int v_educnt               = 0; //교육인원
    int v_comlcnt              = 0; //수료인원
    int v_ncomlcnt             = 0; //미수료 인원
    int v_educnt_total         = 0; //교육인원 총합
    int v_comlcnt_total        = 0; //수료인원 총합
    int v_ncomlcnt_total       = 0; //미수료인원 총합
    double v_suryopercen       = 0; //수료율
    double v_suryopercen_total = 0; //수료율 총합
    double re = 0;
    double re1 = 0;
    int i = 0;

	ArrayList list = list = (ArrayList)request.getAttribute("GenderList");
	DataBox dbox = null;

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body>
    <table cellspacing="1" cellpadding="5" border="1">
	  <tr>
		<td width="25%">성별</td>
		<td width="17%">교육인원</td>
		<td width="17%">수료인원</td>
		<td width="17%">미수료인원</td>
		<td width="14%">수료율</td>
	  </tr>

<%
        String [] v_value = {"1","2"};
        String [] v_text = {"남", "여"};

        for(int j = 0; j < v_value.length; j++)  {
            v_codenm   = v_text[j];
            v_comlcnt  = 0;
            v_ncomlcnt = 0;
            v_educnt   = 0;

            for (i = 0; i<list.size(); i++ ) {
                dbox = (DataBox)list.get(i);
                v_code     = dbox.getString("d_sex");

                if (v_value[j].equals(v_code)) {
                    v_comlcnt  = dbox.getInt   ("d_comlcnt");
                    v_ncomlcnt = dbox.getInt   ("d_ncomlcnt");
                    v_educnt   = v_comlcnt + v_ncomlcnt;
                }
            }
            v_suryopercen = ((v_educnt - v_ncomlcnt) /(double)v_educnt) * 100;
            re = Math.round(v_suryopercen * 1000) / 1000;

            v_educnt_total      += v_educnt;        //교육인원 총합
            v_comlcnt_total     += v_comlcnt;       //수료인원 총합
            v_ncomlcnt_total    += v_ncomlcnt;      //미수료인원 총합
            v_suryopercen_total += v_suryopercen;

            double v_suryo_total_percen = ((v_educnt_total - v_ncomlcnt_total) / (double)v_educnt_total) * 100;
            re1 = Math.round(v_suryo_total_percen * 1000) / 1000;
%>
          <tr >
            <td ><%=v_text[j]%></td>
            <td ><%=v_educnt %></td>
            <td ><%=v_comlcnt%></td>
            <td ><%=v_ncomlcnt%></td>
            <td ><%=re%>%</td>
          </tr>
<%
        }
%>
          <tr> 
            <td  width="25%">계</td>
            <td  width="17%"><%=v_educnt_total%></td>
            <td  width="17%"><%=v_comlcnt_total%></td>
            <td  width="17%"><%=v_ncomlcnt_total%></td>
            <td  width="17%"><%=re1%>%</td>
          </tr>
        </table>
</body>
</html>
