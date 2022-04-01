<%
//**********************************************************
//  1. 제      목: 교재판매
//  2. 프로그램명 : zu_Book_L.jsp
//  3. 개      요: 교재판매
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
    int row              = 10 ;
    if (v_pageno == 0)  v_pageno = 1;

    String  ss_classify   = box.getStringDefault("s_classify","ALL");
    String  v_searchtext  = box.getString("p_searchtext");

    int    i = 0;
    String v_bookcode      = "";          // 교재코드
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

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("selectUserBookList");

    String upload_url = conf.getProperty("url.upload");
    DecimalFormat  df = new DecimalFormat("###,##0");


    /*------------------------ 텝 색깔 이미지 --------------------------*/
    String classify_img1 = "tab_01.gif";
    String classify_img2 = "tab_02.gif";
    String classify_img3 = "tab_03.gif";
    String classify_img4 = "tab_04.gif";
    String classify_img5 = "tab_05.gif";

    if (ss_classify.equals("ALL"))      classify_img1 = "tab_01on.gif";
    else if (ss_classify.equals("G01")) classify_img2 = "tab_02on.gif";
    else if (ss_classify.equals("G02")) classify_img3 = "tab_03on.gif";
    else if (ss_classify.equals("G03")) classify_img4 = "tab_04on.gif";
    else if (ss_classify.equals("G04")) classify_img5 = "tab_05on.gif";
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
function go(index) {
     document.form1.p_pageno.value = index;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.p_process.value = "listPage";
     document.form1.submit();
}
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.p_process.value = "listPage";
     document.form1.submit();
}

//상세보기
function view(bookcode) {
    document.form1.p_bookcode.value = bookcode;
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.target = "_self";
    document.form1.p_process.value = "selectPage";
    document.form1.submit();
}

// 일반/전문가, 대분류코드 선택
function whenSelect(classify){
    document.form1.s_classify.value   = classify;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.book.BookServlet';
    document.form1.p_process.value = 'listPage';

    document.form1.submit();
}

function whenSearch() {
    document.form1.p_pageno.value = 1;
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

// 교재신청
function whenBookPropose(bookcode) {
    document.form1.p_bookcode.value = bookcode;

     var new_Open = window.open("","BookBill","scrollbars=auto,width=700,height=430,resizable=no");
     document.form1.target = "BookBill";
     document.form1.p_process.value = "bookBillPage";
     document.form1.action = "/servlet/controller.book.BookServlet";
     document.form1.submit();
}

//-->
</SCRIPT>

<form name = "form1" method = "post">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"   value = "<%=v_pageno %>">
    <input type = "hidden" name = "s_classify" value = "<%=ss_classify%>">
    <input type = "hidden" name = "p_bookcode" value = "">

                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="35" align="right" background="/images/user/game/book/<%=tem_subimgpath%>/tit_01.gif" class="location"><img src="/images/user/game/common/location_bl.gif"> HOME > book > <strong>교재판매</strong></td>
                    </tr>
                    <tr>
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>

                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="5" align="right">
                      <a href="javascript:whenSelect('ALL')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image48','','/images/user/game/book/tab_01on.gif',1)"><img src="/images/user/game/book/<%=classify_img1%>" name="Image48" width="96" height="32" border="0"></a><a href="javascript:whenSelect('G01')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image49','','/images/user/game/book/tab_02on.gif',1)"><img src="/images/user/game/book/<%=classify_img2%>" name="Image49" width="109" height="32" border="0"></a><a href="javascript:whenSelect('G02')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image50','','/images/user/game/book/tab_03on.gif',1)"><img src="/images/user/game/book/<%=classify_img3%>" name="Image50" width="109" height="32" border="0"></a><a href="javascript:whenSelect('G03')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image51','','/images/user/game/book/tab_04on.gif',1)"><img src="/images/user/game/book/<%=classify_img4%>" name="Image51" width="109" height="32" border="0"></a><a href="javascript:whenSelect('G04')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image52','','/images/user/game/book/tab_05on.gif',1)"><img src="/images/user/game/book/<%=classify_img5%>" name="Image52" width="109" height="32" border="0"></a><br>
                      </td>
                    </tr>
                    <tr>
                      <td height="5" bgcolor="#ededed"></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td colspan="2" >
                      <!--
                      <font color="blue"><b>※ 1권 구매시 배송료 착불(\3,500), 2권 이상 구매시 배송료 무료 (배송료는 선결제되지 않습니다.)
                      <br>※ 도서구입 내역은 수강신청 -> <a href="/servlet/controller.account.AccountManagerServlet?p_process=MyBillList">결제내역</a>에서 확인 가능합니다. 교재관련문의) 02-540-8436
                      </b></font>
                      -->
                      <br><font color="red"><b>※ 교재판매가 일시중단 됨을 알려드립니다. 자세한 사항은 공지사항 참고 하세요.
                      </b></font>
                      </td>
                    </tr>
                   </table>

