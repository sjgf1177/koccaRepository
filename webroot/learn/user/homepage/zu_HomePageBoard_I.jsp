<%
//**********************************************************
//  1. ��      ��: �ڷ�� 
//  2. ���α׷��� : zu_HomePageBoard_I.jsp
//  3. ��      ��: �ڷ�� �Է�ȭ��
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.07.01 �̿���
//  7. ��      ��: 2005.07.01 �̿���
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  ������ ������ִ� ���ϼ�
    String  v_indate     = "";
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	String  content = "";
	String  width = "650";
	String  height = "200";

box.put("leftmenu","04");                       // �޴� ���̵� ����
%>


<!------- ��� �ΰ�,�޴�,�������� ���� ���� ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- ��� �ΰ�,�޴�,�������� ���� ��  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//�Էµ� ���� ����
function insert() {
	if(document.all.use_editor[0].checked) {        
        form1.p_content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
	}else {
		form1.p_content.value = document.all.txtDetail.value;
    }

if (blankCheck(document.all.p_title.value)) {
    alert("������ �Է��ϼ���!");
    document.all.p_title.focus();
    return;
}   
    if (realsize(document.form1.p_title.value) > 200) {
        alert("������ �ѱ۱��� 100�ڸ� �ʰ����� ���մϴ�.");
        document.form1.p_title.focus();
        return;
    }

    document.form1.p_pageno.value = "1";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

//�󳻿� üũ
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//�����Է��� ����ϰ� ����Ʈ�� �̵�
function cancel() {
     document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//���ڼ� üũ
function realsize( value ) {
 var len = 0;
 if ( value == null ) return 0;
 for(var i=0;i<value.length;i++){
     var c = escape(value.charAt(i));
     if ( c.length == 1 ) len ++;
     else if ( c.indexOf("%u") != -1 ) len += 2;
     else if ( c.indexOf("%") != -1 ) len += c.length/3;
 }
 return len;
}

//-->
</script>
</head>

		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; �н��������� &gt; Q&amp;A</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> <table width="675" border="0" cellspacing="0" cellpadding="0">

                    <tr> 
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_data.jpg" ></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- qna table -->
				<form name = "form1" enctype = "multipart/form-data" method = "post">
				<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
				<input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
				<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
				<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
				<input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
				<input type = "hidden" name = "p_content"    value="">
				<input type = "hidden" name = "p_process" value="">

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="97"><img src="/images/user/support/text_title.gif"></td>
                      <td width="579" class="tbl_gleft"> 
						<input type="text" size="80" maxlength="100" name="p_title" value="" class="input"> 
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_pe.gif"></td>
                      <td class="tbl_gleft"><%= s_username %></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_day.gif"></td>
                      <td class="tbl_gleft"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
					<%  if(box.getBoolean("p_isUpload")) { %>

                    <tr> 
                      <td><img src="/images/user/support/text_addfile.gif" ></td>
                      <td class="tbl_gleft"> 
					  <input type="FILE" name="p_file1" size="80" class="input"><br>
					  <input type="FILE" name="p_file2" size="80" class="input"><br>
					  <input type="FILE" name="p_file3" size="80" class="input"><br>
					  <input type="FILE" name="p_file4" size="80" class="input"><br>
					  <input type="FILE" name="p_file5" size="80" class="input"><br>
                      </td>
                    </tr>
                      <%  }   %>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr>
                      <td colspan="2"><img src="/images/user/support/text_contents2.gif"></td>
                    </tr>
                    <tr valign="top"> 
                      <td colspan="2" class="tbl_contents">
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
						<!--<textarea name="content" rows="15" cols="90" onkeyup="fc_chk_byte(this,1200);" ></textarea>-->
                        <!-- DHTML Editor  -->
  
                      </td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="2"></td>
                    </tr>
                    <tr bgcolor="B8D5E5"> 
                      <td height="5" colspan="2"><img src="/images/user/support/sup_tb_bo.gif" width="675" height="5"></td>
                    </tr>
                  </table>
				  </form>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right">&nbsp; </td>
                      <td width="257" align="right"> <a href="javascript:insert()"><img src="/images/user/button/btn_register.gif" border="0"></a> 
                        <a href="javascript:cancel()"><img src="/images/user/button/btn_cancel.gif" border="0"></a> 
                      </td>
                    </tr>
                  </table>
				
				</td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->