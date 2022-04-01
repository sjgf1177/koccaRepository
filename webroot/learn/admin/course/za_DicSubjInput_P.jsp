<%
//**********************************************************
//  1. 제      목: 클래스 FileToDB
//  2. 프로그램명 : za_ClassFileToDB_P.jsp
//  3. 개      요: 클래스 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 2004/01/29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    
    String  ss_upperclass    = box.getString("s_upperclass");
    String  ss_middleclass   = box.getString("s_middleclass");
    String  ss_subjnm    	 = box.getString("s_subjnm");
    String  ss_subjcourse    = box.getString("s_subjcourse");
    
    String  v_mode    = box.getString("p_mode");

    String  v_luserid   = box.getSession("userid");

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

    ListSet ls = null;
    
    ArrayList  studentlist = new ArrayList();
    int i=0;

    DBConnectionManager connMgr = null;

    ResultSet rs = null;
           
    
    boolean v_isexception = false;
    String   v_return_msg  = "";
%>
<html>
<head>
<title>용어사전 EXCEL UPLOAD</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function golistPage() {
  document.form2.action = "/servlet/controller.course.DicSubjAdminServlet";
  document.form2.p_process.value = "ExcelUploadPage";
  document.form2.submit();
}

function whenClose() {
	opener.search();
	self.close();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="700" border="0" cellspacing="0" cellpadding="0">
<form name="form2" method="post" >
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="s_subjcourse"    value="<%=ss_subjcourse%>">
  <input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>">
  <input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>">
  <input type="hidden" name="s_subjnm"   value="<%=ss_subjnm%>">

</form>
  <tr>
    <td align="center" valign="top">
    <br>
    <br>
      <!----------------- 업로드 자료 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" height='35'><b><font size="3">과정명 : <%=ss_subjnm%></font></b></td>
        </tr>
        <tr>
          <tdheight='5'></td>
        </tr>
        
        
<%  try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

		System.out.println("시작1");
        // 엑셀데이타 읽기
        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.default")+v_newFileName));
        sheet = workbook.getSheet(0);
        String v_userid  = "";
        
        String v_words = "";
        String v_descs = "";
        String v_groups = "";

        
        int isOk = 0;
        int rowCnt = sheet.getRows();
        
        
        v_userid = box.getSession("userid");
        
        DicSubjAdminBean dicbean = new DicSubjAdminBean();
        

	        for (i=1; i < sheet.getRows() ; i++ ) {
	        
	            v_words = sheet.getCell(0,i).getContents();
	            
	            if (v_words.equals("")) {
	            	break;
	            }
	            
	            v_groups = sheet.getCell(1,i).getContents();
	            v_descs = sheet.getCell(2,i).getContents();
	            
				
				isOk = dicbean.insertDicExcel(ss_subjcourse, v_words, v_groups, v_descs, v_userid);
System.out.println("isOk = " + isOk);
	            
	            if (isOk == 0 ) { 
	            	v_isexception = true; 
	            }
	            

%>
        <tr>
          <td align="left">
            <%= "Row No. "+ (i+1) + "  / " + v_words + " / 결과 : "  %>
            <% if (isOk==1) out.println("성공"); else out.println("실패"); %>
          </td>
        </tr>
<%     }


 

	if(ls != null) { try { ls.close(); }catch (Exception e) {} }

%>




      </table>
      <!----------------- 업로드 자료 끝 ----------------->
<%      } catch (Exception e) {
                        v_isexception = true;
                        v_return_msg = e.getMessage();
%>
      <br>
      <!----------------- 에러메세지 시작 ----------------->
      <table  cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" ><font color='blue'><%=i+1%>번째에서 에러입니다.</font></td>
        </tr>
        <tr>
          <td align="left" >에러 메세지 : <%=e.getMessage()%></td>
        </tr>
      </table>
      
      
        <!----------------- 에러메세지 끝 ----------------->
<%      } finally { %>
	<br><br>
      <table  cellpadding="0" cellspacing="0">
        <tr align="left">
          <td  >

<%
						//첨부파일삭제
						FileManager.deleteFile(v_newFileName);
                
                        if(connMgr != null) { 
                                try { 
                                        if (v_isexception) {
                                         		out.print("실패한 자료가 있어 저장하지 못했습니다.");
                                                connMgr.rollback();
                                        } else {
                                        		out.print("성공적으로 데이타를 저장했습니다.");
                                        		
                                        		System.out.println("v_mode="+ v_mode);
                                        		if (v_mode.equals("previewFileToDB")) {
                                        			System.out.println("성공 Rollback");
                                        			connMgr.rollback();
                                        		} else {
                                        			System.out.println("성공 Commit");
                                        			connMgr.commit();
                                        		}
                                        }
                                        connMgr.setAutoCommit(true);
                                        connMgr.freeConnection(); 
                                } catch (Exception e10) {} 
                        }
%>
			</td>
        </tr>
      </table>

<%                        
        }
%>
      
      <br>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width=40%></td>
          <td align="center" ><a href="javascript:golistPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          <td align="center" ><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
		  <td width=40%></td>
        </tr>
      </table>
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
