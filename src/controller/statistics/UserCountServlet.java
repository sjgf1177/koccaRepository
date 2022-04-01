//**********************************************************
//1. 제      목: 회원등록현황을 제어하는 서블릿
//2. 프로그램명 : UserCountServlet.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성:
//7. 수      정:
//**********************************************************

package controller.statistics;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.statistics.UserCountBean;
import com.credu.system.AdminUtil;

@WebServlet("/servlet/controller.statistics.UserCountServlet")
public class UserCountServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8284886225471131656L;

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

            ///////////////////////////////////////////////////////////////////
            if (!AdminUtil.getInstance().checkRWRight("UserCountServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("selectYearCnt")) { //  년도별 회원등록현황 페이지로 이동할 때
                this.performSelectYearCnt(request, response, box, out);
            } else if (v_process.equals("selectMonthCnt")) { //  월별 회원등록현황 페이지로 이동할 때
                this.performSelectMonthCnt(request, response, box, out);
            } else if (v_process.equals("selectLocationCnt")) { //지역별 회원등록현황 페이지로 이동할 때
                this.performSelectLocationCnt(request, response, box, out);
            } else if (v_process.equals("selectAgeCnt")) { //연령별 회원등록현황 페이지로 이동할 때
                this.performSelectAgeCnt(request, response, box, out);
            } else if (v_process.equals("selectVocationCnt")) { //연령별 회원등록현황 페이지로 이동할 때
                this.performSelectVocationCnt(request, response, box, out);

            } else if (v_process.equals("selectStat")) { //회원가입 통계 기본화면
                this.performSelectStat(request, response, box, out);
            } else if (v_process.equals("selectYearStat")) { //회원가입 누적  New 통계
                this.performSelectYearStat(request, response, box, out);
            } else if (v_process.equals("selectMonthStat")) { // 월별 회원가입 누적  New 통계
                this.performSelectMonthStat(request, response, box, out);
            } else if (v_process.equals("selectAgeStat")) { // 연령별  New 통계
                this.performSelectAgeStat(request, response, box, out);
            } else if (v_process.equals("selectVocationStat")) { // 직업별  New 통계
                this.performSelectVocationStat(request, response, box, out);
            } else if (v_process.equals("selectLocationStat")) { // 지역별  New 통계
                this.performSelectLocationStat(request, response, box, out);
            } else if (v_process.equals("selectWeekStat")) { // 요일별  New 통계
                this.performSelectWeekStat(request, response, box, out);
            } else if (v_process.equals("selectHourStat")) { // 시간별  New 통계
                this.performSelectHourStat(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 년도별 회원등록현황 페이지 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 년도별 회원등록현황 페이지 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectYearStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            //
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberYearStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectYearStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "회원가입 누적");//엑셀 제목
                box.put("tname", "연도|전체|남자|여자|미확인");//컬럼명
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 월별 회원등록현황 페이지 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectMonthStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberMonthStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectMonthStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "월별  신규회원 가입");//엑셀 제목
                box.put("tname", "연도월|전체|남자|여자|미확인");//컬럼명
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연령별 회원등록현황 페이지 New
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
            String v_return_url = "/learn/admin/statistics/za_MemberAgeStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectAgeStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 통계 ");//엑셀 제목
                box.put("tname", "연도월|전체|10대|20대|30대|40대|50대|60대|70대|미확인");//컬럼명
                box.put("tcode", "d_indate|d_usercnt|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 직업별 회원등록현황 페이지 New
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
            String v_return_url = "/learn/admin/statistics/za_MemberVocationStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectVocationStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 통계 ");//엑셀 제목
                box.put("tname", "연도월|전체|고등학생|대학생|공무원|공공기관|프리랜서|예비취업자|회사원|주부|군인|교직원|의료인|법조인|언론인|종교인|연예인|체육인|교수|강사|자영업|기타|미등록 ");//컬럼명
                box.put("tcode",
                        "d_indate|d_usercnt|d_high|d_univ|d_country|d_gonggong|d_freelancer|d_preoffice|d_office|d_jubu|d_miltiary|d_univoffice|d_hospital|d_lawyer|d_broad|d_jongkyo|d_entertain|d_sports|d_proffesor|d_lecter|d_owner|d_etc|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지역별 회원등록현황 페이지 New
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

            String v_return_url = "/learn/admin/statistics/za_MemberLocationStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectLocationStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 통계 ");//엑셀 제목
                box.put("tname", "연도월|전체|서울|경기|부산|인천|대구|대전|광주|전북|전남|충북|충남|강원|경북|경남|울산|제주|기타  ");//컬럼명
                box.put("tcode", "d_indate|d_usercnt|d_seoul|d_kyunggi|d_busan|d_incheon|d_daegu|d_daejeon|d_kwangju|d_jeonbook|d_jeonnam|d_chungbook|d_choongnam|d_gangwon|d_gyungbook|d_gyungnam|d_woolsan|d_jejoo|d_etc");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 요일별 회원등록현황 페이지 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectWeekStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberWeekStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectWeekStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 통계 ");//엑셀 제목
                box.put("tname", "연도|월|전체|월|화|수|목|금|토|일  ");//컬럼명
                box.put("tcode", "d_date_year|d_date_month|d_tot|d_mon|d_tue|d_wed|d_thi|d_fri|d_sat|d_sun");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 시간별 회원등록현황 페이지 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectHourStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_MemberHourStatAjax_L.jsp";

            UserCountBean bean = new UserCountBean();
            ArrayList<DataBox> list = bean.SelectHourStat(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "연령별 통계 ");//엑셀 제목
                box.put("tname", "연도|월|전체|2시|4시|6시|8시 |10시 |12시|14시|16시|18시|20시|22시|24시   ");//컬럼명
                box.put("tcode", "d_date_year|d_date_month|d_tot|d_first|d_second|d_third|d_fourth|d_fifth|d_sixth|d_seventh|d_eightth|d_nineth|d_tenth|d_eleventh|d_twelveth");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 년도별 회원등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectYearCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 월별 회원등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectMonthCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectMonthCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListMonth_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListMonth_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지역별 회원등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectLocationCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectLocationCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListLocation_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListLocation_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 연령별 회원등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectAgeCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectAgeCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListAge_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 직업별 회원등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectVocationCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectVocationCnt(box);
            request.setAttribute("UserCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_UserEntryListVocation_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

}
