<%
//**********************************************************
//  1. 제      목: Propose Wizard Search LIST
//  2. 프로그램명: za_ProposeWizard_L.jsp
//  3. 개      요: 교육대상자 마법사 실행?page
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 11. 03
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<!--jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" /-->

<%
    //DEFINED class&variable START

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    //out.print("box="+box);
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String ems_url = conf.getProperty("ems.url.value");
    String  v_process   = box.getString("p_process");
    String  v_action    = box.getString("p_action");
    
    ArrayList list      = null;
    ArrayList list1      = null;
    //ProposeWizardBean   wizbean = new ProposeWizardBean();

    String v_comp              = "";
    String v_compnm            = "";
    String v_companynm         = "";
    String v_gpmnm             = "";
    String v_jikup             = "";
    String v_jikupnm           = "";
    String v_jikwi             = "";
    String v_jikwinm           = "";
    String v_jikmu             = "";
    String v_jikmunm           = "";
    String v_jikho             = "";
    String v_jikhonm           = "";
    String v_fix_gubun         = "";
    String v_sex               = "";
    String v_userid            = "";
    String v_cono              = "";
    String v_subj              = "";
    String v_subjnm            = "";
    String v_subjseq           = "";
    String v_isproposeapproval = "";
    String v_name              = "";
    String v_resno             = "";
    String v_resno1            = "";
    String v_resno2            = "";
    String v_email             = "";
    String v_ismailing         = "";
    
    String v_psubj              = "";
    String v_psubjnm            = "";
    String v_psubjseq           = "";
    String v_pisproposeapproval = "";
    String v_pchkfinal          = "";
    String v_subjseqgr = "";
    String v_appdate ="";
    
    int     i           =  0;

   //DEFINED class&variable END

    //DEFINED in relation to select START
    /* 회사정보 select Params */

    //String  v_proposetype = box.getStringDefault("p_proposetype","3");   //수강신청방식
    String  v_proposetype = "";   //수강신청방식
    String  v_ongo        = "whenSelection('go')";
    
    //out.println("v_action="+v_action);
    
    /* 교육 및 과정 차수 정보 selected Params*/
    String  ss_grcode     = box.getString("s_grcode");                      //교육그룹
    String  ss_gyear      = box.getString("s_gyear");                       //년도
    String  ss_grseq      = box.getString("s_grseq");                       //교육차수
    String  ss_subjcourse = box.getString("s_subjcourse");                  //과정&코스
    String  ss_mastercd   = box.getString("s_mastercd");                  //과정&코스
    String  ss_subjseq    = box.getString("s_subjseq");                     //과정 차수
    String  ss_company    = box.getString("s_company");                      //회사
    
    //DEFINED in relation to select END
    if (v_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectList");
        list1 = (ArrayList)request.getAttribute("selectMasterInfo");
    }
    
    
    //out.println(list);

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    //검색
    function whenSelection(p_action) {
        document.form1.p_action.value = p_action;
        if(p_action == 'go') {
        //alert(document.form1.s_grcode.value);
            if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
                  alert("교육그룹을 선택하세요.");
                  return ;
            }
            if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
                  alert("교육차수을 선택하세요.");
                  return ;
            }
            if(document.form1.s_mastercd.value == 'ALL'){
                  alert("마스터과정을 선택하세요.");
                  return ;
            }
            top.ftop.setPam();
        }
        document.form1.p_action.value = p_action;
        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.propose.ProposeWizardServlet';
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }

    // 대상자 삭제
    function whenDelete(p_action) {
        if(document.form1.s_mastercd.value == 'ALL'){
            alert("과정을 선택하세요.");
            return;
        }

        if (chkSelected() <1) {
            alert("대상자를 선택하세요.");
            return;
        }
        
        if(document.form1.p_userlist.value != ""){
          alert("신청된 대상에 대해서는 삭제할수 없습니다. \n\n완전삭제를 원하시면 [수강생관리]메뉴를 이용하십시오. ");
          document.form1.p_userlist.value = "";
          return;
        }
        document.form1.p_userlist.value = "";

        if (confirm("지정된대상자를 삭제하시겠습니까?")) {
          document.form1.p_action.value = p_action;
          document.form1.target = "_self";
          document.form1.action='/servlet/controller.propose.ProposeWizardServlet';
          document.form1.p_process.value = 'delete';
          //alert(document.form1.p_process.value);
          document.form1.submit();
        }
        else{
         return;
        }
    }

