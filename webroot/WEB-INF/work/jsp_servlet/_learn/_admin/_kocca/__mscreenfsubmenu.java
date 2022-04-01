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

public final class __mscreenfsubmenu extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/kocca/mScreenFsubMenu.jsp", 1422494632083L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<script src=\"/mobile/assets/js/jquery-1.11.1.min.js\"></script>\r\n<script src=\"/mobile/assets/js/common.js\"></script>\r\n<script src=\"/mobile/assets/js/json2.js\"></script>\r\n\r\n<script language=\"Javascript\">\r\n<!--\r\n    \r\n    $(document).ready( function() {\r\n        fnShowSunmenu(\"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\");        \r\n    });\r\n    \r\n    \r\n    function fnShowSunmenu(parent_code){\r\n\r\n        var listArea = $(\"#newList\");\r\n        var listStr = \"\";\r\n        var i = 0;\r\n        $.ajaxSetup({cache:false});\r\n        $.ajax({\r\n                type: \"get\"\r\n            ,   url: \"/servlet/controller.system.MenuAdminServlet\"\r\n            ,   dataType: \"json\"\r\n            ,   data: \"p_process=subMenuSelect&parent_code=\" + parent_code\r\n            ,   success: function(data){\r\n\r\n                    var totCnt = 0;\r\n                    var currCnt = data.subMenuList.length;\r\n\r\n                    if ( currCnt > 0 ) {\r\n                        $.each(data.subMenuList, function() {\r\n\r\n//                            alert(this.d_pgm );\r\n                            \r\n                            if (this.d_menunm == \"separator\" ) {\r\n                                listStr += \"------------</br>\";\r\n                            }else{\r\n                                if (this.d_levels == 1){\r\n                                    listStr += \"</br><b>\"+this.d_menunm + \"</br></br>\"\r\n                                } else {\r\n                                    listStr += \"<font style=\\\"font-size:10pt\\\"><a href=\\\"\"+ this.d_pgm + \"\\\" target=\\\"fbody\\\">\"+ this.d_menunm + \"</a></br>\";\r\n                                }\r\n                            }\r\n\r\n                            $(\"#newList\").append( listStr );\r\n                            \r\n                        });\r\n                        //alert(listStr);\r\n                    } else {\r\n                        listStr = \"<li class=\\\"no-data\\\">메뉴를 볼 수 없습니다.</li>\";\r\n                    }\r\n\r\n                    listArea.empty();\r\n                    listArea.html( listStr );\r\n\r\n                }\r\n            ,   complete: function(arg1, arg2) {\r\n                    // alert(arg1 + \" : \" + arg2);\r\n                }\r\n            ,   error: function(arg1, arg2) {\r\n                }\r\n        });\r\n\r\n    }\r\n\r\n-->\r\n</script>\r\n</head>\r\n\r\n<body topmargin=0>\r\n<form name=\"ff\" method=\"post\" action=\"\" target=\"fbody\">\r\n</form><div id =\"oldList\">\r\n\r\n</div>\r\n<div id =\"newList\">\r\n\r\n</div>\r\n</p>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

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
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

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


            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

                }
            }
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(fstIndex
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
