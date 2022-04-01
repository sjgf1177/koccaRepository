<%
/**
 * file name : za_ProposeCancelMember_E.jsp
 * date      :
 * programmer:
 * function  : 수강생 관리 리스트 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_CoursePorgress_E.xls");
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_grcode    = "";
    String  v_grcodenm  = "";
    String  v_gyear     = "";
    String  v_grseq     = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_propstart = "";
    String  v_propend   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    String  v_isgoyong  = "";

    String  v_propose         = "";
    String  v_chkfirst        = "";
    String  v_chkfinal        = "";
    String  v_student         = "";
    String  v_completion      = "";
	
	String  v_tmp_subj	= "";
	String  v_iscourseYn = "";

	int		v_subjcnt	= 0;

    int     v_cnt_propose     = 0;
    int     v_cnt_chkfirst    = 0;
    int     v_cnt_chkfinal    = 0;
    int     v_cnt_student     = 0;
    int     v_cnt_completion  = 0;

    String  v_subjtypenm  = "";
    String  v_isnewcourse = "";
    int     v_rowspan     =  0;
    int     i = 0;
    int     j = 0;

    int     l = 0;

    ArrayList list      = null;

    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getStringDefault("s_grseq","ALL");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수

    String  ss_action    = box.getString("s_action");

    list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>신청명단 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
		<tr> 
            <td height="50" rowspan="2"><b>NO</b></td>
            <!--td rowspan="2"><b>구분</b></td-->            
            <td rowspan="2" colspan="2"><b>과정명</b></td>
            <td rowspan="2"><b>과정차수</b></td>
            <td rowspan="2"><b>고용여부</b></td>
            <td rowspan="2"><b>수강 신청기간</b></td>
            <td rowspan="2"><b>교육기간</b></td>
            <td colspan="4" height="25"><b>인원</b></td>
          </tr>
          <tr> 
            <td height="25"><b>신청</b></td>
            <td ><b>승인</b></td>
            <td ><b>교육</b></td>
            <td ><b>수료</b></td>
          </tr>
          <%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

				if( dbox != null)
				{
					v_grcode		= dbox.getString("d_grcode"); 
					v_grcodenm		= dbox.getString("d_grcodenm"); 
					v_gyear			= dbox.getString("d_gyear"); 
					v_grseq			= dbox.getString("d_grseq"); 
					v_course		= dbox.getString("d_course"); 
					v_cyear			= dbox.getString("d_cyear"); 
					v_courseseq		= dbox.getString("d_courseseq"); 
					v_coursenm		= dbox.getString("d_coursenm"); 
					v_subj			= dbox.getString("d_subj"); 
					v_year			= dbox.getString("d_year"); 
					v_subjseq		= dbox.getString("d_subjseq"); 
					v_subjseqgr		= dbox.getString("d_subjseqgr"); 
					v_subjnm		= dbox.getString("d_subjnm"); 
					v_isonoff		= dbox.getString("d_isonoff"); 
					v_propstart		= StringManager.chkNull(dbox.getString("d_propstart") );
					v_propend		= StringManager.chkNull(dbox.getString("d_propend"));
					v_edustart		= dbox.getString("d_edustart"); 
					v_eduend		= dbox.getString("d_eduend"); 
					v_isgoyong		= dbox.getString("d_isgoyong"); 
					v_iscourseYn	= dbox.getString("d_isbelongcourse"); 

					v_propose        = StringManager.chkNull(dbox.getString("d_propose"));
					v_chkfirst       = StringManager.chkNull(dbox.getString("d_chkfirst") );
					v_chkfinal       = StringManager.chkNull(dbox.getString("d_chkfinal") );
					v_student        = StringManager.chkNull(dbox.getString("d_student") );
					v_completion     = StringManager.chkNull(dbox.getString("d_completion") );
					v_cnt_propose    = dbox.getInt("d_cnt_propose"); 
					v_cnt_chkfirst   = dbox.getInt("d_cnt_chkfirst"); 
					v_cnt_chkfinal   = dbox.getInt("d_cnt_chkfinal"); 
					v_cnt_student    = dbox.getInt("d_cnt_student"); 
					v_cnt_completion = dbox.getInt("d_cnt_completion"); 
					v_subjcnt		 = dbox.getInt("d_subjcnt"); 

					if(v_isonoff.equals("ON"))
					{
						v_subjtypenm = "사이버";
					}
					else 
					{
						v_subjtypenm = "집합";
					}
				}

               %>
          <tr> 
            <td ><%= i+1 %></td>
            <!--td ><%//=v_subjtypenm%></td-->            
<% if(v_iscourseYn.equals("Y"))
	{
		if(j == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000") )
		{ j = 1;
			%>				
            <td rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
			<td ><%=v_subjnm %></a></td>
	<%	
		}else{ 
		
		j++;
		%>
			<td ><%=v_subjnm %></a></td>
<%		}
		v_tmp_subj = v_course;
		
	} else { %>

            <td colspan="2"><%=v_subjnm%></a></td>
<% } %>
            
			<td ><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td ><%=v_isgoyong %></td>
            <td ><%= FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_propend,"yyyy/MM/dd") %></td>
            <td ><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
            <td ><%= v_cnt_propose %></td>
            <td ><%= v_cnt_chkfinal %></td>
            <td ><%= v_cnt_student %></td>
            <td ><%= v_cnt_completion %></td>
            
          </tr>
          <%
          }
          if(v_process.equals("select") && i == 0){ %>
          <% if (list.size()==0) { %>
                <tr><td colspan="12">진행중인 차수 정보가 없습니다.</td></tr>
<% } %>
          <%  } %>
        </table>
      </td>
  </tr>
</table>
</body>
</html>