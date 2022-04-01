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
<%@ page contentType="text/html;charset=MS949" %>
<%//@page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.sql.*" %>
<%@ page import="jxl.Cell" %>
<%@ page import="jxl.Sheet" %>
<%@ page import="jxl.Workbook" %>
<%@ page import="com.credu.library.DBConnectionManager" %>
<%@ page import="com.credu.library.FileManager" %>
<%@ page import="com.credu.library.HashCipher" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.system.MemberAdminBean" %>
<%@ page import="com.dunet.common.util.EncryptUtil" %>
<%@ page import="com.dunet.common.util.Constants" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    DBConnectionManager connMgr = null;
    String v_process = box.getString("p_process");

    MemberAdminBean bean = null;
    Hashtable outputdata = null;

    int isOk1 = 0;
    int isOk2 = 0;
    int isOk3 = 0;

    String userid = "";
    String username = "";
    String pwd = "";
    String email = "";
    String handphone = "";
    String birth = "";
    String sex = "";
    String deptNm = "";
    String isMailing = "";
    String memberGubun = "";

    String relaFileName = box.getRealFileName("p_file");
    String newFileName = box.getNewFileName("p_file");

    int i = 0;
    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;
    Exception ex01 = null;
%>
<!doctype html>
<html>
<head>
<title>회원등록 FileToDB</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
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
            <br/>

<%
    try {

        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.home")+newFileName));
        sheet = workbook.getSheet(0);

        bean = new MemberAdminBean();

        String resultValue = "0";
        String resultText = "";

        String resultMessage = "";
        String grcode = box.getString("p_grcode");
        String grPrefix = bean.getidPrefix(grcode);
        String mobileUserid = "";

        int v_membyn = 0;
        int v_resbyn = 0;
        int errcnt = 0;
        int okcnt = 0;

        for (i = 1; i < sheet.getRows() ; i++ ) { // 첫 번째 Row는 각 항목에 대한 제목이 들어가 있음. 이에 따라 1부터 시작.

            userid = sheet.getCell( 0, i ).getContents();

            if ( !grPrefix.equals("") ) {
                if ( userid.indexOf(grPrefix) == 0 ) {
                    mobileUserid = userid;
                } else {
                    mobileUserid = grPrefix + userid;
                }
            }

            username = sheet.getCell( 1 ,i ).getContents();
            pwd = sheet.getCell( 2 ,i ).getContents();
            email = sheet.getCell( 3 ,i ).getContents();
            handphone = sheet.getCell( 4 ,i ).getContents();
            birth = sheet.getCell( 5 ,i ).getContents();
            sex = sheet.getCell( 6 ,i ).getContents();
            deptNm = sheet.getCell( 7 ,i ).getContents();
            isMailing = sheet.getCell( 8 ,i ).getContents();
            memberGubun = sheet.getCell( 9 ,i ).getContents();

            pwd = HashCipher.createHash(pwd);

            box.put("connMgr", connMgr);
            box.put("userid", userid);
            box.put("name", username);
            box.put("pwd", pwd);
            box.put("email", email);
            box.put("handphone", handphone);
            box.put("sex", sex);
            box.put("birth", birth);
            box.put("deptNm", deptNm);
            box.put("isMailing", isMailing);
            box.put("memberGubun", memberGubun);
            box.put("mobileUserid", mobileUserid);
            box.put("grcode", grcode);

            resultValue = "0";
            resultText = "";

            v_membyn = bean.isExitMember2(connMgr, userid, grcode);

            if(v_membyn != 0){
                resultValue = "1";
                resultText = "중복된 아이디가 있습니다.";

            } if ( !birth.equals("") && birth.length() != 8 ) {
                resultValue = "2";
                resultText = "생년월일이 8자리가 아닙니다.";

            } else if ( !sex.equals("") && ( Integer.valueOf(sex) < 0 || Integer.valueOf(sex) > 9 ) ) {
                resultValue = "3";
                resultText = "성별은 남:1/3, 여:2/4, 외국인:5/6으로 입력합니다.";

            } else {
                if (v_process.equals("insertMember")) {
                    outputdata = bean.insertMember(box);

                    isOk1 = Integer.valueOf((String)outputdata.get("p_errorcode")).intValue();

                    if(isOk1 > 0) {
                        resultText = "정상입력.";
                    } else {
                        ex01 = (Exception)outputdata.get("p_exception");
                        resultValue = "1";
                        resultText = "데이터입력중 오류가 발생했습니다.";
                    }
                }
            }

            if(!resultValue.equals("0")){
                errcnt++;
            } else {
                okcnt++;
            }
            resultMessage += "<tr><td class=\"table_02_1\">"+(i)+"</td><td class=\"table_02_1\">" + userid + "</td><td class=\"table_02_1\">" + username + "</td><td class=\"table_02_1\">"+resultText+"</td>";
        }   // end for

%>

            <!-- 결과정보 시작 //-->

            <table>
                <tr>
                    <td>
                        <font color="red">ERROR건수가 1건이상 발생하면 일괄처리가 자동취소됩니다.</font>
                    </td>
                </tr>
            </table>

            <table cellspacing="1" cellpadding="5" class="table_out">
                <colgroup>
                    <col style="width:10%;" />
                    <col style="width:15%;" />
                    <col style="width:15%;" />
                    <col style="width:*;" />
                </colgroup>
                <tr>
                    <td colspan="4" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" colspan="4">총 데이터 : <%=i-1%>건 / ERROR : <%=errcnt%>건</td>
                </tr>
                <tr>
                    <th class="table_title">줄번호</th>
                    <th class="table_title">아이디</th>
                    <th class="table_title">이름</th>
                    <th class="table_title">MESSAGE</th>
                </tr>
                    <%= resultMessage %>
                </tr>
            </table>
            <!-- 결과정보 시작 //-->
            <br/><br/>
<%
            if(v_process.equals("insertMember")){
                  if(errcnt == 0) {
                      connMgr.commit();
                  } else {
                      connMgr.rollback();
                  }
            } else connMgr.rollback();

        } catch (Exception ex) {

            // ErrorManager.getErrorStackTrace(ex);

%>
            <br/>
            <br/>
            <!-- 에러메세지 시작 //-->
            <table>
                <tr>
                    <td>
                        <font color="blue"><b>(엑셀양식 Error)<b></font>
                    </td>
                </tr>
                <tr>
                    <td>
                        <font color="red">엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <font color="red">&nbsp;&nbsp;--> 입력한 영역만 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br/>
                        &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br/>
                    </td>
                </tr>
            </table>
<%
        } finally {
            //첨부파일삭제
            FileManager.deleteFile( newFileName );
            if(connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {}
            }
        }
%>
            <br/>
            <!-- 등록, 미리보기 버튼 시작 //-->
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center" >
                        <a href="/servlet/controller.system.MemberAdminServlet?p_process=memberInsertPage"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;&nbsp;
                        <a href="#" onclick="javascript:self.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a>
                    </td>
                </tr>
            </table>
            <!-- 등록, 미리보기 버튼 끝 //-->
            <br/>
            <br/>
        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
