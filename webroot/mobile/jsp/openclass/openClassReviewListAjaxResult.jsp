<%@ page contentType="application/json;charset=euc-kr" %>
<%@ page import = "com.credu.library.RequestBox" %>
<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "com.credu.library.StringManager" %>
<%@ page import = "java.util.ArrayList" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    DataBox dbox = null;

    ArrayList openClassReviewList = (ArrayList)request.getAttribute("openClassReviewList");

    StringBuffer result = new StringBuffer();

    int num = 0;
    int checkpoin = 0;
    int userRnk = 0;
    int rnk = 0;
    int cnt = 0;
    String name = "";
    String cont = "";
    String regdt = "";
    String regid = "";
    try {
        if (openClassReviewList == null && openClassReviewList.size() > 0 ) {
            result.append("{result: null}");
        } else {
            result.append("[\n");
            for ( int i = 0; i < openClassReviewList.size(); i++) {
                dbox = (DataBox)openClassReviewList.get(i);
                num = dbox.getInt("d_num");
                checkpoin = dbox.getInt("d_checkpoin");
                userRnk = dbox.getInt("d_user_rnk");
                rnk = dbox.getInt("d_rnk");
                cnt = dbox.getInt("d_cnt");

                name = dbox.getString("d_name");
                cont = dbox.getString("d_cont").replaceAll("\r\n", "<br/>").replaceAll("\"", "'");
                regdt = dbox.getString("d_regdt");
                regid = dbox.getString("d_regid");

                result.append("{\n");
                result.append("\"num\": \"").append(num).append("\",\n");
                result.append("\"checkpoin\": \"").append(checkpoin).append("\",\n");
                result.append("\"userRnk\": \"").append(userRnk).append("\",\n");
                result.append("\"rnk\": \"").append(rnk).append("\",\n");
                result.append("\"cnt\": \"").append(cnt).append("\",\n");
                result.append("\"name\": \"").append(name).append("\",\n");
                result.append("\"cont\": \"").append(cont).append("\",\n");
                result.append("\"regdt\": \"").append(regdt).append("\",\n");
                result.append("\"regid\": \"").append(regid).append("\"\n");
                result.append("}");
                if ( i < openClassReviewList.size() - 1) {
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

