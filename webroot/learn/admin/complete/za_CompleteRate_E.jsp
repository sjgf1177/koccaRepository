<%
//**********************************************************
//  1. 제      목: COMPLETE RATE LIST
//  2. 프로그램명: za_CompleteRate_E.jsp
//  3. 개      요: 수료율 조회 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%	response.setHeader("Content-Disposition", "attachment; filename=za_CompleteRate_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
	  
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

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
    String  v_edustart  =  "";
    String  v_eduend    =  "";
    String  v_isnewcourse= "";
    String  v_isonoff   =  "";
    String  v_isonoff_value="";
	String v_tmp_subj	= "";
	String v_iscourseYn	= "";

    int     v_educnt    =  0;
    int     v_gradcnt1  =  0;
    int     v_gradcnt2  =  0;
    int     v_gradcnt   =  0;
    int     v_gradrate  =  0;
    int     v_teducnt   =  0;
    int     v_tgradcnt  =  0;
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
	int		v_subjcnt	= 0;
    int     l           =  0;

    ArrayList list      = null;
    //DEFINED class&variable END

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

    String  ss_edustart  = box.getString("s_start");         //교육시작일
    String  ss_eduend    = box.getString("s_end");           //교육종료일
	String  p_subjnm	 = box.getString("p_subjnm");		 //과정명

    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";
	//DEFINED in relation to select END

	list = (ArrayList)request.getAttribute("CompleteRateExcel");
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서 

%>
<HTML>
<HEAD>
<TITLE></TITLE>
</HEAD>
<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<table width="1000" border="1" cellspacing="0" cellpadding="0" height="663">
	<tr>
		<td align="center" height="30"><font size="3"><b>수료율 조회 리스트</b></font></td>
	</tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table cellspacing="1" cellpadding="5" border="1">
          <tr> 
            <td width="4%"><b>NO</b></td>
            <td width="38%" colspan="2"><b>과정</b></td>
            <td width="8%"><b>차수</b></td>
            <td width="8%"><b>구분</b></td>
            <td width="17%"><b>교육기간</b></td>
            <td width="7%"><b>교육</b></td>
            <td width="8%"><b>수료</b></td>
            <td width="10%"><b>수료율(%)</b></td>
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
                    v_edustart      = dbox.getString("d_edustart");
                    v_eduend        = dbox.getString("d_eduend");
                    v_isnewcourse   = dbox.getString("d_isnewcourse");
                    v_educnt        = dbox.getInt("d_educnt");
                    v_gradcnt1      = dbox.getInt("d_gradcnt1");
                    v_gradcnt2      = dbox.getInt("d_gradcnt2");
                    v_iscourseYn    = dbox.getString("d_isbelongcourse");
                    v_subjcnt       = dbox.getInt("d_subjcnt");

                    v_totalpage     = dbox.getInt("d_totalpage");
                    v_isonoff       = dbox.getString("d_isonoff");

                    if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
                    else                      {     v_isonoff_value="집합";     }
                    v_edustart      = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                    v_eduend        = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
                    v_gradcnt       = v_gradcnt1 + v_gradcnt2;
                    
                    if(v_educnt > 0) {
                    	v_gradrate      = (int)(((float)v_gradcnt/v_educnt) * 100);
                    } else {
                    	v_gradrate = 0;
                    }
                    
                    v_teducnt      += v_educnt;
                    v_tgradcnt     += v_gradcnt;
               %>
          <tr align="left"> 
            <td class="table_01"><%= i + 1 %></td>
<% if(v_iscourseYn.equals("Y"))
	{ 
		if(l == v_subjcnt) v_tmp_subj = "";

		if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
		{
			l = 1;
			%>
			<td class="table_02_2" rowspan="<%=v_subjcnt%>"><%=v_coursenm %></td>            <!-- 과정인 경우 -->
            <td class="table_02_2"> <%=v_subjnm%></td>
<%	
			v_tmp_subj = v_course;
		}else{ 
			l++;
		%>		
           
            <td class="table_02_2"> <%=v_subjnm%></td>
<%		}
	} else { %>
            
            <td class="table_02_2" colspan="2"> <%=v_subjnm%></td>
<% } %>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1"><%=v_isonoff_value%></td>
            <td class="table_02_1"><%= v_edustart %> ~ <%= v_eduend %></td>
            <td class="table_02_1"><%=v_educnt%></td>
            <td class="table_02_1"><%= v_gradcnt1 + v_gradcnt2 %></td>
            <td class="table_02_1"><%=v_gradrate%>%</td>
          </tr>
          <% }

         if(i == 0 && ss_action.equals("go")){ %>
          <tr> 
            <td class="table_02_1" colspan="9">등록된 내용이 없습니다</td>
          </tr>
	<% } %>
        </table>
      </td>
  </tr>
</table>
</body>
</html>
