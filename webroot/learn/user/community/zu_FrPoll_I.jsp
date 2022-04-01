<%@ page contentType = "text/html;charset=MS949" %>
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
    String  v_brd_fg      = box.getString("p_brd_fg");  
    String  v_pollno     = box.getString("p_pollno");
    String  v_title      = box.getString("p_title");
    String  v_fdte       = box.getString("p_fdte");
    String  v_tdte       = box.getString("p_tdte");
    String  v_background = box.getString("p_background");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    int     v_pageno     = box.getInt("p_pageno");

%>  
<%@ include file="/learn/user/community/include/cm_top.jsp"%> 
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
$(document).ready(function(){
	$("#p_fdte").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_tdte").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

<!--
//저장
function uf_insertOK() {
    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }


    if(document.form1.p_fdte.value ==''){
       alert('조사기간을 입력하여야합니다.');document.form1.p_fdte.focus();return;
    }

    if(document.form1.p_tdte.value ==''){
       alert('조사기간을 입력하여야합니다.');document.form1.p_tdte.focus();return;
    }

    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "insertData";
    document.form1.submit();
}


//리스트
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}


//예시 페이지로 이동
function uf_msSamplePage() {

   open_window('movesamplePage', '/servlet/controller.community.CommunityFrPollServlet?p_process=movesamplePage', 100, 100, 670, 480, 'no', 'no', 'no', 'no', 'no');
  
}


//날짜 입력 체크
function TxtCheck(key, CtlName, TextMask){
    if((key > 47&&key < 58)||(key > 95 && key <106)) {
        if(CtlName.value.length < TextMask.length) { 
        var aa = CtlName.value + String.fromCharCode(key);
    
        if(key > 47 && key < 58) var zz = aa.charAt(aa.length-1);
        else if(key > 95 && key < 106){
        
            switch(key){
                case 96: zz = '0'; break; case 97: zz = '1'; break;
                case 98: zz = '2'; break; case 99: zz = '3'; break;
                case 100: zz = '4'; break; case 101: zz = '5'; break;
                case 102: zz = '6'; break; case 103: zz = '7'; break;
                case 104: zz = '8'; break; case 105: zz = '9'; break;
            }
        }

        if(TextMask.charAt(aa.length-1) == '#') CtlName.value = CtlName.value + zz;
        else CtlName.value = CtlName.value + TextMask.charAt(aa.length-1) +zz;
        return false;
        }
    }
    else if(key == 8 || key == 9 || key == 13) return ; else return false;

}

// 질문 항목 추가 
var itemCnt = 5;

function itemAdd(){
	
    var itemAddDiv = document.getElementById("itemAdd_div"); 
	
	var sItem = itemAddDiv.innerHTML;

	sItem += "<p id='item_p' name='item_p' class=\"item\">항목"+itemCnt+" <input type=hidden name=\"p_fieldno\"><input name=\"p_field_name\" type=\"text\" class=\"inbox va_up1\" style=\"width:190px;\" /></p>"

	document.getElementById("itemAdd_div").innerHTML = sItem;

	itemCnt++;
}

function itemDel() {
	if(itemCnt == 5) {
        alert("더이상 항목삭제 할수 없습니다.");
        return;
    }
    var parent_obj  = document.getElementById("itemAdd_div");

    var span_obj = document.getElementsByName("item_p");

    for(var i = 0 ; i < span_obj.length ; i++) {
        if(span_obj.length-1 == i) {
            parent_obj.removeChild(span_obj[i]);
        } 
    }

    itemCnt--;
}
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name = "form1"    enctype = "multipart/form-data"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_pollno"      value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_rowseq"      value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_brd_fg"      value = "<%=box.getString("p_brd_fg")%>">
<input type = "hidden" name = "p_re_gen_fg"   value = "1">

            <h1><img src="/images/portal/community/tit_13.gif" alt="설문작성" /></h1>

            <table class="list">
            <!--[if ie]><colgroup><col width="85px" /><col /></colgroup><![endif]-->
            <colgroup><col width="105px" /><col /></colgroup>
            <thead>
            <tr>
                <th class="ta_l">작성자</th>
                <td class="ta_l end"><%=box.getSession("name")%></td>
            </tr>
            <tr>
                <th class="ta_l">제목</th>
                <td class="ta_l in end"><input name="p_title" type="text" class="inbox" style="width:500px;" value=""/></td>
            </tr>
            <tr>
                <th class="ta_l">설문기간</th>
                <td class="ta_l in end">
                    <!-- 시작일 <input name="p_fdte" id="p_fdte" type="text" class="inbox " style="width:65px;" value=""  OnClick="MiniCalById('p_fdte')" readonly /> <a href="javascript:MiniCalById('p_fdte')"><img src="/images/portal/ico/ico_calendar.gif" class="va_m"/></a> ~
                    종료일 <input name="p_tdte" id="p_tdte" type="text" class="inbox " style="width:65px;" value=""  OnClick="MiniCalById('p_tdte')" readonly /> <a href="javascript:MiniCalById('p_tdte')"><img src="/images/portal/ico/ico_calendar.gif" class="va_m"/></a>-->
                    시작일 <input name="p_fdte" id="p_fdte" type="text" class="datepicker_input1 " style="width:65px;" value=""> ~
                    종료일 <input name="p_tdte" id="p_tdte" type="text" class="datepicker_input1 " style="width:65px;" value="">
                </td>
            </tr>
            <tr>
                <th class="ta_l va_t">설문배경</th>
                <td class="ta_l in end"><textarea name="p_background" style="width:94%; height:40px;"></textarea></td>
            </tr>
            <tr>
                <th class="ta_l">문항</th>
                <td class="ta_l in end"><input name="p_need_question" type="text" class="inbox" style="width:500px;" /></td>
            </tr>
            <tr>
                <th class="ta_l va_t"    id="itemView_div1">답변항목</th>
                <td class="ta_l in end"  id="itemView_div2">
                    <div>
                    <p id="item_p" name="item_p" class="item">항목1 <input type=hidden name="p_fieldno"><input name="p_field_name" type="text" class="inbox va_up1" style="width:190px;" value="" /></p>
                    <p id="item_p" name="item_p" class="item">항목2 <input type=hidden name="p_fieldno"><input name="p_field_name" type="text" class="inbox va_up1" style="width:190px;" value="" /></p>
                    <p id="item_p" name="item_p" class="item">항목3 <input type=hidden name="p_fieldno"><input name="p_field_name" type="text" class="inbox va_up1" style="width:190px;" value="" /></p>
                    <p id="item_p" name="item_p" class="item">항목4 <input type=hidden name="p_fieldno"><input name="p_field_name" type="text" class="inbox va_up1" style="width:190px;" value="" /></p>
                    <div id="itemAdd_div"></div>
		            <p>
		                <a href="javascript:itemDel();" class="board_btn fl_r"><span>항목삭제</span></a>
		                <a href="javascript:itemAdd();" class="board_btn fl_r"><span>항목추가</span></a>
		            </p>
                    </div>
                </td>
            </tr>
            </thead>
            </table>

            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>등록</span></a></p>
            
<div id=minical OnClick="this.style.display='none';" style=" display:none;position: absolute;">
</div>

</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->