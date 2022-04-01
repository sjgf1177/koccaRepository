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

	box.put("topmenu","6");
    if (box == null) box = RequestManager.getBox(request);

    String v_process= box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");
    String p_categorycd = box.getString("p_categorycd");            // request 카테고리
    String v_selCategory = box.getString("p_selCategory");
    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    String content      = "";
    String width        = "650";
    String height       = "200";

    String s_userid     = box.getSession("userid");
    String s_name       = box.getSession("name");
    
    String tabimg1 ="tab01.gif";
    String tabimg2 ="tab02.gif";
    String tabimg3 ="tab03.gif";
    String tabimg4 ="tab04.gif";
    String tabimg5 ="tab05.gif";
    
    if (p_categorycd.equals("B0")){
    	tabimg2 ="tab02over.gif";
    } else if (p_categorycd.equals("G0")){
    	tabimg3 ="tab03over.gif";
    } else if (p_categorycd.equals("K0")){
    	tabimg4 ="tab04over.gif";
    }else if (p_categorycd.equals("MINE")){
    	tabimg5 ="tab05over.gif";
    } else {
    	tabimg1 ="tab01over.gif";
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

    if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert(" 에디터에 내용을 입력해 주세요 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return; 
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
/*
    if (frm.p_categorycd.options[frm.p_categorycd.selectedIndex].value == "") {
        alert("분류를 선택하세요!");
        frm.p_categorycd.focus();
        return;
    }
*/
    document.getElementById("p_content").value = CrossEditor.GetBodyValue();	
    
    frm.p_searchtext.value    = "";
    frm.p_select.value        = "";
    frm.p_selCategory.value   = "";
    frm.p_pageno.value        =  1;
    
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

//-->
</script>

<%@ include file="/learn/user/portal/include/topfactory.jsp"%>

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
			<!-- 쓰기 폼 시작 -->
			<form name = "form1" enctype = "multipart/form-data" method = "post">
		    <input type = "hidden" name = "p_seq"           value = "<%= v_seq %>">
		    <input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext %>">
		    <input type = "hidden" name = "p_select"        value = "<%=v_select %>">
		    <input type = "hidden" name = "p_selCategory"   value = "<%=v_selCategory %>">    
		    <input type = "hidden" name = "p_pageno"        value = "<%=v_pageno %>">		   
		    <input type = "hidden" name = "p_process"       value = "">
		    <input type = "hidden" name = "p_userid"      value = "">
		    <input type = "hidden" name = "p_upfilecnt"   value = "">
		    <input type = "hidden" name = "p_types"       value = "">
		    <input type = "hidden" name = "p_categorycd"  value = "<%=p_categorycd%>">
    
			<table class="writeform" cellpadding="0" cellspacing="0">
				<colgroup><col width="150"><col width="*"></colgroup>
				<tr>
					<th class="colorA">과정명</th>
					<td class="colorA noline">
					<%= SelectSubjBean.getSubj3(box,p_categorycd)%>
						<!--<select name="p_catecd"><option value="A01001000">11가지 법칙으로 끝내는 스토리텔링</option></select>
						-->
					</td>
				</tr>
				<tr>
					<th class="colorA">제목</th>
					<td class="colorA noline"><input name="p_title" type="text" size="60"></td>
				</tr>
				<tr>
					<td colspan="2" class="colorB noline">
					 <textarea  id="p_content" name="content" style="display:none;"></textarea>
                    <script type="text/javascript">
                      var CrossEditor = new NamoSE("contents");
                       CrossEditor.editorStart();
                    </script> 
					</td>
				</tr>				
			</table>
			<div class="writefooter">
				<a href="javascript:insert();"><img src="/images/factory/btn_write.gif" alt="등록하기" border=0></a>				
				<a href="javascript:selectList();"><img src="/images/factory/btn_list.gif" alt="목록" border=0></a>
			</div>
			</form>	
			<!-- 쓰기 폼 끝 -->
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
