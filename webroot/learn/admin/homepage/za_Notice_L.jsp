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
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="com.credu.homepage.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.course.*" %>
<%@ page import="com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%

    NoticeAdminBean selectData = new NoticeAdminBean();

    RequestBox box      = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  ss_grcode   = box.getString("s_grcode");            //교육그룹
    String  s_gadmin    = box.getSession("gadmin");
    String  v_gadmin    = StringManager.substring(s_gadmin, 0, 1);
    String  isAll       = !v_gadmin.equals("A") ? "false" : "true";
    String  v_selGroup  = box.getStringDefault("p_selGroup", "ALL");

    //out.println(v_selcomp);

    int v_tabseq          = box.getInt("p_tabseq");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");


    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");


    int    v_seq     = 0;            // 일련번호
    int    v_cnt     = 0;            // 조회수

    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";            // 등록자
    String v_grcode    = "";              // 교육그룹
    String v_grcodenm  = "";
    String v_filecnt   = "";
    String v_onoffgugun   = "";

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int     v_totalrowcount =  0;

    ArrayList edugroup  = (ArrayList)request.getAttribute("selectGrcode");
    // ArrayList list1     = (ArrayList)request.getAttribute("selectList1");
    ArrayList list2     = (ArrayList)request.getAttribute("selectList2");

%>
<html>
<head>
<title>공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
         document.form1.p_process.value = "select";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
         document.form1.p_process.value = "select";
         document.form1.submit();
    }

    function selectComp() {
        document.form1.p_pageno.value = 1;
        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    function view(seq) {
        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function insert() {
//        if (document.form1.p_comp.value=="ALL") {
//          alert("회사를 선택해주세요");
//          return;
//      }
        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.method = "post";
        document.form1.submit();
    }

    function search() {
        <%if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) { %>
        whenSelection();
        <% } %>
        
        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.method = "post";
        document.form1.submit();
    }

// 조건 검색
function whenSelection() {
    var ff = document.form1;
        //교육그룹관리자는 교육그룹을 선택해야함
        <%if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) { %>
          if (ff.s_grcode.value == 'ALL' || ff.s_grcode.value == '----' || ff.s_grcode.value == '' ) {
                alert("교육그룹을 선택하세요.");
                return;
            }
        <% } %>
        //ff.p_selGroup.value = ff.s_grcode.value;

        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
}
<%if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A") && box.getString("s_grcode").equals("")) { %>
location.href = "/servlet/controller.homepage.NoticeAdminServlet?p_process=select&s_grcode=<%=box.getSession("tem_grcode")%>&p_selGroup=<%=box.getSession("tem_grcode")%>&p_tabseq=<%=v_tabseq %>&&p_search=adtitle";
<% } %>

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = '/servlet/controller.homepage.NoticeAdminServlet';
        document.form1.p_process.value = "select";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "get">
    <input type = "hidden" name = "p_process"   value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_seq"       value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr>
            <td><img src="/images/admin/homepage/homepage_title1.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- form 시작 ----------------->
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="250">
              <!-- 교육그룹 시작 -->
              <%= !v_gadmin.equals("A") ? "<font color=\"red\">★</font>":"" %>
                                     교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                    onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="<%= isAll %>" />
              <% //교육그룹 관리자는 ALL 검색이 안되게 설정 %>
              <input type="hidden" name="s_grseq" value="ALL">
            <!-- 교육그룹 종료 -->
<input type="hidden" name="s_grseq" value="ALL">
            </td>
            <td width="670" align="right">
              <select name="p_search" >
                <option value='adtitle'     <% if (v_search.equals("adtitle")) out.println("selected"); %>>제목</option>
                <option value='adcontents' <% if (v_search.equals("adcontents")) out.println("selected"); %>>내용</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
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


        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="10" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title" width="8%" height="25"><b>공지사항 구분</b></td>
            <td class="table_title" ><b>내용</b></td>
            <td class="table_title" width="8%"><b>작성자</b></td>
            <td class="table_title" width="5%"><b>온/오프</b></td>
            <td class="table_title" width="12%"><b>등록일</b></td>
            <td class="table_title" width="8%"><b>사용여부</b></td>
            <td class="table_title" width="7%" height="25"><b>팝업여부</b></td>
            <!--td class="table_title" width="5%" height="25"><b>gubun</b></td-->
            <td class="table_title" width="8%" height="25"><b>로그인여부</b></td>
            <td class="table_title" width="8%" ><b>조회수</b></td>
          </tr>
          <!-- 전체공지사항 시작 -->
<%
            String v_gubun     = "";
            String v_useyn     = "";
            String v_popup     = "";
            String v_loginyn   = "";

            for(int i = 0; i < list2.size(); i++) {

                //NoticeData data2   = (NoticeData)list2.get(i);

                DataBox dbox1 = (DataBox)list2.get(i);

                v_seq      = dbox1.getInt("d_seq");
                v_cnt      = dbox1.getInt("d_cnt");
                v_adtitle  = dbox1.getString("d_adtitle");
                v_addate   = dbox1.getString("d_addate");
                v_adname   = dbox1.getString("d_adname");
                v_gubun    = dbox1.getString("d_gubun");
                v_useyn    = dbox1.getString("d_useyn");
                v_loginyn  = dbox1.getString("d_loginyn");
                v_popup    = dbox1.getString("d_popup");
                v_filecnt  = dbox1.getString("d_filecnt");
                v_addate   = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");

                v_dispnum   = dbox1.getInt("d_dispnum");
                v_totalpage = dbox1.getInt("d_totalpage");
                v_totalrowcount = dbox1.getInt("d_totalrowcount");

                if(v_loginyn.equals("Y")){
                  v_loginyn = "로그인후";
                }
                else if(v_loginyn.equals("Y")){
                  v_loginyn = "로그인전";
                }
                else if(v_loginyn.equals("AL")){
                  v_loginyn = "전체";
                }

                v_onoffgugun    = dbox1.getString("d_onoffgubun");
                if( v_onoffgugun.equals("C") ) {
                    v_onoffgugun = "온라인";
                } else if( v_onoffgugun.equals("S") ) {
                    v_onoffgugun = "오프라인";
                } else {
                    v_onoffgugun = "";
                }
%>

          <tr>
            <td class="table_01" ><%=v_dispnum%></td>
            <td class="table_02_1"><%= (v_gubun.equals("N") ? "공지" : (v_gubun.equals("G") ? "안내" : "정보") ) %></td>
            <td class="table_02_2"><a href="javascript:view('<%=v_seq%>', '<%=v_filecnt%>')"><%=v_adtitle%></a></td>
            <td class="table_02_1"><%=v_adname%></td>
            <td class="table_02_1"><%=v_onoffgugun%></td>
            <td class="table_02_1"><%=v_addate%></td>
            <td class="table_02_1" ><%=v_useyn%></td>
            <td class="table_02_1" ><%=v_popup%></td>
            <td class="table_02_1" ><%=v_loginyn%></td>
            <td class="table_02_1"><%=v_cnt%></td>
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
