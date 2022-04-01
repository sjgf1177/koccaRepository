<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명 : gu_KnowBoard_I.jsp
//  3. 개      요: 지식공유 최근지식 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼:
//  6. 작      성: 이나연 06.01.03
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    box.put("topmenu","99");

    String v_process= box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");
    
    String v_categorycd = box.getString("p_area");            // request 카테고리
    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    String content      = "";
    String width        = "650";
    String height       = "200";

    String s_userid     = box.getSession("userid");
    String s_name       = box.getSession("name");
    
    String full_toggle = "off";
    String sub1_toggle = "off";
    String sub2_toggle = "off";
    String sub3_toggle = "off";
    String sub4_toggle = "off";
    String sub5_toggle = "off";

    String sub_navi = "";

    if (v_categorycd.equals("C0")){
    	sub1_toggle = "on"; 
    	sub_navi = "문의";
    }else if (v_categorycd.equals("B0")){
    	sub2_toggle = "on"; 
    	sub_navi = "방송영상";
    } else if (v_categorycd.equals("G0")){
    	sub3_toggle = "on"; 
    	sub_navi = "게임개발";
    } else if (v_categorycd.equals("K0")){
    	sub4_toggle = "on"; 
    	sub_navi = "문화콘텐츠";
    }else if (v_categorycd.equals("MINE")){
    	sub5_toggle = "on"; 
    	sub_navi = "내가작성한글";
    } else {
    	full_toggle = "on"; 
    	sub_navi = "종합";
    }
%>
<script language="JavaScript" type="text/JavaScript">
<!--
 //리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
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

//선택 조회
function whenSelection(p_action) {

 	//var v_grcode, v_subj, v_upperclass, v_middleclass, v_lowerclass;
	//v_grcode      = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;	
 	//v_subj        = document.form1.s_subjcourse.value;
 	document.form1.p_catecd.value  = document.form1.s_subjcourse.value;
 }

// 카테고리 선택
function openCategory(){
    window.self.name = "winSelectList";
    open_window("openwinSelectList","","100","100","390","400","","","","yes","");
    document.form1.target = "openwinSelectList";
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "OpenCategoryPage";
    document.form1.submit();
    document.form1.target = window.self.name;

}

 //데이터를 입력한다.
function insert() {
	var frm = document.form1;

	if (frm.p_tgubun.value == "SEL"){
		alert("말머리를 선택하세요.");
		frm.p_tgubun.focus();
		return;
	}else if(frm.p_tgubun.value == "C"){
		if(frm.p_category.value == "SEL"){
			alert("분야를 선택하세요.");
			frm.p_category.focus();
			return;
		}
	}

    if (blankCheck(frm.p_title.value)) {
        alert("제목을 입력하세요!");
        frm.p_title.focus();
        return;
    }                
    
    if (realsize(frm.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        frmp_title.focus();
        return;
    }

    //document.getElementById("txtfield4").value = CrossEditor.GetBodyValue();	
    
    frm.p_searchtext.value    = "";
    frm.p_pageno.value        =  1;

    var subjinfo = frm.p_subjinfo.value;

    frm.p_subj.value = subjinfo.substr(0,7);
    frm.p_subjseq.value = subjinfo.substr(7,4);
    frm.p_year.value = subjinfo.substr(11,4);
    frm.p_lesson.value = "000";

    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();    
}

//입력 내용의 공백을 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;

    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);

    return (false);
}

//글자수를 체크
function realsize( value ) {
    var len = 0;
    if ( value == null ) return 0;
    for(var i=0;i<value.length;i++){
        var c = escape(value.charAt(i));
        if ( c.length == 1 ) len ++;
        else if ( c.indexOf("%u") != -1 ) len += 2;
        else if ( c.indexOf("%") != -1 ) len += c.length/3;
    }
    return len;
}

