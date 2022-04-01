<%
//**********************************************************
//  1. 제      목: 고객센타 > 학습자료실
//  2. 프로그램명 : ku_pds_L.jsp
//  3. 개      요: 학습자료실
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";
    String v_class1 = "";
    String v_class2 = "";
    int v_seq       = 0;
    int v_levels    = 0;
    int v_cnt       = 0;
    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");
    if (v_pageno == 0) v_pageno = 1;
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");

    String ss_gadmin   = (String)box.getSession("gadmin");
 
    ArrayList list       = (ArrayList)request.getAttribute("selectHomePageBoardList");
    box.put("leftmenu","04");                       // 메뉴 아이디 세팅                  
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//검색된 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_pageno.value = "1";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//상세화면보기 페이지로 이동
function select(seq, upfilecnt, userid) {
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = seq;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value    = userid;
    document.form1.submit();
}
//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_process.value = "";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
     document.form1.p_process.value = "";
     document.form1.submit();
}
//-->
</script>



<form name = "form1" enctype = "multipart/form-data" method = "post">
        <input type = "hidden" name = "p_process" value = "">
        <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
        <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
        <input type = "hidden" name = "p_seq"       value = "">
        <input type = "hidden" name = "p_upfilecnt" value = "">
        <input type = "hidden" name = "p_userid"    value = "">


                       <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 학습자료실</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_pds.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_pds.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="7"></td>
                          </tr>
                          <tr> 
                            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td>&nbsp;</td>
                                  <td width="10"><select name="p_search" class="input2">
                                      <option value="title" <% if (v_search.equals("title")) out.print("selected"); %>>:: 제목</option>
                                      <option value="content" <% if (v_search.equals("content")) out.print("selected"); %>> 내용 </option>
                                    </select></td>
                                  <td width="6">&nbsp;</td>
                                  <td width="135"> <input name="p_searchtext" type="text" class="input4"> 
                                  </td>
                                  <td width="4"><div align="right"></div></td>
                                  <td width="37"><a href="javascript:selectList()"><img src="/images/user/kocca/button/btn_search.gif" border="0"></a></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td height="8"></td>
                          </tr>
                        </table>
                        <!--게시판목록 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="5"><img src="/images/user/kocca/customer/bar_fir.gif" ></td>
                            <td width="669" class="tbl_bgcolor_notice"><table width="669" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="30" class="tbl_grc"><img src="/images/user/kocca/customer/text_no.gif" ></td>
                                  <td width="351" class="tbl_grc"><img src="/images/user/kocca/customer/text_subject.gif" ></td>
                                  <td width="75" class="tbl_grc"><img src="/images/user/kocca/customer/text_writer.gif" ></td>
                                  <td width="60" class="tbl_grc"><img src="/images/user/kocca/customer/text_search.gif" ></td>
                                  <td width="60" class="tbl_grc"><img src="/images/user/kocca/customer/st_add.gif"></td>
                                  <td width="93" class="tbl_grc"><img src="/images/user/kocca/customer/text_writedau.gif" ></td>
                                </tr>
                              </table></td>
                            <td width="6"><img src="/images/user/kocca/customer/bar_tail.gif" width="6" height="33"></td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="3"></td>
                          </tr>
                          <tr> 
                            <td height="1" colspan="3" class="tbl_bgcolor_notice"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
<%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_userid    = dbox.getString("d_userid");
                v_name      = dbox.getString("d_name");
                v_indate    = dbox.getString("d_indate");
                v_title     = dbox.getString("d_title");
                v_seq       = dbox.getInt("d_seq");
                v_cnt       = dbox.getInt("d_cnt");
                v_levels    = dbox.getInt("d_levels");
                v_upfilecnt = dbox.getInt("d_upfilecnt");
                v_dispnum   = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_rowcount  = dbox.getInt("d_rowcount");

                if(i%2 == 0){   
                    v_class1 = "board_text1";
                    v_class2 = "board_text3";
                }else{
                    v_class1 = "board_text2";
                    v_class2 = "board_text4";
                }

                    if (!v_searchtext.equals("")&&v_search.equals("title")) {
                        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                    } else if(!v_searchtext.equals("")&&v_search.equals("name")){
                        v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                    }

%>
                          <tr> 
                            <td width="35" class="tbl_grc"><%=v_dispnum%></td>
                            <td width="351" class="tbl_gleft">
                                <a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a> 
                                    <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%>
                                <img src="/images/user/kocca/customer/ico_new.gif" width="22" height="11"><%}%></a>
                            </td>
                            <td width="75" class="tbl_grc">운영자 (<%=v_userid%>)</td>
                            <td width="60" class="tbl_grc"><%=v_cnt%></td>
                            <td width="60" class="tbl_grc">
                                <% if (v_upfilecnt>0) {%> <img src="/images/user/kocca/customer/ico_add.gif" width="13" border="0">
                                <% }else{ %> - <%}%></td>
                            <td width="99" class="tbl_grc"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                          </tr>
<%}%>
<%if (i == 0){%>
                          <tr> 
                            <td width="640" class="tbl_grc" height="25"> 등록된 내용이 없습니다 </td>
                          </tr> 
<%}%>
                          <tr> 
                            <td height="1" colspan="6" class="linecolor_main01"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td class="tbl_bgcolor_notice" height="3" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td height="27" class="linecolor_notice" rowspan="2"> 
                              <!-- 페이지 -->
                              <table width="680" border="0" align="center" cellpadding="0" cellspacing="0">
                                  <tr> 
                                    <td width="540">
                                    <%= PageUtil.printPageListKocca(v_totalpage, v_pageno, row) %>
                                    </td>
                                    <td align="right" valign="bottom"> 
                                        <a href="javascript:insertPage()"><img src="/images/user/game/button/btn_register.gif" border="0"></a>&nbsp;&nbsp;
                                    </td>
                                  </tr>
                                </table>  
                            </td>
                          </tr>
                          <tr> 
                            <td height="1" class="linecolor_main01"></td>
                          </tr>
                        </table>
    </form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->