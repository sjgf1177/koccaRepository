<%
//**********************************************************
//  1. 제      목: LEARNING STATUS LIST BY CLASS
//  2. 프로그램명: za_LearningStatusByClass_L.jsp
//  3. 개      요: 클래스별 학습현황
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.1.28
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String ems_url = conf.getProperty("ems.url.value");

    String  v_companynm = "";
    String  v_compnm    = "";
    String  v_grcodenm  = "";
    String  v_jikwi     = "";
    String  v_jikwinm   = "";
    String  v_jikup     = "";
    String  v_jikupnm   = "";
    String  v_userid    = "";
    String  v_cono      = "";
    String  v_name      = "";
    String  v_tuserid   = "";
    String  v_tname     = "";
    String  v_comptel   = "";
    String  v_email     = "";
    String  v_gradu_yn  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjnm    = "";
    String  v_work_plcnm = "";
    String  v_sdate      = "";
    String  v_sdate_view = "";
    int     v_tstep     =  0;
    int     v_avtstep   =  0;
    int     v_report    =  0;
    int     v_act       =  0;
    int     v_mtest     =  0;
    int     v_ftest     =  0;
    int     v_score     =  0;
    int     v_point     =  0;
    int     v_avg_tstep =  0;
    int     v_avg_avtstep= 0;
    int     v_avg_report=  0;
    int     v_avg_act   =  0;
    int     v_avg_mtest =  0;
    int     v_avg_ftest =  0;
    int     v_avg_score =  0;
    int     v_total     =  0;
    int     i           =  0;

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String  v_orderColumn  = box.getString("p_orderColumn");           //정렬할 컬럼명

    ArrayList list      = null;
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_grseq     = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm   = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_company   = box.getString("s_company");          //회사    String  ss_class     = box.getString("s_class");         //클래스
    String  ss_action    = box.getString("s_action");
    String  v_ongo      = "whenSelection('go')";


    //DEFINED in relation to select END

    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("LearningStatusListByClass");
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    //검색
    function whenSelection(ss_action) {
        if (ss_action=="go"){
            if (chkParam() == false) {
                return;
            }
            top.ftop.setPam();
        }
        document.form1.s_action.value = ss_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'LearningStatusListByClass';
        document.form1.submit();
    }

    // 정렬
    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_orderColumn.value = column;
        whenSelection("go");
    }


    // EXCEL 출력
    function whenExcel() {
        if(document.form1.s_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }
        window.self.name = "LearningStatusListByClass";
        open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form1.target = "openExcel";
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'LearningStatusExcelByClass';
        document.form1.submit();
    }

    // 폼메일 전송
    function whenFormMail() {
        if (chkParam() == false) {
            return;
        }
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      document.form1.target = "_self";
      document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
      document.form1.p_process.value = 'SendFormMail';
      document.form1.submit();
    }

    // 일반메일 전송
    function whenFreeMail() {
        if (chkParam() == false) {
            return;
        }
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }
      window.self.name = "MemberList";
      open_window("openFreeMail","","100","100","640","550");
      document.form1.target = "openFreeMail";
//      document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }

    //SMS 전송
    function whenSMS() {
      if (chkSelected() < 1) {
        alert('SMS를 발송할 학습자를 선택하세요');
        return;
      }

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;
      ff.p_touch.value = "09";

      window.self.name = "MemberList";
      open_window("openSMS","","100","100","620","354");
      document.form1.target = "openSMS";
      document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
      document.form1.p_process.value = 'SendSMS';
      document.form1.submit();
    }

    // 메일/SMS 내역 리스트
    function whenMailSmsSendLst() {
      window.self.name = "MemberList";
      open_window("openSendList","","100","100","640","550");
      document.form1.target = "openSendList";
      document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
      document.form1.p_process.value = 'EmailSmsSendList';
      document.form1.submit();
    }

    // 파라미터 체크
    function chkParam() {
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
        alert("교육그룹을 선택하세요.");
        return false;
      }
      if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '----') {
        alert("교육차수를 선택하세요.");
        return false;
      }
