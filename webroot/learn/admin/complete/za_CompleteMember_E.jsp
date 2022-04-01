<%
//**********************************************************
//  1. 제      목: COMPLETE MEMBER LIST
//  2. 프로그램명: za_CompleteMember_E.jsp
//  3. 개      요: 수료명단조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 26
//  7. 수      정: 
//**********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_CompleteMember_E.xls");
	  response.setHeader("Content-Description", "JSP Generated Data");
	  
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  ss_grcode      = box.getString("s_grcode");           //교육그룹
    String  ss_gyear       = box.getString("s_gyear");            //년도
    String  ss_grseq       = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm     = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass  = box.getString("s_upperclass");       //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");      //과정중분류
    String  ss_lowerclass  = box.getString("s_lowerclass");       //과정소분류

    String  ss_uclass      = box.getStringDefault("s_uclass","ALL");        //과정분류
            
    
    String  ss_subjcourse  = box.getString("s_subjcourse");       //과정&코스
    
    String  ss_subjseq     = box.getString("s_subjseq");          //과정 차수
    
    String  ss_edustart    = box.getString("s_start");            //교육시작일
    String  ss_eduend      = box.getString("s_end");              //교육종료일
			 			 
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

	String  v_grseq     =  "";
    String  v_course    =  "";
    String  v_cyear     =  "";
    String  v_courseseq =  "";
    String  v_coursenm  =  "";
    String  v_subj      =  "";
    String  v_year      =  "";
    String  v_subjnm    =  "";
    String  v_subjseq   =  "";
    String  v_subjseqgr =  "";
    String  v_isonoff   =  "";
    String  v_compnm    =  "";
    String  v_companynm =  "";    
    String  v_jikwinm   =  "";
    String  v_jikupnm   =  "";
    String  v_userid    =  "";
    String  v_cono      =  "";
    String  v_name      =  "";
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_isgraduated= "";
    String  v_email     =  "";
    String  v_ismailing =  "";
    String  v_place     =  "";
    String  v_isnewcourse= "";
    String  v_isonoff_value="";
	String	v_membergubun = "";

	String v_tmp_subj	= "";
	String v_iscourseYn	= "";

    int     v_tstep     =  0;
    int     v_avtstep   =  0;
    int     v_mtest     =  0;
    int     v_ftest     =  0;
    int     v_htest     =  0;
    int     v_report    =  0;
    int     v_act       =  0;
    int     v_etc1      =  0;
    int     v_etc2      =  0;
    int     v_score     =  0;
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     v_subjcnt	=  0;
    int     i           =  0;
	int		l			= 0;
	int		v_usercnt	= 0;

	ArrayList list      = null;

	list = (ArrayList)request.getAttribute("CompleteMemberExcel");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
</HEAD>
<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<table width="1000" border="1" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>과정별 교육이력 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td rowspan="2"><b>NO</b></td>
            <td rowspan="2" colspan="2"><b>과정</b></td>
            <td rowspan="2"><b>회원분류</b></td>
            <td rowspan="2"><b>ID</b></td>
            <td rowspan="2"><b>성명</b></td>
            <td rowspan="2"><b>차수</b></td>
            <!--td rowspan="2"><b>구분</b></td-->
            <td rowspan="2"><b>교육기간</b></td> 
            <td colspan="7"><b>점 수</b></td>
            <td rowspan="2"><b>취득<br>점수</b></td>
            <td rowspan="2"><b>수료<br>여부</b></td>
          </tr>
          <tr> 
            <td><b>진도율<br>(점수)</b></td>
            <td><b>중간<br>평가</b></td>
            <td><b>최종<br>평가</b></td>
            <td><b>형성<br>평가</b></td>
            <td><b>리포트</b></td>
            <td><b>참여도</b></td>
            <td><b>기타</b></td>
          </tr>
