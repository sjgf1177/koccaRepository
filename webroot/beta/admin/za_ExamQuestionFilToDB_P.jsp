<%
/**
 * file name : za_ExamQuestionFileToDB_P.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : �� ���� �ϰ�ó��
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.beta.*" %>
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
    String  v_lesson      = "";
    String  v_examtype      = "";
    String  v_examtext      = "";
    String  v_exptext      = "";
    String  v_levels      = "";
    int  v_selcount      = 0;

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

  //  DataBox dbox = (DataBox)request.getAttribute("ETestMasterData");

%>
<html>
<head>
<title>�� ���� - �ϰ�ó��</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function list() {
        document.form1.action = "/servlet/controller.beta.BPExamQuestionServlet";
        document.form1.p_process.value = "ExamQuestionFileToDB";
        document.form1.submit();
    }

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form ���� ----------------->
<form name="form1" method="post" >
    <input type="hidden" name="p_process"    value="">
    <input type="hidden" name="p_action"     value="<%=v_action%>">

    <input type="hidden" name="s_subj"       value="<%=v_subj%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title ���� ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_test.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title �� ----------------->

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

            sql1 = "select examnum from TZ_EXAM";
            sql1+=  " where subj = ? and examnum = ? and isdel = ? "; 
            pstmt1 = connMgr.prepareStatement(sql1);   
            
            sql2 =  "insert into TZ_EXAM(subj, examnum, lesson, examtype, examtext, exptext, levels, selcount, luserid, ldate, isdel) ";
            sql2+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            }

            for (i=1; i < sheet.getRows() ; i++ ) {
                v_count = i;
				cell      = sheet.getCell(0,i);      // ����
                v_subj  = cell.getContents();
                cell      = sheet.getCell(1,i);      // examnum
                v_examnum  = Integer.parseInt(cell.getContents());
                cell      = sheet.getCell(2,i);      // ����
                v_lesson  = cell.getContents();
                cell      = sheet.getCell(3,i);      // ����
                v_examtype  = cell.getContents();
                cell      = sheet.getCell(4,i);      // ����
                v_examtext  = cell.getContents();
                cell      = sheet.getCell(5,i);      // ����
                v_exptext  = cell.getContents();
                cell      = sheet.getCell(6,i);      // ����
                v_levels  = cell.getContents();
                cell      = sheet.getCell(7,i);      // ����
                v_selcount  = Integer.parseInt(cell.getContents());

                if (v_process.equals("insertFileToDB")) {

                pstmt1.setString( 1, v_subj);   
                pstmt1.setInt( 2, v_examnum);   				
                pstmt1.setString( 3, "N");   

                    try {
                        rs = pstmt1.executeQuery();
                        
                        if(!rs.next()) {     //     ���ſ� ��ϵ� examnum �� Ȯ���ϰ� ���� ��쿡�� ���          

                        pstmt2.setString( 1, v_subj);      
                        pstmt2.setInt( 2, v_examnum);       
                        pstmt2.setString( 3, v_lesson);       
                        pstmt2.setString( 4, v_examtype);
                        pstmt2.setString( 5, v_examtext);    
                        pstmt2.setString( 6, v_exptext);				
                        pstmt2.setString( 7, v_levels);
                        pstmt2.setInt( 8, v_selcount);
                        pstmt2.setString( 9, s_userid);
                        pstmt2.setString( 10, FormatDate.getDate("yyyyMMddHHmmss"));
                        pstmt2.setString( 11, "N");         
                            
                            isOk = pstmt2.executeUpdate();

                            cnt += isOk;
                            next++;
%>
          <tr> 
            <td align="left"> 
            <%= (i-1) + " ��°  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " + v_examtext  + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount%>
            </td>
          </tr>
<%                        
						} else {
%>
          <tr> 
            <td align="left"> 
            <%= (i-1) + " ��°  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " + v_examtext  + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount%>�� �̹� ��� �Ǿ� �ֽ��ϴ�.
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
        <!----------------- ���ε� �ڷ� �� ----------------->

<%

        } catch (Exception e) {
            connMgr.rollback();
%>
        <br>
        <!----------------- �����޼��� ���� ----------------->
        <table class="table_out" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="left" ><font color='red'><%=v_count-1%>��°���� �����Դϴ�.</font></td>
          </tr>
          <tr> 
            <td align="left" >���� �޼��� : <%=e.getMessage()%></td>
          </tr>
          
        </table>
        <!----------------- �����޼��� �� ----------------->
<%
        } finally {
            //÷�����ϻ���
            FileManager.deleteFile(conf.getProperty("dir.upload.exam")+v_realFileName);
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
        <br>
        <!----------------- ���, �̸����� ��ư ���� ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" ><a href="javascript:list()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- ���, �̸����� ��ư �� ----------------->

        <br>
        <br>
        <!----------------- ���ǻ��� ���� ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><b><font size="3">(���ǻ���)</font></b></td>
          </tr>
          <tr>
            <td height="8"></td>
          </tr>
        <tr>
          <td height="20">1. �������� ����� ���� �������� �����Ͽ� ����� ��.</td>
        </tr>
        <tr>
          <td height="20">2. �������� �ۼ��� [<b><font color="#003AEA">���</font></b>]�� ������ ���� ��.</td>
        </tr>
        <tr>
          <td height="20">3. <font color="red">���� ��� ���� �����ҽ� ġ������ ������ �߻��մϴ�.</font></td>
        </tr>
          <tr>
            <td height="20">4. ���� ������ ������ ��. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Etestmember.xls&p_realfile=Etestmember.xls'><b>��������</b></a>)</td>
          </tr>
        </table>
        <!----------------- ���ǻ��� �� ----------------->

      <br>
    </td>
  </tr>
</table>
</form>
<!----------------- form �� ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>