<%
//**********************************************************
//  1. 제      목: 과정게시판
//  2. 프로그램명: zu_StudyBoard_L.jsp
//  3. 개      요: 과정게시판 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.2.27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int v_tabseq   = box.getInt("p_tabseq");

    int v_seq = 0;
    int v_levels = 0;
    int v_cnt = 0;
    int i     = 0;

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";

    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;

    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectStudyBoardList");
%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function selectList() {
        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
        document.form1.p_process.value = "";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>




<body topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_process"   value = "">
    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_seq"       value = "">
    <input type = "hidden" name = "p_upfilecnt" value = "">
    <input type = "hidden" name = "p_userid"    value = "">



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
      <td><img src="/images/user/study/inquiry_title.gif" alt="질문방" ></td>
      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      <td><img src="/images/user/study/gongi_tail.gif"></td>
    </tr>
  </table>
            <!----------------- 타이틀 끝 ---------------->
            <br>
            <!----------------- 조회 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td valign="bottom"></td>
                <td align="right"> 
                  <table width="245" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td> 
                        <select  name='p_search' class="input">
                            <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                            <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                            <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
                        </select>
                      </td>
                      <td> 
                        <input size="18" name="p_searchtext" class="input" value='<%=v_searchtext %>'>
                      </td>
                      <td>
                      <a href = "javascript:selectList();"><img src="/images/user/button/btn_j.gif" alt="조회"  border="0"></a></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
            </table>
            <!----------------- 조회 끝 ---------------->                        

            <!----------------- 게시판 리스트 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
              <tr> 
                <td class="board_color_line"></td>
              </tr>
            </table>

            <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
              <tr> 
                <td> 
                  <table cellspacing="1" cellpadding="3" class="table2">
                    <tr> 
                      <td class="board_title_bg1" width="5%">번호</td>
                      <td class="board_title_bg1">제 목</td>
                      <td class="board_title_bg1">등록일</td>
                      <td class="board_title_bg1">작성자</td>
                      <td class="board_title_bg1" width="7%">조회수</td>
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
			v_levels    = dbox.getInt("d_levels");
			v_upfilecnt = dbox.getInt("d_upfilecnt");
			v_dispnum   = dbox.getInt("d_dispnum");
			v_totalpage = dbox.getInt("d_totalpage");
			v_rowcount  = dbox.getInt("d_rowcount");


		// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
		if (!v_searchtext.equals("")) {
			if (v_search.equals("name")) {
				v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			} else if (v_search.equals("title")) {
				v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			}
		}
			if(i%2 == 0){   
				v_class1 = "board_text1";
				v_class2 = "board_text3";
			}else{
				v_class1 = "board_text2";
				v_class2 = "board_text4";
			}

%>
                    <tr> 
                      <td class="<%=v_class1%>"><%=v_dispnum%></td>
                      <td class="<%=v_class2%>">
                      <img src = "/images/admin/system/space.gif" width="<%=v_levels*15-30%>" height ="2" border =0>
                      <%if (v_levels > 1) {%>
                        <img src="/images/user/study/reply_icon1.gif"> 
                      <% } %>
                        <a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a>
                      <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){ %>
                         &nbsp;&nbsp;<img src = "/images/user/support/ico_new.gif" align = "absmiddle" border = "0">
                      <%  } %>
                      </td>
                      <td class="<%=v_class1%>"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                      <td class="<%=v_class1%>"><a href = "mailto:<%//=fbean.getSession("email")%>"><%= v_name %></a></td>
                      <td class="<%=v_class1%>"><%=v_cnt%></td>
                    </tr>
<%  } if(i == 0){   %>    
                    <tr> 
                      <td class="board_text1" colspan="5">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
                  </table>

                </td>
              </tr>
            </table>
            <!----------------- 게시판 리스트 끝 ---------------->
<br>

            <!----------------- 작성, 닫기 버튼 시작 ---------------->
            <table cellspacing="0" cellpadding="0"  class="open_board_table_out1">
              <tr> 
                <td width=70%><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %></td>
                <td align=right>
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/user/button/btn_write.gif" alt="작성"  border="0"></a><%  }   %></td>
				<td witdh=5%></td>
                <td width=8% align=right><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  alt="닫기"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 작성, 닫기 버튼 끝 ----------------->
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
