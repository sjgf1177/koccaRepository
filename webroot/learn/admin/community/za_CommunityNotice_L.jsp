<%
//**********************************************************
//  1. 제      목: 학습동호회 공지사항
//  2. 프로그램명: za_CommunityNotice_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: mscho 2004. 02. 17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box  = (RequestBox)request.getAttribute("requestbox");
    String v_subjnm = box.getString("p_subjnm");
    int v_commid    = 0;

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
    
    ArrayList list = (ArrayList)request.getAttribute("selectCommunityNoticeList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function selectList() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
   <form name="form1" method="post" action="">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_commId" value='0'>
    <input type = "hidden" name = "p_subjnm"    value = "<%=v_subjnm %>">
    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_seq"       value = "">
    <input type = "hidden" name = "p_upfilecnt" value = "">
    <input type = "hidden" name = "p_userid"    value = "">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/community/tit_comm_02.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
        <br>
        <br>

        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>                       
            <td align="right" width="100%">
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_addplus.gif"  border="0"></a>
                <%  }   %>            
            </td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->

        <!----------------- 과정 자료실  리스트 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%"><b>NO</b></td>
            <td class="table_title"><b>제목</b></td>
            <td class="table_title" width="15%"><b>등록일자</b></td>
            <td class="table_title" width="10%"><b>작성자</b></td>
            <td class="table_title" width="10%"><b>조회수</b></td>
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
        v_totalpage = dbox.getInt("d_totalpagecount");
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
            <td class="table_01"><%=v_dispnum%></td>
            <td class="table_02_6"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a>
              <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){ %>
                 &nbsp;&nbsp;<img src = "/images/community/ico_new.gif" align = "absmiddle" border = "0">
              <%  } %>            
            </td>
            <td class="table_02_1" align="center"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td class="table_02_1" align="center"><a href = "mailto:<%//=fbean.getSession("email")%>"><%= v_name %></a></td>
            <td class="table_02_1" align="center"><%=v_cnt%></td>
          </tr>
<%  } if(i == 0){   %>    
                    <tr> 
                      <td class="table_02_1" align="center" colspan="5">등록된 내용이 없습니다.</td>
                    </tr>    
<%    } %>
        </table>
        <!----------------- 과정 자료실  리스트 끝 ----------------->
        <br>





        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center" class="ms"><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %></td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
        </table>




        <br>




      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
