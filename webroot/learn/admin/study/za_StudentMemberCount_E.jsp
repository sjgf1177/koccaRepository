<%
//**********************************************************
//  1. 제      목: STUDENT MEMBER COUNT EXCEL
//  2. 프로그램명: za_StudentMemberCount_E.jsp
//  3. 개      요: 입과인원조회 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김영만 2003. 9. 1
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
 
<%	response.setHeader("Content-Disposition", "inline; filename=StudentMemberCount.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_grseq     =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_propstart =  "";
    String  v_propend   =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    int     v_studentlimit =  0;
    String  v_isnewcourse= "";
    int     v_totalpage =  0;
    int     v_rowcount  =  0;    
    int     v_rowspan   =  0;                    
    int     v_total     =  0;   
    int     i           =  0;
    int     v_stucnt      =  0;
    int     v_comcnt      =  0;
    int     v_cancnt      =  0;
	int     v_totalstucnt      =  0;
    int     v_totalcomcnt      =  0;
    int     v_totalcancnt      =  0;


    ArrayList list      = null;
    //DEFINED class&variable END

    list = (ArrayList)request.getAttribute("StudentMemberCountExcel");
%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<head>
<title>입과인원 Excel보기</title>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- 입과인원조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" border="1">
          <tr> 
            <td align="center">No</b></td>
            <!--td align="center">교육<br>차수</b></td-->
            <!--td align="center">과정/코스</td-->
            <td align="center">과정</b></td>
            <td align="center">과정<br>차수</b></td>
            <td class="table_title">신청기간</td>
            <td class="table_title">교육기간</td>
            <td class="table_title">정원</td>
            <td class="table_title">학습진행자인원</td>
            <td class="table_title">학습완료자인원</td>
            <td class="table_title">수강취소자인원</td>
          </tr>
        <%                          
            for(i = 0; i < list.size(); i++) {                                    
                    StudentStatusData data  = (StudentStatusData)list.get(i);
                    v_grseq         = data.getGrseq();       
                    v_course        = data.getCourse();      
                    v_cyear         = data.getCyear();       
                    v_courseseq     = data.getCourseseq();   
                    v_coursenm      = data.getCoursenm();    
                    v_subj          = data.getSubj();        
                    v_year          = data.getYear();        
                    v_subjnm        = data.getSubjnm();      
                    v_subjseq       = data.getSubjseq();     
                    v_propstart     = data.getPropstart();    
                    v_propend       = data.getPropend();    
                    v_edustart      = data.getEdustart();    
                    v_eduend        = data.getEduend();      
                    v_studentlimit  = data.getStudentlimit();  
                    v_stucnt        = data.getStucnt();
                    v_comcnt        = data.getComcnt();
                    v_cancnt        = data.getCancnt();
                    v_isnewcourse   = data.getIsnewcourse(); 
                    v_rowspan       = data.getRowspan();     
                    v_totalpage     = data.getTotalPageCount();
                    v_rowcount      = data.getRowCount();                    
                    v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");
                    v_propend       = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");
                    v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");     
       %>
        <tr> 
            <td align="center"><%= list.size() - i %></td>
            <!--td align="center"><%= v_grseq %>차수</td-->
            <%  if(v_course.equals("000000")){   %> <!-- 과정인 경우 -->
                <td align="center">
                <%=v_subjnm%>
                </td>
            <% }else if(v_isnewcourse.equals("Y")){ %><!-- 코스이면서 이전 코스와 일치하지 않는경우 -->
                 <!--td rowspan="<%=v_rowspan%>" align="center">
                 [코스] <%=v_coursenm%><%=v_courseseq%>
                 </td-->
             <% } %>                            
            <%  if(!v_course.equals("000000")){   %><!-- 코스인 경우 -->
                <!--td align="center">
                <%=v_subjnm%>
                </td-->
            <% } %>
            <td class="table_02_1">&nbsp;<%= v_subjseq%></td>            
            <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
            <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
            <td class="table_02_1"><%= v_studentlimit %></td>
            <td class="table_02_1"><%= v_stucnt %></td>
            <td class="table_02_1"><%= v_comcnt %></td>
            <td class="table_02_1"><%= v_cancnt %></td>
        </tr>
         <% 
			v_totalstucnt=v_totalstucnt+v_stucnt;
            v_totalcomcnt=v_totalcomcnt+v_comcnt;
            v_totalcancnt=v_totalcancnt+v_cancnt;
			}
		%>
         <tr>
            <td class="table_02_1" colspan="8">계</td>   
            <td class="table_02_1"><%= v_totalstucnt %></td>   
            <td class="table_02_1"><%= v_totalcomcnt %></td>   
            <td class="table_02_1"><%= v_totalcancnt %></td>   
         </tr>
        </table>
      <!----------------- 입과인원 조회 끝 ----------------->
     <br>
    </td>
  </tr> 
</table>
</body>
</html>
