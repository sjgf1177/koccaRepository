<%
//**********************************************************
//  1. 제       목: 차수 조회화면
//  2. 프로그램명: za_off_grseq_L.jsp
//  3. 개       요: 차수 조회화면
//  4. 환       경: JDK 1.5
//  5. 버       젼: 1.0
//  6. 작       성: swchoi 2009.11.18
//  7. 수       정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%!
    private final String NULL = "";
    private String chkAcceptResult(ArrayList<DataBox> acceptResultList, String p_acceptResult) {
//      if(p_acceptResult.length() == 2 && (p_acceptResult.charAt(0) == 'N' || p_acceptResult.charAt(1) == 'Y' || p_acceptResult.charAt(1) == 'N')) {
//      if(p_acceptResult.length() == 2 && (p_acceptResult.charAt(0) == 'N' || p_acceptResult.charAt(1) == 'N')) {
//          return SelectBoxBean.getSelectedString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
//      }
//      else
            return SelectBoxBean.getSelectBoxString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
    }
%>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    //DEFINED in relation to select START
    String ss_year = box.getString("s_year");           // 연도
    String ss_subj = box.getString("s_subjcode");       // 과정
    String ss_subjseq = box.getString("s_subjseq");     // 차수
    String ss_subjyear = box.getString("s_subjyear");   // 과정년도

    if( ss_year.equals("") ) {
        ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
    }
    String ss_upperclass = box.getStringDefault("s_upperclass", "S01");        //과정대분류
    String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");  //과정중분류
    String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");        //과정소분류
    String ss_action   = box.getString("s_action");
    String s_subjsearchkey = box.getString("s_subjsearchkey");
    //DEFINED in relation to select END
    ArrayList list      = null;
    ArrayList<DataBox> acceptResultList = null;
    if(ss_action.equals("go")){ //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("resultList");
        acceptResultList = (ArrayList<DataBox>) request.getAttribute("acceptResultList");
    }
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    String isAll = !v_gadmin.equals("A") ? "false" : "true";

    String  v_orderType = box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<title> 차수관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//삭제
var ff;
function deleteSubjSeq(subj, year, subjseq, seq) {
    document.form1.p_process.value = 'delete';
    $("#u_subjseq").val(subjseq);
    $("#u_year").val(year);
    $("#u_seq").val(seq);
    document.form1.action="/servlet/controller.off.OffApprovalAdminServlet";
    document.form1.p_subj.value  = subj;
    if(confirm("정말 삭제하시겠습니까?"))document.form1.submit();
}

// 검색
function whenSelection(ss_action) {
    if (ss_action=="go")    {
        top.ftop.setPam();
    }
    if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
        return;
    }
    if( document.form1.s_subjseq.value == 'ALL' ) {
        alert("차수를 선택하세요.");
        document.form1.s_subjseq.focus();
        return;
    }
        document.form1.s_action.value = ss_action;
        document.form1.p_process.value = 'listPage';
        document.form1.submit();

}

//정렬
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.s_action.value = "go";
    document.form1.p_process.value = 'listPage';
    document.form1.p_orderColumn.value = column;

    document.form1.submit();
}
//전체 옵션선택 버튼
function whenChangeAll(idx){
    if(!confirm("변경가능한 승인상태가 모두 변경됩니다.\n\n계속하시겠습니까?")) return;

    for(var i=0;i<document.ff.length;i++){
        if(document.ff.elements[i].type=="select-one"){
            document.ff.elements[i].selectedIndex=idx;
        }
    }
    alert("[확인]버튼을 누르셔야만 저장됩니다");
}
function whenApprovalProcess() {
    $("[name='p_middleclass']").val($("[name='s_middleclass']").val());
    $("[name='p_lowerclass']").val($("[name='s_lowerclass']").val());
    $("[name='p_upperclass']").val($("[name='s_upperclass']").val());
    document.ff.p_process.value="insert";
    document.ff.submit();
}
function whenExcel() {
    document.form1.target = 'mainFrame';
    $("#isExcel").val("true");
    document.form1.s_action.value = "go";
    document.form1.p_process.value = 'listPage';
    document.form1.submit();
    $("#isExcel").val("false");
    document.form1.target = '_self';
}

