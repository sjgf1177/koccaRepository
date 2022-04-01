<%
//**********************************************************
//  1. 제      목: 운영자자료실
//  2. 프로그램명 : za_AdminQna_A.jsp
//  3. 개      요: 운영자자료실 답변
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2005. 1. 2
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box 		= (RequestBox)request.getAttribute("requestbox");
    String  v_process  	= box.getString("p_process");
    int  v_pageno      	= box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_tabseq     = box.getInt("p_tabseq");               // 게시판 ID
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
		int     v_refseq     = box.getInt("p_refseq");
		int     v_levels     = box.getInt("p_levels");
		int     v_position   = box.getInt("p_position");
    int     v_orgseq     = v_seq;

    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    DataBox dbox = (DataBox)request.getAttribute("selectQna");
    if (dbox != null ) {
        v_seq         	 = dbox.getInt("d_seq");
        v_userid      	 = dbox.getString("d_userid");
        v_name        	 = dbox.getString("d_name");
        v_title       	 = dbox.getString("d_title");
        v_content     	 = dbox.getString("d_content");
        v_cnt         	 = dbox.getInt("d_cnt");
        v_indate      	 = dbox.getString("d_indate");

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }
    
    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

    // 리스트
    //ArrayList list = (ArrayList)request.getAttribute("selectQnaList");
    
%>
<html>
<head>
<title> 학습관리지원시스템 </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/hhi_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	// 저장하기
    function insert_adminqna() {
    		document.form1.p_content.value = document.form1.Wec.MIMEValue;	
				document.form1.p_content.value.replace("&","&amp;");
				
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }                
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }                     
				if (document.form1.Wec.Value.length < 3) {
					alert("내용을 입력하세요");
					return;
				}

        document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
        document.form1.p_process.value = "reply";
        document.form1.submit();
    }
	
	//목록으로
    function list_adminqna() {
        document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }

//-->
</SCRIPT>


</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type= "hidden" name= "p_pagesize"   value= "<%=v_pagesize %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq%>">
    <input type = "hidden" name = "p_seq"        value = "<%=v_seq %>">
    <input type = "hidden" name = "p_refseq"		 value = "<%=v_refseq%>">
    <input type = "hidden" name = "p_levels"		 value = "<%=v_levels%>">
    <input type = "hidden" name = "p_position"		 value = "<%=v_position%>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">
    <input type = "hidden" name = "p_content"     value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
    <td align="center" valign="top"> 
    <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/tutor/tit_new_quest.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

      <br>
      <table border="0" cellspacing="1" cellpadding="5">
      	<tr>
      		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
					<td  class=sub_title>질문</td>
      	</tr>
      </table>
      
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="15%" class="table_title"><strong>작성자</strong></td>
          <td height="25" class="table_02_2"><%=v_name%></td>
        </tr>
        <tr class="table_02_2">
          <td height="25" class="table_title" ><strong>등록일</strong></td>
          <td height="25" class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
        </tr>
        <tr>
          <td width="15%" class="table_title"><strong>제목</strong></td>
          <td height="25" class="table_02_2"><%= v_title %></td>
        </tr>
        <tr>
          <td width="15%" class="table_title"><strong>내용</strong></td>
          <td height="100" class="table_02_2"><%= v_content %></textarea>
          </td>
        </tr>
		  	<tr>
				<td class = "table_title" align = "center">첨부파일</td>
				<td class = "table_02_2"  align = "left">
				<%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %> 
				</td>
		  	</tr>
      </table>
      <br>
      <!-----------------답변 시작 ---------------->
      <table border="0" cellspacing="1" cellpadding="5">
      	<tr>
      		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
					<td  class=sub_title>답변등록</td>
      	</tr>
      </table>
      	
      <!----------------- 답변 FORM 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr > 
          <td height="25" class="table_title"><strong>등록일</strong></td>
          <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
        </tr>
        <tr> 
          <td width="15%" class="table_title"><strong>제목</strong></td>
          <td height="25" class="table_02_2"><input type="text" name="p_title" size = 135 class="input"></td>
        </tr>
        <tr> 
          <td width="15%" class="table_title"><strong>내용</strong></td>
          <td height="25" class="table_02_2">
          	<!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('p_content');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
          </td>
        </tr>
        <tr valign="middle"> 
          <td width="15%" class="table_title"><strong>파일첨부</strong></td>
          <td height="25" class="table_02_2"> 
            <%
                  int    i_fileLimit         = AdminQnaBean.getFILE_LIMIT();              // 제한 첨부 파일수 
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %>
          </td>
        </tr>
      </table>
      <!----------------- 답변 FORM 끝 ----------------->
		  <!----------------- total 시작 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="right" class="ms"><a href="javascript:insert_adminqna()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
          <td width="1%" align="center" class="ms">&nbsp;</td>
          <td align="left" class="ms"><a href="javascript:list_adminqna()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table>
      <br>
      <!----------------- total 끝 ----------------->
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>

</form>
</body>
</html>
