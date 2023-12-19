<%
//**********************************************************
//  1. 제      목: manager screen -display MenuApplet
//  2. 프로그램명 : za_Menu_L.jsp
//  3. 개      요: 메뉴 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
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
//    box.setSession("s_grcode","N000001");
//    box.setSession("s_gadmin","A1");
/*============ 테스트용 ======================*/
    String  v_grcode    = box.getSession("s_grcode");
    String  v_gadmin    = box.getSession("gadmin");
    String  v_gadminnm  = box.getSession("gadminnm");
    String  name      = box.getSession("name");
    String  v_systemgubun = box.getString("p_systemgubun");

    if (v_grcode.equals(""))     v_grcode    = "N000001";
    if (v_gadmin.equals(""))     v_grcode    = "ZZ";
    if (v_systemgubun.equals("") || v_systemgubun == null) v_systemgubun = "1";

    ArrayList list = (ArrayList)myMenuBean.subSelectList(box);
    MenuData data  = null;
    DataBox mdbox = null;
    String last_parent = "";
    int last_level = 0;

%>
<!doctype html>
<html lang="ko">
<head>
<title>Management.<%=v_grcode%>.<%=v_gadmin%>.</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=KS_C_5601">

<link rel="stylesheet" type="text/css" href="/css/menu_style.css">

<script type="text/javascript" src = "/script/user_patch.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<script type="text/javascript">
    var vals = "";
    var last_menu = "";
    //var timeout;
    //var  oPopup = window.createPopup();
    //oPopup.document.createStyleSheet('/css/menu_style.css');

    function SystemChange(){
        document.form1.submit();
    }

    function openWin(url){
        //alert("test");
        window.open(url,"win","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=1024,height=768");
    }

    function whenMenuClicked(url){
        parent.fbody.location="loading.jsp";
    }

    function fnShowMenu(obj, menu_cd) {

        document.form1.parent_code.value = menu_cd;
        parent.fleft.fnShowSunmenu(menu_cd);

    }
    function fnlogout(){
        if (confirm("로그아웃 하시겠습니까?")) {
            parent.location.href="/learn/admin/kocca/logout.jsp";

        }
    }


    var idleTime = 0;
    $(document).ready(function () { //Increment the idle time counter every minute.
        var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
        //일정시간 움직임이 있으면 초기화
        $(this).mousemove(function (e) { idleTime = 0; });
        $(this).keypress(function (e) { idleTime = 0; });
    });

    function timerIncrement() {
        idleTime = idleTime + 1;
        if (idleTime >= 120) { // 120 minutes
            parent.location.href="/learn/admin/kocca/autoLogout.jsp";
        }
        //console.log(idleTime);
    }

</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth=0 marginheight=0>
<form name="form1" method="post" action="mScreenFmenu.jsp">
<input type="hidden" name ="parent_code" value ="">
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="/images/admin/system/admin_main_top_bg.gif">
    <tr>
        <!--
        <td bgcolor="7B6AAE"><img src="/images/admin/system/admin_main_top_01.gif"></td>
        -->
        <td><img src="/images/admin/system/admin_main_top_01.gif" width="260" height="66"><%=v_gadminnm%> <%=name%>님 |
            <input type="button"  onclick="javascript:fnlogout();" value="로그아웃">
        <td align="right"><img src="/images/admin/system/admin_main_top_02.gif" width="274" height="66"></td>
    </tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td><img src="/images/img/memu_head.gif"></td>
        <td class="menubg">
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
<%
    int v_menu_cnt = 0, v_maxmenuItem_cnt=0;
    int v_menuNum=0, v_menuItemNum=0;
    String v_pa="";
    String m_parent = "";
    String m_menuNm = "";
    int m_levels = 0;

    for (int i=0;i<list.size(); i++){
        mdbox = (DataBox)list.get(i);

        m_levels = mdbox.getInt("d_levels");
        m_parent = mdbox.getString("d_parent");
        m_menuNm = mdbox.getString("d_menunm");

        if (m_levels==1){
//            v_menuNum++;
//            v_menuItemNum=0;
%>
                    <td class="menu" id="<%=m_parent%>"><a href="javascript:fnShowMenu(this, '<%=m_parent%>');" onfocus="blur()"><%=m_menuNm%></a></td>
<%
        }
    }
%>
                </tr>
            </table>
        </td>
        <td width="800"><img src="/images/img/memu_tail.gif"></td>
    </tr>
</table>
</form>
</body>
</html>