function whenAllExcel() {
    document.form1.target = 'mainFrame';
    $("#isAllExcel").val("true");
    document.form1.s_action.value = "go";
    document.form1.p_process.value = 'listPage';
    document.form1.submit();
    $("#isAllExcel").val("false");
    document.form1.target = '_self';
}

function viewPropose(uid, subj, subjseq, year) {
    window.self.name = "ProposeList";
    open_window("openMember","","100","100","1024","700",'','','','yes');
    document.form2.target = "openMember";

    document.form2.p_userid.value = uid;
    document.form2.p_subj.value = subj;
    document.form2.p_subjseq.value = subjseq;
    document.form2.p_year.value = year;

    document.form2.submit();
}


// SMS 전송
function whenSMS() {
  if (chkSelected() < 1) {
    alert('SMS를 발송할 학습자를 선택하세요');
    return;
  }

  ff =document.ff;
  ff.p_touch.value = "09";

  window.self.name = "StudentMemberList";
  open_window("openSMS","","100","100","620","354");
  document.ff.target = "openSMS";
  document.ff.action='/servlet/controller.study.StudentStatusAdminServlet';
  document.ff.p_process.value = 'SendSMS';
  document.ff.submit();
}
//전체선택취소
function whenAllSelectCancel() {
  if(document.ff.all['p_checks'] == '[object]') {
    if (document.ff.p_checks.length > 0) {
      for (i=0; i<document.ff.p_checks.length; i++) {
        document.ff.p_checks[i].checked = false;
      }
    } else {
      document.ff.p_checks.checked = false;
    }
  }
}
//전체선택
function whenAllSelect() {
  if(document.ff.all['p_checks'] == '[object]') {
    if (document.ff.p_checks.length > 0) {
      for (i=0; i<document.ff.p_checks.length; i++) {
        document.ff.p_checks[i].checked = true;
      }
    } else {
      document.ff.p_checks.checked = true;
    }
  }
}

// 메일발송
function whenFreeMail() {
  if (chkSelected() < 1) {
    alert('메일을 발송할 학습자를 선택하세요');
    return;
  }

  ff =document.ff;

  window.self.name = "StudentMemberList";
  open_window("openFreeMail","","10","10","800","650");
  document.ff.target = "openFreeMail";
  document.ff.action='/servlet/controller.study.StudyTotalStatusServlet';
  document.ff.p_process.value = 'SendFreeMail';
  document.ff.submit();
}
// 조건채크
function chkSelected() {
  var selectedcnt = 0;
  if(document.ff.all['p_checks'] == '[object]') {
    if (document.ff.p_checks.length > 0) {
      for (i=0; i<document.ff.p_checks.length; i++) {
        if (document.ff.p_checks[i].checked == true) {
          selectedcnt++;
        }
      }
    } else {
      if (document.ff.p_checks.checked == true) {
        selectedcnt++;
      }
    }
  }

  return selectedcnt;
}

//결제세부정보 조회
function fnPayInfo(p_tid) {
    window.self.name = "OffBillList";
    open_window("openPayInfo","","100","100","620","450",false,false,false,true,true);
    document.form1.target = "openPayInfo";
    document.form1.p_tid.value = p_tid;
    document.form1.action='/servlet/controller.off.OffBillAdminServlet';
    document.form1.p_process.value = 'paycancelPage';
    document.form1.submit();
    document.form1.target = window.self.name;
    document.form1.action='/servlet/controller.off.OffApprovalAdminServlet';
}

