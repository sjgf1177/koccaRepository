<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_L.jsp
//  3. 개      요: 선택과정차수별 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_hsubjnm   = box.getString("p_hsubjnm");


    //DEFINED in relation to select START
    String  ss_grcode       = box.getString("s_grcode");          //교육그룹
    String  ss_gyear        = box.getString("s_gyear");           //년도
    String  ss_grseq        = box.getString("s_grseq");           //교육차수
    String  ss_grseqnm      = box.getString("s_grseqnm");         //교육차수명
    String  ss_upperclass   = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass  = box.getString("s_middleclass");     //과정중분류
    String  ss_lowerclass   = box.getString("s_lowerclass");      //과정소분류
    String  ss_subjcourse   = box.getString("s_subjcourse");      //과정&코스
    String  ss_subjseq      = box.getString("s_subjseq");         //과정 차수
    String  ss_isclosed		= box.getString("s_isclosed");        //진행여부
//    String  v_ongo			= "whenSelection('selectAll')";
    //DEFINED in relation to select END

    int     v_seq       = 0;
    String  v_typesnm   = "";
    String  v_title     = "";
    String  v_addate    = "";
	String	v_codenm	= "";
    int     i = 0;
    ArrayList list    = (ArrayList)request.getAttribute("selectList");       // 공지리스트
    
    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelection(p_action) {
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "select";
		document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
    	document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

function goSubjectListPage() {
  
  document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
  document.form1.p_process.value = "selectAll";
  document.form1.p_action.value = "go";
  document.form1.submit();
}
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process" >
    <input type="hidden" name="p_action">
    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_year"    value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
    <input type="hidden" name="p_hsubjnm" value="<%=v_hsubjnm%>">
    <input type="hidden" name="p_seq"     value="">
    
    <input type="hidden" name="s_grcode"    	value="<%=ss_grcode%>">     <!--in relation to select-->
	<input type="hidden" name="s_gyear"     	value="<%=ss_gyear%>">      <!--in relation to select-->
	<input type="hidden" name="s_grseq"     	value="<%=ss_grseq%>">      <!--in relation to select-->
	<input type="hidden" name="s_upperclass"    value="<%=ss_upperclass%>"> <!--in relation to select-->
	<input type="hidden" name="s_middleclass"   value="<%=ss_middleclass%>"><!--in relation to select-->
	<input type="hidden" name="s_subjcourse"	value="<%=ss_subjcourse%>"> <!--in relation to select-->
	<input type="hidden" name="s_subjseq"   	value="<%=ss_subjseq%>">    <!--in relation to select-->
    
    <input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/co_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	    <!----------------- title 끝 ----------------->
      <br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
		    <td width="1%"><img src="/images/admin/common/icon.gif" ></td> 
            <td class=sub_title><%=v_hsubjnm%><!--인력개발부 &gt; 0001 &gt; Life Planning &gt; 0001--></td>
          </tr>
        </table>
        <!----------------- 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="right" height="20"><a href="javascript:insert()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
          </tr>
          <tr> 
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 추가 버튼 끝 ----------------->


        <!----------------- 공지사항관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td class="table_top_line" colspan="4" ></td>
          </tr>
          <tr> 
            <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title" width="15%">공지구분</td>
            <td class="table_title"><a href="javascript:whenOrder('title')" class="e">제목</a></td>
            <td class="table_title" width="16%"><a href="javascript:whenOrder('addate')" class="e">작성일</a></td>
          </tr>

<%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_seq       = dbox.getInt("d_seq");
                v_title     = dbox.getString("d_title");
                v_addate    = dbox.getString("d_addate");
                v_codenm    = dbox.getString("d_codenm");

%>

          <tr> 
            <td class="table_01" height="25"><%= list.size() - i %></td>
            <td class="table_02_1"><%= v_codenm %> </td>
            <td class="table_02_2"><a href="javascript:view('<%= v_seq%>')"><%= v_title%></a></td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_addate,"yyyy/MM/dd") %> </td>
          </tr>

<%
            }

          if(list.size() == 0){ %>
              <tr>
                <td class="table_02_1" colspan="4">등록된 내용이 없습니다</td>
              </tr>
<%  } %>
        </table>
        <!----------------- 공지사항관리 끝 ----------------->
        <br>
        <br>
      <!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center">&nbsp;</td>
          <td align="center"><a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
     </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
