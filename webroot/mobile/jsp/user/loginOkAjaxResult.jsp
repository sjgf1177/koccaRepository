<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.RequestManager" %>

<%
    RequestBox box = RequestManager.getBox(request);
    DataBox dbox = (DataBox)request.getAttribute("loginResult");

    StringBuffer result = new StringBuffer();
    result.setLength(0);

    String userid = "";

    try {
        if ( dbox != null && !dbox.isEmpty() ) {
            userid = dbox.getString("d_userid");
            result.append("{    \n");
            result.append(" \"loginResult\": true,  \n");
            result.append(" \"userId\": \"" + dbox.getString("d_userid") + "\",  \n");
            result.append(" \"userName\": \"" + dbox.getString("d_name") + "\"  \n");
            result.append("}");
        } else {
            result.append("{    \n");
            result.append(" \"loginResult\": false  \n");
            result.append("}");
        }

        System.out.println(result.toString());
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( result.toString() );
%>
