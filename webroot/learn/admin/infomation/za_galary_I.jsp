<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_I.jsp
//  3. 개      요: 과정차수별공지사항 과정차수별등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
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
	String  v_process  = box.getString("p_process");
	int v_tabseq   = box.getInt("p_tabseq");
	
	int  v_pageno      = box.getInt("p_pageno");
	
	String  v_searchtext = box.getString("p_searchtext");
	String  v_search     = box.getString("p_search");
	
	String s_username  = box.getSession("name");
	String v_compcd = "";
	
	String v_server   = conf.getProperty("kocca.url.value");
	String v_tem_url  = conf.getProperty("url.namo.template");
	String v_tem_name = conf.getProperty("name.namo.template.ini");
	String v_tem_url_name = v_server + v_tem_url + v_tem_name;
	
	String  s_gadmin    = box.getSession("gadmin");
	String v_gadmin ="";
	if(!s_gadmin.equals("")){
	  v_gadmin = s_gadmin.substring(0,1);
	}
	
  	String content 	= "";
  	String width	= "540";
  	String height	= "300";

%>

<html>
<head>
<title>::: 수강생 갤러리 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 저장
    function insert_check() {
        if(blankCheck(document.form1.p_title.value)){
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

        if (document.form1.p_position.value == "") {
            alert("소속/이름을 입력하세요");
            document.form1.p_position.focus();
            return;
        }
        if (realsize(document.form1.p_position.value) > 200) {
            alert("소속/이름은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_position.focus();
            return;
        }
		
		document.form1.p_content.value = document.form1.Wec.MIMEValue;

		document.form1.p_content.value.replace("&","&amp;");

        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }
		/*
        if (realsize(document.form1.p_adcontent.value) > 2000) {
            alert("내용은 한글기준 1000자를 초과하지 못합니다.");
            document.form1.p_adcontent.focus();
            return;
        }*/

        document.form1.action = "/servlet/controller.infomation.GalaryAdminServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

    // 전체목록으로 이동
    function list() {
        document.form1.action = "/servlet/controller.infomation.GalaryAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    // 공지구분 선택
    function selectCategory() {
        document.form1.action = "/servlet/controller.infomation.GalaryAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype="multipart/form-data" >
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_tabseq"     value = "<%=v_tabseq %>">
    <input type = "hidden" name="p_content"    value = "">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.1_10.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

        <!----------------- 전체공지 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td colspan="3" height="25" class="table_02_2"><input type="text" name="p_title" size = 100 class="input" maxlength="80"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td width="35%" height="25" class="table_02_2"><%=s_username%></td>
            <td width="15%" class="table_title"><strong>소속/이름</strong></td>
            <td width="35%" height="25" class="table_02_2"><input type="text" name="p_position" style="width:300px;" class="input" maxlength="80"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>승인여부</strong></td>
            <td colspan="3" height="25" class="table_02_2">
              <select name="p_flagYn">
	              <option value='Y'>Yes</option>
	              <option value='N'>No</option>
              </select>
            </td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td colspan="3" height="25" class="table_02_2">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_content");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
            </td>
          </tr>
          <tr>
              <td width="16%" class="table_title"><b>대표 이미지</b></td>
              <td colspan="3" width="81%" class="table_02_2">
                <!--input name="p_file1" type="file" class="input" size="118" maxlength="100" tabindex=65 -->
                <input type="FILE" name="p_file1" size="118" class="input"><br>
              </td>
          </tr>

        </table>
        <!----------------- 전체공지 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
