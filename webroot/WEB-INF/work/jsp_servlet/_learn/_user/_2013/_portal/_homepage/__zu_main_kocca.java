package jsp_servlet._learn._user._2013._portal._homepage;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.credu.library.DataBox;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class __zu_main_kocca extends  weblogic.servlet.jsp.JspBase  implements weblogic.servlet.jsp.StaleIndicator {

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
        if (sci.isResourceStale("/learn/user/2013/portal/homepage/zu_Main_KOCCA.jsp", 1422236050395L ,"9.2.3.0","Asia/Seoul")) return true;
        if (sci.isResourceStale("/learn/user/2013/portal/include/header.jsp", 1422236050130L ,"9.2.3.0","Asia/Seoul")) return true;
        if (sci.isResourceStale("/learn/user/2013/portal/include/footer.html", 1422236050130L ,"9.2.3.0","Asia/Seoul")) return true;
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

    private final static String  _wl_block1 ="\r\n<!DOCTYPE html> \r\n<html>\r\n\t<head> \r\n    <meta property=\"og:title\" content=\"";
    private final static byte[]  _wl_block1Bytes = _getBytes( _wl_block1 );

    private final static String  _wl_block2 ="\">\r\n    <meta property=\"og:url\" content=\"";
    private final static byte[]  _wl_block2Bytes = _getBytes( _wl_block2 );

    private final static String  _wl_block3 ="\">\r\n    <meta property=\"og:description\" content=\"";
    private final static byte[]  _wl_block3Bytes = _getBytes( _wl_block3 );

    private final static String  _wl_block4 ="\">\r\n    <meta property=\"og:image\" content=\"";
    private final static byte[]  _wl_block4Bytes = _getBytes( _wl_block4 );

    private final static String  _wl_block5 ="\">\r\n\r\n\t<meta charset=\"euc-kr\">\r\n\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n\r\n\t<title>";
    private final static byte[]  _wl_block5Bytes = _getBytes( _wl_block5 );

    private final static String  _wl_block6 ="</title>\r\n\r\n    <link href=\"/css/jqueryui/1.10.0/themes/base/jquery-ui.css\" rel=\"stylesheet\" />\r\n\t<link href=\"/css/2013/common.css\" rel=\"stylesheet\">\r\n\t<link href=\"/css/2013/layout.css\" rel=\"stylesheet\">\r\n\r\n\t<link href=\"/css/2013/header.css\" rel=\"stylesheet\">\r\n";
    private final static byte[]  _wl_block6Bytes = _getBytes( _wl_block6 );

    private final static String  _wl_block7 ="\r\n\t<link href=\"/css/2013/main.css\" rel=\"stylesheet\">\r\n";
    private final static byte[]  _wl_block7Bytes = _getBytes( _wl_block7 );

    private final static String  _wl_block8 ="\r\n\t<link href=\"/css/2013/sub.css\" rel=\"stylesheet\">\r\n\r\n\t<script type=\"text/javascript\" src=\"/js/jquery/1.8.3/jquery.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/jqueryui/1.10.0/jquery-ui.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/2013/jquery.selectbox-0.6.1.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/js/2013/common.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"/js/2013/header.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"/js/2013/lib.js\"></script>\r\n\t<script type=\"text/javascript\" src=\"/js/2013/main.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/script/cresys_lib.js\"></script>\r\n    <script type=\"text/javascript\" src=\"/script/mainscript.js\"></script>\r\n    <!-- <script type=\"text/javascript\" src=\"/js/2013/caption.js\"></script> //-->\r\n    <!-- <script type=\"text/javascript\" src=\"/js/2013/jquery.easing.1.3.js\"></script> //-->\r\n    <!-- <script type=\"text/javascript\" src=\"/js/2013/jquery.cycle2.min.js\"></script> //-->\r\n\r\n</head>\r\n<body>\r\n<!-- 상단 기본 form //-->\r\n<form name=\"topdefaultForm\" method=\"post\" action=\"/\" id=\"topdefaultForm\" >\r\n    <input type=\"hidden\" name=\"p_process\" value=\"\" />\r\n    <input type=\"hidden\" name=\"gubun\" value=\"0\" />\r\n    <input type=\"hidden\" name=\"menuid\" value=\"\" />\r\n    <input type=\"hidden\" name=\"url\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_cmuno\" value=\"\" />\r\n    <input type=\"hidden\" name=\"searchClass\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_auth\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_topuserid\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_toppwd\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_area\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_lsearchtext\" id=\"oLsearchtext\" value=\"\" />\r\n    <input type=\"hidden\" name=\"p_lecture_cls\" value=\"\" /> <!-- 열린강좌(콘텐츠온) 분류별 조회가 필요하여 해당 엘리먼트 추가 //-->\r\n    <input type=\"hidden\" name=\"pageNo\" value=\"\" /> <!-- 열린강좌(콘텐츠온) 페이징 기능이 필요하여 해당 엘리먼트 추가 //-->\r\n    <input type=\"submit\" class=\"close\" title=\"실행\" />\r\n</form>\r\n\r\n<!-- topbar -->\r\n<div id=\"topbar\">\r\n\t<!-- body -->\r\n\t<div class=\"body\">\r\n\t\t<div class=\"app-link\">\r\n\t\t\t<a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp\">한국콘텐츠아카데미앱</a>\r\n\t\t</div>\r\n\t\t<div class=\"util\">\r\n";
    private final static byte[]  _wl_block8Bytes = _getBytes( _wl_block8 );

    private final static String  _wl_block9 ="\r\n\t\t\t<a href=\"/learn/user/2013/portal/homepage/zu_MainLogin.jsp\">로그인</a><span class=\"line\"> | </span>\r\n\t\t\t<a href=\"/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin\">회원가입</a>\r\n";
    private final static byte[]  _wl_block9Bytes = _getBytes( _wl_block9 );

    private final static String  _wl_block10 ="\r\n            <a href=\"#\" class=\"my-menu-open\">";
    private final static byte[]  _wl_block10Bytes = _getBytes( _wl_block10 );

    private final static String  _wl_block11 ="님 개인메뉴</a><span class=\"line\"> | </span>\r\n\t\t\t<div class=\"my-menu-layer\">\r\n\t\t\t\t<ul>\r\n\t\t\t\t\t<li><a href=\"/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage\">온라인 강의실</a></li>\r\n\t\t\t\t\t<li><a href=\"/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage\">현장교육 강의실</a></li>\r\n\t\t\t\t\t<li><a href=\"/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE\">내가 작성한 글</a></li>\r\n\t\t\t\t\t<li><a href=\"/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage\">상담내역</a></li>\r\n\t\t\t\t\t<li><a href=\"/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate\">정보관리</a></li>\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n\t\t\t<a href=\"javascript:mainmenu(\'998\');\">로그아웃</a>\r\n";
    private final static byte[]  _wl_block11Bytes = _getBytes( _wl_block11 );

    private final static String  _wl_block12 ="\r\n\t\t</div>\r\n\t</div>\r\n\t<!-- /body -->\r\n</div>\r\n<!-- /topbar -->\r\n<!-- header-new -->\r\n<div id=\"header-new\">\r\n\t<div class=\"brand\">\r\n\t\t<a href=\"/servlet/controller.homepage.MainServlet\"><img src=\"/images/common/brand.png\" alt=\"한국콘텐츠아카데미\"></a>\r\n\t</div>\r\n\r\n<!--\r\n\t<div class=\"search\">\r\n\t\t<div class=\"field\">\r\n\t\t\t<input type=\"text\">\r\n\t\t\t<input type=\"image\" src=\"/images/common/btn-search.png\" alt=\"검색\">\r\n\t\t</div>\r\n\t\t<div class=\"word\">\r\n\t\t\t<ul id=\"searchWord\">\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어1</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어1</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어1</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어1</a></span>\r\n\t\t\t\t</li>\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어2</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어2</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어2</a></span>\r\n\t\t\t\t\t<span><a href=\"#\">인기검색어2</a></span>\r\n\t\t\t\t</li>\r\n\t\t\t</ul>\r\n\t\t</div>\r\n\t</div>\r\n\r\n-->\r\n</div>\r\n<!-- /header-new -->\r\n<!-- nav -->\r\n<div id=\"nav\">\r\n\t<div class=\"sub-bg\"></div>\r\n\t<ul>\r\n\t\t<li><a href=\"/servlet/controller.homepage.HomeNoticeServlet?p_process=List\">새소식</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomeNoticeServlet?p_process=List\">공지사항</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.TongBoardServlet?p_process=list\" >아카데미 이야기</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.CMUBoardServlet?p_process=list\" >CMU 스토리</a></li>\r\n\t\t\t\t<!-- 2015-01-09 개편변경\r\n                <li><a href=\"javascript:alert(\'이벤트 준비중입니다.\');\">이벤트</a></li>\r\n                <li><a href=\"/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList\">보도자료</a></li>\r\n                -->\t\t\t\t\r\n\t\t\t</ul>\r\n\t\t</li>\r\n\t\t<li><a href=\"/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001\">현장교육</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001\">현업인 직무재교육</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002\">차세대 인재 양성교육</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList\">일정안내</a></li>\r\n\t\t\t</ul>\r\n\t\t</li>\r\n\t\t<li><a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList\">온라인교육</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList\">분야별</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup\">직업별</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem\">교육체계도</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList\">휴넷인문학</a></li>\r\n\t\t\t</ul>\r\n\t\t</li>\r\n\t\t<li><a href=\"javascript:fnOpenClassList(\'ALL\');\">열린강좌</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"javascript:fnOpenClassList(\'ALL\');\">분류별</a></li>\r\n\t\t\t\t<li><a href=\"javascript:fnOpenClassList(\'ALL\');\">테마별</a></li>\r\n\t\t\t</ul>\r\n\t\t</li>\r\n<!--\r\n\t\t<li><a href=\"/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage\">자료실</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage\">온라인교육</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.CMUBoardServlet?p_process=list\" >CMU 스토리</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.TongBoardServlet?p_process=list\">아카데미 소식 뒷이야기</a></li>\r\n\t\t\t</ul>\r\n\t\t</li>\r\n-->\r\n        <li><a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome\">고객센터</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome\">학습지원</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList\">자주하는 질문</a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList\">교육문의</a></li>\r\n\t\t\t\t<!--\r\n                <li><a href=\"javascript:alert(\'수료증 발급안내 준비중입니다.\');\">수료증 발급안내</a></li>\r\n                -->\r\n\t\t\t</ul>\r\n\t\t</li>\r\n\t\t<li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision\">아카데미소개</a>\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision\">아카데미소개</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage\">인사말</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility\">시설소개</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu\">교육사업안내</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR\">홍보영상</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER\">교육협력기관</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageContactServlet?p_process=contactus\">제휴문의</a></li>\r\n                <li><a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction\">오시는길</a></li>\r\n\t\t\t</ul>\r\n\t\t</li>\r\n\t</ul>\r\n</div>\r\n<!-- /nav -->";
    private final static byte[]  _wl_block12Bytes = _getBytes( _wl_block12 );

    private final static String  _wl_block13 ="\r\n<!-- main-container -->\r\n<div id=\"main-container\">\r\n\t<!-- main-content1 -->\r\n\t<div class=\"main-content1\">\r\n\t\t<!-- main-banner -->\r\n\t\t<div class=\"main-banner\">\r\n\t\t\t<div class=\"header\">\r\n\t\t\t\t<ul id=\"mainBannerLg\">\r\n                    ";
    private final static byte[]  _wl_block13Bytes = _getBytes( _wl_block13 );

    private final static String  _wl_block14 ="\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul id=\"mainBanner\">\r\n                    ";
    private final static byte[]  _wl_block14Bytes = _getBytes( _wl_block14 );

    private final static String  _wl_block15 ="\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<!-- /main-banner -->\r\n\t\t<!-- side -->\r\n\t\t<div class=\"side\">\r\n\t\t\t<!-- lt-tab -->\r\n\t\t\t<div class=\"lt-tab\">\r\n\t\t\t\t<div class=\"header\">\r\n\t\t\t\t\t<h3>새소식</h3>\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li class=\"active\"><a href=\"#\">전체</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\">공지</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\">이벤트</a></li>\r\n\t\t\t\t\t\t<!-- <li><a href=\"#\">보도자료</a></li> //-->\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t\t<a href=\"#\" class=\"more\" style=\"display:none;\"><img src=\"/images/common/btn-more.png\" alt=\"더 보기\"></a>\r\n\t\t\t\t</div>\r\n                <!-- 전체 탭 목록 시작 //-->\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n";
    private final static byte[]  _wl_block15Bytes = _getBytes( _wl_block15 );

    private final static String  _wl_block16 ="\r\n\t\t\t\t\t\t<li><a href=\"#\" onclick=\"fnViewBoardDetail(\'";
    private final static byte[]  _wl_block16Bytes = _getBytes( _wl_block16 );

    private final static String  _wl_block17 ="\', ";
    private final static byte[]  _wl_block17Bytes = _getBytes( _wl_block17 );

    private final static String  _wl_block18 =");\"><strong>[";
    private final static byte[]  _wl_block18Bytes = _getBytes( _wl_block18 );

    private final static String  _wl_block19 ="]</strong> ";
    private final static byte[]  _wl_block19Bytes = _getBytes( _wl_block19 );

    private final static String  _wl_block20 ="</a> ";
    private final static byte[]  _wl_block20Bytes = _getBytes( _wl_block20 );

    private final static String  _wl_block21 ="</li>\r\n";
    private final static byte[]  _wl_block21Bytes = _getBytes( _wl_block21 );

    private final static String  _wl_block22 ="\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n                <!-- 전체 탭 목록 종료 //-->\r\n\r\n                <!-- 공지사항 탭 목록 시작 //-->\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n";
    private final static byte[]  _wl_block22Bytes = _getBytes( _wl_block22 );

    private final static String  _wl_block23 ="\r\n\t\t\t\t\t\t<li><a href=\"#\" onclick=\"fnViewBoardDetail(\'NOTICE\', ";
    private final static byte[]  _wl_block23Bytes = _getBytes( _wl_block23 );

    private final static String  _wl_block24 ="\r\n\t\t\t\t\t\t<li><a href=\"#\">등록된 공지사항이 없습니다.</li>\r\n";
    private final static byte[]  _wl_block24Bytes = _getBytes( _wl_block24 );

    private final static String  _wl_block25 ="\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n                <!-- 공지사항 탭 목록 끝 //-->\r\n\r\n                <!-- 이벤트 탭 목록 시작 //-->\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n";
    private final static byte[]  _wl_block25Bytes = _getBytes( _wl_block25 );

    private final static String  _wl_block26 ="\r\n\t\t\t\t\t\t<li><a href=\"#\" onclick=\"fnViewBoardDetail(\'EVENT\', ";
    private final static byte[]  _wl_block26Bytes = _getBytes( _wl_block26 );

    private final static String  _wl_block27 ="\r\n\t\t\t\t\t\t<li><a href=\"#\">현재 진행중인 이벤트가 없습니다.</li>\r\n";
    private final static byte[]  _wl_block27Bytes = _getBytes( _wl_block27 );

    private final static String  _wl_block28 ="\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n                <!-- 이벤트 탭 목록 끝 //-->\r\n\r\n                <!-- 보도자료 탭 목록 \r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[444]</strong> 제목 입니다 <img src=\"/images/common/icon-n.png\" alt=\"N\"></a></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n                //-->\r\n\t\t\t</div>\r\n\t\t\t<!-- /lt-tab -->\r\n\t\t\t<div class=\"banner\">\r\n\t\t\t\t<a href=\"http://www.ckl.or.kr/\" target=\"_blank\" title=\"새창으로 열립니다.\"><img style=\"border-style:solid; border-color:#dedede; border-width:1px;\" src=\"/images/main/bnr.jpg\" alt=\"Content Korea Lab. 대한민국 콘텐츠 창작지원 콘텐츠코리아랩이 책임지겠습니다.\"></a>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<!-- /side -->\r\n\t</div>\r\n\t<!-- /main-content1 -->\r\n\t<!-- main-content2 -->\r\n\t<div class=\"main-content2\">\r\n\t\t<!-- lt-wz-group -->\r\n\t\t<div class=\"lt-wz-group\">\r\n\t\t\t<div class=\"header\">\r\n\t\t\t\t<ul>\r\n\t\t\t\t\t<li class=\"active\"><a href=\"#\">아카데미 이야기</a></li>\r\n\t\t\t\t\t<li><a href=\"#\">방송영상</a></li>\r\n\t\t\t\t\t<li><a href=\"#\">게임</a></li>\r\n\t\t\t\t\t<li><a href=\"#\">만애캐</a></li>\r\n\t\t\t\t\t<li><a href=\"#\">음악&middot;공연</a></li>\r\n\t\t\t\t\t<li><a href=\"#\">열린강좌</a></li>\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n            <!-- 아카데미 이야기 시작 //-->\r\n\t\t\t<div class=\"body\" style=\"display:block;\">\r\n";
    private final static byte[]  _wl_block28Bytes = _getBytes( _wl_block28 );

    private final static String  _wl_block29 ="\r\n\t\t\t\t<div class=\"type2-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.homepage.TongBoardServlet?p_process=view&p_pageno=1&p_pagesize=15&seq=";
    private final static byte[]  _wl_block29Bytes = _getBytes( _wl_block29 );

    private final static String  _wl_block30 ="&searchType=title&searchKeyword=\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"";
    private final static byte[]  _wl_block30Bytes = _getBytes( _wl_block30 );

    private final static String  _wl_block31 ="\" alt=\"";
    private final static byte[]  _wl_block31Bytes = _getBytes( _wl_block31 );

    private final static String  _wl_block32 ="\"></span>\r\n\t\t\t\t\t\t<span class=\"con\">\r\n\t\t\t\t\t\t\t<span class=\"subject\">";
    private final static byte[]  _wl_block32Bytes = _getBytes( _wl_block32 );

    private final static String  _wl_block33 ="</span>\r\n\t\t\t\t\t\t\t<span class=\"text\">";
    private final static byte[]  _wl_block33Bytes = _getBytes( _wl_block33 );

    private final static String  _wl_block34 ="</span>\r\n\t\t\t\t\t\t</span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t\t<ul class=\"type2\">\r\n";
    private final static byte[]  _wl_block34Bytes = _getBytes( _wl_block34 );

    private final static String  _wl_block35 ="\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.homepage.TongBoardServlet?p_process=view&p_pageno=1&p_pagesize=15&seq=";
    private final static byte[]  _wl_block35Bytes = _getBytes( _wl_block35 );

    private final static String  _wl_block36 ="&searchType=title&searchKeyword=\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"";
    private final static byte[]  _wl_block36Bytes = _getBytes( _wl_block36 );

    private final static String  _wl_block37 ="\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">";
    private final static byte[]  _wl_block37Bytes = _getBytes( _wl_block37 );

    private final static String  _wl_block38 ="</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n";
    private final static byte[]  _wl_block38Bytes = _getBytes( _wl_block38 );

    private final static String  _wl_block39 ="\r\n\r\n\t\t\t\t</ul>\r\n\t\t\t</div>\r\n            <!-- 아카데미 이야기 끝 //-->\r\n\r\n            <!-- 방송영상 시작 //-->\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul class=\"type1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CB10015\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171433171_lee1.gif\" alt=\"After Effect CS5\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">After Effect CS5</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CB10017\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171434491_lee1.gif\" alt=\"Adobe Premiere Pro CS5\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">Adobe Premiere Pro CS5</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CB10020\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171436031_lee1.gif\" alt=\"디지털 영상 색 보정\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">디지털 영상 색 보정</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CB12003\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171446581_lee1.gif\" alt=\"글쓰기를 위한 창조적 사고\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">글쓰기를 위한 창조적 사고</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t\t\t\t<div class=\"type1-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CB12007\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"/images/main/_category_thumb/CB12007_dslr_188x338.jpg\" alt=\"전문가도 부럽지 않은 DSLR 활용하기\"></span>\r\n\t\t\t\t\t\t<span class=\"subject\">전문가도 부럽지 않은 DSLR 활용하기</span>\r\n\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            <!-- 방송영상 끝 //-->\r\n\r\n            <!-- 게임 시작 //-->\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul class=\"type1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG14006\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171615491_lee1.gif\" alt=\"Unity3D를 이용한 모바일 게임 개발\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">Unity3D를 이용한 모바일 게임 개발</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG07126\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171530451_lee1.gif\" alt=\"게임캐릭터디자인1\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">게임캐릭터디자인1</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG11001\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171545581_lee1.gif\" alt=\"게임 기획 및 그래픽 실무자를 위한 게임기술 이해\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">게임 기획 및 그래픽 실무자를 위한 게임기술 이해</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG14005\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171615111_lee1.gif\" alt=\"3D 게임을 위한 캐릭터 모델링\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">3D 게임을 위한 캐릭터 모델링</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t\t\t\t<div class=\"type1-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG12011\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"/images/main/_category_thumb/CG12011_Unity3d_188x338.jpg\" alt=\"Unity3d를 이용한 게임 만들기\"></span>\r\n\t\t\t\t\t\t<span class=\"subject\">Unity3d를 이용한 게임 만들기</span>\r\n\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            <!-- 게임 끝 //-->\r\n\r\n            <!-- 만애캐 시작 //-->\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul class=\"type1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK09167\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171643441_lee1.gif\" alt=\"헐리우드 애니메이션 스토리텔링 전략\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">헐리우드 애니메이션 스토리텔링 전략</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK14010\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171732421_lee1.gif\" alt=\"캐릭터 심리학의 응용\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">캐릭터 심리학의 응용</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK14001\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171726501_lee1.gif\" alt=\"스토리텔링과 발상을 통해 보는 만화 창작의 비밀\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">스토리텔링과 발상을 통해 보는 만화 창작의 비밀</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK14002\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171727211_lee1.gif\" alt=\"캐릭터와 플롯을 통해 보는 만화 창작의 비밀\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">캐릭터와 플롯을 통해 보는 만화 창작의 비밀</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t\t\t\t<div class=\"type1-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK14003\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"/images/main/_category_thumb/CK14003_character_188x338.jpg\" alt=\"캐릭터 심리학의 이해\"></span>\r\n\t\t\t\t\t\t<span class=\"subject\">캐릭터 심리학의 이해</span>\r\n\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            <!-- 만애캐 끝 //-->\r\n\r\n            <!-- 음악공연 시작 //-->\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul class=\"type1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK10080\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171702541_lee1.gif\" alt=\"공연기획과 홍보마케팅 실무\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">공연기획과 홍보마케팅 실무</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK11004\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171715281_lee1.gif\" alt=\"글로컬문화콘텐츠의 이해와 사례\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">글로컬문화콘텐츠의 이해와 사례</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK06063\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171624211_lee1.gif\" alt=\"음반제작워크숍\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">음반제작워크숍</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CK10073\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/bulletin/Subject_introducefile_201407171658231_lee1.gif\" alt=\"대중음악콘텐츠기획과 해외진출\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">대중음악콘텐츠기획과 해외진출</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t\t\t\t<div class=\"type1-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=previewSubjDetailPage&p_subj=CG14007\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"/images/main/_category_thumb/CG14007_pop_music_188x338.jpg\" alt=\"대중음악과 친해지기\"></span>\r\n\t\t\t\t\t\t<span class=\"subject\">대중음악과 친해지기</span>\r\n\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            <!-- 음악공연 끝 //-->\r\n\r\n            <!-- 열린강좌 시작 //-->\r\n\t\t\t<div class=\"body\">\r\n\t\t\t\t<ul class=\"type1\">\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=129&p_lecture_cls=GC03\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/accept/GoldClassAdmin_img_file/129.jpg\" alt=\"이종범의 디지털로 웹툰 제작하기 1 - 스토리의 기본 개념\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">이종범의 디지털로 웹툰 제작하기 1 - 스토리의 기본 개념</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=159&p_lecture_cls=GC07\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/accept/GoldClassAdmin_img_file_201406301702561_lee1.jpg\" alt=\"ACES 기술소개 1 - 빛의 영역 RGB\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">ACES 기술소개 1 - 빛의 영역 RGB</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=177&p_lecture_cls=GC06\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/accept/GoldClassAdmin_img_file_201406301710131_lee1.jpg\" alt=\"최초이자 최고의 브랜드 필름 성공요인 1부\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">최초이자 최고의 브랜드 필름 성공요인 1부</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=61&p_lecture_cls=GC04\">\r\n\t\t\t\t\t\t\t<span class=\"thumb\"><img src=\"http://edu.kocca.or.kr/upload/accept/GoldClassAdmin_img_file_201405231539221_lee1.gif\" alt=\"대중음악을 말하다\"></span>\r\n\t\t\t\t\t\t\t<span class=\"subject\">대중음악을 말하다</span>\r\n\t\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t\t</a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\r\n\t\t\t\t<div class=\"type1-lg\">\r\n\t\t\t\t\t<a href=\"/servlet/controller.infomation.GoldClassHomePageServlet?p_process=selectView&p_seq=87&p_lecture_cls=GC01\">\r\n\t\t\t\t\t\t<span class=\"thumb\"><img src=\"/images/main/_category_thumb/openclass_87_188x338.jpg\" alt=\"TV포맷 트렌드 1 - 가장 많이 만들어지고 팔리는 포맷 장르 - 데이팅 I\"></span>\r\n\t\t\t\t\t\t<span class=\"subject\">TV포맷 트렌드 1 - 가장 많이 만들어지고 팔리는 포맷 장르 - 데이팅 I</span>\r\n\t\t\t\t\t\t<span class=\"text\"></span>\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            <!-- 열린강좌 끝 //-->\r\n\r\n\t\t</div>\r\n\t\t<!-- /lt-wz-group -->\r\n\t\t<!-- side -->\r\n\t\t<div class=\"side\">\r\n\r\n            <div class=\"search\">\r\n                <div class=\"field\">\r\n                    <input type=\"text\" class=\"mainSearchInput\" id=\"topWarp_searchText\" name=\"topWarp_searchText\" placeholder=\"과정검색\" onkeypress=\"searchtotal_enter(event);\" style=\"font-weight:bold; color:#808080;\" />\r\n                    <a href=\"#\" onclick=\"totalSubjSearch1();\"><input type=\"image\" src=\"/images/common/btn-search.png\" alt=\"과정검색\"/></a>\r\n                </div>\r\n\r\n                <div class=\"word\">\r\n                    <ul id=\"searchWord\">\r\n                        <li>\r\n";
    private final static byte[]  _wl_block39Bytes = _getBytes( _wl_block39 );

    private final static String  _wl_block40 ="\r\n                        </li>\r\n                        <li>\r\n";
    private final static byte[]  _wl_block40Bytes = _getBytes( _wl_block40 );

    private final static String  _wl_block41 ="\r\n                            <span><a href=\"javascript:fnSearchSubjByKeyword(\'";
    private final static byte[]  _wl_block41Bytes = _getBytes( _wl_block41 );

    private final static String  _wl_block42 ="\');\">";
    private final static byte[]  _wl_block42Bytes = _getBytes( _wl_block42 );

    private final static String  _wl_block43 ="</a></span>\r\n";
    private final static byte[]  _wl_block43Bytes = _getBytes( _wl_block43 );

    private final static String  _wl_block44 ="\r\n                        </li>\r\n                    </ul>\r\n                </div>\r\n            </div>\r\n\r\n\t\t\t<!-- lt-tab -->\r\n            <!--\r\n\t\t\t<div class=\"lt-tab\">\r\n\t\t\t\t<div class=\"header\">\r\n\t\t\t\t\t<h3>게시판</h3>\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li class=\"active\"><a href=\"#\">Best 후기</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\">자료</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\">질문</a></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t\t<a href=\"#\" class=\"more\"><img src=\"/images/common/btn-more.png\" alt=\"MORE\"></a>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[행사]</strong> 제목 입니다 <img src=\"/images/common/icon-n.png\" alt=\"N\"></a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[행사]</strong> 제목 입니다</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[행사]</strong> 제목 입니다</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[행사]</strong> 제목 입니다</a></li>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[행사]</strong> 제목 입니다</a></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[222]</strong> 제목 입니다 <img src=\"/images/common/icon-n.png\" alt=\"N\"></a></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div class=\"body\">\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t<li><a href=\"#\"><strong>[333]</strong> 제목 입니다 <img src=\"/images/common/icon-n.png\" alt=\"N\"></a></li>\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n            //-->\r\n\t\t\t<!-- /lt-tab -->\r\n\r\n\t\t\t<div class=\"banner\">\r\n\t\t\t\t<a href=\"/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR\"><img src=\"/images/main/banner_0";
    private final static byte[]  _wl_block44Bytes = _getBytes( _wl_block44 );

    private final static String  _wl_block45 =".jpg\" width=\"280\" height=\"96\" alt=\"홍보영상 바로보기\"></a>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"banner\">\r\n\t\t\t\t<a href=\"/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList\"><img src=\"/images/main/bn-apply.jpg\" alt=\"온라인교육 수강신청\"></a>\r\n\t\t\t</div>\r\n\t\t\t<ul class=\"quick\">\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService\"><img src=\"/images/main/q1-off.png\" class=\"ro\" alt=\"원격지원 서비스\"></a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome\"><img src=\"/images/main/q2-off.png\" class=\"ro\" alt=\"학습 가이드\"></a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList\"><img src=\"/images/main/q3-off.png\" class=\"ro\" alt=\"자주하는 질문\"></a></li>\r\n\t\t\t\t<li><a href=\"/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage\"><img src=\"/images/main/q4-off.png\" class=\"ro\" alt=\"수료증 출력\"></a></li>\r\n\t\t\t</ul>\r\n\t\t</div>\r\n\t\t<!-- /side -->\r\n\t</div>\r\n\t<!-- main-content2 -->\r\n\t<!-- main-content3 -->\r\n\t<div class=\"main-content3\">\r\n\t\t<!-- col1 -->\r\n\t\t<div class=\"col1\">\r\n\t\t\t<img src=\"/images/main/callcenter.jpg\" alt=\"한국콘텐츠아카데미 교육문의 연락처입니다. 현장 교육 문의는 지역번호 02, 국번 3219, 번호 6540이며, 온라인 교육 문의는 지역번호 02, 국번 3219, 번호 5483입니다.\">\r\n\t\t</div>\r\n\t\t<!-- col1 -->\r\n\t\t<!-- col2 -->\r\n\t\t<div class=\"col2\">\r\n\t\t\t<ul>\r\n\t\t\t\t<li><a href=\"https://www.facebook.com/edukocca\" target=\"_blank\"><img src=\"/images/main/sns-f.png\" alt=\"페이스북\" /></a></li>\r\n\t\t\t\t<li><a href=\"https://twitter.com/edukocca\" target=\"_blank\"><img src=\"/images/main/sns-t.png\" alt=\"트위터\" /></a></li>\r\n\t\t\t\t<li><a href=\"http://blog.naver.com/kocca01\" target=\"_blank\"><img src=\"/images/main/sns-b.png\" alt=\"블로그\" /></a></li>\r\n\t\t\t\t<li><a href=\"http://www.youtube.com/KoreanContent\" target=\"_blank\"><img src=\"/images/main/sns-y.png\" alt=\"유투브\" /></a></li>\r\n\t\t\t</ul>\r\n\t\t</div>\r\n\t\t<!-- col2 -->\r\n\t\t<!-- col3 -->\r\n\t\t<div class=\"col3\">\r\n\t\t\t<a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp\"><img src=\"/images/main/f-app.png\" alt=\"모바일에서도 한국콘텐츠아카데미\"></a>\r\n\t\t</div>\r\n\t\t<!-- col3 -->\r\n\t</div>\r\n\t<!-- main-content3 -->\r\n</div>\r\n<!-- /main-container -->\r\n";
    private final static byte[]  _wl_block45Bytes = _getBytes( _wl_block45 );

    private final static String  _wl_block46 ="<!-- footer-new -->\r\n<div id=\"footer-new\">\r\n\t<!-- body -->\r\n\t<div class=\"body\">\r\n\t\t<!-- brand -->\r\n\t\t<div class=\"brand\">\r\n\t\t\t<img src=\"/images/common/brand-footer.png\" alt=\"한국콘텐츠아카데미\">\r\n\t\t</div>\r\n\t\t<!-- /brand -->\r\n\t\t<!-- info -->\r\n\t\t<div class=\"info\">\r\n\t\t\t<div class=\"link-group\">\r\n\t\t\t\t<a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome\">이용안내</a><span class=\"line\"> | </span>\r\n\t\t\t\t<a href=\"/servlet/controller.homepage.HomePageContactServlet?p_process=contactus\">제휴문의</a><span class=\"line\"> | </span>\r\n\t\t\t\t<a href=\"/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap\">사이트맵</a>\r\n\t\t\t\t<select onchange=\"fnMoveFamilySite(this.value);\">\r\n\t\t\t\t\t<option value=\"\">관련사이트</option>\r\n                    <option value=\"http://www.mcst.go.kr/\">문화체육관광부</option>\r\n                    <option value=\"http://www.kocca.kr/\">한국콘텐츠진흥원</option>\r\n                    <option value=\"http://library.kocca.or.kr/\">콘텐츠도서관</option>\r\n                    <option value=\"http://www.gamehub.or.kr/\">글로벌게임허브센터</option>\r\n                    <option value=\"http://www.culturecontent.com/\">문화콘텐츠닷컴</option>\r\n                    <!-- <option value=\"http://www.dccenter.kr/main_ft.jsp?syscd=FT\">콘텐츠이용보호센터</option> 2015-01-16 현재 홈페이지 서비스 불가 //-->\r\n                    <!-- <option value=\"http://www.contentright.or.kr/\">저작권위탁관리시스템</option> 2015-01-16 현재 홈페이지 내용이 상이함. 포털에서 검색되지 않음 //-->\r\n                    <option value=\"http://www.uci.or.kr/\">콘텐츠식별체계(UCI)</option>\r\n                    <option value=\"http://www.kgq.or.kr/\">게임국가자격기술검정</option>\r\n                    <option value=\"http://www.culturist.or.kr/\">종합인력정보시스템</option>\r\n\t\t\t\t</select>\r\n\t\t\t</div>\r\n\t\t\t<div class=\"copyright\">\r\n\t\t\t\t서울특별시 양천구 목동동로 203-1 방송회관 8층 | 사업자등록번호:117-82-04412<br>\r\n\t\t\t\tCOPYRIGHT&copy; 2013 KOCCA ALL RIGHTS RESERVED\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<!-- /info -->\r\n\t\t<!-- logo-group -->\r\n\t\t<div class=\"logo-group\">\r\n\t\t\t<a href=\"http://validator.w3.org/check?uri=http://edu.kocca.or.kr/servlet/controller.homepage.MainServlet\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_1.png\" alt=\"교육기부기관 인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/wa.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_2.gif\" alt=\"웹어워드코리아 클린콘텐츠 특별대상\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/wau.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_3.gif\" alt=\"웹 접근성 우수사이트 인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/elearning.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_4.gif\" alt=\"e러닝전문기관인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/i_safe.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_5.gif\" alt=\"개인정보보호안전 인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/eprivacy.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_6.gif\" alt=\"개인정보보호우수사이트 인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'http://edu.kocca.or.kr/contents/temp/gcs.html\',410,570,\'no\');\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_7.gif\" alt=\"콘텐츠제공서비스 품질인증\" /></a>\r\n\t\t\t<a href=\"javascript:pop_view(\'https://mark.inicis.com/mark/popup_v1.php?no=19844&amp;st=1322725008\',520,625,\'no\')\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_8.gif\" alt=\"이니시스 결제 시스템\" /></a>\r\n\t\t\t<a href=\"#\"><img src=\"http://edu.kocca.or.kr/images/2013/common/mark_9.gif\" alt=\"\" /></a>\r\n\t\t</div>\r\n\t\t<!-- /logo-group -->\r\n\t</div>\r\n\t<!-- /body -->\r\n</div>\r\n<!-- /footer-new -->\r\n<form name=\"familySiteform\" action=\"\" method=\"get\" target=\"_blank\">\r\n</form>\r\n</body>\r\n</html>";
    private final static byte[]  _wl_block46Bytes = _getBytes( _wl_block46 );

    private final static String  _wl_block47 ="\r\n\r\n";
    private final static byte[]  _wl_block47Bytes = _getBytes( _wl_block47 );

    private String subStringBytes(String str, int byteLength) {
        // String 을 byte 길이 만큼 자르기.

        int retLength = 0;
        int tempSize = 0;
        int asc;

        if(str == null || "".equals(str) || "null".equals(str)){
            str = "";
        }

        int length = str.length();

        for (int i = 1; i <= length; i++) {
            asc = (int) str.charAt(i - 1);
            if (asc > 127) {
                if (byteLength >= tempSize + 2) {
                    tempSize += 2;
                    retLength++;
                } else {
                    return str.substring(0, retLength) + "...";
                }
            } else {
                if (byteLength > tempSize) {
                    tempSize++;
                    retLength++;
                }
            }
        }

        return str.substring(0, retLength);
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

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    box.put("isMain", "Y");

    ArrayList<DataBox> mainBannerList = (ArrayList)request.getAttribute("mainBannerList");
    ArrayList<DataBox> mainNoticeList = (ArrayList)request.getAttribute("mainNoticeList");
    ArrayList<DataBox> popularKeywordList = (ArrayList)request.getAttribute("popularKeywordList");
    ArrayList<DataBox> academyStoryList = (ArrayList)request.getAttribute("academyStoryList");

    ArrayList<DataBox> noticeList = new ArrayList<DataBox>();
    ArrayList<DataBox> eventList = new ArrayList<DataBox>();

    DataBox dbox = null;

    StringBuffer mainBannerLgSB = new StringBuffer();
    StringBuffer mainBannerSB = new StringBuffer();
    for ( int i = 0 ; i < mainBannerList.size() ; i++ ) {
        dbox = (DataBox)mainBannerList.get(i);

            mainBannerLgSB.append("<li>");
            mainBannerLgSB.append("<a href=\"").append(dbox.getString("d_url")).append("\" target=\"").append(dbox.getString("d_url_target")).append("\"><img src=\"").append(dbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(dbox.getString("d_explain")).append("\" /></a>");
            mainBannerLgSB.append("</li>\n");

            mainBannerSB.append("<li>\n");
            mainBannerSB.append("<a href=\"#\" data-slide-index=\"").append(i).append("\">\n");
            mainBannerSB.append("   <span class=\"thumb\"><img src=\"").append(dbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(dbox.getString("d_explain")).append("\" /></span>\n");
            mainBannerSB.append("   <span class=\"text\">").append(dbox.getString("d_title")).append("</span>\n");
            mainBannerSB.append("</a>\n");
            mainBannerSB.append("</li>\n");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd");
    Date dt = new Date();
    String dd = sdf.format(dt);

            {_writeText(response, out, _wl_block0, _wl_block0Bytes);}

    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");

    boolean isUserLogin = ( !topWarp_userid.equals("") ) ? true : false;

    String titleStr = box.getString("title_str");
    titleStr = (titleStr == null || titleStr.equals("")) ? "한국콘텐츠아카데미" : titleStr + " - 한국콘텐츠아카데미";

            {_writeText(response, out, _wl_block1, _wl_block1Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( box.getString("og_title") 
));
            {_writeText(response, out, _wl_block2, _wl_block2Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( box.getString("og_url") 
));
            {_writeText(response, out, _wl_block3, _wl_block3Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( box.getString("og_description") 
));
            {_writeText(response, out, _wl_block4, _wl_block4Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( box.getString("og_image") 
));
            {_writeText(response, out, _wl_block5, _wl_block5Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( titleStr 
));
            {_writeText(response, out, _wl_block6, _wl_block6Bytes);}

    if ( box.getString("isMain").equals("Y") ) {

            {_writeText(response, out, _wl_block7, _wl_block7Bytes);}

    }

            {_writeText(response, out, _wl_block8, _wl_block8Bytes);}

        if(topWarp_userid.equals("")) {

            {_writeText(response, out, _wl_block9, _wl_block9Bytes);}

        } else {

            {_writeText(response, out, _wl_block10, _wl_block10Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf(topWarp_usernm
));
            {_writeText(response, out, _wl_block11, _wl_block11Bytes);}

        }

            {_writeText(response, out, _wl_block12, _wl_block12Bytes);}
            {_writeText(response, out, _wl_block13, _wl_block13Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( mainBannerLgSB.toString() 
));
            {_writeText(response, out, _wl_block14, _wl_block14Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( mainBannerSB.toString() 
));
            {_writeText(response, out, _wl_block15, _wl_block15Bytes);}

    String boardType = "";
    String gubunCode = "";
    String gubunName = "";
    String newYn = "";
    int seq = 0;

    for ( int i = 0 ; i < mainNoticeList.size() ; i++ ) {
        dbox = (DataBox)mainNoticeList.get(i);
        seq = dbox.getInt("d_seq");
        boardType = dbox.getString("d_board_type");
        gubunCode = dbox.getString("d_gubun_code");
        newYn = dbox.getString("d_new_yn");
        if ( boardType.equals("NOTICE") ) {
            noticeList.add( dbox );
            gubunName = (gubunCode.equals("N") ? "공지" : ( gubunCode.equals("G") ? "안내" : "정보" ) );
        } else {
            eventList.add( dbox );
            gubunName = "행사";
        }

        if ( i < 5 ) {

            {_writeText(response, out, _wl_block16, _wl_block16Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( boardType 
));
            {_writeText(response, out, _wl_block17, _wl_block17Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( seq 
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( gubunName 
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( this.subStringBytes( dbox.getString("d_title"), 36) 
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" 
));
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}

        }
    }

            {_writeText(response, out, _wl_block22, _wl_block22Bytes);}

    if ( noticeList != null && noticeList.size() > 0 ) {
        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            dbox = (DataBox)noticeList.get(i);
            seq = dbox.getInt("d_seq");
            gubunCode = dbox.getString("d_gubun_code");
            newYn = dbox.getString("d_new_yn");
            gubunName = gubunCode.equals("N") ? "공지" : ( gubunCode.equals("G") ? "안내" : "정보" );

            {_writeText(response, out, _wl_block23, _wl_block23Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( seq 
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( gubunName 
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( this.subStringBytes( dbox.getString("d_title"), 36) 
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" 
));
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}

        }
    } else {

            {_writeText(response, out, _wl_block24, _wl_block24Bytes);}

    }

            {_writeText(response, out, _wl_block25, _wl_block25Bytes);}

    if ( eventList != null && eventList.size() > 0 ) {
        for ( int i = 0 ; i < eventList.size() ; i++ ) {
            dbox = (DataBox)eventList.get(i);
            seq = dbox.getInt("d_seq");
            gubunCode = dbox.getString("d_gubun_code");
            newYn = dbox.getString("d_new_yn");
            gubunName = (gubunCode.equals("O") ) ? "진행" : "종료" ;

            {_writeText(response, out, _wl_block26, _wl_block26Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( seq 
));
            {_writeText(response, out, _wl_block18, _wl_block18Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( gubunName 
));
            {_writeText(response, out, _wl_block19, _wl_block19Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( this.subStringBytes( dbox.getString("d_title"), 36) 
));
            {_writeText(response, out, _wl_block20, _wl_block20Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" 
));
            {_writeText(response, out, _wl_block21, _wl_block21Bytes);}

        }
    } else {

            {_writeText(response, out, _wl_block27, _wl_block27Bytes);}

    }

            {_writeText(response, out, _wl_block28, _wl_block28Bytes);}

    for ( int i = 0 ; i < academyStoryList.size() ; i++ ) {
        dbox = (DataBox)academyStoryList.get(i);
        if ( i == 0 ) {

            {_writeText(response, out, _wl_block29, _wl_block29Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getInt("d_seq") 
));
            {_writeText(response, out, _wl_block30, _wl_block30Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( !dbox.getString("d_save_img_nm").equals("") ? dbox.getString("d_save_img_nm") : "/images/main/_thumb.gif"
));
            {_writeText(response, out, _wl_block31, _wl_block31Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_title") 
));
            {_writeText(response, out, _wl_block32, _wl_block32Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_title").replaceAll("\'\'", "\'") 
));
            {_writeText(response, out, _wl_block33, _wl_block33Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( this.subStringBytes( StringManager.removeHTML( StringManager.removeHTMLHead( dbox.getString("d_cont") ) ).replaceAll("&nbsp;", " ").replaceAll("<br>", ""), 170 ) 
));
            {_writeText(response, out, _wl_block34, _wl_block34Bytes);}

        } else {

            {_writeText(response, out, _wl_block35, _wl_block35Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getInt("d_seq") 
));
            {_writeText(response, out, _wl_block36, _wl_block36Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( !dbox.getString("d_save_img_nm").equals("") ? dbox.getString("d_save_img_nm") : "/images/main/_thumb.gif"
));
            {_writeText(response, out, _wl_block31, _wl_block31Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_title") 
));
            {_writeText(response, out, _wl_block37, _wl_block37Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_title").replaceAll("\'\'", "\'") 
));
            {_writeText(response, out, _wl_block38, _wl_block38Bytes);}

        }
    }

            {_writeText(response, out, _wl_block39, _wl_block39Bytes);}

    for ( int i = 0 ; i < popularKeywordList.size() ; i++ ) {
        dbox = (DataBox)popularKeywordList.get(i);

        if ( i != 0 && i % 4 == 0 ) {

            {_writeText(response, out, _wl_block40, _wl_block40Bytes);}

        }

            {_writeText(response, out, _wl_block41, _wl_block41Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_keyword") 
));
            {_writeText(response, out, _wl_block42, _wl_block42Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( dbox.getString("d_keyword") 
));
            {_writeText(response, out, _wl_block43, _wl_block43Bytes);}

    }

            {_writeText(response, out, _wl_block44, _wl_block44Bytes);}
            out.print( weblogic.utils.StringUtils.valueOf( Integer.parseInt(dd) % 2 
));
            {_writeText(response, out, _wl_block45, _wl_block45Bytes);}
            {_writeText(response, out, _wl_block46, _wl_block46Bytes);}
            {_writeText(response, out, _wl_block47, _wl_block47Bytes);}
        } catch (Throwable __ee){
            if(!(__ee instanceof javax.servlet.jsp.SkipPageException)) {
                while ((out != null) && (out != _originalOut)) out = pageContext.popBody(); 
                _releaseTags(_activeTag);
                pageContext.handlePageException(__ee);
            }
        }
    }
}
