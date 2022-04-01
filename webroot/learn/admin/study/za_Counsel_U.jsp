<%
//**********************************************************
//  1. 제      목: 상담내역 수정
//  2. 프로그램명: za_Counsel_U.jsp
//  3. 개      요: 상담내역 수정/조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_userid    = box.getString("p_userid");
    int     v_no       = box.getInt("p_no");

    int     v_pageno = box.getInt("p_pageno");


    String v_cuserid = ""; 
    String v_name    = ""; 
    String v_title   = "";
    String v_ftext   = "";
    String v_ctext   = "";
    String v_mcode   = "";
    String v_subj    = "";
    String v_year    = "";
    String v_subjseq = "";
    String v_etime   = "";
    String v_status  = "";
    String v_sdate   = "";
    String v_ldate   = "";
	String v_gubun   = "";
	String v_cname   = "";
    String v_sdate_view = "";
    String v_ldate_view = "";
    int i = 0;

    DataBox dbox   = (DataBox)request.getAttribute("selectCounsel");

    if (dbox != null) {
        v_userid  = dbox.getString("d_userid");
        v_name    = dbox.getString("d_name");
        v_cuserid = dbox.getString("d_cuserid");
        v_title   = dbox.getString("d_title");
        v_ftext   = dbox.getString("d_ftext");
        v_ctext   = dbox.getString("d_ctext");
        v_mcode   = dbox.getString("d_mcode");
        v_subj    = dbox.getString("d_subj");
        v_year    = dbox.getString("d_year");
        v_subjseq = dbox.getString("d_subjseq");
        v_etime   = dbox.getString("d_etime");
        v_status  = dbox.getString("d_status");
        v_sdate   = dbox.getString("d_sdate");
        v_ldate   = dbox.getString("d_ldate");
		v_gubun   = dbox.getString("d_gubun");
		v_cname   = dbox.getString("d_cname");
        v_sdate_view = FormatDate.getFormatDate(v_sdate,"yyyy/MM/dd");
        v_ldate_view = FormatDate.getFormatDate(v_ldate,"yyyy/MM/dd");

        if (v_etime.equals("")) v_etime="0";
    }
%>
<html>
<head>
<title>상담내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate_view").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
	// 체크 후 수정
    function update_check() {
        if (document.form1.p_subj.value == "") {
            alert("과정을 선택하세요");
            document.form1.p_subj.focus();
            return;
        }
        if (document.form1.p_status.value == "") {
            alert("처리상태를 선택하세요");
            document.form1.p_status.focus();
            return;
        }
        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }
        if (document.form1.p_ctext.value == "") {
            alert("내용을 입력하세요");
            document.form1.p_contents.focus();
            return;
        }
        if (realsize(document.form1.p_ctext.value) > 4000) {
            alert("내용은 한글기준 2000자를 초과하지 못합니다.");
            document.form1.p_ctext.focus();
            return;
        }
        if (realsize(document.form1.p_ftext.value) > 4000) {
            alert("처리내용은 한글기준 2000자를 초과하지 못합니다.");
            document.form1.p_ftext.focus();
            return;
        }
        if (!number_chk_noalert(document.form1.p_etime.value)) {
          alert('상담시간이 잘못입력되었습니다.');
		  document.form1.p_etime.focus();
          return;
        }
        var p_sdate = make_date(document.form1.p_sdate_view.value);
        if (p_sdate == "") {
            alert("상담일자을 입력하세요");
            return;
        }

        document.form1.p_sdate.value = p_sdate;

        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.p_process.value = "CounselUpdate";
        document.form1.submit();
    }

    function delete_check() {
        document.form1.action = "/servlet/controller.study.StudyStatusAdminServlet";
        document.form1.p_process.value = "CounselDelete";
        document.form1.submit();
    }


// 시간을 설정 하세요
var limit= <%=v_etime%>+":01";

var parselimit=limit.split(":");
parselimit=parselimit[0]*60+parselimit[1]*1;

