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
	int v_pageno       = box.getInt("p_pageno");
	String v_grcode    = box.getString("s_grcode");
	String v_gyear     = box.getString("s_gyear");
	String v_grseq     = box.getString("s_grseq");
	String v_year2     = "";
	String v_subj2     = "";
	String v_subjseqgr = "";
	String v_cp        = box.getString("p_cp");
	String v_subj      = box.getString("p_subj");
    String v_subjseq   = box.getString("p_subjseq");
    String v_errcnt    = box.getString("p_errcnt");       //첫페이지에서 넘어온 errcnt
    
    String s_grcodename = box.getString("s_grcodename");
    String s_gryearname = box.getString("s_gryearname");
    String s_grseqname  = box.getString("s_grseqname");

    String v_subjseq2 = "";
    
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

	if(v_process.equals("insertFileToDBALL")){

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
    int j = 0;

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

function action(p_process, p_errcnt) {
  document.form2.action = "/servlet/controller.cp.CPResultServlet";
  document.form2.p_process.value = p_process;                              //insertFileToDB,previewFileToDB
  document.form2.p_errcnt.value = p_errcnt;
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
	<input type="hidden" name="s_grseq" value="<%=v_grseq%>">
	<input type="hidden" name="p_year" value="<%=v_year%>">
	<input type="hidden" name="p_cp" value="<%=v_cp%>">
	<input type="hidden" name="p_subj" value="<%=v_subj%>">
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
	<input type="hidden" name="s_grcodename" value="<%=s_grcodename%>">
    <input type="hidden" name="s_gryearname" value="<%=s_gryearname%>">
    <input type="hidden" name="s_grseqname" value="<%=s_grseqname%>">

  <input type="hidden" name="p_grcode"     value="<%=v_grcode%>">
  <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
  <input type="hidden" name="p_grseq"      value="">
  <input type="hidden" name="p_offsubj"    value="<%=v_subj%>">
  <input type="hidden" name="p_offsubjseq" value="<%=v_subjseq%>">
  <input type="hidden" name="p_errcnt"     value="">

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
                            <td colspan="4" height=35><font color="4F9AE6">아래의 결과 데이터를 확인하시고 하단의 <FONT COLOR=RED>저장버튼</FONT>을 클릭하셔야만 등록처리가 완료됩니다..</font></td>
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
      int okcnt = 0;
      String v_inputtime = FormatDate.getDate("yyyyMMddHHmmss");
      String v_errvalue = "0";
      String v_errtxt = "";
      String v_errString = "";
      String v_okString  = "";
      String v_isclosed = "";
      String v_iscpflag = "";



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

      //out.println("v_inputtime====>"+v_inputtime);
      String temp_subjseq = "0000";

      //tz_student(수강생정보) 테이블 update구문
      pstmt_update_student = finishbean.getPreparedStatement(connMgr, "SUBJECT_STUDENT", "update_result");

      for (i=2; i < sheet.getRows() ; i++ ) {
        //out.println("iiii===>>====>"+i);
        //out.println(i);
        //교육년도 및 차수가 맞는지 확인한다.
        v_year2    = sheet.getCell(0,i).getContents();
        v_subj2    = sheet.getCell(2,i).getContents();
        v_subjseq2 = sheet.getCell(3,i).getContents();
        v_userid   = sheet.getCell(4,i).getContents();
        v_subjseqgr = v_subjseq2;
        
        //소문자로변경
        v_userid = v_userid.toLowerCase();
        
        
        if(!temp_subjseq.equals(v_subjseq2)){
          v_subjseq2 = df.format(Integer.parseInt(v_subjseq2));
          v_subjseq = fbean.selectGrSubjseq(connMgr, v_subj2, v_year2, v_subjseq2, v_grcode);
          temp_subjseq = v_subjseq2;
        }
        
        //양식error Message 출력
        if(v_year2.equals("")){
          out.println("<font color=red><b>엑셀양식Error</b><br><b>"+(i+1)+" 번째행 error!!교육년도를 반드시 입력하여야 합니다.</b></font>");
          return;
        }
        
        if(v_subj2.equals("")){
          out.println("<font color=red><b>엑셀양식Error</b><br><b>"+(i+1)+" 번째행 error!!과정코드를 반드시 입력하여야 합니다.</b></font>");
          return;
        }
        
        if(v_subjseq2.equals("")){
          out.println("<font color=red><b>엑셀양식Error</b><br><b>"+(i+1)+" 번째행 error!!차수를 반드시 입력하여야 합니다.</b></font>");
          return;
        }

        if(v_subjseq.equals("")){
          out.println("<br><font color=red><b>엑셀양식Error</b><br><b>"+(i+1)+" 번째행 error!! 해당과정에 <font color=blue>"+v_subjseq2+"</font>차수가 존재하지 않습니다.</b></font>");
          return;        
        }
        
        ////////////////////////////////////////////////////////////////////////////

        v_errvalue=msbean.isExitSubj(connMgr, v_subj2);

        if(v_errvalue.equals("0")){
          v_errvalue=msbean.isExitSeq(connMgr, v_grcode, v_year2, v_subj2, v_subjseq);

          if(v_errvalue.equals("0")){
            DataBox dbox = msbean.SubjseqInfo(connMgr, v_subj2, v_grcode, v_subjseq, v_year2);

            if(dbox != null){
              v_isclosed = dbox.getString("d_isclosed");
              v_iscpflag = dbox.getString("d_iscpflag");
            }
            if(!v_iscpflag.equals("") && !v_iscpflag.equals("N")){
              v_errvalue = "23";
            }
            if(v_isclosed.equals("Y")){
              v_errvalue = "22";
            }
          }

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


            ///////////////////////////////////////////////////////////////////////////////////
            if(v_errvalue.equals("0")){
                subjseqdata = finishbean.getSubjseqInfo(connMgr, v_subj2, v_year2, v_subjseq);

                data = new CPStoldData();
                data.setSubj   (v_subj2);
	            data.setYear   (v_year2);
	            data.setSubjseq(v_subjseq);
				data.setWstep    (subjseqdata.getWstep());
	            data.setWmtest   (subjseqdata.getWmtest());
	            data.setWftest   (subjseqdata.getWftest());
	            data.setWhtest   (subjseqdata.getWhtest());
	            data.setWreport  (subjseqdata.getWreport());
	            data.setWact     (subjseqdata.getWact());
	            data.setWetc1    (subjseqdata.getWetc1());
	            data.setWetc2    (subjseqdata.getWetc2());
	            data.setGradscore(subjseqdata.getGradscore());
	            data.setGradstep (subjseqdata.getGradstep());
	            data.setEdustart (subjseqdata.getEdustart());
	            data.setEduend   (subjseqdata.getEduend());
	            data.setUserid(v_userid); // 아이디
	            data.setScore(0);
	            data.setTstep(Double.valueOf(sheet.getCell(6,i).getContents()).doubleValue());  // 진도율(출석률점수)
	            data.setMtest(0);  // 중간평가점수
	            data.setFtest(Double.valueOf(sheet.getCell(7,i).getContents()).doubleValue());  // 최종평가점수
	            data.setHtest(0);  // 형성평가점수
	            data.setReport(Double.valueOf(sheet.getCell(8,i).getContents()).doubleValue()); // 리포트점수
	            data.setAct(0);	//Activity 점수
	            data.setEtc1(0);	//기타1 점수
	            data.setEtc2(0);	//기타2 점수
	            data.setScoreFile(Double.valueOf(sheet.getCell(9,i).getContents()).doubleValue());	//총점 점수
	            data.setAvtstep(0);
	            data.setAvmtest(0);
	            data.setAvftest(0);
	            data.setAvhtest(0);
	            data.setAvreport(0);
	            data.setAvact(0);
	            data.setAvetc1(0);
	            data.setAvetc2(0);
	            data.setIsgraduated(sheet.getCell(10,i).getContents());	//수료여부(Y/N)
	            data.setNotgraduetc(sheet.getCell(11,i).getContents());	//미이수사유(두자리코드)
	            data.setRank(sheet.getCell(16,i).getContents());	//성적우수자
	            
				//가중치 적용 score 계산
				finishbean.calc_subj_score(connMgr, data);


                //이수기준확인 하여 이수여부 확인
                if(
                  subjseqdata.getGradscore() > data.getScore() ||
                  subjseqdata.getGradstep() > data.getTstep() ||
                  subjseqdata.getGradexam() > data.getMtest() ||
                  subjseqdata.getGradftest() > data.getFtest() ||
                  subjseqdata.getGradhtest() > data.getHtest() ||
                  subjseqdata.getGradreport() > data.getReport()
                  )
                {
                	v_ispass = "N";
                }
                else
                	v_ispass = "Y";


                if(!sheet.getCell(10,i).getContents().equals(v_ispass)){
                	//실수료여부와 엑셀파일의 수료여부가 틀리면 붉은색으로 표시하여 알림.
                	v_ispass1 = "<font color=red>" + sheet.getCell(10,i).getContents() + "</font>";
                	v_ispass2 = "<font color=red>" + v_ispass + "</font>";
                	v_errvalue = "24";
                }
                else{
                	v_ispass1 = sheet.getCell(10,i).getContents();
                	v_ispass2 = v_ispass;
                }


                if(!String.valueOf(data.getScoreFile()).equals(String.valueOf(data.getScore()))){
	            	//등록파일 score와 가중치적용 score가 다르면 붉은색으로 표시하여 알림.
	            	v_score1 = "<font color=red>" + String.valueOf(data.getScoreFile()) + "</font>";
	            	v_score2 = "<font color=red>" + String.valueOf(data.getScore()) + "</font>";
	            	v_errvalue = "25";
	            }
	            else{
	            	v_score1 = String.valueOf(data.getScoreFile());
	            	v_score2 = String.valueOf(data.getScore());
	            }

                //미이수사유
	            if(!String.valueOf(data.getNotgraduetc()).equals("")){
					v_notgraduetc = StringManager.replace(data.getNotgraduetc(),"01","진도미달");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"02","평가미응시");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"03","과제미제출");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"04","학습출참");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"05","평가/과제미제출");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"06","성적미달");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"07","평가점수 미달");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"08","리포트점수 미달");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"09","퇴사");
					v_notgraduetc = StringManager.replace(v_notgraduetc,"10","중도퇴소");
	            }
	            else{
	            	v_notgraduetc = "";
	            }

            }
            //////////////////////////////////////////////////////////////////////////////////
          }
        }
        //out.println("v_errvalue+===============>>>>>>>"+v_errvalue);
        // Message 출력
        v_errtxt = msbean.isGetErrtxt(v_errvalue);


        if(!v_errvalue.equals("0")){
          v_errString+="<tr bgcolor=#ffffff align=center>";
          v_errString+="<td>"+(i+1)+"</td>";
          v_errString+="<td ><font color=red>"+v_errtxt+"</font></td>";
          v_errString+="<td >"+v_subj2+"</td>";
          v_errString+="<td >"+v_subjseqgr+"</td>";
          v_errString+="<td >" + v_userid + "</td>";
          v_errString+="<td >" + sheet.getCell(5,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(6,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(7,i).getContents() + "</td>";
          v_errString+="<td >" + sheet.getCell(8,i).getContents() + "</td>";
          v_errString+="<td >" + v_score1 + "</td>";
          v_errString+="<td >" + v_score2 + "</td>";
          v_errString+="<td >" + v_ispass1 + "</td>";
          v_errString+="<td >" + v_ispass2 + "</td>";
          v_errString+="<td >" + v_notgraduetc + "</td>";
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
          v_okString+="<td >" + sheet.getCell(5,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(6,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(7,i).getContents() + "</td>";
          v_okString+="<td >" + sheet.getCell(8,i).getContents() + "</td>";
          v_okString+="<td >" + v_score1 + "</td>";
          v_okString+="<td >" + v_score2 + "</td>";
          v_okString+="<td >" + v_ispass1 + "</td>";
          v_okString+="<td >" + v_ispass2 + "</td>";
          v_okString+="<td >" + v_notgraduetc + "</td>";
          v_okString+="</tr>";
          okcnt++;
        }

        //000을붙여 자릿수를 맞춘다.
        v_subjseqgr = df.format(Integer.parseInt(v_subjseqgr));

        insertData.clear();
        insertData.put("connMgr",     connMgr);
        insertData.put("inputtime",   v_inputtime);
        insertData.put("subj",        v_subj2);
        insertData.put("year",        v_year2);
        insertData.put("subjseq",     v_subjseq);   //subjseqgr trans
        insertData.put("userid",      v_userid);
        insertData.put("score",       new Double(sheet.getCell(9,i).getContents()) );
        insertData.put("tstep",       new Double(sheet.getCell(6,i).getContents()));
        insertData.put("mtest",       new Double(0));
        insertData.put("ftest",       new Double(sheet.getCell(7,i).getContents()));
        insertData.put("report",      new Double(sheet.getCell(8,i).getContents()));
        insertData.put("act",         new Double(0));
        insertData.put("etc1",        new Double(0));
        insertData.put("etc2",        new Double(0));
        insertData.put("isgraduated", sheet.getCell(10,i).getContents());
        insertData.put("luserid",     box.getSession("userid"));
        insertData.put("subjseqgr",   v_subjseqgr);
        insertData.put("rank",        sheet.getCell(15,i).getContents());
        //out.println("v_subjseq==>"+v_subjseq);
        isOk = cpResult.insertCPexcelTemp(insertData);
        tempisOk = tempisOk*isOk;

        /////////////////////////////////////////결과등록처리/////////////////////////////////////////
	    if (v_process.equals("insertFileToDBALL") && v_errcnt.equals("0") ) {
        
	        // 1.tz_student update 
	        isOK2 = finishbean.update_subj_score(pstmt_update_student, data, v_luserid);
	        tempisOk = tempisOk*isOK2;
        
	    }
	    /////////////////////////////////////////결과등록처리/////////////////////////////////////////
        
        
      }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

%>

  <table width="97%" cellpadding="3" bgcolor="#cccccc" border="0" cellspacing="1">
    <tr bgcolor="#eeeeee" align="center">
      <td rowspan=2>과정</td>
      <td rowspan=2>과정코드</td>
      <td rowspan=2>차수</td>
      <td colspan=3>실수강생</td>
      <td colspan=3>수료처리여부</td>
    </tr>
    <tr bgcolor="#eeeeee" align="center">
      <td>실수강생</td>
      <td>입력수강생</td>
      <td>오류여부</td>
      <td>수료처리여부</td>
      <td>파일등록여부</td>
      <td>오류여부</td>
    </tr>
    
    <%
      ArrayList  exclist = new ArrayList();
      box.put("p_inputtime", v_inputtime);
      exclist = cpResult.selectInpuSubjList(connMgr, box);

      String d_subjnm       = "";
      String d_subj         = "";
      String d_subjseq      = "";
      String d_subjseqgr    = "";
      String d_year         = "";
      String d_isclosed     = "";
      String d_iscpflag     = "";
      String d_eduterm      = "";
      
      int d_inputstucnt  = 0;
      int d_realstucnt   = 0;
      
      String v_errvalue1 = "0";
      String v_errvalue2 = "0";
      String v_errtxt1   = "";
      String v_errtxt2   = "";
      for(j=0; j<exclist.size(); j++){
      v_errvalue1 = "0";
      v_errvalue2 = "0";
        
        DataBox dbox = (DataBox)exclist.get(j);
        d_subjnm      = dbox.getString("d_subjnm");
        d_subj        = dbox.getString("d_subj");
        d_subjseq     = dbox.getString("d_subjseq");
        d_subjseqgr   = dbox.getString("d_subjseqgr");
        d_year        = dbox.getString("d_year");
        d_inputstucnt = dbox.getInt("d_inputstucnt");
        d_realstucnt  = dbox.getInt("d_realstucnt");
        d_isclosed    = dbox.getString("d_isclosed");
        d_iscpflag    = dbox.getString("d_iscpflag");
        d_eduterm     = dbox.getString("d_eduterm");
        
        if(d_inputstucnt != d_realstucnt){
          v_errvalue1 = "31";
        }else{
          v_errvalue1 = "0";
        }
        
        
        if(v_errvalue1.equals("0")){
          if(!d_iscpflag.equals("") && !d_iscpflag.equals("N")){
            v_errvalue2 = "33";
          }else{
            v_errvalue2 = "0";
          }
        }
        
        
        if(d_isclosed.equals("Y")){
            v_errvalue2 = "32";
        }else{
          if(!d_eduterm.equals("5")){  //교육종료체크
            v_errvalue2 = "35";
          }else{
            v_errvalue2 = "0";
          }
        }
        
        

        if(!v_errvalue1.equals("0")){
          errcnt++;
        }
        if(!v_errvalue2.equals("0")){
          errcnt++;
        }

        v_errtxt1 = msbean.isGetErrtxt(v_errvalue1);
        v_errtxt2 = msbean.isGetErrtxt(v_errvalue2);
        if(!v_errvalue1.equals("0")){
          v_errtxt1 = "<font color=red>"+v_errtxt1+"</font>";
        }
        
        if(!v_errvalue2.equals("0")){
          v_errtxt2 = "<font color=red>"+v_errtxt2+"</font>";
        }

    %>
    <tr bgcolor=#ffffff align=center>
      <td><%=d_subjnm%></td>
      <td><%=d_subj%></td>
      <td><%=StringManager.cutZero(d_subjseqgr)%></td>
      <td><%=d_realstucnt%></td>
      <td><%=d_inputstucnt%></td>
      <td><%=v_errtxt1%></td>
      <td><%=d_isclosed%></td>
      <td><%=d_iscpflag%></td>
      <td><%=v_errtxt2%></td>
    </tr>
    <%
      if (v_process.equals("insertFileToDBALL")) {
	   	//외부교육과정결과 파일등록 정보저장
	   	isOK3 = cpResult.update_cpresult(connMgr, d_year, d_subj, d_subjseq, v_realFileName, v_newFileName);
	   	tempisOk = tempisOk*isOK3;
      }
    }
    %>
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
      <td>최종평가</td>
      <td>Report</td>
      <td>첨부파일Score</td>
      <td>가중치적용Score</td>
      <td>수료여부</td>
      <td>실수료여부</td>
      <td>미이수사유</td>
    </tr>
    <%if(errcnt > 0){%>
      <%=v_errString%>
    <%}else{%>
      <tr bgcolor=#ffffff align=center>
        <td colspan="20">오류가 없습니다.(저장버튼을 클릭하여 주십시오)</td>
      </tr>
    <%}%>
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
      <td>최종평가</td>
      <td>Report</td>
      <td>첨부파일Score</td>
      <td>가중치적용Score</td>
      <td>수료여부</td>
      <td>실수료여부</td>
      <td>미이수사유</td>
    </tr>

    <%if(okcnt > 0){%>
      <%=v_okString%>
    <%}else{%>
      <tr bgcolor=#ffffff align=center>
        <td colspan="20">정상데이터가 없습니다.</td>
      </tr>
    <%}
    
    if(tempisOk>0){
      connMgr.commit();
    }else{
      connMgr.rollback();
    }
    
    
    %>
    </table>
    <br>
    <br>
    <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr align="center">
          <td align="center">

          <%if(!v_process.equals("insertFileToDBALL") && errcnt < 1){%>
            <a href="javascript:action('insertFileToDBALL', '<%=errcnt%>')"><img src="/images/admin/button/btn_apply.gif"  border="0"></a>&nbsp;&nbsp;
          <%}%>
            <a href="javascript:history.back()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a>&nbsp;&nbsp;
            <a href="javascript:action('','')"><img src="/images/admin/button/btn_list.gif"  border="0"></a>&nbsp;&nbsp;

          </td>
        </tr>

      </table>




<%
    } catch (Exception e) {
                        v_isexception = true;
                        v_return_msg = e.getMessage();

%>
<!----------------- 에러메세지 시작 ----------------->
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" ><font color='red'><%=i+1%>번째에서 에러입니다.</font></td>
        </tr>
        <tr>
          <td align="left" >에러 메세지 : <%=e.getMessage()%></td>
        </tr>
      </table>
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" ><font color='red'><%=j+1%>번째에서 에러입니다.</font></td>
        </tr>
        <tr>
          <td align="left" >에러 메세지 : <%=e.getMessage()%></td>
        </tr>
      </table>
      
        <!----------------- 에러메세지 끝 ----------------->
<%      } finally {
						//첨부파일삭제(미리보기후 저장하므로 삭제하지 않음)
						//FileManager.deleteFile(v_newFileName);
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
