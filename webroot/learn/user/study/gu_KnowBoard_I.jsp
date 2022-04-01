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

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","05");
    String v_process= box.getString("p_process");
    int    v_pageno     = box.getInt("p_pageno");
    String p_categorycd = box.getString("p_categorycd");            // request 카테고리
    int v_seq = box.getInt("p_seq");
    String v_searchtext = box.getString("p_searchtext");
    String v_select     = box.getString("p_select");
    int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    String content      = "";
    String width        = "650";
    String height       = "200";

    String s_userid     = box.getSession("userid");
    String s_name       = box.getSession("name");


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/portal/include/top.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
 //리스트화면으로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
    document.form1.p_process.value = "ListPage";
    document.form1.submit();
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

    if (blankCheck(document.all.p_catecd.value)) {
        alert("카테고리를 선택하세요!");
        return;
    }

        //파일 확장자 필터링
        var islimit = true;

        for(var i=1; i<=1; i++){
            var file = eval("document.form1.p_file"+i+".value");

            if(file!="") {
                islimit = limitFile(file);

                if(!islimit) break;
            }
        }

        if(islimit) {
            document.form1.p_searchtext.value = "";
            document.form1.p_select.value     = "";
            document.form1.action = "/servlet/controller.homepage.KnowBoardUserServlet";
            document.form1.p_process.value = "insert";
            document.form1.submit();
        }else{
            return;
        }
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

<!-- /////////////////////////////////////////////////////////-->
<!--
  파일확장자체크 INCLUDE

  1. page import = "com.credu.system.*" 추가
  2. 자바스크립트 호출부분 추가
    예제)
    //파일 확장자 필터링
    var islimit = true;
    var file = document.form1.p_file1.value;
    if(file!="") {
        islimit = limitFile(file);    //return값 true/false
    }

    if(islimit) {
        document.form1.submit();
    }else{
        return;
    }

-->
<%@ include file = "/learn/library/fileFilter.jsp" %>
<!-- /////////////////////////////////////////////////////////-->

          <!-- center start -->

<form name = "form1" enctype = "multipart/form-data" method = "post">
    <input type="hidden" name="p_process"    value="<%=v_process%>">
    <input type="hidden" name="p_pageno"     value="<%=v_pageno%>">
    <input type="hidden" name="p_seq"        value="<%=v_seq%>">
    <input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
    <input type="hidden" name="p_select"     value="<%=v_select%>">
    <input type="hidden" name="p_upfilecnt"  value="<%=v_upfilecnt %>">
    <input type="hidden" name="p_categorycd" value="<%=p_categorycd%>">

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="720" height="35" align="right"  background="/images/user/game/mystudy/<%=tem_subimgpath%>/tit_joint.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 나의 공부방 > 지식공유게시판</td>
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
 <tr height="30">
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_subject02.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td style="padding-left:10px" colspan="3">
        <input name="p_title" type="text" size="60"> 
    </td>
  </tr>
  <tr height="30">
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_t_writer.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td class="tbl_gleft03" colspan="3"><%=s_name%></td>
  </tr>
  <tr height="30">
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_catagory.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="250" style="padding-left:10px">
        <input name="p_catenm" type="text" readonly size="50" >
    </td>
    <td width="77" class="tbl_grc" align="left"><input type=hidden name="p_catecd">
        <a href="javascript:openCategory();"><img src="/images/user/game/button/btn_catagory.gif"></td>
    <td width="315">&nbsp;</td>
  </tr>
  <tr height="30">
    <td width="77" class="tbl_grc"><img src="/images/user/game/mystudy/text_add_file.gif"></td>
    <td width="1"  valign="bottom" ><img src="/images/user/game/mystudy/vline.gif"></td>
    <td width="250"style="padding-left:10px" colspan="2"><input name="p_file1" type="FILE" class="input" size="50" onkeydown="javascript:return File_CancelKeyInput()">
    </td>
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
    <td width="48" align="right" valign="bottom"><a href="javascript:insert();"><img src="/images/user/game/button/btn_save.gif"></td>
    <td width="2" align="right" valign="bottom">&nbsp;</td>
    <td width="48" align="right" valign="bottom"><a href="javascript:selectList();"><img src="/images/user/game/button/btn_cancel.gif"></td>
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