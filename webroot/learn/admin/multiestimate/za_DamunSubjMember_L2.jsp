<%
/**
 * file name : za_DamunSubjMember_L.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 다면평가 대상자  조회
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    String  ss_grcode       = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_damunpapernum       = box.getInt("s_damunpapernum");
    String  ss_subjseq    = box.getString("s_subjseq");

    ArrayList list = (ArrayList)request.getAttribute("DamunMemberList");
	DataBox dbox0 = (DataBox)request.getAttribute("DamunPaperData");

           String v_ing = "";
		   int v_update = 0;
		   int v_gubun = 0;

		   String v_sulstart1 = "";
           String v_sulend1 = "";
           v_sulstart1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunstart"),"yyyy-MM-dd");
	       v_sulend1 = FormatDate.getFormatDate(dbox0.getString("d_fdamunend"),"yyyy-MM-dd");

		   String v_sulstart2 = "";
           String v_sulend2 = "";
           v_sulstart2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunstart"),"yyyy-MM-dd");
	       v_sulend2 = FormatDate.getFormatDate(dbox0.getString("d_sdamunend"),"yyyy-MM-dd");

		   if(dbox0.getInt("d_damunpapernum") > 0){
          
		   long v_fstart = Long.parseLong(dbox0.getString("d_fdamunstart"));
           long v_fend = Long.parseLong(dbox0.getString("d_fdamunend"));
              
            if (dbox0.getString("d_d_gubun").equals("1")){
                  v_gubun = 1; 

                 java.util.Date d_now = new java.util.Date();
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
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_fend){
		            v_ing = "다면평가 진행이 완료되었습니다.";
					v_update = 3;
		         } else if (v_fstart <= v_now && v_now < v_fend){
		            v_ing = "다면평가가 진행 중입니다"; 
					v_update = 2;
		         }
	         } else if (dbox0.getString("d_d_gubun").equals("2")){
			     v_gubun = 2;
			     long v_sstart = Long.parseLong(dbox0.getString("d_sdamunstart"));
                 long v_send = Long.parseLong(dbox0.getString("d_sdamunend"));		 

                 java.util.Date d_now = new java.util.Date();
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
		            v_ing = "<font color='red'>다면평가 진행 전입니다.</font>"; 
					v_update = 1;
		         } else if (v_now > v_send){
		            v_ing = "사후 다면평가 진행이 완료되었습니다.";
					v_update = 5;
		         } else if (v_fstart <= v_now && v_now < v_fend){
		            v_ing = "사전 다면평가가 진행 중입니다"; 
					v_update = 2;
		         } else if (v_fend <= v_now || v_now < v_sstart){
		            v_ing = "사후 다면평가 진행 전입니다"; 
					v_update = 3;
		         } else if (v_sstart <= v_now || v_now < v_send){
		            v_ing = "사후 다면평가가 진행 중입니다"; 
					v_update = 4;
		         }
			 }			 
		   }

    String  v_ggubun        = box.getStringDefault("p_gubun",  DamunSubjBean.DEFAULT_SUBJ);

    String v_title_img = ""; 
    if (v_ggubun.equals("COMMON"))     v_title_img = "3";
    else if (v_ggubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_ggubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
%>
<html>
<head>
<title>다면평가 대상자 리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function changeTabpage(p_gubun) {

    if (p_gubun == "COMMON") {
      document.form2.action = "/servlet/controller.multiestimate.DamunCommonMailServlet";
	
	} 
	document.form2.p_action.value = 'change';
	document.form2.p_gubun.value = p_gubun;
    document.form2.s_grcode.value = "";
	document.form2.p_process.value = 'DamunMailListPage';
	document.form2.submit();
}
function InsertSubjMember() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow =  window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "SubjMemberTargetListPage";
    document.form2.p_action.value = "go";
    document.form2.p_flag.value = 1;
    document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function InsertMember(p_damunpapernum) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "DamunMemberInsertPage";
    document.form2.p_action.value = "go";
    document.form2.p_damunpapernum.value = p_damunpapernum;
    document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
	document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}

function InsertFileToDB(p_damunpapernum) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "DamunMemberFileToDB";
    document.form2.p_action.value = "go";
    document.form2.p_damunpapernum.value = p_damunpapernum;
    document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
	document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function DeleteMember() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}
  if (chkSelected() < 1) {
    alert('삭제할 대상자를 선택하세요');
    return;
  }
  document.form2.p_process.value = 'DamunMemberDelete';
  document.form2.p_action.value = "go";
  document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
  document.form2.submit();
}
function ObserverList ( p_asgnnm, p_jikupnm, p_cono, p_name, p_subjuserid) {
  document.form2.p_process.value = 'DamunOBMemberListPage';
  document.form2.p_action.value = "go";
  document.form2.p_asgnnm.value = p_asgnnm;
  document.form2.p_jikupnm.value = p_jikupnm;
  document.form2.p_cono.value = p_cono;
  document.form2.p_name.value = p_name;
  document.form2.p_subjuserid.value = p_subjuserid;
  document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
  document.form2.submit();
}
function MailSend(v_mailgubun) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}
  if (chkSelected() < 1) {
    alert('메일을 보낼 대상자를 선택하세요');
    return;
  }
      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'DamunMailSendPage';
    document.form2.p_damunpapernum.value = document.form2.s_damunpapernum.options[document.form2.s_damunpapernum.selectedIndex].value;
    document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    document.form2.p_subj.value = document.form2.s_subjcourse.options[document.form2.s_subjcourse.selectedIndex].value;
    document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    document.form2.p_action.value = "go";
    document.form2.p_mailgubun.value = v_mailgubun;
	document.form2.action = "/servlet/controller.multiestimate.DamunSubjMailServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function autoObserver() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
	if (document.form2.s_subjseq.value == '----') {
			alert("차수를 선택하세요.");
			return ;
	}
	if (document.form2.s_damunpapernum.value == '0') {
			alert("다면평가지를 선택하세요.");
			return ;
	}

  if (chkSelected() < 1) {
    alert('메일을 보낼 대상자를 선택하세요');
    return;
  }

      window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'AutoObserverInsertPage';
    document.form2.p_damunpapernum.value = document.form2.s_damunpapernum.options[document.form2.s_damunpapernum.selectedIndex].value;
    document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    document.form2.p_subj.value = document.form2.s_subjcourse.options[document.form2.s_subjcourse.selectedIndex].value;
    document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    document.form2.p_action.value = "go";
    document.form2.p_mailgubun.value = v_mailgubun;
	document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
    document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}
function ReturnList() {
  document.form2.p_process.value = 'DamunPaperListPage';
  document.form2.p_action.value = "go";
  document.form2.action = "/servlet/controller.multiestimate.DamunSubjPaperServlet";
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
  document.form2.p_process.value = 'DamunMemberListPage';
  document.form2.p_action.value  = p_action;
  if (p_action == "go"){
  document.form2.p_damunpapernum.value = document.form2.s_damunpapernum.options[document.form2.s_damunpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_subj.value = document.form2.s_subjcourse.options[document.form2.s_subjcourse.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  // top.ftop.setPam();
  }
  document.form2.submit();
}
function ReloadPage() {
  document.form2.action = "/servlet/controller.multiestimate.DamunSubjMemberServlet";
  document.form2.p_process.value = 'DamunMemberListPage';
  document.form2.p_action.value = "go";
  document.form2.p_damunpapernum.value = document.form2.s_damunpapernum.options[document.form2.s_damunpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_subj.value = document.form2.s_subjcourse.options[document.form2.s_subjcourse.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  document.form2.submit();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjMemberServlet">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
	  <br>
     <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle">과정평가</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('COMMON')" class="c">일반평가</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
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
                                   <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                				   <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				                   <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				                   <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
				                   <%= SelectSubjBean.getSubjseq(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                   다면평가지<%= DamunSubjMemberBean.getDamunpaperSelect (ss_grcode, ss_gyear, ss_subjcourse, "s_damunpapernum", ss_damunpapernum, "")%>
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
<%if(v_update > 0){
          if(v_gubun == 1){
%>
        <tr align="right">
		  <td align="left">
            <a href="javascript:('')"><img src="/images/admin/button/btn_nameprint.gif" border="0"></a>		    
			<%if(v_update ==1 || v_update == 2){%><a href="javascript:MailSend('1')"><img src="/images/admin/button/btn_damunsend.gif" border="0"></a><%}%>
            <%if(v_update == 1){%>
			<a href="javascript:autoObserver()"><img src="/images/admin/button/btn_gunauto.gif" border="0"></a>		  
		  </td>
          <td>
            <a href="javascript:InsertSubjMember('')"><img src="/images/admin/button/btn_cpeopleplus.gif" border="0"></a>
			<a href="javascript:InsertFileToDB('')"><img src="/images/admin/button/btn_peoplefilep.gif" border="0"></a>
            <a href="javascript:InsertMember('')"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
            <a href="javascript:DeleteMember()"><img src="/images/admin/button/btn_peopledelete.gif" border="0"></a>
			<%}else if(v_update == 2){%>
		  </td>
          <td>
            <%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart1%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend1%>" size="10" readonly>
			<%}else if(v_update == 3){%>
		  </td>
          <td>
		   <%=v_ing%>
			<%}%>
		  </td>
        </tr>
<%
		  } else if (v_gubun == 2){
%>
        <tr align="right">
		  <td align="left">
            <a href="javascript:('')"><img src="/images/admin/button/btn_nameprint.gif" border="0"></a>		    
<%if(v_update ==1 || v_update == 2){%>
            <a href="javascript:MailSend('2')"><img src="/images/admin/button/btn_beforedanumsend.gif" border="0"></a>
<%}else if(v_update ==3 || v_update == 4){%>
            <a href="javascript:MailSend('3')"><img src="/images/admin/button/btn_afterdanumsend.gif" border="0"></a>
<%}%>
            <%if(v_update == 1){%>
            <a href="javascript:autoObserver()"><img src="/images/admin/button/btn_gunauto.gif" border="0"></a>		  
		  </td>
          <td>
            <a href="javascript:InsertSubjMember('')"><img src="/images/admin/button/btn_cpeopleplus.gif" border="0"></a>
			<a href="javascript:InsertFileToDB('')"><img src="/images/admin/button/btn_peoplefilep.gif" border="0"></a>
            <a href="javascript:InsertMember('')"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
            <a href="javascript:DeleteMember()"><img src="/images/admin/button/btn_peopledelete.gif" border="0"></a>
			<%}else if(v_update == 2){%>
		  </td>
          <td>
            <%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart1%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend1%>" size="10" readonly>
			<%}else if(v_update == 3){%>
		  </td>
          <td>
		   <%=v_ing%>
			<%}else if(v_update == 4){%>
		  </td>
          <td>
            <%=v_ing%>&nbsp;
            기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart2%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend2%>" size="10" readonly>
			<%}else if(v_update == 5){%>
		  </td>
          <td>
		   <%=v_ing%>
			<%}%>
		  </td>
        </tr>
<%
		  } 	  
%>
<%}else{%>
        <tr align="right">
		  <td align="left">
            <a href="javascript:('')"><img src="/images/admin/button/btn_nameprint.gif" border="0"></a>		    
		  </td>
          <td><%=v_ing%></td>
        </tr>
<%}%>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 대상자추가, 대상자삭제 버튼 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_grcode"       value="<%=ss_grcode%>">
        <input type="hidden" name="p_subj"       value="<%=ss_subjcourse%>">
        <input type="hidden" name="p_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
        <input type="hidden" name="p_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="p_damunpapernum"       value="<%=ss_damunpapernum%>">
        <input type="hidden" name="p_asgnnm"     value="">
        <input type="hidden" name="p_jikupnm"     value="">
        <input type="hidden" name="p_cono"     value="">
        <input type="hidden" name="p_name"     value="">
		<input type="hidden" name="p_subjuserid"     value="">
		<input type="hidden" name="p_mailgubun"     value="">
		<input type="hidden" name="p_gubun"     value="">
		<input type="hidden" name="p_flag"     value="">



        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title">NO</td>
          <td class="table_title">소속</td>
          <td class="table_title">직위</td>
          <td class="table_title">사번</td>
          <td class="table_title">성명</td>
          <td class="table_title">관찰자</td>
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
          <td class="table_02_1"><a href="javascript:ObserverList('<%=dbox2.getString("d_asgnnm")%>', '<%=dbox2.getString("d_jikupnm")%>', '<%=dbox2.getString("d_cono")%>', '<%=dbox2.getString("d_name")%>', '<%=dbox2.getString("d_userid")%>')">리스트</a></td>
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
