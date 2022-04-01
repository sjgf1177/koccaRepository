<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Letter_U.jsp
//  3. 개      요: 공지사항 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.dunet.common.util.*" %>  
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_selcomp  = box.getString("p_selcomp");

    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
//    String  v_comp    = box.getString("p_comp");
    int v_tabseq   = box.getInt("p_tabseq");
    int     v_seq          = box.getInt("p_seq");
    int v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_compcd	    = "";           // 대상회사
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    String v_popup   = "";              //팝업공지여부
    String v_uselist  = "";              //
    String v_useframe = "";
    String v_isall    = "";              //
    String  v_upfile    = "";
    String  v_upfile_value    = "";
    String  v_grcodecd  ="";
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    //NoticeData data = (NoticeData)request.getAttribute("selectNotice");
    DataBox dbox = (DataBox)request.getAttribute("selectNotice");
    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
		v_startdate = dbox.getString("d_startdate");
		v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
        v_cnt       = dbox.getInt("d_cnt");
		v_loginyn   = dbox.getString("d_loginyn");
		v_useyn   	= dbox.getString("d_useyn");
		v_compcd	= dbox.getString("d_compcd");
		v_popwidth	= dbox.getInt("d_popwidth");
		v_popheight = dbox.getInt("d_popheight");
		v_popxpos   = dbox.getInt("d_popxpos");
		v_popypos   = dbox.getInt("d_popypos");
		v_popup     = dbox.getString("d_popup");
		v_uselist   = dbox.getString("d_uselist");
		v_useframe  = dbox.getString("d_useframe");
		v_isall     = dbox.getString("d_isall");
		v_grcodecd  = dbox.getString("d_grcodecd");
		v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
    }
    v_adcontent = StringManager.replace(v_adcontent, "&nbsp;","&amp;nbsp;" );
    String s_username  = box.getSession("name");

    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;


    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }
    
    int fileCnt = v_realfileVector != null ? v_realfileVector.size() : 0 ;
%>


