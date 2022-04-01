<%
//**********************************************************
//  1. 제      목: 지식공유 최근지식
//  2. 프로그램명: gu_KnowBoard_U.jsp
//  3. 개      요: 지식공유 최근지식 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.03
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.dunet.common.util.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
		RequestBox box = (RequestBox)request.getAttribute("requestbox");
		
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
		
		DataBox dbox = (DataBox)request.getAttribute("SelectView");
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

<script language="JavaScript" type="text/JavaScript">
<!--

//변경된 내용을 수정
function update() {
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
    document.form1.p_process.value = "update";
    document.form1.submit(); 

}

//리스트 화면으로 이동
function cancel() {
     document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
     document.form1.p_process.value = "ListPage";
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

//글자 사이즈 체크
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
//-->
</script>
          <!-- center start -->
 
<%@ include file="/learn/user/portal/include/topfactory.jsp"%>

	<div id="container">
		<div class="contentL">
			<!-- 좌측 콘텐츠 시작 -->
			<div><img src="/images/factory/show.gif"></div>
			<ul class="tabmenu">
				<li><img src="/images/factory/tab01over.gif" id="tab01" onclick="postchg('1');" alt="종합"></li>
				<li><img src="/images/factory/tab02.gif" id="tab02" onclick="postchg('2');" alt="방송영상"></li>
				<li><img src="/images/factory/tab03.gif" id="tab03" onclick="postchg('3');" alt="게임개발"></li>
				<li><img src="/images/factory/tab04.gif" id="tab04" onclick="postchg('4');" alt="문화컨텐츠"></li>
				<li><img src="/images/factory/tab05.gif" id="tab05" onclick="postchg('5');" alt="내가 작성한 글"></li>
			</ul>
			<!-- 쓰기 폼 시작 -->
			<form name = "form1" enctype = "multipart/form-data" method = "post">
		    <input type = "hidden" name = "p_seq"           value = "<%= v_seq %>">
		    <input type = "hidden" name = "p_searchtext"    value = "">
		    <input type = "hidden" name = "p_select"        value = "">
		    <input type = "hidden" name = "p_selCategory"   value = "">    
		    <input type = "hidden" name = "p_pageno"        value = "">
		    <input type = "hidden" name = "p_catecd"        value = "">
		    <input type = "hidden" name = "p_process"       value = "">
		    <input type = "hidden" name = "p_userid"      value = "">
		    <input type = "hidden" name = "p_upfilecnt"   value = "">
		    <input type = "hidden" name = "p_types"       value = "<%=v_types %>">
    
    
			<table class="writeform" cellpadding="0" cellspacing="0">
				<colgroup><col width="150"><col width="*"></colgroup>
				
				<tr>
					<th class="colorA">제목</th>
					<td class="colorA noline"><input name="p_title" type="text" size="60" value="<%=dbox.getString("d_title") %>"></td>
				</tr>
				 <tr>
	             <td colspan="8" class="td con">
	                    <textarea id="p_content" name="content" style="display:none;"><%=StringManager.replace(dbox.getString("d_contents"), "&", "&amp") %></textarea>
						<script type="text/javascript">
						    var CrossEditor = new NamoSE("contents");
						    var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
						    CrossEditor.editorStart();
						    CrossEditor.SetUISize("650","400");
						    CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
						</script>
	                </td>
	            </tr>            
							
			</table>
			
            <div class="writefooter">
            <a href="javascript:update();" class="btn_gr"><img src="/images/factory/btn_update.gif"></a><a href="javascript:cancel();" class="btn_gr"><img src="/images/factory/btn_cancel.gif"></a>			
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
