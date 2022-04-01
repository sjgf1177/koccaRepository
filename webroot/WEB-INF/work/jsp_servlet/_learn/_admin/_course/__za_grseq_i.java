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
import com.credu.common.*;

public final class __za_grseq_i extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/course/za_Grseq_I.jsp", 1422235985589L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/ui-lightness/ui.all.css\" />\r\n<script type=\'text/javascript\' src=\'/script/jquery-1.3.2.min.js\'></script>\r\n<script type=\"text/javascript\" src=\"/script/ui.core.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/effects.core.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/effects.blind.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/effects.drop.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/effects.explode.js\"></script>\r\n<script type=\"text/javascript\" src=\"/script/ui.datepicker.js\"></script>\r\n<script language = \"javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<script language=\"JavaScript\">\r\n$(document).ready(function(){\r\n    $(\"#p_propstart1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_propend1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_edustart1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_eduend1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_startcanceldate\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_endcanceldate\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_sulstartdate1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_sulenddate1\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_sulstartdate2\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n    $(\"#p_sulenddate2\").datepicker({defaultDate: new Date(),showOn: \"both\",showAnim: \"blind\",showOptions: {direction: \'horizontal\'},duration: 200})\r\n});\r\n<!--\r\n// 셀렉트 선택후 검색\r\nfunction whenSelection() {\r\n    document.form1.p_process.value=\"insertPage\";\r\n    document.form1.action=\"/servlet/controller.course.GrseqServlet\";\r\n    document.form1.submit();\r\n}\r\n\r\n// 셀렉트 선택후 검색\r\nfunction whenSelection(ss_action) {\r\n    document.form1.p_process.value=\"insertPage\";\r\n    document.form1.action.value=\"/servlet/controller.course.GrseqServlet\";\r\n    //if (ss_action==\"go\")    {\r\n            if (document.form1.s_grcode.value == \'ALL\' || document.form1.s_grcode.value == \'----\') {\r\n                alert(\"교육그룹을 선택하세요.\");\r\n                return ;\r\n            }\r\n      //}\r\n    document.form1.submit();\r\n}\r\n\r\n// 저장\r\nfunction whenSubmit(){\r\n\r\n    if (!document.form1.p_homepageyn[0].checked){\r\n        if (!document.form1.p_homepageyn[1].checked){\r\n            alert(\"홈페이지 노출여부를 선택해 주십시오.\");\r\n            return;\r\n        }\r\n    }\r\n    if (document.form1.p_gyear.value.length != 4){\r\n        alert(\"년도를 4자리로 입력하여 주십시오\");\r\n        return;\r\n    }\r\n    if (document.form1.p_grseqnm.value.length < 2){\r\n        alert(\"교육차수명을 2~25자리로 입력하여 주십시오\");\r\n        return;\r\n    }\r\n\r\n    var selectObj = document.form1.p_makeoption;\r\n\r\n    if (selectObj.options[selectObj.selectedIndex].value == \"\"){\r\n        alert(\"연결과정 승계를 선택하세요\");\r\n        selectObj.focus();\r\n        return;\r\n    } else {\r\n        if(selectObj.options[selectObj.selectedIndex].value == \"MAKE_ALL\"){\r\n            if(document.form1.s_grcode.value == \"\") {\r\n                alert(\"그룹코드를 선택하세요\");\r\n                document.form1.s_grcode.focus();\r\n                return;\r\n            }\r\n        } else if (selectObj.options[selectObj.selectedIndex].value == \"SELECT_ALL\"){\r\n            if(document.form1.s_grcode.value == \"\") {\r\n                alert(\"그룹코드를 선택하세요\");\r\n                document.form1.s_grcode.focus();\r\n                return;\r\n            }\r\n            if(document.form1.s_gyear.value == \"\") {\r\n                alert(\"년도를 선택하세요\");\r\n                document.form1.s_gyear.focus();\r\n                return;\r\n            }\r\n            if(document.form1.s_grseq.value == \"\" || document.form1.s_grseq.value == \"ALL\") {\r\n                alert(\"차수를 선택하세요\");\r\n                document.form1.s_grseq.focus();\r\n                return;\r\n            }\r\n        }\r\n    }\r\n\r\n    dateChk();\r\n\r\n    document.form1.p_copy_gyear.value = document.form1.s_gyear.value;\r\n    document.form1.p_copy_grseq.value = document.form1.s_grseq.value;\r\n\r\n    document.form1.submit();\r\n}\r\n\r\n    // 날자형식변환\r\n    function dateChk(){\r\n        ff = document.form1;\r\n\r\n        if(ff.p_propstart1.value != null){\r\n            ff.p_propstart.value=make_date(ff.p_propstart1.value)+ff.p_propstart2.options[ff.p_propstart2.selectedIndex].value;\r\n        }\r\n        if(ff.p_propend1.value != null){\r\n            ff.p_propend.value=make_date(ff.p_propend1.value)+ff.p_propend2.options[ff.p_propend2.selectedIndex].value;\r\n        }\r\n\r\n        if(ff.p_edustart1.value != null){\r\n            ff.p_edustart.value=make_date(ff.p_edustart1.value)+ff.p_edustart2.options[ff.p_edustart2.selectedIndex].value;\r\n        }\r\n        if(ff.p_eduend1.value != null){\r\n            ff.p_eduend.value=make_date(ff.p_eduend1.value)+ff.p_eduend2.options[ff.p_eduend2.selectedIndex].value;\r\n        }\r\n\r\n\r\n        return true;\r\n    }\r\n\r\n// 날자 삭제(달력)\r\nfunction whenclick(caldate) {\r\n    caldate.value = \"\";\r\n}\r\n\r\nfunction changeMakeOption() {\r\n    var selectObj = document.form1.p_makeoption;\r\n    var selectDiv = document.getElementById(\"div_select\");\r\n    var trObj     = document.getElementsByName(\"sTr\");\r\n    var trLen     = trObj.length;\r\n\r\n    if(selectObj.options[selectObj.selectedIndex].value == \"MAKE_ALL\"){\r\n        selectDiv.style.display = \"none\";\r\n        for(var i = 0 ; i < trLen ; i++) {\r\n            trObj[i].style.display = \"block\";\r\n        }\r\n            window.resizeTo(630,600);\r\n    } else if (selectObj.options[selectObj.selectedIndex].value == \"SELECT_ALL\"){\r\n        selectDiv.style.display = \"block\";\r\n        for(var i = 0 ; i < trLen ; i++) {\r\n            trObj[i].style.display = \"block\";\r\n        }\r\n            window.resizeTo(630,600);\r\n    } else if(selectObj.options[selectObj.selectedIndex].value == \"MANUAL\"){\r\n        selectDiv.style.display = \"none\";\r\n        for(var i = 0 ; i < trLen ; i++) {\r\n            trObj[i].style.display = \"none\";\r\n        }\r\n            window.resizeTo(630,400);\r\n    }\r\n}\r\n//-->\r\n</script>\r\n</head>\r\n\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n\r\n<form name = \"form1\" method = \"post\" action=\"/servlet/controller.course.GrseqServlet\">\r\n    <input type=\"hidden\" name=\"p_process\"   value=\"insert\">\r\n    <input type=\"hidden\" name=\"p_grcode\"    value=\"";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\">\r\n    <!--input type=\"hidden\" name=\"p_grcode\"        value=\"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\"-->\r\n    <!--input type=\"hidden\" name=\"p_gyear\"         value=\"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\"-->\r\n    <input type=\"hidden\" name=\"p_copy_gyear\">\r\n    <input type=\"hidden\" name=\"p_copy_grseq\">\r\n\r\n    <input type=\"hidden\" name=\"s_upperclass\"    value=\"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\"> <!--in relation to select-->\r\n    <input type=\"hidden\" name=\"s_middleclass\"   value=\"";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\"><!--in relation to select-->\r\n    <input type=\"hidden\" name=\"s_subjcourse\"    value=\"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\"> <!--in relation to select-->\r\n    <input type=\"hidden\" name=\"s_subjseq\"       value=\"";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\">    <!--in relation to select-->\r\n\r\n<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\r\n      <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr>\r\n          <td><img src=\"/images/admin/course/c_title02.gif\" ></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n      <!----------------- title 끝 ----------------->\r\n        <br>\r\n        <br>\r\n\r\n         <!----------------- 교육차수 정보 등록/수정 시작 ----------------->\r\n        <table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n          <tr>\r\n            <td colspan=\"2\" class=\"table_top_line\"></td>\r\n          </tr>\r\n          <tr>\r\n            <td width=\"20%\" height=\"25\" class=\"table_title\"><b>교육그룹</b></td>\r\n            <td class=\"table_02_2\" width=\"81%\"> <b>";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="</b></td>\r\n          </tr>\r\n          <tr>\r\n            <td class=\"table_title\" height=\"25\"><b>년도</b></td>\r\n            <td class=\"table_02_2\">\r\n                <input name=\"p_gyear\"       type=\"text\"     class=\"input\" size=\"4\" value=\"";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\">\r\n                <!--input name=\"p_grseqnm\"  type=\"hidden\" value=\"0001\"-->\r\n            </td>\r\n          </tr>\r\n          <tr>\r\n            <td class=\"table_title\" height=\"25\"><b>교육차수명</b></td>\r\n            <td class=\"table_02_2\">\r\n              <input name=\"p_grseqnm\" type=\"text\" class=\"input\" size=\"40\" maxlength=\"50\" value=\"";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\"> <b>ex) 2006.01.01 ~ 2006.01.31</b>\r\n            </td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"25\" class=\"table_title\"><b>연결과정 승계</b></td>\r\n            <td class=\"table_02_2\">\r\n              <select name=\"p_makeoption\" onchange=\"changeMakeOption();\">\r\n                <option value=\"\"> -- 선택 --</option>\r\n                <option value=\"MAKE_ALL\"   ";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="selected";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 =">교육그룹에 등록된 과정 모두 일괄생성</option>\r\n                <option value=\"SELECT_ALL\" ";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 =">선택 교육차수에 등록된 과정 모두 일괄생성</option>\r\n                <option value=\"MANUAL\"     ";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 =">직접 선택</option>\r\n              </select>\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>복사대상교육차수</b></td>\r\n            <td class=\"table_02_2\">\r\n                                교육그룹 ";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="\r\n            <div id=\"div_select\">\r\n                              연도 ";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\r\n                               교육차수 ";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="\r\n            </div>\r\n            ";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="<!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->\r\n            ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="<!-- getGyear(RequestBox, isChange)   교육년도  -->\r\n            ";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="<!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>수강신청시작일시</b></td>\r\n            <td class=\"table_02_2\">\r\n              <input name=\"p_propstart1\" id=\"p_propstart1\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\">\r\n              일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n              <select name=\"p_propstart2\">\r\n                ";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n              </select>\r\n              시&nbsp;\r\n              <a href=\"javascript:whenclick(document.form1.p_propstart1)\"> <img src=\"/images/admin/button/btn_del.gif\" align=\"absmiddle\" border=\"0\"></a>\r\n              <input type=hidden name=\"p_propstart\" value=\"\">\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>수강신청종료일시</b></td>\r\n            <td class=\"table_02_2\">\r\n              <input name=\"p_propend1\" id=\"p_propend1\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\">\r\n              일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n              <select name=\"p_propend2\">\r\n                ";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 ="\r\n              </select>\r\n              시&nbsp;\r\n              <a href=\"javascript:whenclick(document.form1.p_propend1)\"> <img src=\"/images/admin/button/btn_del.gif\" align=\"absmiddle\" border=\"0\"></a>\r\n              <input type=hidden name=\"p_propend\"  value=\"\">\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>학습 시작일시</b></td>\r\n            <td class=\"table_02_2\">\r\n              <input name=\"p_edustart1\" id=\"p_edustart1\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\">\r\n              일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n              <select name=\"p_edustart2\">\r\n                ";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 ="\r\n              </select>\r\n              시&nbsp;\r\n              <a href=\"javascript:whenclick(document.form1.p_edustart1)\"> <img src=\"/images/admin/button/btn_del.gif\" align=\"absmiddle\" border=\"0\"></a>\r\n              <input type=hidden name=\"p_edustart\"  value=\"\">\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>학습 종료일시</b></td>\r\n            <td class=\"table_02_2\">\r\n              <input name=\"p_eduend1\" id=\"p_eduend1\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\">\r\n              일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\r\n              <select name=\"p_eduend2\">\r\n                ";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 ="\r\n              </select>\r\n              시&nbsp;\r\n              <a href=\"javascript:whenclick(document.form1.p_eduend1)\"> <img src=\"/images/admin/button/btn_del.gif\" align=\"absmiddle\" border=\"0\"></a>\r\n              <input type=hidden name=\"p_eduend\" value=\"\">\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\"><b>수강신청취소기간</b></td>\r\n            <td class=\"table_02_2\">\r\n                <input name=\"p_startcanceldate\" id=\"p_startcanceldate\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\" onMouseover=\"if(this.value==\'\') this.value=p_propstart1.value\"> 일\r\n                - <input name=\"p_endcanceldate\" id=\"p_endcanceldate\" type=\"text\" class=\"datepicker_input1\" size=\"10\" value=\"\" onMouseover=\"if(this.value==\'\') this.value=p_propend1.value\"> 일\r\n              <!-- 학습시작일로부터 <input type=\"text\" name=\"p_canceldays\" value=\"0\" size=\"5\" class=\"input\"> 일 -->\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td class=\"table_title\" height=\"25\">홈페이지노출여부</td>\r\n            <td class=\"table_02_2\">\r\n              <input type=\"radio\" value=\"Y\" name=\"p_homepageyn\" >노출&nbsp;&nbsp;&nbsp;\r\n              <input type=\"radio\" value=\"N\" name=\"p_homepageyn\" >미노출\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td align=\"center\" class=\"table_title\">사전설문</td>\r\n            <td class=\"table_02_2\">\r\n              ";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

    private final static String  _wl_block26 ="\r\n              <br><br>\r\n              설문기간 : <input type=\"text\" name=\"p_sulstartdate1\" id=\"p_sulstartdate1\" class=\"datepicker_input1\" size=\"10\" value=\"\"> 일\r\n              ~ <input type=\"text\" name=\"p_sulenddate1\" id=\"p_sulenddate1\" class=\"datepicker_input1\" size=\"10\" value=\"\"> 일\r\n            </td>\r\n          </tr>\r\n\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td align=\"center\" class=\"table_title\">사후설문</td>\r\n            <td class=\"table_02_2\">\r\n              ";
    private final static byte[]  _wl_block26Bytes = _getBytes( _wl_block26 );

    private final static String  _wl_block27 ="\r\n              <br><br>\r\n              설문기간 : <input type=\"text\" name=\"p_sulstartdate2\" id=\"p_sulstartdate2\" class=\"datepicker_input1\" size=\"10\" value=\"\"> 일\r\n              ~ <input type=\"text\" name=\"p_sulenddate2\" id=\"p_sulenddate2\" class=\"datepicker_input1\" size=\"10\" value=\"\"> 일\r\n            </td>\r\n          </tr>\r\n          <tr id=\"sTr\" name=\"sTr\">\r\n            <td align=\"center\" class=\"table_title\">운영담당</td>\r\n            <td class=\"table_02_2\">\r\n                <input type=\"text\" name=\"p_charger\" />\r\n            </td>\r\n          </tr>\r\n\r\n        </table>\r\n        <!----------------- 교육차수 정보 등록/수정 끝 ----------------->\r\n        <br>\r\n\r\n\r\n        <!----------------- 저장, 취소 버튼 시작 ----------------->\r\n        <table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n          <tr onMouseover=\"if(p_startcanceldate.value==\'\') p_startcanceldate.value=p_propstart1.value;if(p_endcanceldate.value==\'\') p_endcanceldate.value=p_propend1.value\">\r\n            <td align=\"center\"><a href=\"javascript:whenSubmit()\"><img src=\"/images/admin/button/btn_save.gif\" border=\"0\"></a></td>\r\n            <td width=8></td>\r\n            <td align=\"center\"><a href=\"javascript:window.close()\"><img src=\"/images/admin/button/btn_cancel.gif\" border=\"0\"></a></td>\r\n          </tr>\r\n        </table>\r\n        <!----------------- 저장, 취소 버튼 끝 ----------------->\r\n\r\n        <br>\r\n\r\n      </td>\r\n  </tr>\r\n\r\n    <tr><td>";
    private final static byte[]  _wl_block27Bytes = _getBytes( _wl_block27 );

    private final static String  _wl_block28 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block28Bytes = _getBytes( _wl_block28 );

    private final static String  _wl_block29 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block29Bytes = _getBytes( _wl_block29 );

    private final static String  _wl_block30 ="\r\n    ";
    private final static byte[]  _wl_block30Bytes = _getBytes( _wl_block30 );

    private final static String  _wl_block31 ="</td></tr>\r\n\r\n</table>\r\n\r\n</form>\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block31Bytes = _getBytes( _wl_block31 );

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
//  1. 제      목: 교육그룹등록 화면
//  2. 프로그램명: za_EduGroup_I.jsp
//  3. 개      요: 교육그룹등록 화면
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
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

                }
            }
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_gyear     = box.getString("s_gyear");            //년도
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String  ss_subjcourse= box.getString("s_subjcourse");       //과정&코스
    String  ss_subjseq   = box.getString("s_subjseq");          //과정 차수
    String  ss_grseq   = box.getString("s_grseq");              //교육그룹 차수
    //DEFINED in relation to select END

    String v_grcode = box.getString("p_grcode");
    String v_gyear  = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    String v_grseqnm = box.getString("p_grseqnm");

    String p_charger = box.getString("p_charger");
    GrseqBean grseqbean = new GrseqBean();
    String v_makeoption = box.getString("p_makeoption");
    String v_copy_gyear = box.getString("p_copy_gyear");
    String v_copy_grseq = "";

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcode
));
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_gyear
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_upperclass
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_middleclass
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_subjcourse
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_subjseq
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(GetCodenm.get_grcodenm(v_grcode)
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseqnm
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
if(v_makeoption.equals("MAKE_ALL")){
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
}
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
if(v_makeoption.equals("SELECT_ALL")){
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
}
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
if(v_makeoption.equals("MANUAL")){
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
}
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
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
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
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
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
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
            __tag2.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" style=\'width:130px;\'", java.lang.String .class));
            __tag2.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
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
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
//= SelectEduBean.getGrcode(box, true, false)
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
//= SelectEduBean.getGyear(box, true)
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
//= SelectEduBean.getGrseq(box, true, true)
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getDateOptions(0,23,0)
));
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getDateOptions(0,23,23)
));
            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getDateOptions(0,23,0)
));
            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getDateOptions(0,23,23)
));
            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(grseqbean.selectSulPaper("p_sulpaper1",v_grcode, "",1)
));
            {_writeText(response, out, _wl_block26, _wl_block26Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(grseqbean.selectSulPaper("p_sulpaper2",v_grcode, "",1)
));
            {_writeText(response, out, _wl_block27, _wl_block27Bytes);}

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
            {_writeText(response, out, _wl_block28, _wl_block28Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block29, _wl_block29Bytes);}
  }   
            {_writeText(response, out, _wl_block30, _wl_block30Bytes);}
            {_writeText(response, out, _wl_block31, _wl_block31Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
