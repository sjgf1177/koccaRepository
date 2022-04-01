<%
//**********************************************************
//  1. 제      목: 인원 상세정보
//  2. 프로그램명: za_JobInclination_L.java
//  3. 개      요: 직업별 인원 상세정보
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: msc
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_grcode    = box.getString("s_grcode");        //교육그룹
    String v_subj      = box.getString("s_subjcourse"); // 과정코드

    String v_jikup     = box.getString("p_code");
    String v_codenm    = box.getString("p_codenm");
    String v_subjnm    = box.getString("p_subjnm");
    String v_grcodenm  = box.getString("p_grcodenm");

    String v_subjseq     = "";
    String v_membergubun = "";
    String v_userid      = "";
    String v_name        = "";

    int i = 0;

    ArrayList list = null;
    DataBox dbox = null;
    list = (ArrayList)request.getAttribute("JobDetailList");
%>
<html>
<head>
<title>직업별 성향 분석</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--

// 엑셀출력
function goExcel(p_action) {
    window.self.name = "JobDetailExcelList";
    open_window("openExcelList","","100","100","700","400","yes","yes","yes","yes","yes");
    document.form1.target = "openExcelList";
    document.form1.action='/servlet/controller.analysis.InclinationAdminServlet';
    document.form1.p_process.value = 'jobDetailExcelList';
    document.form1.submit();
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.analysis.InclinationAdminServlet">
    <input type="hidden" name="p_process"  value="">

    <input type="hidden" name="s_grcode"     value="<%=v_grcode%>">
    <input type="hidden" name="s_subjcourse" value="<%=v_subj%>">
    <input type="hidden" name="p_code"       value="<%=v_jikup%>">

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/analysis/tit_analysis_stu1.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

    <!--------------------닫기 버튼 시작---------------------------->
      <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
      <td align="right">
        <a href="javascript:javascript:self.close()"><img src="/images/user/kocca/button/btn_close.gif" border="0"></a>
      </td>
      </table>
    <!---------------------닫기 버튼 끝------------------------------>

        <!----------------- FORM 시작 ----------------->
       <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>교육그룹</strong></td>
            <td height="25" class="table_02_2"><%=v_grcodenm%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>과정명</strong></td>
            <td height="25" class="table_02_2"><%=v_subjnm%></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>직업</strong></td>
            <td height="25" class="table_02_2"><%=v_codenm%></td>
          </tr>
        </table>
        <!----------------- FORM 끝 ----------------->
        <br>

     <!---------------------리스트 Title 시작------------------------------>
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" height="20" class="sub_title">회원 리스트</td>
          <td align="right">
            <a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
    <!---------------------리스트 Title 끝------------------------------>

    <!---------------------리스트 시작------------------------------>
      <table class="table_out" cellspacing="1" cellpadding="5">
      <tr>
        <td colspan="11" class="table_top_line"></td>
      </tr>

      <tr>
        <td class="table_title" width="5%">NO</td>
        <td class="table_title" width="10%">회원구분</td>
        <td class="table_title" width="5%">차수</td>
        <td class="table_title" width="10%">ID</td>
        <td class="table_title" width="10%">성명</td>
      </tr>

<%
        for (i=0; i<list.size(); i++ ) {
            dbox = (DataBox)list.get(i);
            v_subjseq     = dbox.getString("d_subjseq");
            v_membergubun = dbox.getString("d_membergubun");
            v_userid      = dbox.getString("d_userid");
            v_name        = dbox.getString("d_name");

            if(v_membergubun.equals("P")){
                v_membergubun = "개인";
            } else if(v_membergubun.equals("C")){
                v_membergubun = "기업";
            } else if(v_membergubun.equals("U")){
                v_membergubun = "대학";
            }
%>
          <tr class="table_02_1">
            <td class="table_02_1"><%=list.size()-i%></td>
            <td class="table_02_1"><%= v_membergubun %></td>
            <td class="table_02_1"><%= StringManager.cutZero(v_subjseq) %>차</td>
            <td class="table_02_1"><%= v_userid %></td>
            <td class="table_02_1"><%= v_name %></td>
          </tr>
<%
        }
%>

      </table>
    <!---------------------리스트 끝------------------------------>
  </tr>

</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>