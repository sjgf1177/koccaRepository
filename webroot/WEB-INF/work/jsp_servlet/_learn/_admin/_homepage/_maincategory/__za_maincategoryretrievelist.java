package jsp_servlet._learn._admin._homepage._maincategory;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.ArrayList;
import com.credu.library.DataBox;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

public final class __za_maincategoryretrievelist extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/homepage/maincategory/za_MainCategoryRetrieveList.jsp", 1422494631865L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n<title>메인 인기/추천 관리 - 홈페이지 - 관리자 - 한국콘텐츠아카데미</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n\r\n<link rel=\"stylesheet\" type=\"text/CSS\" href=\"/css/admin_style.css\">\r\n<script type=\"text/javascript\" src=\"/script/cresys_lib.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/jquery/1.11.1/jquery-1.11.1.min.js\"></script>\r\n<script type=\"text/javascript\">\r\n<!--\r\n    /**\r\n     * 검색조건으로 조회\r\n     */\r\n    function fnSearchMainCategoryList() {\r\n    }\r\n\r\n    /**\r\n     * 등록화면으로 이동\r\n     */\r\n    function fnGoToRegisterPage() {\r\n        location.href = \"/learn/admin/homepage/maincategory/za_MainCategoryRegisterPage.jsp\";\r\n    }\r\n//-->\r\n</script>\r\n</head>\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n    <tr>\r\n        <td align=\"center\" valign=\"top\">\r\n            <!-- title 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"page_title\">\r\n                <tr>\r\n                    <td style=\"background-color:#fff; width:240px; font-size:20px; font-weight:bold;\">메인 인기/추천 관리</td>\r\n                    <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n                </tr>\r\n            </table>\r\n            <!-- title 끝 //-->\r\n\r\n            <br/>\r\n            <!-- form 시작 //-->\r\n            <form id=\"oMainCategorySearchForm\" name=\"mainCategorySearchForm\" method=\"get\">\r\n                <input type=\"hidden\" id=\"oProcess\" name=\"process\" value=\"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\" />\r\n            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"form_table_bg\" style=\"width:97%; border-style: solid; border-width:1px;\">\r\n                <colgroup>\r\n                    <col style=\"width:12%;\" />\r\n                    <col style=\"width:*;\" />\r\n                    <col style=\"width:20%;\" />\r\n                </colgroup>\r\n                <tr>\r\n                    <th scope=\"cols\" style=\"height:26px; text-align:right; padding:4px;\">카테고리명 : </th>\r\n                    <td style=\"padding:4px;\"><input type=\"text\" id=\"oSearchCategoryNm\" name=\"searchCategoryNm\" style=\"width:520px;\" value=\"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\" /></td>\r\n                    <td rowspan=\"2\"><a href=\"javascript:fnSearchMainCategoryList();\"><img src=\"/images/admin/button/btn_inquiry.gif\" border=\"0\"></a></td>\r\n                </tr>\r\n                <tr>\r\n                    <th scope=\"cols\" style=\"height:26px; text-align:right; padding:4px;\">사용여부 : </th>\r\n                    <td style=\"padding:4px;\">\r\n                        <label><input type=\"radio\" id=\"oSearchUseYnAll\" name=\"searchUseYn\" value=\"A\" />전체</label>\r\n                        <label><input type=\"radio\" id=\"oSearchUseYnY\" name=\"searchUseYn\" value=\"Y\" />사용</label>\r\n                        <label><input type=\"radio\" id=\"oSearchUseYnN\" name=\"searchUseYn\" value=\"N\" />미사용</label>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td colspan=\"3\" height=\"3\"></td>\r\n                </tr>\r\n            </table>\r\n            </form>\r\n            <!-- form 끝 //-->\r\n            <br/>\r\n            <!-- 버튼 시작 //-->\r\n            <table class=\"table1\" cellspacing=\"1\" cellpadding=\"5\">\r\n                <tr>\r\n                    <td style=\"width:100%; text-align:right;\"><a class=\"board_btn\" onclick=\"fnGoToRegisterPage();\"><span style=\"font-size:12px;\">카테고리 등록</span></a></td>\r\n                </tr>\r\n            </table>\r\n            <!-- 버튼 끝 //-->\r\n\r\n            <!-- 목록  시작 //-->\r\n            <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n                <colgroup>\r\n                    <col style=\"width:6%;\" />\r\n                    <col style=\"width:*;\" />\r\n                    <col style=\"width:10%;\" />\r\n                    <col style=\"width:10%;\" />\r\n                    <col style=\"width:6%;\" />\r\n                    <col style=\"width:9%;\" />\r\n                    <col style=\"width:7%;\" />\r\n                    <col style=\"width:12%;\" />\r\n                </colgroup>\r\n\r\n                <tr>\r\n                    <td colspan=\"8\" class=\"table_top_line\" style=\"height:2px;\"></td>\r\n                </tr>\r\n                <tr>\r\n                    <th class=\"table_title\" height=\"25\">NO</th>\r\n                    <th class=\"table_title\">카테고리명</th>\r\n                    <th class=\"table_title\">카테고리 타입</th>\r\n                    <th class=\"table_title\">레이아웃 타입</th>\r\n                    <th class=\"table_title\">순서</th>\r\n                    <th class=\"table_title\">등록과정 수</th>\r\n                    <th class=\"table_title\">사용여부</th>\r\n                    <th class=\"table_title\">과정등록</th>\r\n                </tr>\r\n\r\n";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\r\n                <tr>\r\n                    <td class=\"table_01\">";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="</td>\r\n                    <td class=\"table_02_1\">";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="</td>\r\n                    <td class=\"table_02_2\">";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="</td>\r\n                    <td class=\"table_02_1\"><a class=\"board_btn\" onclick=\"fnRegisterItemPage(";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 =");\"><span>과정 등록</span></a></td>\r\n                </tr>\r\n";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n                <tr>\r\n                    <td class=\"table_01\" colspan=\"8\" align=\"center\">조회된 목록이 없습니다.</td>\r\n                    <!-- <td class=\"table_02_1\"></td> //-->\r\n                </tr>\r\n";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\r\n            </table>\r\n            <!-- 목록 끝 //-->\r\n\r\n            <!-- paging 시작 //-->\r\n            <br/>\r\n            <table width=\"97%\" height=\"26\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                    <td align=\"right\" valign=\"absmiddle\">\r\n                        ";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            <!-- paging 끝 //-->\r\n        </td>\r\n    </tr>\r\n</table>\r\n\r\n";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="\r\n    ";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="\r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

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
// 1. 파일명 : za_MainCategoryRetrieveList.jsp
// 2. 프로그램명 : 홈페이지 메인화면 인기/추천 항목 목록 조회
// 3. 설명 : 홈페이지 메인화면 인기/추천 영역에 표시되는 카테고리 목록을 조회한다.
// 4. 환경: JDK 1.5
// 5. 버젼: 1.0
// 6. 작성 : 2015-01-27
// 7. 수정 이력 :
//          2015-01-27 최초 작성
//***********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
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

    ArrayList<DataBox> mainCategoryList = (ArrayList<DataBox>)request.getAttribute("mainCategoryList");

    out.println(mainCategoryList.size());

    String process = box.getString("process");
    String searchCategoryNm = box.getString("searchCategoryNm");

            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( process 
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( searchCategoryNm 
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}

    if ( mainCategoryList != null && mainCategoryList.size() > 0 ) {
        DataBox dbox = null;
        for( int i = 0 ; i < mainCategoryList.size() ; i++ ){
            dbox = (DataBox)mainCategoryList.get(i);

            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getInt("d_category_seq") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_category_nm") 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_category_type") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_layout_type") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_sort_order") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_reg_item_cnt") 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_use_yn") 
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getInt("d_category_seq") 
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}

        }
    } else {

            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}

    }

            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}

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
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
  }   
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
