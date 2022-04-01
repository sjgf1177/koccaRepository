<%
//**********************************************************
//  1. 제      목: 교재판매 상세보기
//  2. 프로그램명 : zu_Book_R.jsp
//  3. 개      요: 교재판매 상세보기
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","09");                       // 메뉴 아이디 세팅
    String  v_process = box.getString("p_process");
    int v_pageno         = box.getInt("p_pageno");

    String ss_classify   = box.getStringDefault("s_classify","ALL");
    String v_searchtext  = box.getString("p_searchtext");
    String v_bookcode      = box.getString("p_bookcode");          // 교재코드

    String v_classify      = "";          // 분류
    String v_codenm        = "";          // 분류명
    String v_bookname      = "";          // 교재명
    int    v_price         = 0;           // 정가
    int    v_dis_price     = 0;           // 판매가
    String v_intro         = "";          // 교재소개
    String v_writer        = "";          // 저자소개
    String v_content       = "";          // 목차
    String v_review        = "";          // 리뷰
    String v_recom         = "";          // 추천교재여부
    String v_luserid       = "";          // 등록자
    String v_ldate         = "";          // 등록일
    String v_realfilename1 = "";          // 큰이미지   실제파일명
    String v_newfilename1  = "";          // 큰이미지   저장파일명
    String v_realfilename2 = "";          // 작은이미지 실제파일명
    String v_newfilename2  = "";          // 작은이미지 저장파일명
    String v_image_view1   = "";
    String v_image_view2   = "";

    String upload_url = conf.getProperty("url.upload");
    DecimalFormat  df = new DecimalFormat("###,##0");

    DataBox dbox = (DataBox)request.getAttribute("selectBook");

    if (dbox != null) {
        v_classify      = dbox.getString("d_classify");
        v_codenm        = dbox.getString("d_codenm");
        v_bookname      = dbox.getString("d_bookname");
        v_price         = dbox.getInt   ("d_price");
        v_dis_price     = dbox.getInt   ("d_dis_price");
        v_intro         = dbox.getString("d_intro");
        v_writer        = dbox.getString("d_writer");
        v_content       = dbox.getString("d_content");
        v_review        = dbox.getString("d_review");
        v_recom         = dbox.getString("d_recom");
        v_luserid       = dbox.getString("d_luserid");
        v_ldate         = dbox.getString("d_ldate");
        v_realfilename1 = dbox.getString("d_realfilename1");
        v_newfilename1  = dbox.getString("d_newfilename1");
        v_realfilename2 = dbox.getString("d_realfilename2");
        v_newfilename2  = dbox.getString("d_newfilename2");

        if (v_newfilename1.equals("")) v_image_view1 = "/images/user/game/book/blank_book1.gif";
        else                           v_image_view1 = upload_url + "bulletin/" + v_newfilename1;
        if (v_newfilename2.equals("")) v_image_view2 = "/images/user/game/book/blank_book2.gif";
        else                           v_image_view2 = upload_url + "bulletin/" + v_newfilename2;

        v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");
        v_intro   = StringManager.replace(v_intro,"\n","<br>");
        v_writer  = StringManager.replace(v_writer,"\n","<br>");
        v_content = StringManager.replace(v_content,"\n","<br>");
        v_review  = StringManager.replace(v_review,"\n","<br>");
     }

     String s_userid = box.getSession("userid");
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--