/*
    function chkParams(){
        ff = document.form1;
        if(blankCheck(ff.p_grcode.value)|| blankCheck(ff.p_gyear.value) || blankCheck(ff.p_grseq.value) || blankCheck(ff.p_subjcourse.value) || blankCheck(ff.p_subjseq.value)){
            alert('과정을 지정후 저장하세요.');
            return false;
        }
        return true;
    }

    //과정선택후 Setting 과정Params
    function setSubjSeqInfo(grcode,gyear,grseq, subjcourse, subjseq){
        ff = document.form1;
        ff.p_grcode.value=grcode;
        ff.p_gyear.value=gyear;
        ff.p_grseq.value=grseq;
        ff.p_subjcourse.value=subjcourse;
        ff.p_subjseq.value=subjseq;
        
    }
*/

    //대상자 추가 OPEN WINDOW
    function whenSelSubjInfo(){
        ff =document.form1;
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
              alert("교육그룹을 선택하세요.");
              return ;
        }
        if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
              alert("교육차수을 선택하세요.");
              return ;
        }
        if(document.form1.s_mastercd.value == 'ALL'){
              alert("마스터과정을 선택하세요.");
              return ;
        }
        
        grcode = ff.s_grcode.value;
        gyear = ff.s_gyear.value;
        grseq = ff.s_grseq.value;
        mastercd = ff.s_mastercd.value;
        company = ff.s_company.value;
        masternm = ff.s_mastercd.options[ff.s_mastercd.selectedIndex].text;
        grcodenm = ff.s_grcode.options[ff.s_grcode.selectedIndex].text;
        gyearnm = ff.s_gyear.options[ff.s_gyear.selectedIndex].text;
        //totalcnt = ff.s_total.value;
        //alert("subjnm="+mastercd);
        document.form1.target = "addTarget";
        
        
        var url = "/servlet/controller.propose.ProposeWizardServlet";
        url    += "?p_process=OpenTagetSelect";
        url    += "&s_grcode="+grcode;
        url    += "&s_gyear="+gyear;
        url    += "&s_grseq="+grseq;
        url    += "&s_mastercd="+mastercd;
        url    += "&s_company="+company;
        url    += "&s_masternm="+masternm;
        url    += "&s_grcodenm="+grcodenm;
        url    += "&s_gyearnm="+gyearnm;
        
        //alert(url);

        //var new_Open = window.open( url,"assign1", "width=850,height=600,scrollbars=yes,resizable=yes" );
        
        
        open_window("masterTarget",url,"100","100","850","600","yes","yes","",true,true);
    }

    // 정렬컬럼 선택
    function whenOrder(column) {
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.student.StudentStatusAdminServlet';
        document.form1.p_process.value = 'StudentMemberList';
        document.form1.p_orderColumn.value = column;
        document.form1.submit();
    }

    //
    function chkSelected() {
      var selectedcnt = 0;
      if(document.form1.all['p_checks'] == '[object]') {
        if (document.form1.p_checks.length > 0) {
          for (i=0; i<document.form1.p_checks.length; i++) {
            if (document.form1.p_checks[i].checked == true) {
              selectedcnt++;
              
              

              //삭제불가능 userid list
              if(document.form1.p_isdeleteable[i].value == "N"){ 
                document.form1.p_userlist.value += document.form1.p_userid[i].value+", ";
              }

            }
          }
        } else {
          if (document.form1.p_checks.checked == true) {
            selectedcnt++;

             if(document.form1.p_isdeleteable.value == "N"){ 
                document.form1.p_userlist.value += document.form1.p_userid.value+", ";
             }

          }
        }
      }
      return selectedcnt;
    }

    // 전체선택
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

    //페이지이동
    function go(index) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.propose.ProposeWizardServlet';
         document.form1.p_process.value = "StudentMemberList";
         document.form1.submit();
    }
    
    // 마스터정보
    function whenSelMasterInfo(proposetype){
        
        if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
              alert("교육그룹을 선택하세요.");
              return ;
        }
        
        if(document.form1.s_mastercd.value == 'ALL'){
              alert("마스터과정을 선택하세요.");
              return ;
        }
        
        ff =document.form1;
        
        window.self.name = "winSelectGrsubj";
        farwindow = window.open("", "openWinGrsubj", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 700, height = 640, top=0, left=0");
        ff.target = "openWinGrsubj";
        ff.action = "/servlet/controller.propose.MasterSubjServlet";
        ff.p_process.value="assignSubjCourse";
        ff.p_mastercd.value=ff.s_mastercd.value;
        ff.p_masternm.value=ff.s_mastercd.options[ff.s_mastercd.selectedIndex].text;
        ff.p_proposetype.value=proposetype;
        ff.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
    
    }
    
    //페이지 이동
    function goPage(pageNum) {
         document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.student.StudentStatusAdminServlet';
         document.form1.p_process.value = "StudentMemberList";
         document.form1.submit();
    }

    // 회원정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeMemberList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }
    //alert(document.form1.s_subjcourse);
    

    function whenChanged(userid) {
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.propose.ProposeWizardServlet';
        alert(document.form1.p_proposetype.value);
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }
    
    // 정렬
    function ordering(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_order.value = column;
        whenSelection("go");
    }


    //메일 발송
    function whenFreeMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      document.form1.p_userlist.value = "";
      
      ff =document.form1;
      
      
      ff.p_msubjnm.value=ff.s_mastercd.options[ff.s_mastercd.selectedIndex].text;
      ff.p_mseqgrnm.value='0000';
      ff.p_msubj.value=ff.s_mastercd.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value='0000';
      
