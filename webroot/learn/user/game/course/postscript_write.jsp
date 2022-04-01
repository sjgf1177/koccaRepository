<%
//**********************************************************
//  1. 제      목: 교육후기 - 후기 쓰기/수정
//  2. 프로그램명: postscript_write.jsp
//  3. 개      요: 교육후기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2007.12.15
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);
	
	String v_grcode = box.getString("p_grcode");
	String v_seq = box.getString("p_seq");
	String v_subj = box.getString("p_subj");
	String v_subjnm = box.getString("p_subjnm");
	String v_next_process = box.getString("p_next_process");
	
	// Update시
	String v_title = "";
	String v_contents = "";
	String v_type = "I";
	
	DataBox dbox = (DataBox) request.getAttribute("select");
	if ( dbox != null ) {
		v_title = dbox.getString("d_title");
		v_contents = dbox.getString("d_contents");
		v_type = "U";
	}
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function insert( type ) {
		var form1 = document.form1;
		
		if ( form1.p_title.value == "" ) {
			alert( '후기 제목을 입력하세요.' );
			form1.p_title.focus();
			return;
		}

		if ( form1.p_contents.value == "" ) {
			alert( '내용을 입력하세요.' );
			form1.p_contents.focus();
			return;
		}

		if ( type == 'U' ) {
			form1.p_process.value = "update";
		} else {
			form1.p_process.value = "insert";
		}

		form1.submit();
	}
</script>
</head>

<body bgcolor="F6F6F6" onload="document.form1.p_title.focus();">
<form name="form1" method="post" action="/servlet/controller.course.PostScriptServlet">
  <input type="hidden" name="p_process">
  <input type="hidden" name="p_next_process" value="<%=v_next_process%>">
  <input type="hidden" name="p_grcode" value="<%=v_grcode%>">
  <input type="hidden" name="p_seq" value="<%=v_seq%>">
  <input type="hidden" name="p_subj" value="<%=v_subj%>">
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
              <!--
              <tr>
                <td valign="bottom"  align="right" style="padding-top:10px"><a href="#"><img src="/images/user/game/button/btn_all_list_u.gif"></a><a href="#"><img src="/images/user/game/button/btn_my_list.gif"></a></td>
              </tr>-->
              <tr>
                <td valign="bottom" style="padding-top:10" align="right"><b>(과정명)</b> 과목의 내용평가, 그래픽, 기타소감을 자세히 작성해 주시기 바랍니다. </td>
              </tr>
              <tr>
                <td valign="bottom" style="padding-top:10"><img src="/images/user/game/course/b1_note.gif" align="absmiddle"><font color="#000000"><%= v_subjnm %></font></td>
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
                <td width="100" bgcolor="E7E6D9" align="center"><font color="black"><b>제 목</b></font></td>
                <td width="420" style="padding-left:10px"><input type="text" name="p_title" value="<%=v_title%>" size="73"></td>
              </tr>
              <tr>
                <td width="100" bgcolor="E7E6D9" align="center"><font color="black"><b>내 용</b></font></td>
                <td width="420" style="padding:10 10 10 10"><textarea name="p_contents" cols="72" rows="20" class="board-form-02"><%=v_contents%></textarea></td>
              </tr>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="5" colspan="2" align="right"></td>
              </tr>
              <tr>
                <td colspan="2" align="center"></td>
              </tr>
              <tr>
                <td colspan="2" align="right">
				<a href="javascript:insert('<%=v_type%>');"><img src="/images/user/game/button/btn_write01.gif" border="0"></a>                
				<a href="javascript:history.back(-1);"><img src="/images/user/game/button/btn_list.gif" border="0"></a>
                </td>
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
</form>

</body>
</html>
