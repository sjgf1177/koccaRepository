<%
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 답변화면
//  2. 프로그램명: zu_HomePageQna_A.jsp
//  3. 개      요: 홈페이지 Q&A 답변화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    String  v_types      = box.getString("p_types");
    String  v_title      = "";
    String  v_contents   = "";
    String  v_indate     = "";
    String  v_inuserid   = "";
    String  v_inusername = "";
	String  v_categorycd = "";
	Vector  v_realfileVector = null;
	Vector  v_savefileVector = null;
	String  v_realmotion  = "";
	String  v_savemotion = "";
	String content = "";
	String v_name="";
	String v_content = "";
	String width = "650";
	String height = "200";

	DataBox dbox = (DataBox)request.getAttribute("selectQna");

	v_inuserid   = dbox.getString("d_inuserid");
	v_types = dbox.getString("d_types");
	v_indate    = dbox.getString("d_indate");
	v_title    = dbox.getString("d_title");
	v_inuserid   = dbox.getString("d_inuserid");
	v_types = dbox.getString("d_types");
	v_indate    = dbox.getString("d_indate");
	v_title    = dbox.getString("d_title");
	v_contents          = dbox.getString("d_contents");
	//v_content = dbox.getString("d_contents");
	//content   = dbox.getString("d_contents");
	v_name = dbox.getString("d_name");
	v_realfileVector = (Vector)dbox.getObject("d_realfile");
	v_savefileVector = (Vector)dbox.getObject("d_savefile");
	v_realmotion  = dbox.getString("d_realmotion");
	v_savemotion  = dbox.getString("d_savemotion");
	v_categorycd  = dbox.getString("d_categorycd");

    String s_userid    = box.getSession("userid");
    String s_name      = box.getSession("name");
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
<script language="JavaScript" type="text/JavaScript">
<!--

//이전 페이지로 이동
function cancel() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//답변 내용을 등록저장
function insert() {
    if(document.all.use_editor[0].checked) {        
    form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
    form1.content.value = document.all.txtDetail.value;
    }
    if (blankCheck(document.all.p_title.value)) {
        alert("제목을 입력하세요!");
        document.all.p_title.focus();
        return;
    }                
    if (realsize(document.all.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.all.p_title.focus();
        return;
    }                     
    if (blankCheck(document.all.content.value)) {
        alert("내용을 입력하세요!");
        document.all.content.focus();
        return;
    }       
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "reply";              
    document.form1.submit();
}

//입력 내용의 유무를 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}
function realsize( value ) {//입력글자수 체크
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
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_qna.jpg" ></td>
                    </tr>
                  </table>
                  <!-- qna table -->
				  <form name = "form1" enctype = "multipart/form-data" method = "post">
				  <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
				  <input type = "hidden" name = "p_searchtext"  value = "">
				  <input type = "hidden" name = "p_select"      value = "">
				  <input type = "hidden" name = "p_pageno"      value = "">
				  <input type = "hidden" name = "p_process">
				  <input type = "hidden" name = "p_categorycd"  value = "<%=v_categorycd%>">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="94"><img src="/images/user/support/text_title.gif"></td>
                      <td colspan="5" class="tbl_btit"><%=v_title%></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_pe.gif"></td>
                      <td width="149" class="tbl_gleft"><%=v_name%></td>
                      <td width="95"><img src="/images/user/support/text_day.gif"></td>
                      <td width="93" class="tbl_gleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%> </td>
                      <td width="95">&nbsp;</td>
                      <td width="149" class="tbl_gleft">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td colspan="6" class="tbl_contents"><%=v_contents %></td>
                    </tr>
                   
                    <tr bgcolor="B8D5E5"> 
                      <td height="1" colspan="6"></td>
                    </tr>
					  <tr> 
                      <td height="10" colspan="6"></td>
                    </tr>
                  </table>
				  <!-- 답변table -->
				  <table width="675" border="0" cellspacing="0" cellpadding="0">
                  
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/text_answertit.gif" width="675" height="27"></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="97"><img src="/images/user/support/text_retitle.gif" width="94" height="27"></td>
                      <td width="579" class="tbl_btit"> <input type="text" size="70" maxlength="200" name="p_title"> 
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_repe.gif" width="94" height="27"></td>
                      <td class="tbl_gleft"><%=s_name%></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/support/text_reday.gif"></td>
                      <td class="tbl_gleft"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr>
                      <td>
					  <table width="94" height="100%" border="0" cellpadding="0" cellspacing="0" background="../../../images/user/support/text_bg_head2.gif">
                          <tr> 
                            <td height="9"  valign="top"><img src="/images/user/support/text_top_head2.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center"><img src="/images/user/support/text_addfile_a2.gif"></td>
                          </tr>
                          <tr> 
                            <td height="8" valign="bottom"><img src="/images/user/support/text_bo_head2.gif" ></td>
                          </tr>
                        </table>					  
					  </td>
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
					  <td width="257" align="right"><a href="javascript:insert()"><img src="/images/user/button/btn_register.gif" border="0"></a>&nbsp;<a href="javascript:cancel()"><img src="/images/user/button/btn_cancel.gif" border="0"></a> 
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
