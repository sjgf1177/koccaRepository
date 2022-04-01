<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunSubjQuestion_L.java
//  3. 개      요: 과정설문 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
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

	String v_tem_grcode = box.getSession("tem_grcode");		// 세션 그룹구분값    

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");
    String  v_gubun        = box.getStringDefault("p_gubun",SulmunSubjBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_distcode    = box.getString("s_distcode");        //문제분류

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹

	String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getString("s_subjcourse");

    String  ss_distcode    = box.getStringDefault("s_distcode","ALL");

    String  s_gadmin = box.getSession("gadmin");
    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";
%>
<html>
<head>
<title>설문문제관리-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {

	if (p_gubun == "TARGET") {
      document.form1.action = "/servlet/controller.research.SulmunTargetQuestionServlet";

	} else if (p_gubun == "COMMON") {
      document.form1.action = "/servlet/controller.research.SulmunCommonQuestionServlet";
	
	} else if (p_gubun == "REGIST") {
      document.form1.p_action.value = "go";
      document.form1.s_grcode.value = "<%=v_tem_grcode%>";            	
      document.form1.action = "/servlet/controller.research.SulmunRegistQuestionServlet";

	} else if (p_gubun == "ALL") {
      document.form1.action = "/servlet/controller.research.SulmunAllQuestionServlet";    	
	} 
	  document.form1.p_process.value = "SulmunQuestionListPage";
	  document.form1.p_gubun.value = p_gubun;
      document.form1.s_grcode.value = "";
      document.form1.s_distcode.value = "All";
      document.form1.submit();     
}
function whenSelection(p_action) {

    var v_grcode, v_gubun, v_subj, v_distcode, v_upperclass, v_middleclass, v_lowerclass;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
	v_gubun       = document.form1.p_gubun.value;
    v_distcode    = document.form1.s_distcode.options[document.form1.s_distcode.selectedIndex].value;

    v_subj        = document.form1.s_subjcourse.value;
    v_upperclass  = document.form1.s_upperclass.value;
    v_middleclass = document.form1.s_middleclass.value;
    v_lowerclass  = document.form1.s_lowerclass.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_subj=="ALL"||v_subj=="----"){
            alert("과정을 선택하세요.");
            return;
        }
    }
    if (p_action=="go") {
     document.form1.p_process.value = 'SulmunQuestionListPage';
     document.form1.p_action.value  = p_action;
	 document.form1.submit();		
	  top.ftop.setPam();
	} else {
     document.form1.p_process.value = 'SulmunQuestionListPage';
     document.form1.p_action.value  = p_action;
     document.form1.p_grcode.value  = v_grcode;
     document.form1.s_distcode.value = "All";
	 document.form1.submit();	
	}
}
function detailSelect(p_sulnum) {
       window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
		document.form1.p_process.value = "SulmunQuestionUpdatePage";
		document.form1.p_sulnum.value = p_sulnum;
		document.form1.p_subj.value = document.form1.s_subjcourse.value;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}
function insert() {

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
		document.form1.p_process.value = "SulmunQuestionInsertPage";
		document.form1.p_subj.value = document.form1.s_subjcourse.value;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}
function insertPool() {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="ALL"){
            alert("과정을 선택하세요");
            return;
    }
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = "SulmunQuestionPoolPage";
    document.form1.p_action.value = "go";
	document.form1.p_subj.value = document.form1.s_subjcourse.value;
    document.form1.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}
function scaleview() {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
        window.self.name = "winScaleView";
        farwindow = window.open("", "openWinScale", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 562, top=0, left=0");
        document.form1.target = "openWinScale";
		document.form1.action = "/servlet/controller.research.ScaleServlet";
		document.form1.p_process.value = "ScaleListPage";
		document.form1.p_scalecode.value = 0;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}
function ReloadPage(p_action) {
  document.form1.p_process.value = 'SulmunQuestionListPage';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.research.SulmunSubjQuestionServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
    <input type="hidden" name="p_action"  value="<%=v_action%>">
    <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
    <input type="hidden" name="p_distcode"  value="<%=v_distcode%>">
    <input type="hidden" name="p_scalecode"  value="">
    <input type="hidden" name="p_subj"  value="">
    <input type="hidden" name="p_sulnum"  value="">
<%  if (v_classdepth > 1) {                        %>
    <input type="hidden" name="s_lowerclass" value="">
<%  } else if (v_classdepth > 0) {                        %>
    <input type="hidden" name="s_lowerclass" value="">
    <input type="hidden" name="s_middleclass" value="">
<%  }                                                     %>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title01.gif" ></td>
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
          <!--td width="2"></td>   
		  <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_left"></td>
                <td class="<%=v_tab_color5%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_middle"><a href="javascript:changeTabpage('ALL')" class="c">전체설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_bottom"></td>
              </tr>
            </table>
          </td-->   
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

                    <tr>
                      <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="0" class="form_table_bg">
                          <tr>
                            <td height="7"></td>
                          </tr>
                          <tr>
                            <td align="center">
                              <table border=0 width="99%" cellspacing="0" cellpadding="0" class="form_table">
                                <tr>
                                  <td align=left> <font color="red">★</font> 
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
								   &nbsp;
                                   <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                				   <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				                   <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
								   </td>
								   <td width="10%" >&nbsp;</td>
								   </tr>
								   <tr>
								   <td  valign=middle>&nbsp;&nbsp;&nbsp;
								   &nbsp; <font color="red">★</font> 
				                   <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                                   &nbsp; 
								  설문분류<%=CodeConfigBean.getCodeGubunSelect (SulmunSubjBean.DIST_CODE, "", 1, "s_distcode", ss_distcode, "javascript:whenSelection('change')", 2)%>&nbsp;&nbsp;&nbsp;
								   <td width="10%" >&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
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
                    <table cellpadding="0" cellspacing="0" >
                      <tr> 
                        <td align="left"><a href="javascript:scaleview()" class="c"><img src="/images/admin/button/btn_sulmunchuckdo.gif" border="0"></a></td>
                        <td width=750></td>
                        <td align="right"><a href="javascript:insert()" class="c"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
                        <td width=8></td>
                        <td align="right"><a href="javascript:insertPool()" class="c"><img src="/images/admin/button/btn_poolplus.gif" border="0"></a></td>
                      </tr>
                      <tr>
                        <td height=8></td>
                        <td height=8></td>
                       <td height=8></td>
                      </tr>
                    </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 공통설문 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="table_out">
                    <tr>
                      <td colspan="4" class="table_top_line"></td>
                    </tr>

                    <tr>
                      <td width="5%" class="table_title">NO</td>
                      <td class="table_title">문제</td>
                      <td class="table_title">설문분류</td>
                      <td class="table_title">문제분류</td>
                    </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("SulmunQuestionList");
    DataBox dbox = null;
    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); %>
                    <tr>
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_sulnum"))%></td>
                      <td class="table_02_1"><a href="javascript:detailSelect('<%=String.valueOf(dbox.getInt("d_sulnum"))%>')" class="e"><%=dbox.getString("d_sultext")%></a></td>
                      <td class="table_02_1"><%=dbox.getString("d_distcodenm")%></td>
                      <td class="table_02_1"><%=dbox.getString("d_sultypenm")%></td>
                    </tr>
<%  } %>
                 </table>
				 <br>
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
