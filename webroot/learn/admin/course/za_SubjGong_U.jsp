<%
//**********************************************************
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명 : za_SubjGong_U.jsp
//  3. 개      요: 과정차수별공지사항 과정차수별수정
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
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.dunet.common.util.*" %>  

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
	//int    i         = 0;
	int    v_cnt     = 0;
	String v_userid  = "";
	String v_name    = "";
	String v_indate  = "";
	String v_content = "";
	String v_upfileview = "";
	
	Vector v_realfileVector = null;
	Vector v_savefileVector = null;
	
    int v_pageno         = box.getInt("p_pageno");
	
  	String content 	= "";
  	String width	= "540";
  	String height	= "300";

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
    
    v_adcontent = StringManager.replace(v_content, "&nbsp;","&amp;nbsp;" );
    
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;

%>

<html>
<head>
<title>::: 공지사항 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function initPage()
    {
        //document.form1.Wec.value = document.form1.p_adcontent.value;
    	document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";
	   	
    }
    
    function insert_check() {
        if(blankCheck(document.form1.p_title.value)){
            alert("제목을 입력하세요");
            document.form1.p_title.focus();
            return;
        }
            
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

        document.form1.p_adcontent.value = document.form1.Wec.MIMEValue;
        document.form1.p_adcontent.value.replace("&","&amp;");

        if (document.form1.Wec.TextValue.length < 3) {
            alert("내용을 입력하세요");
            return;
        }
		
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.course.SubjGongAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="initPage()">
<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name = "p_process"  value = "<%= v_process %>">

    <input type='hidden' name='s_grcode'     value='<%=ss_grcode%>'>
    <input type='hidden' name='s_gyear'      value='<%=ss_gyear%>'>
    <input type='hidden' name='s_grseq'      value='<%=ss_grseq%>'>
    <input type='hidden' name='s_uclass'     value='<%=ss_uclass%>'>
    <input type='hidden' name='s_subjcourse' value='<%=ss_subjcourse%>'>
    <input type='hidden' name='s_subjseq'    value='<%=ss_subjseq%>'>
    <input type='hidden' name='s_isclosed'   value='<%=ss_isclosed%>'>
    <input type="hidden" name="p_pageno"     value="<%=v_pageno%>">

    <input type="hidden" name="p_subj"    value="<%=v_subj%>">
    <input type="hidden" name="p_year"    value="<%=v_year%>">
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>">
    <input type="hidden" name="p_seq"     value="<%=v_seq%>">
    <input type="hidden" name="p_hsubjnm" value="<%=v_hsubjnm%>">
    <input type="hidden" name="p_adcontent" value="<%=StringUtil.convertHtmlchars(v_adcontent)%>">
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
            <td><%=v_hsubjnm%></td>
          </tr>
        </table>
        <br>
        <!----------------- 전체공지 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <tr> 
            <td class="table_title" rowspan="2" width="16%"><b>제목</b></td>
            <td class="table_02_2" rowspan="2" width="44%"> 
              <input name="p_title" type="text" class="input" size="98" value="<%=v_title%>">
            </td>
            <td class="table_title" height="25" width="16%"><b>과정명</b></td>
            <td class="table_title" width="15%"><b>v_subjnm</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>교육일수</b></td>
            <td class="table_title"><b>v_edudates</b></td>
          </tr>
          <tr> 
            <td class="table_title"><b>공지구분</b></td>
            <td class="table_02_2"> 
<!-- 공지구분 SELECT BOX  START   -->
<%= CodeConfigBean.getCodeSelect("noticeCategory","",1,"p_types",v_types,"",0,"") %>
<!-- 공지구분 SELECT BOX  END     -->
            </td>
            <td class="table_title" height="25"><b>교육기간</b></td>
            <td class="table_title"><b>v_eduperiod</b></td>
          </tr>
          <tr> 
            <td class="table_title" rowspan="12"><b>내용</b></td>
            <td class="table_02_2" rowspan="12"> 
                <!-- 나모 Editor  -->              
                <p align="center">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_adcontent");</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
                <!-- 나모 Editor  -->   
            </td>
            <td class="table_title" height="25"><b>운영자 이름</b></td>
            <td class="table_title"><b>v_name</b></td>
          </tr>
          <!--tr> 
            <td class="table_title" height="25"><b>운영자 이름</b></td>
            <td class="table_title"><b>v_name</b></td>
          </tr-->
          <tr> 
            <td class="table_title" height="25"><b>운영자 전화</b></td>
            <td class="table_title"><b>v_comptel</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>운영자 E-mail</b></td>
            <td class="table_title"><b>v_email</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>가중치(진도율)</b></td>
            <td class="table_title"><b>v_wstep</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>가중치(중간평가)</b></td>
            <td class="table_title"><b>v_wmtest</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>가중치(최종평가)</b></td>
            <td class="table_title"><b>v_wftest</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>가중치(프로젝트)</b></td>
            <td class="table_title"><b>v_wreport</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>가중치(액티비티)</b></td>
            <td class="table_title"><b>v_wact</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>수료기준점수</b></td>
            <td class="table_title"><b>v_gradscore</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>수료기준진도율</b></td>
            <td class="table_title" ><b>v_gradstep</b></td>
          </tr>
          <!--tr> 
            <td class="table_title" height="25"><b>이수점수</b></td>
            <td class="table_title"><b>v_point</b></td>
          </tr-->
          <tr> 
            <td class="table_title" height="25"><b>시작일</b></td>
            <td class="table_title"><b>v_edustart</b></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>종료일</b></td>
            <td class="table_title"><b>v_eduend</b></td>
          </tr>
			  <tr>
		  <td height="25" class="table_title">첨부파일</td>
            <td class="table_02_2" colspan="3">
             <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td id="sTd" width="90%">
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
              </table>
            </td>
          </tr>
        </table>
        <!----------------- 전체공지 끝 ----------------->


        <br>
        <!----------------- 저장, 취소 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center"><a href='javascript:insert_check()'><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td width=4></td>
            <td align="center"><a href='javascript:list()'><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>

</body>
</html>
