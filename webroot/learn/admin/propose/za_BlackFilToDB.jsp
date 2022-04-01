<%
//**********************************************************
//  1. 제      목: 수강신청 제약 대상자 FileToDB
//  2. 프로그램명 : za_BlackFileToDB.jsp
//  3. 개      요: 수강신청 제약 대상자 FileToDB
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성:
//  7. 수      정: 정경진 2005.07.19
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box = (RequestBox)request.getAttribute("requestbox");


String  s_grcode	= box.getString("grcode");
String  s_gryear	= box.getString("gryear");
String  s_grseq		= box.getString("grseq");
String  s_conditioncode    = box.getString("conditioncode");

String  v_file1     = box.getString("p_file");


%>
<html>
<head>
<title>수강제약 대상자 엑셀등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function insert_check() {
  action("insertFileToDB");
}
function preview() {
  action("previewFileToDB");
}
function action(p_process) {                
  

  if(blankCheck(document.form3.p_file.value)){
    alert("DB로 가져갈 파일을 선택해 주세요");
    return;
  }

  if (document.form3.p_file.value.length > 0 ){
    var data = document.form3.p_file.value;
    data = data.toUpperCase(data);
    if (data.indexOf(".XLS") < 0) {
      alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
      return;
    }
  }
  document.form3.action = "/servlet/controller.propose.AcceptFileToDBServlet";
  document.form3.p_process.value = p_process;                              
  document.form3.submit();

}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/system/tit_new_cancelapply.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
      <form name="form1" method="post" action="/servlet/controller.propose.AcceptServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">

		<tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td>
				     교육그룹 : <b>[<%=GetCodenm.get_grcodenm(s_grcode)%>]</b> 교육차수 : <b>[<%=GetCodenm.get_grseqnm(s_grcode, s_gryear, s_grseq)%>]</b> 수강제약구분 :  <b>[<%=CodeConfigBean.getCodeName("0050",s_conditioncode)%>]</b>
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
      <form name="form3" method="post" action="/servlet/controller.propose.AcceptServlet" enctype="multipart/form-data">
        <input type="hidden" name="p_process"   value="">
        <input type="hidden" name="p_action"    value="">
        <input type="hidden" name="p_grcode"	value="<%=s_grcode%>">
        <input type="hidden" name="p_gryear"	value="<%=s_gryear%>">
        <input type="hidden" name="p_grseq"		value="<%=s_grseq%>">
        <input type="hidden" name="p_conditioncode"  value="<%=s_conditioncode%>">

        <tr>
          <td align="center">
            <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center">
                  <input name="p_file" type="FILE" class="input" size="145">
                </td>
              </tr>
              
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 찾아보기 끝 ----------------->
      <br>
      <!----------------- 미리보기, 확인, 취소 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="928" height="20" align="right"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
		  <td width=10></td>
          <td align="right" width="32"><a href="javascript:preview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
        </tr>
      </table>


      </form>
      <!----------------- 미리보기, 확인, 취소 버튼 끝 ----------------->
      <br>
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=dir_txt ><b><font size="3">(주의사항 - 필독)</font></b></td>
        </tr>
        <tr>
          <td height="8"></td>
        </tr>
        
        <tr>
          <td height="20" class=dir_txt >1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >2. 엑셀파일 작성시 <font color="#A661D7">[<b>사번</b>]</font>, <font color="#A661D7">[<b>직무(W),어학(L) 구분</b>]</font>의 순서로 만들 것.</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >3. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=sugangjeyak.xls&p_realfile=sugangjeyak.xls'><b>샘플파일</b></a>)</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
      
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align='left'>샘플파일</td>
        </tr>
        <tr>
          <td><img src="/images/admin/propose/blacklistExcel.jpg" border="0"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</body>
</html>
