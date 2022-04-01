<%
//**********************************************************
//  1. 제      목: 클래스배분 FileToDB
//  2. 프로그램명 : za_ClassFilToDB_P.jsp
//  3. 개      요: 클래스배분 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진 2005.7.05
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
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.study.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;

    ProposeBean proposebean = null;
    ClassBean   classbean   = null;

    Hashtable inputdata  = new Hashtable();
    Hashtable outputdata = new Hashtable();

    int isOk = 0;
    int isOk1 = 0;

    String  v_process   = box.getString("p_process");

    String  ss_grcode   = box.getString("s_grcode");           //교육주관
    String  ss_gyear    = box.getString("s_gyear");            //년도
    String  ss_grseq    = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm  = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq  = box.getString("s_subjseq");          //과정 차수

    String v_subjnm   = box.getString("s_subjnm");
    String v_grcodenm = box.getString("s_grcodenm");
    String v_subjseqgrnm = box.getString("s_subjseqgrnm");


    String  v_luserid   = box.getSession("userid");


    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");
//out.println("real filename111 ==> "+v_realFileName);
//out.println("new filename111 ==> "+v_newFileName);
//if(true) return;
    int     i=0;
    String  v_class     = "";
    String  v_classnm   = "";
    String  v_userid    = "";
    String  v_tutor     = "";
    String  v_subj      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";

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
  document.form1.action = "/servlet/controller.study.ClassServlet";
  document.form1.p_process.value = "classFileToDBPage";
  document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="800" border="0" cellspacing="0" cellpadding="0" height="663">

<form name="form1" method="post" >
  <input type="hidden" name="p_process" value="">
  <input type="hidden" name="s_grcode"  value="<%=ss_grcode%>">
  <input type="hidden" name="s_gyear"   value="<%=ss_gyear%>">
  <input type="hidden" name="s_grseq"   value="<%=ss_grseq%>">
  <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
  <input type="hidden" name="s_middleclass"  value="<%=ss_middleclass%>">
  <input type="hidden" name="s_subjcourse" value="<%=ss_subjcourse%>">

</form>

  <tr>
    <td align="center" valign="top">
            <!----------------- title 시작----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/study/tit_class.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
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
                        <b><font size="3">&nbsp;&nbsp;교육그룹:<%=v_grcodenm%>&nbsp;&nbsp;&nbsp;&nbsp;년도:<%=ss_gyear%>&nbsp;&nbsp;&nbsp;&nbsp;과정:<%=v_subjnm%>&nbsp;&nbsp;&nbsp;&nbsp;차수:<%=v_subjseqgrnm%></font></b>
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
      <br><br>
      <br>
      <!---------------------------------------------------------------------------->

