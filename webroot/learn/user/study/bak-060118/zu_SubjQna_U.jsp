<%
//**********************************************************
//  1. 제      목: SUBJ QNA UPDATE
//  2. 프로그램명: zu_SubjQna_U.jsp
//  3. 개      요: 과정 질문 업데이트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김영만 2003. 9. 8
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
    String  v_process = box.getString("p_process");    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_gadminYn = box.getString("p_gadminYn");    //관리자여부
    String  v_subj    = box.getString("p_subj");        //과정코드
    String  v_year    = box.getString("p_year");         //년도
    String  v_subjseq = box.getString("p_subjseq");       //과정 차수
    String  v_lesson  = box.getString("p_lesson");
    String  v_lessonnm  = box.getString("p_lessonnm");
    int     v_seq     = box.getInt("p_seq");
    int     v_ptypes  = box.getInt("p_types");
    int     v_kind    = box.getInt("p_kind");

    String  v_title="", v_contents="",  v_inuserid="",v_inusernm="";
	
	String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("username");
    v_inuserid= box.getSession("userid");
    v_inusernm= box.getSession("name");


    String  v_indate="";
    String  v_isopen="";
    int  v_types = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    // editor
    String content      = "";
    String width        = "600";
    String height       = "300"; 
        
    ArrayList qnalist1      = null;
    //DataBox data = (DataBox)request.getAttribute("SubjqnaDetail2");
    qnalist1 = (ArrayList)request.getAttribute("SubjqnaDetail2");
    DataBox data1 = null;

    //v_seq     = data1.getSeq();
    //v_types     = data1.getTypes();
    //v_title   = data1.getTitle();
    //v_contents = data1.getContents();
    //content     = StringManager.replace(data1.getContents(),"\n;","<br>");   
    //v_inuserid= data1.getInuserid();
    //v_inusernm= data1.getInusernm();
    //v_indate  =  FormatDate.getFormatDate(data1.getIndate(),"yyyy/MM/dd");
    //v_isopen  = StringManager.chkNull(data1.getIsOpen());
    //String v_togubun  = data1.getTogubun();       

    data1 = (DataBox)qnalist1.get(0); 
    v_seq       = data1.getInt("d_seq");             
    v_title     = data1.getString("d_title");                    
    content     = StringManager.replace(data1.getString("d_contents"),"\n;","<br>");   
    v_inuserid  = data1.getString("d_inuserid");
    v_inusernm  = data1.getString("d_name");
    v_indate    = FormatDate.getFormatDate(data1.getString("d_indate"),"yyyy/MM/dd");
    v_isopen    = StringManager.chkNull(data1.getString("d_isopen"));
    String vs_kind    = data1.getString("d_kind");  
    String v_togubun  = data1.getString("d_togubun");  
    String v_realfile = data1.getString("d_realfile");                    

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
  function qna_insert() {     
        if(!confirm("저장하시겠습니까?")) {
                 return;
            }
        if (document.ff.p_title.value == "") {
            alert("제목을 입력하세요");
            document.ff.p_title.focus();
            return;
        }

        if (realsize(document.ff.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.ff.p_title.focus();
            return;
        }

    	if(document.all.use_editor[0].checked) {        
    	    ff.p_contents.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    	}else {
    		ff.p_contents.value = document.all.txtDetail.value;
    	}


	    //파일 확장자 필터링  
	    var islimit = true;	    
	    for(var i=1; i<=5; i++){
	        var file = eval("document.ff.p_file"+i+".value");

    	    if(file!="") {
    	        islimit = limitFile(file);
    	        
    	        if(!islimit) break;
    	    }	        
	    }
 	    	    	    	    
	    if(islimit) {
            document.ff.target = "_self";
            document.ff.p_search.value     = "";
            document.ff.p_searchtext.value = "";
            document.ff.p_process.value = 'SubjQnaUpdate';        
            document.ff.submit();
	    }else{
	        return;
	    }
	            
    }

    function goQnaDetailPage() {
      document.ff.submit();
    }
    
    function detail_qna() {
        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
        document.ff.p_process.value = "SubjQnaList";
        document.ff.submit();
    }
-->
</SCRIPT>
</head>




<body topmargin="0" leftmargin="0">
<form name="ff" method="post" action="/servlet/controller.study.SubjQnaStudyServlet" enctype = "multipart/form-data">
        <input type="hidden" name="p_process"    value="QnaDetail">
        <input type="hidden" name="p_search"     value = "<%=v_search %>">
        <input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>">
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_year"       value="<%=v_year%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_lesson"     value="<%=v_lesson%>">
        <input type="hidden" name="p_seq"        value="<%=v_seq%>"> <!--글번호key-->
        <input type="hidden" name="p_kind"        value="<%=v_kind%>"><!--질문분류번호key-->
        <input type="hidden" name="p_types"      value="<%=v_ptypes%>">
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

            <table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
              <tr> 
                <td height="5"></td>
              </tr>
            </table>


            <!----------------- 질문 수정 시작 ---------------->
            <table cellspacing="0" cellpadding="0" class="open_board_vertical_table_out" align="center">
              <tr> 
                <td width="3" class="board_color_line"></td>
                <td> 
                  <table cellspacing="1" cellpadding="1" class="table2">
                  <%if(vs_kind.equals("0")){%><!--질문일때만 수정가능-->
                    <tr>
                      <td class="board_title_bg2" width="150">구분</td>
                      <td class="table_text3">
                        <input type="radio" name="p_togubun" value="1" <%if(v_togubun.equals("1")){out.println("checked");}%>>운영자에게&nbsp;
                        <input type="radio" name="p_togubun" value="2" <%if(v_togubun.equals("2")){out.println("checked");}%>>강사에게</td>
                    </tr>                   
                    <%}%>
                    <tr> 
                      <td class="board_title_bg2" width="200">제목</td>
                      <td class="table_text3"> 
                        <input size="90" maxlength="90" name="p_title" type="text" class="input" value=`<%=v_title%>`>
                      </td>
                    </tr>
                    <tr> 
                      <td class="board_title_bg2" width="200">작성자</td>
                      <td class="table_text3"><%=v_inusernm %></td>
                    </tr>
<%            if (v_types == 0) {                                %>
                    <tr> 
                      <td class="board_title_bg2" width="200">공개여부</td>
                      <td class="table_text3"> 
                        <input type="radio" name="p_isopen" value="Y" <%if(v_isopen.equals("Y")){%>checked<%}%>>공개&nbsp; 
                        <input type="radio" name="p_isopen" value="N" <%if(v_isopen.equals("N")){%>checked<%}%>>비공개 
                      </td>
                    </tr>
<%          } else {                                             %>
                        <input type="hidden" name="p_isopen" value="Y">
<%          }                                                     %>

                    <tr> 
                      <td class="board_title_bg2" width="200">내용</td>
                      <td class="table_text3"> 
                        <!--<textarea name="p_contents" cols="70" rows="15" class="input"><%=v_contents%></textarea>-->
						<!-- DHTML Editor  -->
			            <%//@ include file="/portal/include/DhtmlEditor.jsp" %>
    		            <%@ include file="/include/DhtmlEditor.jsp" %>
                        <!-- DHTML Editor  -->                        
                      </td>
                    </tr>
                    

    			    <tr>
                        <td class="board_title_bg2">첨부파일</td>
                        <td class="table_text3">                         
                        <% if(!v_realfile.equals("")){ %>
                        <input type = "hidden" name = "p_upfilecnt"  value = "<%=qnalist1.size()%>"><!--첨부파일 갯수-->
                        <%   for(int j=0; j<qnalist1.size(); j++){         
                             DataBox dbox = (DataBox)qnalist1.get(j);  
                                           
                        %>     
						     <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%=dbox.getString("d_savefile")%>&p_realfile=<%=dbox.getString("d_realfile")%>'>					
                             <%=dbox.getString("d_realfile")%></a> &nbsp;
			
			       			<input type = "checkbox"  name = "p_fileseq<%=j%>" value = "<%=dbox.getInt("d_fileseq")%>"> (삭제시 체크)<br>
					    	<input type = "hidden" name = "p_realfile"  value = <%=dbox.getString("d_realfile")%>>
						    <input type = "hidden" name = "p_savefile<%=j%>"  value ="<%=dbox.getString("d_savefile")%>">
						    <%}%>
                        <%}%>
						  <input type="FILE" name="p_file1" size="80" class="input"><br>
						  <input type="FILE" name="p_file2" size="80" class="input"><br>
						  <input type="FILE" name="p_file3" size="80" class="input"><br>
						  <input type="FILE" name="p_file4" size="80" class="input"><br>
						  <input type="FILE" name="p_file5" size="80" class="input"><br>
						</td>
					  </tr>
                  </table>
                </td>
              </tr>
            </table>
            <!----------------- 질문 수정 끝 ---------------->
            <br>
            <!----------------- 저장, 목록 버튼 시작 ----------------->            
            <table width="120" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td align="center"><a href="javascript:qna_insert();" ><img src="/images/user/button/btn_save.gif"  border="0"></a></td>
                <td align="center"><a href="javascript:detail_qna()" ><img src="/images/user/button/btn_list.gif"  border="0"></a></td>
              </tr>
            </table>
            <!----------------- 저장, 목록 버튼 끝 ----------------->
            <br>
</form>
</body>
</html>
