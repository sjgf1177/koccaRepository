<% 
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 수정화면
//  2. 프로그램명: zu_HomePageQna_U.jsp
//  3. 개      요: 홈페이지 Q&A 수정화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 2
//  7. 수      정: 이연정 2005. 7. 2
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

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "", v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");
int v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
String content = "";
String width = "650";
String height = "200";	

String s_name = box.getSession("name");

Vector v_realfileVector = null;          //      원파일명 배열
Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

DataBox dbox = (DataBox)request.getAttribute("selectQna");

v_inuserid   = dbox.getString("d_inuserid");
v_types      = dbox.getString("d_types");
v_indate     = dbox.getString("d_indate");
v_title     = dbox.getString("d_title");
v_seq       = dbox.getInt("d_seq");

content     = StringManager.replace(dbox.getString("d_contents"),"\n;","<br>");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
String s_userid  = box.getSession("userid");
box.put("leftmenu","02");                       // 메뉴 아이디 세팅

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
       
//상세보기 페이지로 이동            
function cancel(seq, userid, types) {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "selectView";
    document.form1.p_seq.value = seq;
    document.form1.p_userid.value    = userid;
	document.form1.p_types.value    = types;
    document.form1.submit();
}

//수정한 내용을 저장
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
    //alert(document.form1.p_fileseq[0].value);
    //return;
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "update";                
    document.form1.submit();
} 

//입력글자의 유무를 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//글자수 체크
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
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; Q&amp;A</td>
              </tr>
              <tr> 
                <td align="center" valign="top">
                 <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_qna.jpg" ></td>
                    </tr>

                  </table>
                  <!-- qna table -->
                  <form name = "form1" enctype = "multipart/form-data" method = "post">
				  <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
			      <input type = "hidden" name = "p_types"      value = "<%= v_types %>">
			      <input type = "hidden" name = "p_searchtext" value = "">
			      <input type = "hidden" name = "p_select"     value = "">
			      <input type = "hidden" name = "p_pageno"     value = "">
			  	  <input type = "hidden" name = "p_userid"     value = "<%=v_inuserid%>">
			      <input type = "hidden" name = "p_process">
				  <input type = "hidden" name = "p_upfilecnt"  value = "<%=v_upfilecnt %>">


                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="97"><img src="/images/user/support/text_title.gif"></td>
                      <td width="579" class="tbl_gleft"> <input type="text" name = "p_title" size = "84" maxlength = "100" value = "<%= v_title %>"> 
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_pe.gif"></td>
                      <td class="tbl_gleft"><%= s_name %></td>
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

		<!---------파일첨부여부 ------------>
<% if(v_upfilecnt>0){%>

                    <tr> 
                      <td height="100" valign="top">
					  <table width="94" height="100%" border="0" cellpadding="0" cellspacing="0" background="../../../images/user/support/text_bg_head.gif">
                          <tr>
                            <td height="9"  valign="top"><img src="../../../images/user/support/text_top_head.gif"></td>
                          </tr>
                          <tr>
                            <td align="center"><img src="/images/user/support/text_prefile_a.gif"></td>
                          </tr>
                          <tr>
                            <td height="8" valign="bottom"><img src="../../../images/user/support/text_bo_head.gif" ></td>
                          </tr>
                        </table></td>
                      <td class="tbl_gleft"> 
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
                    <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                  </tr>
		<!---------파일첨부여부 끝 ------------>
                  <tr> 
                      <td><img src="/images/user/support/text_addfile.gif"></td>
                      <td class="tbl_gleft">
						<input type="FILE" name="p_file1" size="45" class="input"><br>
						<input type="FILE" name="p_file2" size="45" class="input"><br>
						<input type="FILE" name="p_file3" size="45" class="input"><br>
						<input type="FILE" name="p_file4" size="45" class="input"><br>
						<input type="FILE" name="p_file5" size="45" class="input"><br>
					  </td>
                    </tr>
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
                      <td width="257" align="right"> 
					  <!--등록하기-->
					  <a href = "javascript:update();"><img src="/images/user/button/btn_register.gif" border="0"></a> 
					  <!--취소하기-->
                      <a href = "javascript:cancel('<%=v_seq%>','<%=v_inuserid%>','<%=v_types%>')"><img src="/images/user/button/btn_cancel.gif" border="0"></a> 
                      </td>
                    </tr>
                  </table></td>
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

