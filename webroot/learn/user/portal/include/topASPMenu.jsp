<%@ page import = "com.dunet.common.util.*" %>
<%
    TempletBean templetBeans    = new TempletBean();
    
    List    mainMenuList    = templetBeans.getMainMenuList(box.getSession("tem_grcode"));
    
    int     mainListSize    = mainMenuList != null ? mainMenuList.size() : 0 ;
    
    String mMenuGrtype      = "";
    String mMenuGubun       = "";
    String mMenuId          = "";
    String mMenuName        = "";
    String mMenuUrl         = "";
    String mMenuImgPath     = "";
    String mMenuImg         = "";
    String mMenuOverImg     = "";
    String mMenuSubMenuHomeImg = "";
    String mSubImg          = "";
    String mSubOverImg      = "";
    
    List    subMenuList     = templetBeans.getSubMenuList(box.getSession("tem_grcode"));

    String sMenuGubun       = "";
    String sMenuId          = "";
    String sMenuName        = "";
    String sMenuUrl         = "";
    String sMenuImg         = "";
    String sMenuOverImg     = "";
    String sMenuImgPath     = "";
    String sSubImg          = "";
    String sSubOverImg      = "";
    

%>
<input type="hidden" id="mainListSize" name="mainListSize" value="<%=mainListSize %>" />
	<!-- Top Menu 시작 -->
	<ul class="gnb">
	<% if(mainMenuList != null || mainMenuList.size() > 0 ) {
		   for(int top_i = 0 ; top_i <  mainMenuList.size() ; top_i++){
			   DataBox mainBbox = (DataBox)mainMenuList.get(top_i);
		   
			   mMenuGrtype  = mainBbox.getString("d_grtype");
			   mMenuGubun   = mainBbox.getString("d_gubun");
			   mMenuId      = mainBbox.getString("d_menuid");
			   mMenuName    = mainBbox.getString("d_menuname");
			   mMenuUrl     = mainBbox.getString("d_menuurl");
			   mMenuImg     = mainBbox.getString("d_menuimg");
			   mMenuOverImg = mainBbox.getString("d_menuoverimg");
			   
		%>
		<li><a href="javascript:menuMainForward('<%=mMenuGubun%>','<%=mMenuUrl %>');"><img id="menuImg<%=top_i+1 %>" src="/images/portal/asp/<%=mMenuImg %>" alt="<%=mMenuName %>"  onmouseover="mainOver(this, <%=top_i+1 %>);" onmouseout="mainOut(this, <%=top_i+1 %>);" onblur="mainOut(this, <%=top_i+1 %>);"/></a>
			<div id="gnb_sub" name = "gnb_sub" class="gnb_sub" style="display:none;">
				<ul class="sub<%=top_i+1 %>">
	    <%      
	            for(int top_j = 0 ; top_j < subMenuList.size(); top_j++) {
	                DataBox subBox = (DataBox)subMenuList.get(top_j);
		            
	                sMenuGubun   = subBox.getString("d_gubun");
		            sMenuId      = subBox.getString("d_menuid");
		            sMenuName    = subBox.getString("d_menuname");
		            sMenuUrl     = subBox.getString("d_menuurl");
		            sMenuImg     = subBox.getString("d_menuimg");
		            sMenuOverImg = subBox.getString("d_menuoverimg");
		            sSubImg       = subBox.getString("d_subimg");
		            sSubOverImg   = subBox.getString("d_suboverimg");
		            sMenuImgPath  = StringUtil.replaceAll(subBox.getString("d_imgpath"), '\\', '/');
		            
	    	       if(sMenuGubun.equals(mMenuGubun)) {
	    %>
					<li><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><img src="<%=sMenuImgPath+sSubImg %>" alt="<%=sMenuName %>" onmouseover="imgover(this);" onmouseout="imgout(this);" /></a></li>
	    <%        
	               }
	    	   }%>
				</ul>
			</div>
		</li>
	    <% 
		   }
	   }    %>

		<!--li class="allmenu"><a href="javascript:menuForward('4', '11')"><img src="/images/portal/common/allmenu.gif" alt="전체메뉴" /></a></li-->
		<li class="allmenu"><a href="javascript:openAllMenu();"><img src="/images/portal/asp/allmenu.gif" alt="전체메뉴" /></a></li>
	</ul>
    <!-- Top Menu 종료 -->	
    
<script language="JavaScript">
function openAllMenu() {
    var allmenu = document.getElementById("allmenu");
    var submenus = document.getElementsByName("gnb_sub");
    var submenulen = submenus.length;

    for(var i = 0 ; i < submenulen ; i++) {
        	submenus[i].style.display = "none";
    }

    var tempHtml = "<table width='100%'><tr><td><center><img src='/images/portal/common/ajax-loader1.gif'></center></td></tr></tablt>";

    if ( allmenu.style.display == "none") { 
    	$('#allmenu').html(tempHtml);
        $('#allmenu').load('/learn/user/portal/include/siteMapPop.jsp'); 
        allmenu.style.display = "block"; 
    } else { 
        allmenu.style.display = "none"; 
    }
}
 </script>
    
