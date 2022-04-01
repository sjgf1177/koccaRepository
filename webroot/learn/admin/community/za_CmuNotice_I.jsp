<%
//**********************************************************
//  1. 제      목: 커뮤니티 공지사항등록 
//  2. 프로그램명 : za_CmuNotice_I.jsp
//  3. 개      요: 커뮤니티 공지사항등록.
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getString("p_faq_type");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_grcode	 = box.getString("s_grcode");
    int     v_pageno     = box.getInt("p_pageno");
    String	v_grtype	= box.getString("p_grtype");

    String  content      = box.getString("content");
    String  width = "540";
    String  height = "200";
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="javascript"></script>
<SCRIPT language="javascript">
<!--

function uf_insertOK() {
    if (document.form1.p_title.value == "") {
        alert("제목을 입력하세요");
        document.form1.p_title.focus();
        return;
    }
    document.form1.content.value = document.form1.Wec.MIMEValue;
    document.form1.content.value.replace("&","&amp;");
    if (document.form1.Wec.Value.length < 3) {
        alert("내용을 입력하세요");
        return;
    }

    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}
//리스트로 이동
function uf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminFaqServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0" >
<form name="form1" enctype="multipart/form-data" method="post">
	<input type="hidden" name="p_process"	value ="">
	<input type="hidden" name="p_faq_type"	value ="<%=v_faq_type%>">
	<input type="hidden" name="p_pageno"    value ="<%=v_pageno%>">
	<input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
	<input type="hidden" name="p_select"	value ="<%=v_select%>">
	<input type="hidden" name="content"		value ="<%=content%>">
	<input type="hidden" name="s_grcode"	value ="<%=v_grcode%>">
	<input type="hidden" name="p_grtype"	value ="<%=v_grtype%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
 <tr>
  <td align="center"> 
   <!----------------- title 시작 ----------------->
   <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
    <tr> 
     <td><img src="../../../images/admin/homepage/tit_comm_notice.gif"></td>
     <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
    </tr>
   </table>
   <br>

   <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
    <tr> 
     <td class="table_title" width="18%">작성자</td>
     <td class="table_02_2"  width="82%"><%=s_username%></td>
    </tr>
    <tr> 
     <td class="table_title" width="18%">제목</td>
     <td class="table_02_2"  width="82%"><input name="p_title" type="text" class="input" size="129"></td>
    </tr>

     <tr>
      <td class="table_title">내용</td>
      <td class="table_02_2" style="padding-top:5;padding-bottom:5">
        <!-- 나모 액티브 스퀘어  시작 -->
        <script language="javascript" src="/script/user_patch.js"></script>
        <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
        <script language="javascript">object_namoActivepatch('100%','500');</script>
        <!-- 나모 액티브 스퀘어  종료 -->
      </td>
     </tr>   
 	   <tr> 
  	  <td class="table_title" width="18%">파일첨부</td>
    	 <td class="table_02_2"  width="82%">
            <%
             int    i_fileLimit         = CommunityAdminDirectBean.getFILE_LIMIT();              // 제한 첨부 파일수
             %>
             <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %> 
        </td>
    	</tr>
    </table>
<br>
<table width="970" border="0" cellspacing="0" cellpadding="0">
     <tr align="center"> 
      <td>
       <a href="javascript:uf_insertOK();" onfocus="this.blur()"><img src="/images/admin/button/btn_save.gif" border = "0"></a>
        <a href="javascript:uf_listOK();" onfocus="this.blur()"><img src="/images/admin/button/btn_cancel.gif" border = "0"></a>
       </td>
     </tr>
    </table>
  </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
