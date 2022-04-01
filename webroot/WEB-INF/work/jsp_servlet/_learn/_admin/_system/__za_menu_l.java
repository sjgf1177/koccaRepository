package jsp_servlet._learn._admin._system;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.credu.system.*;
import com.credu.library.*;

public final class __za_menu_l extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/system/za_Menu_L.jsp", 1422235945228L ,"9.2.3.0","Asia/Seoul")) return true;
        if (sci.isResourceStale("/learn/library/getJspName.jsp", 1422235939253L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n\r\n";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n\r\n<link rel=\"stylesheet\" type=\"text/CSS\" href=\"/css/admin_style.css\">\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n    function move_list(menu, parent, levels) {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_searchtext.value = \"\";\r\n        document.form1.p_levels.value     = levels;\r\n        document.form1.p_upper.value      = menu;\r\n        document.form1.p_parent.value     = parent;\r\n        document.form1.p_process.value    = \"select\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function view(menu) {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_menu.value   = menu;\r\n        document.form1.p_process.value = \"selectView\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function insert() {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_process.value = \"insertPage\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function search() {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_process.value = \"select\";\r\n        document.form1.submit();\r\n    }\r\n\r\n//-->\r\n</SCRIPT>\r\n</head>\r\n\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\">\r\n    <input type = \"hidden\" name = \"p_process\" value = \"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\">\r\n    <input type = \"hidden\" name = \"p_grcode\"  value = \"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\">\r\n    <input type = \"hidden\" name = \"p_menu\"    value = \"\">\r\n    <input type = \"hidden\" name = \"p_levels\"  value = \"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\">\r\n    <input type = \"hidden\" name = \"p_upper\"   value = \"";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\">\r\n    <input type = \"hidden\" name = \"p_parent\"  value = \"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\">\r\n\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"665\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\"> \r\n\r\n\r\n      <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr> \r\n          <td><img src=\"/images/admin/system/unite_title01.gif\" ></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n      <!----------------- title 끝 ----------------->\r\n      <br>\r\n        <table class=\"form_table_out\" cellspacing=\"0\" cellpadding=\"1\">\r\n          <tr> \r\n            <td align=\"center\"> \r\n              <table class=\"form_table_bg\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr> \r\n                  <td height=\"7\"></td>\r\n                </tr>\r\n                <tr> \r\n                  <td height=\"26\" style=\"padding-left=10;padding-right=10\">";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 =" </td>\r\n                </tr>\r\n              </table>\r\n              <table class=\"form_table_bg\" cellspacing=\"0\" cellpadding=\"0\" >\r\n                <tr valign=\"middle\"> \r\n                  <td width=\"200\" height=\"26\" style=\"padding-left=10;padding-right=10\">";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="&nbsp;&nbsp; </td>\r\n                  <td width=\"490\" align=\"right\">메뉴명 \r\n                    <input name=\"p_searchtext\" type=\"text\" class=\"input\" value=\"";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\"> \r\n                  </td>\r\n                  <td width=\"245\" style=\"padding-left=10;padding-right=10\"> \r\n                    <a href=\'javascript:search()\'><img src=\"/images/admin/button/btn_inquiry.gif\" border=0></a>\r\n                    <a href=\'javascript:insert()\'><img src=\"/images/admin/button/btn_add.gif\" border=0></a>\r\n                    <a href=\'/servlet/controller.system.MenuAuthAdminServlet?p_process=updatePage&p_grcode=";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\'><img src=\"/images/admin/button/btn_menuright.gif\" border=0></a>\r\n                  </td>\r\n                </tr>\r\n                <tr> \r\n                  <td height=\"7\" colspan=\"3\"></td>\r\n                </tr>\r\n              </table></td>\r\n          </tr>\r\n        </table>\r\n        <br>\r\n<script type=\'text/javascript\' src=\'/script/jquery-1.3.2.min.js\'></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n//관심버튼 클릭\r\nfunction whenSimpleSave(menu) {\r\n\r\n\t$.post(\"/servlet/controller.common.AjaxServlet\"\r\n\t\t\t, {\t sqlNum:\"adminMenu.updateUse\",rerurnFunction:\"search\"\r\n\t\t\t\t, p_menu:menu\r\n\t\t\t}\r\n\t\t\t, function(data) {\r\n\t\t\t\t$(\"#ajaxDiv\").html(data);\r\n\t\t\t});\r\n}\r\n//-->\r\n</SCRIPT>\r\n<div id=\"ajaxDiv\"></div>\r\n\r\n        <!----------------- List 시작 ----------------->\r\n        <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n          <tr> \r\n            <td colspan=\"6\" class=\"table_top_line\"></td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"25\" class=\"table_title\"><b>메뉴코드</b></td>\r\n            <td width=\"25%\" class=\"table_title\"><b>메뉴코드명</b></td>\r\n            <td width=\"30%\" class=\"table_title\"><b>관련 Program</b></td>\r\n            <td width=\"10%\" class=\"table_title\"><b>메뉴순서</b></td>\r\n            <td width=\"10%\" class=\"table_title\"><b>사용여부</b></td>\r\n            <td width=\"10%\" class=\"table_title\"><b>하위코드</b></td>\r\n          </tr>\r\n\r\n";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\r\n          <tr> \r\n            <td height=\"25\" class=\"table_01\"> ";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 =" </td>\r\n            <td class=\"table_02_1\"><a href=\"javascript:view(\'";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="\')\">";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="</a></td>\r\n            <td class=\"table_02_1\">";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="</td>\r\n            <td class=\"table_02_1\">";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="</td>\r\n            <td class=\"table_02_1\"><a href=\"javascript:whenSimpleSave(\'";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="</a></td>\r\n            <td class=\"table_03_1\"><a href=\"javascript:move_list(\'";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\',\'";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="\')\"><img src=\"/images/admin/button/b_downcode.gif\" align=\"absmiddle\" border=\"0\"></a></td>\r\n          </tr>\r\n";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="\r\n\r\n        </table>\r\n        <!----------------- List 끝 ----------------->\r\n      </td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 ="\r\n    ";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 =" \r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

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
//  1. 제      목: 메뉴 리스트
//  2. 프로그램명 : za_Menu_L.jsp
//  3. 개      요: 메뉴 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 6
//  7. 수      정:
//***********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

                }
            }
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_searchtext    = box.getString("p_searchtext");

