<%
//**********************************************************
//  1. 제      목: 개설예정과정게시판
//  2. 프로그램명 : za_PreCourse_L.jsp
//  3. 개      요: 개설예정과정게시판 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int v_seq = 0;
    int v_cnt = 0;
    int i     = 0;

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";

    int v_dispnum   = 0; // 총게시물수
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int v_tabseq    = box.getInt("p_tabseq");
    int v_pageno    = box.getInt("p_pageno");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    ArrayList list = (ArrayList)request.getAttribute("list");
%>


<html>
<head>
<title> Untitled Document </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">



<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">

<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
	//조건절 조회
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    //페이지 이동
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    //상세화면 조회
    function selectList() {
        document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        document.form1.p_process.value = "viewPage";
        document.form1.submit();
    }
    //입력화면 이동
    function insertPage() {
        document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    //리스트 조회
    function select(seq, userid) {
        document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
        document.form1.p_process.value = "viewPage";
        document.form1.p_seq.value = seq;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"   value = "">
    <input type = "hidden" name = "p_pageno"    value = "">
	<input type = "hidden" name = "p_rowseq"      value="">
    <input type = "hidden" name = "p_seq"       value = "">
    <input type = "hidden" name = "p_userid"    value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="600">
  <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/homepage/tit_precourse.gif" border="0"></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
      <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr valign="middle">
            <td align="right" width="85%">
              <select name="p_search" >
                <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value='<%=v_searchtext %>'> </td>
            <td align="right">
                  <a href = "javascript:selectList();"><img src="/images/admin/button/b_go.gif" border="0"></a>
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                <%  }   %>            
            </td>
          </tr>
        </table>
        <br>

        <!----------------- List 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="8%"  class="table_title" height="25" align="center"><b>NO</b></td>
            <td width="54%" class="table_title"><b>개설예정과목명</b><b></b></td>
            <td width="8%"  class="table_title"><b>작성자</b></td>
            <td width="14%" class="table_title"><b>등록일</b></td>
            <td width="8%"  class="table_title"><b>조회수</b></td>
            <!--td width="8%"  class="table_title"><b>첨부파일</b></td-->
          </tr>
<%
    for(i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
        v_userid    = dbox.getString("d_userid");
        v_name      = dbox.getString("d_name");
        v_indate    = dbox.getString("d_indate");
        v_title     = dbox.getString("d_title");
        v_seq       = dbox.getInt("d_seq");
        v_cnt       = dbox.getInt("d_cnt");
        v_dispnum   = dbox.getInt("d_dispnum");
        v_totalpage = dbox.getInt("d_totalpage");
        v_rowcount  = dbox.getInt("d_rowcount");

		if( 0<v_dispnum ){

			// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
			if (!v_searchtext.equals("")) {
				if (v_search.equals("name")) {
					v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				} else if (v_search.equals("title")) {
					v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				}
			}

%>
          <tr>
            <td class="table_01" height="25"><%=v_dispnum%></td>
            <td  class="table_02_2">
              <a href="javascript:select('<%=v_seq%>','<%=v_userid%>')"><%=v_title%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){          %>
                 &nbsp;&nbsp;<img src = "/images/user/course/btn_new.gif" align = "absmiddle" border = "0">
              <%  }                                                                                                 %>
            </td>
            <td class="table_02_1"><%=v_name %></a></td>
            <td class="table_02_1"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td class="table_02_1"><%=v_cnt%></td>
          </tr>
<%
		}    
	}
%>

        </table>
        <!----------------- List 끝 ----------------->
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
            </td>
          </tr>
        </table>
        <br>
        <!----------------- total 끝 ----------------->
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
