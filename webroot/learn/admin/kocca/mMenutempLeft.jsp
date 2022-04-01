<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

/*============ 테스트용 ======================*/
//    box.setSession("userid","lee1");
//    box.setSession("gadmin","A1");
//    box.setSession("resno","6906031010912");
//    box.setSession("name","김연남");
//    box.setSession("email","jeong@credu.com");
//    box.setSession("usergubun","KDGI");
/*============ 테스트용 ======================*/

   String v_gadmin = box.getSession("gadmin");
   String v_systemgubun = box.getString("p_systemgubun");
   

   

%>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="Javascript">
<!--
	function gomenu(url) {
		document.ff.action=url;
		document.ff.submit();
	}
-->
</script>
</head>

<body topmargin=0>
<form name="ff" method="post" action="" target="fbody">
</form>
<font style="font-size:9pt">
<%if (v_gadmin.equals("A1") || v_gadmin.equals("A1") || v_gadmin.equals("B1") || v_gadmin.equals("B1")) { %>
과정<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SubjectServlet?p_process=listPage" target="fbody">과정개설</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.CourseServlet?p_process=listPage" target="fbody">전문가관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.EduGroupServlet?p_process=listPage" target="fbody">교육그룹관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.templet.TempletServlet?p_process=listPage" target="fbody">템플릿 관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.GrseqServlet?p_process=" target="fbody">차수개설</a><br>                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.OffLineSubjectServlet?p_process=listPage" target="fbody">오프라인 강좌 개설</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.contents.MasterFormServlet?p_process=listPage" target="fbody">마스터폼 관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.contents.MasterFormServlet?p_process=listPageDetail" target="fbody">마스터폼 정보</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.contents.SCObjectServlet?p_process=listPage" target="fbody">OBJECT 관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.contents.SCOServlet?p_process=listPage" target="fbody">SCO 관리</a><br>                                            
신청입과<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.ApprovalServlet?p_process= " target="fbody">신청승인 처리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.StudentManagerServlet?p_process=StudentMemberList" target="fbody">수강생관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.AcceptServlet?p_process=proposeFileToDB" target="fbody">입과일괄처리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.ProposeStatusAdminServlet?p_process=ProposeMemberList" target="fbody">신청명단조회</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.ProposeStatusAdminServlet?p_process=ProposeCancelMemberList" target="fbody">반려/취소명단조회</a><br>        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.propose.ApprovalServlet?p_process=OffLinelistPage" target="fbody">오프라인 신청자관리</a><br>                            
학습현황<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudentStatusAdminServlet?p_process=StudentMemberCountList" target="fbody">입과인원조회</a><br>                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudentStatusAdminServlet?p_process=StudentMemberList" target="fbody">입과명단조회</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ClassServlet?p_process=listPage" target="fbody">클래스구성</a><br>                                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyStatusAdminServlet?p_process=PersonalSearchPage" target="fbody">개인별 학습현황</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyStatusAdminServlet?p_process=LearningStatusListByGrcode" target="fbody">교육그룹별 학습현황</a><br>        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyStatusAdminServlet?p_process=LearningTimeSelectList" target="fbody">학습시간조회</a><br>                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyTotalStatusServlet?p_process=StudyTotalList" target="fbody">종합학습현황</a><br>                        
과정운영<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SubjGongAdminServlet?p_process=selectPre" target="fbody">공지사항</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.DicSubjAdminServlet?p_process=selectPre" target="fbody">용어사전</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.CourseProgressAdminServlet?p_process=selectPre" target="fbody">과정차수별진행상황</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ToronAdminServlet?p_process=ToronList" target="fbody">토론방</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ProjectAdminServlet?p_process=ProjectQuestionsAdmin" target="fbody">리포트출제관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ProjectAdminServlet?p_process=ProjectSubmitAdmin" target="fbody">리포트평가관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyAdminDataServlet?p_process=adminList" target="fbody">과정별자료실</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SubjShamAdminServlet?p_process=selectPre" target="fbody">과정모의학습</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SuperiorityMemberAdminServlet?p_process=selectList" target="fbody">우수회원관리</a><br>                        
수료<br>                                                                                                                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.FinishServlet?p_process=listPage" target="fbody">수료처리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.FinishInputServlet?p_process=listPage" target="fbody">집합과정일괄처리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.CompleteStatusAdminServlet?p_process=CompleteMemberList" target="fbody">과정별교육이력</a><br>                
설문/평가<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.research.SulmunAllQuestionServlet?p_process=SulmunQuestionListPage" target="fbody">설문문제관리</a><br>            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.research.SulmunAllPaperServlet?p_process=SulmunPaperListPage" target="fbody">설문지관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.research.SulmunTargetMemberServlet?p_process=SulmunMemberListPage" target="fbody">설문대상자관리</a><br>                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamQuestionServlet?p_process=ExamQuestionListPage" target="fbody">평가문제관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamMasterServlet?p_process=ExamMasterListPage" target="fbody">평가마스터관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamPaperServlet?p_process=ExamPaperListPage" target="fbody">평가문제지관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamResultServlet?p_process=ExamResultListPage" target="fbody">평가결과분석</a><br>                            
E-test<br>                                                                                                                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestGroupServlet?p_process=ETestGroupListPage" target="fbody">E-test 그룹관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestQuestionServlet?p_process=ETestQuestionListPage" target="fbody">E-test 문제관리</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestMasterServlet?p_process=ETestMasterListPage" target="fbody">E-test 마스터관리</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestMemberServlet?p_process=ETestMemberListPage" target="fbody">E-test 대상자관리</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestPaperServlet?p_process=ETestPaperListPage" target="fbody">E-test 문제지관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.etest.ETestResultServlet?p_process=ETestResultListPage" target="fbody">E-test 결과분석</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.jindan.JindanQuestionServlet?p_process=JindanQuestionListPage" target="fbody">진단문제</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.jindan.JindanResultServlet?p_process=JindanResultListPage" target="fbody">진단 결과분석</a><br>                        
홈페이지<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.NoticeAdminServlet?p_process=select" target="fbody">공지사항관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.PollAdminServlet?p_process=select" target="fbody">CyberPoll관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.HomePageFaqCategoryAdminServlet?p_process=selectList" target="fbody">FAQ관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.QnaAdminServlet?p_process=selectList" target="fbody">통합Q&A관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.NoticeTempletServlet?p_process=select" target="fbody">공지사항 템플릿 관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.LinkSiteServlet?p_process=selectList" target="fbody">관련사이트관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.KnowBoardServlet?p_process=KonwCodeList" target="fbody">지식공유 분류코드</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.community.CommunityAdminFaqServlet?p_process=selectlist&p_faq_type=DIRECT" target="fbody">커뮤니티공지사항</a><br>    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.community.CommunityAdminRoomServlet?p_process=listPage" target="fbody">커뮤니티관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.community.CommunityAdminPoliceServlet?p_process=listPage" target="fbody">불건전커뮤니티신고현황</a><br>                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.NewsAdminServlet?p_process=selectList&p_type=HR" target="fbody">뉴스레터</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.community.CommunityAdminCategoryServlet?p_process=selectList" target="fbody">커뮤니티카테고리관리</a><br>            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.HomePagePreCourseAdminServlet?p_process=selectList" target="fbody">개설예정과정관리</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.HomePageAdminSuperiorityMemberServlet?p_process=selectList" target="fbody">메인 우수회원리스트 관리</a><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.KOpenPowerAdminServlet?p_process=selectList " target="fbody">파워인터뷰관리</a><br>                            
환경설정<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.MenuAdminServlet?p_process=select" target="fbody">운영메뉴관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.MenuSubAdminServlet?p_process=selectMenu" target="fbody">메뉴모듈관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.MenuAuthAdminServlet?p_process=updatePage" target="fbody">메뉴별권한관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.ManagerAdminServlet?p_process=select" target="fbody">운영자관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.CodeAdminServlet?p_process=select" target="fbody">코드관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.ClassifySubjectServlet?p_process=listPage" target="fbody">과정분류</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.SitemapAdminServlet?p_process=select" target="fbody">운영자사이트맵</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.MemberSearchServlet?p_process=memberSearchPage" target="fbody">회원조회</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.PdsAdminServlet?p_process=" target="fbody">운영자자료실</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.OutUserAdminServlet?p_process=select" target="fbody">업체관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.DiscountServlet?p_process=selectList" target="fbody">할인율관리</a><br>                                        
베타테스트<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BPCourseServlet?p_process=listPage" target="fbody">과정관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BetaMasterFormServlet?p_process=listPage" target="fbody">마스터폼관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BetaSCObjectServlet?p_process=listPage" target="fbody">OBJECT관리</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BetaSCOServlet?p_process=listPage" target="fbody">SCO관리</a><br>                                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.SubjectSimulationServlet?p_process=listPage" target="fbody">과정 모의학습</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BPProjectAdminServlet?p_process=ProjectQuestionsAdmin" target="fbody">리포트 출제관리</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.beta.BPExamQuestionServlet?p_process=ExamQuestionListPage" target="fbody">평가문제 출제관리</a><br>                    
통계/분석<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.statistics.StatisticsAdminServlet?p_process=StatisticsExe" target="fbody">매출현황</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.statistics.UserCountServlet?p_process=selectYearCnt&s_grcode=ALL" target="fbody">회원등록현황</a><br>                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.CourseStateAdminServlet?p_process=selectPre" target="fbody">과정운영 통계</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.system.CountServlet?p_process=selectMonthDay&s_grcode=ALL" target="fbody">사용자로그</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.research.SulmunAllResultServlet?p_process=SulmunResultPage" target="fbody">설문통계</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.statistics.SubjYnAdminServlet?p_process=SubjYnExe" target="fbody">신규/폐강과정 현황</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.CompleteStatusAdminServlet?p_process=CompleteRateList" target="fbody">수료율 통계</a><br>                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.analysis.InclinationAdminServlet?p_process=jobInclinationList" target="fbody">학습자성향</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.analysis.InclinationAdminServlet?p_process=routeList" target="fbody">학습자경로</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.analysis.InclinationAdminServlet?p_process=satisfactionList" target="fbody">학습자만족도</a><br>                    
행정서비스<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.polity.DiplomaAdminServlet?p_process=selectList" target="fbody">수료증관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.polity.TaxAdminServlet?p_process=selectList" target="fbody">세금계산서관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.polity.AccountAdminServlet?p_process=selectList" target="fbody">계좌이체관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.polity.SettlementAdminServlet?p_process=selectList" target="fbody">카드결제관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.polity.RefundmentAdminServlet?p_process=selectList" target="fbody">환불신청관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.goyong.GoYongManageServlet?p_process=HuryunExe" target="fbody">훈련실시신고</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.goyong.GoYongManageServlet?p_process=SuryoJeung" target="fbody">수료증발급대장</a><br>                                
강사지원<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorAdminServlet?p_process=TutorList" target="fbody">강사등록</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorActionAdminServlet?p_process=listPage" target="fbody">강사활동관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorValuationAdminServlet?p_process=listPage" target="fbody">강사평가관리</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorAdminMainServlet?p_process=listPage&p_action=go" target="fbody">강사메인</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorActionAdminServlet?p_process=listPage2&s_action=go" target="fbody">활동관리</a><br>                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.NoticeAdminServlet?p_process=selectList" target="fbody">강사공지사항</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.AdminQnaServlet?p_process=" target="fbody">강사질문방</a><br>                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorMessageAdminServlet?p_process=listPage" target="fbody">교수메세지</a><br>                                    
교재판매<br>                                                                                                                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.book.BookAdminServlet?p_process=listPage" target="fbody">교재관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.book.BookSellAdminServlet?p_process=listPage" target="fbody">교재판매관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.book.BookSellAdminServlet?p_process=cancelPage" target="fbody">교재환불관리</a><br>                                
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.book.BookSellAdminServlet?p_process=listPageNew" target="fbody">교재판매관리(NEW)</a><br>                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPCourseServlet?p_process=listPage" target="fbody">교육과정관리</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPCourseSeqServlet?p_process=listPage" target="fbody">교육과정 차수관리</a><br>                                    
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPEduStudentServlet?p_process=listPage" target="fbody">입과 인원 조회</a><br>                                        
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPResultServlet?p_process=listPage" target="fbody">교육결과등록</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPEduStatusServlet?p_process=listPage" target="fbody">학습현황</a><br>                                            
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.cp.CPCourseServlet?p_process=listPage3" target="fbody">과정운영관리</a><br>                                        
<% } else if (v_gadmin.equals("P1")) { %>
학습현황<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudentStatusAdminServlet?p_process=StudentMemberCountList" target="fbody">입과인원조회</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudentStatusAdminServlet?p_process=StudentMemberList" target="fbody">입과명단조회</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyStatusAdminServlet?p_process=LearningTimeSelectList" target="fbody">학습시간조회</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyTotalStatusServlet?p_process=StudyTotalList" target="fbody">종합학습현황</a><br><br>
과정운영<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SubjGongAdminServlet?p_process=selectPre" target="fbody">공지사항</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.DicSubjAdminServlet?p_process=selectPre" target="fbody">용어사전</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.CourseProgressAdminServlet?p_process=selectPre" target="fbody">과정차수별진행상황</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ToronAdminServlet?p_process=ToronList" target="fbody">토론방</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ProjectAdminServlet?p_process=ProjectQuestionsAdmin" target="fbody">리포트출제관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.ProjectAdminServlet?p_process=ProjectSubmitAdmin" target="fbody">리포트평가관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.study.StudyAdminDataServlet?p_process=adminList" target="fbody">과정별자료실</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.course.SubjShamAdminServlet?p_process=selectPre" target="fbody">과정모의학습</a><br><br>
수료<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.FinishServlet?p_process=listPage" target="fbody">수료처리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.complete.CompleteStatusAdminServlet?p_process=CompleteMemberList" target="fbody">과정별교육이력</a><br><br>
설문/평가<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamQuestionServlet?p_process=ExamQuestionListPage" target="fbody">평가문제관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamMasterServlet?p_process=ExamMasterListPage" target="fbody">평가마스터관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamPaperServlet?p_process=ExamPaperListPage" target="fbody">평가문제지관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.exam.ExamResultServlet?p_process=ExamResultListPage" target="fbody">평가결과분석</a><br><br>
홈페이지<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.NoticeAdminServlet?p_process=select" target="fbody">공지사항관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.homepage.QnaAdminServlet?p_process=selectList"  target="fbody">통합Q&A관리</a><br><br>
강사지원<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorAdminMainServlet?p_process=listPage&p_action=go" target="fbody">강사메인</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorActionAdminServlet?p_process=listPage2&s_action=go" target="fbody">활동관리</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.NoticeAdminServlet?p_process=selectList" target="fbody">강사공지사항</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.AdminQnaServlet?p_process=" target="fbody">강사질문방</a><br><br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.tutor.TutorMessageAdminServlet?p_process=listPage" target="fbody">교수메세지</a><br><br>

<% } %>
오프라인<br>
&nbsp;&nbsp;&nbsp;<a href="/servlet/controller.off.OffBatchRecordsAdminServlet?p_process=FileToDBPage" target="fbody">배치작업</a><br><br>
</p>
</body>
</html>
