<%
//**********************************************************
//  1. 제      목: 클래스 FileToDB
//  2. 프로그램명 : za_cpeduResultinput.jsp
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
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.propose.MainSubjSearchBean" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.cp.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
	String v_searchtext = box.getString("p_searchtext");
	int v_pageno = box.getInt("p_pageno");
	String v_grcode = box.getString("s_grcode");
	String v_gyear = box.getString("s_gyear");
	String v_year2 = "";
	String v_subj2 = "";
	String v_subjseqgr = "";
	String v_cp = box.getString("p_cp");
	String v_subj     = box.getString("p_subj");
    String v_subjseq     = box.getString("p_subjseq");
    String ss_grcode     = box.getString("s_grcode");
    String s_grcodename = box.getString("s_grcodename");
    String s_gryearname = box.getString("s_gryearname");
    String s_grseqname  = box.getString("s_grseqname");

    String v_subjseq2 = "";
    String v_grseq = "";
    String v_name = "";
    String v_realFileName = "";
    String v_newFileName = "";
    String v_userid = "";
    String v_notgraduetc = "";
	String v_comp = "";
	String v_jikwi = "";
	String v_jikwinm = "";
	String v_errmsg = "";	//에러메세지
	int v_student = 0;	//수강생수
	int v_isErr = 1;	//에러여부
	int v_isErr1 = 1;	//오류여부1
	int v_isErr2 = 1;	//오류여부2
	int v_isErr3 = 1;	//오류여부3
	int v_isErr4 = 1;	//오류여부4
    int isOK1 = 0;
    int isOK2 = 0;
    int isOK3 = 0;
    int isOK4 = 1;
    int isOK5 = 1;
    int isOK6 = 1;
    String isOK_MSG = "";
    int isOK_Final = 1;
    
	String v_score1 = "";
	String v_score2 = "";
	String v_ispass = "";
	String v_ispass1 = "";
	String v_ispass2 = "";

    String  v_luserid   = box.getSession("userid");

    String  v_year = box.getString("p_year");

	if(v_process.equals("insertFileToDB")){

		//실제 db등록시
	    v_realFileName = box.getString("p_realFile");
	    v_newFileName  = box.getString("p_newFile");
	}
	else{

		//미리보기시
	    v_realFileName = box.getRealFileName("p_file");
	    v_newFileName  = box.getNewFileName("p_file");
	}


	
    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

    CPStoldData   data = null;
    ArrayList  studentlist = new ArrayList();
    int i=0;

    DBConnectionManager connMgr = null;
    PreparedStatement pstmt_select = null;
    PreparedStatement pstmt_update = null;
    PreparedStatement pstmt_insert = null;
    PreparedStatement pstmt_update_student = null;
    PreparedStatement pstmt_select_member  = null;
    ResultSet rs = null;
    
    com.credu.cp.CPSubjseqData subjseqdata = null;
    
    //CPProposeBean proposebean = new CPProposeBean();
			
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
function goback() {
	history.back()
  //document.form2.action = "/servlet/controller.complete.FinishInputServlet";
  //document.form2.p_process.value = "listPage";
  //document.form2.submit();
}

function action(p_process) {  
  document.form2.action = "/servlet/controller.cp.CPResultServlet";
  document.form2.p_process.value = p_process;                              //insertFileToDB,previewFileToDB
  document.form2.p_action.value = "go";
  document.form2.submit();
}
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
<form name="form2" method="post">
	<input type="hidden" name="p_process" value="">
	<input type="hidden" name="p_action" value="">
	<input type="hidden" name="p_searchtext" value="<%=v_searchtext%>">
	<input type="hidden" name="p_pageno" value="<%=v_pageno%>">
	<input type="hidden" name="s_grcode" value="<%=v_grcode%>">
	<input type="hidden" name="s_gyear" value="<%=v_gyear%>">
	<input type="hidden" name="p_year" value="<%=v_year%>">
	<input type="hidden" name="p_cp" value="<%=v_cp%>">
	<input type="hidden" name="p_subj" value="<%=v_subj%>">
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
	
  <input type="hidden" name="p_grcode"     value="<%=v_grcode%>">
  <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
  <input type="hidden" name="p_grseq"      value="">
  <input type="hidden" name="p_offsubj"    value="<%=v_subj%>">
  <input type="hidden" name="p_offsubjseq" value="<%=v_subjseq%>">
  
  <input type="hidden" name="p_realFile" value="<%=v_realFileName%>">
  <input type="hidden" name="p_newFile" value="<%=v_newFileName%>">
  
