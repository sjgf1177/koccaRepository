<%
/**
 * file name : za_DamunFileToDB.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 다면평가  일괄처리
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
   
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode      = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum  = box.getInt("s_damunpapernum");
    String  ss_subjseq     = box.getStringDefault("p_subjseq","ALL");
	
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");

%>
<html>
<head>
<title>다면평가  화면</title>
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

    document.form1.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
    document.form1.p_process.value = process;
    document.form1.submit();
}


function whenSelection(p_action) {
  document.form1.p_process.value = 'DamunSubjMemberFileToDB';
  document.form1.p_action.value  = p_action;
  document.form1.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
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
        <input type="hidden" name="s_grcode"       value="<%=ss_grcode%>">
        <input type="hidden" name="s_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_subjcourse"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="s_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="s_damunpapernum"       value="<%=ss_damunpapernum%>">
        <input type="hidden" name="p_grcode"       value="<%=ss_grcode%>">
        <input type="hidden" name="p_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="p_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="p_damunpapernum"       value="<%=ss_damunpapernum%>">

<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
	    <br>
      <table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
		  <td width="3%" align=right><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" class=sub_title>대상자/관찰자 파일추가</td>
        </tr>
      </table>	
        <br>
        <!----------------- 찾아보기 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"> 
              <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr> 
                  <td align="center"> 
                    <input name="p_file" type="FILE" SIZE='100' class="input">
                  </td>
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
            <td width="900" height="20" align="right"><a href="javascript:insert_check('DamunFileToDB_INSERT')"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
			<td width=7></td>
            <td align="right" width="32"><a href="javascript:insert_check('DamunFileToDB_P')"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
			<td width=7></td>
			<td align="right" width="40"> <a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></td>
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
          <td height="20" class=dir_txt >2. 대상자와 관계 : <font color="#A661D7"><b>1-상사, 2-동료,  3-부하</b></font> </td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >3. 엑셀파일 작성시 <font color="#A661D7">[<b>대상자ID,대상자와 관계,관찰자ID</b>]</font>의 순서로 만들 것.</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >4. <font color="#E60873">샘플 양식 폼을 변경할시 치명적인 오류가 발생합니다.</font></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >5. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=damun.xls&p_realfile=damun.xls'><b>샘플파일</b></a>)
          </td>
        </tr>
        </table>
        <!----------------- 주의사항 끝 ----------------->

      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/admin/multiestimate/Sample_DamunMember.bmp" border="0"></td>
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
