<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>
<%@ page import="com.credu.library.StringManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    box.put("isMain", "Y");

    ArrayList<DataBox> mainBannerList = (ArrayList)request.getAttribute("mainBannerList");
    ArrayList<DataBox> mainNoticeList = (ArrayList)request.getAttribute("mainNoticeList");
    ArrayList<DataBox> popularKeywordList = (ArrayList)request.getAttribute("popularKeywordList");
    // ArrayList<DataBox> academyStoryList = (ArrayList)request.getAttribute("academyStoryList");
    ArrayList<DataBox> mainCategoryList = (ArrayList)request.getAttribute("mainCategoryList");

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

    SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
    Date dt2 = new Date();
    String dd = sdf2.format(dt2);

    StringBuffer mainCategoryHeaderSB = new StringBuffer();
    StringBuffer mainCategoryBodySB = new StringBuffer();

    int prevCategorySeq = 0;
    int currCategorySeq = 0;
    int positionNum = 0;
    int itemCnt = 0;
    int rnk = 0;
    String layoutType = "";

    int bodyItemCount = 0;

    if (mainCategoryList != null && mainCategoryList.size() > 0)  {
        for( int i = 0 ; i < mainCategoryList.size(); i++ ) {
            dbox = (DataBox)mainCategoryList.get(i);
            currCategorySeq = dbox.getInt("d_category_seq");
            positionNum = dbox.getInt("d_position_num");
            itemCnt = dbox.getInt("d_item_cnt");
            rnk = dbox.getInt("d_rnk");
            layoutType = dbox.getString("d_layout_type");

            // header
            if ( i == 0 && prevCategorySeq != currCategorySeq ) {
                mainCategoryHeaderSB.append("<div class=\"header\">\n");
                mainCategoryHeaderSB.append("               <ul>\n");
                mainCategoryHeaderSB.append("                   <li class=\"active\"><a href=\"#\">").append(dbox.getString("d_category_nm")).append("</a></li>\n");
            }

            if ( i > 0 && prevCategorySeq != currCategorySeq ) {
                mainCategoryHeaderSB.append("                   <li><a href=\"#\">").append(dbox.getString("d_category_nm")).append("</a></li>\n");
            }

            if ( i == mainCategoryList.size() - 1 ) {
                // mainCategoryHeaderSB.append("                   <li><a href=\"#\">").append(dbox.getString("d_category_nm")).append("</a></li>\n");
                mainCategoryHeaderSB.append("               </ul>\n");
                mainCategoryHeaderSB.append("            </div>\n");
            }

            mainCategoryBodySB.append("\n");
            // body
            if ( prevCategorySeq != currCategorySeq ) {
                mainCategoryBodySB.append("            <!-- ").append(dbox.getString("d_category_nm")).append(" 시작 //-->\n");

                if ( i == 0 ) {
                    mainCategoryBodySB.append("            <div class=\"body\" style=\"display:block;\">\n");
                } else if ( i > 0 ) {
                    mainCategoryBodySB.append("            <div class=\"body\">\n");
                }
                bodyItemCount = 0;
            }

            if ( layoutType.equals("A") ) {
                if ( itemCnt > 0 ) {
                    if ( rnk == 1 ) {
                        mainCategoryBodySB.append("                <ul class=\"type1\">\n");
                    } 

                    if ( rnk > 0 && positionNum < 5 ) {
                        mainCategoryBodySB.append("                    <li>\n");
                        mainCategoryBodySB.append("                        <a href=\"" + dbox.getString("d_item_url") + "\">\n");
                        mainCategoryBodySB.append("                            <span class=\"thumb\"><img src=\"" + dbox.getString("d_thumb_file_nm") + "\" alt=\"" + dbox.getString("d_item_nm") + "\"></span>\n");
                        mainCategoryBodySB.append("                            <span class=\"subject\">" + dbox.getString("d_item_nm") + "</span>\n");
                        mainCategoryBodySB.append("                            <span class=\"text\"></span>\n");
                        mainCategoryBodySB.append("                        </a>\n");
                        mainCategoryBodySB.append("                    </li>\n");

                    }

                    if ( rnk == itemCnt ) {
                        mainCategoryBodySB.append("                </ul>\n");
                    }

                    if ( rnk > 0 && positionNum == 5 ) {
                        mainCategoryBodySB.append("                <div class=\"type1-lg\">\n");
                        mainCategoryBodySB.append("                    <a href=\"" + dbox.getString("d_item_url") + "\">\n");
                        mainCategoryBodySB.append("                        <span class=\"thumb\"><img src=\"" + dbox.getString("d_thumb_file_nm") + "\" alt=\"" + dbox.getString("d_item_nm") + "\"></span>\n");
                        mainCategoryBodySB.append("                        <span class=\"subject\">" + dbox.getString("d_item_nm") + "</span>\n");
                        mainCategoryBodySB.append("                        <span class=\"text\"></span>\n");
                        mainCategoryBodySB.append("                    </a>\n");
                        mainCategoryBodySB.append("                </div>\n");
                    }

                    // bodyItemCount++;
                } else {
                    mainCategoryBodySB.append("                <div style=\"width:100%; padding-top:20%; text-align:center; font-weight:bold; font-size:18px;\">등록된 과정이 없습니다</div>\n");
                }

            } else {
                if ( itemCnt > 0 ) {
                    if ( rnk == 1 ) {
                        mainCategoryBodySB.append("                <div class=\"type2-lg\">\n");
                        mainCategoryBodySB.append("                    <a href=\"" + dbox.getString("d_item_url") + "\">\n");
                        mainCategoryBodySB.append("                        <span class=\"thumb\"><img src=\"" + dbox.getString("d_thumb_file_nm") + "\" alt=\"" + dbox.getString("d_item_nm") + "\"></span>\n");
                        mainCategoryBodySB.append("                        <span class=\"con\">\n");
                        mainCategoryBodySB.append("                            <span class=\"subject\">" + dbox.getString("d_item_nm") + "</span>\n");
                        mainCategoryBodySB.append("                            <span class=\"text\">" + this.subStringBytes( dbox.getString("d_board_cont").replaceAll("&nbsp;", " ").replaceAll("<br>", ""), 170 ) + "</span>\n");
                        mainCategoryBodySB.append("                        </span>\n");
                        mainCategoryBodySB.append("                    </a>\n");
                        mainCategoryBodySB.append("                </div>\n");
                    }

                    if ( rnk == 2 ) {
                        mainCategoryBodySB.append("                <ul class=\"type2\">\n");
                    }

                    if ( rnk > 1 ) {
                        mainCategoryBodySB.append("                    <li>\n");
                        mainCategoryBodySB.append("                        <a href=\"" + dbox.getString("d_item_url") + "\">\n");
                        mainCategoryBodySB.append("                            <span class=\"thumb\"><img src=\"" + dbox.getString("d_thumb_file_nm") + "\" alt=\"" + dbox.getString("d_item_nm") + "\"></span>\n");
                        mainCategoryBodySB.append("                            <span class=\"subject\">" + dbox.getString("d_item_nm") + "</span>\n");
                        mainCategoryBodySB.append("                            <span class=\"text\"></span>\n");
                        mainCategoryBodySB.append("                        </a>\n");
                        mainCategoryBodySB.append("                    </li>\n");
                    }

                    if ( rnk == itemCnt ) {
                        mainCategoryBodySB.append("                </ul>\n");
                    }
                } else {
                    mainCategoryBodySB.append("                 <div style=\"width:100%; padding-top:20%; text-align:center; font-weight:bold; font-size:18px;\">등록된 과정이 없습니다</div>\n");
                }

            }


            if ( (i > 0 && rnk == itemCnt ) || itemCnt == 0) {
                mainCategoryBodySB.append("            </div>\n");
                mainCategoryBodySB.append("            <!-- ").append(dbox.getString("d_category_nm")).append(" 끝 //-->\n");
            }

            prevCategorySeq = currCategorySeq;
        }
    } else {
    }
