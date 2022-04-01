<%
//**********************************************************
//  1. 제      목: 과정수강생리스트
//  2. 프로그램명 : za_Permission_L.jsp
//  3. 개      요: 과정수강생 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_usernm    = box.getString("p_usernm");
    String  v_subj      = box.getString("p_subj");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_year      = box.getString("p_year");

    int v_pageno    = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    
    String v_userid="", v_cono="", v_name="", v_compnm="", v_email="";

    ArrayList list = (ArrayList)request.getAttribute("select");
    

%>
<html>
<head>
<title>::: 수강생 현황 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/cresys_lib.js"></SCRIPT>
<SCRIPT LANGUAGE = "JAVASCRIPT">
function select(){
document.form1.submit();
}
</SCRIPT>
</head>
<body topmargin="0" leftmargin="0" marginheight="0">
<form name="form1" method="post" action="/servlet/controller.study.StudentListServlet">
<input type="hidden" name="p_subj" value="<%=v_subj%>">
<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
<input type="hidden" name="p_year" value="<%=v_year%>">
  <table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="783" height="100%">
    <tr> 
      <td> <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
          <tr> 
            <td align="center" valign="top" class="body_color"> <br> 
              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                <tr> 
                  <td><img src="/images/user/study/student_title.gif"></td>
                  <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                  <td><img src="/images/user/study/gongi_tail.gif"></td>
                </tr>
              </table>
              <!----------------- 타이틀 끝 ---------------->
              <br> 
              <!----------------- 조회 시작 ---------------->
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
                <tr> 
                  <td valign="bottom"></td>
                  <td align="right"> <table width="245" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td align="right">성명 : 
                          <input type="text" name="p_usernm" class="input" value="<%=v_usernm%>" size="10" maxlength="10"></td>
                        <td align="center"> <a href = "javascript:select();"><img src="/images/user/button/btn_j.gif" border="0"></a> 
                        </td>
                      </tr>
                    </table></td>
                </tr>
                <tr> 
                  <td height="5"></td>
                </tr>
              </table>
              <!----------------- 조회 끝 ---------------->
              <!----------------- 게시판 리스트 시작 ---------------->
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
                <tr> 
                  <td class="board_color_line"></td>
                </tr>
              </table>
              <table cellspacing="0" cellpadding="0" class="open_board_table_out2">
                <tr> 
                  <td> <table cellspacing="1" cellpadding="3" class="table2">
                      <tr> 
                        <td class="board_title_bg1" width="33%">ID</td>
                        <td class="board_title_bg1" width="33%">이름</td>
                        <!--td class="board_title_bg1" width="10%">쪽지</td-->
                        <td class="board_title_bg1" width="34%">E-mail</td>
                      </tr>
                      <%                    
                for(int i = 0; i < list.size(); i++) {
                    DataBox dbox = (DataBox)list.get(i);
                    v_userid    = dbox.getString("d_userid");
                    v_cono      = dbox.getString("d_cono");
                    v_name      = dbox.getString("d_name");
                    v_compnm    = dbox.getString("d_compnm");
                    v_email     = dbox.getString("d_email");
                    v_totalpage = dbox.getInt("d_totalpage");
                    v_rowcount  = dbox.getInt("d_rowcount");

                    
%>
                      <tr>
                        <td class="board_text1"><%=dbox.getString("d_userid")%></td>
                        <td class="board_text1"><%=v_name%></td>
                        <!--td class="board_text1">쪽지</td-->
                        <td class="board_text1"><%=v_email%></td>
                      </tr>
                      <%
                }
%>
                    </table></td>
                </tr>
              </table>
              <!----------------- 게시판 리스트 끝 ---------------->
              <!----------------- 작성, 닫기 버튼 시작 ---------------->
              <table cellspacing="0" cellpadding="0" class="open_board_table_out1">
                <tr> 
                  <td height="11" colspan="3"></td>
                </tr>
                <tr> 
                  <td align="center">
					    <table width="130" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="19">
							<%= HomePageQnaBean.printPageList(v_totalpage, v_pageno, row) %>
							</td>
                          </tr>
                        </table>                  
                  </td>
                  <td align="right" width="65"><a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif" border="0"></a></td>
                </tr>
              </table>
              <!----------------- 작성, 닫기 버튼 끝 ----------------->
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
