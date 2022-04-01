package jsp_servlet._learn._admin._templet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import java.text.*;
import com.credu.system.*;
import com.credu.library.*;
import com.credu.templet.*;

public final class __za_templet_l extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/templet/za_Templet_L.jsp", 1422235993358L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n\r\n<html>\r\n<head>\r\n<title></title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link href=\"/css/admin_style.css\" rel=\"stylesheet\" type=\"text/css\">\r\n<script language = \"javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n\r\n//관리수정화면으로 이동\r\nfunction whenMove(process, grcode, grcodenm){\r\n    document.form1.p_grcode.value = grcode;\r\n    document.form1.p_grcodenm.value = grcodenm;\r\n    document.form1.p_process.value = process;\r\n    document.form1.action=\"/servlet/controller.templet.TempletServlet\"\r\n    document.form1.submit();\r\n}\r\n\r\n\r\nfunction whenGrtype(){\r\n    document.form1.p_process.value = \"listPage\";\r\n    document.form1.action=\"/servlet/controller.templet.TempletServlet\"\r\n    document.form1.submit();\r\n}\r\n// 메뉴관리 (팝업)\r\nfunction menuPopup() {\r\n    window.self.name = \"TempletMain\";\r\n    open_window(\"menuPopup\",\"\",\"30\",\"30\",\"1050\",\"600\",\'\',\'\',\'\',\'yes\',\'\',\'\',\'\');\r\n    document.form1.target = \"menuPopup\";\r\n    document.form1.action=\'/servlet/controller.course.TempletMenuAdminServlet\';\r\n    document.form1.p_process.value = \'select\';\r\n    document.form1.submit();\r\n\r\n    document.form1.target = window.self.name;\r\n}\r\n\r\n//-->\r\n</SCRIPT>\r\n</head>\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\" action=\"\">\r\n    <input type=\"hidden\" name=\"p_process\" value=\"";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\">\r\n    <input type=\"hidden\" name=\"p_grcode\"   value=\"\">\r\n    <input type=\"hidden\" name=\"p_grcodenm\"   value=\"\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n     <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr> \r\n            <td><img src=\"/images/admin/course/c_title01_1.gif\"></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n     <!----------------- title 끝 ----------------->\r\n      <br>\r\n      <br>\r\n      <!----------------- 교육그룹 시작 ----------------->\r\n";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n      <table width=\"97%\" cellspacing=\"1\" cellpadding=\"5\" >\r\n        <tr> \r\n          <td colspan=\"8\" align=\"left\">\r\n          ";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\r\n          </td>\r\n        </tr>\r\n      </table>\r\n";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\r\n        ";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\r\n        <input type=\"hidden\" name=\"p_grtype\" value=\"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\">\r\n ";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\r\n      <table width=\"97%\" cellspacing=\"1\" cellpadding=\"5\" >\r\n        <tr> \r\n          <td colspan=\"8\" align=\"right\">\r\n\t\t\t<a href=\"javascript:menuPopup();\"><img src=\"/images/admin/portal/s.2_01.gif\" alt=\"사용자 메뉴관리\" style=\"border=0\"/></a><br></br>\r\n          </td>\r\n        </tr>\r\n      </table>\r\n      <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n        <tr> \r\n          <td colspan=\"8\" class=\"table_top_line\"></td>\r\n        </tr>\r\n        <tr> \r\n          <td width=\"3%\" rowspan=\"2\" class=\"table_title\"><b>NO</b></td>\r\n          <td width=\"23%\" rowspan=\"2\" class=\"table_title\"><b>교육그룹</b></td>\r\n          <td width=\"12%\" rowspan=\"2\" class=\"table_title\"><b>Template Type</b></td>\r\n          <td width=\"10%\" rowspan=\"2\" class=\"table_title\"><b>기본정보</b></td>\r\n          <td height=\"25\" colspan=\"2\" class=\"table_title\"><b>콘텐츠/메뉴 관리</b></td>\r\n          <td width=\"14%\" rowspan=\"2\" class=\"table_title\"><b>Template 보기</b></td>\r\n        </tr>\r\n        <tr> \r\n          <td width=\"10%\" height=\"25\" class=\"table_title\"><b>Main</b></td>\r\n          <td width=\"10%\" class=\"table_title\"><b>Sub</b></td>\r\n        </tr>\r\n";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\r\n        <tr> \r\n          <td align=\"center\" bgcolor=\"#EEEEEE\" height=\"25\">";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="</td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 =" </td>\r\n          <td align=\"center\" class=\"table_02_1\"><a href=\"javascript:whenMove(\'updateManagerPage\', \'";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\', \'";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\')\">관리</a><br> </td>\r\n          <td align=\"center\" class=\"table_02_1\">";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="<a href=\"javascript:whenMove(\'updateMainPage\', \'";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="\')\">수정</a>";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="</td>\r\n          <td align=\"center\" class=\"table_02_1\">";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="<a href=\"javascript:whenMove(\'updateSubPage\', \'";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="<a href=\"/servlet/controller.homepage.MainServlet?tem_grcode=";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="&tem_type=";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="\" target=\"new\">보기</a>";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="</td>\r\n        </tr>\r\n";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n\r\n      </table>\r\n        <!----------------- 교육그룹 끝 ----------------->\r\n        <br>\r\n    </td>\r\n  </tr>\r\n</table>\r\n\r\n</form>\r\n\r\n";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 ="\r\n    ";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

    private final static String  _wl_block26 ="\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block26Bytes = _getBytes( _wl_block26 );

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
//  1. 제      목: 템플릿 관리화면
//  2. 프로그램명: za_Templet_L.jsp
//  3. 개      요: 템플릿 관리화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 06. 21
//  7. 수      정: 
//**********************************************************

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
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_process= box.getString("p_process");
    String v_grcode    = "";
    String v_grcodenm  =  "";
    String v_type      = "";
    String v_type_view = "";

    ArrayList  list = (ArrayList)request.getAttribute("EduGroupList");


            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_process
));
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
/* if (StringManager.substring(box.getSession("gadmin"), 0, 1).equals("A")) { 
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(CodeConfigBean.getCodeGubunSelect("0060","",1,"p_grtype",box.getString("p_grtype"),"onChange=\"whenGrtype()\"",3) 
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
 } else  {                                                   
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
//=CodeConfigBean.getCodeName("grtype",box.getSession("grtype")) 
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(box.getSession("grtype")
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
 }                                                          */ 
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}




    for (int i=0; i<list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);

        v_grcode = dbox.getString("d_grcode");
        v_grcodenm = dbox.getString("d_grcodenm");
        v_type = dbox.getString("d_type");
        if (!v_type.equals("")) v_type_view = "Type " + v_type;
        else v_type_view = "";

            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf((i+1)
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcodenm
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_type_view
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcodenm
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
if (!v_type.equals("")) {
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
}
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
if (!v_type.equals("")) {
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
}
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
if (!v_type.equals("")) {
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_type
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
}
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}
  }    
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}

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
            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}
  }   
            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}
            {_writeText(response, out, _wl_block26, _wl_block26Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
