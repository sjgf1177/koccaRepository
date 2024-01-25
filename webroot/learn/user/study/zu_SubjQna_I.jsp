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
    String  v_grcode        = box.getSession("grcode");
    String  v_process       = box.getString("p_process");
    String  v_searchtext    = box.getString("p_searchtext");
    String  v_search        = box.getString("p_search");
    String  v_gadminYn      = box.getString("p_gadminYn");   //관리자여부
    String  v_subj          = box.getString("p_subj");        //과정코드
    String  v_year          = box.getString("p_year");        //년도
    String  v_subjseq       = box.getString("p_subjseq");     //과정 차수
    String  v_lesson        = box.getString("p_lesson");
    String  v_lessonnm      = box.getString("p_lessonnm");
    int     v_seq           = box.getInt("p_seq");

    if(v_lesson.equals("")) v_lesson="000";

    String  v_title     = "", v_contents = "",  v_inuserid = "",v_inusernm = "";
    String  v_indate    ="";
    String  v_isopen    ="";
    int     v_types     = 0;
    int     v_nanscnt   =0;
    int     i           = 0;
    ArrayList list1     = null;

    String  v_user_id   = box.getSession("userid");
    String  v_user_name = box.getSession("name");
    v_inuserid= box.getSession("userid");
    v_inusernm= box.getSession("name");

    // editor
    String content      = "";
    String width        = "600";
    String height       = "300";
    box.put("p_accessGubun","subjQna");

    SubjectBean     subjbean = new SubjectBean();
    SubjectData     subjdata = new SubjectData();
                    subjdata = subjbean.SelectSubjectData(box);

    EduStartBean    bean        = new EduStartBean();
    EduScoreData    scoredata   = new EduScoreData();
                    scoredata   = bean.SelectEduScore(box);

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
	<script type="text/javascript" src="/namo_cross/js/namo_scripteditor.js"></script>
	<script type="text/javascript" src="/script/cresys_lib.js"></script>
	<script type="text/javascript" src='/script/user_patch.js'></script>
	<script type="VBScript" src="/script/cresys_lib.vbs"></script>
	
	<script type="text/javascript">
	    var grCode = '<%= box.getSession("tem_grcode") %>';
	
	    /**
	     * 등록하기
	     */
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
	
	        if ( document.getElementById("p_content").value == "" ) {
	            alert("내용을 입력해 주세요.");
	            document.getElementById("p_content").focus();
	            return;
	        }
	
	        document.form1.target = "_self";
	        document.form1.action = "/servlet/controller.study.SubjQnaStudyServlet";
	        document.form1.p_search.value     = "";
	        document.form1.p_searchtext.value = "";
	        document.form1.p_process.value = "SubjQnaInsert";
	        document.form1.submit();
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
	</script>
</head>
<body id="pop_study">
	<form name="form1" method="post" action="/servlet/controller.study.SubjQnaStudyServlet" enctype = "multipart/form-data">
	    <input type="hidden" name="p_process"    value="<%=v_process%>"/>
	    <input type="hidden" name="p_search"     value="<%=v_search %>"/>
	    <input type="hidden" name="p_searchtext" value="<%=v_searchtext %>"/>
	    <input type="hidden" name="p_subj"       value="<%=v_subj%>"/>
	    <input type="hidden" name="p_year"       value="<%=v_year%>"/>
	    <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>"/>
	    <input type="hidden" name="p_lesson"     value="<%=v_lesson%>"/>
	    <input type="hidden" name="p_seq"        value="<%=v_seq%>"/>
	    <input type="hidden" name="p_lessonnm"   value="<%=v_lessonnm%>"/>
	    <input type="hidden" name="p_gadminYn"   value="<%=v_gadminYn%>"/>
	    <input type="hidden" name="p_contents"/>
	    <section>
	        <div class="wrapper">
	            <div class="container">
	                <div class="row">
	                    <div class="col-12">
	                        <div class="subContainer_pop">
	                            <div class="sub_section">
	                                <div class="sub_contents_header">
	                                    <span>과정질문방</span>
	                                </div>
	                                <div class="sub_contents_body">
<%  if (scoredata != null) { %>
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
<%  } %>
	                                    <div class="sub_info_body">
	                                        <table class="write_table th_align_left1">
	                                            <colgroup>
	                                                <col width="17%">
	                                                <col width="auto">
	                                            </colgroup>
	                                            <tbody>
	                                                <tr>
	                                                    <th><span>*</span>구분</th>
	                                                    <td>
	                                                        <input type="radio" name="p_togubun" id="forAdmin" value="1" checked>
	                                                        <label for="forAdmin">운영자에게</label>
	                                                        <input type="radio" name="p_togubun" id="forLecturer" value="2">
	                                                        <label for="forLecturer">강사에게123</label>
	                                                    </td>
	                                                </tr>
	                                                <tr>
	                                                    <th><span>*</span>제목</th>
	                                                    <td><input type="text" name="p_title" style="width:100%" placeholder="제목을 입력하세요."></td>
	                                                </tr>
	                                                <tr>
	                                                    <th><span>*</span>작성자</th>
	                                                    <td><%= v_inusernm %></td>
	                                                </tr>
	                                                <tr>
	                                                    <th><span>*</span>비밀글</th>
	                                                    <td>
	                                                        <input type="radio" name="p_isopen" id="open_y" value="Y">
	                                                        <label for="open_y">공개</label>
	                                                        <input type="radio" name="p_isopen" id="open_n" value="N" checked="checked">
	                                                        <label for="open_n">비공개</label>
	                                                    </td>
	                                                </tr>
	                                                <tr>
	                                                    <th><span>*</span>내용</th>
	                                                    <td><textarea name="p_content" id="p_content" placeholder="내용을 입력하세요."></textarea></td>
	                                                </tr>
	                                                <tr>
	                                                    <th>첨부파일</th>
	                                                    <td>
										                    <%@ include file="/learn/user/typeB/include/multiAttach_include_QnA_I.jsp" %>
	                                                    </td>
	                                                </tr>
	                                            </tbody>
	                                        </table>
	                                    </div>
	                                    
	                                    
	                                </div>
	                                <div class="popup_btnBox">
	                                    <a href="javascript:qna_insert();" class="btn_write">등록</a>
	                                    <a href="javascript:qna_list();" class="btn_courseCancel">취소</a>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </section>
   	</form>
</body>
</html>