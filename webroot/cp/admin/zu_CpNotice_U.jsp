<%
//**********************************************************
//  1. 제      목: 외주관리 공지사항 수정화면
//  2. 프로그램명: zu_CpNotice_U.jsp
//  3. 개      요: 외주관리 공지사항 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 08. 09
//  7. 수      정: 이연정 2005. 08. 09
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
ArrayList list = (ArrayList)request.getAttribute("selectCpinfo");
String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "", s_gubun="";
String v_cpseq = "", v_cpnm = "", v_compcd	    = "";
String v_compnm = "", v_comp = "", v_loginyn="";




int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 
String v_searchtext = box.getString("p_searchtext");
String v_select     = box.getString("p_select");
int    v_pageno     = box.getInt("p_pageno");

Vector v_fileseqVector  = null;          //      저장된 파일번호 배열
Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion     = "";
String v_savemotion     = "";
String content  = "";
String width    = "650";
String height   = "200";
String s_usernm = box.getSession("name");

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid     = dbox.getString("d_userid");
v_name       = dbox.getString("d_adname");
v_indate     = dbox.getString("d_addate");
v_title      = dbox.getString("d_adtitle");
v_seq        = dbox.getInt("d_seq");
v_content    = dbox.getString("d_adcontent");
v_compcd     = dbox.getString("d_compcd");
v_loginyn    = dbox.getString("d_loginyn");
content      = v_content;
//v_content    = dbox.getString("d_content");

String s_cpseq      = box.getSession("cpseq");
String s_gadmin     = box.getSession("gadmin");

v_realmotion     = dbox.getString("d_realmotion");
v_savemotion     = dbox.getString("d_savemotion");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
//------------------------------------------------------------------------------------------------------

s_gubun = box.getString("p_gubun");

//대상회사
	int tmplen= 0;
	String compcd="'1'";
	if(!v_compcd.equals("")) {
		compcd=v_compcd.substring(0, v_compcd.length()-1);
		compcd=StringManager.replace(compcd, ",", "','");
		compcd="'"+compcd+"'";
	}
	
	///out.println("v_compcd===>>>>"+compcd);
	
	CpNoticeAdminBean bean = new CpNoticeAdminBean();
	ArrayList compList = bean.selectCompany(box, compcd);

%>