function listBook() {
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

// 교재신청
function whenBookPropose(bookcode) {
/*
    document.form1.p_bookcode.value = bookcode;

     var new_Open = window.open("","BookBill","scrollbars=auto,width=700,height=430,resizable=no");
     document.form1.target = "BookBill";
     document.form1.p_process.value = "bookBillPage";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.submit();
*/

    farwindow = window.open("", "winInicis", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
    document.fbill.p_bookcode.value = bookcode;
    document.fbill.target = "winInicis";
    document.fbill.submit();

    farwindow.window.focus();

}

	//장바구니 담기
	function whenBookBasket(bookcode, price) {

	   if(!confirm("장바구니에 담으시겠습니까?")){
		 return;
	   }

	   document.fcart.target = "_self";
	   document.fcart.p_price.value = price;
	   document.fcart.p_bookcode.value = bookcode;
	   document.fcart.p_process.value = "InsertBasketBook";
	   document.fcart.action = "/servlet/controller.account.AccountManagerServlet";
	   document.fcart.submit();
	}


//-->
</SCRIPT>
<form name="fbill" method="post" action="/inicis/plugin_check_Book.jsp">
	<input type="hidden" name="p_bookcode">
</form>

<form name="fcart" method="post">
	<input type="hidden" name="p_process">
	<input type="hidden" name="p_bookcode">
	<input type="hidden" name="p_price">
</form>

<form name="form1" method="post">
    <input type = "hidden" name="p_process"    value = "<%=v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="s_classify"   value = "<%=ss_classify %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_bookcode"   value = "<%=v_bookcode%>">

                  <!-- 타이틀 -->
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="35" align="right" background="/images/user/game/book/<%=tem_subimgpath%>/tit_01.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > book > <strong>교재판매</strong></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>

                  <!--본문 시작-->
                  <TABLE id="Table5" cellSpacing="0" cellPadding="0" width="606" border="0">
                    <TR>
                      <TD vAlign="top" align="center">
                                         <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="2" >
                      <!--
                      <font color="blue"><b>※ 1권 구매시 배송료 착불(\3,500), 2권 이상 구매시 배송료 무료 (배송료는 선결제되지 않습니다.)
                      <br>※ 도서구입 내역은 수강신청 -> <a href="/servlet/controller.account.AccountManagerServlet?p_process=MyBillList">결제내역</a>에서 확인 가능합니다. 교재관련문의) 02-540-8436
                      </b></font>
                      -->
                      <font color="red"><b>※ 교재판매가 일시중단 됨을 알려드립니다. 자세한 사항은 공지사항 참고 하세요.
                      </b></font>
                      </td>
                    </tr>
                   </table>

                      <TABLE id="Table6" cellSpacing="0" cellPadding="0" width="100%" border="0">
                          <TR>
                            <TD bgColor="#dadada" height="1"></TD>
                          </TR>

                          <TR>
                            <TD bgColor="#dadada" height="1"></TD>
                          </TR>
                          <TR>
                            <TD valign="top">

                            <TABLE id="Table12" cellSpacing="0" cellPadding="0" width="720" border="0">
                                <TR>
                                  <TD height="5" colspan="2" align="center" valign="top" ></TD>
                                </TR>

                                <TR>
                                  <TD width="26%" height="205"  align="center" valign="top" class="n_gray"><img src="<%=v_image_view1%>" width="148" height="220">
                                  </TD>
                                  <!--글 내용 시작-->
                                  <TD class="type_indent10" width="74%" valign="top"><asp:label id="lblContent" Runat="server">
                                      <table width="524" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                          <td colspan="3">&nbsp;</td>
                                        </tr>
                                        <tr>
                                          <td colspan="3" class="tit_book"><strong><%=v_bookname%></strong></td>
                                        </tr>
                                        <tr>
                                          <td width="64" style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" ><font color="#000000">
                                            정가 :</font></td>
                                          <td style="padding-top:3px;"><%=df.format(v_price)%>원
                                          </td>
                                          <td width="397" rowspan="2" valign="bottom" style="padding-bottom:2px;">
                                          <% //if (s_userid.equals("lee1") || s_userid.equals("gameacademy") || s_userid.equals("br0809") || s_userid.equals("supreme7701") || s_userid.equals("ironsky") || s_userid.equals("sr0809") || s_userid.equals("sd1076")) { %>
                                          <!--<%=v_bookcode%>-->
                                          <%// if (v_bookcode.equals("1018") || v_bookcode.equals("1017") || v_bookcode.equals("1016") || v_bookcode.equals("1015") || v_bookcode.equals("1014") || v_bookcode.equals("1013") || v_bookcode.equals("1012")) { %>
                                          <!--a href="javascript:whenBookPropose('<%=v_bookcode%>')"><img src="/images/user/game/book/btn_buy.gif" border="0"></a-->
                                          <!--
                                          <% if (s_userid.equals("")) { %>
                                           	<a href="javascript:alert('로그인 후 이용 가능합니다.');"><img src="/images/user/button/btn_getcart.gif" border="0"></a>
                                          <% } else { %>
                                          	<a href="javascript:whenBookBasket('<%=v_bookcode%>',<%=v_dis_price%>)"><img src="/images/user/button/btn_getcart.gif" border="0"></a>
                                          <% } %>
                                          -->
                                          <%// } %>

                                            <a href="javascript:listBook()"><img src="/images/user/game/book/btn_booklist.gif" width="67" height="19" border="0"></a>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                                            <font color="#000000">판매가 :</font></td>
                                          <td width="63" style="padding-top:3px;"><strong><%=df.format(v_dis_price)%>원
                                            </strong></td>
                                        </tr>
                                        <tr >
                                          <td height="5" colspan="3" background="/images/user/game/book/line_bg.gif"></td>
                                        </tr>
                                        <tr>
                                          <td height="5" colspan="3" valign="top"></td>
                                        </tr>
                                        <tr>
                                          <td valign="top" style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                                            <font color="#000000">책소개 :</font></td>
                                          <td colspan="2" style="padding-top:3px;"><%=v_intro%></td>
                                        </tr>
                                        <tr>
                                          <td height="5" colspan="3" ></td>
                                        </tr>
                                        <tr>
                                          <td style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                                            <font color="#000000">저자 :</font></td>
                                          <td colspan="2" style="padding-top:3px;"><%=v_writer%></td>
                                        </tr>
                                      </table>
                                    </asp:label></TD>
                                  <!--글 내용 끝-->
                                </TR>
                              </TABLE></TD>
                          </TR>
                          <TR>
                            <TD bgColor="#f5f5f5" height="5"></TD>
                          </TR>
                        </TABLE></TD>
                    </TR>
                  </TABLE>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td align="right"><table width="699" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="10"></td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/game/book/st_booklist.gif"></td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="699" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><%=v_content%></td>
                          </tr>
                        </table>
                        <table width="699" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <td><img src="/images/user/game/book/st_review.gif" width="352" height="15"></td>
                          </tr>
                          <tr>
                            <td height="5"></td>
                          </tr>
                        </table>
                        <table width="699" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><%=v_review%></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->