<%  try {

        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.class")+v_newFileName));
        sheet = workbook.getSheet(0);

        proposebean = new ProposeBean();
        classbean   = new ClassBean();
        MainSubjSearchBean msbean = new MainSubjSearchBean();
        FinishBean fbean = new FinishBean();
        DecimalFormat df = new DecimalFormat("0000");

        String v_errvalue = "";
        String v_errtxt = "";
        String v_year = "";

        String v_isonoff  = "";
        String v_isclosed = "";
        String v_edustart = "";
        String v_eduend   = "";

        String v_errString = "";
        String v_okString = "";


        int errcnt = 0;
        int okcnt = 0;

//        out.println(sheet.getRows());
        for (i=1; i < sheet.getRows() ; i++ ) {
            v_class  = StringManager.trim( sheet.getCell(0,i).getContents());  // 클래스
            v_class = df.format(Integer.parseInt(v_class));
            v_classnm = ClassBean.CLASSNM + v_class;
            v_userid = StringManager.trim( sheet.getCell(1,i).getContents());  // 아이디
            v_tutor = StringManager.trim( sheet.getCell(2,i).getContents());  // 튜터아이디

            // 과정 ALL 일경우
            if(ss_subjcourse.equals("ALL")){
              // 과정 체크
              v_subj = StringManager.trim(sheet.getCell(3,i).getContents());  // 과정
              if(v_subj.equals("")){
                out.println("<font color=red><b>엑셀양식Error</b><br><b>과정ALL선택시에는 엑셀 입력양식에 과정을 입력하여야 합니다.</b></font>");
                return;
              }
              // 차수 체크
              v_subjseq = StringManager.trim(sheet.getCell(4,i).getContents());  // 차수
             if(v_subjseq.equals("")){
                out.println("<font color=red><b>엑셀양식Error</b><br><b>과정ALL선택시에는 엑셀 입력양식에 차수를 입력하여야 합니다.</b></font>");
                return;
              }

              v_subjseqgr = df.format(Integer.parseInt(v_subjseq));
              v_subjseq = fbean.selectGrSubjseq(connMgr, v_subj, ss_gyear, v_subjseqgr, ss_grcode);
//out.println(v_subjseq);
            }else{
              v_subj = ss_subjcourse;
              v_subjseqgr = StringManager.replace(v_subjseqgrnm,"차","");
              v_subjseq = ss_subjseq;
            }

            v_year = proposebean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, v_subj, v_subjseq);
//out.println(v_year);
            DataBox dbox = msbean.SubjseqInfo(v_subj, ss_grcode, v_subjseq, v_year);
            if(dbox != null){
              v_isonoff  = dbox.getString("d_isonoff");
              v_isclosed = dbox.getString("d_isclosed");
              v_edustart = dbox.getString("d_edustart");
              v_eduend   = dbox.getString("d_eduend");
            }

//out.println("v_edustart"+ v_edustart);
//out.println("v_eduend"+ v_eduend);

            if (v_process.equals("insertFileToDB")) {
                //차수 존재 여부 체크
                v_errvalue=msbean.isExitSeq(ss_grcode, ss_gyear, v_subj, v_subjseq);
//out.println(v_errvalue);
                //인사DB검색(Member 존재 여부 체크)
                if(v_errvalue.equals("0")){
                    v_errvalue=msbean.isExitMember(v_userid);
                    //학습 테이블 검색
                    if(v_errvalue.equals("0")){
                        v_errvalue=msbean.isExitStudent(v_userid, v_subj, v_year, v_subjseq);
                    }
                    //튜터DB 검색(TUTOR 존재 여부 체크)
                    if(v_errvalue.equals("0")){
                        v_errvalue=msbean.isExitTutor(v_tutor);
                    }
                }

                // Message 출력
                v_errtxt = msbean.isGetErrtxt(v_errvalue);

                if(v_errvalue.equals("0")){
                  inputdata.clear();
                  inputdata.put("connMgr", connMgr);
                  inputdata.put("subj",     v_subj);
                  inputdata.put("year",     v_year);
                  inputdata.put("subjseq",  v_subjseq);
                  inputdata.put("userid",   v_userid);
                  inputdata.put("class",    v_class);
                  inputdata.put("classnm",    v_classnm);
                  inputdata.put("tutorid",  v_tutor);
                  inputdata.put("luserid",  v_luserid);
                  inputdata.put("edustart", v_edustart);
                  inputdata.put("eduend",   v_eduend);
                  inputdata.put("classtype", ClassBean.PLURAL_CLASS);

                   outputdata = classbean.FileToDB(inputdata);

                  isOk = Integer.valueOf((String)outputdata.get("p_errorcode")).intValue();
//out.println("isOk :" + isOk);
                    if(isOk > 0) {
                    } else {
                        ex01 = (Exception)outputdata.get("p_exception");
//                        throw(ex01);
                        v_errvalue = "999";
                        v_errtxt = msbean.isGetErrtxt(v_errvalue);
                    }
                }
                else{
                  errcnt++;
                }

            } else {  // 미리보기

              //차수 존재 여부 체크
              v_errvalue=msbean.isExitSeq(ss_grcode, ss_gyear, v_subj, v_subjseq);

              //인사DB검색(Member 존재 여부 체크)
              if(v_errvalue.equals("0")){
                v_errvalue=msbean.isExitMember(v_userid);
                    //학습 테이블 검색
                    if(v_errvalue.equals("0")){
                        v_errvalue=msbean.isExitStudent(v_userid, v_subj, v_year, v_subjseq);
                    }
                    //튜터DB 검색(TUTOR 존재 여부 체크)
                    if(v_errvalue.equals("0")){
                        v_errvalue=msbean.isExitTutor(v_tutor);
                    }
              }

              v_errtxt = msbean.isGetErrtxt(v_errvalue);

            }

            if(!v_errvalue.equals("0")){
              v_errString += "<tr><td class=table_02_1>"+(i)+"</td><td class=table_02_1>" + v_class + "</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + v_tutor + "</td><td class=table_02_1>" + v_subj + "</td><td class=table_02_1>" + StringManager.cutZero(v_subjseqgr)+"차</td><td class=table_02_1>"+v_errtxt+"</td>";
              errcnt++;
            } else{
              v_okString  += "<tr><td class=table_02_1>"+(i)+"</td><td class=table_02_1>" + v_class + "</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + v_tutor + "</td><td class=table_02_1>" + v_subj + "</td><td class=table_02_1>" + StringManager.cutZero(v_subjseqgr)+"차</td><td class=table_02_1>"+v_errtxt+"</td>";
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
            <td class="table_title" width="10%">클래스</td>
            <td class="table_title" width="15%">사번</td>
            <td class="table_title" width="15%">튜터아이디</td>
            <td class="table_title" width="15%">과정</td>
            <td class="table_title" width="15%">입력차수</td>
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
            <td class="table_title" width="10%">클래스</td>
            <td class="table_title" width="15%">사번</td>
            <td class="table_title" width="15%">튜터아이디</td>
            <td class="table_title" width="15%">과정</td>
            <td class="table_title" width="15%">입력차수</td>
            <td class="table_title" >MESSAGE</td>
          </tr>
          <%=v_okString%>
      </table>
      <!----------------- 결과 정보 출력 끝 ----------------->

<%
        if(v_process.equals("insertFileToDB")){
//out.println("errcnt : " + errcnt);
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
            <font color=red>1. 과정 ALL선택후 과정/차수를 입력하지 않은경우 Error처리 됩니다.</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>&nbsp;&nbsp;-->과정/차수입력후 다시 시도하여 주십시오.</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>2. 엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br>
          </td>
        </tr>
        <tr>
          <td>
            <font color=red>&nbsp;&nbsp;--> 입력한 영역<b>(클래스,사번,강사아이디, 과정, 차수)</b>만 지정하고 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br>
                            &nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br>
          </td>
        </tr>
      </table>
<%

        } finally {
                //첨부파일삭제
            FileManager.deleteFile(conf.getProperty("dir.upload.accept")+v_newFileName);
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><a href="javascript:golistPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
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