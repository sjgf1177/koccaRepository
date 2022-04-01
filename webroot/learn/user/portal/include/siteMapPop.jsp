<%@ page contentType = "text/html;charset=utf-8" %>
<%@ page errorPage = "/learn/library/error.jsp" %>

<%@ page import = "java.util.List" %>
<%@ page import = "com.dunet.common.util.*" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.templet.TempletBean" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%
    TempletBean templetBeans    = new TempletBean();
    
    List    mainMenuList    = templetBeans.getMainMenuList((String)session.getAttribute("tem_grcode"));
    
    int     mainListSize    = mainMenuList != null ? mainMenuList.size() : 0 ;
    
    String mMenuGubun       = "";
    String mMenuName        = "";
    
    RequestBox box = new RequestBox("box");

    List    subMenuList     = templetBeans.getSubMenuList((String)session.getAttribute("tem_grcode"));

    String sMenuGubun       = "";
    String sMenuId          = "";
    String sMenuName        = "";
    String sMenuUrl         = "";
    

%>
        <div class="menu">
        <% if(mainMenuList != null || mainMenuList.size() > 0 ) {
           for(int top_i = 0 ; top_i <  mainMenuList.size() ; top_i++){
               DataBox mainBbox = (DataBox)mainMenuList.get(top_i);
           
               mMenuGubun   = mainBbox.getString("d_gubun");
               mMenuName    = mainBbox.getString("d_menuname");
        %>
            <dl <%=top_i == 0 ? "class=\"fir\"" : top_i == mainMenuList.size() -1 ? "class=\"mg_rn\"" : "" %>>
            <dt><img src="/images/portal/common/allmenu_tit<%=mMenuGubun %>.gif" alt="<%=mMenuName %>" /></dt>
        <%      
                for(int top_j = 0 ; top_j < subMenuList.size(); top_j++) {
                    DataBox subBox = (DataBox)subMenuList.get(top_j);
                    
                    sMenuGubun   = subBox.getString("d_gubun");
                    sMenuId      = subBox.getString("d_menuid");
                    sMenuName    = subBox.getString("d_menuname");
                    sMenuUrl     = subBox.getString("d_menuurl");
                    
                   if(sMenuGubun.equals(mMenuGubun)) {
        %>
                    <dd><a href="javascript:menuForward('<%=sMenuGubun %>', '<%=sMenuId %>');"><%=sMenuName %></a></dd>
        <%        
                   }
               }%>
            </dl>
        <% 
           }
       }    %>
         </div>
        <p class="btn"><a href="javascript:openAllMenu();" class="board_btn2"><span>창닫기</span></a></p>