-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="existSubjSeqDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>
<form name="form1" method="post" action="/servlet/controller.off.OffApprovalAdminServlet">
    <input type="hidden" name="p_process"  value="" />
    <input type="hidden" name="isSeq"    value="true" />
    <input type="hidden" name="p_addSubjSeq" id="p_addSubjSeq" value="0" />
    <input type="hidden" name="p_addSeq" id="p_addSeq" value="0" />
    <input type="hidden" id="lastSubjSeq" name="lastSubjSeq"     value="0" />
    <input type="hidden" name="p_subj"  value="" />
    <input type="hidden" name="p_subjnm" value="" />
    <input type="hidden" name="s_action" value="" />
    <input type="hidden" name="p_orderColumn" />
    <input type="hidden" name="isExcel" id="isExcel" value="false" />
    <input type="hidden" name="isAllExcel" id="isAllExcel" value="false" />
    <input type="hidden" name="p_orderType" value="<%= v_orderType %>" />
    <input type="hidden" name="u_year" id="u_year" value="" />
    <input type="hidden" name="u_seq" id="u_seq" value="" />
    <input type="hidden" name="p_tid" id="p_tid" value="" />
    <input type="hidden" name="u_subjseq" id="u_subjseq" value="" />

    <input type="hidden" name="ss_subj" value="<%= ss_subj %>" />
    <input type="hidden" name="ss_year" value="<%= ss_year %>" />
    <input type="hidden" name="ss_subjseq" value="<%= ss_subjseq %>" />
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/portal/s.1_off04.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->
            <br/>
            <!----------------- form 시작 ----------------->
            <table cellspacing="0" cellpadding="1" class="form_table_out">


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
                                <!------------------- 조건검색 시작 ------------------------->
                                    <table cellspacing="0" cellpadding="0" width="99%">
                                        <tr>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0" width="99%">
                                                    <tr>
                                                        <td>
                                                            <script type="text/javascript">
                                                                var isFirst = 0;
                                                                function fnSearchBox() {
                                                                    if(isFirst>1)
                                                                    changes_subjcode(
                                                                            $("#s_year").val(),
                                                                            $("#s_upperclass").val(),
                                                                            $("#s_middleclass").val(),
                                                                            $("#s_lowerclass").val(),
                                                                            $("#s_year").val()
                                                                            );
                                                                    isFirst++;
                                                                }
                                                                function fnSearchBoxs_middleclass() {
                                                                    if(isFirst>1)
                                                                    changes_lowerclass($("#s_upperclass").val(), $("#s_middleclass").val());
                                                                    isFirst++;
                                                                }
                                                                function fnSearchBoxs_subjterm() {
                                                                    if(isFirst>2)
                                                                        changes_subjterm($('#s_subjcode').val(), $('#s_year').val(), $('#s_subjseq').val());
                                                                        isFirst++;
                                                                }
                                                            </script>
                                                            <font color="red">★</font>연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" " onChange="fnSearchBox();" attr=" " selectedValue="<%= ss_year %>" isLoad="true" all="none" />
                                                            대분류 <kocca_select:select name="s_upperclass" sqlNum="off.0002"  param=" " onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                                                            중분류 <kocca_select:select name="s_middleclass" sqlNum="off.0003"  param="<%= ss_upperclass %>" onChange="changes_lowerclass(s_upperclass.value, this.value);" afterScript="fnSearchBoxs_middleclass" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                                                            소분류 <kocca_select:select name="s_lowerclass" sqlNum="off.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>" afterScript="fnSearchBox" onChange="fnSearchBox()" attr=" " selectedValue="<%=ss_lowerclass %>" isLoad="true" all="true" />
                                                        </td>
                                                        <td rowspan=2 width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') --></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <%--과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "--%>
                                                            <%--onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />--%>
                                                            <font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj" param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>" onChange="changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no"/>
                                                            차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>" onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                                                            과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') Main_subjcourse();" value="<%=s_subjsearchkey%>">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                <!-------------------- 조건검색 끝 ---------------------------->
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
<br/>
<!----------------- 추가 버튼 시작 ----------------->
</form>

<form name="ff" method="post" action="/servlet/controller.off.OffApprovalAdminServlet">
    <input type="hidden" name="p_touch" value='03'>
    <input type="hidden" name="p_offlinemail" value='true'>
    <input type="hidden" name="p_middleclass">
    <input type="hidden" name="p_lowerclass" >
    <input type="hidden" name="p_upperclass" >
    <input type="hidden" name="s_subjsearchkey" >
    <input type="hidden" name="p_process"  value="">

