<%
//**********************************************************
//  1. 제      목: 과정체계도
//  2. 프로그램명: za_CourseMap_U.jsp
//  3. 개      요: 과정체계도 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 7. 28 이연정
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

	int    v_tabseq     = dbox.getInt("d_tabseq");
	String v_tabseqname = dbox.getString("d_tabseqname");
	String v_realpdf    = dbox.getString("d_realpdf");
	String v_savepdf    = dbox.getString("d_savepdf");
	String v_realsfile  = dbox.getString("d_realsfile");
	String v_savesfile  = dbox.getString("d_savesfile");
	String v_reallfile  = dbox.getString("d_reallfile");
	String v_savelfile  = dbox.getString("d_savelfile");
	String v_luserid 	= dbox.getString("d_luserid");
	String v_ldate    	= dbox.getString("d_ldate");	
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
    document.form1.action = "/servlet/controller.homepage.HomePageCourseMapAdminServlet";
    document.form1.p_process.value = "selectList";     
    document.form1.submit();	
}  

 //데이터를 입력한다.
function insert() {
    if (blankCheck(document.all.p_tabseqname.value)) {
        alert("분류명을 입력하세요!");
        document.all.p_tabseqname.focus();
        return;
    }
    if (blankCheck(document.all.p_pdffile.value)) {
        alert("pdf 파일을 입력하세요!");
        document.all.p_pdffile.focus();
        return;
    }
    if (blankCheck(document.all.p_sfile.value)) {
        alert("이미지 파일을 입력하세요!");
        document.all.p_sfile.focus();
        return;
    }
    if (blankCheck(document.all.p_lfile.value)) {
        alert("이미지 파일을 입력하세요!");
        document.all.p_lfile.focus();
        return;
    }

    document.form1.action = "/servlet/controller.homepage.HomePageCourseMapAdminServlet";
	document.form1.p_process.value = "insert";
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
function realsize( value ) {//글자수를 체크
    var len = 0;
    if ( value == null ) return 0;
    for(var i=0;i<value.length;i++){
        var c = escape(value.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;
    }
    return len;
}


</script>

</head>

<body topmargin=0 leftmargin=0>
<form  name = "form1"  method = "post" enctype = "multipart/form-data">
<input type = "hidden" name = "p_process"     value = "">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">

        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/homepage/homepage_title13.gif"></td>
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
          <td class="table_title">분류명</td>
          <td class="table_02_2"><input type="text" name = "p_tabseqname" value="<%=v_tabseqname%>" size="50" maxlength ="50"></td>
        </tr>
        <tr>
          <td class="table_title">pdf 파일명</td>
          <td class="table_02_2"><input type="file" name="p_pdffile" size="45" class="input" value="<%=v_pdffile%>"></td>
        </tr>
        <tr>
          <td class="table_title">작은이미지파일명</td>
          <td class="table_02_2"><input type="file" name="p_sfile" size="45" class="input" value="<%=v_sfile%>"></td>
        </tr>
        <tr>
          <td class="table_title">큰이미지파일명</td>
          <td class="table_02_2"><input type="file" name="p_lfile" size="45" class="input" value="<%=v_lfile%>"></td>
        </tr>
      </table>

      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
			<!--등록하기-->
			<td align = "right"><a href="javascript:insert();"><img src="/images/user/button/btn_register.gif" border="0"></a> &nbsp;&nbsp;
			<!--취소하기-->
			<a href="javascript:selectList();"><img src="/images/user/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
