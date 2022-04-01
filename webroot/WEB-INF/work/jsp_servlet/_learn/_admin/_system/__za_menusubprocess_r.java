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

public final class __za_menusubprocess_r extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/system/za_MenuSubProcess_R.jsp", 1422235945664L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n\r\n<link rel=\"stylesheet\" type=\"text/CSS\" href=\"/css/admin_style.css\">\r\n<script language = \"javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n    function modify_menusubprocess() {\r\n        document.form1.action = \"/servlet/controller.system.MenuSubProcessAdminServlet\";\r\n        document.form1.p_process.value = \"updatePage\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function delete_menusubprocess() {\r\n        document.form1.p_searchtext.value = \"\";\r\n        document.form1.action = \"/servlet/controller.system.MenuSubProcessAdminServlet\";\r\n        document.form1.p_process.value = \"delete\";\r\n        document.form1.submit();\r\n    }\r\n\r\n    function list_menusubprocess() {\r\n        document.form1.action = \"/servlet/controller.system.MenuSubProcessAdminServlet\";\r\n        document.form1.p_process.value = \"select\";\r\n        document.form1.submit();\r\n    }\r\n//-->\r\n</SCRIPT>\r\n</head>\r\n\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\">\r\n    <input type = \"hidden\" name = \"p_process\"      value = \"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\">\r\n    <input type = \"hidden\" name = \"p_searchtext\"   value = \"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\">\r\n    <input type = \"hidden\" name = \"p_grcode\"       value = \"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\">\r\n    <input type = \"hidden\" name = \"p_menu\"         value = \"";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\">\r\n    <input type = \"hidden\" name = \"p_seq\"          value = \"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\">\r\n    <input type = \"hidden\" name = \"p_modulenm\"     value = \"";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\">\r\n    <input type = \"hidden\" name = \"p_processvalue\" value = \"";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"665\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\"> \r\n\r\n      <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr> \r\n          <td><img src=\"/images/admin/system/unite_title02.gif\" ></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n\t  <!----------------- title 끝 ----------------->\r\n\r\n\r\n      <br>\r\n        <!----------------- form 시작 ----------------->\r\n        <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n          <tr> \r\n            <td colspan=\"2\" class=\"table_top_line\"></td>\r\n          </tr>\r\n          <tr class=\"table_02_2\"> \r\n            <td height=\"26\" class=\"table_title\" ><strong>메뉴코드</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"26\" class=\"table_title\"><strong>메뉴코드명</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="</td>\r\n          </tr>\r\n          <tr class=\"table_02_2\"> \r\n            <td height=\"26\" class=\"table_title\" ><strong>모듈번호</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"26\" class=\"table_title\"><strong>모듈명</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td height=\"26\" class=\"table_title\">프로세스</td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td height=\"26\" class=\"table_title\"><strong>서블릿타입</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="</td>\r\n          </tr>\r\n          <tr> \r\n            <td width=\"15%\" height=\"26\" class=\"table_title\"><strong>함수명</strong></td>\r\n            <td height=\"25\" class=\"table_02_2\">";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="</td>\r\n          </tr>\r\n        </table>\r\n\r\n        <br>\r\n        <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr> \r\n            <td align=\"right\" class=\"ms\"><a href=\"javascript:modify_menusubprocess()\"><img src=\"/images/admin/button/btn_modify.gif\" border=\"0\"></a></td>\r\n            <td width=\"1%\" align=\"center\" class=\"ms\">&nbsp;</td>\r\n            <td width=\"50\" align=\"center\" class=\"ms\"><a href=\"javascript:delete_menusubprocess()\"><img src=\"/images/admin/button/btn_del.gif\"  border=\"0\"></a></td>\r\n            <td width=\"1%\" align=\"center\" class=\"ms\">&nbsp;</td>\r\n            <td align=\"left\" class=\"ms\"><a href=\"javascript:list_menusubprocess()\"><img src=\"/images/admin/button/btn_list.gif\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table> \r\n        <!----------------- form 끝 ----------------->\r\n        <br>\r\n    </td>\r\n  </tr>\r\n</table>\r\n\r\n\r\n";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n    ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 =" \r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

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
//  1. 제      목: 프로세스 관리
//  2. 프로그램명 : za_MenuSubProcess_I.jsp
//  3. 개      요: 프로세스 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
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

    String v_searchtext   = box.getString("p_searchtext");
    String v_grcode       = CodeConfigBean.getConfigValue("cur_nrm_grcode");
    String v_menu         = box.getString("p_menu");
    String v_menunm       = MenuAdminBean.getMenuName(v_grcode, v_menu);
    int v_seq             = box.getInt("p_seq");
    String v_modulenm     = box.getString("p_modulenm");
    String v_processvalue = box.getString("p_processvalue");

    String v_servlettype     = "";
    String v_method          = "";
	String v_servlettypeview = "";

    MenuSubProcessData data = (MenuSubProcessData)request.getAttribute("selectMenuSubProcess");

    v_servlettype = data.getServlettype();
    v_method      = data.getMethod();
	if (v_servlettype.equals("1"))      v_servlettypeview = "조회";
	else if (v_servlettype.equals("2")) v_servlettypeview = "조회(쓰기액션있음)";
	else if (v_servlettype.equals("4")) v_servlettypeview = "쓰기액션";


            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_process 
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_searchtext
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_seq
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_modulenm
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_processvalue
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menu
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_menunm
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_seq
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_modulenm
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_processvalue
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_servlettype
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_method
));
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}

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
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
  }   
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
