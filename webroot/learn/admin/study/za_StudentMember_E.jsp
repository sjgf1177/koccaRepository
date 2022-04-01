<%
//**********************************************************
//  1. 제      목: STUDENT MEMBER EXCEL
//  2. 프로그램명: za_StudentMember_E.jsp
//  3. 개      요: 학습진행자 명단조회 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_StudentMember_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

	RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list      = null;
    list = (ArrayList)request.getAttribute("StudentMemberExcel");

    String  v_process   = box.getString("p_process");
    int v_pageno        = box.getInt("p_pageno");

    String  v_grseq     =  "";
	String  v_grseqnm   =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_compnm    =  "";
    String  v_jikupnm   =  "";
    String  v_jikwinm   =  "";
    String  v_userid    =  "";
    String  v_name      =  "";
    String  v_email      =  "";
    String  v_handphone  =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_ismailing =  "";
    String  v_isnewcourse= "";
    String  v_tstep     =  "";
    String  v_mtest     =  "";
    String  v_ftest     =  "";
    String  v_htest     =  "";
    String  v_report    =  "";
    String  v_etc1      =  "";
    String  v_etc2      =  "";
    String  v_avtstep   =  "";
    String  v_avmtest   =  "";
    String  v_avftest   =  "";
    String  v_avhtest   =  "";
    String  v_avreport  =  "";
    String  v_avetc1    =  "";
    String  v_avetc2    =  "";
    String  v_score     =  "";
    String  v_isonoff   =  "";
    String  v_isonoff_value="";
    String  v_subjseqgr =  "";
	String 	v_membergubunnm = "";
	String  v_isbelongcourse ="";
    int v_subjcnt = 0;			// 전문가(코스)과정에 속한 과정수

    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
    
    String  v_resno		= "";
    String  v_post1     = "";
    String  v_post2     = "";
    String  v_addr      = "";
    String  v_addr2     = "";
    String  v_comp_post1= "";
    String  v_comp_post2= "";
    String  v_comp_addr1= "";
    String  v_comp_addr2= "";
    String  v_hometel   = "";
    String  v_comptel   = "";
    String  v_jikup     = "";
    String  v_degree    = "";

    
 
    %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</HEAD>

<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

        <table  cellspacing="0" cellpadding="0" class="table_out" >
          <tr>
            <td>
                    <table cellspacing="1" cellpadding="5" class="box_table_out" border="1">
                      <tr>
                        <td class="table_title">No</td>
                        <td class="table_title">교육차수</td>
                        <td class="table_title" colspan="2">과정</td>
                        <td class="table_title">회원분류</td>
                        <td class="table_title">ID</td>
                        <td class="table_title">성명</td>
                        
                        <td class="table_title">주민번호</td>
                        
                        <td class="table_title">EMAIL</td>
                        <td class="table_title">이동전화</td>
                        <td class="table_title">과정<br>차수</td>
                        <!--td class="table_title">구분</td-->
                        <td class="table_title">진도율</td>
                        <td class="table_title">중간평가</td>
                        <td class="table_title">최종평가</td>
                        <td class="table_title">형성평가</td>
                        <td class="table_title">리포트</td>
                        <td class="table_title">참여율</td>
                        <td class="table_title">기타</td>
                        <td class="table_title">진도율 기준점수</td>
                        <td class="table_title">중간평가 기준점수</td>
                        <td class="table_title">최종평가 기준점수</td>
                        <td class="table_title">형성평가 기준점수</td>
                        <td class="table_title">리포트 기준점수</td>
                        <td class="table_title">참여율 기준점수</td>
                        <td class="table_title">기타 기준점수</td>
                        <td class="table_title">총점</td>
                        
                        <td class="table_title">우편번호</td>
                        <td class="table_title">집전화번호</td>
                        <td class="table_title" colspan="2">집주소</td>
                        
                        <td class="table_title">우편번호</td>
                        <td class="table_title">회사전화번호</td>
                        <td class="table_title" colspan="2">회사주소</td>
                        <td class="table_title">메일수신여부</td>
                        
                      </tr>