/*
      window.self.name = "ProposeMemberList";     
      open_window("openFreeMail","","100","100","640","522");  
      document.form1.target = "openFreeMail";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.p_isMailing.value = '1';
      document.form1.submit();
*/
      window.self.name = "MemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
//      document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }   
-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<!-- 회사관련 select boxs -->
<form name = "form1" method="post" action="/servlet/controller.propose.ProposeWizardServlet">
<input type = "hidden" name="p_process"  value="<%=v_process%>">
<input type = "hidden" name="p_mastercd"  value="">
<input type = "hidden" name="p_masternm"  value="">
<input type="hidden" name="p_proposetype"  value="<%=v_proposetype%>">
<input type = "hidden" name="p_action"   value="<%=v_action%>">    <!--in relation to select-->
<input type="hidden" name="p_order"  value="name">
<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
<input type="hidden" name="p_isMailing" value='1'>
<input type="hidden" name="p_userlist" value=''>


<!-- tz_humantouc insert parameter start-->
<input type="hidden" name="p_touch" value='01'>
<input type="hidden" name="p_msubjnm" value=''>
<input type="hidden" name="p_mseqgrnm" value=''>
<input type="hidden" name="p_msubj" value=''>
<input type="hidden" name="p_myear" value=''>
<input type="hidden" name="p_msubjseq" value=''>
<!-- tz_humantouc insert parameter end -->

