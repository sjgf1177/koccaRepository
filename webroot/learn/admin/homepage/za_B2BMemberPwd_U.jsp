<%
//**********************************************************
//  1. title : B2B 회원 비밀번호 일괄변경 화면
//  2. path & file : /learn/admin/homepage/za_B2BMemberPwd_U.jsp
//  3. desc : Poll 리스트
//  4. env : JDK 1.3
//  5. ver : 1.0
//  6. author : saderaser
//  7. create date : 2014-06-23
//  7. modify history :
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.DataBox" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope= "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String process = box.getString("process");
    String grCode = box.getString("grCode");

    String userId = "";     // 사용자 ID
    String userName = "";   // 사용자 이름
    String encPwd ="";      // 암화화된 현재 비밀번호
    String decPwd = "";     // 복호화된 현재 비밀번호
    String newEncPwd = "";  // 새로 등록할 암호화 비밀번호

    ArrayList list = (ArrayList)request.getAttribute("b2bMemberList");
%>
<!doctype html>
<html lang="ko">
<head>
<title>B2B 회원 비밀번호 일괄변경 화면 | 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript">
<!--

    /**
     * B2B 회원 목록 조회
     */
    function fnSelectB2BMemberList() {
        $("#oProcess").val( "mainPage" );

        $("#oB2BMemberSearchForm").attr("action", "/servlet/controller.homepage.MemberInfoAdminServlet");
        $("#oB2BMemberSearchForm").submit();
    }

    /**
     * B2B 회원 비밀번호 일괄 변경
     */
    function fnUpdateB2BMemberPwd() {
        var pwdArr = $("input[name=newEncPwd]");
        var procUserArr = $("input[name=procUser]");
        var validPwdCnt = 0;

        for ( var i = 0 ; i < procUserArr.length ; i++ ) {
            if( procUserArr[i].value == "Y" ) {
                ++validPwdCnt;
            }
        }

        if ( $("#oGrCode").val() != $("#oOrgGrCode").val() ) {
            alert("선택한 교육그룹과 조회된 교육그룹이 다르거나 아직 조회가 이루어지지 않은 상태입니다.\n조회를 먼저 실행해주세요.");
            return;
        } else  if ( validPwdCnt == 0 ) {
            alert("변경할 내용이 없습니다.");
            return;
        } else  if ( pwdArr.length != validPwdCnt ) {
            if ( confirm("일부 사용자는 변경할 수 없습니다.\n그래도 진행하시겠습니까?") ) {
                fnDoUpdate();
            } else {
                return;
            }
        } else {
            if ( confirm("회원 비밀번호를 일괄 변경하시겠습니까?") ) {
                fnDoUpdate();
            } else {
                return;
            }
        }
    }

    function fnDoUpdate() {
        $("#oProcess").val( "updateB2BMemberPassword" );

        $("#oB2BMemberSearchForm").attr("action", "/servlet/controller.homepage.MemberInfoAdminServlet");
        $("#oB2BMemberSearchForm").submit();
    }
    /**
     * 교육그룹 값 설정
     */
    function fnSetGrcode(pGrCode) {
        $("#oGrCode").val(pGrCode);
    }

    $(function() {
        $("#oSelGrCode").bind("change", function() {
            fnSetGrcode($(this).val());
        });
    });
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form id="oB2BMemberSearchForm" name="B2BMemberSearchForm" method="post">
    <input type="hidden" id="oProcess" name="process" value="" />
    <input type="hidden" id="oGrCode" name="grCode" value="<%= grCode %>" />
    <input type="hidden" id="oOrgGrCode" name="orgGrCode" value="<%= grCode %>" />

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td background="/images/admin/homepage/title_bg.gif" class="page_title"><h2>교육그룹 비밀번호 갱신</h2></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <table width="97%" height="26" border="0" cellpadding="8" cellspacing="0" style="border:1px solid;">
                <colgroup>
                    <col style="width:88px;" />
                    <col style="width:280px;" />
                    <col style="width:*;" />
                </colgroup>
                
                <tr>
                    <th style="font-family:돋움; font-size:13px;">
                        교육그룹
                    </th>
                    <td align="left" valign="middle">
                        <kocca:selectBox name="selGrCode" id="oSelGrCode" optionTitle="-- 선택 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= grCode %>" isLoad="true" />
                    </td>
                    <td align="left" valign="middle">
                        <a class="board_btn" onclick="fnSelectB2BMemberList();"><span style="font-size:12px;">조회</span></a>
<%
    if ( list != null && list.size() > 0 ) {
%>
                        <a class="board_btn2" onclick="fnUpdateB2BMemberPwd();"><span style="font-size:12px;">비밀번호 변경</span></a>

<%
    }
%>
                    </td>
                </tr>
            </table>
            <br/>

            <!-- B2B 회원 목록 start //-->
            <table class="table_out" cellspacing="1" cellpadding="4">
                <colgroup>
                    <col style="width:40px;" />
                    <col style="width:90px;" />
                    <col style="width:90px;" />
                    <col style="width:100px;" />
                    <col style="width:325px;" />
                    <col style="width:325px;" />
                </colgroup>

                <tr>
                    <th height="25" class="table_title"><b>번호</b></th>
                    <th class="table_title"><b>아이디</b></th>
                    <th class="table_title"><b>이름</b></th>
                    <th class="table_title"><b>현재비밀번호(복호화)</b></th>
                    <th class="table_title"><b>현재비밀번호(암호화)</b></th>
                    <th class="table_title"><b>신규비밀번호(암호화)</b></th>
                </tr>
<%
        DataBox dbox = null;

        if ( list != null && list.size() > 0 ) {
            for(int i = 0; i < list.size(); i++) {

                dbox = (DataBox)list.get(i);
%>

                <tr>
                    <td class="table_01" height="20"><%= (i + 1) %></td>
                    <td class="table_02_1">
                        <%= dbox.getString("d_userid") %>
                        <input type="hidden" id="oUserId<%= i %>" name="userId" value="<%= dbox.getString("d_userid") %>" />
                    </td>
                    <td class="table_02_1"><%= dbox.getString("d_name") %></td>
                    <td class="table_02_1" style="text-align:left; word-break:break-all;"><%= (dbox.getString("d_pwd").equals ( dbox.getString("d_newEncPwd") ) ) ? "<span style=\"color:#f00;\">처리완료</span>" : dbox.getString("d_decPwd") %></td>
                    <td class="table_02_1" style="text-align:left; word-break:break-all;"><%= dbox.getString("d_pwd") %></td>
                    <td class="table_02_1" style="text-align:left; word-break:break-all;">
                        <%= dbox.getString("d_newEncPwd") %>
                        <input type="hidden" id="oNewEncPwd<%= i %>" name="newEncPwd" value="<%= dbox.getString("d_newEncPwd") %>" />
                        <input type="hidden" id="oProcUser<%= i %>" name="procUser" value="<%= (dbox.getString("d_pwd").equals ( dbox.getString("d_newEncPwd") ) ) ? "N" : "Y" %>" />
                    </td>
                </tr>
<%
                dbox.clear();
            }
        }
%>
            </table>
        </td>
    </tr>
</table>

</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
