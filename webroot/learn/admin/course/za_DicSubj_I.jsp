<%
//**********************************************************
//  1. ��      ��: ������(�Ϲ�)
//  2. ���α׷���: za_DicSubj_L.jsp
//  3. ��      ��: ������(�Ϲ�) ���
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 9
//  7. ��      ��:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");
    String  v_searchtext     = box.getString("p_searchtext");
    String  ss_upperclass    = box.getString("s_upperclass");    // �����з�
    String  ss_middleclass   = box.getString("s_middleclass");   // �����з�
    String  ss_lowerclass    = box.getString("s_lowerclass");    // �����з�
    String  ss_subj          = box.getString("s_subjcourse");    // �����ڵ�
    String  v_subj           = box.getString("p_subj");
    String  v_upperclassnm   = GetCodenm.get_subjattnm(ss_upperclass); 
    String  v_subjnm         = GetCodenm.get_subjnm(ss_subj); 

    // ���𿡵���
  	String contents = "";

    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name; 

%>

<html>
<head>
<title>::: ������ :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function insert_check() {
        if (blankCheck(document.form1.p_words.value)) {
            alert("�ܾ �Է��ϼ���");
            document.form1.p_words.focus();
            return;
        }
        if (realsize(document.form1.p_words.value) > 100) {
            alert("�ܾ�� �ѱ۱��� 50�ڸ� �ʰ����� ���մϴ�.");
            document.form1.p_words.focus();
            return;
        }
        if(confirm('�������� ����Ͻðڽ��ϱ�?')){
        	document.form1.p_contents.value = document.form1.Wec.MIMEValue;	

            document.form1.p_searchtext.value = "";
            document.form1.action = "/servlet/controller.course.DicSubjAdminServlet";
            document.form1.p_process.value = "insert";
            document.form1.submit();
        }
    }

    function list() {
        document.form1.action = "/servlet/controller.course.DicSubjAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
    <input type = "hidden" name = "p_seq"         value = "">

    <input type = "hidden" name = "s_upperclass"  value = "<%= ss_upperclass %>">
    <input type = "hidden" name = "s_middleclass" value = "<%= ss_middleclass %>">
    <input type = "hidden" name = "s_lowerclass"  value = "<%= ss_lowerclass %>">
    <input type = "hidden" name = "s_subjcourse"  value = "<%= ss_subj %>">
    <input type = "hidden" name = "p_subj"        value = "<%= v_subj %>">
    <input type = "hidden" name = "p_contents"    value = "">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title ���� ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title �� ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
		   <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class=sub_title><%= v_upperclassnm %> &gt; <%= v_subjnm %></td>
          </tr>
        </table>

        <br>
        <!----------------- ������(�Ϲ�) ���� ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" width="16%" height="25"><b>�з�</b></td>
            <td class="table_02_2" width="81%"> 
            <%= DicSubjAdminBean.getDicGroupSelect("") %>
            </td>
          </tr>
          <tr> 
            <td class="table_title" width="16%" height="25"><b>���</b></td>
            <td class="table_02_2" width="81%"> 
              <input name="p_words" type="text" class="input" size="118">
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>����</b></td>
            <td class="table_02_2"> 
              <!--<textarea name="p_descs" cols="100" rows="15"></textarea>-->
              
                <!-- ���� Editor  -->
                <p align="center">
                <!-- ���� ��Ƽ�� ������  ���� -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted('p_contents');</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- ���� ��Ƽ�� ������  ���� -->
                </p>
                <!-- ���� Editor  -->               
            </td>
          </tr>
        </table>
        <!----------------- ������(�Ϲ�) �� ----------------->

        <br>
        <!----------------- ����, ��� ��ư ���� ----------------->
        <table border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=4></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- ����, ��� ��ư �� ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>