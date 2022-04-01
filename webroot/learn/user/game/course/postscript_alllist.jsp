<%
//**********************************************************
//  1. 제      목: 교육후기 - 전체 후기
//  2. 프로그램명: postscript_alllist.jsp
//  3. 개      요: 교육후기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2007.12.15
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	
	int v_pageno = box.getInt("p_pageno");
	int v_dispnum = 0;
	int v_totalpage = 0;
	int v_rowcount = 1;
	
	String v_grcode = box.getString("p_grcode");
    List allList = (ArrayList) request.getAttribute("allList");
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function myList() {
		document.location.href="/servlet/controller.course.PostScriptServlet?p_process=myList";
	}

	function allList() {
		document.location.href="/servlet/controller.course.PostScriptServlet?p_process=allList";
	}
	
	function detail( seq ) {
		var form1 = document.form1;
		form1.p_process.value = "select";
		form1.p_seq.value = seq;
		
		form1.submit();
	}
	
	//페이지 이동
	function goPage(pageNum) {
		 document.form1.p_pageno.value = pageNum;
		 document.form1.p_process.value = "allList";
		 document.form1.submit();
	}
</script>
</head>

<body bgcolor="F6F6F6">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
<form name="form1" method="post" action="/servlet/controller.course.PostScriptServlet">
  <input type="hidden" name="p_process">
  <input type="hidden" name="p_next_process" value="allList">
  <input type="hidden" name="p_grcode" value="<%=v_grcode%>">
  <input type="hidden" name="p_seq">
  <input type="hidden" name="p_subj">
  <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
</form>
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
                <td valign="bottom"  align="right" style="padding-top:10px"><a href="javascript:allList();"><img src="/images/user/game/button/btn_all_list_u.gif"></a><a href="javascript:myList();"><img src="/images/user/game/button/btn_my_list.gif"></a></td>
              </tr>
              <!--
              <tr>
                <td valign="bottom" ><img src="/images/user/game/course/b1_note.gif" align="absmiddle"><font color="#000000"></font></td>
              </tr>-->
              <tr>
                <td height="5"></td>
              </tr>
            </table>
            <!-- 수강안내 -->
            <table width="620" border="2" cellspacing="0"
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr>
                <td height="3" colspan="4" class="linecolor_board6"></td>
              </tr>
              <tr>
                <td width="80" class="tbl_gtit3"><b>분류</b></td>
                <td width="150" class="tbl_gtit3"><b>과정명</b></td>
                <td width="220" class="tbl_gtit3"><b>교육후기</b></td>
                <td width="150" class="tbl_gtit3"><b>작성자(ID)</b></td>
              </tr>
              <tr>
                <td height="5" colspan="4" ></td>
              </tr>
              <%
              		DataBox dbox = null;
              
              		String v_seq = "";
              		String v_subj = "";
              		String v_classnm = "";
              		String v_subjnm = "";
              		String v_title = "";
              		String v_userid = "";
              		String v_usernm = "";
              		
              		if ( allList != null || allList.size() > 0 ) {
              			for ( int i=0; i<allList.size(); i++ ) {
	              			dbox = (DataBox) allList.get(i);
	              			
	              			v_seq = dbox.getString("d_seq");
	              			v_subj = dbox.getString("d_subj");
	              			v_classnm = dbox.getString("d_classnm");
	              			v_subjnm = dbox.getString("d_subjnm");
	              			v_title = dbox.getString("d_title");
	              			v_userid = dbox.getString("d_userid");
	              			v_usernm = dbox.getString("d_usernm");

	              			v_totalpage	= dbox.getInt("d_totalpage");
	                        v_rowcount	= dbox.getInt("d_rowcount");  
	              			
              %>              
              <tr>
                <td class="tbl_grc"><%=v_classnm%></td>
                <td class="tbl_grc"><%=v_subjnm%></td>
                <%
                	if ( v_userid.equals(box.getSession("userid") ) || box.getSession("gadmin").equals("A1") ) {
                %>
                <td class="tbl_bleft"><b><a href="javascript:detail('<%=v_seq%>')"><%=StringManager.formatTitle(v_title, 20)%></a></b></td>
                <%
                	} else {
				%>                		
                <td class="tbl_bleft"><a href="javascript:alert('이벤트 후기 내역은 작성자만 볼 수 있습니다.')"><%=StringManager.formatTitle(v_title, 20)%></a></td>
				<%
                	}
                %>
                <td align="center"><%=v_usernm%>(<%=v_userid%>)</td>
              </tr>
              <%
              			}
              		} else {
              %>
              <tr>
                <td colspan="4" align="center">교육후기가 없습니다.</td>
              </tr>
              <%
              		}
              %>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
              	<td align="center" colspan="3" style="padding:4px 0px 0px 4px"><%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %></td>
              </tr>
              <tr>
                <td height="5" colspan="3" align="right"></td>
              </tr>
              <tr>
                <td colspan="3" align="center"></td>
              </tr>
              <tr>
                <td colspan="3" align="right"><a href="javascript:self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
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

</body>
</html>
