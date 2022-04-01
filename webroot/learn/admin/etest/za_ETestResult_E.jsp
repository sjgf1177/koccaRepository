<%
/**
 * file name : za_ETestResult_L2.jsp
 * date      : 2003/09/07
 * programmer:
 * function  : 평가문제지 평가점수 조회
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<% 
	response.setHeader("Content-Disposition", "inline; filename=za_ETestResult_L2.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    
    String  ss_grcode    = box.getString("s_grcode");        //교육주관
    String  ss_gyear    = box.getString("s_gyear");       
	String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_etestsubj    = box.getString("s_etestsubj");       
    String p_action = box.getString("p_action");    
    if(p_action.equals("go")) ss_etestsubj = box.getString("s_etestsubj");

    String  ss_etestcode    = box.getString("s_etestcode");

	String v_flag = box.getString("p_flag");    
	if(v_flag.equals("asc")) v_flag = "desc";
	else v_flag = "asc";

    ArrayList  list = (ArrayList)request.getAttribute("ETestResultList");
	ETestResultData	data  = null;

	DataBox dbox0 = (DataBox)request.getAttribute("ETestMasterData");
%>
<html>
<head>
<title>ETest결과 Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
<%
    Vector  v_average = (Vector)request.getAttribute("ETestResultAverage");     

           String v_ing = "";
		   int v_update = 0;

		   if(!dbox0.getString("d_etesttext").equals("")){

		   long v_start = Long.parseLong(dbox0.getString("d_startdt"));
           long v_end = Long.parseLong(dbox0.getString("d_enddt"));

                 Date d_now = new Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());
				 String d_hour = String.valueOf(d_now.getHours());
				 String d_minute = String.valueOf(d_now.getMinutes());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
				 if (d_hour.length() == 1){
				        d_hour = "0" + d_hour; 				 
				 }
				 if (d_minute.length() == 1){
				        d_minute = "0" + d_minute; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day+d_hour+d_minute); 


		   if (v_start > v_now){
		            v_ing = "<font color='red'>테스트 진행 전입니다.</font>"; 
					v_update = 1;
		   } else if (v_now > v_end){
		            v_ing = "테스트 진행이 완료되었습니다.";
					v_update = 3;
		   } else if (v_start <= v_now || v_now < v_end){
		            v_ing = "테스트가 진행 중입니다"; 
					v_update = 2;
		   } 
		   }
%>
<%

	if(v_average.size() > 0){
%>
	  <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="10%" class="table_title">문제수</td>
          <td class="table_title">총점</td>
          <td class="table_title">응시자수</td>
          <td class="table_title">전체평균</td>
          <td class="table_title">최고점수</td>
          <td class="table_title">최저점수</td>
        </tr>
		<tr> 
          <td class="table_02_1"><%=(String)v_average.get(0)%></td>
          <td class="table_02_1">100점</td>
          <td class="table_02_1"><%=(String)v_average.get(1)%></td>
          <td class="table_02_1"><%=(String)v_average.get(2)%></td>
          <td class="table_02_1"><%=(String)v_average.get(3)%></td>
          <td class="table_02_1"><%=(String)v_average.get(4)%></td>
        </tr>
      </table>
	  <br>
      <br>
<%
    }	
%>

      <!----------------- 평가점수 조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form3" method="post" action="/servlet/controller.etest.ETestUserServlet">
        <input type="hidden" name="p_process"  value="">
        <input type="hidden" name="p_action"   value="">
  			<input type="hidden" name="p_etestsubj"     value="">
			  <input type="hidden" name="p_gyear"     value="">
			  <input type="hidden" name="p_etestcode"  value="">
			  <input type="hidden" name="p_userid"   value="">
  			  <input type="hidden" name="p_etesttext"   value="">
			  <input type="hidden" name="p_average"   value="">
			  <input type="hidden" name="p_personcnt"   value="">
			  <input type="hidden" name="p_examtypenm"  value="">
			  <input type="hidden" name="p_isadmin"  value="Y">
			</form>  
        <tr> 
          <td colspan="13" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="6%" class="table_title">NO</td>
          <td class="table_title">테스트명</td>
          <td class="table_title">테스트연도</td>
          <td class="table_title">소속</td>
          <td class="table_title">직위</td>
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">상태</td>
          <td class="table_title">시작일시</td>
          <td class="table_title">완료일시</td>
          <td class="table_title">점수</td>
        </tr>
<%  for (int i=0; i<list.size(); i++) {
        data  = (ETestResultData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=data.getSubjnm()%></td>
          <td class="table_02_1"><%=data.getYear()%></td>
          <td class="table_02_1"><%=data.getAsgnnm()%></td>
          <td class="table_02_1"><%=data.getJikwinm()%></td>
          <td class="table_02_1"><%=data.getCono()%></td>
          <td class="table_02_1"><%=data.getName()%></td>
          <td class="table_02_1"><%=data.getStatus()%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getStarted(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEnded(),"yyyy/MM/dd HH:mm")%></td>
          <td class="table_02_1"><%=data.getScore()%></td>
        </tr>
<%  } %>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
