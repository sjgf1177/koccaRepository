<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "java.lang.*" %>

<%@ page import = "jxl.*" %>

<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<%@ page import = "com.dunet.common.util.StringUtil" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;
    PreparedStatement pstmt1 = null;
    StringBuffer strQuery = null;
    String sql1 = "";
    int isOk = 0;
    int index = 1;
    
    String seq = "";
    String subtitle = "";
    
	String v_realFileName = box.getRealFileName("p_file");
	String v_newFileName  = box.getNewFileName("p_file");
	
    String s_userid = box.getSession("userid");
    String v_action = box.getString("p_action");

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;
%>
<html>
<head>
<title>열린강좌 자막 엑셀 등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">


</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form 시작 ----------------->
<form name="form1" method="post" >
    <input type="hidden" name="p_process"    value="">
    <input type="hidden" name="p_action"     value="<%=v_action%>">

<%
        try {
            String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName(v_newFileName));
            StringBuffer sb1 = new StringBuffer(v_newFileName);
            sb1.reverse();
            int point=sb1.indexOf("\\");
            String revFilename=sb1.substring(0,point);
            sb1.delete(0,sb1.length());
            revFilename=sb1.append(revFilename).reverse().toString();
            
            String fullName = "";
            String currYear = FormatDate.getDate("yyyy")+"\\";
            
            fullName=conf.getProperty("dir.home").substring(0, conf.getProperty("dir.home").length()-1) +conf.getProperty("dir.upload." + v_dirKey)+currYear+revFilename;
            
            workbook = Workbook.getWorkbook(new File(fullName));            
            sheet = workbook.getSheet(0);

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            	
           	strQuery = new StringBuffer();
           	strQuery.append("UPDATE TZ_GOLDCLASS SET SUBTITLE = ?, LUSERID = ?, LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')	\n");
           	strQuery.append("WHERE SEQ = ?																						\n");
           	            	
            for(int i = 1; i < sheet.getRows(); i++ ) {
            	seq = sheet.getCell(0,i).getContents();
                subtitle = sheet.getCell(1,i).getContents();
                
                if ("".equals(seq)){
                	System.out.println("5.error break");
                    break;
                    
                }else{
                	pstmt1 = connMgr.prepareStatement(strQuery.toString());
	            	index = 1;
	            	pstmt1.setCharacterStream(index++, new StringReader(subtitle), subtitle.length());
	            	pstmt1.setString(index++, s_userid);
	            	pstmt1.setString(index++, seq);
	            	
	            	isOk = pstmt1.executeUpdate();
	            	
                }
            }
            
            

        } catch (Exception e) {
        	e.printStackTrace();
            connMgr.rollback();
            out.print("<script  type='text/javascript'>alert('파일추가 실패');window.close();</script>");
        } finally {
            connMgr.commit();
            //첨부파일삭제
            FileManager.deleteFile(v_newFileName);
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
    <script type="text/javascript">
        alert('저장이 완료 되었습니다.');
        window.close();
    </script>
</form>
<!----------------- form 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
