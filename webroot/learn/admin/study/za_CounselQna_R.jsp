<%
//**********************************************************
//  1. 제      목: 개인볅 학습현황  Qna 과정질문방
//  2. 프로그램명 : za_CounselQna_R.jsp
//  3. 개      요: Qna 상세보기화면
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.10.30
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box        = (RequestBox)request.getAttribute("requestbox");
	String  v_categorycd     = box.getString("p_categorycd");
	int     v_seq         = box.getInt("p_seq");
	String  v_subj        = box.getString("p_subj");
	String  v_year        = box.getString("p_year");
	String  v_subjseq     = box.getString("p_subjseq");
	String  v_type        = box.getString ("p_type");
	String  v_searchtext  = box.getString("p_searchtext");
	String  v_select      = box.getString("p_select");
	String  v_repkind     = "";
	int     v_repseq     = 0;

	int v_pageno = box.getInt("p_pageno");

	DataBox dbox = (DataBox)request.getAttribute("selectQnaCourse");
	String v_inuserid   = dbox.getString("d_inuserid");
	String v_lesson     = dbox.getString("d_lesson");
	String v_kind       = dbox.getString("d_kind");
	String v_name       = dbox.getString("d_name");
	String v_indate     = dbox.getString("d_indate");
	String v_title      = dbox.getString("d_title");
	String v_contents   = dbox.getString("d_contents");
	String v_grcodenm   = dbox.getString("d_grcodenm");
	String v_grcode     = dbox.getString("d_grcode");
	String v_okyn1      = dbox.getString("d_okyn1");
	String v_okuserid1  = dbox.getString("d_okuserid1");
	String v_okdate1    = dbox.getString("d_okdate1");
	String v_okyn2      = dbox.getString("d_okyn2");
	String v_okuserid2  = dbox.getString("d_okuserid2");
	String v_okdate2    = dbox.getString("d_okdate2");
	String v_scsubjnm   = dbox.getString("d_scsubjnm");
	String v_subjseqgr  = dbox.getString("d_subjseqgr");
	Vector v_realfileVector = (Vector)dbox.getObject("d_realfile");
	Vector v_savefileVector = (Vector)dbox.getObject("d_savefile");
	String v_realmotion  = dbox.getString("d_realmotion");
	String v_savemotion  = dbox.getString("d_savemotion");
	int    v_filecount   = v_realfileVector.size();

	QnaAdminBean hpqbean = new QnaAdminBean();

	//Relative Start
	String pp_searchStart	= box.getString("p_searchStart");
	String pp_searchEnd     = box.getString("p_searchEnd");
	String ss_gyear         = box.getString("s_gyear");
	String ss_grseq         = box.getString("s_grseq");
	String ss_upperclass    = box.getString("s_upperclass");
	String ss_middleclass   = box.getString("s_middleclass");
	String ss_lowerclass    = box.getString("s_lowerclass");
	String ss_subjcourse    = box.getString("s_subjcourse");
	String ss_subjseq       = box.getString("s_subjseq");
	String pp_order       	= box.getString("p_order");
	String pp_orderType     = box.getString("p_orderType");
	//Relative End


%>

<html>
<head>
<title>상담내역</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--

//리스트 페이지로 이동
function listpage() {
	document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
	document.form1.p_action.value = "go";

	if(document.form1.p_type.value == "ALL"){
	  document.form1.p_process.value = "selectList";
	}else{
	  document.form1.p_process.value = "selectListCourse";
	}

	//alert(document.form1.p_process.value);

	document.form1.submit();
}

//수정한 내용을 저장
function viewsave() {
	if(document.form1.p_categorycd[0].checked==true){
		alert("분류종류를 선택하세요");
		return;
	}else{
		if(document.form1.p_okyn1.checked == true){
			document.form1.p_okyn1.value = "Y";
		}else{
			document.form1.p_okyn1.value = "";
		}

		if(document.form1.p_okyn2.checked == true){
			document.form1.p_okyn2.value = "Y";
		}else{
			document.form1.p_okyn2.value = "";
		}

		//alert(document.form1.p_okyn1.value);
		//alert(document.form1.p_okyn2.value);

		document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
		document.form1.p_process.value = "viewCoursesave";
		document.form1.submit();
	}
}

//답변 페이지로 이동
function replyPage(v_categorycd) {
	/*if(v_categorycd =='00'){
		alert("분류가 아직 정해지지 않았습니다. 분류를 선택하세요");
		return;
	}else{*/
	document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
	document.form1.p_process.value = "replyCoursePage";

	document.form1.submit();
	//}
}

//뷰 화면 삭제
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
		document.form1.p_process.value = "deleteCourse";
		document.form1.submit();
	}
	else {
		return;
	}
}


