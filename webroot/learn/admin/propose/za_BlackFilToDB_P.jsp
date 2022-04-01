<%
//**********************************************************
//  1. 제      목: 수강신청 제약 대상자 FileToDB
//  2. 프로그램명 : za_BlackFileToDB_P.jsp
//  3. 개      요: 수강신청 제약 대상자 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 이창훈 2004.11.20
//  7. 수      정: 정경진 2005.07.19
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;

    PreparedStatement propose_pstmt = null;
    PreparedStatement student_pstmt = null;

    String propose_sql = "";
    String student_sql = "";

    AcceptFileToDBBean bean = null;
    Hashtable inputdata  = new Hashtable();
    Hashtable outputdata = null;

    int isOk = 0;
    int isOk1 = 0;

    String  v_process   = box.getString("p_process");

	String  v_isretire    = box.getStringDefault("p_isretire","Y");
	String  v_isemtpty    = box.getStringDefault("p_isemtpty","N");
	String  v_isstoped    = box.getStringDefault("p_isstoped","N");
	String  v_stuoverlap  = box.getStringDefault("p_stuoverlap","N");
	String  v_isgraduated = box.getStringDefault("p_isgraduated","N");

	String  ss_grcode	    = box.getString("p_grcode");            //교육주관
	String  ss_gyear	    = box.getString("p_gryear");            //년도
	String  ss_grseq	    = box.getString("p_grseq");             //교육차수
	String  ss_conditioncode	    = box.getString("p_conditioncode");     //수강제약코드

    String  v_luserid   = box.getSession("userid");

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");
//out.println("real filename111 ==> "+v_realFileName);
//out.println("new filename111 ==> "+v_newFileName);
//if(true) return;
    int     i=0;
    String  v_userid  = "";
//    String  v_cono    = "";
    String  v_name    = "";
    String  v_gubun  = "";

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

    Exception ex01 = null;
%>
<html>
<head>
<title>수강신청 제약 FileToDB</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function golistPage() {
  document.form1.action = "/servlet/controller.propose.AcceptFileToDBServlet";
  document.form1.p_process.value = "FileToDB";
  document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">

<form name="form1" method="post" >
  <input type="hidden" name="p_process" value="">
  <input type="hidden" name="s_grcode"  value="<%=ss_grcode%>">
  <input type="hidden" name="s_gyear"   value="<%=ss_gyear%>">
  <input type="hidden" name="s_grseq"   value="<%=ss_grseq%>">
  <input type="hidden" name="s_conditioncode"  value="<%=ss_conditioncode%>">


  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/system/tit_new_cancelapply.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      
      <!----------------- 교육정보시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="15"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td>
				     교육그룹 : <b><%=GetCodenm.get_grcodenm(ss_grcode)%></b> 교육차수 : <b><%=GetCodenm.get_grseqnm(ss_grcode, ss_gyear, ss_grseq)%></b> 수강제약구분 :  <b><%=CodeConfigBean.getCodeName("0050",ss_conditioncode)%></b>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="15"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
	  <br>
      <!---------------------------------------------------------------------------->
      
<%  try {

        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.accept")+v_newFileName));
        sheet = workbook.getSheet(0);
        

        bean = new AcceptFileToDBBean();
        MainSubjSearchBean msbean = new MainSubjSearchBean();
        
        String v_errvalue = "0";
        String v_errtxt = "";
        String v_year = "";
        String v_isproposeapproval = "";
        String v_useproposeapproval = "";

        String v_isonoff  = "";
        String v_isclosed = "";
        String v_edustart = "";
        String v_eduend   = "";
        String v_subjseqgr = "";
        
        
        String v_appstatus = "";
        
        String v_errString = "";
        String v_okString = "";


        int errcnt = 0;
        int okcnt = 0;
        
        for (i=1; i < sheet.getRows() ; i++ ) {
            v_userid = sheet.getCell(0,i).getContents();  // 아이디
            v_gubun = sheet.getCell(1,i).getContents();  // 사유

			if (v_process.equals("insertFileToDB")) {// start if
               
                //인사DB검색
                if(v_errvalue.equals("0")){
                  v_errvalue=msbean.isExitMember(v_userid, v_isretire, v_isemtpty, v_isstoped);
                }
                
                if(v_errvalue.equals("0")){
                  v_errvalue=bean.isExitBlack(box, v_userid);
                }
                       							  
                if(v_errvalue.equals("0")){

                  inputdata.clear();
                  inputdata.put("connMgr",	connMgr);
                  inputdata.put("grcode",	ss_grcode);
                  inputdata.put("gryear",	ss_gyear);
                  inputdata.put("grseq",	ss_grseq);
                  inputdata.put("conditioncode",  ss_conditioncode);
                  inputdata.put("userid",	v_userid);
                  inputdata.put("gubun",	v_gubun);
                  inputdata.put("luserid",	v_luserid );

                  isOk = bean.insertBlackList(inputdata);

				  if(isOk==0) v_errvalue = "999";

                }
                else{
                  errcnt++;
                }

				v_errtxt = msbean.isGetErrtxt(v_errvalue);

           }else{
              
				  //인사DB검색
				  if(v_errvalue.equals("0")){
					v_errvalue=msbean.isExitMember(v_userid, v_isretire, v_isemtpty, v_isstoped);
				  }

				  if(v_errvalue.equals("0")){
					  v_errvalue=bean.isExitBlack(box, v_userid);
				  }
				
				  v_errtxt = msbean.isGetErrtxt(v_errvalue);

			}	// end if

            if(!v_errvalue.equals("0")){
              v_errString += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>"+v_errtxt+"</td>";
            }
            else{
              v_okString  += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>"+v_errtxt+"</td>";
            }

        }
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
            <td class="table_title" colspan="8">ERROR : <%=errcnt%>건</td>
          </tr>
          <tr>
            <td class="table_title" width="10%">줄번호</td>
            <td class="table_title" width="20%">사번</td>
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
            <td colspan="15" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" colspan="8">정상입력</td>
          </tr>
          <tr>
            <td class="table_title" width="10%">줄번호</td>
            <td class="table_title" width="20%">사번</td>
            <td class="table_title" >MESSAGE</td>
          </tr>
          <%=v_okString%>        
      </table> 
      <!----------------- 결과 정보 출력 끝 ----------------->
      
<%      

        if(v_process.equals("insertFileToDB")){
        
          if(errcnt == 0) {

              connMgr.commit();
          } else {

              connMgr.rollback();
          }
        
        }

        } catch (Exception e) {
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
            <font color=red>&nbsp;&nbsp;--> 입력한 영역<b>(사번)</b>만 지정하고 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br>
                            &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br>
          </td>
        </tr>
      </table>
<%

        } finally {
				//첨부파일삭제
			FileManager.deleteFile(conf.getProperty("dir.upload.accept")+v_newFileName);
		        if(propose_pstmt != null) { try { propose_pstmt.close(); } catch (Exception e) {} }
		        if(student_pstmt != null) { try { student_pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr><td align="center" ><a href="javascript:history.back()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;&nbsp;<a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
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