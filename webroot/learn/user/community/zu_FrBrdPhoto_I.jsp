<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    String  v_cmuno      = box.getString("p_cmuno");
    String  v_menuno     = box.getString("p_menuno");    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);
    int     v_pageno     = box.getInt("p_pageno");

	String  v_board_fg	 = box.getStringDefault("p_brd_fg","0");

     String v_filecnt       = (String)request.getAttribute("fielcnt");
     if(v_filecnt==null||"".equals(v_filecnt))	v_filecnt="0";


%>  
  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
//등록
function uf_insertOK() {
	var v_board_fg = document.form1.p_brd_fg.value;

    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }
    
	if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
        alert("내용을 입력해 주세요 !!"); 
        CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
        return; 
    } 

    document.getElementById("p_content").value = CrossEditor.GetBodyValue();
	document.form1.p_brd_fg.value= v_board_fg;
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}

//리스트
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrBoardServlet";
    document.form1.p_process.value = "selectlist";
    document.form1.submit();
}

//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    enctype = "multipart/form-data"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_menuno"      value="<%=v_menuno%>">
<input type = "hidden" name = "p_pageno"      value = "">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_upfilecnt"   value = "">
<input type = "hidden" name = "p_brdno"       value = "">
<input type = "hidden" name = "p_rowseq"      value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_brd_fg"       value = "<%=box.getString("p_brd_fg")%>">

            <h1><img src="/images/portal/community/tit_04.gif" alt="앨범" /></h1>

            <table class="study_write">
            <!--[if ie]><colgroup><col width="45px" /><col width="400" /><col width="55px" /><col width="120" /></colgroup><![endif]-->
            <colgroup><col width="65px" /><col width="400" /><col width="75px" /><col width="120" /></colgroup>
            <tr>
                <th class="th"><label for="subject">제목</label></th>
                <td colspan="7" class="td"><input type="text" name="p_title" id="subject" class="inbox" style="width:97%;" /></td>
            </tr>
            <tr>
                <th class="th">작성자</th>
                <%if(v_menuno.equals("1")){%>
                <td class="td txt"><%=s_username%></td>
                <%} else { %>
                <td class="td"><%=s_username%>
                <input type="radio" name="p_display_fg" style="vertical-align:-1px;" value = "Y" class="va_up1 mg_l15" checked /> 공개 
                <input type="radio" name="p_display_fg" style="vertical-align:-1px;" value = "N" class="va_up1 mg_l10" /> 비공개
                </td>
                <%} %>
                <th class="th">작성일자</th>
                <td class="td stxt ff_t"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
            </tr>
            
            <tr>
                <td colspan="8" class="td con" style="padding: 0 5 0 0;">
                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
					<script type="text/javascript">
					    var CrossEditor = new NamoSE("contents");
					    CrossEditor.editorStart();
					    CrossEditor.SetUISize("650","400");
					</script>
                </td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = null;        // 실제 파일명
            Vector i_savefileVector    = null;        // 저장 파일명
            Vector i_fileseqVector     = null;          // 파일 일련번호
            
            int    i_fileLimit         = 1;             // 제한 첨부 파일수
            
            String i_formName          = "form1";                                     // Form 이름
            String i_ServletName       = "CommunityFrBoardServlet";                   // 저장 서블릿 이름
            String i_boardStyle        = "study_write";                               // 입력 테이블 Class 명
            %>
            <%@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
            <!-- 파일첨부 종료 -->
            
            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>등록</span></a><a href="javascript:wf_listOK();" class="btn_gr"><span>취소</span></a></p>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->
