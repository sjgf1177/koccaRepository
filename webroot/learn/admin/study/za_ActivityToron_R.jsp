<%
//**********************************************************
//  1. 제      목: 토론 상세보기
//  2. 프로그램명  za_ActivityToron_R.jsp
//  3. 개      요: 토론 상세보기화면
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
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

    String  v_started   = "";
    String  v_ended     = "";
    String  v_title     = "";                
    String  v_addate    = "";
    String  v_adcontent = "";
    String  v_aduserid  = "";
    String  v_name      = "";
    String  v_userid    = ""; 
    String  v_title1    = "";      
    String  v_adcontent1= "";      
    String  v_userid1   = "";      
    String  v_addate1   = "";       
    String  v_name1     = ""; 
    int     v_seq1      =  0;               
    int     v_cnt1      =  0;       
    int     v_seq       =  0;
    int     v_cnt       =  0;
    int     v_levels    =  0;
    int     i           =  0;
    boolean v_status    =  false;
    ToronData data2     = null;
    ArrayList list      = null;

    data2 = (ToronData)request.getAttribute("toronSelect");     
    v_seq1      = data2.getSeq();
    v_title1    = data2.getTitle();         
    v_adcontent1= data2.getAdcontent();
    v_userid1   = data2.getAduserid();
    v_addate1   = data2.getAddate();    
    v_name1     = data2.getName();          
    v_cnt1      = data2.getCnt();       
    v_addate1   = FormatDate.getFormatDate(v_addate1,"yyyy/MM/dd"); 
    
    list = (ArrayList)request.getAttribute("toronList");

%>

<html>
<head>
<title>토론내용</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" >

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
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
            <td  class=sub_title>토론 의견 리스트</td>
		  </tr>
          <tr>
            <td height="3"></td>
          </tr>
        </table>
        <!----------------- 토론방 의견 내용 시작 ---------------->
        <!----------------- 의견작성, 리스트으로 ---------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="90%">&nbsp; </td>
            <td align="right" width="200"></td>
          </tr>
        </table>
        <!----------------- 의견작성, 리스트 버튼 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="20%" height="25" class="table_title">의견제목</td>
          <td width="80%" class="table_02_2" colspan="3"><%=v_title1%></td>
        </tr>
        <tr>
          <td width="20%" height="25" class="table_title">작성자</td>
          <td class="table_02_2"><%=v_name1%></td>
          <td width="20%" height="25" class="table_title">작성일</td>
          <td class="table_02_2"><%=v_addate1%></td>
        </tr>
        <tr>
          <td colspan="4" class="table_02_2" align="center">
            <table width="98%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td><%= StringManager.replace(v_adcontent1,"\r\n","<br>") %></td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>

    <table width="50%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
        <td width="30%">&nbsp;</td>
        <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        <td width="30%">&nbsp;</td>
      </tr>
    </table>
    <br/>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>