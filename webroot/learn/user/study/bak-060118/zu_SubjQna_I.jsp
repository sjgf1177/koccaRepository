<%
//**********************************************************
//  1. 제      목: SUBJ QNA INSERT
//  2. 프로그램명: zu_SubjQna_I.jsp
//  3. 개      요: 과정 질문 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 2003. 9. 8
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_grcode     = box.getSession("grcode"); 
    String  v_process   = box.getString("p_process");
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gadminYn = box.getString("p_gadminYn");   //관리자여부
    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year    = box.getString("p_year");        //년도
    String  v_subjseq = box.getString("p_subjseq");     //과정 차수
    String  v_lesson  = box.getString("p_lesson");
    String  v_lessonnm  = box.getString("p_lessonnm");
    int     v_seq     = box.getInt("p_seq");
    if(v_lesson.equals("")) v_lesson="000";
    String  v_title="", v_contents="",  v_inuserid="",v_inusernm="";
    String  v_indate="";
    String  v_isopen="";
    int  v_types = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    ArrayList list1      = null;

    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
    v_inuserid= box.getSession("userid");
    v_inusernm= box.getSession("name");

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";
%>
<!------- 파일확장자체크 INCLUDE ------------>       
<%@ include file = "/learn/library/fileFilter.jsp" %>
<html>
<head>
<title>::: 질문방 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_study.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
  // 등록하기
  function qna_insert() {
 
        if (blankCheck(document.all.p_title.value)) {
            alert("제목을 입력하세요!");
            document.all.p_title.focus();
            return;
        }                
        if (realsize(document.all.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.all.p_title.focus();
            return;
    	}
    	
        /*if (blankCheck(document.form1.p_contents.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_contents.focus();
            return;
        }*/   	

    	if(document.all.use_editor[0].checked) {        
            document.form1.p_contents.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
		    document.form1.p_contents.value = document.all.txtDetail.value;
	    }
	        

	    //파일 확장자 필터링  
	    var islimit = true;	    
	    for(var i=1; i<=5; i++){
	        var file = eval("document.form1.p_file"+i+".value");

    	    if(file!="") {
    	        islimit = limitFile(file);
    	        
    	        if(!islimit) break;
    	    }	        
	    }
 	    	    	    	    
	    if(islimit) {
          document.form1.target = "_self";
          document.form1.p_search.value     = "";
          document.form1.p_searchtext.value = "";
        	document.form1.p_process.value = "SubjQnaInsert";
          document.form1.submit();
	    }else{
	        return;
	    }	      
    }

    function goQnaDetailPage() {
      document.form1.submit();
    }

    // 목록으로이동
    function qna_list() {
        document.form1.target = "_self";
        document.form1.action = "/servlet/controller.study.SubjQnaStudyServlet";
        document.form1.p_process.value = "SubjQnaList";
        document.form1.submit();
    }
-->
</SCRIPT>
</head>


<body topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.study.SubjQnaStudyServlet" enctype = "multipart/form-data">
    <input type="hidden" name="p_process"    value="<%=v_process%>">
    <input type="hidden" name="p_search"     value="<%=v_search %>">
    <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>">
    <input type="hidden" name="p_subj"       value="<%=v_subj%>">
    <input type="hidden" name="p_year"       value="<%=v_year%>">
    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
    <input type="hidden" name="p_lesson"     value="<%=v_lesson%>">
    <input type="hidden" name="p_seq"        value="<%=v_seq%>">
    <input type="hidden" name="p_lessonnm"   value="<%=v_lessonnm%>">
    <input type="hidden" name="p_gadminYn"   value="<%=v_gadminYn%>">
    <input type="hidden" name="p_contents">

    <br>
            <!----------------- 타이틀 시작 ----------------->
  				<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
    				<tr> 
      					<td><img src="/images/user/study/inquiry_title.gif"></td>
      					<td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
      					<td><img src="/images/user/study/gongi_tail.gif"></td>
    				</tr>
  				</table>
            <!----------------- 타이틀 끝 ---------------->
            <br>


    <!----------------- 질문 등록 시작 ---------------->
    <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out" align="center">
      <tr>
        <td width="3" class="board_color_line"></td>
        <td>
          <table cellspacing="1" cellpadding="1" class="table2">
            <tr>
              <td class="board_title_bg2" width="150">구 분</td>
              <td class="table_text3">
                <input type="radio" name="p_togubun" value="1" checked>운영자에게&nbsp;
                <input type="radio" name="p_togubun" value="2">강사에게</td>
            </tr>          
            <tr>
              <td class="board_title_bg2" width="150">제 목</td>
              <td class="table_text3">
                <input size="70" maxlength="20" name="p_title" class="input">
              </td>
            </tr>
            <tr>
              <td class="board_title_bg2" width="150">작성자</td>
              <td class="table_text3"><%=v_inusernm%></td>
            </tr>
            <tr>
              <td class="board_title_bg2" width="150">공개여부</td>
              <td class="table_text3">
                <input type="radio" name="p_isopen" value="Y" checked>공개&nbsp;
                <input type="radio" name="p_isopen" value="N">비공개</td>
            </tr>
            <tr>
              <td class="board_title_bg2">내 용</td>
              <td class="table_text3">
                <!--<textarea name="p_contents" cols="70" rows="15" class="input"></textarea>     -->
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->                       
              </td>
            </tr>
			  <tr>
                <td class="board_title_bg2">첨부파일</td>
				<td class="table_text3">
				  <input type="FILE" name="p_file1" size="45" class="input"><br>
				  <input type="FILE" name="p_file2" size="45" class="input"><br>
				  <input type="FILE" name="p_file3" size="45" class="input"><br>
				  <input type="FILE" name="p_file4" size="45" class="input"><br>
				  <input type="FILE" name="p_file5" size="45" class="input"><br>
				</td>
			  </tr>            
          </table>
        </td>
      </tr>
    </table>
    <!----------------- 질문 등록 끝 ---------------->
    <br>

    <!----------------- 저장, 목록 버튼 시작 ----------------->
    <table width="120" border="0" cellspacing="0" cellpadding="0" align="center">
      <tr>
          <td align="center"><a href="javascript:qna_insert()" ><img src="/images/user/button/btn_save.gif" border="0"></a></td>
          <td align="center"><a href="javascript:qna_list()" ><img src="/images/user/button/btn_list.gif" border="0"></a></td>
      </tr>
    </table>
    <!----------------- 저장, 목록 버튼 끝 ----------------->
    <br>

</form>
</body>
</html>
