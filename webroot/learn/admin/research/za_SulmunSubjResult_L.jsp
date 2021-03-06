<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunSubjResult_L.java
//  3. 개      요: 전체 - 과정설문 - 결과분석
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getStringDefault("s_grcode",box.getString("s_grcode"));        //교육그룹

//    String  v_subj        = box.getStringDefault("p_subj",  SulmunSubjBean.DEFAULT_SUBJ);
    String  v_subj        = box.getString("s_subjcourse");
    String  v_gyear       = box.getString("s_gyear");
    String  v_subjseq       = box.getString("s_subjseq");
    int v_sulpapernum  = box.getInt("s_sulpapernum");

    String  v_company        = box.getString("s_company");
    String  v_jikwi       = box.getString("s_jikwi");
    String  v_jikun       = box.getString("s_jikun");
    String  v_workplc       = box.getString("s_workplc");

    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }

    String  s_gadmin = box.getSession("gadmin");

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
    String v_tab_color4 = "black";
    String v_tab_color5 = "black";
    DecimalFormat  df = new DecimalFormat("0.00");

    ArrayList list1 = (ArrayList)request.getAttribute("ResultMemberList");
    ArrayList list = (ArrayList)request.getAttribute("SulmunResultList");
%>
<html>
<head>
<title>설문분석</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 조회 검색
function whenSelection(p_action) {
    if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (p_action=="go") {
        if (document.form1.s_grseq.value=="----"){
            alert("교육차수를 선택하세요");
            return;
        }
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        if (document.form1.s_subjseq.value=="----"){
            alert("차수를 선택하세요");
            return;
        }
        if (document.form1.s_sulpapernum.value=='0'){
            alert("설문지를 선택하세요");
            return;
        }

    }
  document.form1.target = "_self";
  document.form1.p_action.value  = p_action;
  document.form1.p_process.value = 'SulmunResultPage';
  document.form1.submit();
}

// 엑셀출력
function goExcel(p_action) {
    if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (p_action=="go") {
        if (document.form1.s_grseq.value=="----"){
            alert("교육차수를 선택하세요");
            return;
        }
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }

        if (document.form1.s_sulpapernum.value==""){
            alert("설문지를 선택하세요");
            return;
        }
    }

    document.form1.p_grcode.value      = document.form1.s_grcode.value;
    document.form1.p_subj.value        = document.form1.s_subjcourse.value;
    document.form1.p_subjseq.value     = document.form1.s_subjseq.value;
    document.form1.p_gyear.value       = document.form1.s_gyear.value;
    document.form1.p_sulpapernum.value = document.form1.s_sulpapernum.value;

    window.self.name = "reporting";
    document.form1.target = "openExcel1";
    open_window("openExcel1","","100","100","900","600","yes","yes","yes","yes","yes");
    document.form1.p_process.value="SulmunResultExcelPage";
    document.form1.action="/servlet/controller.research.SulmunSubjResultServlet"
    document.form1.submit();

    document.form1.target = window.self.name;
}


// 개인별 보기
function UserPage(p_grcode, p_subj, p_sulpapernum, name, userid, answers) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=678, height=600, top=0, left=0");
  document.form1.target = "openWinQuestion";
  document.form1.p_process.value = "SulmunUserPage";
  document.form1.p_grcode.value  = p_grcode;
  document.form1.p_subj.value    = p_subj;
  document.form1.p_sulpapernum.value = p_sulpapernum;
  document.form1.p_userid.value = userid;
  document.form1.p_name.value = name;
  document.form1.p_answers.value = answers;
  document.form1.submit();

  //farwindow.window.focus();
  //document.form1.target = window.self.name;
}


// 다른 설문 이동
function changeTabpage(p_gubun) {

    if (p_gubun == "TARGET") {
      document.form1.action = "/servlet/controller.research.SulmunTargetResultServlet";

    } else if (p_gubun == "COMMON") {
      document.form1.action = "/servlet/controller.research.SulmunCommonResultServlet";

    } else if (p_gubun == "REGIST") {
          document.form1.action = "/servlet/controller.research.SulmunRegistResultServlet";

    }

    document.form1.target = "_self";
    document.form1.p_action.value = 'change';
    document.form1.p_gubun.value = p_gubun;
    document.form1.s_grcode.value = "";
    document.form1.p_process.value = 'SulmunResultPage';
    document.form1.submit();
}

