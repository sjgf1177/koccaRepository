<%
//**********************************************************
//  1. 제      목: 결제확인
//  2. 프로그램명: zu_BookBillCheck_P.jsp
//  3. 개      요: 결제확인
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process       = box.getString("p_process");
    String  v_bookcode      = box.getString("p_bookcode");
    String  v_bookname      = box.getString("p_bookname");
    int     v_dis_price     = box.getInt   ("p_dis_price");
    int     v_realpaymoney  = box.getInt   ("p_realpaymoney");
    String  v_accountname   = box.getString("p_accountname");
    String  v_paydate       = box.getString("p_paydate");
    String  v_receive       = box.getString("p_receive");
    String  v_post1         = box.getString("p_post1");
    String  v_post2         = box.getString("p_post2");
    String  v_addr1         = box.getString("p_addr1");
    String  v_addr2         = box.getString("p_addr2");
    String  v_phone         = box.getString("p_phone");

    DecimalFormat  df = new DecimalFormat("###,##0");
%>

<html>
<head>
<title>결제확인</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
</head>


<SCRIPT LANGUAGE="JavaScript">
<!--

//입력
function bookPropose() {
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "bookPropose";
    document.form1.target = "_self";
    document.form1.submit();
}

//-->
</SCRIPT>

<body bgcolor="#F6F6F6">
<form name="form1" method="post">
    <input type='hidden' name='p_process'     value="<%=v_process%>">
    <input type='hidden' name='p_bookcode'    value="<%=v_bookcode%>">
    <input type='hidden' name='p_bookname'    value="<%=v_bookname%>">
    <input type='hidden' name='p_dis_price'   value="<%=v_dis_price%>">
    <input type='hidden' name='p_dis_price'   value="<%=v_dis_price%>">
    <input type='hidden' name='p_accountname' value="<%=v_accountname%>">
    <input type='hidden' name='p_paydate'     value="<%=v_paydate%>">
    <input type='hidden' name='p_receive'     value="<%=v_receive%>">
    <input type='hidden' name='p_post1'       value="<%=v_post1%>">
    <input type='hidden' name='p_post2'       value="<%=v_post2%>">
    <input type='hidden' name='p_addr1'       value="<%=v_addr1%>">
    <input type='hidden' name='p_addr2'       value="<%=v_addr2%>">
    <input type='hidden' name='p_phone'       value="<%=v_phone%>">


<table width="700" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_offlinemoney.gif" ></td>
  </tr>
  <tr>
    <td><table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
            <!-- 수강안내 -->
            <table width="620" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor">
                <td height="3" class="linecolor_app"></td>
                <td width="239" height="3" class="linecolor_app2"></td>
                <td width="90" height="3" class="linecolor_app"></td>
                <td width="152" height="3" class="linecolor_app2"></td>
              </tr>
              <tr>
                <td width="103" class="tbl_gtit2">교재명</td>
                <td colspan="3" class="tbl_gleft"><%=v_bookname%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">교재비</td>
                <td colspan="3" class="tbl_gleft"><strong><%=df.format(v_dis_price)%></strong></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">무통자 입금계좌</td>
                <td colspan="3" class="tbl_gleft"><strong>하나은행</strong> 001-02-121313-12-111 <strong>예금주 :</strong> 사이버게임아카데미</td>
              </tr>
              <tr>
                <td class="tbl_gtit2">입금자</td>
                <td colspan="3" class="tbl_gleft"> <%=v_accountname%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">입금 예정일</td>
                <td colspan="3" class="tbl_gleft"> <%=FormatDate.getFormatDate(v_paydate,"yyyy/MM/dd")%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자명</td>
                <td colspan="3" class="tbl_gleft"> <%=v_receive%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자전화번호</td>
                <td colspan="3" class="tbl_gleft"> <%=v_phone%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자우편번호</td>
                <td colspan="3" class="tbl_gleft"> <%=v_post1%> - <%=v_post2%></td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자주소</td>
                <td colspan="3" class="tbl_gleft"> <%=v_addr1%><br> <%=v_addr2%>
                </td>
              </tr>
            </table>
            <font color="red">※ 결제 내역을 다시 확인해 주시고 맞으면 확인을 클릭 해 주세요.</font>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="right"><a href="javascript:bookPropose()"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> <a href="javascript:self.close();"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