//    String v_grcode = box.getString("p_grcode");
    String v_grcode = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_upper  = box.getString("p_upper");
    String v_parent = box.getString("p_parent");
    int    v_levels = box.getInt("p_levels");
    if (v_levels == 0) {v_levels=1;}
    if (v_levels == 1) {v_upper = "";}

    /*==========   상위메뉴정보     ======================*/
    String v_up_menunm = "";
    String v_up_upper  =  "";
    String v_up_parent =  "";
    String v_printupper   = "";
    String v_printuppernm = "";

    if (!v_upper.equals("")) {
        MenuData updata  = MenuAdminBean.getMenuInfo(v_grcode,v_upper);
        if (updata != null) {
            v_up_menunm =  updata.getMenunm();
            v_up_upper  =  updata.getUpper();
            v_up_parent =  updata.getParent();

            v_printupper   = "상위메뉴코드 : <a href=\"javascript:move_list('" + v_up_upper + "', '" + v_up_parent + "', '" + (v_levels-1) + "')\"><strong>" + v_upper + "</strong></a>";
            v_printuppernm = "상위메뉴명 : " + v_up_menunm ;
        }
    } else {
        v_parent = "";         // 최상위단계에서는 부모값이 없음
    }

    String v_menu        = "";
    String v_menunm      = "";
    String v_pgm         = "";
    String v_para1       = "";
    String v_para2       = "";
    String v_para3       = "";
    String v_para4       = "";
    String v_para5       = "";
    String v_para6       = "";
    String v_para7       = "";
    String v_para8       = "";
    String v_para9       = "";
    String v_para10      = "";
    String v_para11      = "";
    String v_para12      = "";
    String v_isdisplay   = "";
    String v_isdisplaynm = "";
    int    v_orders      = 0;

    ArrayList list = (ArrayList)request.getAttribute("selectList");

            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_process 
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_levels
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_upper
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_parent
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_printupper
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_printuppernm
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_searchtext
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}

            for(int i = 0; i < list.size(); i++) {

                MenuData data  = (MenuData)list.get(i);
                v_menu      = data.getMenu();
                v_menunm    = data.getMenunm();
                v_upper     = data.getUpper();
                v_parent    = data.getParent();
                v_pgm       = data.getPgm();
                v_para1     = data.getPara1();
                v_para2     = data.getPara2();
                v_para3     = data.getPara3();
                v_para4     = data.getPara4();
                v_para5     = data.getPara5();
                v_para6     = data.getPara6();
                v_para7     = data.getPara7();
                v_para8     = data.getPara8();
                v_para9     = data.getPara9();
                v_para10    = data.getPara10();
                v_para11    = data.getPara11();
                v_para12    = data.getPara12();
                v_isdisplay = data.getIsdisplay();

                v_orders    = data.getOrders();

                if (v_isdisplay.equals("Y")) v_isdisplaynm = "사용";
                else v_isdisplaynm = "미사용";


            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menunm
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_pgm+" &"+v_para1+"&"+v_para2+"&"+v_para3+"&"+v_para4+"&"+v_para5+"&"+v_para6+" &"+v_para7+"&"+v_para8+"&"+v_para9+"&"+v_para10+"&"+v_para11+"&"+v_para12
));
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_orders
));
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_isdisplaynm
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_parent
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_levels+1
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}

        }

            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}

//**********************************************************
//  1. 제      목: 페이지명을 출력하는 include 화면
//  2. 프로그램명: getJspName.jsp
//  3. 개      요: 페이지명을 출력하는 include 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 09
//  7. 수      정: 
//**********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
 
        if(conf.getProperty("jsp.name.view").equals("true")) {      
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
  }   
            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}
            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
