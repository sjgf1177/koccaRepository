<%
//**********************************************************
//  1. 제      목: 서비스 > 건의&버그 게시판
//  2. 프로그램명 : gu_Bug_L.jsp
//  3. 개      요: 서비스 > 건의&버그 게시판
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","06");                       // 메뉴 아이디 세팅

	ArrayList list1 = null;

    int		v_tabseq   = box.getInt("p_tabseq");
    int		v_seq      = box.getInt("p_seq");     
    String  v_process  = box.getString("p_process");

	// 검색
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_select");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
	String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
	String v_luserid   = "";
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
	int v_rowcount	= 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
   
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    //리스트 조회
    function select() {
        document.form1.action = "/servlet/controller.homepage.HomeNoticeServlet";
        document.form1.p_process.value = "List";
        document.form1.submit();
    }

	//상세보기
    function selectView(seq) {

		v_url = "/servlet/controller.homepage.HomeNoticeServlet?p_process=selectView&p_seq="+seq;
        document.nform1.action = v_url;
        document.nform1.submit();
    }

//-->
</SCRIPT>

<form name="nform1" method="post">
	<input type="hidden" name="p_process">

<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_bug.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > 운영자에게</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td valign="top"><img src="/images/user/game/service/text_bug.gif"></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_ser5"></td>
    <td width="639" height="2" class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" >
  <tr> 
    <td width="77" class="tbl_grc"><img src="/images/user/game/service/text_subject02.gif"></td>
    <td width="7" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td class="font_specialroom_list"><input name="textfield" type="text" class="input" style="width:300px"></td>
  </tr>
  <tr> 
    <td width="77" class="tbl_grc"><img src="/images/user/game/service/text_writer02.gif" height="11"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td bgcolor="F8F7EF" class="tbl_bleft"><input name="textfield2" type="text" class="input" style="width:195px"></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/service/text_email.gif"></td>
    <td valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td class="tbl_gleft"><input name="textfield22" type="text" class="input" style="width:195px"></td>
  </tr>
  <tr> 
    <td colspan="3" class="tbl_grc">&nbsp;</td>
  </tr>
    <tr> 
    <td class="tbl_grc"><img src="/images/user/game/service/text_file.gif"></td>
    <td valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td class="tbl_gleft"><input name="textfield22" type="text" class="input" style="width:240px">
      &nbsp;<a href="#"><img src="/images/user/game/button/btn_find_long.gif" border="0"></a></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="1" colspan="2" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom"><a href="#"><img src="/images/user/game/button/btn_confirm.gif" border="0"></a></td>
  </tr>
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
</table>

</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
