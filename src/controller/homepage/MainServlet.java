// **********************************************************

// 1. 제 목: 공지사항 제어하는 서블릿(사용자)
// 2. 프로그램명 : MainServlet.java
// 3. 개 요: 공지사항 제어 프로그램(사용자)
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2005. 7. 13
// 7. 수 정1:
// **********************************************************
package controller.homepage;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;

import com.credu.common.DomainUtil;
import com.credu.homepage.HomeNoticeBean;
import com.credu.homepage.HomePageContactBean;
import com.credu.homepage.KoccaMainBean;
import com.credu.homepage.LoginBean;
import com.credu.homepage.MainHomeTypeBBean;
import com.credu.homepage.MainMemberJoinBean;
import com.credu.homepage.NoticeAdminBean;
import com.credu.homepage.TutorLoginBean;
import com.credu.infomation.GoldClassHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.FreeMailBean;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.propose.ProposeCourseBean;
import com.credu.templet.TempletBean;

import ipin.Interop;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.MainServlet")
public class MainServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        String hostname = "";


        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            LoginBean bean = new LoginBean();

            box.setSession("s_gubun", "0"); // 대메뉴 초기화 (대메뉴 이동시 구분값은 param 값으로 처리)

            hostname = request.getHeader("Host");

//            hostname = "freesem.edukocca.or.kr";
//            System.out.println("v_process :: " + v_process);

            box.put("p_hostname", hostname);

            // 구 모바일 콘텐츠 사이트로 이동
            // 새로운 모바일 웹/앱 오픈시 안내 페이지로 변경 필요
            if (box.get("p_hostname").indexOf("edum.kocca.or.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='/servlet/controller.mobile.main.MainServlet'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();

                // 새로운 모바일 웹 페이지 주소
                // 비 로그인 기반이므로 열린강좌 인기 목록을 시작 페이지로 설정
            } else if (box.get("p_hostname").indexOf("m.edu.kocca.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='/servlet/controller.mobile.openclass.OpenClassPopularServlet'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();
                // 모바일 콘텐츠 사이트로 이동
            } else if (box.get("p_hostname").indexOf("edu.kocca.co.kr") > -1 || box.get("p_hostname").indexOf("edu.kocca.or.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='http://edulms.kocca.kr'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();
                // KOCCA 사이버아카데미 홈페이지로 이동
            } else {

            	if (ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
                }

                box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

                if (v_process.equals("authChange")) { // 권한 변경햇을때
                    // 권한세션 변경
                    AlertManager alert = new AlertManager();

                    String v_auth = box.getString("p_auth");
                    String v_userip = request.getHeader("X-Forwarded-For");
                    if (v_userip == null || v_userip.equals("")) {
                        v_userip = request.getRemoteAddr();
                    }

                    box.setSession("gadmin", v_auth);
                    box.setSession("grtype", bean.getGrtype(box));

                    String v_serno = box.getSession("serno"); // 강사 접속 로그 일련번호
                    int v_serno1 = 0; // 강사 접속 로그 일련번호

                    if (v_auth.equals("A1")) { // A1 일때 아이피 체크
                        box.put("p_userip", v_userip);
                        TutorLoginBean tbean = new TutorLoginBean();
                        if (tbean.adminCheck(box) <= 0) {
                            alert.alertFailMessage(out, "접근이 불가능한 IP입니다.");
                            box.setSession("gadmin", "ZZ");
                            return;
                        }
                    }

                    if (v_auth.equals("P1") && v_serno.equals("")) { // 권한이 강사일 경우 강사 로그인 정보에 입력한다.
                        TutorLoginBean tbean = new TutorLoginBean();
                        v_serno1 = tbean.tutorLogin(box);
                        box.setSession("serno", v_serno1);
                    }
                    // 검색조건에서 과정의 전체선택 가능여부를 결정한다.
                    if (v_auth.equals("P1"))
                        box.setSession("isSubjAll", "tutor");
                    else
                        box.setSession("isSubjAll", "true");

                    this.performMainList(request, response, box, out);
                } else if (v_process.equals("popupview")) {
                    this.performPopupView(request, response, box, out);
                } else if (v_process.equals("selectNoticeList")) {
                    this.performSelectNoticeList(request, response, box, out);
                } else if (v_process.equals("selectNoticeView")) {
                    this.performSelectNoticeView(request, response, box, out);
                } else if (v_process.equals("usermail")) { // 사용자 메일작성폼
                    this.performUsermail(request, response, box, out);
                } else if (v_process.equals("usermailsend")) { // 사용자 메일작성
                    this.performUsermailSend(request, response, box, out);
                } else if (v_process.equals("ASP_Login")) { // ASP 로그인
                    this.performASP_Login(request, response, box, out);
                } else if (v_process.equals("ASP_Login_Insert")) { // ASP 회원 가입
                    this.performASP_Login_Insert(request, response, box, out);
                } else if (v_process.equals("ASP_Find_Idpw")) { // ASP 아이디/패스워드
                    // 찾기
                    this.performASP_Find_Idpw(request, response, box, out);
                } else if (v_process.equals("ASP_Login_Update")) { // ASP 회원정보수정
                    this.performASP_Login_Update(request, response, box, out);
                } else if (v_process.equals("MainLogin")) { // 메인홈페이지(KOCCA) 로그인
                    this.performMainLogin(request, response, box, out);
                } else if (v_process.equals("MemberInfoUpdateCheck")) { // 개인정보 변경 여부 확인 후 로그인..(이벤트 후 주석 처리 해야함)

                    String v_eventyn = "";
                    String v_nextchange = "";
                    DataBox eventynCheck = bean.eventynCheck(box);

                    if (eventynCheck != null) {
                        v_eventyn = eventynCheck.getString("d_eventyn");
                        v_nextchange = eventynCheck.getString("d_nextchange");
                    }

                    if (v_eventyn.equals("Y")) {
                        this.performMainList(request, response, box, out);
                    } else if (v_nextchange.equals("Y")) {
                        this.performMainList(request, response, box, out);
                    }
                    this.performMemberInfoUpdateCheck(request, response, box, out);
                } else if (v_process.equals("nextChange")) { // 개인정보 변경 여부 확인 후 로그인..다음에 변경하기(이벤트 후 주석 처리 해야함)
                    this.performNextChange(request, response, box, out);
                } else if (v_process.equals("researchgate")) {
                    this.performReseachGate(request, response, box, out); // 교육수요조사 게이트
                } else if (v_process.equals("research")) {
                    this.performReseach(request, response, box, out); // 사이버콘텐츠아카데미 교육 수요조사
                } else if (v_process.equals("research_2")) {
                    this.performResearch_2(request, response, box, out); // 사이버콘텐츠아카데미 교육 수요조사2
                } else if (v_process.equals("research_save")) {
                    this.performResarch_save(request, response, box, out);
                } else if (v_process.equals("passChangePage")){
                	this.performPassChangePage(request, response, box, out);
                } else if (v_process.equals("agreeChkPage")){
                	this.performAgreeChkPage(request, response, box, out);
                } else { // 메인
                    this.performMainList(request, response, box, out);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 메인
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("rawtypes")
	public void performMainList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {

        try {
            // 접속 URL정보
            box.put("p_servernm", request.getServerName());

            /* localhost 접속시 강제 grcode 부여
			본서버에서는 주석 처리 해야한다.
			N000001 : 한국콘텐츠아카데미
			N000022 : 문화체육관광부
			N000057 : test
			N000134 : 한국콘텐츠진흥원 / contenttest / contenttest
			N000179 : test2
			N000203 : 경기게임마이스터고
			N000210 : 한국콘텐츠진흥원(콘텐츠 성평등센터) / ktest2103 / 1q2w3e4r!
			N000213 : 국민체육진흥공단
			N000215 : 동부건설
			N000241 : 한국예술복지재단
			N000243 : 서울항공비즈니스고등학교
			N000244 : 지역문화진흥원
			N000257 : 한국문학번역원
			*/
            box.setSession("tem_grcode", "N000244");

            TempletBean bean = new TempletBean();
            DataBox listBean = bean.SelectGrcodeExists(box);

            String type = "B";

            if (listBean == null) {
                box.setSession("tem_type", "B");
                box.setSession("tem_grcode", "N000001");
                box.setSession("tem_menu_type", ""); // 메뉴 네비게이션 타입
                box.setSession("tem_main_type", ""); // 메인 화면 타입
            } else if (listBean != null && !listBean.get("d_grcode").equals("")) {
                box.setSession("tem_menu_type", listBean.get("d_menutype")); // 메뉴 네비게이션 타입
                box.setSession("tem_grcode", listBean.get("d_grcode"));
                box.setSession("tem_main_type", listBean.get("d_type")); // 메인 화면 타입
                box.setSession("tem_type", listBean.get("d_type"));
                type = listBean.get("d_type");
            } else {
                box.setSession("tem_type", "B");
                box.setSession("tem_grcode", "N000001");
                box.setSession("tem_menu_type", ""); // 메뉴 네비게이션 타입
                box.setSession("tem_main_type", ""); // 메인 화면 타입
            }

            // URL별 GRCODE 가져오기
            if (box.getSession("tem_grcode").equals("") || box.getSession("tem_grcode") == null) {
                DataBox serverInfo = bean.getGroupInfo(box);

                if (serverInfo != null) {
                	type = serverInfo.get("d_type");
                	box.setSession("tem_type", serverInfo.get("d_type"));
                	box.setSession("tem_grcode", serverInfo.get("d_grcode"));
                } else {
                	type = "A";
                	box.setSession("tem_type", "A");
                	box.setSession("tem_grcode", "N000001");
                }
            }

            // 접속자 IP
            box.put("p_userip", box.get("userip"));

            // GRCODE별 return URL 설정
            String v_url = "";
            String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            if (tem_grcode.equals("")) {

                // GRCODE 소실시...에러 페이지?
                v_url = "/learn/user/portal/homepage/error.jsp";

            } else {
                if (box.getSession("gadmin").equals("P101")) {
                    v_url = "/learn/user/2012/portal/homepage/zu_subj_prof.jsp";
                    ProposeCourseBean courseBean = new ProposeCourseBean();
                    List mainSubjectList = courseBean.selectProfSubjectList(box);
                    request.setAttribute("SubjectList", mainSubjectList);
                } else {
                    if (tem_grcode.equals("N000001")) { // 이후 KoccaYn으로 통합/ASP 구분 통합 메인
                    	NoticeAdminBean nbean = new NoticeAdminBean();

                        int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
                        if (tabseq == 0) {

                            // 게시판 분류에 대한 부분 세팅
                            box.put("p_type", "HN");
                            box.put("p_grcode", "0000000");
                            box.put("p_comp", "0000000000");
                            box.put("p_subj", "0000000000");
                            box.put("p_year", "0000");
                            box.put("p_subjseq", "0000");

                            tabseq = nbean.selectTableseq(box);

                            if (tabseq == 0) {
                                String msg = "게시판정보가 없습니다.";
                                AlertManager.historyBack(out, msg);
                            }

                            box.put("p_tabseq", String.valueOf(tabseq));
                        }

                        v_url = "/learn/user/2013/portal/homepage/zu_Main_KOCCA.jsp";

                        box.setSession("KoccaYn", "Y");
                        if (box.get("p_month").length() > 0) {
                            String rdURL = "/servlet/controller.course.EduScheduleHomePageServlet?"
                                    + "p_process=schlMonthPlanList&menuid=11&gubun=1&p_month=" + box.get("p_month");
                            ServletContext sc = getServletContext();
                            RequestDispatcher rd = sc.getRequestDispatcher(rdURL);
                            rd.forward(request, response);
                        }

                        // 배너 목록
                        KoccaMainBean mainBean = new KoccaMainBean();
                        request.setAttribute("mainBannerList", mainBean.selectBannerList(box));

                        // 공지사항 목록
                        request.setAttribute("mainNoticeList", mainBean.selectNoticeList(box));

                        // 인기검색어 목록
                        request.setAttribute("popularKeywordList", mainBean.selectPopularKeywordList(box));

                        // 아카데미 이야기 목록
                        // request.setAttribute("academyStoryList", mainBean.selectAcademyStoryList(box));

                        // 메인 카테고리 항목
                        request.setAttribute("mainCategoryList", mainBean.selectMainCategoryList(box));

                        // 팝업공지 리스트
                        ArrayList pnlist = nbean.selectListNoticePopupHome(box);
                        request.setAttribute("noticePopup", pnlist);

                    } else {
                    	box.setSession("KoccaYn", "N");
                    	box.put("authority", "");

                    	if(type.equals("B")){
                    		MainHomeTypeBBean bBean = new MainHomeTypeBBean();

                    		// 정규강좌 리스트
                    		ArrayList subjectList = bBean.selectSubjectList(box);
                    		request.setAttribute("subjectList", subjectList);

                    		// 열린강좌 리스트
                    		ArrayList goldClassList = bBean.selectGoldClassList(box);
                    		request.setAttribute("goldClassList", goldClassList);

                    		box.put("p_tabseq", 12); //게시판 seq 셋팅
                    		// 공지사항 리스트
                    		ArrayList noticeList = bBean.selectNoticeList(box);
                    		request.setAttribute("noticeList", noticeList);

                    		box.put("p_popup", "Y"); //팝업 조건 셋팅
                    		// 팝업 리스트
                    		ArrayList noticePopup = bBean.selectNoticeList(box);
                    		request.setAttribute("noticePopup", noticePopup);
                    		v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";

                    	}else{
                    		NoticeAdminBean nbean = new NoticeAdminBean();

                            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
                            if (tabseq == 0) {

                                // 게시판 분류에 대한 부분 세팅
                                box.put("p_type", "HN");
                                box.put("p_grcode", "0000000");
                                box.put("p_comp", "0000000000");
                                box.put("p_subj", "0000000000");
                                box.put("p_year", "0000");
                                box.put("p_subjseq", "0000");

                                tabseq = nbean.selectTableseq(box);

                                if (tabseq == 0) {
                                    String msg = "게시판정보가 없습니다.";
                                    AlertManager.historyBack(out, msg);
                                }

                                box.put("p_tabseq", String.valueOf(tabseq));
                            }

	                    	// ASP 공지사항
	                        HomeNoticeBean noticeBean = new HomeNoticeBean();
	                        ArrayList noticeList = noticeBean.selectDirectList(box);
	                        request.setAttribute("noticeList", noticeList);

	                        // ASP 실무강좌
	//                        PracticalCourseHomePageBean Pbean = new PracticalCourseHomePageBean();
	//                        List practicalList = Pbean.selectList(box);
	//                        request.setAttribute("practicalList", practicalList);


	                        //열린강좌
	                        GoldClassHomePageBean Gbean = new GoldClassHomePageBean();
	                        List goldclassList = Gbean.selectList(box);
	                        request.setAttribute("goldclassList", goldclassList);

	                        // 팝업공지 리스트
	                        ArrayList pnlist = nbean.selectListNoticePopupHome(box);
	                        request.setAttribute("noticePopup", pnlist);

	                        v_url = "/learn/user/portal/homepage/zu_Online_ASP.jsp";
                    	}
                    }
                }
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 팝업상세보기
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPopupView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);

            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // 게시판 분류에 대한 부분 세팅
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");

                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            if (box.getString("p_useframe").equals("Y")) {
                v_url = "/learn/user/portal/homepage/zu_Notice_popOnlycontY.jsp";
            } else {
                v_url = "/learn/user/portal/homepage/zu_Notice_popOnlycontN.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 공지사항리스트
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectNoticeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);
            // 공지사항 시작
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // 게시판 분류에 대한 부분 세팅
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            ArrayList list1 = nbean.selectListNoticeAllHome(box);
            request.setAttribute("selectNoticeListAll", list1);

            ArrayList list2 = nbean.selectListNoticeHome(box);
            request.setAttribute("selectNoticeList", list2);

            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_L.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 공지사항상세보기
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectNoticeView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);
            // 공지사항 시작
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // 게시판 분류에 대한 부분 세팅
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");

                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "게시판정보가 없습니다.";
                    AlertManager.historyBack(out, msg);
                }
                box.put("p_tabseq", String.valueOf(tabseq));
            }

            DataBox dbox = nbean.selectViewNotice(box);
            request.setAttribute("selectNotice", dbox);
            String v_url = "";
            v_url = "/learn/user/homepage/zu_Notice_R.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메일 보내기폼
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsermail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/homepage/zu_Usermail.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            // Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메일 전송
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsermailSend(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            LoginBean bean = new LoginBean();
            int isOk = bean.insertUserMail(box);

            String v_msg = "";
            AlertManager alert = new AlertManager();
            if (isOk > 0) {
                v_msg = "전송되었습니다.";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "전송에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUsermailSend()\r\n" + ex.getMessage());
        }
    }

    public void performASP_Login(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps =
            // DomainUtil.getHttpsDomain(request.getRequestURL().toString());
            ///System.out.println("MainServlet -- performASP_Login");
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String gubun = box.getString("gubun");
            String v_url = "/learn/user/portal/homepage/zu_Online_ASP_Sub.jsp";

            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";
            }

            if (gubun.equals("20")) { // 중복 아이디 체크
                LoginBean bean = new LoginBean();
                int isOk = bean.exists_ID(box);
                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("25")) { // 중복 이메일 체크
                LoginBean bean = new LoginBean();
                int isOk = bean.exists_EMAIL(box);
                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("30")) { // 로그아웃
                box.put("gubun", "");
                box.sessionInvalidate();
                response.sendRedirect("/");
//                performMainList(request, response, box, out);
            }

            if (gubun.equals("40")) { // 아이디/ 패스워드 찾기
                LoginBean bean = new LoginBean();
                String isOk = bean.getASP_Userid(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("50")) { // 암호 찾기
                LoginBean bean = new LoginBean();
                String isOk = bean.selectTempPassword(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("60")) { // 로그인
                // 메인 화면에 오지 않고 외부에서 바로 오기 때문에
                box.put("p_servernm", request.getServerName());

                TempletBean bean = new TempletBean();
                DataBox listBean = bean.SelectGrcodeExists(box);

                NoticeAdminBean nbean = new NoticeAdminBean(); // ASP 팝업공지

                if (box.getString("p_aspgubun").equals("kocsc")) {// kocsc 인경우 userid를 받아와서 pw를 가져온다.
                    DataBox kocscSsoPwd = bean.getKocscSsoPwd(box);
                    box.put("p_pw", kocscSsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("edu1")) {// edu1 인경우 userid를 받아와서 pw를 가져온다.
                    DataBox edu1SsoPwd = bean.getEdu1SsoPwd(box);
                    box.put("p_pw", edu1SsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("hns")) {// hns 인경우 userid를 확인후 가입 or pw를 가져온다.
                    DataBox HnsSsoPwd = bean.checkHnsUserid(box);
                    box.put("p_pw", HnsSsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("N000083")) {// kocuco-hi 인경우 userid를 확인 후 가입 or pw를 가져온다.
                    DataBox Kocucohi = bean.checkKocucohi(box);
                    box.put("p_id", Kocucohi.get("d_userid"));
                    box.put("p_pw", Kocucohi.get("d_pwd"));
                }

                if (listBean != null && !listBean.get("d_grcode").equals("")) {
                    box.setSession("tem_menu_type", listBean.get("d_menutype")); // 메뉴 네비게이션 타입
                    box.setSession("tem_grcode", listBean.get("d_grcode"));
                    box.setSession("tem_main_type", listBean.get("d_type")); // 메인 화면 타입
                } else {
                    box.setSession("tem_type", "A");
                    box.setSession("tem_grcode", "N000001");
                    box.setSession("tem_menu_type", ""); // 메뉴 네비게이션 타입
                    box.setSession("tem_main_type", ""); // 메인 화면 타입
                }

                 // URL별 GRCODE 가져오기
                if (box.getSession("tem_grcode").equals("") || box.getSession("tem_grcode") == null) {
                    DataBox serverInfo = bean.getGroupInfo(box);

                    if (serverInfo == null) {
                        box.setSession("tem_type", "A");
                        box.setSession("tem_grcode", "N000001");
                    } else {
                        box.setSession("tem_type", serverInfo.get("d_type"));
                        box.setSession("tem_grcode", serverInfo.get("d_grcode"));
                    }
                }

                LoginBean bean1 = new LoginBean();
                String isOk = bean1.getASP_Login(box, request);

                // 접속자 IP
                String v_userip = request.getHeader("X-Forwarded-For");
                if (v_userip == null || v_userip.equals("")) {
                    v_userip = request.getRemoteAddr();
                }

                box.put("p_userip", v_userip);

                // String tem_grcode = box.getStringDefault("tem_grcode",
                // box.getSession("tem_grcode"));
                box.setSession("KoccaYn", "N");

                /*
                // 공지사항 시작
                HomeNoticeBean noticeBean = new HomeNoticeBean();
                HomeLetterBean letterBean = new HomeLetterBean();
                ProposeCourseBean courseBean = new ProposeCourseBean();

                // ASP 공지사항
                ArrayList noticeList = noticeBean.selectDirectList(box);
                request.setAttribute("noticeList", noticeList);

                // ASP 뉴스레터
                ArrayList letterList = letterBean.selectDirectList(box);
                request.setAttribute("letterList", letterList);

                // ASP 강좌모음
                List mainSubjectList = courseBean.selectMainSubjectList(box);
                request.setAttribute("mainSubjectList", mainSubjectList);

                // ASP 실무강좌
                PracticalCourseHomePageBean Pbean = new PracticalCourseHomePageBean();
                List practicalList = Pbean.selectList(box);
                request.setAttribute("practicalList", practicalList);

                // 팝업공지 리스트
                ArrayList pnlist = nbean.selectListNoticePopupHome(box);
                request.setAttribute("noticePopup", pnlist);
                */

                AlertManager alert = new AlertManager();

                if (isOk.equals("")) {
                    box.put("p_process", "");

                    String v_servernm = request.getServerName();

                    int isUserid = bean1.getASP_Login_Userid(box);
                    int isPwd = bean1.getASP_Login_Pwd(box);

                    if (isUserid == 0) {
                    	// 문체부 비정상적인 로그인처리
                    	if( v_servernm.equals("mcst.kocca.or.kr") || v_servernm.equals("mcst.edukocca.or.kr") ){
                    		alert.alertOkMessage(out, "정상적인 접근방법이 아닙니다.", "/servlet/controller.homepage.MainServlet", box);
                    	}else{
                    		alert.alertOkMessage(out, "입력하신 회원은 존재 하지 않습니다.", "/servlet/controller.homepage.MainServlet", box);
                    	}
                        return;
                    }

                    if (isPwd == 0) {
                        alert.alertOkMessage(out, "비밀번호가 맞지 않습니다.", "/servlet/controller.homepage.MainServlet", box);
                        return;
                    }

                } else {
                	//String v_mainurl = strHttp + "/servlet/controller.homepage.MainServlet";
                	String v_mainurl = "/servlet/controller.homepage.MainServlet";
                	String authList = bean1.selectAuthList(box);
                    box.setSession("authList", authList);
                    String msg = "";
                    box.put("p_process", "");

                	if (box.getSession("agreechk").equals("N")) {
                		box.put("p_process", "agreeChkPage");
                		box.put("gubun", "88");

                        alert.alertOkMessage(out, "", v_mainurl, box);
                        return;
                	}

                	ArrayList<String> ssoGrcodeList = new ArrayList<String>(Arrays.asList(new String[] {"N000001", "N000022"}));
                    if(!ssoGrcodeList.contains(box.getSession("tem_grcode"))) {
                    	Boolean needPassChange = false;

                    	if (box.getSession("passchangedt") == null || box.getSession("passchangedt").equals("")) {
                    		needPassChange = true;
                    	} else {
                    		Date nowDt = new Date();
                        	SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        	if (nowDt.after(transFormat.parse(box.getSession("passchangedt")))) {
                        		needPassChange = true;
                        	} else {
                        		needPassChange = false;
                        	}
                    	}

                    	if (needPassChange) {
                    		box.put("p_process", "passChangePage");
                    		box.put("gubun", "80");
                    		msg = "비밀번호를 변경한지 3개월 이상 경과하였습니다. 개인정보보호를 위해 비밀번호를 변경해주세요.";
                    	}

                    }

                    alert.alertOkMessage(out, msg, v_mainurl, box);
                    return;
                }
            }

            if (gubun.equals("70")) // 회원정보수정
            {
                LoginBean bean = new LoginBean();
                DataBox dbox = bean.ASP_Edit_Login(box);
                System.out.println(dbox);
                request.setAttribute("ASP_Edit_Login", dbox);
            }

            if (gubun.equals("89")) // 사이트이용동의 수정
            {
            	String v_mainurl = "/servlet/controller.homepage.MainServlet";
            	AlertManager alert = new AlertManager();

                LoginBean bean = new LoginBean();
                int isOK = bean.ASP_updateAgreeChk(box);
                String msg = "귀하는 단체가입 회원에 해당되므로, 개인정보보호를 위해 비밀번호 변경을 권장드립니다.";

        		box.put("p_process", "passChangePage");
        		box.put("p_id", box.getSession("userid"));
        		box.put("gubun", "80");

                alert.alertOkMessage(out, msg, v_mainurl, box);

                return;
            }

            if (gubun.equals("100")) // 암호 변경
            {
                LoginBean bean = new LoginBean();
                int tmp = bean.changePwd(box);

                if(box.getSession("tem_type").equals("B")) {
                	box.put("gubun", "");
                    box.sessionInvalidate();

                    AlertManager alert = new AlertManager();
                    box.put("p_process", "");
                	alert.alertOkMessage(out, "비밀번호가 변경되었습니다. 다시 로그인 해주십시오", "/servlet/controller.homepage.MainServlet", box);
                } else {
                	request.setAttribute("ASP_Edit_Login", tmp);
                    box.put("dbLoad_ID_Exists", tmp);
                    v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
                }

            }

            if (gubun.equals("110")) // 실명인증에서 이름과 주민번호를 입력하고 기존 가입되어 있는지 여부를 확인한다.
            {
                String resno = box.getString("p_resno1") + box.getString("p_resno2");
                String flag = "JID";
                String info[] = null;
                String dupinfo = "";
                String conninfo = "";

                Interop interop = new Interop(); // DI,CI 생성을 위해
                info = interop.Interop(resno, flag).split(";"); // DI를 가져온다.
                dupinfo = info[0];
                conninfo = info[1];

                box.setSession("dupinfo", dupinfo);
                box.setSession("conninfo", conninfo);

                LoginBean bean = new LoginBean();
                int tmp = bean.userIdentify(box);
                request.setAttribute("ASP_Edit_Login", tmp);
                box.put("dbLoad_ID_Exists", tmp);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }
            ServletContext sc = getServletContext();

            // 방송통신심의위원회,사내연수원,홈앤쇼핑인 경우 로그인 후 나의 강의실로 이동한다.
            if (box.getString("p_aspgubun").equals("kocsc") || box.getString("p_aspgubun").equals("edu1") || box.getString("p_aspgubun").equals("hns")) {
                v_url = strHttp + "/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage&gubun=3";
            }

            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            // Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performASP_Find_Idpw(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String gubun = box.getString("gubun");
            String v_url = "/learn/user/portal/homepage/zu_Online_ASP_Sub.jsp";
            // String v_url =
            // "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";

            if (gubun.equals("40")) // 아이디 찾기
            {
                FreeMailBean bean = new FreeMailBean();
                //String isOk = bean.findIdFreeMail(box);

                // sets SMTP server properties
                Properties properties = new Properties();
                //properties.put("mail.smtp.host", "210.96.133.67");
                properties.put("mail.smtp.host", "mail2.kocca.kr");
                properties.put("mail.smtp.port", "9110");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");

                // creates a new session with an authenticator
                Authenticator auth = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("academy@kocca.kr", "academy123!");
                    }
                };

                Session session = Session.getInstance(properties, auth);

                // creates a new e-mail message
                Message msg = new MimeMessage(session);

                msg.setFrom(new InternetAddress("academy@kocca.kr"));
                InternetAddress[] toAddresses = { new InternetAddress("sjgf1177@nate.com") };
                msg.setRecipients(Message.RecipientType.TO, toAddresses);
                msg.setSubject("mail test1");
                msg.setSentDate(new Date());
                msg.setText("mail test2");

                // sends the e-mail
                Transport.send(msg);

/*                Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("academy@kocca.kr", "academy123!");
                    }
                });*/

/*                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("academy@kocca.kr"));
                InternetAddress[] toAddresses = { new InternetAddress("sjgf1177@nate.com") };
                msg.setRecipients(Message.RecipientType.TO, toAddresses);
                msg.setSubject("mail test1");
                msg.setSentDate(new Date());
                msg.setText("mail test2");*/

                // sends the e-mail
                Transport.send(msg);

                String isOk = "true:sjgf1177@nate.com";

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("50")) // 암호 찾기
            {
                FreeMailBean bean = new FreeMailBean();
                String isOk = bean.findPwFreeMail(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            ServletContext sc = getServletContext();

            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            // Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performASP_Login_Insert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            String v_url = "/learn/user/portal/homepage/zu_Online_ASP_Sub.jsp";

            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";
            }

            LoginBean bean = new LoginBean();
            // int isOk = bean.insertUser_Compony(box);
            bean.insertUser_Compony(box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            // Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performASP_Login_Update(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            String strHttp = DomainUtil.getHttpDomain(request.getRequestURL().toString());
            // String strHttps = DomainUtil.getHttpsDomain(request.getRequestURL().toString());

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            // String v_url = "/learn/user/portal/homepage/zu_Online_ASP.jsp";
            String v_url = "/servlet/controller.homepage.MainServlet";
            String v_msg = "";

            LoginBean bean = new LoginBean();
            int isOk = bean.ASP_updateUser(box);

            //v_url = strHttp + v_url; // 수정후 http로 보낸다 HTJ

            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "개인정보가 변경되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "개인정보 변경에 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

            // ServletContext sc = getServletContext();
            // RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            // rd.forward(request, response);
            // Log.info.println(this, box, "Dispatch to " + v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    public void performMainLogin(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

        	if(box.getSession("tem_type").equals("B")){
        		v_url = "/learn/user/typeB/homepage/zu_MainLogin.jsp";
        	}else{
        		v_url = "/learn/user/portal/homepage/zu_MainLogin.jsp";
        	}

            box.put("p_process", "");
            // box.put("p_eventgubun", box.getString("p_eventgubun"));
            //취업 이벤트에서 왔다는 상태값을 넘긴다.

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainLogin()\r\n" + ex.getMessage());
        }
    }

    public void performMobile(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "http://edum.kocca.or.kr/mobile/index.html";

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMobie()\r\n" + ex.getMessage());
        }
    }

    // 이벤트 : 개인정보 변경 여부 문의 후 로그인[이벤트 종료 후 주석 혹은 삭제 함]
    public void performMemberInfoUpdateCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/portal/homepage/zu_MemberInfoUpdateCheck.jsp";

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }

        try {
            String v_url = "";
            v_url = "/learn/user/portal/member/zu_MemberInfo_U.jsp";

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다

            MainMemberJoinBean bean = new MainMemberJoinBean();
            DataBox dbox = bean.memberInfoView(box);

            request.setAttribute("memberView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMemberInfoUpdate()\r\n" + ex.getMessage());
        }
    }

    // 다음에 변경하기 : 개인정보 수정 이벤트
    public void performNextChange(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            LoginBean bean = new LoginBean();

            int isOk = bean.nextChange(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.MainServlet";
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performNextChange()\r\n" + ex.getMessage());
        }
    }

    /* 여기부터 끝까지 교육 수요조사 */

    public void performReseachGate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            String p_link = box.getStringDefault("p_link", "A");

            if (p_link.equals("H1") || p_link.equals("H2") || p_link.equals("E1") || p_link.equals("E2") || p_link.equals("B") || p_link.equals("A")) {
                // v_url =
                // "/learn/user/2012/portal/homepage/zu_Research_gate.jsp"; //설문
                // 게이트화면
                box.put("p_process", "");
                String v_msg = "교육서비스 만족도조사 설문기간이 완료 되었습니다.";
                AlertManager alert = new AlertManager();
                v_url = "/servlet/controller.homepage.MainServlet";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                box.put("p_process", "");
                String v_msg = "잘못된 접근입니다.";
                AlertManager alert = new AlertManager();
                v_url = "/servlet/controller.homepage.MainServlet";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }

    public void performReseach(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            // String v_gubun = box.getString("p_gubun");
            v_url = "/learn/user/2012/portal/homepage/zu_Research_A.jsp";

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }

    public void performResearch_2(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/2012/portal/homepage/zu_Research_2.jsp";

            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }

    public void performResarch_save(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            HomePageContactBean bean = new HomePageContactBean();

            String url = "/learn/user/2012/portal/homepage/zu_Reseach_2.jsp";
            int isOk = bean.insertResearch(box);

            String v_msg = "";

            AlertManager alert = new AlertManager();

            box.put("openercount", "3");

            if (isOk > 0) {
                v_msg = "끝까지 응답해주셔서 진심으로 감사합니다. \\n사이버콘텐츠아카데미 서비스 수준 향상을 위한 귀중한 자료로 \\n활용하겠습니다!";
                alert.alertOkMessage(out, v_msg, url, box, true, true);
            } else {
                v_msg = "";
                alert.alertFailMessage(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performNextChange()\r\n" + ex.getMessage());
        }

    }

    public void performPassChangePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }

    public void performAgreeChkPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";
            request.setAttribute("requestbox", box); // 명시적으로 box 객체를 넘겨준다
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }
}
