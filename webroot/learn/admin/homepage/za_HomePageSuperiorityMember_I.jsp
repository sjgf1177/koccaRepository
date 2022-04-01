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

    String s_username  = box.getSession("name");
	String v_gubun = "";
	String v_gubunNm = "";
    
    if(v_gubun.equals("G"))
	{
		v_gubunNm = "game";
	}
	else
	{
		v_gubunNm = "kocca";
	}

    String v_server   = conf.getProperty(v_gubunNm + ".url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src= "/script/cresys_lib.js"></script>
<script language = "VBScript"   src= "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//저장
    function insert_check() 
		{
		
        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        document.form1.p_content.value.replace("&","&amp;");
		        
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }
		
		if (blankCheck(document.form1.p_singleword.value)) {
            alert("한마디를 입력하세요");
            document.form1.p_singleword.focus();
            return;
        }
        
        if (document.form1.Wec.Value.length < 3) {
	    	alert("내용을 입력하세요");
	    	return;
	    }

		if(document.form1.p_file1.value.length > 0)
		{
			document.form1.p_filecnt.value = 1;
		}

		if(document.form1.p_tmpmonth.value.length < 2)
		{
			document.form1.p_month.value = "0"+ document.form1.p_tmpmonth.value;
		}
		else
		{
			document.form1.p_month.value = document.form1.p_tmpmonth.value;
		}
        
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
        document.form1.p_process.value = "insert";
        
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

	//파일첨부에 쓰기 방지
	function File_CancelKeyInput()
	{
			if(event.keyCode == 9){	// Tab key만 허용
				return true;
			}else{
				alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.'); 
				return false;
			}
		}
	
	
	
	// 템플릿 ini 파일 로드
    function initPage() {
        document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";
    }
    
//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="initPage()">

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>

<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_content"    value = "">
    <input type = "hidden" name="p_filecnt"    value = "">
    <input type = "hidden" name="p_month"    value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/homepage/tit_monthlist.gif" border="0"></td>
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
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td height="25" class="table_02_2"><%=s_username%></td>
          </tr>
		  <tr> 
            <td width="15%" class="table_title"><strong>해당월</strong></td>
            <td height="25" class="table_02_2">
				<select name="p_tmpmonth">
				<% 
					int k = 0;
					for(k = 1 ; k <= 12; k++)
					{
				%>
						<option value="<%=k%>"><%=k%></option>

				<%
					}
				%>
				</select> 월 
			</td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_title" size="100" class="input" maxlength="80"></td>
          </tr>		  
          <tr> 
            <td width="15%" class="table_title"><strong>한마디</strong></td>
            <td height="25" class="table_02_2">
				<textarea name="p_singleword" style="width:610px; height=40px;" maxlength="1000"></textarea>
				<!--input type="text" name="p_singleword" size="100" class="input"--></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2"><!--textarea name="p_adcontent" cols="110" rows="10" -->
                <!-- 나모 Editor  -->
                <p align="left">
				<script language="javascript" src="/namo_active/kor/NamoWec7.js"></script>
                </p>
            <!--/textarea-->
            </td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>사용유무</strong></td>
            <td height="25" class="table_02_2">
              사용<input type="radio" name="p_useyn" value='Y' class="input" checked>&nbsp;
              미사용<input type="radio" name="p_useyn" value='N' class="input" >&nbsp;
            </td>
          </tr>
		  <tr>
			  <td width="16%" class="table_title"><b>첨부파일</b></td>
	          <td width="81%" class="table_02_2">
	            <!--input name="p_file1" type="file" class="input" size="118" maxlength="100" tabindex=65 -->
	            <input type="FILE" name="p_file1" size="118" class="input"  onkeydown="javascript:return File_CancelKeyInput()"><br>
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
