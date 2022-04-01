<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_faq_type   = box.getStringDefault("p_faq_type","DIRECT");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
	
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));


    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList viewfaq    =  new ArrayList();
    ArrayList viewfaqfile=  new ArrayList();
    
    
    int     v_faqno    = 0;
    int     v_rowseq   = 0;
    String  v_title     = "";
    String  v_name      = "";
    String  v_register_dte = "";
    int     i_datediff  = 0;
    int     v_cnt       = 0;
    int     v_file_cnt  = 0;
    
    String  v_new_state = "";
    
 

%>  

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//페이지이동
function goPage(pageNum) {
 document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//조회
function uf_searchOK() {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = 1;
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//새로고침
function uf_refreshOK() {
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//보기
function uf_viewOK(faqno,rowseq) {
    document.form1.p_faqno.value = faqno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityDirectServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_faq_type"    value="<%=v_faq_type%>">
<input type = "hidden" name = "p_pageno"      value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_faqno"       value = "">
<input type = "hidden" name = "p_rowseq"       value = "">


            <h2><img src="/images/portal/community/h2_tit1.gif" alt="공지사항" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>공지사항</strong></p></h2>

            <p><img src="/images/portal/community/notic_borimg.gif" alt="공지사항" /></p>

            <p class="board_serach">
                <select name="p_select">
                    <option value="title"   <%if("title".equals(v_select)) out.print(" selected");%>>제목</option>
                    <option value="content" <%if("content".equals(v_select)) out.print(" selected");%>>내용</option>
                    <option value="name"    <%if("name".equals(v_select)) out.print(" selected");%>>글쓴이</option>
                </select>
                <input type="text" name="p_searchtext" class="inbox" style="width:135px;" value="<%=v_searchtext%>"  onkeypress="javascript:fnKeyPressEnter(event, 'uf_searchOK');"/>
                <input type="image" class="btn" src="/images/portal/btn/btn_serbox_search.gif" alt="검색" onclick="uf_searchOK()"/>
            </p>

            <table class="board_list">
            <colgroup><col width="63px" /><col width="300px" /><col width="80px" /><col width="106px" /><col width="60px" /><col width="62px" /></colgroup>
            <thead>
            <tr class="gray">
                <th><p>번호</p></th>
                <th><p>제목</p></th>
                <th><p>작성자</p></th>
                <th><p>작성일자</p></th>
                <th><p>첨부</p></th>
                <th class="end"><p>조회수</p></th>
            </tr>
            </thead>
            <tbody>
			<%  
			if(list != null || list.size() != 0){
			    for(int i = 0; i < list.size(); i++) {
			        DataBox dbox = (DataBox)list.get(i);
			        
			        v_faqno      = dbox.getInt("d_faqno");
			        v_rowseq     = dbox.getInt("d_rowseq");
			        v_title      = dbox.getString("d_title");
			        v_name       = dbox.getString("d_name");
			        i_datediff   = FormatDate.datediff("date",FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyyMMdd"), FormatDate.getDate("yyyyMMdd"));
			        v_cnt        = dbox.getInt("d_read_cnt");
			        v_file_cnt   = dbox.getInt("d_file_cnt");
			        v_register_dte = FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy.MM.dd");
			        
			        v_totalpage  = dbox.getInt("d_totalpage");
			        v_dispnum    = dbox.getInt("d_dispnum");
			        
			        if(i_datediff<4){
			          v_new_state="<img src='/images/portal/ico/ico_board_n.gif' alt='n'>";
			        }
			
			        if (!v_searchtext.equals("")&&v_select.equals("title")) {
			            v_title = StringManager.replace(dbox.getString("d_title"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			        } else if(!v_searchtext.equals("")&&v_select.equals("name")){
			            v_name = StringManager.replace(dbox.getString("d_name"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			        }
			%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
                <td class="num"><%=v_dispnum %></td>
                <td class="ta_l"><a href="javascript:uf_viewOK(<%=v_faqno%>,<%=v_rowseq%>);"><%=v_title %> <%=v_new_state %></a></td>
                <td><%=v_name %></td>
                <td class="num"><%=v_register_dte%></td>
                <td><%=v_file_cnt > 0 ? "<img src=\"/images/portal/ico/ico_file.gif\" />" : "-"%></td>
                <td class="num"><%=v_cnt %></td>
            </tr>
			<%    }
			    } else {%>
            <tr> 
                <td colspan=6>등록 된 공지사항이 없습니다.</td>
            </tr>
            <% }%>
            </tbody>
            </table>
                                   
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->