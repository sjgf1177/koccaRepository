<%
//********************************************************************
//  1. 제      목: Object등록/수정화면
//  2. 프로그램명 : za_SCObject_I.jsp
//  3. 개      요: Object를 등록/수정하는 화면이다
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 3
//  7. 수      정:
//********************************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
    
    String	p_process	= box.getString("p_process");
    String	p_job		= box.getString("p_job");
    String	p_oid		= box.getString("p_oid");
    
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" src="/js/default.js"></script>
</head>

<script language="javascript">
function whenSubmit(){
    if (document.form1.p_sdesc.value == ""){
        alert("Short Description을 입력하십시오.");
        document.form1.p_sdesc.focus();
        return;
    }
	document.form1.p_process.value="insertSave";
	document.form1.submit();
}
function whenList(){
	document.form1.p_process.value="listPage";
	document.form1.submit();
}
function searchMember(){
  var p_key1  = document.form1.p_mastername.value;
  var p_key2  = 'p_master';
  var url = "/servlet/controller.library.SearchServlet?p_process=member&p_gubun=name" +"&p_key1="+ p_key1 +"&p_key2="+p_key2;
  open_window("",url,"0","0","100","100");
}
function receiveMember(userid, name, resno, email, cono, jikwi, jikwinm, comp, compnm, tmp1, tmp2, tmp3){
	document.form1.p_mastername.value =  name;
    document.form1.p_master.value = userid;  
}
</script>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
     <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
	          <!-------------소제목 시작-------------------------->
 <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>등록</td>
        </tr>
        <tr> 
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table> 
        <!--------------소제목 끝---------------------------->
      <!----------------- Object 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <FORM name="form1" method="post" action="/servlet/controller.contents.SCObjectServlet" enctype = "multipart/form-data">
      		<input type=hidden name="p_process" value="">
      		<input type="hidden" name="s_subj"   value="<%=box.getString("s_subj")%>">
      		<input type=hidden name="s_gubun"   value="<%=box.getString("s_gubun")%>">
      		<input type=hidden name="p_action" value="go">
      		<input type=hidden name="p_job" value="insert">
      		<input type=hidden name="p_master" value="">
        <tr> 
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>Object ID</b></td>
          <td class="table_02_2"></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>설명</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_sdesc" size="50" maxlength="100" value=""></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>비고</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_sdesc2" size="50" maxlength="100" value=""></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>총페이지수</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_npage" size="2" maxlength="2" value=""></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>시작 HTML</b></td>
          <td class="table_02_2"></td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>관리자</b></td>
          <td class="table_02_2">
          	<input type="text" class="input" name="p_mastername" size="30" maxlength="100" value="">
          	&nbsp;&nbsp;
          	<a href='javascript:searchMember()'><img src="/images/admin/button/b_searchb.gif" border="0" align=absmiddle></a>
          </td>
        </tr>
        <tr>
          <td class="table_title" width="18%"><b>업로드파일</b></td>
          <td class="table_02_2">
          	<input type="FILE" class="input" name="p_file1" size="50">&nbsp;&nbsp;
          	
          	<!--<a href='javascript'><img src="/images/admin/system/file_butt.gif" width="37" height="18" border="0" align=absmiddle></a>-->
          </td>
        </tr>
        
        

	  </FORM>	
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
      <!----------------- 저장 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="center" height="20"> 
          	<a href='javascript:whenSubmit()'><img src="/images/admin/button/btn_save.gif"  border="0"></a> 
          	<a href='javascript:whenList()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 저장 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</body>
</html>
<%@ include file = "/learn/library/getJspName.jsp" %>
