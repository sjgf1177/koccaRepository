<!DOCTYPE html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");  
    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");   
    String  v_gadminYn   = box.getString("p_gadminYn");   //관리자여부

    String  v_subj       = box.getString("p_subj");        //과정코드
    String  v_year       = box.getString("p_year");         //년도
    String  v_subjseq    = box.getString("p_subjseq");       //과정 차수
    String  v_lesson     = box.getString("p_lesson");
    String  v_lessonnm   = box.getString("p_lessonnm");
    String  s_userid     = box.getSession("userid");

    String  v_title="",  v_jikwinm="",  v_asgnnm="" ,v_cono="", v_inuserid="",v_inusernm="";
    String  v_indate="", v_contents="", v_replygubun = "", v_replygubun_view = "";
    String  v_isopen="", v_isopen_value="";
    int  v_cnt  = 0;
    int  v_kind = 0;
    int  v_seq = 0, v_anscnt = 0 ;
    int  v_qcnt     = 0;
    int  v_nanscnt  =0;
    int     i            = 0;
    ArrayList list1      = null;
    box.put("needNoDetailRead", "true");
    box.put("p_accessGubun","subjQna");

    list1 = (ArrayList)request.getAttribute("SubjqnaDetail");

    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);
    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    				scoredata	= bean.SelectEduScore(box);

	DataBox qbox = null;
	DataBox abox = null;

	for(i = 0; i < list1.size(); i++) {
		DataBox dbox = (DataBox)list1.get(i);
		if(dbox.getInt("d_kind") == 0){
			qbox = (DataBox)list1.get(i);
		}else{
			if(abox == null){
				abox = (DataBox)list1.get(i);
			}
		}
	}
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
	    // 답변하기
	    function reply_qna(seq, kind) {
	        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
	        document.ff.p_seq.value = seq;
	        document.ff.p_kind.value = kind;
	        document.ff.p_process.value = "SubjQnaReplyPage";
	        document.ff.submit();
	    }
	
	    // 수정하기 
	    function modify_qna(seq, kind) {
	        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
	        document.ff.p_seq.value = seq;
	        document.ff.p_kind.value = kind;
	        document.ff.p_process.value = "SubjQnaUpdatePage";
	        document.ff.submit();
	    }
	
	    // 삭제
	    function delete_qna(seq, kind) {
	        if (confirm("정말로 삭제하시겠습니까?")) {
	            document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
	            document.ff.p_seq.value = seq;
	            document.ff.p_kind.value = kind;
	            document.ff.p_process.value = "SubjQnaDelete";
	            document.ff.submit();
	        }
	        else {
	            return;
	        }
	    }
	
	    function list_qna() {
	        document.ff.action = "/servlet/controller.study.SubjQnaStudyServlet";
	        document.ff.p_process.value = "SubjQnaList";
	        document.ff.submit();
	    }
	</script>
</head>
<body id="pop_study">
	<form name="ff" method="post" action="/servlet/controller.study.SubjQnaStudyServlet">
		<input type="hidden" name="p_process"    value="SubjQnaList"/>
		<input type="hidden" name="p_search"     value = "<%=v_search %>"/>
		<input type="hidden" name="p_searchtext" value = "<%=v_searchtext %>"/>
		<input type="hidden" name="p_subj"       value="<%=v_subj%>"/>
		<input type="hidden" name="p_year"       value="<%=v_year%>"/>
		<input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>"/>
		<input type="hidden" name="p_lesson"     value="<%=v_lesson%>"/>
		<input type="hidden" name="p_seq"        value="<%=v_seq%>"/>
		<input type="hidden" name="p_job"        value=""/>
		<input type="hidden" name="p_kind"      value=""/>
		<input type="hidden" name="p_lessonnm"   value="<%=v_lessonnm%>"/>
		<input type="hidden" name="p_gadminYn"   value="<%=v_gadminYn%>"/>
	</form>
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
													<th>작성일</th>
													<td><%=FormatDate.getFormatDate(qbox.getString("d_indate"),"yyyy.MM.dd") %></td>
													<th>조회수</th>
													<td>0</td>
												</tr>
												<tr>
													<th>작성자</th>
													<td colspan="3"><%=qbox.getString("d_name") %></td>
												</tr>
												<tr>
													<th>제목</th>
													<td colspan="3">[ <%if(qbox.getString("d_togubun").equals("1")){out.println("운영자에게");}else{out.println("강사에게");}%>] <%=qbox.getString("d_title") %></td>
												</tr>
												<tr>
													<td colspan="4"><%= qbox.getString("d_contents").replaceAll("\r\n", "<br/>") %></td>
												</tr>
												<tr>
													<th>첨부파일</th>
													<td colspan="3">
														<%=SubjQnaBean.selectQnaFileList(v_subj, v_year, v_subjseq, qbox.getInt("d_seq"), qbox.getInt("d_kind"))%>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
                                <div class="popup_btnBox">
<%
    if (s_userid.equals(qbox.getString("d_inuserid")) ) {
%>
                                    <a href="javascript:delete_qna('<%=qbox.getInt("d_seq")%>','<%=qbox.getInt("d_kind")%>');" class="btn_delete">삭제</a>
                                    <a href="javascript:modify_qna('<%=qbox.getInt("d_seq")%>','<%=qbox.getInt("d_kind")%>');" class="btn_update">수정</a>
<%
      }
%>
                                    <a href="javascript:list_qna();" class="btn_courseCancel">목록</a>
                                </div>
<%
	if(abox != null){
%>
                                <div class="sub_info_body" style="margin-top:50px;">
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
                                                <td><%=abox.getString("d_title") %></td>
                                                <th>작성일</th>
                                                <td><%=FormatDate.getFormatDate(abox.getString("d_indate"),"yyyy.MM.dd") %></td>
                                            </tr>
                                            <tr>
                                                <td colspan="4"><%= abox.getString("d_contents").replaceAll("\r\n", "<br/>") %></td>
                                            </tr>
                                            <tr>
                                                <th>첨부파일</th>
                                                <td colspan="3">
                                                	<%=SubjQnaBean.selectQnaFileList(v_subj, v_year, v_subjseq, abox.getInt("d_seq"), abox.getInt("d_kind"))%>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
<%
	}
%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>