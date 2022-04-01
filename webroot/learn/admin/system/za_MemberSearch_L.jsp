<%
//**********************************************************
//  1. 제      목: 회원 조회화면
//  2. 프로그램명: za_MemberSearch_L.jsp
//  3. 개      요: 회원 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 12
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.PageUtil" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.StringManager" %>
<%@ page import="com.credu.system.CodeConfigBean" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    String ss_grcode = box.getString("s_grcode");           //교육그룹
    String ss_membergubun = box.getString("s_membergubun");
    String ss_userid = box.getString("s_userid");
    String ss_username = box.getString("s_username");
    String ss_state = box.getString("s_state");          //회원상태
    String ss_ismailing = box.getString("s_ismailing");      //승인상태

    String ss_regstartdate = box.getString("s_regstartdate");      //가입일
    String ss_regenddate = box.getString("s_regenddate");

    String v_orderColumn = box.getString("p_orderColumn");
    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서

    int v_pageno = box.getInt("p_pageno");
    if (v_pageno == 0) {
        v_pageno = 1;
    }

    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    ArrayList list = null;

    if (box.getString("p_action").equals("go"))  {
        list = (ArrayList)request.getAttribute("memberList");
    }
%>
<!doctype html>
<html>
<head>
<title>인사DB검색</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<style type="text/css">
th {font-size:9pt; text-align:left;}
</style>
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src = "/script/jquery-1.3.2.min.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type="text/javascript">
<!--

    /**
     * key event 함수. 엔터키를 눌렀을 때, 검색 실행
     */
    function press_enter(e) {
        if (e.keyCode == "13") {
            whenSelection("go");
        }
    }

    /**
     * 사용자 조회 유효성 검사 및 조회
     */
    function whenSelection(v_action) {
        var searchForm = document.form2; // 검색 폼

        var regStartDate = searchForm.s_regstartdate.value; // 가입일 시작일자
        var regEndDate = searchForm.s_regenddate.value; // 가입일 종료일자

        if ( v_action == "go" ) {
            //교육그룹관리자는 교육그룹을 선택해야함
    <%
        if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) {
    %>
            if (searchForm.s_grcode.value == "ALL" || searchForm.s_grcode.value == "" ) {
                alert("교육그룹을 선택하세요.");
                return ;
            }
    <%
        }
    %>

            //이름 또는 id를 입력하지 않은 경우
            if (searchForm.s_userid.value == "" && searchForm.s_username.value == "" ) {
                //if ( document.form2.p_seltext.value=='' && document.form2.p_seldept.value=='') {
                    //if (document.form2.s_company.value == 'ALL') {
                    //  alert('회사를 선택하세요.');
                    //  return;
                    //}
                //}
            }
        }

        if ( (regStartDate != "" && regEndDate == "") || ( regStartDate == "" && regEndDate != "") ) {
            alert("가입일 항목은 시작일과 종료일을 모두 입력하거나 모두 입력하지 않아야 합니다.");
            return;
        }

        if ( regStartDate != "" && regStartDate.length < 8) {
            alert("가입일 시작일자 값이 충분하지 않습니다.");
            return;
        }
        if ( regEndDate != "" && regEndDate.length < 8) {
            alert("가입일 종료일자 값이 충분하지 않습니다.");
            return;
        }

        searchForm.target = "_self";
        searchForm.p_action.value = v_action;
        searchForm.p_process.value = "memberSearchPage";
        searchForm.action = "/servlet/controller.system.MemberSearchServlet";
        searchForm.submit();
    }

    // 리스트페이지
    function goPage(pageNum) {
        var searchForm = document.form2;
<%
    if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) {
%>
        if (searchForm.s_grcode.value == "ALL" || searchForm.s_grcode.value == "----" || searchForm.s_grcode.value == "" ) {
            alert("교육그룹을 선택하세요.");
            return ;
        }

<%
    }
