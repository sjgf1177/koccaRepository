<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","3");
    
    String v_title      = "";
    String v_contents   = "";
    String v_usernm     = "";
    String v_inuserid   = "";
    String v_indate     = "";
    String v_types      = "";
    String v_categorycd = "";
    String v_isopen     = "";
    String content      = "";
    
    
    int     v_seq        = box.getInt("p_seq");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    int     v_pageno     = box.getInt("p_pageno");
    String  v_selCategory = box.getString("p_selCategory");
    
    String  width        = "650";
    String  height       = "200";   
    
    String  s_name = box.getSession("name");
    
    DataBox dbox = (DataBox)request.getAttribute("selectQna");
    if(dbox != null) {
        v_inuserid   = dbox.getString("d_inuserid");
        v_types      = dbox.getString("d_types");
        v_usernm       = dbox.getString("d_name");
        v_indate     = dbox.getString("d_indate");
        v_title      = dbox.getString("d_title");
        v_seq        = dbox.getInt("d_seq");
        v_categorycd = dbox.getString("d_categorycd");
        v_isopen     = dbox.getString("d_isopen");
    
        content          = dbox.getString("d_contents");
        String s_userid  = box.getSession("userid");    
    }

%>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
 //리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "";     
    document.form1.submit();    
}  

 //데이터를 입력한다.
function update() {

    var frm = document.form1;
     
   /*  if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert(" 에디터에 내용을 입력해 주세요 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return; 
        }  */
    if (blankCheck(document.all.p_title.value)) {
        alert("제목을 입력하세요!");
        document.all.p_title.focus();
        return;
    }                
    if (realsize(document.all.p_title.value) > 100) {
        alert("제목은 한글기준 50자를 초과하지 못합니다.");
        document.all.p_title.focus();
        return;
    }

    if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
        alert("분류를 선택하세요!");
        frm.p_categorycd.focus();
        return;
    }

    //if(!limitFile()){
    //    alert("업로드 가능하지 않은 파일이 존재합니다.");
    //    return;
    //}

    //document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    
    document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
    document.form1.p_process.value = "update";
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
function realsize( value ) {//글자수를 체크
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



//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" method = "post" action="">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_types"       value = "<%= v_types %>">
    <input type = "hidden" name = "p_userid"      value = "<%=v_inuserid%>">
    <input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_select"      value = "<%=v_select %>">
    <input type = "hidden" name = "p_selCategory" value = "<%=v_selCategory %>">
    
    <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_process"     value="">
    
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
					<p onclick="typechoiceopen('1')" tabindex="171" onfocus="typechoiceopen('1')"><span id="choicename">주제</span><img src="/images/2012/sub/page6/arrow_down.png" align="absmiddle" alt="선택"></p>
					<input type="hidden" name="p_select" id="choicetype" value="title">
					<dl id="typelist" class="typelist" style="display:none;">
						<dd onclick="searchtype('주제','title')" tabindex="172" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">주제</dd>
						<dd onclick="searchtype('내용','content')" tabindex="173" onfocus="this.style.background='#1f68b3'" onblur="this.style.background='#4898ea'; searchtypeclose();" onmouseover="this.style.background='#1f68b3'" onmouseout="this.style.background='#4898ea'">내용</dd>
					</dl>
				</li>
				<li class="insert"><input type="text" name="p_searchtext" tabindex="174" class="insearch" onfocus="this.style.background='#ffffff'" title="검색할 과정을 입력해주세요"></li>
				<li class="output"><a href="javascript:searchList();" tabindex="175" title="검색을 시작합니다"><img src="/images/2012/sub/page2/btn_course_search.gif" alt="과정검색"></a></li>
			</ul>
		</div>
		<div class="myclass">
			<div class="writepost">
				<table class="writeform" cellspacing="0" cellpadding="0" summary="묻고답하기에 작성하신 글을 수정하는 공간입니다">
					<colgroup><col width="120"><col width="*"></colgroup>
					<tr>
						<th id="w_category"><h5>분야</h5></th>
						<td name="w_category">
							<select id="txtfield1" tabindex="176" name="p_categorycd" onfocus="choicefield('1');" title="분야를 선택해주세요">
								<option value="">분야선택</option>
								<option value="A1" <% if (v_categorycd.equals("A1")) out.print("selected");%>>게임개발</option>
								<option value="B1" <% if (v_categorycd.equals("B1")) out.print("selected");%>>방송영상</option>
								<option value="C1" <% if (v_categorycd.equals("C1")) out.print("selected");%>>문화콘텐츠</option>
							</select>
						</td>
					</tr>
					<tr>
						<th id="w_subject"><h5>주제</h5></th>
						<td name="w_subject"><input type="text" name="p_title" id="txtfield2" tabindex="177" onfocus="choicefield('2');" class="txt"  value="<%=v_title %>"></td>
					</tr>
					<tr>
						<th id="w_content"><h5>내용</h5></th>
						<td name="w_content"><textarea id="p_content" tabindex="178" name="p_content" id="txtfield3" onfocus="choicefield('3');" title="내용을 입력해주세요"><%=StringManager.replace(content, "&", "&amp") %></textarea></td>
					</tr>
				</table>
			</div>
			<div class="writefooter">
				<a href="javascript:update();" tabindex="179" title="이 글의 수정을 완료하고 목록으로 돌아갑니다"><img src="/images/2012/sub/page6/btn_modify_confirm.gif" align="absmiddle" alt="이 글을 수정합니다"></a>
				<a href="javascript:history.go(-1)" tabindex="180" title="수정을 취소하고 이전페이지로 돌아갑니다"><img src="/images/2012/sub/page6/btn_modify_cancel.gif" align="absmiddle" alt="글 수정을 취소합니다"></a>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
