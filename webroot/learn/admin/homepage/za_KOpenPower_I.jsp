<%
//**********************************************************
//  1. 제      목: 파워인터뷰
//  2. 프로그램명 : za_KOpenPower_I.jsp
//  3. 개      요: 홈페이지 문콘 파워인터뷰 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process	 = box.getString("p_process");
    int		v_pageno	 = box.getInt("p_pageno");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String	s_username	 = box.getSession("name");
	String	v_gubun		 = "";
	String	v_gubunNm	 = "";
    
    if(v_gubun.equals("G")) v_gubunNm = "game";
	else v_gubunNm = "kocca";

 // 웹에디터 템플릿 설정
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
    String  s_gadmin    = box.getSession("gadmin");
    String  v_gadmin	= "";

    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>

<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src= "/script/cresys_lib.js"></script>
<script language = "VBScript"   src= "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript"><!--

//저장
    function insert_check() 
		{
		
        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        //document.form1.p_content_text.value = document.form1.Wec.TextValue;
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
        document.form1.p_process.value = "insert";
        
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.KOpenPowerAdminServlet";
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
//
--></SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>

<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_content"    value = "">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/homepage/tit_power.gif" border="0"></td>
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
              <input type="text" name="p_title" size="100" class="input" maxlength="80">
            </td>
          </tr>     
          <tr> 
            <td width="13%" class="table_title"><strong>작성자</strong></td>
            <td width="15%" height="25" class="table_02_2">운영자</td>
            <td width="14%" class="table_title"><strong>인터뷰 대상</strong></td>
            <td width="20%" height="25" class="table_02_2">
              <kocca_select:select                                       
                name="p_target"                                         
                sqlNum="0001"                                    
                param="0090"             
                onChange=""                         
                attr= "분류"                                        
                selectedValue=""                         
                isLoad="true"                                           
                type="1"                                                
                styleClass=""                                
                all="true" />
            </td>
            <td width="13%" class="table_title"><strong>소속/이름</strong></td>
            <td width="25%" height="25" class="table_02_2">
              <input type="text" name="p_comptext" style="width:200px;" class="input" maxlength="80">
            </td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>사용여부</strong></td>
            <td colspan="5" height="25" class="table_02_2">
              <select name="p_useyn">
                <option value="Y">Y</option>
                <option value="N">N</option>
              </select>
            </td>
          </tr> 
		  <!--tr> 
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
          </tr-->
           
          <!--tr> 
            <td width="15%" class="table_title"><strong>튜터명</strong></td>
            <td height="25" class="table_02_2">
				<input type="text" name="p_title" size="50" class="input" maxlength="80"></td>
			<td width="15%" class="table_title"><strong>소속</strong></td>
            <td height="25" class="table_02_2">
				<input type="text" name="p_comptext" size="50" class="input" maxlength="80"></td>
          </tr-->
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
			  <td width="16%" class="table_title"><b>사진이미지파일첨부</b></td>
	          <td colspan="5"width="81%" class="table_02_2">
	            <!--input name="p_file1" type="file" class="input" size="118" maxlength="100" tabindex=65 -->
	            <input type="FILE" name="p_file1" size="60" class="input"  onkeydown="javascript:return File_CancelKeyInput()"> <strong>( 74 X 56 )</strong> <br>
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

