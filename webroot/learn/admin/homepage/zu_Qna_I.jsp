<%
//**********************************************************
//  1. 제      목: 과정게시판
//  2. 프로그램명: zu_StudyBoard_I.jsp
//  3. 개      요: 과정게시판 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    String  v_subj		 = box.getString("p_subj");        //과정코드
    String  v_year		 = box.getString("p_year");        //년도
    String  v_subjseq	 = box.getString("p_subjseq");     //과정 차수

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");    
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/hhi_lib.js"></script>
<script language="JavaScript">
<!--
// 등록
function insert() {

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

    if (document.form1.Wec.TextValue.length < 3) {
        alert("내용을 입력하세요");
        return;
    }   


    //if(!limitFile()){
    //    alert("업로드 가능하지 않은 파일이 존재합니다.");
    //    return;
    //}  	    	    	    
    
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

// 목록으로이동
function list() {
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" enctype = "multipart/form-data">

	<input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_tabseq"     value = "7">
    <input type = 'hidden' name = "p_content"     value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/homepage/homepage_title12.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

      <br>
        <!----------------- FORM 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 	
				<td width="15%" class="table_title"><strong>과정명</strong></td>
				<td height="25" class="table_02_2"><%= SelectSubjBean.getSubj2(box)%></td>
		  </tr>
					
			<tr>
				<td width="15%" class="table_title">제목</th>
				<td height="25" class="table_02_2"><input type="text" name="p_title" class="inbox" style="width:590px;" /></td>
			</tr>
			<tr>
				<td width="15%" class="table_title">작성자</th>
				<td height="25" class="table_02_2"><%= s_username %></td>
				
			</tr>
			<tr>	
				<td width="15%" class="table_title">작성일</th>
				<td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
			</tr>
			<tr>
				<td class="table_title" class="table_title">내용 </td>
              <td height="25" class="table_02_2">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";namoActiveInitCompleted('p_content');</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
    		  </td>
			</tr>
			
					 <tr> 
		              <td class="table_title" class="table_title">파일첨부 </td>
		              <td class="table_02_2" colspan="3">		              
		                    <!-- 파일첨부 시작 -->
		                    <%
		                    int    i_fileLimit         = HomePageQnaBean.getFILE_LIMIT();             // 제한 첨부 파일수
		                    %>
		                    
		                    <%//@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
		                    <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %>
		                    <!-- 파일첨부 종료 -->
		    		  </td>
		          </tr>
          
					</table>
		          <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="right" class="ms"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <br>
        <!----------------- total 끝 ----------------->
      </td>
  </tr>
</table>

</form>
</body>
</html>