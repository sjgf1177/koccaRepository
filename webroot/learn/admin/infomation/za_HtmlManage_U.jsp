<%
//**********************************************************
//  1. 제      목: Notice Templet
//  2. 프로그램명 : za_NoticeTemplet_U.jsp
//  3. 개      요: 공지사항 수정
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
<%@ page import = "com.dunet.common.util.*" %> 
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    int     v_seq        = box.getInt("p_seq");

    String v_title       = "";           // 제목
    String v_code    = "";           // 템플릿 파일
    String v_content     = "";           // 내용
    

    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_title     = dbox.getString("d_title");
        v_code  = dbox.getString("d_code");
        v_content   = dbox.getString("d_content");

        v_content   = StringManager.replace(v_content,"&nbsp;", "&amp;nbsp;");
     }

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function insert_check() {
        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_adtitle.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_adtitle.focus();
            return;
        }

        if(document.form1.p_code.value == ""){
            alert("코드를 입력하세요.");
            document.form1.p_code.focus();
            return;
        }
        //document.form1.p_content.value = document.form1.Wec.MIMEValue;
        //document.form1.p_content.value.replace("&","&amp;");
        document.form1.p_content.value = document.form1.Wec.BodyValue;
        
        if (document.form1.Wec.Value.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"      value = "<%= v_process %>">
    <input type = "hidden" name = "p_seq"          value = "<%=v_seq %>">
    <input type = "hidden" name = "p_content"      value = "<%=StringUtil.convertHtmlchars(v_content)%>">
    

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.1_18.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        
      <br>


      <!----------------- CNETER 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr> 
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="17%" class="table_title"><b>제목</b></td>
          <td width="83%" height="25" colspan="7" class="table_02_2">
            <input type="text" name="p_title" size = 100 class="input" value='<%= v_title %>'>
          </td>
        </tr>
        <tr> 
          <td width="17%" class="table_title"><b>코드</b></td>
          <td width="83%" height="25" colspan="7" class="table_02_2">
            <input type="text" name="p_code" size = 100 class="input" value='<%= v_code %>'>
          </td>
        </tr>
        <tr> 
          <td align="center" class="table_title">내용</td>
          <td colspan="7" class="table_02_2" >
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_content");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
          </td>
        </tr>
      </table>

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td align="right" height="20">
            <a href="javascript:insert_check()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            <a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>
          </td>
        </tr>
        <tr> 
          <td height="3"></td>
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