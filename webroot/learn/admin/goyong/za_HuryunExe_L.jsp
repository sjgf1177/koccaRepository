<%
//**********************************************************
//  1. 제      목: 훈련실시신고
//  2. 프로그램명:  za_HuryunExe_L.jsp
//  3. 개      요: Qna 리스트 조회화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 7. 7 이연정
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.library.PageUtil" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.goyong.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%  

    String startDate  = FormatDate.getRelativeDate(-60);
    String endDate = FormatDate.getRelativeDate(30);

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
 
    String  v_action    = box.getString("p_action");
    
    //페이징 처리
    int     v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
    int     v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

    String  v_dispnum   = "";       
    int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     v_total_rowcount = 0;
    int     i = 0;
    
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
//    String  ss_company      = box.getString("s_company");          //회사

    ArrayList list = null;
    int listsize = 0;
    if ( v_action.equals("go") ) {
        list = (ArrayList)request.getAttribute("StudentList");

        listsize = list.size();
    }

    String searchStart = FormatDate.getFormatDate(startDate,"yyyy-MM-dd");
    String searchEnd   = FormatDate.getFormatDate(endDate,"yyyy-MM-dd");
    
    String  v_searchStart = box.getString("p_searchStart");
    String  v_searchEnd   = box.getString("p_searchEnd");


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
	$("#p_searchStart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_searchEnd").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}
//-->
function whenSelection(p_action) {

  document.form1.p_action.value = p_action;
  //alert(document.form1.p_action.value);
  if (p_action == 'go') {
//    if (chkParam() == false) {
  //    return;
    //}
    top.ftop.setPam();
  }
  
  //if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
  //  alert("교육그룹을 선택하세요.");
  //  return;
  //}
  
  //if (document.form1.s_gyear.value == 'ALL' || document.form1.s_gyear.value == '') {
  //  alert("교육년도를 선택하세요.");
  //  return;
  //}

  document.form1.p_startdate.value = make_date(document.form1.p_searchStart.value);
  document.form1.p_enddate.value = make_date(document.form1.p_searchEnd.value);

  document.form1.action='/servlet/controller.goyong.GoYongManageServlet';
  document.form1.target = "_self";
  document.form1.p_process.value = 'HuryunExe';                  //com.goyong.GoYongManageBean.selectStudentList(box);
  document.form1.submit();
}

//콤보박스 입력여부에 따라 메세지를 띄워준다.
function chkParam() {
  if (document.form1.s_grcode.value == '----') {
    alert("교육그룹을 선택하세요.");
    return false;
  }
  else if (document.form1.s_subjcourse.value == 'ALL') {
    alert("과정을 선택하세요.");
    return false;
  }
  else if (document.form1.s_subjseq.value == 'ALL') {
    alert("과정차수를 선택하세요.");
    return false;
  }
}

function HuryunDailyPrint(){
    window.self.name = "HuryunDailyPrint";
    open_window("openHuryunDailyPrint","","50","50","750","730",'no','no','no','yes','no');
    document.form1.target = "openHuryunDailyPrint";
    document.form1.action='/learn/admin/goyong/za_HuryunDiaryFrame.jsp';
    //document.form1.p_process.value = 'HuryunDailyPrint';
    document.form1.submit();
    document.form1.target = window.self.name;
}


function whenExcel() {
    window.self.name = "ExcelList";
    open_window("openExcelList","","100","100","700","400","yes","yes","yes","yes","yes");  
      document.form1.p_subj.value = p_subj;
      document.form1.p_year.value = p_year;
      document.form1.p_subjseq.value = p_subjseq;

    document.form1.target = "openExcelList";
    document.form1.action='/servlet/controller.goyong.GoYongManageServlet';
    document.form1.p_process.value = 'ExcelList';
    document.form1.submit();
    document.form1.target = window.self.name;
}

