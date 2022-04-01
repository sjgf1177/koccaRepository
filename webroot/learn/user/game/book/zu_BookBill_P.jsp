<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: zu_BookBill_P.jsp
//  3. 개      요: 과정안내
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

    String  v_process  = box.getString("p_process");
    String  v_bookcode = box.getString("p_bookcode");

    String v_classify      = "";          // 분류
    String v_codenm        = "";          // 분류명
    String v_bookname      = "";          // 교재명
    int    v_price         = 0;           // 정가
    int    v_dis_price     = 0;           // 판매가

    String upload_url = conf.getProperty("url.upload");
    DecimalFormat  df = new DecimalFormat("###,##0");

    DataBox dbox = (DataBox)request.getAttribute("selectBook");

    if (dbox != null) {
        v_classify      = dbox.getString("d_classify");
        v_codenm        = dbox.getString("d_codenm");
        v_bookname      = dbox.getString("d_bookname");
        v_price         = dbox.getInt   ("d_price");
        v_dis_price     = dbox.getInt   ("d_dis_price");
     }

    String v_name    = "";
    String v_post1   = "";
    String v_post2   = "";
    String v_addr    = "";
    String v_addr2   = "";
    String v_hometel = "";

    MemberInfoBean memberbean = new MemberInfoBean();
    DataBox dbox2 =  memberbean.getMemberInfo(box);
    if (dbox2 != null) {
		v_name    = dbox2.getString("d_name");
        v_post1   = dbox2.getString("d_post1");
        v_post2   = dbox2.getString("d_post2");
        v_addr    = dbox2.getString("d_addr");
        v_addr2   = dbox2.getString("d_addr2");
        v_hometel = dbox2.getString("d_hometel");
    } 
%>

<html>
<head>
<title>결제처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
</head>

<script>
//숫자만 입력 가능
function numcheck(oTarget) {
    if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
        if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
            event.returnValue = true;
        else
            event.returnValue = false;
}

//입력
function input() {
    if(document.form1.p_accountname.value.length < 1) {
        alert("입금자를 입력해 주세요.");
        document.form1.p_accountname.focus();
        document.form1.p_accountname.select();
        return;
    }
    if(document.form1.p_paydate.value.length < 1 ) {
        alert("입금일을 입력해 주세요.");
        document.form1.p_paydate.focus();
        document.form1.p_paydate.select();
        return;
    }
    if(document.form1.p_receive.value.length < 1 ) {
        alert("수신자명을 입력해 주세요.");
        document.form1.p_receive.focus();
        document.form1.p_receive.select();
        return;
    }
    if(document.form1.p_phone.value.length < 1 ) {
        alert("수신자전화번호를 입력해 주세요.");
        document.form1.p_phone.focus();
        document.form1.p_phone.select();
        return;
    }
    if(document.form1.p_addr1.value.length < 1 ) {
        alert("수신자주소를 입력해 주세요.");
        document.form1.p_addr1.focus();
        document.form1.p_addr1.select();
        return;
    }


    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "bookBillCheckPage";
    document.form1.target = "_self";

    document.form1.submit();
}

// 우편번호검색
function searchPost() {
    window.self.name = "PersonalSelect";
    open_window("openPost","","100","100","417","400","","","","yes","");
    document.form1.target = "openPost";
    document.form1.action='/servlet/controller.library.PostSearchServlet';
    document.form1.p_process.value = 'SearchPostOpenPage';
    document.form1.submit();
    document.form1.target = window.self.name;
}
// 주소 받아오기
function receivePost(post1, post2, addr, postgubun){
    document.form1.p_post1.value = post1;
    document.form1.p_post2.value = post2;
    document.form1.p_addr1.value = addr;
    document.form1.p_addr2.focus();
    
}
</script>
<body bgcolor="#F6F6F6">
<form name="form1" method="post">
    <input type='hidden' name='p_process'      value="<%=v_process%>">
    <input type='hidden' name='p_bookcode'     value="<%=v_bookcode%>">
    <input type='hidden' name='p_bookname'     value="<%=v_bookname%>">
    <input type='hidden' name='p_dis_price'    value="<%=v_dis_price%>">
    <input type='hidden' name='p_realpaymoney' value="<%=v_dis_price%>">

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
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_accountname"  maxlength="10" value="<%=v_name%>">
                </td>
              </tr>
              <tr>
                <td class="tbl_gtit2">입금 예정일</td>
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_paydate" maxlength="8" onkeydown="javascript:return numcheck(this)"> "-"없이 입력해주세요 (ex.20060130)
                </td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자명</td>
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_receive" maxlength="10" value="<%=v_name%>">
                </td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자전화번호</td>
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_phone" maxlength="14" value="<%=v_hometel%>">
                </td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자우편번호</td>
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_post1" maxlength="3" size="4" value="<%=v_post1%>" readOnly> - 
                    <input type="text" class="input" name="p_post2" maxlength="3" size="4" value="<%=v_post2%>" readOnly>
                    <a href="javascript:searchPost()"><img src="/images/user/game/button/btn_zipcode.gif" align="absmiddle"width="87" height="18" border="0"> </a>
                </td>
              </tr>
              <tr>
                <td class="tbl_gtit2">수신자주소</td>
                <td colspan="3" class="tbl_gleft">
                    <input type="text" class="input" name="p_addr1" maxlength="100" size= "60" value="<%=v_addr%>"><br>
                    <input type="text" class="input" name="p_addr2" maxlength="100" size= "60" value="<%=v_addr2%>">
                </td>
              </tr>
            </table>
            <table width="620" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="right"><a href="javascript:input()"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> <a href="javascript:self.close();"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
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
