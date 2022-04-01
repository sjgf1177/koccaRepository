<%
/**
 * file name : za_DamunSubjObserver_I.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 관찰자파일등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");
    
    String  v_subj= box.getString("p_subj");

 //   DataBox dbox = (DataBox)request.getAttribute("ETestMasterData");
    
%>
<html>
<head>
<title>관찰자 파일등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

function insert_check(process) {
    if(blankCheck(document.form1.p_file.value)){
        alert("DB로 가져갈 파일을 선택해 주세요");
        return;
    }
    if (document.form1.p_file.value.length > 0 ){
        var data = document.form1.p_file.value;
        data = data.toUpperCase(data);

        if (data.indexOf(".XLS") < 0) {
            alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
            return;
        }
    }

    document.form1.action = "/servlet/controller.exam.ExamQuestionServlet";
    document.form1.p_process.value = process;
    document.form1.submit();
}


function whenSelection(p_action) {
  document.form1.p_process.value = 'ExamQuestionFileToDB';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.exam.ExamQuestionServlet";
  document.form1.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form 시작 ----------------->
<form name="form1" method="post" enctype="multipart/form-data">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_subj"       value="<%=v_subj%>">

<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><font size=4><b>관찰자파일등록</b></font></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
		        <!-------------소제목 시작-------------------------->
 <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>일괄등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
        <!----------------- 찾아보기 시작 ----------------->
        <table width="97%" border="1" cellspacing="0" cellpadding="0">
                    <tr>
                      <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg">
                          <tr>
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td align="center">
                              <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                                <tr>
                                  <td><input type="radio" name="p_select" value="1" checked>문제입력&nbsp;<input type="radio" name="p_select" value="2">보기입력</td>
                                  <td align="center" colspan="2"><input name="p_file" type="FILE" class="input"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr>
                            <td height="7"></td>
                          </tr>
                        </table>
                      </td>
                    </tr>

        </table>
        <!----------------- 찾아보기 끝 ----------------->

        <br>
        <!----------------- 등록, 미리보기 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td width="929" align="right" ><a href="javascript:insert_check('insertFileToDB')"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
			<td width=9></td>
            <td width="32" align="right" ><a href="javascript:insert_check('previewFileToDB')"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 등록, 미리보기 버튼 끝 ----------------->

        <br>
        <br>
        <!----------------- 주의사항 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=dir_txt ><b><font size="3">(주의사항)</font></b></td>
        </tr>
        <tr>
          <td height="8"></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >2. 엑셀파일 작성시 <font color="#A661D7">[<b>문제 파일</b>]과 [<b>문제보기 파일</b>]</font>을 나누어 만들 것.</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >3. <font color="#E60873">샘플 양식 폼을 변경할시 치명적인 오류가 발생합니다.</font></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_ExamQuestion.xls&p_realfile=Sample_ExamQuestion.xls'><b>문제 샘플파일</b></a>)</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/admin/exam/Sample_ExamQuestion.gif" border="0"></td>
        </tr>
      </table>
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="20" class=dir_txt >&nbsp;&nbsp;5. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_ExamQuestionSel.xls&p_realfile=Sample_ExamQuestionSel.xls'><b>문제보기 샘플파일</b></a>)</td>
        </tr>
		<br>
		<tr>
          <td><img src="/images/admin/exam/Sample_ExamQuestionSel.gif" border="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>
<!----------------- form 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