function setSubj(p_subj) {
  var v_test;
  v_test = getArrayTest(document.form1.all.p_subj.length);
  if (v_test == -1) {
    document.form1.p_subj.value = p_subj;
  } else if (v_test == 0) {
  } else {
    document.form1.p_subj.options[document.form1.p_subj.selectedIndex].value = p_subj;
  }
}
function getArrayTest(p_object) {
  var cnt;
  if (typeof(p_object) == 'undefined') {
    cnt = -1;
  } else {
    if (typeof(p_object) == 'number') {
        cnt = p_object;
    } else {
      cnt = -1;
    }
  }
  return cnt;
}


// 엑셀결과분석출력
function goDetailExcel(p_action) {
    if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (p_action=="go") {
        if (document.form1.s_grseq.value=="----"){
            alert("교육차수를 선택하세요");
            return;
        }
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        if (document.form1.s_subjseq.value=="----"){
            alert("차수를 선택하세요");
            return;
        }
        if (document.form1.s_sulpapernum.value=='0'){
            alert("설문지를 선택하세요");
            return;
        }
// top.ftop.setPam();
    }

  window.self.name = "SulmunResultPage";
  farwindow = open_window("openDetailExcel","","100","100","700","400","yes","yes","yes","yes","yes");
  document.form1.p_action.value  = 'go';
  document.form1.target = "openDetailExcel";
  document.form1.p_process.value = 'SulmunDetailResultExcelPage';
  document.form1.p_sulpapernum.value = document.form1.s_sulpapernum.options[document.form1.s_sulpapernum.selectedIndex].value;
  document.form1.p_grcode.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
  document.form1.p_subj.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].value;
  document.form1.p_gyear.value = document.form1.s_gyear.options[document.form1.s_gyear.selectedIndex].value;
  document.form1.submit();
  document.form1.target = window.self.name;
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/research/r_title05.gif" ></td>
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
                <td class="<%=v_tab_color1%>_butt_middle">과정설문</td>
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
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('COMMON')" class="c">일반설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('TARGET')" class="c">대상자설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('REGIST')" class="c">가입경로</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>

          <td>&nbsp;</td>
        </tr>
      </table>
      <table cellspacing="0" cellpadding="0" class="table_out">
        <tr>
          <td bgcolor="#636563">
            <table cellspacing="1" cellpadding="0" class="s_table">
              <tr>
                <td bgcolor="#FFFFFF" align="center" valign="top">
                  <br>
                  <!----------------- form 시작 ----------------->
                  <table cellspacing="0" cellpadding="1" class="form_table_out">
                  <form name="form1" method="post" action="/servlet/controller.research.SulmunSubjResultServlet">
                    <input type="hidden" name="p_process"  value="">
                    <input type="hidden" name="p_action"   value="">
                    <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
                    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
                    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
                    <input type="hidden" name="p_subjsel"      value="<%=v_subj%>">
                    <input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>">
                    <input type="hidden" name="p_sulpapernum"       value="<%=v_sulpapernum%>">
                    <input type="hidden" name="p_company"      value="<%=v_company%>">
                    <input type="hidden" name="p_jikwi"      value="<%=v_jikwi%>">
                    <input type="hidden" name="p_jikun"      value="<%=v_jikun%>">
                    <input type="hidden" name="p_workplc"      value="<%=v_workplc%>">
                    <input type="hidden" name="p_gubun"       value="">

                    <input type="hidden" name="p_userid"       value="">
                    <input type="hidden" name="p_name"       value="">
                    <input type="hidden" name="p_answers"       value="">
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
                                  <td height=25> <font color="red">★</font>
                                    <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                                    <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                                    &nbsp;<font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, false)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                                    <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                                    &nbsp;<%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                                    &nbsp;<%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                                  </td>
                                </tr>
                                <tr>
                                  <td height=25>
                                   <font color="red">★</font>  <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                   &nbsp;<font color="red">★</font> <%= SelectSubjBean.getSubjseq(box, true, false)%>
                                   &nbsp;<font color="red">★</font> 설문지<%= SulmunSubjPaperBean.getSulPaperSelect (v_grcode, v_gyear, v_subj, "s_sulpapernum", v_sulpapernum, "", v_subjseq)%>
                                   &nbsp; <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                  </table>
                  <br>
                  <!--<table cellspacing="0" cellpadding="1" class="form_table_out">
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
                                  <td width="19%">
                      회사 <%= SelectCompBean.getCompany(box, true, true)%>&nbsp;<% //-- getCompany(RequestBox, isChange, isALL) ---%>
                      직위 <%= SelectCompBean.getJikwi(box, false, true)%>&nbsp;<% //-- getJikwi(RequestBox, isChange, isALL) ---%>
                      <!--직군 <%= SelectCompBean.getJikun(box, false, true)%>&nbsp;
                      근무지 <%= SelectCompBean.getWorkplc(box, false, true)%>&nbsp;
                                  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>-->
                                  <!--</td>
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
                  </table>-->
                  </form>
                  <!----------------- form 끝 ----------------->
                  <br>
                <table width="97%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
                    <td  class=sub_title>개인별 조회</td>
                  </tr>
                  <tr>
                    <td height="3"></td>
                  </tr>
                </table>
                  <table cellspacing="1" cellpadding="5" class="table_out">
                    <tr>
                      <td colspan="17" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td width="6%" class="table_title">NO</td>
                      <td class="table_title">회원분류</td>
                      <td class="table_title">ID</td>
                      <td class="table_title">성명</td>
