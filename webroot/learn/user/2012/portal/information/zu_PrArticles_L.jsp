<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
    box.put("topmenu","7");
	box.put("submenu","4");

    ArrayList list1 = null;
    ArrayList list2 = null;

    int v_tabseq    = box.getInt("p_tabseq");
    
    String  v_process  = box.getString("p_process");

    // 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd   = box.getString("p_selDtlCd");
    
    int v_pageno    = box.getInt("p_pageno");
    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
    int v_rowcount  = 0;
    
    int    v_seq     = 0;              // 일련번호
    int    v_cnt     = 0;              // 조회수

    String  v_typesnm   = "";
    String  v_title     = "";
    String  v_indate    = "";
    String  v_content   = "";
    String  v_codenm    = "";
    String  v_userid    = "";
    String  v_usernm    = "";
    String  v_position  = "";
    String	v_savefile	= "";
    
    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    list2 = (ArrayList)request.getAttribute("selectList");
   
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--
    function go(index) {
        document.nform1.p_pageno.value = index;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //페이지 변경
    function goPage(pageNum) {
        document.nform1.p_pageno.value = pageNum;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //리스트 조회
    function select() {
        document.nform1.p_pageno.value = 0;
        document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform1.p_process.value = "selectList";
        document.nform1.submit();
    }

    //상세보기
    function selectView(seq) {
    	document.nform1.target  =   "_self";
        document.nform1.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform1.p_process.value = "selectView";
        document.nform1.p_seq.value= seq;
        document.nform1.submit();
    }

//-->
</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
  <input type = "hidden" name = "p_process">
  <input type = "hidden" name = "p_pageno"  value="<%=v_pageno%>"> 
  <input type = "hidden" name = "p_seq"    >
  <input type = "hidden" name = "p_tabseq" value="<%=v_tabseq %>" >
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>아카데미 소식</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/title_issue.gif" alt="아카데미 소식">
			</div>
		</div>
		<div class="prcenter">
			
			<table class="prcenterlist" cellpadding="0" cellspacing="0">
				<colgroup><col width="176"><col width="*"></colgroup>
				<%
				if(list2.size() != 0){
			    for( int i = 0; i < list2.size(); i++) {
				    DataBox dbox     = (DataBox)list2.get(i);
				    
				    v_seq           = dbox.getInt("d_seq");
				    v_cnt           = dbox.getInt("d_cnt");
				    v_title         = dbox.getString("d_title");
				    v_indate        = dbox.getString("d_indate");
				    v_userid        = dbox.getString("d_userid");
				    v_usernm        = dbox.getString("d_name");
				    v_content				= StringManager.formatTitle(StringManager.removeHTML(dbox.getString("d_content")), 250);
				    v_content				= v_content.replaceAll("(!/|\r|\n|&nbsp;|<br>|◆|▲|□)", "");
				    
				    v_dispnum        = dbox.getInt("d_dispnum");
				    v_totalpage      = dbox.getInt("d_totalpage");
				    v_rowcount       = dbox.getInt("d_rowcount");
				    v_position       = dbox.getString("d_position");
				    v_savefile       = StringManager.replace(dbox.getString("d_savefile"),"\\","/");
				    
				    //if (!v_searchtext.equals("")&&v_search.equals("title")) {
				    if (!v_searchtext.equals("")) {
				        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
				    } 
				%>
				<tr>
					<td><img src="<%=v_savefile%>"></td>
					<td>
						<h3><a href="javascript:selectView(<%=v_seq%>)" tabindex="<%="2"+i+"1"%>" title="<%=v_title%>"><%=v_title%></a></a></h3>
						<h5><%=v_content%></h5>
						<p><img src="/images/2012/common/icon_rearrow.gif"><a href="javascript:selectView(<%=v_seq%>)" tabindex="<%="2"+i+"2"%>" title="자세히 보기">자세히 보기</a></p>
					</td>
				</tr>
				<%
					}
		    }else{
			  %>
		    <tr> 
		    	<td colspan="2">등록된 내용이 없습니다.</td>
		    </tr>
        <% } %>
			</table>
		</div>
   
    <div class="pageset">
			<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 10, v_rowcount) %>
		</div>
	</div>
	<input type="submit" class="close" title="실행">
</form>
<!-- Form 영역 종료 -->
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>