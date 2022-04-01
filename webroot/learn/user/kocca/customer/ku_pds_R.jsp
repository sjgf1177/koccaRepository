<%
//**********************************************************
//  1. 제      목: 고객센타 > 학습자료실
//  2. 프로그램명 : ku_pds_R.jsp
//  3. 개      요: 학습자료실
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq     = box.getInt("p_tabseq");
    String  v_process    = box.getString("p_process");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_pageno     = box.getInt("p_pageno");
    int v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int v_orgseq     = v_seq;
    int v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    int v_levels     = 0;
    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 0;
    int i            = 0;
    int v_cnt        = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;

    DataBox dbox = (DataBox)request.getAttribute("selectHomePageBoard");
    if (dbox != null ) {
        v_seq           = dbox.getInt("d_seq");
        v_userid        = dbox.getString("d_userid");
        v_name          = dbox.getString("d_name");
        v_title         = dbox.getString("d_title");
        v_content       = dbox.getString("d_content");

        v_cnt           = dbox.getInt("d_cnt");
        v_indate        = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");

    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");
    
box.put("leftmenu","04");                       // 메뉴 아이디 세팅

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//답변페이지로 이동
function replyPage() {
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_process.value = "replyPage";
    document.form1.submit();
}

//내용 삭제
function delete_HomePageBoard() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form1.p_userid.value = "<%= v_userid %>";
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    else {
        return;
    }
}

//리스트 페이지로 이동
function selectList() {
    document.form1.p_pageno.value = "1";
    document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//수정 페이지로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.KHomeBoardServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//-->
</script>

<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
		<input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq %>">
		<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
		<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
		<input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
		<input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
		<input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">
		<input type = "hidden" name = "p_process"	 value = "">

		  <!-- center start -->
						<table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 학습자료실</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_pds.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_pds.gif" height="33"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="9"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td colspan="6"><img src="/images/user/kocca/customer/bar_1vs1_top.gif" width="680" height="7" ></td>
                          </tr>
                          <tr> 
                            <td height="2" colspan="6"></td>
                          </tr>
                          <tr> 
                            <td width="75" class="textcolor_customer01">질문제목</td>
                            <td width="605" colspan="5" class="tbl_gleft01"><%= v_title %></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1"></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1"></td>
                          </tr>
                          <tr> 
                            <td width="75"  class="textcolor_customer01">작성자</td>
                            <td width="220" class="tbl_gleft02"><%=v_name%></td>
                            <td width="75"  class="textcolor_customer01">작성일</td>
                            <td width="110" class="tbl_gleft02"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                            <td width="75"  class="textcolor_customer01">조회수</td>
                            <td width="125" class="tbl_gleft02"><%=v_cnt%></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="2"></td>
                          </tr>
                          <tr> 
                            <td colspan="6" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td colspan="6"><table width="670" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="24" colspan="2">&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td width="40">&nbsp;</td>
                                  <td><%=v_content%></td>
                                </tr>
                                <tr> 
                                  <td height="20" colspan="2">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
						  <tr> 
							<td width="75" class="textcolor_customer01">첨부파일</td>
							<td colspan="5" align="center"><table width="603" border="0" cellspacing="0" cellpadding="0">
								<tr> 
								  <td bgcolor="EEF0F5" height="15">
								  <%		
										if( v_realfileVector != null ) { 
											for( i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

												String v_realfile = (String)v_realfileVector.elementAt(i);
												String v_savefile = (String)v_savefileVector.elementAt(i);  	

												if(v_realfile != null && !v_realfile.equals("")) {  %> 
									<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'> 
									  <%= v_realfile%></a><br>
									  <input type="hidden" name="p_savefile"  value=<%=v_savefile%>>
									  <%	}else{
									  %>
									첨부된 파일이 없습니다.
									<%			}
											}
										}%>
								  </td>
								</tr>
							  </table></td>
						  </tr>
                          <tr> 
                            <td colspan="6" height="1" class="linecolor_main01"></td>
                          </tr>
                          <tr> 
                            <td height="3" colspan="6" class="linecolor_customer02"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="20"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="27" class="linecolor_notice"><table width="160" border="0" align="right" cellpadding="0" cellspacing="0">
                                      <tr align="right"> 
                                        <td><a href="javascript:updatePage()"><img src="/images/user/kocca/button/btn_mod.gif" border="0"></a>&nbsp;<a href="javascript:delete_HomePageBoard()"><img src="/images/user/kocca/button/btn_del.gif" border="0"></a>&nbsp;<a href="javascript:selectList()"><img src="/images/user/kocca/button/btn_list.gif" border="0"></a>&nbsp;</td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height="1" bgcolor="#cccccc"></td>
                                </tr>
                              </table>
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="20">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->