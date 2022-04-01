<%
//**********************************************************
//  1. 제      목: 교육과정 등록관리
//  2. 프로그램명: za_sul_l.jsp
//  3. 개      요: 교육과정 등록관리 리스트화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%//@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

int i = 0, v_dispnum = 0, v_totalpage = 0, v_rowcount = 0;
int v_totcnt      = 0;
int v_sulpapernum = 0;
int v_replycnt = 0;

String v_subj        = "CP";
String v_grcode      = "ALL";
String v_subjseq     = "0001";
String v_year        = "";
String v_cpseq       = "";
String v_sulnums     = "";
String v_sulpapernm  = "";
String v_cpstatus    = "";
String v_cpnm        = "";

int ss_sulpapernum = box.getInt("s_sulpapernum");
//out.println(ss_sulpapernum);
String v_gyear = box.getStringDefault("p_year", FormatDate.getDate("yyyy"));


ArrayList list = (ArrayList)request.getAttribute("selectCourseList");
String v_searchtext = box.getString("p_searchtext");

String ss_gadmin = box.getSession("gadmin");
String v_gadmin = StringManager.substring(ss_gadmin, 0, 1);
//String v_select = box.getString("p_select");
int v_pageno = box.getInt("p_pageno");

//String s_userid = box.getSession("userid");
//String s_usernm = box.getSession("username");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language="javascript" src = "/script/cresys_lib.js"></script>
<script language="javascript">
<!--
function whenSelection(arg){
    
	document.form1.action = "/servlet/controller.cp.CPSulServlet";
	document.form1.p_process.value = "";
	document.form1.target="_self";
	document.form1.submit();
}

function selectList() {
	document.form1.action = "/servlet/controller.cp.CPSulServlet";
	document.form1.p_process.value = "";
	document.form1.target="_self";
	top.ftop.setPam();
	document.form1.submit();
}

function subjInfo(subj){
	document.form1.action = "/servlet/controller.cp.CPSulServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.p_subj.value = subj;
	document.form1.target="_self";
	document.form1.submit();
}


// 과정 설문응시
function contentsWright(p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulnums, p_cpseq, p_replaycnt) {
  //alert("11111");
  document.form1.p_subj.value = p_subj;
  document.form1.p_grcode.value = p_grcode;
  document.form1.p_gyear.value = p_gyear;
  document.form1.p_subjseq.value = p_subjseq;
  document.form1.p_sulpapernum.value = p_sulpapernum;
  document.form1.p_sulpapernm.value = p_sulpapernm;
  document.form1.p_sulnums.value = p_sulnums;    // 문제번호    
  document.form1.p_cpseq.value = p_cpseq;    
  document.form1.p_replaycnt.value = p_replaycnt;    

  window.self.name ="winSelectView";
  //open_window("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
  //alert(toolbar='+toolbar_str+',menubar='+menubar_str+',status='+statusbar_str+',scrollbars='+scrollbar_str+',resizable='+resizable_str);
  open_window("openSubjChange","","100","100","680","538","no","no","no","yes","no");  

  document.form1.target="openSubjChange";
  document.form1.action="/servlet/controller.cp.CPSulServlet";
  document.form1.p_process.value="SulmunUserPaperListPage";
  document.form1.submit();
}

function whenExcelDown1(){
  if(document.form1.s_sulpapernum.value == 0){
    alert("설문지를 선택하여주십시오!!");
    return;
  }
  var p_year        = document.form1.p_year.value;
  var p_sulpapernum = document.form1.s_sulpapernum.value;
  //document.form1.action="/servlet/controller.cp.CPSulServlet";
  //document.form1.p_process.value="testpage";
  document.form1.action="/cp/admin/za_cpSulResult_L.jsp?s_sulpapernum="+p_sulpapernum+"&p_year="+p_year;
  document.form1.submit();
}

function ReloadPage() {
  document.form1.p_process.value = 'listPage';
  document.form1.target = "_self";
  document.form1.submit();
}

