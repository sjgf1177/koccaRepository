<!DOCTYPE html>
<%
//**********************************************************
//  1. 제      목: 과정게시판
//  2. 프로그램명: zu_StudyBoard_R.jsp
//  3. 개      요: 과정게시판 보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box     = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String  v_subj       = box.getString("p_subj");
    String  v_year       = box.getString("p_year");
    String  v_subjseq    = box.getString("p_subjseq");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int v_levels = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    i         = 0;
    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";
    String v_upfileview = "";

    Vector v_realfileVector = null;
    Vector v_savefileVector = null;


    DataBox dbox = (DataBox)request.getAttribute("selectStudyBoard");
    if (dbox != null ) {
        v_seq           = dbox.getInt("d_seq");
        v_userid        = dbox.getString("d_userid");
        v_name          = dbox.getString("d_name");
        v_title         = dbox.getString("d_title");
        v_content       = dbox.getString("d_content");
        v_cnt           = dbox.getInt("d_cnt");
        v_indate        = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");

//        v_content = StringManager.replace(v_content,"\r\n","<br>");
//        v_content = BoardBean.convertBody(v_content);

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

    // 리스트
    ArrayList list = (ArrayList)request.getAttribute("selectBoardReplyList");
    
    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    				scoredata	= bean.SelectEduScore(box);

%>
<html>
<head>
    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>한국콘텐츠아카데미</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <!--<link href="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" rel="stylesheet">-->
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <!--<script src="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
    
    <script type="text/javascript" src="/script/portal/common.js"></script>
	<script type="text/javascript">
	<!--
	    function go(index) {
	         document.form1.p_pageno.value = index;
	         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	         document.form1.p_process.value = "";
	         document.form1.submit();
	    }
	    function goPage(pageNum) {
	         document.form1.p_pageno.value = pageNum;
	         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	         document.form1.p_process.value = "";
	         document.form1.submit();
	    }
	    function reply_StudyBoard() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "replyPage";
	        document.form1.submit();
	    }
	
	    function modify_StudyBoard() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "updatePage";
	        document.form1.submit();
	    }
	
	    function delete_StudyBoard() {
	        if (confirm("정말로 삭제하시겠습니까?")) {
	            document.form1.p_pageno.value = "1";
	            document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	            document.form1.p_process.value = "delete";
	            document.form1.submit();
	        }
	        else {
	            return;
	        }
	    }
	
	    function list_StudyBoard() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "";
	        document.form1.submit();
	    }
	
	
	    function selectList() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "";
	        document.form1.p_pageno.value = "1";
	        document.form1.submit();
	    }
	
	    function select(seq, upfilecnt, userid) {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "select";
	        document.form1.p_seq.value = seq;
	        document.form1.p_upfilecnt.value = upfilecnt;
	        document.form1.p_userid.value    = userid;
	        document.form1.submit();
	    }
	
	    function reply_StudyBoardRow() {
	    	if (confirm("등록하시겠습니까?")) {
	    		document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	    		document.form1.p_process.value = "replySubmit";
	    		document.form1.submit();
	    	}
	    }
	
	    function del_replyStudyBoard(comseq, ruserid) {
	    	if (confirm("삭제하시겠습니까?")) {
	        	document.form1.r_userid.value = ruserid;
	        	document.form1.r_comseq.value = comseq;
	    		document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	    		document.form1.p_process.value = "replyDelete";
	    		document.form1.submit();
	    	}
	    }
	//-->
	</script>
</head>
<body id="pop_study">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq %>"/>
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>"/>
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>"/>
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>"/>
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>"/>
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>"/>
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>"/>
    <input type = "hidden" name = "p_subj"       value = "<%= v_subj %>"/>
    <input type = "hidden" name = "p_year"       value = "<%= v_year %>"/>
    <input type = "hidden" name = "p_subjseq"    value = "<%= v_subjseq %>"/>
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>"/>
	<!-- 리플 삭제시 사용 -->
    <input type = "hidden" name = "r_userid"/>
    <input type = "hidden" name = "r_comseq"/>
    
    <section>
        <div class="wrapper layer_board_table_wrap">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer_pop">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>게시판</span>
                                </div>
                                <div class="sub_contents_body">