%>
         searchForm.target = "_self";
         searchForm.p_pageno.value = pageNum;
         searchForm.p_action.value = "go";
         searchForm.p_process.value = "memberSearchPage";
         searchForm.submit();
    }

    // 회원 상세 정보 팝업
    function memberView(userid, grcode) {

//        window.self.name = "winSelectGrsubj";
        farwindow = window.open("", "openWinGrsubj", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 750, height = 550, top=0, left=0");
        document.form2.target = "openWinGrsubj";
        document.form2.action = "/servlet/controller.system.MemberSearchServlet";
        document.form2.p_process.value="selectMember";
        document.form2.p_userid.value = userid;
        document.form2.p_grcode.value = grcode;
        document.form2.submit();

        farwindow.window.focus();
//        document.form2.target = window.self.name;

    }

    // 엑셀 다운로드
    function whenExcel() {
    <%  if (!box.getString("p_action").equals("go")) { %>
                alert('먼저 조회해 주십시오');
                return;
    <% } else {
        if (list.size()==0) { %>

        alert("검색된 결과가 없습니다.");
        return;

    <%  }%>

    <% } %>

        ff = document.form2;

        var srchword = "";
        if (confirm("Excel Download시 날짜,사용자,검색조건,결과수 정보가 기록에 남습니다.\nExcel로 Download하시겠습니까?")) {
            //window.self.name = "CompleteMemberList";
            //open_window("openExcel","","10","10","900","660","no","no","no","no","yes");
            //document.form2.target = "openExcel";
            document.form2.target = "_self";
            document.form2.action='/servlet/controller.system.MemberSearchServlet';
            document.form2.p_process.value = 'MemberExcel';

            srchword = "회원구분 :" + ff.s_membergubun.options[ff.s_membergubun.selectedIndex].text + " /";

            if (ff.s_userid.value!='')
                srchword+= "ID :" + ff.s_userid.value + " /";

            if (ff.s_username.value!='')
                srchword+= "성명 :" + ff.s_username.value + " /";

            srchword+= "회원상태 :" + ff.s_state.options[ff.s_state.selectedIndex].text + " /";

            srchword+= "승인상태 :" + ff.s_ismailing.options[ff.s_ismailing.selectedIndex].text + " /";

            ff.p_srchword.value = srchword;


            document.form2.submit();
        }
    }

    /**
     * 로그 보기
     */
    function viewLog() {
        var r = $("[name='chkbox']:checked").length;

        if(r>1) {
            alert("체크는 하나만 하실수 있습니다.");
            return;
        } else if(r==0) {
            alert("로그보기 할 ID를 체크해 주십시오.");
            return;
        }

        document.form2.action = "/servlet/controller.system.MemberSearchServlet";
        // window.self.name = "winSrchMember";
        window.open("", "logView", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=810, height=400, top=0, left=0");
        document.form2.target = "logView";
        document.form2.p_process.value = "logView";

        document.form2.submit();

        //var url = "/servlet/controller.system.MemberSearchServlet?p_process=logView";
        //open_window("",url,"0","0","800","600");
        //window.open("", url, "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=840, height=620, top=0, left=0");
    }

    function inputNum(obj){
        if(event.keyCode >= 48 && event.keyCode <= 57) {
        } else {
            event.returnValue = false;
        }
    }

    // 회원 엑셀 등록
    function whenExcelUpload(type) {
         // window.self.name = "winInsertMember";
         open_window("openWin","","0","0","1020","670","","","","yes","no");
         document.form2.target = "openWin";
         document.form2.p_type.value = type;
         document.form2.p_process.value = "memberInsertPage";
         document.form2.action="/servlet/controller.system.MemberAdminServlet"
         document.form2.submit();
    }

    // 정렬
    function whenOrder(column) {
        if (document.form2.p_orderType.value == " asc") {
            document.form2.p_orderType.value = " desc";
        } else {
            document.form2.p_orderType.value = " asc";
        }

        document.form2.p_orderColumn.value = column;
        whenSelection("go");
    }

    function autoLogin(userid,pwd) {
        document.frmLogin.target = '_blank';
        document.frmLogin.p_userid.value = userid;
        document.frmLogin.p_pwd.value = pwd;
        document.frmLogin.p_topuserid.value = userid;
        document.frmLogin.p_toppwd.value = pwd;
        document.frmLogin.submit();
    }
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="frmLogin" method="post" action="/servlet/controller.homepage.LoginServlet">
    <input type="hidden" name="p_process" value="SSO" />
    <input type="hidden" name="p_originalLoginUserid" value="<%= box.getSession("userid") %>" />
    <input type="hidden" name="p_userid" value="" />
    <input type="hidden" name="p_pwd" value="" />
    <input type="hidden" name="p_topuserid" value="" />
    <input type="hidden" name="p_toppwd" value="" />
</form>

<form name="form2" method="post" action="/servlet/controller.system.MemberSearchServlet">
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="p_action" value="" />
    <input type="hidden" name="p_pageno" value="" />
    <input type="hidden" name="p_userid" value="" />
    <input type="hidden" name="p_srchword" value="" />
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>" />
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>" />
    <input type="hidden" name="p_grcode" value="" />
    <input type="hidden" name="p_type" value="" />
    <input type="hidden" name="s_grseq" value="ALL" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/system/unite_title18.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br />

            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg">
                            <tr>
                                <td height="7" width="99%"></td>
                            </tr>
                            <tr>
                                <td align="center" width="99%" valign="middle">
                                    <table width="98%" border="0" cellspacing="0" cellpadding="4">
                                        <colgroup>
                                            <col cols="8%" />
                                            <col cols="*" />
                                            <col cols="8%" />
                                            <col cols="10%" />
                                            <col cols="8%" />
                                            <col cols="10%" />
                                            <col cols="8%" />
                                            <col cols="10%" />
                                            <col cols="8%" />
                                        </colgroup>
                                        <tr>
                                            <th> 교육그룹 :</th>
                                            <td>
                                                <!-- 교육그룹 시작 -->
<%-- 교육그룹 관리자는 ALL 검색이 안되게 설정 --%>
<%
    if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) {
%>
                                                <font color="red">★</font>
                                                <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 선택 --" optionTitleValue="" type="sqlID" sqlID="selectBox.grcodeList" isLoad="true" selectedValue="<%= ss_grcode %>" />
<%
    } else {
%>
                                                <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 전체 --" optionTitleValue="ALL" type="sqlID" sqlID="selectBox.grcodeList" isLoad="true" selectedValue="<%= ss_grcode %>" />
<%
    }
%>
                                                <!-- 교육그룹 종료 -->
                                            </td>

                                            <th>회원구분 :</th>
                                            <td><%=CodeConfigBean.getCodeGubunSelect ("0029", "", 1, "s_membergubun", ss_membergubun, "", 2)%></td>
                                            <th>회원상태 :</th>
                                            <td>
                                                <select name="s_state" style="width:60">
                                                    <option value="ALL">ALL</option>
                                                    <option value="Y" <% if (ss_state.equals("Y")) out.print("selected");%>>정상</option>
                                                    <option value="N" <% if (ss_state.equals("N")) out.print("selected");%>>탈퇴</option>
                                                </select>
                                            </td>
                                            <th>수신여부 :</th>
                                            <td>
                                                <select name="s_ismailing" sylte="width:60">
                                                    <option value="ALL">ALL</option>
                                                    <option value="Y" <% if (ss_ismailing.equals("Y")) out.print("selected");%>>승인</option>
                                                    <option value="N" <% if (ss_ismailing.equals("N")) out.print("selected");%>>거부</option>
                                                </select>
                                            </td>
                                            <td rowspan="2">
                                                <a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif"  border="0" alt="조회"></a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>가입일 :</th>
                                            <td>
                                                <input type="text" name="s_regstartdate" value="<%=ss_regstartdate%>" class="input" maxlength="8" size="10" onkeyPress="inputNum(this)" style="IME-MODE: disabled" > ~
                                                <input type="text" name="s_regenddate" value="<%=ss_regenddate%>" class="input" maxlength="8" size="10" onkeyPress="inputNum(this)" style="IME-MODE: disabled" >
                                                ex)20110101 ~ 20110501
                                            </td>
                                            <th>ID :</th>
                                            <td>
                                                <input type="text" name="s_userid" class="input" maxlength="10" onkeypress="press_enter(event)" size="10" value="<%=ss_userid%>">
                                            </td>
                                            <th>성명 :</th>
                                            <td colspan="3">
                                                <input type="text" name="s_username" class="input" maxlength="20" onkeypress="press_enter(event)" size="20" value="<%=ss_username%>">
                                            </td>
                                        </tr>
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

