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

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","05");

    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String p_categorycd = box.getString("p_categorycd");            // request 카테고리
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";
    String v_categorycd = "";
    String v_categorynm = "";
    String v_types = "";

    String content = "";
    String width = "650";
    String height = "200";

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    DataBox dbox = (DataBox)request.getAttribute("SelectView");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_contents");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
        
        content = StringManager.replace(dbox.getString("d_contents"),"\n;","<br>");
        v_title = StringManager.replace(v_title, "`", "'");

        v_types      = dbox.getString("d_types");
        v_categorycd = dbox.getString("d_categorycd");
        v_categorynm = dbox.getString("d_categorynm");
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topMystudy.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<!------- 파일확장자체크 INCLUDE ------------>
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!------- 파일확장자체크 INCLUDE END ------------>


<script language="JavaScript" type="text/JavaScript">
<!--

//변경된 내용을 수정
function update() {
    if(document.all.use_editor[0].checked) {
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }
    if (blankCheck(document.form1.p_title.value)) {
        alert("제목을 입력하세요!");
        document.form1.p_title.focus();
        return;
    }
    if (realsize(document.form1.p_title.value) > 200) {
        alert("제목은 한글기준 100자를 초과하지 못합니다.");
        document.form1.p_title.focus();
        return;
    }
    if (blankCheck(document.form1.content.value)) {
        alert("내용을 입력하세요!");
        document.form1.p_content.focus();
        return;
    }

            //파일 확장자 필터링
        var islimit = true;

        for(var i=1; i<=1; i++){
            var file = eval("document.form1.p_file1.value");

            if(file!="") {
                islimit = limitFile(file);

                if(!islimit) break;
            }
        }

        if(islimit) {
            document.form1.p_search.value     = "";
            document.form1.p_searchtext.value = "";
            document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
            document.form1.p_process.value = "update";
            document.form1.submit();
        }else{
            return;
        }

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

<form name = "form1" enctype = "multipart/form-data" method = "post">
    <input type = "hidden" name = "p_tabseq"     value="<%=v_tabseq%>">
    <input type = "hidden" name = "p_process"    value="<%=v_process%>">
    <input type = "hidden" name = "p_pageno"     value="<%=v_pageno%>">
    <input type = "hidden" name = "p_search"     value="<%=v_search%>">
    <input type = "hidden" name = "p_searchtext" value="<%=v_searchtext%>">
    <input type = "hidden" name = "p_seq"        value="<%=v_seq%>">
    <input type = "hidden" name = "p_upfilecnt"  value="<%=v_upfilecnt%>">
    <input type = "hidden" name = "p_userid"     value="<%=v_userid%>">
    <input type = "hidden" name = "p_types"      value="<%=v_types%>">
    <input type = "hidden" name = "p_categorycd" value="<%=p_categorycd%>">


<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_joint.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
      HOME > 나의 공부방 > 지식공유게시판</td>
  </tr>
  <tr>
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td ><img src="/images/user/game/mystudy/st_su_edu.gif"></td>
  </tr>
   <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="81" height="2" class="linecolor_board3"></td>
    <td width="639" height="2" class="linecolor_board4"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0"
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#DFE4CA" frame="hsides" rules="rows" >
 <tr>
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_subject02.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td style="padding-left:10px" colspan="3">
        <input type="text" name="p_title" size="60" class="input" value ="<%= v_title %>">
    </td>
  </tr>
  <tr>
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_writer.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td class="tbl_gleft03" colspan="3"><%=v_name%></td>
  </tr>
  <tr>
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_catagory.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="250" style="padding-left:10px">
        <input type="text" name="p_catenm" size="50" class="input"  valign="center" value='<%=v_categorynm%>'>
        <input type=hidden name='p_catecd'  value='<%=v_categorycd%>'>
    </td>
    <td width="77" class="tbl_grc">
        <a href="javascript:openCategory();"><img src="/images/user/game/button/btn_catagory.gif" align="absmiddle"></a>
    </td>
    <td width="315"></td>
  </tr>
  <tr>
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_add_file.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="250"style="padding-left:10px" colspan="2"><input name="p_file1" type="FILE" class="input" size="50" onkeydown="javascript:return File_CancelKeyInput()"></td>
    <!--td width="77" class="tbl_grc"><img src="/images/user/game/button/btn_search_view.gif"></td-->
    <td width="315"></td>
  </tr>
  <tr>
    <td colspan="5" class="tbl_contents">
        <!-- DHTML Editor  -->
        <%@ include file="/include/DhtmlEditor.jsp" %>
        <!-- DHTML Editor  -->
    </td>
  </tr>
</table>
<table width="721" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="1" colspan="4" class="linecolor_bg01"></td>
  </tr>
  <tr>
    <td height="5" colspan="4"></td>
  </tr>
  <tr>
    <td align="center" valign="bottom">&nbsp;</td>
    <td width="48" align="right" valign="bottom"><a href = "javascript:update();"><img src="/images/user/game/button/btn_save.gif"></td>
    <td width="2" align="right" valign="bottom">&nbsp;</td>
    <td width="48" align="right" valign="bottom"><a href = "javascript:cancel();"><img src="/images/user/game/button/btn_cancel.gif"></td>
  </tr>
  <tr>
    <td height="5" colspan="4" ></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="2" bgcolor="#92C2BB"></td>
  </tr>
</table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->

