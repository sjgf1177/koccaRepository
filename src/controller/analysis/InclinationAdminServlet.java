package controller.analysis;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.analysis.InclinationAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunQuestionExampleData;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.analysis.InclinationAdminServlet")
public class InclinationAdminServlet extends HttpServlet implements Serializable {

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

            if (v_process.equals("jobInclinationList")) { //  ����м� - ����
                this.performJobSelectList(request, response, box, out);
            } else if (v_process.equals("carrerInclinationList")) {
                this.performCarrerSelectList(request, response, box, out);
            } else if (v_process.equals("genderInclinationList")) {
                this.performGenderSelectList(request, response, box, out);
            } else if (v_process.equals("ageInclinationList")) {
                this.performAgeSelectList(request, response, box, out);
            } else if (v_process.equals("routeList")) { //  ��κм�
                this.performRouteList(request, response, box, out);
            } else if (v_process.equals("routeListExcel")) { //  ��κм� EXCEL ���
                this.performRouteListExcel(request, response, box, out);
            } else if (v_process.equals("satisfactionList")) { //  �������м�
                this.performSatisfactionList(request, response, box, out);
            } else if (v_process.equals("jobDetailList")) {
                this.performJobDetailList(request, response, box, out);
            } else if (v_process.equals("carrerDetailList")) {
                this.performCarrerDetailList(request, response, box, out);
            } else if (v_process.equals("genderDetailList")) {
                this.performGenderDetailList(request, response, box, out);
            } else if (v_process.equals("ageDetailList")) {
                this.performAgeDetailList(request, response, box, out);
            } else if (v_process.equals("jobExcelList")) {
                this.performJobExcelList(request, response, box, out);
            } else if (v_process.equals("carrerExcelList")) {
                this.performCarrerExcelList(request, response, box, out);
            } else if (v_process.equals("genderExcelList")) {
                this.performGenderExcelList(request, response, box, out);
            } else if (v_process.equals("ageExcelList")) {
                this.performAgeExcelList(request, response, box, out);
            } else if (v_process.equals("jobDetailExcelList")) {
                this.performJobDetailExcelList(request, response, box, out);
            } else if (v_process.equals("carrerDetailExcelList")) {
                this.performCarrerDetailExcelList(request, response, box, out);
            } else if (v_process.equals("genderDetailExcelList")) {
                this.performGenderDetailExcelList(request, response, box, out);
            } else if (v_process.equals("ageDetailExcelList")) {
                this.performAgeDetailExcelList(request, response, box, out);
            } else if (v_process.equals("jobRouteList")) {
                this.performJobRouteList(request, response, box, out);
            } else if (v_process.equals("carrerRouteList")) {
                this.performCarrerRouteList(request, response, box, out);
            } else if (v_process.equals("genderRouteList")) {
                this.performGenderRouteList(request, response, box, out);
            } else if (v_process.equals("ageRouteList")) {
                this.performAgeRouteList(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }

    }

    private void performJobSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_JobInclination_L.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectJobList(box);
            request.setAttribute("JobList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }

    }

    private void performCarrerSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_CarrerInclination_L.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectCarrerList(box);
            request.setAttribute("CarrerList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }
    }

    private void performGenderSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_GenderInclination_L.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectGenderList(box);
            request.setAttribute("GenderList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }
    }

    private void performAgeSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_AgeInclination_L.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectAgeList(box);
            request.setAttribute("AgeList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }
    }

