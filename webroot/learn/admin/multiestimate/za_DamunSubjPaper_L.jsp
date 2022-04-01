<%
/**
 * file name : za_DamunSubjPaper_L.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 다면평가 문항관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");

    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹
    String  v_gyear     = box.getString("s_gyear"); 
	String  v_grseq     = box.getString("s_grseq");
    String  v_gubun        = box.getStringDefault("p_gubun",  DamunSubjBean.DEFAULT_SUBJ);
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

%>

<html>
<head>
<title>다면평가지-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
function changeTabpage(p_gubun) {

    if (p_gubun == "COMMON") {
      document.form1.action = "/servlet/controller.multiestimate.DamunCommonPaperServlet";
    
    } 
    document.form1.p_action.value = 'change';
    document.form1.p_gubun.value = p_gubun;
    document.form1.s_grcode.value = "";
    document.form1.p_process.value = 'DamunPaperListPage';
    document.form1.submit();
}

function InsertPaperPage(p_subjnm) {
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
        document.form1.action = "/servlet/controller.multiestimate.DamunSubjPaperServlet";
        document.form1.p_process.value = "DamunPaperInsertPage";
        document.form1.p_subjnm.value = p_subjnm;
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function CopyPaperPage() {
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
        document.form1.action = "/servlet/controller.multiestimate.DamunSubjPaperServlet";
        document.form1.p_process.value = "DamunPaperPoolPage";
        document.form1.p_subj.value = document.form1.s_subjcourse.value;
        document.form1.p_action.value = "go";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function UpdatePaperPage(p_damunpapernum, p_subjnm,p_subj,p_d_gubun) {
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
        document.form1.action = "/servlet/controller.multiestimate.DamunSubjPaperServlet";
        document.form1.p_subj.value = p_subj;
        document.form1.p_damunpapernum.value = p_damunpapernum;
        document.form1.p_process.value = 'DamunPaperUpdatePage';
        document.form1.p_subjnm.value = p_subjnm;
        document.form1.p_d_gubun.value = p_d_gubun;
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

function whenSelection(p_action) {
    
    if (p_action=="go") {
        if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
        top.ftop.setPam();
    }
  document.form1.p_action.value  = p_action;
  document.form1.p_process.value = 'DamunPaperListPage';
  document.form1.submit();
}

function PreviewPaper(p_grcode, p_subj, p_damunpapernum, p_damunpapernm) {
  window.self.name = "winSelectView";
  farwindow =   window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=800, height=600, top=0, left=0");
  document.form3.target = "openWinQuestion";
  document.form3.p_process.value = "DamunPaperPreviewPage";
  document.form3.p_grcode.value  = p_grcode;
  document.form3.p_subj.value    = p_subj; 
  document.form3.p_damunpapernum.value= p_damunpapernum; 
  document.form3.p_damunpapernm.value= p_damunpapernm;
  document.form3.submit();
 
  farwindow.window.focus();
  document.form3.target = window.self.name;
}
function ReloadPage() {
  document.form1.p_process.value = 'DamunPaperListPage';
  document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.multiestimate.DamunSubjPaperServlet">
    <input type="hidden" name="p_process"     value="<%=v_process%>">
    <input type="hidden" name="p_action"      value="<%=v_action%>">
    <input type="hidden" name="p_grcode"      value="<%=v_grcode%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
	<input type="hidden" name="p_grseq"      value="<%=v_grseq%>">

    <input type="hidden" name="p_damunpapernum" value="">
    <input type="hidden" name="p_damunpapernm" value="">
    <input type="hidden" name="p_subj"      value="">
    <input type="hidden" name="p_subjnm"      value="">
    <input type="hidden" name="p_gubun"       value="<%=v_gubun%>">
    <input type="hidden" name="p_d_gubun"       value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title02.gif" ></td>
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
                <td class="<%=v_tab_color1%>_butt_middle">과정평가</td>
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
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('COMMON')" class="c">일반평가</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
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
                                  <td >
                                   <font color="red">★</font> <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                                   <%= SelectEduBean.getGyear(box, true )%><!-- getGyear(RequestBox, isChange)   해당연도  -->
                                   <font color="red">★</font> <%= SelectEduBean.getGrseq(box, true, true)%><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
                                   <br><%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                                   <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
                                   <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->

									<!--<font color="red">★</font> <%= SelectSubjBean.getSubjseq(box, true, false)%> -->
									<font color="red">★</font> <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->


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
                      <td align="left"></td>
                      <td align="right"><a href="javascript:InsertPaperPage('')"><img src="/images/admin/button/btn_add.gif" border="0"></a>
                      <a href="javascript:CopyPaperPage()"><img src="/images/admin/button/btn_pyunggacopy.gif" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="3"></td>
                    </tr>
                  </table>
                  <!----------------- 추가버튼 끝 ----------------->
                  <!----------------- 다면평가지 관리-등록 시작 ----------------->
                  <table cellspacing="1" cellpadding="5" class="table_out">

                    <tr> 
                      <td colspan="6" class="table_top_line"></td>
                    </tr>
                    <tr> 
                      <td width="5%"  class="table_title">NO</td>
                      <td width="7%" class="table_title">분류</td>
                      <td width="7%" class="table_title">문제수</td>
                      <td width="39%" width="table_title" class="table_title">다면평가지명</td>
                      <td width="16%" class="table_title">기간(1차)</td>
                      <td width="16%" class="table_title">기간(2차)</td>
                      <td width="10%" class="table_title">기능</td>
                    </tr>
<%
         ArrayList blist = (ArrayList)request.getAttribute("DamunPaperListPage");

        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
 //   out.println("d_fdamunend" + dbox.getString("d_fdamunend"));out.println("yyyyMMdd" + FormatDate.getDate("yyyyMMdd"));
                if (dbox.getInt("d_damunpapernum") > 0) { %>
                      <td class="table_01"><%=String.valueOf(dbox.getInt("d_damunpapernum"))%></td>
                      <td class="table_02_1"><%if(dbox.getString("d_d_gubun").equals("1")){%>단일평가<%}else if(dbox.getString("d_d_gubun").equals("2")){%>사전/사후평가<%}%></td>
                      <td class="table_02_1"><%=String.valueOf(dbox.getInt("d_totcnt"))%></td>
<% if ( dbox.getString("d_fdamunend").equals("") || Integer.parseInt(dbox.getString("d_fdamunend")) - Integer.parseInt(FormatDate.getDate("yyyyMMdd")) > 0 ||  Integer.parseInt(dbox.getString("d_fdamunend")) - Integer.parseInt(FormatDate.getDate("yyyyMMdd")) == 0 ) { %>  
                      <td class="table_02_1"><a href="javascript:UpdatePaperPage('<%=String.valueOf(dbox.getInt("d_damunpapernum"))%>','<%=dbox.getString("d_subjnm")%>','<%=dbox.getString("d_subj")%>','<%=dbox.getString("d_d_gubun")%>')"><%=dbox.getString("d_damunpapernm")%></a></td>
<% } else { %>              
                      <td class="table_02_1"><%=dbox.getString("d_damunpapernm")%></td>
<% } %>
                      <td class="table_02_1"><%=FormatDate.getFormatDate(dbox.getString("d_fdamunstart"),"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(dbox.getString("d_fdamunend"),"yyyy-MM-dd")%></td>
                      <td class="table_02_1"><%if(dbox.getString("d_d_gubun").equals("2")){%><%=FormatDate.getFormatDate(dbox.getString("d_sdamunstart"),"yyyy-MM-dd")%> ~ <%=FormatDate.getFormatDate(dbox.getString("d_sdamunend"),"yyyy-MM-dd")%><%}%></td>
                      <td class="table_02_1">
                      <a href="javascript:PreviewPaper('<%=v_grcode%>', '<%=dbox.getString("d_subj")%>','<%=String.valueOf(dbox.getInt("d_damunpapernum"))%>','<%=dbox.getString("d_damunpapernm")%>')"><img src="/images/admin/button/b_preview.gif" border="0"></a>
                      </td>
<%              } else {  %>
                      <td class="table_01"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
                      <td class="table_02_1"></td>
<%              }  %>
                    </tr>
<%
      }
%>

                  </table>
                  <br>
                  <!----------------- 다면평가지 관리-등록 끝 ----------------->
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
<form name="form3" method="post" action="/servlet/controller.multiestimate.DamunSubjPaperServlet">
  <input type="hidden" name="p_process"     value="">
  <input type="hidden" name="p_grcode"      value="">
  <input type="hidden" name="p_subj"        value="">
  <input type="hidden" name="p_damunpapernum" value="">
  <input type="hidden" name="p_damunpapernm" value="">
  <input type="hidden" name="p_d_gubun" value="">
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>


