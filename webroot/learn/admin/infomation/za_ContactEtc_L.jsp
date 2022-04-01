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

    String  v_user_name    = "";
    String  v_ldate     = "";
    String  v_title    = "";
      
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
        document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
        document.form1.p_process.value = "selectListEtc";
        document.form1.submit();
    }
    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
        document.form1.p_process.value = "selectListEtc";
        document.form1.submit();
    }
    
    function whenSelection(p_action) {
        document.form1.p_action.value = "go";
        document.form1.p_process.value = "selectListEtc";
        document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
        document.form1.submit();
    }

  

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "selectViewEtc";
        document.form1.submit();
    }

function search() {
  
  document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
  document.form1.p_process.value = "selectListEtc";
  document.form1.p_action.value = "go";
  document.form1.submit();
}

function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.action = '/servlet/controller.infomation.ContactUsAdminServlet';
    document.form1.p_process.value = "selectListEtc";
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
    <input type="hidden" name="p_seq" value=>
    
    
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.3.2.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
   
     <br>
      <table  cellspacing="0" cellpadding="0" class="table1" border="0">
        <tr>
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="black_butt_left"></td>
                <td class="black_butt_top"></td>
                <td rowspan="3" class="black_butt_right"></td>
              </tr>
              <tr>
                <td class="black_butt_middle"><a href="/servlet/controller.infomation.ContactUsAdminServlet?p_process=selectList" class="c">단체수강</td>
              </tr>
              <tr>
                <td class="black_butt_bottom"></td>
              </tr>
            </table>
          </td>
         
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="blue_butt_left"></td>
                <td class="blue_butt_top"></td>
                <td rowspan="3" class="blue_butt_right"></td>
              </tr>
              <tr>
                <td class="blue_butt_middle">문의및제안</td>
              </tr>
              <tr>
                <td class="blue_butt_bottom"></td>
              </tr>
            </table>
          </td>
          
           <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="black_butt_left"></td>
                <td class="black_butt_top"></td>
                <td rowspan="3" class="black_butt_right"></td>
              </tr>
              <tr>
                <td class="black_butt_middle"><a href="/servlet/controller.infomation.ContactUsAdminServlet?p_process=selectListVocation" class="c">취업신청</td>
              </tr>
              <tr>
                <td class="black_butt_bottom"></td>
              </tr>
            </table>
          </td>
          
          <td width="120" height="23" align="left" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="black_butt_left"></td>
                <td class="black_butt_top"></td>
                <td rowspan="3" class="black_butt_right"></td>
              </tr>
              <tr>
                <td class="black_butt_middle"><a href="/servlet/controller.infomation.ContactUsAdminServlet?p_process=selectListWebReport" class="c">웹취약성</a></td>
              </tr>
              <tr>
                <td class="black_butt_bottom"></td>
              </tr>
            </table>
          </td>
          
          <td width="60%"></td>
          
		</table>
		<br>
     
     
     
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="870" align="right">
              <select name="p_search" >
                <option value='title'     <% if (v_search.equals("title")) out.println("selected"); %>>주제</option>
                <option value='content' <% if (v_search.equals("content")) out.println("selected"); %>>내용</option>
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


        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td class="table_top_line" colspan="5" ></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title">주제</a></td>
            <td class="table_title" width="16%">성명</td>    
            <td class="table_title" width="16%">날짜</td>            
          </tr>

<%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_seq           = dbox.getInt("d_seq");
                v_title         = dbox.getString("d_title");
                v_user_name         = dbox.getString("d_user_name");
                v_ldate        = dbox.getString("d_addate");
                
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");

%>
          <tr> 
            <td class="table_01" height="25"><%= v_dispnum%></td>
            <td class="table_02_2"><a href="javascript:view('<%= v_seq%>')"><%= v_title %></a></td>
            <td class="table_02_1"><%= v_user_name %> </td>
            <td class="table_02_2"><%= FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd") %></td>
        
          </tr>
<%
            }

          if(list.size() == 0){ %>
              <tr>
                <td class="table_02_1" colspan="6">등록된 내용이 없습니다</td>
              </tr>
<%  } %>
        </table>
        <br>
        <br>
        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" valign="absmiddle">
              <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
            </td>
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
