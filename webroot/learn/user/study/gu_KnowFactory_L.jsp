<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명 : gu_KnowBoard_L.jsp
//  3. 개      요: 지식공유 최근지식 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
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
<%@ page import = "com.dunet.common.util.*" %>



<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_process= box.getString("p_process");
    // 페이징관련
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_select      = box.getString("p_select");

    int v_seq = 0, v_readcnt = 0,  i = 0,  v_cnt = 0;
    String  v_inuserid    = "", v_position = "", v_indate = "", v_title = "", tLink = "",  v_types = "" ,v_name="";
    String  v_categorycd     = box.getString("p_categorycd");
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String v_classname1      = "";
    String v_classname2      = "";
    String v_repstatus  	 = "";
    String v_subjnm		  	 = "";
    
    String tabimg1 ="tab01.gif";
    String tabimg2 ="tab02.gif";
    String tabimg3 ="tab03.gif";
    String tabimg4 ="tab04.gif";
    String tabimg5 ="tab05.gif";

    ArrayList list = (ArrayList)request.getAttribute("selectLastList");
    

    if (v_categorycd.equals("CB")){
    	tabimg2 ="tab02over.gif";
    } else if (v_categorycd.equals("CG")){
    	tabimg3 ="tab03over.gif";
    } else if (v_categorycd.equals("CK")){
    	tabimg4 ="tab04over.gif";
    }else if (v_categorycd.equals("MINE")){
    	tabimg5 ="tab05over.gif";
    } else {
    	tabimg1 ="tab01over.gif";
    }

   
%>

<script language="JavaScript" type="text/JavaScript">
<!--
//입력페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "InsertPage";
    //document.form1.p_pageno.value = "<%= v_pageno %>";
    document.form1.submit();
}

//상세화면 페이지로 이동
function select(seq, upfilecnt, userid, types) {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value   = "selectView";
    document.form1.p_seq.value       = seq;
    document.form1.p_userid.value    = userid;
    document.form1.p_types.value     = types;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.submit();
}


//검색내용에 맞는 리스트 페이지로 이동
function selectList(tab) {
    if (form1.p_searchtext.value == ""){
        alert("검색 할 내용을 입력해 주세요");
    }else{
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.p_pageno.value = "1";
    document.form1.submit();
    showTab(tab);
    }
}

//페이지 이동
function goPage(pageNum) {

    document.form1.p_pageno.value = pageNum;
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.submit();

}


// Tab 선택시 동작
function showTab(clickTab)
{
   //alert(clickTab);
    g_showTab(clickTab, 2);
}

function g_showTab(tabNo, count)
{

    var clickTabNo = new Number(tabNo);
    var tabCount   = new Number(count);

    if( clickTabNo == 0 || tabCount == 0 ) return;

    for(i=1; i<=tabCount; i++)
    {
        div = document.all["tab_"+i];

        if(!div) return;

        if( clickTabNo == i ) {
            // 활성화된 tab 클릭시 return
            if(div.style.display == "") return;
            div.style.display = ""
        }
        else {
            // 선택되지 않는 탭들은 깜빡임 방지.
            if(div.style.display != "none") {
                div.style.display = "none";
            }
        }
    }
}
//-->
</script>
<%@ include file="/learn/user/portal/include/topfactory.jsp"%>
	<!-- 상단 -->
	<div id="container">
		<div class="contentL">
			<!-- 좌측 콘텐츠 시작 -->
			<div><img src="/images/factory/show.gif"></div>
			<ul class="tabmenu">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><img src="/images/factory/<%=tabimg1%>" id="tab01" alt="종합"></a></li>
				<!-- onclick="postchg('1');  -->
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CB"><img src="/images/factory/<%=tabimg2%>" id="tab02"  alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CG"><img src="/images/factory/<%=tabimg3%>" id="tab03"  alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CK"><img src="/images/factory/<%=tabimg4%>" id="tab04"  alt="문화컨텐츠"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE"><img src="/images/factory/<%=tabimg5%>" id="tab05"  alt="내가 작성한 글"></a></li>
			</ul>
			<form name = "form1" method = "post">
			<table class="insearch" cellpadding="0" cellspacing="0">
				<colgroup><col width="*"><col width="60"><col width="130"><col width="47"></colgroup>
				<tr>
					<td class="alignL">&nbsp;<!-- 총 2456건 &nbsp;<b>1</b>/567 페이지 --></td>
					<td>
						<select tabindex="21" name="p_select">
							<option value="title">제목</option>
							<option value="content">내용</option>							
							<option value="name">작성자</option>							
						</select>
					</td>
					<td><input name="p_searchtext"  type="text" tabindex="22"></td>
					<td><a href="javascript:selectList()"><img src="/images/factory/btn_search.gif" tabindex="23" alt="검색"></a></td>
				</tr>
			</table>
			<div class="listform">
				<table class="inlist" cellpadding="0" cellspacing="0">
					<colgroup><col width="50"><col width="30"><col width="*"><col width="70"><col width="70"><col width="50"><col width="50"></colgroup>
					<tr>
						<th>번호</th>
						<th colspan="2">제목</th>
						<th>과정명</th>
						<th>등록일</th>
						<th>조회</th>
						<th>추천</th>
					</tr>

          <!-- 게시판 시작 -->
          
          <input type = "hidden" name = "p_process"     value = "<%=v_process%>">
          <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
          <input type = "hidden" name = "p_seq"         value = "">
          <input type = "hidden" name = "p_userid"      value = "">
          <input type = "hidden" name = "p_upfilecnt"   value = "">
          <input type = "hidden" name = "p_types"       value = "">
          <input type = "hidden" name = "p_categorycd"  value = "<%=v_categorycd%>">
          </form>
