<%
//**********************************************************
//  1. 제      목: Topic SELECT
//  2. 프로그램명: za_Topic_R.jsp
//  3. 개      요: 토론방 상세보기
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
    String  v_tpcode    = box.getString("p_tpcode");
    int v_pageno         = box.getInt("p_pageno");
    //DEFINED class&variable END

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");        //교육그룹
    String  ss_gyear     = box.getString("s_gyear");         //년도
    String  ss_grseq     = box.getString("s_grseq");         //교육차수
    String  ss_uclass    = box.getString("s_uclass");        //과정분류
    String  ss_subjcourse= box.getString("s_subjcourse");    //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");       //과정 차수
    String  ss_action    = box.getString("s_action");
    //DEFINED in relation to select END

    String  v_user_id   = box.getSession("userid");

    String  v_started   = "";
    String  v_ended     = "";
    String  v_title     = "";
    String  v_addate    = "";
    String  v_adcontent = "";
    String  v_aduserid  = "";
    String  v_name      = "";
    String  v_userid    = "";
    int     v_seq       =  0;
    int     v_cnt       =  0;
    int     v_levels    =  0;
    int     i           =  0;
    boolean v_status    =  false;
    ToronData data      = null;
    ArrayList list      = null;

    data = (ToronData)request.getAttribute("topicSelect");
    v_started   = data.getStarted();
    v_ended     = data.getEnded();
    v_title     = data.getTitle();
    v_addate    = data.getAddate();
    v_adcontent = data.getAdcontent();
    v_userid    = data.getAduserid();
    v_name      = data.getName();
    v_cnt       = data.getCnt();
    if((Integer.parseInt(v_started) < Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))&&
    (Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) < Integer.parseInt(v_ended))){
        v_status = true;
    }else{
        v_status = false;
    }
    v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");
    v_started   = FormatDate.getFormatDate(v_started,"yyyy/MM/dd HH");
    v_ended     = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd HH");
    list = (ArrayList)request.getAttribute("toronList");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

    function whenTopicList(){
        document.form1.p_process.value = "TopicList";
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }

    function whenTopicUpdate(){
        document.form1.p_process.value = "TopicUpdatePage";
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }

    function whenTopicDelete(){
        document.form1.p_process.value = "TopicDelete";
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }
    function whenToronInsert(){
        document.form1.p_process.value = "ToronInsertPage";
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }
    function whenToronView(seq){
        document.form1.p_process.value = "ToronSelect";
        document.form1.p_seq.value = seq;
        document.form1.action = "/servlet/controller.study.ToronAdminServlet";
        document.form1.submit();
    }

-->
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
    <input type="hidden" name="p_tpcode"    value="<%=v_tpcode%>">
    <input type="hidden" name="p_seq"       value="">
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

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>토론 주제</td>
		  </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        
        <!----------------- 토론방 내용 시작 ---------------->
        <!--% if(v_user_id.equals(v_userid)){ %-->
          <table width="97%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="574">&nbsp;</td>
              <td align="right">
			   <a href="javascript:whenTopicUpdate()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
               <a href="javascript:whenTopicDelete()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
               <a href="javascript:whenTopicList()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
              </td>
            </tr>
          </table>
         <!--% } %-->        
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="20%" height="25" class="table_title">주제개요</td>
          <td width="30%" class="table_02_2"><%=v_title%></td>
          <td width="20%" height="25" class="table_title">조회수</td>
          <td width="30%" class="table_02_2"><%=v_cnt%></td>
        </tr>
        <tr>
          <td width="20%" height="25" class="table_title">작성자</td>
          <td class="table_02_2"><%=v_name%></td>
          <td width="20%" height="25" class="table_title">토론기간</td>
          <td class="table_02_2"><%=v_started%>시~<%=v_ended%>시</td>
        </tr>
        <tr>
          <td colspan="4" class="table_02_2" align="center">
            <table width="98%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td><%= StringManager.replace(v_adcontent,"\r\n","<br>") %></td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!----------------- 토론방 내용 끝 ---------------->


	  <br><br>

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>토론 의견 리스트</td>
		  </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 의견작성, 리스트으로 ---------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="11" colspan='2'></td>
          </tr>
          <tr> 
            <td width="806">&nbsp; </td>
            <td align="right" width="164">
            <% if(v_status){ %><a href="javascript:whenToronInsert()"><img src="/images/admin/button/btn_add.gif" border="0"></a><%}%>
            <a href="javascript:whenTopicList()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
            
            </td>
          </tr>
        </table>
        <!----------------- 의견작성, 리스트 버튼 끝 ----------------->

      <!----------------- 토론방 보기 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="6" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%" height="28">번호</td>
          <td class="table_title" height="28">의 견</td>
          <td class="table_title" width="10%" height="28">게시일</td>
          <td class="table_title" width="10%" height="28">참여자</td>
        </tr>
        <%
            for(i = 0; i < list.size(); i++) {
                ToronData data1  = (ToronData)list.get(i);
                v_seq       = data1.getSeq();
                v_title     = data1.getTitle();
                v_aduserid  = data1.getAduserid();
                v_addate    = data1.getAddate();
                v_name      = data1.getName();
                v_levels    = data1.getLevels();
                v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");
        %>
        <tr>
          <td class="table_01" height="27"><%= list.size() - i %></td>
          <td class="table_02_2" height="27">
          <% if (v_levels > 1) {%>
            <img src = "/images/user/study/space.gif" width="<%= v_levels*15-30 %>" height ="10" border =0>
            <img src="/images/user/support/ico_re.gif" border="0">
          <% } %>
          <a href="javascript:whenToronView('<%=v_seq%>')"><%= v_title %></a></td>
          <td class="table_02_1"><%= v_addate %></td>
          <td class="table_02_1"><%= v_name %></td>
        </tr>

        <%  }  if(i == 0){ %>
          <tr>
            <td align="center" class="table_02_1" colspan="5">등록된 내용이 없습니다</td>
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
