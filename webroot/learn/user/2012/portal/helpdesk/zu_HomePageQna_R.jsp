<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","3");
    
    int    v_seq        = box.getInt("p_seq");
    int    v_pageno     = box.getInt("p_pageno");
    String v_searchtext = box.getString("p_searchtext");             
    String v_select     = box.getString("p_select");
    String v_selCategory = box.getString("p_selCategory");
    
    String s_userid     = box.getSession("userid");
    
    String v_title      = "";
    String v_contents   = "";
    String v_inuserid   = "";
    String v_indate     = "";
    String v_types      = "";
    String v_name       = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_isopen     = "";
    String v_repstatus  = "";
    int    v_cnt        = 0;
    
    DataBox dbox        = (DataBox)request.getAttribute("selectQna");
    
    if(dbox != null) {
        v_inuserid          = dbox.getString("d_inuserid");
        v_name              = dbox.getString("d_name");
        v_types             = dbox.getString("d_types");
        v_indate            = dbox.getString("d_indate");
        v_title             = dbox.getString("d_title");
        v_seq               = dbox.getInt("d_seq");
        v_cnt               = dbox.getInt("d_cnt");
        v_contents          = dbox.getString("d_contents");
        v_categorycd        = dbox.getString("d_categorycd");
        v_categorynm        = dbox.getString("d_categorynm");
        v_isopen            = dbox.getString("d_isopen");
        v_repstatus         = dbox.getString("d_okyn1");
        
    }

%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">

    //리스트페이지로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";     
        document.form1.submit();    
    }      
    
    function searchList() {
        if (form1.p_searchtext.value == ""){
            alert("검색 할 내용을 입력해 주세요");
        }else{
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
        }
    }
    
    //수정페이지로 이동
    function updatePage() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }
    
    //답변 등록 페이지로 이동
    function replyPage() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }   
    
    //꼬릿말 등록
    function insertcomment() {
        if(document.form2.commentqna.value==""){
            alert("꼬릿말을 등록하세요");
            return;
        }
        document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
        document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form2.p_process.value = "commentInsertPage";
        document.form2.submit();
    }
    
    
    //뷰 화면 삭제
    function deleteAction() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            
            document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }
    
    //꼬릿말 삭제
    function deleteComment(v_commentseq) {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form2.p_commentseq.value = v_commentseq;
            document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
            document.form2.p_process.value = "deleteComment";
            document.form2.submit();
        }
        else {
            return;
        }
    }

</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" method = "post" action="">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <%-- <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>"> --%>
    <%-- <input type = "hidden" name = "p_select"      value = "<%= v_select %>"> --%>
    <input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
    <input type = "hidden" name = "p_selCategory" value = "<%= v_selCategory %>">
    <input type = "hidden" name = "p_process"     value = "">
    <input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
    <input type = "hidden" name = "p_types"       value = "<%=v_types%>">
    
    <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고 계시는 화면의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>묻고답하기</u></td>
			</tr>
		</table>
		<div class="concept">
			<div><img src="/images/2012/sub/page4/title/qna.gif" alt="학습에 대해 궁금한 것이 있다면 무엇이든 물어보세요"></div>
			<ul class="boardsearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" tabindex="121" onfocus="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" name="p_select" id="choicetype" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제','title')" tabindex="122" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용','content')" tabindex="123" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="124" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:searchList();" tabindex="125" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="126" title="질문내용으로 이동합니다">질문글 보기</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="127" title="질문에 대한 아카데미 운영진의 답변으로 이동합니다">답변글 보기</a></dd>
		</dl>
		<br>
		<div class="viewbar" id="skip01"><h6><b><%= v_title %> </b></h6></div>
		<div class="viewcontent">
			<h6><%=v_contents.replace("\n","<BR>")%></h6>
			<%
      Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
      Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
      String i_boardStyle        = "board_view";                                // 보드 스타일
     	%>   
		  <%@ include file="/learn/user/include/zu_MultiAttach_2012_R.jsp" %>
			<table class="about" cellpadding="0" cellspacing="0" summary="게시물 정보를 확인합니다">
				<colgroup><col width="34"><col width="*"><col width="65"><col width="70"><col width="36"><col width="30"></colgroup>
				<tr>
					<th id="w_category">분류</th>
					<td name="w_category"><%=v_categorynm %></td>
					<th id="w_writingdate">작성일자</th>
					<td name="w_writingdate"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
					<th id="w_count">조회</th>
					<td name="w_writingdate"><%=v_cnt%></td>
				</tr>
			</table>
		</div>
		<% if(v_repstatus.equals("3")){ %>
			<%@ include file="/learn/user/2012/portal/helpdesk/zu_HomePageQna_A.jsp"%> 
		<% } %>
		<div class="viewfooter">
		<%if(v_inuserid.equals(box.getSession("userid")) && !v_repstatus.equals("3")){%>
			<button type="button" onclick="javascript:updatePage();" tabindex="191" class="btn_small_linegray" title="이 글을 수정합니다">수정하기</button>
			<button type="button" onclick="javascript:deleteAction();" tabindex="192" class="btn_small_linegray" title="이 글을 삭제합니다">삭제하기</button>
		<% } %>
			<button type="button" onclick="javascript:selectList();" tabindex="193" class="btn_small_lineblue" title="목록으로 돌아갑니다">목록으로</button>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
