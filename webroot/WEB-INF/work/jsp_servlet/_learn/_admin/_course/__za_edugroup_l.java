package jsp_servlet._learn._admin._course;

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
import com.credu.course.*;

public final class __za_edugroup_l extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/course/za_EduGroup_L.jsp", 1422235984590L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<script language=\"JavaScript\">\r\n<!--\r\nvar isChecked = false;\r\n\r\nfunction MM_jumpMenu(targ,selObj,restore){ //v3.0\r\n  eval(targ+\".location=\'\"+selObj.options[selObj.selectedIndex].value+\"\'\");\r\n  if (restore) selObj.selectedIndex=0;\r\n}\r\n\r\n// 등록화면으로 이동\r\nfunction insertGrcode(){\r\n    document.form2.p_process.value=\"insertPage\";\r\n    document.form2.submit();\r\n}\r\n\r\n//수정화면으로 이동\r\nfunction updateGrcode(grcode){\r\n    document.form2.p_process.value=\"updatePage\";\r\n    document.form2.p_grcode.value = grcode;\r\n    document.form2.submit();\r\n}\r\n\r\n//과정연결\r\nfunction assignSubjCourse(grcode){\r\n\r\n        window.self.name = \"winSelectGrsubj\";\r\n        farwindow = window.open(\"\", \"openWinGrsubj\", \"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no, width = 1000, height = 640, top=0, left=0\");\r\n        document.form2.target = \"openWinGrsubj\";\r\n        document.form2.action = \"/servlet/controller.course.EduGroupServlet\";\r\n        document.form2.p_process.value=\"assignSubjCourse\";\r\n        document.form2.p_grcode.value=grcode;\r\n        document.form2.submit();\r\n\r\n        farwindow.window.focus();\r\n        document.form2.target = window.self.name;\r\n\r\n}\r\n\r\n//과정연결\r\nfunction assignSubj(grcode){\r\n\r\n        window.self.name = \"winSelectGrsubj2\";\r\n        farwindow = window.open(\"\", \"openWinGrsubj2\", \"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 730, height = 640, top=0, left=0\");\r\n        document.form2.target = \"openWinGrsubj2\";\r\n        document.form2.action = \"/servlet/controller.course.EduGroupServlet\";\r\n        document.form2.p_process.value=\"assignSubj\";\r\n        document.form2.p_grcode.value=grcode;\r\n        document.form2.submit();\r\n\r\n        farwindow.window.focus();\r\n        document.form2.target = window.self.name;\r\n\r\n}\r\n\r\n    //과정연결\r\n    function recomSubj(grcode){\r\n        window.self.name = \"winSelectGrsubj3\";\r\n        farwindow = window.open(\"\", \"openWinGrsubj3\", \"toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 730, height = 640, top=0, left=0\");\r\n        document.form2.target = \"openWinGrsubj3\";\r\n        document.form2.action = \"/servlet/controller.course.EduGroupServlet\";\r\n        document.form2.p_process.value=\"recomSubj\";\r\n        document.form2.p_grcode.value=grcode;\r\n        document.form2.submit();\r\n\r\n        farwindow.window.focus();\r\n        document.form2.target = window.self.name;\r\n    }\r\n\r\n    //연도별과정등록화면으로 이동\r\n    function whenGrseq(grcode){\r\n        var url = \"/servlet/controller.course.GrseqServlet?p_process=listPage&s_grcode=\"+grcode;\r\n        document.location=url;\r\n    }\r\n\r\n    /**\r\n     * 정렬순서 저장\r\n     */\r\n    function fnUpdateCodeOrder() {\r\n        var docForm = document.form2;\r\n        var i = 0;\r\n        var orgValue = \"\", currValue = \"\";\r\n        var grCode = \"\";\r\n        var updateList = \"\";\r\n\r\n        docForm.p_process.value=\"updateCodeOrder\";\r\n\r\n        var grListLength = document.getElementsByName(\"codeOrder\").length;\r\n\r\n        for( i = 0; i < grListLength; i++ ) {\r\n            orgValue = document.getElementById(\"oOrgCodeOrder\" + i).value;\r\n            currValue = document.getElementById(\"oCodeOrder\" + i).value;\r\n            if ( orgValue != currValue ) {\r\n                grCode = document.getElementById(\"oGrCode\" + i).value;\r\n                updateList += grCode + \"_\" + currValue +  \",\";\r\n            }\r\n        }\r\n\r\n        updateList = updateList.substring(0, updateList.length - 1);\r\n\r\n        docForm.tempElem.value = updateList;\r\n        document.form2.submit();\r\n    }\r\n//-->\r\n</script>\r\n</head>\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name = \"form2\" method = \"post\" action=\"/servlet/controller.course.EduGroupServlet\">\r\n    <input type=\"hidden\" name=\"p_process\" value=\"\">\r\n    <input type=\"hidden\" name=\"p_grcode\"  value=\"\">\r\n    <input type=\"hidden\" name=\"tempElem\"  value=\"\">\r\n</form>\r\n\r\n<form name = \"form1\" method = \"post\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n    <tr>\r\n        <td align=\"center\" valign=\"top\">\r\n\r\n            <!-- title 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"page_title\">\r\n                <tr>\r\n                    <td><img src=\"/images/admin/system/unite_title08.gif\" ></td>\r\n                    <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n                </tr>\r\n            </table>\r\n            <!-- title 끝 //-->\r\n\r\n            <br />\r\n            <!-- 교육그룹추가, 모두선택, 메일전송 버튼 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                    <td width=\"700\" align=\"right\"></td>\r\n                    <td width=\"8\"></td>\r\n                    <td  align=\"right\">\r\n                        <a href=\"javascript:fnUpdateCodeOrder()\">정렬순서 저장</a>\r\n                        <a href=\"javascript:insertGrcode()\"><img src=\"/images/admin/button/btn_edugroupplus.gif\" border=\"0\"></a>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td height=\"3\"></td>\r\n                    <td height=\"3\"></td>\r\n                    <td height=\"3\"></td>\r\n                </tr>\r\n            </table>\r\n            <!-- 교육그룹추가, 모두선택, 메일전송 버튼 끝 //-->\r\n\r\n            <!-- 교육그룹 시작 //-->\r\n            <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"6\">\r\n                <colgroup>\r\n                    <col width=\"5%\">\r\n                    <col width=\"5%\">\r\n                    <col width=\"10%\">\r\n                    <col width=\"21%\">\r\n                    <col width=\"19%\">\r\n                    <col width=\"10%\">\r\n                    <col width=\"25%\">\r\n                    <col width=\"*\">\r\n                </colgroup>\r\n                <tr>\r\n                    <td colspan=\"8\" class=\"table_top_line\"></td>\r\n                </tr>\r\n                <tr>\r\n                    <th height=\"25\" class=\"table_title\"><b>NO</b></th>\r\n                    <th height=\"25\" class=\"table_title\"><b>사용여부</b></th>\r\n                    <th class=\"table_title\"><b>교육구분</b></th>\r\n                    <th class=\"table_title\"><b>교육그룹</b></th>\r\n                    <th class=\"table_title\"><b>관련기관</b></th>\r\n                    <th class=\"table_title\"><b>담당자</b></th>\r\n                    <th class=\"table_title\"><b>기능</b></th>\r\n                    <td class=\"table_title\"><b>정렬순서</b></td>\r\n                </tr>\r\n";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n\r\n                <tr>\r\n                    <td align=\"center\" bgcolor=\"#EEEEEE\" height=\"25\">";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="</td>\r\n                    <td class=\"table_02_1\" align=\"center\" height=\"25\">";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="</td>\r\n                    <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="</td>\r\n                    <td class=\"table_02_1\" align=\"center\"><b><a href=\"javascript:updateGrcode(\'";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\')\">";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="</a></b></td>\r\n                    <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="</td>\r\n                    <td class=\"table_03_1\" align=\"center\">\r\n                        <table width=\"68%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                            <tr>\r\n                                <td align=\"center\"><a href=\"javascript:whenGrseq(\'";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\')\"><img src=\"/images/admin/button/b_year.gif\" border=\"0\"></a></td>\r\n                                <!--td align=\"center\"><a href=\"javascript:assignSubjCourse(\'";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\')\"><img src=\"/images/admin/button/b_courseapply.gif\" border=\"0\"></a></td-->\r\n                                <td align=\"center\"><a href=\"javascript:assignSubj(\'";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\')\"><img src=\"/images/admin/button/b_courseapply.gif\" border=\"0\"></a></td>\r\n                                <td align=\"center\"><a href=\"javascript:recomSubj(\'";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\')\"><img src=\"/images/admin/button/b_recom.gif\" border=\"0\"></a></td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                    <td class=\"table_03_1\" align=\"center\">\r\n                        <input type=\"text\" id=\"oCodeOrder";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\" name=\"codeOrder\" size=\"4\" maxlength=\"2\" value=\"";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="\" />\r\n                        <input type=\"hidden\" id=\"oOrgCodeOrder";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="\" name=\"orgCodeOrder\" value=\"";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="\" />\r\n                        <input type=\"hidden\" id=\"oGrCode";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\" name=\"grCode\" value=\"";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="\" />\r\n                    </td>\r\n                </tr>\r\n";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n\r\n            </table>\r\n        <!-- 교육그룹 끝 //-->\r\n            <br />\r\n        </td>\r\n    </tr>\r\n\r\n    <tr>\r\n        <td>";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n    ";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 ="</td>\r\n    </tr>\r\n\r\n</table>\r\n\r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

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
//  1. 제      목: 교육그룹 리스트 조회화면
//  2. 프로그램명: za_EduGroup_L.jsp
//  3. 개      요: 교육그룹 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
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

    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int resultCnt = box.getInt("resultCnt");

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

    ArrayList  list = (ArrayList)request.getAttribute("EduGroupList");

    for (int i=0; i<list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
 
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf((i+1)
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_useyn")
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grgubunnm")
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grcode")
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grcodenm")
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grtypenm")
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_mastername")
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grcode")
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("grcode")
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grcode")
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grcode")
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( i 
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_code_order") 
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( i 
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_code_order") 
));
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( i 
));
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_grcode") 
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
  } 
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}

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
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}
  }   
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}
            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
