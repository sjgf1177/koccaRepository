<%
//**********************************************************
//  1. 제      목: 설문
//  2. 프로그램명: za_SulmunCommonPaper_L.java
//  3. 개      요: 일반설문 - 설문지관리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
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
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    String  v_action    = box.getString("p_action");

    String  v_grcode    = box.getString("s_grcode");        //교육그룹
    String  v_gyear = box.getString("s_gyear"); 

    String  v_gubun = box.getStringDefault("p_gubun",  SulmunCommonBean.DEFAULT_SUBJ);
  
    String  s_gadmin = box.getSession("gadmin");

    String v_title_img = ""; 
    if (v_gubun.equals("COMMON"))     v_title_img = "3";
    else if (v_gubun.equals("SUBJ"))  v_title_img = "4";
    else if (v_gubun.equals("WHOLE")) v_title_img = "5";

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "blue";
	String v_tab_color4 = "black";
	String v_tab_color5 = "black";
%>

<html>
<head>
<title>설문지-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {

	if (p_gubun == "TARGET") {
      document.form2.action = "/servlet/controller.research.SulmunTargetPaperServlet";

	} else if (p_gubun == "SUBJ") {
      document.form2.action = "/servlet/controller.research.SulmunSubjPaperServlet";

	} else if (p_gubun == "CONTENTS") {
      document.form2.action = "/servlet/controller.research.SulmunContentsPaperServlet";
	} else if (p_gubun == "REGIST") {
      document.form2.action = "/servlet/controller.research.SulmunRegistPaperServlet";
	} else if (p_gubun == "ALL") {
      document.form2.action = "/servlet/controller.research.SulmunAllPaperServlet";    		
	} 
	document.form2.p_action.value = 'change';
	document.form2.p_gubun.value = p_gubun;
    document.form2.s_grcode.value = "";
	document.form2.p_process.value = 'SulmunPaperListPage';
    document.form2.target="_self";
	document.form2.submit();
}

function InsertPaperPage(p_subjnm) {

	if (document.form2.s_grcode.value == '') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
//        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunCommonPaperServlet";
		document.form2.p_process.value = "SulmunPaperInsertPage";
        document.form2.p_subjnm.value = p_subjnm;
		document.form2.submit();

        farwindow.window.focus();
//        document.form2.target = window.self.name;

}

function UpdatePaperPage(p_sulpapernum, p_subjnm,p_subj) {

	if (document.form2.s_grcode.value == '') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
//        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 1000, height = 667, top=0, left=0");
        document.form2.target = "openWinQuestion";
		document.form2.action = "/servlet/controller.research.SulmunCommonPaperServlet";
		document.form2.p_subj.value = p_subj;
        document.form2.p_sulpapernum.value = p_sulpapernum;
        document.form2.p_process.value = 'SulmunPaperUpdatePage';
        document.form2.p_subjnm.value = p_subjnm;
		document.form2.submit();

        farwindow.window.focus();
//        document.form2.target = window.self.name;

}

function whenSelection(p_action) {

	if (document.form2.s_grcode.value == '') {
			alert("교육그룹을 선택하세요.");
			return ;
	}

  document.form2.p_action.value  = p_action;
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.submit();

}

function PreviewPaper(p_grcode, p_subj, p_sulpapernum, p_sulpapernm) {
//  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=678, height=600, top=0, left=0");
  document.form3.target = "openWinQuestion";
  document.form3.p_process.value = "SulmunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_sulpapernum.value= p_sulpapernum; 
  document.form3.p_sulpapernm.value= p_sulpapernm;
  document.form3.submit();
 
  farwindow.window.focus();
//  document.form2.target = window.self.name;
}
function ReloadPage() {
  document.form2.p_process.value = 'SulmunPaperListPage';
  document.form2.submit();
}
    // select box 설정
    $(function() {
        $("#oGrcode").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrYear
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });
    });
    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oSubjcourse").empty();
        $("#oSubjseq").empty();
        $("#oGyear").append("<option value=\"\">== 선택 ==</option>");
        $("#oGrseq").append("<option value=\"\">== 선택 ==</option>");
        $("#oSubjcourse").append("<option value=\"ALL\">== 전체 ==</option>");
        $("#oSubjseq").append("<option value=\"ALL\">== 전체 ==</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.research.SulmunCommonPaperServlet">
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
                <td class="<%=v_tab_color1%>_butt_middle"><a href="javascript:changeTabpage('ALL')" class="c">과정설문</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color1%>_butt_bottom"></td>
              </tr>
            </table>
          </td>

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
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle">일반설문</td>
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
                                    <td  width="10%"> 
                                        <font color="red">★</font>교육그룹
                                    </td>
                                    <td  width="20%">
                                        <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 교육그룹 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= v_grcode %>" isLoad="true" />
                                    </td>
                                    <td  width="5%">
                                        연도
                                    </td>
                                    <td>
<%
    if ( !v_gyear.equals("") || !v_grcode.equals("") ) {
%>
                                        <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" param="<%= v_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= v_gyear %>" isLoad="true" />
<%
    } else {
%>
                                        <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="== 선택 ==" type="sqlID" isLoad="false" />
<%
    }
%>
                                    </td>
                                    <td align='right'>
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
                  <br>
                  <!----------------- form 끝 ----------------->
                  <!----------------- 추가버튼 시작 ----------------->
                  <table cellpadding="0" cellspacing="0" class="table1">
                    <tr> 
                      <td align="right"><a href="javascript:InsertPaperPage('')"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 설문지 관리-등록 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="table_out">

                    <tr> 
                      <td colspan="5" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%" class="table_title">NO</td>
                      <td class="table_title">설문지명</td>
					  <td width="12%" class="table_title">문제수</td>
                      <td width="17%" class="table_title">설문기간</td>
                      <td width="10%" class="table_title">기능</td>
                    </tr>
<%
         ArrayList blist = (ArrayList)request.getAttribute("SulmunPaperListPage");

        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
    
%>
                    <tr> 
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_sulpapernum"))%></td>
                      <td class="table_02_1">
<%// if ( dbox.getString("d_sulend").equals("") || Integer.parseInt(dbox.getString("d_sulend")) - Integer.parseInt(FormatDate.getDate("yyyyMMdd")) > 0 ||  Integer.parseInt(dbox.getString("d_sulend")) - Integer.parseInt(FormatDate.getDate("yyyyMMdd")) == 0 ) { %>				  
					  <a href="javascript:UpdatePaperPage('<%=String.valueOf(dbox.getInt("d_sulpapernum"))%>','','COMMON')"><%=dbox.getString("d_sulpapernm")%></a>
<%// } else { %>					  
					  <%//=dbox.getString("d_sulpapernm")%>
<%// } %>
					  </td>
					  <td class="table_02_1"><%=dbox.getInt("d_totcnt")%></td>
					  <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_sulstart"),"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(dbox.getString("d_sulend"),"yyyy-MM-dd")%></td>
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
<form name="form3" method="post" action="/servlet/controller.research.SulmunCommonPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_sulpapernum" value="">
  <input type="hidden" name="p_sulpapernm" value="">
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>


