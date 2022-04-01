<%
//**********************************************************
//  1. 제      목: 회원 FileToDB
//  2. 프로그램명 : za_MemberFileToDB_P.jsp
//  3. 개      요: 회원 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진 2006.1.20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    List<DataBox> list = (List)request.getAttribute("memberPasswordUpdateList");
%>
<html>
<head>
<title>회원 비밀번호 갱신 | 한국콘텐츠아카데미 관리자</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/system/unite_title18.gif" /></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br />

<%
    String userId = "";
    String msg = "";

    int okCnt = 0, failCnt = 0, notUserCnt = 0;
    int listSize = 0;
    int i = 0;
    try {

        listSize = list.size();
        DataBox dbox = new DataBox("updateMemberPassword");
%>
            <!-- 결과정보 시작 //-->
            <table>
                <tr>
                    <td><font color="red">비회원 정보가 1건 이상 혹은 ERROR건수가 1건 이상 존재할 경우 일괄처리가 취소됩니다.</font></td>
                </tr>
            </table>

            <table cellspacing="1" cellpadding="5" class="table_out">
                <tr>
                    <td colspan="3" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" width="10%">줄번호</td>
                    <td class="table_title" width="30%">아이디</td>
                    <td class="table_title">MESSAGE</td>
                </tr>
<%
        for (i = 0; i < listSize - 1; i++ ) {
            dbox = list.get(i);
            userId = dbox.getString("userId");
            msg = dbox.getString("msg");
            msg = ( msg.equals("Y") ? "비밀번호 변경 성공" : (msg.equals("F") ? "<font color='red'>비밀번호 변경 실패</font>" : "<font color='blue'>사용자 존재하지 않음</font>"));

%>
                <tr>
                    <td class="table_02_1"><%= i + 1 %></td>
                    <td class="table_02_1"><%= userId %></td>
                    <td class="table_02_1"><%= msg %></td>
                </tr>
<%
        }

        dbox = list.get(i);
        okCnt = dbox.getInt("okCnt");
        failCnt = dbox.getInt("failCnt");
        notUserCnt = dbox.getInt("notUserCnt");

%>
                <tr>
                    <td class="table_title" colspan="3">총 데이터: <%= listSize - 1 %>건 / 성공: <%= okCnt %>건 / 실패: <%= failCnt %> / 비회원: <%= notUserCnt %></td>
                </tr>

            </table>
            <br />
            <br />
<%
        if ( failCnt > 0 || notUserCnt > 0 ) {
%>

            <br />
            <br />
            <!-- 에러메세지 시작 //-->
            <table>
                <tr>
                    <td><font color="blue"><b>자료 처리 오류<b></font></td>
                </tr>
                <tr>
                    <td><font color="red">엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br /></td>
                </tr>
                <!--
                <tr>
                    <td>
                        <font color="red">&nbsp;&nbsp; 입력한 영역만 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br />
                        &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br />
                    </td>
                </tr>
                //-->
            </table>
<%
        }
    } catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex);
        out.println(ex.getMessage());
    }
%>
            <br />
            <br />

            <!-- 등록, 미리보기 버튼 시작 //-->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" >
                        <a href="/servlet/controller.system.MemberAdminServlet" title="목록으로 이동"><img src="/images/admin/button/btn_list.gif" alt="목록으로 이동" border="0" /></a>&nbsp;&nbsp;
                        <a href="#" onclick="javascript:self.close();" title="창닫기"><img src="/images/admin/button/btn_close.gif" alt="창닫기" border="0" /></a>
                    </td>
                </tr>
            </table>
            <!-- 등록, 미리보기 버튼 끝 //-->
            <br />
        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>