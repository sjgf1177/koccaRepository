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
<%@ page import = "com.credu.infomation.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd    = box.getString("p_selDtlCd");
    
    int     v_seq          = box.getInt("p_seq");
    
    String s_username  = box.getSession("name");
    
    String v_gubun          = "";
    String v_clsfcd         = "";
    String v_dtlcd          = "";
    String v_clsfnm         = "";
    String v_dtlnm          = "";
    String v_title          = "";
    String v_content        = "";
    String v_professor      = "";
    String v_pictureurl     = "";
    String v_useyn          = "";
    String v_indate         = "";
    String v_usernm         = "";
    String v_userid         = "";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;

    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_gubun          = dbox.getString("d_gubun");  
        v_clsfcd         = dbox.getString("d_clsfcd");      
        v_dtlcd          = dbox.getString("d_dtlcd");       
        v_clsfnm         = dbox.getString("d_clsfnm");      
        v_dtlnm          = dbox.getString("d_dtlnm");
        v_title          = dbox.getString("d_title");       
        v_content        = dbox.getString("d_content");     
        v_professor      = dbox.getString("d_professor");   
        v_pictureurl     = dbox.getString("d_pictureurl"); 
        v_indate         = dbox.getString("d_indate");
        v_usernm         = dbox.getString("d_name");
        v_userid         = dbox.getString("d_userid");
        v_useyn          = dbox.getString("d_useyn");
        
        v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
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

    function list() {
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
	
	function modify_article() {
	    document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
	    document.form1.p_process.value = "updatePage";
	    document.form1.submit();
	}
	
	function delete_article() {
	    document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
	    document.form1.p_process.value = "delete";
	    document.form1.submit();
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
    <input type = "hidden" name="p_selClsfCd"  value = "<%=v_selClsfCd%>">
    <input type = "hidden" name="p_selDtlCd"   value = "<%=v_selDtlCd%>">
    <input type = "hidden" name = "p_seq"      value = "<%=v_seq%>">
    
    

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/portal/s.1_21.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" height="25" class="table_title"><strong>작성자</strong></td>
            <td width="35%" height="25" class="table_02_2" >
              <%=v_usernm %>
            </td>
            <td width="15%" height="25" class="table_title" ><strong>등록일</strong></td>
            <td width="35%" height="25" class="table_02_2"><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
          </tr>
          <tr>
            <td width="15%" height="25" class="table_title"><strong>분류</strong></td>
            <td width="35%" height="25" class="table_02_2" >
              <%=v_clsfnm %>
            </td>
            <td width="15%" height="25" class="table_title"><strong>세부 분류</strong></td>
            <td width="35%" height="25" class="table_02_2" >
              <%=v_dtlnm %>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>교수명</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <%=v_professor %>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <%=v_title %>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2" colspan="3">
                <%=v_content %>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>동영상 경로</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <%=v_pictureurl %>
            </td>
          </tr>
          
          <tr>
              <td width="16%" class="table_title"><b>첨부파일</b></td>
              <td id="sTd" width="81%" class="table_02_2" colspan="3">
                 <%     
                    if( v_realfileVector != null ) { 
                        for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
            
                            String v_realfile = (String)v_realfileVector.elementAt(i);
                            String v_savefile = (String)v_savefileVector.elementAt(i);      

                            if(v_realfile != null && !v_realfile.equals("")) {  %>
                                &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a><br>
                                <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
            <%              }   
                        }
                    }
            %>
              </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>사용여부</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <%=v_useyn %>
            </td>
          </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:modify_article()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
            <td align="center"><a href="javascript:delete_article()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
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
