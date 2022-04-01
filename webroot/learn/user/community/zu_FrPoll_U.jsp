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
    String  v_pollno     = box.getString("p_pollno");
    String  v_questno    = box.getString("p_questno");
    String  v_questnotmp = box.getString("p_questnotmp");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
    String  v_brd_fg     = box.getString("p_brd_fg");  
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    String  s_grcode	 = box.getSession("tem_grcode");
	String  s_grtype	 = GetCodenm.get_grtype(box,s_grcode);

    if(v_questnotmp.length()>0)v_questno=v_questnotmp;
    int     v_pageno     = box.getInt("p_pageno");
    int v_userpollcnt    = Integer.parseInt(String.valueOf(request.getAttribute("userpollcnt")));
    ArrayList list       = (ArrayList)request.getAttribute("list");
    ArrayList list1      = new ArrayList();
    ArrayList list2      = new ArrayList();
    ArrayList list3		 = new ArrayList();
    ArrayList list4		 = new ArrayList();

    String  v_title      = "";
    String  v_fdte       = "";
	String  v_tdte       = "";
    String  v_background = "";
    String  v_str_fg     = "";
    String  v_make_nm    = "";
    String  v_email      = "";
    if(list.size() != 0){
       list1       = (ArrayList)list.get(0);  //리스트
       list2       = (ArrayList)list.get(1);  //항목
       list3       = (ArrayList)list.get(2);  //상세필드
       list4       = (ArrayList)list.get(3);  //응답결과

    }
    if(list1.size() != 0){
      DataBox dbox1 = (DataBox)list1.get(0);

    v_title      = dbox1.getString("d_title");
    v_fdte       = dbox1.getString("d_fdte");
    v_tdte       = dbox1.getString("d_tdte");

    v_background = dbox1.getString("d_background");
    v_make_nm    = dbox1.getString("d_name")+"&nbsp;"+dbox1.getString("d_jikupnm")+"/"+dbox1.getString("d_jikwinm")+"&nbsp;"+dbox1.getString("d_deptnam");
    v_str_fg     = dbox1.getString("d_str_fg");
    v_email      = dbox1.getString("d_email");
    }
    
    DataBox dbox2 = (DataBox)list2.get(0); //항목
    
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

//등록
function uf_insertOK() {
	var k = 0;
	var st_date = make_date(document.form1.p_fdte.value);
    var ed_date = make_date(document.form1.p_tdte.value);

    if(document.form1.p_title.value ==''){
       alert('제목을 입력하여야합니다.');document.form1.p_title.focus();return;
    }

		if(blankCheck(document.form1.p_fdte.value)){
			alert("설문 시작일을 입력하세요.");	return;
		}else {
			alert('시작일 '+st_date+' 입니다');
		}
    
        if(blankCheck(document.form1.p_tdte.value)){
			alert("설문 마감일을 입력하세요.");	return;
        }else{
			alert('종료일 '+ed_date+' 입니다');
		}

        if(st_date > ed_date){
			alert("polling 시작일이 마감일보다 큽니다.");return;
        }

    var fieldAry = document.getElementsByName("p_field_name");
    var fieldCnt = fieldAry.length;
   
	for(var i = 0; i < fieldCnt; i++)
	{
		if(document.form1.p_field_name[i].value.length > 0)
		{
			k++;
		}
	}

	document.form1.p_started.value  = st_date;
    document.form1.p_ended.value    = ed_date;
    document.form1.p_fieldcount.value  = k;

	document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "updateData";
    document.form1.submit();

}



//리스트페이지로이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "movelistPage";
    document.form1.submit();
}

//삭제
function wf_deleteOK() {
    document.form1.action = "/servlet/controller.community.CommunityFrPollServlet";
    document.form1.p_process.value = "deletefieldData";
    document.form1.submit();
}

//예시 페이지로 이동
function uf_msSamplePage() {

   open_window('movesamplePage', '/servlet/controller.community.CommunityFrPollServlet?p_process=movesamplePage', 100, 100, 670, 480, 'no', 'no', 'no', 'no', 'no');
  
}

//질문 항목 추가 
var itemCnt = <%=list3.size() + 1%>;

function itemAdd(){
    var itemAddDiv = document.getElementById("itemAdd_div"); 
    
    var sItem = itemAddDiv.innerHTML;

    sItem += "<p id=\"item_p\" name=\"item_p\" class=\"item\">항목"+itemCnt+" <input type=hidden name=\"p_fieldno\" value=\"\"><input name=\"p_field_name\" value=\"\" type=\"text\" class=\"inbox va_up1\" style=\"width:190px;\" value=\"\" /></p>";

    document.getElementById("itemAdd_div").innerHTML = sItem;

    itemCnt++;
}

