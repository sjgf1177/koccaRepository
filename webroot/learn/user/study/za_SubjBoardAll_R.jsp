<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>게시판</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="JavaScript">
<!--
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
         document.form1.p_process.value = "";
         document.form1.submit();
    }
    function reply_StudyBoard() {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }

    function modify_StudyBoard() {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_StudyBoard() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.p_pageno.value = "1";
            document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_StudyBoard() {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
    }


    function selectList() {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "";
        document.form1.p_pageno.value = "1";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

    function reply_StudyBoardRow() {
    	if (confirm("등록하시겠습니까?")) {
    		document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    		document.form1.p_process.value = "replySubmit";
    		document.form1.submit();
    	}
    }

    function del_replyStudyBoard(comseq, ruserid) {
    	if (confirm("삭제하시겠습니까?")) {
        	document.form1.r_userid.value = ruserid;
        	document.form1.r_comseq.value = comseq;
    		document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
    		document.form1.p_process.value = "replyDelete";
    		document.form1.submit();
    	}
    }
//-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
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

	<!-- 검색조건 복원용 -->
    <input type = "hidden" name = "s_grcode" value="<%= box.get("s_grcode") %>"/>
    <input type = "hidden" name = "s_gyear" value="<%= box.get("s_gyear") %>"/>
    <input type = "hidden" name = "s_grseq" value="<%= box.get("s_grseq") %>"/>
    <input type = "hidden" name = "s_upperclass" value="<%= box.get("s_upperclass") %>"/>
    <input type = "hidden" name = "s_middleclass" value="<%= box.get("s_middleclass") %>"/>
    <input type = "hidden" name = "s_lowerclass" value="<%= box.get("s_lowerclass") %>"/>
    <input type = "hidden" name = "s_subjcourse" value="<%= box.get("s_subjcourse") %>"/>
    <input type = "hidden" name = "s_subjseq" value="<%= box.get("s_subjseq") %>"/>
    <input type = "hidden" name = "s_subjsearchkey" value="<%= box.get("s_subjsearchkey") %>"/>

	<!-- 리플 삭제시 사용 -->
    <input type = "hidden" name = "r_userid"/>
    <input type = "hidden" name = "r_comseq"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit6.gif" alt="게시판" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= dbox.get("d_subjnm") %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(dbox.get("d_edustart"),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(dbox.get("d_eduend"),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
				
				<div class="content">
					<div class="h3_wrap">
						<h3><img src="/images/portal/myclass/study/pop_stit8.gif" alt="게시판" /></h3>
					</div>
					
					<table class="study_view">
					<!--[if ie]><colgroup><col width="50px" /><col width="345px" /><col width="50px" /><col width="80px" /><col width="25px" /><col width="80px" /></colgroup><![endif]-->
					<colgroup><col width="70px" /><col width="345px" /><col width="70px" /><col width="80px" /><col width="45px" /><col width="80px" /></colgroup>
					<tr>
						<th>제목</th>
						<td colspan="5" class="tit"><%= v_title %></td>
					</tr>
					<tr>
						<th>작성자</th>
						<td><%= v_name %></td>
						<th>작성일</th>
						<td class="num"><%= FormatDate.getFormatDate(v_indate, "yyyy.MM.dd") %></td>
						<th>조회</th>
						<td class="num"><%= v_cnt %></td>
					</tr>
					<tr>
						<td colspan="6" class="con"><%= v_content %></td>
					</tr>
					</table>

            <!-- 파일첨부 시작 -->
            <%
                Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                String i_boardStyle        = "study_view";                                // 보드 스타일
            %>
            <%@ include file="/learn/user/include/zu_MultiAttach_R.jsp" %>
            <!-- 파일첨부 종료-->
		
					<p class="list_btn">
<%  
//    if(box.getBoolean("p_isReply")) {    //    답변 가능여부 
//      if(BulletinManager.isAuthority(box, box.getString("p_canReply"))) {    //    답변 여부
	if( !box.getSession("userid").equals("") ) {
%>              
						<a href="javascript:reply_StudyBoard()" class="btn_gr"><span>답변</span></a>
<%
//      }
//    }
	}
%>
<%  if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    수정 여부 %>
						<a href="javascript:modify_StudyBoard()" class="btn_gr"><span>수정</span></a>
<%  
    }

    if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {    //    삭제 여부 
%>
						<a href="javascript:delete_StudyBoard()" class="btn_gr"><span>삭제</span></a>
<%  }  %>                
						<a href="javascript:selectList()" class="btn_gr"><span>목록</span></a>
					</p>
					
					<br/>
					
					<div>
						<input type='text' class='inbox' name='replyContent' style='width:500px;height:20px;'/> 
						<a href="javascript:reply_StudyBoardRow()" class="btn_gr"><span>댓글 등록</span></a>
					</div>
					
					<br/>
					
					<table class="study_view">
						<colgroup><col width="10%" /><col width="70%" /><col width="10%" /><col width="10%" /></colgroup>
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
							<td><%= r_name %></td>
							<th><%= r_content %></th>
							<td class="num"><%= r_ldate %></td>
							<th>
							<% if( box.getSession("userid").trim().equals(r_userid) ) { %>
								<p class="list_btn"><a href="javascript:del_replyStudyBoard('<%=r_comseq %>','<%=r_userid %>')" class="btn_gr"><span>삭제</span></a></p>
							<% } else { %>
								&nbsp;
							<% } %>
							</th>
						</tr>
				<% } 
					if( i == 0 ) {
				%>
						<tr>
							<td colspan="4">등록된 댓글이 없습니다.</td>
						</tr>
				<%		
					}
				%>
					</table>
					
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>