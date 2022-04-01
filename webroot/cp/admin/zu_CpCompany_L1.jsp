<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 회사별게시판 리스트화면
//  2. 프로그램명: zu_CpCompany_L.jsp
//  3. 개      요: 외주관리 시스템 회사별게시판 리스트화면
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

int v_seq = 0,v_readcnt = 0,i = 0;
String  v_classname1 = "";
//String  v_classname2 = ""; 
String v_userid = "", v_name = "", v_position = "", v_indate = "", v_title = "", tLink = "", v_realmotion = "", v_savemotion = "";
int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;

ArrayList list = (ArrayList)request.getAttribute("selectPdsList");
   
String v_searchtext = box.getString("p_searchtext");   
String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno"); 

String s_userid = box.getSession("userid");

String s_usernm = box.getSession("name");

String  v_cpseq = box.getString("p_cpseq");

String s_gadmin = box.getSession("gadmin");
%>

<html>
    <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset = euc-kr">
    <title>외주관리시스템자유게시판 </title>
   <link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
        <script language = "javascript">
            function go(index) {
                 document.form1.p_pageno.value = index;
                 document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
                 document.form1.p_process.value = "";     
                 document.form1.submit();
            }            
            function goPage(pageNum) {
                 document.form1.p_pageno.value = pageNum;
                 document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
                 document.form1.p_process.value = "";     
                 document.form1.submit();
            }                
            function selectList() {
            	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
            	document.form1.p_process.value = "";   
            	document.form1.p_pageno.value = "1";       
            	document.form1.submit();
            }            
            function insertPage() {
            	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
            	document.form1.p_process.value = "insertPage";  
            	document.form1.p_pageno.value = "<%= v_pageno %>";    
            	document.form1.submit();
            }
            function select(num) {
            	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
            	document.form1.p_process.value = "select";
            	document.form1.p_seq.value = num;
            	document.form1.p_userid.value = "<%=v_userid%>";
				document.form1.p_pageno.value = "<%= v_pageno %>"; 
            	document.form1.submit();
            }
            function changeCategory() {
				document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
				document.form1.p_process.value    = "";
				document.form1.submit();
}
			

        </script>
    </head>
    <body  leftmargin = "0" topmargin = "0" marginwidth = "0" marginheight = "0">
        <form name = "form1" method = "post">
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_pageno" value = "">
        <input type = "hidden" name = "p_seq" value = "">
        <input type = "hidden" name = "p_userid" value = "">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA">
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
              <param name="movie" value="/images/gate/O_sub01.swf">
			  <param name=wmode value=transparent>
              <param name="quality" value="high">
              <embed src="/images/gate/O_sub02.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object>
			  </td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
	
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><img src="/images/gate/O_title03.gif" width="85" height="80"></td>
          <td width="25">&nbsp;</td>
          <td width="775"><table width="775" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="169" align="right" valign="middle">
                                       
										<%if(s_gadmin.equals("A1") || s_gadmin.equals("A2")){%>
										<p align="left">
										<!--관리자모드셀렉트박스시작-->
										<%=CpCompanyBean.getCompSelecct("p_cpseq",v_cpseq,"onChange=\"javascript:changeCategory()\"",1)%>
										<!--셀렉트박스끝--></p>
										<%}%>


</td>
                                    <td width="355" align="right" valign="middle">
                                        <p><select name = "p_select" class = "input">
                                            <option value = "title" <% if (v_select.equals("title")) out.print("selected"); %>>제목</option>
                                            <option value = "name" <% if (v_select.equals("name")) out.print("selected"); %>>작성자</option>
                                            <option value = "content" <% if (v_select.equals("content")) out.print("selected"); %>>내용</option>
                </select> </p>
                </td>
				
                                    <td width="192" align="right" valign="middle">                                        <p><input type = "text" onFocus = "this.style.background='#FFFAE1'" onBlur = "this.style.background='#ffffff'" 
                name = "p_searchtext"   size = "25" maxlength = "30">
