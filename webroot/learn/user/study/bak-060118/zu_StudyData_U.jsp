<%
//**********************************************************
//  1. 제      목: 과정자료실
//  2. 프로그램명: zu_StudyData_U.jsp
//  3. 개      요: 과정자료실 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 6
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
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

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    DataBox dbox = (DataBox)request.getAttribute("selectStudyData");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
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
<html>
<head>
<title>::: 자료방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert() {
        moveContent();

        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

        if (blankCheck(document.form1.p_content.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_content.focus();
            return;
        }
/*
        document.forms[0].p_content.editorApplet = DHTMLEdit.tbContentElement;
        document.forms[0].p_content.value = DHTMLEdit.getDocumentHTML();
        document.forms[0].InitHTML.value="";
        document.forms[0].addBody.value="";
*/
        document.form1.p_search.value     = "";
        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.study.StudyDataServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

   function cancel() {
        document.form1.action = "/servlet/controller.study.StudyDataServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
   }

    //namo editor 작성 내용값 옮기기
    function moveContent(){
        document.form1.p_content.value = document.form1.we.BodyValue;
    }
//-->
</SCRIPT>
</head>




<body topmargin="0" leftmargin="0">

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
</SCRIPT>
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">

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
      					<td><img src="/images/user/study/data_title.gif"></td>
      					<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      					<td><img src="/images/user/study/gongi_tail.gif"></td>
    				</tr>
  				</table>
            <!----------------- 타이틀 끝 ---------------->
            <br>


            <!----------------- 게시판 글 등록 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out">
              <tr>
                <td width="3" class="board_color_line"></td>
                <td>
                  <table cellspacing="1" cellpadding="1" class="table2">
                    <tr>
                      <td class="board_title_bg2" width="150">제 목</td>
                      <td class="table_text3">
                        <input size="80" maxlength="200" name="p_title" class="input"  value = `<%= v_title %>`>
                      </td>
                    </tr>
                    <tr>
                      <td class="board_title_bg2" width="150">작성자</td>
                      <td class="table_text3"><%=v_name%>
                      </td>
                    </tr>
                    <tr>
                      <td class="board_title_bg2">내 용</td>
                      <td class="table_text3">
                              <OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                                <PARAM NAME="LPKPath" VALUE="/learn/namo/NamoWec4_Namo.lpk">
                                </OBJECT>
                                <OBJECT ID="we" WIDTH="580" HEIGHT="300" CLASSID="CLSID:C5AC95C0-C873-445B-BA1F-3A430A4DEA42" CODEBASE="/learn/namo/NamoWec.cab#version=4,0,0,17">
                                  <param name="InitFileURL" value="/learn/namo/namowec.env">
                                </OBJECT>
                                <DIV id="tmpHTML" style="visibility:hidden; display:none">
                                    <TEXTAREA NAME="p_content" type=hidden ><%= v_content%></TEXTAREA>
                                </DIV>
                      </td>
                    </tr>

                    <!---------파일첨부여부 ------------>
                    <%  if(box.getBoolean("p_isUpload")) {      %>
                    <tr>
                        <td class = "board_title_bg2" width = "20%" align = "center">첨부파일</td>
                        <td class = "table_text3" width = "80%" align = "left" colspan="3">
                        <% 
                                if (v_realfileVector !=null ) {
                                    for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                                        String v_realfile = (String)v_realfileVector.elementAt(i);
                                        String v_savefile = (String)v_savefileVector.elementAt(i);                              
                                        String v_fileseq  = (String)v_fileseqVector.elementAt(i);                               
                                        if(v_realfile != null && !v_realfile.equals("")) { 
                        %>
                                        &nbsp;<%= v_realfile%>
                                        &nbsp;<input type = "checkbox"  onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_savefile" value =<%=v_savefile%>> (삭제시 체크)
                                        <br>
                                        <input type = "hidden" name = "p_realfile"  value = "<%= v_realfile%>">
                        <%              }  
                                    }
                                }// end of if v_realfileVector != null 
                        %>
                        </td>
                    </tr>
                    <tr>
                        <td class = "board_title_bg2" width = "20%" align = "center">파일첨부</td>
                        <td class = "table_text3" width = "80%" align = "left">
                        <input type = "file" size="65"  name = "p_file"><br>
                        </td>
                    </tr>
                    <%  }           %>
                    <!---------파일첨부여부 끝 ------------>

                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 게시판 글 등록 끝 ---------------->
            <br>


            <!----------------- 저장, 목록 버튼 시작 ----------------->
            <table width="150" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr>
                <td align="center">
                <a href="javascript:insert()"><img src="/images/user/button/btn_save.gif"  border="0"></a></td>
                <td align="center"><a href="javascript:cancel()"><img src="/images/user/button/btn_list.gif" border="0"></a></td>
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
<%@ include file = "/learn/library/getJspName.jsp" %>

</form>
</body>
</html>
