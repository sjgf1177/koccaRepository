<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 8. 1
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_selcomp  = box.getString("p_selcomp");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    //String  v_comp    = box.getString("p_comp");
    
    int     v_seq          = box.getInt("p_seq");
    
    String v_clsfcd         = "";
    String v_dtlcd          = "";
    String v_contenttype    = "";
    String v_title          = "";
    String v_content        = "";
    String v_professor      = "";
    String v_pictureurl     = "";
    String v_gubun          = "";
    String v_genre          = "";
    String v_inspector      = "";
    String v_runningtime    = "";
    String v_production     = "";
    String v_analyze        = "";
    String v_usechk         = "";
    String v_splecturenm    = "";
    String v_spcontent      = "";
    String v_mainyn         = "";
    String v_fileDelYn      = "";
    String v_imageurl       = "";
    String v_professorimg   = "";
    String v_upfile         = "";
    String v_svrfile        = "";
    String v_indate         = "";

    
    DataBox dbox = (DataBox)request.getAttribute("selectOffExpert");
    if (dbox != null) {
        v_clsfcd         = dbox.getString("d_clsfcd");      
        v_dtlcd          = dbox.getString("d_dtlcd");       
        v_contenttype    = dbox.getString("d_contenttype"); 
        v_title          = dbox.getString("d_title");       
        v_content        = dbox.getString("d_content");     
        v_professor      = dbox.getString("d_professor");   
        v_pictureurl     = dbox.getString("d_pictureurl");  
        v_gubun          = dbox.getString("d_gubun");       
        v_genre          = dbox.getString("d_genre");       
        v_inspector      = dbox.getString("d_inspector");   
        v_runningtime    = dbox.getString("d_runningtime"); 
        v_production     = dbox.getString("d_production");  
        v_analyze        = dbox.getString("d_analyze");     
        v_usechk         = dbox.getString("d_usechk");      
        v_splecturenm    = dbox.getString("d_splecturenm"); 
        v_spcontent      = dbox.getString("d_spcontent");   
        v_mainyn         = dbox.getString("d_mainyn");      
        v_fileDelYn      = dbox.getString("d_fileDelYn");   
        v_imageurl       = dbox.getString("d_imageurl");    
        v_professorimg   = dbox.getString("d_professorimg");
        v_upfile         = dbox.getString("d_file");        
        v_svrfile        = dbox.getString("d_file");
        v_indate         = dbox.getString("d_indate");
 
    }
    
    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src= "/script/cresys_lib.js"></script>
<script language = "VBScript"   src= "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//저장
    function modify_article() {
        document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

	function delete_article() {
	    document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
	    document.form1.p_process.value = "delete";
	    document.form1.submit();
	}

    function list() {
        document.form1.action = "/servlet/controller.infomation.PracticalCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function openPracticalCourse(contenttype)
    {

        if(contenttype == "A1"){
            window.open("about:blank", "ViewVod", "top=0, left=0, width=480, height=360, status=no, resizable=no");
        } else {
        	window.open("about:blank", "ViewVod", "top=0, left=0, width=300, height=20, status=no, resizable=no");
        }

   	    document.form1.action="/servlet/controller.infomation.PracticalCourseAdminServlet";
	    document.form1.p_process.value = "popUpVod";
	    document.form1.target="ViewVod";
	    document.form1.submit();
	    document.form1.target="_self";
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>
<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%= v_pageno %>">
    <input type = "hidden" name="p_pagesize"   value = "<%= v_pagesize %>">
    <input type = "hidden" name="p_search"     value = "<%= v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%= v_searchtext %>">
    <input type = "hidden" name="p_seq"        value = "<%= v_seq %>">
    

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/portal/s.1_20.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_02_2"><strong>강좌명</strong></td>
            <td height="25" class="table_02_2"><%=v_title %>
              
            </td>
          </tr>
          <tr> 
            <td height="25" class="table_02_2" colspan="2"><table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td height="10" colspan="2"></td>
                </tr>
                <tr> 
                  <td width="110" valign="top">
                    <table width="100" height="120" border="0" cellpadding="0" cellspacing="0" bgcolor="e0e0e0">
                      <tr> 
                        <td>
                          <a href="javascript:openPracticalCourse('<%=v_contenttype %>')">
                          <img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_imageurl %>" width=152 height=114 border=0>
                          </a>
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td><strong><font color="33939D">강사명</font></strong> 
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=v_professor %></td>
                      </tr>
                      <tr> 
                        <td><strong><font color="33939D">교육과정명</font></strong> 
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=v_production %></td>
                      </tr>
                      <tr> 
                        <td><strong><font color="33939D">제작일</font></strong> 
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=v_indate %></td>
                      </tr>
                      <tr> 
                        <td><strong><font color="33939D">제작</font></strong> 
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=v_inspector %></td>
                      </tr>
                      <tr> 
                        <td><strong><font color="33939D">러닝타임</font></strong> 
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=v_runningtime %></td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr> 
                  <td height="10" colspan="2"></td>
                </tr>
              </table></td>
          </tr>
          <tr> 
            <td width="15%" class="table_02_2"><strong>내용</strong></td>
            <td height="25" class="table_02_2"><%=v_content.replace("\r\n","<br>") %></td>
          </tr>
          <tr> 
            <td width="15%" class="table_02_2"><strong>작품해설</strong></td>
            <td height="25" class="table_02_2"><%=v_analyze.replace("\r\n","<br>") %></td>
          </tr>
       </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <%if(v_gadmin.equals("A") || v_gadmin.equals("B") || (v_gadmin.equals("H") || v_gadmin.equals("K")) ){%>
            <td align="center"><a href="javascript:modify_article()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
            <td align="center"><a href="javascript:delete_article()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
          <%}%>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
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