<input type="hidden" name="userLoginID" value="<%=box.getSession("userid")%>">
<input type="hidden" name="emsName" value="<%=box.getSession("name")%>">
<input type="hidden" name="emsEmail" value="<%=box.getSession("email")%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
<!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/propose/p_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
<!----------------- title 끝 ----------------->
        <br>
      
        <!----------------- form 시작 ----------------->
        <table class="form_table_out" border="0" cellspacing="0" cellpadding="1">
            <tr>
                <td align="center">
                    <table class="form_table_bg" cellspacing="0" cellpadding="0" >
                        <tr><td height="7"></td></tr>
                        <tr>
                            <td align="center">
                                <table class="form_table" cellspacing="0" cellpadding="0" width="99%" >
                                    <tr>
                                        <td><!-- 과정관련 select boxs -->
                                            <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                                            <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                                            <font color="red">★</font> <%=  SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                                            <font color="red">★</font> <%= SelectSubjBean.getMasterSubj(box, true, true, true)%><!-- getMasterSubj(RequestBox, isChange, isALL, isTarget)    마스터과정(s_mastersubj)  -->
                                            <%= SelectCompanyBean.getCompany(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사(s_company)  -->
                                            
                                            <!--input type = "hidden" name="s_grseq"  value="0001"-->
                                            <!--%= SelectSubjBean.getUpperClass(box, true, true, true)%--><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류(s_upperclass)-->
                                            <!--%= SelectSubjBean.getMiddleClass(box, true, true, true)%--><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류(s_middleclass)  -->
                                            <!--%= SelectSubjBean.getLowerClass(box, true, true, true)%--><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류()  -->
                                            
                                            
                                            <!--%= SelectSubjBean.getSubj(box, true, true)%--><!-- getSubj(RequestBox, isChange, isALL)    과정(s_subjcourse)  -->
                                            <!--%= SelectSubjBean.getSubjseq(box, false, true)%--><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수(s_subjseq)  -->
                                            &nbsp;&nbsp;&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                                            <%
                                            if(list1 != null) {
                                              DataBox dbox1  = (DataBox)list1.get(0);
                                              v_proposetype = dbox1.getString("d_proposetype");
                                            }
                                            %>
                                             <a href="javascript:whenSelMasterInfo('<%=v_proposetype%>')"><img src="/images/admin/button/btn_masterinfo.gif" border=0></a>&nbsp;&nbsp;
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
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <br>
        <!----------------- 아이디 또는 이름 검색, 모두선택.모두선택취소 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0" class="table1">
            <tr>
                <td width="58" height="20">
                    <table width="30%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td><input type="text" name="p_test01" maxlength="0" size="1"  style="font-size:0; text-decoration:none; border-style:none;" readonly></td>
                        </tr>
                    </table>
                </td>
                <td width="32" align="right" ><!--a href="javascript:whenAllSelect()"><img src="/images/admin/button/btn_allok.gif" border="0"></a--></td>
                <td width="8"></td>
                <td align="right" width="791" ><a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a></td>
                <td width="8"></td>
                <td width="32"><a href="javascript:whenSelSubjInfo()"><img src="/images/admin/button/btn_peopleplus.gif" border=0></a></td>
                <td width="9"></td>
                <td width="32" align="right"><a href="javascript:whenDelete('go')"><img src="/images/admin/button/btn_peopledelete.gif" border=0></a></td>
            </tr>
            <tr>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
              <td height="3"></td>
            </tr>
        </table>
        
        <!----------------- 교육대상자선정 시작 ----------------->
        <table cellspacing="1" cellpadding="0" class="table_out">
            <tr><td colspan="11" class="table_top_line"></td></tr>
            <tr>
                <td class="table_title" width="5%">No</td>
                <td class="table_title" width="15%"><b><a href="javascript:ordering('compnm1')" class="e">회사</a></b></td>
                <td class="table_title" width="8%"><b><a href="javascript:ordering('userid')" class="e">ID</a></b></td>
                <td class="table_title" width="12%"><b><a href="javascript:ordering('name')" class="e">성명</a></td>
                <td class="table_title" ><b><a href="javascript:ordering('compnm2')" class="e">부서</a></td>
                <td class="table_title" width="8%"><b><a href="javascript:ordering('jikwinm')" class="e">직위</a></b></td>            
                <td class="table_title" width="20%">최종신청 과정</td>
                <td class="table_title" width="7%">최종신청 차수</td>
                <td class="table_title" width="10%">신청일시</td>
                <td class="table_title" width="7%"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
            </tr>
<%  int v_total = 0;
    
    if(list != null) {
        v_total = list.size();
        for(i = 0; i < v_total; i++) {
            DataBox dbox  = (DataBox)list.get(i);
            v_comp       =dbox.getString("d_comp");
            v_compnm     =dbox.getString("d_compnm");
            v_companynm  =dbox.getString("d_companynm");
            v_gpmnm      =dbox.getString("d_gpmnm");
            v_jikup      =dbox.getString("d_jikup");
            v_jikupnm    =dbox.getString("d_jikupnm");
            v_jikwi      =dbox.getString("d_jikwi");
            v_jikwinm    =dbox.getString("d_jikwinm");
            v_jikmu      =dbox.getString("d_jikmu");
            v_jikmunm    =dbox.getString("d_jikmunm");
            v_jikho      =dbox.getString("d_jikho");
            v_jikhonm    =dbox.getString("d_jikhonm");
            v_fix_gubun  =dbox.getString("d_fix_gubun");
            v_sex        =dbox.getString("d_sex");
            v_userid     =dbox.getString("d_userid");
            v_cono       =dbox.getString("d_cono");
            v_subj       =dbox.getString("d_subj");
            v_subjnm     =dbox.getString("d_psubjnm");
            v_subjseq    =dbox.getString("d_psubjseq");
            v_subjseqgr  =dbox.getString("d_psubjseqgr");
            //v_isproposeapproval = dbox.getString("d_pisproposeapproval");
            v_name       =dbox.getString("d_name");
            v_resno      =dbox.getString("d_resno");
            v_appdate    =dbox.getString("d_appdate");
            v_email      =dbox.getString("d_email");
            v_ismailing  =dbox.getString("d_ismailing");

            if(v_subjnm.equals("")){ v_subjnm = "미신청";}
            //if(v_subjseq.equals("")){ v_subjseqgr = "미신청";}
            if(v_subjseq.equals("")){ v_appdate = "미신청";}
            else{
               v_appdate = FormatDate.getFormatDate(v_appdate, "yyyy/MM/dd HH:mm:ss");
            }

            if(v_resno.length() ==13){
                v_resno1    = StringManager.substring(v_resno, 0,6);
                v_resno2    = StringManager.substring(v_resno, 6);
            }

%>
          <tr>
            <td class="table_01"><%=i+1%></td>
            <td class="table_02_1"><%=v_companynm%></td>
            <td class="table_02_1"><%=v_userid%></td>
            <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%=v_name%></a></td>
            <td class="table_02_1"><%=v_compnm%></td>
            <td class="table_02_1"><%=v_jikwinm%></td>
            <td class="table_02_2"><%=v_subjnm%></td>
            <td class="table_02_1"><%if(!v_subjseqgr.equals("")){ out.print(Integer.parseInt(v_subjseqgr)); }else{ out.print("미신청"); }%></td>
            <td class="table_02_1"><%=v_appdate%></td>
            <td class="table_02_1">
              <input type="checkbox" name="p_checks" value="<%=v_userid%>">
              <input type="hidden" name="p_userid" value="<%=v_userid%>">
              <input type="hidden" name="p_isdeleteable" value="<%if(v_subjnm.equals("미신청")){out.print("Y");}else{out.print("N");}%>">
            </td>
          </tr>
<%
        }
        if(i==0){
        
%>
        <tr><td class="table_02_1" bgcolor="#F7F7F7" height="50" colspan="11">검색된 내용이 없습니다</td></tr>
<%      }
    }else { 
%>
         <tr><td align="center" bgcolor="#F7F7F7" height="50" colspan="11">등록된 내용이 없습니다</td></tr>

<%
    }
%>
      </table>
        <!----------------- 교육대상자선정 끝 ----------------->
        <br>

      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
<!--input type="hidden" name="s_total" value="<%=v_total%>"-->
</form>
</body>
</html>
