<%
/**
 * file name : za_OffLineApproval_L.jsp
 * date      :
 * programmer:
 * function  : 수강신청 승인 리스트 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
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

    String  v_process  = "listPage";

    String  ss_grcode          = box.getString("s_grcode");             //교육그룹
    String  ss_grseq           = box.getString("s_grseq");              //교육차수
    String  ss_grseqnm         = box.getString("s_grseqnm");            //교육차수명
    String  ss_mastercd        = box.getString("s_mastercd");           //과정&코스
    String  ss_upperclass      = box.getString("s_upperclass");         //과정대분류
    String  ss_middleclass     = box.getString("s_middleclass");        //과정중분류
    String  ss_lowerclass      = box.getString("s_lowerclass");         //과정소분류
    String  ss_subjcourse      = box.getString("s_subjcourse");         //과정&코스
    String  ss_subjseq         = box.getString("s_subjseq");            //과정 차수
    String  ss_selgubun        = box.getString("s_selgubun");
    String  ss_seltext         = box.getString("s_seltext");
    String  ss_seldept         = box.getString("s_seldept");
    String  ss_edustart        = box.getString("s_start");              //교육시작일
    String  ss_eduend          = box.getString("s_end");                //교육종료일
    String  v_isManagerPropose = box.getString("isManagerPropose");     //운영자승인
    String  ss_subjgubun        = box.getString("s_subjgubun");     //오프라인 강좌 구분
    String  ss_super            = box.getString("p_super");         //우수/일반 회원 구분

    String  v_step      = box.getString("p_step");
    String  v_appstatus = box.getString("p_appstatus");
    String  v_searchkind = box.getStringDefault("p_searchkind", "subjseq");

    String  v_action    = box.getString("p_action");
    String  v_gadmin    = box.getString("s_gadmin");

//    int     v_appauth   = ((Integer)request.getAttribute("appauth")).intValue();
    //int       v_appauth = 0;
    String []   stat_k  = {"ALL","W","Y","N"};
    String []   stat_v  = {"전체","미처리","승인","반려"};


    String  v_finalString = ""; //최종승인 select box 태그

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
    int i=0;

    String  v_ongo     = "whenSelection('go')";
    String  v_seq      = box.getString("isManagerPropose");

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
	$("#p_propstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
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


  document.form1.action='/servlet/controller.propose.ApprovalServlet';
  document.form1.p_process.value = 'OffLinelistPage';                  //APPROVALBEAN.SelectApprovalScreenList (ATTR 
  
  document.form1.target = window.self.name;
  document.form1.submit();
}

// 검색조건 체크 -> 교육그룹은 필수
function chkParam() {
  if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
    alert("교육그룹을 선택하세요.");
    return false;
  }/*
  if (document.form1.s_grseq.value == 'ALL' || document.form1.s_grseq.value == '') {
    alert("교육차수를 선택하세요.");
    return false;
  }
  */
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
    document.ff.p_process.value = "OffLineapprovalProcess";

    document.ff.submit();
}

