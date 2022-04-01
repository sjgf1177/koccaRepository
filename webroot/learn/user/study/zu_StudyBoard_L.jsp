<!DOCTYPE html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int v_tabseq   = box.getInt("p_tabseq");
    String v_subj   = box.getString("p_subj");
    String v_year	= box.getString("p_year");
    String v_subjseq= box.getString("p_subjseq");

    int v_seq = 0;
    int v_levels = 0;
    int v_cnt = 0;
    int i     = 0;

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";
    String	v_replyTitle= "";

    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;

    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");

    String v_class1     = "";
    String v_class2     = "";
    
    ArrayList list = (ArrayList)request.getAttribute("selectStudyBoardList");
    
    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    				scoredata	= bean.SelectEduScore(box);
%>
<html>
<head>
    <meta charset="euc-kr" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>한국콘텐츠아카데미</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet" />
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" />
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet" />
    <link href="/common/css/header.css" rel="stylesheet" />
    <link href="/common/css/footer.css" rel="stylesheet" />
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
    
	<script type="text/javascript" src="/script/portal/common.js"></script>
	<script language="JavaScript">
	<!--
		//조회
	    function go(index) {
	         document.form1.p_pageno.value = index;
	         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	         document.form1.p_process.value = "";
	         document.form1.submit();
	    }
	    //페이지 이동
	    function goPage(pageNum) {
	         document.form1.p_pageno.value = pageNum;
	         document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	         document.form1.p_process.value = "";
	         document.form1.submit();
	    }
	    // 리스트
	    function selectList() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "";
	        document.form1.p_pageno.value = "1";
	        document.form1.submit();
	    }
	
		// 등록
	    function insertPage() {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "insertPage";
	        document.form1.submit();
	    }
	
		// 상세보기
	    function select(seq, upfilecnt, userid) {
	        document.form1.action = "/servlet/controller.study.StudyBoardServlet";
	        document.form1.p_process.value = "select";
	        document.form1.p_seq.value = seq;
	        document.form1.p_upfilecnt.value = upfilecnt;
	        document.form1.p_userid.value    = userid;
	        document.form1.submit();
	    }
	
	//-->
	</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
	<section>
        <div class="wrapper">
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
                                    <%} %>
                                    <div class="sub_boarder_body">
                                        <div class="board_search_box" style="text-align: right; margin-bottom:30px;">
                                            <form name="form1" method="post">
											    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>"/>
											    <input type = "hidden" name = "p_process"   value = ""/>
											    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>"/>
											    <input type = "hidden" name = "p_seq"       value = ""/>
											    <input type = "hidden" name = "p_upfilecnt" value = ""/>
											    <input type = "hidden" name = "p_userid"    value = ""/>
											    <input type = "hidden" name = "p_subj"      value = "<%= v_subj %>"/>
											    <input type = "hidden" name = "p_year"      value = "<%= v_year %>"/>
											    <input type = "hidden" name = "p_subjseq"   value = "<%= v_subjseq %>"/>
                                                <select name="p_search">
                                                    <option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>제목</option>
						                            <option value='name' <% if (v_search.equals("name")) out.print("selected"); %>>작성자</option>
						                            <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>>내용</option>
                                                </select>
                                                <input type="text" class="board_search" name="p_searchtext" value='<%=v_searchtext %>' onKeyPress="fnKeyPressEnter(event,'selectList');" title="검색어를 입력해주세요">
                                                <input type="button" class="btn_board_search" value="" onclick="javascript:selectList();" />
                                            </form>
                                        </div>
                                        <table>
                                            <colgroup>
                                                <col width="8%" />
                                                <col width="auto" />
                                                <col width="12%" />
                                                <col width="25%" />
                                                <col width="12%" />
                                                <col width="8%" />
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>번호</th>
                                                    <th>제목</th>
                                                    <th>작성자</th>
                                                    <th>작성일자</th>
                                                    <th>첨부</th>
                                                    <th>조회수</th>
                                                </tr>
                                            </thead>
                                            <tbody>
<%
	for(i = 0; i < list.size(); i++) {
		DataBox dbox = (DataBox)list.get(i);
		v_userid    = dbox.getString("d_userid");
		v_name      = dbox.getString("d_name");
		v_indate    = dbox.getString("d_indate");
		v_title     = dbox.getString("d_title");
		v_seq       = dbox.getInt("d_seq");
		v_cnt       = dbox.getInt("d_cnt");
		v_levels    = dbox.getInt("d_levels");
		v_upfilecnt = dbox.getInt("d_upfilecnt");
		v_dispnum   = dbox.getInt("d_dispnum");
		v_totalpage = dbox.getInt("d_totalpage");
		v_rowcount  = dbox.getInt("d_rowcount");
		v_replyTitle= "";
		    
		int iReplyPaddingLeft = 0;
		if(v_levels > 1){
			for(int j=1;j < v_levels;j++){
				iReplyPaddingLeft += 35;
			}
		}
		
		String sTrClass = "";
		String sTdStyle = "";
		if(iReplyPaddingLeft > 0){
			sTrClass = "class=\"recommand\"";
			sTdStyle = "style=\"background-position:"+(iReplyPaddingLeft-35)+"px center; padding-left:"+iReplyPaddingLeft+"px;\"";
		}
%>
                                                <tr <%=sTrClass %>>
                                                    <td><%= v_dispnum %></td>
                                                    <td <%=sTdStyle %>><a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a></td>
                                                    <td><%= v_name %></td>
                                                    <td><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
                                                    <td>
<%		if( v_upfilecnt>0){ %>
                                                    	<img src="/common/image/ico_file.gif" alt="첨부파일" />
<%		}%>
                                                    </td>
                                                    <td><%= v_cnt %></td>
                                                </tr>
<%  } if(i == 0){   %>    
												<tr> 
													<td class="board_text1" colspan="6">등록된 내용이 없습니다.</td>
												</tr>
<%    } %>
                                            </tbody>
                                        </table>
                                    </div>
                                    <%=PageUtil.typeB_printPageListDiv(v_totalpage, v_pageno, 0) %>
                                </div>
                                <div class="popup_btnBox">
                                    <a href="javascript:insertPage();" class="btn_write">글쓰기</a>
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