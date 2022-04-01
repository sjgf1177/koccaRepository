<%
/**
 * file name : za_ExamQuestionFileToDB_P2.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 평가 문제 일괄처리
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.cp.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    String sql1 = "";
    String sql2 = "";
    int isOk = 0;
    int cnt = 0;
    int next = 0;
    int i    = 0;

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");
    String s_userid = box.getSession("userid");

    String  v_subjnm    = box.getString("p_subjnm");

    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");

    String  v_subj      = "";
    int  v_examnum      = 0;
    int  v_selnum      = 0;
    String  v_seltext      = "";
    String  v_isanswer      = "";

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

  //  DataBox dbox = (DataBox)request.getAttribute("ETestMasterData");

%>
<html>
<head>
<title>평가 문제 - 일괄처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function list() {
        document.form1.action = "/servlet/controller.cp.BPExamQuestionServlet";
        document.form1.p_process.value = "ExamQuestionFileToDB";
        document.form1.submit();
    }

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form 시작 ----------------->
<form name="form1" method="post" >
    <input type="hidden" name="p_process"    value="">
    <input type="hidden" name="p_action"     value="<%=v_action%>">

    <input type="hidden" name="s_subj"       value="<%=v_subj%>">


<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/exam/e_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->

       <br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
<%
        int v_count = 2;

        try {
            String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName(v_newFileName));

            workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload." + v_dirKey), v_newFileName));

            sheet = workbook.getSheet(0);

           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

            if (v_process.equals("insertFileToDB")) {

            sql1 = "select examnum from TZ_EXAMSEL";
            sql1+=  " where subj = ? and examnum = ? and selnum = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            sql2 =  "insert into TZ_EXAMSEL(subj, examnum, selnum, seltext, isanswer, luserid, ldate) ";
            sql2+=  " values (?, ?, ?, ?, ?, ?, ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

             }

            for (i=2; i < sheet.getRows() ; i++ ) {
                v_count = i;
				cell      = sheet.getCell(0,i);      // subj
                v_subj  = cell.getContents();
                cell      = sheet.getCell(1,i);      // examnum
                v_examnum  = Integer.parseInt(cell.getContents());
                cell      = sheet.getCell(2,i);      // selnum
                v_selnum  = Integer.parseInt(cell.getContents());
                cell      = sheet.getCell(3,i);      // seltext
                v_seltext  = cell.getContents();
                cell      = sheet.getCell(4,i);      // isanswer
                v_isanswer  = cell.getContents();

                if (v_process.equals("previewFileToDB")) {
%>
        <tr>
          <td align="left">
            <%= (i-1) + " 번째  : " + v_subj  + ", " + v_examnum  + ", " + v_selnum  + ", " + v_seltext  + ", " + v_isanswer%>
          </td>
        </tr>
<%
	            }

                if (v_process.equals("insertFileToDB")) {

                pstmt1.setString( 1, v_subj);   
                pstmt1.setInt( 2, v_examnum);   				
                pstmt1.setInt( 3, v_selnum);   

                    try {
                        rs = pstmt1.executeQuery();
                        
                        if(!rs.next()) {     //     과거에 등록된 selnum 를 확인하고 없을 경우에만 등록          

                        pstmt2.setString( 1, v_subj);      
                        pstmt2.setInt( 2, v_examnum);       
                        pstmt2.setInt( 3, v_selnum);       
                        pstmt2.setString( 4, v_seltext);
                        pstmt2.setString( 5, v_isanswer);    
                        pstmt2.setString( 6, s_userid);
                        pstmt2.setString( 7, FormatDate.getDate("yyyyMMddHHmmss"));
                            
                            isOk = pstmt2.executeUpdate();
                            cnt += isOk;
                            next++;
                if(isOk > 0) {
%>
          <tr> 
            <td align="left"> 
            <%= (i-1) + " 번째  : " + v_subj  + ", " + v_examnum  + ", " + v_selnum  + ", " + v_seltext  + ", " + v_isanswer%>는 정상적으로 등록되었습니다.
            </td>
          </tr>
<%    
	            } else {
%>
          <tr> 
            <td align="left" ><font color='red'><%=i-1%>번째에서 에러입니다.</font></td>
          </tr>
          <tr> 
            <td align="left" >에러 메세지 : <%//=e.getMessage()%></td>
          </tr>
<%
                }
                    
						} else {
%>
          <tr> 
            <td align="left"> 
            <%= (i-1) + " 번째  : " + v_subj  + ", " + v_examnum  + ", " + v_selnum  + ", " + v_seltext  + ", " + v_isanswer%>는 이미 등록 되어 있습니다.
            </td>
          </tr>
<%   
						}
                    }catch(Exception e) {} 
                    finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

                }
            }

            if(next == cnt) connMgr.commit();
            else connMgr.rollback();
%>

        </table>
        <!----------------- 업로드 자료 끝 ----------------->

<%

        } catch (Exception e) {
            connMgr.rollback();
%>
        <br>
        <!----------------- 에러메세지 시작 ----------------->
        <table class="table_out" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="left" ><font color='red'><%=v_count-1%>번째에서 에러입니다.</font></td>
          </tr>
          <tr> 
            <td align="left" >에러 메세지 : <%=e.getMessage()%></td>
          </tr>
          
        </table>
        <!----------------- 에러메세지 끝 ----------------->
<%
        } finally {
            //첨부파일삭제
            FileManager.deleteFile(conf.getProperty("dir.upload.exam")+v_realFileName);
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
        <br>
        <!----------------- 등록, 미리보기 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" ><a href="javascript:list()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 등록, 미리보기 버튼 끝 ----------------->

        <br>
        <br>
        <!----------------- 주의사항 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><b><font size="3">(주의사항)</font></b></td>
          </tr>
          <tr>
            <td height="8"></td>
          </tr>
        <tr>
          <td height="20" class=dir_txt >1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >2. 엑셀파일 작성시 <font color="#A661D7">[<b>문제 파일</b>]과 [<b>문제보기 파일</b>]</font>을 나누어 만들 것.</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >3. <font color="#E60873">샘플 양식 폼을 변경할시 치명적인 오류가 발생합니다.</font></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_ExamQuestionSel.xls&p_realfile=Sample_ExamQuestionSel.xls'><b>문제보기 샘플파일</b></a>)</td>
        </tr>
        </table>
        <!----------------- 주의사항 끝 ----------------->

      <br>
    </td>
  </tr>
</table>
</form>
<!----------------- form 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
