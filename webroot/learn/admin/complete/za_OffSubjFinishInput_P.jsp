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
<%@ page import = "com.credu.complete.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    String  v_grcode    = box.getString("s_grcode");
    String  v_grname    = box.getString("s_grname");
    String  v_subjnm    = box.getString("s_subjnm");
    String  v_subjseqnm = box.getString("s_subjseqnm");
    String  v_gyear     = box.getString("s_gyear");
    String  v_grseq     = box.getString("s_grseq");
    String  v_offsubj   = box.getString("s_subjcourse");
    String  v_subjseq= box.getString("s_subjseq");
    String  v_luserid   = box.getSession("userid");
    String  v_imsisubjseq = ""; //1차를 0001로 변환

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
           
    FinishBean  finishbean  = new FinishBean();
    
    boolean v_isexception = false;
    String   v_return_msg  = "";
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function golistPage() {
  document.form2.action = "/servlet/controller.complete.FinishInputServlet";
  document.form2.p_process.value = "listPage";
  document.form2.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
<form name="form2" method="post" >
  <input type="hidden" name="p_process"    value="">
  <input type="hidden" name="s_grcode"     value="<%=v_grcode%>">
  <input type="hidden" name="s_gyear"      value="<%=v_gyear%>">
  <input type="hidden" name="s_grseq"      value="<%=v_grseq%>">
  <input type="hidden" name="s_subjcourse"    value="<%=v_offsubj%>">
  <input type="hidden" name="s_subjseq" value="<%=v_subjseq%>">
</form>
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title11.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 업로드 자료 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" height='35'><b><font size="3">교육그룹:<%=v_grname%> / 

          년도:<%=v_gyear%> / 

          과정:<%=v_subjnm%> /           

          차수:<%=v_subjseqnm%></font></b></td>
        </tr>
        <tr>
          <tdheight='5'></td>
        </tr>
        
        
<%  try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

		System.out.println("시작1");
        // 엑셀데이타 읽기
        System.out.println("v_newFileName = " + v_newFileName);
        System.out.println("file="+conf.getProperty("dir.upload.default")+v_newFileName);
        System.out.println("new file = " + new File(conf.getProperty("dir.upload.default")+v_newFileName) );
        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.default")+v_newFileName));
        System.out.println("시작2");
        sheet = workbook.getSheet(0);
        System.out.println("시작2");
        //String v_subjseq = "";
        String v_xlssubjseq = "";
        String v_userid  = "";
        System.out.println("시작3");
        double v_tstep  = 0;
        double v_ftest  = 0;
        double v_report = 0;
        double v_etc1   = 0;
        
        int isOk = 0;
        int rowCnt = sheet.getRows();
     
     	
     
     	Hashtable updateData = new Hashtable();
		System.out.println("시작4");

		//집합과정인지 판단.
		
		String sql = "";		

		sql = " select subj from tz_subj where subj = '" + v_offsubj + "' and isonoff = 'OFF' ";
        ls = connMgr.executeQuery(sql);
                System.out.println("시작5");
        if (ls.next()) { 

	        DecimalFormat df = new DecimalFormat("0000");        
	     System.out.println("시작6");
	        for (i=1; i < sheet.getRows() ; i++ ) {
	        
	         	System.out.println("자료 업로드 시작 ");
	            v_userid = sheet.getCell(0,i).getContents();
	            
	            if (v_userid.equals("")) {
	            	break;
	            }
	            System.out.println("자료 업로드 시작  2 ");
	            
	            //v_userid = v_userid.substring(1, v_userid.length());
	            
	            //System.out.println("엑셀값은 = " + v_userid.substring(0,1));
	            //System.out.println("길이=" + v_userid.length());
	            //System.out.println("제거=" + );
	            
	            v_tstep  = Double.valueOf(sheet.getCell(2,i).getContents()).doubleValue();
	            v_ftest  = Double.valueOf(sheet.getCell(3,i).getContents()).doubleValue();
	            v_report = Double.valueOf(sheet.getCell(4,i).getContents()).doubleValue();
	            v_etc1   = Double.valueOf(sheet.getCell(5,i).getContents()).doubleValue();
	            
	            System.out.println("자료 업로드 시작  3 ");
	            
	            updateData.put("p_userid",v_userid); // 사번
	            
	            System.out.println("자료 업로드 시작  4 ");
	            
	            //Excel 차수 가져오기
	            v_xlssubjseq = sheet.getCell(1,i).getContents();
	            
	            System.out.println("자료 업로드 시작  5 ");
	            
	            //SELECT에서 차수를 ALL로 한 경우
	            if (v_subjseq.equals("ALL")) {
	            
	            	v_imsisubjseq = sheet.getCell(1,i).getContents();  // 1차수

	
		    		v_subjseq = df.format(Integer.parseInt(v_imsisubjseq)); //0001차수
	
					System.out.println("엑셀차수= " + v_subjseq);
					
					//교육주관 과정차수를 과정차수로 변환
					v_subjseq = finishbean.selectGrSubjseq(connMgr,v_offsubj,v_gyear,v_subjseq,v_grcode);
					
					System.out.println("실제 과정차수 = " + v_subjseq);
					
	            } else {
	            	//과정차수를 선택한경우
	            	
	            	v_imsisubjseq = sheet.getCell(1,i).getContents();  // 1차수
	
		    		v_subjseq = df.format(Integer.parseInt(v_imsisubjseq)); //0001차수
	
					System.out.println("엑셀차수)= " + v_subjseq);
					
					//교육주관 과정차수를 과정차수로 변환
					v_subjseq = finishbean.selectGrSubjseq(connMgr,v_offsubj,v_gyear,v_subjseq,v_grcode);
					
	            	//선택한 과정 차수와 다르면 에러처리
	            	if (!v_subjseq.equals(v_subjseq)) {
	            		v_subjseq = "0000";
					}
	            	
            	
	            	System.out.println("ALL이 아니므로 Excel 차수 이용 = " + v_subjseq);
	            	
	            }
	            
	            updateData.put("p_subj",v_offsubj); // 과정코드
	            updateData.put("p_year",v_gyear); // 년도
	            updateData.put("p_subjseq",v_subjseq); // 차수
	            updateData.put("p_tstep", new Double(v_tstep)); // 출석률점수
	            updateData.put("p_ftest", new Double(v_ftest));  // 평가점수
	            updateData.put("p_report",new Double(v_report));  // 리포트점수
	            updateData.put("p_etc1",  new Double(v_etc1));  // 기타
	            
	            isOk = finishbean.updateOffStudentScore(connMgr, updateData);
	            
	            System.out.println("isOk = " + isOk);
	            
	            if (isOk == 0 ) { 
	            	v_isexception = true; 
	            }
	            
	            System.out.println("step 8");

%>
        <tr>
          <td align="left">
            <%= "Row No. "+ (i+1) + "  / " + v_userid + " / 차수 : " + StringManager.cutZero(v_xlssubjseq) + " / 출석률 : " + v_tstep + " / 평가 : " + v_ftest + " / 리포트 " + v_report + " / 기타 " + v_etc1 + " / 결과 : "  %>
            <% if (isOk==1) out.println("성공"); else out.println("실패"); %>
          </td>
        </tr>
<%     }


 } //off일때만  
  else { %>
        <tr>
          <td align="left">
            집합과정만 EXCEL FILE UPLOAD가 가능합니다.
          </td>
        </tr>

<%  } 


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
                                                connMgr.commit();
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
          <td align="center" ><a href="javascript:golistPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
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
