<%
/**
 * file name : za_JindanQuestion_L.jsp
 * date      : 
 * programmer:
 * function  : 진단문제 - 리스트페이지
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_process      = box.getString("p_process");
    String  v_action       = box.getString("p_action");
    String  v_grcode    = box.getStringDefault("s_grcode","ALL");        //교육그룹

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    //페이지수
    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));

    //페이지당 레코드수
    if (v_pagesize == 0)  v_pagesize = 10;

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
 //   String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getString("s_subjcourse");    //과정&코스
     
    int     v_totalpage		 = 0;
    int     v_rowcount		 = 1;
    int     v_totalrowcount	 = 0;
    int     v_rowspan		 = 0;
    int     v_total			 = 0;
    int		v_dispnum		 = 0;
%>
<html>
<head>
<title>평가문제관리-리스트</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--

// 선택 조회
function whenSelection(p_action) {

    var v_grcode, v_subj, v_upperclass, v_middleclass, v_lowerclass;

	v_grcode        = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].value;
	
    v_subj        = document.form1.s_subjcourse.value;
    v_upperclass  = document.form1.s_upperclass.value;
    v_middleclass = document.form1.s_middleclass.value;
//    v_lowerclass  = document.form1.s_lowerclass.value;

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (p_action=="go") {
        if (v_subj=="ALL"||v_subj=="----"){
            alert("과정을 선택하세요");
            return;
        }
    }
    if (p_action=="go") {
     document.form1.p_process.value = 'JindanQuestionListPage';
     document.form1.p_pageno.value = 1;
     document.form1.p_action.value  = p_action;
	 document.form1.submit();		
     top.ftop.setPam();
	} else {
     document.form1.p_process.value = 'JindanQuestionListPage';
     document.form1.p_action.value  = p_action;
     document.form1.p_grcode.value  = v_grcode;
	 document.form1.submit();	
	}
}

// 추가
function insert() {

	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="ALL"||document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
    }
        window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 690, height = 770, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.jindan.JindanQuestionServlet";
        document.form1.p_process.value = 'JindanQuestionInsertPage';
		document.form1.p_subj.value = document.form1.s_subjcourse.value;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
}

// 문제 내용 보기
function detailSelect(p_subj, p_jindannum, p_jindantype, p_action) {
       window.self.name = "winSelectView";
        farwindow = window.open("", "openWinQuestion", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 690, height = 770, top=0, left=0");
        document.form1.target = "openWinQuestion";
		document.form1.action = "/servlet/controller.jindan.JindanQuestionServlet";
		document.form1.p_process.value = "JindanQuestionUpdatePage";
		document.form1.p_jindannum.value = p_jindannum;
		document.form1.p_jindantype.value = p_jindantype;
		document.form1.p_action.value = p_action;
		document.form1.p_subj.value = p_subj;
		document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;

}

// 새창에서 처리후 화면 RELOAD됨
function ReloadPage(p_action) {
  document.form1.p_process.value = 'JindanQuestionListPage';
  document.form1.p_action.value  = p_action;
  document.form1.submit();
}


// 파일로 추가
function insertFileToDB() {
	if (document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return ;
	}
    if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
    }
    window.self.name = "winInsertPage";     //      opener 객체에 name 을 지정한다
    farwindow = window.open("", "openWinInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 800, height = 600, top=0, left=0");
    document.form1.target = "openWinInsert"
    document.form1.p_process.value = "JindanQuestionFileToDB";
    document.form1.p_action.value = "go";
	document.form1.p_subj.value = document.form1.s_subjcourse.value;
    document.form1.action = "/servlet/controller.jindan.JindanQuestionServlet";
	document.form1.submit();

    farwindow.window.focus();
    document.form1.target = window.self.name;
}

//페이지 이동
function go(index) {
     document.form1.target = "_self";
     document.form1.p_pageno.value = index;
     document.form1.action = '/servlet/controller.jindan.JindanQuestionServlet';
     document.form1.p_process.value = "JindanQuestionListPage";
     document.form1.submit();
}

// 페이지 이동
function goPage(pageNum) {
     document.form1.target = "_self";
     document.form1.p_pageno.value = pageNum;
     document.form1.action = '/servlet/controller.jindan.JindanQuestionServlet';
     document.form1.p_process.value = "JindanQuestionListPage";
     document.form1.submit();
}

// 페이지 사이즈 변경
function pagesize(pageSize) {
     document.form1.target = "_self";
     document.form1.p_pageno.value = 1;
     document.form1.p_pagesize.value = pageSize;
     document.form1.action = '/servlet/controller.jindan.JindanQuestionServlet';
     document.form1.p_process.value = "JindanQuestionListPage";
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
          <td><img src="/images/admin/etest/jindan_title01.gif" ></td>
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
            <form name="form1" method="post" action="/servlet/controller.jindan.JindanQuestionServlet">
              <input type="hidden" name="p_process" value="">
              <input type="hidden" name="p_action"  value="<%=v_action%>">
              <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
              <input type="hidden" name="p_subj"  value="">
              <input type="hidden" name="p_jindannum"  value="">
              <input type="hidden" name="p_jindantype"  value="">
              <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
              <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>">


			  <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table class="form_table" border=0 cellspacing="0" cellpadding="0" width="99%">
                    <tr>
                      <td colspan=3><font color="red">★</font> 
                         <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                         <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
                		 <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
				         <%= SelectSubjBean.getLowerClass(box, true, true, false)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
				         <%//SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                         <%//@ include file="/learn/admin/include/za_GoButton.jsp" %>
                      </td>
                    </tr>
                    <tr>
                      <td align="left" width="20%">
                        <%@ include file="/incmenu/search_subjnm.jsp"%>
                      </td>
                      <td width="63%">
                        <font color="red">★</font>  <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                        <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
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
        <tr align="right">
		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
		  <td align="left" class=sub_title>과정명
            : [<%=GetCodenm.get_subjnm(ss_subjcourse)%>]</td>
          <td align="right">
		  <a href="javascript:insert()" class="a"><img src="/images/admin/button/btn_add.gif" border="0"></a>
		  <a href="javascript:insertFileToDB()"><img src="/images/admin/button/btn_fileplus.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
		   <td height="3"></td>
		    <td height="3"></td>
			 <td height="3"></td>
			  <td height="3"></td>
        </tr>
      </table>
      <!-----------------  과정명, 추가버튼 끝 ----------------->
      <!----------------- 평가 문항관리-등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="30" class="table_title">NO</td>
          <td class="table_title">문제</td>
		  <td width="50" class="table_title">난이도</td>
    	  <td width="60" class="table_title">문제분류</td>


        </tr>
<%  if (!ss_subjcourse.equals("")&&v_action.equals("go")) {
        ArrayList  list = (ArrayList)request.getAttribute("ExamQuestionList");
        DataBox dbox = null;
        for (int i=0; i<list.size(); i++) {
        dbox  = (DataBox)list.get(i); 
        
        //페이지 나누기
		v_totalpage     = dbox.getInt("d_totalpage");
        v_rowcount      = dbox.getInt("d_rowcount");
        v_totalrowcount = dbox.getInt("d_totalrowcount");
        v_dispnum       = dbox.getInt("d_dispnum");

        %>
        <tr>
          <!--td class="table_01"><%//=String.valueOf(dbox.getInt("d_jindannum"))%></td-->
          <td class="table_01"><%=v_dispnum%></td>
          <td class="table_02_2"><a href="javascript:detailSelect('<%=ss_subjcourse%>','<%=String.valueOf(dbox.getInt("d_jindannum"))%>','<%=dbox.getString("d_jindantype")%>','go')" class="a"><%=StringManager.replace(dbox.getString("d_jindantext"),"\n","<br>")%></a></td>
		  <td class="table_02_1"><%=dbox.getString("d_levelsnm")%></td>
		  <td class="table_02_1"><%=dbox.getString("d_jindantypenm")%></td>
       </tr>
<%      }
    }

 %>
      </table>
      <!----------------- 평가 문항관리-등록 끝 ----------------->
      <br>
      <!----------------- page navi 시작 ----------------->
      <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" valign="absmiddle">
            <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
          </td>
        </tr>
      </table>
      <!----------------- page navi 끝 ----------------->
    </td>
  </tr>


</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
