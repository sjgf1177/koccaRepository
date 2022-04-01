<%
//**********************************************************
//  1. 제      목: 교육과정 입과인원조회
//  2. 프로그램명: za_cpeduApprovaluser_l.jsp
//  3. 개      요: 수강확정자명단 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004.12.26
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
String v_subj = "", v_subjnm = "", v_cpnm = "", v_cpsubjseq = "", v_cpsubj = "";
String v_subjseq = "", v_propstart = "", v_propend = "", v_edustart = "", v_eduend = "";
String v_userid = "", v_name = "", v_email = "", v_comptel = "", v_handphone = "", v_eduurl = "", v_compnm="", v_cpeduurl="";

ArrayList list = (ArrayList)request.getAttribute("selectApprovalUserList");

String v_searchtext = box.getString("p_searchtext");
int v_pageno      = box.getInt("p_pageno");
String v_grcode   = box.getString("p_grcode");
String v_gyear    = box.getString("p_gyear");
String v_grcomp   = box.getString("p_grcomp");
String v_grseq    = box.getString("p_grseq");
String v_year     = box.getString("p_year");
String v_cp       = box.getString("p_cp");

v_subj =  box.getString("p_subj");
v_subjseq =  box.getString("p_subjseq");
v_cpsubjseq =  box.getString("p_cpsubjseq");


//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript">
//목록으로
function MoveLink(act) {
	if(act == "1"){
		//수강확정자 명단
		document.form1.p_process.value   = "";
     	document.form1.p_action.value = "go";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
	    document.form1.target = "_self";
	    document.form1.submit();	
	}
	else if(act == "2"){
		//명단 다운로드
		document.form1.p_process.value   = "excelDown";
	    document.form1.action            = "/servlet/controller.cp.CPEduStudentServlet";
	    document.form1.p_downgubun.value = "1";
	    document.form1.target = "_self";
	    document.form1.submit();	
	}
}


// 레포팅출력
function whenExcel() {
	window.self.name = "CpStudentSelectExcel";
	open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
	document.form1.target = "openExcel";
	document.form1.p_process.value = "excelDown";
	document.form1.action='/servlet/controller.cp.CPEduStudentServlet';
	document.form1.submit();
}


//미리보기
function edulink(elink){
	window.open(elink,'','');
	return;
}
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form1" action="" method="post">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_action" value="go">
<input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
<input type="hidden" name="s_grcode" value="<%=v_grcode%>">
<input type="hidden" name="s_gyear" value="<%=v_gyear%>">
<input type="hidden" name="s_grseq" value="<%=v_grseq%>">
<input type="hidden" name="s_grcomp" value="<%=v_grcomp%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
<input type="hidden" name="p_cp" value="<%=v_cp%>">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_downgubun" value="">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>과정명 : <%=GetCodenm.get_subjnm(v_subj)%></td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	          <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_title" width="5%">번호</td>
          <td class="table_title"  width="15%">소속</td>
          <td class="table_title"  width="15%">사번</td>
          <td class="table_title"  width="15%">성명</td>
          <td class="table_title"  width="20%">E-mail</td>
          <td class="table_title"  width="10%">사무실전화</td>
          <td class="table_title"  width="12%">휴대전화</td>
          <td class="table_title"  width="8%">링크</td>
        </tr>
<%
	for(i = 0; i < list.size(); i++) {

        DataBox dbox = (DataBox)list.get(i);  

		//sql  = "select a.userid, a.name, a.email, a.handphone, c.subjnm, c.eduurl, c.cpsubj ";
        
        v_dispnum = dbox.getInt("d_dispnum");
        v_userid = dbox.getString("d_userid");
        v_name   = dbox.getString("d_name");
        v_subjnm   = dbox.getString("d_subjnm");
        v_email    = dbox.getString("d_email");
        v_comptel   = dbox.getString("d_comptel");
        v_handphone = dbox.getString("d_handphone");
        v_eduurl    = dbox.getString("d_eduurl");
        v_cpsubj    = dbox.getString("d_cpsubj");
        v_compnm    = dbox.getString("d_compnm");
        v_cpeduurl  = dbox.getString("d_cpeduurl");
        
        if(!v_eduurl.equals("")){
        	v_eduurl = "<a href=\"javascript:edulink('" + v_eduurl + "?FIELD1=" + v_userid + "&FIELD2=" + v_name + "&FIELD3=" + v_cpsubj + "&FIELD4=" + v_cpsubjseq + "')\"><img src=\"/images/admin/button/b_link.gif\"  border=\"0\"></a>";
        }
        else
        	v_eduurl = "-";

        if(!v_cpeduurl.equals("")){
        	v_cpeduurl = "<a href=\"javascript:edulink('" + v_cpeduurl + "')\"><img src=\"/images/admin/button/b_link.gif\"  border=\"0\"></a>";
        }
        else
        	v_cpeduurl = "-";
%>
        <tr> 
          <td class="table_02_1"  ><%=v_dispnum%></td>
          <td class="table_02_1"  ><%=v_compnm%></td>
          <td class="table_02_1"  ><%=v_userid%></td>
          <td class="table_02_1"  ><%=v_name%></td>
          <td class="table_02_1"  ><%=v_email%></td>
          <td class="table_02_1"  ><%=v_comptel%></td>
          <td class="table_02_1"  ><%=v_handphone%></td>
          <td class="table_02_1"  ><%=v_cpeduurl%></td>
        </tr>
<%
	}
%>
      </table> 
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="10" height=6></td>
          <td align="right" height=6></td>
        </tr>
        <tr> 
          <td width="896">[ 총 <%=box.getString("d_totalrow")%> 명 ]</td>
          <td width="32" align="right"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excel.gif" border="0"></a></td>
          <td width="10">&nbsp;</td>
          <td width="32" align="right"><a href="javascript:MoveLink(1)"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table></td>
  </tr>
  <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %> 
    </td>
  </tr>
</table>
</form>
</body>
</html>