<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--

    function insert_check() {
        var st_date = make_date(document.form1.p_sdate.value);
        var ed_date = make_date(document.form1.p_edate.value);
        
       // var chkselectcnt = chkSelected();
        var chkselectcntgrcode = chkSelectedgrcode();

        document.form1.p_adcontent.value = document.form1.Wec.MIMEValue;
        document.form1.p_adcontent.value.replace("&","&amp;");

       // alert(document.form1.p_grocdecd.value);
       // return;

        if (blankCheck(document.form1.p_adtitle.value)) {
            alert("제목을 입력하세요");
            document.form1.p_adtitle.focus();
            return;
        }
        if (realsize(document.form1.p_adtitle.value) > 200) {
            alert("제목은 한글기준 2000자를 초과하지 못합니다.");
            document.form1.p_adtitle.focus();
            return;
        }

        if (blankCheck(document.form1.p_adcontent.value)) {
            alert("내용을 입력하세요");
            document.form1.p_adcontent.focus();
            return;
        }

        <%if(!v_gadmin.equals("K")){   //회사관리자가 아닐때%>
        if(document.form1.p_login[0].checked == true || document.form1.p_login[1].checked == true ){
          if(chkselectcntgrcode < 1){
            alert("대상교육그룹을 선택하여주십시오");
            return;
          }
        }
        <%}%>


        if(document.form1.p_isAll.checked == true){
            document.form1.p_isAllvalue.value = 'Y';
        }else {
            document.form1.p_isAllvalue.value = 'N';
        }


        if(document.form1.p_popup[0].checked == true){

          if(st_date > ed_date){
            alert("시작일이 종료일보다 큽니다.");
            return;
          }

          if (document.form1.p_popsize1.value == "") {
            alert("가로사이즈를 입력하여주십시오");
            document.form1.p_popsize1.focus();
            return;
          }

          if (!number_chk_noalert(document.form1.p_popsize1.value)) {
            alert('가로사이즈가 잘못입력되었습니다.');
            return;
          }

          if (document.form1.p_popsize2.value == "") {
            alert("세로사이즈를 입력하여주십시오");
            document.form1.p_popsize2.focus();
            return;
          }

          if (!number_chk_noalert(document.form1.p_popsize2.value)) {
            alert('세로사이즈가 잘못입력되었습니다.');
            return false;
          }

          if (document.form1.p_popposition1.value == "") {
            alert("X축 값을 입력하여주십시오");
            document.form1.p_popposition1.focus();
            return;
          }

          if (!number_chk_noalert(document.form1.p_popposition1.value)) {
            alert('X축 값이 잘못입력되었습니다.');
            return false;
          }

          if (document.form1.p_popposition2.value == "") {
            alert("Y축 값을 입력하여주십시오");
            document.form1.p_popposition2.focus();
            return;
          }

          if (!number_chk_noalert(document.form1.p_popposition2.value)) {
            alert('Y축 값이 잘못입력되었습니다.');
            return false;
          }

          if(document.form1.p_uselist.checked == true){
            document.form1.p_uselist.value = 'Y';
          }
          if(document.form1.p_useframe.checked == true){
            document.form1.p_useframe.value = 'Y';
          }

        }

        document.form1.p_popsize1.disabled = false;
        document.form1.p_popsize2.disabled = false;

        document.form1.p_startdate.value  = st_date;
        document.form1.p_enddate.value    = ed_date;
        document.form1.p_pageno.value = 1;
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.LetterAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.LetterAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }


  //팝업미리보기
    function popUpPreview(){
         var popupObj = document.getElementsByName("p_popup");
            var popupObjLen = popupObj.length;

            for(var i = 0 ; i < popupObjLen ; i ++) {
                if(popupObj[i].checked && popupObj[i].value == "N") {
                    alert("팝업 설정을 하셔야 미리 보기가 가능합니다.");
                    return;
                }
            }
                
            var  width  = document.form1.p_popsize1.value;
            var  height = document.form1.p_popsize2.value;
            var  left   = document.form1.p_popposition1.value;
            var  top    = document.form1.p_popposition2.value;

            window.self.name = "CurrentPage";
            open_window("openPopUpView","",left,top,width,height, "no","no","no","yes","no");  

            document.form1.p_content.value = document.form1.Wec.MIMEValue;
            document.form1.p_content.value.replace("&","&amp;");

            document.form1.target = "openPopUpView";
            document.form1.action='/servlet/controller.homepage.EventAdminServlet';
            document.form1.p_process.value = 'popupView';
            document.form1.submit();
            document.form1.target = window.self.name;
    
    }

	//로긴구분 선택시
	function whenLoginSelect(){
	  if(document.form1.p_login[2].checked == true) { //로그인전
          if (grcodeFrame.grcodeform.p_grcode.length > 0) {
            for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
              grcodeFrame.grcodeform.p_grcode[i].checked = false;
              grcodeFrame.grcodeform.p_grcode[i].disabled = true;
            }
          } else {
              grcodeFrame.grcodeform.p_grcode.checked = false;
              grcodeFrame.grcodeform.p_grcode.disabled = true;
          }
      }

      else if(document.form1.p_login[0].checked == true || document.form1.p_login[1].checked == true){ //로그인후
          if (grcodeFrame.grcodeform.p_grcode.length > 0) {
            for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
              grcodeFrame.grcodeform.p_grcode[i].checked = false;
              grcodeFrame.grcodeform.p_grcode[i].disabled = false;
            }
          } else {
              grcodeFrame.grcodeform.p_grcode.checked = false;
              grcodeFrame.grcodeform.p_grcode.disabled = false;
          }
      }
	}

	//체크박스 체크
    function chkSelectedgrcode() {
      var selectedcnt = 0;

      document.form1.p_grocdecd.value = "";

        if (grcodeFrame.grcodeform.p_grcode.length > 0) {
          for (i=0; i<grcodeFrame.grcodeform.p_grcode.length; i++) {
            if (grcodeFrame.grcodeform.p_grcode[i].checked == true) {
              selectedcnt++;
              document.form1.p_grocdecd.value += grcodeFrame.grcodeform.p_grcode[i].value+",";
            }
          }
        } else {
          if (grcodeFrame.grcodeform.p_grcode.checked == true) {
            selectedcnt++;
            document.form1.p_grocdecd.value += grcodeFrame.grcodeform.p_grcode.value+",";
          }
        }
      <%if(v_gadmin.equals("K")){   //회사관리자가 일때%>
          document.form1.p_grocdecd.value = "<%=v_grcodecd%>,";
          return 1;
      <%} else {%>
          return selectedcnt;
      <%}%>
    }



	//팝업여부선택시
	function whenPopupSelect(){
	  if(document.form1.p_popup[0].checked == true) { //Y

	    document.form1.p_popsize1.value = "686";
	    document.form1.p_popsize2.value = "600";
	    document.form1.p_popposition1.value = "0";
	    document.form1.p_popposition2.value = "0";
	    document.form1.p_sdate.value = "";
	    document.form1.p_edate.value = "";

	    document.form1.p_popsize1.disabled = false;
	    document.form1.p_popsize2.disabled = false;
	    document.form1.p_popposition1.disabled = false;
	    document.form1.p_popposition2.disabled = false;
	    document.form1.p_sdate.disabled = false;
	    document.form1.p_edate.disabled = false;
	    document.form1.p_useframe.disabled = false;
	    document.form1.p_uselist.disabled = false;

	  }

	  else if(document.form1.p_popup[1].checked == true){ //N
	    document.form1.p_popsize1.value = "";
	    document.form1.p_popsize2.value = "";
	    document.form1.p_popposition1.value = "";
	    document.form1.p_popposition2.value = "";
	    document.form1.p_sdate.value = "";
	    document.form1.p_edate.value = "";

	    document.form1.p_popsize1.disabled = true;
	    document.form1.p_popsize2.disabled = true;
	    document.form1.p_popposition1.disabled = true;
	    document.form1.p_popposition2.disabled = true;
	    document.form1.p_sdate.disabled = true;
	    document.form1.p_edate.disabled = true;
	    document.form1.p_useframe.disabled = true;
	    document.form1.p_uselist.disabled = true;

	  }
	}

	function whenUseframe(){
	  if(document.form1.p_useframe.checked == true){ //N
	    document.form1.p_popsize1.disabled = false;
	    document.form1.p_popsize2.disabled = false;
	    document.form1.p_popsize1.value = "";
	    document.form1.p_popsize2.value = "";
	  }else{
	    document.form1.p_popsize1.disabled = true;
	    document.form1.p_popsize2.disabled = true;
	    document.form1.p_popsize1.value = "686";
	    document.form1.p_popsize2.value = "600";
	  }
	}

