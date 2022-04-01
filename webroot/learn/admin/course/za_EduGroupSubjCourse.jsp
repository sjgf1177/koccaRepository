<%
//**********************************************************
//  1. 제      목: 교육그룹과정조회
//  2. 프로그램명: za_Subject_L.jsp
//  3. 개      요: 교육그룹과정조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 10. 29
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.common.SelectSubjBean" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.StringManager" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    String v_grcode = box.getString("p_grcode");                //교육그룹코드
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass= box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String ss_action = box.getString("s_action");
    String v_grcodes = "";

    ArrayList list = null;
    if(ss_action.equals("go")){    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("SubjectList");
    }
    String v_middleclass = "";
    String v_isCourse = "";

    String v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<!doctype html>
<html>
<head>
<title>교육그룹관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script language="VBScript" src="/script/cresys_lib.vbs"></script>
<script type="text/javascript">
<!--

    // 셀렉트 선택후 검색
    function whenSelection(ss_action) {
        document.form1.s_action.value = ss_action;
        document.form1.p_process.value = "assignSubj";
        document.form1.submit();
    }

    //정렬
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.s_action.value = "go";
        document.form1.p_process.value = "assignSubj";
        document.form1.p_orderColumn.value = column;

        document.form1.submit();
    }

    /**
     * 선택한 과정들을 해당 그룹에 등록한다.
     */
    function fnInsertGrSubj(){
        var checkedLength = $("input:checkbox[name=p_checks]:checked").length;

        if(checkedLength == 0) {
            alert("선택한 과정이 없습니다.");
            return;
        } else if ( confirm("선택한 과정들을 교육그룹에 등록하시겠습니까?") ) {
            document.form1.action = "/servlet/controller.course.EduGroupServlet";
            document.form1.p_process.value = "assignSubjSave";
            document.form1.submit();
        }
    }

    $(function() {
        $("#oCheckAll").bind("click", function() {
            if ( $(this).is(":checked") ) {
                $("input:checkbox[name=p_checks]").attr("checked", true);
            } else {
                $("input:checkbox[name=p_checks]").attr("checked", false);
            }
        });

        $("input:checkbox[name=p_checks]").bind("click", function() {
            var allCnt = $("input:checkbox[name=p_checks]").length;
            var checkedCnt = $("input:checkbox[name=p_checks]:checked").length;

            if ( allCnt == checkedCnt ) {
                $("#oCheckAll").attr("checked", true);
            } else {
                $("#oCheckAll").attr("checked", false);
            }
        });

    });

//-->
</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">

<table width="700" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
        <!-- title 시작 //-->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
            <tr>
                <td><img src="/images/admin/system/unite_title08.gif" ></td>
                <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
            </tr>
        </table>
        <!-- title 끝 //-->
        <br/>
        <!-- form 시작 //-->
        <form name="form1" method="post" action="/servlet/controller.course.EduGroupServlet">
            <input type="hidden" name="p_grcode" value="<%=v_grcode%>" />
            <input type="hidden" name="p_process" value="" />
            <input type="hidden" name="p_subj" value="" />
            <input type="hidden" name="p_subjnm" value="" />
            <input type="hidden" name="s_action" value="" />
            <input type="hidden" name="p_orderColumn" />
            <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

        <table cellspacing="0" cellpadding="1" class="form_table_out">
            <tr>
                <td bgcolor="#C6C6C6" align="center">
                    <table cellspacing="0" cellpadding="0" class="form_table_bg">
                        <tr>
                            <td height="7" width="99%"></td>
                        </tr>
                        <tr>
                            <td align="center" width="99%" valign="middle">
                                <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                                    <tr>
                                        <td>
                                            <!-- 조건검색 시작 //-->
                                            <table cellspacing="0" cellpadding="0" width="99%">
                                                <tr>
                                                    <td>
                                                        <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                                            <tr>
                                                                <td width="65%">
                                                                    <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                                                                    <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                                                                    <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                                                                </td>
                                                                <td  width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') --></td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </table>
                                            <!---조건검색 끝 //-->
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
        <br/>
        <table cellspacing="1" cellpadding="5"  width="100%">
            <tr>
                <td align="right"><a href="javascript:fnInsertGrSubj();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            </tr>
        </table>
        <!-- 과정 관리 시작 //-->
        <table cellspacing="1" cellpadding="5" class="table_out" width="100%">
            <tr>
                <td colspan="7" class="table_top_line"></td>
            </tr>
            <tr>
                <td class="table_title" width="5%"><b>NO</b></td>
                <td class="table_title" width="15%"><a href="javascript:whenOrder('classname')" class="e">과정분류</a></td>
                <td class="table_title" width="10%"><a href="javascript:whenOrder('codenm')" class="e">교육구분</a></td>
                <td class="table_title" width="10%"><a href="javascript:whenOrder('isuse')" class="e">사용여부</a></td>
                <td class="table_title" width="10%"><a href="javascript:whenOrder('subj')" class="e">과정코드</a></td>
                <td class="table_title" width="45%"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
                <td class="table_title" width="5%"><input type="checkbox" id="oCheckAll" name="p_chkeckall" /></td>
            </tr>
<%
    if(ss_action.equals("go")) {    //go button 선택시만 list 출력
        if ( list != null && list.size() > 0 ) {
            for (int i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
                v_grcodes = dbox.getString("d_grcode");
                v_middleclass = dbox.getString("d_middleclass");

                if (v_middleclass.equals("COU")) {
                    v_isCourse = "Y";
                } else {
                    v_isCourse = "N";
                }
%>
            <tr>
                <td class="table_01"><%= String.valueOf(i+1) %></td>
                <td class="table_02_1"><%= dbox.getString("d_classname") %></td>
                <td class="table_02_1"><%= dbox.getString("d_codenm") %></td>
                <td class="table_02_1"><%= dbox.getString("d_isuse") %></td>
                <td class="table_02_1"><%= dbox.getString("d_subj") %></td>
                <td class="table_02_2"><%= dbox.getString("d_subjnm") %></td>
                <td class="table_02_2"><input id="oCheck<%= i %>" name="p_checks" type="checkbox" value="<%= dbox.getString("d_subj") %>|<%= v_isCourse %>" <%= v_grcodes.equals("") ? "" : "checked" %>></td>
            </tr>
<%
            }
        } else  if (list.size() == 0) {
            if (!StringManager.substring(box.getSession("gadmin"),0,1).equals("H")) {
%>
            <tr><td colspan="12" class="table_02_1">등록된 과정이 없습니다.</td></tr>
<%
            } else {
%>
            <tr><td colspan="9" class="table_02_1">등록된 과정이 없습니다.</td></tr>
<%
            }
        }
    }
%>
        </table>
        <!-- 과정 관리 끝 //-->

        </td>
    </tr>
    <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
