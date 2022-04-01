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
    
    String subjlesson = "";
    String module = "";
    String subj = "";
    String position = "";
    String techid = "";
    String subtitle = "";
    
    String subjCode = "";
    
    
    //차시정보에 자막 등록 추가_20151201
    String v_excelInsert = box.getString("p_excelInsert");

	String v_realFileName = box.getRealFileName("p_file");
	String v_newFileName  = box.getNewFileName("p_file");
	
    String s_userid = box.getSession("userid");
    String v_action = box.getString("p_action");
    String v_subj = box.getString("s_subj");

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;
%>
<html>
<head>
<title>콘텐츠 일괄등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function list() {
        document.form1.action = "/servlet/controller.contents.MasterFormServlet";
        document.form1.p_process.value = "listPage";
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
            if("subjlesson".equals(v_excelInsert)){
	            fullName=conf.getProperty("dir.home")+conf.getProperty("dir.upload." + v_dirKey)+revFilename;
	            
            }else if("subtitle".equals(v_excelInsert)){
	            fullName=conf.getProperty("dir.home").substring(0, conf.getProperty("dir.home").length()-1) +conf.getProperty("dir.upload." + v_dirKey)+currYear+revFilename;
            }
            
            workbook = Workbook.getWorkbook(new File(fullName));            
            sheet = workbook.getSheet(0);

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            
            //차시 등록일 경우
            if("subjlesson".equals(v_excelInsert)){
            	
	            sql1 =  "insert into tz_subjlesson(subj,module,lesson,sdesc,types,owner,starting,isbranch,luserid,userid,ldate)";
	            sql1+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	            pstmt1 = connMgr.prepareStatement(sql1);
	
	            for (int i=1; i < sheet.getRows(); i++ ) {
					subjlesson      = sheet.getCell(0,i).getContents();
	                module      = sheet.getCell(1,i).getContents();
	                subj      = sheet.getCell(2,i).getContents();
	                position  = sheet.getCell(3,i).getContents();
	                techid   = sheet.getCell(4,i).getContents();
	
	                if (subjlesson.equals(""))
	                    break;
	
	                String sn="";
	                if(i<10)
	                    sn="00"+i;
	                else
	                    sn="0"+i;
	
	                subjCode="";
	
	                if(!subj.equals(""))
	                    subjCode=subj;
	                else
	                    subjCode=v_subj;
	
	                String contentPath="";
	
	                if(position.equals(""))
	                    contentPath = "/contents/"+subjCode+"/docs/"+sn.substring(1,3)+"/index.html";
	                else
	                    contentPath=position;
	
	                pstmt1.setString( 1, subjCode);
	                pstmt1.setString( 2, module);
	                pstmt1.setString( 3, sn);
	                pstmt1.setString( 4, subjlesson);
	                pstmt1.setString( 5, "1001");
	                pstmt1.setString( 6, v_subj);
	                pstmt1.setString( 7, contentPath);
	                pstmt1.setString( 8, "N");
	                pstmt1.setString( 9, s_userid);
	                pstmt1.setString( 10, techid);
	                pstmt1.setString( 11, FormatDate.getDate("yyyyMMddHHmmss"));
	
	                isOk = pstmt1.executeUpdate();
				 }
            	
	            
	        //자막 등록일 경우
            }else if("subtitle".equals(v_excelInsert)){
            	
            	strQuery = new StringBuffer();
            	strQuery.append("UPDATE TZ_SUBJLESSON SET SUBTITLE = ?					\n");
            	strQuery.append("WHERE SUBJ = ? AND MODULE = ? AND LESSON = ?	\n");
            	            	
	            for(int i = 1; i < sheet.getRows(); i++ ) {
	            	subj = sheet.getCell(0,i).getContents();
	                module = sheet.getCell(1,i).getContents();
	                subjlesson = sheet.getCell(2,i).getContents();
// 	                subtitle = StringUtil.removeTag(sheet.getCell(3,i).getContents());
	                subtitle = sheet.getCell(3,i).getContents();
	                
	                
	                if (subjlesson.equals("") || subj.equals("") || module.equals("")){
	                	System.out.println("5.error break");
	                    break;
	                    
	                }else{
	                	pstmt1 = connMgr.prepareStatement(strQuery.toString());
		            	index = 1;
		            	pstmt1.setCharacterStream(index++, new StringReader(subtitle), subtitle.length());
		            	pstmt1.setString(index++, subj);
		            	pstmt1.setString(index++, module);
		            	pstmt1.setString(index++, subjlesson);
		            	
		            	isOk = pstmt1.executeUpdate();
		            	
	                }
	            }
            }
            
            

        } catch (Exception e) {
        	e.printStackTrace();
            connMgr.rollback();
            out.print("<script  type='text/javascript'>alert('파일추가 실패');window.close();</script>");
        } finally {
            connMgr.commit();
            //첨부파일삭제
//             FileManager.deleteFile(conf.getProperty("dir.upload.exam")+v_realFileName);
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
