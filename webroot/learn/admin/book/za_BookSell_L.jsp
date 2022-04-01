<%
//**********************************************************
//  1. 제      목: 교재판매관리
//  2. 프로그램명 : za_Book_L.jsp
//  3. 개      요: 교재판매관리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    String ss_startday  = box.getStringDefault("s_startday", FormatDate.getDateAdd("yyyyMMdd", "month", -1));
    String ss_endday    = box.getStringDefault("s_endday",   FormatDate.getDate("yyyyMMdd"));
    String ss_paystat   = box.getString("s_paystat");
    String ss_bookcode  = box.getString("s_bookcode");
    String ss_action    = box.getString("s_action");
    String v_startday   = FormatDate.getFormatDate(ss_startday,"yyyy-MM-dd");
    String v_endday     = FormatDate.getFormatDate(ss_endday,"yyyy-MM-dd");

    int    i = 0;
    int    v_seq           = 0;           // serial no
    String v_userid        = "";          // 신청자 ID
    String v_name          = "";          // 신청자명
    String v_bookcode      = "";          // 교재코드
    String v_bookname      = "";          // 교재명
    int    v_dis_price     = 0;           // 판매가
    String v_indate        = "";          // 판매일
    String v_accountname   = "";          // 입금자
    int    v_realpaymoney  = 0;           // 입금액
    String v_paydate       = "";          // 입금일
    String v_paystat       = "";          // 상태 ( 미입금, 입금완료, 발송중, 발송완료 )
    String v_receive       = "";          // 수신자명
    String v_phone         = "";          // 전화번호
    String v_post1         = "";          // 우편번호1
    String v_post2         = "";          // 우편번호2
    String v_addr1         = "";          // 주소1
    String v_addr2         = "";          // 주소2
    String v_luserid       = "";          // 등록자
    String v_ldate         = "";          // 등록일
    String v_paymethod     = "";          // 등록일
    String v_paymethodnm   = "";          // 등록일

    ArrayList list = null;
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectBookSellList");
    }
%>

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_startday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_endday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    //검색
    function whenSelection(p_action) {
      document.form1.s_action.value = p_action;
      document.form1.s_startday.value = make_date(document.form1.p_startday.value);
      document.form1.s_endday.value   = make_date(document.form1.p_endday.value);

      document.form1.action='/servlet/controller.book.BookSellAdminServlet';
      document.form1.p_process.value = 'listPage';
      document.form1.submit();
    }

    function update(){

      document.form1.action='/servlet/controller.book.BookSellAdminServlet';
      document.form1.p_process.value = 'update';
      document.form1.submit();
    }


    // 회원정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }
//-->
</SCRIPT>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post">
    <input type="hidden" name="p_process"  value="<%=v_process%>">
    <input type="hidden" name="s_action"   value="<%=ss_action%>">
    <input type="hidden" name="s_startday" value="<%=ss_startday%>">
    <input type="hidden" name="s_endday"   value="<%=ss_endday%>">

    <input type="hidden" name="p_chknum" >
    <input type="hidden" name="p_chkvalue" >


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/book/tit_booksell.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="90%" colspan="2" align="left" valign="middle">&nbsp;&nbsp;
                         <%=BookBean.getBook(box, true, true)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         신청일&nbsp;<input name="p_startday" id="p_startday" value="<%=v_startday%>" type="text" class="datepicker_input1" size="10">&nbsp;
                          ~&nbsp;<input name="p_endday" id="p_endday" value="<%=v_endday%>" type="text" class="datepicker_input1" size="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         상태&nbsp;<%=CodeConfigBean.getCodeGubunSelect ("0074", "", 1, "s_paystat", ss_paystat, "onChange=\"javascript:whenSelection('change')\"", 2)%>
                      </td>
                      <td width="100" ><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
                    </tr>
                    <tr>
                        <td height="5" colspan="4"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>       
      </table>
      <br>

      <!-----------------  버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right" height="20"><a href="javascript:update()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!-----------------  버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">       
          <tr>
            <td colspan="18" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="55"><b>신청인<br>(ID)</b></td>
            <td class="table_title" width="80"><b>교재명</b></td>
            <td class="table_title"><b>신청일</b></td>
            <td class="table_title"><b>판매<br>금액</b></td>
            <td class="table_title"><b>입금자</b></td>
            <td class="table_title"><b>입금액</b></td>
            <td class="table_title"><b>입금<br>예정일</b></td>
            <td class="table_title"><b>수취인</b></td>
			<td class="table_title"><b>수취인<br>전화번호</b></td>
            <td class="table_title"><b>수취인<br>주소</b></td>
            <td class="table_title"><b>상태</b></td>
            <td class="table_title"><b>결제구분</b></td>
            <td class="table_title"><b>결제일</b></td>
            <td class="table_title"><b>환불<br>여부</b></td>
            <td class="table_title"><b>환불일</b></td>
          </tr>

<%  
    if (ss_action.equals("go")) {

        for (i=0; i<list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            v_seq           = dbox.getInt   ("d_seq");
            v_userid        = dbox.getString("d_userid");
            v_name          = dbox.getString("d_name");
            v_bookcode      = dbox.getString("d_bookcode");
            v_bookname      = dbox.getString("d_bookname");
            v_dis_price     = dbox.getInt   ("d_dis_price");
            v_indate        = dbox.getString("d_indate");
            v_accountname   = dbox.getString("d_accountname");
            v_realpaymoney  = dbox.getInt   ("d_realpaymoney");
            v_paydate       = dbox.getString("d_paydate");
            v_paystat       = dbox.getString("d_paystat");
            v_receive       = dbox.getString("d_receive");
            v_phone         = dbox.getString("d_phone");
            v_post1         = dbox.getString("d_post1");
            v_post2         = dbox.getString("d_post2");
            v_addr1         = dbox.getString("d_addr1");
            v_addr2         = dbox.getString("d_addr2");
            v_luserid       = dbox.getString("d_luserid"); 
            v_ldate         = dbox.getString("d_ldate");
            v_paymethod     = dbox.getString("d_paymethod");
            v_paymethodnm   = dbox.getString("d_paymethodnm");

%>

            <tr class="table_02_1">
                <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%=v_name %><br>(<%=v_userid%>)</a></td>
                <td class="table_02_1"><%=v_bookname %></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
                <td class="table_02_1"><%=v_dis_price %></td>
                <td class="table_02_1"><%=v_accountname %></td>
                <td class="table_02_1"><%=v_realpaymoney %></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_paydate,"yyyy-MM-dd")%></td>
                <td class="table_02_1"><%=v_receive %></td>
                <td class="table_02_1"><%=v_phone %></td>
                <td class="table_02_2">(<%=v_post1%>-<%=v_post2%>) <br><%=v_addr1%> <%=v_addr2%></td>
                <td class="table_02_1">
                  <%=CodeConfigBean.getCodeGubunSelect ("0074", "", 1, "p_paystat", v_paystat, "", 0)%>
                  <input type=hidden name="p_params" value="<%=v_seq%>">
                </td>
                <td class="table_02_2"><%=v_paymethodnm%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_pgauthdate"),"yyyy-MM-dd")%></td>
                <td class="table_02_1"><%=dbox.getString("d_iscancel")%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_refunddate"),"yyyy-MM-dd")%></td>
            </tr>
<%
        }

        if (i == 0) {
%>
            <tr>
                <td align="center" bgcolor="#FFFFFF" height="100" colspan="22">신청된 내용이 없습니다.</td>
              </tr>
<%     
        }
    }
%>
        </table>
        <br>
      </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>