//메모장에 리스트를 출력한다.
function whenTxt(grcode,gyear,grseq,subjseq,subj) {
    document.form1.p_grcode.value=grcode;
    document.form1.p_subj.value=gyear;
    document.form1.p_grseq.value=grseq;
    document.form1.p_subjseq.value=subjseq;
    document.form1.p_subj.value=subj;


    window.self.name = "TxtList";
    open_window("openTxtList","","100","100","300","300","yes","yes","yes","yes","yes");
    document.form1.target = "openTxtList";
    document.form1.action='/servlet/controller.goyong.GoYongManageServlet';
    document.form1.p_process.value = 'TxtList';
    document.form1.submit();    
}

//상세화면 페이지로 이동한다.
function huryunDetailPage(p_subj,p_year,p_subjseq) {

  document.form1.p_subj.value = p_subj;
  document.form1.p_year.value = p_year;
  document.form1.p_subjseq.value = p_subjseq;
  document.form1.action='/servlet/controller.goyong.GoYongManageServlet';
  document.form1.p_process.value = 'HuryunExe1';
  document.form1.target = "_self";
  //document.form1.p_subj.value  = subj;
  document.form1.submit();
}

// 페이지이동
function go(index) {
    document.form1.target = "_self";
     document.form1.p_pageno.value = index;
     document.form1.action = '/servlet/controller.goyong.GoYongManageServlet';
     document.form1.p_process.value = "HuryunExe";
     document.form1.p_action.value = "go";
     document.form1.submit();
}

// 페이지이동
function goPage(pageNum) {
    document.form1.target = "_self";
     document.form1.p_pageno.value = pageNum;
     document.form1.action = '/servlet/controller.goyong.GoYongManageServlet';
     document.form1.p_process.value = "HuryunExe";
     document.form1.p_action.value = "go";
     document.form1.submit();
}

function pagesize(pageSize) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_pagesize.value = pageSize;
    document.form1.action = '/servlet/controller.goyong.GoYongManageServlet';
    document.form1.p_process.value = "HuryunExe";
    document.form1.p_action.value = "go";
    document.form1.submit();
}
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/hrd/hrd_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post" action="/servlet/controller.goyong.GoYongManageServlet">
    <input type="hidden" name= "p_process" value="listPage">
    <input type="hidden" name= "p_masternm"  value="">
    <input type="hidden" name= "p_mastercd"  value="">
    <input type="hidden" name= "p_action"  value="<%=v_action%>">
    <input type="hidden" name= "p_order"  value="name">
    <input type="hidden" name= "p_gubun"  >
    <input type="hidden" name= "p_startdate" value = "">
    <input type="hidden" name= "p_enddate"   value = "">
    <input type="hidden" name= "p_subj"  >
    <input type="hidden" name= "p_year" value = "">
    <input type="hidden" name= "p_subjseq"   value = "">
    <input type="hidden" name= "p_grcode" value = "">
    <input type="hidden" name= "p_grseq"   value = "">
    <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
    <input type = "hidden" name = "p_pagesize"  value = "<%=v_pagesize%>">

        <table class="form_table_out" cellspacing="0" cellpadding="1">
          <tr>
            <td bgcolor="#C6C6C6" align="center">
              <table class="form_table_bg" cellspacing="0" cellpadding="0" border='0'>
                <tr>
                  <td height="7"></td>
                </tr>
                <tr>
                  <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle">
                      <script type="text/javascript">
                        var startup = 0;
                        function Main_s_subjseq(){
                            if (startup > 1)
                                changes_grseq(document.all.s_grcode.value, document.all.s_gyear.value);
                        }
                        function Main_subjcourse(){
                            if(startup > 1)
                                changes_subjcourse(document.all.s_grcode.value, document.all.s_upperclass.value, document.all.s_middleclass.value, document.all.s_lowerclass.value, document.all.s_grseq.value,'' );
                            startup++;
                        }
                        </script>
                        <!------------------- 조건검색 시작 ------------------------->
                        <font color="red">★</font>
                                                                 교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
                        onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
                      </td>   
                      <td width="73%" colspan="2">
                           연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
                                onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
                            교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
                                param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
                                attr=" style='width:130px;'" onChange="" afterScript="Main_subjcourse" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
                            대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
                                onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                            중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
                                onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                            소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
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
                          과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
                                        param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>"
                                        param7="<%=ss_grseq%>" param9=""
                                        onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value)" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
                                        차수 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
                                        param="<%=ss_grcode%>" param2="<%=ss_grseq %>" param3="<%=ss_subjcourse%>" param4="<%=ss_gyear%>"
                                        onChange="whenSelection('go')" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                                         
                                        </td>
                  <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->

                    </tr>
                    <tr>
                        <td height="5"></td>
                    </tr>
                    <tr>
                      <td width="100%" colspan="4">
