<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","4");
	box.put("submenu","4");
    
    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
    String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_selClsfCd   = box.getString("p_selClsfCd");
    String  v_selDtlCd    = box.getString("p_selDtlCd");

    int v_seq      = box.getInt("p_seq");

    int v_cnt               = 0;
    String v_gubun          = "";
    String v_clsfcd         = "";
    String v_dtlcd          = "";
    String v_clsfnm         = "";
    String v_dtlnm          = "";
    String v_title          = "";
    String v_content        = "";
    String v_professor      = "";
    String v_pictureurl     = "";
    String v_useyn          = "";
    String v_indate         = "";
    String v_usernm         = "";


    DataBox dbox = (DataBox)request.getAttribute("selectView");

    if (dbox != null) {
    	v_gubun          = dbox.getString("d_gubun");  
        v_clsfcd         = dbox.getString("d_clsfcd");      
        v_dtlcd          = dbox.getString("d_dtlcd");       
        v_clsfnm         = dbox.getString("d_clsfnm");      
        v_dtlnm          = dbox.getString("d_dtlnm");
        v_title          = dbox.getString("d_title");       
        v_content        = dbox.getString("d_content");     
        v_professor      = dbox.getString("d_professor");   
        v_pictureurl     = dbox.getString("d_pictureurl"); 
        v_indate         = dbox.getString("d_indate");
        v_usernm         = dbox.getString("d_name");
        v_userid         = dbox.getString("d_userid");
        v_useyn          = dbox.getString("d_useyn");
        v_cnt            = dbox.getInt   ("d_cnt");  

     }
%>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">

    function listnotice(){
    	document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.infomation.SpecialCourseHomePageServlet";
        document.nform2.submit();
    }

</SCRIPT>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform2" method="post">
<input type = "hidden" name = "p_process" >
<input type = "hidden" name = "p_seq" value="<%=v_seq%>">
<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
<input type = "hidden" name = "p_search" value = "<%=v_search %>">
<input type = "hidden" name = "p_selClsfCd" value = "<%=v_selClsfCd %>">
<input type = "hidden" name = "p_selDtlCd" value = "<%=v_selDtlCd %>">

<%
		String full_toggle = "off";
		String sub1_toggle = "off";
		String sub2_toggle = "off";
		String sub3_toggle = "off";				
		
		if ("SC2".equals(v_selClsfCd))        { sub1_toggle = "on"; 
		} else if ("SC".equals(v_selClsfCd)) { sub2_toggle = "on"; 				
		} else if ("SC1".equals(v_selClsfCd)) { sub3_toggle = "on"; 					
		} else { full_toggle = "on"; 				
		}
%>
		

	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 오프라인과정 > <u>교육자료실</u></td>
			</tr>
		</table>
		<div class="concept c02">
			<div><img src="/images/2012/sub/page3/title/offline_pds.gif" alt="교육자료실"></div>
			<ul class="factorysearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
						<dd onclick="searchtype('작성자')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">작성자</dd>
						<dd onclick="searchtype('분류')" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">분류</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" tabindex="11-1" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><input type="image" src="/images/2012/sub/page2/btn_course_search.gif" tabindex="11-2" title="검색을 시작합니다" alt="과정검색"></li>
			</ul>
			<ul class="pagetab">
				<li><a href="/servlet/controller.infomation.SpecialCourseHomePageServlet?p_process=selectList&&p_selClsfCd=ALL" tabindex="12-1" title="교육자료실의 전체 목록을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/total_<%=full_toggle%>.png" alt="전체"></a></li>
				<li><a href="/servlet/controller.infomation.SpecialCourseHomePageServlet?p_process=selectList&p_selClsfCd=SC2" tabindex="12-2" title="방송이론 관련 자료만을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/casttheory_<%=sub1_toggle%>.png" alt="방송영상"></a></li>
				<li><a href="/servlet/controller.infomation.SpecialCourseHomePageServlet?p_process=selectList&p_selClsfCd=SC" tabindex="12-3" title="방송특강 관련 자료만을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/castsp_<%=sub2_toggle%>.png" alt="게임개발"></a></li>
				<li><a href="/servlet/controller.infomation.SpecialCourseHomePageServlet?p_process=selectList&p_selClsfCd=SC1" tabindex="12-4" title="방송제작 관련 자료만을 확인합니다"><img src="/images/2012/sub/page3/tabmenu/castmaking_<%=sub3_toggle%>.png" alt="문화콘텐츠"></a></li>
			
			</ul>
		</div>
		<div class="viewbar"><h6><b><%=v_title%></b></h6></div>
		<div class="viewcontent">
			<h6><%=v_content%></h6>
			<!-- <ul class="fileload">
				
				<li><img src="/images/2012/common/icon/icon_hwp.gif" id="exticon" align="absmiddle" alt="한글문서"> <a href="javascript:filetypecheck('홍길동이 말하는 파이널컷 프로.xls');">홍길동이 말하는 파이널컷 프로.doc</a></li>
				
				<img src="/images/2012/common/icon/icon_doc.gif" align="absmiddle" alt="워드문서">
				<img src="/images/2012/common/icon/icon_ppt.gif" align="absmiddle" alt="프레젠테이션">
				<img src="/images/2012/common/icon/icon_xls.gif" align="absmiddle" alt="엑셀시트">
				<img src="/images/2012/common/icon/icon_idk.gif" align="absmiddle" alt="알 수 없는 파일형식">
				
			</ul> -->
			<table class="about" cellpadding="0" cellspacing="0">
				<colgroup><col width="60"><col width="*"><col width="50"><col width="70"><col width="65"><col width="60"></colgroup>
				<tr>
					<th>세부분류</th>
					<td><%=v_clsfnm %></td>
					<th>작성자</th>
					<td><%=v_professor%></td>
					<th>작성일자</th>
					<td><%=v_indate%></td>
				</tr>
			</table>
		</div>
		<div class="viewfooter">
			<button type="button" onclick="javascript:listnotice();" tabindex="20" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
