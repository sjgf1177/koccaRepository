<%
//**********************************************************
//  1. 제      목: 지식공유 수정
//  2. 프로그램명: zu_KnowBoard_U
//  3. 개      요: 지식공유 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2005. 9.
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
	String p_categorycd = box.getString("p_categorycd");            // request 카테고리
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
    String v_categorycd = "";
    String v_categorynm = ""; 
    String v_types = "";
     
	String content = "";
	String width = "650";
	String height = "200";	

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    DataBox dbox = (DataBox)request.getAttribute("SelectView");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        //v_content     = dbox.getString("d_contents");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
		content = StringManager.replace(dbox.getString("d_contents"),"\n;","<br>");
        v_title = StringManager.replace(v_title, "`", "'");
        
        v_types      = dbox.getString("d_types");
    	v_categorycd = dbox.getString("d_categorycd");
    	v_categorynm = dbox.getString("d_categorynm");        
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    box.put("leftmenu","04"); 
 
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topMyClass.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<!------- 파일확장자체크 INCLUDE ------------>
<%@ include file = "/learn/library/fileFilter.jsp" %>
<script language="JavaScript" type="text/JavaScript">
<!--

//변경된 내용을 수정
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
    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }
    if (blankCheck(document.form1.content.value)) {
        alert("내용을 입력하세요!");
        document.form1.p_content.focus();
        return;
    }
    //alert(document.form1.content.value);
	document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "update";
    document.form1.submit();
}

//리스트 화면으로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
     document.form1.p_process.value = "selectList";
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
    
//글자 사이즈 체크
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


// 카테고리 선택
function openCategory(){
    window.self.name = "winSelectList";
    open_window("openwinSelectList","","100","100","390","400","","","","yes","");
    document.form1.target = "openwinSelectList";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "OpenCategoryPage";
    document.form1.submit();
    document.form1.target = window.self.name;    

}
//-->
</script>
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 나의 강의실 &gt; 지식공유 &gt; 최근지식</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/myclass/type1/tit_knowledge.jpg" alt="나의강의실"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 소제목 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" height="5" colspan="4"><img src="/images/user/myclass/st_know_re.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <!-- 자료실 table -->
				<form name = "form1" enctype = "multipart/form-data" method = "post">				
				<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
				<input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
				<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
				<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
				<input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
				<input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
				<input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">
			    <input type = "hidden" name = "p_process">
				<input type = "hidden" name = "p_types"     value = "<%= v_types %>">
				<input type = "hidden" name = "p_categorycd"  value = "<%=p_categorycd%>">				
			    
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="2"><img src="/images/user/myclass/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="97"><img src="/images/user/myclass/text_title.gif"></td>
                      <td width="579" class="tbl_gleft"> 
					    <input type="text" name="p_title" size="70" maxlength="100" value ="<%= v_title %>"> 
                      </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/myclass/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/myclass/text_name.gif"></td>
                      <td class="tbl_gleft"><%= v_name %></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/myclass/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/myclass/text_day.gif"></td>
                      <td class="tbl_gleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/myclass/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/myclass/text_cate.gif"></td>
                      <td width="409" class="tbl_gleft"><input type="text" name="p_catenm" size="30" value='<%=v_categorynm%>' disabled><input type=hidden name='p_catecd'  value='<%=v_categorycd%>'> 
                        <a href="javascript:openCategory();"><img src="/images/user/button/btn_c_catesel.gif" alt="카테고리선택" align="absmiddle"></a></td>
                    </tr>

                  <tr> 
                    <td colspan="2" valign=top><img src="/images/user/myclass/search_line.gif"></td>
                  </tr>
                  <tr> 
                      <td><img src="/images/user/myclass/text_file2.gif"></td>
                      <td class="tbl_gleft">
						<input type="FILE" name="p_file1" size="45" class="input"><br>
						<input type="FILE" name="p_file2" size="45" class="input"><br>
						<input type="FILE" name="p_file3" size="45" class="input"><br>
						<input type="FILE" name="p_file4" size="45" class="input"><br>
						<input type="FILE" name="p_file5" size="45" class="input"><br>
		<!---------파일첨부여부 ------------>
<% if(v_upfilecnt>0){%>
                      
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
<%}%>
		<!---------파일첨부여부 끝 ------------>							
					  </td>
                    </tr>
                    <tr> 
                      <td colspan="2"><img src="/images/user/myclass/search_line.gif"></td>
                    </tr>
                    <tr>
                      <td colspan="2"><img src="/images/user/myclass/text_contents.gif"></td>
                    </tr>
                    <tr valign="top"> 
                      <td colspan="2" class="tbl_contents">
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                      </td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="2"></td>
                    </tr>
                    <tr bgcolor="B8D5E5"> 
                      <td height="5" colspan="2"><img src="/images/user/myclass/class_tb_bo.gif" width="675" height="5"></td>
                    </tr>
                  </table>
				  </form>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right">&nbsp; </td>
                      <td width="257" align="right"><a href = "javascript:update();"><img src="/images/user/button/btn_register.gif" alt="등록" border="0"></a>
                      <a href = "javascript:cancel();"><img src="/images/user/button/btn_cancel.gif" alt="취소" border="0"></a>
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

