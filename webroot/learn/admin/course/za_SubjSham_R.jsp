<%
//**********************************************************
//  1. 제      목: 과정모의학습
//  2. 프로그램명 : za_SubjSham_R.jsp
//  3. 개      요: 과정모의학습 차수별 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  ss_upperclass  = box.getString("s_upperclass");
    String  ss_middleclass = box.getString("s_middleclass");
    String  ss_lowerclass  = box.getString("s_lowerclass");
    String  ss_subjcourse        = box.getString("s_subjcourse");
    
    String  v_process     = box.getString("p_process");
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_upperclass     = box.getString("p_upperclass");
    String  v_subj           = box.getString("p_subj");
    String  v_isonoff        = box.getString("p_isonoff");    
        
    String  v_upperclassnm   = GetCodenm.get_subjattnm(v_upperclass);
    String  v_subjnm         = GetCodenm.get_subjnm(v_subj);

    String  v_orderColumn   = box.getString("p_orderColumn");
    String  v_orderType     = box.getStringDefault("p_orderType"," asc"); //정렬순서
    int v_pageno = box.getInt("p_pageno");


    String  v_subjcode       = "";
    String  v_year           = "";
    String  v_subjseq        = "";
    String  v_grcode         = "";
    String  v_gyear          = "";
    String  v_grseq          = "";
    String  v_isbelongcourse = "";
    String  v_course         = "";
    String  v_cyear          = "";
    String  v_courseseq      = "";
    String  v_propstart      = "";
    String  v_propend        = "";
    String  v_edustart       = "";
    String  v_eduend         = "";
    String  v_isclosed       = "";
    String  v_subjname       = "";
	String  v_isoutsourcing  = "";
	String	v_grcodenm		 = "";

    String  v_edustartview   = "";
    String  v_eduendview     = "";
    String  v_today          = FormatDate.getDate("yyyyMMddhh");
    String  v_state          = "";

    int     v_ipropstart     = 0;
    int     v_ipropend       = 0;
    int     v_iedustart      = 0;
    int     v_ieduend        = 0;
    int     v_itoday         = 0;

    String v_owner = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    // 상세보기
    function list() {
        document.form1.p_upperclass.value  = "<%= ss_upperclass %>";
        document.form1.p_subj.value        = "<%= ss_subjcourse %>";

        document.form1.action = "/servlet/controller.course.SubjShamAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    function whenViewEdu(url){
    	document.form1.lessonReurl.value = url;
		document.form1.lessonReiurl.value = "";
        window.open(url,'EduStart','width=980,height=600');
    }

    function studyOpenRe() {
    	document.form1.lessonRepopup.value = "1";
    	var rurl = document.form1.lessonReurl.value;
    	var riurl = document.form1.lessonReiurl.value;
    	setTimeout("whenViewEdu('"+rurl+"');" ,100);
    }

//-->
</SCRIPT>

</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "s_upperclass"  value = "<%= ss_upperclass %>">
    <input type = "hidden" name = "s_middleclass" value = "<%= ss_middleclass %>">
    <input type = "hidden" name = "s_lowerclass"  value = "<%= ss_lowerclass %>">
    <input type = "hidden" name = "s_subjcourse"  value = "<%= ss_subjcourse %>">

    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_upperclass" value = "<%=v_upperclass%>">
    <input type = "hidden" name = "p_subj"       value = "<%=v_subj%>">
    <input type = "hidden" name = "upperclass"   value = "<%=v_upperclass%>">
    <input type = "hidden" name = "subj"         value = "<%=v_subj%>">
    <input type="hidden" name="p_orderColumn" value="<%=v_orderColumn%>">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
    <input type="hidden" name="p_action"  value="">
    <!-- 학습창 다시띄우기용 1:다시 안띄우기  2:다시띄우기(학습컨텐츠에서 닫았을경우) -->
    <input type='hidden' name='lessonRepopup' value="1">
    <input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/course/co_title09_1.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 --------------- -->
        <br>
        <!----------------- form 시작 ----------------->

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <!--tr>
            <td><img src="/images/admin/course/icon1.gif" width="14" height="10" border="0"><%= v_upperclassnm %> &gt; <%= v_subjnm %></td>
          </tr-->
          <tr>
            <td align='right'><a href="javascript:list()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- form 끝 ----------------->
        <br>
        <!----------------- 과정모의학습 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="6" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="5%" height="25" class="table_title"><b>NO</b></td>
            <td class="table_title"><b>교육그룹</b></td>
            <td class="table_title"><b>교육차수</b></td>
            <td class="table_title"><b>과정차수</b></td>
            <td class="table_title"><b>교육기간</b></td>
            <td class="table_title"><b>현재상태</b></td>
            <td class="table_title"><b>학습화면으로</b></td>
          </tr>


