<%
//**********************************************************
//  1. 제      목: 파워인터뷰
//  2. 프로그램명 : za_KOpenPower_U.jsp
//  3. 개      요: 홈페이지 문콘 파워인터뷰 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %> 
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");

    int    v_cnt        = 0;            // 조회수
    String v_title      = "";           // 제목
    String v_content    = "";           // 내용
    String v_date       = "";           // 등록일
    String v_name       = "";           // 등록자
    String v_userid     = "";           // userid
    String v_comptext   = "";           // 소속
    String v_target     = "";
    String v_targetnm   = "";

    String v_useyn      = "";           // 사용유무
    String v_month      = "";           // 해당월
    
    String v_realfile   = "";
    String v_savefile   = "";
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    
    if (dbox != null) {    
        v_cnt           = dbox.getInt("d_cnt");
        v_title         = dbox.getString("d_title"); 
        v_comptext      = dbox.getString("d_comptext"); 
        v_content       = dbox.getString("d_contents");
        v_date          = dbox.getString("d_indate");   
        v_name          = dbox.getString("d_lname");
        v_useyn         = dbox.getString("d_useyn");    
        v_realfile      = dbox.getString("d_realfile");
        v_savefile      = dbox.getString("d_savefile");
        v_target        = dbox.getString("d_target");
        v_targetnm      = dbox.getString("d_targetnm");
        
        v_date     = FormatDate.getFormatDate(v_date, "yyyy/MM/dd");
     }  
	

    v_content = StringManager.replace(v_content, "&nbsp;","&amp;nbsp;" );
    String s_username  = box.getSession("name");

    // 웹에디터 템플릿 설정
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
    String s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
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
	//입력
    function update() 
	{
        
        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        //document.form1.p_content_text.value = document.form1.Wec.TextValue;
        document.form1.p_content.value.replace("&","&amp;");
        var file = document.form1.p_file1.value;	

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

        if (blankCheck(document.form1.p_target.value)) {        
            alert("인터뷰 대상을 선택하세요");
            document.form1.p_target.focus();
            return;
        }
        
        if (blankCheck(document.form1.p_comptext.value)) {
            alert("소속을 입력하세요");
            document.form1.p_comptext.focus();
            return;
        }
        
        if (document.form1.Wec.Value.length < 3) {
            alert("내용을 입력하세요");
            return;
        }
		
/*		if(document.form1.p_tmpmonth.value.length < 2)
		{
			document.form1.p_month.value = "0"+ document.form1.p_tmpmonth.value;
		}
		else
		{
			document.form1.p_month.value = document.form1.p_tmpmonth.value;
		}
*/
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.KOpenPowerAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.KOpenPowerAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>

<form name="form1" method="post" enctype="multipart/form-data">
     <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"		value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"		value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext"	value = "<%=v_searchtext %>">
    <input type="hidden" name="p_content"    value = "<%=StringUtil.convertHtmlchars(v_content) %>">
    <input type = "hidden" name="p_seq"			value = "<%=v_seq %>">
    <input type = "hidden" name="p_filecnt" >
    <input type = "hidden" name="p_month" >
	<input type = "hidden" name="p_content_text">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/homepage/title_bg.gif"><img src="/images/admin/homepage/tit_power.gif"  border="0"></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>                        
            <td colspan="6" class="table_top_line"></td>
          </tr>
           <tr> 
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td colspan="5" height="25" class="table_02_2">
              <input type="text" name="p_title" size="100" class="input" maxlength="80" value='<%= v_title %>'>
            </td>
          </tr>     
          <tr> 
            <td width="13%" class="table_title"><strong>작성자</strong></td>
            <td width="15%" height="25" class="table_02_2"><%=s_username%></td>
            <td width="14%" class="table_title"><strong>인터뷰 대상</strong></td>
            <td width="20%" height="25" class="table_02_2">
              <kocca_select:select                                       
                name="p_target"                                         
                sqlNum="0001"                                    
                param="0090"             
                onChange=""                         
                attr= "분류"                                        
                selectedValue="<%=v_target %>"                         
                isLoad="true"                                           
                type="1"                                                
                styleClass=""                                
                all="true" />
            </td>
            <td width="13%" class="table_title"><strong>소속/이름</strong></td>
            <td width="25%" height="25" class="table_02_2">
              <input type="text" name="p_comptext" style="width:200px;" class="input" maxlength="80" value="<%=v_comptext %>">
            </td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>사용여부</strong></td>
            <td colspan="5" height="25" class="table_02_2">
              <select name="p_useyn">
                <option value="Y" <%=v_useyn.equals("Y") ? "selected" : "" %>>Y</option>
                <option value="N" <%=v_useyn.equals("N") ? "selected" : "" %>>N</option>
              </select>
            </td>
          </tr> 
          <tr> 
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td colspan="5" height="25" class="table_02_2"><!--textarea name="p_adcontent" cols="110" rows="10" -->
                <!-- 나모 Editor  -->
                <p align="left">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_content");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
            </td>
          </tr>
		  <tr>
		    <td height="25" class="table_title">첨부파일</td>
            <td colspan="5" class="table_02_2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              
                <tr>
                  <td width="90%"> 
		<%		
					if( v_realfile != null && !v_realfile.equals("")) 
						{  
		%>
						&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a> &nbsp;
						<input type = "checkbox"  name = "p_fildel" value = "<%= v_seq%>"> (삭제시 체크) &nbsp;&nbsp;<font color="blue">    첨부된 파일이 있을시 체크 하지않아도 새로 첨부된 파일로 변경됩니다. </font> <br>
						<input type = "hidden" name = "p_realfile"  value = "<%=v_realfile%>">
						<input type = "hidden" name = "p_savefile"  value ="<%=v_savefile%>">
						<input type = "hidden" name = "p_delfile" >
		<%				} 
		%>
                    <input type="FILE" name="p_file1" size="60" class="input" onkeydown="javascript:return File_CancelKeyInput()"><strong> ※ 이미지크기 ( 98 X 81) </strong><br>
					
					</td>
                  <td width="50">
                  </td>
                </tr>
              </table></td>
		  </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:update()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

