package controller.mobile.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.DataBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.mobile.course.CourseBean;

/**
 * 프로젝트명 : kocca_java 패키지명 : controller.mobile 파일명 : CourseServlet.java 작성날짜 :
 * 2011. 9. 28. 처리업무 : 수정내용 :
 * 
 * Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.
 */

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.mobile.course.CourseServlet")
public class CourseServlet extends javax.servlet.http.HttpServlet {
    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
     * , javax.servlet.http.HttpServletResponse)
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process"); // process

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if ("courseList".equals(v_process)) {
                this.performCourseListPage(request, response, box, out); // 모바일
                // 과정목록

            } else if ("courseView".equals(v_process)) {
                this.performCourseViewPage(request, response, box, out); // 모바일 과정상세

            } else if ("insertInterest".equals(v_process)) {
                this.performCourseInsertInterest(request, response, box, out); // 관심과정 추가

            } else if ("propose".equals(v_process)) {
                this.performCourseInsertPropose(request, response, box, out); // 모바일수강신청
            }

            if ("courseIntro".equals(v_process)) {
                this.performCourseIntroPage(request, response, box, out); // 모바일 과정목록
            }
            if ("searchCourseList".equals(v_process)) {
                this.performSearchCourseList(request, response, box, out); // 과정검색
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 모바일 과정 목록
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performCourseListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_upperclass = box.getString("p_upperclass");
            String v_middleclass = box.getString("p_middleclass");

            String v_menuid = box.getString("p_menuid");
            String v_depth1 = StringManager.substring(v_menuid, 0, 3);

            CourseBean bean = new CourseBean();

            ArrayList upperlist = bean.getUpperSubjattList(box);
            if (upperlist == null)
                upperlist = new ArrayList();

            if (!"100".equals(v_depth1)) {
                //대분류 목록
                request.setAttribute("_UPPERLIST_", upperlist);
                if ("".equals(v_upperclass) && upperlist.size() > 0) {

                    v_upperclass = ((DataBox) upperlist.get(0)).getString("d_upperclass");
                    ;
                    box.put("p_upperclass", v_upperclass);

                }

                //중분류 목록
                ArrayList middlelist = bean.getMiddleSubjattList(box);
                if (middlelist == null)
                    middlelist = new ArrayList();

                request.setAttribute("_MIDDLELIST_", middlelist);

                if ("".equals(v_middleclass) && middlelist.size() > 0) {
                    v_middleclass = ((DataBox) middlelist.get(0)).getString("d_middleclass");
                    ;
                    box.put("p_middleclass", v_middleclass);
                }
            }

            request.setAttribute("_LIST_", bean.getCourseList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/course/zu_course_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 모바일 과정 상세
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCourseViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            CourseBean bean = new CourseBean();
            DataBox dbox = null;
            if ("ON".equals(box.getString("p_cousegubun")))
                dbox = bean.getOnCouseData(box);
            else
                dbox = bean.getOffCouseData(box);

            if (dbox == null)
                dbox = new DataBox("");
            request.setAttribute("_VIEW_", dbox);

            if ("ON".equals(box.getString("p_cousegubun"))) {
                //차시 목록
                request.setAttribute("_LESSONLIST_", bean.getCourseLessonList(box));

                //강사 모록
                request.setAttribute("_TUTORLIST_", bean.getCourseTutorList(box));
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/course/zu_course_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseViewPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 관심과정 등록
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performCourseInsertInterest(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_msg = "";

            AlertManager alert = new AlertManager();
            CourseBean bean = new CourseBean();

            DataBox mbox = bean.insertInterest(box);

            if (mbox.getBoolean("_result_")) {
                v_msg = "저장 되었습니다.";
                box.put("p_process", box.getStringDefault("p_old_process", "courseList"));
                box.put("p_history_back", "N");
                String v_url = "/servlet/controller.mobile.course.CourseServlet";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = mbox.getString("_msg_");

                if ("".equals(v_msg))
                    v_msg = "저장 중 실패 되었습니다.";

                AlertManager.historyBack(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseInsertInterest()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강신청 등록
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performCourseInsertPropose(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_msg = "";

            AlertManager alert = new AlertManager();
            CourseBean bean = new CourseBean();

            int limitcnt = bean.proposeLimitCount(box);

            if (limitcnt >= 3) {
                v_msg = "해당 차수에 신청 가능한 무료과정 수를 최과하였습니다. (1인 3과목 신청 가능)";
                // String v_url = "/servlet/controller.mobile.course.CourseServlet";
                AlertManager.historyBack(out, v_msg);
            } else {
                DataBox mbox = bean.insertPropose(box);
                if (mbox.getBoolean("_result_")) {
                    v_msg = "수강신청 되었습니다.";
                    box.put("p_process", box.getStringDefault("p_old_process", "courseList"));
                    box.put("p_history_back", "N");
                    String v_url = "/servlet/controller.mobile.course.CourseServlet";
                    alert.alertOkMessage(out, v_msg, v_url, box);
                } else {
                    v_msg = mbox.getString("_msg_");

                    if ("".equals(v_msg))
                        v_msg = "이미 수강신청이 되었거나 수강신청이 실패 되었습니다.";

                    AlertManager.historyBack(out, v_msg);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseInsertInterest()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 안내페이지
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCourseIntroPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            String v_menuid = box.getString("p_menuid");
            String v_url = "/learn/mobile/course/zu_onCourseIntro_R.jsp";
            if ("020010000000".equals(v_menuid))
                v_url = "/learn/mobile/course/zu_offCourseIntro_R.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCourseIntroPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 검색
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSearchCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            // String v_menuid = box.getString("p_menuid");

            CourseBean bean = new CourseBean();
            request.setAttribute("_LIST_", bean.getSearchCourseList(box));

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/course/zu_searchCourse_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSearchCourseList()\r\n" + ex.getMessage());
        }
    }
}
