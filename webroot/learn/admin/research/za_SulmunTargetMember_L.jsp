<%
/**
 * file name : za_SulmunTargetMember_L.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 설문 대상자  조회
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_subj      = "TARGET";
    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_sulpapernum       = box.getInt("s_sulpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");

    ArrayList list = (ArrayList)request.getAttribute("SulmunMemberList");
	DataBox dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    int v_mailing =0;
    if (dbox0.getString("d_sulmailing").equals("Y") || dbox0.getString("d_sulmailing").equals("C")){
		v_mailing = 1;
	} 

           String v_sulstart = "";
           String v_sulend = "";
           String v_ing = "";
		   int v_update = 0;

           v_sulstart = FormatDate.getFormatDate(dbox0.getString("d_sulstart"),"yyyy-MM-dd");
	       v_sulend = FormatDate.getFormatDate(dbox0.getString("d_sulend"),"yyyy-MM-dd");
		   if(dbox0.getInt("d_sulpapernum") > 0){
          
		   long v_fstart = Long.parseLong(dbox0.getString("d_sulstart"));
           long v_fend = Long.parseLong(dbox0.getString("d_sulend"));
              
                 Date d_now = new Date();
                 String d_year = String.valueOf(d_now.getYear()+1900);
				 String d_month = String.valueOf(d_now.getMonth()+1);
				 String d_day = String.valueOf(d_now.getDate());

                 if(d_month.length() == 1){
				        d_month = "0" + d_month; 
				 }
				 if (d_day.length() == 1){
				        d_day = "0" + d_day; 				 
				 }
		         long v_now = Long.parseLong(d_year+d_month+d_day); 

		         if (v_fstart > v_now){
		            v_ing = "<font color='red'>설문 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_fend){
		            v_ing = "설문 진행이 완료되었습니다.";
					v_update = 3;
		         } else if (v_fstart <= v_now && v_now < v_fend){
		            v_ing = "설문이 진행 중입니다"; 
					v_update = 2;
		         }
		   }
%>
<html>
<head>
<title>설문 대상자 리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function InsertMember(p_sulpapernum) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "SulmunMemberInsertPage";
    document.form2.p_action.value = "go";
    document.form2.p_sulpapernum.value = p_sulpapernum;
    document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
	document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function InsertFileToDB(p_sulpapernum) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "SulmunMemberFileToDB";
    document.form2.p_action.value = "go";
    document.form2.p_sulpapernum.value = p_sulpapernum;
    document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
	document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function DeleteMember() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
  if (chkSelected() < 1) {
    alert('삭제할 대상자를 선택하세요');
    return;
  }
  document.form2.p_process.value = 'SulmunMemberDelete';
  document.form2.p_action.value = "go";
  document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  document.form2.submit();
}
function MailSend() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
  if (chkSelected() < 1) {
    alert('메일을 보낼 대상자를 선택하세요');
    return;
  }
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'SulmunMailSendPage';
    document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
    document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.research.SulmunTargetMailServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function ReturnList() {
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.p_action.value = "go";
  document.form2.action = "/servlet/controller.research.SulmunTargetPaperServlet";
  document.form2.submit();
}
function chkSelected() {
  var selectedcnt = 0;
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        if (document.form2.p_checks[i].checked == true) {
          selectedcnt++;
        }
      }
    } else {
      if (document.form2.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }
  return selectedcnt;
}
function whenSelection(p_action) {
  document.form2.p_process.value = 'SulmunMemberListPage';
  document.form2.p_action.value  = p_action;
  if (p_action == "go"){
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  // top.ftop.setPam();
  }
  document.form2.submit();
}
function ReloadPage() {
  document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
  document.form2.p_process.value = 'SulmunMemberListPage';
  document.form2.p_action.value = "go";
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  document.form2.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMemberServlet">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
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
                      <td>
	                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						   <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                           설문지<%= SulmunTargetMemberBean.getSulpaperSelect (ss_grcode, ss_gyear, ss_subj, "s_sulpapernum", ss_sulpapernum, "")%>
		                   <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                    	
					  </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td height="3"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <!----------------- 대상자추가, 대상자삭제 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
<%
   if(v_update > 0 && v_mailing > 0){
%>
        <tr align="right">
		  <td align="left">
			<%if(v_update == 1 || v_update == 2){%><a href="javascript:MailSend()"><img src="/images/admin/button/btn_researchsend.gif" border="0"></a><%}%>
		  </td>
          <td>
		    <%if(v_update == 1){%>
            <a href="javascript:InsertFileToDB('<%=ss_sulpapernum%>')"><img src="/images/admin/button/btn_peoplefilep.gif" border="0"></a>
            <a href="javascript:InsertMember('<%=ss_sulpapernum%>')"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
            <a href="javascript:DeleteMember()"><img src="/images/admin/button/btn_peopledelete.gif" border="0"></a>
            <!--<a href="javascript:ReturnList()">설문지리스트</a>-->
			<%} else if (v_update == 2){%>
            <%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend%>" size="10" readonly>
			<%} else if (v_update == 3){%>
			<%=v_ing%>
			<%}%>
		  </td>
        </tr>
<%}else{%>
        <tr align="right">
		  <td align="left"></td>
          <td><%=v_ing%>
            <!--<a href="javascript:ReturnList()">설문지리스트</a>-->
		  </td>
        </tr>
<%}%>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 대상자추가, 대상자삭제 버튼 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <input type="hidden" name="p_process"    value="">
        <input type="hidden" name="s_subj"       value="TARGET">
        <input type="hidden" name="p_grcode"       value="">
        <input type="hidden" name="p_gyear"       value="">
		<input type="hidden" name="p_sulpapernum"       value="">
        <input type="hidden" name="p_mailnum"       value="">
        <input type="hidden" name="p_action"     value="">

        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title">NO</td>
          <td class="table_title">소속</td>
          <td class="table_title">직위</td>
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">구분</td>
        </tr>
<%
        for (int i=0; i<list.size(); i++) {
            DataBox dbox2  = (DataBox)list.get(i);      %>
        <tr>
          <td width="5%" class="table_01"><%=(i+1)%></td>
          <td class="table_02_1"><%=dbox2.getString("d_asgnnm")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_jikupnm")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_cono")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_name")%></td>
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox2.getString("d_userid")%>|<%=dbox2.getString("d_name")%>">
          </td>
        </tr>
<%  }  %>
        </table>
      <br>
    </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
