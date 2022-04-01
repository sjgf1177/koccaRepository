<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<%
DBConnectionManager connMgr = null;

ListSet ls = null;
ArrayList list = null;
String sql = "";

//pool name : jdbc/zpack

try {

connMgr = new DBConnectionManager();
list = new ArrayList();


sql = " select * from ( select rownum rnum,  idx,title,url  from tbSearchData ) where rnum < 101";

ls = connMgr.executeQuery(sql);

while (ls.next()) {
	out.println("idx = " + ls.getString("idx") + "<br>");
	out.println(" / title = " + ls.getString("title") + "<br>");
	out.println(" / url = " + ls.getString("url") + "<br>");
    }

} catch (Exception ex) {
        ErrorManager.getErrorStackTrace(ex, null, sql);
	throw new Exception(ex.getMessage());
} finally {
	if (ls != null) { try {ls.close();} catch (Exception e) {}}
	if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
}

%>