</form>
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/cp/cp_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>	
      <!----------------- title 끝 ----------------->
      <br>
      <br>
      <!----------------- 업로드 자료 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" height='35'>
 	        <table width="600" cellpadding="1" cellspacing="0" class="form_table_out">
              <tr> 
                <td>
                  <table width="600" cellpadding="0" cellspacing="0" class="form_table_bg">
                    <tr> 
                      <td height="7"></td>
                    </tr>
                    <tr> 
                      <td align=center> 
                        <table width=97% border="0" cellspacing="0" cellpadding="3">
                          <tr> 
                            <td height=2></td>
                            <td width="165" height=8></td>
                            <td width="164" height=8></td>
                            <td width="93" height=8></td>
                            <td width="119" height=8></td>
                            <td height=8></td>
                          </tr>
                          <tr> 
                            <td width=20></td>
                            <td colspan="4">교육그룹 :  <strong><font color="4F9AE6"></font><%=s_grcodename%></strong></td>
                            <td width=19></td>
                          </tr>
                          <tr> 
                            <td width=20></td>
                            <td colspan="4">교육년도 :  <strong><font color="4F9AE6"><%=s_gryearname%></font></strong></td>
                            <td width=19></td>
                          </tr>
                          <tr> 
                            <td></td>
                            <td colspan="4">교육차수 : <strong><font color="4F9AE6"></font><%=s_grseqname%></strong></td>
                            <td></td>
                          </tr>
                          <%
                          	if(v_process.equals("insertFileToDB")){
                          		//실제 db등록시
                          %>
                          <tr> 
                            <td></td>
                            <td colspan="4" height=35><font color="4F9AE6">등록처리가 완료되었습니다.</font></td>
                            <td></td>
                          </tr>
                          <%
                          	}
                          	else{
                          		//미리보기시
                          %>
                          <tr> 
                            <td></td>
                            <td colspan="4" height=35><font color="4F9AE6">아래의 결과 데이터를 확인하시고 하단의 등록버튼을 클릭하면 저장됩니다.</font></td>
                            <td></td>
                          </tr>
                          <%
                          	}
                          %>
                          <tr> 
                            <td height=2></td>
                            <td width="165" height=8></td>
                            <td width="164" height=8></td>
                            <td width="93" height=8></td>
                            <td width="119" height=8></td>
                            <td height=8></td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td height="7"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <br>
