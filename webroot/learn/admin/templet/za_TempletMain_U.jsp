<%
//**********************************************************
//  1. 제      목: 템플릿 관리화면
//  2. 프로그램명: za_Templet_U.jsp
//  3. 개      요: 템플릿 관리화면 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 06. 21
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.templet.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process= box.getString("p_process");
    String v_grcode = box.getString("p_grcode");
    String v_grtype   = box.getString("p_grtype");

    String v_type          = "";
    String v_imgpath       = "";
    String v_mainbg        = "";
    String v_mainflash     = "";
    String v_menuflash     = "";
    String v_toplogo       = "";
    String v_topbg         = "";
    String v_foottopimg    = "";
    String v_footbg        = "";
    String v_footbgcolor1  = "";
    String v_footbgcolor2  = "";
    String v_footcopyright = "";
    String v_footleftimg   = "";

    DataBox dbox = (DataBox)request.getAttribute("TempletData");
    if (dbox != null) {
        v_type          = dbox.getString("d_type");
        v_imgpath       = dbox.getString("d_imgpath");
        v_mainbg        = dbox.getString("d_mainbg");
        v_mainflash     = dbox.getString("d_mainflash");
        v_menuflash     = dbox.getString("d_menuflash");
        v_toplogo       = dbox.getString("d_toplogo");
        v_topbg         = dbox.getString("d_topbg");
        v_foottopimg    = dbox.getString("d_foottopimg");
        v_footbg        = dbox.getString("d_footbg");
        v_footbgcolor1  = dbox.getString("d_footbgcolor1");
        v_footbgcolor2  = dbox.getString("d_footbgcolor2");
        v_footcopyright = dbox.getString("d_footcopyright");
        v_footleftimg   = dbox.getString("d_footleftimg");
    }