<%
		v_total = list.size();
		for(i = 0; i < v_total; i++) {                                    
			DataBox dbox = (DataBox)list.get(i);

			v_grseq         = dbox.getString("d_grseq");    
			v_course        = dbox.getString("d_course");
			v_cyear         = dbox.getString("d_cyear");
			v_courseseq     = dbox.getString("d_courseseq");
			v_coursenm      = dbox.getString("d_coursenm");
			v_subj          = dbox.getString("d_subj");
			v_year          = dbox.getString("d_year");
			v_subjnm        = dbox.getString("d_subjnm");
			v_subjseq       = dbox.getString("d_subjseq");
			v_subjseqgr     = dbox.getString("d_subjseqgr");
			v_isonoff       = dbox.getString("d_isonoff");
			v_compnm        = dbox.getString("d_compnm"); 
			v_companynm     = dbox.getString("d_companynm");                 
			v_jikwinm       = dbox.getString("d_jikwinm");
			v_jikupnm       = dbox.getString("d_jikupnm");
			v_userid        = dbox.getString("d_userid");
			v_cono          = dbox.getString("d_cono");
			v_name          = dbox.getString("d_name");
			v_edustart      = dbox.getString("d_edustart");
			v_eduend        = dbox.getString("d_eduend");
			v_isgraduated   = dbox.getString("d_isgraduated");
			v_email         = dbox.getString("d_email");
			v_ismailing     = dbox.getString("d_ismailing");
			v_place         = dbox.getString("d_place");
			v_isnewcourse   = dbox.getString("d_isnewcourse");
			v_membergubun	= dbox.getString("d_membergubun");
			v_iscourseYn	= dbox.getString("d_isbelongcourse");
			
			v_tstep         = dbox.getInt("d_tstep");
			v_avtstep       = dbox.getInt("d_avtstep");
			v_mtest         = dbox.getInt("d_mtest");
			v_ftest         = dbox.getInt("d_ftest");
			v_htest         = dbox.getInt("d_htest");
			v_report        = dbox.getInt("d_report");
			v_act           = dbox.getInt("d_act");
			v_etc1          = dbox.getInt("d_etc1");
			v_score         = dbox.getInt("d_score");
			v_subjcnt       = dbox.getInt("d_subjcnt");
			v_totalpage     = dbox.getInt("d_totalpage");
			v_rowcount      = dbox.getInt("d_rowcount");

			v_edustart		= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
			v_eduend		= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");

			//if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
			//else                      {     v_isonoff_value="집합";     }
			if(v_place == null) v_place = "";

			if(v_membergubun.equals("C"))
			{
				v_membergubun = "기업";
			}
			else if(v_membergubun.equals("U"))
			{
				v_membergubun = "대학";
			}
			else
			{
				v_membergubun = "개인";
			}
	   %>
          <tr align="left">
            <td><%= i+1 %></td>
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			v_usercnt++;
			%>
            <!-- 코스인 경우 -->
            <td class="table_02_2" rowspan="<%=v_subjcnt%>"><%=v_coursenm%></td>
            <td class="table_02_2" ><%=v_subjnm %></td>
<%	
			v_tmp_subj = v_course;
		}else{ 
			l++;
		%>
            <td class="table_02_2" ><%=v_subjnm %></td>
<%		}
	} else { 
			v_usercnt++;%>
            <!-- 과정인 경우 -->
            <td class="table_02_2" colspan="2"><%=v_subjnm %></td>
<% } %>
            <td><%=v_membergubun%></td>              
            <td><%= v_userid %></td>
            <td><%= v_name %></td>
            <td><%=StringManager.cutZero(v_subjseqgr)%></td>
            <!--td><%//=v_isonoff_value%></td-->
            <td><%= v_edustart %>~<br> <%= v_eduend %></td>            
            <td><%=v_tstep%>(<%=v_avtstep%>)</td>
            <td><%=v_mtest%></td><!--중간-->
            <td><%=v_ftest%></td><!--최종-->
            <td><%=v_htest%></td><!--형성-->
            <td><%=v_report%></td>
            <td><%=v_etc1%></td>
            <td><%=v_etc2%></td>
            <td><%=v_score%></td>
            <td><%=v_isgraduated%></td>
          </tr>
          <%
               }
         if(i == 0){ %>
          <tr> 
            <td align="center" bgcolor="#FFFFFF" height="50" colspan="19">수료명단이 없습니다.</td>
          </tr>
		<% } %>
        </table>
      </td>
  </tr>
</table>
</body>
</html>

