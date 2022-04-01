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

public final class __mscreenfbody extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/kocca/mScreenFbody.jsp", 1422235940953L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block2 ="\r\n\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/assets/jquery.multiselect.css\" />\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/assets/jquery.multiselect.filter.css\" />\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/assets/style.css\" />\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/assets/prettify.css\" />\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/jqueryui/1.9.2/themes/redmond/jquery-ui.css\" />\r\n<script type=\"text/javascript\" src=\"/js/jquery/1.7.2/jquery.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/jqueryui/1.9.2/jquery-ui.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/assets/jquery.multiselect.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/assets/jquery.multiselect.filter.js\"></script>\r\n<script type=\"text/javascript\" src=\"/js/assets/prettify.js\"></script>\r\n<script language=\"Javascript\">\r\n<!--\r\n\tfunction winopen() {\r\n\t\tpopwin = window.open(\"mMenutempLeft.jsp\", \"openAdminpop\", \"toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=200,height=700,top=0,left=0\");\r\n\t}\r\n\r\n\tfunction pop_SetStat(){\r\n\t\tpopwin = window.open(\"/learn/admin/statistics/main/za_SettingStat.jsp\", \"openSetStat\", \"toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,width=600,height=700,top=0,left=0\");\r\n\t}\r\n\r\n\t$(document).ready(function() { \r\n\t \t\r\n\t \t$(\'#b_status1_hide, #b_status1_show, #b_status1\').click(function(e) {\r\n\t \t\tif ($(e.target).is(\"#b_status1\")) {  // 정규강좌\r\n\t\t \t\tvar s2_grcode = $(\"#s_grcode\").val();\r\n\t\t \t\tvar s2_gyear = $(\"#s_gyear\").val();\r\n\t\t \t\tvar s2_grseq = $(\"#s_grseq\").val();\r\n\r\n\t\t\t\tif(s2_grcode == \"\") {\r\n\t\t\t\t\talert(\"교육그룹을 선택하세요.\");\r\n\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t\tif(s2_gyear == \"\") {\r\n\t\t\t\t\talert(\"교육년도을 선택하세요.\");\r\n\t\t\t\t\treturn false;\r\n\t\t\t\t}\r\n\t\t\t\t\r\n\t \t\t\t$.ajax({ type : \"POST\", \t\t\t\r\n\t\t\t   \t\turl : \"/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat\", \r\n\t\t\t   \t\tdata: \"&param1=\"+s2_grcode+\"&param2=\"+s2_gyear+\"&param3=\"+s2_grseq, \r\n\t\t\t   \t\tbeforeSend: function(){\r\n\t \t\t\t\t\t$(\"#status1\").show();\r\n\t \t\t\t\t\t$(\'#status1\').html(\'<img src=\"/learn/user/contents/loading.gif\" border=\"0\"/>\');\r\n \t\t\t\t\t},\r\n\t\t\t   \t\tsuccess: function(msg){ \r\n\t\t\t   \t\t\tif(msg.length > 0) {\r\n\t\t\t\t   \t\t\t //$(\'#goldclass_stat\').html(msg);\r\n\t\t\t\t   \t\t\t $(\"#status1\").children().remove();\r\n\t\t\t\t   \t\t\t $(\"#status1\").append(msg);\r\n\t\t\t   \t\t\t} \r\n\t\t\t   \t\t}  \r\n\t\t\t   \t});//end ajax   \r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status1_hide\")) {  // 정규강좌 접기\r\n\t\t \t\t$(\"#status1\").hide();\r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status1_show\")) {  // 정규강좌 펼치기\r\n\t \t\t\t$(\"#status1\").show();\r\n\t \t\t}\r\n\t \t\treturn false;\r\n\t \t});\r\n\r\n\t \t$(\'#b_status2, #b_status2_hide, #b_status2_show\').click(function(e) {\r\n\t\t \tif ($(e.target).is(\"#b_status2\")) {  // 열린강좌 \r\n\t\t \t\tvar search2_year = $(\"#status2_year\").val();\r\n\t \t\t\t$.ajax({ type : \"POST\", \t\t\t\r\n\t\t\t   \t\turl : \"/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat2\", \r\n\t\t\t   \t\tdata: \"&param1=\"+search2_year, \r\n\t\t\t   \t\tbeforeSend: function(){\r\n\t\t\t\t\t\t\t$(\"#status2\").show();\r\n\t\t\t\t\t\t\t$(\"#status2\").html(\'<img src=\"/learn/user/contents/loading.gif\" border=\"0\"/>\');\r\n\t\t\t\t\t},\r\n\t\t\t   \t\tsuccess: function(msg){ \r\n\t\t\t   \t\t\tif(msg.length > 0) {\r\n\t\t\t\t   \t\t\t //$(\'#goldclass_stat\').html(msg);\r\n\t\t\t\t   \t\t\t $(\"#status2\").children().remove();\r\n\t\t\t\t   \t\t\t $(\"#status2\").append(msg);\r\n\t\t\t   \t\t\t} \r\n\t\t\t   \t\t}  \r\n\t\t\t   \t});//end ajax   \r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status2_hide\")) {  // 열린강좌\r\n\t\t \t\t$(\"#status2\").hide();\r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status2_show\")) {  // 열린강좌\r\n\t \t\t\t$(\"#status2\").show();\r\n\t \t\t}\r\n\t \t\treturn false;\r\n\t \t});\r\n\r\n\t \t$(\'#b_status3, #b_status3_hide, #b_status3_show\').click(function(e) {\r\n\t\t \tif ($(e.target).is(\"#b_status3\")) {  // 튜터 \r\n\t\t \t\tvar search3_year = $(\"#status3_year\").val();\r\n\t\t \t\tvar status3_gubun = $(\"#status3_gubun\").val();\r\n\t \t\t\t$.ajax({ type : \"POST\", \t\t\t\r\n\t\t\t   \t\turl : \"/servlet/controller.statistics.MainStatusticServlet?p_process=selectStat3\", \r\n\t\t\t   \t\tdata: \"&param1=\"+search3_year+\"&param2=\"+status3_gubun, \r\n\t\t\t   \t\tbeforeSend: function(){\r\n\t\t\t\t\t\t\t$(\"#status3\").show();\r\n\t\t\t\t\t\t\t$(\'#status3\').html(\'<img src=\"/learn/user/contents/loading.gif\" border=\"0\"/>\');\r\n\t\t\t\t\t},\r\n\t\t\t   \t\tsuccess: function(msg){ \r\n\t\t\t   \t\t\tif(msg.length > 0) {\r\n\t\t\t\t   \t\t\t //$(\'#goldclass_stat\').html(msg);\r\n\t\t\t\t   \t\t\t $(\"#status3\").children().remove();\r\n\t\t\t\t   \t\t\t $(\"#status3\").append(msg);\r\n\t\t\t   \t\t\t} \r\n\t\t\t   \t\t}  \r\n\t\t\t   \t});//end ajax   \r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status3_hide\")) {  // 튜터\r\n\t\t \t\t$(\"#status3\").hide();\r\n\t \t\t}\r\n\t \t\tif ($(e.target).is(\"#b_status3_show\")) {  // 튜터\r\n\t \t\t\t$(\"#status3\").show();\r\n\t \t\t}\r\n\t \t\treturn false;\r\n\t \t});\r\n\t} \r\n\t\r\n\t); \r\n-->\r\n</script>\r\n</head>\r\n\r\n<body>\r\n<!-- table width=\"100%\" height=\"30\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n  <tr> \r\n    <td align=\"center\">&nbsp;</td>\r\n  </tr>\r\n  <tr> \r\n    <td valign=\"top\" align=\"center\" style=\"background-color: #dfdfdf;\">\r\n    \t<b>메뉴가 안보일경우 <a href=\"javascript:winopen();\">여기</a>를 클릭하시기 바랍니다.(임시메뉴)</b>\r\n    </td>\r\n  </tr>\r\n</table-->\r\n\r\n\r\n<form name = \"form1\" method=\"post\">\r\n\r\n<!-- 과정 통계 시작 -->\r\n";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n<div style=\"text-align: center;\">\r\n\t<strong>과정운영 통계</strong><br/>\r\n\t교육그룹 : ";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="&nbsp;&nbsp;\r\n\t교육연도 : ";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="&nbsp;&nbsp;\r\n\t교육차수 : ";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="&nbsp;&nbsp;\r\n\t<button id=\"b_status1\">검색</button>&nbsp;<button id=\"b_status1_hide\">접기</button>&nbsp;<button id=\"b_status1_show\">펼치기</button>&nbsp;&nbsp;&nbsp;<button onclick=\"javascript:pop_SetStat();\">통계사용설정</button>\r\n</div><br/>\r\n<div id=\"status1\" style=\"text-align: center;\">\r\n\t<table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"9\" class=\"table_top_line\"></td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n            <td class=\"table_title\" width=\"10%\" height=\"25\"><b>년도</b></td>\r\n   \t        <td class=\"table_title\"><b>교육차수명</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>과정수</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>신청수</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>승인수</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>반려수</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>수료</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>미수료</b></td>\r\n   \t        <td class=\"table_title\" width=\"8%\"><b>수료률</b></td>\r\n       \t    \r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"9\" class=\"table_02_1\">\r\n\t\t\t조건 선택 후 검색버튼을 클릭해주세요\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n</div>\r\n<!-- 과정 통계 끝 -->\r\n<hr></hr>\r\n<br/>\r\n\r\n<!-- 열린강좌 통계 시작 -->\r\n<div style=\"text-align: center;\">\r\n\t<strong>열린강좌 통계</strong><br/>\r\n\t검색연도 : <select name=\"status2_year\" id=\"status2_year\">\r\n\t  \t<option value=\"2014\" >2014</option>\r\n\t  \t<option value=\"2013\" >2013</option>\r\n\t  \t<option value=\"2012\" >2012</option>\r\n\t</select>&nbsp;&nbsp;\r\n\t<button id=\"b_status2\">검색</button>&nbsp;<button id=\"b_status2_hide\">접기</button>&nbsp;<button id=\"b_status2_show\">펼치기</button>\r\n</div><br/>\r\n<div id=\"status2\" style=\"text-align: center;\">\r\n\t<table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"14\" class=\"table_top_line\"></td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n            <td class=\"table_title\" width=\"9%\" height=\"25\"><b>분류</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>1월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>2월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>3월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>4월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>5월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>6월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>7월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>8월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>9월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>10월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>11월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>12월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>합계</b></td>\r\n       \t    \r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"14\" class=\"table_02_1\">\r\n\t\t\t조회하실 년도를 선택 후 검색버튼을 클릭해주세요\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n</div>\r\n<!-- 열린강좌 통계 끝 -->\r\n<hr></hr>\r\n<br/>\r\n\r\n<!-- 튜터 통계 시작 -->\r\n<div style=\"text-align: center;\">\r\n\t<strong>튜터활동 통계</strong><br/>\r\n\t검색구분 : <select name=\"status3_gubun\" id=\"status3_gubun\">\r\n\t\t<option value=\"I\">정보게시</option>\r\n\t\t<option value=\"Q\">과정질문</option>\r\n\t</select>&nbsp;&nbsp;\r\n\t검색연도 : <select name=\"status3_year\" id=\"status3_year\">\r\n\t  \t<option value=\"2014\" >2014</option>\r\n\t  \t<option value=\"2013\" >2013</option>\r\n\t  \t<option value=\"2012\" >2012</option>\r\n\t</select>&nbsp;&nbsp;\r\n\t<button id=\"b_status3\">검색</button>&nbsp;<button id=\"b_status3_hide\">접기</button>&nbsp;<button id=\"b_status3_show\">펼치기</button>\r\n</div><br/>\r\n<div id=\"status3\" style=\"text-align: center;\">\r\n\t<table class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"14\" class=\"table_top_line\"></td>\r\n\t\t</tr>\r\n\t\t<tr>\r\n            <td class=\"table_title\" width=\"9%\" height=\"25\"><b>분류</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>1월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>2월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>3월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>4월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>5월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>6월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>7월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>8월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>9월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>10월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>11월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>12월</b></td>\r\n   \t        <td class=\"table_title\" width=\"7%\"><b>합계</b></td>\r\n       \t    \r\n\t\t</tr>\r\n\t\t<tr>\r\n\t\t\t<td colspan=\"14\" class=\"table_02_1\">\r\n\t\t\t조회하실 년도를 선택 후 검색버튼을 클릭해주세요\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n</div>\r\n<!-- 튜터 통계 끝 -->\r\n<hr></hr>\r\n\r\n</form>\r\n</body>\r\n</html>\r\n";
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
        response.setHeader("Content-Type", "text/html;charset=MS949");
        out = pageContext.getOut();
        javax.servlet.jsp.JspWriter _originalOut = out;
        javax.servlet.http.HttpSession session = request.getSession( true );
        try {;
            response.setContentType("text/html;charset=MS949");

/**
 * file name : mScreenFbody.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen Initial Body
 */

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

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    String  ss_grcode	= box.getStringDefault("s_grcode","N000001");		   //교육그룹
	String  ss_gyear	 = box.getString("s_gyear");			//년도
	String  ss_grseq	 = box.getString("s_grseq");			//교육차수

            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag0 = null ;
            int __result__tag0 = 0 ;

            if (__tag0== null )__tag0 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag0.setPageContext(pageContext);
            __tag0.setParent(null);
            __tag0.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_grcode", java.lang.String .class));
            __tag0.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0001", java.lang.String .class));
            __tag0.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_gyear(this.value);", java.lang.String .class));
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
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
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
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag2 = null ;
            int __result__tag2 = 0 ;

            if (__tag2== null )__tag2 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag2.setPageContext(pageContext);
            __tag2.setParent(null);
            __tag2.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_grseq", java.lang.String .class));
            __tag2.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.subjseq2", java.lang.String .class));
            __tag2.setParam(ss_grcode
);
            __tag2.setParam2(ss_gyear
);
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