%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 수정
    function update() {
        document.form1.action="/servlet/controller.templet.TempletServlet"
        document.form1.p_process.value = 'updateMain';
        document.form1.submit();
    }

    // 켄텐츠관리 (팝업)
    function contentsPopup(gubun, position) {
        document.form1.p_gubun.value    = gubun;
        document.form1.p_position.value = position;

        window.self.name = "TempletMain";
        open_window("contentsPopup","","100","100","410","260");
        document.form1.target = "contentsPopup";
        document.form1.action='/servlet/controller.templet.TempletServlet';
        document.form1.p_process.value = 'contentsPopup';
        document.form1.submit();

        document.form1.target = window.self.name;
    }

    // 탭 선택
    function select_tab(sel){
        document.form1.target = "_self";
        if(sel == "1")      { document.form1.p_process.value = "updateMainPage";    }
        else if(sel == "2") { document.form1.p_process.value = "updateSubPage";}
        document.form1.action = '/servlet/controller.templet.TempletServlet';
        document.form1.submit();
    }

    // 리스트로 이동
    function movelist(){
        document.form1.action="/servlet/controller.templet.TempletServlet"
        document.form1.p_process.value = 'listPage';
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type="hidden" name="p_process"  value="<%=v_process%>">
    <input type="hidden" name="p_grcode"   value="<%=v_grcode%>">
    <input type="hidden" name="p_grtype"   value="<%=v_grtype%>">
    <input type="hidden" name="p_type"     value="<%=v_type%>">
    <input type="hidden" name="p_imgpath"  value="<%=v_imgpath%>">
    <input type="hidden" name="p_gubun"    value="">
    <input type="hidden" name="p_position" value="">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/course/c_title01_1.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>
     <!----------------- tab 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="12%"> <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="blue_butt_left"></td>
                <td class="blue_butt_top"></td>
                <td rowspan="3" class="blue_butt_right"></td>
              </tr>
              <tr>
                <td class="blue_butt_middle">Main</td>
              </tr>
              <tr>
                <td class="blue_butt_bottom"></td>
              </tr>
            </table></td>
          <td width="1%"></td>
          <td width="12%">
            <table cellspacing="0" cellpadding="0" class="s_table">
                <tr>
                  <td rowspan="3" class="black_butt_left"></td>
                  <td class="black_butt_top"></td>
                  <td rowspan="3" class="black_butt_right"></td>
                </tr>
                <tr>

                <td class="black_butt_middle"><a href="javascript:select_tab('2')" class="c">Sub</a></td>
                </tr>
                <tr>
                  <td class="black_butt_bottom"></td>
                </tr>
              </table>
             </td>
          <td width="75%">&nbsp;</td>
        </tr>
        <tr>
          <td height="2" colspan="4" bgcolor="#6699CC"></td>
        </tr>
      </table>

      <!----------------- 교육그룹 시작 ----------------->

      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td align="center" class="table_title">BackGround 설정</td>
          <td class="table_02_2" >
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <TD style="WIDTH: 73px" align="right">Main 배경&nbsp;: &nbsp;</TD>
                <TD align="left">
                  <input name="p_mainbg" type="file" class="input" size="70" >&nbsp;&nbsp;&nbsp;<b><%=v_mainbg%></b>
                </TD>
              </tr>
            </table>
          </td>
        </tr>
        <tr>
          <td align="center" class="table_title">Main Flash 파일</td>
          <td class="table_02_2" >
            <input name="p_mainflash" type="file" class="input" size="82" >&nbsp;&nbsp;&nbsp;<b><%=v_mainflash%></b>
          </td>
        </tr>
        <tr>
          <td align="center" class="table_title">Menu Flash 파일</td>
          <td class="table_02_2" >
            <input name="p_menuflash" type="file" class="input" size="82" >&nbsp;&nbsp;&nbsp;<b><%=v_menuflash%></b>
          </td>
        </tr>
      </table>
      <br>

      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="3" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="11%" height="50" class="table_title"><b>구분</b></td>
          <td width="16%" class="table_title"><b>영역</b></td>
          <td width="70%" class="table_title"><b>관리</b></td>
          <!--<td class="table_title"><b>선택</b></td>-->
        </tr>
        <tr>
          <td rowspan="2" align="center" bgcolor="#EEEEEE"><strong>TOP</strong></td>
          <td height="25" align="center" class="table_02_1">Top Logo</td>
          <td  class="table_02_2">
            <input name="p_toplogo" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_toplogo%></b>
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Top BackGround</td>
          <td align="center" class="table_02_2">
            <input name="p_topbg" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_topbg%></b>
          </td>
        </tr>
        <tr>
          <td align="center" bgcolor="#EEEEEE"><strong>LEFT</strong></td>
          <td height="25" align="center" class="table_02_1">Contents </td>
          <td align="center" class="table_02_2"><a href="javascript:contentsPopup('0','L')"><img src="/images/admin/button/btn_contentsm.gif" width="82" height="20" border='0'></a>
          </td>
        </tr>
        <tr>
          <td align="center" bgcolor="#EEEEEE" height="25"><strong>RIGHT</strong></td>
          <td class="table_02_1" align="center">Contents </td>
          <td align="center" class="table_02_2"><a href="javascript:contentsPopup('0','R')"><img src="/images/admin/button/btn_contentsm.gif" width="82" height="20" border='0'></a>
          </td>
        </tr>
        <tr>
          <td align="center" bgcolor="#EEEEEE" height="25"><strong>CENTER</strong></td>
          <td class="table_02_1" align="center">Contents </td>
          <td align="center" class="table_02_2"><a href="javascript:contentsPopup('0','C')"><img src="/images/admin/button/btn_contentsm.gif" width="82" height="20" border='0'></a>
          </td>
        </tr>																		   
        <tr>
          <td rowspan="6" align="center" bgcolor="#EEEEEE"><strong>FOOTER</strong></td>
          <td height="25" align="center" class="table_02_1">Footer Top Image</td>
          <td align="center" class="table_02_2">
            <input name="p_foottopimg" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_foottopimg%></b>
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Footer Background</td>
          <td align="center" class="table_02_2">
            <input name="p_footbg" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_footbg%></b>
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Footer bgcolor1</td>
          <td align="center" class="table_02_2">
            <input name="p_footbgcolor1" type="text" class="input" size="20" value="<%=v_footbgcolor1%>">
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Footer bgcolor2</td>
          <td align="center" class="table_02_2">
            <input name="p_footbgcolor2" type="text" class="input" size="20" value="<%=v_footbgcolor2%>">
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Footer CopyRight</td>
          <td align="center" class="table_02_2">
            <input name="p_footcopyright" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_footcopyright%></b>
          </td>
        </tr>
        <tr>
          <td height="25" align="center" class="table_02_1">Footer Left Image</td>
          <td align="center" class="table_02_2">
            <input name="p_footleftimg" type="file" class="input" size="60" >&nbsp;&nbsp;&nbsp;<b><%=v_footleftimg%></b>
          </td>
        </tr>
      </table>
        <!----------------- 교육그룹 끝 ----------------->
        <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
        <tr>
          <td align="center">
            <table width="197" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="59"><a href="javascript:update()"><img src="/images/admin/button/btn_register.gif" border="0"></a></td>
                <td width="79"><a href="javascript:movelist()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