//뷰 화면 삭제
function deleteRepAction(seq, kind) {
	if (confirm("정말로 삭제하시겠습니까?")) {

	    document.form1.p_repseq.value = seq;
	    document.form1.p_repkind.value = kind;

		document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
		document.form1.p_process.value = "repcoursedelete";
		document.form1.submit();
	}
	else {
		return;
	}
}


//답변수정
function editRepPage(seq, kind){
  document.form1.p_repseq.value = seq;
  document.form1.p_repkind.value = kind;

  document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
  document.form1.p_process.value = "repcourseupdatepage";
  document.form1.submit();
}


//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" >
<input type= "hidden" name= "p_seq"         value = "<%= v_seq %>">
<input type= "hidden" name= "p_subj"        value = "<%= v_subj %>">
<input type= "hidden" name= "p_year"        value = "<%= v_year %>">
<input type= "hidden" name= "p_subjseq"     value = "<%= v_subjseq %>">
<input type= "hidden" name= "p_kind"     value = "<%= v_kind %>">
<input type= "hidden" name= "p_searchtext"  value = "<%= v_searchtext %>">
<input type= "hidden" name= "p_select"      value = "<%= v_select %>">
<input type= "hidden" name= "p_pageno"      value = "<%= v_pageno %>">
<input type= "hidden" name= "p_process"     value = "">
<input type= "hidden" name= "p_userid"      value = "<%= v_inuserid %>">
<input type= "hidden" name= "p_action"      value="">
<input type= "hidden" name= "p_approval1"   value="<%=v_okyn1%>">
<input type= "hidden" name= "p_approval2"   value="<%=v_okyn2%>">
<input type= "hidden" name= "p_lesson"      value="<%=v_lesson%>">
<input type= "hidden" name= "p_grcode"      value="<%=v_grcode%>">
<input type= "hidden" name= "p_type"      value="<%=v_type%>">
<input type = "hidden" name = "s_grcode"      value = "<%=box.getString("s_grcode")%>">

<input type= "hidden" name= "p_repseq"         value = "">
<input type= "hidden" name= "p_repkind"         value = "">

		<!-- Relative Start -->
		<input type="hidden" name ="p_searchStart"   	value = "<%=pp_searchStart%>">
		<input type="hidden" name ="p_searchEnd"   	value = "<%=pp_searchEnd%>">
		<input type="hidden" name ="s_gyear"   		value = "<%=ss_gyear%>">
		<input type="hidden" name ="s_grseq"   		value = "<%=ss_grseq%>">
		<input type="hidden" name ="s_upperclass"   	value = "<%=ss_upperclass%>">
		<input type="hidden" name ="s_middleclass"   	value = "<%=ss_middleclass%>">
		<input type="hidden" name ="s_lowerclass"   	value = "<%=ss_lowerclass%>">
		<input type="hidden" name ="s_subjcourse"   	value = "<%=ss_subjcourse%>">
		<input type="hidden" name ="s_subjseq"   		value = "<%=ss_subjseq%>">
		<input type="hidden" name ="p_order"     value="<%=pp_order%>"">
        <input type="hidden" name ="p_orderType" value="<%=pp_orderType%>">
		<!-- Relative End -->


<table width="100%" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/study/tit_counsel.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 내용보기 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0" width="97%">
        <tr>
          <td align="left"><b>[질문보기]</b></td>
        </tr>
      </table>
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="13%" height="25" class="table_title"><b>상담구분 </b></td>
          <td width="35%" class="table_02_2">과정질문방</td>
          <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
          <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
        </tr>
        <tr>
          <td height="25" class="table_title">과정</td>
          <td class="table_02_2" ><%=v_scsubjnm%></td>
          <td class="table_title">차수</td>
          <td class="table_02_2"><%=StringManager.cutZero(v_subjseqgr)%></td>
        </tr>
        <tr>
          <td width="13%" height="25" class="table_title">교육그룹</td>
          <td class="table_02_2" colspan="3"><%=v_grcodenm%></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">첨부파일 </td>
          <td class="table_02_2" colspan="3">
                          	<%
                          		if( v_realfileVector != null ) {
                          			for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

                          				String v_realfile = (String)v_realfileVector.elementAt(i);
                          				String v_savefile = (String)v_savefileVector.elementAt(i);

                          				if(v_realfile != null && !v_realfile.equals("")) {  %>
                          					&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>

                          					<%= v_realfile%></a><br>
                          		<%		}   %>
                                <%
                                    }
                                }
                                %>
          </td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">작성자 </td>
          <td class="table_02_2" colspan="3"><%= v_name %>(<%= v_inuserid %>)</td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">제목 </td>
          <td class="table_02_2" colspan="3"><%= v_title %></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">내용 </td>
          <td class="table_02_2" colspan="3"><%=v_contents%></td>
        </tr>
        <!--tr>
            <td width="13%" height="25" class="table_title">분류</td>
            <td class="table_02_2" colspan="3"><%=QnaAdminBean.homepageGetQnaCategoryTable(v_categorycd)%></td>
        </tr>
	  <%
	  String okyn2str = "";


	  if(v_okyn1.equals("Y")){
		  if(v_okyn2.equals("Y")){
			okyn2str ="disabled checked";
		  }
	  } else {
			okyn2str ="disabled";

	  }

	  %>
        <tr>
          <td width="13%" height="25" class="table_title"><b> 확인1 </b>
            <input type = "checkbox" name="p_okyn1" <%=(v_okyn1.equals("Y") ? "disabled checked":"")%>>
          </td>
          <td width="35%" class="table_02_2"><%=v_okuserid1 %></td>
          <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(v_okdate1, "yyyy/MM/dd")%></td>
        </tr>
        <tr>
          <td width="13%" height="25" class="table_title"><b> 확인2 </b>
            <input type = "checkbox" name="p_okyn2" <%=okyn2str%>>
          </td>
          <td width="35%" class="table_02_2"><%=v_okuserid2 %></td>
          <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(v_okdate2, "yyyy/MM/dd")%></td>
        </tr-->
      </table>
      <br>
      <!----------------- 답변내용보기 시작 ----------------->
