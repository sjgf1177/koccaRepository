<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunSubjPaper_L.java
//  3. 개      요: 과정설문 - 설문지관리
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

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");

    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_gyear    = box.getStringDefault("s_gyear","2004"); 

    String  v_gubun        = box.getStringDefault("p_gubun",  SulmunSubjBean.DEFAULT_SUBJ);
    String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass","ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");

   String  s_gadmin = box.getSession("gadmin");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "blue";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";
%>

<html>
<head>
<title>설문지-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {

	if (p_gubun == "TARGET") {
      document.form1.action = "/servlet/controller.research.SulmunTargetPaperServlet";

	} else if (p_gubun == "COMMON") {
      document.form1.action = "/servlet/controller.research.SulmunCommonPaperServlet";
	
	} else if (p_gubun == "CONTENTS") {
      document.form1.action = "/servlet/controller.research.SulmunContentsPaperServlet";

	} else if (p_gubun == "ALL") {
      document.form1.action = "/servlet/controller.research.SulmunAllPaperServlet";    	
	} 
	document.form1.p_action.value = 'change';
	document.form1.p_gubun.value = p_gubun;
    document.form1.s_grcode.value = "";
	document.form1.p_process.value = 'SulmunPaperListPage';
	document.form1.submit();
}

function InsertPaperPage(p_subjnm, p_subjseq, p_subjseqgr) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
    }
    
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.research.SulmunSubjPaperServlet";
		document.form1.p_process.value = "SulmunPaperInsertPage";
        document.form1.p_subjnm.value = p_subjnm;
		document.form1.p_subjseq.value = p_subjseq;
		document.form1.p_subjseqgr.value = p_subjseqgr;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function CopyPaperPage(p_subjnm, p_subjseq, p_subjseqgr) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
    }
    
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.research.SulmunSubjPaperServlet";
		document.form1.p_process.value = "SulmunPaperPoolPage";
    	document.form1.p_subj.value = document.form1.s_subjcourse.value;
		document.form1.p_subjseq.value = p_subjseq;
		document.form1.p_subjseqgr.value = p_subjseqgr;
        document.form1.p_action.value = "go";
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function UpdatePaperPage(p_sulpapernum, p_subjnm, p_subj, p_subjseq, p_subjseqgr, p_progress) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
    }//alert(p_progress);
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.research.SulmunSubjPaperServlet";
		document.form1.p_subj.value = p_subj;
        document.form1.p_sulpapernum.value = p_sulpapernum;
        document.form1.p_process.value = 'SulmunPaperUpdatePage';
        document.form1.p_subjnm.value = p_subjnm;
		document.form1.p_subjseq.value = p_subjseq;
		document.form1.p_subjseqgr.value = p_subjseqgr;
		document.form1.s_progress.value = p_progress;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function whenSelection(p_action) {

      	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	   }
    if (p_action=="go") {
       if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
           return;
       }
     top.ftop.setPam();
    }
  document.form1.p_action.value  = p_action;
  document.form1.p_process.value = 'SulmunPaperListPage';
  document.form1.submit();
}

function PreviewPaper(p_grcode, p_subj, p_sulpapernum, p_sulpapernm) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openWinQuestion";
  document.form3.p_process.value = "SulmunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_sulpapernum.value= p_sulpapernum; 
  document.form3.p_sulpapernm.value= p_sulpapernm;
  document.form3.submit();
 
  farwindow.window.focus();
  document.form3.target = window.self.name;
}

