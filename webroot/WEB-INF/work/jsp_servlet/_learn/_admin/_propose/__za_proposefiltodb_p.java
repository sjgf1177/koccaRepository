package jsp_servlet._learn._admin._propose;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;
import jxl.*;
import com.credu.homepage.*;
import com.credu.library.*;
import com.credu.propose.*;
import com.credu.complete.FinishBean;

public final class __za_proposefiltodb_p extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/propose/za_ProposeFilToDB_P.jsp", 1422235972047L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<html>\r\n<head>\r\n<title>입과FileToDB</title>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n<link rel=\"stylesheet\" type=\"text/CSS\" href=\"/css/admin_style.css\">\r\n<script language = \"javascript\" src = \"/script/cresys_lib.js\"></script>\r\n<SCRIPT LANGUAGE=\"JavaScript\">\r\n<!--\r\nfunction golistPage() {\r\n    document.form1.action = \"/servlet/controller.propose.AcceptServlet\";\r\n    document.form1.p_process.value = \"proposeFileToDB\";\r\n    document.form1.submit();\r\n}\r\n//-->\r\n</SCRIPT>\r\n</head>\r\n<body bgcolor=\"#FFFFFF\" text=\"#000000\" topmargin=\"0\" leftmargin=\"0\">\r\n<form name=\"form1\" method=\"post\" >\r\n    <input type=\"hidden\" name=\"p_process\" value=\"\">\r\n    <input type=\"hidden\" name=\"s_grcode\" value=\"";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\">\r\n    <input type=\"hidden\" name=\"s_gyear\" value=\"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\">\r\n    <input type=\"hidden\" name=\"s_grseq\" value=\"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\">\r\n    <input type=\"hidden\" name=\"s_upperclass\" value=\"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\">\r\n    <input type=\"hidden\" name=\"s_middleclass\" value=\"";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="\">\r\n    <input type=\"hidden\" name=\"s_subjcourse\" value=\"";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\">\r\n    <input type=\"hidden\" name=\"p_isretire\" value=\"";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\">\r\n    <input type=\"hidden\" name=\"p_isemtpty\" value=\"";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\">\r\n    <input type=\"hidden\" name=\"p_isstoped\" value=\"";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\">\r\n    <input type=\"hidden\" name=\"p_stuoverlap\" value=\"";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="\">\r\n    <input type=\"hidden\" name=\"p_isgraduated\" value=\"";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\">\r\n</form>\r\n<table width=\"1000\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"663\">\r\n    <tr>\r\n        <td align=\"center\" valign=\"top\">\r\n            <!-- title 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=page_title>\r\n                <tr>\r\n                    <td><img src=\"/images/admin/propose/p_title06.gif\" ></td>\r\n                    <td align=\"right\"><img src=\"/images/admin/common/sub_title_tail.gif\" ></td>\r\n                </tr>\r\n            </table>\r\n            <!-- title 끝 //-->\r\n            <br/>\r\n            <br/>\r\n\r\n            <!-- 교육정보시작 //-->\r\n            <table cellspacing=\"0\" cellpadding=\"1\" class=\"form_table_out\">\r\n                <tr>\r\n                    <td bgcolor=\"#C6C6C6\" align=\"center\">\r\n                        <table cellspacing=\"0\" cellpadding=\"0\" class=\"form_table_bg\" >\r\n                            <tr>\r\n                                <td height=\"15\"></td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td align=\"center\" valign=\"middle\">\r\n                                    <table cellspacing=\"0\" cellpadding=\"0\" width=\"99%\" class=\"form_table\">\r\n                                        <tr>\r\n                                            <td>\r\n                                                <b><font size=\"3\">&nbsp;&nbsp;\r\n                                                교육그룹:";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="&nbsp;&nbsp;&nbsp;&nbsp;\r\n                                                년도:";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="&nbsp;&nbsp;&nbsp;&nbsp;\r\n                                                과정:";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="&nbsp;&nbsp;&nbsp;&nbsp;\r\n                                                차수:";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="</font></b>\r\n                                            </td>\r\n                                        </tr>\r\n                                    </table>\r\n                                </td>\r\n                            </tr>\r\n                            <tr>\r\n                                <td height=\"15\"></td>\r\n                            </tr>\r\n                        </table>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n            <br/><br/><br/>\r\n\r\n";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\r\n\r\n            <!-- 결과정보 시작 //-->\r\n            <table>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"red\">ERROR건수가 1건이상 발생하면 일괄처리가 자동취소됩니다.</font>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n\r\n            <table cellspacing=\"1\" cellpadding=\"5\" class=\"table_out\">\r\n                <tr>\r\n                    <td colspan=\"15\" class=\"table_top_line\"></td>\r\n                </tr>\r\n                <tr>\r\n                    <td class=\"table_title\" colspan=\"8\">ERROR : ";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 ="건</td>\r\n                </tr>\r\n                <tr>\r\n                    <td class=\"table_title\" width=\"10%\">줄번호</td>\r\n                    <td class=\"table_title\" width=\"20%\">사번</td>\r\n                    <td class=\"table_title\" width=\"15%\">입력차수</td>\r\n                    <td class=\"table_title\" >MESSAGE</td>\r\n                </tr>\r\n";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="\r\n                <tr>\r\n                    <td class=\"table_02_1\" colspan=\"8\">검색된 Error가 없습니다.</td>\r\n                </tr>\r\n";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="\r\n                </tr>\r\n            </table>\r\n            <!-- 결과 정보 출력 끝 //-->\r\n            <br/><br/>\r\n\r\n            <!-- 결과정보 시작 //-->\r\n            <table cellspacing=\"1\" cellpadding=\"5\" class=\"table_out\">\r\n                <tr>\r\n                    <td colspan=\"15\" class=\"table_top_line\"></td>\r\n                </tr>\r\n                <tr>\r\n                    <td class=\"table_title\" colspan=\"8\">정상입력</td>\r\n                </tr>\r\n                <tr>\r\n                    <td class=\"table_title\" width=\"10%\">줄번호</td>\r\n                    <td class=\"table_title\" width=\"20%\">사번</td>\r\n                    <td class=\"table_title\" width=\"15%\">입력차수</td>\r\n                    <td class=\"table_title\" >MESSAGE</td>\r\n                </tr>\r\n                ";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="\r\n            </table>\r\n            <!-- 결과 정보 출력 끝 //-->\r\n\r\n";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n            <br/><br/>\r\n            <!-- 에러메세지 시작 //-->\r\n\r\n            <table>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"blue\"><b>(엑셀양식 Error)<b></font>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"red\">1. 차수 ALL선택후 차수를 입력하지 않은경우 Error처리 됩니다.</font><br/>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"red\">&nbsp;&nbsp;-->차수입력후 다시 시도하여 주십시오.</font><br/>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"red\">2. 엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br/>\r\n                    </td>\r\n                </tr>\r\n                <tr>\r\n                    <td>\r\n                        <font color=\"red\">&nbsp;&nbsp;--> 입력한 영역<b>(사번,차수,과정)</b>만 지정하고 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br/>&nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br/>\r\n                    </td>\r\n                </tr>\r\n            </table>\r\n";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 ="\r\n            <br/>\r\n            <!-- 등록, 미리보기 버튼 시작 //-->\r\n            <table width=\"97%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n                <tr>\r\n                    <td align=\"center\" ><a href=\"javascript:golistPage()\"><img src=\"/images/admin/button/btn_list.gif\" border=\"0\"></a></td>\r\n                </tr>\r\n            </table>\r\n            <!-- 등록, 미리보기 버튼 끝 //-->\r\n            <br/><br/>\r\n        </td>\r\n    </tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 ="\r\n<table>\r\n    <tr><td>\r\n            ";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 =" <br><br>\r\n            \r\n    </td></tr>\r\n</table>\r\n";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

    private final static String  _wl_block26 ="\r\n    ";
    private final static byte[]  _wl_block26Bytes = _getBytes( _wl_block26 );

    private final static String  _wl_block27 ="\r\n</body>\r\n</html>\r\n\r\n";
    private final static byte[]  _wl_block27Bytes = _getBytes( _wl_block27 );

    private int updateCSSubjSeqInfo(DBConnectionManager connMgr, ArrayList<HashMap<String, String>> csSubjList) throws Exception {
        int[] resultCnt = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        HashMap<String, String> csSubjMap = null;

        try {
            sql.setLength(0);
            sql.append("UPDATE  TZ_SUBJSEQ      \n");
            sql.append("   SET  CS_SUBJ = ?     \n");
            sql.append("    ,   CS_SUBJSEQ = ?  \n");
            sql.append("    ,   CS_YEAR = ?     \n");
            sql.append(" WHERE  GRCODE = ?      \n");
            sql.append("    ,   SUBJ = ?        \n");
            sql.append("    ,   SUBJSEQ = ?     \n");
            sql.append("    ,   YEAR = ?        \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for ( int i = 0 ; i < csSubjList.size(); i++ ) {
                csSubjMap = (HashMap)csSubjList.get(i);
                pstmt.setString(1, csSubjMap.get("cssubj"));
                pstmt.setString(2, csSubjMap.get("cssubjseq"));
                pstmt.setString(3, csSubjMap.get("year"));
                pstmt.setString(4, csSubjMap.get("grcode"));
                pstmt.setString(5, csSubjMap.get("subj"));
                pstmt.setString(6, csSubjMap.get("subjseq"));
                pstmt.setString(7, csSubjMap.get("year"));

                pstmt.addBatch();
            }

            resultCnt = pstmt.executeBatch();
        } catch (Exception e) {
        } finally {
            if ( pstmt != null ) {
                pstmt.close();
                pstmt = null;
            }
        }

        return resultCnt.length;
    }


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
//  2. 프로그램명 : za_ProposeFileToDB_P.jsp
//  3. 개      요: 입과 FileToDB 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 이창훈 2004.11.20
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

    DBConnectionManager connMgr = null;

    PreparedStatement propose_pstmt = null;
    PreparedStatement student_pstmt = null;

    String propose_sql = "";
    String student_sql = "";

    ProposeBean proposebean = null;
    Hashtable inputdata  = new Hashtable();
    Hashtable outputdata = null;

    int isOk = 0;
    int isOk1 = 0;

    String v_process = box.getString("p_process");

    String ss_grcode = box.getString("s_grcode");           //교육주관
    String ss_gyear = box.getString("s_gyear");            //년도
    String ss_grseq = box.getString("s_grseq");            //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");          //교육차수명
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass = box.getString("s_middleclass");    //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류
    String ss_subjcourse = box.getString("s_subjcourse");       //과정&코스                                                                                                   javascript:topLogout();
    String ss_subjseq = box.getString("s_subjseq");          //과정 차수
    String ss_usergrcode = box.getString("s_usergrcode");    //사용자 기관코드

    String org_subjcourse = ss_subjcourse;  //최초 넘어온 과정코드

    String v_isretire = box.getStringDefault("p_isretire", "N");
    String v_isemtpty = box.getStringDefault("p_isemtpty", "N");
    String v_isstoped = box.getStringDefault("p_isstoped", "N");
    String v_stuoverlap = box.getStringDefault("p_stuoverlap", "N");
    String v_isgraduated = box.getStringDefault("p_isgraduated", "N");

    String v_subjnm   = box.getString("s_subjnm");
    String v_grcodenm = box.getString("s_grcodenm");
    String v_subjseqgrnm = box.getString("s_subjseqgrnm");

    String tid = box.getString("tid");
    String v_luserid   = box.getSession("userid");
    String v_inputlevel= box.getString("p_inputlevel");

    String v_realFileName = box.getRealFileName("p_file");
    String v_newFileName  = box.getNewFileName("p_file");
    int     i=0;
    String v_userid  = "";
    String v_name    = "";
    String v_subjseq = "";
    String v_imsisubj      = "";
    String v_imsisubjseq   = "";

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

    Exception ex01 = null;

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grcode
));
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_gyear
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_grseq
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
            out.print( weblogic.utils.StringUtils.valueOf(v_isretire
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_isemtpty
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_isstoped
));
            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_stuoverlap
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_isgraduated
));
            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_grcodenm
));
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(ss_gyear
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjnm
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(v_subjseqgrnm
));
            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}

    try {
        connMgr = new DBConnectionManager();
        connMgr.setAutoCommit(false);

        workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.home")+v_newFileName));
        // workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload.accept")+v_newFileName));
        sheet = workbook.getSheet(0);

        proposebean = new ProposeBean();
        MainSubjSearchBean msbean = new MainSubjSearchBean();
        FinishBean fbean = new FinishBean();
        DecimalFormat df = new DecimalFormat("0000");

        String v_errvalue = "";
        String v_errtxt = "";
        String v_year = "";

        String v_isonoff  = "";
        String v_isclosed = "";
        String v_edustart = "";
        String v_eduend = "";
        String v_subjseqgr = "";


        String v_errString = "";
        String v_okString = "";

        String csSubj = "";
        String csSubjSeq = "";

        ArrayList<HashMap<String, String>> csSubjList = null;
        HashMap<String, String> csSubjMap = null;

        int errcnt = 0;
        int okcnt = 0;

        System.out.println(ss_subjseq+"----------------------------------------------------------------------------");

        for (i=1; i < sheet.getRows() ; i++ ) {
            v_userid = StringManager.trim( sheet.getCell(0,i).getContents());  // 아이디

            // 차수 ALL 일경우
            if(ss_subjseq.equals("ALL")) {
                v_imsisubjseq = StringManager.trim(sheet.getCell(1, i).getContents());  // 차수

                if(v_imsisubjseq.equals("")) {
                    out.println("<font color='red'><b>엑셀양식Error</b><br/><b>차수ALL선택시에는 엑셀 입력양식에 차수를 입력하여야 합니다.</b></font>");
                    return;
                }

                // 과정 ALL 일경우
                if(org_subjcourse.equals("ALL") || org_subjcourse.equals("")){
                    v_imsisubj = StringManager.trim(sheet.getCell(2,i).getContents());  // 과정

                    if(v_imsisubj.equals("")) {
                        out.println("<font color='red'><b>엑셀양식Error</b><br/><b>과정ALL선택시에는 엑셀 입력양식에 과정을 입력하여야 합니다.</b></font>");
                        return;
                    }

                    ss_subjcourse = v_imsisubj;
                }

                System.out.println(i + "     ----------------------------------------------------------------------------");

                v_subjseqgr = df.format(Integer.parseInt(v_imsisubjseq));
                v_subjseq = fbean.selectGrSubjseq(connMgr, ss_subjcourse, ss_gyear, v_subjseqgr, ss_grcode);

            } else {
                v_subjseqgr = StringManager.replace(v_subjseqgrnm,"차","");
                v_subjseq = ss_subjseq;
            }

            v_year = proposebean.getSubjYear(connMgr, ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, v_subjseq);

            DataBox dbox = msbean.SubjseqInfo(connMgr, ss_subjcourse, ss_grcode, v_subjseq, v_year);
            if(dbox != null){
                v_isonoff  = dbox.getString("d_isonoff");
                v_isclosed = dbox.getString("d_isclosed");
                v_edustart = dbox.getString("d_edustart");
                v_eduend   = dbox.getString("d_eduend");
            }

            v_errvalue=msbean.isExitSeq(ss_grcode, ss_gyear, ss_subjcourse, v_subjseq);

            //인사DB검색
            if(v_errvalue.equals("0")){
                v_errvalue=msbean.isExitMember(v_userid, v_isretire, v_isemtpty, v_isstoped);

                //수강신청 테이블 검색
                if(v_errvalue.equals("0")){
                    v_errvalue=msbean.isPropose(v_userid, ss_subjcourse, v_year, v_subjseq);
                }

                //집합과정 중복여부
                if(v_errvalue.equals("0") && v_stuoverlap.equals("Y")){
                    v_errvalue=msbean.ziphapOverLapEduterm(v_edustart, v_eduend, v_userid);
                }

                //해당과정 이수여부 체크
                if(v_errvalue.equals("0") && v_isgraduated.equals("Y")){
                    v_errvalue = msbean.isStold(v_userid, ss_subjcourse, v_year, v_subjseq);
                }
            }

              // Message 출력
            v_errtxt = msbean.isGetErrtxt(v_errvalue);

            if(!v_errvalue.equals("0")){
                v_errString += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + StringManager.cutZero(v_subjseqgr)+"차</td><td class=table_02_1>"+v_errtxt+"</td>";
                errcnt++;
            } else {
                v_okString += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + StringManager.cutZero(v_subjseqgr)+"차("+ss_subjcourse+")</td><td class=table_02_1>"+v_errtxt+"</td>";
                // v_okString += "<tr><td class=table_02_1>"+(i+1)+"</td><td class=table_02_1>" + v_userid + "</td><td class=table_02_1>" + StringManager.cutZero(v_subjseqgr)+"차</td><td class=table_02_1>"+v_errtxt+"</td>";
            }
        }

        if (v_process.equals("insertFileToDB") && errcnt == 0 ) {

            for (i = 1; i < sheet.getRows() ; i++ ) {
                v_userid = StringManager.trim( sheet.getCell(0,i).getContents());  // 아이디
                // 차수 ALL 일경우
                if(ss_subjseq.equals("ALL")){
                    v_imsisubjseq = StringManager.trim(sheet.getCell(1,i).getContents());  // 차수

                    // 과정 ALL 일경우
                    if(org_subjcourse.equals("ALL")){
                        v_imsisubj = StringManager.trim(sheet.getCell(2,i).getContents());  // 과정
                        ss_subjcourse = v_imsisubj;
                    }

                    v_subjseqgr = df.format(Integer.parseInt(v_imsisubjseq));
                    v_subjseq = fbean.selectGrSubjseq(connMgr, ss_subjcourse, ss_gyear, v_subjseqgr, ss_grcode);

                } else {
                    v_subjseqgr = StringManager.replace(v_subjseqgrnm,"차","");
                    v_subjseq = ss_subjseq;
                }

                v_year = proposebean.getSubjYear(connMgr, ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, v_subjseq);

                DataBox dbox = msbean.SubjseqInfo(connMgr, ss_subjcourse, ss_grcode, v_subjseq, v_year);
                if(dbox != null){
                    v_isonoff  = dbox.getString("d_isonoff");
                    v_isclosed = dbox.getString("d_isclosed");
                    v_edustart = dbox.getString("d_edustart");
                    v_eduend   = dbox.getString("d_eduend");
                }

                inputdata.clear();
                inputdata.put("connMgr", connMgr);
                inputdata.put("subj", ss_subjcourse);
                inputdata.put("year", v_year);
                inputdata.put("subjseq", v_subjseq);
                inputdata.put("userid", v_userid);
                inputdata.put("tid", tid);
                inputdata.put("luserid", v_luserid);
                inputdata.put("isdinsert", "Y");
                inputdata.put("chkfirst", "Y");

                inputdata.put("chkfinal", "Y");
                inputdata.put("usergrcode", ss_usergrcode); //기관코드
                inputdata.put("grcode", ss_grcode); //교육그룹

                csSubjMap.put("year", v_year );
                csSubjMap.put("grcode", ss_grcode );
                csSubjMap.put("subjseq", StringManager.trim( sheet.getCell(1,i).getContents() ) );
                csSubjMap.put("subj", StringManager.trim( sheet.getCell(2,i).getContents() ) );
                csSubjMap.put("cssubjseq", StringManager.trim( sheet.getCell(3,i).getContents() ) );
                csSubjMap.put("cssubj", StringManager.trim( sheet.getCell(4,i).getContents() ) );
                csSubjList.add(csSubjMap);

                isOk = proposebean.insertPropose(inputdata);
                isOk1 = proposebean.insertStudent(inputdata);

                if ( isOk > 0 ) {
                    this.updateCSSubjSeqInfo(connMgr, csSubjList);
                }
            }
        }


            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( errcnt 
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}

            if(errcnt != 0){
                out.print(v_errString);
            } else {

            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}

            }

            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_okString 
));
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}

        if(v_process.equals("insertFileToDB")){

            if(errcnt == 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        }

    } catch (Exception e) {
        System.out.println(e);

            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}


    } finally {
        //첨부파일삭제
        FileManager.deleteFile(conf.getProperty("dir.upload.accept")+v_newFileName);
        if(propose_pstmt != null) { try { propose_pstmt.close(); } catch (Exception e) {} }
        if(student_pstmt != null) { try { student_pstmt.close(); } catch (Exception e) {} }
        if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
    }

            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}

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
            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( request.getServletPath()
));
            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}
  }   
            {_writeText(response, out, _wl_block26, _wl_block26Bytes);}
            {_writeText(response, out, _wl_block27, _wl_block27Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