    private void performRouteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //
            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1); // ��躰

            String v_sulnums = box.get("p_sulnums");
            //			System.out.println("������ȣ "+v_sulnums);

            if (!v_sulnums.equals("")) {
                ArrayList<DataBox> sellist = bean.getselText(box);
                request.setAttribute("ResultTextList", sellist); //
            }

            ArrayList<DataBox> list2 = bean.selectResultList(box);
            request.setAttribute("ResultMemberList", list2); // ���κ�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/analysis/za_Route_L.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }

    }

    private void performRouteListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //
            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list1 = bean.SelectObectResultList(box);
            request.setAttribute("SulmunResultList", list1); // ��躰

            String v_sulnums = box.get("p_sulnums");
            //			System.out.println("������ȣ "+v_sulnums);

            if (!v_sulnums.equals("")) {
                ArrayList<DataBox> sellist = bean.getselText(box);
                request.setAttribute("ResultTextList", sellist); //
            }

            ArrayList<DataBox> list2 = bean.selectResultList(box);
            request.setAttribute("ResultMemberList", list2); // ���κ�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/analysis/za_Route_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }

    }

    private void performSatisfactionList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/analysis/za_Satisfaction_L.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<SulmunQuestionExampleData> list1 = bean.SelectObectResultAnalisys(box);

            request.setAttribute("SulmunResultList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performJobSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �˾�
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performJobDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_JobDetailList_P.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectJobDetailList(box);
            request.setAttribute("JobDetailList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �зº� �˾�
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCarrerDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_CarrerDetailList_P.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectCarrerDetailList(box);
            request.setAttribute("CarrerDetailList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �˾�
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performGenderDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_GenderDetailList_P.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectGenderDetailList(box);
            request.setAttribute("GenderDetailList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���̺� �˾�
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performAgeDetailList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_AgeDetailList_P.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectAgeDetailList(box);
            request.setAttribute("AgeDetailList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performJobExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_JobInclination_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectJobList(box);
            request.setAttribute("JobList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �зº� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCarrerExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_CarrerInclination_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectCarrerList(box);
            request.setAttribute("CarrerList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performGenderExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_GenderInclination_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectGenderList(box);
            request.setAttribute("GenderList", list); // ����

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���̺� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performAgeExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_AgeInclination_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectAgeList(box);
            request.setAttribute("AgeList", list); // ���̺�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �˾� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performJobDetailExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_JobDetailList_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectJobDetailList(box);
            request.setAttribute("JobDetailList", list); // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �зº� �˾� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCarrerDetailExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_CarrerDetailList_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectCarrerDetailList(box);
            request.setAttribute("CarrerDetailList", list); // �зº�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� �˾� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performGenderDetailExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_GenderDetailList_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectGenderDetailList(box);
            request.setAttribute("GenderDetailList", list); // ����

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���̺� �˾� Excel List
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performAgeDetailExcelList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_AgeDetailList_E.jsp";

            InclinationAdminBean bean = new InclinationAdminBean();
            ArrayList<DataBox> list = bean.selectAgeDetailList(box);
            request.setAttribute("AgeDetailList", list); // ���̺�

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performExcelList()\r\n" + ex.getMessage());
        }
    }

    public void performJobRouteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_JobRouteList_P.jsp";

            //			InclinationAdminBean bean = new InclinationAdminBean();
            //			ArrayList list = bean.selectJobDetailList(box);
            //			request.setAttribute("JobDetailList", list);  // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    public void performCarrerRouteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_CarrerRouteList_P.jsp";

            //			InclinationAdminBean bean = new InclinationAdminBean();
            //			ArrayList list = bean.selectJobDetailList(box);
            //			request.setAttribute("JobDetailList", list);  // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    public void performGenderRouteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_GenderRouteList_P.jsp";

            //			InclinationAdminBean bean = new InclinationAdminBean();
            //			ArrayList list = bean.selectJobDetailList(box);
            //			request.setAttribute("JobDetailList", list);  // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }

    public void performAgeRouteList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/analysis/za_AgeRouteList_P.jsp";

            //			InclinationAdminBean bean = new InclinationAdminBean();
            //			ArrayList list = bean.selectJobDetailList(box);
            //			request.setAttribute("JobDetailList", list);  // ������

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCorrectionList()\r\n" + ex.getMessage());
        }
    }
}
