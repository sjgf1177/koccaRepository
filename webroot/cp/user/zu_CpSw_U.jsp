<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Sw자료실 수정화면
//  2. 프로그램명: zu_CpSw_U.jsp
//  3. 개      요: 외주관리 시스템 Sw자료실 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 19
//  7. 수      정: 이연정 2005. 07. 19
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

int v_seq           = box.getInt("p_seq");
int v_upfilecnt     = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 
String v_searchtext = box.getString("p_searchtext");
String v_select     = box.getString("p_select");
int v_pageno        = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
Vector v_fileseqVector  = null;          //      저장된 파일번호 배열
String v_realmotion = "";
String v_savemotion = "";
String content = "";
String width = "650";
String height = "200";

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");
v_name   = dbox.getString("d_name");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
content = StringManager.replace(dbox.getString("d_content"),"&amp;","&");
/*
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
*/
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
//------------------------------------------------------------------------------------------------------
%>


<html>
<head>
<title>외주관리시스템 자료실</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/cp_style.css">
 <script language = "javascript">
            function selectList() {
                document.form1.action = "/servlet/controller.cp.CpSwServlet";
                document.form1.p_process.value = "";
				document.form1.submit();	
            }            
            
            function cancel() {
                document.form1.action = "/servlet/controller.cp.CpSwServlet";
                document.form1.p_process.value = "select";
				document.form1.submit();
            }
            
            function update() { 
				if(document.all.use_editor[0].checked) {        
		            form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
				}else {
					form1.content.value = document.all.txtDetail.value;
			    }
                if (blankCheck(document.form1.p_title.value)) {
                    alert("제목을 입력하세요!");
                    document.form1.p_title.focus();
					return;
                }                
                if (realsize(document.form1.p_title.value) > 100) {
                    alert("제목은 한글기준 50자를 초과하지 못합니다.");
                    document.form1.p_title.focus();
                    return;
                }		              
                if (blankCheck(document.form1.content.value)) {
                    alert("내용을 입력하세요!");
                    document.form1.content.focus();
                    return;
                }	    
                document.form1.action = "/servlet/controller.cp.CpSwServlet";
                document.form1.p_process.value = "update";
				document.form1.submit();
            } 
            function blankCheck( msg ) {
                var mleng = msg.length;
                chk=0;
                
                for (i=0; i<mleng; i++) {
                    if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
                }
                if ( chk == 0 ) return (true);
                
                return (false);
            }
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
        </script>
</head>

<body leftmargin=0 topmargin=0>
<form name = "form1" enctype = "multipart/form-data" method = "POST">
	
	<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
	<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
	<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
	<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
	<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
	<input type = "hidden" name = "p_process"     value = "">
	<input type = "hidden" name = "p_userid"      value = "<%= v_userid %>">
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/cp/m_board_on.gif','/images/cp/m_company_on.gif','/images/cp/m_sdata_on.gif','/images/cp/m_swdata_on.gif','/images/user/button/btn_idpw_on.gif','/images/user/button/btn_login_on.gif','/images/cp/m_data_on.gif')">
<!-- top -->
<table width="100%" height="71" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_top.gif">
  <tr>
    <td valign="top"><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_subtotal.gif">
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
					  <!-- sw자료실쓰기 -->
					  <table width="800" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#D5D5D5" >
                          <tr> 
                            <td width="101" class="tbl_grtit_l"><font color="#FF6600" size="-2">▶</font>제목</td>
                            <td class="tbl_btit"><input style="padding:1; border:1 solid #D1D1D1;"  type = "text"  onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "115" maxlength = "100" value = "<%= v_title %>"> 
                            </td>
                          </tr>
                          <tr> 
                            <td width="101" class="tbl_grtit_l"><font color="#FF6600" size="-2">▶</font>작성자</td>
                            <td class="tbl_bleft"><%= v_name %></td>
                          </tr>

<% 
	if(v_upfilecnt>0){%>
                          <tr>
                            <td class="tbl_grtit_l" ><font color="#FF6600" size="-2">▶</font>이전파일</td>
                            <td class="tbl_bleft">
							<%		
								if( v_realfileVector != null ) { 
									for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
									String v_realfile = (String)v_realfileVector.elementAt(i);
									String v_savefile = (String)v_savefileVector.elementAt(i);  
									String v_fileseq = (String)v_fileseqVector.elementAt(i);  	

									
									if(v_realfile != null && !v_realfile.equals("")) {  %>
										&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
									
										<%= v_realfile%></a> &nbsp;
										<input type = "checkbox"  name = "p_fileseq<%=i%>" value = "<%= v_fileseq%>"> (삭제시 체크)<br>
										<input type = "hidden" name = "p_realfile"  value = <%=v_realfile%>>
										<input type = "hidden" name = "p_savefile<%=i%>"  value ="<%=v_savefile%>">
							<%		}   %>
						<%			}
								}
						%>
							</td>
						  </tr>
<%}%>
	                      <tr>
	                          <td width="71" class="tbl_grtit_l" align = "center"><font color="#FF6600" size="-2">▶</font>첨부파일</td>
	                          <td width="704"  align = "left" class = "table-content">
							  <input type="FILE" name="p_file1" size="100" class="input"><br>
							  <input type="FILE" name="p_file2" size="100" class="input"><br>
							  <input type="FILE" name="p_file3" size="100" class="input"><br>
							  <input type="FILE" name="p_file4" size="100" class="input"><br>
							  <input type="FILE" name="p_file5" size="100" class="input"><br>
							</td>
                          </tr>
                          <tr> 
                            <td width="101" valign="top" class="tbl_grtit_l" ><font color="#FF6600" size="-2">▶</font>내용</td>
                            <td  class="tbl_contents">
								<!-- DHTML Editor  -->
								<%@ include file="/include/DhtmlEditor.jsp" %>
								<!-- DHTML Editor  -->	
							</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="center">
					    <table width="800" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="418" align="right">&nbsp; </td>
                            <td align="right"><a href = "javascript:update();"><img src="/images/cp/btn_register.gif" border="0"></a>
                            &nbsp;&nbsp;<a href = "javascript:selectList();"><img src="/images/user/button/btn_list.gif"  border="0"></a>
							&nbsp;&nbsp;<a href = "javascript:cancel();"><img src="/images/user/button/btn_cancel.gif"  border="0"></a></td>
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