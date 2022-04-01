<%
//**********************************************************
//  1. 제      목: LEARNING TIME SELECT LIST
//  2. 프로그램명: za_LearningTimeSelect_E.jsp
//  3. 개      요: 학습시간조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//***********************************************************
%>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_LearningTimeSelect_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");    

	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	ArrayList list = null;
    list = (ArrayList)request.getAttribute("LearningTimeSelectExcel");

    String  v_process   = box.getString("p_process");
    int     v_pageno    = box.getInt("p_pageno");
	String  v_userid    = "";
    String  v_name      = "";
    String  v_membergubunnm  = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_firstedu  = "";
    String  v_totaltime = "";
    String  v_totalminute="";
    String  v_ldatestart= "";
    String  v_ldateend  = "";
    String v_isnewcourse= "";
    String  v_isonoff   =  "";
    String  v_isonoff_value="";
	String v_isbelongcourse = "";		//전문가과정 여부
	int		v_subjcnt	= 0;		//전문가과정일경우 속한 과정수

    int     v_edutotmin = 0;
    int     v_avg_time  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;

    //페이징
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_totalrowcount	=  0;
    int v_pagesize = 10;

    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

%>
<HTML>
<HEAD>
<TITLE></TITLE>

</HEAD>

<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

      <!----------------- 학습시간조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out" border="1">
          <tr>
            <td colspan="11" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" class="table_title">NO</td>
            <td class="table_title" colspan="2">과정명</td>
            <td class="table_title">ID</td>
            <td class="table_title">성명</td>
            <td class="table_title">회원구분</td>
            <td class="table_title">과정<br>차수</td>
            <!--td class="table_title">구분</td-->
            <td class="table_title">최초학습일</td>
            <td class="table_title">최근학습시작일</td>
            <td class="table_title">최근학습종료일</td>
            <td class="table_title">총학습시간</td>
          </tr>
