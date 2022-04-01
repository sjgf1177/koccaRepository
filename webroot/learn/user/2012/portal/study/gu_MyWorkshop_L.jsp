<%
//**********************************************************
//  1. 제      목: 나의강의실 > 나의워크샵
//  2. 프로그램명 : gu_MyWorkshop_L.jsp
//  3. 개      요: 나의 워크샵
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.25
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
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
    
	int		v_seq				= 0;
	String	v_seminargubun		= "";
	String	v_seminarnm			= "";
	String	v_tname				= "";
	String	v_startdate			= "";
	String	v_enddate			= "";
	String	v_propstart			= "";
	String	v_propend			= "";
	String	v_limitmember		= "";
	String	v_code				= "";
	String	v_seminargubunnm	= "";
	String	v_pass_yn 			= "";
	int		v_cnt 				= 0;
	String	v_userid 			= "";
	String	v_indate 			= "";
	String	v_pass_yn_text		= "";
     
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList list1 = (ArrayList)request.getAttribute("MyWorkshopListPage");
      
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//워크샵 신청결과
function viewPassContent(seq) {

	window.open("about:blank", "popUpView", "top=0, left=0, width=500, height=400, status=no, resizable=no");

    document.form1.action="/servlet/controller.homepage.SeminarHomePageServlet";
    document.form1.p_process.value = "selectViewPass";
    document.form1.p_seq.value = seq;
    document.form1.target="popUpView";
    document.form1.submit();
    
    document.form1.target="_self";
}

//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.study.MyWorkshopServlet";
	document.form1.p_process.value = "MyWorkshopListPage";
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
  			<h2><img src="/images/portal/myclass/h2_tit13.gif" alt="나의워크샵" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>나의워크샵</strong></p></h2>
 
			<p><img src="/images/portal/myclass/myworkshop_txt.gif" alt="워크샵 참여 신청 목록 및 선정결과를 확인하실 수 있습니다." /></p>
			
			<table class="board_list mg_t30">
			<colgroup><col width="90px" /><col width="330px" /><col width="90px" /><col width="85px" /><col width="95px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>분류</p></th>
				<th><p>제목</p></th>
				<th><p>교육기간</p></th>
				<th><p>수강신청일</p></th>
				<th class="end"><p>결과발표</p></th>
			</tr>
			</thead>
			<tbody>
<%
        for (int i=0; i<list1.size(); i++) {
            DataBox dbox = (DataBox)list1.get(i);  
            
            v_dispnum			= dbox.getInt("d_dispnum"); 
        	v_seq				= dbox.getInt("d_seq"); 
        	v_seminargubun		= dbox.getString("d_seminargubun"); 
        	v_seminarnm			= dbox.getString("d_seminarnm"); 
        	v_tname				= dbox.getString("d_tname"); 
        	v_startdate			= dbox.getString("d_startdate"); 
        	v_enddate			= dbox.getString("d_enddate"); 
        	v_propstart			= dbox.getString("d_propstart"); 
        	v_propend			= dbox.getString("d_propend"); 
        	v_limitmember		= dbox.getString("d_limitmember"); 
        	v_code				= dbox.getString("d_code"); 
        	v_seminargubunnm	= dbox.getString("d_seminargubunnm"); 
        	v_pass_yn 			= dbox.getString("d_pass_yn"); 
        	v_cnt 				= dbox.getInt("d_cnt"); 
        	v_userid 			= dbox.getString("d_userid");
        	v_indate 			= dbox.getString("d_indate"); 
        	
        	if ("Y".equals(v_pass_yn)) {
        		v_pass_yn_text = "<a href=\"javascript:viewPassContent('" + v_seq + "')\" class=\"board_btn2\"><span>선정자확인</span></a>";
        	} else {
        		v_pass_yn_text = "미발표";
        	}
%>      
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="ta_l"><%= v_seminargubunnm %></td>
				<td class="ta_l"><%= v_seminarnm %></td>
				<td class="num ta_l"><%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~<br /><%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%></td>
				<td class="num"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
				<td><%= v_pass_yn_text %></td>
			</tr>
<%
        }
%>
<%
		if(list1.size()==0){
%>
			<tr height="25"> 
			  <td colspan="5" align="center" valign="middle">검색된 내용이 없습니다.</td>
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