package jsp_servlet._learn._admin._course;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.util.*;
import java.text.*;
import com.credu.course.*;
import com.credu.common.*;
import com.credu.library.*;
import com.credu.system.*;
import com.dunet.common.util.*;

public final class __za_grseq_e extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/admin/course/za_Grseq_E.jsp", 1422235985558L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block0 ="\r\n\r\n";
    private final static byte[]  _wl_block0Bytes = _getBytes( _wl_block0 );

    private final static String  _wl_block1 ="\r\n";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\r\n  \r\n";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-kr\">\r\n</head>\r\n<body>\r\n    <table border=1>\r\n          <tr>            \r\n          \t<td><b>과정</b></td>\r\n          \t<td><b>교육그룹<br/>개설차수</b></td>\r\n          \t<td><b>과정코드</b></td>\r\n          \t<td><b>교육그룹내<br/>과정차수</b></td>\r\n          \t<td><b>신청기간</b></td>\r\n          \t<td><b>교육기간</b></td>\r\n          \t<td><b>정원</b></td>\r\n          \t<td><b>신청인원</b></td>\r\n          \t<td><b>승인인원</b></td>\r\n          \t<td><b>수료인원</b></td>\r\n          \t<td><b>수강료</b></td>\r\n          \t<td><b>자동승인여부</b></td>\r\n          \t<td><b>복습기간</b></td>          \t\r\n          </tr>\r\n          \r\n          ";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\r\n          <tr>          \t\r\n             \t  <td>";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="</td>\t\t\t\r\n\t\t\t      <td>";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="</td>\t\t\t\t\r\n\t\t\t      <td>";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="</td>\r\n\t\t\t      <td>";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 =" ~ ";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="</td>\t\t\t      \t\t\t\t\r\n          </tr>\r\n";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n        </table>\r\n      \r\n</body>\r\n</html>\r\n\r\n";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

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
//  1. 제      목: 과정차수별공지사항
//  2. 프로그램명: za_SubjGong_L.jsp
//  3. 개      요: 선택과정차수별 공지 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            com.credu.library.ConfigSet conf= null;{
                conf=(com.credu.library.ConfigSet)pageContext.getAttribute("conf");
                if(conf==null){
                    conf=new com.credu.library.ConfigSet();
                    pageContext.setAttribute("conf",conf);

                }
            }
            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}

    response.setHeader("Content-Disposition", "attachment; filename=za_regEvent_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_pageno   = box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");
    int     v_totalrowcount =  0;

    String v_grseqnm = "";
    String v_gyear = "";
    String v_grseq = "";
    String v_subj = "";
    String v_subjnm = "";
    String v_year = "";
    String v_subjseqgr = "";
    String v_subjseq = "";
    String v_subjseqgr_1 = "";
    String v_propstart = "";
    String v_propend = "";
    String v_edustart = "";
    String v_eduend = "";
    String v_course = "";
    String v_coursenm = "";
    String v_cyear = "";
    String v_courseseq = "";
    String v_subjcnt = "";
    String v_isonoff = "";
    String v_biyong = "";
    String v_isonoffnm = "";
    String v_rowspan_grseq = "";
    String v_cnt_propose = "";
    String v_cnt_student = "";
    String v_cnt_stold = "";
    String v_autoconfirm = "";
    String v_reviewdays = "";
    String v_studentlimit = "";
    

    int v_dispnum   = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수
        
    
    ArrayList selectRegList    = (ArrayList)request.getAttribute("GrseqList");     


            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}

            for(int i = 0; i < selectRegList.size(); i++) {

                DataBox dbox = (DataBox)selectRegList.get(i);
                
                  v_grseqnm				= dbox.getString("d_grseqnm");
			      v_grseq				= dbox.getString("d_grseq");
			      v_subj				= dbox.getString("d_subj");
			      v_subjnm				= dbox.getString("d_subjnm");
			      v_year				= dbox.getString("d_year");
			      v_subjseqgr			= dbox.getString("d_subjseqgr");
			      v_subjseq				= dbox.getString("d_subjseq");
			      v_subjseqgr_1			= dbox.getString("d_subjseqgr_1");
			      v_propstart			= dbox.getString("d_propstart");
			      v_propend				= dbox.getString("d_propend");
			      v_edustart			= dbox.getString("d_edustart");
			      v_eduend				= dbox.getString("d_eduend");
			      v_course				= dbox.getString("d_course");
			      v_coursenm			= dbox.getString("d_coursenm");
			      v_cyear				= dbox.getString("d_cyear");
			      v_courseseq			= dbox.getString("d_courseseq");
			      v_subjcnt				= dbox.getString("d_subjcnt");
			      v_isonoff				= dbox.getString("d_isonoff");
			      v_biyong				= dbox.getString("d_biyong");
			      v_isonoffnm			= dbox.getString("d_isonoffnm");
			      v_rowspan_grseq		= dbox.getString("d_rowspan_grseq");
			      v_cnt_propose			= dbox.getString("d_cnt_propose");
			      v_cnt_student			= dbox.getString("d_cnt_student");
			      v_cnt_stold  			= dbox.getString("d_cnt_stold");
			      v_autoconfirm  		= dbox.getString("d_autoconfirm");
			      v_reviewdays 			= dbox.getString("d_reviewdays");
			      v_studentlimit		= dbox.getString("d_studentlimit");

            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_subjnm 
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_grseq 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_subj 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_subjseqgr 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd")
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getFormatDate(v_propend,"yyyy/MM/dd") 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd")
));
            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_studentlimit 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_cnt_propose 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_cnt_student 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_cnt_stold 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_biyong 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_autoconfirm 
));
            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( v_reviewdays 
));
            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}

        }

            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