function itemDel() {
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

<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false' ondragstart='return false' onselectstart='return false' style="background : buttonface; margin: 5; margin-top: 2;border-top: 1 solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1 solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99"></div>

<form name = "form1"    enctype = "multipart/form-data"    method = "post">

<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_cmuno"       value="<%=v_cmuno%>">
<input type = "hidden" name = "p_pollno"      value="<%=v_pollno%>">
<input type = "hidden" name = "p_questno"     value="<%=v_questno%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_rowseq"      value = "">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"      value = "<%=v_select%>">
<input type = "hidden" name = "p_brd_fg"      value = "<%=box.getString("p_brd_fg")%>">
<input type = "hidden" name = "p_started"		  value= "">
<input type = "hidden" name = "p_ended"		  value= "">
<input type = "hidden" name = "p_fieldcount"   >
<input type = "hidden" name="p_re_gen_fg" value="1"/>

            <h1><img src="/images/portal/community/tit_13.gif" alt="설문작성" /></h1>
            
            <table class="list">
            <!--[if ie]><colgroup><col width="85px" /><col /></colgroup><![endif]-->
            <colgroup><col width="105px" /><col /></colgroup>
            <thead>
            <tr>
                <th class="ta_l">작성자</th>
                <td class="ta_l end"><%=v_make_nm%></td>
            </tr>
            <tr>
                <th class="ta_l">제목</th>
                <td class="ta_l in end"><input name="p_title" type="text" class="inbox" style="width:500px;" value="<%=v_title%>"/></td>
            </tr>
            <tr>
                <th class="ta_l">설문기간</th>
                <td class="ta_l in end">
                    <!-- 시작일 <input name="p_fdte" id="p_fdte" type="text" class="inbox " style="width:65px;" value="<%=FormatDate.getFormatDate(v_fdte, "yyyy-MM-dd")%>"  OnClick="MiniCalById('p_fdte')" readonly /> <a href="javascript:MiniCalById('p_fdte')"><img src="/images/portal/ico/ico_calendar.gif" class="va_m"/></a> ~
                    종료일 <input name="p_tdte" id="p_tdte" type="text" class="inbox " style="width:65px;" value="<%=FormatDate.getFormatDate(v_tdte, "yyyy-MM-dd")%>"  OnClick="MiniCalById('p_tdte')" readonly /> <a href="javascript:MiniCalById('p_tdte')"><img src="/images/portal/ico/ico_calendar.gif" class="va_m"/></a> -->
                     시작일 <input name="p_fdte" id="p_fdte" type="text" class="datepicker_input1 " style="width:65px;" value="<%=FormatDate.getFormatDate(v_fdte, "yyyy-MM-dd")%>"> ~
                    종료일 <input name="p_tdte" id="p_tdte" type="text" class="datepicker_input1 " style="width:65px;" value="<%=FormatDate.getFormatDate(v_tdte, "yyyy-MM-dd")%>">
                </td>
            </tr>
            <tr>
                <th class="ta_l va_t">설문배경</th>
                <td class="ta_l in end"><textarea name="p_background" style="width:94%; height:40px;"><%=v_background%></textarea></td>
            </tr>
            <tr>
                <th class="ta_l">문항</th>
                <td class="ta_l in end"><input name="p_need_question" type="text" class="inbox" style="width:500px;" value="<%=dbox2.getString("d_need_question") %>"/></td>
            </tr>
            <tr>
                <th class="ta_l va_t">답변항목</th>
                <td id="sTd" class="ta_l in end">
                    <div id="itemAdd_div">
		            <%
		            for(int j=0;j<list3.size();j++){
		                DataBox dbox3 = (DataBox)list3.get(j);
		            %>     
		                    <p id="item_p" name="item_p" class="item">항목<%=j+1 %> <input type=hidden name="p_fieldno" value="<%=dbox3.getString("d_fieldno")%>"><input name="p_field_name" value="<%=dbox3.getString("d_field_name")%>" type="text" class="inbox va_up1" style="width:190px;" value="질문내용" /></p>
		            <%  } %>
	                </div>
	                <p>
	                <a href="javascript:itemDel();" class="board_btn fl_r"><span>항목삭제</span></a>
	                <a href="javascript:itemAdd();" class="board_btn fl_r"><span>항목추가</span></a>
	                </p>
                </td>
            </tr>    
            </thead>
            </table>

            <p class="board_btn"><a href="javascript:uf_insertOK();" class="btn_gr"><span>수정</span></a></p>   

                    
</form>
<!-- Form 영역 종료 -->

<!-- footer 영역 시작 -->
<%@ include file="/learn/user/community/include/cm_bottom.jsp"%>
<!-- footer 영역 종료 -->