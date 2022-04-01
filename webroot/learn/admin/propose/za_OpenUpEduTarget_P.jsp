<%
//**********************************************************
//  1. 제      목: 입과 FileToDB
//  2. 프로그램명 : za_ProposeFileToDB_P.jsp
//  3. 개      요: 입과 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 이창훈 2004.11.20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;

    PreparedStatement edutarget_pstmt = null;

    String edutarget_sql = "";

    ProposeBean proposebean = null;
    OverLapBean olbean = null;
    SubjComBean scbean = null;
    TextComBean tcbean = null;
    
    Hashtable inputdata  = new Hashtable();
    Hashtable outputdata = null;

    int isOk = 0;

    String  v_process   = box.getString("p_process");

    String  ss_grcode     = box.getString("s_grcode");
    String  ss_grseq      = box.getString("s_grseq");
    String  ss_gyear      = box.getString("s_gyear");
    String  ss_subjcourse = box.getString("s_subjcourse");
    String  ss_mastercd   = box.getString("s_mastercd");
    String  ss_masternm   = box.getString("s_masternm");

    String  v_luserid   = box.getSession("userid");

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");

//out.println("new filename ==> "+v_newFileName);
//out.println("Real filename ==> "+v_realFileName);
//if(true)return;
    int     i=0;
    String  v_userid  = "";
//  String  v_cono    = "";
    String  v_name    = "";
    String  v_subjseq = "";

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

    Exception ex01 = null;
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
  document.form2.action = "/servlet/controller.propose.ProposeWizardServlet";
  document.form2.p_process.value = "UpFile";
  document.form2.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table  width="97%"  border="0" cellspacing="0" cellpadding="0" height="663">
<form name="form2" method="post" >
  <input type="hidden" name="p_process" value="">
  <input type="hidden" name="s_mastercd" value="<%=ss_mastercd%>">
</form>
  <tr>
    <td align="center" valign="top">
      <!----------------- 교육차수 검색 title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!-------------------타이틀 끝-------------------------->
      <br>
      <br>
      <!----------------- 업로드 자료 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" height='35'><b><font size="3">교육그룹:<%=ss_grcode%>/년도:<%=ss_gyear%>/마스터과정명:<%=ss_masternm%></font></b></td>
        </tr>
      </table>
<%  try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);
        //out.println(conf.getProperty("dir.upload.edutarget"));

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.edutarget")+v_newFileName));
        sheet = workbook.getSheet(0);

        proposebean = new ProposeBean();
        olbean = new OverLapBean();
        scbean = new SubjComBean();
        tcbean = new TextComBean();
        
        int errcnt = 0;
        String errcode = "0";
        String v_errtxt = "";
        String v_errString = "";
        String v_okString = "";

        if (v_process.equals("EduTargetinsertFileToDB")) {
        		//edutarget_sql  = "insert into TZ_EDUTARGET(mastercd, userid, isproposeapproval, luserid, ldate ) ";
				//edutarget_sql += "values ( ?, ?, ?, ?, ? ) ";
				
				edutarget_sql =  "insert into TZ_EDUTARGET ( " ;
                edutarget_sql+=  " mastercd,  userid,  isproposeapproval, ";
                edutarget_sql+=  " luserid, ldate ) " ;
                edutarget_sql+=  " values ( ";
                edutarget_sql+=  " ?, ?, ?, ";
                edutarget_sql+=  " ?, ? ) ";
                
		        edutarget_pstmt = connMgr.prepareStatement(edutarget_sql);
        }

        
        
        
        for (i=1; i < sheet.getRows() ; i++ ) {
            //out.println(sheet.getRows());
            v_userid = sheet.getCell(0,i).getContents();  // 아이디
            v_name   = sheet.getCell(1,i).getContents();  // 성명
            
            v_userid = v_userid.toLowerCase();
            //out.print(v_userid);
            //out.print(v_name);

            if (v_process.equals("EduTargetinsertFileToDB")) {
                
                //인사db존재여부
                errcode = olbean.isExitMember(connMgr, v_userid, "","","");

                if(errcode.equals("0")){
                  errcode = scbean.IsEduTargetUserid(connMgr, ss_mastercd, v_userid);
                  if(errcode.equals("0")){
                    inputdata.clear();
                    inputdata.put("connMgr",     connMgr);
                    inputdata.put("edutarget_pstmt", edutarget_pstmt);
                    inputdata.put("grcode",    ss_grcode);
                    inputdata.put("gyear",     ss_gyear);
                    inputdata.put("grseq",     ss_grseq);
                    inputdata.put("luserid",   v_luserid);
                    inputdata.put("userid",    v_userid);
                    inputdata.put("name",      v_name);
                    inputdata.put("mastercd",  ss_mastercd);
                    isOk = proposebean.insertEduTarget(inputdata);
                  }
                }
              //out.println("isOk="+isOk);
            }

            else{
            
              //인사db존재여부
              errcode = olbean.isExitMember(connMgr, v_userid, "","","");
              
              
              if(errcode.equals("0")){
                errcode = scbean.IsEduTargetUserid(connMgr, ss_mastercd, v_userid);
              }
              
              //out.println("errcode===>>"+errcode);
              //out.println(v_errString);
              //if(true) return;

            }
            
            v_errtxt = tcbean.isGetErrtxt(errcode);
            
            if(!errcode.equals("0")){
              v_errString += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + errcode + "</td><td class=table_02_1>"+v_errtxt+"</td>";
              errcnt++;
            }
            else{
              v_okString  += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>"+v_errtxt+"</td>";
            }
            
            
        }
        
        
        if (v_process.equals("EduTargetinsertFileToDB")) {
          if(errcnt == 0) {
              connMgr.commit();
          } else {
              connMgr.rollback();
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
            <td class="table_title" width="10%">사번</td>
            <td class="table_title" width="10%">에러코드</td>
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

      <!----------------- 업로드 자료 끝 ----------------->
<%      } catch (Exception e) {
            connMgr.rollback();
%>
      <br>
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
            <font color=red>1. 입력양식과 일치하지 않을수 있습니다..</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>&nbsp;&nbsp;-->(권장) 샘플파일 down후 직접수정하여 UpLoad바랍니다..</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>2. 엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>&nbsp;&nbsp;--> 입력한 영역<b>(사번)</b>만 지정하고 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br>
                            &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br>
          </td>
        </tr>
      </table>
        <!----------------- 에러메세지 끝 ----------------->
<%      } finally {
				//첨부파일삭제
				FileManager.deleteFile(conf.getProperty("dir.upload.edutarget")+v_newFileName);       
				//out.println("isOk");
		        if(edutarget_pstmt != null) { try { edutarget_pstmt.close(); } catch (Exception e) {} }
				if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><a href="javascript:history.back()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><b><font size="3">(주의사항)</font></b></td>
        </tr>
        <tr>
          <td height="8"></td>
        </tr>
        <tr>
          <td height="20">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr>
          <td height="20">2. 엑셀파일 작성시 [<b><font color="#003AEA">사용자아이디, 성명</font></b>]의 순서로 만들 것.</td>
        </tr>
        <tr>
          <td height="20">3. <font color="red">샘플 양식 폼을 변경할시 치명적인 오류가 발생합니다.</font></td>
        </tr>
        <tr>
          <td height="20">4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Edutarget.xls&p_realfile=Edutarget.xls'><b>샘플파일</b></a>)</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>