<html>
<head>
<title>외주관리 공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
//이전 내용을 불러오기
function initPage(){
    document.form1.Wec.Value = document.form1.conText.value;
}
//리스트 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.cp.CpNoticeAdminServlet";
    document.form1.p_process.value = "selectList";     
    document.form1.submit();	
}            
//리스트페페이지로 이동
function cancel() {
    document.form1.action = "/servlet/controller.cp.CpNoticeAdminServlet";
	document.form1.p_process.value = "select";
    document.form1.submit();
}
//내용 수정하기
function update() { 
    var chkselectcnt = chkSelected();
	document.form1.p_content.value = document.form1.Wec.MIMEValue;
    form1.p_content.value.replace("&","&amp;");

    if (blankCheck(document.form1.p_title.value)) {
        alert("제목을 입력하세요!");
        document.form1.p_title.focus();
        return;
    }                
    if (realsize(document.form1.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }
    if(chkselectcnt < 1){
        alert("회사를 선택하여주십시오");
        return;
    }
    if (document.form1.Wec.Value.length < 3) {
        alert("내용을 입력하세요!");
        document.form1.content.focus();
        return;
    }	    
    document.form1.action = "/servlet/controller.cp.CpNoticeAdminServlet";
	document.form1.p_process.value = "update";                
    document.form1.submit();
} 
//빈글자 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}
//글자 사이즈 체크
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
//체크박스 체크
function chkSelected() {
  var selectedcnt = 0;
  
  document.form1.p_compcd.value = "";
  
  if(document.form1.all['p_cpseq'] == '[object]') {
    if (document.form1.p_cpseq.length > 0) {
      for (i=0; i<document.form1.p_cpseq.length; i++) {
        if (document.form1.p_cpseq[i].checked == true) {
          selectedcnt++;
          document.form1.p_compcd.value += document.form1.p_cpseq[i].value+",";
        }
      }
    } else {
      if (document.form1.p_cpseq.checked == true) {
        selectedcnt++;
        document.form1.p_compcd.value += document.form1.p_cpseq.value;
      }
    }
  }
  return selectedcnt;
}

		//-->
</script>
</head>

<body topmargin=0 leftmargin=0 onload="initPage()">
<form name = "form1" enctype = "multipart/form-data" method = "post">
	<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"  value = "">
    <input type = "hidden" name = "p_select"      value = "">
    <input type = "hidden" name = "p_pageno"      value = "">
	<input type = "hidden" name = "p_process">
	<input type = "hidden" name = "p_gubun"       value = "<%=s_gubun%>">
    <input type = "hidden" name = "p_content"     value = "<%=v_content%>">
	<input type = "hidden" name = "p_upfilecnt"   value = "<%=v_upfilecnt %>">
    <input type = "hidden" name = "conText"       value = "<%=content%>">
	<input type = "hidden" name = "p_compcd"      value = "">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	   
      <br>

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">

       <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr>
          <td class="table_title" width="18%">제목</td>
          <td class="table_02_2"  width="82%">
		  <input type = "text" style="padding:1; border:1 solid #D1D1D1;" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "84" maxlength = "100" value = "<%= v_title %>"></td>
		  </td>
        </tr>
        <tr>
          <td class="table_title">작성자</td>
          <td class="table_02_2"><%= s_usernm %></td>
        </tr>

        <tr>
          <td class="table_title">로그인여부</td>
          <td class="table_02_2">
            <input type='radio' name='p_loginyn' value='Y' <%if(v_loginyn.equals("Y")){out.println("checked");}%>>로그인후
            <input type='radio' name='p_loginyn' value='N' <%if(v_loginyn.equals("N")){out.println("checked");}%>>로그인전
          </td>
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

		<input type="checkbox" name="p_cpseq" value="<%=v_cpseq%>" <%if(v_compcd.indexOf(v_cpseq) >-1 ){out.println("checked");}%>><%=v_cpnm%>
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
                <!-- 나모 Editor  -->
		  </td>
        </tr>
<% if( v_upfilecnt > 0 ) {%>

        <tr> 
          <td height="21" class="table_title"style="padding-top:10px">이전파일</td>
          <td class="table_02_2"><br>
			<%		
				if( v_realfileVector != null ) {
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
					String v_realfile = (String)v_realfileVector.elementAt(i);
					String v_savefile = (String)v_savefileVector.elementAt(i);  
					String v_fileseq = (String)v_fileseqVector.elementAt(i);  	
					
					if(v_realfile != null && !v_realfile.equals("")) {  %>
						&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
					
                        <%= v_realfile%></a> &nbsp;
						<input type = "checkbox"  name = "p_fileseq<%=i%>" value = "<%= v_fileseq%>"> (삭제시 체크)<br>
						<input type = "hidden" name = "p_realfile"  value = <%=v_realfile%>>
						<input type = "hidden" name = "p_savefile<%=i%>"  value ="<%=v_savefile%>">
			<%		}   %>
		<%			
				}}
		%><br>
            </td>
          </tr>
<%}%>

        <tr> 
          <td height="21" class="table_title"style="padding-top:10px">파일첨부</td>
          <td class="table_02_2" >
			  <input type="FILE" name="p_file1" size="85" class="input"><br>
			  <input type="FILE" name="p_file2" size="85" class="input"><br>
			  <input type="FILE" name="p_file3" size="85" class="input"><br>
			  <input type="FILE" name="p_file4" size="85" class="input"><br>
			  <input type="FILE" name="p_file5" size="85" class="input"><br>
		  </td>
        </tr>
		</td>
        </tr>
      </table>

      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height=6 ></td>
        </tr>
        <tr>
          <td  align="right">
              <a href="javascript:update()" onfocus=this.blur()>
              <img src="/images/admin/button/btn_save.gif" border="0" border = "0"></a>&nbsp;&nbsp;
              <a href="javascript:cancel();" onfocus=this.blur()>
              <img src="/images/admin/button/btn_list.gif" border="0"></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
</body>
</html>