<!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
<table width="97%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
        <td  class=sub_title>과정기준조회</td>
        <td align="right" height="20" width="58">&nbsp;</td>
        <td width="8"></td>
        <td align="right" height="20" width="70">
            <a href="javascript:whenSMS()"><img src="/images/admin/button/btn_sms.gif" border="0"></a>
        </td>
        <td width="8"></td>
        <td align="right" height="20" width="70">
            <a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>
        </td>
        <td width="8"></td>
        <td align="right" height="20" width="70"><a href="javascript:whenChangeAll(1)"><img src="/images/admin/button/btn_allok.gif" border="0"></a></td>
        <td width="8"></td>
        <td align="right" height="20" width="90"><a href="javascript:whenChangeAll(0)"><img src="/images/admin/button/btn_allcancel.gif" border="0"></a></td>
        <td width="8"></td>
        <td align="right" height="20" width="40"><a href="javascript:whenApprovalProcess()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
        <td width="8"></td>
        <td align="right" height="20" width="70"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
        <td width="8"></td>
        <td align="right" height="20" width="70"><a href="javascript:whenAllExcel()"><img src="/images/admin/button/btn_allexcelprint.gif" border="0" alt="해당연도 전체출력"></a></td>
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
        <td height="3"></td>
        <td height="3"></td>
        <td height="3"></td>
        <td height="3"></td>
    </tr>
</table>
<!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

<!----------------- 과정 관리 시작 ----------------->
<table cellspacing="1" cellpadding="5" class="table_out">
    <tr>
        <td colspan="13" class="table_top_line"></td>
    </tr>
    <tr>
        <td class="table_title" width="34"><b>NO</b></td>
        <td class="table_title"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
        <td class="table_title">직장(학교)명</td>
        <td class="table_title"><a href="javascript:whenOrder('appdate')" class="e">신청일</a></td>
        <td class="table_title">승인상태</td>
        <td class="table_title">결제방법</td>
        <td class="table_title"><a href="javascript:whenOrder('pgauthdate')" class="e">결제일</a></td>
        <td class="table_title">결제상태</td>
        <td class="table_title"><%=ss_subj.equals("SB13033")?"첨부파일":"결재금액" %></td>
        <td class="table_title">학번</td>
        <td class="table_title">취소요청일/결제취소</td>
        <td class="table_title">
            <input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall()"/>
<script language='javascript'>
    function chkeckall(){
        if(document.ff.p_chkeckall.checked){
            whenAllSelect();

        } else {
            whenAllSelectCancel();
        }
    }
</script>
        </td>
    </tr>
