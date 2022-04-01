<%
/**
 * file name : za_Finish_E.jsp
 * date      : 2005/10
 * programmer:
 * function  : 수료처리 리스트 및 처리
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=FinishList.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
	  String  v_action       = box.getString("p_action");

   	ArrayList list = null;
   	list = (ArrayList)request.getAttribute("CompleteListExcel");
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

	int is_complete		= 0;    // 수료여부
	String v_subjnm		= "";
	String v_isclosed	= "";
	String v_approval	= "";
	String v_approvalstatus	= "";
	String v_isonoff	= "";
	String v_subj		= "";
	String v_year		= "";
	String v_subjseq	= "";
	String v_stolddate	= ""; // 수료처리일            	
	String v_isoutsrc	= ""; // 외주업체            	
	String v_subjseqgr	= "";
	String v_eduend		= "";
	String v_date		= "";
	String v_course		= "";
	String v_coursenm	= "";
	String v_tmp_subj	= "";
	String v_iscourseYn	= "";
	String v_finalString = "";
	int		v_subjcnt	= 0;
	int		v_usercnt	= 0;
	int		l			= 0;
	int    v_student  = 0;
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td>
      <table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
        <tr>
          <td align="center" bgcolor="#FFFFFF">

            <!----------------- 집합과정 개인별 수료처리 시작 ----------------->
			<table border="1" cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td rowspan="2" width="5%" class="table_title">NO</td>
          <!--td rowspan="2" class="table_title" width="40">구분</td-->
		  <td rowspan="2" class="table_title" colspan="2">과정</td>
          <td rowspan="2" class="table_title">차수</td>
          <td rowspan="2" class="table_title" width="90">교육기간</td>
          <td colspan="3" class="table_title">인원</td>
          <td rowspan="2" class="table_title">수료처리</td>
          <td rowspan="2" class="table_title">전문가과정 수료처리</td>
          <!--td rowspan="2" class="table_title">외주업체<br>결과등록</td>
          <td rowspan="2" class="table_title">외주업체<br>결과승인</td-->
        </tr>
        <tr> 
          <td class="table_title">교육</td>
          <td class="table_title">수료</td>
          <td class="table_title">미수료</td>
        </tr>
			  <% 
			
			for (int i=0; i<list.size(); i++) {
            	DataBox dbox = (DataBox)list.get(i); 
            	is_complete = 0;    // 수료여부
            	v_subjnm	= dbox.getString("subjnm");
            	v_isclosed	= dbox.getString("isclosed");
            	v_approval	= dbox.getString("d_approvalstatus");
            	v_student	= dbox.getInt("d_studentcnt");            	
            	v_isonoff	= dbox.getString("d_isonoff");
            	v_subj		= dbox.getString("d_subj");
            	v_year		= dbox.getString("d_year");
            	v_subjseq	= dbox.getString("d_subjseq");
            	v_stolddate = dbox.getString("d_stolddate"); // 수료처리일            	
            	v_isoutsrc	= dbox.getString("d_isoutsourcing"); // 외주업체
            	v_course	= dbox.getString("d_course");
				v_coursenm	= dbox.getString("d_coursenm");
				v_iscourseYn= dbox.getString("d_isbelongcourse");
				v_subjseqgr = dbox.getString("d_subjseqgr");
				v_subjcnt	= dbox.getInt("d_subjcnt");

            	if(!v_stolddate.equals(""))  v_stolddate = FormatDate.getRelativeDate(FormatDate.getFormatDate(v_stolddate,"yyyyMMdd"),1); // 수료처리일+1 


                // 수료여부          
                if (!v_eduend.equals(""))
                    if (Integer.parseInt(v_date) > Integer.parseInt(v_eduend)) is_complete=1;
			  %>
        <tr> 
          <td class="table_01" ><%=(i+1)%></td>
          <!--td class="table_02_1"><%//=dbox.getString("isonoffnm")%></td-->
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			v_usercnt++;
			%>
          <td class="table_02_2" rowspan="<%=v_subjcnt%>"><%=v_coursenm%></td>
          <td class="table_02_2"><%=v_subjnm%></td>
<%	
			v_tmp_subj = v_course;
		}else{ 
			l++;
		%>	
          <td class="table_02_2"><%=v_subjnm%></td>
<%		}
	} else { 
			v_usercnt++;%>
          <td class="table_02_2" colspan="2"><%=v_subjnm%></td>
<% } %>
          <td class="table_02_1"><%=StringManager.cutZero(dbox.getString("subjseqgr"))%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("edustart"),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(dbox.getString("eduend"),"yyyy/MM/dd")%></td>
          <td class="table_02_1"><%=v_student%></td>		<!--교육-->
          <td class="table_02_1"><%=dbox.getInt("d_stoldycnt")%></td>			<!--수료-->
          <td class="table_02_1"><%=dbox.getInt("d_stoldncnt")%></td>			<!--미수료-->
          <td class="table_03_1" bgcolor="#FFFFCC"><!-- 1수료처리 -->
          <% if (is_complete==1) { %>
              <% if (v_isclosed.equals("Y")){ %><!--수료-->
                  <% if ( !v_stolddate.equals("") && Integer.parseInt(v_stolddate) >= Integer.parseInt(v_date) ){ %><!--수료처리일+1 까지 수료처리취소 버튼 확인-->
                    <% if(v_isoutsrc.equals("N")){%>
         			    <% if(v_isonoff.equals("ON")){%>
                        수료취소
                        <%}else{%>
                        수료취소
                        <%}%>
                    <%}else{%>
                      수료취소
                    <%}%>
                  <% }else{%>      
                    수료완료
                  <% }%>
                    
              <% }else{ %><!--미수료-->
                 <% if(v_isoutsrc.equals("N")){%>
                    <%if(v_isonoff.equals("ON")){%>
                      수료처리
                    <%}else{%>
                      수료처리
                    <%}%>
                  <%}else{%>
                      수료처리
                  <%} %>
              <% } %>
              
          <% }else{ %>
              <% if (v_isclosed.equals("N")){ %>
                <%if(v_isoutsrc.equals("N")){%>
                 점수재계산
                <%}else{%>
                  -
                <%}%>
              <% }else{%>
            	  수료완료
              <% }%>
          <% } %>
          </td> 
      	  <!--<td class="table_02_1"><%if (v_approval.equals("Y")) out.print("결재완료"); else if (v_approval.equals("B")) out.print("상신중"); else out.print("");%></td>--> <!-- 결재 상태 -->
      	  <td class="table_02_1"><%=dbox.getString("isoutsourcing")%></td> <!-- 위탁교육여부 -->
      	  <!--td class="table_02_1"><%=dbox.getString("iscpresult")%></td--> <!-- 외주업체결과등록 -->
      	  <!--td class="table_02_1"><%=dbox.getString("iscpflag")%></td--> <!-- 외주업체결과승인 -->
        </tr>

<%  }  %>
			</table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