function ReloadPage(p_action) {
  document.form1.p_process.value = 'SulmunPaperListPage';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.research.SulmunSubjPaperServlet">
	<input type="hidden" name="p_process"     value="<%=v_process%>">
	<input type="hidden" name="p_action"      value="<%=v_action%>">
	<input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
	<input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
	<input type="hidden" name="p_sulpapernum" value="">
	<input type="hidden" name="p_subj"      value="">
	<input type="hidden" name="p_subjnm"      value="">
	<input type="hidden" name="p_subjseq"      value="">
	<input type="hidden" name="p_subjseqgr"      value="">
	<input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
	<input type="hidden" name="s_progress"       value="">

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
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('CONTENTS')" class="c">가입경로</a></td>
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
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('CONTENTS')" class="c">콘텐츠설문</a></td>
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

                              <table width="99%" cellspacing="0" cellpadding="0" class="form_table">
                                <tr>
                                  <td align=left> <font color="red">★</font> 
			                       <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
								   &nbsp;
								   <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
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
								   <%= SelectSubjBean.getSubjseq(box, true, true)%><!-- getSubj(RequestBox, isChange, isALL)    과정차수  -->

                                  </td>
								   <td width="10%" ><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
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
         <!--         <table cellpadding="0" cellspacing="0" class="table1">
                    <tr> 
                      <td align="right"><a href="javascript:CopyPaperPage()"><img src="/images/admin/button/btn_sulmuncopy.gif" border="0"></a></td>
                    </tr>
                  </table>-->
				  <br>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 설문지 관리-등록 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="table_out">

                    <tr> 
                      <td colspan="7" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%"  class="table_title">NO</td>
                      <td class="table_title" colspan="2">과정명</td>
                 <!--     <td width="5%"  class="table_title">NO</td>-->
                      <td width="30%" class="table_title">설문지명</td>
                      <td width="10%" class="table_title">문제수</td>
					  <td width="10%" class="table_title">기능</td>
                    </tr>
<%
         ArrayList blist = (ArrayList)request.getAttribute("SulmunPaperListPage");

        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
    
	              if (i==0){
%>
                   <tr> 
                      <td class="table_01"   rowspan="<%=String.valueOf(blist.size())%>">1</td>
                      <td class="table_02_1" rowspan="<%=String.valueOf(blist.size())%>"><%=dbox.getString("d_subjnm")%></td> 
<%			  }	 %>
					  <td class="table_02_1"><%=StringManager.cutZero(dbox.getString("d_subjseqgr"))%>차</td> 
<%             
	                
                if (dbox.getInt("d_sulpapernum") > 0) { %>
                 <!--     <td class="table_01"><%//=String.valueOf(dbox.getInt("d_sulpapernum"))%></td>-->
                      <td class="table_02_1">
					  <a href="javascript:UpdatePaperPage('<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','<%=dbox.getString("d_subjnm")%>','<%=dbox.getString("d_subj")%>', '<%=dbox.getString("d_subjseq")%>', '<%=dbox.getString("d_subjseqgr")%>', '<%=dbox.getInt("d_progress")%>')">
					  <%=dbox.getString("d_sulpapernm")%></a></td>
                      <td class="table_02_1"><%=String.valueOf(dbox.getInt("d_totcnt"))%></td>
					  <td class="table_02_1">
                      <a href="javascript:PreviewPaper('<%=v_grcode%>','<%=dbox.getString("d_subj")%>','<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','<%=dbox.getString("d_sulpapernm")%>')"><img src="/images/admin/button/b_preview.gif" border="0"></a>
                      </td>
<%              } else {  %>
                      <!--<td class="table_01"></td>-->
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"><a href="javascript:InsertPaperPage('<%=dbox.getString("d_subjnm")%>', '<%=dbox.getString("d_subjseq")%>', '<%=dbox.getString("d_subjseqgr")%>')"><img src="/images/admin/button/b_add.gif" border="0"></a>&nbsp;<a href="javascript:CopyPaperPage('<%=dbox.getString("d_subjnm")%>', '<%=dbox.getString("d_subjseq")%>', '<%=dbox.getString("d_subjseqgr")%>')"><img src="/images/admin/button/btn_sulmuncopy.gif" border="0"></a></td>
<%              }  %>
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
<form name="form3" method="post" action="/servlet/controller.research.SulmunSubjPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_subjseq"        value="">
  <input type="hidden" name="p_sulpapernum" value="">
  <input type="hidden" name="p_sulpapernm" value="">
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>


