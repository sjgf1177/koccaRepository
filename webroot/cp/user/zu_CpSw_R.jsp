<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Sw자료실 상세화면
//  2. 프로그램명: zu_CpSw_R.jsp
//  3. 개      요: 외주관리 시스템 Sw자료실 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

int v_upfilecnt = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 
Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";
DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid         = dbox.getString("d_userid");
v_name           = dbox.getString("d_name");
v_indate         = dbox.getString("d_indate");
v_title          = dbox.getString("d_title");
v_seq            = dbox.getInt("d_seq");
v_content        = dbox.getString("d_content");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");

%>

<html>
<head>
<title>외주관리시스템자유게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/cp_style.css"">
	<script language = "javascript">
            function selectList() {
            	document.form1.action = "/servlet/controller.cp.CpSwServlet";
				document.form1.p_process.value = "";     
            	document.form1.submit();	
            }            
            function updatePage() {
            	document.form1.action = "/servlet/controller.cp.CpSwServlet";
				document.form1.p_process.value = "updatePage";
            	document.form1.submit();
            }    
            function deleteAction() {
                if (confirm("정말로 삭제하시겠습니까?")) {
                    document.form1.action = "/servlet/controller.cp.CpSwServlet";
					document.form1.p_process.value = "delete";
                    document.form1.submit();
                }
                else {
                    return;
                }
            }
	</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/cp/m_board_on.gif','/images/cp/m_company_on.gif','/images/cp/m_sdata_on.gif','/images/cp/m_swdata_on.gif','/images/user/button/btn_idpw_on.gif','/images/user/button/btn_login_on.gif','/images/cp/m_data_on.gif')">
<!-- top -->
<table width="100%" height="71" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_top.gif">
  <tr>
    <td valign="top">
	<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_subtotal.gif">
<form name = "form1" method = "post">

<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
<input type = "hidden" name = "p_process"     value = "">
<input type = "hidden" name = "p_userid"      value = "<%= v_userid %>">

  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="/images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="/images/cp/dataimg.jpg"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2">
		
		  <table width="900" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="center" valign="top"></td>
              </tr>
              <tr> 
                <td align="center" valign="top">
				<table width="835" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/cp/tit_swdata.gif" width="835" height="34"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center">
					  <!-- sw자료실 보기 --> 
					  <table width="800" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="120" class="tbl_grtit_l"><font color="#FF6600" size="-2">▶</font>제목</td>
                            <td colspan="3" class="tbl_btit"><%=v_title%></td>
                          </tr>
                          <tr> 
                            <td width="120" class="tbl_grtit_l"><font color="#FF6600" size="-2">▶</font>작성자</td>
                            <td width="384" class="tbl_bleft"><a href="#"><%= v_name %>
                              </a></td>
                            <td width="112" class="tbl_grtit_l"><font color="#FF6600" size="-2">▶</font>작성일</td>
                            <td width="148" class="tbl_bleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                          </tr>
                          <tr> 
                            <td class="tbl_grtit_l" ><font color="#FF6600" size="-2">▶</font>첨부파일</td>
                            <td colspan="3" class="tbl_bdata">
					<%  
					// out.println("box.getBoolean::::::::::::"+box.getBoolean("p_isUpload"));
					//if(box.getBoolean("p_isUpload") ) {    
							for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
							String v_realfile = (String)v_realfileVector.elementAt(i);
							String v_savefile = (String)v_savefileVector.elementAt(i);                        
							if(v_realfile != null && !v_realfile.equals("")) {  %>
								&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
								<%= v_realfile%></a><br>
								<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
						  <%		}   
							    }   
					//		}
						%>

							
							
							</td>
                          </tr>
                          <tr> 
                            <td colspan="4" valign="top" class="tbl_grtit" ><font color="#FF6600" size="-2">▶</font>내용</td>
                          </tr>
                          <tr> 
                            <td colspan="4" valign="top" class="tbl_contents" ><%= StringManager.replace(v_content,"&amp;","&") %></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="center"><table width="800" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="418" align="right">&nbsp; </td>
                            <td align="right"> 
                              <a href = "javascript:updatePage()"><img src="/images/user/button/btn_mod.gif" border="0"></a> 
                              <a href = "javascript:deleteAction()"><img src="/images/user/button/btn_del.gif" border="0"></a> 
                              <a href = "javascript:selectList()"><img src="/images/user/button/btn_list.gif" border="0"></a></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>


<%@ include file = "/include/cp_footer.jsp"%>


<%@ include file = "/learn/library/getJspName.jsp" %> 

</body>
</html>