<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    int v_tabseq          = box.getInt("p_tabseq");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    ArrayList list = (ArrayList)request.getAttribute("selectList");

    String  v_searchtext     = box.getString("p_searchtext");
    String  v_search         = box.getString("p_search");

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;

    int     v_seq       = 0;            // 일련번호
    int     v_cnt       = 0;            // 조회수

    String  v_typesnm   = "";
    String  v_title     = "";
    String  v_ldate     = "";
    String  v_codenm    = "";
    String  v_userid    = "";
    String  v_usernm    = "";
    String  v_flagYn    = "";
    String  v_position  = "";
    String  v_saveFile  = "";

%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//검색내용에 맞는 리스트 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.infomation.GalleryHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//상세화면 페이지로 이동
function viewContent(seq) {
    document.form1.action = "/servlet/controller.infomation.GalleryHomePageServlet";
    document.form1.target="_self";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.submit();
}

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.target="_self";
    document.form1.action = "/servlet/controller.infomation.GalleryHomePageServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.target="_self";
     document.form1.action = "/servlet/controller.infomation.GalleryHomePageServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}
//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" method = "post" action="">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_tabseq"         value = "<%=v_tabseq %>">
      <input type = "hidden" name = "p_userid"      value = "">

            <h2><img src="/images/portal/information/h2_tit6.gif" alt="수강생갤러리" class="fl_l" /><p class="category">Home > 정보광장 > <strong>수강생 갤러리</strong></p></h2>

            <p><img src="/images/portal/information/gallery_borimg.gif" alt="수강생갤러리에서는 수강생들의 작품을 감상하실 수 있습니다" /></p>

            <p class="searchbox mg_t30">
                <img src="/images/portal/board/serbox_gallerytit.gif" alt="갤러리검색" />
                <input type="hidden" name="p_search" value="title">
                <input name="p_searchtext" type="text" class="inbox" style="width:285px;" value="<%=v_searchtext %>" onkeypress="javascript:fnKeyPressEnter(event, 'selectList');"/>
                <input type="image" src="/images/portal/btn/btn_serbox_search.gif" class="va_m" alt="검색" onclick="selectList();"/>
            </p>

            <table width="670px" border="0" cellpadding="0" cellspacing="0">
                <tr><td><div class="info_photo_list"></div></td></tr>
            </table>

            <table width="670px" border="0" cellpadding="0" cellspacing="0" style="overflow:hidden; border-bottom:1px solid #D5D5D5;">
            <%if(list.size() != 0){
                out.print("<tr align='center'>");
                for(int i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);

                   v_seq           = dbox.getInt("d_seq");
                   v_cnt           = dbox.getInt("d_cnt");
                   v_title         = dbox.getString("d_title");
                   v_ldate         = dbox.getString("d_ldate");
                   v_userid        = dbox.getString("d_userid");
                   v_usernm        = dbox.getString("d_name");
                   v_flagYn        = dbox.getString("d_flagyn");
                   v_saveFile      = dbox.getString("d_savefile");
                   v_position      = dbox.getString("d_position");

                   v_dispnum    = dbox.getInt("d_dispnum");
                   v_totalpage  = dbox.getInt("d_totalpage");
                   v_rowcount   = dbox.getInt("d_rowcount");

                   v_saveFile  = StringUtil.replaceAll(v_saveFile, '\\', '/');

                    // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.

                    out.print("<td valign='top'>");
                        out.print("<table width='128px' border='0' cellpadding='0' cellspacing='0'>");
                            out.print("<tr><td>");
                                    out.print("<a href='javascript:viewContent("+v_seq+");'><img src='/servlet/controller.library.DownloadServlet?p_savefile="+v_saveFile+"' width='128' height='95' alt='' /></a>");
                                out.print("</td></tr><tr><td height='10px'></td></tr><tr><td>");
                                    out.print("<a href='javascript:viewContent("+v_seq+");'>"+v_title+"<br />"+v_position+"<br>"+v_usernm+"</a>");
                                out.print("</td>");
                            out.print("</tr><tr><td height='20px'></td></tr>");
                        out.print("</table>");
                    out.print("</td>");
                    if((i+1) % 4 == 0)
                        out.print("</tr><tr align='center'>");
                }
            }else{
                out.print("<td style='border:0px solid;text-align:center;padding-top:4px'>");
                out.print("등록된 게시물이 없습니다.");
                out.print("</td>");
            }
            out.print("</tr>");
            %>
            </table>
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->