<%
//**********************************************************
//  1. ��      ��: Ŀ�´�Ƽ QnA 
//  2. ���α׷��� : zu_CmuQnA_L.jsp
//  3. ��      ��: Ŀ�´�Ƽ QnA.
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.07.01 
//  7. ��      ��: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getString("p_faq_type");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	String  content      = box.getString("content");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);


	String  width = "630";
	String  height = "200";
    ArrayList list       = (ArrayList)request.getAttribute("typelist");

%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
//���
function uf_insertOK() {
    if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
    if(document.form1.p_title.value ==''){
       alert('������ �Է��Ͽ����մϴ�.');document.form1.p_title.focus();return;
    }


    if(document.form1.content.value ==''){
       alert('������ �Է��Ͽ����մϴ�.');document.form1.content.focus();return;
    }
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}

//����Ʈ
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityQnAServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process" value="">
<input type = "hidden" name = "p_faq_type" value="<%=v_faq_type%>">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"      value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"   value = "<%=v_select%>">
<table width="900" cellspacing="3" cellpadding="3">
  <tr>
    <td> 
	  <table width="890" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="right"><a href="/servlet/controller.community.CommunityIndexServlet" onFocus="blur()"><img src="../../images/community/sm_home.gif" border="0"></a></td>
        </tr>
      </table>
      <!-- �ǹٱ� �׵θ��� ���̺� -->
	  <table width="890" border="1" cellspacing="0" 
                         cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
        <tr>
          <td valign="top">
		  <!-- ��ü ��׶��� �ִ� ���̺�-->
		    <table width="876" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <%if(s_grtype.equals("KOCCA")){%>
					<td valign="top" background="/images/community/back_total.jpg">
				<%}else if(s_grtype.equals("KGDI")){%>
					<td valign="top" background="/images/user/game/community/back_total.jpg"><%}%>
				<!-- top table -->
				  <table width="876" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="168" valign="top"><a href="/servlet/controller.community.CommunityIndexServlet" onfocus="blur()">
							<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/top_img01.jpg" border="0"></a></td><%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/top_img01.jpg" border="0"></a></td><%}%>	
						<td width="708" valign="top">
							<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/topimg.jpg"></td>
							<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/topimg.jpg"></td><%}%>	
					</tr>
				</table>
				  <!-- center table start -->
                  <table width="876" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td valign="top" style="padding-left:5px">
					    <table width="860" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <!-- left -->
                            <td width="174"  valign="top" background="../../images/community/bg_left.gif" style="padding-left:5px">
                             <%@ include file="/learn/user/include/topCommunityA.jsp"%>
 							</td>
                            <!-- center -->
                            <td width="686" rowspan="2" valign="top" background="../../images/community/bg_center.gif" > 
                              <table width="686" border="0" cellspacing="0" cellpadding="0" >
                                <tr> 
                                  <td><img src="../../images/community/center_top.gif"></td>
                                </tr>
                              </table>
                              <table width="686" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center" valign="top"> 
                                    <!-- Ŀ�´�Ƽmain center start-->
                                    <!-- ��üŸ��Ʋ -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td>
										  <table width="407" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="407" valign="bottom" class="title">Ŀ�´�Ƽ<img src="../../images/community/title_vline.gif" align="absbottom"><strong><%if("DIRECT".equals(v_faq_type)){out.print("��������");}else{out.print("Q&amp;A");}%></strong></td>
                                            </tr>
                                          </table>
										</td>
                                      </tr>
                                      <tr> 
                                        <td height="1" bgcolor="CCCCCC"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <!--Q&A���� -->
                                    <table width="652" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                                      <tr  class="lcolor"> 
                                        <td height="3" colspan="2" class="line_color_com"></td>
                                      </tr>
                                      <tr> 
                                        <td width="96" class="tbl_ctit1">�ۼ���</td>
                                        <td class="tbl_gleft"><a href="#"><%=s_username%></a></td>
                                      </tr>
                                      <tr> 
                                        <td class="tbl_ctit2" >����÷��</td>
                                        <td valign="top" class="tbl_btit" ><div id="hdiv_MainBg"> 
                                            <input name="p_file1" id="hFileMainBg" type="file" class="input" oncontextmenu="return false" onkeydown="return File_CancelKeyInput();" style="IME-MODE: disabled; HEIGHT: 22px" size="45" />
                                          </div>

                                        </td>
                                      </tr>
                                      <tr> 
                                        <td  class="tbl_ctit1" >����</td>
                                        <td valign="top" class="tbl_btit" ><input name="p_title" type="text" class="input2" size="92"></td>
                                      </tr>
                                      <tr><td colspan="2" valign="top" class="comm_contents" ><%@ include file="/include/DhtmlEditor.jsp" %></td></tr>
                                    </table>
                                    <!-- ��ư -->
                                    <table width="652" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="5" align="right"></td>
                                      </tr>
                                      <tr> 
                                        <td align="right"><a href="javascript:uf_insertOK();"><img src="../../images/user/button/btn_register.gif" border="0"></a> 
                                          <a href="javascript:wf_listOK();"><img src="../../images/user/button/btn_list.gif" border="0"></a></td>
                                      </tr>
                                    </table>
								  </td>
                                </tr>
                                <tr>
                                  <td height="10" align="center" valign="top"></td>
                                </tr>
                              </table>
							</td>
                          </tr>
                          <tr>
                            <td height="100" valign="bottom" background="../../images/community/bg_left2.gif" style="padding-left:5px">&nbsp; 
                            </td>
                          </tr>
                        </table>
						<!-- footer table -->
                        <table width="860" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/community/footer_top.gif"></td>
                          </tr>
                          <tr> 
                            <td height="28" align="center" bgcolor="F0F2F0">
								<%if(s_grtype.equals("KOCCA")){%><img src="/images/user/kocca/community/footer_copy.gif">
								<%}else if(s_grtype.equals("KGDI")){%><img src="/images/user/game/community/footer_copy.gif"><%}%>
							</td>
                          </tr>
                        </table>
					  </td>
                    </tr>
                  </table>
				</td>
              </tr>
            </table>
		  </td>
        </tr>
     </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>