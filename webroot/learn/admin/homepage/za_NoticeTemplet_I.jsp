<%
//**********************************************************
//  1. 제      목: Notice Templet
//  2. 프로그램명 : za_NoticeTemplet_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    int  v_pageno      = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selGubun   = box.getString("p_selGubun");
    
    String v_code        = "";         // 템플릿 코드
    String v_codenm      = "";         // 템플릿 코드명
    
    String s_username  = box.getSession("name");
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
    ArrayList codeList  = (ArrayList)request.getAttribute("codeList");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    // 등록
    function insert_check() {
        if (document.form1.p_adtitle.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_adtitle.focus();
            return;
        }
        if (realsize(document.form1.p_adtitle.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_adtitle.focus();
            return;
        }

        if(document.form1.p_gubun.value == ""){
        	alert("분류를 선택하세요.");
            document.form1.p_gubun.focus();
            return;
        }

        document.form1.p_contents.value = document.form1.Wec.MIMEValue;
        document.form1.p_contents.value.replace("&","&amp;");
        
        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

        document.form1.p_pageno.value = 1;
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.NoticeTempletServlet";
        document.form1.p_process.value = "insert";

        document.form1.submit();
    }

    // 리스트
    function list() {
        document.form1.action = "/servlet/controller.homepage.NoticeTempletServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_selGubun"      value = "<%=v_selGubun %>">
    <input type = "hidden" name = "p_contents"   value = "">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/homepage/tit_noticetem.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <br>

      <!----------------- 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="17%" class="table_title"><b>제목</b></td>
          <td width="83%" height="25" colspan="7" class="table_02_2">
            <input type="text" name="p_adtitle" size = "100" class="input">
          </td>
        </tr>
        <tr> 
          <td width="17%" class="table_title"><b>분류</b></td>
          <td width="83%" height="25" colspan="7" class="table_02_2">     
            <select name="p_gubun">
              <option value="">::선택::</option>
              <% 
                if (codeList != null && codeList.size() > 0) {
                    for(int i = 0 ; i < codeList.size() ; i ++ ) {
                        DataBox dbox   = (DataBox)codeList.get(i);

                        v_code         = dbox.getString("d_code"); 
                        v_codenm       = dbox.getString("d_codenm");
              %>
                <option value="<%=v_code %>"><%=v_codenm %></option>
              <%
                    }
                }
              %>
            </select>
          </td>
        </tr>
        <tr> 
          <td align="center" class="table_title">내용</td>
          <td colspan="7" class="table_02_2" >
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";namoActiveInitCompleted("p_contents");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 --> 
          </td>
        </tr>
      </table>
      <!----------------- 버튼 시작 ----------------->
      <br>
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td align="right" height="20">
            <a href="javascript:insert_check()"><img src="/images/admin/button/btn_add.gif" border="0"></a> 
            <a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>
          </td>
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>

      
    </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %> 
</form>
</body>
</html>