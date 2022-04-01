<%
//**********************************************************
//  1. 제      목 : za_QnaCourse_A.jsp
//  2. 프로그램명 : za_QnaCourse_A.jsp
//  3. 개      요 :  Qna Course 답변화면
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 이연정 2005. 7. 14
//  7. 수      정 :
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
    if (box == null) box = RequestManager.getBox(request);
	String  v_action      = box.getString("p_action");
    String s_userid       = box.getSession("userid");
    String s_name         = box.getSession("name");
	int     v_seq         = box.getInt("p_seq");
	String  v_subj        = box.getString("p_subj");
	String  v_year        = box.getString("p_year");
	String  v_subjseq     = box.getString("p_subjseq");
	String  v_type        = box.getString ("p_type");
//	String  v_categorycd     = box.getString("p_categorycd");

	//int v_seq = box.getInt("p_seq");
	String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
	String v_select = box.getString("p_select"); 

	
	String  v_searchStart = box.getString("p_searchStart");
	String  v_searchEnd   = box.getString("p_searchEnd");
	String  v_startdate   = box.getString("p_startdate");
	String  v_enddate     = box.getString("p_enddate");

	int v_pageno = box.getInt("p_pageno");

	DataBox dbox = (DataBox)request.getAttribute("selectQnaCourse");
	String v_inuserid   = dbox.getString("d_inuserid");
	String v_name       = dbox.getString("d_name");
	String v_indate     = dbox.getString("d_indate");
	String v_title      = dbox.getString("d_title");
	String v_contents   = dbox.getString("d_contents");
	String v_grcode     = dbox.getString("d_grcode");
	String v_grcodenm   = dbox.getString("d_grcodenm");
	String v_okyn1      = dbox.getString("d_okyn1");
	String v_okuserid1  = dbox.getString("d_okuserid1");
	String v_okdate1    = dbox.getString("d_okdate1");
	String v_okyn2      = dbox.getString("d_okyn2");
	String v_okuserid2  = dbox.getString("d_okuserid2");
	String v_okdate2    = dbox.getString("d_okdate2");
	String v_scsubjnm   = dbox.getString("d_scsubjnm");
	String v_subjseqgr  = dbox.getString("d_subjseqgr");
	String v_categorycd = dbox.getString("d_categorycd");
	String v_categorynm = dbox.getString("d_categorynm");
	String v_lesson     = dbox.getString("d_lesson");
	String v_repstatus   = dbox.getString("d_okyn1").equals("") ? "1" : dbox.getString("d_okyn1");

  	String contents 	= "";
  	String width	= "540";
  	String height	= "300";
  	
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
	String pp_orderType      = box.getString("p_orderType");  
	String pp_pageno        = box.getString("p_pageno");  	
	//Relative End
	
	String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

%>

<html>
<head>
<title>수료처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
<!--

	//조회화면으로 이동
	function listpage() {
		document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
		document.form1.p_process.value = "selectList";

		document.form1.submit();
	}

    // 등록 저장
    function insert() {

        if (document.form1.p_title.value == "") {
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

		document.form1.contents.value = document.form1.Wec.MIMEValue;
		document.form1.contents.value.replace("&","&amp;");
		
		//alert(document.form1.contents.value);
		//return;
		
        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }
        document.form1.action = "/servlet/controller.homepage.QnaAdminServlet";
        document.form1.p_process.value = "replyCourse";
        document.form1.submit();
    }

	function realsize( value ) {//입력글자수 체크
		var len = 0;
		if ( value == null ) return 0;
		for(var i=0;i<value.length;i++){
			var c = escape(value.charAt(i));
			if ( c.length == 1 ) len ++;
			else if ( c.indexOf("%u") != -1 ) len += 2;
			else if ( c.indexOf("%") != -1 ) len += c.length/3;
		}
		return len;
	}