<!--
                      <td class="table_title">부서</td>
                      <td class="table_title">직위</td>
-->
                      <td class="table_title" width='7%'>과정<br>만족도</td>
                      <td class="table_title" width='7%'>내용<br>이해도</td>
                      <td class="table_title" width='7%'>과정<br>난이도</td>
                      <td class="table_title" width='7%'>업무<br>활용도</td>
                      <td class="table_title" width='7%'>질문<br>대응</td>
                      <td class="table_title" width='7%'>장애<br>대응</td>
                      <td class="table_title" width='7%'>강사<br>만족도</td>
                      <td class="table_title">응시날짜</td>
                    </tr>

                   <%
                    // 개인별

                    DataBox   data1 = null;
                    for(int i = 0; i < list1.size(); i++) {
                        data1= (DataBox)list1.get(i);
                   %>
                  <tr>
                      <td class="table_01"><%= i+1 %></td>
                      <td class="table_02_1" align="center"><%//=data1.getString("d_compnm")%>일반회원</td>
                      <td class="table_02_1" align="center">
                      <a href="javascript:UserPage('ALL', 'ALL', '<%=v_sulpapernum%>', '<%=data1.getString("d_name")%>', '<%=data1.getString("d_userid")%>', '<%=data1.getString("d_answers")%>');" class="a"><%=data1.getString("d_userid")%></a>
                      </td>
                      <td class="table_02_1" align="center"><%=data1.getString("d_name")%></td>
<!--
                      <td class="table_02_1" align="center"><%=data1.getString("d_deptnam")%></td>
                      <td class="table_02_1" align="center"><%=data1.getString("d_jikwinm")%></td>
-->
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode1")%>점<br>( <%=data1.getDouble("distcode1avg")%>점 )</td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode2")%>점<br>( <%=data1.getDouble("distcode2avg")%>점 )</td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode3")%>점<br>( <%=data1.getDouble("distcode3avg")%>점 )</td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode4")%>점<br>( <%=data1.getDouble("distcode4avg")%>점 ) </td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode5")%>점<br>( <%=data1.getDouble("distcode5avg")%>점 )</td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode6")%>점<br>( <%=data1.getDouble("distcode6avg")%>점 )</td>
                      <td class="table_02_1" align="center">총<%=data1.getInt("d_distcode7")%>점<br>( <%=data1.getDouble("distcode7avg")%>점 )</td>
                      <td class="table_02_1" align="center"><%=FormatDate.getFormatDate(data1.getString("d_ldate"),"yyyy/MM/dd")%></td>
                  </tr>

                  <%
                    }
                  %>
                  </table><br>

                  <!----------------- 엑셀출력 버튼 시작 ----------------->
                  <table width="97%" cellpadding="0" cellspacing="0" class="table1">
                    <tr>
                      <td align="right"><!--<a href="javascript:goDetailExcel('go')" class="c"><img src="/images/admin/research/damun_excel.gif" border="0"></a>-->
                      &nbsp;&nbsp;<a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
                    </tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 엑셀출력 버튼 끝 ----------------->
                  <!----------------- 설문분석 시작 ----------------->
                  <table cellspacing="1" cellpadding="0" class="box_table_out">
                    <tr>
                     <td colspan="5" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td colspan="5" class="table_title_02">총응답자수 : <%=v_replycount%>명 / 전체수강자 : <%=v_studentcount%>명 / 설문응시율 : <%=df.format(v_replyrate)%>%</td>
                    </tr>
