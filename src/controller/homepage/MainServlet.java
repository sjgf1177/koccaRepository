// **********************************************************

// 1. �� ��: �������� �����ϴ� ����(�����)
// 2. ���α׷��� : MainServlet.java
// 3. �� ��: �������� ���� ���α׷�(�����)
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2005. 7. 13
// 7. �� ��1:
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

            box.setSession("s_gubun", "0"); // ��޴� �ʱ�ȭ (��޴� �̵��� ���а��� param ������ ó��)

            hostname = request.getHeader("Host");

//            hostname = "freesem.edukocca.or.kr";
//            System.out.println("v_process :: " + v_process);

            box.put("p_hostname", hostname);

            // �� ����� ������ ����Ʈ�� �̵�
            // ���ο� ����� ��/�� ���½� �ȳ� �������� ���� �ʿ�
            if (box.get("p_hostname").indexOf("edum.kocca.or.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='/servlet/controller.mobile.main.MainServlet'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();

                // ���ο� ����� �� ������ �ּ�
                // �� �α��� ����̹Ƿ� �������� �α� ����� ���� �������� ����
            } else if (box.get("p_hostname").indexOf("m.edu.kocca.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='/servlet/controller.mobile.openclass.OpenClassPopularServlet'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();
                // ����� ������ ����Ʈ�� �̵�
            } else if (box.get("p_hostname").indexOf("edu.kocca.co.kr") > -1 || box.get("p_hostname").indexOf("edu.kocca.or.kr") > -1) {
                out.println("<html>");
                out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=euc-kr'><script language = 'javascript'>");
                out.println(" self.location='http://edulms.kocca.kr'; ");
                out.println("</script>");
                out.println("</head>");
                out.println("</html>");
                out.flush();
                // KOCCA ���̹���ī���� Ȩ�������� �̵�
            } else {

                if (ErrorManager.isErrorMessageView()) {
                    box.put("errorout", out);
                }

                box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

                if (v_process.equals("authChange")) { // ���� ����������
                    // ���Ѽ��� ����
                    AlertManager alert = new AlertManager();

                    String v_auth = box.getString("p_auth");
                    String v_userip = request.getHeader("X-Forwarded-For");
                    if (v_userip == null || v_userip.equals("")) {
                        v_userip = request.getRemoteAddr();
                    }

                    box.setSession("gadmin", v_auth);
                    box.setSession("grtype", bean.getGrtype(box));

                    String v_serno = box.getSession("serno"); // ���� ���� �α� �Ϸù�ȣ
                    int v_serno1 = 0; // ���� ���� �α� �Ϸù�ȣ

                    if (v_auth.equals("A1")) { // A1 �϶� ������ üũ
                        box.put("p_userip", v_userip);
                        TutorLoginBean tbean = new TutorLoginBean();
                        if (tbean.adminCheck(box) <= 0) {
                            alert.alertFailMessage(out, "������ �Ұ����� IP�Դϴ�.");
                            box.setSession("gadmin", "ZZ");
                            return;
                        }
                    }

                    if (v_auth.equals("P1") && v_serno.equals("")) { // ������ ������ ��� ���� �α��� ������ �Է��Ѵ�.
                        TutorLoginBean tbean = new TutorLoginBean();
                        v_serno1 = tbean.tutorLogin(box);
                        box.setSession("serno", v_serno1);
                    }
                    // �˻����ǿ��� ������ ��ü���� ���ɿ��θ� �����Ѵ�.
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
                } else if (v_process.equals("usermail")) { // ����� �����ۼ���
                    this.performUsermail(request, response, box, out);
                } else if (v_process.equals("usermailsend")) { // ����� �����ۼ�
                    this.performUsermailSend(request, response, box, out);
                } else if (v_process.equals("ASP_Login")) { // ASP �α���
                    this.performASP_Login(request, response, box, out);
                } else if (v_process.equals("ASP_Login_Insert")) { // ASP ȸ�� ����
                    this.performASP_Login_Insert(request, response, box, out);
                } else if (v_process.equals("ASP_Find_Idpw")) { // ASP ���̵�/�н�����
                    // ã��
                    this.performASP_Find_Idpw(request, response, box, out);
                } else if (v_process.equals("ASP_Login_Update")) { // ASP ȸ����������
                    this.performASP_Login_Update(request, response, box, out);
                } else if (v_process.equals("MainLogin")) { // ����Ȩ������(KOCCA) �α���
                    this.performMainLogin(request, response, box, out);
                } else if (v_process.equals("MemberInfoUpdateCheck")) { // �������� ���� ���� Ȯ�� �� �α���..(�̺�Ʈ �� �ּ� ó�� �ؾ���)

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
                } else if (v_process.equals("nextChange")) { // �������� ���� ���� Ȯ�� �� �α���..������ �����ϱ�(�̺�Ʈ �� �ּ� ó�� �ؾ���)
                    this.performNextChange(request, response, box, out);
                } else if (v_process.equals("researchgate")) {
                    this.performReseachGate(request, response, box, out); // ������������ ����Ʈ
                } else if (v_process.equals("research")) {
                    this.performReseach(request, response, box, out); // ���̹���������ī���� ���� ��������
                } else if (v_process.equals("research_2")) {
                    this.performResearch_2(request, response, box, out); // ���̹���������ī���� ���� ��������2
                } else if (v_process.equals("research_save")) {
                    this.performResarch_save(request, response, box, out);
                } else if (v_process.equals("passChangePage")){
                    this.performPassChangePage(request, response, box, out);
                } else if (v_process.equals("agreeChkPage")){
                    this.performAgreeChkPage(request, response, box, out);
                } else { // ����
                    this.performMainList(request, response, box, out);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ����
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
            // ���� URL����
            box.put("p_servernm", request.getServerName());

            /* localhost ���ӽ� ���� grcode �ο�
			������������ �ּ� ó�� �ؾ��Ѵ�.
			N000001 : �ѱ���������ī����
			N000022 : ��ȭü��������
			N000057 : test
			N000134 : �ѱ������������ / contenttest / contenttest
			N000179 : test2
			N000203 : �����Ӹ��̽��Ͱ�
			N000210 : �ѱ������������(������ ������) / ktest2103 / 1q2w3e4r!
			N000213 : ����ü���������
			N000215 : ���ΰǼ�
			N000219 : ����
			N000241 : �ѱ������������
			N000243 : �����װ�����Ͻ�����б�
			N000244 : ������ȭ�����
			*/
            /*box.setSession("tem_grcode", "N000057");*/

            TempletBean bean = new TempletBean();
            DataBox listBean = bean.SelectGrcodeExists(box);

            String type = "B";

            if (listBean == null) {
                box.setSession("tem_type", "B");
                box.setSession("tem_grcode", "N000001");
                box.setSession("tem_menu_type", ""); // �޴� �׺���̼� Ÿ��
                box.setSession("tem_main_type", ""); // ���� ȭ�� Ÿ��
            } else if (listBean != null && !listBean.get("d_grcode").equals("")) {
                box.setSession("tem_menu_type", listBean.get("d_menutype")); // �޴� �׺���̼� Ÿ��
                box.setSession("tem_grcode", listBean.get("d_grcode"));
                box.setSession("tem_main_type", listBean.get("d_type")); // ���� ȭ�� Ÿ��
                box.setSession("tem_type", listBean.get("d_type"));
                type = listBean.get("d_type");
            } else {
                box.setSession("tem_type", "B");
                box.setSession("tem_grcode", "N000001");
                box.setSession("tem_menu_type", ""); // �޴� �׺���̼� Ÿ��
                box.setSession("tem_main_type", ""); // ���� ȭ�� Ÿ��
            }

            // URL�� GRCODE ��������
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

            // ������ IP
            box.put("p_userip", box.get("userip"));

            // GRCODE�� return URL ����
            String v_url = "";
            String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

            if (tem_grcode.equals("")) {

                // GRCODE �ҽǽ�...���� ������?
                v_url = "/learn/user/portal/homepage/error.jsp";

            } else {
                if (box.getSession("gadmin").equals("P101")) {
                    v_url = "/learn/user/2012/portal/homepage/zu_subj_prof.jsp";
                    ProposeCourseBean courseBean = new ProposeCourseBean();
                    List mainSubjectList = courseBean.selectProfSubjectList(box);
                    request.setAttribute("SubjectList", mainSubjectList);
                } else {
                    if (tem_grcode.equals("N000001")) { // ���� KoccaYn���� ����/ASP ���� ���� ����
                        NoticeAdminBean nbean = new NoticeAdminBean();

                        int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
                        if (tabseq == 0) {

                            // �Խ��� �з��� ���� �κ� ����
                            box.put("p_type", "HN");
                            box.put("p_grcode", "0000000");
                            box.put("p_comp", "0000000000");
                            box.put("p_subj", "0000000000");
                            box.put("p_year", "0000");
                            box.put("p_subjseq", "0000");

                            tabseq = nbean.selectTableseq(box);

                            if (tabseq == 0) {
                                String msg = "�Խ��������� �����ϴ�.";
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

                        // ��� ���
                        KoccaMainBean mainBean = new KoccaMainBean();
                        request.setAttribute("mainBannerList", mainBean.selectBannerList(box));

                        // �������� ���
                        request.setAttribute("mainNoticeList", mainBean.selectNoticeList(box));

                        // �α�˻��� ���
                        request.setAttribute("popularKeywordList", mainBean.selectPopularKeywordList(box));

                        // ��ī���� �̾߱� ���
                        // request.setAttribute("academyStoryList", mainBean.selectAcademyStoryList(box));

                        // ���� ī�װ� �׸�
                        request.setAttribute("mainCategoryList", mainBean.selectMainCategoryList(box));

                        // �˾����� ����Ʈ
                        ArrayList pnlist = nbean.selectListNoticePopupHome(box);
                        request.setAttribute("noticePopup", pnlist);

                    } else {
                        box.setSession("KoccaYn", "N");
                        box.put("authority", "");

                        if(type.equals("B")){
                            MainHomeTypeBBean bBean = new MainHomeTypeBBean();

                            // ���԰��� ����Ʈ
                            ArrayList subjectList = bBean.selectSubjectList(box);
                            request.setAttribute("subjectList", subjectList);

                            // �������� ����Ʈ
                            ArrayList goldClassList = bBean.selectGoldClassList(box);
                            request.setAttribute("goldClassList", goldClassList);

                            box.put("p_tabseq", 12); //�Խ��� seq ����
                            // �������� ����Ʈ
                            ArrayList noticeList = bBean.selectNoticeList(box);
                            request.setAttribute("noticeList", noticeList);

                            box.put("p_popup", "Y"); //�˾� ���� ����
                            // �˾� ����Ʈ
                            ArrayList noticePopup = bBean.selectNoticeList(box);
                            request.setAttribute("noticePopup", noticePopup);
                            v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";

                        }else{
                            NoticeAdminBean nbean = new NoticeAdminBean();

                            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
                            if (tabseq == 0) {

                                // �Խ��� �з��� ���� �κ� ����
                                box.put("p_type", "HN");
                                box.put("p_grcode", "0000000");
                                box.put("p_comp", "0000000000");
                                box.put("p_subj", "0000000000");
                                box.put("p_year", "0000");
                                box.put("p_subjseq", "0000");

                                tabseq = nbean.selectTableseq(box);

                                if (tabseq == 0) {
                                    String msg = "�Խ��������� �����ϴ�.";
                                    AlertManager.historyBack(out, msg);
                                }

                                box.put("p_tabseq", String.valueOf(tabseq));
                            }

                            // ASP ��������
                            HomeNoticeBean noticeBean = new HomeNoticeBean();
                            ArrayList noticeList = noticeBean.selectDirectList(box);
                            request.setAttribute("noticeList", noticeList);

                            // ASP �ǹ�����
                            //                        PracticalCourseHomePageBean Pbean = new PracticalCourseHomePageBean();
                            //                        List practicalList = Pbean.selectList(box);
                            //                        request.setAttribute("practicalList", practicalList);


                            //��������
                            GoldClassHomePageBean Gbean = new GoldClassHomePageBean();
                            List goldclassList = Gbean.selectList(box);
                            request.setAttribute("goldclassList", goldclassList);

                            // �˾����� ����Ʈ
                            ArrayList pnlist = nbean.selectListNoticePopupHome(box);
                            request.setAttribute("noticePopup", pnlist);

                            v_url = "/learn/user/portal/homepage/zu_Online_ASP.jsp";
                        }
                    }
                }
            }

            String userid = box.getSession("userid");
            if(userid.equals("")) {
                this.performMainLogin(request, response, box, out);
            } else {
                AlertManager alert = new AlertManager();
                String v_mainurl = "";
                String msg = "";

                v_mainurl = "/servlet/controller.system.MenuCountServlet";
                box.put("p_process", "writeLog");
                box.put("gubun", "9");
                box.put("menuid", "02");

                alert.alertOkMessage(out, msg, v_mainurl, box);
                return;
            }

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMainList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �˾��󼼺���
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPopupView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);

            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // �Խ��� �з��� ���� �κ� ����
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");

                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
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
     * �������׸���Ʈ
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectNoticeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);
            // �������� ����
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // �Խ��� �з��� ���� �κ� ����
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/
                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
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
     * �������׻󼼺���
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectNoticeView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);
            // �������� ����
            NoticeAdminBean nbean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq", ""));
            if (tabseq == 0) {
                // �Խ��� �з��� ���� �κ� ����
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");

                tabseq = nbean.selectTableseq(box);
                if (tabseq == 0) {
                    String msg = "�Խ��������� �����ϴ�.";
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
     * ���� ��������
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUsermail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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
     * ���� ����
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
                v_msg = "���۵Ǿ����ϴ�.";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "���ۿ� �����߽��ϴ�.";
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            String gubun = box.getString("gubun");
            String v_url = "/learn/user/portal/homepage/zu_Online_ASP_Sub.jsp";

            if(box.getSession("tem_type").equals("B")){
                v_url = "/learn/user/typeB/homepage/zu_Online_ASP.jsp";
            }

            if (gubun.equals("20")) { // �ߺ� ���̵� üũ
                LoginBean bean = new LoginBean();
                int isOk = bean.exists_ID(box);
                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("25")) { // �ߺ� �̸��� üũ
                LoginBean bean = new LoginBean();
                int isOk = bean.exists_EMAIL(box);
                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("30")) { // �α׾ƿ�
                box.put("gubun", "");
                box.sessionInvalidate();
                response.sendRedirect("/");
//                performMainList(request, response, box, out);
            }

            if (gubun.equals("40")) { // ���̵�/ �н����� ã��
                LoginBean bean = new LoginBean();
                String isOk = bean.getASP_Userid(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("50")) { // ��ȣ ã��
                LoginBean bean = new LoginBean();
                String isOk = bean.selectTempPassword(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("60")) { // �α���
                // ���� ȭ�鿡 ���� �ʰ� �ܺο��� �ٷ� ���� ������
                box.put("p_servernm", request.getServerName());

                TempletBean bean = new TempletBean();
                DataBox listBean = bean.SelectGrcodeExists(box);

                NoticeAdminBean nbean = new NoticeAdminBean(); // ASP �˾�����

                if (box.getString("p_aspgubun").equals("kocsc")) {// kocsc �ΰ�� userid�� �޾ƿͼ� pw�� �����´�.
                    DataBox kocscSsoPwd = bean.getKocscSsoPwd(box);
                    box.put("p_pw", kocscSsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("edu1")) {// edu1 �ΰ�� userid�� �޾ƿͼ� pw�� �����´�.
                    DataBox edu1SsoPwd = bean.getEdu1SsoPwd(box);
                    box.put("p_pw", edu1SsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("hns")) {// hns �ΰ�� userid�� Ȯ���� ���� or pw�� �����´�.
                    DataBox HnsSsoPwd = bean.checkHnsUserid(box);
                    box.put("p_pw", HnsSsoPwd.get("d_pwd"));
                } else if (box.getString("p_aspgubun").equals("N000083")) {// kocuco-hi �ΰ�� userid�� Ȯ�� �� ���� or pw�� �����´�.
                    DataBox Kocucohi = bean.checkKocucohi(box);
                    box.put("p_id", Kocucohi.get("d_userid"));
                    box.put("p_pw", Kocucohi.get("d_pwd"));
                }

                if (listBean != null && !listBean.get("d_grcode").equals("")) {
                    box.setSession("tem_menu_type", listBean.get("d_menutype")); // �޴� �׺���̼� Ÿ��
                    box.setSession("tem_grcode", listBean.get("d_grcode"));
                    box.setSession("tem_main_type", listBean.get("d_type")); // ���� ȭ�� Ÿ��
                } else {
                    box.setSession("tem_type", "A");
                    box.setSession("tem_grcode", "N000001");
                    box.setSession("tem_menu_type", ""); // �޴� �׺���̼� Ÿ��
                    box.setSession("tem_main_type", ""); // ���� ȭ�� Ÿ��
                }

                // URL�� GRCODE ��������
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

                // ������ IP
                String v_userip = request.getHeader("X-Forwarded-For");
                if (v_userip == null || v_userip.equals("")) {
                    v_userip = request.getRemoteAddr();
                }

                box.put("p_userip", v_userip);

                // String tem_grcode = box.getStringDefault("tem_grcode",
                // box.getSession("tem_grcode"));
                box.setSession("KoccaYn", "N");

                /*
                // �������� ����
                HomeNoticeBean noticeBean = new HomeNoticeBean();
                HomeLetterBean letterBean = new HomeLetterBean();
                ProposeCourseBean courseBean = new ProposeCourseBean();

                // ASP ��������
                ArrayList noticeList = noticeBean.selectDirectList(box);
                request.setAttribute("noticeList", noticeList);

                // ASP ��������
                ArrayList letterList = letterBean.selectDirectList(box);
                request.setAttribute("letterList", letterList);

                // ASP ���¸���
                List mainSubjectList = courseBean.selectMainSubjectList(box);
                request.setAttribute("mainSubjectList", mainSubjectList);

                // ASP �ǹ�����
                PracticalCourseHomePageBean Pbean = new PracticalCourseHomePageBean();
                List practicalList = Pbean.selectList(box);
                request.setAttribute("practicalList", practicalList);

                // �˾����� ����Ʈ
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
                        // ��ü�� ���������� �α���ó��
                        if( v_servernm.equals("mcst.kocca.or.kr") || v_servernm.equals("mcst.edukocca.or.kr") ){
                            alert.alertOkMessage(out, "�������� ���ٹ���� �ƴմϴ�.", "/servlet/controller.homepage.MainServlet", box);
                        }else{
                            alert.alertOkMessage(out, "�Է��Ͻ� ȸ���� ���� ���� �ʽ��ϴ�.", "/servlet/controller.homepage.MainServlet", box);
                        }
                        return;
                    }

                    if (isPwd == 0) {
                        alert.alertOkMessage(out, "��й�ȣ�� ���� �ʽ��ϴ�.", "/servlet/controller.homepage.MainServlet", box);
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
                            msg = "��й�ȣ�� �������� 3���� �̻� ����Ͽ����ϴ�. ����������ȣ�� ���� ��й�ȣ�� �������ּ���.";
                        }

                    }

                    v_mainurl = "/servlet/controller.system.MenuCountServlet";
                    box.put("p_process", "writeLog");
                    box.put("gubun", "9");
                    box.put("menuid", "02");

                    alert.alertOkMessage(out, msg, v_mainurl, box);
                    return;
                }
            }

            if (gubun.equals("70")) // ȸ����������
            {
                LoginBean bean = new LoginBean();
                DataBox dbox = bean.ASP_Edit_Login(box);
                System.out.println(dbox);
                request.setAttribute("ASP_Edit_Login", dbox);
            }

            if (gubun.equals("89")) // ����Ʈ�̿뵿�� ����
            {
                String v_mainurl = "/servlet/controller.homepage.MainServlet";
                AlertManager alert = new AlertManager();

                LoginBean bean = new LoginBean();
                int isOK = bean.ASP_updateAgreeChk(box);
                String msg = "���ϴ� ��ü���� ȸ���� �ش�ǹǷ�, ����������ȣ�� ���� ��й�ȣ ������ ����帳�ϴ�.";

                box.put("p_process", "passChangePage");
                box.put("p_id", box.getSession("userid"));
                box.put("gubun", "80");

                alert.alertOkMessage(out, msg, v_mainurl, box);

                return;
            }

            if (gubun.equals("100")) // ��ȣ ����
            {
                LoginBean bean = new LoginBean();
                int tmp = bean.changePwd(box);

                if(box.getSession("tem_type").equals("B")) {
                    box.put("gubun", "");
                    box.sessionInvalidate();

                    AlertManager alert = new AlertManager();
                    box.put("p_process", "");
                    alert.alertOkMessage(out, "��й�ȣ�� ����Ǿ����ϴ�. �ٽ� �α��� ���ֽʽÿ�", "/servlet/controller.homepage.MainServlet", box);
                } else {
                    request.setAttribute("ASP_Edit_Login", tmp);
                    box.put("dbLoad_ID_Exists", tmp);
                    v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
                }

            }

            if (gubun.equals("110")) // �Ǹ��������� �̸��� �ֹι�ȣ�� �Է��ϰ� ���� ���ԵǾ� �ִ��� ���θ� Ȯ���Ѵ�.
            {
                String resno = box.getString("p_resno1") + box.getString("p_resno2");
                String flag = "JID";
                String info[] = null;
                String dupinfo = "";
                String conninfo = "";

                Interop interop = new Interop(); // DI,CI ������ ����
                info = interop.Interop(resno, flag).split(";"); // DI�� �����´�.
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

            // �����Ž�������ȸ,�系������,Ȩ�ؼ����� ��� �α��� �� ���� ���ǽǷ� �̵��Ѵ�.
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            String gubun = box.getString("gubun");
            String v_url = "/learn/user/portal/homepage/zu_Online_ASP_Sub.jsp";
            // String v_url =
            // "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";

            if (gubun.equals("40")) // ���̵� ã��
            {
                FreeMailBean bean = new FreeMailBean();
                String isOk = bean.findIdFreeMail(box);

                box.put("dbLoad_ID_Exists", isOk);
                v_url = "/learn/user/portal/homepage/zu_Online_ASP_ExistsID.jsp";
            }

            if (gubun.equals("50")) // ��ȣ ã��
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            // String v_url = "/learn/user/portal/homepage/zu_Online_ASP.jsp";
            String v_url = "/servlet/controller.homepage.MainServlet";
            String v_msg = "";

            LoginBean bean = new LoginBean();
            int isOk = bean.ASP_updateUser(box);

            //v_url = strHttp + v_url; // ������ http�� ������ HTJ

            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "���������� ����Ǿ����ϴ�.";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                v_msg = "�������� ���濡 �����߽��ϴ�.";
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
            //��� �̺�Ʈ���� �Դٴ� ���°��� �ѱ��.

            String v_userip = request.getHeader("X-Forwarded-For");
            if (v_userip == null || v_userip.equals("")) {
                v_userip = request.getRemoteAddr();
            }

            box.put("p_userip", v_userip);

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMobie()\r\n" + ex.getMessage());
        }
    }

    // �̺�Ʈ : �������� ���� ���� ���� �� �α���[�̺�Ʈ ���� �� �ּ� Ȥ�� ���� ��]
    public void performMemberInfoUpdateCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            v_url = "/learn/user/portal/homepage/zu_MemberInfoUpdateCheck.jsp";

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�

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

    // ������ �����ϱ� : �������� ���� �̺�Ʈ
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

    /* ������� ������ ���� �������� */

    public void performReseachGate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";

            String p_link = box.getStringDefault("p_link", "A");

            if (p_link.equals("H1") || p_link.equals("H2") || p_link.equals("E1") || p_link.equals("E2") || p_link.equals("B") || p_link.equals("A")) {
                // v_url =
                // "/learn/user/2012/portal/homepage/zu_Research_gate.jsp"; //����
                // ����Ʈȭ��
                box.put("p_process", "");
                String v_msg = "�������� ���������� �����Ⱓ�� �Ϸ� �Ǿ����ϴ�.";
                AlertManager alert = new AlertManager();
                v_url = "/servlet/controller.homepage.MainServlet";
                alert.alertOkMessage(out, v_msg, v_url, box);
            } else {
                box.put("p_process", "");
                String v_msg = "�߸��� �����Դϴ�.";
                AlertManager alert = new AlertManager();
                v_url = "/servlet/controller.homepage.MainServlet";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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

            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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
                v_msg = "������ �������ּż� �������� �����մϴ�. \\n���̹���������ī���� ���� ���� ����� ���� ������ �ڷ�� \\nȰ���ϰڽ��ϴ�!";
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
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
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReseach()\r\n" + ex.getMessage());
        }
    }
}
