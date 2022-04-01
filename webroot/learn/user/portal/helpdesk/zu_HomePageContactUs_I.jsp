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

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<script language="JavaScript" type="text/JavaScript">
<!--
var grCode = '<%= box.getSession("tem_grcode") %>';

//입력된 내용 저장
function insert() {

	if( document.form1.p_title.value == '' ) {
		alert("제목을 입력하세요.");
		document.form1.p_title.focus();
		return;
	}
	
    if ( grCode == "N000001" ) {
        if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
            alert(" 에디터에 내용을 입력해 주세요 !!"); 
            CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
            return; 
        }  
    } else {
        if ( document.getElementById("p_content").value == "" ) {
            alert("내용을 입력해 주세요.");
            document.getElementById("p_content").focus();
            return;
        }
    }

    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }

    if ( grCode == "N000001" ) {
        document.getElementById("p_content").value = CrossEditor.GetBodyValue();
    }
    
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
<form name = "form1" enctype = "multipart/form-data" method = "post">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_process" value="">
    
<%
    if( box.getSession("tem_grcode").equals("N000001")) {
%>
	<table>   
      <tr>
      	<td>
      	
      	<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/support/stitle_05.gif" alt="1:1문의" /></td>
        <td class="h_road">Home &gt; 학습지원 안내  &gt; <strong>1:1문의</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="1" colspan="6" bgcolor="#7692CB"></td>
      </tr>
      <tr>
        <td width="100" class="tit_table">제목</td>
        <td colspan="5" class="ct_tit_table_lright"><input name="p_title" type="text" id="p_title" size="50" /></td>
      </tr>
      <tr>
        <td class="tit_table">답변회신메일</td>
        <td class="ct_tit_table_left"><%=s_email%></td>
        <td class="tit_table">작성자</td>
        <td class="ct_tit_table_left"><%=s_username%></td>
        <td width="80" class="tit_table">작성일자</td>
        <td class="ct_tit_table"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
      </tr>
      <tr>
        <td colspan="6" class="ct_tit_table_left">
			<textarea  id="p_content" name="p_content" style="display:none;"></textarea>
                    <script type="text/javascript">
                      var CrossEditor = new NamoSE("contents");
                       CrossEditor.editorStart();
                    </script>
		</td>
      </tr>
      <!--
      <tr>
        <td class="tit_table">첨부파일</td>
        <td colspan="5" class="ct_tit_table_lright"><img src="/images/portal/homepage_renewal/support/btn_search.jpg" width="65" height="21" /><img src="/images/portal/homepage_renewal/support/btn_clean.jpg" width="53" height="21" /></td>
      </tr>
		-->
      <tr>
        <td height="1" colspan="6" bgcolor="CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="6" bgcolor="F7F7F7"></td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="h_road"><a href="javascript:insert();"><img src="/images/portal/homepage_renewal/support/btn_reg.jpg" alt="등록" width="46" height="25" /></a></td>
      </tr>
    </table>
    <!--타이틀부분//-->
    </td>
    <td width="20">&nbsp;</td>
    
  </tr>
</table>
      	
<%
    } else {
%>

            <h2><img src="/images/portal/studying/h2_tit8.gif" alt="1:1 문의" class="fl_l" /><p class="category">Home > 학습지원센터 > <strong>1:1 문의</strong></p></h2>

            <p><img src="/images/portal/studying/inquiry_borimg.gif" alt="사이트 이용시 불편한점, 건의사항, 버그 및 오류  신고할 내용이 있으면 등록해주세요. 회신메일로 답변드립니다" /></p>

            <table class="board_write mg_t30">
            <!--[if ie]><colgroup><col width="80px" /><col width="180px" /><col width="40px" /><col width="50px" /><col width="50px" /><col width="50px" /></colgroup><![endif]-->
            <colgroup><col width="80px" /><col width="180px" /><col width="45px" /><col width="50px" /><col width="55px" /><col width="50px" /></colgroup>
            <tr>
                <th class="th">제목</th>
                <td class="td" colspan="5"><input name="p_title" type="text" class="inbox" style="width:530px;" /></td>
            </tr>
            <tr class="bo">
                <th class="th">답변회신메일</th>
                <!--td><input type="text" class="inbox" style="width:210px;"  /></td-->
                <td class="td"><%=s_email%></td>
                <th class="th">작성자</th>
                <td class="td txt"><%=s_username%></td>
                <th class="th">작성일자</th>
                <td class="td stxt ff_t"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
            </tr>
            <tr>
                <th class="th">내용</th>
                <td colspan="5" class="td">
                    <textarea id="p_content" name="p_content" style="display:block; width:520px; height:280px;"></textarea>
                    <script type="text/javascript">
                        // 2014-07-25
                        // 승인 되지 않은 도메인이라 하는 메시지와 함께 웹 에디터가 로딩되지 않음.
                        // 나모측에 문의 결과 *.kocca.or.kr 도메인에만 적용됨
                        // ASP 사이트는 *.edukocca.or.kr로 되어 있어서 적용되지 않은 상태임.
                        // 이에 따라 문제를 해결하고자 웹 에디터 대신에 textarea로 대체
                        //
                        // var CrossEditor = new NamoSE("contents");
                        // CrossEditor.editorStart();
                    </script>
                </td>
            </tr>
            </table>
            
            <!-- 파일첨부 시작 -->
            <%
            Vector i_realfileVector    = null;        // 실제 파일명
            Vector i_savefileVector    = null;        // 저장 파일명
            Vector i_fileseqVector     = null;         // 파일 일련번호
            
            int    i_fileLimit         = HomePageQnaBean.getFILE_LIMIT();             // 제한 첨부 파일수
            
            String i_formName          = "form1";                                     // Form 이름
            String i_ServletName       = "HomePageQNAServlet";                        // 저장 서블릿 이름
            String i_boardStyle        = "board_write";                               // 입력 테이블 Class 명
            %>
            <%@ include file="/learn/user/portal/include/multiAttach_include.jsp" %>
            <!-- 파일첨부 종료 -->

            <p class="board_btn"><a href="javascript:insert()"" class="board_blue mg_r5"><span>등록</span></a>
            
            <%} %>
      
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->