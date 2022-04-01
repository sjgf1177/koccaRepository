<%
/**
 * file name : za_SulmunQuestion_L.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 설문 문제관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
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
    int v_classdepth = conf.getInt("subj.class.depth");

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");
    String  v_gubun        = box.getStringDefault("p_gubun",SulmunQuestionBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육주관
    String  v_distcode    = box.getString("s_distcode");        //문제분류

    String  ss_grcode    = box.getString("s_grcode");        //교육주관

	String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getString("s_subjcourse");

    String  ss_distcode    = box.getStringDefault("s_distcode","ALL");

    String  s_gadmin = box.getSession("gadmin");
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
<title>설문문제관리-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style_research.css" type="text/css">
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {
  document.form2.p_process.value = 'QuestionListPage';
  document.form2.p_gubun.value = p_gubun;
  document.form2.s_grcode.value = "";
  document.form2.s_distcode.value = "All";
  document.form2.submit();
}
function whenSelection(p_action) {

    var v_grcode = "ALL";
	var v_gubun, v_subj, v_distcode, v_upperclass, v_middleclass, v_lowerclass;
	if(!v_gubun=="CONTENTS")  v_grcode        = document.form2.s_grcode.options[document.form2.s_grcode.selectedIndex].value;

	v_gubun       = document.form2.p_gubun.value;
    v_distcode    = document.form2.s_distcode.options[document.form2.s_distcode.selectedIndex].value;

    v_subj        = document.form2.s_subjcourse.value;
    v_upperclass  = document.form2.s_upperclass.value;
    v_middleclass = document.form2.s_middleclass.value;
    v_lowerclass  = document.form2.s_lowerclass.value;

	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (p_action=="go" && v_gubun=="SUBJ") {
        if (v_subj=="ALL"){
            alert("과정을 선택하세요");
            return;
        }
    }
    if (p_action=="go") {
     document.form2.p_process.value = 'QuestionListPage';
     document.form2.p_action.value  = p_action;
	 document.form2.submit();		
	  top.ftop.setPam();
	} else {
     document.form2.p_process.value = 'QuestionListPage';
     document.form2.p_action.value  = p_action;
     document.form2.p_grcode.value  = v_grcode;
  document.form2.s_distcode.value = "All";
	 document.form2.submit();	
	}
}
function detailSelect(p_sulnum) {
       window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunQuestionServlet";
		document.form2.p_process.value = "QuestionDetailUpdatePage";
		document.form2.p_sulnum.value = p_sulnum;
		document.form2.p_subj.value = document.form2.s_subjcourse.value;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function insert() {

	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
    if (document.form2.p_gubun.value=="SUBJ") {
        if (document.form2.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
        }
    }
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunQuestionServlet";
		document.form2.p_process.value = "QuestionDetailInsertPage";
		document.form2.p_subj.value = document.form2.s_subjcourse.value;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function insertContents() {

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunQuestionServlet";
		document.form2.p_process.value = "QuestionDetailInsertPage";
		document.form2.p_subj.value = document.form2.s_subjcourse.value;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function scaleview() {
	if (document.form2.s_grcode.value == '----') {
			alert("교육주관을 선택하세요.");
			return ;
	}
        window.self.name = "winScaleView";
        farwindow = window.open("", "openWinScale", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 562, top=0, left=0");
        document.form2.target = "openWinScale";
		document.form2.action = "/servlet/controller.research.ScaleServlet";
		document.form2.p_process.value = "ScaleListPage";
		document.form2.p_scalecode.value = 0;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function ReloadPage(p_action) {
  document.form2.p_process.value = 'QuestionListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunQuestionServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
    <input type="hidden" name="p_action"  value="<%=v_action%>">
    <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
    <input type="hidden" name="p_distcode"  value="<%=v_distcode%>">
    <input type="hidden" name="p_scalecode"  value="">
    <input type="hidden" name="p_subj"  value="">
    <input type="hidden" name="p_sulnum"  value="">
<%  if (!v_gubun.equals("SUBJ")) {                        %>
    <input type="hidden" name="s_subjcourse"  value="">
    <input type="hidden" name="s_upperclass" value="">
    <input type="hidden" name="s_middleclass" value="">
    <input type="hidden" name="s_lowerclass" value="">
<%  } else if (v_classdepth > 1) {                        %>
    <input type="hidden" name="s_lowerclass" value="">
<%  } else if (v_classdepth > 0) {                        %>
    <input type="hidden" name="s_lowerclass" value="">
    <input type="hidden" name="s_middleclass" value="">
<%  }                                                     %>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/images/admin/research/title_bg.gif"><img src="/images/admin/research/research_title1.gif" width="259" height="36" border="0">설문 문제관리</td>
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
<%  if(!v_gubun.equals("CONTENTS")){  %>
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
<% } else { %>
                                   <input type="hidden" name="s_grcode" value="ALL">  
<% } %>
<%  if (v_gubun.equals("SUBJ")) { %>
                                  <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
				  <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				  <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				  <%= SelectSubjBean.getSubj(box, false, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
<% }                             %>

<%  if(v_gubun.equals("CONTENTS")){  %>
								  설문분류<%=CodeConfigBean.getCodeGubunSelect (SulmunQuestionBean.DIST_CODE, "", 0, "s_distcode", ss_distcode, "javascript:whenSelection('change')", 2)%>
<% } else{   %>
								  설문분류<%=CodeConfigBean.getCodeGubunSelect (SulmunQuestionBean.DIST_CODE, "", 1, "s_distcode", ss_distcode, "javascript:whenSelection('change')", 2)%>
<% } %>          
								  <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
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
                  <!----------------- 추가버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr>
                      <td height="3"></td>
                    </tr>
					<tr>
                      <td align="left"><a href="javascript:scaleview()" class="c"><font color="black">설문척도</font></a></td>
                    </tr>
					<tr>
<%  if(v_gubun.equals("CONTENTS")){  %>
                      <td align="right"><a href="javascript:insertContents()" class="c"><img src="/images/admin/research/addition1_butt.gif" width="37" height="18" border="0"></a></td>
<% } else{   %>
                      <td align="right"><a href="javascript:insert()" class="c"><img src="/images/admin/research/addition1_butt.gif" width="37" height="18" border="0"></a></td>
<% }  %>
					</tr>
                    <tr>
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 공통설문 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="box_table_out">
                    <tr>
                      <td colspan="4" class="table_top_line"></td>
                    </tr>

<%  if(v_gubun.equals("CONTENTS")){  %>
                    <tr>
                      <td width="5%" class="table_title_01">NO</td>
                      <td class="table_title_01">문제</td>
                      <td class="table_title_01">평가분류</td>
                    </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("QuestionList");
    SulmunQuestionData data = null;
    for (int i=0; i<list.size(); i++) {
        data  = (SulmunQuestionData)list.get(i); %>
                    <tr>
                      <td class="table_01"><%=String.valueOf(data.getSulnum())%></td>
                      <td class="table_02_2"><a href="javascript:detailSelect('<%=String.valueOf(data.getSulnum())%>')" class="e"><%=data.getSultext()%></a></td>
                      <td class="table_02_1"><%=data.getDistcodenm()%></td>
                    </tr>
<%  } %>

<% } else{   %>
                    <tr>
                      <td width="5%" class="table_title_01">NO</td>
                      <td class="table_title_01">문제</td>
                      <td class="table_title_01">설문분류</td>
                      <td class="table_title_01">문제분류</td>
                    </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("QuestionList");
    SulmunQuestionData data = null;
    for (int i=0; i<list.size(); i++) {
        data  = (SulmunQuestionData)list.get(i); %>
                    <tr>
                      <td class="table_01"><%=String.valueOf(data.getSulnum())%></td>
                      <td class="table_02_2"><a href="javascript:detailSelect('<%=String.valueOf(data.getSulnum())%>')" class="e"><%=data.getSultext()%></a></td>
                      <td class="table_02_1"><%=data.getDistcodenm()%></td>
                      <td class="table_02_1"><%=data.getSultypenm()%></td>
                    </tr>
<%  } %>
<%  } %>
                  </table>
                  <!----------------- 공통설문 끝 ----------------->
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
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
