<%
/**
 * file name : za_SulmunResultS_L.jsp
 * date      : 2003/08/26
 * programmer:
 * function  : 주관식 설문결과조회
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

//    String  v_subj        = box.getStringDefault("p_subj",  SulmunBean.DEFAULT_SUBJ);
    String  v_subj        = box.getString("p_subj");
    String  v_gyear       = box.getString("p_gyear");
    String  v_grseq       = box.getString("p_grseq");

    int     v_sulpapernum = box.getInt("p_sulpapernum");

    String  v_grpcomp     = box.getString("p_grpcomp");
    String  v_gpm         = box.getString("p_gpm");
    String  v_dept        = box.getString("p_dept");
    String  v_jikun       = box.getString("p_jikun");
    String  v_jikup       = box.getString("p_jikup");

    int     v_replycount  = box.getInt("p_replycount");
    int     v_studentcount= box.getInt("p_studentcount");
    double  v_replyrate   = 0;
    if (v_studentcount != 0) {
        v_replyrate = (double)Math.round((double)v_replycount/v_studentcount*100*100)/100;
    }
    
    String  v_f_gubun   = "";
    if (v_subj.equals("COMMON") || v_subj.equals("WHOLE")) {
      v_f_gubun = v_subj;
    } else {
      v_f_gubun = "SUBJ";
    }
    
	String  v_grcode = "";
	if (v_subj.equals("WHOLE")) {
      v_grcode      = SulmunBean.DEFAULT_GRCODE;
	} else {
      v_grcode      = box.getStringDefault("p_grcode",SulmunBean.DEFAULT_GRCODE);
	}
    
    String  v_uclass    = box.getString("p_uclass");
    String  v_sulyear   = box.getString("p_sulyear");
    
	  String  s_gadmin = box.getSession("gadmin");
%>
<html>
<head>
<title>설문분석-주관식</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<script language="JavaScript">
<!--
function whenSelection(p_action) {
  document.form2.target = "_self";
  document.form2.p_action.value  = p_action;
  document.form2.p_process.value = 'SResultPage';
  document.form2.submit();
}
function goExcel() {
  window.self.name = "SResultExcelPage";     
  open_window("openExcel","","100","100","700","400","yes","yes","yes","yes","yes");  
  document.form2.p_action.value  = 'go';
  document.form2.target = "openExcel";               
  document.form2.p_process.value = 'SResultExcelPage';   
  document.form2.submit();
}
function changeTabpage(p_subj) {
  document.form2.target = "_self";
  document.form2.p_process.value = 'SResultPage';
  document.form2.p_action.value  = 'change';
  setSubj(p_subj);
  document.form2.submit();
}
function setSubj(p_subj) {
  var v_test; 
  v_test = getArrayTest(document.form2.all.p_subj.length);
  if (v_test == -1) {
    document.form2.p_subj.value = p_subj;
  } else if (v_test == 0) {
  } else {
    document.form2.p_subj.options[document.form2.p_subj.selectedIndex].value = p_subj;
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
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
<%    String v_title_img = ""; 
      if (v_f_gubun.equals("COMMON")) {
        v_title_img = "7";
      } else if (v_f_gubun.equals("SUBJ")) {
        v_title_img = "9";
      } else if (v_f_gubun.equals("WHOLE")) {
        v_title_img = "11";
      }  %>       
          <td background="/images/admin/research/title_bg.gif"><img src="/images/admin/research/research_title7.gif" width="259" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <br>
<%  String v_tab_color = "";
    if (v_f_gubun.equals("COMMON")) v_tab_color = "blue"; else v_tab_color = "black"; %>
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color%>_butt_left"></td>
                <td class="<%=v_tab_color%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_middle"><a href="javascript:changeTabpage('COMMON')" class="c">공통설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
<%  if (v_f_gubun.equals("SUBJ")) v_tab_color = "blue"; else v_tab_color = "black"; %>
          <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color%>_butt_left"></td>
                <td class="<%=v_tab_color%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_middle"><a href="javascript:changeTabpage('SUBJ')" class="c">과정설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
<%  if (v_f_gubun.equals("WHOLE")) v_tab_color = "blue"; else v_tab_color = "black"; %>
          <td width="85">
<%  if (s_gadmin.startsWith("A")) { %>
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color%>_butt_left"></td>
                <td class="<%=v_tab_color%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_middle"><a href="javascript:changeTabpage('WHOLE')" class="c">전체설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color%>_butt_bottom"></td>
              </tr>
            </table>
<%  } %>
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
                  <table cellspacing="0" cellpadding="3" class="form_table_out">
                  <form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
                    <input type="hidden" name="p_process"  value="">
                    <input type="hidden" name="p_action"   value="">
<%  if (!v_f_gubun.equals("SUBJ")) { %>
                    <input type="hidden" name="p_subj"     value="<%=v_subj%>">
<%  }  %>
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
                                  <td>
<%  Vector vec = null;
    SelectParam param = new SelectParam("start", "whenSelection('change')", false, 35, box.getHttpSession());
    if (v_f_gubun.equals("COMMON")) { %>
                                    교육그룹 <% param.setSelectname("p_grcode"); param.setAll(true); %><% vec=SelectionUtil2.getGrcodeR_I(v_grcode, param); v_grcode=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    년도     <% param.setSelectname("p_gyear");  param.setAll(false); %><% vec=SelectionUtil2.getGyearR_I(v_grcode, v_gyear, param); v_gyear=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    교육차수 <% param.setSelectname("p_grseq"); param.setAll(true); %><% vec=SelectionUtil2.getGrseqR_I(v_grcode, v_gyear, v_grseq, param); v_grseq=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    설문지명 <% param.setSelectname("p_sulpapernum"); param.setOnchange(""); param.setAll(false); %><%=SelectionUtil.getSulpapernum(v_grcode, v_gyear, v_grseq, v_subj, "", v_sulpapernum, param)%>
<%  } else if (v_f_gubun.equals("SUBJ")) { %>
                                    교육그룹 <% param.setSelectname("p_grcode"); param.setAll(true); %><% vec=SelectionUtil2.getGrcodeR_I(v_grcode, param); v_grcode=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    년도     <% param.setSelectname("p_gyear");  param.setAll(false); %><% vec=SelectionUtil2.getGyearR_I(v_grcode, v_gyear, param); v_gyear=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    교육차수 <% param.setSelectname("p_grseq"); param.setAll(true); %><% vec=SelectionUtil2.getGrseqR_I(v_grcode, v_gyear, v_grseq, param); v_grseq=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    과정분류 <% param.setSelectname("p_uclass"); %><% vec=SelectionUtil2.getUpperClassR_I(v_grcode, v_gyear, v_grseq, v_uclass, param); v_uclass=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    과정명   <% param.setSelectname("p_subj");  param.setAll(false); %><% vec=SelectionUtil2.getSulmunSubjR(v_grcode, v_gyear, v_grseq, v_uclass, v_subj, param); v_subj=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    설문지명 <% param.setSelectname("p_sulpapernum"); param.setOnchange(""); param.setAll(false); %><%=SelectionUtil.getSulpapernum(v_grcode, v_gyear, v_grseq, v_subj, "", v_sulpapernum, param)%>
<%  } else if (v_f_gubun.equals("WHOLE")) {  %>
                    <input type="hidden" name="p_grcode"   value="<%=v_grcode%>">
                                    년도     <% param.setSelectname("p_sulyear");%><% vec=SelectionUtil2.getWholeSulyearR(v_grcode, v_sulyear, param); v_sulyear=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                    설문지명 <% param.setSelectname("p_sulpapernum"); param.setOnchange(""); param.setAll(false); %><%=SelectionUtil.getSulpapernum(v_grcode, "", "", v_subj, v_sulyear, v_sulpapernum, param)%>
<%  } %>
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
                              <table cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                <tr>
                                  <td width="19%">
                                  회사:<% param.setSelectname("p_grpcomp"); param.setOnchange("whenSelection('change')");  param.setAll(true); vec=SelectionUtil2.getGrcompR_I(v_grcode, "ALL", "ALL", "ALL", "ALL", "ALL", v_grpcomp, param); v_grpcomp=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                  사업부:<% param.setSelectname("p_gpm"); param.setOnchange("whenSelection('change')");  vec=SelectionUtil2.getSelTextR_I(v_grpcomp, SelectionUtil.GPM, v_gpm, param); v_gpm=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                  부서:<% param.setSelectname("p_dept");  param.setOnchange(""); vec=SelectionUtil2.getSelDeptR_I(v_grpcomp, SelectionUtil.GPM, v_gpm, v_dept, param); v_dept=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                  직급:<% param.setSelectname("p_jikun"); vec=SelectionUtil2.getSelTextR_I(v_grpcomp, SelectionUtil.JIKUN, v_jikun, param); v_jikun=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                  직군:<% param.setSelectname("p_jikup"); vec=SelectionUtil2.getSelTextR_I(v_grpcomp, SelectionUtil.JIKUP, v_jikup, param); v_jikup=(String)vec.get(1); %><%=(String)vec.get(0)%>
                                  <a href="javascript:whenSelection('go')" class="c"><img src="/images/admin/research/go1_butt.gif" width="33" height="20" border="0"></a>
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
                  </form>
                  </table>
                  <!----------------- form 끝 ----------------->
                  <br>
                  <br>
                  <table cellspacing="0" cellpadding="0" class="table1">
                    <tr>
<%    String v_text = ""; 
      if (v_f_gubun.equals("COMMON")) {
        v_text = "공통설문";
      } else if (v_f_gubun.equals("SUBJ")) {
        v_text = "과정설문";
      } else if (v_f_gubun.equals("WHOLE")) {
        v_text = "전체설문";
      }  %>       
                      <td height="2"><img src="/images/admin/research/icon2.gif" width="14" height="10" border="0"><%=v_text%></td>
                    </tr>
                  </table>
                  <!----------------- 엑셀출력 버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr>
                      <td align="right"><a href="javascript:goExcel()" class="c"><img src="/images/admin/research/excel_print_butt.gif" width="58" height="18" border="0"></a></td>
                    </tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 엑셀출력 버튼 끝 ----------------->
                  <!----------------- 설문분석 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="box_table_out">
                    <tr>
                      <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr>
                      <td colspan="4" class="table_title_02">총응답자수 : <%=v_replycount%>명 / 전체수강자 : <%=v_studentcount%>명 / 설문응시율 : <%=v_replyrate%>% / 설문지번호 : <%=v_sulpapernum%></td>
                    </tr>
<%  ArrayList    list = (ArrayList)request.getAttribute("SResultList");
    QuestionSubjectAnswerData  data    = null;
    SubjectAnswerData          subdata = null;
    for (int i=0; i < list.size(); i++) {
        data = (QuestionSubjectAnswerData)list.get(i);
        if (data != null) {  %>
                    <tr>
                      <td colspan="3" class="table_title_01">문제<%=data.getSulnum()%></td>
                      <td class="table_title_02"><%="["+data.getDistcodenm()+"] [" + data.getSultypenm() + "] " + data.getSultext()%></td>
                    </tr>
<%          for (int j=0; j < data.size(); j++) {
                subdata  = (SubjectAnswerData)data.get(j); %>
                    <tr>
                      <td width="7%" class="table_01"><%=(j+1)%></td>
                      <td width="10%" class="table_02_1"><%=subdata.getUserid()%></td>
                      <td width="10%" class="table_02_1"><%=subdata.getName()%></td>
                      <td class="table_02_2"><%=subdata.getAnstext()%></td>
                    </tr>
<%          }
        }
    } %>
                  </table>
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

  <tr><td><%@ include file = "/jsp/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>
