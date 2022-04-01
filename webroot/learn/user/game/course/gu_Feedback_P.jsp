<%
//**********************************************************
//  1. 제      목: 과정후기
//  2. 프로그램명: gu_Feedback_P.jsp
//  3. 개      요: 과정후기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    int  v_pageno     = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;

    String  v_subjnm  = box.getString("p_subjnm");

    String v_userid = "";
    String v_usernm = "";
    String v_answer = "";
    String v_ldate  = "";

    int i = 0;;
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;

    ArrayList list   = (ArrayList)request.getAttribute("FeedbackList");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="F6F6F6">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/course/pop_tit_feedback.gif"></td>
  </tr>
  <tr>
    <td>
      <table width="700" height="540" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
            <!-- 서브타이틀 -->
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="5"></td>
              </tr>
              <tr>
                <td><img src="/images/user/game/course/feed_img.jpg" width="620" height="70"></td>
              </tr>
              <tr>
                <td valign="bottom" >&nbsp;</td>
              </tr>
              <tr>
                <td valign="bottom" ><img src="/images/user/game/course/b1_note.gif" align="absmiddle"><font color="#000000"><%=v_subjnm%></font></td>
              </tr>
              <tr>
                <td height="5"></td>
              </tr>
            </table>
            <!-- 수강안내 -->
            <table width="620" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr>
                <td height="3" colspan="2" class="linecolor_board6"></td>
              </tr>
              <tr>
                <td width="114" class="tbl_gtit3">아이디</td>
                <td width="586" class="tbl_gtit3">교육후기</td>
              </tr>
              <tr>
                <td height="5" colspan="2" ></td>
              </tr>
<%

            for(i = 0; i < list.size(); i++) {
                DataBox dbox  = (DataBox)list.get(i);

                v_userid    = dbox.getString("v_userid");
                v_usernm    = dbox.getString("v_usernm");
                v_answer    = dbox.getString("v_answer");
                v_ldate     = dbox.getString("v_ldate");
                v_totalpage = dbox.getInt("v_totalpage");
                v_rowcount  = dbox.getInt("d_rowcount");

%>
              <tr>
                <td class="tbl_grc"><%=v_usernm%>(<%=v_userid%>)</td>
                <td class="tbl_bleft"><%=v_answer%>~</td>
              </tr>
<%
         }

         if(i == 0){ 
%>
              <tr>
                <td height='40' colspan="2" class="tbl_grc">등록된 후기가 없습니다</td>
              </tr>
<%
        }
%>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="5" colspan="2" align="right"></td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                </td>
              </tr>
              <tr>
                <td colspan="2" align="right"><a href="javascript:self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table>
          </td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_bobg.gif" height="21">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
