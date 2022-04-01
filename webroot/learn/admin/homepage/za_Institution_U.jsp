<%
//**********************************************************
//  1. 제      목: 시설관리
//  2. 프로그램명: za_Institution_U.jsp
//  3. 개      요: 시설관리 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 08. 03 이연정
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
	String s_userid   = box.getSession("userid");

	DataBox dbox = (DataBox)request.getAttribute("selectUpdate");

	int    v_no        = dbox.getInt("d_no");
	String v_code      = dbox.getString("d_code");
	String v_insname   = dbox.getString("d_insname");
	String v_realfile  = dbox.getString("d_realfile");
	String v_savefile  = dbox.getString("d_savefile");
	String v_url	   = dbox.getString("d_url");
	String v_luserid   = dbox.getString("d_luserid");
	String v_ldate     = dbox.getString("d_ldate");	
%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="JavaScript"></script>
<SCRIPT LANGUAGE="javascript">

//리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.InstitutionAdminServlet";
    document.form1.p_process.value = "select";     
    document.form1.submit();	
}  

//삭제
function del() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.InstitutionAdminServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

 //데이터를 입력한다.
function update() {
    if (blankCheck(document.all.p_insname.value)) {
        alert("시설명을 입력하세요!");
        document.all.p_insname.focus();
        return;
    } else if (blankCheck(document.all.p_url.value)) {
        alert("URL을 입력하세요!");
        document.all.p_url.focus();
        return;
    }
    document.form1.action = "/servlet/controller.homepage.InstitutionAdminServlet";
	document.form1.p_process.value = "update";
    document.form1.submit();
}

//입력 내용의 공백을 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}



</script>

</head>

<body topmargin=0 leftmargin=0>
<form  name = "form1"  method = "post" enctype = "multipart/form-data">


<input type = "hidden" name = "p_no"         value = "<%=v_no%>">
<input type = "hidden" name = "p_process"    value = "">
<input type = "hidden" name = "p_code"       value = "<%=v_code%>">
<input type = "hidden" name = "p_realfile"   value = "<%=v_realfile%>">
<input type = "hidden" name = "p_savefile"   value = "<%=v_savefile%>">
<input type = "hidden" name = "p_luserid"    value = "<%=v_luserid%>">
<input type = "hidden" name = "p_ldate"      value = "<%=v_ldate%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">

        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/homepage/h_title18.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
       <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr>
          <td class="table_title" width="18%">작성자</td>
          <td class="table_02_2"  width="82%"><%=s_userid%></td>
        </tr>
			<tr>
			  <td class="table_title">시설명</td>
			  <td class="table_02_2"><input type="text" name = "p_insname" value="<%=v_insname%>" size="50" maxlength ="50"></td>
			</tr>
			<tr>
			  <td class="table_title">파일명</td>
			  <td class="table_02_2"><input type="file" name="s_file" size="45" class="input" value="">&nbsp;<b><%=v_realfile%></b></td>
			</tr>
			<tr>
			  <td class="table_title">URL</td>
			  <td class="table_02_2"><input type="text" name="p_url" size="60" class="input" maxlength="200" value="<%=v_url%>"></td>
			</tr>
      </table>

      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
			<!--등록하기-->
			<td align = "right"><a href="javascript:update();"><img src="/images/admin/button/btn_apply.gif" border="0"></a> &nbsp;&nbsp;
			<a href="javascript:del();"><img src="/images/admin/button/btn_del.gif" border="0"></a> &nbsp;&nbsp;

			<!--취소하기-->
			<a href="javascript:selectList();"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
