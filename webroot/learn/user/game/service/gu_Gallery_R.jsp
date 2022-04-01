<%
//**********************************************************
//  1. 제      목: 오프라인 갤러리
//  2. 프로그램명 : gu_Gallery_R.jsp
//  3. 개      요: 오프라인 갤러리 상세보기
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.19
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
	box.put("leftmenu","03");                       // 메뉴 아이디 세팅

    String s_userid  = box.getSession("userid");
    String s_gadmin  = box.getSession("gadmin");
    String v_process =  box.getString("p_process");

    int v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    int v_seq        = box.getInt("p_seq");

    int    v_cnt            = 0;            // 조회수
    String v_title          = "";           // 제목
    String v_content        = "";           // 내용
    String v_indate         = "";           // 등록일
    String v_name           = "";           // 등록자
    String v_luserid        = "";
    String v_userid     = "";
    String v_useyn          = "";           // 사용유무
    String v_grcode         = "";           // 대상 교육그룹
    String v_savefile_S     = "";               // 팝업여부
    String v_savefile_L     = "";               // 리스트사용
    String v_media_savefile = "";               // 프레임사용

    DataBox dbox = (DataBox)request.getAttribute("gallerySelect");
    
    if (dbox != null) {
        v_seq            = dbox.getInt   ("d_seq");
        v_title          = dbox.getString("d_title");  
        v_content        = dbox.getString("d_contents");
        v_indate         = dbox.getString("d_indate");   
        v_name           = dbox.getString("d_name"); 
        v_luserid        = dbox.getString("d_luserid");
        v_userid         = dbox.getString("d_userid");
        v_cnt            = dbox.getInt   ("d_cnt");      
        v_useyn          = dbox.getString("d_useyn");     
        v_grcode         = dbox.getString("d_grcode"); 
        v_savefile_L     = dbox.getString("d_savefile_l");
        v_media_savefile = dbox.getString("d_media_savefile");
    }
    
    v_indate = FormatDate.getFormatDate(v_indate,"yyyy-MM-dd");

%>



<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT LANGUAGE="JavaScript">
<!--

    function listView(){
        var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=List";
        document.formGallery.action = url;
        document.formGallery.submit();
    }

    function deleteGallery(){
        if (confirm("정말로 삭제하시겠습니까?")) {
            var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=delete" ;
            document.formGallery.action = url;
            document.formGallery.submit();
        }else{
            return;
        }
    }

    function updatePage(seq){
        var url = "/servlet/controller.homepage.HomeGalleryServlet?p_process=updaetPage&p_seq="+seq ;
        document.formGallery.action = url;
        document.formGallery.submit();
    }


//-->
</SCRIPT>

<form name="formGallery" method="post">     
    <input type = "hidden" name = "p_seq"     value="<%=v_seq%>">
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
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_off_gallery.gif" 
class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 서비스 > Off Line 학생갤러리</td>
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
    <td width="77" class="tbl_grc"><img src="/images/user/game/service/text_write_day02.gif"></td>
    <td width="7" valign="bottom" ><img src="/images/user/game/service/vline.gif"></td>
    <td width="625" class="tbl_gleft"><%=v_indate%></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/service/text_writer02.gif"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td class="tbl_gleft"><%=v_name%></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/service/text_subject02.gif"></td>
    <td valign="bottom"><img src="/images/user/game/service/vline.gif"></td>
    <td name="p_seq" class="font_specialroom_list" value="<%=v_seq%>"><%=v_title%> </td>
  </tr>

  <tr> 
    <td colspan="3" class="tbl_contents"><table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="319" align="center">
            <%if( !v_media_savefile.equals("")){
                v_media_savefile = conf.getProperty("url.upload") + "bulletin/" + v_media_savefile;
            %>
				<script language = "javascript" src = "/script/user_patch.js"></script>
				<script language='javascript'>
					object_embed("<%=v_media_savefile%>","322","217");
				</script>
                <!--embed src="<%=v_media_savefile%>" width="322" height="217" ></embed-->
            <%}else{%>
                등록된 동영상이 없습니다
            <%}%>
          </td>
          <td width="13">&nbsp;</td>
          <td width="343" align="right"><table width="343" border="0" cellpadding="0" cellspacing="8" bgcolor="#EFE5DC">
              <tr>
                <%if( !v_savefile_L.equals("")){
                    v_savefile_L = conf.getProperty("url.upload") + "bulletin/" + v_savefile_L;%>
                    <td align="center"><img src="<%=v_savefile_L%>" width="322" height="217" ></td>
                <%}else{%>
                    <td align="center"  width="322" height="217" > 등록된 이미지가 없습니다 </td>
                <%}%>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="2" class="linecolor_ser3"></td>
  </tr>
  <tr> 
    <td height="4"></td>
  </tr>
  <tr>
    
    <td height="156" bgcolor="#f8f4ef" class="tbl_contents"><%=v_content%></td>
  </tr>
  <tr> 
    <td height="4"></td>
  </tr>
</table>
<table width="720" height="1" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr> 
    <td class="linecolor_ser3"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
    <td height="2" colspan="5" class="linecolor_ser3"></td>
  </tr>
  <tr> 
    <td height="5" colspan="5"></td>
  </tr>
  <tr> 
    <td width="12" align="center" valign="bottom">&nbsp;</td>
    <td width="423" align="right" valign="bottom">&nbsp;</td>
	<td align="right" >

	<%if(v_userid.equals(box.getSession("userid"))){%>
        
            <a href="javascript:updatePage(<%=v_seq%>)" onfocus=this.blur()><img src="/images/user/game/button/btn_mod.gif" 
			border="0"></a>&nbsp;
            <a href="javascript:deleteGallery()"><img src="/images/user/game/button/btn_del.gif" border="0"></a>&nbsp;
    <%}%>
			<a href="javascript:listView()"><img src="/images/user/game/button/btn_list.gif" border="0"></a>&nbsp;
		</td>
  </tr>
  <tr> 
    <td height="5" colspan="5" ></td>
  </tr>
  <tr> 
    <td height="2" colspan="5" class="linecolor_ser3"></td>
  </tr>
</table>
</body>
</html>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
