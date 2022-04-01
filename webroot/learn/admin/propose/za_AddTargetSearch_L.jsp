<%
/**
 * file name : za_OpenSubjseq_L.jsp
 * date     : 2004.03.18
 * programmer:
 * function : 과정차수에 지정한 교육주관정보 선택
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.propose.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process = box.getString("p_process");

    ArrayList list = null;

    String v_userid = "";
    String v_resno = "";
    String v_name = "";
    String v_sex = "";
    String v_email = "";
    String v_ismailing = "";
    String v_gpmnm = "";
    String v_resno1 = "";
    String v_resno2 = "";
    String v_isTarget = "";
    String v_ispropose = "";
    String v_officegbn = "";
    String v_lyocf = "";
    String v_sspnc = "";
    String v_membergubun = "";

    int i =   0;

    String ss_userid = box.getString("p_userid");    //ID
    String ss_name = box.getString("p_name");      //이름
    String ss_action = box.getString("p_action");
    String ss_fix_gubun = box.getStringDefault("s_fix_gubun","direct");

    String ss_grcode = box.getString("s_grcode");
    String ss_grseq = box.getString("s_grseq");
    String ss_gyear = box.getString("s_gyear");
    String ss_subjcourse = box.getString("s_subjcourse");
    String ss_mastercd = box.getString("s_mastercd");
    String ss_masternm = box.getString("s_masternm");
    String ss_subjseq = box.getString("s_subjseq");
    String ss_subjnm = box.getString("s_subjnm");
    String ss_grcodenm = box.getString("s_grcodenm");
    String ss_gyearnm = box.getString("s_gyearnm");
    String v_targetsubjseq = box.getString("p_targetsubjseq");
    String v_subjseqgr = box.getString("p_subjseqgr");

    box.put("s_grcode",ss_grcode);

    String v_isclosed = "";
    int v_studentlimit = 0;
    int v_propcnt = 0;
    String v_eduterm = "";
    String v_isonoff = "";

    DataBox dbox1 = null;

    dbox1 = (DataBox)request.getAttribute("subjinfo");
    if(dbox1 != null){
        v_studentlimit = dbox1.getInt("d_studentlimit");
        v_propcnt = dbox1.getInt("d_propcnt");
        v_isclosed = dbox1.getString("d_isclosed");
        v_eduterm = dbox1.getString("d_eduterm");
        v_isonoff = dbox1.getString("d_isonoff");
    }

    int v_addpossiblecnt = v_studentlimit - v_propcnt;

    if (ss_action.equals("go")) {   //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectList");
    }

    ArrayList list1 = (ArrayList)request.getAttribute("PossSeqList");
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript">
<!--

    //검색
    function whenSelection(p_action) {
            document.form1.p_action.value = p_action;
            ff = document.form1;

            if (p_action == 'go'& ff.p_userid.value == '' && ff.p_name.value == '' ) {
                alert("성명/USERID 중 검색조건을 한가지이상 입력해 주세요.");
                return;
            }

            document.form1.p_action.value = p_action;
            document.form1.target = "_self";
            document.form1.action='/servlet/controller.propose.StudentManagerServlet';
            document.form1.p_process.value = 'memSearch';
            document.form1.submit();
    }

    // 저장
    function fnSave(p_action) {

        if (chkSelected() < 1) {
            alert("대상자를 선택하세요.");
            return;
        }

        if ( confirm("선택한 인원을 대상자에 추가하시겠습니까?") ) {
            document.form1.p_action.value = p_action;
            document.form1.target = "_parent";
            document.form1.action="/servlet/controller.propose.StudentManagerServlet";
            document.form1.p_process.value = "stuedntinsert";
            document.form1.submit();

            opener.whenSelection("go");
        }
    }

    function whenBillSave() {
        var i_userid = document.all.i_userid;
        if(i_userid != null) {
            if(i_userid.length == <%=v_addpossiblecnt%>) {
                alert("선택하신 인원이 추가 가능인원보다 많습니다");
                return;
            }
            else if (i_userid.length > $("#billCount").val()*1) {
                alert("선택하신 인원이 결제 가능인원보다 많습니다");
                return;
            }
        }
        else {
            alert("선택된 회원이 없습니다.");
            return;
        }
        if ($("#tid").val() == "") {
            alert("선택된 계산서가 없습니다.");
            return;
        }

        document.form1.target = "_parent";
        document.form1.action='/servlet/controller.propose.StudentManagerServlet';
        document.form1.p_process.value = 'stuedntinsert';
        document.form1.submit();

        opener.whenSelection('go');
    }

    // 체크박스 선택 체크
    function chkSelected() {
        return $("input:checkbox[name=p_checks]:checked").length;
    }

    // 전체선택
    function whenAllSelect() {
        if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                document.form1.p_checks[i].checked = true;
                }
            } else {
                document.form1.p_checks.checked = true;
            }
        }
    }

    //전체선택해제
    function whenAllSelectCancel() {
        if(document.form1.all['p_checks'] == '[object]') {
            if (document.form1.p_checks.length > 0) {
                for (i=0; i<document.form1.p_checks.length; i++) {
                document.form1.p_checks[i].checked = false;
                }
            } else {
                document.form1.p_checks.checked = false;
            }
        }
    }

    // 선택 체크
    function whenCheck(index, totcnt){

        if(document.form1.p_isclosed.value == "Y") {
            errorExecute("수료처리되어 해당차수에는 추가할수 없습니다.",index, totcnt);
            document.form1.p_chkeckall.checked = false;
            return;
        }

        var i_currentselcnt = parseInt(document.form1.p_currentselcnt.value);
        var i_addpossiblecnt = parseInt(document.form1.p_addpossiblecnt.value);

        if(totcnt > 1){
            if(document.form1.p_checks[index].checked == true){
                document.form1.p_currentselcnt.value = i_currentselcnt + 1;
            }else{
                document.form1.p_currentselcnt.value = i_currentselcnt - 1;
            }
        }else{
            if(document.form1.p_checks.checked == true){
                document.form1.p_currentselcnt.value = i_currentselcnt + 1;
            }else{
                document.form1.p_currentselcnt.value = i_currentselcnt - 1;
            }
        }

        if(parseInt(document.form1.p_currentselcnt.value)> i_addpossiblecnt) {
            errorExecute("정원을 초과하게 됩니다.",index, totcnt);
            return;
        }
    }

    // 에러 메세지
    function errorExecute(alertmsg,index, totcnt){
        alert(alertmsg);
        if(totcnt > 1){
        document.form1.p_checks[index].checked = false;
        document.form1.p_currentselcnt.value = parseInt(document.form1.p_currentselcnt.value) - 1;
        }else{
        document.form1.p_checks.checked = false;
        document.form1.p_currentselcnt.value = parseInt(document.form1.p_currentselcnt.value) - 1;
        }

    }

    // 엔터키 처리
    function searchEnterKeyEvent(e){
     if (e.keyCode =='13'){ whenSelection('go');    }
    }

    //차수변경
    function whenChange(p_action){
        whenSelection('change');
    }


    //전체체크시 데이타 유효성 체크
    function chkeckall(){
        if(document.form1.p_isclosed.value == "Y"){
        alert("수료되어 해당차수에는 추가할수 없습니다.");
        document.form1.p_chkeckall.checked = false;
        return;
        }

        if(document.form1.p_chkeckall.checked){
        whenAllSelect();
        var addpossiblecnt = parseInt('<%=v_addpossiblecnt%>');
        if (chkSelected() > addpossiblecnt) {
            alert("선택된 대상을 추가하면 정원을 초과합니다.\n 다시 선택하여 주십시오.");
            document.form1.p_chkeckall.checked = false;
            whenAllSelectCancel();
            document.form1.p_currentselcnt.value = '0';
            return;
        }
        }
        else{
            whenAllSelectCancel();
        }

    }
    var rownum = 1;
    function addUser(data) {//{userid:userid,resno:resno,name:name,email:email,membergubun:membergubun,comptext:comptext,handphone:handphone};
        var i_userid = document.all.i_userid;
        var userCnt = 1;
        if(i_userid != null) {
            if(i_userid.length == undefined) {
                if(i_userid.value == data.userid) {
                    alert("이미 선택된 회원입니다.");
                    return;
                }
            }
            else {
                if(i_userid.length == <%=v_addpossiblecnt%>) {
                    alert("선택하신 인원이 추가 가능인원보다 많습니다");
                    return;
                }
                 for (i = 0; i < i_userid.length; i++) {
                    if(i_userid[i].value == data.userid) {
                        alert("이미 선택된 회원입니다.");
                        return;
                    }
                }
            }
            userCnt++;
        }

        var v_membergubun  = "개인";
        if(data.membergubun == "C") {v_membergubun = "기업";}
        else if(data.membergubun =="U") {v_membergubun = "대학";}

        var row = "";
        row += "<tr><td class='table_01'>" +rownum++ + "</td>";
        row += "<td class='table_02_1'>" + v_membergubun + "</td>";
        row += "<td class='table_02_1'><input type='hidden' name='i_userid' value='" + data.userid + "'/>" + data.userid + "</td>";
        row += "<td class='table_02_1'>" + data.name + "</td>";
        row += "<td class='table_02_1'>" + data.comptext + "</td>";
        $("#billneedtable").html($("#billneedtable").html()+row);
        $("#nowUserCnt").html("<b>현재인원</b> : " + userCnt + " 명");
    }

    // 해당과정 수료처리여부 체크
    function init(){
    <%if(v_isclosed.equals("Y")){%>
     alert("수료되어 해당차수에는 추가할수 없습니다");
    <%}%>
    }

    function searchUser() {//575,485
          var url = "/servlet/controller.library.SearchServlet?p_process=user&p_gubun=&p_key1=";
          open_window("",url,"100","70","595","455",'','','','true');
        }
    function searchTaxBill() {
          var url = "/servlet/controller.library.SearchServlet?p_process=taxBill&s_grcode=<%=ss_grcode%>&s_gyear=<%=ss_gyear%>&s_grseq=<%=ss_grseq%>&p_searchtext=";
          open_window("",url,"100","70","595","455",'','','','true');
    }
    function returnTaxBill(data) {
        $("#tid").val(data.d_tid);
        $("#billCount").val(data.d_usecnt);
        $("#taxBillName").html(data.d_goodname);
        $("#billAbleCnt").html("<b>결제가능인원</b> : "+data.d_usecnt+"명");
    }

    $(function() {
<%
    if(v_isclosed.equals("Y")) {
%>
        alert("수료되어 해당차수에는 추가할수 없습니다");
<%
    }
%>

        $("#oCheckAll").bind("click", function() {
            if ( $("#oCheckAll").is(":checked") ) {
                var possibleCnt = $("input:hidden[name=p_addpossiblecnt]").val();
                var allCnt = $("input:checkbox[name=p_checks]").length;

                if (allCnt > possibleCnt) {
                    alert("전체 인원을 선택하면 정원을 초과하게 됩니다.");
                    return;
                } else {
                    $("input:checkbox[name=p_checks]").attr("checked", true);
                }

            } else {
                $("input:checkbox[name=p_checks]").attr("checked", false);
            }

        });

        $("input:checkbox[name=p_checks]").bind("click", function() {
            var allCnt = $("input:checkbox[name=p_checks]").length;
            var checkedCnt = $("input:checkbox[name=p_checks]:checked").length;
            var possibleCnt = $("input:hidden[name=p_addpossiblecnt]").val();

            if ($(this).is(":checked") ) {
                if( $("input:hidden[name=p_isclosed]").val() == "Y") {
                    alert("수료처리되어 해당차수에는 추가할수 없습니다.");
                    $(this).attr("checked", false);
                    $("#oCheckAll").attr("checked", false);
                    return;
                }

                if ( checkedCnt > possibleCnt ) {
                    alert("정원을 초과하게 됩니다.");
                    $(this).attr("checked", false);
                    return;
                }

                if (allCnt == checkedCnt) {
                    $("#oCheckAll").attr("checked", true);
                } else {
                    $("#oCheckAll").attr("checked", false);
                }
            }
        });
    });
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<center>

<form name="form1" method="post" action="/servlet/controller.propose.ProposeWizardServlet">
<input type="hidden" name="p_process"   value="<%=v_process%>">
<input type="hidden" name="s_grcode"     value="<%=ss_grcode%>">
<input type="hidden" name="s_grseq"  value="<%=ss_grseq%>">
<input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">
<input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">
<input type="hidden" name="p_subjseqgr"  value="<%=v_subjseqgr%>">
<input type="hidden" name="s_subjnm"        value="<%=ss_subjnm%>">
<input type="hidden" name="s_grcodenm"   value="<%=ss_grcodenm%>">
<input type="hidden" name="s_gyearnm"       value="<%=ss_gyearnm%>">
<input type="hidden" name="p_action"        value="<%=ss_action%>">
<input type="hidden" name="p_isclosed"   value="<%=v_isclosed%>">
<input type="hidden" name="p_stulimit" value="<%=v_studentlimit%>">
<input type="hidden" name="p_propcnt" value="<%=v_propcnt%>">
<input type="hidden" name="p_addpossiblecnt"     value="<%=v_addpossiblecnt%>">
<input type="hidden" name="p_currentselcnt"  value="0">

<input type="hidden" name="s_company"    value="">


<table width="97%" border="0" cellspacing="0" cellpadding="0" height="550">
    <tr>
    <td align="center" valign="top">
        <!----------------- 교육차수 검색 title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/propose/p_title03.gif" ></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
        </table>
        <!-------------------타이틀 끝-------------------------->
        <br>
        <!--------------------  마스터과정정보 ------------------------>
        <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
            <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                <tr>
                <td height="7"></td>
                </tr>
                <tr>
                <td align="center" valign="middle">

                    <table width="97%" border="0" cellpadding="0" cellspacing="0" class="form_table">
                    <tr>
                    <td>
                        <b>교육그룹</b> : <%=ss_grcodenm%>
                    </td>
                    <td>
                        <b>연도</b> : <%=ss_gyear%>
                    </td>
                    </tr>
                    <tr><td height=4></td></tr>
                    <tr>
                    <td>
                        <b>과정/차수</b> : <%=ss_subjnm%> / <%=StringManager.cutZero(v_subjseqgr)%>차
                        <input type="hidden" name='s_subjseq' value="<%=ss_subjseq%>">
                    </td>
                    <td>
                        <b>추가가능인원</b> : <%if(v_studentlimit!= 1000000){out.print(v_addpossiblecnt+"명");}else{out.print("제한없음");}%>
                        &nbsp;&nbsp;&nbsp;&nbsp;<font id="billAbleCnt"></font>
                        &nbsp;&nbsp;&nbsp;&nbsp;<font id="nowUserCnt"></font>
                    </td>
                    </tr>
                    <tr><td height=4></td></tr>
                    <tr>
                        <td>
                        <b>계산서발급</b>
                            <input type="radio" name="billYn" value="true" onclick="$('#billnone').fadeOut(0);$('#billneed').fadeIn(400);$('#billneedbutton').fadeIn(100)"> Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="billYn" value="false" onclick="$('#billneedbutton').fadeOut(0);$('#billneed').fadeOut(0);$('#billnone').fadeIn(400)" checked> No
                        </td>
                        <td id="billneedbutton" style="display:none"><a href="javascript:searchUser()"><img alt="조회" src="/images/admin/button/btn_peopleplus.gif" border=0></a></td>
                    </tr>
                    </table>
                </td>
                </tr>
                <tr>
                <td height="7"></td>
                </tr>
            </table>
            </td>
        </tr>
        </table>
        <br>
        <!--------------------  마스터과정정보끝 ------------------------>
<div id="billneed" style="display:none">
        <!----------------- 테이블 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
        <thead>
        <tr> <td colspan="5" class="table_top_line"></td></tr>
        <tr>
            <td class="table_title" width="10%"><b>No</b></td>
            <td class="table_title" width="10%"><b>회원구분</b></td>
            <td class="table_title" width="10%"><b>ID</b></td>
            <td class="table_title" width="15%"><b>성명</b></td>
            <td class="table_title"><b>소속</b></td>
        </tr>
        </thead>
        <tbody id="billneedtable">
        </tbody>
        </table>
        <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
            <td class="table_02_1"><b>계산서검색</b>
                <input type="hidden" name="tid" id="tid"/>
                <input type="hidden" name="billCount" id="billCount"/>
                <font id="taxBillName"></font>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:searchTaxBill()"><img src="/images/admin/button/search3_butt.gif" border=0/></a></td>
        </tr>
        </table>
<br/><br/>
        <!----------------- 닫기 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td width=8></td>
            <td width=8></td>
            <td align="center" height="20"><a href="javascript:whenBillSave('<%=ss_action%>')"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;&nbsp;&nbsp;<a href="javascript:window.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
        <tr>
            <td height="3" colspan="5"></td>
        </tr>
        </table>
        <!----------------- 닫기 버튼 끝 ----------------->
        <br>
</div>
<div id="billnone">
<!-- 계산서 발급 안함. : 예전 기본 방식 -->
        <!----------------- 검색조건 입력박스 ----------------->
        <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
            <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
                <tr>
                <td height="7" width="99%">
                </td>
                </tr>
                <tr>
                <td align="center" width="99%" valign="middle">
                    <table width="99%" border="0" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                        <td>
                        성명&nbsp;:&nbsp;<input type="text" name="p_name"   value="<%=ss_name%>" size="10" onClick="javascript:searchEnterKeyEvent(event)">
                        </td>
                        <td>
                        ID&nbsp;:&nbsp;<input type="text" name="p_userid"   value="<%=ss_userid%>" size="10" onClick="javascript:searchEnterKeyEvent(event)">
                        </td>
                        <td></td>
                        <td>
                        &nbsp;&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                        </td>
                    </tr>
                    <tr><td height="7"></td></tr>

                    </table>
                </td>
                </tr>
                <tr>
                <td height="7" width="99%"></td>
                </tr>
            </table>
            </td>
        </tr>
        </table>
        <br>
        <!----------------- 검색조건 입력박스 끝 ----------------->

        <table width="90%" border="0" cellspacing="0" cellpadding="0">
        <tr><td></td></tr>
        </table>
        <!----------------- 테이블 시작 ----------------->
        <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> <td colspan="5" class="table_top_line"></td></tr>
        <tr>
            <td class="table_title" width="10%" align="center"><b>No</b></td>
            <td class="table_title" width="25%" align="center"><b>회원구분</b></td>
            <td class="table_title" width="30%" align="center"><b>ID</b></td>
            <td class="table_title" width="25%" align="center"><b>성명</b></td>
            <td class="table_title" width="10%" align="center"><b><input type="checkbox" name="p_chkeckall" id="oCheckAll"></b></td>
            <!-- <td class="table_title" width="10%" align="center"><b><input type="checkbox" name="p_chkeckall" id="oCheckAll" onClick="javascript:chkeckall()"></b></td> //-->
        </tr>

        <%
        int v_total =0;
        String v_officStatus = "";
        i = 0;
    if(list != null) {
        v_total = list.size();
        int k = 0;
        for(i = 0; i < v_total; i++) {
            DataBox dbox = (DataBox)list.get(i);
            v_userid     = dbox.getString("d_userid");
            v_resno     = dbox.getString("d_resno");
            v_name      = dbox.getString("d_name");
            v_sex       = dbox.getString("d_sex");
            v_email     = dbox.getString("d_email");
            v_membergubun= dbox.getString("d_membergubun");
            v_ismailing = dbox.getString("d_ismailing");
            v_isTarget  = dbox.getString("d_istarget");
            v_ispropose = dbox.getString("d_ispropose");
            v_sspnc     = dbox.getString("d_sspnc");                    //정직 *

            if(v_resno.length() ==13){
                v_resno1    = StringManager.substring(v_resno, 0,6);
                v_resno2    = StringManager.substring(v_resno, 6);
            }

            //회원구분
            if(v_membergubun.equals("C")) {v_membergubun = "기업";}
            else if(v_membergubun.equals("U")) {v_membergubun = "대학";}
            else    {v_membergubun = "개인";}
        %>
        <tr>
            <td class="table_01"><%=i+1%></td>
            <td class="table_02_1"><%=v_membergubun%></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><%=v_name%></td>
            <td class="table_02_1">
            <%
                if(!v_ispropose.equals("Y")){
            %>
                <input type="checkbox" name="p_checks" value="<%=v_userid%>" onClick="javascript:whenCheck('<%=k%>','<%=v_total%>');">
            <%
                k++;
                }else{out.println("완료");}
            %>
            </td>
            </tr>
<%
        }
    }
    if(i == 0) {
%>
         <tr><td align="center" bgcolor="#F7F7F7" height="50" colspan="10">등록된 내용이 없습니다</td></tr>

<%
    }
%>
        </table>
        <!----------------- 테이블 끝 ----------------->
<br/><br/>
        <!----------------- 닫기 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td width=8></td>
            <td width=8></td>
            <td align="center" height="20">
                <a href="javascript:fnSave('<%=ss_action%>')"><img src="/images/admin/button/btn_save.gif" border="0"></a>&nbsp;&nbsp;&nbsp;
                <a href="javascript:window.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
        <tr>
            <td height="3" colspan="5"></td>
        </tr>
        </table>
        <!----------------- 닫기 버튼 끝 ----------------->
        <br>
</div>
    </td>
    </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>

</center>
</body>
</html>