// 레포팅출력
function whenExcelDown() {
	window.self.name = "CpStudentSelectExcel";
	open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
	document.form1.target = "openExcel";
	document.form1.p_process.value = "excelDown";
	document.form1.action='/servlet/controller.cp.CPSulServlet';
	document.form1.submit();
}
-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form1" action="" method="post">
<input type="hidden" name="p_process" value="">
<input type="hidden" name="p_subj" value="">
<input type="hidden" name="p_grcode" value="">
<input type="hidden" name="p_gyear" value="">
<input type="hidden" name="p_subjseq" value="">
<input type="hidden" name="p_sulpapernum" value="">
<input type="hidden" name="p_sulpapernm" value="">
<input type="hidden" name="p_cpseq" value="">
<input type="hidden" name="p_sulnums" value="">
<input type="hidden" name="p_replaycnt" value="">
<input type="hidden" name="p_listok" value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">

	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
            <td><img src="/images/admin/cp/cp_title13.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>

      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>업체설문</td>
        </tr>
        <tr>
          <td height=6></td>
          <td height=6></td>
        </tr>
      </table>
      
	  <table width="970" cellpadding="1" cellspacing="0" class="form_table_out">
        <tr>
          <td align="center"><table width="970" cellpadding="0" cellspacing="0" class="form_table_bg">
              <tr>
                <td height="7"></td>
              </tr>

              <tr>
                <td align="center"> <table  border="0" cellspacing="0" cellpadding="0">
                    <tr>
					  <td>개설년도</td>
					  <td>&nbsp; <%= CPCommonBean.getYear(box, true, true, "2004","p_year")%><!-- getCompany(RequestBox, isChange, isALL)    연도  --></td>
					  <td>&nbsp;</td><td  align="right">&nbsp;</td>
					  <td>교육업체</td>
                      <td> &nbsp;<%= CPCommonBean.getCP(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    교육기관  --></td>
					  <td width=20></td>
					  <!--"N000001" , v_gyear, "CP", ""-->
					  <td>설문지 <%=CPCommonBean.getSulpaperSelect(v_grcode, v_gyear, v_subj, "s_sulpapernum", ss_sulpapernum, "whenSelection('go')")%></td>
					  
					  <td width=20></td>
					  <td>과정명</td>
					  <td width=6></td>
					  <td><input name="p_searchtext" type="text" class="input" style="width:120" value="<%=v_searchtext %>"></td>
					  <td width=6></td>
                      <td><a href="javascript:selectList();"><img src="/images/admin/button/b_go.gif"  border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table></td>
        </tr>
      </table>
	  <br>
	  <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width=90%></td>
            <td align="right" height="20" width="5%"><!--a href="javascript:whenExcelDown1()">테스트</a--></td>
            <td align="right" height="20" width="5%"><a href="javascript:whenExcelDown()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
          </tr>
          <tr>
            <td height="3" colspan="2"></td>
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr>
          <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr>
        <%if(!ss_gadmin.equals("S1") && !ss_gadmin.equals("T1") && !ss_gadmin.equals("M1")){%>
          <td class="table_title"  width="6%">번호</td>
          <td class="table_title"  width="25%">설문지명</td>
          <td class="table_title"  width="19%">업체명</td>
          <td class="table_title"  width="15%">업체입력</td>
          <td class="table_title"  width="15%">AES입력</td>
        <%}else{%>
          <td class="table_title"  width="6%">번호</td>
          <td class="table_title"  width="30%">설문지명</td>
          <td class="table_title"  width="19%">업체명</td>
          <td class="table_title"  width="20%">설문입력</td>
        <%}%>
        </tr>

<%
if (list != null && list.size() > 0){
	for(i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);

		//sql = "select subj, subjnm, isonoff, eduurl, preurl, conturl, cpsubj, cpnm from tz_subj ";

        v_subj          = dbox.getString("d_subj");
        v_grcode        = dbox.getString("d_grcode");
        v_subjseq       = dbox.getString("d_subjseq");
        v_year          = dbox.getString("d_year");
        v_sulpapernum   = dbox.getInt   ("d_sulpapernum");
        v_cpseq         = dbox.getString("d_cpseq");
        v_totcnt        = dbox.getInt("d_totcnt");
        v_sulnums       = dbox.getString("d_sulnums");
        v_sulpapernm    = dbox.getString("d_sulpapernm");
        v_cpstatus      = dbox.getString("d_cpstatus");
        v_cpnm          = dbox.getString("d_cpnm");
        v_replycnt      = dbox.getInt("d_replycnt");
%>

        <tr>
          <td class="table_02_1"><%=i+1%></td> 
          <td class="table_02_1"><%=v_sulpapernm%></td>
          <td class="table_02_1"><%=v_cpnm%></td>
          <td class="table_02_1">
          <%if(v_replycnt < 1){%>
            <%if(ss_gadmin.equals("S1") || ss_gadmin.equals("T1") || ss_gadmin.equals("M1")){%>
            <a href="javascript:contentsWright('<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>','<%=v_sulnums%>', '<%=v_cpseq%>', '<%=v_replycnt%>')">입력</a>
            <%}else{%>
            <a href="javascript:contentsWright('<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>','<%=v_sulnums%>', '<%=v_cpseq%>', '<%=v_replycnt%>')"><font color="red"><b>미입력</b></a>
            <%}%>
          <%}else{%>
            <font color="blue"><b>입력완료</b></font>
          <%}%>
          </td>
          <%if(!ss_gadmin.equals("S1") && !ss_gadmin.equals("T1") && !ss_gadmin.equals("M1")){%>
          <td class="table_02_1">
          <%if(v_replycnt > 0){%>
            <%if(!dbox.getString("d_cpstatus").equals("Y")){%>
              <a href="javascript:contentsWright('<%=v_subj%>', '<%=v_grcode%>', '<%=v_year%>', '<%=v_subjseq%>', '<%=v_sulpapernum%>', '<%=v_sulpapernm%>','<%=v_sulnums%>', '<%=v_cpseq%>', '<%=v_replycnt%>')">입력</a>
            <%}else{%>
              <font color="blue"><b>입력완료</b></font>
            <%}%>
          <%}else{%>
            -
          <%}%>
          </td>
          <%}%>
        </tr>

<%
	}
}else if(list != null){
%>
        <tr>
          <td class="table_02_1" colspan="11" height="28" bgcolor="#ffffff">조회된 데이터가 없습니다.</td>
        </tr>
<%
}
%>
      </table>
    </td>
  </tr>
  <tr height="100">
    <td>
      <%@ include file = "/learn/library/getJspName.jsp" %>
    </td>
  </tr>
</table>
</form>
</body>
</html>

