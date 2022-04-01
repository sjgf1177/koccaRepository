<%
//**********************************************************
//  1. 제      목: 나의강의실 > 나의이벤트
//  2. 프로그램명 : gu_MyEvent_L.jsp
//  3. 개      요: 나의 이벤트
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.27
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.homepage.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    String  ss_userid     = box.getSession("userid");    
	
	int		v_seq		= 0;
	String	v_gubun		= "";
	String	v_title		= "";
	String	v_content	= "";
	String	v_isall		= "";
	String	v_strdate	= "";
	String	v_enddate	= "";
	String	v_loginyn	= "";
	String	v_useyn		= "";
	String	v_popwidth	= "";
	String	v_popheight	= "";
	String	v_popxpos 	= "";
	int		v_cnt 		= 0;
	String	v_popypos 	= "";
	String	v_popup 	= "";
	String	v_uselist	= "";
	String	v_useframe	= "";
	String	v_userid	= "";
	String	v_indate	= "";
	String	v_winneryn	= "";
	String	v_isanswer	= "";
	String	v_winneryn_text	= "";

    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList list1 = (ArrayList)request.getAttribute("MyEventListPage");
      
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//워크샵 신청결과
function viewPassContent(seq) {

	window.open("about:blank", "popUpView", "top=0, left=0, width=500, height=400, status=no, resizable=no");

    document.form1.action="/servlet/controller.homepage.EventHomePageServlet";
    document.form1.p_process.value = "selectViewPass";
    document.form1.p_seq.value = seq;
    document.form1.target="popUpView";
    document.form1.submit();
    
    document.form1.target="_self";
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyEventServlet";
	document.form1.p_process.value = "MyEventListPage";
	document.form1.submit();

}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="/servlet/controller.study.MyQnaServlet">
  <input type = "hidden" name = "p_process"		value = "">
  <input type = "hidden" name = "p_pageno"		value = "<%= v_pageno %>">
  <input type = "hidden" name = "p_seq"			value = "">
  <input type = "hidden" name = "p_userid"		value = "">
			<h2><img src="/images/portal/myclass/h2_tit12.gif" alt="나의이벤트" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의이벤트</strong></p></h2>
 
			<p><img src="/images/portal/myclass/myevent_txt.gif" alt="이벤트 참여 신청 목록 및 선정결과를 확인하실 수 있습니다." /></p>
			
			<table class="board_list mg_t30">
			<colgroup><col width="90px" /><col width="350px" /><col width="155px" /><col width="95px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>제목</p></th>
				<th><p>이벤트기간</p></th>
				<th class="end"><p>결과발표</p></th>
			</tr>
			</thead>
			<tbody>
<%
        for (int i=0; i<list1.size(); i++) {
            DataBox dbox = (DataBox)list1.get(i);  

            v_dispnum	= dbox.getInt("d_dispnum"); 
        	v_seq		= dbox.getInt("d_seq"); 
        	v_gubun		= dbox.getString("d_gubun"); 
        	v_title		= dbox.getString("d_title"); 
        	v_content	= dbox.getString("d_content"); 
        	v_isall		= dbox.getString("d_isall"); 
        	v_strdate	= dbox.getString("d_strdate"); 
        	v_enddate	= dbox.getString("d_enddate"); 
        	v_loginyn	= dbox.getString("d_loginyn"); 
        	v_useyn		= dbox.getString("d_useyn"); 
        	v_popwidth	= dbox.getString("d_popwidth"); 
        	v_popheight	= dbox.getString("d_popheight"); 
        	v_popxpos 	= dbox.getString("d_popxpos"); 
        	v_cnt 		= dbox.getInt("d_cnt"); 
        	v_popypos 	= dbox.getString("d_popypos");
        	v_popup 	= dbox.getString("d_popup"); 
        	v_uselist 	= dbox.getString("d_uselist"); 
        	v_useframe 	= dbox.getString("d_useframe"); 
        	v_userid 	= dbox.getString("d_userid"); 
        	v_indate 	= dbox.getString("d_indate"); 
        	v_winneryn 	= dbox.getString("d_winneryn"); 
        	v_isanswer 	= dbox.getString("d_isanswer"); 
        	v_winneryn_text = dbox.getString("d_winneryn_text"); 
        	
        	if ("Y".equals(v_winneryn)) {
        		v_winneryn_text = "<a href=\"javascript:viewPassContent('" + v_seq + "')\" class=\"board_btn2\"><span>당첨자확인</span></a>";
        	} else {
        		v_winneryn_text = "미발표";
        	}
%>
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= v_dispnum %></td>
				<td class="ta_l"><%= v_title %></td>
				<td class="num"><%=FormatDate.getFormatDate(v_strdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%></td>
				<td><%= v_winneryn_text %></td>
			</tr>
<%
        }
%>
<%
		if(list1.size()==0){
%>
			<tr height="25"> 
			  <td colspan="4" align="center" valign="middle">검색된 내용이 없습니다.</td>
			</tr>
<%
		}
%>
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