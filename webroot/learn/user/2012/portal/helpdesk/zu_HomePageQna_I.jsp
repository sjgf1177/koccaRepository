<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","3");
     
    String v_title     = "";
    String v_content   = "";
    String v_usernm    = "";
    String v_userid    = "";
    String v_regdt     = "";
    
    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    String v_selCategory = box.getString("p_selCategory");
    int    v_pageno     = box.getInt("p_pageno");
    String s_usernm     = box.getSession("name");
    String content      = "";
    String width        = "650";
    String height       = "200";
    
    String s_userid     = box.getSession("userid");

%>
<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
    //리스트화면으로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
        document.form1.p_process.value = "";     
        document.form1.submit();    
    }  
    
    //데이터를 입력한다.
    function insert() {
    
        var frm = document.form1;        
    
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
    
        if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
            alert("분류를 선택하세요!");
            frm.p_categorycd.focus();
            return;
        }
        
        if (blankCheck(frm.p_content.value)) {
            alert("내용을 입력하세요!");
            frm.p_content.focus();
            return;
        } 
    
        //document.getElementById("p_content").value = CrossEditor.GetBodyValue();
        
        frm.p_searchtext.value    = "";
        frm.p_select.value        = "";
        frm.p_selCategory.value   = "";
        frm.p_pageno.value        =  1;
        
        document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
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
    

    function checkYN()
    {
        var r=$("[name='p_isopen']").val();
        if(r=="Y")
            $("[name='p_isopen']").val("N");
        else
            $("[name='p_isopen']").val("Y");
        
        //alert(document.form1.p_isopen.value);
    }
    
    
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post" action="">
    <input type = "hidden" name = "p_seq"           value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_select"        value = "<%=v_select %>">
    <input type = "hidden" name = "p_selCategory"   value = "<%=v_selCategory %>">
    
    <input type = "hidden" name = "p_pageno"        value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_process"       value = "">
    
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
				<table class="writeform" cellspacing="0" cellpadding="0" summary="묻고답하기의 글쓰기 공간입니다">
					<colgroup><col width="120"><col width="*"></colgroup>
					<tr>
						<th id="w_category"><h5>분야</h5></th>
						<td name="w_category">
							<select id="txtfield1" tabindex="176" name="p_categorycd"  onfocus="choicefield('1');" title="분야를 선택해주세요">
								<option value="">분야선택</option>
								<option value="A1">게임개발</option>
								<option value="B1">방송영상</option>
								<option value="C1">문화콘텐츠</option>
							</select>
						</td>
					</tr>
					<tr>
						<th id="w_option"><h5>옵션</h5></th>
						<td name="w_option"><input type="checkbox" tabindex="177" name="p_isopen" id="lock" class="checkbox" value="Y" onclick="checkYN();" title="공개여부를 해주세요"><label for="lock" class="label">이 글을 공개하지 않습니다.</label></td>
					</tr>
					<tr>
						<th id="w_subject"><h5>주제</h5></th>
						<td name="w_subject"><input type="text" name="p_title" tabindex="178" id="txtfield2" onfocus="choicefield('2');" class="txt" title="주제를 입력해주세요"></td>
					</tr>
					<tr>
						<th id="w_content"><h5>내용</h5></th>
						<td name="w_content"><textarea id="p_content" tabindex="179" name="p_content" id="txtfield3" onfocus="choicefield('3');" title="내용을 입력해주세요"></textarea></td>
					</tr>
				</table>
			</div>
			<div class="writefooter">
				<a href="javascript:insert();" tabindex="180" title="작성한 글을 업로드하고 목록으로 돌아갑니다"><img src="/images/2012/sub/page6/btn_confirm.gif" align="absmiddle" alt="작성한 글을 등록합니다"></a>
				<a href="javascript:history.go(-1)" tabindex="181" title="작성을 취소하고 이전페이지로 돌아갑니다"><img src="/images/2012/sub/page6/btn_cancel.gif" align="absmiddle" alt="글작성을 취소합니다"></a>
			</div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