/*
      if (document.form1.s_upperclass.value == 'ALL') {
        alert("분류를 선택하세요.");
        return false;
      }
*/
      if (document.form1.s_subjcourse.value == 'ALL' || document.form1.s_subjcourse.value == '----') {
        alert("과정을 선택하세요.");
        return false;
      }
      if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '----') {
        alert("차수를 선택하세요.");
        return false;
      }
    }

    // 체크박스 체크갯수
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

    //전체선택 취소
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

    // 멤버정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeMemberList";
        open_window("openMember","","100","100","600","460");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }

    // 상담화면 (등록)
    function whenCounsel(userid, subj, year, subjseq) {
        document.form1.p_userid.value = userid;
        document.form1.p_subj.value = subj+"/"+year+"/"+subjseq;

        window.self.name = "ProposeMemberList";
        open_window("openCounsel","","50","50","800","650", false,false,true,true,true);
        document.form1.target = "openCounsel";
        document.form1.action='/servlet/controller.study.CounselAdminServlet';
        document.form1.p_process.value = 'CounselListPage';
        document.form1.submit();

        document.form1.target = window.self.name;
    }

    //
    function emailsmssend(){
        open_window("SendEmailSms","","100","100","700","400","yes","yes","yes","yes","yes");
        document.form1.target = "SendEmailSms";
        document.form1.action='/servlet/controller.study.StudyStatusAdminServlet';
        document.form1.p_process.value = 'LearningStatusSendEmailSms';
        document.form1.submit();
    }


//-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_rprocess" value="LearningStatusListByClass">
    <input type="hidden" name="s_action"  value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <!-- 상담관련-->
    <input type="hidden" name="p_userid"  value="">
    <input type="hidden" name="p_subj"    value="">
    <!-- 상담관련-->

    <!-- tz_humantouc insert parameter start-->
    <input type="hidden" name="p_touch" value=''>
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
          <td><img src="/images/admin/study/s_title07.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">

            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
