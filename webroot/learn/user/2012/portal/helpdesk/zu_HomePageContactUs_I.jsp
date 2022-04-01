<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.cp.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","6");


    int    v_tabseq     = box.getInt("p_tabseq");
    String v_process    = box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    String v_indate     = "";
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");
    String s_email      = box.getSession("email");
    String content      = "";
    String width        = "650";
    String height       = "200";
   // String leftmenu = box.getString("leftmenu");
    
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//입력된 내용 저장
function insert() {

	if( document.form1.p_title.value == '' ) {
		alert("제목을 입력하세요.");
		document.form1.p_title.focus();
		return;
	}	
   
    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }
    
    if (realsize(document.form1.p_content.value) < 1) {
        alert("내용을 입력해주세요");
        document.form1.p_content.focus();
        return;
    }

    //document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    
    document.form1.p_pageno.value = "1";
    document.form1.p_search.value     = "";
    document.form1.p_searchtext.value = "";
    document.form1.action = "/servlet/controller.homepage.HomePageContactServlet";
    document.form1.p_process.value = "insert";
    document.form1.submit();
}

//빈내용 체크
function blankCheck( msg ) {
    var mleng = msg.length;
    chk=0;
    
    for (i=0; i<mleng; i++) {
        if ( msg.substring(i,i+1)!=' ' && msg.substring(i,i+1)!='\n' && msg.substring(i,i+1)!='\r') chk++;
    }
    if ( chk == 0 ) return (true);
    
    return (false);
}

//내용입력을 취소하고 리스트로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.MainServlet";
     //document.form1.p_process.value = "selectList";
     document.form1.submit();
}

//글자수 체크
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

//-->
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post" action="">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_process" value="">
    
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계시는 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 학습지원 > <u>1:1문의</u></td>
			</tr>
		</table>
		<div class="concept">
			<div><img src="/images/2012/sub/page4/title/1n1.gif" alt="학습에 대해 궁금한 것이 있다면 무엇이든 물어보세요"></div>
		</div>
		<div class="myclass">
			<div class="writepost">
				<table class="writeform" cellspacing="0" cellpadding="0">
					<colgroup><col width="120"><col width="*"></colgroup>
					<tr>
						<th><h5>주제</h5></th>
						<td><input type="text" tabindex="171" name="p_title" id="txtfield1" onfocus="choicefield('1');" class="txt" title="주제를 입력해주세요"></td>
					</tr>
					<tr>
						<th><h5>내용</h5></th>
						<td><textarea id="p_content" tabindex="172" name="p_content" onfocus="choicefield('2');" title="내용을 입력해주세요"></textarea></td>
					</tr>
				</table>
			</div>
			<div class="writefooter">
				<a href="javascript:insert();" tabindex="173" title="작성한 글을 업로드하고 목록으로 돌아갑니다"><img src="/images/2012/sub/page6/btn_confirm.gif" align="absmiddle" alt="작성한 글을 등록합니다"></a>
				<a href="javascript:history.go(-1)" tabindex="174" title="작성을 취소하고 이전페이지로 돌아갑니다"><img src="/images/2012/sub/page6/btn_cancel.gif" align="absmiddle" alt="글작성을 취소합니다"></a>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
