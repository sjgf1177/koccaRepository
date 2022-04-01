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
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	
    box.put("topmenu","99");
    
	String  v_process		= box.getString("p_process");
	int     v_pageno		= box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1, v_upfilecnt = 0;
    int     v_pagesize		= box.getInt("p_pagesize");
    int		v_totalrowcount =  0;
    int row					= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    String  v_searchtext	= box.getString("p_searchtext");
    String  v_select		= box.getString("p_select");
    String	v_category		= box.getString("p_area");
    
	String	v_tgubun = "";
	String	v_tabseq = "";
	String	v_seq = "";
	String	v_types = "";
	String	v_title = "";
	String	v_indate = "";
	String	v_inuserid = "";
	String	v_cnt = "";
	String	v_name = "";
	String	v_subj = "";
	String	v_subjseq = "";
	String	v_year = "";
	String	v_lesson = "";
	String	v_subjnm = "";
	String	v_area = "";
	String	v_upperclass = "";
	String	v_middleclass = "";
	String	v_lowerclass = "";
	String	v_answercnt = "";
	int	v_commentcnt = 0;
	String	ss_tgubun = box.getString("ss_tgubun");
	
	ArrayList list = (ArrayList)request.getAttribute("selectLastList");
	
	String full_toggle = "off";
	String sub1_toggle = "off";
	String sub2_toggle = "off";
	String sub3_toggle = "off";
	String sub4_toggle = "off";
	String sub5_toggle = "off";
	
	String sub_navi = "";

	if (v_category.equals("C0")){
		sub1_toggle = "on"; 
		sub_navi = "문의";
	}else if (v_category.equals("B0")){
		sub2_toggle = "on"; 
		sub_navi = "방송영상";
	} else if (v_category.equals("G0")){
		sub3_toggle = "on"; 
		sub_navi = "게임개발";
	} else if (v_category.equals("K0")){
		sub4_toggle = "on"; 
		sub_navi = "문화콘텐츠";
	}else if (v_category.equals("MINE")){
		sub5_toggle = "on"; 
		sub_navi = "내가작성한글";
	} else {
		full_toggle = "on"; 
		sub_navi = "종합";
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
function select(tgubun, tabseq, seq, subj, subjseq, year, lesson, types) {
	
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value	= "selectView";
    document.form1.p_tgubun.value	= tgubun;
    document.form1.p_tabseq.value	= tabseq;
    document.form1.p_seq.value		= seq;
    document.form1.p_types.value    = types;
    document.form1.p_subj.value     = subj;
    document.form1.p_year.value     = year;
    document.form1.p_subjseq.value	= subjseq;
    document.form1.p_lesson.value	= lesson;
    
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
function sstgubun_listpage(){
	document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.submit();
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

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<form name = "form1" method = "post" action="">
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td></td>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 커뮤니티 > <u><%=sub_navi %></u></td>
			</tr>
		</table>
		<div class="concept e01">
			<div><img src="/images/2012/sub/page6/title/common.png" alt="여러분이 궁금한 걸 물어보세요! 분야별 전문가님들이 답변을 달아드립니다."></div>
			<ul class="factorysearch">
				<li class="typechoice">
					<p onclick="typechoiceopen('1')" onfocus="typechoiceopen('1')" tabindex="171"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" id="choicetype" value="subject">
					<input type="hidden" name="p_select" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="175" class="insearch" onfocus="this.style.background='#ffffff'"  value = "<%=v_searchtext%>"></li>
				<li class="output"><a href="javascript:selectList();" tabindex="176" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></li>
			</ul>
			  <input type = "hidden" name = "p_process"     value = "<%=v_process%>">
		      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
		      <input type = "hidden" name = "p_tgubun"      value = "">
		      <input type = "hidden" name = "p_tabseq"   	value = "">
		      <input type = "hidden" name = "p_seq"         value = "">
		      <input type = "hidden" name = "p_types"       value = "">
		      <input type = "hidden" name = "p_area"  value = "<%=v_category%>">
		      <input type = "hidden" name = "p_subj"  value = "">
		      <input type = "hidden" name = "p_year"  value = "">
		      <input type = "hidden" name = "p_subjseq"  value = "">
		      <input type = "hidden" name = "p_lesson"  value = "">
			<ul class="pagetab">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage" tabindex="177" title="지식팩토리의 전체 목록을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/total_<%=full_toggle %>.png" alt="종합"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0" tabindex="178" title="방송영상 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/cast_<%=sub2_toggle%>.png" alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0" tabindex="179" title="게임개발 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/game_<%=sub3_toggle%>.png" alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0" tabindex="180" title="문화콘텐츠 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/culture_<%=sub4_toggle%>.png" alt="문화콘텐츠"></a></li>
<!--				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=C0" tabindex="181" title="과정 외 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/homeqna_<%=sub1_toggle%>.png" alt="문의"></a></li>-->
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" tabindex="182" title="회원님이 작성한 글을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/mywrite_<%=sub5_toggle%>.png" alt="내가 작성한 글"></a></li>
			</ul>
		</div>
		<div style="margin-top: 10px;text-align: right;">
		</div>
		<div class="myclass">
			<div class="listbar">
				<table class="listrable" cellpadding="0" cellspacing="0" summary="지식팩토리의 목록입니다">
					<colgroup><col width="50"><col width="50"><col width="*"><col width="300"><col width="80"><col width="50"></colgroup>
					<tr class="listhead">
						<th id="w_num">번호</th>
						<th><select name="ss_tgubun" onchange="javascript:sstgubun_listpage();">
				<option value="ALL">전체</option>
				<option value="Q" <%if(ss_tgubun.equals("Q")) out.println("selected"); %>>질문</option>
				<option value="I" <%if(ss_tgubun.equals("I")) out.println("selected"); %>>정보</option>
				<option value="D" <%if(ss_tgubun.equals("D")) out.println("selected"); %>>자료</option>
				<option value="T" <%if(ss_tgubun.equals("T")) out.println("selected"); %>>토론</option>
			</select></th>
						<th id="w_subject" colspan="1">주제</th>
						<th id="w_course">관련과정</th>
						<th id="w_date">등록일</th>
						<th id="w_count">조회</th>						
					</tr>
					<%
					if(list.size() > 0 ){
						for(int i = 0; i < list.size(); i++) {
							DataBox dbox = (DataBox)list.get(i);
							
							v_tgubun		= dbox.getString("d_tgubun");
							v_tabseq		= dbox.getString("d_tabseq");
							v_seq			= dbox.getString("d_seq");
							v_types			= dbox.getString("d_types");
							v_title			= dbox.getString("d_title");
							v_inuserid		= dbox.getString("d_inuserid");
							v_cnt			= dbox.getString("d_cnt");
							v_name			= dbox.getString("d_name");
							v_indate		= dbox.getString("d_indate");
							v_subj			= dbox.getString("d_subj");
							v_subjseq		= dbox.getString("d_subjseq");
							v_year			= dbox.getString("d_year");
							v_lesson		= dbox.getString("d_lesson");
							v_subjnm		= dbox.getString("d_subjnm");
							v_area			= dbox.getString("d_area");
							v_upperclass	= dbox.getString("d_upperclass");
							v_middleclass	= dbox.getString("d_middleclass");
							v_lowerclass	= dbox.getString("d_loserclass");
							v_answercnt		= dbox.getString("d_answercnt");
							v_commentcnt	= dbox.getInt("d_commentcnt");
							
							
							v_dispnum   = dbox.getInt("d_dispnum");
			                v_totalpage = dbox.getInt("d_totalpage");
			                v_totalrowcount = dbox.getInt("d_totalrowcount");
			                
			                String t_addate = v_indate.substring(0,8);
							String t_agodate = dbox.getString("d_agodate").substring(0,8);
							int c_addate = Integer.parseInt(t_addate);
							int c_agodate = Integer.parseInt(t_agodate);
							boolean new_che = false;
							String new_tag = "";
							if(c_addate-c_agodate > 0){
								new_che = true;
								new_tag = "<img src=\"/images/2012/main/icon/icon_newnotice.gif\" alt=\"new\">";
							}
							
			                String v_tgubunnm = "";
							if(v_tgubun.equals("T")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_t.gif\" alt=\"토론\" />";}
							else if(v_tgubun.equals("Q")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_q.gif\" alt=\"질문\" />";}
							else if(v_tgubun.equals("C")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_c.gif\" alt=\"자유\" />";}
							else if(v_tgubun.equals("D")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_d.gif\" alt=\"자료\" />";}
							else if(v_tgubun.equals("I")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_i.gif\" alt=\"정보\" />";}
							else if(v_tgubun.equals("R")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_r.gif\" alt=\"문의\" />";}
							else if(v_tgubun.equals("N")){v_tgubunnm = "<img src=\"/images/factory/icon_tutorcom_n.gif\" alt=\"공지\" />";}
							
							// 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
							//if (!v_searchtext.equals("")) {
							//	v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">"+v_searchtext+"</font>");
							//}
					%>
					<tr class="listmiddle<%if(v_tgubun.equals("N"))out.print("2");%>" onmouseover="this.style.background='#fbfbfb'" onmouseout="this.style.background=''">
						<td name="w_num" class="linefirst"><%=v_dispnum%></td>
						<td class="icontype"><%=v_tgubunnm %></td>
						<td name="w_subject" class="title courselist" id="bboardfocus<%=i%>">
							<a href="javascript:select('<%=v_tgubun%>','<%=v_tabseq%>','<%=v_seq%>','<%=v_subj%>','<%=v_subjseq%>','<%=v_year%>','<%=v_lesson%>','<%=v_types%>')" tabindex="<%=i+200%>"  onfocus="boardfocus('b',<%=i%>)"  onblur="boardfocus('b',<%=i%>)" title="[<%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%>] <%=v_title%>">
								<%=StringManager.formatTitle(v_title,32)%><%if(new_che)%> <%=new_tag %>
							</a><span style="color:#fc5f00;"><%if(v_commentcnt > 0)out.print(" ["+v_commentcnt+"]"); %><%if(!v_answercnt.equals("0") && (v_tgubun.equals("Q")||v_tgubun.equals("R")))out.print(" 답변 "+v_answercnt+""); %></span>
						</td>
						<td name="w_course" class="coursename"><%=StringManager.formatTitle(v_subjnm, 32)%></td>
						<td name="w_date"><%=FormatDate.getFormatDate(v_indate, "yyyy.MM.dd")%></td>
						<td name="w_count"><%=v_cnt%></td>
					</tr>
					<%} }else{ %>					
					<tr>
						<td colspan="6" class="nodata linefirst">등록된 게시물이 없습니다.</td>
					</tr>
					<%}%>
				</table>
			</div>
			<div class="boardoption"><button type="button" tabindex="221" onclick="javascript:insertPage();" class="btn_small_lineblue" title="질문글 또는 지식글을 작성할 수 있습니다">글 작성</button></div>
			<div class="pageset">
				<%= PageUtil.re2012_printPageListDiv(v_totalpage, v_pageno, 0, 15, v_rowcount) %>
			</div>
		</div>
	</div>
</form>	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>