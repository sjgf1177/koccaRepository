package jsp_servlet._learn._admin._kocca;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import java.text.*;
import com.credu.homepage.*;
import com.credu.common.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.homepage.LoginBean;
import com.credu.homepage.LoginBean;

public final class __login extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/kocca/login.jsp", 1422235940938L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n\r\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\">\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\" />\r\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\" />\r\n<title>한국콘텐츠아카데미</title>\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/portal/base.css\" />\r\n<script type=\"text/javascript\" src=\"/script/portal/common.js\"></script>\r\n<script type=\"text/javascript\" src=\"/namo_cross/js/namo_scripteditor.js\"></script>\r\n<script type=\'text/javascript\' src=\'/script/jquery-1.3.2.min.js\'></script>\r\n<script type=\"text/javascript\" src=\"/script/cresys_lib.js\"></script>\r\n<script type=\'text/javascript\' src=\'/script/user_patch.js\'></script>\r\n<script language=\"javascript\" src=\"/script/mainscript.js\"></script>\r\n<script language = \"VBScript\" src = \"/script/cresys_lib.vbs\"></script>\r\n</head>\r\n\r\n";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n\r\n<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n<!--\r\n";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\r\n    window.onload = function () {\r\n    document.location.href=\"/learn/admin/kocca/mScreenFset.jsp\";\r\n\r\n    }\r\n";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\r\n    // 페이지 초기화\r\n    function winonload() {\r\n        document.form1.p_userid.focus();\r\n    }\r\n\r\n    /* 패스워드 엔터키 체크 */\r\n    function password_enter(e) {\r\n        if (e.keyCode ==\'13\'){  login();  }\r\n    }\r\n\r\n    // 로그인\r\n    function login() {    \t\r\n        if (input_chk(form1)){\r\n\r\n            form1.p_process.value   = \"login\";\r\n\r\n            form1.action            = \"/servlet/controller.homepage.LoginServlet\";\r\n            form1.submit();\r\n        }\r\n    }\r\n\r\n    // 입력 체크\r\n    function input_chk(form1)  {\r\n        // var chk = ( isNull(form1.elements[\"p_userid\"],\"아이디\") && isNull(form1.elements[\"p_pwd\"],\"비밀번호\") && isNull(form1.elements[\"p_auth\"],\"권한\") );\r\n        // return (chk);\r\n        if(document.form1.p_userid.value==\"\" || document.form1.p_userid.value==null){\r\n            alert(\"아이디를 입력하세요.\");\r\n            document.form1.p_userid.focus();\r\n            return(false);\r\n        }else if(document.form1.p_pwd.value==\"\" || document.form1.p_pwd.value==null){\r\n            alert(\"비밀번호를 입력하세요.\");\r\n            document.form1.p_pwd.focus();\r\n            return(false);\r\n        }else if(document.form1.p_auth.value==\"\" || document.form1.p_auth.value==null){\r\n            alert(\"권한을 선택하세요.\");\r\n            document.form1.p_auth.focus();\r\n            return(false);\r\n        }else {\r\n            return(true);\r\n        }\r\n        \r\n    }\r\n//-->\r\n</script>\r\n\r\n<body>\r\n<!-- form 시작 -->\r\n<form name=\"form1\"  action=\"\" method=\"post\">\r\n    <input type=\"hidden\" name=\"p_process\"    value=\"\" />\r\n    <input type=\"hidden\" name=\"p_isAdmin\"    value=\"Y\" />\r\n\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"login\" style=\"margin-top: 100px;\">\r\n\r\n    <tr>\r\n        <td valign=\"top\" align=\"center\">\r\n            <img src=\"/images/portal/member/membership/loginlogo.gif\" width=\"246\" height=\"81\" alt=\"KOCCA 한국콘텐츠아카데미 로그인\"/>\r\n        </td>\r\n    </tr>  \r\n    <tr>\r\n        <td align=\"center\" valign=\"bottom\">\r\n            \r\n            <table width=\"400\" border=\"0\" cellspacing=\"2\" cellpadding=\"0\" id=\"login_form\">\r\n                <tr>\r\n                    <td align=\"left\">\r\n                        <img src=\"/images/portal/homepage_renewal/member/tit_loginbox01.gif\" alt=\"아이디\" /></td>\r\n                    <td>\r\n                        <input type=\"text\" name=\"p_userid\" id=\"p_userid\" style=\"width:202px; height:24px;\" onkeypress=\"password_enter(event)\" tabindex=\"1\"/>\r\n                    </td>\r\n                    <td rowspan=\"3\">\r\n                        <a href=\"javascript:login();\"><img src=\"/images/portal/homepage_renewal/member/btn_login02.gif\" alt=\"로그인\" tabindex=\"4\" style=\"width: 86px;height: 59px;\"/></a>\r\n                    </td>\r\n                </tr>\r\n\r\n                <tr>\r\n                    <td align=\"left\" width=\"87\"><img src=\"/images/portal/homepage_renewal/member/tit_loginbox02.gif\" alt=\"패스워드\" /></td>\r\n                    <td>\r\n                        <input type=\"password\" name=\"p_pwd\" id=\"p_pwd\" style=\"width:202px; height:24px;\" onkeypress=\"password_enter(event)\" tabindex=\"2\"/>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"87\"> 권한 </td>\r\n                    <td>\r\n                        ";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            \r\n        </td>\r\n    </tr>\r\n    <tr>\r\n        <td height=\"44\" align=\"center\"><img src=\"/images/portal/homepage_renewal/member/line_blue02.gif\" /></td>\r\n    </tr>\r\n</table>\r\n\r\n      \t\r\n</form>\r\n<!-- form 끝 -->\r\n\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

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
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
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
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

	RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String s_userid  = box.getSession("userid");
	box.put("onLoad","onload=\"winonload();\"");


            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
box.put("mainYn", "N"); 
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

    if(box.getSession("tem_grcode").equals("")){
        box.setSession("tem_grcode","N000001");
    }
    
    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");

    if (topWarp_gadmin.equals("")) {
        box.setSession("gadmin", "ZZ");
        topWarp_gadmin = box.getSession("gadmin");
    }
    String referer ="4124124"+ request.getHeader("REFERER");
    if(referer.indexOf("http://localhost")>0 || referer.indexOf("http://lms.kocca.or.kr")>0)
        referer="Y";
    else
        referer="";

    String mainYN=box.get("mainYn");
    String init_margin="";
    String init_margin1="";
    String init_margin2="";
    if(mainYN.equals(""))
    {
        
        init_margin="30";
        init_margin1="10";
        init_margin2="30";
    
    }
    else
    {
        init_margin="20";
        init_margin1="5";
        init_margin2="30";
    }
 
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}

if (!topWarp_userid.equals("") && !topWarp_gadmin.equals("")){
    if(!topWarp_gadmin.equals("ZZ")){

            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}

    }
}

            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( LoginBean.getAuthSelect("p_auth") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
