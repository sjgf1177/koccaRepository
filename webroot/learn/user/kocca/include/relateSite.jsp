<%
//**********************************************************
//  1. 제      목: 관련사이트 
//  2. 프로그램명:  relateSite.jsp
//  3. 개      요: 관련사이트 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.10 이나연
//  7. 수      정: 
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%
	String sv_userid = box.getSession("userid");
	String sv_name   = box.getSession("name");
	String sv_resno  = box.getSession("resno");

	int vv_seq			= 0;  
	int vv_sort			= 0;  
	String vv_sitenm	= "";  
	String vv_url		= "";  
	String vv_isuse		= "";  
	String vv_islogin	= "";  
	String vv_grcode	= "";  
	String vv_ldate		= "";	
	String vv_banner     = "";

	LinkSiteBean site = new LinkSiteBean();
	ArrayList bolist = site.SelectList(box);

%>

<SCRIPT LANGUAGE="JavaScript">
<!--
// 관련 사이트 링크 셀렉트박스
function goSiteSelect() {
	var link1 = document.linkform.relatedlink.value;
	var arr = link1.split(':');

	if(arr[2] == "Y"){
	
		alert("로그인 후 이용해주세요");
		return;
	
	}
	window.open("/learn/user/homepage/zu_LinkSite_Action.jsp?p_grcode="+arr[0]+"&p_seq="+arr[1], "SiteLink", "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes,width=700,height=480,left=80,top=100");
}
//-->
</SCRIPT>

<form name="linkform" method="post">
			<td width="58" height="35">
				<div align="center">
					<img src="/images/user/kocca/homepage/type1/footer_site.gif" ></div></td>
                      <td width="2"><img src="/images/user/kocca/homepage/type1/footer_vline.gif" ></td>
					  <td width="160" class="tbl_bleft2"><select name="relatedlink" onChange="goSiteSelect()">
                          <option>:: 관련사이트바로가기 ::</option>
<%
	for( int ii = 0 ; ii < bolist.size() ; ii++ ){
		DataBox ddbox = (DataBox)bolist.get(ii);		 
		
		vv_seq		= ddbox.getInt("d_seq");  
		vv_sort		= ddbox.getInt("d_sort");  
		vv_sitenm	= ddbox.getString("d_sitenm");  
		vv_url		= ddbox.getString("d_url");  
		vv_isuse	= ddbox.getString("d_isuse");  
		vv_islogin	= ddbox.getString("d_islogin");  
		vv_grcode	= ddbox.getString("d_grcode");
		vv_ldate	= ddbox.getString("d_ldate");	
		vv_banner   = ddbox.getString("d_banner");
%>
		
						  <option value='<%=vv_grcode%>:<%=vv_seq%>:<%=vv_islogin%>'><%=vv_sitenm%></option>
<%}%>
			  </td>
			  <td width="5">&nbsp;</td>
			  <td class="tbl_bleft2">
<%
	for( int ii = 0 ; ii < bolist.size() ; ii++ ){
		DataBox ddbox = (DataBox)bolist.get(ii);		 
		
		vv_seq		= ddbox.getInt("d_seq");  
		vv_sort		= ddbox.getInt("d_sort");  
		vv_sitenm	= ddbox.getString("d_sitenm");  
		vv_url		= ddbox.getString("d_url");  
		vv_isuse	= ddbox.getString("d_isuse");  
		vv_islogin	= ddbox.getString("d_islogin");  
		vv_ldate	= ddbox.getString("d_ldate");	
		vv_banner   = ddbox.getString("d_banner");
%>
		<%if(vv_banner.equals("")){%>
		<%}else{%>
        <a href="<%=vv_url%>"><img src="<%=conf.getProperty("url.upload")+"linksite/"%><%=vv_banner%>" hspace="4">
		<%		}
			}%>
				<!--img src="/images/user/game/homepage/type1/footer_bn01.gif" hspace="4">
				<img src="/images/user/game/homepage/type1/footer_bn02.gif" hspace="4">
				<img src="/images/user/game/homepage/type1/footer_bn05.gif" hspace="4">
				<img src="/images/user/game/homepage/type1/footer_bn06.gif" hspace="4"-->
				<br>
			  </td>
</form>