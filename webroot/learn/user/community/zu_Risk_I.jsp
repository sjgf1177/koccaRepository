<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

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
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
    var insertSw=false;

    //리스트화면으로 이동
    function selectList() {
        document.form1.action = "/servlet/controller.community.CommunityRiskServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }

    //데이터를 입력한다.
    function insert() {
        if(insertSw)
            return;

        insertSw=true;
        
        var frm = document.form1;

        if(document.all.use_editor[0].checked) {
            form1.p_content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
        }else {
            form1.p_content.value = document.all.txtDetail.value;
        }

        if (blankCheck($("[name='p_title']").val())) {
            alert("제목을 입력하세요!");
            $("[name='p_title']").focus();
            insertSw=false;
            return;
        }

        if (realsize($("[name='p_title']").val()) > 100) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            $("[name='p_title']").focus();
            insertSw=false;
            return;
        }

        //document.getElementById("p_content").value = CrossEditor.GetBodyValue();

        frm.p_searchtext.value    = "";
        frm.p_select.value        = "";
        frm.p_selCategory.value   = "";
        frm.p_pageno.value        =  1;

        document.form1.action = "/servlet/controller.community.CommunityRiskServlet";
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
</script>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1" enctype = "multipart/form-data" method = "post">
    <input type = "hidden" name = "p_seq"           value = "<%= v_seq %>">
    <input type = "hidden" name = "p_searchtext"    value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_select"        value = "<%=v_select %>">
    <input type = "hidden" name = "p_selCategory"   value = "<%=v_selCategory %>">

    <input type = "hidden" name = "p_pageno"        value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_process"       value = "">
    <input type = "hidden" name = "p_gubun"       value = "0">
    <input type = "hidden" name = "p_parentseq" value="0"   >
    
            <h2><img src="/images/portal/community/h6_tit_5.gif" alt="실시간위험관리" class="fl_l" /><p class="category">Home > 커뮤니티 > <strong>실시간위험관리</strong></p></h2>

            <table class="board_write">
            <tr class="bo">
                <th class="th" width="50px">작성자</th>
                <td class="td txt" width="400px"><%=s_usernm %></td>
                <th class="th" style="width:60px">작성일자</th>
                <td class="td stxt ff_t" ><%= FormatDate.getDate("yyyy.MM.dd") %></td>
            </tr>
            <tr class="bo">
                <th class="th">제목</th>
                <td colspan="3" class="td"><input name="p_title" id="subject" type="text" class="inbox" style="width:500px;" /></td>
            </tr>
            <tr class="bo">
                <td width=85% colspan="4" class="td con">
                    <textarea  id="p_content" name="p_content" style="display:none;"></textarea>
                    <%@ include file="/include/DhtmlEditor.jsp" %>
                </td>
            </tr>
            </table>
            <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = null;        // 실제 파일명
            Vector i_savefileVector    = null;        // 저장 파일명
            Vector i_fileseqVector     = null;         // 파일 일련번호

            int    i_fileLimit         = 10;             // 제한 첨부 파일수

            String i_formName          = "form1";                                     // Form 이름
            String i_ServletName       = "RiskMenagermentServlet";                        // 저장 서블릿 이름       riskmenagermentServlet
            String i_boardStyle        = "board_write";                               // 입력 테이블 Class 명
            %>

            <%@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
            <!-- 파일첨부 종료 -->

            <p class="board_btn"><a href="javascript:insert();" class="board_blue mg_r5" id="inserBtn"><span>저장</span></a><a href="javascript:history.back(-1);" class="board_blue mg_r5"><span>취소</span></a><a href="javascript:history.back(-1);" class="board_blue"><span>목록</span></a></p>

</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->