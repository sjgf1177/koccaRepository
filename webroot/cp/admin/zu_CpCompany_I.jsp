<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 회사별게시판 등록화면
//  2. 프로그램명: zu_CpCompany_I.jsp
//  3. 개      요: 외주관리 시스템 회사별게시판 등록화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 27
//  7. 수      정: 이연정 2005. 07. 27
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

ArrayList list       = (ArrayList)request.getAttribute("selectCpinfo");
DataBox dbox = null;
String v_title = "", v_content = "", v_userid = "", v_regdt = "";
String   v_cpnm = "", v_cpseq = ""; 
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_seq        = box.getInt("p_seq");
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");
String v_select     = box.getString("p_select");
String v_kind       = box.getString("p_kind");
String s_usernm     = box.getSession("name");
String s_gadmin     = box.getSession("gadmin");
//String v_cpseq      = box.getString("p_cpseq");
String s_cpseq      = box.getSession("cpseq");
String content = "";
String width   = "650";
String height  = "200";
%>

<html>
<head>
<title>회사별 게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
//입력 취소할때
function cancel() {
    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value = "selectList";      
    document.form1.p_searchtext.value = "<%= v_searchtext %>";   
    document.form1.p_select.value = "<%= v_select %>";   
    document.form1.p_pageno.value = "<%= v_pageno %>";     
    document.form1.submit();	
}         
//게시물 등록할때               
function insert(s_cpseq) { 
	var count = 0;

    if (blankCheck(document.all.p_title.value)) {
        alert("제목을 입력하세요!");
        document.all.p_title.focus();
        return;
    }                
    if (realsize(document.all.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.all.p_title.focus();
        return;
    }
	
	document.form1.p_content.value = document.form1.Wec.MIMEValue;
	document.form1.p_content.value.replace("&","&amp;");

	if (document.form1.Wec.Value.length < 3) {
		//alert(document.form1.Wec.Value.length);
		alert("내용을 입력하세요");
		return;
	}
	if(s_cpseq==''){

		for( i=0;i<document.form1.p_cpseq.length;i++){
			if(document.form1.p_cpseq[i].checked ==true) count++;}

			if(count==0){
				alert("업체명을 선택하세요");
			return;
			}
	}

    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}
//공백체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}
//실제 창사이즈로 ...
function realsize( value ) {
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
<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
		<input type = "hidden" name = "p_searchtext"  value = "">
		<input type = "hidden" name = "p_select"      value = "">
		<input type = "hidden" name = "p_pageno"      value = "">
		<input type = 'hidden' name = "p_content"     value = "<%=v_content%>">
		<input type = "hidden" name = "p_process">
		<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
		<input type = "hidden" name = "p_type"        value = "AF">
		<input type = "hidden" name = "p_kind"      value = "0">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/cp/cp_title09.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
       <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr>
          <td class="table_title" width="18%">제목</td>
          <td class="table_02_2"  width="82%"><input type = "text" style="padding:1; border:1 solid #D1D1D1;" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
										name = "p_title" size = "84" maxlength = "100" value = ""></td>
        </tr>
        <tr>
          <td class="table_title">작성자</td>
          <td class="table_02_2"><%= s_usernm %></td>
        </tr>
		<%
		//if(s_gadmin.equals("A1") || s_gadmin.equals("A2") || s_gadmin.equals("H1")){
			if("".equals(s_cpseq)){

		%>

        <tr>
          <td class="table_title">업체명</td>
          <td class="table_02_2">
		  <%
		  		for( int i = 0; i < list.size(); i++) {

					dbox     = (DataBox)list.get(i);  
					v_cpseq  = dbox.getString("d_cpseq");
					v_cpnm   = dbox.getString("d_cpnm");
		%>

		<input type='checkbox' name='p_cpseq' value='<%=v_cpseq%>'><%=v_cpnm%>
			<%}%>
		  </td>
		
        </tr>
		<%}else{%>
		<input type = "hidden" name = "p_cpseq" value ="<%=s_cpseq%>">
		<%}%>
        <tr>
          <td class="table_title">내용</td>
          <td class="table_02_2">
                <!-- 나모 Editor  -->
                <p align="left">
                <OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
                </OBJECT>
                <OBJECT ID="Wec" WIDTH="600" HEIGHT="350"
                CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
                <param name="InitFileURL" value="/namo/namowec.env">
                <param name="InitFileVer" value="1.0">
                </OBJECT>
                </p>
                <!-- 나모 Editor  -->

		  
		  </td>
        </tr>
        <tr>
          <td class="table_title">파일첨부</td>
          <td class="table_02_2">
			<input type="FILE" name="p_file1" size="85" class="input"><br>
			<input type="FILE" name="p_file2" size="85" class="input"><br>
			<input type="FILE" name="p_file3" size="85" class="input"><br>
			<input type="FILE" name="p_file4" size="85" class="input"><br>
			<input type="FILE" name="p_file5" size="85" class="input"><br>
		  </td>
        </tr>
      </table>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
			<!--저장하기-->
			<td align = "right"><a href = "javascript:insert('<%=s_cpseq%>');"><img src="/images/admin/button/btn_save.gif" border="0"></a> &nbsp;&nbsp;
			<!--취소하기-->
			<a href = "javascript:cancel();"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>

      
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
