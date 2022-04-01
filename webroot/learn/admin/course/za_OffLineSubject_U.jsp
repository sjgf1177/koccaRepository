<%
//**********************************************************
//  1. 제      목: OffLineSubject
//  2. 프로그램명 : za_OffLineSubject_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 하경태 2005.12.17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");

    int  v_pageno      = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  ss_grseq     = box.getString("s_grseq");           //교육차수

    String s_username  = box.getSession("name");
	String v_grcode = box.getStringDefault("p_grcode","N000001");
	String v_gubunNm = "";

    if(v_grcode.equals("N000001"))
	{
		v_gubunNm = "kocca";
	}
	else
	{
		v_gubunNm = "game";
	}

    String v_server   = conf.getProperty(v_gubunNm + ".url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

    String  s_gadmin    = box.getSession("gadmin");
    String v_gadmin ="";
    if(!s_gadmin.equals("")){
      v_gadmin = s_gadmin.substring(0,1);
    }

	int v_seq = 0;
    String v_subjgubun	= "";
    String v_subj		= "";
    String v_subjnm		= "";
    String v_subjseq	= "";
    String v_propstart	= "";
    String v_propend	= "";
    String v_dday		= "";
    String v_starttime	= "";
    String v_endtime	= "";
    String v_place		= "";
    String v_gubunnm	= "";
    String v_content	= "";
    String v_ldate		= "";
    String v_tname		= "";
	String v_useyn  	= "";
	String v_limitmember = "";
	String v_target = "";

	DataBox dbox = (DataBox)request.getAttribute("selectHomePageBoard");
    if (dbox != null) {
		v_seq			= dbox.getInt("d_seq");
        v_subjgubun		= dbox.getString("d_subjgubun");
        v_subj			= dbox.getString("d_subj");
        v_subjnm		= dbox.getString("d_subjnm");
        v_subjseq		= dbox.getString("d_subjseq");
        v_propstart		= dbox.getString("d_propstart");
        v_propend		= dbox.getString("d_propend");
        v_dday			= dbox.getString("d_dday");
        v_starttime		= dbox.getString("d_starttime");
        v_endtime		= dbox.getString("d_endtime");
        v_place			= dbox.getString("d_place");
        v_gubunnm		= dbox.getString("d_gubunnm");
        v_content		= dbox.getString("d_content");
        v_ldate			= dbox.getString("d_ldate");
        v_tname			= dbox.getString("d_tname");
		v_useyn   		= dbox.getString("d_useyn");
		v_target   		= dbox.getString("d_target");
		v_limitmember	= dbox.getString("d_limitmember");
		v_grcode		= dbox.getString("d_grcode");
	}

	// 날짜 처리
	if(v_dday.equals("")){v_dday = "";}
	else{v_dday     = v_dday.substring(0,8);}

	if(v_propstart.equals("")){v_propstart = "";}
	else{v_propstart     = v_propstart.substring(0,8);}

	if(v_propend.equals("")){v_propend = "";}
	else{v_propend     = v_propend.substring(0,8);}

%>
<html>
    <head>
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
	$("#p_dday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propstart").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propend").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
	//과정 선택시
	function whenSelection(p_action) {
		  document.form1.p_process.value = 'insertPage';


		  document.form1.action = "/servlet/controller.course.OffLineSubjectServlet";
		  document.form1.submit();
		}
	//저장
    function insert_check()
		{
        document.form1.p_content.value = document.form1.Wec.MIMEValue;
        document.form1.p_content.value.replace("&","&amp;");

		if (document.form1.s_subjgubun.value == "----") {
            alert("분류를 선택하세요");
            document.form1.s_subjgubun.focus();
            return;
        }

        if (blankCheck(document.form1.p_dday.value)) {
            alert("일시를 입력하세요");
            document.form1.p_dday.focus();
            return;
        }
		if (blankCheck(document.form1.p_propstart.value)) {
            alert("신청 시작일을 입력하세요");
            document.form1.p_propstart.focus();
            return;
        }
		if (blankCheck(document.form1.p_propend.value)) {
            alert("신청 종료일을 입력하세요");
            document.form1.p_propend.focus();
            return;
        }
		if (blankCheck(document.form1.p_place.value)) {
            alert("장소를 입력하세요");
            document.form1.p_place.focus();
            return;
        }
		if (blankCheck(document.form1.p_limitmember.value)) {
            alert("인원을 입력하세요");
            document.form1.p_limitmember.focus();
            return;
        }
		if (blankCheck(document.form1.p_subjnm.value)) {
            alert("강의명을 입력하세요");
            document.form1.p_subjnm.focus();
            return;
        }
		if (blankCheck(document.form1.p_tname.value)) {
            alert("담당교수를 입력하세요");
            document.form1.p_tname.focus();
            return;
        }
        if (document.form1.p_content.value.length < 3) {
	    	alert("내용을 입력하세요");
	    	return;
	    }

        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.course.OffLineSubjectServlet";
        document.form1.p_process.value = "update";

        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.course.OffLineSubjectServlet";
        document.form1.p_process.value = "listPage";
        document.form1.submit();
    }
	//구분선택
	function whenGubun()
	{
		if(document.form1.s_subjgubun.value == "00000000000000000002")
		{
			document.getElementById("tr_subj").style.display = "";
		}
		else
		{
			document.getElementById("tr_subj").style.display = "none";
		}
	}

	//파일첨부에 쓰기 방지
	function File_CancelKeyInput()
	{
			if(event.keyCode == 9){	// Tab key만 허용
				return true;
			}else{
				alert('[찾아보기] 버튼을 이용해 첨부하실 파일을 선택하세요.\n\n경로를 수동으로 잘못 입력하실 경우 업로드가 제대로 이루어지지 않습니다.');
				return false;
			}
		}

	// 날자 삭제(달력)
	function whenclick(caldate) {
		caldate.value = "";
	}

	// 템플릿 ini 파일 로드
    function initPage() {
        document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";
		document.form1.Wec.Value = "<%=v_content %>";
		if(document.form1.s_subjgubun.value == "00000000000000000002")
		{
			document.getElementById("tr_subj").style.display = "";
		}
    }
	//숫자만 입력 가능
	function numcheck(oTarget)
	{
		if(((event.keyCode < 48) || (event.keyCode > 57)) && ((event.keyCode < 96) || (event.keyCode > 105)))
			if((event.keyCode == 9) || (event.keyCode == 8) || (event.keyCode == 46))
				event.returnValue = true;
			else
				event.returnValue = false;
	}
	//과정 검색
	function searchMuser() {
	  var p_gubun = 'name';
	  var p_key2  = 'p_muser';
	  var url = "/servlet/controller.library.SearchServlet?p_process=subj";
	  open_window("",url,"0","0","100","100");
	}
	//검색된 과정 받기
	function receiveSubj(subj , subjnm, tmp1, tmp2, tmp3)
	{
		document.form1.p_subj.value = subj;
		document.form1.p_subjnm.value = subjnm;
		document.getElementById("div_subjnm").innerHTML = "<input type='text' name='p_subj' value='" +subjnm + "' size='100' class='input' >";
	}

//-->
</SCRIPT>
	</HEAD>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="initPage()">

<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_seq" value = "<%=v_seq %>">
    <input type = "hidden" name="p_content"   >
    <input type = "hidden" name="p_subj"   >
    <input type = "hidden" name="s_grcode"    value = "<%= v_grcode %>">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
            <td><img src="/images/admin/course/tit_offlineopen.gif" border="0"></td>
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
		  <tr>
            <td width="15%" class="table_title"><strong>분류</strong></td>
            <td height="25" class="table_02_2">
				<select name="s_subjgubun" onChange="whenGubun()">
					<%
						int j = 0;
						String v_gubuncode = "";
						String v_gubuncodenm = "";

						ArrayList list2 = (ArrayList)OffLineSubjectBean.getOffGubun(box);

						for ( j=0; j < list2.size(); j++)
						{
							DataBox dbox2 = (DataBox)list2.get(j);

							v_gubuncode		= dbox2.getString("d_code");
							v_gubuncodenm	= dbox2.getString("d_codenm");
						%>
							<option value="<%=v_gubuncode%>" <% if(v_gubuncode.equals(v_subjgubun)) { %> selected <%}%> ><%=v_gubuncodenm%></option>
						<%
						}
					%>
				</select >
			</td>
          </tr>
         <tr id="tr_subj" style="display:none">
            <td width="15%" class="table_title"><strong>과정/전문가</strong></td>
            <td height="25" class="table_02_2">
				<iframe name="ifrm_subj" style="width:800px; height:100px;" frameborder="0"  src="/servlet/controller.course.OffLineSubjectServlet?p_process=ifrme&p_formname=form1"></iframe>
				<input type="hidden" name="p_subjseq">
				<input type="hidden" name="p_subj">
			</td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>일시</strong></td>
            <td height="25" class="table_02_2">
				<input name="p_dday" id="p_dday" type="text" class="datepicker_input1" size="10" value="<%=v_dday%>">
				<select name="p_starttime">
                  <%=FormatDate.getDateOptions(0,23,Integer.parseInt(v_starttime))%>
                </select>
                시 ~
				<select name="p_endtime">
                  <%=FormatDate.getDateOptions(0,23,Integer.parseInt(v_endtime))%>
                </select>
                시</td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>신청기간</strong></td>
            <td height="25" class="table_02_2">
				<input name="p_propstart" id="p_propstart" type="text" class="datepicker_input1" size="10" value="<%=v_propstart%>">
				~ <input name="p_propend" id="p_propend" type="text" class="datepicker_input1" size="10" value="<%=v_propend%>"></td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>장소</strong></td>
            <td height="25" class="table_02_2">
				<input type="text" name="p_place" size="100" class="input" value="<%=v_place%>"></td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>인원</strong></td>
            <td height="25" class="table_02_2">
				<input type="text" name="p_limitmember" size="3" class="input" onKeydown="numcheck(this);" value="<%=v_limitmember%>">명</td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>강의명</strong></td>
            <td height="25" class="table_02_2">
				<div id="div_subjnm"><input type="text" name="p_subjnm" size="100" class="input" value="<%=v_subjnm%>"></div>
			</td>
          </tr>
		   <tr>
            <td width="15%" class="table_title"><strong>담당교수</strong></td>
            <td height="25" class="table_02_2">
				<input type="text" name="p_tname" size="100" class="input" value="<%=v_tname%>"></td>
          </tr>
		   <tr>
            <td width="15%" class="table_title"><strong>대상</strong></td>
            <td height="25" class="table_02_2">
				<select name="p_target">
					<option value="A" <% if(v_target.equals("A")) {%> selected <% }%> >전회원</option>
					<option value="S" <% if(v_target.equals("S")) {%> selected <% }%> >과정수강자</option>
					<option value="P" <% if(v_target.equals("P")) {%> selected <% }%> >기수강자+과정수강자</option>
				</select></td>
          </tr>

          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2"><!--textarea name="p_adcontent" cols="110" rows="10" -->
                <!-- 나모 Editor  -->
                <p align="left">
							<script language = "javascript" src = "/script/user_patch.js"></script>
							<script language='javascript'>object_namopatch('684','500');</script>
                <!--OBJECT WIDTH="0" HEIGHT="0" CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                   <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
                </OBJECT>
                <OBJECT ID="Wec" WIDTH="684" HEIGHT="500"
                CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
                <param name="InitFileURL" value="/namo/namowec.env">
                <param name="InitFileVer" value="1.0">
                </OBJECT-->
                </p>
            <!--/textarea-->
            </td>
          </tr>
		  <tr>
            <td width="15%" class="table_title"><strong>특강 자료실<br>사용유무</strong></td>
            <td height="25" class="table_02_2">
              사용<input type="radio" name="p_useyn" value='Y' class="input"  <% if(v_useyn.equals("Y")) {%> checked <% }%> >&nbsp;
              미사용<input type="radio" name="p_useyn" value='N' class="input" <% if(v_useyn.equals("N")) {%> checked <% }%>>&nbsp;
            </td>
          </tr>

        </table>
        <!----------------- 공지사항 관리 끝 ----------------->

        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->


      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