<%

            for( i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_bookcode      = dbox.getString("d_bookcode");
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

                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpagecount");

                if (v_newfilename1.equals("")) v_image_view1 = "/images/user/game/book/blank_book1.gif";
                else                           v_image_view1 = upload_url + "bulletin/" + v_newfilename1;
                if (v_newfilename2.equals("")) v_image_view2 = "/images/user/game/book/blank_book2.gif";
                else                           v_image_view2 = upload_url + "bulletin/" + v_newfilename2;
                v_ldate   = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");


%>

                  <!--내용시작-->
                  <table width="720" border="0" cellspacing="0" cellpadding="3"   >
                    <tr>
                      <td colspan="2" class="tblcolor2"></td>
                    </tr>
                    <tr>
                      <td width="131" class="tbl_bc"><a href="javascript:view('<%=v_bookcode%>')"><img src="<%=v_image_view2%>" width="67" height="100" border="0"></a></td>
                      <td width="577" valign="top" class="tbl_bleft">
                        <table width="556" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="5" colspan="3"></td>
                          </tr>
                          <tr>
                            <td colspan="3" class="tit_book"><a href="javascript:view('<%=v_bookcode%>')"><%=v_bookname%></a>
                            <%
                            /*
                            if (v_bookcode.equals("1018") || v_bookcode.equals("1017") || v_bookcode.equals("1016")
                            || v_bookcode.equals("1015") || v_bookcode.equals("1014") || v_bookcode.equals("1013")
                            || v_bookcode.equals("1012") || v_bookcode.equals("1008") || v_bookcode.equals("1007")
                            || v_bookcode.equals("1009") || v_bookcode.equals("1010") || v_bookcode.equals("1011")
                            || v_bookcode.equals("1001") || v_bookcode.equals("1002") || v_bookcode.equals("1004")) {
                            */
                            %>
                            	<!--<font color="blue">(판매중)</font>-->
                            <% //} %>
                            </td>
                          </tr>
                          <tr>
                            <td width="67" style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                              정가 :</td>
                            <td width="78" style="padding-top:3px;"><%=df.format(v_price)%>원 </td>
                            <td width="411" rowspan="2" valign="bottom" style="padding-bottom:2px;"><!--a href="javascript:whenBookPropose('<%=v_bookcode%>')"><img src="/images/user/game/book/btn_buy.gif" border="0"></a--></td>
                          </tr>
                          <tr>
                            <td style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                              판매가 :</td>
                            <td style="padding-top:3px;"><strong><%=df.format(v_dis_price)%>원 </strong></td>
                          </tr>
                          <tr>
                            <td style="padding-top:3px;"><img src="/images/user/game/book/bl-1.gif" >
                              내용 :</td>
                            <td colspan="2" style="padding-top:3px;"><%=StringManager.formatTitle(v_intro, 100)%></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr >
                      <td height="5" colspan="2" background="/images/user/game/book/line_bg.gif"></td>
                    </tr>
                    <tr >
                      <td height="5" colspan="2"></td>
                    </tr>
                  </table>
<%
            }

        if(i == 0 ) {
%>
                  <table width="720" border="0" cellspacing="0" cellpadding="3"   >
                    <tr>
                      <td class="table_02_1" height="100" colspan="2" align="center"> 등록된 교재가 없습니다. </td>
                    </tr>
                    <tr >
                      <td height="5" colspan="2" background="/images/user/game/book/line_bg.gif"></td>
                    </tr>
                    <tr >
                      <td height="5" colspan="2"></td>
                    </tr>
                  </table>
<%
        }
%>

                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="5" align="center"></td>
                    </tr>
                    <tr>
                      <td align="center">
                        <%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                      </td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="10"></td>
                    </tr>
                  </table>
                  <table width="720" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td width="100"><b>교재명 : </b></td>
                            <td width="132"> <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
                            </td>
                            <td width="100"><div align="center"><a href="javascript:whenSearch()"><img src="/images/user/game/button/btn_j.gif"></a></div></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="5"></td>
                    </tr>
                  </table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
