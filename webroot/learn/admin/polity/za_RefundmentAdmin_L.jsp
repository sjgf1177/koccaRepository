<%
/**
 * file name : za_RefundmentAdmin_L.jsp
 * date      :
 * programmer:
 * function  : 환불 신청 관리 리스트 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String  v_process  = "selectList";
    String ems_url = conf.getProperty("ems.url.value");

    String  ss_grcode           = box.getString("s_grcode");        //교육그룹
    String  ss_gyear            = box.getString("s_gyear");         //년도
    String  ss_grseq            = box.getString("s_grseq");         //교육차수
    String  ss_grseqnm          = box.getString("s_grseqnm");       //교육차수명
    String  ss_mastercd         = box.getString("s_mastercd");      //과정&코스
    String  ss_upperclass       = box.getString("s_upperclass");    //과정대분류
    String  ss_middleclass      = box.getString("s_middleclass");   //과정중분류
    String  ss_lowerclass       = box.getString("s_lowerclass");    //과정소분류
    String  ss_subjcourse       = box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq          = box.getString("s_subjseq");       //과정 차수
    String  ss_refundatestart   = box.getString("s_refundatestart");//환불시작일
    String  ss_refundateend     = box.getString("s_refundateend");  //환불종료일
    String  ss_refundstat       = box.getString("s_refundstat");    //환불상태
    
    String  v_action    = box.getString("p_action");

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    String v_dispnum    = "";
    
    int     v_pageno    = box.getInt("v_pageno");

    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
    int     v_total_rowcount = 0;

    if(v_pageno == 0)
    {
        v_pageno = 1;
    }

    String  v_ongo     = "whenSelection('go')";
    String  v_finalString = "";
%>

<html>
<head>
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
<script language="JavaScript">
$(document).ready(function(){
	$("#s_refundatestart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#s_refundateend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function MM_openBrWindow(theURL,winName,features) { //v2.0
      window.open(theURL,winName,features);
    }

    //검색
    function whenSelection(p_action) {
      document.form1.p_action.value = p_action;

      if (p_action == 'go') {
        if (chkParam() == false) {
          return;
        }
        top.ftop.setPam();
      }

      document.form1.action='/servlet/controller.polity.RefundmentAdminServlet';
      document.form1.p_process.value = 'selectList';    
      document.form1.submit();
    }

    // 검색조건 체크
    function chkParam() {
      if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '') {
        alert("교육그룹을 선택하세요.");
        return false;
      }
      if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '') {
        alert("교육차수를 선택하세요.");
        return false;
      }
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

    // 승인(취소,삭제)처리
    function whenApprovalProcess(){
        if (chkParam() == false) {
          return;
        }

        document.form1.p_chkvalue.value = "";

        var oTmp = document.all;
        var ff  = document.form1;
        var k = 0;
        var j = 0;

        for(var i = 0; i < oTmp.length; i++)
        {
            if(oTmp[i].type == "checkbox" && oTmp[i].name != "p_chkeckall")
            {
                if(oTmp[i].checked == true)
                {
                    if (eval("document.getElementById('p_refundmoney" + j + "').value") == "" || eval("document.getElementById('p_refunddate" + j + "').value") == "") {
                        alert("환불액/환불금을 입력하셔야 합니다.");
                        return;
                    }

                    k++;
                    ff.p_chkvalue.value += eval("document.getElementById('p_refundmoney" + j + "').value") + "," + eval("document.getElementById('p_refunddate" + j + "').value") + "," + eval("document.getElementById('p_refundstat" + j + "').value") + "," + oTmp[i].value + "|";
                }
                
                j++;
            }

        }
        if(k < 1)
        {
            alert("하나이상 선택하셔야 합니다.");
            return;
        }

        ff.p_chknum.value = k;
        
        ff.action='/servlet/controller.polity.RefundmentAdminServlet';
        ff.p_process.value = "RefundmentAdminInsert";
        ff.submit();
    }

    //전체 옵션선택 버튼
    function whenChangeAll(idx){
        if(!confirm("변경가능한 승인옵션이 모두 변경됩니다.\n\n계속하시겠습니까?")) return;

        for(var i=0;i<document.form1.length;i++){
            if(document.form1.elements[i].type=="select-one"){
                document.form1.elements[i].selectedIndex=idx;
            }
        }

        alert("[확인]버튼을 누르셔야만 저장됩니다");

    }

    // 회원정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }

    //-------------------------------
    // excel file 생성.
    //-------------------------------
    function whenExcel()
    {
        f = document.form1;
        
        if (chkParam() == false) {
          return;
        }
        
        document.form1.method = 'post';
        document.form1.target = 'mainFrame';

        document.form1.action='/servlet/controller.polity.RefundmentAdminServlet';
        document.form1.p_process.value = 'ExcelDown'; 
        document.form1.p_action.value = "go";

        document.form1.submit();                    
        
    }
    // 날자 삭제(달력)
    function whenclick(caldate) {
        caldate.value = "";
    }

    // 메일발송
    function whenFreeMail() {
      if (chkSelected() < 1) {
        alert('메일을 발송할 학습자를 선택하세요');
        return;
      }

      ff =document.form1;

      ff.p_msubjnm.value=ff.s_subjcourse.options[ff.s_subjcourse.selectedIndex].text;
      ff.p_mseqgrnm.value=ff.s_subjseq.options[ff.s_subjseq.selectedIndex].text;
      ff.p_msubj.value=ff.s_subjcourse.value;
      ff.p_myear.value=ff.s_gyear.value;
      ff.p_msubjseq.value=ff.s_subjseq.value;


      window.self.name = "StudentMemberList";
      open_window("openFreeMail","","10","10","800","650");
      document.form1.target = "openFreeMail";
//      document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
      document.form1.action='<%=ems_url%>/ems/MassMail.do';
      document.form1.p_process.value = 'SendFreeMail';
      document.form1.submit();
    }

    //체크박스 체크
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

    // 페이지이동
    function go(index) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = index;
         document.form1.action = '/servlet/controller.polity.RefundmentAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    // 페이지이동
    function goPage(pageNum) {
        document.form1.target = "_self";
         document.form1.p_pageno.value = pageNum;
         document.form1.action = '/servlet/controller.polity.RefundmentAdminServlet';
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    //숫자만 입력 가능
    function numcheck(oTarget)
    {
        if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
            if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
                event.returnValue = true;
            else
                event.returnValue = false;
    }
//-->
</script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1300" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/polity/tit_service_05.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_masternm"  value="">
    <input type="hidden" name="p_mastercd"  value="<%=ss_mastercd%>">
    <input type="hidden" name="p_action">
    <input type="hidden" name="p_order"  value="name">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">

    <input type="hidden" name="p_grcode" >
    <input type="hidden" name="p_grseq" >
    <input type="hidden" name="p_gyear">
    <input type="hidden" name="p_subj" >
    <input type="hidden" name="p_subjseq" >
    
    <input type="hidden" name="p_chknum" >
    <input type="hidden" name="p_chkvalue" >

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.polity.RefundmentAdminServlet">
    <input type="hidden" name="p_returnprocess"   value="selectList">


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
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="20%" colspan="2" align="left" valign="middle">
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td width="80%" colspan="2">
                        <font color="red">★</font> <%= SelectEduBean.getGyear(box, true)%><!-- getGyear(RequestBox, isChange)   교육년도  -->
                        <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                        <!-- input type= "hidden" name="s_grseq" value="0001"-->
                        <%= SelectSubjBean.getUpperClass(box, true, true, true)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                        <%= SelectSubjBean.getMiddleClass(box, true, true, true)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                      </td>
                      <td width="200" rowspan="5">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="5" colspan="4"></td>
                    </tr>
                    <tr>
                      <td align="left" width="20%">
                        <%@ include file="/incmenu/search_subjnm.jsp"%>
                      </td>
                      <td width="63%" colspan="3">
                        <%= SelectSubjBean.getSubj(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                        <%= SelectSubjBean.getSubjseq(box, false, true)%><!-- getSubjseq(RequestBox, isChange, isALL)    과정차수  -->
                        <!--%= SelectCompanyBean.getCompany(box, true, true)%><!-- getCompany(RequestBox, isChange, isALL)    회사  사용안함.-->
                        <!-------------------- 조건검색 끝 ---------------------------->

                        <!--input type = "hidden" name="s_start">
                        <input type = "hidden" name="s_end">
                        <input type="hidden" name="p_step"  value="2"-->
                    </tr>
                    <tr>
                        <td height="5" colspan="4"></td>
                    </tr>
                    <tr>
                        <td colspan="4">
                        <table cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td>
                                    교육시작일&nbsp;<input name="s_refundatestart" id="s_refundatestart" value="<%=ss_refundatestart%>" type="text" class="datepicker_input1" size="10">&nbsp;
                                    교육종료일&nbsp;<input name="s_refundateend" id="s_refundateend" value="<%=ss_refundateend%>" type="text" class="datepicker_input1" size="10">&nbsp;
                                    환불구분
                                    <select name="s_refundstat">
                                        <option value="ALL" <%if(ss_refundstat.equals("") || ss_refundstat.equals("ALL")) { %> selected <% } %>>전체</option>
                                        <option value="CW" <%if(ss_refundstat.equals("CW")) { %> selected <% } %>>환불신청</option>
                                        <option value="CY" <%if(ss_refundstat.equals("CY")) { %> selected <% } %>>환불완료</option>
                                    </select></td>
                                <td width="30%"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></div><!-- whenSelection('go') -->
                                </td>
                            </tr>
                        </table>
                        <td width="20%" align="right">&nbsp;</td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table><br>

<!---------------------////////////////////////////// 과정/차수정보 ////////////////////////////////-------------------------->
        <%
        String v_ispropose          ="";
        String v_proposetype        ="";
        String v_isedutarget        ="";
        String v_educnt             ="";
        String v_mastercd           ="";
        String v_masternm           ="";

        if (!ss_subjcourse.equals("ALL")&&!ss_subjseq.equals("ALL")&&!ss_subjcourse.equals("")&&!ss_subjseq.equals("")&&v_action.equals("go")) {
            DataBox dbox = (DataBox)request.getAttribute("selectList");

        %>
        <%}%>

        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td class="sub_title">환불 관리</td>
            <td align="right" height="20" width="58">&nbsp;</td>
            <td width="8"></td>
<!--
            <td align="right" height="20" width="70"><a href="javascript:whenChangeAll(2)"><img src="/images/admin/button/btn_allok.gif" border="0"></a></td>
            <td width="8"></td>
            <td align="right" height="20" width="90"><a href="javascript:whenChangeAll(1)"><img src="/images/admin/button/btn_allcancel.gif" border="0"></a></td>
            <td width="8"></td>
-->
            <td align="right" height="20" width="40"><a href="javascript:whenApprovalProcess()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
            <td width="8"></td>
            <td align="right" height="20" width="70"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
          </tr>
          <tr>
            <td height="3" colspan="7"></td>
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">       
          <!--FORM name="ff" method="post" action="/servlet/controller.polity.RefundmentAdminServlet"-->
            <input type="hidden" name="p_grcode"     value="<%=ss_grcode%>">
            <input type="hidden" name="p_gyear"      value="<%=ss_gyear%>">
            <input type="hidden" name="p_grseq"      value="<%=ss_grseq%>">
            <input type="hidden" name="p_grseqnm"    value="<%=ss_grseqnm%>">
            <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
            <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
            <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">
            <input type="hidden" name="p_subjcourse" value="<%=ss_subjcourse%>">
            <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
            <input type="hidden" name="p_mastercd"   value="<%=ss_mastercd%>">
            <input type="hidden" name="p_masternm"   value="">
            <input type="hidden" name="emsName" value="<%=box.getSession("name")%>">
            <input type="hidden" name="emsEmail" value="<%=box.getSession("email")%>">
          <tr>
            <td colspan="18" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="5%"><a href="javascript:ordering('userid')" class="e">신청인<br>(ID)</a></b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('subjnm')" class="e">신청<br>과목</a></b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('subjseq')" class="e">차수</a></b></td>
            <td class="table_title" width="7%"><b><a href="javascript:ordering('name')" class="e">교육기간</a></b></td>
            <td class="table_title"><b>신청일</b></td>
            <td class="table_title"><b>기본<br>수강료</b></td>
            <td class="table_title"><b>할인<br>구분</a></td>
            <td class="table_title"><b>포인트</a></b></td>
            <td class="table_title"><b>납부한<br>금액</a></td>
            <td class="table_title"><b>입금자</a></td>
            <td class="table_title"><b>입금일</a></td>
            <td class="table_title"><b>거래<br>구분</a></td>
            <td class="table_title"><b>환불<br>은행</a></td>
            <td class="table_title"><b>환불<br>계좌</a></td>
            <td class="table_title"><b>환불액</a></td>
            <td class="table_title"><b>환불일</a></td>
            <td class="table_title"><b>환불<br>구분</a></td>
            <td class="table_title"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>

<%  
        if (v_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("selectList");

        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

        String v_subj       = "";
        String v_subjnm     = "";
        String v_membergubun= "";
        String v_userid     = "";
        String v_uname      = "";
        String v_subjseq    = "";       
        String v_chkfinal   = "";
        String v_scyear     = "";
        String v_edustart   = "";
        String v_eduend     = "";
        String v_appdate    = "";
        String v_subjprice  = "";
        String v_usepoint   = "";
        String v_paymoney   = "";
        String v_accountname= "";
        String v_paydate    = "";
        String v_paytype    = "";
        String v_refundbank = "";
        String v_refundaccount  = "";
        String v_discountrate   = "";
        String v_refundmoney    = "";
        String v_refunddate     = "";
        String v_refundstat = "";
        String v_realpaymoney = "";

        for (i=0; i<list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            v_finalString   = "";

            v_subj          = dbox.getString("d_subj");
            v_subjnm        = dbox.getString("d_subjnm");
            v_userid        = dbox.getString("d_userid");
            v_uname         = dbox.getString("d_name");
            v_subjseq       = dbox.getString("d_subjseq");
            v_scyear        = dbox.getString("d_year");
            v_edustart      = dbox.getString("d_edustart");
            v_eduend        = dbox.getString("d_eduend");
            v_appdate       = dbox.getString("d_appdate");
            v_subjprice     = dbox.getString("d_subjprice");
            v_discountrate  = dbox.getString("d_discountrate");
            v_usepoint      = dbox.getString("d_usepoint");
            v_paymoney      = dbox.getString("d_paymoney");
            v_realpaymoney  = dbox.getString("d_realpaymoney");
            v_accountname   = dbox.getString("d_accountname");
            v_paydate       = dbox.getString("d_paydate");
            v_paytype       = dbox.getString("d_paytype");
            v_refundbank    = dbox.getString("d_refundbank");
            v_refundaccount = dbox.getString("d_refundaccount");
            v_refundmoney   = dbox.getString("d_refundmoney");
            v_refunddate    = dbox.getString("d_refunddate");
            v_refundstat    = dbox.getString("d_refundstat");

            v_dispnum       = dbox.getString("d_dispnum");
            v_totalpage     = dbox.getInt("d_totalpage");
            v_rowcount      = dbox.getInt("d_rowcount");
            v_total_rowcount= dbox.getInt("d_total_rowcount");



            //회원구분
            if(v_paytype.equals("C")) {v_paytype = "무통장";}
            else  {v_paytype = "카드";}

%>
            <tr class="table_02_1">
                <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%=v_uname %><br>(<%=v_userid%>)</a></td>
                <td class="table_02_1"><%=v_subjnm %></td>
                <td class="table_02_1"><%=v_subjseq %></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart,"yyyy-MM-dd")%> ~<br><%=FormatDate.getFormatDate(v_eduend,"yyyy-MM-dd")%></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>  
                <td class="table_02_1"><%=v_subjprice %></td>
                <td class="table_02_1"><%=v_discountrate %></td>
                <td class="table_02_1"><%=v_usepoint %></td>

                <td class="table_02_1"><%=v_realpaymoney %></td>
                <td class="table_02_1"><%=v_accountname %></td>
                <td class="table_02_1"><%=v_paydate %></td>
                <td class="table_02_1"><%=v_paytype %></td>
                <td class="table_02_1"><%=v_refundbank %></td>
                <td class="table_02_1"><%=v_refundaccount %></td>
                <td class="table_02_1"><input type="text" name="p_refundmoney" id="p_refundmoney<%=i%>" size="10" value="<%=v_refundmoney %>"onkeydown="javascript:return numcheck(this)"></td>
                <td class="table_02_1"><input type="text" name="p_refunddate" id="p_refunddate<%=i%>" size="10" value="<%=v_refunddate %>"onkeydown="javascript:return numcheck(this)"></td>
                <td class="table_02_1">
                    <select name="p_refundstat" id="p_refundstat<%=i%>">
                        <option value="ALL" <%if(v_refundstat.equals("") || v_refundstat.equals("ALL")) { %> selected <% } %>>전체</option>
                        <option value="CW" <%if(v_refundstat.equals("CW")) { %> selected <% } %>>환불신청</option>
                        <option value="CY" <%if(v_refundstat.equals("CY")) { %> selected <% } %>>환불완료</option>
                    </select>
                </td>
                <td class="table_02_1">
                  <input type="checkbox" name="p_checks" id="p_checks" value="<%=v_userid%>,<%=v_subj%>,<%=v_scyear%>,<%=v_subjseq%>">
                </td>
                <input type="hidden" name="p_subj" value="<%=v_subj%>">
                <input type="hidden" name="p_year" value="<%=v_scyear%>">
                <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
                <input type="hidden" name="p_userid" value="<%=v_userid%>">
            </tr>
<%          //}
        }
        if (i == 0)
            { %>
            <tr>
                <td align="center" bgcolor="#FFFFFF" height="100" colspan="18">등록된 내용이 없습니다.</td>
              </tr>
            <%}
    }

%>
           
            </FORM>
        </table>
        <!----------------- 과정기준조회 끝 ----------------->

        <%  if(i > 0){ %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageList(v_totalpage, v_pageno, v_rowcount) %></td>
          </tr>
        </table>
        <br>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right">◈<b> TOTAL : <%=v_total_rowcount %>명</b></td>
          </tr>
        </table>
        <% } %>

        <br>

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>