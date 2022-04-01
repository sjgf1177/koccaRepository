<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.templet.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) {
	    box = RequestManager.getBox(request);
	}

	DataBox dbox = (DataBox)request.getAttribute("SubMainTemplet");

	if(dbox.getString("d_MENUIMG") != null) {
%>
	<script language="javascript">
		parent.document.getElementById('p_grtype').value = '<%=box.getString("p_grcode")%>';
		parent.document.getElementById('p_gubun').value = '<%=box.getString("p_gubun")%>';
		parent.document.getElementById('p_menuname').value = '<%=dbox.getString("d_MENUNAME")%>';
		parent.document.getElementById('p_menuurl').value = '<%=dbox.getString("d_MENUURL")%>';
		parent.document.getElementById('p_menuimg').value = '<%=dbox.getString("d_MENUIMG")%>';
		parent.document.getElementById('p_menuoverimg').value = '<%=dbox.getString("d_MENUOVERIMG")%>';
		parent.document.getElementById('p_subimg').value = '<%=dbox.getString("d_SUBIMG")%>';
		parent.document.getElementById('p_suboverimg').value = '<%=dbox.getString("d_SUBOVERIMG")%>';
		parent.document.getElementById('p_flashfilename').value = '<%=dbox.getString("d_FLASHFILENAME")%>';
		parent.document.getElementById('p_menuhomeimg').value = '<%=dbox.getString("d_MENUHOMEIMG")%>';
		parent.document.getElementById('p_menuxposition').value = '<%=dbox.getString("d_menuxposition")%>';

//		parent.document.getElementById('isnew').value = '1';MENUXPOSITION
		
		parent.modFormDP();
	</script>
<%
	} else {
%>
	<script language="javascript">
		parent.document.getElementById('p_grtype').value = '<%=box.getString("p_grcode")%>';
		parent.document.getElementById('p_gubun').value = '<%=box.getString("p_gubun")%>';
		parent.document.getElementById('p_menuname').value = '<%=dbox.getString("d_MENUNAME")%>';
		parent.document.getElementById('p_menuurl').value = '<%=dbox.getString("d_MENUURL")%>';
		parent.document.getElementById('p_menuimg').value = '';
		parent.document.getElementById('p_menuoverimg').value = '';
		parent.document.getElementById('p_subimg').value = '';
		parent.document.getElementById('p_suboverimg').value = '';
		parent.document.getElementById('p_flashfilename').value = '';
		parent.document.getElementById('p_menuhomeimg').value = '';
		parent.document.getElementById('p_menuxposition').value = '';

//		parent.document.getElementById('isnew').value = '0';
		
		parent.modFormDP();
	</script>
<%	
	}
%>