<%

			String preCourse = "";			//이전출력과정 - 000000이면 일반과정이고 이외는 전문가과정
			int l = 0;

	//내용이 있고 없음여부
	if( list.size() != 0 ){		// 검색된 내용이 있다면

			v_total = list.size();
			for(i = 0; i < v_total; i++) {

				DataBox dbox = (DataBox)list.get(i);
                
				v_course         = dbox.getString("d_course");
				v_cyear          = dbox.getString("d_cyear");
				v_courseseq      = dbox.getString("d_courseseq");
				v_coursenm       = dbox.getString("d_coursenm");
				v_subj           = dbox.getString("d_subj");
				v_year           = dbox.getString("d_year");
				v_subjnm         = dbox.getString("d_subjnm");
				v_subjseq        = dbox.getString("d_subjseq");
				v_subjseqgr      = dbox.getString("d_subjseqgr");
				v_userid         = dbox.getString("d_userid");
				v_name           = dbox.getString("d_name");
				v_membergubunnm  = dbox.getString("d_membergubunnm");
				v_firstedu       = dbox.getString("d_first_edu");
				v_totaltime      = dbox.getString("d_total_time");
				v_totalminute    = dbox.getString("d_total_minute");
				v_ldatestart     = dbox.getString("d_ldate_start");
				v_ldateend       = dbox.getString("d_ldate_end");
				v_isnewcourse    = dbox.getString("d_isnewcourse");
				v_rowspan        = dbox.getInt("d_rowspan");
				v_isonoff        = dbox.getString("d_isonoff");
				v_isbelongcourse = dbox.getString("d_isbelongcourse");
				v_subjcnt	     = dbox.getInt("d_subjcnt");

                v_totalpage     = dbox.getInt("d_totalpage");
                v_rowcount      = dbox.getInt("d_rowcount");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
                v_dispnum       = dbox.getInt("d_dispnum");

				//StudyStatusData data  = (StudyStatusData)list.get(i);
				//v_course        = data.getCourse();
				//v_cyear         = data.getCyear();
				//v_courseseq     = data.getCourseseq();
				//v_coursenm      = data.getCoursenm();
				//v_subj          = data.getSubj();
				//v_year          = data.getYear();
				//v_subjnm        = data.getSubjnm();
				//v_subjseq       = data.getSubjseq();
				//v_subjseqgr     = data.getSubjseqgr();
				//v_userid        = data.getUserid();
				//v_name          = data.getName();
				//v_membergubunnm = data.getMembergubunnm();                   
				//v_firstedu      = data.getFirstedu();
				//v_totaltime     = data.getTotaltime();
				//v_totalminute   = data.getTotalminute();
				//v_ldatestart    = data.getLdatestart();
				//v_ldateend      = data.getLdateend();
				//v_isnewcourse   = data.getIsnewcourse();
				//v_rowspan       = data.getRowspan();
				//v_isonoff       = data.getIsonoff();
				//v_isbelongcourse = data.getIsbelongcourse();
				//v_subjcnt	= data.getSubjcnt();

				//if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
				//else                      {     v_isonoff_value="집합";     }
				v_firstedu      = FormatDate.getFormatDate(v_firstedu,"yyyy/MM/dd-HH:mm:ss");
				v_ldatestart    = FormatDate.getFormatDate(v_ldatestart,"yyyy/MM/dd-HH:mm:ss");
				v_ldateend      = FormatDate.getFormatDate(v_ldateend,"yyyy/MM/dd-HH:mm:ss");
				if(v_firstedu.length() > 0)  { v_firstedu = v_firstedu.substring(0,16);      }
				if(v_ldatestart.length() > 0){ v_ldatestart=v_ldatestart.substring(0,16);    }
				if(v_ldateend.length() > 0)  { v_ldateend = v_ldateend.substring(0,16);      }
				if(!(v_totaltime.length() > 0) ) {      v_totaltime = "0";     }
				if( ! (v_totalminute.length() >0)){     v_totalminute = "0";   }

				v_edutotmin = Integer.parseInt(v_totaltime)*60 + Integer.parseInt(v_totalminute);

%>
                 <tr>
<%
			//전문가과정이라면
			if( v_isbelongcourse.equals("Y")){

					if(l == v_subjcnt) preCourse = "";
				
					if( !preCourse.equals("000000") && !preCourse.equals(v_course) ) {	
						l = 1;
%>

					<td class="table_01"><%= v_totalrowcount - i %></td>
                    <td class="table_02_1" rowspan="<%=v_subjcnt%>"> <%=v_coursenm%><br><%=StringManager.cutZero(v_courseseq)%>차</td>
					<td class="table_02_2"><font class="text_color04" align="center"><CENTER><%=v_subjnm%></CENTER></font></td>
					<td class="table_02_1"><%= v_userid %></td>
					<td class="table_02_1"><%= v_name %></td>
					<td class="table_02_1"><%=v_membergubunnm%></td>
					<td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
					<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
					<td class="table_02_1"><%if(v_firstedu.equals("")){out.print("-");}else{out.print(v_firstedu);}%></td>
					<td class="table_02_1"><%if(v_ldatestart.equals("")){out.print("-");}else{out.print(v_ldatestart);}%></td>
					<td class="table_02_1"><%if(v_ldateend.equals("")){out.print("-");}else{out.print(v_ldateend);}%></td>
					<td class="table_02_1"><%=v_edutotmin%> min</td>


<%
						preCourse = v_course;
					}else{
						l++;
%>

					<td class="table_01"><%= v_totalrowcount - i %></td>
					<td class="table_02_2"><font class="text_color04" align="center"><CENTER><%=v_subjnm%></CENTER></font></td>
					<td class="table_02_1"><%= v_userid %></td>
					<td class="table_02_1"><%= v_name %></td>
					<td class="table_02_1"><%=v_membergubunnm%></td>
					<td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
					<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
					<td class="table_02_1"><%if(v_firstedu.equals("")){out.print("-");}else{out.print(v_firstedu);}%></td>
					<td class="table_02_1"><%if(v_ldatestart.equals("")){out.print("-");}else{out.print(v_ldatestart);}%></td>
					<td class="table_02_1"><%if(v_ldateend.equals("")){out.print("-");}else{out.print(v_ldateend);}%></td>
					<td class="table_02_1"><%=v_edutotmin%> min</td>

<%
					}


			}else{		//전문가과정이 아니면
%>

					<td class="table_01"><%= v_totalrowcount - i %></td>
					<td class="table_02_2" colspan="2"><font class="text_color04" align="center"><CENTER><%=v_subjnm%></CENTER></font></td>
					<td class="table_02_1"><%= v_userid %></td>
					<td class="table_02_1"><%= v_name %></td>
					<td class="table_02_1"><%=v_membergubunnm%></td>
					<td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
					<!--td class="table_02_1"><%//=v_isonoff_value%></td-->
					<td class="table_02_1"><%if(v_firstedu.equals("")){out.print("-");}else{out.print(v_firstedu);}%></td>
					<td class="table_02_1"><%if(v_ldatestart.equals("")){out.print("-");}else{out.print(v_ldatestart);}%></td>
					<td class="table_02_1"><%if(v_ldateend.equals("")){out.print("-");}else{out.print(v_ldateend);}%></td>
					<td class="table_02_1"><%=v_edutotmin%> min</td>
<%
			}
%>
			</tr>

<%
		}//for

	}else if( list.size() == 0){		//  내용이 없다면
%>

             <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="12">등록된 내용이 없습니다</td>
             </tr>
<%
	}


%>

        </table>
      <!----------------- 학습시간조회 끝 ----------------->
</body>
</HTML>

