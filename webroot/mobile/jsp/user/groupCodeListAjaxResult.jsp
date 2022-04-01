<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.RequestManager" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>
<%
    // RequestBox box = (RequestBox)request.getAttribute("requestbox");
    RequestBox box = RequestManager.getBox(request);
    DataBox dbox = null;

    ArrayList groupCodeList = (ArrayList)request.getAttribute("groupCodeList");

    StringBuffer result = new StringBuffer();

    String grcode = "";
    String grcodenm = "";
    try {
        if (groupCodeList == null && groupCodeList.size() > 0 ) {
            result.append("{result: null}");
        } else {
            result.append("[\n");
            for ( int i = 0; i < groupCodeList.size(); i++) {
                dbox = (DataBox)groupCodeList.get(i);
                grcode = dbox.getString("d_grcode");
                grcodenm = dbox.getString("d_grcodenm");

                result.append("{\n");
                result.append("\"grcode\": \"").append(grcode).append("\",\n");
                result.append("\"grcodenm\": \"").append(grcodenm).append("\"\n");
                result.append("}");
                if ( i < groupCodeList.size() - 1) {
                    result.append(",\n");
                }
            }
            result.append("\n]");
        }

        // System.out.println(result.toString());
    } catch (Exception e) {
        System.out.println("!!!!!!!!!!!" + e);
    }

    out.println( result.toString() );
%>

