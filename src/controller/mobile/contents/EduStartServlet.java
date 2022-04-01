package controller.mobile.contents;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.beta.BetaEduStartBean;
import com.credu.beta.BetaMasterFormBean;
import com.credu.beta.BetaMasterFormData;
import com.credu.contents.EduScoreData;
import com.credu.contents.EduStartBean;
import com.credu.contents.MasterFormBean;
import com.credu.contents.MasterFormData;
import com.credu.course.SubjGongAdminBean;
import com.credu.exam.ExamUserBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.study.ProjectAdminBean;
import com.credu.study.ProjectBean;
import com.credu.study.ToronBean;
import com.credu.system.StudyCountBean;

/**
 * controller.mobile.contents EduStart.java
 * 
 * @author drsin
 * @Date 2011. 11. 1.
 * @Version
 * 
 */
@WebServlet("/servlet/controller.mobile.contents.EduStartServlet")
public class EduStartServlet extends javax.servlet.http.HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 6331243114760004857L;

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
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        try {
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getStringDefault("p_process", "main"); //process 가 없으면 default --> main
            v_subj = box.getString("p_subj");
            v_year = box.getString("p_year");
            v_subjseq = box.getString("p_subjseq");

            if (!v_subj.equals(""))
                box.setSession("subj", v_subj);
            if (!v_year.equals(""))
                box.setSession("year", v_year);
            if (!v_subjseq.equals(""))
                box.setSession("subjseq", v_subjseq);

            // 맛보기 시 세션이 없을 경우
            if ((box.getString("p_year").equals("2000") || box.getString("p_year").equals("PREV")) && box.getSession("userid").equals("")) {
                box.setSession("userid", "guest1");
            }

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            ErrorManager.isMobileReturnUrl(out, request, box, "AA");

            if ("eduList".equals(v_process)) {
                this.eduListPage(request, response, box, out);
            } else if (v_process.equals("eduCheck")) {
                this.eduCheck(request, response, box, out);//진도체크 저장
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    @SuppressWarnings("unchecked")
    public void eduListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            String v_kind = box.getString("p_kind"); // 과거이력
            String v_subj = "";

            if (box.getString("p_subj").equals(""))
                box.put("p_subj", box.getSession("s_subj"));
            if (box.getString("p_year").equals(""))
                box.put("p_year", box.getSession("s_year"));
            if (box.getString("p_subjseq").equals(""))
                box.put("p_subjseq", box.getSession("s_subjseq"));

            // 과거이력
            if (v_kind.equals("2")) { // 테이블도 없으므로 사용 안함을 유추할 수 있음. by swchoi
                v_url = "/learn/user/contents/z_EduChk_List_PAST.jsp";
                EduStartBean bean = new EduStartBean();
                ArrayList<DataBox> list1 = bean.getPastLog(box);
                request.setAttribute("pastlog", list1);

            } else {

                if (!box.getSession("s_subjseq").equals("0000")) {

                    MasterFormBean mfbean = new MasterFormBean();
                    MasterFormData data = mfbean.SelectMasterFormData(box); //마스터폼 정보
                    request.setAttribute("MasterFormData", data);
                    EduStartBean bean = new EduStartBean();

                    // 과정별 메뉴 접속 정보 추가
                    box.put("p_menu", "01");
                    StudyCountBean scBean = new StudyCountBean();
                    scBean.writeLog(box);

                    ArrayList data1 = null;
                    ArrayList dataTime = null;

                    EduScoreData data2 = bean.SelectEduScore(box);
                    request.setAttribute("EduScore", data2);

                    int result = bean.selectUserPage(box);

                    /****** L : 외주 , N : Normal , O : OBC ,S : SCORM , Y : YESTLEAN *****/
                    if (result == 1) {
                        if (data.getContenttype().equals("N")) { //Normal MasterForm

                            data1 = bean.SelectEduList(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/mobile/contents/z_EduChk_List.jsp";
                        } else if (data.getContenttype().equals("M")) { //Normal MasterForm

                            data1 = bean.SelectEduList(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/mobile/contents/z_EduChk_List.jsp";
                        } else if (data.getContenttype().equals("O") || data.getContenttype().equals("S")) { //OBC,SCORM MasterForm

                            data1 = bean.SelectEduListOBC(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/mobile/contents/z_EduChk_List_OBC.jsp";
                        } else if (data.getContenttype().equals("L")) { //외주

                            data1 = bean.SelectEduList(box); //진도데이터 - 학습진행상황
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/mobile/contents/z_EduChk_List.jsp";
                        }

                        request.setAttribute("EduList", data1);
                        request.setAttribute("EduTime", dataTime);

                    } else {
                        String v_msg = "개인화 페이지라 입과생이 아니면 보실 수 없습니다.";
                        AlertManager alert = new AlertManager();
                        alert.selfClose(out, v_msg);
                    }

                    if (data.getContenttype().equals("L")) {
                        System.out.println("외주");
                    } else {
                        /* ========== 권장진도율, 자기진도율 시작 ========== */
                        if (box.getString("p_year").equals(""))
                            box.put("p_year", box.getSession("s_year"));
                        if (box.getString("p_subjseq").equals(""))
                            box.put("p_subjseq", box.getSession("s_subjseq"));
                        if (box.getString("p_subj").equals("")) {
                            box.put("p_subj", box.getSession("s_subj"));
                        }
                        if (box.getString("s_subj").equals(""))
                            box.put("s_subj", box.getSession("s_subj"));

                        v_subj = box.getString("p_subj");

                        SubjGongAdminBean sbean = new SubjGongAdminBean();
                        String promotion = sbean.getPromotion(box);
                        request.setAttribute("promotion", promotion);

                        String progress = sbean.getProgress(box);
                        request.setAttribute("progress", progress);
                        /* ========== 권장진도율, 자기진도율 끝 ========== */

                        box.put("p_subj", "ALL"); // 과정설문용

                        /*
                         * ========== 과정설문 (사전설문)응시여부
                         * =====SulmunSubjUserBean=====
                         */
                        SulmunSubjUserBean sulbean = new SulmunSubjUserBean();
                        int suldata = sulbean.getUserData(box);
                        box.put("p_suldata", String.valueOf(suldata));
                        int ispaper2 = sulbean.getSubjSulmunPaper(box);
                        box.put("p_ispaper2", String.valueOf(ispaper2));
                        /* ========== 과정설문 응시여부 ========== */

                        box.put("p_subj", "REGIST");
                        /* ========== 가입동기(사후설문) 응시여부 ========== */
                        //SulmunContentsUserBean contentsbean = new SulmunContentsUserBean();
                        SulmunRegistUserBean contentsbean = new SulmunRegistUserBean();
                        // 먼저 컨텐츠설문지 잇는지 확인...(2005.10.13)
                        int ispaper = contentsbean.getContentsSulmunPaper(box);
                        box.put("p_ispaper", String.valueOf(ispaper));
                        int contentsdata = contentsbean.getUserData(box);
                        box.put("p_contentsdata", String.valueOf(contentsdata));
                        /* ========== 컨텐츠평가 응시여부 ========== */

                        box.put("p_subj", v_subj);
                        /* ========== 레포트 제출개수 ============== */
                        ProjectAdminBean report = new ProjectAdminBean();
                        int reportadmin = report.getAdminData(box);
                        box.put("p_report", String.valueOf(reportadmin));
                        /* ========== 레포트 제출개수 끝 =========== */

                        /* ========== 레포트 제출여부 ============== */
                        ProjectAdminBean reportuser = new ProjectAdminBean();
                        int reportdata = reportuser.getUserData(box);
                        box.put("p_reportdata", String.valueOf(reportdata));
                        /* ========== 레포트 제출여부 끝 =========== */

                        /* ========== 평가 갯수 ========== */
                        ExamUserBean exambean = new ExamUserBean();
                        ArrayList examdata = exambean.getUserData(box);
                        request.setAttribute("ExamData", examdata);
                        /* ========== 평가 갯수 ========== */

                        /* ========== 평가 응시여부 ========== */
                        ArrayList examresultdata = exambean.getUserResultData(box);
                        request.setAttribute("ExamResultData", examresultdata);
                        /* ========== 평가 응시여부 ========== */

                        // 2008.09.25
                        ExamUserBean ExamBean = new ExamUserBean();
                        ArrayList ExamList1 = ExamBean.SelectUserList(box);
                        request.setAttribute("ExamUserList", ExamList1);

                        ArrayList ExamList2 = ExamBean.SelectUserResultList(box);
                        request.setAttribute("ExamUserResultList", ExamList2);

                        ProjectBean ProjectBean = new ProjectBean();
                        ArrayList ProjectList = ProjectBean.selectProjectList(box);
                        request.setAttribute("ProjectList", ProjectList);

                        ToronBean ToronBean = new ToronBean();
                        ArrayList ToronList = ToronBean.selectTopicList(box);
                        request.setAttribute("TopicList", ToronList);

                        SulmunSubjUserBean SulmunBean = new SulmunSubjUserBean();
                        box.put("s_subj", v_subj);
                        ArrayList SulmunList = SulmunBean.SelectUserList(box);
                        request.setAttribute("SulmunSubjUserList", SulmunList);

                    }

                } else {

                    BetaMasterFormBean mfbean = new BetaMasterFormBean();
                    BetaMasterFormData data = mfbean.SelectBetaMasterFormData(box); //마스터폼 정보
                    request.setAttribute("BetaMasterFormData", data);
                    BetaEduStartBean bean = new BetaEduStartBean();

                    /*
                     * modified by LeeSuMin 2004.02.23. for OBC ArrayList data1=
                     * bean.SelectEduList(box); //mfSubj Menu List
                     * request.setAttribute("EduList", data1); EduScoreData
                     * data2= bean.SelectEduScore(box);
                     * request.setAttribute("EduScore", data2);
                     * 
                     * if (data.getContenttype().equals("N")){ //Normal
                     * MasterForm v_url =
                     * "/learn/mobile/contents/z_EduChk_List.jsp"; }
                     */
                    ArrayList data1 = null;

                    //BetaEduScoreData  data2= bean.SelectEduScore(box);
                    //request.setAttribute("EduScore", data2);
                    if (data.getContenttype().equals("N")) { //Normal MasterForm
                        data1 = bean.SelectEduList(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List.jsp";
                    } else if (data.getContenttype().equals("M")) { //Normal MasterForm(Old)
                        data1 = bean.SelectEduList(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List.jsp";
                    } else if (data.getContenttype().equals("O") || data.getContenttype().equals("S")) { //OBC,SCORM MasterForm
                        data1 = bean.SelectEduListOBC(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List_SCORM.jsp";
                    }
                    request.setAttribute("EduList", data1);

                    /* ========== 권장진도율, 자기진도율 시작 ========== */
                    //SubjGongAdminBean sbean = new SubjGongAdminBean();

                    //String promotion  = sbean.getPromotion(box);
                    //request.setAttribute("promotion", promotion);
                    //String progress = sbean.getProgress(box);
                    //request.setAttribute("progress", progress);
                    /* ========== 권장진도율, 자기진도율 끝 ========== */

                }

            } // 과거이력 구분 end..

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("eduListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 동영상 닫기 및 정지를 했을때 진도체크 저장
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void eduCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box); //      명시적으로 box 객체를 넘겨준다

            InetAddress address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();
            hostAddress = request.getRemoteAddr();
            box.put("hostAddress", hostAddress);

            //진도체크시 사용
            box.setSession("s_subj", box.getString("p_subj"));
            box.setSession("s_year", box.getString("p_year"));
            box.setSession("s_subjseq", box.getString("p_subjseq"));
            box.setSession("userid", box.getString("p_userid"));
            box.setSession("s_eduauth", "Y");

            EduStartBean bean = new EduStartBean();
            String results = bean.EduCheck(box);

            if (results.equals("OK")) {
                box.put("_result_", "Y");
            } else {
                box.put("_result_", "N");
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/contents/zu_educheck_R.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("eduCheck()\r\n" + ex.getMessage());
        }
    }
}
