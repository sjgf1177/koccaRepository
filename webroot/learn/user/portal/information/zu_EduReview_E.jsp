<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.infomation.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String  v_process    = box.getString("p_process");
	int     v_tabseq     = box.getInt   ("p_tabseq");
	int     v_pageno     = box.getInt   ("p_pageno");
	String  s_userid     = box.getSession("userid");
	String  s_name     = box.getSession("name");
	String  v_searchtext = box.getString("p_searchtext");
	String  v_search     = box.getString("p_search");

	//편집기 사이즈
	String width        = "650";
	String height       = "200";
    String  v_date          = "";
    String  v_title         = "";
    String  v_content       = "";
    String  v_name          = "";
    String  v_flagYn        = "";
    String  v_position      = "";
    String  v_url           = "";
    int     v_cnt           =  0;
    String   v_seq           =  "";

    DataBox dbox = (DataBox)request.getAttribute("edit");
    if (dbox != null) {
        v_title     = dbox.getString("d_title");
        v_seq       = dbox.getString("d_seq");
        v_content   = dbox.getString("d_content");
        v_date      = dbox.getString("d_ldate").substring(0,4)+"/"+dbox.getString("d_ldate").substring(4,6)+"/"+dbox.getString("d_ldate").substring(6,8);;
        v_name      = dbox.getString("d_name");
        v_flagYn    = dbox.getString("d_flagyn");
        v_position  = dbox.getString("d_position");
        v_url       = dbox.getString("d_url");
        v_cnt       = dbox.getInt("d_cnt");
     }
	String content = StringManager.replace(dbox.getString("d_content"),"\n;","<br>");
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
 //리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
    document.form1.target = "_self";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
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
        frm.p_title.focus();
        return;
    }

//    if(!limitFile()){
//        alert("업로드 가능하지 않은 파일이 존재합니다.");
//        return;
//    }

    frm.p_searchtext.value    = "";
    frm.p_search.value        = "";
    frm.p_pageno.value        =  1;

    document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.infomation.EduReviewHomePageServlet";
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
<form name = "form1" method = "post">
	<input type = "hidden" name = "p_process" >
	<input type = "hidden" name = "p_tabseq" value="<%=v_tabseq%>">
	<input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
	<input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
	<input type = "hidden" name = "p_search" value = "<%=v_search %>">
    <input type = "hidden" name = "p_seq" value = "<%=v_seq %>">

            <h2><img src="/images/portal/information/h2_tit8.gif" alt="교육후기" class="fl_l" /><p class="category">Home > 정보광장 > <strong>교육후기</strong></p></h2>

            <p><img src="/images/portal/information/edulatter_borimg.gif" alt="과정 수강을 마친 수강생들의 교육후기를 만나보세요 " /></p>

            <table class="board_write">
            <!--[if ie]><colgroup><col width="25px" /><col width="400px" /><col width="65px" /><col width="80px" /></colgroup><![endif]-->
            <colgroup><col width="45px" /><col width="440px" /><col width="85px" /><col width="80px" /></colgroup>
            <tr class="bo">
                <th class="th">제목</th>
                <td class="td"><input name="p_title" type="text" class="inbox" style="width:415px;" value="<%=v_title%>"/></td>
                <th class="th">작성자</th>
                <td class="td txt"><%=s_name %></td>
            </tr>
            <tr class="bo">
                <th class="th">소속</th>
                <td class="td"><input name="p_position" type="text" class="inbox" style="width:200px;"  value="<%=v_position%>"/></td>
                <th class="th">작성일자</th>
                <td class="td stxt"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
            </tr>
            <tr valign="top" class="bo">
                <td colspan="4" class="td con">
                    <textarea id="p_content" name="p_content" style="display:none;"><%=StringManager.replace(v_content, "&", "&amp") %></textarea>
                    <script type="text/javascript">
	                    var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
						var CrossEditor = new NamoSE('contents');
						CrossEditor.editorStart();
	    				CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
					</script> 
                </td>
            </tr>
            </table>

            <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
            Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
            Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호

            int    i_fileLimit         = EduReviewHomePageBean.getFILE_LIMIT();             // 제한 첨부 파일수

            String i_formName          = "form1";                                           // Form 이름
            String i_ServletName       = "EduReviewHomePageServlet";                        // 저장 서블릿 이름
            String i_boardStyle        = "board_write";                               // 보드 스타일
            %>
            <%@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
            <!-- 파일첨부 종료 -->

            <p class="f_btn">
                <a href="javascript:insert();" class="board_violet"><span>등록</span></a>
                <a href="javascript:selectList();" class="board_violet"><span>취소</span></a>
                <a href="javascript:selectList();" class="board_violet"><span>목록</span></a>
            </p>
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->