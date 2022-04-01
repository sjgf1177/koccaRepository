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

public final class __za_grseq_l extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/course/za_Grseq_L.jsp", 1422235985667L ,"9.2.3.0","Asia/Seoul")) return true;
        if (sci.isResourceStale("/learn/library/getJspName.jsp", 1422235939253L ,"9.2.3.0","Asia/Seoul")) return true;
        if (sci.isResourceStale("/learn/admin/include/za_GoButton.jsp", 1422235964650L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" href=\"/css/admin_style.css\" type=\"text/css\">\r\n<script type=\'text/javascript\' src=\'/script/jquery-1.3.2.min.js\'></script>\r\n<script language=\"JavaScript\">\r\n\r\nfunction MM_jumpMenu(targ,selObj,restore){ //v3.0\r\n  eval(targ+\".location=\'\"+selObj.options[selObj.selectedIndex].value+\"\'\");\r\n  if (restore) selObj.selectedIndex=0;\r\n}\r\n\r\n// 셀렉트 선택후 검색\r\nfunction whenSelection(ss_action) {\r\n    document.form1.p_process.value=\"listPage\";\r\n    document.form1.p_action.value = ss_action;\r\n\r\n\tif (ss_action==\"go\")    {\r\n\t\tif (document.form1.s_grcode.value == \'ALL\' || document.form1.s_grcode.value == \'----\' || document.form1.s_grcode.value == \'\') {\r\n\t\t\talert(\"교육그룹을 선택하세요.\");\r\n\t\t\treturn ;\r\n\t\t}\r\n\t\tif (document.form1.s_gyear.value == \'\') {\r\n\t\t\talert(\"연도를 선택하세요.\");\r\n\t\t\treturn ;\r\n\t\t}\r\n\t\ttop.ftop.setPam();\r\n\t}\r\n\t\r\n    document.form1.submit();\r\n}\r\n/*\r\n// 상세페이지로 이동\r\nfunction whenDetail(subj,subjnm, grseq) {\r\n    document.form1.p_process.value=\"listDetailPage\";\r\n    document.form1.p_mgrseq.value = grseq;\r\n    document.form1.p_subj.value = subj;\r\n    document.form1.p_subjnm.value = subjnm;\r\n    document.form1.submit();\r\n}\r\n*/\r\n\r\nfunction elementForm() {\r\n  var form = document.form1;\r\n  var s_url = \"\";\r\n  s_url += \"&s_upperclass=\"+form.s_upperclass.value;\r\n  s_url += \"&s_middleclass=\"+form.s_middleclass.value;\r\n  s_url += \"&s_lowerclass=\"+form.s_lowerclass.value;\r\n  s_url += \"&s_subjsearchkey=\"+form.s_subjsearchkey.value;\r\n  s_url += \"&s_subjcourse=\"+form.s_subjcourse.value;\r\n  return s_url;\r\n}\r\n\r\n//교육차수 추가\r\nfunction addGrseq(){\r\n";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n    alert (\"교육그룹 선택후 go버튼을 먼저 누르신후 차수를 추가하십시요\")\r\n    return\r\n";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n    var url=document.form1.action+\'?p_process=insertPage&p_grcode=";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\';\r\n    window.open(url,\'insert\',\'width=620,height=600, scrollbars=yes\');\r\n";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\r\n}\r\n\r\n//교육차수 상세화면\r\nfunction whenGrseq(gyear,grseq){\r\n    var url=document.form1.action+\'?p_process=updatePage&p_grcode=";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="&p_gyear=\'+gyear+\'&p_grseq=\'+grseq+elementForm();\r\n    window.open(url,\'insert\',\'width=600,height=580\');\r\n}\r\n\r\n//과정지정화면으로 이동\r\nfunction assignSubjCourse(gyear,grseq){\r\n    var url=document.form1.action+\'?p_process=assignPage&p_grcode=";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="&p_gyear=\'+gyear+\'&p_grseq=\'+grseq+elementForm();\r\n    window.open(url,\'insert\',\'toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600\');\r\n}\r\n\r\n//과정일괄 수정화면으로 이동\r\nfunction updateSubjCourse(gyear,grseq){\r\n    var url=document.form1.action+\'?p_process=updatePageSubjCourse&p_grcode=";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="&p_gyear=\'+gyear+\'&p_grseq=\'+grseq;\r\n    window.open(url,\'insert\',\'toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600\');\r\n}\r\n\r\n//수료점수일괄 수정화면으로 이동\r\nfunction updateSubjScore(gyear,grseq){\r\n    var url=document.form1.action+\'?p_process=updatePageSubjScore&p_grcode=";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="&p_gyear=\'+gyear+\'&p_grseq=\'+grseq;\r\n    window.open(url,\'insert\',\'toolbar=no, location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=900,height=600\');\r\n}\r\n\r\n\r\n\r\nfunction delCourse(gyear,grseq,course,cyear,courseq){\r\n    if(!confirm(\"모든 학습데이터까지 삭제되어 복원할 수 없습니다.\\n\\n삭제하시겠습니까?\")) return;\r\n    setHiddenVals(\"delCourse\",gyear,grseq,course,cyear,courseq);\r\n    document.form1.submit();\r\n}\r\nfunction addCourse(coursenm,gyear,grseq,course){\r\n    if(!confirm(\"[\"+coursenm+\"] 코스의 차수를 추가합니다.\\n\\n추가하시겠습니까?\")) return;\r\n    setHiddenVals(\"addCourse\",gyear,grseq,course,\"\",\"\");\r\n    document.form1.submit();\r\n}\r\nfunction delSubj(gyear,grseq,subj,year,subjseq){\r\n    if(!confirm(\"모든 학습데이터까지 삭제되어 복원할 수 없습니다.\\n\\n삭제하시겠습니까?\")) return;\r\n    setHiddenVals(\"delSubj\",gyear,grseq,subj,year,subjseq);\r\n    document.form1.submit();\r\n}\r\nfunction addSubj(subjnm,gyear,grseq,subj){\r\n    if(!confirm(\"[\"+subjnm+\"] 과정의 차수를 추가합니다.\\n\\n추가하시겠습니까?\")) return;\r\n    setHiddenVals(\"addSubj\",gyear,grseq,subj,\"\",\"\");\r\n    document.form1.submit();\r\n}\r\n\r\nfunction setHiddenVals(process, gyear,grseq,subjcourse,year,seq){\r\n    document.form1.p_action.value = \"go\";\r\n    document.form1.p_process.value= process;\r\n    document.form1.p_mgyear.value=gyear;\r\n    document.form1.p_mgrseq.value=grseq;\r\n    document.form1.p_msubjcourse.value=subjcourse;\r\n    document.form1.p_myear.value=year;\r\n    document.form1.p_mseq.value=seq;\r\n}\r\n\r\nfunction whenSubjseq(grcode,gyear,grseq,subj,year,subjseq,isonoff){\r\n    var url=document.form1.action+\'?p_process=updateSubjseqPage&p_grcode=\'+grcode+\'&p_gyear=\'+gyear+\'&p_grseq=\'+grseq+\'&p_subj=\'+subj+\'&p_year=\'+year+\'&p_subjseq=\'+subjseq+\'&p_isonoff=\'+isonoff;\r\n    window.open(url,\'insert\',\'resizable=yes,scrollbars=yes,status=yes,width=800,height=700\');\r\n}\r\nfunction whenCourseseq(grcode,gyear,grseq,course,cyear,courseseq){\r\n    var url=\'/servlet/controller.course.CourseServlet?p_process=updateCourseseqPage&p_grcode=\'+grcode+\'&p_gyear=\'+gyear+\'&p_grseq=\'+grseq+\'&p_course=\'+course+\'&p_cyear=\'+cyear+\'&p_courseseq=\'+courseseq;\r\n    window.open(url,\'insert\',\'resizable=yes,scrollbars=yes,status=yes,width=800,height=700\');\r\n}\r\n\r\n//전체선택\r\nfunction whenAllSelect() {\r\n  if(document.form1.all[\'p_checks\'] == \'[object]\') {\r\n    if (document.form1.p_checks.length > 0) {\r\n      for (i=0; i<document.form1.p_checks.length; i++) {\r\n        document.form1.p_checks[i].checked = true;\r\n      }\r\n    } else {\r\n      document.form1.p_checks.checked = true;\r\n    }\r\n  }\r\n}\r\n\r\n//전체선택취소\r\nfunction whenAllSelectCancel() {\r\n  if(document.form1.all[\'p_checks\'] == \'[object]\') {\r\n    if (document.form1.p_checks.length > 0) {\r\n      for (i=0; i<document.form1.p_checks.length; i++) {\r\n        document.form1.p_checks[i].checked = false;\r\n      }\r\n    } else {\r\n      document.form1.p_checks.checked = false;\r\n    }\r\n  }\r\n}\r\n\r\n//엑셀다운\r\nfunction whenExcel(ss_action) {\r\n\t/*alert(\"준비 중 입니다.\");*/\r\n\r\n\tif(ss_action != \"go\"){\r\n        alert(\'먼저 조회해 주십시오\');\r\n        return;\r\n  }\r\n\r\n    document.form1.action=\'/servlet/controller.course.GrseqServlet\';\r\n    document.form1.p_process.value = \'listPage\';\r\n    document.form1.p_action.value = ss_action;\r\n    document.form1.isExcel.value = \"ok\";\r\n    document.form1.submit();\r\n\r\n}\r\n\r\n// 미입과학습자 삭제\r\nfunction delGrseq() {\r\n  if (chkSelected() < 1) {\r\n    alert(\'미입과 과정을 선택하세요\');\r\n    return;\r\n  }\r\n  if(!confirm(\"모든 학습데이터까지 삭제되어 복원할 수 없습니다.\\n\\n삭제하시겠습니까?\")) return;\r\n\r\n  document.form1.p_mgyear.value=document.form1.s_gyear.value;\r\n  document.form1.p_mgrseq.value=document.form1.s_grseq.value;\r\n  document.form1.p_action.value = \"go\";\r\n  document.form1.p_process.value= \"delSubjArr\";\r\n  document.form1.submit();\r\n}\r\n\r\n// 조건체크\r\nfunction chkSelected() {\r\n  var selectedcnt = 0;\r\n  var form = document.form1;\r\n  if(form.all[\'p_checks\'] == \'[object]\') {\r\n    if (form.p_checks.length > 0) {\r\n      for (i=0; i<form.p_checks.length; i++) {\r\n        if (form.p_checks[i].checked == true) {\r\n          selectedcnt++;\r\n        }\r\n      }\r\n    } else {\r\n      if (form.p_checks.checked == true) {\r\n        selectedcnt++;\r\n      }\r\n    }\r\n  }\r\n\r\n  return selectedcnt;\r\n}\r\n</script>\r\n</head>\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\" action=\"/servlet/controller.course.GrseqServlet\">\r\n        <input type=\"hidden\" name=\"p_process\" value=\"listPage\">\r\n        <input type=\"hidden\" name=\"p_action\"  value=\"\">\r\n        <input type=\"hidden\" name=\"p_grcode\" value=\"";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\">\r\n        <input type=\"hidden\" name=\"p_mgyear\" value=\"\">\r\n        <input type=\"hidden\" name=\"p_mgrseq\" value=\"\">\r\n        <input type=\"hidden\" name=\"p_msubjcourse\" value=\"\">\r\n        <input type=\"hidden\" name=\"p_myear\" value=\"\">\r\n        <input type=\"hidden\" name=\"p_mseq\" value=\"\">\r\n\r\n        <input type=\"hidden\" name=\"p_subj\" value=\"\">\r\n        <input type=\"hidden\" name=\"p_subjnm\" value=\"\">\r\n        \r\n        <input type=\"hidden\" name=\"isExcel\" value=\"\">\r\n\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n  <tr>\r\n    <td align=\"center\" valign=\"top\">\r\n\r\n      <!----------------- title 시작 ----------------->\r\n      <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n        <tr>\r\n          <td><img src=\"/images/admin/course/c_title02.gif\" alt=\"asdf\"></td>\r\n          <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n        </tr>\r\n      </table>\r\n        <!----------------- title 끝 ----------------->\r\n        <br>\r\n        <!----------------- form 시작 ----------------->\r\n      <table cellspacing=\"0\" cellpadding=\"1\" class=\"form_table_out\">\r\n        <tr>\r\n          <td bgcolor=\"#C6C6C6\" align=\"center\">\r\n\r\n            <table cellspacing=\"0\" cellpadding=\"0\" class=\"form_table_bg\" >\r\n              <tr>\r\n                <td height=\"7\"></td>\r\n              </tr>\r\n              <tr>\r\n                <td align=\"center\">\r\n\r\n                  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"99%\" class=\"form_table\">\r\n                    <tr>\r\n                      <td width=\"27%\" colspan=\"2\" align=\"left\" valign=\"middle\">\r\n                      \t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"99%\" class=\"_srchT\">\r\n                        <!------------------- 조건검색 시작 ------------------------->\r\n\t\t\t\t\t\t<script type=\"text/javascript\">\r\n\t\t\t\t\t\t\tvar startup = 0;\r\n\t\t\t\t\t\t\tfunction Main_s_subjseq(){\r\n\t\t\t\t\t\t\t\tif (startup > 1)\r\n\t\t\t\t\t\t\t\t\tchanges_grseq(document.all.s_grcode.value, document.all.s_gyear.value);\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\tfunction Main_subjcourse(){\r\n\t\t\t\t\t\t\t\tif(startup > 1)\r\n\t\t\t\t\t\t\t\t\tchanges_subjcourse(document.all.s_grcode.value, document.all.s_upperclass.value, document.all.s_middleclass.value, document.all.s_lowerclass.value, document.all.s_grseq.value,document.all.s_subjsearchkey.value );\r\n\t\t\t\t\t\t\t\tstartup++;\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t</script>\r\n\t\t\t\t\t<tr>\r\n                        <td width=\"8%\" class=\"_tdT\"><font color=\"red\">★</font>교육그룹</td>\r\n\t\t\t\t\t\t<td width=\"17%\" class=\"_tdS\">";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\r\n\t\t\t\t\t\t</td>\r\n                      \t<td width=\"5%\" class=\"_tdT\">연도</td>\r\n                      \t<td width=\"13%\" class=\"_tdS\">";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td width=\"8%\" class=\"_tdT\">교육차수</td>\r\n\t\t\t\t\t\t<td width=\"24%\" class=\"_tdS\">";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td width=\"8%\" class=\"_tdT\">대분류</td>\r\n\t\t\t\t\t\t<td width=\"17%\" class=\"_tdS\">";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td class=\"_tdT\">중분류</td>\r\n\t\t\t\t\t\t<td class=\"_tdS\">";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t\t<td class=\"_tdT\">소분류</td>\r\n\t\t\t\t\t\t<td class=\"_tdS\">";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="\r\n\t\t\t\t\t\t</td>\r\n                    </tr>\r\n                    <tr>\r\n                    \t<td width=\"8%\" class=\"_tdT\">과정검색</td>\r\n                        <td colspan=\"2\" class=\"_tdS\">\r\n                        \t<input type=\"text\" name=\"s_subjsearchkey\" size=\"17\" onkeypress=\"searchSubjnmKeyEvent(event)\" value=\"";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\">\r\n\t\t\t\t\t\t\t<a href=\"javascript:goSearchSubjnm()\" ><img src=\"/images/admin/button/search3_butt.gif\" border=\"0\" align=\"absmiddle\"></a>\r\n                      \t</td>\r\n                      \t<input type=\"hidden\" name=\"p_returnurlkey\" value=\"";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="\">\r\n\t\t\t\t\t\t<script>\r\n\t\t\t\t\t\t\tdocument.form1.s_subjsearchkey.focus();\r\n\t\t\t\t\t\t\tfunction searchSubjnmKeyEvent(e){\r\n\t\t\t\t\t\t\t\tif (e.keyCode ==\'13\'){  goSearchSubjnm();  }\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\tfunction goSearchSubjnm(){\r\n\t\t\t\t\t\t\t\tif (document.form1.s_grcode.value == \'ALL\' || document.form1.s_grcode.value == \'----\') {\r\n\t\t\t\t\t\t\t\t\talert(\"교육그룹을 선택하세요.\");\r\n\t\t\t\t\t\t\t\t\treturn;\r\n\t\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t\t\tdocument.form1.action=\'/servlet/controller.study.SearchSubjNameServlet\';\r\n\t\t\t\t\t\t\t\tdocument.form1.p_process.value = \'SearchSubjnmKey\';\r\n\t\t\t\t\t\t\t\tdocument.form1.submit();\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t</script>\r\n                      \t<td width=\"8%\" class=\"_tdT\">과정</td>\r\n                      \t<td colspan=\"4\" class=\"_tdS\">";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t\r\n\t\t\t\t\t<tr>\r\n                        <td width=\"8%\" class=\"_tdT\">교육구분</td>\r\n\t\t\t\t\t\t<td width=\"17%\" class=\"_tdS\">\r\n\t\t\t\t\t\t\t<select name=\"p_area\">\r\n\t\t\t\t\t\t\t  <option value=\"\">-전체-</option>\r\n\t\t\t\t\t\t\t  <option value=\"G0\" ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="selected";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 =" >게임</option>\r\n\t\t\t\t\t\t\t  <option value=\"K0\" ";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 =" >방송</option>\r\n\t\t\t\t\t\t\t  <option value=\"B0\" ";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 =" >문화</option>\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</td>\r\n                      \t<td width=\"5%\" class=\"_tdT\">유/무료</td>\r\n                      \t<td width=\"13%\" class=\"_tdS\">\r\n\t\t\t\t\t\t\t<select name=\"pp_biyong\">\r\n\t\t\t\t\t\t\t   <option value=\"\">-전체-</option>\r\n\t\t\t\t\t\t\t   <option value=\"Y\"  ";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 =" >유료</option>\r\n\t\t\t\t\t\t\t   <option value=\"N\"  ";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 =" >무료</option>\r\n\t\t\t\t\t\t\t</select>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\t\r\n\t\t\t\t\t\r\n   ";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

    private final static String  _wl_block26 ="\r\n                        <!-------------------- 조건검색 끝 ---------------------------->\r\n                    <tr>\r\n                    \t<td colspan=\"8\" align=\"right\">";
    private final static byte[]  _wl_block26Bytes = _getBytes( _wl_block26 );

    private final static String  _wl_block27 ="        <a href = \"javascript:whenSelection(\'go\')\"><img src = \"/images/admin/button/b_go.gif\" border = \"0\" align=\"absmiddle\"></a>";
    private final static byte[]  _wl_block27Bytes = _getBytes( _wl_block27 );

    private final static String  _wl_block28 ="</td><!-- whenSelection(\'go\') -->\r\n                    </tR>\r\n                  </table> \r\n                  </td>\r\n                    </tr>\r\n                  </table>\r\n                </td>\r\n              </tr>\r\n              <tr>\r\n                <td height=\"9\"></td>\r\n              </tr>\r\n            </table>\r\n          </td>\r\n        </tr>\r\n      </table>\r\n      <!----------------- form 끝 ----------------->\r\n\r\n        <br>\r\n    ";
    private final static byte[]  _wl_block28Bytes = _getBytes( _wl_block28 );

    private final static String  _wl_block29 ="\r\n        <!----------------- 교육차수추가 버튼 시작 ----------------->\r\n        <table width=\"970\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n          <tr>\r\n            <td align=\"right\" height=\"20\">\r\n\t\t\t\t<a href=\"javascript:delGrseq()\"><img src=\"/images/admin/button/mdel_btn.gif\"  border=0 alt=\"미입과 삭제\"></a>\r\n\t\t\t\t&nbsp;\r\n                <a href=\"javascript:addGrseq()\"><img src=\"/images/admin/button/btn_eduplus.gif\"  border=0 alt=\"교육차수추가\"></a>\r\n                &nbsp;\r\n                <a href=\"javascript:whenExcel(\'go\')\"><img src=\"/images/admin/button/btn_excelprint.gif\" border=\"0\" alt=\"엑셀다운\"></a></td>\r\n            </td>\r\n          </tr>\r\n          <tr>\r\n            <td height=\"3\"></td>\r\n          </tr>\r\n        </table>\r\n        <!----------------- 교육차수추가 버튼 끝 ----------------->\r\n    ";
    private final static byte[]  _wl_block29Bytes = _getBytes( _wl_block29 );

    private final static String  _wl_block30 ="\r\n      <!----------------- 교육차수 관리 시작 ----------------->\r\n      <table width=\"970\" class=\"table_out\" cellspacing=\"1\" cellpadding=\"5\">\r\n        <tr>\r\n          <td colspan=\"13\" class=\"table_top_line\"></td>\r\n        </tr>\r\n        <tr height=\"25\">\r\n          <td width=\"9%\"  class=\"table_title\"><b>교육차수</b></td>\r\n          <td width=\"23%\" class=\"table_title\" colspan=2><b>과정</b></td>\r\n          <!--td width=\"5%\"  class=\"table_title\"><b>구분</b></td-->\r\n          <td width=\"9%\"  class=\"table_title\"><b>교육그룹내<br/>과정차수</b></td>\r\n          <td width=\"10%\" class=\"table_title\"><b>신청기간</b></td>\r\n          <td width=\"10%\" class=\"table_title\"><b>교육기간</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>정원</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>신청인원</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>승인인원</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>수료인원</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>수강료</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>자동승인여부</b></td>\r\n          <td width=\"4%\"  class=\"table_title\"><b>복습기간</b></td>\r\n          <td width=\"5%\"  class=\"table_title\">\r\n<script language=\'javascript\'>\r\nfunction chkeckall(){\r\n    if(document.form1.p_chkeckall.checked){\r\n      whenAllSelect();\r\n    }\r\n    else{\r\n      whenAllSelectCancel();\r\n    }\r\n}\r\n</script>\r\n<input type=\"checkbox\" name=\"p_chkeckall\" onClick=\"javascript:chkeckall()\">\r\n          </td>\r\n          <td width=\"13%\" class=\"table_title\"><b>기능</b></td>\r\n        </tr>\r\n";
    private final static byte[]  _wl_block30Bytes = _getBytes( _wl_block30 );

    private final static String  _wl_block31 ="\r\n        <tr height=25>\r\n";
    private final static byte[]  _wl_block31Bytes = _getBytes( _wl_block31 );

    private final static String  _wl_block32 ="\r\n                <td align=\"center\" bgcolor=\"#EEEEEE\" rowspan=\"";
    private final static byte[]  _wl_block32Bytes = _getBytes( _wl_block32 );

    private final static String  _wl_block33 ="\">\r\n                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n                    <tr><td align=\"center\"><b>";
    private final static byte[]  _wl_block33Bytes = _getBytes( _wl_block33 );

    private final static String  _wl_block34 ="-";
    private final static byte[]  _wl_block34Bytes = _getBytes( _wl_block34 );

    private final static String  _wl_block35 ="</b>\r\n                        <br><a href=\"javascript:whenGrseq(\'";
    private final static byte[]  _wl_block35Bytes = _getBytes( _wl_block35 );

    private final static String  _wl_block36 ="\',\'";
    private final static byte[]  _wl_block36Bytes = _getBytes( _wl_block36 );

    private final static String  _wl_block37 ="\')\">";
    private final static byte[]  _wl_block37Bytes = _getBytes( _wl_block37 );

    private final static String  _wl_block38 ="</a></td>\r\n                    </tr><tr><td height=\"5\"></td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td align=\"center\">\r\n                        <a href=\"javascript:assignSubjCourse(\'";
    private final static byte[]  _wl_block38Bytes = _getBytes( _wl_block38 );

    private final static String  _wl_block39 ="\')\"><img src=\"/images/admin/course/appointment1_butt.gif\" width=\"58\" height=\"18\" border=0></a>\r\n                      </td>\r\n                    </tr>\r\n                    ";
    private final static byte[]  _wl_block39Bytes = _getBytes( _wl_block39 );

    private final static String  _wl_block40 ="\r\n                    <tr>\r\n                      <td align=\"center\">\r\n                        <a href=\"javascript:updateSubjCourse(\'";
    private final static byte[]  _wl_block40Bytes = _getBytes( _wl_block40 );

    private final static String  _wl_block41 ="\')\"><img src=\"/images/admin/course/modify1_butt.gif\" border=0></a>\r\n                      </td>\r\n                    </tr>\r\n                    <tr>\r\n                      <td align=\"center\">\r\n                        <a href=\"javascript:updateSubjScore(\'";
    private final static byte[]  _wl_block41Bytes = _getBytes( _wl_block41 );

    private final static String  _wl_block42 ="\')\"><img src=\"/images/admin/course/editscore_butt.gif\" border=0></a>\r\n                      </td>\r\n                    </tr>\r\n                    ";
    private final static byte[]  _wl_block42Bytes = _getBytes( _wl_block42 );

    private final static String  _wl_block43 ="\r\n                  </table>\r\n                </td>\r\n";
    private final static byte[]  _wl_block43Bytes = _getBytes( _wl_block43 );

    private final static String  _wl_block44 ="\r\n                    <td  class=\"table_02_1\" colspan=\"12\" align=\"center\">등록된 과정이 없습니다.</td>\r\n";
    private final static byte[]  _wl_block44Bytes = _getBytes( _wl_block44 );

    private final static String  _wl_block45 ="\r\n                    <!--<td class=\"table_02_2\" align=\"center\">-</td>-->\r\n                    <td class=\"table_02_1\" align=\"center\" rowspan=\"  ";
    private final static byte[]  _wl_block45Bytes = _getBytes( _wl_block45 );

    private final static String  _wl_block46 =" \"><font class=\"text_color03\">[전문가] ";
    private final static byte[]  _wl_block46Bytes = _getBytes( _wl_block46 );

    private final static String  _wl_block47 ="<br>\r\n                      <a href=\"javascript:whenCourseseq(\'";
    private final static byte[]  _wl_block47Bytes = _getBytes( _wl_block47 );

    private final static String  _wl_block48 ="\')\">\r\n                        ";
    private final static byte[]  _wl_block48Bytes = _getBytes( _wl_block48 );

    private final static String  _wl_block49 ="</a></font>\r\n                    </td>\r\n                    <td class=\"table_02_2\"><font class=\"text_color04\">";
    private final static byte[]  _wl_block49Bytes = _getBytes( _wl_block49 );

    private final static String  _wl_block50 ="</font></td>\r\n ";
    private final static byte[]  _wl_block50Bytes = _getBytes( _wl_block50 );

    private final static String  _wl_block51 ="\r\n                    <!--<td class=\"table_02_2\" align=\"center\">-</td>-->\r\n                    <td class=\"table_02_2\" colspan=\"2\"><font class=\"text_color04\">";
    private final static byte[]  _wl_block51Bytes = _getBytes( _wl_block51 );

    private final static String  _wl_block52 ="</font></td>\r\n";
    private final static byte[]  _wl_block52Bytes = _getBytes( _wl_block52 );

    private final static String  _wl_block53 ="\r\n\r\n          <!--td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block53Bytes = _getBytes( _wl_block53 );

    private final static String  _wl_block54 ="</td-->\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block54Bytes = _getBytes( _wl_block54 );

    private final static String  _wl_block55 ="</td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block55Bytes = _getBytes( _wl_block55 );

    private final static String  _wl_block56 ="</td>\r\n\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block56Bytes = _getBytes( _wl_block56 );

    private final static String  _wl_block57 ="<!--d_proposecnt--></td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block57Bytes = _getBytes( _wl_block57 );

    private final static String  _wl_block58 ="<!--d_studentcnt--></td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block58Bytes = _getBytes( _wl_block58 );

    private final static String  _wl_block59 ="<!--d_stoldcnt--></td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block59Bytes = _getBytes( _wl_block59 );

    private final static String  _wl_block60 ="<!--d_biyong--></td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block60Bytes = _getBytes( _wl_block60 );

    private final static String  _wl_block61 ="<!--d_autoconfirm--></td>\r\n          <td class=\"table_02_1\" align=\"center\">";
    private final static byte[]  _wl_block61Bytes = _getBytes( _wl_block61 );

    private final static String  _wl_block62 ="<!--d_reviewdays--></td>\r\n          <td class=\"table_02_1\" align=\"center\">\r\n          ";
    private final static byte[]  _wl_block62Bytes = _getBytes( _wl_block62 );

    private final static String  _wl_block63 ="\r\n          <input type=\"checkbox\" name=\"p_checks\" value=\"";
    private final static byte[]  _wl_block63Bytes = _getBytes( _wl_block63 );

    private final static String  _wl_block64 =",";
    private final static byte[]  _wl_block64Bytes = _getBytes( _wl_block64 );

    private final static String  _wl_block65 ="\" ";
    private final static byte[]  _wl_block65Bytes = _getBytes( _wl_block65 );

    private final static String  _wl_block66 =">\r\n          ";
    private final static byte[]  _wl_block66Bytes = _getBytes( _wl_block66 );

    private final static String  _wl_block67 ="\r\n          </td>\r\n ";
    private final static byte[]  _wl_block67Bytes = _getBytes( _wl_block67 );

    private final static String  _wl_block68 ="\r\n          <td class=\"table_02_1\" align=\"center\">\r\n            <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n              <tr>\r\n                <td align=\"center\"><a href=\"javascript:delSubj(\'";
    private final static byte[]  _wl_block68Bytes = _getBytes( _wl_block68 );

    private final static String  _wl_block69 ="\')\">\r\n                  <img src=\"/images/admin/button/btn_del.gif\"  border=\"0\"></a>\r\n                </td>\r\n                <td align=\"center\"><a href=\"javascript:addSubj(\'";
    private final static byte[]  _wl_block69Bytes = _getBytes( _wl_block69 );

    private final static String  _wl_block70 ="\')\">\r\n                  <img src=\"/images/admin/button/btn_add.gif\"  border=\"0\"></a>\r\n                </td>\r\n              </tr>\r\n            </table>\r\n          </td>\r\n";
    private final static byte[]  _wl_block70Bytes = _getBytes( _wl_block70 );

    private final static String  _wl_block71 ="\r\n          <td class=\"table_02_1\" align=\"center\" rowspan=\"";
    private final static byte[]  _wl_block71Bytes = _getBytes( _wl_block71 );

    private final static String  _wl_block72 ="\">\r\n            <table width=\"96%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n              <tr>\r\n                <td align=\"center\"><a href=\"javascript:delCourse(\'";
    private final static byte[]  _wl_block72Bytes = _getBytes( _wl_block72 );

    private final static String  _wl_block73 ="\')\">\r\n                  <img src=\"/images/admin/button/btn_del.gif\"  border=\"0\"></a>\r\n                </td>\r\n                <td align=\"center\"><a href=\"javascript:addCourse(\'";
    private final static byte[]  _wl_block73Bytes = _getBytes( _wl_block73 );

    private final static String  _wl_block74 ="\r\n          <td class=\"table_02_1\">&nbsp;</td>\r\n";
    private final static byte[]  _wl_block74Bytes = _getBytes( _wl_block74 );

    private final static String  _wl_block75 ="\r\n        </tr>\r\n";
    private final static byte[]  _wl_block75Bytes = _getBytes( _wl_block75 );

    private final static String  _wl_block76 ="\r\n        <tr><td colspan=\"13\" class=\"table_02_1\">등록된 교육차수가 없습니다.</td></tr>\r\n";
    private final static byte[]  _wl_block76Bytes = _getBytes( _wl_block76 );

    private final static String  _wl_block77 ="\r\n     </table>\r\n     <!----------------- 교육차수 관리 끝 ----------------->\r\n     <br>\r\n     </td>\r\n  </tr>\r\n</table>\r\n</form>\r\n";
    private final static byte[]  _wl_block77Bytes = _getBytes( _wl_block77 );

    private final static String  _wl_block78 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block78Bytes = _getBytes( _wl_block78 );

    private final static String  _wl_block79 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block79Bytes = _getBytes( _wl_block79 );

    private final static String  _wl_block80 ="\r\n    ";
    private final static byte[]  _wl_block80Bytes = _getBytes( _wl_block80 );

    private final static String  _wl_block81 ="\r\n</body>\r\n</html>\r\n";
    private final static byte[]  _wl_block81Bytes = _getBytes( _wl_block81 );

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
//  1. 제      목: 교육차수 리스트 조회화면
//  2. 프로그램명: za_Grseq_L.jsp
//  3. 개      요: 교육차수 리스트 조회화면
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

    //DEFINED in relation to select START
    String  ss_grcode    = box.getString("s_grcode");               //교육그룹
    String  ss_gyear     = box.getString("s_gyear");                //년도
    String  ss_upperclass = box.getString("s_upperclass");          //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");        //과정중분류
    String  ss_lowerclass = box.getString("s_lowerclass");          //과정소분류
    String  ss_subjcourse= box.getString("s_subjcourse");           //과정&코스
    String  s_grseq   = box.getString("s_grseq");                   //과정 차수
    String  s_subjsearchkey   = box.getString("s_subjsearchkey");   //과정명
//    String  s_biyong  = box.getString("s_biyong");                  //교육구분
//    String  s_area   = box.getString("s_area");                     //유/무료
//    System.out.println("s_biyong"+s_biyong+" /s_area"+s_area);

	String v_area = box.getString("v_area");
	String v_biyong = box.getString("v_biyong");

	//DEFINED in relation to select END

    String  v_gyear ="", v_grseq="", v_course="", v_cyear="", v_courseseq="", v_courseStr="";
    String  v_subj="",v_year="",v_subjseq="";
    String  v_subjnmlink = "";
    String  v_precourse = "";
    String s_gadmin = box.getSession("gadmin");
    String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    
    String isExcel = "";


            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
  if (ss_grcode.equals("") || ss_grcode.equals("ALL")) {            
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
  } else {                             
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
  }                                    
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
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
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
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
            __tag1.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_subjcourse", java.lang.String .class));
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
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
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
            __tag2.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" style=\'width:95%;\'", java.lang.String .class));
            __tag2.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("", java.lang.String .class));
            __tag2.setAfterScript(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("Main_subjcourse", java.lang.String .class));
            __tag2.setSelectedValue( s_grseq 
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
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag3 = null ;
            int __result__tag3 = 0 ;

            if (__tag3== null )__tag3 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag3.setPageContext(pageContext);
            __tag3.setParent(null);
            __tag3.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_upperclass", java.lang.String .class));
            __tag3.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0002", java.lang.String .class));
            __tag3.setParam(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag3.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_middleclass(this.value);Main_subjcourse();", java.lang.String .class));
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
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
             com.dunet.common.taglib.KoccaSelectTaglib __tag4 = null ;
            int __result__tag4 = 0 ;

            if (__tag4== null )__tag4 = new  com.dunet.common.taglib.KoccaSelectTaglib ();
            __tag4.setPageContext(pageContext);
            __tag4.setParent(null);
            __tag4.setName(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("s_middleclass", java.lang.String .class));
            __tag4.setSqlNum(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("course.0003", java.lang.String .class));
            __tag4.setParam( ss_upperclass 
);
            __tag4.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("changes_lowerclass(s_upperclass.value, this.value);Main_subjcourse();", java.lang.String .class));
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
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
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
            __tag5.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("whenSelection(\'go\')", java.lang.String .class));
            __tag5.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
            __tag5.setSelectedValue(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("0000", java.lang.String .class));
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
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(box.getString("s_subjsearchkey")
));
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(request.getRequestURI()
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
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
            __tag6.setParam7(s_grseq
);
            __tag6.setParam9(s_subjsearchkey
);
            __tag6.setOnChange(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType("whenSelection(\'go\')", java.lang.String .class));
            __tag6.setAttr(( java.lang.String ) javelin.jsp.utils.JspRuntimeUtils.convertType(" ", java.lang.String .class));
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
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
 if(v_area.equals("G0")){ 
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
 } 
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}
 if(v_area.equals("B0")){ 
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
 } 
            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}
 if(v_area.equals("K0")){ 
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
 } 
            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
 if(v_biyong.equals("Y")){ 
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
 } 
            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}
 if(v_biyong.equals("N")){ 
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
 } 
            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}
