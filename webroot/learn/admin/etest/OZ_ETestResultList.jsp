<%
/**
 * file name : OZ_ETestResultList.jsp
 * date      : 2005/09
 * programmer:
 * function  : 리포트
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    
    String  ss_grcode    = box.getString("s_grcode");        
    String  ss_gyear     = box.getString("s_gyear");       
    String  ss_etestsubj    = box.getString("s_etestsubj");       
%>             
NO|회사|ID|성명|부서|직위|최고점수|1차|2차|3차<%
    box.put("p_action", "go");
    box.put("s_grcode",   ss_grcode );
    box.put("s_gyear",    ss_gyear ); 
    box.put("s_etestsubj",ss_etestsubj);       

    ETestResultBean bean = new ETestResultBean();
    ArrayList       list = bean.EtestResultList(box);
	DataBox	data  = null;   
%>    
<%  
  for (int i=0; i<list.size(); i++) {
        data  = (DataBox)list.get(i); 
%><%=i+1%>|<%=data.getString("d_companynm")%>|<%=data.getString("d_userid")%>|<%=data.getString("d_name")%>|<%=data.getString("d_compnm")%>|<%=data.getString("d_jikwinm")%>|<%=data.getInt("d_maxscore")%>|<%=data.getString("d_one")%>|<%=data.getString("d_two")%>|<%=data.getString("d_three")%>
<%  } %>