%>
<%@ include file="/learn/user/2013/portal/include/header.jsp" %>
<!-- main-container -->
<div id="main-container">
    <!-- main-content1 -->
    <div class="main-content1">
        <!-- main-banner -->
        <div class="main-banner">
            <div class="header">
                <ul id="mainBannerLg">
                    <%= mainBannerLgSB.toString() %>
                </ul>
            </div>
            <div class="body">
                <ul id="mainBanner">
                    <%= mainBannerSB.toString() %>
                </ul>
            </div>
        </div>
        <!-- /main-banner -->
        <!-- side -->
        <div class="side">
            <!-- lt-tab -->
            <div class="lt-tab">
                <div class="header">
                    <h3>새소식</h3>
                    <ul>
                        <li class="active"><a href="#">전체</a></li>
                        <li><a href="#">공지</a></li>
                        <li><a href="#">이벤트</a></li>
                        <!-- <li><a href="#">보도자료</a></li> //-->
                    </ul>
                    <a href="#" class="more" style="display:none;"><img src="/images/common/btn-more.png" alt="더 보기"></a>
                </div>
                <!-- 전체 탭 목록 시작 //-->
                <div class="body">
                    <ul>
<%
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
%>
                        <li><a href="#" onclick="fnViewBoardDetail('<%= boardType %>', <%= seq %>);"><strong>[<%= gubunName %>]</strong> <%= this.subStringBytes( dbox.getString("d_title"), 36) %></a> <%= newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" %></li>
