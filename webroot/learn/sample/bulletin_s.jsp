<%
//**********************************************************
//  1. 제      목: Sample 자료실에서의 리스트화면
//  2. 프로그램명: bulletin_s.jsp
//  3. 개      요: Sample 자료실에서의 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 9
//  7. 수      정: 이정한 2003. 4. 9
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%-- <%@ page import = "com.credu.sample.*" %> --%>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int v_seq = 0,v_readcnt = 0,i = 0;

String v_userid = "", v_usernm = "", v_position = "", v_regdt = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");
String s_usernm = box.getSession("username");
     
%>

<html>
    <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset = euc-kr">
    <title>Sample 자료실 </title>
    <link rel = "StyleSheet" href = "/learn/sample/sub_03.css">
        <script language = "javascript">
            function go(index) {
                 document.form1.p_pageno.value = index;
                 document.form1.action = "/servlet/controller.sample.BulletinServlet";
                 document.form1.p_process.value = "";     
                 document.form1.submit();
            }            
            function goPage(pageNum) {
                 document.form1.p_pageno.value = pageNum;
                 document.form1.action = "/servlet/controller.sample.BulletinServlet";
                 document.form1.p_process.value = "";     
                 document.form1.submit();
            }                
            function selectList() {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "";   
            	document.form1.p_pageno.value = "1";       
            	document.form1.submit();
            }            
            function insertPage() {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "insertPage";  
            	document.form1.p_pageno.value = "<%= v_pageno %>";    
            	document.form1.submit();
            }
            function select(num, upfilecnt, userid) {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "select";
            	document.form1.p_seq.value = num;
            	document.form1.p_userid.value = userid;
            	document.form1.p_pageno.value = "<%= v_pageno %>"; 
            	document.form1.submit();
            }
            function updateOpenPage(num) {
                window.self.name = "winSelectList";     //      opener 객체에 name 을 지정한다
                window.open("", "openWinUpdate", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 700, height = 500, top=0, left=0");      
                document.form1.target = "openWinUpdate"
                document.form1.action = "/servlet/controller.sample.BulletinServlet";
                document.form1.p_process.value = "updateOpenPage";
                document.form1.p_seq.value = num;
                document.form1.submit();
            }    
            function formMailing() {
            	document.form1.action = "/servlet/controller.sample.BulletinServlet";
            	document.form1.p_process.value = "formMail";
            	document.form1.submit();
            }
        </script>
    </head>
    <body bgcolor = "#FFFEF6" leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
        <form name = "form1" method = "post">
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
        <input type = "hidden" name = "p_userid" value = "">

        <table width = "100%" height = "100%" border = "0" cellpadding = "0" cellspacing = "0">
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
                                </table></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr> 
                    <td valign = "top">
                        <table width = "769" border = "0" cellspacing = "0" cellpadding = "0">
                            <tr> 
                                <td width = "29" height = "19">&nbsp;</td>
                                <td height = "19">&nbsp;</td>
                            </tr>
                            <tr> 
                                <td width = "29">&nbsp;</td>
                                <td height = "300" valign = "top"> 
                                    <table width = "710" border = "0" cellspacing = "0" cellpadding = "0">
                                        <tr> 
                                            <td height = "20" valign = "bottom">&nbsp;</td>
                                        </tr>
                                        <tr> 
                                            <td align = "right" width = "515" valign = "top">
                                                <select name = "p_select" class = "input">
                                                    <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
                                                    <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
                                                    <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                                                </select>
                                                <input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                                                 name = "p_searchtext" value = "<%=v_searchtext %>" size = "15" maxlength = "15">
                                                </td>
                                                <td align = "right" width = "65" valign = "top">
                                                    <a href = "javascript:selectList();"><img src = "/learn/sample/images/find.gif" border = "0" valign = "middle"></a>
                                                </td>
                                                <td width = "65" align = "right" valign = "top">
                                                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                                                    <a href = "javascript:insertPage()"><img src = "/learn/sample/images/input.gif" border = "0"></a>
                                                <%  }   %>
                                                </td>
                                                <td width = "65" align = "right" valign = "top">
                                                    <a href = "javascript:formMailing()"><img src = "/images/button/mail_send.gif" border = "0"></a>
                                                </td>
                                            </tr>
                                            <tr>
			      	        <td height = "5" colspan = "3"></td>
			            </tr>
			            <tr>
			      	        <td width = "710" colspan = "3">
                                                    <table width = "710" border = "0" cellpadding = "0" cellspacing = "0">
                                                        <tr> 
                                                            <td>                                                      
                                                                <table cellspacing = "1" cellpadding = "2" border = "0" width = "100%" class = "table_out">
                                                                    <tr class = "table_top_01"> 
                                                                        <td width = "8%"  align = "center">No</td>
                                                                        <td width = "34%" align = "center">제목</td>
                                                                        <td width = "8%" align = "center">작성자</td>
                                                                        <td width = "12%" align = "center">등록일</td>
                                                                        <td width = "8%"  align = "center">조회수</td>
                                                                        <td width = "8%"  align = "center">첨부파일</td>
                                                                        <td width = "8%"  align = "center">동영상</td>
                                                                    </tr>
                                                                    <%                                                                                                                                                
                                                                        for(i = 0; i < list.size(); i++) {
                                                                            BulletinData data = (BulletinData)list.get(i);
                                                                            v_userid   = data.getUserid();
                                                                            v_usernm   = data.getName();
                                                                            v_regdt    = data.getRegdt();
                                                                            v_title    = data.getTitle();
                                                                            v_seq      = data.getNum();
                                                                            v_readcnt  = data.getCnt();
                                                                            v_realmotion  = data.getRealmotion();
                                                                            v_savemotion  = data.getSavemotion();
                                                                            v_upfilecnt   = data.getUpfileCnt();
                                                                            v_totalpage = data.getTotalPageCount();
                                                                            v_rowcount = data.getRowCount();             
                                                                    
                                                                            if (!v_searchtext.equals("")) {
                                                                                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                                                                                // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                                                                            }
                                                                    %>
                                                                    <tr class = "table_contents"> 
                                                                        <td align = "center" height = "23"><%= data.getDispnum() %></td>
                                                                        <td align = "left"><a href = "javascript:select('<%=v_seq%>', '<%=v_upfilecnt%>', '<%=v_userid%>')">&nbsp;&nbsp;<%= v_title %></a>
                                                                        &nbsp;&nbsp;<a href = "javascript:updateOpenPage('<%=v_seq%>');">openUpdate</a>                                                                        
                                                                    <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_regdt, "yyyyMMdd"))){%></a>
                                                                    &nbsp;&nbsp;<img src = "/learn/sample/images/new.gif" align = "absmiddle" border = "0">
                                                                    <%}%>
                                                                    </td>
                                                                    <td align = "center"><a href = "mailto:<%//=fbean.getSession("email")%>"><%= v_usernm %></a></td>
                                                                    <td align = "center"><%= FormatDate.getFormatDate(v_regdt, "yyyy/MM/dd") %></td>
                                                                    <td align = "center"><%= v_readcnt %></td>
                                                                    <td align = "center"><%= v_upfilecnt %></td>
                                                                    <td align = "center">
                                                                    &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savemotion%>&p_realfile=<%= v_realmotion%>'>
                                                                    <%= FileManager.getFileTypeImage(v_realmotion) %></a></td>
                                                                    </tr>
                                                                    <%
                                                                        }
                                                                    %>
                                                                </table>                                                                        
                                                            </td>
                                                        </tr>
                                                        <tr> 
                                                            <td height = "3" align = "center" colspan = "2"><img src = "/learn/sample/images/a_table_underline.gif" width = "708" height = "3"></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height = "30" valign = "bottom" align = "right" colspan = "3"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
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
