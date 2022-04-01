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
    String v_title          = "";
    String v_content        = "";
    String v_professor      = "";
    String v_pictureurl     = "";
    String v_useyn          = "";
    String v_indate         = "";
    String v_usernm         = "";
    String v_userid         = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_gubun          = dbox.getString("d_gubun");  
        v_clsfcd         = dbox.getString("d_clsfcd");      
        v_dtlcd          = dbox.getString("d_dtlcd");       
        v_title          = dbox.getString("d_title");       
        v_content        = dbox.getString("d_content");     
        v_professor      = dbox.getString("d_professor");   
        v_pictureurl     = dbox.getString("d_pictureurl"); 
        v_indate         = dbox.getString("d_indate");
        v_usernm         = dbox.getString("d_name");
        v_userid         = dbox.getString("d_userid");
        v_useyn          = dbox.getString("d_useyn");
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
    function insert_check() {

    document.form1.p_content.value = document.form1.Wec.MIMEValue;                                                                                          
    document.form1.p_content.value.replace("&","&amp;"); 
    
    if (document.form1.p_clsfcd.value == "") {
        alert("자료 분류를 입력하여주십시오");
        document.form1.p_clsfcd.focus();
        return;
    }

    /*if (document.form1.p_dtlcd.value == "") {
        alert("주제 분류를 입력하여주십시오");
        document.form1.p_dtlcd.focus();
        return;
    }*/

    if (document.form1.p_professor.value == "") {
        alert("교수명을 입력하여주십시오");
        document.form1.p_professor.focus();
        return;
    }

       if (document.form1.p_title.value == "") {
            alert("제목을 입력하여주십시오");
            document.form1.p_title.focus();
            return;
        }

    if (document.form1.Wec.Value.length < 3) {
        alert("내용을 입력하세요");
        return;
    }

        document.form1.p_pageno.value = 1;
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.p_selClsfCd.value     = "";
        document.form1.p_selDtlCd.value     = "";
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "update";

        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.infomation.SpecialCourseAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function addClsf(){
        document.form1.action = "/servlet/controller.system.CodeAdminServlet";
        document.form1.p_gubun1.value   = '0087';
        document.form1.p_levels.value   = 1;
        document.form1.p_process.value = "selectSubList";
        document.form1.submit();
   }

    function addDtl(){
        if (document.form1.p_clsfcd.value == "") {
            alert("자료 분류 를 선택해주세요");
            return;
        }
         document.form1.action = "/servlet/controller.system.CodeAdminServlet";
         document.form1.p_gubun1.value   = '0087';
         document.form1.p_levels.value   = 2;
         document.form1.p_upper.value    = document.form1.p_clsfcd.value;
         document.form1.p_process.value = "selectSubList";
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
    <input type = "hidden" name= "p_content"   id= "p_content"  value= "<%=StringUtil.convertHtmlchars(v_content)%>">  
    <input type = "hidden" name="p_gubun1"     value = "">
    <input type = "hidden" name="p_upper"      value = "">
    <input type = "hidden" name="p_levels"     value = "">
    <input type = "hidden" name="p_gubun"      value = "<%=v_gubun%>">
    

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
              <kocca_select:select name="p_clsfcd" sqlNum="0003"  param="0087" param3="1"             
                onChange="changep_dtlcd('0087', this.value, 2)" attr=" " selectedValue="<%=v_clsfcd %>" isLoad="true" all="N" />&nbsp; <!--a href="javascript:addClsf();">분류 추가</a-->
            </td>
            <td width="15%" height="25" class="table_title"><strong>세부 분류</strong></td>
            <td width="35%" height="25" class="table_02_2" >
              <kocca_select:select name="p_dtlcd" sqlNum="0004"  param="0087" param2="<%=v_clsfcd %>" param3="2"            
                onChange="" attr=" " selectedValue="<%=v_dtlcd %>" isLoad="true" all="N" />&nbsp; <!--a href="javascript:addDtl();">분류 추가</a-->
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>교수명</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <input type="text" name="p_professor" size = 50 class="input" maxlength="80" value="<%=v_professor %>">
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <input type="text" name="p_title" size = 100 class="input" maxlength="80" value="<%=v_title %>">
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2" colspan="3">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">;namoActiveInitCompleted("p_content");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>동영상 경로</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <input type="text" name="p_pictureurl" size="84" class="input" value="<%=v_pictureurl %>">
            </td>
          </tr>
          
          <tr>
              <td width="16%" class="table_title"><b>첨부파일</b></td>
              <td id="sTd" width="81%" class="table_02_2" colspan="3">
                  <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
                  
                  int    i_fileLimit         = SpecialCourseBean.getFILE_LIMIT();           // 제한 첨부 파일수
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach.jsp" %> 
              </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><strong>사용여부</strong></td>
            <td height="25" class="table_02_2" colspan="3">
              <input type="checkbox" name="p_useyn" value="Y" <%=v_useyn.equals("Y") ? "checked" : "" %>>
            </td>
          </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
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
