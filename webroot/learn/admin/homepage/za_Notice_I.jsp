<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 8. 1
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    int v_tabseq   = box.getInt("p_tabseq");

 //   String  v_comp    = box.getString("p_comp");
    int  v_pageno      = box.getInt("p_pageno");
    int  v_pagesize    = box.getInt("p_pagesize");
    String  v_selcomp  = box.getString("p_selcomp");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    String s_username  = box.getSession("name");
    String v_compcd = "";

    //ArrayList complist   = (ArrayList)request.getAttribute("compnm");
    ArrayList grcodelist = (ArrayList)request.getAttribute("grcodenm");

    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");

    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }

    String s_grcode = box.getString("s_grcode");

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/cresys_lib.js'></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" >


    $(document).ready(function(){
        $("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
        $("#p_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
    });

    //저장
    function fnSaveNotice() {

        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_edate.value);
      // var chkselectcnt = chkSelected();
        var chkselectcntgrcode = chkSelectedgrcode();

        //alert(document.form1.p_compcd.value);
        //alert(document.form1.p_grocdecd.value);
        //return;

        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        document.form1.p_content.value.replace("&","&amp;");

        if (blankCheck(document.form1.p_adtitle.value)) {
            alert("제목을 입력하세요");
            document.form1.p_adtitle.focus();
            return;
        }

        if (realsize(document.form1.p_adtitle.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_adtitle.focus();
            return;
        }

        if (document.form1.Wec.Value.length < 3) {
            alert("내용을 입력하세요");
            return;
        }

<%
        if(!v_gadmin.equals("K")) { //회사관리자가 아닐때
%>
        if(document.form1.p_login[0].checked == true || document.form1.p_login[1].checked == true ){
            if(chkselectcntgrcode < 1) {
                alert("대상교육그룹을 선택하여주십시오");
                return;
            }
        }
<%
        }
%>


        if(document.form1.p_isAll.checked == true){
            document.form1.p_isAllvalue.value = 'Y';
        }else {
            document.form1.p_isAllvalue.value = 'N';
        }


        if( document.form1.p_onoff[0].checked == true ) {
            document.form1.p_onoffgugun.value = document.form1.p_onoff[0].value;
        } else {
            document.form1.p_onoffgugun.value = document.form1.p_onoff[1].value;
        }


        if(document.form1.p_popup[0].checked == true){   //팝업설정시

            if(st_date > ed_date){
                alert("시작일이 종료일보다 큽니다.");
                return;
            }

            if (document.form1.p_popsize1.value == "") {
                alert("가로사이즈를 입력하여주십시오");
                document.form1.p_popsize1.focus();
                return;
            }

            if (!number_chk_noalert(document.form1.p_popsize1.value)) {
                alert('가로사이즈가 잘못입력되었습니다.');
                return;
            }

            if (document.form1.p_popsize2.value == "") {
                alert("세로사이즈를 입력하여주십시오");
                document.form1.p_popsize2.focus();
                return;
            }

            if (!number_chk_noalert(document.form1.p_popsize2.value)) {
                alert('세로사이즈가 잘못입력되었습니다.');
                return false;
            }

            if (document.form1.p_popposition1.value == "") {
                alert("X축 값을 입력하여주십시오");
                document.form1.p_popposition1.focus();
                return;
            }

            if (!number_chk_noalert(document.form1.p_popposition1.value)) {
                alert('X축 값이 잘못입력되었습니다.');
                return false;
            }

            if (document.form1.p_popposition2.value == "") {
                alert("Y축 값을 입력하여주십시오");
                document.form1.p_popposition2.focus();
                return;
            }

            if (!number_chk_noalert(document.form1.p_popposition2.value)) {
                alert('Y축 값이 잘못입력되었습니다.');
                return false;
            }


            if(document.form1.p_uselist.checked == true){
                document.form1.p_uselist.value = 'Y';
            }

            if(document.form1.p_useframe.checked == true){
                document.form1.p_useframe.value = 'Y';
            }
        }

        if ( confirm("공지사항을 등록하시겠습니까?") ) {
            document.form1.p_popsize1.disabled = false;
            document.form1.p_popsize2.disabled = false;

            document.form1.p_startdate.value  = st_date;
            document.form1.p_enddate.value    = ed_date;
            document.form1.p_pageno.value = 1;
            document.form1.p_searchtext.value = "";
            document.form1.p_search.value     = "";
            document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
            document.form1.p_process.value = "insert";

            document.form1.submit();
        }
    }

    /**
     * 목록 화면으로 이동
     */
    function fnGoToList() {
        document.form1.action = "/servlet/controller.homepage.NoticeAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }


    /**
     * 로긴구분 선택시
     */
    function whenLoginSelect(){
        if(document.form1.p_login[2].checked == true) { //로그인전
            if (grcodeFrame.grcodeform.p_grcode.length > 0) {
                for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
                    grcodeFrame.grcodeform.p_grcode[i].checked = false;
                    grcodeFrame.grcodeform.p_grcode[i].disabled = true;
                }
            } else {
                grcodeFrame.grcodeform.p_grcode.checked = false;
                grcodeFrame.grcodeform.p_grcode.disabled = true;
            }

        } else if(document.form1.p_login[0].checked == true || document.form1.p_login[1].checked == true) { //로그인후
            if (grcodeFrame.grcodeform.p_grcode.length > 0) {
                for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
                    grcodeFrame.grcodeform.p_grcode[i].checked = false;
                    grcodeFrame.grcodeform.p_grcode[i].disabled = false;
                }
            } else {
                grcodeFrame.grcodeform.p_grcode.checked = false;
                grcodeFrame.grcodeform.p_grcode.disabled = false;
            }
        }
    }

    //체크박스 체크
    function chkSelectedgrcode() {
        var selectedcnt = 0;

        document.form1.p_grocdecd.value = "";

        if (grcodeFrame.grcodeform.p_grcode.length > 0) {
            for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
                if (grcodeFrame.grcodeform.p_grcode[i].checked == true) {
                    selectedcnt++;
                    document.form1.p_grocdecd.value += grcodeFrame.grcodeform.p_grcode[i].value+",";
                }
            }
        } else {
            if (grcodeFrame.grcodeform.p_grcode.checked == true) {
                selectedcnt++;
                document.form1.p_grocdecd.value += grcodeFrame.grcodeform.p_grcode.value+",";
            }
        }

<%
        if(v_gadmin.equals("K")) {   //회사관리자가 일때
%>
        document.form1.p_grocdecd.value = "<%=box.getSession("tem_grcode")%>,";
        return 1;
<%
        } else {
%>
        return selectedcnt;
<%
        }
%>
    }


    //팝업여부선택시
    function whenPopupSelect(){
        if(document.form1.p_popup[0].checked == true) { //Y

            document.form1.p_popsize1.value = "686";
            document.form1.p_popsize2.value = "600";
            document.form1.p_popposition1.value = "0";
            document.form1.p_popposition2.value = "0";
            document.form1.p_sdate.value = "";
            document.form1.p_edate.value = "";

            document.form1.p_popposition1.disabled = false;
            document.form1.p_popposition2.disabled = false;
            document.form1.p_sdate.disabled = false;
            document.form1.p_edate.disabled = false;
            document.form1.p_useframe.disabled = false;
            document.form1.p_uselist.disabled = false;

            if(document.form1.p_useframe.checked == true) {
                document.form1.p_popsize1.disabled = false;
                document.form1.p_popsize2.disabled = false;
            } else {
                document.form1.p_popsize1.disabled = true;
                document.form1.p_popsize2.disabled = true;
            }

        } else if(document.form1.p_popup[1].checked == true){ //N
            document.form1.p_popsize1.value = "";
            document.form1.p_popsize2.value = "";
            document.form1.p_popposition1.value = "";
            document.form1.p_popposition2.value = "";
            document.form1.p_sdate.value = "";
            document.form1.p_edate.value = "";

            document.form1.p_popsize1.disabled = true;
            document.form1.p_popsize2.disabled = true;
            document.form1.p_popposition1.disabled = true;
            document.form1.p_popposition2.disabled = true;
            document.form1.p_sdate.disabled = true;
            document.form1.p_edate.disabled = true;
            document.form1.p_useframe.disabled = true;
            document.form1.p_uselist.disabled = true;

        }
    }


    function whenUseframe(){
        if(document.form1.p_useframe.checked == true){ //N
            document.form1.p_popsize1.disabled = false;
            document.form1.p_popsize2.disabled = false;
            document.form1.p_popsize1.value = "";
            document.form1.p_popsize2.value = "";
        } else {
            document.form1.p_popsize1.disabled = true;
            document.form1.p_popsize2.disabled = true;
            document.form1.p_popsize1.value = "686";
            document.form1.p_popsize2.value = "600";
        }
    }


    //팝업미리보기
    function popUpPreview(){
        var popupObj = document.getElementsByName("p_popup");
        var popupObjLen = popupObj.length;

        for(var i = 0 ; i < popupObjLen ; i ++) {
            if(popupObj[i].checked && popupObj[i].value == "N") {
                alert("팝업 설정을 하셔야 미리 보기가 가능합니다.");
                return;
            }
        }

        var width = document.form1.p_popsize1.value;
        var height = document.form1.p_popsize2.value;
        var left = document.form1.p_popposition1.value;
        var top = document.form1.p_popposition2.value;

        window.self.name = "CurrentPage";
        open_window("openPopUpView","",left,top,width,height, "no","no","no","yes","no");

        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        document.form1.p_content.value.replace("&","&amp;");

        document.form1.target = "openPopUpView";
        document.form1.action='/servlet/controller.homepage.EventAdminServlet';
        document.form1.p_process.value = 'popupView';
        document.form1.submit();
        document.form1.target = window.self.name;
    }

    //첨부관련 스크립트

    // Bean 클래스에 명시된 제한 파일 갯수
    var fileMax = <%=NoticeAdminBean.getFILE_LIMIT() %>;

    function addFile(){

        var div_obj = document.getElementsByName("divFile");
        var viewCnt = 0;

        for (var i = 0 ; i < div_obj.length ; i ++) {
            if ( div_obj[i].style.display != "none") {
                viewCnt++;
            }
        }

        if (parseInt(viewCnt) >= parseInt(fileMax)){
            alert("더 이상 첨부 하실수 없습니다. 최대 첨부수 : " + fileMax);
            return;
        }

        for (var i = 0 ; i <= fileMax ; i ++) {
            if ( div_obj[i].style.display == "none") {

                div_obj[i].style.display = "block";
                var fileObj     = document.getElementById("p_file"+i);
                break;
            }
        }
    }

    function delFile(p) {

        var div_obj = document.getElementsByName("divFile")[p-1];
        var fileObj = document.getElementById("p_file"+p);

        fileObj.select();
        document.selection.clear();

        div_obj.style.display   = "none";
    }

    function chooseonoff( obj ) {
        if( obj.checked ) {
            document.getElementById("divonoff").style.display = "block";
        } else {
            document.getElementById("divonoff").style.display = "none";
        }
    }


