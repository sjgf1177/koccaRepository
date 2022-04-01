<%
//**********************************************************
//  1. 제      목 : 과정체계도
//  2. 프로그램명 : za_CourseMap_L.jsp
//  3. 개      요 : 과정체계도 리스트
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 이연정 2005. 7. 27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box        = (RequestBox)request.getAttribute("requestbox");
  String  v_process     = box.getString("p_process");

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
	int v_pageno   = box.getInt("p_pageno"); 
    ArrayList list = (ArrayList)request.getAttribute("selectList");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">

//입력페이지로 이동
function insertPage() {
	document.form1.action = "/servlet/controller.homepage.HomePageCourseMapAdminServlet";
	document.form1.p_process.value = "insertPage";   
	document.form1.submit();
}
//수정페이지로 이동
function updatePage(tabseq) {
	document.form1.p_tabseq.value=tabseq;
	document.form1.action = "/servlet/controller.homepage.HomePageCourseMapAdminServlet";
	document.form1.p_process.value = "updatePage";   
	document.form1.submit();
}
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form  name = "form1"  method = "post">
<input type = "hidden" name = "p_process" value = "">
<input type = "hidden" name = "p_pageno"  value = "">
<input type = "hidden" name = "p_tabseq"  >

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/homepage/homepage_title13.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

        <br>
        <!----------------- form 시작 ----------------->

        <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td  align="right" valign="middle">
              <a href='javascript:insertPage()'><img src="/images/admin/button/btn_add.gif" border="0"></a>
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
            <td colspan="7" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="8%" height="25"><b>NO</b></td>
            <td class="table_title" ><b>분류명</b></td>
            <td class="table_title" ><b>pdf파일</b></td>
            <td class="table_title" ><b>작은이미지파일</b></td>
            <td class="table_title" ><b>큰이미지파일</b></td>
            <td class="table_title" width="8%"><b>작성자</b></td>
            <td class="table_title" width="15%" ><b>등록일</b></td>
          </tr>
          <!-- 전체공지사항 시작 -->
<%
	if(list.size()>0){
		for(int i = 0; i < list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);  
			v_dispnum 	= dbox.getInt("d_dispnum");
			int    v_tabseq     = dbox.getInt("d_tabseq");
			String v_tabseqname = dbox.getString("d_tabseqname");
			String v_realpdf    = dbox.getString("d_realpdf");
			String v_savepdf    = dbox.getString("d_savepdf");
			String v_realsfile  = dbox.getString("d_realsfile");
			String v_savesfile  = dbox.getString("d_savesfile");
			String v_reallfile  = dbox.getString("d_reallfile");
			String v_savelfile  = dbox.getString("d_savelfile");
			String v_luserid 	= dbox.getString("d_luserid");
			String v_ldate    	= dbox.getString("d_ldate");			
%>

          <tr>
            <td class="table_02_1" height="25"><%=v_tabseq%></td>     
            <td class="table_02_1"><a href="javascript:updatePage(<%=v_tabseq%>)"><%=v_tabseqname%></a></td>		 
            <td class="table_02_1"><%=v_realpdf%></td>			 
            <td class="table_02_1"><%=v_realsfile%></td>		
            <td class="table_02_1"><%=v_reallfile%></td>		
            <td class="table_02_1"><%=v_luserid%></td>			 
            <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate, "yyyy/MM/dd")%></td>			     
          </tr>
<%
        }
	}else{

%>
		<tr>
		  <td colspan='15' class="table_02_1">
				등록 된 내용이 없습니다.		
		  </td>
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
              <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
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
