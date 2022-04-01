<%
//**********************************************************
//  1. 제      목: 서비스 > 공지사항 상세보기
//  2. 프로그램명 : gu_Letter_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 05.12.19 이나연
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String  v_process    = box.getString("p_process");
    int     v_pageno     = box.getInt   ("p_pageno");
	String  v_userid     = box.getSession("userid");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
	String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자
	String v_luserid   = "";
    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_grcodecd   = "";           // 대상 교육그룹
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치

    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용

    String v_upfile    ="";
    //String v_realfile  ="";

    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;

    DataBox dbox = (DataBox)request.getAttribute("selectNotice");

    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");
        v_startdate = dbox.getString("d_startdate");
        v_enddate   = dbox.getString("d_enddate");
        v_adtitle   = dbox.getString("d_adtitle");
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");
        v_adname    = dbox.getString("d_adname");
		v_luserid	= dbox.getString("d_luserid");
        v_cnt       = dbox.getInt("d_cnt");
		v_loginyn   = dbox.getString("d_loginyn");
		v_useyn   	= dbox.getString("d_useyn");
		v_grcodecd	= dbox.getString("d_grcodecd");
		v_popwidth	= dbox.getInt("d_popwidth");
		v_popheight = dbox.getInt("d_popheight");
		v_popxpos   = dbox.getInt("d_popxpos");
		v_popypos   = dbox.getInt("d_popypos");
		v_popup     = dbox.getString("d_popup");
		v_upfile    = dbox.getString("d_upfile");
		//v_realfile  = dbox.getString("d_realfile");
		v_uselist   = dbox.getString("d_uselist");
		v_useframe  = dbox.getString("d_useframe");

        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy/MM/dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");


        v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector		= (Vector)dbox.getObject("d_fileseq");
     }

    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";

    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";

    if(v_loginyn.equals("Y")) v_loginyn = "로그인후";
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";

    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">

	function listnotice(){
		document.nform2.action = "/servlet/controller.homepage.HomeLetterServlet";
        document.nform2.submit();
	}

</SCRIPT>

<form name="nform2" method="post">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
</head>

<body>
<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_letter.gif" class="location" ><img src="/images/user/game/common/location_bl.gif">
      HOME > 서비스 > 뉴스레터</td>
  </tr>
  <tr>
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="81" height="2" class="linecolor_ser5"></td>
    <td width="639" height="2" class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0"
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" align="center">
  <tr>
    <td width="77" class="tbl_grc"><img src="/images/user/game/service/text_subject02.gif" width="37" height="11"></td>
    <td width="7" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_adtitle" colspan="10" class="font_specialroom_list" value="<%=v_adtitle%>"><%=v_adtitle%></td>
  </tr>
  <tr>
    <td width="75" class="tbl_grc"><img src="/images/user/game/service/text_writer02.gif" width="37" height="11"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_luserid" width="160" bgcolor="F8F7EF" class="tbl_bleft" value="<%=v_luserid%>"><%=v_adname%></td>
    <td width="7" valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td width="75" class="tbl_grc"><img src="/images/user/game/service/text_writeday.gif" width="37" height="11"></td>
    <td width="7" valign="bottom" class="tbl_bleft"><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_addate" width="100" class="tbl_grc" value="<%=v_addate%>"><%=v_addate%></td>
    <td width="7" valign="bottom"  ><img src="/images/user/game/service/vline.gif"></td>
    <td width="70" class="tbl_grc"><img src="/images/user/game/service/text_search.gif" width="18" height="11"></td>
    <td width="8" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_cnt" width="70" class="tbl_grc" value="<%=v_cnt%>"><%=v_cnt%></td>
    <td bgcolor="F8F7EF" class="tbl_bleft">&nbsp;</td>
  </tr>
  <tr>
    <td class="tbl_grc"><img src="/images/user/game/service/text_page.gif" width="37" height="11"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_adcontent" colspan="10" class="tbl_contents" value="<%=v_adcontent%>"><%=v_adcontent%></td>
  </tr>
  <tr>
    <td class="tbl_grc"><img src="/images/user/game/service/text_file.gif" width="37" height="11"></td>
    <td valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td colspan="10" class="tbl_gleft"><table width="628" border="0" cellpadding="0" cellspacing="0" bgcolor="#F8F4EF">

	<%
		if( v_realfileVector != null ) {
			for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				String v_realfile = (String)v_realfileVector.elementAt(i);
				String v_savefile = (String)v_savefileVector.elementAt(i);
				String v_fileseq = (String)v_fileseqVector.elementAt(i);

				if(v_realfile != null && !v_realfile.equals("")) {
	%>

		<tr bgcolor="#F8F4EF"><a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'><%= v_realfile%></a> &nbsp;<br>
	<%
				}else{%>
		<tr bgcolor="#F8F4EF"> 첨부된 파일이 없습니다<br>
	<%
				}
		}
	}
	%>

        </tr>
      </table>
    </td>
  </tr>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="1" colspan="2" class="linecolor_board"></td>
  </tr>
  <tr>
    <td height="5" colspan="2"></td>
  </tr>
  <tr>
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom">

<%	if( v_userid == null || v_userid ==""){ %>
	<a href="/servlet/controller.homepage.HomeLetterServlet?p_process=moreNotice"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
<%}else{%>
	<a href="javascript:listnotice()"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
<%}%>

  </tr>
  <tr>
    <td height="5" colspan="2" ></td>
  </tr>
</table>
</body>
</html>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->