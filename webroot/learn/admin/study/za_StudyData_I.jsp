<%
//**********************************************************
//  1. 제      목: 과정자료실
//  2. 프로그램명: za_StudyData_I.jsp
//  3. 개      요: 과정자료실 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_subjnm = box.getString("p_subjnm");
    int v_tabseq    = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

    //DEFINED in relation to select START

    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_action    = box.getString("s_action");   
    //DEFINED in relation to select END        

    // 나모에디터
  	String contents = "";
  	String width	= "540";
  	String height	= "250"; 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert() {
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

        //if (blankCheck(document.form1.p_content.value)) {
        //    alert("내용을 입력하세요!");
        //    document.form1.p_content.focus();
        //    return;
        //}
/*
        document.forms[0].p_content.editorApplet = DHTMLEdit.tbContentElement;     
        document.forms[0].p_content.value = DHTMLEdit.getDocumentHTML();      
        document.forms[0].InitHTML.value="";      
        document.forms[0].addBody.value="";
*/

        document.form1.p_content.value = document.form1.Wec.MIMEValue;
		document.form1.p_search.value     = "";
        document.form1.p_searchtext.value = "";
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

   function cancel() {
        document.form1.action = "/servlet/controller.study.StudyAdminDataServlet";
        document.form1.p_process.value = "list";
        document.form1.submit();
   }

//이미지 경로를 바꿔준다.
function setImageNames(filenames,filepath) {
   document.forms[0].DHTMLimages.value = filenames;
   document.forms[0].DHTMLimagesPath.value = filepath;
}

//첨부관련 스크립트

// Bean 클래스에 명시된 제한 파일 갯수
var fileMax = <%=StudyAdminDataBean.getFILE_LIMIT() %>;

function addFile(){

    var div_obj     = document.getElementsByName("divFile");

    var viewCnt     = 0;

    for (var i = 0 ; i < div_obj.length ; i ++) {
        if ( div_obj[i].style.display != "none") {
            viewCnt++;
        }
    }
    
    if (parseInt(viewCnt) >= parseInt(fileMax)){
        alert("더 이상 첨부 하실수 없습니다. 최대 첨부수 : " + fileMax);
        return;
    }

    for (var i = 0 ; i <= fileMax ; i ++) {
        if ( div_obj[i].style.display == "none") {
            
        	div_obj[i].style.display = "block";
        	var fileObj     = document.getElementById("p_file"+i);
            break;
        }
    }
}

function delFile(p) {
 
    var div_obj     = document.getElementsByName("divFile")[p-1];
    var fileObj     = document.getElementById("p_file"+p);

    fileObj.select();
    document.selection.clear();

    div_obj.style.display   = "none";
}

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" enctype = "multipart/form-data">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>">     <!--in relation to select-->
	<input type="hidden" name="s_middleclass"    value="<%=ss_middleclass%>">     <!--in relation to select-->
	<input type="hidden" name="s_lowerclass"    value="<%=ss_lowerclass%>">     <!--in relation to select-->

    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->   
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_subjnm"    value = "<%=v_subjnm %>">    
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_content">



  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

 <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>       
	  <!----------------- title 끝 ----------------->  
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0"><%=v_subjnm%>과정</td>
          </tr>
        </table>
        <br>

        <!----------------- 자료등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="12%" height="25" class="table_title"><b>작성자</b></td>
            <td class="table_02_2" width="88%"><%=s_username%></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>제목</b></td>
            <td class="table_02_2"> 
              <input size="120" maxlength="100" name="p_title" class="input">
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>내용</b></td>
            <td class="table_02_2"> 
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('p_content');</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
			</td>
          </tr>

			  <%  if(box.getBoolean("p_isUpload")) {    //    파일첨부 여부 %>
			  <tr>
				<td class = "table_title" width = "12%" align = "center">첨부파일</td>
				<td class = "table_02_2"  align = "left">
				 <!--
				 <input type = "file" size="120" class="input" name = "p_file1"><br>
				 <input type = "file" size="120" class="input" name = "p_file2"><br>
				 <input type = "file" size="120" class="input" name = "p_file3"><br>
				 <input type = "file" size="120" class="input" name = "p_file4"><br>
				 <input type = "file" size="120" class="input" name = "p_file5">
				 -->
                  <%
                  int    i_fileLimit         = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %> 
				</td>
			  </tr>
			  <%  }   %>

        </table>
        <!----------------- 자료등록 끝 ----------------->



        <br>



        <!----------------- 등록, 취소 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td><a href="javascript:insert()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
			<td width=8></td>
            <td><a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->






        <br>




      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
