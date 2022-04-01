<%
//**********************************************************
//  1. 제      목: 홈페이지자료실
//  2. 프로그램명 : zu_SoftDataBoard_R.jsp
//  3. 개      요: 홈페이지자료실 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.19
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
    RequestBox box     = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int v_levels = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    i         = 0;
    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";


    Vector v_realfileVector = null;
    Vector v_savefileVector = null;

    DataBox dbox = (DataBox)request.getAttribute("selectSoftwareBoard");
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
 //       v_content = StringManager.replace(v_content,"\r\n","<br>");
//        v_content = BoardBean.convertBody(v_content);

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    // 리스트
    ArrayList list = (ArrayList)request.getAttribute("selectSoftwareBoardList");
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%//@ include file="/jsp/user/homepage/supporttop.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }
    
    function reply_SoftDataBoard() {
        document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }

    function modify_SoftDataBoard() {
        document.form1.p_userid.value = "<%= v_userid %>";
        document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_SoftDataBoard() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.p_userid.value = "<%= v_userid %>";
            document.form1.p_pageno.value = "1";
            document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_SoftDataBoard() {
        document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }


    function selectList() {
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.homepage.SoftwareBoardServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }
//-->
</SCRIPT>

    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">

              <!----------------- page 경로 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="route_table">
                <tr> 
                  <td class="route_text"><img src="/images/user/homepage/arrow_icon2.gif" width="20" height="7" border="0">
				  현재위치 : Home &gt; 사이버연수원 &gt; SW자료실</td>
                </tr>
                <tr> 
                  <td class="route_line"></td>
                </tr>
              </table>
              <!----------------- page 경로 끝 ----------------->

              <!----------------- 타이틀 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="b_title_table">
                <tr> <td height="10"></td></tr>
                <tr> 
                  <td background="/images/user/homepage/information_b_title_bg1.gif">
				  <img src="/images/user/homepage/information_b_title2.gif" width="370" height="46" border="0">
				  </td>
                  <td width="7" background="/images/user/homepage/information_b_title_bg1.gif">
				  <img src="/images/user/homepage/information_b_title_bg2.gif" width="7" height="46" border="0">
				  </td>
                </tr>
                <tr> <td height="7"></td></tr>
              </table>
              <!----------------- 타이틀 끝 ----------------->
              <br>

              <!----------------- SW자료실  보기 시작 ---------------->
              <table cellspacing="0" cellpadding="0" class="information_board_color_line_out1">
                <tr> <td class="information_board_color_line"></td></tr>
              </table>

              <table cellspacing="0" cellpadding="0" class="information_board_table_out1">
                <tr> 
                  <td> 
                    <table cellspacing="1" cellpadding="3" class="table2">
                      <tr> 
                        <td class="information_board_title_bg1" width="20%">제 목</td>
                        <td class="board_title_bg3" width="30%"><%= v_title %> </td>
                        <td class="information_board_title_bg1" width="20%">조회수</td>
                        <td class="board_title_bg3" width="30%"><%=v_cnt%></td>
                      </tr>
                      <tr> 
                        <td class="information_board_title_bg1" width="20%">등록일</td>
                        <td class="board_title_bg3"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                        <td class="information_board_title_bg1" width="20%">작성자</td>
                        <td class="board_title_bg3"><%=v_name%></td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="board_text1" align="center"> 
                          <table width="98%" border="0" cellspacing="0" cellpadding="0">
                            <tr> 
                              <td height="7"></td>
                            </tr>
                            <tr> 
                              <td><br><%= v_content %></td>
                            </tr>
                          </table>
                        </td>
                      </tr>
						<!---------파일첨부여부 ------------>

						<%  if(box.getBoolean("p_isUpload") ) {    
								for(i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
								String v_realfile = (String)v_realfileVector.elementAt(i);
								String v_savefile = (String)v_savefileVector.elementAt(i);                        
						%>
						  <tr>
							<td class = "information_board_title_bg1" width = "20%" align = "center">첨부파일</td>
							<td class = "table_text3" width = "80%" align = "left" colspan="3">
							<%		if(v_realfile != null && !v_realfile.equals("")) {  %>
									&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
									<%= v_realfile%></a><br>
									<input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>>
							<%		}   %>
							</td>
						  </tr>
						<%      }   
							}
						%>
					<!---------파일첨부여부 끝 ------------>
					</table>
                  </td>
                </tr>
              </table>
              <!----------------- SW자료실 보기 끝 ---------------->
              <br>


              <!----------------- 목록 버튼 시작 ---------------->
              <table width="200" border="0" cellspacing="0" cellpadding="0">
                <tr> 

<%
                    if(box.getBoolean("p_isReply")) {    //    답변 가능여부
                      if(BulletinManager.isAuthority(box, box.getString("p_canReply"))) {    //    답변 여부
                %>
                    <td align="center">
                    <a href="javascript:reply_SoftDataBoard()"><img src="/images/user/homepage/reply_butt1.gif" width="45" height="16" border="0"></a>
                    </td>
                <%
                      }
                    }

                    if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {    //    삭제 여부
                %>
                    <td align="center"><a href="javascript:delete_SoftDataBoard()">
                    <img src="/images/user/homepage/del_butt1.gif" width="45" height="16" border="0">
                    </td>
                <%
                    }
                    if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    수정 여부
                %>
                    <td align="center">
                    <a href="javascript:modify_SoftDataBoard()"><img src="/images/user/homepage/modify_butt1.gif" width="45" height="16" border="0"></a>
                    </td>
                <%
                    }
                %>
                 <td align="center">
				  <a href="javascript:list_SoftDataBoard()">
				  <img src="/images/user/homepage/list_butt1.gif" width="45" height="16" border="0">
				  </a>
				  </td>
                </tr>
              </table>
              <!----------------- 목록 버튼 끝 ---------------->
              <br>


              <table cellspacing="0" cellpadding="0" class="information_board_table_out2">
                <tr>
                  <td height="5"></td>
                </tr>
                <tr>
                  <td background="/images/user/homepage/information_dot1.gif" height="3"></td>
                </tr>
                <tr>
                  <td height="5"></td>
                </tr>
              </table>
              <br>

              <!----------------- 조회 시작 ----------------->
              <table cellpadding="0" cellspacing="0" class="tab_table">
                <tr> 
                  <td align="right">
                    <table cellpadding="0" cellspacing="0" width="39%" border="0">
                      <tr> 
                        <td> 
                            <select name="p_search" class="inputpsearch">
                                <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                                <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
                                <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
                            </select>
                        </td>
                        <td align="center"> 
                          <input name="p_searchtext" type="text" class="inputsub" value='<%=v_searchtext %>'></td>
                        <td width="100" align="right">
						<a href = "javascript:selectList();"><img src="/images/user/homepage/search_butt2.gif" width="45" height="16" border="0"></a>
                        </td>
                      </tr>
                      <tr> 
                        <td height="5"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
              <!----------------- 조회 끝 ----------------->

              <!----------------- SW자료실 리스트 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="information_board_color_line_out1">
                <tr> 
                  <td class="information_board_color_line"></td>
                </tr>
              </table>

              <table cellspacing="0" cellpadding="0" class="information_board_table_out1">
                <tr> 
                  <td> 
                    <table cellspacing="1" cellpadding="3" class="table2">
                      <tr> 
                        <td class="information_board_title_bg1" width="5%">번호</td>
                        <td class="information_board_title_bg1" width="5%">파일</td>
                        <td class="information_board_title_bg1">제 목</td>
                        <td class="information_board_title_bg1" width="13%">등록일</td>
                        <td class="information_board_title_bg1" width="7%">조회수</td>
                      </tr>
                <%
                if ( list !=null ) { 
                    for(i = 0; i < list.size(); i++) {
                        DataBox dbox2 = (DataBox)list.get(i); 
                        v_userid    = dbox2.getString("d_userid");
                        v_name      = dbox2.getString("d_name");
                        v_indate    = dbox2.getString("d_indate");
                        v_title     = dbox2.getString("d_title");
                        v_seq       = dbox2.getInt("d_seq");
                        v_cnt       = dbox2.getInt("d_cnt");
                        v_levels    = dbox2.getInt("d_levels");
                        v_upfilecnt = dbox2.getInt("d_upfilecnt");
                        v_dispnum   = dbox2.getInt("d_dispnum");
                        v_totalpage = dbox2.getInt("d_totalpagecount");
                        v_rowcount  = dbox2.getInt("d_rowcount");

                        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                        if (!v_searchtext.equals("")) {
                            if (v_search.equals("name")) {
                                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                            } else if (v_search.equals("title")) {
                                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
                            }
                        }

                        if (v_orgseq == v_seq) v_title = "<b>" + v_title + "</b>";
                        
                        //홀짝 리스트 row별 css값 변경
                        String v_class1 ="", v_class2=""; 
                        if (((i+1)%2) ==1 ){
                            v_class1 = "board_text1";
                            v_class2 = "board_text3";
                        }else{
                            v_class1 = "board_text2";
                            v_class2 = "board_text4";
                        }

                %>

                      <tr> 
                        <td class="<%=v_class1%>" ><%=v_dispnum%></td>
                        <td class="<%=v_class1%>">
							<% if (v_upfilecnt>0) {%> 
								<img src="/images/user/homepage/file_icon.gif" width="13" height="13" border="0">
							<% } %></td>
                        <td  class="<%=v_class2%>">
							<img src = "/images/admin/system/space.gif" width="<%=v_levels*15-30%>" height ="2" border =0>
							<%if (v_levels > 1) {%>
								<img src="/images/user/homepage/reply_icon1.gif"> 
							<% } %>
								<a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a>
							<% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){          %>
								&nbsp;&nbsp;<img src = "/images/user/homepage/new_icon1.gif" align = "absmiddle" border = "0">
							<%  } %>
						</td>
                        <td class="<%=v_class1%>"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
                        <td class="<%=v_class1%>"><%=v_cnt%></td>
                      </tr>
            <%
                    }
                }else {
            %>
                    <tr><td colspan="5" align=center class="board_text1">등록된 내용이 없습니다. </td></tr>
            <%  } %>
                    </table>
                  </td>
                </tr>
              </table>
              <!----------------- SW자료실 리스트 끝 ----------------->

              <!----------------- 페이지 시작 ----------------->
              <table cellspacing="0" cellpadding="0" class="tab_table">
                <tr> 
                  <td height="11"></td>
                </tr>
                <tr> 
                  <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, row) %>
                  </td>
                </tr>
              </table>
              <!----------------- 페이지 끝 ----------------->
              <br>
              <br>


            </td>
          </tr>
          <tr>
            <td height="8"><img src="/images/user/homepage/information_sub_menu_bg2.gif" width="178" height="8" border="0"></td>
            <td><img src="/images/user/homepage/sub_body_bg2.gif" width="804" height="8" border="0"></td>
          </tr>
        </table>
        <!----------------- 서브메뉴, 내용 들어가는 곳 끝 ----------------->

<!---------- copyright start ------------------->
<%//@ include file="/jsp/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->