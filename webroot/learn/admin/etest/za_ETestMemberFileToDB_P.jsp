<%
/**
 * file name : za_ETestMemberFilToDB_P.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 온라인평가 대상자 일괄처리
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
<%@ page import = "com.credu.etest.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    ListSet ls = null;
    String sql1 = "";
    String sql2 = "";
    int isOk = 0;
    int cnt = 0;
    int next = 0;
    int i    = 0;

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");
    String s_userid = box.getSession("userid");

    String  ss_upperclass= box.getString("s_upperclass");
    String  ss_etestsubj      = box.getString("s_etestsubj");
    String  ss_gyear       = box.getString("s_gyear");
    String  ss_etestcode    = box.getString("s_etestcode");
    String  v_etestsubjnm = box.getString("p_etestsubjnm");
    String  v_etesttext = box.getString("p_etesttext");
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");
    int  v_maxjoin = box.getInt("p_maxjoin");

    String  v_userid = "";

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

%>
<html>
<head>
<title>온라인테스트 응시자화면</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function list() {
        opener.ReloadPage('go');
        document.form1.action = "/servlet/controller.etest.ETestMemberServlet";
        document.form1.p_process.value = "ETestMemberFileToDB";
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

    <input type="hidden" name="p_etestsubjnm"     value="<%=v_etestsubjnm%>">
    <input type="hidden" name="s_upperclass" value="<%=ss_upperclass%>">
    <input type="hidden" name="s_etestsubj"       value="<%=ss_etestsubj%>">
    <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
    <input type="hidden" name="s_etestcode"    value="<%=ss_etestcode%>">


<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title04.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->

        <br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
<%
        int v_count = 2;
        
        String v_errvalue = "";
        String v_errtxt = "";
        String v_errString = "";
        String v_okString = "";
        
        
        int errcnt = 0;

        try {
            String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName(v_newFileName));
            workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload." + v_dirKey), v_newFileName));
            
            sheet = workbook.getSheet(0);
			
			MainSubjSearchBean msbean = new MainSubjSearchBean();
			
			if ((sheet.getRows() - 1) <= v_maxjoin ) {         
			//--------------------------정원 체크------------------------------------------------------------------------
			
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

            if (v_process.equals("insertFileToDB")) {
				
                sql2 =  " insert into tz_etestmember (etestsubj, etestcode, year, userid, luserid, ldate) ";
                sql2+=  "                     values (?, ?, ?, ?, ?, ?)                            ";
                pstmt2 = connMgr.prepareStatement(sql2);
            }


            for (i=1; i < sheet.getRows() ; i++ ) {
    		     v_count = i;
                cell      = sheet.getCell(0,i);      // 아이디
                v_userid  = cell.getContents();
                
                //if (v_process.equals("insertFileToDB")) {
					//System.out.println("v_process=="+v_process);
				    
                    v_errvalue=msbean.isExitMember(v_userid, "", "", "");
                    //System.out.println("v_errvalue=="+v_errvalue);
                    //인사DB체크 if
					if(v_errvalue.equals("0")){
						
						
	                    try {
	                    
	                        //rs = pstmt1.executeQuery();
	                        sql1  =  " select userid from tz_etestmember                            ";
	                		sql1 +=  "  where etestsubj ="  + SQLString.Format(ss_etestsubj);
	                		sql1 +=  "  and year ="  + SQLString.Format(ss_gyear);
	                		sql1 +=  "  and etestcode ="  + SQLString.Format(ss_etestcode);
	                		sql1 +=  "  and userid ="  + SQLString.Format(v_userid);
	
	                        ls = connMgr.executeQuery(sql1);
	                        
	                        if(!ls.next()) {     
	                        	//     과거에 등록된 userid 를 확인하고 없을 경우에만 등록 
	                        	if (v_process.equals("insertFileToDB")) {
		                            pstmt2.setString( 1, ss_etestsubj);      
		                            pstmt2.setString( 2, ss_etestcode);       
		                            pstmt2.setString( 3, ss_gyear);    
		                            pstmt2.setString( 4, v_userid);
		                            pstmt2.setString( 5, s_userid);
		                            pstmt2.setString( 6, FormatDate.getDate("yyyyMMddHHmmss"));
		                            //pstmt2.setString( 7, "N");
		                            
		                            isOk = pstmt2.executeUpdate();
		                            cnt += isOk;
		                        }
	                            next++;
	                            
	                            v_errvalue ="0";
							}else {
								v_errvalue ="11"; //기등록에러
	                        }
							
	                    }catch(Exception e) {
		                    ErrorManager.getErrorStackTrace(e);
		            		throw new Exception(e.getMessage());	
	                    } 
	                    finally { 
	                    	//if (rs != null) { try { rs.close(); } catch (Exception e) {} 
	                    	if(ls != null) { try { ls.close(); } catch (Exception e) {} }
	                    }           

					}//인사DB체크 if end
                //}
                
                // Message 출력---------------------------------------------------------------------------------------------
                v_errtxt = isGetErrtxt(v_errvalue);
                // Message 출력-----end----------------------------------------------------------------------------------------
                if(!v_errvalue.equals("0")){
	              v_errString += "<tr><td class=table_02_1>"+i+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>"+v_errtxt+"</td>";
	              errcnt++;
	            }
	            else{
	              v_okString  += "<tr><td class=table_02_1>"+i+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>"+v_errtxt+"</td>";
	            }
            }


			if(v_process.equals("insertFileToDB")){
				//rollback 처리
	            //if(next == cnt) {
	            if(errcnt == 0) {
					connMgr.commit();
	
	            } else {
					connMgr.rollback();
	            }
	        }
            
			//--------------------------정원 체크----end-------------------------------------------------------------------
			
			} else {                  //정원 초과
			
%>
          <tr> 
            <td align="left"><%=v_maxjoin%>명만 추가 가능합니다.  
            </td>
          </tr>
<%
			}
%>

        </table>
        <!----------------- 업로드 자료 끝 ----------------->
        <!----------------- 결과정보 시작 ----------------->

      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td class="table_title" colspan="8">ERROR : <%=errcnt%>건</td>
          </tr>
          <tr>
            <td class="table_title" width="10%">줄번호</td>
            <td class="table_title" width="20%">ID</td>
            <td class="table_title" >MESSAGE</td>
          </tr>
          <%
            if(errcnt != 0){
             out.print(v_errString);
            }else{
          %>
           <tr>
            <td class="table_02_1" colspan="8">검색된 Error가 없습니다.</td>
          </tr>
           <%}%>
        </tr>
      </table> 
      <!----------------- 결과 정보 출력 끝 ----------------->
      <BR><BR>
      <!----------------- 결과정보 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td class="table_title" colspan="8">정상입력</td>
          </tr>
          <tr>
            <td class="table_title" width="10%">줄번호</td>
            <td class="table_title" width="20%">ID</td>
            <td class="table_title" >MESSAGE</td>
          </tr>
          <%=v_okString%>        
      </table> 
      <!----------------- 결과 정보 출력 끝 ----------------->

<%

        } catch (Exception e) {
            connMgr.rollback();
%>
        <!--br>
        <table class="table_out" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="left" ><font color='red'><%=v_count-1%>번째에서 에러입니다.</font></td>
          </tr>
          <tr> 
            <td align="left" >에러 메세지 : <%=e.getMessage()%></td>
          </tr>
          
        </table-->

<%
        } finally {
            //첨부파일삭제
            FileManager.deleteFile(conf.getProperty("dir.upload.default")+v_realFileName);
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
            <td align="center" >
            <a href="javascript:list()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
            
            </td>
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
          <td height="20">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것.</td>
        </tr>
        <tr>
          <td height="20">2. 엑셀파일 작성시 [<b><font color="#003AEA">ID</font></b>]의 순서로 만들 것.</td>
        </tr>
        <tr>
          <td height="20">3. <font color="red">샘플 양식 폼을 변경할시 치명적인 오류가 발생합니다.</font></td>
        </tr>
          <tr>
            <td height="20">4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_Etestmember.xls&p_realfile=Sample_Etestmember.xls'><b>샘플파일</b></a>)</td>
          </tr>
          <tr>
          <td height="20">5. <font color="red">ERROR건수가 1건이상 발생하면 일괄등록이 자동취소됩니다.</font></td>
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
<%!  
   /**
    Member 존재 여부 체크
    @return String Return
    */
     public String isGetErrtxt(String errvalue) throws Exception {
     	
     	String errtxt = "";
     	
     	if(errvalue.equals("0")){
		   errtxt = "정상.";
		 }		
		 else if(errvalue.equals("1")){
		   errtxt = "인사DB에 존재하지 않습니다.";   
		 }
		 else if(errvalue.equals("2")){
		   errtxt = "퇴직자입니다.";   //
		 }
		 else if(errvalue.equals("3")){
		   errtxt = "휴직자입니다.";   //
		 }
		 else if(errvalue.equals("4")){
		   errtxt = "정직자입니다.";
		 }
		 else if(errvalue.equals("11")){
		   errtxt = "이미 대상자로 등록 되어 있습니다.";
		 }

        return errtxt;
    }
%> 
