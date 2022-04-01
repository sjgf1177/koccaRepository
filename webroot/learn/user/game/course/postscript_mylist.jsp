<%
//**********************************************************
//  1. 제      목: 교육후기 - 나의 후기
//  2. 프로그램명: postscript_mylist.jsp
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

	String v_grcode = box.getString("p_grcode");
    List myList = (ArrayList) request.getAttribute("myList");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function insertPage( subj, subjnm ) {
		var form1 = document.form1;
		form1.p_process.value = "insertPage";
		form1.p_subj.value = subj;
		form1.p_subjnm.value = subjnm;
		
		form1.submit();
	}

	function updatePage( seq, subjnm ) {
		var form1 = document.form1;
		form1.p_process.value = "updatePage";
		form1.p_seq.value = seq;
		form1.p_subjnm.value = subjnm;
		
		form1.submit();
	}
	
	function del( seq ) {
		var form1 = document.form1;
		form1.p_process.value = "delete";
		form1.p_seq.value = seq;
		
		form1.submit();
	}
	
	function allList() {
		var form1 = document.form1;
		form1.p_process.value = "allList";

		form1.submit();
	}
</script>
</head>

<body bgcolor="F6F6F6">
<table width="700" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
<form name="form1" method="post" action="/servlet/controller.course.PostScriptServlet">
  <input type="hidden" name="p_process">
  <input type="hidden" name="p_next_process" value="myList">
  <input type="hidden" name="p_grcode" value="<%=v_grcode%>">
  <input type="hidden" name="p_seq">
  <input type="hidden" name="p_subj">
  <input type="hidden" name="p_subjnm">
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
                <td valign="bottom"  align="right" style="padding-top:10px"><a href="javascript:allList();"><img src="/images/user/game/button/btn_all_list.gif"></a><img src="/images/user/game/button/btn_my_list_u.gif"></td>
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
                <td height="3" colspan="3" class="linecolor_board6"></td>
              </tr>
              <tr>
                <td width="150" class="tbl_gtit3"><b>분류</b></td>
                <td width="300" class="tbl_gtit3"><b>과정명</b></td>
                <td width="170" class="tbl_gtit3"><b>교육후기</b></td>
              </tr>
              <tr>
                <td height="5" colspan="3" ></td>
              </tr>
              <%
              		DataBox dbox = null;
              
              		String v_seq = "";
              		String v_subj = "";
              		String v_classnm = "";
              		String v_subjnm = "";
              		String v_isPostscript = "";
              		
              		if ( myList != null || myList.size() > 0 ) {
              			for ( int i=0; i<myList.size(); i++ ) {
	              			dbox = (DataBox) myList.get(i);
	              			
	              			v_seq = dbox.getString("d_seq");
	              			v_subj = dbox.getString("d_subj");
	              			v_classnm = dbox.getString("d_classnm");
	              			v_subjnm = dbox.getString("d_subjnm");
	              			v_isPostscript = dbox.getString("d_is_postscript");
              %>
              <tr>
                <td class="tbl_grc"><%=v_classnm%></td>
                <td class="tbl_bleft" style="padding-left:10px"><%=v_subjnm%></td>
                <td align="center">
              <%
              				if ( "N".equals(v_isPostscript) ) {
              %>
                	<a href="javascript:insertPage('<%=v_subj%>', '<%=v_subjnm%>');"><img src="/images/user/game/button/btn_write01.gif" border="0"></a>
              <%
              				} else {
			  %>
                	<a href="javascript:updatePage('<%=v_seq%>', '<%=v_subjnm%>');"><img src="/images/user/game/button/btn_modify.gif" border="0"></a> <a href="javascript:del('<%=v_seq%>');"><img src="/images/user/game/button/btn_delete.gif" border="0"></a>
			  <%
              				}
              %>
                </td>
              </tr>
              <%
              			}
              		} else {
			  %>
			  <tr>
			    <td colspan="3" align="center">수강 중인 과정이 없습니다.</td>
			  </tr>
			  <%
              		}
              %>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
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