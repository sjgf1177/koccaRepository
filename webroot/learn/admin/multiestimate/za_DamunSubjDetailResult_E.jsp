<%
/**
 * file name : za_DamunSubjDetailResult_E.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 다면평가결과 Excel상세보기
 */
%>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%@ page errorPage = "/learn/library/error.jsp" %>
<% 
	response.setHeader("Content-Disposition", "inline; filename=za_DamunSubjResult.xls"); 
	response.setHeader("Content-Description", "JSP Generated Data"); 
%> 
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

	String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
	String  ss_subjseq    = box.getString("s_subjseq");

	String  ss_mailgubun       = box.getString("s_mailgubun");

    ArrayList list = (ArrayList)request.getAttribute("DamunDetailResultExcelPage");System.out.println("ArrayList" + list);
    DataBox dbox0 = (DataBox)request.getAttribute("DamunPaperData");

    String  v_action    = box.getString("p_action");

 /*          String v_ing = "";
		   int v_update = 0;
		   int v_gubun = 0;

		   String v_sulstart1 = "";
           String v_sulend1 = "";
           v_sulstart1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunstart"),"yyyy-MM-dd");
	       v_sulend1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunend"),"yyyy-MM-dd");

		   String v_sulstart2 = "";
           String v_sulend2 = "";
           v_sulstart2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunstart"),"yyyy-MM-dd");
	       v_sulend2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunend"),"yyyy-MM-dd");

		   if(dbox0.getInt("d_damunpapernum") > 0){
          
		   long v_fstart = Long.parseLong(dbox0.getString("d_fdamunstart"));
           long v_fend = Long.parseLong(dbox0.getString("d_fdamunend"));
              
            if (dbox0.getString("d_d_gubun").equals("1")){
                  v_gubun = 1; 

                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_fend){
		            v_ing = "다면평가 진행이 완료되었습니다.";
					v_update = 3;
		         } else if (v_fstart <= v_now && v_now < v_fend){
		            v_ing = "다면평가가 진행 중입니다"; 
					v_update = 2;
		         }
	         } else if (dbox0.getString("d_d_gubun").equals("2")){
			     v_gubun = 2;
			     long v_sstart = Long.parseLong(dbox0.getString("d_sdamunstart"));
                 long v_send = Long.parseLong(dbox0.getString("d_sdamunend"));		 

                 java.util.Date d_now = new java.util.Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_send){
		            v_ing = "사후 다면평가 진행이 완료되었습니다.";
					v_update = 5;
		         } else if (v_fstart <= v_now && v_now < v_fend){
		            v_ing = "사전 다면평가가 진행 중입니다"; 
					v_update = 2;
		         } else if (v_fend <= v_now || v_now < v_sstart){
		            v_ing = "사후 다면평가 진행 전입니다"; 
					v_update = 3;
		         } else if (v_sstart <= v_now || v_now < v_send){
		            v_ing = "사후 다면평가가 진행 중입니다"; 
					v_update = 4;
		         }
			 }			 
		   }*/


    String  v_ggubun        = box.getStringDefault("p_gubun",  DamunSubjBean.DEFAULT_SUBJ);

    String v_title_img = ""; 
    if (v_ggubun.equals("COMMON"))     v_title_img = "3";
    else if (v_ggubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_ggubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
%>
<html>
<head>
<title>다면평가결과 Excel상세보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.multiestimate.DamunSubjMailServlet">

<table width="1000" border="1" cellspacing="1" cellpadding="0" height="663">
        <tr>
          <td class="table_title" rowspan="2">대상자사번</td>
          <td class="table_title" rowspan="2">대상자성명</td>
		  <td class="table_title" rowspan="2">사업부</td>
          <td class="table_title" rowspan="2">소속</td>
          <td class="table_title" rowspan="2">관계</td>
          <td class="table_title" rowspan="2">관찰자사번</td>
          <td class="table_title" rowspan="2">관찰자성명</td>
          <td class="table_title" rowspan="2">관찰자직위</td><!-- 사후평가 -->
		  <td class="table_title" colspan="<%=dbox0.getInt("d_totcnt")%>">사전평가</td>
          <td class="table_title" colspan="<%=dbox0.getInt("d_totcnt")%>">사후평가</td>
		</tr>
		<tr>

<%  
        for (int k=0; k<dbox0.getInt("d_totcnt"); k++) {	%>
			<td class="table_title">문제<%=k+1%></td>
<%    } %>
<%  
        for (int k=0; k<dbox0.getInt("d_totcnt"); k++) {	%>
			<td class="table_title">문제<%=k+1%></td>
<%    } %>
        </tr>
<%
        for (int i=0; i<list.size(); i++) {
            DataBox dbox2  = (DataBox)list.get(i);  
            
			String v_relation = "";
			if (dbox2.getString("d_relation").equals("0")){
			   v_relation = "본인";
			} else if (dbox2.getString("d_relation").equals("1")){
			   v_relation = "상사";
			} else if (dbox2.getString("d_relation").equals("2")){
			   v_relation = "동료";
			} else if (dbox2.getString("d_relation").equals("3")){
			   v_relation = "부하";
			}			
%>
        <tr>
		  <td class="table_02_1">&nbsp;<%=dbox2.getString("d_subjuserid")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_subjusernm")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_divinam")%></td>
		  <td class="table_02_1"><%=dbox2.getString("d_subjasgmnm")%></td>
          <td class="table_02_1"><%=v_relation%></td>
          <td class="table_02_1">&nbsp;<%=dbox2.getString("d_obuserid")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_jikwinm")%></td>
<%    
		String fpoint = dbox2.getString("d_fselpoint");//System.out.println("fpoint" + fpoint);
		String spoint = dbox2.getString("d_sselpoint");//System.out.println("spoint" + spoint);
		
		StringTokenizer f_st = new StringTokenizer(fpoint, ",");
		while(f_st.hasMoreElements()) {                            
			String f_token = f_st.nextToken();
			if(f_token.equals("")) break;
%>
			<td class="table_02_1"><%=f_token%></td>
<%    } 
		StringTokenizer s_st = new StringTokenizer(spoint, ",");
		while(s_st.hasMoreElements()) {                            
			String s_token = s_st.nextToken();
			if(s_token.equals("")) break;
%>
			<td class="table_02_1"><%=s_token%></td>
<%    } %>
        </tr>
<%  
	 }
%>
        </table>
      <br>
    </td>
  </tr>
</table>

</form>
</body>
</html>