<%	if (scoredata != null) { %>
                                    <div class="sub_info_body">
                                        <div class="sub_course_alert_box">
                                            <p><%= subjdata.getSubjnm() %></p>
                                        </div>
                                        <div class="sub_course_view_wrap" style="margin-bottom:50px;">
                                            <ul class="myCourseDetail" style="width:100%;">
                                                <li><span>교육기간</span> : <%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~ <%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></li>
                                            </ul>
                                        </div>
                                    </div>
<%	} %>
                                    <div class="sub_info_body">
                                        <table class="write_table th_align_left1">
                                            <colgroup>
                                                <col width="20%">
                                                <col width="auto">
                                                <col width="20%">
                                                <col width="auto">
                                            </colgroup>
                                            <tbody>

                                                <tr>
                                                    <th>제목</th>
                                                    <td colspan="3"><%= v_title %></td>
                                                </tr>
                                                <tr>
                                                    <th>내용</th>
                                                    <td colspan="4"><%= v_content.replaceAll("\r\n", "<br/>") %></td>
                                                </tr>
                                                <tr>
                                                    <th>작성자</th>
                                                    <td colspan="3"><%= v_name %></td>
                                                </tr>
                                                <tr>
                                                    <th>작성일</th>
                                                    <td><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
                                                </tr>
                                                <tr>
                                                    <th>조회수</th>
                                                    <td><%= v_cnt %></td>
                                                </tr>

<!-- 파일첨부 시작 -->
<%
	Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
	Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
%>
												<%@ include file="/learn/user/typeB/include/zu_MultiAttach_R.jsp" %>
<!-- 파일첨부 종료-->
                                            </tbody>
                                        </table>

                                    </div>
                                    <div class="myCourseBoardBtn">
                                        <%
                                            if( !box.getSession("userid").equals("") ) {
                                        %>
                                        <a href="javascript:reply_StudyBoard();">답변</a>
                                        <%
                                            }
                                            if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {
                                        %>
                                        <a href="javascript:modify_StudyBoard();">수정</a>
                                        <%
                                            }
                                            if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {
                                        %>
                                        <a href="javascript:delete_StudyBoard();">삭제</a>
                                        <%
                                            }
                                        %>
                                        <a href="javascript:selectList();">목록</a>
                                    </div>
                                    <div class="sub_contents_body sub_boarder_body" style="margin-top:50px;">
                                        <p class="sub_course_view_title" style="margin-bottom:0">댓글</p>
                                        <div class="command_box" style="margin-bottom:20px;">
                                            <textarea class="command_textarea" name="replyContent"></textarea>
                                            <a href="javascript:reply_StudyBoardRow();" class="btn_command">댓글 등록</a>
                                        </div>
                                        
                                        <table class="td_align_left2">
                                            <colgroup>
                                                <col width="15%">
                                                <col width="auto">
                                                <col width="15%">
                                                <col width="20%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>작성자</th>
                                                    <th>댓글</th>
                                                    <th class="pc_table">날짜</th>
                                                    <th></th>
                                                </tr>
                                            </thead>
                                            <tbody>
<% 
	int r_tabseq = 0;
	int r_seq = 0;
	int r_comseq = 0;
	String r_userid = "";
	String r_name = "";
	String r_content = "";
	String r_luserid = "";
	String r_ldate = "";
	
	for( i = 0; i < list.size(); i++ ) { 
		DataBox rbox = (DataBox)list.get(i);
		r_tabseq    = rbox.getInt("d_tabseq");
		r_seq       = rbox.getInt("d_seq");
		r_comseq    = rbox.getInt("d_comseq");
		r_userid    = rbox.getString("d_userid").trim();
		r_name      = rbox.getString("d_name");
		r_content   = rbox.getHtmlString("d_content");
		r_luserid   = rbox.getString("d_luserid");
		r_ldate     = rbox.getDate("d_ldate", ".");
%>
                                                <tr>
                                                    <td><%= r_name %><p class="mo_view"><%= r_ldate %></p></td>
                                                    <td><%= r_content %></td>
                                                    <td class="pc_table"><%= r_ldate %></td>
                                                    <td>
<%
		if( box.getSession("userid").trim().equals(r_userid) ) {
%>
                                                    	<a href="javascript:del_replyStudyBoard('<%=r_comseq %>','<%=r_userid %>');" class="command_delete" style="margin:0 auto">삭제</a>
<%
		}
%>
                                                    </td>
                                                </tr>
<%
	}
	
	if(i == 0){
%>
												<tr>
													<td colspan="4">등록된 댓글이 없습니다.</td>
												</tr>
<%
	}
%>
                                            </tbody>
                                        </table>
                                    </div>
                                    
                                </div>
<!--                                 <div class="popup_btnBox"> -->
<!--                                     <a href="#" class="btn_courseCancel">목록</a> -->
<!--                                 </div> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</form>
</body>
<style>

    table.td_align_left2{display: table;}
    table.td_align_left2 tbody{display: table-row-group;}
    table.td_align_left2 tr{display: table-row;}
    table.td_align_left2 th, table.td_align_left2 td{display: table-cell;}
    table.td_align_left2 td{word-break: break-all;}

    .layer_board_table_wrap table.td_align_left2 tr:first-child th, .layer_board_table_wrap table.td_align_left2 tr:first-child td{padding-top: 12px;}
</style>
</html>