<%
			DataBox dbox = null;
			String preCourse = "";			//이전출력과정 - 000000이면 일반과정이고 이외는 전문가과정
  			int l = 0;

	//내용이 있고 없음여부
	if( list.size() != 0 ){		// 검색된 내용이 있다면
	               v_total = list.size();

		for(i = 0; i < v_total; i++) {
				dbox = (DataBox)list.get(i);

				v_grseqnm       = dbox.getString("d_grseqnm");
				v_subj          = dbox.getString("d_subj");
				v_year          = dbox.getString("d_year");
				v_subjnm        = dbox.getString("d_subjnm");
				v_subjseq       = dbox.getString("d_subjseq");
				v_subjseqgr     = dbox.getString("d_subjseqgr");
				v_membergubunnm     = dbox.getString("d_membergubunnm");
				v_userid        = dbox.getString("d_userid");
				v_name          = dbox.getString("d_name");
				v_edustart      = dbox.getString("d_edustart");
				v_eduend        = dbox.getString("d_eduend");
				v_email         = dbox.getString("d_email");
				v_handphone         = dbox.getString("d_handphone");
				v_tstep         = dbox.getString("d_tstep");
				v_mtest         = dbox.getString("d_mtest");
				v_ftest         = dbox.getString("d_ftest");
				v_htest         = dbox.getString("d_htest");
				v_report        = dbox.getString("d_report");
				v_etc1          = dbox.getString("d_etc1");
				v_etc2          = dbox.getString("d_etc2");
				v_avtstep       = dbox.getString("d_avtstep");
				v_avmtest       = dbox.getString("d_avmtest");
				v_avftest       = dbox.getString("d_avftest");
				v_avhtest       = dbox.getString("d_avhtest");
				v_avreport      = dbox.getString("d_avreport");
				v_avetc1        = dbox.getString("d_avetc1");
				v_avetc2        = dbox.getString("d_avetc2");
				v_score         = dbox.getString("d_score");
				v_isonoff       = dbox.getString("d_isonoff");
				v_coursenm		= dbox.getString("d_coursenm");
				v_course		= dbox.getString("d_course");
				v_courseseq		= dbox.getString("d_courseseq");
				v_isbelongcourse	= dbox.getString("d_isbelongcourse");
				v_edustart   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
				v_eduend     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
				v_subjcnt	 = dbox.getInt("d_subjcnt");			// 전문가과정의 과목수

				//if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
				//else                      {     v_isonoff_value="집합";     }   
				
				v_resno			=  dbox.getString("d_resno");		 
				v_post1     	=  dbox.getString("d_post1");        
				v_post2     	=  dbox.getString("d_post2");        
				v_addr      	=  dbox.getString("d_addr");         
				v_addr2     	=  dbox.getString("d_addr2");        
				v_comp_post1	=  dbox.getString("d_comp_post1");   
				v_comp_post2	=  dbox.getString("d_comp_post2");   
				v_comp_addr1	=  dbox.getString("d_comp_addr1");   
				v_comp_addr2	=  dbox.getString("d_comp_addr2");   
				v_hometel   	=  dbox.getString("d_hometel");      
				v_comptel   	=  dbox.getString("d_comptel");      
				v_jikup     	=  dbox.getString("d_jikup");        
				v_jikupnm   	=  dbox.getString("d_jikupnm");      
				v_degree    	=  dbox.getString("d_degree");       
				v_ismailing   	=  dbox.getString("d_ismailing");
				
				if (v_resno.length() == 13) {
					v_resno = v_resno.substring(0,6) + "-" + v_resno.substring(6,13);
				}

%>
                 <tr>
<%
			//전문가과정이라면
			if( v_isbelongcourse.equals("Y")){

					if(l == v_subjcnt) preCourse = "";

				
					if( !preCourse.equals("000000") && !preCourse.equals(v_course) ) {	
						l = 1;
%>

                        <td class="table_01"><%=v_total-i%></td>
                        <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_grseqnm%></td>
                        <td class="table_02_2" rowspan="<%=v_subjcnt%>">						  
						  <font class="text_color04" align="center">
						  <CENTER><%=v_coursenm%><br><%=StringManager.cutZero(v_courseseq)%>차</CENTER></font></td>
                        <td class="table_02_2">						  
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_userid %></td>
                        <td class="table_02_1"><%= v_name %></td>
                                                                 
						<td class="table_02_1" style='mso-number-format:"\@";'><%= v_resno %></td>
                        
                        <td class="table_02_1"><%= v_email %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_handphone %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_tstep %></td>
                        <td class="table_02_1"><%= v_mtest %></td>
                        <td class="table_02_1"><%= v_ftest %></td>
                        <td class="table_02_1"><%= v_htest %></td>
                        <td class="table_02_1"><%= v_report %></td>
                        <td class="table_02_1"><%= v_etc1 %></td>
                        <td class="table_02_1"><%= v_etc2 %></td>
                        <td class="table_02_1"><%= v_avtstep %></td>
                        <td class="table_02_1"><%= v_avmtest %></td>
                        <td class="table_02_1"><%= v_avftest %></td>
                        <td class="table_02_1"><%= v_avhtest %></td>
                        <td class="table_02_1"><%= v_avreport %></td>
                        <td class="table_02_1"><%= v_avetc1 %></td>
                        <td class="table_02_1"><%= v_avetc2 %></td>
                        <td class="table_02_1"><%= v_score %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_post1 %>-<%= v_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_hometel %></td>
                        <td class="table_02_1"><%= v_addr %></td>
                        <td class="table_02_1"><%= v_addr2 %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comp_post1 %>-<%= v_comp_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comptel %></td>
                        <td class="table_02_1"><%= v_comp_addr1 %></td>
                        <td class="table_02_1"><%= v_comp_addr2 %></td>
                        <td class="table_02_1"><%= v_ismailing %></td>
                        
                        

<%
						preCourse = v_course;
					}else{
						l++;
%>
	                    <td class="table_01"><%=v_total-i%></td>
                        <td class="table_02_2">						  
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1"><%= v_userid %></td>
                        <td class="table_02_1"><%= v_name %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_resno %></td>
                        
                        <td class="table_02_1"><%= v_email %></td>
                        <td class="table_02_1"><%= v_handphone %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_tstep %></td>
                        <td class="table_02_1"><%= v_mtest %></td>
                        <td class="table_02_1"><%= v_ftest %></td>
                        <td class="table_02_1"><%= v_htest %></td>
                        <td class="table_02_1"><%= v_report %></td>
                        <td class="table_02_1"><%= v_etc1 %></td>
                        <td class="table_02_1"><%= v_etc2 %></td>
                        <td class="table_02_1"><%= v_avtstep %></td>
                        <td class="table_02_1"><%= v_avmtest %></td>
                        <td class="table_02_1"><%= v_avftest %></td>
                        <td class="table_02_1"><%= v_avhtest %></td>
                        <td class="table_02_1"><%= v_avreport %></td>
                        <td class="table_02_1"><%= v_avetc1 %></td>
                        <td class="table_02_1"><%= v_avetc2 %></td>
                        <td class="table_02_1"><%= v_score %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_post1 %>-<%= v_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_hometel %></td>
                        <td class="table_02_1"><%= v_addr %></td>
                        <td class="table_02_1"><%= v_addr2 %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comp_post1 %>-<%= v_comp_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comptel %></td>
                        <td class="table_02_1"><%= v_comp_addr1 %></td>
                        <td class="table_02_1"><%= v_comp_addr2 %></td>
                        <td class="table_02_1"><%= v_ismailing %></td>

<%
					}


			}else{		//전문가과정이 아니면
%>
                        <td class="table_01"><%=v_total-i%></td>
                        <td class="table_02_1"><%=v_grseqnm%></td>
                        <td class="table_02_2"  colspan="2">						  
						  <font class="text_color04" align="center">
						  <CENTER><%=v_subjnm%></CENTER></font></td>
                        <td class="table_02_1"><%=v_membergubunnm%></td>
                        <td class="table_02_1"><%= v_userid %></td>
                        <td class="table_02_1"><%= v_name %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_resno %></td>
                        
                        <td class="table_02_1"><%= v_email %></td>
                        <td class="table_02_1"><%= v_handphone %></td>
                        <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
						<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                        <td class="table_02_1"><%= v_tstep %></td>
                        <td class="table_02_1"><%= v_mtest %></td>
                        <td class="table_02_1"><%= v_ftest %></td>
                        <td class="table_02_1"><%= v_htest %></td>
                        <td class="table_02_1"><%= v_report %></td>
                        <td class="table_02_1"><%= v_etc1 %></td>
                        <td class="table_02_1"><%= v_etc2 %></td>
                        <td class="table_02_1"><%= v_avtstep %></td>
                        <td class="table_02_1"><%= v_avmtest %></td>
                        <td class="table_02_1"><%= v_avftest %></td>
                        <td class="table_02_1"><%= v_avhtest %></td>
                        <td class="table_02_1"><%= v_avreport %></td>
                        <td class="table_02_1"><%= v_avetc1 %></td>
                        <td class="table_02_1"><%= v_avetc2 %></td>
                        <td class="table_02_1"><%= v_score %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_post1 %>-<%= v_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_hometel %></td>
                        <td class="table_02_1"><%= v_addr %></td>
                        <td class="table_02_1"><%= v_addr2 %></td>
                        
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comp_post1 %>-<%= v_comp_post2 %></td>
                        <td class="table_02_1" style='mso-number-format:"\@";'><%= v_comptel %></td>
                        <td class="table_02_1"><%= v_comp_addr1 %></td>
                        <td class="table_02_1"><%= v_comp_addr2 %></td>
                        <td class="table_02_1"><%= v_ismailing %></td>

<%
			}
%>
			</tr>

<%
		}//for

	}
%>
            </td>
          </tr>
        </table>
</body>
</html>