function begintimer(){
    parselimit+=1;
    curmin=Math.floor(parselimit/60);
    cursec=parselimit%60;
    if (curmin!=0)
      curtime=curmin+" 분 "+cursec+" 초";
    else
      curtime="0 : "+cursec+" 초";

//    time.innerText=curtime;
	time.innerText = curmin + " 분";

	document.form1.p_etime.value = curmin;
    setTimeout("begintimer()",1000);
}
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onLoad='begintimer();'>

<form name="form1" method="post" action="/servlet/controller.study.StudyStatusAdminServlet">
    <input type="hidden" name="p_process"    value="<%=v_process%>">
    <input type="hidden" name="p_gubun"      value="<%=v_gubun%>">
    <input type="hidden" name="p_search"     value="userid">
    <input type="hidden" name="p_searchtext" value="<%=v_userid%>">
    <input type="hidden" name="p_userid"     value="<%=v_userid%>">
    <input type="hidden" name="p_no"         value="<%=v_no%>">
    <input type="hidden" name="p_pageno"     value="<%=v_pageno%>">
    
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/study/tit_counsel.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
        <br>

        <table border="0" cellspacing="0" cellpadding="0" width="97%">
          <tr>
            <td align="left"><b>[질문보기]</b></td>
          </tr>
        </table>
        <!----------------- 교육차수 과정지정 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="16%" height="25" class="table_title"><b>상담구분</b></td>
            <td class="table_02_2" width="81%">유선상담</td>
          </tr>
          <tr> 
            <td width="16%" height="25" class="table_title"><b>과정선택</b></td>
            <td class="table_02_2" width="81%">
                <%=CounselAdminBean.getSubjSelect(v_userid,"p_subj",v_subj+"/"+v_year+"/"+v_subjseq)%>
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>처리상태</b></td>
            <td class="table_02_2">
              <select name="p_status">
                <option value="1" <%if (v_status.equals("1")) {%>selected<%}%>>열람전</option>
                <option value="2" <%if (v_status.equals("2")) {%>selected<%}%>>답변준비중</option>
                <option value="3" <%if (v_status.equals("3")) {%>selected<%}%>>답변완료</option>
              </select>
            </td>
          </tr>
          <!--tr> 
            <td width="16%" height="25" class="table_title"><b>분류</b></td>
            <td class="table_02_2" width="81%">
                <%//=CodeConfigBean.getCodeGubunSelect (CounselAdminBean.COUNSEL_KIND, "", 1, "p_mcode", v_mcode, "", 0)%>
            </td>
          </tr-->
          <tr> 
            <td class="table_title" height="25"><b>상담일자</b></td>
            <td class="table_02_2">
              <input name="p_sdate_view" id="p_sdate_view"  class="datepicker_input1" type="text" size="10" value='<%=v_sdate_view%>'>
              <input name="p_sdate" type="hidden" value="<%=v_sdate%>">
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>상담소요시간</b></td>
            <td class="table_02_2"><div id="time"></div><input name="p_etime" type="hidden" class="input" size="10" value=""></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>제목</b></td>
            <td class="table_02_2"><input name="p_title" type="text" class="input" size="80" value="<%=v_title%>"></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>내용</b></td>
            <td class="table_02_2"><textarea name="p_ctext" rows="10" cols="80"><%=v_ctext%></textarea></td>
          </tr>
        </table>
        <br/>
        <table border="0" cellspacing="0" cellpadding="0" width="97%">
          <tr>
            <td align="left"><b>[답변보기]</b></td>
          </tr>
        </table>
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="16%" class="table_title" height="25"><b>상담자</b></td>
            <td class="table_02_2"><%= v_cname %></td>
          </tr>
          <tr> 
            <td width="16%" class="table_title" height="25"><b>처리내용</b></td>
            <td class="table_02_2">
                <textarea name="p_ftext" rows="10" cols="80"><%=v_ftext%></textarea>
            </td>
          </tr>
        </table>
        <br>
        <!----------------- 저장, 닫기 버튼 시작 ----------------->
        <table width="50%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="30%">&nbsp;</td>
            <td align="center"><a href="javascript:update_check()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
            <td align="center"><a href="javascript:delete_check()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
            <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
            <td width="30%">&nbsp;</td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr> 
<table>
</form>
</body>
</html>