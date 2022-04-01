<%
/**
 * file name : za_ETestQuestion_L.jsp
 * date      : 2003/08/29
 * programmer:
 * function  : 평가 문항관리 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_upperclass= box.getStringDefault("s_upperclass", "ALL");
    String  ss_etestsubj    = box.getString("s_etestsubj");       
    String p_action = box.getString("p_action");    
    if(p_action.equals("go")) ss_etestsubj = box.getString("s_etestsubj");
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서  

%>
<html>
<head>
<title>평가문항관리-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--
// 분류선택 - 해당 이테스트 그룹 가져옴
function whenSelection(p_action) {
whenSelection
  document.form1.p_process.value = 'ETestQuestionListPage';
  document.form1.p_action.value  = p_action;
  if (p_action == "go"){
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_etestsubj.value=="0"){
            alert("그룹을 선택하세요");
            return;
    }  
    document.form1.p_etestsubj.value = document.form1.s_etestsubj.options[document.form1.s_etestsubj.selectedIndex].value;
    top.ftop.setPam();
  }
  document.form1.submit();
}

// 문제지 상세보기,수정
function updatePage(p_etestsubj, p_etestnum, p_etesttype, p_action) {
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 900, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.etest.ETestQuestionServlet";
        document.form1.p_process.value = 'ETestQuestionUpdatePage';
        document.form1.p_etestsubj.value  = p_etestsubj;
		document.form1.p_etestnum.value  = p_etestnum;
		document.form1.p_etesttype.value  = p_etesttype;
        document.form1.p_action.value  = p_action;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 문제지 등록
function insertPage(s_etestsubj, p_etestnum, p_action) {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_etestsubj.value=="0"){
            alert("그룹을 선택하세요");
            return;
    }
    window.self.name = "winSelectView";
    farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,copyhistory=no, width = 667, height = 900, top=0, left=0");
    document.form1.target = "openWinQuestion";
	document.form1.action = "/servlet/controller.etest.ETestQuestionServlet";
    document.form1.p_process.value = 'ETestQuestionInsertPage';
    document.form1.p_etestnum.value  = p_etestnum;
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

// 파일로 추가등록
function insertFileToDB() {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_etestsubj.value=="0"){
            alert("그룹을 선택하세요");
            return;
    }
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = "ETestQuestionFileToDB";
    document.form1.p_action.value = "go";
	document.form1.p_etestsubj.value = document.form1.s_etestsubj.value;
    document.form1.action = "/servlet/controller.etest.ETestQuestionServlet";
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

// Pool 추가등록
function insertPool() {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_etestsubj.value=="0"){
            alert("그룹을 선택하세요");
            return;
    }
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 1017, height = 700, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = "ETestQuestionPoolPage";
    document.form1.p_action.value = "go";
	document.form1.p_etestsubj.value = document.form1.s_etestsubj.value;
    document.form1.action = "/servlet/controller.etest.ETestQuestionServlet";
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

// 정렬
function whenOrder(column) {
    if (document.form1.p_orderType.value == " asc") {
        document.form1.p_orderType.value = " desc";
    } else {
        document.form1.p_orderType.value = " asc";
    }

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

// 화면 리로드
function ReloadPage(p_action) {
  document.form1.p_process.value = 'ETestQuestionListPage';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- form 시작 ----------------->
      <table class="form_table_out" cellspacing="0" cellpadding="1">
        <tr>
          <td align="center">
            <table class="form_table_bg" cellspacing="0" cellpadding="0">
            <form name="form1" method="post" action="/servlet/controller.etest.ETestQuestionServlet">
              <input type="hidden" name="p_process" value="">
              <input type="hidden" name="p_action"  value="">
              <input type="hidden" name="p_etestsubj"  value="<%=ss_etestsubj%>">
              <input type="hidden" name="p_etesttype"  value="">
              <input type="hidden" name="p_etestnum"  value="">
              <input type="hidden" name="p_orderColumn">
              <input type="hidden" name="p_orderType" value="<%=v_orderType%>">                   
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table class="form_table" cellspacing="0" cellpadding="0" width="99%">
                    <tr>
                      <td> <font color="red">★</font> 
                         <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						 &nbsp;분류<%=CodeConfigBean.getCodeGubunSelect (ETestBean.ETSUBJ_CLASS, "", 1, "s_upperclass", ss_upperclass, "onchange=javascript:whenSelection('change')", 2)%>
                         <font color="red">★</font>  <%=ETestQuestionBean.getGroupSelect (ss_grcode, ss_upperclass, "s_etestsubj", ss_etestsubj, "")%>
		                 &nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                      </td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </form>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- form 끝 ----------------->
      <br>
      <br>
      <!----------------- 과정명, 추가버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" class=sub_title>그룹명
            : [<%=ETestQuestionBean.get_subjnm(ss_etestsubj)%>]</td>
          <td align="right">
		    <a href="javascript:insertPage('<%=ss_etestsubj%>','0','change')" class="b"><img src="/images/admin/button/btn_add.gif" border="0"></a>
			<a href="javascript:insertPool()"><img src="/images/admin/button/btn_poolplus.gif" border="0"></a>
            <a href="javascript:insertFileToDB()"><img src="/images/admin/button/btn_fileplus.gif" border="0"></a>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명, 추가버튼 끝  ----------------->
      <!----------------- 평가 문항관리-등록 시작 -------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="5" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="4%" class="table_title">NO</td>
          <td width="7%" class="table_title"><a href="javascript:whenOrder('codenm')" class="e">문제분류</a></td>          
          <td width="75%" class="table_title"><a href="javascript:whenOrder('a.etesttext')" class="e">문제명</a></td>
          <td width="7%" class="table_title"><a href="javascript:whenOrder('a.levels')" class="e">난이도</a></td>
          <td width="7%" class="table_title"><a href="javascript:whenOrder('a.etesttype')" class="e">문제타입</a></td>
        </tr>
<%  
    ArrayList  list = (ArrayList)request.getAttribute("QuestionList");
    DataBox dbox = null;
    for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); %>
        <tr>
          <td class="table_01"><%=String.valueOf(dbox.getString("d_etestnum"))%></td>
          <td class="table_01"><%=dbox.getString("d_codenm")%></td>         
          <td class="table_02_2"><a href="javascript:updatePage('<%=dbox.getString("d_etestsubj")%>','<%=String.valueOf(dbox.getString("d_etestnum"))%>','<%=String.valueOf(dbox.getString("d_etesttype"))%>','go')" class="a"><%=dbox.getString("d_etesttext")%></a></td>
          <td class="table_02_1"><%=dbox.getString("d_levelsnm")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_etesttypenm")%></td>
        </tr>
<%  } %>
      </table>
      <!----------------- 평가 문항관리-등록 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