//파일첨부에 쓰기 방지
function File_CancelKeyInput()
{
    if(event.keyCode == 9){ // Tab key만 허용
        return true;
    }else{
        alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.');
        return false;
    }
}
function display_showhide(el_id, showhide){
	if(showhide == "S"){
		$("#"+el_id).show();
	}else{
		$("#"+el_id).hide();
	}
}
function change_tgubun(val){
	if(val == "R"){
		display_showhide("tr_category","S");
		display_showhide("tr_course","H");
	}else{
		display_showhide("tr_course","S");
		display_showhide("tr_category","H");
	}
}
//-->
</script>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 지식팩토리 > <u>종합</u></td>
			</tr>
		</table>
		<div class="concept e01">
			<div><img src="/images/2012/sub/page6/title/common.png" alt="여러분이 궁금한 걸 물어보세요! 분야별 전문가님들이 답변을 달아드립니다."></div>
			<ul class="pagetab">
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage" tabindex="177" title="지식팩토리의 전체 목록을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/total_<%=full_toggle %>.png" alt="종합"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=B0" tabindex="178" title="방송영상 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/cast_<%=sub2_toggle%>.png" alt="방송영상"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=G0" tabindex="179" title="게임개발 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/game_<%=sub3_toggle%>.png" alt="게임개발"></a></li>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=K0" tabindex="180" title="문화콘텐츠 분야 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/culture_<%=sub4_toggle%>.png" alt="문화콘텐츠"></a></li>
<!--				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=C0" tabindex="181" title="과정 외 지식만을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/homeqna_<%=sub1_toggle%>.png" alt="문의"></a></li>-->
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE" tabindex="182" title="회원님이 작성한 글을 확인합니다"><img src="/images/2012/sub/page6/tabmenu/add/mywrite_<%=sub5_toggle%>.png" alt="내가 작성한 글"></a></li>
			</ul>
		</div>
		<div class="myclass">
		<!-- 쓰기 폼 시작 -->
		<form name = "form1" enctype = "multipart/form-data" method = "post" action="">
	    <input type = "hidden" name = "p_seq"           value = "<%=v_seq %>">
	    <input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext %>">
	    <input type = "hidden" name = "p_pageno"        value = "<%=v_pageno %>">		   
	    <input type = "hidden" name = "p_process"       value = "">
	    <input type = "hidden" name = "p_subj" 		    value = "">
	    <input type = "hidden" name = "p_subjseq"   	value = "">
	    <input type = "hidden" name = "p_year"      	value = "">
	    <input type = "hidden" name = "p_lesson"        value = "">
			<div class="writepost">
				<table class="writeform" cellspacing="0" cellpadding="0" summary="새로운 글을 작성할 수 있습니다">
					<colgroup><col width="120"><col width="*"></colgroup>
					<tr>
						<th><h5>말머리</h5></th>
						<td>
							<select name="p_tgubun" onchange="javacript:change_tgubun(this.value);">
								<option value="SEL">선택</option>
								<option value="Q">질문</option>
<!--								<option value="R">문의</option>-->
								<option value="I">정보</option>
<!--								<option value="C">자유</option>-->
								<option value="T">토론</option>
<!--								<option value="D">자료</option>-->
							</select>
						</td>
					</tr>
					<tr id="tr_course" style="display: none;">
						<th id="w_course"><h5>과정명</h5></th>
						<td name="w_course"><%= SelectSubjBean.getSubjInfo(box,v_categorycd)%></td>
					</tr>
					<tr id="tr_category" style="display: none;">
						<th id="w_category"><h5>분류</h5></th>
						<td name="w_category">
							<select name="p_category">
								<option value="SEL">선택</option>
								<option value="C0">일반</option>
								<option value="B0">방송영상</option>
								<option value="G0">게임개발</option>
								<option value="K0">문화콘텐츠</option>
							</select>
						</td>
					</tr>
					<tr>
						<th id="w_subject"><h5>주제</h5></th>
						<td name="w_subject"><input type="text" tabindex="201" name="p_title" id="txtfield2" onfocus="choicefield('2');" class="txt" title="주제를 입력해주세요"></td>
					</tr>
					<tr>
						<th id="w_content"><h5>내용</h5></th>
						<td name="w_content"><textarea id="txtfield3" tabindex="202" name="content" onfocus="choicefield('3');" title="내용을 입력해주세요"></textarea></td>
					</tr>
				</table>
			</div>
			<div class="writefooter">
				<a href="javascript:insert();" tabindex="203" title="작성한 글을 업로드하고 목록으로 돌아갑니다"><img src="/images/2012/sub/page6/btn_confirm.gif" align="absmiddle" alt="작성한 글을 등록합니다"></a>
				<a href="javascript:selectList();" tabindex="204" title="작성을 취소하고 이전페이지로 돌아갑니다"><img src="/images/2012/sub/page6/btn_cancel.gif" align="absmiddle" alt="글작성을 취소합니다"></a>
			</div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>