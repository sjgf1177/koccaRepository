<%
//**********************************************************
//  1. 제      목: 과정 조회화면(베타테스트)
//  2. 프로그램명: za_BetaSubject_L.jsp
//  3. 개      요: 과정 조회화면(베타테스트)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 26
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.beta.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

        String  ss_subjcourse   = box.getStringDefault("s_subjcourse","ALL");    //과정&코스
        String  v_search       = box.getString("p_search");  //검색어
        
%>
<html>
<head>
<title>과정관리-베타테스트시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function InsertSubjPage() {
    document.form2.p_process.value = 'insertPage';
    document.form2.submit();
}

function UpdateSubjPage(subj) {
  document.form2.p_process.value = 'updatePage';
  document.form2.p_subj.value  = subj;
  document.form2.submit();
}

function Preview(subj, subjnm) {
    document.form2.p_process.value = 'previewPage';
    document.form2.p_subj.value    = subj;
    document.form2.p_subjnm.value  = subjnm;
    document.form2.submit();
}

function Subjseq(subj) {
    alert('기존차수 조회화면으로 이동');
    return;
    /*
    document.form2.p_process.value = 'subjseqPage';
    document.form2.p_subj.value  = p_subj;
    document.form2.submit();
    */
}

function whenSelection() {
    document.form2.p_process.value = 'listPage';
    document.form2.submit();
}
//개인역량연결 
function whenAbility(subj){
	var url='/servlet/controller.course.AbilityAdminServlet?p_process=cntlSubjPage&p_subj='+subj;
	window.open(url,'ability','toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=600');
}



-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/b_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <form name="form2" method="post" action="/servlet/controller.beta.BetaSubjectServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_subj"     value="">
          <input type="hidden" name="p_subjnm"   value="">
		  
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                    <tr>
                      <td>
					  <%= SelectSubjBean.getBetaSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				  &nbsp;<input type="text" name="p_search" value="<%=v_search%>" size="20" maxlength="30"><a href="javascript:whenSelection()"><img src="/images/admin/button/b_go.gif" border="0"></a>
                       </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <!----------------- 추가 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right" height="20"><a href="javascript:InsertSubjPage()"><img src="/images/admin/button/btn_cyberplus.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 추가 버튼 끝 ----------------->
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="10%"><b>NO</b></td>
          <td class="table_title" width="20%"><b>과정코드</b></td>
          <td class="table_title" width="45%"><b>과정명</b></td>
          <td class="table_title" width="15%"><b>콘텐츠담당자</b></td>
          <td class="table_title" width="10%"><b>사용</b></td>
        </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("SubjectList");
    BetaSubjectData data = null;
    for (int i=0; i<list.size(); i++) {
        data  = (BetaSubjectData)list.get(i); %>
        <tr>
          <td class="table_01"><%=String.valueOf(i+1)%></td>
          <td class="table_02_1"><%=data.getSubj()%></td>
          <td class="table_02_1"><a href="javascript:UpdateSubjPage('<%=data.getSubj()%>')" class='b'><%=data.getSubjnm()%></a></td>
          <td class="table_02_1"><%=data.getName()%></td>
          <td class="table_02_1"><%=data.getIsuse()%></td> 
        </tr>
<%  } %>
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