<%
        }
    }
%>
                    </ul>
                </div>
                <!-- 전체 탭 목록 종료 //-->

                <!-- 공지사항 탭 목록 시작 //-->
                <div class="body">
                    <ul>
<%
    if ( noticeList != null && noticeList.size() > 0 ) {
        for ( int i = 0 ; i < noticeList.size() ; i++ ) {
            dbox = (DataBox)noticeList.get(i);
            seq = dbox.getInt("d_seq");
            gubunCode = dbox.getString("d_gubun_code");
            newYn = dbox.getString("d_new_yn");
            gubunName = gubunCode.equals("N") ? "공지" : ( gubunCode.equals("G") ? "안내" : "정보" );
%>
                        <li><a href="#" onclick="fnViewBoardDetail('NOTICE', <%= seq %>);"><strong>[<%= gubunName %>]</strong> <%= this.subStringBytes( dbox.getString("d_title"), 36) %></a> <%= newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" %></li>
<%
        }
    } else {
%>
                        <li><a href="#">등록된 공지사항이 없습니다.</li>
<%
    }
%>
                    </ul>
                </div>
                <!-- 공지사항 탭 목록 끝 //-->

                <!-- 이벤트 탭 목록 시작 //-->
                <div class="body">
                    <ul>
<%
    if ( eventList != null && eventList.size() > 0 ) {
        for ( int i = 0 ; i < eventList.size() ; i++ ) {
            dbox = (DataBox)eventList.get(i);
            seq = dbox.getInt("d_seq");
            gubunCode = dbox.getString("d_gubun_code");
            newYn = dbox.getString("d_new_yn");
            gubunName = (gubunCode.equals("O") ) ? "진행" : "종료" ;
%>
                        <li><a href="#" onclick="fnViewBoardDetail('EVENT', <%= seq %>);"><strong>[<%= gubunName %>]</strong> <%= this.subStringBytes( dbox.getString("d_title"), 36) %></a> <%= newYn.equals("Y") ? "<img src=\"/images/common/icon-n.png\" alt=\"신규\"/>" : "" %></li>
<%
        }
    } else {
%>
                        <li><a href="#">현재 진행중인 이벤트가 없습니다.</li>
<%
    }
%>
                    </ul>
                </div>
                <!-- 이벤트 탭 목록 끝 //-->

                <!-- 보도자료 탭 목록
                <div class="body">
                    <ul>
                        <li><a href="#"><strong>[444]</strong> 제목 입니다 <img src="/images/common/icon-n.png" alt="N"></a></li>
                    </ul>
                </div>
                //-->
            </div>
            <!-- /lt-tab -->
            <div class="banner">
                <a href="http://www.ckl.or.kr/" target="_blank" title="새창으로 열립니다."><img style="border-style:solid; border-color:#dedede; border-width:1px;" src="/images/main/bnr.jpg" alt="Content Korea Lab. 대한민국 콘텐츠 창작지원 콘텐츠코리아랩이 책임지겠습니다."></a>
            </div>
        </div>
        <!-- /side -->
    </div>
    <!-- /main-content1 -->
    <!-- main-content2 -->
    <div class="main-content2">
        <!-- lt-wz-group -->
        <div class="lt-wz-group">
            <%= mainCategoryHeaderSB.toString() %>
            <%= mainCategoryBodySB.toString() %>
        </div>
        <!-- /lt-wz-group -->
        <!-- side -->
        <div class="side">

            <div class="search">
                <div class="field">
                    <input type="text" class="mainSearchInput" id="topWarp_searchText" name="topWarp_searchText" placeholder="과정검색" onkeypress="searchtotal_enter(event);" style="font-weight:bold; color:#808080;" />
                    <a href="#" onclick="totalSubjSearch1();"><input type="image" src="/images/common/btn-search.png" alt="과정검색"/></a>
                </div>

                <div class="word">
                    <ul id="searchWord">
                        <li>