<%
    if(ss_action.equals("go") && list!= null) { //go button 선택시만 list 출력
        DataBox data = null;
        String acceptResult = null;
        String v_paymethod = null;
        String v_cancel_btn = null;
        String v_tid = null;
        String v_resultcode = null;
        String v_price="";

        String v_appdate = "";

        int v_limit=0;
        for (int i=0; i<list.size(); i++) {
            data  = (DataBox)list.get(i);

            v_appdate   = data.getString("d_appdate");

            v_paymethod = data.getString("d_paymethod");
            v_tid = data.getString("d_tid");
            v_resultcode = data.getString("d_resultcode");
            v_price=data.getString("d_price");
            v_limit=data.getInt("d_studentlimit");

            int convert = Integer.parseInt(v_price);
            DecimalFormat df = new DecimalFormat("#,###");
            String price=(String)df.format(convert);

            if ("결제완료".equals(v_resultcode) && (!"TaxBill".equals(v_paymethod) && (!"BankBook".equals(v_paymethod)))) {
                v_cancel_btn = "<a href=\"javascript:fnPayInfo('" + v_tid + "')\">[결제취소]</a>";
            } else {
                v_cancel_btn = "-";
            }

            String color="";
            if(i<v_limit) {
                color="";
            } else {
                color="background-color:#FFE0F4";
            }
%>
    <tr id="<%=data.get("d_subjnm")%>">
        <td class="table_01" style="<%=color%>">
            <input type="hidden" name="p_userid" value="<%=data.get("d_userid") %>"/>
            <input type="hidden" name="p_subj" value="<%=data.get("d_subj") %>"/>
            <input type="hidden" name="p_subjseq" value="<%=data.get("d_subjseq") %>"/>
            <input type="hidden" name="p_year" value="<%=data.get("d_year") %>"/>
            <input type="hidden" name="p_seq" value="<%=data.get("d_seq") %>"/>
            <%=String.valueOf(i+1)%>
        </td>
        <td class="table_01" style="<%=color%>"><a href="javascript:viewPropose('<%=data.get("d_userid") %>','<%=data.get("d_subj") %>','<%=data.get("d_subjseq") %>','<%=data.get("d_year") %>');"><%=data.get("d_name") %></a></td>
        <td class="table_01" style="<%=color%>"><%=data.get("d_comptext") %></td>
        <td class="table_01" style="<%=color%>"><%=FormatDate.getFormatDate(v_appdate,"yyyy-MM-dd HH:mm:ss")%></td>

<%
            if(data.get("d_chkstatus").length() == 2 && data.get("d_chkstatus").charAt(1) == 'Y') {
%>
        <td class="table_01" style="background-color:blue">
<%
            } else {
%>
        <td class="table_01" style="<%=color%>">
<%
            }
%>
            <%=chkAcceptResult(acceptResultList, data.get("d_chkstatus")) %>
        </td>
        <td class="table_01" style="<%=color%>"><%=data.get("d_paymethod") %></td>
        <td class="table_01" style="<%=color%>"><%=data.getDate("d_pgauthdate") %> <%= data.getOnlyHourMinute("d_pgauthtime") %></td>
        <td class="table_01" style="<%=color%>"><%=data.get("d_resultcode") %></td>
<%
            if(ss_subj.equals("SB13033")) {
%>
        <td class="table_01" style="<%=color%>">
<%
                if(!data.get("d_savefilenm").equals("")) {
%>
            <a href="/servlet/controller.library.DownloadServlet?p_realfile=<%=data.get("d_savefilenm") %>&p_savefile=<%=data.get("d_realfilenm") %>" target="_blank"><img src="/images/2012/sub/lectur/btn_hangeul.png" class="icon"/></a>
<%
                } else {
%>
            -
<%
                }
%>
        </td>
<%
            } else {
%>
        <td class="table_01" style="<%=color%>"><%=price %></td>
<%
            }
%>
        <td class="table_01" style="<%=color%>"><%=data.get("d_studentno") %></td>
        <td class="table_01" style="<%=color%>"><%if(!data.getDate("d_canceldate").equals("")) {out.println(data.getDate("d_canceldate"));}else{out.println("-");} %>/<%=v_cancel_btn %></td>
        <td class="table_02_1" style="<%=color%>">
            <input type="checkbox" name="p_checks" value="<%=data.get("d_userid") %>,<%=data.get("d_subj") %>,<%=data.get("d_year") %>,<%=data.get("d_subjseq") %>,<%=data.get("d_grcode") %>">
        </td>
    </tr>
<% 
        }

        if (list.size()==0) {
%>
    <tr><td colspan=14 class="table_02_1">검색된 결과가 없습니다.</td></tr>
<%
        }
    }
%>
</form>
<form name="form2" method="post" action="/servlet/controller.off.OffApprovalAdminServlet">
    <input type="hidden" name="p_userid" value=""/>
    <input type="hidden" name="p_subj" value=""/>
    <input type="hidden" name="p_subjseq" value=""/>
    <input type="hidden" name="p_year" value=""/>
    <input type="hidden" name="p_process" value="viewPropose"/>
</form>
</table>
<!----------------- 과정 관리 끝 ----------------->

<%@ include file = "/learn/library/getJspName.jsp" %>

</body>
</html>
