<!DOCTYPE html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gadminYn = box.getString("p_gadminYn");    //관리자여부
    String  v_subj     = box.getString("p_subj");        //과정코드
    String  v_year     = box.getString("p_year");        //년도
    String  v_subjseq  = box.getString("p_subjseq");     //과정 차수
    String  v_lesson   = box.getString("p_lesson");
    String  v_lessonnm = box.getString("p_lessonnm");
    int     v_upfilecnt = 0;
    String  v_title="", v_jikwinm="",v_cono="", v_inuserid="", v_userid="",v_inusernm="";
    String  v_indate="";
    String  v_isopen="";
    int   v_seq = 0, v_anscnt = 0 ;
    int  v_qcnt     = 0;
    int  v_nanscnt	=0;
    int     i            = 0;
    int	v_cnt = 0;
    int	v_dispnum = 0;
    int	v_rowcount = 1;

    v_userid= box.getSession("userid");

    // 페이징관련
    int v_totalpage = 0;
    int row      = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_pageno = box.getInt("p_pageno");   
	if (v_pageno == 0) v_pageno = 1;
	
    ArrayList list1  = (ArrayList)request.getAttribute("SubjQnaList");

    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    box.put("needNoDetailRead", "true");
    box.put("p_accessGubun","subjQna");
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
	    // 등록
	    function qna_insert() {
	  		document.ff.target = "_self";
	        document.ff.p_process.value = 'SubjQnaInsertPage';
	        document.ff.submit();
	    }
	
	    // 상세보기
	    function qna_select(seq,isopen,userid,s_userid,gadminyn) {
	        if((isopen=="Y") || (userid == s_userid) || (gadminyn == "1")){
	            qna_read(seq);
	        } else {
	            alert("비공개입니다.");
	        }
	    }
	    
	    // 상세보기
	    function qna_read(seq) {
	        document.ff.target = "_self";
	        document.ff.p_process.value = 'SubjQnaDetail';
	        document.ff.p_seq.value = seq;
	        document.ff.submit();
	    }
	    
	    // 검색 
	    function qna_search() {
	        document.ff.target = "_self";
	        document.ff.p_process.value = 'SubjQnaList';
	        document.ff.p_pageno.value = "1";
	        document.ff.submit();
	    }
	
	    //페이지 이동
	    function goPage(pageNum) {
	    
	    	document.ff.p_pageno.value = pageNum;
	    	document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
	    	document.ff.p_process.value = "SubjQnaList";
	    	document.ff.submit();
	    }
	</script>
</head>
<body id="pop_study">
    <section>
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer_pop">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>과정 질문방</span>
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
                                    <div class="sub_boarder_body">
                                        <div class="board_search_box" style="text-align: right; margin-bottom:30px;">
                                            <form name="ff" method="post" action="/servlet/controller.study.SubjQnaStudyServlet">
												<input type="hidden" name="p_process"  value="SubjQnaList"/>
												<input type="hidden" name="p_subj"     value="<%=v_subj%>"/>
												<input type="hidden" name="p_year"     value="<%=v_year%>"/>
												<input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>"/>
												<input type="hidden" name="p_lesson"   value="<%=v_lesson%>"/>
												<input type="hidden" name="p_lessonnm" value="<%=v_lessonnm%>"/>
												<input type="hidden" name="p_seq"      value=""/>
												<input type="hidden" name="p_gadminYn" value="<%=v_gadminYn%>"/>
										        <input type = "hidden" name = "p_pageno"  value = "<%=v_pageno%>"/>
                                                <select name="p_search">
						                            <option value="title" <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
						                            <option value="content" <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
						                            <option value="name" <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
                                                </select>
                                                <input type="text" name="p_searchtext" class="board_search" title="검색어를 입력해주세요" value='<%=v_searchtext %>' onKeyPress="fnKeyPressEnter(event,'qna_search');">
                                                <input type="button" class="btn_board_search" value="" onclick="javascript:qna_search();">
                                            </form>
                                        </div>
                                        <table>
                                            <colgroup>
                                                <col width="8%">
                                                <col width="auto">
                                                <col width="12%">
                                                <col width="20%">
                                                <col width="12%">
                                                <col width="12%">
                                                <col width="8%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>번호</th>
                                                    <th>제목</th>
                                                    <th>작성자</th>
                                                    <th>작성일자</th>
                                                    <th>답변여부</th>
                                                    <th>첨부</th>
                                                    <th>조회수</th>
                                                </tr>
                                            </thead>
                                            <tbody>
<%
	for(i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);              
		v_totalpage = dbox.getInt("d_totalpage");

		v_seq     = dbox.getInt("d_seq");
		v_title   = dbox.getString("d_title");
		v_jikwinm = dbox.getString("d_jikwinm");
		v_cono    = dbox.getString("d_cono");
		v_inuserid= dbox.getString("d_inuserid");
		v_inusernm= dbox.getString("d_name");
		v_indate  = FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy.MM.dd");
		v_anscnt  = dbox.getInt("d_anscnt");
		v_isopen  = StringManager.chkNull(dbox.getString("d_isopen"));
		v_upfilecnt = dbox.getInt("d_upfilecnt");
		v_cnt		= dbox.getInt("d_cnt");
		v_dispnum		= dbox.getInt("d_dispnum");
%>
                                                <tr>
                                                    <td><%= v_dispnum %></td>
                                                    <td><a href="javascript:qna_select(<%=v_seq%>, '<%=v_isopen%>', '<%=v_inuserid%>', '<%=v_userid%>', '<%=v_gadminYn%>');"><%= v_title %></a></td>
                                                    <td><%= v_inusernm %></td>
                                                    <td><%= v_indate %></td>
                                                    <td><% if(v_anscnt > 0 ){ out.println("<span class=\"point_blue\">답변완료</span>"); } else { out.println("<span class=\"point_orange\">답변준비중</span>"); }%></td>
                                                    <td><% if( v_upfilecnt>0){ %><img src="/common/image/ico_file.gif" alt="첨부파일"><% } %></td>
                                                    <td><%= v_cnt %></td>
                                                </tr>
<%
	}

	if(i == 0){   %>    
                                                <tr>
                                                    <td colspan="7">등록된 내용이 없습니다.</td>
                                                </tr>
<%	} %>
                                            </tbody>
                                        </table>
                                    </div>
                                    <%= PageUtil.typeB_printPageListDiv(v_totalpage, v_pageno, 10) %>
                                </div>
                                <div class="popup_btnBox">
                                    <a href="javascript:qna_insert();" class="btn_write">등록</a>
                                    <a href="javascript:top.window.close();" class="btn_courseCancel">닫기</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>