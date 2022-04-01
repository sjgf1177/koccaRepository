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

public final class __mscreenfset extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/kocca/mScreenFset.jsp", 1422235940938L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n    alert(\"권한이 없습니다\");\r\n    parent.document.location.href=\"/learn/admin/kocca/login.jsp\";\r\n    // self.close();\r\n//-->\r\n</SCRIPT>\r\n";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n\r\n\t<html>\r\n\t<head>\r\n\t<title>Manager Screen</title>\r\n\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n\t</head>\r\n\r\n  \t\t<frameset cols=\"*\" rows=\"0,105,1*,0,0\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\r\n\t  \t\t<frame src=\"mScreenFtops.jsp\"  name=\"ftop\" scrolling=\"NO\" frameborder=\"NO\">\r\n\t  \t\t<frame src=\"mScreenFmenu.jsp?p_systemgubun=";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\"  name=\"fmenu\" scrolling=\"NO\" frameborder=\"NO\">\r\n            <frameset cols=\"150,*\" rows=\"*\" frameborder=\"NO\" border=\"0\" framespacing=\"0\">\r\n                <frame src=\"mScreenFsubMenu.jsp\"  name=\"fleft\" scrolling=\"NO\" frameborder=\"NO\">                \r\n                <frame src=\"mScreenFbody.jsp\"  id=\"fbody\" name=\"fbody\" scrolling=\"AUTO\" frameborder=\"NO\">\r\n\t  \t\t<frame src=\"mScreenFdown.jsp\"  name=\"fdown\" scrolling=\"NO\" frameborder=\"NO\">\r\n\t\t</frameset>\r\n\t\t<noframes><body bgcolor=\"#FFFFFF\">\r\n\r\n\t</body></noframes>\r\n\t</html>\r\n";
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
//   if (v_gadmin.equals("")) box.setSession("gadmin", "A1");

   if (v_gadmin.equals("") || v_gadmin.equals("ZZ")) {

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

   } else {

            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_systemgubun
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}

   }

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
