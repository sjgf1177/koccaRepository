<%
//**********************************************************
//  1. 제      목: 토론방
//  2. 프로그램명: za_Toron_U.jsp
//  3. 개      요: 토론방 의견수정
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
<%@ page import = "com.dunet.common.util.*" %>   
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
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

    String  v_title     = "";
    String  v_adcontent = "";
    String  v_userid    = "";
    String  v_addate    = "";
    String  v_name      = "";
    int     v_seq       = box.getInt("p_seq");
    int     v_cnt       =  0;
    ToronData data      = null;
	//DEFINED class&variable END

	data = (ToronData)request.getAttribute("toronSelect");
	v_seq       = data.getSeq();
	v_title     = data.getTitle();
	v_adcontent = data.getAdcontent();
	v_userid    = data.getAduserid();
	v_addate    = data.getAddate();
	v_name      = data.getName();
	v_cnt       = data.getCnt();
	v_addate    = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd");

    String content  = StringManager.replace(v_adcontent, "&nbsp;","&amp;nbsp;");    
    String width    = "540";
    String height   = "250";    
    
  	String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name; 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script	language="JavaScript" type="text/JavaScript">
    <!--
        function initPage()
        {   
            document.form1.Wec.Value = document.form1.p_adcontent.value;
        }

        function whenToronInsert() {
            if (blankCheck(document.form1.p_title.value)) {
                alert("제목을 입력하세요!");
                document.form1.p_title.focus();
                return;
            }

            if (realsize(document.form1.p_title.value) > 200) {
                alert("제목은 한글기준 100자를 초과하지 못합니다.");
                document.form1.p_title.focus();
                return;
            }

            /*if (blankCheck(document.form1.p_adcontent.value)) {
                alert("내용을 입력하세요!");
                document.form1.p_adcontent.focus();
                return;
            }*/

            document.form1.p_adcontent.value = document.form1.Wec.MIMEValue;	      
            document.form1.target = "_self";
            document.form1.action = "/servlet/controller.study.ToronAdminServlet";
            document.form1.p_process.value = "ToronUpdate";
            document.form1.submit();
        }
	    function cancel() {
            history.back(-1);
        }
   -->
    </script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="initPage()">


    <form name = "form1" method = "post">
    <input type="hidden" name="p_aduserid" value="<%=v_user_id%>">
    <input type="hidden" name="p_process">
    <input type="hidden" name="p_subjnm"    value="<%=v_subjnm%>">   
    <input type="hidden" name="s_action"    value="<%=ss_action%>">     <!--in relation to select-->
    <input type="hidden" name="s_grcode"    value="<%=ss_grcode%>">     <!--in relation to select-->
    <input type="hidden" name="s_gyear"     value="<%=ss_gyear%>">      <!--in relation to select-->
    <input type="hidden" name="s_grseq"     value="<%=ss_grseq%>">      <!--in relation to select-->
    <input type="hidden" name="s_uclass"    value="<%=ss_uclass%>">     <!--in relation to select-->
    <input type="hidden" name="s_subjcourse"value="<%=ss_subjcourse%>"> <!--in relation to select-->
    <input type="hidden" name="s_subjseq"   value="<%=ss_subjseq%>">    <!--in relation to select-->
    <input type="hidden" name="p_subj"      value="<%=v_subj%>">
    <input type="hidden" name="p_year"      value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"   value="<%=v_subjseq%>">
	<input type="hidden" name="p_subjseqgr"   value="<%=v_subjseqgr%>">
    <input type="hidden" name="p_tpcode"    value="<%=v_tpcode%>">
    <input type="hidden" name="p_seq"       value="<%=v_seq%>">
    <input type="hidden" name="p_pageno"  value="<%=v_pageno%>">
	<input type= "hidden" name= "p_adcontent"  id= "p_adcontent"  value= "<%=StringUtil.convertHtmlchars(content)%>"> 
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
      <br>

      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width=1%><img src="/images/admin/common/icon.gif" ></td>
          <td><img src="/images/admin/study/icon1.gif" width="14" height="10" border="0"> 
              <b><%=v_subjnm%> </b>
          </td>  
        </tr>
      </table>
      <br>

      <!----------------- 의견내기 시작 ---------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
            <td class="table_title" width="16%"><b>제 목</b></td>
            <td class="table_02_2" width="81%"><input size="90" maxlength="200" name="p_title" value='<%=v_title%>' class="input"></td>
        </tr>
        <tr>
            <td class="table_title"><b>작성자</b></td>
            <td class="table_02_2"> <%=v_user_name%></td>
        </tr>
        <tr>
            <td class="table_title"><b>등록일</b></td>
            <td class="table_02_2"><%= v_addate %></td>
        </tr>
        <tr>
            <td class="table_title"><b>의견내용</b></td>
            <td class="table_02_2">
              <!--<textarea name="p_adcontent" cols="90" rows="15" class="input"><%= v_adcontent %></textarea>-->
                <!-- 나모 Editor  -->
                <p align="center">
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";namoActiveInitCompleted("p_adcontent");</script>
                <script language="javascript">object_namoActivepatch('684','500');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                </p>
                <!-- 나모 Editor  --> 
            </td>
        </tr>
      </table>
      <!----------------- 의견내기 끝 ---------------->
      <br>

      <!----------------- 등록, 취소 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center">
          <a href="javascript:whenToronInsert()"><img src="/images/admin/button/btn_apply.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center">
          <a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->
        <br>
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
