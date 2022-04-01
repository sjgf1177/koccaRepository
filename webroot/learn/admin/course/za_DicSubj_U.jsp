<%
//**********************************************************
//  1. 제      목: 용어사전(일반)
//  2. 프로그램명 : za_DicSubj_U.jsp
//  3. 개      요: 용어사전(일반) 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.namo.active.*" %>
<%@ page import = " com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");
    String  v_searchtext     = box.getString("p_searchtext");
    String  ss_upperclass    = box.getString("s_upperclass");    // 과정분류
    String  ss_middleclass   = box.getString("s_middleclass");   // 과정분류
    String  ss_lowerclass    = box.getString("s_lowerclass");    // 과정분류
    String  ss_subj          = box.getString("s_subjcourse");    // 과정코드
    String  v_subj           = box.getString("p_subj");
    String  v_upperclassnm   = GetCodenm.get_subjattnm(ss_upperclass); 
    String  v_subjnm         = GetCodenm.get_subjnm(ss_subj); 


    int     v_seq        = 0;
    String  v_subjcode   = "";
    String  v_subjcodenm = "";
    String  v_words      = "";
    String  v_descs      = "";
    String  v_groups     = "";

    DataBox data = (DataBox)request.getAttribute("selectDic");

    /*v_seq        = data.getSeq();
    v_words      = data.getWords();
    v_descs      = data.getDescs();
    v_groups     = data.getGroups();
    v_subjcode   = data.getSubj();
    v_subjcodenm = data.getSubjnm();*/

    v_seq        = data.getInt("d_seq");
    v_words      = data.getString("d_words");
    v_descs      = data.getString("d_descs");
    v_groups     = data.getString("d_groups");
    v_subjcode   = data.getString("d_subj");
    v_subjcodenm = data.getString("d_subjnm");
    
    String content  = StringManager.replace(v_descs, "&nbsp;","&amp;nbsp;");    
    String width    = "540";
    String height   = "300";
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

%>

<html>
<head>
<title>::: 용어사전 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function initPage()
    {   
        document.form1.Wec.Value = document.form1.p_contents.value;
    }


    function insert_check() {
        if (document.form1.p_words.value == "") {
            alert("단어를 입력하세요");
            document.form1.p_words.focus();
            return;
        }
        if (realsize(document.form1.p_words.value) > 100) {
            alert("단어는 한글기준 50자를 초과하지 못합니다.");
            document.form1.p_words.focus();
            return;
        }
        
        document.form1.p_contents.value = document.form1.Wec.MIMEValue;	
        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.course.DicSubjAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.course.DicSubjAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="initPage()">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">

    <input type = "hidden" name = "s_upperclass"  value = "<%= ss_upperclass %>">
    <input type = "hidden" name = "s_middleclass" value = "<%= ss_middleclass %>">
    <input type = "hidden" name = "s_lowerclass"  value = "<%= ss_lowerclass %>">
    <input type = "hidden" name = "s_subjcourse"  value = "<%= ss_subj %>">
    <input type = "hidden" name = "p_subj"        value = "<%= v_subj %>">
	<input type = "hidden" name = "p_contents"    value = "<%=StringUtil.convertHtmlchars(content)%>">

    

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
		   <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title><%= v_upperclassnm %> &gt; <%= v_subjnm %></td>
          </tr>
        </table>
        <br>
        <!----------------- 용어사전(일반) 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <!--<tr> 
            <td class="table_title" width="16%" height="25"><b>사업부</b></td>
            <td class="table_02_2" width="81%"><%=v_subjcodenm%></td>
          </tr>-->
          <tr> 
            <td class="table_title" width="16%" height="25"><b>분류</b></td>
            <td class="table_02_2" width="81%"> 
<%= DicSubjAdminBean.getDicGroupSelect(v_groups) %>
            </td>
          </tr>
          <tr> 
            <td class="table_title" width="16%" height="25"><b>용어</b></td>
            <td class="table_02_2" width="81%"> 
              <input name="p_words" type="text" class="input" size="118" value="<%=v_words%>">
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>설명</b></td>
            <td class="table_02_2"> 
              <!--<textarea name="p_descs" cols="100" rows="15"></textarea>-->
                <!-- 나모 Editor  -->
                <p align="center">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('p_contents');</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
                <!-- 나모 Editor  -->    
            </td>
          </tr>
        </table>
        <!----------------- 용어사전(일반) 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width=5>&nbsp;</td>
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
