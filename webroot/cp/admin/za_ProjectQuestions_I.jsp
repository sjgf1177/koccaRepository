<%
//**********************************************************
//  1. 제      목: PROJECT INSERT
//  2. 프로그램명: za_ProjectQuestions_I.jsp
//  3. 개      요: 리포트 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_subjseqgr   = box.getString("p_subjseqgr");
    String  v_lesson     = box.getString("p_lesson");
	//DEFINED class&variable END

    String  ss_action    = box.getString("s_action");   

	//과정차수 정보중 시작일,종료일정보 가져오기
    //GrseqBean bean = new GrseqBean();
    //SubjseqData subjseqdata = bean.SelectSubjseqData(box);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css"> 
<script language = "javascript" src = "/script/cresys_lib.js"></script> 
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
    <script	language="JavaScript" type="text/JavaScript">
    <!--	   
        function project_insert() {    
            //var expire_date = make_date(document.form1.ex_date.value);      
            if (blankCheck(document.form1.p_title.value)) {
                alert("제목을 입력하세요!");
                document.form1.p_title.focus();
                return;
            }
                
            if (realsize(document.form1.p_title.value) > 100) {
                alert("제목은 한글기준 100자를 초과하지 못합니다.");
                document.form1.p_title.focus();
                return;
            }   
    
            if (blankCheck(document.form1.p_contents.value)) {
                alert("내용을 입력하세요!");
                document.form1.p_contents.focus();
                return;
            }
//            if(blankCheck(document.form1.ex_date.value)){
//                alert("제출 마감일을 입력하세요.");
//                return;
//            }            
//            document.form1.p_expiredate.value = expire_date;
            document.form1.p_process.value = "ProjectQuestionsInsert";
            document.form1.target = "_self";
            document.form1.action = "/servlet/controller.cp.BPProjectAdminServlet?p_file=2";
            document.form1.submit();
        }    
	    function cancel() {
	        document.form1.p_process.value = "ProjectQuestionsList";
	        document.form1.target = "_self";
            document.form1.action = "/servlet/controller.cp.BPProjectAdminServlet";
            document.form1.submit();	
        }
   -->			
    </script>        
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' 
ondragstart='return false' onselectstart='return false' 
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
   <form name="form1" method="post" action="" enctype = "multipart/form-data">
    <input type="hidden" name="p_process">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_report.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table> 
      <!----------------- title 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
          <%=v_subjnm%> 과정 &gt; <%=v_subjseq%>차</td>
        </tr>
      </table>
      <br>

      <!----------------- 리포트 등록/수정 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" height="25">셋트번호</td>
            <td class="table_02_2"><input name="p_projseq" type="text" class="input" size="10" value="<%=box.getInt("v_maxprojseq")%>"></td>
          </tr>
          <input type="hidden" name="p_reptype" value="R">
          <input type="hidden" name="p_groupcnt">
		  <input name="p_lesson" type="hidden" value="001">
          <tr> 
            <td class="table_title" height="25">제목</td>
            <td class="table_02_2"> <input name="p_title" type="text" class="input" size="72"> 
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25">내용</td>
            <td class="table_02_2"> <textarea name="p_contents" cols="100" rows="14"></textarea> 
            </td>
          </tr>
          <!--tr> 
            <td class="table_title" height="25">교육기간</td>
            <td class="table_02_2"><%//=FormatDate.getFormatDate(subjseqdata.getEdustart(),"yyyy/MM/dd")%> ~ <%//=FormatDate.getFormatDate(subjseqdata.getEduend(),"yyyy/MM/dd")%></td>
          </tr>
          <tr> 
            <td class="table_title" height="25">제출마감일</td>
            <td class="table_02_2"> <input name="ex_date" type="text" class="input" size="10"  value="<%//=FormatDate.getFormatDate(subjseqdata.getEduend(),"yyyy-MM-dd")%>" OnClick="MiniCal(this)" readonly> 
            </td>
          </tr-->
          <tr> 
            <td height="25" class="table_title">출제파일</td>
            <td class="table_02_2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="125"> <input type="file" class="input" size="60" name="p_file1"> 
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td height="25" class="table_title">답안파일</td>
            <td class="table_02_2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="125"> <input type="file" class="input" size="60" name="p_file2"> 
                  </td>
                </tr>
              </table></td>
          </tr>
          <tr>
          	<td class="table_title">답안 제출 옵션</td>
          	<td class="table_02_2">
			<input type="radio" name="ansyn" value="U" checked>사용자직접선택&nbsp;&nbsp;&nbsp;
			<input type="radio" name="ansyn" value="T">TEXT입력방식&nbsp;&nbsp;&nbsp;
			<input type="radio" name="ansyn" value="F" >FILE업로드&nbsp;&nbsp;&nbsp;
			</td>
          </tr>
          <tr>
          	<td class="table_title">사용여부</td>
          	<td class="table_02_2"><input type="radio" name="useyn" value="Y" checked>사용&nbsp;&nbsp;&nbsp;<input type="radio" name="useyn" value="N" >미사용</td>
          </tr>
		  <input type="hidden" name="p_score" value="100"> 
        </table>
      <!----------------- 리포트 등록/수정 끝 ----------------->
      <br>

      <!----------------- 저장, 취소 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center">
          <a href="javascript:project_insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a>
          </td>
		  <td width=8></td>
          <td align="center">
          <a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <!----------------- 저장, 취소 버튼 끝 ----------------->
      <br>
      </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr> 

</table>
</form>
</body>
</html>