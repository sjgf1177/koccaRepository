<%
//**********************************************************
//  1. 제      목: 교육과정결과 등록
//  2. 프로그램명: za_cpeduResult_i.jsp
//  3. 개      요: 교육과정결과 등록폼
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005.7.27
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 0, v_usercnt = 0, v_totalrow = 0;
String v_subj = "", v_subjnm = "", v_cpnm = "", v_cpsubjseq = "", v_cpsubj = "", v_subjseqgr="";
String v_subjseq = "", v_propstart = "", v_propend = "", v_edustart = "", v_eduend = "";
String v_userid = "", v_name = "", v_email = "", v_handphone = "", v_eduurl = "", v_canceldate ="", v_reason = "";

//ArrayList list = (ArrayList)request.getAttribute("selectCancelUserList");

String v_searchtext = box.getString("p_searchtext");
int v_pageno = box.getInt("p_pageno");
String v_grcode = box.getString("p_grcode");
String v_gyear = box.getString("p_gyear");
String v_year = box.getString("p_year");
String v_cp = box.getString("p_cp");

v_subj =  box.getString("p_subj");
v_cpsubjseq =  box.getString("p_cpsubjseq");
v_subjseq =  box.getString("p_subjseq");
v_subjseqgr =  box.getString("p_subjseqgr");



//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");
DataBox dbox = (DataBox)request.getAttribute("recentStatus");
String v_indate = "-";
String v_realfile = "-";


if(dbox != null){
  v_indate  = dbox.getString("d_indate");
  v_realfile = dbox.getString("d_realfile");
  
  if(v_indate.equals("") ){
   v_indate = "-";
  }else{
    v_indate = FormatDate.getFormatDate(v_indate, "yyyy/MM/dd HH:mm");
  }
  
  if(v_realfile.equals("") ){
   v_realfile = "-";
  }
}

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript">
<!--
function action(p_process) {
  if(document.form1.p_file.value.length == 0 ){
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
  
  document.form1.action = "/servlet/controller.cp.CPEduStatusServlet";
  document.form1.p_process.value = p_process;                              //insertFileToDB,previewFileToDB
  document.form1.submit();
}
//-->
</script>
</head>

<body topmargin=0 leftmargin=15>

<form name="form1" action="" method="post" enctype="multipart/form-data">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
<input type="hidden" name="s_grcode" value="<%=v_grcode%>">
<input type="hidden" name="s_gyear" value="<%=v_gyear%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
<input type="hidden" name="p_cp" value="<%=v_cp%>">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_subjseqgr" value="<%=v_subjseqgr%>">

	<!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../images/admin/cp/cp_tit_study.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
<table width="650" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center"> 
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="21" height=15></td>
          <td height=15></td>
        </tr>
        <tr> 
          <td width="21"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>EXCEL 학습현황 등록</td>
        </tr>
        <tr> 
          <td width="21" height=12></td>
          <td height=12></td>
        </tr>
        <tr> 
          <td></td>
          <td class=dir_txt style="line-height:18px">교육현황 데이터를 엑셀로 등록하시려면 정해진 아래의 양식에 맞추어 엑셀파일을 준비해주십시오.<br>             
            등록하시기전에 엑셀파일의 모든 셀을 "텍스트" 타입으로 변환하여 주시기 바랍니다.<br>
            <b><a href="/servlet/controller.library.DownloadServlet?p_savefile=Sample_CPStatus.xls&p_realfile=Sample_CPStatus.xls">[ 엑셀형식보기 ]</a></b></td>
        </tr>
        <tr> 
          <td colspan="2" class=map_line></td>
        </tr>
        <tr> 
          <td height=8></td>
          <td height=8></td>
        </tr>
      </table>
	   
      <table width="600" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr> 
          <td><table width="600" cellpadding="0" cellspacing="0" class="form_table_bg">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align=center> 
                  <table width=97% border="0" cellspacing="0" cellpadding="3">
                    <tr> 
                      <td width=20></td>
                       <td colspan="4">과정명 :  <strong><font color="4F9AE6"><%=GetCodenm.get_subjnm(v_subj)%>[<%=v_subj%>](<%=StringManager.toInt(v_subjseqgr)%>차)</font></strong></td>
                      <td width=19></td>
                    </tr>
                    <tr> 
                      <td></td>
                      <td colspan="4">최근등록일 : <strong><font color="4F9AE6"><%=v_indate%></font></strong></td>
                      <td></td>
                    </tr>
                    <tr> 
                      <td></td>
                      <td colspan="4">등 록 파 일 : <strong><font color="4F9AE6"><%=v_realfile%></font></strong></td>
                      <td></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
            </table></td>
        </tr>
      </table>
      <br>
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="413"> <input name="p_file" type="FILE" class="input" style="width:400"> 
          </td>
          <td width="217"></td>
        </tr>
        <tr> 
          <td colspan="2" class=map_line></td>
        </tr>
        <tr>
          <td colspan="2" height=8></td>
        </tr>
      </table>
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="591" align="right"><a href="javascript:action('statusUpdate')"><img src="/images/admin/button/btn_save.gif"  border="0"></a></td>
          <td width="7"></td>
          <td width="32" align="right"><a href="javascript:history.back()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</form>
</body>
</html>