//-->


</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<form name="form1" method="post" enctype="multipart/form-data">
    <input type= "hidden" name= "p_process"    value= "<%= v_process %>">
    <input type= "hidden" name= "p_pageno"     value= "<%=v_pageno %>">
    <input type= "hidden" name= "p_pagesize"   value= "<%=v_pagesize %>">
    <input type= "hidden" name= "p_search"     value= "<%=v_search %>">
    <input type= "hidden" name= "p_searchtext" value= "<%=v_searchtext %>">
    <input type= "hidden" name= "p_seq"        value= "<%=v_seq %>">
	<input type= "hidden" name= "p_tabseq"     value= "<%=v_tabseq %>">
    <input type= "hidden" name= "p_startdate"  value= "">
    <input type= "hidden" name= "p_enddate"    value= "">
    <input type= "hidden" name= "p_compcd"     value= "">
    <input type= "hidden" name="p_grocdecd"    value= "">
	<input type= "hidden" name= "p_selcomp"    value= "<%=v_selcomp%>">
	<input type= "hidden" name= "p_adcontent"  value= "<%=StringUtil.convertHtmlchars(v_adcontent)%>">
	<input type= "hidden" name= "p_content"    value= "">
	<input type= "hidden" name= "p_isAllvalue" value= "">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
            <td><img src="/images/admin/homepage/tit_news.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- 공지사항 관리 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td height="25" class="table_02_2"><%=s_username%></td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>전체공지여부</strong></td>
            <td height="25" class="table_02_2">
              <input type="checkbox" name="p_isAll" value='Y' <%if(v_isall.equals("Y")){%>checked<%}%>> 전체공지(상단걸기)사용시 선택하여주십시오.
            </td>
          </tr>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>로그인유무</strong></td>
            <td height="25" class="table_02_2">
			   <input type="radio" name="p_login" value='AL' onclick="javascript:whenLoginSelect()" <%if (v_loginyn.equals("AL")){%>checked<%}%>>전체&nbsp;
			   <input type="radio" name="p_login" value='N'  onclick="javascript:whenLoginSelect()" <%if (v_loginyn.equals("N")) {%>checked<%}%>>로그인전&nbsp;
               <input type="radio" name="p_login" value='Y'  onclick="javascript:whenLoginSelect()" <%if (v_loginyn.equals("Y")) {%>checked<%}%>>로그인후&nbsp;
			</td>
          </tr>
          <%if(!v_gadmin.equals("K")){   //회사관리자가 아닐때%>
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>대상교육그룹</strong></td>
            <td height="25" class="table_02_2">
            <iframe name="grcodeFrame" width="850" height="80"  frameborder="0"  src="/servlet/controller.homepage.LetterAdminServlet?p_process=grcodelist&p_grcodecd=<%=v_grcodecd%>&p_loginyn=<%=v_loginyn%>"></iframe>
			</td>
          </tr>
          <%}else{%>
              <input type="hidden" name="p_login"  value='Y' >
              <iframe name="grcodeFrame" width="0" height="0"  frameborder="0"  src="/servlet/controller.homepage.LetterAdminServlet?p_process=grcodelist&p_grcodecd=<%=v_grcodecd%>&p_loginyn=<%=v_loginyn%>"></iframe>
          <%}%>
          <tr>
            <td width="15%" class="table_title"><strong>팝업여부</strong></td>
            <td height="25" class="table_02_2">
              <input type="radio" name="p_popup" value='Y' <%if (v_popup.equals("Y")){%>checked<%}%> onclick="javascript:whenPopupSelect()">Y&nbsp;
              <input type="radio" name="p_popup" value='N' <%if (v_popup.equals("N")){%>checked<%}%> onclick="javascript:whenPopupSelect()">N&nbsp;
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>팝업설정</b></td>
            <td class="table_02_2">
              <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_startdate,"yyyy-MM-dd")%>'>
              ~
              <input name="p_edate" id="p_edate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_enddate,"yyyy-MM-dd")%>'>
			  <br>
			  사이즈 : 가로 <input type="text" size="5" name="p_popsize1" class="input" value="<%=v_popwidth%>" maxlength="4"> / 세로 <input type="text" size="5" name="p_popsize2" class="input" value="<%=v_popheight%>" maxlength="4">
              <br>
			  위치   : x <input type="text" size="5" name="p_popposition1" class="input" value="<%=v_popxpos%>" maxlength="3"> / y <input type="text" size="5" name="p_popposition2" class="input" value="<%=v_popypos%>" maxlength="3">
			  <br>
			  <input type="checkbox" name="p_useframe" value='Y' onclick="javascript:whenUseframe()" <%if(v_useframe.equals("Y")){out.println("checked");}%>>작성내용만보이기&nbsp;
			  <input type="checkbox" name="p_uselist"  value='Y' <%if(v_uselist.equals("Y")){out.println("checked");}%>>홈페이지리스트사용&nbsp;
			  <br>
			  <a href="javascript:popUpPreview()"><img src="/images/admin/button/btn_preview.gif" border="0"></a>
            </td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>사용유무</strong></td>
            <td height="25" class="table_02_2">
              사용<input type="radio" name="p_use" value='Y' class="input" <%if (v_useyn.equals("Y")){%>checked<%}%>>&nbsp;
              미사용<input type="radio" name="p_use" value='N' class="input" <%if (v_useyn.equals("N")){%>checked<%}%>>&nbsp;
            </td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_adtitle" size = 100 class="input" value='<%= v_adtitle %>' maxlength="80"></td>          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2">
                <p align="left">
				<!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>"; namoActiveInitCompleted("p_adcontent");</script>
                <script language="javascript">object_namoActivepatch('100%','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
            </td>
          </tr>
			  <tr>
		  <td height="25" class="table_title">첨부파일</td>
            <td class="table_02_2"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="90%">
                  <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  Vector i_fileseqVector     = (Vector)dbox.getObject("d_fileseq");         // 파일 일련번호
                  
                  int    i_fileLimit         = NoticeAdminBean.getFILE_LIMIT();              // 제한 첨부 파일수
                   
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach.jsp" %> 
			      </td>
                  <td width="50">
                  </td>
                </tr>
              </table></td>
		  </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
      </td>
  </tr>
</table>

<script language="javascript">

<%if(!v_popup.equals("Y")){%>
        document.form1.p_popsize1.disabled = true;
	    document.form1.p_popsize2.disabled = true;
	    document.form1.p_popposition1.disabled = true;
	    document.form1.p_popposition2.disabled = true;
	    document.form1.p_sdate.disabled = true;
	    document.form1.p_edate.disabled = true;
	    document.form1.p_useframe.disabled = true;
	    document.form1.p_uselist.disabled = true;
<%} else if(!v_useframe.equals("Y")){%>
	    document.form1.p_popsize1.disabled = true;
	    document.form1.p_popsize2.disabled = true;
<% } %>
</script>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
