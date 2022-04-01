
<%
//**********************************************************
//  1. 제      목: 홈페이지 오프라인갤러리 
//  2. 프로그램명 : Gallery.jsp
//  3. 개      요: 홈페이지 오프라인갤러리 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.util.Vector.*"%>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

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

	box.put("p_process","mainList");
	box.put("p_grtype","KDGI");

	HomeGalleryBean gbean = new HomeGalleryBean();
	ArrayList list1 = (ArrayList)gbean.selectList(box);

%>
		
		
<form name="formGallery" method="post">		
		<!--<table width="536" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="92" valign="top" style="padding-left:110px;padding-top:10px;"background="../../../../images/user/game/homepage/type1/gallery_back.gif">
	  <table width="400" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="49" valign="top" style="padding-top:10px;"><a href="#"><img src="../../../../images/user/game/button/b_more.gif" border="0"></a></td>
            <td width="120" valign="top"><table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td width="112" bgcolor="#FFFFFF"><img src="../../../../images/user/game/homepage/type1/g_01.jpg" width="102" height="62"></td>
                </tr>
              </table></td>
            <td width="121" valign="top"><table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td valign="top" bgcolor="#FFFFFF"><img src="../../../../images/user/game/homepage/type1/g_02.jpg"></td>
                </tr>
              </table></td>
            <td width="110" valign="top"><table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td valign="top" bgcolor="#FFFFFF"><img src="../../../../images/user/game/homepage/type1/gall_blank.gif" width="102" height="62"></td>
                </tr>
              </table></td>
          </tr>
        </table></td>
    </tr>
  </table> -->


  <table width="536" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="92" valign="top" style="padding-left:110px;padding-top:10px;"background="/images/user/game/homepage/type1/gallery_back.gif">
		<table width="400" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="49" valign="top" style="padding-top:10px;"><a href="/servlet/controller.homepage.HomeGalleryServlet?p_process=moreList"><img src="/images/user/game/button/b_more.gif" border="0"></a></td>
			<td width="350" valign="top">
			  <table width="345" border="0" cellspacing="0" cellpadding="0">
				  <tr>
            
	<% for(i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);
		v_savefile_S = dbox.getString("d_savefile_s");
		v_seq        = dbox.getInt("d_seq");

	     if( v_savefile_S.equals("")){
			v_savefile_S = "/images/user/game/homepage/type1/gall_blank.gif";
	%>	
			<td width="115" valign="top">
			<table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td bgcolor="#FFFFFF"><img src="<%=v_savefile_S%>" width="102" height="62"></td>
                </tr>
            </table>
			</td>
	<%	 }else{
			v_savefile_S = conf.getProperty("url.upload") + "bulletin/" + v_savefile_S;%>
			<td width="115" valign="top">
			<table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td bgcolor="#FFFFFF"><a href="/servlet/controller.homepage.HomeGalleryServlet?p_process=selectView&p_seq=<%=v_seq%>"><img src="<%=v_savefile_S%>" width="102" height="62"></td>
                </tr>
            </table>
			</td>
	<%	 }
	  }
	  if( 0 < 3-list1.size() ){
	  for(int j=0; j < 3-list1.size(); j++ ){
	%>
		<td width="115" valign="top">
			<table width="108" border="1" cellpadding="2" cellspacing="0" bordercolor="#CCCCCC">
                <tr> 
                  <td bgcolor="#FFFFFF"><img src="/images/user/game/homepage/type1/gall_blank.gif" width="102" height="62"></td>
                </tr>
            </table>
			</td>
	<%}}%>
		
			  </tr>
			</table>
			</td>
		</tr>
	  </table> 
  	  </td>
    </tr>
  </table> 
</form>