</script>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_pagesize"   value = "<%=v_pagesize %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_tabseq"     value = "<%=v_tabseq %>">
    <input type = "hidden" name="p_startdate"  value = "">
    <input type = "hidden" name="p_enddate"    value = "">
    <input type = "hidden" name="p_compcd"     value = "">
    <input type = "hidden" name="p_grocdecd"   value = "">
    <input type = "hidden" name="p_selcomp"    value = "<%=v_selcomp%>">
    <input type = "hidden" name="p_content"    value = "">
    <input type = "hidden" name="p_isAllvalue" value = "">
    <input type = "hidden" name="p_onoffgugun" value = "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><img src="/images/admin/homepage/homepage_title1.gif" border="0"></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br/><br/>

            <!-- 공지사항 관리 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>작성자</strong></td>
                    <td height="25" class="table_02_2"><%=s_username%></td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>등록일</strong></td>
                    <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>공지사항 구분</strong></td>
                    <td width="35%" height="25" class="table_02_2">
                        <select name="p_gubun" id="oGubun">
                            <option value="N">공지</option>
                            <option value="I">정보</option>
                            <option value="G">안내</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>전체공지여부</strong></td>
                    <td width="35%" height="25" class="table_02_2">
                        <input type="checkbox" name="p_isAll" value='Y'> 전체공지(상단걸기)사용시 선택하여주십시오.
                    </td>
                </tr>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>로그인유무</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_login" value='AL' onclick="javascript:whenLoginSelect()" checked>전체&nbsp;
                        <input type="radio" name="p_login" value='N'  onclick="javascript:whenLoginSelect()">로그인전&nbsp;
                        <input type="radio" name="p_login" value='Y'  onclick="javascript:whenLoginSelect()">로그인후&nbsp;
                    </td>
                </tr>
