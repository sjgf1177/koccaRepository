<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id="myMenuBean" class="com.credu.system.MenuAdminBean" scope="page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

/*============ 테스트용 ======================*/
//    box.setSession("userid","lee1");
//    box.setSession("gadmin","A1");
//    box.setSession("resno","6906031010912");
//    box.setSession("name","김연남");
//    box.setSession("email","jeong@credu.com");
//    box.setSession("usergubun","KDGI");
/*============ 테스트용 ======================*/

    String v_gadmin = box.getSession("gadmin");
    String v_systemgubun = box.getString("p_systemgubun");

    ArrayList newSelectList = (ArrayList)myMenuBean.subSelectList(box);
    DataBox sdbox = null;

    String last_parent = "";
    int last_level = 0;

    String fstIndex ="";

    if ( newSelectList.size() > 0 ) {
        sdbox = (DataBox)newSelectList.get(0);
        fstIndex = sdbox.getString("d_parent");
    }

%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/json2.js"></script>

<script language="Javascript">
<!--

    $(document).ready( function() {
        fnShowSunmenu("<%=fstIndex%>");
    });


    function fnShowSunmenu(parent_code){

        var listArea = $("#newList");
        var listStr = "";
        var i = 0;
        $.ajaxSetup({cache:false});
        $.ajax({
                type: "get"
            ,   url: "/servlet/controller.system.MenuAdminServlet"
            ,   dataType: "json"
            ,   data: "p_process=subMenuSelect&parent_code=" + parent_code
            ,   success: function(data){

                    var totCnt = 0;
                    var currCnt = data.subMenuList.length;

                    if ( currCnt > 0 ) {
                        $.each(data.subMenuList, function() {

                            if (this.d_menunm == "separator" ) {
                                listStr += "------------<br />";
                            } else {
                                if (this.d_levels == 1){
                                    listStr += "<br /><b>["+ this.d_menunm + "]</b><br /><br />"
                                } else {
                                    listStr += "<font style=\"font-size:12px; font-weight:bold;\"><a href=\""+ this.d_pgm + "\" target=\"fbody\">"+ this.d_menunm + "</a><br />";
                                }
                            }

                            $("#newList").append( listStr );

                        });
                        //alert(listStr);
                    } else {
                        listStr = "<li class=\"no-data\">메뉴를 볼 수 없습니다.</li>";
                    }

                    listArea.empty();
                    listArea.html( listStr );

                }
            ,   complete: function(arg1, arg2) {
                    // alert(arg1 + " : " + arg2);
                }
            ,   error: function(arg1, arg2) {
                }
        });

    }

-->
</script>
</head>

<body topmargin=0>
<form name="ff" method="post" action="" target="fbody">
</form><div id ="oldList">

</div>
<div id ="newList" style="line-height:24px;"></div>
</p>
</body>
</html>
