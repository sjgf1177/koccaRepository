<%
//**********************************************************
//  1. 제      목: 교재관리 수정
//  2. 프로그램명 : za_Book_U.jsp
//  3. 개      요: 교재관리 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    int v_pageno         = box.getInt("p_pageno");

    String  ss_classify    = box.getString("s_classify");
    String  v_searchtext   = box.getString("p_searchtext");
    String  ss_action      = box.getString("s_action");
    String v_bookcode      = box.getString("p_bookcode");          // 교재코드

    String v_classify      = "";          // 분류
    String v_codenm        = "";          // 분류명
    String v_bookname      = "";          // 교재명
    int    v_price         = 0;           // 정가
    int    v_dis_price     = 0;           // 판매가
    String v_intro         = "";          // 교재소개
    String v_writer        = "";          // 저자소개
    String v_content       = "";          // 목차
    String v_review        = "";          // 리뷰
    String v_recom         = "";          // 추천교재여부
    String v_luserid       = "";          // 등록자
    String v_ldate         = "";          // 등록일
    String v_realfilename1 = "";          // 큰이미지   실제파일명
    String v_newfilename1  = "";          // 큰이미지   저장파일명
    String v_realfilename2 = "";          // 작은이미지 실제파일명
    String v_newfilename2  = "";          // 작은이미지 저장파일명
    String v_book_site     = "";          // 판매처
    String v_book_url      = "";          // 판매 주소(URL)
    String v_author        = "";          // 저자
    String v_isbn          = "";          // ISBN

    DataBox dbox = (DataBox)request.getAttribute("selectBook");
    
    if (dbox != null) {    
        v_classify      = dbox.getString("d_classify");
        v_codenm        = dbox.getString("d_codenm");
        v_bookname      = dbox.getString("d_bookname");
        v_price         = dbox.getInt   ("d_price");
        v_dis_price     = dbox.getInt   ("d_dis_price");
        v_intro         = dbox.getString("d_intro");
        v_writer        = dbox.getString("d_writer");
        v_content       = dbox.getString("d_content");
        v_review        = dbox.getString("d_review");
        v_recom         = dbox.getString("d_recom");
        v_luserid       = dbox.getString("d_luserid");
        v_ldate         = dbox.getString("d_ldate");
        v_realfilename1 = dbox.getString("d_realfilename1");
        v_newfilename1  = dbox.getString("d_newfilename1");
        v_realfilename2 = dbox.getString("d_realfilename2");
        v_newfilename2  = dbox.getString("d_newfilename2");
        v_book_site     = dbox.getString("d_book_site");
        v_book_url      = dbox.getString("d_book_url");
        v_author        = dbox.getString("d_author");
        v_isbn          = dbox.getString("d_isbn");

     }
%>

