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
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
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
	String v_cp = box.getString("p_cp");
	String v_subj     = box.getString("p_subj");
    String v_subjseq     = box.getString("p_subjseq");
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
    CPFinishBean  finishbean  = new CPFinishBean();
	CPResultBean cpResult = new CPResultBean();
			
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
          <td><table width="600" cellpadding="0" cellspacing="0" class="form_table_bg">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align=center> <table width=97% border="0" cellspacing="0" cellpadding="3">
                    <tr> 
                      <td width=20></td>
                      <td colspan="4">과정명 :  <strong><font color="4F9AE6"><%=GetCodenm.get_subjnm(v_subj)%>(<%=StringManager.toInt(v_subjseq)%>차)</font></strong></td>
                      <td width=19></td>
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
                      <td height=8></td>
                      <td width="165" height=8></td>
                      <td width="164" height=8></td>
                      <td width="93" height=8></td>
                      <td width="119" height=8></td>
                      <td height=8></td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="7"></td>
              </tr>
            </table></td>
        </tr>
      </table>
      
          <!--<b><font size="3">과정명 : <%=GetCodenm.get_subjnm(v_subj)%> / 교육년도 : <%=v_gyear%> / 교육차수 : <%=v_subjseq%></font></b><br>-->
          </td>
        </tr>
<%  try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        // 과정연도 검색
        //v_year = proposebean.getSubjYear(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq);

        // 과정차수 세부정보 세팅(이수기준,가중치 및 과정정보)
        subjseqdata = finishbean.getSubjseqInfo(connMgr, v_subj, v_year, v_subjseq);



        // 엑셀데이타 읽기
        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.default")+v_newFileName));
        sheet = workbook.getSheet(0);


        //Workbook workbook = Workbook.getWorkbook(new File(savePath_temp + box.getNewFileName("filename")));
        //Sheet sheet = workbook.getSheet(0);
        //Cell myCell = null;
            
            
        //tz_student(수강생정보) 테이블 update구문
        pstmt_update_student = finishbean.getPreparedStatement(connMgr, "SUBJECT_STUDENT", "update_result");        
        
        //tz_stold(수료테이블)  테이블은 사용하지 않음.
        //pstmt_select = finishbean.getPreparedStatement(connMgr, "SUBJECT_STOLD", "select");        
        //pstmt_update = finishbean.getPreparedStatement(connMgr, "SUBJECT_STOLD", "update");        
        //pstmt_insert = finishbean.getPreparedStatement(connMgr, "SUBJECT_STOLD", "insert");        
        
        //해당 유저정보 select 구문(name, comp, jikup)
        pstmt_select_member = finishbean.getPreparedStatement(connMgr, "MEMBER", "select");