<%
    for ( int i = 0 ; i < popularKeywordList.size() ; i++ ) {
        dbox = (DataBox)popularKeywordList.get(i);

        if ( i != 0 && i % 4 == 0 ) {
%>
                        </li>
                        <li>
<%
        }
%>
                            <span><a href="javascript:fnSearchSubjByKeyword('<%= dbox.getString("d_keyword") %>');"><%= dbox.getString("d_keyword") %></a></span>
<%
    }
%>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- lt-tab -->
            <!--
            <div class="lt-tab">
                <div class="header">
                    <h3>게시판</h3>
                    <ul>
                        <li class="active"><a href="#">Best 후기</a></li>
                        <li><a href="#">자료</a></li>
                        <li><a href="#">질문</a></li>
                    </ul>
                    <a href="#" class="more"><img src="/images/common/btn-more.png" alt="MORE"></a>
                </div>
                <div class="body">
                    <ul>
                        <li><a href="#"><strong>[행사]</strong> 제목 입니다 <img src="/images/common/icon-n.png" alt="N"></a></li>
                        <li><a href="#"><strong>[행사]</strong> 제목 입니다</a></li>
                        <li><a href="#"><strong>[행사]</strong> 제목 입니다</a></li>
                        <li><a href="#"><strong>[행사]</strong> 제목 입니다</a></li>
                        <li><a href="#"><strong>[행사]</strong> 제목 입니다</a></li>
                    </ul>
                </div>
                <div class="body">
                    <ul>
                        <li><a href="#"><strong>[222]</strong> 제목 입니다 <img src="/images/common/icon-n.png" alt="N"></a></li>
                    </ul>
                </div>
                <div class="body">
                    <ul>
                        <li><a href="#"><strong>[333]</strong> 제목 입니다 <img src="/images/common/icon-n.png" alt="N"></a></li>
                    </ul>
                </div>
            </div>
            //-->
            <!-- /lt-tab -->

            <div class="banner">
                <a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR"><img src="/images/main/banner_0<%= Integer.parseInt(dd) % 2 %>.jpg" width="280" height="96" alt="홍보영상 바로보기"></a>
            </div>
            <div class="banner">
                <a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><img src="/images/main/bn-apply.jpg" alt="온라인교육 수강신청"></a>
            </div>
            <ul class="quick">
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService"><img src="/images/main/q1-off.png" class="ro" alt="원격지원 서비스"></a></li>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome"><img src="/images/main/q2-off.png" class="ro" alt="학습 가이드"></a></li>
                <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList"><img src="/images/main/q3-off.png" class="ro" alt="자주하는 질문"></a></li>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><img src="/images/main/q4-off.png" class="ro" alt="수료증 출력"></a></li>
            </ul>
        </div>
        <!-- /side -->
    </div>
    <!-- main-content2 -->
    <!-- main-content3 -->
    <div class="main-content3">
        <!-- col1 -->
        <div class="col1">
            <img src="/images/main/callcenter.jpg" alt="한국콘텐츠아카데미 교육문의 연락처입니다. 현장 교육 문의는 지역번호 02, 국번 3219, 번호 6540이며, 온라인 교육 문의는 지역번호 02, 국번 3219, 번호 5483입니다.">
        </div>
        <!-- col1 -->
        <!-- col2 -->
        <div class="col2">
            <ul>
                <li><a href="https://www.facebook.com/edukocca" target="_blank"><img src="/images/main/sns-f.png" alt="페이스북" /></a></li>
                <li><a href="https://twitter.com/edukocca" target="_blank"><img src="/images/main/sns-t.png" alt="트위터" /></a></li>
                <li><a href="http://blog.naver.com/kocca01" target="_blank"><img src="/images/main/sns-b.png" alt="블로그" /></a></li>
                <li><a href="http://www.youtube.com/KoreanContent" target="_blank"><img src="/images/main/sns-y.png" alt="유투브" /></a></li>
            </ul>
        </div>
        <!-- col2 -->
        <!-- col3 -->
        <div class="col3">
            <a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp"><img src="/images/main/f-app.png" alt="모바일에서도 한국콘텐츠아카데미"></a>
        </div>
        <!-- col3 -->
    </div>
    <!-- main-content3 -->
</div>
<!-- /main-container -->
<%@ include file="/learn/user/2013/portal/include/footer.html" %>

<%!
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
%>