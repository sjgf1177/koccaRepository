<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunContentsPaper_L.java
//  3. 개      요: 콘텐츠설문 - 설문지관리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
    String  v_grcode       = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_gyear        = box.getStringDefault("s_gyear","2004"); 
    String  v_gubun        = box.getStringDefault("p_gubun",  SulmunContentsBean.DEFAULT_SUBJ);
    String  s_gadmin       = box.getSession("gadmin");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "blue";
	String v_tab_color5 = "black";
    ArrayList blist = (ArrayList)request.getAttribute("SulmunPaperListPage");
    
%>

<html>
<head>
<title>설문지-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
// 다른 설문 이동
function changeTabpage(p_gubun) {

	if (p_gubun == "TARGET") {
      document.form2.action = "/servlet/controller.research.SulmunTargetPaperServlet";

	} else if (p_gubun == "COMMON") {
      document.form2.action = "/servlet/controller.research.SulmunCommonPaperServlet";
	
	} else if (p_gubun == "SUBJ") {
      document.form2.action = "/servlet/controller.research.SulmunSubjPaperServlet";
	} else if (p_gubun == "ALL") {
      document.form2.action = "/servlet/controller.research.SulmunAllPaperServlet";    		
	} else if (p_gubun == "CP") {
      document.form2.action = "/servlet/controller.research.SulmunCpPaperServlet";
	} 
	document.form2.p_action.value = 'change';
	document.form2.p_gubun.value = p_gubun;
    document.form2.s_grcode.value = "";
	document.form2.p_process.value = 'SulmunPaperListPage';
	document.form2.submit();
}

// 설문지 추가
function InsertPaperPage(p_subjnm) {

<%
    if(blist.size() > 0){
 %>
			alert("콘텐츠 평가는 1개의 설문지만 가능합니다.");
			return ;
<%	 
    }	
%>

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunContentsPaperServlet";
		document.form2.p_process.value = "SulmunPaperInsertPage";
        document.form2.p_subjnm.value = p_subjnm;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}

// 설문지 보기,수정
function UpdatePaperPage(p_sulpapernum, p_subjnm,p_subj) {

        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunContentsPaperServlet";
		document.form2.p_subj.value = p_subj;
        document.form2.p_sulpapernum.value = p_sulpapernum;
        document.form2.p_process.value = 'SulmunPaperUpdatePage';
        document.form2.p_subjnm.value = p_subjnm;
		document.form2.submit();

        farwindow.window.focus();
        document.form2.target = window.self.name;
}

function whenSelection(p_action) {
  document.form2.p_action.value  = p_action;
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.submit();
}

// 미리보기
function PreviewPaper(p_grcode, p_subj, p_sulpapernum, p_sulpapernm) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=678, height=600, top=0, left=0");
  document.form3.target = "openWinQuestion";
  document.form3.p_process.value = "SulmunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_sulpapernum.value= p_sulpapernum; 
  document.form3.p_sulpapernm.value= p_sulpapernm;
  document.form3.submit();
 
  farwindow.window.focus();
  document.form2.target = window.self.name;
}
function ReloadPage() {
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
<form name="form2" method="post" action="/servlet/controller.research.SulmunContentsPaperServlet">
	<input type="hidden" name="p_process"     value="<%=v_process%>">
	<input type="hidden" name="p_action"      value="<%=v_action%>">
	<input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
	<input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
	<input type="hidden" name="p_sulpapernum" value="">
	<input type="hidden" name="p_subj"      value="">
	<input type="hidden" name="p_subjnm"      value="">
	<input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
    <input type="hidden" name="s_grcode" value="ALL">  

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/research/r_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 

		  <!--td width="85">
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
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_left"></td>
                <td class="<%=v_tab_color1%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color1%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_middle"><a href="javascript:changeTabpage('ALL')" class="c">과정설문</a></td>
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
                <td class="<%=v_tab_color4%>_butt_middle">가입경로</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
		  <!--td width="85">
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
          <!--td width="2"></td>
		  <td width="85">
           <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('CP')" class="c">업체설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
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

                  <!----------------- 추가버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr> 
                      <td align="right"><a href="javascript:InsertPaperPage('')"><img src="/images/admin/button/btn_add.gif"  border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 설문지 관리-등록 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="table_out">
                    <tr> 
                      <td colspan="4" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%" class="table_title">NO</td>
					  <td class="table_title">설문지명</td>
					  <td width="12%" class="table_title">문제수</td>
					  <td width="10%" class="table_title">기능</td>
                    </tr>
<%
        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
%>
                    <tr> 
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_sulpapernum"))%></td>
                      <td class="table_02_1"><a href="javascript:UpdatePaperPage('<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','','CONTENTS')"><%=dbox.getString("d_sulpapernm")%></a></td>
                      <td class="table_02_1"><%=dbox.getInt("d_totcnt")%></td>
					  <td class="table_03_1">
                      <a href="javascript:PreviewPaper('<%=v_grcode%>','<%=dbox.getString("d_subj")%>','<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','<%=dbox.getString("d_sulpapernm")%>')"><img src="/images/admin/button/b_preview.gif"  border="0"></a>
                      </td>
                    </tr>
<%  
        } 
%>
                  </table>
				  <br>
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
<form name="form3" method="post" action="/servlet/controller.research.SulmunContentsPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_sulpapernum" value="">
  <input type="hidden" name="p_sulpapernm" value="">
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>


