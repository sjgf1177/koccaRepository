<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunTargetMember_L.java
//  3. 개      요: 설문 대상자  조회
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    //페이지수
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    //페이지당 레코드수
    if (v_pagesize == 0)  v_pagesize = 10;

    String  v_subjnm    = box.getString("p_subjnm");

    String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_subj        = "TARGET";
    String  ss_grcode      = box.getString("s_grcode");
	String  ss_gyear       = box.getString("s_gyear");
	int  ss_sulpapernum    = box.getInt("s_sulpapernum");
    String  ss_subjseq     = box.getStringDefault("s_subjseq","0001");

    int     v_totalpage		 = 0;
    int     v_rowcount		 = 1;
    int     v_totalrowcount	 = 0;
    int     v_rowspan		 = 0;
    int     v_total			 = 0;
    int		v_dispnum		 = 0;

    String ems_url = conf.getProperty("ems.url.value"); // 메일
    
    ArrayList list = (ArrayList)request.getAttribute("SulmunMemberList");
	DataBox dbox0 = (DataBox)request.getAttribute("SulmunPaperData");

    int v_mailing =0;
    if (dbox0.getString("d_sulmailing").equals("Y") || dbox0.getString("d_sulmailing").equals("C")){
		v_mailing = 1;		//		메일수신, 메일수신/홈페이지 둘다
	} 

           String v_sulstart = "";
           String v_sulend = "";
           String v_ing = "";
		   int v_update = 0;

           v_sulstart = FormatDate.getFormatDate(dbox0.getString("d_sulstart"),"yyyy-MM-dd");
	       v_sulend = FormatDate.getFormatDate(dbox0.getString("d_sulend"),"yyyy-MM-dd");
		   if(dbox0.getInt("d_sulpapernum") > 0){

		   long v_fstart = Long.parseLong( (dbox0.getString("d_sulstart")).equals("") ? "0" : dbox0.getString("d_sulstart") );
           long v_fend = Long.parseLong( (dbox0.getString("d_sulend")).equals("") ? "0" : dbox0.getString("d_sulend") );
              
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
		         } else if (v_fstart <= v_now && v_now <= v_fend){
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
// 대상자 추가
function InsertMember(p_sulpapernum) {
    if (document.form2.s_grcode.value == 'ALL' || document.form2.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return;
    }	
	if (document.form2.s_gyear.value=="----"){
		alert("연도를 선택하세요");
		return;
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

// 대상자파일추가
function InsertFileToDB(p_sulpapernum) {
	if (document.form2.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
	if (document.form2.s_gyear.value=="----"){
		alert("연도를 선택하세요");
		return;
	}
	if (document.form2.s_sulpapernum.value == '0') {
			alert("설문지를 선택하세요.");
			return ;
	}
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = "SulmunMemberFileToDB";
    document.form2.p_action.value = "go";
    document.form2.p_sulpapernum.value = p_sulpapernum;
    document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
	document.form2.submit();

    farwindow.window.focus();
    document.form2.target = window.self.name;
}

// 대상자 삭제
function DeleteMember() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
	if (document.form2.s_gyear.value=="----"){
		alert("연도를 선택하세요");
		return;
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

    // 메일발송
    function MailSend() {
        
    	if (document.form2.s_grcode.value == '----') {
    			alert("교육그룹을 선택하세요.");
    			return ;
    	}
    	if (document.form2.s_gyear.value=="----"){
    		alert("연도를 선택하세요");
    		return;
    	}
    	if (document.form2.s_sulpapernum.value == '0') {
    			alert("설문지를 선택하세요.");
    			return ;
    	}
      if (chkSelected() < 1) {
        alert('메일을 보낼 대상자를 선택하세요');
        return;
      }
      
      if(confirm("설문메일을 발송하시겠습니까?")){
        document.form2.target = "_self"
        document.form2.p_process.value    = 'SulmunMail';
        document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
        document.form2.p_grcode.value      = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
        document.form2.p_gyear.value       = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
        
        document.form2.p_action.value = "go";
        document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
        document.form2.submit();
        
      }

    }


	//일반메일 발송
    //function MailSend() {
    //  if (chkSelected() < 1) {
    //    alert('메일을 발송할 학습자를 선택하세요');
    //    return;
    //  }
    //  window.self.name = "ProposeMemberList";
    //  open_window("openFreeMail","","10","10","800","650");
    //  document.form2.target = "openFreeMail";
    //  document.form2.action='<%=ems_url%>/ems/MassMail.do';
    //  document.form2.p_process.value = 'SendFreeMail';
    //  document.form2.submit();
    //}


    // 메일발송
	//    function MailSend() {
    //    	if (document.form2.s_grcode.value == '----') {
    //    			alert("교육그룹을 선택하세요.");
    //    			return ;
    //    	}
    //    	if (document.form2.s_gyear.value=="----"){
    //    		alert("연도를 선택하세요");
    //    		return;
    //    	}
    //    	if (document.form2.s_sulpapernum.value == '0') {
    //    			alert("설문지를 선택하세요.");
    //    			return ;
    //    	}
	//      if (chkSelected() < 1) {
    //        alert('메일을 보낼 대상자를 선택하세요');
    //        return;
    //      }
    //        window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    //        farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 667, height = 700, top=0, left=0");
    //        document.form2.target = "openWinInsert"
    //        document.form2.p_process.value = 'SulmunMailPage';
    //        document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
    //        document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
    //        document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
    //        document.form2.p_action.value = "go";
    //        document.form2.action = "/servlet/controller.research.SulmunTargetMemberServlet";
    //    document.form2.submit();    
    //    farwindow.window.focus();
    //    document.form2.target = window.self.name;
    //}
    

// 설문리스트 이동
function ReturnList() {
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.p_action.value = "go";
  document.form2.action = "/servlet/controller.research.SulmunTargetPaperServlet";
  document.form2.submit();
}

// 대상자선택 체크박스
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

// 조회 검색
function whenSelection(p_action) {
  document.form2.p_process.value = 'SulmunMemberListPage';
  document.form2.p_action.value  = p_action;
  if (p_action == "go"){
	if (document.form2.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}  
	if (document.form2.s_gyear.value=="----"){
		alert("연도를 선택하세요");
		return;
	}
	if (document.form2.s_sulpapernum.value=='0'){
		alert("설문지를 선택하세요");
		return;
	}
  document.form2.p_pageno.value = 1;
  document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
  document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
  document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
  // top.ftop.setPam();
  }
  document.form2.submit();
}

// 페이지 이동
function go(index) {
     document.form2.target = "_self";
     document.form2.p_pageno.value = index;
     document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
     document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
     document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
     document.form2.p_action.value = "go";
     document.form2.action = '/servlet/controller.research.SulmunTargetMemberServlet';
     document.form2.p_process.value = "SulmunMemberListPage";
     document.form2.submit();
}

// 페이지 이동
function goPage(pageNum) {
     document.form2.target = "_self";
     document.form2.p_pageno.value = pageNum;
     document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
     document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
     document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
     document.form2.p_action.value = "go";
     document.form2.action = '/servlet/controller.research.SulmunTargetMemberServlet';
     document.form2.p_process.value = "SulmunMemberListPage";
     document.form2.submit();
}

// 페이지 사이즈 변경
function pagesize(pageSize) {
     document.form2.target = "_self";
     document.form2.p_pageno.value = 1;
     document.form2.p_sulpapernum.value = document.form2.s_sulpapernum.options[document.form2.s_sulpapernum.selectedIndex].value;
     document.form2.p_grcode.value = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;
     document.form2.p_gyear.value = document.form2.s_gyear.options[document.form2.s_gyear.selectedIndex].value;
     document.form2.p_pagesize.value = pageSize;
     document.form2.p_action.value = "go";
     document.form2.action = '/servlet/controller.research.SulmunTargetMemberServlet';
     document.form2.p_process.value = "SulmunMemberListPage";
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

// 전체 체크박스 checked
function whenAllSelect() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = true;
      }
    } else {
      document.form2.p_checks.checked = true;
    }
  } 
}

// 전체 체크박스 checked cancel
function whenAllSelectCancel() {
  if(document.form2.all['p_checks'] == '[object]') {
    if (document.form2.p_checks.length > 0) {
      for (i=0; i<document.form2.p_checks.length; i++) {
        document.form2.p_checks[i].checked = false;
      }
    } else {
      document.form2.p_checks.checked = false;
    }
  } 
}


// 전체 체크박스 선택
function checkall(){
    if(document.form2.p_checkall.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
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
                      <td> <font color="red">★</font>  
	                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						    <font color="red">★</font>  
						    <%=SelectEduBean.getDamunGyear(box, true, false )%><!-- SelectEduBean.getGyear(box, true) 해당연도  -->
                            <font color="red">★</font>  설문지<%= SulmunTargetMemberBean.getSulpaperSelect2 (ss_grcode, ss_gyear, ss_subj, "s_sulpapernum", ss_sulpapernum, "")%>
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
   if(v_update > 0){
%>
        <!--<tr align="right">
		  <td align="right"></td>
          <td>기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend%>" size="10" readonly>			
		  </td>
        </tr>-->
    	<tr align="right">
          <td>
			<%if(v_update < 3 && v_mailing > 0){%>
			<a href="javascript:MailSend()"><img src="/images/admin/button/btn_researchsend.gif" border="0"></a>
			<%}%>          
		    <%if(v_update < 3 ){%>
			<a href="javascript:InsertFileToDB('<%=ss_sulpapernum%>')"><img src="/images/admin/button/btn_peoplefilep.gif" border="0"></a>
            <a href="javascript:InsertMember('<%=ss_sulpapernum%>')"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
            <a href="javascript:DeleteMember()"><img src="/images/admin/button/btn_peopledelete.gif" border="0"></a>
            <!--<a href="javascript:ReturnList()">설문지리스트</a>-->
			<%} else if (v_update == 3){%>
			<%=v_ing%>&nbsp;기간 : &nbsp;<input name="p_sulstart_view" type="text" class="input" value="<%=v_sulstart%>" size="10" readonly>-<input name="p_sulend_view" type="text" class="input" value="<%=v_sulend%>" size="10" readonly>		
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
        <input type="hidden" name="p_grcode"     value="">
        <input type="hidden" name="p_gyear"       value="">
		<input type="hidden" name="p_sulpapernum"  value="<%=box.getString("ss_sulpapernum")%>">
        <input type="hidden" name="p_mailnum"       value="">
        <input type="hidden" name="p_action"     value="">

        <input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">
	    <input type="hidden" name="emsName"     value="<%=box.getSession("name")%>">
	    <input type="hidden" name="emsEmail"    value="<%=box.getSession("email")%>">     
	    
	    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
        <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>">
	       
        <tr>
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title">NO</td>
          <td class="table_title">회원구분</td>                  
          <td class="table_title">아이디</td>          
          <td class="table_title">성명</td>
          <td class="table_title">가입일</td>
          <td class="table_title"><input type="checkbox" name="p_checkall" onClick="javascript:checkall()"></td>
        </tr>
<%
        for (int i=0; i<list.size(); i++) {
            DataBox dbox2  = (DataBox)list.get(i);      
            
            //페이지 나누기
			v_totalpage     = dbox2.getInt("d_totalpage");
            v_rowcount      = dbox2.getInt("d_rowcount");
            v_totalrowcount = dbox2.getInt("d_totalrowcount");
            v_dispnum       = dbox2.getInt("d_dispnum");
            
%>
        <tr>
          <td width="5%" class="table_01"><%=v_dispnum%></td>
          <td class="table_02_1"><%=dbox2.getString("d_registgubunnm")%></td>          
          <td class="table_02_1"><%=dbox2.getString("d_userid")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox2.getString("d_indate")%></td>          
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=dbox2.getString("d_userid")%>,TARGET,<%=ss_gyear%>,<%=ss_subjseq%>,<%=dbox2.getString("d_ismailcnt")%>">
          </td>
        </tr>
<%  }  %>
        </table>
      <br>
     <!----------------- page navi 시작 ----------------->
     <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
       <tr>
         <td align="right" valign="absmiddle">
           <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
         </td>
       </tr>
     </table>
     <!----------------- page navi 끝 ----------------->

    </td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
