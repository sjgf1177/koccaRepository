<%
//**********************************************************
//  1. 제      목: 참여도관리 엑셀
//  2. 프로그램명: za_TutorValuation_E.jsp
//  3. 개      요: 참여도관리 조회 엑셀
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.11
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_Activity_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    ArrayList list      = null;
    list = (ArrayList)request.getAttribute("activitylistExcel");
    
    String  v_process   = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

	String  v_course         = "";
    String  v_cyear          = "";
    String  v_courseseq      = "";
    String  v_coursenm       = "";
    String  v_grcodenm       = "";
    String  v_subj	         = "";
	String  v_year		     = "";
	String  v_subjnm         = "";
    String	v_subjseq        = "";
    String	v_subjseqgr      = "";
    String	v_userid         = "";
    String	v_name           = "";
    String	v_membergubunnm  = "";
    String	v_isonoff        = "";
    String	v_isbelongcourse = "";
    int     v_logincnt	     = 0;
    int		v_qnacnt	     = 0;
    int     v_toroncnt	     = 0;
    int		v_subjcnt	     = 0;
    double  v_etc1	         = 0d;
    double  v_etc2	         = 0d;
    double  v_wetc1	         = 0d;
    double  v_wetc2	         = 0d;
    double  v_avetc1	     = 0d;
    double  v_avetc2	     = 0d;

    int     v_totalpage		 = 0;
    int     v_rowcount		 = 1;
    int     v_totalrowcount	 = 0;
    int     v_rowspan		 = 0;
    int     v_total			 = 0;
    int		v_dispnum		 = 0;
    int     i				 = 0;

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
            <td class="table_title" rowspan="2">No</td>
            <td class="table_title" rowspan="2">교육그룹</td>
            <td class="table_title" rowspan="2">과정명</td>            
            <td class="table_title" rowspan="2">과정차수</td>
			<td class="table_title" rowspan="2">성명</td>
			<td class="table_title" colspan="3">평가항목</td>
			<td class="table_title" colspan="3">참여도</td>
			<td class="table_title" colspan="3">기타</td>
          </tr>
          <tr> 
            <td class="table_title">접속횟수</td>
            <td class="table_title">과정질문방</td>
            <td class="table_title">토론방</td>
            <td class="table_title">비중</td>
            <td class="table_title">점수</td>
            <td class="table_title">평가</td>
            <td class="table_title">비중</td>
            <td class="table_title">점수</td>
            <td class="table_title">평가</td>
          </tr>
<%
	//내용이 있고 없음여부
	if( list.size() != 0 ){		// 검색된 내용이 있다면
       v_total = list.size();

		for( i = 0 ; i < list.size() ; i++ ){
			DataBox dbox = (DataBox)list.get(i);		
			v_course         = dbox.getString("d_course");
			v_cyear          = dbox.getString("d_cyear");
			v_courseseq      = dbox.getString("d_courseseq");
			v_coursenm       = dbox.getString("d_coursenm");
			v_grcodenm       = dbox.getString("d_grcodenm");
			v_subj           = dbox.getString("d_subj");
			v_year           = dbox.getString("d_year");
			v_subjnm         = dbox.getString("d_subjnm");
			v_subjseq        = dbox.getString("d_subjseq");
			v_subjseqgr      = dbox.getString("d_subjseqgr");
			v_userid         = dbox.getString("d_userid");
			v_name           = dbox.getString("d_name");
			v_membergubunnm  = dbox.getString("d_membergubunnm");
			v_isonoff        = dbox.getString("d_isonoff");
			v_isbelongcourse = dbox.getString("d_isbelongcourse");
			v_logincnt       = dbox.getInt("d_logincnt");
			v_qnacnt         = dbox.getInt("d_qnacnt");
			v_toroncnt       = dbox.getInt("d_toroncnt");
			v_subjcnt        = dbox.getInt("d_subjcnt");
			v_etc1           = dbox.getDouble("d_etc1");
			v_etc2           = dbox.getDouble("d_etc2");
			v_wetc1          = dbox.getDouble("d_wetc1");
			v_wetc2          = dbox.getDouble("d_wetc2");
			v_avetc1         = dbox.getDouble("d_avetc1");
			v_avetc2         = dbox.getDouble("d_avetc2");

%>

          <tr> 
            <td class="table_01"><%= v_total-i %></td>
            <td class="table_02_2"><%= v_grcodenm %></td>    
            <td class="table_02_2"><%= v_subjnm %></td>            
            <td class="table_02_1"><%= StringManager.cutZero(v_subjseqgr) %></td>
            <td class="table_02_1"><%= v_name %></td>
            <td class="table_02_1"><%= v_logincnt %></td>
            <td class="table_02_1"><%= v_qnacnt %></td>
            <td class="table_02_1"><%= v_toroncnt %></td>
            <td class="table_02_1"><%= v_wetc1 %></td>   
            <td class="table_02_1"><%= v_etc1 %></td>   
            <td class="table_02_1"><%= v_avetc1 %></td>  
            <td class="table_02_1"><%= v_wetc2 %></td>   
            <td class="table_02_1"><%= v_etc2 %></td>   
            <td class="table_02_1"><%= v_avetc2 %></td>           
          </tr>

<%
		}

	}
%>
          </table>
	    </td>
	  </tr>
	</table>
</body>
</html>