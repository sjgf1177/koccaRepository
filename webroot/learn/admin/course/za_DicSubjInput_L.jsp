<%
//**********************************************************
//  1. 제      목 : 용어사전 Excel Upload
//  2. 프로그램명 : za_DicSubjInput_L.jsp
//  3. 개      요 : 용어사전 Excel Upload
//  4. 환      경 : JDK 1.4
//  5. 버      젼 : 1.0
//  6. 작      성 : 2004/02/02
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//2005.12.06_하경태 : 과정 -> 교육그룹으로 변경
    //String  ss_subjcourse	= box.getString("s_subjcourse");       //과정&코스
	String  ss_subjcourse	= box.getString("s_grcode");       //교육그룹코드
    String  ss_upperclass	= box.getString("s_upperclass");       //대분류
    String  ss_middleclass	= box.getString("s_middleclass");       //중분류
    String  ss_subjnm		= box.getString("s_subjnm");       //중분류


%>
<html>
<head>
<title>용어사전 EXCEL UPLOAD</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 등록
function insert_check() {
  action("insertFileToDB");
}

// 미리보기
function preview() {
  action("previewFileToDB");
}

// 조회
function action(p_process) {
  ff1 = document.form1;
  ff3 = document.form3;

  if(blankCheck(ff3.p_file.value)){
    alert("DB로 가져갈 파일을 선택해 주세요");
    return;
  }
  if (ff3.p_file.value.length > 0 ){
    var data = ff3.p_file.value;
    data = data.toUpperCase(data);
    if (data.indexOf(".XLS") < 0) {
      alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
      return;
    }
  }

  

  ff3.action = "/servlet/controller.course.DicSubjAdminServlet";
  ff3.p_process.value = "ExcelUploadSave";
  ff3.p_mode.value = p_process;
  ff3.submit();
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="700" border="0" cellspacing="0" cellpadding="0">
	<tr>
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
	<br>
  	<td align="center" valign="top"> 
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
      <form name="form1" method="post" action="/servlet/controller.complete.FinishInputServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
		
        <tr> 
          <td align="center"> 
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr> 
                      <td>
                      과정명 : <%=ss_subjnm%>

                      </td>
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
      </form>  
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <!----------------- 찾아보기 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
      <form name="form3" method="post" action="/servlet/controller.complete.FinishInputServlet" enctype="multipart/form-data">
      
  		<input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
  		<input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
  		<input type="hidden" name="s_middleclass"  value="<%=ss_middleclass%>">
  		<input type="hidden" name="s_subjnm"  value="<%=ss_subjnm%>">
  		
  		
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_mode"    value="">
        <input type="hidden" name="p_action"     value="">

		
        <tr> 
          <td align="center"> 
            <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td align="center"> 
                  <input name="p_file" type="FILE" class="input" size=70>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </form>  
      </table>
      <!----------------- 찾아보기 끝 ----------------->
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="930" height="20" align="right"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
		  <td width=8></td>
          <td align="right" width="32"><a href="javascript:preview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="heed_table">
        <tr> 
          <td class="text_02">(주의사항)</td>
        </tr>
        <tr> 
          <td height="8"></td>
        </tr>
        <tr> 
          <td class="heed_table">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr> 
          <td class="heed_table">2. 엑셀파일 작성시 [<b><font color="#003AEA">용어, 구분, 설명</font></b>]의 순서로 
            만들 것.</td>
        </tr>
        <tr>
          <td class="heed_table">4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_DicSubj.xls&p_realfile=Sample_DicSubj.xls'><b>샘플파일</b></a>)</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/admin/course/DicSubj.gif" border="0"></td>
        </tr>
      </table>      
	  </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</body>
</html>
