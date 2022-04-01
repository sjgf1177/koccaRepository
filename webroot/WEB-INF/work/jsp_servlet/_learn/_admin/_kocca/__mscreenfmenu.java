package jsp_servlet._learn._admin._kocca;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import java.text.*;
import com.credu.library.*;
import com.credu.system.*;

public final class __mscreenfmenu extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

    private static void _releaseTags(javax.servlet.jsp.tagext.JspTag t) {
        while (t != null) {
            if(t instanceof javax.servlet.jsp.tagext.Tag) {
                javax.servlet.jsp.tagext.Tag tmp = (javax.servlet.jsp.tagext.Tag)t;
                t = ((javax.servlet.jsp.tagext.Tag) t).getParent();
                try {
                    tmp.release();
                } catch(Exception ignore) {}
            }
            else {
                t = ((javax.servlet.jsp.tagext.SimpleTag)t).getParent();
            }
        }
    }

    public boolean _isStale(){
        boolean _stale = _staticIsStale((weblogic.servlet.jsp.StaleChecker) getServletConfig().getServletContext());
        return _stale;
    }

    public static boolean _staticIsStale(weblogic.servlet.jsp.StaleChecker sci) {
        if (sci.isResourceStale("/learn/admin/kocca/mScreenFmenu.jsp", 1422235940938L ,"9.2.3.0","Asia/Seoul")) return true;
        return false;
    }

    private static void _writeText(javax.servlet.ServletResponse rsp, javax.servlet.jsp.JspWriter out, String block, byte[] blockBytes) 
    throws java.io.IOException {
        if (!_WL_ENCODED_BYTES_OK || _hasEncodingChanged(rsp)){
            out.print(block);
        } else {
            ((weblogic.servlet.jsp.ByteWriter)out).write(blockBytes, block);
        }
    }

    private static boolean _hasEncodingChanged(javax.servlet.ServletResponse rsp){
        if (_WL_ORIGINAL_ENCODING.equals(rsp.getCharacterEncoding())){
            return false;
        }
        return true;
    }

    private static boolean _WL_ENCODED_BYTES_OK = true;
    private static final String _WL_ORIGINAL_ENCODING = "euc-kr";

    private static byte[] _getBytes(String block){
        try {
            return block.getBytes(_WL_ORIGINAL_ENCODING);
        } catch (java.io.UnsupportedEncodingException u){
            _WL_ENCODED_BYTES_OK = false;
        }
        return null;
    }

    private final static String  _wl_block0 ="\r\n";
    private final static byte[]  _wl_block0Bytes = _getBytes( _wl_block0 );

    private final static String  _wl_block1 ="\r\n<!doctype html>\r\n<html lang=\"ko\">\r\n<head>\r\n<title>Management.";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 =".";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 =".</title>\r\n<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=KS_C_5601\">\r\n\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/menu_style.css\">\r\n\r\n<script type=\"text/javascript\" src = \"/script/user_patch.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/jquery-1.3.2.min.js\"></script>\r\n<script type=\"text/javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<script language = \"VBScript\" src = \"/script/cresys_lib.vbs\"></script>\r\n\r\n<script type=\"text/javascript\">\r\n    var vals = \"\";\r\n    var last_menu = \"\";\r\n    //var timeout;\r\n    //var  oPopup = window.createPopup();\r\n    //oPopup.document.createStyleSheet(\'/css/menu_style.css\');\r\n\r\n    function SystemChange(){\r\n        document.form1.submit();\r\n    }\r\n\r\n    function openWin(url){\r\n        //alert(\"test\");\r\n        window.open(url,\"win\",\"toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=1024,height=768\");\r\n    }\r\n\r\n    function whenMenuClicked(url){\r\n        parent.fbody.location=\"loading.jsp\";\r\n    }\r\n\r\n    function fnShowMenu(obj, menu_cd) {\r\n\r\n        document.form1.parent_code.value = menu_cd;\r\n        parent.fleft.fnShowSunmenu(menu_cd);\r\n\r\n    }\r\n    function fnlogout(){\r\n        if (confirm(\"로그아웃 하시겠습니까?\")) {\r\n            parent.location.href=\"/learn/admin/kocca/logout.jsp\";\r\n\r\n        }\r\n    }\r\n\r\n</script>\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=0 marginheight=0>\r\n<form name=\"form1\" method=\"post\" action=\"mScreenFmenu.jsp\">\r\n<input type=\"hidden\" name =\"parent_code\" value =\"\">\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\"/images/admin/system/admin_main_top_bg.gif\">\r\n    <tr>\r\n        <!--\r\n        <td bgcolor=\"7B6AAE\"><img src=\"/images/admin/system/admin_main_top_01.gif\"></td>\r\n        -->\r\n        <td><img src=\"/images/admin/system/admin_main_top_01.gif\" width=\"260\" height=\"66\">";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 =" ";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="님 | \r\n            <input type=\"button\"  onclick=\"javascript:fnlogout();\" value=\"로그아웃\">            \r\n        <td align=\"right\"><img src=\"/images/admin/system/admin_main_top_02.gif\" width=\"274\" height=\"66\"></td>\r\n    </tr>\r\n</table>\r\n\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n    <tr>\r\n        <td><img src=\"/images/img/memu_head.gif\"></td>\r\n        <td class=\"menubg\">\r\n            <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr>\r\n";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\r\n                    <td class=\"menu\" id=\"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\"><a href=\"javascript:fnShowMenu(this, \'";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\');\" onfocus=\"blur()\">";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="</a></td>\r\n";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n                </tr>\r\n            </table>\r\n        </td>\r\n        <td width=\"800\"><img src=\"/images/img/memu_tail.gif\"></td>\r\n    </tr>\r\n</table>\r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    static private javelin.jsp.JspFunctionMapper _jspx_fnmap = javelin.jsp.JspFunctionMapper.getInstance();

    public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
    throws javax.servlet.ServletException, java.io.IOException {

        javax.servlet.ServletConfig config = getServletConfig();
        javax.servlet.ServletContext application = config.getServletContext();
        javax.servlet.jsp.tagext.JspTag _activeTag = null;
        Object page = this;
        javax.servlet.jsp.JspWriter out;
        javax.servlet.jsp.PageContext pageContext = javax.servlet.jsp.JspFactory.getDefaultFactory().getPageContext(this, request, response, "/learn/library/error.jsp", true , 8192 , true );
        response.setHeader("Content-Type", "text/html;charset=euc-kr");
        out = pageContext.getOut();
        javax.servlet.jsp.JspWriter _originalOut = out;
        javax.servlet.http.HttpSession session = request.getSession( true );
        try {;
            response.setContentType("text/html;charset=euc-kr");

//**********************************************************
//  1. 제      목: manager screen -display MenuApplet
//  2. 프로그램명 : za_Menu_L.jsp
//  3. 개      요: 메뉴 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 8
//  7. 수      정:
//***********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            com.credu.system.MenuAdminBean myMenuBean= null;{
                myMenuBean=(com.credu.system.MenuAdminBean)pageContext.getAttribute("myMenuBean");
                if(myMenuBean==null){
                    myMenuBean=new com.credu.system.MenuAdminBean();
                    pageContext.setAttribute("myMenuBean",myMenuBean);

                }
            }
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}

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


            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gadmin
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gadminnm
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(name
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}

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

            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(m_parent
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(m_parent
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(m_menuNm
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}

        }
    }

            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
