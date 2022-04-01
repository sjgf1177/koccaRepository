<%
//**********************************************************
//  1. 제      목: Topic List
//  2. 프로그램명: za_Topic_L.jsp
//  3. 개      요: 토론방 주제별리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 23
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_grcode    = box.getString("p_grcode");
    String  v_grcodenm  = box.getString("p_grcodenm");
    String  v_gyear     = box.getString("p_gyear");
    String  v_grseq     = box.getString("p_grseq");
    String  v_subj      = box.getString("p_subj");
    String  v_subjnm    = box.getString("p_subjnm");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
	String  v_subjseqgr   = box.getString("p_subjseqgr");
    String  v_tpcode    = "";
    String  v_started   = "";
    String  v_ended     = "";
    String  v_title     = "";
    String  v_name      = "";
    String  v_addate    = "";
    String  v_finished  = "";
    int     i           = 0;
     ArrayList list     = null;
	//DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");
    int v_pageno         = box.getInt("p_pageno");
    //DEFINED in relation to select END

    list = (ArrayList)request.getAttribute("topicList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
    function toron_list() {
        document.form1.target = "_self";
    	document.form1.p_process.value='ToronList';
    	document.form1.action='/servlet/controller.study.ToronAdminServlet';
    	document.form1.submit();
    }

    function topicView(tpcode){
        document.form1.p_process.value = "TopicSelect";
        document.form1.p_tpcode.value = tpcode;
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }

    function topic_insert() {
        document.form1.target = "_self";
    	document.form1.p_process.value='TopicInsertPage';
    	document.form1.action='/servlet/controller.study.ToronAdminServlet';
    	document.form1.submit();
    }

    function toron_cal() {
        document.form1.target = "_self";
    	document.form1.p_process.value='StudentMemberList';
    	document.form1.action='/servlet/controller.study.ToronAdminServlet';
    	document.form1.submit();
    }
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process">
    <input type="hidden" name="s_action"    value="<%=ss_action%>">    <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="p_grcode"    value="<%=v_grcode%>">
    <input type="hidden" name="p_grcodenm"  value="<%=v_grcodenm%>">
    <input type="hidden" name="p_gyear"     value="<%=v_gyear%>">
    <input type="hidden" name="p_grseq"     value="<%=v_grseq%>">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
	<input type="hidden" name="p_subjseqgr"   value="<%=v_subjseqgr%>">
    <input type="hidden" name="p_tpcode">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">

    <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/course/co_title06.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	        <!----------------- title 끝 ----------------->
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <!--<td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0"> <%=v_grcodenm%> 교육그룹
            &gt; <%=v_gyear%> 년도 &gt;  <%=v_grseq%> 교육차수 &gt;  <%=v_subjnm%> 과정 &gt; <%=StringManager.cutZero(v_subjseqgr)%> 차
          </td>-->
          <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0">
              <b><%=v_subjnm%> </b>
          </td>
        </tr>
      </table>

      <br>

      <!----------------- 리스트, 주제발의 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="bottom" height="20" class=dir_txt>☞ 아래에서 토론에 참여할 주제를 선택하십시오.</td>
          <td align="center" width="75">
          <a href="javascript:toron_cal()">토론평가</a>
          </td>
          <td align="center" width="75">
          <a href="javascript:topic_insert()"><img src="/images/admin/button/btn_subject.gif"  border="0"></a>
          </td>
          <td align="right" width="65">
          <a href="javascript:toron_list()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 리스트, 주제발의 버튼 끝 ----------------->

      <!----------------- 토론방 보기 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="5%" class="table_title"><b>NO</b></td>
          <td class="table_title"><b>상태</b></td>
          <td class="table_title"><b>주제</b></td>
          <td class="table_title"><b>발의자</b></td>
          <td class="table_title" width="10%"><b>발의일</b></td>
          <td class="table_title" width="17%"><b>토론기간</b></td>
        </tr>
        <%
        for(i = 0; i < list.size(); i++) {
            ToronData data  = (ToronData)list.get(i);
            v_tpcode    = data.getTpcode();
            v_started   = data.getStarted();
            v_ended     = data.getEnded();
            v_title     = data.getTitle();
            v_name      = data.getName();
            v_addate    = data.getAddate();

            if(!v_ended.equals("") && (Integer.parseInt(FormatDate.getFormatDate(v_ended,"yyyyMMdd")) < Integer.parseInt(FormatDate.getDate("yyyyMMdd")))){
                v_finished = "마감";
            } else {  v_finished = "진행중";
            }
            v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");
            v_started   = FormatDate.getFormatDate(v_started,"yyyy/MM/dd");
            v_ended     = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd");
        %>
        <tr>
          <td class="table_01"><%= list.size() - i%></td>
          <td class="table_02_1"><%=v_finished%></td>
          <td class="table_02_1"><a href="javascript:topicView('<%=v_tpcode%>')"><%=v_title%></a></td>
          <td class="table_02_1"><%=v_name%></td>
          <td class="table_02_1"><%=v_addate%></td>
          <td class="table_02_1"><%=v_started%>~<%=v_ended%></td>
        </tr>
        <% }
        if(i == 0){ %>
              <tr>
                <td class="table_02_1" colspan="6">등록된 내용이 없습니다</td>
              </tr>
         <%  } %>
      </table>
      <!----------------- 토론방 보기 끝 ----------------->
      <br>
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
