<%
//**********************************************************
//  1. 제      목: 분석 > 학습자 경로분석
//  2. 프로그램명:  za_Route_L.jsp
//  3. 개      요: 학습자경로 분석
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode        = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_subj          = box.getString("s_subjcourse");
    String  v_gyear         = box.getString("s_gyear");
    String  v_subjseq       = box.getString("s_subjseq");
    int     v_sulpapernum   = box.getInt("s_sulpapernum");
    String  v_action    = box.getString("p_action");
    int i = 0 ;
    
 // 셀렉트 검색 관련 (09.12.10)
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수

    //int     v_replycount    = box.getInt("p_replycount");

    int     v_replycnt      = 0;
    double  v_replyrate     = 0;


    String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
    String v_tab_color4 = "blue";
    String v_tab_color5 = "black";
    DecimalFormat  df = new DecimalFormat("0.00");


    String v_comlcnt  = "";
    String v_ncomlcnt = "";
    int v_educnt_i = 0; //교육인원
    int v_educnt_total = 0; //교육인원 총합
    int v_comlcnt_i = 0;    //수료인원
    int v_comlcnt_total = 0;    //수료인원 총합
    int v_ncomlcnt_i = 0;   //미수료 인원
    int v_ncomlcnt_total = 0;   //미수료인원 총합
    double v_suryopercen = 0;   //수료율
    double v_suryopercen_total = 0; //수료율 총합
    double re1 = 0;

    // 개인별
    ArrayList list1      = null;
    DataBox data1        = null;
    list1 = (ArrayList)request.getAttribute("ResultMemberList");

    // 수료율
    ArrayList studentlist = null;

    if ( v_action.equals("go") ) {
        ArrayList textlist = (ArrayList)request.getAttribute("ResultTextList");
    }
