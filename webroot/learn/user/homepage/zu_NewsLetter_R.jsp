<%
//**********************************************************
//  1. 제      목: 사용자 뉴스레터
//  2. 프로그램명 : zu_NewsLetter_R.jsp
//  3. 개      요: 사용자 뉴스레터 상세보기
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 9. 2
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    String  v_type    = box.getStringDefault("p_type","HR");
    int     v_tabseq     = box.getInt("p_tabseq");               // 게시판 ID
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;

	String v_title_image = "";
	if (v_type.equals("HR")) v_title_image = "tit_hrnews.gif";
	else                     v_title_image = "tit_whatnews.gif";

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    DataBox dbox = (DataBox)request.getAttribute("selectBoard");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_content = StringManager.replace(v_content,"\r\n","<br>");
    }

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script src="/script/cafe_select.js" language="JavaScript"></script>


<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad='init();'>
<table cellpadding="0" cellspacing="10" bgcolor="#EEEEEE" width="670" height="100%">
    <tr>
        <td valign="top">
            <table cellpadding="0" cellspacing="1" bgcolor="#BEBEBE" class="table2" height="100%">
                <tr>
                    <td align="center" valign="top" class="body_color">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
                <td>
				    <br>  
                  <!----------------- 타이틀 시작 ----------------->
                  <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
                    <tr> 
                      <td><img src="/images/user/common/<%=v_title_image%>"></td>
                      <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
                      <td><img src="/images/user/study/gongi_tail.gif"></td>
                    </tr>
                  </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
                <td align="center" valign="top">
                  <!-- qna table -->
                  <table width="640" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td colspan="4"><img src="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/common/text_pe.gif"></td>
                      <td width="243" class="tbl_gleft"><%=v_name%></td>
                      <td width="94"><img src="/images/user/common/text_id.gif"></td>
                      <td width="209" class="tbl_gleft"><%=v_userid%></td>
                    </tr>
					 <tr> 
                      <td colspan="4"><img src="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/common/text_day.gif"></td>
                      <td width="243" class="tbl_gleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                      <td width="94"><img src="/images/user/common/text_view.gif"></td>
                      <td width="209" class="tbl_gleft"><%=v_cnt%></td>
                    </tr>
                    <tr> 
                      <td colspan="4"><img src="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr> 
                      <td width="94"><img src="/images/user/common/text_title.gif"></td>
                      <td colspan="3" class="tbl_btit"><%=v_title%></td>
                    </tr>
                    <tr> 
                      <td colspan="4"><img src="/images/user/common/search_line-1.gif"></td>
                    </tr>
                    <tr> 
                      <td colspan="4" class="tbl_contents"><%= v_content %></td>
                    </tr>
                    <tr > 
                      <td height="5" colspan="4"><img src="/images/user/common/com_tb_bo-1.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="4"></td>
                    </tr>
                  </table>
                  <table width="640" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="487" align="right">
					  <!-- 창그만보기 -->
					  <table width="290" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                        </table> </td>
                      <td width="153" align="right"> <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  border="0"></a></td>
                    </tr>
                  </table></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</table>
</body>
</html>

