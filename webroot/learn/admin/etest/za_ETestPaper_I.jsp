<%
/**
 * file name : za_ETestPaper_I.jsp
 * date      : 2003/09/04
 * programmer:
 * function  : 평가문제지 등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_upperclass= box.getString("p_upperclass");
    String  v_subj      = box.getString("p_subj");
    String  v_year       = box.getString("p_year");
    String  v_subjseq    = box.getString("p_subjseq");

    DataBox dbox = (DataBox)request.getAttribute("ETestPaperMaster");
%>
<html>
<head>
<title>평가문제지등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_exam.css" type="text/css">
<script language="JavaScript">
<!--
function InsertPaper() {
  if (!chkData()) {
    return;
  }
  document.form2.p_process.value = 'ETestPaperInsert';
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}
function goMasterListPage() {
  document.form2.p_process.value = 'ETestPaperListPage';
  document.form2.p_action.value  = 'go';
  document.form2.submit();
}
function chkData() {
  var k = 0;
  var v_create_method = new Array;
  var v_before_paper  = new Array;
  for (i=0; i<document.form2.length; i++) {
    if (document.form2.elements[i].name == 'p_create') {
      v_create_method[k] = document.form2.elements[i].options[document.form2.elements[i].selectedIndex].value;
    }
    if (document.form2.elements[i].name == 'p_before_paper') {
      v_before_paper[k] = document.form2.elements[i].selectedIndex;
      k++;
    }
  }
  for (i=0; i<k; i++) {
    if (v_create_method[i] == '2' && v_before_paper[i] < 0) {
      alert('기존 차수를 선택하세요');
      return false;
    }
  }
  return true;
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명, 년도, 차수 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="3">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table width="98%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td width="10%" height="25">◈<b> 과정명</b></td>
                      <td width="25%" align="left">: <%=box.getString("p_subjnm")%>(<%=v_subj%>)</td>
                      <td width="10%">◈<b> 년도</b></td>
                      <td width="22%" align="left">: <%=v_year%></td>
                      <td width="10%">◈<b> 차수</b></td>
                      <td width="22%" align="left">: <%=v_subjseq%></td>
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
      <!----------------- 과정명, 년도, 차수 끝 ----------------->
      <br>
      <br>
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td><b>☞ 자동 생성할 경우에는 저장버튼만 누르시고, 다른 차수의 문제지를 copy할 경우에는 차수를 선택하세요.</b></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 평가문제지 수정 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestPaperServlet">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="p_action"     value="">
        <input type="hidden" name="p_upperclass" value="<%=v_upperclass%>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">

        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title_01">문항수<br>(상/중/하 : 전체)</td>
          <td class="table_title_01">기본점수</td>
          <td class="table_title_01">선택</td>
          <td class="table_title_01">차수선택</td>
        </tr>
            <input type="hidden" name="p_cntlevel1"  value="<%=dbox.getInt("d_cntlevel1")%>">
            <input type="hidden" name="p_cntlevel2"  value="<%=dbox.getInt("d_cntlevel2")%>">
            <input type="hidden" name="p_cntlevel3"  value="<%=dbox.getInt("d_cntlevel3")%>">
            <input type="hidden" name="p_cntsubjectetest" value="<%=dbox.getInt("d_cntsubjectetest")%>">
        <tr>
          <td class="table_02_1">
            <%=dbox.getInt("d_cntlevel1") + "/" + dbox.getInt("d_cntlevel2") + "/" + dbox.getInt("d_cntlevel3") + " : " + (dbox.getInt("d_cntlevel1")+dbox.getInt("d_cntlevel2")+dbox.getInt("d_cntlevel3"))%></td>
          <td class="table_02_1">
            <input name="p_grbase" type="text" class="input" size="4" value="0" maxlength="4">
          </td>
          <td class="table_02_1">
            <%= ETestBean.getCodenm(false, false, "s_create", ETestBean.PAPER_CREATE)%><% //-- getCodenm(isChange, isALL, selectbox name, gubun code) ---%>
          </td>
          <td class="table_02_1">
            <%= ETestBean.getBeforePaper(v_subj, v_subjseq)%>
          </td>
        </tr>
      </form>
      </table>
      <!----------------- 평가문제지 수정 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertPaper()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goMasterListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
