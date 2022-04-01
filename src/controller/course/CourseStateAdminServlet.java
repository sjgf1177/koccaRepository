//**********************************************************
//  1. 제      목: 과정운영정보 제어하는 서블릿
//  2. 프로그램명 : CourseStateAdminServlet.java
//  3. 개      요: 과정운영정보 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 8. 13
//  7. 수      정:
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseStateAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.course.CourseStateAdminServlet")
public class CourseStateAdminServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7250147305085305268L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("CourseStateAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("select")) { // 조회할때
                this.performSelectList(request, response, box, out);

            } else if (v_process.equals("selectPre")) { // 조건 검색 전
                this.performSelectPre(request, response, box, out);

            } else if (v_process.equals("CourseStateExcel")) { // 엑셀
                this.performCourseStateExcel(request, response, box, out);

            } else if (v_process.equals("Reporting")) {
                this.performReporting(request, response, box, out);
            }

            // 2009.12.07 과정운영통계 메뉴 개발
            if (v_process.equals("selectSaleList")) { // 매출현황 조회할때
                this.performSelectSaleList(request, response, box, out);

            } else if (v_process.equals("selectPreTotalStat")) { // 통합검색 조건 검색 전
                this.performSelectPreTotalStat(request, response, box, out);

            } else if (v_process.equals("selectTotalStat")) { // 상세보기 조건 검색 후
                this.performSelectTotalStat(request, response, box, out);

            } else if (v_process.equals("selectPreGubunStat")) { // 분야별 조건 검색 전
                this.performSelectPreGubunStat(request, response, box, out);

            } else if (v_process.equals("selectGubunStat")) { // 분야별 조건 검색 후
                this.performSelectGubunStat(request, response, box, out);

            } else if (v_process.equals("selectPreSeqStat")) { // 차수별 조건 검색 전 new
                this.performSelectPreSeqStat(request, response, box, out);

            } else if (v_process.equals("selectSeqStat")) { // 차수별 조건 검색 후 new
                this.performSelectSeqStat(request, response, box, out);

            } else if (v_process.equals("selectPreCourseStat")) { // 과정별 조건 검색 전 new
                this.performSelectPreCourseStat(request, response, box, out);

            } else if (v_process.equals("selectCourseStat")) { // 과정별조건 검색 후 new
                this.performSelectCourseStat(request, response, box, out);

            } else if (v_process.equals("selectPreSatisfyStat")) { // 만족도 조건 검색 전 new
                this.performSelectPreSatisfyStat(request, response, box, out);

            } else if (v_process.equals("selectSatisfyStat")) { // 만족도 검색 후 new
                this.performSelectSatisfyStat(request, response, box, out);

            } else if (v_process.equals("selectAgeStat")) { // 연령별 검색 후 new
                this.performSelectAgeStat(request, response, box, out);

            } else if (v_process.equals("selectGenderStat")) { // 성별 검색 후 new
                this.performSelectGenderStat(request, response, box, out);

            } else if (v_process.equals("selectLocationStat")) { // 지역별 검색 후 new
                this.performSelectLocationStat(request, response, box, out);

            } else if (v_process.equals("selectVocationStat")) { // 직업별 검색 후 new
                this.performSelectVocationStat(request, response, box, out);

            }  else if (v_process.equals("selectGroupStat")) { // 교육그룹별조건 검색 후 new
                this.performSelectGroupStat(request, response, box, out);

            } else if (v_process.equals("selectSaleListPre")) { // 매출현황 조건 검색 전
                this.performSelectSaleListPre(request, response, box, out);

            } else if (v_process.equals("selectSaleListExcel")) { // 엑셀
                this.performSelectSaleListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleList")) { // 입과현황 - 수강생별 - 결제액별 조회할때
                //this.performSelectEnterStudentSaleList(request, response, box, out);
                performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleListPre")) { // 입과현황 - 수강생별 - 결제액별 조건 검색 전
                //this.performSelectEnterStudentSaleListPre(request, response, box, out);
                this.performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleListExcel")) { // 입과현황 - 수강생별 - 결제액별 엑셀
                this.performSelectEnterStudentSaleListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunList")) { // 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  조회할때
                this.performSelectEnterStudentGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunListPre")) { // 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  조건 검색 전
                this.performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunListExcel")) { // 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  엑셀
                this.performSelectEnterStudentGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldList")) { // 입과현황 - 분야별 - 일반  조회할때
                this.performSelectEnterFieldList(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldListPre")) { // 입과현황 - 분야별 - 일반  조건 검색 전
                this.performSelectEnterFieldListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldListExcel")) { // 입과현황 - 분야별 - 일반 엑셀
                this.performSelectEnterFieldListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunList")) { // 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 조회할때
                this.performSelectEnterFieldGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunListPre")) { // 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 조건 검색 전
                this.performSelectEnterFieldGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunListExcel")) { // 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀
                this.performSelectEnterFieldGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseList")) { // 입과현황 - 과정별 - 일반 - 조회할때
                this.performSelectEnterCourseList(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseListPre")) { // 입과현황 - 과정별 - 일반 - 조건 검색 전
                this.performSelectEnterCourseListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseListExcel")) { // 입과현황 - 과정별 - 일반 - 엑셀
                this.performSelectEnterCourseListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunList")) { // 입과현황 - 과정별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 조회할때
                this.performSelectEnterCourseGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunListPre")) { // 입과현황 - 과정별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 조건 검색 전
                this.performSelectEnterCourseGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunListExcel")) { // 입과현황 - 과정별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀
                this.performSelectEnterCourseGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqList")) { // 입과현황 - 차수별 - 일반 조회할때
                this.performSelectEnterSeqList(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqListPre")) { //  입과현황 - 차수별 - 일반  조건 검색 전
                this.performSelectEnterSeqListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqListExcel")) { // 입과현황 - 차수별 - 일반  엑셀
                this.performSelectEnterSeqListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunList")) { // 입과현황 - 차수별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 조회할때
                this.performSelectEnterSeqGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunListPre")) { //  입과현황 - 차수별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  조건 검색 전
                this.performSelectEnterSeqGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunListExcel")) { // 입과현황 - 차수별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별  엑셀
                this.performSelectEnterSeqGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassAllList")) { // 수료현황 전체 조회할때
                this.performSelectPassAllList(request, response, box, out);

            } else if (v_process.equals("selectPassAllListPre")) { // 수료현황 전체 조건 검색 전
                this.performSelectPassAllListPre(request, response, box, out);

            } else if (v_process.equals("selectPassAllListExcel")) { // 수료현황 전체 엑셀
                this.performSelectPassAllListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassCourseList")) { // 수요현황 과정별 조회할때
                this.performSelectPassCourseList(request, response, box, out);

            } else if (v_process.equals("selectPassCourseListPre")) { // 수료현황 과정별 조건 검색 전
                this.performSelectPassCourseListPre(request, response, box, out);

            } else if (v_process.equals("selectPassCourseListExcel")) { // 수현황 과정별 엑셀
                this.performSelectPassCourseListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassPersonList")) { // 조회할때
                this.performSelectPassPersonList(request, response, box, out);

            } else if (v_process.equals("selectPassPersonListPre")) { // 조건 검색 전
                this.performSelectPassPersonListPre(request, response, box, out);

            } else if (v_process.equals("selectPassPersonListExcel")) { // 엑셀
                this.performSelectPassPersonListExcel(request, response, box, out);

            } else if (v_process.equals("selectStudyList")) { // 학습현황 조회할때
                this.performSelectStudyList(request, response, box, out);

            } else if (v_process.equals("selectStudyListPre")) { //  학습현황 조건 검색 전
                this.performSelectStudyListPre(request, response, box, out);

            } else if (v_process.equals("selectStudyListExcel")) { // 엑셀
                this.performSelectStudyListExcel(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunList")) { // 조회할때
                this.performSelectPreSulmunList(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunListPre")) { // 조건 검색 전
                this.performSelectPreSulmunListPre(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunListExcel")) { // 엑셀
                this.performSelectPreSulmunListExcel(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunList")) { // 조회할때
                this.performSelectSatiSulmunList(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunListPre")) { // 조건 검색 전
                this.performSelectSatiSulmunListPre(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunListExcel")) { // 엑셀
                this.performSelectSatiSulmunListExcel(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 종합현황 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreTotalStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //CourseStateAdminBean bean = new CourseStateAdminBean();
            //ArrayList list = bean.selectTotalList(box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_TotalStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_TotalStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 종합현황 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectTotalStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_TotalStatajax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectTotalList(box);

            //ArrayList list = new ArrayList();
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//필수
                box.put("title", "종합 검색");//엑셀 제목
                box.put("tname", "분야|구분|차수명|ID|성별|이름|과정명|수료");//컬럼명
                box.put("tcode", "d_areaname|d_gubun|d_grseqnm|d_userid|d_sex|d_name|d_subjnm|d_graduate");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_TotalStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분야별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreGubunStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //CourseStateAdminBean bean = new CourseStateAdminBean();
            //ArrayList list = bean.selectTotalList(box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_GubunStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분야별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectGubunStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectCategorylList(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_GubunStatAjax_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSeqStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SeqStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectSeqStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_SeqStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSeqList(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "종합 검색");//엑셀 제목
                box.put("tname", "구분|분류|분야|과목수|교육인원|수료인원|수료율|만족도");//컬럼명
                box.put("tcode", "d_grseqnm|d_cate|d_areaname|d_subj_cnt|d_user_cnt|d_grad_cnt|d_grad_rate|d_sul_rate");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정별 리스트(검색전) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // String v_return_url = "/learn/admin/statistics/za_CourseStat_L.jsp";

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정별 리스트(검색후) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_CourseStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectCourseList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "과정별 검색");//엑셀 제목
                box.put("tname", "분야|과정명|교육인원|수료|미수료|만족도");//컬럼명
                box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CousreStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 만족도 통계(검색전) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSatisfyStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SatisfyStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SatisfyStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 만족도 통계(검색후) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectSatisfyStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_SatisfyStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectSatisfyList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "만족도 검색");//엑셀 제목
                box.put("tname", "분야|과정명|교육인원|참여인원|만족도");//컬럼명
                box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_sul_cnt|d_sul_rate");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SatisfyStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연령별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectAgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_AgeStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectAgeList(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 검색");//엑셀 제목
                box.put("tname", "차수명|분류|분야|전체|10대|20대|30대|40대|50대|60대|70대|미확인");//컬럼명
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 성별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectGenderStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_GenderStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectGenderList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "성별 검색");//엑셀 제목
                box.put("tname", "차수명|분류|분야|전체|남자|여자|미확인");//컬럼명
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_man|d_woman|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GenderStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지역별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectLocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_LocationStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectLocationList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "성별 검색"); //엑셀 제목
                box.put("tname", "차수명|분류|분야|전체|서울|경기|부산|인천|대구|대전|광주|전북|전남|충북|충남|강원|경북|경남|울산|제주|기타 ");//컬럼명
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_seoul|d_kyunggi|d_busan|d_incheon|d_daegu|d_daejeon|d_kwangju|d_jeonbook|d_jeonnam|d_chungbook|d_choongnam|d_gangwon|d_gyungbook|d_gyungnam|d_woolsan|d_jejoo|d_etc_cnt");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_LocationStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 직업별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectVocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_VocationStatAjax_L.jsp";
            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectVocationList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "직업별 검색"); //엑셀 제목
                box.put("tname", "차수명|분류|분야|전체|고등학생|대학생|공무원|공공기관|프리랜서|예비취업자|회사원|주부|군인|교직원|의료인|법조인|언론인|종교인|연예인|체육인|교수|강사|자영업|기타  ");//컬럼명
                box.put("tcode",
                        "d_grseqnm|d_areaname|d_cate|d_tot|d_high|d_univ|d_country|d_gonggong|d_freelancer|d_preoffice|d_office|d_jubu|d_miltiary|d_univoffice|d_hospital|d_lawyer|d_broad|d_jongkyo|d_entertain|d_sports|d_proffesor|d_lecter|d_owner|d_etc");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_LocationStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectListCourseState(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseState_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseState_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCourseStateExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectListCourseState(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 리포트보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReporting(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_Reporting_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReporting()\r\n" + ex.getMessage());
        }
    }

    //**************************************************************
    //2009.12.07 과정운영 통계 개발
    //**************************************************************//
    /**
     * 매출현황 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 매출현황 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 매출현황 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 결제액별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 결제액별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 결제액별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentGubunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 수강생별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentGubunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 일반 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterField_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 일반 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterField_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 일반 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 분야별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 일반 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 일반 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 일반 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 과정별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 일반 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeq_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 일반 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeq_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 일반 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 입과현황 - 차수별 - 일반, 회원구분별, 성별, 연령대별, 학력별, 문화산업종사자별, 비문화사업종사자별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 전체 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassAllList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassAll_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 전체 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassAll_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 전체 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassAllList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 과정별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 과정별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 과정별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 개인별 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassPersonList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassPerson_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 개인별 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassPerson_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수료현황 - 개인별 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassPersonList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습현황 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectStudyList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습현황 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습현황 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectStudyList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 사전설문 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<SulmunQuestionExampleData> list = bean.selectPreSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePreSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 사전설문 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePreSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 사전설문 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<SulmunQuestionExampleData> list = bean.selectPreSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 만족도 설문 리스트(검색후)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSatiSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatiSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 만족도 설문 리스트(검색전)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatiSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * 차수별 만족도 설문 엑셀보기
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSatiSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunListExcel()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * 교육그룹별 리스트(검색후) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectGroupStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_GroupStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectGroupList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "교육그룹별 검색");//엑셀 제목
                box.put("tname", "교육그룹|교육인원|수료|미수료|만족도");//컬럼명
                box.put("tcode", "d_grnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GroupStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

}