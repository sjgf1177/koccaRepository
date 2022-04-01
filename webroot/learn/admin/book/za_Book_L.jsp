<%
//**********************************************************
//  1. 제      목: 교재관리
//  2. 프로그램명 : za_Book_L.jsp
//  3. 개      요: 교재관리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    int row              = 10 ;
    if (v_pageno == 0)  v_pageno = 1;

    String  ss_classify   = box.getString("s_classify");
    String  v_searchtext  = box.getString("p_searchtext");
    String  ss_action     = box.getString("s_action");

    int    i = 0;
    String v_bookcode      = "";          // 교재코드
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
    int    v_cnt           =  0;          // 조회수            


    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = null;
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectBookList");
    }
%>

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.book.BookAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.book.BookAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    //상세보기
    function view(bookcode) {
        document.form1.action = "/servlet/controller.book.BookAdminServlet";
        document.form1.p_bookcode.value = bookcode;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }
    // 입력
    function insert() {
        document.form1.action = "/servlet/controller.book.BookAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 조회
    function whenSelection(action) {
        document.form1.p_pageno.value = 1;
        document.form1.s_action.value = action;
        document.form1.action = "/servlet/controller.book.BookAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"   value = "<%=v_pageno %>">
    <input type = "hidden" name = "s_action"   value = "<%=ss_action %>">
    <input type = "hidden" name = "p_bookcode" value = "">

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

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        분류 <%=CodeConfigBean.getCodeGubunSelect ("0073", "", 1, "s_classify", ss_classify, "onChange=\"javascript:whenSelection('change')\"", 2)%>
                      </td>
                      <td width="73%" colspan="2">
                        교재명 <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
                      </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->

      <br>
      <br>
        <table width="97%" height="26" border="0" cellpadding="2" cellspacing="0">
          <tr>
            <td width="250">
            </td>
            <td width="610" align="right">&nbsp; </td>
            <td width="160" align="right" valign="middle">
                <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <!----------------- 교재 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="8" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%"  height="25"><b>NO</b></td>
            <td class="table_title" width="10%"><b>분류</b></td>
            <td class="table_title" width="7%"><b>코드</b></td>
            <td class="table_title" width="40%"><b>교재명</b></td>
            <!--td class="table_title" width="10%"><b>정가</b></td>
            <td class="table_title" width="10%"><b>판매가</b></td-->
            <td class="table_title" width="20%"><b>판매처</b></td>
            <td class="table_title" width="10%"><b>등록일</b></td>
            <!--td class="table_title" width="8%" ><b>추천여부</b></td-->
            <td class="table_title" width="8%" ><b>조회수</b></td>
          </tr>
<%
        if (ss_action.equals("go")) { 
            for( i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_bookcode      = dbox.getString("d_bookcode");
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
                v_cnt           = dbox.getInt("d_cnt");

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpagecount");

                v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");

%>
          <tr>
            <td class="table_01" ><%=v_dispnum %></td>
            <td class="table_01" ><%=v_codenm %></td>
            <td class="table_02_1"><%=v_bookcode%></a></td>
            <td class="table_02_2"><a href="javascript:view('<%=v_bookcode%>')"><%=v_bookname%></td>
            <!--td class="table_02_1"><%=v_price%></td>
            <td class="table_02_1"><%=v_dis_price%></td-->
            <td class="table_02_1"><%=v_book_site%></td>
            <td class="table_02_1"><%=v_ldate%></td>
            <td class="table_02_1"><%=v_cnt%></td>
          </tr>
<%
            }

        if(i == 0 ) {
%>
          <tr>
            <td class="table_02_1" height="100" colspan="7" align="center"> 등록된 데이터가 없습니다. </td>
          </tr>
<%
        }
    }
%>
        </table>
        <!----------------- 교재 관리 끝 ----------------->

        <!----------------- 페이징 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
            </td>
          </tr>
        </table>
        <!----------------- 페이징 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
