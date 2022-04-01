<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명 : za_SubjGong_R.jsp
//  3. 개      요: 과정차수별공지사항 선택과목상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process        = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_hsubjnm   = box.getString("p_hsubjnm");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    int v_pageno = box.getInt("p_pageno");   

    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_isclosed  = box.getString("s_isclosed");      //진행여부

    String  v_types      = "";
    String  v_typesnm    = "";
    String  v_addate     = "";
    String  v_title      = "";
    String  v_adcontent  = "";
    int    i         = 0;
    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_content = "";
    String v_upfileview = "";

    Vector v_realfileVector = null;
    Vector v_savefileVector = null;

   /*  공지 정보   */
    DataBox dbox = (DataBox)request.getAttribute("selectGong");
    if (dbox != null ) {
        v_seq           = dbox.getInt("d_seq");
        v_userid        = dbox.getString("d_userid");
        v_name          = dbox.getString("d_name");
        v_title         = dbox.getString("d_title");
        v_content       = dbox.getString("d_adcontent");
        v_cnt           = dbox.getInt("d_cnt");
        v_indate        = dbox.getString("d_addate");
        v_types         = dbox.getString("d_types");
        v_typesnm       = dbox.getString("d_typesnm");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
    }
%>

<html>
<head>
<title>::: 공지사항 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	
	//공지사항 수정
    function modify_gong() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }
	
	//공지사항 삭제
    function delete_gong() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
	
	//공지사항 리스트
    function list_gong() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">

    <input type='hidden' name='s_grcode'     value='<%=ss_grcode%>'>
    <input type='hidden' name='s_gyear'      value='<%=ss_gyear%>'>
    <input type='hidden' name='s_grseq'      value='<%=ss_grseq%>'>
    <input type='hidden' name='s_uclass'     value='<%=ss_uclass%>'>
    <input type='hidden' name='s_subjcourse' value='<%=ss_subjcourse%>'>
    <input type='hidden' name='s_subjseq'    value='<%=ss_subjseq%>'>
    <input type='hidden' name='s_isclosed'   value='<%=ss_isclosed%>'>

    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_year"    value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
    <input type="hidden" name="p_hsubjnm" value="<%=v_hsubjnm%>">
    <input type="hidden" name="p_seq"     value="<%=v_seq%>">
    <input type="hidden" name="p_pageno"     value="<%=v_pageno%>">

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

        <br>
        <!----------------- 공지사항관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= v_indate %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>구분</strong></td>
            <td height="25" class="table_02_2"><%= v_typesnm %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><%= v_title %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="50" class="table_02_2"><br><%= v_content %><br></td>
          </tr>
		  <tr>
			<td class = "table_title" align = "center">첨부파일</td>
			<td class = "table_02_2"  align = "left" >
	              <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %> 
			</td>
		  </tr>
        </table>
       <!----------------- 공지사항관리 끝 ----------------->

        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href="javascript:modify_gong()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
			<td width=4></td>
            <td align="center"><a href="javascript:delete_gong()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
			<td width=4></td>
            <td align="center"><a href="javascript:list_gong()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
