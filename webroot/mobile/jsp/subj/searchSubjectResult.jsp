<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page errorPage="/jsp/library/error.jsp" %>

<%@ page import = "com.credu.library.DataBox" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "com.credu.mobile.subj.SubjectBean" %>

<%@ include file="/mobile/jsp/include/init_utf8.jsp" %>

<%
    request.setCharacterEncoding("utf-8");

    // String keyword = box.getString("keyword");
    String keyword = request.getParameter("keyword");
    System.out.println("keyword : " + keyword);
    DataBox dbox = null;

    String pageTitle = "검색결과";
    String subjFlag = "";

    String titleTag = "";

    if ( isApp ) {
        titleTag = "검색결과";
    } else if ( isWeb ) {
        titleTag = "검색결과 | 한국콘텐츠아카데미";
    }

    SubjectBean bean = new SubjectBean();

    ArrayList<DataBox> subjectList = bean.searchSubjectList(box, keyword);
    ArrayList<DataBox> onlineSubjList = new ArrayList<DataBox>();
    ArrayList<DataBox> openclassSubjList = new ArrayList<DataBox>();

    // ArrayList onlineSubjList = (ArrayList)request.getAttribute("onlineSubjList");
    // ArrayList openclassSubjList = (ArrayList)request.getAttribute("openclassSubjList");
    // %ED%95%A8
    // %EB%B0%A9%EC%86%A1
    response.setCharacterEncoding("utf-8");

    try {
        String specials = "";
        String bizType = "";

        for (int i = 0; i < subjectList.size(); i++) {
            dbox = ((DataBox) subjectList.get(i));
            subjFlag = dbox.getString("d_subj_flag");

            if (subjFlag.equals("ONLINE")) {
                onlineSubjList.add(dbox);
            } else if (subjFlag.equals("OPENCLASS")) {
                openclassSubjList.add(dbox);
            }
        }


%>
<!DOCTYPE html> 
<html> 
<head> 
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= titleTag %></title> 
<link href="/mobile/assets/css/style.css" rel="stylesheet">
<script src="/mobile/assets/js/jquery-1.11.1.min.js"></script>
<script src="/mobile/assets/js/common.js"></script>
<script src="/mobile/assets/js/openclass.js"></script>
</head>
<body>

<!-- header -->
<%@ include file="/mobile/jsp/include/header_utf8.jsp" %>
<!-- /header -->

<%@ include file="/mobile/jsp/include/slidemenu_utf8.jsp" %>

<div class="page-header">
    <h2>검색어: <%= keyword %></h2>
</div>

<%
isApp = true;
    if ( isApp ) {
%>
<h2 class="page-subject-bar">정규과정 (<%= onlineSubjList.size() %>)</h2>
<div class="container">
	<div class="panel">
<%
        if ( onlineSubjList.size() == 0 ) {
%>
		<p class="search-no-data">검색 결과가 없습니다.</p>
<%
        } else {
%>
		<ul class="thumb-list thumb-list-lecture">
<%
            for( int i = 0; i < onlineSubjList.size(); i++ ) {
                dbox = (DataBox)onlineSubjList.get(i);
                specials = dbox.getString("d_specials");
                bizType = dbox.getString("d_biz_type");
%>
			<li>
				<a href="#">
					<span class="thumb"><img src="<%= dbox.getString("d_subjfilenamenew") %>" alt=""></span>
					<span class="body">
						<span class="name"><%= dbox.getString("d_tutor_nm") %></span>
						<span class="text">
							<%= dbox.getString("d_subjnm") %>
						</span>
					</span>
					<span class="icon-group">
<%
                if (specials.substring(0, 1).equals("Y") ) {
%>
						<i class="icon icon-text-box icon-primary">신규</i>
<%
                }

                if ( !bizType.equals("") ) {
%>
						<i class="icon icon-text-box icon-info"><%= bizType.equals("F01") ? "기본" : "실무" %></i>
<%
                }
%>

					</span>
				</a>
			</li>
<%
            }
%>
		</ul>
<%
        }
%>
	</div>
</div>
<%
    }
%>

<h2 class="page-subject-bar">열린강좌  (<%= openclassSubjList.size() %>)</h2>
<div class="container">
	<div class="panel">
<%
        if ( openclassSubjList.size() == 0 ) {
%>
		<p class="search-no-data">검색 결과가 없습니다.</p>
<%
        } else {
%>
		<ul class="thumb-list thumb-list-lecture">
<%
            for( int i = 0; i < openclassSubjList.size(); i++ ) {
                dbox = (DataBox)openclassSubjList.get(i);
                specials = dbox.getString("d_specials");
%>
			<li>
				<a href="javascript:fnOpenClassViewDetail( 2, '<%= dbox.getString("d_subj") %>', '');">
					<span class="thumb"><img src="<%= dbox.getString("d_subjfilenamenew") %>" alt=""></span>
					<span class="body">
						<span class="name"><%= dbox.getString("d_tutor_nm") %></span>
						<span class="text">
							<%= dbox.getString("d_subjnm") %>
						</span>
					</span>
					<span class="icon-group">
<%
                if (specials.equals("Y") ) {
%>
						<i class="icon icon-text-box icon-primary">신규</i>
<%
                }
%>
					</span>
				</a>
			</li>
<%
            }
%>
		</ul>
<%
        }
%>
	</div>
</div>
</body>
</html>

<%
    } catch (Exception e) {
        System.out.println("Exception occured : " + e.getMessage());
    }
%>