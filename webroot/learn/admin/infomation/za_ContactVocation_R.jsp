<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명 : za_SubjGong_R.jsp
//  3. 개      요: 과정차수별공지사항 선택과목상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");
    
    int     v_tabseq        = box.getInt("p_tabseq");
    int     v_seq           = box.getInt("p_seq");
    int     v_pageno        = box.getInt("p_pageno");
    
    String  v_searchtext    = box.getString("p_searchtext");
    String  v_search        = box.getString("p_search");

    String  v_tel        	= "";
    String  v_email         = "";
    String  v_homepage     	= "";
    String  v_category     	= "";

    String  v_user_name    = "";
    String  v_title     		= "";
    String  v_link  		= "";
    String  v_savefile_l   	= "";
    String  v_date   		= "";
    String  v_content   		= "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    
    if (dbox != null) {
        v_tel    		= dbox.getString("d_tel");
        v_email       	= dbox.getString("d_email");        
        v_category      = dbox.getString("d_category");      
        v_user_name     = dbox.getString("d_user_name");
        v_title   		= dbox.getString("d_title");
        v_content		= dbox.getString("d_content");
        v_link    		= dbox.getString("d_link");
        
        v_date	    	= dbox.getString("d_addate");
        v_savefile_l	= dbox.getString("d_savefile_l");
		
     }  
%>
<html>
<head>
<title>::: ContactUS :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    
    // 리스트
    function list_gong() {
        document.form1.action = "/servlet/controller.infomation.ContactUsAdminServlet";
        document.form1.p_process.value = "selectListVocation";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type="hidden" name="p_process"    value = "<%= v_process %>">
    <input type="hidden" name="p_seq"        value="<%=v_seq%>">
    <input type="hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type="hidden" name="p_search"     value = "<%=v_search %>">
    <input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.3.2.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- 공지사항관리 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
         <tr>
           <td width="13%" class="table_title" class="table_title">이름 </td>
           <td width="37%"class="table_02_2"><%= v_user_name %></td>
           <td width="13%" height="25" class="table_title""><b> 등록일</b></td>
           <td width="37%" class="table_02_2"><%= FormatDate.getFormatDate(v_date, "yyyy/MM/dd") %></td>
       </tr>      
       
       <tr>
           <td width="13%" class="table_title" class="table_title">전화 </td>
           <td width="37%"class="table_02_2"><%= v_tel %></td>
           <td width="13%" height="25" class="table_title""><b> 이메일</b></td>
           <td width="37%" class="table_02_2"><%= v_email  %></td>
       </tr>
       
        <tr>
           <td width="13%" class="table_title" class="table_title">카테고리 </td>
           <td width="37%"class="table_02_2"><%= v_category %></td>
           <td width="13%" height="25" class="table_title""><b> 링크</b></td>
           <td width="37%" class="table_02_2"><%= v_link  %></td>
       </tr>
       
     
       <tr>
           <td class="table_title"><strong>제목</strong></td>
           <td colspan="3" height="25" class="table_02_2"><%=v_title %></td> 
       </tr>
       
      
       
       <tr>
           <td class="table_title" class="table_title">내용 </td>
           <td colspan="3" class="table_02_2" colspan="3"><%=v_content.replace("\n","<BR>")%></td>
       </tr>
       
        <tr>
           <td class="table_title"><strong>파일</strong></td>
           <td colspan="3" height="25" class="table_02_2">
           <% if  (!v_savefile_l.equals("")) {  %>
           <a href="<%=v_savefile_l %>"> 다운로드</a>
           <% } %>
           </td> 
       </tr>
    
     </table>
        <!----------------- 공지사항관리 끝 ----------------->

        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
           
            <td align="center"><a href="javascript:list_gong()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
