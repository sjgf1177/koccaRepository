<!DOCTYPE html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
	//학습창 파라미터정보
	String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_mftype    = box.getString("p_mftype");
    String  s_userid    = box.getSession("userid");

	//파라미터 셋팅
    session.setAttribute("subjseq", v_subjseq);
    session.setAttribute("user_id",s_userid );
    session.setAttribute("subj",v_subj );
    session.setAttribute("year", v_year);
    session.setAttribute("mftype", v_mftype);
    
	
    //공지사항, 설문목록
    ArrayList listall   = (ArrayList)request.getAttribute("selectListAll");    	// 젠체공지리스트
    ArrayList list      = (ArrayList)request.getAttribute("selectList");       	// 공지리스트
    ArrayList sullist   = (ArrayList)request.getAttribute("SulmunSubjUserList");	// 과정설문리스트
    ArrayList sullist1  = (ArrayList)request.getAttribute("SulmunContentsUserList");// 가입설문리스트

    
	//개인별 학습정보
    String v_edustart  = (String)request.getAttribute("edustart");		//학습시작일
    String v_isalways  = (String)request.getAttribute("isalways");		//상시학습여부
    String v_promotion  = (String)request.getAttribute("promotion");	//권장진도율
    String v_progress   = (String)request.getAttribute("progress");		//자기진도율
    
    if(v_edustart.equals("")){
    	v_edustart = "-";
    }else{
    	v_edustart = FormatDate.getFormatDate(v_edustart,"yyyy.MM.dd");
    }



    //스콤 맛보기 여부
    String  s_scorealpath = conf.getProperty("dir.scorealpath");
    String v_ispreview = box.getString("p_ispreview"); 
    SCOBean sco = new SCOBean();
    ArrayList list2 ;
    if (v_ispreview.equals("Y")) {
        list2 = sco.BookmarkInfoPreview(v_subj, v_year, v_subjseq, s_userid);
    } else {
        list2 = sco.BookmarkInfo(v_subj, v_year, v_subjseq, s_userid);
    }

    
    String v_oid = "";
    String v_lesson = "";
    String v_starting = "";
    String v_whole_starting = "";
    for(int i = 0; i < list2.size(); i++) {
        SCOData data= (SCOData)list2.get(i);
        v_oid = data.getOid();
        v_lesson = data.getLesson();

        v_starting = data.getStarting();
        v_whole_starting = s_scorealpath + v_starting;
    }


	//과정정보 조회
    SubjectBean subjbean = new SubjectBean();
    SubjectData subjdata = null;
    subjdata = subjbean.SelectSubjectData(box);
    String t_contenttype = subjdata.getContenttype();

    
    //학습정보 점수조회

    String  v_edutime = "";  // 학습시간
    String  v_edudate = "";  // 최근학습일
    int     v_educount= 0;   // 강의실접근횟수
    EduStartBean bean = new EduStartBean();
    ArrayList edutimelist= bean.SelectEduTimeCountOBC(box);
    EduListData edutime = null;
    if(edutimelist.size()>0) {
        edutime = (EduListData)edutimelist.get(0);
        v_edutime = edutime.getTotal_time();
        v_edudate = edutime.getFirst_edu();
        v_educount= edutime.getLesson_count();
    }

    SulmunAllPaperBean sulbean = new SulmunAllPaperBean();
    int sulprogress = sulbean.getSulProgress(v_subj, v_year, v_subjseq);
    int sulresult = sulbean.getSulResult(v_subj, v_year, v_subjseq);
    String  v_today = FormatDate.getDate("yyyyMMdd");
    String v_reviewend = null;
    
    if(v_edudate.equals("")){
    	v_edudate = "-";
    }else{
    	v_edudate = FormatDate.getFormatDate(v_edudate,"yyyy.MM.dd");
    }

    int v_gradstep = 60;
    EduScoreData scoredata = bean.SelectEduScore(box);
    try {
    	if (scoredata == null) {
        	v_reviewend = v_today;
    	}else if (StringManager.toInt(v_today+"00") < StringManager.toInt(scoredata.getEduend())) {
        	v_reviewend = scoredata.getEduend();
        	v_gradstep = (int)scoredata.getGradstep();
    	}else{
    		v_gradstep = (int)scoredata.getGradstep();
        	String v_reviewtype = scoredata.getReviewtype();
        	int v_reviewdays = scoredata.getReviewdays();
        	String v_reviewstart = scoredata.getEduend();
	        if (v_reviewtype.equals("D")) {
	            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "date");
	        } else if (v_reviewtype.equals("W")) {
	            v_reviewend = getAddDay(v_reviewstart, v_reviewdays* 7, "date");
	        } else if (v_reviewtype.equals("M")) {
	            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "month");
	        } else if (v_reviewtype.equals("Y")) {
	            v_reviewend = getAddDay(v_reviewstart, v_reviewdays, "year");
	        }
	    }
    }catch( Exception e) {
        System.out.println(e);
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
	<script type="text/javascript" src="/script/portal/backspaceN.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			//팝업창 타이틀 설정
		    top.document.title = "<%= subjdata.getSubjnm() %> - 한국콘텐츠아카데미";
		    
		    //링크연결
		    $('td.ta_l').find('a').click(function(){
		        parent.configs.location = "/learn/user/contents/overconfig.jsp";
		    });
		
		    //안내메시지
			<%if ( v_subj.equals("CK15002") ) {%>
		    alert("※ 본 과정은 [한국양성평등교육진흥원] 콘텐츠를 지원받아 운영되는 과정으로 수료증이 발급되지 않습니다.\n\n 교육시간으로 인정되는 과정을 원하는 경우에는 [한국양성평등교육진흥원(egenedu.kigepe.or.kr/)]으로 신청하시기 바랍니다.");
			<%}%>
			
			<%
			if(!v_subjseq.equals("0000")){
				if(Double.parseDouble(v_progress)>=sulprogress && sulprogress > 0 && sulresult == 0){
			%>
					goSulpaper();
			<%
				}
			}
			%>
		});
		
		
		function whenBr(lesson,branch){
		    top.etop.whenBranchFromProcessWin(lesson,branch);
		}

		//공지사항상세보기
		function view(seq) {
		    window.self.name = "SubjGongStudy";
		    window.open("", "openGongList", "scrollbars=no,width=930,height=600,scrollbars=yes,resizable=yes'");
		    document.form1.target = "openGongList"
		
		    document.form1.action          = "/servlet/controller.study.SubjGongStudyServlet";
		    document.form1.p_seq.value     = seq;
		    document.form1.p_process.value = "selectView";
		    document.form1.submit();
		
		    document.form1.target = window.self.name;
		}

		
		<%if(!v_subjseq.equals("0000")){%>
		function goSulpaper() {
		    //document.form2.submit();
		    var subj = document.form1.p_subj.value;
		    var year = document.form1.p_year.value;
		    var subjseq = document.form1.p_subjseq.value;
		    var url = "/servlet/controller.study.StudySulmunServlet?&p_subj="+subj+"&p_year="+year+"&p_subjseq="+subjseq;
		    window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width=800,height=600 ").focus();
		}
		<%}%>

		//팝업호출
		function upWin(url,w,h){
		    window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
		}
		
		
		// 과정 설문응시
		function contentsWright1(p_subjnm, p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend, p_sulnums){
	        document.form3.p_subj.value = "ALL";
	        document.form3.s_subj.value = p_subj;  
	        document.form3.p_subjnm.value = p_subjnm;
	        document.form3.p_grcode.value = "ALL";
	        document.form3.s_grcode.value = p_grcode;   
	        document.form3.p_gyear.value = p_gyear;
	        document.form3.p_subjseq.value = p_subjseq;
	        document.form3.p_sulpapernum.value = p_sulpapernum;
	        document.form3.p_sulpapernm.value = p_sulpapernm;
			document.form3.p_sulstart.value = p_sulstart;
	        document.form3.p_sulend.value = p_sulend;
	        document.form3.p_sulnums.value = p_sulnums;    // 문제번호    
	        document.form3.p_listok.value = '0';

	        farwindow = window.open("", "openSulpage", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 930, height = 600, top=0, left=0");
	        document.form3.target = "openSulpage";
	        document.form3.action = "/servlet/controller.research.SulmunSubjUserServlet";
	        document.form3.p_process.value = "SulmunUserPaperListPage";
	        document.form3.submit();

		}
		
		//가입섦문 응시
		function contentsWright(){
			
			farwindow = window.open("", "openSulpage", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 930, height = 600, top=0, left=0");
			document.form4.p_subj.value = "REGIST";
		    document.form4.p_process.value = 'SulmunUserPaperListPage';
			document.form4.target = "openSulpage";
		    document.form4.action = "/servlet/controller.research.SulmunRegistUserServlet";
		    document.form4.submit();
		}
	</script>
</head>

<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" value="<%=v_process%>"/>
    <input type="hidden" name="p_subj"    value="<%=v_subj%>"/>
    <input type="hidden" name="p_year"    value="<%=v_year%>"/>
    <input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>
    <input type="hidden" name="p_seq"     value=""/>
</form>
<form name="form2" method="post" action="/servlet/controller.research.SulmunServlet">
  <input type="hidden" name="p_process" value="UserResultListPage"/>
  <input type="hidden" name="p_f_gubun" value="C"/>
</form>

<%
	 DataBox dbox01 = null;
	 if(sullist != null && sullist.size() > 0){
	     dbox01 = (DataBox)sullist.get(0);
	 }else{
	     dbox01 = new DataBox("resoponsebox");
	 }
%>
<form name="form3" id="form3" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">
	<input type="hidden" name="p_subjnm"    value=""/>            
	<input type="hidden" name="s_subj"    value="<%=dbox01.getString("d_subj")%>"/>    
	<input type="hidden" name="s_grcode"  value=""/>        
	<input type="hidden" name="p_sulstart"  value=""/>
	<input type="hidden" name="p_sulend"  value=""/>
	<input type="hidden" name="p_sulnums"  value=""/>        
	<input type="hidden" name="p_listok"  value=""/>      
	 
	<input type="hidden" name="p_process" value=""/>
	<input type="hidden" name="p_action"  value=""/>
	<input type="hidden" name="p_subj"    value=""/>
	<input type="hidden" name="p_gubun"    value=""/>
	<input type="hidden" name="p_grcode"  value="<%=dbox01.getString("d_grcode")%>"/>
	<input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>"/>
	<input type="hidden" name="p_subjseq"  value="<%=dbox01.getString("d_subjseq")%>"/>
	<input type="hidden" name="p_sulpapernum"  value="<%=dbox01.getString("d_sulpapernum")%>"/>
	<input type="hidden" name="p_sulpapernm"  value="<%=dbox01.getString("d_sulpapernm")%>"/>
	<input type="hidden" name="p_sulstart"  value="<%=dbox01.getString("d_sulstart")%>"/>
	<input type="hidden" name="p_sulend"  value="<%=dbox01.getString("d_sulend")%>"/>
	<input type="hidden" name="p_isalways"  value="<%=v_isalways%>"/>      
</form>

<%
	DataBox dbox0 = null;
	if(sullist != null && sullist1.size() > 0){
		dbox0 = (DataBox)sullist1.get(0);
	}else{
		dbox0 = new DataBox("resoponsebox");
	}
%>
<form name="form4" method="post" action="/servlet/controller.research.SulmunRegistUserServlet">
	<input type="hidden" name="p_process" value=""/>
	<input type="hidden" name="p_action"  value=""/>
	<input type="hidden" name="p_subj"    value="<%=dbox0.getString("d_subj")%>"/>
	<input type="hidden" name="s_subj"    value="<%=box.getString("p_subj")%>"/>
	<input type="hidden" name="p_grcode"  value="ALL"/>
	<input type="hidden" name="p_gyear"  value="<%=box.getString("p_year")%>"/>
	<input type="hidden" name="p_subjseq"  value="<%=box.getString("p_subjseq")%>"/>
	<input type="hidden" name="p_sulpapernum"  value="<%=dbox0.getString("d_sulpapernum")%>"/>
	<input type="hidden" name="p_sulpapernm"  value="<%=dbox0.getString("d_sulpapernm")%>"/>
	<input type="hidden" name="p_sulstart"  value="<%=dbox0.getString("d_sulstart")%>"/>
	<input type="hidden" name="p_sulend"  value="<%=dbox0.getString("d_sulend")%>"/>
	<input type="hidden" name="p_isalways"  value="<%=v_isalways%>"/>    
</form>
    <section>
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer_pop">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>학습하기</span>
                                </div>
                                <div class="sub_contents_body">
                                    <div class="sub_info_body">
                                        <div class="sub_course_alert_box">
                                            <p><%= subjdata.getSubjnm() %></p>
                                        </div>
                                        <div class="sub_course_view_wrap" style="margin-bottom:30px;">
                                            <ul class="myCourseDetail">
                                                <li><span>학습시작일</span>&nbsp;:&nbsp;&nbsp;<%=v_edustart %></li>
                                                <li><span>최근학습일</span>&nbsp;:&nbsp;&nbsp;<%=v_edudate %></li>
                                                <li><span>학습횟수</span>&nbsp;:&nbsp;&nbsp;<%=v_educount %>회</li>
                                            </ul>
                                            <ul class="myCourseDetail">
                                                <li><span>나의 진도율</span>&nbsp;:&nbsp;<span class="progress_bar blue"><%= v_progress %>%<span style="width:<%= v_progress %>%"></span></span></li>
                                                <li><span>이수기준</span>&nbsp;:&nbsp;&nbsp;<span class="courseDetailList">진도율 <%=v_gradstep%>% 이상</span></li>
                                            </ul>
                                        </div>
<!--
                                        <div class="sub_course_alert_box">
                                            <p style="font-size: 12px;">본 과정은 상시학습으로 특별한 수료기준이 없으나 학습 현황 참고사항이며, 수료확인 또는 증빙이 필요할 경우 진도율 <%=v_gradstep%>%이상이 되어야 수료층 출력이 가능합니다.</p>
                                        </div>
-->
                                    </div>
                                    <div class="sub_boarder_body">
                                    	<!--
                                        <p class="sub_course_view_title">공지사항</p>
                                        <table class="td_align_center">
                                            <colgroup>
                                                <col width="15%">
                                                <col width="auto">
                                                <col width="25%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>번호</th>
                                                    <th>제목</th>
                                                    <th>등록일</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            	<%
					            				// 전체
									            for(int i = 0; i < listall.size(); i++) {
									            	
									                SubjGongData data= (SubjGongData)listall.get(i);
									                int v_seq       = data.getSeq();
									                String v_typesnm   = data.getTypesnm();
									                String v_title     = data.getTitle();
									                String v_addate    = data.getAddate();
												%>
								                    <tr>
								                        <td>전체</td>
								                        <td><a href="javascript:view('<%= v_seq %>')"><%= v_title %></a></td>
								                        <td><%= FormatDate.getFormatDate(v_addate,"yyyy.MM.dd") %></td>
								                    </tr>
												<%
									            }
												
												//일반공지
									            for(int i = 0; i < list.size(); i++) {
									                DataBox dbox = (DataBox)list.get(i);
									                int v_seq       = dbox.getInt("d_seq");
									                String v_typesnm   = dbox.getString("d_typesnm");
									                String v_title     = dbox.getString("d_title");
									                String v_addate    = dbox.getString("d_addate");
												%>
													<tr>
								                        <td><%= list.size() - i %></td>
								                        <td><a href="javascript:view('<%= v_seq %>')"><%= v_title %></a></td>
								                        <td><%= FormatDate.getFormatDate(v_addate,"yyyy.MM.dd") %></td>
								                    </tr>
												<%
									            }
												
												//공지글이 없을때
									            if(v_process.equals("select") && listall.size() == 0 && list.size() == 0){
												%>
								                    <tr>
								                        <td colspan="3">등록된 내용이 없습니다</td>
								                    </tr>
												<%	
												} 
												%>
                                                <tr>
                                                    <td colspan="3">등록된 내용이 없습니다.</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        -->
                                        <p class="sub_course_view_title" style="margin-top:50px;">설문</p>
                                        <table>
                                            <colgroup>
                                                <col width="20%">
                                                <col width="auto">
                                                <col width="20%">
                                                <col width="25%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>설문유형</th>
                                                    <th>제목</th>
                                                    <th>참여여부</th>
                                                    <th>현진도/응시진도</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <%
												if((sullist != null && sullist.size() > 0) || (sullist1 != null && sullist1.size() > 0)){
													
													if(sullist != null && sullist.size() > 0){
														for (int i = 0; i < sullist.size(); i++) {
										            		DataBox sdbox = (DataBox)sullist.get(i);  
											%>		
														<tr>
															<td>과정설문</td>
															<td><%= String.valueOf(sdbox.getString("d_sulpapernm")) %></td>
															<td>
																<%
																	if(sdbox.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(sdbox.getString("d_progress")))){
																		if(sdbox.getString("d_dispyn").equals("Y")) { 
																%>
																			<a href="javascript:contentsWright1('<%=sdbox.getString("d_subjnm")%>', '<%=sdbox.getString("d_subj")%>', '<%=sdbox.getString("d_grcode")%>', '<%=sdbox.getString("d_year")%>', '<%=sdbox.getString("d_subjseq")%>', '<%=sdbox.getInt("d_sulpapernum")%>', '<%=sdbox.getString("d_sulpapernm")%>', '', '','<%=sdbox.getString("d_sulnums")%>')">
																				<span class="btn_record">참여하기</span>
																			</a>
																<%				
																		} else { 
																%>
																  			설문기간아님
																<%				
																		} 
																	}else if(sdbox.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)<Double.parseDouble(sdbox.getString("d_progress")))){
																		if(sdbox.getString("d_dispyn").equals("Y")) { 
																%>
																			진도미달
																<%				
																		} else { 
																%>
												                      		설문기간아님
																<%				
																		} 
																%>
																<%		
																	}else if(sdbox.getString("d_eachcnt").equals("1")){
																%>
												                      		참여완료
																<%	
																	}
																%>
															</td>
															<td><%= v_progress %> / <%= sdbox.getString("d_progress") %></td>
														</tr>
											<%
														}
													}
													
													if(sullist1 != null && sullist1.size() > 0){
														for (int i = 0; i < sullist1.size(); i++) {
										            		DataBox sdbox = (DataBox)sullist1.get(i); 
										  	%>          		
										  				<tr>
															<td>가입설문</td>
															<td><%= String.valueOf(sdbox.getString("d_sulpapernm")) %></td>
															<td>
																<%			
																	if(box.getString("p_contentsdata").equals("0")){%>
																		<a href="javascript:contentsWright()"><span class="btn_record">참여하기</span></a>
																<%			
																	}else{
																%>
											                      		참여완료
																<%			
																	}
																%>
															</td>
															<td>-</td>
														</tr>
										  	<%
														}
													}
												}else{
											%>		
												<tr>
													<td colspan="4">
														등록된 내용이 없습니다
													</td>
												</tr>
											<%
												}
											%>
                                            </tbody>
                                        </table>
                                        
                                        <!-- 학습창메뉴 -->
										<%
										if (!v_ispreview.equals("Y")) {
					   					%>
                                        <div class="myCourseLearningBtn">
                                            <a href="javascript:upWin('/servlet/controller.contents.EduStart?p_process=eduList&p_lesson_link=Y',1005,615);">과정 상세정보</a>
                                            <a href="javascript:upWin('/servlet/controller.study.StudyBoardServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',1005,615);">과정 게시판</a>
                                            <a href="javascript:upWin('/servlet/controller.study.SubjQnaStudyServlet?p_process=SubjQnaFrame&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',1005,615);">과정 질문방</a>
                                            <a href="javascript:upWin('/servlet/controller.study.StudyDataServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>',1005,615);">과정 자료실</a>
                                            <a href="javascript:upWin('/servlet/controller.study.StudySulmunServlet?&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>&p_isalways=<%=v_isalways%>',1005,615);">과정 설문</a>
                                            <a href="javascript:upWin('/servlet/controller.study.StudyExamServlet?p_process=StudyExamListPage&p_subj=<%=v_subj%>&p_year=<%=v_year%>&p_subjseq=<%=v_subjseq%>&p_isalways=<%=v_isalways%>',1005,615);">평가</a>
                                        </div>
                                        <%
                                        }
                                        %>
                                        
                                        <p class="sub_course_view_title" style="margin-top:50px;">차시정보</p>
                                        <table class="td_align_left1">
                                            <colgroup>
                                                <col width="auto">
                                                <col width="15%">
                                                <col width="20%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>차시</th>
                                                    <th>학습여부</th>
                                                    <th>최종학습일</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <%
											String v_module="";
											String v_brlink=""; 
											String v_lesssonlink="";
									
									
									        int n = 0;
									        EduListData x = null;
									        ArrayList brlist = null;
									        MfBranchData bData = null;
									        String today = FormatDate.getDate("yyyyMMdd");
									        list = (ArrayList)request.getAttribute("EduList");
									        
									        for (int i=0; i<list.size(); i++) {
									            x  = (EduListData)list.get(i);
									            int m=0;
									            
									            for(int k=n;k < list.size();k++) {
									                EduListData y = (EduListData)list.get(k);
									                if (y.getRecordType().equals("STEP")) {
									                    if(y.getModule().equals(x.getModule()) ) {
									                        m++;
									                    } else {
									                        n=k;
									                        break;
									                    }
									                }
									            }
									            
									            if(x.getRecordType().equals("STEP")){
									            	
									            	if(x.getIsbranch().equals("Y")){
								                        v_brlink = "<br>";
								                        for(int j=0;j<brlist.size();j++){
								                            bData = (MfBranchData)brlist.get(j);
								                            v_brlink=v_brlink + "<a href=\"javascript:whenBr('"+x.getLesson()+"','"+bData.getBranch()+"')\">"+bData.getSdesc()+"</a>&nbsp;&nbsp";
								                        }
								                    }
								
								                    String fromdate = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getFromdate()));
								                    String todate   = FormatDate.getRelativeDate(x.getEdustart(), Integer.parseInt(x.getTodate()));
								
							                        v_lesssonlink   = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";
								                    if (Integer.parseInt(x.getTodate()) != 0 && !"".equals(fromdate) && !"".equals(todate)) {
								                        if ((Integer.parseInt(today) >= Integer.parseInt(fromdate)) && (Integer.parseInt(today) <= Integer.parseInt(todate))) {
								                            v_lesssonlink = "<a href=\"javascript:top.etop.starting('" + x.getLesson() + "')\">"+ x.getLesson() + "-" + x.getSdesc() + "</a>";
								                        } else {
								                            v_lesssonlink = x.getLesson() + "-" + x.getSdesc();
								                        }
								                    }
											%>
                                                <tr>
                                                    <td><%= v_lesssonlink %><%= v_brlink %></td>
                                                    <td><%= get_isEducatedTxt(x.getIsEducated()) %></td>
                                                    <td><%= FormatDate.getFormatDate(x.getLdate(),"yyyy.MM.dd-HH:mm") %></td>
                                                </tr>
                                            <%
									            }
									        }
                                            %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="popup_btnBox">
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

<%!
	//학습상태 확인
	public String get_isEducatedTxt(String val){
	    if(val.equals("Y")) {
	        return "학습완료";
	    } else if(val.equals("P")) {
	        return "";
	    } else {
	        return "미학습";
	    }
	}
	//날짜계산
	public String getAddDay(String v_reviewstart, int v_reviewdays, String type) throws Exception{
	    String result = FormatDate.getFormatDate(FormatDate.getDateAdd(FormatDate.getFormatDate(v_reviewstart,"yyyyMMdd"),"yyyyMMdd", type, v_reviewdays), "yyyy.MM.dd");
	        return  result;
	}
%>