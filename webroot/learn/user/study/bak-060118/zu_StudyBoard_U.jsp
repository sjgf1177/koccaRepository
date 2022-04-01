<%
//**********************************************************
//  1. 제      목: 과정게시판
//  2. 프로그램명: zu_StudyBoard_U.jsp
//  3. 개      요: 과정게시판 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 02. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

     DataBox dbox = (DataBox)request.getAttribute("selectStudyBoard");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        //v_content     = dbox.getString("d_content");
        content     = StringManager.replace(dbox.getString("d_content"),"\n;","<br>");   
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");

        v_title = StringManager.replace(v_title, "`", "'");
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");


%>
<!------- 파일확장자체크 INCLUDE ------------>       
<%@ include file = "/learn/library/fileFilter.jsp" %>
<html>
<head>
<title>::: 게시판 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript" src = "/script/cresys_lib.vb"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert() {
        //moveContent();

        if (blankCheck(document.form1.p_title.value)) {
            alert("제 목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제 목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

    	if(document.all.use_editor[0].checked) {        
            document.form1.p_content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.form1.p_content.value = document.all.txtDetail.value;
	    }

	    //파일 확장자 필터링  
	    var islimit = true;
	    
	    for(var i=1; i<=5; i++){
	        var file = eval("document.form1.p_file"+i+".value");

    	    if(file!="") {
    	        islimit = limitFile(file);
    	        
    	        if(!islimit) break;
    	    }	        
	    }
 	    	    	    	    
	    if(islimit) {
			document.form1.p_search.value     = "";
			document.form1.p_searchtext.value = "";
			document.form1.action = "/servlet/controller.study.StudyBoardServlet?p_file=5";
			document.form1.p_process.value = "update";
			document.form1.submit();
	    }else{
	        return;
	    }	
    }

   function cancel() {
        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
   }
    //namo editor 작성 내용값 옮기기
    function moveContent(){
        document.form1.p_content.value = document.form1.we.BodyValue;
    }

	//이미지 경로를 바꿔준다.
	function setImageNames(filenames,filepath) {
	   document.forms[0].DHTMLimages.value = filenames;
	   document.forms[0].DHTMLimagesPath.value = filepath;
	}
//-->
</SCRIPT>
<!-- SCRIPT FOR NAMO EDITOR -->
<SCRIPT LANGUAGE="VBScript">
<!--
    Function Load_Content()
            document.form1.we.BodyValue = document.form1.p_content.value
            document.form1.we.focus
    End Function

    Sub we_OnInitCompleted
        call Load_Content()
    End Sub

    Function Move_Content()
            document.form1.p_content.value = document.form1.we.BodyValue
    End Function
-->
</script>
</head>




<body topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">
    <input type = "hidden" name = "p_content"    value="">
    <input type = "hidden" name = "p_isedu"      value="1"><!--학습창임-->

<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
  <tr> 
    <td> 
      <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" class="body_color"> 
<br>
            <!----------------- 타이틀 시작 ----------------->
  <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    <tr> 
      <td><img src="/images/user/study/board_title.gif"></td>
      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      <td><img src="/images/user/study/gongi_tail.gif"></td>
    </tr>
  </table>
            <!----------------- 타이틀 끝 ---------------->
            <br>
            <!----------------- 게시판 글 등록 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
				  	<tr>
                        <td class="board_color_line" height=2></td>
                        <td class="board_color_line" height=2></td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="110">제 목</td>
                      <td class="table_text3"> 
                        <input size="80" maxlength="200" name="p_title" class="input"  value = `<%= v_title %>`>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="110">작성자</td>
                      <td class="table_text3"><%=v_name%>
                      </td>
                    </tr>
                      <tr> 
                        <td class="board_title_bg2">내 용</td>
                        <td class="table_text3" style=padding-left:8px><br> 
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->  
                          
                          </td>
                      </tr>
					  <tr>
                        <td class="board_title_bg2">첨부파일</td>
						<td class="table_text3">
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
			<%		}   
				    }
				}
		%>
						  <input type="FILE" name="p_file1" size="45" class="input"><br>
						  <input type="FILE" name="p_file2" size="45" class="input"><br>
						  <input type="FILE" name="p_file3" size="45" class="input"><br>
						  <input type="FILE" name="p_file4" size="45" class="input"><br>
						  <input type="FILE" name="p_file5" size="45" class="input"><br>
						</td>
					  </tr>

                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 게시판 글 등록 끝 ---------------->
            <br>
            <!----------------- 저장, 목록 버튼 시작 ----------------->
            <table  border="0" cellspacing="0" cellpadding="0" align="center" class="open_board_table_out1">
              <tr>
			    <td width=70%>&nbsp;</td>  
                <td align=right><a href="javascript:insert()"><img src="/images/user/button/btn_save.gif"  border="0"></a></td>
				<td witdh=5%></td>
                <td width=8% align=right><a href="javascript:cancel()"><img src="/images/user/button/btn_list.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장, 목록 버튼 끝 ----------------->
            <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<script LANGUAGE="javascript">

<MM:BeginLock translatorClass="MM_SSI" type="ssi" orig="%3C%25@ include file = %22/learn/library/getJspName.jsp%22 %25%3E" fileRef="/learn/library/getJspName.jsp" depFiles="file:///X|/autoever/learn/library/getJspName.jsp"><%
//**********************************************************
//  1. 제      목: 페이지명을 출력하는 include 화면
//  2. 프로그램명: getJspName.jsp
//  3. 개      요: 페이지명을 출력하는 include 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 09
//  7. 수      정: 
//**********************************************************
%>
<% 
        if(conf.getProperty("jsp.name.view").equals("true")) {      %>
<table>
    <tr><td>
            <%= request.getServletPath()%> <br><br>
            
    </td></tr>
</table>
<%  }   %>
    <MM:EndLock>

</form>
</body>
</html>