<%
    SulmunQuestionExampleData data    = null;
    SulmunExampleData         subdata = null;

    int k = 0;
    int l = 0;
    for (int i=0; i < list.size(); i++) {
        data = (SulmunQuestionExampleData)list.get(i);
        if (data.getSultype().equals(SulmunSubjBean.OBJECT_QUESTION) || data.getSultype().equals(SulmunSubjBean.MULTI_QUESTION)) { %>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%>
                      </td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%              }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {

%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%        int m = 0;   //out.println(data.getSubjectAnswer().size());//out.println(v_replycount);
            for (int j=0; j < data.getSubjectAnswer().size(); j++) {
                if(((data.getSubjectAnswer().size() / v_replycount)*m + (k+1)) == (j+1)) {
            //  if((m + (k+1)) == (j+1)) {
                    if(m < v_replycount) m++;
                //  if(m < data.getSubjectAnswer().size()) m++;
%>
                    <tr>
                      <td width="7%" class="table_01"></td>
                      <td  colspan="4" style="padding-left=6" class="table_02_2"><%=(String)data.getSubjectAnswer().get(j)%></td>
                    </tr>

<%           }
           }
           k++;
       } else if (data.getSultype().equals(SulmunSubjBean.COMPLEX_QUESTION)) {

%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="4" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) { %>
                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }
           }
%>
<%       int n = 0;
            int etc = 0;
            for (int j=0; j < data.getComplexAnswer().size(); j++) {
                if(((data.getComplexAnswer().size() / v_replycount)*n + (l+1)) == (j+1)) {
                    if(n < v_replycount) n++;
                        if( !((String)data.getComplexAnswer().get(j)).equals("")) {
                            etc++;
%>
                    <tr>
                      <td width="7%" class="table_01"><%if(etc==1){%>기타<%}%></td>
                      <td  colspan="4" style="padding-left=6" class="table_02_2"><%=(String)data.getComplexAnswer().get(j)%></td>
                    </tr>
<%
                    }
                }
           }
           l++;
       } else if (data.getSultype().equals(SulmunSubjBean.FSCALE_QUESTION)) {

              double d = 0;
              int person = 0;
              double v_point = 0;

           for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

                    d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
                    person += subdata.getReplycnt();
                }
            }

        v_point = d / person;
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%

           for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }
            }
       } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {

              double d = 0;
              int person = 0;
              double v_point = 0;

           for (int j=1; j <= data.size(); j++) {
                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {

                    d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
                    person += subdata.getReplycnt();
                }
            }

        v_point = d / person;
%>
                    <tr>
                      <td class="table_title_01">문제<%=i+1%></td>
                      <td colspan="3" style="padding-left:4px" class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                      <td width="7%" class="table_01"><%if(v_point >=0){%><%=df.format(v_point)%>점<%}%></td>
                    </tr>
<%

           for (int j=1; j <= data.size(); j++) {

                subdata  = (SulmunExampleData)data.get(j);
                if (subdata != null) {
%>

                    <tr>
                      <td width="7%" class="table_01"><%=subdata.getSelnum()%></td>
                      <td width="29%" style="padding-left=6" class="table_02_2"><%=subdata.getSeltext()%></td>
                      <td  width="11%" class="table_02_1"><%=subdata.getReplycnt()%>명</td>
                      <td width="11%" class="table_02_1"><%=subdata.getReplyrate()%>%</td>
                      <td width="39%" class="table_02_2">
                      <table cellspacing="0" cellpadding="0" class="s_table">
                        <tr>
<%                  if ((int)subdata.getReplyrate()==0) { %>
                          <td>&nbsp;</td>
<%                  } else { %>
                          <td width="<%=(int)subdata.getReplyrate()%>%" class="graph">&nbsp;</td>
<%                  } %>
                          <td style="padding-left:5px">&nbsp;</td>
                        </tr>
                        </table>
                      </td>
                    </tr>
<%           }
            }
       }
    } %>
                  </table>
                  <br>
                  <!----------------- 설문분석 끝 ----------------->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>

</table>
</body>
</html>