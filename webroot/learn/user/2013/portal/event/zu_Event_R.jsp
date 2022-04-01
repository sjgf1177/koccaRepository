<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.library.FormatDate" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box =(RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","5");
	box.put("submenu","7");

	String vv_email = null;
	String vv_handphone = null;
	
	//EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
	
	vv_email = box.getSession("email");
	vv_handphone = box.getSession("handphone");
	//if(!box.getSession("email").equals("")) vv_email = box.getSession("email");//encryptUtil.decrypt(box.getSession("email"));
    //if(!box.getSession("handphone").equals("")) vv_handphone = encryptUtil.decrypt(box.getSession("handphone"));
    
    String  v_process = box.getString("p_process");
    int     v_pageno = box.getInt("p_pageno");
    
    String s_userid = box.getSession("userid");
    String s_usernm = box.getSession("name");
    String s_email = box.getSession("email");
    String s_handphone = box.getSession("handphone");
    String s_gender  	= box.getSession("gender");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search = box.getString("p_search");
    
    int v_seq = box.getInt("p_seq");

    int    v_cnt = 0;            // 조회수
    String v_gubun = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    
    String v_enddate = "";           // 팝업공지종료일
    String v_title = "";           // 제목
    String v_content = "";           // 내용
    String v_date = "";           // 등록일
    String v_name = "";           // 등록자
    String v_userid = "";
    String v_isanswer = "";           // 답변형 설정 여부
    String v_apply_yn = "";
    String v_status = "";
    String v_periodyn = "";
    String v_periodtxt = "";

    DataBox dbox =(DataBox)request.getAttribute("selectView");

    if(dbox != null) {
    	
    	v_gubun = dbox.getString("d_gubun");            
        v_startdate = dbox.getString("d_strdate"); 
        v_enddate = dbox.getString("d_enddate");   
        v_title = dbox.getString("d_title");  
        v_content = dbox.getString("d_content");
        v_date = dbox.getString("d_indate");   
        v_name = dbox.getString("d_name");
        v_userid = dbox.getString("d_userid");   
        v_cnt = dbox.getInt("d_cnt");      
        v_isanswer = dbox.getString("d_isanswer");   
        v_apply_yn = dbox.getString("d_apply_yn");
        v_periodyn = dbox.getString("d_periodyn");

    }

    if (v_periodyn.equals("Y")){
        v_periodtxt = "진행중";
    } else {
        v_periodtxt = "종료";
    }
    
    
    box.put("title_str", "이벤트 - 새소식");

%>
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
    $(function() {
        $(document.body).css("font-family", "돋움,Dotum,AppleGothic,sans-serif").css("font-size", "12px");
    });

    function listnotice(){
        document.nform2.p_process.value = "selectList";
        document.nform2.action = "/servlet/controller.homepage.EventHomePageServlet";
        document.nform2.submit();
    }

    function applyEvent() {
        <% if(v_apply_yn.equals("Y")){ %>
             alert("이미 신청하셨습니다.");
        <% } else { %>

        var bRtn = confirm("등록하시겠습니까?");

        if(bRtn) {
	        document.nform2.action="/servlet/controller.homepage.EventHomePageServlet";
	        document.nform2.p_process.value = "insertRequest";
	        document.nform2.submit();
        }
        <% } %>
    }
    

  //덧글 삭제
  function deleteComment(v_commentseq,v_indate) {
  	if(confirm("정말로 응모글을  삭제하시겠습니까?")) {
  		document.nform2.p_commentseq.value = v_commentseq;
  		document.nform2.p_commentindate.value = v_indate;
  		document.nform2.action = "/servlet/controller.homepage.EventHomePageServlet";
  		document.nform2.p_process.value = "deleteComment";
  		document.nform2.submit();
  	}
  	else {
  		return;
  	}
  }
  
    //덧글 수정
    function updateComment(v_commentseq,v_indate) {
  	    if(confirm("정말로 응모글을 수정하시겠습니까?")) {
            document.nform2.p_commentseq.value = v_commentseq;
            document.nform2.p_commentindate.value = v_indate;
            document.nform2.action = "/servlet/controller.homepage.EventHomePageServlet";
            document.nform2.p_process.value = "updateCommentPage";
            document.nform2.submit();
  	    } else {
  		    return;
  	    }
    }

</SCRIPT>
<!-- 스크립트영역종료 -->
<!-- container-new 시작 //-->
<div id="container-new">

<!-- Form 영역 시작 -->
<form name="nform2" method="post" action="">
    <input type = "hidden" name = "p_process" >
    <input type = "hidden" name = "p_seq" value="<%=v_seq%>">
    <input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_commentseq" value = "">
    <input type = "hidden" name = "p_commentindate" value = "">
    <input type = "hidden" name = "p_pageno" value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_search" value = "<%=v_search %>">
    <input type = "hidden" name = "p_gender" value = "<%=s_gender %>">

    <div id="mainMenu">
        <!-- 온라인 교육 -->
        <ul class="location">
            <li>홈</li>
            <li>새소식</li>
            <li>이벤트 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active" 삽입 -->
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List" >공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.EventHomePageServlet?p_process=selectList" class="active">이벤트</a></li>
                    <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                    <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
                    <!-- 2015-01-09 개편삭제
                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                    <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" class="active">과정안내</a></li>
                    <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">커뮤니티</a></li>
                    -->
                </ul>
            </li>
        </ul>

        <!-- 서브메인용 -->
<!-- 2015-01-07 개편삭제
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">과정안내</a></li>
                <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><strong><u>커뮤니티</u></strong></a></li>
            </ul>
        </div>
-->
        <!-- //서브메인용 -->
    </div>

    <div class="content">
        <h2 class="subTit">이벤트 </h2>

        <div id="subCont">
            <h3 class="blind">전체 현재위치</h3>
            <div class="noticBox">
                콘텐츠아카데미에서 진행되는 다양한 이벤트 소식을 전해드립니다.
            </div>
            <div class="article">
                <div class="info">
                    <div style="font-size: 13px;font-weight: bold;"><span><img src="/images/2013/common/<%= v_periodyn.equals("Y")? "event_01.png" : "event_02.png"%>" style="margin-bottom: -3px;"></span>
                    <%= v_title %></div>
                    <div style="padding-top:8px; color : #878787;"><%=FormatDate.getFormatDate(v_startdate, "yyyy.MM.dd")%> ~ <%=FormatDate.getFormatDate(v_enddate, "yyyy.MM.dd")%></div>
                    <!-- <p class="curriculum"></p> //-->
                </div>
                <div class="info2">
<%
/*        if( !fileNm.equals("") ) {
%>
                    
                    <p class="file">
                    <!-- 첨부파일 위치
                    <a href="/servlet/controller.library.DownloadServlet?p_savefile=<%= saveFileNm %>&p_realfile=<%= fileNm %>"><%= fileNm %></a><input type="hidden" name="p_savefile" value="<%= saveFileNm %>" />
                    //-->
                    </p>
<%
        }
*/
%>
                </div>

                <div class="inner">
                    <%= v_content %>
                </div>

            </div>
            <!-- //article -->

            <div class="btnArea right">
                <a href="javascript:listnotice()();"><img src="/images/2013/btn/btn_list.gif" alt="목록" /></a>

            </div>
        <!---------------------------------------------------------------------------------------------------------------------------------------------------//-->
        
       
        </div>
	</div>
</form>

</div>
<!--  //container-new 끝 //-->
<%@ include file="/learn/user/2013/portal/include/footer.html"%>
