package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.ClassifySubjectBean;
import com.credu.homepage.EventHomePageBean;
import com.credu.homepage.HomeLetterBean;
import com.credu.homepage.HomeNoticeBean;
import com.credu.homepage.HomePageFAQBean;
import com.credu.homepage.HomePageQnaBean;
import com.credu.homepage.SeminarHomePageBean;
import com.credu.infomation.GoldClassHomePageBean;
import com.credu.infomation.PracticalCourseBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.off.OffClassifySubjectBean;
import com.credu.off.ProposeOffBean;
import com.credu.propose.ProposeCourseBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.SubMenuMainServlet")
public class SubMenuMainServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    */
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out 		= null;
        RequestBox 	box 		= null;
        String 		v_process 	= "";
        
        boolean     v_canRead 	= false;
        boolean     v_canAppend = false;
        boolean     v_canModify = false;
        boolean     v_canDelete = false;
        boolean     v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            
            // 로긴 check 루틴 VER 0.2 - 2003.09.9
			/*if (!AdminUtil.getInstance().checkLogin(out, box)) {
				return; 
			}*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));
			
            if(v_process.equals("INFORMATION")) {     			 		     //  정보광장 메인
            	this.performInformationPage(request, response, box, out);
            } else if(v_process.equals("HELPDESK")) {                        //  학습지원센터 메인
            	this.performHelpDeskPage(request, response, box, out);
            } else if(v_process.equals("ONLINE_COURSE")) {                   //  온라인과정 메인
            	this.performOnlineCoursePage(request, response, box, out);
            } else if(v_process.equals("OFFLINE_COURSE")) {                      //  오프라인과정 메인
            	this.performOfflineCoursePage(request, response, box, out);
            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }
    
    /**
    //  온라인과정 메인페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOnlineCoursePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
             
             ClassifySubjectBean codeBean = new ClassifySubjectBean();
             
             List middleClassList = codeBean.SelectMiddleClassList(box);
             request.setAttribute("middleClassList", middleClassList);
             
             ProposeCourseBean courseBean = new ProposeCourseBean();
             
             List mainSubjectList = courseBean.selectMainSubjectList(box);
             request.setAttribute("mainSubjectList", mainSubjectList);

             // 공지사항
             HomeNoticeBean noticeBean = new HomeNoticeBean();
             
             box.put("onoff", "1");
             ArrayList noticeList = noticeBean.selectDirectList(box);
 	         request.setAttribute("noticeList", noticeList);
 	         
 	         // 뉴스레터
 	         HomeLetterBean letterBean = new HomeLetterBean();
 	         
 	         int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
             if (tabseq == 0) {
                 /*------- 게시판 분류에 대한 부분 세팅 -----*/
                 box.put("p_type", "HN");
                 box.put("p_grcode", "0000000");
                 box.put("p_subj", "0000000000");
                 box.put("p_year", "0000");
                 box.put("p_subjseq", "0000");
                 /*----------------------------------------*/
                 tabseq = letterBean.selectTableseq(box);
                 if (tabseq == 0) {
                     String msg = "게시판정보가 없습니다";  //게시판정보가 없습니다
                     AlertManager.historyBack(out, msg);
                 }
                 box.put("p_tabseq", String.valueOf(tabseq));
             }
	         
	         ArrayList letterList = letterBean.selectDirectList(box);
	         request.setAttribute("letterList", letterList);
 	         
 	         
             // 이벤트
             EventHomePageBean eventBean = new EventHomePageBean();
             
             ArrayList eventList = eventBean.selectList(box);
             request.setAttribute("eventList", eventList);
             
             // 워크샵
             SeminarHomePageBean seminarBean = new SeminarHomePageBean();

             ArrayList seminarList = seminarBean.selectList(box);
             request.setAttribute("seminarList", seminarList);

             ServletContext sc = super.getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/online/zu_Online_Course_M.jsp");
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to /learn/user/portal/online/zu_Online_Course_M.jsp");

         } catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }
    
    /**
    //  오프라인과정 메인페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOfflineCoursePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
             
             OffClassifySubjectBean codeBean = new OffClassifySubjectBean();
             List middleClassList = codeBean.SelectMiddleClassList(box);
             request.setAttribute("middleClassList", middleClassList);
             
             ProposeOffBean courseBean = new ProposeOffBean();
             List mainSubjectList = courseBean.selectMainSubjectList(box);
             request.setAttribute("mainSubjectList", mainSubjectList);
             
             
          // 공지사항
             HomeNoticeBean noticeBean = new HomeNoticeBean();
             
             box.put("onoff", "0");
             ArrayList noticeList = noticeBean.selectDirectList(box);
 	         request.setAttribute("noticeList", noticeList);
 	         
 	         // 뉴스레터
 	         HomeLetterBean letterBean = new HomeLetterBean();
 	         
 	         int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));
             if (tabseq == 0) {
                 /*------- 게시판 분류에 대한 부분 세팅 -----*/
                 box.put("p_type", "HN");
                 box.put("p_grcode", "0000000");
                 box.put("p_subj", "0000000000");
                 box.put("p_year", "0000");
                 box.put("p_subjseq", "0000");
                 /*----------------------------------------*/
                 tabseq = letterBean.selectTableseq(box);
                 if (tabseq == 0) {
                     String msg = "게시판정보가 없습니다";  //게시판정보가 없습니다
                     AlertManager.historyBack(out, msg);
                 }
                 box.put("p_tabseq", String.valueOf(tabseq));
             }
	         
	         ArrayList letterList = letterBean.selectDirectList(box);
	         request.setAttribute("letterList", letterList);
 	         
 	         
             // 이벤트
             EventHomePageBean eventBean = new EventHomePageBean();
             
             ArrayList eventList = eventBean.selectList(box);
             request.setAttribute("eventList", eventList);
             
             
             // 워크샵
             SeminarHomePageBean seminarBean = new SeminarHomePageBean();

             ArrayList seminarList = seminarBean.selectList(box);
             request.setAttribute("seminarList", seminarList);

             ServletContext sc = super.getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/offline/zu_Offline_Course_M.jsp");
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to /learn/user/portal/offline/zu_Offline_Course_M.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }
    
    /**
    //  정보광장 메인페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInformationPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
             
             // 골드클래스
             GoldClassHomePageBean goldclassBean = new GoldClassHomePageBean();
             
             List GoldClassList = goldclassBean.selectMainGoldClassList(box);
             request.setAttribute("GoldClassList", GoldClassList);
             
             List PreGoldClassList = goldclassBean.selectPreGoldClassList(box);
             request.setAttribute("PreGoldClassList", PreGoldClassList);
             
             // 실무강좌
             PracticalCourseBean practicalCourseBean = new PracticalCourseBean();
             List PracticalCourseList = practicalCourseBean.selectListPracticalCourse(box);
             request.setAttribute("PracticalCourseList", PracticalCourseList);

             // 이벤트
             EventHomePageBean eventBean = new EventHomePageBean();
             
             ArrayList eventList = eventBean.selectList(box);
             request.setAttribute("eventList", eventList);
             
             
             // 워크샵
             SeminarHomePageBean seminarBean = new SeminarHomePageBean();

             ArrayList seminarList = seminarBean.selectList(box);
             request.setAttribute("seminarList", seminarList);

             ServletContext sc = super.getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/information/zu_Information_M.jsp");
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to /learn/user/portal/information/zu_Information_M.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }
    
    /**
    //  헬프데스크 메인페이지
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performHelpDeskPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
             
             //FAQ
             HomePageFAQBean faqBean = new HomePageFAQBean();
             
             ArrayList faqCategoryList = faqBean.selectFaqCategoryList(box);
             request.setAttribute("faqCategoryList", faqCategoryList);

             ArrayList faqList = faqBean.selectListBestFaq(box);
 			 request.setAttribute("faqList", faqList);
             
             // QNA
             HomePageQnaBean qnaBean = new HomePageQnaBean();

             ArrayList qnaList = qnaBean.selectListQna(box);
             request.setAttribute("qnaList", qnaList);
             
             // 공지사항
             HomeNoticeBean noticeBean = new HomeNoticeBean();
             
             ArrayList noticeList = noticeBean.selectDirectList(box);
 	         request.setAttribute("noticeList", noticeList);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/helpdesk/zu_HelpDesk_M.jsp");
             rd.forward(request, response);

             Log.info.println(this, box, "Dispatch to /learn/user/portal/helpdesk/zu_HelpDesk_M.jsp");

         } catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    @SuppressWarnings("unchecked")
	public void errorPage(RequestBox box, PrintWriter out)
        throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
            //  Log.sys.println();

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }
    
}

