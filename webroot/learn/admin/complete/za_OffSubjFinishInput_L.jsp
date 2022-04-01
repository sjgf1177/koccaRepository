<%
//**********************************************************
//  1. 제      목 : 집합과장 수료자 일괄수료처리
//  2. 프로그램명 : za_OffSubjFinishInput_L.jsp
//  3. 개      요 : 집합과장 수료자 일괄수료처리
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 0.1
//  6. 작      성 : 2004/02/02
//  7. 수      정 :
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.complete.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getStringDefault("s_grseq","ALL");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_subjcourse   = box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");          //과정 차수

%>
<html>
<head>
<title>수료FileToDB </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function insert_check() {
  action("insertFileToDB");
}

function preview() {
  action("previewFileToDB");
}

function action(p_process) {
  ff1 = document.form1;
  ff3 = document.form3;

  if (ff1.s_grcode.value == 'ALL' || ff1.s_grcode.value == '----') {
         alert("교육그룹을 선택하세요.");
         return;
  }
  
  if (top.ftop != null) { 
      top.ftop.setPam();
  }
  
  if (ff1.s_subjcourse.value == '----') {
    alert("집합과정을 선택하세요.");
    return;
  }
  if (ff1.s_subjseq.value == '----') {
    alert("차수를 선택하세요.");
    return;
  }
  if(blankCheck(ff3.p_file.value)){
    alert("DB로 가져갈 파일을 선택해 주세요");
    return;
  }
  if (ff3.p_file.value.length > 0 ){
    var data = ff3.p_file.value;
    data = data.toUpperCase(data);
    if (data.indexOf(".XLS") < 0) {
      alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
      return;
    }
  }

  ff3.s_grcode.value     = ff1.s_grcode.value; 
  ff3.s_gyear.value      = ff1.s_gyear.value; 
  ff3.s_grseq.value      =  ff1.s_grseq.value; 
  //ff3.s_grseq.value      = "0001"; 
  ff3.s_subjcourse.value = ff1.s_subjcourse.value; 
  ff3.s_subjseq.value   = ff1.s_subjseq.value; 
  
  ff3.s_grname.value = ff1.s_grcode.options[ff1.s_grcode.selectedIndex].text;
  ff3.s_subjnm.value = ff1.s_subjcourse.options[ff1.s_subjcourse.selectedIndex].text;
  ff3.s_subjseqnm.value = ff1.s_subjseq.options[ff1.s_subjseq.selectedIndex].text;

  ff3.action = "/servlet/controller.complete.FinishInputServlet";
  ff3.p_process.value = p_process;
  ff3.submit();
}
function whenSelection(p_action) {
  ff1 = document.form1;

  ff1.p_process.value = 'listPage';
  ff1.p_action.value = p_action;
  ff1.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
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
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
      <form name="form1" method="post" action="/servlet/controller.complete.FinishInputServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="isOffSubj" value="Y">
        
        <tr> 
          <td align="center"> 
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="7"></td>
              </tr>
              <tr> 
                <td align="center"> 
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr> 
                      <td><font color="red">★</font> 
                          <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                          <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                          <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  --><br>
                          <font color="red">★</font> <%= SelectSubjBean.getSubj(box, true, false, false, true)%><!-- getSubj(RequestBox, isChange, isALL, justOn, justOff)    집합과정  -->
                          <%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->

                      </td>
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
      </form>  
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <!----------------- 찾아보기 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
      <form name="form3" method="post" action="/servlet/controller.complete.FinishInputServlet" enctype="multipart/form-data">
      
        <input type="hidden" name="s_grname">
        <input type="hidden" name="s_subjnm">
        <input type="hidden" name="s_subjseqnm">
        
        <input type="hidden" name="s_grcode"      value="<%=ss_grcode%>">
        <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="s_grseq"       value="<%=ss_grseq%>">
        <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
        <input type="hidden" name="s_subjseq"     value="<%=ss_subjseq%>">

    
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="isOffSubj"    value="Y">
        
        <tr> 
          <td align="center"> 
            <table width="21%" height="26" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td align="center"> 
                  <input name="p_file" type="FILE" class="input" size=70>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </form>  
      </table>
      <!----------------- 찾아보기 끝 ----------------->
      <br>
      <!----------------- 등록, 미리보기 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="930" height="20" align="right"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>
          <td width=8></td>
          <td align="right" width="32"><a href="javascript:preview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 미리보기 버튼 끝 ----------------->
      <br>
      <br>
      <!----------------- 주의사항 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="heed_table">
        <tr> 
          <td class="text_02">(주의사항)</td>
        </tr>
        <tr> 
          <td height="8"></td>
        </tr>
        <tr> 
          <td class="heed_table">1. 엑셀파일 저장시 엑셀 형식으로 저장하여 사용할 것</td>
        </tr>
        <tr> 
          <td class="heed_table">2. 엑셀파일 작성시 [<b><font color="#003AEA">ID, 차수, 
            출석률점수,평가점수,리포트점수, 기타점수</font></b>]의 순서로 
            만들 것.</td>
        </tr>
        <tr> 
          <td class="heed_table">3. <font color="red">샘플 양식 변경시 오류가 발생할 수 있습니다.</font></td>
        </tr>
        <tr>
          <td class="heed_table">4. 샘플 파일을 참고할 것. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=OffSubj.xls&p_realfile=OffSubj.xls'><b>샘플파일</b></a>)</td>
        </tr>
        <tr>
          <td class="heed_table">5. 차수를 ALL로 선택한 경우에는 차수를 입력하여야 합니다.</td>
        </tr>
        <tr>
          <td class="heed_table">6. 점수는 가중치를 적용하지 않은 100점에 대한 점수를 입력합니다.</td>
        </tr>
        <tr>
          <td class="heed_table">7. 점수가 없는 경우 0점으로 입력하여야 합니다..</td>
        </tr>
      </table>
      <!----------------- 주의사항 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><img src="/images/admin/complete/OffSubj.gif" border="0"></td>
        </tr>
      </table>      
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>      
</table>
</body>
</html>
