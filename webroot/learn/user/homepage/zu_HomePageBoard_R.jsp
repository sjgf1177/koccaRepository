<%
//**********************************************************
//  1. 제      목: 자료실
//  2. 프로그램명 : zu_HomePageBoard_R.jsp
//  3. 개      요: 자료실 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 이연정
//  7. 수      정: 2005.07.01 이연정
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

    // 리스트
    ArrayList list = (ArrayList)request.getAttribute("selectHomePageList");
box.put("leftmenu","04");                       // 메뉴 아이디 세팅

/*if (v_search.equals("title") ) {
	v_title    = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
} else if (v_search.equals("content")){
	v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
}else if (v_search.equals("name")){
	v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
}*/
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//답변페이지로 이동
function replyPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "replyPage";
    document.form1.submit();
}

//내용 삭제
function delete_HomePageBoard() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form1.p_userid.value = "<%= v_userid %>";
        document.form1.p_pageno.value = "1";
        document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
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
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//수정 페이지로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//-->
</script>
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; Q&amp;A</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> <table width="675" border="0" cellspacing="0" cellpadding="0">

                    <tr> 
                      <td colspan="4"><img src="/images/user/support/search_line.gif"></td>
                    </tr>
                    <tr> 
                      <td height="5" colspan="4"></td>
                    </tr>
                  </table>
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
				<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
				<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
				<input type = "hidden" name = "p_search"     value = "<%=v_search %>">
				<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
				<input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
				<input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
				<input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">
				<input type = "hidden" name = "p_process" value = "">
          <tr> 
            <td colspan="5"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td width="94"><img src="/images/user/support/text_title.gif"></td>
            <td colspan="4" class="tbl_btit"><%= v_title %></td>
          </tr>
          <tr> 
            <td colspan="5"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/support/text_pe.gif"></td>
            <td width="285" class="tbl_gleft"><%=v_name%><!--(<%=v_userid%>)--></td>
            <td width="94"><img src="/images/user/support/text_day.gif"></td>
            <td width="200" class="tbl_gleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
            <td width="1">&nbsp;</td>
          </tr>
          <tr> 
            <td colspan="5"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td width="94" valign="top" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_top_head.gif" width="94" height="9"></td>
            <td colspan="4" rowspan="3" class="tbl_bdata"> <%  if(box.getBoolean("p_isUpload") ) {    
							for(i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
							String v_realfile = (String)v_realfileVector.elementAt(i);
							String v_savefile = (String)v_savefileVector.elementAt(i);                        
							if(v_realfile != null && !v_realfile.equals("")) {  %> <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'> 
              <%= v_realfile%></a><br> <input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>> 
              <%		}   
							    }   
							}
						%> </td>
          </tr>
          <tr>
            <td align="center" valign="top" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_addfile_a.gif"></td>
          </tr>
          <tr>
            <td valign="bottom" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_bo_head.gif"></td>
          </tr>
          <tr> 
            <td colspan="5"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td colspan="5" class="tbl_contents"><%=v_content%></td>
          </tr>
          <tr bgcolor="B8D5E5"> 
            <td height="5" colspan="5"><img src="/images/user/support/sup_tb_bo.gif" width="675" height="5"></td>
          </tr>
          <tr> 
            <td height="10" colspan="5"></td>
          </tr>
        </table>
				  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="418" align="right">&nbsp; </td>
                      <td width="257" align="right">
					  <!--<a href="javascript:replyPage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_answer.gif" border="0"></a> -->
					  <%if(v_userid.equals(box.getSession("userid"))){%>
                        <a href="javascript:updatePage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_mod.gif" border="0"></a> 
                        <a href="javascript:delete_HomePageBoard()"><img src="/images/user/button/btn_del.gif" border="0"></a> 
                      <%}%>
                        <a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_list.gif" border="0"></a></td>
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