<SCRIPT LANGUAGE="JavaScript">
<!--

    //입력
    function update() {
        if (blankCheck(document.form1.p_bookname.value)) {
            alert("교재명을 입력하세요");
            document.form1.p_bookname.focus();
            return;
        }

        if (blankCheck(document.form1.p_author.value)) {
            alert("저자명을 입력하세요");
            document.form1.p_author.focus();
            return;
        }

        if (realsize(document.form1.p_bookname.value) > 200) {
            alert("교재명은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_bookname.focus();
            return;
        }

        /*if (!number_chk_noalert(document.form1.p_price.value)) {
            alert('정가가 잘못입력되었습니다.');
            return ;
        }

        if (!number_chk_noalert(document.form1.p_dis_price.value)) {
            alert('판매가가 잘못입력되었습니다.');
            return ;
        }*/

        if (blankCheck(document.form1.p_book_site.value)) {
            alert("판매처를 입력하세요");
            document.form1.p_book_site.focus();
            return;
        }

        if (blankCheck(document.form1.p_book_url.value)) {
            alert("판매처 주소(URL)를 입력하세요");
            document.form1.p_book_url.focus();
            return;
        }

        var booksurl = document.form1.p_book_url;

        if (booksurl.value.indexOf("http://") == -1 && booksurl.value.indexOf("HTTP://") == -1 ) {
            booksurl.value = "http://" + booksurl.value;
        } 

        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.book.BookAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.book.BookAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    //파일첨부에 쓰기 방지
    function File_CancelKeyInput()
    {
        if(event.keyCode == 9){ // Tab key만 허용
            return true;
        }else{
            alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.'); 
            return false;
        }
    }
//-->
</SCRIPT>

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%=v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="s_action"     value = "<%=ss_action %>">
    <input type = "hidden" name="s_classify"   value = "<%=ss_classify %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_bookcode"   value = "<%=v_bookcode %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
          <tr>
            <td><img src="/images/admin/portal/s.1_22.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>교재코드</strong></td>
            <td height="25" class="table_02_2"><%=v_bookcode%></td>
            <td width="15%" class="table_title"><strong>분류</strong></td>
            <td height="25" class="table_02_2"><%=CodeConfigBean.getCodeGubunSelect ("0073", "", 1, "p_classify", v_classify, "", 0)%></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>교재명</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_bookname" size="50" class="input" maxlength="50" value="<%=v_bookname%>"></td>
            <td width="15%" class="table_title"><strong>저자명</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_author" size="55" class="input" maxlength="50" value="<%=v_author%>"></td>
          </tr>
          <!--tr> 
            <td width="15%" class="table_title"><strong>정가</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_price" size="10" class="input" maxlength="10" value="<%=v_price%>"></td>
            <td width="15%" class="table_title"><strong>판매가</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_dis_price" size="10" class="input" maxlength="10" value="<%=v_dis_price%>"></td>
          </tr-->
          <tr> 
            <td width="15%" class="table_title"><strong>판매처</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_book_site" size="50" class="input" maxlength="200" value="<%=v_book_site %>"></td>
            <td width="15%" class="table_title"><strong>ISBN</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_isbn" size="55" class="input" maxlength="200" value="<%=v_isbn %>"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>판매 URL</strong></td>
            <td height="25" class="table_02_2" colspan="3"><input type="text" name="p_book_url" size="125" class="input" maxlength="200" value="<%=v_book_url %>"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>교재소개</strong></td>
            <td height="25" class="table_02_2" colspan="3"><textarea name="p_intro" style="width:100%" cols="110" rows="10"><%=v_intro%></textarea></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>저자소개</strong></td>
            <td height="25" class="table_02_2" colspan="3"><textarea name="p_writer" style="width:100%" cols="110" rows="10"><%=v_writer%></textarea></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>목차</strong></td>
            <td height="25" class="table_02_2" colspan="3"><textarea name="p_content" style="width:100%" cols="110" rows="10"><%=v_content%></textarea></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>리뷰</strong></td>
            <td height="25" class="table_02_2" colspan="3"><textarea name="p_review" style="width:100%" cols="110" rows="10"><%=v_review%></textarea></td>
          </tr>
          <tr>
            <td width="16%" class="table_title"><b>교재이미지<br>( 133 X 152 )</b></td>
            <td width="81%" class="table_02_2" colspan="3">
<%      if(v_realfilename2 != null && !v_realfilename2.equals("")) {        %>
                <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_newfilename2%>&p_realfile=<%= v_realfilename2%>'><%= v_realfilename2%></a>&nbsp;
				<input type = "checkbox"  name = "p_file20" value = "1"> (삭제시 체크) &nbsp;&nbsp;&nbsp; <font color="blue">    첨부된 파일이 있을시 체크 하지않아도 새로 첨부된 파일로 변경됩니다. </font><br>
              <input type = "hidden" name = "p_file21"  value = "<%=v_realfilename2%>">
              <input type = "hidden" name = "p_file22"  value = "<%=v_newfilename2%>">
 <%      }                                                    %>
              <input type="FILE" name="p_file2" size="60" class="input"  onkeydown="javascript:return File_CancelKeyInput()"> <strong>( 53 X 79 )</strong> <br>
            </td>
          </tr>
          <!--tr>
            <td width="16%" class="table_title"><b>상세이미지파일<br>( 150 X 220 )</b></td>
            <td width="81%" class="table_02_2" colspan="3">
<%      if(v_realfilename1 != null && !v_realfilename1.equals("")) {        %>
                <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_newfilename1%>&p_realfile=<%= v_realfilename1%>'><%= v_realfilename1%></a>&nbsp;
                <input type = "checkbox"  name = "p_file10" value = "1"> (삭제시 체크) &nbsp;&nbsp;&nbsp;  <font color="blue">    첨부된 파일이 있을시 체크 하지않아도 새로 첨부된 파일로 변경됩니다. </font><br>
              <input type = "hidden" name = "p_file11"  value = "<%=v_realfilename1%>">
              <input type = "hidden" name = "p_file12"  value = "<%=v_newfilename1%>">
 <%      }                                                    %>
              <input type="FILE" name="p_file1" size="60" class="input"  onkeydown="javascript:return File_CancelKeyInput()"> <strong>( 150 X 220 )</strong> <br>
            </td>
          </tr-->
          <!--tr> 
            <td width="15%" class="table_title"><strong>추천과정</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <select name= "p_recom">
                <option value="N" <% if (v_recom.equals("N")){%>selected<%}%>>N</option>
                <option value="Y" <% if (v_recom.equals("Y")){%>selected<%}%>>Y</option>
              </select>
            </td>
          </tr-->
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->
        <br>

        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:update()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

</body>
</html>