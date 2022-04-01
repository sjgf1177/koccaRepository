<%
//**********************************************************
//  1. 제      목: SUBJ QNA LIST
//  2. 프로그램명: zu_SubjQnaSubjseq_L.jsp
//  3. 개      요: 질문 admin bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김영만 2003. 9. 1
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
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
    String  ss_grcode    = box.getString("ss_grcode");        //교육그룹
    String  ss_gyear     = box.getString("ss_gyear");         //년도
    String  ss_grseq     = box.getString("ss_grseq");         //교육차수
    String  ss_uclass    = box.getString("ss_uclass");        //과정분류
    String  ss_subjcourse= box.getString("ss_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("ss_subjseq");       //과정 차수

    String  v_process   = box.getString("p_process");    
    String  v_lesson		= "";
    String  v_lessonnm  = "";
    int  v_qcnt     = 0;    
    int  v_nanscnt	=0;
    int     i            = 0; 
    ArrayList list1      = null; 
    //DEFINED class&variable END

    String v_gadminYn = (String)request.getAttribute("GadminYn");  // 운영자여부 1:운영자,0:학습자
    //DEFINED in relation to select START
    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year     = box.getString("p_year");         //년도
    String  v_subjseq   = box.getString("p_subjseq");       //과정 차수
    //DEFINED in relation to select END
    
    list1 = (ArrayList)request.getAttribute("SubjqnaSubjseqList");
        
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
   function qnalist_select(lesson,lessonnm) {
        document.ff.target = "rightFrame";
        document.ff.action='/servlet/controller.study.SubjQnaStudyServlet';
        document.ff.p_process.value = 'SubjQnaList';
        document.ff.p_subj.value = '<%=v_subj%>';                
        document.ff.p_year.value = '<%=v_year%>';     
        document.ff.p_subjseq.value = '<%=v_subjseq%>';  
        document.ff.p_lesson.value = lesson;
        document.ff.p_lessonnm.value = lessonnm;
        document.ff.submit();
        
    }

	function goQnaSubjListPage() {	  
	  document.ff.submit();
	}    
-->    	
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="ff" method="post" action="/servlet/controller.study.SubjQnaStudyServlet">
		<input type="hidden" name="p_process"    value="SubjQnaList">
		<input type="hidden" name="p_subj"       value="">
		<input type="hidden" name="p_year"       value="">
		<input type="hidden" name="p_subjseq"    value="">
		<input type="hidden" name="p_lesson"     value="">
		<input type="hidden" name="p_lessonnm"   value="">
		<input type="hidden" name="s_grcode"     value="<%=ss_grcode%>">
		<input type="hidden" name="s_gyear"      value="<%=ss_gyear%>">
		<input type="hidden" name="s_grseq"      value="<%=ss_grseq%>">
		<input type="hidden" name="s_uclass"     value="<%=ss_uclass%>">
		<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">
		<input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>">
		<input type="hidden" name="p_gadminYn"   value="<%=v_gadminYn%>">

  <table width="270" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td align="center" valign="top"> 

      <br>
      <br>

      
      <!----------------- 과정차시별 질문현황 조회 시작 ----------------->
      
      <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
        <tr> 
            <td class="board_color_line"></td>
        </tr>
      </table>
      
      <table width="97%"  class="open_board_table_out2" border="0" cellspacing="1" cellpadding="5">
        
        <tr> 
          <td class="board_title_bg1" width="7%"><b>일차</b></td>
          <td class="board_title_bg1"><b>차시명</b></td>
          <td class="board_title_bg1" width="10%"><b>질문수</b></td>
          <td class="board_title_bg1" width="10%"><b>미응답갯수</b></td>
        </tr>
        
        <%
            for(i = 0; i < list1.size(); i++) {
                QnaData data= (QnaData)list1.get(i);
                v_qcnt     	= data.getQcnt();    
                v_nanscnt   =	data.getNoanscnt(); 
                v_lesson    = data.getLesson();
                v_lessonnm  = data.getLessonnm();

        %>        
        <tr> 
          <td class="board_text6" align="center"><%= v_lesson %></td>
          <td class="board_text1"><a href="javascript:qnalist_select('<%=v_lesson%>','<%=v_lessonnm%>');"><%= v_lessonnm%></a></td>
          <td class="board_text1"><%= v_qcnt %></td>
          <td class="board_text1"><%= v_nanscnt %></td>
        </tr>
        <%
    			}
        %>

      </table>
      <!----------------- 과정차시별 질문현황 조회 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

</form>
</body>
</html>
