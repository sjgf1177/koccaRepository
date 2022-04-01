<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_L.jsp
//  3. 개      요: 선택과정차수별 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    
    int v_tabseq          = box.getInt("p_tabseq");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    //2009.10.26 페이지수 추가
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    //2009.10.27 페이지당 레코드수 추가
    if (v_pagesize == 0)  v_pagesize = 10;
    
    int     v_totalrowcount =  0;
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");

    int     v_seq       = 0;            // 일련번호
    int     v_cnt       = 0;            // 조회수

    String  v_type      = "";           // 설정 분류 코드(A1 일자 설정, B1 년월 설정)
    String  v_typenm    = "";           // 설정 분류 코드 이름
    String  v_content   = "";           // 명언 내용
    String  v_startdate = "";           // 시작일
    String  v_enddate   = "";           // 종료일
    String  v_month     = "";           // 년월
    String  v_ldate     = "";           // 최종 수정일
    String  v_userid    = "";           // 작성자 아이디
    String  v_usernm    = "";           // 작성자 이름
    String  v_flagYn    = "";           // 현재 상태
    String  v_date      = "";           // 노출설정일 표시
    
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    
    int     i = 0;
    
    ArrayList list    = (ArrayList)request.getAttribute("selectList");       // 공지리스트
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    
    function whenSelection(p_action) {
        document.form1.p_action.value = "go";
        document.form1.p_process.value = "selectList";
        document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

function search() {
  
  document.form1.action = "/servlet/controller.homepage.WelcomeMessageAdminServlet";
  document.form1.p_process.value = "selectList";
  document.form1.p_action.value = "go";
  document.form1.submit();
}

function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.action = '/servlet/controller.homepage.WelcomeMessageAdminServlet';
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" >
    <input type="hidden" name="p_action">
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>">
    <input type="hidden" name="p_pageno"   value="<%=v_pageno %>">
    <input type="hidden" name="p_tabseq"   value=<%=v_tabseq %>>
    <input type="hidden" name="p_seq" value=>
    
    
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.1_13.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
      <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="870" align="right">
              내용 : 
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
              <input type="hidden" name="p_search" value='content' >
            </td>
            <td width="100" align="right" valign="middle"> <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
              <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->


        <!----------------- 공지사항관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td class="table_top_line" colspan="4" ></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title">명언목록</td>
            <td class="table_title" width="10%">설정관리</td>
            <td class="table_title" width="16%">노출 설정일</td>
            
          </tr>

<%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_seq           = dbox.getInt("d_seq");
                v_content       = dbox.getString("d_content");
                v_type          = dbox.getString("d_type");
                v_ldate         = dbox.getString("d_ldate");
                v_userid        = dbox.getString("d_userid");
                v_usernm        = dbox.getString("d_name");
                v_flagYn        = dbox.getString("d_flagyn");
                v_startdate     = dbox.getString("d_startdate");
                v_enddate       = dbox.getString("d_enddate");
                v_month         = dbox.getString("d_month");
                System.out.println("타입 : " + v_type);
                System.out.println("v_startdate : " + v_startdate);
                System.out.println("v_enddate : " + v_enddate);
                System.out.println("v_month : " + v_month);
                if(v_type.equals("A1")){
                	v_date      = FormatDate.getFormatDate(v_startdate,"yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(v_enddate,"yyyy/MM/dd");
                } else if(v_type.equals("B1")){
                	v_date      = v_month.substring(0, 4) + "년 "+ v_month.substring(4)+"월";
                }
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
%>

          <tr> 
            <td class="table_01" height="25"><%= v_dispnum%></td>
            <td class="table_02_2"><a href="javascript:view('<%= v_seq%>')"><%= v_content %></a></td>
            <td class="table_02_1"><%= v_flagYn.equals("Y") ? "현재설정" : "--" %> </td>
            <td class="table_02_1"><%= v_date %> </td>
          </tr>

<%
            }

          if(list.size() == 0){ %>
              <tr>
                <td class="table_02_1" colspan="6">등록된 내용이 없습니다</td>
              </tr>
<%  } %>
        </table>
        <!----------------- 공지사항관리 끝 ----------------->
        <br>
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
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
