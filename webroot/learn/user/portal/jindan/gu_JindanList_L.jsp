<%
//**********************************************************
//  1. 제      목: 나의강의실> 학습진단
//  2. 프로그램명 : gu_JindanList_L.jsp
//  3. 개      요: 학습진단
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

    String  v_upperclass	= "";
    String  v_middleclass	= "";
    String  v_middleclassnm	= "";
    String  v_lowerclass	= ""; 
    String  v_lowerclassnm  = "";
    String  v_isjindan		= "";
     
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0, v_tabseq = 0;
    
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
        
    ArrayList list1 = (ArrayList)request.getAttribute("JindanListPage");
      
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//페이지 이동
function goPage(pageNum) {

	document.form1.p_pageno.value = pageNum;
	document.form1.action = "/servlet/controller.jindan.JindanUserServlet";
	document.form1.p_process.value = "JindanListPage";
	document.form1.submit();

}

//진단문제평가
function testGo(classname, class1, class2, class3, chk){
	if(chk == "Y"){
			var ok = confirm( classname+"은(는) 이미 응시하신 내역이 있습니다. \n재응시할경우 기존의 데이타는 없어집니다. \n다시 응시하시겠습니까?"); 
	}else{
			var ok = confirm( classname+"의 진단테스트를 시작하시겠습니까?"); 
	}

	if(ok){
		window.self.name = "winListPage";
		window.open("", "selfTest", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 700, height = 680, top=0, left=0");
		document.form1.class1.value = class1;
		document.form1.class2.value = class2;
		document.form1.class3.value = class3;
		document.form1.classname.value = classname;
		document.form1.target = "selfTest"
		document.form1.p_process.value = "JindanUserPaperListPage";
		document.form1.action = "/servlet/controller.jindan.JindanUserServlet";
		document.form1.submit();

		document.form1.target = window.self.name;

	}else{
		return;
	}
	
}

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="form1" method="post" action="/servlet/controller.jindan.JindanUserServlet">
  <input type="hidden" name="p_process"	value = "">
  <input type="hidden" name="p_pageno"	value = "<%= v_pageno %>">
  <input type="hidden" name="class1"	value = "">
  <input type="hidden" name="class2"	value = "">
  <input type="hidden" name="class3"	value = "">
  <input type="hidden" name="classname"	value = "">
  
			<h2><img src="/images/portal/myclass/h2_tit10.gif" alt="학습진단" class="fl_l" /><p class="category">Home > 나의강의실 > <strong>학습진단</strong></p></h2>
 
			<p><img src="/images/portal/myclass/examine_txt.gif" alt="진단 분야 및 난이도에 따른 학습진단 테스트를 할 수 있는 공간입니다." /></p>
						
			<ul class="tabwrap mg_t30">
				<li><a class="tab_myclass tab_myclass_on"><span>진단하기</span></a></li>
				<li><a href="/servlet/controller.jindan.JindanUserServlet?p_process=JindanHistoryList" class="tab_myclass"><span>진단이력보기</span></a></li>
			</ul>
			
			<table class="board_list">
			<colgroup><col width="53px" /><col width="197px" /><col width="80px" /><col width="360px" /></colgroup>
			<thead>
			<tr class="myclass">
				<th><p>번호</p></th>
				<th><p>진단분야</p></th>
				<th><p>난이도</p></th>
				<th class="end"><p class="examine">진단하기</p></th>
			</tr>
			</thead>
			<tbody>
<%
        for (int i=0; i<list1.size(); i++) {
            DataBox dbox = (DataBox)list1.get(i);  
            v_dispnum		= dbox.getInt("d_dispnum");
            v_upperclass	= dbox.getString("d_upperclass");
            v_middleclass	= dbox.getString("d_middleclass");
            v_middleclassnm	= dbox.getString("d_middleclassnm");
            v_lowerclass	= dbox.getString("d_lowerclass");
            v_lowerclassnm	= dbox.getString("d_lowerclassnm");
            v_isjindan		= dbox.getString("d_isjindan");
            v_totalpage		= dbox.getInt("d_totalpage");
%>      
			<tr onmouseover="className='over'" onmouseout="className=' '">
				<td class="num"><%= v_dispnum %></td>
				<td><%= v_middleclassnm %></td>
				<td><%= v_lowerclassnm %></td>
				<td class="ta_l examine"><a href="javascript:testGo('<%= v_middleclassnm %> <%= v_lowerclassnm %>','<%= v_upperclass %>','<%= v_middleclass %>','<%= v_lowerclass %>','<%= v_isjindan %>')" class="board_btn2"><span>진단하기</span></a></td>
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