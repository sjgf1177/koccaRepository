<%
//**********************************************************
//  1. ��      ��: ����ڷ��
//  2. ���α׷��� : za_NoticeAdmin_I.jsp
//  3. ��      ��: ����ڷ�� ���
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 1. 2
//  7. ��      ��:
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
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // �Խù� ID
    int     v_orgseq     = v_seq;

    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    

    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");        
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        //v_content = StringManager.replace(v_content,"\r\n","<br>");

        // �˻�� �־��ٸ� �ش� �κ��� �ش������ �ΰ���Ų��.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }
    
    
    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
%>
<html>
<head>
<title>Notice</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function modify_pdsadmin() {

        document.form1.action = "/servlet/controller.tutor.OffPlanNoteAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
        
    }

    function delete_pdsadmin() {
        if (confirm("������ �����Ͻðڽ��ϱ�?")) {
            document.form1.action = "/servlet/controller.tutor.OffPlanNoteAdminServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_pdsadmin() {
        document.form1.action = "/servlet/controller.tutor.OffPlanNoteAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function selectList() {
        document.form1.action = "/servlet/controller.tutor.OffPlanNoteAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.tutor.OffPlanNoteAdminServlet";
        document.form1.p_process.value = "selectView";
        document.form1.p_seq.value = seq;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }
//-->
</SCRIPT>


</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type= "hidden" name= "p_pagesize"   value= "<%=v_pagesize %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title ���� ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/portal/s.1_23.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title �� ----------------->

      <br>
        <!----------------- FORM ���� ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>�ۼ���</strong></td>
            <td height="25" class="table_02_2"><%=v_name%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>�����</strong></td>
            <td height="25" class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>����</strong></td>
            <td height="25" class="table_02_2"><%= v_title %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>����</strong></td>
            <td height="100" class="table_02_2"><%= v_content %>
            </td>
          </tr>
        <!---------����÷�ο��� ------------>
          <tr>
            <td class = "table_title" align = "center">÷������</td>
            <td class = "table_02_2"  align = "left">
            <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // ���� ���ϸ�
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // ���� ���ϸ�
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %>  
            </td>
          </tr>
    <!---------����÷�ο��� �� ------------>
          
        </table>
        <!----------------- FORM �� ----------------->
        <br>

        <!----------------- ����, ����, ����Ʈ ��ư ���� ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr>
<%  //if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    ���� ���� 

     if(v_gadmin.equals("A") ){
%>
            <td align="center"><a href="javascript:modify_pdsadmin()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
            <td align="center"><a href="javascript:delete_pdsadmin()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
<%   }   %>
            <td align="center"><a href="javascript:list_pdsadmin()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
          </tr>
        </table>
        <!----------------- ����, ����, ����Ʈ ��ư �� ----------------->


        <!---------------------- �˻� & ����Ʈ ------------------------------->
        <!--table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
          <tr valign="middle">
            <td align="right">
              <select name="p_search" >
                <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>�ۼ���</option>
                <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>����</option>
                <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>����</option>
              </select>
              <input name="p_searchtext" type="text" class="input" value='<%=v_searchtext %>'> </td>
            <td width="120" align="right">
              <a href = "javascript:selectList();"><img src="/images/admin/system/search1_butt.gif" height="18" border=0></a>
            <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    ���� ���� %>
              <a href = "javascript:insertPage();"><img src="/images/admin/system/record1_butt.gif" height="18" border=0></a>
            <%  }   %>
            </td>
          </tr>
        </table>-->
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>

</form>
</body>
</html>