%>
        <tr>
          <td align="left"><br>
			<table width="97%" cellpadding="3" bgcolor="#cccccc" border="0" cellspacing="1">
              <tr bgcolor="#eeeeee" align="center">
                <td>순번</td>
                <td>오류여부</td>
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
<%
		//수강생수 조회
		v_student = cpResult.selectStudentCnt(v_year, v_subj, v_subjseq);
		
		if(v_student != sheet.getRows()-2)	v_isErr = 0;	//수강생수가 틀리면 저장이 안되도록 한다.
		
		
		int v_cnt = 1;
		
        for (i=2; i < sheet.getRows() ; i++ ) {
            data = new CPStoldData();
            
            //교육년도 및 차수가 맞는지 확인한다.
            v_year2 = sheet.getCell(0,i).getContents();
            v_subjseq2 = sheet.getCell(3,i).getContents();
            
            if(!v_year2.equals(v_year)){
            	isOK4 = 0;
            	v_isErr1 = 0;
            	v_errmsg = "교육년도오류";
            }
            else isOK4 = 1;

            if(StringManager.toInt(v_subjseq2) != StringManager.toInt(v_subjseq)){
            	isOK5 = 0;
            	v_isErr2 = 0;
            	v_errmsg = "차수오류";
            }
            else isOK5 = 1;
            
            //실제 과정수강생인지 체크
            v_userid = sheet.getCell(4,i).getContents();
            
			isOK6 = cpResult.selectStudent(v_year, v_subj, v_subjseq, v_userid);
			
			if(isOK6 == 0){
            	v_isErr3 = 0;
            	v_errmsg = "수강생 아님";
            }

            pstmt_select_member.setString( 1, v_userid);
            rs =  pstmt_select_member.executeQuery();
            
            if(rs.next()) {                    
				v_name = rs.getString("name");
				v_comp = rs.getString("comp");
				v_jikwi = rs.getString("jikwi");
				v_jikwinm = rs.getString("jikwinm");
            }
            else{
            	v_name = "";
            	v_comp = "";
            	v_jikwi = "";
            	v_jikwinm = "";
            }
	            
            if(isOK4*isOK5*isOK6 > 0){
            
	            data.setSubj   (v_subj);
	            data.setYear   (v_year);
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
                data.setUserid(sheet.getCell(4,i).getContents()); // 아이디
	            data.setScore(0);
	            data.setTstep(Double.valueOf(sheet.getCell(6,i).getContents()).doubleValue());  // 진도율(출석률점수)
	            data.setMtest(Double.valueOf(sheet.getCell(7,i).getContents()).doubleValue());  // 중간평가점수
	            data.setFtest(Double.valueOf(sheet.getCell(8,i).getContents()).doubleValue());  // 최종평가점수
	            data.setHtest(Double.valueOf(sheet.getCell(9,i).getContents()).doubleValue());  // 형성평가점수
	            data.setReport(Double.valueOf(sheet.getCell(10,i).getContents()).doubleValue()); // 리포트점수
	            data.setAct(Double.valueOf(sheet.getCell(11,i).getContents()).doubleValue());	//Activity 점수
	            data.setEtc1(Double.valueOf(sheet.getCell(12,i).getContents()).doubleValue());	//기타1 점수
	            data.setEtc2(Double.valueOf(sheet.getCell(13,i).getContents()).doubleValue());	//기타2 점수                        
	            data.setScoreFile(Double.valueOf(sheet.getCell(14,i).getContents()).doubleValue());	//총점 점수
	            data.setAvtstep(0);
	            data.setAvmtest(0);
	            data.setAvftest(0);
	            data.setAvhtest(0);
	            data.setAvreport(0);
	            data.setAvact(0);
	            data.setAvetc1(0);
	            data.setAvetc2(0);
	            data.setIsgraduated(sheet.getCell(15,i).getContents());	//수료여부(Y/N)
	            //data.setIsgraduated("N");	//수료여부(Y/N)
	            data.setNotgraduetc(sheet.getCell(16,i).getContents());	//미이수사유(두자리코드)
	            
				data.setName(v_name);
				data.setComp(v_comp);
				data.setJik(v_jikwinm);
	            
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
                
                if(!sheet.getCell(15,i).getContents().equals(v_ispass)){
                	//실수료여부와 엑셀파일의 수료여부가 틀리면 붉은색으로 표시하여 알림.
                	v_ispass1 = "<font color=red>" + sheet.getCell(15,i).getContents() + "</font>";
                	v_ispass2 = "<font color=red>" + v_ispass + "</font>";
                	v_isErr4 = 0;
                }
                else{
                	v_ispass1 = sheet.getCell(15,i).getContents();
                	v_ispass2 = v_ispass;
                }
                

	            //studentlist.add(data);
	            
	            if(!String.valueOf(data.getScoreFile()).equals(String.valueOf(data.getScore()))){
	            	//등록파일 score와 가중치적용 score가 다르면 붉은색으로 표시하여 알림.
	            	v_score1 = "<font color=red>" + String.valueOf(data.getScoreFile()) + "</font>";
	            	v_score2 = "<font color=red>" + String.valueOf(data.getScore()) + "</font>";
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
%>
              <tr bgcolor="#ffffff" align="center">
                <td><%= v_cnt++%></td>
                <td>정상</td>
                <td><%=data.getUserid()%></td>
                <td><%=data.getName()%></td>
                <td><%=String.valueOf(data.getTstep())%></td>
                <td><%=String.valueOf(data.getMtest())%></td>
                <td><%=String.valueOf(data.getFtest())%></td>
                <td><%=String.valueOf(data.getHtest())%></td>
                <td><%=String.valueOf(data.getReport())%></td>
                <td><%=String.valueOf(data.getAct())%></td>
                <td><%=String.valueOf(data.getEtc1())%></td>
                <td><%=String.valueOf(data.getEtc2())%></td>
                <td><b><%=v_score1%></b></td>
                <td><b><%=v_score2%></b></td>
                <td><b><%=v_ispass1%></b></td>
                <td><b><%=v_ispass2%></b></td>
                <td><%=v_notgraduetc%></td>
              </tr>
<%          
				//결과등록처리
				if (v_process.equals("insertFileToDB") && v_isErr > 0) {
	
	                // 1.tz_student update 
	                isOK2 = finishbean.update_subj_score(pstmt_update_student, data, v_luserid);
	                isOK_MSG = "" + isOK2;
	                if(isOK_MSG.equals("0")){
	                	isOK_Final = 0;
	                }
	            }
	            
			}
			else{
%>
              <tr bgcolor="#ffffff" align="center">
                <td><%= v_cnt++%></td>
                <td><font color="red"><%= v_errmsg%></font></td>
                <td><%=v_userid%></td>
                <td><%=v_name%></td>
                <td><%=sheet.getCell(6,i).getContents()%></td>
                <td><%=sheet.getCell(7,i).getContents()%></td>
                <td><%=sheet.getCell(8,i).getContents()%></td>
                <td><%=sheet.getCell(9,i).getContents()%></td>
                <td><%=sheet.getCell(10,i).getContents()%></td>
                <td><%=sheet.getCell(11,i).getContents()%></td>
                <td><%=sheet.getCell(12,i).getContents()%></td>
                <td><%=sheet.getCell(13,i).getContents()%></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
              </tr>
<%
			}
        }
%>
			</table>
		  </td>
		</tr>
	  </table>
<%
        if (v_process.equals("insertFileToDB")) {

			//외부교육과정결과 파일등록 정보저장
			isOK3 = cpResult.update_cpresult(v_year, v_subj, v_subjseq, v_realFileName, v_newFileName);
			
        }
        
      	//----------------- 업로드 자료 끝 -----------------      	
		} catch (Exception e) {
                        v_isexception = true;
                        v_return_msg = e.getMessage();
%>
      <br>
      <!----------------- 에러메세지 시작 ----------------->
      <table class="table_out" cellpadding="0" cellspacing="0">
        <tr>
          <td align="left" ><font color='red'><%=i+1%>번째에서 에러입니다.</font></td>
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
                
                        if(connMgr != null) { 
                                try { 
                                        if (v_isexception) {
                                                connMgr.rollback();
                                        } else {
                                                connMgr.commit();
                                        }
                                        connMgr.setAutoCommit(true);
                                        connMgr.freeConnection(); 
                                } catch (Exception e10) {} 
                        }
        }
