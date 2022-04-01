<%
//**********************************************************
//  1. 제      목: 자료실
//  2. 프로그램명 : zu_HomePageBoard_L.jsp
//  3. 개      요: 자료실 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 이연정
//  7. 수      정: 2005.07.01 이연정
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
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
    ArrayList list      = (ArrayList)request.getAttribute("selectHomePageBoardList");
box.put("leftmenu","04");                       // 메뉴 아이디 세팅
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
//검색된 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_pageno.value = "1";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//상세화면보기 페이지로 이동
function select(seq, upfilecnt, userid) {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = seq;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value    = userid;
    document.form1.submit();
}
//페이지 이동
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
	 document.form1.p_process.value = "";
	 document.form1.submit();
}
//-->
</script>
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 자료실</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_data.jpg" ></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
				  
                  <!-- qna table -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
				<form name = "form1" enctype = "multipart/form-data" method = "post">
   			    <input type = "hidden" name = "p_process" value = "">
				<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
				<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
				<input type = "hidden" name = "p_seq"       value = "">
				<input type = "hidden" name = "p_upfilecnt" value = "">
				<input type = "hidden" name = "p_userid"    value = "">
                    <tr valign="top"> 
                      <td colspan="6"> <table width="675" border="0" cellpadding="0" cellspacing="0" background="/images/user/support/sup_tb_bg.gif">
                          <tr> 
                            <td width="6"><img src="/images/user/support/sup_tb_head.gif" width="5" height="28"></td>
                            <td width="39" align="center" valign="bottom"><img src="/images/user/support/sup_tb_num.gif" width="19" height="20"></td>
                            <td width="316" align="center" valign="bottom"><img src="/images/user/support/sup_tb_title.gif" width="19" height="20"></td>
                            <td width="143" align="center" valign="bottom"><img src="/images/user/support/sup_tb_pe.gif" width="29" height="20"></td>
                            <td width="82" align="center" valign="bottom"><img src="/images/user/support/sup_tb_day.gif" width="27" height="20"></td>
                            <td width="50" align="center" valign="bottom"><img src="/images/user/support/sup_tb_addfile.gif" width="38" height="20"></td>
                            <td width="29" align="center" valign="bottom"><img src="/images/user/support/sup_tb_view.gif" width="18" height="20"></td>
                            <td width="10" align="right"><img src="/images/user/support/sup_tb_tail.gif"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/sup_tb_tline.gif"></td>
                    </tr>
                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>

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
                      <td width="45" class="tbl_br"><%=v_dispnum%></td>
                      <td width="316" class="tbl_bleft"><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a> 
					  <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%><img src="/images/user/support/ico_new.gif"><%}%></td>

                     <td width="143" class="tbl_br"><%=v_name%>(<%=v_userid%>)</td>
                      <td width="82" class="tbl_br"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                      <td width="51" align="center"><% if (v_upfilecnt>0) {%> <img src="/images/user/support/ico_file.gif" width="14" height="14"><% } %></td>
                      <td width="38" class="tbl_br"><%=v_cnt%></td>
                    </tr>
                    <tr> 
                      <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
                    </tr>

<%
        }
%>
<%   if (i == 0 ) {                         %>
                      <tr> 
                        <td class="board_text1" colspan ='5' align='center'>등록된 내용이 없습니다.</td>
                      </tr>
<%   }                                     %>

                    <tr> 
                      <td height="3" colspan="6"></td>
                    </tr>
                    <tr bgcolor="B8D5E5"> 
                      <td height="5" colspan="6"><img src="/images/user/support/sup_tb_bo.gif" width="675" height="5"></td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="10" colspan="2"></td>
                    </tr>
                    <tr> 
                      <td width="418" align="right">
              <!----------------- 페이지 시작 ----------------->
              <table width="130" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td>
                            <%= HomePageQnaBean.printPageList(v_totalpage, v_pageno, row) %>
                            </td>
                          </tr>
                        </table>
              <!----------------- 페이지 끝 ----------------->
					
						</td>
                      <td width="257" align="right" valign="top"><a href="javascript:insertPage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_register.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                    <tr> 
                      <td width="227">&nbsp;</td>
                      <td width="56"> 
					    <select name="p_search" >
						  <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                          <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                          <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
                        </select> </td>
                      <td width="132"><input name="p_searchtext" type="text" class="input" value='<%=v_searchtext %>' size="20"></td>
                      <td width="260"><a href = "javascript:selectList();"><img src="/images/user/button/btn_search.gif"></a>
					  </td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
				  </form>
				  </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->