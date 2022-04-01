<%
//**********************************************************
//  1. 제      목: 템플릿 관리화면
//  2. 프로그램명: za_Templet_L.jsp
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
    
    String v_mainmenu 		= "";
    String v_mainmenucode 	= "";
    
    ArrayList  list = (ArrayList)request.getAttribute("TempletMasterList");
/*
    String v_type        = "";
    String v_imgpath     = "";
    String v_subbg1      = "";
    String v_subtopimg1  = "";
    String v_subbg2      = "";
    String v_subtopimg2  = "";
    String v_subbg3      = "";
    String v_subtopimg3  = "";
    String v_subbg4      = "";
    String v_subtopimg4  = "";
    String v_subbg5      = "";
    String v_subtopimg5  = "";
    String v_subbg6      = "";
    String v_subtopimg6  = "";
    String v_subbg7      = "";
    String v_subtopimg7  = "";
    String v_subbg8      = "";
    String v_subtopimg8  = "";

    DataBox dbox = (DataBox)request.getAttribute("TempletData");
    if (dbox != null) {
        v_type        = dbox.getString("d_type");
        v_imgpath     = dbox.getString("d_imgpath");
        v_subbg1      = dbox.getString("d_subbg1");
        v_subtopimg1  = dbox.getString("d_subtopimg1");
        v_subbg2      = dbox.getString("d_subbg2");
        v_subtopimg2  = dbox.getString("d_subtopimg2");
        v_subbg3      = dbox.getString("d_subbg3");
        v_subtopimg3  = dbox.getString("d_subtopimg3");
        v_subbg4      = dbox.getString("d_subbg4");
        v_subtopimg4  = dbox.getString("d_subtopimg4");
        v_subbg5      = dbox.getString("d_subbg5");
        v_subtopimg5  = dbox.getString("d_subtopimg5");
        v_subbg6      = dbox.getString("d_subbg6");
        v_subtopimg6  = dbox.getString("d_subtopimg6");
        v_subbg7      = dbox.getString("d_subbg7");
        v_subtopimg7  = dbox.getString("d_subtopimg7");
        v_subbg8      = dbox.getString("d_subbg8");
        v_subtopimg8  = dbox.getString("d_subtopimg8");
    }
*/
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 수정
    function update() {
        document.form1.action="/servlet/controller.templet.TempletServlet"
        document.form1.p_process.value = 'updateSub';
        document.form1.submit();
    }

	function insert() {
		document.inputForm.action = "/servlet/controller.templet.TempletServlet";
		document.inputForm.p_process.value = "insertSubTemplet";
		document.inputForm.submit();
	}

    // 켄텐츠관리 (팝업)
    function menuPopup(gubun, position) {
        document.form1.p_gubun.value    = gubun;
        document.form1.p_position.value = position;

        window.self.name = "TempletMain";
        open_window("menuPopup","","100","100","410","260");
        document.form1.target = "menuPopup";
        document.form1.action='/servlet/controller.templet.TempletServlet';
        document.form1.p_process.value = 'menuPopup';
        document.form1.submit();

        document.form1.target = window.self.name;
    }

    // 대메뉴 등록 (팝업)
    function mainMenuPopup() {

        window.self.name = "TempletMain";
        open_window("menuPopup","","100","100","410","260");
        document.form1.target = "menuPopup";
        document.form1.action='/servlet/controller.templet.TempletServlet';
        document.form1.p_process.value = 'updateMainMenuPage';
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

    function menuTempletMod(grcode, menucode) {
		window.progIF.location.href = "/servlet/controller.templet.TempletServlet?p_process=modeform&p_grcode="+grcode+"&p_gubun="+menucode;
    }

    function modFormDP() {
		if(document.inputForm.style.display == 'none') {
			document.inputForm.style.display = 'block';
		} else {
			document.inputForm.style.display = 'none';
		}
    }
//-->
</SCRIPT>
</head>

<body bgcolor="FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<!----------------- 입력폼 시작 ----------------->
<form id='detailForm' name="inputForm" method="post" style="display:none"  enctype="multipart/form-data">
	<input type="hidden" name="p_process" value="">
	<input id="isnew" type="hidden" name="isnew" value="">
	<input type="hidden" name="p_kind" id="p_kind" value="MB">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_03.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			<td colspan="4" class="table_top_line"></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>기관코드</b></td>
			<td class="table_02_2"><input type="text" name="p_grtype" id="p_grtype" value="" readonly/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴구분</b></td>
			<td class="table_02_2"><input type="text" name="p_gubun" id="p_gubun" value="" readonly/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴코드</b></td>
			<td class="table_02_2"><input type="text" name="p_menuid" id="p_menuid" value="00" readonly/>*시스템자동부여</td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴순서</b></td>
			<td class="table_02_2"><input type="text" name="p_orders" id="p_orders" value="0" readonly/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴시작좌표(좌측기준)</b></td>
			<td class="table_02_2"><input type="text" name="p_menuxposition" id="p_menuxposition" value="0"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴명</b></td>
			<td class="table_02_2"><input type="text" name="p_menuname" id="p_menuname" value="" style="width:95%"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>관련URL</b></td>
			<td class="table_02_2"><input type="text" name="p_menuurl" id="p_menuurl" value="" style="width:95%"/></td>
		  </tr>
		  <tr id="subInput">
			<td height="25" class="table_01" width="25%" rowspan="6"><b>메뉴이미지</b></td>
			<td class="table_02_2">TOP Standard Image<input name="p_menuimg" id="p_menuimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">TOP Rollover Image <input name="p_menuoverimg" id="p_menuoverimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">LEFT HOME Standard Image <input name="p_subimg" id="p_subimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">LEFT HOME Rollover Image <input name="p_suboverimg" id="p_suboverimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">FLASH Image <input name="p_flashfilename" id="p_flashfilename" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">LEFT MAIN Menu Image <input name="p_menuhomeimg" id="p_menuhomeimg" type="file" class="input" size="60" /></td>
		  </tr>
		</table>
	  </td>
  </tr>
</table>
<table width="1000"><tr>
	<td align="center">
		<a href="javascript:insert();"><img alt="저장" src="/images/admin/portal/btn_save.gif" border=0></a>&nbsp;&nbsp;
		<a href="javascript:modFormDP();"><img alt="취소" src="/images/admin/portal/bt_cancel.gif" border=0></a>
	</td>
</tr></table>
</form>
<!----------------- 입력폼 끝 ----------------->
<iframe name="progIF" id="progIF" style="display:none" width='800' height='600' frameborder="no" src=""></iframe>

<form name="form1" method="post" enctype = "multipart/form-data">
    <input type="hidden" name="p_process" value="<%=v_process%>">
    <input type="hidden" name="p_grcode"   value="<%=v_grcode%>">
    <input type="hidden" name="p_grtype"   value="<%=v_grtype%>">
    <!-- input type="hidden" name="p_type"     value="<%//=v_type%>">
    <input type="hidden" name="p_imgpath"  value="<%//=v_imgpath%>"-->
    <input type="hidden" name="p_gubun"    value="">
    <input type="hidden" name="p_position"  value="">

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
          <td width="12%">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="blue_butt_left"></td>
                <td class="blue_butt_top"></td>
                <td rowspan="3" class="blue_butt_right"></td>
              </tr>
              <tr>
                <td class="blue_butt_middle">Sub</td>
              </tr>
              <tr>
                <td class="blue_butt_bottom"></td>
              </tr>
            </table></td>
          <td width="75%">&nbsp;</td>
        </tr>
        <tr>
          <td height="2" colspan="4" bgcolor="#6699CC"></td>
        </tr>
      </table>

      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="5" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="20%" class="table_title" height="50" ><b>기관코드</b></td>
          <td width="30%" class="table_title"><b>1차메뉴</b></td>
          <td width="20%" class="table_title"><b>2차메뉴 관리</b></td>
          <td width="30%" class="table_title"><b>템플릿 관리</b></td>
        </tr>
<%
	for( int i = 0; i < list.size(); i++ ) {
		DataBox dbox = (DataBox)list.get(i);
		
		v_mainmenu = dbox.getString("d_MENUNAME");
		v_mainmenucode = dbox.getString("d_GUBUN");
%>
		<tr>
			<td class="table_02_1"><%=v_grcode%></td>
			<td class="table_02_1"><%=v_mainmenu%></td>
			<td class="table_02_1"><a href="javascript:menuPopup('<%=v_mainmenucode%>','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
			<td class="table_02_1"><a href="javascript:menuTempletMod('<%=v_grcode%>','<%=v_mainmenucode%>')">수정</a></td>
		</tr>
<%
	}
%>
        <!-- tr>
          <td align="center" bgcolor="#EEEEEE" height="25" rowspan="16"><strong>SUB LEFT MENU</strong></td>
          <td class="table_02_1" rowspan="2">온라인과정</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('1','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg1</td>
          <td class="table_02_2"><input name="p_subbg1" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg1%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg1 </td>
          <td class="table_02_2"><input name="p_subtopimg1" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg1%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">오프라인과정</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('2','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg2</td>
          <td class="table_02_2"><input name="p_subbg2" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg2%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg2 </td>
          <td class="table_02_2"><input name="p_subtopimg2" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg2%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">나의강의실</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('3','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg3</td>
          <td class="table_02_2"><input name="p_subbg3" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg3%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg3 </td>
          <td class="table_02_2"><input name="p_subtopimg3" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg3%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">학습지원센터</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('4','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg4</td>
          <td class="table_02_2"><input name="p_subbg4" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg4%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg4 </td>
          <td class="table_02_2"><input name="p_subtopimg4" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg4%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">정보광장</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('5','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg5</td>
          <td class="table_02_2"><input name="p_subbg5" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg5%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg5 </td>
          <td class="table_02_2"><input name="p_subtopimg5" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg5%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">커뮤니티</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('6','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg6</td>
          <td class="table_02_2"><input name="p_subbg6" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg6%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg6 </td>
          <td class="table_02_2"><input name="p_subtopimg6" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg6%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">아카데미소개</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('7','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg7</td>
          <td class="table_02_2"><input name="p_subbg7" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg7%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg7 </td>
          <td class="table_02_2"><input name="p_subtopimg7" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg7%></b></td>
        </tr>
        <tr>
          
          <td class="table_02_1" rowspan="2">서비스(공통)</td>
          <td class="table_02_1" rowspan="2"><a href="javascript:menuPopup('8','C')"><img src="/images/admin/button/btn_menum.gif" width="70" height="20" border=0></a></td>
          <td class="table_02_1">Sub Bg8</td>
          <td class="table_02_2"><input name="p_subbg8" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subbg8%></b></td>
        </tr>
        <tr>
          <td class="table_02_1">subtopimg8 </td>
          <td class="table_02_2"><input name="p_subtopimg8" type="file" class="input" size="40" >&nbsp;&nbsp;&nbsp;<b><%//=v_subtopimg8%></b></td>
        </tr-->
      </table>

      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="10"></td>
        </tr>
        <tr>
          <td align="center">
                <a href="javascript:mainMenuPopup()"><img src="/images/admin/button/btn_register.gif" border="0"></a>
                <a href="javascript:movelist()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
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