%>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="630" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">
<%
	if(v_process.equals("insertFileToDB")){
		//실제 db등록시
%>
          <a href="javascript:action('')"><img src="/images/admin/button/btn_list.gif"  border="0"></a>
<%
	}
	else{
		//미리보기시
		if(!(v_isErr == 0 || v_isErr1*v_isErr2*v_isErr3*v_isErr4 == 0)){
%>
          <a href="javascript:action('insertFileToDB')"><img src="/images/admin/button/btn_apply.gif"  border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<%
		}
%>
          <a href="javascript:history.back()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a>
<%
	}
%>
          </td>
        </tr>
      </table>
      <!--
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><a href="javascript:goback()"><img src="/images/admin/homepage/list1_butt.gif" border="0"></a></td>
        </tr>
      </table>
      -->
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
    </td>
  </tr>
</table>
<%
		if(v_isErr == 0){
%>
<script language="javascript">
alert("수강생수가 업로드한 엑셀파일과 일치하지 않아 등록할 수 없습니다.\n\n확인수정후 다시 업로드해주세요.\n\n실제 수강생수 : <%=v_student%>\n등록파일 수강생수 : <%=sheet.getRows()-2%>");
</script>
<%		
		}
		else if(v_isErr4 == 0){
%>
<script language="javascript">
alert("등록한 엑셀파일의 수료여부에 오류가 있어 등록할 수 없습니다.\n\n수료여부가 붉은색으로 표시된 부분을 다시 확인수정후 다시 업로드해주세요.");
</script>
<%		
		}
		else if(v_isErr1*v_isErr2*v_isErr3 == 0){
%>
<script language="javascript">
alert("등록한 엑셀 파일에 오류가 있어 결과등록이 불가합니다. 메세지를 확인하시고 다시 업로드해주세요.");
</script>
<%		
		}
        else if(v_process.equals("insertFileToDB")) {
			if(isOK_Final*isOK3 > 0){
%>
<script language="javascript">
alert("정상적으로 저장되었습니다.");
</script>
<%
        	}
        	else{
%>
<script language="javascript">
alert("비정상적으로 저장처리 되었습니다.\n\n일부 엑셀데이터가 저장되지 않았습니다.");
</script>
<%
        	}
		}
%>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
