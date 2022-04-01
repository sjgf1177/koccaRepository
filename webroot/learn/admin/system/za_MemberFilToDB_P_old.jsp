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
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;
    String  v_process   = box.getString("p_process");

    MemberAdminBean bean = null;
    Hashtable outputdata = null;

    int isOk1 = 0;
    int isOk2 = 0;
    int isOk3 = 0;

    String v_userid      = "";
    String v_name        = "";
    String v_eng_name    = "";
    String v_email       = "";
    String v_resno       = "";
    String v_pwd         = "";
    String v_post1       = "";
    String v_post2       = "";
    String v_addr        = "";
    String v_addr2       = "";
    String v_comp_post1  = "";
    String v_comp_post2  = "";
    String v_comp_addr1  = "";
    String v_comp_addr2  = "";
    String v_hometel     = "";
    String v_handphone   = "";
    String v_comptel     = "";
    String v_comptext    = "";
    String v_degree      = "";
    String v_jikup       = "";
    String v_ismailing   = "";
    String v_islettering = "";
    String v_isopening   = "";
    String v_membergubun = "";
    String v_registgubun = "";

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");

    int     i=0;
    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;
    Exception ex01 = null;
%>
<html>
<head>
<title>회원등록 FileToDB</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/system/unite_title18.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>


<%  try {

        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.bulletin")+v_newFileName));
        sheet = workbook.getSheet(0);

        bean = new MemberAdminBean();

        String v_errvalue = "0";
        String v_errtxt = "";

        String v_errString = "";
        String v_okString = "";

        int v_membyn = 0;
        int errcnt = 0;
        int okcnt = 0;

        for (i=1; i < sheet.getRows() ; i++ ) {
            v_userid      = sheet.getCell( 0,i).getContents();
            v_name        = sheet.getCell( 1,i).getContents();
            v_eng_name    = sheet.getCell( 2,i).getContents();
            v_email       = sheet.getCell( 3,i).getContents();
            v_resno       = sheet.getCell( 4,i).getContents();
            v_pwd         = sheet.getCell( 5,i).getContents();
            v_post1       = sheet.getCell( 6,i).getContents();
            v_post2       = sheet.getCell( 7,i).getContents();
            v_addr        = sheet.getCell( 8,i).getContents();
            v_addr2       = sheet.getCell( 9,i).getContents();
            v_comp_post1  = sheet.getCell(10,i).getContents();
            v_comp_post2  = sheet.getCell(11,i).getContents();
            v_comp_addr1  = sheet.getCell(12,i).getContents();
            v_comp_addr2  = sheet.getCell(13,i).getContents();
            v_hometel     = sheet.getCell(14,i).getContents();
            v_handphone   = sheet.getCell(15,i).getContents();
            v_comptel     = sheet.getCell(16,i).getContents();
            v_comptext    = sheet.getCell(17,i).getContents();
            v_degree      = sheet.getCell(18,i).getContents();
            v_jikup       = sheet.getCell(19,i).getContents();
            v_ismailing   = sheet.getCell(20,i).getContents();
            v_islettering = sheet.getCell(21,i).getContents();
            v_isopening   = sheet.getCell(22,i).getContents();
            v_membergubun = sheet.getCell(23,i).getContents();
            v_registgubun = sheet.getCell(24,i).getContents();

            box.put("connMgr", connMgr);
            box.put("userid",      v_userid     );
            box.put("name",        v_name       );
            box.put("eng_name",    v_eng_name   );
            box.put("email",       v_email      );
            box.put("resno",       v_resno      );
            box.put("pwd",         v_pwd        );
            box.put("post1",       v_post1      );
            box.put("post2",       v_post2      );
            box.put("addr",        v_addr       );
            box.put("addr2",       v_addr2      );
            box.put("comp_post1",  v_comp_post1 );
            box.put("comp_post2",  v_comp_post2 );
            box.put("comp_addr1",  v_comp_addr1 );
            box.put("comp_addr2",  v_comp_addr2 );
            box.put("hometel",     v_hometel    );
            box.put("handphone",   v_handphone  );
            box.put("comptel",     v_comptel    );
            box.put("comptext",    v_comptext   );
            box.put("degree",      v_degree     );
            box.put("jikup",       v_jikup      );
            box.put("ismailing",   v_ismailing  );
            box.put("islettering", v_islettering);
            box.put("isopening",   v_isopening  );
            box.put("membergubun", v_membergubun);
            box.put("registgubun", v_registgubun);

            v_membyn = bean.isExitMember(v_userid);
            if(v_membyn == 0){
                outputdata = bean.insertMember(box);

                isOk1 = Integer.valueOf((String)outputdata.get("p_errorcode")).intValue();

                if(isOk1 > 0) {
                    v_errtxt = "정상입력.";
                } else {
                    ex01 = (Exception)outputdata.get("p_exception");
                    v_errvalue = "1";
                    v_errtxt = "데이터입력중 오류가 발생했습니다.";
                }
            }else{
                v_errvalue = "1";
                v_errtxt = "중복된 아이디가 있습니다.";
            }

            if(!v_errvalue.equals("0")){
                v_errString += "<tr><td class=table_02_1>"+(i)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + v_name + "</td><td class=table_02_1>" + v_resno + "</td><td class=table_02_1>"+v_errtxt+"</td>";
                errcnt++;
            } else {
                v_okString += "<tr><td class=table_02_1>"+(i)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + v_name + "</td><td class=table_02_1>" + v_resno + "</td><td class=table_02_1>"+v_errtxt+"</td>";
                okcnt++;
            }
        }   // end for

%>

      <!----------------- 결과정보 시작 ----------------->

      <table>
        <tr>
          <td>
            <font color=red>ERROR건수가 1건이상 발생하면 일괄처리가 자동취소됩니다.</font>
          </td>
        </tr>
      </table>

      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td colspan="15" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" colspan="5">총 데이터 : <%=i-1%>건 / ERROR : <%=errcnt%>건</td>
          </tr>
          <tr>
            <td class="table_title" width="10%">줄번호</td>
            <td class="table_title" width="15%">아이디</td>
            <td class="table_title" width="15%">이름</td>
            <td class="table_title" width="15%">주민등록번호</td>
            <td class="table_title">MESSAGE</td>
          </tr>
<%
            if(errcnt != 0){
             out.print(v_errString);
            }else{
             out.print(v_okString);
}%>
        </tr>
      </table>
      <!----------------- 결과정보 시작 ----------------->
      <br><br>
<%
            if(v_process.equals("insertMember")){
                  if(errcnt == 0) {
                      connMgr.commit();
                  } else {
                      connMgr.rollback();
                  }
            } else connMgr.rollback();

    } catch (Exception ex) {

    //ErrorManager.getErrorStackTrace(ex);

%>
      <br>
      <br>
      <!----------------- 에러메세지 시작 ----------------->
      <table>
        <tr>
          <td>
            <font color=blue><b>(엑셀양식 Error)<b></font>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>&nbsp;&nbsp;--> 입력한 영역만 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br>
            &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br>
          </td>
        </tr>
      </table>
<%
        } finally {
                //첨부파일삭제
            FileManager.deleteFile(conf.getProperty("dir.upload.bulletin")+v_newFileName);
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" >
          <a href="javascript:history.back();"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;&nbsp;
          <a href="javascript:self.close();"><img src="/images/admin/button/btn_close.gif" border="0"></a>
          </td>
        </tr>
      </table>
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>