<%
//**********************************************************
//  1. 제      목: 오프라인 갤러리
//  2. 프로그램명 : gu_Gallery_L.jsp
//  3. 개      요: 오프라인 갤러리 목록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","03");                       // 메뉴 아이디 세팅

	String s_userid  = box.getSession("userid");
	String s_name    = box.getSession("name");
	String v_process = box.getString("p_process");

    String v_userid		= "";
    String v_name		= "";
    String v_indate		= "";
    String v_title		= "";
    String v_content    = "";
	String v_savefile_S = "";
    int v_seq       = 0;
    int v_levels    = 0;
    int v_cnt       = 0;
    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;

	ArrayList list1 = (ArrayList)request.getAttribute("galleryList");

	v_process = "selectView";
	box.put("p_process", v_process);

%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--
	function viewGallery(seq){
		
		if( seq == 0){
			alert('로그인을 해주세요');
		}
		var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=selectView&p_seq="+seq;
		document.form1.action = url;
        document.form1.submit();
	}

	function addGallery(){
		var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=insertPage";
		document.form1.action = url;
        document.form1.submit();
	}

	//페이지 이동
	function goPage(pageNum) {
		var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=List&p_pageno="+pageNum;
		document.form1.action = url;
		document.form1.submit();
	}

//-->
</SCRIPT>

<form name="form1" method="post">		
	<input type = "hidden" name = "p_seq"	  value="<%=v_seq%>">
	<input type="hidden"   name="p_pageno"    value="<%=v_pageno%>">
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
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_off_gallery.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > Off Line 학생갤러리</td>
  </tr>
  <tr> 
    <td height="10">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>  
    <td align="center"><img src="/images/user/game/service/text_img_gallery.gif"></td>
  </tr>
  <tr> 
    <td height="10"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td width="112">&nbsp;</td>
    <td width="608"><table width="608" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="4" height="27" valign="top" ><img src="/images/user/game/service/ba_f01.gif" ></td>
          <td width="602" align="center" valign="bottom" bgcolor="#DAAE86"><table width="602" height="23" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="145" align="center"  ><img src="/images/user/game/service/text_gallery_name.gif" ></td>
                <td width="1" valign="bottom"  ><img src="/images/user/game/service/vline02.gif" ></td>
                <td align="center"  ><img src="/images/user/game/service/text_page02.gif" ></td>
                <td width="1" valign="bottom" ><img src="/images/user/game/service/vline02.gif" ></td>
                <td width="110" align="center" ><img src="/images/user/game/service/text_wirter02.gif" ></td>
                <td width="1" valign="bottom"  ><img src="/images/user/game/service/vline02.gif" ></td>
                <td width="78" align="center"  ><img src="/images/user/game/service/text_writeday02.gif" ></td>
              </tr>
            </table> </td>
          <td width="3" align="right" valign="top"><img src="/images/user/game/service/ba_tail02.gif"  ></td>
        </tr>
        <tr> 
          <td height="5" colspan="3"></td>
        </tr>
      </table></td>
  </tr>
</table>


<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
<%

		for(i = 0; i < list1.size(); i++) {
			DataBox dbox = (DataBox)list1.get(i);
			v_userid     = dbox.getString("d_userid");
			v_name       = dbox.getString("d_name");
			v_indate     = dbox.getString("d_indate");
			v_title      = dbox.getString("d_title");
	        v_content    = dbox.getString("d_contents");
			v_savefile_S = dbox.getString("d_savefile_s");
			v_seq        = dbox.getInt("d_seq");
			v_cnt        = dbox.getInt("d_cnt");
			v_dispnum    = dbox.getInt("d_dispnum");
			v_totalpage  = dbox.getInt("d_totalpage");
			v_rowcount   = dbox.getInt("d_rowcount");

%>
	
	<%if( (i%2) == 1 ){%>
	<!-- bg 있는목록  -->
	<tr class="linecolor_ser6" align="center" valign="center" height="35">
      <td>
	<%}else{%>
	<!-- bg 없는목록  -->
    <tr align="center" valign="center" height="35">
      <td width="108">
	<%}%><table width="108" border="0" cellspacing="0" cellpadding="0">
			<tr> 
			  <td><img src="/images/user/game/service/gallery_box_top.gif" ></td>
			</tr>
			<tr> 
			  <td background="/images/user/game/service/gallery_bg.gif"><div align="center">
					<%if( v_savefile_S.equals("")){
						v_savefile_S = "/images/user/game/homepage/"+tem_subimgpath+"/gall_blank.gif";
					%>
						<img src="<%=v_savefile_S%>" border="0" width=102 height=62 align="center">
					<%}else{
						v_savefile_S = conf.getProperty("url.upload") + "bulletin/" + v_savefile_S;%>
						<a href="javascript:viewGallery(<%=v_seq%>)"><img src="<%=v_savefile_S%>" border="0" width=102 height=62 ></a>
					<%}%></div></td>
			</tr>
			<tr> 
			  <td><img src="/images/user/game/service/gallery_box_bottom.gif" ></td>
			</tr>
		 </table>
	  </td>
      <td name="p_seq" width="153"  class="tbl_grc" value="<%=v_seq%>"><%=v_title%></td>
      <td name="p_content" width="267"class= "tbl_gleft" value=""><a href="javascript:viewGallery(<%=v_seq%>)"><%=v_content%></a></td>
      <td name="p_name" width="110"  class="tbl_grc" value=""><%=v_name%></td>
      <td name="p_indate" width="82"  class="tbl_grc" value=""><%=FormatDate.getFormatDate(v_indate,"yyyy-MM-dd")%></td>
    </tr>

	<%if( (i%2) == 1 ){%>
	<!-- bg 있는목록  -->
	<tr class="linecolor_ser6">
	<%}else{%>
	<tr>
	<%}%>
      <td height="6" colspan="5"></td>
    </tr>
<!--도트라인 -->
  <!--tr> 
	<td height="1" colspan="5"></td>
  </tr-->
  <tr> 
	<td height="1" background="/images/user/game/service/bg_dotline.gif" colspan="5"></td>
  </tr>
  <tr> 
	<td height="3" colspan="5"></td>
  </tr>
<%
        }
%>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="112" height="2" class="linecolor_ser5"></td>
    <td width="608" height="2" class="linecolor_ser3"></td>
  </tr>
</table>

<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
  <tr> 
    <td width="665" align="center" valign="bottom"> 
      <!-- page -->
		<table width="540" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr> 
			<td>
			<%= PageUtil.printPageListGame(v_totalpage, v_pageno, row) %>
			</td>
		  </tr>
		</table>  
	</td>

	  <%if(s_userid != ""){%>	<%-- 로그인 회원만 등록 가능 --%>
		  <td width="55" align="center" valign="bottom"><a href="javascript:addGallery()"><img src="/images/user/game/button/btn_register.gif"></td>
	  <%}else{}%>
  </tr>
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
  <tr> 
    <td height="2" colspan="2"class="linecolor_board"></td>
  </tr>
</table>
</body>
</html>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