//전체 옵션선택 버튼
function whenChangeAll(idx){
    if(!confirm("변경가능한 승인옵션이 모두 변경됩니다.\n\n계속하시겠습니까?")) return;

    for(var i=0;i<document.ff.length;i++){
    //alert(document.ff.elements[i].type+":"+document.ff.elements[i].selectedIndex);
        if(document.ff.elements[i].type=="select-one"){
            document.ff.elements[i].selectedIndex=idx;
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

        document.form1.action='/servlet/controller.propose.ApprovalServlet';
        document.form1.p_process.value = 'OffLineExcelDown'; 
        document.form1.p_action.value = "go";

        document.form1.submit();                    
        
    }
//-->
</script>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/propose/tit_offline_apply.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post" action="/servlet/controller.propose.ApprovalServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_masternm"  value="">
    <input type="hidden" name="p_mastercd"  value="<%=ss_mastercd%>">
    <input type="hidden" name="p_action"  value="">
    <input type="hidden" name="p_order"  value="name">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">

    <input type="hidden" name="p_grcode"    >
    <input type="hidden" name="p_grseq"     >
    <input type="hidden" name="p_gyear"     >
    <input type="hidden" name="p_subj"     >
    <input type="hidden" name="p_subjseq"   >
    <input type="hidden" name="p_gubun"     value="3">
    <input type="hidden" name="p_gubunnm"   value="교육개시">
    <input type="hidden" name="p_ispropose" value="">
    <input type="hidden" name="p_isapproval" value="">
    <input type="hidden" name="p_searchkind" value="<%=v_searchkind%>">

    <input type="hidden" name="p_returnurl"       value="/servlet/controller.propose.ApprovalServlet">
    <input type="hidden" name="p_returnprocess"   value="OffLinelistPage">


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
                      <td width="90%" colspan='4'>
                            <!------------------- 조건검색 시작 ------------------------->
                            <font color="red">★</font>과정 분류
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
	<kocca_select:select name="s_subjgubun" sqlNum="gubun"  param="0061"
	onChange="" attr=" " selectedValue="<%= ss_subjgubun %>" isLoad="true" all="false" />
                        <!------------------- 조건검색 시작 ------------------------->
<font color="red">★</font>
	교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
	onChange="" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
                        
                             <input type="hidden" name="p_step"  value="2">
                             승인
                              <select name="p_appstatus">
                              <%
                                  for(int ix=0;ix<4;ix++){
                                      %>  <option value="<%=stat_k[ix]%>" <%=((v_appstatus.equals(stat_k[ix])? "selected":" "))%> ><%=stat_v[ix]%></option> <%
                                  }
                              %>
                              </select>

                            <!-------------------- 조건검색 끝 ---------------------------->
							수강신청일 <input name="p_propstart" id="p_propstart" type="text" class="datepicker_input1" size="10" value="">
                        ~ <input name="p_propend" id="p_propend" type="text" class="datepicker_input1" size="10" value="">
                            <input type = "hidden" name="s_start">
                            <input type = "hidden" name="s_end">
                            <input type="hidden" name="p_step"  value="2">
                        <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
      </table>

      <!----------------- form 끝 ----------------->

</form>


<!---------------------////////////////////////////// 과정/차수정보 ////////////////////////////////-------------------------->
        <%
        String v_ispropose          ="";
        String v_proposetype        ="";
        String v_isedutarget        ="";
        String v_educnt             ="";
        String v_mastercd           ="";
        String v_masternm           ="";

        if (!ss_subjcourse.equals("ALL")&&!ss_subjseq.equals("ALL")&&!ss_subjcourse.equals("")&&!ss_subjseq.equals("")&&v_action.equals("go")) {
            DataBox dbox1 = (DataBox)request.getAttribute("isOffLineManagerStatus");
            if (dbox1 !=null ) {
                  v_ispropose          = dbox1.getString("d_ispropose");
                  v_mastercd           = dbox1.getString("d_mastercd");
                  v_masternm           = dbox1.getString("d_masternm");
                  v_proposetype        = dbox1.getString("d_proposetype");
                  v_isedutarget        = dbox1.getString("d_isedutarget");
                  v_educnt             = dbox1.getString("d_educnt");
            }
        }
%>

        <!---------------------////////////////////////////// 과정/차수정보끝 ////////////////////////////////-------------------------->


        <!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>과정기준조회</td>
            <td align="right" height="20" width="58"></td>
            <td width=8></td>
            <td align="right" height="20" width="70"><a href="javascript:whenChangeAll(0)"><img src="/images/admin/button/btn_allok.gif" border="0"></a></td>
            <td width=8></td>
            <td align="right" height="20" width="90"><a href="javascript:whenChangeAll(1)"><img src="/images/admin/button/btn_allcancel.gif" border="0"></a></td>
            <td width=8></td>
            <td align="right" height="20" width="70"><a href="javascript:whenChangeAll(2)"><img src="/images/admin/button/btn_alldelete.gif" border="0"></a></td>
            <td width=8></td>
            <td align="right" height="20" width="40"><a href="javascript:whenApprovalProcess()"><img src="/images/admin/button/btn_checkok.gif" border="0"></a></td>
            <td width=8></td>
            <td align="right" height="20" width="70"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
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
          </tr>
        </table>
        <!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="9" class="table_top_line"></td>
          </tr>
          <FORM name="ff" method="post" action="/servlet/controller.propose.ApprovalServlet">
            <input type="hidden" name="p_process"    value="approvalProcess">
            <input type="hidden" name="p_grcode"     value="<%=ss_grcode%>">
            <input type="hidden" name="p_grseq"      value="<%=ss_grseq%>">
            <input type="hidden" name="p_grseqnm"    value="<%=ss_grseqnm%>">
            <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
            <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
            <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">
            <input type="hidden" name="p_subjcourse" value="<%=ss_subjcourse%>">
            <input type="hidden" name="p_subjseq"    value="<%=ss_subjseq%>">
            <input type="hidden" name="p_searchkind" value="<%=v_searchkind%>">
            <input type="hidden" name="p_mastercd"   value="<%=ss_mastercd%>">
            <input type="hidden" name="p_masternm"   value="">
            <input type="hidden" name="s_subjgubun"   value="<%=ss_subjgubun%>">
            <input type="hidden" name="p_step"       value="<%=v_step%>">
            <input type="hidden" name="p_appstatus"  value="<%=v_appstatus%>">

          <tr>
            <td class="table_title" width="10%"><b><a href="javascript:ordering('c.codenm')" class="e">분류</a></b></td>
            <td class="table_title" width="20%"><a href="javascript:ordering('s.subjnm')" class="e">강의명</a></b></td>
            <td class="table_title" width="10%"><b>수강신청기간</b></td>
            <td class="table_title" width="10%"><b><a href="javascript:ordering('s.dday')" class="e">강좌 일시</a></b></td>
            <td class="table_title" width="10%"><b>시간</b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('p.userid')" class="e">ID</a></b></td>
            <td class="table_title" width="5%"><b><a href="javascript:ordering('m.name')" class="e">성명</a></b></td>
            <td class="table_title" width="5%"><b>최종승인</b></td>
            <td class="table_title" width="10%"><b><a href="javascript:ordering('p.ldate')" class="e">신청일시</a></b></td>
          </tr>

<%  //out.println(v_action);
        if (v_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("OffLineApprovalList");
        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

        v_seq   = "";
        String v_gubunNm    = "";
        String v_subjnm     = "";
        String v_propstart  = "";
        String v_propend    = "";
        String v_dday       = "";
        String v_starttime  = "";
        String v_endtime    = "";
        String v_membergubun = "";
        String v_userid     = "";
        String v_superyn    = "";
        String v_status     = "";
        String v_name       = "";
        String v_ldate      = "";

        for (i=0; i<list.size(); i++) 
        {
            DataBox dbox = (DataBox)list.get(i);

            v_seq       = dbox.getString("d_seq");
            v_gubunNm   = dbox.getString("d_gubunnm");
            v_subjnm    = dbox.getString("d_subjnm");
            v_propstart = dbox.getString("d_propstart");
            v_propend   = dbox.getString("d_propend");
            v_dday      = dbox.getString("d_dday");
            v_starttime = dbox.getString("d_starttime");
            v_endtime   = dbox.getString("d_endtime");
            v_membergubun= dbox.getString("d_membergubun");
            v_userid    = dbox.getString("d_userid");
            v_superyn   = dbox.getString("d_superyn");
            v_status    = dbox.getString("d_status");
            v_name      = dbox.getString("d_name");
            v_ldate     = dbox.getString("d_ldate");

            if(v_superyn.equals("Y")) { v_superyn = "★";}
            else {v_superyn = "";}

            //날짜 처리
            if(v_propstart.equals("")){v_propstart = "미정";}
            else{v_propstart     = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");}

            if(v_propend.equals("")){v_propend = "미정";}
            else{v_propend     = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");}

            if(v_dday.equals("")){v_dday = "미정";}
            else{v_dday     = FormatDate.getFormatDate(v_dday,"yyyy/MM/dd");}
%>
            <tr class="table_02_1">
                <td class="table_02_1"><%=v_gubunNm%></td>
                <td class="table_02_2"><%=v_subjnm%></td>
                <td class="table_02_1"><%=v_propstart%> ~ <br> <%=v_propend%></td>
                <td class="table_02_1"><%=v_dday%></td>
                <td class="table_02_1"><%=v_starttime%> 시 ~ <br> <%=v_starttime%>시</td>
                <td class="table_02_1"><a href="javascript:whenMemberInfo('<%=v_userid%>')" class="e"><%=v_userid%><%=v_superyn%></a></td>
                <td class="table_02_1"><%=v_name%></td>
                <td class="table_02_1">
                    <select name="p_status">
                        <option value="<%=(v_seq + ":" + ss_grcode + ":"+v_userid)%>:Y" <% if(v_status.equals("Y")) { %> selected <%}%>>승인</option>
                        <option value="<%=(v_seq + ":" + ss_grcode + ":"+v_userid)%>:W" <% if(v_status.equals("W")) { %> selected <%}%>>미처리</option>
                        <option value="<%=(v_seq + ":" + ss_grcode + ":"+v_userid)%>:N" <% if(v_status.equals("N")) { %> selected <%}%>>반려</option>
                        <option value="<%=(v_seq + ":" + ss_grcode + ":"+v_userid)%>:D" <% if(v_status.equals("D")) { %> selected <%}%>>삭제</option>
                    </select></td>
                <td class="table_02_1"><%=FormatDate.getFormatDate(v_ldate,"yyyy-MM-dd HH:mm:ss")%></td>                
            </tr>
<%          //}
        }
        if (i == 0)
            { %>
            <tr>
                <td align="center" bgcolor="#FFFFFF" height="100" colspan="9">등록된 내용이 없습니다.</td>
              </tr>
            <%}
    }

%>
           
            </FORM>
        </table>
        <!----------------- 과정기준조회 끝 ----------------->

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right" class="ms">◈<b> TOTAL : <%=i%> 명</b></td>
          </tr>
        </table>

        <br>

      </td>
  </tr>

</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
