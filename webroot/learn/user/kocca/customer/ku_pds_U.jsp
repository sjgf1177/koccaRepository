<%
//**********************************************************
//  1. 제      목: 고객센타 > 학습자료실
//  2. 프로그램명 : ku_pds_U.jsp
//  3. 개      요: 학습자료실
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

	String content = "";
	String width = "650";
	String height = "200";	

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    DataBox dbox = (DataBox)request.getAttribute("selectHomePageBoard");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        //v_content     = dbox.getString("d_content");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
		content = StringManager.replace(dbox.getString("d_content"),"\n;","<br>");
        v_title = StringManager.replace(v_title, "`", "'");
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

box.put("leftmenu","04");                       // 메뉴 아이디 세팅
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

 //데이터를 입력한다.
function update() {
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
		
	var islimit1 = true;
	var islimit2 = true;
	var islimit3 = true;
	var file1 = document.form1.p_file1.value;
	var file2 = document.form1.p_file2.value;
	var file3 = document.form1.p_file3.value;
	
	if(file1!="") {islimit1 = limitFile(file1);}
	if(!islimit1){  return;	}
	
	if(file2!="") {islimit1 = limitFile(file2);}
	if(!islimit2){  return;	}
	
	if(file3!="") {islimit1 = limitFile(file3);}
	if(!islimit3){  return;	}
	
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
	document.form1.p_process.value = "update";
    document.form1.submit();
}

//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//내용입력을 취소하고 리스트로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
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

<!-- /////////////////////////////////////////////////////////-->
<!--
  파일확장자체크 INCLUDE
  
  1. page import = "com.credu.system.*" 추가
  2. 자바스크립트 호출부분 추가
    예제)
    //파일 확장자 필터링  
	var islimit = true;
	var file = document.form1.p_file1.value;  
	if(file!="") {
	    islimit = limitFile(file);    //return값 true/false
	}
	
	if(islimit) {
        document.form1.submit();
	}else{
	    return;
	}

-->
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!-- /////////////////////////////////////////////////////////-->


<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_seq"		 value = "<%= v_seq %>">
		<input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq %>">
		<input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
		<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
		<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
		<input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
		<input type = "hidden" name = "p_content"    value = "">
		<input type = "hidden" name = "p_process"	 value = "">

		  <!-- center start -->
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 학습자료실</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_pds.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_pds.gif" height="33"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="9"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="2"><img src="/images/user/kocca/customer/bar_1vs1_top.gif" width="680" height="7" ></td>
                          </tr>
                          <tr> 
                            <td height="2" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="textcolor_customer01">질문제목</td>
                            <td width="605" class="tbl_gleft01"><input name="p_title" type="text" class="input3" style="width:300" value="<%=v_title%>"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="textcolor_customer01">작성자</td>
                            <td width="605" class="tbl_gleft02">운영자</td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="2"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr valign="top"> 
                            <td colspan="2" class="tbl_bleft">
								<!-- DHTML Editor  -->
								<%@ include file="/include/DhtmlEditor.jsp" %>
								<!-- DHTML Editor  -->
							</td>
                          </tr>
						  <tr> 
                            <td colspan="2" height="1"></td>
                          </tr>
						  <tr> 
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="textcolor_customer01">첨부파일</td>
                           	<td class="tbl_gleft"> 
<!---------파일첨부여부 ------------>
								<table width="604" border="0" cellspacing="0" cellpadding="0">
									<tr> 
									  <td width="604">
<% if(v_upfilecnt>0){%>
<%		
if( v_realfileVector != null ) { 
	for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
		String v_realfile = (String)v_realfileVector.elementAt(i);
		String v_savefile = (String)v_savefileVector.elementAt(i);  
		String v_fileseq  = (String)v_fileseqVector.elementAt(i);  	

		if(v_realfile != null && !v_realfile.equals("")) {  %>
										&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
									
										<%= v_realfile%></a> &nbsp;
										<input type = "checkbox"  name = "p_fileseq<%=i%>"	value = "<%= v_fileseq%>"> (삭제시 체크)<br>
										<input type = "hidden"	  name = "p_realfile"		value = "<%=v_realfile%>">
										<input type = "hidden"	  name = "p_savefile<%=i%>" value = "<%=v_savefile%>">
<%				}   %>
<%			}
		}
%>
											  </td>
<%}%>	<!---------파일첨부여부 ------------>
									</tr>
									<tr>
									  <td class="tbl_bleft">
										<input type="FILE" name="p_file1" size="60" class="input3"><br>
										<input type="FILE" name="p_file2" size="60" class="input3"><br>
										<input type="FILE" name="p_file3" size="60" class="input3"><br>
									  </td>
									</tr>
							  </table>
							</td>
                          </tr>
                          <tr> 
                            <td colspan="2" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="2" class="linecolor_customer02"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="20"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="27" class="linecolor_notice"><table width="52" border="0" align="right" cellpadding="0" cellspacing="0">
                                      <tr> 
                                        <td><a href="javascript:update()"><img src="/images/user/kocca/button/btn_save.gif"></a><a href="javascript:cancle()"><img src="/images/user/kocca/button/btn_list.gif"></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height="1" bgcolor="#cccccc"></td>
                                </tr>
                              </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="20">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->