<%

       for(int i = 0; i < list.size(); i++) {

           SubjseqData data   = (SubjseqData)list.get(i);

           v_subjcode       =  data.getSubj();
           v_year           =  data.getYear();
           v_subjseq        =  data.getSubjseq();
           v_grcode         =  data.getGrcode();
           v_gyear          =  data.getGyear();
           v_grseq          =  data.getGrseq();
           v_isbelongcourse =  data.getIsbelongcourse();
           v_course         =  data.getCourse();
           v_cyear          =  data.getCyear();
           v_courseseq      =  data.getCourseseq();
           v_propstart      =  data.getPropstart();
           v_propend        =  data.getPropend();
           v_edustart       =  data.getEdustart();
           v_eduend         =  data.getEduend();
           v_isclosed       =  data.getIsclosed();
           v_subjname       =  data.getSubjnm();
		   v_isoutsourcing  =  data.getIsoutsourcing();
		   v_grcodenm		=  data.getGrcodenm();

           v_ipropstart     = StringManager.toInt(v_propstart);
           v_ipropend       = StringManager.toInt(v_propend);
           v_iedustart      = StringManager.toInt(v_edustart);
           v_ieduend        = StringManager.toInt(v_eduend);
           v_itoday         = StringManager.toInt(v_today);

           if (v_ipropstart*v_ipropend*v_iedustart*v_ieduend == 0)    v_state ="";
           else if (v_itoday < v_ipropstart)                          v_state = "<font color='green'>"+"수강신청대기중"+"</font>";
           else if (v_itoday > v_ipropstart && v_itoday < v_ipropend) v_state = "<font color='black'>"+"수강신청중"+"</font>";
           else if (v_itoday <  v_iedustart)							v_state = "<font color='blue'>"+"교육전"+"</font>";
           else if (v_itoday > v_iedustart && v_itoday < v_ieduend)   v_state = "<font color='blue'>"+"교육중"+"</font>";
           else if (v_itoday > v_ieduend)                             v_state = "<font color='red'>"+"교육완료"+"</font>";

            v_edustartview   = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
            v_eduendview     = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
%>

          <tr>
            <td class="table_01" height="25"><%= i+1 %></td>
            <td class="table_02_1"><%=v_grcodenm %></td>
            <td class="table_02_1"><%=v_grseq%></td>
            <td class="table_02_1"><%=v_subjseq%></td>
            <td class="table_02_1"><%=v_edustartview%> ~ <%=v_eduendview%></td>
            <td class="table_02_1"><%=v_state%></td>
            <td class="table_03_1">
<%
//          v_owner = GetCodenm.get_owner(v_subj);
          if (!v_isoutsourcing.equals("Y")) {  // 자체과정일경우만

            if(v_isonoff.equals("ON")) {  // 사이버과정일경우만 
%>

            <a href="javascript:whenViewEdu('<%=EduEtc1Bean.make_eduURL(v_subjcode,v_year,v_subjseq)%>')"><img src='/images/admin/button/b_coursego.gif' border=0></a>
            
<%        
            }
          } 
 %>
            </td>
          </tr>
<%
       }
%>

        </table>
        <!----------------- 과정모의학습 끝 ----------------->


        <br>
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