/*                      <!--%= SelectEduBean.getGyear(box, true)%--><!-- getGyear(RequestBox, isChange)   교육년도  -->
   <!--  SelectEduBean.getGrcode(box, true, false)%--><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
						< %= SelectEduBean.getGrseq(box, true, true)% ><!-- getGrseq(RequestBox, isChange, isALL)   교육차수  -->
						<!--input type= "hidden" name="s_grseq" value="0001"-->
						< %= SelectSubjBean.getUpperClass(box, true, true, true)% ><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  -->
						< %= SelectSubjBean.getMiddleClass(box, true, true, true)% ><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  -->
						< %= SelectSubjBean.getLowerClass(box, true, true, true)% ><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
                        <!-- %= SelectSubjBean.getSubj(box, true, true)%--><!-- getSubj(RequestBox, isChange, isALL)    과정  -->*/
            {_writeText(response, out, _wl_block26, _wl_block26Bytes);}
            {_writeText(response, out, _wl_block27, _wl_block27Bytes);}
            {_writeText(response, out, _wl_block28, _wl_block28Bytes);}
 if (!ss_grcode.equals("")){
            {_writeText(response, out, _wl_block29, _wl_block29Bytes);}
 } 
            {_writeText(response, out, _wl_block30, _wl_block30Bytes);}

        if (box.getString("p_action").equals("go")) {
            ArrayList  list = (ArrayList)request.getAttribute("GrseqList");

            for (int i=0; i<list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);
// 리스트 깨찜 수정

				int subjcnt = dbox.getInt("d_subjcnt");

				if( subjcnt >= 4){
					subjcnt = 3;
				}

                v_subjnmlink = "<a href=\"javascript:whenSubjseq('"+ ss_grcode +"','"+ dbox.getString("d_gyear") +"','"+ dbox.getString("d_grseq") +"','"+ dbox.getString("d_subj") +"','"+ dbox.getString("d_year") +"','"+ dbox.getString("d_subjseq") +"','"+ dbox.getString("d_isonoff") +"')\" class='b'>"+ dbox.getString("d_subjnm") +"</a>";

            {_writeText(response, out, _wl_block31, _wl_block31Bytes);}

                if (!v_grseq.equals(dbox.getString("d_grseq")) || !v_gyear.equals(dbox.getString("d_gyear")) ){
                    v_grseq     = dbox.getString("d_grseq");
                    v_gyear     = dbox.getString("d_gyear");


            {_writeText(response, out, _wl_block32, _wl_block32Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getInt("d_rowspan_grseq")
));
            {_writeText(response, out, _wl_block33, _wl_block33Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block34, _wl_block34Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block35, _wl_block35Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block37, _wl_block37Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grseqnm")
));
            {_writeText(response, out, _wl_block38, _wl_block38Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block39, _wl_block39Bytes);}
 if(!dbox.getString("d_subj").equals("")) { 
            {_writeText(response, out, _wl_block40, _wl_block40Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block41, _wl_block41Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block42, _wl_block42Bytes);}
 } 
            {_writeText(response, out, _wl_block43, _wl_block43Bytes);}

            }

            //if (dbox.getString("d_subj") == null || (dbox.getString("d_subj").equals(""))){
            if (dbox.getString("d_grseqnm") == null || (dbox.getString("d_grseqnm").equals(""))){

            {_writeText(response, out, _wl_block44, _wl_block44Bytes);}

            } else {
                v_courseStr = dbox.getString("d_course") + dbox.getString("d_cyear") + dbox.getString("d_courseseq");
                if (!dbox.getString("d_course").equals("") && !dbox.getString("d_course").equals("000000") && !v_courseStr.equals(v_course+v_cyear+v_courseseq)){
                    v_course    = dbox.getString("d_course");
                    v_cyear     = dbox.getString("d_cyear");
                    v_courseseq = dbox.getString("d_courseseq");

            {_writeText(response, out, _wl_block45, _wl_block45Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(subjcnt 
));
            {_writeText(response, out, _wl_block46, _wl_block46Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_courseseq
));
            {_writeText(response, out, _wl_block47, _wl_block47Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_gyear")
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grseq")
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_course
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_cyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_courseseq
));
            {_writeText(response, out, _wl_block48, _wl_block48Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_coursenm")
));
            {_writeText(response, out, _wl_block49, _wl_block49Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjnmlink
));
            {_writeText(response, out, _wl_block50, _wl_block50Bytes);}

                }else if (dbox.getString("d_course").equals("000000")){
                    v_course    = dbox.getString("d_course");
                    v_cyear     = dbox.getString("d_cyear");
                    v_courseseq = dbox.getString("d_courseseq");

            {_writeText(response, out, _wl_block51, _wl_block51Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjnmlink
));
            {_writeText(response, out, _wl_block52, _wl_block52Bytes);}

                } else {

            {_writeText(response, out, _wl_block51, _wl_block51Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjnmlink
));
            {_writeText(response, out, _wl_block52, _wl_block52Bytes);}

                }

            {_writeText(response, out, _wl_block53, _wl_block53Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_isonoffnm")
));
            {_writeText(response, out, _wl_block54, _wl_block54Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? StringManager.cutZero(dbox.getString("d_subjseqgr")) : "&nbsp;"
));
            {_writeText(response, out, _wl_block55, _wl_block55Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? FormatDate.getFormatDate(dbox.getString("d_propstart"), "yyyy/MM/dd") + " ~ " + FormatDate.getFormatDate(dbox.getString("d_propend"), "yyyy/MM/dd") : "&nbsp;"
));
            {_writeText(response, out, _wl_block55, _wl_block55Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? FormatDate.getFormatDate(dbox.getString("d_edustart"),"yyyy/MM/dd")   + " ~ " + FormatDate.getFormatDate(dbox.getString("d_eduend")  ,"yyyy/MM/dd") : "&nbsp;"
));
            {_writeText(response, out, _wl_block56, _wl_block56Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_studentlimit") : "&nbsp;"
));
            {_writeText(response, out, _wl_block55, _wl_block55Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_propose")  : "&nbsp;"
));
            {_writeText(response, out, _wl_block57, _wl_block57Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_student")  : "&nbsp;"
));
            {_writeText(response, out, _wl_block58, _wl_block58Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_cnt_stold")    : "&nbsp;"
));
            {_writeText(response, out, _wl_block59, _wl_block59Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_biyong")       : "&nbsp;"
));
            {_writeText(response, out, _wl_block60, _wl_block60Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getString("d_autoconfirm")  : "&nbsp;"
));
            {_writeText(response, out, _wl_block61, _wl_block61Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(!dbox.getString("d_subj").equals("") ? dbox.getInt("d_reviewdays")  : "&nbsp;"
));
            {_writeText(response, out, _wl_block62, _wl_block62Bytes);}

          	int i4Cnt = dbox.getInt("d_studentlimit") + dbox.getInt("d_cnt_propose") + dbox.getInt("d_cnt_student") + dbox.getInt("d_cnt_stold");
            if(!dbox.getString("d_subj").equals("")) { 
          
            {_writeText(response, out, _wl_block63, _wl_block63Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_gyear")
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_grseq")
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_subj")
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_year")
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_subjseq")
));
            {_writeText(response, out, _wl_block64, _wl_block64Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_isonoff")
));
            {_writeText(response, out, _wl_block65, _wl_block65Bytes);}
 if(i4Cnt == 0) { out.print("checked"); } 
            {_writeText(response, out, _wl_block66, _wl_block66Bytes);}
 } 
            {_writeText(response, out, _wl_block67, _wl_block67Bytes);}

                if (!dbox.getString("d_subj").equals("") && v_course.equals("000000")){
                    v_subj      = dbox.getString("d_subj");
                    v_year      = dbox.getString("d_year");
                    v_subjseq   = dbox.getString("d_subjseq");

            {_writeText(response, out, _wl_block68, _wl_block68Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subj
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_year
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjseq
));
            {_writeText(response, out, _wl_block69, _wl_block69Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_subjnm")
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subj
));
            {_writeText(response, out, _wl_block70, _wl_block70Bytes);}

                }else if (!dbox.getString("d_subj").equals("") && !dbox.getString("d_course").equals("000000") && !v_courseStr.equals(v_precourse)){
                    v_precourse = v_courseStr;

            {_writeText(response, out, _wl_block71, _wl_block71Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(subjcnt
));
            {_writeText(response, out, _wl_block72, _wl_block72Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_course
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_cyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_courseseq
));
            {_writeText(response, out, _wl_block73, _wl_block73Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(dbox.getString("d_coursenm")
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_gyear
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grseq
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_course
));
            {_writeText(response, out, _wl_block70, _wl_block70Bytes);}

                } else {

            {_writeText(response, out, _wl_block74, _wl_block74Bytes);}

                } 

            {_writeText(response, out, _wl_block75, _wl_block75Bytes);}


            }    // end if
         }       // end for

         if (list.size()==0) {

            {_writeText(response, out, _wl_block76, _wl_block76Bytes);}

         }
   }  // end if : if (box.getString("p_action").equals("go")) {

            {_writeText(response, out, _wl_block77, _wl_block77Bytes);}

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
            {_writeText(response, out, _wl_block78, _wl_block78Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block79, _wl_block79Bytes);}
  }   
            {_writeText(response, out, _wl_block80, _wl_block80Bytes);}
            {_writeText(response, out, _wl_block81, _wl_block81Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