</p>
</td>
                                    <td width="1%" align="right" valign="middle">&nbsp;</td>
                                    <td width="50" align="right" valign="middle">                                        <p><a href = "javascript:selectList()"><img src="/images/gate/btn_search.gif" width="50" height="22" border="0" align="middle"></a></p>
</td>
              </tr>
                                <tr>
                                    <td height="8" align="right" colspan="5" width="775"></td>
              </tr>
            </table>
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="64" class=O_boardbgimg><span class=b_title>번호</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="366" class=O_boardbgimg><span class=b_title>제목</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="123" class=O_boardbgimg><span class=b_title>작성자</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="154" class=O_boardbgimg><span class=b_title>작성일</span></td>
                <td width="1" class=O_boardbgimg><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                <td width="64" class=O_boardbgimg><span class=b_title>조회</span></td>
              </tr>
<%if(list.size() != 0){%>
				<%for(i = 0; i < list.size(); i++) {
					DataBox dbox = (DataBox)list.get(i);  
                    v_dispnum = dbox.getInt("d_dispnum");
					v_userid   = dbox.getString("d_userid");
					v_name   = dbox.getString("d_name");
					
					v_indate    = dbox.getString("d_indate");
					v_title    = dbox.getString("d_title");
					v_seq      = dbox.getInt("d_seq");
					v_readcnt  = dbox.getInt("d_cnt");
					v_realmotion  = dbox.getString("d_realmotion");
					v_savemotion  = dbox.getString("d_savemotion");
					v_upfilecnt   = dbox.getInt("d_filecnt");
					v_totalpage = dbox.getInt("d_totalpage");
					v_rowcount = dbox.getInt("d_rowcount");  
					
                  if (!v_searchtext.equals("")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                        // 제목검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                        }
					if ((i % 2) == 0) {
						v_classname1 = "b_list_W";
						//v_classname2 = "b_list_W";
					} else {
					  
						v_classname1 = "b_list_G";
						//v_classname2 = "b_list_G";
					}
	
					%>
              <tr class = "table_contents">  
                <td width="64" align="center" class="<%=v_classname1%>"><%=v_dispnum%></td>
                <td valign="bottom" class="<%=v_classname1%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="366" class="<%=v_classname1%>" style="padding-left:10"><a href="javascript:select(<%=v_seq%>)" target="_self"><%=v_title%></a></td>
                <td valign="bottom" class="<%=v_classname1%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="123" align="center" class="<%=v_classname1%>"><%=v_name%></td>
                <td valign="bottom" class="<%=v_classname1%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="154" align="center" class="<%=v_classname1%>"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                <td valign="bottom" class="<%=v_classname1%>"><img src="/images/gate/L_board_Lg.gif" width="1"></td>
                <td width="64" align="center" class="<%=v_classname1%>"><%=v_readcnt%></td>
              </tr>
			
                                                                  
              <tr> 
                <td colspan="9"  class=b_guide></td>
              </tr>
                <%}%>
<%}else{%>
           

                                                    <tr>
                                                        <td class=b_list_W colspan="13" align=center >
															등록 된 내용이 없습니다.		
														</td>
													</tr>

<%}%>		
              <tr> 
                <td colspan="9" class=b_list_bottom></td>
              </tr>
              <tr> 
                <td colspan="9" height=8></td>
              </tr>
              <tr> 
                <td height=20 colspan="9"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td height = "30" valign = "middle" align = "right" width="100%"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
                                                <td width="1%" height="30" align="right" valign="middle">&nbsp;</td>
                      <td width="50" align="right" valign="middle">
					  <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
						<a href = "javascript:insertPage()"><img src="/images/gate/btn_regist.gif" width="50" height="22" border="0"></a></td>
					   <%  }   %>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td colspan="9" height=20></td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
          <td width="775" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
		
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
