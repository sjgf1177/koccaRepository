<%
//**********************************************************
//  1. 제      목: PERSONAL SELECT
//  2. 프로그램명: za_PersonalSearch_I.jsp
//  3. 개      요: 개인조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=MS949"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.study.*"%>
<%@ page import="com.credu.common.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.system.*"%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    String v_process = box.getString("p_process");
    //DEFINED class&variable END

    String ss_company = box.getString("s_company");          //회사
    String s_gadmin = box.getSession("gadmin");
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
<!--
    function open_window(name, url, left, top, width, height, scrollbar, toolbar, menubar, statusbar, resizable) {
        toolbar_str = toolbar ? "yes" : "no";
        menubar_str = menubar ? "yes" : "no";
        statusbar_str = statusbar ? "yes" : "no";
        scrollbar_str = scrollbar ? "yes" : "no";
        resizable_str = resizable ? "yes" : "no";
        window.open(url, name, "left="+left+",top="+top+",width="+width+",height="+height+",toolbar="+toolbar_str+",menubar="+menubar_str+",status="+statusbar_str+",scrollbars="+scrollbar_str+",resizable="+resizable_str).focus();
    }

    function text_enter(e) {
        if (e.keyCode == "13"){
            whenSelection("go");
            e.keyCode = 0;
        }
    }

    function whenSelection(ss_action) {
        if (ss_action == "go") {
            top.ftop.setPam();
        }

        if (ss_action == "go"){
            if ( $("#oSearchtext").val() == "") {
                alert("검색어를 입력하세요!");
                $("#oSearchtext").focus();
                return;
            }

            var checkedIndex = $("input:radio[name=p_search]").index($("input:radio[name=p_search]:checked"));

            if( checkedIndex > 0 ){
                if (realsize(document.form1.p_searchtext.value) < 4) {
                    alert("성명, 이메일 또는 핸드폰번호를  정확히 입력하십시오.");
                    document.form1.p_searchtext.focus();
                    return;
                }

                if(checkedIndex == 2 || checkedIndex == 3) {
                    if($("select[name=p_memberChoice]").val() == ""){
                        alert("회원을 선택해 주십시오.");
                        $("select[name=p_memberChoice]").focus();
                        return;
                    }
                }

                // window.self.name = "winPersonalSearch";
                open_window("openWinView","","100","100","560","380","yes");
                document.form1.target = "openWinView";
                document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
                document.form1.p_process.value = "PersonalNameSelectList";
                document.form1.submit();
            } else {
                document.form1.target = "_self";
                document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
                document.form1.p_process.value = "PersonalSelect";
                document.form1.submit();
            }
        }
    }

    function companyChange() {
        document.form1.p_company.value = "01" + document.form1.company.value;
    }

    function fnSearchUserFromPopup( grcode, userid, process) {
        document.form1.p_process.value = process;
        document.form1.p_search[0].checked = true;
        document.form1.p_searchtext.value = grcode+userid;
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.target = "_self";
        document.form1.submit();
    }
//-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process"  value="<%=v_process%>" />
    <input type="hidden" name="p_company"  value="<%=ss_company%>" />
    <input type="hidden" name="s_grcode" value="<%=box.getSession("tem_grcode")%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/study/s_title04.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br/>
             
            <!-- 소제목 시작 //-->
             <table width="970" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
                    <td class=sub_title>ID/성명/주민번호 조회</td>
                </tr>
                <tr>
                    <td height=6></td>
                    <td height=6></td>
                </tr>
            </table>
            <!-- -소제목 끝-- //-->

            <table class="form_table_out" cellspacing="0" cellpadding="1">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">
                        <table class="form_table_bg" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="7"></td>
                            </tr>
                            <tr>
                                <td align="center" valign="middle"> ◈ ID/성명/주민번호 중 하나를 입력하세요.</td>
                            </tr>
                            <tr>
                                <td height="9"></td>
                            </tr>
                            <tr>
                                <td align="center" valign="middle">
                                    <table class="form_table" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <input type="radio" name="p_search" id="oRadioUserid" value="userid" checked /> <label for="oRadioUserid">ID</label>
                                                <input type="radio" name="p_search" id="oRadioName" value="name" /> <label for="oRadioName">성명</label>
                                                <!-- <input type="radio" name="p_search" id="oRadioResno" value="resno" /> <label for="oMemberChoice">주민번호</label> //-->
                                                <input type="radio" name="p_search" id="oRadioEmail" value="email" /> <label for="oRadioEmail">이메일</label>
                                                <input type="radio" name="p_search" id="oRadioHandphone" value="handphone" /> <label for="oRadioHandphone">핸드폰</label> &nbsp;
                                                <label for="oMemberChoice" style="font-weight:bold;">회원선택</label>
                                                <select name="p_memberChoice" id="oMemberChoice">
                                                    <option value="">-선택-</option>
                                                    <option value="general">일반회원</option>
                                                    <option value="asp">기업회원</option>
                                                </select>
                                                <input type="text" name="p_searchtext" id="oSearchtext" onkeypress="text_enter(event);" />
                                                <input type="text" name="tempElement" id="oTempElement" style="display:none;"/>
                                                &nbsp;&nbsp;
                                            </td>
                                            <td>
                                                &nbsp;<a href="javascript:whenSelection('go')"><img src="/images/admin/button/b_go.gif"  border="0"></a>
                                            </td>
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

            <br/>
        </td>
    </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
