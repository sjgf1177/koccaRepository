 <%
//**********************************************************
//  1. 제      목: 교재신청확인/취소 
//  2. 프로그램명 : zu_BookPropose_L.jsp
//  3. 개      요: 교재신청확인/취소 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅
    String  v_process = box.getString("p_process");

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
    String v_iscancel      = "";          // 취소구분
    String v_refundstat    = "";          // 환불상태
    String v_luserid       = "";          // 등록자
    String v_ldate         = "";          // 등록일

    String v_paystat_view = "";
    String v_paymethod = "";
    String v_paymethodnm = "";
    String v_btn_view = "";

    ArrayList list = (ArrayList)request.getAttribute("selectUserSellList");

    DecimalFormat  df = new DecimalFormat("###,##0");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topBook.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

// 교재신청취소
function whenBookProposeCancel1(seq) {
    document.form1.p_seq.value = seq;
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "bookProposeCancel";
    document.form1.submit();
}

// 교재신청취소
function whenBookProposeCancel2(seq) {
    document.form1.p_seq.value = seq;

    var new_Open = window.open("","whenBookProposeCancel2","scrollbars=auto,width=400,height=255,resizable=no");
    document.form1.target = "whenBookProposeCancel2";
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "bookBillCancelPage";
    document.form1.submit();
}

//-->
</SCRIPT>

<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process" value = "<%= v_process %>">
    <input type = "hidden" name = "p_seq"     value = "">

                  <!-- 타이틀 -->
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="35" align="right" background="/images/user/game/book/<%=tem_subimgpath%>/tit_01.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > book > <strong>교재신청 확인/취소</strong></td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>
             
                  <table width="720" border="2" cellspacing="0"  cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" rules="rows" frame="hsides">
                    <tr> 
                      <td width="120" align="center" bgcolor="#F5FBE3"><font color="#000000">교재명</font></td>
                      <td width="70" align="center" bgcolor="#E4F4B5"><font color="#000000">신청일</font></td>
                      <td width="60"  align="center" bgcolor="#F5FBE3"><font color="#000000">금액</font></td>
                      <td width="60"  align="center" bgcolor="#E4F4B5"><font color="#000000">수신자명</font></td>
                      <td width="180" align="center" bgcolor="#F5FBE3"><font color="#000000">수신자주소</font></td>
                      <td width="55" align="center" bgcolor="#E4F4B5"><font color="#000000">입금상태</font></td>
                      <td width="55" align="center" bgcolor="#F5FBE3"><font color="#000000">결제구분</font></td>
                      <td width="90" align="center" bgcolor="#E4F4B5"><font color="#000000">신청취소</font></td>
                    </tr>
<%  
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
            v_iscancel      = dbox.getString("d_iscancel");
            v_refundstat    = dbox.getString("d_refundstat");
            v_luserid       = dbox.getString("d_luserid"); 
            v_ldate         = dbox.getString("d_ldate");
            v_paymethod		= dbox.getString("d_paymethod");
            v_paymethodnm	= dbox.getString("d_paymethodnm");

            if (v_iscancel.equals("N")) {
                v_paystat_view  = GetCodenm.get_codenm("0074",v_paystat);
            } else {
               v_paystat_view  = GetCodenm.get_codenm("0075",v_refundstat);
            }

            if (v_iscancel.equals("N") && v_paystat.equals("A")) { 
                v_btn_view = "<a href=\"javascript:whenBookProposeCancel1('" + v_seq + "')\"><img src='/images/user/game/button/b_apply_cancel.gif'></a>";
            } else if (v_iscancel.equals("N") && v_paystat.equals("B")) { 
                v_btn_view = "<a href=\"javascript:whenBookProposeCancel2('" + v_seq + "')\"><img src='/images/user/game/button/b_apply_cancel.gif'></a>";
            } else {
                v_btn_view = "";
            }
%>
                    <tr> 
                      <td class="tbl_gleft"><%=v_bookname %></td>
                      <td class="tbl_grc"><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
                      <td class="tbl_grc"><%=df.format(v_dis_price)%></td>
                      <td class="tbl_grc"><%=v_receive %></td>
                      <td class="tbl_gleft">(<%=v_post1%>-<%=v_post2%>) <br><%=v_addr1%> <%=v_addr2%></td>
                      <td class="tbl_grc"><%=v_paystat_view%></td>
                      <td class="tbl_grc"><%=v_paymethodnm%></td>
                      <td class="tbl_grc"><%=v_btn_view%></td>
                    </tr>
<%
        }

        if (i == 0) {
%>
                    <tr>
                      <td align="center" bgcolor="#FFFFFF" height="100" colspan="8">신청된 내용이 없습니다.</td>
                    </tr>
<%     
        }
%>
                  </table> 
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