<!-- SELECT BOX WITH CLASS  START ---->
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td width="73%" colspan="2">
                        <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                        <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                        <!--input type= "hidden" name="s_grseq" value="0001"-->
                        <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                      </td>
                    </tr>
                    <tr>
                        <td height="5"></td>
                    </tr>
                    <tr>
                      <td align="left" width="20%">
                        <%@ include file="/incmenu/search_subjnm.jsp"%>
                      </td>
                      <td width="63%">
                        <font color="red">★</font> <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                        <font color="red">★</font> <%= SelectSubjBean.getSubjseq(box, true, false)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                        <%= SelectSubjBean.getSubjClass(box, true, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    클래스  -->
                        <%= SelectCompanyBean.getCompany(box, false, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사  -->
                        <!-------------------- 조건검색 끝 ---------------------------->

                        <input type = "hidden" name="s_start">
                        <input type = "hidden" name="s_end">
                        <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                    </tr>
                  </table>
<!-- SELECT BOX WITH CLASS  END---->
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

      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="right" height="20">&nbsp;
            <!--<a href="javascript:whenFormMail()"><img src="/images/admin/button/btn_dogmail.gif" border="0"></a>-->
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            <a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            <a href="javascript:whenSMS()"><img src="/images/admin/button/btn_sms.gif" border="0"></a>
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            <a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif" border="0"></a>
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            <a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif" border="0"></a>
            </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->
      <!----------------- 클래스별 학습현황 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="18" class="table_top_line"></td>
        </tr>
        <tr>
          <td  class="table_title" width="5%">NO</td>
          <td  class="table_title"><a href="javascript:whenOrder('compnm1')" class="e">회사</a></td>
          <td  class="table_title"><a href="javascript:whenOrder('subj')" class="e">과정명</a></td>
          <td  class="table_title"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
          <td  class="table_title"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
          <td  class="table_title"><a href="javascript:whenOrder('compnm2')" class="e">부서</a></td>
          <td  class="table_title"><a href="javascript:whenOrder('jiknm')" class="e">직위</a></td>
          <td  class="table_title">근무지</td>
          <td  class="table_title">강사명</td>
          <td  class="table_title">진도율(점수)</td>
          <td  class="table_title">과제물</td>
          <td  class="table_title">중간평가</td>
          <td  class="table_title">최종평가</td>
          <td  class="table_title">총점</td>
          <td  class="table_title">수료여부</td>
          <td  class="table_title">최종상담일자</td>
<!--          <td  class="table_title">메일/SMS</td>-->
          <td  class="table_title">기능</td>
        </tr>

        <%
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
                v_total = list.size();
                for(i = 0; i < v_total; i++) {
                    StudyStatusData data  = (StudyStatusData)list.get(i);
                    v_grcodenm      = data.getGrcodenm();
                    v_companynm     = data.getCompanynm();
                    v_compnm        = data.getCompnm();
                    v_jikwi         = data.getJikwi();
                    v_jikwinm       = data.getJikwinm();
                    v_jikup         = data.getJikup();
                    v_jikupnm       = data.getJikupnm();
                    v_userid        = data.getUserid();
                    v_cono          = data.getCono();
                    v_name          = data.getName();
                    v_tuserid       = data.getTuserid();
                    v_tname         = data.getTname();
                    v_comptel       = data.getComptel();
                    v_email         = data.getEmail();
                    v_tstep         = data.getTstep();
                    v_avtstep       = data.getAvtstep();
                    v_report        = data.getReport();
                    v_act           = data.getAct();
                    v_mtest         = data.getMtest();
                    v_ftest         = data.getFtest();
                    v_score         = data.getScore();
                    v_point         = data.getPoint();
                    v_subj          = data.getSubj();
                    v_year          = data.getYear();
                    v_subjseq       = data.getSubjseq();
                    v_subjnm        = data.getSubjnm();
                    v_sdate         = StringManager.chkNull(data.getSdate());
                    v_work_plcnm    = data.getWork_plcnm();

                    v_sdate_view    = FormatDate.getFormatDate(v_sdate,"yyyy/MM/dd");;

                    //if(v_score >= v_point){ v_gradu_yn = "Y"; }
                    //else                  { v_gradu_yn = "N"; }
                    v_gradu_yn = data.getIsgraduated();
               %>
        <tr>
          <td class="table_01"><%= list.size() - i %></td>
          <td class="table_02_1"><%=v_companynm%></td>
          <td class="table_02_2"><%=v_subjnm%></td>
          <td class="table_02_1"><a href="javascript:whenCounsel('<%=v_userid%>','<%=v_subj%>','<%=v_year%>','<%=v_subjseq%>')" class="e"><%= v_userid %></a></td>
          <td class="table_02_1"><%= v_name %></td>
          <td class="table_02_1"><%= v_compnm %></td>
          <td class="table_02_1"><%= v_jikwinm %></td>
          <td class="table_02_1"><%=v_work_plcnm%></td>
          <td class="table_02_1"><%=v_tname%></td>
          <td class="table_02_1"><%= v_tstep %>(<%= v_avtstep %>)</td>
          <td class="table_02_1"><%= v_report %></td>
          <td class="table_02_1"><%= v_mtest %></td>
          <td class="table_02_1"><%= v_ftest %></td>
          <td class="table_02_1"><%= v_score %></td>
          <td class="table_02_1"><%= v_gradu_yn %></td>
          <td class="table_02_1"><%= v_sdate_view %></td>
<!--          <td class="table_02_1"><a href="javascript:whenMailSmsSendLst()" class="e">내역</a></td>-->
          <td class="table_02_1">
            <input type="checkbox" name="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
          </td>
        </tr>
         <%
                    v_avg_tstep     += v_tstep;
                    v_avg_avtstep   += v_avtstep;
                    v_avg_report    += v_report;
                    v_avg_act       += v_act;
                    v_avg_mtest     += v_mtest;
                    v_avg_ftest     += v_ftest;
                    v_avg_score     += v_score;
               }
            }
         if(i == 0 && ss_action.equals("go")){ %>
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="18">등록된 내용이 없습니다</td>
              </tr>
       <% }else if(i > 0){
                    v_avg_tstep     = v_avg_tstep/v_total;
                    v_avg_avtstep   = v_avg_avtstep/v_total;
                    v_avg_report    = v_avg_report/v_total;
                    v_avg_act       = v_avg_act/v_total;
                    v_avg_mtest     = v_avg_mtest/v_total;
                    v_avg_ftest     = v_avg_ftest/v_total;
                    v_avg_score     = v_avg_score/v_total;
         %>

        <tr>
          <td colspan="9" class="table_02_3">평균</td>
          <td class="table_02_3"><%=v_avg_tstep%>(<%=v_avg_avtstep%>)</td>
          <td class="table_02_3"><%=v_avg_report%></td>
<!--           <td class="table_02_3"><%=v_avg_act%></td> -->
          <td class="table_02_3"><%=v_avg_mtest%></td>
          <td class="table_02_3"><%=v_avg_ftest%></td>
          <td class="table_02_3"><%=v_avg_score%></td>
          <td class="table_02_3">&nbsp;</td>
          <td class="table_02_3">&nbsp;</td>
<!--          <td class="table_02_3">&nbsp;</td>-->
          <td class="table_02_3">&nbsp;</td>
        </tr>
        <% } %>
      </table>
      <!----------------- 클래스별 학습현황 끝 ----------------->
     <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>

</table>
</form>
</body>
</html>