<%
        if(list.size() != 0){
            for(i = 0; i < list.size(); i++) {
                   DataBox dbox = (DataBox)list.get(i);
                   v_dispnum    = dbox.getInt   ("d_dispnum");
                   v_inuserid   = dbox.getString("d_inuserid");
                   v_name       = dbox.getString("d_name");
                   v_cnt        = dbox.getInt   ("d_cnt");
                   v_indate     = dbox.getString("d_indate");
                   v_title      = dbox.getString("d_title");
                   v_seq        = dbox.getInt   ("d_seq");
                   v_types      = dbox.getString("d_types");
                   v_readcnt    = dbox.getInt   ("d_recommend");
                   v_upfilecnt  = dbox.getInt   ("d_filecnt");
                   v_totalpage  = dbox.getInt   ("d_totalpage");
                   v_rowcount   = dbox.getInt   ("d_rowcount");
                   v_repstatus  = dbox.getString("d_okyn1");
                   v_subjnm  	= dbox.getString("d_subjnm");

                    // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
                    if (!v_searchtext.equals("")&&v_select.equals("title")) {
                        v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                    } else if(!v_searchtext.equals("")&&v_select.equals("name")){
                        v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>");
                    }

%>
					<tr>
						<td><%=v_dispnum%></td>
						<td>
						<%if(v_repstatus.equals("3")){ %>
						<img src="/images/factory/icon_answer.gif" alt="답변완료">						
						<%} else if(v_repstatus.equals("4")){ %>
						<img src="/images/factory/icon_top.gif" alt="게시글">
						<%}else{ %>
						<img src="/images/factory/icon_wait.gif" alt="대기중">
						<%} %>
						</td>
						<td class="alignL indent5"> <a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_inuserid%>','<%=v_types%>')"><%=v_title%></a></td>
						<td><img src="/images/factory/icon_coursename.gif" alt="<%=v_subjnm %>"></td>
						<td><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
						<td><%=v_cnt%></td>
						<td><%=v_readcnt%></td>
					</tr>
  	<%}%>
<%}else{%>

    <tr>
      <td colspan="7" align=center valign=top>등록된 내용이 없습니다. </td>
    </tr>
<%}%>
	</table>
			</div>
			<table class="listfooter" cellpadding="0" cellspacing="0">
				<colgroup><col width="150"><col width="*"><col width="150"></colgroup>
				<tr>
					<td class="alignL">&nbsp;</td>
					<td class="alignC">
						<table class="pageset">
							<colgroup><col width="100"><col width="20"><col width="*"><col width="20"><col width="100"></colgroup>
							<tr>
							<td>
							<%=PageUtil.printPageList(v_totalpage, v_pageno, row) %>
							</td>
							
								<!-- <td class="alignR"><a href="#"><img src="/images/factory/btn_prev10.gif" alt="이전 10페이지"></a></td>
								<td class="alignR"><a href="#"><img src="/images/factory/btn_prev.gif" alt="이전페이지"></a></td>
								<td class="alignC"><b>1</b>&nbsp; 2&nbsp; 3&nbsp; 4&nbsp; 5&nbsp; 6&nbsp; 7&nbsp; 8&nbsp; 9&nbsp; 10</td>
								<td class="alignL"><a href="#"><img src="/images/factory/btn_next.gif" alt="다음페이지"></a></td>
								<td class="alignL"><a href="#"><img src="/images/factory/btn_next10.gif" alt="다음 10페이지"></a></td> -->
							</tr>
						</table>
					</td>
					<td class="alignR"><a href="javascript:insertPage();"><img src="/images/factory/btn_write.gif" alt="등록하기"></a></td>
				</tr>
			</table>
			<!-- 좌측 콘텐츠 끝 -->
		</div>
		<%@ include file="/learn/user/portal/include/rightmenu.jsp"%>	
		<div class="clearL"><!-- no cut --></div>
	</div>
	<!-- 하단 시작 -->
	<div id="footer">
		<%@ include file="/learn/user/portal/include/footerfactory.jsp"%>	
	</div>
	<!-- 하단 끝 -->
</div>
</body>
</html>

