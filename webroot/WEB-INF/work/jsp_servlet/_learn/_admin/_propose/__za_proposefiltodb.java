package jsp_servlet._learn._admin._propose;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import com.credu.system.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.propose.*;

public final class __za_proposefiltodb extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/propose/za_ProposeFilToDB.jsp", 1422235971813L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n<html>\r\n<head>\r\n<title>입과FileToDB11</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<script type=\"text/javascript\" src=\"/script/cresys_lib.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/jquery-1.3.2.min.js\"></script>\r\n<script language=\"VBScript\" src = \"/script/cresys_lib.vbs\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\n\r\n    /**\r\n     * 일괄 입과처리 (등록)\r\n     */\r\n    function fnRegGroupPropose() {\r\n        action(\"insertFileToDB\");\r\n    }\r\n\r\n    /**\r\n     * 일괄 입과처리 미리보기 (등록)\r\n     */\r\n    function fnPreview() {\r\n        action(\"previewFileToDB\");\r\n    }\r\n\r\n\r\n    function action(p_process) {\r\n        var docForm = document.form3;\r\n\r\n        if(blankCheck(docForm.p_file.value)){\r\n            alert(\"업로드할 엑셀 파일을 선택해 주시기 바랍니다.\");\r\n            return;\r\n        }\r\n\r\n        if (docForm.p_file.value.length > 0 ){\r\n            var data = docForm.p_file.value;\r\n            data = data.toUpperCase(data);\r\n            if (data.indexOf(\".XLS\") < 0) {\r\n                alert(\"파일종류는 XLS 파일만 가능합니다.\");\r\n                return;\r\n            }\r\n        }\r\n\r\n        if (document.form1.s_grcode.value == \"ALL\") {\r\n            alert(\"교육그룹을 선택하세요.\");\r\n            return;\r\n        }\r\n\r\n        if (document.form1.s_grseq.value == \"ALL\") {\r\n            alert(\"교육차수를 선택하세요.\");\r\n            return;\r\n        }\r\n\r\n        top.ftop.setPam();\r\n\r\n        /*if (document.form1.grcodeText.value == \'\') {\r\n        alert(\"회사코드를 입력 하세요\");\r\n        document.form1.grcodeText.focus();\r\n        return;\r\n        }*/\r\n        /*\r\n        if(docForm.p_stuoverlap.checked == true) {\r\n        docForm.p_stuoverlap.value = \"Y\";\r\n        }else{\r\n        docForm.p_stuoverlap.value = \"N\";\r\n        }*/\r\n\r\n\r\n        if(docForm.p_isgraduated.checked == true) {\r\n            docForm.p_isgraduated.value = \"Y\";\r\n        }else{\r\n            docForm.p_isgraduated.value = \"N\";\r\n        }\r\n\r\n        docForm.s_subjnm.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].text;\r\n        docForm.s_grcodenm.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].text;\r\n        docForm.s_subjseqgrnm.value = document.form1.s_subjseq.options[document.form1.s_subjseq.selectedIndex].text;\r\n\r\n        docForm.s_grcode.value = document.form1.s_grcode.value;\r\n        docForm.s_gyear.value = document.form1.s_gyear.value;\r\n        docForm.s_grseq.value = document.form1.s_grseq.value;\r\n        docForm.s_upperclass.value = document.form1.s_upperclass.value;\r\n        docForm.s_middleclass.value = document.form1.s_middleclass.value;\r\n        docForm.s_subjcourse.value = document.form1.s_subjcourse.value;\r\n        docForm.s_subjseq.value = document.form1.s_subjseq.value;\r\n        docForm.s_usergrcode.value =  document.form1.grcodeText.value;\r\n\r\n        docForm.action = \"/servlet/controller.propose.AcceptServlet\";\r\n        docForm.p_process.value = p_process;                              //insertFileToDB,previewFileToDB\r\n        docForm.submit();\r\n    }\r\n\r\n    function whenSelection(p_action) {\r\n        document.form1.p_process.value = \"proposeFileToDB\";\r\n        document.form1.p_action.value = p_action;\r\n        document.form1.submit();\r\n    }\r\n\r\n\r\n    // 검색조건 체크\r\n    function chkParam() {\r\n        if (document.form1.s_grcode.value == \'ALL\' || document.form1.s_grcode.value == \'----\') {\r\n            alert(\"교육그룹을 선택하세요.\");\r\n            return false;\r\n        }\r\n\r\n        if (document.form1.s_grseq.value == \'ALL\' || document.form1.s_grseq.value == \'----\') {\r\n            alert(\"교육차수를 선택하세요.\");\r\n            return false;\r\n        }\r\n\r\n        if (document.form1.s_subjcourse.value != \'ALL\' && document.form1.s_subjcourse.value != \'----\') {\r\n            if (document.form1.s_subjseq.value == \'ALL\' || document.form1.s_subjseq.value == \'----\') {\r\n                alert(\"과정차수를 선택하세요.\");\r\n                return false;\r\n            }\r\n        }\r\n    }\r\n\r\n    // 과정코드 엑셀파일\r\n    function whenExcel() {\r\n        if (chkParam() == false) {\r\n            return;\r\n        }\r\n\r\n        window.self.name = \"proposeExcel\";\r\n        open_window(\"openExcel\",\"\",\"100\",\"100\",\"700\",\"400\",\"yes\",\"yes\",\"yes\",\"yes\",\"yes\");\r\n        document.form1.target = \"openExcel\";\r\n        document.form1.action=\'/servlet/controller.propose.AcceptServlet\';\r\n        document.form1.p_process.value = \'proposeListExcel\';\r\n        document.form1.submit();\r\n\r\n        document.form1.target = window.self.name;\r\n    }\r\n\r\n    var startup = 0;\r\n    function Main_s_grseq(){\r\n        if (startup > 0) {\r\n            changes_grseq(document.form1.s_grcode.value, document.form1.s_gyear.value);\r\n        }\r\n    }\r\n\r\n    function Main_subjcourse(){\r\n        var subjSearchKey = \'\';\r\n        if(startup > 2) {\r\n            if (document.form1.s_subjsearchkey == \"undefine\") {\r\n                subjSearchKey = \"\";\r\n            } else {\r\n                subjSearchKey = document.form1.s_subjsearchkey.value;\r\n            }\r\n\r\n            changes_subjcourse(document.form1.s_grcode.value, document.form1.s_upperclass.value, document.form1.s_middleclass.value, document.form1.s_lowerclass.value, document.form1.s_grseq.value , subjSearchKey );\r\n            // alert(document.form1.s_grcode.value+ \" , \" + document.form1.s_upperclass.value+ \" , \" + document.form1.s_middleclass.value+ \" , \" + document.form1.s_lowerclass.value+ \" , \" + document.form1.s_subjseq.value+ \" , \" +\'\' );\r\n        }\r\n        startup++;\r\n    }\r\n\r\n    function searchTaxBill() {\r\n        var url = \"/servlet/controller.library.SearchServlet?p_process=taxBill&s_grcode=";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="&s_gyear=";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="&s_grseq=";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="&p_searchtext=\";\r\n        open_window(\"\",url,\"100\",\"70\",\"595\",\"455\",\'\',\'\',\'\',\'true\');\r\n    }\r\n\r\n    function returnTaxBill(data) {\r\n        $(\"#tid\").val(data.d_tid);\r\n        $(\"#taxBillName\").html(\"<b>계산서명</b> : \" + data.d_goodname);\r\n    }\r\n\r\n//-->\r\n</script>\r\n</head>\r\n<body bgcolor=\"#ffffff\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n    <tr>\r\n        <td align=\"center\" valign=\"top\">\r\n        <!-- title 시작 //-->\r\n        <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n            <tr>\r\n                <td><img src=\"/images/admin/propose/p_title06.gif\" ></td>\r\n                <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n            </tr>\r\n        </table>\r\n        <!-- title 끝 //-->\r\n\r\n        <br/>\r\n        <!-- form 시작 //-->\r\n        <form name=\"form1\" method=\"post\" action=\"/servlet/controller.propose.AcceptServlet\">\r\n            <input type=\"hidden\" name=\"p_process\" value=\"\">\r\n            <input type=\"hidden\" name=\"p_action\"  value=\"\">\r\n        <table cellspacing=\"0\" cellpadding=\"1\" class=\"form_table_out\">\r\n            <tr>\r\n                <td bgcolor=\"#C6C6C6\" align=\"center\">\r\n                    <table cellspacing=\"0\" cellpadding=\"0\" class=\"form_table_bg\" >\r\n                        <tr>\r\n                            <td height=\"7\"></td>\r\n                        </tr>\r\n                        <tr>\r\n                            <td align=\"center\" valign=\"middle\">\r\n                                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"99%\" class=\"form_table\">\r\n                                    <tr>\r\n                                        <td  align=\"left\" valign=\"middle\">\r\n                                                <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"99%\" class=\"_srchT\">\r\n                                                    <tr>\r\n                                                        <td width=\"12%\" class=\"_tdT\">\r\n                                                            <font color=\"red\">★</font>교육그룹\r\n                                                        </td>\r\n                                                        <td width=\"22%\" class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\r\n                                                        </td>\r\n                                                        <td width=\"12%\" class=\"_tdT\">\r\n                                                            <font color=\"red\">★</font>연도\r\n                                                        </td>\r\n                                                        <td width=\"14%\" class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\r\n                                                        </td>\r\n                                                        <td width=\"12%\" class=\"_tdT\">\r\n                                                            <font color=\"red\">★</font>교육차수\r\n                                                        </td>\r\n                                                        <td width=\"28%\" class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\r\n                                                        </td>\r\n                                                    </tr>\r\n                                                    <tr>\r\n                                                        <td class=\"_tdT\">대분류</td>\r\n                                                        <td class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\r\n                                                        </td>\r\n                                                        <td class=\"_tdT\">중분류</td>\r\n                                                        <td class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n                                                        </td>\r\n                                                        <td class=\"_tdT\">소분류</td>\r\n                                                        <td class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\r\n                                                        </td>\r\n                                                    </tr>\r\n                                                    <tr>\r\n                                                        <td class=\"_tdT\">과정검색</td>\r\n                                                        <td class=\"_tdS\">\r\n                                                            <input type=\"text\" name=\"s_subjsearchkey\" size=\"17\" onkeypress=\"if(event.keyCode==\'13\') Main_subjcourse();\" value=\"";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\">\r\n                                                            <a href=\"javascript:Main_subjcourse()\" ><img src=\"/images/admin/button/search3_butt.gif\" border=\"0\" align=\"absmiddle\"></a>\r\n                                                        </td>\r\n                                                        <td class=\"_tdT\">과정</td>\r\n                                                        <td colspan=\"3\" class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\r\n                                                        </td>\r\n                                                    </tr>\r\n                                                    <tr>\r\n                                                        <td class=\"_tdT\">과정차수</td>\r\n                                                        <td class=\"_tdS\">\r\n                                                            ";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="\r\n                                                        </td>\r\n                                                        <td class=\"_tdT\"><!--<font color=\"red\">★</font>-->회사코드</td>\r\n                                                        <td class=\"_tdS\"><input type=\"text\" id=\"grcodeText\" name=\"grcodeText\" ></td>\r\n                                                        <td colspan=\"2\" align=\"right\"><a href=\"javascript:whenExcel()\"><img src=\"/images/admin/button/btn_course_numexcel.gif\" border=\"0\"></a></td>\r\n                                                    </tr>\r\n                                                </table>\r\n\r\n                                            </td>\r\n                                        </tr>\r\n                                    </table>\r\n                                </td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td height=\"9\"></td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            </form>\r\n            <!-- form 끝 //-->\r\n\r\n            <br/>\r\n            <!-- 찾아보기 시작 //-->\r\n            <form name=\"form3\" method=\"post\" action=\"/servlet/controller.propose.AcceptServlet\" enctype=\"multipart/form-data\">\r\n                <input type=\"hidden\" name=\"p_process\" value=\"\">\r\n                <input type=\"hidden\" name=\"p_action\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_grcode\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_gyear\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_grseq\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_upperclass\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_middleclass\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_subjcourse\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_subjseq\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_subjnm\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_grcodenm\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_subjseqgrnm\" value=\"\">\r\n                <input type=\"hidden\" name=\"s_usergrcode\" value=\"\">\r\n                <input type=\"hidden\" name=\"p_inputlevel\" value=\"student\">\r\n\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr>\r\n                    <td align=\"center\">\r\n                        <table width=\"21%\" height=\"26\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                            <tr id=\"billneedbutton\" style=\"display:none\">\r\n                                <td>\r\n                                    <input type=\"hidden\" name=\"tid\" id=\"tid\" disabled/><font id=\"taxBillName\"><b>계산서명</b> : </font>\r\n                                </td>\r\n                                <td align=\"right\">\r\n                                    <a href=\"javascript:searchTaxBill()\"><img src=\"/images/admin/button/search3_butt.gif\" border=0/></a>\r\n                                </td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td align=\"center\" colspan=\"2\">\r\n                                    <input name=\"p_file\" type=\"FILE\" class=\"input\" size=\"145\">\r\n                                </td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            <!-- 찾아보기 끝 //-->\r\n            <br/>\r\n            <!-- 미리보기, 확인, 취소 버튼 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                    <td colspan=\"2\" height=\"20\">\r\n                        <b>계산서발급</b>\r\n                        <input type=\"radio\" name=\"billYn\" value=\"true\" onclick=\"$(\'#billneedbutton\').fadeIn(300);$(\'#tid\').attr(\'disabled\',\'\')\"> Yes &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n                        <input type=\"radio\" name=\"billYn\" value=\"false\" onclick=\"$(\'#billneedbutton\').fadeOut(200);$(\'#tid\').attr(\'disabled\',\'true\');\" checked> No\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=10></td>\r\n                    <td height=\"20\" align=\"right\"><a href=\"javascript:fnRegGroupPropose()\"><img src=\"/images/admin/button/btn_add.gif\"  border=\"0\"></a></td>\r\n                    <td width=10></td>\r\n                    <td align=\"right\" width=\"32\"><a href=\"javascript:fnPreview()\"><img src=\"/images/admin/button/btn_preview.gif\" border=\"0\"></a></td>\r\n                </tr>\r\n            </table>\r\n\r\n            <table width=\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                    <td width=\"928\" height=\"20\" align=\"left\">데이터체크 유무선택</td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"928\" height=\"20\" align=\"left\"><input type=\"checkbox\" disabled checked>개설차수 비교(기본 : 개설된 차수가 없으면 에러처리)</td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"928\" height=\"20\" align=\"left\"><input type=\"checkbox\" disabled checked>회원 DB 조회(기본 : 회원 DB에 존재하지 않으면 에러처리)</td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"928\" height=\"20\" align=\"left\"><input type=\"checkbox\" disabled checked>수강신청중/학습중체크(기본 : 해당 과정차수에 대상자가 수강신청을 한기록이 있으면 에러처리)</td>\r\n                </tr>\r\n                <tr>\r\n                    <td width=\"928\" height=\"20\" align=\"left\"><input type=\"checkbox\" name=\"p_isgraduated\" ";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="checked";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 =">이수(수료)자체크 (해당과정 이수자 에러처리)</td>\r\n                </tr>\r\n            </table>\r\n            </form>\r\n            <!-- 미리보기, 확인, 취소 버튼 끝 //-->\r\n            <br/><br/><br/>\r\n\r\n            <!-- 주의사항 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr>\r\n                    <td class=\"dir_txt\"><b><font size=\"3\">(주의사항 - 필독)</font></b></td>\r\n                </tr>\r\n                <tr>\r\n                    <td height=\"8\"></td>\r\n                </tr>\r\n                <tr>\r\n                    <td height=\"20\" class=\"dir_txt\">1. <font color=\"#E60873\">교육그룹과 연도, 교육차수를 선택한 후 \"과정/차수코드 엑셀다운로드\"를 눌러서 양식을 다운로드</font></td>\r\n                </tr>\r\n                <tr>\r\n                    <td height=\"20\" class=\"dir_txt\">2. <font color=\"#E60873\">다운로드 된 문서를 기준으로 사용자ID를 추가하여 반드시 \"Excel\" 문서로 저장한다.</font></td>\r\n                </tr>\r\n                <tr>\r\n                    <td height=\"20\" class=\"dir_txt\">3. 엑셀파일 작성시 <font color=\"#A661D7\">[<b>학습자ID,( 차수 ), ( 과정 )</b>]</font>의 순서로 만들 것.</td>\r\n                </tr>\r\n            </table>\r\n            <!-- 주의사항 끝 //-->\r\n\r\n            <br/>\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                <tr>\r\n                    <td align=\"left\">샘플</td>\r\n                </tr>\r\n                <tr>\r\n                    <td><img src=\"/images/admin/propose/PropExcel3.gif\" border=\"0\"></td>\r\n                </tr>\r\n            </table>\r\n        </td>\r\n    </tr>\r\n    <tr>\r\n        <td>";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n    ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="</td>\r\n    </tr>\r\n</table>\r\n</body>\r\n</html>\r\n";
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
//  1. 제      목: 입과 FileToDB
//  2. 프로그램명 : za_ProposeFileToDB.jsp
//  3. 개      요: 입과 FileToDB
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성:
//  7. 수      정:
//***********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
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

    String ss_grcode = box.getString("s_grcode");           //교육그룹
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_grseq = box.getString("s_grseq");            //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");          //교육차수명
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String ss_subjcourse = box.getString("s_subjcourse");       //과정&코스
    String ss_subjseq = box.getString("s_subjseq");          //과정 차수
    String s_subjsearchkey = box.getString("s_subjsearchkey");
    String v_stuoverlap = box.getStringDefault("p_stuoverlap", "Y");
    String v_isgraduated = box.getStringDefault("p_isgraduated", "Y");

    String v_file1 = box.getString("p_file");

            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_gyear
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grseq
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag0 = null ;
            int __result__tag0 = 0 ;

            if (__tag0== null )__tag0 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag0.setPageContext(pageContext);
            __tag0.setParent(null);
            __tag0.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_grcode", java.lang.String .class));
            __tag0.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0001", java.lang.String .class));
            __tag0.setParam(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag0.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_gyear(this.value);", java.lang.String .class));
            __tag0.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag0.setSelectedValue( ss_grcode 
);
            __tag0.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag0.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("false", java.lang.String .class));
            _activeTag=__tag0;
            __result__tag0 = __tag0.doStartTag();

            if (__result__tag0!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag0== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag0.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag0);
                return;
            }
            _activeTag=__tag0.getParent();
            __tag0.release();
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag1 = null ;
            int __result__tag1 = 0 ;

            if (__tag1== null )__tag1 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag1.setPageContext(pageContext);
            __tag1.setParent(null);
            __tag1.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_gyear", java.lang.String .class));
            __tag1.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.year", java.lang.String .class));
            __tag1.setParam( ss_grcode 
);
            __tag1.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_grseq(s_grcode.value, this.value);", java.lang.String .class));
            __tag1.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_s_grseq", java.lang.String .class));
            __tag1.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag1.setSelectedValue( ss_gyear 
);
            __tag1.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag1.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("false", java.lang.String .class));
            _activeTag=__tag1;
            __result__tag1 = __tag1.doStartTag();

            if (__result__tag1!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag1== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag1.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag1);
                return;
            }
            _activeTag=__tag1.getParent();
            __tag1.release();
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag2 = null ;
            int __result__tag2 = 0 ;

            if (__tag2== null )__tag2 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag2.setPageContext(pageContext);
            __tag2.setParent(null);
            __tag2.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_grseq", java.lang.String .class));
            __tag2.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.subjseq", java.lang.String .class));
            __tag2.setParam(ss_grcode
);
            __tag2.setParam2(ss_gyear
);
            __tag2.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
            __tag2.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_subjcourse()", java.lang.String .class));
            __tag2.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_subjcourse", java.lang.String .class));
            __tag2.setSelectedValue( ss_grseq 
);
            __tag2.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag2.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            _activeTag=__tag2;
            __result__tag2 = __tag2.doStartTag();

            if (__result__tag2!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag2== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag2.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag2);
                return;
            }
            _activeTag=__tag2.getParent();
            __tag2.release();
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag3 = null ;
            int __result__tag3 = 0 ;

            if (__tag3== null )__tag3 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag3.setPageContext(pageContext);
            __tag3.setParent(null);
            __tag3.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_upperclass", java.lang.String .class));
            __tag3.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0002", java.lang.String .class));
            __tag3.setParam(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag3.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_middleclass(this.value);", java.lang.String .class));
            __tag3.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag3.setSelectedValue( ss_upperclass 
);
            __tag3.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag3.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            _activeTag=__tag3;
            __result__tag3 = __tag3.doStartTag();

            if (__result__tag3!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag3== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag3.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag3);
                return;
            }
            _activeTag=__tag3.getParent();
            __tag3.release();
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag4 = null ;
            int __result__tag4 = 0 ;

            if (__tag4== null )__tag4 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag4.setPageContext(pageContext);
            __tag4.setParent(null);
            __tag4.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_middleclass", java.lang.String .class));
            __tag4.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0003", java.lang.String .class));
            __tag4.setParam( ss_upperclass 
);
            __tag4.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_lowerclass(s_upperclass.value, this.value);", java.lang.String .class));
            __tag4.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_s_grseq", java.lang.String .class));
            __tag4.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag4.setSelectedValue( ss_middleclass 
);
            __tag4.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag4.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            _activeTag=__tag4;
            __result__tag4 = __tag4.doStartTag();

            if (__result__tag4!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag4== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag4.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag4);
                return;
            }
            _activeTag=__tag4.getParent();
            __tag4.release();
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag5 = null ;
            int __result__tag5 = 0 ;

            if (__tag5== null )__tag5 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag5.setPageContext(pageContext);
            __tag5.setParent(null);
            __tag5.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_lowerclass", java.lang.String .class));
            __tag5.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0004", java.lang.String .class));
            __tag5.setParam( ss_upperclass 
);
            __tag5.setParam2( ss_middleclass 
);
            __tag5.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
            __tag5.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_s_grseq", java.lang.String .class));
            __tag5.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag5.setSelectedValue( ss_lowerclass 
);
            __tag5.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag5.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            _activeTag=__tag5;
            __result__tag5 = __tag5.doStartTag();

            if (__result__tag5!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag5== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag5.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag5);
                return;
            }
            _activeTag=__tag5.getParent();
            __tag5.release();
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(s_subjsearchkey
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag6 = null ;
            int __result__tag6 = 0 ;

            if (__tag6== null )__tag6 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag6.setPageContext(pageContext);
            __tag6.setParent(null);
            __tag6.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_subjcourse", java.lang.String .class));
            __tag6.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("subjlist1|subjlist2|subjlist3|subjlist4|subjlist5|subjlist6|subjlist7|subjlist8|subjlist9", java.lang.String .class));
            __tag6.setParam(ss_grcode
);
            __tag6.setParam4(ss_upperclass
);
            __tag6.setParam5(ss_middleclass
);
            __tag6.setParam6(ss_lowerclass
);
            __tag6.setParam7(ss_grseq
);
            __tag6.setParam9( s_subjsearchkey 
);
            __tag6.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_subjseq(s_grcode.value, s_grseq.value, s_subjcourse.value, s_gyear.value);", java.lang.String .class));
            __tag6.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
            __tag6.setSelectedValue( ss_subjcourse 
);
            __tag6.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag6.setAll( box.getSession("isSubjAll") 
);
            _activeTag=__tag6;
            __result__tag6 = __tag6.doStartTag();

            if (__result__tag6!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag6== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag6.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag6);
                return;
            }
            _activeTag=__tag6.getParent();
            __tag6.release();
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag7 = null ;
            int __result__tag7 = 0 ;

            if (__tag7== null )__tag7 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag7.setPageContext(pageContext);
            __tag7.setParent(null);
            __tag7.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_subjseq", java.lang.String .class));
            __tag7.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("subjectSeq1|subjectSeq2|subjectSeq3|subjectSeq4", java.lang.String .class));
            __tag7.setParam(ss_grcode
);
            __tag7.setParam2(ss_grseq
);
            __tag7.setParam3(ss_subjcourse
);
            __tag7.setParam4(ss_gyear
);
            __tag7.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
            __tag7.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag7.setSelectedValue( ss_subjseq 
);
            __tag7.setIsLoad(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            __tag7.setAll(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("true", java.lang.String .class));
            _activeTag=__tag7;
            __result__tag7 = __tag7.doStartTag();

            if (__result__tag7!= javax.servlet.jsp.tagext.Tag.SKIP_BODY){
                if (__result__tag7== javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_BUFFERED) {
                     throw  new  javax.servlet.jsp.JspTagException("Since tag class com.dunet.common.taglib.KoccaSelectTaglib does not implement BodyTag, it cannot return BodyTag.EVAL_BODY_BUFFERED");
                }
            }
            if (__tag7.doEndTag()== javax.servlet.jsp.tagext.Tag.SKIP_PAGE){
                _activeTag = null;
                _releaseTags(__tag7);
                return;
            }
            _activeTag=__tag7.getParent();
            __tag7.release();
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
if(v_isgraduated.equals("Y")){
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
}
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
