<%
//**********************************************************
//  1. 제      목: Sample 자료실에서의 수정화면
//  2. 프로그램명: bulletin_uopen.jsp
//  3. 개      요: Sample 자료실에서의 수정화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 9
//  7. 수      정: 이정한 2003. 4. 9
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.sample.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_usernm = "", v_userid = "", v_regdt = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;

BulletinData data = (BulletinData)request.getAttribute("selectPds");

v_seq = data.getNum();
v_userid = data.getUserid();
v_usernm = data.getName();
v_title = data.getTitle();
v_content = data.getContent();
v_realfileVector = data.getRealfileVector();
v_savefileVector = data.getSavefileVector();
v_regdt = data.getRegdt();
%>

<html>
    <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset=euc-kr">
    <title>Sample 자료실 </title>
    <link rel = "StyleSheet" href = "/learn/sample/sub_03.css">
        <script language = "javascript">
            function selectList() {
                document.form1.action = "/servlet/controller.sample.BulletinServlet";
                document.form1.p_process.value = "";     
                document.form1.submit();	
            }            
            
            function cancel() {
                document.form1.action = "/servlet/controller.sample.BulletinServlet";
                document.form1.p_process.value = "select";
                document.form1.submit();
            }
            
            function update() { 
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
                if (blankCheck(document.form1.p_content.value)) {
                    alert("내용을 입력하세요!");
                    document.form1.p_content.focus();
                    return;
                }	    
                document.form1.action = "/servlet/controller.sample.BulletinServlet";
                document.form1.p_process.value = "updateAtOpenWin";                
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
    <body bgcolor = "#FFFEF6" leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
    	<form name = "form1" enctype = "multipart/form-data" method = "POST">
	        <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
	        <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
	        <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
	        <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
	        <input type = "hidden" name = "p_process"  value = "">
	        <input type = "hidden" name = "p_userid" value = "<%= v_userid %>">
	        
	        <table width = "100%" border = "0" cellspacing = "0" cellpadding = "0" height = "100%">
	            <tr> 
	                <td height = "76" background = "/learn/sample/images/title_bg.gif">
	                    <table width = "769" border = "0" cellspacing = "0" cellpadding = "0">
	                        <tr> 
	                            <td width = "136"><img src = "/learn/sample/images/top_title_03.gif" width = "136" height = "76"></td>
	                            <td>
	                                <table width = "633" height = "76" border = "0" cellpadding = "0" cellspacing = "0">
	                                    <tr> 
	                                        <td height = "31" valign = "bottom"><img src = "/learn/sample/images/03_4_00.gif" width = "98" height = "22"></td>
	                                    </tr>
	                                    <tr> 
	                                        <td height = "38" align = "right" valign = "top">
	                                            <table width = "628" height = "38" border = "0" cellpadding = "0" cellspacing = "0">
	                                                <tr> 
	                                                    <td class = "title_text">Sample 자료실입니다.<br>유익한 자료를 등록해 주세요.</td>
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
	            <tr> 
	                <td valign = "top"> 
	                    <table width = "769" border = "0" cellpadding = "0" cellspacing = "0">
	                        <tr>
	                            <td width = "29" height = "19">&nbsp;</td>
	                            <td height = "19">&nbsp;</td>
	                        </tr>
	                        <tr> 
	                            <td width = "29">&nbsp;</td>
	                            <td width = "710" valign = "top">
	                                <table cellspacing = "1" cellpadding = "2" border = "0" width = "100%" class = "table_out"> 	                                    
	                                    <tr>
	                                        <td class = "table-title" width = "20%" align = "center">제목</td>
	                                        <td class = "table-content" width = "80%" align = "left">
	                                            <input type = "text"  onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background = '#ffffff'" 
	                                            name = "p_title" size = "50" maxlength = "100" value = "<%= v_title %>"></td>
	                                    </tr>
	                                    <tr>
	                                        <td class = "table-title" width = "20%" align = "center">작성자</td>
	                                        <td class = "table-content" width = "80%" align = "left">&nbsp;&nbsp;<%= v_usernm %></td>
	                                    </tr>
	                                    <tr>
	                                        <td class = "table-title" width = "20%" align = "center">등록일</td>
	                                        <td class = "table-content" width = "80%" align = "left">&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_regdt, "yyyy/MM/dd") %></td>
	                                    </tr>
	                                    <tr>
	                                        <td class = "table-title" width = "20%" align = "center">내용</td>
	                                        <td class = "table-content" width = "80%" align = "left">
	                                            <textarea  onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name='p_content' align = "left" cols = '80' rows = '20' wrap = 'hard' style = "width: 100%; height: 200;"><%= v_content%></textarea>      				
	                		            </td>
	                                    </tr>
	                           <%  if(box.getBoolean("p_isUpload")) {    //    파일첨부 여부 %>
	                                    <tr>
	                                        <td class = "table-title" width = "20%" align = "center">첨부파일</td>
	                                        <td class = "table-content" width = "80%" align = "left">
	                                        <%   for(int i = 0; i < v_realfileVector.size(); i++) {  
	                                                     String v_realfile = (String)v_realfileVector.elementAt(i);
                                                             String v_savefile = (String)v_savefileVector.elementAt(i);
	                                                    if(v_realfile != null) {  %>                                                                                   
                                                    &nbsp;<%= v_realfile%>
                                                    &nbsp;<input type = "checkbox"  onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_savefile" value = "<%= v_savefile%>"> (삭제시 체크)
                                                    <br>                                                    
                                                    <input type = "hidden" name = "p_realfile"  value = "<%= v_realfile%>">
                                                <%        }                                                                
                                                         }   %>
                                                </td>
	                                    </tr>	                          
					<tr>
                                                <td class = "table-title" width = "20%" align = "center">파일첨부</td>
                                                <td class = "table-content" width = "80%" align = "left">
                                                    <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file"><br>
                                                    <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file"><br>
                                                    <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file"><br>
                                                    <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file"><br>
                                                    <input type = "file" onFocus = "this.style.background = '#FFFAE1'" onBlur = "this.style.background='#ffffff'" name = "p_file">
                                                    <%//= JspUtil.getMaxFileSize("bbs.file.size") %>
                                                </td>
					</tr>
				<%  }   %>         			
	                                </table>
	                                <table border = "0" cellpadding = "0" cellspacing = "0" width = "100%">      
	                                    <tr>
	                                        <td height = "10"></td>
	                                    </tr
	                                    <tr>
	                                        <td height = "7"></td>
	                                    </tr>
	                                    <tr>
	                                        <td align = "right" height = "20" valign = "bottom">
	                                        <a href = "javascript:update();"><img src = "/learn/sample/images/save.gif" border = "0"></a>
					    <a href = "javascript:cancel()"><img src = "/learn/sample/images/cancel.gif" border = "0"></a>
	                                        <a href = "javascript:selectList();"><img src = "/learn/sample/images/list.gif" border = "0"></a></td>
	                                    </tr>					
	                                </table>
	                            </td>
	                        </tr>
	                    </table>
	                </td>
	            </tr>
	        </table>
	        <%@ include file = "/learn/library/getJspName.jsp" %>
    	</form>
	</body>
</html>


