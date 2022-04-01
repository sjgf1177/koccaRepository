<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunContentsQuestion_L.java
//  3. 개      요: 콘텐츠설문 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    String  v_gubun        = box.getStringDefault("p_gubun",SulmunContentsBean.DEFAULT_SUBJ);
    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_distcode    = box.getString("s_distcode");        //문제분류

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹

    String  ss_distcode    = box.getStringDefault("s_distcode","ALL");

    String  s_gadmin = box.getSession("gadmin");
    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "blue";
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
      document.form2.action = "/servlet/controller.research.SulmunTargetQuestionServlet";
	} else if (p_gubun == "COMMON") {
      document.form2.action = "/servlet/controller.research.SulmunCommonQuestionServlet";
	} else if (p_gubun == "SUBJ") {
      document.form2.action = "/servlet/controller.research.SulmunSubjQuestionServlet";
	} else if (p_gubun == "ALL") {
      document.form2.action = "/servlet/controller.research.SulmunAllQuestionServlet";      
	} else if (p_gubun == "CP") {
      document.form2.action = "/servlet/controller.research.SulmunCPQuestionServlet"; 
	} 
	//alert(document.form2.action);
	  document.form2.p_process.value = "SulmunQuestionListPage";
	  document.form2.p_gubun.value = p_gubun;
      document.form2.s_grcode.value = "";
      document.form2.s_distcode.value = "All";
      document.form2.submit();     
}
function whenSelection(p_action) {

    var v_grcode = "ALL";
	var v_gubun, v_subj, v_distcode, v_upperclass, v_middleclass, v_lowerclass;

	v_gubun       = document.form2.p_gubun.value;
    v_distcode    = document.form2.s_distcode.options[document.form2.s_distcode.selectedIndex].value;

    if (p_action=="go") {
     document.form2.p_process.value = 'SulmunQuestionListPage';
     document.form2.p_action.value  = p_action;
	 document.form2.submit();		
	 // top.ftop.setPam();
	} else {
     document.form2.p_process.value = 'SulmunQuestionListPage';
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
		document.form2.action = "/servlet/controller.research.SulmunContentsQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionUpdatePage";
		document.form2.p_sulnum.value = p_sulnum;
		document.form2.p_subj.value = document.form2.s_subjcourse.value;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function insertContents() {

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 767, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunContentsQuestionServlet";
		document.form2.p_process.value = "SulmunQuestionInsertPage";
		document.form2.p_subj.value = document.form2.s_subjcourse.value;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}
function scaleview() {
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
  document.form2.p_process.value = 'SulmunQuestionListPage';
  document.form2.p_action.value  = p_action;
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunContentsQuestionServlet">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_gubun"   value="<%=v_gubun%>">
    <input type="hidden" name="p_action"  value="<%=v_action%>">
    <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
    <input type="hidden" name="p_distcode"  value="<%=v_distcode%>">
    <input type="hidden" name="p_scalecode"  value="">
    <input type="hidden" name="p_subj"  value="">
    <input type="hidden" name="p_sulnum"  value="">
    <input type="hidden" name="s_subjcourse"  value="">
    <input type="hidden" name="s_upperclass" value="">
    <input type="hidden" name="s_middleclass" value="">
    <input type="hidden" name="s_lowerclass" value="">

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
                <td class="<%=v_tab_color1%>_butt_middle"><a href="javascript:changeTabpage('SUBJ')" class="c">과정설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
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
          <!--td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle">콘텐츠설문</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td-->          
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle">가입경로</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <!--td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_left"></td>
                <td class="<%=v_tab_color5%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_middle"><a href="javascript:changeTabpage('CP')" class="c">업체설문</a></td>
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
                              <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                                <tr>
                                  <td>
                                   <input type="hidden" name="s_grcode" value="ALL">  
								  설문분류<%=CodeConfigBean.getCodeGubunSelect (SulmunContentsBean.DIST_CODE, "", 0, "s_distcode", ss_distcode, "javascript:whenSelection('change')", 2)%>
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
                  <table cellpadding="0" cellspacing="0">
					<tr>
                      <td align="left"><a href="javascript:scaleview()" class="c"><img src="/images/admin/button/btn_sulmunchuckdo.gif" border="0"></a></td>
					  <td width=810></td>
					  <td align="right"><a href="javascript:insertContents()" class="c"><img src="/images/admin/button/btn_add.gif"border="0"></a></td>
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
                      <td class="table_title">평가분류</td>
                    </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("SulmunQuestionList");
    DataBox dbox = null;
    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); %>
                    <tr>
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_sulnum"))%></td>
                      <td class="table_02_2"><a href="javascript:detailSelect('<%=String.valueOf(dbox.getInt("d_sulnum"))%>')" class="e"><%=dbox.getString("d_sultext")%></a></td>
                      <td class="table_02_1"><%=dbox.getString("d_distcodenm")%></td>
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
