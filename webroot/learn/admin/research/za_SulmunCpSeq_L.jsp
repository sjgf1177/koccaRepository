<%
//**********************************************************
//  1. 제      목: 교육과정 차수 등록관리
//  2. 프로그램명: za_cpcourseSeq_l.jsp
//  3. 개      요: 교육과정 차수 등록관리 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005.7.18
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_subj        = box.getString("p_subj");
String v_grcode      = box.getString("p_grcode");
String v_year        = box.getString("p_year");
String v_subjseq     = box.getString("p_subjseq");
String v_sulpapernum = box.getString("p_sulpapernum");


String v_cpseq      = "";
String v_cpnm       = "";
String v_userid     = "";
String v_name       = "";
String v_comptel    = "";
String v_compgubun  = "";





ArrayList list = (ArrayList)request.getAttribute("selectCompListPage");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>업데이트현황 리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript">
<!--

function InsertCpSeq(){
  //alert(window.self.name);
  if(chkSelected()<1){
    alert("매핑대상업체를 선택하여주십시오");
    return;
  }
  document.form1.p_process.value = "SulmunPaperCpInsert";
  document.form1.submit();
}


//체크박스 체크
    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
              selectedcnt++;
            }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
            selectedcnt++;
          }
        }
      }
      return selectedcnt;
    }

	//전체선택
    function whenAllSelect() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = true;
          }
        } else {
          document.form1.p_checks.checked = true;
        }
      }
    }

	// 전체선택 취소
    function whenAllSelectCancel() {
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            document.form1.p_checks[i].checked = false;
          }
        } else {
          document.form1.p_checks.checked = false;
        }
      }
    }
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form1" method="post" action="/servlet/controller.research.SulmunCpPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_subj"        value="<%=v_subj%>">
  <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
  <input type="hidden" name="p_year"        value="<%=v_year%>">
  <input type="hidden" name="p_subjseq"     value="<%=v_subjseq%>">
  <input type="hidden" name="p_sulpapernum" value="<%=v_sulpapernum%>">

<table width="620" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td align="center">
      <br>
      <table width="590" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>업체현황</td>
        </tr>
        <tr>
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
	  <table width="590"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
	          <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>

        <tr>
          <td class="table_title" width="8%">번호</td>
          <td class="table_title"  width="32%">업체명</td>
          <td class="table_title"  width="20%">업체형태</td>
          <td class="table_title"  width="30%">담당자명(id)</td>
          <td class="table_title"  width="10%"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
        </tr>

        <%for(int i = 0; i<list.size(); i++){
            DataBox dbox = (DataBox)list.get(i);

            v_cpseq     = dbox.getString("d_cpseq");
            v_cpnm      = dbox.getString("d_cpnm");
            v_userid    = dbox.getString("d_userid");
            v_name      = dbox.getString("d_name");
            v_comptel   = dbox.getString("d_comptel");
            v_compgubun = dbox.getString("d_compgubun");

            if(v_compgubun.equals("S")){
              v_compgubun = "CP";
            }else if(v_compgubun.equals("T")){
              v_compgubun = "BP";
            }else if(v_compgubun.equals("M")){
              v_compgubun = "CP+BP";
            }

        %>
        <tr>
          <td class="table_02_1"><%=i+1%></td>
          <td class="table_02_1"><%=v_cpnm%></td>
          <td class="table_02_1"><%=v_compgubun%></td>
          <td class="table_02_1"><%=v_name%>(<%=v_userid%>)</td>
          <td class="table_02_1"><input type="checkbox" name="p_checks" value="<%=v_cpseq%>" <%=dbox.getString("d_ischeck")%>></td>
        </tr>
        <%}%>
      </table>
      <!----------------- 등록 버튼 시작 ----------------->
      <br>
      <table cellspacing="0" cellpadding="0" >
        <tr>
          <td><a href="javascript:InsertCpSeq()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
          <td width="10"></td>
          <td><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
    </td>
  </tr>
</table>
<br>
</form>
</body>
</html>


