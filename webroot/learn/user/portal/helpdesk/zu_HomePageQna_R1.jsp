<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
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
	
	    content          = StringManager.replace(dbox.getString("d_contents"),"\n;","<br>");
	    String s_userid  = box.getSession("userid");    
	}

%>
<%@ include file="/learn/user/portal/include/top.jsp"%>
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
     
    if(document.all.use_editor[0].checked) {
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
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

    if(!limitFile()){
        alert("업로드 가능하지 않은 파일이 존재합니다.");
        return;
    }
    
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
<form name = "form1" enctype = "multipart/form-data" method = "post">
    <input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
    <input type = "hidden" name = "p_types"       value = "<%= v_types %>">
    <input type = "hidden" name = "p_userid"      value = "<%=v_inuserid%>">
    <input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_select"      value = "<%=v_select %>">
    <input type = "hidden" name = "p_selCategory" value = "<%=v_selCategory %>">
    
    <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_process"     value="">

            <h2><img src="/images/portal/studying/h2_tit3.gif" alt="Q&A" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>Q&A</strong></p></h2>

            <p><img src="/images/portal/studying/qna_borimg.gif" alt="묻고 싶은 사항을 등록하시면, 담당자가 답변해드립니다. 질문 내용에 대한 공개를 원하지 않으시면 비공개로 작성해주세요. " /></p>
                              
            <table class="board_write mg_t30">
            <!--[if ie]><colgroup><col width="35px" /><col width="120px" /><col width="55px" /><col width="80px" /><col width="40px" /><col width="80px" /><col width="50px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="55px" /><col width="120px" /><col width="75px" /><col width="80px" /><col width="60px" /><col width="80px" /><col width="70px" /><col width="80px" /></colgroup>
            <tr>
                <th>분류</th>
                <td>
                    <kocca_select:select                                       
                        name="p_categorycd"                                         
                        sqlNum="0001"                                    
                        param="0088"             
                        onChange="changeCategory(this.value);"                         
                        attr= "분류"                                        
                        selectedValue="<%=v_categorycd %>"                         
                        isLoad="true"                                           
                        type="1"                                                
                        styleClass=""                                
                        all="false" />
                </td>
                <th><img src="/images/portal/ico/ico_board_s.gif" alt="" class="va_m mg_r7" />비밀글</th>
                <td><input name="p_isopen" type="checkbox" value = "N" <%=v_isopen.equals("N") ? "checked" : "" %>></td>
                <th>작성자</th>
                <td class="txt"><%=v_usernm%></td>
                <th>작성일자</th>
                <td class="stxt ff_t"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
            </tr>
            <tr class="bo">
                <th>제목</th>
                <td colspan="7"><input name="p_title" type="text" class="inbox" style="width:590px;" value="<%=v_title %>/></td>
            </tr>
            <tr>
                <td width=100% colspan="8" class="con">
                    <textarea name="content" rows="" cols=""><%=content %></textarea>
                    <!--script type="text/javascript">
                      var CrossEditor = new NamoSE("content");
                       CrossEditor.editorStart();
                    </script> 
                    <input type="hidden" id="content" name="content" value="" --> 
                </td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
            
            int    i_fileLimit         = HomePageQnaBean.getFILE_LIMIT();             // 제한 첨부 파일수
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_U.jsp" %> 
            <!-- 파일첨부 종료 -->
    
            <p class="board_btn"><a href="javascript:insert();" class="board_blue mg_r5"><span>등록</span></a><a href="javascript:history.back(-1);" class="board_blue mg_r5"><span>취소</span></a><a href="javascript:history.back(-1);" class="board_blue"><span>목록</span></a></p>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->