<%
    if(!v_gadmin.equals("K")) {   //회사관리자가 아닐때
%>
                <tr class="table_02_2">
                    <td height="25" class="table_title" ><strong>대상교육그룹</strong></td>
                    <td height="25" class="table_02_2">
                        <iframe name="grcodeFrame" width="850" height="160" frameborder="0" src="/servlet/controller.homepage.NoticeAdminServlet?p_process=grcodelist&gubun=admin"></iframe>
                    </td>
                </tr>
<%
    } else {
%>
                        <iframe name="grcodeFrame" width="0" height="0" frameborder="0" src="/servlet/controller.homepage.NoticeAdminServlet?p_process=grcodelist&gubun=admin"></iframe>
<%
    }
%>
                <tr id="divonoff">
                    <td height="25" class="table_title" ><strong>온/오프 구분</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_onoff" value='C'  checked>온라인&nbsp;
                        <input type="radio" name="p_onoff" value='S' >오프라인&nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>팝업여부</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_popup" value='Y' onclick="javascript:whenPopupSelect()">Y&nbsp;
                        <input type="radio" name="p_popup" value='N' onclick="javascript:whenPopupSelect()" checked>N&nbsp;
                    </td>
                </tr>
                <tr>
                    <td height="25" class="table_title"><b>팝업설정</b></td>
                    <td class="table_02_2"> 기간 :
                        <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='' disabled> ~
                        <input name="p_edate" id="p_edate" class="datepicker_input1" type="text" size="10" value='' disabled>
                        <br/>
                        사이즈 : 가로 <input type="text" size="5" name="p_popsize1" class="input" value="" maxlength="4" disabled> / 세로 <input type="text" size="5" name="p_popsize2" class="input" value="" maxlength="4" disabled>
                        <br/>
                        위치 : LEFT <input type="text" size="5" name="p_popposition1" class="input" value="" maxlength="3" disabled> / TOP <input type="text" size="5" name="p_popposition2" class="input" value="" maxlength="3" disabled>
                        <br/>
                        <input type="checkbox" name="p_useframe" value='Y' onclick="javascript:whenUseframe()" disabled>작성내용만보이기&nbsp;
                        <input type="checkbox" name="p_uselist"  value='Y' disabled>홈페이지리스트사용&nbsp;
                        <br/>
                        <a href="javascript:popUpPreview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>사용유무</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_use" value='Y' class="input" checked>사용&nbsp;
                        <input type="radio" name="p_use" value='N' class="input">미사용&nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>커뮤니티사용 여부</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="radio" name="p_community_yn" value='Y' class="input">사용&nbsp;
                        <input type="radio" name="p_community_yn" value='N' class="input" checked>미사용&nbsp;
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>제목</strong></td>
                    <td height="25" class="table_02_2">
                        <input type="text" name="p_adtitle" id="p_adtitle" size = 100 class="input" maxlength="80" onkeydown="fnTypingCheck('p_adtitle', 100)" onchange="fnTypingCheck('p_adtitle', 100)">
                    </td>
                </tr>
                <tr>
                    <td width="15%" class="table_title"><strong>내용</strong></td>
                    <td height="25" class="table_02_2">
                        <!-- 나모 액티브 스퀘어  시작 -->
                        <script language="javascript" src="/script/user_patch.js"></script>
                        <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
                        <script language="javascript">object_namoActivepatch('100%','500');</script>
                        <!-- 나모 액티브 스퀘어  종료 -->
                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>첨부파일</b></td>
                    <td id="sTd" width="81%" class="table_02_2">
<%
                  int    i_fileLimit         = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
%>
                        <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %>
                    </td>
                </tr>
                <tr>
                    <td width="16%" class="table_title"><b>모바일 앱 메시지 발송 여부</b></td>
                    <td id="sTd" width="81%" class="table_02_2">
                        <select id="oSendMessageYn" name="sendMessageYn">
                            <option value="N" selected="selected">아니오</option>
                            <option value="Y">예</option>
                        </select>
                        (예를 선택하고 공지사항을 저장하면 앱을 설치한 모든 학습자에게 알림 메시지가 전송됩니다.)
                    </td>
                </tr>

            </table>
            <!-- 공지사항 관리 끝 //-->

            <br/>
            <!-- 저장, 취소 버튼 시작 //-->
            <table width="11%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center"><a href="javascript:fnSaveNotice()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
                    <td align="center"><a href="javascript:fnGoToList()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
                </tr>
            </table>
            <!-- 저장, 취소 버튼 끝 //-->
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

<!--  웹필터 수정 -->  
<%@ include file="/webfilter/webfilter/inc/initCheckWebfilter.jsp"%>
<!--  웹필터 수정 -->

</body>
</html>