<%
    if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) {
%>
            <!-- 엑셀출력, 회원엑셀등록, 로그보기 버튼 시작 //-->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" height="20">&nbsp;</td>
                    <td align="right">
                        <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>&nbsp;&nbsp;
                        <a href="javascript:whenExcelUpload('A')"><img src="/images/admin/button/btn_mem_exsel.gif"  border="0"></a>&nbsp;&nbsp;
                        <a href="javascript:viewLog();"><img src="/images/admin/button/btn_logview.gif"  border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- 엑셀출력, 회원엑셀등록, 로그보기 버튼 끝 //-->
<%
    }
%>
            <!-- 회원 목록 시작 //-->
            <table cellspacing="1" cellpadding="5" class="table_out">
                <colgroup>
                    <col cols="6%" />
                    <col cols="9%" />
                    <col cols="15%" />
                    <col cols="15%" />
                    <col cols="17%" />
                    <col cols="11%" />
                    <col cols="11%" />
                    <col cols="10%" />
                    <col cols="6%" />
                </colgroup>

                <tr>
                    <td colspan="9" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title"><b>NO</b></td>
                    <td class="table_title"><a href="javascript:whenOrder('comp')" class="e"><b>회원분류</b></a></td>
                    <td class="table_title"><a href="javascript:whenOrder('userid')" class="e"><b>아이디</b></a></td>
                    <td class="table_title"><a href="javascript:whenOrder('name')" class="e"><b>성명</b></a></td>
                    <!-- <td class="table_title"><a href="javascript:whenOrder('resno')" class="e"><b>주민번호</b></a></td> //-->
                    <td class="table_title"><a href="javascript:whenOrder('email')" class="e"><b>E-mail</b></a></td>
                    <!-- <td class="table_title" width="9%"><a href="javascript:whenOrder('pwd')" class="e"><b>비밀번호</b></a></td> //-->
                    <td class="table_title"><a href="javascript:whenOrder('indate')" class="e"><b>최초입력</b></a></td>
                    <td class="table_title"><a href="javascript:whenOrder('ldate')" class="e"><b>최종수정</b></a></td>
                    <td class="table_title"><a href="javascript:whenOrder('state')" class="e"><b>상태</b></a></td>
                    <td class="table_title">선택</td>
                </tr>
