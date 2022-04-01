<%@ page contentType = "text/html;charset=euc-kr" %>
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
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_menuno     = box.getString("p_menuno");    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
	//
	String  v_board_fg	 = box.getStringDefault("p_brd_fg","0");

    int v_dispnum    = 0;
    int v_totalpage  = 0;
    int v_rowcount   = 1;
    int v_upfilecnt  = 0;
    int v_pageno             = box.getInt("p_pageno");
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    
    String  v_title       = "";
    String  v_name        = "";  
    String  v_new_state   = "";  
    String  v_replyTitle  = "";  
    String  v_secret      = "";  
    String  v_register_dte  = "";  
    String  v_savefile   = "";

    int     i_datediff    = 0;  
    int     v_brdno       = 0;
    int     v_rowseq      = 0;
    int     v_file_cnt    = 0;
    int     v_read_cnt    = 0;
    int     v_lv          = 0;



    ArrayList list       = (ArrayList)request.getAttribute("list");

%>
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//등록페이지로이동
function uf_moveInsertPage() {
	var v_board_fg = document.form1.p_brd_fg.value;

	if("0" == (v_board_fg)){
		//alert('공지사항 등록하러 갑니다 '+ v_board_fg +' : v_board_fg 값');
	}else if ("3" == (v_board_fg)){
		//alert('앨범 등록하러 갑니다 '+ v_board_fg +' : v_board_fg 값');
	}else{
		//alert('게시판/ 자료실 등록하러 갑니다 '+ v_board_fg +' : v_board_fg 값');
	}
	
	document.form1.p_brd_fg.value = v_board_fg;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}


//페이지이동
function goPage(pageNum) {
    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//조회
function uf_searchOK() {
    if(document.form1.p_searchtext.value ==''){
       alert('검색어를 입력하여야합니다.');document.form1.p_searchtext.focus();return;
    }

    document.form1.p_pageno.value = 1;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//새로고침
function uf_refreshOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//보기페이지로이동
function uf_viewOK(brdno,rowseq) {
	
    document.form1.p_brdno.value = brdno;
    document.form1.p_rowseq.value = rowseq;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "viewPage";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"      value="">
<input type = "hidden" name = "p_cmuno"        value="<%=v_cmuno%>">
<input type = "hidden" name = "p_menuno"       value="<%=v_menuno%>">
<input type = "hidden" name = "p_pageno"       value = "">
<input type = "hidden" name = "p_userid"       value = "">
<input type = "hidden" name = "p_upfilecnt"    value = "">
<input type = "hidden" name = "p_brdno"        value = "">
<input type = "hidden" name = "p_rowseq"       value = "">
<input type = "hidden" name = "p_brd_fg"       value = "<%=v_board_fg%>">

            <h1 class="list">
                <img src="/images/portal/community/tit_04.gif" alt="앨범" />
                <p><select name="p_select">
                <option value="title"   <%if("title".equals(v_select)) out.print(" selected");%>>제목</option>
                <option value="content" <%if("content".equals(v_select)) out.print(" selected");%>>내용</option>
                <option value="name"    <%if("name".equals(v_select)) out.print(" selected");%>>글쓴이</option>
                </select><input type="text" name="p_searchtext" class="inbox" style="width:144px;" onkeypress="javascript:fnKeyPressEnter(event, 'uf_searchOK');"/><a href="javascript:uf_searchOK();"><img src="/images/portal/btn/btn_serbox_search.gif" alt="검색" /></a></p>
            </h1>

            <div class="gallery_warp">
			<%  
			if(list.size() != 0){
			    for(int i = 0; i < list.size(); i++) {
			        DataBox dbox = (DataBox)list.get(i);
			        
			        v_title       = dbox.getString("d_title");
		        	v_name        = dbox.getString("d_name");
		        	v_new_state   = dbox.getString("d_new_state");
		        	v_replyTitle  = dbox.getString("d_replyTitle");
		        	v_secret      = dbox.getString("d_secret");
		        	v_register_dte= dbox.getString("d_register_dte");
		        	v_savefile    = dbox.getString("d_savefile");
                                    
		        	v_brdno       = dbox.getInt("d_brdno");
		        	v_rowseq      = dbox.getInt("d_rowseq");
		        	v_file_cnt    = dbox.getInt("d_file_cnt");
		        	v_read_cnt    = dbox.getInt("d_read_cnt");
		        	v_lv          = dbox.getInt("d_lv");
			        
			        v_totalpage = dbox.getInt("d_totalpage");
			        v_dispnum   = dbox.getInt("d_dispnum");

			        if (!v_searchtext.equals("")&&v_select.equals("title")) {
			            v_title = StringManager.replace(dbox.getString("d_title"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			        } else if(!v_searchtext.equals("")&&v_select.equals("name")){
			            v_name = StringManager.replace(dbox.getString("d_name"), v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
			        }
			        
			        i_datediff= FormatDate.datediff("date",FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyyMMdd"), FormatDate.getDate("yyyyMMdd"));
			
			        if(i_datediff < 4){
			          v_new_state="<img src='/images/portal/ico/ico_board_n.gif' align=absbottom>";
			        }
			        
			        if(dbox.getInt("d_lv") > 1){
				        for(int j=1;j < dbox.getInt("d_lv");j++){
				        	v_replyTitle +="&nbsp;&nbsp;";
				        }
				        v_replyTitle += "<img src=\"/images/portal/community/ico_re.gif\" align=\"absbottom\">";
			        }
			        
			        if(dbox.getString("d_display_fg").equals("N")){
			        	v_secret    = "<img src=\"/images/portal/ico/ico_board_s.gif\" />";			                	
			        }
			        v_title = StringManager.formatTitle(v_title, 16);
			
			%>
			    <dl class="gallery_list<%=i!= 0 && (i+1) % 4 == 0 ? "  mg_rn" : "" %>">
                    <dt><a href="javascript:uf_viewOK(<%=v_brdno%>,<%=v_rowseq%>);"><img src="/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile %>" width="125" height="110" alt="" /></a></dt>
                    <dd class="tit"><a href="javascript:uf_viewOK(<%=v_brdno%>,<%=v_rowseq%>);"><%=v_title %></a></dd>
                    <dd class="con">작성자 : <%=v_name%></dd>
                    <dd class="data"><%=FormatDate.getFormatDate(v_register_dte, "yyyy.MM.dd")%> <span class="bar">|</span> 조회 <%=v_read_cnt%></dd>
                </dl>
            <%
			    }
			} else {
            %>
			<dl> 
			 <dd><br/>등록 된 게시물이 없습니다.</dd>  
			</dl>
            <% }%>
            </div>
            
            <p class="list_btn"><a href="javascript:uf_moveInsertPage();" class="btn_gr"><span>등록</span></a></p>
            
        <!-- 페이징 영역 종료 -->
         <%= PageUtil.printPageSizeListDivNoPagesize(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
        <!-- 페이징 영역 종료 -->
            
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
