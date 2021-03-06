<%
//**********************************************************
//  1. 제      목: STUDENT MEMBER COUNT LIST
//  2. 프로그램명: za_SelectedSubjAll_E.jsp
//  3. 개      요: 입과인원조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_SelectedSubjAll_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	
	ArrayList list = null;
    list = (ArrayList)request.getAttribute("StudentMemberCountExcel");

	String  v_grseq         =  "";
    String  v_grseqnm       =  "";
    String  v_course        =  "";
    String  v_cyear         =  "";
    String  v_courseseq     =  "";
    String  v_coursenm      =  "";
    String  v_subj          =  "";
    String  v_year          =  "";
    String  v_subjnm        =  "";
    String  v_subjseq       =  "";
    String  v_subjseqgr     =  "";
    String  v_propstart     =  "";
    String  v_propend       =  "";
    String  v_edustart      =  "";
    String  v_eduend        =  "";
    String  v_isonoff       =  "";
    String  v_isonoff_value = "";
    String  v_isnewcourse   = "";
    String  v_studentcnt    = "";
    String  v_totalcnt      = "";

    int     v_studentlimit =  0;
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
    int     v_procnt    =  0;
    int     v_proycnt    =  0;
    int     v_stucnt    =  0;
    int     v_comcnt    =  0;
    int     v_cancnt    =  0;
    int     v_totalstucnt   =  0;
    int     v_totalcomcnt   =  0;
    int     v_totalcancnt   =  0;
    int     v_totalprocnt   =  0;
    int     v_totalproycnt  =  0;
    int     v_totalstulimit =  0;
    int     v_personcnt     =  0;

    int		v_today = 0;
    int		v_dday = 0;                     //D데이 저장 변수
    int		v_edustart_value = 0;
    int		v_eduend_value = 0;
    int		v_count = 0;

%>
<HTML>
<HEAD>
<TITLE></TITLE>

</HEAD>

<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<!----------------- 입과명단조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out" border="1">
          <tr>
            <td colspan="13" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title">No</td>
            <td class="table_title">교육차수</td>
            <td class="table_title">과정</td>
            <td class="table_title" width="4%">과정<br>차수</td>
            <!--td class="table_title">구분</td-->
            <td class="table_title">신청기간</td>
            <td class="table_title">교육기간</td>
            <td class="table_title">정원</td>
            <td class="table_title">총신청<BR>인원</td>
            <td class="table_title">수강취소/<BR>반려인원</td>
            <td class="table_title">학습예정<BR>인원</td>
            <td class="table_title">학습진행<BR>인원</td>
            <td class="table_title">학습완료<BR>인원</td>
          </tr>
        <%
                v_total = list.size();

if(list.size() != 0 ){

                for(i = 0; i < v_total; i++) {
                    StudentStatusData data  = (StudentStatusData)list.get(i);
                    v_grseq         = data.getGrseq();
                    v_grseqnm       = data.getGrseqnm();
                    v_course        = data.getCourse();
                    v_cyear         = data.getCyear();
                    v_courseseq     = data.getCourseseq();
                    v_coursenm      = data.getCoursenm();
                    v_subj          = data.getSubj();
                    v_year          = data.getYear();
                    v_subjnm        = data.getSubjnm();
                    v_subjseq       = data.getSubjseq();
                    v_subjseqgr     = data.getSubjseqgr();
                    v_propstart     = data.getPropstart();
                    v_propend       = data.getPropend();
                    v_edustart      = data.getEdustart();
                    v_eduend        = data.getEduend();
                    v_studentlimit  = data.getStudentlimit();
                    v_isnewcourse   = data.getIsnewcourse();
                    v_rowspan       = data.getRowspan();
                    v_totalpage     = data.getTotalPageCount();
                    v_rowcount      = data.getRowCount();
                    v_isonoff       = data.getIsonoff();


                    v_totalstulimit += v_studentlimit;

                    //if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
                    //else                      {     v_isonoff_value="집합";     }

                    v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));
                    if(!v_edustart.equals("")&&v_edustart.length() == 10){v_edustart_value = Integer.parseInt(v_edustart);}
                    if(!v_eduend.equals("")&&v_eduend.length() == 10){v_eduend_value = Integer.parseInt(v_eduend);}

                    v_procnt        = data.getProcnt();
                    v_cancnt        = data.getCancnt();

                    if(v_edustart_value>0&&v_edustart_value>=v_today){          //학습예정과정을 나타내기위한조건
                        v_proycnt       = data.getProycnt();
                        v_stucnt        = 0;
                        v_comcnt        = 0;
                    }
                    if(v_edustart_value>0&&v_edustart_value<v_today&&v_eduend_value>=v_today){  //학습진행중인과정을 나타내기위한 조건
                        v_proycnt       = 0;
                        v_stucnt        = data.getStucnt();
                        v_comcnt        = 0;
                    }
                    if(v_eduend_value>0&&v_eduend_value<v_today){                                //학습완료과정을 나타내기위한조건
                        v_proycnt       = 0;
                        v_stucnt        = 0;
                        v_comcnt        = data.getComcnt();
                    }

                    v_personcnt     = v_procnt;                        //수강신청인원(신청자+취소자)

                    v_totalprocnt=v_totalprocnt+v_personcnt;           //총수강신청인원
                    v_totalcancnt=v_totalcancnt+v_cancnt;              //총수강취소인원
                    v_totalproycnt=v_totalproycnt+v_proycnt;           //학습예정인원
			        			v_totalstucnt=v_totalstucnt+v_stucnt;              //학습진행인원
			        			v_totalcomcnt=v_totalcomcnt+v_comcnt;              //학습완료인원


                    v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");
                    v_propend       = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");
                    v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
                    

                    if(v_studentlimit == 0) {
                    	v_studentcnt = "-";
                    } else {
                    	v_studentcnt = String.valueOf(v_studentlimit);
                    }
                    
                    if(v_totalstulimit == 0) {
                    	v_totalcnt = "-";
                    } else {
                    	v_totalcnt = String.valueOf(v_totalstulimit);
                    }
        %>
        <tr>
            <td class="table_01"><%= data.getDispnum() %></td>
            <td class="table_02_1"><%=v_grseqnm%></td>
            <td class="table_02_2" ><%=v_subjnm%></td>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <!--td class="table_02_1"><%=v_isonoff_value%></td-->
            <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
            <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
            <td class="table_02_1"><%= v_studentcnt%></td>
            <td class="table_02_1"><%= v_personcnt %></td>
            <td class="table_02_1"><%= v_cancnt %></td>
            <td class="table_02_1"><%= v_proycnt %></td>
            <td class="table_02_1"><%= v_stucnt %></td>
            <td class="table_02_1"><%= v_comcnt %></td>
         </tr>
         <%
       }

         %>
         <tr>
            <td class="table_02_1" colspan="6">계</td>
            <td class="table_02_1"><%= v_totalcnt %></td>
            <td class="table_02_1"><%= v_totalprocnt %></td>
            <td class="table_02_1"><%= v_totalcancnt %></td>
            <td class="table_02_1"><%= v_totalproycnt %></td>
            <td class="table_02_1"><%= v_totalstucnt %></td>
            <td class="table_02_1"><%=v_totalcomcnt %></td>
         </tr>
		 </table>
<%
  }else{		// 내용이 없다면

%>

              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="13">등록된 내용이 없습니다</td>
              </tr>

	    </table> 
 <% } %>
      <!----------------- 입과명단 조회 끝 ----------------->

</body>
</HTML>