<%

	ArrayList list = (ArrayList)request.getAttribute("selectQnaCourseListA");
	ArrayList list1 = new ArrayList();
	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
	int v_cnt = 0;
	int v_dispnum = 0, v_totalpage = 0, v_rowcount = 1;
		if(list.size() != 0) {
			for(int i = 0; i < list.size(); i++) {
				DataBox dboxA = (DataBox)list.get(i);
				v_dispnum = dboxA.getInt("d_dispnum");
				v_inuserid   = dboxA.getString("d_inuserid");
				v_name   = dboxA.getString("d_name");
				v_cnt = dboxA.getInt("d_cnt");
				v_indate    = dboxA.getString("d_indate");
				v_title    = dboxA.getString("d_title");
				v_seq      = dboxA.getInt("d_seq");
                v_totalpage = dboxA.getInt("d_totalpage");
                v_rowcount = dboxA.getInt("d_rowcount");
                v_contents = dboxA.getString("d_contents");
				v_scsubjnm = dboxA.getString("d_scsubjnm");
				v_subjseqgr = dboxA.getString("d_subjseqgr");
				v_repseq = dboxA.getInt("d_seq");
				v_repkind = dboxA.getString("d_kind");
%>
      <br/>
      <table border="0" cellspacing="0" cellpadding="0" width="97%">
        <tr>
          <td align="left"><b>[답변보기]</b></td>
        </tr>
      </table>
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td width="13%" height="25" class="table_title"><b> 작성자 </b></td>
          <td width="35%" class="table_02_2"><%= v_name %>(<%=v_inuserid%>) </td>
          <td width="13%" height="25" class="table_title""><b> 날짜</b></td>
          <td class="table_02_2"><%=FormatDate.getFormatDate(v_indate, "yyyy/MM/dd")%></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">첨부파일 </td>
          <td class="table_02_2" colspan=3>
               <%
                    list1 = hpqbean.fileCourseList(v_subj,  v_year, v_subjseq , v_repseq, v_repkind);

               	   if(list1 != null){
               	      for(int ai= 0 ; ai<list1.size(); ai++){
               	         DataBox dboxf = (DataBox)list1.get(ai);
               	         String v_realfile = dboxf.getString("d_realfile");
               	         String v_savefile = dboxf.getString("d_savefile");
               	         if(v_realfile != null && !v_realfile.equals("")) {  %>
               	         	&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=v_savefile%>&p_realfile=<%=v_realfile%>'>

               	         	<%= v_realfile%></a><br>
               	         	<!--input type = "hidden" name = "p_savefile"  value = <%=v_savefile%>-->
               <%		 }

               	      }
               		}
               %>
          </td>
        </tr>
        <tr>
          <td height="25" class="table_title">과정</td>
          <td class="table_02_2" ><%=v_scsubjnm%></td>
          <td class="table_title">차수</td>
          <td class="table_02_2"><%=StringManager.cutZero(v_subjseqgr)%></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title"> 제목</td>
          <td class="table_02_2" colspan="3"><%=v_title%></td>
        </tr>
        <tr>
          <td class="table_title" class="table_title">내용 </td>
          <td class="table_02_2" colspan="3"><%=v_contents%></td>
        </tr>
      </table>
      <br>
<%}}%>
      <br>
      <table width="50%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="30%">&nbsp;</td>
          <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
          <td width="30%">&nbsp;</td>
        </tr>
      </table>
      <br/>
      <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
