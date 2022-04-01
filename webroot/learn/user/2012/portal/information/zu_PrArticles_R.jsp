<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","7");
	box.put("submenu","4");
    
    String  v_process    = box.getString("p_process");
    int     v_tabseq     = box.getInt   ("p_tabseq");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    

    int v_seq      = box.getInt("p_seq");

    String  v_date          = "";
    String  v_title         = "";
    String  v_content       = "";
    String  v_name          = "";
    String  v_flagYn        = "";
    String  v_position      = "";
    String  v_url           = "";
    int     v_cnt           =  0;
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    
    if (dbox != null) {
        v_title     = dbox.getString("d_title"); 
        v_content   = dbox.getString("d_content");    
        v_date			= FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy년 MM월 dd일");
        v_name      = dbox.getString("d_name");
        v_flagYn    = dbox.getString("d_flagyn");
        v_position  = dbox.getString("d_position");
        v_url       = dbox.getString("d_url");
        v_cnt       = dbox.getInt("d_cnt");
     }  
    
%>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
    	document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.infomation.BriefingHomePageServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform2" method="post">
	<input type = "hidden" name = "p_process" >
	<input type = "hidden" name = "p_seq" value="<%=v_seq%>">
	<input type = "hidden" name = "p_tabseq" value="<%=v_tabseq%>">
	<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
	<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
	<input type = "hidden" name = "p_search" value = "<%=v_search %>">
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
			<table class="linecolor" cellspacing="0" cellpadding="0">
				<colgroup><col width="25%"><col width="25%"><col width="25%"><col width="*"></colgroup>
				<tr>
					<td class="color1"></td>
					<td class="color2"></td>
					<td class="color3"></td>
					<td class="color4"></td>
				</tr>
			</table>
			<div>
				<h1><%=v_title%></h1>
				<h3></h3>
				<div class="detail">
					<%=StringManager.removeHTMLHead(v_content)%>
					<%
					Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
					Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
					String i_boardStyle        = "board_view";                                // 보드 스타일
					%>
					<%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
				</div>
			</div>
			<div class="prfooter"><button type="button" tabindex="200" onclick="javascript:listnotice();" class="btn_small_gray" title="과정목록으로">목록으로</button></div>
		</div>
	</div>
	<input type="submit" class="close" title="실행">
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
