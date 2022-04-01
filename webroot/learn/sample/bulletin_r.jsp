<%
//**********************************************************
//  1. 제      목: Sample 자료실에서의 상세보기화면
//  2. 프로그램명: bulletin_r.jsp
//  3. 개      요: Sample 자료실에서의 상세보기화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 9
//  7. 수      정: 이정한 2003. 4. 9
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%-- <%@ page import = "com.credu.sample.*" %> --%>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_usernm = "", v_userid = "", v_regdt = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";

BulletinData data = (BulletinData)request.getAttribute("selectPds");

v_seq = data.getNum();
v_userid = data.getUserid();
v_usernm = data.getName();
v_title = data.getTitle();
v_content = data.getContent();
v_realfileVector = data.getRealfileVector();
v_savefileVector = data.getSavefileVector();
v_realmotion = data.getRealmotion();
v_savemotion = data.getSavemotion();
v_regdt = data.getRegdt();   
%>

<html>
    <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset = euc-kr">
    <title>Sample 자료실 </title>
    <link rel = "StyleSheet" href = "/learn/sample/sub_03.css">
        <script language = "javascript">
            function selectList() {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "";     
            	document.form1.submit();	
            }            
            function updatePage() {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "updatePage";
            	document.form1.submit();
            }    
            function deleteAction() {
                if (confirm("정말로 삭제하시겠습니까?")) {
                    document.form1.action = "/servlet/controller.sample.BulletinServlet";
                    document.form1.p_process.value = "delete";
                    document.form1.submit();
                }
                else {
                    return;
                }
            }
        </script>
    </head>
    <body bgcolor = "#FFFEF6" leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
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
                                    <form name = "form1" method = "post">
                                        <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
                                        <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
                                        <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
                                        <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
                                        <input type = "hidden" name = "p_process"  value = "">
                                        <input type = "hidden" name = "p_userid" value = "<%= v_userid %>">
                                            
                                    <tr>
                                        <td class = "table-title" width = "20%" align = "center">제목</td>
                                        <td class = "table-content" width = "80%" align = "left">&nbsp;&nbsp;<%= v_title %></td>
                                    </tr>
                                    <tr>
                                        <td class = "table-title" width = "20%" align = "center">작성자</td>
                                        <td class = "table-content" width = "80%" align = "left">&nbsp;&nbsp;<%= v_usernm %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class = "table-title" width = "20%" align = "center">등록일</td>
                                        <td class = "table-content" width = "80%" align = "left">&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_regdt, "yyyy/MM/dd") %></td>
                                    </tr>
                                    <tr>
                                        <td class = "table-title" width = "20%" align = "center">내용</td>
                                        <td class = "table-content" width = "80%" align = "left">
                                        <table border = "0" cellpadding = "4" cellspacing = "0" width = "100%">
                                    <tr>
                                        <td width = "100%"><%= StringManager.replace(v_content,"\r\n","<br>") %></td>
                                    </tr>
                                </table>            				
    		          </td>
                        </tr>
             <%  if(box.getBoolean("p_isUpload")) {    //    파일첨부 여부 %>
                        <tr>
                            <td class = "table-title" width = "20%" align = "center">첨부파일 </td>
                            <td class = "table-content" width = "80%" align = "left">
                       <%   for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                                   String v_realfile = (String)v_realfileVector.elementAt(i);
                                   String v_savefile = (String)v_savefileVector.elementAt(i);
                                     if(v_realfile != null) {  %>
                                 &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a><br>
                                 <input type = "hidden" name = "p_savefile"  value = "<%= v_savefile%>">
                        <%        }    
                                }   %>
                            </td>
                        </tr>
                        <tr>
                            <td class = "table-title" width = "20%" align = "center">첨부동영상 </td>
                            <td class = "table-content" width = "80%" align = "left">
                                 &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savemotion%>&p_realfile=<%= v_realmotion%>'><%= v_realmotion%></a><br>
                            </td>
                        </tr>
              <%    }   %>            			
                    </table>
                    <table border = "0" cellpadding = "0" cellspacing = "0" width = "100%">      
                        <tr>
                            <td height = "10"></td>
                        </tr>
                        <tr>
                            <td height = "7"></td>
                        </tr>
                        <tr>
                            <td align = "right" height = "20" valign = "bottom">
                                <%  if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    수정 여부 %>
                                    <a href = "javascript:updatePage();"><img src = "/learn/sample/images/modify.gif" border = "0"></a>
                                <%  }
                                       if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {    //    삭제 여부 %>
                                    <a href = "javascript:deleteAction();"><img src = "/learn/sample/images/delete.gif" border = "0"></a>
                                <%  }   %>
                                <a href = "javascript:selectList();"><img src = "/learn/sample/images/list.gif" border = "0"></a>
                            </td>
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


