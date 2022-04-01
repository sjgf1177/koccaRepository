<%
//**********************************************************
//  1. 제      목: 클래스배분 FileToDB
//  2. 프로그램명 : za_ClassFileToDB.jsp
//  3. 개      요: 클래스배분 FileToDB
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode       = box.getString("s_grcode");           //교육그룹
    String  ss_gyear        = box.getString("s_gyear");            //년도
    String  ss_grseq        = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");          //과정 차수

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
// 등록
function insert_check() {
  action("insertFileToDB");
}

// 미리보기
function preview() {
  action("previewFileToDB");
}

// 프로세스별로 처리
function action(p_process) {
  if(blankCheck(document.form1.p_file.value)){
    alert("DB로 가져갈 파일을 선택해 주세요");
    return;
  }
  if (document.form1.p_file.value.length > 0 ){
    var data = document.form1.p_file.value;
    data = data.toUpperCase(data);
    if (data.indexOf(".XLS") < 0) {
      alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
      return;
    }
  }

    if (chkParam() == false) {
      return;
    }

  document.form1.s_subjnm.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].text;
  document.form1.s_grcodenm.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].text;
  document.form1.s_subjseqgrnm.value = document.form1.s_subjseq.options[document.form1.s_subjseq.selectedIndex].text;

  document.form1.target = "_self";
  document.form1.action = "/servlet/controller.study.ClassServlet";
  document.form1.p_process.value = p_process;                              //insertFileToDB,previewFileToDB
  document.form1.submit();
}

// 검색조건 체크
function chkParam() {
  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
    alert("교육그룹을 선택하세요.");
    return false;
  }
  if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
    alert("교육차수를 선택하세요.");
    return false;
  }
  if (document.form1.s_subjcourse.value != 'ALL' && document.form1.s_subjcourse.value != '----') {
      if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '----') {
        alert("과정차수를 선택하세요.");
        return false;
      }
  }
}

// 교육차수 내 과정코드 엑셀파일
function whenExcel() {

    window.self.name = "classTutor";
    open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
    document.form1.target = "openExcel";
    document.form1.action='/servlet/controller.study.ClassServlet';
    document.form1.p_process.value = 'classStudentListExcel';
    document.form1.submit();

    document.form1.target = window.self.name;
}

// 검색
function whenSelection(p_action) {
  document.form1.p_process.value = 'classFileToDBPage';
  document.form1.p_action.value = p_action;
  document.form1.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" action="/servlet/controller.study.ClassServlet" enctype="multipart/form-data">
    <input type="hidden" name="p_process" value="">
    <input type="hidden" name="p_action"  value="">

    <input type="hidden" name="s_subjnm"      value="">
    <input type="hidden" name="s_grcodenm"   value="">
    <input type="hidden" name="s_subjseqgrnm"   value="">

<table width="700" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/study/tit_class.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <!----------------- SELECT 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="40%" colspan="2" align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td width="60%" colspan="2">
                        <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                        <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                        <!--input type= "hidden" name="s_grseq" value="0001"-->
                      </td>
                    </tr>
                    <tr>
                        <td height="5"></td>
                    </tr>

<!--
                    <tr>
                      <td align="left" colspan="3">
                        <%= SelectSubjBean.getUpperClass(box, true, true, true)%>< !-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -- >
                        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%>< !-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -- >
                        <%= SelectSubjBean.getLowerClass(box, true, true, true)%>< !-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -- >
                        </td>
                     </tr>
                    <tr>
                        <td height="5"></td>
                    </tr>
-->
                    <tr>
                      <td align="left" colspan="3">
                        <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                        <%= SelectSubjBean.getSubjseq(box, true, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                        <!-------------------- 조건검색 끝 ---------------------------->
                        </td>
                     </tr>
                    <tr>

                  </table>
                </td>
              </tr>
			  <td align="right">
				 <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_coursecode_excell.gif" border="0"></a>
				</td>
			 </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- SELECT 끝 ----------------->
      <br>
      <!----------------- 찾아보기 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">

        <tr>
          <td align="center">
            <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center">
                  <input name="p_file" type="FILE" class="input" size="100">
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 찾아보기 끝 ----------------->
      <br>
      <!----------------- 미리보기, 확인, 취소 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="928" height="20" align="right"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_add.gif"  border="0"></a></td>
          <td width=10></td>
          <td align="right" width="32"><a href="javascript:preview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
        </tr>
      </table>

      <!----------------- 미리보기, 확인, 취소 버튼 끝 ----------------->
      <br>
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class=dir_txt ><b><font size="3">(주의사항 - 필독)</font></b></td>
        </tr>
        <tr>
          <td height="8"></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >1. <font color="#E60873">과정과 차수선택후 업로드시에는 클래스,학습자ID,강사ID 필드만 입력할것.(과정선택+차수선택)</font>( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=ClassExcel1.xls&p_realfile=ClassExcel1.xls'><b>샘플파일1</b></a>)</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >2. <font color="#E60873">과정 ALL선택후 업로드시에는 엑셀파일작성시 과정칸/차수칸을 반드시 입력할것.(과정ALL + 차수ALL)</font>( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=ClassExcel2.xls&p_realfile=ClassExcel2.xls'><b>샘플파일2</b></a>)</td>
        </tr>
        <tr>
        <tr>
          <td height="20" class=dir_txt >3. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >4. 엑셀파일 작성시 <font color="#A661D7">[<b>클래스, 학습자ID, 강사ID, ( 과정 ), ( 차수 ) </b>]</font>의 순서로 만들 것.</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align='left'>샘플파일1</td>
        </tr>
        <tr>
          <td><img src="/images/admin/propose/ClassExcel1.gif" border="0"></td>
        </tr>
      </table>
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align='left'>샘플파일2</td>
        </tr>
        <tr>
          <td><img src="/images/admin/propose/ClassExcel2.gif" border="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