<%
        if (box.getString("p_action").equals("go"))  {

            for (int i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_dispnum = dbox.getInt("d_Dispnum");
                v_totalpage = dbox.getInt("d_Totalpagecount");

%>
                <tr>
                    <td class="table_01"><%=v_dispnum%></td>
                    <td class="table_02_1"><%=dbox.getString("d_membergubunnm")%></td>
                    <td class="table_02_1"><a href="javascript:memberView('<%=dbox.getString("d_userid")%>', '<%=dbox.getString("d_grcode")%>');"><%=dbox.getString("d_userid")%></a></td>
                    <td class="table_02_1"><%=dbox.getString("d_name")%> [<a href="javascript:autoLogin('<%=dbox.getString("d_userid")%>','<%=dbox.getString("d_pwd")%>')">Login</a>]</td>
                    <td class="table_02_1"><%=dbox.getString("d_email")%></td>
                    <!-- <td class="table_02_1">****</td> //-->
                    <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_ldate"),"yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=dbox.getString("d_state")%></td>
                    <td class="table_02_1"><input type="checkbox" name="chkbox" value="<%=dbox.getString("d_userid")%>"></td>
                </tr>
<%
            }

            if (list.size() == 0) {
%>
                <tr>
                    <td class="table_02_1" colspan="9">검색된 결과가 없습니다.</td>
                </tr>
<%
            }
        }
%>
            </table>
            <!-- 회원 목록 끝 //-->
            <br>

            <!-- 페이징 시작 //-->
            <table width="97%" class="table_02_1" height="26" border="0" cellpadding="5" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageList(v_totalpage, v_pageno, 10) %>
                    </td>
                </tr>
            </table>
            <!-- 페이징 끝 //-->
        </td>
    </tr>
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