%>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 검색
    function whenSelection(v_action) {
        if (v_action=="go"){
            top.ftop.setPam();
            if (chkParam() == false) {
                return;
            }
        }
        document.form1.p_action.value = v_action;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.analysis.InclinationAdminServlet';
        document.form1.p_process.value = 'routeList';
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

    // 엑셀 출력
    function whenExcel() {
        if(document.form1.p_action.value != "go"){
            alert('먼저 조회해 주십시오');
            return;
      }
        //window.self.name = "StudyTotalList";
        //open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");
        //document.form1.target = "openExcel";
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.analysis.InclinationAdminServlet';
        document.form1.p_process.value = 'routeListExcel';
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
/*
      if (document.form1.s_subjcourse.value == 'ALL' || document.form1.s_subjcourse.value == '----') {
        alert("과정을 선택하세요.");
        return false;
      }
      if (document.form1.s_subjseq.value == 'ALL' || document.form1.s_subjseq.value == '----') {
        alert("차수를 선택하세요.");
        return false;
      }
*/
    }

// 인원상세정보 팝업
function open_detailList() {
    window.self.name = "JobRouteList";     //opener 객체에 name 을 지정한다
//  open_window("openJobDetailList","","100","0","800","600","1");
    farwindow = window.open("", "openJobRouteList", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 680, height = 567, top=0, left=0");
    document.form1.target = "openJobRouteList";
    document.form1.action = "/servlet/controller.analysis.InclinationAdminServlet";
    document.form1.p_process.value = "jobRouteList";
    document.form1.submit();
    document.form1.target = window.self.name;

}

//-->
</SCRIPT>
</head>



<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <form name="form1" method="post" action="/servlet/controller.analysis.InclinationAdminServlet">
    <input type="hidden" name="p_process"       value="">
    <input type="hidden" name="p_action"        value="<%=v_action%>">
    <input type="hidden" name="p_grcode"        value="<%=v_grcode%>">
    <input type="hidden" name="p_gyear"         value="<%=v_gyear%>">
    <input type="hidden" name="p_subj"          value="<%=v_subj%>">
    <input type="hidden" name="p_subjsel"       value="<%=v_subj%>">
    <input type="hidden" name="p_subjseq"       value="<%=v_subjseq%>">
    <input type="hidden" name="p_sulpapernum"   value="<%=v_sulpapernum%>">
    <input type="hidden" name="p_gubun"         value="">


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
       <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/analysis/tit_analysis_stu2.gif" ></td>
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
                  <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td height=25><script type="text/javascript">
                        var startup = 0;
                        function Main_s_subjseq(){
                            if (startup > 1)
                                changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);
                        }
                        function Main_subjcourse(){
                            if(startup > 1) 
                                changes_subjcourse(document.form1.s_grcode.value, document.form1.s_upperclass.value, document.form1.s_middleclass.value, document.form1.s_lowerclass.value, document.form1.s_grseq.value,'' );
                            startup++;
                        }
                        </script>
                        <font color="red">★</font> 
				                        교육그룹 <kocca_select:select name="s_grcode" sqlNum="course.0001"  param=" "
				                        onChange="changes_gyear(this.value);" attr=" " selectedValue="<%= ss_grcode %>" isLoad="true" all="false" />
                        <font color="red">★</font> 
				                        연도 <kocca_select:select name="s_gyear" sqlNum="course.year"  param="<%= ss_grcode %>"
				                                onChange="changes_grseq(s_grcode.value, this.value);" afterScript="Main_subjcourse" attr=" " selectedValue="<%= ss_gyear %>" isLoad="true" all="false" />
                        <font color="red">★</font>                                         
                                                                교육차수 <kocca_select:select name="s_grseq" sqlNum="course.subjseq"
                                param="<%=ss_grcode%>" param2="<%=ss_gyear%>"
                                attr=" style='width:130px;'" onChange="Main_subjcourse();" afterScript="" selectedValue="<%= ss_grseq %>" isLoad="true" all="true" />
                                                                대분류 <kocca_select:select name="s_upperclass" sqlNum="course.0002"  param=" "
                                onChange="changes_middleclass(this.value);Main_subjcourse();" attr=" "  selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
                                                                중분류 <kocca_select:select name="s_middleclass" sqlNum="course.0003"  param="<%= ss_upperclass %>"
                                onChange="changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();" attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
                                                                소분류 <kocca_select:select name="s_lowerclass" sqlNum="course.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
                                onChange="Main_subjcourse();" attr=" " selectedValue="<%= ss_lowerclass %>" isLoad="true" all="true" />
                       </td>
                     </tr>
                     <tr>
                       <td height=25>
                       <font color="red">★</font> 
                                                            과정 <kocca_select:select name="s_subjcourse" sqlNum="subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9"
                                        param="<%=ss_grcode%>"param4="<%=ss_upperclass%>"param5="<%=ss_middleclass%>"param6="<%=ss_lowerclass%>"
                                        param7="<%=ss_grseq%>" param9=""
                                        onChange="changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value)" attr=" " selectedValue="<%= ss_subjcourse %>" isLoad="true" all='<%= box.getSession("isSubjAll") %>' />
                      <font color="red">★</font>                                 
                                                             차수 <kocca_select:select name="s_subjseq" sqlNum="subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4"
                                        param="<%=ss_grcode%>" param2="<%=ss_grseq %>" param3="<%=ss_subjcourse%>" param4="<%=ss_gyear%>"
                                        onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
                      <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                      </td>
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

      <br>
      <br>

      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <!--td align="right" width="65">
            <a href="javascript:whenAllSelect()"><img src="/images/admin/button/select1_butt.gif"  border="0"></a>
            </td>
            <td width=4></td>
            <td align="right" width="65">
            <a href="javascript:whenAllSelectCancel()"><img src="/images/admin/button/select_cancel1_butt.gif"  border="0"></a>
            </td>
            <td width=4></td-->
            <td align="right" height="20">&nbsp;
            <!--a href="javascript:whenFormMail()"><img src="/images/admin/button/btn_dogmail.gif"  border="0"></a-->
            </td>
            <td width="4"></td>
            <td align="right" width="65">
            </td>
            <td width=4></td>
            <td align="right" width="65">
            </td>
            <td width=4></td>
            <td align="right" width="200">
            <a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
            </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 독려메일, 메일발송, 엑셀출력, 모두선택, 모두선택취소  버튼 끝 ----------------->

      <!----------------- 성적별 학습현황 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="23" class="table_top_line"></td>
        </tr>
        <tr>
          <td  class="table_title" width="5%" >N0</td>
          <td  class="table_title">가입경로</td>
          <td  class="table_title">교육인원</td>
          <td  class="table_title">수료인원</td>
          <td  class="table_title">미수료인원</td>
          <td  class="table_title">수료율</td>
        </tr>

<%  ArrayList    list     = (ArrayList)request.getAttribute("SulmunResultList");
    ArrayList    textlist = (ArrayList)request.getAttribute("ResultTextList");

        String v_answers  = "";
        String v_seltext  = "";
        int v_suryo     = 0;
        int v_nosuryo       = 0;
        int v_total     = 0;

if( list.size() != 0 ){

   int suryo          =0;
   int nosuryo        =0;
   int total          =0;
   double suryopercen =0;

/*select seltext, selnum from tz_sulsel where subj='REGIST' and grcode = 'N000001' and sulnum = '1' */
    for ( i=0; i < textlist.size(); i++) {
        DataBox t_dbox = (DataBox)textlist.get(i);

        String t_seltext = t_dbox.getString("d_seltext");
        String t_selnum  = t_dbox.getString("d_selnum");

        suryo = 0;    nosuryo = 0;    total = 0;      suryopercen = 0;

            for (int j=0; j < list.size(); j++) {
                DataBox dbox = (DataBox)list.get(j);

                 v_answers = dbox.getString("d_answers");
                 v_seltext = dbox.getString("d_seltext");
                 v_suryo   = dbox.getInt("d_comlcnt");
                 v_nosuryo = dbox.getInt("d_ncomlcnt");
                 v_total   = v_suryo+ v_nosuryo;

                 v_suryopercen = ((v_total - v_nosuryo) /(double)v_total) * 100;

                if( t_selnum.equals(v_answers) ){

                  suryo       = v_suryo;
                  nosuryo     = v_nosuryo;
                  total       = v_suryo + v_nosuryo;
                  suryopercen = v_suryopercen;
%>

<%              }
            }
%>

                    <tr>
                      <td width="7%" class="table_01"><%=i+1%></td>
                      <td width="35%" style="padding-left=6" class="table_02_2"><%=t_seltext%></td>
                      <td width="11%" class="table_02_1"><a href="javascript:open_detailList()"><%=total%>명</td>
                      <td width="11%" class="table_02_1"><%=suryo%>명</td>
                      <td width="11%" class="table_02_1"><%=nosuryo%>명</td>
                      <td width="11%" class="table_02_1"><%=suryopercen%>%</td>
                    </tr>

<%  }
}
%>

<%if( list.size() == 0 ){%>
                    <tr class="table_01" height="35">
                        <td colspan="6"> 경로분석에 대한 평가가 없습니다. </td>
                    </tr>
<%}
%>
</table>
      <!----------------- 성적별 학습현황 끝 ----------------->
     <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>

</table>
</form>