학습기간 <input name="p_searchStart" id="p_searchStart" type="text" class="datepicker_input1" size="10" value="<%=v_searchStart%>"> &nbsp;
                        - <input name="p_searchEnd" id="p_searchEnd" type="text" class="datepicker_input1" size="10" value="<%=v_searchEnd%>">                       
                      </td>                   
                    </tr>
                  </table>
                  </td>
                </tr>
                <tr>
                  <td height="9"></td>
                </tr>
                <tr>
                  <td align="center">
                  </td>
                </tr>
                <tr>
                  <td height="7"></td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
 
<!------------------------------------- form 끝 ------------------------------------->


        <!----------------- 버튼 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="642" height="10"></td>
          </tr>
          <tr>
            <td valign="bottom"></td>
            <td align="right" height="20" width="58"></td>
           <td align="right" height="20" width="32"><a href="javascript:HuryunDailyPrint()"><img src="/images/admin/button/btn_trainprint.gif" border="0"></a></td>
          </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 버튼 끝 ----------------->


        <!----------------- 조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="11" class="table_top_line"></td>
          </tr>
 
          <tr>
            <td class="table_title" width="5%">No</td>
            <td class="table_title" width="30%">교육그룹</td>
            <td class="table_title" width="15%">교육년도</td>
            <td class="table_title" width="30%">과정명</td>
            <td class="table_title" width="10%">차수</td>
            <td class="table_title" width="15%">파일다운로드</td>
          </tr>
<%
    if ( v_action.equals("go") ) {
%>
<%
        for ( i=0; i<list.size(); i++ ) {
            DataBox dbox = (DataBox)list.get(i);
            v_dispnum       = dbox.getString("d_dispnum");
            v_totalpage     = dbox.getInt("d_totalpage");
            v_rowcount      = dbox.getInt("d_rowcount");
            v_total_rowcount= dbox.getInt("d_totalrowcount");
%>


          <tr class="table_02_1">
            <td class="table_02_1"><%= v_dispnum %></td>
            <td class="table_02_1"><%=dbox.getString("d_grcodenm")%></td>
            <td class="table_02_1"><%=dbox.getString("d_gyear")%></td>
            <td class="table_02_2"><a href="javascript:huryunDetailPage('<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_year")%>','<%=dbox.getString("d_subjseq")%>')" class='b'><%=dbox.getString("d_subjnm")%></a></td>
            <td class="table_02_1"><%=StringManager.cutZero(dbox.getString("d_subjseqgr"))%></td>
            <td class="table_03_1"><a href="javascript:whenTxt('<%=dbox.getString("d_grcode")%>','<%=dbox.getString("d_gyear")%>','<%=dbox.getString("d_grseq")%>','<%=dbox.getString("d_subjseq")%>','<%=dbox.getString("d_subj")%>')"><img src="/images/admin/button/b_fileprint.gif" border="0"></a></td>
           </tr>

<%
        }
    }
%>

        </table>
        
   
        <!----------------- 조회 끝 ----------------->
<%  if(i > 0){ %>
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_total_rowcount) %></td>
          </tr>
        </table>
        <br>
        <% } %>
        <br>
      </td>
  </tr>
</table>
</form>
<table>
    <tr><td>
    <%@ include file = "/learn/library/getJspName.jsp" %>
    </td></tr>
</table>

</body>
</html>