<%  try {
      connMgr = new DBConnectionManager();
      connMgr.setAutoCommit(false);

      // 엑셀데이타 읽기
      workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.default")+v_newFileName));
      sheet = workbook.getSheet(0);
      FinishBean fbean = new FinishBean();
      CPFinishBean finishbean = new CPFinishBean();
	  CPResultBean cpResult   = new CPResultBean();
	  MainSubjSearchBean msbean = new MainSubjSearchBean();
      DecimalFormat df        = new DecimalFormat("0000");
      Hashtable insertData        = new Hashtable();
      
      int isOk = 0;
      int tempisOk = 1;
      int errcnt = 0;
      String v_inputtime = FormatDate.getDate("yyyyMMddHHmmss");
      String v_errvalue = "0";
      String v_errtxt = "";
      String v_errString = "";
      String v_okString  = "";



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
      //out.println("v_inputtime====>"+v_inputtime);
      for (i=2; i < sheet.getRows() ; i++ ) {
        //out.println("iiii===>>====>"+i);
        //out.println(i);
        //교육년도 및 차수가 맞는지 확인한다.
        v_year2    = sheet.getCell(0,i).getContents();
        v_subj2    = sheet.getCell(3,i).getContents();
        v_subjseq2 = sheet.getCell(4,i).getContents();
        v_userid   = sheet.getCell(5,i).getContents();
        v_subjseqgr = v_subjseq2;
        
        if(v_subjseq2.equals("")){
          out.println("<font color=red><b>엑셀양식Error</b><br><b>차수ALL선택시에는 엑셀 입력양식에 차수를 입력하여야 합니다.</b></font>");
          return;
        }
        
        v_subjseq2 = df.format(Integer.parseInt(v_subjseq2));
        v_subjseq = fbean.selectGrSubjseq(connMgr, v_subj2, v_year2, v_subjseq2, ss_grcode);
        v_errvalue=msbean.isExitSubj(connMgr, v_subj2);

        if(v_errvalue.equals("0")){
          v_errvalue=msbean.isExitSeq(connMgr, ss_grcode, v_year2, v_subj2, v_subjseq);
          
            //중복여부
            if(v_errvalue.equals("0")){
              v_errvalue=msbean.isExitDupli(connMgr, v_inputtime, v_userid, v_subj2, v_year2, v_subjseq);
            }

            if(v_errvalue.equals("0")){
              
              if(v_errvalue.equals("0")){
                v_errvalue=msbean.isExitMember(connMgr, v_userid);
                //out.println(v_errvalue);
              }
              
              //수강신청 테이블 검색
              if(v_errvalue.equals("0")){
                v_errvalue=msbean.isProposeExit(connMgr, v_userid, v_subj2, v_year2, v_subjseq);
              }
          }
        }
        //out.println("v_errvalue+===============>>>>>>>"+v_errvalue);
        // Message 출력
        v_errtxt = msbean.isGetErrtxt(v_errvalue);

        if(!v_errvalue.equals("0")){
          v_errString+="<tr bgcolor=#ffffff align=center>";
          v_errString+="<td>"+(i+1)+"</td>";
          v_errString+="<td >"+v_errtxt+"</td>";
          v_errString+="<td >"+v_subj2+"</td>";
          v_errString+="<td >"+v_subjseqgr+"</td>";
          v_errString+="<td >" + v_userid + "</td>";
          v_errString+="<td >" + sheet.getCell(6,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(7,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(8,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(9,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(10,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(11,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(12,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(13,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(14,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(15,i).getContents() + "</td>";
          v_errString+="<td >1</td>";
          v_errString+="<td >" + sheet.getCell(16,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(17,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(17,i).getContents() + "</td>";
          v_errString+="</tr>";
          errcnt++;
        }
        else{
          v_okString+="<tr bgcolor=#ffffff align=center>";
          v_okString+="<td>"+(i+1)+"</td>";
          v_okString+="<td >"+v_errtxt+"</td>";
          v_okString+="<td >"+v_subj2+"</td>";
          v_okString+="<td >"+v_subjseqgr+"</td>";
          v_okString+="<td >" + v_userid + "</td>";
          v_okString+="<td >" + sheet.getCell(6,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(7,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(8,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(9,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(10,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(11,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(12,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(13,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(14,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(15,i).getContents() + "</td>";
          v_okString+="<td >1</td>";
          v_okString+="<td >" + sheet.getCell(16,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(17,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(17,i).getContents() + "</td>";
          v_okString+="</tr>";
        }
        
        insertData.clear();
        insertData.put("connMgr",     connMgr);
        insertData.put("inputtime",   v_inputtime);
        insertData.put("subj",        v_subj2);
        insertData.put("year",        v_year2);
        insertData.put("subjseq",     v_subjseq);   //subjseqgr trans
        insertData.put("userid",      sheet.getCell(5,i).getContents());
        insertData.put("score",       new Double(sheet.getCell(15,i).getContents()) );
        insertData.put("tstep",       new Double(sheet.getCell(7,i).getContents()));
        insertData.put("mtest",       new Double(sheet.getCell(8,i).getContents()));
        insertData.put("ftest",       new Double(sheet.getCell(9,i).getContents()));
        insertData.put("report",      new Double(sheet.getCell(11,i).getContents()));
        insertData.put("act",         new Double(sheet.getCell(12,i).getContents()));
        insertData.put("etc1",        new Double(sheet.getCell(13,i).getContents()));
        insertData.put("etc2",        new Double(sheet.getCell(14,i).getContents()));
        insertData.put("isgraduated", sheet.getCell(16,i).getContents());
        insertData.put("luserid",     box.getSession("userid"));
        //out.println("v_errvalue==>"+i);

        isOk = cpResult.insertCPexcelTemp(insertData);
        tempisOk = tempisOk*isOk;
        
      }
      
      if(tempisOk>0){
       connMgr.commit();
      }else{
        connMgr.rollback();
      }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


      
%>

  <table width="97%" cellpadding="3" bgcolor="#cccccc" border="0" cellspacing="1">
    <tr bgcolor="#eeeeee" align="center">
      <td>순번</td>
      <td>오류여부</td>
      <td>과정</td>
      <td>차수</td>
      <td>사번</td>
      <td>성명</td>
      <td>진도율</td>
      <td>중간평가</td>
      <td>최종평가</td>
      <td>형성평가</td>
      <td>Report</td>
      <td>Activity</td>
      <td>기타1</td>
      <td>기타2</td>
      <td>첨부파일Score</td>
      <td>가중치적용Score</td>
      <td>수료여부</td>
      <td>실수료여부</td>
      <td>미이수사유</td>
    </tr>
    <%=v_errString%>
    </table>
    <br>
  
  <table width="97%" cellpadding="3" bgcolor="#cccccc" border="0" cellspacing="1">
    <tr bgcolor="#eeeeee" align="center">
      <td>순번</td>
      <td>오류여부</td>
      <td>과정</td>
      <td>차수</td>
      <td>사번</td>
      <td>성명</td>
      <td>진도율</td>
      <td>중간평가</td>
      <td>최종평가</td>
      <td>형성평가</td>
      <td>Report</td>
      <td>Activity</td>
      <td>기타1</td>
      <td>기타2</td>
      <td>첨부파일Score</td>
      <td>가중치적용Score</td>
      <td>수료여부</td>
      <td>실수료여부</td>
      <td>미이수사유</td>
    </tr>
    <%=v_errString%>
    </table>
    <br>
    <br>
    
    <table width="97%" cellpadding="3" bgcolor="#cccccc" border="0" cellspacing="1">
    <tr bgcolor="#eeeeee" align="center">
      <td>순번</td>
      <td>오류여부</td>
      <td>과정</td>
      <td>차수</td>
      <td>사번</td>
      <td>성명</td>
      <td>진도율</td>
      <td>중간평가</td>
      <td>최종평가</td>
      <td>형성평가</td>
      <td>Report</td>
      <td>Activity</td>
      <td>기타1</td>
      <td>기타2</td>
      <td>첨부파일Score</td>
      <td>가중치적용Score</td>
      <td>수료여부</td>
      <td>실수료여부</td>
      <td>미이수사유</td>
    </tr>
    <%=v_okString%>
    </table>
    <br>
    <br>
    <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr align="center">
          <td align="center">
          
          <%if(errcnt < 1){%>
            <a href="javascript:action('insertFileToDB')"><img src="/images/admin/button/btn_apply.gif"  border="0"></a>&nbsp;&nbsp;
          <%}%>
            <a href="javascript:history.back()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a>&nbsp;&nbsp;
            <a href="javascript:action('')"><img src="/images/admin/button/btn_list.gif"  border="0"></a>&nbsp;&nbsp;
          
          </td>
        </tr>
        
      </table>
    
  
  
  
<%
    } catch (Exception e) {
                        v_isexception = true;
                        v_return_msg = e.getMessage();
                        
%>
error
        <!----------------- 에러메세지 끝 ----------------->
<%      } finally {
						//첨부파일삭제(미리보기후 저장하므로 삭제하지 않음)
						FileManager.deleteFile(v_newFileName);
                        if(pstmt_select != null) { try { pstmt_select.close(); } catch (Exception e) {} }
                        if(pstmt_update != null) { try { pstmt_update.close(); } catch (Exception e) {} }
                        if(pstmt_insert != null) { try { pstmt_insert.close(); } catch (Exception e) {} }
                        if(pstmt_update_student != null) { try { pstmt_update_student.close(); } catch (Exception e) {} }
                        if(pstmt_select_member  != null) { try { pstmt_select_member.close(); } catch (Exception e) {} }
                        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
      <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
