<%
/**
 * file name : za_SulmunQuestionList.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 설문 문항관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");

    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육주관
    String  v_gyear    = box.getStringDefault("s_gyear","2004"); 

    String  v_gubun        = box.getStringDefault("p_gubun",  SulmunPaperBean.DEFAULT_SUBJ);
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

    ArrayList    list = (ArrayList)request.getAttribute("SulmunPaperListPage");
    SulmunPaperData    data = null;
    SulmunPaperSubData subdata = null;
    
    String  s_gadmin = box.getSession("gadmin");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
    if (v_gubun.equals("SUBJ"))     v_tab_color1 = "blue"; 
    else if (v_gubun.equals("TARGET"))  v_tab_color2 = "blue"; 
    else if (v_gubun.equals("COMMON")) v_tab_color3 = "blue";
    else if (v_gubun.equals("CONTENTS")) v_tab_color4 = "blue";

%>

<html>
<head>
<title>설문지-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_research.css" type="text/css">
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {

	if(p_gubun == "CONTENTS") {
	    
	}
	document.form2.p_action.value = 'change';
	document.form2.p_gubun.value = p_gubun;
    document.form2.s_grcode.value = "";
	document.form2.p_process.value = 'SulmunPaperListPage';
	document.form2.submit();
}

function InsertPaperPage(p_subjnm) {
    if (document.form2.p_action.value=="go" && document.form2.p_gubun.value =="SUBJ") {
        if (document.form2.s_subjcourse.value =="ALL"){
            alert("과정을 선택하세요");
            return;
        }
		 top.ftop.setPam();
    }
    document.form2.p_process.value = 'SulmunPaperInsertPage';
    document.form2.p_subjnm.value = p_subjnm;
    document.form2.submit();
}

function UpdatePaperPage(p_sulpapernum, p_subjnm,p_subj) {
    if (document.form2.p_action.value=="go" && document.form2.p_gubun.value =="SUBJ") {
        if (document.form2.s_subjcourse.value =="ALL"){
            alert("과정을 선택하세요");
            return;
        }
		 top.ftop.setPam();
    }

	document.form2.p_subj.value = p_subj;
    document.form2.p_sulpapernum.value = p_sulpapernum;
    document.form2.p_process.value = 'SulmunPaperUpdatePage';
    document.form2.p_subjnm.value = p_subjnm;
    document.form2.submit();
}

function whenSelection(p_action) {
    if (p_action=="go" && document.form2.p_gubun.value =="SUBJ") {
        if (document.form2.s_subjcourse.value =="ALL"){
            alert("과정을 선택하세요");
            return;
        }
		 top.ftop.setPam();
    }
  document.form2.p_action.value  = p_action;
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.submit();
}

function PreviewPaper(p_grcode, p_subj, p_sulpapernum, p_sulpapername) {
  window.self.name = "winSulmunPreview";
  window.open("", "openSulmunPreview", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openSulmunPreview";
  document.form3.p_process.value = "SulumunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_sulpapernum.value= p_sulpapernum; 
  document.form3.p_sulpapername.value= p_sulpapername;
 
  document.form3.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunPaperServlet">
	<input type="hidden" name="p_process"     value="<%=v_process%>">
	<input type="hidden" name="p_action"      value="<%=v_action%>">
	<input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
	<input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
	<input type="hidden" name="p_sulpapernum" value="">
	<input type="hidden" name="p_subj"      value="">
	<input type="hidden" name="p_subjnm"      value="">
	<input type="hidden" name="p_gubun"       value="<%=v_gubun%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/images/admin/research/title_bg.gif"><img src="/images/admin/research/research_title<%=v_title_img%>.gif" width="259" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
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
                <td class="<%=v_tab_color1%>_butt_middle"><a href="javascript:changeTabpage('SUBJ')" class="c">과정설문</a></td>
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
          <td width="85">
<%  if (s_gadmin.startsWith("A")) { %>
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('CONTENTS')" class="c">콘텐츠평가</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
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
                  <table cellspacing="0" cellpadding="3" class="box_form_table_out">
                    <tr>
                      <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="box_form_table_bg">
                          <tr>
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td align="center">
                              <table width="99%" cellspacing="0" cellpadding="0" class="box_form_table">
                                <tr>
                                  <td>
<%  if(v_gubun.equals("CONTENTS")){  %>
                                   <input type="hidden" name="s_grcode" value="ALL">  
                                  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
<% } else if (v_gubun.equals("SUBJ")) { %>
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
			                       <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                                   <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                				   <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				                   <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				                   <%= SelectSubjBean.getSubj(box, false, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
<% } else {%>
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
								   <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                                  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
<% } %>
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
                  <br>
                  <!----------------- form 끝 ----------------->
                  <!----------------- 추가버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr> 
                      <td align="right"><a href="javascript:InsertPaperPage('')"><img src="/images/admin/research/addition1_butt.gif" width="37" height="18" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 설문지 관리-등록 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="box_table_out">

<% if (v_gubun.equals("SUBJ")) { %>
                    <tr> 
                      <td colspan="7" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%"  class="table_title_01">NO</td>
                      <td class="table_title_01" colspan="2">과정명</td>
                      <td width="5%"  class="table_title_01">NO</td>
                      <td width="10%" class="table_title_01">문제수</td>
                      <td width="30%" class="table_title_01">설문지명</td>
                      <td width="10%" class="table_title_01">기능</td>
                    </tr>
<%    
    } else if (v_gubun.equals("TARGET")) {
%>  
                    <tr> 
                      <td colspan="7" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%"  class="table_title_01">NO</td>
                      <td class="table_title_01">설문지명</td>
                      <td width="12%" class="table_title_01">문제수</td>
                      <td width="17%" class="table_title_01">설문기간</td>
                      <td width="10%" class="table_title_01">대상자</td>
                      <td width="10%" class="table_title_01">기능</td>
                    </tr>
<% } else if (v_gubun.equals("COMMON")) { %>
                    <tr> 
                      <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%" class="table_title_01">NO</td>
                      <td class="table_title_01">설문지명</td>
					  <td width="12%" class="table_title_01">문제수</td>
                      <td width="17%" class="table_title_01">설문기간</td>
                      <td width="10%" class="table_title_01">기능</td>
                    </tr>
<% } else if (v_gubun.equals("CONTENTS")) { %>
                    <tr> 
                      <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%" class="table_title_01">NO</td>
					  <td width="12%" class="table_title_01">문제수</td>
					  <td class="table_title_01">설문지명</td>
                      <td width="10%" class="table_title_01">기능</td>
                    </tr>
<%    for (int i=0; i<list.size(); i++) {
            data  = (SulmunPaperData)list.get(i); 
            for (int j=0; j<data.size(); j++) {
                subdata = data.get(j); %>
                    <tr> 
                      <td class="table_01"><%=String.valueOf(subdata.getSulpapernum())%></td>
                      <td class="table_02_1"><%=subdata.getTotcnt()%></td>
                      <td class="table_02_1"><a href="javascript:UpdatePaperPage('<%=String.valueOf(subdata.getSulpapernum())%>','','WHOLE')"><%=subdata.getSulpapername()%></a></td>
                      <td class="table_02_1">
                      <a href="javascript:PreviewPaper('<%=v_grcode%>','<%=data.getSubj()%>','<%=String.valueOf(subdata.getSulpapernum())%>','<%=subdata.getSulpapername()%>')"><img src="/images/admin/research/view1_butt.gif" width="58" height="18" border="0"></a>
                      </td>
                    </tr>
<%      }
        } 
%>
<% } %>
                  </table>
                  <!----------------- 설문지 관리-등록 끝 ----------------->
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
    </td>
  </tr>
</table>
</form>
<form name="form3" method="post" action="/servlet/controller.research.SulmunPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_sulpapernum" value="">
  <input type="hidden" name="p_sulpapername" value="">
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>


