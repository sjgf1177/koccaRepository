<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_L.jsp
//  3. 개      요: 공지사항 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box      = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    //String  ss_grcode   = box.getString("s_grcode");            //교육그룹
    String  s_gadmin    = box.getSession("gadmin");
    String  v_gadmin    = StringManager.substring(s_gadmin, 0, 1);
    String  isAll       = !v_gadmin.equals("A") ? "false" : "true";
    //String  v_selGroup  = box.getStringDefault("p_selGroup", "ALL");

    //int v_tabseq          = box.getInt("p_tabseq");
    
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;
    
    String  v_selDtlCd    = box.getString("p_selDtlCd");
    String  v_selContentType = box.getString("p_selContentType");
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");


    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String v_clsfcd          = "";
    String v_dtlcd           = "";
    String v_dtlnm           = "";
    String v_contenttype     = "";
    String v_contenttypenm   = "";
    String v_title           = "";
    String v_professor       = "";
    String v_usechk          = "";
    String v_imageurl        = "";
    String v_userid          = "";
    String v_name            = "";
    String v_mainyn          = "";
    String v_indate          = "";
    int    seq               =  0;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list     = (ArrayList)request.getAttribute("selectList");
    
%>
<html>
<head>
<title>이벤트 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    function view(seq, gubun) {
        document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
      document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
      document.form1.p_process.value = "insertPage";
      document.form1.submit();
  }

    function search() {
        document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

// 조건 검색
    function whenSelection() {
    	document.form1.p_selDtlCd.value = document.form1.p_selDtlCd1.value;
    	document.form1.p_selContentType.value = document.form1.p_selContentType1.value; 
        document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
        }
<%//if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A") && box.getString("s_grcode").equals("")) { %>
//location.href = "/servlet/controller.infomation.PracticalCourseAdminServlet?p_process=select&s_grcode=<%=box.getSession("tem_grcode")%>&p_selGroup=<%=box.getSession("tem_grcode")%>&&p_search=adtitle";
<%// } %>

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.infomation.PracticalCourseAdminServlet';
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "get">
    <input type = "hidden" name = "p_process"           value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"            value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_pagesize"          value = "<%=v_pagesize%>">
    <input type = "hidden" name = "p_selDtlCd"          value = "<%=v_selDtlCd%>">
    <input type = "hidden" name = "p_selContentType"    value = "<%=v_selContentType%>">
    <input type = "hidden" name = "p_seq"               value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr>
            <td><img src="/images/admin/portal/s.1_20.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="250">
            </td>
            <td width="670" align="right">
              <select name="p_search" >
                <option value='title'   <% if (v_search.equals("title")) out.println("selected"); %>>강의명</option>
                <option value='content' <% if (v_search.equals("content")) out.println("selected"); %>>강사내용</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
            </td>

            <td width="100" align="right" valign="middle"> 
              <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
              <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title" width="10%"><b>
            <kocca_select:select                    
                name="p_selDtlCd1"             
                sqlNum="0001"
                param="0086"                
                onChange="whenSelection(this.value);"             
                attr= "강의분류"                
                selectedValue="<%= v_selDtlCd %>"             
                isLoad="true"               
                type="3"                
                styleClass="table_title"                
                all="true" />   
            </b></td>
            <td class="table_title" width="10%"><b>
            <kocca_select:select                    
                name="p_selContentType1"             
                sqlNum="0001"
                param="0085"                
                onChange="whenSelection(this.value);"             
                attr= "강의유형"                
                selectedValue="<%= v_selContentType %>"             
                isLoad="true"               
                type="3"                
                styleClass="table_title"                
                all="true" /> 
            </b></td>
            <td class="table_title"><b>강좌명</b></td>
            <td class="table_title" width="10%"><b>강사명</b></td>
            <td class="table_title" width="10%"><b>작성자</b></td>
            <td class="table_title" width="10%"><b>등록일</b></td>
            <td class="table_title" width="10%"><b>사용여부</b></td>
            <td class="table_title" width="5%" ><b>조회수</b></td>
          </tr>
          <!-- 전체공지사항 끝 -->

<%

            for(int i = 0; i < list.size(); i++) {

                DataBox dbox = (DataBox)list.get(i);

                v_seq             = dbox.getInt   ("d_num");            
                v_clsfcd          = dbox.getString("d_clsfcd");       
                v_dtlcd           = dbox.getString("d_dtlcd");        
                v_dtlnm           = dbox.getString("d_dtlnm");        
                v_contenttype     = dbox.getString("d_contenttype");  
                v_contenttypenm   = dbox.getString("d_contenttypenm");
                v_title           = dbox.getString("d_title");        
                v_professor       = dbox.getString("d_professor");    
                v_usechk          = dbox.getString("d_usechk");       
                v_imageurl        = dbox.getString("d_imageurl");     
                v_userid          = dbox.getString("d_userid");       
                v_name            = dbox.getString("d_name");
                v_mainyn          = dbox.getString("d_mainyn"); 
                v_indate          = dbox.getString("d_indate");
                v_cnt             = dbox.getInt   ("d_cnt");

                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");

%>
          <tr>
            <td class="table_01"><%=v_mainyn.equals("Y") ? "*" : v_dispnum%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_dtlnm%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_contenttypenm%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_6'" : "class='table_02_2'"%>><a href="javascript:view(<%=v_seq%>)"><%=v_title%></a></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_professor%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_name%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_usechk%></td>
            <td <%=v_mainyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_cnt%></td>
          </tr>
<%
        }
%>

        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
            </td>
          </tr>
        </table>
        <!----------------- total 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
