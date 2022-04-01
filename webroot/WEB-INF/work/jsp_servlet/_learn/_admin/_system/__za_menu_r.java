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

public final class __za_menu_r extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/system/za_Menu_R.jsp", 1422235945274L ,"9.2.3.0","Asia/Seoul")) return true;
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
    private static final String _WL_ORIGINAL_ENCODING = "MS949";

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

    private final static String  _wl_block2 ="\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n\r\n<link rel=\"stylesheet\" type=\"text/CSS\" href=\"/css/admin_style.css\">\r\n<script language = \"javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n    function modify_menu() {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_process.value = \"updatePage\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function delete_menu() {\r\n        document.form1.p_searchtext.value = \"\";\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_process.value = \"delete\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function list_menu() {\r\n        document.form1.action = \"/servlet/controller.system.MenuAdminServlet\";\r\n        document.form1.p_process.value = \"select\";\r\n        document.form1.submit();\r\n    }\r\n//-->\r\n</SCRIPT>\r\n</head>\r\n\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\">\r\n    <input type = \"hidden\" name = \"p_process\" value = \"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\">\r\n    <input type = \"hidden\" name = \"p_grcode\"  value = \"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\">\r\n    <input type = \"hidden\" name = \"p_levels\"  value = \"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\">\r\n    <input type = \"hidden\" name = \"p_upper\"   value = \"";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\">\r\n    <input type = \"hidden\" name = \"p_parent\"  value = \"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\">\r\n    <input type = \"hidden\" name = \"p_searchtext\"  value = \"";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\">\r\n    <input type = \"hidden\" name = \"p_menu\"  value = \"";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"665\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\"> \r\n\r\n      <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr> \r\n          <td><img src=\"/images/admin/system/unite_title01.gif\" ></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n\t   <!----------------- title 끝 ----------------->\r\n      <br>\r\n        <!----------------- form 시작 ----------------->\r\n        <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n          <tr> \r\n            <td colspan=\"2\" class=\"table_top_line\"></td>\r\n          </tr>\r\n          ";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n          ";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\r\n          <tr class=\"table_02_2\"> \r\n            <td height=\"26\" class=\"table_title\" ><strong>메뉴코드</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"26\" class=\"table_title\"><strong>메뉴코드명</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td height=\"26\" class=\"table_title\"><strong>관련 Program</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td height=\"26\" class=\"table_title\"><strong>Parameter</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="&";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"26\" class=\"table_title\"><strong>메뉴순서</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="</td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"26\" class=\"table_title\"><strong>사용여부</strong></td>\r\n            <td height=\"26\" align=\"left\" valign=\"absmiddle\" class=\"table_02_2\">";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="</td>\r\n          </tr>\r\n        </table>\r\n\r\n        <br>\r\n        <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr> \r\n            <td align=\"right\" class=\"ms\"><a href=\"javascript:modify_menu()\"><img src=\"/images/admin/button/btn_modify.gif\" border=\"0\"></a></td>\r\n            <td width=\"1%\" align=\"center\" class=\"ms\">&nbsp;</td>\r\n            <td width=\"50\" align=\"center\" class=\"ms\"><a href=\"javascript:delete_menu()\"><img src=\"/images/admin/button/btn_del.gif\" border=\"0\"></a></td>\r\n            <td width=\"1%\" align=\"center\" class=\"ms\">&nbsp;</td>\r\n            <td align=\"left\" class=\"ms\"><a href=\"javascript:list_menu()\"><img src=\"/images/admin/button/btn_list.gif\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table> \r\n        <!----------------- form 끝 ----------------->\r\n\r\n        <br>\r\n    </td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="\r\n    ";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 =" \r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    static private javelin.jsp.JspFunctionMapper _jspx_fnmap = javelin.jsp.JspFunctionMapper.getInstance();

    public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
    throws javax.servlet.ServletException, java.io.IOException {

        javax.servlet.ServletConfig config = getServletConfig();
        javax.servlet.ServletContext application = config.getServletContext();
        javax.servlet.jsp.tagext.JspTag _activeTag = null;
        Object page = this;
        javax.servlet.jsp.JspWriter out;
        javax.servlet.jsp.PageContext pageContext = javax.servlet.jsp.JspFactory.getDefaultFactory().getPageContext(this, request, response, "/learn/library/error.jsp", true , 8192 , true );
        response.setHeader("Content-Type", "text/html;charset=MS949");
        out = pageContext.getOut();
        javax.servlet.jsp.JspWriter _originalOut = out;
        javax.servlet.http.HttpSession session = request.getSession( true );
        try {;
            response.setContentType("text/html;charset=MS949");

//**********************************************************
//  1. 제      목: 메뉴관리
//  2. 프로그램명 : za_Menu_R.jsp
//  3. 개      요: 메뉴관리 상세보기
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
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

                }
            }
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}


    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String v_searchtext = box.getString("p_searchtext");

    String v_grcode = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_upper  = box.getString("p_upper");
    String v_parent = box.getString("p_parent");
    int    v_levels = box.getInt("p_levels");

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

            v_printupper   = "          <tr>                                                                                   ";
            v_printupper  += "            <td width='15%' height='26' class='table_title'><strong>상위메뉴코드</strong></td>     ";
            v_printupper  += "            <td class='table_02_2'>"+v_upper+"</td>                                              ";
            v_printupper  += "          </tr>                                                                                  ";

            v_printuppernm   = "          <tr>                                                                                 ";
            v_printuppernm  += "            <td width='15%' height='26' class='table_title'><strong>상위메뉴코드명</strong></td> ";
            v_printuppernm  += "            <td class='table_02_2'>"+v_up_menunm+"</td>                                        ";
            v_printuppernm  += "          </tr>                                                                                ";
        }
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
    String v_systemgubun = "";
    String v_systemnm    = "";
    int    v_orders      = 0;

    MenuData data = (MenuData)request.getAttribute("selectMenu");

    if (data!=null) {
		v_menu      = data.getMenu();
		v_menunm    = data.getMenunm();
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
		v_systemgubun = data.getSystemgubun();
   }
    if (v_isdisplay.equals("Y")) v_isdisplaynm = "사용";
    else v_isdisplaynm = "미사용";
    
//    if (v_systemgubun.equals("1")) v_systemnm = "메인시스템";
//    else if(v_systemgubun.equals("2")) v_systemnm= "게이트시스템";

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
            out.print( weblogic.utils.StringUtils.valueOf(v_searchtext
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_printupper
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_printuppernm
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menunm
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_pgm
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para1
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para2
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para3
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para4
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para5
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para6
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para7
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para8
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para9
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para10
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para11
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_para12
));
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_orders
));
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_isdisplaynm
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}

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
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
  }   
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
