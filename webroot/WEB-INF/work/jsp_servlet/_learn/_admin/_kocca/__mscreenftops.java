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

public final class __mscreenftops extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/kocca/mScreenFtops.jsp", 1422235940938L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<html><head><title></title>\r\n<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=KS_C_5601\">\r\n</head>\r\n<script language=\"javascript\">\r\n\tapam = new Array(30);\r\n\t\r\n\tvar\tsizei = 0;\r\n\tfunction init(){\r\n\t\tsetVal(\"s_grcode\",\"\");\r\n\t}\r\n\t\r\n\tfunction openWin(url){\r\n\t\twindow.open(url,\"win\",\"toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=800,height=600\").focus();\r\n\t}\r\n\t\r\n\tfunction getVal(name){\r\n\t\tfor (var i=0; i<sizei;i++){\r\n\t\t\tcpam = apam[i];\r\n\t\t\tif(cpam.name==name)\treturn cpam.val;\t\r\n\t\t}\r\n\t\treturn \"\";\r\n\t}\r\n\tfunction setVal(name,val){\r\n\t\tvar f_exist = false;\r\n\r\n\t\tfor (var i=0; i<sizei;i++){\r\n\t\t\tcpam = apam[i];\r\n\t\t\tif(cpam.name==name)\t{\r\n\t\t\t\tapam[i].val = val;\r\n\t\t\t\treturn;\t\r\n\t\t\t}\r\n\t\t}\r\n\t\t\r\n\t\tif (!f_exist){\r\n\t\t\tcpama = new Object();\r\n\t\t\tcpama.name = name;\r\n\t\t\tcpama.val  = val;\r\n\t\t\tapam[sizei] = cpama;\r\n\t\t\tsizei++;\r\n\t\t}\r\n\t}\r\n\tfunction getAll(){\r\n\t\tfor (var i=0; i<apam.length;i++){\r\n\t\t\tcpam = apam[i];\r\n\t\t\talert(\"cpam[\"+i+\"].name=\"+apam[i].name+\", val=\"+apam[i].val);\t\t\t\r\n\t\t}\t\r\n\t}\r\n\tfunction setPam(){\r\n\t\tfm = parent.fbody.document.form1;\r\n\t\tfor(var i=0;i<fm.length;i++){\r\n\t\t\tif (fm.elements[i].name.substring(0,2)==\"s_\" && fm.elements[i].type==\"select-one\"){\r\n\t\t\t\tif (fm.elements[i].length > 0) {\r\n\t\t\t\t\tvals = fm.elements[i].options[fm.elements[i].selectedIndex].value;\r\n\t\t\t\t\tsetVal(fm.elements[i].name, vals);\r\n\t\t\t\t}\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n\tinit();\r\n\t\t// 강사 접속 로그아웃 처리\r\n\tfunction goLoginout() {\r\n\t\t";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n\t\t\t\tif (confirm(\"운영자 화면을 닫으시겠습니까?\")) {\r\n\t\t\t\t\tdocument.form1.p_process.value   = \"tutorlogout\";\r\n\t\t\t\t\tdocument.form1.action            = \"/servlet/controller.homepage.LoginServlet\";\r\n\t\t\t\t\tdocument.form1.submit();\r\n\t\t\t\t} else {\r\n\t\t\t\t\treturn;\r\n\t\t\t\t}\r\n\t\t";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n\t}\r\n    \r\n</script>\r\n</head>\r\n<body onbeforeunload=\"javascript:goLoginout();\">\r\n<form name=\"form1\" method=\"post\">\r\n    <input type=\'hidden\' name=\'p_process\'>\r\n</form>\r\n</body></html>\r\n";
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

/**
 * file name : mScreenFset.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen FrameSet
 */

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

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

    if (v_grcode.equals(""))     v_grcode 	 = "N000001";
    if (v_gadmin.equals(""))     v_gadmin 	 = "ZZ";

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
 if(v_gadmin.equals("P1")){
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
}
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
