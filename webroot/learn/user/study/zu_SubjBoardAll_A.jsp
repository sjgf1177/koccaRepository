<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 과정게시판
//  2. 프로그램명: zu_StudyBoard_A.jsp
//  3. 개      요: 과정게시판 답변
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
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    int     v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
    String  v_subj		 = box.getString("p_subj");        //과정코드
    String  v_year		 = box.getString("p_year");        //년도
    String  v_subjseq	 = box.getString("p_subjseq");     //과정 차수

    int    v_refseq   = 0;
    int    v_levels   = 0;
    int    v_position = 0;
    int    v_cnt      = 0;
    String v_userid   = "";
    String v_name     = "";
    String v_indate   = "";
    String v_title    = "";
    String v_content  = "";
	String v_addcontent = "";
	String v_contentre = "";
    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";

    DataBox dbox = (DataBox)request.getAttribute("selectStudyBoard");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_refseq      = dbox.getInt("d_refseq");
        v_levels      = dbox.getInt("d_levels");
        v_position    = dbox.getInt("d_position");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
        //content     = StringManager.replace(dbox.getString("d_content"),"\n;","<br>");  
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
        
		v_title = "[RE] " + v_title;
        v_title = StringManager.replace(v_title, "`", "'");
//		v_contentre = "\r\n\r\n\r\n\r\n\r\n\r\n--------------------------------------------------\r\n\r\n" + v_name + "님이 쓰신글\r\n\r\n";
		v_contentre = "<br><br><br><br><br><br>[원본]---------------------------------------------------------------------<br>" + v_name + "님이 쓰신글<br><br><br>";
    }

    String s_userid    = box.getSession("userid");
    String s_name      = box.getSession("name");
    
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>게시판</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script> 
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/user_patch.js'></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>  
<script language="JavaScript">
<!--
    function insert() {
        //moveContent();

        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }

    	if(!CrossEditor.IsDirty()){ // 크로스에디터 안의 콘텐츠 입력 확인 
            alert("내용을 입력해 주세요 !!"); 
            CrossEditor.SetFocusEditor();// 크로스에디터 Focus 이동 
            return; 
        } 

        //if(!limitFile()){
        //    alert("업로드 가능하지 않은 파일이 존재합니다.");
        //    return;
        //}
        	
        document.getElementById("p_content").value = CrossEditor.GetBodyValue();
		document.form1.p_search.value     = "";
		document.form1.p_searchtext.value = "";
		document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet?p_file=5";
		document.form1.p_process.value = "reply";
		document.form1.submit();
    }

   function list() {
        document.form1.action = "/servlet/controller.course.SubjBoardAdminServlet";
        document.form1.p_process.value = "";
        document.form1.submit();
   }

//-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>"/>
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>"/>
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>"/>
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>"/>
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>"/>
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>"/>
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>"/>
    <input type = "hidden" name = "p_refseq"     value = "<%= v_refseq %>"/>
    <input type = "hidden" name = "p_levels"     value = "<%= v_levels %>"/>
    <input type = "hidden" name = "p_position"   value = "<%= v_position %>"/>
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>"/>
    <input type = "hidden" name = "p_isedu"      value="1"/><!--학습창임-->
    <input type = "hidden" name = "p_subj"       value = "<%= v_subj %>"/>
    <input type = "hidden" name = "p_year"       value = "<%= v_year %>"/>
    <input type = "hidden" name = "p_subjseq"    value = "<%= v_subjseq %>"/>
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
					
					<table class="study_write">
					<!--[if ie]><colgroup><col width="45px" /><col width="100px" /><col width="30px" /><col width="" /></colgroup><![endif]-->
					<colgroup><col width="65px" /><col width="100px" /><col width="50px" /><col width="" /></colgroup>
					<tr class="bo">
						<th class="th">제목</th>
						<td colspan="7" class="td"><input type="text" name="p_title" class="inbox" style="width:590px;" value="<%= v_title %>"/></td>
					</tr>
					<tr>
						<th class="th">작성자</th>
						<td class="td txt"><%= s_name %></td>
						<th class="th">작성일</th>
						<td class="td stxt ff_t"><%= FormatDate.getDate("yyyy.MM.dd") %></td>
					</tr>
					<tr>
						<td colspan="8" class="td con">
		                    <textarea id="p_content" name="p_content" style="display:none;">
		                    	<%= v_contentre %>
		                    	<%=StringManager.replace(v_content, "&", "&amp") %>
		                    </textarea>
							<script type="text/javascript">
							    var CrossEditor = new NamoSE("contents");
							    var contentValue = document.getElementById("p_content").value; // Hidden 값 참조
							    CrossEditor.editorStart();
							    CrossEditor.SetUISize("820","500");
							    CrossEditor.SetBodyValue(contentValue); // 콘텐츠 내용 에디터 삽입
							</script>
						</td>
					</tr>
					</table>

		            <!-- 파일첨부 시작 -->
		            <%
		            int    i_fileLimit  = HomePageQnaBean.getFILE_LIMIT();              // 제한 첨부 파일수
		            String gubun		= "6";
		            %>
		            <%@ include file="/learn/user/include/zu_MultiAttach_I.jsp" %> 
		            <!-- 파일첨부 종료 -->
					
					<p class="list_btn"><a href="javascript:insert()" class="btn_gr"><span>등록</span></a><a href="javascript:list()" class="btn_gr"><span>취소</span></a></p>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>