//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype="multipart/form-data">
<input type="hidden" name="p_seq"         value= "<%= v_seq %>">
<input type="hidden" name="p_subj"        value= "<%= v_subj %>">
<input type="hidden" name="p_year"        value= "<%= v_year %>">
<input type="hidden" name="p_subjseq"     value= "<%= v_subjseq %>">
<input type="hidden" name="p_grcode"      value= "<%= v_grcode %>">
<input type="hidden" name="p_searchtext"  value= "<%= v_searchtext %>">
<input type="hidden" name="p_select"      value= "<%= v_select %>">
<input type="hidden" name="p_pageno"      value= "<%= v_pageno %>">
<input type="hidden" name="p_process"     value= "">
<input type="hidden" name="p_userid"      value= "<%= v_inuserid %>">
<input type="hidden" name="p_type"        value= "<%=v_type%>">
<input type="hidden" name="p_categorycd"  value= "<%=v_categorycd%>">
<input type="hidden" name="p_lesson"      value= "<%=v_lesson%>">
<input type="hidden" name="p_action"      value="">
<input type="hidden" name="contents"      value="">
<input type = "hidden" name = "s_grcode"      value = "<%=box.getString("s_grcode")%>">

<input type = "hidden" name = "p_searchStart" value = "<%=v_searchStart%>">
<input type = "hidden" name = "p_searchEnd"   value = "<%=v_searchEnd%>">
<input type = "hidden" name = "p_startdate" value = "<%=v_startdate %>">
<input type = "hidden" name = "p_enddate"   value = "<%=v_enddate %>">

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


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/homepage/homepage_title12.gif" ></td>

        </tr>
      </table>
	  <br>
	        <!----------------- title 끝 ----------------->
	 <table width="97%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
		<td  class=sub_title>Q&A 과정질문방 답변등록</td>
		<td align="right"></td>
		<td align="right"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a>
		<a href="javascript:history.back()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
		</td>
	  </tr>
	</table>

	<!----------------- 등록된 화면보기 ----------------->
  <table cellspacing="1" cellpadding="5" class="table_out">

    <tr> 
        <td width="13%" height="25" class="table_title"><b> 구분 </b></td>
        <td width="35%" class="table_02_2">
				<% if("PQ".equals(v_type)) {
						out.print("홈페이지");
					} else if("CU".equals(v_type)){
						out.print("운영자에게");
					} else {
						out.print("과정질문방");
					}

				%> 
				</td>
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
        <td class="table_title" class="table_title">제목 </td>
        <td class="table_02_2" colspan="3"><%= v_title %></td>
      </tr>
      <tr> 
        <td class="table_title" class="table_title">작성자 </td>
        <td class="table_02_2" colspan="3"><%= v_inuserid %></td>

      </tr>
      <tr> 
        <td class="table_title" class="table_title">내용 </td>
        <td class="table_02_2" colspan="3"><%=v_contents%></td>

      </tr>
      <tr> 
        <td width="13%" height="25" class="table_title">분류</td>
        <td class="table_02_2" colspan="3"><%=v_categorynm%></td>
      </tr>
    </table>
          
		<br>

		<!----------------- 답변내용 입력하기 ----------------->
    <table cellspacing="1" cellpadding="5" class="table_out">
      <tr> 
        <td width="13%" height="25" class="table_title"><b> 작성자 </b></td>
        <td class="table_02_2"><%=s_name%></td>
        <td width="13%" height="25" class="table_title">답변여부</td>
        <td class="table_02_2">
          <select name="p_repstatus">
            <!--option value= "1" <%=v_repstatus.equals("1") ? " selected " : "" %> >열람전</option-->
            <option value= "2" <%=v_repstatus.equals("2") ? " selected " : "" %> >답변준비중</option>
            <option value= "3" <%=v_repstatus.equals("3") ? " selected " : "" %> >답변완료</option>
          </select>
        </td>
      </tr>
      <tr> 
        <td class="table_title" class="table_title">제목 </td>
        <td class="table_02_2" colspan="3"><input type="text"  size="70" name="p_title" class="input" value=""></td>
      </tr>
      <tr> 
        <td class="table_title" class="table_title">내용 </td>
        <td class="table_02_2" colspan="3">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->  
					</td>
        </tr>
        <tr> 
          <td class="table_title" class="table_title">파일첨부 </td>
          <td class="table_02_2" colspan="3">
            <%
            int    i_fileLimit         = QnaAdminBean.getFILE_LIMIT();               // 제한 첨부 파일수
            %>
            <%@ include file="/learn/admin/include/za_MultiAttach_I.jsp" %> 
          </td>
        </tr>
      </table>
			<br>
			<table width="97%" border="0" cellspacing="0" cellpadding="0">
				<tr>
						<td align="right"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a>
						<a href="javascript:history.back()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
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
