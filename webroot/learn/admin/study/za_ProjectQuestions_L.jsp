<%
//**********************************************************
//  1. 제      목: PROJECT QUESTION LIST
//  2. 프로그램명: za_ProjectQuestions_L.jsp
//  3. 개      요: 리포트 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_subjseqgr = box.getString("p_subjseqgr");
    String  v_eduend    = box.getString("p_eduend");    // 교육기간 마지막
    String  v_lesson    = "";
    String  v_lessonnm  = "";
    String  v_reptype   = "";
    String  v_isopen    = "";
    String  v_isopenscore="";
    String  v_title     = "";
    String  v_tocnt     = "";      // 총수강자수
    String  v_grcnt     = "";      // 그룹인원수
    String  v_groupcnt  = "";      // 그룹갯수
	String  v_expiredate= "";	   // 제출만기일
    String  v_deadlinesdate= "";
    String  v_deadlineedate= "";
	String  v_upfile    = "";      // 답안파일
	int 	v_projseq	=  0;
    int     v_ordseq    =  0;
    int     v_score     =  0;
    int     v_micnt     =  0;      //그룹미편성자수
    int     i           =  0;
	int		v_rowspan 	=  0;	   //리포트 차수당 리포트 수
	int		v_rowspanseq=  0;	   //리포트 차수 ROOT SEQ
    ArrayList list      = null;
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");
    //DEFINED in relation to select END


    list = (ArrayList)request.getAttribute("projectQuestionsList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 목록으로 이동
    function projectList() {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsAdmin';
        document.form1.submit();
    }

	// 등록
    function project_insert(subj,subjnm,year,subjseq,lesson) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsInsertPage';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_lesson.value = lesson;
        document.form1.submit();
    }

	// 이전차수 복사
	function project_copy(subj,year,subjseq) {

		//과정차수가 0001인경우 복사하기 기능 없음으로 처리할 것

		var ans = confirm("이전차수의 리포트를 복사하시겠습니까?");
		if (ans) {
			document.form1.action='/servlet/controller.study.ProjectAdminServlet';
			document.form1.p_process.value = 'ProjectQuestionsCopy';
			document.form1.p_subj.value = subj;
			//document.form1.p_subjnm.value = '과정';
			document.form1.p_year.value = year;
			document.form1.p_subjseq.value = subjseq;
			document.form1.p_lesson.value = '12';
			document.form1.submit();
		}
    }

	// 리포트 상세보기
    function project_update(subj,subjnm,year,subjseq,ordseq,lesson, eduend) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectQuestionsUpdatePage';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_ordseq.value = ordseq;
        document.form1.p_lesson.value = lesson;
        document.form1.p_eduend.value = eduend;
        document.form1.submit();
    }

    function project_grouping(subj,subjnm,year,subjseq,ordseq,lesson,groupcnt) {
        document.form1.action='/servlet/controller.study.ProjectAdminServlet';
        document.form1.p_process.value = 'ProjectGroupHandlingPage';
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_year.value = year;
        document.form1.p_subjseq.value = subjseq;
        document.form1.p_ordseq.value = ordseq;
        document.form1.p_lesson.value = lesson;
        document.form1.p_groupcnt.value = groupcnt;
        document.form1.submit();
    }
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="p_subj">
    <input type="hidden" name="p_subjnm" value="<%=v_subjnm%>">
    <input type="hidden" name="p_year">
    <input type="hidden" name="p_subjseq">
    <input type="hidden" name="p_subjseqgr" value="<%=v_subjseqgr%>">
    <input type="hidden" name="p_ordseq">
    <input type="hidden" name="p_lesson">
    <input type="hidden" name="p_groupcnt">
    <input type="hidden" name="p_eduend" value="<%=v_eduend%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/co_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
            <%=v_subjnm%> 과정 &gt; <%=StringManager.cutZero(v_subjseqgr)%>차</td>
          </tr>
        </table>

        <br>
        <!----------------- 추가, 리스트 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="735" height="20" valign="bottom">&nbsp;</td>
            <td align="right" width="155">
            <a href="javascript:project_insert('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_lesson%>')"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
			<td width=8></td>
			<td width="32"><a href="javascript:project_copy('<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')"><img src="/images/admin/button/btn_beforecopy.gif" border="0"></a></td>
			<td width=8></td>
            <td align="right" width="32">
            <a href="javascript:projectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
          <tr>
            <td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
          </tr>
        </table>
        <!----------------- 추가, 리스트 버튼 끝 ----------------->

        <!----------------- 리포트 출제리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" class="table_title"><b>셋트번호</b></td>
            <td class="table_title"><b>리포트 주제</b></td>
            <td width="20%" class="table_title"><b>제출기간</b></td>
            <td width="10%" class="table_title"><b>답안파일</b></td>
            <td width="10%" class="table_title"><b>사용여부</b></td>
          </tr>
          <%
            for(i = 0; i < list.size(); i++) {
                ProjectData data= (ProjectData)list.get(i);
				v_projseq		= data.getProjseq();
                v_ordseq        = data.getOrdseq();
                v_lesson        = data.getLesson();
				v_lessonnm      = data.getLessonnm();
                v_reptype       = data.getReptype();
                v_isopen        = data.getIsopen();
                v_isopenscore   = data.getIsopenscore();
                v_title         = data.getTitle();
                v_score         = data.getScore();
                v_tocnt         = data.getTocnt();
                v_grcnt         = data.getGrcnt();
                v_groupcnt      = data.getGroupcnt();
		v_expiredate	= data.getExpiredate();
                v_deadlinesdate= data.getDeadlinesdate();
                v_deadlineedate= data.getDeadlineedate();
		v_rowspan		= data.getRowspan();
		v_rowspanseq    = data.getRowspanseq();
		v_upfile        = data.getUpfile2(); // 답안파일
		v_eduend        = data.getEduend(); // 답안파일

                if(v_reptype.equals("R")){      v_reptype   =   "일반";   }
                else if(v_reptype.equals("P")){ v_reptype   =   "PROJECT";  }
                else if(v_reptype.equals("C")){ v_reptype   =   "COP";      }
                if(v_tocnt != null && !v_tocnt.equals("")){
                    v_micnt     = Integer.parseInt(v_tocnt) -  Integer.parseInt(v_grcnt);
                }
                if (v_deadlineedate.equals("")) v_deadlineedate = v_expiredate;
           %>
          <tr>
		  <% if (v_rowspanseq ==  v_ordseq) { %>
            <td class="table_02_1" rowspan=<%=v_rowspan%>><%=v_projseq%></td>
		  <% } %>
            <td class="table_02_1"> <a href="javascript:project_update('<%=v_subj%>','<%=v_subjnm%>','<%=v_year%>','<%=v_subjseq%>','<%=v_ordseq%>','<%=v_lesson%>', '<%=v_eduend%>')" class='b'><%=v_title%></a> </td>
            <td class="table_02_1" ><%=FormatDate.getFormatDate(v_deadlinesdate,"yyyy/MM/dd")%> ~ <%=FormatDate.getFormatDate(v_deadlineedate,"yyyy/MM/dd")%></td>
            <td class="table_02_1" ><%if(!v_upfile.equals("")){%><img src='/images/admin/button/icon_file.gif' border='0'><%}%></td>
            <td class="table_02_1" ><%=data.getUseyn()%></td>
          </tr>
          <%}
          if(i == 0){ %>
          <tr>
            <td class="table_02_1" height="50" colspan="5">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
        <!----------------- Activity 리스트 끝 ----------------->
        <br>
        <% if(i > 0){ %>
        <table width="97%"><tr align=right><td><b>☞ 수강자수 : <%=v_tocnt%>명</b></td></tr></table>
        <% } %>
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
